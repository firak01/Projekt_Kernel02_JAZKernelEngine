package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.doublevalue.DoubleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

public class CounterStrategyAlphabetSignificantZZZ extends AbstractCounterStrategyAlphabetSignificantZZZ{
		
	public CounterStrategyAlphabetSignificantZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter);
		this.makeReflectableInitialization();	
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter, int iStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, iStartValue);
		this.initClassMethodCallingString();	
	}
	
	public CounterStrategyAlphabetSignificantZZZ(int iLength, String sCounterFillingCharacter, String sStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, sStartValue);
		this.makeReflectableInitialization();
	}

	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sTotal==null) break main;
			if(sTotal.equals("")){ //initialisierung des Werts mit -1
				bReturn = true;
				break main;
			}					
			
			String sLetterLast = StringZZZ.letterLast(sTotal);						
			
			//C) Significant			   
			//C1) Überprüfe hinsichtlich der Groß-/Kleinschreibung. Die Zeichen müssen hier durchgängig groß oder klein sein.
			//Das prüft man am besten durch Kleinsetzung ab und durch Großsetzung.
			String sLowerized = sTotal.toLowerCase();
			boolean bLowerized = false; boolean bCapsized = false;
			if(sLowerized.equals(sTotal)){
				bLowerized = true;
			}
			
			String sCapsized = sTotal.toUpperCase();
			if(sCapsized.equals(sTotal)){
				bCapsized = true;
			}
			if(!(bLowerized || bCapsized)){
			    	ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein hinsichtlich der Groß-/Kleinschreibung. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
			}
			
			//Nur gültig ohne führende "0"
			//Allerdings "0" selbst darf übergeben werden. Man bekommt die führenden "0" Werte nämlich nicht durch den Zahlenwert wieder "rekonstruiert".
			//Merke: Ggfs. verwendete Füllzeichen wurden in diesem Überprüfungsschritt schon zuvor entfernt.
//			if(!sTotal.equals("0")){				
//				if(sTotal.startsWith("0") && !this.isRightAligned()){
//					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				if(sTotal.endsWith("0") && this.isRightAligned()){
//					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//			}
			
			//Hier spielt links-/rechtsbündig KEINE Rolle bzgl. der Gültigkeit:
//			boolean bRightAligned = this.isRightAligned();
//			if(!bRightAligned){
//			
//				//C2a) Die Zeichen links müssen immer das kleinste Zeichen des Zeichenraums sein.
//				String sLetterMin = CounterByCharacterAscii_AlphanumericZZZ.getCharLowest(bLowerized);
//				for (int icount=1;icount<=sTotal.length()-1;icount++){
//					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
//					if(!sLetterMin.equals(stemp)){
//						ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Alle Zeichen links der rechtesten Stelle müssen das kleinste Zeichen sein (kleinstes Zeichen='"+sLetterMin+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;	
//					}
//				}
//			}else{
//				//C2a) Die Zeichen links müssen immer das kleinste Zeichen des Zeichenraums sein.
//				String sLetterMin = CounterByCharacterAscii_AlphanumericZZZ.getCharLowest(bLowerized);
//				for (int icount=sTotal.length()-1;icount>=1;icount--){
//					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
//					if(!sLetterMin.equals(stemp)){
//						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen rechts der linksten Stelle müssen das kleinste Zeichen sein (kleinstes Zeichen='"+sLetterMin+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//						throw ez;	
//					}
//				}
//			}
			
			bReturn = true;
		}//end main
		return bReturn;
		
	}

	@Override
	public int computeNumberForString(String sTotalUnnormed) throws ExceptionZZZ {
		int iReturn = -1;
		
		//wg. der Problematik der Füllzeichen. Arbeite nur mit dahingehend normiertem String. Also führende Füllzeichen wegtrimmen.		
		//String sFiller = CharZZZ.toString(this.getCounterFilling());
		//String sAlphanumericNormed = this.getAlphabetNormed(sTotalUnnormed, sFiller); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
		String sAlphanumericNormed = this.getCounterStringNormed(sTotalUnnormed); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
		String sTotal = sAlphanumericNormed;
		
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			int iTotalIndexLength=sTotal.length()-1;
									
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bRightAligned = this.isLeftAligned();
			if(bRightAligned){
				
				//Hier spielt der Stellenwert eine Rolle.				
			   for(int icount=0; icount<=iTotalIndexLength; icount++){
				   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
				   
				    //Berechnung...
					char c = stemp.toCharArray()[0];
					int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);
					//itemp--; //es gibt keine 0
											
					int icountSignificant = icount; //sTotal.length()-1-icount;
					int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX,icountSignificant);//-1, es gibt keine 0
					int itempSignificant=0; 
					if(icountSignificant==0){
						itempSignificant = itemp*icountSignificantValue-1;//es gibt keine 0, darum -1			
					}else{
						itempSignificant = itemp*icountSignificantValue;
					}
					
					
					if(iReturn<0){
				    	iReturn = itempSignificant;	    					
					}
					else{
						iReturn = iReturn + itempSignificant;
					}					
			   }				   					
			}else{
				
				//Hier spielt der Stellenwert eine Rolle.
			   for(int icount=iTotalIndexLength; icount>=0; icount--){
				   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
				   
				    //Berechnung...
					char c = stemp.toCharArray()[0];
					int itemp = CounterByCharacterAscii_AlphabetZZZ.getPositionForChar(c);
					//itemp--;
														
					int icountSignificant = sTotal.length()-icount-1; //
										
					int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX,icountSignificant);
					int itempSignificant=0;						
					if(icountSignificant==0){
						itempSignificant = itemp*icountSignificantValue-1;//es gibt keine 0, darum -1			
					}else{
						itempSignificant = itemp*icountSignificantValue;
					}		
					
					if(iReturn<0){
				    	iReturn = itempSignificant;		    					
					}
					else{
						iReturn = iReturn + itempSignificant;
					}
			   }
						
			}				
		}//end main;
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumberIn) throws ExceptionZZZ{
		
		
		String sReturn = null;
		main:{
			int itemp; int iChar;String stemp;
			
			ArrayList<String>listas=new ArrayList<String>();
			boolean bLowercase = this.isLowercase();
			boolean bLeftAligned = this.isLeftAligned();
			
			//Variante, basierend auf "Stellenwert", allerdings soll eine neue Stelle das Minimum sein, also nicht wie im Dezimalsystem 8,9,10,.. Statt dessen 8,9,00 oder in der Anwendung: x,y,z,aa,...ab
			listas = CounterStrategyHelperZZZ.makeSignificantCharsForNumber(this, iNumberIn, bLeftAligned, bLowercase, true);
						
			//Das Zusammenfassen der Werte in eine HelperKlasse verlagert						
			//sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas,bLeftAligned);					
			sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas);
		}//end main:
		return sReturn;
	}

	 /**TODO: Das als Bestandteil eines eigenen Interfaces
	 * @param sAlphabet
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 13:28:43
	 * @throws ExceptionZZZ 
	 */
	public String getAlphabetNormed(String sAlphabet, String sCharToStrip) throws ExceptionZZZ{
		 String sReturn = null;
		 main:{
			 boolean bLowercase = this.isLowercase();
		 	//String sCharToStrip = CounterByCharacterAscii_AlphabetZZZ.getCharForPosition(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN, bLowercase);
			if(!this.isLeftAligned()){
				sReturn = StringZZZ.stripLeft(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}else{
				sReturn = StringZZZ.stripRight(sAlphabet, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			}
		 }//end main:
		 return sReturn;
	 }

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}



}
