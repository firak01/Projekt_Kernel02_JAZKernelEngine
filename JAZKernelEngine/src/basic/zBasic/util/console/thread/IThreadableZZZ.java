package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public interface IThreadableZZZ {
	public boolean start() throws ExceptionZZZ;
	
	public boolean isStopped() throws ExceptionZZZ;
	public void isStopped(boolean bStop) throws ExceptionZZZ;
	public void requestStop() throws ExceptionZZZ;
	
	public long getSleepTime() throws ExceptionZZZ;
	public void setSleepTime(long lSleepTime) throws ExceptionZZZ;	
}
