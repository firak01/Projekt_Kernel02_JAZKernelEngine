package basic.zBasic.util.datatype.enums;

import java.lang.reflect.Field;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class EnumHelperZZZ implements IConstantZZZ{
	
	//++++++++++++++++++++++++++++++	
	//++++++++++++++++++++++++++++++
	/** Beispiele für einen Namen "FLAGZ" oder "STATUSLOCAL"
	 * @param classToCheck
	 * @param sEnumName
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 31.07.2023, 19:09:43
	 */
	public static <E extends Enum> E[] getEnumByName(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			ArrayList<E> listae = new ArrayList<E>();
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Object[] obja = objClass.getEnumConstants();
					for(Object obj : obja) {
						Enum e = (Enum) obj;
						listae.add((E) e);
					}
				 }
			}
			enumaReturn = ArrayListUtilZZZ.toEnumArray(listae);
		}
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> E getEnumByName(Class<?> classToCheck, String sEnumName, String sEnumPropety) throws ExceptionZZZ{
		E enumReturn = null;
		main:{
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			String sEnumFlagZName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			for(Class objClass : listaClass) {
				String sEnumClass = objClass.getName();				
				if(sEnumClass.endsWith(sEnumFlagZName)) {
					Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumName);	
					if(e!=null) {
						enumReturn = (E) e;										
						break main;
					}
				 }
			}
		}//end main:
		return enumReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++

	public static <E extends Enum> E getEnumAsField(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
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
	
	public static <E extends IEnumSetMappedStatusLocalZZZ> E getEnumStatsLocalForGroupAsField(Class<E> enumClass, String sFieldname) throws ExceptionZZZ {
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
