package isf.domain.gchs.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import isf.db.Pool;
import isf.persistence.gchs.GchsEntity;
import isf.domain.gchs.entities.Column;
import isf.domain.gchs.entities.ColumnData;
import isf.domain.gchs.entities.ColumnDataType;
import isf.domain.gchs.entities.DatabaseStructure;
import isf.domain.gchs.entities.ForeignKey;
import isf.domain.gchs.entities.PrimaryKeyData;
import isf.domain.gchs.entities.Table;
import isf.domain.gchs.entities.TuplePrimaryKeyDataDelimiter;
import isf.domain.gchs.entities.WorkPackage;

public class DatabaseStructureHelper
{
	private static final String SQL_GET_COLUMNS="SELECT TABLE_NAME,COLUMN_NAME, DATA_TYPE, NULLABLE FROM ALL_TAB_COLUMNS WHERE OWNER = ? and TABLE_NAME not like '%$%' AND TABLE_NAME NOT IN ('HIS_AUDIT_OBJECT','TMP_AUDIT_OBJECT') ORDER BY table_name ASC, column_id ASC";

	private static final String SQL_GET_PKS="SELECT PK.TABLE_NAME , PK.COLUMN_NAME AS PK_NAME FROM ALL_CONSTRAINTS C JOIN ALL_CONS_COLUMNS PK  ON C.CONSTRAINT_NAME= PK.CONSTRAINT_NAME JOIN ALL_TABLES  ALT ON ALT.TABLE_NAME=PK.TABLE_NAME AND ALT.TABLE_NAME=C.TABLE_NAME JOIN all_tab_columns atc ON atc.table_name = pk.table_name AND atc.column_name = pk.column_name WHERE C.OWNER = ? and atc.OWNER <> 'DROLLA' AND C.CONSTRAINT_TYPE = 'P' and PK.TABLE_NAME not in ('HIS_AUDIT_OBJECT','TMP_AUDIT_OBJECT') ORDER BY PK.TABLE_NAME, atc.column_id";

	private static final String SQL_GET_FKS="SELECT A.TABLE_NAME,A.COLUMN_NAME ,C_PK.TABLE_NAME,R_PK.COLUMN_NAME, C.CONSTRAINT_NAME FROM ALL_CONS_COLUMNS A JOIN ALL_CONSTRAINTS  C ON   A.CONSTRAINT_NAME = C.CONSTRAINT_NAME JOIN ALL_CONSTRAINTS  C_PK ON  C.R_CONSTRAINT_NAME = C_PK.CONSTRAINT_NAME JOIN ALL_CONS_COLUMNS R_PK ON  R_PK.CONSTRAINT_NAME= C_PK.CONSTRAINT_NAME  AND  R_PK.POSITION = A.POSITION JOIN ALL_TABLES ALT ON ALT.TABLE_NAME=R_PK.TABLE_NAME WHERE  A.OWNER = ? AND  C.CONSTRAINT_TYPE = 'R' and A.TABLE_NAME not in ('HIS_AUDIT_OBJECT','TMP_AUDIT_OBJECT')  ORDER BY C.CONSTRAINT_NAME , A.POSITION";

	private static final String SQL_GET_MASTER_TABLES="SELECT OST.TABLE_NAME FROM GCRRS_OSA_CIS_TABLES OST JOIN ALL_TABLES  ALT ON ALT.TABLE_NAME = OST.TABLE_NAME WHERE OST.TABLE_TYPE IN ('P','M') and OST.TABLE_NAME not like ('%$%') and alt.OWNER = ? and OST.TABLE_NAME not  in ('HIS_AUDIT_OBJECT','TMP_AUDIT_OBJECT','GCCB_HCAL_LETTER_FORMAT_COPY')";

	private static final String SQL_YES="Y";

	private static final Log LOG=LogFactory.getLog(DatabaseStructureHelper.class);

