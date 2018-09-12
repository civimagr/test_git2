// //////////////////////////////////////////////////////////
// Persistence Generation for Java (10.21.01)
// ARQv10
// Class: GchsExecutionMode
// Table: GCHS_EXECUTION_MODE
// Package: isf.persistence.gchs
// Target database: Oracle 7.x, 8.x, 9.x, 10.x, 11.x
// //////////////////////////////////////////////////////////

package isf.persistence.gchs;

/**
 * Persistent class for table:GCHS_EXECUTION_MODE
 * <table border="1" cellpadding="1" cellspacing="1">
 * <caption>table columns</caption>
 * <tr><th>PK</th><th>Name</th><th>Type</th><th>Extra options</th></tr>
 * <tr><td><input disabled type="checkbox"></td><td>create_date</td><td>DATE</td><td>auto_init</td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>update_date</td><td>DATE</td><td>auto_init,auto_update</td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>update_user</td><td>STRING</td><td></td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>update_program</td><td>STRING</td><td></td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>optimist_lock</td><td>LONG</td><td></td></tr>
 * <tr><td><input disabled type="checkbox" checked></td><td>cod_develop</td><td>STRING</td><td></td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>cod_user</td><td>STRING</td><td></td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>name_type</td><td>STRING</td><td></td></tr>
 * <tr><td><input disabled type="checkbox"></td><td>name_type_xi18n</td><td>LONG</td><td></td></tr>
 * </table>
 * <pre>
 * Generation parameters:
 * 	Table specification:
 * 		name: GCHS_EXECUTION_MODE
 * 		owner: INCMS_ARCHIVE
 * 	General options:
 * 		Flag variables: bitmaps
 * 		Navigation type: never
 * 		Use String for BLOBs: true
 * 		Write LOBs with SELECT FOR UPDATE: true
 * 		Reset after insert: true
 * 		Reset after update: true
 * 		Include private cache: cache-all-records
 * 		Collection class: java.util.Collection
 * 		Vector class: java.util.Vector
 * 		When to use PreparedStatement in Search: always
 * 		Fetch size: 100
 * 		Initial cache size: 200
 * 		Maximum cache size: 10000
 * 		Fill cache at read: true
 * 		Fill cache at search: true
 * 		Fill cache at find: true
 * 		Trace stream: 
 * 		Source JDK version: 1.5
 * 		Generate ordering methods: false
 * 		How to avoid cross-writings when caching: 
 * 		Readonly cache implementation: classic
 * 		Sequences source: oracle
 * 		Class listeners: false
 * 		Data type for 'number(1)': boolean
 * 		Generate sequences at insertion: true
 * 		newIdBehaviour: do_not_generate
 * 		Cursor_sharing before search: false
 * 		Finders: 0
 * </pre>
 * @since JDK1.5

 */
