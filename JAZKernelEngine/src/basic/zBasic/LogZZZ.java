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
	//### println... ohne protocol sind für die Ausgabe auf der Konsole gedacht.
	//### Daher wird hier über die Syso-Klasse nur system.out verwendet.
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
	
	public static boolean println(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(obj, (IStringFormatManagerZZZ) null, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(obj, saLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Class classObj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean println(Class classObj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computeln(classObj, saLog);
		return Syso.println(sTemp);
	}
	
		
	//##### Gib das Datum aus. 
	//      Die Position des Datums im String wird durch eine Formatanweisung definiert.
	//      Das dann jeweils als Variante mit einer Klasse als Argument
	public static boolean printlnDate(Object obj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, sLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDate(Object obj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(obj, saLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDate(Class classObj, String sLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, sLog);				
		return Syso.println(sTemp);
	}
	public static boolean printlnDate(Class classObj, String[] saLog) throws ExceptionZZZ{
		String sTemp = KernelLogZZZ.computelnDate(classObj, saLog);				
		return Syso.println(sTemp);
	}
	
	//#### Gib die Codeposition aus.
	//     Die Position der Codepostion im String wird durch eine Formatanweisung definiert.
	//     Das dann jeweils als Variante mit einer Klasse als Argument
	public static boolean printlnDateWithPosition(Object obj, String sLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, sLog);
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Object obj, String[] saLog) throws ExceptionZZZ{		
		String sTemp = KernelLogZZZ.computelnDateWithPosition(obj, 1, saLog);
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, 1, sLog);				
		return Syso.println(sTemp);
	}
	
	public static boolean printlnDateWithPosition(Class classObj, String[] saLog) throws ExceptionZZZ{				
		String sTemp = KernelLogZZZ.computelnDateWithPosition(classObj, saLog);				
		return Syso.println(sTemp);
	}

	

	//++++++++++++++++++++++++++++++++++++++++++++++++
}
