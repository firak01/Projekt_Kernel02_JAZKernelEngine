package basic.zUtil.io;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZCustomEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;


/**
This class extends File and not ObjectZZZ !!!
==> it inherits the methods of file and can also be used as input for printwriter/reader - objects !!!
==> it implements AssetObjectZZZ for throwing ExcetptionZZZ
==> it doesn�t implement assetKernelZZZ, because this class is used to create the log-file of the kernel.
         I never implement any functionality in an object which is used for this functionality !!!

TODO Einige static Methoden an basic.zBasic.Util.file.FileEasyZZZ abgeben  
 * @author Lindhauer
 */
public class FileZZZ extends File implements IConstantZZZ, IObjectZZZ, IFlagZEnabledZZZ, IFlagZCustomEnabledZZZ{ //IFlagZLocalEnabledZZZ, 
	private static final long serialVersionUID = 2355847392852232484L;
	
	protected String sDirectoryPath = new String("");
	protected String sFileName = new String(""); // Der ganze Dateiname, also: FileNameOnly + "." + Ending
	
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>();
	private HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>(); 
	private HashMap<String, Boolean>hmFlagCustom = new HashMap<String, Boolean>();
	
//	### Constructor ##########################
	public FileZZZ() throws ExceptionZZZ{
		this("","","init");
	}
	
