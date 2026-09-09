package basic.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ConsoleServiceDecryptZZZ<T> extends AbstractConsoleServiceCryptZZZ<T> {
	private static final long serialVersionUID = -268788952256301715L;


	public ConsoleServiceDecryptZZZ() throws ExceptionZZZ {
		super();
	}
	
	public ConsoleServiceDecryptZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}
	public ConsoleServiceDecryptZZZ(IConsoleControllerZZZ objConsole, String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);
	}
	public ConsoleServiceDecryptZZZ(IConsoleControllerZZZ objConsole, String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);
	}
		

	@Override
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Jetzt können Variablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			
			//Nutze auch die nicht startit fähigen Methoden
			if(!StringZZZ.isEmptyNull(sCallingMethod)) {
				switch(sCallingMethod){
					case "ascii":
						bReturn = startAscii_(hmVariable);
						break;	
					case "processDecryptROT13":
						bReturn = startDecryptROT13_(hmVariable);
						break;
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
						throw ez;
				}
			}else {
				//############## ALTE VERSION, NOCH NICHT ENTFERNT STARTBAR
				bReturn = startDecryptByFactory_(hmVariable);
			}//sCallingMethod
									
			//bReturn = true;
		}//end main:
		return bReturn;
	}
		
	//########################################
	private boolean startAscii_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		KeyPressThreadUtilZZZ.printTableAscii();		
		return true;
	}
	
	//########################################
	
	private boolean startDecryptByFactory_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			if(hmVariable!=null) {
				//Ausgabewerte zurücksetzen
				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_ENCRYPTED);
				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_UNCRYPTED);
				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED);
			}
			
			//Debugausgabe, ob auch alles leer ist
			if(hmVariable!=null) {
				String sDebug = HashMapUtilZZZ.computeDebugString(hmVariable, "<BR>","|");
				System.out.println(sDebug);
			}
					
			//TODOGOON20260818 - eigentlich müsste hier die MEthode per Fallunterscheidung geholt werden
			
			//Die eingegebenen Variablen über eine HashMap aus der Console für die Steuerung der Verschlüsselung nutzen. 			
			//String sCipher = (String) hmVariable.get(CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation());			
			String sCipher = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_CIPHER);	
			this.setCryptType(sCipher);
						
			if(!StringZZZ.isEmpty(sCipher)) {
				//ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
				ICryptZZZ objCrypt = this.getCrypt();				
				boolean bSuccess = this.preStart(objCrypt, hmVariable);
				if(!bSuccess) {					
					System.out.println("PreStart nicht erfolgreich, Abbruch");
					bReturn=false;
					break main;
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++
								
				String sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_TEXT_ENCRYPTED);				
				try {
					String sOutput = objCrypt.decrypt(sInput);
					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED, sOutput);
					
					System.out.println("Entschluesselter Wert:\n"+sOutput);
					String sOutput2 = objCrypt.encrypt(sOutput);
					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_ENCRYPTED, sOutput2);
					System.out.println("Wieder verschluesselter Wert:\n"+sOutput2);
					
					bReturn = true;
				}catch( IllegalArgumentException e) {
					String sError=e.getMessage();
					System.out.println("Fehler bei der Eingabe.\nText enthaelt fuer die Argumentkombination ungueltige Werte.\nFehler: "+sError +"\nbei Eingabe: "+sInput);
					bReturn=false;
				}
				
			}else {
				System.out.println("noch kein Schluesselalgorithmus festgelegt.");
				bReturn = false;
			}
			
		}//end main:
		return bReturn;	
	}
	
	//########################################
	private boolean startDecryptROT13_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		return startDecryptByFactory_(hmVariable);
	}
}
