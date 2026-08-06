package basic.zBasic.util.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.KernelZZZTest;
import junit.framework.TestCase;

public class FileEasyCopyZZZTest extends TestCase{
	public final static String strFILE_DIRECTORY_SOURCE = "ressourceZZZFileEasyCopyTest";
	public final static String strFile_DIRECTORY_DEFAULT_EXTERNAL = KernelZZZTest.strFILE_DIRECTORY_DEFAULT_EXTERNAL;
	public final static String strFILE_DIRECTORY_TARGET = FileEasyCopyZZZTest.strFILE_DIRECTORY_SOURCE;

	
	protected void setUp(){
		try {		
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": SETUP ###############################################.";
		    System.out.println(sLog);
		    			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}			
	}//END setUp
	protected void tearDown() {
//		if(objJarAsSource!=null) {
//			try {
//				objJarAsSource.close();
//			} catch (IOException e) {
//				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
//				ez.printStackTrace();
//				fail("An exception happend testing: " + ez.getDetailAllLast());
//			}
//		}
	}//END tearDown
	
	public void testCopyFile() {
		try {
			String sDirectorySource = FileEasyZZZ.joinFilePathName("test", FileEasyCopyZZZTest.strFILE_DIRECTORY_SOURCE);
			String sFilenameSource = "Datei01.txt";
			String sDirectoryTarget = FileEasyZZZ.joinFilePathName(FileEasyCopyZZZTest.strFile_DIRECTORY_DEFAULT_EXTERNAL, FileEasyCopyZZZTest.strFILE_DIRECTORY_TARGET);
			String sFilenameTarget=null;
			File objFileCreated = FileEasyCopyZZZ.copyFile(sDirectorySource, sFilenameSource, sDirectoryTarget, sFilenameTarget, true);
			assertNotNull(objFileCreated);
			
			boolean bFileExists = FileEasyZZZ.exists(objFileCreated);
			assertTrue(bFileExists);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
	public void testCopyFileByBatch() {
		try {
			String sDirectorySource = FileEasyZZZ.joinFilePathName("test", FileEasyCopyZZZTest.strFILE_DIRECTORY_SOURCE);
			String sFilenameSource = "Datei01.txt";
			String sDirectoryTarget = FileEasyZZZ.joinFilePathName(FileEasyCopyZZZTest.strFile_DIRECTORY_DEFAULT_EXTERNAL, FileEasyCopyZZZTest.strFILE_DIRECTORY_TARGET);
			String sFilenameTarget=null;
			File objFileCreated = FileEasyCopyZZZ.copyFileByBatch(sDirectorySource, sFilenameSource, sDirectoryTarget, sFilenameTarget, true);
			assertNotNull(objFileCreated);
			
			boolean bFileExists = FileEasyZZZ.exists(objFileCreated);
			assertTrue(bFileExists);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
