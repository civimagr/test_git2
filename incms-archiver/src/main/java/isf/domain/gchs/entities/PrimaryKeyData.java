package isf.domain.gchs.entities;

import java.util.List;

public class PrimaryKeyData extends TableKeyData
{

	private PrimaryKey primaryKey;

	public PrimaryKeyData(Table t, PrimaryKey pk, List<ColumnData> pkData)
	{
		table=t;
		primaryKey=pk;
		columnData=pkData;

	}

	public PrimaryKey getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey)
	{
		this.primaryKey=primaryKey;
	}

	@Override
	public String toString()
	{
		StringBuilder ret=new StringBuilder();
		int cont=0;
		for (ColumnData col : columnData)
		{
			if (cont > 0)
			{
				ret.append(",");
			}
			ret.append(col.getData().toString());
			cont++;
		}
		return ret.toString();
	}

	@Override
	public String getRetrieveSQL()
	{
		return table.getRetrieveSQLByPK();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((primaryKey == null) ? 0 : primaryKey.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimaryKeyData other = (PrimaryKeyData) obj;
		if (primaryKey == null) {
			if (other.primaryKey != null)
				return false;
		} else if (!primaryKey.equals(other.primaryKey))
			return false;
		if (columnData==null)
		
			{	if (other.columnData != null)
				return false;
		}
			else if (!columnData.equals(other.columnData))
				return false;
		
		
		return true;
	}

}
