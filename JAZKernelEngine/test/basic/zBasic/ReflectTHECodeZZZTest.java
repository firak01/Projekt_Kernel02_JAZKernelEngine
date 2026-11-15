package basic.zBasic;

import java.util.Calendar;
import java.util.GregorianCalendar;

import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.longs.LongZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import junit.framework.TestCase;

/**
 *  Merke: Das THE im Klassenanemn ist Absicht, weil nämlich die  Reflection - Klassen sellbst 
 *            aus dem  errechneten Stacktrace, etc. herausgerechnet werden.
 *            Darum weichen die Testklassen vom Schema ab.
 * 
 * @author Fritz Lindhauer, 08.08.2019, 23:15:52
 * 
 */
public class ReflectTHECodeZZZTest  extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testGetMethodCurrentName(){
		try{
			String sFunction = "testGetMethodCurrentName";
			String  stemp = ReflectCodeZZZ.getMethodCurrentName();
			assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sFunction));
			
			stemp = ReflectCodeZZZ.getMethodCurrentNameLined(0);
			assertNotNull("Fehler beim Ermitteln des aktuellen Methodennamens mit Zeilennummer (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Methodennamens: '" + stemp + "' wurde nicht erwartet (Zeilennummer).", stemp.startsWith(sFunction) && stemp.length() > sFunction.length());
			
			//######################################### 			
			String sClassMethod = ReflectCodeZZZ.getClassMethodString();
			assertTrue(sClassMethod.startsWith(sClassMethod));
			
			String sMethodCurrentLined1 = ReflectCodeZZZ.getMethodCurrentNameLined(1); //also fuer die Folgezeile (!!!) berechnen
			String sMethodCurrentLined = ReflectCodeZZZ.getMethodCurrentNameLined();
			assertTrue(sMethodCurrentLined.equals(sMethodCurrentLined1));
			
			sMethodCurrentLined1 = ReflectCodeZZZ.getMethodCurrentNameLined(1); //also fuer die Folgezeile (!!!) berechnen
			String sMethodCurrentLined0 = ReflectCodeZZZ.getMethodCurrentNameLined(0);
			assertTrue(sMethodCurrentLined0.equals(sMethodCurrentLined1));
			
			String sMethodCurrent = ReflectCodeZZZ.getMethodCurrentName();
			assertTrue("Fehler bei der Ermittlung der Methode", sMethodCurrentLined.startsWith(sMethodCurrent));
			
			String sMethodCurrent0 = ReflectCodeZZZ.getMethodCurrentName(0); //
			assertTrue(sMethodCurrent.equals(sMethodCurrent0));
			
			
			//Merke: Von dieser Testfunktion aus, ist keine sinnvolle "calling"-Methode aufrufbar.
			String sMethodCallingMinus1 = ReflectCodeZZZ.getMethodCallingName(0);			
			assertTrue(sMethodCallingMinus1.equals(sMethodCurrent0));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetClassCurrentName(){
		try{
			String sClass = this.getClass().getName();
			String  stemp = ReflectCodeZZZ.getClassCurrentName();
			assertNotNull("Fehler beim Ermitteln des aktuellen Klassennamens (NULL)", stemp);
			assertTrue("Fehler beim Ermitteln des aktuellen Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClass));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetClassCallingName(){
		try{
			String sClassCurrent = this.getClass().getName(); 
			String  stemp = ReflectCodeZZZ.getClassCallingName(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln des aufrufenden Klassennamens (NULL)", stemp);
			assertFalse("Fehler beim Ermitteln des aufrufenden Klassennamens: '" + stemp + "' wurde nicht erwartet.", stemp.equals(sClassCurrent));
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetMethodCurrentLine(){
		try{
			int iLineCurrent1 = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			int iLineCurrent0 = ReflectCodeZZZ.getMethodCurrentLine(0,0);
			assertTrue(iLineCurrent0==iLineCurrent1);
			
			//Merke: Von dieser Testfunktion aus, ist keine sinnvolle "calling"-Methode aufrufbar.
			iLineCurrent1 = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			int iLineCalling0 = ReflectCodeZZZ.getMethodCallingLine(0);
			assertTrue(iLineCalling0==iLineCurrent1);
			
			iLineCurrent1 = ReflectCodeZZZ.getMethodCurrentLine(0,1);
			int iLineCalling = ReflectCodeZZZ.getMethodCallingLine();
			assertTrue(iLineCalling==iLineCurrent1);
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetPositionCurrent(){
		try{
			
			//###########################
			//### Hole erst einmal Werte, die dann in dem Ergebnisstring zu finden sein müssen.
			//###########################
			String sClassCurrent = this.getClass().getName(); 						
			String sMethodCurrent = ReflectCodeZZZ.getMethodCurrentName();
			
			//##############
			//### Jetzt erst geht´s los
			//##############
			int iLineCurrentInPosition  = ReflectCodeZZZ.getMethodCurrentLine(0,1); //1 ist da offset fuer die Folgende Zeile
			String  sPositionCurrent = ReflectCodeZZZ.getPositionCurrent(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln der aktuellen CodePosition (NULL)", sPositionCurrent);		
			System.out.println("sPositionCurrent="+sPositionCurrent);
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: Der String '" + sPositionCurrent + "' enthaelt nicht die ausfuehrende Testmethode.", 
					sPositionCurrent.contains("testGetPositionCurrent")); 
				
			assertFalse("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Klassenname sollte jetzt ueber LogStringZZZ protokolliert werden).", 
					sPositionCurrent.startsWith(sClassCurrent) 
					&& sPositionCurrent.length() > sClassCurrent.length());
			
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Die Zeilennummer "+ iLineCurrentInPosition + "sollte enthalten sein.).",
					sPositionCurrent.contains(Integer.toString(iLineCurrentInPosition)));
			
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Der Methodenname sollte jetzt am Anfang stehen).",
					sPositionCurrent.startsWith(sMethodCurrent) 
					&& sPositionCurrent.length() > sMethodCurrent.length());
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetPositionCurrentXml(){
		try{
			//###########################
			//### Hole erst einmal Werte, die dann in dem Ergebnisstring zu finden sein müssen.
			//###########################
			String sClassCurrent = this.getClass().getName(); 						
			String sMethodCurrent = ReflectCodeZZZ.getMethodCurrentName();
			
			//##############
			//### Jetzt erst geht´s los
			//##############
			int iLineCurrentInPosition  = ReflectCodeZZZ.getMethodCurrentLine(0,1);		//1 ist das Offset fuer die Zeile, also die Kommende Zeile...	
			String  sPositionCurrent = ReflectCodeZZZ.getPositionCurrentXml(); //Das ist dann keine meiner Klassen, sondern von sun, etc.
			assertNotNull("Fehler beim Ermitteln der aktuellen CodePosition (NULL)", sPositionCurrent);		
			System.out.println("sPositionCurrent="+sPositionCurrent);
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: Der String '" + sPositionCurrent + "' enthaelt nicht die ausfuehrende Testmethode.", 
					sPositionCurrent.contains("testGetPositionCurrent")); 
				
			assertFalse("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Klassenname sollte jetzt ueber LogStringZZZ protokolliert werden).", 
					sPositionCurrent.startsWith(sClassCurrent) 
					&& sPositionCurrent.length() > sClassCurrent.length());
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Die Zeilennummer "+ iLineCurrentInPosition + "sollte enthalten sein.).",
					sPositionCurrent.contains(Integer.toString(iLineCurrentInPosition)));
			
		
			//Der Methodentag ist nun innnerhalb <postioncurrent> 
			ITagByTypeZZZ objTagMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.METHOD, sMethodCurrent);
			String sMethodTag = objTagMethod.getElementString();
			
			String sPosition = sMethodTag; //eigentlich noch mehr, aber fuer den Test soll es reichen.
			ITagByTypeZZZ objTagPosition = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sPosition);
			
			//Der Tag mit dem Thread steht voran
			long lngThreadId = Thread.currentThread().getId();     
			String sThreadId = LongZZZ.longToString(lngThreadId);
			ITagByTypeZZZ objTagThreadId = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID, sThreadId);
			String sThreadIdTag = objTagThreadId.getElementString();
			
		
	 		//+++++++++++++++++++++++++
			String sPositionTagSearched = StringZZZ.mid(sPositionCurrent, objTagPosition.getTagPartOpening(),objTagPosition.getTagPartClosing());
			assertNotNull(sPositionTagSearched);
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Der Method-Tag sollte enthalten sein).",
					sPositionCurrent.contains(sMethodTag) 
					&& sPositionCurrent.length() > sMethodCurrent.length());
			
			assertTrue("Fehler beim Ermitteln der aktuellen CodePosition: '" + sPositionCurrent + "' wurde nicht erwartet (Der ThreadId-Tag sollte jetzt am Anfang stehen).",
					StringZZZ.startsWith(sPositionCurrent, sThreadIdTag)
					&& sPositionCurrent.length() > sThreadIdTag.length());
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetCallingStack(){
		try{
			//Ermittle aus dem Stacktrace den Klassennamen.Methodennamen. 
			//Dies kann in diversen Möglichkeiten als Schlüssel für eine HashMap verwendet werden.
			String[]saCalling = ReflectCodeKernelZZZ.getCallingStack();
			
			//Weil nur die aufrufende  Test-Klasse darin auftauchen soll gilt:
			assertTrue("Es sollte nur die aufrufende Testklasse im Ergebnis sein.", saCalling.length==1);
			String sCalling = saCalling[0];
			String scallingMethod = ReflectCodeZZZ.getClassCurrentName() + "." + ReflectCodeZZZ.getMethodCurrentName();
			assertTrue("Es sollte die aufrufende Testklasse im Ergebnis sein", sCalling.equals(scallingMethod));
			
			String[]saCalling02 = ReflectCodeZZZ.getCallingStack();
			assertTrue("Es sollte mehr als nur die aufrufende Testklasse im Ergebnis sein.", saCalling02.length>saCalling.length);
	} catch (ExceptionZZZ ez) {
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}
	
	
}//END Class