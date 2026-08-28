package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;

public abstract class AbstractMenuPointZZZ implements IMenuPointZZZ{
	
	HashMap<String,String> hmVariable = null;
	
	public AbstractMenuPointZZZ() throws ExceptionZZZ{
		AbstractMenuPointNew_(null);
	}
	
	public AbstractMenuPointZZZ(HashMap<String,String> hmVariableInit) throws ExceptionZZZ {
		AbstractMenuPointNew_(hmVariableInit);
	}
	
	private boolean AbstractMenuPointNew_(HashMap<String,String> hmVariableInit) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(hmVariableInit!=null) {
				this.hmVariable = hmVariableInit;
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	@Override
	public HashMap<String,String> getVariableHashMap() throws ExceptionZZZ {
		if(this.hmVariable==null) {
			this.hmVariable = new HashMap<String,String>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariableHashMap(HashMap<String,String> hmVariable) throws ExceptionZZZ {
		this.hmVariable = hmVariable;
	}
	
	@Override
	public abstract boolean initit() throws ExceptionZZZ;
	
	@Override
	public boolean initit(HashMap<String,String> hmVariableExternal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.initit();
			
			LinkedHashMap<String,String> hm = (LinkedHashMap<String, String>) HashMapUtilZZZ.mergeMaps(this.getVariableHashMap(), hmVariableExternal);
			this.setVariableHashMap(hm);						
		}//end main
		return bReturn;
	}
	
	@Override
	public abstract boolean onStartit() throws ExceptionZZZ;
	
}
