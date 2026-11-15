package basic.zBasic.util.datatype.enums;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.EnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetFactoryZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
 *
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * 
 * @author Fritz Lindhauer, 10.01.2026, 08:27:05
 */
public class EnumSetMappedUtilZZZ extends EnumSetUtilZZZ{
	private static final long serialVersionUID = 9011362468839990162L;

	public EnumSetMappedUtilZZZ(){		
	}
	public EnumSetMappedUtilZZZ(EnumSet<?>enumSetUsed){
		super(enumSetUsed);
	}
	public EnumSetMappedUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		super(objClass);
	}
	public EnumSetMappedUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		super(objEnumSetFactory, objClass);
	}
	public EnumSetMappedUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		this.setEnumFactoryCurrent(objEnumSetFactory);
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
		
	//###############
	public boolean startsWithAnyAlias(String sToFind) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedUtilZZZ.startsWithAnyAlias(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAlias(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedZZZ> drivers = (Set<IEnumSetMappedZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.name())){
					String sName = driver.name();
					if(StringZZZ.startsWithIgnoreCase(sName,sToFind)){
					  bReturn = true;
					  break;
				  }
			  }
			}
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
	public static IEnumSetMappedZZZ startsWithAnyAlias_EnumMappedObject(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		IEnumSetMappedZZZ objReturn = null;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedZZZ> drivers = (Set<IEnumSetMappedZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				
				if(!StringZZZ.isEmpty(driver.name())){
					if(StringZZZ.startsWithIgnoreCase(sToFind, driver.name())){
					  objReturn = driver;
					  break;
				  }
			  }
			}
		}
		return objReturn;
	}
	
	public boolean startsWithAnyAbbreviation(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedUtilZZZ.startsWithAnyAbbreviation(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAbbreviation(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			Set<IEnumSetMappedZZZ> drivers = (Set<IEnumSetMappedZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedZZZ driver : drivers) {
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//			  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			String sAbbreviation = driver.getAbbreviation();
				if(!StringZZZ.isEmpty(sAbbreviation)){
				  if(sAbbreviation.startsWith(sToFind)){  //!!! hier auch die Groß-/Kleinschreibung unterscheiden.
					  bReturn = true;
					  break;
				  }
			  }
		
			}//end for
		}//end main
		return bReturn;
	}
		
	
	public boolean startsWithAnyDescription(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedUtilZZZ.startsWithAnyDescription(sToFind, drivers);
		}
		return bReturn;
	}
		public static boolean startsWithAnyDescription(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				Set<IEnumSetMappedZZZ> drivers = (Set<IEnumSetMappedZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
				for(IEnumSetMappedZZZ driver : drivers) {
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//				  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
							
					if(!StringZZZ.isEmpty(driver.getDescription())){
					  if(driver.getDescription().startsWith(sToFind)){  //Groß-/Kleinschreibung beachten.
						  bReturn = true;
						  break;
					  }
				  }				
				}//end for
			}//end main:
			return bReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_NameValue(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).name();
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_StringValue(Class<?> clazz, String name) throws ExceptionZZZ {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).toString();//ACHTUNG: Das ist der Name ggfs. mit einem Gleichheitszeichen darin z.B. meinstatus=
		    sReturn = StringZZZ.left(sReturn+"=", "=");
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_toString(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    String sReturn = Enum.valueOf((Class<Enum>)clazz, name).toString();//ACHTUNG: Das ist der Name ggfs. mit einem Gleichheitszeichen darin z.B. meinstatus=
		    return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_OrdinalValue(Class<?> clazz, String name) {
		    if (clazz==null || name==null || name.isEmpty()) {
		        return null;
		    }
		    int iReturn = Enum.valueOf((Class<Enum>)clazz, name).ordinal();
		    Integer intReturn = new Integer(iReturn);
		    return intReturn;
		}
		
		//getAbbreviation();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static String getEnumConstant_AbbreviationValue(Class<IEnumSetMappedZZZ> clazz, String name) throws ExceptionZZZ {
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			
				if(!StringZZZ.isEmpty(driver.getAbbreviation())){
				  if(driver.name().equals(name)){
					  sReturn = driver.getAbbreviation();
					  break main;
				  }
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		//public static String getEnumConstant_DescriptionValue(Class<IEnumSetMappedZZZ> clazz, String name) {
		public static String getEnumConstant_DescriptionValue(Class<IEnumSetMappedZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  sReturn = driver.getDescription();
				  break main;
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}		
			
			
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static String getEnumConstant_StatusMessageValue(Class<IEnumSetMappedStatusLocalZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusLocalZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusLocalZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  sReturn = driver.getStatusMessage();
				  break main;
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}		
			
			
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static int getEnumConstant_StatusGroupIdValue(Class<IEnumSetMappedStatusLocalZZZ> clazz, String name) {				
			int iReturn = -1;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStatusLocalZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStatusLocalZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  iReturn = driver.getStatusGroupId();
				  break main;
			  }
		
			}//end for
			}//end main:
			return iReturn;
		}		
				
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_PositionValue(Class<IEnumSetMappedZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				  if(driver.name().equals(name)){
					 int iValue = driver.getPosition();
					intValue = new Integer(iValue);
				  }
		
			}//end for
			}//end main:
			return intValue;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static Integer getEnumConstant_IndexValue(Class<IEnumSetMappedZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
				  if(driver.name().equals(name)){
					 int iValue = driver.getIndex();
					intValue = new Integer(iValue);
				  }
		
			}//end for
			}//end main:
			return intValue;
		}
		
		//public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
		//<E extends Enum<E> & IEnumSetMappedZZZ>
		//public static ArrayList<E extends Enum<E> & IEnumSetMappedStatusZZZ>toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ {
		public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStatusLocalZZZ> listae) throws ExceptionZZZ{
			ArrayList<Collection<? extends Enum<?>>> listaEnum = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					listaEnum.add((Collection<? extends Enum<?>>) objMapped);					
				}				
			}
			return listaEnum;
		}
		
		public static <E extends Enum> E[] toEnumArray(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ{
			E[] objaeReturn = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				//1. Versuch: Direkt per Cast.
				//Exception:  objaeReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
				
				//2. Versuch:
				//Exception: objaeReturn = (E[]) ArrayListZZZ.toEnumMappedArray(listae);
				
				
				//3. Versuch: Per Mappen der Einzelwerte
				//Das Klappt....IEnumSetMappedZZZ[] objaeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
								
				//EXCEPTION: ArrayList<Collection<? extends Enum<?>>> listaeTemp = new ArrayList<Collection<? extends Enum<?>>>();
				ArrayList<E> listaeTemp = new ArrayList<E>();
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					//if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					//3. aber Excepteion: listaeTemp.add((Collection<? extends Enum<?>>) objMapped);
					listaeTemp.add((E) objMapped); 
				}
				
				//3.
				objaeReturn = ArrayListUtilZZZ.toEnumArray(listaeTemp);
			}
			
			return objaeReturn;
		}
		
		
		public static ArrayList<Collection<? extends Enum<?>>> toEnumArrayList(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ{
			ArrayList<Collection<? extends Enum<?>>> listaEnum = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					listaEnum.add((Collection<? extends Enum<?>>) objMapped);					
				}				
			}
			return listaEnum;
		}
			
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(Enum[] enuma) throws ExceptionZZZ{
			E[] enumaReturn = null;
			main:{
				if(ArrayUtilZZZ.isNull(enuma)) break main;
				if(!(enuma instanceof IEnumSetMappedZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				ArrayList<IEnumSetMappedZZZ> listeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArrayList(enuma);
				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
				
				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedZZZ[listeReturnTemp.size()]);
			}//end main:
			return enumaReturn;	
		}
		
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(List<E> listae){
			E[] enumaReturn = null;
			main:{
				if(listae==null) break main;
				if(listae.size()==0) break main;
											
				enumaReturn = (E[]) listae.toArray(new IEnumSetMappedZZZ[listae.size()]);
			}//end main:
			return enumaReturn;	
		}
		
		public static <E extends Enum> E[] toEnumArrayByMapped(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ{
			E[] objaeReturn = null;
			main:{
				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
				
				//1. Versuch: Direkt per Cast.
				//Exception:  objaeReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
				
				//2. Versuch:
				//Exception: objaeReturn = (E[]) ArrayListZZZ.toEnumMappedArray(listae);
				
				
				//3. Versuch: Per Mappen der Einzelwerte
				//Das Klappt....IEnumSetMappedZZZ[] objaeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
								
				//EXCEPTION: ArrayList<Collection<? extends Enum<?>>> listaeTemp = new ArrayList<Collection<? extends Enum<?>>>();
				ArrayList<E> listaeTemp = new ArrayList<E>();
				
				for(IEnumSetMappedZZZ objMapped : listae) {
					//if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					//3. aber Excepteion: listaeTemp.add((Collection<? extends Enum<?>>) objMapped);
					listaeTemp.add((E) objMapped); 
				}
				
				//3.
				objaeReturn = ArrayListUtilZZZ.toEnumArray(listaeTemp);
			}
			
			return objaeReturn;
		}
		
		
		
//		public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> E[] toEnumArrayByMappedStatusLocal(ArrayList<IEnumSetMappedStatusLocalZZZ> listae) throws ExceptionZZZ{		
//			E[] objaeReturn = null;
//			main:{
//				if(ArrayListUtilZZZ.isEmpty(listae)) break main;
//				
//				//1. Versuch: Direkt per Cast.
//				//Exception:  objaeReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
//				
//				//2. Versuch:
//				//Exception: objaeReturn = (E[]) ArrayListZZZ.toEnumMappedArray(listae);
//				
//				
//				//3. Versuch: Per Mappen der Einzelwerte
//				//Das Klappt....IEnumSetMappedZZZ[] objaeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
//								
//				//EXCEPTION: ArrayList<Collection<? extends Enum<?>>> listaeTemp = new ArrayList<Collection<? extends Enum<?>>>();
//				ArrayList<E> listaeTemp = new ArrayList<E>();
//				
//				for(IEnumSetMappedStatusLocalZZZ objMapped : listae) {
//					//if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
//					//3. aber Excepteion: listaeTemp.add((Collection<? extends Enum<?>>) objMapped);
//					listaeTemp.add((E) objMapped); 
//				}
//				
//				//3.
//				objaeReturn = ArrayListUtilZZZ.toEnumArray(listaeTemp);
//			}
//			
//			return objaeReturn;
//		}
		
		
		
		
		
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110		
//		public static IEnumSetMappedZZZ[] toEnumMapped(ArrayList<IEnumSetMappedZZZ> listae){
//			IEnumSetMappedZZZ[] objaReturn = null;
//			main:{
//				objaReturn = (IEnumSetMappedZZZ[]) ArrayListUtilZZZ.toArray(listae);
//			}
//			return objaReturn;
//		}
//				
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(Enum[] enuma){
//			E[] enumaReturn = null;
//			main:{
//				if(ArrayUtilZZZ.isNull(enuma)) break main;
//				
//				ArrayList<IEnumSetMappedZZZ> listeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedArrayList(enuma);
//				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
//				
//				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedZZZ[listeReturnTemp.size()]);
//			}//end main:
//			return enumaReturn;	
//		}
//		
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(ArrayList<E> listae){
//			E[] enumaReturn = null;
//			main:{
//				if(listae==null) break main;
//				if(listae.size()==0) break main;
//											
//				enumaReturn = (E[]) listae.toArray(new IEnumSetMappedZZZ[listae.size()]);
//			}//end main:
//			return enumaReturn;	
//		}
						
//		public static ArrayList<IEnumSetMappedZZZ> toEnumMappedArrayList(List<IEnumSetMappedZZZ> enuma){
//			ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
//			main:{
//				if(enuma==null) break main;
//				//if(!(enuma instanceof List<IEnumSetMappedZZZ>)) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
//				
//				listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				for(IEnumSetMappedZZZ objenum : enuma) {				
//					listaeReturn.add(objenum);
//				}				
//			}//end main:
//			return listaeReturn;	
//		}
		
		public static <E extends Enum<E> & IEnumSetMappedZZZ> ArrayList<IEnumSetMappedZZZ> toEnumMappedArrayList(Enum[] enuma) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
			main:{
				if(enuma==null) break main;
				if(!(enuma instanceof IEnumSetMappedZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
				for(Enum objenum : enuma) {				
					listaeReturn.add((IEnumSetMappedZZZ) objenum);
				}
			}//end main:
			return listaeReturn;	
		}
		
		public static <E extends Enum<E> & IEnumSetMappedZZZ> ArrayList<IEnumSetMappedZZZ> toEnumMappedArrayList(List<E> listae) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
			main:{
				if(listae==null) break main;
				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
				for(E objenum : listae) {				
					listaeReturn.add((IEnumSetMappedZZZ) objenum);
				}
			}//end main:
			return listaeReturn;	
		}
		
		
		/**
		 * @param enuma
		 * @return
		 */
//		public static ArrayList<IEnumSetMappedStatusZZZ> toEnumMappedStatusArrayList(List<IEnumSetMappedStatusZZZ> enuma){
//			ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
//			main:{
//				if(enuma==null) break main;
//				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
//				
//				listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				for(IEnumSetMappedStatusZZZ objenum : enuma) {				
//					listaeReturn.add(objenum);
//				}				
//			}//end main:
//			return listaeReturn;	
//		}
		
		
		public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<E> toEnumMappedStatusArrayList(List<E> listae) throws ExceptionZZZ{			
			ArrayList<E> listaeReturn = null;
			main:{
				if(listae==null) break main;
				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<E>();
				for(E objenum : listae) {				
					listaeReturn.add(objenum);
				}
			}//end main:
			return listaeReturn;	
		}
		
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedStatusArray(Enum[] enuma){
//			E[] enumaReturn = null;
//			main:{
//				if(ArrayUtilZZZ.isNull(enuma)) break main;
//				
//				ArrayList<IEnumSetMappedStatusZZZ> listeReturnTemp = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(enuma);
//				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
//				
//				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedStatusZZZ[listeReturnTemp.size()]);
//			}//end main:
//			return enumaReturn;	
//		}

//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//		public static <E extends IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumMappedStatusArray(ArrayList<E> listae) throws ExceptionZZZ{
//			IEnumSetMappedStatusZZZ[] enumaReturn = null;
//			main:{
//				if(listae==null) break main;
//				if(listae.size()==0) break main;
//				
//				//das geht so nicht:
//				//s. https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
//				//if(!(listae instanceof IEnumSetMappedStatusZZZ)) break main;
//				
//				//Also eigener, heuristischer Ansatz
//				boolean bInstanceOfMappedStatus = ArrayListUtilZZZ.isInstanceOf(listae, IEnumSetMappedStatusZZZ.class);
//				if(!bInstanceOfMappedStatus)break main;
//				
//				enumaReturn = listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
//			}//end main:
//			return enumaReturn;	
//		}	
		
//		public static ArrayList<IEnumSetMappedStatusZZZ> toEnumMappedStatusArrayList(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ{
//			ArrayList<IEnumSetMappedStatusZZZ> listaeReturn = null;
//			main:{
//				if(listae==null) break main;
//				
//				listaeReturn = new ArrayList<IEnumSetMappedStatusZZZ>();
//				if(listae.size()==0) break main;
//				
//				for(IEnumSetMappedZZZ obj : listae) {
//					IEnumSetMappedStatusZZZ objStatus = (IEnumSetMappedStatusZZZ) obj;
//					listaeReturn.add(objStatus);
//				}								
//			}//end main:
//			return listaeReturn;	
//		}	
		
			
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
