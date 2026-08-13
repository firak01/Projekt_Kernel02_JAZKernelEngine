package basic.zBasic.util.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.FileUtils;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectWorkspaceZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyInCurrentJarZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigZZZ;
import custom.zKernel.LogZZZ;

/**Einfache Dateioperationen
 * @author lindhaueradmin
 *
 */
public class FileEasyZZZ extends AbstractObjectWithExceptionZZZ implements IFileEasyConstantsZZZ{
	private FileEasyZZZ(){
		//Zum Verstecken des Konstruktors, sind halt nur static MEthoden
	}

	
	/** Überprüft, ob unter dem angegebenen pfadnamen "fileName" eine Datei existiert.
	 *  Merke: Das hat einen anderen Algorithums, als die gleiche Methode mit File-Objekt als Parameter
	 * @param fileName
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static boolean exists (String sFilePath, String sFileName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilePath) && StringZZZ.isEmpty(sFileName)){
				ExceptionZZZ ez  = new ExceptionZZZ("FilePath and FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePath, sFileName);
			bReturn = FileEasyZZZ.exists(sFilePathTotal);
		}//end main:
		return bReturn;
	} 
	
/** Überprüft, ob unter dem angegebenen pfadnamen "fileName" eine Datei existiert.
 *  Merke: Das hat einen anderen Algorithums, als die gleiche Methode mit File-Objekt als Parameter
 * @param fileName
 * @return
 * @throws ExceptionZZZ 
 */
public static boolean exists (String sFileName) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Prüfen, ob der Dateiname existiert oder nicht. Dabei wird ggf. auch ein relativer DateiPfad ber�cksichtig.
		File f = FileEasyZZZ.searchFile(sFileName);
		if(f!=null) bReturn = f.exists();
	}//end main:
	return bReturn;
} 

/** Überprüft, ob unter dem angegebenen pfadnamen "fileName" eine Datei existiert.
 *  Merke: Das hat einen anderen Algorithums, als die gleiche Methode mit File-Objekt als Parameter
 * @param fileName
 * @return
 * @throws ExceptionZZZ 
 */
public static boolean existsInWorkspace(String sFileName) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Prüfen, ob der Dateiname existiert oder nicht. Dabei wird ggf. auch ein relativer DateiPfad ber�cksichtig.
		File f = FileEasyZZZ.searchFileInWorkspace(sFileName);
		if(f!=null) bReturn = f.exists();
	}//end main:
	return bReturn;
} 

/** Überprüft, ob das angegebene Fileobjekt existiert.
 *  Merke1: objDir.exists(); //!!! Pfade, die auf ein Verzeichnis weisen existieren immer
 *  Merke2: Das hat einen anderen Algorithums, als die gleiche Methode mit String Parameter
 *  				//Das geht für Verzeichnisse sicher nur mit Using java.nio.file.Files, seit Java 7

 * @param fileName
 * @return
 * @throws ExceptionZZZ 
 */
public static boolean exists(File objFile) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(objFile==null){
			ExceptionZZZ ez  = new ExceptionZZZ("FileObject", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Java 7 Trick, der bei Verzeichnissen funktioniert.
		//Merke: Mit relativen Pfaden wird auch NIO immer ein true zurückgeben.
		
		String sPath = objFile.getAbsolutePath();
		Path path = Paths.get(sPath);
		if (Files.exists(path)) {		
			bReturn = true;
			break main;
		}else {
			bReturn = false;
			break main;
		}
		
		
		
	}//end main:
	return bReturn;
} 

/**Gibt ein File Objekt Datei zurück. Es wird nicht gesucht und auch nicht auf die Existenz geprüft.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getFile(String sDirectory, String sFileName)throws ExceptionZZZ{
	File objReturn = null;
	main:{
   		String stemp = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
   		objReturn = new File(stemp);
	}//end main:
	return objReturn;
}


/**Gibt ein File Objekt Datei zurück. Es wird nicht gesucht und auch nicht auf die Existenz geprüft.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getFile(String sFilePathTotal) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
    	FileEasyZZZ.splitFilePathName(sFilePathTotal, strDirectory, strFileName);
    	String sDirectory=strDirectory.get();
   		String sFileName=strFileName.get();
   		
   		objReturn = FileEasyZZZ.getFile(sDirectory, sFileName);
	}//end main:
	return objReturn;
}

/**Gibt ein File Objekt Verzeichnis zurück. Es wird nicht gesucht und auch nicht auf die Existenz geprüft.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File getDirectory(String sFilePathTotal) throws ExceptionZZZ {
	File objReturn = null;
	main:{
		ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
    	FileEasyZZZ.splitFilePathName(sFilePathTotal, strDirectory, strFileName);
    	String sDirectory=strDirectory.get();
   		objReturn = new File(sDirectory);	
	}//end main:
	return objReturn;
}

public static String getDirectoryNameFirst(String sFilePathTotal) throws ExceptionZZZ {
	String sReturn=sFilePathTotal;
	main:{
		if(StringZZZ.isEmpty(sFilePathTotal)) break main;
		
		StringTokenizer objTokenizer = new StringTokenizer(sFilePathTotal, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
		sReturn = objTokenizer.nextToken();
	}//end main:
	return sReturn;	
}

/**
 * @param sDirectoryStart, ein Teil im Gesamtpfad
 * @param sFilePathTotal, der Gesamtpfad
 * @return                Rueckgabewert: Alles was hinter dem Teil des Gesamtpfads kommt
 * @throws ExceptionZZZ
 */
public static String getDirectoryPathFollowing(String sDirectoryStart, String sFilePathTotal) throws ExceptionZZZ{
	String sReturn = sFilePathTotal;
	main:{
		if(StringZZZ.isEmpty(sFilePathTotal)) break main;
		if(StringZZZ.isEmpty(sDirectoryStart)) break main;
		
		sReturn = StringZZZ.left(sFilePathTotal, sDirectoryStart+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
	}//end main:
	return sReturn;	
}


/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 *  
 *  Merke:Eine NULL oder ".." Angabe bedeutet "Projekt-Ordner-Ebene".
 *  Ein Leerstring, Empty oder "." bedeutet "Root vom Classpath" (also: "src-Ordner-Ebene" bei Eclipse - Anwendung). 
 *
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File searchDirectoryFromFilepath(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(sFilePath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		
		ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
    	FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName);
    	String sDirectory=strDirectory.get();
		
    	objReturn = FileEasyZZZ.searchDirectory(sDirectory);
    	
	}//end main:
	return objReturn;
}


/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}		
	    objReturn = FileEasyZZZ.searchFileObject(sFilePath);
	}//end main:
	return objReturn;
}


public static File searchFileInWorkspace(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}		
	    objReturn = FileEasyZZZ.searchFileObjectInWorkspace(sFilePath);
	}//end main:
	return objReturn;
}

/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sDirectory, String sFileNameIn)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		String sFileName;
		if(StringZZZ.isEmpty(sFileNameIn)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			sFileName = sFileNameIn;
		}
			
		String sFilePath = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);	
		objReturn = FileEasyZZZ.searchFile(sFilePath);

	}//END main:
	return objReturn;	
}

public static File searchFileInWorkspace(IKernelConfigZZZ objConfig, String sDirectory, String sFileNameIn)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		String sFileName;
		if(StringZZZ.isEmpty(sFileNameIn)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			sFileName = sFileNameIn;
		}
		
		 if(objConfig==null) {
	    	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
	    }
		//Das ist ein Z-Kernel Konzept und funktioniert nur innerhalb der Entwicklungsumgebung
	    if(!objConfig.isInIDE()) break main;
		
//	    String sWorkspaceProjekt = objConfig.getProjectPathTotal();
//	    String sDirectoryTotal = FileEasyZZZ.joinFilePathNameForWorkspace(sWorkspaceProjekt, sDirectory);		
	    String sDirectoryTotal = ReflectWorkspaceZZZ.computeWorkspacePath(objConfig, sDirectory);
	    
	    //Da vorher schon ggfs. ein src-Verzeichnis hinzugefügt worden ist jetzt explizit keines mehr hinzufügen
		String sFilePath = FileEasyZZZ.joinFilePathNameForUrl(sDirectoryTotal, sFileName);	
		objReturn = FileEasyZZZ.searchFileInWorkspace(sFilePath);

	}//END main:
	return objReturn;	
}

/**Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird.
 * @param sFilePath
 * @param bTempFileAlternative
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sFilePath, boolean bInJar) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Hier der Workaround mit Refenz-Objekten, aus denen dann der Wert geholt werden kann. Also PASS_BY_REFERENCE durch auslesen der Properties der Objekte.  
		ReferenceZZZ<String> strDirectory = new ReferenceZZZ("");
		ReferenceZZZ<String> strFileName = new ReferenceZZZ("");
		FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName);
		
		String sDirectory = strDirectory.get();
		if(StringZZZ.isEmpty(sDirectory))sDirectory="."; //Lokal suchen.
		String sFileName = strFileName.get();
		
		objReturn = FileEasyZZZ.searchFile(sDirectory, sFileName, bInJar);
		
	}//END main:
	return objReturn;
}




/** Gibt eine gesuchte Datei zurück. Ggfs. auch einen temporär erzeugte Datei, falls die andere nicht gefunden wird. 
 * @param sDirectoryIn
 * @param sFileName
 * @return
 * @throws ExceptionZZZ
 */
public static File searchFile(String sDirectoryIn, String sFileName, boolean bTempFileAlternative)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFileName)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}			
		
		//1. Prüfen, ob die Pfadangabe absolut ist:
		if(sDirectoryIn!=null){
			boolean bIsAbsolut = FileEasyZZZ.isPathAbsolute(sDirectoryIn);
			if(bIsAbsolut){
				//System.out.println(ReflectCodeZZZ.getPositionCurrent()+": (0) Path is absolut. '" + sDirectoryIn + "'");
				File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectoryIn);
				if(objDirectoryNormed==null){
					String sLog = ReflectCodeZZZ.getPositionCurrent()+"(0) Directory does not exist. '" + sDirectoryIn + "'";
					System.out.println(sLog);				
					break main;
				}else{
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+"(0) Directory exists. '" + sDirectoryIn + "'");
				}
				
				
				//Versuch im Input-Pfad suchen (also unterhalb des Source - Folders, z.B. src.). Merke: Dort liegende Dateien sind dann auch per WebServer erreichbar, Aber NICHT gepackt in ein .jar File.
				String sDirectoryNormed = objDirectoryNormed.getAbsolutePath();
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+"(1) Normed Path for file is: '" + sDirectoryNormed+File.separator+sFileName + "'");
				objReturn = FileEasyZZZ.searchFile(sDirectoryNormed+File.separator+sFileName);
				String sLog = null;
				if(objReturn==null){
					 sLog = ReflectCodeZZZ.getPositionCurrent()+"(1) File does not exist (=null). '" + sDirectoryNormed+File.separator+sFileName + "'";	
					 System.out.println(sLog);				     
				}else if(objReturn.exists()) {
					 sLog = ReflectCodeZZZ.getPositionCurrent()+"(1) File exists. '" + sDirectoryNormed+File.separator+sFileName + "'";	
					 System.out.println(sLog);
				     break main;
				}else{
				     sLog = ReflectCodeZZZ.getPositionCurrent()+"(1) File does not exist. '" + sDirectoryNormed+File.separator+sFileName + "'";
				     System.out.println(sLog);
				}
			}//end if isAbsolut
		}else{
			//4. Bei NULL: Versuch den Projektordner verwenden
			//System.out.println(ReflectCodeZZZ.getPositionCurrent()+"(2) Path is relativ. '" + sDirectoryIn + "'");
			String sDirectory = null;
			File objDirectory = FileEasyZZZ.searchDirectory(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT); 
			String sLog = null;
			if(objDirectory!=null){
				sDirectory = objDirectory.getPath();
				sLog = ReflectCodeZZZ.getPositionCurrent()+"(2) Directory-Root exist for '.'='"+sDirectory+"'";
			    System.out.println(sLog);
				objReturn = FileEasyZZZ.searchFile(sDirectory+File.separator+sFileName);
				if(objReturn!=null){
					if(objReturn.exists()) break main;
				}
			}else{
			     sLog = ReflectCodeZZZ.getPositionCurrent()+"(2) Directory-Root does not exist for '.'";
			     System.out.println(sLog);
			}
			
		}
		
		//2. Relative Pfadangaben per Classloader holen. Das Funktioniert dann auch in .jar-Dateien gepackt.		
		ClassLoader classLoader = FileEasyZZZ.class.getClassLoader();	
		URL fileURL = null;		
		String sFilePathTotal = null;
		String sLog = null;
		if(sDirectoryIn==null){
			sFilePathTotal = sFileName; 
		}else{
			sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileName);	
		}
		sLog = ReflectCodeZZZ.getPositionCurrent()+"(3) Searching for file '" + sFilePathTotal +"'";
	    System.out.println(sLog);
		objReturn = FileEasyZZZ.searchFileObject(sFilePathTotal);
		if(objReturn!=null){
			if(objReturn.exists()) break main;
		}
			
				
		//3. Versuche den RootPfad zu verwenden
		String sRoot = FileEasyZZZ.getFileRootPath();
		if(sDirectoryIn==null){
			sFilePathTotal = sRoot + File.separator + sFileName; 
		}else{
			sFilePathTotal = FileEasyZZZ.joinFilePathName(sRoot + File.separator + sDirectoryIn, sFileName);	
		}
		sLog = ReflectCodeZZZ.getPositionCurrent()+"(4) Searching for file '" + sFilePathTotal +"'";
	    System.out.println(sLog);
		objReturn = FileEasyZZZ.searchFileObjectInRunningExecutionProjectPath(sFilePathTotal);
		if(objReturn!=null){
			if(objReturn.exists()) break main;
		}
		
		//+++ 4. Versuch (WebService) im Classpath suchen 
		//Problematik: Zugriff auf die Datei, wenn Sie in einem WAR File gepackt ist.
		//Ansatz, siehe: https://www.java-forum.org/thema/auf-dateien-im-war-zugreifen.157897/
	    //1. Sie muss unterhalb des Source Ordners liegen
	    //2. Über eine Ressource eine "Kopie" erzeugen....
		sLog = ReflectCodeZZZ.getPositionCurrent()+"(5) creating temp file.";
	    System.out.println(sLog);
		 try {
			 	String sDirectoryOnClasspath = sDirectoryIn;
			 	objReturn = FileEasyZZZ.createTempFile();
												
				InputStream resourceAsStream = FileEasyZZZ.class.getClassLoader().getResourceAsStream(sDirectoryOnClasspath + File.separator + sFileName);
				if(resourceAsStream != null){
					FileUtils.copyInputStreamToFile(resourceAsStream, objReturn);
					if(objReturn.exists()) break main;	
				}
		} catch (IOException e) {
				e.printStackTrace();
		}	
		 
		//Wenn man hierher kommt, wurde keine Datei gefunden. Falls keine temporäre Datei gewünscht wird, diese hier wieder entfernen.
		if(!bTempFileAlternative){
			objReturn = null;
		}
				 							
	}//END main:	
	return objReturn;	
}

