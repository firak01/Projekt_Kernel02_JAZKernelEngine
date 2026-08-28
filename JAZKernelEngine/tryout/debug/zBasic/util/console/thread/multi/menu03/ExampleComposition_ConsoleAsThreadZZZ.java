package debug.zBasic.util.console.thread.multi.menu03;

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
					System.out.println("ExampleComposition_ConsoleAsThreadZZZ: Wait Error");
					e.printStackTrace();
					ExceptionZZZ ez = new ExceptionZZZ(e);
					throw ez;
				}
			}while(!this.getConsole().isStopped());
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
		objConsoleController.setKeyPressThread(objKeyPressThread);
			
		IConsoleServiceZZZ objConsoleService = new ExampleConsoleServiceZZZ(objConsoleController);			
		objConsoleController.setConsoleServiceObject(objConsoleService);
		
		//Merke: Beim Ausführen von Aktionen 	erstellt der ExampleConsoleService ggfs. neue Threads, die dann am ConsoleController registriert werden.
		//       Dann soll beim Beenden des ConsoleControllers ein entsprechendes Ereignis an alle registrierten Threads geworfen werden.
		objConsoleController.start(); 
		
	}
}
