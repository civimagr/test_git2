package isf.domain.gchs.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import isf.domain.gchs.exceptions.InconsistencyDetectedException;
//Prepare syntax for Table handling
public class Table implements Comparable<Table>
{

	private final String name;

	private int visitOrder;

	private boolean isDeletable;

	private boolean isNullable;

	private final PrimaryKey primaryKey;

	private final Map<Table, List<ForeignKey>> foreignKeys;

	private List<Column> columns;

	private final List<Table> childTables;

	private final List<Table> parentTables;

	private boolean isMaster;

	private String retrieveSQLByPK="";

	private final String auxRetrieveSQL;

	private String auxPkSQL;

	private final Map<ForeignKey, String> retrieveSQLByFK;

	private String insertSQL;

	private String updateSQL="";

	private String auxUpdateSQL;

	private String deleteSQL="";

	private final String auxDeleteSQL;

	private static final String SQL_SELECT="SELECT ";

	private static final String SQL_SELECT_FROM="SELECT * FROM ";

	private static final String SQL_FROM=" FROM ";

	private static final String SQL_WHERE=" WHERE";

	private static final String SQL_SPACE=" ";

	private static final String SQL_ORDER_BY=" ORDER BY ";

	private static final String SQL_INSERT_INTO="INSERT INTO ";

	private static final String SQL_VALUES="() VALUES ()";

	private static final String SQL_UPDATE="UPDATE ";

	private static final String SQL_SET=" SET";

	private static final String SQL_DELETE="DELETE ";

	private static final String SQL_QUOTATION="\"";

	private static final String SQL_SPACE_QUOTATION=" \"";

	private static final String SQL_AND_QUOTATION=" AND \"";

	private static final String SQL_QUOTATION_EQUALS_QUESTION_MARK="\" = ?";

	private static final String SQL_QUOTATION_EQUALS_NULL="\" = NULL";

	private static final String SQL_QUESTION_MARK="?";

	private static final String SQL_COMMA=",";

	private static final String SQL_COMMA_QUOTATION=",\"";

	private static final String SQL_COMMA_QUESTION_MARK=",?";

	private static final String SQL_CLOSING_BRACKET=")";

	public Table(String name)
	{
		this.name=name;
		visitOrder=0;
		columns=new ArrayList<Column>();
		primaryKey=new PrimaryKey();
		foreignKeys=new HashMap<Table, List<ForeignKey>>();
		columns=new ArrayList<Column>();
		childTables=new ArrayList<Table>();
		parentTables=new ArrayList<Table>();
		setMaster(false);
		auxRetrieveSQL=SQL_SELECT_FROM + name;
		auxPkSQL=SQL_WHERE;
		retrieveSQLByFK=new HashMap<ForeignKey, String>();
		insertSQL=SQL_INSERT_INTO + name + SQL_VALUES;
		auxUpdateSQL=SQL_UPDATE + name + SQL_SET;
		auxDeleteSQL=SQL_DELETE + name;
	}

	protected void addChildTable(Table t, ForeignKey fk)
	{
		if (!childTables.contains(t))
		{
			childTables.add(t);
		}
		StringBuilder select=new StringBuilder();
		select.append(SQL_SELECT_FROM + t.name + SQL_WHERE);
		for (List<Column> dupla : fk.getColumns())
		{
			if (fk.getColumns().size() == 1)
			{
				select.append(SQL_SPACE_QUOTATION);
			}
			else
			{
				select.append(SQL_AND_QUOTATION);
			}
			select.append(dupla.get(0).getName());
			select.append(SQL_QUOTATION_EQUALS_QUESTION_MARK);
		}
		t.retrieveSQLByFK.put(fk, select.toString());
	}

	public void addColumn(Column col)
	{
		columns.add(col);
		String[] parts=insertSQL.split("[)]");
		if (columns.size() == 1)
		{
			parts[0]+=SQL_QUOTATION + col.getName() + SQL_QUOTATION;
			parts[1]+=SQL_QUESTION_MARK;
			auxUpdateSQL+=SQL_QUOTATION + col.getName() + SQL_QUOTATION_EQUALS_QUESTION_MARK;
		}
		else
		{
			parts[0]+=SQL_COMMA_QUOTATION + col.getName() + SQL_QUOTATION;
			parts[1]+=SQL_COMMA_QUESTION_MARK;
			auxUpdateSQL+=SQL_COMMA_QUOTATION + col.getName() + SQL_QUOTATION_EQUALS_QUESTION_MARK;
		}
		insertSQL=parts[0] + SQL_CLOSING_BRACKET + parts[1] + SQL_CLOSING_BRACKET;
		updateSQL=auxUpdateSQL + auxPkSQL;
	}

	public void addPrimaryKey(Column pK)
	{
		primaryKey.addCol(pK);
		if (primaryKey.getColumns().size() == 1)
		{
			auxPkSQL+=SQL_SPACE_QUOTATION + pK.getName() + SQL_QUOTATION_EQUALS_QUESTION_MARK;
		}
		else
		{
			auxPkSQL+=SQL_AND_QUOTATION + pK.getName() + SQL_QUOTATION_EQUALS_QUESTION_MARK;
		}
		retrieveSQLByPK=auxRetrieveSQL + auxPkSQL;
		updateSQL=auxUpdateSQL + auxPkSQL;
		deleteSQL=auxDeleteSQL + auxPkSQL;
	}

