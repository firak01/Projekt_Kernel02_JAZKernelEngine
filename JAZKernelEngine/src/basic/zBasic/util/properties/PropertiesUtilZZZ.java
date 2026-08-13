package basic.zBasic.util.properties;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

/** Utility Klasse zum Arbeiten mit Properties.
 *  Das ergänzt die Klasse KernelPropertyZZZ.
 * 
 * MERKE:
 * Java ignoriert Kommentarzeilen in .properties-Dateien automatisch beim Aufruf der load()-Methode. 
 * Sie müssen diese nicht manuell herausfiltern.
 * Kommentarzeichen in Properties:
 * # (Raute) am Zeilenanfang markiert einen Kommentar.
 * ! (Ausrufezeichen) am Zeilenanfang wird ebenfalls als Kommentar gewertet.
 * Leere Zeilen werden ebenfalls automatisch übersprungen.
 * 
 * @author Fritz Lindhauer
 *
 */
public class PropertiesUtilZZZ implements IConstantZZZ{
	
	public static boolean hasValue(Properties objProperties, String sValue) throws ExceptionZZZ {
	    if (objProperties == null || sValue == null) {
	        return false;
	    }

	    for (Object obj : objProperties.values()) {
	        if (sValue.equals(obj)) {
	            return true;
	        }
	    }

	    return false;
	}
	
	public static boolean hasValueIgnoreCase(Properties objProperties, String sValue) throws ExceptionZZZ {
	    if (objProperties == null || sValue == null) {
	        return false;
	    }

	    for (Object obj : objProperties.values()) {
	        if (sValue.equalsIgnoreCase(String.valueOf(obj))) {
	            return true;
	        }
	    }

	    return false;
	}
	
	//################
	public static Properties load(String sConfigFile)  throws ExceptionZZZ {
		Properties objReturn = null;
		main:{
			File fileConfigFile = new File(sConfigFile);
			objReturn = load(fileConfigFile);			
		}//END main:
		return objReturn;
	}
	
	public static Properties load(File fileConfigFile)  throws ExceptionZZZ {
		Properties objReturn = null;
		main:{
			try {
				objReturn = new Properties();			
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileConfigFile)));			
			
				objReturn.load(in);
				
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ(ioe);
				throw ez;
			}								
		}//END main:
		return objReturn;
	}
}
