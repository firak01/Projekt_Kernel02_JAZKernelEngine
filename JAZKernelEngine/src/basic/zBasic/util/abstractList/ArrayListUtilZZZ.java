package basic.zBasic.util.abstractList;

import java.awt.Component;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectClassZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;

/**Merksatz (wichtig!)(von ChatGPT, 20260110)
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 10.01.2026, 08:22:59
 */
public class ArrayListUtilZZZ<T>  extends ListUtilZZZ {
	private ArrayListUtilZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
	
	/** Variante bei kleinen Listen, die aber list1 verändert:
	 *   // Erstellen einer Kopie von list1, um das Original nicht zu verändern
        List<String> difference = new ArrayList<String>(list1);
        // removeAll entfernt alle Elemente aus 'difference', 
        // die auch in 'list2' enthalten sind.
        difference.removeAll(list2);
	 *   
	 *  Variante hier, Umwandlung in HashSet... soll performanter sein
	 * @param list01
	 * @param list02
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static <T> ArrayList<T> difference(ArrayList<T> list01, ArrayList<T> list02) throws ExceptionZZZ{
		ArrayList<T>listaReturn = null;
		main:{
			if(list01==null || list02==null)break main;
			
		    Set<T> set2 = new LinkedHashSet<T>(list02); // Schnellere Suche
		    ArrayList<T> difference = new ArrayList<T>(list01);
		    difference.removeAll(set2);
		    
		    listaReturn = difference;
		}//End main:
		return listaReturn;
	}	
	
	public static <T> boolean isEmpty(ArrayList<T> objAL) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objAL==null) {
				bReturn = true;
				break main;
			}
			if(objAL.size()==0) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static <T> boolean isNull(ArrayList<T> objAL) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objAL==null) {
				bReturn = true;
				break main;
			}
		}
		return bReturn;
	}
	
	public static <T> T getFirst(ArrayList<T> objAL) throws ExceptionZZZ {
		T objReturn = null;
		main:{
			if(objAL==null)break main;
			if(objAL.isEmpty()) break main;
			
			objReturn = objAL.get(0);
		}
		return objReturn;
	}
	
	public static <T> T getLast(ArrayList<T> objAL) throws ExceptionZZZ {
		T objReturn = null;
		main:{
			if(objAL==null)break main;
			if(objAL.isEmpty()) break main;
			
			int iSize = objAL.size();
			objReturn = objAL.get(iSize-1);
		}
		return objReturn;
	}
	
	public static <T> boolean isSameSize(ArrayList<T> objAL1, ArrayList<T> objAL2) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(objAL1== null){
					ExceptionZZZ ez = new ExceptionZZZ("ArrayList1 to compare'", iERROR_PARAMETER_MISSING,  HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;	
				  }
				if(objAL2== null){
					ExceptionZZZ ez = new ExceptionZZZ("ArrayList2 to compare'", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;	
				  }
				//###################
				int iSize1 = objAL1.size();
				int iSize2 = objAL2.size();
				
				if (iSize1 == iSize2) bReturn = true;
			}//end main:
			return bReturn;
		}
	
	public static <T> String implode(ArrayList<T>lista, String sDelimiterIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(lista==null)break main;
			if(lista.size()==0)break main;
			
			String sDelimiter;
			if(sDelimiterIn==null){
				sDelimiter="";
			}else{
				sDelimiter=sDelimiterIn;
			}
			
			for(int icount=0; icount <= lista.size()-1; icount++){
				String sPosition = (String) lista.get(icount);
				if(sReturn==null){
					sReturn=sPosition;
				}else{
					sReturn+=sDelimiter+sPosition;
				}
			}
		}
		return sReturn;
	}
	
	public static <T> String implodeReversed(ArrayList<T>lista, String sDelimiterIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(lista==null)break main;
			if(lista.size()==0)break main;
			
			String sDelimiter;
			if(sDelimiterIn==null){
				sDelimiter="";
			}else{
				sDelimiter=sDelimiterIn;
			}
			
			for(int icount = lista.size()-1; icount >= 0; icount--){
				String sPosition = (String) lista.get(icount);
				if(sReturn==null){
					sReturn=sPosition;
				}else{
					sReturn+=sDelimiter+sPosition;
				}
			}		
		}
		return sReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static <T> ArrayList<T> intersectOrNotNull(ArrayList<T> list01, ArrayList<T> list02) throws ExceptionZZZ{
		ArrayList<T>listaReturn = null;
		main:{
			if(list01==null && list02==null)break main;
			if(list01==null) {
				if(list02.size()==0) break main;
				
				listaReturn = list02;
				break main;
			}
			
			
			if(list02==null) {
				if(list01.size()==0) break main;
				
				listaReturn = list01;
				break main;
			}
			
			
			listaReturn = ArrayListUtilZZZ.intersect(list01, list02);
		}//End main:
		return listaReturn;
	}	
	
	/** Variante bei kleinen Listen, die aber list1 verändert:
	 *   list1.retainAll(list2);
	 *   
	 *  Variante hier, Umwandlung in HashSet... soll performanter sein
	 * @param list01
	 * @param list02
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static <T> ArrayList<T> intersect(ArrayList<T> list01, ArrayList<T> list02) throws ExceptionZZZ{
		ArrayList<T>listaReturn = null;
		main:{
			if(list01==null || list02==null)break main;
			
			
		    Set<T> set1 = new LinkedHashSet<T>(list01);
		    Set<T> resultSet = new LinkedHashSet<T>();

		    for (T element : list01) {
		        set1.add(element);
		    }

		    for (T element : list02) {
		        if (set1.contains(element)) {
		            resultSet.add(element);
		        }
		    }
		    
		    listaReturn = new ArrayList<T>(resultSet);
		}//End main:
		return listaReturn;
	}	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/* Gibt für die Elemente der Liste die instanceof - Werte zurück.
	 * Also nicht instanceof der Liste selbst...
	 * 
	 * heuristischer Ansatz, mit Probleme. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 * 
	 * 
	 * Aber: Man kann sowieso keine Klasse an diese statische Methode übergeben (Merke: Class<T> geht nicht )
	 * Darum in einer Schleife alle durchgehen.
	 * 
	 * Hier: 
	 * Damit nicht x - Mal (z.B. in einer Fallunterscheidung) isInstanceOf aufgerufen werden muss
	 * Hier einmalig den Datentyp der Elemente bestimmen.
	 * Ggfs. einen Fehler werfen, wenn er nicht eindeutig ist.
	 */
	public static ArrayList<Class<?>> getInstanceOfList(Object objAsList) throws ExceptionZZZ {
		ArrayList<Class<?>>listaReturn=null;
		main:{			
			if(objAsList instanceof List){
				if(ArrayListUtilZZZ.isEmpty((ArrayList<?>) objAsList))break main;
				
				ArrayListZZZ<Class<?>> listaExtended = new ArrayListZZZ<Class<?>>();
				for(Object obj : (List)objAsList) {					
					ArrayList<Class<?>> listaClass = ReflectClassZZZ.getInstanceOfList(obj);
					listaExtended.addAllUnique(listaClass);
				}
				listaReturn = (ArrayList<Class<?>>) listaExtended.toArrayList();
			}
			
		}//end main:
		return listaReturn;	
	}
		
