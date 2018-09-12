package isf.domain.gchs.entities;

import java.util.ArrayList;
import java.util.List;

public class ForeignKey
{

	private final String name;

	private final List<List<Column>> columns; // lista de duplas de fk origen nombre destino nombre

	private final Table table;

	private final Table tableDest;

	private boolean deleteBorderline;

	public ForeignKey(String name, Table table, Table tableDest)
	{
		this.name=name;
		columns=new ArrayList<List<Column>>();
		this.table=table;
		this.tableDest=tableDest;
		deleteBorderline=false;
	}

	public String getName()
	{
		return name;
	}

	public List<List<Column>> getColumns()
	{
		return columns;
	}

	public void addCol(Column fkO, Column fkD)
	{
		List<Column> f=new ArrayList<Column>();
		f.add(fkO);
		f.add(fkD);
		columns.add(f);
	}

	@Override
	public String toString()
	{
		StringBuilder ret=new StringBuilder();
		for (List<Column> cols : columns)
		{
			for (Column col : cols)
			{
				ret.append(" -> " + col.getName());
			}
			ret.append("   /   ");
		}
		return ret.toString();
	}

	public Table getTable()
	{
		return table;
	}

	public Table getDestTable()
	{
		return tableDest;
	}

	public boolean isDeleteBorderline()
	{
		return deleteBorderline;
	}

	/**
	 * 
	 * @param deleteBorderline
	 */
	public void setDeleteBorderline(boolean deleteBorderline)
	{
		this.deleteBorderline=deleteBorderline;
	}

}
