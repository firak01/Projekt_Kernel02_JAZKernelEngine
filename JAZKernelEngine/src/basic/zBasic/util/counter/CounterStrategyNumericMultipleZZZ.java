package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyNumericMultipleZZZ extends AbstractCounterStrategyNumbersOnlyZZZ{
	public CounterStrategyNumericMultipleZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	@Override
	public boolean checkSyntax(String sTotal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sTotal)) break main;
			
			String sLetterFirst = StringZZZ.letterFirst(sTotal);
			
			//A) Multiple: 
			for(int icounter=1;icounter<=sTotal.length()-1;icounter++){
				String stemp=StringZZZ.mid(sTotal, icounter, 1);
			    if(!sLetterFirst.equals(stemp)){
			    	ExceptionZZZ ez = new ExceptionZZZ("MultipleStrategy: Alle Zeichen des ASCII-Zählers müssen gleich sein. String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
			    }
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}

	@Override
	public int computeNumberForString(String sTotal) throws ExceptionZZZ {				
		int iReturn = 0;
		
		main:{
			char[] caValue = sTotal.toCharArray();
			if(caValue.length>=2){
				char c = caValue[caValue.length-1];
				
				//Serielle Zählvariante
				int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
				iC = this.getDigitValueForPositionValue(iC);
				iReturn= iC + (this.getDigitValueMax() * (caValue.length-1));	//An der letzten Stelle den ermittelten Wert nehmen	 und hinzuzählen						
			}
			
			char c = caValue[0];
			int iC = CounterByCharacterAscii_NumericZZZ.getPositionForChar(c);
			if(caValue.length<=1){
				//Ohne Übertrag
				iReturn = this.getDigitValueForPositionValue(iC);
			}else{
				iReturn = iReturn + iC;
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
//				int iModPosition = this.getPositionValueForDigitValue(iMod);
//				if(iDiv>=1){
//					iModPosition=iModPosition-1;//Übertrag
//				}
				//Ermittle den "Modulo"-Wert und davon das Zeichen
				int iModPosition;
				if(iDiv==0){
					iModPosition = iMod +1;
				}else{
					iModPosition = iMod;
				}
				
			//Ermittle den "Modulo"-Wert und davon das Zeichen
			String sCharacter=null;
			if(iMod>=1){
				sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPosition(iModPosition);	
				sReturn = sCharacter;
			}else if(iMod==0){
				sCharacter = CounterByCharacterAscii_NumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX);
				sReturn = "";
			}
			
			//Zusammenfassen der Werte: Multiple Strategie
			boolean bLeftAligned = this.isLeftAligned();
			if(bLeftAligned){
				sReturn = CounterStrategyHelperZZZ.getStringConsolidated(sCharacter, iDiv) + sReturn;
			}else{
				sReturn = sReturn + CounterStrategyHelperZZZ.getStringConsolidated(sCharacter, iDiv);
			}
			}
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}


}
