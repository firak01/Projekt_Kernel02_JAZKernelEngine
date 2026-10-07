package basic.zBasic.util.system;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

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
	
	public static boolean println(String s) throws ExceptionZZZ{
		return SystemSingletonZZZ.getInstance().println(s,true);
	}
	
	public static boolean println(String s, boolean bPrintOutput) throws ExceptionZZZ{
		return SystemSingletonZZZ.getInstance().println(s,bPrintOutput);
	}
	
	//### Zur besseren Darstellung, besondere "Layoutelement"
	public static boolean printSection(String sTitle) throws ExceptionZZZ {
	    System.out.println();
	    printSeparator('=');
	    System.out.println(" " + sTitle);
	    printSeparator('=');
	    return true;
	}
	
	public static boolean printSeparator() throws ExceptionZZZ {
		return printSeparator('#');
	}
	
	public static boolean printSeparator(char cSeparator) throws ExceptionZZZ {
		String sLine = StringZZZ.repeatChar(cSeparator, 20);
		System.out.println(sLine);
		return true;
	}
}
