package debug.zBasic.util.console.thread.single.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.ConsoleThreadZZZ;
import basic.zBasic.util.console.thread.IConsoleZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadDefaultZZZ;


public class DebugConsoleZZZ {
	protected IConsoleZZZ objConsole = null;

	public IConsoleZZZ getConsole() throws ExceptionZZZ {
		return this.objConsole;
	}
	public void setConsole(IConsoleZZZ objConsole) throws ExceptionZZZ {
		this.objConsole = objConsole;
	}
	
	public void startit(String[] args) throws ExceptionZZZ {								
		IConsoleZZZ objConsole = ConsoleThreadZZZ.getInstance();	
		this.setConsole(objConsole);
		
		IKeyPressThreadZZZ objKeyPressThread = new ExampleKeyPressThreadZZZ(objConsole, 100);			
		objConsole.setKeyPressThread(objKeyPressThread);
					
		ExampleConsoleUserStartableZZZ objConsoleUserStartable = new ExampleConsoleUserStartableZZZ(objConsole);			
		objConsole.setConsoleUserStartableObject(objConsoleUserStartable);
		objConsole.start();		
	}

}
