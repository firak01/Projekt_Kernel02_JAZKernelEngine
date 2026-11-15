package basic.zBasic.util.crypt.code;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

public class CryptAlgorithmFactoryZZZ extends AbstractObjectWithFlagZZZ implements ICryptUserZZZ{

private static CryptAlgorithmFactoryZZZ objCryptAlgorithmFactory = null;  //muss static sein, wg. getInstance()!!!

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

private ICryptZZZ objCryptCreatedLast = null; 

	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private CryptAlgorithmFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private CryptAlgorithmFactoryZZZ(){
		super();
	}
	
	public static CryptAlgorithmFactoryZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(CryptAlgorithmFactoryZZZ.class) {
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
	
	public static CryptAlgorithmFactoryZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new CryptAlgorithmFactoryZZZ();
	}
			
	public ICryptZZZ createAlgorithmType(String sCipher) throws ExceptionZZZ {	
		ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(sCipher);
		this.setCryptAlgorithmType(objCrypt);
		return objCrypt;
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public ICryptZZZ createAlgorithmType(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ objEnumCipher) throws ExceptionZZZ{
		return CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(objEnumCipher);		
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public static ICryptZZZ createAlgorithmTypeByCipher(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ objEnumCipher) throws ExceptionZZZ{
		return CryptAlgorithmFactoryZZZ.createAlgorithmTypeByCipher(objEnumCipher.name());
	}
	
	public static ICryptZZZ createAlgorithmTypeByCipher(String sCipher) throws ExceptionZZZ{
		ICryptZZZ objReturn = null;
		main:{			
			check:{
					if(StringZZZ.isEmpty(sCipher)){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Cipher", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
					}
			}//end check
		
		//Merke: // nur Default-Kontruktor () würde das Flag "init" setzen, also besser keine Flags setzen mit null.
		if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation())){
			objReturn = new ROT13ZZZ((String[])null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnumeric.getAbbreviation())){
			objReturn = new ROTnumericZZZ((String[]) null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTascii.getAbbreviation())) {
			objReturn = new ROTasciiZZZ((String[]) null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation())) {
			objReturn = new ROTnnZZZ((String[])null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE26.getAbbreviation())) {
			objReturn = new Vigenere26ZZZ((String[])null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE96.getAbbreviation())) {
			objReturn = new Vigenere96ZZZ((String[])null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENERE256.getAbbreviation())) {
			objReturn = new Vigenere256ZZZ((String[])null);
		}else if(sCipher.equalsIgnoreCase(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENEREnn.getAbbreviation())) {
			objReturn = new VigenereNnZZZ((String[])null);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+"unhandled cipher type '" + sCipher + "'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;				
		}
		
		}//end main
		return objReturn;
	}
	
	
	//Interface aus ICryptUserZZZ
	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCryptCreatedLast=objCrypt;
	}
	
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ{
		return this.objCryptCreatedLast;
	}
	
	
}
