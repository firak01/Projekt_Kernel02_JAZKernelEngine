package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

import static basic.zBasic.IConstantZZZ.*;

import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import junit.framework.TestCase;

public class KernelConfigSectionEntryUtilZZZTest extends TestCase{

	
	//#######################################################
	//#### CONTAINED
	//#######################################################
	public void testGetValueExpressionTagContainedRemoved_SingleTag1(){
		String sTagContainStart; String sTagContainEnd; String sTagRemoveStart; String sTagRemoveEnd; String sPRE; String sMID; String sPOST;
		String sExpressionSource; String sExpressionSolved; String sValue; String sExpression; String sExpression1; String sExpression2;
		Vector3ZZZ<String> vecExpressionSource;Vector3ZZZ<String>vecExpressionSolved;
		try{	
			//###################################################
			//VORGEZOGENER FEHLERTEST START
			
			//TODOGOON20250129;
			
			//########################################################################	
			//3a) die JSON Map ist schoen verschachtelt, Fall OHNE pre - post Werten
			//Ansatz auf einer Ebene tiefer
			//########################################################################
			sTagContainStart = "<JSON>";
			sTagContainEnd = "</JSON>";			
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;			
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "<Z><JSON>";
			sMID = "<JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED +"</JSON:MAP>";
			sPOST =  "</JSON></Z>";			
			vecExpressionSource = new Vector3ZZZ<String>();		
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);

			
			
			//VORGEZOGENER FEHLERTEST ENDE
			//###################################################
			
			
			//########################################################################
			//1a) die JSON Map ist schoen verschachtelt, Fall OHNE pre - post Werte
			//Ansatz auf oberster Ebene
			//########################################################################						
			sTagContainStart = "<Z>";
			sTagContainEnd = "</Z>";	
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";			
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE =  "<Z>";
			sMID = "<JSON><JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED +"</JSON:MAP></JSON>";
			sPOST = "</Z>";		
			vecExpressionSource = new Vector3ZZZ<String>();					
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);			
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
			
