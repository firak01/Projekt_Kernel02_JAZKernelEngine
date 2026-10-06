package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

/** Idee ist, das diese Klasse mit dem kurzen Namen verwendet wird statt LogSingletonZZZ.getInstance(). ...
*  Dann hat diese Klasse noch Komfortfunktionen.
*  
*  Intern wird dann eine Singleton Klasse verwendet, die zudem noch per FLAGZ gesteuert werden könnte. 
* @author Fritz Lindhauer
*
*/
public class Log implements IConstantZZZ{
	private Log(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	public static boolean writeInfo(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeInfo(sInput);
	}
	
	public static boolean writeWarning(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeWarning(sInput);
	}
	
	public static boolean writeDebug(String sInput) throws ExceptionZZZ {
		return LogSingletonZZZ.getInstance().writeDebug(sInput);
	}
	
}
