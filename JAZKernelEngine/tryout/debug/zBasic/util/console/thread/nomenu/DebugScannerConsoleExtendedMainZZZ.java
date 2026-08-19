package debug.zBasic.util.console.thread.nomenu;

import basic.zBasic.util.console.multithread.extended.ConsoleZZZ;
import basic.zBasic.util.console.multithread.extended.IConsoleEnabledZZZ;

public class DebugScannerConsoleExtendedMainZZZ {

	public static void main(String[] args) {
		IConsoleEnabledZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		ConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