	public DatabaseStructure loadDatabaseStructure(String owner, List<String> ignoredFks, Connection connection)
		throws SQLException
	{
		Map<String, Table> tableMap=new HashMap<String, Table>();
		Map<String, Column> columnMap=new HashMap<String, Column>();
		Map<String, ForeignKey> foreignKeyMap=new HashMap<String, ForeignKey>();
		int count;

		LOG.debug("Starting to load database structure");

		// STEP 1: Retrieve tables and columns
		LOG.debug("Loading tables and columns");
		count=loadTablesAndColumns(tableMap, columnMap, owner, connection);
		LOG.debug(String.format("Loaded %d tables and columns", count));

		// STEP 2: Retrieve PK's
		LOG.debug("Loading primary keys");
		count=loadPKs(tableMap, columnMap, owner, connection);
		LOG.debug(String.format("Loaded %d primary keys", count));

		// STEP 3: Retrieve FK's
		LOG.debug("Loading foreign keys");
		count=loadFKs(tableMap, columnMap, foreignKeyMap, ignoredFks, owner, connection);
		LOG.debug(String.format("Loaded %d foreign keys", count));

		// STEP 4: Mark master tables
		LOG.debug("Marking master tables");
		count=markMasterTables(tableMap, columnMap, owner, connection);
		LOG.debug(String.format("Marked %d master tables", count));

		// STEP 5: Return database structure
		LOG.debug("Finished loading database structure");
		return new DatabaseStructure(tableMap, columnMap, foreignKeyMap);
	}

