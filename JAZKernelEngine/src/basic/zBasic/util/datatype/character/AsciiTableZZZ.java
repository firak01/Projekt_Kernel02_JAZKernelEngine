package basic.zBasic.util.datatype.character;

import java.io.Serializable;
import java.util.EnumSet;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMaintypeZZZ.TypeZZZ;

//20230212: Um die Enumeration herum eine Klasse bauen.
//Diese Struktur hat den Vorteil, das solche Werte auch in einer Datenbank per Hibernate persistiert werden können.
//Verwendet wird solch eine Struktur z.B. in der Defaulttext - Klasse des TileHexMapTHM Projekts
//                                        oder auch in CryptAlgorithmMaintypeZZZ.
public class AsciiTableZZZ   implements Serializable{	
	//Entsprechend der internen Enumeration
	//Merke: Die Enumeration dient der Festlegung der Defaultwerte. In den Feldern des Entities werden die gespeicherten Werte gehalten.
	private String fullName, abbr;
	
	public AsciiTableZZZ(){		
	}
	
	public String getFullname(){
		return this.fullName;
	}
	public void setFullname(String sFullname){
		this.fullName = sFullname;
	}
	
	public String getAbbreviation(){
		return this.abbr;
	}
	public void setAbbreviation(String sAbbr){
		this.abbr = sAbbr;
	}

//### Statische Methode (um einfacher darauf zugreifen zu können)
public static Class getEnumClassStatic(){
	try{
		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
	}catch(ExceptionZZZ ez){
		String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
		System.out.println(sError);
	}
	return SectionZZZ.class;    	
}

	//#######################################################
	//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
	//### String fullName, String abbreviation
	//#######################################################
				
	//Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
	//ALIAS("Beschreibung, wird nicht genutzt....","Abkürzung, also das, was im URL String steht. Meist gefolgt von einem  Doppelpunkt, der hinzugerchnet wird, wenn die Abkürzung nicht leer ist.")
	public enum SectionZZZ implements IEnumSetMappedZZZ {//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		BLANK("BLANK","the letter blank",32,32," "),
		NUMBER("NUMBER","the numbers",48,57,"0123456789"),
		LETTER_UPPERCASE("LETTER_UPPERCASE","the uppercase letters / capital letters ",65,90,"ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
		LETTER_LOWERCASE("LETTER_LOWERCASE","the lowercase letters",97,122,"abcdefghijklmnopqrstuvwxyz");

		private String descr, abbr;
		private int iAsciiStart;
		private int iAsciiEnd;
		private String sAsciiSection;

	//#############################################
	//#### Konstruktoren
	//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
	//In der Util-Klasse habe ich aber einen Workaround gefunden.
	SectionZZZ(String abbr, String descr,int iAsciiStart, int iAsciiEnd, String sAsciiSection) {
	    this.descr = descr;
	    this.abbr = abbr;
	    this.iAsciiStart = iAsciiStart;
	    this.iAsciiEnd = iAsciiEnd;
	    this.sAsciiSection = sAsciiSection;
	}

	//the identifierMethod ---> Going in DB
	public String getAbbreviation() {
	 return this.abbr;
	}
	
	public int getStart() {
		return this.iAsciiStart;
	}
	
	public int getEnd() {
		return this.iAsciiEnd;
	}
	
	public String getSection() {
		return this.sAsciiSection;
	}
	
	public int getLength() {
		return this.iAsciiEnd-this.iAsciiStart;
	}
	
	

	public EnumSet<?>getEnumSetUsed(){
		return SectionZZZ.getEnumSet();
	}

	/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
	@SuppressWarnings("rawtypes")
	public static <E> EnumSet getEnumSet() {
		
	 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
		//String sFilterName = "FLAGZ"; /
		//...
		//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
		
		//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
		Class<SectionZZZ> enumClass = SectionZZZ.class;
		EnumSet<SectionZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
		
		 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
		 for(Object obj : objaEnum){
			//System.out.println(obj + "; "+obj.getClass().getName());
			set.add((SectionZZZ) obj);
		}
		return set;
		
	}

	//TODO: Mal ausprobieren was das bringt
	//Convert Enumeration to a Set/List
	private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
		  EnumSet<E> set=EnumSet.noneOf(enumClass);
		  long mask=1;
		  for (  E e : enumClass.getEnumConstants()) {
		    if ((mask & vector) == mask) {
		      set.add(e);
		    }
		    mask<<=1;
		  }
		  return set;
		}

	//+++ Das könnte auch in einer Utility-Klasse sein.
	//the valueOfMethod <--- Translating from DB
	public static SectionZZZ fromAbbreviation(String s) {
	for (SectionZZZ state : values()) {
	   if (s.equals(state.getAbbreviation()))
	       return state;
	}
	throw new IllegalArgumentException("Not a correct abbreviation: " + s);
	}

	//##################################################
	//#### Folgende Methoden bring Enumeration von Hause aus mit. 
			//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
	@Override
	public String getName() {	
		return super.name();
	}

	@Override
	public String toString() {
	    return this.abbr+"#"+this.descr+"#"+this.iAsciiStart+"#"+this.iAsciiEnd+"#"+this.sAsciiSection;
	}

	@Override
	public int getIndex() {
		return ordinal();
	}

	//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
	@Override
	public int getPosition() {
		return getIndex()+1; 
	}

	@Override
	public String getDescription() {
		return this.descr;
	}
	//+++++++++++++++++++++++++
	}//End internal Class	
	}//End Class
