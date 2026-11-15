package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zKernel.IKernelZZZ;

public class CounterByCharacterAsciiFactoryZZZ <T extends ICounterStrategyZZZ> extends AbstractObjectWithFlagZZZ implements ICounterByCharacterAsciiFactoryZZZ {
	private static CounterByCharacterAsciiFactoryZZZ objCounterFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 */
	private CounterByCharacterAsciiFactoryZZZ(){
		super();
	}
	
	public static CounterByCharacterAsciiFactoryZZZ getInstance(){
		if(objCounterFactory==null){
			objCounterFactory = new CounterByCharacterAsciiFactoryZZZ();
		}
		return objCounterFactory;		
	}

	//### AUS Interfaces
	//COUNTER MIT DEFAULTSTRATEGY "MULTIPLE"
	@Override
	public ICounterStringZZZ createCounter(int iCounterType) throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();						
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHABET){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ();
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHANUMERIC){
			objReturn = new CounterByCharacterAscii_AlphanumericZZZ();
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT){
			objReturn = new CounterByCharacterAscii_AlphanumericSignificantZZZ();
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//wichtig: Die initialisierende Methode darf nicht eine beliebige sein, sondern muss eine Methode sein, die diese Methode aufruft. Am besten noch von einer anderen Klasse...
		//Merke: Bei entsprechender Einstellung im Strategie - Objekt darf der Counter nur in der initialisierenden Methode erhöht werden.
		T objCounterStrategy = (T) objReturn.getCounterStrategyObject();
		String sMethodUsed4Init = ReflectCodeKernelZZZ.getClassMethodExternalCallingString();
		objCounterStrategy.setClassMethodCallingString(sMethodUsed4Init);
		
		
		return objReturn;
	}
	@Override
	public ICounterStringZZZ createCounter(int iCounterType, String sStart) throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ(sStart);
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHABET){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ(sStart);	
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHANUMERIC){
			objReturn = new CounterByCharacterAscii_AlphanumericZZZ(sStart);
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT){
			objReturn = new CounterByCharacterAscii_AlphanumericSignificantZZZ(sStart);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	@Override
	public ICounterStringZZZ createCounter(int iCounterType, int iStart)	throws ExceptionZZZ {
		ICounterStringZZZ objReturn = null;
		if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ(iStart);
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHABET){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ(iStart);	
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_ALPHANUMERIC){
			objReturn = new CounterByCharacterAscii_AlphanumericZZZ(iStart);
		}else if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT){
			objReturn = new CounterByCharacterAscii_AlphanumericSignificantZZZ(iStart);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}
	
	//#### COUNTER UNTER ANGABE DER STRATEGY. DABEI GIBT ES VERSCHIEDENEN STRATEGIEN. Die haben auch ggfs. andere Methoden.
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//if(iCounterType==CounterByCharacterAsciiFactoryZZZ.iCOUNTER_TYPE_NUMERIC){
			objReturn = new CounterByCharacterAscii_NumericZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		//}else{
