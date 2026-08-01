package basic.zBasic.util.file.jar;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.PlatformZZZ;

/**siehe
 *  https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
 * Methoden sollen stammen aus: SciJava Common library.
    org.scijava.util.ClassUtils
    org.scijava.util.FileUtils.

 * @author Fritz Lindhauer, 06.08.2020, 09:13:17
 * 
 */
public class JarEasyHelperZZZ {
	/**
	 * Gets the base location of the given class.
	 * <p>
	 * If the class is directly on the file system (e.g.,
	 * "/path/to/my/package/MyClass.class") then it will return the base directory
	 * (e.g., "file:/path/to").
	 * </p>
	 * <p>
	 * If the class is within a JAR file (e.g.,
	 * "/path/to/my-jar.jar!/my/package/MyClass.class") then it will return the
	 * path to the JAR (e.g., "file:/path/to/my-jar.jar").
	 * </p>
	 *
	 * @param c The class whose location is desired.
	 * @throws ExceptionZZZ 
	 * @see FileUtils#urlToFile(URL) to convert the result to a {@link File}.
	 */
	public static URL getLocation(final Class<?> c) throws ExceptionZZZ {
	    if (c == null) return null; // could not load the class

	    // try the easy way first
	    try {
//	    	String sLog = ReflectCodeZZZ.getPositionCurrent()+": A) getProtectionDomain";
//		    System.out.println(sLog);
	        final URL codeSourceLocation =
	            c.getProtectionDomain().getCodeSource().getLocation();
	        if (codeSourceLocation != null) return codeSourceLocation;
	    }
	    catch (final SecurityException e) {
	        // NB: Cannot access protection domain.
	    }
	    catch (final NullPointerException e) {
	        // NB: Protection domain or code source is null.
	    }

	    // NB: The easy way failed, so we try the hard way. We ask for the class
	    // itself as a resource, then strip the class's path from the URL string,
	    // leaving the base path.

	    // get the class's raw resource path
//	    String sLog = ReflectCodeZZZ.getPositionCurrent()+": B1) class.getResource";
//	    System.out.println(sLog);
	    final URL classResource = c.getResource(c.getSimpleName() + ".class");
	    if (classResource == null) return null; // cannot find class resource

//	    sLog = ReflectCodeZZZ.getPositionCurrent()+": B2) class.getResource";
//	    System.out.println(sLog);
	    final String url = classResource.toString();
	    final String suffix = c.getCanonicalName().replace('.', '/') + ".class";
	    if (!url.endsWith(suffix)) return null; // weird URL

	    // strip the class's path from the URL string
//	    sLog = ReflectCodeZZZ.getPositionCurrent()+": B3) class.getResource";
//	    System.out.println(sLog);
	    final String base = url.substring(0, url.length() - suffix.length());

	    String path = base;

	    // remove the "jar:" prefix and "!/" suffix, if present
	    if (path.startsWith("jar:")) path = path.substring(4, path.length() - 2);

	    try {
//	    	sLog = ReflectCodeZZZ.getPositionCurrent()+": B4) class.getResource";
//	 	    System.out.println(sLog + "path="+path);
	        return new URL(path);
	    }
	    catch (final MalformedURLException e) {
	        e.printStackTrace();
	        return null;
	       
	        //GGFS. Die Exceptions aus ExceptionZZZ umbiegen und diese werfen.
//	    } catch (URISyntaxException e) {
//			ExceptionZZZ ez = new ExceptionZZZ("URISyntaxException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
//			throw ez;			
//		}
	    }
	} 

	/**
	 * Converts the given {@link URL} to its corresponding {@link File}.
	 * <p>
	 * This method is similar to calling {@code new File(url.toURI())} except that
	 * it also handles "jar:file:" URLs, returning the path to the JAR file.
	 * </p>
	 * 
	 * @param url The URL to convert.
	 * @return A file path suitable for use with e.g. {@link FileInputStream}
	 * @throws ExceptionZZZ 
	 * @throws IllegalArgumentException if the URL does not correspond to a file.
	 */
	public static File urlToFile(final URL url) throws ExceptionZZZ {
	    return url == null ? null : urlToFile(url.toString());
	}

	/**
	 * Converts the given URL string to its corresponding {@link File}.
	 * 
	 * @param url The URL to convert.
	 * @return A file path suitable for use with e.g. {@link FileInputStream}
	 * @throws ExceptionZZZ 
	 * @throws IllegalArgumentException if the URL does not correspond to a file.
	 */
	public static File urlToFile(final String url) throws ExceptionZZZ {
		File objReturn = null;
		main:{
		if(url==null)break main;
	    String path = url;
	    ObjectZZZ.logLineDate(JarEasyHelperZZZ.class, "(D0) url= '"+url.toString()+"'");
	    if (path.startsWith("jar:")) {
	        // remove "jar:" prefix and "!/" suffix
	        final int index = path.indexOf("!/");
	        path = path.substring(4, index);
//	        sLog = ReflectCodeZZZ.getPositionCurrent()+": D1) path= '"+path+"'";
//			System.out.println(sLog);
	    }
	    try {
	        if (PlatformZZZ.isWindows() && path.matches("file:[A-Za-z]:.*")) {
	            path = "file:/" + path.substring(5);
//	            sLog = ReflectCodeZZZ.getPositionCurrent()+": D2) path= '"+path+"'";
//				System.out.println(sLog);
				objReturn = new File(new URL(path).toURI());
		        break main;
	        }
	    }
	    catch (final MalformedURLException e) {
	        // NB: URL is not completely well-formed.
	    }
	    catch (final URISyntaxException e) {
	        // NB: URL is not completely well-formed.
	    }
	    if (path.startsWith("file:")) {
	        // pass through the URL as-is, minus "file:" prefix
	        path = path.substring(5);
//	        sLog = ReflectCodeZZZ.getPositionCurrent()+": D3) path= '"+path+"'";
//			System.out.println(sLog);
			
			//minus slash etc. from the left
			String[] saStrips = {"/"};
			path = StringZZZ.stripLeft(path, saStrips);
//			sLog = ReflectCodeZZZ.getPositionCurrent()+": D4) path= '"+path+"'";
//			System.out.println(sLog);
			
	        objReturn = new File(path);
	        break main;
	    }
	   
//	    sLog = ReflectCodeZZZ.getPositionCurrent()+": D5) path= '"+path+"'";
//		System.out.println(sLog);
	    
	    //Wenn es bis hierher nicht gelingen ist ein File-Objekt zu erzeugen:
	    throw new IllegalArgumentException("Invalid URL: " + url);
	}//end main:
	return objReturn;
}
}
