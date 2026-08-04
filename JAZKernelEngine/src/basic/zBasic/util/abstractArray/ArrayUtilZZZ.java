package basic.zBasic.util.abstractArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import base.util.abstractArray.ArrayUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;

/** Erweitere nach Bedarf:
 *  base.util.abstractArray.ArrayUtil
 *  
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 29.12.2022, 09:25:21
 * 
 */
public class ArrayUtilZZZ<T>{
	protected ArrayUtilZZZ() {}//static methods only
	
	
	// ======================================================
    // Generische contains-Methode
    // ======================================================
    public static <T> boolean contains(T[] array, T element) {
        if (array == null) return false;

        for (T current : array) {
            if (element == null) {
                if (current == null) return true;
            } else {
                if (element.equals(current)) return true;
            }
        }
        return false;
    }


	
	//##################################################################
    // Variante Arraymaessige Betrachtung
    // ======================================================
    // Schnittmenge
    // ======================================================
    public static <T> T[] intersect(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
        T[] returnArray = null;

        main: {
            if (array01 == null) break main;
            if (array02 == null) break main;
            if (array01.length == 0) break main;
            if (array02.length == 0) break main;

            List<T> tempList = new ArrayList<T>();

            for (T element01 : array01) {
                if (contains(array02, element01)) {
                    tempList.add(element01);
                }
            }

            @SuppressWarnings("unchecked")
            T[] result = (T[]) Array.newInstance(clazz, tempList.size());
            returnArray = tempList.toArray(result);
        }

        return returnArray;
    }

    // ======================================================
    // Schnittmenge oder Nicht-Null-Array zurückgeben
    // ======================================================
    public static <T> T[] intersectOrNotNull(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
        T[] returnArray = null;

        main: {
            if (array01 == null && array02 == null) break main;

            if (array01 == null) {
                if (array02.length == 0) break main;
                returnArray = array02;
                break main;
            }

            if (array02 == null) {
                if (array01.length == 0) break main;
                returnArray = array01;
                break main;
            }

            returnArray = intersect(array01, array02, clazz);
        }

        return returnArray;
    }
    
	 // ======================================================
	 // Differnzmenge (array01 ohne Elemente aus array02)
	 // ======================================================
	 /**Z.B.
	  * 
	  * String[] a1 = {"A", "B", "C", "D"};
		String[] a2 = {"B", "D"};

		String[] result = ArrayUtilZZZ.subset(a1, a2, String.class);

		Ergebnis {"A", "C"}
		
		
	 * @param array01
	 * @param array02
	 * @param clazz
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.02.2026, 17:28:48
	 */
	public static <T> T[] difference(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
	     T[] returnArray = null;
	
	     main: {
	         if (array01 == null) break main;
	         if (array01.length == 0) break main;
	
	         // Wenn array02 null oder leer → komplette array01 ist Teilmenge
	         if (array02 == null || array02.length == 0) {
	             returnArray = array01;
	             break main;
	         }
	
	         List<T> tempList = new ArrayList<T>();
	
	         for (T element01 : array01) {
	             if (!contains(array02, element01)) {
	                 tempList.add(element01);
	             }
	         }
	
	         @SuppressWarnings("unchecked")
	         T[] result = (T[]) Array.newInstance(clazz, tempList.size());
	         returnArray = tempList.toArray(result);
	     }
	
	     return returnArray;
	 }

