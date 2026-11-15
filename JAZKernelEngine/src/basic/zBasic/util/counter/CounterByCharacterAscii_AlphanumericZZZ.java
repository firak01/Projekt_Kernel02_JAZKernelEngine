package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAscii_AlphanumericZZZ <T extends ICounterStrategyAlphanumericZZZ> extends AbstractCounterByCharacterAsciiAlphanumericZZZ {
	//private ICounterStrategyAlphanumericZZZ objCounterStrategy;
	private T objCounterStrategy;
	
//	public static int iPOSITION_MIN=1;  //Merke: die Sonderzeichen werden übersprungen bei Werten >10  und <=16
//	public static int iPOSITION_MAX=36;
//	
//	public static String sREGEX_CHARACTERS="[a-zA-Z0-9]";
	
	public CounterByCharacterAscii_AlphanumericZZZ() throws ExceptionZZZ{
		super();
		int iStartingValue = this.getCounterStrategyObjectDefault().getCounterStart();
		this.setValueCurrent(iStartingValue);
	}
	public CounterByCharacterAscii_AlphanumericZZZ(int iStartingValue) throws ExceptionZZZ {
		super(iStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericZZZ(int iStartingValue, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ {
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public CounterByCharacterAscii_AlphanumericZZZ(ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ {
		super(objCounterStrategy.getCounterStart());
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	//###################
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	 * Der Eingabestring darf nur entweder aus Groß- oder aus Kleinbuschsteben bestehen.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @throws ExceptionZZZ 
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 */
	public static int getNumberForString(String s) throws ExceptionZZZ{		
		return CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(s, null);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie, wenn bMultiple = true.
	 * Der Eingabestring muss aus gleichen Zeichen bestehen. 
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static int getNumberForString(String s, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{		
		return CounterByCharacterAscii_AlphanumericZZZ.getNumberForStringAlphanumeric_(s, objCounterStrategy);
	}
	
	
	/** Merke: Es gibt auch Kleinbuchstaben bei NUMMERN
	 * @param i
	 * @param bMultipleStrategy
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 12:00:15
	 * @throws ExceptionZZZ 
	 */
	private static int getNumberForStringAlphanumeric_(String sTotal, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		int iReturn = -99;		
		main:{
			if(objCounterStrategy==null){
				objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
//				ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
			}
			
			//#### Überprüfe den String	
			//... enthält der Zähler nur gültige Zeichen
			boolean bValid = CounterByCharacterAscii_AlphanumericZZZ.isValidCharacter(sTotal);
			if(!bValid){
				ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: String enthält ungültige Zeichen ('"+sTotal+"')", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//.... Besonderheiten der Zählerstrategien 
			boolean bSyntaxValid = objCounterStrategy.checkSyntax(sTotal);
			if(!bSyntaxValid){
				ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Für die Strategy '" + objCounterStrategy.getClass().getName() + "' ist die Syntax des String snicht korrekt '" + sTotal +"'", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Berechnung...
			iReturn = objCounterStrategy.computeNumberForString(sTotal);

		}//end main:
		return iReturn;		
	}
	
	
	
	//###########################################
	//###########################################
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static String getStringForNumber(int i) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericSerialZZZ();
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
		
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static String getStringForNumber(int i, boolean bLowercase) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericSerialZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Serial"-Strategie. Dies ist Default.
	Z.B. 10 ==> "90" "Serielle" oder beim der "Multiplikator Strategie" "11". Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static String getStringForNumber(int i, boolean bLowercase, boolean bLeftAligned) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericSerialZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		objCounterStrategy.isLeftAligned(bLeftAligned);
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
	public static String getStringForNumber(int i, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
		

	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static String getStringMultipleForNumber(int i) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
	
	/**Behandlung der Werte nach der "Multiple"-Strategie.
	Z.B. 27 ==> ZA "Serielle" oder beim der "Multiplikator Strategie" AA. Multiplikator - Stategie bedeutet: Den Modulo Wert entsprechend häufig darstellen.
	 * @param i
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 09:03:52
	 * @throws ExceptionZZZ 
	 */
	public static String getStringMultipleForNumber(int i, boolean bLowercase) throws ExceptionZZZ{
		ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
		objCounterStrategy.isLowercase(bLowercase);
		return CounterByCharacterAscii_AlphanumericZZZ.getStringAlphanumericForNumber_(i, objCounterStrategy);
	}
	
	/** Merke: Es gibt auch Kleinbuchstaben bei NUMMERN
	 * @param i
	 * @param bMultipleStrategy
	 * @return
	 * @author Fritz Lindhauer, 04.03.2019, 12:00:15
	 * @throws ExceptionZZZ 
	 */
	private static String getStringAlphanumericForNumber_(int iNumber, ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ{
		String sReturn = null;		
		main:{							
			if(objCounterStrategy==null){
				ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein CounterStrategy-Objekt übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sReturn = objCounterStrategy.computeStringForNumber(iNumber);
			
		}//end main:
		return sReturn;		
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
			boolean bValid = CounterByCharacterAscii_AlphanumericZZZ.isValidCharacter(c);
			if(!bValid) break main;
			
			//2. Umrechnung auf den ASCII-Integer-Wert
			int i = c;
								
			//3. Umrechnung dieses Wertes auf die Position im Alphabet
			//    Berücksichtige dabei, ob es ein Klein-oder Großbuchstabe ist.			
				if(i>='0' && i<='9'){
					iReturn = i - '0' + 1;
				}else if(i>='A' && i <='Z'){
					iReturn = i - 'A' + 1;
					iReturn = iReturn + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
				}else if(i>='a' && i <= 'z'){
					iReturn = i - 'a' + 1;
					iReturn = iReturn + CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX;
				}else{
					break main;
				}
							
		}//end main:
		return iReturn;
	}
	
	public static String getCharForPosition(int i) {		
		int iOffset;
		if(i>CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){//Da es eine Lücke von 6 Postionen in den Sonderzeichen gibt, normiere den eingegebenen Wert!!!			
			iOffset = 7;
		}else{
			iOffset = 0; 
		}
		int iNormed = i + iOffset;
		
		return iNormed > (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) && iNormed < (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1+iOffset) ? String.valueOf((char)(iNormed + '0' - 1)) : null;
	}
	
public static String getCharForPosition(int i, boolean bLowercase) {
	if(bLowercase){		
				int iOffset;
				if(i>CounterByCharacterAscii_NumericZZZ.iPOSITION_MAX){//Da es eine Lücke von 6 Postionen in den Sonderzeichen gibt, normiere den eingegebenen Wert!!!			
					iOffset = 7+6+CounterByCharacterAscii_AlphabetZZZ.iPOSITION_MAX; //Also bei Kleinbuchstaben die Großbuchstabenlücke und noch weitere Sonderzeichen (6) überbrücken.
				}else{
					iOffset = 0; 
				}
				int iNormed = i + iOffset;
				return iNormed > (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN-1) && iNormed < (CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX+1+iOffset) ? String.valueOf((char)(iNormed + '0' - 1)) : null; //Bei Kleinbuchstaben sind das andere ASCII Werte. Aber mit 'Zeichen'/Characters kann man wie mit Integer rechnen
	}else{
		return CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(i);
	}
}

public static String getCharHighest(boolean bLowercase){
	int  i= CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MAX;
	return CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(i, bLowercase);
}
public static String getCharLowest(boolean bLowercase){
	int  i= CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN;
	return CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(i, bLowercase);
}

//++++++++++
public static boolean isValidCharacter(char c){
	boolean bReturn = false;
	main:{
		String s = Character.toString(c);
		bReturn = CounterByCharacterAscii_AlphanumericZZZ.isValidCharacter(s);
	}
	return bReturn;
}

public static boolean isValidCharacter(String s){
	boolean bReturn = false;
	main:{
		if(s==null) break main;
		if(s.equals("")){ //initialisierung des Werts mit -1
			bReturn = true;
			break main;
		}
		
		String sRegex = CounterByCharacterAscii_AlphanumericZZZ.sREGEX_CHARACTERS;
		
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
		String sReturn = null;
		main:{
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			sReturn = CounterByCharacterAscii_AlphanumericZZZ.getStringForNumber(iValue, objCounterStrategy);	
			
			//Vor der Rückgabe noch den Wert "nacharbeiten", ggfs.
			sReturn = this.postValueSetting(sReturn);
		}//end main:
		return sReturn;
	}
	
	@Override
	public void setValueCurrent(String sValue) throws ExceptionZZZ{			
		main:{
			//Vor der Eingabe noch den Wert "vorverarbeiten", ggfs.
			sValue = this.postValueSetting(sValue);
		
			ICounterStrategyAlphanumericZZZ objCounterStrategy = this.getCounterStrategyObject();
			int iValue = CounterByCharacterAscii_AlphanumericZZZ.getNumberForString(sValue, objCounterStrategy);
			this.setValueCurrent(iValue);
		}//end main:
	}	
	
	// ++++ Aus Interface
//			public ICounterStrategyAlphanumericZZZ getCounterStrategyObject(){
//				if(this.objCounterStrategy==null){
//					ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();
//					this.objCounterStrategy = objCounterStrategy;
//				}
//				return this.objCounterStrategy;
//			}
	
	//nach Umstellung auf Generics
		public T getCounterStrategyObject() throws ExceptionZZZ{
			if(this.objCounterStrategy==null){
				//Hier den Konkreten Unter-Counter-Typen erzeugen. 				
				T objCounterStrategy = this.getCounterStrategyObjectDefault();							
				this.objCounterStrategy = objCounterStrategy;				
			}
			return (T) this.objCounterStrategy;
		}
		
		public void setCounterStrategyObject(ICounterStrategyAlphanumericZZZ objCounterStrategy){
			this.objCounterStrategy = (T) objCounterStrategy;
		}
		@Override
		public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy) {
			this.objCounterStrategy = (T) objCounterStrategy;
		}
		
		public T getCounterStrategyObjectDefault() throws ExceptionZZZ{
			//20190728: Verwende den global als Default eingestellten Typen
			ICounterStrategyAlphanumericZZZ objCounterStrategy = new CounterStrategyAlphanumericMultipleZZZ();						
			return (T) objCounterStrategy;
		}
}
