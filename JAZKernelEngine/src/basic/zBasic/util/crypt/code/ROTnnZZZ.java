package basic.zBasic.util.crypt.code;

import java.util.HashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Definiere eine Liste von Zeichen.
 * Diese wird dann (auch als Großbuchstaben) durch das ROT (also Zeichenverschiebung) verschlüsselt.
 * 
 * Da dies nur etwas besser ist als nur den ASCII Raum gesammt zu verwenden,
 * erbt diese Klasse von ROTasciiZZZ.
 * Der grundsätzliche Mechanismus bleibt gleich.
 * 
 * @author Fritz Lindhauer, 25.09.2022, 08:10:43
 * 
 * Anregung von:  
 * https://codereview.stackexchange.com/questions/7241/rot-n-algorithm-in-java
 * 
 */
public class ROTnnZZZ extends AbstractROTnnZZZ{	
	ROTnnZZZ() throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super(null);
		String[] saFlagControl = {"init"};
		RotNnNew_(null,-1,saFlagControl);
	}
	ROTnnZZZ(String[]saFlagControl) throws ExceptionZZZ { //Paktesichtbarkeit dieses Konstrktors auf PACKAGE geändert, damit die Factory einfach das Objekt erstellen kann. 
		super(null);		
		RotNnNew_(null,-1,saFlagControl);
	}
	public ROTnnZZZ(int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotNnNew_(null,iCryptKey,null);
	}
	public ROTnnZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(null);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNnNew_(null,iCryptKey,saFlagControl);
	}
	public ROTnnZZZ(String sCharacterPool, int iCryptKey) throws ExceptionZZZ {
		super(null);
		RotNnNew_(sCharacterPool,iCryptKey,null);
	}
	public ROTnnZZZ(String sCharacterPool, int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super(sFlagControl);
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNnNew_(sCharacterPool,iCryptKey,saFlagControl);
	}
	
	@Override
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn;
	}
	
	@Override
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn;
	}
	
	private boolean RotNnNew_(String sCharacterPool, int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
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
					 this.printlnDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					 
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
		this.setCharacterPoolBase(sCharacterPool);
		bReturn = true;
	}//end main:
		return bReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPoolBase=this.getCharacterPoolBase();
		String sCharacterPoolAdditional=this.getCharacterPoolAdditional();
		
		CharacterExtendedZZZ objCharacterMissingReplacement = this.getCharacterMissingReplacment();
		boolean bUseUpperCase = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE);
		boolean bUseLowerCase = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE);
		boolean bUseNumeric = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC);
		boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
		boolean bUseAdditionalCharacter = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEADDITIONALCHARACTER);
		return ROTnnZZZ.encrypt(sInput, sCharacterPoolBase, objCharacterMissingReplacement, iCryptKey,  bUseUpperCase, bUseLowerCase, bUseNumeric, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		String sCharacterPoolBase=this.getCharacterPoolBase();
		String sCharacterPoolAdditional=this.getCharacterPoolAdditional();
				
		CharacterExtendedZZZ objCharacterMissingReplacement = this.getCharacterMissingReplacment();
		boolean bUseUpperCase = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE);
		boolean bUseLowerCase = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE);
		boolean bUseNumeric = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC);
		boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
		boolean bUseAdditionalCharacter = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEADDITIONALCHARACTER);
		return ROTnnZZZ.decrypt(sInput, sCharacterPoolBase, objCharacterMissingReplacement, iCryptKey, bUseUpperCase, bUseLowerCase, bUseNumeric, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);
	}
	
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric, boolean bUseBlank) throws ExceptionZZZ {
		return ROTnnZZZ.encrypt(sInput, sCharacterPoolBaseIn, objCharacterMissingReplacement, n, bUseUppercase, bUseLowercase, bUseNumeric, bUseBlank, false, null);
    }
	
	
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric, boolean bUseBlank, boolean bUseAdditionalCharacter, String sCharacterPoolAdditionalIn) throws ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolBase = null;
			if(StringZZZ.isEmpty(sCharacterPoolBaseIn)) {
				sCharacterPoolBase= AbstractROTnnZZZ.getCharacterPoolBaseDefault();
			}else {
				sCharacterPoolBase = sCharacterPoolBaseIn;
			}

			String sCharacterPoolAdditional = null;
			if(sCharacterPoolAdditionalIn==null) {
				sCharacterPoolAdditional= AbstractVigenereNnZZZ.getCharacterPoolAdditionalDefault();
			}else {
				sCharacterPoolAdditional = sCharacterPoolAdditionalIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercase, bUseLowercase, bUseNumeric, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);
			sReturn = AbstractROTnnZZZ.encrypt(sInput, abcABC, objCharacterMissingReplacement, n);		
		}//end main;
		return sReturn;
    }
	
	public static String decrypt(String sInput, String sCharacterPoolBaseIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric, boolean bUseBlank) throws ExceptionZZZ {
		return ROTnnZZZ.decrypt(sInput, sCharacterPoolBaseIn, objCharacterMissingReplacement, n, bUseUppercase, bUseLowercase, bUseNumeric, bUseBlank, false,null);
	}
	
	public static String decrypt(String sInput, String sCharacterPoolBaseIn, CharacterExtendedZZZ objCharacterMissingReplacement, int n, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric, boolean bUseBlank, boolean bUseAdditionalCharacter,String sCharacterPoolAdditionalIn) throws ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolBase = null;
			if(StringZZZ.isEmpty(sCharacterPoolBaseIn)) {
				sCharacterPoolBase= AbstractROTnnZZZ.getCharacterPoolBaseDefault();
			}else {
				sCharacterPoolBase = sCharacterPoolBaseIn;
			}

			String sCharacterPoolAdditional = null;
			if(sCharacterPoolAdditionalIn==null) {
				sCharacterPoolAdditional= AbstractROTnnZZZ.getCharacterPoolAdditionalDefault();
			}else {
				sCharacterPoolAdditional = sCharacterPoolAdditionalIn;
			}
			
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercase, bUseLowercase, bUseNumeric, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);
			String sCharacterPool = StringZZZ.reverse(abcABC);	
			sReturn = AbstractROTnnZZZ.encrypt(sInput, sCharacterPool, objCharacterMissingReplacement, n);
		}
		return sReturn;						
	}		
}
