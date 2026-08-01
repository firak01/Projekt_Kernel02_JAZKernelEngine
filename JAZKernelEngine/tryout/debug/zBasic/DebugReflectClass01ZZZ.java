package debug.zBasic;

import static java.lang.System.out;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alternative.test.ReflectionDummyZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.data.DataFieldZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;

public class DebugReflectClass01ZZZ {

	/** Hier werden spezielle Methoden der ReflectClassZZZ getestet.
	 *   Insbesondere f�llt auf, das es unterschiede gibt beim Aufruf, je nachdem wie man den Aufruf hinsichtlich der Rechte versucht.
	 *   Die Anspr�che an die Eintr�ge in der  java.policy Datei scheinen unterschiedlich zu sein.
	 *   Damit der SecurityManager und die vewendeten Access-Rechte angewandt werden, folgendes als VM Aufrufparamter verwenden.
	 *   
	 *   -Djava.security.manager -Djava.security.policy=jazkernel_empty.policy -Djava.security.debug=access
	 *   
	 * @param args
	 * 
	 * Fritz Lindhauer; 13.06.2014 08:27:01
	 */
	public static void main(String[] args) {

		System.out.println("### DEBUG: Folgende Debugs finden unter Security-Gesichtspunkten statt #########");
		boolean bHasSecurityManager = false;
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager==null){
			System.out.println("Keine SecurityManager gefunden, erwarte keinerlei Probleme.");
		}else{
			System.out.println("Security Manager: " + securityManager.toString() + " ==> L�se die Probleme.");
			bHasSecurityManager = true;
		}
				
		System.out.println("### DEBUG: EmbededClasses - Aus Classes[] Array ####");
		DebugReflectClass01ZZZ objDebug = new DebugReflectClass01ZZZ();
		objDebug.getEmbeddedClasses("FLAGZ");
		
	
		System.out.println("### Reflection DEBUG ###");
		DataFieldZZZ objField = new DataFieldZZZ("Form");
		objField.Datatype=DataFieldZZZ.sSTRING;
		objField.Fieldname="Form";
		objField.Targetvaluehandling=DataFieldZZZ.sTARGET_VALUE_REPLACE;		
		Class objClassCurrent = objField.getClass();
		Field[] fieldaz = null;
		
		System.out.println("### DEBUG: SelectFields - �BER PRIVILEGEDACTION KLASSE (Mit Helper Klasse), per Reflection aus einer Klasse a) gleiche Dateiquelle ####");
		if(bHasSecurityManager){
			ReflectClassZZZ rf = new ReflectClassZZZ();
			fieldaz = rf.selectFieldsPrivileged(objClassCurrent, 0, Modifier.FINAL);
		}else{	
			fieldaz = ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.
		}
		System.out.println("Gefundene Felder: ");
		for(Field field : fieldaz){
			System.out.println(field.getName());
		}
		
		System.out.println("### DEBUG: SelectFields - UEBER PRIVILEGEDACTION KLASSE, per Reflection aus einer Klasse a) gleiche Dateiquelle ####");
		if(bHasSecurityManager){
			MyPrivilegedAction pr = new MyPrivilegedAction(objClassCurrent);
			AccessController.doPrivileged(pr);
			ArrayList<Field>listaField = pr.getFieldList();
			fieldaz = new Field[listaField.size()];
			fieldaz = listaField.toArray(fieldaz);
		}else{	
			fieldaz = ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.
		}
		System.out.println("Gefundene Felder: ");
		for(Field field : fieldaz){
			System.out.println(field.getName());
		}
		
		
		System.out.println("### DEBUG: SelectFields - Per Reflection aus einer Klasse a) gleiche Dateiquelle ####");
		
		if(bHasSecurityManager){
			ReflectClassZZZ objReflect = new ReflectClassZZZ();
			fieldaz = objReflect.selectFieldsPrivileged(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.		
		}else{	
			fieldaz = ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.
		}
		System.out.println("Gefundene Felder: ");
		for(Field field : fieldaz){
			System.out.println(field.getName());
		}
		//Das funktioniert gut bei einer normalen umgebung, aber was, wenn die Klasse in einem .jar - File vorhanden ist....
		
		System.out.println("### DEBUG: SelectFields - Per Reflection aus einer Klasse b) externe Dateiquelle ####");
		// .jar File erstellt mit einer Dummy - Klasse... siehe JAZDummy Projekt.
		ReflectionDummyZZZ objDummy = new ReflectionDummyZZZ("TestAlias");
		Class objClassExternal = objDummy.getClass(); 
		Field[] fieldaz2 = ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit Ausnahme der Finalen Felder.
		
		System.out.println("Gefundene Felder: ");
		for(Field field : fieldaz2){
			System.out.println(field.getName());
		}
		
		
	}

	private void getEmbeddedClasses(String sFilter) {
		ArrayList<Class<?>> embeddedTypes = null;
		
		TxtReaderZZZ objReader;
		try {
			objReader = new TxtReaderZZZ();
		
			System.out.println("+++ Start scanning ++++");
			embeddedTypes = ReflectClassZZZ.getEmbeddedClasses(objReader.getClass(), sFilter);	
		
			for (Class embeddedClass : embeddedTypes) {
				System.out.println("Embedded Classname stored: " + embeddedClass.getName());		
			}
			
			for (Class embeddedClass : embeddedTypes) {
				Object[] objaEnumConstant = embeddedClass.getEnumConstants();
							
				if(objaEnumConstant!=null){
					
					List<Object> list = Arrays.asList(objaEnumConstant);
					Object obj = list.get(0);
					out.format("%s# Enum aus Klasse %s%n", ReflectCodeZZZ.getMethodCurrentName(), obj.getClass().getName());
					Field[] fields = obj.getClass().getDeclaredFields();
					for(Field field : fields){
						if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
							out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());							
						}
					}//end for				
				}//end if objaEnumConstant!=null		
			}
		} catch (ExceptionZZZ e) {
			e.printStackTrace();		
		}		
		
	}

}
