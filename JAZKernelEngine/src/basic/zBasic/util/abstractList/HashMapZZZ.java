package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

/** Diese Klasse erweitert Hashmap um einige Funktionen.
 *   - zum Vergleichen der Inhalte zwischen 2 Hashmaps. Dabei werden unterschiedliche Kriterien herangezogen.
 * @author lindhaueradmin
 *
 */
public class HashMapZZZ<K,V> extends HashMap implements  IObjectZZZ, IHashMapZZZ{
	private static final long serialVersionUID = -576703130885041379L;
		
	protected volatile String sDebugKeyDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	protected volatile String sDebugEntryDelimiterUsed = null;

	protected volatile String sImplodeKeyDelimiterUsed = null; //zum Formatieren einer Implode Ausgabe
	protected volatile String sImplodeEntryDelimiterUsed = null;
	
	public HashMapZZZ(){
	}
	
	public static HashMapZZZ toHashMapExtended(HashMap hm) throws ExceptionZZZ {
		return MapUtilZZZ.toHashMapZZZ(hm);
	}
	
	public static HashMapZZZ clone(HashMap hm) throws ExceptionZZZ {
		HashMapZZZ hmReturn=null;
		main:{
			if(hm==null)break main;
			
			hmReturn=new HashMapZZZ();
			HashMap hmNew = (HashMap) hm.clone();
			hmReturn.putAll(hmNew);
			
		}//end main:
		return hmReturn;
	}
	
	
	/** Versuche für das angegebene Objekt den Schlüsselwert zurückzugeben.
	 *   Da ein Objekt mit mehreren Schlüsseln abgelegt sein kann: "First"...
	 * @param hm
	 * @param value
	 * @return
	 */
	public static Object getKeyFromValueFirst(Map hm, Object value) throws ExceptionZZZ {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
	
	
	
	/** Versuche für das angegebene Objekt die Schlüsselwert zurückzugeben.
	 * @param hm
	 * @param value
	 * @return
	 */
	public static Object[] getKeysFromValue(Map hm, Object value) throws ExceptionZZZ {
		ArrayList<Object>listasObject = new ArrayList<Object>();
		
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        listasObject.add(o);
	      }
	    }
	    return listasObject.toArray();
	  }
	
	/** Versuche für das angegebene Objekt die Schlüsselwert als String zurückzugeben.
	 * @param hm
	 * @param value
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static String[] getKeysAsStringFromValue(Map hm, Object value) throws ExceptionZZZ {
		String[] saReturn = null;
		
		if(hm == null){
			ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
				
		ArrayList<Object>listasObject = new ArrayList<Object>();		
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        listasObject.add(o);
	      }
	    }
	    saReturn = ArrayListUtilZZZ.toStringArray(listasObject);
	    return saReturn;
	  }
	
	public static String[] getKeysAsStringStartingWith(Map<Object, ?> objHashMap, String sValueToFind) throws ExceptionZZZ {		
		return HashMapZZZ.getKeysAsStringStartingWith(objHashMap, sValueToFind, true);
	}
	
	public static String[] getKeysAsStringStartingWith(Map<Object, ?> objHashMap, String sValueToFind, boolean bIgnoreCase) throws ExceptionZZZ {		
		String[] saReturn=null;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sValueToFind)){
				ExceptionZZZ ez = new ExceptionZZZ("String sValueTofind", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			
			ArrayList<String>listasObject = new ArrayList<String>();
			Set<Object> setObject = objHashMap.keySet();
			Iterator<Object>itObject = setObject.iterator();
			
			if(bIgnoreCase) {
				while(itObject.hasNext()) {
					Object obj = itObject.next();
					String stemp = obj.toString();
					if(stemp.startsWith(sValueToFind)) {
						listasObject.add(stemp);
					}
				}
			}else {
				while(itObject.hasNext()) {
					Object obj = itObject.next();
					String stemp = obj.toString();
					if(StringZZZ.startsWithIgnoreCase(stemp,sValueToFind)) {
						listasObject.add(stemp);
					}
				}
			}
			saReturn = ArrayListUtilZZZ.toStringArray(listasObject);			
		}//end main:
		return saReturn;
	}
	
	
	/** Versuche für das angegebene Objekt die Schlüsselwert zurückzugeben.
	 * @param hm
	 * @param value
	 * @return
	 */
	public static String[] getKeysAsStringFromValue(Map<String,Boolean> hm, Boolean value) throws ExceptionZZZ {
		ArrayList<String>listasObject = new ArrayList<String>();
		
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        listasObject.add(o.toString());
	      }
	    }
	    return ArrayListUtilZZZ.toStringArray(listasObject);
	  }
	
	/** Vergleiche die Werte der in der HashMap gespeicherten Objekte. 
	 *   Aber: Gib eine Methode an, über die die Werte der Objekte erst einmal ermittelt werden sollen.
	 *   Die daraus reslutierenden Werte werden mit .equals() miteinander verglichen.
	* @param hm
	* @param sMethod
	* @return
	* 
	* lindhaueradmin; 18.05.2011 06:02:54
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIndexedZZZ getValueDupsIndexedByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ{
		HashMapIndexedZZZ hmIndexed = new HashMapIndexedZZZ();
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			hmIndexed = HashMapIndexedZZZ.getValueDupsIndexedByMethod(objHashMap, sMethodName);
		}//end main
		return hmIndexed;
	}
	
	
	/** Vergleiche zwei HashMaps bzgl. der Size
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:38:25
	 */
	public boolean isSmallerThan(HashMap objHashMap) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			HashMap objHashMapExt = HashMapZZZ.minElements(this, objHashMap);
			if(objHashMapExt==null) break main;
			if(objHashMapExt.equals(this)) bReturn = true;
		}//end main
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:38:47
	 */
	public boolean isBiggerThan(HashMap objHashMap) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			HashMap objHashMapExt = HashMapZZZ.maxElements(this, objHashMap);
			if(objHashMapExt==null) break main;
			if(objHashMapExt.equals(this)) bReturn = true;
		}//end main
		return bReturn;
	}
	
	public boolean isEqualToByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			
			
			//###############################
			String sMethod = null; //F�r das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;        //F�r das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			try {
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gleich ist.
			boolean	btemp = this.isSameSize(objHashMap);
			if(!btemp){
				bReturn = false;
				break main;
			}
			
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			//1. �u�ere Schleife: Nimm daf�r "das von aussen kommende Vergleichsobjekt"
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				
				
				//+++ Suche nun nach der Methode in den beiden Objekten.
				//Merke: In der Reflection API Paramter f�r die MEthode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestInt; //F�r das Exception Handling
				Method mInt = Mopex.getSupportedMethod(objTestInt.getClass(), sMethodName, null);
				
				sMethod = sMethodName; //Für das Exception Handling
				obj = objTestExt; //Für das Exception Handling
				Method mExt = Mopex.getSupportedMethod(objTestExt.getClass(), sMethodName, null);
				
				if(mInt==null | mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in one of the compared Objects. Key: " + strKeyExt , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			
				
				
				//+++ Rufe die Methode auf beiden Objekten auf. Das Ergebnis sind wiederrum Objekte.
				Object objTestErgInt = null;
				Object objTestErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objTestInt; //F�r das Exception Handling
				objTestErgInt = mInt.invoke(objTestInt, null);
				if(objTestErgInt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgInt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				obj = objTestExt; //F�r das Exception Handling
				objTestErgExt = mExt.invoke(objTestExt, null);
				if(objTestErgExt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgExt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objTestErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objTestErgExt.getClass(), "equals", classaParam);
				
			
				Object objErgEquals = mEqualsExt.invoke(objTestErgExt, new Object[] {objTestErgInt});
				if(objErgEquals!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
					if(objErgEquals.toString().equals("false")){
						bReturn = false;
						break main; //wenn false, dann die Schleife verlassen
					}
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
					bReturn = false;
					break main; //wenn False, dann die Schleife verlassen.
				}
			}
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException " + sMethod + " in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} 
			
		}//end main:
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size().
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:39:48
	 */
	public boolean isSameSize(HashMap objHashMap) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//Vergleiche zwei Hashmaps, bzgl. der Size()
			bReturn = HashMapZZZ.isSameSize(this, objHashMap);
		}//end main
		return bReturn;
	}
	
	/** Vergleiche zwei HashMaps bzgl. der Size().
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 05.09.2011 09:39:48
	 */
	public static boolean isSameSize(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			if (iSize1 == iSize2) bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/** Vergleicht, ob die Objekte, die in der kleineren Liste gespeichert sind, in der gr��eren Liste vorkommen.
	 *   Achtung: Objekte sind viel mehr als z.B. ein Text, den sie repr�sentieren. Verwende f�r den Vergleich von 'objekt.xyzMEthodenname()' die Methode isSubsetFromByMethod. 
	* @param objHashMap
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 14.04.2011 07:03:00
	 */
	public boolean isSubsetFrom(HashMap objHashMap) throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gr��er oder gleich ist.
			boolean btemp = this.isSmallerThan(objHashMap);
			if(!btemp){
				btemp = this.isSameSize(objHashMap);
				if(!btemp){
					bReturn = false;
					break main;
				}
			}
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			//1. �u�ere Schleife: Nimm daf�r die Hashmap mit den wenigsten elementen, das aktuelle objekt
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				if(!objTestInt.equals(objTestExt)){
					bReturn = false;
					break main;
				}
			}
		}//end main
		return bReturn;
	}
	
	/** Objekte sind viel mehr als z.B. ein Text, den sie repr�sentieren. Verwende diese Methode f�r den Vergleich der beiden Objekte bzgl. des Ergebnisses von 'objekt.xyzMEthodenname()'.
	 *    Gel�st wird dies �ber den 2. Paramter, der dem Methodennamen entspricht, der per Reflection-API in dem Objekt gesucht und aufgerufen wird.
	 *    MERKE: Dabei muss es sich um eine Methode handeln, die KEINEN Parameter erwartet!
	* @param objHashMap
	* @param sMethodName
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 14.04.2011 07:10:50
	 */
	public boolean isSubsetFromByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			//###############################
			String sMethod = null; //F�r das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;        //F�r das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			
			//Objektvorbereitungen Nimm daf�r die Hashmap mit den wahrscheinlich wenigsten elementen: Das aktuelle objekt
			HashMap objHashMapExt = this;
			HashMap objHashMapInt = objHashMap;
			
			//Die "Untersuchungsmethoden":
			Method mExt = null;
			Method mInt = null; 
			
			//Ergebnisobjekte, d.h. hier wird das Ergebnis des Methodenaufrufs reingepackt:
			Object objTestErgInt = null;
			Object objTestErgExt = null;
			
			try {
			//1. Kann ja nur Teilmenge sein, wenn die �bergebene Hashmap gr��er oder gleich ist.
			boolean btemp = this.isSmallerThan(objHashMap);
			if(!btemp){
				btemp = this.isSameSize(objHashMap);
				if(!btemp){
					bReturn = false;
					break main;
				}
			}
		
			//2. Vergleiche zwei Hashmaps, elemtweise.
			
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			Iterator it = objHashMapExt.keySet().iterator();
			while(it.hasNext()){
				String strKeyExt = (String) it.next();
				
				//Test 1: Key vorhanden?
				Object objTestInt = objHashMapInt.get(strKeyExt);
				if(objTestInt==null){
					bReturn = false;
					break main;  //Noch nicht einmal den Key gefunden, also ist die kleinere Hashmap definitiv keine Teilmenge 
				}
				
				//Test 2: Werte gleich?
				Object objTestExt = objHashMapExt.get(strKeyExt);
				
				
				//+++ Suche nun nach der Methode in den beiden Objekten.
				mExt= null;
				mInt = null;
				
				//Merke: In der Reflection API Paramter f�r die MEthode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				
//				!!! Es ist wichtig, das erst das "This" objekt gepr�ft wird. Ist darin die "untersuchungsmethode" nicht vorhanden == > Fehler werfen. Ansonsten ist der Fehler egal.
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestExt; //F�r das Exception Handling
				mExt = Mopex.getSupportedMethod(objTestExt.getClass(), sMethodName, null); //Merke: ..ext... ist das "This" objekt.
				
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objTestInt; //F�r das Exception Handling
				mInt = Mopex.getSupportedMethod(objTestInt.getClass(), sMethodName, null);
				
				if(mInt==null | mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in one of the compared Objects. Key: " + strKeyExt , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			
				
				
				//+++ Rufe die Methode auf beiden Objekten auf. Das Ergebnis sind wiederrum Objekte.
				objTestErgInt = null;
				objTestErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				
				obj = objTestExt; //F�r das Exception Handling
				objTestErgExt = mExt.invoke(objTestExt, null);
				if(objTestErgExt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgExt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objTestInt; //F�r das Exception Handling
				objTestErgInt = mInt.invoke(objTestInt, null);
				if(objTestErgInt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objTestErgInt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					break main;
				}
				
			
				
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objTestErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objTestErgExt.getClass(), "equals", classaParam);
				
				Object objErgEquals = mEqualsExt.invoke(objTestErgExt, new Object[] {objTestErgInt});
				if(objErgEquals!=null){
					//System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
					if(objErgEquals.toString().equals("false")){
						bReturn = false;
						break main; //wenn false, dann die Schleife verlassen
					}
				}else{
					//System.out.println("Methode " + sMethod + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
					bReturn = false;
					break main; //wenn False, dann die Schleife verlassen.
				}
			}
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei '" + sMethod + "' in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				//FGL 20131223: Wenn es die Vergleichsmethode im aufrufenden Object nicht vorhanden ist, den Fehler werfen, ansonsten sind die Objekte halt nicht gleich un den Fehler unterdr�cken.
				if(mExt==null){  //Merke: mExt ist die Methode des "This" Objektes. Darum ist es wichtig erst mExt versuchen zu holen und dann mInt. Weil mInt = null soll keinen Fehler werfen.
				   ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException '" + sMethod + "' in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
				}else{
					bReturn = false;
					break main;
				}
			} 
		}//end main
		return bReturn;
	}
	
	
	/** Gibt von 2 �bergebenen HashMaps diejenige mit den wenigsten Elementen zur�ck.
	 *    Bzw. null, wenn beide HashMaps gleich viele Elemente haben.
	* @param objHashMap1
	* @param objHashMap2
	* @return
	* 
	* lindhaueradmin; 10.04.2011 09:34:03
	 * @throws ExceptionZZZ 
	 */
	public static HashMap minElements(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ {
		HashMap objReturn = null;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			int iResult = MathZZZ.min(iSize1, iSize2);
			if(iResult==iSize1 & iResult == iSize2) break main; //null zur�ckgeben, wenn beide Listen gleich viele Elemente haben.
			if(iResult==iSize1){
				objReturn = objHashMap1;
			}else if(iResult==iSize2){
				objReturn = objHashMap2;
			}
			
		}//end main:
		return objReturn;
	}
	
	/** Gibt von 2 �bergebenen HashMaps diejenige mit den meisten Elementen zur�ck.
	 *    Bzw. null, wenn beide HashMaps gleich viele Elemente haben.
	* @param objHashMap1
	* @param objHashMap2
	* @return
	* 
	* lindhaueradmin; 10.04.2011 09:34:03
	 * @throws ExceptionZZZ 
	 */
	public static HashMap maxElements(HashMap objHashMap1, HashMap objHashMap2) throws ExceptionZZZ {
		HashMap objReturn = null;
		main:{
			if(objHashMap1== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap1 to compare'", iERROR_PARAMETER_MISSING,  HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(objHashMap2== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap2 to compare'", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			//###################
			int iSize1 = objHashMap1.size();
			int iSize2 = objHashMap2.size();
			
			int iResult = MathZZZ.max(iSize1, iSize2);
			if(iResult==iSize1 & iResult == iSize2) break main; //null zur�ckgeben, wenn beide Listen gleich viele Elemente haben.
			if(iResult==iSize1){
				objReturn = objHashMap1;
			}else if(iResult==iSize2){
				objReturn = objHashMap2;
			}
			
		}//end main:
		return objReturn;
	}
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static Map sortByEntryValue(Map map) throws ExceptionZZZ {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });
	     
	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 	
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static Map sortByEntryInteger(Map map) throws ExceptionZZZ {
	     List list = new LinkedList(map.entrySet());
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
	     
	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	} 
	
	/**Sortiere die Map. !!! Die Werte m�ssen vergleichbar sein!!!
	 *   http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
	* @param map
	* @return
	* 
	* lindhaueradmin; 22.05.2011 08:54:37
	 */
	static void sortByKeyValue(Map map) throws ExceptionZZZ {
	     List list = new LinkedList(map.keySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue()) .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });
	} 
	
	//### aus IOutputNormedZZ
	@Override
	public String getDebugEntryDelimiter() throws ExceptionZZZ {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) throws ExceptionZZZ {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}
	
	public String getDebugKeyDelimiter() throws ExceptionZZZ {
		String sKeyDelimiter;
		if(this.sDebugKeyDelimiterUsed==null){
			sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
		}else{
			sKeyDelimiter = this.sDebugKeyDelimiterUsed;
		}
		return sKeyDelimiter;
	}
	
	@Override
	public void setDebugKeyDelimiter(String sEntryDelimiter) throws ExceptionZZZ {
		this.sDebugKeyDelimiterUsed = sEntryDelimiter;
	}
	
	//### aus IHashMapExtendedZZZ
	@Override
	public String getImplodeEntryDelimiter() throws ExceptionZZZ{
		String sEntryDelimiter;			
		if(this.sImplodeEntryDelimiterUsed==null){
			sEntryDelimiter = IHashMapZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sImplodeEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setImplodeEntryDelimiter(String sEntryDelimiter) throws ExceptionZZZ {
		this.sImplodeEntryDelimiterUsed = sEntryDelimiter;
	}
	
	public String getImplodeKeyDelimiter() throws ExceptionZZZ {
		String sKeyDelimiter;
		if(this.sImplodeKeyDelimiterUsed==null){
			sKeyDelimiter = IHashMapZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT;
		}else{
			sKeyDelimiter = this.sImplodeKeyDelimiterUsed;
		}
		return sKeyDelimiter;
	}
	
	@Override
	public void setImplodeKeyDelimiter(String sImplodeDelimiter) throws ExceptionZZZ {
		this.sImplodeKeyDelimiterUsed = sImplodeDelimiter;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 * @throws ExceptionZZZ 
	 */
	/* (non-Javadoc)
	 * @see basic.zBasic.IOutputDebugNormedZZZ#computeDebugString()
	 */
	@Override
	public String computeDebugString() throws ExceptionZZZ{
		return this.computeDebugString((String)null, (String)null);		
	}
	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 *  Merke: Beim Arbeiten mit der Scanner-Klasse um z.B. Eingaben entgegenzunehmen, sollte man 
	 *         dies verwenden, sonst wird nach jedem Wort ein Zeilenumbruch (Sprich eine neue Eingabe gemacht).
	 * @param sKeyDelimiterIn
	 * @param sEntryDelimiterIn
	 * @return
	 * @author Fritz Lindhauer, 21.10.2022, 09:56:44
	 */
	/* (non-Javadoc)
	 * @see basic.zBasic.IOutputDebugNormedZZZ#computeDebugString(java.lang.String)
	 */
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
		return this.computeDebugString(sEntryDelimiter, (String)null);
	}
	
	@Override
	public String computeDebugString(String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = this.getDebugKeyDelimiter();
			}else {
				sKeyDelimiter = sKeyDelimiterIn;
			}
			
			String sEntryDelimiter; 
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = this.getDebugEntryDelimiter();
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
			
			sReturn = HashMapUtilZZZ.computeDebugString(this, sKeyDelimiter, sEntryDelimiter);
			
		}//end main
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String computeDebugString(HashMap hmDebug) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeDebugString(hmDebug, null, null);
	}
	
	public static String computeDebugString(HashMap hmDebug, String sEntryDelimiterIn) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeDebugString(hmDebug, null, sEntryDelimiterIn);
	}
	
	public static String computeDebugString(HashMap hmDebug, String sKeyDelimiterIn, String sEntryDelimiterIn) throws ExceptionZZZ{
		
		String sKeyDelimiter;
		if(sKeyDelimiterIn==null){
			sKeyDelimiter = IHashMapZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
		}else {
			sKeyDelimiter = sKeyDelimiterIn;
		}
		
		String sEntryDelimiter; 
		if(sEntryDelimiterIn==null){
			sEntryDelimiter = IHashMapZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = sEntryDelimiterIn;
		}
		
		return HashMapUtilZZZ.computeDebugString(hmDebug, sKeyDelimiter, sEntryDelimiter);
	}
	
	//#######################################################################
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	* @return
	* 
	* lindhauer; 08.08.2011 10:39:40
	 */
	/* (non-Javadoc)
	 * @see basic.zBasic.IOutputImplodeNormedZZZ#computeImplodeString()
	 */
	@Override
	public String computeImplodeString() throws ExceptionZZZ {
		return this.computeImplodeString((String)null, (String)null);		
	}
	

	/** Aufbereitete Ausgabe der Daten als String, mit Zeilenumbruch fuer jeden neuen Eintrag.
	 *  Merke: Beim Arbeiten mit der Scanner-Klasse um z.B. Eingaben entgegenzunehmen, sollte man 
	 *         dies verwenden, sonst wird nach jedem Wort ein Zeilenumbruch (Sprich eine neue Eingabe gemacht).
	 * @param sKeyDelimiterIn
	 * @param sEntryDelimiterIn
	 * @return
	 * @author Fritz Lindhauer, 21.10.2022, 09:56:44
	 */
	/* (non-Javadoc)
	 * @see basic.zBasic.IOutputImplodeNormedZZZ#computeImplodeString(java.lang.String)
	 */
	@Override
	public String computeImplodeString(String sEntryDelimiter) throws ExceptionZZZ {
		return this.computeImplodeString(sEntryDelimiter, (String)null);
	}
	
	@Override
	public String computeImplodeString(String sEntryDelimiterIn, String sKeyDelimiterIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			String sKeyDelimiter;
			if(sKeyDelimiterIn==null){
				sKeyDelimiter = this.getImplodeKeyDelimiter();
			}else {
				sKeyDelimiter = sKeyDelimiterIn;
			}
			
			String sEntryDelimiter; 
			if(sEntryDelimiterIn==null){
				sEntryDelimiter = this.getImplodeEntryDelimiter();
			}else {
				sEntryDelimiter = sEntryDelimiterIn;
			}
			
			sReturn = HashMapUtilZZZ.computeImplodeString(this, sEntryDelimiter, sKeyDelimiter);
			
		}//end main
		return sReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String computeImplodeString(HashMap hmImplode) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeImplodeString(hmImplode, null, null);
	}
	
	public static String computeImplodeString(HashMap hmImplode, String sEntryDelimiterIn) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeImplodeString(hmImplode, sEntryDelimiterIn, null);
	}
	
	public static String computeImplodeString(HashMap hmImplode, String sKeyDelimiterIn, String sEntryDelimiterIn)  throws ExceptionZZZ{
		return HashMapUtilZZZ.computeImplodeString(hmImplode, sEntryDelimiterIn, sKeyDelimiterIn);
	}
	//#######################################################################
	
	
	/** Entferne aus der HashMap den Eintrag an der uebergebenen Indexposition.
	 * Merke: Man kann nicht nach der beim Befuellen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public void removeByIndex(int iIndex) throws ExceptionZZZ {
		main:{
		if(iIndex<0) break main;
		if(iIndex > this.size()-1) break main;
		
		Set setKey = this.keySet();
		Iterator it = setKey.iterator();
		int icount = -1;
		while(it.hasNext()){
			icount++;
			
				Object obj = it.next();
				if(icount==iIndex){
				 	this.remove(obj);
				 break main;
				}
			}//end while itInner.hasnext()
		}//end main
	}
	
	
	/** Gib den Key an der �bergebenen Indexposition zur�ck.
	 * Merke: Man kann nicht nach der beim Bef�llen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public Object getKeyByIndex(int iIndex) throws ExceptionZZZ {
		Object objReturn = null;
		main:{
			if(iIndex<0) break main;
			if(iIndex > this.size()-1) break main;
			
			Set setKey = this.keySet();
			Iterator it = setKey.iterator();
			int icount = -1;
			while(it.hasNext()){
				icount++;
				
					Object obj = it.next();
					if(icount==iIndex){
					 objReturn = obj;
					 break main;
					}
				}//end while itInner.hasnext()
				
			
		} //end main
		return objReturn;
	}
	
	public String[] getKeysAsStringStartingWith(String sValueToFind) throws ExceptionZZZ {
		return this.getKeysAsStringStartingWith(sValueToFind, true);
	}
	public String[] getKeysAsStringStartingWith(String sValueToFind, boolean bIgnoreCase) throws ExceptionZZZ {
		return HashMapZZZ.getKeysAsStringStartingWith(this, sValueToFind, bIgnoreCase);		
	}
	
	/** Gib den Wert an der uebergebenen Indexposition zur�ck.
	 * Merke: Man kann nicht nach der beim Befuellen angegebenen Reihenfolge vorgehen, bzw. den gleichen Index erwarten, da die Indexposition in einer HashMap beliebig ist.
	* @param iIndex; Der Index beginnt mit 0.
	* 
	* lindhauer; 05.09.2011 09:41:21
	 */
	public Object getValueByIndex(int iIndex) throws ExceptionZZZ {
		Object objReturn = null;
		main:{
			if(iIndex<0) break main;
			if(iIndex > this.size()-1) break main;
			
			Set setKey = this.keySet();
			Iterator it = setKey.iterator();
			int icount = -1;
			while(it.hasNext()){
				icount++;
				
					Object obj = it.next();
					if(icount==iIndex){
					 objReturn = this.get(obj);
					 break main;
					}
				}//end while itInner.hasnext()
				
			
		} //end main
		return objReturn;
	}

	
	//### asus IHashMapExtendedZZZ
	
	/**Idee aus der HashMap mal eben Schnell einen einzeiligen String machen zu k�nnen.
	* @param sDelimSet
	* @param sDelimValue
	* @return
	*/
	
	@Override
	public String toStringImplode() throws ExceptionZZZ{
		return this.toStringImplode(null, null);		
	}
	
	@Override
	public String toStringImplode(String sDelimEntryIn, String sDelimKeyIn) throws ExceptionZZZ{
		return this.computeImplodeString(sDelimEntryIn, sDelimKeyIn);
	}

	
	//#### aus IObjectZZZ
	//Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
//			try {
			return this.clone();
//			}catch(CloneNotSupportedException e) {
//				ExceptionZZZ ez = new ExceptionZZZ(e);
//				throw ez;
//					
//			}
	}
}
