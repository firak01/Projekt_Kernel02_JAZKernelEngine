package basic.zBasic.reflection;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/** Reflection-Klassen, die sich auf die Kernel-Klassen biezieht.
 *  Die Methoden wie in der Klasse ReflectCodeZZZ sind hier enthalten, 
 *  aber wenn es z.B. darum geht einen aufrufenden Methodennamen zu ermitteln, schränkt man sich auf die KernelZZZ Klassen ein. 
 *  
 * 
 * @author lindhaueradmin, 08.08.2019, 08:52:07
 * 
 */
public class ReflectCodeKernelZZZ implements IConstantZZZ{
	public static String sREGEX_KERNEL_CLASS = "(ZZZ$|ZZZ(T|t)est)$"; //Also: Am Ende des Klassennamens soll ZZZ stehen, bzw. ZZZTest, aber nicht der Reflection-Klassenname
	
	private ReflectCodeKernelZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * 
	 * Besonderheit:
	 * Schränke dies auf ZZZ-Klassen ein 
	 * UND filtere ggfs. aufrufenden ReflectCode - Klassen aus, also z.B. den Aufruf selbst.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 12.06.2019, 10:08:16
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack() throws ExceptionZZZ{
		//Schränke die Suche auf Kernel-Klassen ein.
		String sRegEx = ReflectCodeKernelZZZ.sREGEX_KERNEL_CLASS; 	
		return ReflectCodeZZZ.getCallingStack(sRegEx);
	}
	
	public static StackTraceElement[] getStackTrace() throws ExceptionZZZ{
		//Schränke die Suche auf Kernel-Klassen ein.
		String sRegEx = ReflectCodeKernelZZZ.sREGEX_KERNEL_CLASS;
		return ReflectCodeZZZ.getStackTrace(sRegEx);
	}
	
	public static StackTraceElement getCallingStackTraceElement() throws ExceptionZZZ{
		StackTraceElement objReturn = null;
		main:{
			objReturn = ReflectCodeKernelZZZ.getCallingStackTraceElement(0);
		}//end main:
		return objReturn;
	}
	