			//############################################################################
			//2a) die JSON Map ist schoen verschachtelt, Fall MIT pre - post Werten
			//Ansatz auf oberster Ebene
			//########################################################################			
			sTagContainStart = "<Z>";
			sTagContainEnd = "</Z>";	
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";
			
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "abcd<Z>";
			sMID = "<JSON><JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT + "</JSON:MAP></JSON>";
			sPOST = "</Z>xyz";			
			vecExpressionSource = new Vector3ZZZ<String>();					
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);

			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
				
			//########################################################################	
			//3a) die JSON Map ist schoen verschachtelt, Fall OHNE pre - post Werten
			//Ansatz auf einer Ebene tiefer
			//########################################################################
			sTagContainStart = "<JSON>";
			sTagContainEnd = "</JSON>";			
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";
			
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT;			
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "<Z><JSON>";
			sMID = "<JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED +"</JSON:MAP>";
			sPOST =  "</JSON></Z>";			
			vecExpressionSource = new Vector3ZZZ<String>();		
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);

			//########################################################################	
			//4a) die JSON Map ist schoen verschachtelt, Fall MIT pre - post Werten
			//Ansatz auf einer Ebene tiefer
			//########################################################################
			sTagContainStart = "<JSON>";
			sTagContainEnd = "</JSON>";			
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";
			
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "abcd<Z><JSON>";
			sMID = "<JSON:MAP>" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_SOLVED +"</JSON:MAP>";
			sPOST = "</JSON></Z>xyz";
			vecExpressionSource = new Vector3ZZZ<String>();		
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);		
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));			
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);

			//######################################################################################
			//######################################################################################
			//######################################################################################
			/* 5a) Beim Call solver kommt eine Situation vor, geschaffend durch INI-Pfad Ersetzung
			 * Hier ist Z-Tag an mehreren Stelle enthalten.
			 * Vector 0= PRE<Z><Z:Call>
			  Vector 1= <Z:Java><Z:Class><Z>basic.zBasic.util.machine.EnvironmentZZZ</Z></Z:Class><Z:Method>getHostName</Z:Method></Z:Java>
			  Vector 2= </Z:Call></Z>POST

			  und<Z>bzw </Z> soll entfernt werden, aber nur aus dem Z:Class Tag Inhalt.
			*/
			sTagContainStart = "<Z:Class>";
			sTagContainEnd = "</Z:Class>";	
			sTagRemoveStart = "<Z>";
			sTagRemoveEnd = "</Z>";
			
			sPRE="PRE<Z><Z:Call>";
			sMID="<Z:Java><Z:Class><Z>basic.zBasic.util.machine.EnvironmentZZZ</Z></Z:Class><Z:Method>getHostName</Z:Method></Z:Java>";
			sPOST="</Z:Call></Z>POST";
			vecExpressionSource = new Vector3ZZZ<String>();		
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sValue = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sValue);
							
			//Als Ergebnis bleibt Z:Class Tag drin. Der innerhalb liegenden Z-Tag ist aber entfernt, dar ganz aussen liegende bleibt erhalten.
			sExpression = "PRE<Z><Z:Call><Z:Java><Z:Class></Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call></Z>POST";
			assertNotNull(sExpression);	
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));
			//Nein: Hier bleibt der Tag 1x ganz aussen erhalten
			//assertFalse(sExpression.contains(sTagRemoveStart));
			//assertFalse(sExpression.contains(sTagRemoveEnd));
			assertEquals("Der Tag '" + sTagRemoveStart + "' sollte nur 1x vorkommen", 1, StringZZZ.count(sExpression, sTagRemoveStart));
			assertEquals("Der Tag '" + sTagRemoveEnd + "' sollte nur 1x vorkommen", 1, StringZZZ.count(sExpression, sTagRemoveEnd));
			
			
			//#######################################################
			//6a) weiteres Veschachtelungsbeispiel
			//    Oberste Ebene als Ansatzpunkt
			//#######################################################			
			sTagContainStart = "<Z>";
			sTagContainEnd = "</Z>";	
			sTagRemoveStart = "<Z:Code>";
			sTagRemoveEnd = "</Z:Code>";	
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));	
			sExpression1 = sExpression;
		
			//b) ++++++++++++++++++++++++++++++++++++++++++++++++
		
			sPRE = "<Z><Z:Encrypted>";			
			sMID = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			sPOST = "</Z:Encrypted></Z>";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
								
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);			
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));	
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
			
			//###########################################################
			//7a) weiteres Verschachtelungsbeispiel
			//    eine Ebene Tiefer als Ansatzpunkt
			//###########################################################					
			sTagContainStart = "<Z:Encrypted>";
			sTagContainEnd = "</Z:Encrypted>";	
			sTagRemoveStart = "<Z:Code>";
			sTagRemoveEnd = "</Z:Code>";			
			
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));	
			sExpression1 = sExpression;
					
			//b) +++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "<Z><Z:Encrypted>";
			sMID = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			sPOST = "</Z:Encrypted></Z>";		
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
								
			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));	
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
																				
			//#########################################
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testGetValueExpressionTagContainedRemoved_MultiTags(){
		//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors".
		
		String sTagContainStart; String sTagContainEnd; String sTagRemoveStart; String sTagRemoveEnd; String sPRE; String sMID; String sPOST;
		String sExpressionSource; String sExpressionSolved; String sValue; String sExpression; String sExpression1; String sExpression2;
		Vector3ZZZ<String> vecExpressionSource; Vector3ZZZ<String>vecExpressionSolved;
		try{	
						
			//die JSON Map ist schoen verschachtelt
			//sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			
			//########################################################################
			//### 
			//#########################################################################
			sTagContainStart = "<Z>";
			sTagContainEnd = "</Z>";						
			sTagRemoveStart = "<JSON:MAP>";
			sTagRemoveEnd = "</JSON:MAP>";	
			
			sPRE = "<Z><JSON><JSON:MAP>";
			sMID = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
			sPOST = "</JSON:MAP></JSON></Z>";				
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			

			vecExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(vecExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainStart, sTagContainEnd);			
			sExpression = VectorUtilZZZ.implode(vecExpressionSolved);
			assertNotNull(sExpression);						
			assertTrue(sExpression.contains(sTagContainStart));
			assertTrue(sExpression.contains(sTagContainEnd));			
			assertFalse(sExpression.contains(sTagRemoveStart));
			assertFalse(sExpression.contains(sTagRemoveEnd));
			
			
			//TODOGOON20250128;
			/*
			//########################################################################
			//### 
			//#########################################################################
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
			
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
						
			//################################################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll der Z-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innnen", soll der JSON:MAP-Tag bestehen bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
		
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			
			//################################################################################	
			//### noch gemischter 
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON>";
			sTagEndSource = "</JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON:MAP>" + sExpression + "</JSON:MAP>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag erhalten bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag geloescht werden...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			*/
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
	
	public void testGetValueExpressionTagContainedRemoved_SingleTag2_WithInterTagContent(){
		String sTagContainerStart; String sTagContainerEnd; String sTagRemoveStart ; String sTagRemoveEnd; String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpressionSolved;		
		String sValue;
		
		try{	
			//#######################
			//Hier der Tag fuer mehre Tags auf verschiedenen Postitionen des Vektors
			sTagRemoveStart= "<Z>";
			sTagRemoveEnd = "</Z>";	
			sTagContainerStart = "<Z:Method>";
			sTagContainerEnd = "</Z:Method>";
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>";			
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method></Z:Method></Z:Java></Z:Call></Z>";
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, sTagContainerStart, sTagContainerEnd);
			assertEquals(sExpressionSolved, sValue);
			
			//#######################
			//Tag Inhalt loeschen UND "Zwischen" den Tags vorhandenen Inhalt loeschen.
			sTagRemoveStart= "<Z>";
			sTagRemoveEnd = "</Z>";	
			sTagContainerStart = "<Z:Method>";
			sTagContainerEnd = "</Z:Method>";
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method>123<Z>abcde</Z>45</Z:Method></Z:Java></Z:Call></Z>";			
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method></Z:Method></Z:Java></Z:Call></Z>";
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, false, false, sTagContainerStart, sTagContainerEnd);
			assertEquals(sExpressionSolved, sValue);
			
			
			//#######################
			//Tag Inhalt loeschen NUR "Zwischen" den Tags vorhandenen Inhalt behalten.
			sTagRemoveStart= "<Z>";
			sTagRemoveEnd = "</Z>";	
			sTagContainerStart = "<Z:Method>";
			sTagContainerEnd = "</Z:Method>";
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method>123<Z>abcde</Z>45</Z:Method></Z:Java></Z:Call></Z>";			
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method>12345</Z:Method></Z:Java></Z:Call></Z>";
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, false, true, sTagContainerStart, sTagContainerEnd);
			assertEquals(sExpressionSolved, sValue);
									
			//#######################
			//Tag Inhalt behalten UND "Zwischen" den Tags vorhandenen Inhalt behalten.
			sTagRemoveStart= "<Z>";
			sTagRemoveEnd = "</Z>";	
			sTagContainerStart = "<Z:Method>";
			sTagContainerEnd = "</Z:Method>";
			sExpressionSource = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method>123<Z>abcde</Z>45</Z:Method></Z:Java></Z:Call></Z>";			
			sExpressionSolved = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method>123abcde45</Z:Method></Z:Java></Z:Call></Z>";
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved(sExpressionSource, sTagRemoveStart, sTagRemoveEnd, true, sTagContainerStart, sTagContainerEnd);
			assertEquals(sExpressionSolved, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
	//#######################################################
	//### SURROUNDING CONTAINER
	//#######################################################
	
	public void testGetValueExpressionTagContainerSurroundingRemoved_FromOutToIn_MultiTags(){
		String sTagStartZ; String sTagEndZ; String sTagContainerStart; String sTagContainerEnd;  
		String sExpression; String sExpressionSolved; 
		String sValue="";

		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors - von Aussen nach Innen entfernt".

			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist false am Ende, damit wird von aussen nach innen das Tag entfernt.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sTagContainerStart="<Z:Method>";
			sTagContainerEnd="</Z:Method>";
			
			sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false, false, sTagContainerStart, sTagContainerEnd);
			assertEquals(sExpressionSolved, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	public void testGetValueExpressionTagContainerSurroundingRemoved_FromInToOut_MultiTags(){
		String sTagStartZ; String sTagEndZ; String sTagContainerStart; String sTagContainerEnd;  
		String sExpression; String sExpressionSolved; 
		String sValue="";
		
		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors - von Aussen nach Innen entfernt".
			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist der erste bool Parameter, damit wird von aussen nach innen das Tag entfernt.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";
			sTagContainerStart="<Z:Method>";
			sTagContainerEnd="</Z:Method>";
			
			sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt
			try {
				sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, true, false, sTagContainerStart, sTagContainerEnd);
				fail("Method should have throw an exception, created by a watchdog... 'toomany number of surrounding opening/closing tags'");
			}catch(ExceptionZZZ ez) {
				String sCause = ez.getMessageLast();
				if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)
					|sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY)
					) {
					//alles o.k., erwarteter Fehler!
				}else {
					if(sCause.contains("container tag at mid-position of the vector3")) {					
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed 'container tag at mid-position of the vector3':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;					
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else {
						ExceptionZZZ ez2 = new ExceptionZZZ("Unexpected exception", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),ez);
						throw ez2;						
					}
				}
			}
			
			//###############  
			// Fuer den Test Z:class-Tag nach hinten. 
			//      und  ein Z:Dummy eingefuegt. Damit gleiche Anzahl Tags vor wie hinter dem Container erzwungen. Also eine Art Symmetrie erzeugt.
			//ABER: Das reicht noch nicht, da von Innen nach aussen nun die Tagparts kurz danach geschlossener, einfacher Tags entfernt wuerden.
			sExpression = "<Z><Z:Call><Z:Java><Z:Dummy><Z>bla</Z></Z:Dummy><Z:Method><Z>abcde</Z></Z:Method><Z:Class><Z>xyz</Z></Z:Class></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Dummy><Z>bla</Z></Z:Dummy><Z:Method><Z>abcde</Z></Z:Method><Z:Class><Z>xyz</Z></Z:Class></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt
			try {
				sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, true, false, sTagContainerStart, sTagContainerEnd);
				fail("Method should have throw an exception, created by a watchdog... 'toomany number of surrounding opening/closing tags'");
			}catch(ExceptionZZZ ez) {
				String sCause = ez.getMessageLast();
				if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)
					|sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY)
					) {
					//alles o.k., erwarteter Fehler!
				}else {
					if(sCause.contains("container tag at mid-position of the vector3")) {					
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed 'container tag at mid-position of the vector3': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;					
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else {
						ExceptionZZZ ez2 = new ExceptionZZZ("Unexpected exception", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),ez);
						throw ez2;						
					}
				}
			}
			
		

			//Eine funktionierende Variante			
			//Fuer den Test ein Z:Class rausgenommen, so dass keine weiteren Z-Tags vorhanden sind.
			//Teste dazu noch vor und hinter dem Tag Text.
			sExpression = "vor<Z>abcd<Z:Call><Z:Java><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>xyz</Z>hinter"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "vorabcd<Z:Call><Z:Java><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>xyzhinter"; //INI-Pfade werden trotzdem ersetzt
			try {
				sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, true, false, sTagContainerStart, sTagContainerEnd);
