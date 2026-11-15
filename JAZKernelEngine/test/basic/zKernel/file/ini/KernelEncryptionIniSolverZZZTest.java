package basic.zKernel.file.ini;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import junit.framework.TestCase;

public class KernelEncryptionIniSolverZZZTest extends TestCase {
	protected final static String sEXPRESSION_ENCRYPTION01_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z:Encrypted></Z>";//= "abcde";	
	protected final static String sEXPRESSION_ENCRYPTION02_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnumeric</Z:Cipher><z:KeyNumber>5</z:KeyNumber><Z:FlagControl>USENUMERIC</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	protected final static String sEXPRESSION_ENCRYPTION03_DEFAULT = "<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>5</z:KeyNumber><z:CharacterPool> abcdefghijklmnopqrstuvwxyz?!</z:CharacterPool><z:FlagControl>USEUPPERCASE</Z:FlagControl><Z:Code>fghij</Z:Code></Z:Encrypted></Z>";
	//TODOGOON20220929; //siehe auskommentierten testCompute04: Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5
	
	private File objFile;
	private IKernelZZZ objKernel;
	private FileIniZZZ objFileIniTest=null;
	
	/// +++ Die eigentlichen Test-Objekte	
	private KernelEncryptionIniSolverZZZ objExpressionSolver;
	private KernelEncryptionIniSolverZZZ objExpressionSolverInit;
	
	

