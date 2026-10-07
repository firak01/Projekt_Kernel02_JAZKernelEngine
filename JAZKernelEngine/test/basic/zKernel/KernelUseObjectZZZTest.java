package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.Log;
import junit.framework.TestCase;

public class KernelUseObjectZZZTest extends TestCase {
	private KernelZZZ objKernelFGL;
	private DummyTestKernelUseObjectZZZ objKernelUserTest;
	protected void setUp() throws Exception {
		try {			
			//TODO: Diese Datei zuvor per Programm erstellen
			objKernelFGL = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
			
			//Das eigentliche Testobjekt
			objKernelUserTest = new DummyTestKernelUseObjectZZZ(objKernelFGL);
	
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
	* Lindhauer; 20.04.2006 09:05:11
	 */
	public void testFlagHandling(){
		try{			
			//Testen, ob das Setzen der Flags möglich ist.
			assertTrue("Setting the Flag 'DEBUG' failed.", objKernelUserTest.setFlag("debug", true));			
			assertTrue("Receiving the previously set Flag 'DEBUG' failed.", objKernelUserTest.getFlag("debug"));
			
			//Testen, ob das Setzen der Flags möglich ist.
			assertTrue("Setting the Flag 'DEBUG' failed.", objKernelUserTest.setFlag("debug", false));			
			assertFalse("Receiving the previously set Flag 'DEBUG' failed.", objKernelUserTest.getFlag("debug"));
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
		}
	}
	
	
	public void testLogLineDateWithPosition() {
		try{
			String sLog = null; String sLog2 = null; String[]saLog = null;
			sLog = "Test01";
			//objKernelUserTest.printlnDateWithPosition(sLog);
			Log.printlnDateWithPosition(objKernelUserTest, sLog);
			
			sLog2 = "unter Test01";
			saLog = StringArrayZZZ.append(sLog, sLog2);
			//objKernelUserTest.printlnDateWithPosition(saLog);
			Log.printlnDateWithPosition(objKernelUserTest, saLog);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
		}
	}
	
	public void testLogProtocolStringWithPosition() {
		try{
			String sLog = null; String sLog2 = null; String[]saLog = null;
			sLog = "Test01";
			//objKernelUserTest.protocolWithPosition(sLog);
			Log.protocolWithPosition(objKernelUserTest, sLog);
			
			sLog2 = "unter Test01";
			saLog = StringArrayZZZ.append(sLog, sLog2);
			//objKernelUserTest.protocolWithPosition(saLog);
			Log.protocolWithPosition(objKernelUserTest, saLog);
						
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
		}
	}

}
