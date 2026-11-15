package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.doublevalue.DoubleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

public class CounterStrategyAlphanumericSignificantZZZ extends AbstractCounterStrategyAlphanumericSignificantZZZ{
	//Wichtig: Die zugreifende Methode if iStartDefault muss final sein, damit auch wirklich auf die Variable dieser Klasse zugegriffen wird. Ansonsten wird auf die Variable der abstrakten Klasse zugegriffen.
	protected int iStartDefault = 0; //Also anders als bei den reinen AlphabetCountern hier mit 0 und nicht mit Leerzeichen beginnen.
	
	public CounterStrategyAlphanumericSignificantZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	public CounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFillingCharacter) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter);
		this.makeReflectableInitialization();	
	}
	
	public CounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFillingCharacter, int iStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, iStartValue);
		this.initClassMethodCallingString();	
	}
	
	public CounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFillingCharacter, String sStartValue) throws ExceptionZZZ{
		super(iLength,sCounterFillingCharacter, sStartValue);
		this.makeReflectableInitialization();
	}
	
	@Override 
	public final int getCounterStartDefault() throws ExceptionZZZ {
		return this.iStartDefault;
	}
	
	@Override
	public final void setCounterStartDefault(int iStart) throws ExceptionZZZ{
		this.iStartDefault = iStart; //Wichtig: Diese Methode muss final sein, damit auch wirklich auf die Variable dieser Klasse zugegriffen wird. Ansonsten wird auf die Variable der abstrakten Klasse zugegriffen.
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
			if(!sTotal.equals("0")){				
				if(sTotal.startsWith("0") && !this.isLeftAligned()){
					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				if(sTotal.endsWith("0") && this.isLeftAligned()){
					ExceptionZZZ ez = new ExceptionZZZ("SignificantStrategy: Ein Wert mit führender '0' darf nicht übergeben werden - führende 0 läßt sich nicht wiederherstellen. Nur als einzelnes Zeichen ist führende '0' erlaubt. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}			
			bReturn = true;
		}//end main
		return bReturn;
		
	}

	@Override
	public int computeNumberForString(String sTotalUnnormed) throws ExceptionZZZ {
		int iReturn = -99;
		
		main:{				
			//wg. der Problematik der führenden 0: Arbeite nur mit dahingehend normiertem String. Also führende 0 wegtrimmen.		
			String sAlphanumericNormed = this.getCounterStringNormed(sTotalUnnormed); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
			String sTotal = sAlphanumericNormed;
			if(sTotal==null)break main;
			if(sTotal.equals("")){
				iReturn = -1;
				break main;
			}
			
			int iTotalIndexLength=sTotal.length()-1;
			
			int iDigitValue_Position_Max = this.getDigitValueMax()+1;
									
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bRightAligned = this.isRightAligned();
			if(bRightAligned){
				
				//Hier spielt der Stellenwert eine Rolle.
			   for(int icount=0; icount<=iTotalIndexLength; icount++){
				   String stemp = StringZZZ.letterAtPosition(sTotal, icount);
				   
				    //Berechnung...
					char c = stemp.toCharArray()[0];
					int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
					itemp--;
											
					int icountSignificant = sTotal.length()-1-icount;
					//int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,icountSignificant);
					int icountSignificantValue = (int) Math.pow(iDigitValue_Position_Max,icountSignificant);
					int itempSignificant = itemp*icountSignificantValue;			
					
					if(iReturn<=0){
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
					int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
					itemp--;
					
					int icountSignificant = icount;
					//int icountSignificantValue = (int) Math.pow(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,icountSignificant);
					int icountSignificantValue = (int) Math.pow(iDigitValue_Position_Max,icountSignificant);
					int itempSignificant = itemp*icountSignificantValue;	
					
					if(iReturn<=0){
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
	public String computeStringForNumber(int iNumberIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			int itemp; int iChar;String stemp;
			
			ArrayList<String>listas=new ArrayList<String>();
			boolean bLeftAligned = this.isLeftAligned();
			boolean bLowercase = this.isLowercase();
			
			//Variante, basierend auf "Stellenwert", allerdings soll eine neue Stelle nicht Minimum sein, das wäre wie im Dezimalsystem 8,9,10,.. Statt 8,9,00 oder in der Anwendung: x,y,z,aa,...ab
			listas = CounterStrategyHelperZZZ.makeSignificantCharsForNumber(this, iNumberIn, bLeftAligned, bLowercase, false);
			
			//Das Zusammenfassen der Werte in eine HelperKlasse verlagert						
			//sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas,bLeftAligned);						
			sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas);
		}//end main:
		return sReturn;
	}

// Ist jetzt in einer Helper Klasse
//	 /**TODO: Das als Bestandteil eines eigenen Interfaces
//	 * @param sAlphanumeric
//	 * @return
//	 * @author Fritz Lindhauer, 30.05.2019, 13:28:43
//	 */
//	public String getAlphanumericNormed(String sAlphanumeric, String sCharToStrip){
//		 String sReturn = null;
//		 main:{
//			 boolean bLowercase = this.isLowercase();		 	
//			if(!this.isLeftAligned()){
//				sReturn = StringZZZ.stripLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
//			}else{
//				sReturn = StringZZZ.stripRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
//			}
//		 }//end main:
//		 return sReturn;
//	 }

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}
}
