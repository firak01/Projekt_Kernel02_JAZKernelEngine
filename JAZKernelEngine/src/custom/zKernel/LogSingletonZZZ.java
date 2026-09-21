package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.IKernelConfigZZZ;

public class LogSingletonZZZ extends AbstractKernelLogZZZ{
	private static final long serialVersionUID = 1L;
	//Merke: Singleton Pattern kann nur in der gleichn Klasse gemacht werden, weil static Methoden nicht abstract sein können.
	private static LogSingletonZZZ objLogSingleton; //muss als Singleton static sein	
	
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
	
  //########################################################################
  	//Die Konstruktoren nun verbergen, wg. Singleton
  	private LogSingletonZZZ() throws ExceptionZZZ{
  		super();
  	}
  	
  	private LogSingletonZZZ(String sDirectoryPath, String sLogFile) throws ExceptionZZZ {
  		super(sDirectoryPath, sLogFile);
  	}
  	
  	private LogSingletonZZZ(String sDirectoryPath, String sLogFile, String sFlagControl) throws ExceptionZZZ {
  		super(sDirectoryPath, sFlagControl);
  	}
  	
  	private LogSingletonZZZ(String sDirectoryPath, String sLogFile, String[] saFlagControl) throws ExceptionZZZ {
  		super(sDirectoryPath, sLogFile, saFlagControl);
  	}	
  	
  	private LogSingletonZZZ(IKernelConfigZZZ objConfig) throws ExceptionZZZ{
  		super(objConfig);
  	}
  	
  	//#############################################################################
	public static LogSingletonZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objLogSingleton = getNewInstance(null, null, (String[]) null);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
		
	public static LogSingletonZZZ getInstance(String sFlagControl) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				String[] saFlagControl= new String[1];
				saFlagControl[0]=sFlagControl;
	
				objLogSingleton = getNewInstance(null, null, saFlagControl);//new LogSingletonZZZ(null, null, saFlagControl);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
	
	public static LogSingletonZZZ getInstance(String... sFlags) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }	
				objLogSingleton = getNewInstance(null, null, sFlags);//new LogSingletonZZZ(null, null, sFlags);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
	
	public static LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objLogSingleton = getNewInstance(sDirectoryPath, sLogFile, (String[]) null);//new LogSingletonZZZ(sDirectoryPath, sLogFile, (String[]) null);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
	
	public static LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile, String sFlag) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				
				String[] saFlag = new String[1];
				saFlag[0] = sFlag;
				objLogSingleton = getNewInstance(sDirectoryPath, sLogFile, sFlag);//new LogSingletonZZZ(sDirectoryPath, sLogFile, sFlag);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
	
	
	public static  LogSingletonZZZ getInstance(String sDirectoryPath, String sLogFile, String... sFlags) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(LogSingletonZZZ.class) {
			if(objLogSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objLogSingleton = getNewInstance(sDirectoryPath, sLogFile, sFlags);//new LogSingletonZZZ(sDirectoryPath, sLogFile, sFlags);
				INITIALIZED=true;
			}
		}
		return objLogSingleton;	
	}
	
	//########################
	public static LogSingletonZZZ getNewInstance(String sDirectoryPath, String sLogFile, String... sFlags) throws ExceptionZZZ{
		//Das hier nur zu initialisieren ist falsch.
		//String[] saFlagZ={"init"};
		//objKernelSingelton = new KernelSingletonZZZ(saFlagZ);	

		return new LogSingletonZZZ(sDirectoryPath, sLogFile, sFlags);
	}
}
