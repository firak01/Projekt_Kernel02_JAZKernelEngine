package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalUserZZZ;
import basic.zKernel.status.IStatusLocalBasicUserZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;

public interface IConsoleControllerZZZ extends IThreadableZZZ, IStatusLocalBasicUserZZZ, IThreadWithStatusLocalEnabledZZZ, IConsoleServiceUserZZZ, IKeyPressThreadUserZZZ, IStatusLocalMessageUserZZZ, IEventBrokerStatusLocalUserZZZ {

	public boolean isInputAllFinished();
	public void isInputAllFinished(boolean bInputFinished);
	
	public boolean isOutputAllFinished();
	public void isOutputAllFinished(boolean bOutputFinished);
	
	public boolean isKeyPressThreadFinished();
	public void isKeyPressThreadFinished(boolean bInputThreadFinished); //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.
	
	public boolean isKeyPressThreadRunning();
	public void isKeyPressThreadRunning(boolean bInputThreadRunning); //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.	
	
	public boolean isConsoleUserThreadRunning();
	public void isConsoleUserThreadRunning(boolean bConsoleUserThreadRunning); //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.
		
	public boolean isConsoleUserThreadFinished();
	public void isConsoleUserThreadFinished(boolean bConsoleUserThreadFinished); //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.
	
	public HashMapZZZ<String, Object> getVariableHashMap();
	public void setVariableHashMap(HashMapZZZ<String, Object> hmVariable);
}
