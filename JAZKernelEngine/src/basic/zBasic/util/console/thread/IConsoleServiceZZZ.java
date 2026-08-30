package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;

public interface IConsoleServiceZZZ extends IConsoleControllerUserZZZ, IVariableHashMapUserZZZ {
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
	public boolean startit() throws ExceptionZZZ;
}
