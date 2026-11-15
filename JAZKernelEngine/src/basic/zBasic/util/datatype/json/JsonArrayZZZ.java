package basic.zBasic.util.datatype.json;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**
 *  
 * Merke: JsonArray ist final und kann nicht extend erweitert werden.
 *        Also findet die Erweiterung über die Verwendung eines entsprechenden Objekts als private Variable statt.
 * @author Fritz Lindhauer, 24.03.2021, 09:04:48
 * 
 */
public class JsonArrayZZZ<T> extends AbstractObjectWithExceptionZZZ  implements Iterable<T>{
	private JsonArray objJsonArray = null;
	
	//Für die Collection-Behandlung: //Merke: Analog zur implemtation in HashMapIndexedZZZ
	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
	
	public JsonArrayZZZ(JsonArray objJsonArray) {
		JsonArrayNew_(objJsonArray);
	}
	public JsonArrayZZZ(JsonElement objJsonElement) throws ExceptionZZZ {
		if(objJsonElement == null) {
			ExceptionZZZ ez = new ExceptionZZZ("No JsonElement provided.", iERROR_PARAMETER_MISSING, JsonArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(objJsonElement.isJsonArray()) {
			JsonArray objJsonArray = objJsonElement.getAsJsonObject().getAsJsonArray();
			JsonArrayNew_(objJsonArray);
		}else {			
			ExceptionZZZ ez = new ExceptionZZZ("Provided JsonElement is no JsonArray.", iERROR_PARAMETER_VALUE, JsonArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;			
		}
	}
	public JsonArrayZZZ(String sJsonArray) throws ExceptionZZZ {
		JsonArray objJsonArray = JsonUtilZZZ.toJsonArray(sJsonArray);
		JsonArrayNew_(objJsonArray);
	}
	public JsonArrayZZZ(String[] saJsonArray) throws ExceptionZZZ {
		JsonArray objJsonArray = JsonUtilZZZ.toJsonArray(saJsonArray);
		JsonArrayNew_(objJsonArray);
	}
	
	private boolean JsonArrayNew_(JsonArray objJsonArray) {
		boolean bReturn = false;
		main:{
			this.setJsonArray(objJsonArray);
			bReturn = true;
		}
		return bReturn;
	}
	
	//################## ERWEITERTE METHODEN
	public JsonElement get(int iArrayIndex) {
		JsonElement objReturn = null;
		main:{
			if(iArrayIndex<=-1)break main;
			
			int iLength = this.length();
			if(iArrayIndex>iLength-1)break main;
			
			objReturn = this.getJsonArray().get(iArrayIndex);
		}
		return objReturn;
	}
	
	//################### NEUE METHODEN
	public int length() {
		int iReturn=0;
		main:{
			int iCount=0;
			while(true) {
				try {
					this.getJsonArray().get(iCount);					
					iCount++;
				}catch(IndexOutOfBoundsException ie) {
					iReturn = iCount;
					break;
				}				
			}
		}//end main:
		return iReturn;
	}
		
	//### GETTER / SETTER
	public void setJsonArray(JsonArray objJsonArray) {
		this.objJsonArray = objJsonArray;
	}
	public JsonArray getJsonArray() {
		return this.objJsonArray;
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
				
				int iLength = this.length();
				if(iIndex >= iLength) {
					ExceptionZZZ ez = new ExceptionZZZ("iIndex >= " + iLength + ", but expected to be < iLenght ", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				  	//doesn�t work. Only works when > JDK 1.4
				  	//Exception e = new Exception();
					//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					throw ez;		
				}							
			}//END Check:
	
			this.iIndexIterator = iIndex;
		}//END main:
	}
	
	public int getIndexCurrent() {
		return this.iIndexIterator;
	}
	
	
	//### INTERFACES
	@Override
	public Iterator<T> iterator() {
		 Iterator<T> it = new Iterator<T>() {
	        	private int iIndexIterator=-1; //Der Index des gerade verarbeiteten Keys im Iterator
	        	private int iIndexWatched=-1;//Der Index des gerade mit hasNext() betrachteten Keys im Iterator
	        		        	
	            @Override
	            public boolean hasNext() {	            	
	            	boolean bReturn = false;
	            	main:{ 		    
	            		JsonArray objJsonArray = getJsonArray();
	            		if(objJsonArray==null)break main;
	            			            		
		            	iIndexWatched = iIndexWatched+1;//das nächste Element halt, ausgehend von -1		            	
		            	try {
		            		JsonElement objTemp = objJsonArray.get(iIndexWatched);
		            		bReturn = true;
		            	}catch(IndexOutOfBoundsException ie) {	
		            		iIndexWatched = iIndexWatched-1;
		            	}            	
	            	}//end main:
	            	return bReturn;
	            }

	            @SuppressWarnings("unchecked")
				@Override
	            public T next() {
	                T objReturn = null;
	                main:{
	                	
	                	
	                	int iIndexCur = this.iIndexIterator;
	                	if(iIndexCur<this.iIndexWatched) {
	                		iIndexCur = this.iIndexWatched;
	                	}else {
	                		iIndexCur = iIndexCur + 1;
	                	}
	                	
		            	objReturn = (T) getJsonArray().get(iIndexCur);		            	
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
}