public static File searchFilePath(String sFilePath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//1. Aufteilen auf Datei und Verzeichnis
		File objFile = new File(sFilePath);
		String sDirectory = objFile.getParent();
		if(sDirectory==null){ //ROOT
			objReturn = new File(sFilePath);
			break main;
		}	
		
		File objDirectoryNormed = FileEasyZZZ.searchDirectory(sDirectory);
		String sDirectoryPathNormed = objDirectoryNormed.getAbsolutePath();
		String sFileName = objFile.getName();
		
		String sFilePathNormedTotal = sDirectoryPathNormed + File.separator + sFileName;
		objReturn = new File(sFilePathNormedTotal);
		
	}//END main:
	return objReturn;
}

public static boolean searchIsDirectoryEmpty(String sDirectoryPath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			File objFile = FileEasyZZZ.searchDirectory(sDirectoryPath);						
			if(objFile.exists()==false){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' does not exist (... on the web server).", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}				
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory (... on the web server).", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String[] saFile = objFile.list();
			if(saFile==null){
				bReturn = true;
			}else{
				if(saFile.length>=1){
					bReturn = false;
				}else{
					bReturn = true;
				}
			}
	}
	return bReturn;
}

public static File searchMakeDirectory(String sDirectoryPath) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		File objDirectory = FileEasyZZZ.searchDirectory(sDirectoryPath);
		String sDirectoryPathNormed = objDirectory.getAbsolutePath();
		System.out.println(ReflectCodeZZZ.getPositionCurrent()+"Errechneter Pfad für das KernelLog='" + sDirectoryPathNormed +"'");
	
		FileEasyZZZ.makeDirectory(sDirectoryPathNormed);
        
		objReturn = new File(sDirectoryPathNormed);
	}//end main:
	return objReturn;
}

/*Rückgabewert ist das 'File-Objekt' vom Eingabe FilePath.
 * Darüber hinaus wird der Pfad aufgeteilt und zurückgegeben.
 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
 */
public static File splitFilePathName(String sFilePath, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		objReturn = splitFilePathName_(sFilePath, strDirectory, strFileName);
	}//END main:
	return objReturn;	
}

public static File splitFilePathName(String sFilePath, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName, char cDirectorySeparator) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		objReturn = splitFilePathName_(sFilePath, strDirectory, strFileName, cDirectorySeparator);
	}//END main:
	return objReturn;	
}


/*Rückgabewert ist das 'File-Objekt' vom Eingabe FilePath.
 * Darüber hinaus wird der Pfad aufgeteilt und zurückgegeben.
 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
 */
private static File splitFilePathName_(String sFilePathIn, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName) throws ExceptionZZZ{
	File objReturn = null;
	main:{
	if(StringZZZ.isEmpty(sFilePathIn)){
		ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	//String sSeparator = CharZZZ.toString(cDirectorySeparator);
	//Nein, die Idee ist es den Dateipfad erst zu normieren
	String sFilePath = FileEasyZZZ.normlizeFilePath(sFilePathIn);
	
	objReturn = new File(sFilePath);
	String sDirectory = "";	
	//Aufteilen auf Datei und Verzeichnis, nur wenn es einen Seperator im Pfad gibt, sonst ist es lediglich der Dateiname.
	sDirectory = objReturn.getParent();
	if(sDirectory==null){ //ROOT		
		break main;
	}	

	String sFileName = objReturn.getName();
	
	//#### Die Rückgabewerte
	strDirectory.set(sDirectory);
	strFileName.set(sFileName);
	
	}//END main:
	return objReturn;	
}

/*Rückgabewert ist das 'File-Objekt' vom Eingabe FilePath.
 * Darüber hinaus wird der Pfad aufgeteilt und zurückgegeben.
 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
 */
private static File splitFilePathName_(String sFilePathIn, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName, char cDirectorySeparator) throws ExceptionZZZ{
	File objReturn = null;
	main:{
	if(StringZZZ.isEmpty(sFilePathIn)){
		ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	
	//Normalerweise ist die Idee ist es den Dateipfad erst zu normieren, hier habe wir aber den Separator übergeben bekommen
	//String sFilePath = FileEasyZZZ.normlizeFilePath(sFilePathIn);
	String sFilePath  = sFilePathIn;
	String sSeparator = CharZZZ.toString(cDirectorySeparator);
	
	objReturn = new File(sFilePath);
	String sDirectory = "";
	if(sFilePath.contains(sSeparator)){
		//Aufteilen auf Datei und Verzeichnis, nur wenn es einen Seperator im Pfad gibt, sonst ist es lediglich der Dateiname.
		sDirectory = objReturn.getParent();
		if(sDirectory==null){ //ROOT		
			break main;
		}	
	}

	String sFileName = objReturn.getName();
	
	//#### Die Rückgabewerte
	strDirectory.set(sDirectory);
	strFileName.set(sFileName);
	
	}//END main:
	return objReturn;	
}


/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
 *  Darum wird hier per classloader die Ressource geholt.
 *  
 *  Merke:Eine NULL oder ".." Angabe bedeutet "Projekt-Ordner-Ebene".
 *  Ein Leerstring, Empty oder "." bedeutet "Root vom Classpath" (also: "src-Ordner-Ebene" bei Eclipse - Anwendung). 
 *  
 *  Merke: Wenn das Verzeichnis in einer .jar Datei liegt, kann man nur die JAR - Datei selbst zurückgeben.
 * @param sDirectoryIn
 * @author lindhaueradmin, 13.02.2019, 07:14:31
 * @param sFilePath
 * @return
 * @throws ExceptionZZZ
 */
public static File searchDirectory(String sDirectoryPathIn) throws ExceptionZZZ{
	File objReturn = null;
	main:{
		String sLog = "Directory to search for= '" + sDirectoryPathIn + "'";
		ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
		objReturn = FileEasyZZZ.searchDirectory(sDirectoryPathIn, false);
	}//end main:
	return objReturn;
}

/** Eine NULL oder ".." Angabe bedeutet "Projekt-Ordner-Ebene".
 *  Ein Leerstring, Empty oder "." bedeutet "Root vom Classpath" (also: "src-Ordner-Ebene" bei Eclipse - Anwendung). 
 *  
 *  Merke: Wenn das Verzeichnis in einer .jar Datei liegt, kann man nur die JAR - Datei selbst zurückgeben.
 * @param sDirectoryIn
 * @return
 * @throws ExceptionZZZ
 * @author lindhaueradmin, 13.02.2019, 07:14:31
 */
public static File searchDirectory(String sDirectoryIn, boolean bSearchInJar)throws ExceptionZZZ{
	File objReturn = null;
	main:{
		String sLog = null; String sDirectory = null;
		
		sDirectory = sDirectoryIn;
		if(sDirectory!=null) {
			if(FileEasyZZZ.isPathAbsolute(sDirectory)) {
				//Suche nach absolutem Pfad
				objReturn = new File(sDirectory);
				if(objReturn!=null) {				
					if(objReturn.exists()) {
						if(objReturn.isDirectory())	break main;
					}
				}
			}
		}else {
			sLog = "sDirectory=null.";
			ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);			
		}
		
		sDirectory = FileEasyZZZ.getFileUsedPath(sDirectoryIn);		
		if(FileEasyZZZ.isPathRelative(sDirectory)){
			//Relative Pfadangabe....
			//ACHTUNG ENDLOSSCHLEIFE WENN MAN HIER NICHT IN DEN ABSOLUTEN PFAD UMSCHWENKT...				
			String sDirectoryRoot = FileEasyZZZ.getDirectoryOfExecutionAsString();
			sLog = "(2) sDirectoryRoot='" + sDirectoryRoot + "'";
			ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
			
			String sDirectoryAbsolute = FileEasyZZZ.joinFilePathName(sDirectoryRoot, sDirectory);
			sLog = "(2) GEBAUTER ABSOLUTER PFAD sDirectoryAbsolute='" + sDirectoryAbsolute + "'";
			ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
			
			objReturn = FileEasyZZZ.searchDirectory(sDirectoryAbsolute, bSearchInJar); //Diesmal aber als absoluten Pfad...
			if(objReturn!=null){
				if(objReturn.exists()) {
					if(objReturn.isDirectory())	break main;
				}
			}
			
			objReturn = FileEasyZZZ.searchFileObjectInRunningExecutionProjectPath(sDirectory);			
			if(objReturn!=null){
				if(objReturn.exists()) {
					if(objReturn.isDirectory())	break main;
				};
			}
			
			if(!bSearchInJar) break main;
			
			//##################################################
			//Suche nach dem Verzeichnis in der gleichen JAR DAtei:
			//Merke: Verzeichnisse können nur zurückgegeben  werden, wenn Sie als Kopie irgendwo erstellt werden.
			sLog = "() SUCHE IN JAR mit searchResourceDirectoryFirst mit '" + sDirectory + "' und sTargetDirectoryPathRootIn=ZZZ";
			ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, sLog);
			objReturn = JarEasyInCurrentJarZZZ.searchResourceDirectoryFirst(sDirectory, "ZZZ");
			if(objReturn!=null){
				if(objReturn.exists()) {
					if(objReturn.isDirectory())	break main;
				}
			}		
		}
		
		if(sDirectory.equals(sDirectoryIn)) break main; //sonst Endlosschleifengefahr
		
		//Falls der Converter etwas am Dateinamen geändert hat, diese Methode neu aufrufen.
		objReturn = FileEasyZZZ.searchDirectory(sDirectory);
		if(objReturn!=null){
			if(objReturn.exists()) {
				if(objReturn.isDirectory())	break main;
			}
		}	
						
		//+++ Wenn noch nichts existiert NULL 
		objReturn = null;						
	}//END main:
	return objReturn;	
}


public static boolean searchExists(String sFilePath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FileName", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//Prüfen, ob der Dateiname existiert oder nicht. Dabei wird ggf. auch ein relativer DateiPfad berücksichtig.
		File f = FileEasyZZZ.searchFile(sFilePath);
		bReturn = f.exists();
	}//end main:
	return bReturn;	
}


public static boolean isPathSubExistingOfDirectory(File objFileDirectory, String sFilePath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(objFileDirectory==null) {
			ExceptionZZZ ez  = new ExceptionZZZ("File Object for Directory", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(StringZZZ.isEmpty(sFilePath)){
			ExceptionZZZ ez  = new ExceptionZZZ("FilePath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)) break main; //Das aktuelle Verzeichnis ist kein Unterverzeichnis
		if(sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_PARENT)) break main; //Das Elternverzeichnis ist kein Unterverzeichnis
		
		String sDirPathTotal;
		if(FileEasyZZZ.isPathAbsolute(sFilePath)) {
			sDirPathTotal = objFileDirectory.getAbsolutePath();		
			
			String sInterchange = StringZZZ.right(sDirPathTotal, sFilePath, true);
			if(!StringZZZ.isEmpty(sInterchange)) break main; //Wenn also der Pfad ein abweichender Pfad ist, dann ist es kein Unterverzeichnis.
			//Damit ist es egal, ob das Verzeichnis existiert....
		}else{
			//Die Pfade erst noch miteinander vernüpfen
			String sDirPath = objFileDirectory.getAbsolutePath();
			sDirPathTotal = FileEasyZZZ.joinFilePathName(sDirPath, sFilePath);
		}
		
		File file2proof = new File(sDirPathTotal);
		bReturn = FileEasyZZZ.isDirectoryExisting(file2proof);
		
	}//end main:
	return bReturn;
}

public static boolean isPathSubExistingOfDirectoryTemp(String sFilePath) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{		
		String sDirectoryTemp = EnvironmentZZZ.getHostDirectoryTemp();
		File objFileDirTemp = new File(sDirectoryTemp);
		
		bReturn = FileEasyZZZ.isPathSubExistingOfDirectory(objFileDirTemp, sFilePath);
		
	}//end main:
	return bReturn;
}

public static  boolean isPathAbsolute(String sFilePathNameIn)throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		String sFilePathName=null;
//		if(sFilePathNameIn==null){//Merke: Darf ein Leerstring sein oder auch NULL. Das wird dann ggfs. im FileEasyConstantConvertZZZ zum Worspace-Pfad oder ProjektOrdner-Pfasd.
//			ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
		if(sFilePathNameIn==null) break main; //Merke: NULL ist kein absoluter Pfad.				
		if(sFilePathNameIn.equals("")) break main; //Merke: Leerstring ist kein absoluter Pfad.
		sFilePathName=sFilePathNameIn;
		
		Pattern p = Pattern.compile(IFileEasyConstantsZZZ.sFILE_ABSOLUT_REGEX); 
		Matcher m =p.matcher( sFilePathName ); 
		bReturn =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
		if(bReturn) break main;
		 
		 
		if ( sFilePathName.startsWith("\\\\")) { //Wohl für Netzwerkpfade...
			bReturn = true;
			break main;
		}else if (sFilePathName.startsWith("\\")){
			 //irgendwie wohl ein Backslash hinzugekommen, normalerweise relativ, aber noch mal testen
			 sFilePathName = StringZZZ.trimFileSeparatorsLeft(sFilePathName);
			 bReturn = FileEasyZZZ.isPathAbsolute(sFilePathName);	
		}else if (sFilePathName.startsWith("//")){
			 //irgendwie wohl ein Slash hinzugekommen, normalerweise relativ, aber noch mal testen
			sFilePathName = StringZZZ.stripFileSeparatorsLeft(sFilePathName);
			 bReturn = FileEasyZZZ.isPathAbsolute(sFilePathName);
		}else if (sFilePathName.startsWith("/")){
			 //irgendwie wohl ein Slash hinzugekommen, normalerweise relativ, aber noch mal testen
			 sFilePathName = StringZZZ.trimFileSeparatorsLeft(sFilePathName);
			 bReturn = FileEasyZZZ.isPathAbsolute(sFilePathName);
		}
	}//end main:
	return bReturn;
}

