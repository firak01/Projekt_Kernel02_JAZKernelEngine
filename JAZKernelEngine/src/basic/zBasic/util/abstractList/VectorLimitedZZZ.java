package basic.zBasic.util.abstractList;

import java.util.Collection;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IVectorLimitedZZZ;
import basic.zBasic.LogZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.ReflectCodeZZZ;
import custom.zKernel.Log;

/** Zu beachten, problematisch:
 *  - es werden sofort alle indexposition mit einem Defaultwert belegt.
 *    Damit fuehrt ein .add(...) automatisch immer zu einem Fehler "ungueltige Indexposition".
 *    
 * - somit wird addAll(Collection ...) unsinnig.
 * 
 * - Loesungsansaetze:
 *   a) addAll führt immer ein Replace aus, beginnend von dem gewuenschten Index.
 *   b) man definiert den Vector-Inhalt quasi wie einen CicularBuffer. D.h. wird der hoechste erlaubte Indexwert erreicht faengt man wieder mit Index 0 an.
 *   
 *  - Merke: Der Defaultwert ist das NullObjectZZZ.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 10.10.2024, 12:15:24
 * 
 */
public class VectorLimitedZZZ<T> extends VectorZZZ<T> implements IVectorLimitedZZZ{
	private static final long serialVersionUID = -4483450165450297325L;
	private int iSizeMax=0;
	
	
	public VectorLimitedZZZ(int iSizeMax) throws ExceptionZZZ {
		super();
		this.setSizeMax(iSizeMax);		
		this.reset(); //setze defaultobject... 		
	}
	
	public VectorLimitedZZZ(int iSizeMax, Vector3ZZZ<String> vecInitial) throws ExceptionZZZ{
		super();
		this.setSizeMax(iSizeMax);
		this.resetValues(null);
		
		for (int i=0; i<this.getSizeMax(); i++)
			this.replace(i, vecInitial.elementAt(i));
	}

	//### Aus IResettableValuesWithDefault
	@Override
	public boolean reset() throws ExceptionZZZ{
		//super.reset();//gibt es nicht, da oberste Ebene.
		this.resetValues();
		return true;
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		return this.resetValues(null);
	}
	
	@Override
	public boolean resetValues(Object objDefaultIn) throws ExceptionZZZ {
		Object objDefault = null;
		if(objDefaultIn==null) {
			objDefault = this.getObjectDefaultNew();
		}else {
			objDefault = objDefaultIn;
		}
		
		for(int i=0; i<=this.getSizeMax()-1;i++) {
			if(this.getEntry(i)!=null) {
				this.replace(i, objDefault);
			}else {
				this.add(i, objDefault);
			}
		}	
		return true;
	}
		
	@Override
	public boolean resetValuesWithDefault() throws ExceptionZZZ {
		Object objDefault = this.getObjectDefaultNew();
		return this.resetValues(objDefault);
	}
	
	@Override
	public boolean resetWithDefault(Object objDefault) throws ExceptionZZZ{
		//super.reset(objDefault);//gibt es nicht, da oberste Ebene
		this.resetValues(objDefault);
		return true;
	}
	
	
	
	//## aus IVectorLimitedZZZ
	@Override 
	public Object getObjectDefaultNew() throws ExceptionZZZ {
		return new NullObjectZZZ();
	}
	
	@Override 
	public int sizeMax() {
		return this.iSizeMax;
	}
	
	@Override 
	public int getSizeMax() {
		return this.sizeMax();
	}	
	private void setSizeMax(int iSize) {
		this.iSizeMax = iSize;
	}
	
	@Override
	public boolean isValidSize(int iSize) throws ExceptionZZZ{
		if(iSize>=0) return true;
		if(iSize<=this.sizeMax()) return true;
		
		return false;
	}
	