	protected void setUp(){
		try {			
						
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			//#### Ein init TestObjekt
			String[] saFlagInit = {"init"};
			objExpressionSolverInit = new KernelEncryptionIniSolverZZZ(objKernel, saFlagInit);
			
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
			objExpressionSolver = new KernelEncryptionIniSolverZZZ(objKernel, objFileIniTest, saFlag);
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
		
		btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
		assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION +"' sollte zur Verfügung stehen.", btemp);
		
		//Dummy test
		btemp = objExpressionSolver.setFlag("gibtEsNicht", false); //Ansonsten wird der Wert sofort ausgerechnet
		assertFalse("Das Flag 'gibtEsNicht' sollte nicht zur Verfügung stehen.", btemp);
		
		//Flags, die es zwar gibt, aber nicht in der encryption!!!
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true); //soll dann egal sein
		assertFalse("Flag unerwartet vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL, true); //soll dann egal sein
		assertFalse("Flag unerwartet vorhanden '" + IKernelCallIniSolverZZZ.FLAGZ.USECALL + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA, true);
		assertFalse("Flag unerwartet vorhanden '" + IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA + "'", btemp);
		
		//+++++++++++++++++
		btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
		assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
	
		btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
		assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
					
		btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
		btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
		assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
		
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
	
	public void testCompute_Enrcyption() {
		String sExpressionSource=null;
//		try {
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			testCompute_Encryption_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_Encryption_(String sExpressionIn){
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpression; String sExpressionSubstituted; 
		String sExpressionSolved; String sExpressionSolvedTagless;
		String sPre; String sPost; String sTag; String sTagSolved;
		IKernelConfigSectionEntryZZZ objEntry; ReferenceZZZ<IKernelConfigSectionEntryZZZ>objSectionEntryReference;
	
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 1) Ohne jegliche Expression-Berechnung
			//1a)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1b)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;	
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//1c)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression; 	
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
				
			//1d)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2) Ohne Solver-Berechung
			//2a)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = sExpressionSubstituted; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile			
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, objExpressionSolver.getName(), false);								
						
			sTagSolved = sExpressionSolved; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, objExpressionSolver.getName(), false);								
			
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//2b)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sPre = "";
			sPost = "";
			sTag = sExpressionSubstituted; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile			
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, objExpressionSolver.getName(), false);								
						
			sTagSolved = sExpressionSolved; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, objExpressionSolver.getName(), false);								
			
			//Beim Parsen ohne Solver, bleibt sogar das Encryption-Tag drin, auch wenn sonst die Tags entfernt werden.
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//2c) Beim Solven ohne Solver, bleibt alles wie est ist.
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = sExpressionSubstituted; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile			
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, objExpressionSolver.getName(), false);								
						
			sTagSolved = sExpressionSolved; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, objExpressionSolver.getName(), false);								
			
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//2d)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			//ohne Solver bleibt auch immer der Z-Tag drin.  sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);

			sPre = "";
			sPost = "";
			
			sTag = sExpressionSubstituted; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile			
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, sTagStartZ, sTagEndZ, false);
			sTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTag, objExpressionSolver.getName(), false);								
						
			sTagSolved = sExpressionSolved; //der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, sTagStartZ, sTagEndZ, false);
			sTagSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sTagSolved, objExpressionSolver.getName(), false);								
						
			//Beim Solven ohne Solver, werden nur die äusseren Z-Tags ggfs. entfernt.
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 3) Ohne Encryption-Solver-Berechung
			//3a)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//3b)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
		
			//Beim Parsen ohne encryption, muss doch dieser encryption - Tag drinbleiben. Hier werden also nur die aeussern Z-Tags entfernt.
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//3c)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			//Beim Solven ohne encryption, bleibt alles an Tags drin.
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
		
			//3d) Aber generell wird der Solver ausgefuehrt
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;	
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";			
			//Beim Solven ohne encryption muss dieser encryption - Tag drinbleiben
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 4) Mit Encryption-Berechnung
			//4a) nur parsen bringt keinen Unterschied, wenn die Tags drinbleiben sollen
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression; 
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			//false: d.h. Tags sollen drin bleiben sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);

			//4b) Werdem beim reinen Parsen die umgebenden Tags entfernt, dann bleibt trotzem das encryption Tag drin. Das eigentliche Aufloesen findet aber nicht statt.
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE);
			
			//4c)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSubstituted = sExpression;
			sExpressionSolved = sTagStartZ + "abcde" + sTagEndZ;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			sPre="";
			sPost="";
			sTag="TODO ALS KONSTANTE";
			sTagSolved="TODO ALS KONSTANTE";
			//Wichtig: z:Encrypted soll aus dem Ergebnis weg sein, wg. Aufloesen!!! Auch wenn die umgebenden Z-Tags drin bleiben.   //"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";
			//String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);

			//4d)
			sExpression = sExpressionIn;		
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "abcde";//"<Z><Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code></Z>";
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE);
							
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	
	private boolean testCompute_Encryption_Unexpressed_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved;
			String sValue; Vector3ZZZ<String> vecValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
			
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
			//### EXPRESSION - NICHT EXPRESSION BEHANDLUNG .solve
			//####################################################################################
			
			//Anwenden der ersten Formel, ohne Berechnung oder Formelersetzung						
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn;
			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, false); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
						
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //soll dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade substituiert.
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;		
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
				
				sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}
			
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
				
				assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
			
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

//Nachtestutil verschieben
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference,  objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				assertFalse(objEntry.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
				assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also "unveraendert"
									
				assertFalse(objEntry.isPathSubstituted());
				assertFalse(objEntry.isVariableSubstituted());
								
				assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

//Nach TestUtil verschieben
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objEntry = objExpressionSolver.parseAsEntry(sExpression,  objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
				
//				Nach TestutilVerschieben
				//assertTrue(objEntry.isParseCalled()); //Der Parse-Schritt wurde gemacht.
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				//assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
				//assertFalse(objEntry.isPathSubstituted());
//				assertIsPathSubstituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				//assertFalse(objEntry.isVariableSubstituted());
//				assertIsVariableSubstituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				
				//assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert"
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
				sValue = objEntry.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
									
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				sExpression = sExpressionIn;
				sExpressionSolved = sExpressionSolvedIn;
				objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference,  objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

//NAch TestUtil verschieben
				//assertFalse(objEntry.isParseCalled()); //Der Parse-Schritt wurde NICHT gemacht.
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isParsedChanged()); //es wird ja nix gemacht, also immer "unveraendert"
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);)
				
				//assertFalse(objEntry.isPathSubstituted());
