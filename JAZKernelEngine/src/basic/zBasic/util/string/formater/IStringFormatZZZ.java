package basic.zBasic.util.string.formater;

import static basic.zBasic.reflection.IReflectCodeZZZ.sPOSITION_FILE_SEPARATOR;
import static basic.zBasic.reflection.IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
import static basic.zBasic.reflection.IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;
import static basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ.sENUMNAME;

import java.util.EnumSet;

import basic.zBasic.util.datatype.xml.XmlUtilUnensuredZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.reflection.position.ITagTypeClassNameZZZ;
import basic.zBasic.reflection.position.ITagTypeDateZZZ;
import basic.zBasic.reflection.position.ITagTypeThreadIdZZZ;
import basic.zBasic.reflection.position.ITagTypeFileNameZZZ;
import basic.zBasic.reflection.position.ITagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.ITagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.ITagTypeMethodZZZ;

public interface IStringFormatZZZ extends ITagTypeMethodZZZ, ITagTypeLineNumberZZZ, ITagTypeFileNameZZZ, ITagTypeFilePositionZZZ{ //verwenden von z.B. import static basic.zBasic.reflection.position.ITagTypeFileNameZZZ.*; geht nicht, weil ja die Konstanten alle gleich heissen. 
	//Merke: Es soll folgendes abgebildet werden, z.B.: 
	//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
	//       Dazu wird das Format als Anweisung in ein Array abgelegt, in dem dann die einzelnen Teilkomponenten ausgerechnet werden.
		
	
	//Merke 20260131: Stand jetzt ist, dass sPOSITION_MESSAGE_SEPARATOR nur 1x in der Formatierungsanweisung vorkommen darf.
	//                Andere Formatierungsanweisungen können haeufiger verwendet werden. Z.B. auch um 2 Kommentare voneinander zu trennen.
	public static String sSEPARATOR_PREFIX_DEFAULT="";
	public static String sSEPARATOR_POSTFIX_DEFAULT="";
	public static String sSEPARATOR_MESSAGE_DEFAULT=IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
	public static String sSEPARATOR_POSITION_DEFAULT=IReflectCodeZZZ.sPOSITION_IN_FILE_SEPARATOR_LEFT + " "; //Leerzeichen, zwingend notwendig um die Position Clickbar in der Konsole zu machen.
																											 //Zwar ist in der Formatkonfiguration das Leerzeichen auch vorhanden, beim Normieren des Strings entfernt der Justifier aber diesen Leerstring  (halt überfluessige Leerzeichen wegtrimmen)
	                                                                                                         //Hole so das Leerzeichen wieder rein.
	
	
	public static String sSEPARATOR_01_DEFAULT="^";
	public static String sSEPARATOR_02_DEFAULT="° ";
	public static String sSEPARATOR_03_DEFAULT=IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;
	public static String sSEPARATOR_04_DEFAULT=" | ";
	
	
	
	//Merke: TODO Idee ist es das Custom-Format in einer einzigen Zahl zu definieren.
	//Merke: Jedes dieser Argumente ist als Primzahl zu defnieren.
	//       Es gilt Positionswert = Position * Primzahl
	public static int iARG_CONTROL = 0;
	public static int iARG_STRING = 1;
	public static int iARG_SYSTEM = 2;
	public static int iARG_OBJECT = 3;	
	public static int iARG_STRINGHASHMAP = 5;
	public static int iARG_STRINGXML = 7;
	public static int iARG_CONTROLSTRING = 11;
	
	
	//Argumente der compute Methode (sofern vorhanden und != null)
	//TODO IDEE: Wenn man eine Zahl angibt, soll die Zusammenstellung des Formats definiert sein.
	//           Darum sind das alles Primzahlen...
	public static int iFACTOR_NULL_STRING=0;
	
