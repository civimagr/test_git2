package isf.domain.gchs.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import isf.domain.gchs.entities.ExecutionMode;
import isf.domain.gchs.entities.ForeignKey;
import isf.domain.gchs.entities.ForeignKeyData;
import isf.domain.gchs.entities.PendingRow;
import isf.domain.gchs.entities.PrimaryKeyData;
import isf.domain.gchs.entities.RowData;
import isf.domain.gchs.entities.TableKeyData;
import isf.domain.gchs.entities.WorkPackage;
import isf.domain.gchs.exceptions.ChildRecordFoundException;
import isf.domain.gchs.exceptions.InconsistencyDetectedException;
import isf.domain.gchs.exceptions.LobManipulationException;
import isf.domain.gchs.exceptions.ParentKeyNotFoundException;
import isf.domain.gchs.exceptions.RowAlreadyExistException;
import isf.domain.gchs.exceptions.SharedMemoryConflictException;
import isf.domain.gchs.exceptions.SharedMemoryIllegalException;

public class Archiver
{
	private static final Log LOG=LogFactory.getLog(Archiver.class);

	private final WorkPackage workPackage;

	private final Connection connOrig;

	private final Connection connDest;

	private String lastFKNotFound=null;

	private final boolean debugMode;

	private final ExecutionMode execMode;

	private final SharedMemory sharedMemory;

	private final ProcessStatsHelper stats;

	public Archiver(WorkPackage workPackage, SharedMemory sharedMemory, ProcessStatsHelper stats, boolean debugMode, ExecutionMode execMode, Connection connection, Connection destConnection)
	{
		this.workPackage=workPackage;
		this.debugMode=debugMode;
		this.sharedMemory=sharedMemory;
		this.stats=stats;
		this.execMode=execMode;
		connOrig=connection;
		connDest=destConnection;
	}

	public void startArchiving()
		throws SharedMemoryConflictException,
		SQLException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		LobManipulationException
	{
		try
		{
			LOG.info(String.format("Archiving thread %d started", workPackage.getId()));

			for (PrimaryKeyData pk : workPackage.getContent())
			{
				archive(pk, pk.getTable().isDeletable(), null, new DebugArchiveInfo());
			}

			LOG.info(String.format("Archiving thread %d finished successfully", workPackage.getId()));
		}
		finally
		{
			sharedMemory.releaseWorkPackage(workPackage.getId());
		}
	}

	private void archive(TableKeyData key, boolean inAffectedRegion, PrimaryKeyData previousPK, DebugArchiveInfo d)
		throws SharedMemoryConflictException,
		SQLException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		LobManipulationException
	{
		List<RowData> pendingDeleteRows=new ArrayList<RowData>();

		// STEP 1: Retrieve rows from database by PK or FK
		List<RowData> rowList=RowData.retrieve(key, connOrig, stats);

		for (RowData row : rowList)
		{
			// STEP 2: Archive parents (only for deletable tables).
			archiveParents(row, inAffectedRegion, previousPK, d);

			// STEP 3: Block row
			if (!archiveBlockRow(row, rowList, inAffectedRegion))
			{
				continue;
			}

			// STEP 4: Print debug information
			if (debugMode)
			{
				StringBuilder log=new StringBuilder();
				log.append(workPackage.getId() + " - ");
				log.append(d.toString());
				if (inAffectedRegion)
				{
					log.append("!");
				}
				log.append(" - " + key.getTable().getName() + " PK: " + row.getPrimaryKey().toString());
				LOG.debug(log.toString());
			}

			if (connDest != null)
			{
				List<PendingRow> pendingArchiveRows=new ArrayList<PendingRow>();

				// STEP 5: Insert row in destination database
				archiveInsert(row, rowList, pendingArchiveRows, pendingDeleteRows, inAffectedRegion, d);

				// STEP 6: Insert pending rows (caused by circularities)
				archiveInsertPendingRows(pendingArchiveRows, row, d);
			}
			else
			{
				sharedMemory.markRowAsInserted(row.getPrimaryKey(), workPackage.getId());
			}

			// STEP 7a: If it's a master table, we finished

			if (key.getTable().isMaster() || ExecutionMode.COPY_MASTER_TABLES.equals(execMode))
			{
				return;
			}

			// STEP 7b: Otherwise, we archive child rows
			List<ForeignKeyData> childFks=row.getChildFks();
			for (ForeignKeyData childFk : childFks)
			{
				archiveChild(childFk, childFks, row, inAffectedRegion, d);
			}

			// STEP 8: Delete the row
			archiveDeleteOrSetNull(row, previousPK, inAffectedRegion);

			// STEP 9: Delete pending rows (caused by circularities)
			archiveDeletePendingRows(pendingDeleteRows);
		}
	}

