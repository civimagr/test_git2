package isf.domain.gchs.entities;

/*
 * Revisions:
 * 
 * 07/06/2016 pcal [PIEI-] Initial.
 */

/**
 * 
 * @author pcal
 * @version 2016.00
 */
public enum ExecutionMode {
	MOVE("EXEMOD0002", true, true, true), COPY("EXEMOD0003", true, true, false), DELETE("EXEMOD0001", false, false, true), COPY_MASTER_TABLES("EXEMOD9999", true, true, false);

	private String codDevelop;

	private boolean indInsert;

	private boolean indUpdate;

	private boolean indDelete;

	ExecutionMode(String codDevelop, boolean indInsert, boolean indUpdate, boolean indDelete)
	{
		this.codDevelop=codDevelop;
		this.indInsert=indInsert;
		this.indUpdate=indUpdate;
		this.indDelete=indDelete;
	}

	public String getCodDevelop()
	{
		return codDevelop;
	}

	public static ExecutionMode fromString(String codDevelop)
	{
		if (codDevelop != null)
		{
			for (ExecutionMode b : ExecutionMode.values())
			{
				if (codDevelop.equalsIgnoreCase(b.codDevelop))
				{
					return b;
				}
			}
		}
		return null;
	}

	public boolean isInsert()
	{
		return indInsert;
	}

	public boolean isUpdate()
	{
		return indUpdate;
	}

	public boolean isDelete()
	{
		return indDelete;
	}
}
