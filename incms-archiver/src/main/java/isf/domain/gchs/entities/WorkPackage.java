package isf.domain.gchs.entities;

import java.util.ArrayList;
import java.util.List;

public class WorkPackage
{

	public static final String RESULT_OK="true";

	public static final String RESULT_ERROR="false";

	private final String tableName;

	private final int id;

	private final List<PrimaryKeyData> content;

	public WorkPackage(int id, String nameString)
	{
		tableName=nameString;
		this.id=id;
		content=new ArrayList<PrimaryKeyData>();
	}

	public int getId()
	{
		return id;
	}

	public void add(PrimaryKeyData pkdata)
	{
		content.add(pkdata);
	}

	public List<PrimaryKeyData> getContent()
	{
		return content;
	}

	public String getTableName()
	{
		return tableName;
	}

}
