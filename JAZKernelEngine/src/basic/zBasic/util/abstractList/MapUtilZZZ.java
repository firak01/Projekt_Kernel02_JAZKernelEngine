package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;

public class MapUtilZZZ implements IConstantZZZ{
	
	//Private Konstruktor, zum Verbergen, damit die Klasse nicht instanziiert werden kann.
	//Ist hier protected, damit HashMapUtilZZZ erben kann, wg. Fehlermeldung:
	//"Implicit super constructor MapUtilZZZ() is not visible for default constructor. Must define an explicit constructor"
	//Zudem: <K,V> nicht auf Klassenebene definieren, sondern damit das für static Methoden möglich ist, nur auf Methodenebene. 
	//       oder <?,?> verwenden.
	
	protected MapUtilZZZ() {}
	
	public static boolean isEmpty(Map<?,?> m) {
		boolean bReturn = false;
		main:{
			if(m==null) {
				bReturn = true;
				break main;
			}
			if(m.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	
	//###################################################################################################################
	public static <K, V> Map<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {
	   return mergeMaps_LastKeyRemains(map1,map2);
	}
	
	public static <K, V> Map<K, V> mergeMaps_LastKeyRemains(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {
	    HashMap<K, V> result = new HashMap<K, V>();

	    if (map1 != null) {
	        result.putAll(map1);
	    }

	    if (map2 != null) {
	        result.putAll(map2);
	    }

	    return result;
	}
	
	//###################################################################################################################
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesUniqueKeyAcrosswise(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValuesKeyAcrosswise_(null, m1, m2);
	}
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesUniqueKeyAcrosswise(ArrayListZZZ<K> listaK, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValuesKeyAcrosswise_(listaK, m1, m2);
	}
	
	private static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesKeyAcrosswise_(ArrayListZZZ<K> listaKIn, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		LinkedHashMap<K,V[]> mReturn = null;
		main:{
//				if(listaKIn == null) {
//					ExceptionZZZ ez = new ExceptionZZZ("ArrayListZZZ<K>", iERROR_PARAMETER_MISSING, MapUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;				
//				}
			
			ArrayListZZZ<K> listaK = new ArrayListZZZ<K>();
			if(listaKIn==null || listaKIn.size()==0) {//wenn es keine Sortierspalten gibt...
				if(m1==null) {
					mReturn = (LinkedHashMap<K, V[]>) m2;
					break main;
				}
				if(m2==null || m2.isEmpty()) {
					mReturn = (LinkedHashMap<K, V[]>) m1;
					break main;
				}
								
				//1. Baue eine Arraylist aller Schlüssel auf, ohne Duplikate
				listaK = MapUtilZZZ.mergeKeys(m1, m2);
			}else {
				//Auch wenn die Liste selbst nicht aufgebaut, sondern nur uebernommen wird... Duplikate entfernen.
				listaK = ArrayListUtilZZZ.unique(listaKIn);				
			}
			
			Map<K,V[]> mReturnTemp01 = MapUtilZZZ.mergeMapsAndJoinArrayValuesUniqueKeyEachwise(listaK, m1, m2);
			
			
			Map<K,V[]> mReturnTemp02 = MapUtilZZZ.uniqueValuesKeyAcrosswise(mReturnTemp01);
			
			mReturn = MapUtilZZZ.toLinkedHashMap_ArrayValue(mReturnTemp02);
			
		}//end main:
		return mReturn;
	}
	
	//###############################
	public static <K,V> HashMap<K,V> toHashMap(Map<K,V> map){
	    if(map == null) return null;
	    return new HashMap<K,V>(map);
	}
	
	public static <K,V> HashMapZZZ<K,V> toHashMapZZZ(Map<K,V> map){		
		HashMapZZZ<K,V> hmReturn=null;
		main:{
			if(map==null)break main;
			
			hmReturn=new HashMapZZZ<K,V>();
			hmReturn.putAll(map);		
		}//end main:
		return hmReturn;
	}
	
	public static <K,V> HashMap<K,V[]> toHashMap_ArrayValue(Map<K,V[]> map){
	    if(map == null) return null;
	    return new HashMap<K,V[]>(map);
	}
	
	public static <K,V> LinkedHashMap<K,V[]> toLinkedHashMap_ArrayValue(Map<K,V[]> map){
	    if(map == null) return null;
	    return new LinkedHashMap<K,V[]>(map);
	}
	
	
	//###############################
	public static <K,V> Map<K,V[]> uniqueValuesKeyAcrosswise(Map<K,V[]> map) throws ExceptionZZZ{

	    if(map == null) return null;

	    Map<K,V[]> result = new LinkedHashMap<K,V[]>();
	    Set<V> seenValues = new HashSet<V>();

	    for(Map.Entry<K,V[]> entry : map.entrySet()){
	        K key = entry.getKey();
	        V[] values = entry.getValue();

	        if(values == null){
	            result.put(key, null);
	            continue;
	        }

	        List<V> uniqueList = new ArrayList<V>();

	        for(V v : values){
	            if(!seenValues.contains(v)){
	                seenValues.add(v);
	                uniqueList.add(v);
	            }
	        }

	        V[] uniqueArray = Arrays.copyOf(values, uniqueList.size());
	        for(int i = 0; i < uniqueList.size(); i++){
	            uniqueArray[i] = uniqueList.get(i);
	        }

	        result.put(key, uniqueArray);
	    }

	    return result;
	}
	
	//###################################################################################################################
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesUniqueKeyEachwise(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValuesKeyEachwise_(null, m1, m2);
	}
	
	public static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesUniqueKeyEachwise(ArrayListZZZ<K> listaK, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		return MapUtilZZZ.mergeMapsAndJoinArrayValuesKeyEachwise_(listaK, m1, m2);
	}
	
	private static <K,V> Map<K,V[]> mergeMapsAndJoinArrayValuesKeyEachwise_(ArrayListZZZ<K> listaKIn, Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ{
		LinkedHashMap<K,V[]> mReturn = null;
		main:{
//			if(listaKIn == null) {
//				ExceptionZZZ ez = new ExceptionZZZ("ArrayListZZZ<K>", iERROR_PARAMETER_MISSING, MapUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;				
//			}
			
			ArrayListZZZ<K> listaK = new ArrayListZZZ<K>();
			if(listaKIn==null || listaKIn.size()==0) {//wenn es keine Sortierspalten gibt...
				if(m1==null) {
					mReturn = (LinkedHashMap<K, V[]>) m2;
					break main;
				}
				if(m2==null || m2.isEmpty()) {
					mReturn = (LinkedHashMap<K, V[]>) m1;
					break main;
				}
								
				//1. Baue eine Arraylist aller Schlüssel auf, ohne Duplikate
				listaK = MapUtilZZZ.mergeKeys(m1, m2);
			}else {
				//Auch wenn die Liste selbst nicht aufgebaut, sondern nur uebernommen wird... Duplikate entfernen.
				listaK = ArrayListUtilZZZ.unique(listaKIn);				
			}
			
			
			mReturn = (LinkedHashMap<K, V[]>) new LinkedHashMap<K,V[]>();
			
			//2. Gehe alle Keys durch, hole die Werte und joine diese ggfs.
			for(K objKey : listaK) {
				
				//Wert erste Hashmap
				V[] objaValue1 = m1.get(objKey);
				if(objaValue1!=null) {
					
					//pruefe, ob die andere map den gleichen key hat.
					V[] objaValue2 = m2.get(objKey);
					if(objaValue2!=null) {
						
						V[]objaValueReturn = ArrayUtilZZZ.joinUnique(objaValue1, objaValue2);
						mReturn.put(objKey, objaValueReturn);
					}else {
						mReturn.put(objKey, objaValue1);
					}
				}else {
					//pruefe, ob die andere map den gleichen key hat.
					V[] objaValue2 = m2.get(objKey);
					if(objaValue2!=null) {
						mReturn.put(objKey, objaValue2);
					}	//da objaValue1 null ist, eruebrigt sich der Rest				
				}
			}
		}//end main:
		return mReturn;
	}
	
	
	//#################################################
	public static <K,V> ArrayListZZZ<K> mergeKeys(Map<K,V[]> m1) throws ExceptionZZZ {
		return MapUtilZZZ.mergeKeys_(m1, null);
	}
	
	public static <K,V> ArrayListZZZ<K> mergeKeys(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ {
		return MapUtilZZZ.mergeKeys_(m1, m2);
	}
	
	
	private static <K,V> ArrayListZZZ<K> mergeKeys_(Map<K,V[]> m1, Map<K,V[]> m2) throws ExceptionZZZ {
		ArrayListZZZ<K> listaReturn = null;
		main:{
			if(m1==null && m2 ==null) break main;			
			listaReturn = new ArrayListZZZ<K>();
			
			//0. Falls nur 1x HashMap, dann ist das fuer jeden Fall deren KeySet
			if(m1==null) {
				Set<K> set2 = m2.keySet();
				Iterator<K> it2 = set2.iterator();
				while(it2.hasNext()) {
					K objKey2 = it2.next();
					listaReturn.addUnique(objKey2);
				}
				break main;
			}
			if(m2==null || m2.isEmpty()) {
				Set<K> set1 = m1.keySet();
				Iterator<K> it1 = set1.iterator();
				while(it1.hasNext()) {
					K objKey1 = it1.next();
					listaReturn.addUnique(objKey1);
				}
				break main;
			}
						
			
			//1. Baue eine Arraylist aller Schlüssel auf, ohne Duplikate								
			Set<K> set1 = m1.keySet();
			Iterator<K> it1 = set1.iterator();
			while(it1.hasNext()) {
				K objKey1 = it1.next();
				listaReturn.addUnique(objKey1);
			}
			
			Set<K> set2 = m2.keySet();
			Iterator<K> it2 = set2.iterator();
			while(it2.hasNext()) {
				K objKey2 = it2.next();
				listaReturn.addUnique(objKey2);
			}
		}//end main:
		return listaReturn;
	}
}