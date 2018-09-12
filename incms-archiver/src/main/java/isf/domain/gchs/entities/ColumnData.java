package isf.domain.gchs.entities;

public class ColumnData
{

	private Column column;

	private Object data;

	public ColumnData(Column column, Object data)
	{
		this.column=column;
		this.data=data;
	}

	public Column getColumn()
	{
		return column;
	}

	public void setColumn(Column column)
	{
		this.column=column;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data=data;
	}

	@Override
	public String toString()
	{
		if (data != null)
		{
			return " " + column.getName() + "=" + data.toString() + ",";
		}
		else
		{
			return " " + column.getName() + "= '' ,";
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
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
		ColumnData other = (ColumnData) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