//				fail("Method should have throw an exception, created by a watchdog... 'toomany number of surrounding opening/closing tags'");
			}catch(ExceptionZZZ ez) {
				String sCause = ez.getMessageLast();
//				if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)
//					|sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY)
//					) {
//					//alles o.k., erwarteter Fehler!
//				}else {
					if(sCause.contains("container tag at mid-position of the vector3")) {					
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed 'container tag at mid-position of the vector3':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;					
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + "':" + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else if(sCause.contains(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL)) {						
						ExceptionZZZ ez2 = new ExceptionZZZ("Watchdog failed '" + XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + "': " + ez.getMessageLast(), iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez2;
					}else {
						ExceptionZZZ ez2 = new ExceptionZZZ("Unexpected exception", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName(),ez);
						throw ez2;						
					}
//				}
			}
			assertEquals(sExpressionSolved, sValue);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception. '" + ez.getMessageLast() + "' at '" + ez.getFunctionLast() + "'");			
		}
	}
	
	
	//#######################################################
	//#### SURROUNDING
	//#######################################################
	public void testGetValueExpressionTagSurroundingRemoved_SingleTag(){
		String sTagpartRemoveStart; String sTagpartRemoveEnd; String sPRE; String sMID; String sPOST;
		String sValue; String sExpressionSource; String sExpression; String sExpression1; String sExpression2; String sExpression3;
		Vector3ZZZ<String> vecExpressionSource;
		
		try{	
			sTagpartRemoveStart = "<Z>";
			sTagpartRemoveEnd = "</Z>";
			
			//##########################
			//### Vorgezogenern Fehlertest: Start
			
						
			//### Vorgezogener Fehlertest: Ende
			//###########################
			
			
			
			//#########################################
			//### 1a) die Encryption ist schoen verschachtelt
			//### Fall OHNE PRE und POST Werten.
			//##########################################
			sExpressionSource = KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT;
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
						
			//b) ++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "<Z><Z:Encrypted>";
			sMID = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			sPOST = "</Z:Encrypted></Z>";			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd, true, false);			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
			
		
			//c) +++++++++++++++++++++++++++++++++++++++++++++++
			sPRE = "<Z><Z:Encrypted>";
			sMID = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			sPOST = "</Z:Encrypted></Z>";			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			sExpression3 = sExpression;
			assertEquals(sExpression1, sExpression3);

	
			//#########################################
			//### 2a) Encryption ist schoen verschachtelt
			//### Fall MIT PRE und POST Werten.
			//##########################################
			sExpressionSource = "abcd" + KernelEncryptionIniSolverZZZTest.sEXPRESSION_ENCRYPTION01_DEFAULT + "xyz";			
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++
			sPRE = "abcd<Z><Z:Encrypted>";
			sMID = "<Z:Cipher>ROT13</Z:Cipher><Z:Code>nopqr</Z:Code>";
			sPOST = "</Z:Encrypted></Z>xyz";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
			
			//############################
			//### 3a)
			//20241103: Aber jetzt mit pre - post Werten
		
			//TODOGOON20241103;
			/*/Beim Call solver kommt eine Situation vor, hier abbilden
			 * Vector 0= PRE<Z><Z:Call>
			  Vector 1= <Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java>
			  Vector 2= </Z:Call></Z>POST

			  und<Z>
              bzw </Z> soll entfernt werden.
			*/
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE="PRE<Z><Z:Call>";
			sMID="<Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java>";
			sPOST="</Z:Call></Z>POST";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>basic.zBasic.util.machine.EnvironmentZZZ</Z:Class><Z:Method>getHostName</Z:Method></Z:Java></Z:Call>POST";
			sValue = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sValue);
			assertEquals(sExpression, sValue);
			
			
			
			
			//########################################################################		
			
			//#########################################
			//### 4a) JSON:MAP ist schoen verschachtelt
			//### Fall MIT PRE und POST Werten.
			//### innerste Ebene
			//##########################################
			sTagpartRemoveStart = "<JSON:MAP>";
			sTagpartRemoveEnd = "</JSON:MAP>";
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
			
			//b) +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE="abcd<Z><JSON><JSON:MAP>";
			sMID=KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_JSON;
			sPOST="</JSON:MAP></JSON></Z>xyz";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);
						
			//########################################################################
			
			//#########################################
			//### 5a) JSON:MAP ist schoen verschachtelt
			//### Fall MIT PRE und POST Werten.
			//### eine Ebene hoeher
			//##########################################
			sTagpartRemoveStart = "<JSON>";
			sTagpartRemoveEnd = "</JSON>";	
			sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sExpression = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			assertNotNull(sExpression);
			sExpression1 = sExpression;
			
			//b) ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			sPRE="abcd<Z><JSON><JSON:MAP>";
			sMID=KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT_JSON;
			sPOST="</JSON:MAP></JSON></Z>xyz";
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sPRE);
			vecExpressionSource.replace(1, sMID);
			vecExpressionSource.replace(2, sPOST);
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagpartRemoveStart, sTagpartRemoveEnd);
			
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			sExpression2 = sExpression;
			assertEquals(sExpression1, sExpression2);

		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGetValueExpressionTagSurroundingRemoved_MultiTags(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		Vector3ZZZ<String> vecExpressionSource; boolean bDirectionFromInToOut;
		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors".
			
			//die JSON Map ist schoen verschachtelt
			//sExpressionSource = "abcd" + KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_DEFAULT + "xyz";
			sExpressionSource = KernelJsonMapIniSolverZZZTest.sEXPRESSION_JSONMAP01_CONTENT;
		
			//########################################################################
			//### Test: Entferne aus dem Vector die Tags von "aussen nach innen" vs "innen nach aussen".
			//### Merke: Default ist von "innen nach aussen"...
			//#########################################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "innen nach aussen", bleibt der Z-Tag bestehen...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
						
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagEndZ));
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "innen nach aussen", wird der JSON:MAP-Tag entfernt..
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
						
			//################################################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll der Z-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innnen", soll der JSON:MAP-Tag bestehen bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON><JSON:MAP>";
			sTagEndSource = "</JSON:MAP></JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, sExpression);
			vecExpressionSource.replace(2, sTagEndSource);
			
		
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			
			//################################################################################	
			//### noch gemischter 
						
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag entfernt werden...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z><JSON>";
			sTagEndSource = "</JSON></Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON:MAP>" + sExpression + "</JSON:MAP>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag erhalten bleiben...
			bDirectionFromInToOut = false;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertTrue(sExpression.contains(sTagStartZ));
			assertTrue(sExpression.contains(sTagStartZ));
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Beim Löschen der umgebenden Tags von "aussen nach innen", soll in dieser Konstellation der JSON:MAP-Tag geloescht werden...
			bDirectionFromInToOut = true;
			sExpression = sExpressionSource;
			sTagStartSource = "<Z>";
			sTagEndSource = "</Z>";	
			
			vecExpressionSource = new Vector3ZZZ<String>();
			vecExpressionSource.replace(0, sTagStartSource);
			vecExpressionSource.replace(1, "<JSON><JSON:MAP>" + sExpression + "</JSON:MAP></JSON>");
			vecExpressionSource.replace(2, sTagEndSource);
			
			
			sTagStartZ = "<JSON:MAP>";
			sTagEndZ = "</JSON:MAP>";	
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionSource, sTagStartZ, sTagEndZ, false, bDirectionFromInToOut);					
			sExpression = VectorUtilZZZ.implode(vecExpressionSource);
			assertNotNull(sExpression);
			assertFalse(sExpression.contains(sTagStartZ));
			assertFalse(sExpression.contains(sTagStartZ));
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testGetValueExpressionTagSurroundingRemoved_FromOutToIn_MultiTags(){
		String sTagStartZ; String sTagEndZ;String sTagStartSource; String sTagEndSource;  
		String sExpressionSource; String sExpression; String sExpressionOld;
		String sExpressionSolved; String sExpressionSolvedTagless;
		Vector3ZZZ<String> vecExpressionSource; boolean bDirectionFromInToOut;
		String sValue;
		
		try{	
			//Hier der Test für "mehrere Tags auf anderen Positionen des Vectors - von Aussen nach Innen entfernt".

			
			//+ Wichtige Hilfsmethode pruefen, wichtig ist false am Ende, damit wird von aussen nach innen das Tag entfernt.
			sTagStartZ = "<Z>";
			sTagEndZ = "</Z>";	
			
			sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call></Z>"; //INI-Pfade werden trotzdem ersetzt
			sExpressionSolved = "<Z:Call><Z:Java><Z:Class><Z>xyz</Z></Z:Class><Z:Method><Z>abcde</Z></Z:Method></Z:Java></Z:Call>"; //INI-Pfade werden trotzdem ersetzt
			sValue = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, sTagStartZ, sTagEndZ, false, false);
			assertEquals(sExpressionSolved, sValue);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}
