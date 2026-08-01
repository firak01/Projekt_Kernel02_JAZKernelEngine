package basic.zUtil.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import basic.javagently.Stream;
import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import junit.framework.TestCase;
import custom.zUtil.io.FileExpansionZZZ;
import custom.zUtil.io.FileZZZ;

public class FileExpansionZZZTest extends TestCase {	
	private FileExpansionZZZ objExpansionTest;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglkernel\\kerneltest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTestExpansion.txt");
	

	protected void setUp(){
		try {			
			
			//DEFINITION DER BASISDATEI... Merke: OHNE ANSPRUCH DARAUF, dass sie wirklich existiert.
			//Eine Beispieldatei. Mit der Basisdatei kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
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
			
			//NEIN: Nicht pauschal Dateien erzeugen. Das sollte den einzelnen Tests überlassen bleiben.
//			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, strFILE_NAME_DEFAULT );			
//			Stream objStreamFile = new Stream(sFilePathTotal, 1);  //This is not enough, to create the file
//			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
//			objStreamFile.close();

			//The main object used for testing
			FileZZZ objFileTest = new FileZZZ(sFilePathUsed, strFILE_NAME_DEFAULT);
			objExpansionTest = new FileExpansionZZZ(objFileTest);
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}		
	}//END setup
	
