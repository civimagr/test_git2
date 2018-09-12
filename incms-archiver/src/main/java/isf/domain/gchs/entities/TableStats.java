package isf.domain.gchs.entities;

public class TableStats
{
	private final Table table;

	private long totalTimeInsert;

	private int totalInserts;

	private long totalTimeInsertAlreadyExists;

	private int totalInsertsAlreadyExists;

	private long totalTimeUpdates;

	private int totalUpdates;

	private long totalTimeDeletes;

	private int totalDeletes;

	private long totalTimeSelects;

	private int totalSelects;

	public TableStats(Table tab)
	{
		table=tab;
		totalTimeInsert=0;
		totalInserts=0;
		totalTimeInsertAlreadyExists=0;
		totalInsertsAlreadyExists=0;
		totalTimeUpdates=0;
		totalUpdates=0;
		totalTimeDeletes=0;
		totalDeletes=0;
		totalTimeSelects=0;
		totalSelects=0;
	}

	public long getTotalTimeInsertAlreadyExists()
	{
		return totalTimeInsertAlreadyExists;
	}

	public int getTotalInsertsAlreadyExists()
	{
		return totalInsertsAlreadyExists;
	}

	public long getTotalTimeInsert()
	{
		return totalTimeInsert;
	}

	public int getTotalInserts()
	{
		return totalInserts;
	}

	public long getTotalTimeUpdates()
	{
		return totalTimeUpdates;
	}

	public int getTotalUpdates()
	{
		return totalUpdates;
	}

	public long getTotalTimeDeletes()
	{
		return totalTimeDeletes;
	}

	public int getTotalDeletes()
	{
		return totalDeletes;
	}

	public long getTotalTimeSelects()
	{
		return totalTimeSelects;
	}

	public int getTotalSelects()
	{
		return totalSelects;
	}

	public Table getTable()
	{
		return table;
	}

	public void addTimeInsert(long time)
	{
		totalTimeInsert+=time;
		totalInserts++;
	}

	public void addTimeInsertAlreadyExists(long time)
	{
		totalTimeInsertAlreadyExists+=time;
		totalInsertsAlreadyExists++;
	}

	public void addTimeUpdate(long time)
	{
		totalTimeUpdates+=time;
		totalUpdates++;
	}

	public void addTimeDelete(long time)
	{
		totalTimeDeletes+=time;
		totalDeletes++;
	}

	public void addTimeSelect(long time, int rows)
	{
		totalTimeSelects+=time;
		totalSelects+=rows;
	}

	public long getAvgTimeInsert()
	{
		if (totalInserts > 0)
		{
			return totalTimeInsert / totalInserts;
		}
		return 0;
	}

	public long getAvgTimeInsertAlreadyExists()
	{
		if (totalInsertsAlreadyExists > 0)
		{
			return totalTimeInsertAlreadyExists / totalInsertsAlreadyExists;
		}
		return 0;
	}

	public long getAvgTimeUpdates()
	{
		if (totalUpdates > 0)
		{
			return totalTimeUpdates / totalUpdates;
		}
		return 0;
	}

	public long getAvgTimeDeletes()
	{
		if (totalDeletes > 0)
		{
			return totalTimeDeletes / totalDeletes;
		}
		return 0;
	}

	public long getAvgTimeSelects()
	{
		if (totalSelects > 0)
		{
			return totalTimeSelects / totalSelects;
		}
		return 0;
	}

	public long getAvgTime()
	{
		int total=totalInserts + totalInsertsAlreadyExists + totalUpdates + totalDeletes + totalSelects;
		if (total > 0)
		{
			return (totalTimeInsert + totalTimeInsertAlreadyExists + totalTimeUpdates + totalTimeDeletes + totalTimeSelects) / (total);
		}
		return 0;
	}
}
