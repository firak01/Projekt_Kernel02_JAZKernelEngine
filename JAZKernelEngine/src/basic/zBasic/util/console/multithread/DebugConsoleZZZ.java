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
			
			TODOGOON20260817;//Der Zähler unten soll zur Demonstration herauskommen
			                 //KeyPressThreadDefault ruft nicht objConsoleUserStartable.startit(...) auf.
			IKeyPressThreadZZZ objKeyPressThread = new KeyPressThreadDefaultZZZ(objConsole, 100);
			
			objConsole.setKeyPressThread(objKeyPressThread);
						
			//IConsoleUserStartableZZZ objConsoleUser = new DummyConsoleUserStartableZZZ(objConsole);			
			DummyConsoleUserStartableZZZ objConsoleUserStartable = new DummyConsoleUserStartableZZZ(objConsole);			
			objConsole.setConsoleUserStartableObject(objConsoleUserStartable);
			objConsole.start();
			
			Syso.println("Counter: " + objConsoleUserStartable.getCounter());
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