public class GchsExecutionMode
implements java.io.Serializable, java.lang.Cloneable
{
	/** Serial UID */
	private static final long serialVersionUID=5894751542L;

	/** Static SQL sentence: INSERT (all columns). */
	private static final java.lang.String SQL_INSERT_ALL="INSERT /*INSERT_ALL*/ INTO GCHS_EXECUTION_MODE(CREATE_DATE,UPDATE_DATE,UPDATE_USER,UPDATE_PROGRAM,COD_DEVELOP,COD_USER,NAME_TYPE,NAME_TYPE_XI18N,OPTIMIST_LOCK) VALUES(?,?,?,?,?,?,?,?,?)";

	/** Static SQL sentence: DELETE. */
	private static final java.lang.String SQL_DELETE="DELETE /*DELETE*/ FROM GCHS_EXECUTION_MODE WHERE COD_DEVELOP=? AND OPTIMIST_LOCK=?";

	/** Static SQL sentence: UPDATE (all columns) for BATCH operation. */
	private static final java.lang.String SQL_UPDATE_BATCH="UPDATE /*UPDATE_BATCH*/ GCHS_EXECUTION_MODE SET CREATE_DATE= ? , UPDATE_DATE= ? , UPDATE_USER= ? , UPDATE_PROGRAM= ? , COD_USER= ? , NAME_TYPE= ? , NAME_TYPE_XI18N= ? , OPTIMIST_LOCK= ?  WHERE COD_DEVELOP=? AND OPTIMIST_LOCK=?";

	/** Static SQL sentence: INSERT (all columns) for BATCH operation. */
	private static final java.lang.String SQL_INSERT_BATCH="INSERT /*INSERT_BATCH*/ INTO GCHS_EXECUTION_MODE(CREATE_DATE,UPDATE_DATE,UPDATE_USER,UPDATE_PROGRAM,COD_DEVELOP,COD_USER,NAME_TYPE,NAME_TYPE_XI18N,OPTIMIST_LOCK) VALUES(?,?,?,?,?,?,?,?,?)";

	/** Static SQL sentence: SELECT. */
	private static final java.lang.String SQL_READ="SELECT /*READ*/ * FROM GCHS_EXECUTION_MODE WHERE COD_DEVELOP=?";

	/** Cache (indexed by {COD_DEVELOP}) */
	private static isf.persistence.ReadonlyCache<java.lang.String,GchsExecutionMode> pkCache=new isf.persistence.ReadonlyCache<java.lang.String,GchsExecutionMode>(200, 10000);

	/** Column 'CREATE_DATE'. */
	private java.sql.Date myCreateDate;

	/** Column 'UPDATE_DATE'. */
	private java.sql.Date myUpdateDate;

	/** Column 'UPDATE_USER'. */
	private java.lang.String myUpdateUser;

	/** Column 'UPDATE_PROGRAM'. */
	private java.lang.String myUpdateProgram;

	/** Column 'OPTIMIST_LOCK'. */
	private long myOptimistLock;

	/** Column 'COD_DEVELOP'. */
	private java.lang.String myCodDevelop;

	/** Column 'COD_USER'. */
	private java.lang.String myCodUser;

	/** Column 'NAME_TYPE'. */
	private java.lang.String myNameType;

	/** Column 'NAME_TYPE_XI18N'. */
	private long myNameTypeXi18n;

	/** Flags for nullity. */
	private int flagsNul=(int)0xffffffff;

	/** Flags for modification. */
	private int flagsMod=(int)0x0;

	/**
	 * Return a new object as a clone the current object.
	 * @return New object.
	 * @exception java.lang.CloneNotSupportedException Thrown to indicate that the clone method in class Object has been called to clone an object, but that the object's class does not implement the Cloneable interface.
	 */
	@java.lang.Override
	public GchsExecutionMode clone()
	throws
		java.lang.CloneNotSupportedException
	{
		super.clone();
		return copy();
	}

	/**
	 * Get the value of the property CreateDate.
	 * @return Value of column 'CREATE_DATE'.
	 */
	public java.sql.Date getCreateDate()
	{
		return this.myCreateDate;
	}

	/**
	 * Set the value of the property CreateDate.
	 * @param pCreateDate Value for columnSetup 'CREATE_DATE'.
	 */
	public void setCreateDate(final java.sql.Date pCreateDate)
	{
		this.myCreateDate=isf.persistence.PersistenceHelper.simpleDateWithoutTime(pCreateDate);
		this.flagsNul=(int)((this.myCreateDate==null) ? (this.flagsNul|0x1) : (this.flagsNul&0xfffffffe));
		this.flagsMod|=0x1;
	}

	/**
	 * Get the value of the property UpdateDate.
	 * @return Value of column 'UPDATE_DATE'.
	 */
	public java.sql.Date getUpdateDate()
	{
		return this.myUpdateDate;
	}

	/**
	 * Set the value of the property UpdateDate.
	 * @param pUpdateDate Value for columnSetup 'UPDATE_DATE'.
	 */
	public void setUpdateDate(final java.sql.Date pUpdateDate)
	{
		this.myUpdateDate=isf.persistence.PersistenceHelper.simpleDateWithoutTime(pUpdateDate);
		this.flagsNul=(int)((this.myUpdateDate==null) ? (this.flagsNul|0x2) : (this.flagsNul&0xfffffffd));
		this.flagsMod|=0x2;
	}

	/**
	 * Get the value of the property UpdateUser.
	 * @return Value of column 'UPDATE_USER'.
	 */
	public java.lang.String getUpdateUser()
	{
		return (null==this.myUpdateUser || "null".equals(this.myUpdateUser)) ? "" : this.myUpdateUser;
	}

	/**
	 * Set the value of the property UpdateUser.
	 * @param pUpdateUser Value for columnSetup 'UPDATE_USER'.
	 */
	public void setUpdateUser(final java.lang.String pUpdateUser)
	{
		this.myUpdateUser=pUpdateUser;
		this.flagsNul=(int)((this.myUpdateUser==null) ? (this.flagsNul|0x4) : (this.flagsNul&0xfffffffb));
		this.flagsMod|=0x4;
	}

	/**
	 * Get the value of the property UpdateProgram.
	 * @return Value of column 'UPDATE_PROGRAM'.
	 */
	public java.lang.String getUpdateProgram()
	{
		return (null==this.myUpdateProgram || "null".equals(this.myUpdateProgram)) ? "" : this.myUpdateProgram;
	}

	/**
	 * Set the value of the property UpdateProgram.
	 * @param pUpdateProgram Value for columnSetup 'UPDATE_PROGRAM'.
	 */
	public void setUpdateProgram(final java.lang.String pUpdateProgram)
	{
		this.myUpdateProgram=pUpdateProgram;
		this.flagsNul=(int)((this.myUpdateProgram==null) ? (this.flagsNul|0x8) : (this.flagsNul&0xfffffff7));
		this.flagsMod|=0x8;
	}

	/**
	 * Get the value of the property OptimistLock.
	 * @return Value of column 'OPTIMIST_LOCK'.
	 */
	public long getOptimistLock()
	{
		return this.myOptimistLock;
	}

	/**
	 * Set the value of the property OptimistLock.
	 * @param pOptimistLock Value for columnSetup 'OPTIMIST_LOCK'.
	 */
	public void setOptimistLock(final long pOptimistLock)
	{
		this.myOptimistLock=pOptimistLock;
		this.flagsNul=(int)(this.flagsNul & 0xffffffef);
		this.flagsMod|=0x10;
	}

	/**
	 * Get the value of the property CodDevelop.
	 * @return Value of column 'COD_DEVELOP'.
	 */
	public java.lang.String getCodDevelop()
	{
		return (null==this.myCodDevelop || "null".equals(this.myCodDevelop)) ? "" : this.myCodDevelop;
	}

	/**
	 * Set the value of the property CodDevelop.
	 * @param pCodDevelop Value for columnSetup 'COD_DEVELOP'.
	 */
	public void setCodDevelop(final java.lang.String pCodDevelop)
	{
		this.myCodDevelop=pCodDevelop;
		this.flagsNul=(int)((this.myCodDevelop==null) ? (this.flagsNul|0x20) : (this.flagsNul&0xffffffdf));
		this.flagsMod|=0x20;
	}

	/**
	 * Get the value of the property CodUser.
	 * @return Value of column 'COD_USER'.
	 */
	public java.lang.String getCodUser()
	{
		return (null==this.myCodUser || "null".equals(this.myCodUser)) ? "" : this.myCodUser;
	}

	/**
	 * Set the value of the property CodUser.
	 * @param pCodUser Value for columnSetup 'COD_USER'.
	 */
	public void setCodUser(final java.lang.String pCodUser)
	{
		this.myCodUser=pCodUser;
		this.flagsNul=(int)((this.myCodUser==null) ? (this.flagsNul|0x40) : (this.flagsNul&0xffffffbf));
		this.flagsMod|=0x40;
	}

	/**
	 * Get the value of the property NameType.
	 * @return Value of column 'NAME_TYPE'.
	 */
	public java.lang.String getNameType()
	{
		return (null==this.myNameType || "null".equals(this.myNameType)) ? "" : this.myNameType;
	}

	/**
	 * Set the value of the property NameType.
	 * @param pNameType Value for columnSetup 'NAME_TYPE'.
	 */
	public void setNameType(final java.lang.String pNameType)
	{
		this.myNameType=pNameType;
		this.flagsNul=(int)((this.myNameType==null) ? (this.flagsNul|0x80) : (this.flagsNul&0xffffff7f));
		this.flagsMod|=0x80;
	}

	/**
	 * Get the value of the property NameTypeXi18n.
	 * @return Value of column 'NAME_TYPE_XI18N'.
	 */
	public long getNameTypeXi18n()
	{
		return this.myNameTypeXi18n;
	}

	/**
	 * Set the value of the property NameTypeXi18n.
	 * @param pNameTypeXi18n Value for columnSetup 'NAME_TYPE_XI18N'.
	 */
	public void setNameTypeXi18n(final long pNameTypeXi18n)
	{
		this.myNameTypeXi18n=pNameTypeXi18n;
		this.flagsNul=(int)(this.flagsNul & 0xfffffeff);
		this.flagsMod|=0x100;
	}

	/**
	 * Serializes the current object as a string.
	 * @return A human-readable representation of the current object.
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "GchsExecutionMode:{CREATE_DATE="+this.myCreateDate+", UPDATE_DATE="+this.myUpdateDate+", UPDATE_USER="+(this.myUpdateUser==null ? null : (this.myUpdateUser.length()<20 ? this.myUpdateUser : this.myUpdateUser.substring(0, 20)+"..."))+", UPDATE_PROGRAM="+(this.myUpdateProgram==null ? null : (this.myUpdateProgram.length()<20 ? this.myUpdateProgram : this.myUpdateProgram.substring(0, 20)+"..."))+", OPTIMIST_LOCK="+this.myOptimistLock+", COD_DEVELOP="+(this.myCodDevelop==null ? null : (this.myCodDevelop.length()<20 ? this.myCodDevelop : this.myCodDevelop.substring(0, 20)+"..."))+", COD_USER="+(this.myCodUser==null ? null : (this.myCodUser.length()<20 ? this.myCodUser : this.myCodUser.substring(0, 20)+"..."))+", NAME_TYPE="+(this.myNameType==null ? null : (this.myNameType.length()<20 ? this.myNameType : this.myNameType.substring(0, 20)+"..."))+", NAME_TYPE_XI18N="+this.myNameTypeXi18n+"}";
	}

	/**
	 * Creates a clone object. Before inserting the new object, the PK must be re-set.
	 * @return The new object.
	 */
	public GchsExecutionMode copy()
	{
		GchsExecutionMode gchsExecutionMode=new GchsExecutionMode();
		// CREATE_DATE (DATE):
		if (isNullCreateDate())
		{
			gchsExecutionMode.setNullCreateDate();
		}
		else
		{
			gchsExecutionMode.setCreateDate(new java.sql.Date(getCreateDate().getTime()));
		}
		
		// UPDATE_DATE (DATE):
		if (isNullUpdateDate())
		{
			gchsExecutionMode.setNullUpdateDate();
		}
		else
		{
			gchsExecutionMode.setUpdateDate(new java.sql.Date(getUpdateDate().getTime()));
		}
		
		// UPDATE_USER (STRING):
		if (isNullUpdateUser())
		{
			gchsExecutionMode.setNullUpdateUser();
		}
		else
		{
			gchsExecutionMode.setUpdateUser(getUpdateUser());
		}
		
		// UPDATE_PROGRAM (STRING):
		if (isNullUpdateProgram())
		{
			gchsExecutionMode.setNullUpdateProgram();
		}
		else
		{
			gchsExecutionMode.setUpdateProgram(getUpdateProgram());
		}
		
		// OPTIMIST_LOCK (LONG):
		if (isNullOptimistLock())
		{
			gchsExecutionMode.setNullOptimistLock();
		}
		else
		{
			gchsExecutionMode.setOptimistLock(getOptimistLock());
		}
		
		// COD_DEVELOP (STRING):
		if (isNullCodDevelop())
		{
			gchsExecutionMode.setNullCodDevelop();
		}
		else
		{
			gchsExecutionMode.setCodDevelop(getCodDevelop());
		}
		
		// COD_USER (STRING):
		if (isNullCodUser())
		{
			gchsExecutionMode.setNullCodUser();
		}
		else
		{
			gchsExecutionMode.setCodUser(getCodUser());
		}
		
		// NAME_TYPE (STRING):
		if (isNullNameType())
		{
			gchsExecutionMode.setNullNameType();
		}
		else
		{
			gchsExecutionMode.setNameType(getNameType());
		}
		
		// NAME_TYPE_XI18N (LONG):
		if (isNullNameTypeXi18n())
		{
			gchsExecutionMode.setNullNameTypeXi18n();
		}
		else
		{
			gchsExecutionMode.setNameTypeXi18n(getNameTypeXi18n());
		}
		
		return gchsExecutionMode;
	}

	/**
	 * Reset the Modification flags.
	 */
	protected void resetModifiedFlags()
	{
		this.flagsMod=(int)0x0;
	}

	/**
	 * Set the Modification flags.
	 */
	protected void setModifiedFlags()
	{
		this.flagsMod=(int)0xffffffff;
	}

	/**
	 * Checks if the property myCreateDate has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedCreateDate()
	{
		return ((this.flagsMod & 0x1)!=0);
	}

	/**
	 * Checks if the last value readed for the property myCreateDate is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullCreateDate()
	{
		return ((this.flagsNul & 0x1)!=0);
	}

	/**
	 * Set the value of the property myCreateDate to null.
	 */
	public void setNullCreateDate()
	{
		this.myCreateDate=null;
		this.flagsNul|=0x1;
		this.flagsMod|=0x1;
	}

	/**
	 * Checks if the property myUpdateDate has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedUpdateDate()
	{
		return ((this.flagsMod & 0x2)!=0);
	}

	/**
	 * Checks if the last value readed for the property myUpdateDate is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullUpdateDate()
	{
		return ((this.flagsNul & 0x2)!=0);
	}

	/**
	 * Set the value of the property myUpdateDate to null.
	 */
	public void setNullUpdateDate()
	{
		this.myUpdateDate=null;
		this.flagsNul|=0x2;
		this.flagsMod|=0x2;
	}

	/**
	 * Checks if the property myUpdateUser has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedUpdateUser()
	{
		return ((this.flagsMod & 0x4)!=0);
	}

	/**
	 * Checks if the last value readed for the property myUpdateUser is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullUpdateUser()
	{
		return ((this.flagsNul & 0x4)!=0);
	}

	/**
	 * Set the value of the property myUpdateUser to null.
	 */
	public void setNullUpdateUser()
	{
		this.myUpdateUser=null;
		this.flagsNul|=0x4;
		this.flagsMod|=0x4;
	}

	/**
	 * Checks if the property myUpdateProgram has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedUpdateProgram()
	{
		return ((this.flagsMod & 0x8)!=0);
	}

	/**
	 * Checks if the last value readed for the property myUpdateProgram is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullUpdateProgram()
	{
		return ((this.flagsNul & 0x8)!=0);
	}

	/**
	 * Set the value of the property myUpdateProgram to null.
	 */
	public void setNullUpdateProgram()
	{
		this.myUpdateProgram=null;
		this.flagsNul|=0x8;
		this.flagsMod|=0x8;
	}

	/**
	 * Checks if the property myOptimistLock has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedOptimistLock()
	{
		return ((this.flagsMod & 0x10)!=0);
	}

	/**
	 * Checks if the last value readed for the property myOptimistLock is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullOptimistLock()
	{
		return ((this.flagsNul & 0x10)!=0);
	}

	/**
	 * Set the value of the property myOptimistLock to null.
	 */
	public void setNullOptimistLock()
	{
		this.myOptimistLock=0L;
		this.flagsNul|=0x10;
		this.flagsMod|=0x10;
	}

	/**
	 * Checks if the property myCodDevelop has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedCodDevelop()
	{
		return ((this.flagsMod & 0x20)!=0);
	}

	/**
	 * Checks if the last value readed for the property myCodDevelop is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullCodDevelop()
	{
		return ((this.flagsNul & 0x20)!=0);
	}

	/**
	 * Set the value of the property myCodDevelop to null.
	 */
	public void setNullCodDevelop()
	{
		this.myCodDevelop=null;
		this.flagsNul|=0x20;
		this.flagsMod|=0x20;
	}

	/**
	 * Checks if the property myCodUser has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedCodUser()
	{
		return ((this.flagsMod & 0x40)!=0);
	}

	/**
	 * Checks if the last value readed for the property myCodUser is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullCodUser()
	{
		return ((this.flagsNul & 0x40)!=0);
	}

	/**
	 * Set the value of the property myCodUser to null.
	 */
	public void setNullCodUser()
	{
		this.myCodUser=null;
		this.flagsNul|=0x40;
		this.flagsMod|=0x40;
	}

	/**
	 * Checks if the property myNameType has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedNameType()
	{
		return ((this.flagsMod & 0x80)!=0);
	}

	/**
	 * Checks if the last value readed for the property myNameType is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullNameType()
	{
		return ((this.flagsNul & 0x80)!=0);
	}

	/**
	 * Set the value of the property myNameType to null.
	 */
	public void setNullNameType()
	{
		this.myNameType=null;
		this.flagsNul|=0x80;
		this.flagsMod|=0x80;
	}

	/**
	 * Checks if the property myNameTypeXi18n has been modified since last reset (instantiation, read, insert or update).
	 * @return true only if the property has been modified.
	 */
	public boolean isModifiedNameTypeXi18n()
	{
		return ((this.flagsMod & 0x100)!=0);
	}

	/**
	 * Checks if the last value readed for the property myNameTypeXi18n is null.
	 * @return true only if the value is null.
	 */
	public boolean isNullNameTypeXi18n()
	{
		return ((this.flagsNul & 0x100)!=0);
	}

	/**
	 * Set the value of the property myNameTypeXi18n to null.
	 */
	public void setNullNameTypeXi18n()
	{
		this.myNameTypeXi18n=0L;
		this.flagsNul|=0x100;
		this.flagsMod|=0x100;
	}

	/**
	 * Creates a clone object with the PK properties empty
	 * @return The new object.
	 */
	public GchsExecutionMode copyWithoutPk()
	{
		GchsExecutionMode gchsExecutionMode=new GchsExecutionMode();
		// CREATE_DATE (DATE):
		if (isNullCreateDate())
		{
			gchsExecutionMode.setNullCreateDate();
		}
		else
		{
			gchsExecutionMode.setCreateDate(new java.sql.Date(getCreateDate().getTime()));
		}
		
		// UPDATE_DATE (DATE):
		if (isNullUpdateDate())
		{
			gchsExecutionMode.setNullUpdateDate();
		}
		else
		{
			gchsExecutionMode.setUpdateDate(new java.sql.Date(getUpdateDate().getTime()));
		}
		
		// UPDATE_USER (STRING):
		if (isNullUpdateUser())
		{
			gchsExecutionMode.setNullUpdateUser();
		}
		else
		{
			gchsExecutionMode.setUpdateUser(getUpdateUser());
		}
		
		// UPDATE_PROGRAM (STRING):
		if (isNullUpdateProgram())
		{
			gchsExecutionMode.setNullUpdateProgram();
		}
		else
		{
			gchsExecutionMode.setUpdateProgram(getUpdateProgram());
		}
		
		// OPTIMIST_LOCK (LONG):
		if (isNullOptimistLock())
		{
			gchsExecutionMode.setNullOptimistLock();
		}
		else
		{
			gchsExecutionMode.setOptimistLock(getOptimistLock());
		}
		
		// COD_USER (STRING):
		if (isNullCodUser())
		{
			gchsExecutionMode.setNullCodUser();
		}
		else
		{
			gchsExecutionMode.setCodUser(getCodUser());
		}
		
		// NAME_TYPE (STRING):
		if (isNullNameType())
		{
			gchsExecutionMode.setNullNameType();
		}
		else
		{
			gchsExecutionMode.setNameType(getNameType());
		}
		
		// NAME_TYPE_XI18N (LONG):
		if (isNullNameTypeXi18n())
		{
			gchsExecutionMode.setNullNameTypeXi18n();
		}
		else
		{
			gchsExecutionMode.setNameTypeXi18n(getNameTypeXi18n());
		}
		
		return gchsExecutionMode;
	}

	/**
	 * Set the value of the property CreateDate.
	 * @param pCreateDate Value for columnSetup 'CREATE_DATE'.
	 */
	public void setCreateDateExtended(final java.sql.Date pCreateDate)
	{
		this.myCreateDate=pCreateDate;
		this.flagsNul=(int)((this.myCreateDate==null) ? (this.flagsNul|0x1) : (this.flagsNul&0xfffffffe));
		this.flagsMod|=0x1;
	}

	/**
	 * Set the value of the property UpdateDate.
	 * @param pUpdateDate Value for columnSetup 'UPDATE_DATE'.
	 */
	public void setUpdateDateExtended(final java.sql.Date pUpdateDate)
	{
		this.myUpdateDate=pUpdateDate;
		this.flagsNul=(int)((this.myUpdateDate==null) ? (this.flagsNul|0x2) : (this.flagsNul&0xfffffffd));
		this.flagsMod|=0x2;
	}

	/**
	 * Retrieve an object in the database for the param identifier.
	 * @param connection JDBC connection.
	 * @param pCodDevelop Value for field CodDevelop.
	 * @return Read object
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static GchsExecutionMode readFromJDBC(final java.sql.Connection connection, final java.lang.String pCodDevelop)
	throws
		java.sql.SQLException
	{
		java.sql.PreparedStatement ps=connection.prepareStatement(SQL_READ);
		java.sql.ResultSet rs=null;
		int p=1;
		try
		{
			ps.setString(p, pCodDevelop);
			p++;
			rs=ps.executeQuery();
			GchsExecutionMode gchsExecutionMode;
			if (rs.next())
			{
				gchsExecutionMode=new GchsExecutionMode();
				gchsExecutionMode.loadResultSet(rs);
			}
			else
			{
				gchsExecutionMode=null;
			}
			return gchsExecutionMode;
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			ps.close();
		}
	}

	/**
	 * Loads the current ResultSet record into the current object.
	 * @param rs Source ResultSet.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public void loadResultSet(final java.sql.ResultSet rs)
	throws
		java.sql.SQLException
	{
		// CREATE_DATE
		this.myCreateDate=rs.getDate("CREATE_DATE");
		if (!rs.wasNull())
		{
			this.myCreateDate=new java.sql.Date(this.myCreateDate.getTime());
		}
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x1) : (this.flagsNul&0xfffffffe));
		
		// UPDATE_DATE
		this.myUpdateDate=rs.getDate("UPDATE_DATE");
		if (!rs.wasNull())
		{
			this.myUpdateDate=new java.sql.Date(this.myUpdateDate.getTime());
		}
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x2) : (this.flagsNul&0xfffffffd));
		
		// UPDATE_USER
		this.myUpdateUser=rs.getString("UPDATE_USER");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x4) : (this.flagsNul&0xfffffffb));
		
		// UPDATE_PROGRAM
		this.myUpdateProgram=rs.getString("UPDATE_PROGRAM");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x8) : (this.flagsNul&0xfffffff7));
		
		// OPTIMIST_LOCK
		this.myOptimistLock=rs.getLong("OPTIMIST_LOCK");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x10) : (this.flagsNul&0xffffffef));
		
		// COD_DEVELOP
		this.myCodDevelop=rs.getString("COD_DEVELOP");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x20) : (this.flagsNul&0xffffffdf));
		
		// COD_USER
		this.myCodUser=rs.getString("COD_USER");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x40) : (this.flagsNul&0xffffffbf));
		
		// NAME_TYPE
		this.myNameType=rs.getString("NAME_TYPE");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x80) : (this.flagsNul&0xffffff7f));
		
		// NAME_TYPE_XI18N
		this.myNameTypeXi18n=rs.getLong("NAME_TYPE_XI18N");
		this.flagsNul=(int)(rs.wasNull() ? (this.flagsNul|0x100) : (this.flagsNul&0xfffffeff));
		
		resetModifiedFlags();
	}

	/**
	 * Retrieve from the database a collection of objects for the specified condition (using PreparedStatement always).
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.util.Vector<GchsExecutionMode> search(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*SEARCH*/ * FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		if (order!=null && order.trim().length()>0)
		{
			sql2+=" ORDER BY "+order;
		}
		java.util.Collection<GchsExecutionMode> collection = loadSQL(connection, sql1, sql2, new java.util.Vector<GchsExecutionMode>());
		return (collection instanceof java.util.Vector) ? (java.util.Vector<GchsExecutionMode>)collection : new java.util.Vector<GchsExecutionMode>(collection);
	}

	/**
	 * Retrieve from the database a collection of objects for the specified condition (using PreparedStatement always).
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @param collection Collection of read objects
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.util.Collection<GchsExecutionMode> collect(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*SEARCH*/ * FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		if (order!=null && order.trim().length()>0)
		{
			sql2+=" ORDER BY "+order;
		}
		return loadSQL(connection, sql1, sql2, collection);
	}

	/**
	 * Retrieve from the database an iterator of objects for the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @return Read iterator (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static isf.persistence.ISQLIterator<GchsExecutionMode> searchIterator(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*ITERATOR*/ * FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		if (order!=null && order.trim().length()>0)
		{
			sql2+=" ORDER BY "+order;
		}
		final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
		java.sql.PreparedStatement st=connection.prepareStatement(sql);
		st.setFetchSize(100);
		java.sql.ResultSet rs=st.executeQuery();
		return new SQLIterator(rs);
	}

	/**
	 * Retrieve from the database an iterator of objects for the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @param arguments Arguments container.
	 * @return Read iterator (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static isf.persistence.ISQLIterator<GchsExecutionMode> searchIterator(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		try
		{
			final java.lang.String sql1="SELECT /*ITERATOR_ARGS*/ * FROM GCHS_EXECUTION_MODE";
			java.lang.String sql2="";
			if (where!=null && where.trim().length()>0)
			{
				String sqlTransformed = isf.util.gcqs.advanced.exec.SessionFilterHelper.transformSql(connection.getMetaData().getDatabaseProductName(), where);
				sql2+=" WHERE " + sqlTransformed;
			}
			if (order!=null && order.trim().length()>0)
			{
				sql2+=" ORDER BY "+order;
			}
			final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
			final java.sql.PreparedStatement ps=connection.prepareStatement(sql);
			ps.setFetchSize(100);
			if (arguments!=null)
			{
				arguments.putArgumentsInPreparedStatement(ps);
			}
			final java.sql.ResultSet rs=ps.executeQuery();
			return new SQLIterator(rs);
		}
		catch (isf.util.GcqsQuerySystemException e)
		{
				throw new isf.util.QuerySystemRuntimeException(e);
		}
	}

	/**
	 * Load a collection of records.
	 * @param connection JDBC connection.
	 * @param sql1 SQL query without WHERE nor ORDER.
	 * @param sql2 Clauses WHERE + ORDER (may be empty, but never null).
	 * @param collection Collection of read objects
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static java.util.Collection<GchsExecutionMode> loadSQLFromJDBC(final java.sql.Connection connection, final java.lang.String sql1, final java.lang.String sql2, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		if (collection==null)
		{
			collection=new java.util.ArrayList<GchsExecutionMode>();
		}
		final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
		java.sql.PreparedStatement st=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			st.setFetchSize(100);
			rs=st.executeQuery();
			GchsExecutionMode gchsExecutionMode;
			while (rs.next())
			{
				gchsExecutionMode=new GchsExecutionMode();
				gchsExecutionMode.loadResultSet(rs);
				collection.add(gchsExecutionMode);
			}
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			st.close();
		}
		return collection;
	}

	/**
	 * Load a collection of records.
	 * @param connection JDBC connection.
	 * @param sql1 SQL query without WHERE nor ORDER.
	 * @param sql2 Clauses WHERE + ORDER (may be empty, but never null).
	 * @param arguments Arguments container (may be null).
	 * @param collection Collection of read objects
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static java.util.Collection<GchsExecutionMode> loadSQL(final java.sql.Connection connection, final java.lang.String sql1, final java.lang.String sql2, final isf.persistence.IArguments arguments, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		if (collection==null)
		{
			collection=new java.util.ArrayList<GchsExecutionMode>();
		}
		final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
		if (arguments != null && arguments.containsCollections())
		{
			isf.util.gcqs.advanced.spec.QuerySpec query=new isf.util.gcqs.advanced.spec.QuerySpec("Table GCHS_EXECUTION_MODE", sql);
			if (!(arguments instanceof isf.util.gcqs.advanced.argument.ArgumentsImpl))
			{
				throw new java.lang.IllegalArgumentException("Expected isf.util.gcqs.advanced.argument.ArgumentsImpl");
			}
			query.setFetchSize(100);
			isf.util.gcqs.advanced.argument.ArgumentsImpl argumentsImpl=(isf.util.gcqs.advanced.argument.ArgumentsImpl)arguments;
			for (isf.util.gcqs.advanced.spec.ArgumentSpec argumentSpec : argumentsImpl.getArgumentSpecs())
			{
				query.addArgument(argumentSpec);
			}
			try
			{
				query.setFetchSize(100);
				isf.util.gcqs.advanced.exec.QueryResolver2 resolver=new isf.util.gcqs.advanced.exec.QueryResolver2(query);
				java.util.Locale translateLanguage=null;
				java.sql.ResultSet rs=resolver.execute(connection, argumentsImpl.getValues(), translateLanguage);
				try
				{
					resultsetToBeans(rs, collection);
				}
				finally
				{
					rs.getStatement().close();
				}
			}
			catch (isf.util.GcqsQuerySystemException e)
			{
				throw new java.sql.SQLException(e);
			}
		}
		else
		{
			java.sql.PreparedStatement st=connection.prepareStatement(sql);
			try
			{
				st.setFetchSize(100);
				if (arguments != null)
				{
					arguments.putArgumentsInPreparedStatement(st);
				}
				java.sql.ResultSet rs=st.executeQuery();
				resultsetToBeans(rs, collection);
			}
			finally
			{
				st.close();
			}
		}
		return collection;
	}

	/**
	 * Converts a ResultSet into a collection of objects.
	 * @param rs Input.
	 * @param collection Collection of read objects.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static void resultsetToBeans(final java.sql.ResultSet rs, final java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		while (rs.next())
		{
			GchsExecutionMode gchsExecutionMode=new GchsExecutionMode();
			gchsExecutionMode.loadResultSet(rs);
			collection.add(gchsExecutionMode);
		}
	}

	/**
	 * Create an arguments container.
	 * @param size Number of arguments.
	 * @return Arguments container.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static isf.persistence.IArguments createArguments(final int size)
	throws
		java.sql.SQLException
	{
		return new isf.util.gcqs.advanced.argument.ArgumentsImpl(size);
	}

	/**
	 * Retrieve from the database a collection of objects for the specified condition (always uses PreparedStatement).
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @param arguments Arguments container.
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.util.Vector<GchsExecutionMode> search(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		try
		{
			final java.lang.String sql1="SELECT /*SEARCH_ARGS*/ * FROM GCHS_EXECUTION_MODE";
			java.lang.String sql2="";
			if (where!=null && where.trim().length()>0)
			{
				String sqlTransformed = isf.util.gcqs.advanced.exec.SessionFilterHelper.transformSql(connection.getMetaData().getDatabaseProductName(), where);
				sql2+=" WHERE " + sqlTransformed;
			}
			if (order!=null && order.trim().length()>0)
			{
				sql2+=" ORDER BY "+order;
			}
			java.util.Collection<GchsExecutionMode> collection = loadSQL(connection, sql1, sql2, arguments, new java.util.Vector<GchsExecutionMode>());
			return (collection instanceof java.util.Vector) ? (java.util.Vector<GchsExecutionMode>)collection : new java.util.Vector<GchsExecutionMode>(collection);
		}
		catch (isf.util.GcqsQuerySystemException e)
		{
				throw new isf.util.QuerySystemRuntimeException(e);
		}
	}

	/**
	 * Retrieve from the database a collection of objects for the specified condition (always uses PreparedStatement).
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param order SQL ordering clauses.
	 * @param arguments Arguments container.
	 * @param collection Collection of read objects
	 * @return Collection of read objects (each one is an instance of this class).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.util.Collection<GchsExecutionMode> collect(final java.sql.Connection connection, final java.lang.String where, final java.lang.String order, final isf.persistence.IArguments arguments, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		try
		{
			final java.lang.String sql1="SELECT /*SEARCH_ARGS*/ * FROM GCHS_EXECUTION_MODE";
			java.lang.String sql2="";
			if (where!=null && where.trim().length()>0)
			{
				String sqlTransformed = isf.util.gcqs.advanced.exec.SessionFilterHelper.transformSql(connection.getMetaData().getDatabaseProductName(), where);
				sql2+=" WHERE " + sqlTransformed;
			}
			if (order!=null && order.trim().length()>0)
			{
				sql2+=" ORDER BY "+order;
			}
			return loadSQL(connection, sql1, sql2, arguments, collection);
		}
		catch (isf.util.GcqsQuerySystemException e)
		{
				throw new isf.util.QuerySystemRuntimeException(e);
		}
	}

	/**
	 * Retrieve the sumatory for an attribute of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static double sumByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*SUM*/ SUM("+columnName+") FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
		java.sql.PreparedStatement st=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			rs=st.executeQuery();
			rs.next();
			return rs.getDouble(1);
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			st.close();
		}
	}

	/**
	 * Retrieve the sumatory for an attribute of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @param arguments Arguments container.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static double sumByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*SUM_ARGS*/ SUM("+columnName+") FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		final java.lang.String sql=(sql2.length())==0 ? sql1 : sql1+sql2;
		final java.sql.PreparedStatement ps=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			if (arguments!=null)
			{
				arguments.putArgumentsInPreparedStatement(ps);
			}
			rs=ps.executeQuery();
			rs.next();
			return rs.getDouble(1);
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			ps.close();
		}
	}

	/**
	 * Sumatory of value of the property OptimistLock.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static double sumOptimistLock(final java.sql.Connection connection, final java.lang.String where)
	throws
		java.sql.SQLException
	{
		return sumByCriteria(connection, where, "OPTIMIST_LOCK");
	}

	/**
	 * Sumatory of value of the property OptimistLock.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param arguments Arguments container.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static double sumOptimistLock(final java.sql.Connection connection, final java.lang.String where, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		return sumByCriteria(connection, where, "OPTIMIST_LOCK", arguments);
	}

	/**
	 * Sumatory of value of the property NameTypeXi18n.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static double sumNameTypeXi18n(final java.sql.Connection connection, final java.lang.String where)
	throws
		java.sql.SQLException
	{
		return sumByCriteria(connection, where, "NAME_TYPE_XI18N");
	}

	/**
	 * Sumatory of value of the property NameTypeXi18n.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param arguments Arguments container.
	 * @return Summatory.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static double sumNameTypeXi18n(final java.sql.Connection connection, final java.lang.String where, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		return sumByCriteria(connection, where, "NAME_TYPE_XI18N", arguments);
	}

	/**
	 * Retrieve the number of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @return Number of objects found.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static long countByCriteria(final java.sql.Connection connection, final java.lang.String where)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*COUNT*/ COUNT(*) FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		final java.lang.String sql=(sql2.length()==0) ? sql1 : sql1+sql2;
		java.sql.PreparedStatement st=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			rs=st.executeQuery();
			rs.next();
			return rs.getLong(1);
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			st.close();
		}
	}

	/**
	 * Retrieve the number of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param arguments Arguments container.
	 * @return Number of objects found.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static long countByCriteria(final java.sql.Connection connection, final java.lang.String where, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		final java.lang.String sql1="SELECT /*COUNT_ARGS*/ COUNT(*) FROM GCHS_EXECUTION_MODE";
		java.lang.String sql2="";
		if (where!=null && where.trim().length()>0)
		{
			sql2+=" WHERE "+where;
		}
		final java.lang.String sql=(sql2.length())==0 ? sql1 : sql1+sql2;
		final java.sql.PreparedStatement ps=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			if (arguments!=null)
			{
				arguments.putArgumentsInPreparedStatement(ps);
			}
			rs=ps.executeQuery();
			rs.next();
			return rs.getLong(1);
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			ps.close();
		}
	}

	/**
	 * Calculates the maximum value of one column.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @return Maximum value (the actual class depends of the type of the column).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.lang.Object maxByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName)
	throws
		java.sql.SQLException
	{
		return functionByCriteria(connection, where, columnName, "MAX");
	}

	/**
	 * Calculates the maximum value of one column.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @param arguments Arguments container.
	 * @return Maximum value (the actual class depends of the type of the column).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.lang.Object maxByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		return functionByCriteria(connection, where, columnName, "MAX", arguments);
	}

	/**
	 * Calculates the minumum value of one column.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @return Minimum value (the actual class depends of the type of the column)
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.lang.Object minByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName)
	throws
		java.sql.SQLException
	{
		return functionByCriteria(connection, where, columnName, "MIN");
	}

	/**
	 * Calculates the minumum value of one column.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @param arguments Arguments container.
	 * @return Minimum value (the actual class depends of the type of the column)
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.lang.Object minByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		return functionByCriteria(connection, where, columnName, "MIN", arguments);
	}

	/**
	 * Retrieve the maximum or minimum value in a column of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @param function Function ("MAX" or "MIN").
	 * @return Maximum or minimum result (java.lang.Double for numeric types; java.sql.Date for date/timestamp types; java.lang.String for any text type).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static java.lang.Object functionByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName, final java.lang.String function)
	throws
		java.sql.SQLException
	{
		java.lang.String sql="SELECT /*FUNCTION*/ "+function+"("+columnName+") FROM GCHS_EXECUTION_MODE";
		if (where!=null && where.trim().length()>0)
		{
			sql+=" WHERE "+where;
		}
		final java.sql.Statement st=connection.createStatement();
		java.sql.ResultSet rs=null;
		try
		{
			rs=st.executeQuery(sql);
			rs.next();
			java.lang.Object result=rs.getObject(1);
			// Mapping of Oracle JDBC classes to ARQv10 classes.
			if (result instanceof java.math.BigDecimal)
			{
				result=new java.lang.Double(((java.math.BigDecimal)result).doubleValue());
			}
			else if (result instanceof java.sql.Timestamp)
			{
				result=new java.sql.Date(((java.sql.Timestamp)result).getTime());
			}
			return result;
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			st.close();
		}
	}

	/**
	 * Retrieve the maximum or minimum value in a column of objects that meet the specified condition.
	 * @param connection JDBC connection.
	 * @param where SQL condition.
	 * @param columnName Column name.
	 * @param function Function ("MAX" or "MIN").
	 * @param arguments Arguments container.
	 * @return Maximum or minimum result (java.lang.Double for numeric types; java.sql.Date for date/timestamp types; java.lang.String for any text type).
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static java.lang.Object functionByCriteria(final java.sql.Connection connection, final java.lang.String where, final java.lang.String columnName, final java.lang.String function, final isf.persistence.IArguments arguments)
	throws
		java.sql.SQLException
	{
		java.lang.String sql="SELECT /*FUNCTION_ARGS*/ "+function+"("+columnName+") FROM GCHS_EXECUTION_MODE";
		if (where!=null && where.trim().length()>0)
		{
			sql+=" WHERE "+where;
		}
		final java.sql.PreparedStatement ps=connection.prepareStatement(sql);
		java.sql.ResultSet rs=null;
		try
		{
			if (arguments!=null)
			{
				arguments.putArgumentsInPreparedStatement(ps);
			}
			rs=ps.executeQuery();
			rs.next();
			java.lang.Object result=rs.getObject(1);
			// Mapping of Oracle JDBC classes to ARQv10 classes.
			if (result instanceof java.math.BigDecimal)
			{
				result=new java.lang.Double(((java.math.BigDecimal)result).doubleValue());
			}
			else if (result instanceof java.sql.Timestamp)
			{
				result=new java.sql.Date(((java.sql.Timestamp)result).getTime());
			}
			return result;
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			ps.close();
		}
	}

	/**
	 * Delete the current object from the database.
	 * @param connection JDBC connection.
	 * @return Always true.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.GcgcCorruptedRegisterException If another external thread has already updated/deleted the current record.
	 */
	public boolean delete(final java.sql.Connection connection)
	throws
		java.sql.SQLException,
		isf.persistence.GcgcCorruptedRegisterException
	{
		if ("true".equals(java.lang.System.getProperty("isf.persistence.overrideOptimistLock")))
		{
			GchsExecutionMode gchsExecutionMode=read(connection, getCodDevelop());
			if (gchsExecutionMode != null)
			{
				setOptimistLock(gchsExecutionMode.getOptimistLock());
			}
		}
		boolean canBeDeleted=true;
		java.sql.PreparedStatement ps=connection.prepareStatement(SQL_DELETE);
		int p=1;
		try
		{
			// Columnas PK (dentro del WHERE):
			// WHERE: COD_DEVELOP (STRING):
			ps.setString(p, myCodDevelop);
			p++;
			
			// WHERE: OPTIMIST_LOCK (LONG):
			ps.setLong(p, this.myOptimistLock);
			p++;
			
			
			if (ps.executeUpdate()==0)
			{
				throw new isf.persistence.GcgcCorruptedRegisterException("No rows deleted in table GCHS_EXECUTION_MODE with PK {"+"COD_DEVELOP="+this.myCodDevelop+",OPTIMIST_LOCK="+this.myOptimistLock+"}");
			}
		}
		finally
		{
			ps.close();
		}
		setModifiedFlags();
		return canBeDeleted;
	}

	/**
	 * Update the current object in the database.
	 * @param connection JDBC connection.
	 * @return Always true.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.GcgcCorruptedRegisterException If another external thread has already updated/deleted the current record.
	 */
	public boolean update(final java.sql.Connection connection)
	throws
		java.sql.SQLException,
		isf.persistence.GcgcCorruptedRegisterException
	{
		long currentTimeMillis=java.lang.System.currentTimeMillis();
		java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
		if ("true".equals(java.lang.System.getProperty("isf.persistence.overrideOptimistLock")))
		{
			GchsExecutionMode gchsExecutionMode=read(connection, getCodDevelop());
			if (gchsExecutionMode != null)
			{
				setOptimistLock(gchsExecutionMode.getOptimistLock());
			}
		}
		boolean canBeUpdated=true;
		java.lang.StringBuilder set=new java.lang.StringBuilder(148);
		char separator=' ';
		boolean hasUpdatableColumns=false;
		// Columnas de datos:
		if (((this.flagsMod & 0x1)!=0))
		{
			// SQL: CREATE_DATE (DATE):
			set.append(separator).append("CREATE_DATE=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		if (((this.flagsMod & 0x4)!=0))
		{
			// SQL: UPDATE_USER (STRING):
			set.append(separator).append("UPDATE_USER=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		if (((this.flagsMod & 0x8)!=0))
		{
			// SQL: UPDATE_PROGRAM (STRING):
			set.append(separator).append("UPDATE_PROGRAM=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		if (((this.flagsMod & 0x40)!=0))
		{
			// SQL: COD_USER (STRING):
			set.append(separator).append("COD_USER=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		if (((this.flagsMod & 0x80)!=0))
		{
			// SQL: NAME_TYPE (STRING):
			set.append(separator).append("NAME_TYPE=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		if (((this.flagsMod & 0x100)!=0))
		{
			// SQL: NAME_TYPE_XI18N (LONG):
			set.append(separator).append("NAME_TYPE_XI18N=?");
			separator=',';
			hasUpdatableColumns=true;
		}
		
		if (hasUpdatableColumns)
		{
			// HAY columnas para actualizar.
			
			// auto-update columns:
			if (!((this.flagsMod & 0x2)!=0))
			{
				this.setUpdateDate(sqlDateCurrentTimeMillis);
			}
			if (((this.flagsMod & 0x2)!=0))
			{
				// SQL: UPDATE_DATE (DATE):
				set.append(separator).append("UPDATE_DATE=?");
				separator=',';
			}
			
			// SQL: OPTIMIST_LOCK (LONG):
			set.append(separator).append("OPTIMIST_LOCK=?");
			
			java.lang.StringBuilder sentence=new java.lang.StringBuilder("UPDATE /*UPDATE*/ GCHS_EXECUTION_MODE SET ".length()+set.length()+" WHERE ".length()+"COD_DEVELOP=? AND OPTIMIST_LOCK=?".length());
			sentence.append("UPDATE /*UPDATE*/ GCHS_EXECUTION_MODE SET ").append(set).append(" WHERE ").append("COD_DEVELOP=? AND OPTIMIST_LOCK=?");
			java.lang.String sql=sentence.toString();
			java.sql.PreparedStatement ps=connection.prepareStatement(sql);
			int p=1;
			try
			{
				// Columnas de datos:
				// SET: CREATE_DATE (DATE):
				if (((this.flagsMod & 0x1)!=0))
				{
					if (isNullCreateDate())
					{
						ps.setNull(p, java.sql.Types.DATE);
					}
					else
					{
						ps.setTimestamp(p, new java.sql.Timestamp(this.myCreateDate.getTime()));
					}
					p++;
				}
				
				// SET: UPDATE_USER (STRING):
				if (((this.flagsMod & 0x4)!=0))
				{
					if (isNullUpdateUser())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, this.myUpdateUser);
					}
					p++;
				}
				
				// SET: UPDATE_PROGRAM (STRING):
				if (((this.flagsMod & 0x8)!=0))
				{
					if (isNullUpdateProgram())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, this.myUpdateProgram);
					}
					p++;
				}
				
				// SET: COD_USER (STRING):
				if (((this.flagsMod & 0x40)!=0))
				{
					if (isNullCodUser())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, this.myCodUser);
					}
					p++;
				}
				
				// SET: NAME_TYPE (STRING):
				if (((this.flagsMod & 0x80)!=0))
				{
					if (isNullNameType())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, this.myNameType);
					}
					p++;
				}
				
				// SET: NAME_TYPE_XI18N (LONG):
				if (((this.flagsMod & 0x100)!=0))
				{
					if (isNullNameTypeXi18n())
					{
						ps.setNull(p, java.sql.Types.NUMERIC);
					}
					else
					{
						ps.setLong(p, this.myNameTypeXi18n);
					}
					p++;
				}
				
				// SET: UPDATE_DATE (DATE):
				if (((this.flagsMod & 0x2)!=0))
				{
					if (isNullUpdateDate())
					{
						ps.setNull(p, java.sql.Types.DATE);
					}
					else
					{
						ps.setTimestamp(p, new java.sql.Timestamp(this.myUpdateDate.getTime()));
					}
					p++;
				}
				
				
				// Columna OPTIMIST_LOCK (dentro del SET):
				long pOptimistLock=(this.myOptimistLock < 99) ? this.myOptimistLock + 1 : 1;
				ps.setLong(p, pOptimistLock);
				p++;
				
				// Columnas PK:
				// WHERE: COD_DEVELOP (STRING):
				ps.setString(p, myCodDevelop);
				p++;
				
				// WHERE: OPTIMIST_LOCK (LONG):
				ps.setLong(p, this.myOptimistLock);
				p++;
				
				if (ps.executeUpdate()==0)
				{
					throw new isf.persistence.GcgcCorruptedRegisterException("No rows updated in table GCHS_EXECUTION_MODE with PK {"+"COD_DEVELOP="+this.myCodDevelop+",OPTIMIST_LOCK="+this.myOptimistLock+"}");
				}
				this.myOptimistLock=pOptimistLock;
			}
			finally
			{
				ps.close();
			}
			resetModifiedFlags();
		}
		return canBeUpdated;
	}

	/**
	 * Insert the current object in the database.
	 * @param connection JDBC connection.
	 * @return Always true.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public boolean insert(final java.sql.Connection connection)
	throws
		java.sql.SQLException
	{
		boolean canBeInserted=true;
		long currentTimeMillis=java.lang.System.currentTimeMillis();
		java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
		if (!((this.flagsMod & 0x1)!=0))
		{
			this.setCreateDate(sqlDateCurrentTimeMillis);
		}
		if (!((this.flagsMod & 0x2)!=0))
		{
			this.setUpdateDate(sqlDateCurrentTimeMillis);
		}
		java.util.Collection<java.lang.String> sequencesExpected=new java.util.ArrayList<java.lang.String>(1);
		java.lang.StringBuilder columns=new java.lang.StringBuilder(130);
		java.lang.StringBuilder values=new java.lang.StringBuilder(27);
		char separator=' ';
		// SQL: CREATE_DATE (DATE):
		if (((this.flagsMod & 0x1)!=0))
		{
			columns.append(separator).append("CREATE_DATE");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: UPDATE_DATE (DATE):
		if (((this.flagsMod & 0x2)!=0))
		{
			columns.append(separator).append("UPDATE_DATE");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: UPDATE_USER (STRING):
		if (((this.flagsMod & 0x4)!=0))
		{
			columns.append(separator).append("UPDATE_USER");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: UPDATE_PROGRAM (STRING):
		if (((this.flagsMod & 0x8)!=0))
		{
			columns.append(separator).append("UPDATE_PROGRAM");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: COD_DEVELOP (STRING):
		if (((this.flagsMod & 0x20)!=0))
		{
			columns.append(separator).append("COD_DEVELOP");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: COD_USER (STRING):
		if (((this.flagsMod & 0x40)!=0))
		{
			columns.append(separator).append("COD_USER");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: NAME_TYPE (STRING):
		if (((this.flagsMod & 0x80)!=0))
		{
			columns.append(separator).append("NAME_TYPE");
			values.append(separator).append('?');
			separator=',';
		}
		
		// SQL: NAME_TYPE_XI18N (LONG):
		if (((this.flagsMod & 0x100)!=0))
		{
			columns.append(separator).append("NAME_TYPE_XI18N");
			values.append(separator).append('?');
			separator=',';
		}
		
		columns.append(separator).append("OPTIMIST_LOCK");
		values.append(separator).append('?');
		separator=',';
		java.lang.StringBuilder sentence=new java.lang.StringBuilder(54+columns.length()+values.length());
		sentence.append("INSERT /*INSERT*/ INTO GCHS_EXECUTION_MODE(").append(columns).append(") VALUES (").append(values).append(')');
		java.lang.String sql=sentence.toString();
		java.sql.PreparedStatement ps=(sequencesExpected.size()>0)
			? connection.prepareStatement(sql, (java.lang.String[])sequencesExpected.toArray(new java.lang.String[sequencesExpected.size()]))
			: connection.prepareStatement(sql)
		;
		int p=1;
		try
		{
			// SET: CREATE_DATE (DATE):
			if (((this.flagsMod & 0x1)!=0))
			{
				if (isNullCreateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(this.myCreateDate.getTime()));
				}
				p++;
			}
			// SET: UPDATE_DATE (DATE):
			if (((this.flagsMod & 0x2)!=0))
			{
				if (isNullUpdateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(this.myUpdateDate.getTime()));
				}
				p++;
			}
			// SET: UPDATE_USER (STRING):
			if (((this.flagsMod & 0x4)!=0))
			{
				if (isNullUpdateUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myUpdateUser);
				}
				p++;
			}
			// SET: UPDATE_PROGRAM (STRING):
			if (((this.flagsMod & 0x8)!=0))
			{
				if (isNullUpdateProgram())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myUpdateProgram);
				}
				p++;
			}
			// SET: COD_DEVELOP (STRING):
			if (((this.flagsMod & 0x20)!=0))
			{
				if (isNullCodDevelop())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myCodDevelop);
				}
				p++;
			}
			// SET: COD_USER (STRING):
			if (((this.flagsMod & 0x40)!=0))
			{
				if (isNullCodUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myCodUser);
				}
				p++;
			}
			// SET: NAME_TYPE (STRING):
			if (((this.flagsMod & 0x80)!=0))
			{
				if (isNullNameType())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myNameType);
				}
				p++;
			}
			// SET: NAME_TYPE_XI18N (LONG):
			if (((this.flagsMod & 0x100)!=0))
			{
				if (isNullNameTypeXi18n())
				{
					ps.setNull(p, java.sql.Types.NUMERIC);
				}
				else
				{
					ps.setLong(p, this.myNameTypeXi18n);
				}
				p++;
			}
			// SQL: OPTIMIST_LOCK (LONG):
			ps.setLong(p, 1);
			p++;
			setOptimistLock(1);
			if (ps.executeUpdate()==1)
			{
				if (sequencesExpected.size()>0)
				{
					// Read the new sequence values:
					java.sql.ResultSet rsSequences=ps.getGeneratedKeys();
					rsSequences.close();
				}
			}
		}
		finally
		{
			ps.close();
		}
		resetModifiedFlags();
		return canBeInserted;
	}

	/**
	 * Insert the current object in the database, with ALL of its fields (unset fields are filled with NULL values). Use this method preferrably instead of <code>insert</code>, but only if no default values are defined for any column.
	 * @param connection JDBC connection.
	 * @return Always true.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public boolean insertAll(final java.sql.Connection connection)
	throws
		java.sql.SQLException
	{
		boolean canBeInserted=true;
		long currentTimeMillis=java.lang.System.currentTimeMillis();
		java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
		if (!((this.flagsMod & 0x1)!=0))
		{
			this.setCreateDate(sqlDateCurrentTimeMillis);
		}
		if (!((this.flagsMod & 0x2)!=0))
		{
			this.setUpdateDate(sqlDateCurrentTimeMillis);
		}
		{
			java.lang.String[] sequencesExpected=new java.lang.String[] { };
			final java.sql.PreparedStatement ps=connection.prepareStatement(SQL_INSERT_ALL, sequencesExpected);
			int p=1;
			try
			{
				// SQL: CREATE_DATE (DATE):
				if (isNullCreateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(this.myCreateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_DATE (DATE):
				if (isNullUpdateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(this.myUpdateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_USER (STRING):
				if (isNullUpdateUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myUpdateUser);
				}
				p++;
				// SQL: UPDATE_PROGRAM (STRING):
				if (isNullUpdateProgram())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myUpdateProgram);
				}
				p++;
				// SQL: COD_DEVELOP (STRING):
				if (isNullCodDevelop())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myCodDevelop);
				}
				p++;
				// SQL: COD_USER (STRING):
				if (isNullCodUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myCodUser);
				}
				p++;
				// SQL: NAME_TYPE (STRING):
				if (isNullNameType())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, this.myNameType);
				}
				p++;
				// SQL: NAME_TYPE_XI18N (LONG):
				if (isNullNameTypeXi18n())
				{
					ps.setNull(p, java.sql.Types.NUMERIC);
				}
				else
				{
					ps.setLong(p, this.myNameTypeXi18n);
				}
				p++;
				// SQL: OPTIMIST_LOCK (LONG):
				ps.setLong(p, 1);
				setOptimistLock(1);
				if (ps.executeUpdate()==1)
				{
					// Read the new sequence values:
					java.sql.ResultSet rsSequences=ps.getGeneratedKeys();
					rsSequences.close();
				}
			}
			finally
			{
				ps.close();
			}
			resetModifiedFlags();
		}
		return canBeInserted;
	}

	/**
	 * Insert (as a batch) a collection of objects in the database, with ALL of its fields (unset fields are filled with NULL values). Use this method preferrably instead of repeating <code>insert</code>, but only if no default values are defined for any column.
	 * @param connection JDBC connection.
	 * @param collection Collection of GchsExecutionMode objects.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.NotAllRecordsSuccesfullyBatchedException If not all objects were succesfully inserted.
	 */
	public static void insertBatch(final java.sql.Connection connection, final java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException,
		isf.persistence.NotAllRecordsSuccesfullyBatchedException
	{
		if ("true".equalsIgnoreCase(System.getProperty("optimize.batch")))
		{
			optimizedInsertBatch(connection, collection);
		}
		else
		{
			java.sql.PreparedStatement ps=connection.prepareStatement(SQL_INSERT_BATCH);
			try
			{
				for (java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
				{
					GchsExecutionMode gchsExecutionMode=iterator.next();
					long currentTimeMillis=java.lang.System.currentTimeMillis();
					java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
					if (!((gchsExecutionMode.flagsMod & 0x1)!=0))
					{
						gchsExecutionMode.setCreateDate(sqlDateCurrentTimeMillis);
					}
					if (!((gchsExecutionMode.flagsMod & 0x2)!=0))
					{
						gchsExecutionMode.setUpdateDate(sqlDateCurrentTimeMillis);
					}
					int p=1;
					// SQL: CREATE_DATE (DATE):
					if (gchsExecutionMode.isNullCreateDate())
					{
						ps.setNull(p, java.sql.Types.DATE);
					}
					else
					{
						ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myCreateDate.getTime()));
					}
					p++;
					// SQL: UPDATE_DATE (DATE):
					if (gchsExecutionMode.isNullUpdateDate())
					{
						ps.setNull(p, java.sql.Types.DATE);
					}
					else
					{
						ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myUpdateDate.getTime()));
					}
					p++;
					// SQL: UPDATE_USER (STRING):
					if (gchsExecutionMode.isNullUpdateUser())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, gchsExecutionMode.myUpdateUser);
					}
					p++;
					// SQL: UPDATE_PROGRAM (STRING):
					if (gchsExecutionMode.isNullUpdateProgram())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, gchsExecutionMode.myUpdateProgram);
					}
					p++;
					// SQL: COD_DEVELOP (STRING):
					if (gchsExecutionMode.isNullCodDevelop())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, gchsExecutionMode.myCodDevelop);
					}
					p++;
					// SQL: COD_USER (STRING):
					if (gchsExecutionMode.isNullCodUser())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, gchsExecutionMode.myCodUser);
					}
					p++;
					// SQL: NAME_TYPE (STRING):
					if (gchsExecutionMode.isNullNameType())
					{
						ps.setNull(p, java.sql.Types.VARCHAR);
					}
					else
					{
						ps.setString(p, gchsExecutionMode.myNameType);
					}
					p++;
					// SQL: NAME_TYPE_XI18N (LONG):
					if (gchsExecutionMode.isNullNameTypeXi18n())
					{
						ps.setNull(p, java.sql.Types.NUMERIC);
					}
					else
					{
						ps.setLong(p, gchsExecutionMode.myNameTypeXi18n);
					}
					p++;
					// SQL: OPTIMIST_LOCK (LONG):
					ps.setLong(p, 1);
					gchsExecutionMode.setOptimistLock(1);
					ps.addBatch();
				}
				ps.executeBatch();
				if (ps.getUpdateCount()!=collection.size())
				{
					throw new isf.persistence.NotAllRecordsSuccesfullyBatchedException("GCHS_EXECUTION_MODE", collection.size(), ps.getUpdateCount(), collection);
				}
				else
				{
					for (final java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
					{
						final GchsExecutionMode gchsExecutionMode=iterator.next();
						gchsExecutionMode.resetModifiedFlags();
					}
				}
			}
			finally
			{
				ps.close();
			}
		}
	}

	/**
	 * Update (as a batch) a collection of objects in the database, with ALL of its fields (unset fields are filled with NULL values). Use this method preferrably instead of repeating <code>update</code>, but only if no default values are defined for any column.
	 * @param connection JDBC connection.
	 * @param collection Collection of GchsExecutionMode objects.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.NotAllRecordsSuccesfullyBatchedException If not all objects were succesfully inserted.
	 */
	public static void updateBatch(final java.sql.Connection connection, final java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException,
		isf.persistence.NotAllRecordsSuccesfullyBatchedException
	{
		java.sql.PreparedStatement ps=connection.prepareStatement(SQL_UPDATE_BATCH);
		try
		{
			for (java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
			{
				GchsExecutionMode gchsExecutionMode=iterator.next();
				long currentTimeMillis=java.lang.System.currentTimeMillis();
				java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
				if (!((gchsExecutionMode.flagsMod & 0x2)!=0))
				{
					gchsExecutionMode.setUpdateDate(sqlDateCurrentTimeMillis);
				}
				int p=1;
				// SQL: CREATE_DATE (DATE):
				if (gchsExecutionMode.isNullCreateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myCreateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_DATE (DATE):
				if (gchsExecutionMode.isNullUpdateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myUpdateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_USER (STRING):
				if (gchsExecutionMode.isNullUpdateUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myUpdateUser);
				}
				p++;
				// SQL: UPDATE_PROGRAM (STRING):
				if (gchsExecutionMode.isNullUpdateProgram())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myUpdateProgram);
				}
				p++;
				// SQL: COD_USER (STRING):
				if (gchsExecutionMode.isNullCodUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myCodUser);
				}
				p++;
				// SQL: NAME_TYPE (STRING):
				if (gchsExecutionMode.isNullNameType())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myNameType);
				}
				p++;
				// SQL: NAME_TYPE_XI18N (LONG):
				if (gchsExecutionMode.isNullNameTypeXi18n())
				{
					ps.setNull(p, java.sql.Types.NUMERIC);
				}
				else
				{
					ps.setLong(p, gchsExecutionMode.myNameTypeXi18n);
				}
				p++;
				// Columna OPTIMIST_LOCK (dentro del SET):
				long pOptimistLock=(gchsExecutionMode.myOptimistLock < 99) ? gchsExecutionMode.myOptimistLock + 1 : 1;
				ps.setLong(p, pOptimistLock);
				p++;
				
				// Columnas PK:
				// WHERE: COD_DEVELOP (STRING):
				ps.setString(p, gchsExecutionMode.myCodDevelop);
				p++;
				
				// WHERE: OPTIMIST_LOCK (LONG):
				ps.setLong(p, gchsExecutionMode.myOptimistLock);
				p++;
				
				ps.addBatch();
			}
			int [] recordsUpdated = ps.executeBatch();
			
			int updateCount = 0;
			
			java.util.Collection<GchsExecutionMode> recordsFailed = new java.util.ArrayList<GchsExecutionMode>();
			final java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();
			for (int i = 0; i < recordsUpdated.length && iterator.hasNext(); i++)
			{
				final GchsExecutionMode gchsExecutionMode=iterator.next();
				if (recordsUpdated[i]==1 || recordsUpdated[i]==java.sql.PreparedStatement.SUCCESS_NO_INFO)
				{
					long pOptimistLock=(gchsExecutionMode.myOptimistLock < 99) ? gchsExecutionMode.myOptimistLock + 1 : 1;
					gchsExecutionMode.myOptimistLock=pOptimistLock;
					gchsExecutionMode.resetModifiedFlags();
					updateCount++;
					
				}
				else
				{
					recordsFailed.add(gchsExecutionMode);
				}
			}
			
			if (!recordsFailed.isEmpty())
			{
				isf.persistence.NotAllRecordsSuccesfullyBatchedException notAllRecordsSuccesfullyBatchedEx = new isf.persistence.NotAllRecordsSuccesfullyBatchedException("GCHS_EXECUTION_MODE", collection.size(), updateCount, collection);
				notAllRecordsSuccesfullyBatchedEx.setRecordsFailed(recordsFailed);
				throw notAllRecordsSuccesfullyBatchedEx;
			}
		}
		finally
		{
			ps.close();
		}
	}

	/**
	 * Delete (as a batch) a collection of objects in the database.
	 * @param connection JDBC connection.
	 * @param collection Collection of GchsExecutionMode objects.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.NotAllRecordsSuccesfullyBatchedException If not all objects were succesfully deleteed.
	 */
	public static void deleteBatch(final java.sql.Connection connection, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException,
		isf.persistence.NotAllRecordsSuccesfullyBatchedException
	{
		java.sql.PreparedStatement ps=connection.prepareStatement(SQL_DELETE);
		try
		{
			for (java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
			{
				GchsExecutionMode gchsExecutionMode=iterator.next();
				int p=1;
				// Columnas PK (dentro del WHERE):
				
				// WHERE: COD_DEVELOP (STRING):
				ps.setString(p, gchsExecutionMode.myCodDevelop);
				p++;
				
				// WHERE: OPTIMIST_LOCK (LONG):
				ps.setLong(p, gchsExecutionMode.myOptimistLock);
				p++;
				
				ps.addBatch();
			}
			ps.executeBatch();
			if (ps.getUpdateCount()!=collection.size())
			{
				throw new isf.persistence.NotAllRecordsSuccesfullyBatchedException("GCHS_EXECUTION_MODE", collection.size(), ps.getUpdateCount(), collection);
			}
		}
		finally
		{
			ps.close();
		}
	}

	/**
	 * Inserts in batch mode with automatic generation of sequences.
	 * @param connection JDBC connection.
	 * @param collection Collection of GchsExecutionMode objects.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 * @exception isf.persistence.NotAllRecordsSuccesfullyBatchedException If not all objects were succesfully inserted.
	 */
	private static void optimizedInsertBatch(final java.sql.Connection connection, final java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException,
		isf.persistence.NotAllRecordsSuccesfullyBatchedException
	{
		java.sql.PreparedStatement ps=connection.prepareStatement(SQL_INSERT_BATCH);
		try
		{
			for (java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
			{
				GchsExecutionMode gchsExecutionMode=iterator.next();
				long currentTimeMillis=java.lang.System.currentTimeMillis();
				java.sql.Date sqlDateCurrentTimeMillis=new java.sql.Date(currentTimeMillis);
				if (!((gchsExecutionMode.flagsMod & 0x1)!=0))
				{
					gchsExecutionMode.setCreateDate(sqlDateCurrentTimeMillis);
				}
				if (!((gchsExecutionMode.flagsMod & 0x2)!=0))
				{
					gchsExecutionMode.setUpdateDate(sqlDateCurrentTimeMillis);
				}
				int p=1;
				// SQL: CREATE_DATE (DATE):
				if (gchsExecutionMode.isNullCreateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myCreateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_DATE (DATE):
				if (gchsExecutionMode.isNullUpdateDate())
				{
					ps.setNull(p, java.sql.Types.DATE);
				}
				else
				{
					ps.setTimestamp(p, new java.sql.Timestamp(gchsExecutionMode.myUpdateDate.getTime()));
				}
				p++;
				// SQL: UPDATE_USER (STRING):
				if (gchsExecutionMode.isNullUpdateUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myUpdateUser);
				}
				p++;
				// SQL: UPDATE_PROGRAM (STRING):
				if (gchsExecutionMode.isNullUpdateProgram())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myUpdateProgram);
				}
				p++;
				// SQL: COD_DEVELOP (STRING):
				{
					ps.setString(p, gchsExecutionMode.myCodDevelop);
				}
				p++;
				// SQL: COD_USER (STRING):
				if (gchsExecutionMode.isNullCodUser())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myCodUser);
				}
				p++;
				// SQL: NAME_TYPE (STRING):
				if (gchsExecutionMode.isNullNameType())
				{
					ps.setNull(p, java.sql.Types.VARCHAR);
				}
				else
				{
					ps.setString(p, gchsExecutionMode.myNameType);
				}
				p++;
				// SQL: NAME_TYPE_XI18N (LONG):
				if (gchsExecutionMode.isNullNameTypeXi18n())
				{
					ps.setNull(p, java.sql.Types.NUMERIC);
				}
				else
				{
					ps.setLong(p, gchsExecutionMode.myNameTypeXi18n);
				}
				p++;
				// SQL: OPTIMIST_LOCK (LONG):
				ps.setLong(p, 1);
				gchsExecutionMode.setOptimistLock(1);
				ps.addBatch();
			}
			ps.executeBatch();
			if (ps.getUpdateCount()!=collection.size())
			{
				throw new isf.persistence.NotAllRecordsSuccesfullyBatchedException("GCHS_EXECUTION_MODE", collection.size(), ps.getUpdateCount(), collection);
			}
			else
			{
				for (final java.util.Iterator<GchsExecutionMode> iterator=collection.iterator();iterator.hasNext();)
				{
					final GchsExecutionMode gchsExecutionMode=iterator.next();
					gchsExecutionMode.resetModifiedFlags();
				}
			}
		}
		finally
		{
			ps.close();
		}
	}

	/**
	 * Put one persistence object in the static caches.
	 * @param gchsExecutionMode Persistence object.
	 */
	private static void putObjectInCaches(GchsExecutionMode gchsExecutionMode)
	{
		// Almacenar el objeto en la caché principal:
		pkCache.put(gchsExecutionMode.myCodDevelop, gchsExecutionMode);
	}

	/**
	 * Checks if cache is enabled.
	 * @return true if it is enabled.
	 */
	private static final boolean isCacheEnabled()
	{
		java.lang.String s=java.lang.System.getProperty("isf.persistence.cache_enabled");
		return (s!=null && s.equalsIgnoreCase("true"));
	}

	/**
	 * Resets the cache.
	 */
	public static void resetCache()
	{
		synchronized (pkCache)
		{
			pkCache.reset();
		}
	}

	/**
	 * Loads the cache from JDBC.
	 * @param connection JDBC connection.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static void refreshCache(java.sql.Connection connection)
	throws
		java.sql.SQLException
	{
		// Obsérvese que este método siempre se va a ejecutar privadamente y dentro de una región crítica.
		// Vaciar caché:
		pkCache.reset();
		java.sql.PreparedStatement st=connection.prepareStatement("SELECT /*LOAD_CACHE*/ * FROM GCHS_EXECUTION_MODE");
		java.sql.ResultSet rs=null;
		try
		{
			st.setFetchSize(100);
			rs=st.executeQuery();
			GchsExecutionMode gchsExecutionMode;
			for (int i=0;rs.next() && i<10000;)
			{
				// Leer objeto:
				gchsExecutionMode=new GchsExecutionMode();
				gchsExecutionMode.loadResultSet(rs);
				putObjectInCaches(gchsExecutionMode);
			}
		}
		finally
		{
			if (rs!=null)
			{
				rs.close();
			}
			st.close();
		}
		pkCache.markAsLoaded();
	}

	/**
	 * Refresh cache if necessary.
	 * @param connection JDBC connection.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static final void refreshCacheIfNecessary(java.sql.Connection connection)
	throws
		java.sql.SQLException
	{
		if (pkCache.needsReload())
		{
			refreshCache(connection);
		}
	}

	/**
	 * Reads an object by PK from the cache.
	 * @param connection JDBC connection.
	 * @param pCodDevelop Value for field CodDevelop.
	 * @return Read object or null if not found.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static GchsExecutionMode readFromCache(final java.sql.Connection connection, final java.lang.String pCodDevelop)
	throws
		java.sql.SQLException
	{
		synchronized(pkCache)
		{
			refreshCacheIfNecessary(connection);
		}
		GchsExecutionMode gchsExecutionMode=(GchsExecutionMode)pkCache.get(pCodDevelop);
		if (gchsExecutionMode==null)
		{
			synchronized(pkCache)
			{
				gchsExecutionMode=(GchsExecutionMode)pkCache.get(pCodDevelop);
				if (gchsExecutionMode==null && pkCache.size()==10000)
				{
					gchsExecutionMode=readFromJDBC(connection,pCodDevelop);
					if (gchsExecutionMode!=null)
					{
						putObjectInCaches(gchsExecutionMode);
						pkCache.markAsLoaded();
					}
				}
			}
		}
		pkCache.markAsLoaded();
		return gchsExecutionMode;
	}

	/**
	 * Reads an object by PK from the cache if active, or else from JDBC.
	 * @param connection JDBC connection.
	 * @param pCodDevelop Value for field CodDevelop.
	 * @return Read object or null if not found.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static GchsExecutionMode read(final java.sql.Connection connection, final java.lang.String pCodDevelop)
	throws
		java.sql.SQLException
	{
		return (isCacheEnabled())
			? readFromCache(connection,pCodDevelop)
			: readFromJDBC(connection,pCodDevelop)
		;
	}

	/**
	 * Reads a collection of objects by a SQL from the cache (without condition nor order).
	 * @param connection JDBC connection.
	 * @return Read collection.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	private static java.util.Collection<GchsExecutionMode> loadFromCache(final java.sql.Connection connection)
	throws
		java.sql.SQLException
	{
		synchronized(pkCache)
		{
			refreshCacheIfNecessary(connection);
			return pkCache.values();
		}
	}

	/**
	 * Reads a collection of objects by a SQL condition and order, from the cache if active, or else from JDBC.
	 * @param connection JDBC connection.
	 * @param sql1 SQL query without WHERE nor ORDER.
	 * @param sql2 Clauses WHERE + ORDER (may be empty, but never null).
	 * @param collection Collection of read objects
	 * @return Read collection.
	 * @exception java.sql.SQLException If a JDBC error occurs.
	 */
	public static java.util.Collection<GchsExecutionMode> loadSQL(final java.sql.Connection connection, final java.lang.String sql1, final java.lang.String sql2, java.util.Collection<GchsExecutionMode> collection)
	throws
		java.sql.SQLException
	{
		return (isCacheEnabled() && sql2.length()==0)
			? loadFromCache(connection)
			: loadSQLFromJDBC(connection,sql1,sql2,collection)
		;
	}

	/**
	 * Implements the interface ISQLIterarator to create an iterator from a Resultset.

	 */
	protected static class SQLIterator extends isf.persistence.AbstractSQLIterator<GchsExecutionMode>
	{
		/**
		 * Initialize the object.
		 * @param rs The source ResulSet.
		 * @exception java.sql.SQLException If a JDBC error occurs.
		 */
		public SQLIterator(final java.sql.ResultSet rs)
		throws
			java.sql.SQLException
		{
			super(rs);
		}

		/**
		 * Returns the next element in the iteration.
		 * @param rs The source ResulSet.
		 * @return The read object.
		 * @exception java.sql.SQLException If a JDBC error occurs.
		 */
		@java.lang.Override
		protected GchsExecutionMode create(final java.sql.ResultSet rs)
		throws
			java.sql.SQLException
		{
			final GchsExecutionMode gchsExecutionMode=new GchsExecutionMode();
			gchsExecutionMode.loadResultSet(rs);
			return gchsExecutionMode;
		}

	}

}

