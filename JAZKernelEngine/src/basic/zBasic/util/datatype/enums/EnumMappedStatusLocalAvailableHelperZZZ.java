package basic.zBasic.util.datatype.enums;

import static basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ.sENUMNAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;


public class EnumMappedStatusLocalAvailableHelperZZZ  implements IConstantZZZ{
	//+++++++++++++++++++++++++
	//+++++++++++++++++++++++++
	//Hier wird in einer beliebige (!!!!) Klasse nach dem Enum mit dem Namen gesucht. 
	//Darum ist der Returnwert vorgegeben, aber die Klasse ist <?>
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, true, true);
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, true);
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ searchEnumMapped(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMapped_(classToCheck, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}
	
	private static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ searchEnumMapped_(Class<?> classToCheck, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate) throws ExceptionZZZ {
		E enumReturn = null;
		main:{
			String sEnumInnerName = ReflectClassZZZ.sINDICATOR_CLASSNAME_INNER + sEnumName;
			
			//+++++++++++++++++++++++++++++++++++
			//1. Direkte die Klasse abfragen
			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getEmbeddedClasses(classToCheck);
			if(!ArrayListUtilZZZ.isEmpty(listaClass)) {				
				for(Class objClass : listaClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						IEnumSetMappedZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumInnerName);	
						if(e!=null) {
							if(e.getName().equals(sEnumPropertyName)) {
								enumReturn = (E) e;										
								break main;
							}
						}
					 }
				}
			}
			
			//+++++++++++++++++++++++++++++++++++
			//2. Hole die Interfaces
			if(bScanInterfaceImmidiate) {
				//Achtung, das holt alle moeglichen Interfaces, aber nicht die Interfaces aus denen Interface erbt.
				//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(classToCheck);
								
				ArrayList<Class<?>> listaInterfaceByClass=new ArrayList<Class<?>>();
				ReflectClassZZZ.scanInterfacesSuper(classToCheck, listaInterfaceByClass);
				
				for(Class objClass : listaInterfaceByClass) {
					String sEnumClass = objClass.getName();				
					if(sEnumClass.endsWith(sEnumInnerName)) {
						IEnumSetMappedZZZ e = EnumHelperZZZ.getEnumAsField(objClass, sEnumInnerName);	
						if(e!=null) {
							if(e.getName().equals(sEnumPropertyName)) {
								enumReturn = (E) e;										
								break main;
							}
						}
					 }
				}
				
			}
			
		
			//+++++++++++++++++++++++++++++++++++
			//3. Hole die Elternklassen.
			if(bScanParentClassImmidiate) {
				ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(classToCheck);
				for(Class<?> objclsSuper : listaClassSuper) {
					//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					enumReturn = (E) EnumMappedAvailableHelperZZZ.searchEnumMapped(objclsSuper,sEnumName, sEnumPropertyName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
					if(enumReturn!=null) break main;
				}
			}
			
		}//end main:
		return enumReturn;
	}


	//+++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++
	public static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, true);		
	}

	
	public static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);		
	}

	public static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName(Object objForClass, String sEnumName, String sEnumPropertyName) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, true);
	}

	public static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedByName_(objForClass, sEnumName, sEnumPropertyName, bScanInterfaceImmidiate);
	}
	
	private static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName_(Class objClass, String sEnumName, String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusLocalZZZ objReturn = null;
		main:{
			if(objClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumName String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			HashMap<String,IEnumSetMappedStatusLocalZZZ> hmStatus = EnumMappedStatusLocalAvailableHelperZZZ.searchHashMapEnumMapped(objClass, sEnumName, bScanInterfaceImmidiate);
			if(hmStatus==null)break main;
			
			IEnumSetMappedStatusLocalZZZ objBooleanMessage = hmStatus.get(sEnumPropertyName);
			if(objBooleanMessage==null) break main;
			
			objReturn = (IEnumSetMappedStatusLocalZZZ) objBooleanMessage; //objBooleanMessage.getEnumObject();
		
		}//end main:
		return objReturn;		
	}

	private static IEnumSetMappedStatusLocalZZZ searchEnumMappedByName_(Object objForClass, String sEnumName,  String sEnumPropertyName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
		IEnumSetMappedStatusLocalZZZ objReturn = null;
		main:{
			if(objForClass==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Object for Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "Enum String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sEnumPropertyName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "EnumProperty String", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			Class<?> objClass = objForClass.getClass();
			ArrayList<IEnumSetMappedStatusLocalZZZ> listae = EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList(objClass, sEnumName, bScanInterfaceImmidiate);
			if(ArrayListUtilZZZ.isEmpty(listae)) break main;
			
			for(IEnumSetMappedStatusLocalZZZ obje : listae) {
				String sName = obje.getName();
				if(sEnumPropertyName.equals(sName)) {
					objReturn = obje;
					break main;
				}
			}
		
		}//end main:
		return objReturn;		
	}

	//####################################################################
	//####################################################################
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Object objForClass)  throws ExceptionZZZ {
		Class<?> cls = objForClass.getClass();
		String sEnumName = sENUMNAME;
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, true, true);
		return enumaReturn;
	}
	 
	//++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, true, true);
		return enumaReturn;
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		E[] enumaReturn = (E[]) searchEnumMappedArray_(cls, sEnumName, bScanInterfaceImmidiate, true);
		return enumaReturn;
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
		return enumaReturn;
	}
	
	//+++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Object objForClass, String sEnumName)  throws ExceptionZZZ {
		Class<?> cls = objForClass.getClass();
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, true, true);
		return enumaReturn;
	}
	 
	//++++++++++++++++++++++++
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, true, true);
		return enumaReturn;
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, bScanInterfaceImmidiate, true);
		return enumaReturn;
	}

	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate)  throws ExceptionZZZ {
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = searchEnumMappedArray_(cls,sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);
		return enumaReturn;
	}
	
	private static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> IEnumSetMappedStatusLocalZZZ[] searchEnumMappedArray_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ{
		IEnumSetMappedStatusLocalZZZ[] enumaReturn = null;
		main:{
			if(classToCheck==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<E> listae = EnumMappedStatusLocalAvailableHelperZZZ.searchEnumList(classToCheck, sEnumName, bScanInterfaceImmidiate, bScanSuperclassImmidiate);					
			//enumaReturn = ArrayListUtilZZZ.toEnumArray(listae);
			enumaReturn = EnumSetMappedStatusLocalUtilZZZ.toEnumMappedArray(listae);
		}//end main:
		return enumaReturn;
	}

	
	//####################################################################
	//####################################################################
	//Aber wir durchsuchen nun einmal beliebige Klassen
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedList(Class<?> cls)  throws ExceptionZZZ {
		String sEnumName = sENUMNAME;
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, true, true);
	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedList(Class<?> cls, String sEnumName)  throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, true, true);
	}

	//public static ArrayList<Collection<? extends Enum<?>>> getStatusLocalEnumListInheritedAvailable(Class cls, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	//public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, true);
	}

	//public static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
	//public static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedList(Class<?> cls, String sEnumName, boolean bScanInterfaceImmidiate, boolean bScanParentClassImmidiate)  throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList_(cls, sEnumName, bScanInterfaceImmidiate, bScanParentClassImmidiate);
	}

	//private static ArrayList<IEnumSetMappedZZZ> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
			//private static <E extends IEnumSetMappedZZZ> ArrayList<E> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
			//Das ? ist unspezifisch, ohne ? funktioniert wenigstens der Compiler, ob es dann auch zur Laufzeit funktioniert????
	private	static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedList_(Class<?> cls, String sEnumName, boolean bScanInterfaceImmediate, boolean bScanSuperclassImmediate)  throws ExceptionZZZ {
				ArrayList<IEnumSetMappedStatusLocalZZZ> listaeReturn = null;
				main:{
				if(cls==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
	//			//1. von der Classe selbst implementiert		
				Enum[] enuma = EnumAvailableHelperZZZ.searchEnumArray(cls,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
				listaeReturn = EnumUtilZZZ.toArrayListMapped(enuma);
				
				//ArrayList<IEnumSetMappedZZZ>listaeByDirect=null;
				ArrayList<IEnumSetMappedStatusLocalZZZ>listaeByDirect=null;
				if(!ArrayUtilZZZ.isNull(enuma)) {	
					//listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					listaeReturn = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
					for(Enum objEnum : enuma) {				
						//IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;				
						E e = (E) objEnum;
						if(!listaeReturn.contains(e)) {
							//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
							listaeReturn.add(e);
						}
					}			
				}
				
						
				//20210404: Die von der Klasse als Interface direkt implementierten reichen nicht.
				//          Es fehlen hier die Interfaces extends Interface, usw.
				
				//2. allen Interfaces der Klasse, auch den "Elterninerfaces"
				//In der scanInterfacesSuper-Methode meiner ReflectClass-Klasse wird folgendes beachtet.https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				//ArrayList<IEnumSetMappedZZZ>listaeByInterface=null;
				ArrayList<IEnumSetMappedStatusLocalZZZ>listaeByInterface=null;
				if(bScanInterfaceImmediate) {
					ArrayList<Class<?>> listaInterfaceSuper=new ArrayList<Class<?>>();
					ReflectClassZZZ.scanInterfacesSuper(cls, listaInterfaceSuper);
					for(Class<?> objclsByInterface : listaInterfaceSuper) {
								
						Enum[] enumaByInterface = EnumAvailableHelperZZZ.searchEnumArray(objclsByInterface, sEnumName,bScanInterfaceImmediate, false);
						
						//ArrayList<IEnumSetMappedZZZ>listaeByInterfaceTemp=null; 
						ArrayList<IEnumSetMappedStatusLocalZZZ>listaeByInterfaceTemp=null;
						if(!ArrayUtilZZZ.isNull(enumaByInterface)) {			
							//listaeByInterfaceTemp = new ArrayList<IEnumSetMappedZZZ>();
							listaeByInterfaceTemp = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
							for(Enum objEnum : enumaByInterface) {						
								//IEnumSetMappedZZZ e = (IEnumSetMappedZZZ) objEnum;
								E e = (E) objEnum;
								if(!listaeByInterfaceTemp.contains(e)) {
									//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
									listaeByInterfaceTemp.add(e);
								}
							}
						}
						if(!ArrayListUtilZZZ.isEmpty(listaeByInterfaceTemp)) {
							//if(ArrayListUtilZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedZZZ>();				
							if(ArrayListUtilZZZ.isEmpty(listaeByInterface)) listaeByInterface = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
							//listaeByInterface = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.join(listaeByInterface, listaeByInterfaceTemp);
							listaeByInterface = (ArrayList<IEnumSetMappedStatusLocalZZZ>) ArrayListUtilZZZ.join(listaeByInterface, listaeByInterfaceTemp);
						}
					}
					
					
					
				}
				
				//3. Hole die Elternklassen.
				//ArrayList<IEnumSetMappedZZZ>listaeByClassSuper=null;
				ArrayList<IEnumSetMappedStatusLocalZZZ>listaeByClassSuper=null;
				if(bScanSuperclassImmediate) {
					ArrayList<Class<?>> listaClassSuper=ReflectClassZZZ.getSuperClasses(cls);
				
					for(Class<?> objclsSuper : listaClassSuper) {
						//Rekursion durchfuehren !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
						//z.B. ArrayList<Collection<? extends Enum<?>>> listaStatusByClassSuper = new ArrayList<Collection<? extends Enum<?>>>();
						
						//s. ChatGPT vom 20260110
						//ArrayList<IEnumSetMappedZZZ> listaeByClassSuperTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objclsSuper,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
						//ArrayList<E> listaeByClassSuperTemp = EnumAvailableHelperZZZ.searchEnumMappedList(objclsSuper,sEnumName, bScanInterfaceImmediate, bScanSuperclassImmediate);
						ArrayList<IEnumSetMappedStatusLocalZZZ> listaeByClassSuperTemp = searchEnumMappedList(objclsSuper, sEnumName, bScanInterfaceImmediate);
						//listaeByClassSuper = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
						//listaeByClassSuper = (ArrayList<IEnumSetMappedStatusLocalZZZ>) ArrayListUtilZZZ.join(listaeByClassSuper, (ArrayList<E>) listaeByClassSuperTemp);
						listaeByClassSuper = (ArrayList<IEnumSetMappedStatusLocalZZZ>) ArrayListUtilZZZ.join(listaeByClassSuper, listaeByClassSuperTemp);
					}
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByDirect)) {		
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
					listaeReturn.addAll(listaeByDirect);			
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByInterface)) {
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
					listaeReturn.addAll(listaeByInterface);
				}
				
				if(!ArrayListUtilZZZ.isEmpty(listaeByClassSuper)) {
					//if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedZZZ>();
					if(listaeReturn==null) listaeReturn = new ArrayList<IEnumSetMappedStatusLocalZZZ>();
					listaeReturn.addAll(listaeByClassSuper);
				}
				
				//listaeReturn = (ArrayList<IEnumSetMappedZZZ>) ArrayListUtilZZZ.unique(listaeReturn);
				listaeReturn = (ArrayList<IEnumSetMappedStatusLocalZZZ>) ArrayListUtilZZZ.unique(listaeReturn);
			}//end main:
			return listaeReturn;
			}

			public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedListDirect(Class<E> cls, String sEnumName)  throws ExceptionZZZ {
				return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedListDirect_(cls, sEnumName, true);
			}

			public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedStatusListDirect(Class<E> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {
				return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedListDirect_(cls, sEnumName, bScanInterfaceImmidiate);
			}

			static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<IEnumSetMappedStatusLocalZZZ> searchEnumMappedListDirect_(Class<E> cls, String sEnumName, boolean bScanInterfaceImmidiate)  throws ExceptionZZZ {		
				ArrayList<IEnumSetMappedStatusLocalZZZ> listaeReturn = searchEnumMappedList(cls,sEnumName,bScanInterfaceImmidiate, false); 		
				return listaeReturn;
			}

			/** Rueckgabewert ist eine HashMap in Form <"String des EnumNamens", IEnmSetMappdedStatusZZZ>. 
			 * @param classToCheck
			 * @throws ExceptionZZZ
			 * @author Fritz Lindhauer, 10.03.2024, 17:38:09
			 * @param E 
			 */
			//public static HashMap<String,Collection<? extends Enum<?>>> getHashMapEnumStatusLocal(Class<?> classToCheck) throws ExceptionZZZ {
			//public static HashMap<String,Collection<? extends IEnumSetMappedZZZ>> getHashMapEnumStatusLocal(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {
			//Das ? ist unspezifisch, ohne ? funktioniert wenigstens der Compiler, ob es dann auch zur Laufzeit funktioniert????
			public static HashMap<String, IEnumSetMappedStatusLocalZZZ> searchHashMapEnumMapped(Class classToCheck, String sEnumName, boolean bScanInterfaceImmidiate) throws ExceptionZZZ {		
				HashMap<String, IEnumSetMappedStatusLocalZZZ> hmReturn = null;
				main:{
					
		//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110
//						IEnumSetMappedZZZ[] enumaMappedStatus= EnumAvailableHelperZZZ.searchEnumMapped(classToCheck, sEnumName, bScanInterfaceImmidiate);
//						if(enumaMappedStatus==null) break main;
					
					//s. ChatGPT vom 20260110
					//ArrayList<IEnumSetMappedZZZ>listaEnumaMappedStatus= EnumAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName, bScanInterfaceImmidiate);
					List<IEnumSetMappedStatusLocalZZZ> listaEnumaMappedStatus = EnumMappedStatusLocalAvailableHelperZZZ.searchEnumMappedList(classToCheck, sEnumName, bScanInterfaceImmidiate);
					
					if(listaEnumaMappedStatus==null) break main;

					hmReturn = new HashMap<String, IEnumSetMappedStatusLocalZZZ>();			
					for(IEnumSetMappedStatusLocalZZZ objEnumMapped : listaEnumaMappedStatus) {
						String sEnum = objEnumMapped.getName();
						hmReturn.put(sEnum, objEnumMapped);
					}		
				}//end main:
				return hmReturn;
			}
			
			
	//########################################
	//########################################
	public static <E extends Enum & IEnumSetMappedStatusLocalZZZ> ArrayList<E> searchEnumList(Object objForClass, String sEnumName) throws ExceptionZZZ {
		Class<?> classToCheck = objForClass.getClass();
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,true,true);
	}
	
	public static <E extends Enum & IEnumSetMappedStatusLocalZZZ> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,true,true);
	}
	
	public static <E extends Enum & IEnumSetMappedStatusLocalZZZ> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, true);
	}
	
	public static <E extends Enum & IEnumSetMappedStatusLocalZZZ> ArrayList<E> searchEnumList(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImmidiate) throws ExceptionZZZ {
		return EnumMappedStatusLocalAvailableHelperZZZ.searchEnumList_(classToCheck,sEnumName,bScanInterfaceImidiate, bScanSuperclassImmidiate);
	}
	
	private static <E extends Enum & IEnumSetMappedStatusLocalZZZ> ArrayList<E> searchEnumList_(Class<?> classToCheck, String sEnumName, boolean bScanInterfaceImidiate, boolean bScanSuperclassImidiate) throws ExceptionZZZ {
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
