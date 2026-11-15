package basic.zBasic;

import java.lang.reflect.Method;
import java.util.ArrayList;

import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;
import junit.framework.TestCase;

/**
 *  Merke: Das THE im Klassenanemn ist Absicht, weil nämlich die  Reflection - Klassen sellbst 
 *            aus dem  errechneten Stacktrace, etc. herausgerechnet werden.
 *            Darum weichen die Testklassen vom Schema ab.
 * 
 * @author Fritz Lindhauer, 08.08.2019, 23:15:52
 * 
 */
public class ReflectClassZZZTest  extends TestCase{
	
	//private ObjectZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			//objObjectTest = new ObjectZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testGetInstanceOfList(){
		 try {
			   //###############################
			   //### 1. Testobjekt
		 	   DummyTestObjectZZZ obj = new DummyTestObjectZZZ();	 	  
		 	   ArrayList<Class<?>>listaInstance = ReflectClassZZZ.getInstanceOfList(obj);
		 	   assertNotNull(listaInstance);
		 	   
		 	   boolean btemp = obj instanceof DummyTestObjectZZZ;
		 	   if(btemp) {
		 		   assertTrue(listaInstance.contains(DummyTestObjectZZZ.class));
		 	   }
		 	   
		 	  btemp = obj instanceof IDummyTestObjectZZZ;
		 	   if(btemp) {
		 		   assertTrue(listaInstance.contains(IDummyTestObjectZZZ.class));
		 	   }
		 	   
		 	  //###############################
		 	  //### 2. TestObjekt
		 	  DummyTestObjecWithDefaultValuesZZZ obj2 = new DummyTestObjecWithDefaultValuesZZZ();
		 	  ArrayList<Class<?>>listaInstance2 = ReflectClassZZZ.getInstanceOfList(obj2);
		 	  assertNotNull(listaInstance2);
		 	  
		 	  btemp = obj2 instanceof DummyTestObjecWithDefaultValuesZZZ;
		 	  if(btemp) {
		 		   assertTrue(listaInstance2.contains(DummyTestObjecWithDefaultValuesZZZ.class));
		 	   }
		 	   
		 	   System.out.println("test");
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
	 		}    
	}
	


	
	
}//END Class