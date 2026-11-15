package basic.zBasic.util.abstractList;

import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public class HashMapZZZTest extends TestCase{
	 private HashMapZZZ hmTest = null;
	 private HashMapZZZ<String, DummyTestObjecWithDefaultValuesZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		try {			
			//### Die TestObjecte, normal	
			hmTest=new HashMapZZZ();
			hmTest.put("Alias1","Das ist ein Test");
			hmTest.put("Alias2", "Das ist der Gleichheits  Fall");
			
			//### Das spezielle Generics Testobjekt
			DummyTestObjecWithDefaultValuesZZZ objTest01 = new DummyTestObjecWithDefaultValuesZZZ();
			objTest01.setFlag("init", true);
			
			DummyTestObjecWithDefaultValuesZZZ objTest02 = new DummyTestObjecWithDefaultValuesZZZ();
			
			hmTestGenerics = new HashMapZZZ<String, DummyTestObjecWithDefaultValuesZZZ>();
			hmTestGenerics.put("test01", objTest01);
			hmTestGenerics.put("test02", objTest02);
			
		
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		
		
	}//END setup
	    
	    public void testIsBiggerSmallerThan(){
			try{
				boolean btemp;
			
				//+++ PR�FOBJEKT 1)
				//Gleichheitsfall
				HashMapZZZ hmTest2 = new HashMapZZZ();
				hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
				btemp = hmTest.isBiggerThan(hmTest2);
				assertTrue("Testobjekt aus dem Setup sollte gr��er sein als Vergleichsobjekt.", btemp);

				
				//Nun Teilmengen vergleichen: Fall a
				hmTest2.put("Alias1", "Das ist ein Test Fall a");
				btemp = hmTest.isBiggerThan(hmTest2);
				assertFalse("Die beiden Objekte sollten von der Gr��e gleich sein, also nicht gr��er.", btemp);
				
				btemp = hmTest.isSameSize(hmTest2);
				assertTrue("Die beiden Objekte sollten von der Gr��e gleich sein.", btemp);
			
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
	    
	public void testIsSubsetFrom(){
		try{
			boolean btemp;
		
			//+++ PR�FOBJEKT 1)
			//Gleichheitsfall
			HashMapZZZ hmTest2 = new HashMapZZZ();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1","Das ist ein Test");  //Geliche Werte aber andere Reihenfolge
			btemp = hmTest.isSubsetFrom(hmTest2);
			assertTrue("Die Alias - Wertkombination ist gleich, true erwartet", btemp);

			
			//Nun Teilmengen vergleichen: Fall a
			hmTest2.put("Alias1", "Das ist ein Test Fall aaaa"); //Gleicher Key, aber andere Wert
			btemp = hmTest.isSubsetFrom(hmTest2);
			assertFalse("Die alias Wertkombination ist nicht gleich, false erwartet", btemp);

			//+++ PR�FOBJEKT 2) (neues Pr�fobjekt ist notwendig, wenn die Menge nicht mehr Teilmenge sein kann)
			hmTest2.clear();
			
			//Nun Teilmengen vergleichen: Fall b
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1b", "Das ist ein Test b");
			btemp = hmTest.isSubsetFrom(hmTest2);
			assertFalse("Die Key Aliasse sind nicht gleich, false erwartet", btemp);
			
			//assertEquals("Das ist ein Test", stemp);
			
		
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testIsSubsetFromByMethod(){
		try{
			boolean btemp;
		
			//+++ PRUEFOBJEKT 1)
			//Gleichheitsfall
			HashMapZZZ hmTest2 = new HashMapZZZ();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1","Das ist ein Test");  //Geliche Werte aber andere Reihenfolge
			btemp = hmTest.isSubsetFromByMethod(hmTest2, "toString");
			assertTrue("Die Alias - Wertkombination ist gleich, true erwartet", btemp);

			
			//Nun Teilmengen vergleichen: Fall a
			hmTest2.put("Alias1", "Das ist ein Test Fall aaaa"); //Gleicher Key, aber andere Wert
						
			btemp = hmTest.isSubsetFromByMethod(hmTest2, "toString");
			assertFalse("Die alias Wertkombination ist nicht gleich, false erwartet", btemp);

			//+++ PRUEFOBJEKT 2) (neues Pr�fobjekt ist notwendig, wenn die Menge nicht mehr Teilmenge sein kann)
			hmTest2.clear();
			
			//Nun Teilmengen vergleichen: Fall b
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1b", "Das ist ein Test b");
			btemp = hmTest.isSubsetFromByMethod(hmTest2, "toString");
			assertFalse("Die Key Aliasse sind nicht gleich, false erwartet", btemp);
			
			//assertEquals("Das ist ein Test", stemp);
			
		
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	
	public void testIsEqualToByMethod(){
		try{
			boolean btemp;
		
			//+++ PRUEFOBJEKT 1)
			//Gleichheitsfall
			HashMapZZZ hmTest2 = new HashMapZZZ();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1","Das ist ein Test");  //Geliche Werte aber andere Reihenfolge
			btemp = hmTest.isEqualToByMethod(hmTest2, "toString");
			assertTrue("Die Alias - Wertkombination ist gleich, true erwartet", btemp);

			
			//+++ PRUEFOBJEKT 2) (neues Pr�fobjekt ist notwendig, wenn die Menge nicht mehr Teilmenge sein kann
			//Nun Teilmengen vergleichen: Fall Key ungleich
			hmTest2.clear();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1b", "Das ist ein Test");
			btemp = hmTest.isEqualToByMethod(hmTest2, "toString");
			assertFalse("Die Key Aliasse sind ungleich: false erwartet", btemp);
			
			
			
			
			//Nun Teilmengen vergleichen: Fall Werte ungleich
			hmTest2.clear();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1", "Das ist ein Test b");
			btemp = hmTest.isEqualToByMethod(hmTest2, "toString");
			assertFalse("Die Key Aliasse sind gleich, die Werte nicht: false erwartet", btemp);
			
			//assertEquals("Das ist ein Test", stemp);
			
		
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testDebugString(){
		try{
			hmTest.clear();
			hmTest.put( "Alias1a", "Das ist ein Test");
			hmTest.put( "Alias1b", "2. Zeile");
			hmTest.put( "Alias2a", "3. Zeile");
			hmTest.put( "Alias2b", "4. Zeile");
			
		
			String stemp = hmTest.computeDebugString();
			assertFalse(stemp==null);
			assertFalse(stemp.equals(""));
			
			System.out.println(ReflectCodeZZZ.getPositionCurrent() + " Ausgabe des Debug-Strings. Merke: Unsortiert ist erwartet: ");
			System.out.println(stemp);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		} 
	}
	
	public void testRemoveByIndex(){
		try{
			hmTest.clear();
			hmTest.put( "Alias1a", "Das ist ein Test");
			hmTest.put( "Alias1b", "2. Zeile");
			hmTest.put( "Alias2a", "3. Zeile");
			hmTest.put( "Alias2b", "4. Zeile");
			
			int iSizePre = hmTest.size();
			assertEquals(4, iSizePre);
			
			/* da die Indexposition in der Hashmap nict feststeht, kann man nicht so einfach vorgehen
			String sValuePre = (String) hmTest.get("Alias1b");
			assertEquals("2. Zeile", sValuePre); */
			//Hole den Wert an der Indexposition
			String sKeyPre = (String) hmTest.getKeyByIndex(1);
			String sValuePre = (String) hmTest.getValueByIndex(1);
			assertEquals(sValuePre, hmTest.get(sKeyPre));
			
			//Hole den Wert an der Indexposition + 1
			String sKeyPrePlus = (String) hmTest.getKeyByIndex(2);
			String sValuePrePlus = (String) hmTest.getValueByIndex(2);
			assertEquals(sValuePrePlus, hmTest.get(sKeyPrePlus));
			
			
			//+++++++++++++++++++++++++++
			hmTest.removeByIndex(1);
						
			//+++++++++++++++++++++++++++
			int iSizePost = hmTest.size();
			assertEquals(iSizePre-1, iSizePost);
			
			/* da die Indexposition in der HashMap nicht feststeht, kann man nicht so einfach vorgehen
			String sValuePost = (String) hmTest.get("Alias1b");  //Merke: Man kann nicht nach der oben angegebenen Reihenfolge vorgehen, da die Indexposition beleibig ist.
			assertNull("Nach dem Entfernen sollte fuer diesen Key kein Wert mehr vorhanden sein", sValuePost);*/
			String sKeyPost = (String) hmTest.getKeyByIndex(1);
			String sValuePost = (String) hmTest.getValueByIndex(1);
			assertNotSame("Nach dem Entfernen sollte f�r diesen Key an der Position ein anderer Wert sein", sValuePre, sValuePost);
			assertEquals(sValuePost, hmTest.get(sKeyPost));
			
			//Dies sollte dann dem vorherigen Wert an der +1 Indexposition entsprechen
			assertEquals(sKeyPrePlus, sKeyPost);
			assertEquals(sValuePrePlus, sValuePost);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		} 
	}
	
	public void testGenerics(){
		try{
			DummyTestObjecWithDefaultValuesZZZ objTestCompare = new DummyTestObjecWithDefaultValuesZZZ();
			objTestCompare.setFlag("init", true);
			
			boolean bAnyFound = false;
			Set<String> setKey = hmTestGenerics.keySet();
			for(String objKey : setKey){
				DummyTestObjecWithDefaultValuesZZZ objTemp = (DummyTestObjecWithDefaultValuesZZZ)hmTestGenerics.get(objKey);
				bAnyFound = objTemp.getFlag("init") == objTestCompare.getFlag("init");
				if(bAnyFound) break;
			}
			assertTrue("No flag 'init' change found. There should be a flag set to 'true'", bAnyFound);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		} 
		}

	public void testNoSuchMethodExceptionHandling(){
		try{
			boolean btemp;
			
			//!! dazu darf man nicht mit generics arbeiten.
			HashMapZZZ hmTest2 = new HashMapZZZ();
			hmTest2.put("Alias2", "Das ist der Gleichheits  Fall");
			hmTest2.put("Alias1","Das ist ein Test");  //Geliche Werte aber andere Reihenfolge
			
			DummyTestObjecWithDefaultValuesZZZ objTestCompare = new DummyTestObjecWithDefaultValuesZZZ();
			objTestCompare.setFlag("init", true);
			hmTest.clear();
			hmTest.put("Alias1", objTestCompare);
			
			btemp = hmTest.isSubsetFromByMethod(hmTest2, "gibtsNurImDummy");
			assertFalse("Die Methode 'gibtsNurImDummy' sollte einen abgefangenen Fehler provozieren. Es sollte nur false zur�ckgegeben werden.", btemp);
			
			btemp = false;
			boolean bIsErrorZZZ = false;
			try{
			btemp = hmTest.isSubsetFromByMethod(hmTest2, "gibtsUeberhauptNicht");
			}catch(ExceptionZZZ ez){
				bIsErrorZZZ = true;
			}
			assertTrue("Die Methode 'gibtsUeberhauptNicht' sollte einen Fehler provozieren.", bIsErrorZZZ);
			assertFalse(btemp);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		} 
	}
	  
}
