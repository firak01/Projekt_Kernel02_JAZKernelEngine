package basic.zKernel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmFactoryZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledConstantZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniParserZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_VariableZZZ;
import basic.zKernel.file.ini.TestUtilZZZ;
import custom.zKernel.ConfigFGL;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

/** wird in der TestSuite AllTest zusammengefasst.
 * Darum gibt es hier keine Main - Methode.
 * Die Main Methode von AllTest kann ausgefuehrt werden.
 */
public class KernelZZZTest extends TestCase {
	//Der abschliessende Verzeichnisname muss dem in der Ini definierten relativen Verzeichnisnamen entsprechen
	public final static String strFILE_DIRECTORY_DEFAULT = new String("test");
	public final static String strFILE_DIRECTORY_DEFAULT_EXTERNAL = new String("c:\\fglKernel\\KernelTest\\"+KernelZZZTest.strFILE_DIRECTORY_DEFAULT);
	
	//Der Dateiname muss dem in der Ini definierten Dateinamen entsprechen.
	public final static String strFILE_NAME_DEFAULT = new String("ZKernelConfigKernel_test.ini");
		
	//+++ Die eigentlichen Test-Objekte
	private IKernelZZZ objKernelFGL;
	private IKernelZZZ objKernelTEST;
	private IKernelZZZ objKernel4ModuleReadonlyFGL;
	private IKernelZZZ objKernel4ModuleReadonlyTEST;
	
	
	protected void setUp(){
		try {			
			String sFileDirectorySource="test";
			String sFileNameSource="ZKernelConfigKernel_test.ini";
			
			//### Kernel-Objekte mit Konfigurationsdateien zum "Nur Lesen"
			//Es wird keine Kopie der im Test-Verzeichnis liegenden Datei erstellt.
			//Dadurch koennen ausgehend von dieser Original-Datei andere Dateien (Moduldateien) im gleichen Verzeichnis gefunden werden.
			objKernel4ModuleReadonlyFGL=new KernelZZZ("FGL", "01", sFileDirectorySource, sFileNameSource,(String[]) null);
			objKernel4ModuleReadonlyTEST=new KernelZZZ("TEST", "01", sFileDirectorySource, sFileNameSource,(String[]) null);
			
			//### Kernel-Objekte mit Konfigurationsdateien zum "Reinschreiben"
			//Ausgehend von der im Test-Verzeichnis liegenden Datei,
			//die im Test konkret verwendete Datei wird extra erstellt.			
			File objFile = TestUtilZZZ.createKernelFileUsed_KernelZZZTest(sFileDirectorySource, sFileNameSource);
	
			String sFileDirectorySourceCopied=objFile.getParent();
			String sFileNameSourceCopied=objFile.getName();			
			objKernelFGL = new KernelZZZ("FGL", "01", sFileDirectorySourceCopied, sFileNameSourceCopied,(String[]) null);
			objKernelTEST = new KernelZZZ("TEST", "01", sFileDirectorySourceCopied, sFileNameSourceCopied,(String[]) null);

			//### Externe Modulkonfigurationsdatei erstellen, die in einigen Testfaellen verwendet wird.
			//Hier wird der konkrete in der Kernel-Objekt Konfigurationsdatei verwendete Name hart-coded verwendet.
			//weil das Suchen nach der richtigen Moduldatei ja auch schon einen Kernelfunktionalitaet ist.
			//Den Setup der Kerneltests mit einer selbst zu testenden Methode zu machen ist unschoen.
			File objFileModuleExternUsed = TestUtilZZZ.createKernelFileUsed_KernelZZZTest(sFileDirectorySource, "ZKernelConfigKernelModulExtern_test.ini");			
		} catch (ExceptionZZZ e) {
			e.printStackTrace();
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	private void removeLogFile(IKernelZZZ objKerneltemp) throws ExceptionZZZ{
		//		Log-File entfernen
		if(objKerneltemp != null){
			KernelLogZZZ objLog = objKerneltemp.getLogObject();
			if(objLog != null){
				FileZZZ objFile = objLog.getFileObject();
				if(objFile!=null){
					objFile.deleteOnExit();
				}
			}
		}
	}
	private void removeLogFile(KernelZZZ objKerneltemp) throws ExceptionZZZ{
		//		Log-File entfernen
		if(objKerneltemp != null){
			KernelLogZZZ objLog = objKerneltemp.getLogObject();
			if(objLog != null){
				FileZZZ objFile = objLog.getFileObject();
				if(objFile!=null){
					objFile.deleteOnExit();
				}
			}
		}
	}
	
public void testContructor(){
		//Wichtig: Das Entfernen des Log-Files, das ja immer erzeugt wird bei der Kernel Initiierung explizit programmieren.
	
	
		//Falls ein Datei nicht existiert, dann soll ein Fehler geworfen werden
		IKernelZZZ objKerneltemp01=null;
		try{
			objKerneltemp01 = new KernelZZZ("TEST", "01", "c:\\fglkernel\\NotExisting", "ZKernelConfigKernel_test.ini",(String[]) null);
			fail("Directory 'c:\\fglkernel\\NotExisting' was expected not to exist. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
			//Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp01);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		IKernelZZZ objKerneltemp02=null;
		try{
			objKerneltemp02 = new KernelZZZ("TEST", "01", "", "ZKernelConfigKernel_testNotExisting.ini", (String[]) null);
			fail("Directory 'c:\\fglkernel\\NotExisting' was expected not to exist. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp02);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try{
		//Dann wird das Objekt nur initialisiert, um statische Methoden zu verwenden
		IKernelZZZ objKernelInit = new KernelZZZ();
		assertTrue(objKernelInit.getFlag("init")==true);
		
		//TestKonfiguration pr�fen
		assertFalse(objKernelFGL.getFlag("init")==true); //Nun w�re init falsch
		assertEquals("FGL", objKernelFGL.getApplicationKey());
		assertEquals("01", objKernelFGL.getSystemNumber());
		assertEquals("FGL!01", objKernelFGL.getSystemKey()); //so wird die Categorie in der ini-Datei bezeichnet
		
		assertNotNull("Test-Configuration-File does not exist", objKernelFGL.getFileConfigKernelAsIni()); //die Test-Konfigurationsdatei soll vorhanden sein.
				
		assertNotNull("Log-Filepath in configuration file does not exist", objKernelFGL.getLogObject());
		
		
		
		//Testkonfiguration pr�fen
		assertFalse(objKernelTEST.getFlag("init")==true); //Nun w�re init falsch
		assertEquals("TEST", objKernelTEST.getApplicationKey());
		assertEquals("01", objKernelTEST.getSystemNumber());
		assertEquals("TEST!01", objKernelTEST.getSystemKey()); //so wird die Categorie in der ini-Datei bezeichnet
		
		assertNotNull("Test-Configuration-File does not exist", objKernelTEST.getFileConfigKernelAsIni()); //die Test-Konfigurationsdatei soll vorhanden sein.
				
		assertNotNull("Log-Filepath in configuration file does not exist", objKernelTEST.getLogObject());
		
		
		//TEST: Einen angegebenen Applikationsschlussel gibt es n der Datei nicht
		IKernelZZZ objKerneltemp=null;
		try{
			objKerneltemp = new KernelZZZ("NichtDa", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",(String)null); //Keine Objektzuweiseung durchf�hren, es geht nur darum die Exception auszul�sen !!!
			fail("A not existing application-key should throw an error");
		}catch (ExceptionZZZ ez){
			//Erwarteter Fehler, keine Ausgabe !!!
//			Log-File entfernen
			this.removeLogFile(objKerneltemp);
		}
		
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		//###############################################
		//Exceptions für fehlende Parameter im Construktor
		IKernelZZZ objKerneltemp=null;
		try{
			objKerneltemp = new KernelZZZ("", "01", "", "ZKernelConfigKernel_test.ini", (String)null);
			fail("Application Key was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			objKerneltemp = new KernelZZZ("TEST", "", "", "ZKernelConfigKernel_test.ini", (String)null);
			fail("SystemNumber was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			objKerneltemp = new KernelZZZ("TEST", "01", "", "",(String) null);
			fail("File was not passed. An expected exception was not thrown");
		}catch(ExceptionZZZ ez){
			//Dieser Fehler wird erwartet.
//			Log-File entfernen
			try {
				this.removeLogFile(objKerneltemp);
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}//END Function

public void testConstructorConfigObject(){

	try{
		String[] saArg ={"-k","TEST","-s","01" ,"-d","test", "-f", "ZKernelConfigKernel_test.ini"};
		IKernelConfigZZZ objConfig = new ConfigFGL(saArg);
		assertTrue(objConfig.isOptionObjectLoaded());
		 
		IKernelZZZ objKerneltemp01 = new KernelZZZ(objConfig, (String[]) null);
		assertEquals("TEST", objKerneltemp01.getApplicationKey());
		assertEquals("01", objKerneltemp01.getSystemNumber());
		
		File objFile = objKerneltemp01.getFileConfigKernel();
		assertEquals("ZKernelConfigKernel_test.ini", objFile.getName());
		
//		Log-File entfernen
		this.removeLogFile(objKerneltemp01);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
		
		//########################	
	try{
		String[] saArg2 ={"-s","01" ,"-d","test", "-f", "ZKernelConfigKernel_test.ini"};
		IKernelConfigZZZ objConfig = new ConfigFGL(saArg2);
		assertTrue(objConfig.isOptionObjectLoaded());
		IKernelZZZ objKerneltemp02  = new KernelZZZ(objConfig, (String[]) null);
		try{					
			//Der Default - Applikation - key (des Config-Objekts)  sollte hier stehen, weil es wurde kein anderer uebergeben.
			assertEquals(objKerneltemp02.getApplicationKey(), objConfig.getApplicationKeyDefault());
			assertFalse(objKerneltemp02.getApplicationKey().equals(""));
		}catch(ExceptionZZZ ez){
			//erwarteter Fehler
//			Log-File entfernen
			this.removeLogFile(objKerneltemp02);
		}
		
	}catch(Exception e){
		fail("Method throws an exception." + e.getMessage());
	}
	

}

public void testComputeSystemSectionNamesForProgram() {
	try{
		ArrayList<String> listasProgramSection = objKernelFGL.computeSystemSectionNamesForProgram("TestProgramName");
		assertNotNull(listasProgramSection);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}




public void testProofModuleIsConfigured(){
	try{
		boolean btemp = objKernelFGL.proofFileConfigModuleIsConfigured("TestModule");
		assertTrue(btemp);
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
* Lindhauer; 20.04.2006 09:05:11
 */
public void testFileConfigAllByDir(){
	try{
		//Konfiguration & Methode testen
		//1. Hole das aktuelle Verzeichnis
		File objFileDir = objKernel4ModuleReadonlyFGL.getFileConfigKernel().getParentFile();
		String sDir = objFileDir.getAbsolutePath();
				
		//Hier sollte jetzt etwas gefunden werden
		File[]objaFileConfig = objKernel4ModuleReadonlyFGL.getFileConfigModuleAllByDir(sDir);
		assertNotNull("There should be at least one ini-file.", objaFileConfig);
		assertTrue("There should be at least two files. ArraySize: " + objaFileConfig.length, objaFileConfig.length>1);
		
		//2. Test mit einem Verzeichnis, das es nicht gibt.
		String sDirNixda = "c:\\temp\\nixda";
		File[] objaFileConfigNixda=null;
		try {
			objaFileConfigNixda = objKernel4ModuleReadonlyFGL.getFileConfigModuleAllByDir(sDirNixda);
			fail("An Exception should have happened looking in a not existing directory '" + sDirNixda + "'");
		}catch(ExceptionZZZ ez){			
		}
		assertNull("This directory was expected to not exist. Ergo there shouldn´t be any ini-file.", objaFileConfigNixda);
				
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
}

/** void, Testet die Methode und auch, ob es das Test-Modul-Konfigurations-File auch gibt.
* Lindhauer; 20.04.2006 09:05:11
 */
public void testGetFileConfigModuleIniByAlias(){
	try{
		//Konfiguration & Methode testen
		File objFile = objKernel4ModuleReadonlyFGL.getFileConfigModuleOLDDIRECT("TestModule");
		assertNotNull("The module for the alias 'TestModule' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModule' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigModuleOLDDIRECT("NotExistingModuleTest"));
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
	
	try{
		//Konfiguration & Methode testen
		File objFile = objKernel4ModuleReadonlyFGL.getFileConfigModuleOLDDIRECT("TestModuleExtern");
		assertNotNull("The module for the alias 'TestModuleExtern' is not configured in the kernel-configuration-file.", objFile);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		assertTrue("The configuration file for the alias 'TestModuleExtern' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objKernelFGL.getFileConfigModuleOLDDIRECT("NotExistingModuleTest"));
		
		//#### Auslesen von Werten aus dieser Datei
		//+++ über module Alias
		String sValueModuleExtern = objKernel4ModuleReadonlyFGL.getParameterByModuleAlias("TestModuleExtern", "testModulePropertyExtern").getValue();
		sValueModuleExtern = StringZZZ.left(sValueModuleExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","TestModuleValueExtern",sValueModuleExtern);
		
		
		//+++ über Program Alias
		String sValueProgramExtern = objKernel4ModuleReadonlyFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern", "testGlobalProperty").getValue();
		sValueProgramExtern = StringZZZ.left(sValueProgramExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","testWert global extern",sValueProgramExtern);
		
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
	
	//#################################################
	try{
		//Konfiguration & Methode testen
		FileIniZZZ objFileConfig = objKernel4ModuleReadonlyFGL.getFileConfigModuleIni("TestModuleExtern");
		assertNotNull("The module for the alias 'TestModuleExtern' is not configured in the kernel-configuration-file.", objFileConfig);
		
		//Testen, ob die Modulkonfiguration auch vorhanden ist
		File objFile = objFileConfig.getFileObject();
		assertNotNull(objFile);		
		assertTrue("The configuration file for the alias 'TestModuleExtern' does not exist.", objFile.exists());
				
		//Diese Konfiguration sollte es nicht geben
		FileIniZZZ objFileConfigNotExisting = objKernel4ModuleReadonlyFGL.getFileConfigModuleIni("NotExistingModuleTest");
		assertNull("The module for the alias 'NotExistingModuleTest' seems to be configured in the kernel-configuration-file, or this tested method is buggy.", objFileConfigNotExisting);
		
		//#### Auslesen von Werten aus dieser Datei
		//+++ über module Alias
		String sValueModuleExtern = objKernel4ModuleReadonlyFGL.getParameterByModuleAlias("TestModuleExtern", "testModulePropertyExtern").getValue();
		sValueModuleExtern = StringZZZ.left(sValueModuleExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","TestModuleValueExtern",sValueModuleExtern);
		
		
		//+++ über Program Alias
		String sValueProgramExtern = objKernel4ModuleReadonlyFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern", "testGlobalProperty").getValue();
		sValueProgramExtern = StringZZZ.left(sValueProgramExtern+"|","|");
		assertEquals("The configuration of 'TestProg' in the module 'TestModuleExtern' returns an unexpected value.","testWert global extern",sValueProgramExtern);
		
	}catch(ExceptionZZZ ez){
		fail("An Exception happend looking for the configuration file for some alias: " + ez.getDetailAllLast());
	}
	
}

public void testProofModuleFileIsConfigured(){
	try{
		assertEquals(true, objKernel4ModuleReadonlyFGL.proofFileConfigModuleIsConfigured("TestModule"));
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testProofModuleFileExists(){
	try{
		// Dieses Modul soll existieren
		assertEquals(true, objKernel4ModuleReadonlyFGL.proofFileConfigModuleExists("TestModule"));
		
		// Dieses Modul sollte nicht existieren
		assertEquals(false, objKernel4ModuleReadonlyFGL.proofFileConfigModuleExists("blablablablabla"));
		
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
}

public void testGetModuleAll(){
	try{
		ArrayList listaModuleString = objKernel4ModuleReadonlyFGL.getModuleAll();
		assertNotNull(listaModuleString);
		assertTrue(listaModuleString.size()>=1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

public void testGetParameter(){
	boolean btemp; String stemp;
	String sValue=null;
	try{
		// Dieses Modul soll existieren
		sValue = objKernelTEST.getParameter("TestGetParameter").getValue();
		sValue = StringZZZ.left(sValue+"|","|");
		assertEquals("Test erfolgreich", sValue);
				
		sValue = objKernelTEST.getParameter("TestGetParameter_Encrypted").getValue();
		sValue = StringZZZ.left(sValue+"|","|");
		assertEquals("<Z><Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted></Z>",sValue);

		//############################################################
		//Nun die Verschluesselung Schritt fuer Schritt "einschalten"	
		//objKernelTest.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		//....

		
		
		//+++ Expression und der Solver muss noch aktiviert werden
		btemp = objKernelTEST.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true); 
		assertTrue("Flag nicht vorhanden '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'", btemp);
		
		btemp = objKernelTEST.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);//ohne Parser wird der Expression-Ausdruck nicht erkannt. 
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", btemp);
		
		btemp = objKernelTEST.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,true); //reicht nicht das auszuschliessen...
		assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", btemp);
		
		//Das reicht noch nicht, unbeding den Cache leeren
		objKernelTEST.getCacheObject().clear();
		sValue = objKernelTEST.getParameter("TestGetParameter_Encrypted").getValue();
		sValue = StringZZZ.left(sValue+"|","|");
		assertEquals("<Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted>", sValue);
		
		
		//Hiermit teste ich aber auch das "Flag-Array-Setzen" per Enumeration
		IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ = new IKernelEncryptionIniSolverZZZ.FLAGZ[1];
		objaEnum_IKernelEncryptionIniSolverZZZ[0]=IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION;		
		boolean[] baReturn0 = objKernelTEST.setFlag(objaEnum_IKernelEncryptionIniSolverZZZ, true);
		assertTrue("Das Flag USEENCRYPTION wurde erwartet",baReturn0[0]);//Verschluesselung aufloesen ist ja genau das was wir wollen.
	
		IObjectWithExpressionZZZ.FLAGZ[] objaEnum_IKernelExpressionIniSolverZZZ = new IObjectWithExpressionZZZ.FLAGZ[1];
		objaEnum_IKernelExpressionIniSolverZZZ[0]=IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION;
		boolean[] baReturn1 = objKernelTEST.setFlag(objaEnum_IKernelExpressionIniSolverZZZ, true);
		assertTrue("Das Flag USEEXPRESSION wurde erwartet",baReturn1[0]);//Ohne das Flag wird die Behandlung irgendwelcher Ausdrücke gar nicht gemacht.
		
//		IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniZZZ = new IKernelZFormulaIniZZZ.FLAGZ[1];
//		objaEnum_IKernelZFormulaIniZZZ[0]=IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA;
//		boolean[] baReturn2 = objKernelTest.setFlag(objaEnum_IKernelZFormulaIniZZZ, true);
//		assertTrue("Das Flag USEFORMULA wurde erwartet",baReturn2[0]);//Ohne das Flag wird <z>...</z> um das Ergebnis ggfs. rum sein.
		
		//Das reicht noch nicht, unbeding den Cache leeren, sonst wird das unverschlüsselte Objekt zurueckgeliefert
		objKernelTEST.getCacheObject().clear();
		
		//Jetzt erst wird der neue Wert auch mit "Entschluesselung" geholt
		sValue = objKernelTEST.getParameter("TestGetParameter_Encrypted").getValue();
		sValue = StringZZZ.left(sValue+"|","|");
		assertEquals("Test erfolgreich!", sValue);
		
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
}

/** void
 * Voraussetzung ist: Vorhandensein einer entsprechenden Modul-Konfigurations-.ini-Datei. Siehe testModuleExists()
* Lindhauer; 20.04.2006 07:49:38
 */
public void testGetParameterByModuleAlias(){
	
	//####################################
	//#### TESTS VORWEG
	String sModuleName = "TestModule";
	try{		
		File objFile = objKernel4ModuleReadonlyFGL.getFileConfigModuleOLDDIRECT(sModuleName);
		assertNotNull(objFile);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	//Konfiguration im "Externen" Modul, d.h. in einer anderen Datei als der ApplicationKey
	String sModuleNameExtern = "TestModuleExtern";
	try{		
		File objFileExtern = objKernel4ModuleReadonlyFGL.getFileConfigModuleOLDDIRECT(sModuleNameExtern);
		assertNotNull(objFileExtern);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	//######################################################
	try{
		//Konfiguration im "Lokalen" Module
		// In der Modulkonfiguration soll dieser Eintrag existieren
		String stemp = objKernel4ModuleReadonlyFGL.getParameterByModuleAlias(sModuleName, "testModuleProperty").getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected 'TestModuleValue' as a value of property 'testModuleProperty'. Configured in the Module 'TestModule' of the Application 'FGL'", "TestModuleValue" , stemp);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	//+++++++++++++++++++++++++++++++++++++
	try{
		//a) Parameter per Methode holen
		String stemp = objKernel4ModuleReadonlyFGL.getParameterByModuleAlias(sModuleNameExtern, "testModulePropertyExtern").getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("TestModuleValueExtern", stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}				
}

public void testGetParameterByProgramAlias(){
	// !!!ACHTUNG / VORSICHT, DIE WERTE KÖNNEN AUS EINEM CACHE KOMMEN, darum vor jedem Test den Cache leeren!!!
	
	try{
		//A1) Übergabe als direkte Section testen
		String stemp = objKernelFGL.getParameterByProgramAlias("FGL!01_TestProg","testProgramProperty" ).getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try {
		int iClearedObjects = objKernelFGL.getCacheObject().clear();
		assertTrue(iClearedObjects>=1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
		
		
	try{
		//A2) Übergabe als direkte Section testen
		String stemp = objKernelFGL.getParameterByProgramAlias("TestModule", "FGL!01_TestProg","testProgramProperty" ).getValue();
		stemp = StringZZZ.left(stemp+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert local 4 program" , stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		

	//B1) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
			//!!! GROSS-/Keinschreibung ist NICHT relevant		
		 
	//Program nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es das angegebene Program nicht
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramNameNICHTVORHANDEN", "testProgramProperty").getValue(); 
		assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	
	//Nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es kein "testProgramName" mit der Property
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramPropertyNICHTVORHANDEN").getValue(); 
		assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	
	//Vorhanden Fall. Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [TestModule!01] definiert ist.
	//Reihenfolge des Sections! Der [TestProg] Eintrag muss hinter dem [FGL!01_TestProg] Eintrag
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramProperty4").getValue();
		stemp2 = StringZZZ.left(stemp2+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty4'. Configured in the 'TestModule' of the Application 'FGL'", "testwert4 local 4 program" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	//Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [FGL!01] und der Wert aber nur GLOBAL definiert ist.	
	try{		
		String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModule", "testProgramName", "testProgramPropertyGLOBAL").getValue();
		stemp2 = StringZZZ.left(stemp2+"|","|");
		assertEquals("Expected as a value of property 'testProgramProperty'. Configured in the 'TestModule' of the Application 'FGL'", "testwert global 4 progname SOLL GEFUNDEN WERDEN" , stemp2);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	
	}

public void testGetParameterByProgramAlias_ExternesModul() {
	//######################################################################
		//### TESTS IM "EXTERNEN MODUL"
		//### D.h. das Modul ist in einer anderen Datei definiert!!!
		//######################################################################
					
		try{
			//A1) Übergabe als direkte Section testen. Modulname, ProgramName und Systemnummer werden daraus gezogen.
			//    Der Programname wird dann sogar noch in einen Alias umgewandelt.
			String stemp = objKernelFGL.getParameterByProgramAlias("TestModuleExtern!01_TestProgramName","testProgramPropertyExtern4" ).getValue();
			stemp = StringZZZ.left(stemp,"|");			
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		try {
			int iClearedObjects = objKernelFGL.getCacheObject().clear();
			assertTrue(iClearedObjects>=1);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
		try{
			//A2) Übergabe als direkte Section testen
			String stemp = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "TestProgExtern","testProgramPropertyExtern4" ).getValue();
			stemp = StringZZZ.left(stemp+"|","|");
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		
		//B1) Übergabe als Programname testen. 20061021 nun muss der Wert gefunden werden, auch wenn der Programalias ohne Systemnumber angegeben wird
		//!!! GROSS-/Keinschreibung ist NICHT relevant		
	 
		//Program nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es das angegebene Program nicht
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramNameNICHTVORHANDEN", "testProgramProperty").getValue();
			assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}

		//Nicht vorhanden Fall!!! D.h. in der TestModule-Datei gibt es kein "testProgramName" mit der Property
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramName", "testProgramPropertyNICHTVORHANDEN").getValue();			
			assertNull("No Value should have been found for the NICHTVORHANDEN program", stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
			
		//Vorhanden Fall. Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [TestModule!01] definiert ist.
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleExtern", "testProgramName", "testProgramPropertyExtern4").getValue();
			stemp2 = StringZZZ.left(stemp2+"|","|");
			assertEquals("Expected as a value of property 'testProgramPropertyExtern4'. Configured in the 'TestModuleExtern' of the Application 'FGL'", "testwertextern by progalias GLOBAL" , stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
		
		//Hier ist also 'testProgramName' ein Parameter, für den ein Alias in [FGL!01] und der Wert aber nur GLOBAL definiert ist.
		//Merke: Wird das Modul als Alias definiert, muss irgenwo auch der Modulkonfigurationsstring stehen: KernelConfigFileTestModuleAliasExtern, und wenn es eben in einer Section mit dem Modulalias selbst ist
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleAliasExtern", "testProgramName", "testProgramProperty5").getValue();
			assertNull("NULL erwartet. Wert ist aber '" + stemp2 + "'", stemp2); //es heiss testProgramNameExtern. Mit dem anderen Programnamen soll nichts gefunden werden.
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
				
		try{		
			String stemp2 = objKernelFGL.getParameterByProgramAlias("TestModuleAliasExtern", "testProgramNameExtern", "testProgramPropertyExtern5").getValue();
			stemp2 = StringZZZ.left(stemp2+"|","|");			
			assertEquals("Expected as a value of property 'testProgramPropertyExtern5'. Configured in the 'TestModule' of the Application 'FGL'", "TestModuleValueExtern by modulealias local SOLL GEFUNDEN WERDEN" , stemp2);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}	
}


public void testSearchAliasForProgram() {
	try {
		//TestProgramName=TestProg
		String sProgramAlias = objKernelFGL.searchAliasForProgram("TestProgramName");
		assertEquals("TestProg", sProgramAlias);
			
		String sProgramAliasUnavailable = objKernelFGL.searchAliasForProgram("TestProgramNameUNAVAILABLE");
		assertNull("NULL erwartet. Wert ist aber '" + sProgramAliasUnavailable + "'", sProgramAliasUnavailable); 
		
		//Test mit Modul in externer Datei
		String sProgramAliasExtern = objKernelFGL.searchAliasForProgram("TestModuleExtern", "TestProgramName");
		assertEquals("TestProgExtern", sProgramAliasExtern);				
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testSearchAliasForModule() {
	try {
		//Erst testen, dass auch kein Leerwert kommt
		String sModule = this.getClass().getName();//basic.zKernel.KernelZZZTest
		String sReturnAlias = objKernelFGL.searchAliasForModule(sModule);
		//System.out.println("TEST: "+sReturnAlias);//TestProg
		sReturnAlias = StringZZZ.left(sReturnAlias+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("TestProg", sReturnAlias);
		
		//+++ Externes Modul
		String sReturnAliasExtern = objKernelFGL.searchAliasForModule("TestModuleExtern");
		//System.out.println("TEST: "+sReturnAlias);//TestProg
		sReturnAliasExtern = StringZZZ.left(sReturnAliasExtern+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("TestModuleAliasExtern", sReturnAliasExtern);
		
	} catch (ExceptionZZZ ez) {
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

public void testGetProgramAliasFor() {
	try {
		String sClassname = this.getClass().getName();
		IKernelConfigSectionEntryZZZ objEntryAlias = null;
		
		//Negativfall, d.h. in den FGL Sections ist diese Testklasse nicht vorhanden!							
		objEntryAlias = objKernelTEST.getProgramAliasFor(sClassname);
		assertFalse(objEntryAlias.hasAnyValue());
	
		//Jetzt den Positivfall
		objEntryAlias = objKernelFGL.getProgramAliasFor(sClassname);
		assertTrue(objEntryAlias.hasAnyValue());
					
		String sAlias = objEntryAlias.getValue();
		sAlias = StringZZZ.left(sAlias+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertTrue(sAlias.equals("TestProg"));				
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias(){
	try {
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sProperty = "testProgramProperty4";
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwert4 local 4 program",sReturnSaved);
		
		//Testvariante A: Jetzt wird der Wert aber aus dem Cache geholt
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);		
	
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);
		
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//Vergleiche den String MIT der TIMESTAMP VARIANTE, also nicht: sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);	
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testSetParameterByProgramAlias_CreateSectionWhenNotExisist(){
	try {
		//String sModule = this.getClass().getName();
		String sModule = "SectionNotExistTest";
		String sProgram = "SectionNotExistJUnitTest";
		String sProgramAlias = "TestProg4SectionNotExist";
		String sProperty = "testProgramProperty4SectionNotExist";
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//### Erst testen, dass auch auf jeden Fall ein NULL kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertFalse(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		assertNull("Null als Ergebnis erwartet",sReturnSaved);
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("'null' als Ergebnis erwartet","null",sReturnSaved);
		
		//Merke: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		
		//### Nun das Setzen durchführen
		String sValueNotExisting = "testValue4SectionNotExist";
		String sValue = null;
		String sReturnSection = null;
		sValue = sValueNotExisting + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		
		//Wenn das Programm definiert ist, Section erstellen.
		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);
				
		//Merke: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		
		//### Testen, ob das Ergebnis kommt
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		sReturnSaved = objEntry.getValue();
		assertNotNull("Null als Ergebnis NICHT erwartet",sReturnSaved);
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("Als Ergebnis erwartet '" + sValueNotExisting + "'",sValueNotExisting,sReturnSaved);
		
		//ABER: Auch wenn die Section nicht existiert, wird der Wert in die Ini-Datei geschrieben. 
		//      Das Schreiben erfolgt dann auf Applikationsebene.
		//      Daher unbedingt in dem Rueckgabeentry auf den Section-Eintrag pruefen.
		sReturnSection = objEntry.getSection();
		//Nicht sofort prüfen, sonst abbruch... assertEquals("Der Wert wurde nicht in die definierte aber fehlende Section geschrieben '"+ sProgram + "'",sProgram,sReturnSection );
		//Es muss der Wert auf jeden Fall geloescht werden.
		if(!sProgramAlias.equals(sReturnSection)) {
		   //Den Wert loeschen und danach den fail werfen...
		   objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, null, true);
			
		   fail("Der Wert wurde zwar geschrieben, aber nicht in die definierte aber fehlende (und zu erstellende!) Section: '"+ sProgram + "'");
		}
		
		
		
		//Merke: Den Cache bei jedem Schritt explizit leeren
		//Ausser dieses Mal, denn der Wert soll nun aus dem Cache kommen... objKernelFGL.getCacheObject().clear();
		
		
		
		//Testvariante: Jetzt wird der Wert aber aus dem Cache geholt
//		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
//		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
//		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		sReturnSaved = objEntry.getValue();
		assertNotNull("Null als Ergebnis NICHT erwartet",sReturnSaved);
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("Als Ergebnis erwartet '" + sValueNotExisting + "'",sValueNotExisting,sReturnSaved);
				
	//############################################################################
	//### Aufraeumen
	//############################################################################
	//TODOGOON 20230408: Nun wieder die Section komplett loeschen. Momentan wird nur der Eintrag geloescht.
		 objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, null, true);
	//TODOGOON: Das muss als Methode für FileIniZZZ entwickelt werden.
		
		
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
//		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
//		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
//		objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sProperty, sValue, true);
		
		
		//Pruefen, ob nach dem Test die Section und der Wert auch weg sind.
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertFalse(objEntry.hasAnyValue());
		sReturnSaved = objEntry.getValue();
		assertNull("Null als Ergebnis erwartet",sReturnSaved);
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("'null' als Ergebnis erwartet","null",sReturnSaved);
		
		
	} catch (ExceptionZZZ ez) {
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_Encrypted_ROT13_ChangeValueA(){
	boolean bFlagExists;
	try {					
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sProperty = "testProgramPropertyEncryptedROT13";
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//#######################################################
		//### FALL A) Es gibt bereits einen verschluesselten Wert
		//###         Dann wird nach der erfolgreichen Suche das vorhandenen CRYPT-Objekt wiederverwendet
		//#######################################################
		
		bFlagExists = objKernelFGL.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
		assertTrue("Flag '"+IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);
		assertTrue("Flag '"+IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name()+ "' sollte vorhanden sein.",bFlagExists);
				
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
		assertTrue("Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		assertTrue("Flag '"+IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
		assertTrue("Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		//Aufruf der privaten Testmethode
		String sErg = testSetParameterByProgramAlias_Encrypted_ChangeValue_A_(objKernelFGL, sModule, sProgram, sProperty);
		assertEquals("<Z><Z:Encrypted><Z:Cipher>ROT13</Z:Cipher><Z:Code>grfgjreg4qrpelcgrq ybpny 4 cebtenz</Z:Code></Z:Encrypted></Z>",sErg);
		
		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
}


/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_Encrypted_ROT13_NewValueBa(){
	boolean bFlagExists;
	try {					
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sPropertyOld = "testProgramProperty_old_ForEncryption";
		String sPropertyEncryptedValue = "testProgramPropertyEncrypted_new_ROT13";
		
		//+++ Stetzen der notwendigen FlagZ-Eintraege
		bFlagExists = objKernelFGL.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
		assertTrue("Flag '"+IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);
		assertTrue("Flag '"+IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name()+ "' sollte vorhanden sein.",bFlagExists);
				
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
		assertTrue("Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		assertTrue("Flag '"+IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
		assertTrue("Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//Zuerst muss das gewuenschte CRYPT-Objekt erstellt werden, also der zu verwendende Crypt-Algorithmus.
		CryptAlgorithmFactoryZZZ objCryptFactory = CryptAlgorithmFactoryZZZ.getInstance();
		assertNotNull(objCryptFactory);
		ICryptZZZ objCrypt = objCryptFactory.createAlgorithmType(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13);
		assertNotNull(objCrypt);
		
		//Aufruf der privaten Testmethode
		String sEncrypted = testSetParameterByProgramAlias_Encrypted_NewValue1_Ba_(objKernelFGL, sModule, sProgram, sPropertyOld, sPropertyEncryptedValue, objCrypt);
		assertEquals("Mh irefpuyhrffryaqre Jreg",sEncrypted);//den Wert hier mal konstant vorgeben.
		
		String sOriginal = testSetParameterByProgramAlias_Encrypted_NewValue2_Ba_(objKernelFGL, sModule, sProgram, sPropertyEncryptedValue, sEncrypted, objCrypt);
		assertEquals("Zu verschluesselnder Wert",sOriginal);
				
		String sEmpty = testSetParameterByProgramAlias_Encrypted_NewValueClear_(objKernelFGL, sModule, sProgram, sPropertyEncryptedValue, objCrypt);
		assertNull("NULL erwartet. Wert ist aber '" + sEmpty + "'", sEmpty);		
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_Encrypted_VIGENEREnn_NewValueBa(){
	boolean bFlagExists;
	try {					
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sPropertyOld = "testProgramProperty_old_ForEncryption";
		String sPropertyEncryptedValue = "testProgramPropertyEncrypted_new_VIGENEREnn";
		
		//+++ Stetzen der notwendigen FlagZ-Eintraege
		bFlagExists = objKernelFGL.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
		assertTrue("Flag '"+IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);
		assertTrue("Flag '"+IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name()+ "' sollte vorhanden sein.",bFlagExists);
				
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
		assertTrue("Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		assertTrue("Flag '"+IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
		assertTrue("Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//Zuerst muss das gewuenschte CRYPT-Objekt erstellt werden, also der zu verwendende Crypt-Algorithmus.
		CryptAlgorithmFactoryZZZ objCryptFactory = CryptAlgorithmFactoryZZZ.getInstance();
		assertNotNull(objCryptFactory);
		ICryptZZZ objCrypt = objCryptFactory.createAlgorithmType(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.VIGENEREnn);
		assertNotNull(objCrypt);
		
		//Speziell fuer Vigenere - Verschluesselungen
		//Das soll abgebildet werden:
		//TestGetParameter_Encrypted=<Z><Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted></Z>
		objCrypt.setCryptKey("Hundi");
		objCrypt.setCharacterPoolBase(" abcdefghijklmnopqrstuvwxyz");
		objCrypt.setCharacterPoolAdditional("!");
		
		//USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER
		objCrypt.setFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEUPPERCASE.name(), true);
		objCrypt.setFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USENUMERIC.name(), true);
		objCrypt.setFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USELOWERCASE.name(), true);
		objCrypt.setFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEADDITIONALCHARACTER.name(), true);
		
		//Aufruf der privaten Testmethoden
		String sEncrypted = testSetParameterByProgramAlias_Encrypted_NewValue1_Ba_(objKernelFGL, sModule, sProgram, sPropertyOld, sPropertyEncryptedValue, objCrypt);
		assertEquals("vPnznZNqlu2zGwnTIriAHfsvC",sEncrypted);//den Wert hier mal konstant vorgeben.
		
		String sOriginal = testSetParameterByProgramAlias_Encrypted_NewValue2_Ba_(objKernelFGL, sModule, sProgram, sPropertyEncryptedValue, sEncrypted, objCrypt);
		assertEquals("Zu verschluesselnder Wert",sOriginal);
				
		String sEmpty = testSetParameterByProgramAlias_Encrypted_NewValueClear_(objKernelFGL, sModule, sProgram, sPropertyEncryptedValue, objCrypt);
		assertNull("NULL erwartet. Wert ist aber '" + sEmpty + "'", sEmpty);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
}
	
private String testSetParameterByProgramAlias_Encrypted_NewValue1_Ba_(IKernelZZZ objKernelUsed, String sModule, String sProgram, String sPropertyOld, String sPropertyEncryptedValue, ICryptZZZ objCrypt){
	String sReturn = null;
	try {
		
				//#######################################################
				//### FALL B) Es gibt noch keinen verschluesselten Wert.
				//###         D.h. es muss alles neu erstellt werden.
				//###     Ba) Den Wert vorher verschlusseln   
				//#######################################################
				
				//Erst den zu verschluesselnden Wert aus der INI-Datei auslesen						
				IKernelConfigSectionEntryZZZ objEntryOriginal = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sPropertyOld);
				assertTrue(objEntryOriginal.hasAnyValue());
				String sOriginal = objEntryOriginal.getValue();	
				sOriginal = StringZZZ.left(sOriginal+"|", "|");//Damit das ggfs. frühere Setzen eines Timestamps in der Property keinen Fehler erzeugt.
				assertEquals("Zu verschluesselnder Wert",sOriginal);
				
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++
				//### a) Vorher verschluesseln und diesen Wert setzen
				String sOriginalTimestamp = sOriginal + "|Timestamp: "+ DateTimeZZZ.computeTimestamp();//Hänge einen Zeitstempel an
				
				//### Einsatz des Crypt-Algorthmusses
//!!!!!         //Besonderheit: In diesem Test vorher verschluesseln				
				String sEncryptedTimestamp = objCrypt.encrypt(sOriginalTimestamp);
				assertNotNull(sEncryptedTimestamp);
				assertFalse("Originalwert und verschluesselter Wert sollten unterschiedliche sein",sOriginalTimestamp.equals(sEncryptedTimestamp));
				
				//Merke: Den Timestamp kann man in diesem Fall wieder herausholen, da | nicht verschluesselt werden sollte.
				//       Und nur der Wert ohne Timestamp bleibt ja immer fix.
				String sSeparator=null;
				CharacterExtendedZZZ objCharacterReplacement = objCrypt.getCharacterMissingReplacment();
				if(objCharacterReplacement!=null) {
					sSeparator = objCharacterReplacement.toString();
				}else {
					sSeparator = "|";
				}
				String sEncrypted = StringZZZ.left(sEncryptedTimestamp+sSeparator, sSeparator);//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
				assertNotNull(sEncrypted);
				
				//Merke: Auf einen konkreten Wert hier nicht abprüfen, da diese private Methode mehrmals mit anderen Werten aufgerufen wird.
				//Den gefundenen Wert zurückgeben
				sReturn = sEncrypted;
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getDetailAllLast());
			}
	return sReturn;	
}

private String testSetParameterByProgramAlias_Encrypted_NewValue2_Ba_(IKernelZZZ objKernelUsed, String sModule, String sProgram, String sPropertyEncryptedValue, String sEncryptedValue, ICryptZZZ objCrypt){
	String sReturn = null;
	try {
		
				//#######################################################
				//### Folgetest
				//### FALL B) Jetzt gibt es einen verschluesselten Wert 
				//###         und der muss wieder neu geschrieben werden.  
				//#######################################################
				
				//assertEquals("vPnznZNqlu2zGwnTIriAHfsvC",sEncrypted);//den Wert hier mal konstant vorgeben.
				String sEncryptedTimestamp = sEncryptedValue + "|Timestamp: "+ DateTimeZZZ.computeTimestamp();//Hänge einen Zeitstempel an
				objKernelUsed.setParameterByProgramAliasEncrypted(sModule, sProgram, sPropertyEncryptedValue, sEncryptedTimestamp, objCrypt);
				
				//+++ Den Cache bei jedem Schritt explizit leeren
				objKernelUsed.getCacheObject().clear();
				
				//+++ ... und wieder auslesen
				IKernelConfigSectionEntryZZZ objEntryDecrypted = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sPropertyEncryptedValue);
				assertTrue(objEntryDecrypted.hasAnyValue());
				
				String stemp = objEntryDecrypted.getRaw();
				assertNotNull(stemp);
				//Im RAW String wird der oben verwendete CIPHER-String erwartet.
				boolean btemp = StringZZZ.contains(stemp, objCrypt.getCipherType().getAbbreviation());//CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation());
				assertTrue("Der CIPHER String wird in der verschluesselten ini-Zeile erwartet: '"+ objCrypt.getCipherType().getAbbreviation() +"'",btemp);
				
				
				
				
				stemp = objEntryDecrypted.getValue();
				
				//Merke: Den Timestamp kann man in diesem Fall wieder herausholen, da | nicht verschluesselt werden sollte.
				//       Und nur der Wert ohne Timestamp bleibt ja immer fix.
				String sSeparator=null;
				CharacterExtendedZZZ objCharacterReplacement = objCrypt.getCharacterMissingReplacment();
				if(objCharacterReplacement!=null) {
					sSeparator = objCharacterReplacement.toString();
				}else {
					sSeparator = "|";
				}
				sReturn = StringZZZ.left(stemp+sSeparator, sSeparator);//Damit das ggfs. frühere Setzen eines Timestamps in der Property keinen Fehler erzeugt.
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getDetailAllLast());
			}
	return sReturn;	
}

private String testSetParameterByProgramAlias_Encrypted_NewValueClear_(IKernelZZZ objKernelUsed, String sModule, String sProgram, String sPropertyEncryptedValue, ICryptZZZ objCrypt){
	String sReturn = null;
	try {
		
				//#######################################################
				//### Folgetest
				//### FALL B) Jetzt den neu geschriebenen Wert loeschen  
				//#######################################################
				
				//+++ Den Cache bei jedem Schritt explizit leeren
				objKernelUsed.getCacheObject().clear();
				
				//++++++++++++++
				//Den Wert leersetzen.
				objKernelUsed.setParameterByProgramAlias(sModule, sProgram, sPropertyEncryptedValue, (String) null);
				
				//+++ Den Cache bei jedem Schritt explizit leeren
				objKernelUsed.getCacheObject().clear();
					
				//+++ Nun soll kein Wert gefunden werden
				IKernelConfigSectionEntryZZZ objEntryNichtMehrDa = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sPropertyEncryptedValue);
				assertFalse(objEntryNichtMehrDa.hasAnyValue());
				
				sReturn = objEntryNichtMehrDa.getValue();
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getDetailAllLast());
			}
	return sReturn;	
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_Encrypted_ROT13_NewValueBb(){
	boolean bFlagExists;
	try {					
		String sModule = this.getClass().getName();
		String sProgram = "TestProg";
		String sPropertyOld = "testProgramProperty_old_ForEncryption";
		String sPropertyEncryptedValue = "testProgramPropertyEncrypted_new_ROT13";
		
		//+++ Stetzen der notwendigen FlagZ-Eintraege
		bFlagExists = objKernelFGL.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
		assertTrue("Flag '"+IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER, true);
		assertTrue("Flag '"+IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER.name()+ "' sollte vorhanden sein.",bFlagExists);
				
		bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
		assertTrue("Flag '"+IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION, true);
		assertTrue("Flag '"+IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA, true);
		assertTrue("Flag '"+IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA.name()+ "' sollte vorhanden sein.",bFlagExists);
		
		
		//############################################
		//### "NICHT EXTERNES" MODUL
		//############################################
		//Teste das "Parameter Holen" auch für das "nicht externe", d.h. über den aktuellen Klassennamen angegebene Test Modul
		
		//#######################################################
		//### FALL B) Es gibt noch keinen verschluesselten Wert.
		//###         D.h. es muss alles neu erstellt werden.
		//###     Bb) Den Wert unverschluesselt uebergeben.   
		//#######################################################
		
		//Zuerst muss das gewuenschte CRYPT-Objekt erstellt werden, also der zu verwendende Crypt-Algorithmus.
		CryptAlgorithmFactoryZZZ objCryptFactory = CryptAlgorithmFactoryZZZ.getInstance();
		assertNotNull(objCryptFactory);
		ICryptZZZ objCrypt = objCryptFactory.createAlgorithmType(CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13);
		assertNotNull(objCrypt);
		
		String sErg = testSetParameterByProgramAlias_Encrypted_NewValue_Bb_(objKernelFGL, sModule, sProgram, sPropertyOld, sPropertyEncryptedValue, objCrypt);
		assertEquals("Zu verschluesselnder Wert",sErg);
		
		String sEmpty = testSetParameterByProgramAlias_Encrypted_NewValueClear_(objKernelFGL, sModule, sProgram, sPropertyEncryptedValue, objCrypt);
		assertNull("NULL erwartet. Wert ist aber '" + sEmpty + "'", sEmpty);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
}

/** void, Test: Replacing an entry in a section of the ini-file
 * @author Fritz Lindhauer, 13.08.2022, 08:46:20
 */
public void testSetParameterByProgramAlias_ExternesModule(){
	try {
		String sModuleExtern = "TestModuleExtern";
		
		String sProgram = "TestProgExtern";
		String sProperty = "testProgramPropertyExtern4";
		
		//##########################################
		//### EXTERNES MODUL
		//##########################################
		//Teste das "Parameter Holen" auch für oben angegebenes "externe" Test Modul
		
		//Erst testen, dass auch kein Leerwert kommt			
		IKernelConfigSectionEntryZZZ objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());
		String sReturnSaved = objEntry.getValue();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwertextern by progalias GLOBAL",sReturnSaved);
		
		//Testvariante A: Jetzt wird der Wert aber aus dem Cache geholt
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP WERT.
		String sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModuleExtern, sProgram, sProperty, sValue, true);				
		objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);		
	
		//Testvariante B: Den Cache bei jedem Schritt explizit leeren
		objKernelFGL.getCacheObject().clear();
		sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//STRING OHNE DEN TIMESTAMP TEIL!!!
		sValue = sReturnSaved + "|Timestamp: "+DateTimeZZZ.computeTimestamp();
		objKernelFGL.setParameterByProgramAlias(sModuleExtern, sProgram, sProperty, sValue, true);
		
		objKernelFGL.getCacheObject().clear();
		objEntry = objKernelFGL.getParameterByProgramAlias(sModuleExtern, sProgram, sProperty);
		assertTrue(objEntry.hasAnyValue());		
		sReturnSaved = objEntry.getValue();
		//sReturnSaved = StringZZZ.left(sReturnSaved+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals(sValue,sReturnSaved);	
		
	} catch (ExceptionZZZ ez) {
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

private String testSetParameterByProgramAlias_Encrypted_ChangeValue_A_(IKernelZZZ objKernelUsed, String sModule, String sProgram, String sProperty) {
	String sReturn = null;
	
	String sValueAsExpression=null;
	try {

		//+++ Den Cache bei jedem Schritt explizit leeren
		objKernelUsed.getCacheObject().clear();				
		
		//Erst testen, dass auch durch Verschluesselung kein Leerwert kommt		
		IKernelConfigSectionEntryZZZ objEntryDecryptedOriginal = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntryDecryptedOriginal.hasAnyValue());
		String sDecryptedOriginal = objEntryDecryptedOriginal.getValue();
		sDecryptedOriginal = StringZZZ.left(sDecryptedOriginal+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		assertEquals("testwert4decrypted local 4 program",sDecryptedOriginal);

		//Sichern des "RAW" Werts
		String sRawEncrypted = objEntryDecryptedOriginal.getRawEncryptedAsExpression();
		String sRaw =  objEntryDecryptedOriginal.getRawAsExpression();
		assertEquals(sRaw,sRawEncrypted);//Vor der Entschluesselung wird der RAW String hier noch "archiviert".
				
		//Im RAW String wird der oben verwendete CIPHER-String erwartet.
		boolean btemp = StringZZZ.contains(sRaw, CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation());
		assertTrue("Der CIPHER String wird in der verschluesselten ini-Zeile erwartet: '"+ CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation() +"'",btemp);
		
		//Testen der entschluesselten Werte		
		sValueAsExpression = objEntryDecryptedOriginal.getValueAsExpression();
		assertNotNull(sValueAsExpression);
		
		//####################################
		//+++ Den Cache bei jedem Schritt explizit leeren
		objKernelUsed.getCacheObject().clear();
						
		//Funktioniert nur, wenn es schon einen CRYPT-Tag gibt in der Property. Dann wird der gefundene Crypt-Algorithmus wiederverwendet.
		ICryptZZZ objCrypt = objEntryDecryptedOriginal.getCryptAlgorithmType();
		sDecryptedOriginal = objEntryDecryptedOriginal.getValue();
		sDecryptedOriginal = StringZZZ.left(sDecryptedOriginal+"|", "|");//Damit das Setzen des Timestamps in der Property keinen Fehler erzeugt.
		String sDecryptedTimestamp = sDecryptedOriginal + "|Timestamp: "+ DateTimeZZZ.computeTimestamp();//Hänge einen Zeitstempel an		
		String sEncrypted = objCrypt.encrypt(sDecryptedOriginal);
		String sEncryptedTimestamp = sEncrypted + "|Timestamp: "+ DateTimeZZZ.computeTimestamp();//Hänge einen Zeitstempel an  
		
		//ACHTUNG!!! DAS SETZT NUR DEN VERSCHLUESSELTEN WERT, OHNE DIE ARGUMENTE		
		objKernelUsed.setParameterByProgramAlias(sModule, sProgram, sProperty, sEncryptedTimestamp);  

		//########################################
		//+++ Den Cache bei jedem Schritt explizit leeren
		objKernelUsed.getCacheObject().clear();
		
		IKernelConfigSectionEntryZZZ objEntryDecrypted = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntryDecrypted.hasAnyValue());
		String stemp = objEntryDecrypted.getValue();
		assertTrue(sEncryptedTimestamp.equals(stemp));
		
		//Testen der entschluesselten Werte (nach dem Entschluesseln nicht mehr Null, wert wird im "wrapup" vom postSolve gesetzt)
		sValueAsExpression = objEntryDecryptedOriginal.getValueAsExpression();
		assertNotNull(sValueAsExpression);
				
		assertFalse(sRaw.equals(sValueAsExpression));              //also der entschluesselte Werte darf nicht dem Raw Wert entsprechen
		assertFalse(sValueAsExpression.equals(sDecryptedOriginal));//um den Expression String sollte Z-Tag drum sein.		


		//##########################################
		//+++ Den Cache bei jedem Schritt explizit leeren
		objKernelUsed.getCacheObject().clear();
				
		//+++ Die CRYPT-TAG Einträge müssen statt des Einzelwerts neu geschrieben werden.
		//MERKE: DAS IST DIE BESONDERHEIT DIESES TESTS: ES WIRD DAS ORIGINAL CRYPT-OBJEKT NACH DER ERSTEN SUCHE WIEDERVERWENDET
		ICryptZZZ objCryptOriginal = objEntryDecryptedOriginal.getCryptAlgorithmType();//Das Crypt - Objekt kommt aus der vorherigen Abfrage, wird also wiederverwendet.
		//Merke: Die Verschluesselung wird nun ohne Timestamp geschrieben.
		sEncrypted = objCryptOriginal.encrypt(sDecryptedOriginal);
		objKernelUsed.setParameterByProgramAliasEncrypted(sModule, sProgram, sProperty, sEncrypted, objCryptOriginal);
		
		//############################################
		//+++ Den Cache bei jedem Schritt explizit leeren
		objKernelUsed.getCacheObject().clear();
		
		//Prüfe das oben gesetzte Ergebnis PUR, also USEEXPRESSION auf false
		//TODOGOON20230325;//Das Flag USEEXPRESSION=false wird ignoriert, wenn es nur am Kernel-Objekt geändert wird. 
        //Es wird dann trotzdem der entschluesselte Wert zurückgegeben!!!
		//GRUND: Das intern verwendete KernelFileIniZZZ - Objekt wird über das Setzen des Flags nicht informiert.
		//IDEE:  Erbende Flag-Objekte müssen sich registrieren und werden dann benachrichtigt, wenn das Flag gesetzt wird (Listener Prinzip)
		//Merke: Das ist nicht mehr notwendig, wenn das Registrieren am Flag-Event klappt:
		//       objKernelFGL.getFileConfigKernelIni().setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION, false);

		objKernelUsed.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, false);

		IKernelConfigSectionEntryZZZ objEntryRaw = objKernelUsed.getParameterByProgramAlias(sModule, sProgram, sProperty);
		assertTrue(objEntryRaw.hasAnyValue());
		String stest = objEntryRaw.getValue();
		assertEquals(sRaw,stest);//Also der String muss nach all der Transformation identisch sein.
		
		stest = objEntryRaw.getRaw();
		assertNotNull(stest);
		assertEquals(sRaw,stest);//Also der String muss nach all der Transformation identisch sein.
		
		
		//Im RAW String wird der oben verwendete CIPHER-String erwartet.
		btemp = StringZZZ.contains(stest, CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation());
		assertTrue("Der CIPHER String wird in der verschluesselten ini-Zeile erwartet: '"+ CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation() +"'",btemp);

		sReturn = stest;
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
	return sReturn;
}

private String testSetParameterByProgramAlias_Encrypted_NewValue_Bb_(IKernelZZZ objKernelUsed, String sModule, String sProgram, String sPropertyOld, String sPropertyEncryptedValue, ICryptZZZ objCrypt){
	String sReturn = null;
	try {
		
		        //Erst den zu verschluesselnden Wert aus der INI-Datei auslesen						
				IKernelConfigSectionEntryZZZ objEntryOriginal = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sPropertyOld);
				assertTrue(objEntryOriginal.hasAnyValue());
				String sOriginal = objEntryOriginal.getValue();	
				sOriginal = StringZZZ.left(sOriginal+"|", "|");//Damit das ggfs. frühere Setzen eines Timestamps in der Property keinen Fehler erzeugt.
				assertEquals("Zu verschluesselnder Wert",sOriginal);

				//++++++++++++++++++++++++++++++++++++++++++++++++++++
				//### b) Den unverschluesselten Wert direkt uebergeben
				String sOriginalTimestamp = sOriginal + "|Timestamp: "+ DateTimeZZZ.computeTimestamp();//Hänge einen Zeitstempel an
		
//!!!!!!       //In diesem Test eben nicht vorher verschluesseln.
//				String sEncryptedTimestamp = objCrypt.encrypt(sOriginalTimestamp);
//				assertNotNull(sEncryptedTimestamp);					
//				objKernelFGL.setParameterByProgramAliasEncrypted(sModule, sProgram, sPropertyEncryptedValue, sOriginalTimestamp, objCrypt);
				
				//Anhand des uebergebenen Crypt-Algorithmus wird erkannt... verschluessele
				objKernelFGL.setParameterByProgramAlias(sModule, sProgram, sPropertyEncryptedValue, sOriginalTimestamp, objCrypt);
				
				//+++ Den Cache bei jedem Schritt explizit leeren
				objKernelFGL.getCacheObject().clear();
				
				//+++ ... und wieder auslesen
				IKernelConfigSectionEntryZZZ objEntryDecrypted = objKernelFGL.getParameterByProgramAlias(sModule, sProgram, sPropertyEncryptedValue);
				assertTrue(objEntryDecrypted.hasAnyValue());
				
				String stemp = objEntryDecrypted.getRaw();
				assertNotNull(stemp);
				
				//Im RAW String wird der oben verwendete CIPHER-String erwartet.
				boolean btemp = StringZZZ.contains(stemp, CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation());
				assertTrue("Der CIPHER String wird in der verschluesselten ini-Zeile erwartet: '"+ CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation() +"'",btemp);
				
				
				stemp = objEntryDecrypted.getValue();
				
				//Merke: Bei ROT13 ist sichergestellt, das | nicht verschluesselt wird.
				stemp = StringZZZ.left(stemp+"|", "|");//Damit das ggfs. frühere Setzen eines Timestamps in der Property keinen Fehler erzeugt.
				assertTrue(sOriginal.equals(stemp));
				
				sReturn = stemp;												
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getDetailAllLast());
	}
	return sReturn;
	}
	


public void testGetParameterFromClass_ALS_SAMMLUNG_VERSCHIEDENER_METHODEN(){
	
	//#########################################################################
	//Erst mal die Konfiguration per Klassennamen sicherstellen
	String sClassname = this.getClass().getName();

	try{		
		File objFile = objKernel4ModuleReadonlyFGL.getFileConfigModuleOLDDIRECT(sClassname);
		assertNotNull(objFile);
				
		FileIniZZZ objFileConfigIni = objKernelFGL.getFileConfigModuleIni(sClassname);
		assertNotNull(objFileConfigIni);
		objFile = objFileConfigIni.getFileObject();
		
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	try{
		//a) Parameter per Methode holen
		String stemp = objKernel4ModuleReadonlyFGL.getParameterByModuleAlias(sClassname, "TestParameter1FromClass").getValue();
		
		assertEquals("TestValue1FromClass", stemp);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
	//+++ STEHEN LASSEN AUS DOKUGRUENDEN
	//Hier wird derjetzt der GLOBALE Wert zurückgeliefert, das sollte frueher nicht sein...
	//Aktuell werden die Programnamen mit UNterstrich _ abgetrennt.	
//	try{
//		//b) Parameter aus dem Programm (das als Alias so aussieht wie der Klassenname) holen
//		String sAgentSection = objKernelFGL.getSystemKey() + "!" + sClassname;
//		String stemp2 = objKernelFGL.getParameterByProgramAlias(sClassname, sAgentSection, "TestParameter2FromClass").getValue(); 
//		assertEquals("TestValue2FromClass", stemp2);
//	}catch(ExceptionZZZ ez){
//		fail("An exception happend testing: " + ez.getDetailAllLast());
//	}	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//TODOGOON20220912;//Neuer GITHUB TOKEN
	try{
		//b2) Nun soll der Klassenname ohne den Systemkey funktionieren
		String stemp4 = objKernel4ModuleReadonlyFGL.getParameterByProgramAlias(sClassname, sClassname, "TestParameter2FromClass").getValue();
		
		assertEquals("TestValue2FromClass SOLL GEFUNDEN WERDEN", stemp4);   //Womit der Eintrag ein anderer wäre als der mit "SystemKey" - spezifizierte
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
		
	try{
		//C) "Verkürzte Parameter"
		//Wenn der Modulname und der Programname gleich sind, dann soll es moeglich sein ganz einfach nur den Programnamen und die gesuchte Property zu uebergeben
		String stemp6 = objKernel4ModuleReadonlyFGL.getParameterByProgramAlias(sClassname, "TestParameter1Abbreviated").getValue();
		
		assertEquals("TestValue1Abbreviated SOLL GEFUNDEN WERDEN", stemp6);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}	
	
}

public void testGetModuleAliasFromFilename(){
	try {
	assertEquals("MYTEST", KernelZZZ.computeModuleAliasByFilename("ZKernelConfigMYTEST_blabla.ini"));
	assertEquals("MYTEST", KernelZZZ.computeModuleAliasByFilename("ZKernelConfigMYTEST.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("ZKernelMyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("ZKernelxyzConfigMyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("MyNixTest.ini"));
	assertEquals("", KernelZZZ.computeModuleAliasByFilename("MyNixTest.txt"));
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

/** void, testet den Zugriff auf die Aliasnamen, welche der Kernel im Zugriff hat.
 * Dabei gibt es zwei Parameter:
 * 1) Konfiguriert
 * 2) Als Datei existent
* Lindhauer; 30.04.2006 09:14:41
 */
public void testGetModuleAliasAll(){
	try{
		//Hole alle Werte, die konfiguriert sind und existieren
	//		+++ Von den konfigurierten Modulen nur diejenige, die auch existieren.
		ArrayList listaAlias = objKernel4ModuleReadonlyFGL.getFileConfigModuleAliasAll(true, true);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias);
		assertFalse("There is no alias configured and no module does exist (arraylist empty case)", listaAlias.isEmpty()==true);
		assertTrue("There was the correct configuration expected of the module 'TestModule'. Application 'TEST'", listaAlias.contains("TestModule"));
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
	//	//+++ Von den konfigurierten Modulen diejenigen, deren Konfigurationsdatei fehlt
		//ArrayList listaAlias3 = objKernelTest.getFileConfigModuleAliasAll(false, true);
		ArrayList listaAlias3 = objKernel4ModuleReadonlyTEST.getFileConfigModuleAliasAll(false, true);
		assertNotNull("A configured module with missing configuraton file was expected (arraylist null case)", listaAlias3);
		assertFalse("A configured module with missing configuraton file was expected (arraylist empty case)", listaAlias3.isEmpty()==true);
		assertTrue("There was a missing configuration file expected. Application 'TEST'", listaAlias3.contains("NotExisting"));  //Das ist die Datei 'ZKernelConfigTestModuleNotExisting_test.ini'
	    assertFalse("There was only one missing configuration file expected. Application 'TEST", listaAlias3.size() > 1);
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
    	
	try{
		//+++ Von den in dem Kernel-Verzeichnis existierenden Moduldateien, diejenigen, deren Konfiguration in dieser "Hauptkernelkonfiguration" (noch) nicht erfolgt ist.
		ArrayList listaAlias2 = objKernel4ModuleReadonlyTEST.getFileConfigModuleAliasAll(true, false);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias2);
		assertFalse("There is no file found wich is not configured (use a dummy configuration file at least. Starting the filename with 'ZKernelConfig ....' (arraylist empty case)", listaAlias2.isEmpty()==true);
		assertTrue("The configuration file for the module  'ExistingButNotConfigured' should exist. But was expected to be NOT configured in the applicationalias 'TEST'", listaAlias2.contains("ExistingButNotConfigured")); //Das KernelModul wird in der Applikation FGL konfiguriert als Section der Ini-Datei:  [FGL#01]
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	try{
		//+++ Alle Module, sowohl aus der Konfiguration als auch aus dem Kernel-Verzeichnis 
		//ArrayList listaAlias4 = objKernelTest.getFileConfigModuleAliasAll(false, false);
		ArrayList listaAlias4 = objKernel4ModuleReadonlyTEST.getFileConfigModuleAliasAll(false, false);
		assertNotNull("There is no alias configured and no module does exist (arraylist null case)", listaAlias4);
		assertFalse("There is no alias configured and no module does exist (arraylist empty case)", listaAlias4.isEmpty()==true);
		assertTrue("The module 'Kernel' was expected to be NOT configured in the application 'TEST' but a file should be there", listaAlias4.contains("Kernel")); //Das KernelModul wird in der Applikation FGL konfiguriert als Section der Ini-Datei:  [FGL#01]
		assertTrue("The module 'NotExisting' was expected to be configured, but a file should be not there. Application 'TEST'", listaAlias4.contains("NotExisting"));  //Das ist die Datei 'ZKernelConfigTestModuleNotExisting_test.ini'
	    assertTrue("The module 'Test' was expected to be configured PLUS an existing file. Application 'TEST'", listaAlias4.contains("Test"));	
	}catch(ExceptionZZZ ez){
		fail("An exception happend testing: " + ez.getDetailAllLast());
	}
	
	}

	public void  testGetSectionAliasFor() {
		try {
			String sClassname = this.getClass().getName();
			IKernelConfigSectionEntryZZZ objEntryAlias = null;
			
			//Negativfall							
			objEntryAlias = objKernelTEST.getSectionAliasFor(sClassname);
			assertFalse(objEntryAlias.hasAnyValue());
		
			//Jetzt den Positivfall
			objEntryAlias = objKernelFGL.getSectionAliasFor(sClassname);
			assertTrue(objEntryAlias.hasAnyValue());
						
			String sAlias = objEntryAlias.getValue();
			assertTrue(sAlias.equals("TestProg"));						
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetParameterArrayWithStringByProgramAlias() {
		try {
			boolean bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag wurde erwartet: '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'",bFlagExists);
			bFlagExists = objKernelFGL.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag wurde erwartet: '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'",bFlagExists);
			bFlagExists = objKernelFGL.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);
			assertTrue("Flag wurde erwartet: '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'",bFlagExists);
			
			String[]sa = objKernelFGL.getParameterArrayWithStringByProgramAlias("Test", "testGetParameterArrayByProgramAlias", "testValue01");
			assertNotNull(sa);
			assertFalse(ArrayUtilZZZ.isNull(sa));											
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetParameterHashMapWithStringByProgramAlias() {
		try {
			boolean bFlagExists = objKernelFGL.setFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION, true);
			assertTrue("Flag wurde erwartet: '" + IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION + "'",bFlagExists);
			
			bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER,true); //ohne aktivierten Parser keine SUBSTITUTION  
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER + "'", bFlagExists);

			bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH + "'", bFlagExists);
		
			bFlagExists = objKernelFGL.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE, true);			
			assertTrue("Flag nicht vorhanden '" + IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE + "'", bFlagExists);
			
			bFlagExists = objKernelFGL.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER, true);
			assertTrue("Flag nicht vorhanden '" + IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER + "'", bFlagExists);
						
			bFlagExists = objKernelFGL.setFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON, true);
			assertTrue("Flag wurde erwartet: '" + IKernelJsonIniSolverZZZ.FLAGZ.USEJSON + "'",bFlagExists);
			
			bFlagExists = objKernelFGL.setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP, true);
			assertTrue("Flag wurde erwartet: '" + IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP + "'",bFlagExists);
			
			HashMap<String,String> hm = objKernelFGL.getParameterHashMapWithStringByProgramAlias("Test", "testGetParameterHashMapStringByProgramAlias", "testValue01");
			assertNotNull(hm);
			assertFalse(hm.isEmpty());
									
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testGetParameterHashMapWithEntryByProgramAlias() {
		try {

			HashMapIndexedObjectZZZ<Integer,IKernelConfigSectionEntryZZZ>hm= objKernelFGL.getParameterHashMapWithEntryByProgramAlias("Test", "testGetParameterHashMapStringByProgramAlias", "testValue01");
			assertNotNull(hm);
			assertFalse(hm.isEmpty());
			
			//Wichtig: In den einzelnen EntryObjekten sollte jetzt immer ein anderer Alias sein....
			//........
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSearchModuleFileByProgramAlias() {
		try {

			String sModule="TestModuleExtern";
			String sProgramOrSection="TestProg";
			FileIniZZZ objFileIniModule = objKernel4ModuleReadonlyFGL.searchModuleFileWithProgramAlias(sModule, sProgramOrSection);
			assertNotNull(objFileIniModule);
			
			boolean bExists = FileEasyZZZ.exists(objFileIniModule.getFileObject());
			assertTrue(bExists);
			
			//Wichtig: In den einzelnen EntryObjekten sollte jetzt immer ein anderer Alias sein....
			//........
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	
}//END Class