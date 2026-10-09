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

	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public boolean protocolLine(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLine(Object obj, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	public boolean protocolLine(Class objClass, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLine(Class objClass, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++ Explzit mit Datum, weil es wichtig ist
	//+++++++++++++++++++++++++++++++++++++++++++

	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public boolean protocolLineDate(Object obj, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLineDate(Object obj, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	public boolean protocolLineDate(Class objClass, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.	
	public boolean protocolLineDate(Class objClass, String... sLogs) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	
	
	//++++++++++++++++++++++++++
	//+++ mit StringFormat
    //+++ Merke: Kein explizite LineDate Methode, wenn man das Mapping-Format übergibt.... darin sollte das Datum nämlich -falls gewünscht- sein.
	//++++++++++++++++++++++++++

	//Merke: Ggfs. möchte man fuer ein anderes Objekt einen Log-Eintrag generieren (z.B. ein EventBroker für den gerade verarbeiteten Listener)
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLine(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;

	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ; //Intention dahinter: Anders als logLineDate wird ggfs. noch woanders als im System.out protokolliert. In einfachen Klassen normalerweise wie logLineDate.
	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ;
	public boolean protocolLine(Class objClass, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ;

	
	
	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition 
	//##################################
	//..............

}
