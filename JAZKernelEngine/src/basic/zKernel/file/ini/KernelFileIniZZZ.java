/*
 * Created on 05.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file.ini;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.formula.ZFormulaIniLineZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.crypt.code.ICryptUserZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryCreatorZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.AbstractKernelObjectZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.cache.ICachableObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;


/**

@author 0823 ,date 05.10.2004
*/
/**
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 31.08.2024, 08:07:50
 * 
 */
public class KernelFileIniZZZ<T> extends AbstractKernelUseObjectZZZ<T> implements IKernelFileIniZZZ, IKernelExpressionIniHandlerUserZZZ, IKernelConfigSectionEntryUserZZZ, IValueVariableUserZZZ, IListenerObjectFlagZsetZZZ, IIniTagWithConversionZZZ, IObjectWithExpressionZZZ, IKernelExpressionIniParserZZZ, IKernelZFormulaIni_PathZZZ, ISolveEnabledZZZ, IKernelExpressionIniSolverZZZ, IKernelCallIniSolverZZZ, IKernelJavaCallIniSolverZZZ, IKernelJsonIniSolverZZZ, IKernelJsonArrayIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelZFormulaIniZZZ, IKernelZFormulaIni_VariableZZZ, IKernelEncryptionIniSolverZZZ, ICryptUserZZZ, ICachableObjectZZZ{
//20170123: Diese Flags nun per Reflection aus der Enumeration FLAGZ holen und in eine FlagHashmap (s. ObjectZZZ) verwenden.
//	private boolean bFlagFileUnsaved;
//	private boolean bFlagFileNew; // don't create a file in the constructor
//	private boolean bFlagFileChanged;
//	private boolean bFlagUseFormula=true;  //Falls true, dann wird ggf. die Formel in der ini-Datei aufgelöst. z.B. <Z>[Section A]Value1</Z>. Siehe KernelExpressionIniSolver.
	
//Flags, die alle Z-Objekte haben
//	private boolean bFlagDebug;
//	private boolean bFlagInit;
		
	private static final long serialVersionUID = -7112079956493555008L;
	protected IniFile objFileIni;
	protected File objFile;
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable;
	protected ICryptZZZ objCrypt = null;
	//private IKernelConfigSectionEntryZZZ objEntry = null;
	protected IKernelExpressionIniHandlerZZZ objExpressionHandler = null;
	
	
	//20190718: Durch KernelConfigEntryZZZ - Klasse soll es nicht mehr notwendig sein die Werte in KernelFileIniZZZ zu speichern.
	//20190226: Problem: Wenn ein Wert (z.B. <z:Empty/> konvertiert wurde, weiss das die aufrufenden Methode nicht.
	//                   Um auf solch einen Wert reagieren zu können (also im genannten Beispiel zu merken: Der Wert ist absichtlich leer) das Flag und den 
	//private boolean bValueConverted=false;//Wenn durch einen Converter der Wert verändert wurde, dann wird das hier festgehalten.
	//private String sValueRaw=null;
	
	//Aus ICachableObjectZZZ
	private boolean bSkipCache=false;

	public KernelFileIniZZZ() throws ExceptionZZZ{
		super("init");//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, null, null, null, null);
	}
	
