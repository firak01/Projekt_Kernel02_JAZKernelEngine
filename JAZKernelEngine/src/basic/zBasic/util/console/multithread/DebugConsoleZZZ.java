package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadDecryptZZZ;
import basic.zBasic.util.system.Syso;


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
