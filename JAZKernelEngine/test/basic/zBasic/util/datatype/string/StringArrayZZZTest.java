/**
 * 
 */
package basic.zBasic.util.datatype.string;


import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.json.JsonArrayZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;

/**
 * @author 0823
 *
 */
public class StringArrayZZZTest extends TestCase implements IConstantZZZ {
//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufr�umen um tearDown().
	
//	Objekt, das getestet werden soll
	private StringArrayZZZ objArrayTest;
	private StringArrayZZZ objArraySorted;
	private StringArrayZZZ objArrayEmpty;

	protected void setUp(){
		try{
			String[] satest = {"eins", "zwei", "drei"};
			objArrayTest = new StringArrayZZZ(satest);
			
			
			String[] saSorted ={"B wert", "D wert", "F wert"};
			objArraySorted = new StringArrayZZZ(saSorted);

			objArrayEmpty = new StringArrayZZZ();
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		    			
	}//END setup
	 
	public void testPlusString(){
		try{
			//Die statische Methode testen.
			String[] sa1 = {"eins", "zwei", "drei"};
			
			//a) ohne Werte
			String[]sanew1 = StringArrayZZZ.plusString(sa1, "", "BEHIND");
			assertTrue(sanew1.length==sa1.length);
			assertTrue(sanew1[2].equals(sa1[2]));
			
			
			//b) mit Werten
			 String stemp="angeh�ngt";			 
			 String[] sanew = StringArrayZZZ.plusString(sa1, stemp, "BEHIND");
			 assertTrue(sanew.length==sa1.length);
			 assertTrue(sanew[2].equals("dreiangeh�ngt"));
			 
			 
			 String[] sanew2 =  StringArrayZZZ.plusString(sa1, stemp, "BEFORE");
			 assertTrue(sanew2.length==sa1.length);
			 assertTrue(sanew2[2].equals("angeh�ngtdrei"));
			 
			 
			 //Die Methode testen, die das interne Array ver�ndert
			 objArrayTest.plusString(stemp, "BEHIND");
			 String[] sanew3 = objArrayTest.getArray();
			 assertTrue(sanew3[2].equals("dreiangeh�ngt"));
			 
			 
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	public void testPlusStringArray(){
		try{
	//		Die statische Methode testen.
			String[] sa1 = {"eins", "zwei", "drei"};
			
			//a) ohne Werte
			String[]sanew1 = StringArrayZZZ.plusStringArray(sa1, null, "BEHIND");
			assertTrue(sanew1.length==sa1.length);
			assertTrue(sanew1[2].equals(sa1[2]));
			
			
			//b) mit Werten
			String[] sa2 = {"A","B","C","D"};		
			 String[] sanew = StringArrayZZZ.plusStringArray(sa1, sa2, "BEHIND");
			 assertTrue(sanew.length==sa2.length);  //sa2 hat mehr Werte !!!
			 assertTrue(sanew[2].equals("dreiC"));
			 assertTrue(sanew[3].equals("D"));
			 
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testInsertSorted(){
		try{
			//1. Die statische Methode testen.
			String[] saSorted ={"B wert", "D wert", "F wert"};
			
			//1a. mittenrein
			String[] saNew1a = StringArrayZZZ.insertSorted(saSorted, "C wert", "IgnoreCase");
			assertFalse(saNew1a.length==saSorted.length);
			assertTrue(saNew1a.length==saSorted.length+1);
			
			//1b. ans Ende
			String[] saNew1b = StringArrayZZZ.insertSorted(saSorted, "G wert", "IgnoreCase");
			assertFalse(saNew1b.length==saSorted.length);
			assertTrue(saNew1b.length==saSorted.length+1);
			
			//1c an den Anfang
			String[] saNew1c = StringArrayZZZ.insertSorted(saSorted, "A wert", "IgnoreCase");
			assertFalse(saNew1c.length==saSorted.length);
			assertTrue(saNew1c.length==saSorted.length+1);
			
			//2. die normale Methode testen
			//2a. leeres objArrayEmpty
			objArrayEmpty.insertSorted("C wert", "IgnoreCase");
			String[] saNew2a = objArrayEmpty.getArray();
			assertTrue(saNew2a.length==1);
			assertFalse(saNew2a.length==saSorted.length);
			
			//2b. mittenrein in initialisiertes objArraySorted
			objArraySorted = new StringArrayZZZ(saSorted);			
			objArraySorted.insertSorted("C wert", "IgnoreCase");
			String[] saNew2b = objArraySorted.getArray();
			assertFalse(saNew2b.length==saSorted.length);
			assertTrue(saNew2b.length==saSorted.length+1);

			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testSplitByKeyToMap() {
		try {
			String[] sa01 = {"a","b","#","c","d","e","f","g","*","h","i","j","~","x","y"}; 
			String[] saKey = {"#","~","!","*"};
			
			Map<String, String[]> hmValue = StringArrayZZZ.splitByKeysToMap(sa01, saKey);
			assertNotNull(hmValue);
			assertEquals(4,hmValue.size());
			
			
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testToJsonByStringBuilder(){
		try{
			//1. Die statische Methode testen.
			String[] saValue ={"B wert", "D wert", "F wert"};
			
			String sErg = StringArrayZZZ.toJsonByStringBuilder(saValue);
			assertNotNull(sErg);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testToJson() {
		try{
			String sErg; String stemp;
			
			//1. Die statische Methode testen.
			String[] saValue ={"B wert", "D wert", "F wert"};
			
			sErg = StringArrayZZZ.toJson(saValue);
			assertNotNull(sErg);
			
			String sValue = "[[\"110917       \", 3.0099999999999998, -0.72999999999999998, 2.8500000000000001, 2.96, 685.0, 38603.0], [\"110917    \", 2.71, 0.20999999999999999, 2.8199999999999998, 2.8999999999999999, 2987.0, 33762.0]]";
			JsonArrayZZZ objaJson = StringZZZ.toJsonArray(sValue);
			JsonElement objJsonElement = objaJson.get(0);
			stemp = objJsonElement.toString();
			System.out.println(stemp);
									
			JsonArrayZZZ objArrayInner = StringZZZ.toJsonArray(stemp);			
			Iterator<?> it = objArrayInner.iterator();			
			while(it.hasNext()) {			
				JsonElement element = (JsonElement) it.next();
				stemp = element.getAsString();
				System.out.println(stemp);								
			}
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
}
