package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadDecryptZZZ;
import basic.zBasic.util.system.Syso;

public class DebugConsoleZZZ {

	public static void main(String[] args) {
		try {						
			IConsoleZZZ objConsole = ConsoleThreadZZZ.getInstance();	
			
			//Merke: Hier ohne Menü fehlt so etwas wie...
			//TODOGOON20260817;//Mache hier ein mini-Menü
			//IKeyPressThreadZZZ objKeyPressThread = new ConsoleUserThreadZZZ(objConsole);
			IKeyPressThreadZZZ objKeyPressThread = new KeyPressThreadDefaultZZZ(objConsole, 100);
			
			objConsole.setKeyPressThread(objKeyPressThread);
						
			//IConsoleUserStartableZZZ objConsoleUser = new DummyConsoleUserStartableZZZ(objConsole);			
			DummyConsoleUserStartableZZZ objConsoleUser = new DummyConsoleUserStartableZZZ(objConsole);			
			objConsole.setConsoleUserStartableObject(objConsoleUser);
			objConsole.start();
			
			Syso.println("Counter: " + objConsoleUser.getCounter());
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
