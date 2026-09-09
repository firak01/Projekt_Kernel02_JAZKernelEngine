package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalUserZZZ;
import basic.zKernel.status.IStatusLocalBasicUserZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointUserZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;

public interface IConsoleControllerZZZ extends IThreadableZZZ, IConsoleControlableZZZ, IStatusLocalBasicUserZZZ, IThreadWithStatusLocalEnabledZZZ, IConsoleServiceUserZZZ, IMenuPointUserZZZ, IVariableHashMapUserZZZ, IKeyPressThreadUserZZZ, IStatusLocalMessageUserZZZ, IEventBrokerStatusLocalUserZZZ {

	public boolean isInputAllFinished() throws ExceptionZZZ;
	public void isInputAllFinished(boolean bInputFinished)throws ExceptionZZZ;
	
	public boolean isKeyPressThreadRunning() throws ExceptionZZZ;
	public void isKeyPressThreadRunning(boolean bInputThreadRunning) throws ExceptionZZZ; //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.	

	public boolean isKeyPressThreadFinished() throws ExceptionZZZ;
	public void isKeyPressThreadFinished(boolean bInputThreadFinished) throws ExceptionZZZ; //setzen, wenn die Eingabe im KeyPressThread vorerst abgeschlossen ist.

	public boolean isConsoleUserThreadRunning() throws ExceptionZZZ;
	public void isConsoleUserThreadRunning(boolean bConsoleUserThreadRunning) throws ExceptionZZZ; //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.
		
	public boolean isConsoleUserThreadFinished() throws ExceptionZZZ;
	public void isConsoleUserThreadFinished(boolean bConsoleUserThreadFinished) throws ExceptionZZZ; //setzen, wenn der gestartete ConsolenUserThread beendet wurde. Dann kann eine neue Eingabe gestartet werden.

}
