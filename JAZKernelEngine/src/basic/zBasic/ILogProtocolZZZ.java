package basic.zBasic;

import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

public interface ILogProtocolZZZ extends ILogPrintZZZ{
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
	
	//Merke: 20240130 Weil es in AbstractMainOVPN diese Methode gibt hier aufgenommen. Damit muss man die Codestellen nicht alle durch logLineDate ersetzen, wenn man mal ein Snippet daraus nutzt.
//	public void protocol(String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
//	public void protocol(String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public boolean protocolLine(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLine(Object obj, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	public boolean protocolLine(Class objClass, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLine(Class objClass, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	
	//++++++++++++++++++++++++++
	//Merke: 20240427 Nun wird auch mit einer moeglichen Formatanweisung fuer LogStringFormatZZZ geschrieben.
//	public void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
//	public void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
//	public void protocol(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;
	
	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;

	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;

	
	//+++++++++++++++++++++++++++++++++++++++++++
	
	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition 
	//##################################
	//..............

}
