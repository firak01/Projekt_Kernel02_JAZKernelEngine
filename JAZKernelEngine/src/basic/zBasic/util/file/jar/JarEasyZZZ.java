package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileArrayEasyZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.file.filter.IFilenameFilterExpansionUserZZZ;
import basic.zBasic.util.file.jar.filter.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.filter.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.zip.FileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryWithContentPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFilenamePartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEasyZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zBasic.util.machine.EnvironmentZZZ;

/**
 * Merke; In den JAREasy-Klassen gibt es die Konvention: 
		  peek => nix erzeugen
		  search => im Temp erzeugen 
		  find   => im Temp erzeugen, Im Gegensatz zu den Search-Methoden wird hier immer ein 
		  extract   => TODO GOON: Sollte wohl die Dateien in einem anderen Verzeichnis erstellen, als dem Temp Verzeichnis.
		                          Ausser es heisst extract to Temp....
 * 
 * @author Fritz Lindhauer, 24.12.2020, 09:51:33
 * 
 */
public class JarEasyZZZ implements IConstantZZZ, IResourceHandlingObjectZZZ{
	public static String sDIRECTORY_CURRENT = IFileEasyConstantsZZZ.sDIRECTORY_CURRENT;
	public static String sDIRECTORY_PARENT = IFileEasyConstantsZZZ.sDIRECTORY_PARENT;
	public static String sDIRECTORY_SEPARATOR = IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR;
	public static char cDIRECTORY_SEPARATOR = IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR;
	public static String sDIRECTORY_RESSOURCE_ROOT = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER; //Merke: Wenn man ein Projekt in eine JAR packt, hängt alles unter dem src Ordner
	
		
	/** Merke: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann wird lediglich die JAR Datei zurückgegeben.
	 *         Verwende für die Behandlung eines ganzen Verzeichnisses (oder auch nur des Verzeichnisses) 
	 *         die Methode, die ein Verzeichnis im TEMP-Ordner des HOST Rechners erstellt. 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static ZipEntry[] extractDirectoriesFromJarAsTrunkEntries(JarFile objJarFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
				if(jarEntryOfDirectory==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sTargetDirectoryPath;
				if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
					sTargetDirectoryPath= ".";
				}else {
					sTargetDirectoryPath = sTargetDirectoryPathIn;
				}
				
				String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
				sDirectoryFilePathInJar = FileEasyZZZ.getParent(sDirectoryFilePathInJar);
			
				objaReturn = JarEasyZZZ.extractDirectoriesFromJarAsTrunkEntries(objJarFile, sDirectoryFilePathInJar);
		}//end main:
		return objaReturn;
	}
	
	/** Merke: In der Jar Datei gibt es keine reinen Verzeichniseinträge, wenn Dateien darin enthalten sind.
	 *         Dieser wird "künstlich" erzeugt.
	 *         Es gibt auch nicht für jedes Verzeichnis einen Eintrag,
	 *         sondern nur für die "letzten" Verzeichnisse des Pfades und für die "künstlich" erzeugten Verzeichnisse
	 *         ohne Dateien.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static ZipEntry[] extractDirectoriesFromJarAsTrunkEntries(JarFile objJarFile, String sSourceDirectoryPath) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
			try{
				if(StringZZZ.isEmpty(sSourceDirectoryPath)){
					ExceptionZZZ ez = new ExceptionZZZ("No source filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
			
				//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
				String archiveName = objJarFile.getName();				
				sSourceDirectoryPath = JarEasyUtilZZZ.toJarFilePath(sSourceDirectoryPath);				
				IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceDirectoryPath);
				//IFileDirectoryPartFilterZipZZZ objFilterFilePathPart = objFilterDirectoryInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterDirectoryInJar );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<ZipEntry>objaZipEntry = new ArrayList<ZipEntry>();
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					if(zeTemp.isDirectory()) {
						boolean bErg = ZipEasyZZZ.containsByName(objaZipEntry, zeTemp);
						if(!bErg){//!!!Aber nur 1x erstellen, sonst bekommt man redundante Einträge, insbesondere wenn die JAR-Datei "mit Verzeichnissen" erstellt wurde.
							objaZipEntry.add(zeTemp);
						}
					}else {
						//Erstelle ein neues, dummy-Objekt für einen ZipEntry, das es so nicht im File-Objekt gibt.
						String zeName = zeTemp.getName();
						String zeNameParent = JarEasyUtilZZZ.computeDirectoryFromJarPath(zeName);
						ZipEntry zeNew = new ZipEntry(zeNameParent);
						boolean bErg = ZipEasyZZZ.containsByName(objaZipEntry, zeNew);
						if(!bErg){//!!!Aber nur 1x erstellen, sonst bekommt man redundante Einträge
							objaZipEntry.add(zeNew);
						}
					}
				}								
				
				objaReturn = ArrayListUtilZZZ.toZipEntryArray(objaZipEntry); 
				
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	
	
	
	
		
	
	
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractDirectoryToTemp(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{		
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, true);		
		}//end main:
		return objReturn;
	}
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractDirectoryToTemp(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn=null;
		main:{						
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objReturn;
	}
	
	public static File extractDirectoryToTemp(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;		
		main:{
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objReturn;
	}
	
	private static File extractDirectoryToTemp_(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			File[] objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
			if(objaReturn==null) break main;
			
			//Problem: In dem Array sind nun alle extrahierten Dateien. Aber nicht das Verzeichnis selbst.
			//Daher parent zurückgeben
			objReturn = objaReturn[0].getParentFile();	
		}//end main:
		return objReturn;
	}
	
	
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] extractDirectoryToTemps(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{						
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, true);
											
			}//end main:
			return objaReturn;
	}
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{	
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, bWithFiles);			
		}//end main:
		return objaReturn;
	}
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{	
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
									
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);
		}//end main:
		return objaReturn;
	}
	
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] extractDirectoryToTemps(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{								
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
															
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);
		    
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
				
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
File[] objaReturn = null;
		
		main:{
			try {
				if(objJarAsFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
				objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, true);
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	
	public static File[] extractDirectoryToTemps(File objFileAsJar, String sDirectoryFilePathInJarIn, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{									
			objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJarIn, sTargetDirectoryFilepathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	public static File[] extractDirectoryToTemps(JarFile objJarAsFile, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn = null;
		
		main:{
			try {
				if(objJarAsFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);
				objaReturn = JarEasyZZZ.extractDirectoryToTemps_(objFileAsJar, sDirectoryFilePathInJar,sTargetDirectoryFilepathIn, bWithFiles);
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	private static File[] extractDirectoryToTemps_(File objFileAsJar, String sDirectoryFilePathInJar, String sTargetDirectoryFilepathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn = null;
		
		main:{
			if(objFileAsJar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)) {
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String sTargetDirectoryFilepath;
			if(sTargetDirectoryFilepathIn==null)
				sTargetDirectoryFilepath="";  //TEMP VERZEICHNIS???
			else {
				//Links und rechts ggfs. übergebenen Trennzeichen entfernen. So normiert kann man gut weiterarbeiten.				
				sTargetDirectoryFilepath=StringZZZ.stripFileSeparators(sTargetDirectoryFilepathIn);									
			}
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) sTargetDirectoryFilepath='" + sTargetDirectoryFilepath +"'.";
		   	System.out.println(sLog);
			
			//+++ Verwende nun die Resourcen - Behandlung, damit das Verzeichnis auch tatsächlich erstellt wird ++++++++++++
			try {			
			    if(bWithFiles) {
			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) With Files Case.";
				   	System.out.println(sLog);
				   	
					//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
					IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sDirectoryFilePathInJar, null);
					objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryFilepath);					
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Without Files Case.";
				   	System.out.println(sLog);
				   	
					//Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
					IFileDirectoryPartFilterZipUserZZZ objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sDirectoryFilePathInJar);										
					objaReturn = JarEasyZZZ.findDirectoryInJar(objFileAsJar, objFilterDirInJar, sTargetDirectoryFilepath, false);
				}
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractEntryToTemp(File objFileAsJar, JarEntry jarEntryOfDirectory, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{			
			if(jarEntryOfDirectory==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntryOfDirectory.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sDirectoryFilePathInJar = jarEntryOfDirectory.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);			
			
			objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);
		}//end main:
		return objReturn;
	}
	
	
	
	/**
	 * Merke: Wenn das nur ein Verzeichnis ist, dann würde die JAR Datei selbst zurückgegeben.
			  In dem Fall ist es besser die Variante für die Arbeit mit dem Temp-Verzeichnis zu wählen.
	 * 
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractEntryToTemp(JarFile objJarAsFile, JarEntry jarEntry, String sTargetDirectoryFilepathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{			
			if(objJarAsFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			if(jarEntry==null){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "No JarEntry provided", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(!jarEntry.isDirectory()) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Provided JarEntry is not a directory", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
						
			File objFileAsJar = JarEasyUtilZZZ.toFile(objJarAsFile);			
			String sDirectoryFilePathInJar = jarEntry.getName();
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": DirectoryFilePathInJar='" + sDirectoryFilePathInJar + "'";
		    System.out.println(sLog);	
					   
		    if(jarEntry.isDirectory()) {
		    	objReturn = JarEasyZZZ.extractDirectoryToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn, true);
		    }else {
		    	objReturn = JarEasyZZZ.extractFileToTemp_(objFileAsJar, sDirectoryFilePathInJar, sTargetDirectoryFilepathIn);
		    }
			
		}//end main:
		return objReturn;
	}
	
	public static ZipEntry[] extractFilesFromJarAsTrunkZipEntries(JarFile objJarFile, String sSourceDirectoryPath) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sSourceDirectoryPath,null);
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFileInJar );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<ZipEntry>objaEntryTemp = new ArrayList<ZipEntry>();
				while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						objaEntryTemp.add(zeTemp);
				}
				objaReturn = ArrayListUtilZZZ.toZipEntryArray(objaEntryTemp);				
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }	    
			}//end main:
			return objaReturn;
	}
	
	public static boolean extractFromJarAsTrunk(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn,ReferenceZZZ<HashMap<ZipEntry,File>>hashmapTrunk) throws ExceptionZZZ {	
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sTargetDirectoryPath;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)){
				sTargetDirectoryPath= ".";
			}else {
				sTargetDirectoryPath = sTargetDirectoryPathIn;
			}
			
			//Merke: Weil es "trunk" ist, wird das Verzeichnis HIER nicht erstellt, sondern erst beim Speichern des Trunk-Inhalts.
			//Merke: Hier gibt es zudem eine "Call by Reference" Problematik, darum über ein Zwischenobjekt arbeiten und dort hinein die Objekte ablegen.
			HashMap<ZipEntry,File> hmTrunk = hashmapTrunk.get();
			hmTrunk = JarEasyZZZ.extractFromJarAsTrunk(objJarFile, sSourceDirectoryPath, sTargetDirectoryPath);	//DAS DAUERT LANGE					
			hashmapTrunk.set(hmTrunk);
			
			/* Merke: Man kann das ZipEntry-Objekt nicht so konstruieren, dass man es für die HashMap als key verwenden kann.
			sSourceDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sSourceDirectoryPath);
			ZipEntry ze = new ZipEntry(sSourceDirectoryPath);
			File objReturn = hashmapTrunk.get().get(ze); //Aber: Das sollte damit nicht automatisch erstellt sein.
			System.out.println("T"); */

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static HashMap<ZipEntry,File>extractFromJarAsTrunk(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ{
		HashMap<ZipEntry,File>hmReturn = new HashMap<ZipEntry,File>();
		main:{
			
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				//IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceDirectoryPath);
				//FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				//JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );//Das dauert laaange
				
				IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceDirectoryPath);
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterDirectoryInJar );//Das dauert laaange
								
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();				
				while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
												
						File objFileTemp = JarEasyUtilZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);
						
