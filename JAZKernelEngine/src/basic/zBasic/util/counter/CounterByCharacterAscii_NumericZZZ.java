package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class CounterByCharacterAscii_NumericZZZ <T extends ICounterStrategyNumericZZZ> extends AbstractCounterByCharacterAsciiNumericZZZ{
	//private ICounterStrategyNumericZZZ objCounterStrategy;
	private T objCounterStrategy;
	
	
	//##### DAMIT DER COUNTER ÜBER DIE FACTORY ERZEUGT WERDEN KANN UND SICH DEN AKTUELLEN WERT, ETC. MERKT
	//Konstruktoren
	public CounterByCharacterAscii_NumericZZZ(){
		super();
		this.setValueCurrent(this.getDigitValueMin());
	}
	public CounterByCharacterAscii_NumericZZZ(int iStartingValue){
		super(iStartingValue);		
	}
	public CounterByCharacterAscii_NumericZZZ(int iStartingValue, ICounterStrategyNumericZZZ objCounterStrategy){
		super(iStartingValue, objCounterStrategy);		
	}
	public CounterByCharacterAscii_NumericZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);
	}
	public CounterByCharacterAscii_NumericZZZ(String sStartingValue, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ{
		super(sStartingValue, objCounterStrategy);
	}	
	
	//##### STATISCHE METHODEN ######
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i) throws ExceptionZZZ{
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericSerialZZZ();
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuchstaben
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i, boolean bLeftAligned) throws ExceptionZZZ{
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericSerialZZZ();
		objCounterStrategy.isLeftAligned(bLeftAligned);
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuchstaben
	}
		
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringMultipleForNumber(int i) throws ExceptionZZZ{
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuschstaben
	}
	
	
	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static String getStringForNumber(int i,ICounterStrategyZZZ objCounterStrategy) throws ExceptionZZZ{	
		return CounterByCharacterAscii_NumericZZZ.getStringNumericForNumber_(i, objCounterStrategy);//es gibt keine Kleinbuschstaben
	}
	
	
	/** Merke: Es gibt keine Kleinbuchstaben bei NUMMERN
	 * @param i
	 * @param bMultipleStrategy
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 12:00:15
	 */
	private static String getStringNumericForNumber_(int iNumber, ICounterStrategyZZZ objCounterStrategy) throws ExceptionZZZ{
		String sReturn = null;		
		main:{	
				if(objCounterStrategy==null){
					objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
//					ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
				}
				
				sReturn = objCounterStrategy.computeStringForNumber(iNumber);
				
			
		}//end main:
		return sReturn;		
	}
	
	

	
	public static String getCharForPosition(int i) {
		return i > (CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN-1) && i < (CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX+1) ? String.valueOf((char)(i + '0' - 1)) : null; //Merke: Es gibt keine Kleinbuchstaben Variante
	}
	public static String getCharHighest(boolean bLowercase){
		int  i= CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
		return CounterByCharacterAscii_NumericZZZ.getCharForPosition(i);
	}
	public static String getCharLowest(boolean bLowercase){
		int  i= CounterByCharacterAscii_NumericZZZ.iPOSITION_MIN;
		return CounterByCharacterAscii_NumericZZZ.getCharForPosition(i);
	}
	
	
	/** Grundlage für einen Konstruktor, bei dem ein String als Ausgangszähler übergeben wird.
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 16.03.2019, 08:46:42
	 */
	public static int getPositionForChar(char c){
		int iReturn = -1;
		main:{
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_NumericZZZ.isValidCharacter(c);
			if(!bValid) break main;
			
			//2. Umrechnung auf den ASCII-Integer-Wert
			int i = c;
			
			//3. Umrechnung dieses Wertes auf die Position in Nummerischen ASCII-Bereich
			//String.valueOf((char)(i + '0' - 1))
			iReturn = i - '0' + 1;//Umgerechnet aus der Methode getCharForPositiionInNumeric
							
		}
		return iReturn;
	}
	
	//++++++++++
	public static boolean isValidCharacter(char c){
		boolean bReturn = false;
		main:{
			String s = Character.toString(c);
			bReturn = CounterByCharacterAscii_NumericZZZ.isValidCharacter(s);
		}
		return bReturn;
	}
	
	
	
	//##############################################
	//Statische Methoden, um aus einer Buchstabenkombination eine Zahl zu machen.
	public static int getNumberForString(String sValue) throws ExceptionZZZ{			
				ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericSerialZZZ();
				return CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric_(sValue, objCounterStrategy);						
	}
	public static int getNumberForStringMultiple(String sValue) throws ExceptionZZZ{			
		ICounterStrategyZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
		return CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric_(sValue, objCounterStrategy);						
	}
	public static int getNumberForString(String sValue, ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ{
		return CounterByCharacterAscii_NumericZZZ.getNumberForStringNumeric_(sValue, objCounterStrategy);
	}
	
	private static int getNumberForStringNumeric_(String sValue, ICounterStrategyZZZ objCounterStrategy) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sValue))break main;
			
			//1. Prüfen, ist das überhaupt ein erlaubtes Zeichen?
			boolean bValid = CounterByCharacterAscii_NumericZZZ.isValidCharacter(sValue);
			if(!bValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Ungültiges Zeichen übergeben im String '" + sValue + "'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objCounterStrategy==null){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Kein Strategy - Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//.... Besonderheiten der Zählerstrategien 
			boolean bSyntaxValid = objCounterStrategy.checkSyntax(sValue);
			if(!bSyntaxValid){
				ExceptionZZZ ez = new ExceptionZZZ("NumericCounter: Für die Strategy '" + objCounterStrategy.getClass().getName() + "' ist die Syntax des String snicht korrekt '" + sValue +"'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			iReturn = objCounterStrategy.computeNumberForString(sValue);														
		}//end main:
		return iReturn;
	}

	public static boolean isValidCharacter(String s){
		boolean bReturn = false;
		main:{
			if(s==null) break main;
			if(s.equals("")){ //initialisierung des Werts mit -1
				bReturn = true;
				break main;
			}
			
			String sRegex = CounterByCharacterAscii_NumericZZZ.sREGEX_CHARACTERS;
			
			bReturn = true;
			char[] ca = s.toCharArray();
			for(int icount = 0; icount<= ca.length-1; icount++){
				char ctemp = ca[icount];
				String stemp = Character.toString(ctemp);
				if(!stemp.matches(sRegex)){
					bReturn = false;
					break main;
				}
			}
		}
		return bReturn;
	}
	
//### Aus Interface
	@Override
	public String peekChange(int iValue) throws ExceptionZZZ {
		ICounterStrategyNumericZZZ objCounterStrategy = this.getCounterStrategyObject();
		String sCurrent = CounterByCharacterAscii_NumericZZZ.getStringForNumber(iValue,objCounterStrategy);
		return sCurrent;
	}
	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ{	
		ICounterStrategyNumericZZZ objCounterStrategy = this.getCounterStrategyObject();
		int iValue = CounterByCharacterAscii_NumericZZZ.getNumberForString(sValue,objCounterStrategy);
		this.setValueCurrent(iValue);
	}	
	
	// ++++ Aus Interface
//	public ICounterStrategyNumericZZZ getCounterStrategyObject(){
//		if(this.objCounterStrategy==null){
//			ICounterStrategyNumericZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
//			this.objCounterStrategy = objCounterStrategy;
//		}
//		return this.objCounterStrategy;
//	}
	//nach Umstellung auf Generics
	public T getCounterStrategyObject() throws ExceptionZZZ{
		if(this.objCounterStrategy==null){
			ICounterStrategyNumericZZZ objCounterStrategy = new CounterStrategyNumericMultipleZZZ();
			this.objCounterStrategy = (T) objCounterStrategy;
		}
		return (T) this.objCounterStrategy;
	}
			
	@Override
	public void setCounterStrategyObject(ICounterStrategyNumericZZZ objCounterStrategy){
		this.objCounterStrategy = (T) objCounterStrategy;
	}
	@Override
	public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy) {
		this.objCounterStrategy = (T) objCounterStrategy;
	}
	
	
}
