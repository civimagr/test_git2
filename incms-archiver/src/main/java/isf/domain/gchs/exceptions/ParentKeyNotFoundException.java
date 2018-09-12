package isf.domain.gchs.exceptions;

public class ParentKeyNotFoundException extends Exception
{

	private static final long serialVersionUID=7061087234835316285L;

	private final String fKName;

	public ParentKeyNotFoundException(String fkName)
	{
		fKName=fkName;
	}

	public String getFKName()
	{
		return fKName;
	}

}