						//Das Ergebnis in die Trunk - HashMap packen
						hmReturn.put(zeTemp, objFileTemp);
				}
								
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
			
		}//end main:
		return hmReturn;
	}
	
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  
	* Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden dabei nicht angegelgt, sondern kommen in den Dateinamen
	* Z.B. template_template_server_starter.txt35232264199775134.tmp ist dann die Datei template_server_starter.txt im template Verzeichnis.
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	* @throws ExceptionZZZ
	* @author Fritz Lindhauer, 25.11.2020, 08:38:17
	**/
	public static File extractFileFromJarAsTemp(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
			File objReturn=null;
			main:{
				objReturn = JarEasyZZZ.extractFileFromJarAsTemp_(objJarFile, sFilePath);
			}//end main:
			return objReturn;
		}
	
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  
	* Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden dabei nicht angegelgt, sondern kommen in den Dateinamen
	* Z.B. template_template_server_starter.txt35232264199775134.tmp ist dann die Datei template_server_starter.txt im template Verzeichnis.
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	* @throws ExceptionZZZ
	* @author Fritz Lindhauer, 25.11.2020, 08:38:17
	**/
	public static File extractFileFromJarAsTemp(JarFile objJarFile, JarEntry entry) throws ExceptionZZZ {
			File objReturn=null;
			main:{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(entry==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				String sFilePath = entry.getName();
				String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Extracting Entry '" + sFilePath + "'";
				System.out.println(sLog);
				
				sFilePath = JarEasyUtilZZZ.toFilePath(sFilePath);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) Entry to FilePath '" + sFilePath + "'";
				System.out.println(sLog);
				
				objReturn = JarEasyZZZ.extractFileFromJarAsTemp_(objJarFile, sFilePath);
				
			}//end main:
			return objReturn;
		}
	
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  
	* Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden dabei nicht angegelegt, sondern kommen in den Dateinamen
	* Z.B. template_template_server_starter.txt35232264199775134.tmp ist dann die Datei template_server_starter.txt im template Verzeichnis.
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	* @throws ExceptionZZZ
	* @author Fritz Lindhauer, 25.11.2020, 08:38:17
	**/
	private static File extractFileFromJarAsTemp_(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
			if(StringZZZ.isEmpty(sFilePath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			try {
				
				if(JarEasyUtilZZZ.isInJarStatic()) {
					objReturn = JarEasyZZZ.extractFileFromJarThisAsTemp_(objJarFile, sFilePath);	
					//oder vielleicht besser?    objReturn = JarEasyInCurrentJarZZZ.extractFileAsTemp(sFilePath);
				}else {
					try {
						JarEntry entry = objJarFile.getJarEntry(sFilePath);
				        if (entry == null) {
				            throw new IOException("Eintrag '" + sFilePath + "' wurde in der Jar-Datei nicht gefunden.");
				        }
				
				        // InputStream aus der Jar-Datei öffnen
				        InputStream is = objJarFile.getInputStream(entry);
				        if (is == null) {
				            throw new IOException("Konnte InputStream für '" + sFilePath + "' nicht öffnen.");
				        }
				
				        // Temporäre Datei mit gleichem Namen anlegen
				        objReturn = FileEasyZZZ.createTempFile(sFilePath);
				
				        FileOutputStream fos = null;
				        try {
				            fos = new FileOutputStream(objReturn);
				            byte[] buffer = new byte[4096];
				            int bytesRead;
				            while ((bytesRead = is.read(buffer)) != -1) {
				                fos.write(buffer, 0, bytesRead);
				            }
				        } finally {
				            if (fos != null) {
				                try { fos.close(); } catch (IOException ignore) {}
				            }
				            try { is.close(); } catch (IOException ignore) {}
				        }
				        
			        
						}catch(IOException ioe) {
							ExceptionZZZ ez  = new ExceptionZZZ("An error happened. IOException: " + ioe.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}//end if
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened. Exception: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }	
			}//end main:
			return objReturn;
		}
	
		
	/**
	*  This method is responsible for extracting resource files from within the .jar to an temporarily existing file.
	*  NOT a file peristed in the temp - Directory !!!
	*  
	* Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden dabei nicht angegelegt, sondern kommen in den Dateinamen
	* Z.B. template_template_server_starter.txt35232264199775134.tmp ist dann die Datei template_server_starter.txt im template Verzeichnis.
	*  @param filePath The filepath is the directory within the .jar from which to extract the file.
	*  @return A file object to the extracted file
	* @throws ExceptionZZZ
	* @author Fritz Lindhauer, 25.11.2020, 08:38:17
	**/
	private static File extractFileFromJarThisAsTemp_(JarFile objJarFile, String sFilePath) throws ExceptionZZZ {
		//IDEE: Innerhalb der gleichen Jar - Datei dies über den Classpath und ClassLoader machen.
		
		
			File objReturn=null;
			main:{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(StringZZZ.isEmpty(sFilePath)){
					ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}

				try{					
			        File f = FileEasyZZZ.createTempFile(sFilePath); 
			        FileOutputStream resourceOS = new FileOutputStream(f);
			        byte[] byteArray = new byte[1024];
			        int i;

			      //Der Pfad muss so sein, wie in der JAR - Datei abgelegt. Also mit Slashes, z.B.: InputStream classIS = getClass().getClassLoader().getResourceAsStream("Resources/"+filePath);
			        String sFilePathInJar = JarEasyUtilZZZ.toJarFilePath(sFilePath);
			        String sLog = ReflectCodeZZZ.getPositionCurrent()+": (G) Trying to create InputStream for : '" + sFilePathInJar + "'";
					System.out.println(sLog);
					
			        InputStream classIS = JarEasyZZZ.class.getClassLoader().getResourceAsStream("/" + sFilePathInJar);
			//While the input stream has bytes
			        while ((i = classIS.read(byteArray)) > 0) 
			        {
			//Write the bytes to the output stream
			            resourceOS.write(byteArray, 0, i);
			        }
			//Close streams to prevent errors
			        if(classIS!=null) classIS.close();
			        if(resourceOS!=null) resourceOS.close();
			        if(f==null) {
			        	break main;		    
			        }else {
			        	objReturn = f;
			        }
				}catch (Exception e){
			    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }	    
				}//end main:
				return objReturn;				
		}
	
	
			
	
	
	
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File extractFileFromJarAsFileDummy(JarFile objJarFile, String sSourcePath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				JarEntry entry = JarEasyUtilZZZ.getEntryForFile(objJarFile, sSourcePath);
				if(entry==null){
				  	String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR PATH: '" + sSourcePath +"'";
				   	System.out.println(sLog);	
				   	break main;
				}						
								
				objReturn = JarEasyUtilZZZ.createFileDummy(entry, sTargetDirectoryPathIn);
								
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	/** 
	 Merke1: Wenn ein Verzeichnis aus der JAR Datei zu extrahieren ist, dann werden lediglich die File Objekt zurückgegeben.
             Die Datei selbst - oder das Verzeichnis - wird nicht erzeugt.
	 Merke2: Mit diesen File-Objekten kann man eigentlich nur die Existenz in der JAR-Datei prüfen.
		     Einen Input Stream zu bekommen ist die Voraussetzung für das Erstellen der Datei als Kopie.
		     Diesen kann man aus dem ZipEntry bekommen....
		     
			//BEISPIEL, aus Dokugründen stehen lassen;
			//ZipFile zf = objJarInfo.getZipFile();
			//while(itEntryName.hasNext()) {
			//	String sKey = itEntryName.next();
			//	ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
			//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)														
			//InputStream is = zf.getInputStream(zeTemp);
			//String sKeyNormed = StringZZZ.replace(sKey, "/", FileEasyZZZ.sDIRECTORY_SEPARATOR);
			//String sPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sApplicationKeyAsSubDirectoryTemp + FileEasyZZZ.sDIRECTORY_SEPARATOR + sKeyNormed);			
			//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
			//FileEasyZZZ.removeFile(sPath);								
			//Files.copy(is, Paths.get(sPath))
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47	
	 */
	public static ZipEntry[] extractFromJarAsTrunkZipEntries(JarFile objJarFile, String sSourceFilePath, String sTargetDirectoryPathIn, boolean bExtractFiles) throws ExceptionZZZ {
		ZipEntry[] objaReturn=null;
		main:{
			
			if(bExtractFiles) {
				objaReturn = JarEasyZZZ.extractFilesFromJarAsTrunkZipEntries(objJarFile, sSourceFilePath);				
			}else {				
				//Merke: In der Jar Datei gibt es keine reinen Verzeichniseinträge, wenn Dateien darin enthalten sind.
				//Man kann nur leere Verzeichnisse explizit zusätzlich holen, um so diese auch zu finden.
				
				//ABER: Wenn überhaupt keine Verzeichnisse darin enthalten sind, dann hilft alles nichts.
				//Darum: Bei der Jar-Erzeugung "add directories" auswählen.
				objaReturn = JarEasyZZZ.extractDirectoriesFromJarAsTrunkEntries(objJarFile, sSourceFilePath);
			}
			    
		}//end main:
		return objaReturn;
	}
	
	
	public static File extractFileAsTemp(JarFile jar, String sPath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(jar==null) {
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLog = null;			    
			try {						
				   JarEntry entry = JarEasyUtilZZZ.getEntryForFile(jar, sPath); 
				   if(entry==null){
					   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND: '" + sPath +"'";
					   System.out.println(sLog);			    	
					}else{					    	
					   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND: '" + sPath +"'";
					   System.out.println(sLog);
					    	
					   //Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
					   if(entry.isDirectory()) { //Dateien nicht extrahieren!!!
						   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY, WILL NOT BE EXTRACTED AS TEMP-FILE";
						   System.out.println(sLog);
					   }else {				
						   sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS FILE, WILL BE EXTRACTED AS TEMP-FILE";
						   System.out.println(sLog);
					    		
						    	
					   		objReturn = extractFileFromJarAsTemp(jar, entry);
					    }
					}						    		
				   jar.close();
				} catch (IOException e1) {
					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(), e1);
					throw ez;
				}				
		}//end main:
		return objReturn;
	}
	
	
	
	//#################################################################################
	
	private static File extractFileToTemp_(File objFileAsJar, String sFilePathInJarIn, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File objReturn=null;
		main:{							
			File[] objaReturn = JarEasyZZZ.extractFilesToTemp_(objFileAsJar, sFilePathInJarIn, sTargetDirectoryPathIn);
			if(objaReturn==null) break main;
			
			objReturn = objaReturn[0];
		}//end main:
		return objReturn;
	}
		
	private static File[] extractFilesToTemp_(File objFileAsJar, String sFilePathInJarIn, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			if(objFileAsJar==null){
				ExceptionZZZ ez = new ExceptionZZZ("No File as JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!FileEasyZZZ.isJar(objFileAsJar)) {
				ExceptionZZZ ez = new ExceptionZZZ("Provided File is no JarFile.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String sTargetDirectoryPath;
			if(sTargetDirectoryPathIn==null)
				sTargetDirectoryPath="";  //TEMP VERZEICHNIS???
			else {
				//Links und rechts ggfs. übergebenen Trennzeichen entfernen. So normiert kann man gut weiterarbeiten.				
				sTargetDirectoryPath=StringZZZ.stripFileSeparators(sTargetDirectoryPathIn);									
			}
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) sTargetDirectoryFilepath='" + sTargetDirectoryPath +"'.";
		   	System.out.println(sLog);
			
			//+++ Verwende nun die Resourcen - Behandlung, damit das Verzeichnis auch tatsächlich erstellt wird ++++++++++++
			try {			
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(sFilePathInJarIn);
				objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPath);
			
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
		}//end main:
		return objaReturn;
	}
	
	
	
	public static File[] extractFilesFromJarAsTrunkFileDummies(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			if(StringZZZ.isEmpty(sSourceDirectoryPath)){
				ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objJarFile==null){
				ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			try{
				//1. Aus der Jar Datei alle Dateien in dem Verzeichnis herausfiltern.						
				//Dieser Filter hat als einziges Kriterium den Verzeichnisnamen...
				String archiveName = objJarFile.getName();
				IFileFilePartFilterZipUserZZZ objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceDirectoryPath);
				FilenamePartFilterPathZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					File objFileTemp = JarEasyUtilZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);
					objaFileTempInTemp.add(objFileTemp);
				}
				objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);
				
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e);
				throw ez;
		    }	    
			}//end main:
			return objaReturn;
	}
	
	
	public static File[] findFile(File objDirectory, IFilenameFilterExpansionUserZZZ objFilter) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			
			check:{				
				String sDirPath = null;
				if(objDirectory==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "The Directory '" + objDirectory.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objDirectory);
				if(bIsJar) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The Directory '" + objDirectory.getPath() + "', is a jar File. To extract from a jar File use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(objDirectory.exists()==false){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The Directory '" + objDirectory.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;
				}else if(objDirectory.isDirectory()==false){
						ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The file '" + objDirectory.getPath() + "', was expected to be a file, not e.g. a directory.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
						throw ez;					
				}
								
				if(objFilter==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFilter missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;				
				}
				
			}//End check
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden		
			objaReturn = objDirectory.listFiles(objFilter);		
		}//End main		 	
		return objaReturn;
	}


