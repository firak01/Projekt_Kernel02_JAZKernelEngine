package debug.zBasic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.PrivilegedAction;
import java.util.ArrayList;

import basic.zBasic.reflection.ReflectClassZZZ;

/* Beispiel f�r eine Klasse, die in einem priveligierten Teil eine andere Klasse aufruft. Ziel soll es sein die Anspr�che der java.policy Datei zu erf�llten. 
 *  Wichtig: Damit das funktioniert muss es �berhaupt eine java.policy Datei geben. Sie kann vom Inhalt auch leer sein.*/ 
public class MyPrivilegedAction  implements PrivilegedAction {
	private ArrayList<Field>listaField=new ArrayList<Field>();
	private Class objClassCurrent = null;

	public MyPrivilegedAction(Class objClassCurrent) {		
		this.objClassCurrent = objClassCurrent;
	}

	public Object run() {
		//	 privileged code:
		Field[] fieldaz =ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.
		for(Field f : fieldaz){
			listaField.add(f);
		}
		return listaField;
	}
	
	public ArrayList<Field>getFieldList(){
		return this.listaField;
	}
}