/** Pr�ft, ob der Pfad relativ ist: Das ist der Fall, wenn 
 * 
 *     A file name is relative to the current directory if it does not begin with one of the following:

        A UNC name of any format, which always start with two backslash characters ("\\"). For more information, see the next section.
        A disk designator with a backslash, for example "C:\" or "d:\".
        A single backslash, for example, "\directory" or "\file.txt". This is also referred to as an absolute path.


* @param sPath
* @return
* 
* lindhaueradmin; 04.11.2012 05:04:10
 */
public static boolean isPathRelative(String sFilePathName)throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
//		if(sFilePathName==null){ //Merke: Darf ein Leerstring sein oder auch NULL. Das wird dann ggfs. im FileEasyConstantConvertZZZ zum Worspace-Pfad oder ProjektOrdner-Pfasd.
//			ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
		
		bReturn = !FileEasyZZZ.isPathAbsolute(sFilePathName);
		
	}//end main:
	return bReturn;
}

public static  boolean isPathValid(String sFilePathName)throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(sFilePathName==null){//Merke: Darf ein Leerstring sein.
			ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(sFilePathName.equals("")) break main; //Merke: Leerstring ist kein absoluter Pfad.
		
		Pattern p = Pattern.compile(IFileEasyConstantsZZZ.sDIRECTORY_VALID_WINDOWS_REGEX); 
		 Matcher m =p.matcher( sFilePathName ); 
		 boolean bMatched  =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
		 if(bMatched) {
			 bReturn = true;
			 break main;
		 }
		 			                        
	}//end main:
	return bReturn;
}

	public static boolean makeDirectory(File objFile) throws ExceptionZZZ {
		if(objFile==null) {
			ExceptionZZZ ez  = new ExceptionZZZ("FileObject", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if (objFile.exists() && !objFile.isDirectory()) {
			return false;
		}
		if (objFile.exists() && objFile.isDirectory()) {
			return true;
		}
		return objFile.mkdirs ();
	}

	/** prüft ob ein gegebener dirName ein Directory repräsentiert und erzeugt gegebenenfalls ein solches dir */
	public static boolean makeDirectoryForFile (String sFileName) throws ExceptionZZZ{
		File f = new File (sFileName);
		return FileEasyZZZ.makeDirectoryForFile(f);
	}
	
	public static boolean makeDirectoryForFile(File objFile) throws ExceptionZZZ {
		if(objFile==null) {
			ExceptionZZZ ez  = new ExceptionZZZ("FileObject", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if (objFile.exists() && !objFile.isFile()) {
			return false;
		}
		if (objFile.exists() && objFile.isFile()) {
			return true;
		}
		
		File objDir = objFile.getParentFile();
		return objDir.mkdirs ();
	}
	
	public static boolean makeDirectoryForDirectory(File objFile) throws ExceptionZZZ {
		if(objFile==null) {
			ExceptionZZZ ez  = new ExceptionZZZ("FileObject", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if (objFile.exists()) {
			return true;
		}
		
		return objFile.mkdirs ();
	}

	/** prüft ob ein gegebener dirName ein Directory repräsentiert und erzeugt gegebenenfalls ein solches dir */
	public static boolean makeDirectory (String dirName) throws ExceptionZZZ{
		File d = new File (dirName);
		return FileEasyZZZ.makeDirectory(d);
	}
	
	public static boolean removeFile(String sFilePathName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilePathName)){
				ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sFilePathName);
			bReturn = FileEasyZZZ.removeFile(objFile);
		}
		return bReturn;
}
	
public static boolean removeFile(File objFile) throws ExceptionZZZ{
	boolean bReturn = false;
	main:{
		if(objFile==null){
			ExceptionZZZ ez  = new ExceptionZZZ("File Object", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(objFile.exists()==false){
			bReturn = true;
			break main;
		}
		if(objFile.isDirectory()){
			bReturn = false;
			break main;
		}
		
		if(objFile.isFile()==false){
			ExceptionZZZ ez = new ExceptionZZZ("File='" + objFile.getAbsolutePath() + "' is not a file.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		bReturn = objFile.delete();			
	}
	return bReturn;
}

	/**Ersetzte das angegebene Verzeichnis durch ein neu erstelltes.
	 * @param objFileDirectory
	 * @return
	 * @author Fritz Lindhauer, 20.11.2020, 13:56:52
	 * @throws ExceptionZZZ 
	 */
	public static boolean replaceDirectory(File objFileDirectory) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sLog;
			if(objFileDirectory==null) {
				ExceptionZZZ ez  = new ExceptionZZZ("File Object", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if(!objFileDirectory.exists()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"FALL: VERZEICHNIS EXISTIERT NOCH NICHT '" + objFileDirectory.getAbsolutePath() + "'";
			   	System.out.println(sLog);
			   	bReturn = FileEasyZZZ.createDirectory(objFileDirectory);
			   	break main;
			}
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+"FALL: VERZEICHNIS EXISTIERT " + objFileDirectory.getAbsolutePath() + "'";
		   	System.out.println(sLog);
		   	boolean bSuccess = FileEasyZZZ.removeDirectoryContent(objFileDirectory, true);	
			if(!bSuccess) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"(I) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Keine Operation mit dem Verzeichnis möglich '" + objFileDirectory.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}	
			bReturn = true;
		}//end main:
	return bReturn;
	}
	
	
	/** Löscht ein Verzeichnis.
	* @return boolean,	 true, wenn das Verzeichnis nicht existiert hat oder erfolgreich gel�scht werden konnte.
	*                					 false, wenn das Verzeichnis nicht leer ist.
	* @param sDirectoryPath
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 25.10.2006 09:38:28
	 */
	public static boolean searchRemoveDirectory(String sDirectoryPath) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sDirectoryPath);
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Gibt false zur�ck, wenn z.B. das Directory nicht leer ist.
			bReturn = objFile.delete();
			
		}
		return bReturn;
	}
	
	
	public static boolean searchRemoveDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile = new File(sDirectoryPath);
			if(objFile.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFile.isDirectory()==false){
				ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bEmptyDirectoryBefore==true){
				//Hole alle dateien und l�sche diese
				File[] objaFile =  objFile.listFiles();
				for(int icount = 0; icount <= objaFile.length - 1; icount++){
					objaFile[icount].delete();
				}		
				bReturn = objFile.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
			}else{			
				//Gibt false zur�ck, wenn z.B. das Directory nicht leer ist.
				bReturn = objFile.delete();
			}
		}
		return bReturn;
	}
	
	public static boolean searchRemoveFile(String sFilePathName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilePathName)){
				ExceptionZZZ ez  = new ExceptionZZZ("FilePathName", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//20190116: Sicherstellen, dass die Datei (auch auf einem WebServer) gefunden wird.
			//File objFile = new File(sFilePathName);
			File objFileNormed = FileEasyZZZ.searchFile(sFilePathName);						
			if(objFileNormed.exists()==false){
				bReturn = true;
				break main;
			}
			
			if(objFileNormed.isFile()==false){
				ExceptionZZZ ez = new ExceptionZZZ("FilePathName='" + sFilePathName + "' is a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			bReturn = objFileNormed.delete();
			
		}
		return bReturn;
	}
	
	
	
	
	
	public static boolean isDirectoryEmpty(String sDirectoryPath) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
				if(StringZZZ.isEmpty(sDirectoryPath)){
					ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				File objFile = new File(sDirectoryPath);
				if(objFile.exists()==false){
					ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' does not exist.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
				if(objFile.isDirectory()==false){
					ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + sDirectoryPath + "' is not a directory.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String[] saFile = objFile.list();
				if(saFile==null){
					bReturn = true;
				}else{
					if(saFile.length>=1){
						bReturn = false;
					}else{
						bReturn = true;
					}
				}
		}
		return bReturn;
	}
	
	/** Löscht ein Verzeichnis.
	* @return boolean,	 true, wenn das Verzeichnis nicht existiert hat oder erfolgreich gel�scht werden konnte.
	*                					 false, wenn das Verzeichnis nicht leer ist.
	* @param sDirectoryPath
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 25.10.2006 09:38:28
	 */
	public static boolean removeDirectory(String sDirectoryPath) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectory(sDirectoryPath,false,false);
	}
	
	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
	 * @param objFileIn
	 * @param bEmptyDirectoryBefore
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile =  FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			if(objFile==null)break main;
			bReturn = FileEasyZZZ.removeDirectory(objFile, bEmptyDirectoryBefore, false);
		}
		return bReturn;
	}
	
	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
	 * @param objFileIn
	 * @param bEmptyDirectoryBefore
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectory(String sDirectoryPath, boolean bEmptyDirectoryBefore, boolean bRemoveSubDirectories) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile =  FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			if(objFile==null) {
				bReturn = true; //Wenn nix da ist, war das Löschen trotzdem erfolgreich.
				break main;
			}
			bReturn = FileEasyZZZ.removeDirectory(objFile, bEmptyDirectoryBefore,bRemoveSubDirectories);
		}
		return bReturn;
	}
	
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  ACHTUNG: Löscht IMMER mehrerer Dateien des Parent-Verzeichnis.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(File objFile) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectoryContent(objFile, true, true);
	}
	
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  Löschen mehrerer Dateien des Parent-Verzeichnis nur  wenn bEmptyDirectoryContainingMoreFiles true ist.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(File objFile, boolean bEmptyDirectoryContainingMoreFiles) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectoryContent(objFile, bEmptyDirectoryContainingMoreFiles, true);
	}
	
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  ACHTUNG: Löscht immer auch mehrerer Dateien des Parent-Verzeichnis, wenn eine Datei übergeben wurde.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(String sDirectoryPath) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectoryContent(sDirectoryPath, true, true);
	}
	
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  Löschen mehrerer Dateien des Parent-Verzeichnis nur  wenn bEmptyDirectoryContainingMoreFiles true ist.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(String sDirectoryPath, boolean bEmptyDirectoryContainingMoreFiles) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectoryContent(sDirectoryPath, bEmptyDirectoryContainingMoreFiles, true);
	}
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  Löschen mehrerer Dateien des Parent-Verzeichnis nur  wenn bEmptyDirectoryContainingMoreFiles true ist.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(String sDirectoryPath, boolean bEmptyDirectoryContainingMoreFiles, boolean bRemoveSubDirectories) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sDirectoryPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("DirectoryPath", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			File objFile =  FileEasyZZZ.searchDirectory(sDirectoryPath); //Soll auch auf einem Web Server die passende Datei finden.
			if(objFile==null) break main;
			
			bReturn = FileEasyZZZ.removeDirectoryContent(objFile, bEmptyDirectoryContainingMoreFiles, bRemoveSubDirectories);
		}
		return bReturn;
	}
	/** Entferne nur den Inhalt eines Verzeichnisses. Das Verzeichnis selbst bleibt besthen. 
	 *  Wird eine Datei übergeben, wird sie gelöscht, sofern sie alleine im Verzeichnis ist.
	 *  Löschen mehrerer Dateien des Parent-Verzeichnis nur  wenn bEmptyDirectoryContainingMoreFiles true ist.
	 * @param objFileIn
	 * @param bEmptyDirectoryContainingMoreFile    Sicherheitsflag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
	 */
	public static boolean removeDirectoryContent(File objFileIn, boolean bEmptyDirectoryContainingMoreFiles, boolean bRemoveSubDirectories) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileIn==null){
				ExceptionZZZ ez  = new ExceptionZZZ("File Object for DirectoryPath ", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
							
			//Merke: Wenn kein Verzeichnis übergeben wurde, dann wird das Verzeichnis eben geholt.
			File objFileDirectory;
			if(objFileIn.isFile()) {
				objFileDirectory = objFileIn.getParentFile();
				if(objFileDirectory==null) break main;			
			}else {
				objFileDirectory = objFileIn;
			}
			if(objFileDirectory.exists()==false){
				bReturn = true;
				break main;
			}
			String sLog = ReflectCodeZZZ.getPositionCurrent()+"(J) XXXXXXXXXXXXXX.";
		   	System.out.println(sLog);
			
			//Hole alle dateien und lösche diese ggfs.
			File[] objaFile =  objFileDirectory.listFiles();
			if(objaFile.length==1) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"(JA) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				//Es ist ggfs. nur die Ausgangsdatei vorhanden, also löschen
				File objFileTemp = objaFile[0];
				if(objFileTemp.isFile()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"(JAA) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					bReturn = objFileTemp.delete();
				}else {	
					sLog = ReflectCodeZZZ.getPositionCurrent()+"(JAB) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					bReturn = FileEasyZZZ.removeDirectory(objFileTemp, bEmptyDirectoryContainingMoreFiles,bRemoveSubDirectories);			
				} 
				if(!bReturn) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"(K) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez  = new ExceptionZZZ("Unable to delete File Object (a) '" +objFileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"(JB) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				//Nur löschen, wenn explizit gesagt worden ist "alle Dateien" löschen
				if(bEmptyDirectoryContainingMoreFiles) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBA) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					for(int icount = 0; icount <= objaFile.length - 1; icount++){
						File objFileTemp = objaFile[icount];
						if(objFileTemp.isFile()) {
							bReturn = objaFile[icount].delete();
							if(!bReturn) {
								sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBAA) XXXXXXXXXXXXXX.";
							   	System.out.println(sLog);
							   	
								ExceptionZZZ ez  = new ExceptionZZZ("Unable to delete File Object(b) '" +objFileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, null, ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
						}else {
							bReturn = FileEasyZZZ.removeDirectoryContent(objFileTemp, bEmptyDirectoryContainingMoreFiles,bRemoveSubDirectories);
							if(!bReturn) {
								sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBAB) XXXXXXXXXXXXXX.";
							   	System.out.println(sLog);
							   	
								ExceptionZZZ ez  = new ExceptionZZZ("Unable to delete Content for Directory (c) '" +objFileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, null, ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
							if(bRemoveSubDirectories) {
								sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBBB) XXXXXXXXXXXXXX.";
							   	System.out.println(sLog);
							   	
								bReturn = FileEasyZZZ.removeDirectory(objFileTemp, false);
								if(!bReturn) {
									sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBBBA) XXXXXXXXXXXXXX.";
								   	System.out.println(sLog);
								   	
									ExceptionZZZ ez  = new ExceptionZZZ("Unable to delete Directory (d) '" +objFileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, null, ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}
							}
						}						
					}							
					bReturn = true; //Merke: Das Verzeichnis selbst soll ja nicht gelöscht werden.
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+"(JBB) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					//Das Verzeichnis wird nicht geleert, darf also nicht gelöscht werden.
					ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + objFileDirectory.getAbsolutePath() + "' is not a single file containing directory. Call this method with the 'bEmptyDirectoryContainingMoreFiles' argument.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;						
				}										
			}							
		}
		return bReturn;
	}
	
	/**Das Problem mit einfachen java.io.File ist, dass es in .jar Datei nicht funktioniert. 
	 *  Darum wird hier per classloader die Ressource geholt.
	 * @param sFilePath
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String getNameFromFilepath(String sFilePath) throws ExceptionZZZ{
		return getNameFromFilepath_(sFilePath, File.separatorChar);
	}
	
	
	private static String getNameFromFilepath_(String sFilePath, char cDirectorySeparator) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(sFilePath==null){ //Merke: Anders als bei einer Datei, darf der Directory-Name leer sein.
				ExceptionZZZ ez  = new ExceptionZZZ("sFilePath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	FileEasyZZZ.splitFilePathName(sFilePath, strDirectory, strFileName, cDirectorySeparator);
	    	sReturn=strFileName.get();	    	
		}//end main:
		return sReturn;
	}
	
	public static String getNameEnd(File objFile) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(objFile==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Fileobject parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			String sFileName = objFile.getName();		
			sReturn = NameEndCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	public static String getNameEnd(String sFilenameTotal) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(sFilenameTotal==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			String sFileName = FileEasyZZZ.getNameFromFilepath(sFilenameTotal);		
			sReturn = NameEndCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	public static String NameEndCompute(String sFileName){
		String sFunction = new String("");
		
		int iFileOnlyLength;
				
		main:{
					
		//Ermitteln der Dateinamensendung.
		iFileOnlyLength = sFileName.lastIndexOf(IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR);
		if(iFileOnlyLength > -1){
			sFunction = sFileName.substring(iFileOnlyLength + 1);
		}

		}
		
		end:{
			return sFunction;
		}		
	} // end function
	
	
	public static String getNameOnly(File objFile) throws ExceptionZZZ {
		String sReturn = new String("");
		main:{
			if(objFile == null) {
				 ExceptionZZZ ez = new ExceptionZZZ("Missing Fileobject as parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;
			}
			
			String sFilenameTotal = objFile.getAbsolutePath();
			sReturn = FileEasyZZZ.getNameOnly(sFilenameTotal);
		}//end main:
		return sReturn;
	}
	public static String getNameOnly(String sFilenameTotal) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			check:{
				if(sFilenameTotal==null){
					 ExceptionZZZ ez = new ExceptionZZZ("Missing Filepath parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
			}//END Check:
		
			
			String sFileName = FileEasyZZZ.getNameFromFilepath(sFilenameTotal);
		
			sReturn = NameOnlyCompute(sFileName);
		
		return sReturn;
		}//END main:
	}
	
	
	public static String NameOnlyCompute(String sFileName) throws ExceptionZZZ{
		String sFunction = new String("");
		
		main:{
			check:{
				if(sFileName==null){
				 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;		 
				}
			}//end check:
				
				
			int iEnding = getNameEnd(sFileName).length();
			if(iEnding > 0){
				sFunction = sFileName.substring(0,sFileName.length()-(iEnding + 1));
			}else{
				sFunction = sFileName;
			}
		}
		end:{
			return sFunction;
		}		
	}
	
	public static String getNameWithChangedEnd(File objFile, String sEndIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objFile==null){
			 ExceptionZZZ ez = new ExceptionZZZ("Missing Fileobject Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			  //doesn�t work. Only works when > JDK 1.4
			  //Exception e = new Exception();
			  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			  throw ez;		 
			}
			
			String sFileName = objFile.getName();
			sReturn = FileEasyZZZ.getNameWithChangedEnd(sFileName, sEndIn);
			
		}//end main;
		return sReturn;
	}
	
	public static String getNameWithChangedEnd(String sFileName, String sEndIn) throws ExceptionZZZ{
		String sReturn = null;
		
			if(StringZZZ.isEmpty(sFileName)){
			 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			  //doesn�t work. Only works when > JDK 1.4
			  //Exception e = new Exception();
			  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			  throw ez;		 
			}
			
			String sEnd;
			if(sEndIn == null){
				sEnd = "";
			}else{
				sEnd = sEndIn;
			}

		
		//#########################################
		//Ermitteln der Dateinamensendung.
		int iFileOnlyLength = sFileName.lastIndexOf(IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR);
		if(iFileOnlyLength > -1){
			sReturn = StringZZZ.left(sFileName, iFileOnlyLength + 1);
			if(StringZZZ.isEmpty(sReturn)){
				//Dann ist irgendwas anders als geplant......
				sReturn = sFileName + IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEnd;
			}else{
				sReturn = sReturn + sEnd;
			}
		}else{
			//Es gibt keinen Punkt im Dateinamen
			sReturn = sFileName + IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEnd;
		}
		return sReturn;
	}
	


public static String getNameWithChangedSuffixKeptEnd(String sFileName, String sSuffix) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFileName)){
			 ExceptionZZZ ez = new ExceptionZZZ("Missing Filename Parameter.", 101, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			  //doesn�t work. Only works when > JDK 1.4
			  //Exception e = new Exception();
			  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			  throw ez;		 
			}
			
			if(StringZZZ.isEmpty(sSuffix)) break main;
			
			String sFileNameLeft = FileEasyZZZ.getNameOnly(sFileName);
			String sFileNameRight = FileEasyZZZ.getNameEnd(sFileName);
			
			sReturn = sFileNameLeft + sSuffix + IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sFileNameRight;
		
		}//end main:
		return sReturn;
	}

	public static String getParent(File objFile) throws ExceptionZZZ{
		String sReturn = "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			if(objFile==null) break main;
			
			String sFilePath = objFile.getAbsolutePath();
			sReturn = FileEasyZZZ.getParent(sFilePath);
		}
		return sReturn;
	}

	public static String getParent(String sFilePathIn) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			sReturn = FileEasyZZZ.getParent(sFilePathIn, null);
		}//end main:
		return sReturn;
	}
	public static String getParent(String sFilePathIn, String sDirectorySeparatorUsedIn) throws ExceptionZZZ {
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			
			//An empty string is allowed
			if(StringZZZ.isEmpty(sFilePathIn)) break main;
			String sFilePath=StringZZZ.stripFileSeparatorsRight(sFilePathIn);			
			
			String sDirectorySeparatorUsed;
			if(StringZZZ.isEmpty(sDirectorySeparatorUsedIn)) {
				sDirectorySeparatorUsed = IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR;
			}else {
				sDirectorySeparatorUsed = sDirectorySeparatorUsedIn;
			}
			
			
			StringTokenizer token = new StringTokenizer(sFilePath, sDirectorySeparatorUsed);
			while(token.hasMoreTokens()) {
				String stemp = (String) token.nextToken();			
				if(token.hasMoreTokens()) {
					if(sReturn.equals("")) {
						sReturn = stemp; //Merke: Es geht ja darum den "Vorletzten" Eintrag zu finden.
					}else {
						sReturn = sReturn + sDirectorySeparatorUsed + stemp;
					}
				}else {					
					break; //Merke: Es geht ja darum den "Vorletzten" Eintrag zu finden.
				}
				
					//Auskommentiert, weil in einer JAR-Datei alle Pfade des  ZipEntries mit "Slash" sind. Also wäre "\\" falsch extra anzuhängen.
//					if(!StringZZZ.isEmpty(stemp)) {
//						if(! stemp.endsWith(File.separator)){
//							stemp = stemp + File.separator;
//						}
//					}					
			}
			
		}//end main:
		return sReturn;
	}
	
	
	/** Sinnvolle Methode, wenn es ausreicht einen simplen Dateinamen zu haben, ohne die Verzeichnisstruktur.
	 *  Wird z.b. verwendet beim Speichern einer Ressource als echte temp-Datei.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 08.08.2020, 12:59:00
	 * @throws ExceptionZZZ 
	 */
	public static String flattenFilePathToFileName(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sFilePathNormedForTempFile = StringZZZ.replace(sFilePath, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, "_");													
			sReturn = StringZZZ.replace(sFilePathNormedForTempFile, "/", "_");
		}//end main:
		return sReturn;
	}
	
	/** Verwend Slash statt Backslash, z.B. Git verwendet grundsätzlich '/'
	 * @param sFilePath
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String normlizeFilePath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmptyNull(sFilePath)) {
				String stemp = " 'FilePathString'";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING,FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			sReturn = sFilePath.replace('\\', '/'); //z.B. 	 // Git verwendet grundsätzlich '/'
		}
		return sReturn;	
	}
	
	/** Verwende Slash statt Backslash, bzw. umgekehrt.
	 *  Das ist nicht so pauschal wie die Methode ohne Angabe des Ziel "Verzeichnistrenners".
	 *  z.B. Git verwendet grundsätzlich '/'. Wenn ich unter Windows arbeite, will ich aber vielleicht immer den Windows-Verzeichnistrenner haben.
	 * @param sFilePath
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String normalizeFilePath(String sFilePath, char cDirectorySeparator) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmptyNull(sFilePath)) {
				String stemp = " 'FilePathString'";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING,FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(cDirectorySeparator == '\\'){
				sReturn = sFilePath.replace('/', cDirectorySeparator); //z.B. 	 // Git verwendet grundsätzlich '/'
			}else if(cDirectorySeparator == '/'){
				sReturn = sFilePath.replace('\\', cDirectorySeparator); //z.B. 	 // Git verwendet grundsätzlich '/'	
			}else {
				String stemp = "Directory Separator not supported: '" + cDirectorySeparator + "'";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_VALUE,FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}
		return sReturn;	
	}
	
	public static String joinFilePathName(File objFileForDirectory, String sFileNameIn) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
			//An empty string is allowed
			if(objFileForDirectory==null){
				//here is the code throwing an ExceptionZZZ
				String stemp = " 'FileObject for directory'";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING,  FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   //doesn�t work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
				   throw ez;	
			}
			
			String sFilePath=objFileForDirectory.getAbsolutePath();
			String sDirectory;
			if(objFileForDirectory.isFile()) {
				File objFileDirectory = FileEasyZZZ.searchDirectoryFromFilepath(sFilePath);
				sDirectory = objFileDirectory.getAbsolutePath();
			}else {
				sDirectory = sFilePath;
			}
			
			sReturn = FileEasyZZZ.joinFilePathName(sDirectory, sFileNameIn);
		
		}//end main:
		return sReturn;
	}

	/** Joins any Filepath-String with the Filename. E.g. in the case the filepath has an backslash at the end
	 * @param sFilePath
	 * @param sFilePath
	 * @throws ExceptionZZZ 
	 */
	public static String joinFilePathName(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{
		return joinFilePathName_(sFilePathIn, sFileNameIn, File.separatorChar, false, false, false, false);
	}
	
	/** Joins any Filepath-String with the Filename. E.g. in the case the filepath has an backslash at the end
	 * @param sFilePath
	 * @param sFilePath
	 * @throws ExceptionZZZ 
	 */
	public static String joinFilePathName(String sFilePathIn, String sFileNameIn, boolean bReturnAsRelativePath) throws ExceptionZZZ{
		return joinFilePathName_(sFilePathIn, sFileNameIn, File.separatorChar, false, false, false, bReturnAsRelativePath);
	}
	
	public static String joinFilePathName(String sFilePathIn, String sFileNameIn, char cDirectorySeparator) throws ExceptionZZZ{
		return joinFilePathName_(sFilePathIn, sFileNameIn, cDirectorySeparator, false, false, false, false);
	}
	
	/** Es wird der lokale Root bei relativen Pfaden nicht vorangestellt.
	 *                               Sondern nur ein SLASH.
	 *                               Ist quasi für Pfade auf anderen Servern, z.B. FTP.
	 * @param sFilePathIn
	 * @param sFileNameIn              
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2021, 08:45:42
	 */
	public static String joinFilePathNameForRemote(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{
		return joinFilePathName_(sFilePathIn, sFileNameIn, File.separatorChar, true, false, false, false);
	}
	
	/** Es wird der lokale Root bei relativen Pfaden nicht vorangestellt.
	 *                               Sondern nur ein SLASH.
	 *                               Ist quasi für Pfade auf anderen Servern, z.B. FTP.
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @param cDirectorySeparator
	 * @param bRemote,               wenn true, dann wird der lokale Root bei relativen Pfaden nicht vorangestellt. Ist quasi für Pfade auf anderen Servern.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2021, 08:47:01
	 */
	public static String joinFilePathNameForRemote(String sFilePathIn, String sFileNameIn, char cDirectorySeparator) throws ExceptionZZZ{
		return joinFilePathName_(sFilePathIn, sFileNameIn, cDirectorySeparator, true, false, false, false);
	}
	
	/**Den Dateipfad so zusammensetzen wie er für ein Url gueltig ist.
	 * Ein normaler Dateipfad bekommt ggfs. src-Ordner vorangestellt
	 * Ein normaler Dateipfad bekommt ggfs. Slashe.
	 * 
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.05.2023, 20:53:42
	 */
	public static String joinFilePathNameForUrl(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{		
		return joinFilePathNameForJar(sFilePathIn, sFileNameIn);
	}
	
	/**Den Dateipfad so zusammensetzen wie er für ein ZIP / JAR Archiv gueltig ist.
	 * Ein normaler Dateipfad bekommt ggfs. src-Ordner vorangestellt
	 * Ein normaler Dateipfad bekommt ggfs. Slashe.
	 * 
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.05.2023, 20:53:42
	 */
	public static String joinFilePathNameForJar(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{		
		String sReturn = joinFilePathName_(sFilePathIn, sFileNameIn, JarEasyZZZ.cDIRECTORY_SEPARATOR, false, true, false, false); //Ohne src voranzustellen
		sReturn = JarEasyUtilZZZ.toJarFilePath(sReturn); //Backslash in Slash umwandeln, etc.                    
		return sReturn;
	}
	
	/**Den Dateipfad so zusammensetzen dass immer der ermittelte ROOT-Pfad, also ggfs. src verwendet wird.
	 * Slash in Backslash umwandeln, etc.
	 * 
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.05.2023, 20:53:42
	 */
	public static String joinFilePathNameForWorkspace(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ{		
		String sReturn = joinFilePathName_(sFilePathIn, sFileNameIn, JarEasyZZZ.cDIRECTORY_SEPARATOR, false, false, true, false); //Ohne src voranzustellen
		sReturn = JarEasyUtilZZZ.toFilePath(sReturn); //Slash in Backslash umwandeln, etc.                    
		return sReturn;
	}
	
	/**Den Dateipfad so zusammensetzen dass immer der ermittelte ROOT-Pfad, also ggfs. src verwendet wird.
	 * Slash in Backslash umwandeln, etc.
	 * 
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.05.2023, 20:53:42
	 */
	public static String joinFilePathNameForWorkspace(String sFilePathIn, String sFileNameIn, boolean bReturnAsRelativePath) throws ExceptionZZZ{		
		String sReturn = joinFilePathName_(sFilePathIn, sFileNameIn, JarEasyZZZ.cDIRECTORY_SEPARATOR, false, false, true, true); //Ohne src voranzustellen
		sReturn = JarEasyUtilZZZ.toFilePath(sReturn); //Slash in Backslash umwandeln, etc.                    
		return sReturn;
	}
	
	/**
	 * @param sFilePathIn
	 * @param sFileNameIn
	 * @param cDirectorySeparator
	 * @param bRemote,               wenn true, dann wird der lokale Root bei relativen Pfaden nicht vorangestellt. Ist quasi für Pfade auf anderen Servern.
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.02.2021, 08:47:13
	 */
	private static String joinFilePathName_(String sFilePathIn, String sFileNameIn, char cDirectorySeparator, boolean bRemote, boolean bUrl, boolean bWorkspace, boolean bReturnAsRelativePath) throws ExceptionZZZ{
		String sReturn= "";//Merke: Es ist wichtig ob null oder Leerstring. Je nachdem würde eine andere Stelle des Classpath als Root verwendet.
		main:{
		String stemp;
		String sFilePath; String sFileName; String sRoot="";
		String sDirectorySeparator = StringZZZ.char2String(cDirectorySeparator);
				
		//ZUALLERERST DEN FILENAME pruefen
		sFileName = StringZZZ.stripFileSeparators(sFileNameIn);
		if(!StringZZZ.isEmpty(sFileName)) {
			if(FileEasyZZZ.isPathAbsolute(sFileName)){
				sReturn = sFileName;
				break main;
			}
		}
		
		//ZUERST DEN ROOT AUSRECHNEN
		//!!! WENN bRemote, dann den Pfad nicht verändern!!!
		if(bRemote) {
			//Sicherstellen, dass bei dem Remotefile nur der Slash voransteht und nicht etwa der Eclipse Src-Ordner.
			//Zumindest bei T-Online FTP Server ist der fuehrende Slah gewünscht.
			sRoot = CharZZZ.toString(cDirectorySeparator);
			sFilePath = sFilePathIn;					
		}else if(bUrl) {
			//!!! Wenn Pfade in einem JAR / ZIP File bearbeitet werden, dann keine src-Folder voranstellen
			sFilePath = sFilePathIn;
		} else if(bWorkspace) {
			//!!! Wenn sichergestellt werden soll, dass ein src-Verzeichnis ggfs. verwendet wird, auch bei ggf. absoluten Pfaden
			sFilePath = sFilePathIn;						
			if(!(StringZZZ.endsWith(sFilePath, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER) |
				 StringZZZ.endsWith(sFilePath, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER) |
				 StringZZZ.endsWith(sFilePath, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER))){
				
				sRoot = FileEasyZZZ.getFileRootPath();
				sFilePath = FileEasyZZZ.joinFilePathName(sFilePath, sRoot, cDirectorySeparator);
			}
		} else{
			 IFileEasyPathObjectZZZ objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn,cDirectorySeparator, bReturnAsRelativePath);			 		 
			 sRoot = objPath.getRootAdditionalComputed();	
			 if(bReturnAsRelativePath) { //auch wenn der relative Pfad zurueckgegeben wird, ist der PathTotal immer vollstaendiger.
				 if(FileEasyZZZ.isPathRelative(objPath.getFilePathTotal())){
					 sFilePath = objPath.getFilePathTotal(); 					 
				 }else {
					 sFilePath = objPath.getFilePath(); //Darin sind ggfs. ergänzende Verzeichnispfade wie src, test, tryout enthalten.
				 }
			 }else {
				 sFilePath = objPath.getFilePathTotal(); //also: Der Converter liefert für sich den Gesamtpfad zurueck. Aber das .join(...) bildet für sich den Gesamtpfad mit dem Dateinamen. Darum heissen die Variablen so. //alt... FileEasyZZZ.joinFilePathName(sRoot, sFilePath, cDirectorySeparator);				 
			 }			
		}
				
		
		//ObjectZZZ.logLineDate(FileEasyZZZ.class,  "wird NICHT benutzt sFilePathPure='"+ sFilePathPure + "'" );
		//ObjectZZZ.logLineDate(FileEasyZZZ.class,  "wird NICHT benutzt sRoot='"+ sRoot + "'" );
		//ObjectZZZ.logLineDate(FileEasyZZZ.class,  "wird benutzt! sFilePath as total='"+ sFilePath+"'" ); 
		
		if(StringZZZ.isEmpty(sFileName)){
			//An empty string or NULL is allowed for Filename	
			sReturn = sFilePath;
			break main;
		}
		
		//Falls sFileNameIn ein Teil von sFilePathIn ist, oder in einem anderen Verzeichnis			
		//1. TEST: Name hat Bestandteil des Filepfads enthalten
		if(!StringZZZ.isEmpty(sFilePath)) {
			if(sFileName.startsWith(sFilePath+sDirectorySeparator)) {
				sFileName = StringZZZ.right(sFileName, sFilePath+sDirectorySeparator, true);
			}											   	
		}		 	
						
		StringTokenizer objToken;			
		String sDelim = sDirectorySeparator;
		
		//+++ Get the strings in the expected value for joining them
		if(!sFilePath.equals("")){
			objToken = new StringTokenizer(sFilePath, sDelim);
			
			//Eleminate empty strings
			String sFilePathTemp = new String("");
			int iNrOfTokenTotal = objToken.countTokens(); //Merke: Dies wird durch den Aufruf von .nextToken() immer ver�ndert/weniger. Darum vorher in einer Variablen sichern.
			for( int icount = 1;icount <= iNrOfTokenTotal; icount++){
				stemp = objToken.nextToken();									
				if(!stemp.equals("") && !stemp.equals(sDelim)){
						if(!sFilePathTemp.equals("")){
							sFilePathTemp = sFilePathTemp + sDelim + stemp;
						}else{
							sFilePathTemp = stemp;
						}
					}
				} //END for
			sFilePath = sFilePathTemp;												
			}
					
			//+++  Join the strings		
			if(sFilePath.equals("")){
				//A)	
				if(!FileEasyZZZ.isPathAbsolute(sFileName)){
					sReturn = sRoot  + sDirectorySeparator +  sFileName;
				}else {
					sReturn = sFileName;
				}
			}else{
				if(sFileName.equals("")){
					//B)
					if(FileEasyZZZ.isPathAbsolute(sFilePath)){
						sReturn = sFilePath;						
					}else {
						if((IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + sFilePath).endsWith(IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)) {
							sReturn = sFilePath + sDirectorySeparator + sFileName; //Für die TESTfälle: Dateien, die im test-Ordner liegen.
						}else if((IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + sFilePath).endsWith(IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)) {
							sReturn = sFilePath + sDirectorySeparator + sFileName; //Für die TRYOUTfälle: Dateien, die im tryout-Ordner liegen.
						}else {
							sReturn = sDirectorySeparator +  sFilePath;
						}
					}	
				}else{
					//C)
					//ÄHEM, hier im C - Fall scheint alles gleich ausgerchnet zu werden. Dann kann man den Code aber deutlich vereinfachen.
					
					if(FileEasyZZZ.isPathAbsolute(sFilePath)){
						sReturn = sFilePath + sDirectorySeparator + sFileName;						
					}else {
						if((IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + sFilePath).endsWith(IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)) {							
							sReturn = sFilePath + sDirectorySeparator + sFileName; //Für die TESTfälle: Dateien, die im test-Ordner liegen.
						}else if((IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + sFilePath).endsWith(IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)) {
							sReturn = sFilePath + sDirectorySeparator + sFileName; //Für die TESTfälle: Dateien, die im tryout-Ordner liegen.
						}else {							
							sReturn = sFilePath + sDirectorySeparator + sFileName;
						}
					}					
				}
			}
			
			//Vor dem Schluss noch einmal normieren, also Backslashe etc. vorne und hinten wegnehmen.
			sReturn = StringZZZ.trimFileSeparators(sReturn);
			if(bRemote) {
				sReturn = IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR_UNIX + sReturn; //Somit wird sichergestellt, das auf dem Remote Server ein relativer Pfad vorhanden ist.
			}
		}//end main
		return sReturn;
	}
	
	
	
	

	/** Changes the ending of a filename. e.g. test.csv --> test.ini 
	 @param sFilePath
	 @param sEndNew
	 @return the name with the new ending
	 */
	public static String NameEndChange(String sFileNameIn, String sEndNewIn)throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp; String sFileName;String sEndNew;
			if(sFileNameIn==null){
				//here is the code throwing an ExceptionZZZ
				stemp = "''FileName'";			   
				  ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   //doesn't work. Only works when > JDK 1.4
				   //Exception e = new Exception();
				   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
				   throw ez;	
			}else if (sFileNameIn.equals("")){
					//	here is the code throwing an ExceptionZZZ
					stemp = "'FileName'";
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_EMPTY + stemp, iERROR_PARAMETER_EMPTY, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					//doesn't work. Only works when > JDK 1.4
					//Exception e = new Exception();
					//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");			  
					throw ez;	
			}else{
				sFileName = sFileNameIn;
			}
			
			if(sEndNewIn==null){
				sReturn = sFileName;
				break main;
			}else if(sEndNewIn.equals("")){
				sReturn = sFileName;
				break main;
			}else{
				sEndNew = sEndNewIn;
			}
			
			//#################
			String sFileOnly;
			int iFileOnlyLength = sFileName.lastIndexOf(IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR);
			if(iFileOnlyLength > -1){
				sFileOnly = sFileName.substring(0, iFileOnlyLength);   //.substring(iFileOnlyLength + 1);
			}else{
				sFileOnly = sFileName;
			}
			
			sReturn = sFileOnly + IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEndNew;			
		}//end main
		return sReturn;	
	}//end function
	
	
	public static String PathNameTotalCompute(String sDirectoryName, String sFileName) throws ExceptionZZZ{
		String sReturn;		
		main:{
			sReturn = FileEasyZZZ.joinFilePathName(sDirectoryName, sFileName);
		}
		return sReturn;	
	} // end function
	
	
	/** Da die Arbeit mit dem normalen fileobjekt.isDirectory() nicht immer klappt. 
	 *  Wir hier intern !fileobjekt.isFile() verwendet.
	 * @param file2proof
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 30.11.2020, 09:11:01
	 */
	public static boolean isDirectory(File file2proof) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(file2proof==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			bReturn = !file2proof.isFile();
		}//end main:
		ObjectZZZ.logLineDate(FileEasyZZZ.class, "isDirectory=" + bReturn );
		return bReturn;	
	}
	
	/** Da die Arbeit mit dem normalen fileobjekt.isDirectory() nicht immer klappt. 
	 *  Wir hier intern !fileobjekt.isFile() verwendet.
	 * @param file2proof
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 30.11.2020, 09:11:01
	 */
	public static boolean isDirectoryExisting(File file2proof) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(file2proof==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			bReturn = FileEasyZZZ.isDirectory(file2proof);
			if(bReturn) {
				bReturn = FileEasyZZZ.exists(file2proof);
			}
		
		}//end main:
		LogZZZ.logProtocolStringStatic(FileEasyZZZ.class, "isDirectoryExisting=" + bReturn);//.writeLine(FileEasyZZZ.class, "isDirectoryExisting=" + bReturn) ;
		return bReturn;	
	}
	
	/** Da die Arbeit mit dem normalen fileobjekt.isDirectory() nicht immer klappt. 
	 *  Wir hier intern !fileobjekt.isFile() verwendet.
	 * @param file2proof
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 30.11.2020, 09:11:01
	 */
	public static boolean isDirectoryExistingInTemp(String sDirectorySubInTemp) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String stemp;
			if(sDirectorySubInTemp==null){				
				stemp = " 'FilePathInTemp' ";
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + stemp, iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());				   		 
				throw ez;	
			}
						
			if(FileEasyZZZ.isPathSubExistingOfDirectoryTemp(sDirectorySubInTemp)){
				String sFilePathTemp = EnvironmentZZZ.getHostDirectoryTemp();
				String sFilePathTotal = FileEasyZZZ.joinFilePathName(sFilePathTemp, sDirectorySubInTemp);
				File file2proof = new File(sFilePathTotal);
				bReturn = FileEasyZZZ.isDirectoryExisting(file2proof);				
			}						
		}//end main:
		ObjectZZZ.logLineDate(FileEasyZZZ.class, "isDirectoryExistingInTemp=" + bReturn) ;
		return bReturn;	
	}
	
	/** Da die Arbeit mit dem normalen fileobjekt.isFile() nicht immer klappt (z.B. wenn die Datei nicht auf der Platte liegt) 
	 *  Wir hier intern auch !FileEasyZZZ.isDirectory() verwendet. 
	 *  Da also dies nur klappt, bei Dateien, die auch tatsächlich existieren => entsprechenden Methodennamen gewählt.
	 * @param file2proof
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 30.11.2020, 09:11:01
	 */
	public static boolean isFileExisting(File file2proof) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(file2proof==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			boolean bExists = FileEasyZZZ.exists(file2proof);
			if(bExists) {
				bReturn = file2proof.isFile();
				if(bReturn) break main;
			}else {
				bReturn = !FileEasyZZZ.isDirectory(file2proof);
			}						
		}//end main;
		ObjectZZZ.logLineDate(FileEasyZZZ.class, "isFileExisting=" + bReturn + " (File: '" + file2proof.getAbsolutePath() + "')") ;
		return bReturn;	
	}
	
	/** Checks if a file-object is the root-directory.
	* @param file2proof
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 06.02.2007 11:06:26
	 */
	public static boolean isRoot(File file2proof) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(file2proof==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(FileEasyZZZ.isDirectory(file2proof)==false) break main;
			ObjectZZZ.logLineDate(FileEasyZZZ.class, "#isDirectory=false' (File: '" + file2proof.getAbsolutePath() + "')") ;
			
			File fileParent = file2proof.getParentFile();
			if(fileParent == null) bReturn = true;
		}
		ObjectZZZ.logLineDate(FileEasyZZZ.class, "isRoot=" + bReturn) ;
		return bReturn;
	}
	
	
		/**Resourcen in anderen Projektordnern müssen anders geholt werden als ueber den den Classloader.
		 * Wenn die Projekte in einer .jar Datei zusammengepackt sind, dann gibt es dieses Problem nicht.
		 * 
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 24.06.2023, 12:51:14
		 */
		public static boolean isInIDE() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				boolean bProof = FileEasyZZZ.isOnServer();
				if(bProof) break main;
				
				bProof = FileEasyZZZ.isInJarStatic();
				if(bProof) break main;					
				
				bReturn = true;
			}//end main:
			return bReturn;
		}
		
		/**Resourcen in anderen Projektordnern müssen anders geholt werden als ueber den den Classloader.
		 * Wenn die Projekte in einer .jar Datei zusammengepackt sind, dann gibt es dieses Problem nicht.
		 * 
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 24.06.2023, 12:51:14
		 */
		public static boolean isInIDE(Class<?> classObject) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(classObject==null){
					ExceptionZZZ ez = new ExceptionZZZ("No class object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				boolean bProof = FileEasyZZZ.isOnServer();
				if(bProof) break main;
				
				bProof = JarEasyUtilZZZ.isInJar(classObject);
				if(bProof) break main;					
				
				bReturn = true;
			}//end main:
			return bReturn;
		}
		
		/** Resourcen werden anders geholt, wenn die Klasse in einer JAR-Datei gepackt ist.
		 *  Das Problem ist, das ein Zugriff auf Ressourcen anders gestaltet werden muss, wenn die Applikation in einer JAR-Datei läuft.
		 *  Merke: Static Klassen müssen diese Methode selbst implementieren.
		 * @return
		 * @author lindhaueradmin, 21.02.2019
		 * @throws ExceptionZZZ 
		 */
		public static boolean isInJarStatic() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = JarEasyUtilZZZ.isInJar(FileEasyZZZ.class);
			}
			return bReturn;
		}
		
		/** Prüft, ob die Anwendung auf einem Webserver läuft oder im Eclipse Workspace.
		 *  Beim Eclipse Workspace wird vom Vorhandensein des "src" - Ordners ausgegangen.
		 *  Beim Serverlauf, gibt es den src-Ordner nicht. Er liegt dann auf dem Classpath. 
		 *  
		 *  Heuristische Lösung. 
		 *  Funktioniert so im Vergleich "Webservice" vs. "Swing Standalone in Eclipse"
		 * @return
		 */
		public static boolean isOnServer(){
			boolean bReturn = false;
			main:{
				File objFileTest = new File(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER);
				bReturn = !objFileTest.exists();			
			}//end main:
			return bReturn;
		}
		
		
		/** Prüft, ob die Datei eine Textdatei ist oder nicht.
		 *  Einsatz z.B. bei einem GIT Tool, das sich nur auf Textdateien beziehen kann.
		 *  Z.B. Konflikte auflösen.
		 *  
		 *  s. ChatGPT vom 2026-06-27
		 * @param file
		 * @return
		 * @throws IOException
		 */
		public static boolean isFileText(File file) throws ExceptionZZZ{
			 FileInputStream fis = null;
			try {
			    if (file == null || !file.isFile()) {
			        return false;
			    }
			    
			    try {
			        fis = new FileInputStream(file);
			        
			        
			        byte[] buffer = new byte[4096];
			        int len = fis.read(buffer);
	
			        if (len == -1) {
			            // Leere Datei -> als Text betrachten
			            return true;
			        }
	
			        for (int i = 0; i < len; i++) {
			            int b = buffer[i] & 0xFF;
	
			            // NUL-Byte => sehr wahrscheinlich Binärdatei
			            if (b == 0) {
			                return false;
			            }
	
			            // Erlaubte Steuerzeichen
			            if (b == '\n' || b == '\r' || b == '\t' || b == '\f') {
			                continue;
			            }
	
			            // Sonstige Steuerzeichen (<32) gelten als binär
			            if (b < 32) {
			                return false;
			            }
			        }
	
			        return true;
			    } finally {
			        if (fis != null) {
			            fis.close();
			        }
			    }
		    }catch(IOException ioe) {
		        	ExceptionZZZ ez = new ExceptionZZZ(ioe);
		        	throw ez;
		    }
		}
		
		
		 /**
		   * Determine whether a file is a JAR File.
		   * Idee aus: http://www.java2s.com/Code/Java/File-Input-Output/DeterminewhetherafileisaJARFile.htm
		   */
		public static boolean isJar(File objFile) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No fileobject provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(!objFile.exists()) {
					//Keine Exception werfen. Der Test ist halt nur fehlgeschlagen					
					//ExceptionZZZ ez = new ExceptionZZZ("Fileobject existiert nicht: '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					//throw ez;
					
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "Provided File Objekt does not exist = '" + objFile.getAbsolutePath() +"'");
					break main;
				}
				if(!objFile.isFile()) {
					//Keine Exception werfen. Der Test ist halt nur fehlgeschlagen					
					//ExceptionZZZ ez = new ExceptionZZZ("Fileobject ist keine Datei: '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					//throw ez;
					
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "Provided File Objekt is no file = '" + objFile.getAbsolutePath() +"'");
					break main;
				}
				
				
								 
				try {
					//Merke: Wenn es kein ZipFile ist, so wird ein Fehler (IOException) geworfen. Dieses vorher prüfen und so die Exception vermeiden. 
					if(!FileEasyZZZ.isZip(objFile)) {
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "Provided File Objekt is no zip-file-Type = '" + objFile.getAbsolutePath() +"'");
						break main;
					}
					
					ZipFile zip = new ZipFile(objFile);
					bReturn = zip.getEntry("META-INF/MANIFEST.MF") != null;
					zip.close();
				} catch (ZipException e) {
					ExceptionZZZ ez = new ExceptionZZZ("ZIPExpeption for '" + objFile.getAbsolutePath() + "' : " + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),e);
					throw ez;
				} catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IOExpeption for '" + objFile.getAbsolutePath() + "' : "  + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),e);
					throw ez;
				}				 								
			}//end main
			return bReturn;
		}
		
	
		  /**
		   * Determine whether a file is a ZIP File.
		   * Idee aus: http://www.java2s.com/Code/Java/File-Input-Output/DeterminewhetherafileisaJARFile.htm
		   */
		  public static boolean isZip(File objFile) throws ExceptionZZZ{
			  boolean bReturn = false;
				main:{
					if(objFile==null){
						ExceptionZZZ ez = new ExceptionZZZ("No fileobject provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(!objFile.exists()){
						ExceptionZZZ ez = new ExceptionZZZ("Fileobject not exists: '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					if(!objFile.isFile()) {
						ExceptionZZZ ez = new ExceptionZZZ("Fileobject is no file: '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
		      
		      if(!objFile.canRead()) {
		    		ExceptionZZZ ez = new ExceptionZZZ("Cannot read file '" + objFile.getAbsolutePath() + "'", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
		      }

		      if(objFile.length() < 4)  break main;

		      try {
			      DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(objFile)));
			      int test = in.readInt();
			      in.close();
			      bReturn = (test == 0x504b0304);
		      } catch (IOException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IOExpeption for '" + objFile.getAbsolutePath() + "' : "  + e.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),e);
					throw ez;
				}
			  }//end main;
			  return bReturn;
		  }
		
		public static File[] listDirectoriesOnly(File fileDirectoryIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				if(fileDirectoryIn==null) break main;
				
				File fileDirectory = null;
				if(fileDirectoryIn.isDirectory()) {
					fileDirectory = fileDirectoryIn;
				}else {
					fileDirectory = fileDirectoryIn.getParentFile();
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "Verwende Parent '" + fileDirectory.getAbsolutePath() +"'");					
				}
				
				FileFilter directoryFilter = new FileFilter() {
					public boolean accept(File file) {
						return file.isDirectory();
					}
				};

				objaReturn = fileDirectory.listFiles(directoryFilter);
			}//end main:
			return objaReturn;
		}
		
		/** Listet Dateiobjekte auf, aber auch nur Dateiobjekte und keine Verzeichnisse.
		 *  Anders als File.listFiles(), bei dem auch Verzeichnisobjekte zurückgegeben werden. 
		 * @param fileDirectoryIn
		 * @return
		 * @author Fritz Lindhauer, 10.10.2020, 07:29:10
		 * @throws ExceptionZZZ 
		 */
		public static File[] listFilesOnly(File fileDirectoryIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				if(fileDirectoryIn==null) break main;
				
				File fileDirectory = null;
				if(fileDirectoryIn.isDirectory()) {
					fileDirectory = fileDirectoryIn;
				}else {					
					fileDirectory = fileDirectoryIn.getParentFile();
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "Verwende Parent '" + fileDirectory.getAbsolutePath() +"'");					
				}
				
				FileFilter fileFilter = new FileFilter() {
					public boolean accept(File file) {						
						return file.isFile();
					}
				};

				objaReturn = fileDirectory.listFiles(fileFilter);
			}//end main:
			return objaReturn;
		}
		  
	/** Merke: In einer .jar Datei kann kein Zugriff über File-Objekte erfolgen.
	 *         Verwende dazu die JarEasyZZZ - Klasse.
	 *  
	 * @return
	 */
	public static File searchFileObject(String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{			
			if(sFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			objReturn = searchFileObjectByClassloader_(sFile, false);
			if(objReturn==null) {
				objReturn = searchFileObjectByUri_(sFile,false);
			}
		}//end main:
		return objReturn;			
		
	}
	
	public static File searchFileObjectInWorkspace(String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{			
			if(sFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			objReturn = searchFileObjectByUri_(sFile, false);
		}//end main:
		return objReturn;		
	}
	
	/** Problematik: Java kennt keine Projekte und der Classloader wird also nur das aktuell laufende nutzen.
	 *  Ziel ist es in der Entwicklungumgebung Dateien in anderen Projekten zu finden, 
	 *  diese Projekte können als Module genutzt und in der ini-Datei definiert sein.
	 * 
	 * @param sFile
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static File searchFileObjectInWorkspace(IKernelConfigZZZ objConfig, String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			ObjectZZZ.logLineDate(FileEasyZZZ.class, "Suche auf Projektebene im Workspace.");
		    		    
		    if(objConfig==null) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }
			//Das ist ein Z-Kernel Konzept. Vorraussetzung: Nicht in .Jar oder auf einem Server, also in Eclipse Entwicklungs Umgebung
			if(!objConfig.isInIDE()) break main;					    		    
		
			//Ziel ist z.B.: C:\1fgl\repo\EclipseOxygen_V01\Projekt_Kernel02_JAZLanguageMarkup\JAZLanguageMarkup\src\ZKernelConfig_HtmlTableHandler.ini			
			String sFilePath = ReflectWorkspaceZZZ.computeWorkspacePath(objConfig, sFile);		   
			boolean bExists = FileEasyZZZ.existsInWorkspace(sFilePath);
			if(bExists) {
				objReturn = new File(sFilePath);
			}						
		}//end main:
		return objReturn;			
	}
	
	/** Problematik: In der Entwicklungumgebung quasi die "Codebase" ermitteln oder dort wo der Code aufgerufen wird
	 * @param sFile
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static File searchFileObjectInRunningExecutionProjectPath(String sFile) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+"Suche auf der Ebene des ausfuehrenden Projekts.";
		    System.out.println(sLog);
		    
			//Lösungsidee 3: Merke: "." holt doch tatsächlich den Ordner des aktuell ausgefuehrten Projekts!!!
			if(sFile==null) {//Merke: NULL führt in der searchFileObjectByClassloader_ Methode zu einer Exception.				
				sFile=FileEasyZZZ.getFileRootPath();
				ObjectZZZ.logLineDate(FileEasyZZZ.class, "NULL in '" + sFile +"' umgeändert.");
			}
			objReturn = searchFileObjectByClassloader_(sFile, true);
								
			//Lösungsidee 2:
			//20190215: Arbeite mit TEMP-Ordner
//			if(sFile==null){
//			try {
//				objReturn = File.createTempFile("temp", "ZZZ");						
//				objReturn.deleteOnExit();
//				} catch (IOException e1) {
//					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}	
//			}
			
			//Lösungsidee 1: 
			//20190215: Versuch des Zugriffs über den Eclipse Workspace
			//https://help.eclipse.org/mars/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2FresInt_filesystem.htm
			//Nette Idee: Bleibt aber wohl der PluginEntwicklung vorbehalten.
			//Als Standalone bekomme ich folgenden Fehler: java.lang.NoClassDefFoundError: org/eclipse/osgi/util/NLS
			/*
			try {
			IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();									
			IProject myWebProject = myWorkspaceRoot.getProject("JAZKernel");
			   // open if necessary
			   if (myWebProject.exists() && !myWebProject.isOpen()) myWebProject.open(null);
			
			   IFolder imagesFolder = myWebProject.getFolder("images");
			   if (imagesFolder.exists()) {
			      // create a new file
			      IFile newLogo = imagesFolder.getFile("newLogo.png");
			      FileInputStream fileStream = new FileInputStream("c:/MyOtherData/newLogo.png");
			      newLogo.create(fileStream, false, null);
			      // create closes the file stream, so no worries.   
			   }

//Weitere Code Snippets
//			   IFolder newImagesFolder = myWebProject.getFolder("newimages");
//			   newImagesFolder.create(false, true, null);
//			   IPath renamedPath = newImagesFolder.getFullPath().append("renamedLogo.png");
//			   newLogo.move(renamedPath, false, null);
//			   IFile renamedLogo = newImagesFolder.getFile("renamedLogo.png");
			   
				} catch (CoreException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				*/
										
		}//end main:
		return objReturn;			
	}
	
	private static File searchFileObjectByClassloader_(String sPathIn, boolean bUseProjectBase) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			ClassLoader classLoader = FileEasyZZZ.class.getClassLoader();
			URL workspaceURL = null; String sLog=null;
			
			if(sPathIn==null){							
			  	ExceptionZZZ ez = new ExceptionZZZ("Path", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;								
			}
			
			//Versuch den eingegebenen Pfad zu normieren. Die Pfade werden dabei behandelt wie in FileEasyZZZ.searchDirectory(String sDirectoryIn, boolean bSearchInJar)		
			String sPath; boolean bUseClasspathSource=false; boolean bUseProjectBaseForTest=false; boolean bUseProjectBaseForTryout=false;
			if(bUseProjectBase) {
				sPath = sPathIn;
			}else {
				if(sPathIn.startsWith(XmlUtilZZZ.computeTagNull())){			
					bUseProjectBase=true;
					sPath = StringZZZ.stripLeft(sPathIn, XmlUtilZZZ.computeTagNull());
				}else if(sPathIn.startsWith(XmlUtilZZZ.computeTagEmpty())){		
					bUseClasspathSource=true;
					sPath = StringZZZ.stripLeft(sPathIn, XmlUtilZZZ.computeTagEmpty());
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_PARENT)){
					bUseProjectBase=true;
					sPath = sPathIn;
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)){			
					bUseClasspathSource=true;
					sPath = sPathIn;				
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)){
					bUseProjectBaseForTest=true;
					sPath = sPathIn;
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)){
					bUseProjectBaseForTryout=true;
					sPath = sPathIn;
				}else{
					if(FileEasyZZZ.isPathAbsolute(sPathIn)) {
						sPath = sPathIn;
					}else {					
						//+++ Der Normalfall, aber auch hier darauf achten, nicht den Root erneut davorzusetzen.
						String sPathRoot = FileEasyZZZ.getFileRootPath();
						if( !(sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
							bUseClasspathSource=true;
						}else {
							bUseClasspathSource=false;
						}
						sPath = sPathIn;
					}
				}
					
				//Nun ggfs. noch vorhandene Pfadzeichen am Anfang/Ende entfernen.
				sPath = StringZZZ.stripFileSeparators(sPath);
				
				if(bUseClasspathSource) {										
					String sPathRoot = FileEasyZZZ.getFileRootPath();
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}
				
				if(bUseProjectBaseForTest) {					
					String sPathRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}
				
				if(bUseProjectBaseForTryout) {					
					String sPathRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}								
			}
						
			try {						
				//1. Versuch mit Classloader
				workspaceURL = new File(sPath).toURI().toURL();
				if(workspaceURL!=null){
					ObjectZZZ.logLineDateWithPosition(FileEasyZZZ.class, "(B) Searching for file by classloader.getResource '" + sPath +"'");
					String sPathInWorkspace = workspaceURL.getPath();
					String[] saStringsToBeStripped ={File.separator};
					String sPathNormed = StringZZZ.stripRight(sPathInWorkspace, saStringsToBeStripped);					
					
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(B) URL is not null'");
					try {			
						objReturn = new File(workspaceURL.toURI());
						if(FileEasyZZZ.exists(objReturn)) break main;
					} catch(URISyntaxException e) {
						objReturn = new File(workspaceURL.getPath());
						if(FileEasyZZZ.exists(objReturn)) break main;
					}
				}else{
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(B) URL is null'");
				}
				
				
				//2. Versuch (beim "." wird ggfs. das bin - - Verzeichnis zurükgegeben. Dies entfernen.
				if(workspaceURL!=null){
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(C) Searching for file by classloader.getResource '" + sPath +"', stripping bin - Directory");		
					String sPathInWorkspace = workspaceURL.getPath();
					String[] saStringsToBeStripped2 ={"bin",File.separator};
					String sParthNormed = StringZZZ.stripRight(sPathInWorkspace, saStringsToBeStripped2);
					workspaceURL = classLoader.getResource(sParthNormed);
					if(workspaceURL!=null){
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(C) URL is not null'");
						try {			
							objReturn = new File(workspaceURL.toURI());
							if(FileEasyZZZ.exists(objReturn)) break main;
						} catch(URISyntaxException e) {
							objReturn = new File(workspaceURL.getPath());
							if(FileEasyZZZ.exists(objReturn)) break main;
						}
					}else{
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(C) URL is null'");						
					}
				}				
			} catch (MalformedURLException e) {	
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	private static File searchFileObjectByUri_(String sPathIn, boolean bUseProjectBase) throws ExceptionZZZ{
		File objReturn = null;
		main:{			
			URL workspaceURL = null; String sLog=null;
			
			if(sPathIn==null){							
			  	ExceptionZZZ ez = new ExceptionZZZ("Path", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;								
			}
			
			//Versuch den eingegebenen Pfad zu normieren. Die Pfade werden dabei behandelt wie in FileEasyZZZ.searchDirectory(String sDirectoryIn, boolean bSearchInJar)		
			String sPath; boolean bUseClasspathSource=false; boolean bUseProjectBaseForTest=false; boolean bUseProjectBaseForTryout=false;
			if(bUseProjectBase) {
				sPath = sPathIn;
			}else {
				if(sPathIn.startsWith(XmlUtilZZZ.computeTagNull())){			
					bUseProjectBase=true;
					sPath = StringZZZ.stripLeft(sPathIn, XmlUtilZZZ.computeTagNull());
				}else if(sPathIn.startsWith(XmlUtilZZZ.computeTagEmpty())){		
					bUseClasspathSource=true;
					sPath = StringZZZ.stripLeft(sPathIn, XmlUtilZZZ.computeTagEmpty());
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_PARENT)){
					bUseProjectBase=true;
					sPath = sPathIn;
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)){			
					bUseClasspathSource=true;
					sPath = sPathIn;				
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)){
					bUseProjectBaseForTest=true;
					sPath = sPathIn;
				}else if(sPathIn.startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)){
					bUseProjectBaseForTryout=true;
					sPath = sPathIn;
				}else{
					if(FileEasyZZZ.isPathAbsolute(sPathIn)) {
						sPath = sPathIn;
					}else {					
						//+++ Der Normalfall, aber auch hier darauf achten, nicht den Root erneut davorzusetzen.
						String sPathRoot = FileEasyZZZ.getFileRootPath();
						if( !(sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
							bUseClasspathSource=true;
						}else {
							bUseClasspathSource=false;
						}
						sPath = sPathIn;
					}
				}
					
				//Nun ggfs. noch vorhandene Pfadzeichen am Anfang/Ende entfernen.
				sPath = StringZZZ.stripFileSeparators(sPath);
				
				if(bUseClasspathSource) {										
					String sPathRoot = FileEasyZZZ.getFileRootPath();
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}
				
				if(bUseProjectBaseForTest) {					
					String sPathRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}
				
				if(bUseProjectBaseForTryout) {					
					String sPathRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
					if((sPathIn+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR).startsWith(sPathRoot+IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
						sPath = sPathIn;
					}else {
						sPath = FileEasyZZZ.joinFilePathName(sPathRoot, sPathIn); //Merke: Darin wird auch StripFile Separators gemacht.
					}
				}							
			}
			
			
			//1. Versuch ohne Classloader
			try {		
				workspaceURL = new File(sPath).toURI().toURL();
				if(workspaceURL!=null){	
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(A) Searching for file by URI '" + sPath +"'");
				    				    
					String sWorkspaceURL = workspaceURL.getPath();					
					sWorkspaceURL = StringZZZ.stripFileSeparators(sWorkspaceURL);
					objReturn = new File(sWorkspaceURL);	
					if(FileEasyZZZ.exists(objReturn)) {
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(A1) FileObjekt gefunden '" + sPath + "'");
						break main;
					}else{
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(A1) FileObjekt nicht gefunden '" + sPath + "'");
					}
				}else{
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(A1) URL is null'");
				}
				
			} catch (MalformedURLException e) {	
				ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück.
	 *  Ist allerdings nur ein relativer Pfad.
	 * @return
	 */
	public static String getFileRootPath(){
		String sReturn = "";
		main:{
			if(isOnServer()){
				sReturn = "";
			}else{
				sReturn = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
			}
		}//end main:
		return sReturn;
	}
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück.
	 *  Ist der absolute Pfad.
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getFileRootPathAbsolute() throws ExceptionZZZ {
		String sReturn = "";
		main:{
			String sDirParent = FileEasyZZZ.getDirectoryOfExecutionAsString();
			String sDirRoot = FileEasyZZZ.getFileRootPath();
			
			sReturn = FileEasyZZZ.joinFilePathName(sDirParent, sDirRoot);	
		}//end main:
		return sReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück, der fuer die Ausfuehrung von "weiteren" JAVA-Klassen benoetigt wird.
	 *  Dabei ist der Ausgangspunkt der Projektpfad der gerade ausgeführten Java-Klasse.
	 *  Ist allerdings nur ein relativer Pfad.
	 * @return
	 */
	public static String getFileRootPathForExecutionJava(){
		String sReturn = "";
		main:{
			if(isOnServer()){
				sReturn = "";
			}else{
				sReturn = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_EXECUTIONFOLDER_JAVA;
			}
		}//end main:
		return sReturn;
	}
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Root-Pfad zurück.
	 *  Ist der absolute Pfad.
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getFileRootPathAbsoluteForExecutionJava() throws ExceptionZZZ {
		String sReturn = "";
		main:{
			String sDirParent = FileEasyZZZ.getDirectoryOfExecutionAsString();
			String sDirRoot = FileEasyZZZ.getFileRootPathForExecutionJava();
			
			sReturn = FileEasyZZZ.joinFilePathName(sDirParent, sDirRoot);	
		}//end main:
		return sReturn;
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Pfad zurück, nach Möglichkeit "relativ", ggfs. aber auch "absolut"
	 * @param sFilePathRaw
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getFileUsedPath(String sFilePathRaw) throws ExceptionZZZ{
		return FileEasyZZZ.getFileUsedPath_(sFilePathRaw, true);
	}
	
	/** Gibt für Workspace oder WebServer Anwendungen den korrekten Pfad zurück, nach Möglichkeit "absolut".
	 * @param sFilePathRaw
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String getFileUsedPathAbsolute(String sFilePathRaw) throws ExceptionZZZ{
		return FileEasyZZZ.getFileUsedPath_(sFilePathRaw, true);
	}
	
	private static String getFileUsedPath_(String sFilePathRaw, boolean bAsAbsolute) throws ExceptionZZZ{
		String sReturn=null;
		main:{
			if(bAsAbsolute) {
				IFileEasyPathObjectZZZ objPath = FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathRaw);
				sReturn = objPath.getFilePathTotal();				
			}else {
				IFileEasyPathObjectZZZ objPath = FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathRaw);
				sReturn = objPath.getFilePathTotal();
			}
		}//end main
		return sReturn;		
	}
	
	
	//++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++
	
	/** Returns the root-string of a file.
	 *   if the file-object does not have a path, then null will be returned.
	 *   Format will be: e.g. c:    
	 *   Ergo: No File.Seperator at the end.
	 *   
	* @param file2read
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 08.02.2007 12:28:09
	 */
	public static String getRoot(File file2read) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(file2read==null){
				ExceptionZZZ ez = new ExceptionZZZ("No file object provided.", iERROR_PARAMETER_MISSING, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sPathTotal = file2read.getAbsolutePath();
			if(StringZZZ.isEmpty(sPathTotal)) break main;
			
			sReturn = FileEasyZZZ.getRoot(sPathTotal);			
		}
		return sReturn;
	}
	
	public static String getRoot(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)) break main;
			
			StringTokenizer token = new StringTokenizer(sFilePath, File.separator );
			sReturn = (String) token.nextElement();
			if(! sReturn.endsWith(File.separator)){
				sReturn = sReturn + File.separator;
			}
		}
		return sReturn;
	}
	
	
	public static String getDirectoryOfSystemClassloaderAsExternalString() throws ExceptionZZZ {
		String sReturn = "";
		main:{						
			URL url= ClassLoader.getSystemResource(".");
			if(url==null) {
				String sLog = "unable to receive url object";
				ObjectZZZ.logLineDate(FileEasyZZZ.class, sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_RUNTIME, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				ObjectZZZ.logLineDate(FileEasyZZZ.class, "URL = '"+url.toExternalForm() + "'");				
			}
			sReturn = url.toExternalForm();
		}//end main:
		return sReturn;
	}
	
	public static String getDirectoryOfSystemClassloaderAsString() throws ExceptionZZZ {
		String sReturn = "";
		main:{						
			URL url= ClassLoader.getSystemResource(".");
			if(url==null) {
				String sLog = "unable to receive url object";
				ObjectZZZ.logLineDate(FileEasyZZZ.class, sLog);
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_RUNTIME, FileEasyZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				ObjectZZZ.logLineDate(FileEasyZZZ.class, "URL = '"+url.toString() + "'");				
			}
			sReturn = url.toString();
		}//end main:
		return sReturn;
	}
	
	
	/** Wenn der Code in Eclipse läuft, wird der Pfad zum Projekt zurückgegeben, z.B.:
	 * C:\1fgl\repo\EclipseOxygen_V01\Projekt_Kernel02_JAZKernel\JAZKernel
	 * Wenn der Code z.B. über eine JarDatei und ein startendes Batch - File ausgefürht wird,
	 * wird der Pfad zu dem Ausfürhenden Verzeichnis ausgegeben, z.B.: 
	 * C:\1fgl\client\OVPN
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.10.2020, 09:23:23
	 * @throws ExceptionZZZ 
	 */
	public static File getDirectoryOfExecution() throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sPathTotal = FileEasyZZZ.getDirectoryOfExecutionAsString();			
			objReturn = new File(sPathTotal);
		}
		return objReturn;		
	}
	public static String getDirectoryOfExecutionAsString() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//Das aktuelle Verzeichnis herausfinden, z.B. wenn der Code in einem Eclipse Projekt augeführt wird.
			//Trick: .....
			File fileTemp = new File(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT);
			
			//Allerdings hat der Pfad ggfs. ein \. am Ende. Dies entfernen
			String sPathTotal = fileTemp.getAbsolutePath();
			sReturn = StringZZZ.stripRight(sPathTotal, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + IFileEasyConstantsZZZ.sDIRECTORY_CURRENT);
		}
		return sReturn;	
		
	}
	
	
	
	public static void copyFile(File src, File dest, int bufSize,  boolean force) throws ExceptionZZZ {
		main:{
		try{
	
		    if(dest.exists()) {
		        if(force) {
		            dest.delete();
		        } else {
		            throw new IOException("Cannot overwrite existing file: " + dest.getPath());
		        }
		    }
		    byte[] buffer = new byte[bufSize];
		    int read = 0;
		    InputStream in = null;
		    OutputStream out = null;
		    try {
		        in = new FileInputStream(src);
		        out = new FileOutputStream(dest);
		        while(true) {
		            read = in.read(buffer);
		            if (read == -1) {
		                //-1 bedeutet EOF
		                break;
		            }
		            out.write(buffer, 0, read);
		        }
		    } finally {
		        // Sicherstellen, dass die Streams auch
		        // bei einem throw geschlossen werden.
		        // Falls in null ist, ist out auch null!
		        if (in != null) {
		            //Falls tats�chlich in.close() und out.close()
		            //Exceptions werfen, die jenige von 'out' geworfen wird.
		            try {
		                in.close();
		            }
		            finally {
		                if (out != null) {
		                    out.close();
		                }
		            }
		        }
		    }
	    
		}catch(IOException ioe){
			ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "", ioe);
			throw ez;
		}
		}//End main
	}
	
	public static File createTempFile() throws ExceptionZZZ{
		File objReturn = null;
		main:{
			try {
				objReturn = File.createTempFile("temp", "ZZZ");
				objReturn.deleteOnExit();
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "", ioe);
				throw ez;
			}					
			
		}
		return objReturn;
	}
	
	public static File createTempFile(String sFilePath) throws ExceptionZZZ{
		File objReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sFilePath)) {
					objReturn = FileEasyZZZ.createTempFile();
					break main;
				}
						
				//Falls ein Verzeichnispfad übergeben wird, wird dieser "flach" gemacht. Man kann als temp-Datei keine Verzeichnisse bauen.
				String sFilePathNormedForTempFile = FileEasyZZZ.flattenFilePathToFileName(sFilePath);
				
				ObjectZZZ.logLineDate(FileEasyZZZ.class, "(F) To FileName flattended Path: '" + sFilePathNormedForTempFile + "'");				
				objReturn = File.createTempFile(sFilePathNormedForTempFile, null);
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + ioe.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "",ioe);
				throw ez;
			}
			
		}//end main:
		return objReturn;
	}
	
	public static boolean  createDirectory(String sDirectoryName) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectory(sDirectoryName);
	}
	public static boolean createDirectoryForDirectory(String sFileDirectory) throws ExceptionZZZ {
		File fileDirectory = new File(sFileDirectory);
		return FileEasyZZZ.createDirectoryForDirectory(fileDirectory);
	}
	
	public static boolean  createDirectory(File file) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectory(file);
	}
	public static boolean  createDirectoryForFile(String sFilePath) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectoryForFile(sFilePath);
	}
	public static boolean  createDirectoryForFile(File file) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectoryForFile(file);
	}
	public static boolean  createDirectoryForDirectory(File file) throws ExceptionZZZ{
		return FileEasyZZZ.makeDirectoryForDirectory(file);
	}


	/** Lösche das angegebene Verzeichnis, aber nur das Verzeichnis und nicht ggfs. vorhandenen Inhalt (Dateien und Unterverzeichnisse)
	 * @param objFileIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.11.2020, 09:14:55
	 */
	public static boolean removeDirectory(File objFileIn) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectory(objFileIn,false,false);
	}
	
	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
		 * @param objFileIn
		 * @param bEmptyDirectoryBefore
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
		 */
	public static boolean removeDirectory(File objFileIn, boolean bEmptyDirectoryBefore) throws ExceptionZZZ{
		return FileEasyZZZ.removeDirectory(objFileIn,bEmptyDirectoryBefore,false);	
	}
	/** Entferne das Verzeichnis. Wenn eine Datei übergeben wird, entferne das Elternverzeichnis. 
		 * @param objFileIn
		 * @param bEmptyDirectoryBefore
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.04.2020, 09:49:51
		 */
		public static boolean removeDirectory(File objFileIn, boolean bEmptyDirectoryBefore, boolean bRemoveSubDirectories) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objFileIn==null){
					ExceptionZZZ ez  = new ExceptionZZZ("File Object for DirectoryPath ", iERROR_PARAMETER_MISSING, null, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
				ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA) XXXXXXXXXXXXXX.");
				
				//Merke: Wenn kein Verzeichnis übergeben wurde, dann wird das Verzeichnis eben geholt.
				File objFileDirectory;
				boolean bFileStart = false;
				if(objFileIn.isFile()) {
					bFileStart = true;
					objFileDirectory = objFileIn.getParentFile();
					
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA->X) XXXXXXXXXXXXXX.");
					if(objFileDirectory==null) break main;			
				}else {
					objFileDirectory = objFileIn;
				}
				if(objFileDirectory.exists()==false){
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA->Y) XXXXXXXXXXXXXX.");				   	
					bReturn = true;
					break main;
				}
				if(FileEasyZZZ.isRoot(objFileDirectory)) {
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA->Z) XXXXXXXXXXXXXX.");				   	
					break main;
				}
				
				//REKURSION: Wenn Unterverzeichnisse gelöscht werden sollen. Diese hier holen.
				if(bEmptyDirectoryBefore || bFileStart){
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAA) XXXXXXXXXXXXXX.");
				   	
					//Hole alle Dateien und Verzeichniss			
					File[] objaFile =  objFileDirectory.listFiles();
					if(objaFile.length==0) {
						if(bRemoveSubDirectories) {
							ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAAA) XXXXXXXXXXXXXX.");						   							   
							bReturn = objFileDirectory.delete();//Lösche das aktuelle Verzeichnis, es sollte nun leer sein.
						}else {
							//Dann ist es halt ohne Löschen des Verzeichnisses erfolgreich zuende
							bReturn = true;
						}
					}else {
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAAB) XXXXXXXXXXXXXX.");
					   	
						//Nur löschen, wenn explizit gesagt worden ist "alle Dateien" löschen
						if(bEmptyDirectoryBefore) {
							for(int icount = 0; icount <= objaFile.length - 1; icount++){
								File objFileTemp = objaFile[icount];
								if(objFileTemp.isFile()) {
									ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAABA) XXXXXXXXXXXXXX.");								   									   	
									bReturn = objFileTemp.delete();
								}else {
									if(bRemoveSubDirectories) {
										ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAABB) XXXXXXXXXXXXXX.");									   										   	
										bReturn = FileEasyZZZ.removeDirectory(objFileTemp, bEmptyDirectoryBefore, bRemoveSubDirectories);
									}else {
								}	
							}//if(!bReturn).....
						}		
							
						ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KAABC) XXXXXXXXXXXXXX.");						
						bReturn = objFileDirectory.delete(); //Das Verzeichnis sollte nun leer sein und kann dadurch gel�scht werden
					}else {
							//Das Verzeichnis wird nicht geleert, darf also nicht gelöscht werden.
							//Hier keinen Fehler werfen, da die Eingabeparameter das so wünschen
//							ExceptionZZZ ez = new ExceptionZZZ("DirectoryPath='" + objFileDirectory.getAbsolutePath() + "' is not an empty directory. Call this method with the 'emptyDirectoryBefore=true' argument.", iERROR_PARAMETER_VALUE, null, ReflectCodeZZZ.getMethodCurrentName());
//							throw ez;	
							bReturn = true; //Da das Verzeichnis ja existiert... 							
						}
					}
				}else{			
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA->ZZ) XXXXXXXXXXXXXX.");
				   						
					//Gibt false zurück, wenn z.B. das Directory nicht leer ist.
					bReturn = objFileDirectory.delete();
					
					ObjectZZZ.logLineDate(FileEasyZZZ.class, "(KA->ZZ) bReturn = " + bReturn);
				}
			}
			return bReturn;
		}
	


}//END class