	public void testFlagZ(){
		try{
		boolean  bExists = objExpansionTest.proofFlagExists("NIXDA");
		assertFalse("Object should NOT have FlagZ 'NIXDA'",bExists);
		
		boolean bSetted = false;
			try{
				bSetted = objExpansionTest.setFlag("NIXDA", true);
				assertFalse("Setting an unavailable FLAGZ 'NIXDA' should return false",bSetted);
			} catch (ExceptionZZZ ez) {
				fail("Setting an unavailable FLAGZ should NOT throw an error.");		
			}
			
			//TestKonfiguration prüfen
			assertTrue(objExpansionTest.getFlag("init")==false); //Nun wäre init falsch		
			
			//An object just initialized
			FileExpansionZZZ objFileExpansionInit = new FileExpansionZZZ();
			assertTrue(objFileExpansionInit.getFlag("init")==true);
			
			//Flags aus der FileZZZ-Klasse
			assertTrue(objExpansionTest.getFlag(IFileExpansionStateEnabledZZZ.FLAGZ.FILE_CURRENT_FOUND)==false);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
		
	/** void, Tests: Validating the result of computing a expansion. 
	 * a) Computing the biggest expansion number which could be created, by a given number of digits 
	* Lindhauer; 21.04.2006 09:51:08
	 */
	public void testExpansionLookalike(){
//		try {
		//wichtig: Ich will die Gewissheit haben, dass das auch mit anderen Werten als dem Standardfall von 3 Ziffern funktioniert
		//Zu beachten ist, das die Funktion einen String zur�ckliefert.
		
		assertEquals("99",FileExpansionZZZ.getExpansionMax(2));		
		assertEquals("999",FileExpansionZZZ.getExpansionMax(3));
		assertEquals("9999",FileExpansionZZZ.getExpansionMax(4));
		assertEquals("99999",FileExpansionZZZ.getExpansionMax(5));
		
		//Hier wird eine F�llvariable zur Berechnung verwendet.
		//Dies soll auch wieder mit den unterschiedlichsten Werten m�glich sein
		objExpansionTest.setExpansionLength(0);
		assertEquals("",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(1);
		assertEquals("2",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(3);
		assertEquals("002",objExpansionTest.computeExpansion("0",2));
		
		objExpansionTest.setExpansionLength(4);
		assertEquals("0002",objExpansionTest.computeExpansion("0",2));
		
		//Falls ein anderes F�llzeichen �bergeben werden soll
		// Hier 3 Unterstriche vor der Ziffer
		assertEquals("___2",objExpansionTest.computeExpansion("_",2));
		
		// Hier 2 Unterstriche und die Zahl ist 2 stellig
		assertEquals("__32",objExpansionTest.computeExpansion("_",32));
		
//		 Hier kein Unterstrich und die Zahl ist 4 stellig
		assertEquals("4321",objExpansionTest.computeExpansion("_",4321));
		
		
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}
	}
	
	public void testIterateExpansionAll_While_HasNext_Next(){	
		try {
			objExpansionTest.setExpansionFilling('0');
			objExpansionTest.setExpansionLength(3);
			
			String sFileNameOnly = FileEasyZZZ.getNameOnly(strFILE_NAME_DEFAULT);
			String sFilePathUsed = computeFilePath_();
			String sEnding = computeFileEnding_();
			
		//####################################################
		//######## Generelles Entfernen ggfs. vorhandener Testdateien		
		removeGeneratedTestFileAll_(sFilePathUsed, sFileNameOnly, sEnding);	
		
		//######## Der eigentliche Test		
		//Die einzelnen Tests immmer mit verschiedenenen Varianten des Arrays ( Elemente: Lücken in der Folge und Anzahl) durchführen.
		int iErg;
		String[][] saString = {
								{"","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016"},
								{"002","003","004","005","006","007","008","009","010","011","012","013","014","015","016"},
								{"001","002","003","004","005","006","007","008","009","010","011","012"},
								{"002","003","004","005","006","007","008","009","010","011","012"},
								{"002","003","005","006","008","009","011","012","013","014","015","016"}
		                      };
		int[] iaSummeQuer = StringArrayZZZ.toInteger(saString);
		
		
		int iTestCaseIndexMax = saString.length-1;
		
		//####################################################
		//######## A) Schleife mit hasNext() und 1x darin next(
		System.out.println(("##################################"));		
		System.out.println("### TESTCASES hasnext() + next() 1x");		
		for(int iTestCaseIndex = 0 ; iTestCaseIndex <= iTestCaseIndexMax;iTestCaseIndex++) {
			generateTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);
			iErg = iterateExpansionAll_WhileHasNext_Next1x_();
			int iSummeQuer = iaSummeQuer[iTestCaseIndex];
			assertEquals(iSummeQuer,iErg);				
			removeGeneratedTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);	
		}
		
	
		//####################################################
		//##### B) Schleife mit hasNext() und 2x darin next()
		System.out.println(("##################################"));
		System.out.println("### TESTCASES hasnext() + next() 2x");	
		for(int iTestCaseIndex = 0 ; iTestCaseIndex <= iTestCaseIndexMax;iTestCaseIndex++) {
			generateTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);
			iErg = iterateExpansionAll_WhileHasNext_Next2x_();	
			int iSummeQuer = iaSummeQuer[iTestCaseIndex];
			assertEquals(iSummeQuer,iErg);			
			removeGeneratedTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);	
		}
		
		//####################################################
		//##### C) Schleife mit hasNext() und 3x darin next()
		System.out.println(("##################################"));
		System.out.println("### TESTCASES hasnext() + next() 3x");	
		for(int iTestCaseIndex = 0 ; iTestCaseIndex <= iTestCaseIndexMax;iTestCaseIndex++) {
			generateTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);
			iErg = iterateExpansionAll_WhileHasNext_Next3x_();	
			int iSummeQuer = iaSummeQuer[iTestCaseIndex];
			assertEquals(iSummeQuer,iErg);				
			removeGeneratedTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);	
		}
		
		//####################################################
		//##### D) Zuerst ohne Schleife 3x hintereinder next() aufrufen. Anschliessend eine Schleife mit 1x next()
		System.out.println(("##################################"));
		System.out.println("### TESTCASES next() 3x + hasnext()");	
		for(int iTestCaseIndex = 0 ; iTestCaseIndex <= iTestCaseIndexMax;iTestCaseIndex++) {
			generateTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);
			iErg = iterateExpansionAll_WhileNext3x_HasNext_();	
			int iSummeQuer = iaSummeQuer[iTestCaseIndex];
			assertEquals(iSummeQuer,iErg);				
			removeGeneratedTestFileExpansionWanted_(saString[iTestCaseIndex], sFilePathUsed, sFileNameOnly, sEnding);	
		}
						
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	} 
	}
	
	private String computeFilePath_() throws ExceptionZZZ {
		String sReturn=null;
		if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
			sReturn = strFILE_DIRECTORY_DEFAULT;
		}else{
			//Eclipse Workspace
			File f = new File("");
		    String sPathEclipse = f.getAbsolutePath();
		    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
		    
		    sReturn = sPathEclipse + IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + "test";		   
		}
		return sReturn;
	}
	
	private String computeFileEnding_() throws ExceptionZZZ {
				String sReturn = null;						
				String sEnding = FileEasyZZZ.getNameEnd(strFILE_NAME_DEFAULT);
				if(!StringZZZ.isEmpty(sEnding)) {
					sReturn = IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR + sEnding;
				}else {
					sReturn = sEnding;
				}
				return sReturn;				
	}
	
	private void removeGeneratedTestFileAll_(String sFilePathUsed, String sFileNameOnly, String sEnding) throws ExceptionZZZ {
		//ZUM "VOR DEM TEST ALLE DATEIEN LÖSCHEN"!!!!
		//Alle verwendeten Dateiexpansions
		String[] saStringAll = {"","001","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016"};
		removeGeneratedTestFileExpansionWanted_(saStringAll, sFilePathUsed, sFileNameOnly, sEnding );
	}
	
	private void removeGeneratedTestFileExpansionWanted_(String[] saExpansionWanted, String sFilePathUsed, String sFileNameOnly, String sEnding) throws ExceptionZZZ {
				
		//Alle verwendeten Dateiexpansions
		String[] saStringAll = {"","001","002","003","004","005","006","007","008","009","010","011","012","013","014","015","016"};
		String[] saFileNameAll = StringArrayZZZ.plusString(saStringAll, sFileNameOnly, "BEFORE");
		String[] saFileNameAllWithEnding = StringArrayZZZ.plusString(saFileNameAll, sEnding, "BEHIND");		
		for(int iCount=0;iCount<=saFileNameAllWithEnding.length-1;iCount++) {
			String sFilePathName = sFilePathUsed + IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR + saFileNameAllWithEnding[iCount];
			FileEasyZZZ.removeFile(sFilePathName);			
		}		
}
	
	private void generateTestFileExpansionWanted_(String[] saExpansionWanted, String sFilePathUsed, String sFileNameOnly, String sEnding) throws ExceptionZZZ{
		String[] saFileName = StringArrayZZZ.plusString(saExpansionWanted, sFileNameOnly, "BEFORE");
		String[] saFileNameWithEnding = StringArrayZZZ.plusString(saFileName, sEnding, "BEHIND");
		
		//Anschliessend die speziellen Testdateien neu erzeugen.
		for(int iCount=0;iCount<=saFileNameWithEnding.length-1;iCount++) {
			String sFileNameWithEnding = saFileNameWithEnding[iCount];
			String sFilePathTotal =  FileEasyZZZ.joinFilePathName(sFilePathUsed, sFileNameWithEnding );			
			IStreamZZZ objStreamFile;
			try {
				objStreamFile = new StreamZZZ(sFilePathTotal, 1);
				objStreamFile.println("This is a temporarily test file: " + iCount);      //Now the File is created
				objStreamFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage());
				throw ez;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage());
				throw ez;
			}  		
		}
	}
	
	private int iterateExpansionAll_WhileHasNext_Next1x_() throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			String sScript = ReflectCodeZZZ.getMethodCurrentName();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+++ Starting: " + sScript);
			
			int iCounter=0;
			Iterator<String> itExpansion = objExpansionTest.iterator();		
			while(itExpansion.hasNext()) {
				iCounter++;			
				String sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();				
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			}
			System.out.println("Expansionsuche beendet.");		
		}//end main:
		return iReturn;
	}
	
	
	private int iterateExpansionAll_WhileHasNext_Next2x_() throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			String sScript = ReflectCodeZZZ.getMethodCurrentName();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+++ Starting: " + sScript);
			
			int iCounter=0;
			Iterator<String> itExpansion = objExpansionTest.iterator();		
			while(itExpansion.hasNext()) {
				iCounter++;			
				String sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
				
				//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
				iCounter++;
				sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();											
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			}
			System.out.println("Expansionsuche beendet.");		
		}//end main:
		return iReturn;
	}
	
	private int iterateExpansionAll_WhileHasNext_Next3x_() throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			String sScript = ReflectCodeZZZ.getMethodCurrentName();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+++ Starting: " + sScript);
			
			int iCounter=0;
			Iterator<String> itExpansion = objExpansionTest.iterator();		
			while(itExpansion.hasNext()) {
				iCounter++;			
				String sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
				
				//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
				iCounter++;
				sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
				
				//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
				iCounter++;
				sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();							
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			}
			System.out.println("Expansionsuche beendet.");		
		}//end main:
		return iReturn;
	}
	
	private int iterateExpansionAll_WhileNext3x_HasNext_() throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			String sScript = ReflectCodeZZZ.getMethodCurrentName();
			System.out.println("++++++++++++++++++++++++");
			System.out.println("+++ Starting: " + sScript);
			
			
			Iterator<String> itExpansion = objExpansionTest.iterator();	
			int iCounter=0;
			iCounter++;
			
			//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
			String sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();
			iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			
			//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
			iCounter++;
			sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();	
			iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			
			//Test: wiederholt next() aufrufen muss klappen. Dann wird auf das durch hasNext() gecachte Objekt nicht zugegriffen.
			iCounter++;
			sExpansion = (String) itExpansion.next();			
			System.out.print("Expansion: '" + sExpansion + "', ");
			if(iCounter%10==0)System.out.println();	
			iReturn = iReturn + StringZZZ.toInteger(sExpansion);
						
			while(itExpansion.hasNext()) {
				iCounter++;			
				sExpansion = (String) itExpansion.next();			
				System.out.print("Expansion: '" + sExpansion + "', ");
				if(iCounter%10==0)System.out.println();		
				iReturn = iReturn + StringZZZ.toInteger(sExpansion);
			}
			System.out.println("Expansionsuche beendet.");		
		}//end main:
		return iReturn;
	}
	
}
