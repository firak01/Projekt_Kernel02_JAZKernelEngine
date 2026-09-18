package basic.zBasic.util.console.thread;

public class Key_yesZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
	private Key_yesZZZ() {
		super();
	}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_yesZZZ();
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
		return IKeyPressConstantZZZ.cKeyYes;
	}

	@Override
	public char getKeyChar() {
		return Key_yesZZZ.getKey();
	}
	
	@Override
	public String getKeyText() {
		return "yes";
	}
}
