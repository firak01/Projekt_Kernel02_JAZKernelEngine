package basic.zBasic;

import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.file.FileEasyConstantConverterZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import custom.zKernel.Log;
import junit.framework.TestCase;

public class ObjectZZZTest extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;
	private DummyTestObjectWithFlagOnFlagListeningZZZ objObjectTestListening = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			objObjectTestListening = new DummyTestObjectWithFlagOnFlagListeningZZZ();
		
//		} catch (ExceptionZZZ e) {
//			ez.printStackTrace();
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testConstructor(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
			assertTrue(objObjectInit.getFlag("init")==true); 
			
			
			//TestKonfiguration pr�fen
			assertFalse(objObjectTest.getFlag("init")==true); //Nun wäre init falsch
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetFlagZ(){
		try{
		//Init - Object
		String[] saFlag = {"init"};
		DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ(saFlag);
		assertTrue(objObjectInit.getFlag("init")==true); 
		
		
		//TestKonfiguration prüfen.
		//1. Hole alle FlagZ des Objekts
		String[] saTest01 = objObjectInit.getFlagZ();
		assertNotNull(saTest01); 		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarchie mindestens 4 FlagZ erwartet: DUMMY, DEBUG, INIT und FOR_TEST.",saTest01.length==4);
		assertTrue(StringArrayZZZ.contains(saTest01,"DUMMY"));			
		assertTrue(StringArrayZZZ.contains(saTest01,"FOR_TEST"));
		assertTrue(StringArrayZZZ.contains(saTest01,"DEBUG"));
		assertTrue(StringArrayZZZ.contains(saTest01,"INIT"));
		
		//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
		String[]saTest02 = objObjectInit.getFlagZ(true);
		assertNotNull(saTest02);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
		assertTrue(StringArrayZZZ.contains(saTest02,"INIT"));		
		
		String[]saTest02b = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02b);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie 2 FlagZ für 'false' erwartet: DUMMY, DEBUG.",saTest02b.length==3);
		assertTrue(StringArrayZZZ.contains(saTest02b,"DUMMY"));
		assertTrue(StringArrayZZZ.contains(saTest02b,"FOR_TEST"));
		assertTrue(StringArrayZZZ.contains(saTest02b,"DEBUG"));
		
		objObjectInit.setFlag("DEBUG", true);
		String[]saTest02c = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02c);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
				
		objObjectInit.setFlag("DUMMY", true);
		String[]saTest02d = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02d);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur noch 1 Flag für 'false' erwartet.",saTest02d.length==1);
		
		objObjectInit.setFlag("FOR_TEST", true);
		String[]saTest02e = objObjectInit.getFlagZ(false);
		assertNotNull(saTest02e);		
		assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie kein Flag für 'false' erwartet.",saTest02e.length==0);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
		
	}
	
	
	public void testSetFlagZ_listening(){
		try{
			//Init - Object
			String[] saFlag = {"init"};
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ();
			DummyTestObjectWithFlagOnFlagListeningZZZ objObjectInitListening = new DummyTestObjectWithFlagOnFlagListeningZZZ();
			
			//Das Objekt mit der "Faehigkeit" Listing am Objekt mit Flag registrieren.
			//Merke: Jedes Objekt mit Flag hat automatisch ein eingebauts SenderObjekt
			objObjectInit.registerForFlagEvent(objObjectInitListening);
			
			
			//1. +++ Reagieren auf einen fremden Wert
			//!!! Wichtig nun ein Flag Setzen
			objObjectInit.setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST, true);
			
			//Merke: das DummyTestObjekt hat eine Eigenschaft, die nur nach dem "erfolgreichen hoeren" gesetzt ist.
			String sValue = objObjectInitListening.getValueDummyByFlagEvent();
			assertNotNull("Ein Wert sollte in dem flagChanged() Event gesetzt worden sein", sValue);
			

			String sLog = ReflectCodeZZZ.getPositionCurrent()+"Per Event empfangener Wert aus dem setFlag='"+sValue+"'";
			System.out.println(sLog);
			assertEquals(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST.name(), sValue);
			
			objObjectInit.unregisterForFlagEvent(objObjectInitListening);
			
			//########################################################################
			//2. +++ Reagieren auf einen eigenen Wert
			objObjectInitListening.setValueDummyByFlagEvent(null);
			objObjectInitListening.setFlag(IListenerObjectFlagZsetZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT, true);
			
			 //Ein anderes Objekt wird registriert. Aber wg. dem Flag wird das eigene Objekt auch registriert und hört das Objekt auf sich selbst auch!!!
			objObjectInitListening.registerForFlagEvent(objObjectTestListening);

			String sValue02 = objObjectInitListening.getValueDummyByFlagEvent();
			assertNull("NULL erwartet. Wert ist aber '" + sValue02 + "'. Noch sollte kein Wert in dem flagChanged() Event gesetzt worden sein", sValue02);
			
			
			objObjectInitListening.setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST, true);
			sValue02 = objObjectTestListening.getValueDummyByFlagEvent();
			assertNotNull("Jetzt sollte ein Wert in dem flagChanged() Event gesetzt worden sein", sValue02);
			
			String sLog02 = ReflectCodeZZZ.getPositionCurrent()+"Per Event empfangener Wert aus dem EIGENEN setFlag='"+sValue02+"'";
			System.out.println(sLog02);
			assertEquals(IDummyTestObjectWithFlagZZZ.FLAGZ.FOR_TEST.name(), sValue02);
			
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	public void testLogProtocolWithPosition() {
		try {			
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ();
			//objObjectInit.protocolWithPosition("TESTWERT logProtocolWithPosition");
			Log.protocolWithPosition(objObjectInit, "TESTWERT logProtocolWithPosition");
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	
	}
	
	public void testLogLineDate() {
		try {		
			DummyTestObjectWithFlagZZZ objObjectInit = new DummyTestObjectWithFlagZZZ();
			//objObjectInit.printlnDate("TESTWERT logLineDate");
			Log.printlnDate(objObjectInit, "TESTWERT logLineDate");
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	
	}
	
	
	public void testLogLineDateWithPostion() {
		try {		
			//ObjectZZZ.printLineDateWithPosition(FileEasyConstantConverterZZZ.class, "TESTWERT logLineDateWithPosition");
			Log.printlnDateWithPosition(FileEasyConstantConverterZZZ.class, "TESTWERT logLineDateWithPosition");
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}

}//END Class
