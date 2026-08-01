package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class JarEasyUrlZZZ implements IConstantZZZ{

	public static String computeUrlPathForContainingResource(JarFile objJar, String sResourcePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objJar==null) break main;
			if(StringZZZ.isEmpty(sResourcePath)) break main;
				
			String sJarFile = objJar.getName();
			sReturn = "jar:file:/" + sJarFile + "!/" + sResourcePath; //Merke das Ausrufezeichen ist wichtig. Sonst Fehler: no !/ in spec
		}//end main:
		return sReturn;
	}

	/** Wenn man einen Pfad innerhalb einer JAR-Datei übergibt, bekommt man hier die JAR-als File Objekt zurück.
	 *  https://stackoverflow.com/questions/8014099/how-do-i-convert-a-jarfile-uri-to-the-path-of-jar-file
	 *  Eine URL wird aussehen wie in JarEasyZZZ.computeUrlPathForContainingResource(...) erstellt.
	 * @author Fritz Lindhauer, 19.07.2020, 08:43:29
	 * @throws ExceptionZZZ 
	 */
	public static File getJarCurrentFromUrl(JarFile objJarFile, String sUrl) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			try {
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//							
			//String sUrl = JarEasyZZZ.computeUrlPathForContainingResource(objJarFile, sDirectoryPath); 
	//		String sUrl = JarEasyZZZ.computeUrlPathForContainingResource(objJarFile, sKey);
	//		String sLog = ReflectCodeZZZ.getPositionCurrent()+": String to fetch URL from JarFileObject '" + sUrl + "'" ;
	//	    System.out.println(sLog);			   
		    
			URL url = new URL(sUrl);		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": URL created from JarFileObject '" + url + "'" ;
		    System.out.println(sLog);
		    
			
			//DAS Holt immer nur die JAR - Datei selbst:
		    JarURLConnection connection = (JarURLConnection) url.openConnection();
			URI uri = connection.getJarFileURL().toURI();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": URI.getPath created from JarFileObject '" + uri.getPath() + "'" ;
		    System.out.println(sLog);
		    	    
		    objReturn = new File(uri);
	    
			} catch (MalformedURLException e) {
				ExceptionZZZ ez = new ExceptionZZZ("MalformedURLException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (URISyntaxException e) {
				ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException '" + e.getMessage() + "'", iERROR_PARAMETER_VALUE, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}

}
