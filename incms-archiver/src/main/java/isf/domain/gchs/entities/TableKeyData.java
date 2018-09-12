package isf.domain.gchs.entities;

import java.util.List;

public abstract class TableKeyData
{
	protected Table table;

	protected List<ColumnData> columnData;

	public Table getTable()
	{
		return table;
	}

	public List<ColumnData> getColumnData()
	{
		return columnData;
	}

	public void setColumnData(List<ColumnData> columnData)
	{
		this.columnData=columnData;
	}

	public abstract String getRetrieveSQL();

}
