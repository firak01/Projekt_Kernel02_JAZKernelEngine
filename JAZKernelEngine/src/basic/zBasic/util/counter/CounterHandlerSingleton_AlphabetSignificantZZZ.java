package basic.zBasic.util.counter;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
//import basic.zBasic.KernelSingletonVIA;
//import basic.zKernel.IKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;

public class CounterHandlerSingleton_AlphabetSignificantZZZ {
	//Merke: Singleton Pattern kann nur in der gleichn Klasse gemacht werden, weil static Methoden nicht abstract sein können.
	private static CounterHandlerSingleton_AlphabetSignificantZZZ objCounterSingleton = null; //muss static sein, wg. getInstance()!!!
	
	//##########################################################
	//Trick, um Mehrfachinstanzen zu verhindern (optional)
	//Warum das funktioniert:
	//initialized ist static → nur einmal pro ClassLoader
	//Wird beim ersten Konstruktoraufruf gesetzt
	//Jeder weitere Versuch (Reflection!) schlägt fehl
    private static boolean INITIALIZED = false;
    
    //Reflection-Schutz ist eine Hürde, kein Sicherheitsmechanismus.
    //Denn:
    //Field f = AbstractService.class.getDeclaredField("initialized");
    //f.setAccessible(true);
    //f.set(null, false);
    //Danach kann man wieder instanziieren.
	//##########################################################
	    
	
	private HashMap<String,ICounterAlphabetSignificantZZZ>hmCounter=new HashMap<String, ICounterAlphabetSignificantZZZ>();
	ICounterStrategyAlphabetSignificantZZZ objCounterStrategy;
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @param objFrame
	 * @throws ExceptionZZZ
	 */	
	private CounterHandlerSingleton_AlphabetSignificantZZZ(){
		super(); 
	}
	
	public static CounterHandlerSingleton_AlphabetSignificantZZZ getInstance() throws ExceptionZZZ{
		
		synchronized(CounterHandlerSingleton_AlphabetSignificantZZZ.class) {
			if(objCounterSingleton == null) {
				 // optional: Schutz vor Reflection
		        if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
		        objCounterSingleton = getNewInstance();
				INITIALIZED=true;
			}
		}
	
			//Merke: Hier ohne Kernel-Objekt arbeiten.
			//!!! Das Singleton Kernel Objekt holen und setzen !!!
//			KernelSingletonVIA objKernel = KernelSingletonVIA.getInstance();
//			objApplicationSingleton.setKernelObject(objKernel);			
		return objCounterSingleton;		
	}
	
	public static CounterHandlerSingleton_AlphabetSignificantZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objCounterSingleton = new CounterHandlerSingleton_AlphabetSignificantZZZ();
		return objCounterSingleton;
	}
	
	
	public ICounterAlphabetSignificantZZZ getCounterFor() throws ExceptionZZZ{
		ICounterAlphabetSignificantZZZ objCounter = null;
		main:{
			
			//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
			String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
						
			//Suche in der Hashmap nach einem String des Klassen.Methodennamens, der schon in der HashMap gespeichert ist.
			//Wird er gefunden, dann soll der Counter wiederverwendet werden.			
			HashMap<String,ICounterAlphabetSignificantZZZ> hmCounter = this.getCounterHashMap();
			for(String sCallingTemp : saCalling){
				objCounter = hmCounter.get(sCallingTemp);
				if(objCounter!=null)break ;
			}
									
			//Wenn es solch einen String nicht gibt, erzeuge neuen Counter und speichere in Hashmap unter dem aktuellen Methodennamen.
			if(objCounter==null){
				CounterByCharacterAsciiFactoryZZZ objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
				
				ICounterStrategyAlphabetSignificantZZZ objCounterStrategy = this.getCounterStrategy();//Merke: Wenn die CounterStrategy neu erstellt wird, dann wird die ExterneInitialisierungsmethode über den StackTrace geholt.								
				int iStart = objCounterStrategy.getCounterStart();

				objCounter = objCounterFactory.createCounter(objCounterStrategy, iStart);
				
				//Ermittle die Aufrufende Methode. Alle gültig..
				//String sCalling = ReflectCodeKernelZZZ.getClassMethodExternalCallingString();
				//String sCalling = saCalling[saCalling.length-1]; //die vorherige Methode ist im StackTrace die mit dem höchsten Index
				//... aber das erscheint mir am sichersten...
				String sCalling = objCounterStrategy.getClassMethodCallingString();
				this.setCounterFor(sCalling, objCounter);
			}
		}
		return objCounter;
	}
	
	public void setCounterFor(String sCalling, ICounterAlphabetSignificantZZZ objCounter){
		this.getCounterHashMap().put(sCalling, objCounter);
	}
	
	private ICounterAlphabetSignificantZZZ getCounterFor(String sKey){
		ICounterAlphabetSignificantZZZ objCounter = null;
		main:{
			
		}
		return objCounter;
	}
	
	private HashMap<String,ICounterAlphabetSignificantZZZ> getCounterHashMap(){
		return this.hmCounter;
	}
		
	public ICounterStrategyAlphabetSignificantZZZ getCounterStrategy() throws ExceptionZZZ{
		ICounterStrategyAlphabetSignificantZZZ objReturn = null;
		main:{
			objReturn = this.objCounterStrategy;
			if(objReturn==null){
				objReturn = new CounterStrategyAlphabetSignificantZZZ();
				
				//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
				String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
				//wichtig: Momentan ist die getCounterStrategy() Methode die Initialisierungsmethode.  Diese aber überschreiben..
				objReturn.setClassMethodCallingString(saCalling[saCalling.length-1]);//die vorherige Methode ist im StackTrace die mit dem höchsten Index
				
			}
		}//end main:
		return objReturn;
	}
	public void setCounterStrategy(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
	

}
