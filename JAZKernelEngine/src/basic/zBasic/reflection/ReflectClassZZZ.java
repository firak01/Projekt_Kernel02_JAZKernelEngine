package basic.zBasic.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import debug.zBasic.MyPrivilegedAction;
import basic.javareflection.mopex.Mopex;
import basic.javareflection.mopex.UQueue;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ReflectClassZZZ implements IConstantZZZ{
	public static final String sINDICATOR_CLASSNAME_INNER= "$"; //Innere Klassen haben ein $ in ihrem; objClassInstance.getClass().getName()

	public ReflectClassZZZ(){
		//private zum 'Verstecken" des Konstruktors, aber protected, damit andere Klassen daraus erben können.
	}//only static Methods
	
	
	/**
	     * Produces an array of all the fields in a class, each of which has all of the 
	     * modifiers indicated by the int parameter mustHave and none of the modifiers indicated
	     * by the int parameter mustNotHave. Note that "all the fields in a class" means the
	     * class and all of its superclasses. For example, selectFields(x,0,Modifier.FINAL)
	     * returns all the fields that are not final.
	     * 
	     * ACHTUNG: Seit Java 1.6: Problematisch, wenn der Code auf einem Server ausgef�hrt wird, in dem Java Security �ber die java.policy Datei geregelt wird.
	     *                  Verwende dann die entprechende ..Priviliged - Methode.
	     *
	     * @return Field[]
	     * @param cls java.lang.Class
	     * @param mustHave int
	     * @param mustNotHave int
	     * 
	     * Aus dem Buch: "Java Reflection in Action"
	     */
		//start extract selectFields
	    public static Field[] selectFields( Class cls, 
						int mustHave, 
						int mustNotHave ) {
		UQueue fq = new UQueue(Field.class);
		Class[] ca = Mopex.selectAncestors(cls,0,0);
		for ( int j = 0; j < ca.length; j++ ){
		    Field[] fa = ca[j].getDeclaredFields();
		    for ( int i = 0; i < fa.length; i++ ) {
			int mods = fa[i].getModifiers();
			if ( ((mods & mustHave) == mustHave) 
			     && ((mods & mustNotHave) == 0) )
			    fq.add( fa[i] );
		    }
		}
		return (Field[])fq.toArray();
	    }
	//start extract selectFields
	    
	/* Verwende eine "Privilegierte Helper Klasse, um per Reflection API Werte auszulesen.
	 *  Seit Java 1.6. tritt beim Einsatz auf Servern die Problematik der java.policy Datei auf.
	 *   *  Wichtig: Damit das funktioniert muss es �berhaupt eine java.policy Datei geben. Sie kann vom Inhalt auch leer sein.*/   
   public Field[] selectFieldsPrivileged(final Class cls, final int mustHave, final int mustNotHave)  {
		UQueue fq = new UQueue(Field.class);
		Class[] ca = Mopex.selectAncestors(cls,0,0);
		for ( int j = 0; j < ca.length; j++ ){
		    //Field[] fa = ca[j].getDeclaredFields();			
			ReflectHelperDeclaredFieldsZZZ pr = new ReflectHelperDeclaredFieldsZZZ(ca[j]);
			AccessController.doPrivileged(pr);
			ArrayList<Field>listaField = pr.getFieldList();
			Field[] fa = new Field[listaField.size()];
			fa = listaField.toArray(fa);
		
		    for ( int i = 0; i < fa.length; i++ ) {
			int mods = fa[i].getModifiers();
			if ( ((mods & mustHave) == mustHave) 
			     && ((mods & mustNotHave) == 0) )
			    fq.add( fa[i] );
		    }
		}
		return (Field[])fq.toArray();
    	}

	    /** Ermittelt intern alle Elternklassen von clsCurrent und pr�ft dann, ob die clsSuperclass darin enthalten ist. 
	     *   Verwendet intern Mopex.getSuperclasses(class) aus dem Buch "Java Reflections in Action".
	     *   
	    * @param clsCurrent
	    * @param clsSuperclass
	    * 
	    * lindhaueradmin; 13.03.2007 08:37:02
	     * @throws ExceptionZZZ 
	     */
	    public static boolean isChildclassFrom(Class clsCurrent, Class clsSuperclass) throws ExceptionZZZ{
	    	boolean bReturn = false;
	    	main:{
	    		if(clsCurrent==null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    		if(clsSuperclass == null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Superclass", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    		
	    		//1. Alle Superclassen ermitteln
	    		Class[] clsaSuper = Mopex.getSuperclasses(clsCurrent);
	    		if(clsaSuper==null) break main;
	    		if(clsaSuper.length <= 0) break main;
	    		
	    		//2. nun im Array durchgehen
	    		String sSuperclass = clsSuperclass.getName();
	    		for(int icount = 0 ; icount <= clsaSuper.length - 1; icount ++){
	    			String stemp = clsaSuper[icount].getName();
	    			if(stemp.equals(sSuperclass)){
	    				bReturn = true;
	    				break main;
	    			}
	    		}
	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public static boolean hasSuperclass(Class clsCurrent) throws ExceptionZZZ{
	    	boolean bReturn = false;
	    	main:{
	    		if(clsCurrent==null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    			    		
	    		//Alle Superclassen ermitteln
	    		Class[] clsaSuper = Mopex.getSuperclasses(clsCurrent);
	    		if(clsaSuper==null) break main;
	    		if(clsaSuper.length <= 0) break main;
	    		bReturn = true;
	    		
	    	}//end main:
	    	return bReturn;
	    }
	    
	    public static String getSuperclassName(Class clsCurrent) throws ExceptionZZZ{
	    	String sReturn = null;
	    	main:{
	    		if(clsCurrent==null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    		
	    		Class clsSuper = clsCurrent.getSuperclass();
	    		if(clsSuper!=null) sReturn = clsSuper.getName();
	    	}//end main:
	    	return sReturn;
	    }
	    
	    public static boolean isImplementing(Class clsCurrent, Class clsInterface) throws ExceptionZZZ{
	    	boolean bReturn = false;
	    	main:{
	    		if(clsCurrent==null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    		if(clsInterface == null){
	    			ExceptionZZZ ez = new ExceptionZZZ("Class Interface", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
	    		}
	    		
	    		//1. Alle Interfaceklassen ermitteln
	    		Class[] clsaInterface = Mopex.getAllInterfaces(clsCurrent);
	    		
	    		if(clsaInterface==null) break main;
	    		if(clsaInterface.length <= 0) break main;
	    		
	    		//2. nun im Array durchgehen
	    		String sInterface = clsInterface.getName();
	    		for(int icount = 0 ; icount <= clsaInterface.length - 1; icount ++){
	    			String stemp = clsaInterface[icount].getName();
	    			if(stemp.equals(sInterface)){
	    				bReturn = true;
	    				break main;
	    			}
	    		}
	    		
	    	}//end main
	    	return bReturn;
	    }
	    
	    public static boolean isInner(Class objClass){
	    	boolean bReturn = false;
	    	main:{
	    		if(objClass==null) break main;
	    		
	    		String sName = objClass.getName();
	    		CharSequence cSeq = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER;
	    		if(sName.contains(cSeq)){
	    			bReturn = true;
	    		}else{
	    			bReturn = false;
	    		}
	    	}//end main
	    	return bReturn;
	    }
	    
	    /*
	     * Feststellen, ob das Feld einer Klasse eine public Konstante ist.
	     */
	    public static boolean isPublicStaticFinal(Field field){
			int modifiers = field.getModifiers();
			return (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers));
		}
	    
	    /* Feststellen, ob eine Klasse abstract ist
	     * 
	     */
	    public static boolean isAbstract(Class objClass){
	    	return Modifier.isAbstract( objClass.getModifiers());
	    }
	    
	    public static Class<?> getEnclosingClass(Class<?> objClass) throws ClassNotFoundException{
	    	Class<?> objReturn = null;
	    	main:{
	    		if(objClass==null)break main;
	    		if(!ReflectClassZZZ.isInner(objClass))break main;
	    		
	    		String sClassNameInner = objClass.getName();
	    		int iIndicatorPosition = sClassNameInner.indexOf(ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER);
				String sEnclosingClassName = sClassNameInner.substring(0, iIndicatorPosition);
				objReturn = Class.forName(sEnclosingClassName);
	    	}//end main
	    	return objReturn;
	    }
	    
	    /* ArrayList mit den Klassen-Objekten der Elternklassen zur�ckgeben. 
	     * ! intern Rekursiver Aufruf !
	     */
		public static ArrayList<Class<?>> getSuperClasses(Class objClass){
			ArrayList<Class<?>> superTypes = new ArrayList<Class<?>>();
			
			Class objSuperClass = objClass.getSuperclass();
			if(objSuperClass!=null){
			//System.out.println("SuperClassname: " + objSuperClass.getName());
			superTypes.add(objSuperClass);
			
			ReflectClassZZZ.scanSuperClasses(objSuperClass, superTypes);
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
					ReflectClassZZZ.scanInterfacesSuper(objInterfaceSuper, listaInterfaceSuper);					
				}
			}
		}
		
		/* ArrayList mit den Klassen-Objekten der Elternklassen zurueckgeben. 
	     * ! intern Rekursiver Aufruf !
	     */
		public static void scanSuperClasses(Class<?>objClass, ArrayList<Class<?>> listaClassSuper){
			
			Class<?>objClassSuper = objClass.getSuperclass();
			if(objClassSuper!=null){
				//System.out.println("SuperClassname: " + objClassSuper.getName());
				listaClassSuper.add(objClassSuper);
				
				ReflectClassZZZ.scanSuperClasses(objClassSuper, listaClassSuper);
			}
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 * 
		 */
		public static ArrayList<Class<?>> getEmbeddedClasses(Class objClass, String sFilterClassname) throws ExceptionZZZ{
			ArrayList<Class<?>> embeddedTypes = new ArrayList<Class<?>>();
						
			ReflectClassZZZ.scanEmbeddedClasses(objClass, embeddedTypes);
			
			//!!! Filtere den gesuchten Klassennamen heraus, d.h. entferne alle, auf die er nicht zutrifft.
			for(Class objClassTemp : embeddedTypes){
				String sClassname = objClassTemp.getName();
				String sTemp = StringZZZ.rightback(sClassname,"$");
				if(!sTemp.equals(sFilterClassname)){
					embeddedTypes.remove(objClassTemp);
				}
			}
			return embeddedTypes;
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 * 
		 */
		public static ArrayList<Class<?>> getEmbeddedClasses(Class objClass) throws ExceptionZZZ{
			ArrayList<Class<?>> embeddedTypes = new ArrayList<Class<?>>();
					
			ReflectClassZZZ.scanEmbeddedClasses(objClass, embeddedTypes);
			
			return embeddedTypes;
		}
		
		/* ArrayList mit den eingebetteten Klassen zurückgeben. Wird vom mir verwendet für die FLAGZ - Enum Definition
		 * 
		 */
		public static void scanEmbeddedClasses(Class objClass, ArrayList<Class<?>> embeddedTypes) throws ExceptionZZZ{
			if(objClass==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
    			throw ez;
			}
			Class[] objaClass = objClass.getClasses();
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
			ArrayList<Class<?>> listaReturn = new ArrayList<Class<?>>();			
			main:{
				if(objClass==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Class Current", iERROR_PARAMETER_MISSING, ReflectClassZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
	    			throw ez;
				}
			
				Class[] objaClassDirect = objClass.getInterfaces();
				for(Class objClassDirect : objaClassDirect) {
					listaReturn.add(objClassDirect);
				}
				
				//Nun auf der Suche nach weiteren Interfaces.
				ArrayList<Class<?>> superTypes = new ArrayList<Class<?>>();	
				ReflectClassZZZ.scanSuperClasses(objClass, superTypes);
				for(Class objClassSuper : superTypes) {
					//System.out.println("Elternklasse: " + objClassSuper.getName());
					if(objClassSuper!=null) {
						Class[] objaInterfaceSuper = objClassSuper.getInterfaces();
						for(Class objInterfaceSuper : objaInterfaceSuper) {
							listaReturn.add(objInterfaceSuper);
						}
					}
				}													
			}//end main:
			return listaReturn;
		}
		
		public static ArrayList<Class<?>> getInstanceOfList(Object objToCheck) throws ExceptionZZZ {
			ArrayList<Class<?>>listaReturn=null;
			main:{
				if(objToCheck==null)break main;
				
				//Merke: Es gibt das auch bei ArrayListZZZ. Aber hier wird alles betrachte, nicht nur List-Objekte ...
				//       if(objAsList instanceof List){
				//	if(ArrayListZZZ.isEmpty((ArrayList<?>) objAsList))break main;
					
				ArrayListZZZ<Class<?>> listaExtended = new ArrayListZZZ<Class<?>>();
				Class<? extends Object> classTemp = objToCheck.getClass();//Das funktioniert aber nicht mit Interfaces
				listaExtended.addUnique(classTemp);
					
				ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(objToCheck.getClass());
				listaExtended.addAllUnique(listaInterface);
				
				listaReturn = (ArrayList<Class<?>>) listaExtended.toArrayList();				
			}//end main:
			return listaReturn;	
		}
}
