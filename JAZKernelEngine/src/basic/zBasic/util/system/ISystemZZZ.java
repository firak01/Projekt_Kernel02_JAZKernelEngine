package basic.zBasic.util.system;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;

public interface ISystemZZZ extends IListenerObjectFlagZsetZZZ, ISystemEnabledZZZ{
		
	//############################################################
	//### Methoden
	//############################################################
	public void println(String s, boolean bPrintOutput) throws ExceptionZZZ;
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	//............ Siehe ISystemEnabledZZZ
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
