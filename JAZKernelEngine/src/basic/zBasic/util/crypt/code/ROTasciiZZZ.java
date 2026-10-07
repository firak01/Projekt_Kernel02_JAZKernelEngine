package basic.zBasic.util.crypt.code;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zKernel.Log;

/**Als Liste von Zeichen wird der gesamte ASCII Raum verwendet.
 * Diese wird dann durch das ROT (also Zeichenverschiebung) verschlüsselt.
 * Merke: Das ist schwieriger zu entschlüsseln als der ROTnn Mechanismus, da der CharacterPool bekannt ist.
 *        Buch_KrypthografieMitJava\\Buch.KryptografieMitJava\\tryout\\basic\\zBasic\\util\\crypt\\CaesarDecode.java
 *        Kann so etwas entschlüsseln, ohne die Zeichenverschiebung zu kennen.
 * 
 * @author Fritz Lindhauer, 25.09.2022, 08:10:43
 * 
 * Anregung von:  
 * https://codereview.stackexchange.com/questions/7241/rot-n-algorithm-in-java
 * 
 */
public class ROTasciiZZZ extends AbstractROTnnZZZ{	
	private static final long serialVersionUID = 4403104917278413094L;
	
	ROTasciiZZZ() throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super();
		String[] saFlagControl = {"init"};
		RotAsciiNew_(-1,saFlagControl);
	}
	
	ROTasciiZZZ(String[] saFlagControl)throws ExceptionZZZ{
		super();				
		RotAsciiNew_(-1,saFlagControl);
	}
	
	ROTasciiZZZ(String sFlagControl)throws ExceptionZZZ{
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotAsciiNew_(-1,saFlagControl);
	}
	
	public ROTasciiZZZ(int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotAsciiNew_(iCryptKey, null);
	}
	public ROTasciiZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotAsciiNew_(iCryptKey,saFlagControl);
	}
	
	@Override
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTascii;
	}
	
	@Override
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTascii;
	}

	private boolean RotAsciiNew_(int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		//try{	 		
			//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			 String stemp; boolean btemp; String sLog;
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){
					 String sKey = stemp;
					 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
					 Log.printlnDate(this, ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					 
					// Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
					// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
					// throw ez;		 
				}
			}
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			
		}
		this.setCryptNumber(iCryptKey);
		
		String sCharacterPool = ROTasciiZZZ.getCharacterPoolStringAscii();
		this.setCharacterPoolBase(sCharacterPool);
		
		
		
	}//end main:
		return bReturn;
	}
	
	public static String getCharacterPoolStringAscii() {
		String sReturn = "";
		
		for(int i = 0; i<=255; i++) {
			char c = (char)(i);
			sReturn = sReturn+c;
		}
		
		return sReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();		
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, iCryptKey);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPool=this.getCharacterPool();		
		return ROTasciiZZZ.decrypt(sInput, sCharacterPool, iCryptKey);
	}
	
	@Override
	public String getCharacterPoolBase() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sCharacterPoolBase)) {
			this.sCharacterPoolBase = ROTasciiZZZ.getCharacterPoolStringAscii();
		}
		return this.sCharacterPoolBase;
	}
		
	public static String encrypt(String sInput, int n)  throws IllegalArgumentException, ExceptionZZZ {
		String sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);
	
	}
	
	
	public static String encrypt(String sInput, String sCharacterPoolIn, int n) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPool= null;
			if(StringZZZ.isEmpty(sCharacterPoolIn)) {
				sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
			}else {
				sCharacterPool = sCharacterPoolIn;
			}
			
			String abcABC = sCharacterPool;
			
			
			int len = abcABC.length();
			
			//MERKE: Wg. der Zuordnung zu einer Map muss sichergestellt sein, dass kein Zeichen im CharacterPool doppelt vorkommt.
			//+++++++++++ CharacterPool normieren			
			ArrayListZZZ<Character> listasCharacterPool = new ArrayListZZZ<Character>();
			for (int i = 0; i < len; i++) {
				try {
					listasCharacterPool.addUnique(abcABC.charAt(i));
				} catch (ExceptionZZZ e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }			
			//+++++++++++
			
			
			len = listasCharacterPool.size();

		    Map<Character, Character> map = new HashMap<Character, Character>();
		    for (int i = 0; i < len; i++) {
		        //map.put(abcABC.charAt(i), abcABC.charAt((i + n + len) % len));
		    	map.put((Character)listasCharacterPool.get(i), (Character)listasCharacterPool.get((i + n + len) % len));
		    }
	
		    StringBuilder sb = new StringBuilder();
		    for(int i = 0; i < sInput.length(); i++) {
		    	char cEncoded = sInput.charAt(i);
		        Character ch = map.get(cEncoded);
		        if (ch == null) {
		            throw new IllegalArgumentException("Illegal character '" + sInput.charAt(i) + "'");
		        }
		        sb.append(ch);
		    }
		    sReturn =  sb.toString();
		}//end main;
		return sReturn;
    }
	
	public static String decrypt(String sInput, int n)  throws IllegalArgumentException, ExceptionZZZ{

		String sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);		
	}
	
	public static String decrypt(String sInput, String sCharacterPoolIn, int n)  throws IllegalArgumentException, ExceptionZZZ{

		String sCharacterPool=null;
		if(StringZZZ.isEmpty(sCharacterPool)) {
			sCharacterPool= ROTasciiZZZ.getCharacterPoolStringAscii();
		}else {
			sCharacterPool=sCharacterPoolIn;
		}
		sCharacterPool = StringZZZ.reverse(sCharacterPool);
		
		return ROTasciiZZZ.encrypt(sInput, sCharacterPool, n);		
	}	
}

