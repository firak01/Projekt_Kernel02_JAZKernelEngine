package basic.zBasic;

import java.lang.reflect.Method;

import basic.zBasic.reflection.ReflectUtilZZZ;
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
public class ReflectUtilZZZTest  extends TestCase{
	
	//private ObjectZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			//objObjectTest = new ObjectZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testInvokeStaticMethodOnClass(){
		try{
			String sClassName = "basic.zBasic.util.machine.EnvironmentZZZ";
			Class objClass = ReflectUtilZZZ.findClass(sClassName);
			assertNotNull(objClass);
			
			String sMethod = "getHostName";
			Method objMethod = ReflectUtilZZZ.findMethodForMethodName(objClass, sMethod);
			assertNotNull(objMethod);
			
			Object objErg = ReflectUtilZZZ.invokeStaticMethod(objMethod);
			assertNotNull(objErg);
			
			String sErg = objErg.toString();
			String sTest = EnvironmentZZZ.getHostName();
			assertEquals(sErg,sTest);
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	


	
	
}//END Class