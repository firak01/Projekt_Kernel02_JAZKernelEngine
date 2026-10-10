package debug.zBasic.util.console.thread.multi.menu03;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ.PRINTLEVEL;
import basic.zBasic.util.system.SystemSingletonZZZ;
import custom.zKernel.LogSingletonZZZ;
import custom.zKernel.ILogLevelUserZZZ.LOGLEVEL;
import debug.zBasic.util.console.thread.single.menu.ExampleConsoleServiceZZZ;

public class DebugConsoleThreadMultiMenu03_MainZZZ {

	public static void main(String[] args) {
		try {
			SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG); //Wenn höher als LogLevel ==> Keine Ausgaben mit dieser Stufe auf die Konsole.	Z.B. PRINTLEVEL.INFO > LOGLEVEL.DEBUG ==> Keine Ausgabe		
			LogSingletonZZZ.getInstance().setLogLevelOverall(LOGLEVEL.DEBUG);       //Wenn nicht anders angegben, ist das Level der Ausgabe entsprechend
			
			
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

			System.out.println(StringZZZ.repeat("-", 20));
			System.out.println("\nProgramm beendet.\niCount am Schluss: " + iCount + "\n");
			System.out.println(StringZZZ.repeat("-", 20));
		} catch (ExceptionZZZ ez) {
			System.out.println(ez.getMessageLast());
			ez.printStackTrace();
		}
		
	}

}