	//############################################################################
	//### Variante Mengenmaessige Betrachtung
	//### Falls Reihenfolg nicht wichtig waere: Set<T> resultSet = new HashSet<T>();
	//### Da in einer Array-Sortierung ggfs. die Reihenfolge wichtig ist:  Set<T> resultSet = new LinkedHashSet<T>();
	// ======================================================
	// Mathematische Schnittmenge (ohne Duplikate)
	// ======================================================
	public static <T> T[] intersectSet(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
	    if (array01 == null || array02 == null) {
	        return null;
	    }

	    Set<T> set1 = new LinkedHashSet<T>();
	    Set<T> resultSet = new LinkedHashSet<T>();

	    for (T element : array01) {
	        set1.add(element);
	    }

	    for (T element : array02) {
	        if (set1.contains(element)) {
	            resultSet.add(element);
	        }
	    }

	    @SuppressWarnings("unchecked")
	    T[] result = (T[]) Array.newInstance(clazz, resultSet.size());
	    return resultSet.toArray(result);
	}
	
	
	// ======================================================
	// Mathematische Differenzmenge (ohne Duplikate)
	// ======================================================
	public static <T> T[] differenceSet(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
	    if (array01 == null) {
	        return null;
	    }

	    Set<T> resultSet = new LinkedHashSet<T>();
	    Set<T> set2 = new LinkedHashSet<T>();

	    if (array02 != null) {
	        for (T element : array02) {
	            set2.add(element);
	        }
	    }

	    for (T element : array01) {
	        if (!set2.contains(element)) {
	            resultSet.add(element);
	        }
	    }

	    @SuppressWarnings("unchecked")
	    T[] result = (T[]) Array.newInstance(clazz, resultSet.size());
	    return resultSet.toArray(result);
	}
	
	
	// ======================================================
	// Mathematische Vereinigungsmenge (ohne Duplikate)
	// ======================================================
	public static <T> T[] unionSet(T[] array01, T[] array02, Class<T> clazz) throws ExceptionZZZ {
	    if (array01 == null && array02 == null) {
	        return null;
	    }

	    Set<T> resultSet = new LinkedHashSet<T>();

	    if (array01 != null) {
	        for (T element : array01) {
	            resultSet.add(element);
	        }
	    }

	    if (array02 != null) {
	        for (T element : array02) {
	            resultSet.add(element);
	        }
	    }

	    @SuppressWarnings("unchecked")
	    T[] result = (T[]) Array.newInstance(clazz, resultSet.size());
	    return resultSet.toArray(result);
	}
	
 	
	//############################################################################
	//############################################################################
	public static <T> boolean isEmpty(T[] objArray) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(objArray);
			if(bReturn) break main;
			
			for(int i = 0; i<=objArray.length-1;i++) {
				if(objArray[i]!=null) {
					break main;					
				}
				
				//FGL: 20241227 - Das muss noch mit ... instanceof ... Fallunterscheidung erweitert werden
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	public static boolean isEmpty(boolean[] ba) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ba);
			if(bReturn) break main;
			
