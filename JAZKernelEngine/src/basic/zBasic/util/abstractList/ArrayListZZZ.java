/*
 * Created on 29.11.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.rule.GenericMatcherArrayListZZZ;
import basic.zBasic.rule.IMatchRuleZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


/**
 * @author Lindhauer
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ArrayListZZZ<T> extends AbstractArrayListZZZ<T> implements IArrayListExtendedZZZ{
	private static final long serialVersionUID = 2859619907770188881L;
		
	/**
	 * @param iCapacityInitial
	 */
	public ArrayListZZZ(int iCapacityInitial) {
		super(iCapacityInitial);			
	}
	public ArrayListZZZ(){		
		super();
	}
	public ArrayListZZZ(T[]obja) {	
		ArrayListExtendedNew_(obja);
	}
	
	private boolean ArrayListExtendedNew_(T[] obja){		
		boolean bReturn = false;
		main:{
			if(obja==null) break main;
			
			for(T obj : obja) {
				this.add(obj);
			}
			bReturn = true;
		}//end main:
				
		return bReturn;
	}//end private constructor	
	
	@SuppressWarnings("unchecked")
	public void addUnique(Object obj) throws ExceptionZZZ{
		main:{
			if(obj==null){
				ExceptionZZZ ez = new ExceptionZZZ("Object'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			
			if(this.contains(obj)) break main;
			this.add((T) obj);			
		}//end main
	}
	
	public void addAllUnique(ArrayList<?> lista) throws ExceptionZZZ {
		main:{
			if(ArrayListUtilZZZ.isEmpty(lista))break main;
			
			for(Object obj : lista) {
				this.addUnique(obj);
			}
		}//end main		
	}
	
	public HashMapIndexedZZZ getValueDupsIndexedByMethod(String sMethodName) throws ExceptionZZZ{
		HashMapIndexedZZZ hmReturn = null;
		main:{
			hmReturn = HashMapIndexedZZZ.getValueDupsIndexedByMethod(this, sMethodName);
		}
		return hmReturn;
	}
	
	/** Entferne Dubletten. Dabei wird die IndexHashMap zum Kennzeichnen der Dubletten verwendet.
	 *    Intern werden die Dubletten in einer einzigen ArrayList zusammengefasst und diese dann gel�scht.
	* @param hm
	* @param hmIndexed
	* @param sFlagRemain "KEEPFIRST" / "KEEPLAST", default ist "KEEPLAST"
	* @throws ExceptionZZZ
	* @return Anzahl der entferneten werte
	* 
	* lindhauer; 17.05.2011 11:37:46
	 */
	public int removeDupsFromByIndex( HashMapIndexedZZZ hmIndexed, String sFlagRemainIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			iReturn = HashMapIndexedZZZ.removeDupsFromByIndex(this, hmIndexed, sFlagRemainIn);
		}//end main
		return iReturn;
	}
	
	public <V, K> ArrayListZZZ<T> filter(K key, IMatchRuleZZZ<T, K> rule) throws ExceptionZZZ {
		 ArrayListZZZ<T> listaReturn=null;
		 main:{
			 if(key == null) {
				ExceptionZZZ ez = new ExceptionZZZ("Key'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;
			 }
			 
			 if(rule==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Rule'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;
			 }
			 
			 ArrayListZZZ<T> values = this;
			 listaReturn = GenericMatcherArrayListZZZ.filter(values, key, rule);
		 }//end main:
		 return listaReturn;
	 }
	
	 public <V, K> ArrayListZZZ<T> filter(K[] keys, IMatchRuleZZZ<T, K> rule) throws ExceptionZZZ {
		 ArrayListZZZ<T> listaReturn=null;
		 main:{
			 if(ArrayUtilZZZ.isNull(keys)) {
				 listaReturn = (ArrayListZZZ<T>) this.clonez();
				 break main;
			 }
			 
			 if(rule==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Rule'", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;
			 }
			 
			 ArrayListZZZ<T> values = this;
			 return GenericMatcherArrayListZZZ.filter(values, keys, rule);
		 }//end main:
		 return listaReturn;
	 }
	
}//end class
