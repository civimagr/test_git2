package isf.domain.gchs.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import isf.domain.gchs.entities.StatsType;
import isf.domain.gchs.entities.Table;
import isf.domain.gchs.entities.TableStats;

public class ProcessStatsHelper
{
	private static final String TABLE_HEADER="Tabla									Cantidad";

	private static final String TABLE_MASK="%1$-32s %2$7d";

	private final Map<Table, TableStats> statisInfoMap;

	private static final Log LOG=LogFactory.getLog(ProcessStatsHelper.class);

	public ProcessStatsHelper()
	{
		statisInfoMap=new HashMap<Table, TableStats>();
	}

	synchronized public void informProcessEnded(Table table, StatsType type, int rows, long time)
	{
		TableStats info=statisInfoMap.get(table);
		if (info == null)
		{
			info=new TableStats(table);
			statisInfoMap.put(table, info);
		}

		switch (type)
		{
			case SELECT:
				info.addTimeSelect(time, rows);
				break;
			case INSERT:
				info.addTimeInsert(time);
				break;
			case INSERT_ALREADY_EXISTS:
				info.addTimeInsertAlreadyExists(time);
				break;
			case UPDATE:
				info.addTimeUpdate(time);
				break;
			case DELETE:
				info.addTimeDelete(time);
				break;
			default:
				LOG.error("Wrong process on process ended");
		}

	}

	public synchronized String getUpdateSummary()
	{
		StringBuilder output=new StringBuilder();
		int total=0;
		output.append("Datos actualizados:" + System.lineSeparator());
		output.append(TABLE_HEADER + System.lineSeparator());
		for (TableStats ts : statisInfoMap.values())
		{
			if (ts.getTotalUpdates() != 0)
			{
				total+=ts.getTotalUpdates();
				output.append(String.format(TABLE_MASK, ts.getTable().getName(), ts.getTotalUpdates()) + System.lineSeparator());
			}
		}
		output.append(String.format(TABLE_MASK, "Total actualizados", total) + System.lineSeparator());
		return output.toString();
	}

	public synchronized String getInsertSummary()
	{
		StringBuilder output=new StringBuilder();
		int total=0;
		output.append("Datos insertados:" + System.lineSeparator());
		output.append(TABLE_HEADER + System.lineSeparator());
		for (TableStats ts : statisInfoMap.values())
		{
			if (ts.getTotalInserts() != 0)
			{
				total+=ts.getTotalInserts();
				output.append(String.format(TABLE_MASK, ts.getTable().getName(), ts.getTotalInserts()) + System.lineSeparator());
			}
		}
		output.append(String.format(TABLE_MASK, "Total insertados", total) + System.lineSeparator());
		return output.toString();
	}

	public synchronized String getDeleteSummary()
	{
		StringBuilder output=new StringBuilder();
		int total=0;
		output.append("Datos borrados:" + System.lineSeparator());
		output.append(TABLE_HEADER + System.lineSeparator());
		for (TableStats ts : statisInfoMap.values())
		{
			if (ts.getTotalDeletes() != 0)
			{
				total+=ts.getTotalDeletes();
				output.append(String.format(TABLE_MASK, ts.getTable().getName(), ts.getTotalDeletes()) + System.lineSeparator());
			}
		}
		output.append(String.format(TABLE_MASK, "Total borrados:", total) + System.lineSeparator());
		return output.toString();
	}

}
