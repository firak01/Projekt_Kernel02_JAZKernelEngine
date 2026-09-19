package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractKeyPressCharZZZ implements IKeyPressCharZZZ, IKeyPressCharUserZZZ, IKeyPressConstantZZZ{
	protected boolean bKeyDefault=false;
	
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
	public abstract char getKeyChar();
	
	@Override
	public abstract String getKeyText();
	
	@Override
	public String getKeyDescription() {
		return "";
	}
	

}
