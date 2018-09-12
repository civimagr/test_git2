package isf.domain.gchs.exceptions;

import java.util.ArrayList;
import java.util.List;

import isf.domain.gchs.entities.PendingRow;
import isf.domain.gchs.entities.PrimaryKeyData;
import isf.domain.gchs.entities.RowData;

public class SharedMemoryConflictException extends Exception
{

	private static final long serialVersionUID=-7161136860962562636L;

	private final int blockOwner;

	private final PrimaryKeyData blockedPk;

	private final List<PendingRow> pendingArchiveRows;

	private final List<RowData> pendingDeleteRows;

	public SharedMemoryConflictException(int blockOwner, PrimaryKeyData blockedPk)
	{
		this.blockOwner=blockOwner;
		this.blockedPk=blockedPk;
		pendingArchiveRows=new ArrayList<PendingRow>();
		pendingDeleteRows=new ArrayList<RowData>();
	}

	public int getBlockOwner()
	{
		return blockOwner;
	}

	public PrimaryKeyData getBlockedPk()
	{
		return blockedPk;
	}

	public void addPendingArchiveRow(PendingRow row)
	{
		pendingArchiveRows.add(row);
	}

	public void addPendingArchiveRows(List<PendingRow> rows)
	{
		pendingArchiveRows.addAll(rows);
	}

	public List<PendingRow> getPendingArchiveRows()
	{
		return pendingArchiveRows;
	}

	public void addPendingDeleteRow(RowData row)
	{
		pendingDeleteRows.add(row);
	}

	public List<RowData> getPendingDeleteRows()
	{
		return pendingDeleteRows;
	}

}
