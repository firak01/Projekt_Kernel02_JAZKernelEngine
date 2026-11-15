package basic.zBasic.reflection;

import java.lang.reflect.Field;
import java.security.PrivilegedAction;
import java.util.ArrayList;

/* Klasse, in der in einem privelidgierten Abschnitt per Refelction API die Methode "getDeclaredFields()" aufgerufen wird.
 *  Aufrufende Klasse ist ReflectClassZZZ. 
 *  Wichtig: Damit das funktioniert muss es eine java.policy Datei geben. Sie kann vom Inhalt auch leer sein.*/ 
public class ReflectHelperDeclaredFieldsZZZ  implements PrivilegedAction {
	private ArrayList<Field>listaField=new ArrayList<Field>();
	private Class objClassCurrent = null;

	public ReflectHelperDeclaredFieldsZZZ(Class objClassCurrent) {		
		this.objClassCurrent = objClassCurrent;
	}

	public Object run() {
		//	 privileged code:		
		Field[] fieldaz =objClassCurrent.getDeclaredFields();
		for(Field f : fieldaz){
			listaField.add(f);
		}
		return listaField;
	}
	
	public ArrayList<Field>getFieldList(){
		return this.listaField;
	}
}


