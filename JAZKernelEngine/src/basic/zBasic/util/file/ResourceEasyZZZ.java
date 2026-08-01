package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.JarEasyInCurrentJarZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;

/**Klasse bietet Zugriff auf Ressource-Dateien.
 * Dabei ist es egal ob die Datei im Filesystem oder in einer JAR-Datei liegt.
 * 
 * Es wird immer zuerst auf dem Filesystem nachgesehen,
 * danach erst in der JAR-Datei. Grund: So kann man Dateien der JAR-Datei mit Dateien des Filesystems überschreiben. 
 *  
 * 
 * @author Fritz Lindhauer, 23.12.2020, 09:35:08
 * 
 */
public class ResourceEasyZZZ extends AbstractObjectWithExceptionZZZ implements IResourceHandlingObjectZZZ{
	private ResourceEasyZZZ(){
		//Zum Verstecken des Konstruktors
	}

	public static File doClassloaderGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = JarEasyUtilZZZ.class.getClassLoader().getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, true);		
		}
		return objReturn;
	}

	public static File doClassGetResource(String sPath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath))break main;
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) isearching for path '" + sPath + "'";
			System.out.println(sLog);
			URL workspaceURL = JarEasyUtilZZZ.class.getResource(sPath);
			objReturn = ResourceEasyZZZ.getResource(workspaceURL, sPath, false);			
		}
		return objReturn;
	}

	static File getResource(URL workspaceURL, String sPath, boolean byClassloader) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog=null;
			if(JarEasyUtilZZZ.isInJarStatic()){				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) in .jar searching.";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) WorkspaceURL is not null '" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
				    try {
				    	objReturn = FileEasyZZZ.createTempFile();
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) temp File created.";
					    System.out.println(sLog);
					    
				    	FileOutputStream fosResource = new FileOutputStream(objReturn);
				   
					    int i; boolean bAnyFound=false;
					    byte[] ba = new byte[1024];
					    InputStream isClass = null;
					    if(byClassloader){
					    	isClass = JarEasyUtilZZZ.class.getClassLoader().getResourceAsStream(sPath);
					    }else{
					    	isClass = JarEasyUtilZZZ.class.getResourceAsStream(sPath);
					    }
					   
					   //while the InputStram has bytes				   
						while((i=isClass.read(ba))>0){
							bAnyFound=true;
							
							 //write the bytes to the output stream
							fosResource.write(ba,0,i);						  					
						}
						if(bAnyFound){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) any Bytes found.";
						    System.out.println(sLog);
						}else{
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) no Bytes found!!!";
						    System.out.println(sLog);
						}
						
						//close streams to prevent errors
						isClass.close();
						fosResource.close();
				
				} catch (FileNotFoundException fnfe) {
					ExceptionZZZ ez = new ExceptionZZZ("FileNotFoundException: '" + fnfe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "",fnfe);
					throw ez;
				} catch (IOException ioe) {
					ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "",ioe);
					throw ez;
				}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) WorkspaceURL is null, searching for JarFile.";
				    System.out.println(sLog);
				    try {
					    //Nun muss mann die Ressource aus einer .jar-Datei holen.
					    //Für das Return - Objekt muss man differenziert verfahren: Verzeichnis oder Datei
					    //0. JarFile holen
					    JarFile jar = JarKernelZZZ.getJarFileCurrent();
					    if(jar==null) {
						    sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) JarFileCurrent not available.";
						    System.out.println(sLog);
						    break main;
					    }
					    
					    //1. Prüfe auf Verzeichnis / Datei
					    boolean bEntryIsDirectory = JarEasyInCurrentJarZZZ.isDirectory(jar, sPath);
					    if(bEntryIsDirectory) {
					    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) ENTRY IS DIRECTORY, WILL NOT BE EXTRACTED AS TEMP-FILE, BUT AS REAL TEMP DIRECTORY.";
					    	System.out.println(sLog);
					    	objReturn = JarEasyInCurrentJarZZZ.searchResourceDirectoryFirst(sPath, "");
					    }else {
					    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) ENTRY IS FILE, WILL BE EXTRACTED AS TEMP-FILE";
						    System.out.println(sLog);						    	
					    	objReturn = JarEasyInCurrentJarZZZ.extractFileAsTemp(sPath);
					    }
					    
					    if(objReturn==null){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource NICHT gefunden '" + sPath + "' (NULL CASE)";
						    System.out.println(sLog);
						}else {
						    if(FileEasyZZZ.exists(objReturn)){
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource gefunden '" + sPath + "'";
							    System.out.println(sLog);							
							}else {
								sLog = ReflectCodeZZZ.getPositionCurrent()+": (BA) Ressource NICHT gefunden '" + sPath + "'";
							    System.out.println(sLog);								
							}
						}
				    
					  //JarFile wieder schliessen.
					  jar.close();
				    } catch (IOException ioe) {
						ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "",ioe);
						throw ez;
					}
				}
				
				
				
			}else{								
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) not in .jar searching for path '" + sPath + "'";
				System.out.println(sLog);
				if(workspaceURL!=null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (BB) URL is not null for path '" + sPath + "' ('" + workspaceURL.toString() + "')";
				    System.out.println(sLog);
					try {			
						objReturn = new File(workspaceURL.toURI());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBA) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}else {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBA) Datei NICHT gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					} catch(URISyntaxException e) {
						objReturn = new File(workspaceURL.getPath());
						if(objReturn.exists()){
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBB) Datei gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}else {
							sLog = ReflectCodeZZZ.getPositionCurrent()+": (BBB) Datei NICHT gefunden '" + sPath + "'";
						    System.out.println(sLog);
							break main;
						}
					}
				}else{
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (B) URL is null'";
				    System.out.println(sLog);
				}
				
			}
			
		}//end main:
		return objReturn;
	}
	
	
	/** Finde nur das Verzeichnis in der JAr Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File peekDirectoryInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objReturn = JarEasyInCurrentJarZZZ.peekDirectoryFirst(sPath, sDirExtractTo);
			}else {				
				JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileAsJar);
				objReturn = JarEasyZZZ.peekDirectoryFirst(objJar, sPath, sDirExtractTo, false);				
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Finde nur die angegebene Datei in der Jar Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File peekFileInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objReturn = JarEasyInCurrentJarZZZ.peekFileFirst(sPath, sDirExtractTo);
			}else {
				JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileAsJar);
				objReturn = JarEasyZZZ.peekFileFirst(objJar, sPath, sDirExtractTo);
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Finde nur die angegebene Datei in der Jar Datei. Es wird ein Dummy-Objekt zurückgegeben, das NICHT auf der Platte ist.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File[] peekFilesInJar(File objFileAsJar, String sFilename, String sDirExtractTo)throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objaReturn = JarEasyInCurrentJarZZZ.peekFiles(sFilename, sDirExtractTo);
			}else {
				JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileAsJar);
				objaReturn = JarEasyZZZ.peekFiles(objJar, sFilename, sDirExtractTo);
			}
			
		}//end main:
		return objaReturn;
	}
	
	
	
	
	/** Finde nur das Verzeichnis in der JAr Datei. Es werden die Dateien als Dummy-Objekt zurückgegeben, die NICHT auf der Platte sind.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File[] peekFilesOfDirectoryInJar(File objFileAsJar, String sPath, String sDirExtractTo)throws ExceptionZZZ {
		File[]objaReturn = null;
		main:{
			if(objFileAsJar==null)break main;			
			if(ResourceEasyZZZ.isInSameJarStatic(objFileAsJar)) {				
				objaReturn = JarEasyInCurrentJarZZZ.peekDirectories(sPath, sDirExtractTo);
			}else {
				JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileAsJar);
				objaReturn = JarEasyZZZ.peekDirectories(objJar, sPath, sDirExtractTo);						
			}
		}//end main:
		return objaReturn;
	}
	
	/** Finde das Verzeichnis. Zuerst auf einer Festplatte nachsehen, danach ggfs. in der JAR Datei, wenn der Code in einer JAR Datei ausgeführt wird.
	 *  Es wird ein File-Objekt zurückgegeben, das auf der Platte gespeichert ist, bzw. ein das Temp-Verzeichnis auf die Platte gespeichert wird.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File searchDirectory(String sDirToSearch)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			
			
			String sLog = "(1) Directory to search for: '" + sDirToSearch + "'";
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
			objReturn = FileEasyZZZ.searchDirectory(sDirToSearch);
			if(objReturn!=null) {
				if(FileEasyZZZ.isDirectoryExisting(objReturn)) break main;
			}
			
			if(ResourceEasyZZZ.isInJarStatic()) {
				sDirToSearch = JarEasyInCurrentJarZZZ.joinFilePathName("src", sDirToSearch)	;			
				sLog = "(2) Directory to search for: '" + sDirToSearch + "'";				
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
				objReturn = JarEasyInCurrentJarZZZ.searchResourceDirectory(sDirToSearch, null, true);
			}
		}//end main:
		return objReturn;
	}
	
	public static String searchDirectoryAsString(String sDirToSearch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			File objReturn = ResourceEasyZZZ.searchDirectory(sDirToSearch);
			if(objReturn!=null) {
					sReturn = objReturn.getAbsolutePath();			
			}			
		}//end main:
		return sReturn;
	}
	
	public static String searchDirectoryAsStringRelative(String sDirToSearch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sDirectoryPath = ResourceEasyZZZ.searchDirectoryAsString(sDirToSearch);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Directory Path found = '" + sDirectoryPath + "'");
			if(sDirectoryPath ==null) break main;
			
			String sDirectoryWorkspace = null;
			if(ResourceEasyZZZ.isInJarStatic()){
				//Datei oder Verzeichnis wurden temporär abgelegt, hier: 'C:\DOKUME~1\LINDHA~1\LOKALE~1\Temp\ZZZ\src\resourceZZZ\image\tray'
				//Das ist aber egal... 
				
				sDirectoryWorkspace =  JarEasyZZZ.sDIRECTORY_RESSOURCE_ROOT + JarEasyZZZ.sDIRECTORY_SEPARATOR + sDirToSearch;
				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Path in Jar found = '" + sDirectoryPath + "'");
				
				sReturn = sDirectoryWorkspace;
			}else {
				sDirectoryWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();				
				sDirectoryWorkspace = sDirectoryWorkspace + IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER + IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR;
				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Workspace Path found = '" + sDirectoryPath + "'");				
				sReturn = StringZZZ.right(sDirectoryPath, sDirectoryWorkspace);	
			}
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Found DirectoryAsStringRelative = '" + sReturn + "'");						
		}//end main:
		return sReturn;
	}
	
	
	
	/** Finde das Verzeichnis. Zuerst auf einer Festplatte nachsehen, danach ggfs. in der JAR Datei, wenn der Code in einer JAR Datei ausgeführt wird.
	 *  Es wird ein File-Objekt zurückgegeben, das auf der Platte gespeichert ist, bzw. ein das Temp-Verzeichnis auf die Platte gespeichert wird.
	 *  
	 * @param sDirToSearch
	 * @param bWithFiles       Beim Erstellen des Verzeichnisses aus einer Jar-Datei werden sofort auch die darin enthaltenden Dateien extrahiert und erstellt.. 
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 24.12.2020, 11:03:04
	 */
	public static File searchDirectory(String sDirToSearch, boolean bWithFiles)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			objReturn = FileEasyZZZ.searchDirectory(sDirToSearch);
			if(objReturn!=null) {
				if(FileEasyZZZ.isDirectoryExisting(objReturn)) break main;
			}
			
			if(ResourceEasyZZZ.isInJarStatic()) {
				objReturn = JarEasyInCurrentJarZZZ.searchResourceDirectory(sDirToSearch, null, bWithFiles);
			}
		}//end main:
		return objReturn;
	}
	
	/** Finde das Verzeichnis. Zuerst auf einer Festplatte nachsehen, danach ggfs. in der JAR Datei, wenn der Code in einer JAR Datei ausgeführt wird.
	 *  Es wird ein File-Objekt zurückgegeben, das auf der Platte gespeichert ist, bzw. ein das Temp-Verzeichnis auf die Platte gespeichert wird.
	 * @param objFileAsJar
	 * @param sPath
	 * @param sDirExtractTo
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.10.2020, 13:01:39
	 */
	public static File searchFile(String sFileToSearch)throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = "(1) File to search for: '" + sFileToSearch + "'";
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
			objReturn = FileEasyZZZ.searchFile(sFileToSearch,false);//Merke: Dieses False soll bewirken NICHT in einer Jar-DAtei zu suchen
			if(objReturn!=null) {
				if(FileEasyZZZ.isFileExisting(objReturn)) break main;
			}
			
			if(ResourceEasyZZZ.isInJarStatic()) {
				sLog = "(2) File to search for: '" + sFileToSearch + "'";
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
				objReturn = JarEasyInCurrentJarZZZ.searchResourceFileFirst(sFileToSearch,null);
			}
		}//end main:
		return objReturn;
	}
	
	
	/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
	 *   Merke: Static Klassen müssen diese Methode selbst implementieren. Das ist dann das Beispiel.
	 * @return
	 * @author lindhaueradmin, 21.02.2019
	 * @throws ExceptionZZZ 
	 */
	public static boolean isInJarStatic() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = JarEasyUtilZZZ.isInJar(ResourceEasyZZZ.class);
		}
		return bReturn;
	}
	
	public static boolean isInSameJarStatic(File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			if(!ResourceEasyZZZ.isInJarStatic()) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running from jar file.";
			    System.out.println(sLog);
				break main;			
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_VALUE, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
			 if(objFileJar==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Unable to get CodeLocation for running jar", iERROR_RUNTIME, ResourceEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			 }
			  
			 bReturn = objFileJar.equals(objFileAsJar);
		}//end main;
		return bReturn;
	}
	
	//### Interfaces
		//aus IRessourceHandlingObjectZZZ
		
		//### Ressourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist. Also:
			/** Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
			 *   Merke: Static Klassen müssen diese Methode selbst implementieren.
			 * @return
			 * @author lindhaueradmin, 21.02.2019
			 * @throws ExceptionZZZ 
			 */
			public boolean isInJar() throws ExceptionZZZ{
				boolean bReturn = false;
				main:{
					bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
				}
				return bReturn;
			}
}

