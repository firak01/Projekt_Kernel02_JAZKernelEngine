package basic.zKernel;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.GetOptZZZ;
import junit.framework.TestCase;

public class GetOptZZZTest extends TestCase{
	private GetOptZZZ objOptTest;

	
	
	protected void setUp(){
		//try {
			//objKernel= new KernelZZZ("FGL", "01", "c:\\fglKernel\\KernelTest", "ZKernelConfigKernel_test.ini",null);
			
		/* damit wird logObject null und der test darf nicht weitergehen
			String[] a = {"init"};
			objKernelTest = new KernelZZZ("FGL", "01", "", "ZKernelConfigKernel_test.ini",a);		
			*/
			objOptTest = new GetOptZZZ();
			
			
		//} catch (ExceptionZZZ e) {
		//	fail("Method throws an exception." + e.getMessageLast());
		//}		
	}
	
	public void testGetPatternList4ControlWithValue() {
		try {
			String sPattern=null;
			ArrayList<String>listaPattern=null;
			
			//Liste Argumente mit Wert
			sPattern = "a:b:c:;";
			listaPattern = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//mal ein Argument "ohne Wert" einbauen, das wird jetzt für diese Liste ignoriert
			sPattern = "a:d|b:c:";
			listaPattern = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//pattern nur mit Argumenten "ohne Wert"
			//a) Fall nur 1x
			sPattern = "e";
			listaPattern = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//b) Fall Nx
			sPattern = "e|d|f|";
			listaPattern = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//Pattern mit "einfachem Argument" am Schluss, wird komplett ignoriert
			sPattern = "e|d|f|x";
			listaPattern = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
					
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testGetPatternList4ControlWithOptionalValue() {
		try {
			String sPattern=null;
			ArrayList<String>listaPattern=null;
			
			//Liste Argumente mit (optional) Wert
			sPattern = "a.b.c.;";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//mal ein Argument "ohne Wert" einbauen, das wird jetzt für diese Liste ignoriert
			sPattern = "a.d|b,c.";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(2,listaPattern.size());
			
			//pattern nur mit Argumenten "ohne Wert"
			//a) Fall nur 1x
			sPattern = "e";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//b) Fall Nix
			sPattern = "e|d|f|";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//Pattern mit "einfachem Argument" am Schluss, wird komplett ignoriert
			sPattern = "e|d|f|x";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//c) Fall Nix
			sPattern = "e:d:f:";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			//Pattern mit "einfachem Argument" am Schluss, wird komplett ignoriert
			sPattern = "e:d:f:x";
			listaPattern = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
					
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testGetPatternList4ControlSimple() {
		try {
			String sPattern=null;
			ArrayList<String>listaPattern=null;
			
			//Liste Argumente "ohne Wert"
			//a) Fall nur 1x
			sPattern = "e";
			listaPattern = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			assertNotNull(listaPattern);
			assertEquals(1,listaPattern.size());
			
			//b) Fall Nx
			sPattern = "a|b|c|;";
			listaPattern = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//mal ein Argument "mit Wert" einbauen, das wird jetzt für diese Liste ignoriert
			sPattern = "a|d:b|c|";
			listaPattern = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//pattern nur mit Argumenten "mit Wert"
			sPattern = "e:d:f:";
			listaPattern = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			assertNotNull(listaPattern);
			assertEquals(0,listaPattern.size());
			
			
			//Pattern mit einfachem Zeichen am Schluss, wird zu einem Argument "ohne Wert" 
			sPattern = "e:d:f:x";
			listaPattern = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			assertNotNull(listaPattern);
			assertEquals(1,listaPattern.size());
			assertEquals("x", listaPattern.get(0));
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	
	
	public void testGetPatternList4ControlAll() {
		try {
			String sPattern=null;
			ArrayList<String>listaPattern=null;
			
			//Liste Argumente "ohne Wert"
			sPattern = "a|b|c|;";
			listaPattern = GetOptZZZ.getPatternList4ControlAll(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			//mal ein Argument "mit Wert" einbauen, das wird jetzt für die Gesamt-Liste mit aufgenommen
			sPattern = "a|d:b|c|";
			listaPattern = GetOptZZZ.getPatternList4ControlAll(sPattern);
			assertNotNull(listaPattern);
			assertEquals(4,listaPattern.size());
			
			//pattern nur mit Argumenten "mit Wert"
			sPattern = "e:d:f:";
			listaPattern = GetOptZZZ.getPatternList4ControlAll(sPattern);
			assertNotNull(listaPattern);
			assertEquals(3,listaPattern.size());
			
			
			//Pattern mit einfachem Zeichen am Schluss, wird zu einem Argument "ohne Wert" 
			sPattern = "e:d:f:x";
			listaPattern = GetOptZZZ.getPatternList4ControlAll(sPattern);
			assertNotNull(listaPattern);
			assertEquals(4,listaPattern.size());
			assertEquals("e", listaPattern.get(0));
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	/**Fuer den Pattern String gilt: 1 Zeichen, ggf. gefolgt von einem Doppelpunkt
	 * Pruefe auf: 
	 * - doppelte Zeichen (au�er dem Doppelpunkt)
	 * - pruefe auf zwei hintereinander folgende Doppelpunkte
	 * 
	* lindhauer; 30.06.2007 08:21:33
	 */
	public void testIsPatternStringValid(){
		try{
			String stemp=null;
			String sResult=null;
			int itemp= 0;
			
			//##################################
			//#### DOPPELPUNKT
			//##################################
	
			stemp = ":a:bc:;";//Mit einem Doppelpunkt startend
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,3, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Argumente mit doppelten Buchstaben sind erlaubt im Pattern
			stemp = "aa:bc:";  //doppelte Buchstaben sind erlaubt, auch am Anfang
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
									
			stemp = "a:bc:dd"; //doppelte Buchsteben sind erlaubt, auch am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			stemp = "a:bbc:d"; //doppelt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Argumente im Pattern String sind nicht erlaubt
			stemp = "a:a:bc:";  
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Separatoren im Pattern String sind nicht erlaubt
			stemp = "a::bc:";  //doppelter Doppelpunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			stemp = "a:bc::";  //doppelter Doppelpunkt am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "a:bc:d"; //o.k.
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//##################################
			//#### PIPE
			//##################################
			stemp = "|a|bc|;";//Mit einem Doppelpunkt startend
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,3, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Argumente mit doppelten Buchstaben sind erlaubt im Pattern
			stemp = "aa|bc|";  //doppelte Buchstaben sind erlaubt, auch am Anfang
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
									
			stemp = "a|bc|dd"; //doppelte Buchsteben sind erlaubt, auch am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			stemp = "a|bbc|d"; //doppelt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Argumente im Pattern String sind nicht erlaubt
			stemp = "a|a|bc|";  
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Separatoren im Pattern String sind nicht erlaubt
			stemp = "a||bc|";  //doppelter Doppelpunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			stemp = "a|bc||";  //doppelter Doppelpunkt am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "a|bc|d"; //o.k.
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			//#################################
			//#### PUNKT
			//#################################
			stemp = ".a.bc.;";//Mit einem Doppelpunkt startend
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,3, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Argumente mit doppelten Buchstaben sind erlaubt im Pattern
			stemp = "aa.bc.";  //doppelte Buchstaben sind erlaubt, auch am Anfang
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
									
			stemp = "a.bc.dd"; //doppelte Buchsteben sind erlaubt, auch am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			stemp = "a.bbc.d"; //doppelt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Argumente im Pattern String sind nicht erlaubt
			stemp = "a.a.bc.";  
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Separatoren im Pattern String sind nicht erlaubt
			stemp = "a..bc.";  //doppelter Doppelpunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			stemp = "a.bc..";  //doppelter Doppelpunkt am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp = "a.bc.d"; //o.k.
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//#################################
			//#### GEMISCHT: DOPPELPUNKT, PIPE, PUNKT
			//#################################
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Argumente mit doppelten Buchstaben sind erlaubt im Pattern
			stemp = "aa:bc.";  //doppelte Buchstaben sind erlaubt, auch am Anfang
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
									
			stemp = "a.bc:dd"; //doppelte Buchsteben sind erlaubt, auch am Ende
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			stemp = "a|bbc:d"; //doppelt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Argumente im Pattern String sind nicht erlaubt
			stemp = "a:a.bc.";  
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,1, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			//doppelte Platzhalter im Pattern String sind nicht erlaubt
			stemp = "a.||bc:";  //doppelter Pipe in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			stemp = "a.::bc|";  //doppelter Doppepunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			stemp = "a:..bc|";  //doppelter Punkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,2, itemp);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Platzhalter auf Platzhalter im Pattern String ist nicht erlaubt
			stemp = "a.|bc:";  //doppelter Pipe in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,4, itemp);
			
			stemp = "a.:bc|";  //doppelter Doppepunkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,4, itemp);
			
			stemp = "a:.bc|";  //doppelter Punkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,4, itemp);
			
			stemp = "a:|bc|";  //doppelter Punkt in der Mitte
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,4, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++ Positivtest
			stemp = "a:bc.d"; //o.k.
			sResult = objOptTest.proofPatternString(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			
			
			
			
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testProofArgument(){
		try{
			String stemp=null;
			String sResult=null;
			int itemp= 0;
			
			objOptTest.setPattern("ssh|https|pat:z");//siehe IConfigDEV.sPATTERN_DEFAULT
			stemp = "-ssh";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:c:b");//Im Pattern, ohne Argumente nach hinten.
			stemp = "-a hallo -c Welt -b";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			stemp="-a hallo -b -c"; 
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60, itemp);//'c' als Steuerungszeichen erkannt, aber nix folgt.
			
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,10, itemp); //Fehler: 'Der vorherige Eintrag ist kein Steuerzeichen'
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b -c"; 
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,29, itemp);//Fehler: Error 29: Previous control character needs an argument, but this is: '-c'. Ergo: Missing argument for controlcharacter 'b'.
			
			stemp="-a hallo -b welt -c"; 
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60, itemp);//Fehler: Error 60: Last control character has no argument: '-c'
			
			stemp="-a hallo -b welt -c test"; //DAS IST DANN GUT
			sResult= objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			stemp="-a hallo -b -cc";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,9, itemp);//Error 09: Control characters found ('cc'), which are not in Pattern ('a:b:c:')
			
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b welt"; //ist auch valid, Normalfall
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			stemp="-a hallo -b welt -c zwei drei"; 
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,10, itemp); //Fehler: Error 10: Previous entry is no control character expecting an argument. Argument string: 'drei'
			
			//Aber in Hochkomma wäre das in Ordnung
			stemp="-a hallo -b welt -c 'zwei drei'"; 
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1, itemp);
			
			
			//+++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b|c:"); //Nun soll -cc falsch sein
			stemp="-a hallo -b -cc";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			//nun sind Steuerzeichen mit mehr als 1 Zeichen Laenge erlaubt assertEquals(20,itemp); //Fehler "Falsche L�nge des Steuerzeichens
			assertEquals(sResult,9,itemp); //Fehler "cc" ist nicht im Patter der Steuerzeichen vorhanden
			
			stemp="-a hallo -b -cc blabla";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			//nun sind Steuerzeichen mit mehr als 1 Zeichen Laenge erlaubt assertEquals(20, itemp);//Fehler: 'Falsche Laenge eines Steuerzeichens'
			assertEquals(sResult,9,itemp); //Fehler "cc" ist nicht im Patter der Steuerzeichen vorhanden
			
			stemp="-a hallo -b -d blabla";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,9, itemp);//Fehler: 'Steuerzeichen d nicht im pattern string'
			
			//###################
			//Test mit nur einem Steuerzeichen
			objOptTest.setPattern("a"); //Nun soll -cc falsch sein
			stemp="-a";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); 
			
			stemp="-b";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,9,itemp); //Steuerzeichen nicht im pattern string
			
			stemp = "-a welt";
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,12,itemp); //der vorherige Eintrag -a ist kein Steuerzeichen mit Argument
			
			//Nun hinter a ein Argument benoetigen
			objOptTest.setPattern("a:");
			stemp="-a";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60,itemp); //Steuerzeichen verlang Argument, aber nix folgt
			
			stemp="-a welt";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //o.k.
			
			//Nun hinter a ein optionales Argument
			objOptTest.setPattern("a.");
			stemp="-a";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //Steuerzeichen verlang nur optionales Argument
			
			stemp="-a welt";  
			sResult = objOptTest.proofArgument(stemp);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //o.k.
		
		    //##########################################
			//Test ohne ein  Steuerzeichen
			try{
				objOptTest.setPattern("");
				stemp="-a";  
				sResult = objOptTest.proofArgument(stemp);
				fail("Method should throw an exception: No pattern string but is '" + sResult + "'");
				
			}catch(ExceptionZZZ ez){
			}
			
			//Test ohne einen wert
    		stemp="";  
	    	sResult = objOptTest.proofArgument(stemp);
	    	itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //o.k.	
			
			
			//### Test mit leerem Argument fuer ein Steuerzeichen
			objOptTest.setPattern("a:");
			String[] saArg={"-a",""};
			sResult = objOptTest.proofArgument(saArg);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60,itemp); //ein Argument fehlt	
			
			//###################################################################
			//Test mit mehreren hintereinanderfolgenden Werten
			objOptTest.setPattern("a:b:");
			String[] saArg2={"-a","", "-b", ""};
			sResult = objOptTest.proofArgument(saArg2);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,29,itemp); //ein Argument fehlt
			
			
			//Test mit gemischten Werten
			objOptTest.setPattern("a:b:c:");
			String[] saArg3={"-a","", "-b", "xyz", "-c", ""};
			sResult = objOptTest.proofArgument(saArg3);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,29,itemp); //ein Argument fehlt	
			
			//################################################
			//################
			//B) Nun mal die Argumentenliste auffüllen
			objOptTest.setPattern("a:");
			String[] saArgB={"-a","hallo"};
			sResult = objOptTest.proofArgument(saArgB);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //	
			
			//###################################################################
			//Test mit mehreren hintereinanderfolgenden Werten
			objOptTest.setPattern("a:b:");
			String[] saArg2B={"-a","hallo", "-b", ""};
			sResult = objOptTest.proofArgument(saArg2B);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60,itemp); //ein Argument fehlt abschliessend
			
			
			//Test mit gemischten Werten
			objOptTest.setPattern("a:b:c:");
			String[] saArg3B={"-a","", "-b", "xyz", "-c", ""};
			sResult = objOptTest.proofArgument(saArg3B);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,29,itemp); //ein Argument fehlt	
			
			//#################################################
			//### Optionale Parameter
			//#################################################
			//C Test mit mehreren hintereinanderfolgenden Werten
			objOptTest.setPattern("a.b.");
			String[] saArg2C={"-a","", "-b", ""};
			sResult = objOptTest.proofArgument(saArg2);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); 
			
			
			//Test mit gemischten Werten
			objOptTest.setPattern("a.b.c.");
			String[] saArg3C={"-a","", "-b", "xyz", "-c", ""};
			sResult = objOptTest.proofArgument(saArg3);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //	
			
			//################################################
			//################
			//CB) Nun mal die Argumentenliste auffüllen
			objOptTest.setPattern("a.");
			String[] saArgCB={"-a","hallo"};
			sResult = objOptTest.proofArgument(saArgCB);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //	
			
			//###################################################################
			//Test mit mehreren hintereinanderfolgenden Werten
			objOptTest.setPattern("a:b:");
			String[] saArg2CB={"-a","hallo", "-b", ""};
			sResult = objOptTest.proofArgument(saArg2CB);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,60,itemp); //Letzer Kontrolparameter ohne argument
			
			
			//Test mit gemischten Werten und letzter Wert optional
			objOptTest.setPattern("a.b:c.");
			String[] saArg3CB={"-a","", "-b", "xyz", "-c", ""};
			sResult = objOptTest.proofArgument(saArg3CB);
			itemp = GetOptZZZ.computeCodeFromProofResult(sResult);
			assertEquals(sResult,-1,itemp); //ein Argument fehlt	
			
			
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	/** Pruefe den Argument String:
	 * - hinter dem Pattern String Argument mit Doppelpunkt muss immer etwas stehen (also beim Pattern String a:b:c: fuehrt der Argument String "-a wert1 -b -c" zu einem Fehler weil hinter B kein Wert ist).
	 * - nicht jede option braucht da zu sein
	 *  
	* - hinter dem Pattern String Argument mit Pipe darf nix stehen (also beim Pattern String a|b:c: fuehrt der Argument String "-a wert1 -b wert2 -c wert3" zu einem Fehler weil hinter -a nix erwartet wird). 
	* 
	* lindhauer; 30.06.2007 08:26:25
	 */
	public void testIsArgumentStringValid(){		
		try{
			String stemp=null;
			boolean btemp=false;
			
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:c:b|");//ohne Argumente nach hinten
			stemp = "-a hallo -c Welt -b";
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b -c"; //ist auch valid 'c' als Steuerungszeichen 'mit Argument' erkannt, aber nix folgt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:d:");
			stemp="-a hallo -b -c"; //ist nicht valid 'c' wird in diesem Fall als Steuerungszeichen erkannt, aber vorher war auch ein Steuerzeichen, das ein Argument verlangt hat. Also fehlt etwas.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -c zwei"; //ist valid, Normalfall
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b welt -c zwei drei"; //nicht valid, 'drei' ist kein Steuerzeichen 
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -c zwei -d drei"; //nun wird 'drei' als Argument erkannt
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			//+++++++++++++++++++++++++++++++++++++++++
			//+++ 
			//+++++++++++++++++++++++++++++++++++++++++
			//Laenge >1 der Steuerungzeichen eingeben
			objOptTest.setPattern("a:b:cc:d:");
			
			stemp="-a hallo -b -cc"; //ist nicht valid 'cc' wird in diesem Fall als Steuerungszeichen erkannt, aber nix folgt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -cc zwei"; //ist valid 'cc' wird in diesem Fall als Steuerungszeichen erkannt, und vorher war auch ein Steuerzeichen, das ein Argument verlangt hat. Das vorhanden war.
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			
			stemp="-a hallo -b welt -cc "; //ist nicht valid 'cc' wird in diesem Fall als Steuerungszeichen erkannt, aber. Aber es fehlt das Argument.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -cc zwei drei"; //ist nicht valid 'drei' wird als Argument erkannt. Aber es fehlt das Steuerzeichen
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -cc zwei -d drei"; //ist valid 'drei' wird als Argument erkannt, von dem vorherigen Steuerzeichen -d
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			//+++++++++++++++++++++++++++++
			//+++
			//+++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b|c:");// -d ist kein Argument
			stemp = "-a hallo -c Welt -b -d";
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			objOptTest.setPattern("a:b|c:");// hinter b darf kein Wert stehen
			stemp = "-a hallo -c Welt -b irgendwas";
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			objOptTest.setPattern("a:b|c:");// hinter b darf kein Wert stehen
			stemp = "-a hallo -b irgendwas -c Welt";
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			stemp="-a -b -c"; //ist auch valid 'c' als Steuerungszeichen 'mit Argument' erkannt, aber nix folgt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
			objOptTest.setPattern("a:b:c:");
			stemp="-a hallo -b -c "; //ist auch valid 'c' wird in diesem Fall als Wert und nicht als Steuerungszeichen erkannt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -c zwei"; //ist auch valid, Normalfall
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			stemp="-a hallo -b welt -c zwei drei"; 
			btemp = objOptTest.isArgumentValid(stemp); //ist nicht valid 'drei' wird in diesem Fall als Argument ohne Steuerungszeichen erkannt.
			assertFalse(btemp);
			
			//Falsche L�nge der Steuerungzeichen eingeben
			stemp="-a hallo -b -cc"; //ist nicht valid 'cc' wird in diesem Fall als Steuerungszeichen ohne Wert erkannt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//##################################################
			//### NEGATIVTESTS
			//##################################################
			objOptTest.setPattern("a:b|c:");//ohne Argumente nach hinten
			stemp = "-a hallo -c Welt -b -d"; // d gibt es nicht als Steuerzeichen
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b -c"; //ist auch valid 'c' als Steuerungszeichen 'mit Argument' erkannt, aber nix folgt.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b -c welt zwei"; //ist NICHT valid. 'zwei' wird als Argumentwert erkannt, der "�berfl�ssig" ist.
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++
//			objOptTest.setPattern("a:b:c:");
//			stemp="-a hallo -b -c"; //ist auch valid 'c' wird in diesem Fall als Wert und nicht als Steuerungszeichen erkannt.
//			btemp = objOptTest.isArgumentValid(stemp);
//			assertFalse(btemp);
			
			stemp="-a hallo -b welt -c zwei -d"; //d gibt es nicht
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			stemp="-a hallo -b welt -c zwei drei"; 
			btemp = objOptTest.isArgumentValid(stemp); //drei gibt es nicht 
			assertFalse(btemp);
			
			//Falsche L�nge der Steuerungzeichen eingeben
			stemp="-a hallo -b -cc"; //'cc' gibt es nicht
			btemp = objOptTest.isArgumentValid(stemp);
			assertFalse(btemp);
			
			
			//++++++++++++++++++++++++++++++
			//+++
			//++++++++++++++++++++++++++++++
			//Arbeiten mit einfachem HOCHKOMMATA
			objOptTest.setPattern("a:b:c:");//ohne Argumente nach hinten
			stemp="-a hallo -b welt -c 'zwei drei' "; //nun wird 'zwei drei' als Argument erkannt
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
			
			//+++++++++++++++++++++++++++++++
			//+++
			//+++++++++++++++++++++++++++++++
			objOptTest.setPattern("a.b.c.");//ohne Argumente nach hinten
			stemp="-a hallo -b welt -c 'zwei drei' "; //nun wird 'zwei drei' als Argument erkannt
			btemp = objOptTest.isArgumentValid(stemp);
			assertTrue(btemp);
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	

	public void testLoadOptionAll(){
		try{
			
			
			
			//+++ Einfache Variante
			objOptTest.setPattern("a");
			String[] saTemp = {"-a"};
			boolean btemp = objOptTest.loadOptionAll(saTemp);
			assertTrue(btemp);
			
			String stemp = objOptTest.readOptionNext();
			assertEquals("a", stemp);
			
			
			
			//+++ Mehr Pattern und Pattern zuruecksetzen
			objOptTest.setPattern("b|c");
			String[] saTemp2 ={"-c","-b"}; //Mal die Reihenfolge verglichen mit dem PAttern vertauschen
			btemp = objOptTest.loadOptionAll(saTemp2);
			assertTrue(btemp);
			
			stemp = objOptTest.readOptionNext();
			assertNotNull(stemp);
			assertFalse(stemp.equalsIgnoreCase("a"));
			
			String stemp2 = objOptTest.readOptionNext();
			assertNotNull(stemp2);
			assertTrue(stemp2.equalsIgnoreCase("b") | stemp2.equalsIgnoreCase("c"));
			
			String stemp3 = objOptTest.readOptionNext();
			assertNull("NULL erwartet. Wert ist aber '" + stemp3 + "'", stemp3);  //Das waere das 3. auslesen, bei nur 2 Parametern
			
			stemp3 = objOptTest.readOptionFirst(); //Wieder von vorne anfangen !!!
			assertNotNull(stemp3);
			assertTrue(stemp3.equalsIgnoreCase("b") | stemp3.equalsIgnoreCase("c"));
			
			
			stemp = objOptTest.readValue("a");  //+++ Werte auslesen
			assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp); //Key nicht vorhanden
			
			stemp = objOptTest.readValue("b");
			assertNotNull("NULL nicht erwartet.", stemp);//b ist Key und gleichzeitig Wert (Seit 20260316)
			assertEquals("b",stemp);
			
			//+++++++++++++++++ Pattern mit Keys, die Argumente beinhalten
			objOptTest.setPattern("w|x:y:z");
			String[] saTemp3 ={"-y","werty", "-w", "-x", "wertx", "-z"}; //Mal die Reihenfolge verglichen mit dem Pattern vertauschen
			btemp = objOptTest.loadOptionAll(saTemp3);
			assertTrue(btemp);
			
			stemp = objOptTest.readValue("w");  //+++ Werte auslesen
			assertNotNull("NULL nicht erwartet.", stemp); //w ist Key und gleichzeitig Wert
			assertEquals("w",stemp);
			
			stemp = objOptTest.readValue("z");  //+++ Werte auslesen
			assertNotNull("NULL nicht erwartet.", stemp); //z ist Key und gleichzeitg Wert (seit 20260316)
			assertEquals("z",stemp);
			
			
			stemp = objOptTest.readValue("x");
			assertNotNull(stemp); 
			assertEquals("wertx", stemp);
			
			stemp = objOptTest.readValue("y");
			assertNotNull(stemp); 
			assertEquals("werty", stemp);
			
			
			
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	public void testLoadOptionAll_escapedByQuotes(){
		try{
			String sValue=null; String sValueExpected=null;
			
			
			//+++ Einfache Variante
			objOptTest.setPattern("a:b:");
			String[] saTemp = {"-a","eins zwei"};
			boolean btemp = objOptTest.loadOptionAll(saTemp);
			assertTrue(btemp);
			
			sValueExpected = "a";
			sValue = objOptTest.readOptionNext();
			assertEquals(sValueExpected, sValue);
			
			sValueExpected = null;
			sValue = objOptTest.readOptionNext();
			assertEquals(sValueExpected, sValue);
			
			//+++ Den Wert holen
			sValueExpected = "eins zwei";
			sValue= objOptTest.readValue("a");  //+++ Werte auslesen
			assertEquals(sValueExpected, sValue);
			
			//Negativfall
			sValueExpected = null;
			sValue= objOptTest.readValue("b");  //+++ Werte auslesen
			assertEquals(sValueExpected, sValue);
			
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}	
	}
	
	public void testReadKey(){
		String sValue=null; String sValueExpected=null;
		try{
			//##########################
			//Hier einen Wert am Anfang OHNE Argumente holen
			String sPattern = "ssh|https|pat:z:"; //s. IKernelConfigZZZ
			String[] saArg = {"-ssh"};
			GetOptZZZ objOptTest01 = new GetOptZZZ(sPattern);
			objOptTest01.loadOptionAll(saArg);
			
			sValueExpected = "ssh";
			sValue = objOptTest01.readValue("ssh");				
		    assertNotNull(sValue);
		    assertEquals(sValueExpected,sValue);		    		    		    		    

		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
}
