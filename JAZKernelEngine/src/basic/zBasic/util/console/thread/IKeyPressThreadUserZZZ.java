package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

//### Damit wird der konkrete KeyPressThread von anderen KeyPressThreads aus nutzbar, ohne den Code zu verdoppeln
public interface IKeyPressThreadUserZZZ {
	public IKeyPressThreadZZZ getKeyPressThread() throws ExceptionZZZ;
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ;
}
