package basic.zBasic.util.console.thread;

public class Key_cancelZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_cancelZZZ() {
			super();
		}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_cancelZZZ();
		}
		return objKey;
	}
	
	@Override
	public IKeyPressCharZZZ getKeyCharObject() {
		return this.objKey;
	}

	@Override
	public void setKeyObject(IKeyPressCharZZZ objKey) {
		this.objKey = objKey;
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyCancel;
	}

	@Override
	public char getKeyChar() {
		return Key_cancelZZZ.getKey();
	}
	
	
	@Override
	public String getKeyText() {
		return "cancel";
	}
}
