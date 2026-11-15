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
import java.util.Vector;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.IOutputDebugNormedWithKeyZZZ;
import basic.zBasic.IOutputDebugNormedZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadEncryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

/** Diese Klasse erweitert Hashmap um einige Funktionen.
 *   - Es bleibt nur der erste Eintrag bestehen.
 *     wenn man einen weiteren Eintrag fuer den gleichen Schluessel hinzufuegt.
 *     (Daher wollte ich diese Klasse ggfs. auch nennen "HashMapDictionaryZZZ")
 *     
 * - Merke: Klasse HashMapKeepLastZZZ gibt es nicht.
 *          Jede HashMap ist de facto "keep last";
 * @author lindhaueradmin
 *
 */
public class HashMapKeepFirstZZZ<K,V> extends HashMap implements  IConstantZZZ, IObjectZZZ, IHashMapKeepFirstZZZ{
	private static final long serialVersionUID = -576703130885041379L;
	
	//fuer IOutputDebugNormedZZZ
	protected volatile String sDebugEntryDelimiterUsed = null; //zum Formatieren einer Debug Ausgabe
	
	//fuer IOutputDebugNormedWithKeyZZZ
	protected volatile String sDebugKeyDelimiterUsed = null; 
	
	//fuer IHashMapExtendedZZZ
	protected volatile String sImplodeEntryDelimiterUsed = null;
	protected volatile String sImplodeKeyDelimiterUsed = null;
	
	public HashMapKeepFirstZZZ(){
	}
	
	public static HashMapKeepFirstZZZ toHashMapKeepFirst(HashMap hm){
		HashMapKeepFirstZZZ hmReturn=null;
		main:{
			if(hm==null)break main;
			
			hmReturn=new HashMapKeepFirstZZZ();
			hmReturn.putAll(hm);
			
//			Set setKey = hm.keySet();
//			Iterator itKey = setKey.iterator();
//			while(itKey.hasNext());{
//				hmReturn.putAll(m);
//			}
			
		}//end main:
		return hmReturn;
	}
	
	public static HashMapKeepFirstZZZ clone(HashMap hm) {
		HashMapKeepFirstZZZ hmReturn=null;
		main:{
			if(hm==null)break main;
			
			hmReturn=new HashMapKeepFirstZZZ();
			HashMap hmNew = (HashMap) hm.clone();
			hmReturn.putAll(hmNew);
			
		}//end main:
		return hmReturn;
	}
	