	@Override
	public boolean ensureValidSize(int iSize) throws ExceptionZZZ{
		if(iSize<0) return false;
		if(iSize<= this.sizeMax()) return true;
		
		ExceptionZZZ ez = new ExceptionZZZ("New Size is invalid: '" + iSize +"'", iERROR_PARAMETER_VALUE, ObjectUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	
	@Override
	public boolean ensureValidIndex(int iIndex) throws ExceptionZZZ{
		boolean bReturn = this.ensureValidSize(iIndex + 1);
		if(bReturn) return true;
		
		ExceptionZZZ ez = new ExceptionZZZ("New Index is invalid: '" + iIndex +"'", iERROR_PARAMETER_VALUE, ObjectUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	
	
	
	
		
	@Override
	public Object replace(int iIndex, Object objIn) throws ExceptionZZZ{
		Object objReturn = null;
		this.ensureValidIndex(iIndex);
		
		Object obj;
		if(objIn==null) {
			obj = this.getObjectDefaultNew();
		}else {
			obj = objIn;
		}
		objReturn = super.replace(iIndex, obj);
		
		return objReturn;		
	}
	
	//### ueberschriebene Vector Originale
	@SuppressWarnings("unchecked")
	@Override
	public void addElement(Object objIn) {
		try {
			this.ensureValidSize(this.sizeNext());
			
			Object obj;
			if(objIn==null) {
				obj = this.getObjectDefaultNew();
			}else {
				obj = objIn;
			}
			super.addElement(obj);
		}catch(ExceptionZZZ ez) {
			try {
				Log.protocol(this, ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Object objIn) {
		boolean bReturn = false;
		try {
			this.ensureValidSize(this.sizeNext());
			
			Object obj;
			if(objIn==null) {
				obj = this.getObjectDefaultNew();
			}else {
				obj = objIn;
			}
			bReturn = super.add(obj);		
			this.iIndexUsedLast = this.size()-1;
			
		}catch(ExceptionZZZ ez) {
			try {
				Log.protocol(this, ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void add(int iIndex, Object objIn) {
		try {
			this.ensureValidSize(this.sizeNext());
			
			Object obj;
			if(objIn==null) {
				obj = this.getObjectDefaultNew();
			}else {
				obj = objIn;
			}
			super.add(iIndex, obj);
			this.iIndexUsedLast = iIndex;
			
		}catch(ExceptionZZZ ez) {
			try {
				Log.protocol(this, ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(Collection col) {
		boolean bReturn = false;
		main:{
			try {
				if(col==null)break main;
				
				this.ensureValidSize(col.size());
				
				bReturn = super.addAll(col);
				this.iIndexUsedLast = this.size()-1;
				
			}catch(ExceptionZZZ ez) {
				try {
					Log.protocol(this, ez.getDetailAllLast());
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
				}
			}
		}//end main:
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int iIndex, Collection col) {
		boolean bReturn = false;
		main:{
			try {
				if(col==null)break main;
			
				this.ensureValidSize((iIndex+1) + col.size());
				
				bReturn = super.addAll(iIndex, col);
				this.iIndexUsedLast = this.size()-1;
			}catch(ExceptionZZZ ez) {
				try {
					Log.protocol(this, ez.getDetailAllLast());
				} catch (ExceptionZZZ e) {
					e.printStackTrace();
				}
			}
		}//end main:
		return bReturn;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void insertElementAt(Object objIn, int iIndex) {
		
		try {
			this.ensureValidSize(iIndex);
			
			Object obj;
			if(objIn==null) {
				obj = this.getObjectDefaultNew();
			}else {
				obj = objIn;
			}
			super.insertElementAt(obj, iIndex);
			this.iIndexUsedLast = iIndex;
			
		}catch(ExceptionZZZ ez) {
			try {
				Log.protocol(this, ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
	}

	
	
	//### Aus IVectorZZZ (muss ueberschrieben werden, um die Anzahl 3 zu garantieren)
    //.... momentan noch nix hier
}
