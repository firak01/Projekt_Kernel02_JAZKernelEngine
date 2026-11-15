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

public class CounterByCharacterAscii_AlphanumericSignificantZZZTest  extends TestCase{
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
	    
	   
	    
 public void testGetNumberForStringAlphanumeric_StrategySignificant(){
	    	String stemp; int itemp; int itempLeft; int itempRight; int itempCheck; boolean btemp;
	    	ICounterStrategyAlphanumericZZZ objCounterStrategy = null;
	    	try{
	    		objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
	    	} catch (ExceptionZZZ ez) {
	    		ez.printStackTrace();
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
	    	try {
		    	stemp = "0aA"; //Groß-/Kleinschreibung nicht mischen
		    	itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);//Parameter true="Serielle Strategy"
		    	fail("Method should have thrown an exception for the string '"+stemp+"'");
						    	
			} catch (ExceptionZZZ ez) {
				//Erwartetete Exception
			} 
	    	
	    	//Merke: Bei der Signifikant-Strategie ist die Positon der Zeichen zueinander hinsichtlich der Gültigkeit egal....
	    	//Gehe daher sofort zu den "Berechnungstests":
	    	
	    	
	    	//##### GÜLTIGE WERTE
	    	try {
				stemp = ""; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", -1,itemp); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1

		    	String sCheck = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itemp, objCounterStrategy);
		    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", stemp, sCheck);		    			    	
		    	
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	try {
				stemp = "0"; 
				itemp = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy);
				btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(stemp, itemp);
		    	assertTrue("Fehler beim Check auf Null Werte", btemp);
		    	assertEquals("Erwarteter Wert ", 0,itemp); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1

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
		    	assertEquals("Links- oder Rechtsbündig soll bei '0' (also gleichen Zeichen) den gleichen Zahlenwert haben", itempLeft, itempRight);		   		    
			} catch (ExceptionZZZ ez) {
				fail("Method throws an exception." + ez.getMessageLast());
			} 
	    	
	    	//++++++++++++++++++++++++++++++
		    	try {				
					stemp = "00";
			    	objCounterStrategy.isLeftAligned(false);
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73		    
					fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
		    	}catch (ExceptionZZZ ez){
		    		//Erwarteter Fehler
		    	}
			    		
			    	//Mache die Gegenprobe
		    	try {				
					stemp = "00";
			    	objCounterStrategy.isLeftAligned(true);
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig 73		    
					fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
		    	}catch (ExceptionZZZ ez){
		    		//Erwarteter Fehler
		    	}
		    	
		    	//###################################
		    	//### Tests mit der "private" Funktion, die alles kann....
		    	//###################################
		    	try {				
					stemp = "AA";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;						
					}
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	
		    	try {				
					stemp = "AAA";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    	try {				
					stemp = "ABC";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}			    				    	
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					fail("Method throws an exception." + ez.getMessageLast());
				} 
		    	
		    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try {		
				objCounterStrategy.isLeftAligned(false);
				stemp = "0Z";
				itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //linksbündig Fehler    
				fail("Methode sollte eine Exception geworfen haben: Führende 0 ist nicht erlaubt, da sich der String aus dem Wert nicht wiederherstellen lässt.");
	    	}catch (ExceptionZZZ ez){
	    		//Erwarteter Fehler
	    	}

