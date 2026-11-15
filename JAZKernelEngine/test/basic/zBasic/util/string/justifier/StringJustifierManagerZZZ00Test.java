package basic.zBasic.util.string.justifier;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.PrimeFactorizationZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerJustifiedZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import junit.framework.TestCase;

public class StringJustifierManagerZZZ00Test extends TestCase {
	private StringJustifierManagerZZZ objJustifierManagerTest = null;
	
	protected void setUp(){
//		try {			
//			
//					
//			
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 	
	}//END setup

	public void testContructor() {
		try{
			//Das soll ein Singleton sein. Einmal definiert, ueberall verfuegbar.
			IStringJustifierManagerZZZ objManager = StringJustifierManagerZZZ.getInstance();
			boolean btemp1a = objManager.setFlag(IStringJustifierManagerZZZ.FLAGZ.DUMMY, true);
			assertTrue(btemp1a);
						
			StringJustifierManagerZZZ.destroyInstance();
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Nun mal eine neue Version holen. Das Flag sollte fehlen.
			IStringJustifierManagerZZZ objManager2 = StringJustifierManagerZZZ.getNewInstance();
			
			boolean btemp2a = objManager2.getFlag(IStringJustifierManagerZZZ.FLAGZ.DUMMY);
			assertFalse(btemp2a);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
}
