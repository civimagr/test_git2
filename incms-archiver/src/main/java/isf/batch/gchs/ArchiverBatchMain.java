package isf.batch.gchs;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import isf.batch.BatchGenericRetrieveAndTreatMultiThread;
import isf.batch.variants.BatchLaunchCondition;
import isf.db.Pool;
import isf.domain.gchs.ArchiverHelper;
import isf.persistence.gchs.GchsExecutionMode;
import isf.servlets.Session;
import isf.servlets.gchs.ArchiveVariantRequestDOMImpl;
import isf.servlets.gchs.IArchiveVariantRequest;
import isf.util.log.Log;
import isf.domain.gchs.util.Archiver;
import isf.domain.gchs.util.ProcessStatsHelper;
import isf.domain.gchs.util.SharedMemory;
import isf.domain.gchs.entities.ExecutionMode;
import isf.domain.gchs.entities.WorkPackage;
import isf.domain.gchs.exceptions.InconsistencyDetectedException;
import isf.domain.gchs.exceptions.LobManipulationException;
import isf.domain.gchs.exceptions.SharedMemoryConflictException;
import isf.domain.gchs.exceptions.SharedMemoryIllegalException;

/**
 * Archiver tool main batch class
 * 
 * @author pcal
 * @version 2016.00
 */

/*
 * Revisions:
 * 
 * 16/06/2016 pcal [PIEI-18364] Initial.
 */
public class ArchiverBatchMain extends BatchGenericRetrieveAndTreatMultiThread
{
	// Batch name
	private static final String PROCESS_NAME="INCMSHSS10";

	// *** Fields to store execution status ***
	// Total number of tasks
	private int taskCount;

	// Number of finished Ok tasks
	private int taskOkCount;

	// Number of aborted tasks
	private int taskErrorCount;

	// *** Fields to store variant data ***
	// Execution mode
	private ExecutionMode execMode;

	// Destination database profile
	private String destDbProfile=null;

	// Indicates if debug mode is enabled
	private boolean indDebug;

	// *** Default variant ***
	// This is the default batch launch condition, it can be overridden using a command line argument
	// It shouldn't be static, but it's the only way to make it work
	private static String codBatchLaunchCondition="INCMSHSS10";

	// *** Auxiliary classes for archiving ***
	// This class keeps track of processed rows
	private SharedMemory sharedMemory;

	// This class is used for statistics
	private ProcessStatsHelper stats;

	// *** Format masks used for business logging ***
	private static final String SUMMARY_MASK="%1$-36s %2$7d";

	private static final String DATE_MASK="dd/MM/YYYY HH:mm:ss";

	/**
	 * Constructor
	 * 
	 * @param parameter Execution date in YYYYMMDD format
	 * @throws Exception
	 */

