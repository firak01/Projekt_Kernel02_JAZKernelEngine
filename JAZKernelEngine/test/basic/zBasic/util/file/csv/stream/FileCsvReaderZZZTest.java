package basic.zBasic.util.file.csv.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zBasic.util.system.Syso;

public class FileCsvReaderZZZTest  extends TestCase{
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_SORTED_NAME_DEFAULT = new String("JUnitTest_sorted.csv");
	private final static String strFILE_UNSORTED_NAME_DEFAULT = new String("JUnitTest_unsorted.csv");
	private final static String strFILE_EMPTY_NAME_DEFAULT = new String("JUnitTest_empty.csv");
	
	
	private File objFileSorted;
	private File objFileEmpty;
	
	private String sLineFirstForTest=null;
	private String sLineSecondForTest=null;
	
	/// +++ Die eigentlichen Test-Objekte
	@SuppressWarnings("rawtypes")
	private FileCsvReaderZZZ objReaderInit;
	@SuppressWarnings("rawtypes")
	private FileCsvReaderZZZ objReaderSorted;
		
	//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufraeumen im tearDown().
	List<String>listFilePathUsed= null; // Wichtig für das Aufräumen
	
	@SuppressWarnings("rawtypes")
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
			
			sLineFirstForTest = ";This is a temporarily test file for FileCsvReaderZZZTest.";
			sLineSecondForTest = "#This text has sorted lines. Comment lines should be ignored.";
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFileSortedPathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(sLineFirstForTest);      //Now the File is created. This is a comment line
			objStreamFile.println(sLineSecondForTest);
			objStreamFile.println("'A Header';'B Header';'C IntHeader'");
			objStreamFile.println("'1A Line';'1B Line';1");
			objStreamFile.println("'2A Line';'2B Line';2");			
			objStreamFile.println("'3A Line';'3B Line';3");
			objStreamFile.println(";Comment line to be ignored.");
			objStreamFile.println("'4A Line';'4B Line';4");
			objStreamFile.println("  ");  //empty line to be ignored 
			objStreamFile.println("'5A Line';'5B Line';5");			
			objStreamFile.close();
			
			
			
			objFileSorted = new File(sFileSortedPathTotal);
			
			
			//Wichtig für das Aufräumen
			listFilePathUsed = new ArrayList<String>(); 
			listFilePathUsed.add(objFileSorted.getAbsolutePath());
			
			
			//### Die TestObjecte
			
			//An object just initialized, only for writing
			objReaderInit = new FileCsvReaderZZZ(); 
			
			//The main objects used for testing
			String[] saFlag = {"IsFileSorted", "IgnoreCommentLine", "IgnoreEmptyLine"};
			objReaderSorted = new FileCsvReaderZZZ(objFileSorted, saFlag);
															
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
				bSetted = objReaderInit.setFlag("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			boolean  bExists = objReaderInit.proofFlagExists("NIXDA");
			assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
			
			//++++++++++++
			bExists = objReaderSorted.proofFlagExists("IsFileSorted");
			assertTrue("Object should have FlagZ '" + TxtReaderZZZ.FLAGZ.IsFileSorted + "'",bExists);
			
			bExists = objReaderSorted.proofFlagExists("NIXDA");
			assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
			boolean btemp = objReaderSorted.getFlag("IsFileSorted");
			assertTrue("Object should have FlagZ '" + TxtReaderZZZ.FLAGZ.IsFileSorted + "' set to true", btemp);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
	
	public void testParseLine(){
		try{
			String sLine;
			
			sLine="'a';'b'";
			Vector<String> vecValue = FileCsvReaderZZZ.parseLine(sLine,';');
			assertNotNull(vecValue);
			
			//wohl mit komma
			sLine="'a','b'";
			String[]saValue = FileCsvReaderZZZ.parseCsvLine(sLine);
			assertNotNull(saValue);
			
			sLine="'a','b'";
			List<String>listaValue = FileCsvReaderZZZ.parseCsvLineAsList(sLine);
			assertNotNull(listaValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testParseNextLineAsHashTable_byLoad(){
		try{
			
			//boolean bSuccess  = objReaderSorted.load();
			Hashtable<String,String> hsCsv = null;
			Set<String> setHeader = null;
			int iLine=-1;
			int iColumn=-1;
			
			//1. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsHashTable();
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();			
			for(String sHeader : setHeader) {
				
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//2. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsHashTable();
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();
			for(String sHeader : setHeader) {
				iColumn++;
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//Weitere Zeilen 3te bis Schluss.
			do {
				iLine++;
				hsCsv = objReaderSorted.parseNextLineAsHashTable();
				//assertNotNull(hsCsv); //irgendwann soll es ja null werden, wenn nix mehr da ist
				if(hsCsv!=null) {
					setHeader = hsCsv.keySet();
					iColumn=-1;
					for(String sHeader : setHeader) {
						iColumn++;
						Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
					}
				}//end if hsCsv!=null
			} while(hsCsv!=null);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testParseNextLineAsMap_byLoad(){
		try{
			
			//boolean bSuccess  = objReaderSorted.load();
			LinkedHashMap<String,String> hsCsv = null;
			Set<String> setHeader = null;
			int iLine=-1;
			int iColumn=-1;
			
			//1. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsMap();
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();			
			for(String sHeader : setHeader) {
				
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//2. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsMap();
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();
			for(String sHeader : setHeader) {
				iColumn++;
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//Weitere Zeilen 3te bis Schluss.
			do {
				iLine++;
				hsCsv = objReaderSorted.parseNextLineAsMap();
				//assertNotNull(hsCsv); //irgendwann soll es ja null werden, wenn nix mehr da ist
				if(hsCsv!=null) {
					setHeader = hsCsv.keySet();
					iColumn=-1;
					for(String sHeader : setHeader) {
						iColumn++;
						Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
					}
				}//end if hsCsv!=null
			} while(hsCsv!=null);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testParseNextLineAsMap_backward_byLoad(){
		try{
			
			//boolean bSuccess  = objReaderSorted.load();
			LinkedHashMap<String,String> hsCsv = null;
			Set<String> setHeader = null;
			int iLine=-1;
			int iColumn=-1;
			
			//1. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsMap(false);
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();			
			for(String sHeader : setHeader) {
				
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//2. Zeile
			iLine++;
			iColumn=-1;
			hsCsv = objReaderSorted.parseNextLineAsMap(false);
			assertNotNull(hsCsv);			
			setHeader = hsCsv.keySet();
			for(String sHeader : setHeader) {
				iColumn++;
				Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
			}
			
			//Weitere Zeilen 3te bis Schluss.
			do {
				iLine++;
				hsCsv = objReaderSorted.parseNextLineAsMap(false);
				//assertNotNull(hsCsv); //irgendwann soll es ja null werden, wenn nix mehr da ist
				if(hsCsv!=null) {
					setHeader = hsCsv.keySet();
					iColumn=-1;
					for(String sHeader : setHeader) {
						iColumn++;
						Syso.println(iLine + ": " + sHeader + "\t= " + hsCsv.get(sHeader));
					}
				}//end if hsCsv!=null
			} while(hsCsv!=null);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//end class