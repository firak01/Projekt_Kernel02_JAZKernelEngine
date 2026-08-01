package basic.zBasic.util.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.file.jar.IJarEasyConstantsZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import junit.framework.TestCase;

public class JarEasyTestCommonsZZZ extends TestCase implements IJarEasyConstantsZZZ{

	public  static boolean ensureDirectoryTempDoesNotExist(String sDirToExtractTo) throws ExceptionZZZ {
		boolean bReturn = false;
		
		//VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
		String sDirToExtractToTotal = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sDirToExtractTo);
		boolean bErg = FileEasyZZZ.removeDirectoryContent(sDirToExtractToTotal, true);
		bReturn = FileEasyZZZ.removeDirectory(sDirToExtractToTotal);
		
		return bReturn;
	}
	
	public static boolean ensureDirectoryStructureInTempExistsForDirectories(File[] objaDirCreated, String sDirToExtractTo, String sDirToExtract) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sLogTemp;String sLog;			
			for(File objFileTemp : objaDirCreated ) {
				bReturn = JarEasyTestCommonsZZZ.ensureDirectoryStructureInTempExistsForDirectory(objFileTemp, sDirToExtractTo, sDirToExtract);
				if(!bReturn)break main;
			}				
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean ensureDirectoryStructureInTempExistsForDirectory(File objDirCreated, String sDirToExtractTo, String sDirToExtract) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sLogTemp;String sLog;
			String sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			String sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			
			sDirToExtract = JarEasyUtilZZZ.toFilePath(sDirToExtract);
			String sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			
			if(!FileEasyZZZ.exists(objDirCreated)) {
				sLogTemp = " FileObjekt '" + objDirCreated.getAbsolutePath() + "' wurde nicht real (auf der Festplatte) erstellt.";
				sLog = ReflectCodeZZZ.getPositionCurrent()+ sLogTemp;
				System.out.println(sLog);
				
				fail(sLogTemp);
			}else {
				sLogTemp = " Verzeichniskonsistenzpruefung. Errechnete Variable: sDirParent='"+sDirParent+"' und der Pfad ist: '" + objDirCreated.getAbsolutePath() + "'";
				sLog = ReflectCodeZZZ.getPositionCurrent()+ sLogTemp;
				System.out.println(sLog);
				
				//Verzeichnispfad vergleichen!!!
				//ABER: Zusätzliche, überflüssig angehängte Verzeichnisse kann man so nicht entdecken.
				assertTrue(sLogTemp, objDirCreated.getAbsolutePath().startsWith(sDirParent));										
			}
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	

	public static boolean ensureDirectoryStructureInTempExistsForFiles(File[] objaFileCreated, String sDirToExtractTo, String sDirToExtract) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sLogTemp;String sLog;
			String sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			String sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sDirToExtractTo);
			
			sDirToExtract = JarEasyUtilZZZ.toFilePath(sDirToExtract);
			String sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);			
			for(File objFileTemp : objaFileCreated ) {
				if(!FileEasyZZZ.exists(objFileTemp)) {
					sLogTemp = " FileObjekt '" + objFileTemp.getAbsolutePath() + "' wurde nicht real (auf der Festplatte) erstellt.";
					sLog = ReflectCodeZZZ.getPositionCurrent()+ sLogTemp;
					fail(sLog);
				}else {
					//Verzeichnispfad für die Datei ermitteln
					File objParentTemp = objFileTemp.getParentFile();
										
					sLogTemp = " Verzeichniskonsistenzpruefung. Errechnete Variable: sDirParent='"+sDirParent+"' und der Pfad ist: '" + objParentTemp.getAbsolutePath() + "'";
					sLog = ReflectCodeZZZ.getPositionCurrent()+ sLogTemp;
					System.out.println(sLog);
					
					//Verzeichnispfad vergleichen!!!
					//ABER: Zusätzliche, überflüssig angehängte Verzeichnisse kann man so nicht entdecken.
					assertTrue(sLogTemp, objParentTemp.getAbsolutePath().startsWith(sDirParent));										
				}
			}				
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
