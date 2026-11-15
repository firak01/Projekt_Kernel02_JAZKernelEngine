package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

/**
 *  
 * 
 * @author Fritz Lindhauer, 08.03.2019, 07:40:07
 * 
 * Lösung basiert darauf, dass man mit Character-Zeichen  rechnen kann.
 * 
	 Eine einfacheres Beispiel ist: 	 https://forum.chip.de/discussion/832166/in-while-schleife-alphabet-hochzaehlen

		//Characters können wie Integer ebenfalls hochgezählt werden,somit entfällt die Suche nach dem Ascii-Wert...so müsste es in etwa dann aussehen 
		public static void main (String args[]) {
		
		   char anfang='a';
		   char ende='z';
		
		   String AbisZ="";
		
		   while (anfang<=ende) {
		   
		      AbisZ=AbisZ+anfang;
		      anfang++;
		
		    }
 * @param <T>

 * 
 */
public abstract class AbstractCounterByCharacterAsciiZZZ<T extends ICounterStrategyZZZ> implements IConstantZZZ, ICounterStringZZZ{
	//20190606: Merke: Wenn man das in den abstrakten Klassen hält, bekommt man 2 objCounterStrategy - Objekte,
	//darum diese Strategy-Objekte nur in den "Endklassen" verwenden. 
	//T objCounterStrategy;
	
	private String sSuffix="";
	private String sPrefix="";	
	private int iValueCurrent=-1; //Die berechneten Strings nicht speichern. Nur den Wert. Die Umrechnung wird dann den jeweiligen Static-Methoden überlassen.
		
	public AbstractCounterByCharacterAsciiZZZ(){		
	}
	
	public AbstractCounterByCharacterAsciiZZZ(int iStartingValue){
		this.setValueCurrent(iStartingValue);
	}
	
	public AbstractCounterByCharacterAsciiZZZ(String sStartingValue) throws ExceptionZZZ{
		this.setValueCurrent(sStartingValue);
	}

		
	//Das Problem ist, dass der Integer Wert nur über eine CounterStrategy errechnet wird.
	//Die Counter Strategy ist aber auf diese "Vererbungsebene" der Abstrakten Klassen nicht vorhanden!!!
	//Darum hier den Konstruktor nicht anbieten
//	public AbstractCounterByCharacterAsciiZZZ(String sStartingValue){
//		//Zuerst den Wert des Strings ermitteln. Diesen kann man dann Wegsichern
//	    //....		
//	}
	
	//### Aus Interface	
	public abstract String peekChange(int iValue) throws ExceptionZZZ;
	public abstract void setValueCurrent(String sValue) throws ExceptionZZZ;	
	
	public int getValueCurrent(){
		return this.iValueCurrent;
	}
	public void setValueCurrent(int iValue){
		this.iValueCurrent=iValue;
	}
	

	public String getStringSuffix(){
		return this.sSuffix;
	}
	public void setStringSuffix(String sSuffix){
		this.sSuffix = sSuffix;
	}
	
	public String getStringPrefix(){
		return this.sPrefix;
	}
	public void setStringPrefix (String sPrefix){
		this.sPrefix=sPrefix;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	public String current() throws ExceptionZZZ {
		int iValueCurrent = this.getValueCurrent();
		return this.peekChange(iValueCurrent);
	}	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.datatype.counter.ICounterStringZZZ#next()
	 */
	public String next() throws ExceptionZZZ{
		//Gibt den wert mit dem Zähler erhöht zurück
		//Prüfe, ob die Strategie besagt, dass dies dafür eine erlaubte Methode ist.				
		boolean btest = this.getCounterStrategyObject().isIncreasableInOtherMethod();
		if(!btest){
			String stest = ReflectCodeKernelZZZ.getClassMethodExternalCallingString();
			String sMethodOfInit=this.getCounterStrategyObject().getClassMethodCallingString();
			
			//Wenn das Erhöhen des Zählers in einer anderen Methode nicht erlaubt ist, gib dann in der anderen Methode den aktuellen Wert zurück.
			if(!stest.equals(sMethodOfInit)) {
				int iValueCurrent = this.getValueCurrent();
				if(iValueCurrent<0) {
					return "0"; //sonst gibt es bei "Buchstabenzählern" Probleme im PostPorcessing
				}else {
					return this.current();
				}
			}
		}
		
		//Wenn erlaubt hisichtlich der Methode, erhöhe den Zähler
		int iValueCurrent = this.getValueCurrent();
		iValueCurrent++;
		this.setValueCurrent(iValueCurrent);
		return this.current();	
	}
	public String change(int iValue) throws ExceptionZZZ{
		//Setze den neuen Wert
		this.setValueCurrent(iValue);
		return this.current();
	}
	//++++++++++++++++++++++++++++++++++++++++++++++++++
	public String peekNext() throws ExceptionZZZ{
		//Gibt den wert wie er sein würde, ohne den Zähler zu erhöhen
		int iValueCurrent = this.getValueCurrent();
		iValueCurrent++;
		return this.peekChange(iValueCurrent);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	public String getString() throws ExceptionZZZ{
		return this.getStringPrefix() + this.current() + this.getStringSuffix();
	}
	
	public String getStringNext() throws ExceptionZZZ{
		//DAS ERHÖHT DEN ZÄHLER		
		return this.getStringPrefix() + this.next() + this.getStringSuffix();		
	}
	
	public String getStringChange(int iValue) throws ExceptionZZZ{
		//DAS ERHÖHT DEN ZÄHLER		
		return this.getStringPrefix() + this.change(iValue) + this.getStringSuffix();		
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	public String getStringPeekNext() throws ExceptionZZZ{
		//DAS ERHÖHT DEN ZÄHLER NICHT
		return this.getStringPrefix() + this.peekNext() + this.getStringSuffix();
	}
	public String getStringPeekChange(int iValue) throws ExceptionZZZ{
		//DAS ÄNDERT DEN ZÄHLER NICHT
		return this.getStringPrefix() + this.peekChange(iValue) + this.getStringSuffix();
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String preValueSetting(String sValue) throws ExceptionZZZ {
		//Da das normalerweise nicht verwendet wird einfach den Eingabewert zurückgeben. Wenn es gebraucht wird, die Methode überschreiben.
		return sValue;
	}
	@Override
	public String postValueSetting(String sValue) throws ExceptionZZZ {
		//Da das normalerweise nicht verwendet wird einfach den Eingabewert zurückgeben. Wenn es gebraucht wird, die Methode überschreiben.
		return sValue;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public String toString(){
		String s = this.getClass().getName();
		try {
			s+=" : " + this.getString();
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return s;
	}
	//+++
		
	//###### AUS Interface - Zugriff über CounterStrategy Objekt
	@Override
	public boolean isRightAligned() throws ExceptionZZZ{
		return this.getCounterStrategyObject().isRightAligned();
	}
	
	@Override
	public void isRightAligned(boolean bValue) throws ExceptionZZZ{
		this.getCounterStrategyObject().isRightAligned(bValue);
	}
	
	@Override
	public boolean isLeftAligned() throws ExceptionZZZ{
		return this.getCounterStrategyObject().isLeftAligned();
	}
	
	@Override
	public void isLeftAligned(boolean bValue) throws ExceptionZZZ{
		this.getCounterStrategyObject().isLeftAligned(bValue);
	}
	

	// ++++ Aus Interface
		@Override
		public abstract void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy);
		
		@Override	
		public abstract T getCounterStrategyObject() throws ExceptionZZZ;
		
}
