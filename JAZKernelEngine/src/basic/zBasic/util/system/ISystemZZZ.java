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
	public void print(String s, boolean bPrintOutput) throws ExceptionZZZ;
	public void print(String s, int iPrintLevel) throws ExceptionZZZ;
	public void print(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ;
	public void print(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	
	public void println(String s, boolean bPrintOutput) throws ExceptionZZZ;
	public void println(String s, int iPrintLevel) throws ExceptionZZZ;
	public void println(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ;
	public void println(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	//............ Siehe ISystemEnabledZZZ
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
