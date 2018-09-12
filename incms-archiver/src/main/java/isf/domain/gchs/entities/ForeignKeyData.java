package isf.domain.gchs.entities;

import java.util.List;

public class ForeignKeyData extends TableKeyData
{

	private ForeignKey foreignKey;

	public ForeignKeyData(Table table, ForeignKey fk, List<ColumnData> colData)
	{
		this.table=table;
		foreignKey=fk;
		columnData=colData;
	}

	public ForeignKey getForeignKey()
	{
		return foreignKey;
	}

	public void setForeignKey(ForeignKey foreignKey)
	{
		this.foreignKey=foreignKey;
	}

	@Override
	public String getRetrieveSQL()
	{
		return table.getRetrieveSQLByFK(foreignKey);
	}

}
