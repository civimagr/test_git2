package isf.domain.gchs.entities;

public class Column
{

	private final String name;

	private final ColumnDataType dataType;

	private final boolean isNullable;

	public Column(String name, ColumnDataType dataType, boolean isNullable)
	{
		this.name=name;
		this.dataType=dataType;
		this.isNullable=isNullable;
	}

	public String getName()
	{
		return name;
	}

	public ColumnDataType getDataType()
	{
		return dataType;
	}

	public boolean isNullable()
	{
		return isNullable;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result + (isNullable ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Column other = (Column) obj;
		if (dataType != other.dataType)
			return false;
		if (isNullable != other.isNullable)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
