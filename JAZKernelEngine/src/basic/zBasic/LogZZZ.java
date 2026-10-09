package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.system.Syso;
import basic.zBasic.util.system.SystemSingletonZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.LogSingletonZZZ;

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
	public static boolean println(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computeln(obj, (IStringFormatManagerZZZ) null, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;				
	}
	
	public static boolean println(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computeln(obj, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;

	}
	
	public static boolean println(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computeln(classObj, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean println(Class classObj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computeln(classObj, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static boolean printlnDate(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(obj, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean printlnDate(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(obj, saLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean printlnDate(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(classObj, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	public static boolean printlnDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;		
			String sTemp = KernelLogZZZ.computelnDate(classObj, saLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	//
	//     Wichtig: Jede Methode gibt es auch als Variante mit iLevel, für das Level des Stacktrace.
	//              Das ist aber nur für die Positionsermittlung wichtig.
	
	public static boolean printlnDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	public static boolean printlnDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;			
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, iLevel+1, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean printlnDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	public static boolean printlnDateWithPosition(Object obj, int iLevel, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, iLevel+1, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	public static boolean printlnDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, 1, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	public static boolean printlnDateWithPosition(Class classObj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, iLevel+1, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	
	public static boolean printlnDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, saLog);				
		return Syso.println(sTemp);
	}
	public static boolean printlnDateWithPosition(Class classObj, int iLevel, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, iLevel, saLog);				
		return Syso.println(sTemp);
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Biete die Log-Methoden auch static an, siehe ILogZZZ, bzw. AbstractObjectZZZ fuer den Code
	//+++++++++++++++++++++++++++++++++++++++++++++++
	
	public synchronized static boolean protocol(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			if(!canProtocol()) break main;
			
			if(obj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}else {
				for(String sLog : sLogs) {
					bReturn = LogZZZ.protocol(obj, sLog);
				}	
			}				
		}//end main:
		return bReturn;
	}	
	public synchronized static boolean protocol(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			String sLogUsed;
			sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocol(Class classObj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLogs);
			bReturn = Syso.println(sLogUsed);				
		}//end main:
		return bReturn;
	}	
	public synchronized static boolean protocol(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, sLog);
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}

	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE		
	//### Merke: Wg. Positionsangabe immer auch die Methode mit dem iLevel für die Stacktraceposition zur Verfügung stellen.
	public synchronized static boolean protocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocolWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	//+++++++++++++++++++++++++
	
	public synchronized static boolean protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn  = protocolWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	public synchronized static boolean protocolWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
		
	}
	
	public synchronized static boolean protocolWithPosition(Class objClass, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	//############ ALLE METHODEN NUN AUCH NOCH MIT DATUM UND MIT POSITIONSANGABE
	//++++ Merke: wg. Postionsangabe auch eine Methode mit dem iLevel für den Stacktrace zur Verfügung stellen
	public synchronized static boolean protocolDateWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolDateWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocolDateWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolDateWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	//+++++++++++++++++++++++++
	
	public synchronized static boolean protocolDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn  = protocolWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	public synchronized static boolean protocolDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
		
	}
	
	public synchronized static boolean protocolDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	public synchronized static boolean protocolDateWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Mit StringFormat
	//++++ Merke: Dann gibt es hier keine Methode mit Datum drin. Will man das Datum haben, muss es im StringFormat enthalten sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public synchronized static boolean protocol(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(sLogs)) break main;
			if(!canProtocol()) break main;
			
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				bReturn = LogZZZ.protocol(obj, sLogs);
				break main;
			}
			
			int iIndex=0;
			if(obj==null) {			
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						bReturn = LogZZZ.protocol(ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						bReturn = LogZZZ.protocol(sLog);
					}
				}
			}else {
				for(String sLog : sLogs) {
					if(ienumaMappedLogString.length>iIndex) {
						bReturn = LogZZZ.protocol(obj, ienumaMappedLogString[iIndex],sLog);
						iIndex++;
					}else {
						bReturn = LogZZZ.protocol(sLog);
					}
				}			
			}
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			String sLogUsed;
			if(obj==null) {
				sLogUsed = StringFormatManagerZZZ.getInstance().compute(ienumMappedLogString, sLog);
			}else {
				sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
			}
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocol(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			if(classObj==null) {			
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
				LogZZZ.protocol(classObj, sLogs);
				break main;
			}
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumaMappedLogString, sLogs);		
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}

	public synchronized static boolean protocol(Class classObj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			if(classObj==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
				
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(classObj, ienumMappedLogString, sLog);		
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}
	public static boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static boolean protocolWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static boolean protocolWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static boolean protocolWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++
	
	public static synchronized boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static synchronized boolean protocolWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	
	public static synchronized boolean protocolWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static synchronized boolean protocolWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			String[] saLog = sLogs;
			bReturn = protocolWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	//+++++++++++++++++++++++++++++++++
	
	public static  synchronized boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	
	public static synchronized boolean protocolWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	public static synchronized boolean protocolWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	
	public static synchronized boolean protocolWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	

	//+++++++++++++++++++++++++++++++++++
	//
	//+++++++++++++++++++++++++++++++++++	
	
	private static boolean protocolWithPosition__(Object obj, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			int iLevel = iLevelIn + 1;
			bReturn = protocolWithPosition(obj.getClass(), iLevel, ienumaMappedLogString, saLogs);
		}//end main:
		return bReturn;
		
	}
	private static boolean protocolWithPosition__(Class objClass, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			int iLevel = iLevelIn + 1;
			String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
			String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
			//return this.protocolLine(objClass, iLevelIn, ienumaMappedLogString, saLog); 
	
			bReturn = protocol__(objClass, ienumaMappedLogString, saLog);
		}
		return bReturn;
	}
	
	private static boolean protocol__(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;		
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objIn, ienumaMappedLogString, saLog);
			
			//Hier wird kein FileTextWriter-Objekt zur Verfügung gestellt	
			//bReturn = this.writeLine(sLogUsed);
			
			//Daher wird auch nix in eine Datei geschrieben. Also direkte Ausgabe			
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}
	private static boolean protocol__(Class objClassIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;		
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClassIn, ienumaMappedLogString, saLog);
			
			
			//Hier wird kein FileTextWriter-Objekt zur Verfügung gestellt			
			//bReturn = this.writeLine(sLogUsed);
			
			//Daher wird auch nix in eine Datei geschrieben. Also direkte Ausgabe			
			bReturn = Syso.println(sLogUsed);
		}//end main:
		return bReturn;
	}


	
}
