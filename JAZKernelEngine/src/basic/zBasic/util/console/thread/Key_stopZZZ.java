package basic.zBasic.util.console.thread;

public class Key_stopZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_stopZZZ() {
			super();
		}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_stopZZZ();
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
	
	@Override
	public String getKeyText() {
		return "stop";
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyStop;
	}

	@Override
	public char getKeyChar() {
		return Key_stopZZZ.getKey();
	}
}
