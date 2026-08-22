package basic.zBasic.util.crypt.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ConsoleServiceEncryptZZZ extends AbstractConsoleServiceCryptZZZ {
	private static final long serialVersionUID = 1L;

	public ConsoleServiceEncryptZZZ() throws ExceptionZZZ {
		super();
	}
	
	public ConsoleServiceEncryptZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}
	public ConsoleServiceEncryptZZZ(IConsoleControllerZZZ objConsole, String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);
	}
	public ConsoleServiceEncryptZZZ(IConsoleControllerZZZ objConsole, String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);
	}
	
	private int iCounter = 0;
		
	public int getcounter() {
		return this.iCounter;
	}
	
//	@Override
//	public boolean start() throws ExceptionZZZ {
//		boolean bReturn = false;
//		try {
//		main:{
//			this.getConsole().isConsoleUserThreadRunning(true);
//			//Merke: Diesen Teil nicht als Schleife ausführen... viel zu kompliziert... es gibt schon genug andere Threads
//			//while(!this.isStopped()) {
//			if(this.isStopped()) break main;
//			if(this.isOutputAllFinished()) break main; //wenn Z.B. schon ein Menuepunkt ausgefuehrt worden ist. Z.B. eine einfache ASCII-Tabelle ausgegeben wurde.
//			if(!this.isInputAllFinished()) break main; 
//			String sInput = null;
//			
//			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
//			//       Darum muss man alles in dem KeyPressThread erledigen
//			//Warten auf die fertige Eingabe.			
//			//if(!this.getConsole().isKeyPressThreadFinished()) break main;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: WARTE AUF FERTIGE KONSOLENEINGABE ######");				
//			do {
//				 try {				 
//					 Thread.sleep(200);
//					 //System.out.println("CryptThread wartet auf fertige Konsoleneingabe");
//				} catch (InterruptedException e) {
//					System.out.println("KeyPressThread: Wait Error");
//					e.printStackTrace();
//				}				 
//			}while(!this.getConsole().isInputAllFinished());
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: WARTE AUF FERTIGE KONSOLENEINGABE ######");
//			
//			
//			//this.isOutputAllFinished(false);
//			
//			
//			this.iCounter++;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Zähler crypt: " + iCounter);
//
//			HashMapZZZ<String,Object>hmVariable=this.getConsole().getVariableHashMap();
//			this.startit(hmVariable);
//			
//			
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: DUMMYWARTEN ALS TEST ######");
//			 try {				 
//				 Thread.sleep(4500);
//			} catch (InterruptedException e) {
//				System.out.println("KeyPressThread: Wait Error");
//				e.printStackTrace();
//			}
//			 if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: DUMMYWARTEN ALS TEST ######");			 
//			 this.isOutputAllFinished(true);			
//			//}//end while !isStopped
//		}//end main:
//		}catch(ExceptionZZZ ez) {
//			ez.printStackTrace();
//		}
//		this.getConsole().isConsoleUserThreadFinished(true);
//		return bReturn;
//	}
	
	
	//ALTE VERSION
