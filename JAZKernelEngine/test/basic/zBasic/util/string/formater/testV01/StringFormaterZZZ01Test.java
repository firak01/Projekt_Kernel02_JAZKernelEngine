package basic.zBasic.util.string.formater.testV01;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormaterZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.util.string.formater.IStringFormaterZZZ.FLAGZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import junit.framework.TestCase;

/** Erweiterungsansatz: Verwende zur Reduktion der Redundanz eine Helper-Klasse.
 *  Diese wurde von ChatGPT basierend auf den .testV00 Klassen erstellt 
 *  
 * 
 * @author Fritz Lindhauer, 15.11.2025, 16:50:40
 * 
 */
public class StringFormaterZZZ01Test extends TestCase{
	
	private StringFormaterZZZ objLogStringTest = null;

	protected void setUp(){
		try {			
			objLogStringTest = new StringFormaterZZZ();
								
			//################################################
			//Singleton zurücksetzen, damit der Test auch mit mehreren JUnit Tests funktioniert.
			StringFormatManagerZZZ.getInstance().reset();			
			StringJustifierManagerZZZ.getInstance().reset(); //sonst werden die Zeilen unübersichtlich lang mit leerzeichen aufgefüllt
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 	
	}//END setup

	public void testContructor() {
		try{
			//Das soll ein Singleton sein. Einmal definiert, ueberall verfuegbar.
			StringFormaterZZZ objLogString = new StringFormaterZZZ();
			boolean btemp1a = objLogString.setFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD, true);
			assertTrue(btemp1a);
			
			boolean btemp1b = objLogString.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD);
			assertTrue(btemp1b);
					
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testComputeN() {
		try {
			//20240423:
			//MErke: Diese Primfaktorenzerlegung, etc. wird noch nirgendwo verwendet			
			int iNumber = objLogStringTest.computeFormatPositionsNumber();
			assertTrue(iNumber>0);//Verwende die custom-FormatPosition

			int[]iatest = PrimeFactorizationZZZ.primeFactorsAsIntArray(iNumber);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": '" + IntegerArrayZZZ.implode(iatest) + "'");
			
			int iValue = PrimeNumberZZZ.computePositionValueFromPrime(iNumber, 3);
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + iValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testCompute_FormatDefault() {
		
		try{
			DummyTestObjectZZZ dummy = new DummyTestObjectZZZ();
	        String sLog = "der erste Logeintrag";
	        String thread = "[Thread:";
	        String cls = dummy.getClass().getSimpleName() + IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;

	        String result = objLogStringTest.compute(dummy, sLog);

	        StringFormatTestHelper.assertContainsOnce(result, sLog);
	        StringFormatTestHelper.assertThreadAndClass(result, thread, cls);
	        StringFormatTestHelper.assertOrder(result, cls, thread, sLog);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCompute_FormatDefined_Using_Single_Double_Array(){
		try{
			String sLog1 = null; String sLog2 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag";
			sLog2 = "der zweite Logeintrag";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			//+++ Bei 1x Strintype soll der Logeintrag nur 1x erscheinen.					
			IEnumSetMappedStringFormatZZZ[] ienumaFormat01= {
							IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
							IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,																						
							};
			objLogStringTest.setFormatPositionsMapped(ienumaFormat01);
		
			sLogValue = objLogStringTest.compute(objDummy, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag aber nur 1x in der Zeile erscheinen.	
			//a) identischer Fall
			IEnumSetMappedStringFormatZZZ[] ienumaFormat02= {
							IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
							IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
							IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
							};
			
			objLogStringTest.setFormatPositionsMapped(ienumaFormat02);
						
			sLogValue = objLogStringTest.compute(objDummy, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//wiederholter (erster) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertEquals(iLogIndex1, iLogIndex2);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der identische Logeintrag auch 2x in der Zeile erscheinen. Wenn entsprechend uebergeben	
			//b) gleicher Logeintraege		
			objLogStringTest.setFormatPositionsMapped(ienumaFormat02);
			
			sLogValue = objLogStringTest.compute(objDummy, sLog1, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==2); //!! nun 2x, trotz zweitem STRINGTYPE, aber LINENEXT existiert nicht, bzw. ist nachfolgend
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//wiederholter (erster) Logeintrag ist vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog1);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2 > iLogIndex1 + sLog1.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (OHNE ARGNEXT) soll der Logeintrag auch 2x in der Zeile erscheinen.	
			//c) unterschiedliche Logeintraege			
			objLogStringTest.setFormatPositionsMapped(ienumaFormat02);
						
			sLogValue = objLogStringTest.compute(objDummy, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x,
			
			assertTrue(StringZZZ.contains(sLogValue, sLog2));
			assertTrue(StringZZZ.count(sLogValue, sLog2)==1); //!! nun 1x,
			
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag 
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2> iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
	
	public void testCompute_FormatDefined_Using_LINENEXT_log_hinten(){
		try{			
			String sLog1 = null; String sLog2 = null; String sLog3 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iLogIndex3 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag etwas laenger";
			sLog2 = "der zweite Logeintrag";
			sLog3 = "der dritte Logeintrag soll noch laenger sein, trotzdem alle buendig?";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormat03= {
					IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
					};
			
			
			//+++ Bei 2x Strintype soll der Logeintrag nur 1x erscheinen auch mit ARGNEXT.								
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
		
			sLogValue = objLogStringTest.compute(objDummy, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//b) unterschiedliche Logeintraege							
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
			
			//TODOGOON: MIT ARGNEXT soll der 2te Logeintrag auf eine andere Zeile Kommen
			sLogValue = objLogStringTest.compute(objDummy, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex3 = StringZZZ.indexOfLast(sLogValue, sLog3);
			assertTrue(iLogIndex3 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 3x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//c) ARRAY der Logeintraege			
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
						
			String[]saLog = new String[3];
			saLog[0]=sLog1;
			saLog[1]=sLog2;
			saLog[2]=sLog3;
			sLogValue = objLogStringTest.compute(objDummy, saLog);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x,
			
			assertTrue(StringZZZ.contains(sLogValue, sLog2));
			assertTrue(StringZZZ.count(sLogValue, sLog2)==1); //!! nun 1x,
			
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag 
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2> iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
	
	
	public void testCompute_FormatDefined_Using_LINENEXT_log_voranstehend(){
		try{			
			String sLog1 = null; String sLog2 = null; String sLog3 = null; String sThread = null; String sClassName = null;
			String sLogValue = null; 
			int iLogIndex1 = -1; int iLogIndex2 = -1; int iLogIndex3 = -1; int iThreadIndex = -1; int iClassNameIndex = -1;
			
			DummyTestObjectZZZ objDummy = new DummyTestObjectZZZ();
			sLog1 = "der erste Logeintrag etwas laenger";
			sLog2 = "der zweite Logeintrag";
			sLog3 = "der dritte Logeintrag soll noch laenger sein, trotzdem alle buendig?";
			sThread = "[Thread:";
			sClassName = objDummy.getClass().getSimpleName();
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormat03= {
					IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,						
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,	
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
					IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING,
					};
			
			
			//+++ Bei 2x Strintype soll der Logeintrag nur 1x erscheinen auch mit ARGNEXT.								
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
		
			sLogValue = objLogStringTest.compute(objDummy, sLog1);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1);
									
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfFirst(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfFirst(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfFirst(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 2x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//b) unterschiedliche Logeintraege							
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
			
			//TODOGOON: MIT ARGNEXT soll der 2te Logeintrag auf eine andere Zeile Kommen
			sLogValue = objLogStringTest.compute(objDummy, sLog1, sLog2);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x, trotz zweitem STRINGTYPE
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			
			//zweiter (anderer) Logeintrag ist nicht vorhanden
			iLogIndex3 = StringZZZ.indexOfLast(sLogValue, sLog3);
			assertTrue(iLogIndex3 == -1);
						
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ Bei 3x Strintype (MIT LINENEXT voneinandere trennen) soll jeder Logeintrag in einer anderen der Zeile erscheinen.	
			//c) ARRAY der Logeintraege			
			objLogStringTest.setFormatPositionsMapped(ienumaFormat03);
						
			String[]saLog = new String[3];
			saLog[0]=sLog1;
			saLog[1]=sLog2;
			saLog[2]=sLog3;
			sLogValue = objLogStringTest.compute(objDummy, saLog);
			System.out.println("In der nächsten Zeile erst geht der Logeintrag los...: "+ReflectCodeZZZ.getPositionCurrent()+"\n" + sLogValue);			
			
			assertTrue(StringZZZ.contains(sLogValue, sLog1));
			assertTrue(StringZZZ.count(sLogValue, sLog1)==1); //!! nun 1x,
			
			assertTrue(StringZZZ.contains(sLogValue, sLog2));
			assertTrue(StringZZZ.count(sLogValue, sLog2)==1); //!! nun 1x,
			
										
			assertTrue(StringZZZ.count(sLogValue,sThread)==1);//wg %s kann man nicht auf die Konstante selbst abprüfen, die ja nicht eretzt wurde
			
			assertTrue(StringZZZ.count(sLogValue,sClassName)==1); //Der Name sollte 1x vorkommen, mit einem Doppelpunkt dahinter.
			
			//Logeintrag soll hinter dem Classnamen stehen
			iLogIndex1 = StringZZZ.indexOfLast(sLogValue, sLog1);
			iThreadIndex = StringZZZ.indexOfLast(sLogValue, sThread);
			assertTrue(iThreadIndex > iLogIndex1+sLog1.length());
			
			iClassNameIndex = StringZZZ.indexOfLast(sLogValue, sClassName);
			assertTrue(iLogIndex1 > iClassNameIndex+sClassName.length());
			
			//zweiter Logeintrag 
			iLogIndex2 = StringZZZ.indexOfLast(sLogValue, sLog2);
			assertTrue(iLogIndex2 > -1);
			assertTrue(iLogIndex2> iThreadIndex + sThread.length());
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	} 
}//end class
