package basic.zBasic.util.system;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelConfigUserZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import custom.zKernel.ILogLevelUserZZZ.LOGLEVEL;

public interface ISystemZZZ extends IListenerObjectFlagZsetZZZ, ISystemEnabledZZZ, IKernelConfigUserZZZ, IPrintLevelUserZZZ{
		
	//############################################################
	// GETTER / SETTER
	//############################################################
	
	//############################################################
	//### Methoden
	//############################################################
	public boolean print(String s, boolean bPrintOutput) throws ExceptionZZZ;
	public boolean print(String s, int iPrintLevel) throws ExceptionZZZ;
	public boolean print(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ;
	public boolean print(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	
	public boolean println(String s, boolean bPrintOutput) throws ExceptionZZZ;
	public boolean println(String s, int iPrintLevel) throws ExceptionZZZ;
	public boolean println(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ;
	public boolean println(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	//............ Siehe ISystemEnabledZZZ
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
