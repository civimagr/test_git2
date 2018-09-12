package isf.servlets.gchs;

import isf.servlets.*;
import isf.servlets.InvalidValueFormatException;
import org.w3c.dom.*;


/**
 * Implementation class for Request 'ArchiveVariant' according to DOM model.
 * 
 * <p>This file was generated automatically by tools.genex.ExchangeDocumentGenerator 10.21.02
 * <br>by ARQv10 (Indra Software Labs)
 * <br>(c) 2000-2016 ARQv10 (Indra Software Labs)
 * 
 * <p>******************** DO NOT EDIT ********************</p>
 * 
 * @author pcal
 * 
 */
@javax.annotation.Generated(value = "tools.genex.ExchangeDocumentGenerator", date="2016-06-20", comments="Generated by isfv10-genex version 10.21.02 from ARQv10 (Indra Software Labs)")
public class ArchiveVariantRequestDOMImpl extends isf.servlets.RequestDOMImp
implements
	IArchiveVariantRequest
{
	
	// ****************************************************************************
	// * Variables
	// ****************************************************************************
	/** The main object representing the document itself.  */
	private IArchiveVariantRequest.ArchiveVariant51344 mainObject;
	
	
	// ****************************************************************************
	// * Constructors
	// ****************************************************************************
	
	/**
	 * Constructor: initializes main object.
	 * @param document DOM for input.
	 */
	public ArchiveVariantRequestDOMImpl(Document document)
	{
		super(document);
		initialize();
	}
	
	
	// ****************************************************************************
	// * Methods
	// ****************************************************************************
	
	/**
	 * Overrides isf.servlets.RequestDOMImp.setDocument() for rebuild the main object.
	 * @param document DOM for input.
	 */
	public final void setDocument(Document document)
	{
		super.setDocument(document);
		initialize();
	}
	
	/**
	 * Main object's initialization.
	 */
	private void initialize()
	{
		mainObject=new IArchiveVariantRequest.ArchiveVariant51344();
		try
		{
			StructureArrangerDOMImpl arranger=new StructureArrangerDOMImpl3(mainObject, true, false);
			mainObject.writeArranger(arranger);
			arranger.fillFromNode(getDocument().getDocumentElement());
		}
		catch (InvalidValueFormatException ex)
		{
			throw new isf.servlets.ExchangeException(ex);
		}
	}
	
	/**
	 * Checks that all structures and variables defined as mandatory are present in all this structure's instances, as well for his defined structures (recursively).
	 * @exception MissingMandatoryFieldException If any mandatory variable or struct is missing.
	 * @exception InvalidValueFormatException If any field has an incorrect value format.
	 */
	public void validateMandatoryFields()
	throws
		MissingMandatoryFieldException,
		InvalidValueFormatException
	{
		((StructureArrangerDOMImpl)mainObject.readArranger()).validateTopMostStruct();
	}
	
	/**
	 * Gets the content of simple struct 'CodExecutionMode'.
	 * @return Execution mode.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public String getCodExecutionMode()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getCodExecutionMode();
	}
	
	/**
	 * Checks if exists simple structure 'CodExecutionMode'.
	 * @return true if exists simple structure 'CodExecutionMode'; false if not.
	 */
	public boolean existsCodExecutionMode()
	{
		try
		{
			return getCodExecutionMode()!=null;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
	
	/**
	 * Gets the content of simple struct 'DestinationDBProfile'.
	 * @return Destination database profile.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public String getDestinationDBProfile()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getDestinationDBProfile();
	}
	
	/**
	 * Checks if exists simple structure 'DestinationDBProfile'.
	 * @return true if exists simple structure 'DestinationDBProfile'; false if not.
	 */
	public boolean existsDestinationDBProfile()
	{
		try
		{
			return getDestinationDBProfile()!=null;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
	
	/**
	 * Gets the content of simple struct 'IdEntity'.
	 * @return Entity to achive identifier.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public Long getIdEntity()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getIdEntity();
	}
	
	/**
	 * Checks if exists simple structure 'IdEntity'.
	 * @return true if exists simple structure 'IdEntity'; false if not.
	 */
	public boolean existsIdEntity()
	{
		try
		{
			return getIdEntity()!=null;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
	
	/**
	 * Gets the content of simple struct 'Condition'.
	 * @return SQL where condition to identify initial rows.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public String getCondition()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getCondition();
	}
	
	/**
	 * Checks if exists simple structure 'Condition'.
	 * @return true if exists simple structure 'Condition'; false if not.
	 */
	public boolean existsCondition()
	{
		try
		{
			return getCondition()!=null;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
	
	/**
	 * Gets the content of simple struct 'PreExecuteScript'.
	 * @return SQL script to run before actual archiving.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public String getPreExecuteScript()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getPreExecuteScript();
	}
	
	/**
	 * Checks if exists simple structure 'PreExecuteScript'.
	 * @return true if exists simple structure 'PreExecuteScript'; false if not.
	 */
	public boolean existsPreExecuteScript()
	{
		try
		{
			return getPreExecuteScript()!=null;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
	
	/**
	 * Gets the content of simple struct 'IndDebug'.
	 * @return Indicates if debugging output is enabled.
	 * @exception isf.servlets.InvalidRequest If the value read is not valid for the struct.
	 */
	public boolean getIndDebug()
	throws isf.servlets.InvalidRequest
	{
		return mainObject.getIndDebug();
	}
	
	/**
	 * Checks if exists simple structure 'IndDebug'.
	 * @return true if exists simple structure 'IndDebug'; false if not.
	 */
	public boolean existsIndDebug()
	{
		try
		{
			getIndDebug();
			return true;
		}
		catch (isf.servlets.InvalidRequest ex)
		{
			return false;
		}
	}
}