	public ForeignKey addForeignKey(String constraintName, Table tableDest, Column fkO, Column fkD, boolean isBidirectionalConstraint)
	{
		if (!parentTables.contains(tableDest))
		{
			parentTables.add(tableDest);
		}
		List<ForeignKey> fklist=foreignKeys.get(tableDest);
		ForeignKey fk=null;
		if (fklist == null)
		{
			fklist=new ArrayList<ForeignKey>();
			foreignKeys.put(tableDest, fklist);
		}
		else
		{
			for (ForeignKey fkaux : fklist)
			{
				if (fkaux.getName().equals(constraintName))
				{
					fk=fkaux;
				}
			}
		}
		if (fk == null)
		{
			fk=new ForeignKey(constraintName, this, tableDest);
			fklist.add(fk);
		}
		fk.addCol(fkO, fkD);
		if (isBidirectionalConstraint)
		{
			tableDest.addChildTable(this, fk);
		}
		return fk;
	}

	public void setVisitOrder(int visitOrder)
	{
		this.visitOrder=visitOrder;
	}

	public int getVisitOrder()
	{
		return visitOrder;
	}

	public void setDeletable(boolean isDeletable)
	{
		this.isDeletable=isDeletable;
	}

	public boolean isDeletable()
	{
		return isDeletable;
	}

	public String getName()
	{
		return name;
	}

	public List<Column> getColumns()
	{
		return columns;
	}

	public PrimaryKey getPrimaryKey()
	{
		return primaryKey;
	}

	public List<Table> getChildTables()
	{
		return childTables;
	}

	public List<Table> getParentTables()
	{
		return parentTables;
	}

	public List<ForeignKey> getForeignKeyT(Table t)
	{
		return foreignKeys.get(t);
	}

	public ForeignKey getForeignKeyF(String fkS)
		throws InconsistencyDetectedException
	{
		for (List<ForeignKey> fkL : foreignKeys.values())
		{
			for (ForeignKey fk : fkL)
			{
				if (fk.getName().equals(fkS))
				{
					return fk;
				}
			}
		}
		throw new InconsistencyDetectedException("Error: FK " + fkS + " inconsistente, verificar si existe en base origen");
	}

	public Map<Table, List<ForeignKey>> getForeignKeys()
	{
		return foreignKeys;
	}

	public boolean isMaster()
	{
		return isMaster;
	}

	public void setMaster(boolean isMaster)
	{
		this.isMaster=isMaster;
	}

	public String getRetrieveSQLByPK()
	{
		return retrieveSQLByPK;
	}

	public String getRetrieveSQLByFK(ForeignKey foreignKey)
	{
		return retrieveSQLByFK.get(foreignKey);
	}

	public String getRetrieveSQLByCondition(String packageDelimiter, String sqlCondition)
	{
		StringBuilder sql=new StringBuilder();
		sql.append(SQL_SELECT);
		int count=0;
		for (Column column : primaryKey.getColumns())
		{
			if (count > 0)
			{
				sql.append(SQL_COMMA);
			}
			sql.append(column.getName());
			count++;
		}
		if (packageDelimiter != null && packageDelimiter.length() > 0)
		{
			sql.append(SQL_COMMA + packageDelimiter);
		}
		sql.append(SQL_FROM + name + SQL_WHERE + SQL_SPACE + sqlCondition);
		if (packageDelimiter != null && packageDelimiter.length() > 0)
		{
			sql.append(SQL_ORDER_BY + packageDelimiter);
		}
		return sql.toString();
	}

	public String getInsertSQL()
	{
		return insertSQL;
	}

	public String getUpdateSQL()
	{
		return updateSQL;
	}

	public String getDeleteSQL()
	{
		return deleteSQL;
	}

	/**
	 * 
	 * @param isNullable
	 */
	public void setNullable(boolean isNullable)
	{
		this.isNullable=isNullable;

	}

	public boolean isNullable()
	{
		return isNullable;
	}

	/**
	 * 
	 * @param previousPK
	 * @return
	 */
	public String getUpdateNullSQL(PrimaryKeyData previousPK)
	{

		String sql=SQL_UPDATE + name + SQL_SET + SQL_QUOTATION + previousPK.getColumnData().get(0).getColumn().getName() + SQL_QUOTATION_EQUALS_NULL + auxPkSQL;
		return sql;
	}

	@Override
	public int compareTo(Table compareTable)
	{
		int compareOrder=compareTable.getVisitOrder();
		// ascending order
		// return visitOrder - compareOrder;

		// descending order
		return compareOrder - visitOrder;

	}

	@Override
	public int hashCode()
	{
		final int prime=31;
		int result=1;
		result=prime * result + ((name == null)
			? 0
			: name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		Table other=(Table)obj;
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		return true;
	}

	public void sortParentAndChilds()
	{
		Collections.sort(parentTables);
		Collections.sort(childTables);
	}

}