public static File[] findDirectoryInJar(JarFile objJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
	File[] objaReturn = null;
	main:{		
		if(objJar==null) {
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile-Object missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;
		}				
		File objFileJar = JarEasyUtilZZZ.toFile(objJar);
		objaReturn = JarEasyZZZ.findDirectoryInJar(objFileJar, objDirectoryFilterInJar, sTargetDirectoryPathIn, bWithFiles);					
	}//End main		 	
	return objaReturn;
}


public static File[] findDirectoryInJar(File objFileJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
	File[] objaReturn = null;
	main:{	
		
		//Wenn im Ergebnis Dateien sein sollen, dann einen anderen FileFilter verwenden			
		HashMap<ZipEntry,File> hmTrunk = JarEasyZZZ.findDirectoryInJarAsTrunk(objFileJar, objDirectoryFilterInJar, sTargetDirectoryPathIn, bWithFiles);
		
		JarFile jf = JarEasyUtilZZZ.toJarFile(objFileJar);
		if(bWithFiles) {
			objaReturn = saveTrunkAsFiles(jf, hmTrunk);					
		}else {
			objaReturn = saveTrunkAsDirectories(jf, hmTrunk);
		}
					
	}//End main		 	
	return objaReturn;
}


public static HashMap<ZipEntry,File> findDirectoryInJarAsTrunk(File objFileJar, IFileDirectoryPartFilterZipUserZZZ objDirectoryFilterInJar, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ{
		HashMap<ZipEntry,File>hmReturn = new HashMap<ZipEntry,File>();
		main:{
			try {
			check:{								
				String sDirPath = null;
				if(objFileJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				boolean bIsJar = FileEasyZZZ.isJar(objFileJar);
				boolean bIsZip = FileEasyZZZ.isZip(objFileJar);
				if(!bIsJar && !bIsZip) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The File '" + objFileJar.getPath() + "', is not a jar File. To extract from a directory use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}

				if(objFileJar.exists()==false){
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The JarFile '" + objFileJar.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
																
				if(objDirectoryFilterInJar==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileDirectoryFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}														
			}//End check
		
			String sApplicationKeyAsSubDirectoryTemp;
			if(StringZZZ.isEmpty(sTargetDirectoryPathIn)) {
				sApplicationKeyAsSubDirectoryTemp = "FGL";
			}else {
				sApplicationKeyAsSubDirectoryTemp = sTargetDirectoryPathIn;
			}
			
			//####################
			String sPathDirTemp;
			if(FileEasyZZZ.isPathRelative(sApplicationKeyAsSubDirectoryTemp)){
				String sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
				sPathDirTemp = FileEasyZZZ.joinFilePathName(sDirTemp, sApplicationKeyAsSubDirectoryTemp);					
			}else {
				sPathDirTemp = sApplicationKeyAsSubDirectoryTemp;
			}
				
			//Innerhalb der JAR-Datei wird immer mit / gearbeitet dies wieder rückgängig machen.
			sPathDirTemp = JarEasyUtilZZZ.toFilePath(sPathDirTemp);
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html			
			String archiveName = objFileJar.getAbsolutePath();
			JarInfo objJarInfo = null;
			if(bWithFiles) {
				IFileDirectoryWithContentPartFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilterWithConent();		
				objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			}else {
				IFileDirectoryPartFilterZipZZZ objPartFilter = objDirectoryFilterInJar.getDirectoryPartFilter();			
				objJarInfo = new JarInfo( archiveName,  objPartFilter );//Mit dem Filter wird nur das Verzeichnis herausgefiltert.
			}
			
			//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
			Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();																		
			Set<String> setEntryName = ht.keySet();
			Iterator<String> itEntryName = setEntryName.iterator();	
			if(bWithFiles) { //Fall: Mit Dateien
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					String sNameTemp = JarEasyUtilZZZ.toFilePath(zeTemp.getName());
					
					//Nun aus dem ZipEntry ein File Objekt machen 
					//https://www.rgagnon.com/javadetails/java-0429.html	
					File objFileTemp = objFileTemp = new File(sPathDirTemp, sNameTemp);
										
					//Das Ergebnis in die Trunk - HashMap packen
					hmReturn.put(zeTemp, objFileTemp);
				}
			}else { //Fall: Ohne Dateien
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					if(zeTemp.isDirectory()) {
						String sNameTemp = JarEasyUtilZZZ.toFilePath(zeTemp.getName());
				
						//Nun aus dem ZipEntry ein File Objekt machen 
						//https://www.rgagnon.com/javadetails/java-0429.html				
						File objFileTemp = new File(sPathDirTemp, sNameTemp);
				
						//Das Ergebnis in die Trunk - HashMap packen
						hmReturn.put(zeTemp, objFileTemp);
					}else {
						//Aus den Dateien das Verzeichnis holen
						String sNameTemp = JarEasyUtilZZZ.toFilePath(zeTemp.getName());
						String sNameDir = FileEasyZZZ.getParent(sNameTemp);;
								
						String sPathDirTotal = FileEasyZZZ.joinFilePathName(sPathDirTemp, sNameDir);
						File objFileTemp = new File(sPathDirTotal);

						//Nun in einer Schleife die Verzeichnisse hinzufügen. Problem, was als zeTemp nehmen? Es wird x-mal das gleich File-Objekt hinzugefügt.
						hmReturn.put(zeTemp,  objFileTemp);
					}
				}
			}
						
	}catch (Exception e){
    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
    }	    			
	}//End main		 	
	return hmReturn;
	}


