package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyNumericSerialZZZ extends AbstractCounterStrategyNumbersOnlyZZZ{
	public CounterStrategyNumericSerialZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;			
			
			String sLetterLast = StringZZZ.letterLast(sTotal);						
			
			//B) Seriell			   
			//B1) Überprüfe hinsichtlich der Groß-/Kleinschreibung. Die Zeichen müssen hier durchgängig groß oder klein sein.
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
			    	ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein hinsichtlich der Groß-/Kleinschreibung. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
			}
			
			String sLetterMax = CounterByCharacterAscii_NumericZZZ.getCharHighest(bLowerized);
			if(this.isLeftAligned()){
				//B2: Die Zeichen links müssen immer das höchste Zeichen des Zeichenraums sein.
				for (int icount=0;icount<=sTotal.length()-2;icount++){
					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
					if(!sLetterMax.equals(stemp)){
						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen links der rechtesten Stelle müssen das höchste Zeichen sein (Höchstes Zeiche='"+sLetterMax+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}
				}
			}else{
				//B2b: Die Zeichen rechts müssen immer das höchste Zeichen des Zeichenraums sein.
				for (int icount=sTotal.length()-1;icount>=1;icount--){
					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
					if(!sLetterMax.equals(stemp)){
						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen links der rechtesten Stelle müssen das höchste Zeichen sein (Höchstes Zeiche='"+sLetterMax+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}
				}				
			}
			
			bReturn = true;
		}//end main
		return bReturn;
		
	}

	@Override
	public int computeNumberForString(String sTotal) throws ExceptionZZZ {
		int iReturn = 0;
		
		main:{
			boolean bLeftAligned = this.isLeftAligned();
			if(bLeftAligned){				
				char[] caValue = sTotal.toCharArray();
				for (int icounter=caValue.length-1; icounter>=0 ; icounter--){
					char c = caValue[icounter];
					
					//Serielle Zählvariante				
					if(icounter==0){
						int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
						iC = this.getDigitValueForPositionValue(iC);
						iReturn+= iC;	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen
						if(caValue.length>=2){
							iReturn+=1; //Stellenübertrag
						}
					}else{
						iReturn+= this.getDigitValueMax() * icounter;//Den "Stellenwert" ermitteln und hinzuzählen.
					}	
				}
			}else{
				char[] caValue = sTotal.toCharArray();
				for (int icounter=0; icounter<= caValue.length-1; icounter++){
					char c = caValue[icounter];
					
					//Serielle Zählvariante					
					if(icounter==(caValue.length-1)){
						int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
						iC = this.getDigitValueForPositionValue(iC);
						iReturn+= iC;	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen
						if(caValue.length>=2){
							iReturn+=1; //Stellenübertrag
						}
					}else{
						iReturn+= this.getDigitValueMax() * icounter;//Den "Stellenwert" ermitteln und hinzuzählen.
					}	
				}
			}		
		}//end main;
		return iReturn;			
	}

	@Override
	public String computeStringForNumber(int iNumber) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(iNumber<-1)break main;
			if(iNumber==-1){
				sReturn="";
				break main;
			}
			
			 //Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			int iDiv = Math.abs(iNumber / this.getDigitValueMax() ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % this.getDigitValueMax();
				
			if(iMod==0 && iDiv==0){
				sReturn = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN);
				break main;
			}else{	
				ArrayList<String>listas=new ArrayList<String>();
				boolean bLeftAligned = this.isLeftAligned();
				//boolean bLowercase = this.isLowercase(); //numerisch nicht vorhanden.
				
				int iModPosition = this.getPositionValueForDigitValue(iMod);
				
				//DA NICHT SIGNIFIKANZ ALSO 0 GÜLTIGES ZEICHEN 
				iModPosition=iModPosition-1;
				
				if(iDiv>=1){
					iModPosition=iModPosition-1;//Übertrag										
				}
				if(bLeftAligned){
					//von Links die Liste beginnen
					for(int icount = 1; icount <= iDiv; icount++){					
						String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
						listas.add(stemp);
					}
					if(iMod>=1){
						iMod = this.getPositionValueForDigitValue(iModPosition);
						String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iMod);					
						listas.add(stemp);
					}
					
					
				}else{
					if(iMod>=1){
						iMod = this.getPositionValueForDigitValue(iModPosition);
						String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iMod);
						listas.add(stemp);
					}
					
					for(int icount = 1; icount <= iDiv; icount++){
						String stemp = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
						listas.add(stemp);
					}	
								
				}
				
				//Das Zusammenfassen der Werte in eine HelperKlasse verlagert						
				//sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas,bLeftAligned);	
				sReturn = CounterStrategyHelperZZZ.getStringConsolidated(listas);
			}
			
								
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}
}
