package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public interface IKeyPressCharZZZ {
	public char getKeyChar();
	public String getKeyTag() throws ExceptionZZZ;
	public String getKeyText() throws ExceptionZZZ;
	public String getKeyDescription() throws ExceptionZZZ;
	
	public boolean isKeyDefault() throws ExceptionZZZ;
	public void isKeyDefault(boolean bValue) throws ExceptionZZZ;
}
