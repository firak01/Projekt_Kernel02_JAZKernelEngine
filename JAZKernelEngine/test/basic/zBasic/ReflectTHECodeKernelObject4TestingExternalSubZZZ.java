package basic.zBasic;

import basic.zBasic.reflection.ReflectCodeKernelZZZ;

public class ReflectTHECodeKernelObject4TestingExternalSubZZZ extends AbstractObjectWithFlagZZZ {
	//Konstruktor
	public ReflectTHECodeKernelObject4TestingExternalSubZZZ() throws ExceptionZZZ{
				super();				
	}

	
	public String makeClassMethodExternalCallingString() throws ExceptionZZZ {
		return ReflectCodeKernelZZZ.getClassMethodExternalCallingString();
	}	
	
	public String[] makeClassMethodExternalCallingStack() throws ExceptionZZZ {
		return ReflectCodeKernelZZZ.getCallingStackExternal();
	}
}
