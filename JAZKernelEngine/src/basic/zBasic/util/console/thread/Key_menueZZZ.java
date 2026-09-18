package basic.zBasic.util.console.thread;

public class Key_menueZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_menueZZZ() {
			super();
		}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_menueZZZ();
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
		return IKeyPressConstantZZZ.cKeyMenue;
	}

	@Override
	public char getKeyChar() {
		return Key_menueZZZ.getKey();
	}
	
	@Override
	public String getKeyText() {
		return "menue";
	}
}