	private void archiveParents(RowData row, boolean inAffectedRegion, PrimaryKeyData previousPK, DebugArchiveInfo d)
		throws SharedMemoryConflictException,
		SQLException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		LobManipulationException
	{
		if (inAffectedRegion && row.getTable().isDeletable())
		{
			for (PrimaryKeyData pk : row.getDeletableParentPks())
			{
				if ((previousPK == null) || (!pk.equals(previousPK)))
				{
					archive(pk, inAffectedRegion, null, d.incrementParent());
				}
			}
		}
	}

	private boolean archiveBlockRow(RowData row, List<RowData> rowList, boolean inAffectedRegion)
		throws SharedMemoryConflictException
	{
		try
		{
			return sharedMemory.blockRow(row.getPrimaryKey(), workPackage.getId(), inAffectedRegion);
		}
		catch (SharedMemoryConflictException e)
		{
			// Estoy parado justo donde se detectó la circularidad
			for (RowData r : rowList.subList(rowList.indexOf(row) + 1, rowList.size()))
			{
				e.addPendingArchiveRow(new PendingRow(r.getPrimaryKey(), inAffectedRegion)); // Probablemente esto no pase nunca
			}
			throw e;
		}
	}

	private void archiveInsert(RowData row, List<RowData> rowList, List<PendingRow> pendingArchiveRows, List<RowData> pendingDeleteRows, boolean inAffectedRegion, DebugArchiveInfo d)
		throws LobManipulationException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		SharedMemoryConflictException,
		SQLException
	{
		boolean fin=false;
		boolean loopDetected=false;
		boolean updateRequired=false;
		while (!fin)
		{
			try
			{
				insertOrUpdateRow(row, updateRequired, loopDetected, stats, connDest);
				fin=true;
				lastFKNotFound=null;
				sharedMemory.markRowAsInserted(row.getPrimaryKey(), workPackage.getId());
			}
			catch (ParentKeyNotFoundException pe)
			{
				LOG.debug("Parent not found on insert", pe);
				if (detectInsertLoop(pe, row))
				{
					loopDetected=true;
				}
				else
				{
					recoverFromInsertConflict(pe, row, rowList, pendingArchiveRows, pendingDeleteRows, inAffectedRegion, d);
				}
			}
			catch (RowAlreadyExistException re)
			{
				LOG.debug("Row already exists on insert", re);
				boolean masterTablesMode=ExecutionMode.COPY_MASTER_TABLES.equals(execMode);
				fin=masterTablesMode && !row.getTable().isMaster() || !masterTablesMode && !inAffectedRegion;
				updateRequired=masterTablesMode && row.getTable().isMaster() || !masterTablesMode && inAffectedRegion;
			}
		}
	}

	/**
	 * 
	 * @param row
	 * @param updateRequired
	 * @param loopDetected
	 * @param stats2
	 * @param connDest2
	 * @throws LobManipulationException
	 * @throws SQLException
	 * @throws RowAlreadyExistException
	 * @throws ParentKeyNotFoundException
	 */
	private void insertOrUpdateRow(RowData row, boolean updateRequired, boolean loopDetected, ProcessStatsHelper stats, Connection connection)
		throws ParentKeyNotFoundException,
		RowAlreadyExistException,
		SQLException,
		LobManipulationException
	{
		if (!updateRequired)
		{
			row.insert(connection, loopDetected, stats);
		}
		if (updateRequired || loopDetected)
		{
			row.update(connection, stats);
		}
	}

	private void archiveInsertPendingRows(List<PendingRow> pendingArchiveRows, RowData row, DebugArchiveInfo d)
		throws SharedMemoryConflictException,
		SQLException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		LobManipulationException
	{
		for (PendingRow pendingRow : pendingArchiveRows)
		{
			archive(pendingRow.getKeyData(), pendingRow.isInDeletableRegion(), row.getPrimaryKey(), d.incrementPending());
		}
	}

	private void archiveChild(ForeignKeyData childFk, List<ForeignKeyData> childFks, RowData row, boolean inAffectedRegion, DebugArchiveInfo d)
		throws SQLException,
		SharedMemoryIllegalException,
		InconsistencyDetectedException,
		LobManipulationException,
		SharedMemoryConflictException
	{
		boolean fin=false;
		while (!fin)
		{
			boolean childInAffectedRegion=inAffectedRegion && (childFk.getTable().isDeletable() || childFk.getTable().isNullable());
			if (connDest != null || childInAffectedRegion)
			{
				try
				{
					archive(childFk, childInAffectedRegion, row.getPrimaryKey(), d.incrementChild());
					fin=true;
				}
				catch (SharedMemoryConflictException e)
				{
					recoverFromChildConflict(e, childFk, childFks, row, inAffectedRegion);
				}
			}
			else
			{
				fin=true;
			}
		}
	}