	//### aus IObjectZZZ
	//Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
//				try {
			return this.clone();
//				}catch(CloneNotSupportedException e) {
//					ExceptionZZZ ez = new ExceptionZZZ(e);
//					throw ez;			
//				}
	}
		
	
	public static Object put(HashMap<Object, Object> hm, Object key, Object value) throws ExceptionZZZ{
		return HashMapKeepFirstZZZ.putButKeep(hm, key, value);
	}
	
	public static Object putButKeep(HashMap<Object, Object> hm, Object key, Object value) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(hm==null) break main;
			if(key==null) {				
				ExceptionZZZ ez = new ExceptionZZZ( "Key missing", iERROR_PARAMETER_MISSING, HashMapKeepFirstZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			boolean bExists = hm.containsKey(key);	
			if(!bExists) {
				//hm.putIfAbsent(key, value);		    //So eine Funktion mit der Anforderung gibt es also schon in HashMap selbst.
				//aber: Exception in thread "main" java.lang.NoSuchMethodError: java.util.HashMap.putIfAbsent(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
				//Grund: Erst ab Java 1.8.
					
				hm.put(key, value);
				objReturn = value;
			}else {
				objReturn = hm.get(key);
			}
		}//end main:		
		return objReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object put(Object key, Object value) {
		Object objReturn = null;
		
		//ACHTUNG: Hier ruft die HashMap wg. "this" immer wieder diese put(...) Methode selbst auf, darum putButKeep(...) .
		//objReturn = HashMapKeepFirstZZZ.putButKeep(this, key, value);
			
		if(this.containsKey(key)) {
				//mach nix
		}else {
			super.put(key, value);
		}
		return objReturn;				
	}
	
	@Override
	public Object putButKeep(Object key, Object value) throws ExceptionZZZ {
		return HashMapKeepFirstZZZ.putButKeep(this, key, value);
	}
	
	

	//#####################
	@Override
	public String computeDebugString() throws ExceptionZZZ{
		return this.toString();
	}
	
		
	@Override
	public String getDebugEntryDelimiter() {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IOutputDebugNormedZZZ.sDEBUG_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sDebugEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}
	
	@Override
	public void setDebugEntryDelimiter(String sEntryDelimiter) {
		this.sDebugEntryDelimiterUsed = sEntryDelimiter;
	}
	
	//### aus IOutputDebugNormedWithKeyZZZ
	@Override
	public String computeDebugString(String sEntryDelimiter) throws ExceptionZZZ {
		String sKeyDelimiter = this.getDebugKeyDelimiter();
		return this.computeDebugString(sEntryDelimiter, sKeyDelimiter);
	}
	
	@Override
	public String computeDebugString(String sEntryDelimiter,String sKeyDelimiter) throws ExceptionZZZ{
		return HashMapUtilZZZ.computeDebugString(this, sEntryDelimiter, sKeyDelimiter);
	}
	
	public String getDebugKeyDelimiter() {
		String sKeyDelimiter;
		if(this.sDebugKeyDelimiterUsed==null){
			sKeyDelimiter = IOutputDebugNormedWithKeyZZZ.sDEBUG_KEY_DELIMITER_DEFAULT;
		}else{
			sKeyDelimiter = this.sDebugKeyDelimiterUsed;
		}
		return sKeyDelimiter;
	}
	
	@Override
	public void setDebugKeyDelimiter(String sKeyDelimiter) {
		this.sDebugKeyDelimiterUsed = sKeyDelimiter;
	}

	
	//### aus IHashMapExtendedZZZ
	
	//##################################
	//### IMPLODE
	//##################################
	@Override
	public void setImplodeEntryDelimiter(String sEntryDelimiter) throws ExceptionZZZ {
		this.sImplodeEntryDelimiterUsed = sEntryDelimiter;
	}

	@Override
	public String getImplodeEntryDelimiter() throws ExceptionZZZ {
		String sEntryDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sEntryDelimiter = IHashMapZZZ.sIMPLODE_ENTRY_DELIMITER_DEFAULT;
		}else {
			sEntryDelimiter = this.sImplodeEntryDelimiterUsed;
		}
		return sEntryDelimiter;
	}

	@Override
	public void setImplodeKeyDelimiter(String sKeyDelimiter) throws ExceptionZZZ {
		this.sImplodeKeyDelimiterUsed = sKeyDelimiter;
	}

	@Override
	public String getImplodeKeyDelimiter() throws ExceptionZZZ {
		String sKeyDelimiter;			
		if(this.sDebugEntryDelimiterUsed==null){
			sKeyDelimiter = IHashMapZZZ.sIMPLODE_KEY_DELIMITER_DEFAULT;
		}else {
			sKeyDelimiter = this.sImplodeKeyDelimiterUsed;
		}
		return sKeyDelimiter;
	}

	@Override
	public String computeImplodeString() throws ExceptionZZZ {
		String sKeyDelimiter = this.getImplodeKeyDelimiter();
		String sEntryDelimiter = this.getImplodeEntryDelimiter();
		return this.computeDebugString(sEntryDelimiter, sKeyDelimiter);
	}

	@Override
	public String computeImplodeString(String sEntryDelimiter) throws ExceptionZZZ {
		String sKeyDelimiter = this.getDebugKeyDelimiter();
		return this.computeDebugString(sEntryDelimiter, sKeyDelimiter);
	}

	@Override
	public String computeImplodeString(String sEntryDelimiter, String sKeyDelimiter) throws ExceptionZZZ {
		return HashMapUtilZZZ.computeImplodeString(this, sEntryDelimiter, sKeyDelimiter);
	}

	@Override
	public String toStringImplode() throws ExceptionZZZ {
		return this.toStringImplode(null, null);
	}

	@Override
	public String toStringImplode(String sEntryDelimiter, String sKeyDelimiter) throws ExceptionZZZ {
		return this.computeImplodeString(sEntryDelimiter, sKeyDelimiter);
	}	
}
