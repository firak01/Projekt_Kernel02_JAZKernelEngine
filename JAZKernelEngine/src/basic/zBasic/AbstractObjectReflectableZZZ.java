package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractObjectReflectableZZZ <T> extends AbstractObjectWithExceptionZZZ implements IObjectReflectableZZZ{
	protected String sClassMethodCalling=null;		
	protected HashMap<String,String> hmClassMethodCalling = new HashMap<String,String>();
	
	public AbstractObjectReflectableZZZ(){
		super();		
	}
	@Override
	public String getClassMethodCallingString() throws ExceptionZZZ {
		//Basierend auf dem aktuellen Klassennamen...
		
		return this.sClassMethodCalling;
	}
	
	@Override
	public  void putClassMethodCallingHashMap(String sClassMethodCalling) throws ExceptionZZZ {
		String sClass = this.getClass().getName();
		this.getClassMethodCallingHashMap().put(sClass, sClassMethodCalling);
	}

	@Override
	public HashMap<String,String> getClassMethodCallingHashMap() throws ExceptionZZZ {
		return this.hmClassMethodCalling;
	}
	@Override
	public void setClassMethodCallingString(String sClassMethodCalling) throws ExceptionZZZ {
		main:{
		//Nur setzen, wenn nicht schon gesetzt wurde.
			//if(this.sClassMethodCalling!=null) break main;
			
			 //Prüfe auf Korrektheit des Wertes
			if(StringZZZ.isEmpty(sClassMethodCalling)){
				
				ExceptionZZZ ez = new ExceptionZZZ("ObjectReflectable - String Classenname.Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!StringZZZ.contains(sClassMethodCalling, ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR)){
				ExceptionZZZ ez = new ExceptionZZZ("ObjectReflectable - String Classenname.Methodenname erwartet. Es fehlt der Punkt!", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}		
			this.putClassMethodCallingHashMap(sClassMethodCalling);
			this.sClassMethodCalling=sClassMethodCalling; 
		}		
	}
	@Override
	public boolean initClassMethodCallingString() throws ExceptionZZZ {		 
		String sClassMethodCalling = ReflectCodeKernelZZZ.getClassMethodExternalCallingString(3);//.getClassMethodCallingString(3);//2 sowieso, wg. Aufruf aus Konstruktor  + 1 weil es aus makeReflectable... aufgerufen wird.
		this.setClassMethodCallingString(sClassMethodCalling);
		return true;
	}
		
	
//	@Override
//	public void setClassMethodInitialisedName(Class<?>objClass, String sMethod) throws ExceptionZZZ{
//		if(objClass==null){
//			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: Classenobject erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//		if(StringZZZ.isEmpty(sMethod)){
//			ExceptionZZZ ez = new ExceptionZZZ("StrategyObject: String Methodenname erwartet.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
//				
//		String sClassname = objClass.getName();
//		this.sClassMethodInitialisedName = sClassname + ReflectCodeZZZ.sCLASS_METHOD_SEPERATOR + sMethod;		
//	}			

}
