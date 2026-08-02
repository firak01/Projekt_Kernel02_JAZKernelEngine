package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.parser.FileTextParserZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zBasic.util.system.Syso;

public class FileTextParserZZZTest extends TestCase{
	private File objFileSource=null;
	private File objFileTarget=null;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitKernelFileParseTest.txt");
	private final static String strFILE_NAME_DEFAULT_RESULT = new String("JUnitKernelFileParseResult.txt");

	
	private FileTextParserZZZ objParserTest = null;
	
	List<String>listFilePathUsed= null; // Wichtig für das Aufräumen

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
			//Merke: Dadurch, dass man die Datei per stream - aufbaut, werden keine Einträge an eine vorherige Datei angehängt, sondern die Datei wird für jeden Test neu geschrieben.			
			String sFilePathTotal = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
			}
			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.println("erste zeile");      //Now the File is created
			objStreamFile.println("zweite zeile");   
			objStreamFile.println("dritte zeile");    
			objStreamFile.println("vierte zeile");   
			objStreamFile.println("fünfte zeile");     
			objStreamFile.println("sechste zeile");      
			objStreamFile.println("sechste zeile ist doppelt");     
			objStreamFile.println("doppelte sechste zeile");     
			objStreamFile.println("erste zeile ist doppelt");  
			objStreamFile.println("doppelte erste zeile");    
			objStreamFile.println("noch eine doppelte erste zeile");   
			objStreamFile.close();			
			this.objFileSource = new File(sFilePathTotal);
			
						
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT_RESULT );
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT_RESULT );			   
			}						
			this.objFileTarget = new File(sFilePathTotal);
			
			//Wichtig für das Aufräumen
			listFilePathUsed = new ArrayList<String>(); 
			listFilePathUsed.add(objFileSource.getAbsolutePath());
			listFilePathUsed.add(objFileTarget.getAbsolutePath());
			
			//The main object used for testing
			objParserTest = new FileTextParserZZZ(objFileSource, (String[]) null);
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	 

	@Override
	protected void tearDown() {
		try {
			if(listFilePathUsed!=null) {
				for(String sFilePath : listFilePathUsed) {
					Syso.println("Lösche Datei: '" + sFilePath + "'");
					boolean btemp = FileEasyZZZ.removeFile(sFilePath);
					if(!btemp) {
						Syso.println("Konnte Datei: '" + sFilePath + "' nicht erfolgreich löschen.");
					}
				}
			}
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testConstructor(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			FileTextParserZZZ objParserInit = new FileTextParserZZZ(null, saFlag);
			assertTrue(objParserInit.getFlag("init")==true); 
			
			
			//TestKonfiguration pr�fen
			assertFalse(objParserTest.getFlag("init")==true); //Nun w�re init falsch
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testLineNumberAll(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			Integer[] intaLine = objParserTest.readLineNumberAll(objRe);
			assertNotNull(intaLine);
			assertEquals(intaLine.length, 2);
			
			
			RE objRe2 = new RE(s2Search);
			Integer[] intaLine2 = objParserTest.readLineNumberAll(objRe2);
			assertNotNull(intaLine2);
			assertEquals(intaLine2.length, 4);
			
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testReplaceLine(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			int iLineReplaced = objParserTest.replaceLine(this.objFileTarget, objRe, "das war ein Test");
			assertEquals(2,iLineReplaced);
			
			RE objRe2 = new RE("^das war ein Test");
			iLineReplaced = objParserTest.replaceLine(this.objFileTarget, objRe2, "das war ein weiterer Test");
			assertEquals(2, iLineReplaced);
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	
	public void testHasLine(){
		String s2Search="erste";		
		try {
			RE objRe = new RE("^" + s2Search);
			boolean btemp = objParserTest.hasLine(objRe);
			assertTrue(btemp);
			
			objRe = new RE("^DAS GIBT ES NICHT");
			btemp = objParserTest.hasLine(objRe);
			assertFalse(btemp);
			
			//###################################
			btemp = objParserTest.hasLine("erste zeile", false);
			assertTrue(btemp);
			
			btemp = objParserTest.hasLine("das gibt es nicht", false);
			assertFalse(btemp);
			
		} catch (RESyntaxException e) {
			fail("An 'regular expression syntax' exception happend: " + e.getMessage());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testAppendLine(){
		String s2Append = "Das soll die letzte Zeile werden";
		try{
			long ltemp = objParserTest.appendLine(objFileTarget, s2Append);
			assertTrue(ltemp >= 2); //Weil eine Zeil ja schon vorher drin stehen soll
			
			long ltemp2 = objParserTest.appendLine(objFileTarget, "Nein, das hier ist jetzt das allerletzte");
			assertTrue(ltemp2 > ltemp); //Weil eine Zeil ja schon vorher drin stehen soll
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}//END Class
