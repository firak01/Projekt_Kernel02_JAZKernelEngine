package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IConsoleUserThreadZZZ extends IThreadEnabledZZZ{
	public IConsoleZZZ getConsole();
	public void setConsole(IConsoleZZZ objConsole);
	
	boolean isInputAllFinished();
	void isInputAllFinished(boolean bInputFinished);
	boolean isOutputAllFinished();
	void isOutputAllFinished(boolean bOutputFinished);	
	
	//###################################################
	boolean startit(HashMapZZZ hm) throws ExceptionZZZ; //Methode wird nach dem Warten auf das Eingabeende ausgeführt.		                                                      //So gekapselt kann sie auch aus einem anderen Thread heraus aufgerufen werden.	
}