	private void archiveDeleteOrSetNull(RowData row, PrimaryKeyData previousPK, boolean inAffectedRegion)
		throws SQLException,
		LobManipulationException,
		InconsistencyDetectedException
	{
		if (!inAffectedRegion)
		{
			return;
		}
		else if (row.getTable().isDeletable())
		{
			boolean finished=false;
			while (!finished)
			{
				try
				{
					row.delete(connOrig, stats);
					finished=true;
				}
				catch (ChildRecordFoundException e)
				{
					// LOG.debug("Child record found on delete", e);
					recoverFromDeleteConflict(e, row);
				}
			}
		}
		else if (row.getTable().isNullable())
		{
			row.setNullReferenceColumn(connOrig, previousPK, stats);
		}
		else
		{
			throw new SQLException("Error critico, borrado de tabla " + row.getTable().getName() + " no borrable ni nullable.");
		}
	}

	private void archiveDeletePendingRows(List<RowData> pendingDeleteRows)
		throws SQLException,
		LobManipulationException,
		InconsistencyDetectedException
	{
		if (connDest != null)
		{
			for (RowData rowToDelete : pendingDeleteRows)
			{
				archiveDeleteOrSetNull(rowToDelete, null, true);
			}
		}
	}

	private void recoverFromChildConflict(SharedMemoryConflictException e, ForeignKeyData childFk, List<ForeignKeyData> childFks, RowData row, boolean inAffectedRegion)
		throws SharedMemoryConflictException,
		InconsistencyDetectedException
	{
		if (e.getBlockOwner() == workPackage.getId())
		{
			// Estoy bloqueado conmigo mismo
			if (e.getBlockedPk().equals(row.getPrimaryKey()))
			{
				// la fila bloqueada soy yo mismo
				// Esto no debería ocurrir
				throw new InconsistencyDetectedException("Circularidad sin sentido en hijas!!!");
			}
			else
			{
				// Agrego solo las que faltan
				for (ForeignKeyData pfk : childFks.subList(childFks.indexOf(childFk) + 1, childFks.size()))
				{
					boolean childInAffectedRegion2=inAffectedRegion && (pfk.getTable().isDeletable() || pfk.getTable().isNullable());
					e.addPendingArchiveRow(new PendingRow(pfk, childInAffectedRegion2));
				}
				if (inAffectedRegion)
				{
					if (row.getTable().isDeletable())
					{
						e.addPendingDeleteRow(row);
					}
					else if (row.getTable().isNullable())
					{
						throw new InconsistencyDetectedException("Child conflict on nullable table not supported");
					}
				}
				throw e;
			}
		}
		else
		{
			// Estoy bloqueado por otro hilo, espero
			waitForOtherThread();
		}
	}

	private void recoverFromDeleteConflict(ChildRecordFoundException e, RowData row)
		throws SQLException,
		LobManipulationException,
		InconsistencyDetectedException
	{
		int waitForOtherThread=0;
		ForeignKeyData fKData=row.getChildFk(e.getFKName());
		List<RowData> childRows=RowData.retrieve(fKData, connOrig, stats);
		boolean nullableChild=fKData.getTable().isNullable();
		for (RowData childRow : childRows)
		{
			Integer otherThread=sharedMemory.getRowOwner(childRow.getPrimaryKey());
			if (otherThread != null && otherThread.intValue() != workPackage.getId())
			{
				waitForOtherThread=otherThread.intValue();
			}
		}

		if (waitForOtherThread > 0)
		{
			// No pude borrar porque una fila hija está bloqueada por otro hilo, debo esperar
			waitForOtherThread();
		}
		else if (nullableChild)
		{
			// Seteo nulo sin bloqueo
			for (RowData childRow : childRows)
			{
				childRow.setNullReferenceColumn(connOrig, row.getPrimaryKey(), stats);
			}
		}
		else
		{
			LOG.error(row.getTable().getName() + " - " + row.getPrimaryKey().toString() + " no puede ser borradas por hijo " + e.getFKName());
			throw new InconsistencyDetectedException("Verifique configuración, hay filas(" + row.getTable().getName() + " - " + row.getPrimaryKey().toString() + ") que no pueden ser borradas por hijo " + e.getFKName());
		}

	}

