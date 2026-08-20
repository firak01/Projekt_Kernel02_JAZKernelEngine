package debug.zBasic.util.console.thread.multi.menuless;

public class DebugScannerConsoleExtendedMainZZZ {

	public static void main(String[] args) {
		IConsoleEnabledZZZ objConsoleUser = new DummyConsoleUserZZZ();
				
		ConsoleZZZ objConsole = ConsoleZZZ.getInstance();				
		objConsole.setConsoleUserObject(objConsoleUser);
		objConsole.start();
		
	}

}
