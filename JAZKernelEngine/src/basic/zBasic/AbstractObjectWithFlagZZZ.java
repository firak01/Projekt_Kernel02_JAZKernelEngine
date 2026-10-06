package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.flag.IFlagZCustomEnabledZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.IFlagZLocalEnabledZZZ;
import basic.zKernel.flag.event.EventObjectFlagZsetZZZ;
import basic.zKernel.flag.event.IEventBrokerFlagZsetUserZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.event.ISenderObjectFlagZsetZZZ;
import basic.zKernel.flag.event.KernelSenderObjectFlagZsetZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;

public abstract class AbstractObjectWithFlagZZZ<T> extends AbstractObjectWithExceptionZZZ<Object> implements IFlagZEnabledZZZ, IEventBrokerFlagZsetUserZZZ, IFlagZCustomEnabledZZZ, IFlagZLocalEnabledZZZ{
	private static final long serialVersionUID = 1L;

	/**20130721: Erweitert um HashMap und die Enum-Flags, Compiler auf 1.6 geändert
	 * 
	 */
//Flags, die alle Z-Objekte haben
//	public enum FLAGZ{
//		DEBUG, INIT; //Verschoben nach IFlagZZZ, weil nicht alle Klassen von ObjectZZZ erben können (weil sie schon von einer anderen Klasse erben).
//	}
	
	protected volatile HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721       //Normale Flags. Werden über Vererbungsinstanzen eingesammelt.
	protected volatile HashMap<String, Boolean>hmFlagPassed = new HashMap<String, Boolean>(); //Neu 20210402 //Flags, die von anderen Objekten übergeben worden sind.
	protected HashMap<String, Boolean>hmFlagCustom = new HashMap<String, Boolean>(); //Neu 20260419          //Wie normale Flags, nur anwendungspezifische. Werden über Vererbungsinstanzen eingesammelt. 
	protected HashMap<String, Boolean>hmFlagLocal = new HashMap<String, Boolean>(); //Neu 20220720           //Wie normale Flags, nur anwendungspezifische. Werden NICHT über Vererbungsinstanzen eingesammelt.
		
	
	protected ISenderObjectFlagZsetZZZ objEventFlagZBroker = null;//Das Broker Objekt, an dem sich andere Objekte regristrieren können, um ueber Aenderung eines Flags per Event informiert zu werden.
	
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	
	public AbstractObjectWithFlagZZZ() {	
		super();		
	}
	public AbstractObjectWithFlagZZZ(String sFlag) throws ExceptionZZZ {
		super();
		if(!StringZZZ.isEmpty(sFlag)) this.setFlag(sFlag, true);
	}
	public AbstractObjectWithFlagZZZ(String[] saFlag) throws ExceptionZZZ {
		super();
		if(saFlag!=null){
			if(saFlag.length>=1){
				String sLog;
				for(int icount =0; icount <= saFlag.length-1; icount++){
					if(!StringZZZ.isEmpty(saFlag[icount])){						
						boolean bFound = this.setFlag(saFlag[icount], true);
						if(!bFound) {
							sLog = ReflectCodeZZZ.getPositionCurrent()+"Flag not available: '" + saFlag[icount] +"'";
							this.protocol(sLog);							
						}
					}
				}
			}
		}
	}
	public AbstractObjectWithFlagZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super();
		//Die ggf. vorhandenen Flags setzen.
		if(hmFlag!=null){
			for(String sKey:hmFlag.keySet()){
				String stemp = sKey;
				boolean btemp = this.setFlag(sKey, hmFlag.get(sKey));
				if(btemp==false){
					ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available (passed by hashmap).", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
			}
		}
	}
	
		//Meine Variante Objekte zu clonen.
	    //Merke: Einfaches Clonen erzeugt einen "Shallow Copy", 
	    //       Damit sich aber Flag-Aenderungen in einer Kopie nicht auf Flags des Originals auswirken.
	    //       Auch eine Kopie der HashMaps machen
		@Override
		public Object clonez() throws ExceptionZZZ {
			AbstractObjectWithFlagZZZ<T> obj = null;
			main:{
//				try {					
					obj = (AbstractObjectWithFlagZZZ<T>) super.clonez();
										
					HashMap<String, Boolean> hmFlag = HashMapUtilZZZ.copy(obj.hmFlag);
					obj.hmFlag = hmFlag;
					
					HashMap<String, Boolean> hmFlagLocal = HashMapUtilZZZ.copy(obj.hmFlagLocal);
					obj.hmFlagLocal = hmFlagLocal;
					
					HashMap<String, Boolean> hmFlagPassed = HashMapUtilZZZ.copy(obj.hmFlagPassed);
					obj.hmFlagPassed = hmFlagPassed;
					
//				}catch(CloneNotSupportedException e) {
//					ExceptionZZZ ez = new ExceptionZZZ(e);
//					throw ez;						
//				}
			}//end main:
			return (Object) obj;
		}
	
	//###############################################
	//### FLAG HANDLING
	//###############################################
	
