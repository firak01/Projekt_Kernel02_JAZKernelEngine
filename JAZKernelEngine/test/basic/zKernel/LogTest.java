package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.system.SystemSingletonZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ.PRINTLEVEL;
import custom.zKernel.Log;
import junit.framework.TestCase;

public class LogTest extends TestCase{

	private static final String strTEST_ENTRY01_DEFAULT = new String("bla");
	private static final String strTEST_ENTRY02_DEFAULT = new String("blub");
	
	public void testwriteInfo(){
		try {
				boolean bValue=false;
				
			TODOGOON20261113;//Hier wird ohne Objekt/Klasse falsch "gerechnet"!!!
				
				//##################################################################################
				//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
				//##################################################################################
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG);
				bValue = Log.writeInfo(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testwriteLineDate(){
	try {
			boolean bValue=false;
			
		
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
			//##################################################################################
			
			bValue = Log.writeLineDate(strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
}
