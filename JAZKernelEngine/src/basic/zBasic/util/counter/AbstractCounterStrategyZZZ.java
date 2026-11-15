package basic.zBasic.util.counter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectReflectableImplementerZZZ;
import basic.zBasic.AbstractObjectReflectableZZZ;
import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractCounterStrategyZZZ extends AbstractObjectReflectableZZZ implements IObjectReflectableImplementerZZZ, ICounterStrategyZZZ{
	//TODO: Default Werte gehören eigentlich als Konstante ins Interface.
	private int iStartDefault = -1;		
	private int iStart = -1;
	
	private boolean bLeftAligned = false;//d.h. rechts stehen dann die Werte mit dem niedrigsten DigitValue, wie man es vom Dezimalsystem her kennt.
	private boolean bIncreasableInOtherMethod = false;
	
	public AbstractCounterStrategyZZZ() throws ExceptionZZZ{
		super();			
	}
	
	public AbstractCounterStrategyZZZ(int iStartValue) throws ExceptionZZZ{
		super();			
		this.setCounterStart(iStartValue);
	}
	
	//++++ Aus Interface
	@Override
	public boolean isLeftAligned() throws ExceptionZZZ {
		return this.bLeftAligned;
	}
	@Override
	public void isLeftAligned(boolean bValue) throws ExceptionZZZ {
		this.bLeftAligned = bValue;
	}
	
	@Override
	public boolean isRightAligned() throws ExceptionZZZ {
		return !this.bLeftAligned;
	}
	@Override
	public void isRightAligned(boolean bValue) throws ExceptionZZZ {
		this.bLeftAligned = !bValue;
	}
	
	//Merke: Das gibt es in der Numerischen Variante (ohne LowerCase) und in der Alphabet Variante (mit LowerCase)
	//TODO GOON 201909014: 
//	@Override
//	public abstract String getCharForPosition(int iPositionInMinMaxRange) throws ExceptionZZZ;
	
	
	@Override
	public boolean isIncreasableInOtherMethod() throws ExceptionZZZ{
		return this.bIncreasableInOtherMethod;
	}
	@Override
	public void isIncreasableInOtherMethod(boolean bValue) throws ExceptionZZZ{
		this.bIncreasableInOtherMethod = bValue;
	}
	
	@Override
	public abstract boolean makeReflectableInitialization() throws ExceptionZZZ;
	
	
	//Aus Interface ICounterStrategy
	@Override
	public void setCounterStart(int iStart) throws ExceptionZZZ{
		this.iStart = iStart;
	}
				
	@Override
	public int getCounterStart() throws ExceptionZZZ{
		if(this.iStart<=this.getCounterStartDefault()){
			this.iStart = this.getCounterStartDefault();
		}
		return this.iStart;
	}
	
	@Override
	public void setCounterStartDefault(int iStart) throws ExceptionZZZ{
		this.iStartDefault = iStart;
	}
				
	@Override
	public int getCounterStartDefault() throws ExceptionZZZ{		
		return this.iStartDefault;
	}
	
	@Override
	public int getDigitValueMin() throws ExceptionZZZ{
		return this.getCharacterPositionMin()-1;
	}
	
	@Override
	public int getDigitValueMax() throws ExceptionZZZ{
		return this.getCharacterPositionMax()-1;
	}
	
	@Override
	public String getCharForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ{		
		int iPositionValue = this.getPositionValueForDigitValue(iDigitValueInMinMaxRange);
		return this.getCharForPosition(iPositionValue);
	}
	
		
	@Override
	public int getPositionValueForDigitValue(int iDigitValueInMinMaxRange) throws ExceptionZZZ{
		return iDigitValueInMinMaxRange + 1;
	}
	
	@Override
	public int getDigitValueForPositionValue(int iPositionValueInMinMaxRange) throws ExceptionZZZ{		
		return iPositionValueInMinMaxRange - 1;
	}

}
