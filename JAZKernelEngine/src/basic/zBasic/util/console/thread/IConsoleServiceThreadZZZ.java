package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IConsoleServiceThreadZZZ extends IThreadableZZZ, IConsoleControllerUserZZZ, IConsoleServiceZZZ{
	boolean isInputAllFinished();
	void isInputAllFinished(boolean bInputFinished);
	boolean isOutputAllFinished();
	void isOutputAllFinished(boolean bOutputFinished);	
}
