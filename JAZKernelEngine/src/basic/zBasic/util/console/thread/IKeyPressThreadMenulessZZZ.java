package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointUserZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;

public interface IKeyPressThreadMenulessZZZ extends IKeyPressThreadZZZ, IVariableHashMapUserZZZ {
    public boolean initit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die
    
    //Ohne Menüpunkt, wird alles im KeyPressThread bereitgestellt.
    public IThreadableZZZ getServiceThread() throws ExceptionZZZ;
	public void setServiceThread(IThreadableZZZ objServiceThread) throws ExceptionZZZ;	
}
