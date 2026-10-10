package debug.zBasic.util.console.thread.multi.menu03;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.KeyPressUtilZZZ;
import basic.zBasic.util.counter.ICounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.counter.ICounterStringZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zKernel.Log;

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
			String sCounterValueCurrent = null; String sLog = null;
			if(hmVariableExternal!=null) {
				
				//Hole aus der externen HashMap nur die Werte, die interessieren
				String sTemp = HashMapUtilZZZ.computeDebugString(hmVariableExternal);
				sLog = ReflectCodeZZZ.getPositionCurrent() + ": hmVariableExternal \n" + sTemp;
				//System.out.println(sLog);
				Log.writeDebug(sLog);
				sCounterValueCurrent = (String) hmVariableExternal.get("OUTPUT_COUNTER_VALUE_CURRENT");							
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent() + ": hmVariableExternal ist NULL";
				//System.out.println(sLog);
				Log.writeDebug(sLog);
			}
			
			//Plus alle anderen INPUT - Variablen.			
			HashMapZZZ<String, Object> hmVariableInternal = this.getVariableHashMap();							   			
			if(hmVariableInternal!=null) {	
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
	public boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Hole aus der externen HashMap nur die Werte, die interessieren
			if(hmVariableExternal!=null) {
				
				//Hole aus der externen HashMap nur die Werte, die interessieren
				//Hier nix...					
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": hmVariableExternal ist NULL");				
			}
			

//    		//######################################################################
//        	//### Eingabe des zu verarbeitenden/hier: entschluesslenden Textes
//        	//Merke: Verschluesselte Beispiele kann man sich mit EncryptConsoleMainZZZ erstellen.
//			
//    		//Merke Fehler abfangen, wie z.B.: Exception in thread "Thread-1" java.lang.IllegalArgumentException: Illegal character 'ß'
//			//Das passiert beim Aufruf der Verschlüsselung selbst.
//        	System.out.println("Geben Sie den zu entschluesselnden Text als String ein");
//        	String sInput = this.getInputReader().nextLine();
//        	if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_TEXT_ENCRYPTED, sInput);
//        	if(StringZZZ.isEmpty(sInput)) {
//        		this.cancelToMenue(hmVariable);
//        	}
			
			//######################################################################
        	//### Eingabe des maximalen Zählerwerts
			String sQuestion = "Geben Sie den maximalen Zählerwert als Zahl ein, ENTER für unbegrenzt:";
        	
        	//Der Menüpunkt braucht Zugriff auf die übergeordnete Konsole.
			//Gut das die per Singleton erreichbar ist.
	    	IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();
	    	IKeyPressThreadZZZ objKeyPressThread = objConsoleController.getKeyPressThread();
	    	Scanner inputReader = objKeyPressThread.getInputReader();

	    	//String sInput = KeyPressUtilZZZ.makeInputNumericCancel(inputReader, sQuestion);
			boolean bGoon=false; String sInput = null;
			do {
		    	sInput = KeyPressUtilZZZ.makeInputNumericCancel(inputReader, sQuestion, true);
		    	if(StringZZZ.isEmptyTrimmed(sInput)) {
		    		//Wert ist unendlich...
		    		bGoon = true;
		    		bReturn = true;
		    	} else if (StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
		    		//CANCEL
		    		bGoon = true;
		    		bReturn = false;
		    	} else if(StringZZZ.isNumeric(sInput)) {
		    		HashMapZZZ<String, Object> hmVariableInternal = this.getVariableHashMap();							   			
					if(hmVariableInternal!=null) {	
						hmVariableInternal.put("INPUT_COUNTER_VALUE_MAX", sInput);
						this.setVariableHashMap(hmVariableInternal);
					}
					bGoon = true;
			    	bReturn = true;	
		    	} else {	    		    		
		    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
	        		System.out.println("Hier ungueltige Eingabe. Vielleicht erst zum Menü mit 'm' zurückgehen?");			                		
	            	bGoon=false;	
		    	}
			}while(!bGoon);

		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean onStartit() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Der Menüpunkt braucht Zugriff auf die übergeordnete Konsole.
			//Gut das die per Singleton erreichbar ist.
	    	IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();
			
			//Der Service, der im Thread wiederholt ausgeführt wird
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
	  		   
		    //Den objConsoleController am objCounterServiceThread registrieren. (implementiert IListenerObjectStatusLocalZZZ)
		    //Dann kann er auf den "stopped" Status reagieren.
		    objConsoleServiceThread_for_counterService.registerForStatusLocalEvent(objConsoleController);
		    
		    
			//Den neu erstellten Thread starten, er wird dann aus dem 
		    //ConsoleServiceMyAlphabetCounterZZZ - Objekt die Methode startit() aufrufen.
		    Thread t2 = new Thread(objConsoleServiceThread_for_counterService);
		    t2.start();
			
		  	//ACHTUNG... Es wird nicht auf das Ende des Threads gewartet.			    
		    bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	@Override
	public boolean onStopit() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = super.onStopit();
			if(!bReturn) break main;
				
			//Den aktuellen Wert an den Controller zurückschreiben.
			final ConsoleServiceThreadZZZ objConsoleServiceThread_for_counterService = (ConsoleServiceThreadZZZ) this.getServiceThread();
			ConsoleServiceMyAlphabetCounterZZZ objConsoleService = (ConsoleServiceMyAlphabetCounterZZZ) objConsoleServiceThread_for_counterService.getConsoleServiceObject();
			HashMapZZZ<String,Object> hmVariable = this.getVariableHashMap(); //objConsoleService.getVariableHashMap();
			
			IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();
			objConsoleController.addVariableHashMap(hmVariable);
			
			ICounterStringZZZ objCounter = objConsoleService.getCounter();
			int iCount = objCounter.getValueCurrent();			
			System.out.println(StringZZZ.repeat("-", 20));
			System.out.println("\nExampleMenuPoint_2 gestoppt.\niCount soweit: " + iCount + "\n");
			System.out.println(StringZZZ.repeat("-", 20));
		}//end main:
		return bReturn;
	}
	
}