	private boolean detectInsertLoop(ParentKeyNotFoundException pe, RowData row)
		throws InconsistencyDetectedException
	{
		String fkName=pe.getFKName();

		if (lastFKNotFound != null && fkName.equals(lastFKNotFound))
		{
			throw new InconsistencyDetectedException("Se detectó que hay datos inconsistentes para la FK  " + fkName + " en la tabla " + row.getTable().getName() + " que impiden insertar en la base destino. Verifique si esta FK está validada.");
		}

		lastFKNotFound=fkName;
		PrimaryKeyData parentPk=row.getParentPkByFkName(fkName);
		if (parentPk.getTable().isMaster() && !ExecutionMode.COPY_MASTER_TABLES.equals(execMode))
		{
			throw new InconsistencyDetectedException("Se detectó que se requieren filas de la tabla " + parentPk.getTable().getName() + " que es maestra, y esto no debería ocurrir. No se puede continuar. Verifique que se ejecutó correctamente el proceso de copia de tablas maestras.");
		}
		return parentPk.equals(row.getPrimaryKey());

	}

	private void recoverFromInsertConflict(ParentKeyNotFoundException pe, RowData row, List<RowData> rowList, List<PendingRow> pendingArchiveRows, List<RowData> pendingDeleteRows, boolean inAffectedRegion, DebugArchiveInfo d)
		throws SQLException,
		SharedMemoryIllegalException,
		LobManipulationException,
		SharedMemoryConflictException,
		InconsistencyDetectedException
	{
		String fkName=pe.getFKName();
		ForeignKey fk=row.getTable().getForeignKeyF(fkName);
		PrimaryKeyData parentPk=row.getParentPkByFkName(fkName);
		boolean finished=false;
		while (!finished)
		{
			try
			{
				boolean parentInAffectedRegion=inAffectedRegion && (parentPk.getTable().isDeletable() || parentPk.getTable().isNullable()) && !fk.isDeleteBorderline();
				archive(parentPk, parentInAffectedRegion, null, d.incrementParent());
				finished=true;
			}
			catch (SharedMemoryConflictException e)
			{
				if (e.getBlockOwner() == workPackage.getId())
				{
					// Estoy bloqueado conmigo mismo
					if (e.getBlockedPk().equals(row.getPrimaryKey()))
					{
						// la fila bloqueada soy yo mismo
						// Volvi atras, en este punto debería poder insertar
						pendingArchiveRows.addAll(e.getPendingArchiveRows());
						pendingDeleteRows.addAll(e.getPendingDeleteRows());
						finished=true;
					}
					else
					{
						for (RowData r : rowList.subList(rowList.indexOf(row), rowList.size()))
						{
							e.addPendingArchiveRow(new PendingRow(r.getPrimaryKey(), inAffectedRegion));
						}
						sharedMemory.unblockRow(row.getPrimaryKey(), workPackage.getId());
						throw e;
					}
				}
				else
				{
					// Estoy bloqueado por otro hilo, lo espero
					waitForOtherThread();
				}
			}
		}

	}

	private void waitForOtherThread()
	{
		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e1)
		{
			LOG.error("InterrumptedExcepton waiting for other thead", e1);
		}
	}

	static class DebugArchiveInfo implements Cloneable
	{
		private int parentCount=0;

		private int childCount=0;

		private int pendingCount=0;

		@Override
		public String toString()
		{
			StringBuilder sb=new StringBuilder();
			for (int i=0; i < parentCount; i++)
			{
				sb.append("P");
			}
			for (int i=0; i < childCount; i++)
			{
				sb.append("H");
			}
			for (int i=0; i < pendingCount; i++)
			{
				sb.append("D");
			}
			return sb.toString();
		}

		public DebugArchiveInfo incrementParent()
		{
			DebugArchiveInfo d=null;
			try
			{
				d=(DebugArchiveInfo)this.clone();
			}
			catch (CloneNotSupportedException e)
			{
				// This should never happen
				LOG.error("Increment Parent does not support cloning", e);
			}
			d.parentCount++;
			return d;
		}

		public DebugArchiveInfo incrementChild()
		{
			DebugArchiveInfo d=null;
			try
			{
				d=(DebugArchiveInfo)this.clone();
			}
			catch (CloneNotSupportedException e)
			{
				// This should never happen
				LOG.error("Increment Child does not support cloning", e);
			}
			d.childCount++;
			return d;
		}

		public DebugArchiveInfo incrementPending()
		{
			DebugArchiveInfo d=null;
			try
			{
				d=(DebugArchiveInfo)this.clone();
			}
			catch (CloneNotSupportedException e)
			{
				// This should never happen
				LOG.error("Increment Pending does not support cloning", e);
			}
			d.pendingCount++;
			return d;
		}

	}
}
