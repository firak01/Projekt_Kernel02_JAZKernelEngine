package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;

public interface IConsoleServiceZZZ extends IConsoleControllerUserZZZ {
	public boolean startit(HashMapZZZ<String,String> hmVariable) throws ExceptionZZZ;
	public boolean startit() throws ExceptionZZZ;
}
