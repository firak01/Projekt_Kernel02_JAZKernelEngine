package debug.zBasic.util.console.thread.multi.menu03;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.crypt.thread.ConsoleServiceEncryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import debug.zBasic.util.console.thread.multi.menuless.ExampleConsoleThreadZZZ;

public class ExampleConsoleServiceZZZ<T> extends AbstractConsoleServiceZZZ<T> implements IExampleConsoleServiceZZZ {
	private static final long serialVersionUID = -310118654741546925L;
	
	protected volatile int iCounter = 0;
	
	//### Konstruktor
	public ExampleConsoleServiceZZZ() throws ExceptionZZZ {
		super();
	}
	public ExampleConsoleServiceZZZ(IConsoleControllerZZZ objConsoleController) throws ExceptionZZZ {
		super(objConsoleController);
	}

	
	//### METHODEN
	@Override
	public int getCounter() throws ExceptionZZZ{
		return this.iCounter;
	}
	
	@Override
	public void setCounter(int iCounter) throws ExceptionZZZ {
		this.iCounter = iCounter;
	}
	
	//Das ist kein eigener Thread mehr
//	@Override
//	public boolean start() throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			if(this.isStopped()) break main;
//			
//			this.iCounter++;
//			System.out.println("Zähler: " + iCounter);
//			 try {				 
//				Thread.sleep(100);
//				bReturn = true;
//			} catch (InterruptedException e) {
//				System.out.println("KeyPressThread: Wait Error");
//				e.printStackTrace();
//			}
//		}//end main:
//		return bReturn;	
//	}
	
	
	//Startit wird dann von einem anderen Thread aus aufgerufen.
	@Override
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Jetzt können Varablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			if(StringZZZ.isEmptyNull(sCallingMethod)) break main;
			
			switch(sCallingMethod){
//				case "ascii":
//					bReturn = startAscii_(hmVariable);
//					break;
				case "process1":
					bReturn = startProcess1_(hmVariable);
					break;
//				case "countAlphanumeric":
//					bReturn = startCountAlphanumeric_(hmVariable);
//					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
					throw ez;
			}
			
			
			
			//bReturn = true;
		}//end main:
		return bReturn;
	}	
	
//	private boolean startAscii_(HashMapZZZ hmVariable) throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			KeyPressThreadUtilZZZ.printTableAscii();
//			bReturn = true;
//		}//end main:
//		return bReturn;
//	}
	
	private boolean startProcess1_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			int iCounter = this.getCounter();
			//String sInput = (String) hmVariable.get("INPUT_COUNTER_VALUE_CURRENT");
			//int iCounter = StringZZZ.toInteger(sInput);
			iCounter++;
			
			//### AUSGABE
			System.out.println("Zähler: " + iCounter);
			
			//### VARIABLENWERT RETTEN			
			//a) eigenen Klasse
			this.setCounter(iCounter);
			
			//b) HashMap, sowohl als Eingabewert als auch als globaler Ausgabewert
			String sOutput = Integer.toString(iCounter);
			hmVariable.put("INPUT_COUNTER_VALUE_CURRENT", sOutput);
			hmVariable.put("OUTPUT_COUNTER_VALUE_CURRENT", sOutput);
			
			//##################### Direkt den Wert im ConsolenService Setzten
			//                      ... z.B. für die Ausgabe des Zählers am Schluss.
			IExampleConsoleServiceZZZ objConsoleService = (IExampleConsoleServiceZZZ) this.getConsoleController().getConsoleServiceObject();
			if(objConsoleService!=null) objConsoleService.setCounter(iCounter);
			//#####################
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
//	private boolean startCountAlphanumeric_(HashMapZZZ hmVariable) throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			IConsoleControllerZZZ objConsoleController = this.getConsoleController();
//			
//			//Der Service, der im Thread ausgeführt wird
//			ConsoleServiceMyAlphabetCounterZZZ objCounterService = new ConsoleServiceMyAlphabetCounterZZZ();
//			objCounterService.setConsoleController(objConsoleController); //Damit kann er dann auf globale Angaben der Console zugreifen
//			                                                              //!!! er kann dann auch darüber auf den ConsoleService zugreifen.
//			                                                              //    Dort kann er dann das bisherige Ergebnis ablegen
//			
//			//TODOGOON20260824;//IDEE für jede aufgerufenen Methode 1x den ConsoleServiceThreadZZZ erzeugen und dann in einer HashMap ablegen und wieder holen.
//			//Dann wird er nur 1x erstellt und der Thread auch nur 1x gestartet.
//			final ConsoleServiceThreadZZZ objCounterServiceThread = new ConsoleServiceThreadZZZ();
//			objCounterServiceThread.setConsoleController(objConsoleController);
//			objCounterServiceThread.setConsoleServiceObject(objCounterService);
//			
//			//Den objCounterServiceThread am ConsoleController registrieren.
//			//Dann kann er auf die "quit" Anweisung reagieren.
//		    objConsoleController.registerForStatusLocalEvent(objCounterServiceThread);
//	  
//			//Den neu erstellten Thread starten, er wird dann aus dem 
//		    //ConsoleServiceMyAlphabetCounterZZZ - Objekt die Methode startit() aufrufen.
//		    Thread t2 = new Thread(objCounterServiceThread);
//		    t2.start();
//			
//		  	//ACHTUNG... Es wird nicht auf das Ende des Threads gewartet.	
//		    //           Man bekommt also immer 0.
//			//Übernimm den Zählerwert (nicht den String!) in den eigenen Zähler.
//			//So kann man ggfs. auf einen anderen Zählertyp umschalten und fortfahren
//			String sCounter = (String) hmVariable.get("OUTPUT_COUNTER_VALUE_CURRENT");
//			int iCounter = StringZZZ.toInteger(sCounter);
//			this.setCounter(iCounter);
//		}//end main:
//		return bReturn;	
//	}
	
	
	
	
	
}
