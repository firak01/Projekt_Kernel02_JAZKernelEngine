package basic.zBasic.util.console.thread;

public class Key_menueZZZ extends AbstractKeyZZZ{
	private static IKeyZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_menueZZZ() {
			super();
		}
	
	public static IKeyZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_menueZZZ();
		}
		return objKey;
	}
	
	@Override
	public IKeyZZZ getKeyObject() {
		return this.objKey;
	}

	@Override
	public void setKeyObject(IKeyZZZ objKey) {
		this.objKey = objKey;
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyMenue;
	}

	@Override
	public char getKeyChar() {
		return Key_menueZZZ.getKey();
	}
}
