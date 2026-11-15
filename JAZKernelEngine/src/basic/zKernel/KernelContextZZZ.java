package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;


public class  KernelContextZZZ extends AbstractObjectWithExceptionZZZ implements IKernelContextZZZ{
	private String sModuleName = null;
	private String sProgramName = null;
	private Class objClass = null;
	
	public KernelContextZZZ(String sModuleName, String sProgramName){
		this.sModuleName = sModuleName;
		this.sProgramName = sProgramName;
	}
	public KernelContextZZZ(Class objClass) throws ExceptionZZZ{
		if(objClass==null){
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object not passed.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
			this.objClass = objClass;
			
			this.sModuleName = objClass.getName();
			this.sProgramName = objClass.getName();
	}
	
	
	public String getModuleName(){
		return this.sModuleName;		
	}
	public String getProgramName(){
		return this.sProgramName;
	}
	public Class getCallingClass(){
		return this.objClass;
	}
}
