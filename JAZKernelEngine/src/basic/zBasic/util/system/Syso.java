package basic.zBasic.util.system;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

/** Idee ist, das diese Klasse mit dem kurzen Namen verwendet wird statt System.out ...
 *  Dann hat diese Klasse noch Komfortfunktionen.
 *  
 *  Intern wird dann eine Singleton Klasse verwendet, die zudem noch per FLAGZ gesteuert werden könnte. 
 * @author Fritz Lindhauer
 *
 */
public class Syso implements IConstantZZZ{
	private Syso(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	public static void println(String s) throws ExceptionZZZ{
		SystemZZZ.getInstance().println(s,true);
	}
	
	public static void println(String s, boolean bPrintOutput) throws ExceptionZZZ{
		SystemZZZ.getInstance().println(s,bPrintOutput);
	}
}
