package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public interface IThreadEnabledZZZ {
	public boolean start() throws ExceptionZZZ;
	
	public boolean isStopped();
	public void isStopped(boolean bStop);
	public void requestStop();
}
