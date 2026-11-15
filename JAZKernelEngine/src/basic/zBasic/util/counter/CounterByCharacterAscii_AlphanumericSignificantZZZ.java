package basic.zBasic.util.counter;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 *  
 * 
 * @author Fritz Lindhauer, 03.03.2019, 12:49:22
 * 
 */
public class CounterByCharacterAscii_AlphanumericSignificantZZZ<T extends ICounterStrategyAlphanumericSignificantZZZ> extends AbstractCounterByStrategyAlphanumericSignificantZZZ{
	//Als generics in die Abstracte Klasse verschoben private ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy;
	T objCounterStrategy;

	public CounterByCharacterAscii_AlphanumericSignificantZZZ() throws ExceptionZZZ{
		super();	
		
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(int iStartingValue) throws ExceptionZZZ {
		super(iStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(String sStartingValue) throws ExceptionZZZ{
		super(sStartingValue);		
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(int iStartingValue, ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy) throws ExceptionZZZ {
		super(iStartingValue);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	public CounterByCharacterAscii_AlphanumericSignificantZZZ(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy) throws ExceptionZZZ {
		super(CounterByCharacterAscii_AlphanumericSignificantZZZ.iPOSITION_MIN-1);
		this.setCounterStrategyObject(objCounterStrategy);
	}
	
	


	//### Aus Interface
	@Override
	public int getCounterLength() throws ExceptionZZZ {
		return this.getCounterStrategyObject().getCounterLength();
	}

	@Override
	public void setCounterLength(int iLength) throws ExceptionZZZ {
		this.getCounterStrategyObject().setCounterLength(iLength);
	}

	@Override
	public char getCounterFilling() throws ExceptionZZZ {		
		return  this.getCounterStrategyObject().getCounterFilling();
	}

	@Override
	public void setCounterFilling(char cFilling) throws ExceptionZZZ {
		this.getCounterStrategyObject().setCounterFilling(cFilling);
	}
	
	@Override
	public void setCounterFilling(Character charFilling) throws ExceptionZZZ {
		this.getCounterStrategyObject().setCounterFilling(charFilling);
	}
	
	@Override 
	public boolean hasCounterFilling() throws ExceptionZZZ{
		return this.getCounterStrategyObject().hasCounterFilling();
	}
	
	//### Aus Interface	
	@Override
	public String preValueSetting(String sValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
		if(StringZZZ.isEmpty(sValue)){
			ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein Wert für das PREPROCESSING übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
											
		//if(this.getCounterLength()>= 1){
			//Führendes Füllzeichen entfernen, aber nur wenn es innerhalb der maximalen Zählergröße liegt. Merke: Nur dann schlägt ggfs. eine Validierung fehl. Was gewünscht ist!!!
			//int iTimes=this.getCounterLength() - sValue.length();
			//(if(iTimes>=0){			
		
		//Merke: 20190724: Jetzt immer Füllzeichen wegtrimmen. Damit mann den Zählerwert so wie er ist als erneuten Inputwert verwenden kann.
				char cToBeStripped = this.getCounterFilling();
				
				String sStringToBeStripped = StringZZZ.char2String(cToBeStripped) ;
				
				
				if(this.getCounterStrategyObject().isLeftAligned()){
					sReturn = StringZZZ.stripRight(sValue, sStringToBeStripped);
				}else{
					sReturn = StringZZZ.stripLeft(sValue, sStringToBeStripped);
				}
		//	}else{
		//		sReturn = sValue;
		//	}
		//}else{
		//	sReturn = sValue;
		//}
				
				
			//20190724: Wenn der Zähler länger ist alls die Zählerlänge, dann wirf einen Fehler
			if(sReturn.length()>this.getCounterLength()){
				ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Auch nach dem Entfernen der Füllzeichen im PREPROCESSING ist der String länger als der Zähler erlaubt (" + sReturn.length() + " > " + this.getCounterLength() + ").", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
		
		}//end main;
		return sReturn;
	}
	@Override
	public String postValueSetting(String sValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
		if(sValue==null){
			ExceptionZZZ ez = new ExceptionZZZ("AlphanumericCounter: Kein Wert für das POSTPROCESSING übergeben.", iERROR_PARAMETER_VALUE, CounterByCharacterAscii_AlphanumericSignificantZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		if(this.getCounterLength()>=1){
			
			//Führendes Füllzeichen hinzunehmen, aber nur bis zum Zählermaximum bzgl. der Länge.
			int iTimes=this.getCounterLength() - sValue.length();
			if(iTimes >= 1){		
				if(this.hasCounterFilling()){
					char cFilling = this.getCounterFilling();
					String sString = StringZZZ.char2String(cFilling);
					String stemp = StringZZZ.repeat(sString, iTimes);
									
					if(this.getCounterStrategyObject().isLeftAligned()){
						sReturn = sValue + stemp; //Wenn der Zähler links ausgerichtet ist, kommen die Füllzeichen nach rechts!
					}else{
						sReturn = stemp + sValue; //Wenn der Zähler rechts ausgerichtet ist, kommen die Füllzeichen nach links!
					}
				}else{
					sReturn = sValue;
				}
			}else{
				sReturn = sValue;
			}
		}else{
			sReturn = sValue;
		}
		
		}//end main;
		return sReturn;
	}
	
	
	
	//###################
	// ++++ Aus Interface
//	@Override
//	public ICounterStrategyAlphanumericSignificantZZZ getCounterStrategyObject(){
//	if(this.objCounterStrategy==null){
//		ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
//		this.objCounterStrategy = objCounterStrategy;
//	}
//	return this.objCounterStrategy;
//	}
	//nach Umstellung auf Generics
	
	/* in abstrakte Klasse verschoben */
	/*
	@Override
	public T getCounterStrategyObject(){
		if(this.objCounterStrategy==null){
			ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
			this.objCounterStrategy = objCounterStrategy;
		}
		return (T) this.objCounterStrategy;
	}
	
	@Override
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy){
		this.objCounterStrategy = objCounterStrategy;	
	}
	
	@Override
	public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy) {
		this.objCounterStrategy = (ICounterStrategyAlphanumericSignificantZZZ) objCounterStrategy;
	}
	*/
	
	//nach Umstellung auf Generics
	public T getCounterStrategyObject() throws ExceptionZZZ{
		if(this.objCounterStrategy==null){
			ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy = new CounterStrategyAlphanumericSignificantZZZ();
			this.objCounterStrategy = (T) objCounterStrategy;
		}
		return (T) this.objCounterStrategy;
	}
	
	@Override
	public void setCounterStrategyObject(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy) {
		this.objCounterStrategy = (T) objCounterStrategy;
	}
	
	@Override
	public void setCounterStrategyObject(ICounterStrategyZZZ objCounterStrategy) {
		this.objCounterStrategy = (T) objCounterStrategy;
	}
}