	public KernelFileIniZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
		KernelFileIniNew_(null, null, null, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		
		String[]saFlagControl = {""};
		KernelFileIniNew_(null, objKernel.getFileConfigKernelDirectory(), objKernel.getFileConfigKernelName(), null, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null,saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel, hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		
		String[]saFlagControl = HashMapZZZ.getKeysAsStringFromValue(hmFlag, new Boolean(true));
		KernelFileIniNew_(null, sDirectory, sFilename, null,saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile) throws ExceptionZZZ{
		super(objKernel, (String) null);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile,null, null, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile,String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile,null, null, null, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile,HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel,hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		
		String[]saFlagControl = HashMapZZZ.getKeysAsStringFromValue(hmFlag, new Boolean(true));
		KernelFileIniNew_(objFile,null, null, null, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(null, sDirectory, sFilename, null, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, String sDirectory, String sFilename,  HashMapCaseInsensitiveZZZ<String,String> hmVariable, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel, hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		
		String[]saFlagControl = HashMapZZZ.getKeysAsStringFromValue(hmFlag, new Boolean(true));
		KernelFileIniNew_(null, sDirectory, sFilename, hmVariable, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile, null, null, hmVariable, null);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, saFlagControl);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		KernelFileIniNew_(objFile, null, null, hmVariable, saFlagControl);
	}
	
	public KernelFileIniZZZ(IKernelZZZ objKernel, File objFile, HashMapCaseInsensitiveZZZ<String,String> hmVariable, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(objKernel,hmFlag);//20210402: Die direkte FlagVerarbeitung wird nun im ElternObjekt gemacht
		
		String[]saFlagControl = HashMapZZZ.getKeysAsStringFromValue(hmFlag, new Boolean(true));
		KernelFileIniNew_(objFile,null, null, hmVariable, saFlagControl);
	}
	
	// DESTRUCTOR
	protected void finalize(){
		if(this.objFileIni!=null) this.objFileIni = null;
		if(this.objFile!=null) this.objFile = null;
	}
	
	/** ++++++++++++++++++++++++++++++++++++++++++
	
	 @author 0823 , date: 05.10.2004
	 @param objKernel
	 @param objLog
	 @param sFilePath
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileIniNew_(File objFileIn, String sDirectoryIn, String sFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagUsed) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String sLog;
	 main:{
		 	
	 	try{
	 		if(saFlagUsed!=null){
		 		if(StringArrayZZZ.containsIgnoreCase(saFlagUsed, "INIT")) {
					this.setFlag("INIT", true);
				} 
	 		}
	 		bReturn = this.getFlag("INIT");
	 		if(bReturn) break main;
	 			

			if (this.getFlag("filenew")==false){		
				if(objFileIn==null & (sDirectoryIn==null | sFileIn == null)){
					ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter File-Object or File-Path information", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
		
				File objFile = null;			 
				if(objFileIn !=null){
					this.setFileObject(objFileIn);
					objFile = objFileIn;						
				}else if(sFileIn.equals("")){
						ExceptionZZZ ez = new ExceptionZZZ( "Parameter Filename is empty: '" + objFile.getPath() + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 
				}else{
					String sDirectory = FileEasyZZZ.getFileUsedPath(sDirectoryIn);
					if(sDirectory.equals("")){
						objFile = new File(sFileIn);
					}else{
						String sFilePath = FileEasyZZZ.joinFilePathName(sDirectory, sFileIn);
						objFile = new File(sFilePath);
					}				
				}
				
				if(objFile==null){
					sLog = "Configuration File does not exist (=null, für Directory='" + sDirectoryIn +"', File='" + sFileIn +"')'.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if(!FileEasyZZZ.exists(objFile)){
					sLog = "Configuration File does not exist '" + objFile.getAbsolutePath() + "'.";
					this.logLineDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
					ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
								
				//create the ini-file-object from file-object
				this.setFileObject(objFile);
	
				IniFile objFileIni = new IniFile(objFile.getPath(), false); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;		
			}else{
				IniFile objFileIni = new IniFile(); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;	
			}
	 	}catch (IOException e){
	 		System.out.println(e.getMessage());	
	 		bReturn = false;
	 		break main;
		 }
	 	
	 	this.setHashMapVariable(hmVariable);
	 	}//end main:
		return bReturn;
	 }//end function
	
	
	public static String getSectionUsedForPropertyBySystemKey(IKernelFileIniZZZ objKernelIniFile, String sSystemKeyAsSection, String sProperty) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objKernelIniFile==null) break main;
			
			if(StringZZZ.isEmpty(sSystemKeyAsSection)) break main;
			if(StringZZZ.isEmpty(sProperty)) break main;
			
			//0. Erst einmal feststellen ob wir in der Haupt- bzw. Eltersection starten oder in der SystemSection.
			boolean bUseSystemKey=false;
			String sValueOld=null;
			String sSectionUsed = null;
			boolean bSystemKey = AbstractKernelObjectZZZ.isSystemSection(sSystemKeyAsSection);
			if(bSystemKey) {	
				sSectionUsed = sSystemKeyAsSection;
				
				//A) Hole den Wert in der SystemKeySection
				sValueOld = objKernelIniFile.getFileIniObject().getValue(sSectionUsed, sProperty);
				if(StringZZZ.isEmpty(sValueOld)) {
					
					//Wurde in der PARENTSECTION der Wert überhaupt gesetzt?
					sSectionUsed = AbstractKernelObjectZZZ.computeSectionFromSystemSection(sSystemKeyAsSection);
					sValueOld = objKernelIniFile.getFileIniObject().getValue(sSectionUsed, sProperty);
					if(StringZZZ.isEmpty(sValueOld)) {
						bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
					}else {
						bUseSystemKey=false;
					}
				}else {
					
					bUseSystemKey=true;												
				}
				
			}else {
				//B) Fange mit dem SystemKey an
				String sSystemNumber = objKernelIniFile.getKernelObject().getSystemNumber();
				sSectionUsed = AbstractKernelObjectZZZ.computeSystemSectionNameForSection(sSystemKeyAsSection, sSystemNumber);
						
				sValueOld = objKernelIniFile.getFileIniObject().getValue(sSectionUsed, sProperty);
				if(StringZZZ.isEmpty(sValueOld)) {
					bUseSystemKey=false;
				}else {
					
					//Wurde dort der Wert gesetzt?
					sSectionUsed = sSystemKeyAsSection;
					sValueOld = objKernelIniFile.getFileIniObject().getValue(sSectionUsed, sProperty);
					if(StringZZZ.isEmpty(sValueOld)) {
						bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
					}else {
						bUseSystemKey=false;
					}
				}
			}
			
			//+++ Nun festlegen, welche konkrete Section für diesen Wert verwendet werden soll
			sSectionUsed=null;
			if(bUseSystemKey) {
				String sSystemNumber = objKernelIniFile.getKernelObject().getSystemNumber();
				sReturn = AbstractKernelObjectZZZ.computeSystemSectionNameForSection(sSystemKeyAsSection, sSystemNumber);					
			}else {
				sReturn = AbstractKernelObjectZZZ.computeSectionFromSystemSection(sSystemKeyAsSection);
			}
		}
		return sReturn;
	}
	
	/** Gibt true zur�ck, wenn in dem Section String (ab dem 2. Buchstaben) ein Ausrufezeichen steht und ggf. ein Folgebuchstabe.
	 * Also der minimalste Section-Name ist z.b. "a!a"
	* @param sSection
	* @return
	* 
	* lindhauer; 09.01.2008 07:29:59
	 * @throws ExceptionZZZ 
	 */
	public static boolean isSectionWithSystemNrAny(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			if(sSection.length()<=2) break main;
			if(sSection.startsWith("!")) break main;
			if(sSection.endsWith("!")) break main;
			
			bReturn =  StringZZZ.contains(sSection, "!");
		}
		return bReturn;
	}
	
	/** TODO String[], All properties of all sections of the .ini-file. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 10:16:44
	 * @return String[]
	 */
	public String[] getPropertyAll(){
		return this.objFileIni.getVariables();
	}
	
	/** String[], All Properties of a section. The Array is contains only unique values.
	* Lindhauer; 23.04.2006 09:57:53
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getPropertyAll(String sSectionIn) throws ExceptionZZZ {
		return this.getPropertyAll_(sSectionIn, true);
	}
	

	/** String[], All Properties of a section. The Array is contains only unique values.
	 * @param sSectionIn
	 * @param bSearchApplicationKey: false=restrict this only to the Section, do not search for the applicationKey.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 02.11.2021, 09:09:48
	 */
	public String[] getPropertyAll(String sSectionIn, boolean bSearchApplicationKey) throws ExceptionZZZ {
		return this.getPropertyAll_(sSectionIn, bSearchApplicationKey);
	}
	
	private String[] getPropertyAll_(String sSectionIn, boolean bSearchApplicationKey) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			String sSection;String sApplication; String sSystemNumber;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sSection = sSectionIn;
				}			
				
				
			}//end check:
			
