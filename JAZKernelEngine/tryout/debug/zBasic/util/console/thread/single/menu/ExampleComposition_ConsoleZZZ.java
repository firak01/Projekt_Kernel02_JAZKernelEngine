package debug.zBasic.util.console.thread.single.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenuableZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadDefaultZZZ;


public class ExampleComposition_ConsoleZZZ {
	protected IConsoleControllerZZZ objConsole = null;

	public IConsoleControllerZZZ getConsole() throws ExceptionZZZ {
		return this.objConsole;
	}
	public void setConsole(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		this.objConsole = objConsole;
	}
	
	public void startit(String[] args) throws ExceptionZZZ {								
		IConsoleControllerZZZ objConsole = ConsoleControllerZZZ.getInstance();	
		this.setConsole(objConsole);
		
		IKeyPressThreadMenuableZZZ objKeyPressThread = new ExampleKeyPressThreadZZZ(objConsole, 100);			
		objConsole.setKeyPressThread(objKeyPressThread);
					
		ExampleConsolServiceZZZ objConsoleService = new ExampleConsolServiceZZZ(objConsole);			
		objConsole.setConsoleServiceObject(objConsoleService);
		objConsole.start();		
	}

}