			try {				
				objCounterStrategy.isLeftAligned(true);
				stemp = "0Z";
				itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(stemp,objCounterStrategy); //rechtsbündig 73		    
				assertEquals("Erwarteter Wert ", 1260,itempRight); //Merke: Hier wird der Stellenwert berechnet. Und der ist um -1 kleiner als derASCII-Zeichnewert von "0"=1
				
				//+++ Gegenprobe
				String stempRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
				assertEquals("Erwarteter Wert ", stemp,stempRight);
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			} 	
		    	
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					fail("Method throws an exception." + ez.getMessageLast());
				} 	
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			try{
					stemp = "1Z9B";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					stemp = "B9Z1";
					btemp = getNumberForStringAlphanumeric_StrategySignificantTest_(objCounterStrategy,stemp);
					if(!btemp){
						ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Fehler beim String '" + stemp + "'", KernelZZZ.iERROR_RUNTIME, CounterByCharacterAscii_AlphanumericSignificantZZZTest.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					fail("Method throws an exception." + ez.getMessageLast());
				}				    						
	    }
 
	 private boolean getNumberForStringAlphanumeric_StrategySignificantTest_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric){
			boolean bReturn = false;
			main:{
				try{
					String stemp; String sCheckLeft; String sCheckRight; int itemp; int itempLeft; int itempRight; int itempRightCheck;  int itempLeftCheck; boolean btemp;													
					String sAlphanumericNormed = null; String sAlphanumericReversedNormed = null;
										
					//##################################################
			    	//### Den String testen
			    	//##################################################	
					objCounterStrategy.isLeftAligned(false);
					sAlphanumericNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumeric);				
					itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormed,objCounterStrategy); 
					itempLeftCheck = itempLeft; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	
			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);			    	
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormed, sCheckLeft);	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isLeftAligned(true);
			    	sAlphanumericNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumeric);	
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericNormed,objCounterStrategy); 
			    	itempRightCheck = itempRight; //Variable speichern für die Umkehrung des Strings
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);		
			    				    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericNormed, sCheckRight);	
			    	
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphanumericNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericNormed + "'.", itempLeft==itempRight);
			    	}
			    	
			    	
			    	//##################################################
			    	//### Den String umdrehen und erneut testen
			    	//##################################################			    	
			    	String sAlphanumericReversed = StringZZZ.reverse(sAlphanumeric);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isLeftAligned(false);
			    	sAlphanumericReversedNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumericReversed);					    
			    	itempLeft = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericReversedNormed,objCounterStrategy); 
			    	btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericReversedNormed, itempLeft);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie andersherum und andersbündig orientiert.  '" + sAlphanumericReversedNormed +"'", itempLeft,itempRightCheck);

			    	//Mache die Gegenprobe
			    	sCheckLeft = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempLeft, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericReversedNormed, sCheckLeft);		    			    				    	    					    	    	
		    	
			    	//+++++++++++++++++++++++++++++++++++
			    	objCounterStrategy.isLeftAligned(true);
			    	sAlphanumericReversedNormed = getAlphanumericNormed_(objCounterStrategy, sAlphanumericReversed);	
			    	itempRight = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sAlphanumericReversedNormed,objCounterStrategy); 
					btemp = assertCheckNullBordersAlphanumericStrategyBasedSignificant_(sAlphanumericReversedNormed, itempRight);
			    	assertTrue("Fehler beim Check auf Null Werte", btemp);	
			    	assertEquals("Umgedrehter String soll den gleichen Zahlenwert haben wie anders-bündig orientiert.  '" + sAlphanumericReversedNormed +"'", itempLeft,itempRightCheck);
			    	
			    	//Mache die Gegenprobe
			    	sCheckRight = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(itempRight, objCounterStrategy);
			    	assertEquals("Gegenprobe wurde erfolgreich erwartet.", sAlphanumericReversedNormed, sCheckRight);
			    	
			    	//+++++++++++++++++++++++++++++++++++
			    	if(!StringZZZ.isPalindrom(sAlphanumericReversedNormed)){
			    		assertFalse("Links- oder Rechtsbündig darf NICHT den gleichen Zahlenwert haben, außer bei 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericReversedNormed + "'.", itempLeft==itempRight);
			    	}else{
			    		assertTrue("Links- oder Rechtsbündig MUSS den gleichen Zahlenwert haben, da es ein 'Palindrom' wie z.B. AA. String ist '" + sAlphanumericReversedNormed + "'.", itempLeft==itempRight);
			    	}
			    			
			    	bReturn = true;
				} catch (ExceptionZZZ ez) {
					fail("Method throws an exception." + ez.getMessageLast());
				} 				    	    								
			}//end main:
			return bReturn;
		}
	 
	 /** TODO GOON IcounterStrategyAlphanumericSignificant als neues Interface und diese Methode darin einbauen.
	 * @param objCounterStrategy
	 * @param sAlphanumeric
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:33:20
	 * @throws ExceptionZZZ 
	 */
	private String getAlphanumericNormed_(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric) throws ExceptionZZZ{
		 String sReturn = null;
		 main:{
			 boolean bLowercase = objCounterStrategy.isLowercase();
		 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
			if(!objCounterStrategy.isLeftAligned()){
				sReturn = StringZZZ.stripLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }




}//end class
	

