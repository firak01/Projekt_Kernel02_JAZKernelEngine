package basic.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_DIRECTORY_DEFAULT;
import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_NAME_DEFAULT;

import java.io.File;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerEnabledZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zUtil.io.FileExpansionZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;
import custom.zKernel.ConfigZZZ;
import custom.zKernel.ILogZZZ;
import custom.zKernel.LogZZZ;
import custom.zUtil.io.FileZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelLogZZZ extends AbstractObjectWithFlagZZZ implements ILogZZZ, IFileExpansionEnabledZZZ, IFileExpansionUserZZZ{
	private static final long serialVersionUID = -3655229269566034195L;

	//flags 
	//private boolean bFlagUse_FILE_Expansion; //Zeigt an, ob eine Dateinamens Expansion angehängt werden muss, oder eine bestehende Expansion ersetzt hat.
	protected volatile IKernelConfigZZZ objConfig = null;   //die Werte für den Applikationskey, Systemnummer, etc.
		
	private String sLogFilename=null;
	private String sLogDirectorypath=null;
	
	protected IStringFormatManagerZZZ objStringFormatManager = null;
	protected IFileExpansionZZZ objFileExpansion = null;
	private FileTextWriterZZZ objFileTextWriter = null;
	private FileZZZ objFileZZZ = null;
	
	//++++++++++++++++++++++++
	//Konstruktoren
	public AbstractKernelLogZZZ(){	
		super();
	}


	/**
	 * Constructor KernelLogZZZ.
	 * @param LogFile-Path
	 */
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn) throws ExceptionZZZ {
		super();
		AbstractKernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, (String[])null);
	}
	
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String sFlagControl) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		AbstractKernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	public AbstractKernelLogZZZ(String sDirectoryPathIn, String sLogFileIn, String[] saFlagControl) throws ExceptionZZZ {
		super();
		AbstractKernelLogNew_(null, sDirectoryPathIn, sLogFileIn, null, saFlagControl);
	}
	
	public AbstractKernelLogZZZ(IKernelConfigZZZ objConfig) throws ExceptionZZZ {
		super();
		AbstractKernelLogNew_(objConfig, null, null,  null, (String[]) null);
	}
	
	private void AbstractKernelLogNew_(IKernelConfigZZZ objConfig, String sDirectoryPathIn, String sLogFileIn, IFileExpansionZZZ objFileExpansion, String[] saFlagControl) throws ExceptionZZZ{
		
		//TODOGOON202600304;//1. Checken ob "init" in Array, dann break
		                 //2. objStringFormatManger als instanz holen
		                 //3. den objStringFormatManager am LogObjekt für die Flagset Operation registrieren
		                 //4. nun erst die Flags setzen
		main:{
		if(saFlagControl!=null){
			boolean btemp = false;
			for(int icount=0;icount <= saFlagControl.length-1;icount++){
				String stemp = saFlagControl[icount];
				btemp = this.setFlag(stemp, true);
				
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ(IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					   throw ez;		 
				}
			}
			if(this.getFlag("init")) break main;
		}
		
		this.setConfigObject(objConfig);
		
		String sLogFile=null;
		if(StringZZZ.isEmpty(sLogFileIn)) {
			if(objConfig!=null) {
				sLogFile = objConfig.getLogFileName();
			}else {
				sLogFile = this.getFilename();
			}
		}else {
			sLogFile = sLogFileIn;
		}
		this.setFilename(sLogFile);
		
		String sDirectoryName=null;
		if(StringZZZ.isEmpty(sDirectoryPathIn)) {
			if(objConfig!=null) {
				sDirectoryName = objConfig.getLogDirectoryName();
			}else {
				sDirectoryName = this.getDirectory();
			}
		}else {
			sDirectoryName = sDirectoryPathIn;
		}
		this.setDirectory(sDirectoryName);
		
		FileTextWriterZZZ objFileTextWriter = this.getFileTextWriterObject();
		if(objFileTextWriter!=null) {
			this.writeLineDate("Log created");
		}else {
			sDirectoryName=this.getDirectory();
			sLogFile = this.getFilename();
			System.out.println("Unable to create FileWriterObject for Log, for path '" + sDirectoryName + " and filename: " + sLogFile + "'.");
		}			
	}//end main:
		
	}

	//### aus IStringFormatManagerUserZZZ #########################
	public IStringFormatManagerZZZ getStringFormatManager() throws ExceptionZZZ{
		if(this.objStringFormatManager==null) {
			this.objStringFormatManager = StringFormatManagerZZZ.getInstance();
			this.registerForFlagEventAdopted((IListenerObjectFlagZsetZZZ) this.objStringFormatManager);//Damit wird dieser an die Flags gekoppelt
		}
		return this.objStringFormatManager;
	}
	
	public void setStringFormatManager(IStringFormatManagerZZZ objStringFormatManager) throws ExceptionZZZ{
		this.objStringFormatManager = objStringFormatManager;
	}
	
	//### aus ILogStringComputerZZZ ################################
	public String computeLine(Object object, String sLog) throws ExceptionZZZ{
		IStringFormatManagerZZZ objFormatManager = this.getStringFormatManager();
		return AbstractKernelLogZZZ.computeLine(object, objFormatManager, sLog);				
	}
	
	
	//#########################################################################################################
	//#########################################################################################################
	//#### static Methoden #########################################
	
	
	//#######################################################
	//### Da diese Formate an mehreren Stellen verwendet werden .compute...Justified,  .compute...Jagged
	//### werden hier die Arrays einmal definiert durch diese Methoden.
	//#######################################################
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineDefault() throws ExceptionZZZ{
		return getFormatForComputeLine();
	}
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLine() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedStringFormatZZZ[]iaFormat = {
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLine_withObject() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedStringFormatZZZ[]iaFormat = {				 
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineWithPosition_withObject() throws ExceptionZZZ{
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 //Merke: Da nun alles STRING_BY... ist, muss man keine XML-Tags mehr aus dem String entfernen, wie mit:
		 //       sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		IEnumSetMappedStringFormatZZZ[]iaFormat= {
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
		 };
		return iaFormat;
	}
	
	
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineDate() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedStringFormatZZZ[]iaFormat= {
				 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineDate_withObject() throws ExceptionZZZ{
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 IEnumSetMappedStringFormatZZZ[]iaFormat= {
				 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,				 
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
		 };
		 return iaFormat;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineDateWithPosition_withObject() throws ExceptionZZZ{
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
		
		 //20240427;//Baue den LogString nun mit einer konfigurierbaren Klasse
		 //Merke: Da nun alles STRING_BY... ist, muss man keine XML-Tags mehr aus dem String entfernen, wie mit:
		 //       sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		IEnumSetMappedStringFormatZZZ[]iaFormat= {
				 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
				 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORPOSITION_STRING,
				 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
		 };
		return iaFormat;
	}
	
	
	public static IEnumSetMappedStringFormatZZZ[] getFormatForComputeLineDateWithPositionXml_withObject() throws ExceptionZZZ{
		//Da die Position nicht an anderer Stelle ermittelt werden kann, sie hier in die Log-Strings aufnehmen.
		//Bei der Abarbeitung wird geprüft, ob der verwendete Tag "positioncurrent" vorhanden ist.
		//Wenn das der Fall ist, gib diesen an der durch die Formatanweisung festgelegten Position aus.
	
		IEnumSetMappedStringFormatZZZ[]iaFormat= {
			IStringFormatZZZ.LOGSTRINGFORMAT.DATE_XML,			
			IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_XML,
			//Merke: Die Methode aus ReflectCodeZZZ.getPositionCurrent stammt (nicht anders zu bekommen), ist die Quelle XML
			IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_XML_BY_STRING,
			IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR01_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_XML_BY_STRING,
			//Merke: Die Zeilenummer aus ReflectCodeZZZ.getPositionCurrent stammt (nicht anders zu bekommen), ist die Quelle XML
			IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORPOSITION_XML,
			IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML_BY_XML,
		};
		return iaFormat;
	}
	
	
	//#######################################################
	//### als einfache STRING Rueckgabe, basierend nur auf STRING Werte.
	//### Da PositionCurrent - XML ist, kann das hier nicht vorkommen.
	//#######################################################
	
	public synchronized static String computeLine(Object objIn, IStringFormatManagerZZZ objFormatManagerIn, String sLog) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		IStringFormatManagerZZZ objFormatManager=null; 
		if(objFormatManagerIn==null) {
			objFormatManager = StringFormatManagerZZZ.getInstance();		
		}else {
			objFormatManager = objFormatManagerIn;
		}
		
		
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		return computeLine__(objFormatManager, classObj, saLog);
	}
	
	public synchronized static String computeLine(Object objIn, IEnumSetMappedStringFormatZZZ[]iaFormat, String sLog) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();		
		return computeLine__(objFormatManager, classObj, 1, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Object objIn, String... sLogs) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLine__(objFormatManager, classObj, saLog);
	}	
	
	public synchronized static String computeLine(Object objIn, IEnumSetMappedStringFormatZZZ[]iaFormat, String... sLogs) throws ExceptionZZZ {		
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLine__(objFormatManager, classObj, 1, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Class classObj, String sLog) throws ExceptionZZZ {	
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance(); 
		return computeLine__(objFormatManager, classObj, saLog);
	}
	
	public synchronized static String computeLine(Class classObj, IEnumSetMappedStringFormatZZZ[]iaFormat, String sLog) throws ExceptionZZZ {
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLine__(objFormatManager, classObj, 1, iaFormat, saLog);
	}
	
	public synchronized static String computeLine(Class classObj, String... sLogs) throws ExceptionZZZ {
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLine__(objFormatManager, classObj, saLog);
	}
	
	private static String computeLine__(IStringFormatManagerZZZ objFormatManager, Class classObj, String[] saLog) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]iaFormat = getFormatForComputeLine_withObject();		
		return objFormatManager.compute(classObj, iaFormat, saLog);
	}
	
	private static String computeLine__(IStringFormatManagerZZZ objFormatManager, Class classObj, int iStackTraceLevelIn, IEnumSetMappedStringFormatZZZ[]iaFormatIn, String[] saLogIn) throws ExceptionZZZ{
		String[]saLog = null;
		IEnumSetMappedStringFormatZZZ[]iaFormat = null;
		if(iaFormatIn==null) {
			iaFormat = getFormatForComputeLine_withObject();
			saLog = saLogIn;
		}else {
			iaFormat = iaFormatIn;			
			//int iStackTraceLevel = iStackTraceLevelIn + 1;
			
			//Nun könnte auch etwas mit Positionsangabe gefordert sein, also auf Verdacht:
			//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
			String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(iStackTraceLevelIn);
			 
			//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
			//String[] satemp = StringArrayZZZ.append(satemp, sPositionCalling);
			saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);			
		}
		
		return objFormatManager.compute(classObj, iaFormat, saLog);
	}
	
	//##################################################################################
	//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
	//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
	//### sollte sie fehlen.
	//##################################################################################
	
	//+++ mit Datum	
	public synchronized static String computeLineDate(Object objIn) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDate__(objFormatManager, classObj, null);
	}
	
	public synchronized static String computeLineDate(Object objIn, String sLog) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDate__(objFormatManager, classObj, saLog);
	}
	
	public synchronized static String computeLineDate(Object objIn, String... sLogs) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog=sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDate__(objFormatManager, classObj, saLog);
	}
	
	
	public synchronized static String computeLineDate(Class classObj, String sLog) throws ExceptionZZZ {	
		String[]saLog = new String[1];
		saLog[0] = sLog;
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDate__(objFormatManager, classObj, saLog);
	}
	
	public synchronized static String computeLineDate(Class classObj, String... sLogs) throws ExceptionZZZ {	
		String[]saLog=sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDate__(objFormatManager, classObj, saLog);
	}
	
	private static String computeLineDate__(IStringFormatManagerZZZ objFormatManager, Class classObj, String[] saLog) throws ExceptionZZZ {	
		IEnumSetMappedStringFormatZZZ[]iaFormat = getFormatForComputeLineDate_withObject();		
		return objFormatManager.compute(classObj, iaFormat, saLog);
	}
	

	//#######################################################
	//### als einfache STRING Rueckgabe, aber teilweise basierende auf XML Werte.
	//### Merke1: Damit spart man sich ggfs. das Entfernen von XML-Tags.
	//### Merke2: Zeilennummer, etc aus der CodePosition kann nur als XML Wert zur Vefuegung gestellt werden.
	//#######################################################
	
	public synchronized static String computeLineDateWithPosition(Object objIn, String sLog) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[] saLog = new String[1];
		saLog[0]=sLog;
		return computeLineDateWithPosition__(classObj, 1, saLog);
	}
	
	public synchronized static String computeLineDateWithPosition(Object objIn, String sLog1, String sLog2) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[] saLog = new String[2];
		saLog[0]=sLog1;
		saLog[1]=sLog2;
		return computeLineDateWithPosition__(classObj, 1, saLog);
	}	
	
	
	public synchronized static String computeLineDateWithPosition(Object objIn, String... sLogs) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[] saLog = sLogs;
		return computeLineDateWithPosition__(classObj, 1, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPosition(Object objIn, int iLevelIn, String sLog) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		int iLevel = iLevelIn + 1;
		
		String[] saLog = new String[1];
		saLog[0]=sLog;
		return computeLineDateWithPosition__(classObj, iLevel, saLog);
	}
	
	public synchronized static String computeLineDateWithPosition(Object objIn, int iLevelIn, String sLog1, String sLog2) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		int iLevel = iLevelIn + 1;
		
		String[] saLog = new String[2];
		saLog[0]=sLog1;
		saLog[1]=sLog2;
		return computeLineDateWithPosition__(classObj, iLevel, saLog);
	}	
	
	
	public synchronized static String computeLineDateWithPosition(Object objIn, int iLevelIn, String... sLogs) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		int iLevel = iLevelIn + 1;
		
		String[] saLog = sLogs;
		return computeLineDateWithPosition__(classObj, iLevel, saLog);
	}
	
	//++++++++++++++++++++
	public synchronized static String computeLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ {	
		String[] saLog = new String[1];
		saLog[0] = sLog;
		return computeLineDateWithPosition__(classObj, 1, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPosition(Class classObj, String... sLogs) throws ExceptionZZZ {	
		String[] saLog = sLogs;
		return computeLineDateWithPosition__(classObj, 1, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPosition(Class classObj, int iLevelIn, String sLog) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		String[] saLog = new String[1];
		saLog[0] = sLog;
		return computeLineDateWithPosition__(classObj, iLevel, saLog);
	}
	
	public synchronized static String computeLineDateWithPosition(Class classObj, int iStackTraceLevelIn, String... sLogs) throws ExceptionZZZ {
		int iStackTraceLevel = iStackTraceLevelIn + 1;
		String[] saLog = sLogs;
		return computeLineDateWithPosition__(classObj, iStackTraceLevel, saLog);
	}
	
	private static String computeLineDateWithPosition__(Class classObj, int iLevelIn, String[] saLogIn) throws ExceptionZZZ {	
		IEnumSetMappedStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPosition_withObject();
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		int iLevel = iLevelIn + 1;
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(iLevelIn);
		//String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevelIn);
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		//String[] satemp = StringArrayZZZ.append(satemp, sPositionCalling);
		String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
		 
		return StringFormatManagerZZZ.getInstance().compute(classObj, iaFormat, saLog);
	}
	
	
	//##########################################
	//### ALS XML RUECKGABE, daher .computeJagged(...) verwenden. 
	//### Bei .computeJustified(...) wird es zwar buendig gemacht, aber es werden die XML-Tags entfernt. 
	//###
	//### TODOGOON20251124;//Hier die Formattypen auch als XML_BY_XML zur Verfuegung stellen
	//###  
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.DATE_XML,
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.THREADID_XML,
	//### ILogStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_XML_BY_STRING,
	//##########################################
	//+++ mit CodePosition
	public synchronized static String computeLineDateWithPositionXml(Object objIn, String sLog) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, 1, saLog);
	}
	
	public synchronized static String computeLineDateWithPositionXml(Object objIn, int iStackTraceOffset, String sLog) throws ExceptionZZZ {	
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, iStackTraceOffset, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPositionXml(Object objIn, String... sLogs) throws ExceptionZZZ {
		Object obj=null;
		if(objIn==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		Class classObj = obj.getClass();
		
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, 1, saLog);
	}
	
	
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, String sLog) throws ExceptionZZZ {	
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, 1, saLog);
	}
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, int iStackTraceLevelIn, String sLog) throws ExceptionZZZ {	
		String[]saLog = new String[1];
		saLog[0] = sLog;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, iStackTraceLevelIn, saLog);
	}
	
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, String... sLogs) throws ExceptionZZZ {	
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, 1, saLog);
	}
	
	public synchronized static String computeLineDateWithPositionXml(Class classObj, int iStackTraceLevelIn, String... sLogs) throws ExceptionZZZ {
		String[]saLog = sLogs;
		
		IStringFormatManagerZZZ objFormatManager = StringFormatManagerXmlZZZ.getInstance();
		return computeLineDateWithPositionXml__(objFormatManager, classObj, iStackTraceLevelIn, saLog);
	}
	
	private static String computeLineDateWithPositionXml__(IStringFormatManagerZZZ objFormatManager, Class classObj, int iStackTraceLevelIn, String[] saLogIn) throws ExceptionZZZ {
		int iStackTraceLevel = iStackTraceLevelIn + 1;
	
		IEnumSetMappedStringFormatZZZ[]iaFormat = getFormatForComputeLineDateWithPositionXml_withObject();		
		
		//Fuer die Positionsermittlung die XML Variante nehmen. Nur sie kann dann hinsichtlich der einzelnen Bestandteilen, wg. der Tags aufgeloest werden.
		String sPositionCalling = ReflectCodeZZZ.getPositionCallingXml(iStackTraceLevel);
		 
		//Packe diesen String mit in die Log-Strings, zur Abarbeitung durch den FormatManager
		String[] saLog = StringArrayZZZ.prepend(saLogIn, sPositionCalling);
		 
		//return StringFormatManagerZZZ.getInstance().computeJagged_(classObj, iaFormat, saLog);
		return objFormatManager.compute(classObj, iaFormat, saLog);
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Biete die Log-Methoden auch static an, siehe ILogZZZ, bzw. AbstractObjectZZZ fuer den Code
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	public synchronized static void logProtocolStringStatic(Object obj, String... sLogs) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			
			if(obj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}else {
				for(String sLog : sLogs) {
					logProtocolStringStatic(obj, sLog);
				}	
			}		
		}//end main:
	}
		
	public synchronized static void logProtocolStringStatic(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed;
		sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);
		System.out.println(sLogUsed);
	}

	public synchronized static void logProtocolStringStatic(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				AbstractKernelLogZZZ.logProtocolStringStatic(obj, sLogs);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						AbstractKernelLogZZZ.logProtocolStringStatic(ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						AbstractKernelLogZZZ.logProtocolStringStatic(sLog);
					}
				}
			}else {
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						AbstractKernelLogZZZ.logProtocolStringStatic(obj, ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						AbstractKernelLogZZZ.logProtocolStringStatic(sLog);
					}
				}			
			}
		}//end main:
	}

	public synchronized static void logProtocolStringStatic(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = StringFormatManagerZZZ.getInstance().compute(ienumMappedLogString, sLog);
		}else {
			sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
		}
		System.out.println(sLogUsed);
	}
	
	//++++++++++++++++++++++++
	public synchronized static void logProtocolStringStatic(Class classObj, String... sLogs) throws ExceptionZZZ{
		main:{
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLogs);
			System.out.println(sLogUsed);				
		}//end main:
	}
		
	public synchronized static void logProtocolStringStatic(Class classObj, String sLog) throws ExceptionZZZ{		
		if(classObj==null) {			
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}

		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLog);
		System.out.println(sLogUsed);
	}

	public synchronized static void logProtocolStringStatic(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				LogZZZ.logProtocolStringStatic(classObj, sLogs);
				break main;
			}
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumaMappedLogString, sLogs);		
			System.out.println(sLogUsed);
		}//end main:
	}

	public synchronized static void logProtocolStringStatic(Class classObj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		if(classObj==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	
		}
			
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumMappedLogString, sLog);		
		System.out.println(sLogUsed);
	}
	
	
		
	//##############################################################
	private FileTextWriterZZZ createFileTextWriterInternal(String sFilepath) throws ExceptionZZZ {
		FileTextWriterZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilepath))break main;
		
			objReturn = new FileTextWriterZZZ(sFilepath);
		}
		return objReturn;
	}
			
	public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ{
		if(this.objFileTextWriter==null) {
			FileZZZ objFile = this.getFileObject();
			String sFilename = objFile.PathNameTotalExpandedNextCompute();
			this.objFileTextWriter = createFileTextWriterInternal(sFilename);		
		}
		return this.objFileTextWriter;
	}

	@Override
	public synchronized boolean writeLine(String stemp){
		boolean bReturn = false;
		FileTextWriterZZZ objFileWriter;
		try {
			System.out.println(stemp);
			
			objFileWriter = this.getFileTextWriterObject();
			bReturn = objFileWriter.writeLine(stemp);						
		} catch (ExceptionZZZ e) {		
			e.printStackTrace();
		}	
		return bReturn;
	}
	
	//#####################################################################################
	public synchronized boolean WriteLine(Object obj, String sLine) throws ExceptionZZZ{
		String[]saLine = new String[0];
		saLine[0]=sLine;
		return WriteLine__(obj.getClass(), saLine);
	}
	
	
	public synchronized boolean WriteLine(Class classObj, String sLine) throws ExceptionZZZ{
		String[]saLine = new String[0];
		saLine[0]=sLine;
		return WriteLine__(classObj, saLine);
	}
	
	synchronized private boolean WriteLine__(Class classObj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = LogZZZ.computeLine(classObj, sLogs); //Darin wird die Zeile schon "bündig gemacht".		
		bReturn = writeLine(sLine);
				
		return bReturn;
	}

	//##################################################################################################
	
	@Override
	synchronized public boolean writeLineDate(String sLog) throws ExceptionZZZ{
		return WriteLineDate_(this, sLog);
	}
	
	@Override
	synchronized public boolean writeLineDate(String... sLogs) throws ExceptionZZZ{
		return WriteLineDate_(this, sLogs);
	}
	
	@Override
	synchronized public boolean writeLineDate(String sLog1, String sLog2) throws ExceptionZZZ{
		String[]saLog = new String[2];
		saLog[0]=sLog1;
		saLog[1]=sLog2;
		return WriteLineDate_(this, saLog);
	}
	
	@Override
	synchronized public boolean writeLineDate(Object obj, String sLog) throws ExceptionZZZ{
		return WriteLineDate_(obj, sLog);
	}
	
	synchronized private boolean WriteLineDate_(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;	
		
		String sLine = LogZZZ.computeLineDate(obj, sLogs); //Darin wird die Zeile schon "bündig gemacht".		
		bReturn = writeLine(sLine);
				
		return bReturn;
	}
	
	//######################################
	@Override
	synchronized public boolean writeLineDateWithPosition(String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition__(this.getClass(), 1, sLog);
	}
	
	@Override
	synchronized public boolean writeLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{
		return WriteLineDateWithPosition__(classObj, 1, sLog);
	}
	
	private boolean WriteLineDateWithPosition__(Class classObj, int iStackTraceLevelIn, String sLog) throws ExceptionZZZ{
		int iStackTraceLevel = iStackTraceLevelIn + 1;
		String sLine = computeLineDateWithPosition(classObj, iStackTraceLevel, sLog);
		return writeLine(sLine);
	}
	

	//############################################
	@Override
	synchronized public boolean writeLineDateWithPosition(Object objIn, String sLog) throws ExceptionZZZ{
		Object obj;
		if(objIn==null) {
			obj=this;
		}else {
			obj=objIn;
		}
		Class classObj = obj.getClass();
		return WriteLineDateWithPosition__(classObj, 1, sLog);
	}
	
	@Override
	synchronized public boolean writeLineDateWithPosition(Object objIn, int iStackTraceLevelIn, String sLog) throws ExceptionZZZ{
		int iStackTraceLevel = iStackTraceLevelIn +1;
		
		Object obj;
		if(objIn==null) {
			obj=this;
		}else {
			obj=objIn;
		}
		Class classObj = obj.getClass();
		
		return WriteLineDateWithPosition__(classObj, iStackTraceLevel, sLog);
	}
	
	
	/** Merke: Beim XML String findet kein "Bündigmachen" mit dem Justifier statt. 
	 * @param obj
	 * @param stemp
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 20:26:58
	 */
	@Override
	synchronized public boolean writeLineDateWithPositionXml(String stemp) throws ExceptionZZZ{
		Class classObj = this.getClass();
		return WriteLineDateWithPositionXml__(classObj, 1, stemp);
	}
	
	/** Merke: Beim XML String findet kein "Bündigmachen" mit dem Justifier statt. 
	 * @param obj
	 * @param stemp
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 20:26:58
	 */
	@Override
	synchronized public boolean writeLineDateWithPositionXml(Class classObj, String stemp) throws ExceptionZZZ{
		return WriteLineDateWithPositionXml__(classObj, 1, stemp);
	}
	
	/** Merke: Beim XML String findet kein "Bündigmachen" mit dem Justifier statt. 
	 * @param obj
	 * @param stemp
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 20:26:58
	 */
	@Override
	synchronized public boolean writeLineDateWithPositionXml(Object objIn, String stemp) throws ExceptionZZZ{		
		Object obj;
		if(objIn==null) {
			obj=this;
		}else {
			obj=objIn;
		}
		
		Class classObj = obj.getClass();
		return WriteLineDateWithPositionXml__(classObj, 1, stemp);
	}
	
	/** Merke: Beim XML String findet kein "Bündigmachen" mit dem Justifier statt. 
	 * @param obj
	 * @param stemp
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 20:26:58
	 */
	@Override
	synchronized public boolean writeLineDateWithPositionXml(Object objIn, int iStackTraceLevelIn, String stemp) throws ExceptionZZZ{
		int iStackTraceLevel = iStackTraceLevelIn + 1;
		
		Object obj;
		if(objIn==null) {
			obj=this;
		}else {
			obj=objIn;
		}
		Class classObj = obj.getClass();
		return WriteLineDateWithPositionXml__(classObj, iStackTraceLevel, stemp);
	}
	
	private boolean WriteLineDateWithPositionXml__(Class classObj, int iStackTraceLevelIn, String stemp) throws ExceptionZZZ{
		boolean bReturn = false;	
		int iStackTraceLevel = iStackTraceLevelIn + 1;
		
		String sLine = AbstractKernelLogZZZ.computeLineDateWithPositionXml(classObj, iStackTraceLevel, stemp);
		bReturn = writeLine(sLine);
		
		return bReturn;
	}
	
	@Override
	public void setFileObject(FileZZZ objFile) throws ExceptionZZZ{
		this.objFileZZZ = objFile;
	}
	
	@Override
	public FileZZZ getFileObject() throws ExceptionZZZ{
		if(this.objFileZZZ == null) {
			String sDirectoryPathNormed = this.getDirectory();
			String sLogFile = this.getFilename();
			
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();				
			FileZZZ objFile = new FileZZZ(sDirectoryPathNormed, sLogFile, objFileExpansion, null);
			this.setFileObject(objFile);
		}
		return this.objFileZZZ;
	}
	
	@Override
	public String getFilenameExpanded() throws ExceptionZZZ{
		return this.objFileZZZ.getNameExpandedCurrent();
	}
	
	@Override
	public String getFilename() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sLogFilename)) {
			String sLogFileNameFound=null;
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				sLogFileNameFound = objConfig.getLogFileName();
			}
			
			if(StringZZZ.isEmpty(sLogFileNameFound)) {
				sLogFileNameFound =new String(sLOG_FILE_NAME_DEFAULT);
			}else {
				this.setFilename(sLogFileNameFound);
			}
		}
		return this.sLogFilename;
	}
		

	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelLogZZZ#Write(java.lang.String)
	 */
	@Override
	public synchronized boolean write(String stemp) throws ExceptionZZZ{
		boolean bReturn = false;
		FileTextWriterZZZ objFileWriter;
//		try {
			objFileWriter = this.getFileTextWriterObject();
			bReturn = objFileWriter.write(stemp); //Kein Zeilenumbruch.
			
			System.out.print(stemp); //Kein Zeilenumbruck
//		} catch (ExceptionZZZ e) {			
//			e.printStackTrace();
//		}	
		return bReturn;
	}



	//############################################
	//### Functions implemented by interface
	//.....
	@Override
	public void setFilename(String sLogFilename) throws ExceptionZZZ{
		this.sLogFilename = sLogFilename;
		
		//File Objekte wieder zurücksetzen
		this.objFileZZZ = null;
		this.objFileTextWriter = null;
	}
	
	@Override
	public void setDirectory(String sLogDirectorypath) throws ExceptionZZZ {
		
		//20190116: Suche nach dem Pfad. Er ist ggfs. als Serverapplication ohne den "src"-Ordner.
		File objDirectory = FileEasyZZZ.searchDirectory(sLogDirectorypath);
		if(objDirectory==null) {
			String sError = "Verzeichnis für das KernelLog  ='" + sLogDirectorypath +"' existiert nicht.";
			System.out.println(ReflectCodeZZZ.getPositionCurrent()+": " + sError);
			ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;					
		}	
		String sDirectoryPathNormed = objDirectory.getAbsolutePath();
		String sLog = "Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'";
		//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": Errechneter existierender Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
		ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
		this.sLogDirectorypath = sDirectoryPathNormed; 
		
		//File Objekte wieder zurücksetzen
		this.objFileZZZ = null;
		this.objFileTextWriter = null;
	}
	
	@Override
	public String getDirectory() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sLogDirectorypath)) {
			String sLogFileDirectoryFound=null;
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				sLogFileDirectoryFound = objConfig.getLogDirectoryName(); 
			}
			
			if(StringZZZ.isEmpty(sLogFileDirectoryFound)) {
				sLogFileDirectoryFound =new String(sLOG_FILE_DIRECTORY_DEFAULT);
			}else {
				this.setDirectory(sLogFileDirectoryFound);
			}
		}
		return this.sLogDirectorypath;
	}
	
	@Override
	public IFileExpansionZZZ getFileExpansionObject() throws ExceptionZZZ{	
		if(this.getFlag(IFileExpansionEnabledZZZ.FLAGZ.USE_FILE_EXPANSION.name())) {
			if(this.objFileExpansion==null) {
				String sDir = this.getDirectory();			
				String sName = this.getFilename();
				FileZZZ objFileBase;
//				try {
					objFileBase = new FileZZZ(sDir,sName);
					this.objFileExpansion=new FileExpansionZZZ(objFileBase);
//				} catch (ExceptionZZZ e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}			
			}
			return this.objFileExpansion;
		}else {
			return null;
		}
	}
	
	@Override
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) throws ExceptionZZZ{
		this.objFileExpansion = objFileExpansion;
		
		//File Objekt aktualisieren
		FileZZZ objFile;
//		try {
			objFile = this.getFileObject();
			if(objFile!=null) {
				objFile.setFileExpansionObject(objFileExpansion);
			}
//		} catch (ExceptionZZZ e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
	}
	
	//### aus IKernelConfigUserZZZ
	@Override
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
		if(this.objConfig==null){
			this.objConfig = new ConfigZZZ(null);			
		}
		return this.objConfig;
	}
	
	@Override
	public void setConfigObject(IKernelConfigZZZ objConfig){
		this.objConfig = objConfig;
	}
	
	
	//#######################################
	//### FLAG Handling
	
	//### Aus Interface IStringFormatManagerEnabledZZZ
	@Override
	public boolean getFlag(IStringFormatManagerEnabledZZZ.FLAGZ objEnum_IStringFormatManagerZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IStringFormatManagerZZZ.name());
	}
	
	@Override
	public boolean setFlag(IStringFormatManagerEnabledZZZ.FLAGZ objEnum_IStringFormatManagerZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IStringFormatManagerZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IStringFormatManagerEnabledZZZ.FLAGZ[] objaEnum_IStringFormatManagerZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IStringFormatManagerZZZ)) {
				baReturn = new boolean[objaEnum_IStringFormatManagerZZZ.length];
				int iCounter=-1;
				for(IStringFormatManagerEnabledZZZ.FLAGZ objEnum_IStringFormatManagerZZZ:objaEnum_IStringFormatManagerZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IStringFormatManagerZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IStringFormatManagerEnabledZZZ.FLAGZ objEnum_IStringFormatManagerZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IStringFormatManagerZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//###################################################
	//### FLAG HANDLING #################################
	//###################################################
	
	//### aus IFileExpansionEnabledZZZ
	@Override
	public boolean getFlag(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}

	@Override
	public boolean setFlag(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IFileExpansionEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//###################################
	//### FLAG CUSTOM Handling
		
	@Override
	public boolean getFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());
	}

	@Override
	public boolean setFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}


	//###################################
	//### FLAGLOCAL Handling

	/* ES GIBT HIER KEIN FLAGLOCAL
	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	*/
			
}//end class
