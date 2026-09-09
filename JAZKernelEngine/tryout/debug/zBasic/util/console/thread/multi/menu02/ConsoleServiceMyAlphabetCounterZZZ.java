package debug.zBasic.util.console.thread.multi.menu02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.counter.ICounterStringZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
	
	@Override
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
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
	
	private boolean startCountAlphanumeric_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		return startCountByFactory_(hmVariable);
	}
		
	
	
	//########################################
	
	private boolean startCountByFactory_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
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
