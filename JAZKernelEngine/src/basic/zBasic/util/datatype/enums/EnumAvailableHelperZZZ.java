package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.reflection.ReflectInterfaceZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.FlagZHelperZZZ;
import basic.zKernel.status.IStatusBooleanMessageZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ;
import basic.zKernel.status.StatusBooleanMessageZZZ;
import basic.zKernel.status.StatusLocalAvailableHelperZZZ;

/**Siehe auch StatuslLocalAvailableHelperZZZ
 *  
 * Merksatz (wichtig!)(von ChatGPT, 20260110)
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * 
 * @author Fritz Lindhauer, 27.03.2024, 19:44:32
 */
public class EnumAvailableHelperZZZ implements IConstantZZZ{	
		
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static boolean proofOnChange(HashMap<String,Boolean>hmStatusLocal, String sStatusName, boolean bValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			if(hmStatusLocal==null)break main;
						
			//1. existiert der Statuswert schon in der HashMap
			Boolean objValue = hmStatusLocal.get(sStatusName.toUpperCase());
			if(objValue==null) {
				bReturn = true;
				break main;
			}
			
			//2. Ist der Statuswert identisch
			if(objValue.booleanValue()!=bValue) {
				bReturn = true;
				break main;
			}				
		}//end main:
		return bReturn;
	}
	
	
	public static boolean proofExists(Class<?> cls, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumPropertyName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
					
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saAvailable = EnumAvailableHelperZZZ.searchInherited(cls, sEnumName);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saAvailable, sEnumPropertyName)) {
					bReturn = true;	
					break main;
				}
			}												
		}//end main:
		return bReturn;
	}
			
	public static boolean proofDirectExists(Class<?> cls, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumPropertyName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//NUR die Klasse selbst
			String[] saStatusAvailable = EnumAvailableHelperZZZ.searchDirect(cls, sEnumName);			
			if(saStatusAvailable!=null) {
				if(StringArrayZZZ.contains(saStatusAvailable, sEnumPropertyName)) {
					bReturn = true;	
					break main;
				}
			}						
		}//end main:
		return bReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, true, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchListDirect(Class cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		return searchListDirect_(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmediate);
	}
	
	private static ArrayList<String> searchListDirect_(Class cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
		ArrayList<String>listasReturn = null;
		main:{
			Enum[]enuma = EnumAvailableHelperZZZ.searchEnumArray(cls, sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
			if(ArrayUtilZZZ.isNull(enuma))break main;
			
			String[] saReturn = EnumUtilZZZ.toString(enuma);
			listasReturn = StringArrayZZZ.toArrayList(saReturn);
			
		}//end main:
		return listasReturn;
	}
	
	
	
	
	
	
	//public static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
	//s. ChatGPT vom 200260110
	//Class<E> statt Class<?> ist der entscheide Punkt. Das ? ist für den Compiler eine BlackBox und enthält keine Typinformationen
	//public static <E extends Enum<E> & IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<E> cls, String sEnumName)  throws ExceptionZZZ {
	
	

	//#######################################################
	//+++++++++++++++++++++++++++++++++++++++++
	//+++ Speziel für das IEnumSetMappedStatusZZZ ++++++++++++++++++
		
	
			
	//+++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++


	
		//public static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		//s. ChatGPT vom 200260110
		//Class<E> statt Class<?> ist der entscheide Punkt. Das ? ist für den Compiler eine BlackBox und enthält keine Typinformationen

		//public static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		//s. ChatGPT vom 200260110
		//Class<E> statt Class<?> ist der entscheide Punkt. Das ? ist für den Compiler eine BlackBox und enthält keine Typinformationen
		//public static <E extends Enum<E> & IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<E> cls, String sEnumName)  throws ExceptionZZZ {
	
	
		//################################################################################################
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, true, true);		
	}
	
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}
	
	public static ArrayList<String> searchList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchList_(cls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static ArrayList<String> searchList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		//1. von der Classe selbst implementiert
		//ArrayList<String> listasByDirect = EnumAvailableHelperZZZ.searchListDirect(cls,sEnumName,bScanInterfaceImmidiate);
		//false, damit nicht sofort die Interfaces geholt werden. Doppelte Schleifen sparen!!!
		ArrayList<String> listasByDirect = EnumAvailableHelperZZZ.searchListDirect(cls,sEnumName,false); 
				
		//2. allen Interfaces der Klasse, auch den erbenden implementiert
		ArrayList<String> listasByInterface = null;
		if(bScanInterfaceImmidiate) {
			//Achtung, das holt alle moeglichen Interfaces, aber nicht die Interfaces aus denen Interface erbt.
			//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(classToCheck);
			
			ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
			ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);			
			for(Class<?> objclsByInterface : listaClassInterface) {
				//false, weil ja die Interfaces eh betrachtet werde sollen
				Enum[] enumaByInterface = searchEnumArray(objclsByInterface, sEnumName, false, false);
				ArrayList<String>listasByInterfaceTemp=null;
				if(!ArrayUtilZZZ.isNull(enumaByInterface)) {	
					listasByInterfaceTemp = new ArrayList<String>();
					for(Enum objEnum : enumaByInterface) {
						String sEnum = objEnum.name();
						if(!listasByInterfaceTemp.contains(sEnum)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listasByInterfaceTemp.add(sEnum);
						}
					}			
				}
				if(!ArrayListUtilZZZ.isEmpty(listasByInterfaceTemp)) {
					if(ArrayListUtilZZZ.isEmpty(listasByInterface)) listasByInterface = new ArrayList<String>();				
					listasByInterface.addAll(listasByInterfaceTemp);
				}
			}
		}
		
		//3. von den Elternklassen der Klasse implementiert
		//!!! Rekursion
		ArrayList<String> listasByParent = new ArrayList<String>();	
		if(bScanParentClassImmidiate) {
			ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
			for(Class objcls : listaobjClass) {
				//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
				//Darum: Von der Vererbungshierarchie des Interface implementiert.
				ArrayList<String> listasByParentTemp = EnumAvailableHelperZZZ.searchList(objcls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
				if(!ArrayListUtilZZZ.isEmpty(listasByParentTemp)) {
					if(ArrayListUtilZZZ.isEmpty(listasByParent)) listasByParent = new ArrayList<String>();				
					listasByParent.addAll(listasByParentTemp);
				}			
			}
		}
				
		if(!ArrayListUtilZZZ.isEmpty(listasByDirect)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByDirect);
		}
		
		if(!ArrayListUtilZZZ.isEmpty(listasByInterface)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByInterface);
		}
		
		if(!ArrayListUtilZZZ.isEmpty(listasByParent)) {
			if(listasReturn==null) listasReturn = new ArrayList<String>();
			listasReturn.addAll(listasByParent);
		}
		
		listasReturn = (ArrayList<String>) ArrayListUtilZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	public static String[] search(Class<?> cls, String sEnumName) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
	
		ArrayList<String> listas = searchList(cls, sEnumName);
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] searchDirect(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = EnumAvailableHelperZZZ.searchListDirect(cls, sEnumName);			
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
		
	public static String[] searchInherited(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = EnumAvailableHelperZZZ.searchList(cls, sEnumName);					
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
			
			
	
		
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls, sEnumName, true, true);
//	 }
//	 
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls,sEnumName,bScanInterfaceImmidiate, true);
//	 }
//	
//	 public static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
//		 return EnumAvailableHelperZZZ.searchListEnumMappedList_(cls,sEnumName,bScanInterfaceImmidiate, bScanParentClassImmidiate);
//	 }
//	
//	
//	/** s. https://stackoverflow.com/questions/31104584/how-to-save-enum-class-in-a-hashmap
//	 * aber wenn ich ArrayList<Collection<? extends Enum<?>>> verwende, 
//	 * kommt es beim Hinzufügen des Enum - Objekts trotzdem zu einem TypeCast Fehler
//	 * Darum alles auf IEnumSetMappedZZZ umgestellt.
//	 * @param classToCheck
//	 * @throws ExceptionZZZ
//	 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
//	 */
//	private static ArrayList<IEnumSetMappedZZZ> searchListEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
//		ArrayList<IEnumSetMappedZZZ> listaeReturn = null;
//		main:{
//			if(cls==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//				 throw ez;
//			}
//		
//			//+++++++++++++++++++++++++++++++++++++++++++++++
//			//1. von der Classe selbst implementiert
//			ArrayList<IEnumSetMappedZZZ> listaeByDirect = EnumAvailableHelperZZZ.searchEnumMappedListDirect(cls, sEnumName, bScanInterfaceImmidiate);
//		
//		
//			//++++++++++++++++++++++++++++++++++++++++++++++++
//			//2. von den Interfaces implementiert
//			//20240331: Hier fehlen die erbenden interfacss
//			ArrayList<IEnumSetMappedZZZ> listaeByInterface = null;
//			if(bScanInterfaceImmidiate) {
//				//2. allen Interfaces der Klasse, auch den erbenden implementiert
//				//ArrayList<String> listasInterface = new ArrayList<String>();
//				//kann nicht instanziiert werden... ArrayList<? extends Enum<?>> listasInterface = new ArrayList<? extends Enum<?>>();
//				//ArrayList<Collection<? extends Enum<?>>> listasInterface = new ArrayList<Collection<? extends Enum<?>>>();			
//				ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
//				ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
//				for(Class<?> objclsByInterface : listaClassInterface) {					
//					Enum[] enumaByInterface = EnumAvailableHelperZZZ.searchEnum(objclsByInterface, sEnumName, false, false);
//					if(!ArrayUtilZZZ.isEmpty(enumaByInterface)){
//						for(Enum objEnum : enumaByInterface) {
//							if(ArrayListZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedZZZ>();
//							IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;
//							listaeByInterface.add(e);
//						}
//					}
//				}
//			}
//		
//			//+++++++++++++++++++++++++++++++++++++++++++++++
//			//3. von den Elternklassen der Klasse implementiert	
//			//!!! Rekursion
//			ArrayList<IEnumSetMappedZZZ> listaeBySuperclass = null;
//			if(bScanSuperclassImidiate) {
//				ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
//				for(Class<?> objcls : listaobjClass) {
//					//Rekursion der interited Klassen und Interfaces einleiten !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!		
//					ArrayList<IEnumSetMappedZZZ> listaeBySuperclassTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objcls,sEnumName,bScanInterfaceImmidiate, bScanSuperclassImidiate);
//					listaeBySuperclass = ArrayListZZZ.join(listaeBySuperclass, listaeBySuperclassTemp);
////					if(!ArrayListZZZ.isEmpty(listaeTemp)) {
////						for(IEnumSetMappedZZZ objEnum : listaeTemp) {
////							if(ArrayListZZZ.isEmpty(listaeByParent)) listaeByParent = new ArrayList<IEnumSetMappedZZZ>();
////							listaeByParent.add(objEnum);
////						}
////					}
//				}
//			}
//		
//			if(!ArrayListZZZ.isEmpty(listaeByDirect)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeByDirect);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeByInterface)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeByInterface);
//			}
//			
//			if(!ArrayListZZZ.isEmpty(listaeBySuperclass)) {
//				if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
//				listaeReturn.addAll(listaeBySuperclass);
//			}
//		
//			//listasReturn = (ArrayList<Collection<? extends Enum<?>>>) ArrayListZZZ.unique(listasReturn);
//			listaeReturn = (ArrayList<IEnumSetMappedZZZ>) ArrayListZZZ.unique(listaeReturn);
//		}//end main:
//		return listaeReturn;
//	}
	
	
	
	
	//++++++++++++++++++++++++
	//++++++++++++++++++++++++
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Object objForClass, String sEnumName) throws ExceptionZZZ {
//		Class<?> classToCheck = objForClass.getClass();
//		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, true, true);
//	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, true, true);
//	}

//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, bScanInterfaceImmidiate, true);
//	}

//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	public static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnumMapped_(classToCheck,sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
//	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//	private static <E extends IEnumSetMappedZZZ> E[] searchEnumMapped_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
//		E[] enumaReturn = null;
//		main:{
//			
//			ArrayList<IEnumSetMappedZZZ> listaMappedReturn = EnumAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName,bScanInterfaceImmidiate,bScanParentClassImmidiate);
//			if(ArrayListUtilZZZ.isEmpty(listaMappedReturn))break main;
//			
//			enumaReturn = ArrayListUtilZZZ.toEnumMappedArrayByMapped(listaMappedReturn);	
//		}//end main
//		return enumaReturn;
//	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
//	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnum_(classToCheck,sEnumName,true);
//	}
	
//	public static <E extends Enum> E[] searchEnum(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		return EnumAvailableHelperZZZ.searchEnum_(classToCheck,sEnumName,bScanInterfaceImmidiate);
//	}
	
//	private static <E extends Enum> E[] searchEnum_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
//		E[] objaeReturn = null;
//		main:{
//			if(classToCheck==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//				 throw ez;
//			}
//			
//			ArrayList<IEnumSetMappedZZZ> listaeDirect = EnumAvailableHelperZZZ.searchEnumMappedListDirect(classToCheck, sEnumName);
//			objaeReturn = ArrayListZZZ.toEnumArrayByMapped(listaeDirect);
//		}//end main:
//		return objaeReturn;
//	}
		
		
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++	
	public static <E extends Enum> E searchEnum(Object objForClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		Class<?> classToCheck = objForClass.getClass(); 
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImidiate, true);
	}
	
	public static <E extends Enum> E searchEnum(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnum_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImidiate, bScanSuperclassImidiate);
	}
	
	private static <E extends Enum> E searchEnum_(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
						
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedZZZ[] enumaByInterface3 = (IEnumSetMappedZZZ[])objEnum; 
		  		
				Object[] objaEnum = classToCheck.getEnumConstants();
				for(Object objEnum : objaEnum) {
					Enum e = (E) objEnum;
					if(e.name().equals(sEnumPropertyName)) {
						enumReturn = (E) e;								
						break main;
					}					
				} 	
				
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);			
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumPropertyName);	
						if(e!=null) {
							enumReturn = (E) e;										
							break main;
						}
					 }
				}
			}
			
			//Falls noch nix gefunden wurde... ggfs. die Interfaces scannen
			if(bScanInterfaceImidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces	
				//ArrayList<Class<?>> listaeClassByInterface = ReflectInterfaceZZZ.getEmbeddedClasses(classToCheck);

				//Achtung: Das holt alles sonstigen Interfaces, nicht die Interfaces, aus denen geerbt wird.
				//ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				//In der scanInterfacesSuper-Methode meiner ReflectClass-Klasse wird folgendes beachtet.https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				for(Class objClassInterface : listaInterfaceByClass) {
					
					//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
					ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
					for(Class objClass : listaClassByInterface) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumInnerName)) {
							Enum e = EnumHelperZZZ.getEnumAsField(objClass, sEnumPropertyName);	
							if(e!=null) {
								enumReturn = (E) e;										
								break main;
							}
						 }
					}//end for												
				}//end for	
			}//end if

			//++++++++++++++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			if( bScanSuperclassImidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//!!!Rekursion
					enumReturn=EnumAvailableHelperZZZ.searchEnum(objclsSuper, sEnumName,sEnumPropertyName,bScanInterfaceImidiate,bScanSuperclassImidiate);
					if(enumReturn!=null)break main;
				}			
			}			
		}//end main:
		return enumReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++
		//+++++++++++++++++++++++++++++++++
		public static <E extends Enum> E[] searchEnumArray(Object objForClass, String sEnumName)  throws ExceptionZZZ {
			Class<?> cls = objForClass.getClass();
			E[] enumaReturn = searchEnumArray_(cls,sEnumName, false, false);
			return enumaReturn;
		}
		 
		public static <E extends Enum> E[] searchEnumArray(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
			E[] enumaReturn = searchEnumArray_(cls,sEnumName, false, false);
			return enumaReturn;
		}
		
		public static <E extends Enum> E[] searchEnumArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
			E[] enumaReturn = searchEnumArray_(cls,sEnumName, bScanInterfaceImmidiate, false);
			return enumaReturn;
		}

		public static <E extends Enum> E[] searchEnumArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
			E[] enumaReturn = searchEnumArray_(cls,sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
			return enumaReturn;
		}
		
		
		private static <E extends Enum> E[] searchEnumArray_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ{
			E[] enumaReturn = null;
			main:{
				if(classToCheck==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
				ArrayList<E> listae = EnumAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);					
				enumaReturn = ArrayListUtilZZZ.toEnumArray(listae);
			}//end main:
			return enumaReturn;
		}

	
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumListDirect(Object objForClass, String sEnumName) throws ExceptionZZZ {
		Class<?> classToCheck = objForClass.getClass();
		return EnumAvailableHelperZZZ.searchEnumListDirect_(classToCheck,sEnumName,true);
	}

	public static <E extends Enum> ArrayList<E> searchEnumListDirect(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumListDirect_(classToCheck,sEnumName,true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumListDirect(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumListDirect_(classToCheck,sEnumName,bScanInterfaceImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> searchEnumListDirect_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		ArrayList<E> listaReturn = EnumAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, false);			
		return listaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++
	public static <E extends Enum> ArrayList<E> searchEnumList(Object objForClass, String sEnumName) throws ExceptionZZZ {
		Class<?> classToCheck = objForClass.getClass();
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,true,true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,true,true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, true);
	}
	
	public static <E extends Enum> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ {
		return EnumAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, bScanSuperclassImmidiate);
	}
	
	private static <E extends Enum> ArrayList<E> searchEnumList_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
		ArrayList<E> listaeReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			ArrayList<E> listaeByClass = null;
			if(classToCheck.isEnum()) { //Auch wenn das Enum direkt angegben wird.
				//z.B. das enum selbst gibt es so:
				//Class<?> objClassFromInterface = IDummyTestObjectWithStatusByInterfaceZZZ.STATUSLOCAL.class;))
		    	//Object[] objEnum = objClassFromInterface.getEnumConstants();
		    	//IEnumSetMappedZZZ[] enumaByInterface3 = (IEnumSetMappedZZZ[])objEnum; 
		  		    	 				
				Object[] objaEnum = classToCheck.getEnumConstants();
				if(!ArrayUtilZZZ.isNull(objaEnum)) {
					listaeByClass = new ArrayList<E>();
					for(Object obj : objaEnum) {
						Enum e = (Enum) obj;
						listaeByClass.add((E) e);
					} 
				}
			}else {
				ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
				if(!ArrayListUtilZZZ.isEmpty(listaClass)) {
					for(Class objClass : listaClass) {
						String sEnumClass = objClass.getName();				
						if(sEnumClass.endsWith(sEnumInnerName)) {
							Object[] obja = objClass.getEnumConstants();
							
							for(Object obj : obja) {
								if(ArrayListUtilZZZ.isEmpty(listaeByClass)) listaeByClass = new ArrayList<E>();
								Enum e = (Enum) obj;
								listaeByClass.add((E) e);
							}
						}
					}	
				}
			}
						
			ArrayList<E>listaeByInterface=null;
			if(bScanInterfaceImidiate) {
				//+++++++++++++++++++++++++++++++++
				//Holle alle eingebetteten Klassen aus Interfaces
				//!!! Achtung, das holt nicht die Vererbung der Interfaces, sondern alle sonstigen Interfaces:
				//ArrayList<Class<?>> listaInterfaceByClass = ReflectInterfaceZZZ.getInterfaces(classToCheck);
				
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				if(!ArrayListUtilZZZ.isEmpty(listaInterfaceByClass)) {					
					for(Class objClassInterface : listaInterfaceByClass) {
						
						//Diese Interfaceklassen jetzt auch nach Embedded-Klassen scannen
						ArrayList<Class<?>> listaClassByInterface = ReflectClassZZZ.getEmbeddedClasses(objClassInterface);
						if(!ArrayListUtilZZZ.isEmpty(listaClassByInterface)) {
							for(Class objClass : listaClassByInterface) {
								String sEnumClass = objClass.getName();				
								if(sEnumClass.endsWith(sEnumInnerName)) {
									Object[] obja = objClass.getEnumConstants();
									for(Object obj : obja) {
										if(ArrayListUtilZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<E>();
										//20240403, warum wird hier gemappt? IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) obj;
										//                                   listaeByInterface.add((E) e);
										listaeByInterface.add((E) obj);
									}
								 }
							}//end for	
						}
					}//end for					
				}	
			}//end if
			
			
			//++++++++++++++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			ArrayList<E>listaeBySuperclass=null;
			if( bScanSuperclassImidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//!!!Rekursion
					ArrayList<E> listaeBySuperclassTemp=EnumAvailableHelperZZZ.searchEnumList(objclsSuper, sEnumName,bScanInterfaceImidiate,bScanSuperclassImidiate);
					listaeBySuperclass = (ArrayList<E>) ArrayListUtilZZZ.join(listaeBySuperclass, listaeBySuperclassTemp);			
				}			
			}
			
			
			//+++++++++++++++++++++++++++++++++++++++++++++
			if(!ArrayListUtilZZZ.isEmpty(listaeByClass)) {		
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeByClass);
			}
		
			if(!ArrayListUtilZZZ.isEmpty(listaeByInterface)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeByInterface);
			}
			
			if(!ArrayListUtilZZZ.isEmpty(listaeBySuperclass)) {
				if(listaeReturn==null) listaeReturn = new ArrayList<E>();
				listaeReturn.addAll(listaeBySuperclass);
			}
			
		
			listaeReturn = (ArrayList<E>) ArrayListUtilZZZ.unique(listaeReturn);
		}//end main:
		return listaeReturn;
	}
}

