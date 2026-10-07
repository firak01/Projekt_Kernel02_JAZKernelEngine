package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import custom.zKernel.Log;

public class ROT13ZZZ extends AbstractROTZZZ{
	public ROT13ZZZ() throws ExceptionZZZ {
		super();		
		Rot13New_(null);
	}
	
	public ROT13ZZZ(String[] saFlagControl) throws ExceptionZZZ {
		super();		
		Rot13New_(saFlagControl);
	}
	
	private boolean Rot13New_(String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		//try{	 		
			//setzen der übergebenen Flags	
			if(saFlagControlIn != null){
				 String stemp; boolean btemp; String sLog;
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){
						 String sKey = stemp;
						 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
						 Log.printlnDate(this, ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
						 
						// Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
						// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
						// throw ez;		 
					}
				}
			}
		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	@Override
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13;
	}
	
	@Override
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		return ROT13ZZZ.encryptIt(sInput);
	}

	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		return ROT13ZZZ.decryptIt(sInput);
	}
	
	public static String encryptIt(String input) {
		return ROT13ZZZ.crypt(input);
	}
	public static String decryptIt(String input) {
		return ROT13ZZZ.crypt(input);
	}
	private static String crypt(String input) {

        char[] values = input.toCharArray();
        for (int i = 0; i < values.length; i++) {
            char letter = values[i];

            if (letter >= 'a' && letter <= 'z') {
                // Rotate lowercase letters.

                if (letter > 'm') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            } else if (letter >= 'A' && letter <= 'Z') {
                // Rotate uppercase letters.

                if (letter > 'M') {
                    letter -= 13;
                } else {
                    letter += 13;
                }
            }
            values[i] = letter;
        }
        // Convert array to a new String.
        return new String(values);
    }	
}
