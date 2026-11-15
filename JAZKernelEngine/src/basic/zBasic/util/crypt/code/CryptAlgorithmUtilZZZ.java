package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.formula.ZFormulaIniLineZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;

public class CryptAlgorithmUtilZZZ implements IConstantZZZ{
	public static boolean isUsingCharacterPoolBase(ICryptZZZ objCrypt) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'CryptAlgorithm Object'",iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			int iAlgorithmMaintype = objCrypt.getCipherType().getMaintype();
			if(iAlgorithmMaintype == CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal()) {
				bReturn = true;
			}else if(iAlgorithmMaintype == CryptAlgorithmMaintypeZZZ.TypeZZZ.ROT.ordinal()) {
				bReturn = false;
			}			
		}
		return bReturn;
	}
}
