package basic.zBasic.util.string.formater;

import java.util.HashMap;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;

public class StringFormaterZZZ extends AbstractStringFormaterZZZ{
	private static final long serialVersionUID = 5164996113432507434L;
		
	public StringFormaterZZZ() throws ExceptionZZZ{		
		super();
	}
	
	public StringFormaterZZZ(ArrayListUniqueZZZ<Integer> listaintStringIndexRead) throws ExceptionZZZ{		
		super(listaintStringIndexRead);
	}
	
//	public LogStringFormaterZZZ(IStringJustifierZZZ objJustifier) throws ExceptionZZZ{		
//		super(objJustifier);
//	}

	@Override
	public IEnumSetMappedStringFormatZZZ[] getFormatPositionsMappedCustom() {
		IEnumSetMappedStringFormatZZZ[] iaenumReturn = {
				IStringFormatZZZ.LOGSTRINGFORMAT.DATE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAMESIMPLE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_XML,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,					
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML_BY_XML,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,				
		};
		return iaenumReturn;
	}

	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringCustom() throws ExceptionZZZ {
		HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
		
		//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),"'" + ILogStringZZZ.LOGSTRING.CLASSNAME.getFormat() + "'");
		
		return hmReturn;
	}
	
	

	
}
