package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenuableZZZ;
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
	
	
	@Override
	public boolean startit(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
		HashMapZZZ<String,Object> hmVariable = objMenuPoint.getVariableHashMap();
		return startit(hmVariable);
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
	
	private boolean startCountAlphanumeric_(HashMap<String,String> hmVariable) throws ExceptionZZZ {
		return startCountByFactory_(hmVariable);
	}
		
	
	
	//########################################
	
	private boolean startCountByFactory_(HashMap<String,String> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			int iCounterType = -1; int iCounterValueMax = -1;
			if(hmVariable!=null) {
				//Ausgabewerte zurücksetzen
				hmVariable.remove("OUTPUT_COUNTER_VALUE_CURRENT");
				//hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_UNCRYPTED);
				//hmVariable.remove(KeyPressThreadEncryptZZZ.sOUTPUT_TEXT_DECRYPTED);
			
			
				//Debugausgabe, ob auch alles leer ist			
				String sDebug = HashMapUtilZZZ.computeDebugString(hmVariable, "<BR>","|");
				System.out.println(sDebug);
			
					
			
				//Dahinter steckt eine definierte Zahl.
				String sCounterKey = (String) hmVariable.get("INPUT_COUNTER_TYPE"); //Aus ExampleMenuPoint_2ZZZ.initit()
				iCounterType = StringZZZ.toInteger(sCounterKey);	
				this.setCounterType(iCounterType);
				
				String sConterValueMax = (String) hmVariable.get("INPUT_COUNTER_VALUE_MAX"); //Aus ExampleMenuPoint_2ZZZ.initit()
				iCounterValueMax  = StringZZZ.toInteger(sConterValueMax);	
				this.setCounterValueMax(iCounterValueMax);
			}
			
			//### Verarbeitung je nach CounterType
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
				//Falls der Zähler nicht ganz neu ist, gibt es ggfs. schon einen anderen Wert in dem Konsolenspeicher
				//Diesen dann als Startwert verwenden, aber nur wenn intern noch kein Wert vorliegt.
				int iValueCurrent = 0;
				IExampleConsoleServiceZZZ objConsoleService = (IExampleConsoleServiceZZZ) this.getConsoleController().getConsoleServiceObject();
				if(objConsoleService!=null) {
					iValueCurrent = objConsoleService.getCounter();
				}
				
				String sValueCurrent = (String) hmVariable.get("INPUT_COUNTER_VALUE_CURRENT");				
				if(!StringZZZ.isEmpty(sValueCurrent)) {
					iValueCurrent = StringZZZ.toInteger(sValueCurrent);
				}
				
				
				String sOutput = "Zählerwert '" + sValueCurrent + "' (hochzählen angehalten).";
				
				
				if(iValueCurrent >= this.getCounterValueMax() && this.getCounterValueMax()>=1) {
					//ANHALTEN 
					IKeyPressThreadMenuableZZZ objKeyPressThread = (IKeyPressThreadMenuableZZZ) objConsoleService.getConsoleController().getKeyPressThread();
					IMenuPointZZZ objMenuePointOld = objKeyPressThread.getMenuPoint();
					objMenuePointOld.onStopit();
				}else {
					//WEITER HOCHZÄHLEN
					
					//+++++++++++++++++++++++++++++++++++++++++++++++++++
					//Rechnen mit dem Zählwert und in alle Stellen zurückschreiben...
					iValueCurrent = iValueCurrent+1;
					objCounter.setValueCurrent(iValueCurrent);
					sValueCurrent = Integer.toString(iValueCurrent);
					hmVariable.put("INPUT_COUNTER_VALUE_CURRENT", sValueCurrent);
							
					sOutput = objCounter.getStringNext();
					hmVariable.put("OUTPUT_COUNTER_VALUE_CURRENT", sOutput);
								
					//Direkt den Wert im ConsolenService Setzten
					//... z.B. für die Ausgabe des Zählers am Schluss, oder einen "Fortsetzungsstart".				
					if(objConsoleService!=null) {
						objConsoleService.setCounter(iValueCurrent);					
					}
				
				}
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//AUSGABE
				System.out.println(StringZZZ.repeat("-", 20) + "\n" + sOutput + "\n" + StringZZZ.repeat("-", 20));

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
