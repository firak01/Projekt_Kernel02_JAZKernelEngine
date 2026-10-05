package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelJsonIniSolverZZZTest extends TestCase {	
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnit_KernelJsonIniSolverZZZTest.ini");
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonIniSolverZZZ objExpressionSolver;
	private KernelJsonIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {				
			//Mache die utility Klasse TestUtilZZZ.createKernelFileUsed()
			//Diese sollten alle Tests verwenden und nicht mehr die im Projekt hinterlegte ini-Datei.
			//Hintergrund: Es fehlen sonst ggfs. Einträge / Pfade, bzw. die sind nicht mehr aktuell!!!!
												
			//#################
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonIniSolverZZZ(objKernel, objFileIniTest, saFlagInit);
				
			//#### Das konkrete TestObject				
			objFile = TestUtilZZZ.createKernelFileUsed4JUnit_KernelExpressionIniHandlerZZZTest();


			//Merke: Für diesen Test das konkrete Ini-File an das Test-Objekt uebergeben und sich nicht auf den Kernel selbst beziehen.
			//Merke: 20250816 - Jetzt wird das KernelIniFile-Objekt mit .registerForFlagEvents mit den Flags des Solver verknuepft.
            // Darum duerfen jetzt nicht erst einmal alle Flags gesetzt werden, sonst sind ggfs. zuviele gesetzt.
			/*
			String[] saFlagFileIni= {
							IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name(),
							IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name(),
							IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH.name(),
							IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE.name(),
							IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name(),
							IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name(),
							IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH.name(),
							IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name(),
							IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name(),
							IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()
							}; //Merke: In static Utility-Methoden ist auch wichtig, was im Ini-File für Flags angestellt sind.
			                   //       und nicht nur die Flags vom ExpressionIniHandler
			objFileIniTest = new FileIniZZZ(objKernel,  objFile, saFlagFileIni);
			*/
			objFileIniTest = new FileIniZZZ(objKernel,  objFile);
			
			String[] saFlag = {""}; //Die Flags werden in den konkreten Tests an-/ausgeschaltet.
			objExpressionSolver = new KernelJsonIniSolverZZZ(objKernel, objFileIniTest, saFlag);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 	
	}//END setup
	
	public void testFlagHandling(){
		boolean btemp;
		try{
			assertTrue(objExpressionSolverInit.getFlag("init")==true);
			assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
			
			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	/** Diesen Test hat auch jeder Solver. 
	 *  Es gibt also im Solver keine allgemeingueltige .isExpression() Wert als Loesung.
	 *  .isExpression bezieht sich auf den jeweiligen Tag des Solvers.
	 * 
	 * @author Fritz Lindhauer, 19.10.2025, 07:35:22
	 */
	public void testIsExpression() {
		try {			
			String sExpression=null;
						
			sExpression = "<Z>bin kein ExpressionHandler</Z>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
		
	
			String sTagName = objExpressionSolver.getName();
			sExpression = XmlUtilZZZ.computeTag(sTagName, "ein beliebiger text");
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMap(){
		boolean btemp;
		String sValue;
		String sExpression; String sExpressionSolved;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Anwenden der ersten Formel, ohne JSON-Berechnung	
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
			assertTrue("Das Flag '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "' sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "' sollte zur Verfügung stehen.", btemp);
			
			sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);			
			sValue = objExpressionSolver.parse(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);//unveraendert wg. useexpresseion = false,  usejson = false
			
			
			//+++++++++++++++++++++++++					
			//### Berechung der Formel			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "' sollte zur Verfügung stehen.", btemp);
			
			
			//a) Default, drinlassen des Z-Tags
			sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpression;		
			sValue = objExpressionSolver.parse(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);
			
			//b) Zum Entfernen des Z-Tags einen Parameter angeben
			sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sValue = objExpressionSolver.parse(sExpression, false);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);
			
			//################################################################
			//Berechne nun aus einem ini-File heraus
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Anwenden der ersten Formel, ohne JSON-Berechnung		
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
			assertTrue("Das Flag '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "' sollte zur Verfügung stehen.", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "' sollte zur Verfügung stehen.", btemp);
			
			sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sValue = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1").getValue();			
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++
			//Anwenden der ersten Formel, mit JSON-Berechnung		
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
			assertTrue("Das Flag '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "' sollte zur Verfügung stehen.", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "' sollte zur Verfügung stehen.", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "' sollte zur Verfügung stehen.", btemp);
			
			
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testJsonHashmap", "Map1");
			assertNotNull(objEntry);
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			
			HashMap<String,String> hm = objEntry.getValueHashMap();
			assertNotNull(hm);
			String sValue01 = hm.get("UIText01");
			assertNotNull(sValue01);
			assertEquals("TESTWERT1DO2JSON01", sValue01);												
			
			String sValue02 = hm.get("UIText02");
			assertNotNull(sValue02);
			assertEquals("TESTWERT1DO2JSON02", sValue02);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeArrayList(){
		boolean btemp;
		String sValue;
		String sExpression; String sExpressionSolved;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		try {	
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name() + "' sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() + "' sollte zur Verfügung stehen.", btemp);
			
			
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);			
			sValue = objExpressionSolver.parse(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);//unveraendert wg. useexpresseion = false,  usejson = false
			
		
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Anwenden der ersten Formel, ohne JSON-Berechnung		
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() + "' sollte zur Verfügung stehen.", btemp);
			
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;			
			sValue = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1").getValue();			
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Mit Solver sollte dann Z-Tag wegfallen. Wichtig beim ini-File: Cache Löschen.
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name() + "' sollte zur Verfügung stehen.", btemp);
						
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			
			
			//Meke: Der Cache wird nur verwendet, wenn das Kernel Objekt für die Property-Suche verwendet wird.
			//      also nicht notwendig: objFileIniTest.getKernelObject().getCacheObject().clear();
			//      im Gegensatz zu       objKernel.getparameter(.......)			
			sValue = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1").getValue();			
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//+++++++++++++++++++++++++
			//### Berechung der Formel			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() + "' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name() + "' sollte zur Verfügung stehen.", btemp);

			
			//a) default bleibt beim Parsen der Z-Tag drin 
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;			
			sValue = objExpressionSolver.parse(sExpression);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);
			
			//b) Will man den Z-Tag entfernt haben, muss man einen Parameter uebergeben
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sValue = objExpressionSolver.parse(sExpression, false);//compute gibt nur einen DebugString zurück
			assertNotNull(sValue);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++++
			//Berechne nun aus einem ini-File heraus
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
			assertTrue("Das Flag '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name() + "' sollte zur Verfügung stehen.", btemp);
						
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
				
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "' sollte zur Verfügung stehen.", btemp);
							
			IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValue("Section for testJsonArraylist", "Array1");
			assertNotNull(objEntry);
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonArray());
			
			ArrayList<String> als = objEntry.getValueArrayList();
			assertNotNull(als);
			String sValue01 = als.get(0);
			assertTrue(sValue01.equals("TESTWERT2DO2JSON01"));
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
		
	public void testJson() {
		try {			
			String sExpression = "<Z>bin kein JSON</Z>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
		
		
			sExpression = "<JSON>kljkljlkjklj</JSON>";
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
			//TODOGOON: DIE BERECHUNG ERFOLGT ANHAND DER KONKRETEN ERSTELLENT DATEI
//			sExpression = sEXPRESSION01_DEFAULT;
//			bValue = objExpressionSolver.isExpression(sExpression);
//			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//END class
	
