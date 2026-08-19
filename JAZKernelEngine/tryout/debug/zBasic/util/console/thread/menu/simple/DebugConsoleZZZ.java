package debug.zBasic.util.console.thread.menu.simple;

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
		
		IKeyPressThreadZZZ objKeyPressThread = new KeyPressThreadDefaultZZZ(objConsole, 100);			
		objConsole.setKeyPressThread(objKeyPressThread);
					
		DummyConsoleUserStartableZZZ objConsoleUserStartable = new DummyConsoleUserStartableZZZ(objConsole);			
		objConsole.setConsoleUserStartableObject(objConsoleUserStartable);
		objConsole.start();		
	}

}
