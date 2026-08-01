package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 19.10.2020, 08:10:14
 * 
 */
public class JarEasyUtilZZZ extends AbstractObjectWithExceptionZZZ implements IJarEasyConstantsZZZ{
	
	
	//Ich lasse mal untenstehend da, aus Dokumentationsgründen.
	//https://stackoverflow.com/questions/51494579/regex-windows-path-validator
	//und zum Testen: https://regex101.com/r/4JY31I/1
	//
	//Das wäre für Windows Dateipfade public static String sDIRECTORY_VALID_REGEX="^(?:[a-z]:)?[\\/\\\\]{0,2}(?:[.\\/\\\\ ](?![.\\/\\\\\\n])|[^<>:\"|?*.\\/\\\\ \\n])+$";
		/* So the regex is as follows:
		
		    ^ - Start of the string.
		    (?:[a-z]:)? - Drive letter and a colon, optional.
		    [\/\\]{0,2} - Either a backslash or a slash, between 0 and 2 times.
		    (?: - Start of the non-capturing group, needed due to the + quantifier after it.
		        [.\/\\ ] - The first alternative.
		        (?![.\/\\\n]) - Negative lookahead - "forbidden" chars.
		    | - Or.
		        [^<>:"|?*.\/\\ \n] - The second alternative.
		    )+ - End of the non-capturing group, may occur multiple times.
		    $ - End of the string.
		
		If you attempt to match each path separately, use only i option.
		
		But if you have multiple paths in separate rows, and match them globally in one go, add also g and m options.
		
		For a working example see https://regex101.com/r/4JY31I/1
		 */
	//Also angepasst an eine JAR Datei
	//* Ohne Laufwerk
	//* Ohne Slashes nach dem Laufwerk
	//* Es gilt: Keine Slashe am Anfang ^(?![/])
	//  Es gilt: Slash am Ende (?:[/]$)
	//	([(?:\/{1}]$)
	//  Das wäre mindestens 2 Slash am Ende (\/{2,}$)
	//  Als Negativer Lookahead: (?![\/{2,}]$)
	
	
	//ABER: Ich habe es nicht geschaft, über die Prüfung von Anfangszeichen und Endzeichen hinaus
	//      im gleichen Ausdruck auf doppelte und mehr "/" zu prüfen.
	//      Dabei u.a. den negativen Lookahead ausprobiert, wie (?![\/{2,}]$)
	
	//TODOGOON: Es müsste auch noch angegeben werden, welche Zeichen gültig sind.
	
	
	private JarEasyUtilZZZ(){
		//Zum Verstecken des Konstruktors
	}
	
