package isf.domain.gchs.entities;

import java.util.Map;

/*
 * Revisions:
 * 
 * 06/06/2016 pcal [PIEI-] Initial.
 */

/**
 * 
 * @author pcal
 * @version 2016.00
 */
public class DatabaseStructure
{
	private final Map<String, Table> tableMap;

	private final Map<String, Column> columnMap;

	private final Map<String, ForeignKey> foreignKeyMap;

	public static final String SEPARATOR="@";

	/**
	 * 
	 * @param tableMap
	 * @param columnMap
	 */
	public DatabaseStructure(Map<String, Table> tableMap, Map<String, Column> columnMap, Map<String, ForeignKey> foreignKeyMap)
	{
		this.tableMap=tableMap;
		this.columnMap=columnMap;
		this.foreignKeyMap=foreignKeyMap;
	}

	/**
	 * @return tableMap
	 */
	public Map<String, Table> getTableMap()
	{
		return tableMap;
	}

	/**
	 * @return columnMap
	 */
	public Map<String, Column> getColumnMap()
	{
		return columnMap;
	}

	/**
	 * @return foreignKeyMap
	 */
	public Map<String, ForeignKey> getForeignKeyMap()
	{
		return foreignKeyMap;
	}

	/**
	 * 
	 */
	public void resetEntityRelations()
	{
		for (Table table : tableMap.values())
		{
			table.setDeletable(false);
			table.setNullable(false);
		}
		for (ForeignKey foreignKey : foreignKeyMap.values())
		{
			foreignKey.setDeleteBorderline(false);
		}

	}

}
