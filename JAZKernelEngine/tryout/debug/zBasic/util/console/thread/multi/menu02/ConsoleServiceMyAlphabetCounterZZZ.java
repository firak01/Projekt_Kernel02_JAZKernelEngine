package debug.zBasic.util.console.thread.multi.menu02;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.counter.CounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.counter.ICounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.counter.ICounterStringZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class ConsoleServiceMyAlphabetCounterZZZ<T> extends AbstractConsoleServiceMyCounterZZZ<T> {
	private static final long serialVersionUID = -2911808778962336187L;

	public ConsoleServiceMyAlphabetCounterZZZ() throws ExceptionZZZ {
		super();
	}
	
	public ConsoleServiceMyAlphabetCounterZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}
	public ConsoleServiceMyAlphabetCounterZZZ(IConsoleControllerZZZ objConsole, String sFlag) throws ExceptionZZZ {
		super(objConsole, sFlag);
	}
	public ConsoleServiceMyAlphabetCounterZZZ(IConsoleControllerZZZ objConsole, String[] saFlag) throws ExceptionZZZ {
		super(objConsole, saFlag);
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
//			this.iCounter++;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Zähler crypt: " + iCounter);
//
//			HashMapZZZ<String,Object>hmVariable=this.getConsole().getVariableHashMap();			
//			this.startit(hmVariable);
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
	
	
	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Jetzt können Variablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			
			//Nutze auch die nicht startit fähigen Methoden
			if(!StringZZZ.isEmptyNull(sCallingMethod)) {
				switch(sCallingMethod){	
					case "countAlphanumeric":
						bReturn = startCountAlphanumeric_(hmVariable);
						break;
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
						throw ez;
				}
			}else {
				//############## ALTE VERSION, NOCH NICHT ENTFERNT STARTBAR
				bReturn = startCountByFactory_(hmVariable);
			}//sCallingMethod
									
			//bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private boolean startCountAlphanumeric_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		return startCountByFactory_(hmVariable);
	}
		
	
	
	//########################################
	
	private boolean startCountByFactory_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			if(hmVariable!=null) {
				//Ausgabewerte zurücksetzen
				hmVariable.remove("OUTPUT_COUNTER_VALUE_CURRENT");
				//hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_UNCRYPTED);
				//hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED);
			}
			
			//Debugausgabe, ob auch alles leer ist
			if(hmVariable!=null) {
				String sDebug = hmVariable.computeDebugString("<BR>","|");
				System.out.println(sDebug);
			}
					
			
			//Dahinter steckt eine definierte Zahl.
			String sCounterKey = (String) hmVariable.get("INPUT_COUNTER_TYPE");
			int iCounterType = StringZZZ.toInteger(sCounterKey);	
			this.setCounterType(iCounterType);
			
			if(iCounterType>=1) {
				//ICounterStringZZZ objCounter = CounterByCharacterAsciiFactoryZZZ.getInstance().createCounter(iCounterType);//CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(sCipher);
				ICounterStringZZZ<?> objCounter = this.getCounter();
				boolean bSuccess = this.preStart(objCounter, hmVariable);
				if(!bSuccess) {					
					System.out.println("PreProcessing nicht erfolgreich, Abbruch");
					bReturn=false;
					break main;
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++
				String sValueCurrent = (String) hmVariable.get("INPUT_COUNTER_VALUE_CURRENT");
				int iValueCurrent = 0;
				if(!StringZZZ.isEmpty(sValueCurrent)) {
					iValueCurrent = StringZZZ.toInteger(sValueCurrent);
				}
				
				iValueCurrent = iValueCurrent+1;
				objCounter.setValueCurrent(iValueCurrent);
				sValueCurrent = Integer.toString(iValueCurrent);
				hmVariable.put("INPUT_COUNTER_VALUE_CURRENT", sValueCurrent);
						
				String sOutput = objCounter.getStringNext();
				hmVariable.put("OUTPUT_COUNTER_VALUE_CURRENT", sOutput);

				//### AUSGABE
				System.out.println(sOutput);

				
				//##################### Direkt den Wert im ConsolenService Setzten
				//                      ... z.B. für die Ausgabe des Zählers am Schluss.
				IExampleConsoleServiceZZZ objConsoleService = (IExampleConsoleServiceZZZ) this.getConsoleController().getConsoleServiceObject();
				if(objConsoleService!=null) objConsoleService.setCounter(iValueCurrent);
				//#####################
				
				bReturn = true;
			}else {
				System.out.println("noch kein Zähleralgorithmus festgelegt.");
				bReturn = false;
			}
			
		}//end main:
		return bReturn;	
	}

	
}
