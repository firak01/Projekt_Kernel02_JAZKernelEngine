package basic.zKernel;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IExpressionEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectWorkspaceZZZ;
import basic.zBasic.formula.ZFormulaIniLineZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.counter.CounterHandlerSingleton_AlphabetSignificantZZZ;
import basic.zBasic.util.counter.CounterHandlerSingleton_AlphabetZZZ;
import basic.zBasic.util.counter.CounterStrategyAlphabetSignificantZZZ;
import basic.zBasic.util.counter.ICounterAlphabetSignificantZZZ;
import basic.zBasic.util.counter.ICounterAlphabetZZZ;
import basic.zBasic.util.counter.ICounterStrategyAlphabetSignificantZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.IKernelCacheUserZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import basic.zKernel.cache.KernelCacheZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.IKernelCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniParserZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelFileIniZZZ;
import basic.zKernel.file.ini.IKernelJavaCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_VariableZZZ;
import basic.zKernel.file.ini.KernelFileIniZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniConverterZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import custom.zKernel.ConfigZZZ;
import custom.zKernel.FileFilterModuleZZZ;
import custom.zKernel.ILogZZZ;
import custom.zKernel.LogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;


/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelObjectZZZ extends AbstractObjectWithFlagZZZ implements IKernelZZZ, IKernelConfigConstantZZZ, IKernelLogUserZZZ, IKernelContextUserZZZ, IKernelCacheUserZZZ, IResourceHandlingObjectZZZ, IExpressionEnabledZZZ,IKernelEncryptionIniSolverZZZ, IKernelJsonIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelCallIniSolverZZZ {
	//Beachte static Konstanten in IKernelConfigConstant
	//public final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	//public final static String strFILE_NAME_DEFAULT = new String("JUnitKernelTest.ini");
	
	private static final long serialVersionUID = 699752030418877631L;
	
	
	private FileFilterModuleZZZ objFileFilterModule=null;
	
	//Merke 20180721: Wichtig ist mir, dass die neue HashMap für Variablen NICHT im Kernel-Objekt gespeichert wird. 
	//                        Sie hängt aussschliesslich am Kernel...IniFileZZZ-Objekt. 
	//                        Eventuelle Variablen-"Zustände" werden dann beim Holen der Parameter als Argument übergeben, sind aber NICHT im Kernel-Objekt gespeichert.
			
	//20210110: Nur noch über FileIniZZZ - Objekt ereichbar...  private IniFile objIniConfig=null;
	//20210110: Nur noch über FileIniZZZ - Objekt ereichbar...  private File objFileKernelConfig=null;
	protected volatile FileIniZZZ objFileIniKernelConfig = null; 
	//private IKernelConfigSectionEntryZZZ objEntry = null;
	
	protected volatile String sFileConfig="";
	protected volatile String sDirectoryConfig="";
	
	protected volatile String sSystemNumber="";	
	protected volatile String sApplicationKey="";

	protected volatile LogZZZ objLog = null;
	protected volatile IKernelConfigZZZ objConfig = null;   //die Werte für den Applikationskey, Systemnummer, etc.
	protected volatile IKernelContextZZZ objContext = null; //die Werte des aufrufenden Programms (bzw. sein Klassenname, etc.)
	
	protected volatile IKernelCacheZZZ objCache = null; //Ein Zwischenspeicher für die aus der Ini-Konfiguration gelesenen Werte.
	
	protected volatile ICryptZZZ objCrypt = null; //Ein CryptAlgorithmus für in der Ini-Konfiguration verschlüsselte Werte.
		
/**  Verwende diesen Konstruktor, wenn die Defaultangaben für das Verzeichnis und für den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
* lindhauer; 14.08.2007 06:40:31
 * @throws ExceptionZZZ
 */
public AbstractKernelObjectZZZ() throws ExceptionZZZ{
	super("init");//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
	
	//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
	IKernelConfigZZZ objConfig = this.getConfigObject();
	KernelNew_(objConfig, null, null, null, null, null, null, (String[])null);
}

public AbstractKernelObjectZZZ(String[] saFlagControl) throws ExceptionZZZ{
	super(saFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
	
	//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.   ConfigZZZ objConfig = new ConfigZZZ();
	IKernelConfigZZZ objConfig = this.getConfigObject();
	KernelNew_(objConfig, null, null, null, null, null, null, (String[]) null);
}

public AbstractKernelObjectZZZ(String[] saArg, String[] saFlagControl) throws ExceptionZZZ{
	super(saFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
	
	//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.   ConfigZZZ objConfig = new ConfigZZZ();
	IKernelConfigZZZ objConfig = this.getConfigObject();	
	KernelNew_(objConfig, null, null, null, null, null, null, saArg);
}


	/**Merke: Damit einzelne Projekte ihr eigenes ConfigZZZ - Objekt verwenden k�nnen, wird in diesem Konstruktor ein Interface eingebaut.
	* lindhauer; 14.08.2007 07:19:55
	 * @param objConfig
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public AbstractKernelObjectZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(objConfig, null, null, null, null, null, null, (String[]) null);
	}

	public AbstractKernelObjectZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(objConfig, null, null, null, null, null, null, (String[]) null);
	}
	
	/**
	CONSTRUCTOR

	TODO: Contructor mit File-Object
	TODO: Finden der Notes.ini. In der Notes.ini eine Section [FritZ-Kernel] einbauen und diese mit einer property versehen, welche den Pfad zur Configurationsdatei beinhaltet.
	            Dies als static Methode anbieten, die ein File-Object zur�ckliefert, dass dann Grundlage f�r die Kernel-Konfiguration ist.
	

	
	 @author 0823 , date: 18.10.2004
	 @param sApplicationKey, the namespace main part e.g. FGL
	 @param sSystemNumber, an additional text to specify different system for the application key, e.g. 01.   
	 Together with the 'application key' this makes the 'system number', e.g. FGL#01
	 @param sFileConfigPath, specifies the directory containing the basic configuration file, e.g. 
	 @param sFileConfigName
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		super(saFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, (String[]) null);
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saArg, String[] saFlagControl ) throws ExceptionZZZ{
		super(saFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saArg);
	}
	
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, (String[]) null);
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saArg, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, null, saArg);
	}
	
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, IKernelContextZZZ objContext, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, objContext, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName,null, (String[]) null);
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, IKernelContextZZZ objContext, String[] saArg, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl);//20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		KernelNew_(null, objContext, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName,null, saArg);
	}
	
	
	/** Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und f�r den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	* lindhauer; 14.08.2007 06:34:16
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param sFlagControl
	 * @throws ExceptionZZZ
	 */
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		
		//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
		IKernelConfigZZZ objConfig = this.getConfigObject();
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, (String[]) null);
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String[] saArg, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		IKernelConfigZZZ objConfig = this.getConfigObject();
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, saArg);
	}
	
	
	/**  Verwende diesen Konstruktor, wenn die Defaultangaben f�r das Verzeichnis und f�r den ini-Dateinamen verwendet werden sollen:
	 * -Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	 * Diese Werte stammen aus ConfigZZZ im custom.zKernel - Verzeichnis.
	 * 
	* lindhauer; 14.08.2007 06:45:43
	 * @param sApplicationKey
	 * @param sSystemNumber
	 * @param saFlagControl
	 * @throws ExceptionZZZ
	 */
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		
		//20181005: Die Default - Konfiguration nun auch in den verschiedenen Projekten konfigurierbar machen.  ConfigZZZ objConfig = new ConfigZZZ();
		IKernelConfigZZZ objConfig = this.getConfigObject();				
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, (String[]) null);
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, String[]saArg, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		IKernelConfigZZZ objConfig = this.getConfigObject();				
		KernelNew_(objConfig,null, sApplicationKey, sSystemNumber, null, null, null, saArg);
	}
	
	
	
	/** Choose this construktor, if you want to create another Kernel-Object, using the same configuration and log-file, but another Application
	* Lindhauer; 08.05.2006 08:21:25
	 * @param sApplicationKey
	 * @param objKernelNew
	 * @param saFlagControl
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, IKernelZZZ objKernelOld, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		main:{
			check:{
				if(objKernelOld==null){
					  ExceptionZZZ ez = new ExceptionZZZ("Missing kernel-object parameter.", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END check:
	
		File objFile = objKernelOld.getFileConfigKernel();
		String sFileConfigPath = objFile.getParent();
		String sFileConfigName = objFile.getName();
		LogZZZ objLog = objKernelOld.getLogObject();
		IKernelConfigZZZ objConfig = objKernelOld.getConfigObject();
		
		KernelNew_(objConfig, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objLog, (String[]) null);
		}//END main
	}
	public AbstractKernelObjectZZZ(String sApplicationKey, String sSystemNumber, IKernelZZZ objKernelOld, String[] saArg, String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210402: Direkte Flag-Verarbeitug wird nun in ObjectZZZ gemacht
		main:{
			check:{
				if(objKernelOld==null){
					  ExceptionZZZ ez = new ExceptionZZZ("Missing kernel-object parameter.", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END check:
	
		File objFile = objKernelOld.getFileConfigKernel();
		String sFileConfigPath = objFile.getParent();
		String sFileConfigName = objFile.getName();
		LogZZZ objLog = objKernelOld.getLogObject();
		//MErke: Da saArg übergeben werden soll, nicht das ConfigObjekt des alten Kernels verwenden 
		//       IKernelConfigZZZ objConfig = objKernelOld.getConfigObject();		
		//KernelNew_(objConfig, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objLog, saArg);
		
		KernelNew_(null, null, sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, objLog, saArg);
		}//END main
	}
	
	
	// DESTRUCTOR
	protected void finalize(){		
	}
	
	
	//############################
	@Override
	public LogZZZ getLogObject(){
		return this.objLog;
	}
	
	/** 
	The configuration file specifies where other configuration files are.
	The default filename of this file is: 'ZKernelConfigKernel_default.ini'.
	<CR>### Example:
		
	[FGL#01]
#Productive system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini


[FGL#02]
#Test system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini


[FGL#03]
#Developing system
KernelLogPath=c:\tempfgl\KernelLog
KernelLogFile=ZKernelLog_default.txt
KernelConfigPathKernel=c:\tempfgl\KernelConfig
KernelConfigFileKernel=ZKernelConfigKernel_default.ini
KernelConfigPathImport=c:\tempfgl\KernelConfig
KernelConfigFileImport=ZKernelConfigImport_default.ini

### End Example
	
	 @author 0823 , date: 18.10.2004
	 @return File-Object.
	 @throws ExceptionZZZ
	 */
	public File getFileConfigKernel() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = null;
			String sFileConfig = null;
			check:{
				//Falls schon mal geholt, nicht neu holen
				if(this.objFileIniKernelConfig!=null){
					objReturn = this.objFileIniKernelConfig.getFileObject();
					break main;
				}
				
				sFileConfig = this.getFileConfigKernelName();
				if(StringZZZ.isEmpty(sFileConfig)){
					sLog = "Missing property: 'Configuration File-Name'";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end check
		

			String sDirectoryConfig = this.getFileConfigKernelDirectory();	
			
			//Suche nach der Datei, ggfs. mit relativem Pfad unterhalb des Workspace oder sogar im Classpath (.war / .jar Datei, s. WebService)
			//MErke: Wenn diese Datei nicht existiert, wird im TEMP Verzeichnis eine temporäre Datei erstellt. z.B. C:\DOKUME~1\MYUSER~1\LOKALE~1\Temp\temp9018141784814640806ZZZ
			//       Diese existiert demnach immer...
			objReturn = FileEasyZZZ.searchFile(sDirectoryConfig, sFileConfig);
			if(objReturn==null){
				sLog = "'Configuration File' does not exist (null) in the current directory or in: " + sDirectoryConfig + this.sFileConfig + " or in the classpath.";
				System.out.println(sLog);				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}else if(objReturn.exists()==false){			
				sLog = "Configuration File '" + this.sFileConfig + "' does not exist in the current directory or in: '" + sDirectoryConfig + "' or in the classpath.";
				System.out.println(sLog);				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}	
		    			    
			if(objReturn.isDirectory()){	
				sLog = "'Configuration Filename' is a directory: " + this.sDirectoryConfig + File.separator + this.sFileConfig;
				System.out.println(sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Merke: Das soll dann einen Fehler werfen, wenn es eine temporäre Datei ist
			//TODO GOON 20190128: Eine temporäre Datei mit den übergebenen Daten füllen, so dass Sie als Minimale Kernelkonfiguration durchgeht.
			if(objReturn.isFile()){
				
				//Merke: Die einzige Methode eine neue temporäre Datei zu erkennen ist, dass die Dateigröße 0 ist.
				if(objReturn.length()==0){				
					sLog = "The configuration file '" + objReturn.getPath() + "' is empty. Probabley a temporary file because the original file does not exist ('" + this.sDirectoryConfig + File.separator + this.sFileConfig+"').";
					System.out.println(sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
			}

			
			
		}//end main:		
		return objReturn;
	}
	
	@Override
	public String getFileConfigKernelName() throws ExceptionZZZ{		
		String sFileConfigKernelName = this.sFileConfig;
		if(StringZZZ.isEmpty(sFileConfigKernelName)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sFileConfigKernelName=objConfig.getConfigFileNameDefault();
			this.sFileConfig = sFileConfigKernelName;
		}
		return sFileConfigKernelName;
	}
	protected void setFileConfigKernelName(String sFileConfig){
		this.sFileConfig = sFileConfig;
	}
	
	@Override
	public String getFileConfigKernelDirectory() throws ExceptionZZZ{		
		String sDirectoryConfig = this.sDirectoryConfig;
		File objDir = null;
		
		boolean bDirectoryFound = false;
		if(StringZZZ.isEmpty(sDirectoryConfig)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sDirectoryConfig=objConfig.getConfigDirectoryNameDefault();			
			//if(sDirectoryConfig.equals("")||KernelZFormulaIni_EmptyZZZ.getTagEmpty().equals(sDirectoryConfig)){
			if(sDirectoryConfig.equals("")||XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME).equals(sDirectoryConfig)){
				String sDirConfigTemp = AbstractKernelObjectZZZ.sDIRECTORY_CONFIG_DEFAULT; 
				objDir = new File(sDirConfigTemp);
				if(objDir.exists()){
					sDirectoryConfig = sDirConfigTemp;
					bDirectoryFound = true;
				}else{
					//Pfad relativ zum Eclipse Workspace
					URL workspaceURL=null;
					try {
						workspaceURL = new File(sDirectoryConfig).toURI().toURL();
						String sWorkspaceURL = workspaceURL.getPath();					
						sWorkspaceURL = StringZZZ.stripFileSeparatorsRight(sWorkspaceURL);				
						objDir = FileEasyZZZ.searchDirectory(sWorkspaceURL);
						if(objDir.exists()){
							sDirectoryConfig = objDir.getAbsolutePath();
							bDirectoryFound = true;
						}else{
							//Pfad relativ zum src - Ordner
							sDirConfigTemp = FileEasyZZZ.getFileRootPath(); 		
							objDir = new File(sDirConfigTemp);
							if(objDir.exists()){
								sDirectoryConfig = sDirConfigTemp;
								bDirectoryFound = true;
							}
						}
					} catch (MalformedURLException e) {
						ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			
				}									
		}else if(sDirectoryConfig==null || XmlUtilZZZ.computeTagEmpty(ZTagFormulaIni_NullZZZ.sTAG_NAME).equals(sDirectoryConfig)){
			
			//Pfad relativ zum Eclipse Workspace
			URL workspaceURL=null;;
			try {
				workspaceURL = new File(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT).toURI().toURL();
				String sWorkspaceURL = workspaceURL.getPath();					
				sWorkspaceURL = StringZZZ.stripFileSeparatorsRight(sWorkspaceURL);				
				objDir = FileEasyZZZ.searchDirectory(sWorkspaceURL);
				if(objDir.exists()){
					sDirectoryConfig = objDir.getAbsolutePath();
					bDirectoryFound = true;
				}else{
					//Pfad relativ zum src - Ordner
					String sDirConfigTemp = FileEasyZZZ.getFileRootPath(); 		
					objDir = new File(sDirConfigTemp);
					if(objDir.exists()){
						sDirectoryConfig = sDirConfigTemp;
						bDirectoryFound = true;
					}
				}
			} catch (MalformedURLException e) {
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		}
			
		}else{
			/* Merke: Das sind die hierin verarbeiteten Suchpfade
			 * if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CURRENT) | sDirectoryIn.equals(""))==> FileEasyZZZ.getFileRootPath();
		     * if(sDirectoryIn.equals(FileEasyZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER))==> sDirectory = sDirectoryIn; 
		     * if(FileEasyZZZ.isPathRelative(sDirectoryIn))==> FileEasyZZZ.getFileRootPath() + File.separator + sDirectoryIn;
		     * und dann wird auf die Existenz des Verzeichnisses geprüft.
			 */
			objDir = FileEasyZZZ.searchDirectory(sDirectoryConfig);
			if(objDir!=null){
				//sofort gefunden... dann nimm es.
				sDirectoryConfig = objDir.getAbsolutePath();
				bDirectoryFound = true;
			}else{
				//Jetzt geht die Sucherei los
				String sDirConfigTemp = AbstractKernelObjectZZZ.sDIRECTORY_CONFIG_DEFAULT+File.separator+sDirectoryConfig;			
				objDir = new File(sDirConfigTemp);
				if(objDir.exists()){
					sDirectoryConfig = objDir.getAbsolutePath();//sDirConfigTemp;
					bDirectoryFound = true;
				}else{
					//Merke 20190129: Hier wird keine Fehlermeldung geworfen. Jetzt ist die Idee mit einer temporären Minimalkonfigurationsdatei (selbst erzeugt) weiterzuarbeiten.
					bDirectoryFound = false;
				}
			}								
		}
		if(bDirectoryFound){
			this.setFileConfigKernelDirectory(sDirectoryConfig);
		}
		return sDirectoryConfig;
	}
	protected void setFileConfigKernelDirectory(String sDirectoryConfig){
		this.sDirectoryConfig = sDirectoryConfig;
	}
	
	@Override
	public IniFile getFileConfigKernelAsIni() throws ExceptionZZZ{
		IniFile objReturn = null;
		main:{
			if(this.objFileIniKernelConfig==null) {
				File objFile = this.getFileConfigKernel();
				if(objFile==null)break main;
				try {				
					objReturn = new IniFile(objFile.getPath());
				} catch (IOException e) {
					String sLog = "Configuration File. Not able to create ini-FileObject.";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName() );
					throw ez;
				} 
			}else {
				objReturn = this.objFileIniKernelConfig.getFileIniObject();
			}
		}//end main:
		return objReturn;	
	}
	
	public IniFile getFileConfigModuleAsIni(String sModule) throws ExceptionZZZ{
		return this.getFileConfigModuleAsIni_(sModule, true);
	}
	
	public IniFile getFileConfigModuleAsIni(String sModule, boolean bIncludeSystemSection) throws ExceptionZZZ{
		return this.getFileConfigModuleAsIni_(sModule, bIncludeSystemSection);
	}
	
	private IniFile getFileConfigModuleAsIni_(String sModule, boolean bIncludeSystemSection) throws ExceptionZZZ{
		IniFile objReturn = null;
		main:{
			check:{
				if(sModule == null){							
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sModule.equals("")){							
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}						
			}//end check:
			
			//Hole zuerst das "Basis-File"
			File objFile = this.getFileConfigKernel();
			if(objFile==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty Property FileConfigKernel",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			KernelFileIniZZZ objFileIniDummy = new FileIniZZZ();
			String[]saFlagControl = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objFileIniDummy, true);
			IKernelFileIniZZZ objConfigIni = new FileIniZZZ(this,objFile,saFlagControl);
			
			//########################################
			//### Vereinfachung mit ArrayList
			//########################################						
			String sSystemNumber = this.getSystemNumber();
			
			ArrayList<String> listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule(objConfigIni, sModule, sSystemNumber);
			if(bIncludeSystemSection) {
				String sApplicationKey = this.getApplicationKey();
				
				ArrayList<String> listasSystemSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKey, sSystemNumber);
				listasModuleSection = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(listasModuleSection, listasSystemSection);
			}
			IniFile objIni = objConfigIni.getFileIniObject();
			objReturn = this.KernelSearchFileConfigDirectLookup_(objIni, sModule, listasModuleSection);
						
		}//end main:
		return objReturn;
	}
	
	//#######################
	public IniFile getFileConfigModuleAsIniInWorkspace(IKernelConfigZZZ objConfig, String sModule) throws ExceptionZZZ{
		return this.getFileConfigModuleAsIniInWorkspace_(objConfig, sModule, true);
	}
	
	public IniFile getFileConfigModuleAsIniInWorkspace(IKernelConfigZZZ objConfig, String sModule, boolean bIncludeSystemSection) throws ExceptionZZZ{
		return this.getFileConfigModuleAsIniInWorkspace_(objConfig, sModule, bIncludeSystemSection);
	}
	
	private IniFile getFileConfigModuleAsIniInWorkspace_(IKernelConfigZZZ objConfig, String sModule, boolean bIncludeSystemSection) throws ExceptionZZZ{
		IniFile objReturn = null;
		main:{
			if(objConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;
			
			check:{
				if(sModule == null){							
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sModule.equals("")){							
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}						
			}//end check:
			
			//Hole zuerst das "Basis-File"
			File objFile = this.getFileConfigKernel();
			if(objFile==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty Property FileConfigKernel",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
			KernelFileIniZZZ objFileIniDummy = new FileIniZZZ();
			String[]saFlagControl = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objFileIniDummy, true);
			IKernelFileIniZZZ objConfigIni = new FileIniZZZ(this,objFile,saFlagControl);
			
			//########################################
			//### Vereinfachung mit ArrayList
			//########################################			
			String sApplicationKey = this.getApplicationKey();
			String sSystemNumber = this.getSystemNumber();
			
			ArrayList<String> listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule(objConfigIni, sModule, sApplicationKey, sSystemNumber);			
			if(bIncludeSystemSection) {
				ArrayList<String> listasSystemSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKey, sSystemNumber);
				listasModuleSection = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(listasModuleSection, listasSystemSection);
			}
			
			
			IniFile objIni = objConfigIni.getFileIniObject();
			objReturn = this.KernelSearchFileConfigDirectLookupInWorkspace_(objConfig, objIni, sModule, listasModuleSection);
						
		}//end main:
		return objReturn;
	}
		
	public String getApplicationKey() throws ExceptionZZZ{		
		String sApplicationKey = this.sApplicationKey;
		if(StringZZZ.isEmpty(sApplicationKey)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sApplicationKey = objConfig.getApplicationKeyDefault();
			this.setApplicationKey(sApplicationKey);
		}
		return this.sApplicationKey;
	}
	protected void setApplicationKey(String sApplicationKey){
		this.sApplicationKey = sApplicationKey;
	}
	
	public String getSystemNumber() throws ExceptionZZZ{
		String sSystemNumber = this.sSystemNumber;
		if(StringZZZ.isEmpty(sSystemNumber)){
			IKernelConfigZZZ objConfig = this.getConfigObject();
			sSystemNumber = objConfig.getSystemNumberDefault();
			this.setSystemNumber(sSystemNumber);
		}		
		return this.sSystemNumber;
	}
	protected void setSystemNumber(String sSystemNumber){
		this.sSystemNumber=sSystemNumber;
	}

	/**
	 * @return sApplicationKey + "!" + sSystemNumber, also der Modulname z.B. f�r das Produktivsystem
	 * @throws ExceptionZZZ 
	 */
	public String getSystemKey() throws ExceptionZZZ{
		String stemp = this.getApplicationKey();
		String stemp2 = this.getSystemNumber();
		return AbstractKernelObjectZZZ.computeSystemSectionNameForSection(stemp, stemp2);
	}
	
	public static String computeSystemSectionNameForSection(String sSection, String sSystemNumber) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;

			if(!StringZZZ.isEmpty(sSystemNumber)){
				if(!AbstractKernelObjectZZZ.isSystemSection(sSection)) {
					sReturn = sSection + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber;
				}else {
					sReturn = sSection;
				}
			}else{
				sReturn = sSection;
			}
		}//end main:
		return sReturn;
	}
	
	public ArrayList<String> computeSystemSectionNames() throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{
			String sApplicationKey = this.getApplicationKey();
			String sSystemNr = this.getSystemNumber();
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKey, sSystemNr);
		}
		return alsReturn;
	}
	public ArrayList<String> computeSystemSectionNames(String sApplicationOrModuleKey) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{			
			String sSystemNr = this.getSystemNumber();
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModuleKey, sSystemNr);
		}
		return alsReturn;		
	}
 	public static ArrayList<String> computeSystemSectionNames(String sApplicationOrModuleKey, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{			
			String sSection2use="";
			if(!StringZZZ.isEmpty(sApplicationOrModuleKey)) {
				if(!StringZZZ.isEmpty(sSystemNumber)) {
					if(!StringZZZ.endsWithIgnoreCase(sApplicationOrModuleKey, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber)) {
						sSection2use = sApplicationOrModuleKey + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber;
					}else {
						sSection2use = sApplicationOrModuleKey;
					}
					alsReturn.add(sSection2use);
				}
				alsReturn.add(sApplicationOrModuleKey);
			}
		}//end main:
		return alsReturn;
	}
	
	public static ArrayList<String> computeSystemSectionNamesForSection(String sSectionIn, String sApplicationKey, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		
		main:{
			if(StringZZZ.isEmpty(sSectionIn)) break main;
			
			String sSection=null; String sProgram=null;
			//+++ Ggfs. direkt den Wert hinzufügen		
			boolean bSystemSection = AbstractKernelObjectZZZ.isSystemSection(sSectionIn);
			if(bSystemSection) {
				alsReturn.add(sSectionIn);
			} 
			
			//### Vorbereitung für die Varianten 
			//+++ Normiere zuerst den SectionInput
			sSection = AbstractKernelObjectZZZ.computeSectionFromSystemSection(sSectionIn);
				
			//+++ Hole ggfs. einen Programnamen
			boolean bSystemSectionWithProgram = AbstractKernelObjectZZZ.isSystemSectionWithProgram(sSectionIn);
			if(bSystemSectionWithProgram) {
				sProgram = AbstractKernelObjectZZZ.computeProgramFromSystemSection(sSectionIn);
			}				
			
						
			//###VARIANTE A section!Systemnr ##########################################
			String sSection2use = null;	
									
			if(!StringZZZ.isEmpty(sSystemNumber)){
				if(!StringZZZ.endsWithIgnoreCase(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber)) {
					sSection2use = sSection + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber;
				} else {
					sSection2use = sSection;
				}
				alsReturn.add(sSection2use);
			}
			sSection2use = sSection; 
			alsReturn.add(sSection2use);
					
			
			if(StringZZZ.isEmpty(sProgram))break main;
			
			
			//### Variante B: Für Sections=Progam, ohne den ApplicationKey zu verwenden
			if(!StringZZZ.isEmpty(sSystemNumber)){
				if(!StringZZZ.endsWithIgnoreCase(sProgram, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber)) {						
					sSection2use = sProgram + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber;
				}else {
					sSection2use = sProgram;
				}
				alsReturn.add(sSection2use);
			}
			sSection2use = sProgram;			
			alsReturn.add(sSection2use);
			
			
			//### Variante C: Für Sections mit Programalias: Section!Systemnr_Program ##########################################			
			if(!StringZZZ.isEmpty(sSystemNumber)){
				if(!StringZZZ.endsWithIgnoreCase(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber)) {				
					sSection2use = sSection + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;
				} else {
					sSection2use = sSection +  IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;
				}				
				alsReturn.add(sSection2use);
			}
			sSection2use = sSection + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;			
			alsReturn.add(sSection2use);
			
			
			//### Variante D: Für Sections mit Programalias: ApplicationKey!Systemnr_Program ##########################################
			if(StringZZZ.isEmpty(sApplicationKey))break main;
			if(!StringZZZ.isEmpty(sSystemNumber)){
				if(!StringZZZ.endsWithIgnoreCase(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber)) {			
					sSection2use = sApplicationKey + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;
				}else {
					sSection2use = sApplicationKey + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;
				}
				alsReturn.add(sSection2use);
			}
			sSection2use = sApplicationKey + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgram;			
			alsReturn.add(sSection2use);									
		}//end main:
		alsReturn = (ArrayList<String>) ArrayListUtilZZZ.uniqueKeepLast(alsReturn);
		return alsReturn;
		
	}
	
	
	//#############################
	public ArrayList<String> computeSystemSectionNamesForModule(String sModule) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{
			FileIniZZZ objFileIniconfig = this.getFileConfigKernelIni();
			String sApplicationOrModule = this.getApplicationKey();
			String sSystemNr = this.getSystemNumber();
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule(objFileIniconfig, sModule, sApplicationOrModule, sSystemNr);
		}
		return alsReturn;
	}
		
	
	public static ArrayList<String> computeSystemSectionNamesForModule(IKernelFileIniZZZ objFileIniConfig, String sModule, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();		
		main:{
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
						
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule_(objFileIniConfig, sModule, null, sSystemNumber);			
			
		}//end main:
		return alsReturn;
	}
	
	public static ArrayList<String> computeSystemSectionNamesForModule(IKernelFileIniZZZ objFileIniConfig, String sModule, String sApplicationOrModule, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();		
		main:{
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
						
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule_(objFileIniConfig, sModule, sApplicationOrModule, sSystemNumber);			
			
		}//end main:
		return alsReturn;
	}
	
	//+++++++++++++++++++++++
	
	public ArrayList<String> computeSystemSectionNamesForModulePure(String sModule) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{
			FileIniZZZ objFileIniconfig = this.getFileConfigKernelIni();
			String sSystemNr = this.getSystemNumber();
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule(objFileIniconfig, sModule, null, sSystemNr);
		}
		return alsReturn;
	}
	
	
	public ArrayList<String> computeSystemSectionNamesForModulePure(IKernelFileIniZZZ objFileIniConfig, String sModule, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();		
		main:{
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
						
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule_(objFileIniConfig, sModule, null, sSystemNumber);			
			
		}//end main:
		return alsReturn;
	}
	
	//+++++++++++++++++++++++
	private static ArrayList<String> computeSystemSectionNamesForModule_(IKernelFileIniZZZ objFileIniConfig, String sModule, String sApplicationOrModule, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		
		main:{			
			if(StringZZZ.isEmpty(sModule)) break main;
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			//1. Zuerst Aliasse	
			 if(!StringZZZ.isEmpty(sApplicationOrModule)) {
				String sModuleAlias = AbstractKernelObjectZZZ.searchAliasForModule(objFileIniConfig, sModule, sApplicationOrModule, sSystemNumber);
				ArrayList<String> alsModuleAlias = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
				if(alsModuleAlias!=null) {
	            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModuleAlias);
	            }
			 }else {
				 String sModuleAlias = AbstractKernelObjectZZZ.searchAliasForModule(objFileIniConfig, sModule, sSystemNumber);
					ArrayList<String> alsModuleAlias = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
					if(alsModuleAlias!=null) {
		            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModuleAlias);
		            } 
			 }
			
			//2. Dann Systemkeys mit dem Modulnamen
			ArrayList<String> alsModule = AbstractKernelObjectZZZ.computeSystemSectionNames(sModule, sSystemNumber);
            if(alsModule!=null) {
            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModule);
            }
            
        	//3. Applicationkeys
            if(!StringZZZ.isEmpty(sApplicationOrModule)) {
            	ArrayList<String> alsApplication = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
            	if(alsApplication!=null) {
            		alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsApplication);
            	}
            }
          
		}//end main:
		alsReturn = (ArrayList<String>) ArrayListUtilZZZ.uniqueKeepLast(alsReturn);
		return alsReturn;
	}
	
	@Override
	public ArrayList<String> computeSystemSectionNamesForProgram(String sProgram) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		main:{
			FileIniZZZ objFileIniconfig = this.getFileConfigKernelIni();
			String sApplicationKey = this.getApplicationKey();
			String sSystemNr = this.getSystemNumber();
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFileIniconfig, sProgram, sApplicationKey, sSystemNr);
		}
		return alsReturn;
	}
	
	public static ArrayList<String> computeSystemSectionNamesForProgram(IKernelFileIniZZZ objFileIniConfig, String sProgramOrAliasIn, String sApplicationKey, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();		
		main:{
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileIniConfig, sProgramOrAliasIn,null, sApplicationKey,sSystemNumber);			
			
		}//end main:
		return alsReturn;
	}
	
	public static ArrayList<String> computeSystemSectionNamesForProgram(IKernelFileIniZZZ objFileIniConfig, String sProgramOrAliasIn,String sModule, String sApplicationKey, String sSystemNumber) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();		
		main:{
			if(objFileIniConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			alsReturn = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileIniConfig, sProgramOrAliasIn, sModule, sApplicationKey, sSystemNumber);			
			
		}//end main:
		return alsReturn;
	}
		
	private static ArrayList<String> computeSystemSectionNamesForProgram_(IKernelFileIniZZZ objFileConfigIni, String sProgramIn,String sModuleIn, String sApplicationAliasIn, String sSystemNumberIn) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		
		main:{			
			if(StringZZZ.isEmpty(sProgramIn)) break main;
			if(objFileConfigIni==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			String sSystemNumberUsed = "";
            String sModuleUsed = "";
            String sProgramUsed = "";
		    if(StringZZZ.isEmpty(sSystemNumberIn)) {
		    	sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sModuleIn);		    	
		    	if(StringZZZ.isEmpty(sSystemNumberUsed)) {
		    		sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sProgramIn);		    		
		    	}
		    	
		    }else {
		    	sSystemNumberUsed = sSystemNumberIn;	    		
		    }
		    		    
		    						
			if(StringZZZ.isEmpty(sModuleIn)) {
				sModuleUsed = AbstractKernelObjectZZZ.extractModuleFromSection(sProgramIn);
			}else {
				sModuleUsed = AbstractKernelObjectZZZ.extractModuleFromSection(sModuleIn);
			}
			
			if(StringZZZ.isEmpty(sProgramIn)) {
				sProgramUsed = AbstractKernelObjectZZZ.extractProgramFromSection(sModuleIn);				
			}else {
				sProgramUsed = AbstractKernelObjectZZZ.extractProgramFromSection(sProgramIn);
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			//0. Ermittle zuerst einen ggfs. vorhandenen Modulalias
			String sModuleAlias = AbstractKernelObjectZZZ.searchAliasForModule(objFileConfigIni, sModuleUsed, sApplicationAliasIn, sSystemNumberUsed);
            //PROBLEM: WAS TUN, wenn der Modulalias in einer anderen ini Datei (nämlich der des Moduls) definiert ist?
			
			
			//0. Ermittle danach einen ggfs. vorhandenen ProgramAlias, zuerst aus dem Modulalias, dann aus dem Modul, dann aus der Applikation		
			String sProgramAlias = null;
			if(!StringZZZ.isEmpty(sModuleAlias)) {
				sProgramAlias = AbstractKernelObjectZZZ.searchAliasForProgram(objFileConfigIni, sProgramUsed, sModuleAlias, sSystemNumberUsed);
			}
			if(StringZZZ.isEmpty(sProgramAlias)) {
				sProgramAlias = AbstractKernelObjectZZZ.searchAliasForProgram(objFileConfigIni, sProgramUsed, sModuleUsed, sSystemNumberUsed);
			}
			if(StringZZZ.isEmpty(sProgramAlias)) {
				sProgramAlias = AbstractKernelObjectZZZ.searchAliasForProgram(objFileConfigIni, sProgramUsed, sApplicationAliasIn, sSystemNumberUsed);
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++						
            //A) Mit einem ggfs. gefundenen ProgramAlias
			if(!StringZZZ.isEmpty(sProgramAlias)) {
				//1. Zuerst Modulebene
				//a) Modulealias				
				if(!StringZZZ.isEmpty(sModuleAlias)) {
					ArrayList<String> alsModuleAliasProgramAlias = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramAlias, sModuleAlias, sSystemNumberUsed);
		            if(alsModuleAliasProgramAlias!=null) {
		            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModuleAliasProgramAlias);
		            }
				}else {
					//die Suche nach dem Modulnamen ist nicht eine Alternative, sondern eine Ergänzung...				
				}
							
				//b) Modulname
				ArrayList<String> alsModuleProgramAlias = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramAlias, sModuleUsed, sSystemNumberUsed);
	            if(alsModuleProgramAlias!=null) {
	            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModuleProgramAlias);
	            }
	            
	            
	          //2. Danach auf Applikationsebene	            
	          ArrayList<String> alsApplicationProgramAlias = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramAlias, sApplicationAliasIn, sSystemNumberUsed);
	          if(alsApplicationProgramAlias!=null) {
	        	  alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsApplicationProgramAlias);
	          }	          	            
			}//end if !isempty(sProgramAlias)
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						
			//B) Mit dem ProgramUsed
			//a) Modulealias			
			if(!StringZZZ.isEmpty(sModuleAlias)) {
				ArrayList<String> alsModuleAlias = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramUsed, sModuleAlias, sSystemNumberUsed);
	            if(alsModuleAlias!=null) {
	            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModuleAlias);
	            }
			}else {
				//die Suche nach dem Modulnamen ist nicht eine Alternative, sondern eine Ergänzung...				
			}
						
			//b) Modulname
			ArrayList<String> alsModule = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramUsed, sModuleUsed, sSystemNumberUsed);
            if(alsModule!=null) {
            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsModule);
            }
            
            //2. auf Applikationsebene mit dem Programnamen
            ArrayList<String> alsApplicationProgram = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram_(objFileConfigIni, sProgramUsed, sApplicationAliasIn, sSystemNumberUsed);
            if(alsApplicationProgram!=null) {
            	alsReturn = (ArrayList<String>) ArrayListUtilZZZ.joinKeepLast(alsReturn, alsApplicationProgram);
            }
            
            //#############
          //3. Baue zum Schluss noch den Programnamen und den Alias direkt ein
            if(!StringZZZ.isEmpty(sProgramAlias)) {
	  			ArrayList<String> alsProgramAliasKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sProgramAlias, sSystemNumberUsed);			
	  			for(String sProgramAliasKeyUsedTemp : alsProgramAliasKeyUsed) {
	  				alsReturn.add(sProgramAliasKeyUsedTemp);
	  			}
            }
  			ArrayList<String> alsProgramKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sProgramUsed, sSystemNumberUsed);			
  			for(String sProgramKeyUsedTemp : alsProgramKeyUsed) {
  				alsReturn.add(sProgramKeyUsedTemp);
  			}
  			
  			
            
            //4. Baue zum Schluss noch die SystemKeys des Moduls ein
          //############################################################################################
		  //### Vereinfache den Code dadurch, dass aus der Übergabe die Details geholt werden.
		  //############################################################################################ 
  			if(!StringZZZ.isEmpty(sModuleAlias)) {
  				ArrayList<String> alsSystemAliasKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumberUsed);			
  				for(String sSystemAliasKeyUsedTemp : alsSystemAliasKeyUsed) {
  					alsReturn.add(sSystemAliasKeyUsedTemp);
  				}
  			}
            ArrayList<String> alsSystemKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleUsed, sSystemNumberUsed);			
			for(String sSystemKeyUsedTemp : alsSystemKeyUsed) {
				alsReturn.add(sSystemKeyUsedTemp);
			}
						
		 //5. Baue zum Schluss noch die SystemKeys der Applikation ein (das wird zwar vorher auch bestimmt irgendwann gesetzt, damit sie ganz unten stehen aber noch hier am Schluss hinzufügen)
          //############################################################################################
		  //### Vereinfache den Code dadurch, dass aus der Übergabe die Details geholt werden.
		  //############################################################################################
			String sApplicationKey = AbstractKernelObjectZZZ.extractModuleFromSection(sApplicationAliasIn);
            ArrayList<String> alsApplicationKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKey, sSystemNumberUsed);			
			for(String sApplicationKeyUsedTemp : alsApplicationKeyUsed) {
				alsReturn.add(sApplicationKeyUsedTemp);
			}
			
		}//end main:
		alsReturn = (ArrayList<String>) ArrayListUtilZZZ.uniqueKeepLast(alsReturn);
		return alsReturn;
	}
	
	private static ArrayList<String> computeSystemSectionNamesForProgram_(IKernelFileIniZZZ objFileConfigIni, String sProgramIn,String sModuleOrApplicationAliasIn, String sSystemNumberIn) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		
		main:{	
			if(objFileConfigIni==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIniZZZ",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName() );
				throw ez;
			}
			
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
			HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			String sSection2use=null;						
			String sSystemNumberUsed = "";
			String sModuleOrApplicationAliasUsed = "";
			String sProgramAliasUsed = "";
			String sProgramUsed = "";
			
			if(StringZZZ.isEmpty(sProgramIn)) {
				sProgramUsed = AbstractKernelObjectZZZ.extractProgramFromSection(sModuleOrApplicationAliasIn);
			}else {
				sProgramUsed = sProgramIn;
			}
			if(StringZZZ.isEmpty(sProgramUsed)) break main;
			
			//############################################################################################
		    //### Vereinfache den Code dadurch, dass aus der Übergabe die Details geholt werden.
		    //############################################################################################
		    if(StringZZZ.isEmpty(sSystemNumberIn)) {
		    	sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sModuleOrApplicationAliasIn);
		    	
		    	if(StringZZZ.isEmpty(sSystemNumberUsed)) {
		    		sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sProgramIn);		    		
		    	}
		    	
		    }else {
		    	sSystemNumberUsed = sSystemNumberIn;		    		
		    }
		    
		    if(StringZZZ.isEmpty(sModuleOrApplicationAliasIn)) {
		    	sModuleOrApplicationAliasUsed = AbstractKernelObjectZZZ.extractModuleFromSection(sProgramIn);
		    }else {
		    	sModuleOrApplicationAliasUsed = sModuleOrApplicationAliasIn;
		    }
			
			//#############################################################################################
			//### Aufbau der Zielstrukturen
			//#############################################################################################		   		   
			ArrayList<String> alsSystemKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleOrApplicationAliasUsed, sSystemNumberUsed);				

			//Suche nach dem Alias
			IKernelConfigSectionEntryZZZ objEntry = AbstractKernelObjectZZZ.searchProgramAliasFor(objFileConfigIni, sProgramUsed, sModuleOrApplicationAliasUsed, sSystemNumberUsed, null);
			
			//20240509: Rette die Suchschritte
			HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
			hmSearched.add(hmSearchedTemp);
			
			if(objEntry.hasAnyValue()) {
				sProgramAliasUsed = objEntry.getValue();							
				
				//Baue folgendes: Modul!01_TestProgAlias und Modul_TestProgAlias				
				for(String sSystemKeyUsedTemp : alsSystemKeyUsed) {
					sSection2use = sSystemKeyUsedTemp +IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgramAliasUsed;
					alsReturn.add(sSection2use);
				}
				
				//Baue folgendes: TestProgAlias!Systemnr und TestProgAlias
				ArrayList<String> alsProgAliasUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sProgramAliasUsed, sSystemNumberUsed);
				for(String sProgAliasUsedTemp : alsProgAliasUsed) {
					alsReturn.add(sProgAliasUsedTemp);
				}
			}
		
		    //+++++++++++++++++++++++++++++++++++++							
			//Baue folgendes: Modul!01_TestProg und Modul_TestProg					
			for(String sSystemKeyUsedTemp : alsSystemKeyUsed) {
				sSection2use = sSystemKeyUsedTemp +IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sProgramUsed;
				alsReturn.add(sSection2use);
			}
							
			//Baue folgendes: TestProg!01 und TestProg
			ArrayList<String> alsProgKeyUsed = AbstractKernelObjectZZZ.computeSystemSectionNames(sProgramUsed, sSystemNumberUsed);				
			for(String sProgKeyUsedTemp : alsProgKeyUsed) {				
				alsReturn.add(sProgKeyUsedTemp);
			}
														
			//Baue folgendes: Modul!01 und Modul
			ArrayList<String> alsModule = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleOrApplicationAliasUsed, sSystemNumberUsed);
			for(String sModuleTemp : alsModule) {
				alsReturn.add(sModuleTemp);
			}
		}//end main:
		alsReturn = (ArrayList<String>) ArrayListUtilZZZ.uniqueKeepLast(alsReturn);
		return alsReturn;
	}
	
	public static String extractSytemNumberFromSection(String sSection) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			if(!StringZZZ.contains(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)) {				
				break main;
			}
			
			//1) FGL!01!program			
			sReturn=StringZZZ.right(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);
			if(StringZZZ.isEmpty(sReturn))break main;
			
			if(StringZZZ.contains(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)) {
				sReturn = StringZZZ.left(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);
				if(StringZZZ.isEmpty(sReturn))break main;
			}
			
			//2) FGL!01_program
			if(StringZZZ.contains(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM)) {
				sReturn = StringZZZ.left(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM);			
			}
			
			//3) Wenn immer noch nicht gefunden worden ist, den Wert selbst zurückgeben.
			if(StringZZZ.isEmpty(sReturn)) {
				sReturn = sSection;
			}
		}//end main:
		return sReturn;
	}
	
	
	public static String extractModuleFromSection(String sSection) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			
			//1) FGL!01!program	
			if(StringZZZ.contains(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)) {
				sReturn = StringZZZ.left(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);
				if(StringZZZ.isEmpty(sReturn))break main;
			}else {
				sReturn = sSection;
			}
						
			//2) FGL_program
			if(StringZZZ.contains(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM)) {
				sReturn = StringZZZ.left(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM);				
			}
			
			//3) Wenn immer noch nicht gefunden worden ist, den Wert selbst zurückgeben.
			if(StringZZZ.isEmpty(sReturn)) {
				sReturn = sSection;
			}
		}
		return sReturn;
	}
	
	public static String extractProgramFromSection(String sSection) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			
			//1) FGL_program				
			if(StringZZZ.contains(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM)) {
				sReturn = StringZZZ.right(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM);				
									
				//2) FGL!01_program!01	
				if(StringZZZ.contains(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)) {
					sReturn = StringZZZ.left(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);				
				}
			}
			
			//2) FGL!01!program
			if(StringZZZ.isEmpty(sReturn)) {				
				if(StringZZZ.count(sSection, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)>=2) {
					sReturn = StringZZZ.right(sReturn, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);				
				}
			}
			
			//3) program   - ja richtig nur der Wert selbst.
			if(StringZZZ.isEmpty(sReturn)) {
				sReturn = sSection;
			}
		}
		return sReturn;
	}
	
	/** Prüft, ob der SectionName ein Ausrufezeichen enthält
	 * @param sSection
	 * @return
	 * @author Fritz Lindhauer, 30.11.2021, 10:27:07
	 * @throws ExceptionZZZ 
	 */
	public static boolean isSystemSection(String sSection) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			
			if(sSection.contains(IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER)) {
				bReturn = true;
			}
		}//end main:
		return bReturn;
	}
	
	/** Prüft, ob der SectionName ein Ausrufezeichen enthält UND danach einen Unterstrich mit einem möglichen Programnamen.
	 * @param sSection
	 * @return
	 * @author Fritz Lindhauer, 11.08.2022, 08:55:28
	 * @throws ExceptionZZZ 
	 */
	public static boolean isSystemSectionWithProgram(String sSectionIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = AbstractKernelObjectZZZ.isSystemSection(sSectionIn);			
			if(!bReturn) break main;
			
			String sSection = AbstractKernelObjectZZZ.computeSectionFromSystemSection(sSectionIn);
			String sRest = StringZZZ.right(sSectionIn, sSection);
			if(StringZZZ.contains(sRest,IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM)) {
				bReturn = true;
			}else {
				bReturn = false;
			}
			
		}//end main:
		return bReturn;
	}
	
	
	/** Eine Systemsection mit Programnamen hat die Form Section!SystemNumber_Program
	 *  also Z.B. JDX!01_IP_CONTEXT
	 *  Diese Methode liefert den Wert hinter dem ersten Unterstrich zurück.
	 * @param sSystemSection
	 * @return
	 * @author Fritz Lindhauer, 11.08.2022, 09:06:13
	 * @throws ExceptionZZZ 
	 */
	public static String computeProgramFromSystemSection(String sSectionIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			boolean bExist = AbstractKernelObjectZZZ.isSystemSectionWithProgram(sSectionIn);			
			if(!bExist) break main;
			
			String sSection = AbstractKernelObjectZZZ.computeSectionFromSystemSection(sSectionIn);
			String sRest = StringZZZ.right(sSectionIn, sSection);						
			sReturn = StringZZZ.right(sRest, IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM);
		}//end main:
		return sReturn;
	}
	
	/** Eine Systemsection hat die Form Section!SystemNumber
	 *  also Z.B. JDX!01
	 *  Diese Methode liefert den Wert vor dem Ausrufezeichen zurück.
	 * @param sSystemKey
	 * @return
	 * @author Fritz Lindhauer, 28.11.2021, 09:25:38
	 * @throws ExceptionZZZ 
	 */
	public static String computeSectionFromSystemSection(String sSystemSection) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sSystemSection)) break main;
			
			sReturn = StringZZZ.left(sSystemSection+KernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER, KernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER);
		}//end main:
		return sReturn;
	}
	
	public String getKernelKey(){
		return "ZZZ";
	}
	
	/** FileFilterModuleZZZ, also einen FileFilter, der beim Auflisten der Dateien file.list(objFileFilter) eingestzt werden kann. Dieser FileFilter soll nur Modul-Ini Dateien erlauben.
	* Lindhauer; 12.05.2006 08:55:47
	 * @return
	 */
	public FileFilterModuleZZZ getFileFilterModule(){
		if(this.objFileFilterModule==null){
			this.objFileFilterModule = new FileFilterModuleZZZ();
		}
		return this.objFileFilterModule;
	}
	public void setFileFilterModule(FileFilter objFileFilter){
		this.objFileFilterModule = (FileFilterModuleZZZ) objFileFilter;
	}
	
	//### Aus Interface IKernelFileIniUserZZZ
	@Override
	public FileIniZZZ getFileConfigKernelIni() throws ExceptionZZZ{
		FileIniZZZ objReturn = this.objFileIniKernelConfig;
		if(objReturn==null && !this.getFlag("INIT")){
			String sKey = this.getApplicationKey();
			objReturn = this.getFileConfigModuleIni(sKey);
			this.objFileIniKernelConfig = objReturn;
		}else if(objReturn!=null && objReturn.getFlag("INIT") && !this.getFlag("INIT")) {
			String sLog=null;
			
			//Nun vollständig initialisieren
			String sDirectoryConfig = this.getFileConfigKernelDirectory();
			String sFileConfig = this.getFileConfigKernelName();			
			String sFilePath = FileEasyZZZ.joinFilePathName(sDirectoryConfig, sFileConfig);					
			boolean bExists = FileEasyZZZ.exists(sFilePath);
			if(!bExists) {
				String sDirectoryConfigDefault = AbstractKernelObjectZZZ.sDIRECTORY_CONFIG_DEFAULT;
				sLog = "Default Filename for configuration does not exist here: '" + sFilePath + "'. Looking in default direcotry '" + sDirectoryConfigDefault + "'" ;
				this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);

				sFilePath = FileEasyZZZ.joinFilePathName(sDirectoryConfigDefault, sFileConfig);						
				bExists = FileEasyZZZ.exists(sFilePath);
				
				if(!bExists) {
					sLog = "Default file in default directory not found: '" + sFilePath + "'";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					//sLog = "Changing directory to '" + sDirectoryConfigDefault + "'";
					//this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					//this.setFileConfigKernelDirectory(sDirectoryConfigDefault);
					sLog = "Directory for other files still used '" + sDirectoryConfig + "'";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);	
				}
			}
			File objFile = new File(sFilePath);
			objReturn.setFileObject(objFile);						
		}else if(objReturn==null && this.getFlag("INIT")){
			objReturn = new FileIniZZZ(); //also Flag init=false
			this.objFileIniKernelConfig = objReturn;
		}else {
			//dann sollte alles fertig sein.
		}
		return objReturn;	
	}
	
	@Override
	public void setFileConfigKernelIni(FileIniZZZ objFileIniKernelConfig){
		this.objFileIniKernelConfig = objFileIniKernelConfig;
	}
	
	//########################################
	
	/** Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn't proof the file existance !!! <CR>
		Gets the Entries starting with 'KernelConfigPath...', 'KernelConfigFile...', where ... is the Alias.
		<CR><CR>
		e.g. for the alias 'Export' the following entries may be found<CR>
		KernelConfigPathExport=c:\tempfgl\KernelConfig<CR>
		KernelConfigFileExport=ZKernelConfigExport_default.ini<CR>
		
	 @date: 26.10.2004
	 @param sAlias
	 @return FileIniZZZ, like getFileConfigByAlias.
	 @throws ExceptionZZZ
	 */
	public FileIniZZZ getFileConfigModuleIni(String sModuleAlias) throws ExceptionZZZ{
		FileIniZZZ objReturn = null;		
				main:{
					check:{
						if(sModuleAlias == null){							
							ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
						if(sModuleAlias.equals("")){							
							ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}						
					}//end check:
					
					String sAlias = AbstractKernelObjectZZZ.extractModuleFromSection(sModuleAlias);
		
					//first, get the Kernel-Configuration-INI-File
					//IniFile objIni = this.getFileConfigKernelAsIni(); //Hier fehlt der Alias...
					//NEIN, das wäre wohl eine Endlosschleife: FileIniZZZ objKernelIni = this.getFileConfigIniByAlias(sAlias);
					IniFile objIni = this.getFileConfigModuleAsIni(sAlias);
					if(objIni==null){
						String sLog = "FileIni missing for Alias in execution Project Path: " + sAlias;
						sLog = ReflectCodeZZZ.getMethodCurrentNameLined() + sLog;
						System.out.println(sLog);
						this.logLineDate(sLog);
						
						//ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						//throw ez;																		
						break main;
					} //20190705: UND ... WAS WIRD NUN MIT DER GEFUNDENEN INI-DATEI gemacht? Neu: Setze die Datei.
																			
					String sFilePathTotal = objIni.getFileName();
					File objFileModule = new File(sFilePathTotal);			
					
					//++++++++++++++++++++++++++++++++++++++++++++++++++++	
					//3. Neues FileIniZZZ Objekt erstellen, ggfs.
					if(this.objFileIniKernelConfig==null){												
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						FileIniZZZ exDummy = new FileIniZZZ();					
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true);//this.getFlagZ_passable(true, exDummy);						
						objReturn = new FileIniZZZ(this, objFileModule, saFlagZpassed);
						
					}else{
						//Übernimm die gesetzten FlagZ...
						//Aber so werden alle Flags geholt, was nicht korrekt ist, denn das sind auch die nicht gesetzten Flags
						//HashMap<String,Boolean>hmFlagZ = this.getFileConfigKernelIni().getHashMapFlagZ();
						//objReturn = new FileIniZZZ(this,  objFileModule, hmFlagZ);
						
						//Uebernimm nur die relevanten Flags, also nur die Flags, die sowohl im Kernel als auch im KernelFile gesetzt sind!!!
						FileIniZZZ exDummy = new FileIniZZZ();
						String[]saFlagRelevant = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true);
						objReturn = new FileIniZZZ(this,  objFileModule, saFlagRelevant);
						
						//Übernimm die gesetzten Variablen...
						HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getFileConfigKernelIni().getHashMapVariable();						
						objReturn.setHashMapVariable(hmVariable);
					}
					
									
					/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pr�fen
					if(objReturn.exists()==false){
						sMethod = this.getMethodCurrentName();
						ExceptionZZZ ez = new ExceptionZZZ("File not found '" + sFilePath + "\\" + sFileName + "'",101, this, sMethod, null );
						throw ez;		
					}
					*/
					//20210421: Nein, damit würde die Hauptkernelkonfiguratin ggfs. durch eine Modulkonfiguration überschrieben
					//          this.setFileConfigIni(objReturn);
					
					
				}//end main:
		return objReturn;
	}
	
	/** Nur fuer die Arbeit in einer Entwicklungsumgebung. 
	 *  Hier werden dann ggfs. andere Projektordner durchsucht.
	 *  Die Bestimmung der Projektordner erfolgt über die Kernel-Konfiguration und darin vorhandenene Methoden.
	  
	Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn't proof the file existance !!! <CR>
	Gets the Entries starting with 'KernelConfigPath...', 'KernelConfigFile...', where ... is the Alias.
	<CR><CR>
	e.g. for the alias 'Export' the following entries may be found<CR>
	KernelConfigPathExport=c:\tempfgl\KernelConfig<CR>
	KernelConfigFileExport=ZKernelConfigExport_default.ini<CR>
	
 @date: 26.10.2004
 @param sAlias
 @return FileIniZZZ, like getFileConfigByAlias.
 @throws ExceptionZZZ
 */
