package debug.zBasic.util.console.thread.multi.menuless02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.system.SystemSingletonZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ.PRINTLEVEL;
import custom.zKernel.LogSingletonZZZ;
import custom.zKernel.ILogLevelUserZZZ.LOGLEVEL;
import debug.zBasic.util.console.thread.multi.menuless02.ExampleComposition_ConsoleAsThreadZZZ;

/** Weiterentwicklung von ...Menueless01_MainZZZ
 *  orientiert sich an den Klassen mit Menü
 *  
 *  Unterschiede zu ...Menueless01_MainZZZ
 *  ExampleComposition_ConsoleAsThreadZZZ wird verwendet.
 *       D.h. darin liegt auch schon der Thread. 
 *       
 *  ExampleConsoleThreadZZZ 
 *      wird deshalb nicht mehr benötigt
 *            
 *  
 * @author Fritz Lindhauer
 *
 */
public class DebugConsoleThreadMultiMenuless02_MainZZZ {

	public static void main(String[] args) {
		try {
			SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.VERBOSE); //Wenn höher als LogLevel ==> Keine Ausgaben mit dieser Stufe auf die Konsole.	Z.B. LOGLEVEL.DEBUG < PRINTLEVEL.INFO ==> Keine Ausgabe der DEBUG Zeilen		
			LogSingletonZZZ.getInstance().setLogLevelOverall(LOGLEVEL.VERBOSE);      //Wenn nicht anders angegben, ist das Level der Ausgabe entsprechend
						
			//Ist kein Thread, darum nur start(), Beispiel mit Compostion als Singleton...	
			//ExampleComposition_ConsoleZZZ objConsole = ExampleComposition_ConsoleAsThreadZZZ.getInstance();							
			//objConsole.start();
			
			//Ist jetzt ein Thread
			//Wenn dieser Thread gestartet wird, wartet er, bis die Konsole beendet ist.
			ExampleComposition_ConsoleAsThreadZZZ objConsoleThread = new ExampleComposition_ConsoleAsThreadZZZ(args);
			objConsoleThread.run();
			
			
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
			System.out.println(ez.getMessageLast());
		}
	}

}
