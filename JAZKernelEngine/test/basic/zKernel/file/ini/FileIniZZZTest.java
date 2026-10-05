package basic.zKernel.file.ini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.*;
import custom.zUtil.io.FileZZZ;

public class FileIniZZZTest extends TestCase {
	public final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	public final static String strFILE_NAME_DEFAULT = new String("JUnitFileIniTest.ini");
	
	public final static String[] saValueTestGetAll ={"ValueA", "ValueB", "ValueC", "ValueD", "ValueE"} ;
	private Hashtable objHtSection;  
	
	private File objFile;
	private IKernelZZZ objKernel;
	private IKernelZZZ objKernelInit;
	private KernelLogZZZ objLog;
	
	//+++ Die eigentlichen Test-Objekte
	private FileIniZZZ objFileIniInit;
	private FileIniZZZ objFileIniTest;
	

	protected void setUp(){
		try {			
			
			
			
			//### Hashtable zum Setzen einer Beispielsection in testSetSection()
			objHtSection = new Hashtable();
			objHtSection.put("PropertySetA", "Testvalue set by hashtableA");
			objHtSection.put("PropertySetB", "Testvalue set by hashtableB");
			
			
			//Kernel + Log - Object dem TestFixture hinzuf�gen. Siehe test.zzzKernel.KernelZZZTest
			objKernel = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			objLog = objKernel.getLogObject();
			
			//### Die TestObjecte			
			//An object just initialized
			objKernelInit = new KernelZZZ();
			
			String[] saFlagInit = {"init"};
			objFileIniInit = new FileIniZZZ(objKernel,  objFile, saFlagInit);
			
			//The main object used for testing
			//objFileIniTest = new FileIniZZZ(objKernel, sFileDirectoryUsed, strFILE_NAME_DEFAULT, (String[]) null);

			objFile = TestUtilZZZ.createKernelFileUsed_FileIniZZZTest();
			String[] saFlag = {""}; //Merke: Die Flags des Testobjekts selbst werden in den einzelnen Tests explizit gesetzt.
			objFileIniTest = new FileIniZZZ(objKernel, objFile, saFlag);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessage());
		}			
	}//END setup
	
	
	public void testFlagHandling(){
		try{
			
			//TestKonfiguration prüfen.
			//1. Hole alle FlagZ des Objekts
			String[] saTest01 = objFileIniInit.getFlagZ();
			assertNotNull(saTest01);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 2 FlagZ erwartet: Also nicht nur DEBUG und INIT.",saTest01.length>=3);
			
			//2. Hole alle FlagZ Einträge, die entsprechend true/false gesetzt sind.
			//Init - Object
			assertTrue(objFileIniInit.getFlag("init")==true);
			
			String[]saTest02 = objFileIniInit.getFlagZ(true);
			assertNotNull(saTest02);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie nur 1 FlagZ für 'true' erwartet: INIT.",saTest02.length==1);
					
			String[]saTest02b = objFileIniInit.getFlagZ(false);
			assertNotNull(saTest02b);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie mehr als 1 FlagZ für 'false' erwartet: Also nicht nur DEBUG.",saTest02b.length>=2);
			
			objFileIniInit.setFlag("DEBUG", true);
			String[]saTest02c = objFileIniInit.getFlagZ(false);
			assertNotNull(saTest02c);		
			assertTrue("Es wurden auf dieser Ebenen der Objekthierarrchie JETZT EIN FLAG WENIGER für 'false' erwartet.",saTest02c.length==saTest02b.length-1);
			
			//TestKonfiguration pruefen
			assertTrue(objFileIniInit.getFlag("init")==true);
			assertFalse(objFileIniTest.getFlag("init")==true); //Nun wäre init falsch
			
			
			

			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	/** Erweitertes Flag Handling. 
	 *   Das Ziel ist es gesetzte Flags an andere Objekte "Weiterzureichen"
	 * 
	 */
	public void testFlagPassHandling(){
		//TESTE DIE FUNKTIONALITÄT DER FLAG - ÜBERGABE.
		//Merke: Das wird z.B. bei Erzeugung von FileIniZZZ Objekten im Kernel gemacht, so dass wichtige Flags in den erzeugten Objekten auch gesetzt sind.		
		try{
			
			//### Erst einmal direkt testen						
			HashMap<String, Boolean> hmFlagZ = new HashMap<String, Boolean>();					
			FileIniZZZ exDummy = new FileIniZZZ();					
			String[] saFlagZpassed = objKernelInit.getFlagZ_passable(true, exDummy);						
			assertNotNull(saFlagZpassed);
			int iFlagz = saFlagZpassed.length;
			assertTrue("Es wurde genau 1 gesetztes Flag erwartet", iFlagz==1);
			
			//... und das sollte das init Flag sein.
			boolean btemp = exDummy.getFlag("init");
			assertTrue(btemp);
			btemp = objKernelInit.getFlag("init");
			assertTrue(btemp);
			
			//####################################
			//### Nun mit Objekt-Einsatz
			//####################################
		
			File objFileModule = objFile;//Die im setup konstruierte Datei		
			FileIniZZZ objReturn = null; //Das Objekt, das neu erzeugt wird.
			
			//+++ Wie es im Kernel angewendet wird....
			//a) FileConfigKernelIni ist vorhanden.
			FileIniZZZ objFileIniKernelConfig = objKernel.getFileConfigKernelIni(); 			
			if(objFileIniKernelConfig==null){	
				
				//DAS DARF HIER NICHT PASSIEREN !!! Dieses objKernel-Objekt sollte im Test immer eine ini-Datei haben !!!
				fail("This Kernel Object should have a valid ini-File.");
								
			}else{
				//Übernimm die gesetzten FlagZ...
				hmFlagZ = objFileIniKernelConfig.getHashMapFlag();
				
				//Übernimm die gesetzten Variablen...
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = objFileIniKernelConfig.getHashMapVariable();
				objReturn = new FileIniZZZ(objKernel,  objFileModule, hmFlagZ);
				objReturn.setHashMapVariable(hmVariable);	
			}
									
			hmFlagZ = objReturn.getHashMapFlag();
			assertNotNull(hmFlagZ);
			iFlagz = hmFlagZ.size();
			assertTrue("Es wurde mindestens 1 gesetztes Flag erwartet", iFlagz>=1);
			
			//... aber das init-Flag sollte nicht als gesetzt dabei sein.
			btemp = objReturn.getFlag("init");
			assertFalse(btemp);
			
					
			//##############################################
			
			
			//b) FileConfigKernelIni ist NICHT vorhanden.
			IKernelZZZ objKernelInit = new KernelZZZ();
			objFileIniKernelConfig = objKernelInit.getFileConfigKernelIni();
			if(objFileIniKernelConfig==null){
				
				//DAS DARF HIER NICHT PASSIEREN !!! Dieses objKernel-Objekt sollte im Test immer eine ini-Datei haben !!!
				fail("This DEFAULT - Kernel Object should have a valid DEFAULT ini-File.");

			}else{
				//Übernimm die gesetzten FlagZ...
				hmFlagZ = objFileIniKernelConfig.getHashMapFlag();
				
				//Übernimm die gesetzten Variablen...
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = objFileIniKernelConfig.getHashMapVariable();
				objReturn = new FileIniZZZ(objKernel,  objFileModule, hmFlagZ);
				objReturn.setHashMapVariable(hmVariable);	
			}
			
			hmFlagZ = objReturn.getHashMapFlag();
			assertNotNull(hmFlagZ);
			iFlagz = hmFlagZ.size();
			assertTrue("Es wurde nur 1 gesetztes Flag erwartet", iFlagz==1);
			
			//... und dann sollte das init Flag gesetzt sein.
			btemp = objReturn.getFlag("init");
			assertTrue(btemp);
			objFileIniKernelConfig.getFlag("init");
			assertTrue(btemp);
						
			//##############################################################
			//c) Beim objKernelInit und der weiteren Verwendung sieht das genauso aus
			IKernelZZZ objKernelInit2 = new KernelZZZ();
			objFileIniKernelConfig = objKernelInit2.getFileConfigKernelIni();
			if(objFileIniKernelConfig==null){
				
				//DAS DARF HIER NICHT PASSIEREN !!! Dieses objKernel-Objekt sollte im Test immer eine ini-Datei haben !!!
				fail("This DEFAULT - Kernel Object should have a valid DEFAULT ini-File.");

			}else{
				//Übernimm die gesetzten FlagZ...
				hmFlagZ = objFileIniKernelConfig.getHashMapFlag();
				
				//Übernimm die gesetzten Variablen...
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = objFileIniKernelConfig.getHashMapVariable();
				objReturn = new FileIniZZZ(objKernelInit2,  objFileModule, hmFlagZ);
				objReturn.setHashMapVariable(hmVariable);	
			}
			
			hmFlagZ = objReturn.getHashMapFlag();
			assertNotNull(hmFlagZ);
			iFlagz = hmFlagZ.size();
			assertTrue("Es wurde nur 1 gesetztes Flag erwartet", iFlagz==1);
			
			//... und das sollte das nicht das gesetzte init Flag sein.
			btemp = objReturn.getFlag("init");
			assertTrue(btemp);
			objFileIniKernelConfig.getFlag("init");
			assertTrue(btemp);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	/** void, Test: Reading an entry in a section of the ini-file
	* Lindhauer; 22.04.2006 12:54:32
	 */
	public void testGetProperty(){
		try {
			boolean btemp;
			
			//+++ 20251006: Setzen der notwendigen Flags für diesen Test
			//... mit Berechnung PATH
			btemp = objFileIniTest.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
			assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,true);  
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,true);  
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", btemp);
							
			btemp = objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
			
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA,true);
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA + "'", btemp);
		
			//... mit Berechnung MATH
			btemp = objFileIniTest.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH, true); 
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH + "'", btemp);
				
			//... mit Berechnung JSON
			btemp = objFileIniTest.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true); 
			assertTrue("Das Flag '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON +"' sollte zur Verfügung stehen.", btemp);
							
			//... mit Berechnung JSON-MAP
			btemp = objFileIniTest.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);//Ansonsten wird der Wert sofort ausgerechnet 
			assertTrue("Das Flag '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP +"' sollte zur Verfügung stehen.", btemp);
			
			
			
			//########################################################################################################
			//Erst testen, dass auch kein Leerwert kommt
			IKernelConfigSectionEntryZZZ objEntry =objFileIniTest.getPropertyValue("Section A", "Testentry1"); 
			assertNotNull("Es soll ein Entry-Object zurueckgeliefert werden.", objEntry);
			String sTestValueTemp = objEntry.getValue();
			assertFalse("No empty entry was expected for  the property 'Testentry1' in 'Section A'", sTestValueTemp.equals(""));
			
			objEntry = objFileIniTest.getPropertyValue("Section A", "Testentry1 NOT EXISTING");
			assertNotNull("Auch wenn es alles nicht gibt, ein Entry-Objekt soll zurueckgeliefert werden.", objEntry);
			assertTrue("Zumindest eine Section sollte existent geflagt sein, auch wenn die zuletzt geprüfte Section ggfs. nicht existiert.",objEntry.hasAnySectionExists());
			assertNotNull("Wenn es die Section gibt, den Wert aber nicht, dann soll der Rueckgabewert aber NICHT NULL sein", objEntry.getValue());
			assertTrue("Wenn es die Section gibt, den Wert aber nicht, dann soll der Rueckgabewert aber der Leerstring sein", objEntry.getValue().equals(""));
			
			//nun den Wert testen, wie er im setup definiert wurde
			objEntry = objFileIniTest.getPropertyValue("Section A", "Testentry1");
			assertNotNull("Es soll ein Entry-Object zurueckgeliefert werden.", objEntry);			
			assertTrue("Zumindest eine Section sollte existent geflagt sein, auch wenn die zuletzt geprüfte Section ggfs. nicht existiert.",objEntry.hasAnySectionExists());
			assertTrue("Die Section sollte existent geflagt sein, auch wenn die zuletzt geprüfte Section ggfs. nicht existiert.",objEntry.sectionExists());
			assertEquals("Testvalue1", objEntry.getValue());
			
			//auch wenn es die Section überhaupt nicht gibt, darf kein Fehler entstehen
			objEntry = objFileIniTest.getPropertyValue("blablbllalbl SECTION DOES NOT EXIST", "Not existing entry");
			assertNotNull("Auch wenn es alles nicht gibt, ein Entry-Objekt soll zurueckgeliefert werden.", objEntry);
			assertFalse("Die Section sollte als nicht existent geflagt sein.",objEntry.sectionExists());
			assertFalse("Keine Section sollte existent geflagt sein.",objEntry.hasAnySectionExists());			
			assertNull("NULL erwartet. Wert ist aber '" + objEntry.getValue() + "' Wenn es alles nicht gibt, dann soll der Rueckgabewert aber NULL sein", objEntry.getValue());
			
			
			//20251006: Nach der gruendlichen Ueberarbeitung der letzten 2 Jahre(!) gibt es viel mehr Flags 
			//          und eine echte "mathematische"(!) Formel ist das inzwischen hier nicht mehr, 
			//          sondern lediglich eine PATH Ersetzung durch den Parser.
			//          Merke: PATH Anweisungen müssen mitlerweile in geschweiften Klammern stehen!
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false); //reicht nicht das auszuschliessen...
			objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, false);     //...wird naemlich vom Parser gemacht!!!			
			objEntry = objFileIniTest.getPropertyValue("Section for formula", "Formula1");
			String sTestValueFormula = objEntry.getValue();
			assertEquals("Das ist der '<Z>{[Section for formula value]Value1}</Z>' Wert.", sTestValueFormula); 
			
			objFileIniTest.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true);//Das reicht nicht
			objEntry = objFileIniTest.getPropertyValue("Section for formula", "Formula1");
			sTestValueFormula = objEntry.getValue(); //Es findet ein Solven statt, darum verschwinden die Z-Tags
			assertEquals("Das ist der '{[Section for formula value]Value1}' Wert.", sTestValueFormula); 
		
			objFileIniTest.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true); //jetzt sollte es klappen
			objEntry = objFileIniTest.getPropertyValue("Section for formula", "Formula1");
			sTestValueFormula = objEntry.getValue();
			assertEquals("Das ist der 'first value' Wert.", sTestValueFormula); //Schliesslich soll erst hier umgerechnet werden.
			
			//NEU 20070306: Hier über eine Formel die Property auslesen
			//TODOGOON 20251006: Wenn mn das so macht, dann muss man auch eine echte Formel in der zugrundeliegenden INI-Datei verwenden.
			//also Formula2....
			objFileIniTest.setFlag("useFormula", false);
			objFileIniTest.setFlag("useFormula", true);//jetzt sollte es klappen
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	/** void, Test: Reading all properties of a section
	* Lindhauer; 23.04.2006 10:24:46
	 */
	public void testGetPropertyAll(){
		try{
			String[] saProperty = objFileIniTest.getPropertyAll("Section for testGetPropertyAll");
			assertNotNull("Unexpected: The array of all properties is null", saProperty);
			assertEquals(5, saProperty.length);
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
		
	/** void, Test: Creating and changing entries of a section.
	* Lindhauer; 22.04.2006 13:18:49
	 */
	public void testSetProperty(){
		try {
			//Bestehenden Wert in einer bestehenden Section ändern
		

		    //Testen: Wird die neue Section gleichzeitig anlegen ???
		    //erst einmal nicht speichern
			assertTrue(objFileIniTest.setPropertyValue("Section B", "Testentry1","Testvalue1", false));
			
			//nun mal mit speichern, demnach wird alles andere auch gespeichert
			assertTrue(objFileIniTest.setPropertyValue("Section C", "Testentry1","Testvalue1", true));
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testSetProperty_encrypted(){
		try {
			//Bestehenden Wert in einer bestehenden Section ändern
		
			//1. Den Crypt-Algorithmus erzeugen, Merke ROT13 braucht keine weiteren Argumente, darum wählen wir ihn.		
			ICryptZZZ objCrypt = CryptAlgorithmFactoryZZZ.getInstance().createAlgorithmType(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13);
				
		    //Testen: Wird die neue Section gleichzeitig angelegt ???
		    //erst einmal nicht speichern
			assertTrue(objFileIniTest.setPropertyValue("Section D", "Testentry1EncryptedROT13","Testvalue1", objCrypt, false));
			
			//nun mal mit speichern, demnach wird alles andere auch gespeichert
			assertTrue(objFileIniTest.setPropertyValue("Section E", "Testentry1EncryptedROT13","Testvalue1", objCrypt, true));
			
			//An der Stelle wird nun ein Ausdruck erwartet wie:
			//testProgramPropertyEncryptedROT13=<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>grfgjreg4qrpelcgrq ybpny 4 cebtenz</Z:Code></Z:Encrypted></Z>
			
			
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testDeleteProperty(){
			try{
			//eine property entfernen, die es nicht gib. Auch das soll true zur�ckliefern
			assertTrue(objFileIniTest.deleteProperty("Section A", "das gibt es nicht", true));
			
			//eine property entfernen, die im Setup daf�r erstellt worden ist
			assertTrue(objFileIniTest.deleteProperty("Section A", "TestentryToBeDeleted", true));
			
			//die einzige Property einer Sektion entfernen, die daf�r im Setup extra erstellt worden ist.
			assertTrue(objFileIniTest.deleteProperty("Section for deletion", "TestentryToBeDeleted", true));
			
			//assertTrue()
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
		
	public void testGetSectionAll(){
		try{
		String[] saSection = objFileIniTest.getSectionAll();
		assertNotNull("Unexpected: The array of all sections is null", saSection);
		assertEquals(12, saSection.length);
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
/** void, Test: Getting an unsorted Array of the values of a section.
* Lindhauer; 25.04.2006 08:23:16
 */
public void testGetValueAll(){
	try{
		String[] saValue = objFileIniTest.getPropertyValueAll("Section for testGetValueAll");
		assertNotNull("Unexpected: The array of all properties is null", saValue);
		assertEquals(5, saValue.length);
		for(int icount= 0; icount < saValue.length; icount++){
			assertEquals(saValueTestGetAll[icount], saValue[icount]);
		}
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


/** void, Test: Setting all property=value pairs of a section. Test: Saving it.
* Lindhauer; 25.04.2006 08:22:30
 */
public void testSetSectionSave(){
	try{
		//Anlegen der Section, Paramter true bedeutet, das zuvor eine ggf. vorhandene Section gleichen Namens entfernt wird.
		boolean btemp = objFileIniTest.setSection("Section for testSetSection", objHtSection, true);
		assertTrue("Setting the section 'Section for testSetSection' seems to have failed.",btemp);
				
		//Diese �nderung auch mal abspeichern. Damit ist sie am Ende der Datei.
		btemp = objFileIniTest.save();
		assertTrue("Saving the newly set section 'Section for testSetSection' seems to have failed.",btemp);
				
		//Nun testen, ob die Werte der Hashtable tats�chlich in der Datei stehen. Sie sollten angeh�ngt werden
		//TODO GOON: Klasse entwickeln, welche eine Datei "von hinten nach vorne liest."
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testProofSectionExists(){
	try{
		//Fehlerfall pr�fen
		boolean btemp = objFileIniTest.proofSectionExistsDirectLookup("Section This does not exist");
		assertFalse("The section 'Section This does not exist' was expected not to exist",btemp);
				
		//Truefall pr�fen
		boolean btemp2 = objFileIniTest.proofSectionExistsDirectLookup("Section for proofsectionexists");   //!!! Damit ist auch bewiesen, dass Gro�-/Kleinschreibung keine Rolle spielt 
		assertTrue("The section 'Section for ProofSectionExists' was expected to exist",btemp2);
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testGetPropertyValueSystemNrSearched(){
	/*
	      objStreamFile.println("[Section for testGetPropertyValueSystemNrSearched!01]");
			objStreamFile.println("Value1=first value systemnr 01");
	 */
	try{
		String sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","Value1", "01").getValue();
		assertEquals("first value systemnr 01",sValue);
		
		//Ohne explizite Uebergabe der SystemNr gilt: Wenn der Wert nicht gefunden wird, so wird im globalen nachgesehen		
		sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","ValueGlobalOnly", null).getValue();
		assertEquals("second value global",sValue);
		
		//Wird allerdings die Sektion schon lokal übergeben, so wird nur in der lokalen konfiguration nachgesehen
		IKernelConfigSectionEntryZZZ objEntry = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched!01","ValueGlobalOnly", "01"); 
		sValue = objEntry.getValue();
		assertTrue("Ein richtiger Wert für die Property 'ValueGlobalOnly' wurde bei der Verwendung einer explizit mit einer Systemnr versehenen Section nicht erwartet.",StringZZZ.isEmpty(sValue));
		boolean bHasAnyValue = objEntry.hasAnyValue();
		assertFalse("Ein Wert für die Property 'ValueGlobalOnly' wurde bei der Verwendung einer explizit mit einer Systemnr versehenen Section nicht erwartet.",bHasAnyValue);
		
		//Nun die Suche nach einem Wert, den es nicht global gibt, muss trotzdem funktionieren
		sValue = objFileIniTest.getPropertyValueSystemNrSearched("Section for testGetPropertyValueSystemNrSearched","ValueLocalOnly", "01").getValue();
		assertEquals("local value systemnr 01",sValue);
		
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}



}//END class
	
