package isf.domain.gchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import isf.persistence.IArguments;
import isf.persistence.gchs.GchsEntity;
import isf.persistence.gchs.GchsEntityBorderlineFk;
import isf.persistence.gchs.GchsEntityDeletableTable;
import isf.persistence.gchs.GchsEntityNullableTable;
import isf.persistence.gchs.GchsIgnoreFk;
import isf.domain.gchs.util.DatabaseStructureHelper;
import isf.domain.gchs.entities.DatabaseStructure;
import isf.domain.gchs.entities.ExecutionMode;
import isf.domain.gchs.entities.ForeignKey;
import isf.domain.gchs.entities.Table;
import isf.domain.gchs.entities.WorkPackage;
import isf.domain.gchs.exceptions.DatabaseConnectionException;

/*
 * Revisions:
 * 
 * 16/06/2016 pcal [PIEI-] Initial.
 */

/**
 * 
 * @author pcal
 * @version 2016.00
 */
public class ArchiverHelper
{

	private static final String SQL_WHERE_ID_ENTITY="ID_ENTITY = ?";

	private static final Log LOG=LogFactory.getLog(ArchiverHelper.class);

	/**
	 * 
	 * @param previousSql
	 * @param batchDate
	 * @param dbProfile
	 * @throws SQLException
	 */
	public void executeSQL(String sqlScript, Date batchDate, Connection connection)
		throws SQLException
	{
		java.sql.Date batchSqlDate=new java.sql.Date(batchDate.getTime());
		StringTokenizer tokenizer=new StringTokenizer(sqlScript, ";");

		LOG.debug("Starting to execute scripts");

		while (tokenizer.hasMoreTokens())
		{
			String sql=tokenizer.nextToken().trim();
			if (sql.length() > 0)
			{
				LOG.debug("SQL to execute: " + sql);
				int variableCount=new StringTokenizer(sql, "?").countTokens() - 1;
				try (PreparedStatement st=connection.prepareStatement(sql))
				{
					for (int i=1; i <= variableCount; i++)
					{
						st.setDate(i, batchSqlDate);
					}
					st.executeUpdate();
				}
			}
		}

		LOG.debug("Finished executing scripts");
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 * @throws DatabaseConnectionException
	 */
	public List<WorkPackage> getWorkPackagesForEntity(ExecutionMode execMode, long idEntity, String sqlCondition, Date batchDate, Connection connection)
		throws SQLException
	{
		GchsEntity entity=GchsEntity.read(connection, idEntity);

		DatabaseStructureHelper dbStructureHelper=new DatabaseStructureHelper();
		DatabaseStructure dbStructure=dbStructureHelper.loadDatabaseStructure(entity.getOwner(), getIgnoredFks(entity.getIdIgnoreFkSet(), connection), connection);

		loadEntityRelationsIntoDatabaseStructure(entity, execMode, dbStructure, connection);

		calculateVisitOrder(dbStructure, entity.getTableName());

		return dbStructureHelper.getWorkPackagesForEntity(dbStructure, entity, sqlCondition, batchDate, connection);
	}

	private void calculateVisitOrder(DatabaseStructure dbStructure, String tableName)
	{
		Table table=dbStructure.getTableMap().get(tableName);
		calculateVisitOrder(dbStructure, table, 1, new ArrayList<Table>());
		for (Table t : dbStructure.getTableMap().values())
		{
			if (t.getVisitOrder() > 0)
			{
				t.sortParentAndChilds();
			}
		}
	}

	private void calculateVisitOrder(DatabaseStructure dbStructure, Table table, int level, List<Table> previousTables)
	{
		if ((table.isDeletable() || table.isNullable()) && level > table.getVisitOrder() && !previousTables.contains(table))
		{
			table.setVisitOrder(level);
			List<Table> parentAndChilds=new ArrayList<Table>();
			List<Table> newPreviousList=new ArrayList<Table>();
			parentAndChilds.addAll(table.getParentTables());
			parentAndChilds.addAll(table.getChildTables());
			newPreviousList.addAll(previousTables);
			newPreviousList.add(table);
			for (Table child : parentAndChilds)
			{
				calculateVisitOrder(dbStructure, child, level + 1, newPreviousList);
			}
		}
	}

	/**
	 * Load entity relationship of database structures
	 * @param executionMode
	 * @param idEntity
	 * @param dbStructure
	 * @param connection
	 * @throws SQLException
	 */
	private void loadEntityRelationsIntoDatabaseStructure(GchsEntity entity, ExecutionMode executionMode, DatabaseStructure dbStructure, Connection connection)
		throws SQLException
	{
		if (!ExecutionMode.DELETE.equals(executionMode) && !ExecutionMode.MOVE.equals(executionMode))
		{
			return;
		}

		// Deletable tables
		IArguments iArguments1;
		iArguments1=GchsEntityDeletableTable.createArguments(1);
		iArguments1.add(entity.getIdEntity());

		Collection<GchsEntityDeletableTable> deletableTables=GchsEntityDeletableTable.collect(connection, SQL_WHERE_ID_ENTITY, null, iArguments1, null);

		for (GchsEntityDeletableTable deletableTable : deletableTables)
		{
			String tableName=deletableTable.getTableName().toUpperCase();
			Table table=dbStructure.getTableMap().get(tableName);
			if (table != null)
			{
				table.setDeletable(true);
			}
			else
			{
				LOG.error(String.format("Deletable table %s was ignored because it doesn't exist", tableName));
			}
		}

		// Nullable tables
		IArguments iArguments2;
		iArguments2=GchsEntityNullableTable.createArguments(1);
		iArguments2.add(entity.getIdEntity());

		Collection<GchsEntityNullableTable> nullableTables=GchsEntityNullableTable.collect(connection, SQL_WHERE_ID_ENTITY, null, iArguments2, null);

		for (GchsEntityNullableTable nullableTable : nullableTables)
		{
			String tableName=nullableTable.getTableName().toUpperCase();
			Table table=dbStructure.getTableMap().get(tableName);
			if (table != null)
			{
				table.setNullable(true);
			}
			else
			{
				LOG.error(String.format("Nullable table %s was ignored because it doesn't exist", tableName));
			}
		}

		// Borderline FK's
		IArguments iArguments3;
		iArguments3=GchsEntityBorderlineFk.createArguments(1);
		iArguments3.add(entity.getIdEntity());

		Collection<GchsEntityBorderlineFk> borderlineFks=GchsEntityBorderlineFk.collect(connection, SQL_WHERE_ID_ENTITY, null, iArguments3, null);

		for (GchsEntityBorderlineFk borderlineFk : borderlineFks)
		{
			String foreignKeyName=borderlineFk.getForeignKeyName().toUpperCase();
			ForeignKey foreignKey=dbStructure.getForeignKeyMap().get(foreignKeyName);
			if (foreignKey != null)
			{
				foreignKey.setDeleteBorderline(true);
			}
			else
			{
				LOG.error(String.format("Borderline foreign key %s was ignored because it doesn't exist", foreignKeyName));
			}
		}
	}

	/**
	 * 
	 * @param idIgnoreFkSet
	 * @param session
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	private List<String> getIgnoredFks(long idIgnoreFkSet, Connection connection)
		throws SQLException
	{
		List<String> result=new ArrayList<String>();

		IArguments iArguments;
		iArguments=GchsIgnoreFk.createArguments(1);
		iArguments.add(idIgnoreFkSet);

		Collection<GchsIgnoreFk> ignoredFks=GchsIgnoreFk.collect(connection, "ID_IGNORE_FK_SET = ?", null, iArguments, null);

		for (GchsIgnoreFk ignoredFk : ignoredFks)
		{
			result.add(ignoredFk.getForeignKeyName());
		}
		return result;
	}

}
