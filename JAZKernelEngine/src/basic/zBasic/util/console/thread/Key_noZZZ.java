package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.KernelConfigZZZ;
import basic.zKernel.KernelSingletonZZZ;

public class Key_noZZZ extends AbstractKeyPressCharZZZ{
	//Merke: Singleton Pattern kann nur in der gleichn Klasse gemacht werden, weil static Methoden nicht abstract sein können.
	protected static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
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
		    
	
	//Verwendung als Singleton
	private Key_noZZZ() {
		super();
	}
	
	public static  IKeyPressCharZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(IKeyPressCharZZZ.class) {
			if(objKey==null){
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objKey = getNewInstance();
			}
		}
		return objKey;	
	}
	
	public static IKeyPressCharZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		
		//Das hier nur zu initialisieren ist falsch. Schliesslich kennt man doch den Application-Key
		//String[] saFlagZ={"init"};
		//objKernelSingelton = new KernelSingletonZZZ(saFlagZ);	
		
		return objKey = new Key_noZZZ();
	}
	
	@Override
	public IKeyPressCharZZZ getKeyCharObject() {
		return this.objKey;
	}

	@Override
	public void setKeyObject(IKeyPressCharZZZ objKey) {
		this.objKey = objKey;
	}
	
	@Override
	public String getKeyText() {
		return "no";
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyNo;
	}

	@Override
	public char getKeyChar() {
		return Key_noZZZ.getKey();
	}
}
