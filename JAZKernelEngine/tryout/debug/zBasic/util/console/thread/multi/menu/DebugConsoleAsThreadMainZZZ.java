package debug.zBasic.util.console.thread.multi.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleZZZ;
import debug.zBasic.util.console.thread.single.menu.DummyConsoleUserStartableZZZ;

public class DebugConsoleAsThreadMainZZZ {

	public static void main(String[] args) {
		try {		
			//Wenn dieser Thread gestartet wird, wartet er, bis die Konsole beendet ist.
			DebugConsoleAsThreadZZZ objConsoleForDebug = new DebugConsoleAsThreadZZZ(args);
			objConsoleForDebug.run();
			
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
