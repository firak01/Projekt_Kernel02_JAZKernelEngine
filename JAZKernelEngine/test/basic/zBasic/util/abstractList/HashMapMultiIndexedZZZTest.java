package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public class HashMapMultiIndexedZZZTest extends TestCase{
    private HashMapMultiIndexedZZZ hmTest = null;
	
    protected void setUp(){
      
	//try {			
		//### Die TestObjecte		
		hmTest=new HashMapMultiIndexedZZZ();
	
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
    
public void testValueSetGet(){
	try{
		hmTest.clear();
		
		//Index prüfen
		int iIndexLast = hmTest.getIndexLast();
		assertEquals(-1,iIndexLast);
		int iIndexHigh = hmTest.getIndexHigh();
		assertEquals(-1,iIndexHigh);
		
		Integer intTest = new Integer(0);
		hmTest.put(intTest, "Alias1a", "Das ist ein Test");
		
		//Index prüfen
		iIndexLast = hmTest.getIndexLast();
		assertEquals(0,iIndexLast);
		iIndexHigh = hmTest.getIndexHigh();
		assertEquals(0,iIndexHigh);
		
				
		//Nun den Wert wieder auslesen
		String stemp = (String)hmTest.getEntryByAlias(intTest, "Alias1a");
		assertEquals("Das ist ein Test", stemp);
		
		//Neue Werte hinzunehmen
		hmTest.put("Alias1b", "2. Zeile");
		hmTest.put( "Alias2a", "3. Zeile");
		hmTest.put("Alias2b", "4. Zeile");
		hmTest.put("Alias2c", "5. Zeile");
		
		//Index prüfen
		iIndexLast = hmTest.getIndexLast();
		assertEquals(4,iIndexLast);
		iIndexHigh = hmTest.getIndexHigh();
		assertEquals(4,iIndexHigh);
		
		//Nun den Wert wieder auslesen
		intTest = new Integer(iIndexHigh);
		stemp = (String)hmTest.getEntryByAlias(intTest, "Alias2c");
		assertEquals("5. Zeile", stemp);
	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testDebugString(){
	try{
		hmTest.clear();
		hmTest.put( "Alias1a", "Das ist ein Test");
		hmTest.put("Alias1b", "2. Zeile");
		hmTest.put( "Alias2a", "3. Zeile");
		hmTest.put("Alias2b", "4. Zeile");
		hmTest.put("Alias2c", "5. Zeile");
		
	
		String stemp = hmTest.computeDebugString();
		assertFalse(stemp==null);
		assertFalse(stemp.equals(""));
		
		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " Ausgabe des Debug-Strings. Merke: Sortiert nach 1. Spalte ist erwartet: ");
		System.out.println(stemp);
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetOuterKeySet(){
	try{
		hmTest.clear();
		hmTest.put( "Alias1a", "Das ist ein Test");
		hmTest.put( "Alias1b", "2. Zeile");
		hmTest.put("Alias2a", "3. Zeile");
		hmTest.put( "Alias2b", "4. Zeile");
		hmTest.put( "Alias2c", "5. Zeile");
		
		Set<String> setKeyOuter = (Set<String>) hmTest.getOuterKeySet();
		assertTrue(setKeyOuter.size()==5);//Für jeden Eintrag wird ein neuer Äusserer Key mit dem Integer Wert gemacht.
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}
	
	public void testGetInnerKeySet(){
		try{
			hmTest.clear();
			hmTest.put("Alias1a", "Das ist ein Test");
			hmTest.put("Alias1b", "2. Zeile");
			hmTest.put("Alias2a", "3. Zeile");
			hmTest.put("Alias2b", "4. Zeile");
			hmTest.put("Alias2c", "5. Zeile");
			
			
			Integer intTest = new Integer(0);
			Set<String> setKeyInner = (Set<String>) hmTest.getInnerKeySet(intTest);
			assertTrue(setKeyInner.size()==1);
			
			//Index prüfen
			int iIndexLast = hmTest.getIndexLast();
			assertEquals(4,iIndexLast);
			int iIndexHigh = hmTest.getIndexHigh();
			assertEquals(4,iIndexHigh);
			
			Integer intTest02 = new Integer(iIndexLast);
			Set<String> setKeyInner02 = (Set<String>) hmTest.getInnerKeySet(intTest02);
			assertTrue(setKeyInner02.size()==1);
			
			
			
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testGet_high_last(){
		try{
			hmTest.clear();
			hmTest.put("Alias1a", "Das ist ein Test");
			hmTest.put("Alias1b", "2. Zeile");
			hmTest.put("Alias2a", "3. Zeile");
			hmTest.put("Alias2b", "4. Zeile");
			hmTest.put("Alias2c", "5. Zeile");

			
			HashMap<String,String> hmLastInner = hmTest.getLast();
			assertNotNull(hmLastInner);

			HashMap<String,String> hmHighInner = hmTest.getHigh();
			assertNotNull(hmHighInner);
			
			//#################################################

			
			String s = (String) hmTest.getEntryHigh("nicht da");
			assertNull("NULL erwartet. Wert ist aber '" + s + "'", s);
			
			String s2 = (String) hmTest.getEntry("nicht da");
			assertNull("NULL erwartet. Wert ist aber '" + s2 + "'", s2);
			
			//#######################
			String sHigh = (String) hmTest.getEntryHigh("Alias2c");
			assertEquals(sHigh,"5. Zeile");
			
			String sLast = (String) hmTest.getEntryHigh("Alias2c");
			assertEquals(sLast,sHigh); //Sollte identisch sein, wenn man noch auf keinem anderen Index gearbeitet hat.
			
			//Nun Hinzufügen
			hmTest.put("Alias3a","6. Zeile");
			
			sHigh = (String) hmTest.getEntryHigh("Alias3a");
			assertEquals(sHigh,"6. Zeile");
			
			sLast = (String) hmTest.getEntryHigh("Alias3a");
			assertEquals(sLast,sHigh); //Sollte identisch sein, wenn man noch auf keinem anderen Index gearbeitet hat.
			
			//#################################################
			
			String sHighKey = (String) hmTest.getKeyHigh();
			assertEquals("Alias3a", sHighKey);
			String sHighValue = (String) hmTest.getEntryHigh();
			assertEquals(sHigh, sHighValue);
			//##################################################
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testAdd(){
		try{
			hmTest.clear();
			hmTest.put("Alias1a", "Das ist ein Test");
			hmTest.put("Alias1b", "2. Zeile");
			hmTest.put("Alias2a", "3. Zeile");
			hmTest.put("Alias2b", "4. Zeile");
			hmTest.put("Alias2c", "5. Zeile");

			int iHigh = hmTest.getIndexHigh();
			
			//#####################################################
			//Nun eine weitere Hashmap erstellen
			HashMapMultiIndexedZZZ hmToAdd = new HashMapMultiIndexedZZZ();
			hmToAdd.put("NeuerAlias1a", "6. Zeile");
		    hmToAdd.put("NeuerAlias1b", "7. Zeile");
			
			hmTest.add(hmToAdd);
			
			int iHighNew = hmTest.getIndexHigh();
			assertTrue(iHigh+2==iHighNew);
			
			String sValue = (String) hmTest.getEntryByAlias("NeuerAlias1a");
			assertNull("NULL erwartet. Wert ist aber '" + sValue + "'", sValue); //WEIL kein Index fuer die aeussere Schleife angegeben wurde UND dann im Letzten Eintrag geguckt wird. Da ist der Schlüsselwert halt nicht.
			
			Integer intIndex = new Integer(iHigh+1);
			sValue = (String) hmTest.getEntryByAlias(intIndex, "NeuerAlias1a");
			assertEquals("6. Zeile", sValue);
			
			String sValueLastNew = (String) hmTest.getEntryLast();
			assertEquals("7. Zeile", sValueLastNew);
			
			String sKeyLastNew = (String) hmTest.getKeyLast();
			assertEquals("NeuerAlias1b", sKeyLastNew);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
  
}//END Class

