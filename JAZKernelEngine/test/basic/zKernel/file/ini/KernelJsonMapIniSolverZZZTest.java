package basic.zKernel.file.ini;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
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

public class KernelJsonMapIniSolverZZZTest extends TestCase {	
	//Problem dabei: Die Reihenfolge der Einträge in der HashMap ist nicht fest vorgegeben. Darum LinkedHashMap fuer die Strings verwenden
	protected final static String sEXPRESSION_JSONMAP01_CONTENT_SOLVED = "UIText01=TESTWERT1DO2JSON01, UIText02=TESTWERT1DO2JSON02";
	//NEIN, JsonSolved ist immer ein String ohne die Json-Tags.      protected final static String sEXPRESSION_JSONMAP01_CONTENT_SOLVED_JSON = "{" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED + "}";
	protected final static String sEXPRESSION_JSONMAP01_SOLVED = "<Z>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED + "</Z>";
	protected final static String sEXPRESSION_JSONMAP01_CONTENT = "\"UIText01\":\"TESTWERT1DO2JSON01\",\"UIText02\":\"TESTWERT1DO2JSON02\"";
	protected final static String sEXPRESSION_JSONMAP01_CONTENT_JSON = "{" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT + "}";
	protected final static String sEXPRESSION_JSONMAP01_DEFAULT = "<Z><JSON><JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_JSON +"</JSON:MAP></JSON></Z>";
	
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;	
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJsonMapIniSolverZZZ objExpressionSolver;
	private KernelJsonMapIniSolverZZZ objExpressionSolverInit;
	
	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJsonMapIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelJsonMapIniSolverZZZ(objKernel, saFlag);
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
	
		
	
