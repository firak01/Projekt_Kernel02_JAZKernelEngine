package basic.zBasic;

public interface ILogPrintZZZ {
	//#################################
	//### Drei Wege Logs zu schreiben (A / B / C).
	//### Die Formatierung dieses Strings mit ILogStringZZZ - Methodik ist moeglich.
	//##################################
		
	//##################################
	//### A) Die Idee ist, das hier ein einfaches System.out gemacht wird. (siehe IObjectLogZZZ)
	//##################################
	
	public void println(String sLog) throws ExceptionZZZ;
	public void println(String[] saLog) throws ExceptionZZZ;
	
	//!!! Merke 20240512: Mache in den (abstrakten) Klassen, die diese Methoden implementieren die Methoden "synchronized"
	public void printlnDate(String sLog) throws ExceptionZZZ;
	public void printlnDate(String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	public void printlnDateWithPosition(String sLog) throws ExceptionZZZ;
	public void printlnDateWithPosition(String... sLogs) throws ExceptionZZZ; //Nutzt intern KernelLogZZZ-statische Methode;
	
	
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
