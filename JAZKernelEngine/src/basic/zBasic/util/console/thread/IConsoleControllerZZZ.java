package basic.zBasic.util.console.thread;

import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IConsoleControllerZZZ extends IThreadEnabledZZZ, IConsoleServiceUserZZZ, IKeyPressThreadUserZZZ {
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);
	
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
