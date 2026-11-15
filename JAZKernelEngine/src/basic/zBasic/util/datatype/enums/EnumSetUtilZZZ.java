package basic.zBasic.util.datatype.enums;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.config.IEnumSetKernelConfigDefaultEntryZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class EnumSetUtilZZZ extends AbstractObjectWithExceptionZZZ{
	protected EnumSet<?> enumSetCurrent=null;
	protected IEnumSetFactoryZZZ objEnumSetFactory=null;
	
	public EnumSetUtilZZZ(){		
	}
	public EnumSetUtilZZZ(EnumSet<?>enumSetUsed){
		this.setEnumSetCurrent(enumSetUsed);
	}
	public EnumSetUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		if(objClass==null){
			ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		IEnumSetFactoryZZZ objEnumSetFactory = EnumSetFactoryZZZ.getInstance();
		this.setEnumFactoryCurrent(objEnumSetFactory);
		
		EnumSet<?> enumSetUsed = this.getEnumSet(objClass);
		this.setEnumSetCurrent(enumSetUsed);
	}
	public EnumSetUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		if(objEnumSetFactory==null){
			ExceptionZZZ ez  = new ExceptionZZZ("EnumSetFactoryObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(objClass==null){
			ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(!objClass.isEnum()){
			ExceptionZZZ ez  = new ExceptionZZZ("ClassObject must be implemented as enum", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		this.setEnumFactoryCurrent(objEnumSetFactory);
		
		EnumSet<?> enumSetUsed = this.getEnumSet(objClass);
		this.setEnumSetCurrent(enumSetUsed);
	}
	public EnumSetUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		this.setEnumFactoryCurrent(objEnumSetFactory);
	}
	
	public IEnumSetFactoryZZZ getEnumSetFactoryInstanceCurrent(){
		return this.objEnumSetFactory; 
	}
	public void setEnumFactoryCurrent(IEnumSetFactoryZZZ objEnumSetFactory){
		this.objEnumSetFactory = objEnumSetFactory;
	}
	
	//##################################
	/*
	 * Ziel ist es eine Instanz der Enum Klasse zu bekommen. Die Instanz ist notwendig, um auf die Static-Methode getEnumSet() aufrufen zu können.
	 * 
	 * Versuch über ein Factory Klasse an die Instanz zu gelangen...
	 */
	public EnumSet<?> getEnumSet(Class objClassEnumSetUsed) throws ExceptionZZZ{
		main:{
			if(objClassEnumSetUsed==null){
				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			//EnumSetFactoryZZZ objFactory = EnumSetFactoryZZZ.getInstance(); //Nicht merhr direkt, sondern etwas dynamischer auf ein Klasse zugreifen
			IEnumSetFactoryZZZ objFactory = this.getEnumSetFactoryInstanceCurrent();
			if (objFactory==null){
				ExceptionZZZ ez  = new ExceptionZZZ("ObjectFactoryCurrent is Null ", iERROR_PARAMETER_VALUE, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			EnumSet<?> x = objFactory.getEnumSet(objClassEnumSetUsed);
			this.setEnumSetCurrent(x);			
		}//end main:
		return this.getEnumSetCurrent();
	}
	
	//##################################
	/* 
	 * Ziel ist es eine Instanz der Enum Klasse zu bekommen. Die Instanz ist notwendig, um auf die Static-Methode getEnumSet() aufrufen zu können.
	 * 
	 * Das Problem ist, dass man eigentlich Enum Klassen nicht per Reflection erzeugen kann, weil der Konstruktor private ist.
	 * Solch ein Konstruktor kann auch nicht einfach public gemacht werden, das liegt daran wie die reinen Enum-Klassen designed sind.
	 * Weder "extends" noch "implements" funktioniert.
	 * 
	 * Der Workaround per Reflection die Klasse zu erzeugen funktioniert leider nur im Debugger unter Java 1.6.
	 *  
	 */
	public EnumSet<?> getEnumSet_TryoutReflectionFailed(Class objClassEnumSetUsed) throws ExceptionZZZ{
		main:{
			if(objClassEnumSetUsed==null){
				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
	
			Object objClassInstantiated =null;
			try {
				@SuppressWarnings("unchecked")
				EnumSet<?> set = EnumSet.noneOf(objClassEnumSetUsed);//Erstelle ein leeres EnumSet
				
				
				//TODO GOON: Per Reflection eine Instanz der Klasse holen
				//ACHTUNG: Enums haben keine public Konstruktor und können daher nicht instantiiiert werden?		
				//               Das wird im folgenden Code bewiesen
				/*				
				Class<?> objClassEnclosing = ReflectClassZZZ.getEnclosingClass(objClassEnumSetUsed);				
				if(objClassEnclosing!=null){
					//also eine innere Klasse
					Object objClassEnclosingInstance = objClassEnclosing.newInstance();
					Constructor<?> ctor;
					try {
						ctor = objClassEnumSetUsed.getDeclaredConstructor(objClassEnclosing);
				
						//Object objInnerInstance = ctor.newInstance(objClassEnclosingInstance);
						objClassInstantiated = ctor.newInstance(objClassEnclosingInstance);
					   System.out.println("erfolgerich instantiiert.");
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}else{
					//Also keine innere Klasse
					//Merke: Enums haben keinen public  Konstruktor, können also nicht Intantiiert werden. objClassInstantiated = objClassEnumSetUsed.newInstance();//java.lang.InstantiationException
					Constructor<?> ctor;
					try {
//						Constructor constructor = ctClass.getConstructor(new Class[] { String.class, String.class, String.class });
//						ConstructorTest ctArgs = (ConstructorTest) constructor.newInstance(new Object[] { "first", "second", "third" });
//						ctArgs.setPub("created this with constructor.newInstance(new Object[] { \"first\", \"second\", \"third\" })");
						
						//ctor = objClassEnumSetUsed.getConstructor(objClassEnumSetUsed);
						ctor = objClassEnumSetUsed.getDeclaredConstructor(objClassEnumSetUsed,String.class, String.class);
						//ctor = objClassEnumSetUsed.getConstructor(objClassEnumSetUsed,String.class, String.class);
						if(ctor==null){
							ExceptionZZZ ez  = new ExceptionZZZ("ClassObject besitzt nicht den für das Mapping vorausgesetzten Konstruktor mit 2 Strings", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else{							
						}			
						
						//no such method exception   Object objInnerInstance = ctor.newInstance(objClassEnclosingInstance);
						objClassInstantiated = ctor.newInstance(objClassEnumSetUsed);
						 System.out.println("erfolgerich instantiiert.");
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				  */
					
				//HIER DEN WORKAROUND VERWENDEN
					//ACHTUNG: ANGEBLICH haben Enums keine public Konstruktor und können daher nicht instantiiiert werden.?
					//Das soll eine Lösung sein: 					
					/* Example: 
					 * enum Day { SUN, MON, TUES, WED, THUR, FRI, SAT }
					 * What if you want to add one more day? Usually, you can't, and you may say: "why would I want to do that?". Well, let's say we just want to. 
					 * If you are interested about my use case, see at the end. So let's try. First try, extend the enum: public enum DayPlus extends Day { MYDAY }This doesn't work, Javac doesn't like 'extends' here. 
					 * The same if you try 'implements'. 
					 * What about using reflection?Day.class.newInstance();This compiles - but doesn't work as either. 
					 * 
					 * 
					 * Constructor con = Day.class.getDeclaredConstructors()[0]; 
					 * Method[] methods = con.getClass().getDeclaredMethods(); 
					 * for (Method m : methods) { 
					 * 		if (m.getName().equals("acquireConstructorAccessor")) { 
					 * 			m.setAccessible(true); 
					 * 			m.invoke(con, new Object[0]);
					 * 		 } 
					 * } 
					 * Field[] fields = con.getClass().getDeclaredFields(); 
					 * Object ca = null; 
					 * for (Field f : fields) { 
					 * 		if (f.getName().equals("constructorAccessor")) { 
					 * 			f.setAccessible(true); 
					 * 			ca = f.get(con); 
					 * 		} 
					 * } 
					 * Method m = ca.getClass().getMethod( "newInstance", new Class[] { Object[].class }); 
					 * m.setAccessible(true);
					 * Day v = (Day) m.invoke(ca, new Object[] { new Object[] { "VACATION", Integer.MAX_VALUE } }); 
					 * System.out.println(v.getClass() + ":" + v.name() + ":" + v.ordinal());
					 */	
					
				    System.setSecurityManager(null); //Testweise den Security Manager abstellen, damit sind private MEthoden ggfs. besser erreichbar.
					Constructor<?> con;
					try {
//						Constructor constructor = ctClass.getConstructor(new Class[] { String.class, String.class, String.class });
//						ConstructorTest ctArgs = (ConstructorTest) constructor.newInstance(new Object[] { "first", "second", "third" });
//						ctArgs.setPub("created this with constructor.newInstance(new Object[] { \"first\", \"second\", \"third\" })");
						
						//ctor = objClassEnumSetUsed.getConstructor(objClassEnumSetUsed);
						//no such Method Exception: ctor = objClassEnumSetUsed.getDeclaredConstructor(objClassEnumSetUsed,String.class, String.class);
						//                                    ctor = objClassEnumSetUsed.getConstructor(objClassEnumSetUsed,String.class, String.class);
						//ctor = objClassEnumSetUsed.getDeclaredConstructor(String.class, String.class);
						con = objClassEnumSetUsed.getDeclaredConstructors()[0];
						if(con==null){
							ExceptionZZZ ez  = new ExceptionZZZ("ClassObject besitzt nicht den für das Mapping vorausgesetzten Konstruktor mit 2 Strings", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}else{	
							 Method[] methods = con.getClass().getDeclaredMethods(); //für private Methoden
							 for (Method m : methods) { 
								 if (m.getName().equals("acquireConstructorAccessor")) { 
									    System.out.println("Methode mit dem Namen acquireCostructorAcessor gefunden.");
							  			m.setAccessible(true); //wichtig bei private Methoden.
							  			System.out.println("invoke auf den Konstruktor");
							 			m.invoke(con, new Object[0]);
							 			System.out.println("invoke auf den Konstruktor - erfolgreich");
							 			break; 
							  		 } 
							  } 
							 
							 
							 Field[] fields = con.getClass().getDeclaredFields(); 
							Object ca = null; 
							for (Field f : fields) { 
								if (f.getName().equals("constructorAccessor")) { 
									f.setAccessible(true); 
									ca = f.get(con); 
									System.out.println("Feld mit dem Namen constructorAcessor gefunden. ca.getClass().getName(): "+ca.getClass().getName());
									break;
							 	} 
							 } 
							 System.out.println("hole Methode newInstance auf ein gefundenes  Objekt.");
							 Method m = ca.getClass().getMethod( "newInstance", new Class[] { Object[].class }); 
							 if (m==null){
								 System.out.println("Methode newInstance auf ein gefundenes  Objekt NICHT GEFUNDEN.");
							 }else{								 
								 System.out.println("Methode newInstance auf ein gefundenes  Objekt GEFUNDEN.");
								 
								 m.setAccessible(true);
								 //HIER DIE VORLAGE 
//								 Day v = (Day) m.invoke(ca, new Object[] { new Object[] { "VACATION", Integer.MAX_VALUE } }); 
//								 System.out.println(v.getClass() + ":" + v.name() + ":" + v.ordinal());
								 
								 //UND HIER DER WEG DIES ZU REALISIEREN
								//java.lang.reflect.InvocationTargetException,  Caused by: java.lang.IllegalArgumentException: argument type mismatch
								 //objClassInstantiated = m.invoke(ca, new Object[] {new Object[]{"OHJE","YESYES"}});
								 
								 //Invocation taget Exception, Caused by: java.lang.IllegalArgumentException: wrong number of arguments
								 //objClassInstantiated = m.invoke(ca, new Object[] {new Object[]{"OHJE",new String("YESYES"), new String("NONO")}});
								 
								 //InvocationTargetException: Caused by: java.lang.IllegalArgumentException: argument type mismatch
								 //objClassInstantiated = m.invoke(ca, new Object[] {new Object[]{new String("YESYES"), new String("NONO")}});
								 
								 //java.lang.IllegalArgumentException: wrong number of arguments
								 //objClassInstantiated = m.invoke(ca, new Object[] {new String("YESYES"), new String("NONO")});
								 
								 //java.lang.IllegalArgumentException: wrong number of arguments
								 //objClassInstantiated = m.invoke(ca);
								 
								// java.lang.IllegalArgumentException: argument type mismatch
								 //objClassInstantiated = m.invoke(ca, new  Object());
								 
								 //Caused by: java.lang.IllegalArgumentException: wrong number of arguments
								 //objClassInstantiated = m.invoke(ca, new  String[]{"ENS","ZWEI"});
								 
								 //java.lang.IllegalArgumentException: object is not an instance of declaring class
								 //objClassInstantiated = m.invoke(new Object());
								 
								 //ca ist von sun.reflect.DelegatingConstructorAccessorImpl ,,, newInstance(..) erwartet auf jeden Fall ein Object-Array
								 //java.lang.IllegalArgumentException: wrong number of arguments
								 //objClassInstantiated = m.invoke(ca, new  Object[]{});
								 
								 //java.lang.IllegalArgumentException: argument type mismatch
								 //objClassInstantiated = m.invoke(ca, new  Object[]{""});
								 //objClassInstantiated = m.invoke(ca, new  Object[]{new String("")});
								 //objClassInstantiated = m.invoke(ca, new  Object[]{new Object()});
								 
								 //Invocation Target Exception Caused by: java.lang.IllegalArgumentException: wrong number of arguments
								// objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{}});
								 
								 //Caused by: java.lang.IllegalArgumentException: argument type mismatch
								 //objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{"",""}});
								// objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{new Object(),new Object()}});
								// objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{new String(""),new String("")}});
								 
								 //Äh, das klappt, im DEBUGGER, TODO GOON: Das soll klappen im Echtlauf!!! Momentan "Wrong Number of Arguments"
								 //objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{new String("irgendwas"),new Integer(1)}});								
								 //objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{new String("irgendwas"), Integer.MAX_VALUE}});
								 
								 //Klappt unter 1.6 im Debugger 
								 objClassInstantiated = m.invoke(ca, new  Object[]{new Object[]{"irgendwas", Integer.MAX_VALUE}});								 
								 System.out.println("Methode newInstance erfolgreich ausgeführt.");
							 }							 
						}			
						
						//Object objInnerInstance = ctor.newInstance(objClassEnclosingInstance);
						//Klappt nicht ... objClassInstantiated = ctor.newInstance(objClassEnumSetUsed);
					
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					
//				System.out.println("Klasseninstanz erstellt.");
//				System.out.println("Klassenname: " + objClassInstantiated.getClass().getName());
				
	             //###################
				//Nun die statische Methode aufrufen getEnumSet(). Das kann auch wieder nur per Reflection passieren
				Method method;
				try {
					method = objClassInstantiated.getClass().getMethod("getEnumSet", null);
					EnumSet<?> setByReflection = (EnumSet<?>) method.invoke(null, null);
					this.setEnumSetCurrent(setByReflection);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}			
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}//end main:
		return this.getEnumSetCurrent();	
	}
	public EnumSet<?> getEnumSetCurrent() {
		return (EnumSet<?>) this.enumSetCurrent;
	}

	public void setEnumSetCurrent(EnumSet<?> enumSetCurrent) {
		this.enumSetCurrent = enumSetCurrent;
	}
	
	
	//###############
	public boolean startsWithAnyAlias(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedUtilZZZ.startsWithAnyAlias(sToFind, drivers);
		}
		return bReturn;
	}
	
	public IEnumSetMappedZZZ  startsWithAnyAlias_EnumMappedObject(String sToFind) throws ExceptionZZZ{
		IEnumSetMappedZZZ objReturn = null;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			objReturn = EnumSetMappedUtilZZZ.startsWithAnyAlias_EnumMappedObject(sToFind, drivers);
		}
		return objReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String readEnumConstant_NameValue(Class<?> clazz, String name) {
	    if (clazz==null || name==null || name.isEmpty()) {
	        return null;
	    }
	    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).name();
	    return sReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String readEnumConstant_StringValue(Class<?> clazz, String name) {
	    if (clazz==null || name==null || name.isEmpty()) {
	        return null;
	    }
	    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).toString();
	    return sReturn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_OrdinalValue(Class<?> clazz, String name) {
	    if (clazz==null || name==null || name.isEmpty()) {
	        return null;
	    }
	    int iReturn = Enum.valueOf((Class<Enum>)clazz, name).ordinal();
	    Integer intReturn = new Integer(iReturn);
	    return intReturn;
	}
							
	//#############################
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_IndexValue(Class<IEnumSetZZZ> clazz, String name) {
		Integer intValue = null;
		main:{
	    if (clazz==null || name==null || name.isEmpty()) break main;
	  
	    
	    IEnumSetZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
		for(IEnumSetZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
			
			  if(driver.getName().equals(name)){
				 int iValue = driver.getIndex();
				intValue = new Integer(iValue);
			  }
	
		}//end for
		}//end main:
		return intValue;
	}
	
			
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer readEnumConstant_PositionValue(Class<IEnumSetZZZ> clazz, String name) {
		Integer intValue = null;
		main:{
	    if (clazz==null || name==null || name.isEmpty()) break main;
	  
	    
	    IEnumSetZZZ[] enumaSetMapped = clazz.getEnumConstants();
	    if(enumaSetMapped==null) break main; //Das ist der Fall, wenn es isch um die übergebene Klasse nicht um eine Enumeration handelt
	    
		for(IEnumSetZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
			
			  if(driver.getName().equals(name)){
				 int iValue = driver.getPosition();
				intValue = new Integer(iValue);
			  }
	
		}//end for
		}//end main:
		return intValue;
	}
	
	
	public static boolean startsWithAnyAlias(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(setEnumCurrent==null) break main; 			
			
			@SuppressWarnings("unchecked")
			Set<IEnumSetZZZ> drivers = (Set<IEnumSetZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
		    
			for(IEnumSetZZZ driver : drivers) {
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.getName())){
					if(StringZZZ.startsWithIgnoreCase(driver.getName(),sToFind)){
					  bReturn = true;
					  break;
				  }
			  }
			}
		}
		return bReturn;
	}

	public static IEnumSetZZZ startsWithAnyAlias_EnumMappedObject(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		IEnumSetZZZ objReturn = null;
		main:{
			if(setEnumCurrent==null) break main; 
			
			@SuppressWarnings("unchecked")
			Set<IEnumSetZZZ> drivers = (Set<IEnumSetZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetZZZ driver : drivers) {
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.getName())){
					if(StringZZZ.startsWithIgnoreCase(sToFind, driver.getName())){
					  objReturn = driver;
					  break;
				  }
			  }
			}
		}
		return objReturn;
	}
	
		//###################################################
		//### Mengenoperationen
	//TODOGOON ChatGPT vom 20260222: Das muss getestet werden.
	public static IEnumSetZZZ difference(EnumSet<?> setEnumCurrent, EnumSet<?> setEnumToCompare) throws ExceptionZZZ{
		IEnumSetZZZ objReturn = null;
		main:{
			if(setEnumCurrent==null && setEnumToCompare==null) break main; 
			
			//merke: removeAll veraendert das Set also vorher kopieren
			EnumSet<?> enumCopied = EnumSet.copyOf(setEnumCurrent);
			enumCopied.removeAll(setEnumToCompare);
			
			objReturn = (IEnumSetZZZ) enumCopied;
		}
		return objReturn;
	}
	
	/*
	 EnumSet<MyType> intersection = EnumSet.copyOf(set1);
intersection.retainAll(set2);
	 */
	
	/*
	 EnumSet<MyType> union = EnumSet.copyOf(set1);
union.addAll(set2);
	 */
		
		
		
		//##################################################
		
			
		/* TODO GOON: Weitere Ideen für die Utitlity Klasse
		 * hier kombineren von zwei Enumerations:
		 * If I have an Enum, I can create an EnumSet using the handy EnumSet class

enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
EnumSet<Suit> reds = EnumSet.of(Suit.HEARTS, Suit.DIAMONDS);
EnumSet<Suit> blacks = EnumSet.of(Suit.CLUBS, Suit.SPADES);

Give two EnumSets, how can I create a new EnumSet which contains the union of both of those sets?

		 *EnumSet<Suit> redAndBlack = EnumSet.copyOf(reds);
redAndBlack.addAll(blacks);
		 * 
		 */
	
	

}
