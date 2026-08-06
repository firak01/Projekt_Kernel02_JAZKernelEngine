package basic.zBasic.util.file.jar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarKernelZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;
import basic.zBasic.util.file.jar.filter.FileDirectoryFilterInJarZZZ;
import basic.zBasic.util.file.jar.filter.FileFileFilterInJarZZZ;
import basic.zBasic.util.file.txt.parser.FileTextParserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class JarEasyUtilZZZTest extends TestCase{	
	private JarFile objJarAsSource=null;
	
	protected void setUp(){

		String sLog;
		main:{
		try {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": ############################################### SETUP .";
			System.out.println(sLog);

			objJarAsSource = JarKernelZZZ.getJarFileUsed();
				
			//MERKE ALS VORBEREITUNG: Verzeichnisse löschen. Das Vor dem Test machen, im Test selbst. Aber nicht im Setup, dann das würde das vor jedem Test ausgeführt.			 

		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		}//End main:
		
			
	}//END setUp
	protected void tearDown() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": ############################################### TEARDOWN.";
		    System.out.println(sLog);
		    
			if(objJarAsSource!=null) {
				try {
					objJarAsSource.close();
				} catch (IOException e) {
					ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyUtilZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
					ez.printStackTrace();
					fail("An exception happend testing: " + ez.getDetailAllLast());
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}//END tearDown
	
	public void testComputeTargetDirectoryUsedAsTrunk() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		   
			String sTargetDirectoryPathRoot; String sPathInJarIn; String sFilenameInJarIn;	    
			File fileDirectory;
			
			//VARIANTE 1) Nur das Verzeichnis erstellen... also den reinen Verzeichnis Filter
		    sTargetDirectoryPathRoot = "NichtDaFGL\\FIND_RESOURCE_DIRECTORY_EMPTY";
		    sPathInJarIn = null;
		    sFilenameInJarIn = "bat/KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+" VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "', sPathInJarIn= '" + sPathInJarIn + "', sFilenameInJarIn= '"+ sFilenameInJarIn +"'";
			System.out.println(sLog);
		    fileDirectory = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(sTargetDirectoryPathRoot, sPathInJarIn, sFilenameInJarIn);
		    assertNotNull(fileDirectory);
		    assertFalse(fileDirectory.exists()); //Diese Verzeichnisse sollen NOCH nicht erstellt sein.
		    
		    //VARIANTE 2) Das Verzeichnis mit allen darin enthaltenen Dateien erstellen;
			sTargetDirectoryPathRoot = "NichtDaFGL\\FIND_RESOURCE_FILE02";
			sPathInJarIn = "bat";
			sFilenameInJarIn = "KernelZZZTest_GUIStarter_JarEasyUtil.bat";
			sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "', sPathInJarIn= '" + sPathInJarIn + "', sFilenameInJarIn= '"+ sFilenameInJarIn +"'";
			System.out.println(sLog);
			fileDirectory = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(sTargetDirectoryPathRoot, sPathInJarIn, sFilenameInJarIn);
		    assertNotNull(fileDirectory);
		    assertFalse(fileDirectory.exists()); //Diese Verzeichnisse sollen NOCH nicht erstellt sein.
		    
		    //VARIANTE 3) Nur Pfad angegeben
			sTargetDirectoryPathRoot = "NichtDaFGL\\FIND_RESOURCE_FILE03";
			sPathInJarIn = "bat";
			sFilenameInJarIn = null;
			sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "', sPathInJarIn= '" + sPathInJarIn + "', sFilenameInJarIn= '"+ sFilenameInJarIn +"'";
			System.out.println(sLog);
			fileDirectory = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(sTargetDirectoryPathRoot, sPathInJarIn, sFilenameInJarIn);
		    assertNotNull(fileDirectory);
		    assertFalse(fileDirectory.exists()); //Diese Verzeichnisse sollen NOCH nicht erstellt sein.
		    
		    //VARIANTE 4) 
		    sTargetDirectoryPathRoot = "NichtDaFGL\\FIND_RESOURCE_FILE04";
		    sPathInJarIn = null;
		    sFilenameInJarIn = "KernelZZZTest_GUIStarter_JarEasyUtil.bat";
		    sLog = ReflectCodeZZZ.getPositionCurrent()+"  VARIABLEN: sTargetDirectoryPathRoot= '" + sTargetDirectoryPathRoot + "', sPathInJarIn= '" + sPathInJarIn + "', sFilenameInJarIn= '"+ sFilenameInJarIn +"'";
			System.out.println(sLog);
			fileDirectory = JarEasyUtilZZZ.computeTargetDirectoryUsedAsTrunk(sTargetDirectoryPathRoot, sPathInJarIn, sFilenameInJarIn);
		    assertNotNull(fileDirectory);
		    assertFalse(fileDirectory.exists()); //Diese Verzeichnisse sollen NOCH nicht erstellt sein.		    		    
		    
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 	   
	}
	
	public void testIsJarPathDirectoryValid() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    

		    String sJarPath; boolean bErg;
		    
		    //### 1. nicht mit / anfangen
		    sJarPath="/template";
		    bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
			assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    
			//### 2. nicht mit / anfangen, auch wenn es mit / endet
		    sJarPath="/template/";
		    bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
		    assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    //auch nicht mit mehr als 1x / endend
		    sJarPath="template//";
		    bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
		    assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    //GÜLTIG
		    sJarPath="template/";
		    bErg = JarEasyUtilZZZ.isJarPathDirectoryValid(sJarPath);
			assertTrue("Sollte gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		   					  
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 		
	}
	
	public void testIsJarPathFileValid() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    

		    String sJarPath; boolean bErg;
		    
		    //### 1. nicht mit / anfangen
		    sJarPath="/template.test";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
			assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    
			//### 2. nicht mit / anfangen, auch wenn es mit / endet
		    sJarPath="/template.test/";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
		    assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    //auch nicht mit mehr als 1x / endend
		    sJarPath="template.test//";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
		    assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    		    
		    //auch nicht mit 1x / endend
		    sJarPath="template.test/";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
		    assertFalse("Sollte KEIN gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
		    
		    //Gültig
		    sJarPath="template.test";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
			assertTrue("Sollte gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		    
			
		    //GÜLTIG mit Verzeichnis		    
		    sJarPath="template/template.test";
		    bErg = JarEasyUtilZZZ.isJarPathFileValid(sJarPath);
			assertTrue("Sollte gültiger Verzeichnispfad in JarDatei sein: " + sJarPath, bErg);
		   					  
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 		
	}
	
	public void testGetCodeLocationUsed() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    

			assertNotNull("Im setup sollte die Jar Datei gefunden worden sein.", objJarAsSource);
		    File objFile = JarEasyUtilZZZ.toFile(objJarAsSource);
		    sLog = ReflectCodeZZZ.getPositionCurrent()+": Im Setup gefundene Jar Datei... objFile.getAbsolutePath() = '" + objFile.getAbsolutePath() + "'";
		    System.out.println(sLog);		    
		    assertTrue(objFile.isFile());
		    assertTrue(FileEasyZZZ.isJar(objFile));
			
			
			//Vorab ein Vergleichsdummy holen.		
			if(JarEasyUtilZZZ.isInJarStatic()) { //FALL: IN JAR AUSFÜHREN
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
			   
			    //+++++++++
				//Nun der eigentliche Test
				File objFileTest = JarEasyUtilZZZ.getCodeLocationUsed();
				assertNotNull(objFileTest);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": JAR File as CodeLocationUsed = '" + objFileTest.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFileTest.exists());
			    assertTrue(FileEasyZZZ.isJar(objFileTest));
			    assertTrue(objFileTest.isFile());
				
			    //+++++++++
				//Nun der eigentliche Test
			    assertEquals(objFileTest,objFile);
			    			    
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt, vermutlich eclipse: !IsInJarStatic";
			    System.out.println(sLog);
			    objFile = JarEasyUtilZZZ.getCodeLocationJar();
			    assertNull(objFile);
			    
			    objFile = FileEasyZZZ.getDirectoryOfExecution();			    
			    assertNotNull(objFile);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory of Execution = '" + objFile.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFile.exists());
			    assertFalse(FileEasyZZZ.isJar(objFile));
			    assertFalse(objFile.isFile());
			    
			    
			    //+++++++++
				//Nun der eigentliche Test
				File objFileTest = JarEasyUtilZZZ.getCodeLocationUsed();
				assertNotNull(objFileTest);
			    sLog = ReflectCodeZZZ.getPositionCurrent()+": Directory of Execution as CodeLocationUsed = '" + objFileTest.getAbsolutePath() +"'";
				System.out.println(sLog);
			    assertTrue(objFileTest.exists());
			    assertFalse(FileEasyZZZ.isJar(objFileTest));
			    assertFalse(objFileTest.isFile());
				
			    assertEquals(objFileTest,objFile);
			}
			    
		
			   
		  
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 
	}
	
	
	
	public void testGetJarFileCurrentAsFile() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
			File objFile = null;
			if(JarEasyUtilZZZ.isInJarStatic()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    objFile = JarKernelZZZ.getJarFileCurrentAsFile();
			    
			    assertNotNull(objFile);
			    assertTrue(objFile.isFile());
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);								
			}		    		  		    
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 
	}
	
	public void testGetJarFileUsedAsFile() {
		try {
		String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
	    System.out.println(sLog);
		
		if(JarEasyUtilZZZ.isInJarStatic()) {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": ISinJarStatic.";
		    System.out.println(sLog);			
		}else {
			sLog = ReflectCodeZZZ.getPositionCurrent()+": NOT ISinJarStatic.";
		    System.out.println(sLog);		    
		}
		File objFile = JarKernelZZZ.getJarFileUsedAsFile();
		assertNotNull(objFile);
		assertTrue(objFile.exists());
		assertTrue(objFile.isFile());
		    
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		} 		
	}
	
	public void testIsInSameJar() {
		try {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START ###############################################.";
		    System.out.println(sLog);
		    
		    if(JarEasyZZZ.isInJarStatic()) {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird in einem Jar ausgeführt: IsInJarStatic";
			    System.out.println(sLog);
			    
		    	assertNotNull("Im setup sollte die Jar Datei gefunden worden sein.", objJarAsSource);
				File objFileJarAsSource = JarEasyUtilZZZ.toFile(objJarAsSource);
				sLog = ReflectCodeZZZ.getPositionCurrent()+": A) objFileJarAsSource.getAbsolutePath() = '" + objFileJarAsSource.getAbsolutePath() + "'";
				System.out.println(sLog);
				    		    	
		    	boolean bErg = JarEasyUtilZZZ.isInSameJarStatic(objFileJarAsSource);
		    	assertTrue(bErg);
		    	
		    	//+++ Gegenprobe mit einem Dummy - Jar - Objekt
		    	File objFileJarAsDummy = JarKernelZZZ.getJarFileDummyAsFile();
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": B) objFileJarAsDummy.getAbsolutePath() = '" + objFileJarAsDummy.getAbsolutePath() + "'";
				System.out.println(sLog);
				
				bErg = JarEasyUtilZZZ.isInSameJarStatic(objFileJarAsDummy);
		    	assertFalse(bErg);
		    	
		    }else {
		    	sLog = ReflectCodeZZZ.getPositionCurrent()+": Wird nicht in einem Jar ausgeführt: !IsInJarStatic";
			    System.out.println(sLog);
			    
			    File objFileJar = JarEasyUtilZZZ.getCodeLocationUsed();
			    assertNotNull(objFileJar);
			    assertTrue(objFileJar.isDirectory());
		    }
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
			
	
	
	
	
}//END Class