			if(bSearchApplicationKey) {
				sApplication = StringZZZ.left(sSection, "!");
				sSystemNumber = StringZZZ.right(sSection, "!");
					
				boolean bFoundSystemNumber=false;String[]saSystemNumber=null;
				if(!StringZZZ.isEmpty(sSystemNumber)) {
					bFoundSystemNumber=true;
					saSystemNumber = this.objFileIni.getVariables(sSection);
					
				}
				
				boolean bFoundApplication=false;String[]saApplication=null;
				if(!StringZZZ.isEmpty(sApplication)) {
					bFoundApplication=true;
					saApplication = this.objFileIni.getVariables(sApplication);
					
				}
				
				if(bFoundSystemNumber && !bFoundApplication) {
					saReturn = saSystemNumber;
					break main;	
				}else if(!bFoundSystemNumber && bFoundApplication) {
					saReturn = saApplication;
					break main;	
				}else if(!bFoundSystemNumber && !bFoundApplication) {
					saReturn = this.objFileIni.getVariables(sSection);
					break main;
				}else {
					String[] saFlag = {"SKIPNULL","ADDMISSING"};
					saReturn = StringArrayZZZ.append(saSystemNumber, saApplication, saFlag);
					break main;	
				}
			}else {
				saReturn = this.objFileIni.getVariables(sSection);
			}		
		}//end main:
		return saReturn;
	}
	
	
	
	/**  String, the value of a property in a section	
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @return the string entry at the section for the specified property
	 @throws ExceptionZZZ
	 */
	public IKernelConfigSectionEntryZZZ getPropertyValue(String sSectionIn, String sPropertyIn) throws ExceptionZZZ{		 
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		
		//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten": //IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
		
		main:{
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			this.getPropertyValue_(sSectionIn, sPropertyIn, objReturnReference);
			objReturn = objReturnReference.get();
			this.setEntry(objReturn);
		}//end main:
		return objReturn;
	}//end function
	
	/**  String, the value of a property in a section	
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @return the string entry at the section for the specified property
	 @throws ExceptionZZZ
	 */
	private IKernelConfigSectionEntryZZZ getPropertyValue_(String sSectionIn, String sPropertyIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		boolean bUseExpression=false;
		if(objReturnReferenceIn==null) {	
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			//Aber hier keine Tag, sondern ein FileIni, darum geht nicht objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
			                                                             //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
		}else {
			objReturnReference = objReturnReferenceIn;
			objReturn = objReturnReference.get();
		}

		
		if(objReturn==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturn = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objReturn);
		}//Achtung: Das objReturn Objekt NICHT generell aus dem FileIni-Objekt uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			
		main:{
			String sSection; String sProperty;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sSection = sSectionIn;
					objReturn.setSection(sSection);
				}			
				if(StringZZZ.isEmpty(sPropertyIn)){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sProperty = sPropertyIn;
					objReturn.setProperty(sProperty,false);
				}					
			}//end check:
							
			//############################
			//### GRUNDSATZ:
			//### ENDLOSSCHLEIFENGEFAHR, WENN NICHT AUF DIESER EBENE DIREKT MIT DEM INIFILE GEARBEITET WUERDE 
			//### UND WIEDER KERNEL.getPropertyValue() aufgerufen wuerde.
			//FileIniZZZ objFileIniConfig = this.getKernelObject().getFileConfigIni();
			//############################
			
			//!!! Falls es schon in der Section ein Ausrufezeichen gibt, so entfaellt der 1. Versuch. Es wird sofort nach der konkreten Section gesucht
			String sReturnRaw = null;
			String sApplicationKeyUsed= this.getKernelObject().getApplicationKey();
			String sSystemNumberUsed=null;
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			if(! KernelFileIniZZZ.isSectionWithSystemNrAny(sSection)){
				//Wenn in der Section keine Systemnr steckt, diese ermitteln.							
				sSystemNumberUsed = this.getKernelObject().getSystemNumber();
				objReturn.setSystemNumber(sSystemNumberUsed);				
			}else {
				sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sSection);
				objReturn.setSystemNumber(sSystemNumberUsed);
				
				//Zuerst direkt in der übergebenen Section nachsehen
				getPropertyValueDirectLookup_(sSection, sProperty, objReturnReference);	
				objReturn = objReturnReference.get();
			}
			
			if(!objReturn.hasAnyValue()) {
				//Wurde bisher nix gefunden: In allen möglichen Sections nachsehen UND das als Program behandeln.
				ArrayList<String>alsSection=AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(this, sSection, sApplicationKeyUsed, sSystemNumberUsed);
				for(String sSectionUsed:alsSection) {
					objReturnReference.set(objReturn);
					this.getPropertyValueDirectLookup_(sSectionUsed, sProperty, objReturnReference);
					objReturn = objReturnReference.get();
					if(objReturn.hasAnyValue()) break;								
				}				
			}
				
			sReturnRaw = objReturn.getRaw();
			if(sReturnRaw==null) break main;
			
			//+++ 20191126: Auslagern der Formelausrechung in eine Utility Klasse. Ziel: Diese Routine von mehreren Stellen aus aufrufen können.
			IKernelExpressionIniHandlerZZZ objExpressionHandler = this.getExpressionHandlerNew();			
			objReturnReference.set(objReturn);
			String sReturnValue = objExpressionHandler.solve(sReturnRaw,objReturnReference);//Merke: parse() reicht nicht, da damit keine Formeln aufgeloest werden.	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ergebnis der Expression: '" + sReturnValue + "'");
			objReturn = objReturnReference.get();						
		}//end main:
		objReturnReference.set(objReturn);
		return objReturn;
	}//end function
	
	public IKernelConfigSectionEntryZZZ getPropertyValueDirectLookup(String sSection, String sProperty) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();  //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist. 
		                                                              //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.	
		main:{			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			this.getPropertyValueDirectLookup_(sSection, sProperty, objReturnReference);
			objReturn = objReturnReference.get();
			this.setEntry(objReturn);
		}//end main:
		return objReturn;
	}
	
	private IKernelConfigSectionEntryZZZ getPropertyValueDirectLookup_(String sSection, String sProperty, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn=null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {	
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			//Aber hier keine Tag, sondern ein FileIni, darum geht nicht objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
			                                                             //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
		}else {
			objReturnReference = objReturnReferenceIn;
			objReturn = objReturnReference.get();
		}
		if(objReturn==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturn = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objReturn);
		}//Achtung: Das objReturn Objekt NICHT generell aus dem FileIni-Objekt uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			objReturn.setSection(sSection);	
			if(StringZZZ.isEmpty(sProperty)) break main;						
			objReturn.setProperty(sProperty, false);
			
			boolean bSectionExists = this.proofSectionExistsDirectLookup(sSection);
			if(bSectionExists) {
				String sReturnRaw=null;
				objReturn.setSection(sSection, true);
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ "Hole Wert für Section= '" + sSection + "' und Property = '" + sProperty +"'");
				sReturnRaw = this.objFileIni.getValue(sSection, sProperty);
				if(sReturnRaw!=null) {											
					objReturn.setRaw(sReturnRaw);
					objReturn.setValue(sReturnRaw);			
					objReturn.setProperty(sProperty, true);
				}
			}else {
				objReturn.setSection(sSection , false);
			}
		}//end main:
		objReturnReference.set(objReturn);
		return objReturn;
	}
	
	public IKernelConfigSectionEntryZZZ getPropertyValueDirectLookup(String sSection, String sProperty, String sSystemNumberIn) throws ExceptionZZZ {
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
		
		main:{
			if(StringZZZ.isEmpty(sSection)) break main;
			objReturn.setSection(sSection);	
			
			String sSystemNumber=null;
			if(StringZZZ.isEmpty(sSystemNumberIn)) {
				//Versuch die SystemNumber aus der Section zu errechnen
				sSystemNumber = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sSection);				
			}else {
				sSystemNumber=sSystemNumberIn;				
			}
			objReturn.setSystemNumber(sSystemNumber);
			
			if(StringZZZ.isEmpty(sProperty)) break main;						
			objReturn.setProperty(sProperty, false);
			
			String sSectionUsed = AbstractKernelObjectZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);
			boolean bSectionExists = this.proofSectionExistsDirectLookup(sSectionUsed);
			if(bSectionExists) {
				String sReturnRaw=null;
				objReturn.setSection(sSectionUsed, true);
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Hole Wert für Section= '" + sSection + "' und Property = '" + sProperty +"'");
				objReturn.setProperty(sProperty, false);
				sReturnRaw = this.objFileIni.getValue(sSectionUsed, sProperty);
				if(sReturnRaw!=null) {											
					objReturn.setRaw(sReturnRaw);
					objReturn.setValue(sReturnRaw);		
					objReturn.setProperty(sProperty, true);
				}
			}else{
				objReturn.setSection(sSectionUsed, false);
			}
		}//end main:
		this.setEntry(objReturn);
		return objReturn;
	}
	
	/** Returns a Value by SystemNr. If this doesn´t exists the global value will be returned (value without a SystemNr).
	* @param sSectionIn
	* @param sPropertyIn
	* @param sSystemNrIn
	* @return The value of a string in the section. Start with the section + "!" + SystemNr
	* 
	* lindhauer; 09.01.2008 07:15:18
	 * @throws ExceptionZZZ 
	 */
	public IKernelConfigSectionEntryZZZ getPropertyValueSystemNrSearched(String sSectionIn, String sPropertyIn, String sSystemNrIn) throws ExceptionZZZ{
		//!!! Das ist lediglich eine Untergeordnete Suchanfrage..., daher: 
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile! //IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); 
		main:{
			String sSectionUsed; String sProperty; String sSystemNumberUsed;
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					//Gibt es einen Programnamen im der Section?
					//Wenn ja , dann das die ganze Section Vewrenenden.
					String sProgram = AbstractKernelObjectZZZ.extractProgramFromSection(sSectionIn);
					if(StringZZZ.isEmpty(sProgram)){										
						sSectionUsed = AbstractKernelObjectZZZ.extractModuleFromSection(sSectionIn);
						if(StringZZZ.isEmpty(sSectionUsed)){
							ExceptionZZZ ez = new ExceptionZZZ( "not extractable parameter 'Section' from '"+sSectionIn + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;
						}	
					}else {
						sSectionUsed = sSectionIn;
					}
				}
				objReturn.setSection(sSectionUsed);
				
				if(StringZZZ.isEmpty(sSystemNrIn)) {
					sSystemNumberUsed = AbstractKernelObjectZZZ.extractSytemNumberFromSection(sSectionIn);
					//Falls leer, keine Exception, sondern die default verwenden
					if(StringZZZ.isEmpty(sSystemNumberUsed)) {
						sSystemNumberUsed = this.getKernelObject().getSystemNumber();
					}					
				}else {
					sSystemNumberUsed = sSystemNrIn;
				}
				objReturn.setSystemNumber(sSystemNumberUsed);
				
				if(StringZZZ.isEmpty(sPropertyIn)){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					sProperty = sPropertyIn;					
				}
				objReturn.setProperty(sProperty);
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReference.set(objReturn);				
				if(StringZZZ.isEmpty(sSystemNrIn)){
					
					String sSectionUsedFirst = AbstractKernelObjectZZZ.computeSystemSectionNameForSection(sSectionUsed, sSystemNumberUsed);
					
					//Zuerst direkt in der übergebenen Section, mit allen Systemnr. nachsehen
					this.getPropertyValue_(sSectionUsedFirst, sProperty, objReturnReference);				
					objReturn = objReturnReference.get();
					if(objReturn.hasAnyValue())  break main;
					
					String sApplicationKeyUsed = this.getKernelObject().getApplicationKey();
						
					//Wurde bisher nix gefunden: In allen möglichen Sections nachsehen UND das als Program behandeln.
					ArrayList<String>alsSection=AbstractKernelObjectZZZ.computeSystemSectionNamesForProgram(this, sSectionUsed, sApplicationKeyUsed, sSystemNumberUsed);
					for(String sSectionUsedTemp:alsSection) {
						this.getPropertyValueDirectLookup_(sSectionUsedTemp, sProperty, objReturnReference);
						objReturn = objReturnReference.get();
						if(objReturn.hasAnyValue()) break;								
					}									
					
				}else{
					//Hier nur nach der explizit uebergebenen SystemNr. Suchen
					sSystemNumberUsed = sSystemNrIn;
					objReturn.setSystemNumber(sSystemNumberUsed);
					
					//Das entfällt String sSectionUsedFirst = KernelKernelZZZ.computeSystemSectionNameForSection(sSectionUsed, sSystemNumberUsed);					
					//Direkt in der übergebenen Section, mit der Systemnr. nachsehen					
					objReturn = this.getPropertyValueDirectLookup(sSectionUsed, sProperty,sSystemNumberUsed);
				}
				
		}//end main:
		this.setEntry(objReturn);
		return objReturn;
	}
	
	/** String[], All values of a section.
	* Lindhauer; 24.04.2006 07:34:11
	 * @param sSectionIn
	 * @return
	 * @throws ExceptionZZZ
	 */
	public String[] getPropertyValueAll(String sSectionIn) throws ExceptionZZZ{
		String[] saReturn=null;
		main:{
			String sSection;
			check:{
				if(this.objFileIni==null){				
					ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					//doesn�t work. Only works when > JDK 1.4
					//Exception e = new Exception();
					//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					throw ez;						
				}else{
					sSection = sSectionIn;
				}			
			}//END check:
			
			//Nun alle Properties der Section auslesen
			String[] saPropertyAll = this.getPropertyAll(sSection);
			if(saPropertyAll==null) break main;
			
			ArrayList listaString = new ArrayList(saPropertyAll.length);
			for(int icount=0; icount < saPropertyAll.length; icount++){
				 IKernelConfigSectionEntryZZZ objEntry = this.getPropertyValue(sSection, saPropertyAll[icount]);
				 String stemp = objEntry.getValue();
				listaString.add(icount, stemp);
			}
			
			//durch den neuen Parameter wird der Datentyp des zur�ckzugebenden Arrays definiert.
			//Trotzdem braucht man den Typcast.
			saReturn =  (String[]) listaString.toArray(new String[0]);
			
		}//END main:
		return saReturn;
	}
	
	/** 
	 this will add an entry to the ini-file
	 the entry will be encrypted
	 if bFlagSaveImmidiate= false, the change will be kept  in memory only.
	 Then you have to call the save() method to write this to the filesystem.
	 if bFlagSaveImmidiate = true, the change will be written to the file immidiately	 	 
	 
	 * @param sSectionIn
	 * @param sPropertyIn
	 * @param sValueIn
	 * @param objCryptAlgorithm
	 * @param bFlagSaveImmidiate
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 12.03.2023, 13:53:24
	 */
	public boolean setPropertyValue(String sSectionIn, String sPropertyIn, String sValueIn, ICryptZZZ objCrypt, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		return this.setPropertyValue_(sSectionIn, sPropertyIn, sValueIn, objCrypt, bFlagSaveImmidiate);
	}//end function
	
	
	/** 
	 this will add an entry to the ini-file
	 if bFlagSaveImmidiate= false, the change will be kept  in memory only.
	 Then you have to call the save() method to write this to the filesystem.
	 if bFlagSaveImmidiate = true, the change will be written to the file immidiately
	 	 
	 @author 0823 , date: 18.10.2004
	 @param sSectionIn
	 @param sPropertyIn
	 @param sValueIn
	 @param bFlagSaveImmediate
	 @return boolean, true = everything was fine
	 @throws ExceptionZZZ
	 */
	public boolean setPropertyValue(String sSectionIn, String sPropertyIn, String sValueIn, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		return this.setPropertyValue_(sSectionIn, sPropertyIn, sValueIn, null, bFlagSaveImmidiate);
	}//end function
	
	private boolean setPropertyValue_(String sSectionIn, String sPropertyIn, String sValueIn, ICryptZZZ objCrypt, boolean bFlagSaveImmidiate) throws ExceptionZZZ{
		boolean bReturn = false;
			main:{
				try {
				String sSection; String sProperty;String sValue;
				check:{
					if(this.objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;		
					}
					if(StringZZZ.isEmpty(sSectionIn)){
						ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;			
					}else{
						sSection = sSectionIn;
					}			
					if(StringZZZ.isEmpty(sPropertyIn)){
						ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Property'",iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;						
					}else{
						sProperty = sPropertyIn;
					}				
					
					//Remark: An empty String may be allowed !!!
					if(sValueIn==null){
						ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Value'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						throw ez;	
					}						 													
				}//end check:
																		
				if(objCrypt!=null) {
					//1. Verschluessel den Wert mit dem übergebenen Algorithmus
					IKernelConfigSectionEntryZZZ objEntryWithCrypt = KernelConfigSectionEntryCreatorZZZ.createEntryEncrypted(sValueIn, objCrypt);
					
					//TODOGOON20230314;
					//2. Baue für den Wert und die Parameterwerte des Algorithmus einen Expression-Tag String
					//Z.B.: <Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>grfgjreg4qrpelcgrq ybpny 4 cebtenz</Z:Code></Z:Encrypted></Z>					
					sValue = ZFormulaIniLineZZZ.createLineFrom(objEntryWithCrypt);
				}else {
					//Merke: 20211130: Beim Einlesen in den "Eigenschafts Editor" DLGBox4Ini gab es das Problem, dass <z:Null> zum Leerstring wird " "!!!
				       //          Das darf nicht sein, denn beim Zurückspeichern wird korrekterweise ein Leerstring " " zu <z:Empty>!!!
				       //Lösung: USEEXPRESSION wird nun über den -z Paramter als false übergeben an das neu zu erstellende Kernel-Objekt.
				       //        Damit wird dieser Wert in dem FileIniZZZ Objekt nicht gesetzt => Es findet keine Übersetzung statt. 
				       //        Korrekterweise bleibt dann z.B. <z:Null> bestehen!!!
				
					
					//Hole den passenden Converter. 
					IConvertEnabledZZZ objExpression = KernelZFormulaIniConverterZZZ.getAsObject(sValueIn);
					
					//20190123: Hier den Stringwert in ein ini-Tag wandeln, falls er z.B. Leerstring ist => KernelExpressionIni_Empty Klasse.
					if(objExpression!=null){
						sValue = objExpression.convert(sValueIn);
						if(!sValueIn.equals(sValue)){
							System.out.println(ReflectCodeZZZ.getMethodCurrentNameLined(0)+ ": Value durch ExpressionIniConverter verändert von '" + sValueIn + "' nach '" + sValue +"'");
						}
					}else{						
						sValue = sValueIn;
					}
				}			
				
				//20211130: Ziel muss es sein den Wert in die passende Section zu füllen, also dort wo schon etwas drin steht.
				String sSectionUsed = this.getSectionUsedForPropertyBySystemKey(sSection, sProperty);
							
				this.objFileIni.setValue(sSectionUsed, sProperty, sValue);
				if(bFlagSaveImmidiate==true){
					this.objFileIni.saveFile();
					this.setFlag(IKernelFileIniZZZ.FLAGZ.FILEUNSAVED, false);
				}else{
					this.setFlag(IKernelFileIniZZZ.FLAGZ.FILEUNSAVED, true);				   				 
				}
				 this.setFlag(IKernelFileIniZZZ.FLAGZ.FILECHANGED, true);
				
				 //if this works, everything is fine
				 bReturn = true;
				 				
				} catch (IOException e) {									
					ExceptionZZZ ez =	new ExceptionZZZ(	"IOException: '" + e.getMessage(),iERROR_RUNTIME,this,ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}//end main:
			return bReturn;
	}//end function
	
	
	/** boolean, set all property=value entries of a section. 
	* Lindhauer; 25.04.2006 08:39:25
	 * @param sSectionIn
	 * @param objHtValue, the property-value-pairs which should be set.
	 * @param bFlagClearbefore, true: The section will be removed and then created again. False: Only add or replace the values. Value-pairs not contained by the hashtable will not be affected.
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public boolean setSection(String sSectionIn, Hashtable objHtValue, boolean bFlagClearBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sSection;
			check:{
				if(this.objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ("missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				if(StringZZZ.isEmpty(sSectionIn)){
					ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'Section'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;					
				}else{
					sSection = sSectionIn;
				}			
				if(objHtValue==null){
					ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'Hashtable Object'",iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else if(objHtValue.size() <= 0){
					ExceptionZZZ ez = new ExceptionZZZ( "empty parameter Hashtable Object'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}else{
					//Es wird hier nicht mit einer Kopie des Objekts gearbeitet, da auch nur aus dem Objekt gelesen werden soll.
				}				
				
			}//end check:
			
			//##############################
			//Falls gewünscht die Section erst entfernen
			if(bFlagClearBefore == true){
				this.deleteSection(sSection);
			}
			
			//Section erstellen oder auffüllen. Alle Elemente der Hashtable durchgehen
			Enumeration objEnum = objHtValue.keys();
			while(objEnum.hasMoreElements()){
				String sProperty = (String) objEnum.nextElement();
				if(!sProperty.trim().equals("")){
					String sValue = (String) objHtValue.get(sProperty);
					
					//Auch falls der Wert ein Leerstring ist, diesen setzen.
					this.setPropertyValue(sSection, sProperty, sValue, false);
				}
			} //end while
			bReturn = true;
			
			
		}//end main:
		return bReturn;
	}
	
	/** String[], All sections of the ini-File
	* Lindhauer; 23.04.2006 10:01:12
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public String[] getSectionAll() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			IniFile objFileIni = this.getFileIniObject();
			if(objFileIni==null){
				String stemp =  "missing property 'FileIniObject'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
						
			//#####
			saReturn = this.objFileIni.getSubjects();
						
		}//end main
		return saReturn;
	}
	
	/** Holle die Section für eine Property, in der der Wert schon gefüllt ist. 
	 *  Ziel ist es also nur dann die Section mit dem "reinen" ApplicationKey zu bekommen, wenn in dem Wert für den "SystemKey" nix drin steht.
	 * @param sSection
	 * @param sProperty
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.12.2021, 07:30:39
	 */
	public String getSectionUsedForPropertyBySystemKey(String sSystemKeyAsSection, String sProperty) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = KernelFileIniZZZ.getSectionUsedForPropertyBySystemKey(this, sSystemKeyAsSection, sProperty);
			
//            if(StringZZZ.isEmpty(sSection))break main;
//            if(StringZZZ.isEmpty(sProperty))break main;
			
//			//0. Erst einmal feststellen ob wir in der Haupt- bzw. Eltersection starten oder in der SystemSection.
//			boolean bUseSystemKey=false;
//			String sValueOld=null;
//			String sSectionUsed = null;
//			boolean bSystemKey = KernelKernelZZZ.isSystemSection(sSection);
//			if(bSystemKey) {	
//				sSectionUsed = sSection;
//				
//				//A) Hole den Wert in der SystemKeySection
//				sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
//				if(StringZZZ.isEmpty(sValueOld)) {
//					
//					//Wurde in der PARENTSECTION der Wert überhaupt gesetzt?
//					sSectionUsed = KernelKernelZZZ.computeSectionFromSystemSection(sSection);
//					sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
//					if(StringZZZ.isEmpty(sValueOld)) {
//						bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
//					}else {
//						bUseSystemKey=false;
//					}
//				}else {
//					
//					bUseSystemKey=true;												
//				}
//				
//			}else {
//				//B) Fange mit dem SystemKey an
//				String sSystemNumber = this.getKernelObject().getSystemNumber();
//				sSectionUsed = KernelKernelZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);
//						
//				sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
//				if(StringZZZ.isEmpty(sValueOld)) {
//					bUseSystemKey=false;
//				}else {
//					
//					//Wurde dort der Wert gesetzt?
//					sSectionUsed = sSection;
//					sValueOld = this.objFileIni.getValue(sSectionUsed, sProperty);
//					if(StringZZZ.isEmpty(sValueOld)) {
//						bUseSystemKey=true; //also dort auch nicht gesetzt, dann nimm doch den Systemkey
//					}else {
//						bUseSystemKey=false;
//					}
//				}
//			}
//			
//			//+++ Nun festlegen, welche konkrete Section für diesen Wert verwendet werden soll
//			sSectionUsed=null;
//			if(bUseSystemKey) {
//				String sSystemNumber = this.getKernelObject().getSystemNumber();
//				sReturn = KernelKernelZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);					
//			}else {
//				sReturn = KernelKernelZZZ.computeSectionFromSystemSection(sSection);
//			}
		}//end main:
		return sReturn;
	}
	
	
	/**
	 * @param sSectionIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.04.2023, 07:49:08
	 */
	public boolean createSection(String sSectionIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
			String sSection = new String("");
			if(StringZZZ.isEmpty(sSectionIn)){
				ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'SectionName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}else{
				sSection = sSectionIn;
			}
			
			
			if(this.objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
			
			
			//#####
			this.objFileIni.createSubject(sSection);
			bReturn = true;
			
			//#####			
	} catch (IOException e) {
		ExceptionZZZ ez =	new ExceptionZZZ(	"IOException: '" + e.getMessage(),iERROR_RUNTIME,this,ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
		
		}//end main
		return bReturn;
	}// end function
	
	/** 
	 * removes as section and all of the property-entries of the section 
	
	 @author 0823 , date: 15.11.2004
	 @param sSectionIn, the Name of the section to remove
	 @return boolean, true, if everything was fine.
	 */
	public boolean deleteSection(String sSectionIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
			String sSection = new String("");
			if(StringZZZ.isEmpty(sSectionIn)){
				ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'SectionName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}else{
				sSection = sSectionIn;
			}
			
			
			if(this.objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ( "missing property 'FileIniObject'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
			
			
			//#####
			this.objFileIni.deleteSubject(sSection);
			bReturn = true;
			
			//#####			
	} catch (IOException e) {
		ExceptionZZZ ez =	new ExceptionZZZ(	"IOException: '" + e.getMessage(),iERROR_RUNTIME,this,ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
		
		}//end main
		return bReturn;
	}// end function
		
	/** boolean, removes a property in a section. If this is the last property of a section, the section entry will be removed too.
	* Lindhauer; 22.04.2006 14:15:56
	 * @param sSectionIn
	 * @param sPropertyIn
	 * @param bFlagSaveImmediate, true = save this change immediately on disk.
	 * @return true, this property does not exist any more (it might has never existed there).
	 * @throws ExceptionZZZ
	 */
	public boolean deleteProperty(String sSectionIn, String sPropertyIn, boolean bFlagSaveImmediate) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
						
			String sSection = new String("");			
			if(StringZZZ.isEmpty(sSectionIn)){				
				ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'SectionName'",iERROR_PARAMETER_MISSING, this,ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;				
			}else{
				sSection = sSectionIn;
			}
			String sProperty=new String("");
			if(StringZZZ.isEmpty(sPropertyIn)){
							ExceptionZZZ ez = new ExceptionZZZ("'PropertyName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
							throw ez;		
			}else{
				sProperty = sPropertyIn;
			}
			
			
			if(this.objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("'File Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;		
			}
			
			this.objFileIni.deleteValue(sSection, sProperty);
			if(bFlagSaveImmediate==true){
				this.objFileIni.saveFile();
			}else{
				this.setFlag("FileChanged", true);
				this.setFlag("FileUnsaved", true);
			}
			bReturn = true;    //Wenn das klappt, dann true setzen
			
			
			
			}catch(IOException e){
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(),iERROR_RUNTIME,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					
		}//end main:
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	@see zzzKernel.basic.KernelObjectZZZ#getFlag(java.lang.String)
	Flags used:<CR>
	- FileChanged
	- FileUnsaved
	- FileNew
	 */
	/*20170123: Soll herausgenommen werden k�nnen wg. ENUMERATION FlagZ Nutzung
	public boolean getFlag(String sFlagName){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
	
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("filechanged")){
				bFunction = bFlagFileChanged;
				break main;
			}else if(stemp.equals("fileunsaved")){
				bFunction = bFlagFileUnsaved;
				break main;
			}else if(stemp.equals("filenew")){
				bFunction = bFlagFileNew;
				break main;
			}else if(stemp.equals("useformula")){
				bFunction = bFlagUseFormula;
				break main;
			}
		}//end main:
		return bFunction;
	}
	*/

	/**
	 * @see AbstractKernelUseObjectZZZ.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * Flags used:<CR>
	 * - FileChanged, if a value is written to the file the flag is changed to true,
	 * - FileUnsaved, if a value is written to the file the flag is changed to true, if the save() method is used it is reset to false.
	 * 			  source_remove: after copying the source_files will be removed.
	 */
	/*20170123: Soll herausgenommen werden k�nnen wg. ENUMERATION FlagZ Nutzung
	public boolean setFlag(String sFlagName, boolean bFlagValue){
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.setFlag(sFlagName, bFlagValue);
			if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("fileunchanged")){
			bFlagFileChanged = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("fileunsaved")){
			bFlagFileUnsaved = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("filenew")){
			bFlagFileNew = bFlagValue;
			bFunction = true;
			break main;
		}else if(stemp.equals("useformula")){
			bFlagUseFormula = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}
	*/
	
	public boolean save() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try {
			
			boolean btemp;
			check:{
				if(this.objFileIni==null){
					ExceptionZZZ ez = new ExceptionZZZ( "'internal fileIni-Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}						 
			}//end check:
			
			//to avoid unnecessary write access to disk
			btemp = this.getFlag("FileUnsaved");
			if(btemp==true){
				
					this.objFileIni.saveFile();			
							bReturn = true;
							btemp = this.setFlag("FileUnsaved", false);				
			}

			} catch (IOException e) {
							ExceptionZZZ ez = new ExceptionZZZ( "IOException: '" + e.getMessage(),iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());						
							throw ez;
						}
		}//end main:
		
		return bReturn;
	}//end function
	
	public boolean save(String sFileNameIn) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				try {
			
			   String sFileName;
				check:{
					if(StringZZZ.isEmpty(sFileNameIn)){
						ExceptionZZZ ez = new ExceptionZZZ("'FileName'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;					
					}else{
						sFileName = sFileNameIn;
					}

				
					if(this.objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("'internal fileIni-Object'", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}						 
				}//end check:
			
				//if a filename is passed, no check of the flag is done, because it can be any filename
				this.objFileIni.saveFile(sFileName);			
				bReturn = true;
				

				} catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(),iERROR_RUNTIME,ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
							}
			}//end main:
		
			return bReturn;
		}//end function
	
	/** boolean, searches all sections of the ini-file for sSection. Returns true if there is a match.
	* Lindhauer; 19.05.2006 09:11:34
	 * @param sSection
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofSectionExistsDirectLookup(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)){
				String stemp = "'Section'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//Das Array aller Sections nach der einen Section durchsuchen
			String[] saSectionAll = this.getSectionAll();
			StringArrayZZZ saZZZ = new StringArrayZZZ(saSectionAll);
			bReturn = saZZZ.containsIgnoreCase(sSection);
					
		}//END main
		return bReturn;
	}
	
	public boolean proofSectionExistsSearched(String sSection) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSection)){
				String stemp = "'Section'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
		
			//1. Suche nach sSection PLUS Systemnumber
			String sSystemNumber = this.getKernelObject().getSystemNumber();
			String sSectionSearch  = AbstractKernelObjectZZZ.computeSystemSectionNameForSection(sSection, sSystemNumber);
			bReturn = this.proofSectionExistsDirectLookup(sSectionSearch);
			if(bReturn)break main;
			
			//2. Suche nach sSection pur.
			sSectionSearch  = sSection;
			bReturn = this.proofSectionExistsDirectLookup(sSectionSearch);
				
		}//END main
		return bReturn;				
	}
	
	/** boolean, searches all sections of the ini-file for sSection and Value. Returns true if there is a match.
	* Lindhauer; 12.02.2019 8:43
	 * @param sSection
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean proofValueExists(String sSection, String sValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(StringZZZ.isEmpty(sSection)){
					String stemp = "'Section'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
				if(this.objFileIni==null){
					String stemp =  "missing property 'FileIniObject'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
			}//END check:
		
		String stemp = this.objFileIni.getValue(sSection, sValue);
		if(stemp!=null){
			bReturn = true;
		}
		}//END main
		return bReturn;
	}
	
	
	//### Getter / Setter
	public File getFileObject() {
		return objFile;
	}
	/** 
	 @param file
	 */
	public void setFileObject(File objFile) throws ExceptionZZZ{		
		if(objFile!=null) {
			if(objFile.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ("File not found '" + objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez; 
			}
			if(objFile.isDirectory()){
				ExceptionZZZ ez = new ExceptionZZZ("Parameter File is a directory: '" + objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez; 
			}
		}
		this.objFile = objFile;
		this.setFlag("INIT", false);
		this.objFileIni = null; //wichtig. Dieses Objekt muss nach einem "umsetzen" neu initialisiert werden.
	}
		

	//### Aus IObjectWithExpression
	@Override
	public boolean isExpressionEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 	
	}
	
	//### aus IParseEntabledZZZ
	@Override
	public boolean isParserEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER);
	}
	
	@Override
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return this.isParserEnabledGeneral();
	}

	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		return true;
	}
	
	//### aus IKernelConfigSectionEntryUserZZZ
	@Override
	public IKernelConfigSectionEntryZZZ getEntryNew() throws ExceptionZZZ {
		IKernelExpressionIniHandlerZZZ objExpressionHandler = this.getExpressionHandler();
		return objExpressionHandler.getEntryNew(); //intern also explizit leer setzen, um etwas neues zu holen.
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ {
		IKernelExpressionIniHandlerZZZ objExpressionHandler = this.getExpressionHandler();
		return objExpressionHandler.getEntry();
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		IKernelExpressionIniHandlerZZZ objExpressionHandler = this.getExpressionHandler();
		objExpressionHandler.setEntry(objEntry);
	}
	
	//######## AUS Interface IKernelFileZZZ
	/** 
	 @date: 26.10.2004
	 @return FileObject
	 * @throws ExceptionZZZ 
	 */
	@Override
	public IniFile getFileIniObject() throws ExceptionZZZ {
		
		if(this.objFileIni==null) {
			try {
				File objFile = this.getFileObject();
				if(objFile==null){
					String stemp =  "missing property 'FileObject'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
				IniFile objFileIni = new IniFile(objFile.getPath(), false); //the target is not to save the file, everytime an entry is made, performance !!!
				this.objFileIni = objFileIni;
			}catch (IOException e){
		 		System.out.println(e.getMessage());			 				 		
			 }
		}		
		return this.objFileIni;
	}
	/** Merke: Private, damit die Erstellung über den Konstruktor nicht umgangen wird.
	 @param file
	 */
	private void setFileIniObject(IniFile objIniFile) {
		this.objFileIni = objIniFile;
	}

	//### Aus IValueVariableUserZZZ
	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable){
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable(){
		if(this.hmVariable==null){
			HashMapCaseInsensitiveZZZ<String,String> hmVariable = new HashMapCaseInsensitiveZZZ<String,String>();
			this.setHashMapVariable(hmVariable);		
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}	
	}
	
	/**Setze die Variable. 
	 *   Besonderheit: Man kann steueren ob Formeln, die diese Variable enthalten kurfristig aus dem Cache genommen werden (skip).
	 *                        Dadurch würden Sie erneut berechnet. 
	 *                        Merke: Das ist Z.B. im TileHexMap Projekt beim Herein-/Herauszoomen und der Berechnung der Seitenlänge des Hexfelds notwendig.
	 * @param sVariable
	 * @param sValue
	 * @param bUncacheFormula
	 * @throws ExceptionZZZ 
	 */
	//Nein, speziell nur dann, wenn ein Cache vorliegt @Override
	public void setVariable(String sVariable, String sValue, boolean bUncacheFormula) throws ExceptionZZZ{
		this.getHashMapVariable().put(sVariable, sValue);		
	
		if(bUncacheFormula){
			//20190817: Mit der Einführung des Cache für Ini - Einträge hat sich gezeigt, dass es notwendig ist, berechnete Ausdrücke nach Änderung der Variablen neu zu holen.
			//                Darum wird für die Ausdrücke, die diese Variablen enthalten, der Cache "geskipped".
			IKernelCacheZZZ objCache = this.getKernelObject().getCacheObject();		
			objCache.isCacheSkippedContainingVariable(true, sVariable);
		}
	}
	
	@Override
	public void setVariable(String sVariable, String sValue) throws ExceptionZZZ{
		this.setVariable(sVariable, sValue, false);
	}	
	
	@Override
	public String getVariable(String sVariable){
		return (String) this.getHashMapVariable().get(sVariable);
	}
	
	//### aus ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}

	
	//#####################################
	//#### aus ISolveEnabledZZZ
	//#####################################
	//In folgender konkreten Implementierung kann ueber das konkrete Flag des konkreten Solvers, dieser ein-/ausgeschaltet werden.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ{
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
	
	@Override
	public boolean isSolverEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
			
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		return true;
	}
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		return true;
	}
			
	@Override 
	public boolean isSolverEnabledEveryRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			
			bReturn = this.isSolverEnabledGeneral();
			if(!bReturn) break main;	
			
			//Auf File-Ebene wird nicht wie auf Solver Ebene differenziert.
			//Die Methode .getParentName() gibt es gar nicht.
			
			//"Elterntags" werden anders behandelt als "Kindtags"
