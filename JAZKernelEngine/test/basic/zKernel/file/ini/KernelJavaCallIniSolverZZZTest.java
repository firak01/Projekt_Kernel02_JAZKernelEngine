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
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.TestUtilZZZ.TestSubtype;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelJavaCallIniSolverZZZTest  extends TestCase {
	public final static String sEXPRESSION_CALL01_PATHSECTION = "ArgumentSection for testCallComputed";
	public final static String sEXPRESSION_CALL01_CLASS_PATHPROPERTY = "JavaClass";
	public final static String sEXPRESSION_CALL01_METHOD_PATHPROPERTY = "JavaMethod";
	public final static String sEXPRESSION_CALL01_DEFAULT = "<Z><Z:Call><Z:Java>"
															    + "<Z:Class>{["
																+ KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_PATHSECTION + "]" 
																+ KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_PATHPROPERTY + "}"
															    + "</Z:Class><Z:Method>{["
																+ KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_PATHSECTION + "]"
																+ KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_PATHPROPERTY + "}"
															    + "</Z:Method>"
																+ "</Z:Java></Z:Call></Z>";
	
	public final static String sCALL01_CLASS_DEFAULT = "basic.zBasic.util.machine.EnvironmentZZZ";
	public final static String sCALL01_METHOD_DEFAULT = "getHostName";
	public final static String sEXPRESSION_CALL01_CLASS_DEFAULT = "<Z:Class>" + KernelJavaCallIniSolverZZZTest.sCALL01_CLASS_DEFAULT + "</Z:Class>";
	public final static String sEXPRESSION_CALL01_METHOD_DEFAULT = "<Z:Method>" + KernelJavaCallIniSolverZZZTest.sCALL01_METHOD_DEFAULT + "</Z:Method>";
	public final static String sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT = "<Z><Z:Call><Z:Java>" + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + "" + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT + "</Z:Java></Z:Call></Z>";
	
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;	
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelJavaCallIniSolverZZZ objExpressionSolver;
	private KernelJavaCallIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelJavaCallIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelJavaCallIniSolverZZZ(objKernel, objFileIniTest, saFlag);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
//			catch (FileNotFoundException e) {			
//				e.printStackTrace();
//			} catch (IOException e) {			
//				e.printStackTrace();
//			}		
	}//END setup
	
	public void testFlagHandling(){
		try{							
		assertTrue(objExpressionSolverInit.getFlag("init")==true);
		assertFalse(objExpressionSolver.getFlag("init")==true); //Nun wäre init falsch
		
		boolean bFlagAvailable = objExpressionSolver.setFlag("usecall", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usecall' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("usecall_java", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag 'usecall_java' sollte zur Verfügung stehen.", bFlagAvailable);
		
		bFlagAvailable = objExpressionSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertFalse("Das Flag 'gibtEsNicht' sollte nicht zur Verfügung stehen.", bFlagAvailable);
		
		
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
	
	
	public void testCompute_JavaCall() {
		String sExpression=null; String sExpressionSubstituted=null; String sExpressionSolved=null; String sPre=null; String sPost=null; String sTag=null; String sTagSolved=null;
		try {
		
		String sHostName = EnvironmentZZZ.getHostName();
		assertNotNull(sHostName);
		
		
		//##################################
		//### Vorgezogener letzter Fehlertest: START

		//C) PRE / POST
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sTag;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.AS_ENTRY, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);
		
		
		
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.DEFAULT, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);
				
				
		//### Vorgezogener letzter Fehlertest: ENDE
		//##################################
	
	
		//A) Teste die Pfadersetzung, die nicht nur im KernelExpresssionIniHandlerZZZ funktionieren soll.
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.DEFAULT,sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);
		
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT; 
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.AS_ENTRY,sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);
		
		
		//B) Test ohne notwendige Pfadersetzung
		sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.DEFAULT,sExpression,  sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);

		sExpression = KernelCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "";
		sPost = "";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.AS_ENTRY,sExpression,  sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);

		//C) PRE / POST
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.DEFAULT, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);
				
		sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
		sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		sExpressionSolved = sHostName;
		sPre = "PRE";
		sPost = "POST";
		sTag = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_CLASS_DEFAULT + KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_METHOD_DEFAULT;		
		sTagSolved = sHostName;
		testCompute_JavaCall_(TestUtilZZZ.TestSubtype.AS_ENTRY, sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved);

		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_JavaCall_(TestUtilZZZ.TestSubtype enumTestSubtype, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn){
		//SIEHE AUCH testCompute_CallWithPrePost
		boolean btemp; int itemp;
				
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost;String sTag; String sTagSolved;
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
			//1a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionIn + sPost;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//1b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionIn + sPost;	
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null;//ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression,sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//1c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionIn + sPost;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			
			//1d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionIn + sPost;
			sExpressionSolved = sPre + sExpressionIn + sPost;
			//sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			//sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht.
			//Der JavaCall-Solve kennt Call garnicht, also nicht berücksichtigen .... sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2. Ohne jegliche Solver-Berechnung (Ergebnisse muessen so sein wie bei Parse)
			//2a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost; //sExpressionSubstituted = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost; //sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//2b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre=sPreIn;
			sPost=sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//2c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre=sPreIn;
			sPost=sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//2d) Ohne jegliche Solver-Berechnung: Solve ... mit Entfernen der umgebenden Z-Tags
			sPre=sPreIn;
			sPost=sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			//aber weil der generelle Solver deaktiviert ist eben nicht: sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);			
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost;	//Beim Parsen werden, wenn wie hier gewuenscht immer der Z-Tag entfernt.
			//aber weil der generelle Solver deaktivert ist eben nicht: sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//+++ 3. Ohne Call-Solver-Berechung
			//3a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = sExpression;

			sTag = null;
			sTagSolved = null;
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			sTag = null;
			sTagSolved = null;
						
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//3c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			
			//ABER: DAS IST DANN DOCH BEIM SOLVEN ANDERS ALS BEIM PARSEN!!!
			//SONST MUESSTE HIER sHostName als Ergebnis erscheinen.
			//ALSO: Die umgebenden Tags müssen drin bleiben
			sExpressionSolved = sExpressionSubstitutedIn;	
			
			sTag = sTagIn;
			
			sTagSolved = sTagSolvedIn;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			
			//Beim Solven ohne Solver, bleibt alles wie es ist, nur substituiert wird
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//3d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre="";
			sPost="";
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			//sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ, false);
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			sExpressionSolved = sExpressionSubstitutedIn;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			sTag = sTagIn;
			
			sTagSolved = sTagSolvedIn;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_Call_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 4. Ohne JavaCallSolver-Berechung
			//4a) Parse ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht. 
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//4c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht. 
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//4d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost;
			sExpressionSolved = sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //auch ohne Solver werden die Pfade substituiert!!!
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);//Zwar gilt: Beim Solven ohne Solver, bleibt alles wie est ist, aber hier wird der Solver generell ausgefuehrt, nur der JAVA-CALL-SOLVER nicht.
			
			//Der reine Tag, ohne umgebende Z-Tags und Call-Tags
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			} else {
				btemp = testCompute_JavaCall_JavaCall_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 5. Mit JavaCallSolver-Berechung
			//5a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen						
			sPre = sPreIn;
			sPost = sPostIn;
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			//false: d.h. Z-Tags sollen drin bleiben sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost;
			//false: d.h. Z-Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);

			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);			
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			
			//5b) Parse ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;		
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			sExpressionSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted, sTagStartZ, sTagEndZ);
			sExpressionSolved = sPre + sExpressionSubstitutedIn + sPost;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ); 
					//sPre + sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT + sPost; //sExpressionSubstitutedIn;			
			//sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved,objExpressionSolver.getTagPartOpening(), objExpressionSolver.getTagPartClosing());
			//sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved, objExpressionSolver.getParentName());
			//true: d.h. keine Z-Tags  sExpressionSolved = ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved);
			sTag = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false); //von aussen nach innen. So bleiben Z-Tags innen(z.B. um den Pfad herum) erhalten.
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelCallIniSolverZZZ.sTAG_NAME, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved,EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			}
			
			//5c) Solve ... ohne Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;		
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;
			sExpressionSolved = sExpressionSolvedIn; 
			sExpressionSolved = sPre + ExpressionIniUtilZZZ.makeAsExpression(sExpressionSolved) + sPost;//Z-Tags kuenstlich draufrechnen.
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
			//5d) Solve ... mit Entfernen der umgebenden Z-Tags
			sPre = sPreIn;
			sPost = sPostIn;		
			sExpression = sPre + sExpressionIn + sPost;
			sExpressionSubstituted = sPre + sExpressionSubstitutedIn + sPost;			
			sExpressionSolved = sPre + sExpressionSolvedIn + sPost;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			if(enumTestSubtype != null && enumTestSubtype == TestUtilZZZ.TestSubtype.AS_ENTRY) {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			}else {
				btemp = testCompute_JavaCall_JavaCall_Solved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			}
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
	private boolean testCompute_JavaCall_Unexpressed_(String sExpressionIn,String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved; 
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
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED;
			
			//####################################################################################
			//### EXPRESSIONSOLVER - NICHT EXPRESSION BEHANDLUNG
			//####################################################################################
													
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Gar keine Expression aktiviert
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true); //soll dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);	//soll dann egal sein		
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);	//soll dann egal sein		
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelCallIniSolverZZZ.sTAG_NAME, false);				
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);	
					
				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnParse());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);	
				
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
				
				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);
				
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
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
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
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		return bReturn;
	}
	
	
	//##############################################################################
	
		private boolean testCompute_JavaCall_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			boolean bReturn = false;
			try {
				boolean btemp; 
				
				String sExpression; String sExpressionSubstituted; String sExpressionSolved;  String sTag=null; String sTagSolved=null; String sPre; String sPost;  
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
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); //soll dann egal sein
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);

				
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
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelCallIniSolverZZZ.sTAG_NAME, false);
						
						//PRE und POST umgebend entfernen!!!
						sExpressionSurroundedTemp = StringZZZ.trimLeft(sExpressionSurroundedTemp, sPre);
						sExpressionSurroundedTemp = StringZZZ.trimRight(sExpressionSurroundedTemp, sPost);
												
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
						assertEquals(sExpressionSurroundedTemp, sValue);
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
					
					sExpressionSurroundedTemp = sExpressionSubstituted;
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
					}
					
					sValue = objEntry.getValue();
					assertEquals(sExpressionSurroundedTemp, sValue);
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
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
			return bReturn;
		}
		
		private boolean testCompute_JavaCall_Call_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			//siehe: testCompute_Call_JavaCall_Unsolved_
			boolean bReturn = false;
			
			try {
				boolean btemp; 
				
				String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagSolved; String sPre; String sPost; 
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
				sTag = sTagIn;
				sTagSolved = sTagSolvedIn;
				
				//Also: Wir testen den "Kind-Solver".
				//Wenn wir den "Kind-Solver" Testen, dann sind die Flags für den "Eltern-Solver" egal
				//Demnach nicht folgendes Flagset verwenden IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.CALL_UNSOLVED;
				//sondern hier in testCompute_JavaCall_Call_Unsolved_:
				IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.CALL_UNSOLVED_JAVACALL_SOLVED;
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
				btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
				assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

				btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
								
				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
				assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
				assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
				
				btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); 
				assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp); //keinen JavaCall - Solver nutzen
			
				boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
				boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
				boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
				
				//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
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
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelJavaCallIniSolverZZZ.sTAG_NAME, false);
						sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelCallIniSolverZZZ.sTAG_NAME, false);				
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

					sValue = objExpressionSolver.getValue();
					assertEquals(sTag, sValue);
													
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
					if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
						sExpressionTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
					}
					sExpressionSubstituted = sExpressionTemp;
					assertEquals(sExpressionSubstituted, sValue);
				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
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
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
			
			return bReturn;
		}

		
	private boolean testCompute_JavaCall_JavaCall_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		//siehe: testCompute_Call_JavaCall_Unsolved_
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
				
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_UNSOLVED;
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp); //keinen JavaCall - Solver nutzen
		
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);	
												
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

				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);
												
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
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
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
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}

	
	private boolean testCompute_JavaCall_JavaCall_Solved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		//siehe: testCompute_Call_JavaCall_Unsolved_
		boolean bReturn = false;
		
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved; 
			String sValue; 		
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry=null;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";		
				
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.JAVACALL_SOLVED;
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp); //keinen JavaCall - Solver nutzen
		
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {				
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);	
												
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.isSurroundingValueToRemove_OnSolve());
				String sExpressionSolvedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSolvedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSolvedTemp;
				assertEquals(sExpressionSolved, sValue);

				sValue = objExpressionSolver.getValue();
				assertEquals(sTagSolved, sValue);
												
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
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
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
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		return bReturn;
	}
	
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01parse(){
		boolean btemp; 
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved;
		String sValue; 
		
		String sTagStart = null; String sTagEnd = null;
		
		String sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
		String sTagEndZ = "</Z>";
	
		
		
		try {
			
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen
			//###############################################

			//erst einmal alle Flags setzen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
						
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT; 
			
			//### OHNE EXPRESSION Verarbeitung
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			//+++ der Gesamtausdruck
			sExpressionSolved = sExpression;	
			sValue = objExpressionSolver.parse(sExpression);			
			assertEquals(sExpressionSolved, sValue);
		
			//+++ nur der Tag selbst
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;//ohne Expression-Auswertung
			sValue = objExpressionSolver.getValue();
			assertEquals(sTagSolved, sValue);
			
			
			//### OHNE SOLVER Verarbeitung
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;

			//+++ der Gesamtausdruck
			sExpressionSolved = sExpression;
			
			//ohne Solver bleibt der Z-Tag im Ausdruck drin:
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
					
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals(sExpressionSolved, sValue);

			//+++ nur der eigene Tagwert
			sExpressionSolved = sExpression;
			
			//Es muss beim Parsen fuer den Wert des Tags selbst(!) ist es egal, ob ein Solver(!) verwendet wird ....
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sTagStart = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);
			
			sTagStart = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Java>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			
			//##################################################################################
			//### PARSEN mit allen aktiviertem Flags durchführen
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//+++ der Gesamtausdruck
			sExpressionSolved = sExpression;
			
			//ohne Solver bleibt der Z-Tag im Ausdruck drin:
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			
			//Beim PARSEN wird - als Ergeniszeile - 
			//der Tag des Solvers nicht entfernt, das passiert erst beim SOLVEN,
			//also nicht:  sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName());
			
			sValue = objExpressionSolver.parse(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			//+++ nur der Tag selbst
			sExpressionSolved = sExpression;
			
			//Allerdings muess beim PARSEN - fuer den Wert des Tags selbst(!) - alle umliegenden Tags enfernt werden.
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sTagStart = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);
			 
			sTagStart = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Java>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);									
			//sExpressionSolved = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
		    		
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//### PARSEN UND SOLVEN mit allen aktivierten Flags durchfuehren
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
											
			//Hier findet nur parse() statt. Darum wird nix aufgeloest, egal welche Flags gesetzt sind.
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
		
			//+++ der Gesamtausdruck
			sExpressionSolved = sExpression;
			
			//ohne Solver bleibt der Z-Tag im Ausdruck drin:			
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			
			//Beim PARSEN wird - als Ergeniszeile - 
			//der Tag des Solvers nicht entfernt, das passiert erst beim SOLVEN,
			//also nicht:  sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName());
			sValue = objExpressionSolver.parse(sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			//+++ nur der Tag selbst
			sExpressionSolved = sExpression;
			
			//Es muss beim Parsen fuer den Wert des Tags selbst(!) egal sein, ob USECALL verwendet wird ....			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sTagStart = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);
			
			sTagStart = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Java>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
		
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionSolved, sValue);	
		//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01solve(){
		boolean btemp; 
		String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sTag; String sTagTemp; String sTagSolved;
		String sValue; 
		String sTagStart; String sTagEnd;		
		String sTagStartZ = "<Z>"; //Zwar wird der Solver nicht ausgefuehrt, aber die Expression wird schon aufgeloest.
		String sTagEndZ = "</Z>";
		
		try {
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen
			//###############################################
			String sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//erst einmal alle Flags setzen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);//Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //Sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);//Sollte dann egal sein			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
			
			//### Ohne EXPRESSION Verarbeitung
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); //Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sExpressionSubstituted;
			
			//der Gesamtausdruck			
			sValue = objExpressionSolver.solve(sExpression);			
			assertEquals(sExpressionSolved, sValue);
			
			//nur der Tag selbst
			sTag = null; //ohne Expression-Auswertung
			sTagSolved = null;//ohne Expression-Auswertung
			sValue = objExpressionSolver.getValue();
			assertEquals(sTagSolved, sValue);
			
			
			//### OHNE SOLVER Verarbeitung
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted; //weil der Solver an sich nicht ausgefuehrt wird, bleibt der Z-Tag drin.
						
			//+++ der Gesmtausdruck			
			//ohne Solver Verarbeitung bleibt der Z-Tag drin
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
				
			sValue = objExpressionSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			//+++ nur der Tagwert selbst (siehe auch PARSE Test)			
			//Es muss beim Parsen fuer den Wert des Tags selbst(!) ist es egal, ob ein Solver(!) verwendet wird ....
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;			
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			sTagStart = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Call>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);
			
			sTagStart = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Java>";
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStart, sTagEnd);							
			//sExpressionParsed = "<Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method>";
			sValue = objExpressionSolver.getValue();
			assertEquals(sExpressionSolved, sValue);
			
		
			//### ohne CALL Verarbeitung
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, false); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sExpressionSubstituted;//aber weil ein Solver an sich ausgefuehrt wird, kommt das Z-Tag weg
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			
			//+++ der Gesamtausdruck			
			sValue = objExpressionSolver.solve(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			//+++ nur der Tagwert selbst
			sTagTemp = sExpressionSubstituted;
			sTagStart = sTagStartZ; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = sTagEndZ;
			sTagTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagTemp, sTagStart, sTagEnd);
						
			sTagStart = "<Z:Call>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Call>";
			sTagTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagTemp, sTagStart, sTagEnd);
			
			sTagStart = "<Z:Java>"; //Ist ja nicht innerhalb des Tags vorhanden
			sTagEnd = "</Z:Java>";
			sTagTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagTemp, sTagStart, sTagEnd);
			
			sTag = sTagTemp;
			sTagSolved = sTag;
			sValue = objExpressionSolver.getValue();
			assertEquals(sTagSolved, sValue);
			
			//### mit CALL Verarbeitung		
			//+++ der Gesamtausdruck
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
														
			//+++ der Gesamtausdruck
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus. 
			//MERKE: Z:Call-Tag raus, da es fuer JavaCallIniSolver dazu eine custom-Methode gibt den "Elterntag/Parenttag" zu entfernen. Auch wenn das nicht das eigentlich verwendete Solver-Objekt ist.
			//Also nicht: sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>"; ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_DEFAULT;
			sExpressionSubstituted = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			sExpressionSolved = sHostName; ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			sValue = objExpressionSolver.solve(sExpression);						
			assertEquals(sExpressionSolved, sValue);
			
			//+++ nur der Tagwert selbst (ABWEICHEND VOM PARSE Ausdruck)
			sTag = sHostName;
			sTagSolved = sTag;
			
			sValue = objExpressionSolver.getValue();
			assertEquals(sTagSolved, sValue);
			
		//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
		
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01parseAsEntry(){
		try {		
			boolean btemp; 
											
			String sExpression; String sExpressionSolved; IKernelConfigSectionEntryZZZ objEntry;
			String sValue; boolean bValue;
			String sHostName; String sClassname; String sMethodname;
			
			String sTagStartZ;	String sTagEndZ; 
						
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			
			
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionSolver.parseAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			sExpressionSolved = sExpression;
			
			//Beim PARSEN belibt der Z-Tag enthalten
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, "Z");
			
			//Beim PARSEN bleibt der Tagname des Solvers im Gesamtstring erhalten.
			//Darum hier nicht: sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName());
			assertEquals("Ohne Auflösung soll Ausgabe gleich Eingabe sein, abzueglich der Z-Tags, die wg. Parse wegfallen.",sExpressionSolved, sValue);
			
			bValue = objEntry.isCall();
			assertTrue(bValue); //beim Parsen gesetzt
			
			bValue = objEntry.isJavaCall(); //beim Parsen gesetzt
			assertTrue(bValue);
			
			//ohne solve wird das bisher nicht gemacht
			sClassname = objEntry.getCallingClassname();
			assertNull("NULL erwartet. Wert ist aber '" + sClassname + "'", sClassname);			
			
			//ohne solve wird das bisher nicht gemacht
			sMethodname = objEntry.getCallingMethodname();
			assertNull("NULL erwartet. Wert ist aber '" + sMethodname + "'", sMethodname);

			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}		
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute01solveAsEntry(){
		try {		
			boolean btemp;  										
			String sExpression; String sExpressionSolved; 
			String sValue; boolean bValue;
			IKernelConfigSectionEntryZZZ objEntry=null;
			String sHostName; String sClassname; String sMethodname;
			
			String sLineWithExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
			
			//###############################################	
			//### JAVA:CALL Ausdruck aus INI-Datei lesen. Es geht um das Entry-Objekt
			//###############################################
							
			//### Nun die Gesamtberechnung durchführen
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); //nun wird der Wert nach dem Parsen zumindest geaendert 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);		
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
			sExpression = KernelJavaCallIniSolverZZZTest.sEXPRESSION_CALL01_SUBSTITUTED_DEFAULT;
						
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus UND Z:Call-Tag auch raus			
			sHostName = EnvironmentZZZ.getHostName(); ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objEntry = objExpressionSolver.solveAsEntry(sLineWithExpression);
			assertNotNull(objEntry);
			sValue = objEntry.getValue();
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
			
			//+++ der Gesamtausdruck
			//Es sollte nur der Rechnername uebrigbleiben... Z-Tag raus. 
			//MERKE: Z:Call-Tag raus, da es fuer JavaCallIniSolver dazu eine custom-Methode gibt den "Elterntag/Parenttag" zu entfernen. Auch wenn das nicht das eigentliche Solver-Objekt ist.
			//Also nicht: sExpressionSolved = "<Z:Call>" + sHostName + "</Z:Call>"; ////Den Rechnernamen dynamisch ermitteln..., z.B.: "HannibalDEV04bVM";
			sExpressionSolved = sHostName;
			assertEquals(sExpressionSolved, sValue);
	
			//Merke: Da der Call-Solver nicht verwendet wird, kann hier nur false sein...
			bValue = objEntry.isCall(); //wird beim Parsen gesetzt
			assertTrue(bValue);
			
			bValue = objEntry.isJavaCall(); //wird beim Parsen gesetzt
			assertTrue(bValue);
			
			sClassname = objEntry.getCallingClassname();		
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe3: '" + sClassname + "'\n");
			assertEquals("basic.zBasic.util.machine.EnvironmentZZZ",sClassname);
			
			//ohne solve wird das bisher nicht gemacht
			sMethodname = objEntry.getCallingMethodname();			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausgabe4: '" + sMethodname + "'\n");
			assertEquals("getHostName",sMethodname);
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}		
}//END class
		