public static File[] findFileInJar(JarFile objJar, IFileFilePartFilterZipUserZZZ objFilterFileInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
	File[] objaReturn = null;
	main:{		
		if(objJar==null) {
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile-Object missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
			throw ez;
		}				
		File objFileJar = JarEasyUtilZZZ.toFile(objJar);				
		objaReturn = JarEasyZZZ.findFileInJar(objFileJar, objFilterFileInJar, sApplicationKeyAsSubDirectoryTempIn);					
	}//End main		 	
	return objaReturn;
}


public static File[] findFileInJar(File objFileJar, IFileFilePartFilterZipUserZZZ objFilterFileInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
	File[] objaReturn = null;
	main:{
		String sLog;
		check:{																		
			if(objFilterFileInJar==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Exception 01).";
				System.out.println(sLog);
				
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FileFileFilterForJar missing.  '", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}														
		}//End check
		sLog = ReflectCodeZZZ.getPositionCurrent()+": START XXXXXXXXXXXXXX.";
		System.out.println(sLog);									
		
		//Zuerst den genauen Namensfilter-Verwenden, sofern vorhanden, danach den allgemeineren Verzeichnisfilter
		IFilenamePartFilterZipZZZ objPartFilter = objFilterFileInJar.computeFilePartFilterUsed();
		String sDirPathInJar = objFilterFileInJar.computeDirectoryPathInJarUsed();
		String sFileNameInJar = objFilterFileInJar.computeFileNameInJarUsed();
		objaReturn = JarEasyZZZ.findFileInJar_(objFileJar, objPartFilter, sDirPathInJar, sFileNameInJar, sApplicationKeyAsSubDirectoryTempIn);
											
	}//End main		 	
	return objaReturn;
}


