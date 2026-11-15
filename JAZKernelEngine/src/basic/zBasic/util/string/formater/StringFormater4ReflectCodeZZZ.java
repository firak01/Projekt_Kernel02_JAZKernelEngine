package basic.zBasic.util.string.formater;

import java.util.HashMap;
import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class StringFormater4ReflectCodeZZZ extends AbstractStringFormaterZZZ{
	private static final long serialVersionUID = 5164996113432507434L;
	
	public StringFormater4ReflectCodeZZZ() throws ExceptionZZZ{		
		super();
	}
	
	
	
	@Override
	public IEnumSetMappedStringFormatZZZ[] getFormatPositionsMappedCustom() {
		IEnumSetMappedStringFormatZZZ[] iaenumReturn = {
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSMETHOD_XML_BY_XML,
				IStringFormatZZZ.LOGSTRINGFORMAT.CLASSFILEPOSITION_XML_BY_XML,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,				
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
				IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
		};
		return iaenumReturn;
	}

	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringCustom() throws ExceptionZZZ {
		HashMap<Integer,String>hmReturn=super.getHashMapFormatPositionStringDefault();
		
		//Hier mögliche Abweichende Strings angeben, z.B. in einfache Hochkommata packen			
		//hmReturn.put(new Integer(ILogStringZZZ.iFACTOR_CLASSNAME),"'" + ILogStringZZZ.LOGSTRING.CLASSNAME.getFormat() + "'");
		
		//und das ist nicht notwendig, dar argnext02 schon einfache hochkommata enthaelt: hmReturn.put(new Integer(ILogStringZZZ.iARGNEXT01), "'" + ILogStringZZZ.sARGNEXT01 + "'");
		//und das bleibt unveraendert hmReturn.put(new Integer(ILogStringZZZ.iTHREAD), ILogStringZZZ.sTHREAD);
		
		
		return hmReturn;
	}	
	//Wenn ganz einfache Ausgabe gewünscht wird, .getPositionCurrentSimple verwenden
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
}
