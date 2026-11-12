package basic.zBasic.util.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class XmlTagMatcherZZZTryout {

	public static void main(String args[]) {
		String sXml;
		
		try {
			//Erster Loesungsbaustein: Parse bekannten Tagnamen
			//sXml="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";			
			//XmlTagMatcherZZZTryout.parseByRegexV01(sXml);
			
			//#############################################################
			sXml = "Wert vor abc<abc>wert vor b<b>Wert in b</b>wert nach b</abc>wert nach abc";
			
			//Zweiter Loesungsbaustein: Parse unbekannten Tagnamen			
			//XmlTagMatcherZZZTryout.parseByRegexV02(sXml);
			
			//Dritter Loesungsbaustein: 1+2 kombiniert
			XmlTagMatcherZZZTryout.parseByRegexV03(sXml);
			
			//Viert Loesungsbaustein: 1+2 Kombiniert UND Verwende Textbestandteile, die keinem Tag zugeordnet sind.			
			//XmlTagMatcherZZZTryout.parseByRegexV04(sXml);
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**siehe https://stackoverflow.com/questions/335250/parsing-xml-with-regex-in-java
	 * @param sXml
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 24.05.2024, 18:03:07
	 */
	public static void parseByRegexV01(String sXml) throws ExceptionZZZ{
		
		main:{
			
		//FGL: Das scheint nur zu funktionieren, wenn man den ersten Tag kennt, beim Namen
		//UND WICHTIG: ES dürfen keine Elemente zwischen den Tags stehen.
		//Funktioniert also:
		//sTest="<DataElements><EmpStatus>2.0</EmpStatus><Expenditure>95465.00</Expenditure><StaffType>11.A</StaffType><Industry>13</Industry></DataElements> <InteractionElements><TargetCenter>92f4-MPA</TargetCenter><Trace>7.19879</Trace></InteractionElements>";
		//
		//Funktioniert also nicht fuer Strings wie:
		//sTest = "was da <abc>wert fuer<b>B ist </b> </abc>";
		
		 /*    RegEx:    	
	    Zwei kleine Tipps für deine Methode
	    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
	    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
	     */
		//es bleibt beim alt: 
		Pattern regex = Pattern.compile("<DataElements>(.*?)</DataElements>", Pattern.DOTALL);
		//Pattern regex = Pattern.compile("<abc>(.*?)</abc>", Pattern.DOTALL);
		Matcher matcher = regex.matcher(sXml);
		Pattern regex2 = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
		if (matcher.find()) {
		    String DataElements = matcher.group(1);
	    	System.out.println(DataElements);
		    Matcher matcher2 = regex2.matcher(DataElements);
		    while (matcher2.find()) {
		        //list.add(new DataElement(matcher2.group(1), matcher2.group(2)));
		    	String stemp = matcher2.group(2);
		    	System.out.println(stemp);
		    	
		    } 
		}
		
	}//end main:
	
	}
	
/** siehe: 
 * @param sXml
 * @throws ExceptionZZZ
 * @author Fritz Lindhauer, 24.05.2024, 18:03:36
 */
public static void parseByRegexV02(String sXml) throws ExceptionZZZ{
		
		main:{
			
	 //String sXml = "<bookstore><book><title>Java Programming</title><author>John Doe</author></book></bookstore>";
	//sTest = "was da <abc>wert fuer<b>B ist </b> </abc>";

	//Fazit: Mit diesem Ausdruck kann man auch unbekannte Tags ermitteln, 
	//       Die sogar vorher und nachher normalen Text haben.
	//       Das könnte man nutzen um so eine Liste der Tags als Struktur zu bekommen und Werte auch noch.
	
     String regex = "<(.*?)>";

     /*    RegEx:    	
	    Zwei kleine Tipps für deine Methode
	    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
	    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
	     */
		//es bleibt beim alt: 
     Pattern pattern = Pattern.compile(regex);
     Matcher matcher = pattern.matcher(sXml);

     while (matcher.find()) {
         String tag = matcher.group(1);
         System.out.println(tag);
     }
		
	}//end main:
	
	}
	
public static void parseByRegexV03(String sXml) throws ExceptionZZZ{
	
	main:{
		
	 //String sXml = "<bookstore><book><title>Java Programming</title><author>John Doe</author></book></bookstore>";
	//sTest = "was da <abc>wert fuer<b>B ist </b> </abc>";
	
	//Fazit: Mit diesem Ausdruck kann man auch unbekannte Tags ermitteln, 
	//       Die sogar vorher und nachher normalen Text haben.
	//       Das könnte man nutzen um so eine Liste der Tags als Struktur zu bekommen und Werte auch noch.

	String regex = "<(.*?)>";

	 /*    RegEx:    	
    Zwei kleine Tipps für deine Methode
    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
     */
	//es bleibt beim alt: 
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(sXml);
 
	// Pattern regex2 = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
	while (matcher.find()) {
	    String tag = matcher.group(1);
	    System.out.println(tag);
	     
	    /*    RegEx:    	
	    Zwei kleine Tipps für deine Methode
	    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
	    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
	     */
		//alt: Pattern regex2 = Pattern.compile("<"+tag +">(.*?)</"+tag+">", Pattern.DOTALL);   
	    //neu:
	    Pattern regex2 = Pattern.compile(Pattern.quote("<"+tag +">(.*?)</"+tag+">"), Pattern.DOTALL);
	    Matcher matcher2 = regex2.matcher(sXml);
		    while (matcher2.find()) {
		        //list.add(new DataElement(matcher2.group(1), matcher2.group(2)));
		    	String stemp = matcher2.group(1);
		    	System.out.println("g1: '" + stemp+ "'");
		    	
		    	//IndexOutOfbounds   stemp = matcher2.group(2);
		    	//System.out.println("g2: '" + stemp + "'");
		    	
		    }
		}
	}//end main:
}
 
 
 public static void parseByRegexV04(String sXml) throws ExceptionZZZ{
		
		main:{					
			 String sXmlRemain = sXml;
			 String sXmlLeft, sXmlRight;
			 
			 //##################################
			 String regex = "<(.*?)>";
			 
			 /*    RegEx:    	
			    Zwei kleine Tipps für deine Methode
			    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
			    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
			     */
				//es bleibt beim alt: 
			 Pattern pattern = Pattern.compile(regex);
			 Matcher matcher = pattern.matcher(sXml);	 	
			 while (matcher.find()) {
			     String sTag = matcher.group(1);
			     sXmlLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
			     System.out.println("sLeft:\t\t" + sXmlLeft);//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
			     System.out.println("sTag:\t\t"  + sTag);
			     
			     sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">");
			     System.out.println("sRemain:\t" + sXmlRemain);

			     //!!!!!!!!!!!!!
			     //!!! Merke: mit dem Verwenden von sXmlRemain findet dieser Ausdruck nichts mehr
			     
			     /*    RegEx:    	
				    Zwei kleine Tipps für deine Methode
				    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
				    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
				     */
					//alt: Pattern regex2 = Pattern.compile("<"+sTag +">(.*?)</"+sTag+">", Pattern.DOTALL);     
			     //neu:
			     Pattern regex2 = Pattern.compile(Pattern.quote("<"+sTag +">(.*?)</"+sTag+">"), Pattern.DOTALL);
			     Matcher matcher2 = regex2.matcher(sXmlRemain);
				    while (matcher2.find()) {
				    	String stemp = matcher2.group(1);
				    	System.out.println("g1: '" + stemp+ "'");		    	
				    }
			 }
	}//end main:

}
	
}