	public static int iFACTOR_STRINGTYPE01_STRING_BY_STRING=1;
	public static int iFACTOR_STRINGTYPE02_STRING_BY_STRING=2;     
	public static int iFACTOR_STRINGTYPE03_STRING_BY_STRING=3;
	
	public static int iFACTOR_LINENEXT_STRING=5;
	
	public static int iFACTOR_CLASSNAME_STRING=7;
	public static int iFACTOR_CLASSNAMESIMPLE_STRING=11;
	public static int iFACTOR_CLASSFILENAME_STRING=13;
	
	public static int iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP=19;
	public static int iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP=23;
	public static int iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP=29;
	public static int iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP=31;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_CLASSMETHOD_XML_BY_XML=41;
	public static int iFACTOR_CLASSFILELINE_XML_BY_XML=43;
	public static int iFACTOR_CLASSFILENAME_XML_BY_XML=47;
	public static int iFACTOR_CLASSFILEPOSITION_XML_BY_XML=53;  //mit der Zeilenummer dahinter
	public static int iFACTOR_POSITIONCURRENT_XML_BY_XML=59;  //Wird aus ObjectZZZ.getPostionCalling() geholt. Z.B. <method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition>#
	
	public static int iFACTOR_DATE_XML_BY_HASHMAP=61;
	public static int iFACTOR_THREADID_XML_BY_HASHMAP=67;
	public static int iFACTOR_CLASSNAME_XML_BY_HASHMAP=71;
	public static int iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP=73;
	public static int iFACTOR_CLASSMETHOD_XML_BY_HASHMAP=79;
	public static int iFACTOR_CLASSFILELINE_XML_BY_HASHMAP=83;
	public static int iFACTOR_CLASSFILENAME_XML_BY_HASHMAP=89;
	public static int iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP=97;  //mit der Zeilenummer dahinter
	
	public static int iFACTOR_CLASSMETHOD_STRING_BY_XML=101;
	public static int iFACTOR_CLASSFILELINE_STRING_BY_XML=103;
	public static int iFACTOR_CLASSFILENAME_STRING_BY_XML=107;
	public static int iFACTOR_CLASSFILEPOSITION_STRING_BY_XML=113;  //mit der Zeilenummer dahinter
	public static int iFACTOR_POSITIONCURRENT_STRING_BY_XML=127;   //Wird aus ObjectZZZ.getPostionCalling() geholt. Z.B. <method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition>#	
	                                                               //... die Tags werden daraus entfernt.
	public static int iFACTOR_THREADID_STRING=131;
	public static int iFACTOR_DATE_STRING=137;
	
	public static int iFACTOR_THREADID_XML=139;
	public static int iFACTOR_DATE_XML=149;
	public static int iFACTOR_STRINGTYPE01_XML_BY_STRING=151;
	public static int iFACTOR_STRINGTYPE02_XML_BY_STRING=157;     
	public static int iFACTOR_STRINGTYPE03_XML_BY_STRING=163;
	public static int iFACTOR_CLASSNAME_XML=167;
	public static int iFACTOR_CLASSNAMESIMPLE_XML=173;
	public static int iFACTOR_CLASSFILENAME_XML=179;
	
	public static int iFACTOR_CONTROLMESSAGESEPARATOR_STRING=181;
	public static int iFACTOR_CONTROLMESSAGESEPARATOR_XML=191;
	public static int iFACTOR_CONTROL01SEPARATOR_STRING=193;
	public static int iFACTOR_CONTROL01SEPARATOR_XML=197;
	
	public static int iFACTOR_CONTROL02SEPARATOR_STRING=199;
	public static int iFACTOR_CONTROL02SEPARATOR_XML=211;
	
	public static int iFACTOR_CONTROL03SEPARATOR_STRING=223;
	public static int iFACTOR_CONTROL03SEPARATOR_XML=227;
	
	public static int iFACTOR_CONTROL04SEPARATOR_STRING=229;
	public static int iFACTOR_CONTROL04SEPARATOR_XML=233;
	
