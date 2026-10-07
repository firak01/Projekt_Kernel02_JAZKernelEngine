package basic.zBasic;

public interface ILogPrintZZZ {
	//#################################
	//### Drei Wege Logs zu schreiben (A / B / C).
	//### Die Formatierung dieses Strings mit ILogStringZZZ - Methodik ist moeglich.
	//##################################
		
	//##################################
	//### A) Die Idee ist, das hier ein einfaches System.out gemacht wird. (siehe IObjectLogZZZ)
	//##################################
	
	public boolean printLine(Object obj, String sLog) throws ExceptionZZZ;
	public boolean printLine(Class objClass, String sLog) throws ExceptionZZZ;
	
	public boolean printLine(Object obj, String[] saLog) throws ExceptionZZZ;
	public boolean printLine(Class objClass, String[] saLog) throws ExceptionZZZ;
	
	
	//!!! Merke 20240512: Mache in den (abstrakten) Klassen, die diese Methoden implementieren die Methoden "synchronized"
	public boolean printLineDate(Object obj, String sLog) throws ExceptionZZZ;
	public boolean printLineDate(Class objClass, String sLog) throws ExceptionZZZ;
	
	public boolean printLineDate(Object obj, String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	public boolean printLineDate(Class objClass, String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	public boolean printLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ;
	public boolean printLineDateWithPosition(Class objClass, String sLog) throws ExceptionZZZ;
	
	public boolean printLineDateWithPosition(Object obj, String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	public boolean printLineDateWithPosition(Class objClass, String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	
	//##################################
	//### B) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ)
	//##################################
	//......
	

	//##################################
	//### C) Die Idee ist, das hier zusätzlich zu dem System.out noch an einer anderen Stelle protokolliert wird.
	//###    (s. IKernelObjectLogZZZ) 
	//###    UND mit Angabe der CodePosition 
	//##################################
	//..............

	
}