	public FileZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);
		String sDirectoryPath = FileEasyZZZ.getParent(sFilePathTotal);
		String sFileName = FileEasyZZZ.getNameFromFilepath(sFilePathTotal);
		FileNew_(sDirectoryPath, sFileName, (String[])null);
	}
	
	public FileZZZ(String sDirectoryPath, String sFileName) throws ExceptionZZZ{
		super(sDirectoryPath + IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR_WINDOWS + sFileName);
		FileNew_(sDirectoryPath, sFileName, (String[])null);
	}
	
	public FileZZZ(String sDirectoryPath, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		FileNew_(sDirectoryPath, sFileName, saFlagControlIn);
	}
	
	
	public FileZZZ(String sDirectoryPath, String sFileName, String sFlagControl) throws ExceptionZZZ{
		super(sDirectoryPath + "\\" + sFileName);
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;
		FileNew_(sDirectoryPath, sFileName, saFlagControl);		 
	}

	private void FileNew_(String sDirectoryPath, String sFileName, String[] saFlagControl) throws ExceptionZZZ {
			main:{
				if(saFlagControl!=null){
					boolean btemp = false;
					for(int icount=0;icount <= saFlagControl.length-1;icount++){
						String stemp = saFlagControl[icount];
						btemp = this.setFlag(stemp, true);
						
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ( IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				this.setPathDirectory(sDirectoryPath);
				this.setName(sFileName);							
		}//End main:
		
	}

	//########################################
	//### GETTTER / SETTER
	//++++++++++++++++++++++++++++++++++++++++++
	
	public String getPathDirectory(){
		//Merke: hierfür gibt es keine Berechnung. Dies wird konstant gesetzt.
		return this.sDirectoryPath;			
	}
	
	public void setPathDirectory(String sToSet){
		this.sDirectoryPath = sToSet;
	}
	
	public String getName() {
		return this.sFileName;
	}
	public void setName(String sFileName) {
		this.sFileName = sFileName;
	}
	
	//### Acessor - Methods ###################
	//+++++++++++++++++++++++++++++++++++++++++
	public String getNameEnd(){	
		return FileEasyZZZ.NameEndCompute(this.getName());
	}
	
	public String getNameWithChangedEnd(String sEnd) throws ExceptionZZZ{
		String sFileName = this.getName();
		return FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);	
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getNameOnly() throws ExceptionZZZ{		
		return FileEasyZZZ.NameOnlyCompute(this.getName());	
	}
	

	//### Functions #########################	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	
	
	//Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}

	
	//###################################################
	//### FLAG HANDLING #################################
	//###################################################
	
	//### aus IFlagEnabledZZZ
	@Override
	public int adoptFlagZrelevantFrom(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
		int iReturn = 0;		
		main:{
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objUsingFlagZ);
			if(StringArrayZZZ.isEmpty(saFlagZpassed)) break main;
			
			for(String sFlag: saFlagZpassed) {
				boolean bValue = objUsingFlagZ.getFlag(sFlag);
				boolean btemp = this.setFlag(sFlag, bValue);
				if(btemp) iReturn++;
			}
		}//end main:
		return iReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.flag.IFlagZEnabledZZZ#adoptFlagZrelevantFrom(basic.zKernel.flag.IFlagZEnabledZZZ, boolean)
	 */
	@Override
	public int adoptFlagZrelevantFrom(IFlagZEnabledZZZ objUsingFlagZ, boolean bValueToSearchFor) throws ExceptionZZZ{
		int iReturn = 0;		
		main:{
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objUsingFlagZ, bValueToSearchFor);
			if(StringArrayZZZ.isEmpty(saFlagZpassed)) break main;
			
			for(String sFlag: saFlagZpassed) {
				boolean btemp = this.setFlag(sFlag, bValueToSearchFor);
				if(btemp) iReturn++;
			}
		}//end main:
		return iReturn;
	}
	

	//### Aus IFlagUserZZZ
	@Override
	public boolean getFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IFlagZEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}	
	
	@Override
	public boolean[] setFlag(IFlagZEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFlagZEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IFlagZEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
			
	@Override
	public boolean[] setFlag(String[] saFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saFlagName)) {
				baReturn = new boolean[saFlagName.length];
				int iCounter=-1;
				for(String sFlagName:saFlagName) {
					iCounter++;
					boolean bReturn = this.setFlag(sFlagName, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen muessen auch public deklariert werden.(non-Javadoc)
	 */
	public boolean getFlag(String sFlagName) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
			Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weteire Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
		
	@Override
	public HashMap<String, Boolean>getHashMapFlag(){
		return this.hmFlag;
	}
	
	@Override
	public void setHashMapFlag(HashMap<String, Boolean> hmFlag) {
		this.hmFlag = hmFlag;
	}
		
	@Override
	public HashMap<String, Boolean> getHashMapFlagPassed() {
		return this.hmFlagPassed;
	}
	@Override
	public void setHashMapFlagPassed(HashMap<String, Boolean> hmFlagPassed) {
		this.hmFlagPassed = hmFlagPassed;
	}
	
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZ(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZ(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZ_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZ_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlag();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZ();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlag(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
				
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
		 * 	 Weitere Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
		 */
		public boolean getFlagCustom(String sFlagName) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) break main;
											
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagCustom();
				Boolean objBoolean = hmFlag.get(sFlagName.toUpperCase());
				if(objBoolean==null){
					bFunction = false;
				}else{
					bFunction = objBoolean.booleanValue();
				}
								
			}	// end main:
			
			return bFunction;	
		}
		
		/** DIESE METHODEN MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
		 * Weitere Voraussetzungen:
		 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
		 * - Innere Klassen müssen auch public deklariert werden.
		 * @param objClassParent
		 * @param sFlagName
		 * @param bFlagValue
		 * @return
		 * lindhaueradmin, 23.07.2013
		 */
		public boolean setFlagCustom(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
			boolean bFunction = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName)) {
					bFunction = true;
					break main;
				}
							
				bFunction = this.proofFlagCustomExists(sFlagName);															
				if(bFunction == true){
					
					//Setze das Flag nun in die HashMap
					HashMap<String, Boolean> hmFlag = this.getHashMapFlagCustom();
					hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
					bFunction = true;								
				}										
			}	// end main:
			
			return bFunction;	
		}
		
		@Override
		public boolean[] setFlagCustom(String[] saFlag, boolean bValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!StringArrayZZZ.isEmptyTrimmed(saFlag)) {
					baReturn = new boolean[saFlag.length];
					int iCounter=-1;
					for(String sFlagName:saFlag) {
						iCounter++;
						boolean bReturn = this.setFlagCustom(sFlagName, bValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
			
		@Override
		public HashMap<String, Boolean>getHashMapFlagCustom(){
			return this.hmFlagCustom;
		}
		
		@Override
		public void setHashMapFlagCustom(HashMap<String, Boolean> hmFlagCustom) {
			this.hmFlagCustom = hmFlagCustom;
		}
		
		/**Gibt alle möglichen FlagZ Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZCustom() throws ExceptionZZZ{
			String[] saReturn = null;
			main:{	
				saReturn = FlagZHelperZZZ.getFlagsZDirectAvailable(this.getClass());				
			}//end main:
			return saReturn;
		}
	
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZCustom(boolean bValueToSearchFor) throws ExceptionZZZ{
			return this.getFlagZCustom_(bValueToSearchFor, false);
		}
		
		public String[] getFlagZCustom(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			return this.getFlagZCustom_(bValueToSearchFor, bLookupExplizitInHashMap);
		}
		
		private String[]getFlagZCustom_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				ArrayList<String>listasTemp=new ArrayList<String>();
				
				//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
				//                                  Diese kann man nur durch Einzelprüfung ermitteln.
				if(bLookupExplizitInHashMap) {
					HashMap<String,Boolean>hmFlag=this.getHashMapFlagCustom();
					if(hmFlag==null) break main;
					
					Set<String> setKey = hmFlag.keySet();
					for(String sKey : setKey){
						boolean btemp = hmFlag.get(sKey);
						if(btemp==bValueToSearchFor){
							listasTemp.add(sKey);
						}
					}
				}else {
					//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
					String[]saFlagZ = this.getFlagZCustom();
					
					//20211201:
					//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
					//         auch wenn sie garnicht gesetzt wurden.
					//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
					for(String sFlagZ : saFlagZ){
						boolean btemp = this.getFlagCustom(sFlagZ);
						if(btemp==bValueToSearchFor ){ //also 'true'
							listasTemp.add(sFlagZ);
						}
					}
				}
				saReturn = listasTemp.toArray(new String[listasTemp.size()]);
			}//end main:
			return saReturn;
		}
		
		/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
		 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
		 * @param name 
		 * @param sFlagName
		 * @return
		 * lindhaueradmin, 23.07.2013
		 * @throws ExceptionZZZ 
		 */
		public boolean proofFlagCustomExists(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZDirectExists(this.getClass(), sFlagName);				
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean proofFlagCustomSetBefore(String sFlagName) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(StringZZZ.isEmpty(sFlagName))break main;
				bReturn = FlagZHelperZZZ.proofFlagZLocalSetBefore(this, sFlagName);
			}
			return bReturn;
		}
		
		@Override
		public boolean resetFlagsCustom() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				HashMap<String,Boolean> hm = this.getHashMapFlagCustom();
				if(hm.isEmpty())break main;
				
				ReferenceHashMapZZZ<String,Boolean>objhmReturn=new ReferenceHashMapZZZ<String,Boolean>();
				objhmReturn.set(hm);
				
				bReturn =FlagZHelperZZZ.resetFlags(objhmReturn); 			
			}//end main:
			return bReturn;
		}
		
			
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
		}
		
		public String[] getFlagZ_passable(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(bValueToSearchFor, bLookupExplizitInHashMap, objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ(bValueToSearchFor,bLookupExplizitInHashMap);
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				//Nun nur die Schnittmenge der beiden StringÄrrays holen.				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
		/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
		 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
		 *    
		 * @return
		 * @throws ExceptionZZZ 
		 */
		public String[] getFlagZ_passable(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
			return this.getFlagZ_passable_(objUsingFlagZ);
		}
		
		private String[] getFlagZ_passable_(IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
			String[] saReturn = null;
			main:{
				
				//1. Hole alle FlagZ, DIESER Klasse, mit dem gewünschten Wert.
				String[] saFlag = this.getFlagZ();
				
				//2. Hole alle FlagZ der Zielklasse
				String[] saFlagTarget = objUsingFlagZ.getFlagZ();
				
				ArrayList<String>listasFlagPassable=new ArrayList<String>();
				//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
				
				saReturn = StringArrayZZZ.intersect(saFlag, saFlagTarget);
			}//end main:
			return saReturn;
		}
		
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public boolean proofFlagExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZExists(this.getClass(), sFlagName);				
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofFlagSetBefore(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZSetBefore(this, sFlagName);
		}
		return bReturn;
	}

	@Override
	public boolean resetFlags() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			HashMap<String,Boolean> hm = this.getHashMapFlag();
			if(hm.isEmpty())break main;
			
			ReferenceHashMapZZZ<String,Boolean>objhmReturn=new ReferenceHashMapZZZ<String,Boolean>();
			objhmReturn.set(hm);
			
			bReturn =FlagZHelperZZZ.resetFlags(objhmReturn); 			
		}//end main:
		return bReturn;
	}
	
	
	//###################################
	//### FLAGLOCAL Handling
	/*ES GIBT HIER KEIN FLAG LOCAL
	@Override
	public boolean getFlagLocal(IFlagZEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());		
	}

	@Override
	public boolean setFlagLocal(IJgitResolverEnabled.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IJgitResolverEnabled.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IJgitResolverEnabled.FLAGZLOCAL objEnum_IJgitResolverEnabled:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnum_IJgitResolverEnabled, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IJgitResolverEnabled.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IJgitResolverEnabled.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalSetBefore(objEnumFlag.name());
	}

	//###################################
	//### FLAGCUSTOM Handling
		
	@Override
	public boolean getFlagCustom(IJgitResolverEnabled.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());		
	}

	@Override
	public boolean setFlagCustom(IJgitResolverEnabled.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(IJgitResolverEnabled.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IJgitResolverEnabled.FLAGZCUSTOM objEnum_IJgitResolverEnabled:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnum_IJgitResolverEnabled, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(IJgitResolverEnabled.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(IJgitResolverEnabled.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}
	*/
}//end class
