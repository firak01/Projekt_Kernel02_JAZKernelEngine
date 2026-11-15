package basic.zBasic.util.abstractEnum;

import static java.lang.System.out;

import java.util.EnumSet;
import java.util.Formatter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IDummyTestObjectWithStatusLocalByInterfaceZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.DummyTestObjectWithStatusLocalByInterfaceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigConstantZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

public class EnumSetTestFactoryZZZ extends AbstractObjectWithFlagZZZ implements IEnumSetFactoryZZZ{

private static EnumSetTestFactoryZZZ objEnumFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist private, wg. Singleton
	 * @param objKernel
	 * @throws ExceptionZZZ
	 */
	private EnumSetTestFactoryZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
	}
	private EnumSetTestFactoryZZZ(){
		super();
	}
	
	public static EnumSetTestFactoryZZZ getInstance(){
		if(objEnumFactory==null){
			objEnumFactory = new EnumSetTestFactoryZZZ();
		}
		return objEnumFactory;		
	}
	
	/*
	 * Das ist der Kerngedanke dieser Factory.
	 * Gib den Klassennamen an, dann kann man die static Methode aufrufen (hart verdrahtet, denn Reflection funktioniert leider nicht).
	 */
	public EnumSet<?>getEnumSet(Class objClassEnum) throws ExceptionZZZ{
		EnumSet<?>objEnumSet = null;
		main:{
			if(objClassEnum==null){
				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			String sClassNameEnum = objClassEnum.getName();
			objEnumSet = getEnumSet(sClassNameEnum);
		}//end main:
		return objEnumSet;
	}
	
	public EnumSet<?>getEnumSet(String sClassNameEnum) throws ExceptionZZZ{
		EnumSet<?>objEnumSetReturn = null;
		main:{
			if(StringZZZ.isEmpty(sClassNameEnum)){
				ExceptionZZZ ez  = new ExceptionZZZ("ClassObject", iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Merke: Switch Anweisung mit String ist erst ab Java 1.7 möglich			
			if (sClassNameEnum.equals("basic.zBasic.util.abstractEnum.EnumSetMappedTestTypeZZZ")){
				//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
//				String sInfo = String.format("%1$s # als sClassNameEnum wird hier gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
//				System.out.print(sInfo);
	        	objEnumSetReturn= EnumSetMappedTestTypeZZZ.getEnumSet(); 
	        	
	        	                          
			}else if(sClassNameEnum.equals("basic.zBasic.IDummyTestObjectWithStatusZZZ$STATUSLOCAL")) {
				objEnumSetReturn= IDummyTestObjectWithStatusLocalByInterfaceZZZ.STATUSLOCAL.getEnumSet();
	        	
			}else if(sClassNameEnum.equals("basic.zBasic.IDummyTestObjectWithStatusByInterfaceZZZ$STATUSLOCAL")) {
				objEnumSetReturn= IDummyTestObjectWithStatusLocalByInterfaceZZZ.STATUSLOCAL.getEnumSet();
//				
//			}else if(sClassNameEnum.equals("basic.zBasic.util.abstractEnum.EnumSetDefaulttextTestTypeZZZ")){
	        	//Beispiel für weiteren Enum-Typen im Projekt.
	        	//Merke: Dieser Typ ist im THM-Projekt definiert. Das darf aber im Kernel nicht eingebunden werden, implementiert er doch IEnumSetTextTHM
				//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
	        	//out.format("%s# sClassNameEnum wurde hier gefunden: %s%n", ReflectCodeZZZ.getPositionCurrent(),sClassNameEnum);
	        	//objEnumSetReturn= EnumSetDefaulttextTestTypeZZZ.getEnumSet();     
	        }else if (sClassNameEnum.equals("basic.zKernel.config.KernelConfigDefaultEntryZZZ$EnumConfigDefaultEntryZZZ")) {//beachte: Innere Klasse, mit $ getrennt.
	        	//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
//	        	String sInfo = String.format("%1$s # als sClassNameEnum wird hier gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
//				System.out.print(sInfo);	        	
	        	objEnumSetReturn= KernelConfigDefaultEntryZZZ.EnumConfigDefaultEntryZZZ.getEnumSet();   
	        }else if (sClassNameEnum.equals("basic.zKernel.IKernelConfigConstantZZZ$EnumConfigDefaultEntryZZZ")) {
	        	objEnumSetReturn= IKernelConfigConstantZZZ.EnumConfigDefaultEntryZZZ.getEnumSet();
	        }else if (sClassNameEnum.equals("basic.zBasic.IDummyTestObjectWithStatusLocalByInterfaceZZZ$STATUSLOCAL")) {
	        	objEnumSetReturn= IDummyTestObjectWithStatusLocalByInterfaceZZZ.STATUSLOCAL.getEnumSet();
			}else{
	        	//Wenn es die Klasse nicht gibt. Keinen Fehler werfen, da ggfs. über einen Vererbungsmechanismus ja zuerst in der Superklasse nachgesehen wurde und dann ggfs. weiter in Kindklassen gesucht wird.
	        	//Auf ObjectZZZ Ebene gibt es noch keine Logging-Klassen
	        	//out.format("%s# sClassNameEnum wird hier NICHT gefunden: %s%n", ReflectCodeZZZ.getPositionCurrent(),sClassNameEnum);
				//String sError = out.toString(); //geht nicht, der Stream ist schon längst zuende
				
				//Lösung ist:
//				StringBuilder sbuf = new StringBuilder();
//				Formatter fmt = new Formatter(sbuf);
//				//Beispiel fmt.format("PI = %f%n", Math.PI);
//				fmt.format(": %1$s # als sClassNameEnum wird hier NICHT gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
//				String sError=sbuf.toString();
				
				//Oder noch kürzere Lösung ist:
				String sError = String.format("%1$s # als sClassNameEnum wird hier NICHT gefunden: %2$s", sClassNameEnum,ReflectCodeZZZ.getPositionCurrent());
				System.out.print(sError);									
	        	ExceptionZZZ ez  = new ExceptionZZZ(": " + sError, iERROR_PARAMETER_MISSING, this.getClass().getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
	        }
		
	}//end main:
	return objEnumSetReturn;
	}
}
