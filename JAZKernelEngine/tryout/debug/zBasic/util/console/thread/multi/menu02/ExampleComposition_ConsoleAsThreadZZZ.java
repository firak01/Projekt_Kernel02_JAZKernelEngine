package debug.zBasic.util.console.thread.multi.menu02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenuableZZZ;

public class ExampleComposition_ConsoleAsThreadZZZ implements Runnable{
	protected IConsoleControllerZZZ objConsoleController = null;
	
	public ExampleComposition_ConsoleAsThreadZZZ() throws ExceptionZZZ{		
	}
	
	public ExampleComposition_ConsoleAsThreadZZZ(String[] args) throws ExceptionZZZ{		
	}
	
	public IConsoleControllerZZZ getConsole() throws ExceptionZZZ {
		return this.objConsoleController;
	}
	public void setConsoleController(IConsoleControllerZZZ objConsoleController) throws ExceptionZZZ {
		this.objConsoleController = objConsoleController;
	}
				
	@Override
	public void run() {
		try {
			start();
		} catch (ExceptionZZZ ez) {
			System.out.println(ez.getMessageLast());
			ez.printStackTrace();
		}
	}
	
	//@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		try {
		main:{
			this.startit();
			
			this.getConsole().isConsoleUserThreadRunning(true);										
			do {
				 try {				 
					 Thread.sleep(200);
					 //System.out.println("ExampleComposition_ConsoleAsThreadZZZ wartet auf fertige Konsoleneingabe");
				} catch (InterruptedException e) {
					System.out.println("02 ExampleComposition_ConsoleAsThreadZZZ: Wait Error");
					e.printStackTrace();
					ExceptionZZZ ez = new ExceptionZZZ(e);
					throw ez;
				}
			}while(!this.getConsole().isStopped());			
				
//CODE 
			// Im ConsoleThread wird dann das ausgewertet;
//			if(this.isStopped()) break main;
//			if(this.isOutputAllFinished()) break main; //wenn Z.B. schon ein Menuepunkt ausgefuehrt worden ist. Z.B. eine einfache ASCII-Tabelle ausgegeben wurde.
//			if(!this.isInputAllFinished()) break main; 
			
			//Warten auf die fertige Eingabe.			
			//if(!this.getConsole().isKeyPressThreadFinished()) break main;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: WARTE AUF FERTIGE KONSOLENEINGABE ######");	
			
			
//CODE 			
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
			//}//end while !isStopped
		}//end main:
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
		}
		this.getConsole().isConsoleUserThreadFinished(true);
		return bReturn;
	}
	
	public void startit() throws ExceptionZZZ {								
		IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();	
		this.setConsoleController(objConsoleController);
		
		//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
		//       Darum muss man alles in dem KeyPressThread erledigen
		IKeyPressThreadMenuableZZZ objKeyPressThread = new ExampleKeyPressThreadZZZ(objConsoleController, 100);
		//Der Key-Thread hat keinen Status, also nicht: objKeyPressThread.setFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED, true);
		objConsoleController.setKeyPressThread(objKeyPressThread);
		//Nein, keypressthread ist selber kein Listener: objConsoleController.registerForStatusLocalEvent(objKeyPressThread);
		
		
		IConsoleServiceZZZ objConsoleService = new ExampleConsoleServiceZZZ(objConsoleController);			
		objConsoleController.setConsoleServiceObject(objConsoleService);
		
		//Merke: Beim Ausführen von Aktionen 	erstellt der ExampleConsoleService ggfs. neue Threads, die dann am ConsoleController registriert werden.
		//       Dann soll beim Beenden des ConsoleControllers ein entsprechendes Ereignis an alle registrierten Threads geworfen werden.
		objConsoleController.start(); 
		
	}
}
