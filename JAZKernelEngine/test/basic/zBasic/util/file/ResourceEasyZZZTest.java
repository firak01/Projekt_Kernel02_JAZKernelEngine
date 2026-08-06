package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.file.jar.JarEasyInCurrentJarZZZ;
import basic.zBasic.util.file.jar.JarEasyTestCommonsZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import junit.framework.TestCase;

public class ResourceEasyZZZTest extends TestCase{
	private File objFileJarAsSource=null;
	private JarFile objJarAsSource=null;
	private String sTargetDirPath=null;
	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    
		    if(!JarEasyUtilZZZ.isInJarStatic())	{				
				//Suche nach der Datei in der OPVN-Jar Datei, die als Konstante definiert wurde.
		    	objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile(JarEasyUtilZZZ.iJAR_TEST);					
			}else {
				objFileJarAsSource = JarKernelZZZ.getJarFileUsedAsFile();
			}
						
			sTargetDirPath=EnvironmentZZZ.getHostDirectoryTemp();
			sLog = ReflectCodeZZZ.getPositionCurrent()+": USE TEMP DIRECTORY '" + sTargetDirPath + "'";
		    System.out.println(sLog);	
			    
				//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test selbst. Aber nicht im Setup, dann das würde das vor jedem Test ausgeführt.			 
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("An exception happend testing: " + ez.getDetailAllLast());
			}
			
	}//END setUp
	protected void tearDown() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": TEARDOWN ###############################################.";
		    System.out.println(sLog);
		    
			if(objJarAsSource!=null) {
				try {
					objJarAsSource.close();
				} catch (IOException e) {
					ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, ResourceEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					ez.printStackTrace();
					fail("An exception happend testing: " + ez.getDetailAllLast());
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}//END tearDown
	
	public void testIsInSameJar() {
		try {
			boolean bErg;
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    assertNotNull(objFileJarAsSource);
		    if(ResourceEasyZZZ.isInJarStatic()) {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
		    	File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
		    	assertNotNull(objFileJar);
		    	
		    	bErg = ResourceEasyZZZ.isInSameJarStatic(objFileJar);
		    	assertTrue(bErg);
		    }else {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);
			    
			    File objFileJar = JarEasyUtilZZZ.getCodeLocationJar();
			    assertNull(objFileJar);
			    
			    objFileJar = JarKernelZZZ.getJarFileKernelAsFile();			    
			    bErg = ResourceEasyZZZ.isInSameJarStatic(objFileJar);
		    	assertFalse(bErg);		    			    	
		    }
		    
		    bErg = ResourceEasyZZZ.isInSameJarStatic(objFileJarAsSource);
	    	assertFalse(bErg);
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
		
	
	public void testPeekDirectoryInJar(){
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    sDirToExtract = "ResourceEasyZZZ_searchFor/template";
			sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_DIRECTORY_IN_JAR_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File objFileDir = ResourceEasyZZZ.peekDirectoryInJar(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot);
		    assertNotNull(objFileDir);
		    if(FileEasyZZZ.exists(objFileDir)) fail("Verzeichnis '" + sDirToExtract + "' sollte  nicht erstellt sein.");			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFileInJar(){
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen													
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    			
			sFileToExtract = "ResourceEasyZZZ_searchFor/template/readmeFGL.txt";
			sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_FILE_IN_JAR_DUMMY";
						
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
									
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File objFile = ResourceEasyZZZ.peekFileInJar(objFileJarAsSource, sFileToExtract, sTargetDirectoryPathRoot);
		    assertNotNull(objFile);
		    if(FileEasyZZZ.exists(objFile)) {
				fail("Datei '" + sTargetDirectoryPathRoot + "' sollte  nicht erstellt sein.");
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Datei als Dummy * '" + objFile.getAbsolutePath() + "'";
			    System.out.println(sLog);
			}
		    		    		    			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFilesInJar(){
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen													
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    			
		    sFileToExtract = "readmeFGL.txt";
			sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_FILES_IN_JAR_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
						
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File[] objaFile = ResourceEasyZZZ.peekFilesInJar(objFileJarAsSource, sFileToExtract, sTargetDirectoryPathRoot);
		    assertNotNull(objaFile);
		    assertTrue("Es wurde 1 Datei erwartetet",objaFile.length==1);
		    for(File objFile : objaFile) {
			    if(FileEasyZZZ.exists(objFile)) {
					fail("Datei '" + sFileToExtract + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": Datei als Dummy * '" + objFile.getAbsolutePath() + "'";
				    System.out.println(sLog);
				}
		    }
		    		    		    		
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testPeekFilesOfDirectoryInJar(){
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen													
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    			
		    sDirToExtract = "ResourceEasyZZZ_searchFor";
		    sTargetDirectoryPathRoot = "FGL\\PEEK_RESOURCE_FILES_IN_JAR_DUMMY";
			
		    //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
									
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}
			
			File[] objaFile = ResourceEasyZZZ.peekFilesOfDirectoryInJar(objFileJarAsSource, sDirToExtract, sTargetDirectoryPathRoot);
		    assertNotNull(objaFile);
		    for(File objFile : objaFile) {
			    if(FileEasyZZZ.exists(objFile)) {
					fail("Datei '" + sDirToExtract + "' sollte  nicht erstellt sein.");
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": FileDummy * '" + objFile.getAbsolutePath() + "'";
				    System.out.println(sLog);
				}
		    }
		    		    		    			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	
	public void testSearchDirectoryInJar() {
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen													
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    
			File objFileDummy;	
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			} 
			 
			
			//Fall AA: Nur Verzeichnis suchen, in temp erstellen
			sDirToExtract = "ResourceEasyZZZ_searchFor";
			sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_DIRECTORY_IN_JAR_DUMMY";
			
			 //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
									
			if(!JarEasyUtilZZZ.isInJarStatic())	{							
				JarFile objJarFile = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
				assertNotNull("JarFile nicht erstellt", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceDirectoryFirst(objJarFile, sDirToExtract, sTargetDirectoryPathRoot);				
			}else {
				
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceDirectoryFirst(sDirToExtract, sTargetDirectoryPathRoot);		
			}
			
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) fail("Verzeichnis '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");			
				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Fall AB: Nur Datei suchen, in temp  erstellen.								
			sFileToExtract = "ResourceEasyZZZ_searchFor/template/template_server_TCP_443.ovpn";
			sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_FILE_IN_JAR_DUMMY";
			
			 //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{							
				JarFile objJarFile = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
				assertNotNull("JarFile nicht erstellt", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceFileFirst(objJarFile, sFileToExtract, sTargetDirectoryPathRoot);				
			}else {
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceFileFirst(sFileToExtract, sTargetDirectoryPathRoot);
			}
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) fail("Datei '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");
																
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	public void testSearchDirectory() {
		try{
			boolean bErg; boolean btemp;
			File objDirCreated; File[] objaDirCreated; File objFileCreated;
			String sTargetDirectoryPathRoot; String sDirToExtract; String sFileToExtract;
			String sDirTemp; String sDirParentTemp;	String sDirParent;
									
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//A) Fall: Dateien exitieren noch nicht. D.h. alles neu anlegen.
			//Aa) Erfolgsfall, ohne Dateien zu erzeugen													
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    
			File objFileDummy;	
			if(JarEasyUtilZZZ.isInJarStatic())	{
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
			    System.out.println(sLog);
			} 
			
			//Fall AA: Nur Verzeichnis suchen, in temp erstellen
			sDirToExtract = "ResourceEasyZZZ_searchFor";
			sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_DIRECTORY_IN_JAR_DUMMY";
			
			 //VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{								
				JarFile objJarFile = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
				assertNotNull("JarFile nicht erstellt", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceDirectoryFirst(objJarFile, sDirToExtract, sTargetDirectoryPathRoot);				
			}else {
				
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceDirectoryFirst(sDirToExtract, sTargetDirectoryPathRoot);		
			}
			
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) {
				fail("Verzeichnis '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");
			}
				
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Fall AB: Nur Datei suchen, in temp  erstellen.								
			sFileToExtract = "ResourceEasyZZZ_searchFor/template/template_server_TCP_443.ovpn";
			sTargetDirectoryPathRoot = "FGL\\SEARCH_RESOURCE_FILE_DUMMY";
			
			//VORBEREITUNG: Verzeichnisse (inkl Unterverzeichnisse) löschen. Das Vor dem Test machen. Aber nicht im Setup, dann das wird vor jedem Test ausgeführt.
			bErg = JarEasyTestCommonsZZZ.ensureDirectoryTempDoesNotExist(sTargetDirectoryPathRoot);			
			if(!bErg) fail("Verzeichnis '" + sTargetDirectoryPathRoot + "' konnte zu Testbeginn nicht geloescht werden.");
			
			if(!JarEasyUtilZZZ.isInJarStatic())	{							
				JarFile objJarFile = JarEasyUtilZZZ.toJarFile(objFileJarAsSource);
				assertNotNull("JarFile nicht erstellt", objJarFile);
					    
				objFileDummy = JarEasyZZZ.searchResourceFileFirst(objJarFile, sFileToExtract, sTargetDirectoryPathRoot);				
			}else {
				objFileDummy = JarEasyInCurrentJarZZZ.searchResourceFileFirst(sFileToExtract, sTargetDirectoryPathRoot);
			}
			assertNotNull(objFileDummy);
			if(!FileEasyZZZ.exists(objFileDummy)) {
				fail("Datei '" + objFileDummy.getAbsolutePath() + "' sollte erstellt worden sein.");
			}												
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
//	
//	public void testFindFile() {		
//		try{
//			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
//		    System.out.println(sLog);
//		    
//			File objDirectoryCreated;	
//			String sPath; String sTargetDirectoryPathRoot;File[] objaFile;
//			if(JarEasyUtilZZZ.isInJarStatic())	{
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Dieser Test wird nur innerhalb einer JAR-Datei durchgeführt.";
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Innerhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			}else {
//				sLog = ReflectCodeZZZ.getPositionCurrent()+": Ausserhalb einer JAR-Datei durchgeführt.";
//			    System.out.println(sLog);
//			    
//				//Fall AA: Nur Verzeichnis als Dummy suchen, nicht erstellen
////				sPath = "debug/zBasic";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP_DUMMY";
////				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResource(sPath, sTargetDirectoryPathRoot);				
////				assertNotNull(objDirectoryCreated);
////				if(objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' sollte nicht erstellt worden sein.");
////				}
////				
////				
////				
////				//FALL BA: NUR VERZEICHNIS ERSTELLEN			
////				sPath = "debug/zBasic";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_DIRECTORY_TO_TEMP";
////				objDirectoryCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot, false);				
////				assertNotNull(objDirectoryCreated);
////				if(!objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////				
////				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
////				if(!FileArrayEasyZZZ.isEmpty(objaFile)){
////					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' sollten nicht erstellt worden sein.");
////				}
////			
////			
////				//FALL BB: DATEI FINDEN
////				sPath = "template/template_server_TCP_443.ovpn";
////				sTargetDirectoryPathRoot = "SEARCH_RESOURCE_FILE_TO_TEMP";
////				File objFileCreated = JarEasyInCurrentJarZZZ.searchResourceToTemp(sPath, sTargetDirectoryPathRoot);
////				assertNotNull(objFileCreated);
////				
////				objDirectoryCreated = objFileCreated.getParentFile();
////				assertNotNull(objDirectoryCreated);
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": Parent=Directory='" + objDirectoryCreated.getAbsolutePath() + "'.";
////			    System.out.println(sLog);
////				
////				if(!objDirectoryCreated.exists()) {
////					fail("Verzeichnis '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}					
////																
////				objaFile = FileEasyZZZ.listFilesOnly(objDirectoryCreated);
////				if(FileArrayEasyZZZ.isEmpty(objaFile)){
////					fail("Dateien innerhalb des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurden nicht erstellt.");
////				}
////				for(File objFileTemp : objaFile) {
////					sLog = ReflectCodeZZZ.getPositionCurrent()+": File='" + objFileTemp.getAbsolutePath() + "'.";
////				    System.out.println(sLog);
////				}
////				
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": sPath='" + sPath + "'.";
////			    System.out.println(sLog);
////			    
////			    String sFilePath = JarEasyZZZ.toFilePath(sPath);
////			    sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePath='" + sFilePath + "'.";
////			    System.out.println(sLog);
////			    
////				String sFileName = FileEasyZZZ.getNameFromFilepath(sPath);
////				sLog = ReflectCodeZZZ.getPositionCurrent()+": sFileName='" + sFileName + "'.";
////			    System.out.println(sLog);
////			    
////				if(!FileArrayEasyZZZ.contains(objaFile,sFilePath)){
////					fail("Datei '" + sFilePath + "' innerhalb des Auflistung des Verzeichnisses '" + objDirectoryCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////								
////				if(!objFileCreated.exists()) {
////					fail("Datei '" + objFileCreated.getAbsolutePath() + "' wurde nicht erstellt.");
////				}
////				if(!objFileCreated.isFile()) {
////					fail("Datei '" + objFileCreated.getAbsolutePath() + "' gilt nicht als Datei.");
////				}
//			}
//			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
//		
//	}

	
	
	
}//END Class
