package basic.zBasic.util.counter;

import java.util.EnumSet;
import java.util.Set;

import junit.framework.TestCase;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetTestFactoryZZZ;
import basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;

public class CounterByCharacterAscii_AlphanumericZZZTest  extends TestCase{
//	 private HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ> hmTestGenerics = null;
	 
	    protected void setUp(){
	      
		//try {			
		
			//### Das spezielle Generics Testobjekt			
//			hmTestGenerics = new HashMapExtendedZZZ<String, EnumSetMappedTestTypeZZZ>();
//			
//			Set<EnumSetMappedTestTypeZZZ> allTypes = EnumSet.allOf(EnumSetMappedTestTypeZZZ.class);
//			for(EnumSetMappedTestTypeZZZ myType : allTypes) {
//				//String sType = myType.getAbbreviation();
//				String sName = myType.name();
//				hmTestGenerics.put(sName,myType);
//			}
			
		
			
		/*
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
		*/
		
	}//END setup
	    
	    private boolean assertCheckNullBordersAlphanumeric_(char cInput,int iResult) throws ExceptionZZZ{
	    	boolean bReturn = false;
	    	main:{
	    		int iAsciiBase = '0';
	    		int iOffset = 0;
	    		boolean bIsLowercase = CharZZZ.isLowercase(cInput);
	    		boolean bIsNumeric = CharZZZ.isNumeric(cInput);
	    		if (bIsNumeric){
	    			iOffset = iAsciiBase-1; 			
	    		}else if(bIsLowercase){
	    			iOffset = 7+7+iAsciiBase-1;
	    		}else{
	    			iOffset = 7+iAsciiBase-1;	
	    		}
	    		
	    			    		
	    		boolean bIsValidCharacter=CounterByCharacterAscii_AlphanumericZZZ.isValidCharacter(cInput);
	    		if(iResult+iOffset>='0' && iResult+iOffset<='9'){
	    			assertTrue("Bei >='0' und <='9' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);					
				}else if(iResult+iOffset>='A' && iResult+iOffset <='Z'){
					assertTrue("Bei >='A' und <='Z' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
				}else if(iResult+iOffset>='a' && iResult+iOffset <= 'z'){
					assertTrue("Bei >='a' und <='z' wird ein gültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);
				}else{
					assertFalse("Es wurde ein ungültiges Zeichen erwartet. Ergebnis '" + iResult + "' für " + cInput, bIsValidCharacter);					
				}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumeric_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput==-1){
	    			if(sResult==null){
	    				assertNotNull("Bei -1  wird keine NULL erwartet. Ergebnis: '" + sResult + "' für " + iInput, sResult);
	    			}else{
	    				assertEquals("Bei -1 wird der Leerstring erwartet","",sResult);
	    			}
	    			bReturn = true;
	    			break main;
	    		}
	    		
	    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNull("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}else if(iInput>CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX){
		    		assertNull("Bei >'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}   		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }	    	    
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(int iInput, String sResult){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput==-1){
	    			if(sResult==null){
	    				assertNotNull("Bei -1  wird keine NULL erwartet. Ergebnis: '" + sResult + "' für " + iInput, sResult);
	    			}else{
	    				assertEquals("Bei -1 wird der Leerstring erwartet","",sResult);
	    			}
	    			bReturn = true;
	    			break main;
	    		}
	    		
	    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    		if(iInput< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1){
		    		assertNull("Bei <'" + (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) + "' wird NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}
		    		
	    		if(iInput>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1){
		    		assertNotNull("Bei >= '" + (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) + "' wird keine NULL erwartet. Ergebnis '" + sResult + "' für " + iInput, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBased_(String sResult, int iInput){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput==-1){
	    			if(sResult==null){
	    				assertNotNull("Bei -1  wird keine NULL erwartet. Ergebnis: '" + sResult + "' für " + iInput, sResult);
	    			}else{
	    				assertEquals("Bei -1 wird der Leerstring erwartet","",sResult);
	    			}
	    			bReturn = true;
	    			break main;
	    		}
	    		
	    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
				int iDiv = Math.abs(iInput / (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1) ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
				int iMod = iInput % (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1);
	    		
				if(iDiv==0 && iMod< CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1){
		    		assertEquals("Bei <'" + (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) + "' wird NULL erwartet. Ergebnis '" + iInput + "' für " + sResult, sResult);
		    	}
		    		
	    		if(iMod>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1){
		    		assertNotNull("Bei >= '" + (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) + "' wird keine NULL erwartet. Ergebnis '" + iInput + "' für " + sResult, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    private boolean assertCheckNullBordersAlphanumericStrategyBasedSignificant_(String sResult, int iInput){
	    	boolean bReturn = false;
	    	main:{
	    		if(iInput==-1){
	    			if(sResult==null){
	    				assertNotNull("Bei -1  wird keine NULL erwartet. Ergebnis: '" + sResult + "' für " + iInput, sResult);
	    			}else{
	    				assertEquals("Bei -1 wird der Leerstring erwartet","",sResult);
	    			}
	    			bReturn = true;
	    			break main;
	    		}
	    		
	    		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    		
	    		//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
				int iDiv = Math.abs(iInput / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
				int iMod = iInput % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	    		
				//Wenn Die 1 = "0" in den Wert 0 geändert wurde (wg. der Berchung des Stellenwerts), dann geht das nicht mehr.
//				if(iDiv==0 && iMod < CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
//		    		assertEquals("Bei <'" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird NULL erwartet. Ergebnis '" + iResult + "' für " + sInput, sInput);
//		    	}
		    		
	    		if(iMod>=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN){
		    		assertNotNull("Bei >= '" + CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN + "' wird keine NULL erwartet. Ergebnis '" + iInput + "' für " + sResult, sResult);
		    	}	    		
	    		bReturn=true;
	    			    		    	
	    	}//end main:
	    	return bReturn;	    	
	    }
	    
	    /** Als Grundlage für den Konstruktor, bei dem das "Startzeichen" übergeben wird. 
	     * 
	     * @author Fritz Lindhauer, 16.03.2019, 08:34:53
	     */
	    public void testGetPositionInAlphanumericForChar(){
	    	try {
	    	//Grenzwerte: Geholt aus der ASCII Tabelle
	    	char ctemp = '/';
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = ':';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '@';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '[';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	
	    	ctemp = '´';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	    	
	    	ctemp = '{';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp); 
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = '0';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(1,itemp);
	    	
	    	ctemp = '9';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(10,itemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'A';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	ctemp = 'a';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(11,itemp);
	    	
	    	//++++++++++++++++++++++++++++++++++++++++
	    	ctemp = 'Z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    	ctemp = 'z';
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(ctemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(ctemp,itemp);
	    	assertTrue("Fehler beim Check auf gültige Werte", btemp);
	    	assertEquals(36,itemp);
	    	
	    	} catch (ExceptionZZZ ez) {
	    		ez.printStackTrace();
	    		fail("Method throws an exception." + ez.getMessageLast());
	    	} 
	    }
	    
	    public void testGetCharForPositionInAlphanumeric(){	    		    	
	    	int itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	    	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	boolean btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
	    	
	    	//+++++++++++++++++++++++++++++++++++++++++++++++
	    	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("0",stemp);
	    	
    			    	
    		//+++++++++++++++++++++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    			    	
    		//++++++++++++++++++++++++++++++++
    		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("Z",stemp);
    		
	    	stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
	        btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("z",stemp);
    		
    		
    		//++++ WEITER WERTE AN "DEN INNEREN RÄNDERN" (also die Stelle an der intern die Sonderzeichen ausgeschnitten werden.
    		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX; //"9"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp,true); //kleinbuschstaben, obwohl es dafür ("9") keine geben soll.
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("9",stemp);
    		
    		//+++++++++
    		itemp = CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1; //"A"....
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp);
	    	btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("A",stemp);
    		
    		stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(itemp, true); //Kleinbuchstaben
    		btemp = assertCheckNullBordersAlphanumeric_(itemp, stemp);
	    	assertTrue("Fehler beim Check auf Null Werte", btemp);
    		assertEquals("a",stemp);
    		
	    }
	    
	   
	    public void testGetNumberForStringAlphanumeric_StrategyMultiple(){
	    	//"MULTIPLE STRATEGY"-Ergebnisse
	    	String stemp; int itemp; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy=null;
	    	try{	    	
	    		objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
	    	} catch (ExceptionZZZ ez) {
	    		fail("Method throws an exception." + ez.getMessageLast());
	    	} 
	    	
	    	//#### GÜLTIGE WERTE
	    	try {
				stemp = "";				
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			}
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {
				stemp = "0";				
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "00"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "9"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "99"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "A"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "AA"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    			    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZZ"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 		
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	int itemptestForNext=0;
			try {
				stemp = "ZZZ"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				itemptestForNext = itemp;
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 	
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				itemptestForNext = itemptestForNext+1;
				
				stemp = "0000"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				assertEquals(itemptestForNext, itemp);
				itemptestForNext = itemp;
				
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    					
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 		
	    }
	    	   
	    public void testGetNumberForStringAlphanumeric_StrategySerial(){
	    	
	    	String stemp; int itemp; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy = null;
	    	try{
	    	 objCounterStrategy = new CounterStrategyAlphanumericSerialZZZ();
	    	} catch (ExceptionZZZ ez) {
	    		ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//Ungültige Werte
	    	try {
				stemp = "?"; //Parameter false="Serielle Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, für die verschiedenen Strategien
	    	//...serielle Strategie
	    	try {
		    	stemp = "0aA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	
	    	
	    	//Ungültige Werte, da die Zeichen nicht in Relation zueinander passen.
	    	//... serielle Strategie
	    	try {
		    	stemp = "01"; //Merke: Die Zeichen rechts müssen immer das höchste Zeichen des Zeichenraums sein.
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Multiple Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	try {
		    	stemp = "0AZZ";//Merke: Die Zeichen rechts müssen immer das höchste Zeichen des Zeichenraums sein. 
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter false="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//Ungültige Werte, da die Zeichen nicht in Relation zueinander passen.
	    	//... serielle Strategie
	    	try {
	    		objCounterStrategy.isLeftAligned(true);
	    	} catch (ExceptionZZZ ez) {
	    		ez.printStackTrace();	
			} 
	    	
	    	try {
		    	stemp = "ZZA0";//Merke: Die Zeichen links müssen immer das höchste Zeichen des Zeichenraums sein. 
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter false="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//######### GÜLTIGE WERTE ###############
	    	//"SERIAL STRATEGIE"-Ergebnisse
	    	//Merke: Einzelner Wert braucht nicht der höchste Wert zu sein.	    
	    	try {
				stemp = ""; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
	    	
	    	try {
				stemp = "0"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	//+++ LEFTALIGNED=FALSE
			try {		
				objCounterStrategy.isLeftAligned(false);
				stemp = "0Z";							
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(36,itemp);
		    	
		    	//Mache die Gegenprobe//itemp= 36!!!	
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try {		
				objCounterStrategy.isLeftAligned(false);
				stemp = "0ZZ";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(71,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			
			try {		
				objCounterStrategy.isLeftAligned(false);
				stemp = "1ZZ";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(72,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try {		
				objCounterStrategy.isLeftAligned(false);
				stemp = "ZZZZ";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(140,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			try {
				objCounterStrategy.isLeftAligned(false);
				stemp = "9";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			    	
			try {
				objCounterStrategy.isLeftAligned(false);
				stemp = "9Z"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "A"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				objCounterStrategy.isLeftAligned(false);
				stemp = "AZ";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZZ"; //Parameter true="Multiple Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ LEFTALIGNED=TRUE
			try {		
				objCounterStrategy.isLeftAligned(true);
				stemp = "Z0";							
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(36,itemp);
		    	
		    	//Mache die Gegenprobe//itemp= 36!!!	
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try {		
				objCounterStrategy.isLeftAligned(true);
				stemp = "ZZ0";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals(71,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			
			try {		
				objCounterStrategy.isLeftAligned(true);
				stemp = "ZZ1";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try {		
				objCounterStrategy.isLeftAligned(true);
				stemp = "ZZZZ";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
		
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++			
			try {
				objCounterStrategy.isLeftAligned(true);
				stemp = "9";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    			    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			    	
			try {
				objCounterStrategy.isLeftAligned(true);
				stemp = "Z9"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "A"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				objCounterStrategy.isLeftAligned(true);
				stemp = "ZA";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "Z";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
			try {
				stemp = "ZZ"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBased_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp,  objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    }
	    
 public void testGetNumberForStringAlphanumeric_StrategySignificant(){
	    	
	    	String stemp; int itemp; int itempLeft; int itempRight; int itempCheck; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy = null;
	    	try{
	    		objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//Ungültige Werte
	    	try {
				stemp = "?"; //Parameter false="Significant Strategy"
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	
	    	//Ungültige Werte, für die verschiedenen Strategien
	    	//...serielle Strategie
	    	try {
		    	stemp = "0aA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//Merke: Bei der Signifikant-Strategie ist die Positon der Zeichen zueinander hinsichtlich der Gültigkeit egal....
	    	//Gehe daher sofort zu den "Berechnungstests":	    	
	    	try {
				stemp = "00"; //Nur gültig ohne führende "0", Allerdings "0" selbst darf übergeben werden.
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				fail("Method should have thrown an exception for the string '"+stemp+"'");		    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	stemp = "0Z";//Die führende 0 wird internenin dem Test  "wegnormiert"
			try {					
				btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
				assertTrue("Fehler beim allgemeinen Test.", btemp);
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
			
			try{
				objCounterStrategy.isLeftAligned(true);
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				assertEquals(1260,itemp);
				
				//Gegenprobe
				String stempRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
				assertEquals(stemp,stempRight);
				
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 	
			
			try{
				objCounterStrategy.isLeftAligned(true);
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 	
			
	    	
	    	//##### GÜLTIGE WERTE
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {
				stemp = "";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", -1,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	try {
				stemp = "Z";
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 35,itemp);
		    	
		    	//Mache die Gegenprobe
		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);
	    	} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
//		    			    	
	    	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	    	
		    	try {				
					stemp = "0";
			    	objCounterStrategy.isLeftAligned(false);
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	assertEquals("Erwarteter Wert ", 0,itempLeft); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
			    	
			    	objCounterStrategy.isLeftAligned(true);
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
			    	assertEquals("Erwarteter Wert ", 0,itempRight); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
			    	assertEquals("Links- oder Rechtsbündig soll bei '00' (also gleichen Zeichen) den gleichen Zahlenwert haben", itempLeft, itempRight);
			    		
			    	//Mache die Gegenprobe
			    	//"0" wird allerdings weggetrimmt
			    	objCounterStrategy.isLeftAligned(false);
			    	boolean bLowercase = false;
			    	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			    	String stempStripped = StringZZZ.stripLeft(stemp, sCharToStrip);		    	
			    	String sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe ('0' vorne entfernt) wurde erfolgreich erwartet.", stempStripped, sCheckLeft);		    			    	
			    	
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	//###################################
		    	//### Tests mit der "private" Funktion, die alles kann....
		    	//###################################
		    	try {				
					stemp = "AA";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;						
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	
		    	try {				
					stemp = "AAA";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	try {				
					stemp = "ABC";	
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			    				    	
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
				    	
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z9B";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "B9Z1";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					objCounterStrategy.isLowercase(true);
					stemp = "1z9b";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "B9Z1";
					objCounterStrategy.isLowercase(false);
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				}
			
			
	    }
 
	 private boolean getNumberForStringAlphanumeric_StrategySignificantTest_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{			
				
					bReturn = this.getNumberForStringAlphanumeric_StrategySignificantStringTest_(objCounterStrategy, sAlphanumeric);
			    	if(!bReturn) break main;
			    	
			    	//##################################################
			    	//### Den String umdrehen und erneut testen
			    	//##################################################			    	
			    	String sAlphanumericReversed = StringZZZ.reverse(sAlphanumeric);
			    	bReturn = this.getNumberForStringAlphanumeric_StrategySignificantStringTest_(objCounterStrategy, sAlphanumericReversed);
			    	if(!bReturn) break main;
						    	    								
			}//end main:
			return bReturn;
		}
	 
	 private boolean getNumberForStringAlphanumeric_StrategySignificantStringTest_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric) throws ExceptionZZZ{
		 boolean bReturn = false;
			main:{			
					String stemp; String sCheckLeft; String sCheckRight; int itemp; int itempLeft; int itempRight; int itempRightCheck;  int itempLeftCheck; boolean btemp;													
					String sAlphanumericNormedLeft = null; String sAlphanumericNormedRight = null; String sAlphanumericReversedNormed = null;
										
					//##################################################
			    	//### Den String testen
			    	//##################################################	
					objCounterStrategy.isLeftAligned(false);
					sAlphanumericNormedRight = CounterStrategyHelperZZZ.getAlphanumericNormed(objCounterStrategy, sAlphanumeric);				
					itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormedRight,objCounterStrategy); 
					itempRightCheck = itempRight; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormedRight, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
			    	//Mache die Gegenprobe, itempRight = 13368
			    	sCheckRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);			    	
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormedRight, sCheckRight);	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isLeftAligned(true);
			    	sAlphanumericNormedLeft = CounterStrategyHelperZZZ.getAlphanumericNormed(objCounterStrategy, sAlphanumeric);	
			    	itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormedLeft,objCounterStrategy); 
			    	itempLeftCheck = itempLeft; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormedLeft, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		
			    				    	
			    	//Mache die Gegenprobe, itempLeft = 15958
			    	sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormedLeft, sCheckLeft);	
			    	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(StringZZZ.isPalindrom(sAlphanumericNormedLeft) && StringZZZ.isPalindrom(sAlphanumericNormedRight)){
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormedLeft + "'.", itempLeft==itempRight);
			    	}else{
			    		if(sAlphanumericNormedLeft.length()==1 && sAlphanumericNormedRight.length()==1){//Merke: Bei längerern String ist der String zwar gleich, wird aber aus unterschiedlichen Richtungen gelesen ==> unterschiedliche Zahlenwerte.
			    			assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da sie die Länge 1 haben. String ist jeweils  '" + sAlphanumericNormedLeft + "'.", itempLeft==itempRight);
			    		}else{
			    			assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormedLeft + "'.", itempLeft==itempRight);
			    		}
			    	}
			    	
			    	bReturn = true;
	 }//end main:
		return bReturn;
	 }
	 
public void testGetStringAlphanumericForNumber_StrategyMultiple(){
	try{
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	int itemp = -1;
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, false); // .getCharMultipleForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("",stemp);
				
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, false); // .getCharMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1",stemp); //Kleinbuchstaben für Zahlen gibt es ja nicht.
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1;

	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("00",stemp); //Keine Kleinbuchstaben bei Ziffern
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests	
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("11",stemp);
	
	//... weitere Tests
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("22",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("22",stemp);
	
	
	//... weitere Tests	
	itemp = (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1)*2;
	
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp,  true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
	
	//... weitere Tests
	itemp = (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1)*2 + 1;
		
	//"MULTIPLE STRATEGY"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("000",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringMultipleForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("000",stemp);
	
	
} catch (ExceptionZZZ ez) {
	fail("Method throws an exception." + ez.getMessageLast());
} 
}

public void testGetStringAlphanumericForNumber_StrategySerial(){
	try{
	
	//"SERIAL STRATEGIE"-Ergebnisse
	int itemp = -1;
	
	String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("",stemp);
		
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
	
	//+++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
		    	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1;
	
	//"SERIAL STRATEGIE"-Ergebnisse
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("z",stemp);

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	//######################
	//Nun Werte eingeben, die über 27 liegen. Diese müssen erlaubt sein.
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("0z",stemp);
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//... weitere Tests
 	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1Z",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("1z",stemp);
	
	//"SERIAL STRATEGIE"-Ergebnisse
	itemp = (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1)*2;
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp);
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("ZZ",stemp);
	
	stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true); //true ist lowercase, false ist uppercase
	btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
	assertTrue("Fehler beim Check auf Null Werte", btemp);
	assertEquals("zz",stemp);
	} catch (ExceptionZZZ ez) {
		fail("Method throws an exception." + ez.getMessageLast());
	} 
}

public void testGetStringAlphanumericForNumber_StrategySerialRightAligned(){
	try{
		
		//"SERIAL STRATEGIE"-Ergebnisse, Grossbuchstaben und rechtsbündig 
		int itemp =-1;
		String stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		boolean btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-2;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1;
		
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); 
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("0",stemp); //die Kleinbuchstaben für Zahlen gibt es ja nicht
			    	
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1;
		
		//"SERIAL STRATEGIE"-Ergebnisse
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); //true ist lowercase, false ist uppercase
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z",stemp);

		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		
		//######################
		//Nun Werte eingeben, die über 36 liegen. Diese müssen erlaubt sein.
		//+++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//"SERIAL STRATEGIE"-Ergebnisse LEFTALIGNED = TRUE
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);//bLeftAligned=true
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z0",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true);//bLeftAligned=true
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z0",stemp);
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
		//... weitere Tests
	 	//"SERIAL STRATEGIE"-Ergebnisse
		//1. true ist lowercase, false ist uppercase | 2. true ist rightbound
		itemp = CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);//bLeftAligned=true
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("Z1",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true);//bLeftAligned=true
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("z1",stemp);
		
		//"SERIAL STRATEGIE"-Ergebnisse
		itemp = (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1)*2;
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, false, true);//bRightAligned=true
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("ZZ",stemp);
		
		stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, true, true); //true ist lowercase, false ist uppercase
		btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
		assertTrue("Fehler beim Check auf Null Werte", btemp);
		assertEquals("zz",stemp);
		
		//++++++ Mal einfach nur hochzählen... dann merkt man den Unterschied zu "Signifikant" Strategie
		itemp = (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX-1)*2+1;
		for(int icount=itemp; icount <= 100; icount++){
			stemp = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(icount, false, true);
			btemp = assertCheckNullBordersAlphanumericStrategyBased_(itemp, stemp);
			//assertTrue("Fehler beim Check auf Null Werte", btemp);
			//assertEquals("Z1",stemp);
		}
		//Ergebnisse sind Z2, Z3, ... , ZA, ZB, ..., wenn LINKSORIENTEIRT
		//Das sieht also anders aus als 10, 11, ..., 19, 20 (!!!), 21, .... wie es bei einem rechtsorientierten "signifikatem Stellenwert" wäre. 
		//Die Hinteren Stellen (links-orientiert) bleiben konstant!
						
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 
}

}//end class
	

