package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.doublevalue.DoubleZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Enthält statische Methoden, die in mehreren CounterStrategy-Klassen verwendet werden.
 * @author lindhaueradmin, 10.09.2019, 07:19:24
 */
public class CounterStrategyHelperZZZ implements IConstantZZZ{
	/** Berechne die Stellenwerte einer Zahl, bezogen auf ein Stellensystem
	 *   und anhand der Stellenwerte wird dann hier übersetzt in ein Character-Zeichen, das als String umgewandelt zurückgegeben wird.
	 * 
	 * Analog zu: https://www.mathe-lexikon.at/mengenlehre/zahlensysteme/dezimalzahlen/dezimalzahl-in-hexadezimalzahl-umrechnen.html
	 * allerdings erweitert um den Strategieansatz die nächste neue Stelle "mit dem minimalen Zeichen" zu versehen.
	 * 
	 * @param objStrategy
	 * @param iNumberIn
	 * @param bRightAligned
	 * @param bLowercase
	 * @param bNewDigitAsMin
	 * @return
	 * @throws ExceptionZZZ
	 * @author lindhaueradmin, 14.09.2019, 08:54:34
	 */
	public static ArrayList<String> makeSignificantCharsForNumber(ICounterStrategyZZZ objStrategy, int iNumberIn, boolean bLeftAligned,boolean bLowercase, boolean bNewDigitAsMin) throws ExceptionZZZ{
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
			if(objStrategy==null){
			 	ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, CounterStrategyHelperZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(iNumberIn<-1)break main;
			if(iNumberIn==-1){
				listasReturn.add("");	
				break main;
			}

			
			//Nachdem die Ausnahmen abgefangen wurden, die reguläre Verarbeitung
			int itemp; int iChar;String stemp;
			int iNumber = iNumberIn;
			int iRest = iNumberIn+1; //+1 um "0" bei Anfangs-iNumber abzufangen.
			int iDigitPositionCur=0;
			int iDigitPositionValueUsed = objStrategy.getDigitValueMax()+1;
			
			//GGfs. für Debuggen, etwas Reflection
//			int iDigitpositionNext=iDigitpositionCur+1;
//			int iPositionValueCur;
//			boolean bWasIncreased=false;
//			boolean bWasReduced=true;
//			boolean bFinalDigitReached=false;
			
			boolean bDebug=false;			
//			if(iNumberIn==35777){
//				System.out.println("Fehler debuggen " + iNumberIn);
//				bDebug=true;
//			}

			while(iRest > 0){
				//1. Schritt: Teilen durch die Anzahl des "Zeichenraums"
				//Hole jeweils die Werte hinter dem Komma			
				float fRest = (float)iNumber / iDigitPositionValueUsed;  								

				//Bestimme daraus den Wert des Zeichens								
				//1. Schritt: Den Rest um den gerade betrachteten Wert reduzieren						
				double dtemp = DoubleZZZ.pointRight(fRest);					
				double dtemp2 = (dtemp * iDigitPositionValueUsed);	
				
				itemp  = DoubleZZZ.toIntRound(dtemp2);
				iChar = itemp+1;//wegen meiner Zeichenholerrei.
				iRest = DoubleZZZ.pointLeft(fRest);	//iNumber - (iPositionValueCur * iDigitPositionValueUsed);
				
				//Problem: Normalerweise ist dann bei eine Stellenübersprung die vordere Ziffer um +1 erhöht. Also 25 => Z , 26 würde zu BA.
				//              Ich will aber AA als Zählerwert erreichen. 
				//Merke: Entsprechend wird dies durch den JUnit - Test auf "AAA" abgeprüft, beim Alphabet-Test.
				if(bNewDigitAsMin){
				if(iDigitPositionCur>=1){
					if(bDebug) System.out.println(iDigitPositionCur + " . TEST " + iNumberIn + " - Charakter " + iChar + " reduziere wert um -1.");
					
					iChar=iChar-1;
					//bWasReduced = true;
					
					//Hier einen Übertrag quasi wieder rückgängig machen.
					if(iChar==0){
						iChar = iDigitPositionValueUsed; //CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX;
						//bWasReduced=false;
					}
					
					if(DoubleZZZ.toInt(dtemp)==0 && DoubleZZZ.toInt(dtemp2)==0){
						iRest=iRest-1;//Damit ggfs. die Schleife verlassen, Ende.
					}
				}else{
					if(bDebug)  System.out.println(iDigitPositionCur + " . TEST " + iNumberIn + " - Charakter " + iChar + " übernehme Wert.");				
				}
				}
				
				stemp =objStrategy.getCharForPosition(iChar);		
				listasReturn.add(stemp);	
				
				//Gehe zur nächsten Stelle
				iNumber = iRest;
				iDigitPositionCur++;
				//iDigitpositionNext=iDigitpositionCur+1;	
			}
		}
		
		//Idee: Wenn nicht linksorientert, dann hier Umsortieren der Liste.
		if(bLeftAligned==false){ 
			listasReturn = (ArrayList<String>) ArrayListUtilZZZ.reverse(listasReturn);
		}
		
		return listasReturn;
	}
	
