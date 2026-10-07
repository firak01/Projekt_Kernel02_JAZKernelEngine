package basic.zBasic;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.system.Syso;
import custom.zKernel.ILogZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.LogSingletonZZZ;

public abstract class AbstractLogZZZ<T> extends AbstractObjectZZZ<T> implements ILogProtocolZZZ, ILogProtocolPositionZZZ, ILogPrintZZZ{
	private static final long serialVersionUID = 6495244810060327188L;

	
	//### aus IObjectLogZZZ, Merke: Dazu gibt es jeweils auch eine static-Methode fuer die Klasse als Argument.	
//	@Override
//	public synchronized void printlnDate(String sLog) throws ExceptionZZZ {
//		ObjectZZZ.printlnDate(this, sLog);
//	}
//
//	@Override
//	public synchronized void printlnDateWithPosition(String sLog) throws ExceptionZZZ {
//		ObjectZZZ.printlnDateWithPosition(this, sLog);
//	}
//	
//	@Override
//	public synchronized void printlnDate(String... sLogs) throws ExceptionZZZ {
//		ObjectZZZ.printlnDate(this, sLogs);
//	}
//	
//	@Override
//	public synchronized void printlnDateWithPosition(String... sLogs) throws ExceptionZZZ {
//		ObjectZZZ.printlnDateWithPosition(this, sLogs);
//	}
	
	
	//### aus ILogPrintZZZ
	@Override
	public boolean printLine(Object obj, String sLog) throws ExceptionZZZ {
		String sTemp = KernelLogZZZ.computeln(obj, sLog);
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLine(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(obj, saLog);
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLine(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, sLog);
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLine(Class classObj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, saLog);
		return Syso.println(sTemp);
	}

	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	@Override
	public boolean printLineDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, sLog);				
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDate(Object obj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, saLog);				
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, sLog);				
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, saLog);				
		return Syso.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	@Override
	public boolean printLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, sLog);
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, saLog);
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, 1, sLog);				
		return Syso.println(sTemp);
	}
	
	@Override
	public boolean printLineDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, saLog);				
		return Syso.println(sTemp);
	}
			
	//### aus IObjectProtocolLogZZZ
	
	//#########################################
	//### log Protocol bedeutete, das dies (falls möglich) in einen Protokolldatei geschrieben wird.
	//### Also sind alle System.outs zu ersetzten durch die Arbeit mit einem LogZZZ-Objekt
	//#########################################
//		@Override
//	public synchronized void protocol(String sLog) throws ExceptionZZZ {
//		this.protocol(this, sLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
//	}
//	
//	@Override
//	public synchronized void protocol(String... sLogs) throws ExceptionZZZ{
//		this.protocol(this, sLogs); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
//	}
	
	@Override
	public synchronized boolean protocol(Object obj, String sLog) throws ExceptionZZZ {
		//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
				
		//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);						
		//wird in WriteLine schon gemacht... System.out.println(sLogUsed);
		
		return objLog.writeLine(sLogUsed);
	}
	
	@Override
	public synchronized boolean protocol(Object obj, String... sLogs) throws ExceptionZZZ{
		//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
		//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLogs);						
		
		//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
		return objLog.writeLine(sLogUsed);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
//	@Override
//	public synchronized boolean protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		String[] saLog = new String[1];
//		saLog[0] = sLog;
//		logProtocol__(ienumaMappedLogString, saLog);
//	}
//	
//	@Override
//	public void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		String[] saLog = sLogs;
//		logProtocol__(ienumaMappedLogString, saLog);
//	}
//	
//	@Override
//	public synchronized void protocol(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
//		String[] saLog = sLogs;
//		logProtocol__(ienumaMappedLogString, saLog);
//	}
	
	@Override
	public synchronized boolean protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		return protocol__(obj, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized boolean protocol(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		String[] saLog = sLogs;
		return protocol__(obj, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized boolean protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;

		String[] saLog = new String[1];
		saLog[0] = sLog;
		return protocol__(obj, ienumaMappedLogString, saLog);
	}
	
	
	private boolean protocol__(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;		
			
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objIn, ienumaMappedLogString, saLog);
			
			//wird schon in .WriteLine(...) gemacht;//System.out.println(sLogUsed);			
			bReturn = objLog.writeLine(sLogUsed);
		}//end main:
		return bReturn;
	}
	
	
//	private boolean protocol__(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			if(ArrayUtilZZZ.isNull(saLog)) break main;		
//			
//			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
//			ILogZZZ objLog = LogSingletonZZZ.getInstance();
//			
//			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(ienumaMappedLogString, saLog);
//			
//			//wird schon in .WriteLine(...) gemacht;//System.out.println(sLogUsed);			
//			bReturn = objLog.writeLine(sLogUsed);
//		}//end main:
//		return bReturn;
//	}
	
	
	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE
//	@Override
//	public synchronized boolean protocolWithPosition(String... sLogs) throws ExceptionZZZ{
//		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
//		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
//		
//		String[] saLog = sLogs;
//		return logProtocolWithPosition__(1, iaFormat, saLog);
//	}
	
//	@Override
//	public synchronized boolean protocolWithPosition(String sLog) throws ExceptionZZZ{
//		
//		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
//		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
//		
//		String[] saLog = new String[1];
//		saLog[0] = sLog;
//		return logProtocolWithPosition__(this, 1, iaFormat, saLog);
//	}
			
	@Override
	public synchronized boolean protocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
	
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = sLogs;
		return protocolWithPosition__(this, 1, iaFormat, saLog);
	}
	
	@Override
	public synchronized boolean protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		return protocolWithPosition__(this, 1, iaFormat, saLog);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
//	@Override
//	public boolean protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		String[] saLog = sLogs;
//		return logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
//	}
//	
//	@Override
//	public synchronized boolean protocolWithPosition(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {		
//		String[] saLog = sLogs;
//		return logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
//	}
//	
//	@Override
//	public synchronized boolean protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		String[] saLog = new String[1];
//		saLog[0] = sLog;
//		return logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
//	}
	
	@Override
	public boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		return protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {		
		String[] saLog = sLogs;
		return protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;

		String[] saLog = new String[1];
		saLog[0] = sLog;
		return protocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}	
	
	private boolean protocolWithPosition__(Object obj, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
		return this.protocol(obj, ienumaMappedLogString, saLog); 
	}
	
//	private boolean logProtocolWithPosition__(int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
//		int iLevel = iLevelIn + 1;
//		String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
//		String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
//		return this.protocol(ienumaMappedLogString, saLog); 
//	}
}
