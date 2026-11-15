package basic.zBasic.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import base.reflection.ReflectionUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Sammlung von bewährten Methoden. 
 * Dabei wird auf andere refelctionUtil - Klassen zurückgegriffen
 *  
 * 
 * @author Fritz Lindhauer, 03.05.2023, 15:51:55
 * 
 */
public class ReflectUtilZZZ implements IConstantZZZ{
	
	private ReflectUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	 /**
     * sucht eine Klasse anhand ihres Namens
     *
     * @param rawClassName qualifizierter Klassenname mit oder ohne Suffix ".class".
     * @return die Klasse oder null, wenn ein Fehler aufgetreten ist.
     **/
    public static <T> Class<T> findClass(String rawClassName)throws ExceptionZZZ {
    	return ReflectionUtil.findClass(rawClassName);
    }
    
    /**
     * Sucht die erste zum Methodennamen passende Methode.
     * Bei Mehrdeutigkeiten wird die erste gefundene Methode zurück geliefert
     * @param clazz
     * @param methodName
     *
     * @return Method oder null
     */
    public static Method findMethodForMethodName(Class<?> clazz, String methodName) throws ExceptionZZZ {
    	return ReflectionUtil.findMethodForMethodName(clazz, methodName);
    }
    
   
    /** Einfacher Fall: Statische Methode ohne weitere Argumente aufrufen.
     * 
     * Abgewandelt von:
     * Klasse: ReflectionUtil
     * public static void invokeStaticMethod(String qualifiedClassname, String methodName, Class<?>[] parameterTypes, Object[] parameterValues) {
     * 
     * @param qualifiedClassname
     * @param methodName
     * @author Fritz Lindhauer, 03.05.2023, 16:11:40
     */
    
    public static Object invokeStaticMethod(Method objMethod) throws ExceptionZZZ {
    	Object objReturn = null;
    	main:{
	        try {            
	            if (objMethod != null) {
	            	//objMethod.invoke(null, parameterValues);
	            	objReturn = objMethod.invoke(null);
	            }
	          //FGL: Erst ab Java 1.7
	          //Multi-catch parameters are not allowed for source level below 1.7
	//        } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
	//            logger.debug(e);
	//        }            
	        } catch (SecurityException e) {
	        	e.printStackTrace();
	        	ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
		    } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	            ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
		    } catch (IllegalAccessException e) {
	            e.printStackTrace();
	            ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
		    } catch (InvocationTargetException e) {
	            e.printStackTrace();
	            ExceptionZZZ ez = new ExceptionZZZ(e);
	        	throw ez;
	        }
    	}//end main:
    	return objReturn;
    }
    
    
    public static Object invokeStaticMethod(String sClassnameWithPackage, String sMethodname) throws ExceptionZZZ {
    	Object objReturn = null;
    	main:{
    		if(StringZZZ.isEmpty(sClassnameWithPackage)){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"ClassnameWithPackage", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;					
			}
    		if(StringZZZ.isEmpty(sMethodname)){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+"Methodname", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;					
			}
    		
    		//1. Die Klasse holen
    		Class objClass = ReflectUtilZZZ.findClass(sClassnameWithPackage);
    		if(objClass==null) {
    			String sLog = "Klasse '" + sClassnameWithPackage + "' nicht gefunden";
    			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+sLog, iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;				    			
    		}
    		
    		//2. Die Methode in der Klasse finden
    		Method objMethod = ReflectUtilZZZ.findMethodForMethodName(objClass, sMethodname);
    		if(objClass==null) {
    			String sLog = "Klasse '" + sClassnameWithPackage + "' hat keine statische Methode '" + sMethodname + "'";
    			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE+sLog, iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;				    			
    		}
    		
    		//3. Aufruf der gefundenen Methode
    		objReturn = ReflectUtilZZZ.invokeStaticMethod(objMethod);
    		
    	}//end main:
    	return objReturn;
    }
}


