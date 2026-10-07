package basic.zBasic;

import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

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
	//##################################
	
	//Merke: Wie bei logLineDate... hier auch die Version mit ...WithPosition
//	public void protocolWithPosition(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
//	public void protocolWithPosition(String... sLogs) throws ExceptionZZZ;
		
	public boolean protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ;
	
//	public void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
//	public void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
//	public void protocolWithPosition(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	public boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
}
