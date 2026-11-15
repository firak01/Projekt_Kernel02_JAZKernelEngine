package basic.zBasic;

import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import junit.framework.TestCase;

public class ReflectCodeZZZTest   extends TestCase{
	
	

	protected void setUp(){
//		try {			
//			
//					
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für <linenr> - Tag nicht mehr.
	 *           Darum steht der Test ganz oben...
	 * 
	 */
	public void testGetPositionCurrentXml() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentXml();
			int iLineNr = 26;
			System.out.println("sValue=" + sValue);
			String sValueContainsExpected = "<positioncurrent><classname>basic.zBasic.ReflectCodeZZZTest</classname><method>testGetPositionCurrentXml</method><filename>ReflectCodeZZZTest.java</filename><linenr>"+ iLineNr + "</linenr><fileposition> (ReflectCodeZZZTest.java:"+ iLineNr + ") </fileposition></positioncurrent>";
			System.out.println("sValueContainsExpected=...\t\t" + sValueContainsExpected);			
			boolean bValue = StringZZZ.contains(sValue, sValueContainsExpected);
			assertTrue(bValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für nicht mehr.
	 * 
	 */
	public void testGetPositionCurrent() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrent();			
			int iLineNr=44;			
			System.out.println("ReflectCodeZZZ.testGetPositionCurrent(): Wert in der naechsten Zeile...");
			System.out.println("sValue=\t\t"+sValue);
			//String sValueExpected = "testGetPositionCurrent @ReflectCodeZZZTest.java:"+ iLineNr + " ~ (ReflectCodeZZZTest.java:"+ iLineNr + ") [A00/]# ";
			//Kein Kommentartrenner am Schluss, da kein Kommentar vorhanden.
			String sValueExpected = "testGetPositionCurrent[A00/] ~(ReflectCodeZZZTest.java:"+ iLineNr + ")";
			System.out.println("sValueExpected=\t"+sValueExpected);
			assertEquals(sValueExpected, sValue);
			
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testGetPositionCurrentSimple() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentSimple();
			int iLineNr=60; 
			System.out.println("sValue=\t\t"+sValue);
			
			String sValueStartsWithExpected="basic.zBasic.ReflectCodeZZZTest.testGetPositionCurrentSimple - Line "+ iLineNr;
			System.out.println("sValueStartsWithExpected=...\t"+sValue);
			boolean bStartsWith = StringZZZ.startsWith(sValue, sValueStartsWithExpected);
			assertTrue(bStartsWith);
						
			boolean bEndsWith = StringZZZ.endsWith(sValue, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR);
			assertTrue(bEndsWith);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	/** Bedenke: Sobald sich die Zeilennummer in diesem Code aendert stimmt der Wert für <linenr> - Tag nicht mehr.
	 *           Darum steht der Test ganz oben...
	 * 
	 */
	public void testGetPositionCurrentXmlFormated() {
		try {
			String sValue = ReflectCodeZZZ.getPositionCurrentXmlFormated();
			int iLineNr = 84;
			//z.B.: <date>2025-12-7_12_34</date><threadid>1</threadid><positioncurrent><classname>basic.zBasic.ReflectCodeZZZTest</classname><method>testGetPositionCurrentXmlFormated</method><linenr>71</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:71) </fileposition></positioncurrent>
			//Da sich das Datum ja immer aendert nicht auf gleich abpruefen (sonst muss ich das Datum extra berechnen)
			//assertEquals("<date>2025-12-7_12_34</date><threadid>1</threadid><positioncurrent><classname>basic.zBasic.ReflectCodeZZZTest</classname><method>testGetPositionCurrentXmlFormated</method><linenr>71</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:71) </fileposition></positioncurrent>", sValue);
			System.out.println("sValue=\t" + sValue);							
			
			String sValueEndsWithExpected = "</date><threadid>1</threadid><positioncurrent><classname>basic.zBasic.ReflectCodeZZZTest</classname><method>testGetPositionCurrentXmlFormated</method><linenr>"+iLineNr+"</linenr><filename>ReflectCodeZZZTest.java</filename><fileposition> (ReflectCodeZZZTest.java:"+iLineNr+") </fileposition></positioncurrent>";
			System.out.println("sValueEndsWthExpected=...\t" + sValueEndsWithExpected);
			boolean bEndsWith = StringZZZ.endsWith(sValue, sValueEndsWithExpected);
			assertTrue(bEndsWith);
			
			String sValueStartsWithExpected = "<date>";
			boolean bStartsWith = StringZZZ.startsWith(sValue, sValueStartsWithExpected);
			assertTrue(bStartsWith);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	//######################################################################################################
	public void testGetPositionCalling() {
		try {
			//Weil es calling Tests sind, eine Untermethode aufrufen, von der aus "gerechnet wird".
			String sValue = testGetPositionCallingSimple_();			
			int iLineNr = 111;
			System.out.println("sValue=\t" + sValue);							
			
			String sValueExpected = "basic.zBasic.ReflectCodeZZZTest.testGetPositionCalling - Line " + iLineNr + "# ";
			System.out.println("sValueExpected=\t" + sValueExpected);
			assertEquals(sValueExpected, sValue); 			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	private String testGetPositionCallingSimple_() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = ReflectCodeZZZ.getPositionCallingSimple();
		}//end main:
		return sReturn;
	}
	
	
	//#####################################################################################################
	
	public void testGetMethodCurrentNameLined() {		
		try {
			//Weil es calling Tests sind, eine Untermethode aufrufen, von der aus "gerechnet wird".
			String sValue = ReflectCodeZZZ.getMethodCurrentNameLined();			
			int iLineNr = 138;
			System.out.println("sValue=\t\t"+sValue);
			
			String sValueStartsWithExpected="testGetMethodCurrentNameLined - Line " + iLineNr;
			System.out.println("sValueStartsWithExpected=...\t"+sValue);
			boolean bStartsWith = StringZZZ.startsWith(sValue, sValueStartsWithExpected);
			assertTrue(bStartsWith);
			
			boolean bEndsWith = StringZZZ.endsWith(sValue, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR);
			assertTrue(bEndsWith);
						
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
}//end class