package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public interface IThreadableZZZ {
	//Merke Erweiterung um quit() ist: IConsoleControlableZZZ
	public boolean start() throws ExceptionZZZ;
	
	public boolean pause() throws ExceptionZZZ;
	public boolean requestPause(boolean bPause) throws ExceptionZZZ;	
	public boolean isPaused() throws ExceptionZZZ;
	public void isPaused(boolean bPause) throws ExceptionZZZ;
	
	public boolean stop() throws ExceptionZZZ;
	public boolean requestStop(boolean bStop) throws ExceptionZZZ;
	public boolean isStopped() throws ExceptionZZZ;
	public void isStopped(boolean bStop) throws ExceptionZZZ;
	
	public boolean finish() throws ExceptionZZZ;
	public boolean requestFinish(boolean bFinish) throws ExceptionZZZ;
	public boolean isFinished() throws ExceptionZZZ;
	public void isFinished(boolean bFinish) throws ExceptionZZZ;
			
	public long getSleepTime() throws ExceptionZZZ;
	public void setSleepTime(long lSleepTime) throws ExceptionZZZ;	
}
