package basic.zBasic.util.abstractList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;
import java.util.Vector;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

/**Klasse dient dazu eine bestehende Hashtable zu verwenden, aber das Ergebnis ggf. sortiert zurückzubekommen.
 * Die Klasse erweitert nicht Hashtable, weil sonst alle MEthoden auf einen Schlag überschrieben werden müssten.
 * @author Lindhauer
 *
 */
public class HashtableSortedZZZ<T,X>  extends AbstractObjectWithExceptionZZZ{
	//TODO Einen eigenen Comparator im Konstruktor hinzufügbar machen.
	
	private Hashtable objHt;
	private Vector objV;	
	private int iIndex=-1;  //Der Index des gerade verarbeiteten Keys im Vektor
	private String sSortDirectionAlias=null;
	
	private HashMap<String, Boolean>hmFlag = new HashMap<String, Boolean>(); //Neu 20130721
	
	
	/**  Konstruktor: Die �bergebenen Hashtable wird aufsteigend sortiert
	* Lindhauer; 26.04.2006 11:09:59
	 * @param objHt
	 * @throws ExceptionZZZ 
	 */
	public HashtableSortedZZZ(Hashtable objHt) throws ExceptionZZZ{
		super();
		HashtableSortedNew_(objHt,"+");
	}
	
	/** Konstruktor: Die �bergebene Hashtable wird mit abhängig von der sSortDirection sortiert.
	 * bei '+': wird aufsteigend sortiert
	 * bei '-': wird absteigend sortiert (mit Collections.reverseOrder())
	 * 
	* Lindhauer; 27.04.2006 13:09:41
	 * @param objHt
	 * @param sSortDirectionIn
	 * @throws ExceptionZZZ
	 */
	public HashtableSortedZZZ(Hashtable objHt, String sSortDirectionIn) throws ExceptionZZZ{
			HashtableSortedNew_(objHt, sSortDirectionIn);
	}
	
	
	private boolean HashtableSortedNew_(Hashtable objHt, String sSortDirectionIn) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			check:{
				if(objHt==null) break main; //TODO flag auf initiert setzen

				if(sSortDirectionIn==null){
					this.sSortDirectionAlias = "+";
				}else if(sSortDirectionIn.equals("")){
					this.sSortDirectionAlias = "+";					
				}else if(!(sSortDirectionIn.equals("+") || sSortDirectionIn.equals("-"))){
					  ExceptionZZZ ez = new ExceptionZZZ( "sSortDirection=" + sSortDirectionIn +", but expected to be '+' or '-'", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  //doesn�t work. Only works when > JDK 1.4
					  //Exception e = new Exception();
					  //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					  throw ez;		 
				}else{
					this.sSortDirectionAlias = sSortDirectionIn;			
				}
			}//END check:
		
		//
		this.objHt = objHt;
		objV = new Vector(objHt.keySet());
		if(this.sSortDirectionAlias.equals("-")){
			Collections.sort(objV, Collections.reverseOrder());				
		}else{
			Collections.sort(objV);			
		}
		
		
		
		}//END main:
		return bReturn;
	}
	
	/*
	 * // Display (sorted) hashtable.
             for (Enumeration e = v.elements(); e.hasMoreElements();) {
             String key = (String)e.nextElement();
             System.out.println(key+":"+ht.get(key));
             
	 */
	
	
	public HashtableSortedZZZ() {
		// TODO Auto-generated constructor stub
	}

	/** Object, Den Key des ersten Objekts zur�ckgeben. (Das ist das erste Element des internen Vectors.)
	* Lindhauer; 27.04.2006 08:13:07
	 * @return Object
	 */
	public Object getKeyFirst(){
		Object objReturn = null;
		try{
		if(objV!=null){
			objReturn =  objV.elementAt(0);			
			this.setKeyCurrent(0); 			
		}
		
		/*
		Enumeration e = objV.elements();
		if(e.hasMoreElements()){
			objReturn = e.nextElement();
		}		
		*/
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
		if(objV!=null){
			objReturn=objV.lastElement();
			this.setKeyCurrent(objV.size()-1);
		}
		/*
		for ( Enumeration e = objV.elements(); e.hasMoreElements();) {
	             objReturn = e.nextElement();
		  }
		  */
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
			if(iIndexTest > objV.size()-1) break main;
			
			objReturn =  objV.elementAt(iIndexTest);			
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
				if(!objV.contains(objKey)) break main;
								
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
				}else if(iIndex > objV.size() - 1){
					  ExceptionZZZ ez = new ExceptionZZZ("iIndex > " + (objV.size() -1) + ", but expected to be <= Vector.size()", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
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
	public Hashtable getHashtable(){
		return objHt;		
	}
	
	/** Treeset, der interne Vector wird und die Werte aus der Hashtable werden  in eine TreeMap umgewandelt. Dieses ist aber wie gew�nscht sortiert.
	* Lindhauer; 27.04.2006 13:27:12
	 * @return TreeMap
	 */
	public  TreeMap getTreeMap(){
		TreeMap objReturn = null;
		if(this.sSortDirectionAlias.equals("-")){
			objReturn = new TreeMap(Collections.reverseOrder());			
		}else{
			objReturn = new TreeMap();	
		}
				
		main:{
			//TODO GOON: Den Vektor in eine neues Treeset umwandeln und die dazugeh�renden Werte setzen.
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
}//END Class
