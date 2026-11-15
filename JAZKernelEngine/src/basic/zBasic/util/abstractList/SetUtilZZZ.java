package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;

public class SetUtilZZZ extends AbstractObjectWithExceptionZZZ implements ICollectionConstantZZZ{
	private static final long serialVersionUID = 4127464724750442138L;

	
	/** Gehe den Iterator des Sets durch und gib den abgezaehlten Wert zurueck.
	 * @param setToBeSearched
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:09:13
	 */	
	public static Object getByIndex(Set<?> setToBeSearched, int iIndex) {
		Object objReturn = null;
		main:{
			if(setToBeSearched==null)break main;
			if(iIndex < 0)break main;
			
			Iterator<?> it = setToBeSearched.iterator();
			int iCount = 0;
			while(it.hasNext()) {
				objReturn = it.next();
				if(iIndex==iCount) {
					break;
				}					
				iCount++;				
			}
		}
		return objReturn;
	}
	
	/** Gehe den Iterator des Sets durch und gib den letzten Wert zurueck.
	 * @param setToBeSearched
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:09:13
	 */
	public static Object getLast(Set<?> setToBeSearched) {
		Object objReturn = null;
		main:{
			if(setToBeSearched==null)break main;
			
			Iterator<?> it = setToBeSearched.iterator();
			while(it.hasNext()) {
				objReturn = it.next();
			}
		}
		return objReturn;
	}
	
	
//	/**Im Map.getEntrySet() sind die Werte mit "=" vorhanden. z.B. so vorhanden StringKey = WertKey
//	 * Das Set ist halt ein besonderes, naemlich Map.Entry
//	 * @param setToBeSearched
//	 * @return
//	 * @author Fritz Lindhauer, 10.05.2024, 11:03:09
//	 */
//	public static <K, V> Object getLastEntry(Set<Map.Entry<K, V>> setToBeSearched) {
//		Object objReturn = null;
//		main:{
//			if(setToBeSearched==null)break main;
//			
//			Iterator<?> it = setToBeSearched.iterator();
//			while(it.hasNext()) {
//				Map.Entry<K, V> entry = (Entry<K, V>) it.next();
//			}
//			objReturn = 
//		}
//		return objReturn;
//	}
	
