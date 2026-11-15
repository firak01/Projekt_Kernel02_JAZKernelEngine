package basic.zKernel.flag;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class FlagZHelperZZZ_BACKUP20240404 implements IConstantZZZ{
	public static final String sENUM_NAME = "FLAGZ";
	
	public static boolean proofFlagZLocalExists(Class<?> cls, String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sFlagName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//NUR die Klasse selbst
//			String[] saFlagAvailable = FlagZHelperZZZ.getFlagsZLocal(cls);
//			if(saFlagAvailable!=null) {
//				if(StringArrayZZZ.contains(saFlagAvailable, sFlagName)) {
//					bReturn = true;	
//					break main;
//				}
//			}
			
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saFlagAvailable = FlagZHelperZZZ_BACKUP20240404.getFlagsZLocalAvailable(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saFlagAvailable, sFlagName)) {
					bReturn = true;	
					break main;
				}
			}												
		}//end main:
		return bReturn;
	}
	
	public static boolean proofFlagZLocalSetBefore(IFlagZCustomEnabledZZZ obj, String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{

				if(obj==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
				if(StringZZZ.isEmpty(sFlagName)) {
					ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
				//Hole die HashMap aller gesetzten Flags
				HashMap<String,Boolean> hmFlag = obj.getHashMapFlagCustom();
				bReturn = hmFlag.containsKey(sFlagName);
		}//end main:
		return bReturn;
	}
	
	public static boolean proofFlagZExists(Class<?> cls, String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sFlagName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//Die Klasse selbst und alle Elternklassen, sowie deren Interface. Achtung, Rekursion wird darin verwendet!
			String[] saFlagAvailable = FlagZHelperZZZ_BACKUP20240404.getFlagsZAvailable(cls);//20210406 das reicht nicht .getFlagsZDirectAvailable(cls);
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.containsIgnoreCase(saFlagAvailable, sFlagName)) {
					bReturn = true;	
					break main;
				}
			}
			
			//Alle Elternklassen, wird nun als Rekursion in FlagZHelperZZZ.getFlagsZInheritedAvailable(cls) gemacht
//			ArrayList<Class<?>> listaClass = ReflectClassZZZ.getSuperClasses(cls);
//			for(Class<?>objClass : listaClass) {
//				String[] saFlagAvailableInherited = FlagZHelperZZZ.getFlagsZInheritedAvailable(objClass); //20210406 das reicht nicht saFlagAvailable = FlagZHelperZZZ.getFlagsZDirectAvailable(objClass);
//				if(saFlagAvailableInherited!=null) {
//					if(StringArrayZZZ.contains(saFlagAvailableInherited, sFlagName)) {
//						bReturn = true;	
//						break main;
//					}
//				}
//			}
			
			
			
/*
			//+++++ ZUM DEBUGGEN / EXPERIMENTIEREN
			//TODOGONN 20200404; //Es fehlen die Elternklassen der Interfaces.
			//ICH SUCHE 
			//interface IPanelCascadedZZZ extends IComponentCascadedUserZZZ
			//beachte https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				
			//Sammler für die Strings
			ArrayList<String> listas = new ArrayList<String>();
			
			Class[] objaclsaByInterface = cls.getInterfaces();
			for(Class objclsByInterface : objaclsaByInterface) {
				Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
				if(enumaByInterface!=null) {			
					for(Enum objEnum : enumaByInterface) {
						String sEnum = objEnum.name();
						listas.add(sEnum);
					}			
				}
				
				//Alle Elternklassen DES INTERFACE
				//Aber beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				ArrayList<Class<?>> listaClassInterfaceInherited = ReflectClassZZZ.getSuperClasses(objclsByInterface);
				for(Class<?>objClassInterfaceInherited : listaClassInterfaceInherited) {
					
					Enum[] enumaByInterfaceInherited = getEnumFlagZ(objClassInterfaceInherited);
					if(enumaByInterfaceInherited!=null) {			
						for(Enum objEnum : enumaByInterfaceInherited) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
						}			
					}
				}
				
				//ALLE INTERFACES DES INTERFACE
				//Beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
				ArrayList<Class<?>> listaClassInterfaceInterface = ReflectClassZZZ.getInterfaces(objclsByInterface);
				for(Class<?>objClassInterfaceInterface : listaClassInterfaceInterface) {
					
					Enum[] enumaByInterfaceInterface = getEnumFlagZ(objClassInterfaceInterface);
					if(enumaByInterfaceInterface!=null) {			
						for(Enum objEnum : enumaByInterfaceInterface) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
							System.out.println("1. " + sEnum);
						}			
					}
				}
				
			}
			
			
			//Hole die Elternklassen 
			ArrayList<Class<?>> listaClass2 = ReflectClassZZZ.getSuperClasses(cls);
			for(Class<?>objClass : listaClass2) {
				
				
				Class[] objaclsaByInterface2 = objClass.getInterfaces();
				for(Class objclsByInterface : objaclsaByInterface2) {
					Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
					if(enumaByInterface!=null) {			
						for(Enum objEnum : enumaByInterface) {
							String sEnum = objEnum.name();
							listas.add(sEnum);
							System.out.println("2. " + sEnum);
						}			
					}
					
					//Alle Elternklassen DES INTERFACE
					ArrayList<Class<?>> listaClassInterfaceInherited = ReflectClassZZZ.getSuperClasses(objclsByInterface);
					for(Class<?>objClassInterfaceInherited : listaClassInterfaceInherited) {
						
						Enum[] enumaByInterfaceInherited = getEnumFlagZ(objClassInterfaceInherited);
						if(enumaByInterfaceInherited!=null) {			
							for(Enum objEnum : enumaByInterfaceInherited) {
								String sEnum = objEnum.name();
								listas.add(sEnum);
								System.out.println("3. " + sEnum);
							}			
						}
					}
					
					//ALLE INTERFACES DES INTERFACE
					//Beachte: https://stackoverflow.com/questions/24020413/get-parent-interface-of-interface-in-java
					ArrayList<Class<?>> listaClassInterfaceInterface = ReflectClassZZZ.getInterfaces(objclsByInterface);
					for(Class<?>objClassInterfaceInterface : listaClassInterfaceInterface) {
						
						Enum[] enumaByInterfaceInterface = getEnumFlagZ(objClassInterfaceInterface);
						if(enumaByInterfaceInterface!=null) {			
							for(Enum objEnum : enumaByInterfaceInterface) {
								String sEnum = objEnum.name();
								listas.add(sEnum);
								System.out.println("4. " + sEnum);
							}			
						}
					}
				}
			}
			*/
		}//end main:
		return bReturn;
	}
	
	public static boolean proofFlagZSetBefore(IFlagZEnabledZZZ obj, String sFlagName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{

				if(obj==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
				if(StringZZZ.isEmpty(sFlagName)) {
					ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					 throw ez;
				}
				
				//Hole die HashMap aller gesetzten Flags
				HashMap<String,Boolean> hmFlag = obj.getHashMapFlag();
				bReturn = hmFlag.containsKey(sFlagName);
		}//end main:
		return bReturn;
	}
	
	public static boolean resetFlags(ReferenceHashMapZZZ<String,Boolean>objhmReturnValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			HashMap<String,Boolean> hm = objhmReturnValue.get();
			if(hm.isEmpty()) break main;
			
			Set<String> setKey = hm.keySet();
			Iterator<String> itKey = setKey.iterator();
			while(itKey.hasNext()) {
				String sKey = itKey.next();
				Boolean objValue = hm.get(sKey);
				if(objValue!=null) {
					if(objValue.booleanValue()) {
						hm.put(sKey, false); //also nur die true werte auf false setzen und dann gibt diese Methode auch einen "Aenderungsvermerk" zurueck.
						bReturn = true;
					}
				}				
			}			
		}//end main:
		return bReturn;
	}
	
	public static boolean proofFlagZDirectExists(Class<?> cls, String sFlagName) throws ExceptionZZZ {
		return proofFlagZDirectExists_(cls, sFlagName, false);
	}
	
	public static boolean proofFlagZLocalDirectExists(Class<?> cls, String sFlagName) throws ExceptionZZZ {
		return proofFlagZDirectExists_(cls, sFlagName, true);
	}
	
	private static boolean proofFlagZDirectExists_(Class<?> cls, String sFlagName, boolean bLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			if(StringZZZ.isEmpty(sFlagName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "FlagString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			//NUR die Klasse selbst
			String[] saFlagAvailable = null;
			if(bLocal) {
			    saFlagAvailable = FlagZHelperZZZ_BACKUP20240404.getFlagsZLocalDirectAvailable(cls);
			}else {
				saFlagAvailable = FlagZHelperZZZ_BACKUP20240404.getFlagsZDirectAvailable(cls);
			}
			if(saFlagAvailable!=null) {
				if(StringArrayZZZ.contains(saFlagAvailable, sFlagName)) {
					bReturn = true;	
					break main;
				}
			}						
		}//end main:
		return bReturn;
	}
		
	public static ArrayList<String> getFlagsZListDirectAvailable(Class<?> cls)  throws ExceptionZZZ {
		return getFlagsZListDirectAvailable_(cls, false);
	}
	
	public static ArrayList<String> getFlagsZLocalListDirectAvailable(Class<?> cls)  throws ExceptionZZZ {
		return getFlagsZListDirectAvailable_(cls, true);
	}
	
	private static ArrayList<String> getFlagsZListDirectAvailable_(Class<?> cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		//1. von der Classe selbst implementiert
		Enum[] enuma = null;
		if(bLocal) {
			enuma = getEnumFlagZLocal(cls);
		}else {
			enuma = getEnumFlagZ(cls);
		}
		if(enuma!=null) {			
			for(Enum objEnum : enuma) {
				String sEnum = objEnum.name();
				if(!listasReturn.contains(sEnum)) {
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
					listasReturn.add(sEnum);
				}
			}			
		}
		
		//2. von den Interfaces der Klasse DIREKT implementiert
		Class<?>[] objclsaByInterface = cls.getInterfaces();
		for(Class<?> objclsByInterface : objclsaByInterface) {
			Enum[] enumaByInterface = null;
			if(bLocal) {
				enumaByInterface = getEnumFlagZLocal(objclsByInterface);
			}else {
				enumaByInterface = getEnumFlagZ(objclsByInterface);
			}
			if(enumaByInterface!=null) {			
				for(Enum objEnum : enumaByInterface) {
					String sEnum = objEnum.name();
					if(!listasReturn.contains(sEnum)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listasReturn.add(sEnum);
					}
				}			
			}
		}
		
	}//end main:
	return listasReturn;
	}
	
	
	
	public static ArrayList<String> getFlagsZListAvailable(Class<?> cls)  throws ExceptionZZZ {
		return getFlagsZListAvailable_(cls, false);
	}
	
	public static ArrayList<String> getFlagsZLocalListAvailable(Class<?> cls)  throws ExceptionZZZ {
		return getFlagsZListAvailable_(cls, true);
	}
	
	private static ArrayList<String> getFlagsZListAvailable_(Class<?> cls, boolean bLocal)  throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{			
			//20240403: Ersetze alles durch eine zentralere Enum-Klassen-Utility
			if(bLocal) {
				listasReturn = EnumAvailableHelperZZZ.searchList(cls, "FLAGZLOCAL");
			}else {
				listasReturn = EnumAvailableHelperZZZ.searchList(cls, "FLAGZ");
			}
	}//end main:
	return listasReturn;
	}
	
	public static ArrayList<String> getFlagsZLocalList(Class cls) throws ExceptionZZZ {
		return FlagZHelperZZZ_BACKUP20240404.getFlagsZList(cls,true);
	}
	
	public static ArrayList<String> getFlagsZList(Class cls) throws ExceptionZZZ {
		return FlagZHelperZZZ_BACKUP20240404.getFlagsZList(cls,false);
	}
	
	
	public static ArrayList<String> getFlagsZList(Class cls, boolean bLocal) throws ExceptionZZZ {
		ArrayList<String> listasReturn = new ArrayList<String>();
		main:{			
		//20240403: Ersetze alles durch eine zentralere Enum-Klassen-Utility
			if(!bLocal) {
				listasReturn = EnumAvailableHelperZZZ.searchList(cls, "FLAGZ");
			}else {
				listasReturn = EnumAvailableHelperZZZ.searchList(cls, "FLAGZLOCAL");
			}
			
		//20240403: ### ersetzt durch obiges ###### 
//		if(cls==null) {
//			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
//			 throw ez;
//		}
//	
//		//1. von der Classe selbst implementiert
//		ArrayList<String> listasDirect = FlagZHelperZZZ.getFlagsZListDirectAvailable(cls);
//				
//		//2. allen Interfaces der Klasse, auch den erbenden implementiert
//		ArrayList<String> listasInterface = new ArrayList<String>();
//		ArrayList<Class<?>> listaClassInterface=new ArrayList<Class<?>>();
//		ReflectClassZZZ.scanInterfacesSuper(cls, listaClassInterface);
//		for(Class<?> objclsByInterface : listaClassInterface) {
//			Enum[] enumaByInterface = getEnumFlagZ(objclsByInterface);
//			if(enumaByInterface!=null) {			
//				for(Enum objEnum : enumaByInterface) {
//					String sEnum = objEnum.name();
//					if(!listasInterface.contains(sEnum)) {
//						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
//						listasInterface.add(sEnum);
//					}
//				}			
//			}
//		}
//		
//		//3. von den Elternklassen der Klasse implementiert
//		ArrayList<String> listasParent = new ArrayList<String>();		
//		ArrayList<Class<?>> listaobjClass = ReflectClassZZZ.getSuperClasses(cls);
//		for(Class objcls : listaobjClass) {
//			//Von dem Interface direkt implementiert. Reicht aber nicht um alle zu erfassen.
//			//ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListDirectAvailable(objcls);
//			
//			//Von der Vererbungshierarchie des Interface implementiert.
//			ArrayList<String> listasTemp = FlagZHelperZZZ.getFlagsZListInheritedAvailable(objcls);
//			
//			listasParent.addAll(listasTemp);
//		}
//		
//		listasReturn.addAll(listasDirect);
//		listasReturn.addAll(listasParent);
//		listasReturn.addAll(listasInterface);
//		listasReturn = (ArrayList<String>) ArrayListZZZ.unique(listasReturn);
	}//end main:
	return listasReturn;
	}
	
	public static String[] getFlagsZ(Class cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = getFlagsZList(cls);
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] getFlagsZLocal(Class cls) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}

		ArrayList<String> listas = getFlagsZLocalListAvailable(cls);
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	
	public static String[] getFlagsZDirectAvailable(Class cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = FlagZHelperZZZ_BACKUP20240404.getFlagsZListDirectAvailable(cls);			
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
	
	public static String[] getFlagsZLocalDirectAvailable(Class cls)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
		if(cls==null) {
			 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			 throw ez;
		}
		
		ArrayList<String> listas = FlagZHelperZZZ_BACKUP20240404.getFlagsZLocalListDirectAvailable(cls);			
		saReturn = ArrayListUtilZZZ.toStringArray(listas);
	}//end main:
	return saReturn;
	}
		
		public static String[] getFlagsZAvailable(Class cls)  throws ExceptionZZZ {
			String[] saReturn = null;
			main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<String> listas = FlagZHelperZZZ_BACKUP20240404.getFlagsZListAvailable(cls);					
			saReturn = ArrayListUtilZZZ.toStringArray(listas);
		}//end main:
		return saReturn;
		}
		
		public static String[] getFlagsZLocalAvailable(Class cls)  throws ExceptionZZZ {
			String[] saReturn = null;
			main:{
			if(cls==null) {
				 ExceptionZZZ ez = new ExceptionZZZ( "Class", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				 throw ez;
			}
			
			ArrayList<String> listas = FlagZHelperZZZ_BACKUP20240404.getFlagsZLocalListAvailable(cls);					
			saReturn = ArrayListUtilZZZ.toStringArray(listas);
		}//end main:
		return saReturn;
		}
	
	
	//++++++++++++++++++++++++++++++
	private static <E extends Enum> E[] getEnumFlagZLocal(Class<?> classToCheck) throws ExceptionZZZ {
		return EnumHelperZZZ.getEnumByName(classToCheck, FlagZHelperZZZ_BACKUP20240404.sENUM_NAME+"LOCAL");
	}
	
	private static <E extends Enum> E getEnumFlagZLocal(Class<?> classToCheck, String sEnumProperty) throws ExceptionZZZ {
		return EnumHelperZZZ.getEnumByName(classToCheck, FlagZHelperZZZ_BACKUP20240404.sENUM_NAME+"LOCAL", sEnumProperty);
	}

		
	//++++++++++++++++++++++++++++++
	private static <E extends Enum> E[] getEnumFlagZ(Class<?> classToCheck) throws ExceptionZZZ {
		return EnumHelperZZZ.getEnumByName(classToCheck, FlagZHelperZZZ_BACKUP20240404.sENUM_NAME);
	}
	
	private static <E extends Enum> E getEnumFlagZ(Class<?> classToCheck, String sEnumProperty) throws ExceptionZZZ {
		return EnumHelperZZZ.getEnumByName(classToCheck, FlagZHelperZZZ_BACKUP20240404.sENUM_NAME, sEnumProperty);
	}
	//+++++++++++++++++++++++++++++++++++++++++++
}
