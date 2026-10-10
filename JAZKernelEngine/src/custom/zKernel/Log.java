package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.LogZZZ;

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
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(obj, sInput);
		}else {
			return LogZZZ.println(obj, sInput);
		}
	}
	
	public static boolean println(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(obj, saInput);
		}else {
			return LogZZZ.println(obj, saInput);			
		}
	}
	
	public static boolean printlnDate(Object obj, String sInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDate(obj, sInput);
		}else {
			return LogZZZ.printlnDate(obj, sInput);
		}
	}
	
	public static boolean printlnDateWithPosition(Object obj, String sInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDateWithPosition(obj, 1, sInput);
		}else {
			return LogZZZ.printlnDateWithPosition(obj, 1, sInput);
		}
	}
	
	public static boolean printlnDateWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {	
			return LogSingletonZZZ.getInstance().printLineDateWithPosition(obj, 1, saInput);
		}else {
			return LogZZZ.printlnDateWithPosition(obj, 1, saInput);
		}
	}
	
	//++++++++++++++++++
	//  protocol
	//+++++++++++++++++	
	public static boolean protocol(Object obj, String sInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, sInput);
		}else {
			return LogZZZ.protocol(obj, sInput);
		}
	}
	 
	public static boolean protocol(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, saInput);
		}else {
			return LogZZZ.protocol(obj, saInput);
		}
	}
	
	//+++++++++++++++++++++++++++++++++
	
	public static boolean protocolWithPosition(Object obj, String sInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, sInput);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, sInput);
		}
	}
	
	public static boolean protocolWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, saInput);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, saInput);
		}
	}

	//++++++++++++++++++
	
	public static boolean protocolDateWithPosition(Object obj, String sInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, sInput);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, sInput);
		}
	}
	
	public static boolean protocolDateWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, saInput);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, saInput);
		}
	}
}
