package debug.zBasic.util.console.thread.multi.menu03;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ_menuPointUsing;

public interface IExampleConsoleServiceZZZ extends IConsoleServiceZZZ, IConsoleServiceZZZ_menuPointUsing {	
	public int getCounter() throws ExceptionZZZ;
	public void setCounter(int iCounter) throws ExceptionZZZ;
}
