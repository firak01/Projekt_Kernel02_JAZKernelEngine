package basic.zBasic.util.crypt.code;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

/** Idee: Die Kernel Version wird dann später ganze Konfigurationsdateien verschlüsseln, oder so....
 *  
 * 
 * @author Fritz Lindhauer, 02.10.2022, 08:50:03
 * 
 */
public class KernelCryptAlgorithmFactoryZZZ extends AbstractKernelUseObjectZZZ implements ICryptUserZZZ{

	private static KernelCryptAlgorithmFactoryZZZ objCryptAlgorithmFactory = null;  //muss static sein, wg. getInstance()!!!

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


	private ICryptZZZ objCryptAlgorithmCreatedLast = null;	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private KernelCryptAlgorithmFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private KernelCryptAlgorithmFactoryZZZ(){
		super();
	}
	
	public static KernelCryptAlgorithmFactoryZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelCryptAlgorithmFactoryZZZ.class) {
			if(objCryptAlgorithmFactory == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objCryptAlgorithmFactory = getNewInstance();
				INITIALIZED=true;
			}
		}
		return objCryptAlgorithmFactory;		
	}
	public static KernelCryptAlgorithmFactoryZZZ getInstance(IKernelZZZ objKernel) throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(KernelCryptAlgorithmFactoryZZZ.class) {
			if(objCryptAlgorithmFactory == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objCryptAlgorithmFactory = getNewInstance(objKernel);
				INITIALIZED=true;
			}
		}
		return objCryptAlgorithmFactory;	
	}
	
	public static KernelCryptAlgorithmFactoryZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new KernelCryptAlgorithmFactoryZZZ();
	}
	
	public static KernelCryptAlgorithmFactoryZZZ getNewInstance(IKernelZZZ objKernel) throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new KernelCryptAlgorithmFactoryZZZ(objKernel);
	}
	
		
	public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ {	
		ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
		this.setCryptAlgorithmType(objCrypt);
		return objCrypt;
	}
	
	//### Aus Interface ICryptZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCryptAlgorithmCreatedLast;
	}
	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCryptAlgorithmCreatedLast = objCrypt;
	}
}
