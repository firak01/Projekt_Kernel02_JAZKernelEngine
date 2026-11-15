package basic.zBasic.reflection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.longs.LongZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormaterZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormater4ReflectCodeZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zKernel.AbstractKernelLogZZZ;

/**Hintergrund dieser Klasse ist, das die statischen Methoden in der Klasse ReflectCodeZZZ immer eine Exception werfen.
* Ich möchte diese Methoden aber z.B. in der Erstellung von Werten eines Enums verwenden oder bei Konstanten.
* Daher duerfen (wie generell bei Feld - Initialisierungen) keine checked Exceptions geworfen werden.
* 
* Lösung: Rufe die Methoden der Klasse mit den Exceptions auf, fange diese aber einfach ab.
*         Beachte hier aber, das es 1 Codezeilenaufruf mehr ist.
*         Private Methoden braucht es hier nicht.
* 
* @author Fritz Lindhauer, 19.11.2025, 07:15:09
* 
*/
public class ReflectCodeUnensuredZZZ  implements IReflectCodeZZZ, IConstantZZZ{

	private ReflectCodeUnensuredZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	//+++ Fuer den Namen der .java - Datei
	public static String getMethodCurrentFileName() {
		try {
			return ReflectCodeZZZ.getMethodCurrentFileName(1); 
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/** use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	 * @return Return the name of the routine that called getCurrentMethodName
	 */
	  public static String getMethodCurrentFileName(int iOffset) {	
		try {
			return ReflectCodeZZZ.getMethodCurrentFileName(iOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	  
	/** @return Name des JavaFiles, welche die aktuelle Methode aufgerufen hat.
	* lindhaueradmin, 27.04.2024
	*/
	  public static String getMethodCallingFileName() {
		  try {
			return ReflectCodeZZZ.getMethodCallingFileName(1); //1 StacktracePostion weiter
		  } catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	  }
	  
	public static String getMethodCallingFileName(int iOffset) {		
		try {
			return ReflectCodeZZZ.getMethodCallingFileName(iOffset + 1); //1 StacktracePostion weiter
		  } catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	//++++++++++++++++++++++ Für den Namen der Klasse und Methode, ohne den Dateinamen
	public static String getMethodCurrentName() {
		try {
			return ReflectCodeZZZ.getMethodCurrentName(1+1);//1 StacktracePostion weiter
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentName(int iStacktraceOffset) {		  
			try {
				return ReflectCodeZZZ.getMethodCurrentName(iStacktraceOffset+1);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
	  
	  public static String getMethodCurrentNameLined() {
		  try {
				return ReflectCodeZZZ.getMethodCurrentNameLined(1);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
	  
	  public static String getMethodCurrentNameLined(int iLineOffset) {
		  try {
				return ReflectCodeZZZ.getMethodCurrentNameLined(iLineOffset+1);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
	  
	  /**
	 * use this method for the constructor of exceptionZZZ when throwing an exceptionZZZ, in an environment < JDK1.4
	 * in an enviroment >= JDK 1.4 use the constructor passing exception as a parameter !!!
	   * @return Return the name of the routine that called getCurrentMethodName
	   */
	  public static String getMethodCurrentNameLined(int iStacktraceOffset, int iLineOffset) {		  
		  try {
				return ReflectCodeZZZ.getMethodCurrentNameLined(iStacktraceOffset+1, iLineOffset);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
	  
	 
	  public static String getMethodCallingName() {
		  try {
				return ReflectCodeZZZ.getMethodCallingName(1);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
	  
	  public static String getMethodCallingName(int iStacktraceOffset) {
		  try {
				return ReflectCodeZZZ.getMethodCallingName(iStacktraceOffset+1);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	  }
		
	 public static String formatMethodCallingLine(int iLine) {         		 
		 try {
				return ReflectCodeZZZ.formatMethodCallingLine(iLine);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	 }
	 
	 public static String getFileCallingLine(String sFilePath, int iLine) {
		 try {
				return ReflectCodeZZZ.getFileCallingLine(sFilePath,iLine);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
	 }
	 
	 public static String formatFileCallingLine(int iLine) {
		 try {
				return ReflectCodeZZZ.formatFileCallingLine(iLine);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}		 
	 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	 public static String formatFileCallingLine(String sFilePath, int iLine) {
		 try {
				return ReflectCodeZZZ.formatFileCallingLine(sFilePath, iLine);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	 public static String formatFileCallingLineForConsole(String sFilePath, int iLine) {
		 try {
				return ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		 }
	 
	 /** Merke: Clickable ist das nur, wenn noch vorne ein Leerzeichen steht.
	  *         Das wird in der enum LOGSTRINGFORMAT (siehe ILogStringFormatZZZ) fuer die entsprechenden Formate der "FilePosition" schon gemacht.
	 * @param sFilePath
	 * @param iLine
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2026, 15:13:22
	 */
	public static String formatFileCallingLineForConsoleClickable(String sFilePath, int iLine) {
		try {
			return ReflectCodeZZZ.formatFileCallingLineForConsoleClickable(sFilePath, iLine);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	 }
	 
	
	 /**
	 * @return Zeile im Stacktrace für den Namen der Methode, welche die aktuelle Methode aufgerufen hat.
	 * lindhaueradmin, 23.07.2013
	 */
	public static int getMethodCallingLine() {
		try {
			return ReflectCodeZZZ.getMethodCallingLine(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		}
	}
	
	public static int getMethodCallingLine(int iStacktraceOffset) {
		try {
			return ReflectCodeZZZ.getMethodCallingLine(iStacktraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		}
	}
	
	public static int getMethodCurrentLine() {
		try {
			return ReflectCodeZZZ.getMethodCurrentLine(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		}
	}
	
	public static int getMethodCurrentLine(int iStackTraceOffset) {
		try {
			return ReflectCodeZZZ.getMethodCurrentLine(iStackTraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		}
	}
	
	public static int getMethodCurrentLine(int iStacktraceOffset, int iLineOffset) {
		try {
			return ReflectCodeZZZ.getMethodCurrentLine(iStacktraceOffset+1,iLineOffset);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		}
	}
	  
	  
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getClassCallingName() {
		try {
			return ReflectCodeZZZ.getClassCallingName(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCallingName(int iStacktraceOffset) {
		try {
			return ReflectCodeZZZ.getClassCallingName(iStacktraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	  }
	
		
	 /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCurrentName() {
		try {
			return ReflectCodeZZZ.getClassCurrentName(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	  }
	
	 /**
	 * @return Name (inkl. Package) der aktuellen Klasse
	 * lindhaueradmin, 23.07.2013
	 * @throws ExceptionZZZ 
	 */
	public static String getClassCurrentName(int iStacktracePositionOffset) {
		try {
			return ReflectCodeZZZ.getClassCurrentName(iStacktracePositionOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	  }
	
	public static String getPositionCurrentInFile() {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFile(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	
	public static String  getPositionCurrentInFile(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFile(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}		
	}
		
	public static String  getPositionCurrentInFile(String sFile, int iLine) {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFile(sFile,iLine+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	//#######################################################
	public static String getPositionCurrentInFileForConsoleClickable() {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFileForConsoleClickable(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String  getPositionCurrentInFileForConsoleClickable(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFileForConsoleClickable(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String  getPositionCurrentInFileForConsoleClickable(String sFile, int iLine) {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFileForConsoleClickable(sFile, iLine+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String  getPositionInFileForConsoleClickable(String sFile, int iLine) {
		try {
			return ReflectCodeZZZ.getPositionCurrentInFileForConsoleClickable(sFile, iLine+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
		
	public static String  getPositionCurrent() {
		try {
			return ReflectCodeZZZ.getPositionCurrent(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String  getPositionCurrent(int iStackTraceOffset) {
		try {
			return ReflectCodeZZZ.getPositionCurrent(iStackTraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++
	public static String  getPositionCalling() {
		try {
			return ReflectCodeZZZ.getPositionCalling(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//wie getPositionCallingPlus(iLevelPlus)
	public static String  getPositionCalling(int iLevelPlus) {
		try {
			return ReflectCodeZZZ.getPositionCalling(iLevelPlus+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//wie getPositionCalling(iLevelPlus)
	public static String  getPositionCallingPlus(int iLevelPlus) {
		try {
			return ReflectCodeZZZ.getPositionCallingPlus(iLevelPlus+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getPosition(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCallingPlus(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getPositionCurrentSeparated(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentSeparated(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
		
	public static String getPositionCurrentSeparated(Object obj, int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentSeparated(obj, iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	public static String getPositionCurrentSeparated(Class classObj, int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentSeparated(classObj, iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
		
	
	//####################
	
	/** Alte Version, vor dem Entwickeln der Formater - Klassen. Hier fehlt der Dateiname.
	 *  Trotzdem schon mit einem "Bündigmacher" für mögliche Folgekommentare.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 16.11.2025, 21:30:35
	 */
	public static String  getPositionCurrentSimple() {
		try {
			return ReflectCodeZZZ.getPositionCurrentSimple(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String  getPositionCurrentSimple(int iStackTraceOffset) {
		try {
			return ReflectCodeZZZ.getPositionCurrentSimple(iStackTraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	//##########################
	public static String  getPositionCallingSimple() {
		try {
			return ReflectCodeZZZ.getPositionCallingSimple(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//#####################################################################################
	//####################
	public static String  getPositionCurrentXml() {
		try {
			return ReflectCodeZZZ.getPositionCurrentXml(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String  getPositionCurrentXml(int iStackTraceOffset) {
		try {
			return ReflectCodeZZZ.getPositionCurrentXml(iStackTraceOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//####################
	public static String  getPositionCallingXml() {
		try {
			return ReflectCodeZZZ.getPositionCallingXml(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//wie getPositionCallingXmlPlus(iLevelPlus)
	public static String  getPositionCallingXml(int iLevelPlus) {
		try {
			return ReflectCodeZZZ.getPositionCallingXml(iLevelPlus+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//wie getPositionCallingXml(iLevelPlus)
	public static String  getPositionCallingXmlPlus(int iLevelPlus) {
		try {
			return ReflectCodeZZZ.getPositionCallingXmlPlus(iLevelPlus+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getPositionXml(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionXml(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	
	
	//#################################################################
	//####################
	public static String  getPositionCurrentXmlFormated() {
		try {
			return ReflectCodeZZZ.getPositionCurrentXmlFormated(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getPositionXmlFormated(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionXmlFormated(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
		
	/**Umgib die einzelen Elemente mit XML-Tags.
	 * Ganz einfach gehalten, weil grundliegende Klasse in zBasic-Bibliothek
	 * Im JAZLanguageMarkup-Projekt gibt es dafuer Komplexeres.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 19.05.2024, 14:59:04
	 * @throws ExceptionZZZ 
	 */
	public static String getPositionCurrentXmlFormated(int iLevel) {
		try {
			return ReflectCodeZZZ.getPositionCurrentXmlFormated(iLevel+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
		  
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   	Falls eine Class übergeben wird, kommt java.lang raus.
  		Das ist nicht gewünscht. Es sollte ein Objekt der Klasse instantiert werden 
		und davon der Pfad geholt werden
		z.B. String sPackagePath = ReflectCodeZZZ.getPackagePath(DebugWriterHtmlByXsltZZZ.class);
		
		Darum eine neue Methode mit Class als Argument.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getPackagePath(Object obj) {
		try {
			return ReflectCodeZZZ.getPackagePath(obj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}		
	  }
	
	 
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   *  REMARK: THIS METHOD THROWS NO EXCEPTION. THEN IT CAN BE USED FOR THE DECLARATION OF STATIC VARIABLES
	   *  
	   	Falls eine Class übergeben wird, kommt java.lang raus.
		Das ist nicht gewünscht. Es sollte ein Objekt der Klasse instantiert werden 
		und davon der Pfad geholt werden
		z.B. String sPackagePath = ReflectCodeZZZ.getPackagePath(DebugWriterHtmlByXsltZZZ.class);
		
		Darum eine neue Methode mit Class als Argument.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getPackagePathForConstant(Object obj) {
		try {
			return ReflectCodeZZZ.getPackagePathForConstant(obj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}		
	  }
	
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 */
	public static String getPackagePath(Class objClass) {
		try {
			return ReflectCodeZZZ.getPackagePath(objClass);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	  }
	
	 /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	   *  REMARK: THIS METHOD THROWS NO EXCEPTION. THEN IT CAN BE USED FOR THE DECLARATION OF STATIC VARIABLES 
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ  
	 */
	public static String getPackagePathForConstant(Class objClass) {
		try {
			return ReflectCodeZZZ.getPackagePathForConstant(objClass);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	  }
	
	  /** String, use this method for receiving the path to the current package. This will return a string containg File.seperator-character.
	   *  Remark: Using Class.getPackagePath will result in a String beginning with the word 'Package '.
	   *  
	* lindhaueradmin; 25.06.2006 12:51:08
	 * @param obj
	 * @return
	 */
	public static String getPackagePathByReflection(Class objClass) {
		try {
			return ReflectCodeZZZ.getPackagePathByReflection(objClass);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	  }
	
	
	
	/**  Gibt den reinen Klassennamen (also ohne . oder / ) zur�ck.
	 * Falls "this" in einer statischen Methode aufgerufen wird, so gibt es die gleiche Methode noch mit einem erwarteten class-Object als Parameter.
	 * 
	 * Merke: obj.getClass().getName() gibt auch den Pfad zur�ck. 
	 * 
	* @param obj
	* @return
	* 
	* lindhauer; 25.02.2008 10:40:28
	 */
	public static String getClassNameOnly(Object obj) {
		try {
			return ReflectCodeZZZ.getClassNameOnly(obj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}		
	}
	
	/** Gibt den reinen Klassennamen (also ohne . oder / ) zurück.
	 *   Diese Methode ist in statischen methoden zu verwenden, wenn kein "this" erlaubt ist.
	* @param classOfObject
	* @return
	* 
	* lindhauer; 26.02.2008 09:39:50
	 */
	public static String getClassNameOnly(Class classOfObject) {
		try {
			return ReflectCodeZZZ.getClassNameOnly(classOfObject);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	}
	
	public static String getClassFileName(Object obj) {
		try {
			return ReflectCodeZZZ.getClassNameOnly(obj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getClassFileName(Class classObj) {
		try {
			return ReflectCodeZZZ.getClassFileName(classObj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}		
	}
	
	
	public static String getClassFilePath(Object obj) {
		try {
			return ReflectCodeZZZ.getClassFilePath(obj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getClassFilePath(Class classObj) {
		try {
			return ReflectCodeZZZ.getClassFilePath(classObj);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Liefert den Namen einer aufrufenden Methode oder deren Aufrufer
	 * @param callerID - gibt an, wieviele Aufrufe oberhalb der aufrufenden Methode
	 * ausgegeben werden sollen. 0 liefert den Namen der aufrufenden Methode, 1 den
	 * Namen des uebergeordneten Aufrufers usw.
	 * @return - Name der Aufrufenden Methode
	 */
	public static String getCaller(int callerID) {
		try {
			return ReflectCodeZZZ.getCaller(callerID);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * @return
	 * @author Fritz Lindhauer, 12.06.2019, 10:08:16
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack() {
		try {
			return ReflectCodeZZZ.getCallingStack();
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	/**Gib ein Array der Methoden des Stacktrace zurück.
	 * In der Form von Klassenname.Methodenname, so dass dieser String als einfacher Schlüssel, 
	 * z.B. in einer HashMap verwendet werden kann.
	 * @param sClassnameFilterRegEx : Schränke die Array-Einträge ein, auf Klassen, die diesen RegEx Ausdruck haben.
	 * @return
	 * @author Fritz Lindhauer, 16.06.2019, 07:42:03
	 * @throws ExceptionZZZ 
	 */
	public static String[] getCallingStack(String sRegEx) {
		try {
			return ReflectCodeZZZ.getCallingStack(sRegEx);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	

	
	public static String getClassMethodString() {
		try {
			return ReflectCodeZZZ.getClassMethodString();
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getClassMethodString(StackTraceElement objStack) {
		try {
			return ReflectCodeZZZ.getClassMethodString(objStack);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getClassMethodCallingString() {
		try {
			return ReflectCodeZZZ.getClassMethodCallingString(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	public static String getClassMethodCallingString(int iOffset) {
		try {
			return ReflectCodeZZZ.getClassMethodCallingString(iOffset+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	
	
	/**Hohle den String, der aktuellen Klasse
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 13.08.2019, 07:22:51
	 */
	public static String getClassMethodCurrentString() {
		try {
			return ReflectCodeZZZ.getClassMethodCurrentString(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	
	
	
	
	
	/**
	 * Ermittelt den Namen der aufrufenden Funktion.
	 */
	public static String lastCaller(String className) {
		try {
			return ReflectCodeZZZ.lastCaller(className);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * Ermittelt die Tiefe des Aufrufstacks.
	 */
	public static int callStackSize() {
		try {
			return ReflectCodeZZZ.callStackSize();
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return -1;
		} 
    }
	
	public static StackTraceElement[] getStackTrace() {		
		try {
			return ReflectCodeZZZ.getStackTrace();
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	
	public static StackTraceElement[] getStackTrace(int iStartingLevelIn) {
		try {
			return ReflectCodeZZZ.getStackTrace(iStartingLevelIn+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		} 
	}
	
	//+++++++++++++++++
	public static StackTraceElement[] getStackTraceCalling() { 		
		try {
			return ReflectCodeZZZ.getStackTraceCalling(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static StackTraceElement[] getStackTraceCalling(int iStartingLevelIn) {
		try {
			return ReflectCodeZZZ.getStackTraceCalling(iStartingLevelIn+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static StackTraceElement[] getStackTrace(String sRegEx) {
		try {
			return ReflectCodeZZZ.getStackTrace(sRegEx);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	public static String getStackTraceString() {
		try {
			return ReflectCodeZZZ.getStackTraceString(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getStackTraceString(int iStackTraceOffset) throws ExceptionZZZ {	
		return ReflectCodeZZZ.getStackTraceString(iStackTraceOffset+1);
	}
	
	
	public static String[] getStackTraceStringArray() {
		try {
			return ReflectCodeZZZ.getStackTraceStringArray(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String[] getStackTraceStringArray(int iLevelIn) {
		try {
			return ReflectCodeZZZ.getStackTraceStringArray(iLevelIn+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getStackTraceCallingString() {
		try {
			return ReflectCodeZZZ.getStackTraceCallingString(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String[] getStackTraceCallingStringArray() {
		try {
			return ReflectCodeZZZ.getStackTraceCallingStringArray(1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String[] getStackTraceCallingStringArray(int iLevelIn) {
		try {
			return ReflectCodeZZZ.getStackTraceCallingStringArray(iLevelIn+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String getStackTraceCallingString(int iLevelIn) {
		try {
			return ReflectCodeZZZ.getStackTraceCallingString(iLevelIn+1);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String stackTraceToString(StackTraceElement[] stackTrace) {
		try {
			return ReflectCodeZZZ.stackTraceToString(stackTrace);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static String[] stackTraceToStringArray(StackTraceElement[] stacktrace) {
		try {
			return ReflectCodeZZZ.stackTraceToStringArray(stacktrace);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	public static void printStackTrace(StackTraceElement[] stackTrace) {
		try {
			ReflectCodeZZZ.printStackTrace(stackTrace);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();			
		}
	}
	
	public static void printStackTrace() {
		try {
			ReflectCodeZZZ.printStackTrace();
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();			
		}
	}
	
	public static void printStackTrace(String sMessage) {
		try {
			ReflectCodeZZZ.printStackTrace(sMessage);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();			
		}
	}
	
	
	/**Jetzt hat man die Postionsangaben mit Tags versehen, will sie dann aber wieder raus haben.
	 * Das ist z.B. im LogStringFormatManagerZZZ der Fall, der die LogStrings ohne "stoerende" Tags ausgeben will. 
	 * 
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.11.2025, 18:39:28
	 */
	public static String removePositionCurrentTagPartsFrom(String sXml) {
		try {
			return ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sXml);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
}
