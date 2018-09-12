package isf.domain.gchs.entities;

import java.util.List;

public class StatsInfo
{

	private List<TableStats> tableStats;

	private String lastUpdate;

	public List<TableStats> getTableStats()
	{
		return tableStats;
	}

	public void setTableStats(List<TableStats> tableStats)
	{
		this.tableStats=tableStats;
	}

	public String getLastUpdate()
	{
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate)
	{
		this.lastUpdate=lastUpdate;
	}

}
