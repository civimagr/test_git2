package isf.domain.gchs.entities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import isf.domain.gchs.util.ProcessStatsHelper;
import isf.domain.gchs.exceptions.ChildRecordFoundException;
import isf.domain.gchs.exceptions.InconsistencyDetectedException;
import isf.domain.gchs.exceptions.LobManipulationException;
import isf.domain.gchs.exceptions.ParentKeyNotFoundException;
import isf.domain.gchs.exceptions.RowAlreadyExistException;

public class RowData
{
	private static final Log LOG=LogFactory.getLog(RowData.class);

	private final Table table;

	private final Map<Column, ColumnData> columnData; // Para acelerar la búsqueda por un nombre de columna

	private final List<ColumnData> orderedColumnData; // Porque para insertarlo y actualizarlo lo necesito ordenado

	private final PrimaryKeyData primaryKey;

	public RowData(Table table, List<ColumnData> orderedColumnData, Map<Column, ColumnData> columnData, PrimaryKeyData primaryKey)
	{
		this.table=table;
		this.columnData=columnData;
		this.orderedColumnData=orderedColumnData;
		this.primaryKey=primaryKey;
	}

	@Override
	public String toString()
	{
		StringBuilder rowToString=new StringBuilder();
		rowToString.append(table.getName() + " ");
		for (ColumnData colData : columnData.values())
		{
			rowToString.append(colData.toString());
		}
		return rowToString.toString();
	}

	public Table getTable()
	{
		return table;
	}

	public PrimaryKeyData getPrimaryKey()
	{
		return primaryKey;
	}

	private static RowData createRowDataFromResultSet(ResultSet resultS, TableKeyData key)
		throws SQLException
	{
		Map<Column, ColumnData> columnDataMap=new HashMap<Column, ColumnData>();
		List<ColumnData> columnDataList=new ArrayList<ColumnData>();
		List<ColumnData> pkData=new ArrayList<ColumnData>();
		PrimaryKey pk=key.getTable().getPrimaryKey();
		for (Column column : key.getTable().getColumns())
		{
			Object data=null;
			data=resultS.getObject(column.getName());
			ColumnData columnData=new ColumnData(column, data);
			columnDataMap.put(column, columnData);
			columnDataList.add(columnData);
			if (pk.getColumns().contains(column))
			{
				pkData.add(columnData);
			}
		}
		PrimaryKeyData pkd=new PrimaryKeyData(key.getTable(), key.getTable().getPrimaryKey(), pkData);
		return new RowData(key.getTable(), columnDataList, columnDataMap, pkd);
	}

