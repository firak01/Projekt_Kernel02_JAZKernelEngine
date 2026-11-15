package basic.zBasic.xml.tagtype;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagBasicZZZ;


public abstract class AbstractTagByTypeZZZ extends AbstractTagBasicZZZ implements ITagByTypeZZZ{			
	private static final long serialVersionUID = -3411751655174978836L;
	protected ITagTypeZZZ objTagType = null;		
	protected Vector<ITagByTypeZZZ>vecChildTags = null;
	
	public AbstractTagByTypeZZZ() throws ExceptionZZZ{
		super();
	}
	
	public AbstractTagByTypeZZZ(ITagTypeZZZ objType) throws ExceptionZZZ{
		super();
		AbstractTagNew_(objType, "");
	}
	
	public AbstractTagByTypeZZZ(ITagTypeZZZ objType, String sValue) throws ExceptionZZZ{
		super();
		AbstractTagNew_(objType, sValue);
	}
		
	private void AbstractTagNew_(ITagTypeZZZ objType, String sValue) throws ExceptionZZZ{
		main:{
			if(objType==null){
				ExceptionZZZ ez = new ExceptionZZZ("KernelTagType", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(sValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("Element-Value", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					

			this.setTagType(objType);
			this.setValue(sValue);			
		}//End main
	}
		
	//######## Getter / Setter ##################
	@Override
	public Vector<ITagByTypeZZZ> getChildTags(){
		return this.vecChildTags;
	}
	
	@Override
	public void setChildTags(Vector<ITagByTypeZZZ>vecTags) {
		this.vecChildTags = vecTags;
	}
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++ Details aus TagType +++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//#### Aus Interfacace
	//s. Analog zu z.B. KernelZFormulaIni_NullZZZ, dort aber als Static Methoden.
	//Merke: Erst ab Java 8 können static Ausdrücke in ein interface

	@Override
	public ITagTypeZZZ getTagType(){
		return this.objTagType;
	}
	protected void  setTagType(ITagTypeZZZ objTagType){
		this.objTagType = objTagType;
	}
	
	//Bemerkenswert, hier ueberschreiben weg TagType
	@Override
	public String getName() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagName();
		}else {
			return super.getName();
		}
	}	

	//+++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String getTagPartOpening() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartOpening();
		}else {
			return "";
		}
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{				
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartClosing();
		}else {
			return "";
		}
	}	
	
	@Override
	public String getEmpty() throws ExceptionZZZ{
		ITagTypeZZZ objType = this.getTagType();
		if(objType!=null) {
			return objType.getTagPartEmpty();
		}else {
			return "";
		}
	}	
}