	//### Aus IFlagZEnabledZZZ
	@Override
	public boolean getFlag(Enum enumFlag) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumFlag==null) {
				break main;
			}
			
			String sFlagName = enumFlag.name();
			bFunction = this.getFlag(sFlagName);							
		}	// end main:
		
		return bFunction;
	}
	
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
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
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
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
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
	@Override
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
	@Override
	public String[] getFlagZ(boolean bValueToSearchFor) throws ExceptionZZZ{
		return this.getFlagZ_(bValueToSearchFor, false);
	}
		
	@Override
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
		
	/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück, die auch als FLAGZ in dem anderen Objekt überhaupt vorhanden sind.
	 *  Merke: Diese Methode ist dazu gedacht FlagZ-Werte von einem Objekt auf ein anderes zu übertragen.	
	 *    
	 * @return
	 * @throws ExceptionZZZ 
	 */
	@Override
	public String[] getFlagZ_passable(boolean bValueToSearchFor, IFlagZEnabledZZZ objUsingFlagZ) throws ExceptionZZZ{
		return this.getFlagZ_passable_(bValueToSearchFor, false, objUsingFlagZ);
	}
	
	@Override
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
			
			//Nun nur die Schnittmenge der beiden StringÄrrays hiolen.
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
	@Override
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
		
		
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung -, DIE IHRE FLAGS SETZEN WOLLEN
	 * Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen müssen auch public deklariert werden.
	 * @param objClassParent
	 * @param sFlagName
	 * @param bFlagValue
	 * @return
	 * lindhaueradmin, 23.07.2013
	 */
	@Override
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
				
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				if(this.objEventFlagZBroker!=null) {
					IEventObjectFlagZsetZZZ event = new EventObjectFlagZsetZZZ(this,1,sFlagName.toUpperCase(), bFlagValue);
					this.objEventFlagZBroker.fireEvent(event);
				}
				
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
	
	/** DIESE METHODE MUSS IN ALLEN KLASSEN VORHANDEN SEIN - über Vererbung ODER Interface Implementierung -, DIE IHRE FLAGS SETZEN WOLLEN
	 *  SIE WIRD PER METHOD.INVOKE(....) AUFGERUFEN.
	 * @param name 
	 * @param sFlagName
	 * @return
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	@Override
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
	
	//##################################################	
	//### CUSTOM FLAGS
	//##################################################
	/* @see basic.zBasic.IFlagZZZ#getFlagZ(java.lang.String)
	 * 	 Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen m�ssen auch public deklariert werden.(non-Javadoc)
	 */
	@Override
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
	@Override
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
				for(String sFlag:saFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(sFlag, bValue);
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
	@Override
	public String[] getFlagZCustom() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{	
			saReturn = FlagZHelperZZZ.getFlagsZCustom(this.getClass());				
		}//end main:
		return saReturn;
	}
		
	/**Gibt alle "true" gesetzten FlagZ - Werte als Array zurück. 
	 * @return
	 * @throws ExceptionZZZ 
	 */
	@Override
	public String[] getFlagZCustom(boolean bValueToSearchFor) throws ExceptionZZZ{
		return this.getFlagZCustom_(bValueToSearchFor, false);
	}
		
	@Override
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
	@Override
	public boolean proofFlagCustomExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			//bReturn = FlagZHelperZZZ.proofFlagZDirectExists(this.getClass(), sFlagName);				
			bReturn = FlagZHelperZZZ.proofFlagZCustomExists(this.getClass(), sFlagName);
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
	
	
	
	
				
	
	
	//##################################################	
	//### LOCAL FLAGS
	//##################################################
	/* @see basic.zBasic.IFlagZLocalEnabledZZZ#getFlagZ(java.lang.String)
	 * 	 Weitere Voraussetzungen:
	 * - Public Default Konstruktor der Klasse, damit die Klasse instanziiert werden kann.
	 * - Innere Klassen muessen auch public deklariert werden.(non-Javadoc)
	 */
	@Override
	public boolean getFlagLocal(String sFlagName) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
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
	@Override
	public boolean setFlagLocal(String sFlagName, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) {
				bFunction = true;
				break main;
			}
						
			bFunction = this.proofFlagLocalExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlagLocal();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;	
	}
			
	@Override
	public boolean[] setFlagLocal(String[] saFlag, boolean bValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saFlag)) {
				baReturn = new boolean[saFlag.length];
				int iCounter=-1;
				for(String sFlag:saFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(sFlag, bValue);
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
	public HashMap<String, Boolean>getHashMapFlagLocal(){
		return this.hmFlagLocal;
	}
	
	@Override
	public void setHashMapFlagLocal(HashMap<String, Boolean> hmFlagLocal) {
		this.hmFlagLocal = hmFlagLocal;
	}
	
	/**Gibt alle möglichen FlagZ Werte als Array zurück. 
	 * @return
	 * @throws ExceptionZZZ 
	 */
	@Override
	public String[] getFlagZLocal() throws ExceptionZZZ{
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
	@Override
	public String[] getFlagZLocal(boolean bValueToSearchFor) throws ExceptionZZZ{
		return this.getFlagZLocal_(bValueToSearchFor, false);
	}
		
	@Override
	public String[] getFlagZLocal(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
		return this.getFlagZLocal_(bValueToSearchFor, bLookupExplizitInHashMap);
	}
			
	private String[]getFlagZLocal_(boolean bValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			ArrayList<String>listasTemp=new ArrayList<String>();
			
			//FALLUNTERSCHEIDUNG: Alle gesetzten FlagZ werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
			//                                  Diese kann man nur durch Einzelprüfung ermitteln.
			if(bLookupExplizitInHashMap) {
				HashMap<String,Boolean>hmFlag=this.getHashMapFlagLocal();
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
				String[]saFlagZ = this.getFlagZLocal();
				
				//20211201:
				//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
				//         auch wenn sie garnicht gesetzt wurden.
				//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
				for(String sFlagZ : saFlagZ){
					boolean btemp = this.getFlagLocal(sFlagZ);
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
	@Override
	public boolean proofFlagLocalExists(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
							
			bReturn = FlagZHelperZZZ.proofFlagZLocalExists(this.getClass(), sFlagName);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofFlagLocalSetBefore(String sFlagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName))break main;
			bReturn = FlagZHelperZZZ.proofFlagZLocalSetBefore(this, sFlagName);
		}
		return bReturn;
	}
	
	@Override
	public boolean resetFlagsLocal() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			HashMap<String,Boolean> hm = this.getHashMapFlagLocal();
			if(hm.isEmpty())break main;
			
			ReferenceHashMapZZZ<String,Boolean>objhmReturn=new ReferenceHashMapZZZ<String,Boolean>();
			objhmReturn.set(hm);
			
			bReturn =FlagZHelperZZZ.resetFlags(objhmReturn); 			
		}//end main:
		return bReturn;
	}

	
	//##########################################################
	//+++ Benachrichtige über Veränderung der "normalen" Flags
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### Aus dem Interface IEventBrokerFlagZUserZZZ
	@Override
	public ISenderObjectFlagZsetZZZ getSenderFlagUsed() throws ExceptionZZZ {
		if(this.objEventFlagZBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender fuer Aenderungen an den Flags zu erstellen, der dann registrierte Objekte ueber Aenderung von Flags informiert
			ISenderObjectFlagZsetZZZ objSenderFlag = new KernelSenderObjectFlagZsetZZZ();
			this.objEventFlagZBroker = objSenderFlag;
		}
		return this.objEventFlagZBroker;
	}

	@Override
	public void setSenderFlagUsed(ISenderObjectFlagZsetZZZ objSenderFlag) {
		this.objEventFlagZBroker = objSenderFlag;
	}
	

	@Override
	public void registerForFlagEvent(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		main:{
			if(objEventListener==null)break main;
			if(objEventListener.getFlag("INIT")) break main; 
		
			this.getSenderFlagUsed().addListenerObjectFlagZset(objEventListener);
		}//end main:
	}
	
	
	@Override 
	public void registerForFlagEventAdopted(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		main:{
			if(objEventListener==null)break main;
			if(objEventListener.getFlag("INIT")) break main; 
			
			this.registerForFlagEvent(objEventListener);
		
			//Übernimm nun die schon gesetzten FLAG Werte
			//1. die Flags ermitteln
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objEventListener, true);
			if(StringArrayZZZ.isEmpty(saFlagZpassed)) break main;
		
			//2. die Flags setzen
			for(String sFlag : saFlagZpassed) {
				objEventListener.setFlag(sFlag, true);
			}
			objEventListener.adoptFlagZrelevantFrom(this, true);		
		}//end main
	}
	
	@Override
	public void unregisterForFlagEvent(IListenerObjectFlagZsetZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderFlagUsed().removeListenerObjectFlagZset(objEventListener);
	}
	
	
	@Override
	public boolean setFlag(Enum enumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumFlag==null) {
				break main;
			}
			
			String sFlagName = enumFlag.name();
			//Nein, trotz der Redundanz nicht machen, da nun der Event anders gefeuert wird, nämlich über das enum
			//bFunction = this.setFlag(sFlagName, bFlagValue);	
			
			bFunction = this.proofFlagExists(sFlagName);															
			if(bFunction == true){
				
				//Setze das Flag nun in die HashMap
				HashMap<String, Boolean> hmFlag = this.getHashMapFlag();
				hmFlag.put(sFlagName.toUpperCase(), bFlagValue);
			
				//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
				//Dann erzeuge den Event und feuer ihn ab.
				//Merke: Nun aber ueber das enum			
				if(this.objEventFlagZBroker!=null) {
					IEventObjectFlagZsetZZZ event = new EventObjectFlagZsetZZZ(this,1,enumFlag, bFlagValue);
					this.objEventFlagZBroker.fireEvent(event);
				}
				
				bFunction = true;								
			}										
		}	// end main:
		
		return bFunction;
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
	
	


	
}
