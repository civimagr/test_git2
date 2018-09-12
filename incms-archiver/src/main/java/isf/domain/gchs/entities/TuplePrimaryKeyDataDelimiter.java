package isf.domain.gchs.entities;

public class TuplePrimaryKeyDataDelimiter
{

	private final PrimaryKeyData pkData;

	private final Integer pakageDelimiter;

	public TuplePrimaryKeyDataDelimiter(PrimaryKeyData xpkData, Integer xpakageDelimiter)
	{
		pkData=xpkData;
		pakageDelimiter=xpakageDelimiter;
	}

	public Integer getPakageDelimiter()
	{
		return pakageDelimiter;
	}

	public PrimaryKeyData getPkData()
	{
		return pkData;
	}

}
