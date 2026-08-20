package debug.zBasic.util.console.thread.single.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleZZZ;

public class DebugConsoleMainZZZ {

	public static void main(String[] args) {
		try {						
			//Wird DebugConsoleZZZ nicht als eigener Thread gestartet, läuft das sofort durch 
			//und gibt das Endergebnis sofort aus.
			DebugConsoleZZZ objConsoleForDebug = new DebugConsoleZZZ();
			objConsoleForDebug.startit(args);
			
			IConsoleZZZ objConsole = objConsoleForDebug.getConsole();
			//IConsoleUserStartableZZZ objStartable = objConsole.getConsoleUserStartableObject();
			DummyConsoleUserStartableZZZ objStartable = (DummyConsoleUserStartableZZZ) objConsole.getConsoleUserStartableObject();
			int iCount = objStartable.getCounter();
			System.out.println("iCount am Schluss: " + iCount);
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
