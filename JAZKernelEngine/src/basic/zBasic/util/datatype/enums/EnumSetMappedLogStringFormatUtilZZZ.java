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
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
 *
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * 
 * @author Fritz Lindhauer, 10.01.2026, 08:27:05
 */
public class EnumSetMappedLogStringFormatUtilZZZ extends EnumSetUtilZZZ{
	private static final long serialVersionUID = 9011362468839990162L;

	public EnumSetMappedLogStringFormatUtilZZZ(){		
	}
	public EnumSetMappedLogStringFormatUtilZZZ(EnumSet<?>enumSetUsed){
		super(enumSetUsed);
	}
	public EnumSetMappedLogStringFormatUtilZZZ(Class<?>objClass)throws ExceptionZZZ{
		super(objClass);
	}
	public EnumSetMappedLogStringFormatUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory, Class<?> objClass) throws ExceptionZZZ{
		super(objEnumSetFactory, objClass);
	}
	public EnumSetMappedLogStringFormatUtilZZZ(IEnumSetFactoryZZZ objEnumSetFactory){
		this.setEnumFactoryCurrent(objEnumSetFactory);
	}
	
	//###############
	public boolean startsWithAnyAlias(String sToFind) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			bReturn = EnumSetMappedLogStringFormatUtilZZZ.startsWithAnyAlias(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAlias(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedStringFormatZZZ> drivers = (Set<IEnumSetMappedStringFormatZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStringFormatZZZ driver : drivers) {
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
	
	public IEnumSetMappedStringFormatZZZ  startsWithAnyAlias_EnumMappedObject(String sToFind) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ objReturn = null;
		main:{
			EnumSet<?> drivers = this.getEnumSetCurrent();//..allOf(JdbcDriverClassTypeZZZ.class);
			objReturn = EnumSetMappedLogStringFormatUtilZZZ.startsWithAnyAlias_EnumMappedObject(sToFind, drivers);
		}
		return objReturn;
	}
	public static IEnumSetMappedStringFormatZZZ startsWithAnyAlias_EnumMappedObject(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ objReturn = null;
		main:{
			@SuppressWarnings("unchecked")
			Set<IEnumSetMappedStringFormatZZZ> drivers = (Set<IEnumSetMappedStringFormatZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStringFormatZZZ driver : drivers) {
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
			bReturn = EnumSetMappedLogStringFormatUtilZZZ.startsWithAnyAbbreviation(sToFind, drivers);
		}
		return bReturn;
	}
	
	public static boolean startsWithAnyAbbreviation(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			Set<IEnumSetMappedStringFormatZZZ> drivers = (Set<IEnumSetMappedStringFormatZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
			for(IEnumSetMappedStringFormatZZZ driver : drivers) {
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
			bReturn = EnumSetMappedLogStringFormatUtilZZZ.startsWithAnyDescription(sToFind, drivers);
		}
		return bReturn;
	}
		public static boolean startsWithAnyDescription(String sToFind, EnumSet<?> setEnumCurrent) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				Set<IEnumSetMappedStringFormatZZZ> drivers = (Set<IEnumSetMappedStringFormatZZZ>) setEnumCurrent;//..allOf(JdbcDriverClassTypeZZZ.class);
				for(IEnumSetMappedStringFormatZZZ driver : drivers) {
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
		public static String getEnumConstant_AbbreviationValue(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) throws ExceptionZZZ {
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
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
		public static String getEnumConstant_DescriptionValue(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
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
		public static Integer getEnumConstant_PositionValue(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
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
		public static Integer getEnumConstant_IndexValue(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) {
			Integer intValue = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
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
		
			
		//################################################################
		//### Besondere Werte dieses ENUMS
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static String getEnumConstant_Format(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) {				
			String sReturn = null;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  sReturn = driver.getFormat();
				  break main;
			  }
		
			}//end for
			}//end main:
			return sReturn;
		}		
			
			
		@SuppressWarnings({ "unchecked", "rawtypes" })			
		public static int getEnumConstant_Factor(Class<IEnumSetMappedStringFormatZZZ> clazz, String name) {				
			int iReturn = -1;
			main:{
		    if (clazz==null || name==null || name.isEmpty()) break main;
		  
		    
		    IEnumSetMappedStringFormatZZZ[] enumaSetMapped = clazz.getEnumConstants();
			for(IEnumSetMappedStringFormatZZZ driver : enumaSetMapped) {
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver ALIAS  als driver.name() from Enumeration="+driver.name());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.toString() from Enumeration="+driver.toString());
//					  System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Driver als driver.abbreviaton from Enumeration="+driver.getAbbreviation());
				
			if(driver.name().equals(name)){						 
				  iReturn = driver.getFactor();
				  break main;
			  }
		
			}//end for
			}//end main:
			return iReturn;
		}		
			
		
		//############################### UMWANDLUNGSMETHODEN
		
		//public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
		//<E extends Enum<E> & IEnumSetMappedZZZ>
		//public static ArrayList<E extends Enum<E> & IEnumSetMappedStatusZZZ>toEnum(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ {
		public static ArrayList<Collection<? extends Enum<?>>> toEnum(ArrayList<IEnumSetMappedStringFormatZZZ> listae) throws ExceptionZZZ{
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
		
		public static <E extends Enum> E[] toEnumArray(ArrayList<IEnumSetMappedStringFormatZZZ> listae) throws ExceptionZZZ{
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
				
				for(IEnumSetMappedStringFormatZZZ objMapped : listae) {
					//if(listaEnum==null) listaEnum = new ArrayList<Collection<? extends Enum<?>>>();
					//3. aber Excepteion: listaeTemp.add((Collection<? extends Enum<?>>) objMapped);
					listaeTemp.add((E) objMapped); 
				}
				
				//3.
				objaeReturn = ArrayListUtilZZZ.toEnumArray(listaeTemp);
			}
			
			return objaeReturn;
		}
		
		
//		public static <E extends Enum> E[] toEnumArrayByMapped(ArrayList<IEnumSetMappedZZZ> listae) throws ExceptionZZZ {
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
//				for(IEnumSetMappedZZZ objMapped : listae) {
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
//
//		}
//		
	
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN 
		public static <E extends IEnumSetMappedStringFormatZZZ> IEnumSetMappedStringFormatZZZ[] toEnumMappedArray(ArrayList<IEnumSetMappedStatusLocalZZZ> listae) throws ExceptionZZZ{
			IEnumSetMappedStringFormatZZZ[] objaReturn = null;
			main:{
				objaReturn = (IEnumSetMappedStringFormatZZZ[]) listae.toArray(new IEnumSetMappedStringFormatZZZ[listae.size()]);
			}
			return objaReturn;
		}
				
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedStringFormatZZZ> E[] toEnumMappedArray(Enum[] enuma) throws ExceptionZZZ{
			E[] enumaReturn = null;
			main:{
				if(ArrayUtilZZZ.isNull(enuma)) break main;
				
				ArrayList<IEnumSetMappedStringFormatZZZ> listeReturnTemp = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArrayList(enuma);
				if(ArrayListUtilZZZ.isEmpty(listeReturnTemp)) break main;
				
				enumaReturn = (E[]) listeReturnTemp.toArray(new IEnumSetMappedStringFormatZZZ[listeReturnTemp.size()]);
			}//end main:
			return enumaReturn;	
		}

		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
		//Meiner Meinung nach schon, wenn wir explizit nur mit diesem Interface arbeiten und nicht CASTEN
		public static <E extends IEnumSetMappedStringFormatZZZ> E[] toEnumMappedArray(List<E> listae){
			E[] enumaReturn = null;
			main:{
				if(listae==null) break main;
				if(listae.size()==0) break main;
											
				enumaReturn = (E[]) listae.toArray(new IEnumSetMappedStringFormatZZZ[listae.size()]);
			}//end main:
			return enumaReturn;	
		}
						
		public static <E extends Enum<E> & IEnumSetMappedStringFormatZZZ> ArrayList<IEnumSetMappedStringFormatZZZ> toEnumMappedArrayList(List<IEnumSetMappedStringFormatZZZ> listae) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaeReturn = null;
			main:{
				if(listae==null) break main;
				//if(!(enuma instanceof IEnumSetMappedStatusZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedStringFormatZZZ>();
				for(IEnumSetMappedStringFormatZZZ objenum : listae) {				
					listaeReturn.add(objenum);
				}
			}//end main:
			return listaeReturn;	
		}
		
		
		public static <E extends Enum<E> & IEnumSetMappedStringFormatZZZ> ArrayList<IEnumSetMappedStringFormatZZZ> toEnumMappedArrayList(Enum[] enuma) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaeReturn = null;			
			main:{
				if(enuma==null) break main;
				if(!(enuma instanceof IEnumSetMappedStringFormatZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedStringFormatZZZ>();
				for(Enum objEnum : enuma) {
					IEnumSetMappedStringFormatZZZ objEnumMapped = (IEnumSetMappedStringFormatZZZ) objEnum;
					listaeReturn.add(objEnumMapped);
				}
			}//end main:
			return listaeReturn;	
		}
		
		public static <E extends Enum<E> & IEnumSetMappedStringFormatZZZ> ArrayList<IEnumSetMappedStringFormatZZZ> toEnumMappedArrayList(IEnumSetMappedStringFormatZZZ[] enuma) throws ExceptionZZZ{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaeReturn = null;			
			main:{
				if(enuma==null) break main;
				if(!(enuma instanceof IEnumSetMappedStringFormatZZZ[])) break main; //sicherstellen, das der Datentyp "Castfaehig" ist.
				
				listaeReturn = new ArrayList<IEnumSetMappedStringFormatZZZ>();
				for(IEnumSetMappedStringFormatZZZ objEnum : enuma) {
					IEnumSetMappedStringFormatZZZ objEnumMapped = (IEnumSetMappedStringFormatZZZ) objEnum;
					listaeReturn.add(objEnumMapped);
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

		
		//TODODOON20260222;
		/* Hier Vergleichsoperation von 2 Arrays des EnumSets machen. 
		 * Es geht darum die Werte zu ermitteln, die in enuma02 enthalten sind, aber in enum01 nicht.
		 */
		public static <E extends IEnumSetMappedStringFormatZZZ> IEnumSetMappedStringFormatZZZ[] differenceMappedArray_rightSet(IEnumSetMappedStringFormatZZZ[] enuma01, IEnumSetMappedStringFormatZZZ[] enuma02) throws ExceptionZZZ{
			//E[] objaReturn=null;
			//ArrayListZZZ<IEnumSetMappedStringFormatZZZ>listaReturn=new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
			IEnumSetMappedStringFormatZZZ[] objaReturn;
			main:{
				//Merke: Einzelnes EnumSet würde so gemacht:
				//EnumSetUtilZZZ.difference(setEnumCurrent, setEnumToCompare)
				//Aber hier haben wir ein Array[]
				//Da es aber ein Array von EnumSets ist funktioniert die Array_Methode (basierend auf .equals() im Hintergrund nicht)
				//ArrayUtilZZZ.differenceSet(enuma01, enuma02, IEnumSetMappedStringFormatZZZ.class);
				
				//Ergo spezielle Methode hier - ... auch wg. der Typcast Problematik				
				ArrayListZZZ<IEnumSetMappedStringFormatZZZ>listaReturn=new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
				for(IEnumSetMappedStringFormatZZZ enum02temp : enuma02){
					String sName02temp = enum02temp.getName();
					boolean bfound = false;
					for(IEnumSetMappedStringFormatZZZ enum01temp : enuma01) {
						String sName01temp = enum01temp.getName();
						if(sName02temp.equals(sName01temp)) {
							bfound=true;
							break;
						}else {							
						}
					}
					if((!bfound)) {
						listaReturn.add(enum02temp);										
					}					
				}
				
				objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaReturn);
			}//end main:
			return objaReturn;
		}
}
