package basic.zBasic;

import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import junit.framework.TestCase;

/**
 *  Merke: Das THE im Klassenanemn ist Absicht, weil nämlich die  Reflection - Klassen sellbst 
 *            aus dem  errechneten Stacktrace, etc. herausgerechnet werden.
 *            Darum weichen die Testklassen vom Schema ab.
 * 
 * @author Fritz Lindhauer, 08.08.2019, 23:15:52
 * 
 */
public class ReflectTHECodeKernelZZZTest  extends TestCase{
	
	private ReflectTHECodeKernelObject4TestingZZZ objObjectTest = null;

	protected void setUp(){
	try {			
			
			//The main object used for testing
			objObjectTest = new ReflectTHECodeKernelObject4TestingZZZ();
			
		
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		} 
	}//END setup
	 
	public void testGetCallingStack(){		
		try{
			//Verbereitende initialisierung:
			String sCallingMethod = ReflectCodeZZZ.getClassCurrentName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + ReflectCodeZZZ.getMethodCurrentName();			
			String[]saCalling = ReflectCodeZZZ.getCallingStack();
						
			//+++++++++++++++++++++++++++++++++++++++++++++
			//Ermittle aus dem Stacktrace den Klassennamen.Methodennamen. 
			//Dies kann in diversen Möglichkeiten als Schlüssel für eine HashMap verwendet werden.
			String[]saCallingIntern01 = ReflectCodeKernelZZZ.getCallingStack();
			
			//Weil nur die aufrufende  Test-Klasse darin auftauchen soll gilt:
			assertTrue("Es sollte nur die aufrufende Testmethode im Ergebnis sein.", saCallingIntern01.length==1);
			String sCallingIntern01 = saCallingIntern01[0];			
			assertTrue("Es sollte die aufrufende Testmethode im Ergebnis sein", sCallingMethod.equals(sCallingIntern01));
			
			
			//### Nun die Methode innerhalb eines erzeugten Objekts aufrufen ##########################
			boolean btemp = objObjectTest.startAsMain();
			String[] saCallingIntern02 = objObjectTest.getCallingStackComputed();			
			assertTrue("Es sollte die Größe des Arrays höher sein als bei lokalem Funktionsaufruf", saCallingIntern01.length<saCallingIntern02.length);
			
			String sCallingIntern02 = saCallingIntern01[saCallingIntern01.length-1];
			assertTrue("Es sollte die aufrufende Testmethode im Ergebnis sein, als Wert mit dem höchsten Index", sCallingMethod.equals(sCallingIntern02));
									
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
	}
	
	public void testGetClassMethodCallingString(){
		try{
			//Verbereitende initialisierung:
			String sCallingMethod = ReflectCodeZZZ.getClassCurrentName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + ReflectCodeZZZ.getMethodCurrentName();			
			
			//Ermittle aus einem Objekt, das innerhalb einer Methode erstellt wurde, den Namen der aufrufenden Methode.
			boolean btemp2 = objObjectTest.startAsSubInitialiseObject();//der Aufruf der Methode, die das zu anlaylsierende Objekt erzeugt.
			IObjectReflectableZZZ objCreatedInternal = (IObjectReflectableZZZ) objObjectTest.getObjectInitialisedInternal();			
			String sMethodWhichHasObjectInitialized = objCreatedInternal.getClassMethodCallingString();
			sCallingMethod = objObjectTest.getClass().getName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + "startAsSubInitialiseObject";
			assertTrue("Es sollte die Objekt erstellende Testmethode im Ergebnis sein", sCallingMethod.equals(sMethodWhichHasObjectInitialized));
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetClassMethodExternalCallingString(){
		try{
			//Verbereitende initialisierung:
			String sCallingMethod = ReflectCodeZZZ.getClassCurrentName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + ReflectCodeZZZ.getMethodCurrentName();			
			
			//Ermittle aus einem Objekt, das innerhalb einer Methode erstellt wurde, den Namen der aufrufenden Methode.
			boolean btemp2 = objObjectTest.startAsSubInitialiseObject4ExternalTest();//der Aufruf der Methode, die das zu anlaylsierende Objekt erzeugt.
			ReflectTHECodeKernelObject4TestingExternalSubZZZ objCreatedInternal = (ReflectTHECodeKernelObject4TestingExternalSubZZZ) objObjectTest.getObjectInitialisedInternal();			
			String sExternalCallingMethod = objCreatedInternal.makeClassMethodExternalCallingString();			
			assertTrue("Es sollte die aktuelle Methode also die externalCalling Methode des Objekts im Ergebnis sein", sCallingMethod.equals(sExternalCallingMethod));
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
	public void testGetCallingStackExternal(){
		try{
			//Verbereitende initialisierung:
			String sCallingMethod = ReflectCodeZZZ.getClassCurrentName() + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + ReflectCodeZZZ.getMethodCurrentName();			
			
			//Ermittle aus einem Objekt, das innerhalb einer Methode erstellt wurde, den Namen der aufrufenden Methode.
			boolean btemp2 = objObjectTest.startAsSubInitialiseObject4StackExternalTest();//der Aufruf der Methode, die das zu anlaylsierende Objekt erzeugt.
			ReflectTHECodeKernelObject4TestingExternalSubZZZ objCreatedInternal = (ReflectTHECodeKernelObject4TestingExternalSubZZZ) objObjectTest.getObjectInitialisedInternal();			
			String[] saExternalCallingStack = objCreatedInternal.makeClassMethodExternalCallingStack();			
			assertNotNull("Es sollte ein errechnter Stacktrace im Ergebnis sein", saExternalCallingStack);
			assertTrue("Die Länge des errechneten Stactrace sollte 1 sein.", saExternalCallingStack.length==1);
			assertTrue("Es sollte die aktuelle Methode also die externalCalling Methode des Objekts im Ergebnis sein", sCallingMethod.equals(saExternalCallingStack[0]));
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
	}
	
		
}//END Class