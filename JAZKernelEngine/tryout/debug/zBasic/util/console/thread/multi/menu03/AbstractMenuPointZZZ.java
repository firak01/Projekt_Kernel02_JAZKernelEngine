package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;

public abstract class AbstractMenuPointZZZ implements IMenuPointZZZ{
	
	HashMapZZZ<String,Object> hmVariable = null;
	IThreadableZZZ objThreadForConsoleService = null;
	
	public AbstractMenuPointZZZ() throws ExceptionZZZ{
		AbstractMenuPointNew_(null);
	}
	
	public AbstractMenuPointZZZ(HashMapZZZ<String,Object> hmVariableInit) throws ExceptionZZZ {
		AbstractMenuPointNew_(hmVariableInit);
	}
	
	private boolean AbstractMenuPointNew_(HashMapZZZ<String,Object> hmVariableInit) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(hmVariableInit!=null) {
				this.hmVariable = hmVariableInit;
			}
			
			
		}//end main;
		return bReturn;
	}
	
	//### GETTER / SETTER
	@Override
	public HashMapZZZ<String,Object> getVariableHashMap() throws ExceptionZZZ {
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapZZZ<String,Object>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		this.hmVariable = hmVariable;
	}
	
	
	@Override
	public IThreadableZZZ getServiceThread() throws ExceptionZZZ {
		return this.objThreadForConsoleService;
	}

	@Override
	public void setServiceThread(IThreadableZZZ objServiceThread) throws ExceptionZZZ {
		this.objThreadForConsoleService = objServiceThread;
	}
	
	
	//############################################################
	
	@Override
	public abstract boolean initit(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ;
	
	@Override
	public abstract boolean onStartit() throws ExceptionZZZ;
	
	@Override
	public boolean onStopit() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			IThreadableZZZ objThread = this.getServiceThread();
			if(objThread!=null) {
				System.out.println("Versuche den aktuellen ServiceThread zu beenden");
				objThread.requestStop();
								
				this.setServiceThread(null);
			}
		    bReturn = true;
		}//end main:
		return bReturn;
	}
}
