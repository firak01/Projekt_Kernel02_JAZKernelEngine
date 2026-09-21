package basic.zBasic.util.counter;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeKernelZZZ;
//import basic.zBasic.KernelSingletonVIA;
//import basic.zKernel.IKernelZZZ;
import basic.zBasic.ReflectCodeZZZ;

public class CounterHandlerSingleton_AlphabetZZZ {
	//Merke: Singleton Pattern kann nur in der gleichn Klasse gemacht werden, weil static Methoden nicht abstract sein können.
	private static CounterHandlerSingleton_AlphabetZZZ objCounterSingleton = null; //muss static sein, wg. getInstance()!!!
	
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

	
	private HashMap<String,ICounterAlphabetZZZ>hmCounter=new HashMap<String, ICounterAlphabetZZZ>();
	ICounterStrategyAlphabetZZZ objCounterStrategy;
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @param objFrame
	 * @throws ExceptionZZZ
	 */	
	private CounterHandlerSingleton_AlphabetZZZ(){
		super(); 
	}
	
	public static CounterHandlerSingleton_AlphabetZZZ getInstance() throws ExceptionZZZ{
		
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(CounterHandlerSingleton_AlphabetZZZ.class) {
			if(objCounterSingleton == null) {

				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objCounterSingleton = getNewInstance();
				INITIALIZED=true;
		
			}
			//Merke: Hier ohne Kernel-Objekt arbeiten.
			//!!! Das Singleton Kernel Objekt holen und setzen !!!
//			KernelSingletonVIA objKernel = KernelSingletonVIA.getInstance();
//			objApplicationSingleton.setKernelObject(objKernel);			
		}
		return objCounterSingleton;		
	}
	
	public static CounterHandlerSingleton_AlphabetZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new CounterHandlerSingleton_AlphabetZZZ();
	}
	
	public ICounterAlphabetZZZ getCounterFor() throws ExceptionZZZ{
		ICounterAlphabetZZZ objCounter = null;
		main:{
			
			//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
			String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
						
			//Suche in der Hashmap nach einem String des Klassen.Methodennamens, der schon in der HashMap gespeichert ist.
			//Wird er gefunden, dann soll der Counter wiederverwendet werden.			
			HashMap<String,ICounterAlphabetZZZ> hmCounter = this.getCounterHashMap();
			for(String sCallingTemp : saCalling){
				objCounter = hmCounter.get(sCallingTemp);
				if(objCounter!=null)break ;
			}
									
			//Wenn es solch einen String nicht gibt, erzeuge neuen Counter und speichere in Hashmap unter dem aktuellen Methodennamen.
			if(objCounter==null){
				CounterByCharacterAsciiFactoryZZZ objCounterFactory = CounterByCharacterAsciiFactoryZZZ.getInstance();
				
				ICounterStrategyAlphabetZZZ objCounterStrategy = this.getCounterStrategy();//Merke: Wenn die CounterStrategy neu erstellt wird, dann wird die ExterneInitialisierungsmethode über den StackTrace geholt.								
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
	
	public void setCounterFor(String sCalling, ICounterAlphabetZZZ objCounter){
		this.getCounterHashMap().put(sCalling, objCounter);
	}
	
	private ICounterAlphabetZZZ getCounterFor(String sKey){
		ICounterAlphabetZZZ objCounter = null;
		main:{
			
		}
		return objCounter;
	}
	
	private HashMap<String,ICounterAlphabetZZZ> getCounterHashMap(){
		return this.hmCounter;
	}
		
	public ICounterStrategyAlphabetZZZ getCounterStrategy() throws ExceptionZZZ{
		ICounterStrategyAlphabetZZZ objReturn = null;
		main:{
			objReturn = this.objCounterStrategy;
			if(objReturn==null){
				objReturn = new CounterStrategyAlphabetSerialZZZ();
				
				//Ermittle die aktuelle Stacktraceposition und dann jeweils die aufrufende Methode.
				String[] saCalling = ReflectCodeKernelZZZ.getCallingStackExternal();
				//wichtig: Momentan ist die getCounterStrategy() Methode die Initialisierungsmethode.  Diese aber überschreiben..
				objReturn.setClassMethodCallingString(saCalling[saCalling.length-1]);//die vorherige Methode ist im StackTrace die mit dem höchsten Index
				
			}
		}//end main:
		return objReturn;
	}
	public void setCounterStrategy(ICounterStrategyAlphabetZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;
	}
	

}
