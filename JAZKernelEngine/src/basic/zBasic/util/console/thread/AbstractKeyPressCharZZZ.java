package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractKeyPressCharZZZ implements IKeyPressCharZZZ, IKeyPressCharUserZZZ, IKeyPressConstantZZZ{
	
	
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
