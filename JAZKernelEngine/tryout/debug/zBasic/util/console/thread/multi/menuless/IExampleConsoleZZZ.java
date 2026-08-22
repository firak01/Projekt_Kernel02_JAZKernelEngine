package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.util.console.thread.IConsoleServiceZZZ;

public interface IExampleConsoleZZZ {
	public IConsoleServiceZZZ getConsoleUserObject();
	public void setConsoleUserObject(IConsoleServiceZZZ objConsoleUser) ;	
	
	public ExampleKeyPressThreadZZZ getKeyPressThread();
	public ExampleConsoleThreadZZZ getConsoleThread();
}
