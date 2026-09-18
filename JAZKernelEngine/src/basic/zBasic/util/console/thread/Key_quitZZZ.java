package basic.zBasic.util.console.thread;

public class Key_quitZZZ extends AbstractKeyPressCharZZZ{
	private static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
	//Verwendung als Singleton
		private Key_quitZZZ() {
			super();
		}
	
	public static IKeyPressCharZZZ getInstance() {
		if(objKey==null) {
			objKey = new Key_quitZZZ();
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
		return "quit";
	}
	
	

	public static char getKey() {
		return IKeyPressConstantZZZ.cKeyQuit;
	}

	@Override
	public char getKeyChar() {
		return Key_quitZZZ.getKey();
	}
}