	/** Gehe den Iterator des Sets durch und gib den letzten Wert zurueck.
	 * @param setToBeSearched
	 * @return
	 * @author Fritz Lindhauer, 10.05.2024, 09:09:13
	 */
	public static Object getFirst(Set<?> setToBeSearched) {
		Object objReturn = null;
		main:{
			if(setToBeSearched==null)break main;
			
			Iterator<?> it = setToBeSearched.iterator();
			while(it.hasNext()) {
				objReturn = it.next();
				break;
			}
			
		}
		return objReturn;
	}
	
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 * @throws ExceptionZZZ 
	 */
	public static List<?>sortAsString(Set<?> setToBeSorted, int iSortDirection) throws ExceptionZZZ{
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			if(iSortDirection==SetUtilZZZ.iSORT_DIRECTION_ASCENDING) {
				listReturn = SetUtilZZZ.sortAsString(setToBeSorted);
			}else if (iSortDirection==SetUtilZZZ.iSORT_DIRECTION_DESCENDING) {
				listReturn = SetUtilZZZ.sortAsStringReversed(setToBeSorted);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Only Ascending or Descending as Constant 1 or -1 is allowed. Value is ='" + iSortDirection + "'",  iERROR_PARAMETER_VALUE, SetUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsString(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsStringReversed(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList, Collections.reverseOrder());//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 * @throws ExceptionZZZ 
	 */
	public static List<String>sortToString(Set<?> setToBeSorted, int iSortDirection) throws ExceptionZZZ{
		List<String> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			if(iSortDirection==SetUtilZZZ.iSORT_DIRECTION_ASCENDING) {
				listReturn = SetUtilZZZ.sortToString(setToBeSorted);
			}else if (iSortDirection==SetUtilZZZ.iSORT_DIRECTION_DESCENDING) {
				listReturn = SetUtilZZZ.sortToStringReversed(setToBeSorted);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Only Ascending or Descending as Constant 1 or -1 is allowed. Value is ='" + iSortDirection + "'",  iERROR_PARAMETER_VALUE, SetUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<String>sortToString(Set<?> setToBeSorted){
		List<String> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<String>sortToStringReversed(Set<?> setToBeSorted){
		List<String> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<String> hsKey = new HashSet<String>((Collection<? extends String>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<String> numbersList = new ArrayList<String>(hsKey) ;        //set -> list
			Collections.sort(numbersList, Collections.reverseOrder());//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 * @throws ExceptionZZZ 
	 */
	public static List<?>sortAsInteger(Set<?> setToBeSorted, int iSortDirection) throws ExceptionZZZ{
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			if(iSortDirection==SetUtilZZZ.iSORT_DIRECTION_ASCENDING) {
				listReturn = SetUtilZZZ.sortAsInteger(setToBeSorted);
			}else if (iSortDirection==SetUtilZZZ.iSORT_DIRECTION_DESCENDING) {
				listReturn = SetUtilZZZ.sortAsIntegerReversed(setToBeSorted);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Only Ascending or Descending as Constant 1 or -1 is allowed. Value is ='" + iSortDirection + "'",  iERROR_PARAMETER_VALUE, SetUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsInteger(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<?>sortAsIntegerReversed(Set<?> setToBeSorted){
		List<?> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList, Collections.reverseOrder());//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 * @throws ExceptionZZZ 
	 */
	public static List<Integer>sortToInteger(Set<?> setToBeSorted, int iSortDirection) throws ExceptionZZZ{
		List<Integer> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			if(iSortDirection==SetUtilZZZ.iSORT_DIRECTION_ASCENDING) {
				listReturn = SetUtilZZZ.sortToInteger(setToBeSorted);
			}else if (iSortDirection==SetUtilZZZ.iSORT_DIRECTION_DESCENDING) {
				listReturn = SetUtilZZZ.sortToIntegerReversed(setToBeSorted);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Only Ascending or Descending as Constant 1 or -1 is allowed. Value is ='" + iSortDirection + "'",  iERROR_PARAMETER_VALUE, SetUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * Merke: Hier wird der Datentyp nach Integer geändert.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<Integer>sortToInteger(Set<?> setToBeSorted){
		List<Integer> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
						
			//      Falls hier dann der Datentyp nicht passt... wird der Fehler geworfen...
			List<Integer> numbersList = SetUtilZZZ.toListInteger(setToBeSorted);					
			Collections.sort(numbersList);//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	/**Grund: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
	 * Nur so kann man z.B. das KeySet einer HashMap sortiert durchgehen.
	 * Merke: Hier ist die Sortierung in "reverseOrder", also anders als die natürliche Sortierung. 
	 * Merke: Hier wird der Datentyp nach Integer geändert.
	 * @param setToBeSorted
	 * @return
	 * @author Fritz Lindhauer, 23.02.2020, 08:47:15
	 */
	public static List<Integer>sortToIntegerReversed(Set<?> setToBeSorted){
		List<Integer> listReturn = null;
		main:{
			if(setToBeSorted==null)break main;
			
			//Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list
			Collections.sort(numbersList, Collections.reverseOrder());//Sort the list
			listReturn = numbersList;
		}//end main:
		return listReturn;
	}
	
	//################################
	public static List<String>asListString(Set<String>setString){
		List<String> listStringReturn = null;
		main:{
			if(setString == null) break main;

			listStringReturn =new ArrayList<String>(setString) ;        //set -> list
		}//end main:
		return listStringReturn;
	}
	
	/**
	 *Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			//HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			//List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list			
			//Aber: Eine automatische Umwandlung nach Integer ist das noch nicht. Das muss man von Hand machen.
	 * @param setIn
	 * @return
	 * @author Fritz Lindhauer, 04.03.2020, 20:07:01
	 */
	public static List<String>toListString(Set<?>setIn){
		List<String> listStringReturn = null;
		main:{
			if(setIn == null) break main;

			listStringReturn = new ArrayList<String>();
			for(Object objTemp : setIn) {				
				String sTemp = null;
				if(objTemp instanceof Integer) {
					sTemp = objTemp.toString();
				}else if(objTemp instanceof String) {
					sTemp = objTemp.toString();
				}else {
					sTemp = objTemp.toString();
				}
				listStringReturn.add(sTemp);
			}			       
		}//end main:
		return listStringReturn;
	}
		
	public static Set<String>toString(Set<Integer>setIntIn){
		Set<String> setStrReturn = null;
		main:{
			if(setIntIn == null) break main;
			
			HashSet<String> hsStr = new HashSet<String>();
			for(Integer intTemp : setIntIn) {
				String sTemp=intTemp.toString();
				hsStr.add(sTemp);								
			}
			setStrReturn = hsStr;
		}//end main:
		return setStrReturn;
	}
	
	
	//################################
	public static List<Integer>asListInteger(Set<Integer>setInt){
		List<Integer> listIntReturn = null;
		main:{
			if(setInt == null) break main;

			listIntReturn =new ArrayList<Integer>(setInt) ;        //set -> list
		}//end main:
		return listIntReturn;
	}
	
	/**
	 *Aber: Die Sortierung ist im Set nicht sichergestellt. Darum explizit sortieren.
			//HashSet<Integer> hsKey = new HashSet<Integer>((Collection<? extends Integer>) setToBeSorted);//Ist wohl notwendig: https://stackoverflow.com/questions/31279495/how-to-convert-from-an-immutable-set-to-a-hashset
			//List<Integer> numbersList = new ArrayList<Integer>(hsKey) ;        //set -> list			
			//Aber: Eine automatische Umwandlung nach Integer ist das noch nicht. Das muss man von Hand machen.
	 * @param setIn
	 * @return
	 * @author Fritz Lindhauer, 04.03.2020, 20:05:12
	 */
	public static List<Integer>toListInteger(Set<?>setIn){
		List<Integer> listIntReturn = null;
		main:{
			if(setIn == null) break main;

			listIntReturn = new ArrayList<Integer>();
			for(Object objTemp : setIn) {				
				Integer intTemp = null;
				if(objTemp instanceof Integer) {
					intTemp = (Integer) objTemp;
				}else if(objTemp instanceof String) {
					intTemp = new Integer((String) objTemp);
				}else {
					intTemp = new Integer((int)objTemp);
				}
				listIntReturn.add(intTemp);
			}			       
		}//end main:
		return listIntReturn;
	}
		
	public static Set<Integer>toInteger(Set<String>setStrIn){
		Set<Integer> setIntReturn = null;
		main:{
			if(setStrIn == null) break main;
			
			HashSet<Integer> hsInt = new HashSet<Integer>();
			for(String stemp : setStrIn) {
				Integer intTemp=new Integer(stemp);
				hsInt.add(intTemp);								
			}
			setIntReturn = hsInt;
		}//end main:
		return setIntReturn;
	}
}