	public static int iFACTOR_CONTROLPOSITIONSEPARATOR_STRING=239;
	public static int iFACTOR_CONTROLPOSITIONSEPARATOR_XML=241;
	
	public static int iFACTOR_CONTROLBORDERLEFT_SEPARATOR=251; //Die nicht in der Konfiguration anzugebenden Linke Spaltenbegrenzung.
	
	//Weitere Primzahlen sind:
	//11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59,61 ,67 71, 73, 79, 83, 89, 97 "Algorithmus ist 'Das Sieb des Eratosthenes'"
	//101,103,107,109,113,127,131,137,139,149,151,157,163,167,173,179,181,191,193,197,199,
	//211,223,227,229,233,239,241,251,257,263,269,271,277,281,283,293

	//Statt mehrere Strukturen zu pflegen, lieber eine enum, mit den Konfigurationswerten.
	//Merke: Methoden und Zeilennummern können nicht aus dem aktuellen Objekt ermittelt werden, daher sind sie von einem uebergebenen String abhaengig.
	//       POSITIONCALLING ist das entsprechende Format und kann fuer die Reihenfolge festgelegt werden.

	//Definition des Verhaltens:
	//String Typ  -> 1:1 den Wert uebernehmen mit PRE und POST Anweisung
	//HashMap Typ -> 1:1 den Wert uebernehmen mit PRE und POST Anweisung
	//XML Typ     -> Den Tag per definierten Type auslesen und als XML zurueckgeben.
	
	//Aufbau des Enum:
	//ALIAS("Uniquename bzw. Tag",Faktor, "Format... Merke %s für den String wert muss für String.format() sein",Kennzeichen des Argumenttyps,"PostfixSeparatorString", "Beschreibung, wird nicht genutzt....")	
	public enum LOGSTRINGFORMAT implements IEnumSetMappedStringFormatZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{		
	CONTROL_NULL_STRING_("null",IStringFormatZZZ.iFACTOR_NULL_STRING, "", "null", IStringFormatZZZ.iARG_CONTROL, "","Dummy Null Wert. Noch nicht benutzt!!!"),	
		