	public static List<RowData> retrieve(TableKeyData key, Connection connOrig, ProcessStatsHelper stats)
		throws SQLException,
		LobManipulationException
	{
		List<RowData> rowDataList=new ArrayList<RowData>();
		PreparedStatement statement=null;
		ResultSet resultS=null;
		long startingTime=System.currentTimeMillis();
		try
		{
			statement=connOrig.prepareStatement(key.getRetrieveSQL());
			setColumnDataAsStatementVariables(statement, key.getColumnData(), false, null);
			resultS=statement.executeQuery();
			while (resultS.next())
			{
				rowDataList.add(createRowDataFromResultSet(resultS, key));
			}
			stats.informProcessEnded(key.getTable(), StatsType.SELECT, rowDataList.size(), System.currentTimeMillis() - startingTime);
			return rowDataList;
		}
		catch (SQLException e)
		{
			LOG.error("ERROR RETRIVE 010 " + e.getMessage() + key.getRetrieveSQL(), e);
			throw e;
		}
		finally
		{
			try
			{
				if (resultS != null)
				{
					resultS.close();
				}
			}
			catch (SQLException e)
			{
				LOG.error("Error closing result set on retrieve", e);
			}
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				LOG.error("Error closing statement on retrieve", e);
			}
		}
	}

	public void insert(Connection connection, boolean forceNull, ProcessStatsHelper stats)
		throws ParentKeyNotFoundException,
		RowAlreadyExistException,
		SQLException,
		LobManipulationException
	{
		long startingTime=System.currentTimeMillis();
		PreparedStatement statement=null;
		try
		{
			statement=connection.prepareStatement(table.getInsertSQL());
			setColumnDataAsStatementVariables(statement, orderedColumnData, forceNull, connection);
			statement.executeUpdate();
			stats.informProcessEnded(table, StatsType.INSERT, 1, System.currentTimeMillis() - startingTime);
		}
		catch (SQLException e)
		{
			if (e.getErrorCode() == 1)
			{
				stats.informProcessEnded(table, StatsType.INSERT_ALREADY_EXISTS, 1, System.currentTimeMillis() - startingTime);
				throw new RowAlreadyExistException();
			}
			else if (e.getErrorCode() == 2291)
			{
				int fin=e.getMessage().indexOf(")");
				int inicio=e.getMessage().indexOf(".");
				String fkName=e.getMessage().substring(inicio + 1, fin);
				throw new ParentKeyNotFoundException(fkName);
			}
			else
			{
				LOG.error("Row Data Insert " + e.getMessage() + ": " + table.getName() + "-" + primaryKey.getColumnData().get(0).getData().toString(), e);
				throw e;
			}
		}
		finally
		{
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				LOG.error("Error closing statement on insert", e);
			}
		}
	}

	public void update(Connection connection, ProcessStatsHelper stats)
		throws ParentKeyNotFoundException,
		SQLException,
		LobManipulationException
	{
		long startingTime=System.currentTimeMillis();
		try (PreparedStatement statement=connection.prepareStatement(table.getUpdateSQL()))
		{
			List<ColumnData> valuesAndPkList=new ArrayList<ColumnData>();
			valuesAndPkList.addAll(orderedColumnData);
			valuesAndPkList.addAll(primaryKey.getColumnData());
			setColumnDataAsStatementVariables(statement, valuesAndPkList, false, connection);
			statement.executeUpdate();
			stats.informProcessEnded(table, StatsType.UPDATE, 1, System.currentTimeMillis() - startingTime);
		}
		catch (SQLException e)
		{
			if (e.getErrorCode() == 2291)
			{
				int fin=e.getMessage().indexOf(")");
				int inicio=e.getMessage().indexOf(".");
				String fkName=e.getMessage().substring(inicio + 1, fin);
				throw new ParentKeyNotFoundException(fkName);
			}
			else
			{
				LOG.error("Row Data Update" + e.getMessage() + ": " + table.getName() + "-" + primaryKey.getColumnData().get(0).getData().toString(), e);
				throw e;
			}
		}

	}

	public void delete(Connection connection, ProcessStatsHelper stats)
		throws SQLException,
		LobManipulationException,
		ChildRecordFoundException
	{
		long startingTime=System.currentTimeMillis();
		try (PreparedStatement statement=connection.prepareStatement(table.getDeleteSQL()))
		{
			setColumnDataAsStatementVariables(statement, primaryKey.getColumnData(), false, connection);
			statement.executeUpdate();
			stats.informProcessEnded(table, StatsType.DELETE, 1, System.currentTimeMillis() - startingTime);
		}
		catch (SQLException e)
		{
			if (e.getErrorCode() == 2292)
			{
				int fin=e.getMessage().indexOf(")");
				int inicio=e.getMessage().indexOf(".");
				String fkName=e.getMessage().substring(inicio + 1, fin);
				throw new ChildRecordFoundException(fkName);
			}
			else
			{
				LOG.error("Row Data Delete" + e.getMessage() + ": " + table.getName() + "-" + primaryKey.getColumnData().get(0).getData().toString(), e);
				throw e;
			}
		}
	}

	public List<ForeignKeyData> getChildFks()
	{
		List<ForeignKeyData> foreignKeys=new ArrayList<ForeignKeyData>();
		for (Table childTable : table.getChildTables())
		{
			for (ForeignKey fk : childTable.getForeignKeyT(table))
			{
				ForeignKeyData fkd=new ForeignKeyData(childTable, fk, primaryKey.getColumnData());
				foreignKeys.add(fkd);
			}
		}
		return foreignKeys;
	}

	public ForeignKeyData getChildFk(String fkName)
	{
		for (Table childTable : table.getChildTables())
		{
			for (ForeignKey fk : childTable.getForeignKeyT(table))
			{
				if (fk.getName().equals(fkName))
				{
					return new ForeignKeyData(childTable, fk, primaryKey.getColumnData());
				}
			}
		}
		return null;
	}

	public PrimaryKeyData getParentPkByFkName(String fkName)
		throws InconsistencyDetectedException
	{
		ForeignKey fk=table.getForeignKeyF(fkName);
		List<ColumnData> pkData=new ArrayList<ColumnData>();
		for (List<Column> dupla : fk.getColumns())
		{
			Object value=columnData.get(dupla.get(0)).getData();
			Column col=dupla.get(1);
			pkData.add(new ColumnData(col, value));
		}
		Table parentTable=fk.getDestTable();
		PrimaryKeyData pk=new PrimaryKeyData(parentTable, parentTable.getPrimaryKey(), pkData);
		return pk;
	}

	private List<PrimaryKeyData> getDeletableParentPks(Table parentTable)
	{
		List<PrimaryKeyData> foreignKeys=new ArrayList<PrimaryKeyData>();
		Map<Table, List<ForeignKey>> fkMap=table.getForeignKeys();
		for (ForeignKey fk : fkMap.get(parentTable))
		{
			if (!fk.isDeleteBorderline())
			{
				List<ColumnData> pkData=new ArrayList<ColumnData>();

				for (List<Column> dupla : fk.getColumns())
				{
					Object value=columnData.get(dupla.get(0)).getData();
					Column col=dupla.get(1);
					pkData.add(new ColumnData(col, value));
				}
				foreignKeys.add(new PrimaryKeyData(parentTable, parentTable.getPrimaryKey(), pkData));
			}
		}
		return foreignKeys;
	}

	public List<PrimaryKeyData> getDeletableParentPks()
	{
		List<PrimaryKeyData> foreignKeys=new ArrayList<PrimaryKeyData>();
		Map<Table, List<ForeignKey>> fkMap=table.getForeignKeys();

		for (Table parentTable : fkMap.keySet())
		{
			if (parentTable.isDeletable())
			{
				foreignKeys.addAll(getDeletableParentPks(parentTable));
			}

		}
		return foreignKeys;
	}

	public void setNullReferenceColumn(Connection connection, PrimaryKeyData previousPK, ProcessStatsHelper stats)
		throws SQLException,
		LobManipulationException
	{
		long startingTime=System.currentTimeMillis();
		PreparedStatement statement=null;
		try
		{
			statement=connection.prepareStatement(table.getUpdateNullSQL(previousPK));
			List<ColumnData> valuesAndPkList=new ArrayList<ColumnData>();
			valuesAndPkList.addAll(primaryKey.getColumnData());
			setColumnDataAsStatementVariables(statement, valuesAndPkList, false, connection);
			statement.executeUpdate();
			stats.informProcessEnded(table, StatsType.UPDATE, 1, System.currentTimeMillis() - startingTime);
		}
		catch (SQLException e)
		{
			LOG.error("Row Data Set Null " + e.getMessage() + ": " + table.getName() + "-" + primaryKey.getColumnData().get(0).getData().toString(), e);
			throw e;
		}
	}

	private static void setColumnDataDate(PreparedStatement statement, ColumnData column, int position, boolean forceNull)
		throws SQLException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			statement.setTimestamp(position, (java.sql.Timestamp)column.getData());
		}
		else
		{
			statement.setNull(position, java.sql.Types.DATE);
		}
	}

	private static void setColumnDataVarchar2(PreparedStatement statement, ColumnData column, int position, boolean forceNull)
		throws SQLException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			statement.setString(position, (String)column.getData());
		}
		else
		{
			statement.setNull(position, java.sql.Types.VARCHAR);
		}
	}

	private static void setColumnDataNumber(PreparedStatement statement, ColumnData column, int position, boolean forceNull)
		throws SQLException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			statement.setBigDecimal(position, (BigDecimal)column.getData());
		}
		else
		{
			statement.setNull(position, java.sql.Types.NUMERIC);
		}
	}

	private static void setColumnDataClob(PreparedStatement statement, ColumnData column, int position, boolean forceNull, Connection connection)
		throws SQLException,
		LobManipulationException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			Clob origClob=(Clob)column.getData();
			CLOB destClob=CLOB.createTemporary(connection, false, CLOB.DURATION_SESSION);
			try (OutputStream os=destClob.setAsciiStream(origClob.length()); InputStream is=origClob.getAsciiStream())
			{
				byte[] buffer=new byte[destClob.getBufferSize()];
				int byteread=0;
				while ((byteread=is.read(buffer)) != -1)
				{
					os.write(buffer, 0, byteread);
				}
			}
			catch (IOException e)
			{
				LOG.error("Error setting CLOB value " + e.getMessage(), e);
				throw new LobManipulationException();
			}
			statement.setClob(position, destClob);

		}
		else
		{
			statement.setNull(position, java.sql.Types.CLOB);
		}
	}

	private static void setColumnDataLong(PreparedStatement statement, ColumnData column, int position, boolean forceNull)
		throws SQLException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			String s=(String)column.getData();
			StringReader aux=new StringReader(s);
			statement.setCharacterStream(position, aux, s.length());
		}
		else
		{
			statement.setNull(position, java.sql.Types.LONGVARCHAR);
		}
	}

	private static void setColumnDataBlob(PreparedStatement statement, ColumnData column, int position, boolean forceNull, Connection connection)
		throws SQLException,
		LobManipulationException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			Blob origBlob=(Blob)column.getData();
			BLOB destBlob=BLOB.createTemporary(connection, false, BLOB.DURATION_SESSION);
			try (OutputStream os=destBlob.setBinaryStream(origBlob.length()); InputStream is=origBlob.getBinaryStream())
			{
				byte[] buffer=new byte[destBlob.getBufferSize()];
				int byteread=0;
				while ((byteread=is.read(buffer)) != -1)
				{
					os.write(buffer, 0, byteread);
				}
			}
			catch (IOException e)
			{
				LOG.error("Error setting BLOB value " + e.getMessage(), e);
				throw new LobManipulationException();
			}
			statement.setBlob(position, destBlob);
		}
		else
		{
			statement.setNull(position, java.sql.Types.BLOB);
		}
	}

	private static void setColumnDataLongRaw(PreparedStatement statement, ColumnData column, int position, boolean forceNull)
		throws SQLException
	{
		if (column.getData() != null && (!forceNull || !column.getColumn().isNullable()))
		{
			statement.setBinaryStream(position, new ByteArrayInputStream((byte[])column.getData()), ((byte[])column.getData()).length);
		}
		else
		{
			statement.setNull(position, java.sql.Types.LONGVARCHAR);
		}
	}

	private static void setColumnDataAsStatementVariables(PreparedStatement statement, List<ColumnData> columnDataList, boolean forceNull, Connection connection)
		throws SQLException,
		LobManipulationException
	{
		int position=1;
		for (ColumnData column : columnDataList)
		{
			switch (column.getColumn().getDataType())
			{
				case DATE:
					setColumnDataDate(statement, column, position, forceNull);
					break;
				case VARCHAR2:
					setColumnDataVarchar2(statement, column, position, forceNull);
					break;
				case NUMBER:
					setColumnDataNumber(statement, column, position, forceNull);
					break;
				case CLOB:
					setColumnDataClob(statement, column, position, forceNull, connection);
					break;
				case LONG:
					setColumnDataLong(statement, column, position, forceNull);
					break;
				case BLOB:
					setColumnDataBlob(statement, column, position, forceNull, connection);
					break;
				case LONG_RAW:
					setColumnDataLongRaw(statement, column, position, forceNull);
					break;
				default:
					throw new SQLException("Unknown Datatype");
			}
			position++;
		}
	}

}
