package basic.zKernel;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;

public class KernelConfigZZZTest  extends TestCase{
	private KernelConfigZZZ objConfigTest;
	private final String sApplicationKey = "FGL";
	private final String sSystemNumber = "01";
	private final String sFile = "ZKernelConfigKernel_test.ini";
	private final String sDirectory = "";

	 
	
	protected void setUp(){
		try {
			//objKernel= new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",null);
			
		/* damit wird logObject null und der test darf nicht weitergehen
			String[] a = {"init"};
			objKernelTest = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",a);		
			*/
			String[] saArg ={"-k",sApplicationKey,"-s", sSystemNumber ,"-d",sDirectory, "-f", sFile};
			objConfigTest = new KernelConfigZZZ(saArg);
			
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	/**F�r den Pattern String gilt: 1 Zeichen, ggf. gefolgt von einem Doppelpunkt
	 * Pr�fe auf: 
	 * - doppelte Zeichen (au�er dem Doppelpunkt)
	 * - pruefe auf zwei hintereinander folgende Doppelpunkte
	 * 
	* lindhauer; 30.06.2007 08:21:33
	 */
	public void testInitialization(){
		try{
	
			//Ohne Argumente
			KernelConfigZZZ objConfigInit = new KernelConfigZZZ(null);
		    assertNotNull(objConfigInit);
		
		    //Mit leerem Argument
		    String[] saArg ={""};
		    KernelConfigZZZ objConfigInit2 = new KernelConfigZZZ(saArg);
		    assertNotNull(objConfigInit2);

		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	

	}
	
	public void testReadKey(){
		try{
			//##########################
			//Objekt Ohne Argumente
			KernelConfigZZZ objConfigInit = new KernelConfigZZZ(null);
		    assertNotNull(objConfigInit);
		    
		    String stemp = objConfigInit.readApplicationKey();
		    assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp);
		    
		    String stemp2proof = objConfigInit.getApplicationKeyDefault();
		    assertFalse(stemp2proof.equals(stemp));
		
		    //#########################
		    //Objekt Mit leerem Argument
		    String[] saArg ={""};
		    KernelConfigZZZ objConfigInit2 = new KernelConfigZZZ(saArg);
		    assertNotNull(objConfigInit2);
		    
		    stemp = objConfigInit2.readApplicationKey();
		    stemp2proof = objConfigInit2.getApplicationKeyDefault();
		    assertFalse(stemp2proof.equals(stemp));
		    
		    //#########################
		    //Testfixture Object (s. setUp)
		    stemp =objConfigTest.readApplicationKey();
		    assertNotNull(stemp);
		    assertTrue(stemp.equals(sApplicationKey));
		    
		    
		    

		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	
	}