	public static File computeTargetDirectoryUsedAsTrunk(String sTargetDirPathRootIn, String sPathInJarIn, String sFilenameInJarIn) throws ExceptionZZZ {
		ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
		ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sPathInJarIn);
		ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFilenameInJarIn);
		return JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
	}
	
	public static File computeTargetDirectoryUsedAsTrunk(ReferenceZZZ<String> strTargetDirPathRoot, ReferenceZZZ<String> strPathInJar, ReferenceZZZ<String> strFilenameInJar) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			//RÜCKGABE DES VERZEICHNISSES ALS FILE-OBJEKT, DAS DANN ERZEUGT WERDEN KANN.
			String sLog;		
			
			//##################
			// A) Pfade und Dateiname in der JAR - Datei
			String sDirPathInJar = strPathInJar.get();
			String sFilenameInJar = strFilenameInJar.get();
			
			//Nun alles erneut ausrechnen und für die Rückgabe vorbereiten.
			String sFilePathTotal;
			if(StringZZZ.isEmpty(sDirPathInJar) && !StringZZZ.isEmpty(sFilenameInJar)) {
				sDirPathInJar = JarEasyUtilZZZ.computeDirectoryFromJarPath(sFilenameInJar);
				sFilenameInJar = JarEasyUtilZZZ.computeFilenameFromJarPath(sFilenameInJar);	
			}else if(!StringZZZ.isEmpty(sDirPathInJar) && StringZZZ.isEmpty(sFilenameInJar)) {
				sDirPathInJar = JarEasyUtilZZZ.toJarDirectoryPath(sDirPathInJar);//Sicherheithalber ggfs. noch ein / anhängen.
				sDirPathInJar = JarEasyUtilZZZ.computeDirectoryFromJarPath(sDirPathInJar);
				sFilenameInJar = JarEasyUtilZZZ.computeFilenameFromJarPath(sDirPathInJar);							
			}
			
			//Nun alles für die Rückgabe vorbereiten.			
			strPathInJar.set(sDirPathInJar);
			strFilenameInJar.set(sFilenameInJar);

			//##################
			//Da ggfs. der Pfad im Dateinamen steht, hier erst einmal alles zu einem String zusammenfassen.
			sFilePathTotal = JarEasyUtilZZZ.joinJarFilePathName(sDirPathInJar, sFilenameInJar);

			// B) (Temporärer) Pfad auf der Platte als Zielverzeichnis für die extrahierten Dateien			
			String sTargetDirPathRoot=strTargetDirPathRoot.get();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Übergebener Zieldateipfad. '" + sTargetDirPathRoot + "'";
		   	System.out.println(sLog);
			
			if(StringZZZ.isEmpty(sTargetDirPathRoot) && StringZZZ.isEmpty(sDirPathInJar)){
				//FALL 1: Kein Zielverzeichnis und Kein Pfad in der Jar Datei angegeben
				sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), "ZZZ");				
			}else if(StringZZZ.isEmpty(sTargetDirPathRoot) && !StringZZZ.isEmpty(sDirPathInJar)) {
				//FALL 2: Kein Zielverzeichnis, aber Pfad in der Jar Datei angegeben
				sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
				sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sDirPathInJar);				
			}else {
				//FALL 3: Zielverzeichnis angegeben
				if(!FileEasyZZZ.isPathAbsolute(sTargetDirPathRoot)) {
					//3A) Als relativer Pfad					
					sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirPathRoot);
					
					if(!StringZZZ.isEmpty(sDirPathInJar)) {
						//sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
						//sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(sTargetDirPathRoot, sDirPathInJar);
					}else {
						sLog = ReflectCodeZZZ.getPositionCurrent()+": FALL: Kein Pfadbestandteil für die Datei innerhalb der JarDatei übergeben. VERMUTLICH SUCHE IN ALLEN VERZEICHNISSEN.";
					   	System.out.println(sLog);					
					}
				}else {
					//3B) Als absoluter Pfad
					sDirPathInJar = JarEasyUtilZZZ.toFilePath(sDirPathInJar);
				   	sTargetDirPathRoot = FileEasyZZZ.joinFilePathName(sTargetDirPathRoot, sDirPathInJar);					
				}
			}
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sTargetDirPathRoot='"+sTargetDirPathRoot+"'";
		   	System.out.println(sLog);
		   	
		   	//Für die Rückgabe vorbereiten.
		   	strTargetDirPathRoot.set(sTargetDirPathRoot);
			objReturn = new File(sTargetDirPathRoot);			
		}
		return objReturn;
	}
	
	public static File createTargetDirectoryRoot(String sTargetDirPathRootIn, String sPathInJarIn, String sFilenameInJarIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
			ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sPathInJarIn);
			ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFilenameInJarIn);
			File fileDirTrunk = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
	
			boolean bSuccess = FileEasyZZZ.replaceDirectory(fileDirTrunk);
			if(bSuccess) {
				objReturn = fileDirTrunk;
				break main;
			}
		}//end main:
		return objReturn;
	}
	
	public static File createTargetDirectoryRoot(ReferenceZZZ<String> strTargetDirPath, ReferenceZZZ<String> strPathInJar, ReferenceZZZ<String> strFilenameInJar) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			File fileDirTrunk = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(strTargetDirPath, strPathInJar, strFilenameInJar);
		
			boolean bSuccess = FileEasyZZZ.replaceDirectory(fileDirTrunk);
			if(bSuccess) {
				objReturn = fileDirTrunk;
				break main;
			}
		}//end main:
		return objReturn;
	}

	/** Prüft, ob die Datei / Ressource einer JAR-Datei liegt.
	 *  Merke: In einer .jar Datei kann kein Zugriff über File-Objekte erfolgen.
	 *  
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static boolean isInJar(Class<?> classObject) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(classObject==null){
				ExceptionZZZ ez = new ExceptionZZZ("No class object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Siehe https://www.rgagnon.com/javadetails/java-0391.html
			String className = classObject.getName().replace('.', '/');
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": className='"+className+"'");
						
			String classJar = classObject.getResource("/" + className + ".class").toString();
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": classJar='"+classJar+"'");
			
			if (classJar.startsWith("jar:")) {
			    System.out.println("*** running from jar!");
				 bReturn=true;
			}else{
				 System.out.println("*** NOT running from jar!");
			}
		}//end main:
		return bReturn;
		
		   //Alternativer Ansatz: https://www.edureka.co/community/5035/retrieving-the-path-of-a-running-jar-file			   
		   //return new File(AbcClass.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
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
			bReturn = isInJar(JarEasyZZZ.class);
		}
		return bReturn;
	}
	
	public static boolean isInSameJar(Class classObject, File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			if(!JarEasyUtilZZZ.isInJar(classObject)) {
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": Not running from jar file.";
			    System.out.println(sLog);
				break main;			
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile: '" + objFileAsJar.getAbsolutePath() + "'", iERROR_PARAMETER_VALUE, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 File objFileJarCodeLocation = JarEasyUtilZZZ.getCodeLocationJar();
			 if(objFileJarCodeLocation==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Unable to get CodeLocation for running jar", iERROR_RUNTIME, JarEasyUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			 }
			 
			 String sLog = ReflectCodeZZZ.getPositionCurrent()+": A) File from JarEasyUtilZZZ.getCodeLocationUsed() = '" + objFileJarCodeLocation.getAbsolutePath() + "'";
			 System.out.println(sLog);

			  sLog = ReflectCodeZZZ.getPositionCurrent()+": B) File provided as argument, to be tested = '" + objFileAsJar.getAbsolutePath() + "'";
			  System.out.println(sLog);
			  
			 bReturn = objFileJarCodeLocation.equals(objFileAsJar);
		}//end main;
		return bReturn;
	}
	
	public static boolean isInSameJarStatic(File objFileAsJar) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objFileAsJar==null)break main;
			
			bReturn = isInSameJar(JarEasyUtilZZZ.class, objFileAsJar);			
		}//end main;
		return bReturn;
	}

	public static File getCodeLocationJar() throws ExceptionZZZ{
		File objReturn=null;
		main:{
			/*
			https://stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file						
			 */
			
			//1.Schritt die URL holen
			URL url = JarEasyHelperZZZ.getLocation(JarEasyUtilZZZ.class);
			if(url==null)break main;
			
			//Step 2: URL to File	
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": C1) URL as location '"+url.toString()+"'";
			System.out.println(sLog);
			File objTemp = JarEasyHelperZZZ.urlToFile(url);
			if(objTemp==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File is NULL";
				System.out.println(sLog);
				break main;
			}
			if(!objTemp.exists()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) File does not exist '" + objTemp.getAbsolutePath() + "'";
				System.out.println(sLog);
				break main;
			}
			if(!objTemp.isFile()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": C2) Fileobject is not a file '" + objTemp.getAbsolutePath() + "'";
				System.out.println(sLog);
				break main;
			}
			objReturn = objTemp;
		}
		return objReturn;
	}
	
	/** Versucht erst die CodeLocationJar zu holen. Wenn das nicht hilft, den aktullen classpath bzw. Eclipse Workspace
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.10.2020, 08:54:58
	 */
	public static File getCodeLocationUsed() throws ExceptionZZZ{
		File objReturn=null;
		main:{			
			objReturn = JarEasyUtilZZZ.getCodeLocationJar();
			if(objReturn==null) {
				objReturn = FileEasyZZZ.getDirectoryOfExecution();
			}
		}
		return objReturn;		
	}

	

	public static File toFile(JarFile objJar) {
		File objReturn = null;
		main:{
			if(objJar==null)break main;
			
			String sFileAsJar = objJar.getName();
			objReturn = new File (sFileAsJar);
		}//end main:
		return objReturn;
	}

	/**Innerhalb der JAR-Datei wird immer mit / gearbeitet dies wieder rückgängig machen und ggfs. den Windows Trenner verwenden.
	 * @param sJarPath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:08
	 * @throws ExceptionZZZ 
	 */
	public static String toFilePath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sJarPath)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source JarPath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sReturn = StringZZZ.replace(sJarPath, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR_UNIX, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
			
			//UND: Abschliessend gibt es bei Verzeichnissen ein \ ... aber NUR 1x
			sReturn=StringZZZ.stripFileSeparatorsRight(sReturn);
			
		}
		return sReturn;
		
	}

	/** Innerhalb der JAR-Datei wird immer mit / gearbeitet.
	 *  Also einen Dateipfad dahingehend normieren.
	 *  
	 *  Verzeichnisse sind mit / abgeschlossen.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:31
	 * @throws ExceptionZZZ 
	 */
	public static String toJarDirectoryPath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = JarEasyUtilZZZ.toJarFilePath(sFilePath);
			
			//UND: Abschliessend gibt es bei Verzeichnissen ein / ... aber NUR 1x
			sReturn=StringZZZ.stripRight(sReturn, "/");
			sReturn=sReturn+"/";
		}
		return sReturn;
	}

	public static JarFile toJarFile(File objFileAsJar) throws ExceptionZZZ{
		JarFile objReturn = null;
		main:{
			if(objFileAsJar==null)break main;
			if(!(FileEasyZZZ.isJar(objFileAsJar)||FileEasyZZZ.isZip(objFileAsJar))){
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile or Zip File.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			try {
				objReturn = new JarFile(objFileAsJar);
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + e.getMessage(),ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
			}
		}//end main:
		return objReturn;
	}

	/** Innerhalb der JAR-Datei wird immer mit / gearbeitet.
	 *  Also einen Dateipfad dahingehend normieren.
	 * @param sFilePath
	 * @return
	 * @author Fritz Lindhauer, 19.07.2020, 07:35:31
	 * @throws ExceptionZZZ 
	 */
	public static String toJarFilePath(String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)){
				//ODER: ROOT DER JAR DATEI, WIE?
				ExceptionZZZ ez = new ExceptionZZZ("No source FilePath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//UND: Ggfs. vorhandene Separatoren links entfernen, z.B. ".\\"
			sReturn = StringZZZ.stripFileSeparatorsLeft(sFilePath);
			
			//UND: Abschliessend gibt es NUR bei Verzeichnissen ein / ... aber NUR 1x
			sReturn = StringZZZ.stripFileSeparatorsRight(sReturn);
			
			//Innerhalb der JAR-Datei wird immer mit / gearbeitet.
			sReturn = StringZZZ.replace(sReturn, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, "/");
		}
		return sReturn;
	}

	public static JarEntry getEntryForFile(JarFile jar, String sPath) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getEntry_(jar, sPath, false);
		}//end main:
		return objReturn;
	}
	
	public static JarEntry getEntryForDirectory(JarFile jar, String sPath) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			objReturn = JarEasyUtilZZZ.getEntry_(jar, sPath, true);
		}//end main:
		return objReturn;
	}
	
	private static JarEntry getEntry_(JarFile jar, String sPath, boolean bToDirectory) throws ExceptionZZZ {
		JarEntry objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(jar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			String sLog = null;			
			String sPathInJar = null;
			if(bToDirectory) {
				sPathInJar = toJarDirectoryPath(sPath);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for Directory '" + sPathInJar + "'";				
				System.out.println(sLog);
			}else{
				sPathInJar = toJarFilePath(sPath); 
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) Searching for File '" + sPathInJar + "'";				
				System.out.println(sLog);
			}
			
				
			objReturn = jar.getJarEntry(sPathInJar);
				  
		}//end main:
		return objReturn;
	}
	
	/* Es wird der Pfad aufgeteilt und zurückgegeben.
	 * Da Java nur ein CALL_BY_VALUE machen kann, weden hier für die eingefüllten Werte Referenz-Objekte verwendet.
	 */
	public static void splitJarFilePathToDirectoryAndName(String sJarPath, ReferenceZZZ<String> strDirectory, ReferenceZZZ<String> strFileName) throws ExceptionZZZ{		
		main:{
			if(StringZZZ.isEmpty(sJarPath)){
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sDirectory=null;
			String sFileName=null;
			if(JarEasyUtilZZZ.isDirectory(sJarPath)){
				sDirectory = sJarPath;
				sFileName = "";
			}else {
				sDirectory = StringZZZ.leftback(sJarPath, IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
				if(!StringZZZ.isEmpty(sDirectory)) {
					sDirectory=sDirectory + IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR;
					sFileName = StringZZZ.right(sJarPath, sDirectory);
				}else {
					sDirectory = "";
					sFileName = sJarPath;
				}			
			}
			
			//#### Die Rückgabewerte
			strDirectory.set(sDirectory);
			strFileName.set(sFileName);		
		}//END main:		
	}
	
	public static boolean isDirectory(String sJarPath) throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) break main;
			
			if(sJarPath.endsWith(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) bReturn = true;			
		}//end main:
		return bReturn;		
	}
	
	public static String computeDirectoryFromJarPath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	
	    	if(JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);		    	
	    		sReturn = strDirectory.get();
	    		break main;
	    	}
	    	
	    	if(JarEasyUtilZZZ.isJarPathFileValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);
	    		sReturn = strDirectory.get();
	    		break main;
	    	}
	    	
		}
		return sReturn;
	}
	public static String computeFilenameFromJarPath(String sJarPath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			ReferenceZZZ<String> strDirectory=new ReferenceZZZ<String>("");
	    	ReferenceZZZ<String> strFileName=new ReferenceZZZ<String>("");
	    	
	    	if(JarEasyUtilZZZ.isJarPathFileValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);	
	    		sReturn = strFileName.get();
	    		if(!StringZZZ.isEmpty(sReturn)) break main;
	    		
	    		
	    		
	    	}
	    	
	    	if(JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath)) {
	    		JarEasyUtilZZZ.splitJarFilePathToDirectoryAndName(sJarPath, strDirectory, strFileName);
	    		sReturn = strFileName.get();
	    		if(!StringZZZ.isEmpty(sReturn)) break main;	    		
	    	}
	    	
	    	
	    	//Wenn jetzt noch nicht gefunden wurde, dann als Dateinamen den rechten Teil vom Separator nehmen.
	    	//Das ist z.B. der Fall wenn nach einem reinen Dateinamen gesucht wird. Also gar kein Pfad angegeben wurde.
	    	if(StringZZZ.startsWithIgnoreCase(sJarPath, IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) {
	    		sReturn = StringZZZ.right(sJarPath, IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
	    	}	    	
    		break main;
	    	
	    	
		}
		return sReturn;
	}
	
	public static boolean isJarPathValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
