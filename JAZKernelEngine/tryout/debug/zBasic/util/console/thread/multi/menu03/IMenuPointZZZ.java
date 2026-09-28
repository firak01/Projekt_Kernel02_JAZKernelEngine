package debug.zBasic.util.console.thread.multi.menu03;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;

public interface IMenuPointZZZ {
	public HashMapZZZ<String,Object> getVariableHashMap() throws ExceptionZZZ; //Merke: Die interne HashMap, die eigentlich verwendet wird.
	public void setVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
	
	public IThreadableZZZ getServiceThread() throws ExceptionZZZ;
	public void setServiceThread(IThreadableZZZ objServiceThread) throws ExceptionZZZ;
	
	public boolean initit(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ;  //Merke: Aus der übergebenen HashMap kann der Menüpunkt die Werte ziehen, die ihn interessieren und in die interne HashMap packen.
	public boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ; //Merke: Aus der übergebenen HashMap kann der Menüpunkt die Werte ziehen, die ihn interessieren und in die interne HashMap packen.
	
	
	//Entweder wird darin:
	//a) der Code direkt ausgeführt
	//b) das ServiceObjekt erstellt und die startit() Methode ausgeführt
	//C) das ServiceThreadObject mit dem passenden ServiceObjekt erstellt und der ServiceThread gestartet 
	//   (der dann vom ServiceObject in einer Schleife die startit() Methode aufruft)	
	public boolean onStartit() throws ExceptionZZZ; 
	
	
	//Falls ein extra Thread gestartet wurde, den auch darin beenden
	public boolean onStopit() throws ExceptionZZZ;
}
