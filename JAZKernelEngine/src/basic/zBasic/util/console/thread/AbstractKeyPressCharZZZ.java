package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractKeyPressCharZZZ implements IKeyPressCharZZZ, IKeyPressCharUserZZZ, IKeyPressConstantZZZ{
	protected boolean bKeyDefault=false;
	protected String sKeyDescription=null;
	
	//### GETTER / SETTER
	@Override
	public void isKeyDefault(boolean bValue) throws ExceptionZZZ{
		this.bKeyDefault = bValue;
	}
	@Override
	public boolean isKeyDefault() throws ExceptionZZZ {
		return this.bKeyDefault;
	}
	
	
	//#########################
	@Override
	public String getKeyTag() throws ExceptionZZZ {
		char cKey = this.getKeyCharObject().getKeyChar();
		String sReturn = KeyPressUtilZZZ.computeKeyTag(cKey);
		return sReturn;
	}
	
	@Override
	public abstract char getKeyChar() throws ExceptionZZZ;
		
	@Override
	public String getKeyText() throws ExceptionZZZ {
		return CharZZZ.toString(this.getKeyChar());
	}
	
	
	@Override
	public String getKeyDescription() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sKeyDescription)) {
			return this.getKeyText();
		}else {
			return this.sKeyDescription;
		}
	}
	
	@Override
	public void setKeyDescription(String sKeyDescription) throws ExceptionZZZ {
		this.sKeyDescription = sKeyDescription;
	}
	

}
