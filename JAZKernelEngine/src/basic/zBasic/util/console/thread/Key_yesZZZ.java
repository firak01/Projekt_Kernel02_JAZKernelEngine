package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public class Key_yesZZZ extends AbstractKeyPressCharZZZ{
	TODOGOON20260920;//Mache eine abstract class AbstractKeyPressCharSingletonZZZ 
	                 //Da kommt dann die Singleton Funktionalität rein
	protected static IKeyPressCharZZZ objKey=null; //muss static sein, wg. getInstance()!!!
	
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
	
	//Wenn man den Tastendruck in verschiedenen Menüs verwendet, in denen es z.B. unterschiedliche "Defaultkeys" gibt, sollte man eine neue Instanz holen
	public static IKeyPressCharZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objKey = null;
		objKey = getInstance();
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
