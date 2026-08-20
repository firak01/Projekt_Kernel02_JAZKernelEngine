package debug.zBasic.util.console.thread.multi.menuless;

public interface IConsoleZZZ {
	public IConsoleEnabledZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleEnabledZZZ objConsoleUser) ;	
	
	public KeyPressThreadZZZ getKeyPressThread();
	public ConsoleThreadZZZ getConsoleThread();
}