	public static StackTraceElement getCallingStackTraceElement(int iOffset) throws ExceptionZZZ{
		StackTraceElement objReturn = null;
		main:{
			StackTraceElement[] stackTrace = ReflectCodeKernelZZZ.getStackTrace();
			if(stackTrace.length>=4){
				int iPositionInStacktrace = ReflectCodeZZZ.iPOSITION_STACKTRACE_CALLING + iOffset;
				objReturn = stackTrace[iPositionInStacktrace];				
			}		
		}//end main:
		return objReturn;
	}
	
	
	public static String getCallingClassMethod() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			StackTraceElement objStack = ReflectCodeKernelZZZ.getCallingStackTraceElement();
			sReturn = ReflectCodeZZZ.getClassMethodString(objStack);			
		}//end main
		return sReturn;
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * 
	 * Besonderheit 1:
	 * Im Ausgangsstacktrace sind nur Kernel-Klassen.Methodennamen enthalten.
	 * 
	 * Besonderheit 2:
	 * Nur die Methoden sind darin enthalten, die von einer anderen Klasse ("external") stammen als die aktuell ausführende Klasse.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 12.06.2019, 10:08:16
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStackExternal() throws ExceptionZZZ{
		return ReflectCodeKernelZZZ.getCallingStackExternal(2);
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * 
	 * Besonderheit 1:
	 * Im Ausgangsstacktrace sind nur Kernel-Klassen.Methodennamen enthalten.
	 * 
	 * Besonderheit 2:
	 * Nur die Methoden sind darin enthalten, die von einer anderen Klasse ("external") stammen als die aktuell ausführende Klasse.
	 * 
	 * @param sClassnameFilterRegEx : Schränke die Array-Einträge ein, auf Klassen, die diesen RegEx Ausdruck haben.
	 * @return
	 * @author Fritz Lindhauer, 16.06.2019, 07:42:03
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStackExternal(int iOffset) throws ExceptionZZZ{
		String[] saReturn=null;
		main:{		
		//++++++++++
		//1. Name der aktuellen Klasse
		String sClass = ReflectCodeZZZ.getClassCurrentName(iOffset);
		
		//2. Der komplette Stacktrace.
		String[] saCallingStack = ReflectCodeKernelZZZ.getCallingStack();

		//3. Hole die Indexposition der "external" Methode.
		//Diese Sortierreihenfolge ist wichtig. Ansonsten besteht die Gefahr von Elternklassen, wie z.B. ObjectZZZ, dass Sie ja auch einen anderen Klassennamen haben und im Stacktrace stehen.		
		String sCallingStackPrevious = null; 
		int iCallingStackPrevious=saCallingStack.length-1;
		int iCallingStack = iCallingStackPrevious;
		for(int icount=saCallingStack.length-1; icount>=0;icount--){
			String sCallingStack=saCallingStack[icount];
			
			//3. Suche den Namen der aktuellen Klasse links vom "."
			String sCallingClass = StringZZZ.leftback(sCallingStack, ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR);
					
			//4. Suche die andere Klasse, die vorher aufgerufen woden ist.
			if(sCallingClass.equals(sClass)){
				//andere Klasse gefunden 
				//sReturn = sCallingStackPrevious;
				iCallingStack = iCallingStackPrevious;
				break;
			}else{
				//andere Klasse gefunden
				//5. Gib den gefundenene Klasse.Methode String zurück
				//sReturn = sCallingStack;
				//break;
			}
			sCallingStackPrevious = sCallingStack;
			iCallingStackPrevious = icount;
		}
	
		//5. Baue den neuen Stacktrace zusammen
		ArrayList<String>listasTemp = new ArrayList<String>();
		for(int icount = iCallingStack; icount <= saCallingStack.length-1;icount++){
			listasTemp.add(saCallingStack[icount]);
		}
		
		saReturn = ArrayListUtilZZZ.toStringArray( listasTemp);
	}//end main
	return saReturn;
	}
	
	
	/** Holle den CallingString von einer Methode, die nicht der gleichen Klasse - wie die aktuell behandelte - entspricht.
	 * 
	 * Besonderheit:
	 * Im Ausgangsstacktrace sind nur Kernel-Klassen.Methodennamen enthalten.
	 * 
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:20:28
	 */
	public static String getClassMethodExternalCallingString() throws ExceptionZZZ{
		return ReflectCodeKernelZZZ.getClassMethodExternalCallingString(2);//1 wäre die Methode, die  diese aufruft, aber um die echte auf-aufrufende Methode zu bekommen ein weiteres +1
	}
	/** Holle den CallingString von einer Methode, die nicht der gleichen Klasse - wie die aktuell behandelte - entspricht.
	 * 
	 * Besonderheit:
	 * Im Ausgangsstacktrace sind nur Kernel-Klassen.Methodennamen enthalten.
	 * @param iOffset
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:20:28
	 */
	public static String getClassMethodExternalCallingString(int iOffset) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			//1. Name der aktuellen Klasse
			String sClass = ReflectCodeZZZ.getClassCurrentName(iOffset);
			
			//2. Der komplette Stacktrace.
			String[] saCallingStack = ReflectCodeKernelZZZ.getCallingStack();
									
			//Diese Sortierreihenfolge ist wichtig. Ansonsten besteht die Gefahr von Elternklassen, wie z.B. ObjectZZZ, dass Sie ja auch einen anderen Klassennamen haben und im Stacktrace stehen.
			String sCallingStackPrevious = null;
			for(int icount=saCallingStack.length-1; icount>=0;icount--){
				String sCallingStack=saCallingStack[icount];
				
				//3. Suche den Namen der aktuellen Klasse links vom "."
				String sCallingClass = StringZZZ.leftback(sCallingStack, ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR);
						
				//4. Suche die andere Klasse, die vorher aufgerufen woden ist.
				if(sCallingClass.equals(sClass)){
					//andere Klasse gefunden 
					sReturn = sCallingStackPrevious;
					break;
				}else{
					//andere Klasse gefunden
					//5. Gib den gefundenene Klasse.Methode String zurück
					//sReturn = sCallingStack;
					//break;
				}
				sCallingStackPrevious = sCallingStack;
			}
		}
		return sReturn;
	}

}
