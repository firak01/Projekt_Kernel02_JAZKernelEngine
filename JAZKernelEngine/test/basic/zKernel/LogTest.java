package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.system.SystemSingletonZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ.PRINTLEVEL;
import custom.zKernel.Log;
import junit.framework.TestCase;

public class LogTest extends TestCase{

	private static final String strTEST_ENTRY01_DEFAULT = new String("bla");
	private static final String strTEST_ENTRY02_DEFAULT = new String("blub");
	
	public void testInfo(){
		try {
				boolean bValue=false;

				//##################################################################################
				//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
				//##################################################################################
				
				//#############################################################
				//Beim PrintLEvel Warning, kommt hier nix raus
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.WARNING);												
				bValue = Log.writeInfo(strTEST_ENTRY01_DEFAULT);
				assertFalse(bValue);
				
				//#############################################################
				//Erst beim PrintLEvel Info, findet eine Ausgabe statt
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.INFO);												
				bValue = Log.writeInfo(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
								
				//################################################################
				//Beim PrintLevel Debug soll noch eine 2te Zeile zusätzlich rauskommen, mit Details.
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG);
				bValue = Log.writeInfo(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
								
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testDebug(){
	try {
			boolean bValue=false;
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
			//##################################################################################

			//#############################################################
			//Beim PrintLevel Warning, kommt hier nix raus
			SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.WARNING);												
			bValue = Log.writeDebug(strTEST_ENTRY01_DEFAULT);
			assertFalse(bValue);
			
			//#############################################################
			//Erst beim PrintLevel Info, kommt hier nix raus
			SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.INFO);												
			bValue = Log.writeDebug(strTEST_ENTRY01_DEFAULT);
			assertFalse(bValue);

			//################################################################
			//Erst beim PrintLevel Debug eine Ausgabe mit Details.			
			SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG);
			bValue = Log.writeDebug(strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWarning(){
		try {
				boolean bValue=false;
				
				//##################################################################################
				//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
				//##################################################################################
				
				//Beim PrintLevel Warning, kommt hier etwas raus
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.WARNING);								
				bValue = Log.writeWarning(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
				
				//#############################################################
				//Auch beim PrintLevel Info, kommt hier etwas raus
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.INFO);												
				bValue = Log.writeWarning(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);

				//################################################################
				//Beim PrintLevel Debug, zusätzlic eine Ausgabe mit Details.			
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG);
				bValue = Log.writeWarning(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testVerbose(){
		try {
				boolean bValue=false;
				
				//##################################################################################
				//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
				//##################################################################################
				
				//Beim PrintLevel Warning, kommt hier nix raus
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.WARNING);								
				bValue = Log.writeVerbose(strTEST_ENTRY01_DEFAULT);
				assertFalse(bValue);
				
				//#############################################################
				//Beim PrintLevel Info, kommt hier nix raus
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.INFO);												
				bValue = Log.writeVerbose(strTEST_ENTRY01_DEFAULT);
				assertFalse(bValue);

				//################################################################
				//Beim PrintLevel Debug, kommt nix raus.			
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.DEBUG);
				bValue = Log.writeVerbose(strTEST_ENTRY01_DEFAULT);
				assertFalse(bValue);
				
				//################################################################
				//Beim PrintLevel Debug, zusätzlich eine Ausgabe mit Details.
				SystemSingletonZZZ.getInstance().setPrintLevelOverall(PRINTLEVEL.VERBOSE);
				bValue = Log.writeVerbose(strTEST_ENTRY01_DEFAULT);
				assertTrue(bValue);
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
}
