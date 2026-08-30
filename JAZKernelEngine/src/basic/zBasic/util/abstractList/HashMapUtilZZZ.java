package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class HashMapUtilZZZ extends MapUtilZZZ {
	
	//Private Konstruktor, zum Verbergen, damit die Klasse nicht instanziiert werden kann.
	//Ist hier protected, damit HashMapUtilZZZ erben kann, wg. Fehlermeldung:
	//"Implicit super constructor MapUtilZZZ<K,V>() is not visible for default constructor. Must define an explicit constructor"
	//Zudem: <K,V> nicht auf Klassenebene definieren, sondern damit das für static Methoden möglich ist, nur auf Methodenebene. 
	//       oder <?,?> verwenden.		
	protected HashMapUtilZZZ() {}
	
	public static boolean isEmpty(HashMap<?,?> hm) {
		boolean bReturn = false;
		main:{
			if(hm==null) {
				bReturn = true;
				break main;
			}
			if(hm.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	
	
	//### fuer IOutputNormedZZ	
	
	//#############################################################################
	//### DEBUG HASHMAP-MULTI
	//#############################################################################	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */				
	@SuppressWarnings("rawtypes")
	public static String computeDebugString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ {
	    String sReturn = "";
	    main: {
	        // HashMapOuter durchgehen
	        if (hmDebug == null) break main;
	        if (hmDebug.size() == 0) break main;

	        String sEntryDelimiter;
	        if (sEntryDelimiterIn == null) {
	            sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
	        } else {
	            sEntryDelimiter = sEntryDelimiterIn;
	        }

	        String sKeyDelimiter;
	        if (sKeyDelimiterIn == null) {
	            sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
	        } else {
	            sKeyDelimiter = sKeyDelimiterIn;
	        }

	        Set entrySetOuter = hmDebug.entrySet();
	        Iterator itOuter = entrySetOuter.iterator();
	        while (itOuter.hasNext()) {
	            if (!StringZZZ.isEmpty(sReturn)) {
	                sReturn = sReturn + sEntryDelimiter;
	            }

	            Map.Entry entryOuter = (Map.Entry) itOuter.next();
	            Object objOuterKey = entryOuter.getKey();
	            Object objOuterValue = entryOuter.getValue();

	            String sKeyOuter = String.valueOf(objOuterKey);
	            HashMap hmInner = (HashMap) objOuterValue;

	            // 20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
	            String stemp = HashMapZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
	            if (stemp != null) {
	                String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
	                String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter + sKeyDelimiter, "BEFORE");
	                sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey, sEntryDelimiter);
	            } else {
	                sReturn = sReturn + sKeyOuter;
	            }
	        } // end while itOuter.hasNext()
	    } // end main
	    return sReturn;
	}
	
	
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 * @param <T>
	 * @param <X>
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */				
	@SuppressWarnings("rawtypes")
	public static String computeImplodeString(HashMapMultiZZZ hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ {
	    String sReturn = "";
	    main: {
	        // HashMapOuter durchgehen
	        if (hmDebug == null) break main;
	        if (hmDebug.size() == 0) break main;

	        String sEntryDelimiter;
	        if (sEntryDelimiterIn == null) {
	            sEntryDelimiter = IHashMapZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT;
	        } else {
	            sEntryDelimiter = sEntryDelimiterIn;
	        }

	        String sKeyDelimiter;
	        if (sKeyDelimiterIn == null) {
	            sKeyDelimiter = IHashMapZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT;
	        } else {
	            sKeyDelimiter = sKeyDelimiterIn;
	        }

	        Set entrySetOuter = hmDebug.entrySet();
	        Iterator itOuter = entrySetOuter.iterator();
	        while (itOuter.hasNext()) {
	            if (!StringZZZ.isEmpty(sReturn)) {
	                sReturn = sReturn + sEntryDelimiter;
	            }

	            Map.Entry entryOuter = (Map.Entry) itOuter.next();
	            Object objOuterKey = entryOuter.getKey();
	            Object objOuterValue = entryOuter.getValue();

	            String sKeyOuter = String.valueOf(objOuterKey);
	            HashMap hmInner = (HashMap) objOuterValue;

	            // 20190801: HIER DEBUG FUNKTIONALITÄT VON HashMapExtendedZZZ verwenden.
	            String stemp = HashMapZZZ.computeDebugString(hmInner, sKeyDelimiter, sEntryDelimiter);
	            if (stemp != null) {
	                String[] saValue = StringZZZ.explode(stemp, sEntryDelimiter);
	                String[] saValueWithKey = StringArrayZZZ.plusString(saValue, sKeyOuter + sKeyDelimiter, "BEFORE");
	                sReturn = sReturn + StringArrayZZZ.implode(saValueWithKey, sEntryDelimiter);
	            } else {
	                sReturn = sReturn + sKeyOuter;
	            }
	        } // end while itOuter.hasNext()
	    } // end main
	    return sReturn;
	}
	
	//#############################################################################
	//### DEBUG
	//#############################################################################	
	//================== PUBLIC API: HashMap ==================
	public static String computeDebugString(HashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, null, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeDebugString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, null, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeDebugString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal__(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeDebugStringInternal__(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	  
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapZZZ.sDEBUG_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
	    return sReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static <K,V> String computeDebugString4Arrays(LinkedHashMap<K,V[]> hmImplode) throws ExceptionZZZ {
		return computeDebugStringInternal4Arrays__(hmImplode, null, null);
	}

	public static <K,V> String computeDebugString4Arrays(LinkedHashMap<K,V[]> hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal4Arrays__(hmImplode, sEntryDelimiterIn, null);
	}

	public static <K,V> String computeDebugString4Arrays(LinkedHashMap<K,V[]> hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeDebugStringInternal4Arrays__(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}

	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <K,V> String computeDebugStringInternal4Arrays__(Map<K,V[]> hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	  
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
	                ? IHashMapZZZ.sDEBUG_KEY_DELIMITER_DEFAULT 
	                : sKeyDelimiterIn;

	        String sEntryDelimiter = (sEntryDelimiterIn == null) 
	                ? IHashMapZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT 
	                : sEntryDelimiterIn;
	        
	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();

	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            //String sKeyPart = String.valueOf(objKey) + sKeyDelimiter;
	            //String sIndent = createWhitespace__(sKeyPart.length()) + sKeyDelimiter;
	            String sKeyPart = String.valueOf(objKey) + sEntryDelimiter + sKeyDelimiter; //Baumhierarchie erstellen, kürzere Zeilen, dafür mehr....
	            String sIndent = sKeyDelimiter; 

	            // ================= ARRAY HANDLING =================
	            if (objValue != null && objValue.getClass().isArray()) {

	                int length = java.lang.reflect.Array.getLength(objValue);

	                for (int i = 0; i < length; i++) {
	                    Object element = java.lang.reflect.Array.get(objValue, i);
	                    String sLine;

	                    if (i == 0) {
	                        sLine = sKeyPart + String.valueOf(element);
	                    } else {
	                        sLine = sIndent + String.valueOf(element);
	                    }

	                    if (StringZZZ.isEmpty(sReturn)) {
	                        sReturn = sLine;
	                    } else {
	                        sReturn = sReturn + sEntryDelimiter + sLine;
	                    }
	                }

	            } else {
	                // ================= NORMAL VALUE =================
	                String sPair = sKeyPart + String.valueOf(objValue);

	                if (StringZZZ.isEmpty(sReturn)) {
	                    sReturn = sPair;
	                } else {
	                    sReturn = sReturn + sEntryDelimiter + sPair;
	                }
	            }
	        }
	    }//end main:
	    return sReturn;
	}
	
	private static String createWhitespace__(int length) {
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        sb.append(' ');
	    }
	    return sb.toString();
	}
	
	//#############################################################################	
    //### IMPLODE
	//#############################################################################
	//================== PUBLIC API: HashMap ==================
	public static String computeImplodeString(HashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, null, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	
	//================== PUBLIC API: LinkedHashMap ==================
	//Merke: Linked HashMap soll die Reihenfolge erhalten
	public static String computeImplodeString(LinkedHashMap hmImplode) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, null, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, sEntryDelimiterIn, null);
	}

	public static String computeImplodeString(LinkedHashMap hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {
		return computeImplodeStringInternal__(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	
	//================== PRIVATE GENERIC IMPLEMENTATION ==================
	@SuppressWarnings("rawtypes")
	private static String computeImplodeStringInternal__(Map hmImplode, String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ {	    
	    String sReturn = null;
	    main: {
	        if (hmImplode == null || hmImplode.size() == 0) break main;

	        String sKeyDelimiter = (sKeyDelimiterIn == null) 
		    		? IHashMapZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT 
		    		: sKeyDelimiterIn;

		    String sEntryDelimiter = (sEntryDelimiterIn == null) 
		    		? IHashMapZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT 
		    		: sEntryDelimiterIn;

	        Set setEntry = hmImplode.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator it = setEntry.iterator();
	        while (it.hasNext()) {
	            Map.Entry entry = (Map.Entry) it.next();   // raw cast
	            Object objKey = entry.getKey();
	            Object objValue = entry.getValue();

	            String sPair = String.valueOf(objKey) + sKeyDelimiter + String.valueOf(objValue);

	            if (StringZZZ.isEmpty(sReturn)) {
	                sReturn = sPair;
	            } else {
	                sReturn = sReturn + sEntryDelimiter + sPair;
	            }
	        }
	    }
	    return sReturn;
	}
	
	
	//#############################################################################
	public static String computeAsHashMapEntry(String sKey, String sValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKey)) break main;
			
			sReturn = "{"+sKey+"="+sValue + "}";
		}//end main:
		return sReturn;
	}

	/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
	 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
	 */
	public static HashMap<String,Boolean> copy(HashMap<String,Boolean> hmOriginal) throws ExceptionZZZ {
	    HashMap<String,Boolean> copy = new HashMap<String, Boolean>();
	    for (Map.Entry<String, Boolean> entry : hmOriginal.entrySet())
	    {
	        copy.put((entry).getKey(), entry.getValue());
	    }
	    return copy;
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
		 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
		 */
	//	public static HashMap<String,MySpecialClass> copy(HashMap<String,MySpecialClass> hmOriginal){
	//	    HashMap<String,MySpecialClass> copy = new HashMap<String, MySpecialClass>();
	//	    for (Map.Entry<String, MySpecialClass> entry : hmOriginal.entrySet())
	//	    {
	//	        copy.put((entry).getKey(), entry.getValue()));
	//	    }
	//	    return copy;
	//	}
		
		
		
	//	/** Erstelle eine echte Kopie der HashMap und nicht nur einen Clone, bei dem die Referencen gleich bleiben.
	//	 *  siehe: https://stackoverflow.com/questions/28288546/how-to-copy-hashmap-not-shallow-copy-in-java
	//	 */
		
		//TODOGOON: Das ist nicht im Einsatz, testen. 
		public static HashMap<Integer, List<?>> copyWithList(HashMap<Integer, List<?>> listOriginal) throws ExceptionZZZ {
		    HashMap<Integer, List<?>> copy = new HashMap<Integer, List<?>>();
		    for (Map.Entry<Integer, List<?>> entry : listOriginal.entrySet())
		    {
		        copy.put(entry.getKey(),
		           // Or whatever List implementation you'd like here.
		           new ArrayList<Object>(entry.getValue()));
		    }
		    return copy;
		}
		
		//##############################################################################
		public static <K,V> HashMap<K,V> fromMap(Map<K,V> mapOriginal) throws ExceptionZZZ {
			HashMap<K,V> hmReturn = null;
			main:{
				if(mapOriginal==null) break main;
				
				hmReturn = new HashMap<K,V>();
				 for (Map.Entry<K, V> entry : mapOriginal.entrySet()){
				        hmReturn.put(entry.getKey(), entry.getValue());
				 }				
			}//end main:
			return hmReturn;
		}
		
		//##############################################################################

		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 * @param <K>
		 * @param <V>
		 */
		public static <K, V> Object getEntryByIndex(Map<K, V> map, int iIndex) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				if(iIndex < 0)break main;
				
				Set<Map.Entry<K, V>> setEntry = map.entrySet();
			    ////Nein, das wuerde eben StringKey=StringWert zurueckgeben   objReturn = SetZZZ.getByIndex(setEntry, iIndex);
				//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
				int iCount = 0;			
				for(Map.Entry<K, V> entry : setEntry) {
					if(iCount == iIndex) {
						objReturn = entry.getValue();
						break;
					}
				}
				
			}
			return objReturn;
		}

		//+++++++++++++++++++++++++++++++++++++++++++
		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 * @param <K>
		 * @param <V>
		 */
		public static <K,V> Object getEntryFirst(Map<K,V> map) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				
				Set<Map.Entry<K, V>> setEntry = map.entrySet();
				////Nein, das wuerde eben StringKey=StringWert zurueckgeben objReturn = SetZZZ.getLast(setEntry); objReturn = SetZZZ.getFirst(setEntry);
				//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
				for(Map.Entry<K, V> entry : setEntry) {
					objReturn = entry.getKey();
				}
		
				
			}
			return objReturn;
		}

		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 * @param <K>
		 * @param <V>
		 */
		public static <K, V> Object getEntryLast(Map<K,V> map) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				
				Set<Map.Entry<K, V>> setEntry = map.entrySet();//!!! Also z.B. StringKey=StringWert
				//Nein, das wuerde eben StringKey=StringWert zurueckgeben objReturn = SetZZZ.getLast(setEntry);
				//Statt dessen: (siehe: https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
				for(Map.Entry<K, V> entry : setEntry) {
					objReturn = entry.getValue();
				}
			}
			return objReturn;
		}

		//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 */
		public static Object getKeyByIndex(Map<?,?> map, int iIndex) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				
				Set<?> setKey = map.keySet();
				objReturn = SetUtilZZZ.getByIndex(setKey, iIndex);
			}
			return objReturn;
		}

		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 */
		public static Object getKeyFirst(Map<?,?> map) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				
				Set<?> setKey = map.keySet();
				objReturn = SetUtilZZZ.getFirst(setKey);
			}
			return objReturn;
		}

		/** Gehe einfach das KeySet durch und gib den ersten Eintrag zurueck.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 10.05.2024, 09:15:46
		 */
		public static Object getKeyLast(Map<?,?> map) throws ExceptionZZZ {
			Object objReturn = null;
			main:{					
				if(map==null)break main;
				
				Set<?> setKey = map.keySet();
				objReturn = SetUtilZZZ.getLast(setKey);
			}
			return objReturn;
		}
		
		
		//####################################
		public static <K, V> HashMap<K, V> mergeMaps(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {			  
		   return HashMapUtilZZZ.mergeMaps_LastKeyRemains(map1,map2);
		}
		
		public static <K, V> HashMap<K, V> mergeMaps_LastKeyRemains(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {
		    Map<K, V> mapTemp = MapUtilZZZ.mergeMaps_LastKeyRemains(map1,map2);
		    return MapUtilZZZ.toHashMap(mapTemp);
		}
		
		//####################################
		public static <K, V> HashMapZZZ<K, V> mergeMapsZZZ(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {			  
		   return HashMapUtilZZZ.mergeMapsZZZ_LastKeyRemains(map1,map2);
		}
		
		public static <K, V> HashMapZZZ<K, V> mergeMapsZZZ_LastKeyRemains(Map<K, V> map1, Map<K, V> map2) throws ExceptionZZZ {
		    Map<K, V> mapTemp = MapUtilZZZ.mergeMaps_LastKeyRemains(map1,map2);
		    return MapUtilZZZ.toHashMapZZZ(mapTemp);
		}

		
		//####################################

		/**
		 * Merke: Eine normale HashMap ist NIE sortierbar.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 26.02.2020, 17:50:30
		 * @throws ExceptionZZZ 
		 */
		public static HashMapIterableKeyZZZ<String, Object> sortByKeyAsInteger_StringObject(Map<String,Object> map, int iSortDirection) throws ExceptionZZZ{
			HashMapIterableKeyZZZ<String, Object>hmReturn=null;
			main:{					
				if(map==null)break main;
				
				//1. Hole das KeySet, als Liste sortiert
				hmReturn = new HashMapIterableKeyZZZ<String,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
				
				Set<String> setStrToBeSorted = map.keySet();			
				if(setStrToBeSorted.size()==0) break main;
							
				List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setStrToBeSorted, iSortDirection);
				
				//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
				for(Integer intSorted : listIntSorted) {
					String sKey = intSorted.toString();
					Object objValue = map.get(sKey);
					hmReturn.put(sKey, objValue);				
				}						
			}//end main:
			return hmReturn;
		}

		/**
		 * Merke: Eine normale HashMap ist NIE sortierbar.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 26.02.2020, 17:50:30
		 * @throws ExceptionZZZ 
		 */
		public static HashMapIterableKeyZZZ<String, Object> sortByKeyAsInteger_StringString(Map<String,String> map) throws ExceptionZZZ{
			HashMapIterableKeyZZZ<String, Object>hmReturn=null;
			main:{					
				if(map==null)break main;
				
				//1. Hole das KeySet, als Liste sortiert
				hmReturn = new HashMapIterableKeyZZZ<String,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
				
				Set<String> setStrToBeSorted = map.keySet();			
				if(setStrToBeSorted.size()==0) break main;
				
				List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setStrToBeSorted);
				
				//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
				for(Integer intSorted : listIntSorted) {
					String sKey = intSorted.toString();
					String sValue = (String) map.get(sKey);
					hmReturn.put(sKey, sValue);				
				}						
			}//end main:
			return hmReturn;
		}

		/**
		 * Merke: Eine normale HashMap ist NIE sortierbar.
		 * @param map
		 * @return
		 * @author Fritz Lindhauer, 26.02.2020, 17:49:20
		 * @throws ExceptionZZZ 
		 */
		public static HashMapIterableKeyZZZ<Integer,Object> sortByKeyInteger(Map<Integer,Object> map) throws ExceptionZZZ {
			HashMapIterableKeyZZZ<Integer,Object>hmReturn=null;
			main:{
				if(map==null)break main;
				
				//1. Hole das KeySet, als Liste sortiert
				hmReturn = new HashMapIterableKeyZZZ<Integer,Object>();//new HashMap<String, ?>() funktioniert dagegen nicht. ? ist zu unspezifisch;
				
				Set<Integer> setIntToBeSorted = map.keySet();			
				if(setIntToBeSorted.size()==0) break main;
				
				List<Integer> listIntSorted = SetUtilZZZ.sortToInteger(setIntToBeSorted);
						
				//2. Gehe die sortierte Liste durch, hole den Wert und füge alles der neuen Hashmap hinzu.
				for(Integer intSorted : listIntSorted) {
					Object objValue = map.get(intSorted);
					hmReturn.put(intSorted, objValue);				
				}						
			}//end main:
			return hmReturn;
		}

		/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
		 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
		* @param map
		* @return
		* 
		* lindhaueradmin; 22.05.2011 08:54:37
		 */
		public static void sortByKeyInteger_usingInnerComparator(Map<Integer,?> map) throws ExceptionZZZ {
			
		     List<Integer> list = new LinkedList(map.keySet());
		     Collections.sort(list, new Comparator() {
		          public int compare(Object o1, Object o2) {
		        	  int iReturn = 0;
		        	  
		        	//  iReturn =( ((Map.Entry)o1).getValue()) .compareTo((Comparable)((Map.Entry)o2).getValue());
		        	Integer int1 = ((Integer) o1);
		        	Integer int2 = ((Integer) o2);
		        	iReturn = int1.compareTo(int2);
		      
		               return iReturn; 
		          }
		     });
		}
}
