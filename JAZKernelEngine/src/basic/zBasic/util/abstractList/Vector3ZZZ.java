package basic.zBasic.util.abstractList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


//#### Aus IVector3ZZZ

/** Vektor, der immer maximal 3 Elemente haben darf.
 *  Aber auch immer aus 3 Elementen besteht!
 *  
 *  Merke: 
 *  Vorgesehen fuer die Zerlegeung von Strings in einen Teil links, mittig, rechts.
 *  Z.B. beim Parsen von (XML-)Tags.
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 25.09.2024, 07:58:44
 * 
 */
public class Vector3ZZZ<T> extends VectorLimitedZZZ<T> implements IVector3ZZZ<T>{
	private static final long serialVersionUID = -4863797296442866090L;

	public Vector3ZZZ() throws ExceptionZZZ {
		super(3);
	}
	
	public Vector3ZZZ(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		super(3, vecExpression);
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void replace(Object objValue) throws ExceptionZZZ {
		main:{
			if(objValue == null) {
				replace_(null);
			}else if(objValue instanceof NullObjectZZZ) {
				replace_(null);
			}else if(objValue instanceof String ) {
				replace_((String) objValue);			
			}else {
				//ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objValue.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				//throw ez;
				replace_(objValue.toString());
			}								
		}//end main:
	}
	
	private void replace_(String sValue) throws ExceptionZZZ{
		
		if(this.size()==0) this.add(0, new NullObjectZZZ());
		
		if(this.size()>=2) this.removeElementAt(1);
		if(sValue==null) {
			this.add(1, new NullObjectZZZ());
		}else {
			this.add(1, sValue); //nicht by default ersetzen
		}
		
		if(this.size()==2) this.add(2, new NullObjectZZZ());
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void replace(Object objLeft, Object objMid, Object objRight) throws ExceptionZZZ {
		main:{			
			if((objLeft==null | objLeft instanceof String) && (objMid==null || objMid instanceof String) && (objRight==null || objRight instanceof String)) {
				replace_((String) objLeft, (String) objMid, (String) objRight);
			}else if((objLeft instanceof NullObjectZZZ) && (objMid instanceof NullObjectZZZ) && (objRight instanceof NullObjectZZZ)) {
				replace_(null, null, null);
			}else {
				//ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objLeft.getClass().getName() +"','" + objMid.getClass().getName() +"','" + objRight.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				//throw ez;
				replace_(objLeft.toString(), objMid.toString(), objRight.toString());
			}	
		}//end main:
	}
	
	private void replace_(String sLeft, String sMid, String sRight) throws ExceptionZZZ{
		
		if(this.size()>=1) this.removeElementAt(0);	
		if(sLeft==null){
			this.add(0, new NullObjectZZZ());
		}else {
			this.add(0, sLeft);
		}
		
		if(this.size()>=2) this.removeElementAt(1);
		if(sMid==null) {
			this.add(1, new NullObjectZZZ());
		}else {
			this.add(1, sMid); //zentral wichtig: In der Mitte immer das "Extrakt". HIER ABER LEER, bzw. nur Separator
		}
		
		
		
		if(this.size()>=3) this.removeElementAt(2);	
		if(sRight==null) {
			this.add(2, new NullObjectZZZ());
		}else {
			this.add(2, sRight);
		}
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void replaceIgnoreNull(Object objValue) throws ExceptionZZZ {
		main:{
			if(objValue==null) break main;
			if (objValue instanceof NullObjectZZZ) break main;
			
			if(objValue instanceof String) {
				replaceByDefault_((String) objValue);
			}else {
				//ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objValue.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				//throw ez;
				replaceByDefault_(objValue.toString());
			}
				
				
		}//end main:
	}
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void replace(Object objLeft, Object objRight, boolean bReturnSeparators, String sSepMid ) throws ExceptionZZZ {
		main:{
			if(objLeft==null || objRight==null) break main;
			
			if(objLeft instanceof String) {
				replaceWithSeparator_((String) objLeft, (String) objRight, bReturnSeparators, sSepMid);
			}else {
				replaceWithSeparator_(objLeft.toString(), objRight.toString(), bReturnSeparators, sSepMid);
			}
		}//end main:
	}
	
	private void replaceWithSeparator_(String sLeft, String sRight, boolean bReturnSeparators, String sSepMid) throws ExceptionZZZ {
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sRight); //Merke: ensure... wirft eine Exception
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(bReturnSeparators ==true){
			if(this.size()>=1) this.removeElementAt(0);
			if(!StringZZZ.isNull(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, this.getObjectDefaultNew());
			}
			
			if(this.size()>=2) this.removeElementAt(1);
			this.add(1, sSepMid); //zentral wichtig: In der Mitte immer das "Extrakt". HIER ABER LEER, bzw. nur Separator
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isNull(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, this.getObjectDefaultNew());
			}
		
		}else if(bReturnSeparators == false){
			if(this.size()>=1) this.removeElementAt(0);										
			if(!StringZZZ.isNull(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, this.getObjectDefaultNew());
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, this.getObjectDefaultNew());
			
			if(this.size()>=3) this.removeElementAt(2);										
			if(!StringZZZ.isNull(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, this.getObjectDefaultNew());
			}
		}
	}
	
	
	private void replaceWithSeparatorByDefault_(String sLeft, String sRight, boolean bReturnSeparators, String sSepMid) throws ExceptionZZZ {
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sRight); //Merke: ensure... wirft eine Exception
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(bReturnSeparators ==true){
			if(this.size()>=1) this.removeElementAt(0);
			if(StringZZZ.isEmpty(sLeft)){
				this.add(0, this.getObjectDefaultNew());
			}else {
				this.add(0, sLeft);
			}
			
			if(this.size()>=2) this.removeElementAt(1);
			if(sSepMid==null) {
				this.add(1, this.getObjectDefaultNew());
			}else {
				this.add(1, sSepMid); //zentral wichtig: In der Mitte immer das "Extrakt". HIER ABER LEER, bzw. nur Separator
			}
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(StringZZZ.isEmpty(sRight)){
				this.add(2, this.getObjectDefaultNew());				
			}else {
				this.add(2, sRight);
			}
		
		}else if(bReturnSeparators == false){
			if(this.size()>=1) this.removeElementAt(0);										
			if(StringZZZ.isEmpty(sLeft)){
				this.add(0, this.getObjectDefaultNew());				
			}else {
				this.add(0, sLeft);
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, this.getObjectDefaultNew()); 
			
			if(this.size()>=3) this.removeElementAt(2);										
			if(StringZZZ.isEmpty(sRight)){
				this.add(2, this.getObjectDefaultNew());				
			}else {
				this.add(2, sRight);
			}
		}
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void replaceIgnoreNull(Object objLeft, Object objMid, Object objRight ) throws ExceptionZZZ {
		main:{
			if(objMid==null) break main;
			if(objLeft==null & objRight==null) {
				this.replace(objMid);
				break main;
			}
			
			if(objLeft instanceof NullObjectZZZ & objRight instanceof NullObjectZZZ) {
				this.replace(objMid);
				break main;
			}
			
			
			if((objLeft==null | objLeft instanceof String) && (objMid==null || objMid instanceof String) && (objRight==null || objRight instanceof String)) {
				replaceWithSeparatorByDefault_((String) objLeft, (String) objMid, (String) objRight, false, null, null);
			}else {				
				//ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objLeft.getClass().getName() +"','" + objMid.getClass().getName() +"','" + objRight.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				//throw ez;
				replaceWithSeparatorByDefault_(objLeft.toString(), objMid.toString(), objRight.toString(), false, null, null);
			}
		}//end main:
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void replaceByDefault() throws ExceptionZZZ {		
				replaceByDefault_(null);
	}
	
	@Override
	public void replaceByDefault(Object objValueMid) throws ExceptionZZZ {		
				replaceByDefault_(objValueMid);
	}
	
	private void replaceByDefault_(Object objMid) throws ExceptionZZZ{
		
		if(this.size()==0) this.add(0, this.getObjectDefaultNew());
		
		if(this.size()>=2) this.removeElementAt(1);
		
		if(objMid==null || objMid instanceof String) {
			if(StringZZZ.isEmpty((String) objMid)){ 
				this.add(1, this.getObjectDefaultNew());
			}else {
				this.add(1, (String) objMid);				
			}
		}else if(objMid instanceof NullObjectZZZ) {
			this.add(1, this.getObjectDefaultNew());
		}else {
			ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objMid.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}	
			
		if(this.size()==2) this.add(2, this.getObjectDefaultNew()); //wenn Leer by Default ersetzen
	}
	
	@Override
	public void replaceByDefault(Object objLeft, Object objMid, Object objRight ) throws ExceptionZZZ {
		main:{			
			if(objLeft==null & objRight==null) {
				this.replaceByDefault(objMid);
				break main;
			}
			
			if((objLeft==null | objLeft instanceof String) && (objMid==null || objMid instanceof String) && (objRight==null || objRight instanceof String)) {
				replaceWithSeparatorByDefault_((String) objLeft, (String) objMid, (String) objRight, false, null, null);
			} else if((objLeft instanceof NullObjectZZZ) && (objMid instanceof NullObjectZZZ) && (objRight instanceof NullObjectZZZ)) {
				replaceWithSeparatorByDefault_(null, null, null, false, null, null);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objLeft.getClass().getName() +"','" + objMid.getClass().getName() +"','" + objRight.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//end main:
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void replace(Object objLeft, Object objMid, Object objRight, boolean bReturnSeparators, String sSepLeft, String sSepRight ) throws ExceptionZZZ {
		main:{
			if(objMid==null) break main;
			if(objLeft==null & objRight==null) {
				this.replace(objMid);
				break main;
			}
			if(objMid instanceof NullObjectZZZ) break main;
			if(objLeft instanceof NullObjectZZZ & objRight instanceof NullObjectZZZ) {
				this.replace(objMid);
				break main;
			}
			if((objLeft==null | objLeft instanceof String) && (objMid==null || objMid instanceof String) && (objRight==null || objRight instanceof String)) {				
				replaceWithSeparatorByDefault_((String) objLeft, (String) objMid, (String) objRight, bReturnSeparators, sSepLeft, sSepRight);
			}else if((objLeft instanceof NullObjectZZZ) && (objMid instanceof NullObjectZZZ) && (objRight instanceof NullObjectZZZ)) {
				replaceWithSeparatorByDefault_(null, null, null, bReturnSeparators, sSepLeft, sSepRight);
			}else {
				ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + objLeft.getClass().getName() +"','" + objMid.getClass().getName() +"','" + objRight.getClass().getName() +"'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
	}
	
	private void replaceWithSeparatorByDefault_(String sLeft, String sMid, String sRight, boolean bReturnSeparators, String sSepLeft, String sSepRight) throws ExceptionZZZ {
		
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sMid); //Merke: ensure... wirft eine Exception
		ObjectUtilZZZ.ensureTypeEquals(sRight, sMid);
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(bReturnSeparators ==true && !StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft + sSepLeft);
			}else {
				this.add(0, sSepLeft);
			}
								
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, sMid);//zentral wichtig: In der Mitte immer das "Extrakt".
			
			if(this.size()>=3) this.removeElementAt(2);											
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sSepRight + sRight);
			}else {
				this.add(2,  sSepRight);
			}
		}else if(bReturnSeparators ==false && !StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, this.getObjectDefaultNew());
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, sMid);
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, this.getObjectDefaultNew());
			}
		
		} else if(bReturnSeparators ==true && StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft + sSepLeft);
			}else {
				this.add(0, sSepLeft);
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, this.getObjectDefaultNew());
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sSepRight + sRight);
			}else {
				this.add(2, sSepRight);
			}
		}  else {
			if(this.size()>=1) this.removeElementAt(0);						
			if(!StringZZZ.isEmpty(sLeft)){
				this.add(0, sLeft);
			}else {
				this.add(0, this.getObjectDefaultNew());
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, this.getObjectDefaultNew());
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(!StringZZZ.isEmpty(sRight)){
				this.add(2, sRight);
			}else {
				this.add(2, this.getObjectDefaultNew());
			}
		}
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	@Override
	public void replaceKeepSeparatorCentral(Object objLeft, Object objMid, Object objRight, String sSepLeft, String sSepRight) throws ExceptionZZZ {
		main:{
			if(objMid==null) break main;
			if(objLeft==null & objRight==null) {
				this.replace(objMid);
				break main;
			}
			
			if(objMid instanceof String) {
				replaceKeepSeparatorCentral_((String) objLeft, (String) objMid, (String) objRight, sSepLeft, sSepRight);
			}else {
				replaceKeepSeparatorCentral_(objLeft.toString(), objMid.toString(), objRight.toString(), sSepLeft, sSepRight);
			}
		}//end main:
	}
	
	private void replaceKeepSeparatorCentral_(String sLeft, String sMid, String sRight, String sSepLeft, String sSepRight) throws ExceptionZZZ {
		
		ObjectUtilZZZ.ensureTypeEquals(sLeft, sMid); //Merke: ensure... wirft eine Exception
		ObjectUtilZZZ.ensureTypeEquals(sRight, sMid);
		
		//Nun die Werte in den ErgebnisVector zusammenfassen
		if(!StringZZZ.isEmpty(sMid)){
			if(this.size()>=1) this.removeElementAt(0);
			if(StringZZZ.isEmpty(sLeft)){
				this.add(0, this.getObjectDefaultNew());				
			}else {
				this.add(0, sLeft);
			}
								
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, sSepLeft + sMid + sSepRight);//zentral wichtig: In der Mitte immer das "Extrakt".
			
			if(this.size()>=3) this.removeElementAt(2);											
			if(StringZZZ.isEmpty(sRight)){
				this.add(2, this.getObjectDefaultNew());
			}else {
				this.add(2, sRight);				
			}
		}else {
			//sMid ist leer		
			if(this.size()>=1) this.removeElementAt(0);						
			if(StringZZZ.isEmpty(sLeft)){
				this.add(0, this.getObjectDefaultNew());				
			}else {
				this.add(0, sLeft);
			}
			
			if(this.size()>=2) this.removeElementAt(1);						
			this.add(1, this.getObjectDefaultNew());
			
			if(this.size()>=3) this.removeElementAt(2);						
			if(StringZZZ.isEmpty(sRight)){
				this.add(2, this.getObjectDefaultNew());
			}else {
				this.add(2, sRight);				
			}
		} 
	}
}
