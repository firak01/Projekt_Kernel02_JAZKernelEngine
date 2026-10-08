package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.system.Syso;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.KernelLogZZZ;

/** Wichtige Klasse ohne das Singleton-Log Objekt
 *  und ohne eine FileWriter zu verwenden.
 *  
 *  So kann man beim Initialisieren des Singleton-Log Objekts auch LogAusgaben machen,
 *  weil eine Datei noch nicht definiert sein muss.
 *  
 * @author Fritz Lindhauer
 *
 * @param <T>
 */
public class LogZZZ<T> extends AbstractLogZZZ<T> {
	private static final long serialVersionUID = -4674809839183596083L;

	//#####################
	//### Methoden hier static zur Verfuegung stellen.
	//### Damit koennen diese auch in static Methoden genutzt werden.
	//#####################	
	
	//#############################################
	//### println... ohne protocol sind für die Ausgabe auf der Konsole gedacht.
	//### Daher wird hier über die Syso-Klasse nur system.out verwendet.
    //##############################################

	

	//##### Gib den String aus.
	//  Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//  Das dann jeweils als Variante mit einer Klasse als Argument
	//public static void logLine(String sLog) throws ExceptionZZZ{		
//	public void logLine(String sLog) throws ExceptionZZZ{
//		String sTemp = KernelLogZZZ.computeLine(this.getClass(), sLog);
//		Syso.println(sTemp);
//	}
//	
//	//public static void logLine(String[] saLog) throws ExceptionZZZ{		
//	public void logLine(String[] saLog) throws ExceptionZZZ{
//		String sTemp = KernelLogZZZ.computeLine(this.getClass(), saLog);
//		Syso.println(sTemp);
//	}
	
	public static boolean println(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(obj, (IStringFormatManagerZZZ) null, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(obj, saLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Class classObj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, saLog);
		return Syso.println(sTemp);
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static boolean printlnDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, sLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDate(Object obj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, saLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, sLog);				
		return Syso.println(sTemp);
	}
	public static boolean printlnDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, saLog);				
		return Syso.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	public static boolean printlnDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, sLog);
		return Syso.println(sTemp);
	}
	public static boolean printlnDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, iLevel+1, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, saLog);
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, 1, sLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, saLog);				
		return Syso.println(sTemp);
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Biete die Log-Methoden auch static an, siehe ILogZZZ, bzw. AbstractObjectZZZ fuer den Code
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	public synchronized static void protocol(Object obj, String... sLogs) throws ExceptionZZZ{
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			
			if(obj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}else {
				for(String sLog : sLogs) {
					LogZZZ.protocol(obj, sLog);
				}	
			}		
		}//end main:
	}
		
	public synchronized static void protocol(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed;
		sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);
		Syso.println(sLogUsed);
	}

	public synchronized static void protocol(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				LogZZZ.protocol(obj, sLogs);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						LogZZZ.protocol(ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						LogZZZ.protocol(sLog);
					}
				}
			}else {
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						LogZZZ.protocol(obj, ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						LogZZZ.protocol(sLog);
					}
				}			
			}
		}//end main:
	}

	public synchronized static void protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		String sLogUsed;
		if(obj==null) {
			sLogUsed = StringFormatManagerZZZ.getInstance().compute(ienumMappedLogString, sLog);
		}else {
			sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
		}
		Syso.println(sLogUsed);
	}
	
	//++++++++++++++++++++++++
	public synchronized static void protocol(Class classObj, String... sLogs) throws ExceptionZZZ{
		main:{
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLogs);
			Syso.println(sLogUsed);				
		}//end main:
	}
		
	public synchronized static void protocol(Class classObj, String sLog) throws ExceptionZZZ{		
		if(classObj==null) {			
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}

		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLog);
		Syso.println(sLogUsed);
	}

	public synchronized static void protocol(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		main:{
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				LogZZZ.protocol(classObj, sLogs);
				break main;
			}
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumaMappedLogString, sLogs);		
			Syso.println(sLogUsed);
		}//end main:
	}

	public synchronized static void protocol(Class classObj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		if(classObj==null) {
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	
		}
			
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumMappedLogString, sLog);		
		Syso.println(sLogUsed);
	}

	

	//++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//TODOGOON20261008: Biete die Log-Methoden auch static an.
	
	
}
