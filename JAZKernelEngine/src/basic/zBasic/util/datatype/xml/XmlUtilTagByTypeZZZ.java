package basic.zBasic.util.datatype.xml;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;

public class XmlUtilTagByTypeZZZ implements IConstantZZZ{

	//+++++++++++++++++++++++++++++
	/**FGL: Der intern verwendete RegEx funktioniert nur, wenn man den ersten Tag kennt, beim Namen
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.06.2024, 11:17:35
	 */
	public static ITagByTypeZZZ getTagNextFromFactoryByName(String sTagName, String sXml) throws ExceptionZZZ {
		ITagByTypeZZZ objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sTagName)) break main;
			if(StringZZZ.isEmpty(sXml)) break main;
			
			//StringZZZ.left(sXml, XmlUtilZZZ.sTagClosing )		
			//Pattern regex = Pattern.compile("<DataElements>(.*?)</DataElements>", Pattern.DOTALL);
			String sTagOpening = XmlUtilZZZ.computeTagPartOpening(sTagName); 
			String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt: Pattern regex = Pattern.compile(sTagOpening + "(.*?)" + sTagClosing, Pattern.DOTALL);
			//neu: 
			Pattern regex = Pattern.compile(Pattern.quote(sTagOpening + "(.*?)" + sTagClosing), Pattern.DOTALL);
			Matcher matcher = regex.matcher(sXml);
			
			/*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//aber es bleibt beim alten:
			Pattern regex2 = Pattern.compile("<([^<>]+)>([^<>]+)</\\1>");
			if (matcher.find()) {
			    String DataElements = matcher.group(1);
		    	System.out.println(DataElements);
		    	
		    	//Erstelle erst das Tag-Objekt, wenn etwas gefunden wurde.
		    	objReturn = TagByTypeFactoryZZZ.createTagByName(sTagName);
				
		    	//Sichere den TagWert
		    	objReturn.setValue(DataElements);
		    	
		    	//FGL: Was soll das dann??? ist das wichtig??? Wahrscheinlich nur fuer ineinander verschachtelte Tags
		    	//sExpression = "<Z:Call><filename><filename>{[ArgumentSection for testCallComputed]JavaClass}</filename><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></filename></Z:Call>";
		    	//ABER: DAS Klappt NUR wenn man den ClosingTag ergänzt!!!
			    Matcher matcher2 = regex2.matcher(DataElements+sTagClosing);
			    while (matcher2.find()) {
			        //list.add(new DataElement(matcher2.group(1), matcher2.group(2)));
			    	String stemp = matcher2.group(2);			    	
			    	System.out.println(stemp);
			    	
			    	//Sichere den TagWert
			    	objReturn.setValue(stemp);
			    	
			    	//Nimm nur den erste Tag?
			    	//NEIN: Dann klappt es auch mit verschachtelten gleichen Tags.
			    	//break;
			    } 
			}
			
		}//end main:
		return objReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	public static Vector3ZZZ<String> parseFirstVector(String sExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();		
		main:{
			if(objTagType==null) break main;
			
			if(StringZZZ.contains(sExpression, objTagType.getTagPartEmpty())) {
				vecReturn = StringZZZ.vecSplitFirst(sExpression, objTagType.getTagPartEmpty(), false,true);				
				
			}else {
				vecReturn = StringZZZ.vecMid(sExpression, objTagType.getTagPartOpening(), objTagType.getTagPartClosing(), false,true);
				
			}
		}
		return vecReturn;
	}

	public static boolean isExpression(String sExpression, ITagTypeZZZ objTagType) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			boolean btemp = StringZZZ.contains(sExpression, objTagType.getTagPartOpening(), false);
			if(btemp) {
				btemp = StringZZZ.contains(sExpression, objTagType.getTagPartClosing(), false);
				if(btemp==false) break main;
			}else {
				btemp = StringZZZ.contains(sExpression, objTagType.getTagPartEmpty(), false);
				if(btemp==false) break main;
			}
												
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	//###########################
	public static String replaceTagParts(String sXml, ITagTypeZZZ objTagType, String sToReplaceWith) throws ExceptionZZZ{
		String sReturn = sXml;
		main:{
			sReturn = StringZZZ.replace(sReturn, objTagType.getTagPartOpening(),sToReplaceWith);
			sReturn = StringZZZ.replace(sReturn, objTagType.getTagPartClosing(),sToReplaceWith);
								
		}//end main
		return sReturn;
	}
	
	public static String replaceTagParts(String sXml, ITagTypeZZZ objTagType, String sToReplaceOpeningWith, String sToReplaceClosingWith) throws ExceptionZZZ{
		String sReturn = sXml;
		main:{
			sReturn = StringZZZ.replace(sReturn, objTagType.getTagPartOpening(),sToReplaceOpeningWith);
			sReturn = StringZZZ.replace(sReturn, objTagType.getTagPartClosing(),sToReplaceClosingWith);								
		}//end main
		return sReturn;
	}
	
	//###########################
	public static String replaceTagParts(String sXml, ITagByTypeZZZ objTag, String sToReplaceWith) throws ExceptionZZZ{
		String sReturn = sXml;
		main:{
			sReturn = StringZZZ.replace(sReturn, objTag.getTagPartOpening(),sToReplaceWith);
			sReturn = StringZZZ.replace(sReturn, objTag.getTagPartClosing(),sToReplaceWith);
								
		}//end main
		return sReturn;
	}
	
	public static String replaceTagParts(String sXml, ITagByTypeZZZ objTag, String sToReplaceOpeningWith, String sToReplaceClosingWith) throws ExceptionZZZ{
		String sReturn = sXml;
		main:{
			sReturn = StringZZZ.replace(sReturn, objTag.getTagPartOpening(),sToReplaceOpeningWith);
			sReturn = StringZZZ.replace(sReturn, objTag.getTagPartClosing(),sToReplaceClosingWith);								
		}//end main
		return sReturn;
	}
	

}