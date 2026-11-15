package basic.zBasic;

import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;

public class ObjectUtilZZZ implements IConstantZZZ{
	/** Merke: Ensure Methoden werfen dann die ExceptionZZZ
	 * @param obj1
	 * @param obj2
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.09.2024, 08:31:16
	 */
	public static boolean ensureTypeEquals(Object obj1, Object obj2) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(obj1==null) break main;
			if(obj2==null) break main;
			
			if(!ObjectUtilZZZ.typeEquals(obj1, obj2)) {
				ExceptionZZZ ez = new ExceptionZZZ("Objects are from different type: '" + obj1.getClass().getName() +"', '" + obj2.getClass().getName() + "'", iERROR_PARAMETER_VALUE, ObjectUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
		
	/** Merke: Anders als die ensure... Methode wird hier keine ExceptionZZZ geworfen, beim Vergleich selbst
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.09.2024, 08:35:38
	 */
	public static boolean typeEquals(Object obj1, Object obj2) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(obj1==null) break main;
			if(obj2==null) break main;
			
			bReturn = obj1.getClass().equals(obj2.getClass());
		}//end main:
		return bReturn;
	}
	
	public static void logProtocol(Object obj, String sLog) throws ExceptionZZZ{
		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);						
		System.out.println(sLogUsed);
	}
}
