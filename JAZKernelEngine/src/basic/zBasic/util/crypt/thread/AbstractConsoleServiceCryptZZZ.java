package basic.zBasic.util.crypt.thread;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMaintypeZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;

public abstract class AbstractConsoleServiceCryptZZZ<T> extends AbstractConsoleServiceZZZ<T>{
	private static final long serialVersionUID = 4300438111371922169L;
	
	protected String sCryptType = null;
	protected ICryptZZZ objCrypt = null;
	
	
	//### KONSTRUKTOR
	public AbstractConsoleServiceCryptZZZ()  throws ExceptionZZZ {
		super();
	}
	public AbstractConsoleServiceCryptZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);		
	}
	public AbstractConsoleServiceCryptZZZ(IConsoleControllerZZZ objConsole,String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);		
	}
	public AbstractConsoleServiceCryptZZZ(IConsoleControllerZZZ objConsole,String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);	
	}
	
	//### GETTER / SETTER
	public String getCryptType() throws ExceptionZZZ{
		return this.sCryptType;
	}
	
	public void setCryptType(String sCryptType) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sCryptType)) {
			this.sCryptType = sCryptType;
			this.setCrypt(null);
		}else if(sCryptType.equals(this.sCryptType)) {
			//mache nix
		}else {
			//bei einem neuen CryptType, setze CryptType Objekt leer.
			this.sCryptType = sCryptType;
			this.setCrypt(null);			
		}
	}
	
	
	public ICryptZZZ getCrypt() throws ExceptionZZZ{
		if(this.objCrypt == null) {
			String sCipher = this.getCryptType();
			this.objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
		}
		return this.objCrypt;
	}
	
	public void setCrypt(ICryptZZZ objCrypt) throws ExceptionZZZ {
		this.objCrypt = objCrypt;
	}
	
	
	//### METHODEN
	public boolean preStart(ICryptZZZ objCrypt, HashMapZZZ<String,Object>hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			bReturn = this.preStartObject(objCrypt,hmVariable);
			if(!bReturn) {
				System.out.println("PreStart Objecteinstellungen nicht erfolgreich, Abbruch");
				bReturn=false;
				break main;
			}
			
			bReturn = this.preStartFlags(objCrypt, hmVariable);
			if(!bReturn) {
				System.out.println("PreStart Flags nicht erfolgreich, Abbruch");
				bReturn=false;
				break main;
			}
		}//end main:
		return bReturn;
	}
	
	public boolean preStartObject(ICryptZZZ objCrypt, HashMapZZZ<String,Object>hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sInput=null;
			
			//+++ Zusätzlich gesetzte Argumente				
			if(objCrypt.getSubtype()==CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()) {
				//A) Für ROT-Algorithmen
				//0) NumericKey
				sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_KEY_NUMERIC);
				if(!StringZZZ.isEmpty(sInput)) {
					Integer intCryptKey = new Integer(sInput);
					int iCryptKey = intCryptKey.intValue();
					objCrypt.setCryptNumber(iCryptKey);
				}

			}else if(objCrypt.getSubtype()==CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()) {
				//B) Für Vigenere-Algorithmen
				//0) StringKey
				sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_KEY_STRING);
				if(!StringZZZ.isEmpty(sInput)) {
					String sCryptKey = sInput;						
					objCrypt.setCryptKey(sCryptKey);
				}
									
			}else {
				System.out.println("Der Algorithmus-Subtyp wird nicht behandelt.");
				bReturn = false;
				break main;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean preStartFlags(ICryptZZZ objCrypt, HashMapZZZ<String,Object>hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sInput=null;
			
			//a) alle Flags setzen und ggfs. auswerten
			String[] saFlags = hmVariable.getKeysAsStringStartingWith(KeyPressThreadEncryptZZZ.sINPUT_FLAG);
			if(saFlags!=null) {
				for(String stemp : saFlags) {
					String sFlagName = StringZZZ.right(stemp,KeyPressThreadEncryptZZZ.sINPUT_FLAG);
					Boolean bValue = (Boolean) hmVariable.get(stemp);
					objCrypt.setFlag(sFlagName, bValue);
				}
			}
			
			//b) CharacterPool setzen, wenn verwendet
			if(objCrypt.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USESTRATEGY_CHARACTERPOOL.name())) {
				sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_CHARACTERPOOL);
				if(!StringZZZ.isEmpty(sInput)) {
					objCrypt.setCharacterPoolBase(sInput);
					
					//... und dann auch das fehlende Zeichen setzen
					CharacterExtendedZZZ objCharacterMissingReplacement = new CharacterExtendedZZZ('¶');//Mal was anderes verwenden als den "DefaultBuchstaben" aus ICharacterPoolUserConstantZZZ
					objCrypt.setCharacterMissingReplacement(objCharacterMissingReplacement);
				}
				
				//ba) Zusaetzlichen Zeichenpool setzen, wenn verwendet					
				if(objCrypt.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEADDITIONALCHARACTER.name())) {
					sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_CHARACTERPOOL_ADDITIONAL);
					if(!StringZZZ.isEmpty(sInput)) {
						objCrypt.setCharacterPoolAdditional(sInput);
					}
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//########################
}
