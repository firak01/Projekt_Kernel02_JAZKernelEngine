package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarKernelZZZ;
import basic.zBasic.util.file.jar.filter.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.filter.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import junit.framework.TestCase;

public class JarEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    
			objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile();
						
				
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
		    
			//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.			 
						
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}			
	}//END setUp
	protected void tearDown() {
		if(objJarAsSource!=null) {
			try {
				objJarAsSource.close();
			} catch (IOException e) {
				ExceptionZZZ ez=null;
				try {
					ez = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				}catch(ExceptionZZZ ez1){
					ez1.printStackTrace();
					fail("Method throws an exception." + ez1.getMessageLast());
				}				
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
		}
	}//END tearDown
	
	
		
	public void testExtractDirectoryToTemp(){
		try{	
			boolean btemp;
			File objDirCreated; File[] objaDirCreated;
			String sTargetDirectoryPathRoot;
			String sDirTemp; String sDirParentTemp;	String sDirParent; boolean bErg;
			
			
			String sDirToExtract="template";
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen										
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHOUTFILES";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot,false);
			if(objaDirCreated==null) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			
			
			btemp = JarEasyTestCommonsZZZ.ensureDirectoryStructureInTempExistsForDirectories(objaDirCreated, sTargetDirectoryPathRoot, sDirToExtract);
			assertTrue("Die Verzeichnisstruktur ist nicht korrekt: sDirToExtractTo='"+sTargetDirectoryPathRoot+"'|sDirToExtract='"+sDirToExtract+"'", btemp);
			
			//Ab) Erfolgsfall, mit Dateien erzeugen							
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHFILES";			
			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot);
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sTargetDirectoryPathRoot);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			}else {
				//Verzeichnispfad vergleichen
				assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objDirCreated.getAbsolutePath() + "'", sDirParent.equals(objDirCreated.getAbsolutePath()));
			}
			if(!objDirCreated.exists()) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt.");
			}
			
			//Ac) Erfolgsfall, mit Dateien erzeugen						
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHFILES02";			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot, true);									
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sTargetDirectoryPathRoot);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);			
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}

			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHOUTFILES";				
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot, false);		
			if(objaDirCreated==null) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sTargetDirectoryPathRoot);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Verzeichnis '" + objFileTemp.getAbsolutePath() + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfade vergleichen!!!!
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getAbsolutePath()));
				}
			}
			
			//Bb) Erfolgsfall, mit Dateien erzeugen						
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHFILES";					
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot, true);						
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sTargetDirectoryPathRoot);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}
			}
			
			//Bc) Erfolgsfall, mit Dateien erzeugen						
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_DIRECTORY_WITHFILES02";			
			objaDirCreated = JarEasyZZZ.extractDirectoryToTemps(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot, true);
			if(objDirCreated==null) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt (NULL-WERT).");
			}
			
			sDirTemp = EnvironmentZZZ.getHostDirectoryTemp();									
			sDirParentTemp =  FileEasyZZZ.joinFilePathName(sDirTemp, sTargetDirectoryPathRoot);
			sDirParent = FileEasyZZZ.joinFilePathName(sDirParentTemp, sDirToExtract);
			for(File objFileTemp : objaDirCreated ) {
				if(!objFileTemp.exists()) {
					fail("Datei '" + objFileTemp.getAbsolutePath() + " im Verzeichnis '" + sTargetDirectoryPathRoot + "' wurde nicht erstellt.");
				}else {
					//Verzeichnispfad mit Elternverzeichnis vergleichen
					assertTrue("Es wird als Verzeichnisstruktur erwartet: '" + sDirParent + "' aber der Pfad ist: '" + objFileTemp.getAbsolutePath() + "'", sDirParent.equals(objFileTemp.getParentFile().getAbsolutePath()));
				}
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
//	public void testExtractDirectoryToTemp(){
//		try{
//			JarFile objJar = new JarFile(objFileJarAsSource);
//			
//			
//			String sDirToExtract="template";
//			String sDirToExtractTotal; String sTargetDirPathTotal;
//			File objDirCreated;
//			
//			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
//			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
//			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
//			sDirToExtractTotal = "FGL_WITHOUTFILES_TEMP" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
//			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);	
//						
//			objDirCreated = JarEasyZZZ.extractDirectoryToTemp(objJar, sDirToExtract, sTargetDirPathTotal, false);
//			if(!objDirCreated.exists()) {
//				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
//			}
//			
//			//Ab) Erfolgsfall, mit Dateien erzeugen						
//			sDirToExtractTotal = "FGL_WITHFILES_TEMP" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
//			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
//			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
//			if(!objDirCreated.exists()) {
//				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
//			}
//			
////			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
////			//B) Fall: Dateien exitieren bereits. D.h. alles komplett löschen in der Methode und neu anlegen.
////			//Ba) Erfolgsfall, ohne Dateien zu erzeugen					
////			sDirToExtractTotal = "FGL_WITHOUTFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
////			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
////			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, false);
////			if(!objDirCreated.exists()) {
////				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
////			}
////			
////			//Bb) Erfolgsfall, mit Dateien erzeugen						
////			sDirToExtractTotal = "FGL_WITHFILES" + FileEasyZZZ.sDIRECTORY_SEPARATOR + sDirToExtract;
////			sTargetDirPathTotal = FileEasyZZZ.joinFilePathName(sTargetDirPath, sDirToExtractTotal);			
////			objDirCreated = JarEasyZZZ.extractDirectory(objFileJarAsSource, sDirToExtract, sTargetDirPathTotal, true);
////			if(!objDirCreated.exists()) {
////				fail("Verzeichnis '" + sTargetDirPathTotal + "' wurde nicht erstellt.");
////			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		} catch (IOException e) {
//			fail("An IOException happend testeing: " + e.getMessage());
//		}
//	}
	
	public void testExtractFromJarAsTrunk() {
		try{
			File[] objaReturn; File objFile; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog;
			String sDirToExtractTo; boolean bErg;
			
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
					
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen		
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK01";
						
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtract='" + sDirToExtract + "'";
			System.out.println(sLog);
			
			
			
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN FILE OBJEKT.
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
			File[] objaCreated01 = JarEasyZZZ.peekDirectories(objJar,sDirToExtract, sDirToExtractTo, false);
			if(objaCreated01==null) fail("Verzeichnis '" + sDirToExtract + "' wurde nicht gefunden (NULL-WERT).");
			
			assertTrue("Es wurde nur das Verzeichnis erwartet zu '"+sDirToExtract+"' (also keine Unterverzeichnisse, keine Dateien)",objaCreated01.length==1);
			for(File objFileTemp : objaCreated01) {
				 if(FileEasyZZZ.exists(objFileTemp)) {
						fail("File-Objekt '" + objFileTemp.getAbsolutePath() + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File-Objekt als Dummy * '" + objFileTemp.getAbsolutePath() + "'";
				    System.out.println(sLog);
				    bErg = FileEasyZZZ.isFileExisting(objFileTemp);
					assertFalse("File-Objekt sollte ein Verzeichnis sein (keine Datei): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
				}
			}
					
			
			//Ab) Erfolgsfall, mit Dateien erzeugen						
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_FILES";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
			
			File[] objaCreated02 = JarEasyZZZ.peekDirectories(objJar, sDirToExtract, sDirToExtractTo, true);
			if(objaCreated02==null) {
				fail("Verzeichnis '" + sDirToExtractTo + "' wurde nicht gefunden (NULL-WERT).");
			}
			assertTrue("Es wurde nur die Dateien erwartet zu '"+sDirToExtract+"' ",objaCreated02.length==3);
			for(File objFileTemp : objaCreated02) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": File-Objekt Dummy? * '" + objFileTemp.getAbsolutePath() + "'";
			    System.out.println(sLog);
			    
				bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis!): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
				
				bErg = FileEasyZZZ.isFileExisting(objFileTemp);
				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
								
			}
			

		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testExtractFromJarAsTrunk_hmZipEntry01() {
		try{
			String sDirToExtractTo; String sDirToExtract;String sTargetDirectoryPathRoot; boolean bErg;
			String sLog;
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen
			sDirToExtract="template";			
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORY_HASHMAP"; //D.h. dieses Verzeichnis darf nicht erstellt werden.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");		
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN ZipEntry und FILE OBJEKTE, die es noch nicht auf der Platte zu geben braucht.
			HashMap<ZipEntry,File> hmTrunk = new HashMap<ZipEntry,File>();
			
			//Merke: hmTrunk ist sonst leer. CallByReference-Problematik, Lösung mit Zwischenobjekt
			ReferenceZZZ<HashMap<ZipEntry,File>> hashmapTrunk=new ReferenceZZZ<HashMap<ZipEntry,File>>(hmTrunk);			
			bErg = JarEasyZZZ.extractFromJarAsTrunk(objJar, sDirToExtract, sDirToExtractTo, hashmapTrunk);// Das dauert laaange!!!
			if(!bErg) fail("Verzeichnis '" + sDirToExtract + "' wurde nicht als Trunk extrahiert.");
			
			hmTrunk = hashmapTrunk.get();
			assertFalse(hmTrunk.size()==0);
			assertTrue(hmTrunk.size()>=1);
			
			Set<ZipEntry>setEntry = hmTrunk.keySet();
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(bErg) {
					fail("Datei sollte nur als Trunk und nicht jetzt schon existieren: " + fileTemp.getAbsolutePath());
				}
			}
								
			
			
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testExtractFromJarAsTrunk_hmZipEntry02() {
		try{
			String sDirToExtractTo; String sDirToExtract;String sTargetDirectoryPathRoot; boolean bErg;
			String sLog;
			
			//####################################################################
			//Merke: Mit diesen File-Objekten kann man eigentlich nur die Existenz in der JAR-Datei prüfen.
			//Einen Input Stream zu bekommen ist die Voraussetzung für das Erstellen der Datei als Kopie
			//
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
			
			//Um also auf ein echtes File-Objekt zugreifen zu können als "TRUNK" ein ZipFileEntry-Array zurückliefern.
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Auf das Jar - Dummy mit der ausgwählten Konstellation zugreifen
			JarFile objJar = JarKernelZZZ.getJarFileTestZip();	//Das "zip" soll Verzeichnisse ohne Dateien enthalten.					
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) DATEIEN SUCHEN ABER NICHT ERZEUGEN				
			sDirToExtract = "JarEasyZZZ_searchForDirectories/subDirectory03";
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORY_HASHMAP03a"; //D.h. dieses Verzeichnis darf nicht erstellt werden.			
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");			
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN ZipEntry und FILE OBJEKTE, die es noch nicht auf der Platte zu geben braucht.			
			//Merke: hmTrunk ist sonst leer. CallByReference-Problematik, Lösung mit Zwischenobjekt
			HashMap<ZipEntry,File> hmTrunk = new HashMap<ZipEntry,File>();			
			ReferenceZZZ<HashMap<ZipEntry,File>> hashmapTrunk=new ReferenceZZZ<HashMap<ZipEntry,File>>(hmTrunk);			
			bErg = JarEasyZZZ.extractFromJarAsTrunk(objJar, sDirToExtract, sDirToExtractTo, hashmapTrunk);// Das dauert laaange!!!
			if(!bErg) fail("Verzeichnis '" + sDirToExtract + "' wurde nicht als Trunk extrahiert.");
		
			hmTrunk = hashmapTrunk.get();
			assertFalse(hmTrunk.size()==0);
			assertTrue(hmTrunk.size()>=1);
			
			Set<ZipEntry>setEntry = hmTrunk.keySet();
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(bErg) {
					fail("Datei sollte nur als Trunk und nicht jetzt schon existieren: " + fileTemp.getAbsolutePath());
				}
			}
								
		
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			//Ab) EXPLIZIT NUR DATEIEN SUCHEN
			sDirToExtract = "JarEasyZZZ_searchForDirectories/subDirectory03";
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORY_HASHMAP03b"; //D.h. dieses Verzeichnis darf nicht erstellt werden.

			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			ZipEntry[] objaCreated03 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar,sDirToExtract, sDirToExtractTo, true);
			assertNotNull("EntryArray-Objekt für Ziel '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).",objaCreated03);
			assertTrue("Es wurde nur die Dateien erwartet zu '"+sDirToExtract+"'. Anzahl der Einträge (="+objaCreated03.length+") anders als erwartet.",objaCreated03.length==7);
			for(ZipEntry objZipEntryTemp : objaCreated03) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ZipEntry-Objekt Dummy-Datei ? * '" + objZipEntryTemp.getName() + "'";
			    System.out.println(sLog);
			    
//			    if(objZipEntryTemp.isDirectory()) {
//					fail("ZipEntry '" + objZipEntryTemp.getName() + "' sollte KEIN Verzeichnis sein.");
//				}
			    
			  //TODO: Irgendwie noch automatisiert prüfen, dass das Verzeichnis nicht erstellt worden ist.
//				boolean bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis!): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//				
//				bErg = FileEasyZZZ.isFileExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//								
																	
			}
						
			
			//Ac) EXPLIZIT NUR VERZEICHNISSE SUCHEN
			sDirToExtract = "JarEasyZZZ_searchForDirectories/subDirectory03";
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORY_HASHMAP03c"; //D.h. dieses Verzeichnis darf nicht erstellt werden.
			
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			ZipEntry[] objaCreated04 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar, sDirToExtract, sDirToExtractTo, false);
			assertNotNull("EntryArray-Objekt für Ziel '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).",objaCreated04);
			assertTrue("Es wurde nur die Verzeichnisse erwartet zu '"+sDirToExtract+"'. Anzahl der Einträge (="+objaCreated04.length+") anders als erwartet.",objaCreated04.length==3);			
			for(ZipEntry objZipEntryTemp : objaCreated04) {				
			    if(objZipEntryTemp.isDirectory()) {
			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Verzeichnis * " + objZipEntryTemp.getName() + "' als ZipEntry gefunden.";
					System.out.println(sLog);
				}
			    if(!objZipEntryTemp.isDirectory()) {
			    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Datei * " + objZipEntryTemp.getName() + "' als ZipEntry gefunden.";
					System.out.println(sLog);
					
					fail("ZipEntry '" + objZipEntryTemp.getName() + "' sollte KEINE DATEI sein.");
				}
			    
			  //TODO: Irgendwie noch automatisiert prüfen, dass das Verzeichnis nicht erstellt worden ist.
//				boolean bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis!): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//				
//				bErg = FileEasyZZZ.isFileExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//																								
			}
			
			
			//######################################################################################
			//WAS TUN, WENN ES NUR EIN UNTERVERZEICHNIS OHNE DATEIEN DRIN GIBT. WIRD DAS AUCH ERFASST?
			//Ba) SUCHE NACH DATEIEN IN Verzeichnis mit LEEREM Unterverzeichnis
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORIES_SUB04a";
			sDirToExtract = "JarEasyZZZ_searchForDirectories/subDirectory04withoutFiles";

			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			//HIER SOLLEN KEINE EINTRÄGE VORHANDEN SEIN, DA NUR DATEIEN GEHOLT WERDEN.
			ZipEntry[] objaCreated05 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar,sDirToExtract, sDirToExtractTo, true);
			assertNull("EntryArray-Objekt für Ziel '" + sDirToExtractTo + "' sollte nicht erstellt sein (NULL-WERT). Es gibt keine Dateien in den Verzeichnissen. ",objaCreated05);
						
			
			//WAS TUN; WENN ES NUR EIN UNTERVERZEICHNIS OHNE DATEIEN DRIN GIBT. WIRD DAS AUCH ERFASST?
			//Ba) SUCHE NACH VERZEICHNIS in Verzeichnis mit LEEREM Unterverzeichnis
			sTargetDirectoryPathRoot = "FGL\\EXTRACT_AS_TRUNK_OF_DIRECTORIES_SUB04b";
			sDirToExtract = "JarEasyZZZ_searchForDirectories/subDirectory04withoutFiles";

			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sDirToExtractTo='" + sDirToExtractTo + "'";
			System.out.println(sLog);
						
			ZipEntry[] objaCreated06 = JarEasyZZZ.extractFromJarAsTrunkZipEntries(objJar,sDirToExtract, sDirToExtractTo, false);
			assertNotNull("EntryArray-Objekt für Ziel '" + sDirToExtractTo + "' wurde nicht erstellt (NULL-WERT).",objaCreated06);
			assertTrue("Es wurde nur die Verzeichnisse erwartet zu '"+sDirToExtract+"' ",objaCreated06.length==4);
			for(ZipEntry objZipEntryTemp : objaCreated06) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": ZipEntry-Objekt Dummy-Datei ? * '" + objZipEntryTemp.getName() + "'";
			    System.out.println(sLog);
			    
			    if(!objZipEntryTemp.isDirectory()) {
					fail("ZipEntry '" + objZipEntryTemp.getName() + "' sollte KEINE DATEI sein.");
				}
			    
			  //TODO: Irgendwie noch automatisiert prüfen, dass das Verzeichnis nicht erstellt worden ist.
