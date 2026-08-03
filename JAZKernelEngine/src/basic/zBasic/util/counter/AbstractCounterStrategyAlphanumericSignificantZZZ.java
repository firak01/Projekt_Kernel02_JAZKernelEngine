package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyAlphanumericSignificantZZZ extends AbstractCounterStrategyAlphanumericZZZ implements ICounterStrategyAlphanumericSignificantZZZ{
	
	//TODO: Default Werte gehören eingentlich als Konstante ins Interface.	 	
	int iCounterLengthDefault = 4;
	char cCounterFillingDefault = "0".toCharArray()[0]; //das sollte dann auch "0" sein, oder?;
	private int iCounterLength;
	
	//20190724: Nicht als einzelnen char speichern, sondern als Array.  Hier kann man über die Länge abprüfen, ob ein Zeichen überhaupt gesetzt wurde
	//https://stackoverflow.com/questions/8534178/how-to-represent-empty-char-in-java-character-class
	//Ist noch nichts gesetzt wird dann der DefaultCharachter herangezogen	
	//Ein Leerzeichen, d.h. wenn man die Filling Zeichen verzichten will wäre Character.MIN_VALUE;
	private char[] caCounterFilling = new char[1]; 	//ist also besser als private char cCounterFilling;
	
	public AbstractCounterStrategyAlphanumericSignificantZZZ() throws ExceptionZZZ{
		super();			
		this.setCounterLength(iCounterLengthDefault);
		this.setCounterFilling(cCounterFillingDefault);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, char cCounterFilling) throws ExceptionZZZ{
		this();		
		this.setCounterLength(iLength);
		this.setCounterFilling(cCounterFilling);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling) throws ExceptionZZZ{
		this();		
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);		
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling, int iCounterStart) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);
		this.setCounterStart(iCounterStart);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(int iLength, String sCounterFilling, String sCounterStart) throws ExceptionZZZ{
		this();
		this.setCounterLength(iLength);
		this.setCounterFilling(sCounterFilling);
		this.setCounterStart(sCounterStart);
	}
	public AbstractCounterStrategyAlphanumericSignificantZZZ(boolean bLowercase) throws ExceptionZZZ{
		super();
		this.initClassMethodCallingString();	
		this.isLowercase(true);
	}

	//+++ Aus Interfaces				
		@Override
		public int getCounterLength() {
			if(this.iCounterLength<=0){
				this.iCounterLength= this.iCounterLengthDefault;
			}
			return this.iCounterLength;
		}

		@Override
		public void setCounterLength(int iLength) throws ExceptionZZZ {
			if(iLength<0){				
				ExceptionZZZ ez = new ExceptionZZZ("Ungültiger Wert für CounterLength (= "+iLength+"). Merke: Bei 0 wird später wieder die Defaultlänge genommen.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.iCounterLength=iLength;
		}
		
		@Override 
		public boolean hasCounterFilling(){
			boolean bReturn = false;		
			if(this.caCounterFilling.length>=1){
				char cFilling = this.caCounterFilling[0];
				if(!CharZZZ.isEmptyNull(cFilling)){
					bReturn=true; 
				}				
			}
			return bReturn;
		}

		@Override
		public char getCounterFilling() {		
			if(this.caCounterFilling.length==0){
				this.caCounterFilling = new char[1];
				this.caCounterFilling[0] = this.cCounterFillingDefault;//Merke: Wurde es einmal gesetzt, dann wird nicht wieder das Default-Füllzeichen verwendet werden.
			}
			return this.caCounterFilling[0];			
		}

		@Override
		public void setCounterFilling(char cFilling) {
			if(CharZZZ.isEmptyNull(cFilling)){
				this.caCounterFilling[0]=Character.MIN_VALUE;
			}else{
				this.caCounterFilling[0]=cFilling;
			}
		}
		
		@Override
		public void setCounterFilling(Character charFilling) {
			if(charFilling==null){
				//Dadurch soll verhindert werden, dass NICHT wieder der DefaultCharachter herangezogen wird. Das Array hat dann nämlich eine Länge > 0.				
				this.setCounterFilling(Character.MIN_VALUE);
			}else{
				this.setCounterFilling(charFilling.charValue());				
			}
		}
		
		@Override
		public void setCounterFilling(String sCounterFilling)throws ExceptionZZZ{
			if(!StringZZZ.isEmpty(sCounterFilling)){
				if(sCounterFilling.length()>=2){
					ExceptionZZZ ez = new ExceptionZZZ("Ungültiger Wert für CounterFilling (= "+ sCounterFilling +"). Nur ein Buchstabe erlaubt.",iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				char cCounterFilling = sCounterFilling.toCharArray()[0];
				this.setCounterFilling(cCounterFilling);
			}else{
				this.setCounterFilling(CharZZZ.getEmpty());
			}
		}
		
		@Override		
		public String getCounterStringNormed(String sAlphanumeric) throws ExceptionZZZ {
			 String sReturn = null;
			 main:{
				 //String sCharToStrip = CounterByCharacterAscii_AlphanumericZZZ.getCharForPosition(CounterByCharacterAscii_AlphanumericZZZ.iPOSITION_MIN, bLowercase);
				 String sCharToStrip = CharZZZ.toString(this.getCounterFilling()); 				
				 if(this.isLowercase()){
					 sCharToStrip = sCharToStrip.toLowerCase();
				 }else{
					 sCharToStrip = sCharToStrip.toUpperCase();
				 }
				if(!this.isLeftAligned()){
					sReturn = StringZZZ.stripLeft(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
				}else{
					sReturn = StringZZZ.stripRight(sAlphanumeric, sCharToStrip); //Merke: Führende "0" Werte können nicht wiederhergestelllt werden, aus dem Zahlenwert.
				}
			 }//end main:
			 return sReturn;
		 }
		
		
		
}
