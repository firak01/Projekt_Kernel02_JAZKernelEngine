package debug.zBasic.util.console.thread.multi.menu;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;

public interface IExampleConsoleServiceZZZ extends IConsoleServiceZZZ {	
	public int getCounter() throws ExceptionZZZ;
	public void setCounter(int iCounter) throws ExceptionZZZ;
}
