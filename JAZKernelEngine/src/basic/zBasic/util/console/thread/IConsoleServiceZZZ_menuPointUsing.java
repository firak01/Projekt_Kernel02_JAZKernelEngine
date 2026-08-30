package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointUserZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;

public interface IConsoleServiceZZZ_menuPointUsing extends IConsoleControllerUserZZZ, IMenuPointUserZZZ {
	//Das objMenuPoint-Objekt hat den auszuführenden Code in sich, objMenuPoint.onStartit();
	public boolean startit() throws ExceptionZZZ;
	public boolean startit(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ;
}
