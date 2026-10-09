package basic.zBasic;

import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

/** Positionsermittlung ist ein eigener Weg diese aus einer zuvor extra erstellen XML-Struktur auszulesen.
 *  Darum hier als eigenen Interfaceklasse. 
 *  
 * @author Fritz Lindhauer
 *
 */
public interface ILogProtocolPositionZZZ extends ILogProtocolZZZ{
	//#################################
	//### Drei Wege Logs zu schreiben (A / B / C).
	//### Die Formatierung dieses Strings mit ILogStringZZZ - Methodik ist moeglich.
	//##################################
	
	//##################################
	//### A) Die Idee ist, das hier ein einfaches System.out gemacht wird. (siehe IObjectLogZZZ)
	//##################################
	//................
	
	//##################################
	//### B) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ)
	//##################################
	//................
	
	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition
	//### 	Merke: Bei Positionsangaben - Das Level angeben für den Stacktrace.	
	//###   Merke: Wie bei logLineDate... hier auch die Version mit ...WithPosition
	//##################################
		
	public boolean protocolLineWithPosition(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Object obj, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ;
	
	public boolean protocolLineWithPosition(Class objClass, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ;
	
	
	//+++++++++++++++
	//+++ mit Datum, weil es wichtig ist
	public boolean protocolLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineDateWithPosition(Object obj, int iLevel, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineDateWithPosition(Object obj, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineDateWithPosition(Object obj, int iLevel, String... sLogs) throws ExceptionZZZ;
	
	public boolean protocolLineDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineDateWithPosition(Class objClass, int iLevel, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineDateWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineDateWithPosition(Class objClass, int iLevel, String... sLogs) throws ExceptionZZZ;
	
	
	//+++++++++++++++++ 
	//+++ Mit StringFormat
	//Kein Date wenn man das Mapping-Format übergibt, darin sollte das Datum sein
	public boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Object obj, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	public boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLineWithPosition(Class objClass, int iLevel, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	
	
}
