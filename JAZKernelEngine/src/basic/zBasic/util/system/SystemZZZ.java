package basic.zBasic.util.system;

import basic.zBasic.ExceptionZZZ;

/** Singleton Klasse, für alles was mit System. ... gemacht werden kann.
 *  Kann durch FLAGZ ggfs. gesteuert werden.
 * @author Fritz Lindhauer
 *
 */
public class SystemZZZ<T> extends AbstractSystemZZZ<T>{
	private static final long serialVersionUID = 2524968434491371812L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static ISystemZZZ objSystemINSTANCE=null;

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
	
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected SystemZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static synchronized ISystemZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(SystemZZZ.class) {
			if(objSystemINSTANCE == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objSystemINSTANCE = getNewInstance();
				INITIALIZED=true;
			}
		}
		return (ISystemZZZ) objSystemINSTANCE;
	}
	
	public static ISystemZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objSystemINSTANCE = new SystemZZZ();
		return (ISystemZZZ)objSystemINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objSystemINSTANCE = null;
	}

	
	//#################################################################
	//### ...... METHODEN ............
	//#################################################################
	
	
}
