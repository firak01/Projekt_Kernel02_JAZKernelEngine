package basic.zBasic;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import custom.zKernel.ILogZZZ;
import custom.zKernel.LogSingletonZZZ;
import custom.zKernel.KernelLogZZZ;

public class AbstractObjectZZZ<T> implements IObjectZZZ, IOutputDebugNormedZZZ, ILogProtocolPositionZZZ{
	private static final long serialVersionUID = 4785854649300281154L;

	//fuer IOutputDebugNormedZZZ
	protected volatile String sDebugEntryDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.
	public AbstractObjectZZZ() {		
	}	
	
	/**Overwritten and using an object of jakarta.commons.lang
	 * to create this string using reflection. 
	 * Remark: this is not yet formated. A style class is available in jakarta.commons.lang. 
	 */
	@Override
	public String toString(){
		String sReturn = "";
		sReturn = ReflectionToStringBuilder.toString(this);
		return sReturn;
	}
	

	//### aus Clonable
	@Override
	//https://www.geeksforgeeks.org/clone-method-in-java-2/
	//Hier wird also "shallow clone" gemacht. Statt einem deep Clone.
	//Beim Deep Clone müssten alle intern verwendeten Objekte ebenfalls neu erstellt werden.	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	
	//Meine Variante Objekte zu clonen, aber erzeugt nur einen "Shallow Clone".
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
		
		
	//### aus IOutputDebugNormedZZZ
	@Override
	public String computeDebugString() throws ExceptionZZZ{
		return this.toString();
	}
	
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
		return this.computeDebugString() + sEntryDelimiter;
	}
	
	@Override
	public String getDebugEntryDelimiter() {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}
			
	//### aus ILogPrintZZZ, Merke: Dazu gibt es jeweils auch eine static-Methode fuer die Klasse als Argument.
	@Override
	public void println(String sLog) throws ExceptionZZZ{		
		//String sTemp = KernelLogZZZ.computeLine(this.getClass(), sLog);
		//System.out.println(sTemp);
		ObjectZZZ.println(this, sLog);
	}
	
	//public static void logLine(String[] saLog) throws ExceptionZZZ{
	@Override
	public void println(String[] saLog) throws ExceptionZZZ{
		//String sTemp = KernelLogZZZ.computeLine(this.getClass(), saLog);
		//System.out.println(sTemp);
		
		ObjectZZZ.println(this, saLog);
	}
	
	@Override
	public synchronized void printlnDate(String sLog) throws ExceptionZZZ {
		ObjectZZZ.printlnDate(this, sLog);
	}

	@Override
	public synchronized void printlnDateWithPosition(String sLog) throws ExceptionZZZ {
		ObjectZZZ.printlnDateWithPosition(this, sLog);
	}
	
	@Override
	public synchronized void printlnDate(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.printlnDate(this, sLogs);
	}
	
	@Override
	public synchronized void printlnDateWithPosition(String... sLogs) throws ExceptionZZZ {
		ObjectZZZ.printlnDateWithPosition(this, sLogs);
	}
			
	//### aus ILogProtocolZZZ
	
	//#########################################
	//### log Protocol bedeutete, das dies (falls möglich) in einen Protokolldatei geschrieben wird.
	//### Also sind alle System.outs zu ersetzten durch die Arbeit mit einem LogZZZ-Objekt
	//#########################################
	@Override
	public synchronized void protocol(String sLog) throws ExceptionZZZ {
		this.protocol(this, sLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void protocol(String... sLogs) throws ExceptionZZZ{
		this.protocol(this, sLogs); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
	}
	
	@Override
	public synchronized void protocol(Object obj, String sLog) throws ExceptionZZZ {
		//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
				
		//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);						
		//wird in WriteLine schon gemacht... System.out.println(sLogUsed);
		
		objLog.writeLine(sLogUsed);
	}
	
	@Override
	public synchronized void protocol(Object obj, String... sLogs) throws ExceptionZZZ{
		//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
		ILogZZZ objLog = LogSingletonZZZ.getInstance();
		
		//wichtig: Wenn dies vor dem Holen der Log Instanz gemacht wird, arbeitet man mit einer weit links liegenden "justifier-Grenze".
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLogs);						
		
		//wird in WriteLine schon gemacht... System.out.println(sLogUsed);		
		objLog.writeLine(sLogUsed);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public synchronized void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocol__(ienumaMappedLogString, saLog);
	}
	
	@Override
	public void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		logProtocol__(ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocol(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		String[] saLog = sLogs;
		logProtocol__(ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		logProtocol__(obj, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
		String[] saLog = sLogs;
		logProtocol__(obj, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;

		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocol__(obj, ienumaMappedLogString, saLog);
	}
	
	
	private void logProtocol__(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;		
			
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(objIn, ienumaMappedLogString, saLog);
			
			//wird schon in .WriteLine(...) gemacht;//System.out.println(sLogUsed);			
			objLog.writeLine(sLogUsed);
		}//end main:
	}
	
	
	private void logProtocol__(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLog) throws ExceptionZZZ {
		main:{
			if(ArrayUtilZZZ.isNull(saLog)) break main;		
			
			//Wichtig: Hole erst die Log Instanz. Darin wird schon jede menge Protokolliert und die "justifier-Grenze" verschoben.
			ILogZZZ objLog = LogSingletonZZZ.getInstance();
			
			String sLogUsed = StringFormatManagerZZZ.getInstance().compute(ienumaMappedLogString, saLog);
			
			//wird schon in .WriteLine(...) gemacht;//System.out.println(sLogUsed);			
			objLog.writeLine(sLogUsed);
		}//end main:
	}
	
	
	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE
	@Override
	public synchronized void protocolWithPosition(String... sLogs) throws ExceptionZZZ{
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = sLogs;
		logProtocolWithPosition__(1, iaFormat, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(String sLog) throws ExceptionZZZ{
		
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocolWithPosition__(this, 1, iaFormat, saLog);
	}
			
	@Override
	public synchronized void protocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
	
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = sLogs;
		logProtocolWithPosition__(this, 1, iaFormat, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
		
		//Wir wollen hier zwar ohne Datum, aber mit Positionsangabe
		IEnumSetMappedStringFormatZZZ[]iaFormat = KernelLogZZZ.getFormatForComputeLineWithPosition_withObject();
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocolWithPosition__(this, 1, iaFormat, saLog);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {		
		String[] saLog = sLogs;
		logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocolWithPosition__(this, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;
		
		String[] saLog = sLogs;
		logProtocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {		
		String[] saLog = sLogs;
		logProtocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}
	
	@Override
	public synchronized void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaMappedLogString[0] = ienumMappedLogString;

		String[] saLog = new String[1];
		saLog[0] = sLog;
		logProtocolWithPosition__(obj, 1, ienumaMappedLogString, saLog);
	}	
	
	private void logProtocolWithPosition__(Object obj, int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
		this.protocol(obj, ienumaMappedLogString, saLog); 
	}
	
	private void logProtocolWithPosition__(int iLevelIn, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String[] saLogs) throws ExceptionZZZ {
		int iLevel = iLevelIn + 1;
		String sPositionCalling = ReflectCodeZZZ.getPositionXml(iLevel); //Xml deshalb, weil sich daraus die Details gezogen werden kann. Ohne XML werden das 2 Zeilen im Log.
		String[] saLog = StringArrayZZZ.prepend(saLogs, sPositionCalling);
		this.protocol(ienumaMappedLogString, saLog); 
	}
}
