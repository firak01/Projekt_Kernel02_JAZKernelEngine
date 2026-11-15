package basic.zBasic.util.machine;

import java.net.InetAddress;
import java.net.UnknownHostException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 23.01.2020, 09:30:25
 * 
 */
public class EnvironmentZZZ implements IConstantZZZ{
	
	private EnvironmentZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	public static String getHostName() throws ExceptionZZZ{
		String sReturn = null;
		try {			
			InetAddress objInet = InetAddress.getLocalHost();
			sReturn = objInet.getHostName();						
		} catch (UnknownHostException e) {			
			ExceptionZZZ ez = new ExceptionZZZ("UnknownHostException: ", iERROR_RUNTIME,  EnvironmentZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return sReturn;		
	}
	
	
	public static String getHostIp() throws ExceptionZZZ{
		String sReturn = null;
		try {			
			InetAddress objInet = InetAddress.getLocalHost();
			sReturn = objInet.getHostAddress();						
		} catch (UnknownHostException e) {			
			ExceptionZZZ ez = new ExceptionZZZ("UnknownHostException: ", iERROR_RUNTIME,  EnvironmentZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		return sReturn;		
	}
	
	public static String getHostDirectoryTemp() throws ExceptionZZZ{
		String sReturn = null;
//		try {
			sReturn = System.getProperty("java.io.tmpdir");
//		} catch (UnknownHostException e) {			
//			ExceptionZZZ ez = new ExceptionZZZ("UnknownHostException: ", iERROR_RUNTIME,  EnvironmentZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
//			throw ez;
//		}
		return sReturn;		
	}
	
	
	/**The bug you reference (bug 4787391) has been fixed in Java 8. 
	 * Even if you are using an older version of Java, the System.getProperty("user.home") approach is probably still the best. The user.home approach seems to work in a very large number of cases. A 100% bulletproof solution on Windows is hard, because Windows has a shifting concept of what the home directory means.

If user.home isn't good enough for you I would suggest choosing a definition of home directory for windows and using it, getting the appropriate environment variable with System.getenv(String).
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 14.01.2021, 18:23:26
	 */
	public static String getUserHome() throws ExceptionZZZ{
		String sReturn = null;
		//try {
			sReturn = System.getProperty("user.home");			
		//}
		return sReturn;
	}
}
