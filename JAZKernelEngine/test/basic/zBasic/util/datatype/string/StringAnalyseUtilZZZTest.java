/**
 * 
 */
package basic.zBasic.util.datatype.string;


import junit.framework.TestCase;

import java.util.Iterator;

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
public class StringAnalyseUtilZZZTest extends TestCase implements IConstantZZZ {
	//	+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufraeumen im tearDown().
	
//	//	Objekt, das getestet werden soll
//	private StringArrayZZZ objArrayTest;
//	private StringArrayZZZ objArraySorted;

	@Override
	protected void setUp(){
//		try{
//			objArrayTest = new StringArrayZZZ();
//			objArraySorted = new StringArrayZZZ();
//			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}	    			
	}//END setup
	
	@Override
	protected void tearDown() {
//		try{
//			if(doCleanup){
//         
//          }
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	 
	public void testConsistsOnlyOf(){
		try{
			//Die statische Methode testen.
			String sTest = null; boolean bValue = false;
			String[] saTest01 = {" ", "eins", "zwei", "drei"};
			sTest = "dreizwei";
			bValue = StringAnalyseUtilZZZ.consistsOnlyOf(sTest, saTest01);
			assertTrue(bValue);
			
			sTest = "drei zwei";
			bValue = StringAnalyseUtilZZZ.consistsOnlyOf(sTest, saTest01);
			assertTrue(bValue);
			
			sTest = "drei vier zwei";
			bValue = StringAnalyseUtilZZZ.consistsOnlyOf(sTest, saTest01);
			assertFalse(bValue);
			
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
}
