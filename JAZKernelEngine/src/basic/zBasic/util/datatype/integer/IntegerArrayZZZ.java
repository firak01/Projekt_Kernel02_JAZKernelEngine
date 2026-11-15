package basic.zBasic.util.datatype.integer;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


public class IntegerArrayZZZ implements IConstantZZZ {
	private Integer[] iaIntern;
	private boolean bIsInteger = false;

	//Konstruktoren
	public IntegerArrayZZZ(Integer args[]) throws ExceptionZZZ{
		if(args==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.iaIntern = args;
		this.bIsInteger = true;
	}
	
	public static boolean contains(int[] iaInteger, int iValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(iaInteger==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, IntegerArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			for(int iInteger : iaInteger) {
				if(iInteger==iValue) {
					bReturn=true;
					break main;
				}
			}
		}//end main:
		return bReturn;
	}
	
	public static Integer[] toObject(int[] iaInteger) throws ExceptionZZZ{
		if(iaInteger==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, IntegerArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		int iSize = iaInteger.length;
		Integer[]intaInteger = new Integer[iSize-1];
		for(int icount = 0; icount<=iSize-1;icount++){
			int iTemp = iaInteger[icount];
			Integer intTemp = new Integer(iTemp);
			intaInteger[icount] = intTemp;
		}
		return intaInteger;
	}
	
	public static Integer[] plus(Integer[] iaInteger, int iValue) throws ExceptionZZZ{
		Integer[] objReturn = null;
		main:{
			if(iaInteger==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, IntegerArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(iValue==0){
				objReturn = new Integer[iaInteger.length];
				System.arraycopy(iaInteger,0,objReturn, 0, iaInteger.length);
				break main;
			}
			
			int iSize = iaInteger.length;
			objReturn = new Integer[iSize];
			
			for(int icount = 0; icount<=iSize-1;icount++){
				int itemp = iaInteger[icount].intValue() + iValue;
				objReturn[icount]=new Integer(itemp);
			}
			break main;
			
		}//End main:
		return objReturn;
	}
	
	public static String implode(int[] iaInteger) throws ExceptionZZZ{
		return IntegerArrayZZZ.implode_(iaInteger, "");
	}
	
	public static String implode(int[] iaInteger, String sDelimiter) throws ExceptionZZZ{
		return IntegerArrayZZZ.implode_(iaInteger, sDelimiter);
	}
	
	private static String implode_(int[] iaInteger, String sDelimiter) throws ExceptionZZZ{		
		String sReturn=null;
		main:{
			int iSize = iaInteger.length;
			
			if(iaInteger==null){
//				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, IntegerArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				break main;
			}
			
			for(int icount = 0; icount<=iSize-1;icount++){
				int iTemp = iaInteger[icount];//.intValue();
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = new Integer(iTemp).toString();
				}else {
					sReturn = sReturn + sDelimiter + iTemp; 
				}
			}
			break main;			
		}//end main:
		return sReturn;
	}
	
	
	
	public static String implode(Integer[] iaInteger) throws ExceptionZZZ{
		return IntegerArrayZZZ.implode_(iaInteger, "");
	}
	
	public static String implode(Integer[] iaInteger, String sDelimiter) throws ExceptionZZZ{
		return IntegerArrayZZZ.implode_(iaInteger, sDelimiter);
	}
	
	
	
	private static String implode_(Integer[] intaInteger, String sDelimiter) throws ExceptionZZZ{
		String sReturn=null;
		main:{
			if(intaInteger==null){
//				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, IntegerArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				break main;
			}
			
			int iSize = intaInteger.length;
			
			for(int icount = 0; icount<=iSize-1;icount++){
				Integer intTemp = intaInteger[icount];
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = intTemp.toString();
				}else {
					sReturn = sReturn + sDelimiter + intTemp.toString(); 
				}
			}
			break main;			
		}//end main:
		return sReturn;
	}
}
