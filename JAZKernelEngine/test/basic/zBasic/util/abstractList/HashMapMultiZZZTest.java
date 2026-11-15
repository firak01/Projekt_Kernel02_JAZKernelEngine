package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;

public class HashMapMultiZZZTest extends TestCase{
    private HashMapMultiZZZ hmTest = null;
	
    protected void setUp(){
      
	//try {			
		//### Die TestObjecte		
		hmTest=new HashMapMultiZZZ();
	
	/*
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	*/
	
}//END setup
    
    
public void testValueSetGet(){
	try{
		hmTest.clear();
		hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
		
		//Nun den Wert wieder auslesen
		String stemp = (String)hmTest.get("Alias1", "Alias1a");
		assertEquals("Das ist ein Test", stemp);
		
	
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testDebugString(){
	try{
		hmTest.clear();
		hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
		hmTest.put("Alias1", "Alias1b", "2. Zeile");
		hmTest.put("Alias2", "Alias2a", "3. Zeile");
		hmTest.put("Alias2", "Alias2b", "4. Zeile");
		hmTest.put("Alias2", "Alias2c", "5. Zeile");
		
	
		String stemp = hmTest.computeDebugString();
		assertFalse(stemp==null);
		assertFalse(stemp.equals(""));
		
		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " Ausgabe des Debug-Strings - Merke: Unsortiert ist erwartet. ");
		System.out.println(stemp);
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetOuterKeySet(){
	try{
		hmTest.clear();
		hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
		hmTest.put("Alias1", "Alias1b", "2. Zeile");
		hmTest.put("Alias2", "Alias2a", "3. Zeile");
		hmTest.put("Alias2", "Alias2b", "4. Zeile");
		hmTest.put("Alias2", "Alias2c", "5. Zeile");
		
		Set<String> setKeyOuter = (Set<String>) hmTest.getOuterKeySet();
		assertTrue(setKeyOuter.size()==2);
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}
	
	public void testGetInnerKeySet(){
		try{
			hmTest.clear();
			hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
			hmTest.put("Alias1", "Alias1b", "2. Zeile");
			hmTest.put("Alias2", "Alias2a", "3. Zeile");
			hmTest.put("Alias2", "Alias2b", "4. Zeile");
			hmTest.put("Alias2", "Alias2c", "5. Zeile");
			
			
			Set<String> setKeyInner = (Set<String>) hmTest.getInnerKeySet("Alias2");
			assertTrue(setKeyInner.size()==3);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testToHashMap() {
		try{
			hmTest.clear();
			hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
			hmTest.put("Alias1", "Alias1b", "2. Zeile");
			hmTest.put("Alias2", "Alias2a", "3. Zeile");
			hmTest.put("Alias2", "Alias2b", "4. Zeile");
			hmTest.put("Alias2", "Alias2c", "5. Zeile");
			
			HashMap<String,String> hm01 = hmTest.toHashMapInnerKeyString(IHashMapMultiZZZ.FLAGZ.TO_HASHMAP_KEEPFIRST);
			assertTrue(hm01.size()==5);
			
			hmTest.clear();
			hmTest.put("Alias1", "Alias1a", "Das ist ein Test");
			hmTest.put("Alias1", "Alias1a", "2. Zeile");
			hmTest.put("Alias2", "Alias2a", "3. Zeile");
			hmTest.put("Alias2", "Alias2a", "4. Zeile");
			hmTest.put("Alias2", "Alias2a", "5. Zeile");
			
			HashMap<String,String> hm02 = hmTest.toHashMapInnerKeyString(IHashMapMultiZZZ.FLAGZ.TO_HASHMAP_KEEPFIRST);
			assertTrue(hm02.size()==2);
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
  
}//END Class

