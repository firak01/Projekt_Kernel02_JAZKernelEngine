package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionHandlingMainTypeZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestCaseZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestFlagsetZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedTestSurroundingZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.IParseZZZ;
import basic.zBasic.util.xml.tagsimple.ITagBasicChildZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class TestUtilAsTestZZZ extends TestCase{
	
	//Diese Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
	
	//Diese Konstanten werden in einem enum verwendet. 
	//Diese finale Konstanten sind notwendig, um in einer switch...case Abfrage genutzt zu werden.
	//Das funktioniert nämlich nicht, da es keine finale Konstante ist   case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():	
	public static final String sFLAGSET_UNEXPRESSED="uex";
	public static final String sFLAGSET_UNPARSED="upa";
	public static final String sFLAGSET_UNSOLVED="uso";
	public static final String sFLAGSET_UNPARSED_UNSOLVED="upa_uso";
	
	public static final String sFLAGSET_FORMULA_UNSOLVED="fuso";
	public static final String sFLAGSET_FORMULA_SOLVED="fso";
	
	public static final String sFLAGSET_MATH_UNSOLVED="fmuso";
	public static final String sFLAGSET_MATH_SOLVED="fmso";
	
	public static final String sFLAGSET_SOLVED="so";
	public static final String sFLAGSET_PATH_UNSUBSTITUTED="pusu";
	public static final String sFLAGSET_PATH_SUBSTITUTED="psu";
	public static final String sFLAGSET_VARIABLE_UNSUBSTITUTED="vusu";
	public static final String sFLAGSET_VARIABLE_SUBSTITUTED="vsu";
	
	//ist nur uex: public static final String sFLAGSET_CALL_UNEXPRESSED="cuex";
	public static final String sFLAGSET_CALL_UNSOLVED="cus";
	public static final String sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED="cusjcs";
	public static final String sFLAGSET_CALL_SOLVED="cs";	
	public static final String sFLAGSET_JAVACALL_UNSOLVED="jcus";
	public static final String sFLAGSET_JAVACALL_SOLVED="jcs";	
	
	//ist nur uex: public static final String sFLAGSET_JSON_UNEXPRESSED="juex";
	public static final String sFLAGSET_JSON_SOLVED="js";
	public static final String sFLAGSET_JSON_UNSOLVED="jus";
	
		
	//ist nur uex:  public static final String sFLAGSET_JSONARRAY_UNEXPRESSED="jauex";
	//ist nur jaus: public static final String sFLAGSET_JSONARRAY_JSONARRAY_UNSOLVED="jajaus";
	public static final String sFLAGSET_JSONARRAY_SOLVED="jas";
	public static final String sFLAGSET_JSONARRAY_UNSOLVED="jaus";
	
		
	//ist nur uex: public static final String sFLAGSET_JSONMAP_UNEXPRESSED="jmuex";
	public static final String sFLAGSET_JSONMAP_SOLVED="jms";
	public static final String sFLAGSET_JSONMAP_UNSOLVED="jmus";
	
	
	public static final String sFLAGSET_ENCRYPTION_SOLVED="encs";
	public static final String sFLAGSET_ENCRYPTION_UNSOLVED="encus";
	
	public static final String sCASE_PARSE="p";
	public static final String sCASE_SOLVE="s";
	public static final String sCASE_PARSE_AS_ENTRY="pae";
	public static final String sCASE_SOLVE_AS_ENTRY="sae";
	public static final String sCASE_AS_ENTRY="ae";
	
		
	
	private TestUtilAsTestZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
	
	
	public static boolean assertIsFormulaSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bSolveTested = objEnumSurrounding ==EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE || objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_KEEP;
			boolean bParseTested = objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE || objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_KEEP;
			if(bSolveTested && objEntry.isSolved()) {
				assertTrue(objEntry.isFormulaSolveCalled());
				assertTrue(objEntry.isFormulaSolved());
				bReturn = true;
			}else if( bParseTested && objEntry.isParsed()) {
				assertFalse(objEntry.isFormulaSolveCalled());
				assertFalse(objEntry.isFormulaSolved());
			}else {
				assertFalse(objEntry.isFormulaSolveCalled());
				assertFalse(objEntry.isFormulaSolved());
			}
		}//end main;
		return bReturn;
	}
	
	
	
	public static boolean assertIsFormulaSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{					
			if(!objEntry.isFormula()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()){ //.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//#Bei normalem .solve() gilt: Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Aber hier wird ein "Untersolver" getestet. Also normieren.
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolveFormula, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolveFormula, normiert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParseFormula, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParseFormula normiert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (on...Formula unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (on...Formula unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isFormulaSolved()) {
					if(objEntry.isSubstitutedChanged()){ 
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							assertFalse(objEntry.isFormulaSolvedChanged());
							bReturn = false;
						}else {
							assertTrue(objEntry.isFormulaSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							assertFalse(objEntry.isFormulaSolvedChanged());
							bReturn = false;
						}else {
							assertTrue(objEntry.isFormulaSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					assertFalse(objEntry.isFormulaSolvedChanged());
					bReturn = false;
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isFormulaSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(objEntry.isSubstitutedChanged()){ 
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							assertFalse(objEntry.isFormulaSolvedChanged());
							bReturn = false;
						}else {
							assertTrue(objEntry.isFormulaSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							assertFalse(objEntry.isFormulaSolvedChanged());
							bReturn = false;
						}else {
							assertTrue(objEntry.isFormulaSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					assertFalse(objEntry.isFormulaSolvedChanged());
					bReturn = false;
				}								
			}						
		}//end main;
		return bReturn;
}
	
	
	public static boolean assertIsParseCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			
			assertTrue(objEntry.isParseCalled());
			bReturn = true;
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsParsed(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(objEntry.isParseCalled()){
				if(objSolver.isParserEnabledCustom() && objSolver.isParserEnabledThis()) {
					assertTrue(objEntry.isParsed());
					bReturn = true;
				}else {
					assertFalse(objEntry.isParsed());
					bReturn = false;
				}
			}else {
				assertFalse(objEntry.isParsed());
				bReturn = false;
			}
					
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsParsedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			//Bei ...IsSolvedChanged wuerde man solved-Ergebnis vergleichen
			//String sExpression2compareWithExpression =sExpressionSolved;
			//String sExpression2compare = sExpression;
			//if(objEntry.isSubstitutedChanged()) {
			
			
			if(objEntry.isParsed()) {
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isParsed()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, veraendert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				if(objEntry.isParsed()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
						assertFalse(objEntry.isParsedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isParsedChanged());
						bReturn = true;
					}
				}
			}else {
				assertFalse(objEntry.isParsedChanged());
				bReturn = false;				
			}
			
			//Bei ...IsSolvedChanged zu verwendender else-Teil			
//			}else {
//				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isParsed()) {
//					sExpression2compareWithExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
//					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, veraendert)=" + sExpression2compareWithExpression);
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, veraendert)=" + sExpression2compare);
//				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isParsed()) {
//					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
//					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
//				}else {
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
//					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
//				}
//				
//				if(objEntry.isParsed()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
//					if(sExpression2compareWithExpression.equals(sExpression2compare)) { 
//						assertFalse(objEntry.isParsedChanged());
//						bReturn = false;
//					}else {
//						assertTrue(objEntry.isParsedChanged());
//						bReturn = true;
//					}
//				}
//			}
		}//end main;
		return bReturn;
	}
		
	//##########################################
	public static boolean assertIsSubstituteCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsParsed(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isSubstituteCalled());
				bReturn = true;
			}else {
				assertFalse(objEntry.isSubstituteCalled());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsSubstituted(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isSubstituteCalled());
				bReturn = true;
			}else {
				assertFalse(objEntry.isSubstituteCalled());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
		
	public static boolean assertIsSubstitutedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, veraendert)=" + sExpressionSubstituted2compare);
			}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
			}
			
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
					assertFalse(objEntry.isSubstitutedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
					bReturn = true;
				}
			}else {
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
					assertFalse(objEntry.isSubstitutedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isSubstitutedChanged());
					bReturn = true;					
				}
			}
		}//end main;
		return bReturn;
	}
		
	//#############################################################
	public static boolean assertIsPathSubstituteCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isPathSubstituteCalled());
				bReturn = true;
			}else {
				assertFalse(objEntry.isPathSubstituteCalled());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsPathSubstituted(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsPathSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isPathSubstituted());
				bReturn = true;
			}else {
				assertFalse(objEntry.isPathSubstituted());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsPathSubstitutedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isPathSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, veraendert)=" + sExpressionSubstituted2compare);
			}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isPathSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
			}
			
			if(objEntry.isPathSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
					assertFalse(objEntry.isPathSubstitutedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isPathSubstitutedChanged());
					bReturn = true;
				}
			}
		}//end main;
		return bReturn;
	}
	
	//##########################################
	public static boolean assertIsVariableSubstituteCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isVariableSubstituteCalled());
				bReturn = true;
			}else {
				assertFalse(objEntry.isVariableSubstituteCalled());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsVariableSubstituted(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			
			if(assertIsSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
				assertTrue(objEntry.isVariableSubstituted());
				bReturn = true;
			}else {
				assertFalse(objEntry.isVariableSubstituted());
				bReturn = false;
			}
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsVariableSubstitutedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpression;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isPathSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, veraendert)=" + sExpressionSubstituted2compare);
			}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isPathSubstituted()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
			}
			
			if(objEntry.isSubstituted()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
					assertFalse(objEntry.isVariableSubstitutedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isVariableSubstitutedChanged());
					bReturn = true;
				}
			}
		}//end main;
		return bReturn;
	}
	
	
	
	
	
	//#################################################
	public static boolean assertIsSolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			assertTrue(objEntry.isSolveCalled());
			bReturn = true;
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			if(objEntry.isSolveCalled()) {
				if(objSolver.isSolverEnabledThis()) {
					assertTrue(objEntry.isSolved());
					bReturn = true;
				}else {
					assertFalse(objEntry.isSolved());
					bReturn = false;
				}
			}else {
				assertFalse(objEntry.isSolved());
				bReturn = false;
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	
	public static boolean assertIsSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()){ //.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//# Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Beim Testen von Untersolvern müsste normiert werden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht normiert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse, normiert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isSolved() && objEntry.isFormula()){
					//if(objEntry.isSubstitutedChanged()){
					if(assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(objEntry.isSubstitutedChanged()){ 
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}								
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsEncrypted(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
		
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;

			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseEncryption = objSolver.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name());
				if(!(bUseEncryption)) {
					assertFalse(objEntry.isEncrypted()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.			
			if(XmlUtilZZZ.containsTagName(sExpression, KernelEncryptionIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isEncrypted());
				bReturn = true;
			}else {
				assertFalse(objEntry.isEncrypted()); 
				bReturn = false;
			}
						
		}//end main;
		return bReturn;	
	}
	
	public static boolean assertIsDecrypted(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{		
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
										
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			boolean bEncryptionSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelEncryptionIniSolverZZZ.sTAG_NAME);			
			if(!bEncryptionSolverCalledPrevious) {
				//System.out.println(("Vorher wurde Call-Solver nicht aufgerufen. Also Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isDecrypted()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				bReturn = false;
			}else {
				if(!objEntry.isEncrypted()) {
					assertFalse(objEntry.isDecrypted()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
					bReturn = false;					
				}else {
				
					//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
					//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
					boolean bUseExpression = objSolver.isExpressionEnabledGeneral();					
					boolean bUseSolver = objSolver.isSolverEnabledGeneral();					
					boolean bUseSolverThis = objSolver.isSolverEnabledEveryRelevant();
					
					
					//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
					if(bAsEntry) {
						System.out.println(("Aufruf als Entry. Also wurde Encryption-Solver aufgerufen, aber nicht direkt."));
					
						if(bUseExpression && bUseSolver && bUseSolverThis) {											
							assertTrue(objEntry.isDecrypted());
							bReturn = true;
						} else {
							assertFalse(objEntry.isDecrypted());
							bReturn = false;
						}
					}else {
						if(bUseExpression && bUseSolver && bUseSolverThis) {											
							assertTrue(objEntry.isDecrypted());
							bReturn = true;
						}else {
							assertFalse(objEntry.isDecrypted());
							bReturn = false;
						}								
					}
				}
			}		
			
		}//end main;
		return bReturn;
	}
	
	
	//#############################################
	public static boolean assertIsJavaCall(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
		
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;

			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseCall = objSolver.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name());
				boolean bUseJavaCall = objSolver.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA.name());
				if(!(bUseCall || bUseJavaCall)) {
					assertFalse(objEntry.isJavaCall()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.			
			if(XmlUtilZZZ.containsTagName(sExpression, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isJavaCall());
				bReturn = true;
			}else {
				assertFalse(objEntry.isJavaCall()); 
				bReturn = false;
			}
						
		}//end main;
		return bReturn;	
	}
	
	
	public static boolean assertIsJavaCallSolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			//if(!objEntry.isJavaCall()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
			
		 	//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			boolean bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			boolean bJavaCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJavaCallIniSolverZZZ.sTAG_NAME);
			if(!bJavaCallSolverCalledPrevious) {
				//System.out.println(("Vorher wurde Call-Solver nicht aufgerufen. Also Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				bReturn = false;
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also wurde Call-Solver aufgerufen, aber nicht direkt."));
					assertTrue(objEntry.isJavaCallSolveCalled());
					bReturn = true;
					
					assertFalse(objEntry.isCallSolved()); //isCallSolved wird nur gesetzt, wenn nicht für den Call-Solver generell deaktiviert.
				}else {
					//System.out.println(("Vorher wurde Call-Solver aufgerufen (nicht als Entry). Also Call-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
					bReturn = true;
					
					assertFalse(objEntry.isCallSolved());      //isCallSolved wird nur gesetzt, wenn nicht für den Call-Solver generell deaktiviert.
				}
			}			 
			
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsJavaCallSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			//if(!objEntry.isCall()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
						
		 	//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			boolean bJavaCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJavaCallIniSolverZZZ.sTAG_NAME);
			if(!bJavaCallSolverCalledPrevious) {
				//System.out.println(("Vorher wurde Call-Solver nicht aufgerufen. Also Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolved()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				bReturn = false;
				
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also wurde Call-Solver aufgerufen, aber nicht direkt."));
					assertTrue(objEntry.isJavaCallSolved());
					bReturn = true;
										
				}else {
					System.out.println(("Vorher wurde Call-Solver aufgerufen (nicht als Entry). Also Call-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJavaCallSolved());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
					bReturn = true;
										
				}
			}			 
			
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsJavaCallSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			if(!objEntry.isJavaCall()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()){ //.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//# Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Beim Testen von Untersolvern müsste normiert werden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht normiert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse, normiert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isSolved() && objEntry.isJavaCall()){
					//if(objEntry.isSubstitutedChanged()){
					if(assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isJavaCallSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isJavaCallSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(objEntry.isSubstitutedChanged()){ 
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isJavaCallSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isJavaCallSolveCalled());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}								
			}
			
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsJsonSolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		
		main:{
			
			//Das loest das Problem, das der ExpressionHandler ein deaktivierten Solver nicht ausfuehrt.
			//Der Solver selbst wird aber auch ausgefuehrt, wenn er deaktiv ist, aber ein "CHILD"-Solver nicht.
			//Also USJSON ist Deaktiv USEJSPN_MAP waere aktiv.
			boolean btemp = objSolver.isSolverEnabledByFlagZ(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			//boolean btemp = objSolver.isSolverEnabledAnyRelevant(); //das ist fuer den ExpressionHandler keine Loesung, weil ja durchaus ein anderer Solver als JSON aktivert sein kann.
			if(!btemp)break main;
			
			assertTrue(objEntry.isJsonSolveCalled()); //aufgerufen wird der jsonSolve ja.			
			bReturn = true;
			
			
		}//end main:
		return bReturn;					
	}
	
	public static boolean assertIsJsonSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		
		main:{
			
			boolean btemp = assertIsJsonSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); 
			if(!btemp)break main;
			
			assertTrue(objEntry.isJsonSolved()); //aufgerufen wird der jsonSolve ja.
			
			bReturn = true;
		}//end main:
		return bReturn;					
	}
	
	public static boolean assertIsJsonSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!objEntry.isJson()) break main;
			
			boolean btemp = assertIsJsonSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			if(!btemp) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			
			if(objEntry.isSubstitutedChanged()) { //;objEntry.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht veraendert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isJsonSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
						assertFalse(objEntry.isJsonSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonSolvedChanged());
						bReturn = true;
					}
				}else {
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
						assertFalse(objEntry.isJsonSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonSolvedChanged());
						bReturn = true;					
					}
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isJsonSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithExpression.equals(sExpression2compare)) { 
						assertFalse(objEntry.isJsonSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonSolvedChanged());
						bReturn = true;
					}
				}else {
					if(sExpression2compareWithExpression.equals(sExpression2compare)) {
						assertFalse(objEntry.isJsonSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonSolvedChanged());
						bReturn = true;					
					}
				}
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	//#############################################
	public static boolean assertIsJson(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
		
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;

			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseJson = objSolver.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name());
				boolean bUseJsonMap = objSolver.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name());
				boolean bUseJsonArray = objSolver.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name());
				if(!(bUseJson || bUseJsonMap || bUseJsonArray)) {
					assertFalse(objEntry.isJson()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.			
			if(XmlUtilZZZ.containsTagName(sExpression, KernelJsonIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isJson());
				bReturn = true;
			}else {
				assertFalse(objEntry.isJson()); 
				bReturn = false;
			}
						
		}//end main;
		return bReturn;	
	}
	
	//#############################################
	public static boolean assertIsJsonArray(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
		
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;

			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseJson = objSolver.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name());
				boolean bUseJsonMap = objSolver.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name());
				boolean bUseJsonArray = objSolver.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name());
				if(!(bUseJson || bUseJsonMap || bUseJsonArray)) {
					assertFalse(objEntry.isJsonMap()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.			
			if(XmlUtilZZZ.containsTagName(sExpression, KernelJsonArrayIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isJsonArray());
				bReturn = true;
			}else {
				assertFalse(objEntry.isJsonArray()); 
				bReturn = false;
			}
						
		}//end main;
		return bReturn;	
	}
	
	public static boolean assertIsJsonArraySolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonArray()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bJsonSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonIniSolverZZZ.sTAG_NAME);
			boolean bJsonArraySolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonArrayIniSolverZZZ.sTAG_NAME);
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
			
			if(bJsonArraySolverCalledPrevious) {
				assertTrue(objEntry.isJsonArraySolveCalled());		
				bReturn = true;
			}else {
				if(bExpressionHandlerCalledPrevious) {
					
				}
				assertFalse(objEntry.isJsonArraySolveCalled());
				bReturn = false;
			}
		
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsJsonArraySolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonArray()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			if(objEntry.isJsonArraySolveCalled()) {
				assertTrue(objEntry.isJsonArraySolved());
				bReturn = true;				
			}else {
				assertFalse(objEntry.isJsonArraySolved());
				bReturn = false;
			}
												
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsJsonArraySolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonArray()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()) {// objEntry.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonArraySolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht veraendert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonArraySolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isJsonArraySolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
						assertFalse(objEntry.isJsonArraySolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonArraySolvedChanged());
						bReturn = true;
					}
				}else {
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
						assertFalse(objEntry.isJsonArraySolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonArraySolvedChanged());
						bReturn = true;					
					}
				}
				
			}else {	
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonArraySolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonArraySolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isJsonArraySolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
														
					if(sExpression2compareWithExpression.equals(sExpression2compare)) { 
						assertFalse(objEntry.isJsonArraySolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonArraySolvedChanged());
						bReturn = true;
					}
				}else {					
					if(sExpression2compareWithExpression.equals(sExpression2compare)) {
						assertFalse(objEntry.isJsonArraySolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonArraySolvedChanged());
						bReturn = true;					
					}
				}
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	//#############################################
	public static boolean assertIsJsonMap(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
		
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.	
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;

			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseJson = objSolver.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name());
				boolean bUseJsonMap = objSolver.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name());
				boolean bUseJsonArray = objSolver.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY.name());
				if(!(bUseJson || bUseJsonMap || bUseJsonArray)) {
					assertFalse(objEntry.isJsonMap()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.			
			if(XmlUtilZZZ.containsTagName(sExpression, KernelJsonMapIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isJsonMap());
				bReturn = true;
			}else {
				assertFalse(objEntry.isJsonMap()); 
				bReturn = false;
			}
						
		}//end main;
		return bReturn;	
	}
	
	public static boolean assertIsJsonMapSolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonMap()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bJsonSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonIniSolverZZZ.sTAG_NAME);
			boolean bJsonMapSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonMapIniSolverZZZ.sTAG_NAME);
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
			
			if(bJsonMapSolverCalledPrevious) {
				assertTrue(objEntry.isJsonMapSolveCalled());
				bReturn = true;
				
			}else {
				if(bExpressionHandlerCalledPrevious) {
					
				}
				assertFalse(objEntry.isJsonMapSolveCalled());
				bReturn = false;
			}
			
		}//end main;
		return bReturn;
	}
	
	public static boolean assertIsJsonMapSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonMap()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
							
			if(objEntry.isJsonMapSolveCalled()) {
				assertTrue(objEntry.isJsonMapSolved());
				bReturn = true;				
			}else {
				assertFalse(objEntry.isJsonMapSolved());
				bReturn = false;
			}
												
		}//end main;
		return bReturn;
	}
	
	
	
	public static boolean assertIsJsonMapSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			if(!objEntry.isJsonMap()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()) { //objEntry.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonMapSolved()) {
					//# Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Beim Testen von Untersolvern müsste normiert werden.
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, normiert) =" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonMapSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse, normiert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isJsonMapSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
						assertFalse(objEntry.isJsonMapSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonMapSolvedChanged());
						bReturn = true;
					}
				}else {
					if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
						assertFalse(objEntry.isJsonMapSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonMapSolvedChanged());
						bReturn = true;					
					}
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isJsonMapSolved()) {
					//#Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Beim Testen von Untersolvern müsste normiert werden.
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, normiert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, normiert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isJsonMapSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, normiert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse, normiert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isJsonMapSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(sExpression2compareWithExpression.equals(sExpression2compare)) { 
						assertFalse(objEntry.isJsonMapSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonMapSolvedChanged());
						bReturn = true;
					}
				}else {
					if(sExpression2compareWithExpression.equals(sExpression2compare)) {
						assertFalse(objEntry.isJsonMapSolvedChanged());
						bReturn = false;
					}else {
						assertTrue(objEntry.isJsonMapSolvedChanged());
						bReturn = true;					
					}
				}
			}
			
			
		}//end main;
		return bReturn;
	}
	
	//###########################################
	public static boolean assertIsCall(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			
			//Löse das von allen Prozessschritten/Aufrufen. Die Aufrufe werden vermieden wenn ein entsprechendes Flag fehlt.
			//Also: ein is..."TAG" muss vorliegen, wenn logisch gilt: Parser an und der TAG ist im String.				
			boolean bUseExpression = objSolver.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			boolean bUseParser = objSolver.isParserEnabledGeneral();
			if(!bUseParser) break main;

			boolean bUseParserThis = objSolver.isParserEnabledCustom();
			if(!bUseParserThis) break main;
			
			//Wenn asEntry geparst wurde, dann zieht die Programmierung des ExpressionHandlers und wie sich die Flags dort auswirken.
			if(bAsEntry) {
				boolean bUseCall = objSolver.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL.name());
				boolean bUseJavaCall = objSolver.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA.name());
				if(!(bUseCall || bUseJavaCall)) {
					assertFalse(objEntry.isCall()); 
					bReturn = false;
					break main;
				}
			}
				
			//Ansonsten richtet es sich danach, ob der Tag darin steht.
			if(XmlUtilZZZ.containsTagName(sExpression, KernelCallIniSolverZZZ.sTAG_NAME, false)) {
				assertTrue(objEntry.isCall());
				bReturn = true;
			}else {
				assertFalse(objEntry.isCall()); 
				bReturn = false;
			}
				
		}//end main;
		return bReturn;		
	}
	
	public static boolean assertIsCallSolveCalled(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			//if(!objEntry.isCall()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
						
		 	//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			boolean bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(!bCallSolverCalledPrevious) {
				//System.out.println(("Vorher wurde Call-Solver nicht aufgerufen. Also Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				bReturn = false;
		
			}else {
				//Egal ob als Entry-Aufruf oder nicht: Wenn der Solve Aufruf in der History protokolliert ist, ist der Wert entsprechend gesetzt.
				if(bAsEntry) {
					assertTrue(objEntry.isCallSolveCalled());
					bReturn = true;					
				}else {
					assertTrue(objEntry.isCallSolveCalled());
					bReturn = true;
				}
			}			 						
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsCallSolved(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			//if(!objEntry.isCall()) break main;
			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			
			boolean bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
						
		 	//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			boolean bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(!bCallSolverCalledPrevious) {
				//System.out.println(("Vorher wurde Call-Solver nicht aufgerufen. Also Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isCallSolved()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				bReturn = false;
				
			}else {
				if(objSolver.isSolverEnabledByFlagZ(IKernelCallIniSolverZZZ.FLAGZ.USECALL)) {
					assertTrue(objEntry.isCallSolved());
					bReturn = true;					
				}else {
					assertFalse(objEntry.isCallSolved());
					bReturn = false;
				}
			}			 
			
			
		}//end main;
		return bReturn;
	}
	
	
	public static boolean assertIsCallSolvedChanged(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		/*
		boolean bReturn = false;
		main:{			
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isCallSolved()) {
				//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
				//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht veraendert)=" + sExpressionSubstituted2compare);
			}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
				sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
				sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, veraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse veraendert)=" + sExpressionSubstituted2compare);									
			}else {
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
				System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
			}
			
			if(objEntry.isCallSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
					assertFalse(objEntry.isCallSolvedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isCallSolvedChanged());
					bReturn = true;
				}
			}else {
				if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) {
					assertFalse(objEntry.isCallSolvedChanged());
					bReturn = false;
				}else {
					assertTrue(objEntry.isCallSolvedChanged());
					bReturn = true;					
				}
			}
		}//end main;
		return bReturn;
		*/
		
		boolean bReturn = false;
		main:{			
			String sExpression = sExpressionIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
						
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sExpression2compareWithSubstituted=sExpressionSolved;
			String sExpression2compareWithExpression =sExpressionSolved;
			String sExpressionSubstituted2compare = sExpressionSubstituted;
			String sExpression2compare = sExpression;
			if(objEntry.isSubstitutedChanged()){ //.isParsedChanged()) {
				//ziehe substituted heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//# Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//Beim Testen von Untersolvern müsste normiert werden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onSolve, Besonderheit nicht normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onSolve, Besonderheit nicht normiert)=" + sExpressionSubstituted2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (onParse, normiert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (onParse, normiert)=" + sExpressionSubstituted2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithSubstituted (unveraendert)=" + sExpression2compareWithSubstituted);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpressionSubstituted2compare (unveraendert)=" + sExpressionSubstituted2compare);
				}
				
				
				if(objEntry.isSolved() && objEntry.isJavaCall()){
					//if(objEntry.isSubstitutedChanged()){
					if(assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry)) {
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isCallSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isCallSolvedChanged());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}
				
			}else {
				//ziehe expression heran
				if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.SOLVE_REMOVE && objEntry.isSolved()) {
					//Weil das Entfernen des Z-Tags durch das Solven als "Wertaenderung" gilt. Darf hier nicht diese Normierung stattfinden.
					//sExpression2compareWithSubstituted = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithSubstituted, sTagStartZ, sTagEndZ, false);
					//sExpressionSubstituted2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSubstituted2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onSolve, Besonderheit nicht veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onSolve, Besonderheit nicht veraendert)=" + sExpression2compare);
				}else if(objEnumSurrounding == EnumSetMappedTestSurroundingTypeZZZ.PARSE_REMOVE && objEntry.isSolved()) {
					sExpression2compareWithExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compareWithExpression, sTagStartZ, sTagEndZ, false);
					sExpression2compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression2compare, sTagStartZ, sTagEndZ, false);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (onParse, veraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (onParse veraendert)=" + sExpression2compare);									
				}else {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compareWithExpression (unveraendert)=" + sExpression2compareWithExpression);
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sExpression2compare (unveraendert)=" + sExpression2compare);
				}
				
				if(objEntry.isSolved()){ //Kann hier eigentlich nicht getestet werden. Ggfs. wird eine Expression ohne INI-PATH uebergeben
					if(objEntry.isSubstitutedChanged()){ 
						if(sExpression2compareWithSubstituted.equals(sExpressionSubstituted2compare)) { 
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isCallSolvedChanged());
							bReturn = true;
						}
					}else {
						if(sExpression2compareWithExpression.equals(sExpression2compare)) {
							//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
							//assertFalse(objEntry.isSolvedChanged());
							//bReturn = false;
						}else {
							assertTrue(objEntry.isCallSolveCalled());
							bReturn = true;					
						}
					}
				}else {
					//Das kann man nicht sagen, der solvedChanged-Status kann woanders her kommen
					//assertFalse(objEntry.isSolvedChanged());
					//bReturn = false;
				}								
			}
			
			
		}//end main;
		return bReturn;
	}
	
	
	
	//############################################################################
	public static boolean assertFileIniEntry(ISolveEnabledZZZ objSolver, IEnumSetMappedTestFlagsetZZZ objEnumFlagset, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestCaseZZZ objEnumTestCase, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean bIsExpressionValidForTest=false;
			boolean bAsEntry=false;
												
			String sCaseSet = objEnumTestCase.getAbbreviation();
			switch(sCaseSet) {
			case sCASE_PARSE_AS_ENTRY:
			case sCASE_SOLVE_AS_ENTRY:
			case sCASE_AS_ENTRY:
				bAsEntry=true;
				break;
			default:
				bAsEntry=false;
				break;
			}
			
			
			switch(sCaseSet) {
			case sCASE_PARSE_AS_ENTRY:
			case sCASE_PARSE:{
				bIsExpressionValidForTest = TestUtilAsTestZZZ.isExpressionValidForFlagset_parse(objEnumFlagset, sExpressionIn);
				if(bIsExpressionValidForTest) {
					assertFileIniEntry_parse(objSolver, objEnumSurrounding, objEnumFlagset, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);
				}
				break;
			}	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//https://stackoverflow.com/questions/18412936/or-operator-in-switch-case
			//also man kann in diesem Ausdruck nicht "verodern" ||, da dies keine feststehende Expression ist, sondern zur Laufzeit gebaut wuerde
			//case sCASE_SOLVE || sCASE_AS_ENTRY:{
			//praktikable Loesung: Schreibe die Cases untereinander und lasse das break weg!
			case sCASE_SOLVE_AS_ENTRY:
			case sCASE_AS_ENTRY:
			case sCASE_SOLVE:{
				bIsExpressionValidForTest = TestUtilAsTestZZZ.isExpressionValidForFlagset_solve(objEnumFlagset, sExpressionIn);
				if(bIsExpressionValidForTest) {
					assertFileIniEntry_solve(objSolver, objEnumSurrounding, objEnumFlagset,objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);
				}
				break;
			}
			default:
				fail("Test CaseSet '" + sCaseSet + "' ist nicht definiert");
				break;
			}
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	public static boolean assertFileIniEntry_parse(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			
			String sCaseSet = TestUtilAsTestZZZ.sCASE_PARSE;
			String sFlagSet = objEnumTestFlagset.getAbbreviation();

				switch(sFlagSet) {
				//Merke: Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
				case sFLAGSET_UNEXPRESSED:
					bReturn = assertFileIniEntry_parse_FLAGSET_UNEXPRESSED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_PATH_UNSUBSTITUTED:
					bReturn = assertFileIniEntry_parse_FLAGSET_PATH_UNSUBSTITUTED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
										
				case sFLAGSET_PATH_SUBSTITUTED:
					bReturn = assertFileIniEntry_parse_FLAGSET_PATH_SUBSTITUTED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_ENCRYPTION_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_ENCRYPTION_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_ENCRYPTION_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_ENCRYPTION_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_UNSOLVED:		
					bReturn = assertFileIniEntry_parse_FLAGSET_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
							
				case sFLAGSET_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);									
					break;
				
				case sFLAGSET_FORMULA_UNSOLVED:					
					bReturn = assertFileIniEntry_parse_FLAGSET_FORMULA_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);									
					break;
			
				case sFLAGSET_MATH_UNSOLVED: //Z:Formula wird ja gesolved					
					bReturn = assertFileIniEntry_parse_FLAGSET_FORMULA_MATH_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);									
					break;
				case sFLAGSET_MATH_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_FORMULA_MATH_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break; 
				case sFLAGSET_CALL_UNSOLVED:			
					bReturn = assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
																			
				case sFLAGSET_JAVACALL_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JAVACALL_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_JAVACALL_UNSOLVED:				
					bReturn = assertFileIniEntry_parse_FLAGSET_JAVACALL_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
					
				case sFLAGSET_JSON_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSON_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
				case sFLAGSET_JSONARRAY_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONARRAY_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);					
					break;
					
				case sFLAGSET_JSONARRAY_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONARRAY_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
					break;
				//Merke: Folgende Test sind zwar denkbar aber unnoetig
					// case sFLAGSET_JSONARRAY_UNEXPRESSED:
					//Merke: Diese Flagset Konstellation gibt es nicht. 
					//       Sinnvoll ist ein Test wenn die Expressionbehandlung ganz ausgeschaltet ist.					
					//       Auch JSONARRAY sollte getestet werden wenn die Expressionbehandlung ganz ausgeschaltet ist.
					//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNEXPRESSED);
					//break;
					//case sFLAGSET_JSONARRAY_JSONARRAY_UNEXPRESSED:
					//Merke: Diese Konstellation gibt es fuer einen "Untersolver" nicht. Es kann nur die Expressionbehandlung ganz ausgeschaltet werden.
					//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNEXPRESSED);
					//break;	
					
				case sFLAGSET_JSONMAP_UNSOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONMAP_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);				
					break;
				case sFLAGSET_JSONMAP_SOLVED:
					bReturn = assertFileIniEntry_parse_FLAGSET_JSONMAP_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);
					break;
				default:
					fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
					break;
				}					
										
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_parse_FLAGSET_UNEXPRESSED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertFalse(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!					
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde ja NICHT gemacht.
				
			assertFalse(objEntry.isParsed()); //sollte ohne Expression abgebrochen worden sein
			assertFalse(objEntry.isSolved()); //sollte ohne Expression abgebrochen worden sein
				
			assertFalse(objEntry.isParsedChanged());
			assertFalse(objEntry.isSolvedChanged()); //Ohne Expression Behandlung wird auch nichts geaendert.
			assertFalse(objEntry.isSubstitutedChanged());
			assertFalse(objEntry.isPathSubstituted());					
			assertFalse(objEntry.isVariableSubstituted());
					
				
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
					
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_PATH_UNSUBSTITUTED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			sExpressionSolved = sExpressionSolvedIn;
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			assertFalse(objEntry.isPathSubstitutedChanged());									
			assertFalse(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
				//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertFalse(objEntry.isSolveCalled()); //Der Solve-Schritt wurde nicht gemacht.					
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert									 
			assertFalse(objEntry.isSolved()); //Der Solve-Schritt wurde nicht gemacht.
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_parse_FLAGSET_PATH_SUBSTITUTED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null; String sExpressionSubstituted2compareWithExpression = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			sExpressionSolved = sExpressionSolvedIn;
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//##############################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_parse_FLAGSET_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################						
			assertIsParseCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			
			//++++++++++++++++++++++++++++++++			
			assertIsPathSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());					
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//##############################################
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++						
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Kann hier eigentlich nicht abgefragt werden, weil ggfs. doch ein CALL in der Expression ist.
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null; String sExpressionSubstituted2compareWithExpression = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); 	//ohne Expression-Nutzung kein Expression Eintrag!!!

			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//##############################################


	
										
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
		
	private static boolean assertFileIniEntry_parse_FLAGSET_FORMULA_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); 						
			
			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			//assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//#############################################
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	
	private static boolean assertFileIniEntry_parse_FLAGSET_FORMULA_MATH_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//+++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
			//++++++++++++++++++++++++++++		
			assertTrue(objEntry.isPathSubstituteCalled());
			assertTrue(objEntry.isPathSubstituted());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituteCalled());					
			assertTrue(objEntry.isVariableSubstituted());
				//+++ Auf wertaenderung kann man hier doch auch eigentlich nicht so abfragen
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isSubstituteCalled());
			assertTrue(objEntry.isSubstituted());
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
						
			//++++++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //nur mit parse wird hier nix geaendert 
			assertFalse(objEntry.isSolved());
			assertFalse(objEntry.isSolvedChanged());

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++																				
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_FORMULA_MATH_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!								
			
			//++++++++++++++++++++
			assertTrue(objEntry.isParsed()); 
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);			
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted());
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
				//+++ Werte kann man hier doch auch eigentlich nicht so abfragen					
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isSubstituteCalled());
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted());
			
			//++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //hier nur der PARSE Fall
			assertFalse(objEntry.isSolvedChanged());
			assertFalse(objEntry.isSolved());
			
													
			//++++++++++++++++++++++						
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
		
	private static boolean assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled()); 					
			assertFalse(objEntry.isSolveCalled()); //hier nur .parse() - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertTrue(objEntry.isParsed());  //auch ohne solver wird geparsed
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			//kann ja theoretisch auch ein anderer Call sein... assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled()); 					
			assertFalse(objEntry.isSolveCalled()); //hier nur .parse() - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertTrue(objEntry.isParsed());  //Merke: Auch wenn Call disabled ist, so ist JAVACALL aktiv. also wird geparsed:
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt (auch wenn call disabled ist).
			assertTrue(objEntry.isJavaCall());
			
			//kann ja theoretisch auch ein anderer Call sein... assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JAVACALL_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled());
			
			assertFalse(objEntry.isSolveCalled()); //nur parse - Fall
			assertFalse(objEntry.isCallSolveCalled()); //nur PARSE - Fall
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); 				
			
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isJavaCall());	//Beim Parsen wird das festgestellt
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
					
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JAVACALL_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isParseCalled());
			assertFalse(objEntry.isSolveCalled()); 
			assertFalse(objEntry.isCallSolveCalled()); 
			assertFalse(objEntry.isJavaCallSolveCalled());
			assertTrue(objEntry.isExpression());
			
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); 				
			
			/*Zum Suchen der Gegenstelle TODOGOON20250308;
			 * 				//Es gibt keinen generischen Ansatz .isCall(true) zu erzeugen.
			                 //Das koennte man in KernelJavaCallIniSolverZZZ.parseFirstVector(...) zwar speziell tun.
			                 //Aber eigentlich will ich das irgendwie generisch machen...
			                 //so etwas mit "ElternTags"
			//TODOGOON20250308; //TICKETGOON20250308; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			*/
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall());	//JavaCall ist "unused" 
			assertNull(objEntry.getCallingClassname());
			assertNull(objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	private static boolean assertFileIniEntry_parse_FLAGSET_ENCRYPTION_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false; boolean bJsonArraySolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################					
			assertIsParseCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//assertTrue(objEntry.isParsed());
			assertIsParsed(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			assertFalse(objEntry.isSolveCalled());
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);			

			assertTrue(objEntry.isExpression());
			
			
			//++++++++++++++++++++++
			//assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//assertTrue(objEntry.isPathSubstituted());
			assertIsPathSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
			//++++++++++++++++++++++			
			//assertTrue(objEntry.isVariableSubstituteCalled());
			assertIsVariableSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				//+++ Werte kann man hier doch auch eigentlich nicht so abfragen					
			//assertTrue(objEntry.isVariableSubstituted());
			assertIsVariableSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++
			
			//++++++++++++++++++++++
			//assertTrue(objEntry.isSubstituteCalled());
			assertIsSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//assertTrue(objEntry.isSubstituted());
			assertIsSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen	
			assertIsJson(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonMap(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArray(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
										
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			//TODOGOON20250915; //HIER DIE ASSERTS AUS DER AUFRUFENDEN METHODE ÜBERNEHMEN
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind			
			boolean bDecrypted = assertIsDecrypted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			if(bDecrypted) {
				assertNotNull(objEntry.getValueDecrypted());
			}else {
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_parse_FLAGSET_ENCRYPTION_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false; boolean bJsonArraySolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################					
			assertIsParseCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertTrue(objEntry.isParsed());
			
			assertFalse(objEntry.isSolveCalled());
			assertFalse(objEntry.isSolved()); //Der konkrete Solver ist nicht involviert
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);			

			assertTrue(objEntry.isExpression());
			
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted());
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
				//+++ Werte kann man hier doch auch eigentlich nicht so abfragen					
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isSubstituteCalled());
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted());
			//+++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen	
			assertIsJson(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonMap(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArray(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
										
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			//TODOGOON20250915; //HIER DIE ASSERTS AUS DER AUFRUFENDEN METHODE ÜBERNEHMEN
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind			
			boolean bDecrypted = assertIsDecrypted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			if(bDecrypted) {
				assertNotNull(objEntry.getValueDecrypted());
			}else {
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			}
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSON_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			
			boolean bJsonSolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
			
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			//++++++++++++++++++++++++						

			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//##############################################



			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //bei diesem Flagset wird json zwar nicht aufgeloest, aber geparsed trotzdem
			//Zwar gilt: Bei diesem Flagset wird json zwar nicht aufgeloest, aber geparsed trotzdem
			//ABER:      Auf einzelne Werte kann nicht geprueft werden, da das Flagset sowohl für Map als auch Array verwendet wird
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			//... es gibt noch keinen Status zu ...ParseCalled()...
			/*
			bJsonSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonIniSolverZZZ.sTAG_NAME);
			bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
			if(bExpressionHandlerCalledPrevious) {
				System.out.println(("Vorher wurde Expression-Handler aufgerufen. Also JsonMap/JsonArray-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJsonMapParseCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
				assertFalse(objEntry.isJsonArrayParseCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
			}else if(bJsonSolverCalledPrevious) {
				System.out.println(("Vorher wurde Json-Solver aufgerufen. Also JsonMap/JsonArray-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJsonMapParseCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
				assertFalse(objEntry.isJsonArrayParseCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also kann JsonMap/JsonArray-Solver nicht direkt aufgerufen worden sein."));
					assertFalse(objEntry.isJsonMapParseCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
					assertFalse(objEntry.isJsonArrayParseCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				}else {
					System.out.println(("Vorher wurde Json-Solver NOCH NICHT aufgerufen. Also JsonMap/JsonArray-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJsonMapParseCalled());  //aber wenn der JavaMapSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
					assertTrue(objEntry.isJsonArrayParseCalled());  //aber wenn der JavaArraySolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....					
				}
			}*/		
			assertFalse(objEntry.isJsonSolved());
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Merke: Auch wenn die Solver wg. des USE... Flags nicht laufen werden, so wird das Parsen immer gemacht
			//       !!! Aber nur bis zur naechsten "Parent-Tag-Grenze". D.h. isJson wird gesetzt werden auch ohne aktives Json. isJsonMap allerdings nicht!!!!
			//assertTrue(objEntry.isJsonMap());             			
			
			assertFalse(objEntry.isJsonMapSolveCalled()); //OHNE JSON-Solver Aufruf, keinen JsonMapSolver aufruf.
			assertFalse(objEntry.isJsonMapSolved());      
				
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Merke: Auch wenn die Solver wg. des USE... Flags nicht laufen werden, so wird das Parsen immer gemacht
			//       !!! Aber nur bis zur naechsten "Parent-Tag-Grenze". D.h. isJson wird gesetzt werden auch ohne aktives Json. isJsonMap allerdings nicht!!!!
			//assertTrue(objEntry.isJsonArray());             			
			
			assertFalse(objEntry.isJsonArraySolveCalled()); //OHNE JSON-Solver Aufruf, keinen JsonArraySolver aufruf.
			assertFalse(objEntry.isJsonArraySolved());      
		
			//Methode wird ja sowohl unter Map als auch unter Array Input genutzt
			boolean bAnyJsonChild = objEntry.isJsonMap() || objEntry.isJsonArray();
			assertTrue(bAnyJsonChild);   //Wird beim Parsen festgestellt
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
								
			//fail("Testutil for flagset missing: " + sFLAGSET_JSON_UNSOLVED);		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONARRAY_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden

			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertTrue(objEntry.isParsed()); 
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++						

			assertTrue(objEntry.isSubstituteCalled()); 			
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++
			//##############################################

			assertTrue(objEntry.isVariableSubstituteCalled()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//auf WErte kann hier nicht abgefragt werden, ggf. ja keine Variablen im String drin.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Werte kommen aus dem Parsen.  
			assertFalse(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNSOLVED);		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONARRAY_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Es ist geparsed worden....
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isParsedChanged()					
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());															
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isSubstitutedChanged()					
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Wir sind im PARSE Test, da wurde noch nix gesolved					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Kommt aus parse
			assertFalse(objEntry.isJsonMap());
			assertTrue(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_SOLVED);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONMAP_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			

			assertFalse(objEntry.isSolveCalled()); //hier ist der Parse Fall	
			assertFalse(objEntry.isSolvedChanged()); //nur mit parse wird hier nix geaendert						
			assertFalse(objEntry.isSolved()); //hier ist der Parse Fall
			
			//###########################			
			assertTrue(objEntry.isParseCalled());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed()); 
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						


			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++						

			assertTrue(objEntry.isSubstituteCalled()); 
			assertIsSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//##############################################


			assertTrue(objEntry.isVariableSubstituteCalled()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//auf Werte kann hier nicht abgefragt werden, ggf. ja keine Variablen im String drin.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //JSON_MAP unsolved, aber JSON solved
			assertFalse(objEntry.isJsonMap()); //Wert kommt aus parse.. zwar ist parserEnabledCustom=true, aber parserEnabledThis=false
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_UNSOLVED);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_parse_FLAGSET_JSONMAP_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sExpression2compareWithSolved = null; String sExpression2compareWithSubstituted = null;
			String sExpressionSubstituted2compare = null;
			
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Es ist geparsed worden....
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isParsedChanged()					
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());															
			//Kann nicht getestet wurde, weil SOLVE den Tag ggfs. verfaelscht: objEntry.isSubstitutedChanged()					
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertFalse(objEntry.isSolveCalled()); //Wir sind im PARSE Test, da wurde noch nix gesolved					
			assertFalse(objEntry.isSolvedChanged()); 
			assertFalse(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			
			assertTrue(objEntry.isJson()); //Kommt aus parse
			assertTrue(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_SOLVED);
		
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
		
	/**
	 * @param objEnumSurrounding
	 * @param objEnumTestFlagset
	 * @param objEntry
	 * @param sExpressionIn
	 * @param sExpressionSubstitutedIn
	 * @param sExpressionSolvedIn
	 * @param bAsEntry
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.05.2025, 06:58:22
	 */
	public static boolean assertFileIniEntry_solve(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IEnumSetMappedTestFlagsetZZZ objEnumTestFlagset,IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sCaseSet = TestUtilAsTestZZZ.sCASE_SOLVE;
			String sFlagSet = objEnumTestFlagset.getAbbreviation();
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
			
			
			boolean bCallSolverCalledPrevious=false;
			
			switch(sFlagSet) {
			//Das ist keine Konstante, case EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED.getAbbreviation():
			case sFLAGSET_UNEXPRESSED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNEXPRESSED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;	
			case sFLAGSET_UNPARSED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNPARSED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);								
				break;		
			case sFLAGSET_UNSOLVED:					
				bReturn = assertFileIniEntry_solve_FLAGSET_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);								
				break;
			case sFLAGSET_UNPARSED_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_UNPARSED_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;										
				
			case sFLAGSET_SOLVED:				
				bReturn = assertFileIniEntry_solve_FLAGSET_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);				
				break;
			
			case sFLAGSET_PATH_UNSUBSTITUTED:				
				bReturn = assertFileIniEntry_solve_FLAGSET_PATH_UNSUBSTITUTED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;

			case sFLAGSET_PATH_SUBSTITUTED:
				bReturn = assertFileIniEntry_solve_FLAGSET_PATH_SUBSTITUTED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			
			case sFLAGSET_ENCRYPTION_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_ENCRYPTION_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_ENCRYPTION_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_ENCRYPTION_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_FORMULA_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_FORMULA_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			case sFLAGSET_MATH_UNSOLVED:	//Es wird z:Formula gesolved!!!
				bReturn = assertFileIniEntry_solve_FLAGSET_FORMULA_MATH_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);
				break;
						
			case sFLAGSET_MATH_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_MATH_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, sTagIn, sTagSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_CALL_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			
			case sFLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED:	
				bReturn = assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
								
			case sFLAGSET_JAVACALL_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JAVACALL_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;

			case sFLAGSET_JAVACALL_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JAVACALL_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_JSON_SOLVED:
				fail("Testutil for flagset missing: " + sFLAGSET_JSON_SOLVED);
				break;
				
			case sFLAGSET_JSON_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSON_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;			
				
			case sFLAGSET_JSONARRAY_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONARRAY_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
								
			case sFLAGSET_JSONARRAY_UNSOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONARRAY_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;

			case sFLAGSET_JSONMAP_SOLVED:
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONMAP_SOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
				
			case sFLAGSET_JSONMAP_UNSOLVED:					
				bReturn = assertFileIniEntry_solve_FLAGSET_JSONMAP_UNSOLVED_(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
				break;
			default:
				fail("Test Flagset '" + sFlagSet + "' im Case '"+sCaseSet+"' ist nicht definiert");
				break;
			}
									
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNEXPRESSED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isSolveCalled()); //auch ohne Expression Auswertung wurde hier solve() aufgerufen.
			assertFalse(objEntry.isParseCalled()); //... danach wurde abgebrochen, also auch kein parse ausgefuehrt.
			assertFalse(objEntry.isExpression());
												
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertFalse(objEntry.isParsed()); //sollte ohne EXPRESSION abgebrochen worden sein				
			assertFalse(objEntry.isParsedChanged());
			
			assertFalse(objEntry.isPathSubstituted());				
			assertFalse(objEntry.isVariableSubstituted());
			
			//Keine Solver Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertFalse(objEntry.isSolved()); //sollte ohne EXPRESSION abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());						
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_UNPARSED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
			assertFalse(objEntry.isExpression()); //ohne Expression-Nutzung, bzw. hier ohne Parser-Nutzung auch kein Expression Eintrag!!!									
			
			//+++++++++++++++++++++++++++++++++++++++
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertFalse(objEntry.isParsed());
			
			//++++++++++++++++++++++++++++++++++++++++++++				
			assertFalse(objEntry.isPathSubstituteCalled());
			assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			
			//++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertFalse(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled());
			assertTrue(objEntry.isSolved()); //hier wird nicht geparsed, aber gesolved. Darum wird SOLVE nicht abgebrochen.
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Parser Ergebnisse
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....									
			assertTrue(objEntry.isParsed()); //Auch ohne Solver wird geparsed
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());		
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
			//Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled());  //aufgerufen wurde der Solver ja...
			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_UNPARSED_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			//wie unsolved, aber hier gibt es auch keine Parser - Ergebnisse
			assertFalse(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
								
			//+++++++++++++++++++++++++++++++++++++++
			//Keine Parser Ergebnisse (Abfrage der asserts in umgekehrter Reihenfolge ihres moeglichen Wertesetzens im Code)
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertFalse(objEntry.isParsedChanged());
			assertFalse(objEntry.isParsed());     //Nun wird weder geparsed noch gesolved.
			
			//+++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isPathSubstituteCalled());				
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertFalse(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isVariableSubstituteCalled());
				//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			assertFalse(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++
			//Keine Solver Ergebnisse
			assertTrue(objEntry.isSolveCalled()); //aufgerufen wurde der Solver ja
			assertFalse(objEntry.isSolved()); //sollte ohne SOLVE abgebrochen worden sein
			assertFalse(objEntry.isSolvedChanged());//schliesslich wird hier nix gesolved()!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind					
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Kann hier eigentlich nicht abgefragt werden, weil ggfs. eine Expression ohne CALL-Ausdruck vorliegt
			//assertFalse(objEntry.isCall());
			//assertFalse(objEntry.isJavaCall());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			//assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//Wert mit speziellen Entry-Formelwert vergleichen
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
						
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_MATH_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sTag = sTagIn;
			String sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConvertedValue=null; String sFormulaSolvedAndConvertedLine=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....		
			assertTrue(objEntry.isParsed()); 
				//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
			//+++++++++++++++++++++++++++++++				
			
			//+++++++++++++++++++++++++++++++									
			assertTrue(objEntry.isPathSubstituteCalled());															
			assertTrue(objEntry.isPathSubstituted());	
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled());			
			assertTrue(objEntry.isSolved()); //sollte auch SOLVE_MATH wird solve ausgefuehrt
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++				
			//++++++++++++++++++++++									
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			if(objEntry.isFormula()) {
				sFormulaSolvedAndConvertedValue = objEntry.getValueFormulaSolvedAndConverted();
				assertEquals(sTagSolved, sFormulaSolvedAndConvertedValue);
			}else {
				//Wenn keine Formel im Wert vorhanden ist, dann ist hier auch nix gefuellt.
				sFormulaSolvedAndConvertedValue = objEntry.getValueFormulaSolvedAndConverted();
				assertNull("NULL erwartet, aber es ist: '" + sFormulaSolvedAndConvertedValue + "'", sFormulaSolvedAndConvertedValue);
			}
			
			//DER GESAMTAUSDRUCK IST IMMER DRIN
			sFormulaSolvedAndConvertedLine = objEntry.getLineFormulaSolvedAndConverted();					
			assertEquals(sExpressionSolved, sFormulaSolvedAndConvertedLine);

			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_FORMULA_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null; String sExpressionSubstituted2compare = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....			
			assertTrue(objEntry.isParsed()); 
			//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
			//+++++++++++++++++++++++++++++++				
			
			//+++++++++++++++++++++++++++++++									
			assertTrue(objEntry.isPathSubstituteCalled());																			
			assertTrue(objEntry.isPathSubstituted());
			//da ggfs. durch das Solven der Wert veraendert wurde kann das hier nicht abgefragt werden:
			//assertIsPathSubstitutedChanged(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled());			
			assertTrue(objEntry.isSolved()); //auch ohne SOLVE_MATH wird SOLVE ausgefuehrt
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++				
			//++++++++++++++++++++++									
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_ENCRYPTION_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false; boolean bJsonArraySolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertIsParseCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			//assertTrue(objEntry.isParsed());
			assertIsParsed(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			
			
			//+++++++++++++++++++++++
			//assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			//assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++

			assertIsVariableSubstituteCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			//assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			assertIsVariableSubstituted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen	
			//assertTrue(objEntry.isJson());
			assertIsJson(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);			
			//assertFalse(objEntry.isJsonMap());
			assertIsJsonMap(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//assertTrue(objEntry.isJsonArray());
			assertIsJsonArray(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
										
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			//TODOGOON20250915; //HIER DIE ASSERTS AUS DER AUFRUFENDEN METHODE ÜBERNEHMEN
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind			
			boolean bDecrypted = assertIsDecrypted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			if(bDecrypted) {
				assertNotNull(objEntry.getValueDecrypted());
			}else {
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_ENCRYPTION_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false; boolean bJsonArraySolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertIsParseCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertIsParsed(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertIsSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertIsSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen
			assertIsJson(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonMap(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArray(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			
			
			//TODOGOON20250915; //HIER DIE ASSERTS AUS DER AUFRUFENDEN METHODE ÜBERNEHMEN
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			boolean bDecrypted = assertIsDecrypted(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			if(bDecrypted) {
				assertNotNull(objEntry.getValueDecrypted());
			}else {
				assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_FORMULA_MATH_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sTagIn, String sTagSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			String sTag = sTagIn;
			String sTagSolved = sTagSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null; String sFormulaSolvedAndConvertedValue=null;String sFormulaSolvedAndConvertedLine=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....			
			assertTrue(objEntry.isParsed()); 
			//Merke: .isParsedChange() laesst sich hier nicht ermitteln.
			//+++++++++++++++++++++++++++++++				
			
			//+++++++++++++++++++++++++++++++									
			assertTrue(objEntry.isPathSubstituteCalled());																
			assertTrue(objEntry.isPathSubstituted());	
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled());
			assertTrue(objEntry.isSolved()); //sollte auch SOLVE_MATH wird solve ausgefuehrt
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);		
			//++++++++++++++++++++++				
			//++++++++++++++++++++++									
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isFormulaSolveCalled());
			assertIsFormulaSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsFormulaSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
//			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
//			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
//					
//			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
//			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			sFormulaSolvedAndConvertedValue = objEntry.getValueFormulaSolvedAndConverted();
			assertEquals(sTagSolved, sFormulaSolvedAndConvertedValue);
					
			sFormulaSolvedAndConvertedLine = objEntry.getLineFormulaSolvedAndConverted();					
			assertEquals(sExpressionSolved, sFormulaSolvedAndConvertedLine);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			assertFalse(objEntry.isCall());
			assertFalse(objEntry.isJavaCall());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());	

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sLineFormulaSolvedAndConverted=null; String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertTrue(objEntry.isParsed());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																										
			assertTrue(objEntry.isPathSubstituted());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++Variablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());													
			assertTrue(objEntry.isVariableSubstituted());
					//+++ Werte kann man hier doch auch eigentlich nicht so abfragen
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...												
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isFormulaSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertIsFormulaSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsFormulaSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			sLineFormulaSolvedAndConverted = objEntry.getLineFormulaSolvedAndConverted();			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();
		
			
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			
			
			if(objEnumSurrounding.isSurroundingValueToKeep_OnSolve()) {
				//Nein, einfach einen Z-Tag darumzumachen ist nicht richtig, wenn z.B. PRE und POST Strings vorhanden sind
				//String sLineFormulaSolvedAndConvertedAsExpression = KernelExpressionIniHandlerZZZ.makeAsExpressionDefault(sLineFormulaSolvedAndConverted);
				String sLineFormulaSolvedAndConvertedAsExpression = sLineFormulaSolvedAndConverted;
				assertEquals(sExpressionSolved, sLineFormulaSolvedAndConvertedAsExpression);
			}else {
				assertEquals(sExpressionSolved, sLineFormulaSolvedAndConverted);
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_PATH_UNSUBSTITUTED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....			
			assertTrue(objEntry.isParsed());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																									
			assertFalse(objEntry.isPathSubstituted());
			assertFalse(objEntry.isPathSubstitutedChanged());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++Varaiablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());														
			assertTrue(objEntry.isVariableSubstituted());
		      //+++ Werte kann man hier doch auch eigentlich nicht so abfragen
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...							
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_PATH_SUBSTITUTED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....					
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.	
			assertTrue(objEntry.isParsed()); 
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());																
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertFalse(objEntry.isPathSubstituted());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//+++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...				
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++++++++++++++++++++


			//+++ kann man hier doch auch eigentlich nicht so abfragen					
			assertFalse(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn nicht gesolved wird, dann ist doch geparsed worden....
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
		
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Variablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());																
			assertTrue(objEntry.isVariableSubstituted());
				//+++ kann man hier doch auch eigentlich nicht so abfragen
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertIsJavaCallSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsCallSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsCallSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);		
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertTrue(objEntry.isSolved());			
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.

			//DEFAULT: Wenn der Solver generel ausgestellt wird, dann wird nix dahinter aufgerufen.
			//ABER:    Call und JavaCallSolver ueberschreiben die Methode isParserEnabledThis.
			assertIsCall(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJavaCallSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
//			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
//			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
//			if(bCallSolverCalledPrevious) {
//				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
//				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
//			}else {
//				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
//				if(bAsEntry) {
//					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
//					assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
//				}else {
//					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
//					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
//				}
//			}
			
			assertIsCallSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
							

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Merke: Auch wenn die Solver wg. des USE... Flags nicht laufen werden, so wird das Parsen immer gemacht
			//       !!! Aber nur bis zur naechsten "Parent-Tag-Grenze". D.h. isJson wird gesetzt werden auch ohne aktives Json. isJsonMap allerdings nicht!!!!
			//Hier kann man isJavaCall() auch nur abfrage, weil es noch keinen anderen "CallTyp" gibt.
			//assertTrue(objEntry.isJavaCall());            ///Beim Parsen wird halt nur isCall festgestellt. Auch wenn useCall = false ist. Den "KindParser"/ "KindSolver" nutzt man dann nicht. 			
			assertIsJavaCall(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			assertIsJavaCallSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//assertFalse(objEntry.isJavaCallSolveCalled()); //OHNE Call-Solver Aufruf, keinen JavaCallSolver aufruf.
			assertIsJavaCallSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//assertFalse(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
			
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}

	private static boolean assertFileIniEntry_solve_FLAGSET_CALL_UNSOLVED_JAVACALL_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....			
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());			
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Variablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());														
			assertTrue(objEntry.isVariableSubstituted());
			//+++ Werte kann man hier doch auch eigentlich nicht so abfragen
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertTrue(objEntry.isSolved());     //unsolved - Flagset
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.

			//DEFAULT: Wenn der Solver generel ausgestellt wird, dann wird nix dahinter aufgerufen.
			//ABER:    Call und JavaCallSolver ueberschreiben die Methode isParserenabledThis.      
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(bCallSolverCalledPrevious) {
				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
				assertFalse(objEntry.isCallSolved());
			}else {
							
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
				if(bAsEntry) {
					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
					assertTrue(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom JAVACALL-Handler gemacht, wenn CALL-Handler nicht verwendet wurde
				}else {
					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
				}
			}
			
								
			assertTrue(objEntry.isJavaCall());            ///Beim Parsen wird das festgestellt, JavaCall ist enabled
			assertTrue(objEntry.isJavaCallSolved());      //Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JAVACALL_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//+++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....		
			assertTrue(objEntry.isParsed()); 
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());			
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...			
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertTrue(objEntry.isCallSolveCalled());
			assertTrue(objEntry.isCallSolved());
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertTrue(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertTrue(objEntry.isJavaCallSolved());
			assertIsJavaCallSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNotNull("NOT NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JAVACALL_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isParsed());
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);		
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
	
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//+++Varaiablen Substitution waere an +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isVariableSubstituteCalled());
																//+++ kann man hier doch auch eigentlich nicht so abfragen														
			assertTrue(objEntry.isVariableSubstituted());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++				
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);								
			assertTrue(objEntry.isSolved());
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			assertFalse(objEntry.isJson()); //Ergebnisse kommen vom Parsen, JSON unsolved
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Frueher: Ist der JavaCall-SOLVER deaktiviert, wird nun nicht der CallSolver aufgerufen, also das Kennzeichen nicht gesetzt
			//Jetzt 20250311: Durch die zu ueberschreibende Methode aus ISolveUserZZZ .updateValueSolveCalled(...) werden alle relevanten Kennzeichen gesetzt
			//Generische Problematik. Stichwort "Elterntag" TODOGOON20250308; TICKET20250308; Diese Testutility wird auch von KernelJavaCallIniSolverZZZTest aufgerufen.
			//ABER: Wenn der Solver generel aus gestellt wird, dann wird nix dahinter aufgerufen.
			assertTrue(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertTrue(objEntry.isCallSolveCalled());
			assertTrue(objEntry.isCallSolved()); //trotz Deaktivierung des Solvers mit JAVACALL-Unsolved Flag wird der CALL-Solver ausgefuehrt					

			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
						
			assertFalse(objEntry.isJavaCall());
			
			//Hier differenzieren, ob der Aufruf direkt erfolgte oder schon der Solver des "Elterntags" aufgerufen worden ist......
			bCallSolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelCallIniSolverZZZ.sTAG_NAME);
			if(bCallSolverCalledPrevious) {
				System.out.println(("Vorher wurde Call-Solver aufgerufen. Also Java-Call-Solver nicht direkt aufgerufen."));
				assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
			}else {
				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
//				if(bAsEntry) {
//					System.out.println(("Aufruf als Entry. Also kann JavaCall-Solver nicht direkt aufgerufen worden sein."));
//					assertFalse(objEntry.isJavaCallSolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
//				}else {
//					System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
//					assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
//				}
				
				//ABER: Hier ist nicht call-deaktiviert, sondern nur javacall. Darum wird call immer vorher aufgerufen, egal ob entry oder nicht.
				System.out.println(("Vorher wurde Call-Solver NOCH NICHT aufgerufen. Also JavaCall-Solver direkt aufgerufen."));
				assertTrue(objEntry.isJavaCallSolveCalled());  //aber wenn der JavaCallSolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....									
			}
			assertFalse(objEntry.isJavaCallSolved());      //Aber: Das es gilt auch beim direkten Aufruf: Der konkrete JAVACALL-Solver ist duch Flags deaktiviert, er wird zwar aufgerufen, aber nicht ausgefuehrt				
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
							
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.

			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	private static boolean assertFileIniEntry_solve_FLAGSET_JSON_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			
			boolean bJsonSolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false; boolean bJsonArraySolverCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); 
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);

			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
				
		
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); 

			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++
			//### JSON
			assertTrue(objEntry.isJson()); 	//Das kommt aus dem PARSEN					
			assertIsJsonSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);		
			assertIsJsonSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry); 
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSON_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	private static boolean assertFileIniEntry_solve_FLAGSET_JSONARRAY_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false; boolean bJsonArraySolverCalledPrevious = false; boolean bExpressionHandlerCalledPrevious = false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertTrue(objEntry.isSolved());
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson());
			assertFalse(objEntry.isJsonMap());
			assertTrue(objEntry.isJsonArray());
			assertIsJsonArraySolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonArraySolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen
