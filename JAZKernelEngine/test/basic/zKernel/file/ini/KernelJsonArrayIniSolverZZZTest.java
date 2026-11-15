package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.TestUtilZZZ.TestSubtype;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelJsonArrayIniSolverZZZTest extends TestCase {	
	public final static String sEXPRESSION_JSONARRAY01_CONTENT_SOLVED = "[TESTWERT2DO2JSON01, TESTWERT2DO2JSON02]";
	public final static String sEXPRESSION_JSONARRAY01_SOLVED = "<Z>" + KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_CONTENT_SOLVED + "</Z>";
	public final static String sEXPRESSION_JSONARRAY01_CONTENT = "[\"TESTWERT2DO2JSON01\",\"TESTWERT2DO2JSON02\"]";
	public final static String sEXPRESSION_JSONARRAY01_DEFAULT = "<Z><JSON><JSON:ARRAY>" + KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_CONTENT +"</JSON:ARRAY></JSON></Z>";
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonArrayIniSolverZZZ objExpressionSolver;
	private KernelJsonArrayIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonArrayIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelJsonArrayIniSolverZZZ(objKernel, saFlag);
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
//		catch (FileNotFoundException e) {			
//			e.printStackTrace();
//		} catch (IOException e) {			
//			e.printStackTrace();
//		}		
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
		
		btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
		
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
	public void testCompute_JsonArray(){		
//		try {			
			String sExpression=null; String sExpressionSubstituted=null; String sExpressionSolved=null; ArrayList<String>alsExpressionSolved=null;
		
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSubstituted = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_SOLVED;
			alsExpressionSolved = new ArrayList<String>();
			alsExpressionSolved.add("TESTWERT2DO2JSON01");
			alsExpressionSolved.add("TESTWERT2DO2JSON02");
			testCompute_JsonArray_(TestUtilZZZ.TestSubtype.DEFAULT, sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved);
			
			
			
			sExpression = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSubstituted = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sExpressionSolved = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_SOLVED;
			alsExpressionSolved = new ArrayList<String>();
			alsExpressionSolved.add("TESTWERT2DO2JSON01");
			alsExpressionSolved.add("TESTWERT2DO2JSON02");
			testCompute_JsonArray_(TestUtilZZZ.TestSubtype.AS_ENTRY, sExpression, sExpressionSubstituted, sExpressionSolved, alsExpressionSolved);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JsonArray_(TestUtilZZZ.TestSubtype enumTestSubtype, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, ArrayList<String>alsExpressionSolvedIn) {
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sExpressionSolvedTemp;  ArrayList<String>alsExpressionSolved;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		IKernelConfigSectionEntryZZZ objEntry=null;
		String sValue;
		boolean btemp = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {	
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			//2c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted; 		
			sPre = "";
			sPost = "";
			alsExpressionSolved = alsExpressionSolvedIn;
			btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
									
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 1. Ohne jegliche Expression-Berechnung
			//1a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression; //nur parse, darum solve=substituted,    und nicht  sExpressionSolvedIn;//alsExpressionSolved.toString();
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved,  EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved,  EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//1b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//1c) Solve ... OHNE Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
			}
			
			//1d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpression;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2. Ohne jegliche Solver-Berechnung (Ergebnisse muessen so sein wie bei Parse)
			//2a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//2b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//2c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//2d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			//Beim Solven ohne Solver bleiben immer die Z-Tags drin. sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 3. Ohne Json-Solver-Berechnung
			//3a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3c) Solve, ohne Json-Solver-Berechnung, d.h. ein Parsen findet bzgl. JSON-Tag nicht statt ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			alsExpressionSolved = alsExpressionSolvedIn;
			//Beim Solven ohne Solver, bleibt alles wie es ist, nur substituiert wird
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//3d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//+++ 4. Mit JsonSolver-Berechnung Ohne JsonArraySolver-Berechung
			
			//4a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false); 
			//der Solver an sich wird ja ausgefuehrt, also Z-Tag weg...
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);	
			}
			
			//4d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//der Solver an sich wird ja ausgefuehrt, also Z-Tag weg...
			sExpressionSolved = sExpressionSubstitutedIn; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArrayUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 5. Mit JsonArray-Solver-Berechnung
			//5a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sPre = "";
			sPost = "";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			//false: d.h. Z-Tags sollen drin bleiben sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sEXPRESSION_JSONARRAY01_CONTENT; //sExpressionSubstitutedIn;
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved,objExpressionSolver.getTagPartOpening(), objExpressionSolver.getTagPartClosing());
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//false: d.h. Z-Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);			
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			
			//5b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sEXPRESSION_JSONARRAY01_CONTENT; //sExpressionSubstitutedIn;			
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved,objExpressionSolver.getTagPartOpening(), objExpressionSolver.getTagPartClosing());
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//true: d.h. keine Z-Tags  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);					
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved,  sPre, sPost,alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//5c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn; //"[TESTWERT2DO2JSON01, TESTWERT2DO2JSON02]"; //Der ArrayList.toString() Ausdruck
			//Den Elterntag des Solvers nicht aufnehmen       sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");//einen Tag eines nicht genutzten Solvers drumpacken
			//Den Tag des konketen Solver nicht aufnehmen.... sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);//den Tag des konkreten Solvers drumpacken.			
			//Der Z-Tag bleibt drumherum.
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//5d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = "";
			sPost = "";			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;			
			//Wichtig: JSON und JSON:MAP sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! 
			//Der Z-Tag sollte weg sein
			sExpressionSolved = sExpressionSolvedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			alsExpressionSolved = alsExpressionSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonArray_JsonArraySolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, alsExpressionSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_JsonArray_Unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
						
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			

			//##########################################
			//### Expression unexpressed Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
				//#######################################
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sExpressionSolved='"+sExpressionSolved+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 0='"+vecValue.get(0).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 1='"+vecValue.get(1).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 2='"+vecValue.get(2).toString();+"'");
//				if(bUseParser && bUseExpressionGeneral) {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertFalse(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertFalse(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String	
//				}else {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertTrue(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertTrue(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String					
//				}
//				sValue = VectorUtilZZZ.implode(vecValue);
//				assertEquals(sExpressionSubstituted, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
//				
//				//+++
//				sExpressionSurroundedTemp = sExpressionSubstituted;
//				if(bUseParser && bUseExpressionGeneral) {
//					sValue = vecValue.get(1).toString();;//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.				
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getName());								
//				}else {
//					sValue = vecValue.get(0).toString();;//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist										
//				}
//				assertTrue(StringZZZ.contains(sExpressionSurroundedTemp,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
//				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				//Nutze eine Sammlung von assert Methoden, für .parseFirstVektor() und Werte-Analyse des solver Objekts.
				btemp = TestUtilAsTestZZZ.assertObjectValue_Solver_OnParseFirstVector(objExpressionSolver, vecValue, objEnumSurrounding, bUseExpressionGeneral, bUseParser, bUseSolver, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost);
				assertTrue(btemp);
				
				//#####################################
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSurroundedTemp, sValue);
							
				//+++ Der Tag Wert
				sValue = objExpressionSolver.getValue();
				if(bUseExpressionGeneral && bUseParser) {
					sExpressionSurroundedTemp = sExpressionSubstituted;
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);			
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);				
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {				
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);	
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionSolver, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}

	//##############################################################################
	
	private boolean testCompute_JsonArray_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null; 
			
			String sExpressionSubstituted2Compare = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
						
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				//#######################################
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				
				//Nutze eine Sammlung von assert Methoden, für .parseFirstVektor() und Werte-Analyse des solver Objekts.
				btemp = TestUtilAsTestZZZ.assertObjectValue_Solver_OnParseFirstVector(objExpressionSolver, vecValue, objEnumSurrounding, bUseExpressionGeneral, bUseParser, bUseSolver, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost);
				assertTrue(btemp);
				
				//#####################################
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSurroundedTemp, sValue);
							
				//+++ Der Tag Wert
				sValue = objExpressionSolver.getValue();
				if(bUseExpressionGeneral && bUseParser) {
					sExpressionSurroundedTemp = sExpressionSubstituted;
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);	
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {							
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSolved='"+sExpressionSolved+"'" );
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue='"+sValue+"'" );
				
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);		
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {				
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);				
				sValue = objEntry.getValue();
								
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);				
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {								
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);								
				sValue = objEntry.getValue();
				
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionSolver, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	private boolean testCompute_JsonArray_JsonUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn,String sPreIn, String sPostIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null; 
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			
			//Also: Wir testen den "Kind-Solver"
			//Wenn wir den "Kind-Solver" Testen, dann sind die Flags für den "Eltern-Solver" egal
			//Demnach nicht folgendes Flagset verwenden IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSON_UNSOLVED;
			//sondern hier in testCompute_JsonArray_JsonUnsolved_:			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSON_UNSOLVED;
			
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				//#######################################
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());

				//Nutze eine Sammlung von assert Methoden, für .parseFirstVektor() und Werte-Analyse des solver Objekts.
				btemp = TestUtilAsTestZZZ.assertObjectValue_Solver_OnParseFirstVector(objExpressionSolver, vecValue, objEnumSurrounding, bUseExpressionGeneral, bUseParser, bUseSolver, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost);
				assertTrue(btemp);
				
				//#####################################
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToKeep_OnParse());
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSurroundedTemp, sValue);
							
				//+++ Der Tag Wert
				sValue = objExpressionSolver.getValue();
				if(bUseExpressionGeneral && bUseParser) {
					sExpressionSurroundedTemp = sExpressionSubstituted;
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);					
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);		
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				String sExpressionTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionTemp;
				assertEquals(sExpressionSubstituted, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
							
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionSolver, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);

			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	
	private boolean testCompute_JsonArray_JsonArrayUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONARRAY_UNSOLVED;
			
			//##########################################
			//### Expression unsolved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				//#######################################
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sExpressionSolved='"+sExpressionSolved+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 0='"+vecValue.get(0).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 1='"+vecValue.get(1).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 2='"+vecValue.get(2).toString();+"'");
//				if(bUseParser && bUseExpressionGeneral) {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertFalse(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertFalse(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String	
//				}else {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertTrue(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertTrue(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String					
//				}
//				sValue = VectorUtilZZZ.implode(vecValue);
//				assertEquals(sExpressionSubstituted, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
//				
//				//+++
//				sExpressionSurroundedTemp = sExpressionSubstituted;
//				if(bUseParser && bUseExpressionGeneral) {
//					sValue = vecValue.get(1).toString();;//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.				
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getName());								
//				}else {
//					sValue = vecValue.get(0).toString();;//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist										
//				}
//				assertTrue(StringZZZ.contains(sExpressionSurroundedTemp,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
//				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
//				
				
				//Nutze eine Sammlung von assert Methoden, für .parseFirstVektor() und Werte-Analyse des solver Objekts.
				btemp = TestUtilAsTestZZZ.assertObjectValue_Solver_OnParseFirstVector(objExpressionSolver, vecValue, objEnumSurrounding, bUseExpressionGeneral, bUseParser, bUseSolver, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost);
				assertTrue(btemp);
				
				//#####################################
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSurroundedTemp, sValue);
							
				//+++ Der Tag Wert
				sValue = objExpressionSolver.getValue();
				if(bUseExpressionGeneral && bUseParser) {
					sExpressionSurroundedTemp = sExpressionSubstituted;
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);		
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);			
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);

				sValue = objEntry.getValue();
				assertEquals(sExpressionSubstituted, sValue);			
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);		
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionSolver, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################

	private boolean testCompute_JsonArray_JsonArraySolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, ArrayList<String>alsExpressionSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpressionSubstituted2compareWithExpression = null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;


			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONARRAY_SOLVED;
			
			//##########################################
			//### Expression solved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				//#######################################
				//+++ Teilberechnungen durchführen.
				//    Es werden wg false die Tags nicht entfernt
				vecValue = objExpressionSolver.parseFirstVector(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sExpressionSolved='"+sExpressionSolved+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 0='"+vecValue.get(0).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 1='"+vecValue.get(1).toString();+"'");
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 2='"+vecValue.get(2).toString();+"'");
//				if(bUseParser && bUseExpressionGeneral) {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertFalse(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertFalse(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String	
//				}else {
//					assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString();)); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
//					assertTrue(StringZZZ.isEmpty(vecValue.get(1).toString();)); //in der 1ten Position ist der Tag
//					assertTrue(StringZZZ.isEmpty(vecValue.get(2).toString();)); //in der 2ten Position ist der Tag nach dem gesuchten String					
//				}
//				sValue = VectorUtilZZZ.implode(vecValue);
//				assertEquals(sExpressionSubstituted, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
//				
//				//+++
//				sExpressionSurroundedTemp = sExpressionSubstituted;
//				if(bUseParser && bUseExpressionGeneral) {
//					sValue = vecValue.get(1).toString();;//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.				
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME);
//					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getName());								
//				}else {
//					sValue = vecValue.get(0).toString();;//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist										
//				}
//				assertTrue(StringZZZ.contains(sExpressionSurroundedTemp,sValue,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
//				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
				
				//Nutze eine Sammlung von assert Methoden, für .parseFirstVektor() und Werte-Analyse des solver Objekts.
				btemp = TestUtilAsTestZZZ.assertObjectValue_Solver_OnParseFirstVector(objExpressionSolver, vecValue, objEnumSurrounding, bUseExpressionGeneral, bUseParser, bUseSolver, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost);
				assertTrue(btemp);
				
				//#####################################
				//+++ Nun die Gesamtberechnung durchführen				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				assertEquals(sExpressionSurroundedTemp, sValue);
							
				//+++ Der Tag Wert
				sValue = objExpressionSolver.getValue();
				if(bUseExpressionGeneral && bUseParser) {
					sExpressionSurroundedTemp = sExpressionSubstituted;
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);	
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {				
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);
				
				sValue = objEntry.getValue();
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Nutze eine Sammlung von assert Methoden, die ein objEntry als input hat.
			//und in der die verschiedenen stati für den unexpressed, unsubstituted, substituted, unsolved, etc Fall stehen.
			btemp = TestUtilAsTestZZZ.assertFileIniEntry(objExpressionSolver, objEnumFlagset, objEnumSurrounding, objEnumTestCase, objEntry, sExpression, sExpressionSubstituted, sExpressionSolved, sTag, sTagSolved);
			assertTrue(btemp);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	//###################################
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeArray(){
		boolean btemp;
		String sExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
		try {	
			Vector3ZZZ<String>vecReturn=null;
			
			//### VORGEZOGENER FEHLERTEST START
			
		    //3. Test: Expression enabled, Parser enabled, Json & JsonArray disabled			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		

			//### VORGEZOGENER FEHLERTEST ENDE
			
			//###############################################
			//1. Test: Expression disabled
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
						
			//### Teilberechnungen durchführen
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		

			
			//########################################################################################
		    //2. Test: Expression enabled, Parser disabled, JsonSolve enabled			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
						
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//########################################################################################
		    //3. Test: Expression enabled, Parser enabled, Json & JsonArray disabled			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		

			
			//########################################################################################
			//4. Test: Expression, Parser, Json UND JSON_ARRAY enabled			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
						
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		

	
			//########################################################################################
			//5. Test: Expression, Parser, Json UND JSON_ARRAY enabled			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
						
			vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		

			
			//### Nun die Gesamtberechnung durchführen
			ArrayList<String>ls1 = objExpressionSolver.computeArrayList(sExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",ls1.size()==0);
				
			
			
			//##############################################################################
			//### Expression solved Fall
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY + "'", btemp);
		
			ArrayList<String>ls2 = objExpressionSolver.computeArrayList(sExpression);
			assertTrue("Mit Auflösung des String soll die ArrayList entsprechende Größe haben. ",ls2.size()==2);

			String sValue = ArrayListZZZ.computeDebugString(ls2);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testJson() {
		try {			
			String sExpression = "<JSON>kljkljlkjklj</JSON>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
			
			sExpression = sEXPRESSION_JSONARRAY01_DEFAULT;
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}//END class
	
