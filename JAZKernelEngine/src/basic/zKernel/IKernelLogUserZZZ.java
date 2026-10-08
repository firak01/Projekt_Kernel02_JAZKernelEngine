package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.KernelLogZZZ;

public interface IKernelLogUserZZZ {
	public abstract KernelLogZZZ getLogObject() throws ExceptionZZZ;
	public abstract void setLogObject(KernelLogZZZ objLog) throws ExceptionZZZ;
	
	//Analog zu ILogZZZ, ILogProtocolPositionZZZ, ILogProtocolZZZ
	//public abstract void logLineDate(String sLog) throws ExceptionZZZ;
	//public abstract void logLineDateWithPosition(String sLog) throws ExceptionZZZ;
}
