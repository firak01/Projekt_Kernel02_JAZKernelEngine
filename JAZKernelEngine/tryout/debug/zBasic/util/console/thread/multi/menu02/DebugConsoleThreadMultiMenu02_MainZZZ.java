package debug.zBasic.util.console.thread.multi.menu02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import debug.zBasic.util.console.thread.single.menu.ExampleConsolServiceZZZ;

public class DebugConsoleThreadMultiMenu02_MainZZZ {

	public static void main(String[] args) {
		try {		
			//Wenn dieser Thread gestartet wird, wartet er, bis die Konsole beendet ist.
			ExampleComposition_ConsoleAsThreadZZZ objConsoleThread = new ExampleComposition_ConsoleAsThreadZZZ(args);
			
			//Erstellt darin alle notwendigen Objekte. Endlosschleife, bis die Console beendet wird. 
			//... Beendet wird die Konsole durch: }while(!this.getConsole().isStopped());
			objConsoleThread.run(); 
			
			//###########################################
			//Für die Schlussausgabe
			//Hole die notwendigen Objekte, um den abschliessenden Wert auszulesen
			IConsoleControllerZZZ objConsole = objConsoleThread.getConsole();			
			IExampleConsoleServiceZZZ objStartable = (IExampleConsoleServiceZZZ) objConsole.getConsoleServiceObject();
			
			//(ExampleConsolServiceZZZ) 
			int iCount = objStartable.getCounter();
			System.out.println("iCount am Schluss: " + iCount);
		} catch (ExceptionZZZ ez) {
			System.out.println(ez.getMessageLast());
			ez.printStackTrace();
		}
		
	}

}
