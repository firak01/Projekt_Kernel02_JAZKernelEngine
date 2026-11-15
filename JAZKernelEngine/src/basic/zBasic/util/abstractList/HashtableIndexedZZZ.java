package basic.zBasic.util.abstractList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;

/**Klasse dient dazu eine bestehende Hashtable zu verwenden, aber das Ergebnis garantiert über eine eindeutigen Schlüsselindex zurückzubekommen.
 * Die Klasse erweitert nicht Hashtable, weil sonst alle Methoden auf einen Schlag überschrieben werden müssten.
 * @author Lindhauer
 *
 */
public class HashtableIndexedZZZ<T,X>  extends AbstractObjectWithExceptionZZZ{	
	private Hashtable<Integer,Object> objHt=null;
	private VectorZZZ<Integer> objVx=null;	
	private int iIndex=-1;  //Der Index des gerade verarbeiteten Keys im Vektor
	
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	
	public HashtableIndexedZZZ() throws ExceptionZZZ{
		HashtableIndexedNew_(null);
	}
	
	/**  Konstruktor: Die übergebenen Hashtable
	 * @param objHt
	 * @throws ExceptionZZZ
	 * 18.02.2020, 18:37:08, Fritz Lindhauer
	 */
	public HashtableIndexedZZZ(Hashtable<Integer,Object> objHt) throws ExceptionZZZ{
		HashtableIndexedNew_(objHt);
	}
	
		
	
	private boolean HashtableIndexedNew_(Hashtable<Integer,Object> objHtIn) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			Hashtable<Integer,Object>objHt = null;
			check:{
				if(objHtIn!=null) {
					this.setHashtable(objHtIn);
				}
				objHt = this.getHashtable();
			}//END check:
		
