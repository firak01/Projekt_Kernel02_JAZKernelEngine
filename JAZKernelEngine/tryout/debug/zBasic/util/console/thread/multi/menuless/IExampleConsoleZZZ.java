package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.util.console.thread.IConsoleUserStartableZZZ;

public interface IExampleConsoleZZZ {
	public IConsoleUserStartableZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleUserStartableZZZ objConsoleUser) ;	
	
	public ExampleKeyPressThreadZZZ getKeyPressThread();
	public ExampleConsoleThreadZZZ getConsoleThread();
}
