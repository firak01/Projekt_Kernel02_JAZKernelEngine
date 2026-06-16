package basic.zKernel;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.file.JarEasyUtilZZZ;
import basic.zKernel.file.ini.IIniTagWithExpressionZZZ;

public class KernelSingletonZZZ extends AbstractKernelObjectZZZ{
	private static final long serialVersionUID = 1L;
	private static KernelSingletonZZZ objKernelSingleton; //muss als Singleton static sein	
	
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
	
    //Die Konstruktoren nun verbergen, wg. Singleton
    private KernelSingletonZZZ() throws ExceptionZZZ{
		super();
	}
	
	private KernelSingletonZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super(saFlagControl);
	}
	
	private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		super(objConfig, sFlagControl);
	}
	
	private KernelSingletonZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
	}	
			
	private KernelSingletonZZZ(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		super(objConfig, saFlagControl);
	}
	
	private KernelSingletonZZZ(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String sFlagControl ) throws ExceptionZZZ{
		super(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, sFlagControl);
	}
	
	
	public static KernelSingletonZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelSingletonZZZ.class) {
			if(objKernelSingleton == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKernelSingleton = getNewInstance();
				INITIALIZED=true;
			}
		}
		return objKernelSingleton;	
	}
		
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelSingletonZZZ.class) {
			if(objKernelSingleton==null){
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKernelSingleton = getNewInstance(objConfig, sFlagControl);
			}
		}
		return objKernelSingleton;	
	}
	
	public static  KernelSingletonZZZ getInstance(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelSingletonZZZ.class) {
			if(objKernelSingleton==null){
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKernelSingleton = getNewInstance(objConfig, saFlagControl);
			}
		}
		return objKernelSingleton;	
	}
	
	public static KernelSingletonZZZ getInstance(String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelSingletonZZZ.class) {
			if(objKernelSingleton==null){
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKernelSingleton = getNewInstance(sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
			}
		}
		return objKernelSingleton;	
	}
	
	public static KernelSingletonZZZ getInstance(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelSingletonZZZ.class) {
			if(objKernelSingleton==null){
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKernelSingleton = getNewInstance(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);
			}
		}
		return objKernelSingleton;	
	}
	
	//####################################
	public static KernelSingletonZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		
		//Das hier nur zu initialisieren ist falsch. Schliesslich kennt man doch den Application-Key
		//String[] saFlagZ={"init"};
		//objKernelSingelton = new KernelSingletonZZZ(saFlagZ);	
		
		//Verwende hier Config-Objekt mit dem gleichen Suffix der Klasse, also ZZZ
		IKernelConfigZZZ objConfig = new KernelConfigZZZ();
		return objKernelSingleton = new KernelSingletonZZZ(objConfig, (String) null);
	}
	
	public static  KernelSingletonZZZ getNewInstance(IKernelConfigZZZ objConfig, String sFlagControl) throws ExceptionZZZ{
		return new KernelSingletonZZZ(objConfig, sFlagControl);
	}
	
	public static  KernelSingletonZZZ getNewInstance(IKernelConfigZZZ objConfig, String[] saFlagControl) throws ExceptionZZZ{
		return new KernelSingletonZZZ(objConfig, saFlagControl);
	}
	
	public static KernelSingletonZZZ getNewInstance(String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		//Verwende hier das Suffix der Klasse als Applicationkey, also ZZZ
		return new KernelSingletonZZZ("ZZZ", sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);	
	}
	
	public static KernelSingletonZZZ getNewInstance(String sApplicationKey, String sSystemNumber, String sFileConfigPath, String sFileConfigName, String[] saFlagControl ) throws ExceptionZZZ{
		return new KernelSingletonZZZ(sApplicationKey, sSystemNumber, sFileConfigPath, sFileConfigName, saFlagControl);	
	}
				
	//#################################
	public String getFileConfigKernelName() throws ExceptionZZZ{			
		return super.getFileConfigKernelName();
	}
	public String getApplicationKey() throws ExceptionZZZ{			
		return super.getApplicationKey();
	}
		
		


	//Aus IRessourceHandlingObject	
	//### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist. Also:
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
	
	/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
	 *   Merke: Static Klassen müssen diese Methode selbst implementieren. Das ist dann das Beispiel.
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ 
	 */
	public static boolean isInJarStatic() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JarEasyUtilZZZ.isInJar(KernelSingletonZZZ.class);
		}
		return bReturn;
	}

	//### Aus IExpressionEnabledZZZ
	@Override
	public boolean isExpressionEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 	
	}
}
