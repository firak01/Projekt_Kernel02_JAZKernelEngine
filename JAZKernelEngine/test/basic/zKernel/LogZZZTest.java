package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerEnabledZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerUserZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zKernel.KernelContextZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.LogZZZ;
import junit.framework.TestCase;

public class LogZZZTest extends TestCase{
	private KernelZZZ objKernelTest;
	private LogZZZ objLogTest;
	
	private static final String strTEST_ENTRY01_DEFAULT = new String("bla");
	private static final String strTEST_ENTRY02_DEFAULT = new String("blub");
	
	
	protected void setUp(){
		try {
			//objKernelTest = new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",(String)null);
			objKernelTest = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String)null);
			
			/* damit wird logObject null und der test darf nicht weitergehen
			String[] a = {"init"};
			objKernelTest = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",a);		
			*/
			
			objLogTest = objKernelTest.getLogObject();
			
			//TestKonfiguration pruefen
			assertNotNull("LogObject provided by KernelObject is null.", objLogTest);
			assertFalse(objLogTest.getFlag("init")==true); //Nun waere init falsch
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testComputeLine_CUSTOM() {
		try {
			int itemp;
			String sLog1=null; String sLog2=null; String sLog3=null;
			String sValue=null; String sMid1=null; String sMid2=null; int iLine=0; String sFilePath=null; String sLinePosition=null; 
			String sValueExpectedStart=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bValue=false;
			
			boolean bStartsWith=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
			
			//Der in der Ausgabe verwendete Methodenname:
			String sLogPosition = "testComputeLine_CUSTOM"; //nicht ausrechnen, da das wieder Logzeilen produzieren wuerde
			
			IStringFormatManagerZZZ objFormatManager = null;
			
			//##############################################
			//ohne bestimmtes Format, verwendet wird also das als default im System vorhandene Format.
			//Merke: Das ist ohne File-Position-Angabe
			//##############################################
			
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.	
			
			objFormatManager = StringFormatManagerZZZ.getInstance();
			objLogTest.registerForFlagEventAdopted(objFormatManager);
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT, false);
			sValue = objLogTest.computeLine(this, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
						
			
			//##############################################
			//Hier die verschiedenen Custom-Formate durchspielen. 
			//Dann muss aber jedes Mal die Ueberpruefung des Ergebnisses eine andere sein. (anfang, mitte, ende).
			//Ueberlass das dem DEFAULT-Test
			//
			//wichtig ist hier nur, das jeder Log-Text 1x erscheint.
			//egal was man konfiguriert: CONTROL_SEPARATORMESSAGE_STRING ist nur 1x im Ergebnis.
			//                           Andere Separatoren können häufiger im Ergebnis sein.
			//###############################################
			
			//Das CUSTOM-Format			
			IEnumSetMappedStringFormatZZZ[]iaFormat= {
					 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR04_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORPOSITION_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
			 };
			
			//Die aktuelle Java-Datei
			sFilePath = ReflectCodeZZZ.getMethodCurrentFileName();
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.			
			sValue = objLogTest.computeLine(this,iaFormat, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet. Ist aber: " + itemp, bValue);
			
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);

			//++++++++++++++++++++++++++
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog2, sLog1); //auch wenn log2 kuerzer als log1 ist, erwarte ich dass die Ausgabe buendig ist 
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Zweizeilige Ausgabe
			itemp = StringZZZ.count(sValue, StringZZZ.crlf());
			bValue = (itemp==1); //!!!!!!!!!!!!!!!!!!!!!!!!!! hier 1x Zeilenumbruch wg. 2 Zeilen
			assertTrue("Mindestens/Nur 1x ein Zeilenumbruch '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet , ist aber " + itemp, bValue);
				
			//Nun 2x den Messageseparator(wg. 2 Zeilen)
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==2); //!!!!!!!!!!!!!!!!!!!!!!!!!! hier 2x wg. 2 Zeilen, wg. 2 Kommentaren
			assertTrue("Mindestens/Nur 2x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den LogString2
			itemp = StringZZZ.count(sValue, sLog2);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog2 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
						
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet , ist aber " + itemp, bValue);
			
			//########################################
			//### Noch einmal machen, nur einen kurzen Log-Text.
			//### Nun wirkt sich auch aus, das der PositionSeparator ~ verwendet wird, und dies bleibt bündig.
			sLog3 = "YTESTY";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog3); //auch wenn log3 kuerzer ist als alles zuvor, erwarte ich dass die Ausgabe buendig ist. Auch in der Spalte vor der CodePosition 
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog3);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog3 + "' erwartet", bValue);
						
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
	
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testComputeLine_DEFAULT() {
		try {
			String sLog1=null; String sLog2=null;
			String sValue=null; String sMid=null; String sMid1=null; String sMid2=null; int iLine=0; String sFilePath=null; String sLinePosition=null;
			String sValueExpectedStart=null; String sValueExpectedMid=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bStartsWith=false; boolean bMidFound=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
			
			//Der in der Ausgabe verwendete Methodenname:
			String sLogPosition = "testComputeLine_DEFAULT";//nicht ausrechnen, weil das ja wieder viele Logzeilen produzieren wuerde
			
			IStringFormatManagerZZZ objFormatManager = null;
			
			//##############################################
			
			//Die aktuelle Java-Datei
			String sClassFilePath = ReflectCodeZZZ.getClassFilePath(this);
			
			
			//#############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT, false); //also neue Zeilen bei mehr Kommentaren
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT, true);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_UNMERGED_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_REDUNDANT_FORMAT, false);
			
			objFormatManager = StringFormatManagerZZZ.getInstance();
			objFormatManager.adoptFlagZrelevantFrom(objLogTest);
			sValue = objLogTest.computeLine(this, sLog1);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T][CF][File:basic\\zKernel\\LogZZZTest.java][/CF][A00/]"; //ohne Datum, darum geht es mit dem Thread los
			sValueExpectedMid = "# [A01]XXXTESTLAENGERXXX[/A01]" ;
			sValueExpectedEnd = "[A00/]";
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);			
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			sMid = sMid.trim();
			assertEquals(sValueExpectedMid, sMid);
			
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			
			sValue = objLogTest.computeLine(this, sLog1, sLog2);
			//sValue = objLogTest.computeLine(this, sLog1, sLog2);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T][CF][File:basic\\zKernel\\LogZZZTest.java][/CF][A00/]"; //ohne Datum, darum geht es mit dem Thread los
			sValueExpectedMid = IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "[A01]XXXTESTLAENGERXXX[/A01][A00/]" ;
			sValueExpectedEnd = IStringFormatZZZ.sSEPARATOR_01_DEFAULT + "[A01]ZZZTESTZZZ[/A01]";
						
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);			
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			sMid = sMid.trim();
			assertEquals(sValueExpectedMid, sMid);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testComputeLine_DEFAULT_followed_by_CUSTOM(){
		try {
			//20260315: Hintergrund dieses Tests ist, dass aufgefallen ist, bei dem CUSTOM TEIL wird der KOMMENTAR-SEPARATOR viel zuweit nach rechts verschoben,  
			//          Die Umsortierung des neuen Formats funktioniert nicht. 
			//          Hier sollte also die reine CUSTOM - Version mit der bestehenden Reihenfolge der DEFAULT - Version gemischt werden.
			
			int itemp;
			String sLog1=null; String sLog2=null; String sLog3=null;
			String sValue=null; String sMid=null; String sMid1=null; String sMid2=null; int iLine=0; String sFilePath=null; String sLinePosition=null;
			String sValueExpectedStart=null; String sValueExpectedMid=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bStartsWith=false; boolean bMidFound=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
		
			boolean bValue=false;
				
			//##############################################
			//Der in der Ausgabe verwendete Methodenname:
			String sLogPosition = "testComputeLine_DEFAULT_followed_by_CUSTOM";//nicht ausrechnen, weil das ja wieder viele Logzeilen produzieren wuerde
			
			//Die aktuelle Java-Datei
			String sClassFilePath = ReflectCodeZZZ.getClassFilePath(this);
			
		
			//########### Dieser zusammengesetzte Test soll keine Einflüsse von aussen haben, also newInstance().
			IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getNewInstance();
			
			
			//####################################################
			//#### DER DEFAULT TEIL
			//####################################################
			
			//#############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT, false); //also neue Zeilen bei mehr Kommentaren
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT, true);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_UNMERGED_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_REDUNDANT_FORMAT, false);
			
			objFormatManager = StringFormatManagerZZZ.getInstance();
			objFormatManager.adoptFlagZrelevantFrom(objLogTest);
			sValue = objLogTest.computeLine(this, sLog1);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T][CF][File:basic\\zKernel\\LogZZZTest.java][/CF][A00/]"; //ohne Datum, darum geht es mit dem Thread los
			sValueExpectedMid = "# [A01]XXXTESTLAENGERXXX[/A01]" ;
			sValueExpectedEnd = "[A00/]";
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);			
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			sMid = sMid.trim();
			assertEquals(sValueExpectedMid, sMid);
			
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			
			sValue = objLogTest.computeLine(this, sLog1, sLog2);
			//sValue = objLogTest.computeLine(this, sLog1, sLog2);
			
			//Da man die Anzahl der zum Buendigmachen verwendeten Leerzeichen nicht kennt: Anfang und Ende vergleichen.
			sValueExpectedStart = "[T][Thread: 1][/T][CF][File:basic\\zKernel\\LogZZZTest.java][/CF][A00/]"; //ohne Datum, darum geht es mit dem Thread los
			sValueExpectedMid = IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "[A01]XXXTESTLAENGERXXX[/A01][A00/]" ;
			sValueExpectedEnd = IStringFormatZZZ.sSEPARATOR_01_DEFAULT + "[A01]ZZZTESTZZZ[/A01]";
						
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);			
			bStartsWith = StringZZZ.startsWith(sValue, sValueExpectedStart);
			assertTrue(bStartsWith);
			
			bEndsWith = StringZZZ.endsWith(sValue, sValueExpectedEnd);
			assertTrue(bEndsWith);
			
			sMid = StringZZZ.mid(sValue, sValueExpectedStart, sValueExpectedEnd);
			sMid = sMid.trim();
			assertEquals(sValueExpectedMid, sMid);
			
			//########################################################################
			//########################################################################
			//### NUN DER CUSTOM TEIL
			//########################################################################
			
			//##############################################
			//ohne bestimmtes Format, verwendet wird also das als default im System vorhandene Format.
			//Merke: Das ist ohne File-Position-Angabe
			//##############################################
			
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.	
			
			objFormatManager = StringFormatManagerZZZ.getInstance();
			objLogTest.registerForFlagEventAdopted(objFormatManager);
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT, false);
			sValue = objLogTest.computeLine(this, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
						
			
			//##############################################
			//Hier die verschiedenen Custom-Formate durchspielen. 
			//Dann muss aber jedes Mal die Ueberpruefung des Ergebnisses eine andere sein. (anfang, mitte, ende).
			//Ueberlass das dem DEFAULT-Test
			//
			//wichtig ist hier nur, das jeder Log-Text 1x erscheint.
			//egal was man konfiguriert: CONTROL_SEPARATORMESSAGE_STRING ist nur 1x im Ergebnis.
			//                           Andere Separatoren können häufiger im Ergebnis sein.
			//###############################################
			
			//Das CUSTOM-Format			
			IEnumSetMappedStringFormatZZZ[]iaFormat= {
					 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR04_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORPOSITION_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
			 };
			
			//Die aktuelle Java-Datei
			sFilePath = ReflectCodeZZZ.getMethodCurrentFileName();
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.			
			sValue = objLogTest.computeLine(this,iaFormat, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet. Ist aber: " + itemp, bValue);
			
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);

			//++++++++++++++++++++++++++
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog2, sLog1); //auch wenn log2 kuerzer als log1 ist, erwarte ich dass die Ausgabe buendig ist 
			System.out.println("LogZZZTest.testComputeLine_CUSTOM(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Zweizeilige Ausgabe
			itemp = StringZZZ.count(sValue, StringZZZ.crlf());
			bValue = (itemp==1); //!!!!!!!!!!!!!!!!!!!!!!!!!! hier 1x Zeilenumbruch wg. 2 Zeilen
			assertTrue("Mindestens/Nur 1x ein Zeilenumbruch '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet , ist aber " + itemp, bValue);
				
			//Nun 2x den Messageseparator(wg. 2 Zeilen)
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==2); //!!!!!!!!!!!!!!!!!!!!!!!!!! hier 2x wg. 2 Zeilen, wg. 2 Kommentaren
			assertTrue("Mindestens/Nur 2x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den LogString2
			itemp = StringZZZ.count(sValue, sLog2);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog2 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
						
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet , ist aber " + itemp, bValue);
			
			//########################################
			//### Noch einmal machen, nur einen kurzen Log-Text.
			//### Nun wirkt sich auch aus, das der PositionSeparator ~ verwendet wird, und dies bleibt bündig.
			sLog3 = "YTESTY";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog3); //auch wenn log3 kuerzer ist als alles zuvor, erwarte ich dass die Ausgabe buendig ist. Auch in der Spalte vor der CodePosition 
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog3);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog3 + "' erwartet", bValue);
						
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
	
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	//##########################################################
	
	public void testComputeLine_CUSTOM_unmergedFormat() {
		try {
			int itemp;
			String sLog1=null; String sLog2=null; String sLog3=null;
			String sValue=null; String sMid1=null; String sMid2=null; int iLine=0; String sFilePath=null; String sLinePosition=null; 
			String sValueExpectedStart=null; String sValueExpectedMid1=null; String sValueExpectedMid2=null;  String sValueExpectedEnd=null;
			boolean bValue=false;
			
			boolean bStartsWith=false; boolean bMid1found=false; boolean bMid2found=false; boolean bEndsWith=false;
			
			//Der in der Ausgabe verwendete Methodenname:
			String sLogPosition = "testComputeLine_CUSTOM_unmergedFormat";//nicht ausrechnen, weil das ja wieder viele Logzeilen produzieren wuerde
			
			
			//Das soll wieder unabhängig sein von vorherigen Tests, also newInstance();
			IStringFormatManagerZZZ objFormatManager = StringFormatManagerZZZ.getNewInstance();;
			
			//##############################################
			//ohne bestimmtes Format, verwendet wird also das als default im System vorhandene Format.
			//Merke: Das ist ohne File-Position-Angabe
			//##############################################
			
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.	
			
			objFormatManager = StringFormatManagerZZZ.getInstance();
			objLogTest.registerForFlagEventAdopted(objFormatManager);
			
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT, false);
			objLogTest.setFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_UNMERGED_FORMAT, true);
			sValue = objLogTest.computeLine(this, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
						
			
			//##############################################
			//Hier die verschiedenen Custom-Formate durchspielen. 
			//Dann muss aber jedes Mal die Ueberpruefung des Ergebnisses eine andere sein. (anfang, mitte, ende).
			//Ueberlass das dem DEFAULT-Test
			//
			//wichtig ist hier nur, das jeder Log-Text 1x erscheint.
			//egal was man konfiguriert: CONTROL_SEPARATORMESSAGE_STRING ist nur 1x im Ergebnis.
			//                           Andere Separatoren können häufiger im Ergebnis sein.
			//###############################################
			
			//Das CUSTOM-Format			
			IEnumSetMappedStringFormatZZZ[]iaFormat= {
					 IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILENAME_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR03_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_STRING_BY_XML,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATOR04_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,	
					 IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORPOSITION_STRING,
					 IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_STRING_BY_XML,				 
			 };
			
			//Die aktuelle Java-Datei
			sFilePath = ReflectCodeZZZ.getMethodCurrentFileName();
			
			//##############################################
			//Zeile mit 1x Logstring
			sLog1 = "XXXTESTLAENGERXXX";			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.			
			sValue = objLogTest.computeLine(this,iaFormat, sLog1);
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
						
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet. Ist aber: " + itemp, bValue);
			
			//Nur 1x den MessageSeparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);

			//++++++++++++++++++++++++++
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
			
			//###############################################
			//Zeile mit 2x Logstring
			sLog2 = "ZZZTESTZZZ";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog2, sLog1); //auch wenn log2 kuerzer als log1 ist, erwarte ich dass die Ausgabe buendig ist 
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den LogString2
			itemp = StringZZZ.count(sValue, sLog2);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog2 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet , ist aber " + itemp, bValue);
			
			//########################################
			//### Noch einmal machen, nur einen kurzen Log-Text.
			//### Nun wirkt sich auch aus, das der PositionSeparator ~ verwendet wird, und dies bleibt bündig.
			sLog3 = "YTESTY";
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLine(this, iaFormat, sLog3); //auch wenn log3 kuerzer ist als alles zuvor, erwarte ich dass die Ausgabe buendig ist. Auch in der Spalte vor der CodePosition 
			System.out.println("LogZZZTest."+sLogPosition+"(): Logausgabe in nächster Zeile.\n" + sValue);
			
			//Nur 1x den LogString1
			itemp = StringZZZ.count(sValue, sLog3);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog3 + "' erwartet", bValue);
						
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet, ist aber " + itemp, bValue);
			
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//Der Name der aufrufenden Klasse muss auch hierdrin sein, mit der Zeilennummer
			sLinePosition = ReflectCodeZZZ.formatFileCallingLineForConsole(sFilePath, iLine);//Neu ausrechnen. Merke: Das wäre mit deraktuellen Zeilennumer und das ist in diesem Fall falsch: ReflectCodeZZZ.getMethodCurrentNameLined();
			itemp = StringZZZ.count(sValue, sLinePosition);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x die Fileposition '" + sLinePosition + "' im Logstring '" + sValue + "' erwartet, ist aber " + itemp, bValue);
	
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
	
	//########################################################
	public void testGetPathDetailAll(){		
		try {
			assertEquals("c:\\fglKernel\\kernellog", objLogTest.getDirectory());
			assertEquals("ZKernelLog_test.txt", objLogTest.getFilename());	
			
		
			//20080106 Ein Log kann auch speziell auf Programmebene definiert werden
			KernelContextZZZ objContext = new KernelContextZZZ(this.getClass());
			KernelZZZ objKernelContext =new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini", objContext, (String)null);
			
			LogZZZ objLogContext = objKernelContext.getLogObject();
			assertEquals("c:\\fglKernel\\kernellog", objLogContext.getDirectory());
			assertEquals("ZKernelLog_LogZZZtest.txt", objLogContext.getFilename());	
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	
	public void testcomputeLineDate() {
		try {
			boolean btemp; int itemp;
			String sValue; boolean bValue;
			String sLog1; String sLog2; int iLine=0; String sFilePath=null; String sLinePosition=null;
			
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			
			//Die aktuelle Java-Datei
			sFilePath = ReflectCodeZZZ.getMethodCurrentFileName();
			
			
			//############################################
			sLog1 = strTEST_ENTRY01_DEFAULT;
			sLog2 = strTEST_ENTRY02_DEFAULT;
			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLineDate(this);
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			
			//0x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==0);
			assertTrue("Keinen LogString '" + sLog1 + "' erwartet", bValue);
			
			//0x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==0);
			assertTrue("Keinen Kommentarseparator erwartet '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			
			//################################################
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLineDate(this, sLog1); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (2) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
			//################################################
			sValue = objLogTest.computeLineDate(this, sLog1, sLog2); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDate(): (3) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur jeweils 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString1 '" + sLog1 + "' erwartet", bValue);
			
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString2 '" + sLog2 + "' erwartet", bValue);
			
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testcomputeLineDateWithPosition() {
		try {
			boolean btemp; int itemp;
			String sValue; boolean bValue;
			String sLog1;String sLog2; int iLine=0; String sFilePath=null; String sLinePosition=null;
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 
			//### Sonst muss man nachher noch dafuer sorgen, das diese spezielle Formatanweisung auch noch explizit hinzugefuegt wird,
			//### sollte sie fehlen.
			//##################################################################################
			
			//Die aktuelle Java-Datei
			sFilePath = ReflectCodeZZZ.getMethodCurrentFileName();
		
			//################################################################
			
			sLog1 = strTEST_ENTRY01_DEFAULT;
			sLog2 = strTEST_ENTRY02_DEFAULT;
			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLineDateWithPosition(this);
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//0x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==0);
			assertTrue("Keinen LogString '" + sLog1 + "' erwartet", bValue);
			
			//0x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==0);
			assertTrue("Keinen Kommentarseparator erwartet '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
			
			
			//########################################
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLineDateWithPosition(this, sLog1); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString '" + sLog1 + "' erwartet", bValue);
			
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
			//#########################################
			
			iLine = ReflectCodeZZZ.getMethodCurrentLine()+1;//+1, weil halt die naechste Zeile im Code.
			sValue = objLogTest.computeLineDateWithPosition(this, sLog1, sLog2); //Darin wird die Zeile schon "bündig gemacht".
			assertNotNull(sValue);
			System.out.println("LogZZZTest.testComputeLineDateWithPosition(): (1) In der nächsten Zeile steht der Testergebnis-String\n" + sValue);
			
			//Nur 1x den jeweiligen LogString
			itemp = StringZZZ.count(sValue, sLog1);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString1 '" + sLog1 + "' erwartet", bValue);
			
			itemp = StringZZZ.count(sValue, sLog2);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den LogString2 '" + sLog2 + "' erwartet", bValue);
						
			//Nur 1x den Messageseparator
			itemp = StringZZZ.count(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			bValue = (itemp==1);
			assertTrue("Mindestens/Nur 1x den Kommentarseparator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "' im Logstring '" + sLog1 + "' erwartet", bValue);
						
			//Nicht am Ende, sondern vor dem uebergebenen String.
			bValue = StringZZZ.endsWith(sValue, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
			assertFalse("Der Separator '" + IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT + "'  darf nicht am Ende stehen, wenn ein String für die Protokollierung übergeben wurde.", bValue);
			
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
		
			bValue = objLogTest.writeLineDate(strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPosition(){
		try {
			boolean bValue=false;
			
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
			//##################################################################################
			
			bValue = objLogTest.writeLineDateWithPosition(strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testWriteLineDateWithPositionXml(){
		try {
			boolean bValue=false;
						
			//##################################################################################
			//### Bei speziellen Anweisungen kein Formatierung-Style-Array uebergeben. 			
			//##################################################################################
			
			bValue = objLogTest.writeLineDateWithPositionXml("");
			assertTrue(bValue);
			
			bValue = objLogTest.writeLineDateWithPositionXml(strTEST_ENTRY01_DEFAULT); 
			assertTrue(bValue);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testConstructorWithDifferentLogFile() {
		try {
			String[]saArg=null; boolean bValue=false;
					
			saArg=new String[4];
			saArg[0]="-ld";
			saArg[1]="c:\\fglkernel\\kernellog";
			saArg[2]="-lf";
			saArg[3]="testLog_Constructor01.txt";
			IKernelConfigZZZ objConfig = new KernelConfigZZZ(saArg);
			LogZZZ objLog01 = new LogZZZ(objConfig);
			bValue=objLog01.writeLineDateWithPosition(this, strTEST_ENTRY01_DEFAULT);
			assertTrue(bValue);
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	
}
