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
import basic.zKernel.flag.FlagZHelperZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class DebugReflectClass03ZZZ extends AbstractObjectWithFlagZZZ{

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
			System.out.println("Security Manager: " + securityManager.toString() + " ==> Loese die Probleme.");
			bHasSecurityManager = true;
		}
		
		//######################################################
		System.out.println("### DEBUG: EmbededClasses der Klasse selber ####");
		DummyClassImplementingEnumByInterface objDebug = new DummyClassImplementingEnumByInterface();
		ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(objDebug.getClass());
		System.out.println("Class embedded found: ");
		for(Class cls : listaClass) {
			System.out.println(cls.getName());
		}
	
		//######################################################
		System.out.println("");
		System.out.println("### DEBUG: EmbededClasses der Klasse selber: Die Namen des Enums ####");		
		ArrayList<String> listasEnum = getEnumNamedInClass(objDebug.getClass(),"FLAGZ");
		for(String sEnum : listasEnum) {
			System.out.println(sEnum);
		}
		
		
		//######################################################
		System.out.println("");
		System.out.println("### DEBUG: EmbededClasses der Klasse selber: Enum als Objekt ####");	
		String sEnumNameToCheck = "CLASSZZZ_DUMMYENUM01";
		boolean bErg = hasEnumFlagZValue(objDebug.getClass(),sEnumNameToCheck);
		System.out.println("Enum mit dem Namen '" + sEnumNameToCheck + "' direkt vorhanden: " + bErg);
		
		//++++
		System.out.println("");
		Enum e = getEnumFlagZ(objDebug.getClass(),sEnumNameToCheck);
		if(e!=null) {
			System.out.println("Enum '" + sEnumNameToCheck + "' found with ordinal: " + e.ordinal());
		}else {
			System.out.println("Enum '" + sEnumNameToCheck + "'  NOT FOUND");
		}
		
		//###################################
		String sFlagNameToCheck = "init";
		boolean bErg2 = FlagZHelperZZZ.proofFlagZExists(objDebug.getClass(), sFlagNameToCheck);
		System.out.println("Flag mit dem Namen '" + sFlagNameToCheck + "' vorhanden: " + bErg2);
		
		//###################################
		String[] saFlagsAll = FlagZHelperZZZ.getFlagsZ(objDebug.getClass());
		for(String sFlag : saFlagsAll) {
			System.out.println("Flag: " + sFlag);			
		}
				
		
		
//		for(String sEnum : listasEnum) {
//			System.out.println(sEnum);
//		}
		
		//### Suche nach dem Enum FLAGZ IN DEN INTERFACES
//		System.out.println("");
//		System.out.println("### DEBUG: Intefaces direct ####");
//		Class[] obja = objDebugEnumByInterface.getClass().getInterfaces();
//		//Merke: Da das Interface wohl nicht direct, sondern über eine abstracte Klasse eingebunden wird, ist das Array leer.
//		
//		System.out.println("");
//		System.out.println("### DEBUG: Intefaces Elternklasse ####");
//		ArrayList<Class<?>> listaClassInterface = ReflectClassZZZ.getInterfaces(objDebugEnumByInterface.getClass());
//		for(Class cls : listaClassInterface) {
//			System.out.println("+++" + cls.getName());
//			
//			ArrayList<Class<?>> listaClassEmbeddedByInterfaces = ReflectClassZZZ.getEmbeddedClasses(cls);
//			System.out.println("++++++ Class embedded found: ");
//			for(Class clsEnumByInterfaces : listaClassEmbeddedByInterfaces) {
//				System.out.println(clsEnumByInterfaces.getName());
//				DebugReflectClass02ZZZ.printEnumValues(clsEnumByInterfaces, "FLAGZ");
//			} //Merke: Wenn FLAGZ nicht in der Klasse selbst als Enum eingebunden ist, wird es so nicht gefunden.
			
//			System.out.println("++++++ Enum Constants found: ");
//			Object[] objEnums = cls.getEnumConstants();
//			for(Object objEnum : objEnums){
//				objEnum.toString();
//			}
//			
			//DebugReflectClass02ZZZ.getEnumValues(cls);
			
			
			
//		} //Merke: Wenn FLAGZ nicht in der Klasse selbst als Enum eingebunden ist, wird es so nicht gefunden.
		
		
		
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private static ArrayList<String> getEnumNamedInClass(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();
				if(sEnumClass.endsWith(ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName)) {
					 Field[] fa = objClass.getDeclaredFields();					 
					 for (Field f : fa) {
						 listasReturn.add(f.getName());						
					 }				 				 
				 }
			}
		return listasReturn;
	 }
	
	private static  boolean hasEnumFlagZValue(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName(); //Z.B.: debug.zBasic.DummyClassImplementingEnumByInterface$FLAGZ
				String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "FLAGZ";
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Enum e = getEnumAsField(objClass, sEnumName);	
					if(e!=null) {
						bReturn = true;
						break main;
					}
				 }
			}
		}
		return bReturn;
	}
	
	private static <E extends Enum> E getEnumFlagZ(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + "FLAGZ";
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Enum e = getEnumAsField(objClass, sEnumName);	
					if(e!=null) {
						enumReturn = (E) e;										
						break main;
					}
				 }
			}
		}//end main:
		return enumReturn;
	}
	
	//private static <E extends Enum> E[] getEnumValues(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
	private static <E extends Enum> E getEnumAsField(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			try {
				//Auflisten aller Felder
//				Field[] fa = enumClass.getDeclaredFields();
//				for(Field f : fa) {
//					 System.out.println(f);
//					 System.out.println(f.getName());
//				     System.out.println(Modifier.toString(f.getModifiers()));
//				}
				
				//Zugriff auf ein konkretes Enum
			    Field f = enumClass.getDeclaredField(sFieldname);
//		        System.out.println(f);
//		        System.out.println(Modifier.toString(f.getModifiers()));
		        f.setAccessible(true);
		        Object o = f.get(null);
		        enumReturn = (E) o;
		        //return (E[]) o;
			 }catch (NoSuchFieldException nsfe) {
				 //Keine Exception werfen, dann ist das Ergebnis einfach NULL
//				ExceptionZZZ ez = new ExceptionZZZ("NoSuchFieldException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), nsfe);
//		    	throw ez;
		    } catch (IllegalAccessException iae) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException", iERROR_RUNTIME, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName(), iae);
		    	throw ez;
		    }			
		}
		return enumReturn;
	}
}
