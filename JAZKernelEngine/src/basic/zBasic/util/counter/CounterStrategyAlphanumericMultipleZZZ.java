package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyAlphanumericMultipleZZZ extends AbstractCounterStrategyAlphanumericZZZ{
	public CounterStrategyAlphanumericMultipleZZZ() throws ExceptionZZZ{
		super();
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
		int iReturn = -1;
		main:{
			if(sTotal==null)break main;
			if(sTotal.equals("")){
				iReturn = -1;
				break main;
			}
			
			String sLetterFirst = StringZZZ.letterFirst(sTotal);
			char c = sLetterFirst.toCharArray()[0];
			int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);
			if (sTotal.length()==1){					
				iReturn = this.getDigitValueForPositionValue(itemp);
			}else if(sTotal.length()>=2){									
//				int iReturntemp = this.getDigitValueForPositionValue(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);
//				iReturntemp = itemp + ((sTotal.length()-1)*iReturntemp);
//				iReturn = this.getDigitValueForPositionValue(iReturntemp);
				int iReturntemp = this.getDigitValueForPositionValue(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);
				if(itemp==CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX){
					iReturntemp = (sTotal.length()-1)*iReturntemp;//+1 Übertrag
				}else{
					iReturntemp = (sTotal.length()-1)*iReturntemp+1;//+1 Übertrag
				}
				iReturn = iReturntemp + this.getDigitValueForPositionValue(itemp);
			}
		}//end main
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
			int iDiv = Math.abs(iNumber / this.getDigitValueMax()); //durch abs wird also intern in ein Integer umgewandelt.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % this.getDigitValueMax();
			
			boolean bLowercase = this.isLowercase();
			if(iMod==0 && iDiv ==0){
				sReturn = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN,bLowercase);
				break main;
			}else if(iMod==0 && iDiv >= 1){
				iDiv=iDiv-1; //Übertrag
				String sCharacter=CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,bLowercase);			
				sReturn =  sCharacter + CounterStrategyHelperZZZ.getStringConsolidated(sCharacter, iDiv);
				break main;
			}else{
			
				//Ermittle den "Modulo"-Wert und davon das Zeichen				
				//Da das Zeichen immer gleich ist, ist die Ausrichtung eigentlich egal.
				//boolean bLeftAligned = this.isLeftAligned();				
				int iModPosition = this.getPositionValueForDigitValue(iMod);
				if(iDiv>=1){
					iModPosition=iModPosition-1;//Übertrag
				}
//				//Zusammenfassen der Werte: Multiple Strategie
				String sCharacter=CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iModPosition,bLowercase);			
				sReturn =  sCharacter + CounterStrategyHelperZZZ.getStringConsolidated(sCharacter, iDiv);				
			}
		}//end main:
		return sReturn;
	}

	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {		
		return this.initClassMethodCallingString();
	}	
}
