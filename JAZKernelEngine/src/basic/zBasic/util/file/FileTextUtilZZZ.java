package basic.zBasic.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class FileTextUtilZZZ  extends AbstractObjectWithExceptionZZZ implements IFileEasyConstantsZZZ{
	private static final long serialVersionUID = 6374706096776767564L;

	private FileTextUtilZZZ(){
		//Zum Verstecken des Konstruktors, sind halt nur static MEthoden
	}

    /** 
     * Hinweis:
readLine() entfernt den Zeilenumbruch, darum wird er wieder angehängt.
Funktioniert vollständig mit Java 1.7.
Für sehr große Dateien wäre ein Streaming-Ansatz besser, aber für normale Textdateien ist das passend.
     * @param file
     * @return
     * @throws Exception
     */
    public static String readFileToString(File file) throws ExceptionZZZ{
    	String sReturn = null;
    	main:{
	        try {
	        	if(file==null)break main;
	    		if(file.exists()) break main;
	    		
	    		StringBuilder sb = new StringBuilder();
	
	            BufferedReader br = null;
		        try {
		            br = new BufferedReader(new FileReader(file));
		
		            String line;
		            while ((line = br.readLine()) != null) {
		                sb.append(line);
		                sb.append(StringZZZ.crlf());                
		            }
		        
		        } finally {
		            if (br != null) {
		                br.close();
		            }
		        }
		        return sb.toString();
		        
	        }catch(Exception e) {
	        	ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
	        }
    	}//end main:
    	return sReturn;
    }
    
    public static List<String> readFileToList(File file) throws ExceptionZZZ {
    	List<String> listaReturn = null;
    	main:{
    		try {
	    		if(file==null)break main;
	    		if(file.exists()) break main;
	    		
	    		 BufferedReader br = null;
	    		try {
	    			br = new BufferedReader(new FileReader(file));
		
	    			listaReturn = new ArrayList<String>();
		
			        String line;
			        while ((line = br.readLine()) != null) {
			        	listaReturn.add(line);
			        }		       
	    		} finally {
		            if (br != null) {
		                br.close();
		            }
		        }
		        		        
	    	}catch(Exception e) {
	        	ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
	        }
    	}//end main:
    	return listaReturn;
    }
}
