package basic.zBasic.util.file;


import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import junit.framework.TestCase;

public class FileEasyConstantConverterZZZTest extends TestCase{
	//private File objFileJarAsSource=null;
	//private JarFile objJarAsSource=null;
	//private String sTargetDirPath=null;
	
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
//				//objJarAsSource.close();
//			} catch (IOException e) {
//				ExceptionZZZ ez  = new ExceptionZZZ("Beim tearDown - IOExcepiton: " + e.getMessage(), ExceptionZZZ.iERROR_RUNTIME, JarEasyZZZTest.class.getName(), ReflectCodeZZZ.getMethodCurrentName());				
//				ez.printStackTrace();
//				fail("An exception happend testing: " + ez.getDetailAllLast());
//			}
//		}
	}//END tearDown
	
	public void testConvertFilePath() {		
		try {
			char cDirectorySeparator = IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR;
			String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
			
			String sFilePathIn = null;
			IFileEasyPathObjectZZZ objPath = null;
			String sRoot=null;
			String sFilePath=null;
			String sFilePathTotal=null;

			//++++++++++++++++++++++++++++
			//+++ Relative Pfade
			//++++++++++++++++++++++++++++
			
			//-- Einzelwert Z-Formula oder leer oder null
			sFilePathIn = null; //Projektebene
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertTrue(FileEasyZZZ.isPathAbsolute(sFilePath));
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals(sFilePath,sFilePathTotal);
			
			//+++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = ""; //src - Ordner
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("src",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("src",sFilePathTotal);
			
			
			//-- Einzelwert
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "src";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("src",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("src",sFilePathTotal);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "test";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("test",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("test",sFilePathTotal);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "tryout";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("tryout",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("tryout",sFilePathTotal);
			
			//-- Mit tieferer Verschachtelung
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "src\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("src",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("src\\directory\\testNotExisting.txt",sFilePathTotal);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "test\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("test",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("test\\directory\\testNotExisting.txt",sFilePathTotal);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sFilePathIn = "tryout\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("tryout",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals("tryout\\directory\\testNotExisting.txt",sFilePathTotal);
			
			
			
			//+++++++++++++++++++++++++++++++++++++
			//als absoluter Pfad
			sFilePathIn = sWorkspace + "\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);	
			
			//Neuen Root (absolut) checken
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("",sRoot); //Wenn also der absolute Pfad uebergeben wurde, ist kein Root hinzuzurechnen.
					
			sFilePathTotal=objPath.getFilePathTotal();	
			//System.out.println("sFilePathTotal=" + sFilePathTotal);
			assertNotNull(sFilePathTotal);
			assertEquals(sWorkspace + "\\directory\\testNotExisting.txt",sFilePathTotal);
			
			sFilePath=objPath.getFilePath();
			System.out.println("sFilePath=" + sFilePath);
			assertNotNull(sFilePath);
			assertEquals(sWorkspace + "\\directory\\testNotExisting.txt",sFilePath);
			

			
			//++++++++++++++++++++++++++++
			//+++ Absolute Pfade
			//++++++++++++++++++++++++++++
			
			//-- ohne Workspacebezug
			sFilePathIn = "c:\\tryout\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals(sFilePathIn,sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals(sFilePathIn,sFilePathTotal);
			
			//-- mit Workspacebezug... Ergebnis als absoluter Pfad			
			sFilePathIn = sWorkspace + "\\" + "tryout\\directory\\testNotExisting.txt";
			
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("",sRoot); //Wenn also der absolute Pfad uebergeben wurde, ist kein Root hinzuzurechnen.
			
			sFilePath=objPath.getFilePath(); //wenn der String von .getRootAdditionalComputed() leer ist, dann kommt hier auch der absolute Pfad raus.			
			assertNotNull(sFilePath);						
			assertEquals(sFilePathIn,sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals(sFilePathIn,sFilePathTotal);
			
			
			//-- mit Workspacebezug... Ergebnis als relativer Pfad (erzwungen per true Parameter)
			sFilePathIn = sWorkspace + "\\" + "tryout\\directory\\testNotExisting.txt";
			
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn,true);
			assertNotNull(objPath);			
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("tryout",sRoot);
			
			sFilePath=objPath.getFilePath();			
			assertNotNull(sFilePath);
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			sFilePathTotal=objPath.getFilePathTotal();			
			assertNotNull(sFilePathTotal);
			assertEquals(sRoot+CharZZZ.toString(cDirectorySeparator)+sFilePath,sFilePathTotal);

		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testConvertFilePathAsAbsolute() {		
		try {
			char cDirectorySeparator = IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR;
			String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
			
			String sFileDirectorySource="test";
			String sFileNameSource="ZKernelConfigKernel_test.ini";
			
			IFileEasyPathObjectZZZ obj = FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFileDirectorySource);
			String sValue = obj.getFilePathTotal();
			assertEquals(sWorkspace + "\\test", sValue);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testConvertFilePathWithPlaceholder() {		
		try {
			char cDirectorySeparator = IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR;
			String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
			
			String sFilePathIn = null;
			IFileEasyPathObjectZZZ objPath = null;
			String sRoot=null;
			String sFilePath=null;
			String sFilePathTotal=null;

			
			//+++++++++++++++++++++++++++
			//+++ Relative Pfade mit Platzhalter voran:
			//+++++++++++++++++++++++++++
			
			//mit dem Parameter "true"= Return as relative path
			sFilePathIn = "<Z:NULL/>\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn, true);
			assertNotNull(objPath);	
			
			//Neuen Root (relativ) checken
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals(sWorkspace,sRoot);
											
			sFilePathTotal=objPath.getFilePathTotal();
			//System.out.println("sFilePathTotal=" + sFilePathTotal);
			assertNotNull(sFilePathTotal);						
			assertEquals(sWorkspace + "\\directory\\testNotExisting.txt",sFilePathTotal);	
			
			sFilePath = objPath.getFilePath();
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			
			//++++++++++++++++++++++++++++++++++++++++			
			//als relativer Pfad uebergeben, wird zu einem relativen Pfad.		
			//NULL
			sFilePathIn = "<Z:NULL/>\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);	
			
			//Neuen Root (relativ) checken
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals(sWorkspace,sRoot);
											
			sFilePathTotal=objPath.getFilePathTotal();
			assertNotNull(sFilePathTotal);						
			assertEquals(sWorkspace + "\\directory\\testNotExisting.txt",sFilePathTotal);	
			
			sFilePath = objPath.getFilePath();
			assertEquals("directory\\testNotExisting.txt",sFilePath);
			
			//++++++++++++++++++++++++++++++++++++++++
			//als relativer Pfad uebergeben, wird zu einem relativen Pfad.		
			//EMPTY			
			sFilePathIn = "<Z:EMPTY/>\\directory\\testNotExisting.txt";
			objPath = FileEasyConstantConverterZZZ.convertFilePath(sFilePathIn);
			assertNotNull(objPath);	
			
			//Neuen Root (relativ) checken
			sRoot= objPath.getRootAdditionalComputed();			
			assertNotNull(sRoot);
			assertEquals("src",sRoot); //Der Projekt-Root, relativ
			
			sFilePath = objPath.getFilePath();
			assertEquals("directory\\testNotExisting.txt",sFilePath);
						
			sFilePathTotal=objPath.getFilePathTotal();
			assertNotNull(sFilePathTotal);						
			assertEquals(sRoot + "\\directory\\testNotExisting.txt",sFilePathTotal);	
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
}
