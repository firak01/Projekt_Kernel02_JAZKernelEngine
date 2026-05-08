package basic.zBasic.util.datatype.string;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZTest;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZTest;
import basic.zKernel.file.ini.KernelZFormulaIni_PathZZZ;
import junit.framework.TestCase;

public class StringZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
		}//END setup
	 
	 public void testCrlf(){
		 try{
		    String stemp;
		    stemp = StringZZZ.crlf();
		    assertNotNull("crlf is never NULL", stemp);
		    assertFalse("Length of crlf is never 0", stemp.length()==0 );
		    assertEquals(System.getProperty("line.separator"),stemp);		   
		    
		    System.out.println("Zeile1: " + stemp + "Das soll in Zeile 2 stehen");
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	public void testLeft(){
		 String stemp;
		 try {
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.left("das ist ein Test", 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("das ist ein Test", -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.left("das ist ein Test", 1);
		 assertEquals("d", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length());
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length() + 1);
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.left("das ist ein Test", sDummy.length() - 1);
		 assertEquals("das ist ein Tes", stemp);
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.left("das ist ein Test", " ");
		 assertEquals("das", stemp);
		 
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	public void testLeftKeep() {
		String sValue;
		try {
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 sValue =StringZZZ.leftKeep(sDummy2, " ");
		 assertEquals("das ", sValue);
		 
		 //###################
		 //Teste auf String, ab einer bestimmten Position VON links
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 0);
		 assertEquals("das ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 1);
		 assertEquals("das ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 2);
		 assertEquals("das ", sValue);
		
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 3);
		 assertEquals("das ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 4);
		 assertEquals("das ist ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 5);
		 assertEquals("das ist ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 6);
		 assertEquals("das ist ", sValue);
		 
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 7);
		 assertEquals("das ist ", sValue);
		
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 8);
		 assertEquals("das ist ein ", sValue);
		
		 sValue=StringZZZ.leftKeep(sDummy2, " ", 9);
		 assertEquals("das ist ein ", sValue);
		 
		 //################################################
		 //1a. Fall aus der Praxis
		 String sDummy3 = "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
		 String sSepLeft = "<Z:Class>";
		 int iIndexStartingFromLeft = 16;
		 boolean bExactMatch = false;
		 sValue=StringZZZ.leftKeep(sDummy3, sSepLeft, bExactMatch, iIndexStartingFromLeft);
		 assertNull(sValue);
		 
		 //1b. Also muss iIndexStartingFromLeft fuer die Ruckgabe des ersten Tags weniger sein
		 iIndexStartingFromLeft = 16-sSepLeft.length();
		 sValue=StringZZZ.leftKeep(sDummy3, sSepLeft, bExactMatch, iIndexStartingFromLeft);
		 assertEquals("<Z:Java><Z:Class>",sValue);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testLeftBack(){
		 String stemp;
		 try {
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.leftback("das ist ein Test", -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.leftback("das ist ein Test", 0);
		 assertEquals("das ist ein Test",stemp);
		 		 		 		 
		 stemp = StringZZZ.leftback("das ist ein Test", 1);
		 assertEquals("das ist ein Tes", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length());
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length() + 1);
		 assertNull(stemp);
		 
		 stemp =StringZZZ.leftback("das ist ein Test", sDummy.length() - 1);
		 assertEquals("d", stemp);
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.leftback("das ist ein Test", " ");
		 assertEquals("das ist ein",stemp);
		 				 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	
	/** Gezählt wird von rechts
	 * 
	 * @author Fritz Lindhauer, 29.11.2025, 08:22:11
	 */
	public void testLeftBackKeep(){
		 String stemp;
		 try {
		 String sDummy = new String("das ist ein Test");
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.leftbackKeep(sDummy, -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.leftbackKeep(sDummy, 0);
		 assertEquals("das ist ein Test",stemp);
		 		 		 		 
		 stemp = StringZZZ.leftbackKeep(sDummy, 1);
		 assertEquals("das ist ein Test", stemp); //Hier wirkt sich das Keep aus (es bleibt ein Zeichen mehr im String)
		 
		 stemp = StringZZZ.leftbackKeep(sDummy, 2);
		 assertEquals("das ist ein Tes", stemp); //Hier wirkt sich das Keep aus (es bleibt ein Zeichen mehr im String)
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 stemp =StringZZZ.leftbackKeep(sDummy, sDummy.length() + 1); //hoeherer Index als Zeichen vorhanden 
		 assertNull(stemp);

		 stemp =StringZZZ.leftbackKeep(sDummy, sDummy.length());
		 assertEquals("d", stemp); //Hier wirkt sich das Keep aus (es bleibt ein Zeichen mehr im String)
		 		 
		 stemp =StringZZZ.leftbackKeep(sDummy, sDummy.length() - 1);
		 assertEquals("da", stemp);
		 
		 //################### 
		 //Teste auf String	
		 stemp =StringZZZ.leftback(sDummy, " ");
		 assertEquals("das ist ein",stemp); 
		 
		 stemp =StringZZZ.leftbackKeep(sDummy, " ");
		 assertEquals("das ist ein ",stemp); //hier wirkt sich das Keep aus
		 				 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	
	
	
	
	//#############################################################
	public void testRight(){
		String stemp;
		try {
		 
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.right("das ist ein Test", 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("das ist ein Test", -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.right("das ist ein Test", 1);
		 assertEquals("t", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length());
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length() + 1);
		 assertEquals(sDummy, stemp);
		 
		 stemp =StringZZZ.right("das ist ein Test", sDummy.length() - 1);
		 assertEquals("as ist ein Test", stemp); //!!! DAS D fehlt
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.right("das ist ein Test", " ");
		 assertEquals("Test", stemp);
		 
		 //System.out.println(stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testRightKeep(){
		String stemp;
		 try {
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.rightKeep(sDummy2, " ");
		 assertEquals(" Test", stemp);
		 
		 //###################
		 //Teste auf String, ab einer bestimmten Position VON rechts
		 stemp =StringZZZ.rightKeep(sDummy2, " ", 6);
		 assertEquals(" ein Test", stemp);
		 
		 stemp =StringZZZ.rightKeep(sDummy2, " ", 5);
		 assertEquals(" ein Test", stemp);
		 
		 stemp =StringZZZ.rightKeep(sDummy2, " ", 4);
		 assertEquals(" Test", stemp);
		 
		 stemp =StringZZZ.rightKeep(sDummy2, " ", 3);
		 assertEquals(" Test", stemp);
		 
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	}
	
	//#############################################
	/**Gezählt wird von links
	 * 
	 * @author Fritz Lindhauer, 29.11.2025, 08:22:51
	 */
	public void testRightback01(){
		String stemp;
		 try {
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.rightback("das ist ein Test", 0);
		 assertEquals("das ist ein Test", stemp);
		 
		 stemp = StringZZZ.rightback("das ist ein Test", -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.rightback("das ist ein Test", 1);
		 assertEquals("as ist ein Test", stemp);
		 
		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("das ist ein Test");		
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length());
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length() + 1);
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightback("das ist ein Test", sDummy.length() - 1);
		 assertEquals("t", stemp); 
		 
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");		
		 stemp =StringZZZ.rightback("das ist ein Test", " ");
		 assertEquals("ist ein Test", stemp);
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	}
	
	/**Gezählt wird von links
	 * 
	 * @author Fritz Lindhauer, 29.11.2025, 08:23:02
	 */
	public void testRightback02(){
		String stemp;
		try {
		stemp = StringZZZ.rightback("123456789", 0);
		assertEquals("123456789", stemp);
		stemp = StringZZZ.rightback("123456789", -1);
		assertNull(stemp);
		
		stemp = StringZZZ.rightback("123456789", 1);
		assertEquals("23456789", stemp);
		
		stemp = StringZZZ.rightback("123456789", 9);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightback("123456789", 10);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightback("123456789", 2);
		assertEquals("3456789", stemp);	
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	//################################################
	/**Gezählt wird von links
	 * 
	 * @author Fritz Lindhauer, 29.11.2025, 08:22:27
	 */
	public void testRightbackKeep01(){
		String stemp;
		 try {
		String sDummy = new String("das ist ein Test");
		
		 //#######################################
		 //Teste den rechten Rand
		 stemp = StringZZZ.rightbackKeep(sDummy, -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.rightback(sDummy, 0);
		 assertEquals("das ist ein Test", stemp);
		 
		 stemp = StringZZZ.rightbackKeep(sDummy, 0);
		 assertEquals("das ist ein Test", stemp);
		
		 
		 stemp = StringZZZ.rightback(sDummy, 1);
		 assertEquals("as ist ein Test", stemp);
		 
		 stemp = StringZZZ.rightbackKeep(sDummy, 1);
		 assertEquals("das ist ein Test", stemp); //Hier wirkt sich das Keep aus
		 
		 stemp = StringZZZ.rightbackKeep(sDummy, 2);
		 assertEquals("as ist ein Test", stemp);
		 
		 //########################################'
		 //Teste den rechten Rand	
		 stemp =StringZZZ.rightbackKeep(sDummy, sDummy.length());
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightbackKeep(sDummy, sDummy.length() + 1);
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.rightback(sDummy, sDummy.length() - 1);
		 assertEquals("t", stemp); 
		 
		 //################### 
		 //Teste auf String		
		 stemp =StringZZZ.rightback(sDummy, " ");
		 assertEquals("ist ein Test", stemp);
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	}
	
	/**Gezählt wird von links
	 * 
	 * @author Fritz Lindhauer, 29.11.2025, 08:22:42
	 */
	public void testRightbackKeep02(){
		String stemp;
		try {
		stemp = StringZZZ.rightbackKeep("123456789", 0);
		assertEquals("123456789", stemp);
		stemp = StringZZZ.rightbackKeep("123456789", -1);
		assertNull(stemp);
		
		stemp = StringZZZ.rightbackKeep("123456789", 1);
		assertEquals("123456789", stemp);  //hier wirkt sich das Keep aus, die "1" bleibt drin.
		
		stemp = StringZZZ.rightbackKeep("123456789", 9);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightbackKeep("123456789", 10);
		assertEquals("", stemp);
		
		stemp = StringZZZ.rightbackKeep("123456789", 2);
		assertEquals("23456789", stemp);	//hier wirkt sich das Keep aus, die "2" bleibt drin.
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	//################################################
	public void testMid(){
		String stemp;
		try {
		 String sTest = "abcdefghijk";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.mid(sTest, -1, 1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, -1);
		 assertNull(stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, 0);
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.mid(sTest, 0, 1);
		 assertEquals("a", stemp);
		 stemp = StringZZZ.mid(sTest, 0, 2);
		 assertEquals("ab", stemp);
		 

		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.mid(sTest, sTest.length()+1, 1);
		 assertNull(stemp);
		 
		 stemp =StringZZZ.mid(sTest, sTest.length(), 1);
		assertEquals("", stemp);
		
		 stemp =StringZZZ.mid(sTest, sTest.length()-1, 1);
		 assertEquals("k", stemp);
		 stemp =StringZZZ.mid(sTest, sTest.length()-1, 0);
		 assertEquals("", stemp);
		
		 stemp =StringZZZ.mid(sTest, sTest.length()-2, 2);
		 assertEquals("jk", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		 
	}
	
	public void testMidKeep(){
		String stemp;
		 try {
		 //################### 
		 //Teste auf String
		 String sDummy2 = new String("das ist ein Test");
		 
		 //Test linke Grenze
		 stemp =StringZZZ.midKeep(sDummy2, 3, "Test");
		 assertEquals(" ist ein Test", stemp);
		 
		 stemp =StringZZZ.midKeep(sDummy2, 4, "Test");
		 assertEquals("ist ein Test", stemp);
		 
		 stemp =StringZZZ.midKeep(sDummy2, 5, "Test");
		 assertEquals("st ein Test", stemp);
		 
		 //Teste rechte Grenze (das Wort)
		 
		 
		 //++++ Negativtest: ist die rechte Grenze nicht vorhanden... null	
		 stemp =StringZZZ.mid(sDummy2, 4, "nix");
		 assertNull("ist ein Test", stemp);
		 
		 stemp =StringZZZ.midKeep(sDummy2, 4, "nix");
		 assertNull("ist ein Test", stemp); //wenn das normal schon NULL zurückliefert, dann das keep auch
		 
		 //++++++ NEGTIVTEST, ist die linke Grenze nicht vorhanden... null		 
		 stemp =StringZZZ.mid(sDummy2, "nix", "Test");
		 assertNull(stemp);  
		 
		 stemp =StringZZZ.midKeep(sDummy2, "nix", "Test");
		 assertNull(stemp);  
		 
		 //##########################################################
		 //#### Positivtest mit Strings
		 stemp =StringZZZ.midKeep(sDummy2, "das", "Test");
		 assertEquals("das ist ein Test", stemp);  
		 
		 stemp =StringZZZ.midKeep(sDummy2, " ", " ");
		 assertEquals(" ist ein ", stemp);  
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		 
	}
	
	
	public void testMidLeftRightback(){
		String stemp;
		try {
		String sTest = "abc~=~xyz";
		//#######################################
		
		//Teste den linken Rand
		stemp = StringZZZ.midLeftRightback(sTest, "", "~");
		assertNotNull(stemp);
		assertEquals("abc", stemp);
		 
		
		//########################################'
		//Teste den rechten Rand
		stemp = StringZZZ.midLeftRightback(sTest, "~", "");
		assertNotNull(stemp);
		assertEquals("xyz",stemp);
		 
		//########################################
		//Teste die Mitte
		stemp =StringZZZ.midLeftRightback(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRightback(sTest, "x", "~");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRightback(sTest, "y", "~");
		 assertNull(stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRightback(sTest, "~", "c");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRightback(sTest, "~", "b");
		 assertNull(stemp);
		 
		 //### mal was praktisches				
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightback(sTest, "c", "c");
		 assertEquals("deab", stemp);//also wie bei .midLeftRight(...)
		 
		 //+++++++++++++++++++++++++++++++++
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRight(sTest, "d", "h");
		 assertEquals("efg", stemp);
		 
		 
		 //++++++++++++++++++++		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightback(sTest, "b", "");
		 assertEquals("cde", stemp);
		 				
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRightback(sTest, "x", "y");
		 assertEquals("[test]", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightback(sTest, "[", "]");
		 assertEquals("test", stemp);  //!!! Wg. diesem Ziel wurde die Methode ueberhaupt entwickelt!!! Es ist anders als bei midLeftRight(...)
		 
		 //++++++ NEGTIVTEST, ist die rechte Grenze nicht vorhanden... null
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightback(sTest, "[", "X");
		 assertNull(stemp);  
		 
		 //++++++ NEGTIVTEST, ist die linke Grenze nicht vorhanden... null
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightback(sTest, "X", "]");
		 assertNull(stemp);  
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		 
	}
	
	public void testMidLeftRight(){
		String stemp;
		try {
		 String sTest = "abc~=~xyz";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.midLeftRight(sTest, "", "~");
		 assertNotNull(stemp);
		 assertEquals("abc~=", stemp);
		 
		
		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.midLeftRight(sTest, "~", "");
		 assertNotNull(stemp);
		 assertEquals("=~xyz",stemp);
		 
		//########################################
		//Teste die Mitte (das sollte dann wie bei midLeftRightback(...) sein
		stemp =StringZZZ.midLeftRight(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRight(sTest, "x", "~");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "y", "~");
		 assertNull(stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRight(sTest, "~", "c");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "~", "b");
		 assertNull(stemp);
		 
		 //ca) mal die Eingabeparameter Strings umgedreht
		 stemp =StringZZZ.midLeftRight(sTest, "~", "x");
		 assertEquals("=~", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "~", "y");
		 assertEquals("=~x", stemp);
		 
		 //cb) ...
		 stemp =StringZZZ.midLeftRight(sTest, "c", "~");
		 assertEquals("~=", stemp);
		 		 
		 stemp =StringZZZ.midLeftRight(sTest, "b", "~");
		 assertEquals("c~=", stemp);
		 
		 
		 
		 //########################################
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "c", "c");
		 assertEquals("deab", stemp); //!!! Das ist das gleiche Ergebnis wie bei midLeftRightBack(...), es wird halt so erwartet. Fuer Schnittmenge gibt es midLeftIntersected(...);
		 		 		 
		 //+++++++++++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "b");
		 assertEquals("a", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "b", ""); //also rechts keine Grenze...
		 assertEquals("cdeabcde", stemp); 
		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "b");
		 assertEquals("cde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "", "b");//also links keine Grenze...
		 assertEquals("abcdea", stemp);
		 
		 //+++++++++++++++++++++++++++++++++		 
		 stemp =StringZZZ.midLeftRight(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRight(sTest, "d", "h");
		 assertEquals("efg", stemp);
		  
		 //++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.leftback(sTest, "c");
		 assertEquals("abcdeab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.rightback(sTest, "c");
		 assertEquals("deabcde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRight(sTest, "c", "c");
		 assertEquals("deab", stemp);
		 
		 //### mal was praktisches
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRight(sTest, "[", "]");
		 assertEquals("test", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRight(sTest, "[", "]");
		 assertEquals("[test]", stemp);  //!!!!! anders als bei midLeftRightback
		 
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testMidLeftRightbackIntersect(){
		String stemp;
		try {
		 String sTest = "abc~=~xyz";
		 //#######################################
		
		 //Teste den linken Rand
		 stemp = StringZZZ.midLeftRightIntersect(sTest, "", "~");
		 assertNotNull(stemp);
		 assertEquals("abc~=", stemp);
		 
		
		 //########################################'
		 //Teste den rechten Rand
		 stemp = StringZZZ.midLeftRightIntersect(sTest, "~", "");
		 assertNotNull(stemp);
		 assertEquals("=~xyz",stemp);
		 
		//########################################
		//Teste die Mitte, da es von links und rechts keine ueberschneidung gibt, leer, aber da die Separatoren gleich sind, wird doch etwas gefunden. 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "~");
		assertNotNull(stemp);
		assertEquals("=", stemp);
		
		
		//########################################
		//Teste ueber die Raender hinaus
		//a) ueber den rechten Rand
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "x", "~");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "y", "~");
		 assertNull(stemp);
		 
		 //b) ueber den linken Rand
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "c");
		 assertNull(stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "b");
		 assertNull(stemp);
		 
		 //ca) mal die Eingabeparameter Strings umgedreht
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "x");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "~", "y");
		 assertEquals("x", stemp);
		 
		 //cb) ...
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "~");
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", "~");
		 assertEquals("c", stemp);
		 
		 
		 
		 //########################################
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "c");
		 assertEquals("ab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "c");
		 assertEquals("de", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "c");
		 assertEquals("deab", stemp); //!!! Das ist das gleiche Ergebnis wie bei midLeftRightBack(...), es wird halt so erwartet. Fuer Schnittmenge gibt es midLeftIntersected(...);
		 		 		 
		 //+++++++++++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.left(sTest, "b");
		 assertEquals("a", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", ""); //also rechts keine Grenze...
		 assertEquals("cdeabcde", stemp); 
		 		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.right(sTest, "b");
		 assertEquals("cde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "", "b");//also links keine Grenze...
		 assertEquals("abcdea", stemp);
		 
		 //+++++++++++++++++++++++++++++++++		 
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "b", "b"); //es gibt also keine gemeinsame Schnittmenge, aber wir betrachten hier nur Grenzen
		 assertEquals("cdea", stemp);
		 
		 //Da sich hier alles ausschliessen wuerde einen anderen Teststring verwenden, in dem keine Zeichen doppelt vorkommen
		 sTest = "abcdefghijk";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "d", "h");
		 assertEquals("efg", stemp);
		 
		 //++++++++++++++++++++
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.leftback(sTest, "c");
		 assertEquals("abcdeab", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.rightback(sTest, "c");
		 assertEquals("deabcde", stemp);
		 
		 sTest = "abcdeabcde";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "c", "c");
		 assertEquals("deab", stemp);
		 
		 //### mal was praktisches
		 sTest = "x[test]y";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "[", "]");
		 assertEquals("test", stemp);
		 
		 sTest = "[[test]]";
		 stemp =StringZZZ.midLeftRightIntersect(sTest, "[", "]");
		 assertEquals("test", stemp);  //!!!!! anders als bei midLeftRight(...) , wie bei midLeftRightback(...)
		 
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testMidRightLeft(){
		String stemp;
		try {
		 String sTest = "https://github.com/firak01/Projekt_Kernel02_JAZDummy.git";
//			 * 
//			 * soll firak01 geholt werden,
//			 * wenn github.com/
//			 * und  / übergeben wird
//			 * 
//			 * UND DAS AUCH wenn vielleicht ohne Projekt gearbeitet werden könnte und die URL für die Suche mit / erweitert wird.
//			 * https://github.com/firak01/Projekt_Kernel02_JAZDummy.git/
//			 * https://github.com/firak01/
		 
		 //#######################################
		
		 //Teste auf normal
		 stemp = StringZZZ.midRightLeft(sTest, "github.com/", "/");
		 assertNotNull(stemp);
		 assertEquals("firak01", stemp);
		 
		 sTest = "https://github.com/firak01/";
		 stemp = StringZZZ.midRightLeft(sTest, "github.com/", "/");
		 assertNotNull(stemp);
		 assertEquals("firak01", stemp);
		
		 //Wenn aber der rechte Teil nicht da ist, null
		 sTest = "https://github.com/firak01";
		 stemp = StringZZZ.midRightLeft(sTest, "github.com/", "/");
		 assertNull(stemp);
		 
		 //Ausser die Angaben zu links oder rechts werden nicht angegeben...
		 sTest = "https://github.com/firak01/Projekt_Kernel02_JAZDummy.git";
		 stemp = StringZZZ.midRightLeft(sTest, "github.com/", "");
		 assertEquals("firak01/Projekt_Kernel02_JAZDummy.git",stemp);

		 
		 sTest = "https://github.com/firak01/Projekt_Kernel02_JAZDummy.git";
		 stemp = StringZZZ.midRightLeft(sTest, "", "/Projekt_Kernel02_JAZDummy");
		 assertEquals("https://github.com/firak01",stemp);
		 
		 //Wenn beide Teile nicht angegeben werden aber null
		 sTest = "https://github.com/firak01/Projekt_Kernel02_JAZDummy.git";
		 stemp = StringZZZ.midRightLeft(sTest, "", "");
		 assertNull(stemp);
		 		
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testStrLeft(){
		 String stemp;
		 try {
		 //#######################################
		 //Teste den linken Rand
		 stemp = StringZZZ.left("123456789", "0");
		 assertNull(stemp);
		 		 
		 stemp = StringZZZ.left("123456789", "1");
		 assertEquals("",stemp);
		 		 
		 stemp = StringZZZ.left("123456789", "2");
		 assertEquals("1", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand
		 String sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.left("123456789", "9");
		 assertEquals("12345678", stemp);
		 
		 stemp =StringZZZ.left("123456789", "");
		 assertEquals("",stemp);
		 
		 
		 //#####################################
		 //TEST F�R die Erweiterung um den index-parameter
		 //#####################################
		 //#######################################
		 //Teste den linken Rand (s. oben)
		 stemp = StringZZZ.left("123456789", 0, "0");
		 assertNull(stemp);
		 
		 stemp = StringZZZ.left("123456789", 0, "1");
		 assertEquals("", stemp);
		 		 
		 stemp = StringZZZ.left("123456789", 0, "2");
		 assertEquals("1", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand (s. oben)
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 0, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.left("123456789", 0, "9");
		 assertEquals("12345678", stemp);
		 
		 stemp =StringZZZ.left("123456789", 0, "");
		 assertEquals("",stemp);
		 
		 //#######################################
		 //Eweiterungen fuer den index-parameter
		 //		Teste den linken Rand
		 stemp = StringZZZ.left("123456789",1, "1");
		 assertNull(stemp);
		 
		 stemp = StringZZZ.left("123456789", 1, "2");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.left("123456789", 1, "3");
		 assertEquals("2", stemp);
		  
		 		 
		 //########################################'
		 //Teste den rechten Rand
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 8, sDummy);  //Das haengt nicht im speziellen von der 8 ab, sondern gilt fuer jden wert > 0, wg. sDummy = sDummy
		 assertNull(stemp);
		 
		 sDummy = new String("123456789");		
		 stemp =StringZZZ.left(sDummy, 7, sDummy);  //Das haengt nicht im speziellen von der 7 ab, sondern gilt fuer jden wert > 0, wg. sDummy = sDummy
		 assertNull(stemp);
		 		 
		 //+++
		 stemp =StringZZZ.left("123456789", 8, "9");
		 assertEquals("", stemp);
		 
		 stemp =StringZZZ.left("123456789",7, "9");
		 assertEquals("8", stemp);
		 
		 stemp =StringZZZ.left("123456789", 8, "");
		 assertEquals("",stemp);
		 
		 stemp =StringZZZ.left("123456789", 7, "");
		 assertEquals("",stemp);
		 
		 
		 //##################
		 //Was passiert, wenn es ein Zeichen im String nicht gibt ?
		 stemp =StringZZZ.left("123456789",7, "A");
		 assertNull(stemp);
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	
	public void testStrLeftback(){
		String stemp;
	try {
		//Teste fehlenden Wert
		stemp = StringZZZ.leftback("123456789 123456789", "0");
		assertNull(stemp);
		
		//Teste den linken Rand
		stemp = StringZZZ.leftback("123456789 123456789","1" );
		assertEquals("123456789 ", stemp);
		
		//Teste den rechten Rand
		stemp = StringZZZ.leftback("123456789 123456789", "9");
		assertEquals("123456789 12345678", stemp);
	
		//Teste normale
		stemp = StringZZZ.leftback("123456789 123456789", "2");
		assertEquals("123456789 1", stemp);
		
		stemp = StringZZZ.leftback("123456789 123456789", "8");
		assertEquals("123456789 1234567", stemp);
		
		stemp = StringZZZ.leftback("123456789 123456789", "5");
		assertEquals("123456789 1234", stemp);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	}
	
	public void testStrRight(){
		 String stemp;
		 try {
		 //#######################################
		 //Teste den rechten Rand
		 stemp = StringZZZ.right("123456789", "9");
		 assertEquals("", stemp);
		 
		 stemp = StringZZZ.right("123456789", "0");
		 assertNull(stemp);
		 
		 stemp = StringZZZ.right("123456789", "8");
		 assertEquals("9", stemp);
		  
		 		 
		 //########################################'
		 //Teste den linken Rand
		 String sDummy = new String("123456789");		
		 stemp =StringZZZ.right(sDummy, sDummy);
		 assertEquals("", stemp);
		 		 
		 stemp =StringZZZ.right("123456789", "1");
		 assertEquals("23456789", stemp);
		 
		 stemp =StringZZZ.right("123456789", "");
		 assertEquals("", stemp);
		 
		 
		 //System.out.println(stemp);
		 }catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	
	public void testStrRightback(){
		
		String stemp;
		try {
		//Teste den linken Rand
		stemp = StringZZZ.rightback("123456789 123456789","1" );
		assertEquals("23456789 123456789", stemp);
		
		//Teste den rechten Rand
		stemp = StringZZZ.rightback("123456789 123456789", "9");
		assertEquals(" 123456789", stemp);
		
		//Teste fehlenden Wert
		stemp = StringZZZ.rightback("123456789 123456789", "0");
		assertNull(stemp);
		
		//Teste normale
		stemp = StringZZZ.rightback("123456789 123456789", "2");
		assertEquals("3456789 123456789", stemp);
		
		stemp = StringZZZ.rightback("123456789 123456789", "8");
		assertEquals("9 123456789", stemp);
		
		stemp = StringZZZ.rightback("123456789 123456789", "5");
		assertEquals("6789 123456789", stemp);
		
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
public void testPadLeft() {
	try {
	String stemp = StringZZZ.padLeft("abc", 5);
	assertEquals("  abc", stemp);
	
	
	String stemp2 = StringZZZ.padLeft("abc",  5, '-');
	assertEquals("--abc",stemp2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testPadRight() {
	try {
	String stemp = StringZZZ.padRight("abc", 5);
	assertEquals("abc  ", stemp);
	
	
	String stemp2 = StringZZZ.padRight("abc",  5, '-');
	assertEquals("abc--",stemp2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}
	

public void testRepeat(){
	try {
	 //#######################################
	 //Teste normal
	 String stemp = StringZZZ.repeat("1", 2);
	 assertEquals("11", stemp);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testStrWord(){
	try {
	String sTest = "aaa bbb ccc";
	
	String stemp = StringZZZ.word(sTest," ", 2);
	assertEquals("bbb", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 1);
	assertEquals("aaa", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 3);
	assertEquals("ccc", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 4);
	assertEquals("", stemp);
	
	stemp = StringZZZ.word(sTest, " ", 0);
	assertNull("NULL erwartet. Wert ist aber '" + stemp + "'", stemp);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


	public void testStripNumeric() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.stripNumeric(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.stripNumeric(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.stripNumeric(sTest);
		assertEquals("1aa b2b cc3", stemp); //Lasse 1 Zeichen uebrig, ohne ein Zeichen uebrig zu lassen waer das trim.
		
		sTest = "123 b2b 543";
		stemp = StringZZZ.stripNumeric(sTest);
		assertEquals("3 b2b 5", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	public void testStripNumericLeft() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.stripNumericLeft(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.stripNumericLeft(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.stripNumericLeft(sTest);
		assertEquals("1aa b2b cc3", stemp); //Lasse 1 Zeichen uebrig, ohne ein Zeichen uebrig zu lassen waer das trim.
		
		sTest = "123 b2b 543";
		stemp = StringZZZ.stripNumericLeft(sTest);
		assertEquals("3 b2b 543", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testStripNumericRight() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.stripNumericRight(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.stripNumericRight(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.stripNumericRight(sTest);
		assertEquals("1aa b2b cc3", stemp); //Lasse 1 Zeichen uebrig, ohne ein Zeichen uebrig zu lassen waer das trim.
		
		sTest = "123 b2b 543";
		stemp = StringZZZ.stripNumericRight(sTest);
		assertEquals("123 b2b 5", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	//###############################
	public void testTrimNumeric() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.trimNumeric(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.trimNumeric(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.trimNumeric(sTest);
		assertEquals("aa b2b cc", stemp);
		
		sTest = "123 b2b 543";
		stemp = StringZZZ.trimNumeric(sTest);
		assertEquals(" b2b ", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testTrimNumericLeft() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.trimNumericLeft(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.trimNumericLeft(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.trimNumericLeft(sTest);
		assertEquals("aa b2b cc3", stemp);	
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testTrimNumericRight() {
		try {
		String sTest = "aaa bbb ccc";
		
		String stemp = StringZZZ.trimNumericRight(sTest);
		assertEquals("aaa bbb ccc", stemp);
		
		sTest = "aaa 123 ccc";
		stemp = StringZZZ.trimNumericRight(sTest);
		assertEquals("aaa 123 ccc", stemp);
		
		sTest = "1aa b2b cc3";
		stemp = StringZZZ.trimNumericRight(sTest);
		assertEquals("1aa b2b cc", stemp);		
		
		sTest = "1aa b2b 543";
		stemp = StringZZZ.trimNumericRight(sTest);
		assertEquals("1aa b2b ", stemp);	
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}


public void testContains(){
	String sTest = "aaa bbb ccc";
	try {
		boolean btemp = StringZZZ.contains(sTest, "  "); //zwei Leerzeichen
		assertFalse(btemp); // der String ist ja nicht vorhanden
		
		boolean btemp2 = StringZZZ.contains(sTest,"c ");
		assertFalse(btemp2); // der String ist ja nicht vorhanden
		
		boolean btemp3 = StringZZZ.contains(sTest, "b ");
		assertTrue(btemp3);
		
		boolean btemp4 = StringZZZ.contains(sTest, "a");
		assertTrue(btemp4);
		
		boolean btemp5 = StringZZZ.contains(sTest, "ccc");
		assertTrue(btemp5);
		
		boolean btemp6 = StringZZZ.contains(sTest, "aaa");
		assertTrue(btemp6);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

public void testCount(){
	try {
		int itemp = StringZZZ.count("12-3-45", "-");
		assertEquals(itemp,2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testCountMatches(){
	try {
		int itemp = StringZZZ.countMatches("12-3-45", "-");
		assertEquals(itemp,2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testCountSubstring(){
	try {
		int itemp = StringZZZ.countSubstring("12-3-45", "-");
		assertEquals(itemp,2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testCountChar(){
	char cToFind='-';
	try {
		int itemp = StringZZZ.countChar("12-3-45", cToFind);
		assertEquals(itemp,2);
		
		Character objChar = new Character(cToFind);
		itemp = StringZZZ.countChar("12-3-45", objChar);
		assertEquals(itemp,2);
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


public void testExplode(){
	try {
	//Erst einmal den einfachen Fall
	String sTest = "panel1<./>text1.getText()";
	String[] saToken = StringZZZ.explode(sTest, "<./>");
	assertNotNull(saToken);
	assertEquals(saToken.length, 2);
	assertEquals(saToken[1], "text1.getText()");
	
	//Nun den Fall: Exploden mit einem Array von Delimitern
	String[] saDelim ={"<./>"};
	saToken = StringZZZ.explode(sTest, saDelim);
	assertNotNull(saToken);
	assertEquals(saToken.length, 2);
	assertEquals(saToken[1], "text1.getText()");
	
	
	
	//##############################################
	//FALL: ARRAY VON MEHREREN DELIMITERN
	sTest = "CarrierSequenze<?/>CarrierCreated<+/>'#'<+/>CarrierSequenze<:/>''";
	String[] saDelim2 ={"<./>","<?/>","<+/>","<:/>"};
	saToken = StringZZZ.explode(sTest, saDelim2);
	assertNotNull(saToken);
	assertEquals(saToken.length, 5);
	assertEquals(saToken[0], "CarrierSequenze");
	assertEquals(saToken[1], "CarrierCreated");
	assertEquals(saToken[2], "'#'");
	assertEquals(saToken[3], "CarrierSequenze");
	assertEquals(saToken[4], "''");
	
	//!!! Das muss auch herauskommen, wenn das Delimiter Array ganz anders ist !!!
	String[] saDelim3 ={"<?/>","<./>","<:/>","<+/>"};
	saToken = StringZZZ.explode(sTest, saDelim3);
	assertNotNull(saToken);
	assertEquals(saToken.length, 5);
	assertEquals(saToken[0], "CarrierSequenze");
	assertEquals(saToken[1], "CarrierCreated");
	assertEquals(saToken[2], "'#'");
	assertEquals(saToken[3], "CarrierSequenze");
	assertEquals(saToken[4], "''");
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testExplodeRespectingQuotes(){
	try {
			String sTest = "eins 'zweiA zweiB' drei";
			String[] saToken = StringZZZ.explodeRespectingQuotes(sTest, " ");
			assertNotNull(saToken);
			assertEquals(saToken.length, 3);
			assertEquals(saToken[0], "eins");
			assertEquals(saToken[1], "zweiA zweiB");
			assertEquals(saToken[2], "drei");
		}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

public void testFindSorted(){
	try {
	String sTest = "eins  zwei  drei  eins  zwei  vier  acht sechzehn";
	String[] saPattern = {"drei", "zwei"};
	
	ArrayList<String>listaString = StringZZZ.findSorted(sTest, saPattern);
	assertNotNull(listaString);
	assertEquals(listaString.size(), 3);
	assertEquals(listaString.get(0), "zwei");
	assertEquals(listaString.get(1), "drei");
	assertEquals(listaString.get(2), "zwei");
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
}


public void testVecMidCascaded(){
	try{
		Vector3ZZZ<String> vecSolved = new Vector3ZZZ<String>();
		String sFormula;
		String sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		
		//############################################################
		//### Vorgezogener letzer Fehlertest: START
		
		//2.
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<Z>", "</Z>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("", sFormula);
				
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("<Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>", sFormula);
				
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("", sFormula);
				
		
		//### Vorgezogener letzter Fehlertest: ENDE
		//############################################################
		
		
		//##################################################
		//1. Test wenn die Tags nicht enthalten sind
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<nixda>", "</nixda>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals(sTest, sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("", sFormula);
		
		//##################################################
		//2.
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<Z>", "</Z>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("", sFormula);
				
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("<Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>", sFormula);
				
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("", sFormula);
		
		//######################################
		//3.
		sTest = "Anfang<Z>das ist der <Z>[Section a]Number</Z> Test</Z>Ende";						
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<Z>", "</Z>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("Anfang", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("das ist der <Z>[Section a]Number</Z> Test", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("Ende", sFormula);
		
		//##################################################
		//4. Nun Randwerte Testen: links 
		sTest = "<Z>[Section a]Number</Z> Test";
		
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<Z>", "</Z>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("[Section a]Number", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals(" Test", sFormula);
		
		//###################################################
		//5. Nun Randwerte Testen: rechts		
		sTest = "<Z>[Section a]Number</Z>";
		
		vecSolved = StringZZZ.vecMidCascaded(sTest, "<Z>", "</Z>", false, false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("[Section a]Number", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("", sFormula);
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}	
}

public void testVecMidFirst(){
	try{
		Vector3ZZZ<String> vecSolved = new Vector3ZZZ<String>();
		String sFormula;
		String sExpression; String sProof;
		
		//############################################################
		//### Vorgezogener letzer Fehlertest: START
		
		//2a. 
		sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
				
		vecSolved = StringZZZ.vecMidFirst(sExpression, "[", "]", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
		assertEquals(vecSolved.size(), 3);
		
		//Es soll noch 1x der umgebenden Tags vorhanden sein, weil die Separatoren-Tags nicht zuruekgekommen sein sollen.
		sProof = VectorUtilZZZ.implode(vecSolved);
		assertEquals(1,StringZZZ.count(sProof, "["));
		assertEquals(1,StringZZZ.count(sProof, "]"));
		

		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("<Z><Z:Call><Z:Java><Z:Class><Z>", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("ArgumentSection for testCallComputed", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>", sFormula);

		
		//### Vorgezogener letzter Fehlertest: ENDE
		//############################################################
		
		
		
		//#####################################################################		
		
		//1.  wenn die Tags nicht enthalten sind
		sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		
		vecSolved = StringZZZ.vecMidFirst(sExpression, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals(sExpression, sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("", sFormula);


		
		
		//##############################
		//2a. 
		sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		
		vecSolved = StringZZZ.vecMidFirst(sExpression, "[", "]", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
		assertEquals(vecSolved.size(), 3);
		
		//Es soll noch 1x der umgebenden Tags vorhanden sein, weil die Separatoren-Tags nicht zuruekgekommen sein sollen.
		sProof = VectorUtilZZZ.implode(vecSolved);
		assertEquals(1,StringZZZ.count(sProof, "["));
		assertEquals(1,StringZZZ.count(sProof, "]"));
		

		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("<Z><Z:Call><Z:Java><Z:Class><Z>", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("ArgumentSection for testCallComputed", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>", sFormula);

		//+++++++++++++++++++++++++++++
		//2b.
		sExpression = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
		
		vecSolved = StringZZZ.vecMidFirst(sExpression, "[", "]", true); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
		assertEquals(vecSolved.size(), 3);
		
		//Es soll noch 1x der umgebenden Tags mehr vorhanden sein, weil die Separatoren-Tags nicht zuruekgekommen sein sollen.
		sProof = VectorUtilZZZ.implode(vecSolved);
		assertEquals(2,StringZZZ.count(sProof, "["));
		assertEquals(2,StringZZZ.count(sProof, "]"));
		

		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("<Z><Z:Call><Z:Java><Z:Class><Z>[", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("ArgumentSection for testCallComputed", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>", sFormula);
		
		
		//###############################
		
		
		//#####################################################################
		// 6a.
		sExpression = "<Z><Z:BLA>[Section A]Testentry1</Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
		
		vecSolved = StringZZZ.vecMidFirst(sExpression, "<Z:BLA>", "</Z:BLA>", false);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("<Z>", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("[Section A]Testentry1", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("<Z:BLA>[Section B]Testentry2</Z:BLA></Z>", sFormula);
		
		//++++++++++++++++++++++++++++++++++++++++++
		// 6b.
		sExpression = "<Z><Z:BLA>[Section A]Testentry1</Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
		
		vecSolved = StringZZZ.vecMidFirst(sExpression, "<Z:BLA>", "</Z:BLA>", true);
		assertEquals(vecSolved.size(), 3);
		
		sFormula = ((Object)vecSolved.get(0)).toString();
		assertEquals("<Z><Z:BLA>", sFormula);
		
		sFormula = ((Object)vecSolved.get(1)).toString();
		assertEquals("[Section A]Testentry1", sFormula);
		
		sFormula = ((Object)vecSolved.get(2)).toString();
		assertEquals("</Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>", sFormula);
				
		
		
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
}

	//#################################################################################
	//####################################################################################################
	
	
	public void testVecMidFirstKeep(){
		try{
			String sExpression; String sExpressionSolved; String sExpressionSolvedTagKept;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
				
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################
	
	
			//###############################################################
			//1. Zum Verdeutlichen des Unterschieds 
			//Teststring Siehe INI_PATH...
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			sExpressionSolved = "[Section A]Testentry1";
			sExpressionSolvedTagKept = "<Z>[Section A]Testentry1</Z>";
			
			vec = StringZZZ.vecMidFirst(sExpression, ">", "<", false);
			
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z", sFormula0); //!!!
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("/Z>POST", sFormula2); //!!!
			
			
			//#########################################################
			//2a. Der eigentliche Test DIESER Funktion +++++++++++++++++++++
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			vec = StringZZZ.vecMidFirstKeep(sExpression, ">", "<", false); 
			assertEquals(vec.size(), 3);
					
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z>POST", sFormula2);
		
			
			//#########################################################
			//2b. Der eigentliche Test DIESER Funktion +++++++++++++++++++++ 
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			vec = StringZZZ.vecMidFirstKeep(sExpression, "<Z>", "</Z>", false); 
			assertEquals(vec.size(), 3);
					
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z>POST", sFormula2);
			
			
			//##############################################################
			//### 1. Praktische Anwendung
			//##############################################################
			sExpression = "<Z:formula><z:Math><Z:VAL>4.0</Z:val><Z:oP>*</Z:op><Z:val>{[Section for testComputeMathArguments FLOAT]WertB_float}</Z:val></Z:math></Z:formula>";
			vec = StringZZZ.vecMidFirstKeep(sExpression, "<Z:vAl>", "</z:VAL>", false, 42);
			assertEquals(vec.size(), 3);
			
			assertEquals(((Object)vec.get(0)).toString(), "<Z:formula><z:Math><Z:VAL>4.0</Z:val><Z:oP>*</Z:op><Z:val>");
			assertEquals(((Object)vec.get(1)).toString(), "{[Section for testComputeMathArguments FLOAT]WertB_float}");
			assertEquals(((Object)vec.get(2)).toString(), "</Z:val></Z:math></Z:formula>");
			
			//##############################################################
			//### 2. Praktische Anwendung
			//##############################################################
			sExpression = "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
			vec = StringZZZ.vecMidFirstKeep(sExpression, "<Z:class>", "</z:Class>", false, 16);
			assertEquals(vec.size(), 3);
			
			assertEquals(((Object)vec.get(0)).toString(), "<Z:Java><Z:Class>");
			assertEquals(((Object)vec.get(1)).toString(), "{[ArgumentSection for testCallComputed]JavaClass}");
			assertEquals(((Object)vec.get(2)).toString(), "</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>");
			
			
			//####################################################
			//3. Zum Verdeutlichen des Unterschieds
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			vec = StringZZZ.vecMidKeepSeparatorCentral(sExpression, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolvedTagKept, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("POST", sFormula2);
			
			
							
			//############################################################
			//### Besonderheiten testen.
			//############################################################			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//1. Test wenn die Tags nicht enthalten sind
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			vec = StringZZZ.vecMidFirst(sExpression, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals(sExpression, sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula2);
			
			
			
								
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testVecMidCascadedKeep(){
		//try{
			String sExpression; String sExpressionSolved; String sExpressionSolvedTagKept; String sSepLeft; String sSepRight;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
			
			
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################
	
			
			
			//###############################################################################
			//Einfacher Teststring Siehe INI_PATH...
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			sExpressionSolved = "[Section A]Testentry1";
			sExpressionSolvedTagKept = "<Z>[Section A]Testentry1</Z>";
			sSepLeft=">";
			sSepRight= "<";
			testVecMidCascadedKeep_simple_(sExpression, sExpressionSolved, sExpressionSolvedTagKept, sSepLeft, sSepRight);
	
			//+++++++++++++++++++++++++++++++++++++++++
			sExpression = "PRE<Z>[Section A]Testentry1</Z>POST";
			sExpressionSolved = "[Section A]Testentry1";
			sExpressionSolvedTagKept = "<Z>[Section A]Testentry1</Z>";
			sSepLeft="<Z>";
			sSepRight= "</Z>";
			testVecMidCascadedKeep_simple_(sExpression, sExpressionSolved, sExpressionSolvedTagKept, sSepLeft, sSepRight);
	
			
			
			//###############################################################
			
			//###############################################################################
			//Complexer Teststring Siehe JAVA_CALL...
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sExpressionSolved = "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
			sExpressionSolvedTagKept = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sSepLeft=">";
			sSepRight="<";			
			testVecMidCascadedKeep_cascaded_(sExpression, sExpressionSolved, sExpressionSolvedTagKept, sSepLeft, sSepRight);
		
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
			//Das Ergebnis ist zwar zum Aufloesen von den XML-Tags nicht sinnvoll, aber ein guter Test...
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sExpressionSolved = "ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod";
			sExpressionSolvedTagKept = "{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}";
			sSepLeft="{[";
			sSepRight="}";			
			testVecMidCascadedKeep_cascaded02_(sExpression, sExpressionSolved, sExpressionSolvedTagKept, sSepLeft, sSepRight);
		
			
			
			//###############################################################
			
			
			
//		}catch(ExceptionZZZ ez){
//			fail("Method throws an exception." + ez.getMessageLast());
//		}
	}
	
	private void testVecMidCascadedKeep_simple_(String sExpressionIn, String sExpressionSolvedIn, String sExpressionSolvedTagKeptIn, String sSepLeftIn, String sSepRightIn){
		try{
			String sExpression; String sExpressionSolved; String sExpressionSolvedTagKept; String sSepLeft; String sSepRight;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			

			//###############################################################################
			//Einfacher Teststring Siehe INI_PATH...			
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn; 
			sExpressionSolvedTagKept = sExpressionSolvedTagKeptIn;
			sSepLeft = sSepLeftIn;
			sSepRight = sSepRightIn;
			
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
				
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################
	
			
			
	
			//###############################################################
			//1. Zum Verdeutlichen des Unterschieds 
			vec = StringZZZ.vecMidCascaded(sExpression, ">", "<", false);
			
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z", sFormula0); //!!!
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("/Z>POST", sFormula2); //!!!
			
			
			//#########################################################
			//2a. Der eigentliche Test DIESER Funktion, Separator bleibt erhalten +++++++++++++++++++++
			vec = StringZZZ.vecMidCascadedKeep(sExpression, sSepLeft, sSepRight, false); 
			assertEquals(vec.size(), 3);
					
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(StringZZZ.count(sProof, "["),1);
			assertEquals(StringZZZ.count(sProof, "]"),1);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z>POST", sFormula2);
										
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	private void testVecMidCascadedKeep_cascaded_(String sExpressionIn, String sExpressionSolvedIn, String sExpressionSolvedTagKeptIn, String sSepLeftIn, String sSepRightIn){
		try{
			String sExpression; String sExpressionSolved; String sExpressionSolvedTagKept; String sSepLeft; String sSepRight;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			

			//###############################################################################
			//Einfacher Teststring Siehe INI_PATH...			
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn; 
			sExpressionSolvedTagKept = sExpressionSolvedTagKeptIn;
			sSepLeft = sSepLeftIn;
			sSepRight = sSepRightIn;
			
			
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
				
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################
	
			
			
	
			//###############################################################
			//1. Zum Verdeutlichen des Unterschieds 
			vec = StringZZZ.vecMidCascaded(sExpression, ">", "<", false);
			
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2, StringZZZ.count(sProof, "["));
			assertEquals(2, StringZZZ.count(sProof, "]"));
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z:Call", sFormula0); //!!!
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("/Z:Call>POST", sFormula2); //!!!
			
			
			//#########################################################
			//2a. Der eigentliche Test DIESER Funktion +++++++++++++++++++++
			vec = StringZZZ.vecMidCascadedKeep(sExpression, sSepLeft, sSepRight, false); 
			assertEquals(vec.size(), 3);
					
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2, StringZZZ.count(sProof, "]"));
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z:Call>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Call>POST", sFormula2);
							
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	private void testVecMidCascadedKeep_cascaded02_(String sExpressionIn, String sExpressionSolvedIn, String sExpressionSolvedTagKeptIn, String sSepLeftIn, String sSepRightIn){
		try{
			String sExpression; String sExpressionSolved; String sExpressionSolvedTagKept; String sSepLeft; String sSepRight;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			

			//###############################################################################
			//Einfacher Teststring Siehe INI_PATH...			
			sExpression = sExpressionIn;
			sExpressionSolved = sExpressionSolvedIn; 
			sExpressionSolvedTagKept = sExpressionSolvedTagKeptIn;
			sSepLeft = sSepLeftIn;
			sSepRight = sSepRightIn;
			
			
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
				
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################
	
			
			
	
			//###############################################################
			//1. Zum Verdeutlichen des Unterschieds 
			vec = StringZZZ.vecMidCascaded(sExpression, sSepLeft, sSepRight, false);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z:Call><Z:Java><Z:Class>", sFormula0); //!!!
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Method></Z:Java></Z:Call>POST", sFormula2); //!!!
			
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(1, StringZZZ.count(sProof, "[")); //!!! eine eckige Klammer ist ja in Separator
			assertEquals(2, StringZZZ.count(sProof, "]"));
			
			//#########################################################
			//2a. Der eigentliche Test DIESER Funktion +++++++++++++++++++++
			vec = StringZZZ.vecMidCascadedKeep(sExpression, sSepLeft, sSepRight, false); 
			assertEquals(vec.size(), 3);
					
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("PRE<Z:Call><Z:Java><Z:Class>{[", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("}</Z:Method></Z:Java></Z:Call>POST", sFormula2);
					
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2, StringZZZ.count(sProof, "]"));
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}

	
	//###########################################################################

	public void testVecMidFirstKeepSeparatorCentral(){
		try{
			String sTest; String sExpressionSolved;
			String sFormula0; String sFormula1; String sFormula2;
			String sProof; Vector<String> vec;
			
			sTest = "<Z>[Section A]Testentry1</Z>";
			
			//##################################################
			//### Letzter fehlgeschlagener Test START
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//1. Test wenn die Tags nicht enthalten sind
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula2);
	
			
			
			//### Letzter fehlgeschlagener Test ENDE
			//##################################################						
			//############################################################
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//1. Test wenn die Tags nicht enthalten sind
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula2);
	
			//2. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z>[Section A]Testentry1</Z>";
						
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(1, StringZZZ.count(sProof, "["));
			assertEquals(1, StringZZZ.count(sProof, "]"));
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sTest, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula2);
			
			//3. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z>[Section A]Testentry1</Z>";
			sExpressionSolved = "[Section A]";
			
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "[", "]", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(1, StringZZZ.count(sProof, "["));
			assertEquals(1, StringZZZ.count(sProof, "]"));
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("<Z>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals(sExpressionSolved, sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("Testentry1</Z>", sFormula2);
			
			
			//#################################################
			//#################################################
			sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
						
			//++++++++++++++++++++++++++++++++++++++++++++++++
			//Test wenn die Tags nicht enthalten sind
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula2);
				
			//++++++++++++++++++++++++++++++++++++++++++++++++
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<Z:Java>", "</Z:Java>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2, StringZZZ.count(sProof, "["));
			assertEquals(2, StringZZZ.count(sProof, "]"));
			
	
			sFormula0 = ((Object)vec.get(0)).toString();
			assertEquals("<Z><Z:Call>", sFormula0);
			
			sFormula1 = ((Object)vec.get(1)).toString();
			assertEquals("<Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java>", sFormula1);
			
			sFormula2 = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Call></Z>", sFormula2);	
			//############################################################
			
			
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testVecMidKeepSeparatorCentral(){
		try{
			String sTest; String sExpressionSolved;
			String sFormula;
			String sProof; Vector<String> vec;
			//Merke: Es wird hier kein Tag - entfernt. Der Boolsche Parameter steht nur für "exactMatch".
			
			//##################################################
			//### Letzter fehlgeschagener Test: START
			
			//2. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidKeepSeparatorCentral(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2,StringZZZ.count(sProof, "]"));
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
			
			//### Letzter fehlgeschagener Test: ENDE
			//##################################################
			
			//############################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++			
			//1. Test wenn die Tags nicht enthalten sind
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidFirst(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
	
			//2. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidKeepSeparatorCentral(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2,StringZZZ.count(sProof, "]"));
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
			//#################################################
			//#################################################
			
			//#####################################################################
			//3. Test wenn die Tags nicht enthalten sind
			sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
			
			vec = StringZZZ.vecMidKeepSeparatorCentral(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
	
			//++++++++++++++++++++++++++++++++++++++++++++
			//4. Erst einmal einen inneren Ausdruck holen, damit nur noch die beiden Z-Ausdrücke aufeinander folgen...
			sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
			
			vec = StringZZZ.vecMidKeepSeparatorCentral(sTest, "<Z:Java>", "</Z:Java>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("<Z><Z:Call>", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("<Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java>", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Call></Z>", sFormula);
	
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//5. Mal die aeusseren Z-Tags weglassen und dann wirklich den erste Z-Tag holen.
			sTest = "<Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>";
			
			vec = StringZZZ.vecMidFirstKeepSeparatorCentral(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			
			//Es soll noch 1x der umgebenden Tags mehr vorhanden sein, weil die Separatoren-Tags nicht zuruekgekommen sein sollen.
			sProof = vec.get(1);
			assertEquals(1,StringZZZ.count(sProof, "<Z>"));
			assertEquals(1,StringZZZ.count(sProof, "</Z>"));
			
	
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("<Z:Call><Z:Java><Z:Class>", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("<Z>[ArgumentSection for testCallComputed]JavaClass</Z>", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>", sFormula);	
			//#########################################################
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testVecMidKeepSeparator(){
		try{
			String sTest; String sExpressionSolved;
			String sFormula;
			String sProof; Vector<String> vec;
			//Merke: Es wird hier kein Tag - entfernt. Der Boolsche Parameter steht nur für "exactMatch".
			
			//##################################################
			//### Letzter fehlgeschagener Test: START
			
			//2. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidKeepSeparator(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2,StringZZZ.count(sProof, "]"));
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("<Z>", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("<Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA>", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("</Z>", sFormula);
			
			//### Letzter fehlgeschagener Test: ENDE
			//##################################################
			
			//############################################################
			
			//++++++++++++++++++++++++++++++++++++++++++++++++			
			//1. Test wenn die Tags nicht enthalten sind
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidFirst(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
	
			//2. ++++++++++++++++++++++++++++++++++++++++++++++++
			sTest = "<Z><Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA></Z>";
			
			vec = StringZZZ.vecMidKeepSeparator(sTest, "<Z>", "</Z>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			//Es soll noch 2x diese speziellen inhaltlichen Tags vorhanden sein, weil in diesem "Cascaded" Tag halt 2 dieser Tags liegen.
			sProof = VectorUtilZZZ.implode(vec);
			assertEquals(2,StringZZZ.count(sProof, "["));
			assertEquals(2,StringZZZ.count(sProof, "]"));
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("<Z>", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("<Z:BLA>[Section A]Testentry1<Z:BLA><Z:BLA>[Section B]Testentry2</Z:BLA>", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("</Z>", sFormula);
			//#################################################
			//#################################################
			
			//#####################################################################
			//3. Test wenn die Tags nicht enthalten sind
			sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
			
			vec = StringZZZ.vecMidKeepSeparator(sTest, "<nixda>", "</nixda>", false);//wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals(sTest, sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("", sFormula);
	
			//++++++++++++++++++++++++++++++++++++++++++++
			//4. Erst einmal einen inneren Ausdruck holen, damit nur noch die beiden Z-Ausdrücke aufeinander folgen...
			sTest = "<Z><Z:Call><Z:Java><Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call></Z>";
			
			vec = StringZZZ.vecMidKeepSeparator(sTest, "<Z:Java>", "</Z:Java>", false); //wichtig: Diese Seperatoren-Tags sollen nicht zurueckkommen!!!
			assertEquals(vec.size(), 3);
			
			sFormula = ((Object)vec.get(0)).toString();
			assertEquals("<Z><Z:Call><Z:Java>", sFormula);
			
			sFormula = ((Object)vec.get(1)).toString();
			assertEquals("<Z:Class><Z>[ArgumentSection for testCallComputed]JavaClass</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method>", sFormula);
			
			sFormula = ((Object)vec.get(2)).toString();
			assertEquals("</Z:Java></Z:Call></Z>", sFormula);

		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}


	//###############################################################
	public void testCapitalize(){
		try {
			String sTest = "das ist der Test";
			String stemp = StringZZZ.capitalize(sTest);
			assertEquals("Das ist der Test", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testCamelcase(){
		try {
			//Variante 1: Ohne Delimiter
			String sTest = "DASIsTEinTest";
			String stemp = StringZZZ.toCamelCase(sTest);
			assertEquals("dasIstEinTest", stemp);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
	}
	
	public void testAbbreviateStrict(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateStrict(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateStrict(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateStrict(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 2);
			System.out.println(sErg);
			assertEquals("A.", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 3);
			System.out.println(sErg);
			assertEquals("A..", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("A...", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrict(sTest, 4);
			System.out.println(sErg);
			assertEquals("a...", sErg);
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrict(sTest, 5);
			System.out.println(sErg);
			assertEquals("ab...", sErg);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testAbbreviateStrictFromRight(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateStrictFromRight(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateStrictFromRight(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 2);
			System.out.println(sErg);
			assertEquals(".A", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 3);
			System.out.println(sErg);
			assertEquals("..A", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("...A", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 4);
			System.out.println(sErg);
			assertEquals("...g", sErg);
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateStrictFromRight(sTest, 5);
			System.out.println(sErg);
			assertEquals("...fg", sErg);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testAbbreviateDynamic(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateDynamic(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateDynamic(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateDynamic(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 2);
			System.out.println(sErg);
			assertEquals("A.", sErg);
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("AAA", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("AA.", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 3);
			System.out.println(sErg);
			assertEquals("A..", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamic(sTest, 4);
			System.out.println(sErg);
			assertEquals("AAA.", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamic(sTest, 4);
			System.out.println(sErg);
			assertEquals("a...", sErg); /// DAS IST EINE BESONDERHEIT, da besonders langer String
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamic(sTest, 5);
			System.out.println(sErg);
			assertEquals("abc..", sErg);
			
			//++++++++++++++++++
			sTest = "abcdefghijklmenop";
			sErg = StringZZZ.abbreviateDynamic(sTest, 6); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("abc...", sErg);
			
			
			//++++++++++++++++++
			sTest = "use.openvpn.serverui.component.IPExternalUpload.DlgIPExternalOVPN";
			sErg = StringZZZ.abbreviateDynamic(sTest, 20); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("use.openvpn.serve...", sErg);
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testAbbreviateDynamicLeft(){
		try{
			String sTest = "";
			String sErg = StringZZZ.abbreviateDynamicLeft(sTest, 2);
			assertEquals("", sErg); 
			
			//+++++++++++++++++
			sTest = "A";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 1);
			assertEquals("A", sErg);
			
			//++++++++++++++++++
			try{
				sTest = "AA";
				sErg = StringZZZ.abbreviateDynamicLeft(sTest, 1);
				fail("Method should have thrown an exception");
			 
			}catch(ExceptionZZZ ez){
				//HIER WIRD EIN FEHLER ERWARTET
			}
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 2);
			System.out.println(sErg);
			assertEquals(".A", sErg);
			
			//+++++++++++++++++++
			
			sTest = "AAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals("AAA", sErg);
			
			//+++++++++++++++++++
			sTest = "AAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals(".AA", sErg);
			
			//++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 3);
			System.out.println(sErg);
			assertEquals("..A", sErg);
			
			//+++++++++++++++++++++
			sTest = "AAAAA";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 4);
			System.out.println(sErg);
			assertEquals(".AAA", sErg);
			
			//+++++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 4);
			System.out.println(sErg);
			assertEquals("...g", sErg); /// DAS IST EINE BESONDERHEIT, da besonders langer String
			
			//+++++++++++++++++++
			sTest = "abcdefg";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 5);
			System.out.println(sErg);
			assertEquals("..efg", sErg);  //Das ist wieder anders als bei der abbreviateStrict !!!
			
			//++++++++++++++++++
			sTest = "abcdefghijklmenop";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 6); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertEquals("...nop", sErg);
			
			//++++++++++++++++++
			sTest = "use.openvpn.serverui.component.IPExternalUpload.DlgIPExternalOVPN";
			sErg = StringZZZ.abbreviateDynamicLeft(sTest, 22); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==22);
			assertEquals("...d.DlgIPExternalOVPN", sErg);
			
			sTest = sErg;
			sErg = StringZZZ.abbreviateDynamic(sTest, 21); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==21);
			assertEquals("...d.DlgIPExternalOV.", sErg);
			
			sErg = StringZZZ.abbreviateDynamic(sTest, 20); //Auch hier wieder Besonderheit, da besonders langer String
			System.out.println(sErg);
			assertTrue(sErg.length()==20);
			assertEquals("...d.DlgIPExternal..", sErg);
			
			
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testIndexOfAll(){
		try {
		String sTest = "das:ist:ein:Test:";
		String [] saToFind = {":"};
		Integer[] intaIndex = StringZZZ.indexOfAll(sTest, saToFind);
		assertNotNull(intaIndex);
		assertEquals(4,intaIndex.length);
		assertEquals(3,intaIndex[0].intValue());
		assertEquals(7, intaIndex[1].intValue());
		assertEquals(11, intaIndex[2].intValue());
		assertEquals(16, intaIndex[3].intValue());
		
		
		//++++++++++++ Das Ergebnis ist nicht sortiert
		String[] saToFind2 = {":", "s"};
		intaIndex = StringZZZ.indexOfAll(sTest, saToFind2);
		assertNotNull(intaIndex);
		assertEquals(7,intaIndex.length);
		assertEquals(3,intaIndex[0].intValue());
		assertEquals(7, intaIndex[1].intValue());
		assertEquals(11, intaIndex[2].intValue());
		assertEquals(16, intaIndex[3].intValue());
		assertEquals(2, intaIndex[4].intValue());
		assertEquals(5, intaIndex[5].intValue());
		assertEquals(14, intaIndex[6].intValue());
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testMatchesPattern_ForIniExpression(){
		try{
			//#########################################################
			//1. Methode: Ermittle ob ein Ausdruck wie im Kernel-Ini-Expression-Path vorkommt
			String sPattern = KernelZFormulaIni_PathZZZ.sTAG_NAME;
			String sString = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			

			sString ="this is text";
			sPattern = "this is text";
			boolean btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			sString ="this is text";
			sPattern = ".*is.*";
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			
//			sString="\n//zum verzweifeln\n";
//			sPattern = "//[^\\r\\n]*[\\r\\n]"; //contains a Java or C# slash-slash comment. Merke fuer Java Ausfuehurung die Backslashe im String escaped.
//			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
//			assertTrue(btemp);
			
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			sPattern = ".*[\\[]*[\\]]*.";  //finde einen Ausdruck in eckigen Klammern
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			sPattern = ".*<Z>.*[\\[]*[\\]].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text 
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Aber in einem JSON-ARRAY String wird obiges auch fuendig.
			//Daher die Hochkommata ausschliessen.
			sString = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sPattern = ".*<Z>.*[\\[]*[\\]].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text 
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			
			//# Das waere das Pattern fuer das JSONARRAY
			sString = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sPattern = ".*<Z>.*\\[\".*\"\\].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text UND auf jeden Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist); dito umgekehrt fuer die geschlossene eckige Klammer.
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			//## Das gleiche(!) Pattern darf aber bei einem INI-Pfad nichts finden
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertFalse(btemp);
			
			//##
			
			//### Wir muessen aber ein Pattern entwickeln, das eine Pfad findet, ein JSONARRAY aber nicht		
			sString = KernelJsonArrayIniSolverZZZTest.sEXPRESSION_JSONARRAY01_DEFAULT;
			sPattern = ".*<Z>.*\\[[^\"].*[^\"]\\].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text UND auf jeden Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist).
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertFalse(btemp);
			
			//#### 	Das gleiche Pattern muss aber eine Path finden
			sString = KernelCallIniSolverZZZTest.sEXPRESSION_JAVACALL01_DEFAULT;
			btemp = StringZZZ.matchesPattern(sString, sPattern, false);
			assertTrue(btemp);
			
			//####
			
		
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			
	}catch(ExceptionZZZ ez){
		fail("Method throws an exception." + ez.getMessageLast());
	}
	}
	
	public void testMatchesPattern(){
		try{
			//#########################################################
			//1. Methode: Nur die Werte sollen in dem String vorkommen (und keine anderen)
			String sPattern = "1234567890ABCD"; //Alle HEX Werte, die in einer Notes-DokumentenId vorkommen k�nnen
			
			String sString = "1234567890ABCD";
			boolean btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "1234567890ABCD098"; //L�ngerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "90ABCD098"; //K�rzerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertTrue(btemp);
			
			sString = "E234567890ABCD"; //Anderer/Falscher Wert am Anfang
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCE"; //Anderer/Falscher Wert am Ende
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567E90ABCD"; //Anderer/Falscher Wert in der Mitte
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDE"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDEF"; //Anderer/Falscher Wert am Ende und String laenger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "E1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "EF1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "34567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "4567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "E1234567890AB"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
			sString = "EF1234567890A"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, -1);
			assertFalse(btemp);
			
//			#########################################################
			//2. Methode: Keiner der Werte darf in dem Pattern String vorkommen
			//Das spare ich mir, weil es nur der umgekehrte Fall ist
			
			//########################################################
			//3. Methode RegEx verwenden			
			sPattern = "[0-9A-D]+";
			
			sString = "1234567890ABCD";
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "1234567890ABCD098"; //L�ngerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "90ABCD098"; //K�rzerer String als der PatternString
			 btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertTrue(btemp);
			
			sString = "E234567890ABCD"; //Anderer/Falscher Wert am Anfang
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCE"; //Anderer/Falscher Wert am Ende
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567E90ABCD"; //Anderer/Falscher Wert in der Mitte
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDE"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "1234567890ABCDEF"; //Anderer/Falscher Wert am Ende und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "E1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "EF1234567890ABCD"; //Anderer/Falscher Wert am Anfang und String l�nger als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "34567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "4567890ABCDE"; //Anderer/Falscher Wert am Ende und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "E1234567890AB"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 1
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
			sString = "EF1234567890A"; //Anderer/Falscher Wert am Anfang und String K�RZER als Pattern String 2
			btemp = StringZZZ.matchesPattern(sString, sPattern, 1);
			assertFalse(btemp);
			
				
		}catch(ExceptionZZZ ez){
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testReplace() {
		try{
			String sReplaceOrig=""; String sReplaced=""; String sErg = "";
			
			//++++ Ersetzungen testen	
			sReplaceOrig = "aaabbbcccdddeeefffeeedddcccbbbaaa";
			sReplaced    = "aaabbbcccZZZeeefffeeeZZZcccbbbaaa";
			sErg = StringZZZ.replace(sReplaceOrig,"ddd","ZZZ");
			assertEquals(sReplaced, sErg); 
			
					
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testReplaceFirst() {
		try{
			String sReplaceOrig=""; String sReplaced=""; String sErg = "";
			
			//++++ Ersetzungen testen	
			sReplaceOrig = "aaabbbcccdddeeefffeeedddcccbbbaaa";
			sReplaced    = "aaabbbcccZZZeeefffeeedddcccbbbaaa";
			sErg = StringZZZ.replaceFirst(sReplaceOrig,"ddd","ZZZ");
			assertEquals(sReplaced, sErg); 
			
					
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testReplaceCharacterGerman(){
		try{
			String sReplaceOrig="";
			String sErg = "";
			
			//++++ Ersetzungen testen
			//A) Ae	++++++++++++++++++++++++++++++++		
			sReplaceOrig = "Maenner";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Männer", sErg); 
			
			//B) Oe ++++++++++++++++++++++++++++++++
			sReplaceOrig = "Moerser";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Mörser", sErg); 
			
			sReplaceOrig = "Oelde";  //intern wird nur eld analysiert (erster und letzter Buchstabe wird nicht betrachtet).
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			//C) Ss
			sReplaceOrig = "Waffenss";  //Dabei wird intern nur affens ber�cksichtig, daher keine Umwandlung !!!
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Asseln";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Odyssee";  //Das y wird als Ausnahme im Regulären Ausdruck berücksichtig, daher keine Umwandlung
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig =  "OdXssee";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("OdXßee", sErg);
			
			
			//D) Ue  //Intern wird hier das ganze Wort betrachtet
			sReplaceOrig = "lquelle"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "ilquelle"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Quelle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//Qualle darf garnicht davon betroffen sein
			sReplaceOrig = "Qualle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "Queen"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
						
			
			
			//#### Ue am Anfang
			sReplaceOrig = "Ueber"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Über", sErg); 
			
			
			//##### Kombinationen
			sReplaceOrig="Muessen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("Müssen", sErg); 
			
			sReplaceOrig = "MMuessen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("MMüssen", sErg); 
			
			//##### Kombinationen mit moegliche mehrere Ersetzungen
			//  !!! Hier unterscheidet sich das Testergebnis von der "Ein Zeichen Ersetzung"
			sReplaceOrig = "groesste";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals("grösste", sErg); //!!! hier kommt es darauf an was zuerst ersetzt wird oe vor ss
			
			//###### Wortlaenge
			sReplaceOrig = "EU";  //es werden nur Worte >= 3 Buchstaben ersetzt. Also Abkuerzungen ausschliessen
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			//##### Keine Ersetzung bei 3 oder mehr aufeinanderfolgenden Vokalen
			sReplaceOrig = "treuer";   // 3 und mehr aufeinanderfolgende Vokale  werden mit RegEx geprueft
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "nachbauen";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//und nun mehr als 3
			sReplaceOrig = "Schueeeeebe"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			//###### Umlautkontext beruecksichtigen, heuristischer Ansatz fuer Eigennamen
			sReplaceOrig = "Suez";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "Goethe";
			sErg = StringZZZ.replaceCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
	
			
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testReplaceOneCharacterGerman(){
		try{
			String sReplaceOrig="";
			String sErg = "";
			
			//++++ Ersetzungen testen
			//A) Ae	++++++++++++++++++++++++++++++++		
			sReplaceOrig = "Maenner";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("Männer", sErg); 
			
			//B) Oe ++++++++++++++++++++++++++++++++
			sReplaceOrig = "Moerser";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("Mörser", sErg); 
			
			sReplaceOrig = "Oelde";  //intern wird nur eld analysiert (erster und letzter Buchstabe wird nicht betrachtet).
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			//C) Ss
			sReplaceOrig = "Waffenss";  //Dabei wird intern nur affens ber�cksichtig, daher keine Umwandlung !!!
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Asseln";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Odyssee";  //Das y wird als Ausnahme im Regulären Ausdruck berücksichtig, daher keine Umwandlung
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig =  "OdXssee";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("OdXßee", sErg);
			
			
			//D) Ue  //Intern wird hier das ganze Wort betrachtet
			sReplaceOrig = "lquelle"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "ilquelle"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg); 
			
			sReplaceOrig = "Quelle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//Qualle darf garnicht davon betroffen sein
			sReplaceOrig = "Qualle"; //Das Q ist als Ausnahme im intern verwendeten RegEx-Ausdruck definiert
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "Queen"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
						
			
			
			//#### Ue am Anfang
			sReplaceOrig = "Ueber"; //Darf nicht veraendert werden
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("Über", sErg); 
			
			
			//##### Kombinationen
			sReplaceOrig="Muessen";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("Müssen", sErg); 
			
			sReplaceOrig = "MMuessen";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("MMüssen", sErg); 
			
			//##### Moegliche mehrere Ersetzungen
			sReplaceOrig = "groesste";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals("grösste", sErg); //!!! hier kommt es darauf an was zuerst ersetzt wird oe vor ss
			
			//###### Wortlaenge
			sReplaceOrig = "EU";  //es werden nur Worte >= 3 Buchstaben ersetzt. Also Abkuerzungen ausschliessen
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			//##### Keine Ersetzung bei 3 oder mehr aufeinanderfolgenden Vokalen
			sReplaceOrig = "treuer";   // 3 und mehr aufeinanderfolgende Vokale  werden mit RegEx geprueft
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "nachbauen";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			//und nun mehr als 3
			sReplaceOrig = "Schueeeeebe"; //Merke: Das Q ist auch als Ausnahme im intern verwendeten RegEx-Ausdruck definiert (s. Quelle")
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			
			//###### Umlautkontext beruecksichtigen, heuristischer Ansatz fuer Eigennamen
			sReplaceOrig = "Suez";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			sReplaceOrig = "Goethe";
			sErg = StringZZZ.replaceOneCharacterGerman(sReplaceOrig);
			assertEquals(sReplaceOrig, sErg);
			
			}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
			}
		}
	
	public void testReplaceLeft(){
		String sValue = null;
		try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "xabcbaaa";			
			sValue = StringZZZ.replaceLeft(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "xxxbcbaaa";
			sValue = StringZZZ.replaceLeft(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
						
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testReplaceRight(){
		String sValue = null;
		try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "aaabcbax";			
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "aaabcbxxx";
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceRight(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	
	public void testReplaceFromLeft1() {
		String sValue = null;
		try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "xabcbaaa";			
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "xaabcbaaa";
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			//+++++++++++++++++++++++++++++++++
			sOrg = "xaaabcbaaa";
			
			sOld = "aa";
			sNew = "x";
			sErg = "xxabcbaaa";
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "xxaabcbaaa";
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		
			sOld = "z";
			sNew = "yy";
			sErg = "xaaabcbaaa";
			sValue = StringZZZ.replaceFromLeft1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		
	}
	
	
	public void testReplaceFromRight1() {
		String sValue = null;
		try{			
			String sOrg="aaabcbaaa";
						
			String sOld = "aa";
			String sNew = "x";
			String sErg = "aaabcbax";			
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "aaabcbaax";
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "x";
			sNew = "yy";
			sErg = sOrg;
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			//+++++++++++++++++++++++++++++++++
			sOrg = "aaabcbaaax";
			
			sOld = "aa";
			sNew = "x";
			sErg = "aaabcbaxx";
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
			sOld = "a";
			sNew = "x";
			sErg = "aaabcbaaxx";
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
			
		
			sOld = "z";
			sNew = "yy";
			sErg = "aaabcbaaax";
			sValue = StringZZZ.replaceFromRight1(sOrg, sOld, sNew);
			assertEquals(sErg, sValue);
		
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
		
		
	}
	
	public void testReplaceFarFrom(){
		try{
			String sErg = "";
				
			String sOrg="aaabcbaaa";
			String sOld = "c";
			String sNew = "bcb";
			
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals(sOrg, sErg);
			
			sNew = "aa";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabaabaaa", sErg);
			
			sNew = "aca";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabacabaaa", sErg);
			
			sNew = "";
			sErg = StringZZZ.replaceFarFrom(sOrg, sOld, sNew);
			assertEquals("aaabbaaa", sErg);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testStartsWithIgnoreCase(){
		try{
			boolean bErg = false;
				
			String sString = "Lorem Ipsum";
			String sMatch = "Lore";
			
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMatch);
			assertTrue(bErg);
			
			String sMiss = "NIXDA";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMiss);
			assertFalse(bErg);
			
			
			sMiss = "Viel länger als Lorem Ispum";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMiss);
			assertFalse(bErg);
			
			sMatch = "loreM";
			bErg = StringZZZ.startsWithIgnoreCase(sString, sMatch);
			assertTrue(bErg);
						
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testShorten(){
		try{
			boolean bErg = false;
				
			String sString = "Lorem Ipsum";
			String sMatch = "Lorem Ipsum";
			
			String sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_NONE, 1);		
			assertTrue(sMatch.equals(sErg));
			
			sMatch = "Lrm psm";			
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL, 1);		
			assertTrue(sMatch.equals(sErg));
			
			sMatch = "Lrm Ipsm";
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL_LOWERCASE, 1);		
			assertTrue(sMatch.equals(sErg));		
			
			sMatch = "Lorem psum";
			sErg = StringZZZ.toShorten(sString, StringZZZ.iSHORTEN_METHOD_VOWEL_UPPERCASE, 1);		
			assertTrue(sMatch.equals(sErg));																				
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}				
	}
	
	public void testIsCharacterDoubled() {
		try {
			//angelehnt an eine Validierung von GetOptZZZ
			boolean bValue=false;			
			char cDelim; String sPattern;
			
			//Positivtest
			sPattern = "a||b:";
			cDelim='|';			
			bValue = StringZZZ.isCharacterDoubled(sPattern,cDelim);
			assertTrue(bValue);
			
			//Negativtest
			sPattern = "a|b:c.";
			cDelim='|';			
			bValue = StringZZZ.isCharacterDoubled(sPattern,cDelim);
			assertFalse(bValue);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}

	
	public void testIsCharacterBehind() {
		try {
			//angelehnt an eine Validierung von GetOptZZZ
			boolean bValue=false;
			char[]caDelim= {':','.','|'};
			char cDelim; String sPattern;
			
			//Positivtest
			sPattern = "a|.b:";
			cDelim='|';			
			bValue = StringZZZ.isCharacterBehind(sPattern,cDelim,caDelim);
			assertTrue(bValue);
			
			//Negativtest
			sPattern = "a|b:c.";
			cDelim='|';			
			bValue = StringZZZ.isCharacterBehind(sPattern,cDelim,caDelim);
			assertFalse(bValue);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testIsJson() {
		try{
			boolean bTest;
			
			String sString = "Lorem Ipsum";
			bTest = StringZZZ.isJson(sString);	
			assertFalse(bTest);						
			
			String sValue = "[[\"110917       \", 3.0099999999999998, -0.72999999999999998, 2.8500000000000001, 2.96, 685.0, 38603.0], [\"110917    \", 2.71, 0.20999999999999999, 2.8199999999999998, 2.8999999999999999, 2987.0, 33762.0]]";
			bTest = StringZZZ.isJson(sValue);	
			assertTrue(bTest);
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	}
	
	public void testIsNumericWithPrefix() {
		try{
			String sString = "Lorem Ipsum";
			boolean bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString="---1";		
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString = "-1";
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertTrue(bTest);		
			
			//######
			sString="+++1";		
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertFalse(bTest);
			
			sString = "+1";
			bTest = StringZZZ.isNumericWithPrefix(sString);	
			assertTrue(bTest);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}	
	}
	
	public void testHasConsecutiveDuplicateCharacter() {
		try {
		String sString; char cMatch;
		
		
		sString = "Lorem Ipsum";
		boolean bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);	
		assertFalse(bTest);
		
		sString="---1";		
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1";
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString);
		assertFalse(bTest);	
		
		
		//###############################
		sString = "template//";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertTrue(bTest);
		
		sString = "template//TEST";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertTrue(bTest);
		
		sString = "template/TEST/";
		cMatch='/';
		bTest = StringZZZ.hasConsecutiveDuplicateCharacter(sString, cMatch);
		assertFalse(bTest);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	

	
	public void testEndsWithConsecutiveDuplicateCharacter() {
		try {
		String sString = "Lorem Ipsum";
		boolean bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);	
		assertFalse(bTest);
		
		sString="-1--";		
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1---";
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertTrue(bTest);
		
		sString = "-1---2-";
		bTest = StringZZZ.endsWithConsecutiveDuplicateCharacter(sString);
		assertFalse(bTest);
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
}//End class