	/**
	 * 
	 * @param tableMap
	 * @param columnMap
	 * @param connection
	 * @throws SQLException
	 */
	private int loadTablesAndColumns(Map<String, Table> tableMap, Map<String, Column> columnMap, String owner, Connection connection)
		throws SQLException
	{
		int rowsProcessed=0;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			st=connection.prepareStatement(SQL_GET_COLUMNS);
			st.setString(1, owner);
			rs=st.executeQuery();
			while (rs.next())
			{
				rowsProcessed++;
				String tableName=rs.getString(1).toUpperCase();
				String columnName=rs.getString(2).toUpperCase();
				String dataTypeString=rs.getString(3);
				String nullableString=rs.getString(4);
				boolean nullable=SQL_YES.equals(nullableString);
				String a=dataTypeString.replace(' ', '_');
				int indexOfParenthesis=a.indexOf('(');
				if (indexOfParenthesis > 0)
				{
					a=a.substring(0, indexOfParenthesis);
				}
				ColumnDataType b=null;
				try
				{
					b=ColumnDataType.valueOf(a);
				}
				catch (Exception e)
				{
					throw e;
				}
				Column col=new Column(columnName, b, nullable);
				columnMap.put(tableName + DatabaseStructure.SEPARATOR + col.getName(), col);
				Table newTable=tableMap.get(tableName);
				if (newTable == null)
				{
					newTable=new Table(tableName);
				}
				newTable.setDeletable(false);
				newTable.addColumn(col);
				tableMap.put(tableName, newTable);
			}
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close loading tables and columns", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on statement close loading tables and columns", e);
				}
			}
		}
		return rowsProcessed;
	}

	/**
	 * 
	 * @param tableMap
	 * @param columnMap
	 * @param owner
	 * @param connection
	 * @throws SQLException
	 */
	private int loadPKs(Map<String, Table> tableMap, Map<String, Column> columnMap, String owner, Connection connection)
		throws SQLException
	{
		int rowsProcessed=0;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			st=connection.prepareStatement(SQL_GET_PKS);
			st.setString(1, owner);
			rs=st.executeQuery();
			while (rs.next())
			{
				rowsProcessed++;
				String tableName=rs.getString(1).toUpperCase();
				String columnName=rs.getString(2).toUpperCase();
				Column pk=columnMap.get(tableName + DatabaseStructure.SEPARATOR + columnName);
				Table table=tableMap.get(tableName);
				table.addPrimaryKey(pk);
			}
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close loading pks", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on statement close loading pks", e);
				}
			}
		}
		return rowsProcessed;
	}

	/**
	 * 
	 * @param tableMap
	 * @param columnMap
	 * @param foreignKeyMap
	 * @param ignoredFks
	 * @param owner
	 * @param connection
	 * @throws SQLException
	 */
	private int loadFKs(Map<String, Table> tableMap, Map<String, Column> columnMap, Map<String, ForeignKey> foreignKeyMap, List<String> ignoredFks, String owner, Connection connection)
		throws SQLException
	{
		int rowsProcessed=0;
		PreparedStatement st=null;
		ResultSet rs=null;
		Map<String, String> ignoredFkMap=new HashMap<String, String>();
		for (String ignoredFk : ignoredFks)
		{
			ignoredFkMap.put(ignoredFk.toUpperCase(), ignoredFk);
		}
		try
		{
			st=connection.prepareStatement(SQL_GET_FKS);
			st.setString(1, owner);
			rs=st.executeQuery();

			while (rs.next())
			{
				rowsProcessed++;
				String tableNameO=rs.getString(1).toUpperCase(); // TableName Origen
				String columnNameO=rs.getString(2).toUpperCase(); // RowName Origen
				String tableNameD=rs.getString(3).toUpperCase(); // TableName Destino
				String columnNameD=rs.getString(4).toUpperCase(); // RowName Destino
				String constraintName=rs.getString(5).toUpperCase();
				boolean bidirectionalConstraint=!ignoredFkMap.containsKey(constraintName); // constraint bidireccional
				Table tableDestino=tableMap.get(tableNameD);
				Table tableOrigen=tableMap.get(tableNameO);
				Column colO=columnMap.get(tableNameO + DatabaseStructure.SEPARATOR + columnNameO);
				Column colD=columnMap.get(tableNameD + DatabaseStructure.SEPARATOR + columnNameD);
				ForeignKey fk=tableOrigen.addForeignKey(constraintName, tableDestino, colO, colD, bidirectionalConstraint);
				if (!foreignKeyMap.containsKey(constraintName))
				{
					foreignKeyMap.put(constraintName, fk);
				}
			}
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close loading fks", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on statement close loading fks", e);
				}
			}
		}
		return rowsProcessed;
	}

	/**
	 * 
	 * @param tableMap
	 * @param columnMap
	 * @param owner
	 * @param connection
	 * @throws SQLException
	 */
	private int markMasterTables(Map<String, Table> tableMap, Map<String, Column> columnMap, String owner, Connection connection)
		throws SQLException
	{
		int rowsProcessed=0;
		PreparedStatement st=null;
		ResultSet rs=null;
		try
		{
			st=connection.prepareStatement(SQL_GET_MASTER_TABLES);
			st.setString(1, owner);
			rs=st.executeQuery();
			while (rs.next())
			{
				rowsProcessed++;
				String tableName=rs.getString(1);
				Table table=tableMap.get(tableName);
				table.setMaster(true);
			}
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close marking master tables", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on statement close marking master tables", e);
				}
			}
		}
		return rowsProcessed;
	}

	/**
	 * 
	 * @param dbStructure
	 * @param entity
	 * @param sqlCondition
	 * @param dbProfileFrom
	 * @return
	 * @throws SQLException
	 */
	public List<WorkPackage> getWorkPackagesForEntity(DatabaseStructure dbStructure, GchsEntity entity, String sqlCondition, Date batchDate, Connection connection)
		throws SQLException
	{
		List<WorkPackage> workPackages=new ArrayList<WorkPackage>();
		Table table=dbStructure.getTableMap().get(entity.getTableName());
		String sql=table.getRetrieveSQLByCondition(entity.getPackageDelimiter(), sqlCondition);

		List<TuplePrimaryKeyDataDelimiter> initialRows=getTableRowsIdByCondition(table, sql, batchDate, connection);

		WorkPackage currentPackage=null;
		long packageSize=entity.getPackageSize();
		int cont=0, idWorkPackage=1;

		TuplePrimaryKeyDataDelimiter rowDelimiter=null;
		TuplePrimaryKeyDataDelimiter nextRowDelimiter=null;
		for (int i=0; i < initialRows.size(); i++)
		{
			rowDelimiter=initialRows.get(i);
			if (i + 1 < initialRows.size())
			{
				nextRowDelimiter=initialRows.get(i + 1);
			}
			if (currentPackage == null)
			{
				// Se empieza un paquete nuevo
				idWorkPackage++;
				currentPackage=new WorkPackage(idWorkPackage, rowDelimiter.getPkData().getTable().getName());
			}

			currentPackage.add(rowDelimiter.getPkData());
			cont++;
			if (cont >= packageSize && (rowDelimiter.getPakageDelimiter() == null || !nextRowDelimiter.getPakageDelimiter().equals(rowDelimiter.getPakageDelimiter())))
			{
				// Paquete completo
				workPackages.add(currentPackage);
				currentPackage=null;
				cont=0;
			}

		}
		// Si el último paquete está incompleto
		if (currentPackage != null)
		{
			workPackages.add(currentPackage);
		}

		return workPackages;

	}

	/**
	 * 
	 * @param table
	 * @param sqlCondition
	 * @param batchDate
	 * @param dbProfile
	 * @return
	 * @throws SQLException
	 */
	private List<TuplePrimaryKeyDataDelimiter> getTableRowsIdByCondition(Table table, String sqlCondition, Date batchDate, Connection connection)
		throws SQLException
	{
		PreparedStatement st=null;
		ResultSet rs=null;
		List<TuplePrimaryKeyDataDelimiter> pkList=new ArrayList<TuplePrimaryKeyDataDelimiter>();
		java.sql.Date batchSqlDate=new java.sql.Date(batchDate.getTime());
		int variableCount=new StringTokenizer(sqlCondition, "?").countTokens() - 1;
		try
		{
			st=connection.prepareStatement(sqlCondition);
			for (int i=1; i <= variableCount; i++)
			{
				st.setDate(i, batchSqlDate);
			}
			rs=st.executeQuery();
			int columnCount=rs.getMetaData().getColumnCount();
			while (rs.next())
			{
				List<ColumnData> listColData=new ArrayList<ColumnData>();
				Integer pakageDelimiter=null;
				for (Column col : table.getPrimaryKey().getColumns())
				{
					ColumnData columnData=new ColumnData(col, rs.getObject(col.getName()));
					listColData.add(columnData);
				}

				if (columnCount > table.getPrimaryKey().getColumns().size())
				{
					pakageDelimiter=rs.getInt(columnCount);
				}
				PrimaryKeyData pkData=new PrimaryKeyData(table, table.getPrimaryKey(), listColData);
				TuplePrimaryKeyDataDelimiter pKDelimiter=new TuplePrimaryKeyDataDelimiter(pkData, pakageDelimiter);
				pkList.add(pKDelimiter);
			}
			return pkList;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close getting rows by condition", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on statement close getting rows by condition", e);
				}
			}
		}
	}

	/**
	 * 
	 * @param dbStructure
	 * @param dbProfileOrig
	 * @return
	 * @throws SQLException
	 */
	public List<WorkPackage> getWorkPackagesForUpdateMasterTables(DatabaseStructure dbStructure, String dbProfile)
		throws SQLException
	{
		Connection connection=null;
		List<WorkPackage> workPackages=new ArrayList<WorkPackage>();
		try
		{
			// Get a connection to the database
			connection=Pool.getInstance().getConnection(dbProfile, this.getClass().getName());
			connection.setAutoCommit(false);

			int count=1;
			for (Table table : dbStructure.getTableMap().values())
			{
				if (table.isMaster())
				{
					workPackages.add(getWorkPackageForUpdateTable(table, count, connection));
					count++;
				}
			}

			return workPackages;

		}
		finally
		{
			// STEP 7: Free connection
			if (connection != null)
			{
				connection.rollback();
				Pool.getInstance().freeConnection(dbProfile, this.getClass().getName(), connection);
			}
		}
	}

	/**
	 * 
	 * @param table
	 * @param id
	 * @param connection
	 * @return
	 */
	private WorkPackage getWorkPackageForUpdateTable(Table table, int id, Connection connection)
		throws SQLException
	{
		Statement st=null;
		ResultSet rs=null;
		WorkPackage workPackage=new WorkPackage(id, table.getName());
		try
		{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ");

			int columnCount=0;
			for (Column col : table.getPrimaryKey().getColumns())
			{
				if (columnCount > 0)
				{
					sql.append(",");
				}
				sql.append(col.getName());
			}
			sql.append(" FROM " + table.getName());

			st=connection.createStatement();
			rs=st.executeQuery(sql.toString());
			while (rs.next())
			{
				List<ColumnData> listPkColData=new ArrayList<ColumnData>();
				for (Column col : table.getPrimaryKey().getColumns())
				{
					ColumnData colData=new ColumnData(col, rs.getObject(col.getName()));
					listPkColData.add(colData);
				}
				PrimaryKeyData pkData=new PrimaryKeyData(table, table.getPrimaryKey(), listPkColData);
				workPackage.add(pkData);
			}
			return workPackage;
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception
					LOG.error("SQL Exception on resultset close getting rows for master table update", e);
				}
			}
			if (st != null)
			{
				try
				{
					st.close();
				}
				catch (SQLException e)
				{
					// As we are closing we do not want to raise an exception

					LOG.error("SQL Exception on statement close getting rows for master table update", e);
				}
			}
		}
	}

}