	/**Zusammenfassen der Werte: z.B. auch Multiple Strategie
	 * @param sCharacter
	 * @param iDiv
	 * @return
	 * @author lindhaueradmin, 10.09.2019, 07:40:41
	 */
	public static String getStringConsolidated(String sCharacter, int iDiv) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			if(sCharacter==null) break main;
			
			//Zusammenfassen der Werte: Multiple Strategie
			for(int icount=1; icount <= iDiv; icount++){					
					sReturn+=sCharacter;
			}
		}//end main:
		return sReturn;		
	}

	/**Zusammenfassen der Werte: z.B. auch Serial Strategie
	 * @param listas
	 * @return
	 * @author lindhaueradmin, 10.09.2019, 07:21:53
	 * @throws ExceptionZZZ 
	 */
	public static String getStringConsolidated(ArrayList<String>listas) throws ExceptionZZZ {
	//public static String getStringConsolidated(ArrayList<String>listas, boolean bLeftAsigned){
		String sReturn = null;
		main:{
			if(listas==null)break main;
			
			//Die Listenwerte werden von Links beginnend gefüllt, wenn linksbündig,
//			if(bLeftAsigned){	
//				//Reihenfolge in ArrayList umgedreht zusammenfassen.
//				sReturn = ArrayListZZZ.implodeReversed(listas,"");												
//			}else{
				//von rechts beginnend gefüllt, wenn rechtsbündig
				sReturn = ArrayListUtilZZZ.implode(listas, "");						
//			}
		}//end main:
		return sReturn;
	}
	
	 /** TODO GOON IcounterStrategyAlphanumericSignificant als neues Interface und diese Methode darin einbauen.
		 * @param objCounterStrategy
		 * @param sAlphanumeric
		 * @return
		 * @author Fritz Lindhauer, 30.05.2019, 13:33:20
	 * @throws ExceptionZZZ 
		 */
		public static String getAlphanumericNormed(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sAlphanumeric) throws ExceptionZZZ{
			 String sReturn = null;
			 main:{
				 boolean bLowercase = objCounterStrategy.isLowercase();
				 boolean bLeftAligned = objCounterStrategy.isLeftAligned();
				 sReturn = CounterStrategyHelperZZZ.getAlphanumericNormed(sAlphanumeric, bLowercase, bLeftAligned);
			 }//end main:
			 return sReturn;
		 }
		
		public static String getAlphanumericNormed(String sAlphanumeric, boolean bLowercase, boolean bLeftAligned) throws ExceptionZZZ{
			 String sReturn = null;
			 main:{				
			 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
				if(bLeftAligned){
					//Fehlermaker: Wenn Leerstring erlaubt ist, dann ist "0000" auch erlaubt. Also muss alles getrimmt werden.
					sReturn = StringZZZ.trimRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.				
				}else{
					sReturn = StringZZZ.trimLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.								
				}
			 }//end main:
			 return sReturn;
		 }
		
		public static String getNumericNormed(String sAlphanumeric, boolean bLeftAligned) throws ExceptionZZZ{
			 String sReturn = null;
			 main:{				
			 	String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN);
				if(bLeftAligned){
					//Wenn Leerstring erlaubt ist, dann ist "0000" auch erlaubt. Also muss alles getrimmt werden.
					sReturn = StringZZZ.trimRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
				}else{
					sReturn = StringZZZ.trimLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.								
				}
			 }//end main:
			 return sReturn;
		 }
		
		
}