//				assertIsPathSubstituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isVariableSubstituted());
//				assertIsVariableSubstituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);

				//assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert"
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
								
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);

				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
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
	
	private boolean testCompute_Encryption_Unsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved;
			String sValue; Vector3ZZZ<String> vecValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
				
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION - CALL NICHT .solve
			//####################################################################################
						
			//Anwenden des Ausdrucks ohne Solver - Aufruf
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false); //sonst wird ausgerechnet
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //sollte dann egal sein
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
									
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference,  objEnumSurrounding.getSurroundingValueUsedForMethod());
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
									
				//der Wert des Tags selbst unterscheidet sich immer vom Wert der Zeile
				sValue = objExpressionSolver.getValue();
				assertEquals(sTag, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				
				/*sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				
				//assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);)
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference,  objEnumSurrounding.getSurroundingValueUsedForMethod());
			
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);

				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);

				//assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {
				objEntry = objExpressionSolver.parseAsEntry(sExpression,  objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);
				sValue = objEntry.getValue();
				
				String sExpressionSurroundedTemp = sExpressionSubstituted;
				if(bUseExpressionGeneral && bUseParser && objEnumSurrounding.isSurroundingValueToRemove_OnParse()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSubstituted = sExpressionSurroundedTemp;
				assertEquals(sExpressionSubstituted, sValue);
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);	
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry = objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertNotNull(objEntry);				
				sValue = objEntry.getValue();
				
				String sExpressionSurroundedTemp = sExpressionSolved;
				if(bUseExpressionGeneral && bUseSolver && objEnumSurrounding.isSurroundingValueToRemove_OnSolve()) {
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				}
				sExpressionSolved = sExpressionSurroundedTemp;
				assertEquals(sExpressionSolved, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
				//assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
				assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
								
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
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
	
	private boolean testCompute_Encryption_EncryptionSolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved;
			String sValue; Vector3ZZZ<String> vecValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_SOLVED;
			
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
			
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
									
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
			
				
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {							
				sValue = objExpressionSolver.parse(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);

				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sExpressionSolved = sExpressionSolvedIn;	//Der Wert selbst hat dann keine Tags drumherum.			
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);				
				sValue = objExpressionSolver.getValue();					
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);

				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				sExpressionSolved = sExpressionSolvedIn;
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*sExpressionSolved = sExpressionSolvedIn;
				if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert"
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE)) {				
				sValue = objExpressionSolver.solve(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());
				assertEquals(sExpressionSolved, sValue);
				
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sExpressionSolved = sExpressionSolvedIn;	//Der Wert selbst hat dann keine Tags drumherum.			
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
				sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);				
				sValue = objExpressionSolver.getValue();					
				assertEquals(sExpressionSolved, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*if(objEnumSurrounding.getSurroundingValueUsedForMethod()) {
					assertTrue(objEntry.isParsedChanged());
				}else {
					assertFalse(objEntry.isParsedChanged());						
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertTrue(objEntry.isSolvedChanged());
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertTrue(objEntry.isDecrypted());
				assertNotNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht. 
								
				//assertFalse(objEntry.isCall());
//				assertISCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			}
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			//+++ Variante fuer den AsEntry-Test
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY)) {				
				objEntry = objExpressionSolver.parseAsEntry(sExpression, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
												
				sValue = objExpressionSolver.getValue();				
				assertEquals(sExpressionSolved, sValue);

				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
						
				/*if(objEnumSurrounding.getSurroundingValueUsedForMethod()) {
					assertTrue(objEntry.isParsedChanged());
				}else {
					assertFalse(objEntry.isParsedChanged());						
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert"
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
				assertFalse(objEntry.isDecrypted()); //Ist kein solve-Schritt involviert.
				assertNull(objEntry.getValueDecrypted());  				
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
			}
			
			//+++ ... solve verhält sich NICHT wie parse(), bei solve wird aufgeloest...
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY)) {
				objEntry= objExpressionSolver.solveAsEntry(sExpression, objSectionEntryReference, objEnumSurrounding.getSurroundingValueUsedForMethod());				
				assertNotNull(objEntry);
				
				//Der Wert des Tags unterscheidet sich vom Wert der gesamt geparsten Zeile
				sValue = objExpressionSolver.getValue();
				assertEquals(sExpressionSolved, sValue);
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*if(objEnumSurrounding.getSurroundingValueUsedForMethod()) {
					assertTrue(objEntry.isParsedChanged());
				}else {
					assertFalse(objEntry.isParsedChanged());						
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertTrue(objEntry.isSolveCalled()); //Der Solve-Schritt wurde gemacht.
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertTrue(objEntry.isSolvedChanged()); //es wird ja nix gemacht, also "unveraendert" 
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
				assertTrue(objEntry.isDecrypted()); //Ist ja ein solve-Schritt involviert und es wurde aufgeloest.
				assertNotNull(objEntry.getValueDecrypted());  				
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
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
		
	
	
	
	
	
	
	private boolean testCompute_Encryption_EncryptionUnsolved_(String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn, String sTagIn, String sTagSolvedIn, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase) {
		boolean bReturn = false;
		try {
			boolean btemp; 
			
			String sExpression;  String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost; String sTag; String sTagSolved;
			String sValue; Vector3ZZZ<String> vecValue;				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			IKernelConfigSectionEntryZZZ objEntry = null;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionSubstitutedIn;
			sExpressionSolved = sExpressionSolvedIn;
			sPre = sPreIn;
			sPost = sPostIn;
			sTag = sTagIn;
			sTagSolved = sTagSolvedIn;
			
			IEnumSetMappedTestFlagsetZZZ objEnumFlagset = EnumSetMappedTestCaseFlagsetTypeZZZ.ENCRYPTION_UNSOLVED;
			
			//####################################################################################
			//### EXPRESSION .solve
			//####################################################################################
			//Nur Expression ausrechnen, ist aber unverändert vom reinen Ergebnis her.		
			btemp = objExpressionSolver.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);

			btemp = objExpressionSolver.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
		
			btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Das Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER+"' sollte zur Verfügung stehen.", btemp);
			
			btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false);
			assertTrue("Flag nicht vorhanden '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "'", btemp);
		
			boolean bUseExpressionGeneral = objExpressionSolver.isExpressionEnabledGeneral();
			boolean bUseSolver = objExpressionSolver.isSolverEnabledGeneral();
			boolean bUseParser = objExpressionSolver.isParserEnabledGeneral() && objExpressionSolver.isParserEnabledCustom(); //objExpressionSolver.isParserEnabledThis();
			
			
			//+++ ... parse ist nicht solve... also wird hier nichts aufgeloest, aussser die Pfade
			if(objEnumTestCase.equals(EnumSetMappedTestCaseSolverTypeZZZ.PARSE)) {					
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
					sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);			
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2Compare=" + sExpressionSurroundedTemp);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sValue=" + sValue);
					assertEquals(sExpressionSurroundedTemp, sValue); //Beim parse fist Vector wird nie der Z-Tag drum herum entfernt. Das ist Aufgabe von parse().
				}
				
				//+++ Der Entry Wert
				objEntry = objSectionEntryReference.get();
				assertNotNull(objEntry);
				
				//spannend, nicht aufgeloest, aber geparsed!!!
				//allerdings gibt es nur einen Unterschied, wenn die umgebenden Tags entfernt wurden!!!
//				if(bRemoveSurroundingSeparatorsOnSolve) {
//					assertTrue(objEntry.isParsed());  
//				}else {
//					assertFalse(objEntry.isParsed());
//				}
				
				//Falls Pfade substituiert werden, gibt es ebenfalls einen Unterschied, der fuer isParsedChanged sorgt.
//				if(bRemoveSurroundingSeparators | objEntry.isPathSubstituted()) {
//					assertTrue(objEntry.isParsed());
//				}else {
//					assertFalse(objEntry.isParsed());
//				}
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
	
				/*if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolveCalled()); //Der konkrete Solver ist nicht involviert
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertFalse(objEntry.isDecrypted());				
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
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
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled()); //Der parse Schritt wird ausgefuehrt.
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
						
				/*if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
				
				//assertFalse(objEntry.isPathSubstituted());
//				assertIsPathSubsituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isVariableSubstituted());
//				assertIsVariableSubstituted(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);	
				
				//assertTrue(objEntry.isSolveCalled()); //der solve Schritt wird ausgefuehrt
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged()); //der Solver ist ja abgestellt
//				assertIsSolvedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
				assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
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
				

				//+++++++++++++++++++++++++++++++++++++++++++++++++++
				//TODOGOON ab hier in testutil packen.....
				
				//assertTrue(objEntry.isParseCalled());
//				assertIsParseCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				/*if(sExpression.equals(sExpressionSolved)) {
					assertFalse(objEntry.isParsedChanged());						
				}else {
					assertTrue(objEntry.isParsedChanged());
				}*/
//				assertIsParsedChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolveCalled());
//				assertIsSolveCalled(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isSolvedChanged());
//				assertIsSolveChanged(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertFalse(objEntry.isDecrypted());
				assertNull(objEntry.getValueDecrypted()); 				
				
				//assertFalse(objEntry.isCall());
//				assertIsCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				//assertFalse(objEntry.isJavaCall());
//				assertIsJavaCall(objExpressionSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				
				assertNull(objEntry.getCallingClassname());
				assertNull(objEntry.getCallingMethodname());
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
	
		
	
	
	
	//##########################################################
	//##########################################################
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testCompute_EncryptionAsEntry(){
		String sExpressionSource=null;
//		try {
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION02_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION03_DEFAULT;
			testCompute_EncryptionAsEntry_(sExpressionSource);
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	/**void, Test: Reading an entry in a section of the ini-file
	 * 
	 * @author Fritz Lindhauer, 05.05.2023, 08:54:30
	 */
	private void testCompute_EncryptionAsEntry_(String sExpressionIn){
		boolean btemp; int itemp;
		
		String sSection; String sProperty;
		String sExpression; String sExpressionSubstituted; 
		String sExpressionSolved; String sExpressionSolvedTagless;
		String sPre; String sPost; String sTag; String sTagSolved;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objSectionEntryReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		IKernelConfigSectionEntryZZZ objEntry = null;
			
		String sValue;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		try {		
					
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST START
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
				
			//+++ Mit Encryption-Berechnung
			
			//c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			
			//+++++++ VORGEZOGENER LETZTER FEHLERTEST ENDE
			
			
			
			//###########################
		    //### objExpression
			//#########################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 1) Ohne jegliche Expression-Berechnung
			//1a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//1b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//1c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			//1d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unexpressed_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 2) Ohne Solver-Berechung		
			//2a) Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//2b) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpression = sExpressionIn;
			sExpressionSolved = sExpression;
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);

			//2c)  Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//2d) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_Unsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);

			
			
			//+++ 3) Ohne Encryption-Berechung
			//3a) Wenn die Tags drinbleiben sollen ist es egal, ob der Solver an oder aus ist.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//3b) Ist der Solver aus, dann bleiben beim Parsen die Tags des Solver drin, obwohl Tags entfernt werden sollen.
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//3c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//3d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression; 
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			//sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME, false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionUnsolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ 4) Mit Encryption-Berechnung
			//4a)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
		
			//4b)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = sExpression;//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, KernelEncryptionIniSolverZZZ.sTAG_NAME);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.PARSE_AS_ENTRY);
			
			//4c)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
			//4d)
			sExpression = sExpressionIn;
			sExpressionSubstituted = sExpressionIn;
			sExpressionSolved = "<Z><Z:Encrypted>abcde</Z:Encrypted></Z>";//Der Wert des Tags selbst unterscheidet sich vom Wert der gesamt geparsten Zeile und hat nie umgebende Tags des solvers selbst drumherum
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, sTagStartZ, sTagEndZ, false);
			sExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSolved, objExpressionSolver.getName(), false);
			sPre = "";
			sPost = "";
			sTag = "MUSS AUS KONSTANTE KOMMEN";
			sTagSolved = "MUSS AUS KONSTANTE KOMMEN";
			btemp = testCompute_Encryption_EncryptionSolved_(sExpression, sExpressionSubstituted, sExpressionSolved, sPre, sPost, sTag, sTagSolved, EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE, EnumSetMappedTestCaseSolverTypeZZZ.SOLVE_AS_ENTRY);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}

	

	
