package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;

//Methoden, die sowohl der einzelne Formater als auch der FormatManager haben
public interface IStringFormatComputerZZZ {

		//auf ein intern gespeichertes Format angewiesen
		public String compute(String... sLogs) throws ExceptionZZZ;
	
		//ohne ein intern gespeichertes Format oder einen LogString zu verwenden.
		public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
		public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;

		//Das alles noch in einer Variante in der die Klasse uebergeben wird.
		public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		
		//Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
		public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
		public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)throws ExceptionZZZ;
		
		
		//das ganze auch mit dem intern gespeicherten Format
		//Ohne Objekt / Klasse macht das aber keinen Sinn.
		
		//... mit Objekt		//
		public String compute(Object obj, String... sLogs) throws ExceptionZZZ; //ACHTUNG: Hier werden beide Strings in einer Zeile zusammengefasst. Der 2. sollte dann mit dem zweiten argnext formatiert sein
		
		//... mit Klasse
		public String compute(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		//############################
		//Jede Methode auch als ArrayListZZZ, sollten eigentlich private oder protected sein.
		//Das geht aber in Java 1.7 nicht im Interface zu definieren. 
		//Also einen Unterstrich angehängt... zum Darstellen, das diese Methode nicht ausserhalb der Formatierung selbst zu verwenden ist.
		public ArrayListZZZ<String> computeJaggedArrayList_(String... sLogs) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, String... sLogs) throws ExceptionZZZ;	
		
		public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		
		public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;		
		public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;
		public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ;

		//###########################
		public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ;
		public void setStringIndexRead(ArrayListUniqueZZZ<Integer>listaintStringIndexRead) throws ExceptionZZZ;
				
		//Zuruecksetzen, z.B. des Indexwerts, true wenn etwas zurueckzusetzen war.
		public boolean resetStringIndexRead() throws ExceptionZZZ;	
											
		//Zurücksetzen, gibt true zurueck, wenn etwas zurueckzusetzen war.
		public boolean reset() throws ExceptionZZZ;		
}