	//############################################################
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_JsonMap(){
		String sExpressionSource=null; String sExpressionSubstituted; String sExpressionSolved;
//		try {			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSubstituted = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_SOLVED;
			testCompute_JsonMap_(TestUtilZZZ.TestSubtype.DEFAULT, sExpressionSource, sExpressionSubstituted, sExpressionSolved);
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSubstituted = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_SOLVED;
			testCompute_JsonMap_(TestUtilZZZ.TestSubtype.AS_ENTRY, sExpressionSource, sExpressionSubstituted, sExpressionSolved);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JsonMap_(TestUtilZZZ.TestSubtype enumTestSubtype, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn){
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		IKernelConfigSectionEntryZZZ objEntry=null;		
		String sValue;
		boolean btemp = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		

		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			

			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 1. Ohne jegliche Expression-Berechnung
			//1a)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP,  EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			} else {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP,  EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			
			//1b)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;	

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			
			//1c)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression; 	

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//1d)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2. Ohne jegliche Solver-Berechnung
			//2a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);				
			}
			
			
			//2b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//2c)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//2d)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstituted;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			//Beim Solven Ohne Solver bleiben immer die Z-Tags drin... sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 3. Ohne Json-Solver-Berechung
			//3a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpression;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;	
			//Beim Solven ohne Solver, bleibt alles wie es ist, nur substituiert wird
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//3d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_Json_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//+++ 4. Ohne JsonMapSolver-Berechung
			//4a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost,EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4b)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//der Solver an sich wird ja ausgefuehrt, also Z-Tag weg...
			sExpressionSolved = sExpressionSubstitutedIn; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4c)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSubstitutedIn;

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//4d)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//der Solver an sich wird ja ausgefuehrt, also Z-Tag weg...
			sExpressionSolved = sExpressionSubstitutedIn;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 5. Mit JsonMap-Berechnung
			//5a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sEXPRESSION_JSONMAP01_CONTENT; //"{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}";
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, objExpressionSolver.getTagPartOpening(), objExpressionSolver.getTagPartClosing());
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//5b)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;	
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sEXPRESSION_JSONMAP01_CONTENT; //"{\"UIText01\":\"TESTWERT2DO2JSON01\",\"UIText02\":\"TESTWERT2DO2JSON02\"}"; 
			sExpressionSolved = objExpressionSolver.makeAsExpression(sExpressionSolved);
			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			//true: d.h. keine Z-Tags  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//5c)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;//"{UIText02=TESTWERT2DO2JSON02, UIText01=TESTWERT2DO2JSON01}"; //Das ist der Wert von HashMap.toString/(; //sExpressionIn;

			//Den Elterntag des Solvers nicht aufnehmen       sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");//einen Tag eines nicht genutzten Solvers drumpacken
			//Den Tag des konketen Solver nicht aufnehmen.... sExpressionSolvedTemp = objExpressionSolver.makeAsExpression(sExpressionSolvedTemp);//den Tag des konkreten Solvers drumpacken.			
			//Der Z-Tag bleibt drumherum.  			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//5d)
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;

			//Wichtig: JSON und JSON:MAP sollen aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.
			//Der Z-Tag sollte weg sein
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JsonMap_JsonMap_solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	private boolean testCompute_JsonMap_unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
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
			//### Expression unsolved Fall
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
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
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
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
	
	private boolean testCompute_JsonMap_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;  
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
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
				
				sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;				
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
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
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
	
	private boolean testCompute_JsonMap_Json_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost;
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;

			//Also: Wir testen den "Kind-Solver".
			//Wenn wir den "Kind-Solver" Testen, dann sind die Flags für den "Eltern-Solver" egal
			//Demnach nicht folgendes Flagset verwenden: IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSON_UNSOLVED;
			//sondern hier in testCompute_JsonMap_Json_unsolved_:			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_SOLVED;
			
			
			
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
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
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
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
			
			bReturn = true;
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	
	//########################################################################
	
	
	private boolean testCompute_JsonMap_JsonMap_unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; String sValueUsed; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_UNSOLVED;
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
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
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
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

	private boolean testCompute_JsonMap_JsonMap_solved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag=null; String sTagSolved=null; String sPre; String sPost;
			String sValue; Vector3ZZZ<String> vecValue;		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
			
			String sExpressionSubstituted2Compare = null; String sExpressionSurroundedTemp = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;

			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JSONMAP_SOLVED;
			
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
			
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJsonMapIniSolverZZZ.sTAG_NAME, false);
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
	
	//###################################
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01Map(){
		boolean btemp;
		String sExpression; String sExpressionSolved;
		String sValue; Vector<String> vecValue;
		HashMap<String,String>hm;
		try {					
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false);
			assertTrue("Flag nicht vorhanden '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'", btemp);
			
		
			//### Teilberechnungen durchführen
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			vecValue = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecValue, 0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecValue, 1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecValue, 2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
	
			//### Nun die Gesamtberechnung durchführen
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			hm = objExpressionSolver.computeHashMapFromJson(sExpression);
			assertTrue("Ohne Auflösung soll es keine HashMap geben",hm.size()==0);
				
			
			
			//##############################################################################
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
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'", btemp);
		
			sExpression = sEXPRESSION_JSONMAP01_CONTENT_JSON;
			hm = objExpressionSolver.computeHashMapFromJson(sExpression);
			assertNotNull(hm);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",hm.size()==2);

			sValue = HashMapZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
			
			//++++++++++++++++++++++++++++++
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			sExpressionSolved = objExpressionSolver.solve(sExpression);
			hm =objExpressionSolver.getValueHashMap();
			assertNotNull(hm);
			assertTrue("Mit Auflösung des String soll die HashMap entsprechende Größe haben. ",hm.size()==2);

			sValue = HashMapZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
			
			
			//#############################################################
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testJson() {
		try {			
			String sExpression = "<JSON>kljkljlkjklj</JSON>";
			boolean bValue = objExpressionSolver.isExpression(sExpression);
			assertFalse(bValue);
			
			sExpression = sEXPRESSION_JSONMAP01_DEFAULT;
			bValue = objExpressionSolver.isExpression(sExpression);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01(){
		String sValue; String sExpressionSolved; String sExpression;
		IKernelConfigSectionEntryZZZ objEntry=null;
		String sTagStartZ;	String sTagEndZ;
		boolean btemp; 
		sExpression = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
		
		try {			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			//######################################################
			//### 1.1. Teilberechnungen durchführen
			Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.1. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			//#########################################################
			//### 1.2. Gesamtberechnung durchführen
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ);			
		
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein",sExpressionSolved, sValue);
		
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			//#################################################
			//### 1.3. Anwenden der ohne komplettaufloesung
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>"; //False steht für "von aussen nach innen" statt true = "von innen nach aussen".
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);			
//		
//			sTagStart = objExpressionSolver.getTagStarting();
//			sTagEnd = objExpressionSolver.getTagClosing(); //False steht für "von aussen nach innen" statt true = "von innen nach aussen".
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStart, sTagEnd, false);			
		
//			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, KernelJsonMapIniSolverZZZ.sTAG_NAME);
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, KernelJsonIniSolverZZZ.sTAG_NAME);
//			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			sValue = objExpressionSolver.solve(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");			
			assertEquals(sExpressionSolved, sValue);

			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//zu 1.3. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			assertTrue(objEntry.isJson()); //das hat der Parser alles herausgefunden.
			assertFalse(objEntry.isJsonMap());
			
			
			//################################################################
			//### 1.4. Anwenden der Komplettaufloesung
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
//			sTagStartZ = "<Z>";
//			sTagEndZ = "</Z>";
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
//		
//			sTagStartZ = objExpressionSolver.getTagStarting();
//			sTagEndZ = objExpressionSolver.getTagClosing();
//			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
		
			sExpressionSolved = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED;
//ohne JSON TAG drumherum			sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, "JSON");
			sValue = objExpressionSolver.solve(sExpression);			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			assertEquals(sExpressionSolved, sValue);

			//+++++++++++++++++++++++++++++++++++++++++++
			//zu 1.4. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			
			assertTrue(objEntry.isSolveCalled());
			assertTrue(objEntry.isSolvedChanged());
			assertTrue(objEntry.isSolved());
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testComputeHashMapFromJson(){
		String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
		IKernelConfigSectionEntryZZZ objEntry=null;
		String sTagStart;	String sTagEnd;

		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		Vector<String> vecReturn; HashMap<String,String> hm; String sLineWithJson;
		boolean btemp;
		
		try {
			
			//######################################
			//A) Einfacher Test: Leerstring
			sExpressionSource = "";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			
			//######################################
			//B) Einfacher Test: Dummystring
			sExpressionSource = "dummy";
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//"dummy" ist kein gültiger JSON-Hashmpa String, darum kommt nix raus.
			
					
			//###############################################################
			//C) Einfacher Test: Den JSON-Map source String direkt uebernehmen.
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_JSON;
			sExpression = sExpressionSource;
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
					
			//###############################################################
			//D) Aus dem Source String den reinen Json-Part machen. 
			//False steht für "von aussen nach innen" statt true = "von innen nach aussen".
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ, false);
			
			sTagStart = "<JSON>";
			sTagEnd = "</JSON>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStart, sTagEnd, false);
			
			sTagStart = "<JSON:MAP>";
			sTagEnd = "</JSON:MAP>";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStart, sTagEnd, false);
			
			sLineWithJson = sExpression;
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//##############################################################################
			//####### Gesamtpaket testen, d.h. mit echt geparsten Werten
			//### 2.1. Teilberechnungen durchführen - JSON unsolved JSON MAP unsolved	
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); //Ansonsten wird der Wert sofort ausgerechnet
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
									
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 1))); //in der 1ten Position ist der Tag
			assertTrue(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsString(vecReturn, 2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//ohne gueltigen Json-String wird keine HashMap erzeugt.
			sLineWithJson = VectorUtilZZZ.getElementAsString(vecReturn, 1); //Leerstring ist kein gültiger JSON String, darum kommt nix raus.
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.1. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertFalse(objEntry.isParsed());
			
			assertFalse(objEntry.isJson());     //beide Flags sind auf "not use"
			assertFalse(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
			
			
			//###################################################################################
			//### 2.2. Teilberechnungen durchführen - JSON solved, JSON MAP unsolved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, false); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
						
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
						
			//wg USEJSON wird ein JSON-String auf Position 1 gestellt. Ohne gueltigen Json-String wuerde keine HashMap erzeugt.
			sLineWithJson = vecReturn.get(1); 
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertFalse(objEntry.isJsonMap()); //JSON MAP Flag "not used"
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
					
			//######################################################################################
			//### 2.3. Nun die Teilberechnung durchführen JSON unsolved, JSON MAP solved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, false); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);			
			assertFalse(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2))); //in der 2ten Position ist der Tag nach dem gesuchten String		
					
			//2.3.1
			sLineWithJson = vecReturn.get(1); //ist ein gültiger JSON String
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//2.3.2
			sLineWithJson = vecReturn.get(0); //ist kein gültiger JSON String, darum kommt nix raus.
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\nsLineWithJson=" + sLineWithJson);
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());//Also der String ist auch kein gueltiger
			
			//zu 2.3.2. Wert ermitteln
			sValue = HashMapZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
						
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.3. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson()); //!!! obwohl JSON "not used"
			assertTrue(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
	
			
			
			//######################################################################################
			//### 2.4. Nun die Teileberechnung durchführen JSON solved, JSON MAP solved
			btemp = objExpressionSolver.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '"+ IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +" sollte zur Verfügung stehen.", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true); 
			assertTrue("Das Flag '"+ IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +" sollte zur Verfügung stehen.", btemp);
			
			vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
			assertFalse(StringZZZ.isEmpty((String) vecReturn.get(0))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			assertFalse(StringZZZ.isEmpty(vecReturn.get(1).toString())); //in der 1ten Position ist der Tag
			assertFalse(StringZZZ.isEmpty(vecReturn.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String		
			
			//2.4.1.
			sLineWithJson = vecReturn.get(0); //ist kein gültiger JSON String, darum kommt nix raus.
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(0,hm.size());
			
			//2.4.2.
			sLineWithJson = vecReturn.get(1); //ist ein gültiger JSON String
			hm = objExpressionSolver.computeHashMapFromJson(sLineWithJson);
			assertNotNull(hm);
			assertEquals(2,hm.size());
			
			//zu 2.2. Wert ermitteln
			sValue = HashMapZZZ.computeDebugString(hm);	
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe\n" + sValue);
						
			//+++++++++++++++++++++++++++++++++++++++
			//zu 2.2. Entry auswerten
			objEntry = objExpressionSolver.getEntry();
			assertTrue(objEntry.isParseCalled());
			//Wenn gesolved wurde kann man das nicht mehr erkennen, also ob die Aenderung am Parsen oder am Solven liegt  ...
			//... das gilt fuer die allgemeinen Testmethoden. 
			//Aber hier geben wir ja einen expliziten String ohne Substitution vor. 
			assertFalse(objEntry.isParsedChanged());
			assertTrue(objEntry.isParsed());
			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			
			assertFalse(objEntry.isSolveCalled()); //solve spielt hier keine Rolle
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
									
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
}//END class
	
