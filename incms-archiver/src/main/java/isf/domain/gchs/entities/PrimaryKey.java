package isf.domain.gchs.entities;

import java.util.ArrayList;
import java.util.List;

public class PrimaryKey
{

	private final List<Column> columns;

	public PrimaryKey()
	{
		columns=new ArrayList<Column>();
	}

	public List<Column> getColumns()
	{
		return columns;
	}

	public void addCol(Column pK)
	{
		columns.add(pK);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
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
		PrimaryKey other = (PrimaryKey) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		return true;
	}
	

}