//	/** void, Test: Reading an entry in a section of the ini-file
//	* Lindhauer; 22.04.2006 12:54:32
//	 */
//	public void testCompute04(){
//			TODOGOON20220929; //Mache eine richtige Entschlüsselung mit AES UND Danach noch testCompute05 mit MD5			
//			String sValue; String sExpression; String sExpressionSource; String sExpressionSource2;String sExpessionSourceFormulaMath;
//			String sTagStartZ;	String sTagEndZ;
//			boolean btemp;
//			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION04_DEFAULT;
//			try {				
//				btemp = objExpressionSolver.setFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
//				assertTrue("Flag nicht vorhanden '" + IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
//				
//				btemp = objExpressionSolver.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); 
//				assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
//				
//				//Nun erst werden Pfade ersetzt
//				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //muss egal sein, da EXPRESION = FALSE //muss egal sein, wenn EXPRESIONSOLVER = FALSE
//				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
//							
//				//Nun erst werden Variablen ersetzt			
//				btemp = objExpressionSolver.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);
//				assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
//				
//				//noch nicht sofort entschluesseln
//				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, false); //Ansonsten wird der Wert sofort ausgerechnet
//				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
//											
//				//### Teilberechnungen durchführen
//				Vector<String> vecReturn = objExpressionSolver.parseFirstVector(sExpressionSource);
//				assertTrue(StringZZZ.isEmpty(vecReturn.get(0))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
//				assertFalse(StringZZZ.isEmpty(vecReturn.get(1))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.
//				assertTrue(StringZZZ.isEmpty(vecReturn.get(2))); //in der 0ten Position ist der String vor der Encryption, in der 3ten Position ist der String nach der Encryption.		
//				
//				//Gesamtberechnung durchführen
//				sTagStartZ = "<Z>";
//				sTagEndZ = "</Z>";
//				sExpression = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sExpressionSource, sTagStartZ, sTagEndZ);			
//				
//				sValue = objExpressionSolver.parse(sExpressionSource);
//				assertEquals(sExpression, sValue);
//			
//				//#############################################################
//				//Sofort entschluesseln
//				btemp = objExpressionSolver.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true); //Ansonsten wird der Wert sofort ausgerechnet
//				assertTrue("Das Flag '" + IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION + "' sollte zur Verfügung stehen.", btemp);
//												
//				sExpression = "abcde";			
//				
//				sValue = objExpressionSolver.parse(sExpressionSource);
//				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "\tDebugausagabe: '" + sValue + "'\n");
//				assertEquals(sExpression, sValue);
//				
//				//++++++++++++++++++++++++++++++++++++++++++++++++++								
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
//	}
	
}//END class
	
