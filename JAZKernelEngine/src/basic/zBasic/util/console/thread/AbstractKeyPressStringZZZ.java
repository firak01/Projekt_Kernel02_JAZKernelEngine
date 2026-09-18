package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractKeyPressStringZZZ extends AbstractKeyPressCharZZZ implements IKeyPressStringZZZ, IKeyPressStringUserZZZ{
	
	
	@Override
	public String getKeyTag() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String sKey = this.getKeyPressStringObject().getKeyString();
			if(StringZZZ.isEmpty(sKey)) {
				//Strings vor Char
				char cKey = this.getKeyCharObject().getKeyChar();
				sReturn = KeyPressUtilZZZ.computeKeyTag(cKey);
			}else {
				sReturn = KeyPressUtilZZZ.computeKeyTag(sKey);
			}		
		}//end main:
		return sReturn;
	}
	
	@Override
	public abstract String getKeyString();
	
	

}
