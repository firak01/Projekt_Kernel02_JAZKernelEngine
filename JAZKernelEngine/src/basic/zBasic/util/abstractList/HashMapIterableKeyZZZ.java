package basic.zBasic.util.abstractList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

public class HashMapIterableKeyZZZ<T,X>  extends AbstractObjectWithExceptionZZZ implements ICollectionConstantZZZ, Iterable<T>{
	private HashMap<T,X> hmOriginal=null;
	private HashMapIndexedObjectZZZ<Integer,T> hmIndexedKey=null;//also integer als Sortierkrierium , X als originaler Key
	
	public void put(T arg0, X arg1)throws ExceptionZZZ {	
		main:{
			if(arg0==null) {
				ExceptionZZZ ez = new ExceptionZZZ("null key element. Index can not be set.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				  //doesn�t work. Only works when > JDK 1.4
				  //Exception e = new Exception();
				  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
				  throw ez;
			}
			
			HashMapIndexedObjectZZZ<Integer,T> hmIndexed = this.getHashMapIndexedKey();
			hmIndexed.put(arg0);
			
			HashMap<T,X> hmOriginal = this.getHashMap();
			hmOriginal.put(arg0, arg1);
									
		}//end main:			
	}

	public T getKeyFirst() throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedObjectZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyFirst();
			
		}//end main:
		return objReturn;
	}
	public X getValueFirst() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objIndex = this.getKeyFirst();
			if(objIndex==null) break main;
			
			objReturn = this.getValueByIndex(objIndex);

		}//end main:
		return objReturn;
	}
	
	public T getKeyLast()  throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedObjectZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyLast();
			
		}//end main:
		return objReturn;
	}
	public X getValueLast() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objIndex = this.getKeyLast();
			if(objIndex==null) break main;
			
			objReturn = this.getValueByIndex(objIndex);
			
		}//end main:
		return objReturn;
	}
	public T getKeyNext()  throws ExceptionZZZ {
		T objReturn = null;
		main:{
			
			HashMapIndexedObjectZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();
			objReturn = (T) hmIndex.getKeyNext();
			
		}//end main:
		return objReturn;
	}
	public X getValueNext() throws ExceptionZZZ {
		X objReturn = null;
		main:{
			T objIndex = this.getKeyNext();
			if(objIndex==null) break main;
			
			objReturn = this.getValueByIndex(objIndex);
			
		}//end main:
		return objReturn;
	}
	
	public X getValue(T objKey) {
		X objReturn = null;
		main:{
			if(objKey==null)break main;
			
			HashMap<T,X> hm = this.getHashMap();
			if(hm==null )break main;
			
			objReturn = hm.get(objKey);
		}//end main;
		return objReturn;
	}
	
	public X getValueByIndex(int iIndex) throws ExceptionZZZ {
		X objReturn = null;
		main:{
			if(iIndex <= -1) break main;
			
			T intIndex = (T) new Integer(iIndex);
			objReturn = this.getValueByIndex(intIndex);			
		}//end main;
		return objReturn;
	}
	
	public X getValueByIndex(T objIndex) throws ExceptionZZZ {
		X objReturn = null;
		main:{
			if (objIndex==null) break main;
						
			//Hole aus der Indexmap den Schluessel aus der gewuenschten Postion (hier: Erster Index, also 0);
			HashMapIndexedObjectZZZ<Integer,T> hmIndex = this.getHashMapIndexedKey();			
			Object objKeyTemp = hmIndex.getValue((Integer) objIndex);
			X objKey = (X) objKeyTemp;
			
			//Hole mit dem Schluessel den Wert aus der Originalen HashMap.
			HashMap<T,X>hmOriginal =this.getHashMap();
			objReturn = hmOriginal.get(objKey);
		}//end main;
		return objReturn;
	}
	
	
	//### GETTER / SETTER
	public HashMap<T,X>getHashMap() {
		if(this.hmOriginal==null) {
			this.hmOriginal=new HashMap<T,X>();
		}
		return this.hmOriginal;
	}
	/** Ist private, weil es von aussen nie gesetzt werden darf, ohne die IndexWerte auch anzupassen.
	 * @param hmOriginal
	 * @author Fritz Lindhauer, 02.03.2020, 08:03:50
	 */
	private void setHashMap(HashMap<T,X> hmOriginal) {
		this.hmOriginal=hmOriginal;
	}
	
	public HashMapIndexedObjectZZZ<Integer,T>getHashMapIndexedKey(){
		if(this.hmIndexedKey==null) {
			try {
			this.hmIndexedKey=new HashMapIndexedObjectZZZ<Integer,T>();	
			}catch(ExceptionZZZ ez) {
				this.setExceptionObject(ez);
				String sError = ez.getMessageLast();
				try {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": " + sError);
				} catch (ExceptionZZZ e) {					
					e.printStackTrace();
				}
			}
		}
		return this.hmIndexedKey;
	}
	private void setHashMapIndexedKey(HashMapIndexedObjectZZZ<Integer,T>hmIndexedKey) {
		this.hmIndexedKey=hmIndexedKey;
	}
	
	public VectorZZZ<Integer> getVectorIndex() {
		VectorZZZ<Integer> vecReturn = null;
		main:{
			HashMapIndexedObjectZZZ<Integer,T> hmIndexed = this.getHashMapIndexedKey();		
			if(hmIndexed!=null) {
				vecReturn = hmIndexed.getVectorIndex();
			}
		}//end main:
		return vecReturn;
	}	
	
	public HashMap<Integer,T> getHashMapIndex(){
		HashMap<Integer,T> hmReturn = null;
		main:{
			HashMapIndexedObjectZZZ<Integer,T> hmIndexed = this.getHashMapIndexedKey();		
			if(hmIndexed!=null) {
				hmReturn = hmIndexed.getHashMap();
			}
		}//end main:
		return hmReturn;
	}
	
	
	
	//###################
	@Override
	public Iterator<T> iterator() {
		 Iterator<T> it = new Iterator<T>() {
	        	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
	        	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
	        	
	        	
	            @Override
	            public boolean hasNext() {
	            	boolean bReturn = false;
	            	main:{
		            	VectorZZZ<Integer> vec = getVectorIndex();
		            	if(vec==null)break main;
		            	if(!vec.hasAnyElement())break main;
		            	
		            	
		            	Integer intLast = (Integer) vec.lastElement();
		            		            
		            	iIndexWatched = iIndexWatched+1;//das nächste Element halt, ausgehend von -1
		            	Integer intNext = new Integer(iIndexWatched);
		            	
		            	Object objNext = null;
		            	HashMap<Integer,T> hmIndex = getHashMapIndex();
		            	if(hmIndex!=null) {
		            		objNext = hmIndex.get(intNext);
		            	}
		            	bReturn = iIndexWatched <= intLast.intValue() && objNext != null;	            	
	            	}//end main:
	            	return bReturn;
	            }

	            @SuppressWarnings("unchecked")
				@Override
	            public T next() {
	                T objReturn = null;
	                main:{
	                	VectorZZZ<Integer> vec = getVectorIndex();
		            	if(vec==null)break main;
		            	if(!vec.hasAnyElement())break main;
		            	
	                	int iIndexCur = this.iIndexIterator;
	                	if(iIndexCur<this.iIndexWatched) {
	                		iIndexCur = this.iIndexWatched;
	                	}else {
	                		iIndexCur = iIndexCur + 1;
	                	}
	                	
		            	Integer intIndexLast = (Integer) vec.lastElement();
		            	
		            	Object objLast = null;
		            	HashMap<Integer,T> hmIndex = getHashMapIndex();
		            	if(hmIndex!=null) {
		            		objLast = hmIndex.get(intIndexLast);
		            	}
		            	boolean bReturn = iIndexCur <= intIndexLast.intValue() && objLast != null;	 
		            	if(bReturn) {
		            		this.iIndexIterator = iIndexCur;
		            		Integer intIndexCur = new Integer(iIndexCur);
		            		objReturn = (T) hmIndex.get(intIndexCur);
		            	}
	                }//end main:
	            	return objReturn;
	            }

	            @Override
	            public void remove() {
	                throw new UnsupportedOperationException();
	            }
	        };
	        return it;
	    }

	public boolean isEmpty() {
		return this.getHashMap().isEmpty();
	}

	public Set<T> keySet() {
		return this.getHashMap().keySet();
	}

	public int size() {
		return this.getHashMap().size();
	}

	public X get(String sConfig) {
		return this.getHashMap().get(sConfig);
	}
}
