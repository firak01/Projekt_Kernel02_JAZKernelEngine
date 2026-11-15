package basic.zBasic.util.datatype.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.formula.ZFormulaIniLineZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;

/**Diese Klasse unterstuetzt die Arbeit mit JDOm-Objekten und dort vorhandenen Document-Objekten.
 * 
 * Merke: Es gibt auch base.xml.XMLUtil;
 * Aus dieser Klasse kann man sich hierin ggfs. bedienen.
 *  
 * Merke: Es gibt auch XMLUtilZZZ, 
 * Dort werden mit "hauseigenen Mitteln" der Stringverarbeitung einfache Tags gesucht, Werte herausgeloest, etc. 
 * 
 * @author Fritz Lindhauer, 23.03.2023, 12:51:12
 * 
 */
public class XmlDocumentUtilZZZ  implements IConstantZZZ{

	/** Hiermit wird das Dokument erstellt und auch schon das erste Root-Element.
	* 
	* 
	* lindhauer; 22.02.2008 07:06:39
	 * @throws ExceptionZZZ 
	 */
	public static Document createDocumentWithRootTag(String sRootName) throws ExceptionZZZ{
		Document objReturn = new Document();
		main:{
			if(StringZZZ.isEmpty(sRootName)){
				ExceptionZZZ ez = new ExceptionZZZ("RootName", iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Element elem = new Element(sRootName);
			objReturn.setRootElement(elem);
		}//end main:
		return objReturn;
	}
	
/**    	Hintergrund den den Formeln, die in einer Ini-Datei-Zeile stehen
 *      wird sehr häufig ein Doppelpunkt verwendet.
 *      Z.B. in KernelEncryptionIniSolverZZZ.sTAG_NAME steht Z:encrypted
 *      Das ist in XML aber kein gültiger Tag Name.
 *      Hier wird der Doppelpunkt ersetzt, um ihn dann später zur Auflösung der Formel wieder zu setzen.
 * 
 *  	//############
		//Merke: Problematik der gueltigen Zeichen im XML-Elementnamen
		//funktioniert: Element objElementZEncryption = new Element("blabla");
		
		//funktioniert nicht: Element objElementZEncryption = new Element(KernelEncryptionIniSolverZZZ.sTAG_NAME);
		//Das Problem ist, das keine Doppelpunkte im Tag-Element sein duerfen.
		//Das entfernt den Doppelpunkt in einem Tagnamen nicht String stemp = XMLUtil.stripNonValidXMLCharacters(KernelEncryptionIniSolverZZZ.sTAG_NAME);
		//############
		 * 
		 * //### Merke: Alternativ zu diesem Workaround einen Namespace "Z" zu ergaenzen bringt nichts
			//siehe: https://stackoverflow.com/questions/7085503/set-namespace-using-jdom			
			//Namespace objNamespace = Namespace.getNamespace("Z", "http://fgl.homepage.t-online.de");
			//objElementZEncryption.addNamespaceDeclaration(objNamespace);
			//Das ergibt dann nämlich folgendes (hier noch den Doppelpunkt durch einen Unterstrich ersetzt, damit der Code überhaupt funktioniert) :
			//Z><Z_encrypted xmlns:Z="http://fgl.homepage.t-online.de" /></Z>
			//was aber für Z:XYZ Ausdruecke nicht weiterhilft...
			//####################
			
	 * @param sTagName
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.03.2023, 12:20:32
	 */
	public static String replaceColonInXmlTag(String sTagName) throws ExceptionZZZ {
		String sReturn = null;
		main:{									
			if(StringZZZ.isEmpty(sTagName)){
				ExceptionZZZ ez = new ExceptionZZZ("TagName", iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Merke: 0x3a ist der HEXCode in ASCII für den Doppelpunkt
			//siehe:
			//https://stackoverflow.com/questions/655891/converting-utf-8-to-iso-8859-1-in-java-how-to-keep-it-as-single-byte
			//Charset utf8charset = Charset.forName("UTF-8");
			//Charset iso88591charset = Charset.forName("ISO-8859-1");
			//byte[] utf8bytes = { (byte)0xc3, (byte)0xa2, 0x61, 0x62, 0x63, 0x64, 0x58, 0x3a };			    
		    //String string = new String ( utf8bytes, utf8charset );//ergibt den String "a mit Dach" a b c d X :
		    //System.out.println(string);
			
			sReturn = StringZZZ.replace(sTagName, ":", "_0x3a_");
									
		}//end main
		return sReturn;
	}
	
	/** Als Gegenpart zur Methdoe "replaceColonInXmlTag"
	 * @param sTagName
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.03.2023, 12:32:49
	 */
	public static String placeColonInZExpression(String sTagName) throws ExceptionZZZ {
		String sReturn = null;
		main:{									
			if(StringZZZ.isEmpty(sTagName)){
				ExceptionZZZ ez = new ExceptionZZZ("TagName", iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			sReturn = StringZZZ.replace(sTagName, "_0x3a_", ":");
									
		}//end main
		return sReturn;
	}
	
	/** Ausgabe des XMLDocument - Objekts als Datei
	 * @return
	 * @author Fritz Lindhauer, 23.03.2023, 12:35:04
	 */
	public static boolean documentToFile(Document objDocument, File objFile) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objDocument == null){
				ExceptionZZZ ez = new ExceptionZZZ("XML Document", iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(objFile == null){
				ExceptionZZZ ez = new ExceptionZZZ("Target File", iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//File objFileTemp = new File("c:\\temp\\KernelZFormulaIniLineTest.xml");
			try {
				FileWriter objWriter = new FileWriter(objFile);//true = anhaengen
				
				XMLOutputter outputter = new XMLOutputter();
				
				Format format = outputter.getFormat();				
				format.setOmitDeclaration(false); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. fuer falsche Deutsche Umlaute sorgt.
				format.setOmitEncoding(false);   //Damit die Encoding Zeile angezeigt wird   
				//format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>
				//format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren.
				format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" f�r deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute �, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden.
								
				outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht �bernommen				
				outputter.output(objDocument,objWriter);
				
				objWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				bReturn = false;
			} 
						
			bReturn = true;
		}//end main
		return bReturn;		
	}
	
	public static String documentToString(Document objDocument) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			try{
				
				//##############################
				//Ausgabe als String
				//Merke: Das gibt nicht das gewuenschte Ergebnis:
				//stest = objDocument.toString();
								
				StringWriter objWriter = new StringWriter();				
				XMLOutputter outputter = new XMLOutputter();
				
				 Format format = outputter.getFormat();
				 
				 //Ziel: Fuer die Zeile in einer ini-Property einen einfachen String mit XML Tags erzeugen, ohne Dokumentenergaenzungen
				 format.setOmitDeclaration(true); //Merke: Bei true  wird die Zeile <?xml version="1.0" encoding="UTF-8"?> weggelassen, was z.B. bei einem HTML-Dokument ggf. fuer falsche Deutsche Umlaute sorgt.				 
				 format.setOmitEncoding(true);   //Damit die Encoding Zeile angezeigt wird =false, Ohne Encoding Zeile =true   
				 //format.setExpandEmptyElements(true); //aus <aaa/> wird dann <aaa></aaa>    
				 //format.setTextMode(TextMode.PRESERVE);//Ohne diesen Formatierungshinweis, wird ggf. auch der META-Tag mit /> als Abschluss versehen. Dann funktioniert scheinbar dieser Tag im Browser nicht mehr. Die deutschen Umlaute gehen verloren. 
				format.setEncoding("ISO-8859-1");      //Ziel: "ISO-8859-1" fuer deutsch       //Ohne diesen Formatierungshinweis wird UTF-8 verwendet. Das bewirkt, dass z.B. die Deutschen Umlaute ue, etc. in die korrespondierende HTML-Umschreibung umgewandelt werden. 
				 
			    outputter.setFormat(format);  //Das muss man machen, sonst sind werden die neuen Format Einstellungen nicht uebernommen				
				outputter.output(objDocument,objWriter);				
				//Das ergibt auch keinen sinnvollen String nur mit den XML-Tags 
				//sReturn = outputter.toString();
				
				//Das ergibt auch keinen sinnvollen String nur mit den XML-Tags
				//String sContent = objDocument.toString();
				//objWriter.write(sReturn);
				
				//PROBLEM: Hier wird immer ein \r\n angehängt!!!
				sReturn = objWriter.toString();
				
				char[] caToStrip = {'\r','\n'};//Also extra beheben, sonst hat man in Ergebnis immer zusätzliche Zeichen.
				sReturn = StringZZZ.stripCharacters(sReturn, caToStrip);
		
				objWriter.close();				
			}catch(IOException ioe){
				ExceptionZZZ ez = new ExceptionZZZ("IOException: " + ioe.getMessage(), iERROR_RUNTIME, ZFormulaIniLineZZZ.class,ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}						
		}//end main
		return sReturn;	
	}
	
}
