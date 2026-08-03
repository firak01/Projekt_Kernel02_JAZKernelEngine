package basic.zBasic.util.string.formater;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

public interface IStringFormatUserZZZ {
	public IEnumSetMappedStringFormatZZZ[] getStringFormatArrayCurrent() throws ExceptionZZZ;
	public void setStringFormatArrayCurrent(IEnumSetMappedStringFormatZZZ[] ienumaFormatString) throws ExceptionZZZ;
	
	public IEnumSetMappedStringFormatZZZ[] getStringFormatArrayDefault() throws ExceptionZZZ;
	
	//+++++++++++++++++++++++++++++++++++++++++++
	public ArrayListZZZ<IEnumSetMappedStringFormatZZZ> getSeparatorArrayList() throws ExceptionZZZ;
	public void setSeparatorArrayList(ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator) throws ExceptionZZZ;
	
	//Zuruecksetzen, z.B. der Trennzeichen, true wenn etwas zurueckzusetzen war.
	public boolean resetSeparatorArrayList() throws ExceptionZZZ;	
}
