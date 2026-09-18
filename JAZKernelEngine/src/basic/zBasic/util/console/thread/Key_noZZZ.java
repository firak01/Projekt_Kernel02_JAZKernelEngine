package basic.zBasic.util.console.thread;

public class Key_noZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_noZZZ() {
			super();
		}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_noZZZ();
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
		return "no";
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyNo;
	}

	@Override
	public char getKeyChar() {
		return Key_noZZZ.getKey();
	}
}
