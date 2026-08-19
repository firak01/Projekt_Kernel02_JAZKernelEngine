package basic.zBasic.util.console.thread;

public abstract class AbstractKeyZZZ implements IKeyZZZ, IKeyUserZZZ, IKeyPressConstantZZZ{
	
	
	@Override
	public String getKeyTag() {
		char cKey = this.getKeyObject().getKeyChar();
		String sReturn = KeyPressUtilZZZ.computeKeyTag(cKey);
		return sReturn;
	}
	
	@Override
	public abstract char getKeyChar();
	
	

}
