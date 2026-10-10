package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.system.Syso;
import basic.zBasic.util.system.SystemSingletonZZZ;
import custom.zKernel.ILogZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.LogSingletonZZZ;
import custom.zKernel.LogUtilZZZ;

public abstract class AbstractLogZZZ<T> extends AbstractObjectZZZ<T> implements ILogProtocolZZZ, ILogProtocolPositionZZZ, ILogPrintZZZ{
	private static final long serialVersionUID = 6495244810060327188L;

	
	//### Wichtige static Methoden
	public static boolean canPrint() throws ExceptionZZZ{
		return LogUtilZZZ.canPrint();
	}
	
	public static boolean canProtocol() throws ExceptionZZZ{
		return LogUtilZZZ.canProtocol();
	}
	
	
	//### aus ILogPrintZZZ
	@Override
	public boolean printLine(Object obj, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;			
			String sTemp = KernelLogZZZ.computeln(obj, sLog);
			bReturn =  Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLine(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;	
			String sTemp = KernelLogZZZ.computeln(obj, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLine(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computeln(classObj, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLine(Class classObj, String[] saLog) throws ExceptionZZZ{
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
	@Override
	public boolean printLineDate(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(obj, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDate(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(obj, saLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDate(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDate(classObj, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDate(Class classObj, String[] saLog) throws ExceptionZZZ{
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
	@Override
	public boolean printLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean printLineDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, iLevel+1, sLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, saLog);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, 1, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean printLineDateWithPosition(Class classObj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, iLevel+1, sLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDateWithPosition(Object obj, int iLevel, String[] saLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, iLevel+1, saLogs);
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean printLineDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, saLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean printLineDateWithPosition(Class classObj, int iLevel, String[] saLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canPrint()) break main;
			String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, iLevel, saLog);				
			bReturn = Syso.println(sTemp);
		}//end main:
		return bReturn;
	}
	
	//### aus IObjectProtocolLogZZZ
	
	//#########################################
	//### log Protocol bedeutete, das dies (falls möglich) in einen Protokolldatei geschrieben wird.
	//### Also sind alle System.outs zu ersetzten durch die Arbeit mit einem LogZZZ-Objekt
	//#########################################
	
	@Override
	public synchronized boolean protocolLine(Object obj, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
					
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);						
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);
			
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLine(Class objClass, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;			
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
					
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClass, sLog);						
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);
			
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++
	
	@Override
	public synchronized boolean protocolLine(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;			
	
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLogs);						
			
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}	
	@Override
	public synchronized boolean protocolLine(Class objClass, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;	
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClass, sLogs);						
			
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}
	
	
	//++++++++++++++++++++
	//+++ Mit Datum, weil es wichtig ist (!ohne StringFormat!!!)
	//+++++++++++++++++++++
	@Override
	public boolean protocolLineDate(Object obj, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDate_withObject();
			
		
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, iaFormat, sLog);						
		
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDate(Object obj, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDate_withObject();
			
		
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, iaFormat, sLogs);						
		
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDate(Class objClass, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDate_withObject();
			
		
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClass, iaFormat, sLog);						
		
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDate(Class objClass, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDate_withObject();
			
		
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
			//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClass, iaFormat, sLogs);						
		
			//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}

	//######################
	//### Mit Position
	//### Dann immer auch eine Methode für iLevel, der Position im Stacktrace, zur Verfügung stellen.
	//########################
	
	@Override
	public boolean protocolLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, 1, iaFormat, saLog);
			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, iaFormat, saLog);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Object obj, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			bReturn = protocolLineWithPosition__(obj, 1, iaFormat, sLogs);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			bReturn = protocolLineWithPosition__(obj, iLevel+1, iaFormat, sLogs);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, 1, iaFormat, saLog);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, iaFormat, saLog);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			bReturn = protocolLineWithPosition__(objClass, 1, iaFormat, sLogs);			
		}//end main:
		return bReturn;
	}

	@Override
	public boolean protocolLineDateWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
		
			//Wir wollen hier zwar mit Datum, aber ohne Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineDateWithPosition_withObject();
			
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, iaFormat, sLogs);			
		}//end main:
		return bReturn;
	}
	

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Mit StringFormat
	//+++ Merke: Hier keine extra ...Date-Methode. Will man das Datum haben, muss es im StringFormat enthalten sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;	
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocol__(obj, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;	
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocol__(objClass, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;	
			String[] saLog = sLogs;
			bReturn = protocol__(obj, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;	
			String[] saLog = sLogs;
			bReturn = protocol__(objClass, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocol__(obj, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocol__(objClass, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	
	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE
    // Dann nicht vergessen iLevel als Methode zur Verfügung stellen, für die Position im Stacktrace			
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			return protocolLineWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, 1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
			IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
			
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, iaFormat, saLog);
		}//end main:
		return bReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Mit StringFormat
	//+++ Merke: Dann keine Methode mit ...Date anbieten. Will man das Datum haben, muss es im StringFormat enthalten sein
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
			
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			String[] saLog = sLogs;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	//+++++++++++++++++++++++++++++
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	@Override
	public synchronized boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(obj, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, 1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	@Override
	public synchronized boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(!canProtocol()) break main;
			IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaMappedLogString[0] = ienumMappedLogString;
	
			String[] saLog = new String[1];
			saLog[0] = sLog;
			bReturn = protocolLineWithPosition__(objClass, iLevel+1, ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}	
	
	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
	private boolean protocolLineWithPosition__(Object obj, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		return protocolLineWithPosition__(obj.getClass(), iLevel, ienumaMappedLogString, saLogs);
	}
	
	private boolean protocolLineWithPosition__(Class objClass, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
		//return this.protocolLine(objClass, ienumaMappedLogString, saLog);
		return protocol__(objClass, ienumaMappedLogString, saLog);
	}
	
	private boolean protocol__(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(!canProtocol()) break main;
			bReturn = protocol__(objIn.getClass(), ienumaMappedLogString, saLog);
		}//end main:
		return bReturn;
	}
	
	private boolean protocol__(Class objClassIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;
			if(!canProtocol()) break main;
			
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objClassIn, ienumaMappedLogString, saLog);
			
			//wird schon in .WriteLine(...) gemacht;//System.out.println(sLogUsed);			
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}
	
	
}
