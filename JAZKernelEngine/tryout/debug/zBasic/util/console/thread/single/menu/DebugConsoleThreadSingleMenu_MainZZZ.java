package debug.zBasic.util.console.thread.single.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class DebugConsoleThreadSingleMenu_MainZZZ {

	public static void main(String[] args) {
		try {						
			//Wird DebugConsoleZZZ nicht als eigener Thread gestartet, läuft das sofort durch 
			//und gibt das Endergebnis sofort aus.
			ExampleComposition_ConsoleZZZ objConsoleForDebug = new ExampleComposition_ConsoleZZZ();
			objConsoleForDebug.startit(args);
			
			//Für die Schlussausgabe
			//Hole die notwendigen Objekte, um den abschliessenden Wert auszulesen			
			IConsoleControllerZZZ objConsole = objConsoleForDebug.getConsole();			
			ExampleConsoleServiceZZZ objStartable = (ExampleConsoleServiceZZZ) objConsole.getConsoleServiceObject();
			int iCount = objStartable.getCounter();
			System.out.println(StringZZZ.repeat("-", 20));
			System.out.println("\nProgramm beendet.\niCount am Schluss: " + iCount + "\n");
			System.out.println("... aber ohne Thread des ExampleConsoleZZZ läuft der Code sofort duch.");
			System.out.println(StringZZZ.repeat("-", 20));
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
