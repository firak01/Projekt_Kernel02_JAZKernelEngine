package debug.zBasic.util.console.thread.multi.menuless02;

import java.util.Scanner;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadMenulessZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ_menuPointUsing;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zKernel.Log;
import debug.zBasic.util.console.thread.multi.menu03.ConsoleServiceThreadZZZ;


public class ExampleKeyPressThreadZZZ<T> extends AbstractKeyPressThreadMenulessZZZ<T> {
	private static final long serialVersionUID = -5471924402325569415L;
		
	IThreadableZZZ objThreadForConsoleService = null; //ohne Menupoint, hier den ConsoleService-Thread definieren.
	//boolean bStop;
	
    //Method that gets called when the object is instantiated
    public ExampleKeyPressThreadZZZ() throws ExceptionZZZ {
    	super();
    }
    
    public ExampleKeyPressThreadZZZ(long lSleepTime) throws ExceptionZZZ {
    	super(lSleepTime);    	
    }
    
    @Override
	public void makeMenuMain() throws ExceptionZZZ {
    	System.out.println("Eingaben: + - oder Q");
    	try {
			Thread.sleep(this.getSleepTime());					
		} catch (InterruptedException e) {
			System.out.println("KeyPressThread: 1. Wait Error");
			e.printStackTrace();
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;	
		} 
		System.out.println("Warte auf Eingabe...");
	}
   
    @Override
    public boolean start() throws ExceptionZZZ {
    	boolean bReturn = false;
    	main:{
    		//irgendwie auch ohne menuepoint die Variablen HashMap initialisieren
    		HashMapZZZ hmVariable = this.getVariableHashMap();
    		initit(hmVariable);
    		
    		
	    	this.makeMenuMain();
	    	
	    	String sLog="";
	    	int iDebugCounterServiceThread = 0; boolean bConsoleServiceThreadStarted=false;
	        while(true){	
	        	IConsoleServiceZZZ objConsoleService = this.getConsoleController().getConsoleServiceObject();		         
		        if(objConsoleService!=null && !bConsoleServiceThreadStarted) {
	
		        	//Der Thread kann loslaufen, ...
			        ConsoleServiceThreadZZZ objConsoleServiceThread_for_counterService = (ConsoleServiceThreadZZZ) this.getServiceThread();
			        this.setServiceThread(objConsoleServiceThread_for_counterService);
			         
			        objConsoleServiceThread_for_counterService.setConsoleServiceObject(objConsoleService);   
			         
					//Den neu erstellten Thread starten, er wird dann aus dem 
					//ConsoleServiceMyAlphabetCounterZZZ - Objekt die Methode startit() aufrufen.
					Thread t2 = new Thread(objConsoleServiceThread_for_counterService);
					t2.start();
					
					//ACHTUNG... Es wird nicht auf das Ende des Threads gewartet.			    
					bConsoleServiceThreadStarted = true; //damit dann den Start eines weiteren Threads unterbinden.
						
					//... ansonsten wird auf Eingabe gewartet.
		         }
	        	
	        	
		    	 String input = inputReader.next();
		    	 try {
	            	System.out.println("Nach der Eingabe von '"+input+"'");
	            	Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: 2. Wait Error");
					e.printStackTrace();
				}
		         if (input.equals("-")) { //also Minus mach den Thread langsamer... lSleeptime erhöhen
		         	//System.out.println("Pressed -");
		        	long lSleepTime = this.getSleepTime();
		        	lSleepTime+=100; 
		         	try {
						this.setSleepTime(lSleepTime);
					} catch (ExceptionZZZ e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}               
		         }
		         if (input.equals("+")) {  //also Plus mach den Thread schneller... lSleeptime verringern
		         	//System.out.println("Pressed +");
		        	long lSleepTime = this.getSleepTime();
		         	lSleepTime-=100; 
		         	this.setSleepTime(lSleepTime);    
		         }
		         if (input.equalsIgnoreCase("Q")) {
		            this.quit();
		         	break; // quit KeyPressThread
		         }
		         
		         
		         
					
				
	        }
	        bReturn = true;
    	}//end main:
    	return bReturn;
    }
    
//    public boolean isStopped() {
//		return this.bStop;
//	}
//	public void isStopped(boolean bStop) {
//		this.bStop = bStop;
//	}
//	public void requestStop() {
//		this.isStopped(true);
//	}
	
	
	@Override
	public boolean initit(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ {
		//In dem konkreten Fall nix zu tun....
		
		boolean bReturn = false;
		main:{
			//#################################################
			//Was sonst im Menupoint gemacht wird, hier tun
			final ConsoleServiceThreadZZZ objConsoleServiceThread_for_counterService = new ConsoleServiceThreadZZZ();
			this.setServiceThread(objConsoleServiceThread_for_counterService);
			
			IConsoleControllerZZZ objConsoleController = this.getConsoleController();
			objConsoleServiceThread_for_counterService.setConsoleController(objConsoleController);
			
			
			//Den objCounterServiceThread am ConsoleController registrieren.
			//Dann kann er auf die "quit" Anweisung reagieren.
		    objConsoleController.registerForStatusLocalEvent(objConsoleServiceThread_for_counterService);
	  		   
		    //Den objConsoleController am objCounterServiceThread registrieren. (implementiert IListenerObjectStatusLocalZZZ)
		    //Dann kann er auf den "stopped" Status reagieren.
		    objConsoleServiceThread_for_counterService.registerForStatusLocalEvent(objConsoleController);
		 			
			//##################################################
			//Die Hier übergebene Methode wird in ... .startit() ausgelesen.
			//Plus alle anderen INPUT - Variablen.
			HashMapZZZ<String, Object> hmVariableInternal = this.getVariableHashMap();							   
			HashMapZZZ<String, Object> hmVariable = HashMapUtilZZZ.mergeMapsZZZ_LastKeyRemains(hmVariableInternal, hmVariableExternal);

			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			if(StringZZZ.isEmpty(sCallingMethod)) break main;
			
			switch(sCallingMethod){
//				case "ascii":
//					bReturn = initAscii_(hmVariable);
//					break;
				case "process1":
					bReturn = initProcess1_(hmVariable);
					break;
//				case "countAlphanumeric":
//					bReturn = initCountAlphanumeric_(hmVariable);
//					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
					throw ez;
			}
			//bReturn = true;
		}//end main:
		return bReturn;
	}

	//#########################################################################
	private boolean initProcess1_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Hier noch zusätzliche Input Variablen übergebbar.
			//Beispiel:
			//if(hmVariable!=null) {
        	//	String sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation();
        	//	hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CIPHER, sCipher);
        	//}				
			bReturn = true;
		}//end main;	
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectWithStatusLocalZZZ#queryOfferStatusLocalCustom()
	 */
	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		return true;
	}

	@Override
	public IThreadableZZZ getServiceThread() throws ExceptionZZZ {
		return this.objThreadForConsoleService;
	}

	@Override
	public void setServiceThread(IThreadableZZZ objServiceThread) throws ExceptionZZZ {
		this.objThreadForConsoleService = objServiceThread;
	}
	
	
}

