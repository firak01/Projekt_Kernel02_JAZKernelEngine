package basic.zBasic;

import java.io.Serializable;
import java.util.EnumSet;

import base.files.EncodingMaintypeZZZ.TypeZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.stream.StreamZZZ.CharsetUsedZZZ;

public class ExceptionHandlingMainTypeZZZ implements IExceptionHandlingMainTypeZZZ, Serializable {
	private static final long serialVersionUID = 3569349804142194995L;

	//Entsprechend der internen Enumeration
	//Merke: Die Enumeration dient der Festlegung der Defaultwerte. In den Feldern des Entities werden die gespeicherten Werte gehalten.
	private String sDescr, sAbbr;
	
	
	public ExceptionHandlingMainTypeZZZ() {		
	}
	
	@Override
	public String getDescription(){
		return this.sDescr;
	}
	
	@Override
	public void setDescription(String sDescr){
		this.sDescr = sDescr;
	}
	
	@Override
	public String getAbbreviation(){
		return this.sAbbr;
	}
	
	@Override
	public void setAbbreviation(String sAbbr){
		this.sAbbr = sAbbr;
	}
	
	@Override
	public String toString() {
	    //return this.fullName+"="+this.abbr+"#";
		return this.sAbbr+"#"+this.sDescr;
	}


	//### Statische Methode (um einfacher darauf zugreifen zu können)
    public static Class getEnumClassStatic(){
    	try{
    		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Diese Methode muss in den daraus erbenden Klassen überschrieben werden.");
    	}catch(ExceptionZZZ ez){
			String sError = "ExceptionZZZ: " + ez.getMessageLast() + "+\n ThreadID:" + Thread.currentThread().getId() +"\n";			
			System.out.println(sError);
		}
    	return TypeZZZ.class;    	
    }
    
    
  //#######################################################
  //### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
  //### String fullName, String abbreviation
  //#######################################################
  			
  //Merke: Obwohl fullName und abbr nicht direkt abgefragt werden, müssen Sie im Konstruktor sein, um die Enumeration so zu definieren.
  //ALIAS("Beschreibung, wird nicht genutzt....","Abkürzung, also das, was im URL String steht. Meist gefolgt von einem  Doppelpunkt, der hinzugerchnet wird, wenn die Abkürzung nicht leer ist.")
  public enum TypeZZZ implements IEnumSetMappedZZZ {//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
	THROW("throw","throw the error normaly"),
  	CATCH_AWAY("catch_away","don´t throw an error. Maybe use 'break main"),
  	;

  	private String descr, abbr;

	//#############################################
	//#### Konstruktoren
	//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
	//In der Util-Klasse habe ich aber einen Workaround gefunden.
	TypeZZZ(String abbr, String descr) {
	    this.descr = descr;
	    this.abbr = abbr;
	}
	
	
	//the identifierMethod ---> Going in DB
	public String getAbbreviation() {
		return this.abbr;
	}
	
	public EnumSet<?>getEnumSetUsed(){
		return TypeZZZ.getEnumSet();
	}
	
	/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
	@SuppressWarnings("rawtypes")
	public static <E> EnumSet getEnumSet() {
	  	
		//Merke: Das wird anders behandelt als FLAGZ Enumeration.
		//String sFilterName = "FLAGZ"; /
		//...
		//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
		  	
		//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
		Class<TypeZZZ> enumClass = TypeZZZ.class;
		EnumSet<TypeZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
		  	
		Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
		for(Object obj : objaEnum){
			//System.out.println(obj + "; "+obj.getClass().getName());
			set.add((TypeZZZ) obj);
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
	public static TypeZZZ fromAbbreviation(String s) {
		for (TypeZZZ state : values()) {
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
	    return this.abbr+"#"+this.descr;
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

