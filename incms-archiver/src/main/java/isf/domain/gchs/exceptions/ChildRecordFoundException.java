package isf.domain.gchs.exceptions;

public class ChildRecordFoundException extends Exception
{

	private static final long serialVersionUID=-3393072153011711272L;

	private final String fKName;

	public ChildRecordFoundException(String fKName)
	{
		this.fKName=fKName;
	}

	public String getFKName()
	{
		return fKName;
	}
}