//				boolean bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis!): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//				
//				bErg = FileEasyZZZ.isFileExisting(objFileTemp);
//				assertFalse("File-Objekt sollte eine nicht existierende Datei sein (kein Verzeichnis): '"+ objFileTemp.getAbsolutePath()+"'", bErg);
//								
																	
			}		
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testFindDirectoryInJar() {
		try{
			File[] objaReturn; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog; String sDirToExtractTo;
			boolean bErg;
			
			//#############			
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    
		    JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarKernelZZZ.iJAR_TEST);	    
		    assertNotNull("Die Jar-Dummy Datei sollte gefunden worden sein.", objJarFile);
		   		  
		    //###################################################################################
			//A) Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
		    sTargetDirectoryPathRoot = "FGL\\FIND_DIRECTORY_ONLY";
		    sPath = "JarEasyZZZ_searchForDirectories";
		    
		    
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
		    bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");			
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'";
			System.out.println(sLog);
		    
		    
			IFileDirectoryPartFilterZipUserZZZ objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			objaReturn = JarEasyZZZ.findDirectoryInJar(objJarFile, objFilterDirInJar, sTargetDirectoryPathRoot, false);
			assertNotNull(objaReturn);
			for(File objDir : objaReturn) {
				assertNotNull(objDir);	
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objDir.getAbsolutePath() + "'";
				System.out.println(sLog);									
				assertTrue("Das sollte ein existierendendes Verzeichnis sein: " + objDir.getAbsolutePath(), FileEasyZZZ.isDirectoryExisting(objDir));
			}
			
			//#####################################################################################################
			//B) Das Verzeichnis mit allen darin enthaltenen Dateien erstellen
			sTargetDirectoryPathRoot = "FGL\\FIND_DIRECTORY";
		    sPath = "JarEasyZZZ_searchForDirectories";
		    
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
		    bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'";
			System.out.println(sLog);
		    
		    
			objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			objaReturn = JarEasyZZZ.findDirectoryInJar(objJarFile, objFilterDirInJar, sTargetDirectoryPathRoot, true);
			assertNotNull(objaReturn);
			assertTrue("Es sollten mehrere Dateien enthalten sein in dem Verzeichnis '" + sPath + "'", objaReturn.length>=2);
			for(File objFile : objaReturn) {
				assertNotNull(objFile);
				assertTrue("Das File-objekt sollte auf der Platte existieren: " + objFile.getAbsolutePath(), objFile.exists());
				if(objFile.isDirectory()){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objFile.getAbsolutePath() + "'";
					System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
					System.out.println(sLog);
				}				
			}
			
			//##################################################################################
			//##########################################
			//C) FALL: Das Verzeichnis ist NICHT VORHANDEN
			sTargetDirectoryPathRoot = "FGL\\FIND_DIRECTORY_NOT_EXISTING";
		    sPath = "nichtDaVerzeichnis";
		    
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
		    bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'";
			System.out.println(sLog);
		    
		    
			objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			objaReturn = JarEasyZZZ.findDirectoryInJar(objJarFile, objFilterDirInJar, sTargetDirectoryPathRoot, false);
			assertNull(objaReturn);	
			
			//##########################################
			//D) FALL: Das Verzeichnis ist NICHT VORHANDEN, Suche nach Dateien
			sTargetDirectoryPathRoot = "FGL\\FIND_DIRECTORY_NOT_EXISTING_FILESEARCH";
		    sPath = "nichtDaVerzeichnis";
		    
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			sDirToExtractTo = FileEasyZZZ.joinFilePathName(EnvironmentZZZ.getHostDirectoryTemp(),sTargetDirectoryPathRoot);			
			FileEasyZZZ.removeDirectoryContent(sDirToExtractTo, true, true);
			FileEasyZZZ.removeDirectory(sDirToExtractTo);
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'";
			System.out.println(sLog);
		    
		    
			objFilterDirInJar = new FileDirectoryFilterInJarZZZ(sPath);										
			objaReturn = JarEasyZZZ.findDirectoryInJar(objJarFile, objFilterDirInJar, sTargetDirectoryPathRoot, true);
			assertNull(objaReturn);
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
	}
	
	public void testFindFileInJar() {
		try{
			File[] objaReturn; File objFile; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog;
			String sDirToExtractTo; boolean bErg;
			
			//#############
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    
		    JarFile objFileAsJar = JarKernelZZZ.getJarFileUsed();		    
		    assertNotNull(objFileAsJar);
		    
		    //#######################################################################
		    //TESTFÄLLE FÜR: DATEIEN GEFUNDEN
		    //VARIANTE 1: Pfad und Dateinamen im Dateinamen
		    sTargetDirectoryPathRoot = "FGL\\FIND_FILE01";
		    sPath = null;
		    sFilename = "bat/KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");		
		    
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
			sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNotNull(objaReturn);
			assertTrue("Sollte nur 1 Element haben", objaReturn.length==1);
			
			objFile = objaReturn[0];
			sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
			System.out.println(sLog);	
			assertTrue("Dies sollte auf der Platte existieren", objFile.exists());
			assertTrue("Das sollte eine Datei sein: " + objFile.getAbsolutePath(), objFile.isFile());
			
			//##########################################################
			//VARIANTE 2: Pfad und Dateinamen getrennt übergeben
			sTargetDirectoryPathRoot = "FGL\\FIND_FILE02";
		    sPath = "bat";
		    sFilename = "KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
		    			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
			sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNotNull(objaReturn);
			assertTrue("Sollte nur 1 Element haben", objaReturn.length==1);
			
			objFile = objaReturn[0];
			sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
			System.out.println(sLog);	
			assertTrue("Dies sollte auf der Platte existieren", objFile.exists());
			assertTrue("Das sollte eine Datei sein: " + objFile.getAbsolutePath(), objFile.isFile());
			
			//#########################################################
			//VARIANTE 3: Nur Pfad angegeben
			sTargetDirectoryPathRoot = "FGL\\FIND_FILE03";
		    sPath = "bat";
		    sFilename = null;
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);		    
		    
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
			sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNotNull(objaReturn);
			assertTrue("Sollte mehr als 1 Element haben", objaReturn.length>=2);
									
			for(File objFileTemp : objaReturn) {
				assertNotNull(objFile);
				assertTrue("Das File-objekt sollte auf der Platte existieren: " + objFileTemp.getAbsolutePath(), objFileTemp.exists());
				if(objFileTemp.isDirectory()){
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory * '" + objFileTemp.getAbsolutePath() + "'";
					System.out.println(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFileTemp.getAbsolutePath() + "'";
					System.out.println(sLog);
				}				
			}
			
			//#########################################################
			//VARIANTE 4: Nur Datei angegeben. Merke: Das könnte mehrere Objekte zurückgeben. Aber dafür müsste der Dateiname gleich sein.
			sTargetDirectoryPathRoot = "FGL\\FIND_FILE04";
		    sPath = null;
		    sFilename = "KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
		    	
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
			sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNotNull(objaReturn);
			assertTrue("Sollte nur 1 Element haben", objaReturn.length==1);
			
			objFile = objaReturn[0];
			sLog = ReflectCodeZZZ.getPositionCurrent()+": File * '" + objFile.getAbsolutePath() + "'";
			System.out.println(sLog);	
			assertTrue("Dies sollte auf der Platte existieren", objFile.exists());
			assertTrue("Das sollte eine Datei sein: " + objFile.getAbsolutePath(), objFile.isFile());
			
			//#####################################################################################
			//TESTFÄLLE FÜR NICHTS GEFUNDEN: 
			//VARIANTE 1a: Pfad und Dateinamen im Dateinamen, PFAD NICHT VORHANDEN
		    sTargetDirectoryPathRoot = "FGL\\FIND_FILE05_NOT_FOUND_EMPTY";
		    sPath = null;
		    sFilename = "baetschi/KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
		    
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
		    //Theoretisch sollte das Verzeichnis erstellbar sein;
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Erstelle Verzeichnis als Test.";
			System.out.println(sLog);
		    objFile = JarEasyUtilZZZ.createTargetDirectoryRoot(sTargetDirectoryPathRoot, sPath, sFilename);
		    assertNotNull("Verzeichnis sollte erstellt worden sein. ", objFile);
		    assertTrue("Dies sollte auf der Platte sogar existieren", objFile.exists());
		    
		    //Nun aber wieder zur Praxis.
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
		    sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNull("Sollte nichts gefunden haben", objaReturn);
			
			//###################################################################
			//++++++++++++++++++++++++++++++++++++++++++++
			//VARIANTE 1b: Pfad und Dateinamen im Dateinamen, DATEI NICHT VORHANDEN
		    sTargetDirectoryPathRoot = "FGL\\FIND_FILE06_NOT_FOUND_EMPTY";
		    sPath = null;
		    sFilename = "bat/NixdaKernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
		    			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
			
		    //Theoretisch sollte das Verzeichnis erstellbar sein;
			sLog = ReflectCodeZZZ.getPositionCurrent()+": Erstelle Verzeichnis als Test.";
			System.out.println(sLog);		    
		    objFile = JarEasyUtilZZZ.createTargetDirectoryRoot(sTargetDirectoryPathRoot, sPath, sFilename);
		    assertNotNull("Verzeichnis sollte erstellt worden sein. ", objFile);
		    assertTrue("Dies sollte auf der Platte sogar existieren", objFile.exists());
		   	 
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
		    sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNull("Sollte nichts gefunden haben", objaReturn);
			
			//+++++++++++++++++++++++++++++++
			//VARIANTE 1b: Pfad und Dateinamen im Dateinamen, DATEI NICHT VORHANDEN
		    sTargetDirectoryPathRoot = "FGL\\FIND_FILE06_NOT_FOUND_NOT_CREATED";
		    sPath = null;
		    sFilename = "batnixda/KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sPath='" + sPath + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
					    
		    //Theoretisch sollte das Verzeichnis erstellbar sein;
//			sLog = ReflectCodeZZZ.getPositionCurrent()+": Erstelle Verzeichnis als Test.";
//			System.out.println(sLog);		    
//		    objFile = JarEasyUtilZZZ.createTargetDirectoryRoot(sTargetDirectoryPathRoot, sPath, sFilename);
//		    assertNotNull("Verzeichnis sollte erstellt worden sein. ", objFile);
//		    assertTrue("Dies sollte auf der Platte sogar existieren", objFile.exists());
		   	 
			//Dieser Filter hat Unterfilter, die er auswählt anhand der Eingabewerte
		    sLog = ReflectCodeZZZ.getPositionCurrent()+" Führe aus: findFileInJar";
			System.out.println(sLog);
			objFilterFileInJar = new FileFileFilterInJarZZZ(sPath, sFilename);
			objaReturn = JarEasyZZZ.findFileInJar(objFileAsJar, objFilterFileInJar, sTargetDirectoryPathRoot);					
			assertNull("Sollte nichts gefunden haben", objaReturn);
			
			bErg = FileEasyZZZ.isPathSubExistingOfDirectoryTemp(sTargetDirectoryPathRoot);
			assertFalse(bErg);
			
			
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	}
	
	public void testSaveTrunkAsFile() {
		try{
			JarFile objJar = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
			String sDirToExtract="template";
			String sTargetDirectoryPathRoot; boolean bErg;
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen					
			sTargetDirectoryPathRoot = "FGL\\TRUNKENTRY_OF_DIRECTORY";
					    
			//VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) {
				fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			}
						
			//Aa) VERZEICHNIS extrahieren. DAS ERZEUGT NUR EIN ZipEntry und FILE OBJEKTE, die es noch nicht auf der Platte zu geben braucht.
			HashMap<ZipEntry,File> hmTrunk = new HashMap<ZipEntry,File>();
			
			//Merke: hmTrunk ist sonst leer. CallByReference-Problematik, Lösung mit Zwischenobjekt
			ReferenceZZZ<HashMap<ZipEntry,File>> hashmapTrunk=new ReferenceZZZ<HashMap<ZipEntry,File>>(hmTrunk);			
			bErg = JarEasyZZZ.extractFromJarAsTrunk(objJar, sDirToExtract, sTargetDirectoryPathRoot, hashmapTrunk);//das dauert Laaange!!!
			if(!bErg) fail("Verzeichnis '" + sDirToExtract + "' wurde nicht als Trunk extrahiert.");			
			
			hmTrunk = hashmapTrunk.get();			
			assertFalse(hmTrunk.size()==0);
			assertTrue(hmTrunk.size()>=1);
						
			Set<ZipEntry>setEntry = hmTrunk.keySet();
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(bErg) {
					fail("Datei sollte nur als Trunk und nicht jetzt schon existieren: " + fileTemp.getAbsolutePath());
				}
			}
			
			
			//Nun erst auf die Platte bringen
			bErg = JarEasyZZZ.saveTrunkAsFile(objJar, hmTrunk);
			assertTrue(bErg);
			for(ZipEntry entry : setEntry) {
				File fileTemp = hmTrunk.get(entry);
				bErg = FileEasyZZZ.exists(fileTemp);
				if(!bErg) fail("Datei sollte jetzt am Schluss existieren: " + fileTemp.getAbsolutePath());				
			}
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFiles() {
		try{
			
			File[] objaReturn; File objFile; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sDirToExtractTo; String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog;
			boolean bErg;
			
			//#############
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    		    		   		    		
		    //#######################################################################
		    //TESTFÄLLE FÜR: DATEIEN GEFUNDEN
		    //VARIANTE 1: Pfad und Dateinamen im Dateinamen
		    sTargetDirectoryPathRoot = "FGL\\PEEK_FILES01";
		    sFilename = "test4file01.txt";  //Dies Datei soll über alle Verzeichnisse der JAR-DAtei gesucht werden
					
		  //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");			
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
			
			JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarKernelZZZ.iJAR_TEST);	    
		    assertNotNull("Die Jar-Dummy Datei sollte gefunden worden sein.", objJarFile);
		    	  
			objaReturn = JarEasyZZZ.peekFiles(objJarFile, sFilename, sTargetDirectoryPathRoot);
			assertNotNull(objaReturn);
			assertTrue(objaReturn.length==3);
			
			for(File objFileTemp : objaReturn) {
			 if(FileEasyZZZ.exists(objFileTemp)) {
					fail("File-Objekt '" + sFilename + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File-Objekt als Dummy * '" + objFileTemp.getAbsolutePath() + "'";
				    System.out.println(sLog);
				    bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
					assertFalse("File-Objekt sollte kein Verzeichnis (sondern eine Datei) sein: '"+ objFileTemp.getAbsolutePath()+"'", bErg);
				}
			}
			 
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekDirectory() {
		try{
			
			File[] objaReturn; File objFile; IFileFilePartFilterZipUserZZZ objFilterFileInJar;
			String sDirToExtractTo; String sTargetDirectoryPathRoot; String sPath; String sFilename; String sLog;
			boolean bErg;
			
			//#############
			
			sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}	
		    		    		   		    		
		    //#######################################################################
		    //TESTFÄLLE FÜR: VERZEICHNIS GEFUNDEN
		    //VARIANTE 1: Pfad und Unterverzeichnis im Dateinamen
		    sTargetDirectoryPathRoot = "FGL\\PEEK_DIRECTORY01";
		    sFilename = "subDirectory01/sub01";  //Dieses Verzeichnis soll über alle Verzeichnisse der JAR-DAtei gesucht werden
					
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.									
		    bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");			
			
			//TEST
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "'| sFilename='" + sFilename + "'";
			System.out.println(sLog);
			
			JarFile objJarFile = JarKernelZZZ.getJarFileUsed(JarKernelZZZ.iJAR_TEST);	    
		    assertNotNull("Die Jar-Dummy Datei sollte gefunden worden sein.", objJarFile);
		    	  
			objaReturn = JarEasyZZZ.peekDirectories(objJarFile, sFilename, sTargetDirectoryPathRoot, false);
			assertNotNull(objaReturn);
			assertTrue(objaReturn.length==2);//Das Verzeichnis, wie in sFilename PLUS 1 darin enthaltenes Unterverzeichnis.
			
			for(File objFileTemp : objaReturn) {
				 if(FileEasyZZZ.exists(objFileTemp)) {
					fail("File-Objekt '" + sFilename + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": File-Objekt als Dummy * '" + objFileTemp.getAbsolutePath() + "'";
				    System.out.println(sLog);
				    bErg = FileEasyZZZ.isDirectoryExisting(objFileTemp);
					assertFalse("File-Objekt sollte kein Verzeichnis (sondern eine Datei) sein: '"+ objFileTemp.getAbsolutePath()+"'", bErg);
				}
			}
			 
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
}//END Class
