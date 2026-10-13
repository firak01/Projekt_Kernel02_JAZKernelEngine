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
	//... wg. möglichem String... ss, der immer hinten stehen muss, muss die Reihenfolge so sein, das String immer hinten steht.
	//############################################################
	public boolean print(boolean bPrintOutput, String s) throws ExceptionZZZ;
	public boolean print(int iPrintLevel, String s) throws ExceptionZZZ;
	public boolean print(int iPrintLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
	
	
	public boolean print(PRINTLEVEL enumPrintLevel, String s) throws ExceptionZZZ;
	public boolean print(LOGLEVEL enumLogLevel, String s) throws ExceptionZZZ;
	
	public boolean print(PRINTLEVEL enumPrintLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
	public boolean print(LOGLEVEL enumLogLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
	
	
	public boolean println(boolean bPrintOutput, String s) throws ExceptionZZZ;
	public boolean println(int iPrintLevel, String s) throws ExceptionZZZ;
	public boolean println(int iPrintLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
		
	public boolean println(PRINTLEVEL enumPrintLevel, String s) throws ExceptionZZZ;
	public boolean println(LOGLEVEL enumLogLevel, String s) throws ExceptionZZZ;
	
	public boolean println(PRINTLEVEL enumPrintLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
	public boolean println(LOGLEVEL enumLogLevel, boolean bPrintOutput, String s) throws ExceptionZZZ;
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	//............ Siehe ISystemEnabledZZZ
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