	STRINGTYPE01_STRING_BY_STRING("stringtype01",IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A01]", "%s",IStringFormatZZZ.iARG_STRING,  "[/A01]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
	STRINGTYPE02_STRING_BY_STRING("stringtype02",IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A02]", "%s",IStringFormatZZZ.iARG_STRING, "[/A02]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
	//STRINGTYPE03_STRING_BY_STRING("stringtype03",ILogStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING, ILogStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A03]", "%s",ILogStringFormatZZZ.iARG_STRING, sPOSITION_MESSAGE_SEPARATOR + "[/A03]" + ILogStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
	STRINGTYPE03_STRING_BY_STRING("stringtype03",IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A03]", "%s",IStringFormatZZZ.iARG_STRING, "[/A03]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),
	CONTROL_LINENEXT_("linenext",IStringFormatZZZ.iFACTOR_LINENEXT_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A05/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Teile an dieser Stelle die Formatanweisungen auf. Alles danach ist fuer eine Folgezeile. Ohne Ausgabe des aktuellen LogString."),				
	
	CLASSMETHOD_STRING_BY_HASHMAP("methodbystring",IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRING,"" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem  in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),		
	CLASSFILELINE_STRING_BY_HASHMAP("linenrbystring",IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRING, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
	CLASSFILENAME_STRING_BY_HASHMAP("filenamebystring",IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRING, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent()."),
	//Hier die Leerzeichen nicht verwenden. Die Position mit Leerzeichen kommt schon so in den String rein.... Dann ist noch ein Leerzeichen zuviel
	//CLASSFILEPOSITION_STRING_BY_HASHMAP("filepositionbystring",IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + " " + IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT, "%s",IStringFormatZZZ.iARG_STRING, IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT + " " + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent(). (Merke: Damit das in der Console clickbar ist Leerzeichen drum)."),		
	CLASSFILEPOSITION_STRING_BY_HASHMAP("filepositionbystring",IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "" + IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT, "%s",IStringFormatZZZ.iARG_STRING, IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT + "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei in diesem String Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrent(). (Merke: Damit das in der Console clickbar ist Leerzeichen drum, das wird aber schon im String bereitgestellt. Darf also hier nicht extra noch rein)."),
	
	
	CLASSMETHOD_XML_BY_XML("method",IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILELINE_XML_BY_XML("linenr",IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILENAME_XML_BY_XML("filename",IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILEPOSITION_XML_BY_XML("fileposition",IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei aus diesem XML-String in diesem XML-Tag Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),		
	POSITIONCURRENT_XML_BY_XML("positioncurrent", IStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s", IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "getPositionCurrent - Kann nur von aussen als String uebergeben werden. Wird geholt ueber ObjectZZZ.getPostitionCallingXml(). . Hat kuenstliches Tag <positioncurrent> und entaelt z.B. '<method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition># '."),
	
	DATE_XML_BY_HASHMAP("datebyhm",IStringFormatZZZ.iFACTOR_DATE_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeDateZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeDateZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das Datum in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
	THREADID_XML_BY_HASHMAP("threadidbyhm",IStringFormatZZZ.iFACTOR_THREADID_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeThreadIdZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeThreadIdZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das ThreadId in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
	CLASSNAME_XML_BY_HASHMAP("classnamebyhm",IStringFormatZZZ.iFACTOR_CLASSNAME_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeClassNameZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeClassNameZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Klassennamen in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
	CLASSNAMESIMPLE_XML_BY_HASHMAP("classnamesimplebyhm",IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeClassNameZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeClassNameZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Klassennamen (ohne Package) in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
	CLASSMETHOD_XML_BY_HASHMAP("methodbyhm",IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeMethodZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeMethodZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
	CLASSFILELINE_XML_BY_HASHMAP("linenrbyhm",IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeLineNumberZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeLineNumberZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
	CLASSFILENAME_XML_BY_HASHMAP("filenamebyhm",IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeFileNameZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeFileNameZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),
	CLASSFILEPOSITION_XML_BY_HASHMAP("filepositionbyhm",IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + XmlUtilUnensuredZZZ.computeTagPartOpening(ITagTypeFilePositionZZZ.sTAGNAME), "%s",IStringFormatZZZ.iARG_STRINGHASHMAP, XmlUtilUnensuredZZZ.computeTagPartClosing(ITagTypeFilePositionZZZ.sTAGNAME) + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei in diesem Format gespeichert in einer HashMap aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXmlFormated()."),		
	
	CLASSMETHOD_STRING_BY_XML("methodbyxml",IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den Methodennamen in diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILELINE_STRING_BY_XML("linenrbyxml",IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Codezeil in der Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILENAME_STRING_BY_XML("filenamebyxml",IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "", "%s",IStringFormatZZZ.iARG_STRINGXML, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml()."),
	CLASSFILEPOSITION_STRING_BY_XML("filepositionbyxml",IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + " " + IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT, "%s",IStringFormatZZZ.iARG_STRINGXML, IReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT + " " + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die errechnete Position in der Java-Datei aus diesem XML-String in diesem STRING Format aus, ermittelt in ReflectCodeZZZ.getPositionCurrentXml(). (Merke: Damit das in der Console clickbar ist Leerzeichen drum)."),		
	POSITIONCURRENT_STRING_BY_XML("positioncurrentbyxml", IStringFormatZZZ.iFACTOR_POSITIONCURRENT_STRING_BY_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT, "%s", IStringFormatZZZ.iARG_STRINGXML, IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "getPositionCurrent - Kann nur von aussen als String uebergeben werden. Wird geholt ueber ObjectZZZ.getPostitionCallingXml(). Hat kuenstliches Tag <positioncurrent> und entaelt z.B. '<method>searchDirectory</method><fileposition> (FileEasyZZZ.java:625) </fileposition># '. Als Rueckgabe-String werden diese Tags entfernt."),
	
	THREADID_STRING("threadid",IStringFormatZZZ.iFACTOR_THREADID_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[T]", "[Thread: %s]",IStringFormatZZZ.iARG_SYSTEM, "[/T]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die ID des Threads in diesem Format aus."),
	THREADID_XML("threadidxml",IStringFormatZZZ.iFACTOR_THREADID_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[TX]", "[Thread: %s]",IStringFormatZZZ.iARG_SYSTEM, "[/TX]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib die ID des Threads in diesem XML-Format aus."),
	DATE_STRING("date",IStringFormatZZZ.iFACTOR_DATE_STRING,IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[D]", "[%s]",IStringFormatZZZ.iARG_SYSTEM, "[/D]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das errechnete Datum in diesem Format aus."),		
	DATE_XML("datexml",IStringFormatZZZ.iFACTOR_DATE_XML,IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[DX]", "[%s]",IStringFormatZZZ.iARG_SYSTEM, "[/DX]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib das errechnete Datum in diesem XML-Format aus."),
	
	CLASSNAME_STRING("classname",IStringFormatZZZ.iFACTOR_CLASSNAME_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[C]", "%s",IStringFormatZZZ.iARG_OBJECT, IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT +  "[/C]", "Gib den Klassennamen mit Package in diesem Format aus."),
	CLASSNAME_XML("classnamexml",IStringFormatZZZ.iFACTOR_CLASSNAME_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CX]", "%s",IStringFormatZZZ.iARG_OBJECT, IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT +  "[/CX]", "Gib den Klassennamen mit Package in diesem XML-Format aus."),
	CLASSNAMESIMPLE_STRING("classnamesimple",IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CS]", "%s"  + sPOSITION_METHOD_SEPARATOR, IStringFormatZZZ.iARG_OBJECT, "[/CS]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den einfachen Klassennamen in diesem Format aus."),				
	CLASSNAMESIMPLE_XML("classnamesimplexml",IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CSX]", "%s"  + sPOSITION_METHOD_SEPARATOR, IStringFormatZZZ.iARG_OBJECT, "[/CSX]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den einfachen Klassennamen in diesem XML-Format aus."),	
	CLASSFILENAME_STRING("classfilename",IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CF]", "[File:%s]", IStringFormatZZZ.iARG_OBJECT, "[/CF]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT , "Gib den Dateinamen der Java-Klasse in diesem Format aus."),
	CLASSFILENAME_XML("classfilenamexml",IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[CFX]", "[File:%s]", IStringFormatZZZ.iARG_OBJECT, "[/CFX]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT , "Gib den Dateinamen der Java-Klasse in diesem XML-Format aus."),
	
	STRINGTYPE01_XML_BY_STRING("stringtype01xml",IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A01X]", "%s",IStringFormatZZZ.iARG_STRING,  "[/A01X]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem XML-Format aus."),
	STRINGTYPE02_XML_BY_STRING("stringtype02xml",IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A02X]", "%s",IStringFormatZZZ.iARG_STRING, "[/A02X]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem XML-Format aus."),
	STRINGTYPE03_XML_BY_STRING("stringtype03xml",IStringFormatZZZ.iFACTOR_STRINGTYPE03_XML_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A03X]", "%s",IStringFormatZZZ.iARG_STRING, sPOSITION_MESSAGE_SEPARATOR + "[/A03X]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem XML-Format aus."),

	CONTROL_SEPARATORMESSAGE_STRING("separatormessage",IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Kommentar-Separator. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATORMESSAGE_XML("separatormessagexml",IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Kommentar-Separator in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR01_STRING("separator01",IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 01. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR01_XML("separator01xml",IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 01 in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR02_STRING("separator02",IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 02. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR02_XML("separator02xml",IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 02 in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),		
	CONTROL_SEPARATOR03_STRING("separator03",IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 03. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR03_XML("separator03xml",IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 03 in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR04_STRING("separator04",IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 04. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATOR04_XML("separator04xml",IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator 04 in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),		
	CONTROL_SEPARATORPOSITION_STRING("separatorposition",IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator Fileposition (Merke: Damit das in der Console clickbar ist Leerzeichen drum). Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	CONTROL_SEPARATORPOSITION_XML("separatorpositionxml",IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A00X/]", "%s",IStringFormatZZZ.iARG_CONTROL, "" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Schreibe den Separator FilePosition (Merke: Damit das in der Console clickbar ist Leerzeichen drum) in diesem XML-Format. Damit wird das Ziel verbunden ggfs. etwas an dieser Stelle buendig im Log zu bekommen."),
	
	CONTROL_SEPARATORBORDERLEFT_STRING_("null",IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR, "", "null", IStringFormatZZZ.iARG_CONTROL, "","Dummy Null Wert. Z.B. fuer die linke Begrenzung, statt eines Separtors"),
	;		
		
	//#############################################
	//#### Konstruktoren
	//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
	//In der Util-Klasse habe ich aber einen Workaround gefunden.
	int iFactor, iArgumentType;
	private String sAbbreviation,sPrefixSeparator, sFormat,sPostfixSeparator, sDescription;
		
	LOGSTRINGFORMAT(String sAbbreviation, int iFactor, String sPrefixSeparator, String sFormat, int iArgumentType, String sPostfixSeparator, String sDescription) {
	    this.sAbbreviation = sAbbreviation;
	    this.iFactor = iFactor;
	    this.sPrefixSeparator = sPrefixSeparator;
	    this.sFormat = sFormat;
	    this.iArgumentType = iArgumentType;
	    this.sPostfixSeparator = sPostfixSeparator;
	    this.sDescription = sDescription;
	}
	
		public String getAbbreviation() {
			 return this.sAbbreviation;
			}
		
		public int getFactor() {
			return this.iFactor;
		}
		
		public String getPrefixSeparator() {
			return this.sPrefixSeparator;
		}
		
		public String getFormat() {
			 return this.sFormat;
		}
		
		public int getArgumentType() {
			return this.iArgumentType;
		}
		
		public String getPostfixSeparator() {
			return this.sPostfixSeparator;
		}
				
		public EnumSet<?>getEnumSetUsed(){
			return LOGSTRINGFORMAT.getEnumSet();
		}
	
		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<LOGSTRINGFORMAT> enumClass = LOGSTRINGFORMAT.class;
			EnumSet<LOGSTRINGFORMAT> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
					
			Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((LOGSTRINGFORMAT) obj);
			}
			return set;
			
		}
	
		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
		  EnumSet<E> set=EnumSet.noneOf(enumClass);
		  long mask=1;
		  for (  E e : enumClass.getEnumConstants()) {
		    if ((mask & vector) == mask) {
		      set.add(e);
		    }
		    mask<<=1;
		  }
		  return set;
		}
	
		//+++ Das könnte auch in einer Utility-Klasse sein.
		//the valueOfMethod <--- Translating from DB
		public static LOGSTRINGFORMAT fromAbbreviation(String s) {
		for (LOGSTRINGFORMAT state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}
		
		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}
	
		@Override
		public String toString() {//Mehrere Werte mit # abtennen
		    return this.sAbbreviation+"="+this.sDescription;
		}
	
		@Override
		public int getIndex() {
			return ordinal();
		}
	
		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}
	
		@Override
		public String getDescription() {
			return this.sDescription;
		}
		//+++++++++++++++++++++++++
	}//End internal Class
}
