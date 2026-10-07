package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

/** Idee ist, das diese Klasse mit dem kurzen Namen verwendet wird statt LogSingletonZZZ.getInstance(). ...
*  Dann hat diese Klasse noch Komfortfunktionen.
*  
*  Intern wird dann eine Singleton Klasse verwendet, die zudem noch per FLAGZ gesteuert werden könnte. 
* @author Fritz Lindhauer
*
*/
public class Log implements IConstantZZZ{
	private Log(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	public static boolean writeInfo(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeInfo(sInput);
	}
	
	public static boolean writeWarning(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeWarning(sInput);
	}
	
	public static boolean writeDebug(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeDebug(sInput);
	}
	
	//+++++++++++++++++
	
	public static boolean println(Object obj, String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().printLine(obj, sInput);
	}
	
	public static boolean println(Object obj, String[] saInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().printLine(obj, saInput);
	}
	
	public static boolean printlnDate(Object obj, String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().printLineDate(obj, sInput);
	}
	
	public static boolean printlnDateWithPosition(Object obj, String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().printLineDateWithPosition(obj, sInput);
	}
	
	public static boolean printlnDateWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().printLineDateWithPosition(obj, saInput);
	}
	
	//+++++++++++++++++
	
	public static boolean protocol(Object obj, String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().protocol(obj, sInput);
	}
	 
	public static boolean protocol(Object obj, String[] saInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().protocol(obj, saInput);
	}
	
	public static boolean protocolWithPosition(Object obj, String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().protocolWithPosition(obj, sInput);
	}
	
	public static boolean protocolWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().protocolWithPosition(obj, saInput);
	}
	
	
	//++++++++++++++++++
	
	
}
