package basic.zBasic.util.abstractList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

/**Klasse dient dazu eine bestehende HashMap zu verwenden, aber das Ergebnis garantiert über eine eindeutigen Schlüsselindex zurückzubekommen.
 * Die Klasse erweitert nicht HashMap, weil sonst alle Methoden auf einen Schlag überschrieben werden müssten.
 * @author Lindhauer
 * @param <T>
 *
 */

//Merke: Wenn man hier <Integr,V> schreibt, defniert man sich sein eigenes Integer und nutzt nicht mehr die Java-Integer Klasse....
public class HashMapIndexedObjectZZZ<K,V>  extends AbstractObjectWithExceptionZZZ<Object> implements Iterable<V>,Serializable{	
	private static final long serialVersionUID = 8726987571013127695L;
	private HashMap<Integer,V> hmIndexed=null;
	private VectorZZZ<Integer> vecIndex=null;	
	private int iIndexCurrent4Vector=-1;  //Der Index des gerade verarbeiteten Keys im Vektor
	
	private String sDummy=null;
	
	public HashMapIndexedObjectZZZ() throws ExceptionZZZ{
		HashMapIndexedNew_(null);
	}
	
	/**  Konstruktor: Die übergebenen Hashtable
	 * @param objHt
	 * @throws ExceptionZZZ
	 * 18.02.2020, 18:37:08, Fritz Lindhauer
	 */
	public HashMapIndexedObjectZZZ(HashMap<Integer,V> hmIndexed) throws ExceptionZZZ{
		HashMapIndexedNew_(hmIndexed);
	}
	
		
	
	private boolean HashMapIndexedNew_(HashMap<Integer,V> hmIndexedIn) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			HashMap<Integer,V>hmIndexed = null;
			check:{
				if(hmIndexedIn!=null) {
					this.setHashMap(hmIndexedIn);
				}
				hmIndexed = this.getHashMap();
			}//END check:
		
