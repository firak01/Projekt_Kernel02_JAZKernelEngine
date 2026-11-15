package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.DummyTestObjecWithDefaultValuesZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public class HashMapIndexedObjectZZZTest extends TestCase{
	 private HashMapIndexedObjectZZZ<Integer,Object> hmTest = null;	 
	 
	    protected void setUp(){
	      
		try {			
			//### Die TestObjekte, normal	
			this.hmTest=new HashMapIndexedObjectZZZ<Integer,Object>();
			
			//### Das spezielle Generics Testobjekt
			DummyTestObjecWithDefaultValuesZZZ objTest01 = new DummyTestObjecWithDefaultValuesZZZ();
			objTest01.setFlag("init", true);
			objTest01.setValue("1");
			this.hmTest.put(objTest01);
			
			DummyTestObjecWithDefaultValuesZZZ objTest02 = new DummyTestObjecWithDefaultValuesZZZ();
			objTest02.setFlag("init", true);
			objTest02.setValue("2");
			this.hmTest.put(objTest02);
			
			DummyTestObjecWithDefaultValuesZZZ objTest03 = new DummyTestObjecWithDefaultValuesZZZ();
			objTest03.setFlag("init", true);
			objTest03.setValue("3");
			this.hmTest.put(objTest03);
		
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		
		
	}//END setup
	    
	    public void testGetElement() {
	    	try{
				DummyTestObjecWithDefaultValuesZZZ objTest01 = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueFirst();
				assertNotNull(objTest01);
				assertTrue(objTest01.getValue().equals("1"));
				
				DummyTestObjecWithDefaultValuesZZZ objTest03 = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueLast();
				assertNotNull(objTest03);
				assertTrue(objTest03.getValue().equals("3"));
				
				//#############################
				DummyTestObjecWithDefaultValuesZZZ objTestNULL = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueNext();
				assertNull(objTestNULL);
				//#############################
				
				Integer intKey = new Integer(1);//Merke: Index beginnt mit 0, also ist das der zweite Wert.
				DummyTestObjecWithDefaultValuesZZZ objTest02 = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValue(intKey);
				assertNotNull(objTest02);
				assertTrue(objTest02.getValue().equals("2"));
				
				DummyTestObjecWithDefaultValuesZZZ objTestX = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueNext();
				assertNotNull(objTestX);
				assertTrue(objTestX.equals(objTest03));
				
				DummyTestObjecWithDefaultValuesZZZ objTestY = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueNext();
				assertNull(objTestY);
				
				DummyTestObjecWithDefaultValuesZZZ objTestZ = (DummyTestObjecWithDefaultValuesZZZ) this.hmTest.getValueNext();
				assertNull(objTestZ);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    }
	    public void testIterate(){
			try{
	    		int iSumme;
	    		
	    		//variante 1
	    		iSumme = 0;
				Iterator<Object>itObject=this.hmTest.iterator();
				while(itObject.hasNext()) {		
					Integer intIndex = hmTest.getKeyCurrent();
					DummyTestObjecWithDefaultValuesZZZ objDummyTemp = (DummyTestObjecWithDefaultValuesZZZ) hmTest.get(intIndex);
															
					Integer intValue = new Integer(objDummyTemp.getValue());
					iSumme = iSumme + intValue.intValue();
					System.out.println("Summe bisher: " + iSumme);
				}
				assertTrue(iSumme == 6);
				
				//variante 2
				iSumme = 0;
				hmTest.resetIndex();
				Iterator<Object>itObject02=this.hmTest.iterator();
				while(itObject02.hasNext()) {		
					
					DummyTestObjecWithDefaultValuesZZZ objDummyTemp = (DummyTestObjecWithDefaultValuesZZZ) hmTest.getValueNext();
					 
					Integer intValue = new Integer(objDummyTemp.getValue());
					iSumme = iSumme + intValue.intValue();
					System.out.println("Summe bisher: " + iSumme);
				}
				assertTrue(iSumme == 6);
				
				//variante 3, eigentlich die Variante warum extra ein Iterator implementiert wurde.
				iSumme = 0;
				hmTest.resetIndex();
				Iterator<Object>itObject03=this.hmTest.iterator();
				while(itObject03.hasNext()) {		
					
					DummyTestObjecWithDefaultValuesZZZ objDummyTemp = (DummyTestObjecWithDefaultValuesZZZ) itObject03.next();
					 
					Integer intValue = new Integer(objDummyTemp.getValue());
					iSumme = iSumme + intValue.intValue();
					System.out.println("Summe bisher: " + iSumme);
				}
				assertTrue(iSumme == 6);
				
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		}    
	    	    	    	
//	public void testRemoveByIndex(){
//		try{
//			hmTest.clear();
//			hmTest.put( "Alias1a", "Das ist ein Test");
//			hmTest.put( "Alias1b", "2. Zeile");
//			hmTest.put( "Alias2a", "3. Zeile");
//			hmTest.put( "Alias2b", "4. Zeile");
//			
//			int iSizePre = hmTest.size();
//			assertEquals(4, iSizePre);
//			
//			/* da die Indexposition in der Hashmap nict feststeht, kann man nicht so einfach vorgehen
//			String sValuePre = (String) hmTest.get("Alias1b");
//			assertEquals("2. Zeile", sValuePre); */
//			//Hole den Wert an der Indexposition
//			String sKeyPre = (String) hmTest.getKeyByIndex(1);
//			String sValuePre = (String) hmTest.getValueByIndex(1);
//			assertEquals(sValuePre, hmTest.get(sKeyPre));
//			
//			//Hole den Wert an der Indexposition + 1
//			String sKeyPrePlus = (String) hmTest.getKeyByIndex(2);
//			String sValuePrePlus = (String) hmTest.getValueByIndex(2);
//			assertEquals(sValuePrePlus, hmTest.get(sKeyPrePlus));
//			
//			
//			//+++++++++++++++++++++++++++
//			hmTest.removeByIndex(1);
//						
//			//+++++++++++++++++++++++++++
//			int iSizePost = hmTest.size();
//			assertEquals(iSizePre-1, iSizePost);
//			
//			/* da die Indexposition in der HashMap nicht feststeht, kann man nicht so einfach vorgehen
//			String sValuePost = (String) hmTest.get("Alias1b");  //Merke: Man kann nicht nach der oben angegebenen Reihenfolge vorgehen, da die Indexposition beleibig ist.
//			assertNull("Nach dem Entfernen sollte fuer diesen Key kein Wert mehr vorhanden sein", sValuePost);*/
//			String sKeyPost = (String) hmTest.getKeyByIndex(1);
//			String sValuePost = (String) hmTest.getValueByIndex(1);
//			assertNotSame("Nach dem Entfernen sollte f�r diesen Key an der Position ein anderer Wert sein", sValuePre, sValuePost);
//			assertEquals(sValuePost, hmTest.get(sKeyPost));
//			
//			//Dies sollte dann dem vorherigen Wert an der +1 Indexposition entsprechen
//			assertEquals(sKeyPrePlus, sKeyPost);
//			assertEquals(sValuePrePlus, sValuePost);
//			
//			
//		} catch (Exception e) {
//			fail("Method throws an exception." + e.getMessage());
//		} 
//	}
	
	
	  
}
