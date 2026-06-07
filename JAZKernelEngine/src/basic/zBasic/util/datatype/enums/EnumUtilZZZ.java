package basic.zBasic.util.datatype.enums;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;

/**Merksatz (wichtig!) (Von ChatGPT)
 * Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
 * auch wenn das Enum dieses Interface implementiert.
 *   
 * @author Fritz Lindhauer, 10.01.2026, 08:15:10
 * 
 */
public class EnumUtilZZZ {
	public static String[] toString(Enum[] enuma) throws ExceptionZZZ {
		String[] saReturn = null;
		main:{
			if(ArrayUtilZZZ.isNull(enuma)) break main;
			
			ArrayList<String>listasReturn = new ArrayList<String>();
			for(Enum e : enuma) {
				String s = e.toString();
				listasReturn.add(s);
			}
			
			saReturn = ArrayListUtilZZZ.toStringArray(listasReturn);
		}//end main:
		return saReturn;
	}
	
	public static <E extends IEnumSetMappedZZZ> ArrayList<E> toArrayListMapped(Enum[] enuma) throws ExceptionZZZ {	
		ArrayList<E> listaeReturn = null;
		main:{
			if(!ArrayUtilZZZ.isNull(enuma)) {	
				listaeReturn = new ArrayList<E>();
				for(Enum objEnum : enuma) {								
					E e = (E) objEnum;
					if(!listaeReturn.contains(e)) {
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sEnum= '" + sEnum + "' (" + cls.getName() + ")" );
						listaeReturn.add(e);
					}
				}			
			}
		}//end main:
		return listaeReturn;
	}
	
	/**Parse ein enum hinsichtlich aller Wert und vergleiche es mit einem String Wert.
	 * Hier wird eine Exception geworfen, falls der Wert nicht enthalten ist.
	 * Ist halt eine Form der Eingabevalidierung.
	 *
	 * Beispiel; 
	 	PegPartNode pegPartNode =
        findEnumIgnoreCase(
                PegPartNode.class,
                args[3]);
	 * @param enumClass
	 * @param value
	 * @return
	 * 
	 Merke: Meine Idee war folgendes, danach von ChatGPT verfeinert und generisch gemacht:
	 
	 		  //Gehe in einer Schleife von 1 bis 6 alle enums durch 
			  //Vergleiche dann den Namen mit sPegElementNode
			  //Bei Gleichstand hat man das enum gefunden und der Eingabewert war gültig...
			  
			  //Wie nun auf das Enum kommen???
			  //https://stackoverflow.com/questions/8108980/java-using-enum-with-switch-statement
			  
			  String sPegElementNode = null;
			  for(int iPegElementNode = 0; iPegElementNode<=ISfsStructureParser.PegPartNode.values().length-1; iPegElementNode++) {				  
				  ISfsStructureParser.PegPartNode whichEnumPegPartNode = ISfsStructureParser.PegPartNode.values()[iPegElementNode];
				  switch(whichEnumPegPartNode) {
				  case back:
					  if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();
					  break;
				  case front:
					  if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();
					  break;
				  case bottom:
					  if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();
					  break;
				  case top:
					  if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();				  
				  	   break;
				  case left:
					  	if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();					  
					  	break;
				  case right:
					  	if(sPegElementNodeIn.equalsIgnoreCase(whichEnumPegPartNode.name())) sPegElementNode = sPegElementNodeIn.toLowerCase();
					  	break;
				  default:{
					//System.out.println("Dieser Typ wird nicht behandelt: '" + whichEnumPegPartNode.name() + "'" );
					  throw new IllegalArgumentException("Dieser Typ wird nicht behandelt: '" + sPegElementNodeIn + "'" );					  
				  }
				  if(sPegElementNode!=null) break;// for Schleife verlassen
			  }
	 */
	public static <E extends Enum<E>> E parseEnumIgnoreCase(
	        Class<E> enumClass,
	        String value) {

	    if(value == null) {
	        throw new IllegalArgumentException("Enum-Wert darf nicht null sein.");
	    }

	    for(E enumValue : enumClass.getEnumConstants()) {
	        if(enumValue.name().equalsIgnoreCase(value)) {
	            return enumValue;
	        }
	    }

	    throw new IllegalArgumentException(
	            "Ungültiger Wert '" + value
	            + "' für Enum " + enumClass.getSimpleName());
	}
	
	
	/**Parse ein enum hinsichtlich aller Wert und vergleiche es mit einem String Wert.
	 * Hier wird keine Exception geworfen, falls der Wert nicht enthalten ist.
	 * Anders als bei parse... ist halt keine direkte Form der Eingabevalidierung.
	 *
	 * Beispiel:
	    DayOfWeek day =
        parseEnumIgnoreCase(
                DayOfWeek.class,
                "MONDAY");

	 * @param enumClass
	 * @param value
	 * @return
	 */
	public static <E extends Enum<E>> E findEnumIgnoreCase(
	        Class<E> enumClass,
	        String value) {

	    if(value == null) {
	        throw new IllegalArgumentException("Enum-Wert darf nicht null sein.");
	    }

	    for(E enumValue : enumClass.getEnumConstants()) {
	        if(enumValue.name().equalsIgnoreCase(value)) {
	            return enumValue;
	        }
	    }

	    return null;
	}
}