	public ArchiverBatchMain(String[] parameter) throws Exception // NOPMD by pcal
	{
		super(parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see isf.batch.BatchGenericRetrieveAndTreatMultiThread#getProcessName()
	 */
	@Override
	protected String getProcessName()
	{
		return PROCESS_NAME;
	}

	/**
	 * This method retrieve tasks to execute.
	 * It does three things:
	 * 1) Get specific run conditions from a variant
	 * 2) Runs an optional SQL if the entity needs preprocessing
	 * 3) Creates work packages for archiving
	 */
	@Override
	protected List<?> retrieveTasks(Connection connection, Session session, Date batchDate)
		throws Exception // NOPMD by pcal
	{
		sharedMemory=new SharedMemory();
		stats=new ProcessStatsHelper();
		// logBusiness=new ArchiverBatchLog(connection, session, super.getTaskId());

		// STEP 1: Load variation from DB
		final IArchiveVariantRequest variant=new ArchiveVariantRequestDOMImpl(BatchLaunchCondition.getBatchDocument(connection, session, codBatchLaunchCondition));
		if (variant.existsDestinationDBProfile())
		{
			destDbProfile=variant.getDestinationDBProfile();
		}

		execMode=ExecutionMode.fromString(variant.getCodExecutionMode());
		indDebug=variant.getIndDebug();

		// STEP 2: If there is a pre-processing SQL script, we run it
		ArchiverHelper helper=new ArchiverHelper();
		if (variant.existsPreExecuteScript())
		{
			String preExecuteScript=variant.getPreExecuteScript();
			if (preExecuteScript != null && preExecuteScript.length() > 0)
			{
				helper.executeSQL(preExecuteScript, batchDate, connection);
				connection.commit();
			}
		}

		// STEP 3: We get the tasks (work packages) for archiving
		List<WorkPackage> result=helper.getWorkPackagesForEntity(execMode, variant.getIdEntity(), variant.getCondition(), batchDate, connection);
		taskCount=result.size();
		taskOkCount=0;
		taskErrorCount=0;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see isf.batch.BatchGenericRetrieveAndTreatMultiThread#treatTask(java.sql.Connection, isf.servlets.Session, java.util.Date, java.lang.Object)
	 */
	@Override
	protected void treatTask(Connection connection, Session session, Date dateTime, Object taskData)
		throws Exception // NOPMD by pcal
	{
		Connection destConnection=null;
		WorkPackage workPackage=(WorkPackage)taskData;

		try
		{
			if (destDbProfile != null)
			{
				destConnection=Pool.getInstance().getConnection(destDbProfile, this.getClass().getName());
				destConnection.setAutoCommit(false);
			}

			Archiver archiver=new Archiver(workPackage, sharedMemory, stats, indDebug, execMode, connection, destConnection);
			archiver.startArchiving();
			taskOkCount++;

			
			if (destConnection != null)
			{
				destConnection.commit();
			}
		}
		catch (SharedMemoryConflictException | SQLException | SharedMemoryIllegalException | InconsistencyDetectedException | LobManipulationException e)
		{
			taskErrorCount++;
			throw e;
		}
		finally
		{
			if (destConnection != null)
			{
				destConnection.rollback();
				Pool.getInstance().freeConnection(destDbProfile, this.getClass().getName(), destConnection);
			}
		}
	}

	@Override
	protected void executedAllTasks(Connection connection, Session session, java.util.Date dateTime)
		throws Exception
	{
		GchsExecutionMode executionMode=GchsExecutionMode.read(connection, execMode.getCodDevelop());

		DateFormat dateFormat=new SimpleDateFormat(DATE_MASK);
		Date date=new Date();
		String fecha=dateFormat.format(date);
		StringBuilder processResult=new StringBuilder();

		processResult.append("Fin proceso:    " + fecha + System.lineSeparator());

		processResult.append(String.format(SUMMARY_MASK, "Cantidad de paquetes:", taskCount) + System.lineSeparator());
		processResult.append(String.format(SUMMARY_MASK, "Paquetes abortados:", taskErrorCount) + System.lineSeparator());
		processResult.append(String.format(SUMMARY_MASK, "Paquetes fianlizados correctamente:", taskOkCount) + System.lineSeparator());
		processResult.append(System.lineSeparator());

		processResult.append("Modo utilizado: " + executionMode.getNameType() + System.lineSeparator());
		processResult.append(System.lineSeparator());

		if (execMode.isInsert())
		{
			processResult.append(stats.getInsertSummary());
			processResult.append(System.lineSeparator());
		}
		if (execMode.isUpdate())
		{
			processResult.append(stats.getUpdateSummary());
			processResult.append(System.lineSeparator());
		}
		if (execMode.isDelete())
		{
			processResult.append(stats.getDeleteSummary());
			processResult.append(System.lineSeparator());
		}

		Log.getInstance().info(processResult.toString());
		// logBusiness.generateLogOk(connection, processResult.toString());
	}

	/**
	 * Main process
	 * 
	 * @param args Execution date in YYYYMMDD format
	 */
	public static void main(String[] args)
		throws Exception // NOPMD by pcal
	{
		String[] parentArgs;

		// This is a workaround because just one argument (batch date) is supported by superclass
		if (args.length > 1)
		{
			parentArgs=new String[1];
			parentArgs[0]=args[0];
			// Second argument is execution condition
			codBatchLaunchCondition=args[1];
		}
		else
		{
			parentArgs=args;
		}
		new ArchiverBatchMain(parentArgs);
	}

}