//	
//	@Override
//	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			
//			if(hmVariable!=null) {
//				//Ausgabewerte zurücksetzen
//				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_ENCRYPTED);
//				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_UNCRYPTED);
//				hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED);
//			}
//			
//			//Debugausgabe, ob auch alles leer ist
//			if(hmVariable!=null) {
//				String sDebug = hmVariable.computeDebugString("<BR>","|");
//				System.out.println(sDebug);
//			}
//			
//			//TODOGOON20260818 - eigentlich müsste hier die MEthode per Fallunterscheidung geholt werden
//					
//			//Die eingegebenen Variablen über eine HashMap aus der Console für die Steuereung der Verschlüsselung nutzen. 			
//			//String sCipher = (String) hmVariable.get(CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation());
//			String sCipher = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_CIPHER);
//			if(!StringZZZ.isEmpty(sCipher)) {
//				ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
//				boolean bSuccess = this.preProcessing(objCrypt, hmVariable);
//				if(!bSuccess) {					
//					System.out.println("PreProcessing nicht erfolgreich, Abbruch");
//					bReturn=false;
//					break main;
//				}
//								
//				//+++++++++++++++++++++++++++++++++++++++++++++++++
//								
//				String sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_TEXT_UNCRYPTED);				
//				try {
//					String sOutput = objCrypt.encrypt(sInput);
//					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_ENCRYPTED, sOutput);
//					
//					System.out.println("Verschluesselter Wert:\n"+sOutput);
//					String sOutput2 = objCrypt.decrypt(sOutput);
//					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED, sOutput2);
//					System.out.println("Wieder entschluesselter Wert:\n"+sOutput2);
//					
//					bReturn = true;
//				}catch( IllegalArgumentException e) {
//					String sError=e.getMessage();
//					System.out.println("Fehler bei der Eingabe.\nText enthaelt fuer die Argumentkombination ungueltige Werte.\nFehler: "+sError +"\nbei Eingabe: "+sInput);
//					bReturn=false;
//				}
//				
//			}else {
//				System.out.println("noch kein Schluesselalgorithmus festgelegt.");
//				bReturn = false;
//			}
//			
//		}//end main:
//		return bReturn;
//	}
	
	
	//Startit wird dann ggfs. von einem anderen Thread aus aufgerufen.
	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Jetzt können Variablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			
			//Nutze auch die nicht startit fähigen Methoden
			if(!StringZZZ.isEmptyNull(sCallingMethod)) {
				switch(sCallingMethod){
					case "ascii":
						bReturn = ascii_(hmVariable);
						break;	
					case "processEncryptROT13":
						bReturn = processEncryptROT13_(hmVariable);
						break;
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
						throw ez;
				}
			}else {
				//############## ALTE VERSION, NOCH NICHT ENTFERNT STARTBAR
				bReturn = processEncryptByFactory_(hmVariable);
			}//sCallingMethod
									
			//bReturn = true;
		}//end main:
		return bReturn;
	}	
	
	//########################################
	private boolean ascii_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		KeyPressThreadUtilZZZ.printTableAscii();		
		return true;
	}
	
	//########################################
	private boolean processEncryptByFactory_(HashMapZZZ hmVariable) throws ExceptionZZZ {
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
				String sDebug = hmVariable.computeDebugString("<BR>","|");
				System.out.println(sDebug);
			}
			
			//TODOGOON20260818 - eigentlich müsste hier die MEthode per Fallunterscheidung geholt werden
					
			//Die eingegebenen Variablen über eine HashMap aus der Console für die Steuereung der Verschlüsselung nutzen. 			
			//String sCipher = (String) hmVariable.get(CryptCipherAlgorithmMappedValueZZZ.CryptCipherTypeZZZ.ROT13.getAbbreviation());
			String sCipher = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_CIPHER);
			if(!StringZZZ.isEmpty(sCipher)) {
				ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
				boolean bSuccess = this.preProcessing(objCrypt, hmVariable);
				if(!bSuccess) {					
					System.out.println("PreProcessing nicht erfolgreich, Abbruch");
					bReturn=false;
					break main;
				}
								
				//+++++++++++++++++++++++++++++++++++++++++++++++++
								
				String sInput = (String) hmVariable.get(KeyPressThreadEncryptZZZ.sINPUT_TEXT_UNCRYPTED);				
				try {
					String sOutput = objCrypt.encrypt(sInput);
					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_ENCRYPTED, sOutput);
					
					System.out.println("Verschluesselter Wert:\n"+sOutput);
					String sOutput2 = objCrypt.decrypt(sOutput);
					hmVariable.put(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED, sOutput2);
					System.out.println("Wieder entschluesselter Wert:\n"+sOutput2);
					
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
	private boolean processEncryptROT13_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		return processEncryptByFactory_(hmVariable);
	}
	
}
