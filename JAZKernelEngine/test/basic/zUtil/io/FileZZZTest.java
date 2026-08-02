package basic.zUtil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zBasic.util.system.Syso;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class FileZZZTest extends TestCase {	
	private FileZZZ objFileTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTest.txt");
	
	List<String>listFilePathUsed= null; // Wichtig für das Aufräumen

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
			String sFilePathUsed = null;		
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathUsed = strFILE_DIRECTORY_DEFAULT;
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    
			    sFilePathUsed = sPathEclipse + File.separator + "test";		   
			}
			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, strFILE_NAME_DEFAULT );
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.close();

			//The main object used for testing
			objFileTest = new FileZZZ(sFilePathUsed, strFILE_NAME_DEFAULT, "use_file_expansion");
			
			//Wichtig für das Aufräumen
			listFilePathUsed = new ArrayList<String>(); 
			listFilePathUsed.add(objFileTest.getAbsolutePath());
			
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
	
	public void testFlagZ(){
		try{
		boolean  bExists = objFileTest.proofFlagExists("NIXDA");
		assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
		boolean bSetted = false;
			try{
				bSetted = objFileTest.setFlag("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			
			//TestKonfiguration prüfen
			assertFalse(objFileTest.getFlag("init")==true); //Nun wäre init falsch		
			
			//An object just initialized
			FileZZZ objFileInit = new FileZZZ();
			assertTrue(objFileInit.getFlag("init")==true);
			
			//Flags aus der FileZZZ-Klasse
			assertTrue(objFileTest.getFlag("use_file_expansion")==true);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
	
	/** Test: Join the filepath and a filename undere any circumstances
	 */
	public void testJoinFilePathName(){
		try{		
			String sValue = null;
			
			//Normal case
			sValue = FileEasyZZZ.joinFilePathName("c:\\test", "test.txt");
			assertEquals("c:\\test\\test.txt", sValue);
					
			//Path has the backslash (or more backslashes) at the end
			sValue = FileEasyZZZ.joinFilePathName("c:\\test\\\\", "test.txt");
			assertEquals("c:\\test\\test.txt", sValue);
			
			//An here with more directories
			sValue = FileEasyZZZ.joinFilePathName("c:\\test\\1\\\\", "test.txt");
			assertEquals("c:\\test\\1\\test.txt", sValue);
					
			//Path has to be the first param only (ignore any obsolete backslashes)
			sValue = FileEasyZZZ.joinFilePathName("c:\\test\\\\", "");
			assertEquals("c:\\test", sValue);
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	/** Test: Splitting the filename: 
	 * .NameOnly
	 * .NameEnd
	 * 
	 */
	public void testGetPathDetailAll(){
		try{
		assertEquals("JUnitTest", objFileTest.getNameOnly());
		assertEquals("txt", objFileTest.getNameEnd());
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}

	/** Test: Computing possible expansions of the filename-ending
	 * 
	 */
	public void testPathNameExpansion(){
		try{
				//First, no file expected to be there or only one file is there
				assertEquals("", objFileTest.searchExpansionCurrent());
				
				// unverändert ?
				assertEquals("", objFileTest.searchExpansionCurrent());
				
				//im Konstrukutor des FileExpansion objekts wird das Flag FILE_CURRENT_FOUND gesetzt. Darum...
				//3stellig
				assertEquals("001", objFileTest.searchExpansionFreeNext());
				
				//Wenn wir so tun, als gäbe es die Ausgangsdatei nicht, gilt aber wieder
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, false);
				assertEquals("", objFileTest.searchExpansionFreeNext());
				
				
				//#########################################
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, true);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND, false);
				assertEquals("", objFileTest.searchExpansionCurrent()); //Da es die Datei nicht gibt, bleibt es beim Wert
				assertEquals("001", objFileTest.searchExpansionFreeNext());//Da es die Datei laut Flag gibt, kommt ein Wert drauf.
								
				//Erst mit dem Flag, dass kennzeichnet, das angehängt werden soll wird der Name erweitert.
				//Merke: Das passiert dann, wenn die Ausgangsdatei tatsächlich existiert
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, false);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND, true);
				assertEquals("000", objFileTest.searchExpansionCurrent());
				assertEquals("001", objFileTest.searchExpansionFreeNext()); //Da es die Datei nicht gibt, bleibt es beim Wert
				
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, true);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND, true);
				assertEquals("001", objFileTest.searchExpansionCurrent()); //Da es die Datei nicht gibt, bleibt es beim Wert
				assertEquals("002", objFileTest.searchExpansionFreeNext());//Da es die Datei laut Flag gibt, kommt ein Wert drauf.
				
				objFileTest.getFileExpansionObject().setExpansionValueCurrent(2);
				assertEquals("002", objFileTest.searchExpansionCurrent());//Da es die Datei laut Flag gibt, kommt ein Wert drauf.
				assertEquals("003", objFileTest.searchExpansionFreeNext());//Da es die Datei laut Flag gibt, kommt ein Wert drauf.
				
				//######################################################################################
				//4stelling: Merke: Das dauer wg. der Suche der Dateinamen von 9999 bis 0000 lange....
				objFileTest.setExpansionLength(4);
				objFileTest.getFileExpansionObject().setExpansionValueCurrent(0);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, false);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), false);
				assertEquals("",objFileTest.searchExpansionCurrent());
				
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, false);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), true);
				assertEquals("0000",objFileTest.searchExpansionCurrent());		
				assertEquals("0001",objFileTest.searchExpansionFreeNext()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
				
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), true);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), true);
				assertEquals("0001",objFileTest.searchExpansionCurrent()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
				assertEquals("0002",objFileTest.searchExpansionFreeNext()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
				
				//##################################################################
				objFileTest.setExpansionFilling('-');
				objFileTest.setExpansionLength(4);
				objFileTest.getFileExpansionObject().setExpansionValueCurrent(0);
				assertEquals("-", objFileTest.getExpansionFilling());
				
				//	Now get the next expansion
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND.name(), false);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), false);
				assertEquals("",objFileTest.searchExpansionCurrent());
				assertEquals("",objFileTest.searchExpansionFreeNext());	
				
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND, false);
				objFileTest.getFileExpansionObject().setFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_EXPANSION_APPEND.name(), true);
				assertEquals("---0",objFileTest.searchExpansionCurrent());		
				assertEquals("---1",objFileTest.searchExpansionFreeNext()); //Nur wenn es die Datei nicht gibt, bleibt es beim Wert
				
				objFileTest.setExpansionLength(3);
				assertEquals("--1",objFileTest.searchExpansionFreeNext());
				
				objFileTest.getFileExpansionObject().setExpansionValueCurrent(2);
				assertEquals("--3",objFileTest.searchExpansionFreeNext());
				
				
				//#### 
				objFileTest.getFileExpansionObject().setExpansionValueCurrent(0);
				objFileTest.setExpansionLength(3);
				String sDirectory = objFileTest.getParent();
				String sReturn = objFileTest.PathNameTotalExpandedNextCompute(null, null);
				assertEquals( sDirectory + File.separator + "JUnitTest--1.txt", sReturn);
				//System.out.println("the expanded filename: '" + sReturn + "'");
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	
	}
	
	/** void, Tests: Validating the result of computing a expansion. 
	 * a) Computing the biggest expansion number which could be created, by a given number of digits 
	* Lindhauer; 21.04.2006 09:51:08
	 */
	public void testExpansionLookalike(){
		try {
			//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
			//Zu beachten ist, das die Funktion einen String zur�ckliefert.
			
			assertEquals("99",FileExpansionZZZ.getExpansionMax(2));		
			assertEquals("999",FileExpansionZZZ.getExpansionMax(3));
			assertEquals("9999",FileExpansionZZZ.getExpansionMax(4));
			assertEquals("99999",FileExpansionZZZ.getExpansionMax(5));
			
			//Hier wird eine F�llvariable zur Berechnung verwendet.
			//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
			objFileTest.setExpansionLength(0);
			assertEquals("",objFileTest.ExpansionCompute("0",2));
			
			objFileTest.setExpansionLength(1);
			assertEquals("2",objFileTest.ExpansionCompute("0",2));
			
			objFileTest.setExpansionLength(3);
			assertEquals("002",objFileTest.ExpansionCompute("0",2));
			
			objFileTest.setExpansionLength(4);
			assertEquals("0002",objFileTest.ExpansionCompute("0",2));
			
			//Falls ein anderes F�llzeichen �bergeben werden soll
			// Hier 3 Unterstriche vor der Ziffer
			assertEquals("___2",objFileTest.ExpansionCompute("_",2));
			
			// Hier 2 Unterstriche und die Zahl ist 2 stellig
			assertEquals("__32",objFileTest.ExpansionCompute("_",32));
			
			//Hier kein Unterstrich und die Zahl ist 4 stellig
			assertEquals("4321",objFileTest.ExpansionCompute("_",4321));
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
}
