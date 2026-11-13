package basic.zBasic.util.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.abstractList.IVector4XmlZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagSimpleZZZ;
import basic.zBasic.util.abstractList.Vector4XmlTagStringZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.tree.ITreeNodeZZZ;
import basic.zBasic.util.datatype.tree.TreeNodeZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.util.xml.tagsimple.TagSimpleZZZ;

/**Loese XML-Tags per Regulaerem Ausdruck auf.
 * Hole hier nur die Tags, ohne Werte.
 * Dabei werden keine Unterschiede in den Zweigen gemacht.
 * 
 * Ideen dazu hier:
 * https://colinchjava.github.io/2023-10-01/17-43-33-264642-matching-xml-tags-with-java-regular-expressions/
 * https://stackoverflow.com/questions/335250/parsing-xml-with-regex-in-java 
 *  
 * 
 * @author Fritz Lindhauer, 25.05.2024, 06:41:20
 */
public class XmlTagMatcherZZZ {
	public static Vector<String> parseAnyElementsAsVector(String sXml) throws ExceptionZZZ {
		return XmlTagMatcherZZZ.parseAnyElementsAsVector(sXml, true);
	}
	/**Hole einzelne Tag-Teile und ggfs. auch Textelemente als einzelne Bestandteile des Strings.
	 * Merke: So kriegt man nicht einen Tag mit seinen Werten hin...
	 *        Darum ist der Anwendungfall gering.
	 * @param sXml
	 * @param bWithText
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.06.2024, 17:09:43
	 */
	public static Vector<String> parseAnyElementsAsVector(String sXml, boolean bWithText) throws ExceptionZZZ {
		Vector<String> vecReturn = null;
		main:{
			if(sXml==null) break main;
			
			vecReturn = new Vector<String>();
			if(sXml.equals("")) break main;
			
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			 
			//##################################
			String regex = "<(.*?)>";
			
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt bleibt erhalten: 
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 	
			while (matcher.find()) {
			    String sTag = matcher.group(1);
			    sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
			    System.out.println("sLeft:\t\t" + sLeft);//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
			    System.out.println("sTag:\t\t"  + sTag);
			    
			    if(bWithText){			    	
			    	if(!StringZZZ.isEmpty(sLeft)) {
			    		vecReturn.add(sLeft);
			    	}
			    }else {
			    	
			    }
			    vecReturn.add("<" + sTag + ">");
			    
			    //Wenn man jetzt schon sXmlRemain verwendet, dann findet der Ausdruck zu Suche nach dem TagNamen nix mehr.
			    sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sTag+">");
			    System.out.println("sRemain:\t" + sXmlRemain);
			  
			    //weil man sich wg. sXmlRemain diesen Pattern sparen kann
			    //Pattern regex2 = Pattern.compile("<"+sTag +">(.*?)</"+sTag+">", Pattern.DOTALL);     
			    //Matcher matcher2 = regex2.matcher(sXmlRemain);
			    //while (matcher2.find()) {
			    //	String stemp = matcher2.group(1);
			    //	System.out.println("g1: '" + stemp+ "'");		    	
			    //}
			}
			
			
			//Den Rest hinzufuegen
			if(bWithText) {
				if(!StringZZZ.isEmpty(sXmlRemain)) {
					vecReturn.add(sXmlRemain);
				}
			}
		}//end main:
		return vecReturn;
	}
	
	/** Hole hier nur die Werte, ohne Tags.
	 * WICHTIG: Unterschiede im Zweig, in denen die gefundenen Tags stehen werden nicht beruecksichtigt!!!
	 * 
	 * Merke: Zentrale Methode fuer die anderen Methoden um zu einem identifizierten Tag den Wert zu holen.
	 * @param sXml
	 * @param sTag
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 30.05.2024, 07:45:11
	 */
	public static Vector<String>parseAnyValueForTagAsVector(String sXml, String sTag) throws ExceptionZZZ{
		Vector<String>vecReturn= null;
		main:{
			if(StringZZZ.isEmpty(sXml))break main;
			if(StringZZZ.isEmpty(sTag))break main;			
			vecReturn = new Vector<String>();
			
			String sXmlRemain = sXml;
			  
			//ACHTUNG: Damit werden alle Werte des gleich benannten Tags gefunden.
			//         Es wird nicht beruecksichtigt, ob sie in unterschiedlichen Zweigen sind.
			
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
		    	
		    	vecReturn.add(stemp);
		    }
		    
		    //LEERTAGS
		    /*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt: Pattern regex3 = Pattern.compile("<"+sTag +"/>");
		    Pattern regex3 = Pattern.compile(Pattern.quote("<"+sTag +"/>"));     
		    Matcher matcher3 = regex3.matcher(sXmlRemain);
		    while (matcher3.find()) {
		    	String stemp = matcher3.group(1);
		    	System.out.println("g1: '" + stemp+ "'");
		    	
		    	vecReturn.add(stemp);
		    }

			
		}//end main_
		return vecReturn;
	}
	
	//##################################################
	public static IVector4XmlZZZ<?> parseElementsAsVector(String sXml) throws ExceptionZZZ {
		return XmlTagMatcherZZZ.parseElementsAsVectorTagString(sXml);
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVector(String sXml, boolean bWithText) throws ExceptionZZZ{		
		return XmlTagMatcherZZZ.parseElementsAsVectorTagString(sXml, bWithText);
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVector(Vector4XmlTagStringZZZ<String> vec, String sXml, boolean bWithText) throws ExceptionZZZ{
		return XmlTagMatcherZZZ.parseElementsAsVectorTagString(vec, sXml, bWithText);
	}
	
	//############################################################################
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagString(String sXml) throws ExceptionZZZ {
		return parseElementsAsVector_(null, true, sXml, true);
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagString(String sXml, boolean bWithText) throws ExceptionZZZ{		
		return parseElementsAsVector_(null, true, sXml, bWithText);
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagString(Vector4XmlTagStringZZZ<String> vec, String sXml, boolean bWithText) throws ExceptionZZZ{
		return parseElementsAsVector_(vec, true, sXml, bWithText);
	}

	//############################################################################
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagSimple(String sXml) throws ExceptionZZZ{
		return parseElementsAsVector_(null, false, sXml, true); 		
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagSimple(String sXml, boolean bWithText) throws ExceptionZZZ{
		return parseElementsAsVector_(null, false, sXml, bWithText); 		
	}
	
	public static IVector4XmlZZZ<?> parseElementsAsVectorTagSimple(Vector4XmlTagSimpleZZZ<ITagSimpleZZZ> vec, String sXml, boolean bWithText) throws ExceptionZZZ{			
		return parseElementsAsVector_(vec, false, sXml, bWithText); 		
	}
	

	//+#+#+#+# Experiment: Nur 1x diese Methode, was wg. des Interface geht, statt die gleiche Methode 2x nur mit TagSimple, bzw. TagString
	/**Merke: Analog zu parseElementsAsMap_(...)
	 * 
	 * Merke: Das ist ziemlich generisch, so dass diese Methode 
	 *        a) Sowohl von VectorString als auch von VectorTagSimple aufgerufen werden kann
	 *        b) Eine Rekursion moeglich ist. Wichtig: Die Rekursion findet aus sich selbst statt, da man jetzt nicht weiss welche "Ausgangsmethode" man aufrufen soll.  
	 * 
	 * @param vectorReturnIn
	 * @param sXml
	 * @param bWithText
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.06.2024, 08:57:55
	 */
	private static IVector4XmlZZZ<?> parseElementsAsVector_(IVector4XmlZZZ<?> vecReturnIn, boolean bAsTagStringWhenVectorNull, String sXml, boolean bWithText) throws ExceptionZZZ{
		IVector4XmlZZZ<?> vecReturn = null;
		main:{
			boolean bVecFilledBefore;
			
			if(vecReturnIn!=null) {
				vecReturn = vecReturnIn;
				
				if(StringZZZ.isEmpty(sXml)) break main;
				
				bVecFilledBefore=true;//Falls jetzt nur noch der Wert vorhanden ist, kaeme sonst eind text-Tag drum.
                						//Das ist aber falsch, weil der Wert schon in dem XML-Wert vorhanden sein sollte.
			}else {
				if(sXml==null) break main;

				if(bAsTagStringWhenVectorNull) {
					vecReturn = new Vector4XmlTagStringZZZ<String>();
				}else {
					vecReturn = new Vector4XmlTagSimpleZZZ<ITagSimpleZZZ>();
				}
								
				if(sXml.equals("")) break main;
				
				bVecFilledBefore=false; //Nimm nun einzelstehende Werte mit einen text-Tag auf.
			}
			
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			String sContinuingClosingTag = null;
			boolean bParseNextTagOnThisLevel=true;
			 
			//##################################
			String regex = "<(.*?)>";
			
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt bleibt erhalten: 
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 	
			while (matcher.find()) {
				boolean bClosingTag = false;
			    String sValue = null;
			    String sTag = matcher.group(1);			    
			      	
			    if(sContinuingClosingTag!=null) {
			    	//Warte nun Darauf, dass der passende EndTag gefunden wird.
			    	if(sTag.equals(sContinuingClosingTag)) {
			    		bParseNextTagOnThisLevel=true;
			    	}else {
			    		bParseNextTagOnThisLevel=false;
			    	}
			    }else {
			    	bParseNextTagOnThisLevel=true;
			    }
			    
			    if(bParseNextTagOnThisLevel) {//alle "zwischentags des Werts wurden in Rekursion verarbeitet. Nun geht es zu neuen Werten.
				    //Also den/einen abschliessenden Tag nicht betrachten
			    	  if(!StringZZZ.startsWith(sTag, "/")) {
				    	
				    	
					    if(bWithText){
					    	//Nimm ggfs. existierende Texte VOR dem Tag auf.
							sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
						    System.out.println("sLeft:\t\t'" + sLeft+"'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
						    System.out.println("sTag:\t\t'"  + sTag+"'");
						    
					    	if(!StringZZZ.isEmpty(sLeft)) {
					    		vecReturn.put("text",sLeft);
					    	}
					    }else {
					    	
					    }
			    	
						//Ermittle die Werte fuer den Tag per RegEx und packe den Tag und den Wert in die Map
					    Vector<String>vecValue=XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sXmlRemain, sTag);
						if(vecValue!=null) {
						    if(!vecValue.isEmpty()) {
						    	sValue = vecValue.get(0);
						    	vecReturn.put(sTag, sValue);
						    	
						    	//TJA: und nun ggfs. in sValue vorhandene Tags per Rekursion "AUF SICH SELBST" aufloesen.....
						    	vecReturn = XmlTagMatcherZZZ.parseElementsAsVector_(vecReturn, bAsTagStringWhenVectorNull, sValue, bWithText);
						    	sContinuingClosingTag = "/"+sTag;
						    	//Der naechste Tag ist in der While Schleife entweder ein Tag "dazwischen" oder der abschliessende Tag
						    }else {
						    	sValue = null; //Fazit: Leere Wertvektoren nicht übernehmen, das ist wichtig, sonst wird der Index der aeusseren HashMap auch noch um 1 erhoeht.
						    }				    					    					    				    					    	
					    }
				    }else {
				    	bClosingTag = true;	
				    					    	
				    	//Text nach einem abschliessenden Text erfassen, bis zu einem anderen abschliessenden Tag			
				    	//Erst nach der Bestimmung des Werts sXmlRemain errechnen.			    
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sContinuingClosingTag+">");
			   
				    	System.out.println("sRemain:\t'" + sXmlRemain+"'");
						if(StringZZZ.isEmpty(sXmlRemain)) break main;
	
						sContinuingClosingTag=null;
				    }//end if( Abschliessende Tags ausschliessen )
			    
			    }//end if (ParseNextTagOnThisLevel)
			    
			   
			   if(bClosingTag && bWithText) {

			    	sLeft = StringZZZ.left(sXmlRemain + "<", "<");
				    System.out.println("sRightClosing:\t'" + sLeft + "'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
				   
				    if(!StringZZZ.isEmpty(sLeft)) {
				    	vecReturn.put("text",sLeft);
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, sLeft);
				    }else {
				    	
				    }
				   
			   }
			}//end while (matcher.find()) {
				
			//Den Rest hinzufuegen
			if(bWithText) {
				if(bVecFilledBefore && !sXml.equals(sXmlRemain)) { //Damit sollen einzelstehnde Werte zwar erfasst werden, aber nicht wenn sie schon in einem Tag vorhanden sind.
					if(!StringZZZ.isEmpty(sXmlRemain)) {
						vecReturn.put("text", sXmlRemain);
					}
				}else if(!bVecFilledBefore) {
					if(!StringZZZ.isEmpty(sXmlRemain)) {  //Das ist dann der Fall für nur einzelstehende Werte OHNE ein Tag.
						vecReturn.put("text", sXmlRemain);
					}
				}
			}
		}//end main:
		return vecReturn;
	}
	
	
	//#############################################################################
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(String sXml) throws ExceptionZZZ {
		return XmlTagMatcherZZZ.parseElementsAsMap(sXml, true);
	}
	
	/**Gib eine HashMap zurueck mit Tag als Schluessel und String als Wert.
	 * 
	 * 
	 * @param sXml
	 * @param bWithText
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.05.2024, 09:08:35
	 */
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(String sXml, boolean bWithText) throws ExceptionZZZ {
		return parseElementsAsMap_(null, sXml, bWithText);
	}
	
	public static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap(HashMapMultiIndexedZZZ<String,String> hmReturnIn, String sXml, boolean bWithText) throws ExceptionZZZ {
		return parseElementsAsMap_(hmReturnIn, sXml, bWithText);
	}
	
	/**Merke: Analog zu parseElementsAsVector_(...)
	 * @param hmReturnIn
	 * @param sXml
	 * @param bWithAnyValue
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.06.2024, 08:58:24
	 */
	private static HashMapMultiIndexedZZZ<String,String> parseElementsAsMap_(HashMapMultiIndexedZZZ<String,String> hmReturnIn, String sXml, boolean bWithText) throws ExceptionZZZ {
		HashMapMultiIndexedZZZ<String,String> hmReturn = null;
		main:{		
			boolean bMapFilledBefore;
			if(hmReturnIn!=null) {
				hmReturn = hmReturnIn;
				if(StringZZZ.isEmpty(sXml)) break main;
				
				bMapFilledBefore=true;//Falls jetzt nur noch der Wert vorhanden ist, kaeme sonst eind text-Tag drum.
				                      //Das ist aber falsch, weil der Wert schon in dem XML-Wert vorhanden sein sollte.
			}else {
				if(sXml==null) break main;				
				hmReturn = new HashMapMultiIndexedZZZ<String,String>();
				
				if(sXml.equals("")) break main;
				bMapFilledBefore=false; //Nimm nun einzelstehende Werte mit einen text-Tag auf.
			}
			
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			String sContinuingClosingTag = null;
			boolean bParseNextTagOnThisLevel=true;
			 
			//##################################
			String regex = "<(.*?)>";
			
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt bleibt erhalten: 
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 	
			while (matcher.find()) {
				boolean bClosingTag = false;
			    String sValue = null;
			    String sTag = matcher.group(1);			    
			      	
			    if(sContinuingClosingTag!=null) {
			    	//Warte nun Darauf, dass der passende EndTag gefunden wird.
			    	if(sTag.equals(sContinuingClosingTag)) {
			    		bParseNextTagOnThisLevel=true;
			    	}else {
			    		bParseNextTagOnThisLevel=false;
			    	}
			    }else {
			    	bParseNextTagOnThisLevel=true;
			    }
			    
			    if(bParseNextTagOnThisLevel) {//alle "zwischentags des Werts wurden in Rekursion verarbeitet. Nun geht es zu neuen Werten.
				    //Also den/einen abschliessenden Tag nicht betrachten				    
			    	if(!StringZZZ.startsWith(sTag, "/")) {				    					    
					    if(bWithText){
					    	//Nimm ggfs. existierende Texte VOR dem Tag auf.
							sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
						    System.out.println("sLeft:\t\t'" + sLeft+"'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
						    System.out.println("sTag:\t\t'"  + sTag+"'");
						    
					    	if(!StringZZZ.isEmpty(sLeft)) {
					    		hmReturn.put("text",sLeft);
					    	}
					    }else {
					    	
					    }
			    	
						//Ermittle die Werte fuer den Tag per RegEx und packe den Tag und den Wert in die Map
					    Vector<String>vecValue=XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sXmlRemain, sTag);
						if(vecValue!=null) {
						    if(!vecValue.isEmpty()) {
						    	sValue = vecValue.get(0);
						    	hmReturn.put(sTag, sValue);
						    	
						    	//TJA: und nun ggfs. in sValue vorhandene Tags per Rekursion aufloesen.....
						    	hmReturn = XmlTagMatcherZZZ.parseElementsAsMap(hmReturn, sValue, bWithText);
						    	sContinuingClosingTag = "/"+sTag;
						    	//Der naechste Tag ist in der While Schleife entweder ein Tag "dazwischen" oder der abschliessende Tag
						    }else {
						    	sValue = null; //Fazit: Leere Wertvektoren nicht übernehmen, das ist wichtig, sonst wird der Index der aeusseren HashMap auch noch um 1 erhoeht.
						    }				    					    					    				    					    	
					    }
				    }else {
				    	bClosingTag = true;	
				    					    	
				    	 //Text nach einem abschliessenden Text erfassen, bis zu einem anderen abschliessenden Tag			
						 //Erst nach der Bestimmung des Werts sXmlRemain errechnen.
						 sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sContinuingClosingTag+">");
						 
						 System.out.println("sRemain:\t'" + sXmlRemain+"'");
						 if(StringZZZ.isEmpty(sXmlRemain)) break main;
	
						 sContinuingClosingTag=null;
				    }//end if( Abschliessende Tags ausschliessen )
			    
			    }//end if (ParseNextTagOnThisLevel)
			    
			   
			   if(bClosingTag && bWithText) {

			    	sLeft = StringZZZ.left(sXmlRemain + "<", "<");
				    System.out.println("sRightClosing:\t'" + sLeft + "'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
				   
				    if(!StringZZZ.isEmpty(sLeft)) {
				    	hmReturn.put("text",sLeft);
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, sLeft);
				    }else {
				    	
				    }
				   
			   }
			}//end while (matcher.find()) {
				
			//Den Rest hinzufuegen
			if(bWithText) {
				if(bMapFilledBefore && !sXml.equals(sXmlRemain)) { //Damit sollen einzelstehnde Werte zwar erfasst werden, aber nicht wenn sie schon in einem Tag vorhanden sind.
					if(!StringZZZ.isEmpty(sXmlRemain)) {
						hmReturn.put("text", sXmlRemain);
					}
				}else if(!bMapFilledBefore) {
					if(!StringZZZ.isEmpty(sXmlRemain)) {  //Das ist dann der Fall für nur einzelstehende Werte OHNE ein Tag.
						hmReturn.put("text", sXmlRemain);
					}
				}
			}
		}//end main:
		return hmReturn;
	}
	
	
	//#################################################
	public static ITreeNodeZZZ<ITagSimpleZZZ> parseElementsAsTree(String sXml) throws ExceptionZZZ {
		return parseElementsAsTree_(null, sXml, true);
	}
	
	public static ITreeNodeZZZ<ITagSimpleZZZ> parseElementsAsTree(String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		return parseElementsAsTree_(null, sXml, bWithAnyValue);
	}
	
	public static ITreeNodeZZZ<ITagSimpleZZZ> parseElementsAsTree(ITreeNodeZZZ<ITagSimpleZZZ> objParentNode, String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		return parseElementsAsTree_(objParentNode, sXml, bWithAnyValue);
	}
	
	private static ITreeNodeZZZ<ITagSimpleZZZ> parseElementsAsTree_(ITreeNodeZZZ<ITagSimpleZZZ> objParentNodeIn, String sXml, boolean bWithAnyValue) throws ExceptionZZZ {
		ITreeNodeZZZ<ITagSimpleZZZ> objReturn = null;
		main:{	
			
			
			boolean bMapFilledBefore;
			if(objParentNodeIn!=null) {
				bMapFilledBefore=true;
				objReturn = objParentNodeIn;
				
				if(StringZZZ.isEmpty(sXml)) break main;
			}else{
				if(sXml==null) break main;
				
				bMapFilledBefore=false;
				
				//Wie bei Vektor und HashMap auch einen leeren Root-Knoten auf jeden Fall zurückgeben. 
				//DAS IST AUCH DER ROOT ALLER KNOTEN UND ER VERWALTET DEN SUCHINDEX ohne selbst data zu haben!!!
				objReturn = new TreeNodeZZZ<ITagSimpleZZZ>(); //Wenn es noch einen wirklichen Knoten gibt, dann wird der zum objReturn!!!
				if(sXml.equals("")) break main;
			}
						
			//siehe in den Tryouts die Loesungsschritte hierfuer:
			String sXmlRemain = sXml;
			String sLeft;
			String sContinuingClosingTag = null;
			boolean bParseNextTagOnThisLevel=false;
			boolean bParseFirstTagOnThisLevel=false;
			
			ITreeNodeZZZ<ITagSimpleZZZ> objNodeCurrent = null;
			ITreeNodeZZZ<ITagSimpleZZZ> objNodeMain = null;
			
			//##################################
			String regex = "<(.*?)>";
			
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt bleibt erhalten: 
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(sXml);	 							
			while (matcher.find()) {
				boolean bClosingTag = false;
			    String sValue = null;
			    //objNodeCurrent = null;
			    //objNodeMain = null;
			    String sTag = matcher.group(1);			    
			      	
			    if(sContinuingClosingTag!=null) {
			    	//Warte nun Darauf, dass der passende EndTag gefunden wird.
			    	if(sTag.equals(sContinuingClosingTag)) {
			    		bParseNextTagOnThisLevel=true;
			    	}else {
			    		bParseNextTagOnThisLevel=false;
			    	}
			    	bParseFirstTagOnThisLevel=false;
			    }else {
			    	bParseNextTagOnThisLevel=true;
			    	bParseFirstTagOnThisLevel=true;
			    }
			    
			    if(bParseNextTagOnThisLevel) {//alle "zwischentags des Werts wurden in Rekursion verarbeitet. Nun geht es zu neuen Werten.
				    //Also den/einen abschliessenden Tag nicht betrachten				   
			    	if(!StringZZZ.startsWith(sTag, "/")) {
				    	
			    		ITagSimpleZZZ objTagTextBefore=null;
					    if(bWithAnyValue){
					    	//Nimm ggfs. existierende Texte VOR dem Tag auf.
							sLeft = StringZZZ.left(sXmlRemain, "<" + sTag + ">");
						    System.out.println("sLeft:\t\t'" + sLeft+"'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
						    System.out.println("sTag:\t\t'"  + sTag+"'");
						    
					    	if(!StringZZZ.isEmpty(sLeft)) {
					    		
					    		//Definiere den Tag VOR dem eigentlichen Tag.					    		
					    		objTagTextBefore = new TagSimpleZZZ("text",sLeft); 
					    		
					    		//Definiere den neuen Knoten
					    		//Das ist aber nicht extra notwendig, sondern wird beim Hinzufuegen gemacht.
					    		//TreeNodeZZZ<ITagSimpleZZZ> objNodeText = new TreeNodeZZZ<ITagSimpleZZZ>(objTagText);
					    	}					    	
					    }else {
					    	
					    }
			    	
						//Ermittle die Werte fuer den Tag per RegEx und packe den Tag und den Wert in die Map
					    Vector<String>vecValue=XmlTagMatcherZZZ.parseAnyValueForTagAsVector(sXmlRemain, sTag);
						if(vecValue!=null) {
						    if(!vecValue.isEmpty()) {
						    	sValue = vecValue.get(0);
						    	
						    	//Definiere den Tag.					    		
					    		ITagSimpleZZZ objTagValue = new TagSimpleZZZ(sTag,sValue); 
					    		
					    		//Haenge den neuen Knoten unter den aktuellen Konten
					    		if(bMapFilledBefore==false) { //Es wurde also KEIN objReturn in diese Methode uebergeben.
					    			
					    			//objReturn als Root ist aber immer existent
					    			objNodeCurrent = objReturn.addChild(objTagValue);
					    			
					    		}else {
					    			objNodeCurrent = objReturn.addChild(objTagValue);
					    		}
					    						    		
					    		//Haenge einen neuen Knoten mit dem TextBefore NEBEN den aktuellen Konten aber Vorne als erste Sibling
					    		if(objTagTextBefore!=null) {
					    			if(objNodeMain!=null) {
						    			objNodeMain.addSiblingAsFirst(objTagTextBefore);								    			
					    			}else {
					    				objNodeCurrent.addSiblingAsFirst(objTagTextBefore);
					    			}
					    			objTagTextBefore = null; //Wieder auf null setzen, sonst wird der noch einmal verarbeitet
								}					    		
					    		
					    		//Wenn man objNodeCurrent übergibt, dann auf gar keinen objReturn neu ueberschreiben objReturn = XmlTagMatcherZZZ.parseElementsAsTree(objNodeMain, sValue, bWithAnyValue);
					    		objNodeCurrent = XmlTagMatcherZZZ.parseElementsAsTree(objNodeCurrent, sValue, bWithAnyValue);
					    		
						    	sContinuingClosingTag = "/"+sTag;
						    	//Der naechste Tag ist in der While Schleife entweder ein Tag "dazwischen" oder der abschliessende Tag
						    }else {
						    	sValue = null; //Fazit: Leere Wertvektoren nicht übernehmen, das ist wichtig, sonst wird der Index der aeusseren HashMap auch noch um 1 erhoeht.
						    }				    					    					    				    					    	
					    }
						
						//Haenge den neuen Knoten mit dem TextBefore unter den aktuellen Konten
						//Hier der Fall, dass es gar keinen Tag gibt
						if(objTagTextBefore!=null) {
				    		if(objReturn==null) {
				    			objReturn = new TreeNodeZZZ<ITagSimpleZZZ>(objTagTextBefore);
				    		}else {
				    			if(bMapFilledBefore==false) {
				    				//Auf Root Ebene als Sibling
				    				//ABER: Auf Root Ebene gibt es keine Siblings!!!
				    				objReturn.addSiblingAsFirst(objTagTextBefore);
				    				System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Auf Root Ebene gibt es keine Sibling - Werte");
				    			}else {
					    			if(objNodeMain!=null) {
					    				objNodeMain.addSiblingAsFirst(objTagTextBefore);
					    			}else {
					    				objReturn.addSiblingAsFirst(objTagTextBefore);
					    			}
				    			}
				    		}
						}
						
				    }else {
				    	bClosingTag = true;	
				    					    	
				    	//Text nach einem abschliessenden Text erfassen, bis zu einem anderen abschliessenden Tag			
				    	//Erst nach der Bestimmung des Werts sXmlRemain errechnen.
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, "<"+sContinuingClosingTag+">");
						
				    	System.out.println("sRemain:\t'" + sXmlRemain+"'");
						if(StringZZZ.isEmpty(sXmlRemain)) break main;
	
						sContinuingClosingTag=null;
				    }//end if( Abschliessende Tags ausschliessen )
			    
			    }//end if (ParseNextTagOnThisLevel)
			    
			   
			   if(bClosingTag && bWithAnyValue) {

			    	sLeft = StringZZZ.left(sXmlRemain + "<", "<");
				    System.out.println("sRightClosing:\t'" + sLeft + "'");//so etwas ohne Tag muss in ein Tag vom ITagTypeZZZ "Text"
				   
				    if(!StringZZZ.isEmpty(sLeft)) {
				    	//hmReturn.put("text",sLeft);
				    	
				    	//Definiere den Tag.					    		
			    		ITagSimpleZZZ objTagText = new TagSimpleZZZ("text",sLeft); 
			    		
			    		//Definiere den neuen Knoten
			    		//Das ist nicht extra notwendig, sondern wird beim Hinzufuegen gemacht.
			    		//TreeNodeZZZ<ITagSimpleZZZ> objNodeText = new TreeNodeZZZ<ITagSimpleZZZ>(objTagText);
			    		
			    		//Haenge den neuen Knoten unter den aktuellen Konten
			    		if(objReturn==null) {
			    			objReturn = new TreeNodeZZZ<ITagSimpleZZZ>(objTagText);
			    		}else {
			    			if(bMapFilledBefore==false) {
			    				//Auf Root Ebene als Sibling
			    				//ABER: Auf Root Ebene gibt es keine Siblings!!!
			    				//objReturn.addSibling(objTagText);
			    				//System.out.println(ReflectCodeZZZ.getPositionCurrent() + "Auf Root Ebene gibt es keine Sibling - Werte");
			    				objNodeCurrent.addSibling(objTagText);
			    			}else {			    				
			    				//In den Hauptknoten dieser Ebene als sibling
			    				//Merke: Dann kommt automatisch das Unterelement als Child dessen Parent			    
			    				//Merke: Das aktuelle Parent - Element wurde als objReturn übergeben.
			    				if(objNodeMain==null) {
			    					objNodeCurrent.addSibling(objTagText);
			    				}else {
			    					objNodeMain.addSibling(objTagText);
			    				}
			    			}			    			
			    		}
				    					    	
				    	sXmlRemain = StringZZZ.rightback(sXmlRemain, sLeft);
				    }else {
				    	
				    }
				   
			   }
			}//end while (matcher.find()) {
				
			//Den Rest hinzufuegen
			if(bWithAnyValue) {
				if(bMapFilledBefore && !sXml.equals(sXmlRemain)) { //Damit sollen einzelstehnde Werte zwar erfasst werden, aber nicht wenn sie schon in einem Tag vorhanden sind.
					if(!StringZZZ.isEmpty(sXmlRemain)) {
						//hmReturn.put("text", sXmlRemain);
						
						//Definiere den Tag.					    		
			    		ITagSimpleZZZ objTagText = new TagSimpleZZZ("text",sXmlRemain); 
			    		
			    		//Definiere den neuen Knoten
			    		//Das ist nicht extr notwendig, sondern wird beim Hinzufuegen gemacht.
			    		//TreeNodeZZZ<ITagSimpleZZZ> objNodeText = new TreeNodeZZZ<ITagSimpleZZZ>(objTagText);
			    		
			    		//Haenge den neuen Knoten auf gleiche Ebene wie den aktuellen Konten
		    			objReturn.addSibling(objTagText); //Also Erweiterung: Auf gleicher Ebene einfuegen.
		    		}									
				}else if(!bMapFilledBefore) {
					if(!StringZZZ.isEmpty(sXmlRemain)) {  //Das ist dann der Fall für nur einzelstehende Werte OHNE ein Tag.
						//hmReturn.put("text", sXmlRemain);
						
						//Definiere den Tag.					    		
			    		ITagSimpleZZZ objTagText = new TagSimpleZZZ("text",sXmlRemain); 
			    		
			    		//Definiere den neuen Knoten
			    		//Das ist wohl extra nicht notwendig, sondern wird beim Hinzufuegen gemacht.
			    		//TreeNodeZZZ<ITagSimpleZZZ> objNodeText = new TreeNodeZZZ<ITagSimpleZZZ>(objTagText);
			    		
			    		//Haenge den neuen Knoten auf gleiche Ebene wie den aktuellen Konten
		    			if(objReturn.isRoot()) {
		    				objReturn.addChild(objTagText);
		    			}else {
		    				objReturn.addSibling(objTagText); //Also Erweiterung: Auf gleicher Ebene einfuegen.
			    		}
					}
				}
			}
		}//end main:
		return objReturn;
	}
	
}
