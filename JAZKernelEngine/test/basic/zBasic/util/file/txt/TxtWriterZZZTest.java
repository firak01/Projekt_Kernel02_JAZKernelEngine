package basic.zBasic.util.file.txt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zBasic.util.file.txt.bytes.TxtWriterZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;

public class TxtWriterZZZTest  extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_SORTED_NAME_DEFAULT = new String("JUnitTest_sorted.txt");
	private final static String strFILE_UNSORTED_NAME_DEFAULT = new String("JUnitTest_unsorted.txt");
	private final static String strFILE_EMPTY_NAME_DEFAULT = new String("JUnitTest_empty.txt");
	
	
	private File objFileSorted;
	private File objFileUnsorted;
	private File objFileEmpty;
	
	/// +++ Die eigentlichen Test-Objekte
	private TxtWriterZZZ objWriterInit;
	private TxtWriterZZZ objWriterSorted;
	private TxtWriterZZZ objWriterUnsorted;
	private TxtWriterZZZ  objWriterEmpty;
	

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
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
				
			    sFileSortedPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_SORTED_NAME_DEFAULT );
				sFileUnsortedPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_UNSORTED_NAME_DEFAULT );
				sFileEmptyPathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_EMPTY_NAME_DEFAULT );
			}
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFileSortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for TxtWriterZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println("#This text has sorted lines. Comment lines should be ignored.");
			objStreamFile.println("A Line");
			objStreamFile.println("B Line");			
			objStreamFile.println("C Line");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("D Line");
			objStreamFile.println("    ");   //Die Leerzeile soll auch ignoriert werden
			objStreamFile.println("E Line");			
			objStreamFile.println("F Line");
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
			objStreamFile.println("E Line original unsorted");		
			objStreamFile.close();
			
			objStreamFile = new StreamZZZ(sFileEmptyPathTotal, 1);
			objStreamFile.close();
			
			
		
			
			objFileSorted = new File(sFileSortedPathTotal);
			objFileUnsorted = new File(sFileUnsortedPathTotal);
			objFileEmpty = new File(sFileEmptyPathTotal);
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objWriterInit = new TxtWriterZZZ(); 
			
			//The main objects used for testing
			//String[] saFlag = {"IsFileSorted"};
			//String[] saFlag = {"IsFileSorted", "TopOfCommentLine"};
			//String[] saFlag = {"IsFileSorted", "TopOfEmptyLine"};
			//String[] saFlag = {"IsFileSorted", "IgnoreCase"};
			String[] saFlag = {"IsFileSorted", "IgnoreCase", "TopOfEmptyLine", "TopOfCommentLine"};
			objWriterSorted = new TxtWriterZZZ(objFileSorted, saFlag);
			objWriterUnsorted = new TxtWriterZZZ(objFileUnsorted);
			objWriterEmpty = new TxtWriterZZZ(objFileEmpty, null);
			
			
			
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
	
	//*
	public void testAppendVectorString(){
		try{
			Vector vec = new Vector();
			vec.add("1 appended");
			vec.add("2 appended");
			vec.add("3 appended");
			
			objWriterEmpty.appendVectorString(vec);
			objWriterSorted.appendVectorString(vec);   //??? es ist nun fraglich, ob hier die Werte einfach so angeh�ngt werden sollen.
			objWriterUnsorted.appendVectorString(vec);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} //*/
	
	public void testInsertLine(){
		try{
			//This will test the insertion of text
			
			//++++++++++++++++++++++++++++++++++++++
			long lByte = objWriterEmpty.insertLine("D text inserted");
			assertTrue(lByte >= 0);
			
			lByte = objWriterEmpty.insertLine("C text inserted");
			assertTrue(lByte >= 0);
			
			//+++++++++++++++++++++++++++++++++++++++
			lByte = objWriterSorted.insertLine("E a text to be inserted");
			assertTrue(lByte >= 0);
			
			lByte = objWriterSorted.insertLine("D a text to be inserted");
			assertTrue(lByte >= 0);
			
			lByte = objWriterSorted.insertLine("C a text to be inserted");
			assertTrue(lByte >= 0);
			
			
			//+++++++++++++++++++++++++++++++++++++++
			lByte = objWriterUnsorted.insertLine("D text inserted");
			assertTrue(lByte >= 0);
			lByte = objWriterUnsorted.insertLine("C text inserted");
			assertTrue(lByte >= 0);
		
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testRemoveLine(){
		try{
			//This will test the remove of text
			
			//++++++++++++++++++++++++++++++++++++++
			long lByte = objWriterEmpty.removeLineFirst("D Line", 0);
			assertTrue(lByte <= -1);
					
			 lByte = objWriterEmpty.removeLineFirst("D Line", 10);
			assertTrue(lByte <= -1);
				
			//+++++++++++++++++++++++++++++++++++++++
			lByte = objWriterSorted.removeLineFirst("D Line", 0);
			assertTrue(lByte >= 0);
			
			lByte = objWriterSorted.removeLineFirst("D Line", lByte);
			assertTrue(lByte <= -1);
			
			
			lByte = objWriterSorted.removeLineFirst("F Line", 0);
			assertTrue(lByte >= 0);
			
			
			//+++++++++++++++++++++++++++++++++++++++
			lByte = objWriterUnsorted.removeLineFirst("E Line original unsorted",0);
			assertTrue(lByte >= 0);
			lByte = objWriterUnsorted.removeLineFirst("C Line original unsorted",0);
			assertTrue(lByte >= 0);
		
					
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	

	
}//end class
