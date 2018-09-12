package isf.domain.gchs.entities;

public class PendingRow
{
	private final TableKeyData keyData;

	private final boolean inDeletableRegion;

	public PendingRow(TableKeyData keyData, boolean inDeletableRegion)
	{
		this.keyData=keyData;
		this.inDeletableRegion=inDeletableRegion;
	}

	public TableKeyData getKeyData()
	{
		return keyData;
	}

	public boolean isInDeletableRegion()
	{
		return inDeletableRegion;
	}

}
