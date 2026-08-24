package debug.zBasic.util.console.thread.multi.menu02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import debug.zBasic.util.console.thread.single.menu.ExampleConsolServiceZZZ;

public class DebugConsoleThreadMultiMenu_MainZZZ {

	public static void main(String[] args) {
		try {		
			//Wenn dieser Thread gestartet wird, wartet er, bis die Konsole beendet ist.
			ExampleComposition_ConsoleAsThreadZZZ objConsoleThread = new ExampleComposition_ConsoleAsThreadZZZ(args);
			objConsoleThread.run(); //Erstellt dann alle notwendigen Objekte
			
			//###########################################
			//Für die Schlussausgabe
			//Hole die notwendigen Objekte, um den abschliessenden Wert auszulesen
			IConsoleControllerZZZ objConsole = objConsoleThread.getConsole();			
			IExampleConsoleServiceZZZ objStartable = (IExampleConsoleServiceZZZ) objConsole.getConsoleServiceObject();
			
			//(ExampleConsolServiceZZZ) 
			int iCount = objStartable.getCounter();
			System.out.println("iCount am Schluss: " + iCount);
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