//			bJsonArraySolverCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelJsonIniSolverZZZ.sTAG_NAME);
//			bExpressionHandlerCalledPrevious = VectorUtilZZZ.containsString((Vector) objEntry.getHistorySolveCalledVector(), KernelExpressionIniHandlerZZZ.sTAG_NAME);
//			if(bExpressionHandlerCalledPrevious && !bJson) {
//				System.out.println(("Vorher wurde Expression-Handler aufgerufen. Also JsonArray-Solver nicht direkt aufgerufen."));				
//				assertFalse(objEntry.isJsonArraySolveCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
//				assertFalse(objEntry.isJsonArraySolved());
//			}else if(bJsonArraySolverCalledPrevious) {
//				System.out.println(("Vorher wurde JsonArray-Solver aufgerufen. Also JsonArray-Solver nicht direkt aufgerufen."));
//				assertFalse(objEntry.isJsonArraySolveCalled()); //Der Aufruf wird vom Json-Handler vermieden, da durch Flag deaktiviert.
//				assertFalse(objEntry.isJsonArraySolved());
//			}else {
//				//ABER: Bei einem "entry"-Aufruf wird auch der JavaCall-Solver nicht direkt aufgerufen.
//				if(bAsEntry) {
//					System.out.println(("Aufruf als Entry. Also kann JsonArray-Solver nicht direkt aufgerufen worden sein."));
//					assertTrue(objEntry.isJsonArraySolveCalled()); //Der Aufruf wird vom CALL-Handler vermieden, da durch Flag deaktiviert.
//					assertTrue(objEntry.isJsonArraySolved());
//				}else {					
//					System.out.println(("Vorher wurde Json-Solver NOCH NICHT aufgerufen. Also JsonArray-Solver direkt aufgerufen."));
//					assertTrue(objEntry.isJsonArraySolveCalled());  //aber wenn der JavaArraySolver direkt aufgerufen wurde. Wird der Aufruf nicht vermieden....
//					assertTrue(objEntry.isJsonArraySolved());
//						//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen
//					
//				}
//			} 
//			assertIsJsonArraySolvedChanged(objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			
			
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONARRAY_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....			
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());			
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...								
			assertTrue(objEntry.isSolved());     //Solve wird sogar abgeschlossen, nur JSON_ARRAY bleibt unaufgeloest.
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson()); //Ergebnisse kommen vom Parsen.
			assertFalse(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray()); 
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONARRAY_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONMAP_SOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isParsed()); 
			
			//+++++++++++++++++++++++
			assertTrue(objEntry.isPathSubstituteCalled());
			//Beim Solven kann nicht auf die Änderung vom Parsen geprüft werden, 
			//da wir das Ergebnis des Parsens nicht haben.
			//Merke: .isParsedChanged() laesst sich hier nicht ermitteln.
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertTrue(objEntry.isSolved());
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson());
			assertTrue(objEntry.isJsonMap());
			assertFalse(objEntry.isJsonArray());
			
			//+++ Auf Werte kann man auf JSON-Ebene, die ggfs. auch fuer andere Eingabestrings verwendet wird, nicht abfragen						
			assertIsJsonMapSolveCalled(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);			
			assertIsJsonMapSolved(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			assertIsJsonMapSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());

			bReturn = true;
		}//end main:
		return bReturn;
	}


	private static boolean assertFileIniEntry_solve_FLAGSET_JSONMAP_UNSOLVED_(ISolveEnabledZZZ objSolver, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, IKernelConfigSectionEntryZZZ objEntry, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, boolean bAsEntry) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sExpression = sExpressionIn;
			String sExpressionSubstituted = sExpressionSubstitutedIn;
			String sExpressionSolved = sExpressionSolvedIn;
			
			String sTagStartZ = "<Z>";
			String sTagEndZ = "</Z>";	
			
			String sFormulaSolvedAndConverted=null; String sFormulaSolvedAndConvertedAsExpression=null;
			String sExpressionSubstituted2compareWithSolved=null; String sExpressionSubstituted2compareWithExpression = null; 
			String sExpressionParsed2compareWithSubstituted=null; String sExpressionSolved2compareWithSubstituted=null;
			String sExpression2compareWithSubstituted=null; String sExpression2compareWithSolved = null;
			String sSubstituted2compare=null;
				
			boolean bCallSolverCalledPrevious=false;
		
		
			//######## TEST-TEIL ###########################
			assertTrue(objEntry.isExpression()); //ohne Expression-Nutzung kein Expression Eintrag!!!
			
			//++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isParseCalled()); //Auch wenn die Expression nicht verarbeitet wird, dann ist doch geparsed worden....
			assertTrue(objEntry.isParsed());
			assertIsParsedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
								
			//+++++++++++++++++++++++ ++++++++++++++++++++++ Weil nicht gesolved wurde, kann auf das Parse Ergebnis zugegriffen werden
			assertTrue(objEntry.isPathSubstituteCalled());
			assertTrue(objEntry.isPathSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob ein INI-PATH Ausdruck darin ist
			assertIsPathSubstitutedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);						

			//++++++++++++++++++++++++
			//++++++++++++++++++++++++
			
			assertTrue(objEntry.isVariableSubstituted()); //falls das entsprechende Flag gesetzt ist, unabhaengig davon, ob eine INI-Variable darin ist
			//++++++++++++++++++++++++				
			//++++++++++++++++++++++++
			assertTrue(objEntry.isSolveCalled()); //Aufgerufen wurde der solveCall ja...								
			assertTrue(objEntry.isSolved());     //solve selbst wurde ja ausgefuehrt. 
			assertIsSolvedChanged(objSolver, objEnumSurrounding, objEntry, sExpressionIn, sExpressionSubstitutedIn, sExpressionSolvedIn, bAsEntry);
			//+++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			sFormulaSolvedAndConverted = objEntry.getValueFormulaSolvedAndConverted();
			assertNull("NULL erwartet. Wert ist aber '" + sFormulaSolvedAndConverted + "'", sFormulaSolvedAndConverted); //Da keine Formel enthalten ist
					
			sFormulaSolvedAndConvertedAsExpression = objEntry.getValueFormulaSolvedAndConvertedAsExpression();					
			assertEquals(XmlUtilZZZ.computeTagNull(), sFormulaSolvedAndConvertedAsExpression);//Da keine Formel enthalten ist.

			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++

			
			assertTrue(objEntry.isJson()); //Ergebnisse kommen vom Parsen
			assertFalse(objEntry.isJsonMap()); //isParserEnabledCustom=true, aber isParserEnabledThis=false also wird der eigene Tag nicht gefunden
			assertFalse(objEntry.isJsonArray()); //JSON_ARRAY wird nicht benutzt
							
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. keine Variablen in der Expression sind.
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			assertFalse(objEntry.isCallSolveCalled());
			assertFalse(objEntry.isCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			//+++++++++++++++++++++++++
			assertFalse(objEntry.isJavaCallSolveCalled()); //trotz JAVACALL-Unsolved Flag wird der JAVACALL-Solver durchaus aufgerufen
			assertFalse(objEntry.isJavaCallSolved());//Der konkrete Solver ist nicht involviert
			//+++++++++++++++++++++++++
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. doch CRYPTED Werte in der Expression sind
			assertFalse(objEntry.isDecrypted());
			assertNull(objEntry.getValueDecrypted()); //Merke: sValue kann unterschiedlich zu dem decrypted Wert sein. Wenn etwas drumherum steht.
			
			//+++ Auf Werte kann man hier eigentlich nicht so abfragen, weil ggfs. KEINE CALL-Werte in der Expression sind
			assertFalse(objEntry.isCall());		//Beim Parsen wird das festgestellt
			assertFalse(objEntry.isJavaCall()); 	//Beim Parsen wird das festgestellt
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingClassname() + "'", objEntry.getCallingClassname());
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getCallingMethodname() + "'", objEntry.getCallingMethodname());
			
			//fail("Testutil for flagset missing: " + sFLAGSET_JSONMAP_UNSOLVED);

			bReturn = true;
		}//end main:
		return bReturn;
	}

	
	public static boolean isExpressionValidForFlagset_parse(IEnumSetMappedTestFlagsetZZZ objEnumFlagset, String sExpressionIn) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{
			//+++ TEST DISPATCHER (light) +++++++++++
			//Wenn ein Ausdruck keine Expression ist, dann nur den _unexpressed Test erlauben:
			
			bReturn = true;
		}//end main:
		return bReturn;
	}

	public static boolean isExpressionValidForFlagset_solve(IEnumSetMappedTestFlagsetZZZ objEnumFlagset, String sExpressionIn) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{
			//+++ TEST DISPATCHER (light) +++++++++++
			//Wenn ein Ausdruck keine Expression ist, dann nur den _unexpressed Test erlauben:
			boolean bHasZ = XmlUtilZZZ.isExpression4TagXml(sExpressionIn, KernelExpressionIniHandlerZZZ.sTAG_NAME);
			if(!bHasZ) {
				if(!(objEnumFlagset == EnumSetMappedTestCaseFlagsetTypeZZZ.UNEXPRESSED)) {
					break main;
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean assertObjectValue_Solver_OnParseFirstVector(ITagBasicChildZZZ objExpressionSolver, Vector3ZZZ<String>vecValue, IEnumSetMappedTestSurroundingZZZ objEnumSurrounding, boolean bUseExpressionGeneral, boolean bUseParser, boolean bUseSolver, String sExpressionIn, String sExpressionSubstitutedIn, String sExpressionSolvedIn, String sPreIn, String sPostIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sExpression; String sExpressionSubstituted; String sExpressionSolved; String sPre; String sPost;
			String sValue; String sValueUsed;		
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
				
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sExpressionSolved='"+sExpressionSolved+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 0='"+vecValue.get(0).toString()+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 1='"+vecValue.get(1).toString()+"'");
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + "sValue @ 2='"+vecValue.get(2).toString()+"'");
			if(bUseParser && bUseExpressionGeneral) {
				assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString())); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
				assertFalse(StringZZZ.isEmpty(vecValue.get(1).toString())); //in der 1ten Position ist der Tag
				assertFalse(StringZZZ.isEmpty(vecValue.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String	
			}else {
				assertFalse(StringZZZ.isEmpty(vecValue.get(0).toString())); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
				assertTrue(StringZZZ.isEmpty(vecValue.get(1).toString())); //in der 1ten Position ist der Tag
				assertTrue(StringZZZ.isEmpty(vecValue.get(2).toString())); //in der 2ten Position ist der Tag nach dem gesuchten String					
			}
			sValue = VectorUtilZZZ.implode(vecValue);
			assertEquals(sExpressionSubstituted, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			
			//+++
			sExpressionSurroundedTemp = sExpressionSubstituted;
			if(bUseParser && bUseExpressionGeneral) {		
				sValue = vecValue.get(1).toString();;//in der 0ten Position ist der String vor der Map, in der 3ten Position ist der String nach der Map.
				
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, sTagStartZ, sTagEndZ);
				
				//Es ist durchaus erlaubt, dass der ParentName = NULL ist. Darum das Default ExceptionHandling hier uebersteuern
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getParentName(), ExceptionHandlingMainTypeZZZ.TypeZZZ.CATCH_AWAY);
				sExpressionSurroundedTemp = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSurroundedTemp, objExpressionSolver.getName());
				
				//PRE und POST umgebend entfernen!!!
				sExpressionSurroundedTemp = StringZZZ.trimLeft(sExpressionSurroundedTemp, sPre);
				sExpressionSurroundedTemp = StringZZZ.trimRight(sExpressionSurroundedTemp, sPost);
				
				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			}else {
				sValue = vecValue.get(0).toString();;//in der 0ten Position ist der String, entweder wenn der Tag nicht enthalten ist ODER der Parser (ggfs. entsprechend dem Solver) abgestellt ist
				assertEquals(sExpressionSurroundedTemp, sValue); //dann sollen auch die Z-Tags drumherum nicht entfernt werden.
			}
			assertTrue(StringZZZ.contains(sValue,sExpressionSurroundedTemp,false)); //da der Wert selbst nicht als Argument in der Methode uebergeben wurde, koennen wir nur auf Existenz im Gesamtergebnis pruefen.
			
			
			//TODOGOON20250615;//Hole den objEntry.getValue() und vergleiche mit dem Tag-Wert-selbst
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
