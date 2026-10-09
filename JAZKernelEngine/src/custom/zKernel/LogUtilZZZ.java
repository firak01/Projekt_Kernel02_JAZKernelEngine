package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.system.SystemSingletonZZZ;

public class LogUtilZZZ {
	private LogUtilZZZ(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	public static boolean canPrint() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iLogLevel=-1;
			if(LogSingletonZZZ.isInitialized()) {
				iLogLevel = LogSingletonZZZ.getInstance().getLogLevelOverall();
			}else {
				iLogLevel = LogSingletonZZZ.LOGLEVEL_DEFAULT.ordinal();
			}

			int iPrintLevel=-1;
			if(SystemSingletonZZZ.isInitialized()) {
				iPrintLevel = SystemSingletonZZZ.getInstance().getPrintLevelOverall();
			}else {
				iPrintLevel = SystemSingletonZZZ.PRINTLEVEL_DEFAULT.ordinal();
			}
			if(iLogLevel>iPrintLevel) break main;
			
			bReturn = true;
		}
		return bReturn;
	}
	
	public static boolean canProtocol() throws ExceptionZZZ {
		return canPrint();
	}
}
