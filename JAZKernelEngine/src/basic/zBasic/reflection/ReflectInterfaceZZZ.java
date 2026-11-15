package basic.zBasic.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;

import debug.zBasic.MyPrivilegedAction;
import basic.javareflection.mopex.Mopex;
import basic.javareflection.mopex.UQueue;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Ergänzt ReflectClassZZZ um das Durchsuchen der Interfaces.
 * Da statische Methoden nicht vererbt werden können, reiche ich den Methodenaufruf an ReflectClassZZZ weiter.
 * 
 * Manche sind fuer Interfaces nicht sinnvoll, z.B. isAbstract()
 * Diese Methoden wurden dann weggelassen.
 * @author Fritz Lindhauer, 14.03.2024, 17:53:24
 * 
 */
public class ReflectInterfaceZZZ extends ReflectClassZZZ implements IConstantZZZ{
	public static final String sINDICATOR_CLASSNAME_INNER= ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER;//Innere Klassen haben ein $ in ihrem; objClassInstance.getClass().getName()

	private ReflectInterfaceZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
    public static Field[] selectFields( Class cls, int mustHave, int mustNotHave ) {
    	return ReflectClassZZZ.selectFields(cls, mustHave, mustNotHave);
    }
	    	
    public static boolean isChildclassFrom(Class clsCurrent, Class clsSuperclass) throws ExceptionZZZ{
    	return ReflectClassZZZ.isChildclassFrom(clsCurrent, clsSuperclass);
    }
	    
    public static boolean hasSuperclass(Class clsCurrent) throws ExceptionZZZ{
    	return ReflectClassZZZ.hasSuperclass(clsCurrent);
    }
	    
    public static String getSuperclassName(Class clsCurrent) throws ExceptionZZZ{
    	return ReflectClassZZZ.getSuperclassName(clsCurrent);
    }
	    
    public static boolean isImplementing(Class clsCurrent, Class clsInterface) throws ExceptionZZZ{
    	return ReflectClassZZZ.isImplementing(clsCurrent, clsInterface);
    }
	    
    public static boolean isInner(Class objClass){
    	return ReflectClassZZZ.isInner(objClass);
    }
	    
    /*
     * Feststellen, ob das Feld einer Klasse eine public Konstante ist.
     */
    public static boolean isPublicStaticFinal(Field field){
		return ReflectClassZZZ.isPublicStaticFinal(field);
	}
	    
	    
    public static Class<?> getEnclosingClass(Class<?> objClass) throws ClassNotFoundException{
    	return ReflectClassZZZ.getEnclosingClass(objClass);
    }
	    
    /* ArrayList mit den Klassen-Objekten der Elternklassen zurueckgeben. 
     * ! intern Rekursiver Aufruf !
     */
	public static ArrayList<Class<?>> getSuperClasses(Class objClass){
		ArrayList<Class<?>> superTypes = new ArrayList<Class<?>>();
		
		Class objSuperClass = objClass.getSuperclass();
		if(objSuperClass!=null){
		//System.out.println("SuperClassname: " + objSuperClass.getName());
		superTypes.add(objSuperClass);
		
		ReflectInterfaceZZZ.scanSuperClasses(objSuperClass, superTypes);
		}
		return superTypes;
	}
		
		public static void scanInterfacesSuper(Class<?>objClass, ArrayList<Class<?>> listaInterfaceSuper) {
			
			Class<?>[]objaInterfaceSuper = objClass.getInterfaces();
			if(objaInterfaceSuper!=null){
				for(Class<?>objInterfaceSuper:objaInterfaceSuper) {
					//System.out.println("SuperClassname: " + objClassSuper.getName());
					if(!listaInterfaceSuper.contains(objInterfaceSuper)) {
						listaInterfaceSuper.add(objInterfaceSuper);
					}
					
					//Rekursion:
					//Jetzt hat dieses Interface ggfs. auch weitere Interface, aber die werden als Klassen geholt.
					
					//beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
					//         Auch wenn da steht interface extends I...
					//         ist das keine Elternklasse, sondern wiederum Interface des Interace halt.
					ReflectInterfaceZZZ.scanInterfacesSuper(objInterfaceSuper, listaInterfaceSuper);					
				}
			}
		}
		
		/* ArrayList mit den Klassen-Objekten der Elternklassen zurueckgeben. 
	     * ! intern Rekursiver Aufruf !
	     */
		public static void scanSuperClasses(Class<?>objClass, ArrayList<Class<?>> listaClassSuper){
			ReflectClassZZZ.scanSuperClasses(objClass, listaClassSuper);
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 * 
		 */
		public static ArrayList<Class<?>> getEmbeddedClasses(Class objClass, String sFilterClassname) throws ExceptionZZZ{
			return ReflectClassZZZ.getEmbeddedClasses(objClass, sFilterClassname);
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 * 
		 */
		public static ArrayList<Class<?>> getEmbeddedClasses(Class objClass) throws ExceptionZZZ{
			return ReflectClassZZZ.getEmbeddedClasses(objClass);
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 */
		public static void scanEmbeddedClasses(Class objClass, ArrayList<Class<?>> embeddedTypes) throws ExceptionZZZ{
			if(objClass==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectInterfaceZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
    			throw ez;
			}
			
			 //Merke: Hier Abweichung zu ReflectClassZZZZ 
			Class[] objaClass = objClass.getInterfaces();
			
			for(Class objClassTemp : objaClass){
			
				//System.out.println("Classname in array: " + objClass.getName());
				embeddedTypes.add(objClassTemp);
			}				
		}
		
		/** Merke: Da das Interface wohl nicht direct, sondern über eine abstracte Klasse eingebunden wird, ist das Array leer.
		 * @param objClass
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 31.01.2021, 12:34:50
		 */
		public static ArrayList<Class<?>> getInterfaces(Class objClass) throws ExceptionZZZ {
			return ReflectClassZZZ.getInterfaces(objClass);
		}
}
