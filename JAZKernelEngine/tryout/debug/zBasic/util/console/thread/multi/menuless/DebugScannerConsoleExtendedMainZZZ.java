package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleUserStartableZZZ;

public class DebugScannerConsoleExtendedMainZZZ {

	public static void main(String[] args) {
		try {
			IConsoleUserStartableZZZ objConsoleUser = new ExampleConsoleUserStartableZZZ();
				
			ExampleConsoleZZZ objConsole = ExampleConsoleZZZ.getInstance();				
			objConsole.setConsoleUserObject(objConsoleUser);
			objConsole.start();
		
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
			System.out.println(ez.getMessageLast());
		}
	}

}