public FileIniZZZ getFileConfigModuleIniInWorkspace(IKernelConfigZZZ objConfig, String sModuleAlias) throws ExceptionZZZ{
	FileIniZZZ objReturn = null;		
			main:{
				if(objConfig==null) {
					ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
				if(!objConfig.isInIDE()) break main;
				
				check:{
					if(sModuleAlias == null){							
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(sModuleAlias.equals("")){							
						ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}						
				}//end check:
				
				String sAlias = AbstractKernelObjectZZZ.extractModuleFromSection(sModuleAlias);
	
				//first, get the Kernel-Configuration-INI-File
				//IniFile objIni = this.getFileConfigKernelAsIni(); //Hier fehlt der Alias...
				//NEIN, das wäre wohl eine Endlosschleife: FileIniZZZ objKernelIni = this.getFileConfigIniByAlias(sAlias);
				IniFile objIni = this.getFileConfigModuleAsIniInWorkspace(objConfig, sAlias);
				if(objIni==null){
					String sLog = "FileIni missing for Alias in IDE Workspace Project Path: " + sAlias;
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					//ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					//throw ez;																		
					break main;
				} //20190705: UND ... WAS WIRD NUN MIT DER GEFUNDENEN INI-DATEI gemacht? Neu: Setze die Datei.
																		
				String sFilePathTotal = objIni.getFileName();
				File objFileModule = new File(sFilePathTotal);			
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++	
				//3. Neues FileIniZZZ Objekt erstellen, ggfs.
				if(this.objFileIniKernelConfig==null){												
					HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
					FileIniZZZ exDummy = new FileIniZZZ();					
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true);//this.getFlagZ_passable(true, exDummy);						
					objReturn = new FileIniZZZ(this, objFileModule, saFlagZpassed);
					
				}else{
					//Übernimm die gesetzten FlagZ...
					//Aber so werden alle Flags geholt, was nicht korrekt ist, denn das sind auch die nicht gesetzten Flags
					//HashMap<String,Boolean>hmFlagZ = this.getFileConfigKernelIni().getHashMapFlagZ();
					//objReturn = new FileIniZZZ(this,  objFileModule, hmFlagZ);
					
					//Uebernimm nur die relevanten Flags, also nur die Flags, die sowohl im Kernel als auch im KernelFile gesetzt sind!!!
					FileIniZZZ exDummy = new FileIniZZZ();
					String[]saFlagRelevant = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true);
					objReturn = new FileIniZZZ(this,  objFileModule, saFlagRelevant);
					
					//Übernimm die gesetzten Variablen...
					HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getFileConfigKernelIni().getHashMapVariable();						
					objReturn.setHashMapVariable(hmVariable);
				}
				
								
				/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pr�fen
				if(objReturn.exists()==false){
					sMethod = this.getMethodCurrentName();
					ExceptionZZZ ez = new ExceptionZZZ("File not found '" + sFilePath + "\\" + sFileName + "'",101, this, sMethod, null );
					throw ez;		
				}
				*/
				//20210421: Nein, damit würde die Hauptkernelkonfiguratin ggfs. durch eine Modulkonfiguration überschrieben
				//          this.setFileConfigIni(objReturn);
				
				
			}//end main:
	return objReturn;
}

	@Override
	public String searchAliasForModule(String sModule) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(sModule == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sModule.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			FileIniZZZ objFileConfigIni = this.getFileConfigModuleIni(sModule);
			String sApplicationOrModule = this.getApplicationKey();
			IKernelConfigSectionEntryZZZ entry = KernelSearchAliasForModule_(objFileConfigIni, sModule, sApplicationOrModule);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
			
			sReturn = entry.getValue();
		}//end main:
		return sReturn;
	}
	
	
	public static String searchAliasForModule(IKernelFileIniZZZ objFileConfigIni, String sModule, String sSystemNumber) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(objFileConfigIni == null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniZZZ'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
					
			//Suche den Modulalias in der übergebenen Datei 
			IKernelConfigSectionEntryZZZ entry = AbstractKernelObjectZZZ.searchModuleAliasFor(objFileConfigIni, sModule, sSystemNumber, null);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
			
			sReturn = entry.getValue();
		}//end main:
		return sReturn;
	}
	
	public static String searchAliasForModule(IKernelFileIniZZZ objFileConfigIni, String sModule, String sApplicationOrModule, String sSystemNumber) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(objFileConfigIni == null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniZZZ'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if(sApplicationOrModule == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sApplicationOrModule.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
						
			//Suche den Modulalias in der übergebenen Datei 
			IKernelConfigSectionEntryZZZ entry = AbstractKernelObjectZZZ.searchModuleAliasFor(objFileConfigIni, sModule, sApplicationOrModule, sSystemNumber, null);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
			
			sReturn = entry.getValue();
		}//end main:
		return sReturn;
	}
	
	public static String searchAliasForProgram(IKernelFileIniZZZ objFileConfigIni, String sProgram, String sApplicationOrModule, String sSystemNumber) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(objFileConfigIni == null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniZZZ'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if(sProgram == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sProgram.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Program'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			 
			IKernelConfigSectionEntryZZZ entry = AbstractKernelObjectZZZ.searchProgramAliasFor(objFileConfigIni, sProgram, sApplicationOrModule, sSystemNumber, null);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
			
			sReturn = entry.getValue();
		}//end main:
		return sReturn;
	}
	
	@Override
	public String searchFileConfigIniNameByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ {
		String sReturn="";
		main:{
			check:{
				if(objIni == null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'inifile'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sAlias == null){							
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sAlias.equals("")){							
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}						
			}//end check:
		
			IKernelConfigSectionEntryZZZ objEntryFileName = this.searchPropertyByAliasOLDDIRECT(objIni, sAlias,IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);					
			if(!objEntryFileName.hasAnyValue()){											
				//20191029: Den Alias ggfs. normieren, d.h. erst als alias mit Systemkey !01 ... Dann also alias ohne System key, d.h. vor dem !.
				//Das Problem ist sonst, dass die Property KernelConfigFileOVPN!01 heissen muss, für das System01.
				//Der PropertyName sollte aber über alle Systeme gleich sein.
				//DAHER DARF KEINE PROPERTY DEN "!SYSTEMNUMBER" IM NAMEN HABEN. 
				//ABER DER APPLICATIONKEY IST ERLAUBT
				
				String sApplicationKey = this.getApplicationKey();
				objEntryFileName = this.searchPropertyByAliasOLDDIRECT(objIni, sAlias,IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX+sApplicationKey);
				if(!objEntryFileName.hasAnyValue()){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Kein Dateiname konfiguriert für den Alias  '" + sAlias + "' in Datei '" + objIni.getFileName() +"'");	
					break main;
				}
			}
			sReturn = objEntryFileName.getValue();
		}//end main:		
		return sReturn;
	}
	
	@Override
	public String searchFileConfigIniPathByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ {
		String sReturn="";
		main:{
			check:{
				if(sAlias == null){							
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sAlias.equals("")){							
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}						
			}//end check:
		
			String sFilePathUsed = null;
			IKernelConfigSectionEntryZZZ objEntryFilePath = this.searchPropertyByAliasOLDDIRECT(objIni, sAlias,IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX);
			if(!objEntryFilePath.hasAnyValue()){
				//20191029: Den Alias ggfs. normieren, d.h. erst als alias mit Systemkey !01 ... Dann also alias ohne System key, d.h. vor dem !.
				//Das Problem ist sonst, dass die Property KernelConfigFileOVPN!01 heissen muss, für das System01.
				//Der PropertyName sollte aber über alle Systeme gleich sein.
				//DAHER DARF KEINE PROPERTY DEN "!SYSTEMNUMBER" IM NAMEN HABEN. 
				//ABER DER APPLICATIONKEY IST ERLAUBT
								
				String sApplicationKey = this.getApplicationKey();
				objEntryFilePath = this.searchPropertyByAliasOLDDIRECT(objIni, sAlias,IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX+sApplicationKey);
				if(!objEntryFilePath.hasAnyValue()){						
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Kein DateiPfad konfiguriert für den Alias  '" + sAlias + "' in Datei '" + objIni.getFileName() +"'");	
					//break main; Auch mit leerem Dateipfad weitermachen....
				}else if(objEntryFilePath.hasNullValue()){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+": KernelConfigPath für den Alias '" + sAlias + "' ist ABSICHTLICH NULL. Erwarte die Datei im Projektordner.");
					File objDirProject = FileEasyZZZ.searchDirectory(null);
					sFilePathUsed = objDirProject.getAbsolutePath();
				}else{						
					sFilePathUsed = objEntryFilePath.getValue();
				}
			}else {
				sFilePathUsed = objEntryFilePath.getValue();
			}
			
			//NEIN, Weitermachen if(StringZZZ.isEmpty(sFilePathUsed)) break main;
			File objDirTemp = FileEasyZZZ.searchDirectory(sFilePathUsed);
			if(objDirTemp==null){
				String sLog = "Directory with name '" + sFilePathUsed + "' not found.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez  = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_VALUE, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			sReturn = objDirTemp.getAbsolutePath();
		}//end main:
		
		return sReturn;
	}
	
	/** Merke: Arbeit in anderen Projektordnern einer Entwicklungsumgebung ist aktuell nur mit Hilfe des ConfigObjekts und darin enthaltenere Einstellungen abprüfbar.
	 * @param sModule
	 * @param bExistingOnly
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 24.06.2023, 12:32:15
	 */
	@Override
	public FileIniZZZ searchModuleFileByModule(String sModule, boolean bExistingOnly) throws ExceptionZZZ {
		FileIniZZZ objReturn = null;
		main:{								
			objReturn = this.searchModuleFileByModule_(sModule, bExistingOnly);
		}//end main:
		return objReturn;
	}
	
	@Override
	public FileIniZZZ searchModuleFileByModuleInWorkspace(String sModule, boolean bExistingOnly) throws ExceptionZZZ {
		FileIniZZZ objReturn = null;
		main:{			
			IKernelConfigZZZ  objConfig = this.getConfigObject();
			objReturn = this.searchModuleFileByModuleInWorkspace_(objConfig, sModule, bExistingOnly);
		}//end main:
		return objReturn;
	}
	
	@Override
	public FileIniZZZ searchModuleFileWithProgramAlias(String sModule, String sProgramOrSection) throws ExceptionZZZ {
		FileIniZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sModule)){							
				ExceptionZZZ ez = new ExceptionZZZ("'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sProgramOrSection)){							
				ExceptionZZZ ez = new ExceptionZZZ("'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			objReturn = this.searchModuleFileByModuleWithProgramSection_(sModule, sProgramOrSection);
		}//end main:
		return objReturn;
	}
	
	/** File[], returns all files of a directory, which matches the Kernel File Filter
	* Lindhauer; 12.05.2006 09:16:05
	 * @param sDir
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public File[] getFileConfigModuleAllByDir(String sDirIn) throws ExceptionZZZ{
		File[] objaReturn=null;
		main:{
			String sDir;
			check:{
				if(sDirIn==null)break main;				
				if(sDirIn.equals("")){
					sDir=".";
				}else{
					sDir = sDirIn;
				}			
				//Das ist nun m�glich. Konfigurationsdatein werden dirkt in den Projektordner abgelegt.   if(sDir.equals("")) break main;
			}//END check:
			
//			Das Verzeichnis holen
		File objDir = new File(sDir);
			if(objDir.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("The provided string is no directory",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		//Den Modul-FileFilter holen
		FileFilterModuleZZZ objFilter = this.getFileFilterModule();
		if(objFilter==null){
			ExceptionZZZ ez = new ExceptionZZZ("Unable to receive the Kernel File Filter for Module files",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		//Den Modul-FileFilter einsetzen		
		objaReturn  = objDir.listFiles(objFilter);
		
		}//END main:
		return objaReturn;
	}
	
	/** Reads in  the Kernel-Configuration-File the,directory and name information. Returns a file object. But: Doesn�t proof the file existance !!! <CR>
		Gets the Entries starting with 'KernelConfigPath...', 'KernelConfigFile...', where ... is the Alias.
		<CR><CR>
		e.g. for the alias 'Export' the following entries may be found<CR>
		KernelConfigPathExport=c:\tempfgl\KernelConfig<CR>
		KernelConfigFileExport=ZKernelConfigExport_default.ini<CR>

		 @author 0823 , date: 18.10.2004
		 @param sAlias
		 @return File-Object, Existance is not proofed, null: if not configured.
		 @throws ExceptionZZZ
		 */
	public File getFileConfigModuleOLDDIRECT(String sAlias) throws ExceptionZZZ{
			File objReturn = null;
			main:{
						check:{
							if(StringZZZ.isEmpty(sAlias)){								
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}						
						}//end check:
								
						//1. Hole den Dateinamen
						IKernelConfigSectionEntryZZZ objEntryFileName = this.searchPropertyByAliasOLDDIRECT(sAlias,IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
						if(!objEntryFileName.hasAnyValue()) break main;
						String sFileName = objEntryFileName.getValue();
						
						//2. Hole den Dateipfad
						IKernelConfigSectionEntryZZZ objEntryFilePath = this.searchPropertyByAliasOLDDIRECT(sAlias,IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX);
						//Auch wenn der Dateipfad nicht gepflegt ist weiterarbeiten. Es wird dann ein Standard genommen. if(!objEntryFilePath.hasAnyValue()) break main;
						String sFilePath = objEntryFilePath.getValue();
						
						/* Achtung: Es ist nicht Aufgabe dieser Funktion die Existenz der Datei zu pruefen*/					
						//FGL 20181008: Relative Fileangaben verarbeitet und Suche auf dem Classpath (z.B. wg. verpackt in .war / .jar Datei, z.B. WebService - Fall.
						objReturn = FileEasyZZZ.searchFile(sFilePath, sFileName); 
						
				}//end main:
			return objReturn;
		}
	
	@Override
	public File getFileConfigModule(String sModuleAlias) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			FileIniZZZ objFileConfigIni = this.getFileConfigModuleIni(sModuleAlias);
			if(objFileConfigIni==null) break main;
			
			objReturn = objFileConfigIni.getFileObject();			
		}//end main:
		return objReturn;
	}
	
	private String KernelChooseMainSectionUsedForConfigFile_(String sMainSection, String sProgramOrSection) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(!StringZZZ.isEmpty(sMainSection)){
				if(sMainSection.equals(sProgramOrSection)){
					sReturn = this.getApplicationKey();					
				}else{					
					String sModuleAlias = this.searchAliasForModule(sMainSection);
	    			if(StringZZZ.isEmpty(sModuleAlias)) {
	    				sReturn = sMainSection;
	    			}else {
	    				sReturn = sModuleAlias;
	    			}					
				}
				break main;				
			}
			
			sReturn = this.getApplicationKey();			
		}
		return sReturn;
	}
	
	/**Wird der Wert darin gefunden, dann hat man den gültigen Alias gefunden.
	   Mit diese gültigen ModulAlias kann dann später die korrekte Ini-Datei für die Modulkonfiguration geholt werden.
	   Merke: Das Vorhandensein der Datei wird geprüft.	   
	 * @param sModule, z.B. der Klassenname eines Panels oder einer Dialogbox, halt einer Komponenten die IModuleZZZ implementiert.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 21.04.2021, 10:10:26
	 */
	private IKernelConfigSectionEntryZZZ KernelSearchAliasForModule_(FileIniZZZ objFileConfigIni, String sModule, String sApplicationOrModule) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn=new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		
		String sSectionCacheUsed = sApplicationOrModule; 
		String sPropertyCacheUsed = sModule; //this.getSystemKey();
		
		IKernelConfigSectionEntryZZZ objFound=null;boolean bExistsInCache=false;
		main:{
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
			ICachableObjectZZZ objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
			if(objFromCache!=null){					
					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
					bExistsInCache=true;
					objFound = (IKernelConfigSectionEntryZZZ) objFromCache;
					break main;				
			}else{
				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
						
			//TODOGOON20220816;//Warum zuerst alle Module holen?
			
			                 //Algorithmus:
			                 //Hole das in den Systemkeys definierte Modul.
			                 //
							 //Erst wenn das nicht gefunden worden ist... 
			                 //Annahme, dass der Alias selbst übergeben worden ist.
			                 //Dann alle Module holen und darin nach dem Alias suchen
			                 
			                 //Hat man eine Datei gefunden,
			                 //prüfe auf "Vorhandensein"
			                 //prüfe darauf, ob der Alias als Systemkey darin vorhanden ist.
			
			//###############################################			
			objFound = AbstractKernelObjectZZZ.searchModuleAliasFor(objFileConfigIni, sModule, sApplicationOrModule, sSystemNumber, hmDebug);			
			if(objFound==null) {
				String stemp = "ENDE DIESER SUCHE NACH MODULALIAS OHNE ERFOLG (NULL) +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}else if(!objFound.hasAnyValue()){    			
		    	String stemp = "ENDE DIESER SUCHE NACH MODULALIAS OHNE ERFOLG +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}		   	
		}//end main:
		if(objFound!=null && objFound.hasAnyValue() ){
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH MODULALIAS +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
    		
    		//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			if(!bExistsInCache) this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}
		return objReturn;								
	}
	
	/**Wird der Wert darin gefunden, dann hat man den gültigen Alias gefunden.
	   Mit diese gültigen ModulAlias kann dann später die korrekte Ini-Datei für die Modulkonfiguration geholt werden.
	   Merke: Das Vorhandensein der Datei wird geprüft.	   
	 * @param sModule, z.B. der Klassenname eines Panels oder einer Dialogbox, halt einer Komponenten die IModuleZZZ implementiert.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 21.04.2021, 10:10:26
	 */
	private IKernelConfigSectionEntryZZZ KernelSearchAliasForProgram_(String sApplicationOrModule, String sProgram) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn= new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		
		check:{
			if(StringZZZ.isEmpty(sApplicationOrModule)) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}
		
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		
		String sSectionCacheUsed = sApplicationOrModule; 
		String sPropertyCacheUsed = sProgram;
		
		IKernelConfigSectionEntryZZZ objFound=null;boolean bExistsInCache=false;
		main:{
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
			ICachableObjectZZZ objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
			if(objFromCache!=null){					
					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
					bExistsInCache = true;
					objFound = (IKernelConfigSectionEntryZZZ) objFromCache;
					break main;				
			}else{
				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			//### weiter gehts ohne CACHE Erfolg			
			objFound = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			objFound.setProperty(sPropertyCacheUsed);
			objFound.setProperty(sPropertyCacheUsed);
			
			//###############################################
			//Hole die Konfigurationsdatei des Moduls
			File objFileIni = this.getFileConfigModuleOLDDIRECT(sApplicationOrModule);
			FileIniZZZ objFileConfigIni = new FileIniZZZ(this, objFileIni);
			
			objFound = AbstractKernelObjectZZZ.searchProgramAliasFor(objFileConfigIni, sProgram, sApplicationOrModule, sSystemNumber, hmDebug);		
			if(objFound==null) {
				String stemp = "ENDE DIESER SUCHE NACH PROGRAMALIAS OHNE ERFOLG (NULL) +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}else if (!objFound.hasAnyValue()){    			
		    	String stemp = "ENDE DIESER SUCHE NACH PROGRAMALIAS OHNE ERFOLG +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}		   	
		}//end main:
		if(objFound!=null && objFound.hasAnyValue() ){
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH PROGRAMALIAS +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
 		
			//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			if(!bExistsInCache) this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}
		return objReturn;								
	}
	
	
	private FileIniZZZ searchModuleFileByModuleWithProgramSection_(String sModule, String sProgramOrSection) throws ExceptionZZZ{
		FileIniZZZ objReturn=null;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		
		String sSectionCacheUsed = sModule;
		String sPropertyCacheUsed = sProgramOrSection;
		
		boolean bExistsSection=false;ICachableObjectZZZ objFromCache=null;
		FileIniZZZ objFound = null;boolean bExistsInCache=false;
		main:{
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
			objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
			if(objFromCache!=null){					
					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
					bExistsInCache = true;
					objFound = (FileIniZZZ) objFromCache;
					break main;				
			}else{
				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			
			//FileIniZZZ objFileConfigIni = this.getFileConfigKernelIni();
			
			
			//Nun alle Ausprägungen der Systemkey in eine ArrayList packen
			String sApplicationKeyUsed = this.getApplicationKey();
			String sSystemNumberUsed = this.getSystemNumber();

			//#############################
			objFound = this.searchModuleFileByModule(sModule, true);			
			if(objFound==null){
				
				//Falls das Modul in einem anderen Workspace Projekt definiert ist.
				objFound = this.searchModuleFileByModuleInWorkspace(sModule, true);
				if(objFound==null){				
					ExceptionZZZ ez = new ExceptionZZZ("Missing configurationfile. No file defined or available for Module '" + sModule + "' or Application '" + sApplicationKeyUsed + "' and Systemnumber '" + sSystemNumberUsed + "'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else {
				hmDebug.put("Searching in file", objFound.getFileObject().getAbsolutePath());
			}
			
			
			//TODOGOON; //Aufruf der static Methode
			//NEIN, erst einmal wäre das zuviel   objReturn = KernelKernelZZZ.searchModuleFileByModuleWithProgramSection(objFileConfigIni, sProgramOrSection, sModule, sApplicationKey, sSystemNumber, hmDebug);			
			
			//Mit STATIC METHODE WÄRE DANN FOLGENDER CODE ÜBERFLÜSSIG !!!!!!
			ArrayList<String>listasProgramOrSection = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFound, sProgramOrSection, sModule, sApplicationKeyUsed, sSystemNumberUsed);			
			for(String sProgramOrSectionTemp : listasProgramOrSection) {
				try{
					hmDebug.put("ProgramOrSection", sProgramOrSectionTemp);
					
					bExistsSection = objFound.proofSectionExistsDirectLookup(sProgramOrSectionTemp);
					if(bExistsSection) break main;
					
					}catch(ExceptionZZZ ez2){
			    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Versuch über ProgramOrSection '" + sProgramOrSectionTemp + "' schlägt fehl. ...");
			    	}
				}//end for	    				   					  
		}//end main:	
		if(!bExistsSection && objFound==null) {
			String stemp = "ENDE DIESER SUCHE NACH FileIniZZZ für das Modul OHNE ERFOLG. Konfigurationsfile für das Modul nicht gefunden +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//			ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		}else if((!bExistsInCache && !bExistsSection) && objFound != null) {
			String stemp = "ENDE DIESER SUCHE NACH FileIniZZZ für das Modul OHNE ERFOLG. Section existiert nicht im gefundenen Konfigurationsfile. +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
    		
		}else {
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH FileIniZZZ +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + stemp);
    		
    		//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			if(!bExistsInCache) this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}
		return objReturn;								
	}
	
	private FileIniZZZ searchModuleFileByModule_(String sModule, boolean bExistingOnly) throws ExceptionZZZ{
		FileIniZZZ objReturn=null;		
		FileIniZZZ objFound = null;boolean bExists=false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		main:{
			if(StringZZZ.isEmpty(sModule)){							
				ExceptionZZZ ez = new ExceptionZZZ("'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			
//			//############################
//			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
//			objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
//			if(objFromCache!=null){					
//					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
//					objFound = (FileIniZZZ) objFromCache;
//					break main;				
//			}else{
//				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
//			}
						
			//Nun alle Ausprägungen der Systemkey in eine ArrayList packen
			String sApplicationKeyUsed = this.getApplicationKey();
			String sSystemNumberUsed = this.getSystemNumber();


			bymodule:{
				ArrayList<String>alsModuleKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sModule,sSystemNumberUsed);
				for(String sModuleKey : alsModuleKey) {
					//Hole das Modulfile
					objFound = this.getFileConfigModuleIni(sModuleKey);
					if(objFound!=null) {
						//Existiert diese Datei?
						if(bExistingOnly) {
							bExists = FileEasyZZZ.exists(objFound.getFileObject());
							if(bExists) {
								break bymodule;						
							}
						}else {
							break bymodule;
						}
					}
				}								
					
				byapplication:{
					ArrayList<String>alsApplicationKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKeyUsed,sSystemNumberUsed);
					for(String sApplicationKey : alsApplicationKey) {
						//Hole das Applicationfile
						objFound = this.getFileConfigModuleIni(sApplicationKey);
						if(objFound!=null) {
							//Existiert diese Datei?
							if(bExistingOnly) {
								bExists = FileEasyZZZ.exists(objFound.getFileObject());
								if(bExists) {
									break bymodule;						
								}
							}else {
								break bymodule;
							}
						}
					}
					
				}//end byapplication:
			}//end bymodule:	
			
			
			
		}//end main:	
		if(objFound==null) {
			String stemp = "ENDE DIESER SUCHE NACH FileIniZZZ für das Modul im Project Excecution Pfad OHNE ERFOLG. Konfigurationsfile für das Modul nicht gefunden +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + stemp);
			
			this.logLineDateWithPosition(stemp);
			
//			ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		}else {
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH FileIniZZZ  für das Modul im Project Excecution Pfad +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
    		
    		//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			//this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}
		return objReturn;								
	}
	

	private FileIniZZZ searchModuleFileByModuleInWorkspace_(IKernelConfigZZZ objConfig, String sModule, boolean bExistingOnly) throws ExceptionZZZ{
		FileIniZZZ objReturn=null;				
		FileIniZZZ objFound = null;boolean bExists=false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		main:{												
			if(objConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;
			
			if(StringZZZ.isEmpty(sModule)){							
				ExceptionZZZ ez = new ExceptionZZZ("'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
				
//			//############################
//			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST			
//			objFromCache = (ICachableObjectZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
//			if(objFromCache!=null){					
//					hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
//					objFound = (FileIniZZZ) objFromCache;
//					break main;				
//			}else{
//				hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
//			}
					

			//Nun alle Ausprägungen der Systemkey in eine ArrayList packen
			String sApplicationKeyUsed = this.getApplicationKey();
			String sSystemNumberUsed = this.getSystemNumber();


			bymodule:{
				ArrayList<String>alsModuleKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sModule,sSystemNumberUsed);
				for(String sModuleKey : alsModuleKey) {
					//Hole das Modulfile
					objFound = this.getFileConfigModuleIniInWorkspace(objConfig, sModuleKey);
					if(objFound!=null) {
						//Existiert diese Datei?
						if(bExistingOnly) {
							bExists = FileEasyZZZ.exists(objFound.getFileObject());
							if(bExists) {
								break bymodule;						
							}
						}else {
							break bymodule;
						}
					}
				}								
					
				byapplication:{
					ArrayList<String>alsApplicationKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKeyUsed,sSystemNumberUsed);
					for(String sApplicationKey : alsApplicationKey) {
						//Hole das Applicationfile
						objFound = this.getFileConfigModuleIniInWorkspace(objConfig, sApplicationKey);
						if(objFound!=null) {
							//Existiert diese Datei?
							if(bExistingOnly) {
								bExists = FileEasyZZZ.exists(objFound.getFileObject());
								if(bExists) {
									break bymodule;						
								}
							}else {
								break bymodule;
							}
						}
					}
					
				}//end byapplication:
			}//end bymodule:	
			
			
			
		}//end main:	
		if(objFound==null) {
			String stemp = "ENDE DIESER SUCHE NACH FileIniZZZ für das Modul im Project Excecution Pfad OHNE ERFOLG. Konfigurationsfile für das Modul nicht gefunden +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
//			ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		}else {
			String stemp = "ERFOLGREICHES ENDE DIESER SUCHE NACH FileIniZZZ  für das Modul im Project Excecution Pfad +++ Suchpfad: " + hmDebug.computeDebugString("\t|", ":");
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + stemp);
    		
    		//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			//this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}
		return objReturn;								
	}
	
	private IKernelConfigSectionEntryZZZ searchPropertyByAliasOLDDIRECT(String sAlias, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
			if(StringZZZ.isEmpty(sAlias)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			if(StringZZZ.isEmpty(sProperty)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			IniFile objIni = this.getFileConfigKernelAsIni();			
			objReturn = this.searchPropertyByAliasOLDDIRECT(objIni, sAlias, sProperty);
		}//end main:
		return objReturn;		
	}
		
	
	/** Heuristische Lösung. 
	 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
	 * @return
	 * @author lindhaueradmin, 07.11.2018, 07:26:27
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean isOnServer() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			IKernelConfigZZZ objConfig = this.getConfigObject();
			bReturn = objConfig.isOnServer();
		}
		return bReturn;
	}
	
	private IKernelConfigSectionEntryZZZ searchPropertyByAliasOLDDIRECT(IniFile objIni, String sAlias, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug = ""; 
		
		main:{
			check:{
			if(objIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'IniFile'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sAlias)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			if(StringZZZ.isEmpty(sProperty)){								
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//end check:

		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Verwende als ini-Datei für die Suche '"+ objIni.getFileName() + "'.");
		
			//Merke 20190119: Wird ein "empty" Tag in der ZFormel Sprache definiert. Dieser muss hier ausgewertet werden und auch den Abbruch bedingen, wenn er gefunden wird.
		    //Merke:               Das bedeutet eine Beschleunigung . Ein Leerstring bedingt nämlich, dass weitergesucht wird.
		    String sValueFound=null;
		   
			//1. Versuch: Speziell für die Komponente/das Modul definiert
			String sSection = sAlias;
			String sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound=objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);				
				objReturn.setValue(sValueFound);
				break main;
			}
				
			//2.1a. Versuch: Speziel für das System konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getSystemKey();		
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						//this.setValueRaw(sReturnRaw);
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						//this.setValueRaw(null);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.1b. Versuch: Speziel für das System konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getSystemKey();		
			sPropertyUsed = sProperty + sAlias;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						//this.setValueRaw(sReturnRaw);
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						//this.setValueRaw(null);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.2a. Versuch: Global für die Applikation konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{				
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//2.2b. Versuch: Global für die Applikation konfiguriert, mit dem Modulnamen (der dann ggfs. einer Klasse und deren Package entspricht)
			//                                                                         also z.B. KernelConfigFilebasic.zBasic.util.log.ReportLogZZZ
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty + sAlias;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{				
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//3a. Versuch: Speziell für ein System konfiguriert.
			sSection =  this.getSystemKey();
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed );	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setSection(sSection);
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
					
			//3b. Versuch: Global für die Applikation konfiguriert
			sSection = this.getApplicationKey();
			sPropertyUsed = sProperty;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed );
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
					
						
			//4a. Versuch: Global für die Applikation konfiguriert, mit dem SystemKey
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty + this.getSystemKey();
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
							
			//4b. Versuch: Global für die Applikation konfiguriert, mit dem Applikationskey
			sSection = this.getApplicationKey();		
			sPropertyUsed = sProperty +this.getApplicationKey();
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sPropertyUsed + "'");
			hmDebug.put(sSection, sPropertyUsed);
			sValueFound =objIni.getValue(sSection,sPropertyUsed);	
			if(!StringZZZ.isEmpty(sValueFound)) {		
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value gefunden für Property '" + sPropertyUsed + "'=''" + sValueFound + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
				break main;
			}
			
			//Falls irgendwann einmal ein Leerstring (oder ähnliches) gefunden wurde diesen zurückliefern und nicht NULL
			//TODO 20190804: Braucht man diesen Zweig noch, oder kann er weg?
			if(objReturn.hasAnyValue()){
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Zu guter Letzt. Erstmals gefundener Value (section='" + objReturn.getSection() + "') anwenden für Property '" + objReturn.getProperty() + "'=''" + objReturn.getValue() + "'");
				if(this.getFlag("useFormula")==true){
					String sReturnRaw = sValueFound;
					sValueFound = KernelZFormulaIniConverterZZZ.getAsStringStatic(sReturnRaw);  //Auch ohne Formelauswertung die gefundenen Werte zumindest übersetzen
					if(!StringZZZ.equals(sValueFound,sReturnRaw)){
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sReturnRaw + "' nach '" + sValueFound +"'");
						objReturn.setRaw(sReturnRaw);
						objReturn.isExpression(true);
					}else{
						objReturn.setRaw(null);
						objReturn.isExpression(false);
					}
				}
				objReturn.setSection(sSection);
				objReturn.setProperty(sPropertyUsed);
				objReturn.setValue(sValueFound);
			}
		}//end main:
		
		sDebug = hmDebug.computeDebugString(" | ",":");
		if(objReturn.hasAnyValue()){
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug); 
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}
		return objReturn;
	}
	
	
	/** Parameter für ein ganzes Modul holen, aus der Modul-.ini-Datei, die erst noch hier ermittelt werden muss.
	 * Ein Modul ist der Teil, der zwischen dem KernelConfigPath und dem Gleichheitszeichen steht
	 * KernelConfigPath...=,
	 *  bzw. auch KernelConfigFile...=
	 *  Die drei Punkte ... stehen nun für den Modulnamen.
	 *        
	 * Dies ist z.B. der Aliasname für ein 'Program'-Teil
	 * Um für das Program einen Parameter zu holen muss verwendet werden "getParameterByProgramAlias(,,,)
	 * @param objFileConfig, Modul-Konfigurationsfile. Wird z.B. durch  objKernel.getFileConfigByAlias(sModuleAlias); ermittelt.
	 * @param sModuleAlias
	 * @param sParameter
	 * @return String. Wert hinter dem Gleichheitszeichen
	 * @throws ExceptionZZZ
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByModuleAlias(String sModuleAlias, String sParameter)  throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			check:{
				if(sModuleAlias == null){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(sModuleAlias.equals("")){
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: Module alias",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;						
				}															
				
				if(sParameter==null){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(sParameter.equals("")){
					ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end check:

		//+++ First get the Module-Ini-File
		//File objFileModule = this.getFileConfigModuleOLDDIRECT(sModuleAlias);
		File objFileModule = this.getFileConfigModule(sModuleAlias);
		if(objFileModule==null){
			ExceptionZZZ ez = new ExceptionZZZ("Configuration file not configured for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	
		};
		if(objFileModule.exists()==false){
			ExceptionZZZ ez = new ExceptionZZZ("Configuration file '" + objFileModule.getAbsolutePath() + "' not exists for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;		
		}
		if(objFileModule.length()==0){
			ExceptionZZZ ez = new ExceptionZZZ("Configuration file '" + objFileModule.getAbsolutePath() + "' not filled for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
																										
		//+++ Now use another method
		objReturn = this.getParameterByModuleFile(objFileModule, sModuleAlias, sParameter);

	}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
	
	@Override
	public IKernelConfigSectionEntryZZZ[] getParameterByModuleAliasAsArray(String sModule, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}					
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//END check:
				  
			IKernelConfigSectionEntryZZZ objReturn = this.getParameterByModuleAlias(sModule, sProperty);
			
			//Aber das wäre nur ein Stringwert, diesen versuchen zu splitten.
			try {
				objaReturn = KernelConfigSectionEntryZZZ.explode(objReturn, "");
			} catch (CloneNotSupportedException e) {			
				e.printStackTrace();
			}
		}//END main:
		return objaReturn;
	}
	
	@Override
	public synchronized void setParameterByModuleAlias(String sModuleAlias, String sParameter, String sValue, boolean bSaveImmediate) throws ExceptionZZZ{
		main:{
		check:{
			if(sModuleAlias == null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sModuleAlias.equals("")){
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: Module alias",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;						
			}															
			
			if(sParameter==null){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sParameter.equals("")){
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end check:

		this.KernelSetParameterByModuleAlias_(null, sModuleAlias, null, sParameter, sValue, bSaveImmediate);
		
		/*
		//+++ First get the Module-Ini-File
		File objFileModule = this.getFileConfigByAlias(sModuleAlias);
		if(objFileModule==null) break main;
		if(objFileModule.exists()==false){
			ExceptionZZZ ez = new ExceptionZZZ("Configuration file not found for the module'" + sModuleAlias + "'",iERROR_CONFIGURATION_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;		
		}
		
		//first, get the Ini-file-object
		HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
		hmFlag.put(FileIniZZZ.FLAGZ.USEFORMULA.name(), true);
		FileIniZZZ objIni = new FileIniZZZ(this,  objFileModule, hmFlag);
		String sSection = this.getSystemKey();
		objIni.setPropertyValue(sSection, sParameter, sValue, bSaveImmediate);
*/
	
		}//END main
		
	}
	
	private boolean KernelSetParameterByModuleAlias_(FileIniZZZ objFileIniConfigIn, String sModule, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
		
			bReturn = KernelSetParameterByProgramAlias_(objFileIniConfigIn, sModule, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);
						
			}//END main:
		return bReturn;
	}
	
	
	
	
		
	/** Wie .getParameterByModuleAlias(...), hier wird aber der ApplikationKey als Modul-Alias implizit angenommen.
	 * Das kann dann verwendet werden, wenn das Kernel-Konfigurationsfile die Modulkonfiguration auf sich selbst verweist.
	 * 
	 * Beispiel:
	 * Es gibt eine Datei ZKernelConfig_OVPNClient.ini, die als Parameter bei der Erstellung des Kernel-Objekts �bergeben wurde.
	 * Diese Datei hat folgenden Inhalt: 
	 * 
	 * 
[OVPN#01]
#Produktivsystem
KernelLogPath=c:\fglKernel\KernelLog
KernelLogFile=ZKernelLog_OVPNt.txt

######## Modulkonfiguration ##############
;OpenVPN, weist auf das gleiche File
KernelConfigPathOVPN=
KernelConfigFileOVPN=ZKernelConfig_OVPNClient.ini

MeinTestParameter=blablaErgebnis


	 * 
	 * Nun wird die Methode .getParameter("OVPN", "MeinTestParameter") aufgerufen und das ergibt das gleiche "blablaErgebnis"
	 * wie .getParameter("MeinTestParameter") 
	 * 
	* @return String
	* @param sParameter
	* @return 
	* 
	* lindhaueradmin; 13.07.2006 10:45:57
	 * @throws ExceptionZZZ 
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameter(String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; 
		main:{
			String sModulAlias = this.getApplicationKey();
			objReturn = this.getParameterByModuleAlias(sModulAlias, sParameter);
		}//END main:
		return objReturn;
	}
	
	/**Wie get Parameter, aber jetzt wird in den Suchstring auch noch der SystemKey eingebunden.
	 * @param sParameter
	 * @return
	 * @throws ExceptionZZZ
	 * lindhaueradmin, 07.07.2013
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameter4System(String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			String sModul = this.getApplicationKey();
			String sModulWithKey = this.getSystemKey(); //this.getApplicationKey();
			objReturn = this.getParameterByProgramAlias(sModul, sModulWithKey, sParameter);
		}//END main:
		return objReturn;
	}

	/** Parameter f�r ein ganzes Modul holen, aus der Modul-.ini-Datei, die �bergeben wird.
	 * Dies ist z.B. der Aliasname f�r ein 'Program'-Teil
	 * Um f�r das Program einen Parameter zu holen muss verwendet werden "getParameterByProgramAlias(,,,)
	 * @param objFileConfig, Modul-Konfigurationsfile. Wird z.B. durch  objKernel.getFileConfigByAlias(sModuleAlias); ermittelt.
	 * @param sModuleAlias
	 * @param sParameter
	 * @return String. Wert hinter dem Gleichheitszeichen
	 * @throws ExceptionZZZ
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(File objFileConfig, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
				check:{
					if(objFileConfig == null){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileConfig.exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileConfig.isDirectory()==true){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}															
					
					if(StringZZZ.isEmpty(sParameter)){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;								
					}
				}//end check:
					
				//first, get the Ini-file-object			                 	
				String[] saFlagZpassed = this.getFlagZ_passable(true, this);
				saFlagZpassed = StringArrayZZZ.remove(saFlagZpassed, "INIT", true);
				FileIniZZZ objFileIniConfig = new FileIniZZZ(this,  objFileConfig, saFlagZpassed);						
				
				String sModuleAlias = this.getApplicationKey();
				objReturn = KernelSearchParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter, true);						
						}//end main:
		return objReturn;		
	}//end function getParameterByProgramAlias(..)

	@Override
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIniConfig, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			main:{
				check:{
					if(objFileIniConfig == null){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileIniConfig.getFileObject().exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileIniConfig.getFileObject().isDirectory()==true){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
																			
					if(StringZZZ.isEmpty(sParameter)){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}//end check:
	
				//get the value
				String sModuleAlias = this.getApplicationKey(); //Merke: Bei der Suche wird erst nach einem Wert mit dem SystemKey gesucht, der ja ggfs. den ApplicationKey übersteuern kann. //this.getSystemKey();
				if(StringZZZ.isEmpty(sModuleAlias)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}								
				objReturn = KernelSearchParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter, true);

			}//end main:
			return objReturn;		
		}//end function getParameterByModuleAlias(..)
		
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(File objFileConfig, String sModuleAlias, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
				check:{
					if(objFileConfig == null){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileConfig.exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileConfig.isDirectory()==true){
						ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}															
					
					if(StringZZZ.isEmpty(sParameter)){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;								
					}
				}//end check:
					
				//first, get the Ini-file-object
				FileIniZZZ objIniDummy = new FileIniZZZ();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objIniDummy, true);
				FileIniZZZ objFileIniConfig = new FileIniZZZ(this,  objFileConfig, saFlagZpassed);						
								
				objReturn = KernelSearchParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter, true);						
		}//end main:
		return objReturn;		
	}//end function getParameterByProgramAlias(..)

	@Override
	public IKernelConfigSectionEntryZZZ getParameterByModuleFile(FileIniZZZ objFileIniConfig, String sModuleAlias, String sParameter) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			main:{
					check:{
						if(objFileIniConfig == null){
							ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else if(objFileIniConfig.getFileObject().exists()==false){
							ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else if(objFileIniConfig.getFileObject().isDirectory()==true){
							ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
																				
						if(StringZZZ.isEmpty(sParameter)){
							ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Parameter'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}//end check:
		
					//get the value					
					if(StringZZZ.isEmpty(sModuleAlias)){
						ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}								
					objReturn = KernelSearchParameterByModuleFile_(objFileIniConfig, sModuleAlias, sParameter,true);

				}//end main:
			return objReturn;		
		}//end function getParameterByModuleAlias(..)
		
	
	private IKernelConfigSectionEntryZZZ KernelSearchParameterByModuleFile_(FileIniZZZ objFileIniConfigIn, String sModuleAlias, String sProperty, boolean bUseCache) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
							
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
		
		//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
		//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
		String sSectionCacheUsed = sModuleAlias;								
		String sPropertyCacheUsed = sProperty;
		
		IKernelConfigSectionEntryZZZ objFound=null;boolean bExistsInCache=false;
		main:{
			String sSectionUsed = null;
										
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST
			if(bUseCache) {
				IKernelConfigSectionEntryZZZ objFromCache = (IKernelConfigSectionEntryZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
				if(objFromCache!=null){					
						hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
						bExistsInCache=true;
						objFound = objFromCache;
						break main;				
				}else{
					hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
				}
			}else {
				hmDebug.put("DISABLED CACHE FOR: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			//### weiter gehts ohne CACHE Erfolg			
			objFound = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			objFound.setProperty(sProperty);
				
				
				//###################################################################################################			
			    //IDEE: Mache eine Factory, über die dann ein 'AsciiCounterZweistellig' Objekt erstellt werden kann
				//      Dementsprechend in solch einem Objekt den Startwert speichern ung ggfs. per Konstruktor erstellen.
				//      Dann die objAsciiCounterZweistellig.next() bzw. die objAsciiCounterZweistellig.increase() Methode 
				//      letztere zum Endgültigen setzen und erhöhen des Werts anbieten.
				
				//int iStartValue=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;//Zählvariable (beginne anschliessend mit zweistelligen Strings), um im Log den Suchschritt unterscheidbar zu machen.
							
				//Hier einen SingletonCounter holen!!!		
				//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
				//String sStartValue= "A0";
				//ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ(4,"0", sStartValue);	
				//objHandler.setCounterStrategy(objCounterStrategy);
				//ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				
							
				String sStartValue= "";
				//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
				//ICounterStrategyAlphabetZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ(sStartValue);
				//objHandler.setCounterStrategy(objCounterStrategy);
				//ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				
				CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
				ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ(4,"0",sStartValue);
				objCounterStrategy.isIncreasableInOtherMethod(false);//Merke: Die erste Suche geht schon für die Suche nach dem Modul drauf, z.B. KernelConfigPathTestModule. So dass hier schon der 2. Zähler verwendet würde.
				objHandler.setCounterStrategy(objCounterStrategy);
				ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
				String sSearchCounter = objCounter.getStringNext();  // objCounter.current();
				String sDebugKey=null;
				//#################################################################################################
				//1. Konfigurationsfile des Systems holen
				FileIniZZZ objFileIniConfig = null;
			    if(objFileIniConfigIn==null){
			    	objFileIniConfig = this.getFileConfigModuleIni(sModuleAlias);		    	
			    }else{
			    	String stemp = "Verwende übergebenes FileIniZZZ: " + objFileIniConfigIn.getFileObject().getAbsolutePath();
		    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
			    	objFileIniConfig = objFileIniConfigIn;
			    }
			   		   
				//############################################################################################
			    //### Vereinfache den Code, dadurch, dass ein definierte Liste an Schlüsseln abgearbeitet wird.		    
			    //############################################################################################
			    String sApplicationKey = this.getApplicationKey();
			    String sSystemNumber = this.getSystemNumber();
			    
			    String sApplicationOrModuleUsed = sApplicationKey;
			    String sModuleUsed=sModuleAlias;
			   			    		    
			    ArrayList<String> alsSectionModule = AbstractKernelObjectZZZ.computeSystemSectionNamesForModule(objFileIniConfig, sModuleUsed, sApplicationOrModuleUsed, sSystemNumber);
			    int iCounter = -1;
			    
			    for(String sSectionModuleUsed : alsSectionModule) {
			    	sSectionUsed = sSectionModuleUsed;
			    	objFound.setSection(sSectionUsed);
			    	iCounter++;
			    	sDebugKey = "(" + StringZZZ.padLeft(Integer.toString(iCounter), 2, '0') + ")";
			    	hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			    	objFound = KernelSearchParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty);
					if(objFound.hasAnyValue()) break main;
			    }
			    
			    //#################################################
			    
			  //Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
				sDebug = hmDebug.computeDebugString(" | ",":");
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ABBRUCH DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug + " in der Datei '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'");
//				ExceptionZZZ ez = new ExceptionZZZ(sDebug, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				
		}//END main:
		sDebug = hmDebug.computeDebugString(" | ",":");
		if(objFound!=null && objFound.hasAnyValue()) {
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": Wert gefunden fuer sModuleAlias='" + sModuleAlias + "' | " + "' | for the property: '" + sProperty + "'.");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug);
			
			//################
			//DEBUG: Entsprechen die oben eingegebenen Suchstrings auch dem 1. Suchschritt?
			/*
			Set<Integer> setKeyOuter = hmDebug.keySet();
			Integer[] objaKeyOuter = (Integer[]) setKeyOuter.toArray(new Integer[setKeyOuter.size()]);
			Integer obj = objaKeyOuter[0];
			
			//Nun diesen Wert in den Cache übernehmen.
			HashMap<String,String>hmInner = hmDebug.getInnerHashMap(obj);//Das war der 1. Versuch. Er sollte die Eingabewerte beinhalten. Diese sollen die Keys für den Cache sein. Dann wird der Wert beim 2. Aufruf sofort gefunden.
			Set<String> setKey = hmInner.keySet();
			String[] saKey = (String[]) setKey.toArray(new String[setKey.size()]);				
			String sKey = saKey[0];					
			String sValue = hmInner.get(sKey);
			*/
			//###################
			
			//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			if(!bExistsInCache) this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturn = objFound;
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": KEIN WERT GEFUNDEN fuer sModuleAlias='" + sModuleAlias + "' | " + "' | for the property: '" + sProperty + "'.");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}		
		return objReturn;
	}
	
	/** String; Parameter basierend auf dem Systemkey holen und dem ALIAS eines Programms, innerhalb der Modul-.ini-Datei.
	 *
	* Der Aliasname des Programms ist dabei eine weitere Section in der .ini-Datei, mit folgendem Aufbau:
	 * [SYSTEMKEY!PROGRAMALIAS]
	 * 
	 * z.B. für das Programm mit dem Alias copy1 lautet die Section [FGL#01!copy1]
	 * 
	 * 
	 * Hintergrund:
	 * Ein Programm, z.B. ein Notes-Agent sollte seinen eigenen Namen kennen und das Modul, zu dem es geh�rt.
	 * Mit diesem Namen wird es in der Lage sein aus der Modulkonfiguration in einem ersten Schritt den Aliasnamen zu ermitteln.
	 * und in einem n�chsten Schritt kann dann diese Methode eingesetzt werden.
	 * 
	* Lindhauer; 20.04.2006 07:53:05
	 * @param objFileConfig
	 * @param sProgramAlias
	 * @param sParameter
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;//new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(objFileConfig == null){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(objFileConfig.exists()==false){
					ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(objFileConfig.isDirectory()==true){
					ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
					
				if(StringZZZ.isEmpty(sProgramOrSection)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ProgramOrSection'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
					
				if(StringZZZ.isEmpty(sProperty)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Property'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end check:
					
			//first, get the Ini-file-object
			HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
			hmFlag.put(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), true);

			FileIniZZZ objIni = new FileIniZZZ(this, objFileConfig, hmFlag);

			//now call another method
			objReturn = this.getParameterByProgramAlias(objIni, sProgramOrSection, sProperty);
		}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
	
	/**Gibt den Konfigurierten Wert eines Programms wieder. Dabei werden globale Werte durch "Speziell" für die SystemNumber definierte Werte �berschrieben
	 * @param objFileIniConfig
	 * @param sAliasProgramOrSection  
	 * @param sProperty
	 * @return Null, wenn die Property nicht gefunden werden kann, sonst String
	 * @throws ExceptionZZZ, wenn z.B. keine Sektion aus sProgramOrSection in der Konfigurationsdatei existiert.
	 *
	 * javadoc created by: 0823, 20.10.2006 - 09:03:54
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sAliasProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
				check:{
					if(objFileIniConfig == null){
						String stemp = "Missing parameter: 'Configuration file-object'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileIniConfig.getFileObject().exists()==false){
						String stemp = "Wrong parameter: 'Configuration file-object' does not exist.";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else if(objFileIniConfig.getFileObject().isDirectory()==true){
						String stemp = "Wrong parameter: 'Configuration file-object' is as directory.";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
							
					if(StringZZZ.isEmpty(sAliasProgramOrSection)){
						String stemp = "Missing parameter: 'ProgramOrSection'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else {
						objReturn.setSection(sAliasProgramOrSection);
					}
					
					if(StringZZZ.isEmpty(sProperty)){
						String stemp = "Missing parameter: 'Property'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}else {
						objReturn.setProperty(sProperty);
					}
				}//end check:

				String sProgramOrSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sAliasProgramOrSection).getValue();
				if(StringZZZ.isEmpty(sProgramOrSection)){
					sProgramOrSection = sAliasProgramOrSection;
				}							
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReference.set(objReturn);
				objReturn = this.KernelSearchParameterByProgramAlias_(objFileIniConfig, sProgramOrSection, sAliasProgramOrSection, sProperty, true, objReturnReference);								
	}//end main:
	return objReturn;		
}//end function getParameterByProgramAlias(..)
		
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModule, String sProgramOrSection, String sProperty, boolean bUseCache) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);					
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//END check:
				  
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			objReturn = this.KernelSearchParameterByProgramAlias_(null, sModule, sProgramOrSection, sProperty, bUseCache, objReturnReference);
		}//END main:
		return objReturn;
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(objFileIniConfig==null){
					String stemp = "'FileIniZZZ'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setSection(sProgramOrSection);
				}
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setProperty(sProperty);
				}
			}//END check:

			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			objReturn = this.KernelSearchParameterByProgramAlias_(objFileIniConfig, sModule, sProgramOrSection, sProperty, true, objReturnReference);
		}//END main:
		return objReturn;
	}
	
	private IniFile KernelSearchFileConfigDirectLookup_(IniFile objFileIni, String sModule, ArrayList<String>listasModuleOrApplicationSection) throws ExceptionZZZ {
		IniFile objReturn = null;
		main:{
			if(objFileIni==null){
				String sLog = "'Inifile'";
				sLog = ReflectCodeZZZ.getMethodCurrentNameLined() + sLog;
				System.out.println(sLog);
				this.logLineDate(sLog);
								
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(listasModuleOrApplicationSection==null) {
				String sLog = "'ListAsModuleOrApplication Section'";
				sLog = ReflectCodeZZZ.getMethodCurrentNameLined() + sLog;
				System.out.println(sLog);
				this.logLineDate(sLog);
				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			try {							
				String sFilename=null;
				for(String sModuleOrApplicationSectionSection:listasModuleOrApplicationSection) {
					sFilename = this.KernelSearchFileConfigFilenameDirectLookup_(objFileIni, sModuleOrApplicationSectionSection, sModule);
					if(!StringZZZ.isEmpty(sFilename)) break;
				}								
				if(StringZZZ.isEmpty(sFilename)) break main;//Leerer Dateiname ist ein Abbruchkriterium
				
				String sDirectoryname=null;
				for(String sModuleSection:listasModuleOrApplicationSection) {
					sDirectoryname = this.KernelSearchFileConfigFiledirectoryDirectLookup_(objFileIni, sModuleSection, sModule);
					if(!StringZZZ.isEmpty(sDirectoryname)) break;				
				}
				//Merke: Bei leerem Verzeichnisnamen wird ein Projektverzeichnis genommen.
				
				//#######################################
				//### Proof the existance of the file
				//#######################################
				String sFileTotal = FileEasyZZZ.joinFilePathName(sDirectoryname, sFilename);				
				if(this.getLogObject()!=null) this.getLogObject().writeLineDateWithPosition("sFileTotal = " +  sFileTotal);
				File objFile = new File(sFileTotal);//Wichtig: Damit sollte diese Datei nicht autmatisch erstellt sein!!!
				
				//Mache das neue Ini-Objekt
				String sPathTotalToUse = objFile.getAbsolutePath();
				String sLog = "Trying to create new IniFile Object for path '" + sPathTotalToUse + "'.";
				sLog = ReflectCodeZZZ.getMethodCurrentNameLined() + sLog;
				this.logLineDate(sLog);
				if(FileEasyZZZ.exists(sPathTotalToUse)) {
					objReturn = new IniFile(sPathTotalToUse);
					
					//TODO GOON 20190214: Hier das neue Ini File der ArrayList der Dateien hinzufügen. Dann muss man es auch nicht immer wieder neu erstellen....
					
				}else {
					sLog = "File does not exist '" + sPathTotalToUse + "'. Will not create ini File (it would bei empty).";					
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined() + "XXXXTest BySyso " + sLog);
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentNameLined() + "XXXXTest ByLogLineDate " + sLog);

				}								
			} catch (IOException ioe) {
				String sLog = "IOException: Configuration File. Not able to create ini-FileObject.";
				sLog = ReflectCodeZZZ.getMethodCurrentNameLined() + sLog;
				System.out.println(sLog);
				this.logLineDate(sLog);
				
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName(), ioe );
				throw ez;
			}
			
		}//end main:
		return objReturn;
	}
	
	private IniFile KernelSearchFileConfigDirectLookupInWorkspace_(IKernelConfigZZZ objConfig, IniFile objFileIni, String sModule, ArrayList<String>listasModuleOrApplicationSection) throws ExceptionZZZ {
		IniFile objReturn = null;
		main:{
			if(objConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;
			
			
			if(objFileIni==null){
				String stemp = "'Inifile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(listasModuleOrApplicationSection==null) {
				String stemp = "'ListAsModuleOrApplication Section'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			try {							
				String sFilename=null;
				for(String sModuleOrApplicationSectionSection:listasModuleOrApplicationSection) {
					sFilename = this.KernelSearchFileConfigFilenameDirectLookupInWorkspace_(objConfig, objFileIni, sModuleOrApplicationSectionSection, sModule);
					if(!StringZZZ.isEmpty(sFilename)) break;
				}								
				if(StringZZZ.isEmpty(sFilename)) break main;//Leerer Dateiname ist ein Abbruchkriterium
				
				String sDirectoryname=null;
				for(String sModuleSection:listasModuleOrApplicationSection) {
					sDirectoryname = this.KernelSearchFileConfigFiledirectoryDirectLookupInWorkspace_(objConfig, objFileIni, sModuleSection, sModule);
					if(!StringZZZ.isEmpty(sDirectoryname)) break;				
				}
				//Merke: Bei leerem Verzeichnisnamen wird ein Projektverzeichnis genommen.
				
				//#######################################
				//### Proof the existance of the file
				//#######################################
				String sFileTotal = FileEasyZZZ.joinFilePathNameForWorkspace(sDirectoryname, sFilename);
				if(this.getLogObject()!=null) this.getLogObject().writeLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#sFileTotal = " +  sFileTotal);
				File objFile = new File(sFileTotal);//Wichtig: Damit sollte diese Datei nicht autmatisch erstellt sein!!!
				
				//Mache das neue Ini-Objekt
				String sPathTotalToUse = objFile.getAbsolutePath();
				String sLog = "Trying to create new IniFile Object for path '" + sPathTotalToUse + "'.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				if(FileEasyZZZ.existsInWorkspace(sPathTotalToUse)) {
					objReturn = new IniFile(sPathTotalToUse);
					//TODO GOON 20190214: Hier das neue Ini File der ArrayList der Dateien hinzufügen. Dann muss man es auch nicht immer wieder neu erstellen....
					
				}else {
					sLog = "File does not exist '" + sPathTotalToUse + "'. Will not create ini File (it would bei empty).";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);					
				}								
			} catch (IOException ioe) {
				String sLog = "IOException: Configuration File. Not able to create ini-FileObject.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName(), ioe );
				throw ez;
			}
			
		}//end main:
		return objReturn;
	}
	
	private String KernelSearchFileConfigFilenameDirectLookup_(IniFile objFileIni, String sModuleOrApplicationSection,String sModule) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			if(objFileIni==null){
				String stemp = "'Inifile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModuleOrApplicationSection)) {
				String stemp ="ModuleOrApplicationSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModule)) {
				String stemp ="ModuleSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sModuleOrApplicationSectionUsed=sModuleOrApplicationSection;
			//sModuleOrApplicationSectionUsed = KernelKernelZZZ.extractModuleFromSection(sModuleOrApplicationSection);
//			if(StringZZZ.isEmpty(sModuleOrApplicationSectionUsed)) {
//				String stemp ="ModuleOrApplicationSection String - Module not available";
//				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
			
			String sPropertyUsed = null;
			String sFileNameUsed = null; String sFileName = null;
			
			//########################################
			//A) DATEINAME	
			sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX +sModule;								
			sFileName =objFileIni.getValue(sModuleOrApplicationSectionUsed, sPropertyUsed ); 					
			if(StringZZZ.isEmpty(sFileName)) {			
											
				//GGFS. OHNE MODUL WIRD DIE APPLIKATION ANGEGEBEN SEIN
				sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX;
				sFileName =objFileIni.getValue(sModuleOrApplicationSectionUsed, sPropertyUsed ); 										
				
			}
			if(StringZZZ.isEmpty(sFileName)) break main;//Leer konfiguriert, bedeutet.. an anderer Stelle suchen
			
			
			sFileNameUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFileName);				
			if(!StringZZZ.equals(sFileName,sFileNameUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
				//this.setValueRaw(sFileName);
			}else{
				//this.setValueRaw(null);
			}
			if(StringZZZ.isEmpty(sFileNameUsed)) break main;
			sReturn = sFileNameUsed;
		}//end main:
		return sReturn;
	}
	
	private String KernelSearchFileConfigFilenameDirectLookupInWorkspace_(IKernelConfigZZZ objConfig, IniFile objFileIni, String sModuleOrApplicationSection,String sModule) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			String sLog = null;
			if(objConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;
			
			if(objFileIni==null){
				String stemp = "'Inifile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModuleOrApplicationSection)) {
				String stemp ="ModuleOrApplicationSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModule)) {
				String stemp ="ModuleSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sModuleOrApplicationSectionUsed=sModuleOrApplicationSection;
			//sModuleOrApplicationSectionUsed = KernelKernelZZZ.extractModuleFromSection(sModuleOrApplicationSection);
//			if(StringZZZ.isEmpty(sModuleOrApplicationSectionUsed)) {
//				String stemp ="ModuleOrApplicationSection String - Module not available";
//				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}
			
			String sPropertyUsed = null;
			String sFileNameUsed = null; String sFileName = null;
			
			//########################################
			//A) DATEINAME	
			sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX +sModule;								
			sFileName =objFileIni.getValue(sModuleOrApplicationSectionUsed, sPropertyUsed ); 					
			if(StringZZZ.isEmpty(sFileName)) {			
											
				//GGFS. OHNE MODUL WIRD DIE APPLIKATION ANGEGEBEN SEIN
				sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX;
				sFileName =objFileIni.getValue(sModuleOrApplicationSectionUsed, sPropertyUsed ); 										
				
			}
			if(StringZZZ.isEmpty(sFileName)) break main;//Leer konfiguriert, bedeutet.. an anderer Stelle suchen
			
			
			sFileNameUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFileName);				
			if(!StringZZZ.equals(sFileName,sFileNameUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
				//this.setValueRaw(sFileName);
			}else{
				//this.setValueRaw(null);
			}
			if(StringZZZ.isEmpty(sFileNameUsed)) break main;
			sReturn = sFileNameUsed;
		}//end main:
		return sReturn;
	}
	
	private String  KernelSearchFileConfigFiledirectoryDirectLookup_(IniFile objFileIni, String sModuleOrApplicationSection, String sModule) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			String sLog = null; String sLog2 = null; String[]saLog = null;
			if(objFileIni==null){
				String stemp = "'Inifile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModuleOrApplicationSection)) {
				String stemp ="ModuleOrApplicationSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModule)) {
				String stemp ="Module String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sModuleOrApplicationSectionUsed=sModuleOrApplicationSection;
			//sModuleOrApplicationSectionUsed = KernelKernelZZZ.extractModuleFromSection(sModuleOrApplicationSection);
			if(StringZZZ.isEmpty(sModuleOrApplicationSectionUsed)) {
				String stemp ="ModuleOrApplicationSection String - Module not available";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sPropertyUsed = null;
			String sFilePathUsed = null; String sFilePath = null;
			
			//########################################
			//##########################################
			//B) VERZEICHNISNAME
			sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX +sModule;						
			sFilePath = objFileIni.getValue(sModuleOrApplicationSectionUsed,sPropertyUsed);				
			if(StringZZZ.isEmpty(sFilePath)) {
				
				//GGFS. OHNE MODUL WIRD DIE APPLIKATION ANGEGEBEN SEIN
				sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX;						
				sFilePath = objFileIni.getValue(sModuleOrApplicationSectionUsed,sPropertyUsed);
			}
			if(StringZZZ.isEmpty(sFilePath)) break main;//Merke: Ein Leer definiertes Verzeichnis ist ein Grund woanders zu suchen.
								
			//Ein per Platzhalter leer definiertes Verzeichnis bedeutet "nicht an anderer Konfigurationsstelle weitersuchen"
			//if(StringZZZ.equalsIgnoreCase(sFilePath, KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) break main;
			//if(StringZZZ.equalsIgnoreCase(sFilePath, KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) break main;
						
			sFilePathUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFilePath);
			if(!StringZZZ.equals(sFilePath,sFilePathUsed)){
				sLog = "Value durch ExpressionIniConverter verändert von '" + sFilePath + "' nach '" + sFilePathUsed +"'";											
				if(sFilePathUsed==null) {				
					sLog2 = "Null Value als Ergebnis des ExpressionIniConverter verändert nach Leerstring.";
					saLog = StringArrayZZZ.append(sLog, sLog2);
					this.logProtocolWithPosition(saLog);
					sFilePathUsed="";
				}else {
					this.logProtocolWithPosition(sLog);
				}
			}
			
			//TODOGOON20251009;//Wenn das ein relativer Pfad ist, suchen:
			//		unterhalb des Pfads der aktuellen KernelKonfiguration suchen.
			//			zum Elternpfad und dann zum relativen Pfad.
			File objKernel = this.getFileConfigKernel();
			if(objKernel!=null){
				File objKernelParent = objKernel.getParentFile();	
				File objKernelParentParent = objKernelParent.getParentFile();
				sFilePathUsed = FileEasyZZZ.joinFilePathName(objKernelParentParent, sFilePathUsed);
				
				File objModule = FileEasyZZZ.getDirectory(sFilePathUsed);
				if(objModule.exists()) {
					sReturn = sFilePathUsed;
					break main;
				}
			}
			
			//Merke: Ein leeres Verzeichnis ist kein Problem, dann Projektstandardverzeichnis nehmen. if(StringZZZ.isEmpty(sFilePathUsed)) break main;
			//Problem: Hierin wird per Classloader gesucht... Das bedeutet es wird beim Modulen ggfs. im falschen Projektordner gesucht.
			File objReturn2 = FileEasyZZZ.searchFileObjectInRunningExecutionProjectPath(sFilePathUsed);
			if(objReturn2!=null) {
				sFilePathUsed = objReturn2.getAbsolutePath();
				sReturn = sFilePathUsed;
				break main;
			}
			
			//... und wenn dort nix gefunden worden ist
			
			//Bei "Modulen" ist das ein Problem, da nun das Projekt des "starts" genommen wird.
			//Ziel: Das Projekt des Moduls nehmen.			
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				objReturn2 = FileEasyZZZ.searchFileObjectInWorkspace(objConfig, sFilePathUsed);
				if(objReturn2!=null) {
					sFilePathUsed = objReturn2.getAbsolutePath();
					sReturn = sFilePathUsed;
					break main;
				}
			}						
			sReturn = sFilePathUsed;				
		}//end main:
		return sReturn;
	}
	
	private String  KernelSearchFileConfigFiledirectoryDirectLookupInWorkspace_(IKernelConfigZZZ objConfig, IniFile objFileIni, String sModuleOrApplicationSection, String sModule) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			if(objConfig==null) {
				ExceptionZZZ ez = new ExceptionZZZ("'IKernelConfig-Object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			//Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;
									
			if(objFileIni==null){
				String stemp = "'Inifile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModuleOrApplicationSection)) {
				String stemp ="ModuleOrApplicationSection String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sModule)) {
				String stemp ="Module String";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sModuleOrApplicationSectionUsed=sModuleOrApplicationSection;
			//sModuleOrApplicationSectionUsed = KernelKernelZZZ.extractModuleFromSection(sModuleOrApplicationSection);
			if(StringZZZ.isEmpty(sModuleOrApplicationSectionUsed)) {
				String stemp ="ModuleOrApplicationSection String - Module not available";
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sPropertyUsed = null;
			String sFilePathUsed = null; String sFilePath = null;
			
			//########################################
			//##########################################
			//B) VERZEICHNISNAME
			sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX +sModule;						
			sFilePath = objFileIni.getValue(sModuleOrApplicationSectionUsed,sPropertyUsed);				
			if(StringZZZ.isEmpty(sFilePath)) {
				
				//GGFS. OHNE MODUL WIRD DIE APPLIKATION ANGEGEBEN SEIN
				sPropertyUsed = IKernelConfigConstantZZZ.sMODULE_DIRECTORY_PREFIX;						
				sFilePath = objFileIni.getValue(sModuleOrApplicationSectionUsed,sPropertyUsed);
			}
			if(StringZZZ.isEmpty(sFilePath)) break main;//Merke: Ein Leer definiertes Verzeichnis ist ein Grund woanders zu suchen.
								
			//Ein per Platzhalter leer definiertes Verzeichnis bedeutet "nicht an anderer Konfigurationsstelle weitersuchen"
			//if(StringZZZ.equalsIgnoreCase(sFilePath, KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) break main;
			//if(StringZZZ.equalsIgnoreCase(sFilePath, KernelZFormulaIni_NullZZZ.getExpressionTagEmpty())) break main;
						
			sFilePathUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFilePath);
			if(!StringZZZ.equals(sFilePath,sFilePathUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFilePath + "' nach '" + sFilePathUsed +"'");								
				//this.setValueRaw(sFilePath);
			}else{
				//this.setValueRaw(null);
			}
			
			//Merke: Ein leeres Verzeichnis ist kein Problem, dann Projektstandardverzeichnis nehmen. if(StringZZZ.isEmpty(sFilePathUsed)) break main;
			//Problem: Hierin wird per Classloader gesucht... Das bedeutet es wird beim Modulen ggfs. im falschen Projektordner gesucht.
//			File objReturn2 = FileEasyZZZ.searchFileObjectInRunningExecutionProjectPath(sFilePathUsed);
//			if(objReturn2!=null) {
//				sFilePathUsed = objReturn2.getAbsolutePath();
//				sReturn = sFilePathUsed;
//				break main;
//			}
			
			//Bei "Modulen" ist das ein Problem, da nun das Projekt des "starts" - also main() -genommen wird.
			//Ziel: Das Projekt des Moduls nehmen.			

				File objReturn2 = FileEasyZZZ.searchFileObjectInWorkspace(objConfig, sFilePathUsed);
				if(objReturn2!=null) {
					sFilePathUsed = objReturn2.getAbsolutePath();
					sReturn = sFilePathUsed;
					break main;
				}
//			}						
			sReturn = sFilePathUsed;				
		}//end main:
		return sReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelSearchParameterByProgramAlias_AliasSystemLookup_(FileIniZZZ objFileIniConfig, String sSection, String sProperty, HashMapMultiIndexedZZZ hmDebug, String sDebugKey) throws ExceptionZZZ{
		
		//TODOGOON: Methode kann geloescht werden, wenn niergendwo referenziert.
		
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		
		IKernelConfigSectionEntryZZZ objFound=null;
		main:{
			if(!StringZZZ.isEmpty(sSection)){
				IKernelConfigSectionEntryZZZ objTemp = null;
				
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				String sSearchCounter = objCounter.getStringNext(); //objCounter.current();
				
				
				//a) mit Systemkey
				//String sDebugKeyUsed = sDebugKey + ".a";
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getSystemKey() + "' für das Programm oder die Section '" + sSection + "'");
				String sSectionUsed = this.getSystemKey();
				String sPropertyUsed = sSection; //Erst nach einem möglichen Aliaswert hier suchen.
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
				objTemp = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed);				
				if(objTemp.hasAnyValue()){									
					sSectionUsed = objTemp.getValue();
					sPropertyUsed = sProperty; //nun erst die nach der übergebenen Property suchen. 
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
					
					objFound = KernelSearchParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sPropertyUsed);
					if(objFound.hasAnyValue()) break main;
				}
				
				//b) mit ApplicationKey
				//sDebugKeyUsed = sDebugKey + ".b";
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getApplicationKey() + "' für das Programm oder die Section '" + sSection + "'");
				sSectionUsed = this.getApplicationKey();								
				sPropertyUsed = sSection; 
				hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
				objTemp = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed);
				if(objTemp.hasAnyValue()){
					sSectionUsed = objTemp.getValue();
					sPropertyUsed = sProperty;
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);	
					objFound = KernelSearchParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sPropertyUsed);
					if(objFound.hasAnyValue()) break main;				
				}
			}
		}//End main:
		if(objFound!=null && objFound.hasAnyValue()) {
			objReturn = objFound;
		}		
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelSearchParameterByProgramAlias_SystemLookup_(FileIniZZZ objFileIniConfig, String sSection, String sProperty, HashMapMultiIndexedZZZ hmDebug, String sDebugKey) throws ExceptionZZZ{
		
		//TODOGOON: Methode kann geloescht werden, wenn niergendwo referenziert.
		
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		
		IKernelConfigSectionEntryZZZ objFound=null;
		main:{
			if(!StringZZZ.isEmpty(sSection)){				
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				
//				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				
				CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
				ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
				
				String sSearchCounter = objCounter.getStringNext(); //objCounter.current();
				
				//############
				//Ansatz: Hole eine Liste der zur Verfügung stehenden Sections, die für dieses Program denkbar wären.
				String sApplicationKey = this.getApplicationKey();
				String sSystemNumber = this.getSystemNumber();
				FileIniZZZ objFileConfigIni = this.getFileConfigKernelIni();
				ArrayList<String>listasSectionProgram = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFileConfigIni, sSection, sApplicationKey, sSystemNumber);
				for(String sSectionUsed : listasSectionProgram) {
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
					
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + "Suche in '"+ sSectionUsed + "' nach dem Wert '" + sProperty + "'");			
					objFound = KernelSearchParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty);
					if(objFound.hasAnyValue()) break main;					
				}
				
//				//############
//				//a) mit Systemkey
//				
//				
//				//String sDebugKeyUsed = sDebugKey + ".a" + sSearchCounter;
//				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined()+ sDebugKeyUsed);
//
//				String sSectionUsed = this.getSystemKey() + IKernelFileIniZZZ.sINI_SUBJECT_SEPARATOR_PROGRAM + sSection;	
//				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);				
//				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + "Suche in '"+ sSectionUsed + "' nach dem Wert '" + sProperty + "'");			
//				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
//				if(objReturn.hasAnyValue()) break main;
//					
//				//b) mit ApplicationKey
//				//sDebugKeyUsed = sDebugKey + ".b" + sSearchCounter;
//				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined()+ sDebugKeyUsed);
//				
//				sSectionUsed = this.getApplicationKey() + "!" + sSection;	
//				hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
//				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + "Suche in '"+ sSectionUsed + "' nach dem Wert '" + sProperty + "'");			
//				objReturn = KernelGetParameterByProgramAlias_DirectLookup_(sSectionUsed, sProperty, objFileIniConfig);
//				if(objReturn.hasAnyValue()) break main;	

				}//if(!StringZZZ.isEmpty(sSection)){									
		}//End main:
		if(objFound != null && objFound.hasAnyValue()) {
			objReturn = objFound;
		}
		return objReturn;
	}
	
	private static IKernelConfigSectionEntryZZZ KernelGetParameter_DirectLookup_(IKernelFileIniZZZ objFileIniConfig, String sSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.	
		main:{
			if(objFileIniConfig==null){
				String stemp = "'FileIniZZZ'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			IniFile objFileIni = objFileIniConfig.getFileIniObject();
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			objReturn = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIni, sSection, sProperty);
			
		}//End main:
		return objReturn;
	}
	
	private static IKernelConfigSectionEntryZZZ KernelGetParameter_DirectLookup_(IniFile objFileIni, String sSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.	
		main:{
			String sReturn = AbstractKernelObjectZZZ.KernelGetParameterString_DirectLookup_(objFileIni, sSection, sProperty);						
			if(!StringZZZ.isEmpty(sReturn)){
				objReturn.setSection(sSection, true);
				objReturn.setRaw(sReturn);
				objReturn.setValue(sReturn);
				objReturn.setProperty(sProperty, true);
			}else{									
				objReturn.setSection(sSection, false);
				objReturn.setProperty(sProperty, false);
			}
			
			
		}//End main:
		return objReturn;
	}
	
	private static String KernelGetParameterString_DirectLookup_(IniFile objFileIni, String sSection, String sProperty) throws ExceptionZZZ{
		String sReturn = new String(""); 	
		main:{
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			if(!StringZZZ.isEmpty(sSection)){
			    boolean bSectionExists = AbstractKernelObjectZZZ.KernelProofSectionExists_DirectLookup_(objFileIni, sSection);
				if(bSectionExists==true){										
					sReturn = objFileIni.getValue(sSection, sProperty);
				}
			}
		}//End main:
		return sReturn;
	}
	
	private static boolean KernelProofSectionExists_DirectLookup_(IniFile objFileIni, String sSection) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objFileIni==null) break main;
			if(StringZZZ.isEmpty(sSection)) break main;
			
			String[] saSubject = objFileIni.getSubjects();
			bReturn = StringArrayZZZ.contains(saSubject, sSection);
		}//end main:
		return bReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelSearchParameterByProgramAlias_DirectLookup_(FileIniZZZ objFileIniConfig, String sSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.	
		main:{
			if(objFileIniConfig==null){
				String stemp = "'FileIniZZZ'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			IniFile objFileIni = objFileIniConfig.getFileIniObject();
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			objReturn.setSection(sSection); //Hier erst einmal setzen. Es wird in der LookupFunktion noch genauer gesetzt.
			objReturn.setProperty(sProperty, false);
			if(!StringZZZ.isEmpty(sSection)){
			    boolean bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSection);
				if(bSectionExists==true){
					objReturn.setSection(sSection,true);
					objReturn = objFileIniConfig.getPropertyValue(sSection, sProperty);					
					if(objReturn.hasAnyValue()) {
						objReturn.setProperty(sProperty, true);
					}
				}else{						
					objReturn.setSection(sSection,false);
				}				
			}
		}//End main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ KernelSearchParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSectionIn, String sProgramOrSectionIn, String sProperty, boolean bUseCache, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
		if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
		String sSectionUsed = null;
		
		//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
		//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
		
		String sProgramOrSection="";
		if(StringZZZ.isEmpty(sProgramOrSectionIn)){
			sProgramOrSection = AbstractKernelObjectZZZ.extractProgramFromSection(sMainSectionIn);
			if(StringZZZ.isEmpty(sProgramOrSection)){
				String stemp = "'ProgramOrSection'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}else{
			sProgramOrSection = AbstractKernelObjectZZZ.extractProgramFromSection(sProgramOrSectionIn);
		}
		
		String sMainSection="";
		if(StringZZZ.isEmpty(sMainSectionIn)) {
			sMainSection = AbstractKernelObjectZZZ.extractModuleFromSection(sProgramOrSectionIn);		    
		}else {
			sMainSection = AbstractKernelObjectZZZ.extractModuleFromSection(sMainSectionIn);;
		}
		
		
		String sSectionCacheUsed = sProgramOrSection;		
		String sPropertyCacheUsed = sProperty;
		
		IKernelConfigSectionEntryZZZ objFound=null;
		boolean bExistsInCache=false;
		main:{		
			//############################
			//VORWEG: IM CACHE NACHSEHEN, OB DER EINTRAG VORHANDEN IST
			if(bUseCache) {
				IKernelConfigSectionEntryZZZ objFromCache = (IKernelConfigSectionEntryZZZ) this.getCacheObject().getCacheEntry(sSectionCacheUsed, sPropertyCacheUsed);
				if(objFromCache!=null){					
						hmDebug.put("From CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
						bExistsInCache=true;
						objFound = objFromCache;
						break main;				
				}else{
					hmDebug.put("Not in CacheZZZ: " + sSectionCacheUsed, sPropertyCacheUsed);
				}
			}else {
				hmDebug.put("DISABLED CACHE FOR: " + sSectionCacheUsed, sPropertyCacheUsed);
			}
			
			//### weiter gehts ohne CACHE Erfolg, setze die Eingabewerte
			objFound = objReturn;	
			objFound.setSection(sSectionCacheUsed);
			objFound.setProperty(sPropertyCacheUsed);
			
			//###################################################################################################			
		    //IDEE: Mache eine Factory, über die dann ein 'AsciiCounterZweistellig' Objekt erstellt werden kann
			//      Dementsprechend in solch einem Objekt den Startwert speichern ung ggfs. per Konstruktor erstellen.
			//      Dann die objAsciiCounterZweistellig.next() bzw. die objAsciiCounterZweistellig.increase() Methode 
			//      letztere zum Endgültigen setzen und erhöhen des Werts anbieten.
			
			//int iStartValue=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;//Zählvariable (beginne anschliessend mit zweistelligen Strings), um im Log den Suchschritt unterscheidbar zu machen.
						
			//Hier einen SingletonCounter holen!!!		
			//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
			//String sStartValue= "A0";
			//ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ(4,"0", sStartValue);	
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
			
						
			String sStartValue= "";
			//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
			//ICounterStrategyAlphabetZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ(sStartValue);
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ(4,"0",sStartValue);
			objCounterStrategy.isIncreasableInOtherMethod(false);//Merke: Die erste Suche geht schon für die Suche nach dem Modul drauf, z.B. KernelConfigPathTestModule. So dass hier schon der 2. Zähler verwendet würde.
			objHandler.setCounterStrategy(objCounterStrategy);
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();  // objCounter.current();
			String sDebugKey=null;
			//#################################################################################################
			//1. Konfigurationsfile des Systems holen
			FileIniZZZ objFileIniConfig = null;
		    if(objFileIniConfigIn==null){
		    	objFileIniConfig = this.searchModuleFileWithProgramAlias(sMainSection, sProgramOrSection);
		    	if(objFileIniConfig==null) {
		    		String stemp = "Konfigurationsdatei nicht vorhanden für Modul: '" + sMainSection + "' | ProgramOrSection: " + sProgramOrSection + "'";
		    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    		if(hmDebug!=null) hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + stemp , sProperty);
		    	}
		    }else{
		    	String stemp = "Verwende übergebenes FileIniZZZ: " + objFileIniConfigIn.getFileObject().getAbsolutePath();
	    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objFileIniConfig = objFileIniConfigIn;
		    }
		   		   
			//############################################################################################
		    //### Vereinfache den Code, dadurch, dass ein definierte Liste an Schlüsseln abgearbeitet wird.		    
		    //############################################################################################
		    String sApplicationKey = this.getApplicationKey();
		    String sSystemNumber = this.getSystemNumber();
		    String sMainSectionAsModuleUsed = sMainSection;
		    String sProgramOrSectionUsed=sProgramOrSection;
		    
		    int iCounter = -1;
		    
		    
		    //Hole nun die Sections für das Program, inkl. Aliaswerte, basierend auf eine mögliche Modulesection
		    ArrayList<String> alsSectionProgram = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFileIniConfig, sProgramOrSectionUsed, sMainSectionAsModuleUsed, sApplicationKey, sSystemNumber);		    		   
		    for(String sSectionProgramUsed : alsSectionProgram) {
		    	sSectionUsed = sSectionProgramUsed;
		    	objFound.setSection(sSectionUsed);
		    	iCounter++;
		    	sDebugKey = "(" + StringZZZ.padLeft(Integer.toString(iCounter), 2, '0') + ")";
		    	if(hmDebug!=null) hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + "Module: |" + sSectionUsed + "'| Program: '" + sSectionProgramUsed + "'", sProperty);
		    	objFound = KernelSearchParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionProgramUsed, sProperty);
				if(objFound.hasAnyValue()) break main;				
		    }//end for
		    			
		    //#################################################		    
			//Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
			sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ABBRUCH DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug + " in der Datei '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'");
//			ExceptionZZZ ez = new ExceptionZZZ(sDebug, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		}//END main:
			
		sDebug = hmDebug.computeDebugString(" | ",":");
		if(objFound!=null && objFound.hasAnyValue()) {
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Wert gefunden fuer sMainSection='" + sMainSection + "'/sSectionUsed='"+sSectionUsed+"' | sProgramOrSection='" + sProgramOrSection + "' | for the property: '" + sProperty + "'.");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug);
			
			
			//################
			//DEBUG: Entsprechen die oben eingegebenen Suchstrings auch dem 1. Suchschritt?
			/*
			Set<Integer> setKeyOuter = hmDebug.keySet();
			Integer[] objaKeyOuter = (Integer[]) setKeyOuter.toArray(new Integer[setKeyOuter.size()]);
			Integer obj = objaKeyOuter[0];
			
			//Nun diesen Wert in den Cache übernehmen.
			HashMap<String,String>hmInner = hmDebug.getInnerHashMap(obj);//Das war der 1. Versuch. Er sollte die Eingabewerte beinhalten. Diese sollen die Keys für den Cache sein. Dann wird der Wert beim 2. Aufruf sofort gefunden.
			Set<String> setKey = hmInner.keySet();
			String[] saKey = (String[]) setKey.toArray(new String[setKey.size()]);				
			String sKey = saKey[0];					
			String sValue = hmInner.get(sKey);
			*/
			//###################
			
			//Verwende die oben abgespeicherten Eingabewerte und nicht die Werte aus der Debug-Hashmap
			if(!bExistsInCache) this.getCacheObject().setCacheEntry(sSectionCacheUsed, sPropertyCacheUsed, (ICachableObjectZZZ) objFound);
			objReturnReference.set(objFound);         
			objReturn = objFound;
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": KEIN WERT GEFUNDEN fuer sMainSection='" + sMainSection + "' | sProgramOrSection='" + sProgramOrSection + "' | for the property: '" + sProperty + "'.");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}				
		return objReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_DirectLookup_(FileIniZZZ objFileIniConfig, String sSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean  bReturn = false;
		main:{			
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKey + " Verwende als sSection '"+ sSection + "' für die Suche nach der Property '" + sProperty + "'");			
			if(!StringZZZ.isEmpty(sSection)){
				boolean bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSection);
				if(bSectionExists==true){
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": !!! Section gefunden für '"+ sSection + "'");
					if(!bSetNew || bFlagDelete){				
						boolean bValueExists = objFileIniConfig.proofValueExists(sSection, sProperty);
						if(bValueExists){
							if(bFlagDelete==false){					
								bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
								if (bReturn) {
									System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
									break main;
								}	
							}else{
								bReturn =  objFileIniConfig.deleteProperty(sSection, sProperty, bFlagSaveImmidiate);
								if (bReturn) {
									System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich gelöscht für Property '" + sProperty + "'=''" + sValue + "'");
									break main;
								}
							}
						}
					}else{
						//Setzen, im 2. Durchlauf
						bReturn = objFileIniConfig.setPropertyValue(sSection, sProperty, sValue, bFlagSaveImmidiate);
						if (bReturn) {
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value erfolgreich  (ggfs. konvertiert) gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
							break main;
						} else {
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": !!! Value nicht erfolgreich gesetzt für Property '" + sProperty + "'=''" + sValue + "'");
							break main;
						}
					}
				}else{
					System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Keine Section gefunden für '"+ sSection + "'");
				}	
			}//if(!StringZZZ.isEmpty(sSection)){			
		}//end main;
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_SystemLookup_(FileIniZZZ objFileIniConfig, String sSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate, HashMapMultiIndexedZZZ hmDebug, String sDebugKey) throws ExceptionZZZ{
		
		//TODOGOON: Methode Kann geloescht werden, wenn niergendwol mehr referenziert.
		
		boolean  bReturn = false;
		main:{
						
			if(!StringZZZ.isEmpty(sSection)){
//				CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//				ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
				
//				CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//				ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
				
				CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
				ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
				String sSearchCounter = objCounter.getStringNext();//.getString();
				
				//a) mit Systemkey								
				String sSectionUsed = this.getSystemKey() + "!" + sSection;		
				hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);									
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate);
				if(bReturn) break main;
									
				//b) mit ApplicationKey				
				sSectionUsed = this.getApplicationKey() + "!" + sSection;				
				hmDebug.put(sDebugKey + "." + sSectionUsed, sProperty);						
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate);
				if(bReturn) break main;	

				}//if(!StringZZZ.isEmpty(sSection)){									
		}//End main:
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_AliasSystemLookup_
	(FileIniZZZ objFileIniConfig, String sProgramOrSection, String sProperty, String sValue, boolean bSetNew, boolean bFlagDelete, boolean bFlagSaveImmidiate, HashMapMultiIndexedZZZ hmDebug, String sDebugKey) throws ExceptionZZZ{
		
		  //TODOGOON: Methode kann geloescht werden, wenn niergendwo mehr referenziert.
		boolean  bReturn = false;
		main:{
		if(!StringZZZ.isEmpty(sProgramOrSection)){	
//			CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
//			ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();
			
//			CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//			ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();
			
			//a) mit Systemkey
			String sDebugKeyUsed = sDebugKey + ".a";
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": " + sDebugKeyUsed + " Suche nach einem Alias in '"+ this.getSystemKey() + "' für das Programm oder die Section '" + sProgramOrSection + "'");			
			String sSectionUsed = objFileIniConfig.getPropertyValue(this.getSystemKey(), sProgramOrSection).getValue();	
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			if(!StringZZZ.isEmpty(sSectionUsed)){
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate);
				if(bReturn) break main;		
			}//if(!StringZZZ.isEmpty(sSection)){
				
				
			//b) mit Applicationkey			
			sSectionUsed = objFileIniConfig.getPropertyValue(this.getApplicationKey(), sProgramOrSection).getValue();
			hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionUsed, sProperty);
			if(!StringZZZ.isEmpty(sSectionUsed)){
				bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig, sSectionUsed, sProperty, sValue, bSetNew, bFlagDelete, bFlagSaveImmidiate);
				if(bReturn) break main;				
			}//if(!StringZZZ.isEmpty(sSection)){

			}//if(!StringZZZ.isEmpty(sProgramOrSection)){
		}//end main;
		return bReturn;
	}
	
	/**
	 * 		Merke: Wenn es fuer dieses Program einen Aliasnamen gibt, 
			die Section [Aliasname] aber noch nicht in der ini Datei vorhanden ist,
			dann wird eine entsprechende Section angelegt und die Werte dort hineingeschrieben.
	 * 
	 * @param objFileIniConfigIn
	 * @param sMainSection
	 * @param sProgramOrSection
	 * @param sProperty
	 * @param sValueIn
	 * @param bFlagSaveImmidiate
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.04.2023, 09:10:28
	 */
	private boolean KernelSetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty, String sValueIn, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug; String sSectionUsed; String sValue=new String(""); IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Wird ggfs. verwendet um zu sehen welcher Wert definiert ist. Diesen dann mit dem neuen Wert überschreiben.
		main:{	
			//TODO 20190815: CACHE VERWENDUNG AUCH BEIM SETZEN EINBAUEN
			//TODO 20230410: DAS SETZEN DES CACHE EINTRAGS BEIM SETZEN EINER NEUEN SECTION PASSIERT IMMER NOCH NICHT.
			//
			//			
			//#######################################################################			
			//TODO 20190711: COUNTER AUCH BEIM SETZEN EINBAUEN: sSearchCounter = objCounter.getStringNext();
			//                                                  sDebugKey = "(00)"+ sSearchCounter;
			//###################################################################################################			
		    //IDEE: Mache eine Factory, über die dann ein 'AsciiCounterZweistellig' Objekt erstellt werden kann
			//      Dementsprechend in solch einem Objekt den Startwert speichern ung ggfs. per Konstruktor erstellen.
			//      Dann die objAsciiCounterZweistellig.next() bzw. die objAsciiCounterZweistellig.increase() Methode 
			//      letztere zum Endgültigen setzen und erhöhen des Werts anbieten.
			
			//Hier einen SingletonCounter holen!!!
			//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
			//String sStartValue= "A0";
			//ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ(4,"0", sStartValue);	
			//objHandler.setCounterStrategy(objCounterStrategy);
			//ICounterAlphanumericSignificantZZZ objCounter = objHandler.getCounterFor();

			String sStartValue= "A";
//			CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
//			ICounterStrategyAlphabetZZZ objCounterStrategy = new CounterStrategyAlphabetSerialZZZ(sStartValue);
//			objHandler.setCounterStrategy(objCounterStrategy);
//			ICounterAlphabetZZZ objCounter = objHandler.getCounterFor();
			
			CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
			ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = new CounterStrategyAlphabetSignificantZZZ(4,"0",sStartValue);
			objHandler.setCounterStrategy(objCounterStrategy);
			ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
			String sSearchCounter = objCounter.getStringNext();
			String sDebugKey=null;
			//#################################################################################################
			//1. Konfigurationsfile des Moduls holen
			FileIniZZZ objFileIniConfig = null;
		    if(objFileIniConfigIn==null){
		    	objFileIniConfig = this.searchModuleFileWithProgramAlias(sMainSection, sProgramOrSection);		    	
		    }else{
		    	String stemp = "Verwende übergebenes FileIniZZZ.";
	    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": "+ stemp);
		    	objFileIniConfig = objFileIniConfigIn;
		    }
		   
		    
		    //2. Den Abschnitt holen, MODULALIAS
			String sMainSectionUsed = this.KernelChooseMainSectionUsedForConfigFile_(sMainSection, sProgramOrSection);
			sSectionUsed = null;	
						
			//3. Setzen des Wertes
			boolean bFlagDelete = false;
			if(sValueIn==null){
				bFlagDelete=true;
			}else{
				sValue=sValueIn;
			}			
			
		//+++ AUSSERHALB DER SCHLEIFE: AlIASWERTE HOLEN
		String sApplicationKey = this.getApplicationKey();
		String sSystemNumber= this.getSystemNumber();
		ArrayListZZZ<String>listasAlias = this.getProgramAliasUsed(objFileIniConfig,sMainSectionUsed, sProgramOrSection, sSystemNumber);
										
		//############################################################################################
	    //### Vereinfache den Code, dadurch, dass ein definierte Liste an Schlüsseln abgearbeitet wird.		    
	    //############################################################################################
	    ArrayList<String> alsSectionProgram = AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFileIniConfig, sProgramOrSection,sApplicationKey, sSystemNumber);
	    
	    //1. Hole die Stelle, an der Werte gepflegt sind.
	    int iCounter = -1;		    
	    for(String sSectionProgramUsed : alsSectionProgram) {
	    	sSectionUsed = sSectionProgramUsed;
	    	iCounter++;
	    	sDebugKey = "(" + StringZZZ.padLeft(Integer.toString(iCounter), 2, '0') + ")";
	    	hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionProgramUsed, sProperty);
	    	objReturn = KernelGetParameter_DirectLookup_( objFileIniConfig, sSectionProgramUsed, sProperty);
			if(objReturn.hasAnyValue()) {
				//2. Setze an diese Stelle die Werte.				    
			    bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig , sSectionUsed, sProperty, sValue, false, bFlagDelete, bFlagSaveImmidiate);
				if(bReturn)break main;
			}
	    }	
	    
		if(bFlagDelete){	
			//Falls der Wert nicht gefunden wurde, hier bReturn = false, aber keine Exception
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Section fuer zu löschende Property nicht gefunden. Ende, keine Exception.");
			bReturn = false;
			break main;
		}
	    
	    //Falls kein früherer Wert gefunden worden ist, pruefe, ob die Section fuer den Program Alias vorhanden ist.
		//Falls nein: Erstelle sie
		String sProgramAliasUsed = null;
		boolean bSectionProgramAliasExists = false;
		for(String sProgramAlias: listasAlias) {
			bSectionProgramAliasExists = objFileIniConfig.proofSectionExistsDirectLookup(sProgramAlias);
			if(bSectionProgramAliasExists) {
				sProgramAliasUsed = sProgramAlias;
				break;
			}
		}
		if(!bSectionProgramAliasExists) {
			int iIndexLast = listasAlias.size()-1;
			if(iIndexLast>=0) {
				sProgramAliasUsed=listasAlias.get(iIndexLast); //Nimm den untersten der Liste
				bReturn = objFileIniConfig.createSection(sProgramAliasUsed);
				if(bReturn) {
					//Setze an diese Stelle die Werte.				    
					bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig , sProgramAliasUsed, sProperty, sValue, true, bFlagDelete, bFlagSaveImmidiate);
					if(bReturn)break main;
				}				
			}
		}
		
	    //Falls also die Section existierte
		//und falls dort kein früherer Wert gefunden worden ist, setze den Wert neu, an der ersten Stelle des Suchstrings.
	    //Das gilt auch, wenn ein Alias für die Section nicht definiert ist
	    iCounter = -1;	
	    for(String sSectionProgramUsed : alsSectionProgram) {
	    	sSectionUsed = sSectionProgramUsed;
	    	iCounter++;
	    	sDebugKey = "Setze Wert (" + StringZZZ.padLeft(Integer.toString(iCounter), 2, '0') + ")";
	    	hmDebug.put(sDebugKey + "(" + sSearchCounter + ") " + sSectionProgramUsed, sProperty);
	    	//Setze an diese Stelle die Werte.				    
			bReturn = KernelSetParameterByProgramAlias_DirectLookup_(objFileIniConfig , sSectionProgramUsed, sProperty, sValue, true, bFlagDelete, bFlagSaveImmidiate);
			if(bReturn)break main;				
	    }
		
	    
	    
	    //#####################################################################		
		//Falls der Parameter immer noch nicht gefunden wurde, hier eine Exception auswerfen.
        //Ansonsten droht die Gefahr einer Endlosschleife.
			String sModuleUsed="";
			boolean bModuleConfig = this.proofFileConfigModuleIsConfigured(sMainSection);
			if(bModuleConfig==false){
																	
				//A2. Versuch: Prüfen, ob es als Systemkey konfiguriert ist
				sModuleUsed = this.getSystemKey();
				bModuleConfig = this.proofFileConfigModuleIsConfigured(sModuleUsed);
				if(bModuleConfig==false){
					
					//A3. Versuch: Prüfen, ob es als Applikationskey konfiguriert ist
					sModuleUsed = this.getApplicationKey();
					bModuleConfig = this.proofFileConfigModuleIsConfigured(sModuleUsed);
					if(bModuleConfig==false){
						String stemp = "Wrong parameter: Module '" + sModuleUsed + "' is not configured or property could not be found anywhere in the file in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					} //end if Versuch 3
				} //end if Versuch 2
			}//end if Versuch 1
			
			//B1. Prüfen, ob das Modul existiert
			boolean bModuleExists = this.proofFileConfigModuleExists(sMainSection);
			if(bModuleExists==false){
				String stemp = "Wrong parameter: Module '" + sModuleUsed + "' does not exist or property could not be found anywhere in the file in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//Abbruch der Parametersuche. Ohne diesen else-Zweig, gibt es ggfs. eine Endlosschleife.
			String stemp = "Parameter nicht in der ini-Datei definiert (Modul/Program or Section) '(" + sMainSection + "/" + sProgramOrSection + ")  in the file '" + objFileIniConfig.getFileObject().getAbsolutePath() + "' for the property: '" + sProperty + "'.";
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
			ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_CONFIGURATION_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
	
		}//END main:
		if(bReturn) {
			//!!! DEN CACHE AKTUALISIEREN
//			SICHERHEITSHALBER NUR DEN CACHE AUF DER OBERSTEN EBENE SETZEN, Alternativ ALLE Ebenen des Suchstrings.
//			IKernelConfigSectionEntryZZZ objFromCache = (IKernelConfigSectionEntryZZZ) this.getCacheObject().getCacheEntry(sSectionUsed, sProperty);
//			if(objFromCache!=null){					
//					hmDebug.put("1) To CacheZZZ: " + sSectionUsed, sProperty);
//					objReturn = objFromCache;			
//			}else{
//				hmDebug.put("1) Not in CacheZZZ, put it: " + sSectionUsed, sProperty);
//			}
			
			IKernelConfigSectionEntryZZZ objFromCache = (IKernelConfigSectionEntryZZZ) this.getCacheObject().getCacheEntry(sProgramOrSection, sProperty);
			if(objFromCache!=null){					
					hmDebug.put("FOUND... Update CacheZZZ: " + sProgramOrSection, sProperty);
					objFromCache.setValue(sValue);
					objReturn = objFromCache;						
			}else{
				//Ich habe hier keine IKernelConfiSectionEntryZZZ, das gültig ist, also Formel berechnet, etc.
				hmDebug.put("FOUND... But not in CacheZZZ: Will not jet put an entry for " + sProgramOrSection, sProperty);				
			}
			
			sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICHES ENDE DIESER SUCHE +++ Suchreihenfolge (Section:Property): " + sDebug);
		}else{
			sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ENDE DIESER SUCHE OHNE ERFOLG +++ Suchreihenfolge (Section:Property): " + sDebug);
		}		
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty, String sValueIn, ICryptZZZ objCrypt, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug; String sSectionUsed; String sValue=new String(""); IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Wird ggfs. verwendet um zu sehen welcher Wert definiert ist. Diesen dann mit dem neuen Wert überschreiben.
		main:{	
			//1) Zuerst muss mit Hilfe des CryptAlgorithmus und die Verschluesselung des Eingabewerts erstellt werden.
			String sValueEncrypted = objCrypt.encrypt(sValueIn);
						
			//2) Nun diesen Wert als <Z> - Expression setzen
			bReturn = this.KernelSetParameterByProgramAliasEncrypted_(objFileIniConfigIn, sMainSection, sProgramOrSection, sProperty, sValueEncrypted, objCrypt, bFlagSaveImmidiate);
		}//end main:
		return bReturn;
	}
	
	private boolean KernelSetParameterByProgramAliasEncrypted_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty, String sValueEncryptedIn, ICryptZZZ objCrypt, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug; String sSectionUsed; String sValue=new String(""); IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Wird ggfs. verwendet um zu sehen welcher Wert definiert ist. Diesen dann mit dem neuen Wert überschreiben.
		main:{	
			//1) Zuerst muss aus dem CryptAlgorithmus und dem schon verschluesseltem Wert der <Z> - Expression String erstellt werden.			
			String sValueEncryptedAsExpression = ZFormulaIniLineZZZ.createLineFromEncrypted(sValueEncryptedIn, objCrypt);
						
			//2) Nun diesen Wert normal setzen, wie ohne Verschluesselung
			bReturn = this.KernelSetParameterByProgramAlias_(objFileIniConfigIn, sMainSection, sProgramOrSection, sProperty, sValueEncryptedAsExpression, bFlagSaveImmidiate);
		}//end main:
		return bReturn;
	}
	
	
	/**Merke: Hier wird davon ausgegangen, das Modul - und Programmname identisch sind !!!
	 * FGL 20180909: Müsste nicht eigentlich der Kernel-ApplicationKey als ModulAlias verwendet werden??? Statt Gleichheit???
	* @param sModuleAndProgramAndSection
	* @param sProperty
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 12.01.2007 09:10:41
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty, boolean bUseCache) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleAndProgramAndSection)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing 'String Module/Program/Section'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}			
			}//END check:
		
			//FGL TODO GOON 20180909: Müsste nicht eigentlich der ApplicationKey als ModulAlias verwendet werden?		
			//sReturn = this.getParameterByProgramAlias(sModuleAndProgramAndSection, sModuleAndProgramAndSection, sProperty);
			
			//Versuch einen Alias als Wert zu holen, erst aus dem Systemkey, dann aus dem ApplicationKey
			IKernelConfigSectionEntryZZZ objAlias = this.getSectionAliasFor(sModuleAndProgramAndSection);
			if(objAlias.hasAnyValue()) {
				String sAlias = objAlias.getProperty();
				objReturn = this.getParameterByProgramAlias(sModuleAndProgramAndSection, sAlias,sProperty, bUseCache);
			}else {
				objReturn = this.getParameterByProgramAlias(this.getApplicationKey(), sModuleAndProgramAndSection, sProperty, bUseCache);
			}
			
			//objReturn = this.getParameterByProgramAlias(this.getSystemKey(), sModuleAndProgramAndSection, sProperty, bUseCache);
		}//END main:
		return objReturn;
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ[] getParameterArrayWithEntryByProgramAlias(String sProgramAndSection, String sProperty) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ[] objaReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			
			//### Modulalias holen
			//Versuch einen Alias als Wert zu holen, erst aus dem Systemkey, dann aus dem ApplicationKey
			//Das passt aber nicht... IKernelConfigSectionEntryZZZ objAlias = this.getSectionAliasFor(sModuleAndProgramAndSection);
			//if(objAlias.hasAnyValue()) {
			//	String sAlias = objAlias.getProperty();
			//	hmReturn = this.getParameterArrayWithEntryByProgramAlias(sAlias, sModuleAndProgramAndSection,sProperty);
			//}else {
			    //Applicationkey als Modulalias
				objaReturn = this.getParameterArrayWithEntryByProgramAlias(this.getApplicationKey(), sProgramAndSection, sProperty);
			//}	
		
		}//end main:
		return objaReturn;
	}
	
	//############################################################################
	@Override
	public IKernelConfigSectionEntryZZZ[] getParameterArrayWithEntryByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null; //new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);					
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//END check:
				  
			objaReturn = this.KernelSearchParameterArrayByProgramAlias_(null, sModule, sProgramOrSection, sProperty);
		}//END main:
		return objaReturn;
	}
	
	
	private IKernelConfigSectionEntryZZZ[] KernelSearchParameterArrayByProgramAlias_(FileIniZZZ objFileIniConfigIn, String sMainSection, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ[] objaReturn = null;
		ArrayList<IKernelConfigSectionEntryZZZ>listaReturn= new ArrayList<IKernelConfigSectionEntryZZZ>();				
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
		
		IKernelConfigSectionEntryZZZ objFound=null;
		main:{				
		//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
		//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
		//Aber: Es wird im Cache kein Array gespeichert, sondern der zu zerlegende String !!!
			//  Darum findet hier nicht das Nachsehen im Cache statt. 
					
		//############################
		hmDebug.put("+++ ArrayMethod calling StringMethod for search. Input: " + sMainSection + "," + sProgramOrSection ,sProperty);
		
		IKernelConfigSectionEntryZZZ objReturn =  new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		objReturn.setSection(sProgramOrSection);
		objReturn.setProperty(sProperty);
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objReturn);
		objFound = this.KernelSearchParameterByProgramAlias_(objFileIniConfigIn, sMainSection, sProgramOrSection, sProperty, true, objReturnReference);
		
		sDebug = hmDebug.computeDebugString(" | ",":");
		if(objFound!=null && objFound.hasAnyValue()) {										
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ ArrayMethod calling StringMethod for search. Zerlege gefundenen Wert nun in Array" + sDebug);
			
			//20210808: Nun gesondert JSON-Array verwenden, alternativ zu der Variante Mehrfachwerte mit einem | zu definieren.
			if(objFound.isJsonArray()) {				 
				 try {
					objaReturn = KernelConfigSectionEntryZZZ.explodeJsonArray(objFound);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(objFound.isJsonMap()) {
				try {
					objaReturn = KernelConfigSectionEntryZZZ.explodeJsonMap(objFound);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}else {
				try {
					objaReturn = KernelConfigSectionEntryZZZ.explode(objFound, null);
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
				
				//################
				//DEBUG: Entsprechen die oben eingegebenen Suchstrings auch dem 1. Suchschritt?
				/*
				Set<Integer> setKeyOuter = hmDebug.keySet();
				Integer[] objaKeyOuter = (Integer[]) setKeyOuter.toArray(new Integer[setKeyOuter.size()]);
				Integer obj = objaKeyOuter[0];
				
				//Nun diesen Wert in den Cache übernehmen.
				HashMap<String,String>hmInner = hmDebug.getInnerHashMap(obj);//Das war der 1. Versuch. Er sollte die Eingabewerte beinhalten. Diese sollen die Keys für den Cache sein. Dann wird der Wert beim 2. Aufruf sofort gefunden.
				Set<String> setKey = hmInner.keySet();
				String[] saKey = (String[]) setKey.toArray(new String[setKey.size()]);				
				String sKey = saKey[0];					
				String sValue = hmInner.get(sKey);
				*/
				//###################
				
				
			}else{
				System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ ArrayMethod calling StringMethod for search. Kein Wert zum Zerlegen gefunden." + sDebug);
			}			
		}//END main:
		return objaReturn;
	}
	
	
	
	//################################################################################################
	/** set the parameter of a program to the Module - Configuration - File
	 * Remark: The file will be immediately saved !!! 
	 * @param objFileConfig,       the Module-Configuration-File
	 * @param sProgramAlias,     the Alias-Name of the Program, e.g. this is the Line ProgIP=FGL#01_IP; where ProgIP is the AliasName which leads to the technical section FGL#101_IP
	 * @param sParameter,         the ParameterName in the technical section
	 * @param sValue,                 the Value to be set, if the parameter does not exist, the entry will be created.	if the value is 'null', the property will be deleted from the file.
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		main:{
			check:{
							if(objFileConfig == null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.exists()==false){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.isDirectory()==true){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
											
						}//end check:
	
						//1. Erstellen des FileIni-Objects
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						hmFlag.put(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), true);
					
						FileIniZZZ objFileIni = new FileIniZZZ(this, objFileConfig,hmFlag);
						this.KernelSetParameterByProgramAlias_(objFileIni, null, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);

		}//end main:
	}//end function setParameterByProgramAlias
		
	//################################################################################################
		/** set the parameter of a program to the Module - Configuration - File
		 * Remark: The file will be immediately saved !!! 
		 * @param objFileConfig,       the Module-Configuration-File
		 * @param sProgramAlias,     the Alias-Name of the Program, e.g. this is the Line ProgIP=FGL#01_IP; where ProgIP is the AliasName which leads to the technical section FGL#101_IP
		 * @param sParameter,         the ParameterName in the technical section
		 * @param sValue,                 the Value to be set, if the parameter does not exist, the entry will be created.	if the value is 'null', the property will be deleted from the file.
		 * @throws ExceptionZZZ
		 */
	@Override
	public synchronized void setParameterByProgramAlias(File objFileConfig, String sProgramOrSection, String sProperty, String sValue) throws ExceptionZZZ{
		main:{
			check:{
							if(objFileConfig == null){
								ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.exists()==false){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' does not exist.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}else if(objFileConfig.isDirectory()==true){
								ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter: 'Configuration file-object' is as directory.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
											
						}//end check:
	
						//1. Erstellen des FileIni-Objects
						HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
						hmFlag.put(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), true);
					
						FileIniZZZ objFileIni = new FileIniZZZ(this, objFileConfig,hmFlag);
						this.KernelSetParameterByProgramAlias_(objFileIni, null, sProgramOrSection, sProperty, sValue, true);

		}//end main:
	}//end function setParameterByProgramAlias
		
	
	/**
	 * @param objFileIniConfig, 			FileIniZZZ, Module-Konfiguration-File
	 * @param sProgramAlias
	 * @param sParameter
	 * @param sValueIn
	 * @param bFlagSaveImmediate,   true if the change should be saved immediately, otherwise the change will be kept im memory and stored in objFileConfig
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sProgramOrSection, String sProperty, String sValue, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
	main:{
		try{
		check:{
						if(objFileIniConfig == null){
							ExceptionZZZ ez = new ExceptionZZZ("'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProgramOrSection)){
							ExceptionZZZ ez = new ExceptionZZZ("SectionOrProgram",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProperty)){
							ExceptionZZZ ez = new ExceptionZZZ("Property",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}												
					}//end check:
									
					this.KernelSetParameterByProgramAlias_(objFileIniConfig, null, sProgramOrSection, sProperty, sValue, bFlagSaveImmidiate);

		}catch(ExceptionZZZ ez){
			this.setExceptionObject(ez);
			throw ez;		
		}
									
	}//end main:
}//end function setParameterByProgramAlias	
	
	/**
	 * @param objFileIniConfig, 			FileIniZZZ, Module-Konfiguration-File
	 * @param sProgramAlias
	 * @param sParameter
	 * @param sValueIn
	 * @param bFlagSaveImmediate,   true if the change should be saved immediately, otherwise the change will be kept im memory and stored in objFileConfig
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sProgramOrSection, String sProperty, String sValue) throws ExceptionZZZ{
	main:{
		try{
		check:{
						if(objFileIniConfig == null){
							ExceptionZZZ ez = new ExceptionZZZ("'Configuration file-object'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProgramOrSection)){
							ExceptionZZZ ez = new ExceptionZZZ("SectionOrProgram",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					
						if(StringZZZ.isEmpty(sProperty)){
							ExceptionZZZ ez = new ExceptionZZZ("Property",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}												
					}//end check:
									
					this.KernelSetParameterByProgramAlias_(objFileIniConfig, null, sProgramOrSection, sProperty, sValue, true);

		}catch(ExceptionZZZ ez){
			this.setExceptionObject(ez);
			throw ez;		
		}
									
	}//end main:
}//end function setParameterByProgramAlias	
	
	
	
	/** Use this method only when Modulename, Sectionname and Programname are equal.
	 *   will call .setParameterByProgramAlias(sModuleAndSectionAndProgram, sModuleAndSectionAndProgram, sProperty, sValue);
	 *   
	 *   Remark: The File will be saved immediate.
	 *   
	* @param sModuleAndSectionAndProgram
	* @param sProperty
	* @param sValue
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 16.01.2007 11:08:36
	 */
	@Override
	public synchronized void setParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty, String sValue) throws ExceptionZZZ{
		main:{
				check:{
					if(StringZZZ.isEmpty(sModuleAndProgramAndSection)){
						ExceptionZZZ ez = new ExceptionZZZ("Module/Section/Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
																	
					//Merke: Die Werte werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
				}//end check:
	
				//Modul = Programname
	            //this.KernelSetParameterByProgramAlias_(null, sModuleAndSectionAndProgram, sModuleAndSectionAndProgram, sProperty, sValue, true);
	            
	          //Idee: 20180909: Müsste nicht eigentlich der ApplicationKey als ModulAlias verwendet werden?
			//this.KernelSetParameterByProgramAlias_(null, this.getApplicationKey(), sModuleAndSectionAndProgram, sProperty, sValue, true);
			//this.KernelSetParameterByProgramAlias_(null, this.getSystemKey(), sModuleAndSectionAndProgram, sProperty, sValue, true);
			
			//202100305: Versuch einen Alias als Wert zu holen, erst aus dem Systemkey, dann aus dem ApplicationKey
			IKernelConfigSectionEntryZZZ objAlias = this.getSectionAliasFor(sModuleAndProgramAndSection);
			if(objAlias.hasAnyValue()) {
				String sAlias = objAlias.getValue();
				try {
					this.KernelSetParameterByProgramAlias_(null, this.getSystemKey(), sAlias, sProperty,sValue, true);
				}catch(ExceptionZZZ ez) {
					this.KernelSetParameterByProgramAlias_(null, this.getApplicationKey(), sAlias, sProperty,sValue, true);
				}
			}else {
				try {
					this.KernelSetParameterByProgramAlias_(null, this.getSystemKey(), sModuleAndProgramAndSection, sProperty, sValue, true);
				}catch(ExceptionZZZ ez) {
					this.KernelSetParameterByProgramAlias_(null, this.getApplicationKey(), sModuleAndProgramAndSection, sProperty, sValue, true);
				}
			}
						
		}
	}
	
	/** void, set a value to a property in a section. The section is either provided directly or will be the value of another property in the systemkey-section (this i call then a 'program'). 
	 * Remark: The change wil be saved immidiately, because you have no chance to get a handle on the FileIniZZZ-Object which is used internally.
	* Lindhauer; 20.05.2006 09:40:36
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @param sValue
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue) throws ExceptionZZZ{			
			main:{
						check:{
							if(StringZZZ.isEmpty(sModule)){
								ExceptionZZZ ez = new ExceptionZZZ("Module",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
																			
							//Merke: Die Werte werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
						}//end check:
	
						if(sModule.equals(sSectionOrProgram)){
							this.setParameterByModuleAlias(sModule, sProperty, sValue, true); //Es muss sofort gespeichert werden, da man keine chance hat auf das noch nicht gespeicherte FiniIni-Objekt von aussen zuzugreifen und dieses anschliessend zu speichern.
						}else{					
							this.KernelSetParameterByProgramAlias_(null, sModule, sSectionOrProgram, sProperty, sValue, true);
						}
		}//end main:
	}
	
	/** void, set a value to a property in a section. The section is either provided directly or will be the value of another property in the systemkey-section (this i call then a 'program'). 
	 * Remark: The change wil be saved immidiately, because you have no chance to get a handle on the FileIniZZZ-Object which is used internally.
	* Lindhauer; 20.05.2006 09:40:36
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @param sValue
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty, String sValue, boolean bSaveImidiately) throws ExceptionZZZ{			
			main:{
						check:{
							if(StringZZZ.isEmpty(sModule)){
								ExceptionZZZ ez = new ExceptionZZZ("Module",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
																			
							//Merke: Die Werte werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
						}//end check:
	
						if(sModule.equals(sSectionOrProgram)){
							this.setParameterByModuleAlias(sModule, sProperty, sValue, bSaveImidiately);
						}else{					
							this.KernelSetParameterByProgramAlias_(null, sModule, sSectionOrProgram, sProperty, sValue, bSaveImidiately);
						}
		}//end main:
	}
	
	/** void, set a value to a property in a section. The section is either provided directly or will be the value of another property in the systemkey-section (this i call then a 'program'). 
	 * Remark: The change wil be saved immidiately, because you have no chance to get a handle on the FileIniZZZ-Object which is used internally.
	 * 
	 * Hier wird ein bereits verschluesselter Wert als ini-Wert gesetzt.
	 * Dabei werden die Verschluesselungsparameter aus objCrypt in diese Zeile korrekt aufgenommen.
	* Lindhauer; 20.05.2006 09:40:36
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @param sValue
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAliasEncrypted(String sModule, String sSectionOrProgram, String sProperty, String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ{			
			main:{
				check:{
					if(StringZZZ.isEmpty(sModule)){
						ExceptionZZZ ez = new ExceptionZZZ("Module",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					if(objCrypt==null){
						ExceptionZZZ ez = new ExceptionZZZ("Crypt-Algorithmtype",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
																	
					//Merke: Die Werte selbst werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
				}//end check:

				if(sModule.equals(sSectionOrProgram)){
					this.setParameterByProgramAliasEncrypted(sSectionOrProgram, sProperty, sValue, objCrypt);
				}else{					
					this.KernelSetParameterByProgramAliasEncrypted_(null, sModule, sSectionOrProgram, sProperty, sValue, objCrypt, true);
				}
		}//end main:
	}
	
	@Override
	public synchronized void setParameterByProgramAlias(String sSectionOrProgram, String sProperty, String sEncrypted,
			ICryptZZZ objCrypt) throws ExceptionZZZ {
		this.KernelSetParameterByProgramAlias_(null, null, sSectionOrProgram, sProperty, sEncrypted, objCrypt, true);
	}

	@Override
	public synchronized void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ {
		if(objCrypt==null){
			ExceptionZZZ ez = new ExceptionZZZ("Crypt-Algorithmtype",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(sModule.equals(sSectionOrProgram)){
			this.setParameterByProgramAlias(sSectionOrProgram, sProperty, sEncrypted, objCrypt);
		}else{					
			this.KernelSetParameterByProgramAlias_(null, sModule, sSectionOrProgram, sProperty, sEncrypted, objCrypt, true);
		}
		
	}	
	
	/** void, set a value to a property in a section. The section is either provided directly or will be the value of another property in the systemkey-section (this i call then a 'program'). 
	 * Remark: The change wil be saved immidiately, because you have no chance to get a handle on the FileIniZZZ-Object which is used internally.
	 * 
	 * Hier wird ein bereits verschluesselter Wert als ini-Wert gesetzt.
	 * Dabei werden die Verschluesselungsparameter aus objCrypt in diese Zeile korrekt aufgenommen.
	* Lindhauer; 20.05.2006 09:40:36
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @param sValue
	 * @throws ExceptionZZZ
	 */
	@Override
	public synchronized void setParameterByProgramAliasEncrypted(String sSectionOrProgram, String sProperty, String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ{			
			main:{
				check:{
					if(StringZZZ.isEmpty(sSectionOrProgram)){
						ExceptionZZZ ez = new ExceptionZZZ("Section or Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					if(objCrypt==null){
						ExceptionZZZ ez = new ExceptionZZZ("Crypt-Algorithmtype",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
																	
					//Merke: Die Werte selbst werden nicht gecheckt. Ein NULL-Wert bedeutet entfernen der Property aus dem ini-File
				}//end check:
					
				this.KernelSetParameterByProgramAliasEncrypted_(null, null, sSectionOrProgram, sProperty, sValue, objCrypt, true);			
		}//end main:
	}
	
	/**Returns an array-object of a configured ini value. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the Separator is not provided a default one will be used.
	 *  
	 * @param sModule
	 * @param sProperty
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 07.04.2020, 08:06:58
	 */
	@Override
	public String[] getParameterArrayStringByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
			HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			//String sFileName = this.getParameterByModuleAlias(sModule, sProperty).getValue();			
			IKernelConfigSectionEntryZZZ[] objaEntry = this.getParameterByModuleAliasAsArray(sModule, sProperty);
			if(objaEntry!=null){
				
				//Hole getValue aus jedem entry und packe es in eine ArrayList, die dann als StringArray zurückgegeben wird.
				ArrayList<String> listasEntry = new ArrayList<String>();
				for(int iCounter=0; iCounter <= objaEntry.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntry = objaEntry[iCounter];
					
					//20240509: Rette die Suchschritte
					HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
					hmSearched.add(hmSearchedTemp);
					
					String sEntry = objEntry.getValue();					
					listasEntry.add(sEntry);
				}
				saReturn = ArrayListUtilZZZ.toStringArray(listasEntry);			
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return saReturn;
	}
		
	

	@Override
	public String[] getParameterArrayWithStringByProgramAlias(String sProgramAndSection, String sProperty)throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			//### Modulalias holen
			//Versuch einen Alias als Wert zu holen, erst aus dem Systemkey, dann aus dem ApplicationKey
			//Das passt aber nicht... IKernelConfigSectionEntryZZZ objAlias = this.getSectionAliasFor(sModuleAndProgramAndSection);
			//if(objAlias.hasAnyValue()) {
			//	String sAlias = objAlias.getProperty();
			//	hmReturn = this.getParameterArrayWithStringByProgramAlias(sAlias, sModuleAndProgramAndSection,sProperty);
			//}else {
			    //Applicationkey als Modulalias
				saReturn = this.getParameterArrayWithStringByProgramAlias(this.getApplicationKey(), sProgramAndSection, sProperty);
			//}	
		}//end main:
		return saReturn;
	}
	
	/**Returns an array-object on a configured value. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the Separator is not provided a default one will be used.
	 *  
	 * @param sModule
	 * @param sSectionOrProgram
	 * @param sProperty
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 07.04.2020, 08:10:43
	 */
	@Override
	public String[] getParameterArrayWithStringByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
			HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			//String sFileName = this.getParameterByProgramAlias(sModule, sSectionOrProgram, sProperty).getValue();
			IKernelConfigSectionEntryZZZ[] objaEntry = this.getParameterArrayWithEntryByProgramAlias(sModule, sSectionOrProgram, sProperty);
			if(objaEntry!=null){
				
				//Hole getValue aus jedem Entry und packe es in eine ArrayList, die dann als StringArray zurückgegeben wird.
				ArrayList<String> listasEntry = new ArrayList<String>();
				for(int iCounter=0; iCounter <= objaEntry.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntry = objaEntry[iCounter];
					
					//20240509: Rette die Suchschritte
					HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
					hmSearched.add(hmSearchedTemp);
					
					String sEntry = objEntry.getValue();					
					listasEntry.add(sEntry);
				}
				saReturn = ArrayListUtilZZZ.toStringArray(listasEntry);			
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return saReturn;
	}
	
	@Override
	public Boolean getParameterBooleanByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		Boolean objReturn = null;		
		main:{
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
			HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			IKernelConfigSectionEntryZZZ objEntry = this.getParameterByProgramAlias(sModule, sProgramOrSection, sProperty);
			
			//20240509: Rette die Suchschritte
			HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
			hmSearched.add(hmSearchedTemp);
			
			if(objEntry.hasAnyValue()) {								
				String sValue = objEntry.getValue();
				boolean bValue = BooleanZZZ.stringToBoolean(sValue);
				objReturn = new Boolean(bValue);
			}else {
				objReturn = new Boolean(false);
			}		
		}//END main:
		return objReturn;
	}
	
	@Override
	public HashMap<String,String>getParameterHashMapWithStringByProgramAlias(String sProgramAndSection, String sProperty) throws ExceptionZZZ{
		HashMap<String,String>hmReturn = null;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
			
		main:{				
			//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
			//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
			//Aber:  Es wird im Cache kein Array gespeichert, sondern der zu zerlegende String !!!
			//       Darum findet hier nicht das Nachsehen im Cache statt. 
			
			
			//### Applicationkey als Modulalias
			hmReturn = this.getParameterHashMapWithStringByProgramAlias(this.getApplicationKey(), sProgramAndSection, sProperty);						
		}
		return hmReturn;
	}
	
	@Override
	public HashMapIndexedObjectZZZ<Integer,IKernelConfigSectionEntryZZZ>getParameterHashMapWithEntryByProgramAlias(String sProgramAndSection, String sProperty) throws ExceptionZZZ{
		HashMapIndexedObjectZZZ<Integer,IKernelConfigSectionEntryZZZ>hmReturn = null;		
		String sDebug;
			
		main:{				
			//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
			//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
			//Aber:  Es wird im Cache kein Array gespeichert, sondern der zu zerlegende String !!!
			//       Darum findet hier nicht das Nachsehen im Cache statt. 
						
			//### Applicationkey als Modulalias
			hmReturn = this.getParameterHashMapWithEntryByProgramAlias(this.getApplicationKey(), sProgramAndSection, sProperty);					
		}
		return hmReturn;
	}
	
	@Override
	public HashMap<String,String>getParameterHashMapWithStringByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		HashMap<String,String>hmReturn = null;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
			
		main:{				
			//Merke: Die Eingabewerte werden ggfs. als Schlüssel für den Cache verwendet.
			//       Sowohl für die Suche im Cache, als auch für das Speichern im Cache. Dadurch wird Performance erreicht.
			//Aber:  Es wird im Cache kein Array gespeichert, sondern der zu zerlegende String !!!
			//       Darum findet hier nicht das Nachsehen im Cache statt. 
						
			//############################
			hmDebug.put("+++ HashMapMethod calling StringMethod for search. Input: " + sModule + "," + sProgramOrSection ,sProperty);
			
			FileIniZZZ objFileIniConfig = this.searchModuleFileWithProgramAlias(sModule, sProgramOrSection);		    	

			IKernelConfigSectionEntryZZZ objReturn =  new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			objReturn.setSection(sProgramOrSection);
			objReturn.setProperty(sProperty);

			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);						
			objReturn = this.KernelSearchParameterByProgramAlias_(objFileIniConfig, sModule, sProgramOrSection, sProperty, true, objReturnReference);
			String sRaw = null;
			if(!objReturn.hasAnyValue()) {
				hmDebug.put("No value found for property", sProperty);
				break main;
			}else {
				sRaw = objReturn.getRaw(); //Merke: .getValue würde ggfs. den Text einer HashMap in Debug-Form zurueckliefern, wie: UIText01	| TESTWERT2DO2JSON01 \n UIText02	| TESTWERT2DO2JSON02 
				hmDebug.put("Raw value '" + sRaw + "' found for property", sProperty);
			}
						
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceJsonMap = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();			
			objReturnReference.set(objReturn);						
			ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();			                                                  			                                                				                                                  
			boolean bSuccess = KernelConfigSectionEntryUtilZZZ.getJsonMapSolved(objFileIniConfig, sRaw, true, null, objReturnReferenceJsonMap, objhmReturnValueJsonSolved);
			objReturn = objReturnReferenceJsonMap.get();
			if(!bSuccess) {
				hmDebug.put("No hashmap value found in '" + sRaw + "' for property", sProperty);
				break main;
			}

			hmDebug.put("Hashmap value found in '" + sRaw + "' for property", sProperty);
			hmReturn = objhmReturnValueJsonSolved.get();
			//Merke: Hier wird keine HashMap von Entry-Objekten zurückgegeben, sondern die HashMap selbst
			//       Darum nicht: System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ HashMapMethod calling StringMethod for search. Zerlege gefundenen Wert nun in HahMap" + sDebug);
				
			
			sDebug = hmDebug.computeDebugString(" | ",":");			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "+++ HashMapMethod calling StringMethod for search. Uebernehme gefundenen Wert " + sDebug);
			objReturn.isJson(true);
			objReturn.isJsonMap(true);
			objReturn.setValue(hmReturn);
		}//end main:
		return hmReturn;
	}
	
	

	@Override
	public HashMapIndexedObjectZZZ<Integer, IKernelConfigSectionEntryZZZ> getParameterHashMapWithEntryByProgramAlias(String sModuleAlias, String sProgramOrSection, String sProperty) throws ExceptionZZZ {
		HashMapIndexedObjectZZZ<Integer, IKernelConfigSectionEntryZZZ>hmReturn = null;
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ();//Speichere hier die Suchwerte ab, um sie später zu Debug-/Analysezwecken auszugeben.
		String sDebug;
			
		main:{		
			
			hmDebug.put("+++ HashMapMethod calling SectionEntry-Method. Input: " + sModuleAlias + "," + sProgramOrSection ,sProperty);
			
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
			HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			//Erst einmal das Array holen. 
			//Merke: Die Werte im Array sind geclont 
			//und es soll bei einer HashMap entsprechende Flags und Werte (z.B. speziell für HashMap ein Aliaswert) gesetzt sein.
			IKernelConfigSectionEntryZZZ[] objaEntry = this.getParameterArrayWithEntryByProgramAlias(sModuleAlias, sProgramOrSection, sProperty);
			if(objaEntry!=null){
				
				//Packe jeden Entry in eine HashMap. Verwende dabei den Index des Entrywerts als Schlüssel für die Indizierte HashMap.
				hmReturn = new HashMapIndexedObjectZZZ<Integer,IKernelConfigSectionEntryZZZ>();
				for(int iCounter=0; iCounter <= objaEntry.length-1;iCounter++) {
					IKernelConfigSectionEntryZZZ objEntry = objaEntry[iCounter];
					
					//20240509: Rette die Suchschritte
					HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
					hmSearched.add(hmSearchedTemp);
					
					int iIndex = objEntry.getIndex();
					Integer intIndex = new Integer(iIndex);
					hmReturn.put(intIndex, objEntry);
				}		
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		
			sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + "+++ HashMapMethod calling SectionEntry-Method. Uebernehme gefundenen Wert " + sDebug);
		}
		return hmReturn;
	}

	
	
	
	/** Return a file-object on a configured file name. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the configured file name is not absolute, the path of the module-configuration-file will be used.
	 *  
	* @param sModule
	* @param sSectionOrProgram
	* @param sProperty
	* @param sValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 09.02.2007 10:39:55
	 */
	@Override
	public File getParameterFileByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		File fileReturn = null;
		main:{
			String sFileName = this.getParameterByModuleAlias(sModule, sProperty).getValue();
			if(!StringZZZ.isEmpty(sFileName)){
				
				//20190220: Da die Datei im Classpath vermutet wird, ist der absolute Pfad ein anderer. Diesen suchen.
				fileReturn = FileEasyZZZ.searchFile(sFileName);
								
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return fileReturn;
	}
	
	/** Return a file-object on a configured file name. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the configured file name is not absolute, the path of the module-configuration-file will be used.
	 *  
	* @param sModule
	* @param sSectionOrProgram
	* @param sProperty
	* @param sValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 09.02.2007 10:39:55
	 */
	@Override
	public File getParameterFileByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		File fileReturn = null;
		main:{
			String sFileName = this.getParameterByProgramAlias(sModule, sSectionOrProgram, sProperty).getValue(); 
			if(!StringZZZ.isEmpty(sFileName)){
				
				//20190220: Da die Datei im Classpath vermutet wird, ist der absolute Pfad ein anderer. Diesen suchen.
				fileReturn = FileEasyZZZ.searchFile(sFileName);
							
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return fileReturn;
	}
	
	/** Return a file-object on a configured file name. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the configured file name is not absolute, the path of the module-configuration-file will be used.
	 *  
	* @param sModule
	* @param sSectionOrProgram
	* @param sProperty
	* @param sValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 09.02.2007 10:39:55
	 */
	@Override
	public ImageIcon getParameterImageIconByModuleAlias(String sModule, String sProperty) throws ExceptionZZZ{
		ImageIcon iconReturn = null;
		main:{
			File objFile = this.getParameterFileByModuleAlias(sModule, sProperty);
			String sFilePath=null;
			if(objFile!=null){
				sFilePath = objFile.getAbsolutePath();
				if(objFile.length()>0){					
					this.getLogObject().writeLineDate("Absoluter Pfad für das ImageIcon: " + sFilePath);
					iconReturn = new ImageIcon(sFilePath);					
				}else{
					String sLog = "Parameter wrong configured. ImageSize = 0 '" + sProperty + "' (File may not exist: '" + sFilePath + "')";
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					this.getLogObject().writeLineDate(sLog);
				}						
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return iconReturn;
	}
	
	/** Return a file-object on a configured file name. 
	 * Because it is used very often, this comfortabel method is available.
	 * Remark: If the configured file name is not absolute, the path of the module-configuration-file will be used.
	 *  
	* @param sModule
	* @param sSectionOrProgram
	* @param sProperty
	* @param sValue
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 09.02.2007 10:39:55
	 */
	@Override
	public ImageIcon getParameterImageIconByProgramAlias(String sModule, String sSectionOrProgram, String sProperty) throws ExceptionZZZ{
		ImageIcon iconReturn = null;
		main:{
			File objFile = this.getParameterFileByProgramAlias(sModule, sSectionOrProgram, sProperty);
			String sFilePath=null;
			if(objFile!=null){
				sFilePath = objFile.getAbsolutePath();
				if(objFile.length()>0){					
					this.getLogObject().writeLineDate("Absoluter Pfad für das ImageIcon: " + sFilePath);
					iconReturn = new ImageIcon(sFilePath);					
				}else{					
					String sLog = "Parameter wrong configured. ImageSize = 0 '" + sProperty + "' (File may not exist: '" + sFilePath + "')";
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					this.getLogObject().writeLineDate(sLog);					
				}		
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("No parameter configured '" + sProperty + "'", iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				
				throw ez;
			}
		}
		return iconReturn;
	}
	
	@Override
	public String searchAliasForProgram(String sProgramName) throws ExceptionZZZ{		
		String sReturn = null;
		main:{
			if(sProgramName == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ProgramName'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sProgramName.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'ProgramName'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			String sModule = this.getApplicationKey();
			IKernelConfigSectionEntryZZZ entry = KernelSearchAliasForProgram_(sModule, sProgramName);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
				
			sReturn = entry.getValue();			
		}
		return sReturn;
	}
	
	@Override
	public String searchAliasForProgram(String sModule, String sProgramName) throws ExceptionZZZ{		
		String sReturn = null;
		main:{
			if(sModule == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sModule.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'Module'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			
			if(sProgramName == "") {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ProgramName'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sProgramName.equals("")) {
				ExceptionZZZ ez = new ExceptionZZZ("Empty parameter: 'ProgramName'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}		
						
			IKernelConfigSectionEntryZZZ entry = KernelSearchAliasForProgram_(sModule, sProgramName);
			if(entry==null) break main;
			if(!entry.hasAnyValue())break main;
				
			sReturn = entry.getValue();			
		}
		return sReturn;
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ getProgramAliasFor(String sModuleOrProgram) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleOrProgram)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing 'String Module/Program/Section'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}			
			}//END check:

			objReturn = this.getSectionAliasFor(sModuleOrProgram);
		}//END main:
		return objReturn;
	}
	
	
	
	/**
	 * @param objFileIniConfig
	 * @param sMainSection
	 * @param sProgramOrAlias
	 * @param sSystemNumber
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public ArrayListZZZ<String> getProgramAliasUsed(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ{
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ(); //Für die durchgeführten Suchen. Statt so vieler system.out Anweisungen.
		//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
		//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
		CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
		ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
		String sSearchCounter = objCounter.getStringNext(); // objCounter.current();
		main:{
			String sProgramNameUsed;
			if(StringZZZ.isEmpty(sMainSection)){
				ExceptionZZZ ez = new ExceptionZZZ("Module/Section",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sProgramOrAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				sProgramNameUsed = sProgramOrAlias;
			}
				
			IKernelConfigSectionEntryZZZ objEntry = null;
			String sProgramAliasUsed=null; boolean bSectionExists= false;
			String sPropertyUsed=null;String sDebugKey = null;
						
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
		    HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
			
			//WICHTIG:
			//WENN DIESER PROGRAMNAME NICHT GEFUNDEN WURDE, DANN IST DAFUER GGFS EIN ALIAS DEFINIERT.
			//DEFINITION DES ALIAS DANN AUF OBERER EBENE: APPLICATIONKEY ! SYSTEMNUMBER
			//                                  ODER NUR: APPLICATIONKEY
			String sApplicationKey = this.getApplicationKey();
			FileIniZZZ objFileConfigIni = this.getFileConfigKernelIni();
			ArrayList<String>alsSection=AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(objFileConfigIni, sProgramNameUsed, sApplicationKey, sSystemNumber);
			
			sPropertyUsed = sProgramNameUsed;			
			for(String sSectionUsed : alsSection) {
				
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				
				bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
				if(bSectionExists==true){
					//Nun in der Section nach dem Wert für das Programm suchen.
					objEntry = objFileIniConfig.getPropertyValue(sSectionUsed, sPropertyUsed); 

					//20240509: Rette die Suchschritte
					HashMapMultiIndexedZZZ hmSearchedTemp = objEntry.getSectionsSearchedHashMap();
					hmSearched.add(hmSearchedTemp);
					
					if(objEntry.hasAnyValue()){
						sProgramAliasUsed = objEntry.getValue();
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
						listasReturn.add(sProgramAliasUsed+ FileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber);//Den gefundenen Programalias zuerst um die SystemNumber erweitern.					
						listasReturn.add(sProgramAliasUsed);
						sDebugKey = "(a.a)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' KEINEN Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
			}			
		}//end main:
		
		
		if(listasReturn.size()>=1){
			String sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICH PROGRAMALIASSE GEFUNDEN +++ Suchreihenfolge (Section:Property): " + sDebug);
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": KEINE PROGRAMALIASSE GEFUNDEN");
		}
		return listasReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ searchModuleAliasFor(IKernelFileIniZZZ objFileIniConfig, String sModule, String sSystemNumber, HashMapMultiIndexedZZZ hmDebug) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			check:{
				if(objFileIniConfig==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniConfig'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sModule)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
												
				if(StringZZZ.isEmpty(sSystemNumber)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'SystemNumber'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//end check:										
				
		
		objReturn = searchModuleAliasFor_(objFileIniConfig, sModule, null, sSystemNumber, hmDebug);
		   
		}//end main:
		return objReturn;
	}
	
	public static IKernelConfigSectionEntryZZZ searchModuleAliasFor(IKernelFileIniZZZ objFileIniConfig, String sModule, String sApplicationOrModule, String sSystemNumber, HashMapMultiIndexedZZZ hmDebug) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			check:{
				if(objFileIniConfig==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniConfig'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sModule)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sApplicationOrModule)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
					
				if(StringZZZ.isEmpty(sSystemNumber)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'SystemNumber'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//end check:										
				

			objReturn = searchModuleAliasFor_(objFileIniConfig, sModule, sApplicationOrModule, sSystemNumber, hmDebug);
		
//		    //20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
//		    HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
//		    
//				
//			//###############################################
//			//0. Suche nach dem Modul in den Systemkeys	
//			ArrayList<String> listasSystemKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
//			for(String sSystemKey : listasSystemKey) {								
//				if(hmDebug!=null) hmDebug.put("Im SystemKey: " + sSystemKey, sModule);					
//							
//				//Zuerst den im SystemKey definierten Modulnamen finden.
//				//20240509 Merke: Dadurch, das hier der "Entry" jedes Mal neu geholt wird, sind vorherige Suchen (gespeichert in der SearchedHashMap) immer weg.
//				IKernelConfigSectionEntryZZZ objEntrySectionModuleByApplicationKey = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIniConfig, sSystemKey, sModule);
//				
//				//20240509: Rette die Suchschritte
//				HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionModuleByApplicationKey.getSectionsSearchedHashMap();
//				hmSearched.add(hmSearchedTemp);
//				
//				if(objEntrySectionModuleByApplicationKey.hasAnyValue()) {
//					String sModuleAlias = objEntrySectionModuleByApplicationKey.getValue();
//					System.out.println("ModulAlias: '"+ sModuleAlias + "' gefunden im Systemkey '" + sSystemKey + "'");										
//
//					//Prüfe nun, ob die Section auch existiert....						
//					ArrayList<String>listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
//					for(String sModuleSection : listasModuleSection) {
//						boolean bExists = objFileIniConfig.proofSectionExistsDirectLookup(sModuleSection);
//						
//						if(bExists) {																									
//							objReturn = objEntrySectionModuleByApplicationKey;
//							
//							//20240509: Bewahre den Suchpfad
//							objReturn.setSectionsSearchedHashMap(hmSearched);
//							break main;					
//						}else {
//							System.out.println("Module '" + sModuleSection + "' exisiert nicht im SystemKey '" + sSystemKey + "'.");
//						}
//					}//end for
//				}
//			}//end for		
//		
//		//###############################################
//		//1. Suche nach dem Modul in den Modelkeys	
//		ArrayList<String> listasModuleKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sModule, sSystemNumber);
//		for(String sModuleKey : listasModuleKey) {								
//			if(hmDebug!=null) hmDebug.put("Im ModuleKey: " + sModuleKey, sModule);					
//						
//			//Zuerst den im SystemKey definierten Modulnamen finden.
//			IKernelConfigSectionEntryZZZ objEntrySectionModuleByModuleKey = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIniConfig, sModuleKey, sModule);
//			
//			//20240509: Rette die Suchschritte
//			HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionModuleByModuleKey.getSectionsSearchedHashMap();
//			hmSearched.add(hmSearchedTemp);
//
//			
//			if(objEntrySectionModuleByModuleKey.hasAnyValue()) {
//				String sModuleAlias = objEntrySectionModuleByModuleKey.getValue();
//				System.out.println("ModulAlias: '"+ sModuleAlias + "' gefunden im Systemkey '" + sModuleKey + "'");										
//
//				//Prüfe nun, ob die Section auch existiert....						
//				ArrayList<String>listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
//				for(String sModuleSection : listasModuleSection) {
//					if(hmDebug!=null) hmDebug.put("Prüfe auf Existenz der Section in der Datei: " + objFileIniConfig.getFileObject().getAbsolutePath(), sModuleSection);					
//					
//					boolean bExists = objFileIniConfig.proofSectionExistsDirectLookup(sModuleSection);
//					
//					if(bExists) {																									
//						objReturn = objEntrySectionModuleByModuleKey;
//						
//						//20240509: Bewahre den Suchpfad
//						objReturn.setSectionsSearchedHashMap(hmSearched);
//						break main;					
//					}else {
//						System.out.println("Module '" + sModuleSection + "' exisiert nicht in der Section in der Datei: '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'.");
//					}
//				}//end for
//			}
//		}//end for		
//		
//		//20240509: Bewahre den Suchpfad
//		objReturn.setSectionsSearchedHashMap(hmSearched);
//		
		}//end main:
		return objReturn;
	}
	
	private static IKernelConfigSectionEntryZZZ searchModuleAliasFor_(IKernelFileIniZZZ objFileIniConfig, String sModule, String sApplicationOrModule, String sSystemNumber, HashMapMultiIndexedZZZ hmDebug) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
//			check:{
//				if(objFileIniConfig==null) {
//					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniConfig'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
//				if(StringZZZ.isEmpty(sModule)) {
//					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Module'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
////				if(StringZZZ.isEmpty(sApplicationOrModule)) {
////					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
////					throw ez;
////				}
//					
//				if(StringZZZ.isEmpty(sSystemNumber)) {
//					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'SystemNumber'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}	
//			}//end check:										
				

		    //20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
		    HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
		    
				
			//###############################################
			//0. Suche nach dem Modul in den Systemkeys	
		    if(!StringZZZ.isEmpty(sApplicationOrModule)) {
				ArrayList<String> listasSystemKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
				for(String sSystemKey : listasSystemKey) {								
					if(hmDebug!=null) hmDebug.put("Im SystemKey: " + sSystemKey, sModule);					
								
					//Zuerst den im SystemKey definierten Modulnamen finden.
					//20240509 Merke: Dadurch, das hier der "Entry" jedes Mal neu geholt wird, sind vorherige Suchen (gespeichert in der SearchedHashMap) immer weg.
					IKernelConfigSectionEntryZZZ objEntrySectionModuleByApplicationKey = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIniConfig, sSystemKey, sModule);
					
					//20240509: Rette die Suchschritte
					HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionModuleByApplicationKey.getSectionsSearchedHashMap();
					hmSearched.add(hmSearchedTemp);
					
					if(objEntrySectionModuleByApplicationKey.hasAnyValue()) {
						String sModuleAlias = objEntrySectionModuleByApplicationKey.getValue();
						System.out.println("ModulAlias: '"+ sModuleAlias + "' gefunden im Systemkey '" + sSystemKey + "'");										
	
						//Prüfe nun, ob die Section auch existiert....						
						ArrayList<String>listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
						for(String sModuleSection : listasModuleSection) {
							boolean bExists = objFileIniConfig.proofSectionExistsDirectLookup(sModuleSection);
							
							if(bExists) {																									
								objReturn = objEntrySectionModuleByApplicationKey;
								
								//20240509: Bewahre den Suchpfad
								objReturn.setSectionsSearchedHashMap(hmSearched);
								break main;					
							}else {
								System.out.println("Module '" + sModuleSection + "' exisiert nicht im SystemKey '" + sSystemKey + "'.");
							}
						}//end for
					}
				}//end for		
		    }
		
		//###############################################
		//1. Suche nach dem Modul in den Modelkeys	
		ArrayList<String> listasModuleKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sModule, sSystemNumber);
		for(String sModuleKey : listasModuleKey) {								
			if(hmDebug!=null) hmDebug.put("Im ModuleKey: " + sModuleKey, sModule);					
						
			//Zuerst den im SystemKey definierten Modulnamen finden.
			IKernelConfigSectionEntryZZZ objEntrySectionModuleByModuleKey = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIniConfig, sModuleKey, sModule);
			
			//20240509: Rette die Suchschritte
			HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionModuleByModuleKey.getSectionsSearchedHashMap();
			hmSearched.add(hmSearchedTemp);

			
			if(objEntrySectionModuleByModuleKey.hasAnyValue()) {
				String sModuleAlias = objEntrySectionModuleByModuleKey.getValue();
				System.out.println("ModulAlias: '"+ sModuleAlias + "' gefunden im Systemkey '" + sModuleKey + "'");										

				//Prüfe nun, ob die Section auch existiert....						
				ArrayList<String>listasModuleSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sModuleAlias, sSystemNumber);
				for(String sModuleSection : listasModuleSection) {
					if(hmDebug!=null) hmDebug.put("Prüfe auf Existenz der Section in der Datei: " + objFileIniConfig.getFileObject().getAbsolutePath(), sModuleSection);					
					
					boolean bExists = objFileIniConfig.proofSectionExistsDirectLookup(sModuleSection);
					
					if(bExists) {																									
						objReturn = objEntrySectionModuleByModuleKey;
						
						//20240509: Bewahre den Suchpfad
						objReturn.setSectionsSearchedHashMap(hmSearched);
						break main;					
					}else {
						System.out.println("Module '" + sModuleSection + "' exisiert nicht in der Section in der Datei: '" + objFileIniConfig.getFileObject().getAbsolutePath() + "'.");
					}
				}//end for
			}
		}//end for		
		
		//20240509: Bewahre den Suchpfad
		objReturn.setSectionsSearchedHashMap(hmSearched);
		
		}//end main:
		return objReturn;
	}
	
	
	//#####################################
	public static IKernelConfigSectionEntryZZZ searchProgramAliasFor(IKernelFileIniZZZ objFileIniConfig, String sProgram, String sApplicationOrModule, String sSystemNumber, HashMapMultiIndexedZZZ hmDebug) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			check:{
				if(objFileIniConfig==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIniConfig'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sApplicationOrModule)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sProgram)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sSystemNumber)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'SystemNumber'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//end check:										
				
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
		    HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
		    
				
			//###############################################
			//0. Suche nach dem Program in den Systemkeys	
			objReturn.setProperty(sProgram);
		
			ArrayList<String> listasSystemKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
			for(String sSystemKey : listasSystemKey) {								
				if(hmDebug!=null) hmDebug.put("Im SystemKey: " + sSystemKey, sProgram);					
				objReturn.setSection(sSystemKey);
				
				//Zuerst den im SystemKey definierten Programnamen finden.
				IKernelConfigSectionEntryZZZ objEntrySectionProgram = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIniConfig, sSystemKey, sProgram);
				
				//20240509: Rette die Suchschritte
				HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionProgram.getSectionsSearchedHashMap();
				hmSearched.add(hmSearchedTemp);
				
				if(objEntrySectionProgram.hasAnyValue()) {
					String sProgramAlias = objEntrySectionProgram.getValue();
					System.out.println("ProgramName: '"+ sProgramAlias + "' gefunden im Systemkey '" + sSystemKey + "'");
					
					objReturn = objEntrySectionProgram;
					
					//20240509: Bewahre den Suchpfad
					objReturn.setSectionsSearchedHashMap(hmSearched);					
					break main;
					
					//Prüfe nun, ob die Section auch existiert....	
					//Merke: Für die reine Ermittlung des Programaliasses ist die Überprüfung der Section so nicht ausreichend.
					//      Es muessen alle "Varianten" der Sections ueberprüft werden, also mit Modulkey, etc.
					//      DAS IST HIER NOCH NICHT MOEGLICH
//					ArrayList<String>listasProgramSection = KernelKernelZZZ.computeSystemSectionNames(sProgramAlias, sSystemNumber);
//					for(String sProgramSection : listasProgramSection) {
//						boolean bExists = objFileIniConfig.proofSectionExists(sProgramSection);
//						
//						if(bExists) {
//							System.out.println("ProgramSection '" + sProgramAlias + "' exisiert im SystemKey '" + sSystemKey + "'.");
//							objReturn = objEntrySectionProgram;
//							break main;					
//						}else {
//							System.out.println("ProgramSection '" + sProgramAlias + "' exisiert nicht im SystemKey '" + sSystemKey + "'.");
//						}
//					}//end for
					
					
				}
			}//end for		
			
			//20240509: Bewahre den Suchpfad
			objReturn.setSectionsSearchedHashMap(hmSearched);
			
		}//end main:
		return objReturn;
	}
	
	
	public static IKernelConfigSectionEntryZZZ searchProgramAliasFor(IniFile objFileIni, String sProgram, String sApplicationOrModule, String sSystemNumber, HashMapMultiIndexedZZZ hmDebug) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			check:{
				if(objFileIni==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'FileIni'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sApplicationOrModule)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'ApplicationOrModule'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sProgram)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Program'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(StringZZZ.isEmpty(sSystemNumber)) {
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'SystemNumber'",iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			}//end check:										
				
			//20240509: Versuch die bisherigen Suchschritte zu speichern und "nach oben" weiterzugeben.
	    	HashMapMultiIndexedZZZ hmSearched = new HashMapMultiIndexedZZZ();
	    
				
			//###############################################
			//0. Suche nach dem Program in den Systemkeys	
			ArrayList<String> listasSystemKey = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
			for(String sSystemKey : listasSystemKey) {								
				if(hmDebug!=null) hmDebug.put("Im SystemKey: " + sSystemKey, sProgram);					
							
				//Zuerst den im SystemKey definierten Programnamen finden.
				IKernelConfigSectionEntryZZZ objEntrySectionProgram = AbstractKernelObjectZZZ.KernelGetParameter_DirectLookup_(objFileIni, sSystemKey, sProgram);
				
				//20240509: Rette die Suchschritte
				HashMapMultiIndexedZZZ hmSearchedTemp = objEntrySectionProgram.getSectionsSearchedHashMap();
				hmSearched.add(hmSearchedTemp);
				
				if(objEntrySectionProgram.hasAnyValue()) {
					String sProgramName = objEntrySectionProgram.getValue();
					System.out.println("ProgramName: '"+ sProgramName + "' gefunden im Systemkey '" + sSystemKey + "'");										

					//Prüfe nun, ob die Section auch existiert....						
					ArrayList<String>listasProgramSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationOrModule, sSystemNumber);
					for(String sProgramSection : listasProgramSection) {
						boolean bExists = objFileIni.hasSection(sProgramSection);
						
						if(bExists) {																									
							objReturn = objEntrySectionProgram;
							break main;					
						}else {
							System.out.println("Program '" + sProgramName + "' exisiert nicht im SystemKey '" + sSystemKey + "'.");
						}
					}//end for
				}
			}//end for	
			
			//20240509: Bewahre den Suchpfad
			objReturn.setSectionsSearchedHashMap(hmSearched);						
		}//end main:
		return objReturn;
	}
	
	
	/**
	 * @param objFileIniConfig
	 * @param sMainSection
	 * @param sProgramOrAlias
	 * @param sSystemNumber
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public ArrayListZZZ<String> getProgramAliasUsed_DirectLookup(FileIniZZZ objFileIniConfig, String sMainSection, String sProgramOrAlias, String sSystemNumber) throws ExceptionZZZ{
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();
		HashMapMultiIndexedZZZ hmDebug = new HashMapMultiIndexedZZZ(); //Für die durchgeführten Suchen. Statt so vieler system.out Anweisungen.
		//CounterHandlerSingleton_AlphanumericSignificantZZZ objHandler = CounterHandlerSingleton_AlphanumericSignificantZZZ.getInstance();
		//CounterHandlerSingleton_AlphabetZZZ objHandler = CounterHandlerSingleton_AlphabetZZZ.getInstance();
		CounterHandlerSingleton_AlphabetSignificantZZZ objHandler = CounterHandlerSingleton_AlphabetSignificantZZZ.getInstance();
		ICounterAlphabetSignificantZZZ objCounter = objHandler.getCounterFor();
		String sSearchCounter = objCounter.getStringNext(); // objCounter.current();
		main:{
			String sProgramNameUsed;
			if(StringZZZ.isEmpty(sMainSection)){
				ExceptionZZZ ez = new ExceptionZZZ("Module/Section",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sProgramOrAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Program",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				sProgramNameUsed = sProgramOrAlias;
			}
				
			String sProgramAliasUsed=null; boolean bSectionExists= false;
			String sSectionUsed=null; String sPropertyUsed=null;String sDebugKey = null;
								
			//############################
			//### GRUNDSATZ
			//### ENDLOSSCHLEIFE, WEIL DARIN WIEDER KERNEL.getPropertyValue() Aufgerufen wird.
			//### Es muss eine objFileIni.getValue(sSectionSearch, sProperty); aufgerufen werden also auf niedrigster Ebene !!!
			//############################
			
			//++++++++++++
			//Suche nach einer überschreibenden Version mit Systemkey				
			sSectionUsed = sMainSection+FileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER+sSystemNumber;
			sPropertyUsed = sProgramNameUsed;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				
			bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
			if(bSectionExists==true){
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Section als Grundlage gefunden '" + sSection + "'.");
				//Nun in der Section nach dem Wert für das Programm suchen.
				String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
				if(!StringZZZ.isEmpty(sRaw)) {
					sProgramAliasUsed = sRaw;
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
					listasReturn.add(sSectionUsed);					
					listasReturn.add(sProgramAliasUsed);
					sDebugKey = "(a.a)";
					hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' KEINEN Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
				}
			}else{
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Keine Section als Grundlage gefunden '" + sSection + "'.");
			}
			
			//++++++++++
			//Suche nach der Version ohne SectionKey
			sSectionUsed = sMainSection;
		    //System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");			  
		    bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
			if(bSectionExists==true){
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Section als Grundlage gefunden '" + sSection + "'.");

				//Nun in der Section nach dem Wert für das Programm suchen.
				String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
				if(!StringZZZ.isEmpty(sRaw)) {
					sProgramAliasUsed = sRaw;					
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
					listasReturn.add(sSectionUsed);					
					listasReturn.add(sProgramAliasUsed);
					sDebugKey = "(b.b)";
					 hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
				}
			}else{
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (b.b) - Keine Section als Grundlage gefunden '" + sSection + "'.");
			}																	
					
				
			//####################################################################################################
			//TODOGOON; //20220806: Programmaliasermittlung, die Variante "ApplicationKey ! SystemNr _ sSectionUsed
			//Suche nach einer überschreibenden Version mit Systemkey
			String sApplicationKey = this.getApplicationKey();
			
			sSectionUsed = sApplicationKey + FileIniZZZ.sINI_SUBJECT_SEPARATOR_SYSTEMNUMBER + sSystemNumber + "_"+ sMainSection;
			sPropertyUsed = sProgramNameUsed;
			//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				
			bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
			if(bSectionExists==true){
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - Section als Grundlage gefunden '" + sSection + "'.");
				//Nun in der Section nach dem Wert für das Programm suchen.
				String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
				if(!StringZZZ.isEmpty(sRaw)) {
					sProgramAliasUsed = sRaw;
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (a.a) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
					listasReturn.add(sSectionUsed);					
					listasReturn.add(sProgramAliasUsed);
					sDebugKey = "(c.c)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - in der Section als Grundlage '" + sSection + "' KEINEN Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
					
				//++++++++++
				//Suche nach einer globalen Version ohne SystemNumber
				sSectionUsed = sApplicationKey + "_"+ sMainSection;
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");			  
				bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Section als Grundlage gefunden '" + sSection + "'.");

					//Nun in der Section nach dem Wert für das Programm suchen.
					String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
					if(!StringZZZ.isEmpty(sRaw)) {
						sProgramAliasUsed = sRaw;					
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - in der Section als Grundlage '" + sSection + "' Wert für '" + sProgramNameUsed + "' gefunden = '" + sProgramAliasUsed +"'.");
						listasReturn.add(sSectionUsed);					
						listasReturn.add(sProgramAliasUsed);
						sDebugKey = "(b.b)";
						 hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}																	
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
				
				//####################################################################################################
				//Programmaliasermittlung, verwende als Section den Systemkey (ApplicationKey + "!" + Systemnumber), also quasi die oberste Ebene					
				sSectionUsed = this.getSystemKey();
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");				 				
				bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Section als Grundlage gefunden '" + sSection + "'.");

					//Nun in der Section nach dem Wert für das Programm suchen.
					String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
					if(!StringZZZ.isEmpty(sRaw)) {
						sProgramAliasUsed = sRaw;
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Value gefunden für Property '" + sProgramNameUsed + "'='" + sProgramAliasUsed + "'");
						
						//Den gefundenen Programalias zuerst um die SystemNumber erweitern.
						listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);
						listasReturn.add(sProgramAliasUsed);	
						sDebugKey = "(c.c)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (c.c) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}
					
				
				//###################################################################################################
				//Programaliasermittlung, verwende als Section den ApplicationKey selbst								
				sSectionUsed = this.getApplicationKey();
				//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Verwende als sSection '"+ sSection + "' für die Suche nach dem Programm als der Property '" + sProgramNameUsed + "'");					
				bSectionExists = objFileIniConfig.proofSectionExistsDirectLookup(sSectionUsed);
				if(bSectionExists==true){
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Section als Grundlage gefunden '" + sSection + "'.");
				
					//Nun in der Section nach dem Wert für das Programm suchen.
					String sRaw = objFileIniConfig.getFileIniObject().getValue(sSectionUsed, sPropertyUsed); 
					if(!StringZZZ.isEmpty(sRaw)) {
						sProgramAliasUsed = sRaw;
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Value gefunden für Property '" + sProgramNameUsed + "'='" + sProgramAliasUsed + "'");
						
						//Den gefundenen Programalias zuerst um die SystemNumber erweitern.
						listasReturn.add(sProgramAliasUsed+"!"+sSystemNumber);					
						listasReturn.add(sProgramAliasUsed);
						sDebugKey = "(d.d)";
						hmDebug.put(sDebugKey + "(" + sSearchCounter + ")." + sSectionUsed, sPropertyUsed);
					}else{
						//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - in der Section als Grundlage '" + sSection + "' keinen Wert für '" + sProgramNameUsed + "' gefunden, der Alias sein könnte.");
					}
				}else{
					//System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Programaliasermittlung (d.d) - Keine Section als Grundlage gefunden '" + sSection + "'.");
				}					
		}//end main:
		
		
		if(listasReturn.size()>=1){
			String sDebug = hmDebug.computeDebugString(" | ",":");
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": ERFOLGREICH PROGRAMALIASSE GEFUNDEN +++ Suchreihenfolge (Section:Property): " + sDebug);
		}else{
			System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0) + ": KEINE PROGRAMALIASSE GEFUNDEN");
		}
		return listasReturn;
	}
	
	/** String; Ausgehend vom Dateinamen werden die "überfl�ssigen" Namensteile abgeschnitten und der Modulalias bleibt bestehen 
	* Lindhauer; 12.05.2006 09:33:01
	 * @param sIn
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String computeModuleAliasBy(File objFile) throws ExceptionZZZ{
		String sReturn =new String("");
		main:{
			 String sIn;
			check:{
				if(objFile==null) break main;
			   if(objFile.isDirectory())break main;
			}
			sIn = objFile.getName();
			sReturn = computeModuleAliasByFilename(sIn);	
		}		
		return sReturn;
	}
	
	/** String, computes the module alias from a filename, provided as as string
	* Lindhauer; 15.05.2006 08:35:30
	 * @param sIn
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String computeModuleAliasByFilename(String sIn) throws ExceptionZZZ{
		String sReturn =new String("");
		main:{
			check:{
				if(StringZZZ.isEmpty(sIn)) break main;
				if(!sIn.toLowerCase().startsWith("zkernelconfig")) break main;	
			}
	
		//in der lowercase-variante die "Ausschneid-Indices ermitteln
		int iLen = new String("zkernelconfig").length();
		String stemp = sIn.substring(iLen);
			
		String[] sa2Find = {"_", "."};
		int iEnd = StringZZZ.indexOfFirst(stemp,sa2Find);
		if(iEnd<=-1) break main;
		
		sReturn = stemp.substring(0, iEnd);
		}
		return sReturn;
	}
	
	/**Bereche den ModulAlias, ausgehend von einem Subject (d.h. normalerweise einer Property der Konfigurationsdatei in der der Pfad zur Moduldatei hinterlegt ist.)
	 * z.B. der String KernelConfigFiletest.zKernelUI.KernelUIZZZTest  ==> ergibt als ModuleAlias: test.zKernelUI.KernelUIZZZTest 
	* @param sSubjectIn
	* @return
	* 
	* lindhaueradmin; 04.03.2007 10:15:54
	 */
	public static String computeModuleAliasBySubject(String sSubjectIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(StringZZZ.isEmpty(sSubjectIn)){
				ExceptionZZZ ez = new ExceptionZZZ("SubjectIn", iERROR_PARAMETER_MISSING, AbstractKernelObjectZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//a) kernelconfigfile
			String sSubject = sSubjectIn.toLowerCase();
			//String stemp = StringZZZ.left(sSubject, "kernelconfigfile");								
			String stemp = StringZZZ.left(sSubject, KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX,false);
						
			//Falls es links daneben etwas gibt: Kein Fehler, aber nix zurueckgeben.
			if(!StringZZZ.isEmpty(stemp)) break main;
			

			//sReturn = StringZZZ.right(sSubject, "kernelconfigfile");
			sReturn = StringZZZ.right(sSubject, KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX,false);
			if(!StringZZZ.isEmpty(sReturn)){
				//Da der Returnwert nun allerdings nur kleingeschrieben waere vom Original ausgehen
				sReturn = sSubjectIn.substring(KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX.length());  //16 ist die Laenge von 'kernelConfigfile'/ KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX
				break main;
			}
			
			
			//b) kernelconfigpath
			//stemp = StringZZZ.left(sSubject, "kernelconfigpath");			
			stemp = StringZZZ.left(sSubject, KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX, false);
			
			//Falls es links daneben etwas gibt: Kein Fehler, aber nix zurueckgeben.
			if(!StringZZZ.isEmpty(stemp)) break main;
			
			//sReturn = StringZZZ.right(sSubject, "kernelconfigpath");
			sReturn = StringZZZ.right(sSubject, KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX, false);
			if(!StringZZZ.isEmpty(sReturn)) {
//				Da der Returnwert nun allerdings nur kleingeschrieben waere vom Original ausgehen
				sReturn = sSubjectIn.substring(KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX.length());  //16 ist die Laenge von 'kernelConfigpath'
				break main;
			}
		}
		return sReturn;
	}
	
	/** String, computes the KernelConfigFile or KernelConfigPath - Property for a module in the ini-File 
	* Lindhauer; 15.05.2006 08:51:41
	 * @param sModuleAlias
	 * @param sPropertyIn
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String computePropertyForModuleAlias(String sPropertyIn, String sModuleAlias) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			String sProperty=null;
			check:{
				if(sModuleAlias==null)break main;
				if(sModuleAlias.equals("")) break main;
				if(sPropertyIn==null){
					sProperty = new String("File");
				}else if(sPropertyIn.equals("")){
					sProperty = new String("File");
				}else if(!(sPropertyIn.toLowerCase().equals("file") || sPropertyIn.toLowerCase().equals("path"))){
					ExceptionZZZ ez = new ExceptionZZZ("Wrong parameter for sProperty='" + sPropertyIn + "', but expected 'File' or 'Path'.'", iERROR_PROPERTY_VALUE, AbstractKernelObjectZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					throw ez;
				}else {
					sProperty=sPropertyIn;
				}
			}
		sReturn= new String(IKernelConfigZZZ.sMODULE_PREFIX+sProperty+sModuleAlias);
		}
		return sReturn;
	}
	
			/** ArrayList
			 * -- true, true => Von den konfigurierten Modulen nur diejenige, die auch existieren.
			 * -- false, true => Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
			 * -- true, false => Von allen Moduldateien ausgehend diejenigen, deren Konfiguration fehlt (!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
			* Lindhauer; 30.04.2006 09:41:15
			 * @param bOnlyConfigured
			 * @param bOnlyExisting
			 * @return
			 * @throws ExceptionZZZ 
			 */
	@Override
	public ArrayList getFileConfigModuleAliasAll(boolean bOnlyConfigured, boolean bOnlyExisting) throws ExceptionZZZ{
				ArrayList listaReturn = new ArrayList();
				main:{
					int iCase=0;
					check:{
						if(bOnlyConfigured==true && bOnlyExisting==true){
							iCase = 1;
						}else if(bOnlyConfigured == true && bOnlyExisting == false){
							iCase = 2;							
						}else if(bOnlyConfigured == false && bOnlyExisting == true){
							iCase = 3;
						}else if(bOnlyConfigured == false && bOnlyExisting == false){
							iCase = 4;
						}
					}//END Check:
					 
					
					//+++ Die KernelKonfigurations-Datei
				File objFile = this.getFileConfigKernel();
				
				HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
				hmFlag.put(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(), true);
				
				FileIniZZZ objFileIni = new FileIniZZZ( this, objFile, hmFlag);
					
				String[] saProperty = null; String sSearch = null; int iIndex; String sSystemKey = null;
				String[] saPropertyByApplication = null;String sApplicationKey = null;
				File objFileTemp=null; File[] objaFileTemp=null; String sDir=null; ArrayList listaConfigured=null; ArrayList listaExisting = null; ArrayList listaFile = null;  
					switch(iCase){
					case 1:
						//+++ Von den konfigurierten Modulen nur diejenige, die auch existieren.
						//Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						sSystemKey = this.getSystemKey();
						saProperty = objFileIni.getPropertyAll(sSystemKey);//... intern werden so auch die Werte des ApplicationKeys geholt.
						
						//...anreichern um die Werte aus dem ApplicationKey
						sApplicationKey = this.getApplicationKey();
						saPropertyByApplication = objFileIni.getPropertyAll(sApplicationKey);
						
						//sSearch = new String("kernelconfigfile");
						sSearch = new String(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..." anfangen. Der Modulname steht unmittelbar dahinter.
						listaConfigured = new ArrayList();
						if(saProperty!=null) {
							for(int icount=0; icount < saProperty.length; icount++){
								String stemp = saProperty[icount].toLowerCase();									
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									String sValueTemp = saProperty[icount].substring(iIndex);
									listaConfigured.add(sValueTemp);
								}
							}
						}
						
						//... anreichern um die Werte aus dem ApplicationKey, sofern nicht schon in der Liste vorhanden.
						if(saPropertyByApplication!=null) {
							for(int icount=0; icount < saPropertyByApplication.length; icount++){
								String stemp = saPropertyByApplication[icount].toLowerCase();
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									String sValueTemp = saPropertyByApplication[icount].substring(iIndex);
									if(!listaConfigured.contains(sValueTemp)){
										listaConfigured.add(sValueTemp);
									}								
								}
							}
						}
						
						
						//Aus der liste nun diejenigen wieder entfernen, die nicht existieren
						//!!! Von hinten nach vorne die liste durchsehen, ansonsten werden die Indizes durcheinandergeworfen: for(int icount=0; icount < listaConfigured.size(); icount++){
						for(int icount=listaConfigured.size()-1; icount >= 0 ; icount--){		
							boolean btemp = this.proofFileConfigModuleExists((String) listaConfigured.get(icount));
							if(btemp == false){
								listaConfigured.remove(icount);
							}
						}
						
						//Alle ggf. doppelt vorkommenden Eintraege entfernen
						listaReturn = ArrayListUtilZZZ.unique(listaConfigured);
						break;
					case 2:
						//######## Von allen gefundenen Moduldateien ausgehend diejenigen, deren Konfiguration fehlt
						//(!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
																	
						//+++ 1. Die konfigurierten Module holen
						//Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. 
						sSystemKey = this.getSystemKey();
						saProperty = objFileIni.getPropertyAll(sSystemKey);
						
						//...anreichern um die Werte aus dem ApplicationKey
						sApplicationKey = this.getApplicationKey();
						saPropertyByApplication = objFileIni.getPropertyAll(sApplicationKey);
						
						
						//sSearch = new String("kernelconfigfile");
						sSearch = new String(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..."/IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX anfangen. Der Modulname steht unmittelbar dahinter.
						listaConfigured = new ArrayList();
						if(saProperty!=null) {
							for(int icount=0; icount < saProperty.length; icount++){
								String stemp = saProperty[icount].toLowerCase();
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									listaConfigured.add(saProperty[icount].substring(iIndex));
								}
							}
						}
						
						//... anreichern um die Werte aus dem ApplicationKey, sofern nicht schon in der Liste vorhanden.
						if(saPropertyByApplication!=null) {
							for(int icount=0; icount < saPropertyByApplication.length; icount++){
								String stemp = saPropertyByApplication[icount].toLowerCase();
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									String sValueTemp = saPropertyByApplication[icount].substring(iIndex);
									if(!listaConfigured.contains(sValueTemp)){
										listaConfigured.add(sValueTemp);
									}								
								}
							}
						}
												
						//+++2. Hole alle Konfigurationsdateien im aktuellen Verzeichnis
						objFileTemp = this.getFileConfigKernel();
						sDir = objFileTemp.getParent();
						if(sDir==null) sDir = "";     //Das ist nun m�glich     if(sDir.equals("")) break main;
						
						//Alle Moduldateien dieses Verzeichnisses holen
						objaFileTemp = this.getFileConfigModuleAllByDir(sDir);
						if(objaFileTemp==null) break main;
						if(objaFileTemp.length==0)break main;
						
						
						
						//+++3. Berechne aus den vorhandenen Konfigurationsdateien wie der Modulname sein sollte
						listaExisting = new ArrayList();
						if(saProperty!=null){
							//Von den vorhandenen Dateien das Modul ermitteln
							for(int icount=0;icount < objaFileTemp.length; icount++){
								String sFilename = FileEasyZZZ.getNameOnly(objaFileTemp[icount].getAbsolutePath());
								String stemp = computeModuleAliasByFilename(sFilename);
								if(!stemp.equals("")) listaExisting.add(stemp);	
							}							
						}
						
						//+++ 4. Die Module aus allen Dateinamen herausfiltern. D.h. ggf. gibt es ja mehrere Dateien zu einem Modul.
						listaFile = new ArrayList();
												
						Iterator it = listaExisting.iterator();
						while(it.hasNext()){
							String sExisting = (String)it.next();
							if(!listaConfigured.contains(sExisting)){
								listaFile.add(sExisting);
							}
						}
					
						//Nun doppelte Modulnamen entfernen
						listaReturn = ArrayListUtilZZZ.unique(listaFile);
						break;
					case 3:
						//+++ Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
//						Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen.
						sSystemKey = this.getSystemKey();
						saProperty = objFileIni.getPropertyAll(sSystemKey);
						
						//...anreichern um die Werte aus dem ApplicationKey
						sApplicationKey = this.getApplicationKey();
						saPropertyByApplication = objFileIni.getPropertyAll(sApplicationKey);
						
						
						
						sSearch = new String(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..."/IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX anfangen. Der Modulname steht unmittelbar dahinter.
						 listaConfigured = new ArrayList();
						 if(saProperty!=null) {
							for(int icount=0; icount < saProperty.length; icount++){
								String stemp = saProperty[icount].toLowerCase();
								
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									listaConfigured.add(saProperty[icount].substring(iIndex));
								}
							}
						 }
						
						//... anreichern um die Werte aus dem ApplicationKey, sofern nicht schon in der Liste vorhanden.
						 if(saPropertyByApplication!=null) {
							for(int icount=0; icount < saPropertyByApplication.length; icount++){
								String stemp = saPropertyByApplication[icount].toLowerCase();
								
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									String sValueTemp = saPropertyByApplication[icount].substring(iIndex);
									if(!listaConfigured.contains(sValueTemp)){
										listaConfigured.add(sValueTemp);
									}								
								}
							}
						 }
						
						//Nun die Module trimmen
						listaFile = ArrayListUtilZZZ.unique(listaConfigured);
						
						//Aus der liste nun diejenigen wieder entfernen, die  existieren, bzw: Diejenigen, die FEHLEN HINZUF�GEN
						listaReturn = new ArrayList();
						for(int icount=0; icount < listaFile.size(); icount++){
							String stemp = (String) listaFile.get(icount);
							
							boolean btemp = this.proofFileConfigModuleExists(stemp);//false=schliesst die Suche über den SystemKey aus. sonst wird nämlich ggfs. die Systemkey-Konfigurationsdatei zurueckgegeben.
							if(btemp == false){
								listaReturn.add(listaFile.get(icount));  //Ein einfaches Entfernen an dieser Stelle ändert die Size der Liste und die Indexpositionen
							}														
						}
												
						break;						
					case 4:
						//+++ Fall: Alle Module, sowohl die Konfigurierten als auch die als Datei vorhandenen
//						(!!! hierbei wird das Kernel-Konfigurations-Verzeichnis nach Moduldateien durchsucht !!!)
						objFileTemp = this.getFileConfigKernel();
						sDir = objFileTemp.getParent();
						if(sDir==null) sDir = "";     //Das ist nun m�glich     if(sDir.equals("")) break main;
						
						
						//Alle Moduldateien dieses Verzeichnisses holen
						objaFileTemp = this.getFileConfigModuleAllByDir(sDir);
						if(objaFileTemp==null) break main;
						if(objaFileTemp.length==0)break main;
												
						// Aus der KernelKonfigurationsdatei alle Werte des aktuellen Systems holen. ! DIE PROPERTIES, nicht die Values !!!
						sSystemKey = this.getSystemKey();
						saProperty = objFileIni.getPropertyAll(sSystemKey);						
							
						//...anreichern um die Werte aus dem ApplicationKey
						sApplicationKey = this.getApplicationKey();
						saPropertyByApplication = objFileIni.getPropertyAll(sApplicationKey);
						
						
						/* falsch, das funktioniert nur mit den Values
						listaConfigured = new ArrayList();
						if(saProperty!=null){
							//Von den Konfigurierten Dateien das Modul ermitteln
							for(int icount = 0; icount < saProperty.length; icount ++){
								
								//Nun den Aliasnamen aus der Property auslesen
								String stemp = getModuleAliasFrom(saProperty[icount]);
								if(!stemp.equals("")) listaConfigured.add(stemp);	
							}							
						}
						*/
																	
						sSearch = new String(IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX);
						iIndex = sSearch.length();	
						
						//Aus dem Array alle ausfiltern, die mit "KernelConfigFile..."/IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX anfangen. Der Modulname steht unmittelbar dahinter.
						listaConfigured = new ArrayList();
						if(saProperty!=null) {
							for(int icount=0; icount < saProperty.length; icount++){
								String stemp = saProperty[icount].toLowerCase();
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									listaConfigured.add(saProperty[icount].substring(iIndex));
								}
							}
						}
						
						//... anreichern um die Werte aus dem ApplicationKey, sofern nicht schon in der Liste vorhanden.
						if(saPropertyByApplication!=null) {
							for(int icount=0; icount < saPropertyByApplication.length; icount++){
								String stemp = saPropertyByApplication[icount].toLowerCase();
								if(StringZZZ.startsWithIgnoreCase(stemp,sSearch)){
									String sValueTemp = saPropertyByApplication[icount].substring(iIndex);
									if(!listaConfigured.contains(sValueTemp)){
										listaConfigured.add(sValueTemp);
									}								
								}
							}
						}
						
						
						//###########
						//Die Module aus allen Dateinamen herausfiltern. D.h. ggf. gibt es ja mehrere Dateien zu einem Modul.
						listaFile = new ArrayList();
						for(int icount=0; icount < objaFileTemp.length; icount ++){
							String stemp = computeModuleAliasBy(objaFileTemp[icount]);
							if(!stemp.equals("")) listaFile.add(stemp);	
						}
						
						listaReturn = ArrayListUtilZZZ.join(listaConfigured, listaFile, true);
															
						break;						
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Unexpected case'",iERROR_RUNTIME, this,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}
										
				}//END main:
				return listaReturn;
			}
	
	public static String getModuleDirectoryPrefix() {
		String stemp = IKernelConfigConstantZZZ.MODULEPROPERTY.Path.name();
		return KernelConfigDefaultEntryZZZ.sMODULE_PREFIX+stemp;		
	}
	public static String getModuleFilenamePrefix() {
		String stemp = IKernelConfigConstantZZZ.MODULEPROPERTY.File.name();
		return KernelConfigDefaultEntryZZZ.sMODULE_PREFIX+stemp;	
	}
	
	/**List aus der KernelKonfiguration alle Konfigurierten Modulenamen aus. Irgenwelche File - Eigenschaften (z.B. die Existenz) werden hier nicht berücksichtig
	* @return
	* 
	* lindhaueradmin; 04.03.2007 10:00:37
	 * @throws ExceptionZZZ 
	 */
	@Override
	public ArrayList getModuleAll() throws ExceptionZZZ{
		ArrayList listaModuleString = new ArrayList();
		main:{
			IniFile objFileIni = this.getFileConfigKernelAsIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelConfigurationIni-File", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			//########################################
			//### Vereinfachung mit ArrayList
			//########################################
			String sApplicationKey = this.getApplicationKey();
			String sSystemNumber = this.getSystemNumber();
			ArrayList<String> listasSystemSection = AbstractKernelObjectZZZ.computeSystemSectionNames(sApplicationKey, sSystemNumber);			
			for(String sSystemSection : listasSystemSection) {
			
				String[] saVar = objFileIni.getVariables(sSystemSection);
				for(int icount = 0; icount <= saVar.length-1; icount ++){
					String sModule = AbstractKernelObjectZZZ.computeModuleAliasBySubject(saVar[icount]);
					if(!StringZZZ.isEmpty(sModule)){
						if(!listaModuleString.contains(sModule)){
							listaModuleString.add(sModule);
						}
					}
				}
			}			
			//########################################
		}
		return listaModuleString;
	}
	
	
	
	/** boolean, proofs the existance of the module configuration lines in the kernel configuration .ini-file.
	 *   A  line has to look like: 	KernelConfigPath + sAlias = c:\.......
	 *   Another line:                 KernelConfigFile + sAlias = .... .ini
	 *   
	 *   This method does not check if a file really exists there.
	 *   
	 * @param sModule
	 * @return 
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean proofFileConfigModuleIsConfigured(String sModule) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "Missing parameter: 'Alias'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING,  this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}							
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			IniFile objIni = this.getFileConfigKernelAsIni();
			String sLog = "XXXTEST Verwende als ini-Datei für die Prüfung '"+ objIni.getFileName() + "'.";
			sLog = ReflectCodeZZZ.getMethodCurrentNameLined(0) + sLog;
			//System.out.println(sLog);
			this.logLineDate(sLog);
			
			//PRÜFUNG: LIES EINEN .ini - DATEINAMEN AUS.
			//Merke: Der Pfad darf leer sein. Dann wird "." als aktuelles Verzeichnis angenommen    String sFilePath = objIni.getValue(stemp,"KernelConfigPath" +sAlias );
			
			//1. Versuch: Systemebene
			String sKeyUsed = this.getSystemKey();			
			String sFileName =objIni.getValue(sKeyUsed, IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX +sModule );
			String sFileNameUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFileName);
			if(!StringZZZ.equals(sFileName,sFileNameUsed)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
				//this.setValueRaw(sFileName);
			}else{
				//this.setValueRaw(null);
			}
			if(!StringZZZ.isEmpty(sFileNameUsed)) {
				bReturn = true;
				break main;
			}
			
			//2. Versuch: Applikationsebene
			if(StringZZZ.isEmptyNull(sFileNameUsed)){
				sKeyUsed = this.getApplicationKey();
				sFileName =objIni.getValue(sKeyUsed, IKernelConfigConstantZZZ.sMODULE_FILENAME_PREFIX+sModule );
				sFileNameUsed = KernelZFormulaIniConverterZZZ.getAsStringStatic(sFileName);
				if(!StringZZZ.equals(sFileName,sFileNameUsed)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sFileName + "' nach '" + sFileNameUsed +"'");
					//this.setValueRaw(sFileName);
				}else{
					//this.setValueRaw(null);
				}
			}
			if(!StringZZZ.isEmpty(sFileNameUsed)) {
				bReturn = true;	
				break main;
			}						
		}//end main:
		return bReturn;
	}
		
	/** boolean, proofs the existance of the module configuration lines in the Kernel configuration .ini-file
	 *  AND checks if the configured file really exists.
	 * 
	* Lindhauer; 21.04.2006 09:39:09
	 * @param sModule
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public boolean proofFileConfigModuleExists(String sModule) throws ExceptionZZZ{
		return this.proofFileConfigModuleExists_(sModule, true); 
	}
	
	@Override
	public boolean proofFileConfigModuleExists(String sModule, boolean bIncludeSystemSectionInSearch) throws ExceptionZZZ{
		return this.proofFileConfigModuleExists_(sModule, bIncludeSystemSectionInSearch);
	}
	
	
	private boolean proofFileConfigModuleExists_(String sModule, boolean bIncludeSystemSectionInSearch) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}								
			}//end check:
			
			//first, get the Kernel-Configuration-INI-File
			IniFile objIni = this.getFileConfigKernelAsIni();			
			String sLog = "XXXXTest Verwende als ini-Datei für die Prüfung '"+ objIni.getFileName() + "'.";
			sLog = ReflectCodeZZZ.getMethodCurrentNameLined(0) + sLog;
			//System.out.println(sLog);
			this.logLineDate(sLog);
			
			IniFile objFile = this.getFileConfigModuleAsIni(sModule, bIncludeSystemSectionInSearch); //false Schliesst die Suche über den SystemKey aus.
			if(objFile!=null) {
				bReturn = FileEasyZZZ.exists(objFile.getFileName());
				if(this.getLogObject()!=null) this.getLogObject().writeLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#FileExists = " + bReturn);
			}else {
				if(this.getLogObject()!=null) this.getLogObject().writeLineDate(ReflectCodeZZZ.getMethodCurrentName() + "#FileExists (null case) = " + bReturn);
			}
		}//end main:
		return bReturn;
	}
	
	/** boolean, proofs the existance of the module configuration lines in the Kernel configuration .ini-file
	 *  AND checks if the configured file really exists.
	 * 
	* Lindhauer; 21.04.2006 09:39:09
	 * @param sAlias
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public boolean proofSectionIsConfigured(String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'Alias'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}								
					
			//first, get the Kernel-Configuration-INI-File
			FileIniZZZ objIni = this.getFileConfigKernelIni();
			if(objIni==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Not configured KernelIniFile'",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = objIni.getFileObject();
			if(objFile==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Configured KernelIniFile-Object has not internal File used.'",iERROR_PROPERTY_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sLog = "xxxxTEST Verwende als ini-Datei für die Prüfung '"+ objFile.getAbsolutePath() + "'.";
			sLog = ReflectCodeZZZ.getMethodCurrentNameLined(0) + sLog;
			//System.out.println(sLog);
			this.logLineDate(sLog);
		
			bReturn = objIni.proofSectionExistsSearched(sAlias);						
		}//end main:
		return bReturn;
	}
	
	@Override
	public void setLogObject(LogZZZ objLog){
		this.objLog = objLog;
	}
	
	private boolean KernelNew_(IKernelConfigZZZ objConfig, IKernelContextZZZ objContext, String sApplicationKeyIn,String sSystemNumberIn, String sDirectoryConfigIn, String sFileConfigIn, LogZZZ objLogIn, String[]saArg) throws ExceptionZZZ{
		boolean bReturn = false;		
		main:{			
				String stemp=null; boolean btemp=false; String sLog = null;
				//DAS PROBLEM IST, DASS DAS KONFIGURATIONSOBJEKT IM GRUNDE IMMER EHER DA SEIN MUSS ALS DER KERNEL, AUCH BEIM PROTOKOLLIEREN
				sLog = "Initializing KernelObject";				
				//this.logProtocolWithPosition(sLog); //20260213 WAR ZWAR ORIGINAL DIE CODE-ANWEISUNG, nun wird ein CONFIG OBJEKT ERSTELLT, etc. !!! MIT DEFAULT WERTEN !!!
				this.logLineDateWithPosition(sLog);   //Nahezu identische LOG Ausgabe, ober ohne CONFIG OBJEKT ERSTELLUNG. Also ohne DEFAULT WERTE.
				
				//20191204: Umstrukturierung:
				//Zusätzlich zu übergebenen Flags müssen auch die Flags vom Config-Objekt übernommen werden, wenn sie vorhanden sind als Flags im KernelObjekt.
				//siehe als schon realisiertes Beispiel: Die Erstellung eines FileIniZZZ Objekts in: KernelKernelZZZ.getFileConfigIniByAlias(String sAlias) throws ExceptionZZZ{
							
				//Es geht dabei darum die direkt gesetzten Flags aus dem Configuration-Objekt zu übernehmen, also z.B. "useFormula" zu übernehmen.												
				String[] saFlagUsed = null;	
				
				
				//########################################################
				//###  Config Objekt erstellen
				//########################################################
				
				//Nun geht es darum ggfs. die Flags zu übernehmen, die in irgendeiner Klasse gesetzt werden sollen.
				//D.h. ggfs. stehen sie in dieser Klasse gar nicht zur Verfügung
				//Falls sie aber übergeben wurden, dann als Kommandozeilen-Argument. D.h. diese Flag - Angaben sollen alles übersteuern. Darum auch nach den "normalen" Flags verarbeiten.
				//Merke: Auch wenn nichts übergeben wurde, ist das Kommandozeilen-Argument ein Array mit 1 Leerwert zu sein.
				if(objConfig==null && !StringArrayZZZ.isEmptyTrimmed(saArg)) {
					//20211024 Wenn kein Config-Objekt übergeben wurde, dann erzeuge eines neu, mit ggfs. übergebenen Argumenten der Commando-Startzeile
					ConfigZZZ objConfigNew = new ConfigZZZ(saArg);					
					objConfig=objConfigNew;
				}	
				
				
				if(objConfig!=null) {
					//Übernimm die direkt gesetzten FlagZ...
					Map<String,Boolean>hmFlagZ = objConfig.getHashMapFlag();
					saFlagUsed = (String[]) HashMapZZZ.getKeysAsStringFromValue(hmFlagZ, Boolean.TRUE);
				}			
				

				if(saFlagUsed!=null){
					if(StringArrayZZZ.containsIgnoreCase(saFlagUsed, "INIT")) {
						this.setFlag("INIT", true);					 
					} 
				}
				bReturn = this.getFlag("INIT");
		 		if(bReturn) break main;
											
		 		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		 		//
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				if(objConfig!=null) {
					this.setConfigObject(objConfig);
						
					//Übernimm die direkt als Kommandozeilenargument gesetzten FlagZ... die können auch "false" sein.
					Map<String,Boolean>hmFlagZpassed = objConfig.getHashMapFlagPassed();	
					if(hmFlagZpassed!=null) {
						
						
					
//						Set<String> setFlag = hmFlagZpassed.keySet();
//						Iterator<String> itFlag = setFlag.iterator();
//						while(itFlag.hasNext()) {
//							String sKey = itFlag.next();
//							 if(!StringZZZ.isEmpty(sKey)){
//								 Boolean booValue = hmFlagZpassed.get(sKey);
//								 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
//								 if(btemp==false){						 
//									 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
//									 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
//	//								  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
//	//								  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
//	//								  throw ez;		 
//								  }
//							 }
//						}
						
						for(Map.Entry<String, Boolean> entry : hmFlagZpassed.entrySet()){
							String sKey = entry.getKey();
							
							if(!StringZZZ.isEmpty(sKey)){
								Boolean booValue = entry.getValue();												
								btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
								if(btemp==false){						 
									sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
									this.logLineDate(sLog);
	//								Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
	//								ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
	//								throw ez;		 
								}
							 }							
						}
						
						
					}//end if hmFlagZpassed!=null
				}//end if objConfig!=null
				
					
								
				//+++++++++++++++++++++++++++++++
				//ggfs Context Object setzen
				this.setContextUsed(objContext);
				  
				
				//++++++++++++++++++++++++++++++++
				String sDirectoryLog = null;
				String sFileLog = null;
				String sApplicationKey = null;
				String sSystemNumber = null;
				String sFileConfig = null;
				String sDirectoryConfig = null;
												
				if(objConfig!=null && StringZZZ.isEmpty(sApplicationKeyIn)){
					if(objConfig.isOptionObjectLoaded()==false){
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sLog = "Config-Object not loaded, using DEFAULTS.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						sApplicationKey = this.getConfigObject().getApplicationKeyDefault();
						if(StringZZZ.isEmpty(sApplicationKey)){
							sLog = "ApplicationKey DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}else {
						sApplicationKey = objConfig.readApplicationKey();
					}	
				}else if(objConfig!=null && !StringZZZ.isEmpty(sApplicationKeyIn)){
					String sApplicationKeyConfig = objConfig.readApplicationKey();
					if(objConfig.isApplicationKeyDefault(sApplicationKeyConfig)) {
						sApplicationKey = sApplicationKeyIn;//20211101: Das ist der Fall, wenn der Kernel für einen anderen Application-Key neu erstellt wird.
					}else {
						sApplicationKey = sApplicationKeyConfig;
					}
				}else {
					sApplicationKey = sApplicationKeyIn;
				}
				if(StringZZZ.isEmpty(sApplicationKey)){
					sLog = "ApplicationKey not passed and not receivable from Config-Object";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				this.setApplicationKey(sApplicationKey);
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++

				if(objConfig!=null && StringZZZ.isEmpty(sSystemNumberIn)){
					if(objConfig.isOptionObjectLoaded()==false){
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sLog = "Config-Object not loaded, using DEFAULTS.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						sSystemNumber = this.getConfigObject().getSystemNumberDefault();
						if(StringZZZ.isEmpty(sSystemNumber)){
							sLog = "sSystemNumber DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}else {
						sSystemNumber = objConfig.readSystemNumber();
					}					
				}else if(objConfig!=null && !StringZZZ.isEmpty(sSystemNumberIn)){
					String sSystemNumberConfig = objConfig.readSystemNumber();
					if(objConfig.isSystemNumberDefault(sSystemNumberConfig)) {
						sSystemNumber = sSystemNumberIn;//20211101: Das ist der Fall, wenn der Kernel für einen anderen Application-Key neu erstellt wird.
					}else {
						sSystemNumber = sSystemNumberConfig;
					}					
				}else {
					sSystemNumber = sSystemNumberIn;
				}
				if(StringZZZ.isEmpty(sSystemNumber)){
					sLog = "SystemNumber not passed and not receivable from Config-Object";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				this.setSystemNumber(sSystemNumber);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//get the Application-Configuration-File
				//A) Directory
				//   Hier kann auf das Config Objekt verzichtet werden. wenn nix gefunden wird, wird "." als aktuelles Verzeichnis genommen				
				if(objConfig!=null && StringZZZ.isEmpty(sDirectoryConfigIn)){
					if(objConfig.isOptionObjectLoaded()==false){
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sLog = "Config-Object not loaded, using DEFAULTS.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						sDirectoryConfig = this.getConfigObject().getConfigDirectoryNameDefault();
						if(StringZZZ.isEmpty(sDirectoryConfig)){
							sLog = "DirectoryConfig DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}else {
						sDirectoryConfig = objConfig.readConfigDirectoryName();
					}					
				}else if(objConfig!=null && !StringZZZ.isEmpty(sDirectoryConfigIn)){
					String sDirectoryConfigConfig = objConfig.readConfigDirectoryName();
					if(objConfig.isConfigDirectoryNameDefault(sDirectoryConfigConfig)) {
						sDirectoryConfig = sDirectoryConfigIn;//20211101: Das ist der Fall, wenn der Kernel für einen anderen Application-Key neu erstellt wird.
					}else {
						sDirectoryConfig = sDirectoryConfigConfig;
					}
				}else {
					sDirectoryConfig = sDirectoryConfigIn;
				}
				if(StringZZZ.isEmpty(sDirectoryConfig)){
					sLog = "Directory is empty and no Configuration-Object passed. Using ROOT - directory.";		
					this.logProtocolWithPosition(sLog);
					sDirectoryConfig = FileEasyZZZ.getFileRootPath();
				}
				
				//Prüfe nun auf Vorhandensein und korregiere ggfs. auf das aktuelle Verzeichnis
				File objDirectoryProof = FileEasyZZZ.searchDirectory(sDirectoryConfig, true);
				if(objDirectoryProof==null){					
					sLog = "Directory does not exists (='"+sDirectoryConfig+"'). Using CURRENT - directory.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					sDirectoryConfig = IFileEasyConstantsZZZ.sDIRECTORY_CURRENT;//Falls das Verzeichnis nicht existiert, verwende das aktuelle Verzeichnis.
				}else{
					sDirectoryConfig=objDirectoryProof.getAbsolutePath();						
				}
				this.setFileConfigKernelDirectory(sDirectoryConfig);
				
				//########
				//B) FileName
				if(objConfig!=null && StringZZZ.isEmpty(sFileConfigIn)){
					if(objConfig.isOptionObjectLoaded()==false){
						//Fall: Das objConfig - Objekt existiert, aber es "lebt" von den dort vorhandenenen DEFAULT-Einträgen
						//      und nicht von irgendwelchen übergebenen Startparametern, sei es per Batch Kommandozeile oder per INI-Datei.
						sLog = "Config-Object not loaded, using DEFAULTS.";
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						sFileConfig = this.getConfigObject().getConfigFileNameDefault();
						if(StringZZZ.isEmpty(sFileConfig)){
							sLog = "FileConfig DEFAULT not receivable from Config-Object, Config-Object  not loaded.";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}else {
						sFileConfig = objConfig.readConfigFileName();
					}					
				}else if(objConfig!=null && !StringZZZ.isEmpty(sFileConfigIn)){
					String sFileConfigConfig = objConfig.readConfigFileName();
					if(objConfig.isConfigFileNameDefault(sFileConfigConfig)) {
						sFileConfig = sFileConfigIn;//20211101: Das ist der Fall, wenn der Kernel für einen anderen Application-Key neu erstellt wird.
					}else {
						sFileConfig = sFileConfigConfig;
					}
				}else {
					sFileConfig = sFileConfigIn;
				}
				if(StringZZZ.isEmpty(sFileConfig)){
					sFileConfig = AbstractKernelObjectZZZ.sFILENAME_CONFIG_DEFAULT;
					sLog = "Filename for configuration is empty. Not passed and not readable from Config-Object. Using default: '" + sFileConfig + "'";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);					
				}
				this.setFileConfigKernelName(sFileConfig);
				
				//#################################################################################
		
				//Prüfe nun ob ggfs. auch das Verzeichnis default sein muss
				String sFilePath = FileEasyZZZ.joinFilePathName(sDirectoryConfig, sFileConfig);					
				boolean bExists = FileEasyZZZ.exists(sFilePath);
				if(!bExists) {
			
					//##################################################################################
					//Die Datei existiert hier nicht
					//Problem mit Modulen, die in einem anderen Projekt definiert werden.
					//Dann sind die Pfade ggfs. unter Eclipse nicht korrekt
					if(!this.isInJar() && !this.isOnServer()) {							
						if(objConfig!=null) {
							//Workaround. Man kann nun objConfig - Pfad versuchen zu nehmen							
							//Das Ziel, z.B. in einem anderen Projektordner: C:\1fgl\repo\EclipseOxygen_V01\Projekt_Kernel02_JAZLanguageMarkup\JAZLanguageMarkup\src\ZKernelConfig_HtmlTableHandler.ini
							sFilePath = ReflectWorkspaceZZZ.computeWorkspacePath(objConfig, sFileConfig);
							System.out.println("Ueberschreibe Verzeichnis, nehme Pfad in anderem Projektordnder: '" + sFilePath + "'");							
							bExists = FileEasyZZZ.exists(sFilePath);
							if(bExists) {
								String sProjectPathTotal = FileEasyZZZ.getParent(sFilePath);
								this.setFileConfigKernelDirectory(sProjectPathTotal);
							}
						}
					}
					
					
					if(!bExists) {
						//Anderes Verzeichnis- DEFAULT, mit absolutem Pfad - probieren
						String sDirectoryConfigDefault = AbstractKernelObjectZZZ.sDIRECTORY_CONFIG_DEFAULT;
						sLog = "Default Filename for configuration does not exist here: '" + sFilePath + "'. Looking in default direcotry '" + sDirectoryConfigDefault + "'" ;
						this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						
						sFilePath = FileEasyZZZ.joinFilePathName(sDirectoryConfigDefault, sFileConfig);						
						bExists = FileEasyZZZ.exists(sFilePath);
			
						if(!bExists) {
							sLog = "Default file in default directory not found: '" + sFilePath + "'";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else {
							//sLog = "Changing directory to '" + sDirectoryConfigDefault + "'";
							//this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
							//this.setFileConfigKernelDirectory(sDirectoryConfigDefault);
							sLog = "Directory for other files still used '" + sDirectoryConfig + "'";
							this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);	
						}
					}
				}
		
				if(this.getFlag("DEBUG")){
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - SystemNr: '" + sSystemNumber + "'");
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - Configurationfile: '" + sFileConfig + "'");
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + " - Configurationpath: '" + sDirectoryConfig + "'");
				}							
						
				
				
				//##################################################################################
				FileIniZZZ objFileIniZZZ = new FileIniZZZ(this);
				this.setFileConfigKernelIni(objFileIniZZZ);
		
				
				//### Arbeite mit den Einträgen in der Ini-Datei
				//Wirf eine Exception wenn weder der Application noch der SystemKey in der Konfigurationsdatei existieren
				String sKeyToProof = this.getApplicationKey();
				boolean bProofConfigMain = this.proofSectionIsConfigured(sKeyToProof);
				if(!bProofConfigMain) {
					sFilePath = FileEasyZZZ.joinFilePathName(this.getFileConfigKernelDirectory(), this.getFileConfigKernelName());
					sLog = "In the configuration file '" + sFilePath + "' does the the section for the ApplicationKey '" + this.getApplicationKey() + "' and the section for the SystemKey '" + this.getSystemKey() + "' not exist or the section is empty.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
						
				//Registriere das FileIniZZZ - Objekt fuer Aenderungen an den Kernel Flags. Z.B. wenn mal die <Z>-Formeln ausgewertet werden sollen, mal nicht.
				this.registerForFlagEvent(objFileIniZZZ);
				
				if(saFlagUsed!=null) {
					//setzen der ggfs. aus dem Config Objekt zu übernehmende, gültige Flags
					for(int iCount = 0;iCount<=saFlagUsed.length-1;iCount++){
					  stemp = saFlagUsed[iCount];
					  if(!StringZZZ.isEmpty(stemp)){
						  btemp = setFlag(stemp, true);
						  if(btemp==false){
							  sLog = "the flag '" + stemp + "' is not available.";
							  this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
							  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
							  throw ez;		 
						  }
					  }
					}
				}//end if saFlagUsed!=null
				
				//##################################################################################
				//create the log using the configured path/file
				IniFile objIni = this.getFileConfigKernelAsIni();
				
				LogZZZ objLog = null;
				if(objLogIn==null){				
					//TODOGOON202603;//Trotz aller Bemuehungen wird die # Kommentarposition nicht vor der ^ Kommentarposition einsortiert. Daher haben wir einen unverhaeltnismaessig langen String aufgrund einer riesigen Luecke, und die Spaltengrenzen werden auch nach rechts verschoben.
					sLog = "Erstelle neues Log Object";
					this.logProtocolWithPosition(sLog);
					
//					1. Versuch: über die Programm-Konfiguration. 
					//Merke: Dies geht nur, wenn ein Context-Objekt �bergeben worden ist. An dieser Stelle kommt man nicht anders an den Namen der Aufrufenden - Klasse (d.h. den Programnamen) dran.
					if(this.getContextUsed()!=null){
						
						//!!! Falls da nix konfiguriert ist, darf kein Fehler geworfen werden !!!
						try{
							sDirectoryLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogPath").getValue(); 
							sFileLog = this.getParameterByProgramAlias(this.getContextUsed().getModuleName(), this.getContextUsed().getProgramName(), "KernelLogFile").getValue(); 
						}catch (ExceptionZZZ ez){
							//nix tun, Ausgabe nur zum Test/Debug
							sLog = ez.getDetailAllLast();
							this.logProtocolWithPosition(sLog);
						}
					}	
					
					//2. Versuch: Über die Globale Konfiguration der Applikation (Systemkey)
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sDirectoryLog = objIni.getValue(this.getSystemKey(), "KernelLogPath");
						sFileLog = objIni.getValue(this.getSystemKey(), "KernelLogFile");										
					}
					
					//3. Versuch: Über die Globale Konfiguration der Applikation (ApplicationKey)
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sDirectoryLog = objIni.getValue(this.getApplicationKey(), "KernelLogPath");
						sFileLog = objIni.getValue(this.getApplicationKey(), "KernelLogFile");	
					}
					
					
					//4. Fehlermeldung, wenn kein Log definiert
					if(StringZZZ.isEmpty(sDirectoryLog) && StringZZZ.isEmpty(sFileLog)){
						sFilePath = FileEasyZZZ.joinFilePathName(this.getFileConfigKernelDirectory(), this.getFileConfigKernelName());
						sLog = "In the configuration file '" + sFilePath + "' is no log configured (Properties 'KernelLogPath', 'KernelLogFile'   ";
						System.out.println(sLog);
						ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_CONFIGURATION_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					if(this.getFlag ("DEBUG")) System.out.println("Initialisiere KernelLog mit folgendem Pfad, Dateinamen: '" + sDirectoryLog +"', '" + sFileLog + "'");
					objLog = new LogZZZ(sDirectoryLog, sFileLog,IFileExpansionEnabledZZZ.FLAGZ.USE_FILE_EXPANSION.name());
				}else{
					System.out.println("Verwende LogObject erneut");
					objLog = objLogIn;
				}
				this.setLogObject(objLog);
		}//end main
		return bReturn;
	}
	
	/**Merke: Hier wird davon ausgegangen, das Modul - und Programmname identisch sind !!!
	 * FGL 20180909: Müsste nicht eigentlich der Kernel-ApplicationKey als ModulAlias verwendet werden??? Statt Gleichheit???
	* @param sModuleAndProgramAndSection
	* @param sProperty
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 12.01.2007 09:10:41
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getSectionAliasFor(String sModuleOrProgramAsSection) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleOrProgramAsSection)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing 'String Module/Program/Section'",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}			
			}//END check:

			
			FileIniZZZ objFileIniConfig = this.getFileConfigKernelIni();
			String sSectionMain = null;
			
			
			ArrayList<String>alsSystemKey=this.computeSystemSectionNames();
			for(String sSystemKey:alsSystemKey) {
				objReturn = objFileIniConfig.getPropertyValue(sSystemKey, sModuleOrProgramAsSection);
				if(objReturn.hasAnyValue()) break main;
			}
			
//			sSectionMain = this.getSystemKey();
//			objReturn = objFileIniConfig.getPropertyValue(sSectionMain, sModuleAndProgramAndSection);
//			if(objReturn.hasAnyValue()) break main;
//			
//			sSectionMain = this.getApplicationKey();
//			objReturn = objFileIniConfig.getPropertyValue(sSectionMain, sModuleAndProgramAndSection);
//			if(objReturn.hasAnyValue()) break main;
			
		}//END main:
		return objReturn;
	}

	
	//##### Interfaces
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
	
	@Override
	public IKernelContextZZZ getContextUsed() {
		return this.objContext;
	}
	
	@Override
	public void setContextUsed(IKernelContextZZZ objContext) {
		this.objContext = objContext;
	}	
	
	@Override
	public IKernelCacheZZZ getCacheObject() throws ExceptionZZZ{
		if(this.objCache==null){
			this.objCache = new KernelCacheZZZ();
		}
		return this.objCache;		
	}
	
	@Override
	public void setCacheObject(IKernelCacheZZZ objCache){
		this.objCache = objCache;
	}
	
	
	//aus IRessourceHandlingObjectZZZ
	/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
	 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ 
	 */
	@Override
	public boolean isInJar() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
		}
		return bReturn;
	}
	
	//analog zu ObjectZZZ, nur jetzt mit KernelLogZZZ, welches den String ausrechnet.
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectZZZ#logLineDate(java.lang.String)
	 */
	@Override
	public void logLineDate(String sLog) throws ExceptionZZZ {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = AbstractKernelLogZZZ.computeLineDate(this);
			
			IStringJustifierZZZ objStringJustifier = SeparatorMessageStringJustifierZZZ.getInstance();
			sTemp = StringFormaterUtilZZZ.justifyInfoPartAdded(objStringJustifier, sTemp, sLog);
			
			System.out.println(sTemp);
		}else {
			objLog.writeLineDate(sLog);
		}			
	}
	
	//analog zu ObjectZZZ, nur jetzt mit KernelLogZZZ, welches den String ausrechnet.
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectZZZ#logLineDateWithPosition(java.lang.String)
	 */
	@Override
	public void logLineDateWithPosition(String sLog) throws ExceptionZZZ {
		LogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			//Hier nicht die Position hinzunehmen. Wg. des Leerstring kommt sie dann VOR den Kommentar
			String sLine = AbstractKernelLogZZZ.computeLineDate(this, ""); 
			
			//Jetzt die Position extra. Sie kommt ganz hintenan.
			String sPosition = ReflectCodeZZZ.getPositionCurrentInFileForConsoleClickable(1);//.getPositionCurrentInFile(1);//current+1=calling
			sLog = sLog + sPosition;
			
			IStringJustifierZZZ objStringJustifier = SeparatorMessageStringJustifierZZZ.getInstance();
			sLine = StringFormaterUtilZZZ.justifyInfoPartAdded(objStringJustifier, sLine, sLog);
			
			System.out.println(sLine);
		}else {
			objLog.writeLineDateWithPosition(this, 1, sLog);
		}			
	}
	
	
	
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sModule, String sProgramOrSection, String sProperty, boolean bUseCache) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(objFileIniConfig==null){
					String stemp = "'FileIniZZZ'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setSection(sProgramOrSection);
				}
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setProperty(sProperty);
				}
			}//END check:
	
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			objReturn = this.KernelSearchParameterByProgramAlias_(objFileIniConfig, sModule, sProgramOrSection, sProperty, bUseCache, objReturnReference);
		}//END main:
		return objReturn;
	}

	/**Gibt den Konfigurierten Wert eines Programms wieder. Dabei werden globale Werte durch "Speziell" für die SystemNumber definierte Werte �berschrieben
		 * @param objFileIniConfig
		 * @param sAliasProgramOrSection  
		 * @param sProperty
		 * @return Null, wenn die Property nicht gefunden werden kann, sonst String
		 * @throws ExceptionZZZ, wenn z.B. keine Sektion aus sProgramOrSection in der Konfigurationsdatei existiert.
		 *
		 * javadoc created by: 0823, 20.10.2006 - 09:03:54
		 */
	@Override
		public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(FileIniZZZ objFileIniConfig, String sAliasProgramOrSection, String sProperty, boolean bUseCache) throws ExceptionZZZ{
			IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			main:{
					check:{
						if(objFileIniConfig == null){
							String stemp = "Missing parameter: 'Configuration file-object'";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else if(objFileIniConfig.getFileObject().exists()==false){
							String stemp = "Wrong parameter: 'Configuration file-object' does not exist.";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else if(objFileIniConfig.getFileObject().isDirectory()==true){
							String stemp = "Wrong parameter: 'Configuration file-object' is as directory.";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
								
						if(StringZZZ.isEmpty(sAliasProgramOrSection)){
							String stemp = "Missing parameter: 'ProgramOrSection'";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else {
							objReturn.setSection(sAliasProgramOrSection);
						}
						
						if(StringZZZ.isEmpty(sProperty)){
							String stemp = "Missing parameter: 'Property'";
							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
							ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else {
							objReturn.setProperty(sProperty);
						}
					}//end check:
	
					String sProgramOrSection = objFileIniConfig.getPropertyValue(this.getSystemKey(), sAliasProgramOrSection).getValue();
					if(StringZZZ.isEmpty(sProgramOrSection)){
						sProgramOrSection = sAliasProgramOrSection;
					}else {
						objReturn.setSection(sProgramOrSection);
					}
					
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReference.set(objReturn);
					objReturn = this.KernelSearchParameterByProgramAlias_(objFileIniConfig, sProgramOrSection, sAliasProgramOrSection, sProperty, bUseCache, objReturnReference);								
		}//end main:
		return objReturn;		
	}//end function getParameterByProgramAlias(..)	
		
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModule, String sProgramOrSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.		
		String sDebug = "";
		main:{
			check:{
				if(StringZZZ.isEmpty(sModule)){
					String stemp = "'String Module'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(StringZZZ.isEmpty(sProgramOrSection)){
					String stemp = "'String ProgramOrSection'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);					
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setSection(sProgramOrSection);
				}
				if(StringZZZ.isEmpty(sProperty)){
					String stemp = "'String Property'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else {
					objReturn.setProperty(sProperty);
				}
			}//END check:
				  
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			objReturn = this.KernelSearchParameterByProgramAlias_(null, sModule, sProgramOrSection, sProperty, true, objReturnReference);
		}//END main:
		return objReturn;
	}

	/**Merke: Hier wird davon ausgegangen, das Modul - und Programmname identisch sind !!!
	 * FGL 20180909: Müsste nicht eigentlich der Kernel-ApplicationKey als ModulAlias verwendet werden??? Statt Gleichheit???
	* @param sModuleAndProgramAndSection
	* @param sProperty
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 12.01.2007 09:10:41
	 */
	@Override
	public IKernelConfigSectionEntryZZZ getParameterByProgramAlias(String sModuleAndProgramAndSection, String sProperty) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{
			check:{
				if(StringZZZ.isEmpty(sModuleAndProgramAndSection)){
					String stemp = "Missing 'String Module/Program/Section'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);	
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}//END check:
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			objReturn = this.KernelSearchParameterByProgramAlias_(null, null, sModuleAndProgramAndSection, sProperty, true, objReturnReference);
		}//END main:
		return objReturn;
	}

	//# aus ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}
	
	
	//######### Intefaces ############################################################################
		
	//### Aus Interface IKernelZZZ
	@Override
	public boolean getFlag(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//### Aus Interface IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//aus IKernelZFormulaIni_VariableZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnum_IKernelExpressionIniParserZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelExpressionIniParserZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniParserZZZ.FLAGZ[] objaEnum_IKernelExpressionIniParserZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelExpressionIniParserZZZ)) {
				baReturn = new boolean[objaEnum_IKernelExpressionIniParserZZZ.length];
				int iCounter=-1;
				for(IKernelExpressionIniParserZZZ.FLAGZ objEnum_IKernelExpressionIniParserZZZ:objaEnum_IKernelExpressionIniParserZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelExpressionIniParserZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
				
		//### aus IKernelJsonIniSolverZZZ
		@Override
		public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnum_IKernelJsonIniSolverZZZ) throws ExceptionZZZ {
			return this.getFlag(objEnum_IKernelJsonIniSolverZZZ.name());
		}
		
		@Override
		public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnum_IKernelJsonIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnum_IKernelJsonIniSolverZZZ.name(), bFlagValue);
		}

		@Override
			public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objaEnum_IKernelJsonIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJsonIniSolverZZZ)) {
						baReturn = new boolean[objaEnum_IKernelJsonIniSolverZZZ.length];
						int iCounter=-1;
						for(IKernelJsonIniSolverZZZ.FLAGZ objEnum_IKernelJsonIniSolverZZZ:objaEnum_IKernelJsonIniSolverZZZ) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnum_IKernelJsonIniSolverZZZ, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}		
		
		
		@Override
		public boolean proofFlagExists(IKernelJsonIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objaEnumFlag.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
		}
		
		//Aus IKernelJsonMapIniSolverZZZ
		@Override
		public boolean getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.getFlag(objEnumFlag.name());
		}
		
		@Override
		public boolean setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}

		@Override
			public boolean[] setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}

		@Override
		public boolean proofFlagExists(IKernelJsonMapIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objaEnumFlag.name());
		}
		
		@Override
		public boolean proofFlagSetBefore(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
		}

		//### aus IKernelJsonArrayIniSolverZZZ
			@Override
			public boolean getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.getFlag(objEnumFlag.name());
			}
			
			@Override
			public boolean setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnumFlag.name(), bFlagValue);
			}

			@Override
				public boolean[] setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
					boolean[] baReturn=null;
					main:{
						if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
							baReturn = new boolean[objaEnumFlag.length];
							int iCounter=-1;
							for(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
								iCounter++;
								boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
								baReturn[iCounter]=bReturn;
							}
						}
					}//end main:
					return baReturn;
				}
			
			@Override
			public boolean proofFlagExists(IKernelJsonArrayIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objaEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
					return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			//### aus IKernelCallIniSolverZZZ
			@Override
			public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.getFlag(objEnumFlag.name());
			}
			
			@Override
			public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnumFlag.name(), bFlagValue);
			}

			@Override
				public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
					boolean[] baReturn=null;
					main:{
						if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
							baReturn = new boolean[objaEnumFlag.length];
							int iCounter=-1;
							for(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
								iCounter++;
								boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
								baReturn[iCounter]=bReturn;
							}
						}
					}//end main:
					return baReturn;
				}
			
			@Override
			public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objaEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
					return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			//### Aus Interface IKernelJavaCallIniSolverZZZ
			@Override
			public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
				return this.getFlag(objEnum_IKernelJavaCallIniSolverZZZ.name());
			}
			
			@Override
			public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJavaCallIniSolverZZZ)) {
						baReturn = new boolean[objaEnum_IKernelJavaCallIniSolverZZZ.length];
						int iCounter=-1;
						for(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ:objaEnum_IKernelJavaCallIniSolverZZZ) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
				return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
					return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			//### aus IKernelZFormulaIni_PathZZZ
			@Override
			public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.getFlag(objEnumFlag.name());
			}
			@Override
			public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnumFlag.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
						baReturn = new boolean[objaEnumFlag.length];
						int iCounter=-1;
						for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
					return this.proofFlagExists(objEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
					return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			
			//### aus IKernelZFormulaIniZZZ
			@Override
			public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ) throws ExceptionZZZ {
				return this.getFlag(objEnum_IKernelZFormulaIniZZZ.name());
			}
			
			@Override
			public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnum_IKernelZFormulaIniZZZ.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIniZZZ)) {
						baReturn = new boolean[objaEnum_IKernelZFormulaIniZZZ.length];
						int iCounter=-1;
						for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ:objaEnum_IKernelZFormulaIniZZZ) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIniZZZ, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			
			
			//### aus IIniTagWithExpressionZZZ
			@Override
			public boolean getFlag(IObjectWithExpressionZZZ.FLAGZ objEnum_IIniTagWithExpressionZZZ) throws ExceptionZZZ {
				return this.getFlag(objEnum_IIniTagWithExpressionZZZ.name());
			}
			
			@Override
			public boolean setFlag(IObjectWithExpressionZZZ.FLAGZ objEnum_IIniTagWithExpressionZZZ, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnum_IIniTagWithExpressionZZZ.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IObjectWithExpressionZZZ.FLAGZ[] objaEnum_IIniTagWithExpressionZZZ, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnum_IIniTagWithExpressionZZZ)) {
						baReturn = new boolean[objaEnum_IIniTagWithExpressionZZZ.length];
						int iCounter=-1;
						for(IObjectWithExpressionZZZ.FLAGZ objEnum_IIniTagWithExpressionZZZ:objaEnum_IIniTagWithExpressionZZZ) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnum_IIniTagWithExpressionZZZ, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagExists(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
			//aus IKernelZFormulaIni_VariableZZZ
			@Override
			public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
				return this.getFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name());
			}
			
			@Override
			public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
				return this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name(), bFlagValue);
			}
			
			@Override
			public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
				boolean[] baReturn=null;
				main:{
					if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_VariableZZZ)) {
						baReturn = new boolean[objaEnum_IKernelZFormulaIni_VariableZZZ.length];
						int iCounter=-1;
						for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ:objaEnum_IKernelZFormulaIni_VariableZZZ) {
							iCounter++;
							boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ, bFlagValue);
							baReturn[iCounter]=bReturn;
						}
					}
				}//end main:
				return baReturn;
			}
			
			@Override
			public boolean proofFlagExists(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagExists(objEnumFlag.name());
			}
			
			@Override
			public boolean proofFlagSetBefore(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
				return this.proofFlagSetBefore(objEnumFlag.name());
			}
			
}//end class
