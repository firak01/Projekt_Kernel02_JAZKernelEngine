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
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.data.DataFieldZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class DebugReflectClass02ZZZ extends AbstractObjectWithFlagZZZ{

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
		try {		
		System.out.println("### DEBUG: Folgende Debugs finden unter Security-Gesichtspunkten statt #########");
		boolean bHasSecurityManager = false;
		SecurityManager securityManager = System.getSecurityManager();
		if(securityManager==null){
			System.out.println("Keine SecurityManager gefunden, erwarte keinerlei Probleme.");
		}else{
			System.out.println("Security Manager: " + securityManager.toString() + " ==> L�se die Probleme.");
			bHasSecurityManager = true;
		}
		
		//######################################################
		System.out.println("### DEBUG: EmbededClasses - Aus Classes[] Array ####");
		FileIniZZZ objDebug = new FileIniZZZ();
		ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(objDebug.getClass());
		System.out.println("Class embedded found: ");
		for(Class cls : listaClass) {
			System.out.println(cls.getName());
		}
	
		//######################################################
		System.out.println("");
		System.out.println("### DEBUG: EmbededClasses - Aus Classes[] Array ####");
		DummyClassImplementingEnumByInterface objDebugEnumByInterface = new DummyClassImplementingEnumByInterface();
		ArrayList<Class<?>> listaClassEnumByInterface = ReflectClassZZZ.getEmbeddedClasses(objDebugEnumByInterface.getClass());
		System.out.println("Class embedded found: ");
		for(Class cls : listaClassEnumByInterface) {
			System.out.println(cls.getName());
		} //Merke: Wenn FLAGZ nicht in der Klasse selbst als Enum eingebunden ist, wird es so nicht gefunden.
		
		
		//### Suche nach dem Enum FLAGZ IN DEN INTERFACES
		System.out.println("");
		System.out.println("### DEBUG: Intefaces direct ####");
		Class[] obja = objDebugEnumByInterface.getClass().getInterfaces();
		//Merke: Da das Interface wohl nicht direct, sondern über eine abstracte Klasse eingebunden wird, ist das Array leer.
		
		System.out.println("");
		System.out.println("### DEBUG: Intefaces Elternklasse ####");
		ArrayList<Class<?>> listaClassInterface = ReflectClassZZZ.getInterfaces(objDebugEnumByInterface.getClass());
		for(Class cls : listaClassInterface) {
			System.out.println("+++" + cls.getName());
			
			ArrayList<Class<?>> listaClassEmbeddedByInterfaces = ReflectClassZZZ.getEmbeddedClasses(cls);
			System.out.println("++++++ Class embedded found: ");
			for(Class clsEnumByInterfaces : listaClassEmbeddedByInterfaces) {
				System.out.println(clsEnumByInterfaces.getName());
				DebugReflectClass02ZZZ.printEnumValues(clsEnumByInterfaces, "FLAGZ");
			} //Merke: Wenn FLAGZ nicht in der Klasse selbst als Enum eingebunden ist, wird es so nicht gefunden.
			
//			System.out.println("++++++ Enum Constants found: ");
//			Object[] objEnums = cls.getEnumConstants();
//			for(Object objEnum : objEnums){
//				objEnum.toString();
//			}
//			
			//DebugReflectClass02ZZZ.getEnumValues(cls);
			
			
			
		} //Merke: Wenn FLAGZ nicht in der Klasse selbst als Enum eingebunden ist, wird es so nicht gefunden.
		
		
		
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getEmbeddedClasses(Class objClass, String sFilter) {
		ArrayList<Class<?>> embeddedTypes = null;
		
		try {					
			System.out.println("+++ Start scanning ++++");
			embeddedTypes = ReflectClassZZZ.getEmbeddedClasses(objClass, sFilter);	
		
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
	

	//private static <E extends Enum> E[] getEnumValues(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
	private static void printEnumValues(Class<?> enumClass, String sFieldnameEnum) throws ExceptionZZZ {
//		 try {
	        //Field f = enumClass.getDeclaredField("$"+sFieldname);
			 String sEnumClass = enumClass.getName();
			 if(sEnumClass.endsWith("$" + sFieldnameEnum)) {
				 Field[] fa = enumClass.getDeclaredFields();
				 System.out.println("+++++++++ Felder: ");
				 for (Field f : fa) {
					 String s = f.toString();
					 System.out.println("f.toString()="+s);
					 System.out.println("f.getName()=" + f.getName());
					 //TODOGOON; //20210131 Mache Eine FlagZUtilZZZ-Klasse, in der diese zur Vefügung stehenden Flags geholt werden.
					 //MErke: Die defnierten KErnelFlags haben als enum keinen Wert. Also ENUM$VALUES nicht in die Liste der FLAGZ aufnehmen.
				 }				 				 
			 }
			 	
//			 Field f = enumClass.getDeclaredField(sFieldname);
//	        System.out.println(f);
//	        System.out.println(Modifier.toString(f.getModifiers()));
//	        f.setAccessible(true);
//	        Object o = f.get(null);
//	        return (E[]) o;
//		 }catch (NoSuchFieldException nsfe) {
//			ExceptionZZZ ez = new ExceptionZZZ("NoSuchFieldException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), nsfe);
//	    	throw ez;
//	    } catch (IllegalAccessException iae) {
//	    	ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), iae);
//	    	throw ez;
//	    }
	 }
}
