package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterStrategyAlphanumericSerialZZZ extends AbstractCounterStrategyAlphanumericZZZ{
	public CounterStrategyAlphanumericSerialZZZ() throws ExceptionZZZ{
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
			
			//Hier spielt links-/rechtsbündig eine Rolle:
			boolean bLeftAligned = this.isLeftAligned();
			String sLetterMax = CounterByCharacterAscii_AlphanumericZZZ.getCharHighest(bLowerized);
			if(bLeftAligned){
				//B2a) Die Zeichen links müssen immer das höchste Zeichen des Zeichenraums sein.				
				for (int icount=0;icount<=sTotal.length()-2;icount++){
					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
					if(!sLetterMax.equals(stemp)){
						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen links der rechtesten Stelle müssen das höchste Zeichen sein (Höchstes Zeichen='"+sLetterMax+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;	
					}
				}								
			}else{
				//B2a) 	Die Zeichen rechts müssen immer das höchste Zeichen des Zeichenraums sein.
				for (int icount=sTotal.length()-1;icount>=1;icount--){			
					String stemp = StringZZZ.letterAtPosition(sTotal,icount);
					if(!sLetterMax.equals(stemp)){
						ExceptionZZZ ez = new ExceptionZZZ("SerialStrategy: Alle Zeichen rechts der linksten Stelle müssen das höchste Zeichen sein (Höchstes Zeichen='"+sLetterMax+"'), String='"+sTotal+"'.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
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
		int iReturn = -99;
		
		main:{
			if(sTotal==null)break main;
			if(sTotal.equals("")){
				iReturn = -1;
				break main;
			}			
			
						
			//Bei der Ermittlung der "zählenden" Stelle spielt links-/rechtsbündig EINE Rolle:
			boolean bLeftAligned = this.isLeftAligned();
			String sLetterCounter = null;
			if(bLeftAligned){
				sLetterCounter = StringZZZ.letterLast(sTotal);					
			}else{
				sLetterCounter = StringZZZ.letterFirst(sTotal);		
			}
			
			//Bei dem Berechnungsalgorithmus selbst spielt links-/rechtsbündig keine Rolle
			//Berechnung...
			char c = sLetterCounter.toCharArray()[0];
			int itemp = CounterByCharacterAscii_AlphanumericZZZ.getPositionForChar(c);			
			if(sTotal.length()==1){					
				int iReturntemp = this.getDigitValueForPositionValue(itemp);
				iReturn = iReturntemp;				    					
			}else if(sTotal.length()>=2){
				int iReturntemp;			
				if(itemp!=CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX){
					iReturntemp = itemp;
				}else{
					iReturntemp = this.getDigitValueForPositionValue(itemp);
				}
				
				
				itemp = this.getDigitValueForPositionValue(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX);
				itemp =  itemp * (sTotal.length()-1);
				
				iReturn = iReturntemp + itemp;
			}
		
			
			//Merke der umgekehrte Weg aus der Zahl einen String zu machen geht so:
			//Ermittle den "Teiler" und den Rest, Also Modulo - Operation
			//int iDiv = Math.abs(i / CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			//int iMod = i % CounterByCharacterAscii_AlphanumericZZZ.iALPHANUMERIC_POSITION_MAX;
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
//			int iDiv = Math.abs(iNumber / CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
//			int iMod = iNumber % CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;

			int iDiv = Math.abs(iNumber / this.getDigitValueMax() ); //durch abs wird also intern in ein Integer umgewandetl.... nicht nur das Weglassen des ggfs. negativen Vorzeichens.
			int iMod = iNumber % this.getDigitValueMax();

			boolean bLowercase = this.isLowercase();
			if(iMod==0 && iDiv==0){
				sReturn = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MIN,bLowercase);
				break main;
			}else{
				ArrayList<String>listas=new ArrayList<String>();
				boolean bLeftAligned = this.isLeftAligned();
										
				int iModPosition = this.getPositionValueForDigitValue(iMod);
				if(iDiv>=1){
					iModPosition=iModPosition-1;//Übertrag
				}
				if(bLeftAligned){
					//Links stehen also zuerst die "gleichen Zeichen"
					for(int icount = 1; icount <= iDiv; icount++){
						String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,bLowercase);
						listas.add(stemp);
					}	
					
					if(iMod>=1){											
						String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iModPosition,bLowercase);
						listas.add(stemp);
					}		
																					
				}else{		
					//Rechts stehen also zuerst die "gleichen Zeichen"
					if(iMod>=1){												
						String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(iModPosition,bLowercase);
						listas.add(stemp);
					}
					
					for(int icount = 1; icount <= iDiv; icount++){
						String stemp = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX,bLowercase);
						listas.add(stemp);
					}																						
				}					
				
				//Idee: Wenn inksorientert, dann hier Umsortieren der Liste.
				if(bLeftAligned){ 
					ArrayListUtilZZZ.reverse(listas);
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
