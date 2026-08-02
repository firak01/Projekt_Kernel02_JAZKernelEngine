package basic.zBasic.util.file.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zBasic.util.system.Syso;

public class TxtReaderZZZTest  extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_SORTED_NAME_DEFAULT = new String("JUnitTest_sorted.txt");
	private final static String strFILE_UNSORTED_NAME_DEFAULT = new String("JUnitTest_unsorted.txt");
	private final static String strFILE_EMPTY_NAME_DEFAULT = new String("JUnitTest_empty.txt");
	
	
	private File objFileSorted;
	private File objFileUnsorted;
	private File objFileEmpty;
	
	private String sLineFirstForTest=null;
	private String sLineSecondForTest=null;
	
	/// +++ Die eigentlichen Test-Objekte
	private TxtReaderZZZ objReaderInit;
	private TxtReaderZZZ objReaderSorted;
	private TxtReaderZZZ objReaderUnsorted;
	private TxtReaderZZZ  objReaderEmpty;
	
	//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufraeumen im tearDown().
	List<String>listFilePathUsed= null; // Wichtig für das Aufräumen
	
	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehängt.
			//Merke: Es soll nicht versucht werden die Datei z.B. mit der Datei aus dem FileIniZZZTest 
			//Merke: Erst wenn es �berhaupt einen Test gibt, wird diese Datei erstellt
			String sFileSortedPathTotal = null;
			String sFileUnsortedPathTotal = null;
			String sFileEmptyPathTotal = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFileSortedPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_SORTED_NAME_DEFAULT );
				sFileUnsortedPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_UNSORTED_NAME_DEFAULT );
				sFileEmptyPathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_EMPTY_NAME_DEFAULT );
			}else{
				//Eclipse Worspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
				
			    sFileSortedPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_SORTED_NAME_DEFAULT );
				sFileUnsortedPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_UNSORTED_NAME_DEFAULT );
				sFileEmptyPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_EMPTY_NAME_DEFAULT );
			}
			
			sLineFirstForTest = ";This is a temporarily test file for TxtWriterZZZTest.";
			sLineSecondForTest = "#This text has sorted lines. Comment lines should be ignored.";
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFileSortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(sLineFirstForTest);      //Now the File is created. This is a comment line
			objStreamFile.println(sLineSecondForTest);
			objStreamFile.println("A Line");
			objStreamFile.println("B Line");			
			objStreamFile.println("C Line");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("D Line");
			objStreamFile.println("  ");  //empty line 
			objStreamFile.println("E Line");			
			objStreamFile.close();
			
			objStreamFile = new StreamZZZ(sFileUnsortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for TxtWriterZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println("#This text has unsorted lines. Comment lines should be ignored and when sorted: placed to the beginning.");
			objStreamFile.println("A Line original unsorted");			
			objStreamFile.println("C Line original unsorted");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("F Line original unsorted");
			objStreamFile.println("B Line original unsorted");			
			objStreamFile.println("D Line original unsorted");
			objStreamFile.println("  ");  //empty line 
			objStreamFile.println("E Line original unsorted");		
			objStreamFile.close();
			
			objStreamFile = new StreamZZZ(sFileEmptyPathTotal, 1);
			objStreamFile.close();
			
			objFileSorted = new File(sFileSortedPathTotal);
			objFileUnsorted = new File(sFileUnsortedPathTotal);
			objFileEmpty = new File(sFileEmptyPathTotal);
						
			//Wichtig für das Aufräumen
			listFilePathUsed = new ArrayList<String>(); 
			listFilePathUsed.add(objFileSorted.getAbsolutePath());
			listFilePathUsed.add(objFileUnsorted.getAbsolutePath());
			listFilePathUsed.add(objFileEmpty.getAbsolutePath());
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objReaderInit = new TxtReaderZZZ(); 
			
			//The main objects used for testing
			String[] saFlag = {"IsFileSorted", "IgnoreCommentLine", "IgnoreEmptyLine"};
			objReaderSorted = new TxtReaderZZZ(objFileSorted, saFlag);
			objReaderUnsorted = new TxtReaderZZZ(objFileUnsorted);
			objReaderEmpty = new TxtReaderZZZ(objFileEmpty, (String) null);
												
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
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
			try {
				if(this.objReaderSorted!=null)	this.objReaderSorted.close();				
				if(this.objReaderUnsorted!=null)	this.objReaderUnsorted.close();
				if(this.objReaderEmpty!=null)	this.objReaderEmpty.close();				
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ(ioe);
				throw ez;
			}
			
			if(doCleanup) {
				if(listFilePathUsed!=null) {
					for(String sFilePath : listFilePathUsed) {
						Syso.println("Lösche Datei: '" + sFilePath + "'");
						boolean btemp = FileEasyZZZ.removeFile(sFilePath);
						if(!btemp) {
							Syso.println("Konnte Datei: '" + sFilePath + "' nicht erfolgreich löschen.");
						}
					}
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testFlagZ(){
		try{
		boolean bSetted = false;
			try{
				bSetted = objReaderEmpty.setFlag("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			boolean  bExists = objReaderEmpty.proofFlagExists("NIXDA");
			assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
			
			//++++++++++++
			bExists = objReaderSorted.proofFlagExists("IsFileSorted");
			assertTrue("Object should have FlagZ '" + TxtReaderZZZ.FLAGZ.IsFileSorted + "'",bExists);
			
			bExists = objReaderSorted.proofFlagExists("NIXDA");
			assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
			boolean btemp = objReaderSorted.getFlag("IsFileSorted");
			assertTrue("Object should have FlagZ '" + TxtReaderZZZ.FLAGZ.IsFileSorted + "' set to true", btemp);
			
			//+++++++++++++++++
			btemp = objReaderUnsorted.getFlag("IsFileSorted");
			assertFalse("Object should NOT have FlagZ '" + TxtReaderZZZ.FLAGZ.IsFileSorted + "' set to true", btemp);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
	
	public void testReadPositionLineFirst(){
		try{
			long lPosition = objReaderUnsorted.readPositionLineFirst("A Line original unsorted", 0);
			assertTrue(lPosition >= 1);
			
			lPosition = objReaderSorted.readPositionLineFirst("A Line original unsorted", 0);
			assertFalse(lPosition >= 1);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testReadLine(){
		try{
			long lStartPosition = this.sLineFirstForTest.length() + 2;  //+2 wg CR + LF
			
			String sLine = objReaderSorted.readLineByByte(lStartPosition);
			assertEquals(sLine, this.sLineSecondForTest);
			
			lStartPosition = this.sLineFirstForTest.length();
			sLine = objReaderSorted.readLineNextByByte(lStartPosition);
			assertEquals(sLine, this.sLineSecondForTest);
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Receiving the content of an .ini-section
	* Lindhauer; 23.04.2006 09:13:04
	 */
	public void testReadVectorString(){
		try{
			long lStartByte = 0;
			
			//This will test the reading of text
			Vector vec = objReaderEmpty.readVectorStringByByte(lStartByte);
			assertTrue(vec.size()==0);
			
			vec.clear();
			vec = objReaderSorted.readVectorStringByByte(lStartByte);
			assertTrue(vec.size() == 5);
			
			vec.clear();
			vec = objReaderUnsorted.readVectorStringByByte(lStartByte);
			assertTrue(vec.size() == 10);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
}//end class