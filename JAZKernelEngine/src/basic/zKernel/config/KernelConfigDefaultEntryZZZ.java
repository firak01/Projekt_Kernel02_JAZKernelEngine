package basic.zKernel.config;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zKernel.IKernelConfigConstantZZZ;

public class KernelConfigDefaultEntryZZZ<IEnumSetConfigKernelConfigDefaultEntryZZZ> implements IKernelConfigConstantZZZ{
	private int iId;
	private String sProperty;
	private String sValueDefault;
	
	//+++ Konstruktor
	public KernelConfigDefaultEntryZZZ() {		
	}
	
	//+++ Gespeicherte Werte
	public int getiId() {
		return iId;
	}

	public void setId(int iId) {
		this.iId = iId;
	}

	public String getProperty() {
		return sProperty;
	}

	public void setProperty(String sProperty) {
		this.sProperty = sProperty;
	}

	public String getValueDefault() {
		return sValueDefault;
	}

	public void setValueDefault(String sValueDefault) {
		this.sValueDefault = sValueDefault;
	}
	
	//### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getEnumClassStatic(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return EnumConfigDefaultEntryZZZ.class;    	
    }

	
}//end class
