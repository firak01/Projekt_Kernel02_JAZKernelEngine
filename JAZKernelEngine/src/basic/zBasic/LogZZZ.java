package basic.zBasic;

import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.system.Syso;
import custom.zKernel.KernelLogZZZ;

public class LogZZZ<T> extends AbstractLogZZZ<T> {
	private static final long serialVersionUID = -4674809839183596083L;

	//#####################
	//### Methoden hier static zur Verfuegung stellen.
	//### Damit koennen diese auch in static Methoden genutzt werden.
	//#####################	
	
	//#############################################
	//### log... ohne Protocol sind für die Ausgabe auf der Konsole gedacht.
	//###        Daher wird hier über meine Syso-Klasse nur system.out verwendet.
    //##############################################

	

	//##### Gib den String aus.
	//  Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//  Das dann jeweils als Variante mit einer Klasse als Argument
	//public static void logLine(String sLog) throws ExceptionZZZ{		
//	public void logLine(String sLog) throws ExceptionZZZ{
//		String sTemp = KernelLogZZZ.computeLine(this.getClass(), sLog);
//		Syso.println(sTemp);
//	}
//	
//	//public static void logLine(String[] saLog) throws ExceptionZZZ{		
//	public void logLine(String[] saLog) throws ExceptionZZZ{
//		String sTemp = KernelLogZZZ.computeLine(this.getClass(), saLog);
//		Syso.println(sTemp);
//	}
	
	public static void println(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(obj, (IStringFormatManagerZZZ) null, sLog);
		Syso.println(sTemp);
	}
	
	public static void println(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(obj, saLog);
		Syso.println(sTemp);
	}
	
	public static void println(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(classObj, sLog);
		Syso.println(sTemp);
	}
	
	public static void println(Class classObj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLine(classObj, saLog);
		Syso.println(sTemp);
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static void printlnDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(obj, sLog);				
		Syso.println(sTemp);
	}
	
	public static void printlnDate(Object obj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(obj, saLog);				
		Syso.println(sTemp);
	}
	
	public static void printlnDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(classObj, sLog);				
		Syso.println(sTemp);
	}
	public static void printlnDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computeLineDate(classObj, saLog);				
		Syso.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	public static void printlnDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(obj, 1, sLog);
		Syso.println(sTemp);
	}
	
	public static void printlnDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(obj, 1, saLog);
		Syso.println(sTemp);
	}
	
	public static void printlnDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(classObj, 1, sLog);				
		Syso.println(sTemp);
	}
	
	public static void printlnDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computeLineDateWithPosition(classObj, saLog);				
		Syso.println(sTemp);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
}
