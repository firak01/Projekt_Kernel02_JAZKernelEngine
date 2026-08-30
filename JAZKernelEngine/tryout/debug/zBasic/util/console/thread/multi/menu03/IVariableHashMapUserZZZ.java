	package debug.zBasic.util.console.thread.multi.menu03;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IVariableHashMapUserZZZ {
	public HashMapZZZ<String,Object> getVariableHashMap() throws ExceptionZZZ;
	public void setVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
}