			for(int i = 0; i<=ba.length-1;i++) {
				if(Boolean.valueOf(ba[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isEmpty(int[] ia) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ia);
			if(bReturn) break main;
			
			for(int i = 0; i<=ia.length-1;i++) {
				if(Integer.valueOf(ia[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	public static boolean isEmpty(char[] ca) {
		boolean bReturn = false;
		main:{
			bReturn = ArrayUtilZZZ.isNull(ca);
			if(bReturn) break main;
			
			for(int i = 0; i<=ca.length-1;i++) {
				if(Character.valueOf(ca[i])!=null) {
					break main;					
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static <T> boolean isNull(T[] objArray) {
		boolean bReturn = false;
		main:{
			if(objArray == null) {
				bReturn = true;
				break main;
			}
			
			if(objArray.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	public static boolean isNull(boolean[] ba) {
		boolean bReturn = false;
		main:{
			if(ba == null) {
				bReturn = true;
				break main;
			}
			
			if(ba.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static boolean isNull(int[] ia) {
		boolean bReturn = false;
		main:{
			if(ia == null) {
				bReturn = true;
				break main;
			}
			
			if(ia.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	public static boolean isNull(char[] ca) {
		boolean bReturn = false;
		main:{
			if(ca == null) {
				bReturn = true;
				break main;
			}
			
			if(ca.length==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	
	public static boolean isNullOrEmpty(int[] theArray) {
	    return theArray == null || theArray.length == 0;
	}
	
	public static <T> boolean isNullOrEmpty(T[] theArray) {
	    return theArray == null || theArray.length == 0;
	}
	
	public static <T> T[] join(T[] objArray1,T[] objArray2) {
		T[] objaReturn=null;
		main:{
			boolean bEmptyArray1 = ArrayUtilZZZ.isNull(objArray1);
			boolean bEmptyArray2 = ArrayUtilZZZ.isNull(objArray2);
			if( bEmptyArray1 && bEmptyArray2) break main;
			
			if( bEmptyArray1 && !bEmptyArray2){
				objaReturn = objArray2;
				break main;
			}
			
			if( !bEmptyArray1 && bEmptyArray2){
				objaReturn = objArray1;
				break main;
			}
			
			//Aus Apache commons
			objaReturn = (T[]) ArrayUtils.addAll(objArray1, objArray2);			
		}//end main:
		return objaReturn;
	}
	
	
	public static <T> T[] joinUnique(T[] objArray1,T[] objArray2) {
		T[] objaReturn=null;
		main:{
			boolean bEmptyArray1 = ArrayUtilZZZ.isNull(objArray1);
			boolean bEmptyArray2 = ArrayUtilZZZ.isNull(objArray2);
			if( bEmptyArray1 && bEmptyArray2) break main;
			
			if( bEmptyArray1 && !bEmptyArray2){
				objaReturn = objArray2;
				break main;
			}
			
			if( !bEmptyArray1 && bEmptyArray2){
				objaReturn = objArray1;
				break main;
			}
						
			objaReturn = ArrayUtilZZZ.join(objArray1, objArray2);
			objaReturn = ArrayUniqueUtilZZZ.toUniqueArray(objaReturn);
		}//end main:
		return objaReturn;
	}
	
	
	
	 /**20251206 - Von ChatGPT inspiriert
     * Hängt zwei Arrays aneinander, wobei bei objArray2 erst ab indexObjArray2 kopiert wird.
     *
     * @param <T> der Elementtyp
     * @param objArray1 erstes Array
     * @param objArray2 zweites Array
     * @param indexObjArray2 Startindex im zweiten Array
     * @return neues Array mit den kombinierten Werten
     * @throws IllegalArgumentException wenn indexObjArray2 außerhalb des gültigen Bereichs liegt
     */
    public static <T> T[] join(T[] objArray1, T[] objArray2, int indexObjArray2) {

        if (objArray1 == null && objArray2 == null) {
            return null;
        }
        if (objArray2 != null) {
            if (indexObjArray2 < 0 || indexObjArray2 > objArray2.length) {
                throw new IllegalArgumentException("indexObjArray2 out of range: " + indexObjArray2);
            }
        }

        int len1 = (objArray1 == null) ? 0 : objArray1.length;
        int len2 = (objArray2 == null) ? 0 : objArray2.length - indexObjArray2;
        int newLen = len1 + len2;

        // Komponententyp ermitteln
        Class<?> componentType;
        if (objArray1 != null) {
            componentType = objArray1.getClass().getComponentType();
        } else {
            componentType = objArray2.getClass().getComponentType();
        }

        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(componentType, newLen);

        int pos = 0;

        // Ersten Teil kopieren
        if (objArray1 != null) {
            System.arraycopy(objArray1, 0, result, pos, len1);
            pos += len1;
        }

        // Zweiten Teil ab indexObjArray2 kopieren
        if (objArray2 != null && len2 > 0) {
            System.arraycopy(objArray2, indexObjArray2, result, pos, len2);
        }

        return result;
    }

	
	
	/** von ChatGPT...
	 * @param array
	 * @param index
	 * @return
	 * @author Fritz Lindhauer, 04.11.2025, 11:05:43
	 */
	public static <T> T[] removeAt(T[] objArray, int index) {
		checkmain:{
			boolean bEmptyArray = ArrayUtilZZZ.isNull(objArray);
			if(bEmptyArray) {
				return objArray;   
			}
			if (index < 0 || index >= objArray.length) {
				return objArray;
			}
		}//end checkmain:
		
		@SuppressWarnings("unchecked")
        T[] objaReturn = (T[]) Array.newInstance(objArray.getClass().getComponentType(), objArray.length - 1);
		main:{
	        // Elemente vor dem Index kopieren
	        System.arraycopy(objArray, 0, objaReturn, 0, index);
	        
	        // Elemente nach dem Index kopieren
	        System.arraycopy(objArray, index + 1, objaReturn, index, objArray.length - index - 1);	        
		}//end main:
        return objaReturn;
    }
	
	 /** Ein und Ausgabearray unterscheiden sich
     * @param objArray
     * @return
     * @author Fritz Lindhauer, 25.11.2025, 19:15:01
     */
    public static <T> T[] removeNulls(T[] objArray) {
    	return removeNulls1_(objArray);   //Ein und Ausgabearray unterscheiden sich
    	//return removeNullsUsingArraycopy_(objArray); //Ein und Ausgabearray unterscheiden sich
    }
    
    /** von ChatGPT erstellte Version 1
     * @param input
     * @return
     * @author Fritz Lindhauer, 25.11.2025, 19:27:29
     */
    private static <T> T[] removeNulls1_(T[] input) {
        if (input == null) {
            return null;
        }

        // 1. Anzahl der nicht-null Elemente bestimmen
        int count = 0;
        for (T element : input) {
            if (element != null) {
                count++;
            }
        }

        // Wenn nur null-Werte vorhanden sind → leeres Array zurückgeben
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(
                input.getClass().getComponentType(),
                count
        );

        // 2. Nicht-null Werte in neues Array kopieren
        int index = 0;
        for (T element : input) {
            if (element != null) {
                result[index++] = element;
            }
        }

        return result;
    }

    
    /**von ChatGPT erstellte Version 2
     * @param input
     * @return
     * @author Fritz Lindhauer, 25.11.2025, 19:27:53
     */
    private static <T> T[] removeNullsUsingArraycopy_(T[] input) {
        if (input == null) {
            return null;
        }

        // 1. Zählen, wie viele Elemente nicht null sind
        int count = 0;
        for (T element : input) {
            if (element != null) {
                count++;
            }
        }

        // Wenn alle null → leeres Array zurückgeben
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(
                input.getClass().getComponentType(), 
                count
        );

        // 2. Kopieren mit System.arraycopy
        int index = 0;
        for (T element : input) {
            if (element != null) {
                // einzelnes Element kopieren
                System.arraycopy(new Object[]{element}, 0, result, index, 1);
                index++;
            }
        }

        return result;
    }
	
	
	
	
	
	
	/** von ChatGPT...
	 * @param array
	 * @param index
	 * @return
	 * @author Fritz Lindhauer, 04.11.2025, 11:05:43
	 */
	public static <T> T[] reverse(T[] objArray) {
		T[] objaReturn = (T[]) Array.newInstance(objArray.getClass().getComponentType(), objArray.length);
		main:{
			if(objArray==null) return null;
			boolean bEmptyArray = ArrayUtilZZZ.isNull(objArray);
			if(bEmptyArray) break main;
			
			// Elemente kopieren		
	        System.arraycopy(objArray, 0, objaReturn, 0, objArray.length - 1);
			
	        //Nun im neu erstellten Array umsortieren.
		    int left = 0;
		    int right = objArray.length - 1;
	
		    while(left <= objArray.length - 1) {
		        objaReturn[left] = objArray[right];		        
		        left++;
		        right--;
		    }

		}//end main:
	    return objaReturn; // optional, da das Array in-place geändert wird
	}
	
	
    /** von ChatGPT erstellt
     * @param sourceArray
     * @param splitValue
     * @param clazz
     * @return
     * @author Fritz Lindhauer, 09.11.2025, 07:21:33
     */
    public static <T> List<T[]> splitByValue(T[] sourceArray, T splitValue, Class<T> clazz) {
    	List<T[]> listaReturn = new ArrayList<T[]>();
    	main:{
	        List<T> currentList = new ArrayList<T>();
	
	        for (T element : sourceArray) {
	            if (element == null ? splitValue == null : element.equals(splitValue)) {
	                // Wenn Split-Wert gefunden: aktuellen Abschnitt abschließen
	                if (!currentList.isEmpty()) {
	                    T[] subArray = currentList.toArray((T[]) java.lang.reflect.Array.newInstance(clazz, currentList.size()));
	                    listaReturn.add(subArray);
	                    currentList.clear();
	                }
	            } else {
	                currentList.add(element);
	            }
	        }
	
	        // Letztes Teilarray hinzufügen (falls vorhanden)
	        if (!currentList.isEmpty()) {
	            T[] subArray = currentList.toArray((T[]) java.lang.reflect.Array.newInstance(clazz, currentList.size()));
	            listaReturn.add(subArray);
	        }
    	}//end main:
    	
        return listaReturn;
    }
    
    public static <T> Map<String, T[]> splitByKeysToMap(T[] values, String[] keys, Class<T> clazz) throws ExceptionZZZ{

        Map<String, T[]> result = new LinkedHashMap<String, T[]>();
        Set<String> keySet = new HashSet<String>(Arrays.asList(keys));

        List<T> currentList = new ArrayList<T>();
        String currentKey = "pre";

        for (T value : values) {

            if (value instanceof String && keySet.contains(value)) {
            	//also neuen Separator gefunden            	
            	result.put(currentKey, ArrayListUtilZZZ.toArray(currentList, clazz));

                currentKey = (String) value;
                currentList = new ArrayList<T>();                
            } else {
            	currentList.add(value);
            }
        }
        
        //das letzte Array auch noch hinzufuegen
        result.put(currentKey, ArrayListUtilZZZ.toArray(currentList, clazz));
        return result;
    }
    
    public static <T> Map<T, T[]> splitByKeysToMap(T[] values, T[] keys, T firstKey, Class<T> clazz) throws ExceptionZZZ{
    	Map<T, T[]> objReturn = null;
    	main:{
    		if(values==null) break main;    		
    		objReturn = new LinkedHashMap<T, T[]>();
    		
    		if(keys==null) break main;
		    Set<T> keySet = new HashSet<T>(Arrays.asList(keys));
		
		    List<T> currentList = new ArrayList<T>();
		    T currentKey = firstKey;
		    
		    for (T value : values) {
		    	
		        if (keySet.contains(value)) { //also neuen Separator gefunden            	
		        	
		        	//Wenn der Separator aber mehrmals vorkommt, muss man die neue ArrayList mit der vorherigen des Separators zusammenfassen.
		        	T[] aPrevious = objReturn.get(currentKey);
		        	T[] aNew = ArrayListUtilZZZ.toArray(currentList, clazz);
		        	T[] a = ArrayUtilZZZ.joinUnique(aPrevious, aNew);
		        	objReturn.put(currentKey, a);
		
		            currentKey = value;
		            currentList = new ArrayList<T>();
		        } else {
		        	 currentList.add(value); 
		        }
		    }
		    
		    //das letzte Array auch noch hinzufuegen
		    objReturn.put(currentKey, ArrayListUtilZZZ.toArray(currentList, clazz));
    	}//end main:
        return objReturn;
    }

    
    //#############################################################
    
    /** Ein und Ausgabearray unterscheiden sich
     * @param objArray
     * @return
     * @author Fritz Lindhauer, 25.11.2025, 19:15:01
     */
    public static <T> T[] trim(T[] objArray) {    	
    	return removeNulls(objArray);
    }
    
   
	
	/** s. https://sentry.io/answers/arraylist-from-array/
	 * 
	 * 
	 * @author Fritz Lindhauer, 16.03.2024, 14:50:47
	 */
//	public static <T> ArrayList<? extends T> toArrayList(T[] objArray) {
//		List<? extends T> listReturn = new ArrayList<T>(Arrays.asList(objArray));
//		return (ArrayList<? extends T>) listReturn;
//	}
    public static <T> ArrayList<T> toArrayList(T[] objArray) {
        if (objArray == null) {
            return new ArrayList<T>();
        }
        return new ArrayList<T>(Arrays.asList(objArray));
    }
	
	/** s. https://sentry.io/answers/arraylist-from-array/
	 * 
	 * 
	 * @author Fritz Lindhauer, 16.03.2024, 14:50:47
	 */
//	public static <T> List<? extends T> toList(T[] objArray) {
//		List<? extends T> listReturn = new ArrayList<T>(Arrays.asList(objArray));
//		return (ArrayList<? extends T>) listReturn;
//	}
	public static <T> List<T> toList(T[] objArray) {
	    if (objArray == null) {
	        return new ArrayList<T>();
	    }
	    return new ArrayList<T>(Arrays.asList(objArray));
	}
	
	public static <T> List<T> toList(T obj) {
	    if (obj == null) {
	        return new ArrayList<T>();
	    }
	    return new ArrayList<T>(Arrays.asList(obj));
	}
	
}