//			ExceptionZZZ ez = new ExceptionZZZ("CounterType wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
		//}
		return objReturn;
	}
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;	
	}
	@Override
	public ICounterNumericZZZ createCounter(ICounterStrategyNumericZZZ objCounterStrategy,String sStart) throws ExceptionZZZ {
		ICounterNumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;	
	}
	
	//+++++++++++++++
	@Override
	public ICounterAlphabetZZZ createCounter(ICounterStrategyAlphabetZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterAlphabetZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		if(objCounterStrategy instanceof CounterStrategyAlphabetSerialZZZ || objCounterStrategy instanceof CounterStrategyAlphabetMultipleZZZ){
			objReturn = new CounterByCharacterAscii_AlphabetZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		}else{		
			ExceptionZZZ ez = new ExceptionZZZ("CounterStategyObject-Type wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}	
	@Override
	public ICounterAlphabetZZZ createCounter(ICounterStrategyAlphabetZZZ objCounterStrategy, int iStart)throws ExceptionZZZ {
		ICounterAlphabetZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;
	}
	@Override
	public ICounterAlphabetZZZ createCounter(ICounterStrategyAlphabetZZZ objCounterStrategy, String sStart) throws ExceptionZZZ {
		ICounterAlphabetZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public ICounterAlphabetSignificantZZZ createCounter(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy) throws ExceptionZZZ{
			ICounterAlphabetSignificantZZZ objReturn = null;
			if(objCounterStrategy==null){
				ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 if(objCounterStrategy instanceof CounterStrategyAlphabetSignificantZZZ){
				//dito, ein AlphanumericCounter... 20190601: GGFs ein spezielles Interface dafür entwickeln??? 
				objReturn = new CounterByCharacterAscii_AlphabetSignificantZZZ();
				objReturn.setCounterStrategyObject(objCounterStrategy);
			 }else{		
					ExceptionZZZ ez = new ExceptionZZZ("CounterStategyObject-Type wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			 return objReturn;
		}
		@Override
		public ICounterAlphabetSignificantZZZ createCounter(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
			ICounterAlphabetSignificantZZZ objReturn = this.createCounter(objCounterStrategy);
			objReturn.setValueCurrent(iStart);
			return objReturn;
		}
		@Override
		public ICounterAlphabetSignificantZZZ createCounter(ICounterStrategyAlphabetSignificantZZZ objCounterStrategy, String sStart) throws ExceptionZZZ {
			ICounterAlphabetSignificantZZZ objReturn = this.createCounter(objCounterStrategy);
			objReturn.setValueCurrent(sStart);
			return objReturn;
		}
	
	//+++++++++++++++
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
//		if(objCounterStrategy instanceof CounterStrategyAlphabetSerialZZZ || objCounterStrategy instanceof CounterStrategyAlphabetMultipleZZZ){
//			objReturn = new CounterByCharacterAscii_AlphabetZZZ();
//			objReturn.setCounterStrategyObject(objCounterStrategy);			
//		}else 
		if(objCounterStrategy instanceof CounterStrategyAlphanumericMultipleZZZ || objCounterStrategy instanceof CounterStrategyAlphanumericSerialZZZ){				 
			objReturn = new CounterByCharacterAscii_AlphanumericZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);			
		}else{		
			ExceptionZZZ ez = new ExceptionZZZ("CounterStategyObject-Type wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objReturn;
	}		
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;
	}
	@Override
	public ICounterAlphanumericZZZ createCounter(ICounterStrategyAlphanumericZZZ objCounterStrategy, String sStart) throws ExceptionZZZ {
		ICounterAlphanumericZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public ICounterAlphanumericSignificantZZZ createCounter(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy) throws ExceptionZZZ{
		ICounterAlphanumericSignificantZZZ objReturn = null;
		if(objCounterStrategy==null){
			ExceptionZZZ ez = new ExceptionZZZ("CounterStrategyObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		 if(objCounterStrategy instanceof CounterStrategyAlphanumericSignificantZZZ){
			//dito, ein AlphanumericCounter... 20190601: GGFs ein spezielles Interface dafür entwickeln??? 
			objReturn = new CounterByCharacterAscii_AlphanumericSignificantZZZ();
			objReturn.setCounterStrategyObject(objCounterStrategy);
		 }else{		
				ExceptionZZZ ez = new ExceptionZZZ("CounterStategyObject-Type wird (noch nicht?) behandelt", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		 return objReturn;
	}
	@Override
	public ICounterAlphanumericSignificantZZZ createCounter(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy, int iStart) throws ExceptionZZZ {
		ICounterAlphanumericSignificantZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(iStart);
		return objReturn;
	}
	@Override
	public ICounterAlphanumericSignificantZZZ createCounter(ICounterStrategyAlphanumericSignificantZZZ objCounterStrategy, String sStart) throws ExceptionZZZ {
		ICounterAlphanumericSignificantZZZ objReturn = this.createCounter(objCounterStrategy);
		objReturn.setValueCurrent(sStart);
		return objReturn;
	}

}