			Vector<Integer>objVector =  new Vector<Integer>(objHt.keySet());
			this.setVectorIndex(objVector);		
		}//END main:
		return bReturn;
	}
	

	/** Object, Den Key des ersten Objekts zur�ckgeben. (Das ist das erste Element des internen Vectors.)
	* Lindhauer; 27.04.2006 08:13:07
	 * @return Object
	 */
	public Object getKeyFirst(){
		Object objReturn = null;
		try{
		if(objVx!=null){
			objReturn =  objVx.elementAt(0);			
			this.setKeyCurrent(0); 			
		}

		}catch(ExceptionZZZ ez){
			objReturn=null;
		}
		return objReturn;
	}
	
	/** Object, Den Key des letzten Objekts zurückgeben. (Das ist das letzte Objekt des inneren Vektors.)
	* Lindhauer; 27.04.2006 08:13:38
	 * @return Object
	 */
	public Object getKeyLast(){
		Object objReturn = null;
		try{
		if(objVx!=null){
			objReturn=objVx.lastElement();
			this.setKeyCurrent(objVx.size()-1);
		}

		}catch(ExceptionZZZ ez){
			objReturn=null;
		}
		 return objReturn;
	}
	
	/** Object, Den Key an der nächsten Indexposition holen. Dabei wird der Index im 1 erhöht.
	* Lindhauer; 27.04.2006 14:16:21
	 * @return Object
	 */
	public Object getKeyNext(){
		Object objReturn = null;
		main:{
			try{
			int iIndexTest = this.iIndex + 1;
			if(iIndexTest > objVx.size()-1) break main;
			
			objReturn =  objVx.elementAt(iIndexTest);			
			this.setKeyCurrent(iIndexTest);
			}catch(ExceptionZZZ ez){
				System.out.println(ez.getDetailAllLast());
			}
		}
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable f�r den ersten Key (gem�� der Sortierung) zur�ck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 */
	public Object getValueFirst(){
		Object objReturn = null;
		main:{
			Object objKey = this.getKeyFirst();
			if(objKey==null) break main;
					
			objReturn = this.objHt.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtable f�r den letzten Key (gem�� der Sortierung) zur�ck.
	* Lindhauer; 27.04.2006 11:30:25
	 * @return Object
	 */
	public Object getValueLast(){
		Object objReturn = null;
		main:{
			Object objKey = this.getKeyLast();
			if(objKey==null) break main;
			
			objReturn = this.objHt.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert der Hashtalbe f�r den n�chsten Key (gem�� der Sortierung) zur�ck und schiebt den Index-Wert um 1 weiter.
	* Lindhauer; 27.04.2006 12:39:15
	 * @return Object
	 */
	public Object getValueNext(){
		Object objReturn = null;
		main:{
			Object objKeyNext = this.getKeyNext();
			if(objKeyNext == null) break main;
			
			objReturn = this.objHt.get(objKeyNext);
		}//end main:
		return objReturn;
	}
	
	/** Object, Gibt den Wert f�r den aktullen Key/den �bergebenen Key zur�ck, ohne den Indexwert weiterzuschieben.
	* Lindhauer; 27.04.2006 14:05:33
	 * @param objKey
	 * @return Object
	 */
	public Object getValue(Object objKey){
		Object objReturn = null;
		main:{
			check:{
				if(objKey==null) break main;
				
				//Das Key-Objekt muss ja im Vektor vorhanden sein
				if(!objVx.contains(objKey)) break main;
								
			}//END check:
		
			objReturn = this.objHt.get(objKey);
		}//END main:
		return objReturn;
	}
	
	/** void, Setze das Objekt, von dem aus mit getNext() weitergegangen werden kann auf eine andere Indexposition des Vektors.
	* Lindhauer; 27.04.2006 11:18:51
	 * @param objCur
	 * @throws ExceptionZZZ 
	 */
	public void setKeyCurrent(int iIndex) throws ExceptionZZZ{
		main:{
			check:{
				if(iIndex <= -1){
					  ExceptionZZZ ez = new ExceptionZZZ( "iIndex <= -1, but expected to be >= 0", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}else if(iIndex > objVx.size() - 1){
					  ExceptionZZZ ez = new ExceptionZZZ("iIndex > " + (objVx.size() -1) + ", but expected to be <= Vector.size()", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;							
				}				
			}//END Check:
	
			this.iIndex = iIndex;
		}//END main:
	}
	
	/** Hashtable, die Im Kontruktor �bergebene Hashtable, unver�ndert.
	 * Merke: Es macht keine Sinn zu versuchen eine irgendwie sortierte Hashtable zur�ckzugeben,
	 * 			da Hashtable immer beliebig sortiert ist.
	* Lindhauer; 27.04.2006 13:26:19
	 * @return Hashtable
	 */
	public Hashtable<Integer,Object> getHashtable(){
		if(this.objHt==null) {
			this.objHt = new Hashtable<Integer,Object>();
		}
		return this.objHt;		
	}
	private void setHashtable(Hashtable<Integer,Object>objHt) {
		this.objHt=objHt;
	}
	
	public VectorZZZ<Integer> getVectorIndex() {
		if(this.objVx==null) {
			this.objVx = new VectorZZZ<Integer>();
		}
		return this.objVx;
	}
	/**Ist private, da der Vector immer sortiert sein muss, also darf man ihn von Aussen nicht setzen.
	 * @param objV
	 * @author Fritz Lindhauer, 19.02.2020, 08:32:25
	 */
	private void setVectorIndex(Vector<Integer>objV) {
		VectorZZZ<Integer> objVx = new VectorZZZ<Integer>(objV);		
		this.objVx = objVx;
	}
	
	
	/** Treeset, der interne Vector wird und die Werte aus der Hashtable werden  in eine TreeMap umgewandelt. Dieses ist aber wie gew�nscht sortiert.
	* Lindhauer; 27.04.2006 13:27:12
	 * @return TreeMap
	 */
	public  TreeMap<Integer, Object> getTreeMap(){
		TreeMap objReturn = new TreeMap();	
				
		main:{
			//Den Vektor in eine neues Treeset umwandeln und die dazugehörenden Werte setzen.
			Object objKey = this.getKeyFirst();
			Object objValue = this.getValue(objKey);
			while(objKey!=null){
				objReturn.put(objKey, objValue);
				
				objKey = this.getKeyNext();
				objValue = this.getValue(objKey);				
			}
			
		}
		return objReturn;
	}
	
	@SuppressWarnings("unchecked")
	public void put(Integer intIndex, Object objValue) throws ExceptionZZZ {		
		VectorZZZ<Integer> vecIndex = this.getVectorIndex();
		boolean bExists = vecIndex.contains(intIndex);
		
		if(bExists) {
			this.getHashtable().remove(intIndex);			
		}else {			
			//Merke: DER VECTOR MUSS IMMER AUFSTEIGEND SORTIERT SEIN !!!! Das wirkt sich hier beim Updaten aus.			
			if(vecIndex.isLastElementGreaterThan(intIndex)) {
				//Neue sortierung notwendig
				vecIndex.addSorted(intIndex, ICollectionConstantZZZ.iSORT_DIRECTION_ASCENDING);			
			}else {
				vecIndex.add(intIndex);//einfach anhängen.
			}
			
		}
		this.getHashtable().put(intIndex, objValue);
		
	}

}//END Class