	/* heuristischer Ansatz, mit Probleme. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 */
	public static boolean isInstanceOf(Object objAsList, Class objClass) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objClass==null)break main;
			
			if(objAsList instanceof List){
				if(ArrayListUtilZZZ.isEmpty((ArrayList<?>) objAsList))break main;
				
//			    if(((List)obj).size()>0 && (((List)obj).get(0) instanceof MeineTolleKlasse)){
//			        // The object is of List<MyObject> and is not empty. Do something with it.
//			    }
				
				//ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(objClass);												
				for(Object obj : (List)objAsList) {
					Class<? extends Object> classTemp = obj.getClass();//Das funktioniert aber nicht mit Interfaces
					
					//also: Die idee ist, das man die Klasse selbst eher findet und auch eher angibt. Darum nicht erst alle instanceOfList - Objekte holen.
					//      Statt hier getInstanceOfList() zu verwenden das hier ausprogrammieren
					//      getInstanceOfList() sollte dann im Vorfeld aufgerufen werden um halt mehrere isInstanceOfList-Aufrufe zu vermeiden
					if(!classTemp.equals(objClass))	{ 					
						ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(obj.getClass());							
						if(!listaInterface.contains(objClass)) break main; 
					}
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;	
	}
	
	/* heuristischer Ansatz, mit Problemen. 
	 * Gemäss: https://stackoverflow.com/questions/10108122/how-to-instanceof-listmytype
	 * nicht sicher und man sollte statt dessen eine "GenericList" verwenden.
	 * 
	 * Aber: Man kann sowieso keine Klasse an diese statische Methode übergeben (Merke: Class<T> geht nicht )
	 * Darum in einer Schleife alle durchgehen.
	 */
	public static <T> boolean isInstanceOf(ArrayList<T> lista, Class objClass) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			if(objClass==null)break main;
			if(ArrayListUtilZZZ.isEmpty(lista))break main;
		
			for(T obj : lista) {
				Class<? extends Object> classTemp = obj.getClass();//Das funktioniert aber nicht mit Interfaces
				
				//also: Die idee ist, das man die Klasse selbst eher findet und auch eher angibt. Darum nicht erst alle instanceOfList - Objekte holen.
				//      Statt hier getInstanceOfList() zu verwenden das hier ausprogrammieren.
				//      getInstanceOfList() sollte dann im Vorfeld aufgerufen werden um halt mehrere isInstanceOfList-Aufrufe zu vermeiden 
				if(!classTemp.equals(objClass))	{					
					ArrayList<Class<?>> listaInterface = ReflectClassZZZ.getInterfaces(obj.getClass());							
					if(!listaInterface.contains(objClass)) break main; 
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	
		
	public static <T> ArrayList<T> join(ArrayList<T> lista1, ArrayList<T> lista2) throws ExceptionZZZ {
		return ArrayListUtilZZZ.join(lista1, lista2, false);
	}
	
	public static <T> ArrayList<T> join(ArrayList<T> lista1, ArrayList<T> lista2, boolean bFlagUnique) throws ExceptionZZZ{
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			
			if(bFlagUnique==false){
				//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
				listaReturn = new ArrayList<T>();
				if(lista1!=null){
					for(int icount=0; icount < lista1.size(); icount++){
						listaReturn.add(lista1.get(icount));
					}
				}//lista1!=null
				if(lista2!=null){
					for(int icount=0; icount < lista2.size(); icount ++){
						listaReturn.add(lista2.get(icount));
					}
				}		
				break main;
				
			}else{
				//Wenn 'uniqued' werden soll, dann ist "keepFirst" die default-Strategie
				listaReturn = ArrayListUtilZZZ.joinKeepFirst(lista1,lista2);		
				break main;
			}//End if (bFlagUnique ....
			
		}//END main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> join(ArrayList<T> lista1, ArrayList<T> lista2, ArrayList<T> lista3) throws ExceptionZZZ {
		return ArrayListUtilZZZ.join(lista1, lista2, lista3, false);
	}
	
	public static <T> ArrayList<T> join(ArrayList<T> lista1, ArrayList<T> lista2, ArrayList<T> lista3, boolean bFlagUnique) throws ExceptionZZZ{
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null  && lista3 ==null) break main;
			
			
			if(bFlagUnique==false){
				//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
				listaReturn = new ArrayList<T>();
				if(lista1!=null){
					for(int icount=0; icount < lista1.size(); icount++){
						listaReturn.add(lista1.get(icount));
					}
				}//lista1!=null
				if(lista2!=null){
					for(int icount=0; icount < lista2.size(); icount ++){
						listaReturn.add(lista2.get(icount));
					}
				}//lista2!=null
				if(lista3!=null){
					for(int icount=0; icount < lista3.size(); icount ++){
						listaReturn.add(lista3.get(icount));
					}
				}			
				break main;
				
			}else{
				//Wenn 'uniqued' werden soll, dann ist "keepFirst" die default-Strategie
				listaReturn = ArrayListUtilZZZ.joinKeepFirst(lista1,lista2,lista3);		
				break main;
			}//End if (bFlagUnique ....
			
		}//END main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> joinKeepFirst(ArrayList<T> lista1, ArrayList<T> lista2) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList<T> listaTemp = new ArrayList<T>();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}		
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> joinKeepFirst(ArrayList<T> lista1, ArrayList<T> lista2, ArrayList<T> lista3) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null && lista3 ==null) break main;
			
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList<T> listaTemp = new ArrayList<T>();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}//lista2!=null
			if(lista3!=null){
				for(int icount=0; icount < lista3.size(); icount ++){
					listaTemp.add(lista3.get(icount));
				}
			}			
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> joinKeepLast(ArrayList<T> lista1, ArrayList<T> lista2, ArrayList<T> lista3) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
					
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList<T> listaTemp = new ArrayList<T>();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}//lista2!=null
			if(lista3!=null){
				for(int icount=0; icount < lista3.size(); icount ++){
					listaTemp.add(lista3.get(icount));
				}
			}			
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepLast(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	//###########################################################
	
	public static <T> ArrayList<T> mergeKeepFirst(ArrayList<T>lista1, ArrayList<T>lista2) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
					
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList<T> listaTemp = new ArrayList<T>();
			if(lista1==null){
				for(int icount=0; icount < lista2.size(); icount++){
					listaTemp.add(lista2.get(icount));
				}
				listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);
				break main;
			}//lista1==null
			
			
			if(lista2==null){
				for(int icount=0; icount < lista1.size(); icount ++){
					listaTemp.add(lista1.get(icount));
				}
				listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);
				break main;
			}		
			
			//Nun als Schritt 2, die Variante mit dem echten Reinmischen.			
			ArrayList<T> list = ArrayListUtilZZZ.mergeKeepFirst_Step2_(lista1, lista2);
			listaReturn = list;
			
		}//END main:
		return listaReturn;
	}
	
	
	/** ChatGPT 2026-03-07
	 * @param lista1
	 * @param lista2
	 * @return
	 * @author Fritz Lindhauer, 07.03.2026, 08:58:33
	 */
	private static <T> ArrayList<T> mergeKeepFirst_Step2_(ArrayList<T> lista1, ArrayList<T> lista2) {
	    ArrayList<T> listaReturn = null;	    
	    main:{
		    if(lista1==null || lista1.size()==0) {
		    	listaReturn = lista2;
		    	break main;
		    }
		    
		    listaReturn = new ArrayList<T>();
	
		    int index2 = 0;
	
		    for (int i = 0; i < lista1.size(); i++) {
	
		        T e1 = lista1.get(i);
	
		        int matchIndex = -1;
	
		        for (int j = index2; j < lista2.size(); j++) {
		            if (e1.equals(lista2.get(j))) {
		                matchIndex = j;
		                break;
		            }
		        }
	
		        if (matchIndex >= 0) {
	
		            // Elemente aus lista2 bis zum Match
		            for (int j = index2; j < matchIndex; j++) {
		                T e2 = lista2.get(j);
		                if (!listaReturn.contains(e2)) {
		                    listaReturn.add(e2);
		                }
		            }
	
		            if (!listaReturn.contains(e1)) {
		                listaReturn.add(e1);
		            }
	
		            index2 = matchIndex + 1;
	
		            // prüfen ob nächstes Element aus lista1 ebenfalls in lista2 vorkommt
		            int nextMatchIndex = -1;
	
		            if (i + 1 < lista1.size()) {
		                T next = lista1.get(i + 1);
	
		                for (int j = index2; j < lista2.size(); j++) {
		                    if (next.equals(lista2.get(j))) {
		                        nextMatchIndex = j;
		                        break;
		                    }
		                }
		            }
	
		            if (nextMatchIndex == -1) {
		                while (index2 < lista2.size()) {
		                    T e2 = lista2.get(index2++);
		                    if (!listaReturn.contains(e2)) {
		                        listaReturn.add(e2);
		                    }
		                }
		            }
	
		        } else {
		            if (!listaReturn.contains(e1)) {
		                listaReturn.add(e1);
		            }
		        }
		    }
	    }//end main;
	    return listaReturn;
	}
	
	
	
	//###########################################################
	public static <T> void remove(ArrayList<T> lista, String sToRemove, boolean bIgnoreCase) throws ExceptionZZZ {
		main:{
		if(lista==null) break main;
		if(sToRemove==null) break main;
		
		if(bIgnoreCase){	
			for(T obj : lista){
				if(sToRemove.equalsIgnoreCase(obj.toString())){			
					lista.remove(obj);
					break main; //wenn man danach weiter durch die Liste gehen will, dann gibt es Fehler.
				}
			}
		}else{
			lista.remove(sToRemove);
		}
		
	  }//end main:
	}
	
	public static <T> void remove(ArrayList<T> lista, Integer intToRemove) throws ExceptionZZZ {
		main:{
		if(lista==null) break main;
		if(intToRemove==null) break main;
		
			
		for(T obj : lista){
			if(obj.equals(intToRemove)){			
				lista.remove(obj);	
				break main;
			}
		}		
	  }//end main:
	}
	

	public static <T> void removeLast(ArrayList<T> lista, int iNumberOfElements2Remove) throws ExceptionZZZ {
		main:{
			if(lista==null) break main;
			if(lista.size()>iNumberOfElements2Remove){
				for(int iCount = 0; iCount <= iNumberOfElements2Remove; iCount++){
					int iLast = lista.size() - 1; //-1 da der Index mit 0 anfängt
					lista.remove(iLast);
				}
			}else{
				lista.clear();
			}
		}//end main:
	}
	
		
	//########################################################################
	/** von ChatGPT erstellt
	 * @param originalList
	 * @return
	 * @author Fritz Lindhauer, 09.11.2025, 08:21:28
	 */
	public static <T> ArrayList<T> reverse(ArrayList<T> listaOriginal) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(listaOriginal==null) break main;
			
        	// Sicherheitskopie erstellen, um die ursprüngliche Liste nicht zu verändern
        	listaReturn = new ArrayList<T>(listaOriginal);
        	Collections.reverse(listaReturn);
		}//end main
        return listaReturn;
    }
	
	/** von ChatGPT erstellt
	 * @param originalList
	 * @return
	 * @author Fritz Lindhauer, 09.11.2025, 08:21:28
	 */
	public static <T> ArrayListZZZ<T> reverse(ArrayListZZZ<T> listaOriginal) throws ExceptionZZZ {
		ArrayListZZZ<T> listaReturn = null;
		main:{
			if(listaOriginal==null) break main;
			
        	// Sicherheitskopie erstellen, um die ursprüngliche Liste nicht zu verändern
        	listaReturn = listaOriginal;
        	Collections.reverse(listaOriginal);
		}//end main
        return listaReturn;
    }
	
	public static <T> ArrayListZZZ<T> reverseV01(ArrayListZZZ<T> lista) throws ExceptionZZZ {
	    ArrayListZZZ<T> listaReturn = null; 
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			
			
			// create a new list, with exactly enough initial capacity to hold the (reversed) list
			final int size = lista.size();
			final int last = size - 1;
			
		    listaReturn = new ArrayListZZZ<T>(size);
		    
		    // iterate through the list in reverse order and append to the result
		    for (int i = last; i >= 0; --i) {
		        final T element = lista.get(i);
		        listaReturn.add(element);
		    }
		}//end main	
	    return listaReturn;
	}	
	
	public static <T> ArrayListUniqueZZZ<T> reverse(ArrayListUniqueZZZ<T> listaOriginal) throws ExceptionZZZ {
		ArrayListUniqueZZZ<T> listaReturn = null;
		main:{
			if(listaOriginal==null) break main;
			
        	// Sicherheitskopie erstellen, um die ursprüngliche Liste nicht zu verändern
        	listaReturn = new ArrayListUniqueZZZ<T>(listaOriginal);
        	Collections.reverse(listaReturn);
		}//end main
        return listaReturn;
    }
	
	
	
	//#############################################################################
	/**
	 * @param lista
	 * @return
	 * 
	 * siehe: https://javahungry.blogspot.com/2017/11/how-to-sort-arraylist-in-descending-order-in-java.html
	 */
	public static void  sortReverseAlphabetOrder(ArrayList<String> lista) throws ExceptionZZZ {
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			 Collections.sort(lista, Collections.reverseOrder());		
		}//end main	
	}
	
	
	
	public static Object[]toArray(ArrayList<?> lista) throws ExceptionZZZ {
		Object[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new Object[lista.size()]);
			int iIndex = -1;
			for(Object obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(ArrayList<T> lista, Object obj) throws ExceptionZZZ {
		T[]aReturn = null;
		main:{
			if(lista==null) break main;
			if(obj==null) {
				aReturn= (T[]) ArrayListUtilZZZ.toArray(lista);
				break main;
			}
			
			Class<T> objClass = (Class<T>) obj.getClass();
			aReturn = ArrayListUtilZZZ.toArray(lista, objClass);
			
		}//end main:
		return aReturn;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(ArrayList<T> lista, Class<T> classObjIn) throws ExceptionZZZ {
		T[]aReturn = null;
		main:{
			if(lista==null) break main;
			
			Class<T> classObj = null; 
			if(classObjIn == null) {
				classObj = (Class<T>) Object.class;
			}else {
				classObj = classObjIn;				
			}
			
			aReturn = lista.toArray((T[]) java.lang.reflect.Array.newInstance(classObj, lista.size()));
		}
		return aReturn;
	}
	
	
//	private static <T> T[] listToArray(List<T> list, Class<T> clazz) {
//      @SuppressWarnings("unchecked")
//      T[] array = (T[]) Array.newInstance(clazz, list.size());
//      return list.toArray(array);
//  }
		
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> lista, Class<T> classObjIn) throws ExceptionZZZ {
		T[]aReturn = null;
		main:{
			if(lista==null) break main;
			
			Class<T> classObj = null; 
			if(classObjIn == null) {
				classObj = (Class<T>) Object.class;
			}else {
				classObj = classObjIn;				
			}
			
			aReturn = lista.toArray((T[]) java.lang.reflect.Array.newInstance(classObj, lista.size()));
		}
		return aReturn;
	}
	
	//####################################
	public static ArrayListZZZ<String> toArrayListString(String[] sa) throws ExceptionZZZ{
		ArrayListZZZ<String> listaReturn = null;
		main:{
			ArrayList<String> listasLine = StringArrayZZZ.toArrayList(sa);
			listaReturn = ArrayListUtilZZZ.toArrayListString(listasLine);
		}//end main:
		return listaReturn;
	}
	
	public static ArrayListZZZ<String> toArrayListString(ArrayList<String> listas) throws ExceptionZZZ{
		ArrayListZZZ<String> listaReturn = new ArrayListZZZ<String>();
		main:{
			for(String s : listas) {
				listaReturn.add(s);
			}
		}
		return listaReturn;
	}
	
	public static <T> ArrayListZZZ<T> toArrayList(ArrayList<T> listas) throws ExceptionZZZ{
		ArrayListZZZ<T> listaReturn = new ArrayListZZZ<T>();
		main:{
			for(T value : listas) {
				listaReturn.add(value);
			}
		}
		return listaReturn;
	}
	
	public static Component[]toComponentArray(ArrayList<Component> lista) throws ExceptionZZZ {
		Component[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new Component[lista.size()]);
			int iIndex = -1;
			for(Component obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}


	public static <E extends Enum> E[] toEnumArray(ArrayList<E> listae) throws ExceptionZZZ {
		E[] enumaReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			enumaReturn = (E[]) listae.toArray(new Enum[listae.size()]);		
		}//end main:
		return enumaReturn;	
		}

	public static <E extends Enum & IEnumSetMappedZZZ> E[] toEnumArrayByMapped(ArrayList<E> listae) throws ExceptionZZZ {
		E[] objaeReturn = null;
		main:{
			if(listae==null) break main;
			if(listae.size()==0) break main;
			
			objaeReturn = (E[]) listae.toArray(new Enum[listae.size()]);
		}//end main:
		return objaeReturn;	
	}
	
	//Wg. CAST Problematik aus dieser Klasse raus. Verwende bessert die Util-Klasse des jeweiling EnumSetMapped
//	public static <E extends Enum & IEnumSetMappedStatusLocalZZZ> E[] toEnumArrayByMappedStatus(ArrayList<E> listae) throws ExceptionZZZ {
//		E[] objaeReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			objaeReturn = (E[]) listae.toArray(new Enum[listae.size()]);
//		}//end main:
//		return objaeReturn;	
//	}
	

	//Wg. CAST Problematik aus dieser Klasse raus. Verwende bessert die Util-Klasse des jeweiling EnumSetMapped
//	public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArrayByMapped(ArrayList<IEnumSetMappedZZZ> listae){
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = (E[]) listae.toArray(new IEnumSetMappedZZZ[listae.size()]);
//		}//end main:
//		return enumaReturn;	
//	}

	//todo loeschen wird alles in EnumSetMappedStatusUtilZZZ gemacht.
//	public static <E extends IEnumSetMappedStatusZZZ> IEnumSetMappedStatusZZZ[] toEnumMappedStatusArrayByMapped(ArrayList<IEnumSetMappedStatusZZZ> listae) throws ExceptionZZZ{
//		IEnumSetMappedStatusZZZ[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = (IEnumSetMappedStatusZZZ[]) listae.toArray(new IEnumSetMappedStatusZZZ[listae.size()]);
//		}//end main:
//		return enumaReturn;	
//	}
	
	
//	public static <E extends Enum<E> & IEnumSetMappedStatusZZZ> ArrayList<E> toEnumMappedArrayListByMapped(List<E> listae) throws ExceptionZZZ{
//		ArrayList<E> listaeReturn = null;
//		main:{
//			if(listae==null) break main;
//			
//			listaeReturn = new ArrayList<E>();
//			if(listae.size()==0) break main;
//			
//			listaeReturn = EnumSetMappedUtilZZZ.toEnumMappedArrayList(listae);
//		}//end main:
//		return listaeReturn;	
//	}
	
	public static <E extends Enum<E> & IEnumSetMappedStatusLocalZZZ> ArrayList<E> toEnumMappedStatusArrayListByMapped(List<E> listae) throws ExceptionZZZ{
		ArrayList<E> listaeReturn = null;
		main:{
			if(listae==null) break main;
			
			listaeReturn = new ArrayList<E>();
			if(listae.size()==0) break main;
			
			listaeReturn = EnumSetMappedUtilZZZ.toEnumMappedStatusArrayList(listae);
		}//end main:
		return listaeReturn;	
	}
	
	
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//	public static <E extends IEnumSetMappedZZZ> E[] toEnumMappedArray(ArrayList<E> listae){
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = EnumSetMappedUtilZZZ.toEnumMappedArray(listae);
//		}//end main:
//		return enumaReturn;	
//	}
	
//Es darf kein Array mit Interface zurueckgegeben werden, s. ChatGPT 20260110	
//	public static <E extends IEnumSetMappedStatusZZZ> E[] toEnumMappedStatusArray(ArrayList<E> listae) throws ExceptionZZZ{
//		E[] enumaReturn = null;
//		main:{
//			if(listae==null) break main;
//			if(listae.size()==0) break main;
//			
//			enumaReturn = (E[]) EnumSetMappedUtilZZZ.toEnumMappedStatusArray(listae);
//		}//end main:
//		return enumaReturn;	
//	}

	
	public static File[]toFileArray(ArrayList<File> lista)  throws ExceptionZZZ {
		File[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new File[lista.size()]);
			int iIndex = -1;
			for(File obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	public static int[]toIntArray(ArrayList<?> lista)  throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			Integer[]intaReturn = lista.toArray(new Integer[lista.size()]);
			
			int iIndex = -1;			
			iaReturn=new int[intaReturn.length];
			for(Integer objInt : intaReturn){
				iIndex++;
				iaReturn[iIndex] = objInt;
			}
		}//end main:
		return iaReturn;	
	}

	public static String[]toStringArray(ArrayList<?> lista)  throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			//saReturn = lista.toArray(new String[lista.size()]);
			saReturn = new String[lista.size()];
			int iIndex = -1;
			for(Object obj : lista){
				iIndex++;
				if(obj!=null) {
					saReturn[iIndex] = obj.toString();
				}else {
					saReturn[iIndex] = null;
				}
			}
		}//end main:
		return saReturn;	
	}

	
	public static ZipEntry[]toZipEntryArray(ArrayList<ZipEntry> lista)  throws ExceptionZZZ {
		ZipEntry[] aReturn = null;
		main:{
			if(lista==null) break main;
			if(lista.size()==0) break main;
			
			aReturn = lista.toArray(new ZipEntry[lista.size()]);
			int iIndex = -1;
			for(ZipEntry obj : lista){
				iIndex++;
				aReturn[iIndex] = obj;
			}
		}//end main:
		return aReturn;	
	}
	
	//###############################
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static <T> ArrayList<T> trim(ArrayList<T> lista) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista==null)break main;					
			
			listaReturn=new ArrayList<T>();
			for(int icount=0; icount < lista.size(); icount++ ){
				T obj = lista.get(icount);
				if(obj!=null) listaReturn.add(obj);
			}	
		}//End main:
		return listaReturn;
	}
	
	//###############################
	public static <T> ArrayList<T> unique(ArrayList<T> lista) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(lista);
		}//End main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> uniqueKeepFirst(ArrayList<T> lista) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			listaReturn=new ArrayList<T>();
			for(int icount=0; icount < lista.size(); icount++ ){
				if(! listaReturn.contains(lista.get(icount))) listaReturn.add(lista.get(icount));
			}	
		}//End main:
		return listaReturn;
	}
	
	public static <T> ArrayList<T> uniqueKeepLast(ArrayList<T> lista) throws ExceptionZZZ {
		ArrayList<T> listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			//Strategie: KeepLast
			//Erst einmal die Listenreihenfolge umdrehen
			ArrayList<T> listaReversed = (ArrayList<T>) ArrayListUtilZZZ.reverse(lista);
			
			ArrayList<T> listaReturnReversed=new ArrayList<T>();
			for(int icount=0; icount < listaReversed.size(); icount++ ){
				if(! listaReturnReversed.contains(listaReversed.get(icount))) listaReturnReversed.add(listaReversed.get(icount));
			}	
			
			//Nach der Verarbeitung die Listenreihenfolge wieder zurückdrehen
			listaReturn = ArrayListUtilZZZ.reverse(listaReturnReversed);
			
		}//End main:
		return listaReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static <T> ArrayListZZZ<T> unique(ArrayListZZZ<T> lista) throws ExceptionZZZ {
		ArrayListZZZ<T> listaReturn = null;
		main:{
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(lista);
		}//End main:
		return listaReturn;
	}
	
	public static <T> ArrayListZZZ<T> uniqueKeepFirst(ArrayListZZZ<T> lista) throws ExceptionZZZ {
		ArrayListZZZ<T> listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			listaReturn=new ArrayListZZZ<T>();
			for(int icount=0; icount < lista.size(); icount++ ){
				if(! listaReturn.contains(lista.get(icount))) listaReturn.add(lista.get(icount));
			}	
		}//End main:
		return listaReturn;
	}
	
	public static <T> ArrayListZZZ<T> uniqueKeepLast(ArrayListZZZ<T> lista) throws ExceptionZZZ {
		ArrayListZZZ<T> listaReturn = null;
		main:{
			if(lista==null)break main;					
		
			//Strategie: KeepLast
			//Erst einmal die Listenreihenfolge umdrehen
			ArrayListZZZ<T> listaReversed = (ArrayListZZZ<T>) ArrayListUtilZZZ.reverse(lista);
			
			ArrayListZZZ<T> listaReturnReversed=new ArrayListZZZ<T>();
			for(int icount=0; icount < listaReversed.size(); icount++ ){
				if(! listaReturnReversed.contains(listaReversed.get(icount))) listaReturnReversed.add(listaReversed.get(icount));
			}	
			
			//Nach der Verarbeitung die Listenreihenfolge wieder zurückdrehen
			listaReturn = ArrayListUtilZZZ.reverse(listaReturnReversed);
			
		}//End main:
		return listaReturn;
	}
	
}//END class