//			if(StringZZZ.contains(sJarPath, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) {
//				bReturn = false;
//				break main;
//			}
			
			//Ansonsten eben nicht wie Dateipfade
			boolean bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
			if(bErg) {
				bReturn = true;
				break main;
			}
			
			bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
			if(bErg) {
				bReturn = true;
				break main;
			}
			
		}
		return bReturn;
	}
	
	public static boolean isJarPathDirectoryValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Da es wohl zu kompliziert ist alles in einem Ausdruck zu erfassen:
			//0. Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
			if(StringZZZ.contains(sJarPath, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) break main;
						
			//1. prüfe auf doppelte / ab. Die sind auch nicht erlaubt.
			boolean bErg = StringZZZ.hasConsecutiveDuplicateCharacter(sJarPath, '/');
			if(bErg) break main;
			
			//2. Nun doch noch den regulären Ausdruck verwenden
			Pattern p = Pattern.compile(IJarEasyConstantsZZZ.sDIRECTORY_VALID_REGEX); 
			 Matcher m =p.matcher( sJarPath ); 
			 boolean bMatched  =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
			 if(bMatched) {
				 bReturn = true;
				 break main;
			 }			 			 					 
		}
		return bReturn;
	}
	
	public static boolean isJarPathFileValid(String sJarPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sJarPath)) {
				ExceptionZZZ ez  = new ExceptionZZZ("sJarPath", iERROR_PARAMETER_MISSING, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Da es wohl zu kompliziert ist alles in einem Ausdruck zu erfassen:
			//0. Jar-Verzeichniseinträge dürfen keinen "Backslash" haben.
			if(StringZZZ.contains(sJarPath, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR_WINDOWS)) break main;
			
			//1. prüfe auf doppelte / ab. Die sind auch nicht erlaubt.
			boolean bErg = StringZZZ.hasConsecutiveDuplicateCharacter(sJarPath, '/');
			if(bErg) break main;
			
			//Ansonsten eben nicht wie Dateipfade			 
			 Pattern p = Pattern.compile(IJarEasyConstantsZZZ.sFILE_VALID_REGEX); 
			 Matcher m =p.matcher( sJarPath ); 
			 boolean bMatched  =  m.find(); //Es geht nicht darum den ganzen Ausdruck, sondern nur einen teil zu finden: m.matches(); 	
			 if(bMatched) {
				 bReturn = true;
				 break main;
			 }						 
		}
		return bReturn;
	}
	
	public static String joinJarFilePathName(String sDirectoryPath, String sFilePath) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sDirectoryPathNormed = "";
			if(!StringZZZ.isEmpty(sDirectoryPath)) {
				sDirectoryPathNormed = JarEasyUtilZZZ.toFilePath(sDirectoryPath);
			}
			String sFilePathNormed = "";
			if(!StringZZZ.isEmpty(sFilePath)){
				sFilePathNormed = JarEasyUtilZZZ.toFilePath(sFilePath);
			}
			
			//!!! Achtung ohne die spezielle Jar-Variante würde ein src vorangestellt... aber in der JAR-Datei ist das nicht so!!!
			sReturn = FileEasyZZZ.joinFilePathNameForUrl(sDirectoryPathNormed, sFilePathNormed);			
		}//end main:
		return sReturn;		
	}

	public static File createFileDummy(ZipEntry ze, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog;
			if(ze==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= EnvironmentZZZ.getHostDirectoryTemp();
			}else {
				sTargetDirectoryPath= EnvironmentZZZ.getHostDirectoryTemp();
				sTargetDirectoryPath = FileEasyZZZ.joinFilePathName(sTargetDirectoryPath, sTargetDirectoryPathIn);
			}
			
			String sEntryName = ze.getName();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sEntryName='" + sEntryName + "'.";
		    System.out.println(sLog);
			
		    String sFilePath = toFilePath(sEntryName);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
		    System.out.println(sLog);
		    
		    String sFilePathTotal = FileEasyZZZ.joinFilePathName(sTargetDirectoryPath, sFilePath);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePathTotal='" + sFilePathTotal + "'.";
		    System.out.println(sLog);
		    
			//Nun aus dem ZipEntry ein File Objekt machen 
			//https://www.rgagnon.com/javadetails/java-0429.html
			objReturn = new File(sFilePathTotal);
			
		}
		return objReturn;
	}
	
	public static File createDirectoryDummy(ZipEntry ze, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog;
			if(ze==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= EnvironmentZZZ.getHostDirectoryTemp();
			}else {
				sTargetDirectoryPath= EnvironmentZZZ.getHostDirectoryTemp();
				sTargetDirectoryPath = FileEasyZZZ.joinFilePathName(sTargetDirectoryPath, sTargetDirectoryPathIn);
			}
			
			String sEntryName;
			if(ze.isDirectory()) {
				sEntryName = ze.getName();
			}else{
				sEntryName = ze.getName();
				sEntryName = JarEasyUtilZZZ.computeDirectoryFromJarPath(sEntryName);
			}
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sEntryName='" + sEntryName + "'.";
		    System.out.println(sLog);
			
		    String sFilePath = toFilePath(sEntryName);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
		    System.out.println(sLog);
		    
		    String sFilePathTotal = FileEasyZZZ.joinFilePathName(sTargetDirectoryPath, sFilePath);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePathTotal='" + sFilePathTotal + "'.";
		    System.out.println(sLog);
		    
			//Nun aus dem ZipEntry ein File Objekt machen 
			//https://www.rgagnon.com/javadetails/java-0429.html
			objReturn = new File(sFilePathTotal);
			
		}
		return objReturn;
	}
}


