package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.counter.ICounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ExampleMenuPoint_2ZZZ extends AbstractMenuPointZZZ {
	public ExampleMenuPoint_2ZZZ() throws ExceptionZZZ {
		super();
	}

	public ExampleMenuPoint_2ZZZ(HashMapZZZ<String,Object> hmVariableInit) throws ExceptionZZZ {
		super(hmVariableInit);
	}

	@Override
	public boolean initit(HashMapZZZ<String, Object>hmVariableExternal) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
			String sCounterValueCurrent = null;
			if(hmVariableExternal!=null) {
				String sTemp = HashMapUtilZZZ.computeDebugString(hmVariableExternal);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": hmVariableExternal \n" + sTemp);
				
				sCounterValueCurrent = (String) hmVariableExternal.get("INPUT_COUNTER_VALUE_CURRENT");							
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": hmVariableExternal ist NULL");				
			}
			
			//Plus alle anderen INPUT - Variablen.			
			HashMapZZZ<String, Object> hmVariableInternal = this.getVariableHashMap();							   			
			if(hmVariable!=null) {	
				//Übernimm den externen key,... um weiterzählen zu könnne.
				hmVariableInternal.put("INPUT_COUNTER_VALUE_CURRENT", sCounterValueCurrent);
				
				//Beispiel mit Verschlüsselung: 
				//Hier werden die Keys für die Variable als Konstante möglich, da sie ihren eigenen KeyPressThread haben
        		//String sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation();
        		//hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CIPHER, sCipher);
				
				//Die Verschiedenen alphanumerischen Zähler haben neben ihrem Namen auch eine "Typenzahl"					
				int iAlphanumericType = ICounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT;
        		String sAlphanumericType = Integer.toString(iAlphanumericType);
        		hmVariableInternal.put("INPUT_COUNTER_TYPE", sAlphanumericType);
        		this.setVariableHashMap(hmVariableInternal);
        	}
			bReturn = true;
		}//end main;	
		return bReturn;
	}
	
	@Override
	public boolean onStartit() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Der Menüpunkt braucht Zugriff auf die übergeordnete Konsole.
			//Gut das die per Singleton erreichbar ist.
	    	IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();
			
			//Der Service, der im Thread ausgeführt wird
			ConsoleServiceMyAlphabetCounterZZZ objCounterService = new ConsoleServiceMyAlphabetCounterZZZ();
			objCounterService.setConsoleController(objConsoleController); //Damit kann er dann auf globale Angaben der Console zugreifen
			                                                              //!!! er kann dann auch darüber auf den ConsoleService zugreifen.
			                                                              //    Dort kann er dann das bisherige Ergebnis ablegen
			//String sCounterKey = (String) hmVariable.get("INPUT_COUNTER_TYPE");
			objCounterService.setMenuPoint(this);    //wichtig, daraus sollte dann der CounterService den Menüpunkt holen. 
			objConsoleController.setMenuPoint(this); //sinnvoll, vielleicht um einen Neustart zu verhindern....
			
			//TODOGOON20260824;//IDEE für jede aufgerufenen Methode 1x den ConsoleServiceThreadZZZ erzeugen und dann in einer HashMap ablegen und wieder holen.
			//Dann wird er nur 1x erstellt und der Thread auch nur 1x gestartet.
			final ConsoleServiceThreadZZZ objConsoleServiceThread_for_counterService = new ConsoleServiceThreadZZZ();
			this.setServiceThread(objConsoleServiceThread_for_counterService);
			
			objConsoleServiceThread_for_counterService.setConsoleController(objConsoleController);
			objConsoleServiceThread_for_counterService.setConsoleServiceObject(objCounterService);
			
			//Den objCounterServiceThread am ConsoleController registrieren.
			//Dann kann er auf die "quit" Anweisung reagieren.
		    objConsoleController.registerForStatusLocalEvent(objConsoleServiceThread_for_counterService);
	  
			//Den neu erstellten Thread starten, er wird dann aus dem 
		    //ConsoleServiceMyAlphabetCounterZZZ - Objekt die Methode startit() aufrufen.
		    Thread t2 = new Thread(objConsoleServiceThread_for_counterService);
		    t2.start();
			
		  	//ACHTUNG... Es wird nicht auf das Ende des Threads gewartet.			    
		    bReturn = true;
		}//end main:
		return bReturn;
	}
}
