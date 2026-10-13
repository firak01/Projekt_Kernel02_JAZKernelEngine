package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.LogZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ.PRINTLEVEL;
import basic.zBasic.util.system.Syso;
import custom.zKernel.ILogLevelUserZZZ.LOGLEVEL;

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
	
	//######################################################################
	//#### ALLE METHODEN FÜR OBJECT. 
	//MERKE: NICHT die CLASS - Methode mit obj.getClass() aufrufen,
	//       da sonst wieder Methoden mit iLevel für die mögliche Position im Stacktrace genutzt werden müsssen.
	public static boolean writeInfo(Object obj, String sLog) throws ExceptionZZZ {
		//return Log.protocol(obj, ILogLevelUserZZZ.LOGLEVEL.INFO, sLog);
		
		//Vor der Info - Ausgabe noch einmal die gleiche Ausgabe machen, mit Position. Als DEBUG.
		if(LogSingletonZZZ.isInitialized()) {
			LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, LOGLEVEL.DEBUG, sLog);
			return Syso.println(PRINTLEVEL.INFO, sLog);			
		}else {			
			LogZZZ.printlnDateWithPosition(obj, 1, LOGLEVEL.DEBUG, sLog); 
			return Syso.println(PRINTLEVEL.INFO, sLog);
		}
	}
	
	public static boolean writeWarning(Object obj, String sLog) throws ExceptionZZZ {
		//return Log.protocol(obj, ILogLevelUserZZZ.LOGLEVEL.WARNING, sLog);
		
		//Vor der Warning - Ausgabe noch einmal die gleiche Ausgabe machen, mit Position. Als DEBUG.
		if(LogSingletonZZZ.isInitialized()) {
			LogSingletonZZZ.getInstance().protocolLineDateWithPosition(obj, 1, LOGLEVEL.DEBUG, sLog);
			return Syso.println(PRINTLEVEL.WARNING, sLog);	
		}else {
			LogZZZ.printlnDateWithPosition(obj, 1, LOGLEVEL.DEBUG, sLog);
			return Syso.println(PRINTLEVEL.WARNING, sLog);			
		}
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
	
	//#######################################################################
	//### ALLES STATIC METHODEN AUCH FÜR CLASS anbieten.
	//###MERKE: Diese CLASS - Methode wird aus der OBJECT - Methode nicht mit obj.getClass() aufrufen,
	//###       da sonst wieder Methoden mit iLevel für die mögliche Position im Stacktrace genutzt werden müsssen.
	//#######################################################################
	public static boolean writeInfo(Class objClass, String sLog) throws ExceptionZZZ {		
		//return Log.protocol(objClass, ILogLevelUserZZZ.LOGLEVEL.INFO, sLog);
		if(LogSingletonZZZ.isInitialized()) {
			//return LogSingletonZZZ.getInstance().protocolLine(objClass, sLog);
			LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, LOGLEVEL.INFO, sLog);
			return Syso.println(sLog);						
		}else {			
			LogZZZ.printlnDateWithPosition(objClass, 1, LOGLEVEL.DEBUG, sLog);
			return Syso.println(sLog);
		}
	}
	
	public static boolean writeWarning(Class objClass, String sLog) throws ExceptionZZZ {		
		//return Log.protocol(objClass, ILogLevelUserZZZ.LOGLEVEL.WARNING, sLog);
		if(LogSingletonZZZ.isInitialized()) {
			//return LogSingletonZZZ.getInstance().WriteLine(objClass, sLog);
			//return LogSingletonZZZ.getInstance().protocolLine(objClass, sLog);
			
			//Vor der Warning - Ausgabe noch einmal die gleiche Ausgabe machen, mit Position. Als DEBUG.
			LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, LOGLEVEL.DEBUG, sLog);
			return Syso.println(sLog);			
		}else {
			//Vor der Warning - Ausgabe noch einmal die gleiche Ausgabe machen, mit Position. Als DEBUG.
			LogZZZ.printlnDateWithPosition(objClass, LOGLEVEL.DEBUG, sLog);
			return Syso.println(sLog);
		}
	}
	
	public static boolean writeDebug(Class objClass, String sLog) throws ExceptionZZZ {
		return Log.protocolDateWithPosition(objClass, 1, ILogLevelUserZZZ.LOGLEVEL.DEBUG, sLog);
	}
	
	public static boolean writeVerbose(Class objClass, String sLog) throws ExceptionZZZ {
		
		return Log.protocolDateWithPosition(objClass, 1, ILogLevelUserZZZ.LOGLEVEL.VERBOSE, sLog);
		
	}
	
	//+++++++++++++++++
	
	public static boolean println(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(objClass, sLog);
		}else {
			return LogZZZ.println(objClass, sLog);
		}
	}
	
	public static boolean println(Class objClass, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLine(objClass, saInput);
		}else {
			return LogZZZ.println(objClass, saInput);			
		}
	}
	
	public static boolean printlnDate(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDate(objClass, sLog);
		}else {
			return LogZZZ.printlnDate(objClass, sLog);
		}
	}
	
	public static boolean printlnDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().printLineDateWithPosition(objClass, 1, sLog);
		}else {
			return LogZZZ.printlnDateWithPosition(objClass, 1, sLog);
		}
	}
	
	public static boolean printlnDateWithPosition(Class objClass, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {	
			return LogSingletonZZZ.getInstance().printLineDateWithPosition(objClass, 1, saInput);
		}else {
			return LogZZZ.printlnDateWithPosition(objClass, 1, saInput);
		}
	}
	
	//++++++++++++++++++
	//  protocol
	//+++++++++++++++++	
	public static boolean protocol(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(objClass, sLog);
		}else {
			return LogZZZ.protocol(objClass, sLog);
		}
	}
	 
	public static boolean protocol(Class objClass, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(objClass, saInput);
		}else {
			return LogZZZ.protocol(objClass, saInput);
		}
	}
	
	public static boolean protocol(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(objClass, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocol(objClass, enumLogLevel, sLog);
		}
	}
	 
	public static boolean protocol(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLine(objClass, enumLogLevel, saInput);
		}else {
			return LogZZZ.protocol(objClass, enumLogLevel, saInput);
		}
	}
	
	//+++++++++++++++++++++++++++++++++
	
	public static boolean protocolWithPosition(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(objClass, 1, sLog);
		}else {
			return LogZZZ.protocolWithPosition(objClass, 1, sLog);
		}
	}
	
	public static boolean protocolWithPosition(Class objClass, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(objClass, 1, saInput);
		}else {
			return LogZZZ.protocolWithPosition(objClass, 1, saInput);
		}
	}
	
	public static boolean protocolWithPosition(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(objClass, 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolWithPosition(objClass, 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolWithPosition(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saInput) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineWithPosition(objClass, 1, saInput);
		}else {
			return LogZZZ.protocolWithPosition(objClass, 1, saInput);
		}
	}

	//++++++++++++++++++
	
	public static boolean protocolDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, 1, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Class objClass, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, 1, saLog);
		}
	}
	
	
	//### Mit Positionsangabe immer iLevel übergeben
	public static boolean protocolDateWithPosition(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Class objClass, int iLevel, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String sLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, iLevel + 1, enumLogLevel, sLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, iLevel + 1, enumLogLevel, sLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Class objClass, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, 1, enumLogLevel, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, 1, enumLogLevel, saLog);
		}
	}
	
	public static boolean protocolDateWithPosition(Class objClass, int iLevel, ILogLevelUserZZZ.LOGLEVEL enumLogLevel, String[] saLog) throws ExceptionZZZ {
		if(LogSingletonZZZ.isInitialized()) {
			return LogSingletonZZZ.getInstance().protocolLineDateWithPosition(objClass, iLevel + 1, enumLogLevel, saLog);
		}else {
			return LogZZZ.protocolDateWithPosition(objClass, iLevel + 1, enumLogLevel, saLog);
		}
	}
	
	
}
