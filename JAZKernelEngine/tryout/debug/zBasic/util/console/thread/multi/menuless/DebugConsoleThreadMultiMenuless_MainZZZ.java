package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;

public class DebugConsoleThreadMultiMenuless_MainZZZ {

	public static void main(String[] args) {
		try {
			//Ist kein Thread, darum nur start(), Beispiel mit Compostion als Singleton...	
			ExampleCompositon_ConsoleZZZ objConsole = ExampleCompositon_ConsoleZZZ.getInstance();							
			objConsole.start();
		
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
			System.out.println(ez.getMessageLast());
		}
	}

}
