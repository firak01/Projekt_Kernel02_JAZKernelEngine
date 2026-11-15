package basic.zBasic.util.abstractEnum;


import static java.lang.System.out;

import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDatabaseMappedValueZZZ;
import basic.zBasic.util.persistence.jdbc.JdbcDriverMappedValueZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

public abstract class AbstractEnumSetFactoryZZZ extends AbstractObjectWithExceptionZZZ implements IEnumSetFactoryZZZ {
	private static IEnumSetFactoryZZZ objEnumFactory = null;  //muss static sein, wg. getInstance()!!!
	
	/**Konstruktor ist auf Package beschränkt, wg. Singleton ist die hieraus erbende Klasse mit private Konstrukor versehen.
	 * @throws ExceptionZZZ
	 */
	public AbstractEnumSetFactoryZZZ(){
		super();
	}
	
//	public static AbstractEnumSetFactoryZZZ getInstance(){
//		if(objEnumFactory==null){
//			objEnumFactory = new AbstractEnumSetFactoryZZZ();
//		}
//		return (AbstractEnumSetFactoryZZZ) objEnumFactory;		
//	}
	
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
	
	public abstract EnumSet<?>getEnumSet(String sClassNameEnum) throws ExceptionZZZ;
}
