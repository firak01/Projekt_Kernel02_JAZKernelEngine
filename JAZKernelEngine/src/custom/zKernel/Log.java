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
	
	public static boolean writeInfo(Object obj, String sLog) throws ExceptionZZZ {
		//return LogSingletonZZZ.getInstance().writeInfo(sLog);
		return Log.protocol(obj, ILogLevelUserZZZ.LOGLEVEL.INFO, sLog);
	}
	
	public static boolean writeWarning(Object obj, String sLog) throws ExceptionZZZ {
		//return LogSingletonZZZ.getInstance().writeWarning(sLog);
		return Log.protocol(obj, ILogLevelUserZZZ.LOGLEVEL.WARNING, sLog);
	}
	
	public static boolean writeDebug(Object obj, String sLog) throws ExceptionZZZ {
		return Log.protocolDateWithPosition(obj, 1, ILogLevelUserZZZ.LOGLEVEL.DEBUG, sLog);
	}
	
	public static boolean writeVerbose(Object obj, String sLog) throws ExceptionZZZ {
		
		return Log.protocolDateWithPosition(obj, 1, ILogLevelUserZZZ.LOGLEVEL.VERBOSE, sLog);
		
	}
	
	//+++++++++++++++++
	
	public static boolean println(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(obj, sLog);
		}else {
			return LogZZZ.println(obj, sLog);
		}
	}
	
	public static boolean println(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(obj, saInput);
		}else {
			return LogZZZ.println(obj, saInput);			
		}
	}
	
	public static boolean printlnDate(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDate(obj, sLog);
		}else {
			return LogZZZ.printlnDate(obj, sLog);
		}
	}
	
	public static boolean printlnDateWithPosition(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDateWithPosition(obj, 1, sLog);
		}else {
			return LogZZZ.printlnDateWithPosition(obj, 1, sLog);
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
	public static boolean protocol(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, sLog);
		}else {
			return LogZZZ.protocol(obj, sLog);
		}
	}
	 
	public static boolean protocol(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, saInput);
		}else {
			return LogZZZ.protocol(obj, saInput);
		}
	}
	
	public static boolean protocol(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocol(obj, enumLogLevel, sLog);
		}
	}
	 
	public static boolean protocol(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(obj, enumLogLevel, saInput);
		}else {
			return LogZZZ.protocol(obj, enumLogLevel, saInput);
		}
	}
	
	//+++++++++++++++++++++++++++++++++
	
	public static boolean protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, sLog);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, sLog);
		}
	}
	
	public static boolean protocolWithPosition(Object obj, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, saInput);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, saInput);
		}
	}
	
	public static boolean protocolWithPosition(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolWithPosition(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(obj, 1, saInput);
		}else {
			return LogZZZ.protocolWithPosition(obj, 1, saInput);
		}
	}

	//++++++++++++++++++
	
	public static boolean protocolDateWithPosition(Object obj, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, saLog);
		}
	}
	
	
	//### Mit Positionsangabe immer iLevel übergeben
	public static boolean protocolDateWithPosition(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Object obj, int iLevel, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, iLevel + 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, iLevel + 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Object obj, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, enumLogLevel, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, 1, enumLogLevel, saLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Object obj, int iLevel, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, iLevel + 1, enumLogLevel, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(obj, iLevel + 1, enumLogLevel, saLog);
		}
	}
	
	
	
}