			Vector<Integer>vecIndex =  new Vector<Integer>(hmIndexed.keySet());
			this.setVectorIndex(vecIndex);		
		}//END main:
		return bReturn;
	}
	
	public String getDummy() {
		if(this.sDummy==null) {
			this.sDummy = "TESTWERT DEFAULT. Statisch, nicht uebergeben!!";
		}
		return this.sDummy;
	}
	public void setDummy(String sDummy) {		
		this.sDummy = sDummy;
	}
	

	/** Object, Den Key des ersten Objekts zurueckgeben. (Das ist das erste Element des internen Vectors.)
	* Lindhauer; 27.04.2006 08:13:07
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Integer getKeyFirst() throws ExceptionZZZ{
		Integer intReturn = null;
//		try{
			VectorZZZ<Integer> vecIndex = this.getVectorIndex();		
			if(vecIndex!=null){
				if(vecIndex.hasAnyElement()) {
					Integer intIndex  =  (Integer) vecIndex.elementAt(0);			
					this.setIndexCurrent(0); 
					intReturn = intIndex;
					
					//Das wäre der Wert und nicht der Key!!!
//					HashMap<Integer,Object>hmIndexed = this.getHashMap();
//					objReturn = hmIndexed.get(intIndex);					
				}
			}
//		}catch(ExceptionZZZ ez){
//			objReturn=null;
//		}
		return intReturn;
	}
	
	public Object get(Integer intKey) {
		return this.hmIndexed.get(intKey);
	}
	
	
	
	/** Object, Den Key des letzten Objekts zurückgeben. (Das ist das letzte Objekt des inneren Vektors.)
	* Lindhauer; 27.04.2006 08:13:38
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Integer getKeyLast() throws ExceptionZZZ{
		Integer intReturn = null;
//		try{
			VectorZZZ<Integer> vecIndex = this.getVectorIndex();
			if(vecIndex!=null){
				if(vecIndex.hasAnyElement()) {
					Integer intIndex=(Integer) vecIndex.lastElement();
					this.setIndexCurrent(vecIndex.size()-1);
					intReturn = intIndex;
					
					//Das wäre schon der Wert!!
//					HashMap<Integer,Object>hmIndexed = this.getHashMap();
//					objReturn = hmIndexed.get(intIndex);
				}
			}
//		}catch(ExceptionZZZ ez){
//			objReturn=null;
//		}
		 return intReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Integer getKeyNext() throws ExceptionZZZ{
		Integer intReturn = null;
		main:{
			//try{
				VectorZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex!=null){
					if(vecIndex.hasAnyElement()) {
						int iIndexTest = this.getIndexCurrent() + 1;
						if(iIndexTest > vecIndex.size()-1) break main;
				
						Integer intIndex =  (Integer) vecIndex.elementAt(iIndexTest);			
						this.setIndexCurrent(iIndexTest);
						intReturn = intIndex; 
								
						//das wäre schon der Wert!!!
//						HashMap<Integer,Object>hmIndexed = this.getHashMap();
//						objReturn = hmIndexed.get(intIndex);
					}
				}
//			}catch(ExceptionZZZ ez){
//				System.out.println(ez.getDetailAllLast());
//			}
		}
		return intReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public Integer getKeyCurrent() throws ExceptionZZZ{
		Integer intReturn = null;
		main:{
//			try{
				VectorZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex!=null){
					if(vecIndex.hasAnyElement()) {
						int iIndexTest = this.getIndexCurrent() + 1;
						if(iIndexTest > vecIndex.size()-1) break main;
				
						Integer intIndex =  (Integer) vecIndex.elementAt(iIndexTest);
						this.setIndexCurrent(iIndexTest);
						
						intReturn = intIndex;
						
						//das wäre schon der Wert!!!
						//HashMap<Integer,Object>hmIndexed = this.getHashMap();
						//objReturn = hmIndexed.get(intIndex);												 
					}
				}
//			}catch(ExceptionZZZ ez){
//				System.out.println(ez.getDetailAllLast());
//			}
		}
		return intReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable fuer den ersten Key (gemaess der Sortierung) zurueck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public V getValueFirst() throws ExceptionZZZ{
		V objReturn = null;
		main:{
			Integer intKey = this.getKeyFirst();
			if(intKey==null) break main;
					
			HashMap<Integer,V>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(intKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashmap fuer den letzten Key (gemaess der Sortierung) zurueck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public V getValueLast() throws ExceptionZZZ{
		V objReturn = null;
		main:{
			Integer intKey = this.getKeyLast();
			if(intKey==null) break main;
			
			HashMap<Integer,V>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(intKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashmap für den nächsten Key (gemaess der Sortierung) zurueck und schiebt den Index-Wert um 1 weiter.
	* Lindhauer; 27.04.2006 12:39:15
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public V getValueNext() throws ExceptionZZZ{
		V objReturn = null;
		main:{
			Integer intKeyNext = this.getKeyNext();
			if(intKeyNext == null) break main;
			
			HashMap<Integer,V>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(intKeyNext);
		}//end main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert für den aktullen Key/den übergebenen Key zurück, ohne den Indexwert weiterzuschieben.
	* Lindhauer; 27.04.2006 14:05:33
	 * @param objKey
	 * @return Object
	 * @throws ExceptionZZZ 
	 */
	public V getValue(Integer intKey) throws ExceptionZZZ{
		V objReturn = null;
		main:{
			check:{
				if(intKey==null) break main;
				
				//Das Key-Objekt muss ja im Vektor vorhanden sein
				VectorZZZ<Integer> vecIndex = this.getVectorIndex();
				if(vecIndex==null) break main;
				if(!vecIndex.hasAnyElement()) break main;				
				if(!vecIndex.contains(intKey)) break main;
				
				//Nicht vergessen: Den aktuell verarbeiteten Index setzten
				int iIndex = intKey.intValue();
				this.setIndexCurrent(iIndex);
			}//END check:
			
			HashMap<Integer,V>hmIndexed = this.getHashMap();
			objReturn = hmIndexed.get(intKey);
		}//END main:
		return objReturn;
	}
	
	public Object getValue(int iKey) throws ExceptionZZZ{
		Object objReturn = null;
		main:{
			if(iKey<0)break main;
			
			Integer intKey = new Integer(iKey);
			objReturn = this.getValue(intKey);
		}
		return objReturn;
	}
	
	/** void, Setze das Objekt, von dem aus mit getNext() weitergegangen werden kann auf eine andere Indexposition des Vektors.
	* Lindhauer; 27.04.2006 11:18:51
	 * @param objCur
	 * @throws ExceptionZZZ 
	 */
	public void setIndexCurrent(int iIndex) throws ExceptionZZZ{
		main:{
			check:{
				if(iIndex <= -1){
					  ExceptionZZZ ez = new ExceptionZZZ( "iIndex <= -1, but expected to be >= 0", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}
				VectorZZZ<Integer> vexIndex = this.getVectorIndex();
				if(!vecIndex.hasAnyElement()) {
					ExceptionZZZ ez = new ExceptionZZZ("no element exists iIndex cann not be set.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;	
				}
				if(iIndex > vexIndex.size() - 1){
					  ExceptionZZZ ez = new ExceptionZZZ("iIndex > " + (vexIndex.size() -1) + ", but expected to be <= Vector.size()", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;							
				}				
			}//END Check:
	
			this.iIndexCurrent4Vector = iIndex;
		}//END main:
	}
	
	public int getIndexCurrent() {
		return this.iIndexCurrent4Vector;
	}
	
	
	/** Damit wird der Index wieder zurückgesetzt.
	 *  (intern auf -1, aber das darf man mit dem setIndexCurrent nicht...
	 */
	public void resetIndex() {
		this.iIndexCurrent4Vector = -1;
	}
	
	/** HashMap, die Im Kontruktor übergebene HashMap, unveraendert.
	 * Merke: Es macht keine Sinn zu versuchen eine irgendwie sortierte Hashtable zurückzugeben,
	 * 			da HashMap immer beliebig sortiert ist.
	 * @return
	 * @author Fritz Lindhauer, 27.02.2020, 17:19:53
	 */
	public HashMap<Integer,V> getHashMap(){
		if(this.hmIndexed==null) {
			this.hmIndexed = new HashMap<Integer,V>();
		}
		return this.hmIndexed;		
	}
	private void setHashMap(HashMap<Integer,V>hmIndexed) {
		this.hmIndexed=hmIndexed;
	}
	
	public VectorZZZ<Integer> getVectorIndex() {
		if(this.vecIndex==null) {
			this.vecIndex = new VectorZZZ<Integer>();
		}
		return this.vecIndex;
	}
	/**Ist private, da der Vector immer sortiert sein muss, also darf man ihn von Aussen nicht setzen.
	 * @param objV
	 * @author Fritz Lindhauer, 19.02.2020, 08:32:25
	 */
	private void setVectorIndex(Vector<Integer>vecIndexed) {
		VectorZZZ<Integer> vecIndexedZZZ= new VectorZZZ<Integer>(vecIndexed);		
		this.vecIndex=vecIndexedZZZ;
	}
	/**Ist private, da der Vector immer sortiert sein muss, also darf man ihn von Aussen nicht setzen.
	 * @param objV
	 * @author Fritz Lindhauer, 19.02.2020, 08:32:25
	 */
	private void setVectorIndex(VectorZZZ<Integer>vecIndex) {			
		this.vecIndex=vecIndex;
	}
	
	@SuppressWarnings("unchecked")
	public void put(Integer intIndex, V objValue) throws ExceptionZZZ {		
		VectorZZZ<Integer> vecIndex = this.getVectorIndex();
		
		boolean bExists = false;		
		if(vecIndex.hasAnyElement()) {
			bExists = vecIndex.contains(intIndex);
		}
		if(bExists) {
			this.getHashMap().remove(intIndex);			
		}else {			
			//Merke: DER VECTOR MUSS IMMER AUFSTEIGEND SORTIERT SEIN !!!! Das wirkt sich hier beim Updaten aus.			
			if(vecIndex.isLastElementGreaterThan(intIndex)) {
				//Neue Sortierung notwendig
				vecIndex.addSorted(intIndex, ICollectionConstantZZZ.iSORT_DIRECTION_ASCENDING);			
			}else {
				vecIndex.add(intIndex);//einfach anhängen.
			}			
		}
		this.getHashMap().put(intIndex, objValue);
		
	}
	
	@SuppressWarnings("unchecked")
	public void put(V objValue) throws ExceptionZZZ {		
		VectorZZZ<Integer> vecIndex = this.getVectorIndex();
		int iMax = -1;
		
		if(vecIndex.hasAnyElement()) {
			Integer intMax = (Integer) vecIndex.lastElement();
			if(intMax!=null) {
				iMax = intMax.intValue();
			}
		}
		iMax=iMax+1;
		
		Integer intMaxNew = new Integer(iMax);
		vecIndex.add(intMaxNew);//einfach anhängen.
		
		HashMap<Integer,V>hmIndexed = this.getHashMap();
		hmIndexed.put(intMaxNew, objValue);		
	}

	//++++ "Komfortmethoden", die eine normale HashMap auch hat anbieten.
	public int size() {
		int iReturn = 0;
		main:{
			if(this.getHashMap()!=null) {
				iReturn = this.getHashMap().size();
			}
		}//end main;
		return iReturn;
	}
	
	public boolean isEmpty() {
		boolean bReturn = true;
		main:{
			if(this.size()>=1) {
				bReturn=false;
			}
		}//end main;
		return bReturn;
	}
	
	public String toString() {
		String sReturn = null;
		main:{
			HashMap<Integer,V>hm = this.getHashMap();
			sReturn = hm.toString();				
		}//end main:
		return sReturn;
	}

	
	
	
	//### Interfaces ++++++++++++
	
	
//	@Override
//	public Iterator<T> iterator() {
//		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
//				
//		//Iterator<Integer> itIndex = vecIndex.iterator();
//		//return (Iterator<T>) itIndex;
//		
//		//ABER: Man iteriert nicht über den Index, sondern über die Objekte der HashMap.
//		
//	}
	
	
	
	//Hier nicht K , sondern V ueber das man iterieren moechte, aber K ist wohl vom Interface notwendig.
	//Darum "Object" verwenden...
//	@Override
//    public Iterator<K> iterator() {
//        Iterator<K> it = new Iterator<K>() {
//        	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
//        	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
//        	
//        	
//            @Override
//            public boolean hasNext() {
//            	boolean bReturn = false;
//            	main:{
//	            	VectorExtendedZZZ<Integer> vec = getVectorIndex();
//	            	if(vec==null)break main;
//	            	if(!vec.hasAnyElement())break main;
//	            	
//	            	
//	            	Integer intLast = (Integer) vec.lastElement();
//	            		            
//	            	iIndexWatched = iIndexWatched+1;//das nächste Element halt, ausgehend von -1
//	            	Integer intNext = new Integer(iIndexWatched);
//	            	bReturn = iIndexWatched <= intLast.intValue() && getHashMap().get(intNext) != null;	            	
//            	}//end main:
//            	return bReturn;
//            }
//
//            @SuppressWarnings("unchecked")
//			@Override
//            public K next() {
//                K objReturn = null;
//                main:{
//                	VectorExtendedZZZ<Integer> vec = getVectorIndex();
//	            	if(vec==null)break main;
//	            	if(!vec.hasAnyElement())break main;
//	            	
//                	int iIndexCur = this.iIndexIterator;
//                	if(iIndexCur<this.iIndexWatched) {
//                		iIndexCur = this.iIndexWatched;
//                	}else {
//                		iIndexCur = iIndexCur + 1;
//                	}
//                	
//	            	Integer intLast = (Integer) vec.lastElement();
//	            	boolean bReturn = iIndexCur <= intLast.intValue() && getHashMap().get(iIndexCur) != null;	 
//	            	if(bReturn) {
//	            		
//	            		//Das geht eben nicht
////	            		this.iIndexIterator = iIndexCur;
////	            		objReturn = (K) getHashMap().get(iIndexCur);
//	            		
//	            		//Dann geht leider nur das 
//	            		objReturn = (K) new Integer(iIndexCur);
//	            	}
//                }//end main:
//            	return objReturn;
//            }
//
//            @Override
//            public void remove() {
//                throw new UnsupportedOperationException();
//            }
//        };
//        return it;
//    }
	
	//### Interfaces ++++++++++++
	
	
//	@Override
//	public Iterator<T> iterator() {
//		VectorExtendedZZZ<Integer> vecIndex = this.getVectorIndex();
//				
//		//Iterator<Integer> itIndex = vecIndex.iterator();
//		//return (Iterator<T>) itIndex;
//		
//		//ABER: Man iteriert nicht über den Index, sondern über die Objekte der HashMap.
//		
//	}
	
	//Hier nicht K , sondern V ueber das man iterieren moechte, aber K ist wohl vom Interface notwendig.
	@Override
    public Iterator<V> iterator() {
        Iterator<V> it = new Iterator<V>() {
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
	            	bReturn = iIndexWatched <= intLast.intValue() && getHashMap().get(intNext) != null;	            	
            	}//end main:
            	return bReturn;
            }

            @SuppressWarnings("unchecked")
			@Override
            public V next() {
                V objReturn = null;
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
                	
	            	Integer intLast = (Integer) vec.lastElement();
	            	boolean bReturn = iIndexCur <= intLast.intValue() && getHashMap().get(iIndexCur) != null;	 
	            	if(bReturn) {
	            		this.iIndexIterator = iIndexCur;
	            		objReturn = (V) getHashMap().get(iIndexCur);
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
	

}//END Class
