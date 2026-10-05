package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelSingletonZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaIniConverterZZZTest extends TestCase {
	//TODO GOON: Dies Klasse ist bisher nur eine Kopie. Passe komplett an.
	//TODO GOON 20190128: Pfad auf spezielle ini - Datei, sollte relativ sein
	
	private final static String strFILE_DIRECTORY_DEFAULT = new String("test");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitTestExpressionIniConverter.ini");
	
	
	private File objFile;
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	private IKernelZZZ objKernel;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelZFormulaIniConverterZZZ objExpressionConverter;
	private KernelZFormulaIniConverterZZZ objExpressionConverterInit;
	
	

	protected void setUp(){
		try {			
			
			//### Eine Beispieldatei. Merke: Die Einträge werden immer neu geschrieben und nicht etwa angehngt.
			//Merke: Erst wenn es überhaupt einen Test gibt, wird diese Datei erstellt
			String sFilePathTotal = null;		
//			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
//				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
//			}else{
//				//Eclipse Worspace
//				File f = new File("");
//			    String sPathEclipse = f.getAbsolutePath();
//			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
//				
//			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse, strFILE_NAME_DEFAULT );				
//			}
			sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			
			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file			
			objStreamFile.println(";This is a temporarily test file for KernelExpressionIniConverterZZZTest.");      //Now the File is created. This is a comment line
			objStreamFile.println(";This file will be newly created by the setUp()-method of this JUnit Test class, every time before a testMethod will be invoked.");
			objStreamFile.println("#This is another commentline");
			objStreamFile.println("[Section A]");
			objStreamFile.println("Testentry1=<z:Empty/>");			
			objStreamFile.println("TestentryDummy=nothing");
						
			objFile = new File(sFilePathTotal);
			
		
			
			//Kernel + Log - Object dem TestFixture hinzufügen. Siehe test.zzzKernel.KernelZZZTest
			//objKernel = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",(String)null);
			
			//Das funktioniert... gesucht wird: 
			//zuerst in			c:\fglkernel\kernelconfig (also default) Ordner
			//wenn dort nichts gefunden wird im Workspace-Root...
			//und wenn dort nichts gefunden wird, wird eine temporäre Datei angelegt.
			//objKernel = KernelSingletonZZZ.getInstance("01", "", "ZKernelConfigKernelSingleton_test.ini", (String[])null);
			
			//Das funktioniert nicht im TEST, sondern nur im src - Ordner: 
			//objKernel = KernelSingletonZZZ.getInstance("01", ".", "ZKernelConfigKernelSingleton_test.ini", (String[])null);
			
			//Das funktioniert, die Testdatei soll im test-Root liegen
			objKernel = KernelSingletonZZZ.getInstance("01", "test", "ZKernelConfigKernelSingleton_test.ini", (String[])null);
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, (String[]) null);
			 			
			//### Die TestObjecte
			objExpressionConverterInit = new KernelZFormulaIniConverterZZZ();
			objExpressionConverter = new KernelZFormulaIniConverterZZZ(objKernel, objFileIniTest, null);
			
			//TestKonfiguration prüfen
//			assertTrue(objExpressionSolverInit.getFlag("init")==true);
//			assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
			
			//#### Ein init TestObjekt
			String[] saFlag = {"init"};
			objFileIniInit = new FileIniZZZ(objKernel,  objFile, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}//END setup
	
	public void testFlagHandling(){
		try{
				
		assertTrue(objExpressionConverterInit.getFlag("init"));
		assertFalse(objExpressionConverter.getFlag("init")); //Nun wäre init falsch

		assertFalse(objExpressionConverterInit.getFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION));
		assertFalse(objExpressionConverter.getFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION)); //Nun wäre init falsch
		
		
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	

	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testConvertEmpty(){
		boolean btemp;
		String sConversionSource; String sConversion; String sValue;
		try {
			//Anwenden der ersten 
			
			//IDEE: convertable != parasable.
			//      convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
			//      parsable wird also lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
		
			//#######################################								
			//1. Test Konvertierung static verwenden
			
			sConversionSource = "abcd<z:Empty/>xyz";
			sConversion = sConversionSource;			
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "<z:Empty/>";
			sConversion = "";
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertEquals(sConversion, sValue);

			
			sConversionSource = "BLABLA";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			
			//+++ as Expression
			sConversionSource = "<z:Empty/>";
			sConversion = "<Z/>";
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "BLABLA";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "<z>BLABLA</z>";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			//#######################################
			//2. Test: Konvertierung per Objekt
			
			//+++ Konvertierung ausgeschaltet
			btemp = objExpressionConverter.setFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION, false);
			assertTrue("Flag nicht vorhanden '" + IIniTagWithConversionZZZ.FLAGZ.USECONVERSION + "'", btemp);
			
			sConversionSource = "<z:Empty/>";
			sConversion = sConversionSource;
			sValue = objExpressionConverter.getAsString(sConversionSource);
			assertEquals(sConversion, sValue);
			
			//+++ Konvertierung einschalten
			btemp = objExpressionConverter.setFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION, true);
			assertTrue("Flag nicht vorhanden '" + IIniTagWithConversionZZZ.FLAGZ.USECONVERSION + "'", btemp);
			
			sConversionSource = "<z:Empty/>";
			sConversion = "";
			sValue = objExpressionConverter.getAsString(sConversionSource);
			assertEquals(sConversion, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testConvertNull(){
		boolean btemp;
		String sConversionSource; String sConversion; String sValue;
		try {
			//Anwenden der ersten 
			
			//IDEE: convertable != parasable.
			//      convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
			//      parsable wird also lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
		
			//#######################################								
			//1. Test Konvertierung static verwenden
			
			sConversionSource = "abcd<z:Null/>xyz";
			sConversion = sConversionSource;			
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "<z:Null/>";
			sConversion = null;
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertNull("NULL erwartet. Wert ist aber '" + sValue + "'", sValue);

			
			sConversionSource = "BLABLA";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsStringStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			
			//+++ as Expression
			sConversionSource = "<z:Null/>";
			sConversion = "<Z/>";
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "BLABLA";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			sConversionSource = "<z>BLABLA</z>";
			sConversion = sConversionSource;
			sValue = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sConversionSource);
			assertEquals(sConversion, sValue);
			
			//#######################################
			//2. Test: Konvertierung per Objekt
			
			//+++ Konvertierung ausgeschaltet
			btemp = objExpressionConverter.setFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION, false);
			assertTrue("Flag nicht vorhanden '" + IIniTagWithConversionZZZ.FLAGZ.USECONVERSION + "'", btemp);
			
			sConversionSource = "<z:Null/>";
			sConversion = sConversionSource;
			sValue = objExpressionConverter.getAsString(sConversionSource);
			assertEquals(sConversion, sValue);
			
			//+++ Konvertierung einschalten
			btemp = objExpressionConverter.setFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION, true);
			assertTrue("Flag nicht vorhanden '" + IIniTagWithConversionZZZ.FLAGZ.USECONVERSION + "'", btemp);
			
			sConversionSource = "<z:Null/>";
			sConversion = null;
			sValue = objExpressionConverter.getAsString(sConversionSource);
			assertNull("NULL erwartet. Wert ist aber '" + sValue + "'", sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** Erweitertes Flag Handling. 
	 *   Das Ziel ist es gesetzte Flags an andere Objekte "Weiterzureichen"
	 * 
	 */
	public void testFlagPassHandling(){
		//A) Teste an dieser Stelle die Funktionalitäten aus ObjectZZZ
		
		try{
				
		//TestKonfiguration prüfen.
		//1. Hole alle FlagZ des Objekts
		String[] saTest01 = objFileIniInit.getFlagZ();
		assertNotNull(saTest01);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 2 FlagZ erwartet: Also nicht nur DEBUG und INIT.",saTest01.length>=3);
		
		//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
		//Init - Object
		assertTrue(objFileIniInit.getFlag("init")==true);
		
		String[]saTest02 = objFileIniInit.getFlagZ(true);
		assertNotNull(saTest02);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
				
		String[]saTest02b = objFileIniInit.getFlagZ(false);
		assertNotNull(saTest02b);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 1 FlagZ für 'false' erwartet: Also nicht nur DEBUG.",saTest02b.length>=2);
		
		objFileIniInit.setFlag("DEBUG", true);
		String[]saTest02c = objFileIniInit.getFlagZ(false);
		assertNotNull(saTest02c);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
		
		
		//B) TESTE DIE FUNKTIONALITÄT DER FLAG - ÜBERGABE.
		KernelZFormulaIniSolverZZZ objSolverInit = new KernelZFormulaIniSolverZZZ();
		
		//B01) Teste mal welche FlagZ es gemeinsam gibt.
		String[] saTestB01 = objFileIniInit.getFlagZ_passable(objSolverInit);
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 3 FlagZ (oder mehr) als Gemeinsamkeit erwartet: INIT, DEBUG, USEFORMULA, USEFORMULA_MATH, ... .",saTestB01.length>=9);
		
		//B02) Teste welche von den gemeinsamen FlagZ hier True gesetzt sind.
		//objFileInit.getFlagZ_passable(true, sTargetClassnameForThePass);
		String[] saTestB02 = objFileIniInit.getFlagZ_passable(true, objSolverInit);
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT SIEBEN FLAGS WENIGER für 'true' erwartet.",saTestB02.length==saTestB01.length-7);
		
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
		
	}

	
}//END class
	
