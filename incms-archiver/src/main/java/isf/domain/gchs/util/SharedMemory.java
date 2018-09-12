package isf.domain.gchs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isf.domain.gchs.entities.PrimaryKeyData;
import isf.domain.gchs.entities.TableKeyData;
import isf.domain.gchs.exceptions.SharedMemoryConflictException;
import isf.domain.gchs.exceptions.SharedMemoryIllegalException;

public class SharedMemory
{

	private final Map<TableKeyData, BlockOwner> blockedPKs;

	private final Map<Integer, List<TableKeyData>> blockOwners;

	private final HashMap<TableKeyData, Boolean> insertedPKs;

	public SharedMemory()
	{
		blockedPKs=new HashMap<TableKeyData, BlockOwner>();
		insertedPKs=new HashMap<TableKeyData, Boolean>();
		blockOwners=new HashMap<Integer, List<TableKeyData>>();
	}

	synchronized public Integer getRowOwner(PrimaryKeyData key)
	{
		BlockOwner b=blockedPKs.get(key);
		if (b == null)
		{
			return null;
		}
		return b.getIdWorkPackage();
	}

	synchronized public boolean blockRow(PrimaryKeyData key, int idWorkPackage, boolean deletableMode)
		throws SharedMemoryConflictException
	{
		if (blockedPKs.containsKey(key))
		{
			return blockExistingRow(key, idWorkPackage, deletableMode);
		}
		else
		{
			return blockNewRow(key, idWorkPackage, deletableMode);
		}
	}

	synchronized private boolean blockExistingRow(PrimaryKeyData key, int idWorkPackage, boolean deletableMode)
		throws SharedMemoryConflictException
	{
		BlockOwner blockOwner=blockedPKs.get(key);
		if (idWorkPackage == blockOwner.getIdWorkPackage())
		{
			return blockExistingRowSameThread(blockOwner, key, idWorkPackage, deletableMode);
		}
		else
		{
			return blockExistingRowDifferentThread(blockOwner, key, idWorkPackage, deletableMode);
		}

	}

	synchronized private boolean blockExistingRowSameThread(BlockOwner blockOwner, PrimaryKeyData key, int idWorkPackage, boolean deletableMode)
		throws SharedMemoryConflictException
	{

		if (!blockOwner.deletableMode && deletableMode)
		{
			blockOwner.deletableMode=deletableMode;
			// Paso por arriba
			return true;
		}
		else if (insertedPKs.containsKey(key))
		{
			// La fila ya fue insertada por el mismo hilo, es onda un "RowAlreadyExists"
			return false;
		}
		else
		{
			// Detectamos circularidad de filas
			throw new SharedMemoryConflictException(blockOwner.getIdWorkPackage(), key);
		}

	}

	synchronized private boolean blockExistingRowDifferentThread(BlockOwner blockOwner, PrimaryKeyData key, int idWorkPackage, boolean deletableMode)
		throws SharedMemoryConflictException
	{

		// Chocaron dos hilos distintos
		if (!blockOwner.deletableMode && deletableMode)
		{
			blockOwners.get(blockOwner.getIdWorkPackage()).remove(key);
			blockOwner.deletableMode=deletableMode;
			blockOwner.idWorkPackage=idWorkPackage;
			blockOwners.get(idWorkPackage).add(key);
			return true;
		}
		else
		{
			throw new SharedMemoryConflictException(blockOwner.getIdWorkPackage(), key);
		}

	}

	synchronized private boolean blockNewRow(PrimaryKeyData key, int idWorkPackage, boolean deletableMode)
		throws SharedMemoryConflictException
	{
		BlockOwner b=new BlockOwner(idWorkPackage, deletableMode);
		blockedPKs.put(key, b);
		if (blockOwners.containsKey(idWorkPackage))
		{
			blockOwners.get(idWorkPackage).add(key);
		}
		else
		{
			List<TableKeyData> keyList=new ArrayList<TableKeyData>();
			keyList.add(key);
			blockOwners.put(idWorkPackage, keyList);
		}
		return true;
	}

	synchronized public void releaseWorkPackage(int idWorkPackage)
	{
		if (blockOwners.get(idWorkPackage) != null)
		{

			for (TableKeyData pk : blockOwners.get(idWorkPackage))
			{
				blockedPKs.remove(pk);
				insertedPKs.remove(pk);
			}
			blockOwners.remove(idWorkPackage);
		}
	}

	synchronized public void unblockRow(PrimaryKeyData key, int idWorkPackage)
		throws SharedMemoryIllegalException
	{
		if (blockedPKs.containsKey(key))
		{
			int blockOwner=blockedPKs.get(key).getIdWorkPackage();
			if (blockOwner == idWorkPackage)
			{
				blockedPKs.remove(key);
				blockOwners.get(idWorkPackage).remove(key);
			}
			else
			{
				throw new SharedMemoryIllegalException();
			}
		}
		else
		{
			throw new SharedMemoryIllegalException();
		}
	}

	synchronized public void markRowAsInserted(PrimaryKeyData key, int idWorkPackage)
		throws SharedMemoryIllegalException
	{

		if (blockedPKs.containsKey(key))
		{
			int blockOwner=blockedPKs.get(key).getIdWorkPackage();
			if (blockOwner == idWorkPackage)
			{
				insertedPKs.put(key, true);
			}
			else
			{
				throw new SharedMemoryIllegalException();
			}
		}
		else
		{
			throw new SharedMemoryIllegalException();
		}
	}

	static class BlockOwner
	{
		private int idWorkPackage;

		private boolean deletableMode;

		public BlockOwner(int idWorkPackage, boolean deletableMode)
		{
			super();
			this.idWorkPackage=idWorkPackage;
			this.deletableMode=deletableMode;
		}

		public int getIdWorkPackage()
		{
			return idWorkPackage;
		}

		public boolean isDeletableMode()
		{
			return deletableMode;
		}
	}
}