public static File[] findFileInJar(File objFileJar, ZipEntryFilter objFilterInJar, String sDirPathInJar, String sFileNameInJar, String sApplicationKeyAsSubDirectoryTempIn) throws ExceptionZZZ{
	File[] objaReturn = null;
	main:{
		objaReturn = JarEasyZZZ.findFileInJar_(objFileJar,objFilterInJar ,sDirPathInJar, sFileNameInJar, sApplicationKeyAsSubDirectoryTempIn);			
	}//End main		 	
	return objaReturn;
}


public static File[] findFileInJar(JarInfo objJarInfoFiltered, String sDirPathInJar, String sTargetDirPathRootIn) throws ExceptionZZZ{
	return JarEasyZZZ.findFileInJar(objJarInfoFiltered, sDirPathInJar, sTargetDirPathRootIn);
}


private static File[] findFileInJar_(File objFileJar, ZipEntryFilter objPartFilter, String sDirPathInJarIn, String sFileNameInJar, String sTargetDirPathRootIn) throws ExceptionZZZ{
		File[] objaReturn = null;
		main:{
			String sLog;
			if(objFileJar==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "JarFile missing" , iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			boolean bIsJar = FileEasyZZZ.isJar(objFileJar);
			if(!bIsJar) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The File '" + objFileJar.getPath() + "', is not a jar File. To extract from a directory use another method.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}

			if(objFileJar.exists()==false){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "The JarFile '" + objFileJar.getPath() + "', does not exist.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
															
			if(objPartFilter==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "IFilenamePartFilterZipZZZ Object missing.", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}	
				
//Merke: Wenn man nur nach dem Dateinamen sucht, dann ist der Pfad in der Jar Datei natürlich leer.
//			if(StringZZZ.isEmpty(sDirPathInJarIn)) {
//				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "Directory Path in Jar Stringobject. ", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
//				throw ez;
//			}
			
			//SUCHE IN JAR FILE		
			String archiveName = objFileJar.getAbsolutePath();			
			JarInfo objJarInfoFiltered = new JarInfo( archiveName, objPartFilter ); //MERKE: DAS DAUERT LAAAANGE
			
			
			
			//##############################################################
//			Alle Dateien auflisten, dazu aber den übergebenen FileFilter verwenden
			//https://www.javaworld.com/article/2077586/java-tip-83--use-filters-to-access-resources-in-java-archives.html
				
			//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
			Hashtable<String,ZipEntry> ht = objJarInfoFiltered.zipEntryTable();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": (JA) XXXXXXXXXXXXXX.";
		   	System.out.println(sLog);
			
			//Wie nun vom ht nach objaReturn, also dem fertigen File-Objekt ???
			//Es geht nur als temporäres Objekt, das man in ein temp-Verzeichnis ablegt.								
			Set<String> setEntryName = ht.keySet();
			Iterator<String> itEntryName = setEntryName.iterator();
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			try {
				boolean bTargetDirPathRootCreated=false; File objFileTemp=null; String sTargetDirPathRoot=null;
				ZipFile zf = objJarInfoFiltered.getZipFile();
				while(itEntryName.hasNext()) {
					
					//Achtung: Wenn die gesuchte Ressource nicht in der Jar - Datei gefunden worden ist, dann darf auch nicht das Zielverzeichnis erstellt worden sein.
					//         Darum erst jetzt (nur 1x ausgeführt!!!) ggfs. alte Verzeichnisse löschen und die Verzeichnisse wieder neu erstellen.
					if(!bTargetDirPathRootCreated) {
						ReferenceZZZ<String> strTargetDirPath = new ReferenceZZZ<String>(sTargetDirPathRootIn);
						ReferenceZZZ<String> strPathInJar = new ReferenceZZZ<String>(sDirPathInJarIn);
						ReferenceZZZ<String> strFilenameInJar = new ReferenceZZZ<String>(sFileNameInJar);						
						objFileTemp = JarEasyUtilZZZ.createTargetDirectoryRoot(strTargetDirPath, strPathInJar, strFilenameInJar);
						
						bTargetDirPathRootCreated=true;						
					}					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JB) XXXXX NEUER EINTRAG XXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JB) Extrahierter Eintrag: '" + zeTemp.getName() + "'";
				   	System.out.println(sLog);
					
					File objFileTempInTemp = ZipEasyZZZ.extractZipEntryToDirectoryRoot(zf, zeTemp, sTargetDirPathRootIn);
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JC) Extrahierter Eintrag als Datei: '" + objFileTempInTemp.getAbsolutePath() + "'";
				   	System.out.println(sLog);
					objaFileTempInTemp.add(objFileTempInTemp);
				}					
				if(zf!=null) zf.close();
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (JD) XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);
			} catch (IOException e) {
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (J ERROR) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//End main		 	
	return objaReturn;
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
			bReturn = JarEasyUtilZZZ.isInJar(JarEasyZZZ.class);
		}
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
		
	public static File peekDirectoryFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{			
				File[] objaReturn = JarEasyZZZ.peekDirectories_(jar, null, sPath, sTargetDirectoryPathRootIn, true);
				if(objaReturn!=null) {
					objReturn = objaReturn[0];
				}			
		}//end main:
		return objReturn;
	}
	
	public static File peekDirectoryFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
		File objReturn = null;
		main:{			
				File[] objaReturn = JarEasyZZZ.peekDirectories_(jar, null, sPath, sTargetDirectoryPathRootIn, bWithFiles);
				if(objaReturn!=null) {
					objReturn = objaReturn[0];
				}			
		}//end main:
		return objReturn;
	}
	
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekDirectories(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{							
				objaReturn = JarEasyZZZ.peekDirectories_(objJarFile, null, sSourceDirectoryPath, sTargetDirectoryPathIn,true);
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekDirectories(JarFile objJarFile, String sSourceDirectoryPath, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{							
				objaReturn = JarEasyZZZ.peekDirectories_(objJarFile, null, sSourceDirectoryPath, sTargetDirectoryPathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekDirectories(JarFile objJarFile, JarEntry entry, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			objaReturn = JarEasyZZZ.peekDirectories_(objJarFile, entry, null, sTargetDirectoryPathIn, bWithFiles);
		}//end main:
		return objaReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	private static File[] peekDirectories_(JarFile objJarFile, JarEntry entryIn, String sSourceFilePathIn, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//#########################################################
				//Ziel: Einen möglichst genauen Entry bekommen. 
				String sLog; String sFilePath; IFileFilePartFilterZipUserZZZ objFilterFileInJar;  IFileDirectoryPartFilterZipUserZZZ objFilterDirectoryInJar; JarEntry entry=null;
				String sSourceFilePath;
				if(entryIn==null){
					if(StringZZZ.isEmpty(sSourceFilePathIn)){
						ExceptionZZZ ez = new ExceptionZZZ("No filepath provided and no JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}						
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) SEARCHING ENTRY AS DIRECTORY PATH: '" + sSourceFilePathIn +"'";
				   	System.out.println(sLog);
					entry = JarEasyUtilZZZ.getEntryForDirectory(objJarFile, sSourceFilePathIn);					
					if(entry==null){
						sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR (Directory) PATH: '" + sSourceFilePathIn +"'";
					   	System.out.println(sLog);
					
					   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) SEARCHING ENTRY AS FILE PATH: '" + sSourceFilePathIn +"'";
					   	System.out.println(sLog);
						entry = JarEasyUtilZZZ.getEntryForFile(objJarFile, sSourceFilePathIn);
					}					
					
					
					if(entry==null) {						
					  	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR PATH: '" + sSourceFilePathIn +"'";
					   	System.out.println(sLog);													   
					}else{					    	
					   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR PATH: '" + sSourceFilePathIn +"'";
					   	System.out.println(sLog);
					}					
				}else{
					entry = entryIn;
				}
																				
				if(entry!=null) {
					sSourceFilePath = entry.getName();
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) Extracting by Entry with Path '" + sSourceFilePath + "'";
					System.out.println(sLog);	
				}else {
					sSourceFilePath = sSourceFilePathIn;
				}
				
				
				//###############################################################								
				//Ziel Einen möglichst genauen Filter erstellen. Hier für Verzeichnisse.
				String archiveName = objJarFile.getName();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();				
				if(bWithFiles) { //Dateien im Verzeichnis
					//1. Aus der Jar Datei nur das Verzeichnis herausfiltern.						
												
					objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceFilePath);
					IFilenamePartFilterZipZZZ objFilterFilePathPart = objFilterFileInJar.getDirectoryPartFilter();										
					JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
					
					//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
					Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
					Set<String> setEntryName = ht.keySet();
					Iterator<String> itEntryName = setEntryName.iterator();
					
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
						
						//Nun aus dem ZipEntry ein File Objekt machen
						File objFileTemp;
						if(zeTemp.isDirectory()) {
							objFileTemp = JarEasyUtilZZZ.createDirectoryDummy(zeTemp, sTargetDirectoryPathIn);							
						}else {
							objFileTemp = JarEasyUtilZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);							
						}
						objaFileTempInTemp.add(objFileTemp);
					}	
					objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);
				}else { //Nur Verzeichnis-Fall PEEK
					objFilterDirectoryInJar = new FileDirectoryFilterInJarZZZ(sSourceFilePath);
																		
					//1. Aus der Jar Datei nur das Verzeichnis herausfiltern, geht nicht. Also nur 					
					JarInfo objJarInfo = new JarInfo( archiveName, objFilterDirectoryInJar );  //Merke: Das dauert laaange
					
					//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
					Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
					Set<String> setEntryName = ht.keySet();
					Iterator<String> itEntryName = setEntryName.iterator();
						
					while(itEntryName.hasNext()) {
						String sKey = itEntryName.next();
						ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
						//Nun aus dem ZipEntry ein File Objekt machen
						File objFileTemp;
						if(zeTemp.isDirectory()) {							
							objFileTemp = JarEasyUtilZZZ.createDirectoryDummy(zeTemp, sTargetDirectoryPathIn);							
						}else {
							objFileTemp = JarEasyUtilZZZ.createFileDummy(zeTemp, sTargetDirectoryPathIn);
							objFileTemp = objFileTemp.getParentFile();
						}
						objaFileTempInTemp.add(objFileTemp);
					}
					objaFileTempInTemp = (ArrayList<File>) ArrayListUtilZZZ.unique(objaFileTempInTemp);//Auch wenn mehrere Dateien in einem Verzeichnis sind, nur 1x das Verzeichnis zurückgeben.
					objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);
				}
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 *  Merke: if(fileAsTrunk.isFile()) arbeitet nicht zuverlässig, wenn es die Datei noch nicht auf Platte gibt.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47
	 * Siehe https://stackoverflow.com/questions/5830581/getting-a-directory-inside-a-jar
	 */
	public static File[] peekFiles(JarFile objJarFile, JarEntry entry, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			objaReturn = JarEasyZZZ.peekFiles_(objJarFile, entry, null, sTargetDirectoryPathRootIn);
		}//end main:
		return objaReturn;
	}
	
	public static File peekFileFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{			
				File[] objaReturn = JarEasyZZZ.peekFiles_(jar, null, sPath, sTargetDirectoryPathRootIn);
				if(objaReturn!=null) {
					objReturn = objaReturn[0];
				}			
		}//end main:
		return objReturn;
	}
	
	/** Merke: Die zurückgegebenen Dateien sind LEER. Darum eignet sich diese Methode lediglich dazu die Existenz von Verzeichnissen/Dateien in der JAR Datei zu prüfen.
	 * @param filePath
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.06.2020, 13:08:47	
	 */
	public static File[] peekFiles(JarFile objJarFile, String sSourceFilePath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
					
		   	objaReturn = JarEasyZZZ.peekFiles_(objJarFile, null, sSourceFilePath,sTargetDirectoryPathIn);
		
		}//end main:
		return objaReturn;
	}
	
	/** Private Methode, um Redundanz zu vermeiden
	 * @param jar
	 * @param sPath
	 * @param sTargetDirectoryPathRootIn
	 * @param bAsDirectory
	 * @param bWithFiles       Merke: Wird ignoriert, wenn bAsDirectory=false!, dann wird nur die Datei zurückgegeben.
	 * @param bSave
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 29.11.2020, 09:30:09
	 */
	private static File[] peekFiles_(JarFile objJarFile, JarEntry entryIn, String sSourceFilePath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
		File[] objaReturn=null;
		main:{
			try{
				//Merke objJarFile wird noch nicht verwendet, aber für das Directory holen schon....
				if(objJarFile==null){
					ExceptionZZZ ez = new ExceptionZZZ("No JarFile provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//#########################################################
				//Ziel: Einen möglichst genauen Entry bekommen.
				String sLog; String sFilePath; IFileFilePartFilterZipUserZZZ objFilterFileInJar; JarEntry entry=null;
				if(entryIn==null){
					if(StringZZZ.isEmpty(sSourceFilePath)){
						ExceptionZZZ ez = new ExceptionZZZ("No filepath provided and no JarEntry-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}						
					entry = JarEasyUtilZZZ.getEntryForFile(objJarFile, sSourceFilePath);
				}else{
					entry = entryIn;
				}
												
				if(entry==null){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR (File) PATH: '" + sSourceFilePath +"'";
				   	System.out.println(sLog);
				   	
					entry = JarEasyUtilZZZ.getEntryForDirectory(objJarFile, sSourceFilePath);
				}
				
				if(entry==null) {						
				  	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT FOUND FOR (Directory) PATH: '" + sSourceFilePath +"'";
				   	System.out.println(sLog);													   
				}else{					    	
				   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR (File) PATH: '" + sSourceFilePath +"'";
				   	System.out.println(sLog);
				}
				
				//##################################################################
				//Ziel: Einen möglichst genauen Filter erstellen. Hier für Dateien.
				if(entry==null) {
					objFilterFileInJar = new FileFileFilterInJarZZZ(null, sSourceFilePath);
				} else {
					sFilePath = entry.getName();
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (E) Extracting by Entry with FilePath '" + sFilePath + "'";
					System.out.println(sLog);					
					objFilterFileInJar = new FileFileFilterInJarZZZ(null, entry.getName());
				}
				
				//#######################################################
				//1. Aus der Jar Datei nur die Datei herausfiltern.						
				String archiveName = objJarFile.getName();												
				IFilenamePartFilterZipZZZ objFilterFilePathPart = objFilterFileInJar.getNamePartFilter();
				JarInfo objJarInfo = new JarInfo( archiveName, objFilterFilePathPart );  //Merke: Das dauert laaange
				
				//Hashtable in der Form ht(zipEntryName)=zipEntryObjekt.
				Hashtable<String,ZipEntry> ht = objJarInfo.zipEntryTable();			
				Set<String> setEntryName = ht.keySet();
				Iterator<String> itEntryName = setEntryName.iterator();
				ArrayList<File>objaFileTempInTemp = new ArrayList<File>();

				//Nur Dateien-Fall
				while(itEntryName.hasNext()) {
					String sKey = itEntryName.next();
					ZipEntry zeTemp = (ZipEntry) ht.get(sKey);
					
					if(!zeTemp.isDirectory()) {
						//Nun aus dem ZipEntry ein File Objekt machen 						
						File objFileTemp = JarEasyUtilZZZ.createFileDummy(zeTemp, sTargetDirectoryPathRootIn);
						objaFileTempInTemp.add(objFileTemp);
					}
				}
										
				objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);					
			}catch (Exception e){
		    	ExceptionZZZ ez  = new ExceptionZZZ("An error happened: " + e.getMessage(), iERROR_RUNTIME, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return objaReturn;
	}
	
	public static boolean saveTrunkAsDirectory(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();				
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				boolean bErg = FileEasyZZZ.makeDirectoryForDirectory(fileTemp);
				if(!bErg) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;

				}
			}
			bReturn = true;		
		}//end main:
		return bReturn;
	}
	
	public static File[] saveTrunkAsDirectories(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{			
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}

			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();
			
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				boolean bErg = FileEasyZZZ.makeDirectoryForDirectory(fileTemp);
				if(!bErg) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}else {
					objaFileTempInTemp.add(fileTemp);
				}
			}
			objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);	
		}//end main:
		return objaReturn;
	}
	
	public static boolean saveTrunkAsFile(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();				
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				File objReturn = JarEasyZZZ.saveTrunkToFile(jf, zeTemp, fileTemp);
				if(objReturn==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;

				}
			}
			bReturn = true;		
		}//end main:
		return bReturn;
	}
	
	public static File[] saveTrunkAsFiles(JarFile jf, HashMap<ZipEntry,File> hmTrunk) throws ExceptionZZZ {
		File[] objaReturn = null;
		main:{			
			if(hmTrunk==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "HashMap with Trunk ZipEntry, File - Objects", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}

			Set<ZipEntry> setEntry = hmTrunk.keySet();
			Iterator<ZipEntry> itEntry = setEntry.iterator();
			
			ArrayList<File>objaFileTempInTemp = new ArrayList<File>();
			while(itEntry.hasNext()) {
				ZipEntry zeTemp = itEntry.next();
				File fileTemp = (File)hmTrunk.get(zeTemp);
				
				File objReturn = JarEasyZZZ.saveTrunkToFile(jf, zeTemp, fileTemp);
				if(objReturn==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_RUNTIME + "Unable to save File - Object '" + fileTemp.getAbsolutePath() + "'", iERROR_RUNTIME, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}else {
					objaFileTempInTemp.add(objReturn);
				}
			}
			objaReturn = ArrayListUtilZZZ.toFileArray(objaFileTempInTemp);	
		}//end main:
		return objaReturn;
	}
	
	public static File saveTrunkToFile(JarFile jf, ZipEntry ze, File fileAsTrunk) throws ExceptionZZZ {
		File objReturn=null;
		main:{

				//### Nun neu erstellen, dabei wird ggfs. zuvor gelöscht
				objReturn = ZipEasyZZZ.extractZipEntryTo(jf, ze, fileAsTrunk);

		}//end main:
		return objReturn;
	}
	
	public static File saveTrunkToDirectory(JarFile jf, ZipEntry ze, String sTargetDirectoryPathIn ) throws ExceptionZZZ {
		File objReturn=null;
		main:{								
			//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
			File objFile = JarEasyUtilZZZ.createFileDummy(ze, sTargetDirectoryPathIn);
			objReturn = ZipEasyZZZ.extractZipEntryTo(jf, ze, objFile);						
		}//end main:
		return objReturn;
	}
	
	

	


		
		/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
		 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
		 *  und das anschliessende speichern davon.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File[] searchResources(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
				File[] objaReturn = null;
				main:{
					    objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, false, false, true);
				}//end main:
				return objaReturn;
			}
		
		/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
		 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
		 *  und das anschliessende speichern davon.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File[] searchResources(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
				File[] objaReturn = null;
				main:{
					objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, false, bWithFiles, true);			
				}//end main:
				return objaReturn;
			}
		
		/** Man könnte auch erst die Datei als File-Dummy suchen und dann extrahieren.
		 *  Aber das dauert jedesmal zu lange, also wird das hier in einem Rutsch gemacht über "Trunk"-Objekte
		 *  und das anschliessende speichern davon.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File searchResourceFile(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
			File objReturn = null;
			main:{				
					File[] objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, false, bWithFiles, true);
					if(objaReturn==null) break main;
					
					objReturn = objaReturn[0];				
			}//end main:
			return objReturn;
			}
		
		/** Man sucht hiermit die Datei, diese wird in einem Temporären Verzeichnis auch extrahiert existieren.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File searchResourceFileFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File objReturn = null;
			main:{				
					File[] objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, false, true, true);
					if(objaReturn==null) break main;
					
					objReturn = objaReturn[0];
			}//end main:
			return objReturn;
			}
		
		/** Man sucht hiermit die Datei, diese wird in einem Temporären Verzeichnis auch extrahiert existieren.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File[] searchResourceFiles(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{				
					objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, false, true, true);					
			}//end main:
			return objaReturn;
			}
		
		
		public static boolean hasResourceDirectory(JarFile jar, String sPath) {
			boolean bReturn = false;
			main:{
				//TODOGOON;//20230525: Wenn man nur prüfen will, ob es ein konkretes Verzeichnis gibt...
				//ABER: GEHT DAS OHNE DIE GESAMTE JAR - DATEI ZU EXTRAHIEREN UND SO AUF PLATTE ZU PACKEN????
			}//end main:
			return bReturn; 
		}
		
		/** Man sucht hiermit das Verzeichnis, dieses wird auch extrahiert(ggfs. mit Dateien) existieren.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File searchResourceDirectoryFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
			File objReturn = null;
			main:{				
					File[] objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, true, bWithFiles, true);
					if(objaReturn==null) break main;
					
					if(!FileEasyZZZ.isDirectory(objaReturn[0])) {
						//Merke: In dem zurückgegebenen Array sind normalerweise nur die Dateien enthalten. Für das Verzeichnis auf das ParentFile des 0ten Elements zugreifen.
						objReturn = objaReturn[0].getParentFile();
					}else {
						objReturn = objaReturn[0];
					}
			}//end main:
			return objReturn;
			}
		
		/** Man sucht hiermit das Verzeichnis, dieses wird auch extrahiert(ggfs. mit Dateien) existieren.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File[] searchResourceDirectories(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{				
				objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, true, bWithFiles, true);					
			}//end main:
			return objaReturn;
			}
		
		/** Man sucht hiermit das Verzeichnis und die darin enthaltenen Dateien dieses wird auch (GGfs. OHNE DATEIEN) extrahiert.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File searchResourceDirectoryFirst(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File objReturn = null;
			main:{				
					File[] objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, true, true, true);
					if(objaReturn==null) break main;
					
					if(!FileEasyZZZ.isDirectory(objaReturn[0])) {
						//Merke: In dem zurückgegebenen Array sind normalerweise nur die Dateien enthalten. Für das Verzeichnis auf das ParentFile des 0ten Elements zugreifen.
						objReturn = objaReturn[0].getParentFile();
					}else {
						objReturn = objaReturn[0];
					}
			}//end main:
			return objReturn;
			}
		
		/** Man sucht hiermit das Verzeichnis und die darin enthaltenen Dateien dieses wird auch (GGfs. OHNE DATEIEN) extrahiert existieren.
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 17.10.2020, 09:26:43
		 */
		public static File[] searchResourceDirectories(JarFile jar, String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{				
					objaReturn = JarEasyZZZ.searchResources_(jar, sPath, sTargetDirectoryPathRootIn, true, true, true);					
			}//end main:
		return objaReturn;
		}

		

		/** Private Methode, um Redundanz zu vermeiden
		 * @param jar
		 * @param sPath
		 * @param sTargetDirectoryPathRootIn
		 * @param bAsDirectory
		 * @param bWithFiles       Merke: Wird ignoriert, wenn bAsDirectory=false!, dann wird nur die Datei zurückgegeben.
		 * @param bSave
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 29.11.2020, 09:30:09
		 */
		private static File[] searchResources_(JarFile jar, String sPath, String sTargetDirectoryPathRootIn, boolean bForDirectory, boolean bWithFiles, boolean bSave) throws ExceptionZZZ {
			File[] objaReturn = null;
			main:{
				String sLog = null;
				try {			
				String sTargetDirectoryPathRoot=null;
				if(StringZZZ.isEmpty(sTargetDirectoryPathRootIn)) {
					sTargetDirectoryPathRoot =  "ZZZ";
				}else {
					sTargetDirectoryPathRoot = sTargetDirectoryPathRootIn;				
				}
		
				JarEntry entry = null;
				if(bForDirectory) {
					entry = JarEasyUtilZZZ.getEntryForDirectory(jar, sPath);
				} else {
					//Achtung....bAsDirectory das macht ein wirklich temporäres Objekt
					entry = JarEasyUtilZZZ.getEntryForFile(jar, sPath);
				}
				if(entry==null){
				  	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE NOT(!) FOUND FOR PATH: '" + sPath +"'";
				   	System.out.println(sLog);			    	
				}else{					    	
				   	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IN JAR FILE FOUND FOR PATH: '" + sPath +"'";
				   	System.out.println(sLog);
					    	
				   	String sTargetDirPath = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(), sTargetDirectoryPathRoot);
				   	
				    //Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
				    if(entry.isDirectory()) { //DATEIEN NICHT SOFORT EXTRAHIEREN!!!  
		
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					    
					   	if(bSave) {
					   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
						   	System.out.println(sLog);
						   	
					   		objaReturn = extractDirectoryToTemps(jar, entry, sTargetDirPath, bWithFiles);
					   		
					   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DA2) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
						   	System.out.println(sLog);
					   	}else {				   		
					   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
						   	System.out.println(sLog);
						   	
					   		objaReturn = peekDirectories(jar, entry,sTargetDirPath, bWithFiles);	
					   		
					   		sLog = ReflectCodeZZZ.getPositionCurrent()+": (DB2) ENTRY IS DIRECTORY: '" + entry.getName() +"'";
						   	System.out.println(sLog);
					   	}
					   	
				    	if(objaReturn!=null) {
				    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY - FILE OBJECTS FROM JARENTRY CREATED: '" + entry.getName() +"'";
					    	System.out.println(sLog);
					    	
					    	//Merke: In dem Array sind nur die Dateien. Auf das Verzeichnis über das erste Element des Arrays zugreifen.
					    	if(FileEasyZZZ.exists(objaReturn[0].getParentFile())) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT EXISTS AS: '" + objaReturn[0].getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT NOT(!) EXISTS AS: '" + objaReturn[0].getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}				    		
			    		}else {
			    			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) DIRECTORY EMPTY - FILE OBJECTS NOT CREATED '" + sTargetDirPath + "' (NULL CASE)";
					    	System.out.println(sLog);
					    	
					    	File objReturnTrunk = JarEasyUtilZZZ.createFileDummy(entry, sTargetDirPath);
					    	FileEasyZZZ.createDirectory(objReturnTrunk);
					    	File objReturn = objReturnTrunk;
					    	if(FileEasyZZZ.exists(objReturn)) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}else {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT NOT(!) EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
						    	System.out.println(sLog);
					    	}	
					    	objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturn);
			    		}
			    	
				    }else {
				    	sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) ENTRY IS NOT A DIRECTORY: '" + entry.getName() +"'";
					   	System.out.println(sLog);
					   	
					   	if(!bWithFiles) {
					   		//Fall: Dateipfad eingegeben, aber es soll keine Datei zurückgegeben werden.
					   		//Also: Verzeichnis zurückgeben		
					   		String sEntryPath = entry.getName();
					   		sEntryPath = JarEasyUtilZZZ.toFilePath(sEntryPath);
					   		String sTargetDirPathFromEntry = FileEasyZZZ.joinFilePathName(sTargetDirPath, sEntryPath);
					   		File objReturnTrunksTargetDirPath = FileEasyZZZ.getDirectory(sTargetDirPathFromEntry);				   		
					   		if(bSave) {					   			
					   			FileEasyZZZ.createDirectoryForDirectory(objReturnTrunksTargetDirPath);
					   			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT PERSISTED AS: '" + objReturnTrunksTargetDirPath.getAbsolutePath() +"' (bSave=true CASE)";
						    	System.out.println(sLog);
					   		}else {
					   			sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (DIRECTORY) TRUNK OBJECT NOT PERSISTED AS: '" + objReturnTrunksTargetDirPath.getAbsolutePath() +"' (bSave=false CASE)";
						    	System.out.println(sLog);
					   		}
					   		objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturnTrunksTargetDirPath);
					   	}else {
						   	File objReturnTrunk = JarEasyUtilZZZ.createFileDummy(entry,sTargetDirPath);
					    	if(objReturnTrunk!=null) {
					    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT FROM JARENTRY CREATED: '" + entry.getName() +"'->'"+objReturnTrunk.getAbsolutePath()+"'";
						    	System.out.println(sLog);
			
						    	if(bSave) {
							    	File objReturn = saveTrunkToFile(jar, entry, objReturnTrunk);
							    	if(objReturn!=null) {
							    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT PERSISTED AS: '" + objReturn.getAbsolutePath() +"'";
								    	System.out.println(sLog);
										    	
								    	if(FileEasyZZZ.exists(objReturn)) {
								    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
									    	System.out.println(sLog);
								    	}else {
								    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT(!) EXISTS AS: '" + objReturn.getAbsolutePath() +"'";
									    	System.out.println(sLog);
								    	}						    							    	
							    	}else {
							    		sLog = ReflectCodeZZZ.getPositionCurrent()+": (D) (FILE) TRUNK OBJECT NOT PERSISTED AS: '" + objReturn.getAbsolutePath() +"' (NULL CASE)";
								    	System.out.println(sLog);
							    	}
							    	objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturn);
						    	}else {
						    		objaReturn = FileArrayEasyZZZ.add(objaReturn, objReturnTrunk);
						    	}					    					    
				    		}
					   	}
				    }
				}
					jar.close();
				} catch (IOException e1) {
					ExceptionZZZ ez  = new ExceptionZZZ("Arbeiten mit temporärer Datei, weil sFile = null. IOException: " + e1.getMessage(), iERROR_RUNTIME, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName(),e1);
					throw ez;
				}			
			}// end main:
			return objaReturn;
		}
		
	
}



