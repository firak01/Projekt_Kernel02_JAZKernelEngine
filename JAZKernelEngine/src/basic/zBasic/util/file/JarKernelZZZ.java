package basic.zBasic.util.file;

import java.io.File;
import java.util.jar.JarFile;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.IJarEasyConstantsZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;

public class JarKernelZZZ extends AbstractObjectWithExceptionZZZ implements IJarEasyConstantsZZZ{
	
	private JarKernelZZZ() {
		//Zum Verstecken des Konstruktors
	}

	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static JarFile getJarFileDummy() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DUMMY";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_DUMMY);
	}

	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static File getJarFileDummyAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DUMMY";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_DUMMY);
	}

	public static JarFile getJarFileKernel() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_KERNEL);
	}

	public static File getJarFileKernelAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_KERNEL);
	}

	public static JarFile getJarFileTest() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_TEST);
	}

	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static File getJarFileTestZipAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_TESTZIP);
	}
	
	
	public static JarFile getJarFileTestZip() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsed(JarEasyUtilZZZ.iJAR_TESTZIP);
	}

	/**Für Tests, z.B. ob eine Resource in der gleichen Jar-Datei liegt.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 03.11.2020, 10:34:34
	 */
	public static File getJarFileTestAsFile() throws ExceptionZZZ{
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS TEST";
		System.out.println(sLog);
		
		return JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_TEST);
	}
	

	public static JarFile getJarFileUsed(int iConstantKeyZZZ) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			objReturn = JarKernelZZZ.getJarFileByConstantKey_(iConstantKeyZZZ);		
		}//end main:
		return objReturn;
	}

	public static File getJarFileUsedAsFile(int iConstantKeyZZZ) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			objReturn = JarKernelZZZ.getJarFileByConstantKeyAsFile_(iConstantKeyZZZ);
		}//end main:
		return objReturn;
	}

	private static JarFile getJarFileByConstantKey_(int iConstantKeyZZZ) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			switch(iConstantKeyZZZ){
			case JarEasyUtilZZZ.iJAR_DUMMY:
				objReturn = JarKernelZZZ.getJarFile_(JarKernelZZZ.sJAR_DIRECTORYPATH_DUMMY, JarKernelZZZ.sJAR_FILENAME_DUMMY);
				break;
			case JarEasyUtilZZZ.iJAR_TEST:
				objReturn = JarKernelZZZ.getJarFile_(JarKernelZZZ.sJAR_DIRECTORYPATH_TEST, JarKernelZZZ.sJAR_FILENAME_TEST);
				break;
			case JarEasyUtilZZZ.iJAR_TESTZIP:
				objReturn = JarKernelZZZ.getJarFile_(JarKernelZZZ.sJAR_DIRECTORYPATH_TESTZIP, JarKernelZZZ.sJAR_FILENAME_TESTZIP);
				break;
			case JarEasyUtilZZZ.iJAR_KERNEL:
				objReturn = JarKernelZZZ.getJarFile_(JarKernelZZZ.sJAR_DIRECTORYPATH_KERNEL, JarKernelZZZ.sJAR_FILENAME_KERNEL);
				break;
			case JarEasyUtilZZZ.iJAR_OVPN:
				objReturn = JarKernelZZZ.getJarFile_(JarKernelZZZ.sJAR_DIRECTORYPATH_OVPN, JarKernelZZZ.sJAR_FILENAME_OVPN);
				break;
			default:
				ExceptionZZZ ez = new ExceptionZZZ("Constant for this key not defined: " + iConstantKeyZZZ, iERROR_PARAMETER_VALUE, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		return objReturn;
	}

	private static File getJarFileByConstantKeyAsFile_(int iConstantKeyZZZ) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			JarFile objJar = JarKernelZZZ.getJarFileByConstantKey_(iConstantKeyZZZ);
			objReturn = JarEasyUtilZZZ.toFile(objJar);
		}
		return objReturn;
	}

	private static File getJarFileAsFile_(String sFileDirectory, String sFileName) throws ExceptionZZZ {
		File objReturn = null;
		main:{				
					//1. Suche: Im Eclipse Workspace. Das wäre ggfs. für Ressource angesagt.
					if(StringZZZ.isEmpty(sFileName)) {
						ExceptionZZZ ez = new ExceptionZZZ("No JarFileName provided", iERROR_PARAMETER_MISSING, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}					
					
					//2. Suche in einem absoluten Verzeichnis. Das wäre der Normalfall beim Testen.
					if(StringZZZ.isEmpty(sFileDirectory)) {
						ExceptionZZZ ez = new ExceptionZZZ("No JarFileDirectory provided", iERROR_PARAMETER_MISSING, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}				
					String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFileDirectory, sFileName);
					String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE '" + sFilePathTotal + "'";
					System.out.println(sLog);
					File objTemp  = new File(sFilePathTotal);
					if(objTemp.exists()) {
						objReturn = objTemp;
						break main;
					}
										
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) UNABLE TO FIND JAR FILE";
					System.out.println(sLog);			
		}//end main:
		return objReturn;		
	}

	private static JarFile getJarFile_(String sFileDirectory, String sFileName) throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{		
			File objFile = getJarFileAsFile_(sFileDirectory, sFileName);
			if(objFile!=null) {
				objReturn = JarEasyUtilZZZ.toJarFile(objFile);
			}			
		}//end main:
		return objReturn;	
	}

	public static File getJarFileDefaultAsFile() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) SEARCHING FOR DEFAULT JAR-FILE.";
			System.out.println(sLog);
			
			File objFileExcecutionDirectory = JarEasyUtilZZZ.getCodeLocationUsed();
			if(objFileExcecutionDirectory==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND.";
			    System.out.println(sLog);
				break main;
			}else if(objFileExcecutionDirectory.isFile()) { 
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND IS NOT A DIRECTORY, RETURNING NULL";
				    System.out.println(sLog);
				    break main; //Aufgeben....
			}else if(objFileExcecutionDirectory.isDirectory()) {  // Run with Eclipse or so.
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) EXECUTION DIRECTORY FOUND, RUNNING IN DIRECTORY: '" + objFileExcecutionDirectory.getAbsolutePath() +"'";
				System.out.println(sLog);
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM WORKSPACE AND FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = getJarFileAsFile_(objFileExcecutionDirectory.getAbsolutePath(),JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
				if(objReturn!=null) break main;
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = getJarFileAsFile_(JarEasyTestCommonsZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DD) UNEXPECTED CASE.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}

	public static File getJarFileDefaultAsFile(String sKey) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) SEARCHING FOR DEFAULT JAR-FILE.";
			System.out.println(sLog);
			
			File objFileExcecutionDirectory = JarEasyUtilZZZ.getCodeLocationUsed();
			if(objFileExcecutionDirectory==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND.";
			    System.out.println(sLog);
				break main;
			}else if(objFileExcecutionDirectory.isFile()) { 
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) NO EXECUTION DIRECTORY FOUND IS NOT A DIRECTORY, RETURNING NULL";
				    System.out.println(sLog);
				    break main; //Aufgeben....
			}else if(objFileExcecutionDirectory.isDirectory()) {  // Run with Eclipse or so.
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) EXECUTION DIRECTORY FOUND, RUNNING IN DIRECTORY: '" + objFileExcecutionDirectory.getAbsolutePath() +"'";
				System.out.println(sLog);
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM WORKSPACE AND FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = getJarFileAsFile_(objFileExcecutionDirectory.getAbsolutePath(),JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
				if(objReturn!=null) break main;
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) USING JAR FILE FROM CONSTANTS DEFAULT";
				System.out.println(sLog);
				objReturn = getJarFileAsFile_(JarEasyTestCommonsZZZ.sJAR_DIRECTORYPATH_KERNEL,JarEasyTestCommonsZZZ.sJAR_FILENAME_KERNEL);
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DD) UNEXPECTED CASE.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}

	public static JarFile getJarFileCurrent() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			final File jarFile = JarKernelZZZ.getJarFileCurrentAsFile();
			objReturn = JarEasyUtilZZZ.toJarFile(jarFile);
		}//end main:
		return objReturn;
	}

	public static File getJarFileCurrentAsFile() throws ExceptionZZZ {
		File objReturn = null;
		main:{			
			String sLog = null;
			final File objCodeLocation = JarEasyUtilZZZ.getCodeLocationJar(); //new File(JarEasyZZZ.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			if(objCodeLocation!=null) {
				if(objCodeLocation.isFile()) {  // Run with JAR file
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE FOUND.";
				    System.out.println(sLog);
				    
					objReturn = objCodeLocation;
					break main;
				}else if(objCodeLocation.isDirectory()) {  // Run with Eclipse or so.					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) JAR FILE NOT FOUND, RUNNING IN DIRECTORY: '" + objCodeLocation.getAbsolutePath() +"'";
					System.out.println(sLog);
					break main;
				}
			}else {
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DC) CODE LOCATION IS NULL. JAR FILE NOT FOUND.";
				System.out.println(sLog);
				break main;
			}
		}//end main:
		return objReturn;
	}

	/**Versuche erst das JarFileCurrentAsFile zu holen. Wenn das nicht vorhanden ist, wird über das Excecution-Verzeichnis ein definierter .Jar-Dateiname gesucht.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.10.2020, 14:36:19
	 * @throws ExceptionZZZ 
	 */
	public static JarFile getJarFileUsed() throws ExceptionZZZ {
		JarFile objReturn = null;
		main:{
			String sLog = null;
			objReturn = getJarFileCurrent();
			if(objReturn!=null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE CURRENT FOUND.";
			    System.out.println(sLog);
				break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) NOT RUNNING IN JAR FILE.";
			    System.out.println(sLog);			    
			    final File jarFile = getJarFileDefaultAsFile();
			    objReturn = JarEasyUtilZZZ.toJarFile(jarFile);
			}
		}//end main:
		return objReturn;
	}

	/**Versuche erst das JarFileCurrentAsFile zu holen. Wenn das nicht vorhanden ist, wird über das Excecution-Verzeichnis ein definierter .Jar-Dateiname gesucht.
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.10.2020, 14:36:19
	 * @throws ExceptionZZZ 
	 */
	public static File getJarFileUsedAsFile() throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = null;
			objReturn = getJarFileCurrentAsFile();
			if(objReturn!=null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) JAR FILE CURRENT FOUND.";
			    System.out.println(sLog);
				break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) NOT RUNNING IN JAR FILE.";
			    System.out.println(sLog);			    
			    objReturn = getJarFileDefaultAsFile();
			}
		}//end main:
		return objReturn;
	}

}