//			if(StringZZZ.isEmpty(this.getParentName())){
//				bReturn = this.isSolverEnabledThis();
//				if(!bReturn) break main;
//			}else {
				bReturn = this.isSolverEnabledThis() && (this.isSolverEnabledAnyParentCustom() || this.isSolverEnabledAnyChildCustom()) ;
				if(!bReturn) break main;
//			}
						
		}//end main:
		return bReturn;
	}
	
	@Override 
	public boolean isSolverEnabledAnyRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			boolean bReturn1 = this.isSolverEnabledThis();						
			boolean bReturn2 = this.isSolverEnabledGeneral();
			boolean bReturn3 = this.isSolverEnabledAnyChildCustom();
			
			bReturn = bReturn1 | bReturn2 | bReturn3;
		}//end main:
		return bReturn;
	}

	@Override 
	public boolean isSolverEnabledByFlagZ(Enum objEnum) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objEnum==null) break main;
			
			bReturn = this.getFlag(objEnum.name());
		}//end main:
		return bReturn;
	}	
	
	
	//########################################
	//### aus ICachableObjectZZZ
	//########################################
	//Merke: KernelFileIniZZZ ist Cachable, damit z.B. Moduldateien behandelt werden können. u.a. verwendet in objKernel.searchModuleFileByModuleWithProgramSection(...)
	@Override
	public boolean isCacheSkipped() throws ExceptionZZZ {
		return this.bSkipCache;
	}

	@Override
	public void isCacheSkipped(boolean bSkip) throws ExceptionZZZ {
		if(!this.bSkipCache) this.bSkipCache = bSkip;
	}

	@Override
	public String getValueForFilter() throws ExceptionZZZ {
		//Bei KernelConfigSectionEntry ist es einfach der RAW String.
		//Hier setze ich den Dateinamen.
		//this.getFileIniObject().getFileName();
		return null;
	}

	@Override
	public boolean wasValueComputed() throws ExceptionZZZ {
		//Bei KernelConfigSectionEntry ist es einfach, dann ist das Objekt berechnet, wenn es eine Formel hat. 
		//Hier setzte ich einen statischen Wert.
		return false; 
	}
		
	
	//######################################
	//### Aus IKernelExpressionIniHandlerUserZZZ
	//######################################
	
	@Override 
	public IKernelExpressionIniHandlerZZZ getExpressionHandlerNew() throws ExceptionZZZ {		
		
		//Noch eine Ebene dazwischen eingebaut, da zusätzlich/alternativ zu den einfachen ZFormeln nun auch JSONArray / JSONMap konfigurierbar sind.
		KernelExpressionIniHandlerZZZ<T> exDummy = new KernelExpressionIniHandlerZZZ<T>();
		String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
		
		HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();				
		KernelExpressionIniHandlerZZZ<T> objExpressionHandler = new KernelExpressionIniHandlerZZZ<T>((FileIniZZZ<T>) this, hmVariable, saFlagZpassed);
		this.objExpressionHandler = objExpressionHandler;
			
		return this.objExpressionHandler;
	}
	
	@Override
	public IKernelExpressionIniHandlerZZZ getExpressionHandler() throws ExceptionZZZ {		
		if(this.objExpressionHandler==null) {									
			IKernelExpressionIniHandlerZZZ objExpressionHandler = this.getExpressionHandlerNew();
			this.objExpressionHandler = objExpressionHandler;
		}		
		return this.objExpressionHandler;
	}

	@Override
	public void setExpressionHandler(IKernelExpressionIniHandlerZZZ objExpressionHandler) throws ExceptionZZZ {
		this.objExpressionHandler = objExpressionHandler;
	}
	
	
	//#######################################
	//### ENTRY HANDLING
	//#######################################
	@Override
	public boolean adoptEntryValuesMissing(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceTarget = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceTarget.set(this.getEntry());
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSource = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceSource.set(objEntry);
		KernelConfigSectionEntryUtilZZZ.adoptEntryValuesMissing(objReturnReferenceTarget, objReturnReferenceSource);
		
		IKernelConfigSectionEntryZZZ objEntryTarget = objReturnReferenceTarget.get();
		this.setEntry(objEntryTarget);
		
		return true;
	}

	@Override
	public boolean resetEntry() throws ExceptionZZZ {
		return this.getEntry().reset();
	}
	
	

	//###################################################
	//### Flag Handling
	
	//### aus IListenerObjectFlagZsetZZZ
	@Override
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventFlagZset==null) break main;
			
			//Wenn das Objekt ueber die Aenderung des Setzen des Flags informiert wird. 
			//Dieses Setzen des Flags ggfs. nachvollziehen.
			String sFlagText = eventFlagZset.getFlagText();
			boolean bFlagValue = eventFlagZset.getFlagValue();
			
			try {
				bReturn = this.setFlag(sFlagText, bFlagValue);
			} catch (ExceptionZZZ e) {
				//Falls es das Flag hier nicht gibt, wird die Exception hier nicht weitergeworfen.
				//Es kann aber auch ggfs. anders verfahren werden. 
			}
			
		}//end main:
		return bReturn;
	}
	
	//### aus IKernelKernelFileIniZZZ
	@Override
	public boolean getFlag(IKernelFileIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelFileIniZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelFileIniZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelFileIniZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelFileIniZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelFileIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus Interface IIniTagWithConversionZZZ
	@Override
	public boolean getFlag(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IIniTagWithConversionZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IIniTagWithConversionZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IIniTagWithConversionZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IIniTagWithConversionZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	//### Aus Interface IObjectWithExpressionZZZ
	@Override
	public boolean getFlag(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IObjectWithExpressionZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IObjectWithExpressionZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IObjectWithExpressionZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IObjectWithExpressionZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IObjectWithExpressionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	

	//### Aus Interface IKernelExpressionIniParserZZZ	
	@Override
	public boolean getFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniParserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniParserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
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
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
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
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
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
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
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
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
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
	public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//###################################################
	//### FLAG: IListenerObjectFlagZsetZZZ
	//###################################################
	
	@Override
	public boolean getFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectFlagZsetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
