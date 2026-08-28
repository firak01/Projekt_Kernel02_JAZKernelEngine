package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public interface IMenuPointZZZ {
	public HashMap<String,String> getVariableHashMap() throws ExceptionZZZ;
	public void setVariableHashMap(HashMap<String,String> hmVariable) throws ExceptionZZZ;
	
	public boolean initit() throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die
	public boolean initit(HashMap<String,String> hmVariable) throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die
	
	public boolean onStartit() throws ExceptionZZZ;
}
