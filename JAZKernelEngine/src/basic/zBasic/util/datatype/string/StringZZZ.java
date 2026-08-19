/*
 * Created on 24.10.2004
 * Eine kleine Hilfsklasse f�r die Behandlung von Strings.
 */
package basic.zBasic.util.datatype.string;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
//import com.sun.scenario.effect.impl.sw.sse.SSEBlend_COLOR_BURNPeer;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.json.JsonArrayZZZ;
import basic.zBasic.util.datatype.json.JsonUtilZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
 
/**

@author 0823 ,date 24.10.2004
*/
/**
 * @author lindhauer
 *
 */
/**
 * @author lindhauer
 *
 */
public class StringZZZ implements IConstantZZZ{
	public static final String  sREPLACE_FAR_FROM_MARK = "<NOREPLACE>";             //Dies wird tempor�r in einen String gesetzt, damit der Wert nicht ersetzt wird.
	
	public static final int iSHORTEN_METHOD_NONE = 0; //in der ToShorten(...)-Funktion verwendete Option, hier "nix kürzen"
	public static final int iSHORTEN_METHOD_VOWEL_LOWERCASE = 1; //in der ToShorten(...)-Funktion verwendete Option, die Vokale zu elimenieren (nur Kelinbuchstaben)
	public static final int iSHORTEN_METHOD_VOWEL_UPPERCASE = 2; //in der ToShorten(...)-Funktion verwendete Option, die Vokale zu elimenieren (nur Großbuchstaben)
	public static final int iSHORTEN_METHOD_VOWEL=3; //Sowohl Groß- als auch Kleinbuschstaben. 
	
	private StringZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static methods
	

	
	
	public static String crlf() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			try{
				sReturn = System.getProperty("line.separator");
			}catch(PatternSyntaxException pex){
				ExceptionZZZ ez = new ExceptionZZZ("Pattern seems to be no valid RegEx - PatternSyntaxException: " + pex.getMessage(), iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			
		}//end main
		return sReturn;
	}
		
	/** 
	 * Merke: In RegEx Ausdruechen muessen eckige Klammern mit Backslash escaped werden.
	 * @param sString
	 * @param sRegEx
	 * @param bExactMatch
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 13.10.2024, 18:15:39
	 */
	public static boolean matchesPattern(String sString, String sRegEx, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)){
				ExceptionZZZ ez = new ExceptionZZZ("No string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sRegEx)){
				ExceptionZZZ ez = new ExceptionZZZ("No pattern-RegEx-string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bExactMatch) {
				bReturn = sString.matches(sRegEx);
			}else {
				String s = sString.toLowerCase();
				System.out.println(("s="+s));
				String sPattern = sRegEx.toLowerCase();
				System.out.println("sPattern="+sPattern);
				
				bReturn = s.matches(sPattern);
				
			}
			
		}//end main:
		return bReturn;		
	}
	
	/** Prueft die Zeichen des Strings gegen die Zeichen des uebergebenen Pattern
	 *   Zur Steuerung dient iFlag:
	 *   = -1  => der String enthaelt nur Zeichen, die im Pattern  enthalten sind
	 *   = 0  => der String enthaelt keine Zeichen, die im Pattern enthalten sind.
	 *   = 1  => das uebergebene Pattern wird als RegEx angesehen und auch so behandelt
	 *    
	 *    
	 *    
	* @param sString
	* @param sPattern
	* @param iFlag
	* @return
	* 
	* lindhaueradmin; 13.04.2008 12:01:34
	 * @throws ExceptionZZZ 
	 */
	public static boolean matchesPattern(String sString, String sPattern, int iFlagIn) throws ExceptionZZZ{
		boolean bReturn=false;
		main:{
			if(StringZZZ.isEmpty(sString)){
				ExceptionZZZ ez = new ExceptionZZZ("No string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("No pattern-string provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			int iFlag;
			if(iFlagIn < 0){
				iFlag = -1;
			}else if(iFlagIn > 0){
				iFlag = 1;
			}else{
				iFlag = iFlagIn;
			}
			
			if(iFlag == 1){
				//Behandle den Pattern String als RegEx
				try{
					bReturn = sString.matches(sPattern);
				}catch(PatternSyntaxException pex){
					ExceptionZZZ ez = new ExceptionZZZ("Pattern seems to be no valid RegEx - PatternSyntaxException: " + pex.getMessage(), iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else if(iFlag == 0){
				//Fall: Es darf kein Zeichen des Pattern im String gefunden werden
				String stemp = null;
				for(int icount = 0; icount <= sString.length()-1; icount++){
					stemp = sString.substring(icount, icount+1);
					if(sPattern.indexOf(stemp)!=-1 ) break main;  // -1 d.h. nix gefunden 
				}
				bReturn = true;
			}else if(iFlag == -1){
				//Fall: Jedes Zeichen im String muss aus dem Pattern stammen
				String stemp = null;
				for(int icount = 0; icount <= sString.length()-1; icount++){
					stemp = sString.substring(icount, icount+1);
					if(sPattern.indexOf(stemp)==-1 ) break main;  // -1 d.h. nix gefunden 
				}
				bReturn = true;
			}
		}//end main
		return bReturn;
	}
	
	/**Diese Methode ersetzt in einem String alle Teilstrings durch einen neuen Teilstring.
	 * Buch: "Das Java Codebook",  
	 * Merke: Erst ab JDK 1.5  gibt es eine String.replace(...) Methode, die dasselbe leistet.
	   */
	  public static String replace(String textStr, String oldStr, String newStr) throws ExceptionZZZ {
		  StringBuffer buffer = new StringBuffer();		 
		  int repLength = oldStr.length();
		  int startIndex = 0;
		  int index = textStr.indexOf(oldStr);
		  int textLength = textStr.length();
		  while((index >= 0) && (index < textLength)) {
			  //Text ohne den zu ersetzenden String an buffer anhaengen
			  buffer.append(textStr.substring(startIndex, index));
			  //neuen substring anhaengen
			  buffer.append(newStr);
			  startIndex = index + repLength;
			  index = textStr.indexOf(oldStr, startIndex);
		  }
		  //letzten Teil des Textes anhaengen
		  buffer.append(textStr.substring(startIndex, textStr.length()));
		  return buffer.toString();
	  }
	  
	  
	 /**Diese Methode ersetzt in einem String nur den ersten Teilstring durch einen neuen Teilstring.
	 * Angelehnt an Buch: "Das Java Codebook",  
	 * Merke: Erst ab JDK 1.5  gibt es eine String.replace(...) Methode, die dasselbe leistet.
	 * 
	 * Meine Erweiterung: Es wird nur der erste gefundene String ersetzt 
	 */
	  public static String replaceFirst(String textStr, String oldStr, String newStr) throws ExceptionZZZ {
		  StringBuffer buffer = new StringBuffer();		  
		  int repLength = oldStr.length();
		  int startIndex = 0;
		  int index = textStr.indexOf(oldStr);
		  //int textLength = textStr.length();
		  //while((index >= 0) && (index < textLength)) {
			  //Text ohne den zu ersetzenden String an buffer anhaengen
			  buffer.append(textStr.substring(startIndex, index));
			  //neuen substring anhaengen
			  buffer.append(newStr);
			  startIndex = index + repLength;
			  index = textStr.indexOf(oldStr, startIndex);
		  //}
		  //letzten Teil des Textes anhaengen
		  buffer.append(textStr.substring(startIndex, textStr.length()));
		  return buffer.toString();
	  }
	  
	  /**Diese Methode ersetzt am Anfang eines Strings alle gesuchten String durch einen neuen Teilstring.
	   */
	  public static String replaceLeft(String sString, String sOld, String sNew) throws ExceptionZZZ {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				int iCounter = 0;
				boolean bGoon = false;		
				while(!bGoon){
					if(sReturn.startsWith(sOld)){ //das waere dann .strip(...) :    && sReturn.length()>=2){
						iCounter++;
						sReturn = StringZZZ.rightback(sReturn, sOld.length());													
					}else{
						bGoon = true;
					}
				}
				
				//Nun den String von vorne wieder aufbauen
				for(int i=0;i<iCounter;i++) {
					sReturn = sNew + sReturn;
				}
			}//end main:
			return sReturn;
	  }
	  
	  /**Diese Methode ersetzt am Anfang eines Strings alle gesuchten String durch einen neuen Teilstring.
	   */
	  public static String replaceRight(String sString, String sOld, String sNew) throws ExceptionZZZ {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				int iCounter = 0;
				boolean bGoon = false;		
				while(!bGoon){
					if(sReturn.endsWith(sOld)){ //das waere dann .strip(...) :    && sReturn.length()>=2){
						iCounter++;
						sReturn = StringZZZ.left(sReturn, (sReturn.length() - sOld.length()));													
					}else{
						bGoon = true;
					}
				}
				
				//Nun den String von vorne wieder aufbauen
				for(int i=0;i<iCounter;i++) {
					sReturn = sReturn + sNew;
				}
			}//end main:
			return sReturn;
	  }
	  
	  /**Diese Methode ersetzt am Anfang eines Strings den gesuchten String 1x durch einen neuen Teilstring.
	   */
	  public static String replaceFromLeft1(String sString, String sOld, String sNew) throws ExceptionZZZ {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				String sLeft; String sRight; 
				if(StringZZZ.startsWith(sString, sOld)) {
					sRight = sString.substring(sOld.length());										
					sReturn = sNew + sRight;
					break main;
				}else {
					sLeft = StringUtils.substringBefore(sString, sOld);
					if(sString.equals(sLeft)) break main; //dann gibt es den String ueberhaupt nicht, weil er ja schon am Anfang nicht da ist.
					
					sRight = sString.substring(sLeft.length()+sOld.length());
					
					sReturn = sLeft + sNew + sRight;
					break main;
				}
			}//end main:
			return sReturn;
	  }
	  
	  /**Diese Methode ersetzt am Ende eines Strings den gesuchten String 1x durch einen neuen Teilstring.
	   */
	  public static String replaceFromRight1(String sString, String sOld, String sNew) throws ExceptionZZZ {
		  String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sOld)) break main;
				
				String sLeft; String sRight;
				if(StringZZZ.endsWith(sString, sOld)) {
					sLeft = StringUtils.substringBeforeLast(sString, sOld);
					
					sReturn = sLeft + sNew;
					break main;
				}else {
					sRight = StringUtils.substringAfterLast(sString, sOld);
					if(StringZZZ.isEmpty(sRight)) break main; //dann gibt es den String ueberhaupt nicht, weil er ja schon am Ende nicht da ist.
					
					sLeft = StringUtils.substringBeforeLast(sString, sOld);
					
					sReturn = sLeft + sNew + sRight;
					break main;
				}
			}//end main:
			return sReturn;
	  }
	  
	  
	  /** Ersetze in einem String alle Teilstrings durch den neuen Teilstring. ABER: Keine Ersetzung, wenn das Ziel schon in dem Teilstring vorkommt.
	   * Bsp: 
	   * Aus "aaabcbaaaa" soll c durch bcb ersetzt werden.
	   * Dann liefert das normale replace(..) folgendes Ergebnis: aaabbcbb
	   * DIESE FarFrom-Function ersetzt den Teilstring hier aber nicht.
	   * 
	* @param textStr
	* @param oldStr
	* @param newStr
	* @return
	* 
	* lindhaueradmin; 22.03.2009 14:43:21
	 */
	public static String replaceFarFrom(String textStr, String oldStr, String newStr) throws ExceptionZZZ {
		  String sReturn = null;
		  main:{
			  if(StringZZZ.isEmpty(textStr)){
				  sReturn = textStr;
				  break main; 
			  }
			  if(StringZZZ.isEmpty(oldStr)){
				 sReturn =  textStr;
				 break main;
			  }
			  if(newStr.equals("")){
				  sReturn = StringZZZ.replace(textStr, oldStr, newStr);
				  break main;
			  }
			  
			  
              //+++ umfangreiches Ersetzten +++++++++
			  
			  if(StringZZZ.contains(newStr, oldStr)){
				  /*NEIN, DAS GEHT NICHT MIT NEM TOKENIZER
				 //String nach dem newStr zerlegen....
				StringTokenizer token = new StringTokenizer(textStr, newStr, false);
				sReturn = "";
				while(token.hasMoreTokens()){
					
					 //... Ersetzung in den Teilstrings ...
						String sReplaceOrig = (String) token.nextToken();
						String stemp = StringZZZ.replace(sReplaceOrig, oldStr, newStr);
											   
						 //... Teilstrings wieder zusammenbauen
						if(sReturn.equals("")){
							sReturn = stemp;
						}else{
							sReturn = sReturn + newStr + stemp;
						}
					}//End while
				*/
				  
	//				String nach dem newStr zerlegen.... (Merke: DAS GEHT NICHT MIT DEM TOKENIZER !!!!)
				  String[] saErg = StringZZZ.explode(textStr, newStr);
				  int icount = 0;
				  for(icount=0; icount <= saErg.length-1; icount++){
					  String sReplaceOrig = saErg[icount];
					  String stemp = StringZZZ.replace(sReplaceOrig, oldStr, newStr);
				     
					  //... Teilstrings wieder zusammenbauen
						if(StringZZZ.isEmpty(sReturn)){
							sReturn = stemp;
						}else{
							sReturn = sReturn + newStr + stemp;
						}
			  }
				  
			  }else{
				  sReturn = StringZZZ.replace(textStr, oldStr, newStr);
			  }
		  }//End main
		  return sReturn;
	  }
	
	/** Drehe den String um.
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 09:07:08
	 */
	public static String reverse(String s) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(s)) break main;
					
			sReturn = new StringBuilder(s).reverse().toString();
		}
		return sReturn;
	}
	  
	 

	  public static String char2String(char c2String) throws ExceptionZZZ {		 
		return String.valueOf(c2String);
	  }
	  
	  /** char, returns the character at position 0. If s2c.lenght() >= 2 an exception will be thrown
	* Lindhauer; 22.04.2006 07:57:58
	 * @param s2c
	 * @return
	 * @throws ExceptionZZZ 
	 */
	public static char string2Char(String s2c) throws ExceptionZZZ {
		if(s2c.length() >= 2) {			
			ExceptionZZZ ez = new ExceptionZZZ("The string should only have one character", 101,  ReflectCodeZZZ.getMethodCurrentName(), ""); 
			//doesn�t work. Only works when > JDK 1.4
			//Exception e = new Exception();
			//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			throw ez;
		}
		  return s2c.charAt(0);
	  }
	
	/** char, returns the character at the index-position. Starting from 0.
	* Lindhauer; 22.04.2006 07:59:18
	 * @param s2c
	 * @param iIndex
	 * @return
	 */
	public static char string2Char(String s2c, int iIndex) throws ExceptionZZZ {	
		if(iIndex <= -2 ){
			ExceptionZZZ ez = new ExceptionZZZ("The index can not be smaller than 0", iERROR_PARAMETER_VALUE,  StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
			//doesn�t work. Only works when > JDK 1.4
			//Exception e = new Exception();
			//ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			throw ez;
		}
	return s2c.charAt(iIndex);
}
	
	
	/** Nimm den Fallback, wenn value nicht gesetzt ist.
	 * @param value
	 * @param fallback
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String coalesce(String value, String fallback) throws ExceptionZZZ{
	    if(value == null || value.trim().isEmpty()) {
	        return fallback;
	    }
	    return value;
	}

	public static boolean contains(String sString, String sMatch) throws ExceptionZZZ {
		return StringZZZ.contains(sString, sMatch, true);	
	}
	
	/**
	 * //Merke: sString.contains(CharSequence) gibt es erst seit Java 1.5
			//CharSequence sequence = sMatch.subSequence(1, sString.length());
			//if(sString.contains(sequence)  .......
			
	 * @param sString
	 * @param sMatch
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 17.05.2024, 17:09:30
	 */
	public static boolean contains(String sString, String sMatch, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			String sSub;
			int iMatchLength=sMatch.length();
			int iProof = sString.length()-iMatchLength;			
			if(bExactMatch){
				for(int icount=0; icount <= iProof; icount++){
					sSub = sString.substring(icount, iMatchLength+icount);
					if(sSub.equals(sMatch)){
						bReturn = true;
						break main;
					}
				}
				
			}else {
			
				String sStringLcased = sString.toLowerCase();
				String sMatchLcased = sMatch.toLowerCase();
				
				for(int icount=0; icount <= iProof; icount++){
					sSub = sStringLcased.substring(icount, iMatchLength+icount);
					if(sSub.equals(sMatchLcased)){
						bReturn = true;
						break main;
					}
				}
			}																
		} //end main:		
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTagXml(String sString, String sMatchTagName)throws ExceptionZZZ {
		return StringZZZ.containsAsTagXml(sString, sMatchTagName, true);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTagXml(String sString, String sMatchTagName, boolean bExactMatch)throws ExceptionZZZ {
		return XmlUtilZZZ.containsTagName(sString, sMatchTagName, bExactMatch);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName, muss aber kein xml-tag sein, sonder jede Kennzeichnung passt
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTag(String sString, String sMatchTagStarting, String sMatchTagClosing) throws ExceptionZZZ {
		return StringZZZ.containsAsTag(sString, sMatchTagStarting, sMatchTagClosing, true);
	}
	
	/** 
	 * @param sString
	 * @param sMatchTagStarting  , das ist ein ganzer Tag und nicht nur der TagName
	 * @param sMatchTagClosing   , das ist ein ganzer Tag und nicht nur der TagName
	 * @param bExactMatch
	 * @return
	 * @author Fritz Lindhauer, 12.07.2024, 10:54:09
	 */
	public static boolean containsAsTag(String sString, String sMatchTagStarting, String sMatchTagClosing, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatchTagStarting)) break main;
			
			boolean bAsTagEmpty;
			if(StringZZZ.isEmpty(sMatchTagClosing)) {				
				bAsTagEmpty = XmlUtilZZZ.isTagEmpty(sMatchTagStarting);
			}else {
				bAsTagEmpty = false;
			}
			
			if(bAsTagEmpty) {
				//Einfach, dann wird es eine Contains
				bReturn = StringZZZ.contains(sString, sMatchTagStarting, bExactMatch);
				break main;
			}
			
			//Merke auf Gleichheit der Tags nicht pruefen.
			//Wenn gleiche Tags vorhanden sind als Eingangswert, dann wird durch die unterschiedlichen Indexwerte zumindest sichergestellt, 
			//dass es zwei verschiedene Stellen sind.
						
			//Nun nicht einfach auf contains prüfen, da die Reihenfolge auch zu beachten ist.
			//StartingTag ist immer VOR dem ClosingTag, auch egal ob Verschachtelt.
			//Wegen verschachtelte Tags muss man dann aber von hinten aus prüfen.
			int iStarting; int iClosing;
			if(bExactMatch) {
				iStarting = sString.indexOf(sMatchTagStarting);
				if(iStarting<=-1)break main;
				
				iClosing = sString.lastIndexOf(sMatchTagClosing);
				if(iClosing<=-1)break main;								
			}else {
				iStarting = sString.toLowerCase().indexOf(sMatchTagStarting.toLowerCase());
				if(iStarting<=-1)break main;
				
				iClosing = sString.toLowerCase().lastIndexOf(sMatchTagClosing.toLowerCase());
				if(iClosing<=-1)break main;								
			}
			
			//Nun noch pruefen ob die Reihenfolge der Tags passt
			if(iClosing <= iStarting) break main;
					
			bReturn = true;
		} //end main:		
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String auch nur irgendein Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsBlankAny(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]==' '){
					bReturn=true;
					break main;
				}
			}			
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String nur Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsBlankOnly(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {				
				if(ca[icount]!=' ') {
					break main;
				}				
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String auch nur irgendeinen Kleinbuchstaben enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsLowercaseAny(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isLowercase(ca[icount])){
					bReturn=true;
					break main;
				}
			}			
		}//end main:
		return bReturn;
	}
	
	/** 
	 * @param sString
	 * @return true, wenn der String nur Kleinbuchstaben und Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:45:41
	 */
	public static boolean containsLowercaseAndBlankOnly(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isLowercase(ca[icount])){					
						break main;
					}
				}
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String auch nur irgendeine Zahle 0-9 enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:16
	 */
	public static boolean containsNumericAny(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
								
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isNumeric(ca[icount])){						
					bReturn = true;
					break main;										
				}
			}
			
			
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String nur Zahlen 0-9 und Leerzeichen enthaelt
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:16
	 */
	public static boolean containsNumericAndBlankOnly(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(sString.equals("")) break main;
								
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isNumeric(ca[icount])){						
						break main;
					}					
				}
			}
			bReturn = true;
			
		}//end main:
		return bReturn;
	}
	
	
	
	/**
	 * @param sString
	 * @return true, wenn der String auch nur irgendeinen Grossbuchstaben enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:44
	 */
	public static boolean containsUppercaseAny(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(CharZZZ.isUppercase(ca[icount])){					
					bReturn = true;
					break main;					
				}
			}					
		}//end main:
		return bReturn;
	}
	
	/**
	 * @param sString
	 * @return true, wenn der String nur Grossbuchstaben und Leerzeichen enthaelt.
	 * @author Fritz Lindhauer, 08.11.2022, 15:46:44
	 */
	public static boolean containsUppercaseAndBlankOnly(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			
			char[]ca = sString.toCharArray();
			for(int icount = 0 ; icount < ca.length; icount++) {
				if(ca[icount]!=' ') {
					if(!CharZZZ.isUppercase(ca[icount])){										
						break main;
					}
				}
			}
			bReturn = true;
			
		}//end main:
		return bReturn;
	}
	
	public static boolean containsOnly(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			char[]ca = sString.toCharArray();
			char[]cmatch = sMatch.toCharArray();
						
			for(int icount = 0 ; icount < ca.length; icount++) {
				for(int icountInner = 0; icount < cmatch.length; icountInner++) {
					if(ca[icount]!=cmatch[icountInner]){												
						break main;
					}
				}					
			}			
			bReturn = true;
		}
		return bReturn;
	}
	
	public static boolean containsOnly(String sString, String sMatch, String sAllowed) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null)break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
			
			char[]callowed = null;
			if(!StringZZZ.isEmpty(sAllowed)) {
				callowed = sAllowed.toCharArray();
			}
			
			char[]ca = sString.toCharArray();
			char[]cmatch = sMatch.toCharArray();
			
						
			for(int icount = 0 ; icount < ca.length; icount++) {			
				if(!CharArrayZZZ.contains(callowed,ca[icount])) {
					for(int icountInner = 0; icount < cmatch.length; icountInner++) {
						if(ca[icount]!=cmatch[icountInner]){												
							break main;
						}
					}					
				}	
			}			
			bReturn = true;
		}
		return bReturn;
	}
	
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equals(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
			
			bReturn = sString.equals(sMatch);
						
		}//end main:
		return bReturn;
	}
	
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equals(String sString, String sMatch, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(bExactMatch) {
				bReturn = StringZZZ.equals(sString, sMatch);
				break main;
			}
			
			
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
							
			bReturn = sString.toLowerCase().equals(sMatch.toLowerCase());
						
		}//end main:
		return bReturn;
	}
	
	
	
	/** Hier muss man nicht auf NULL achten, wenn man den String mit einem Character vergleicht.
	 * @param sString
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 27.10.2022, 12:19:10
	 */
	public static boolean equalsIgnoreCase(String sString, char cMatch) throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			if(sString==null && cMatch == CharZZZ.getEmpty()){
				bReturn = true;
				break main;
			}
			if(sString!=null && cMatch == CharZZZ.getEmpty()){
				bReturn = false;
				break main;				
			}
			if(sString==null && cMatch != CharZZZ.getEmpty()){
				bReturn = false;
				break main;
			}
									
			char[]ca=sString.toCharArray();
			if(ca.length==0) {
				bReturn = false;
				break main;
			}
			
			if(ca.length>=2) {
				bReturn = false;
				break main;
			}
		
			char c = ca[0];
			if(c!=CharZZZ.toUppercase(cMatch) && c!=CharZZZ.toLowercase(cMatch)) {
				bReturn = false;
				break main;
			}		
			
			
		}//end main:
		return bReturn;
	}
	
	/** Hier muss man nicht auf NULL achten, wenn man die Strings miteinander vergleicht.
	 * @param sString
	 * @param sMatch
	 * @return
	 * @author lindhaueradmin, 12.02.2019, 19:43:08
	 */
	public static boolean equalsIgnoreCase(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sString==null && sMatch == null){
				bReturn = true;
				break main;
			}
			if(sString!=null && sMatch == null){
				bReturn = false;
				break main;				
			}
			if(sString==null && sMatch !=null){
				bReturn = false;
				break main;
			}
			
			bReturn = sString.equalsIgnoreCase(sMatch);
						
		}//end main:
		return bReturn;
	}
	
	/* Unter Java String gibt es nur startsWith.*/
	public static boolean endsWith(String sString, String sMatch) throws ExceptionZZZ {
		return StringZZZ.endsWith(sString, sMatch, true);
	}
	
	/* Unter Java String gibt es nur startsWith.*/
	public static boolean endsWith(String sString, String sMatch, boolean bExactMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			if(bExactMatch) {
				int iMatchLength=sMatch.length();			
				if(sString.length() < iMatchLength) break main;
				
				String sSub = sString.substring(sString.length() - iMatchLength, sString.length());
				if(sSub.equals(sMatch)){
					bReturn = true;
					break main;
				}
			}else {
				bReturn = StringZZZ.endsWithIgnoreCase(sString, sMatch);
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	/* Unter Java String gibt es nur startsWith. Hier wird zusätzlich noch geleistet, dass Groß-/Kleinschreibung egal ist */
	public static boolean endsWithIgnoreCase(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			String sSub = sString.substring(sString.length() - iMatchLength, sString.length());
			if(sSub.equalsIgnoreCase(sMatch)){
				bReturn = true;
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	//######################################################################
	public static String[] explode(String sString, char cDelimiter) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(CharZZZ.isEmptyNull(cDelimiter)) break main;
			
			String sDelimiter = CharZZZ.toString(cDelimiter);
			saReturn = StringZZZ.explode(sString, sDelimiter);
		}//end main
		return saReturn;
	}
	
	
	
	public static String[] explode(String sString, String sDelimiter) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sDelimiter==null) break main;
			
			saReturn = StringUtils.splitByWholeSeparator(sString, sDelimiter);
		}//end main
		return saReturn;
	}
	
	public static String[] explode(String sString, String[] saDelimiter) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isNull(saDelimiter)) break main;
			
			//Eine ArrayList aus den Delimitern machen
			ArrayList<String> listaDelim = StringArrayZZZ.toArrayList(saDelimiter);
			
			//Zerlegen mit dem ersten Delimiter
			String sDelimiter = (String) listaDelim.get(0);
			String[] saTemp = StringUtils.splitByWholeSeparator(sString, sDelimiter);  //Sollte hier nicht der Separator im Array enthalten sein ???
			
			//Alle gefundenen Teilstrings erneut zerlegen, aber (!) die Liste der Delimiter um den gerade verarbeiteten reduzieren
			listaDelim.remove(0);
			if(listaDelim.isEmpty()){
				//Damit den letzten Delimiter gefunden. Array zur�ckgeben
				saReturn = saTemp;
				break main;
			}
			
			ArrayList<String> listaStringFound = new ArrayList<String>();//StringArrayZZZ.toArrayList(saTemp);
			for(int icount=0; icount <= saTemp.length-1; icount++){
				String sStringTemp = saTemp[icount];				
				//-1 korrekt ? 
				String[] saDelimTemp = new String[listaDelim.size()-1];
				saDelimTemp = (String[]) listaDelim.toArray(saDelimTemp);
								
				String[] saTemp2 = StringZZZ.explode(sStringTemp, saDelimTemp);
				ArrayList<String> listaTemp = StringArrayZZZ.toArrayList(saTemp2);
				
				//Nun das Ergebnis zu dem bisherigen hinzufuegen
				listaStringFound = (ArrayList<String>) ArrayListUtilZZZ.join(listaStringFound, listaTemp, false);
			}		
			saReturn = new String[listaStringFound.size()-1];
			saReturn = (String[]) listaStringFound.toArray(saReturn);
		}//end main
		return saReturn;
	}
	
	
	//####################################################
	/** Will man das Trennzeichen innerhalb eines Strings ignorieren, funktionieren die einfachen explode - Methoden nicht:
	 * 
	 * ChatGPT vom 20260323
Das, was du brauchst, ist kein einfaches split, sondern ein zustandsbasierter Parser, der erkennt, ob er sich gerade innerhalb von Hochkommas befindet oder nicht.
StringUtils.splitByWholeSeparator(...) kann das nicht, weil es keine Escape-/Quote-Logik kennt.
	 * 
	 * @param input
	 * @param delimiter
	 * @return
	 */
	public static String[] explodeRespectingQuotes(String input, String delimiter) throws ExceptionZZZ {
        if (input == null || input.isEmpty()) {
            return new String[0];
        }

        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();

        boolean inQuotes = false;
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);

            // Hochkomma toggelt den Zustand
            if (c == '\'') {
                inQuotes = !inQuotes;
                i++;
                continue;
            }

            // Prüfen auf Delimiter (nur wenn NICHT in Quotes)
            if (!inQuotes && input.startsWith(delimiter, i)) {
                result.add(current.toString());
                current.setLength(0);
                i += delimiter.length();
                continue;
            }

            current.append(c);
            i++;
        }

        // letztes Element hinzufügen
        result.add(current.toString());

        return result.toArray(new String[result.size()]);
    }
	
	//###################################################
	//+++++++++++++++++++++++++++++++++++++++++	
	/* Unter Java String gibt es nur startsWith. Der Vollstaendigkeit halber hier quasi uebernommen. */	
	public static boolean startsWith(String sString, String[] saMatch, boolean bExactMatch) throws ExceptionZZZ {		
		if(bExactMatch) {
			return StringZZZ.startsWith(sString, saMatch);
		}else {				
			return StringZZZ.startsWithIgnoreCase(sString, saMatch);
		}			
	}
	
	/* Unter Java String gibt es nur startsWith. Der Vollstaendigkeit halber hier quasi uebernommen. */
	public static boolean startsWith(String sString, String[] saMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isEmpty(saMatch)) break main;
									
			for(String sMatch : saMatch) {
				bReturn = StringZZZ.startsWith(sString, sMatch);
				if(bReturn) break main;
			}			
			
		} //end main:		
		return bReturn;
	}
		
	public static boolean startsWithIgnoreCase(String sString, String[] saMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isEmpty(saMatch)) break main;
									
			for(String sMatch : saMatch) {
				bReturn = StringZZZ.startsWithIgnoreCase(sString, sMatch);
				if(bReturn) break main;
			}			
			
		} //end main:		
		return bReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++	
	/* Unter Java String gibt es nur startsWith. Der Vollstaendigkeit halber hier quasi uebernommen. */
	public static boolean startsWith(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			bReturn = sString.startsWith(sMatch);
			
		} //end main:		
		return bReturn;
	}
	
	public static boolean startsWith(String sString, String sMatch, boolean bExactMatch) throws ExceptionZZZ {		
		if(bExactMatch) {
			return StringZZZ.startsWith(sString, sMatch);
		}else {				
			return StringZZZ.startsWithIgnoreCase(sString, sMatch);
		}			
	}
	

	/* Unter Java String gibt es nur startsWith. Hier wird zusätzlich noch geleistet, dass Groß-/Kleinschreibung egal ist */
	public static boolean startsWithIgnoreCase(String sString, String sMatch) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sMatch)) break main;
									
			
			int iMatchLength=sMatch.length();			
			if(sString.length() < iMatchLength) break main;
			
			
			String sSub = sString.substring(0, iMatchLength);
			if(sSub.equalsIgnoreCase(sMatch)){
				bReturn = true;
				break main;
			}
			
		} //end main:		
		return bReturn;
	}
	
	//############################################################
	
	/** String, analog to LotusScript, return the iLength number of strings from the left
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, int iPos) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
//		String sReturn = null;
//		main:{			
//			if (StringZZZ.isEmpty(sString)) break main;
			if(iPos<=-1) break main;
			
			if (iPos > sString.length()){
				sReturn = sString;
			}else{
				sReturn = sString.substring(0, iPos);
			}			
		}//END main:
		return sReturn;
	}
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.left(sString, sToFind, true);
	}
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String left(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
			
//		String sReturn=sString;
//		main:{
//			if (StringZZZ.isEmpty(sString)) break main;
//			sReturn = "";
			
			int iIndex;
			if(bExactMatch){
				iIndex = sString.indexOf(sToFind);				
			}else {
				iIndex = sString.toLowerCase().indexOf(sToFind.toLowerCase()); //Hier wird ignoreCase realisiert.							
			}
			
			if(iIndex<= -1) break main;
			
			sReturn = sString.substring(0, iIndex);
			
		}//END main:
		return sReturn;
	}
	
	
	
	/** String, analog to LotusScript, returns the substring left from the first occurance of sToFind, STARTING From the position. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	* @param sString
	* @param iPosition
	* @param sToFind
	* @return
	* 
	* lindhauer; 06.07.2007 07:24:05
	 */
	public static String left(String sString, int iPosition, String sToFind) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
			
//		String sReturn=sString;
//		main:{
//			if (StringZZZ.isEmpty(sString)) break main;
//
//			sReturn = "";			
			int iStart = 0;
			if(iPosition < 0) {
				iStart = 0;
			}else{ 
				iStart =  iPosition ;  //Weil wir mit rightback den Wert holen
			}
			String sStart = StringZZZ.rightback(sString, iStart); //Hole den String rechts von der angegebenen Position
						
			int iIndex = sStart.indexOf(sToFind);
			if(iIndex<= -1) break main;
						
			sReturn = sStart.substring(0, iIndex);
			
		}//END main:
		return sReturn;
	}
	
	
	//################################################
	
	public static String leftKeep(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.leftKeep(sString, sToFind, true, -1);
	}
	
	public static String leftKeep(String sString, String sToFind, int iIndexStartingPositionFromLeft) throws ExceptionZZZ {
		return StringZZZ.leftKeep(sString, sToFind, true, iIndexStartingPositionFromLeft);
	}
	
	public static String leftKeep(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		return StringZZZ.leftKeep(sString, sToFind, bExactMatch, -1);
	}
	
	public static String leftKeep(String sString, String sToFind, boolean bExactMatch, int iIndexStartingPositionFromLeft) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
						
			int iIndexFromLeft;
			if(iIndexStartingPositionFromLeft==-1) {
				iIndexFromLeft = 0;
			}else {
				iIndexFromLeft = iIndexStartingPositionFromLeft;
			}
			
			int iIndex;
			if(bExactMatch){
				iIndex = sString.indexOf(sToFind, iIndexFromLeft);				
			}else {
				iIndex = sString.toLowerCase().indexOf(sToFind.toLowerCase(), iIndexFromLeft); //Hier wird ignoreCase realisiert.							
			}
			
			if(iIndex<= -1) break main;
			
						
			iIndex += sToFind.length();			
			sReturn = sString.substring(0, iIndex);
			
		}//END main:
		return sReturn;
	}
	
	//#################################################
	
	/**Gezählt wird von rechts 
	 * 
	 * This example returns Lennard. 
@LeftBack("Lennard Wallace"; " ")

	This example returns Lennard Wall. 
@LeftBack("Lennard Wallace";3)
	* @param sString
	* @param sToFind
	* 
	* lindhaueradmin; 04.04.2009 13:15:27
	 */
	public static String leftback(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.leftback(sString, sToFind, true);
	}
	
	/**Gezählt wird von rechts 
	 * 
	 * This example returns Lennard. 
@LeftBack("Lennard Wallace"; " ")

	This example returns Lennard Wall. 
@LeftBack("Lennard Wallace";3)
	* @param sString
	* @param sToFind
	* 
	* lindhaueradmin; 04.04.2009 13:15:27
	 */
	public static String leftback(String sString, String sToFind,boolean bExactMatch) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
						
			int iPosition = -1;
			if(bExactMatch) {			
				iPosition = sString.lastIndexOf(sToFind);				
			}else {
				iPosition = sString.toLowerCase().lastIndexOf(sToFind.toLowerCase());
			}
			if (iPosition == -1) break main;
						
			sReturn = sString.substring(0, iPosition);				
		}
		return sReturn;
	}
	
	/**Gezählt wird von rechts
	 * @param sString
	 * @param iPosFromTheRight
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:20:48
	 */
	public static String leftback(String sString, int iPosFromTheRight) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			if(iPosFromTheRight<=-1) break main;
			int iPosition = sString.length()-iPosFromTheRight;
			if (iPosition <= -1) break main;
								
			sReturn = sString.substring(0, iPosition);				
		}
		return sReturn;
	}
	
	//############################################
	/**Gezählt wird von rechts
	 * @param sString
	 * @param sToFind
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:21:47
	 */
	public static String leftbackKeep(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.leftbackKeep(sString, sToFind, true, -1);
	}
	
	/** Gezählt wird von Rechts
	 * @param sString
	 * @param iPosFromTheRight
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:14:09
	 */
	public static String leftbackKeep(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		return StringZZZ.leftbackKeep(sString, sToFind, bExactMatch, -1);
	}
	
	/** Gezählt wird von Rechts
	 * @param sString
	 * @param iPosFromTheRight
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:14:09
	 */
	public static String leftbackKeep(String sString, String sToFind, int iIndexStartingPositionFromRight) throws ExceptionZZZ {
		return StringZZZ.leftbackKeep(sString, sToFind, true, iIndexStartingPositionFromRight);
	}
	
	/** Gezählt wird von Rechts
	 * @param sString
	 * @param iPosFromTheRight
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:14:09
	 */
	public static String leftbackKeep(String sString, String sToFind, boolean bExactMatch, int iIndexStartingPositionFromRight) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			String sLeftBack=StringZZZ.leftback(sString, sToFind, bExactMatch);
			if(sLeftBack==null)break main;
			
			sReturn = sLeftBack + sToFind;
		}
		return sReturn;

	}
	
	/** Gezählt wird von Rechts
	 * @param sString
	 * @param iPosFromTheRight
	 * @return
	 * @author Fritz Lindhauer, 29.11.2025, 08:14:09
	 */
	public static String leftbackKeep(String sString, int iPosFromTheRight) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			if(iPosFromTheRight<=-1) break main;
			if(iPosFromTheRight>=sString.length()+1) break main; //+1 wg. Keep
			
			int iPosition = sString.length()-iPosFromTheRight + 1; //+1 wg. Keep			
			if(iPosition <= -1) break main;
			if(iPosition > sString.length()-1) iPosition = sString.length(); //sonst Index out of Range Fehler
			
			sReturn = sString.substring(0, iPosition);				
		}
		return sReturn;
	}
	
	//####################################################
	/**
	 * Liefert den linken Teil eines Strings bis zum ersten Auftreten des Delimiters,
	 * wobei Delimiter innerhalb von Hochkommas ignoriert werden.
	 * 
	 * Beispiel:
	 * abc'def'gh|ijk     -> abc'def'gh
	 * abc'd|ef'gh|ijk    -> abc'd|ef'gh
	 * 
	 * @param input
	 * @param delimiter
	 * @return
	 */
	public static String leftRespectingQuotes(String input, String delimiter) throws ExceptionZZZ {
	    if (input == null || input.isEmpty()) {
	        return "";
	    }

	    StringBuilder current = new StringBuilder();
	    boolean inQuotes = false;
	    int i = 0;

	    while (i < input.length()) {
	        char c = input.charAt(i);

	        // Hochkomma toggelt Zustand
	        if (c == '\'') {
	            inQuotes = !inQuotes;
	            current.append(c); // Wichtig: Quote behalten!
	            i++;
	            continue;
	        }

	        // Delimiter prüfen (nur außerhalb von Quotes)
	        if (!inQuotes && input.startsWith(delimiter, i)) {
	            return current.toString(); // sofort abbrechen
	        }

	        current.append(c);
	        i++;
	    }

	    // Kein Delimiter gefunden → kompletter String
	    return current.toString();
	}
	
	//########################################################
	
	/** String, analog to LotusScript, returns the iLength number of strings from the right iPos Position. Null if sString is null or empty.
	* @param sString
	* @param iPos, Indexpositon, beginnt mit 0
	* @param iNumberOfCharacter
	* @return
	* 
	* lindhaueradmin; 18.03.2008 06:29:53
	 */
	public static String mid(String sString, int iPos, int iLength) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
						
//		String sReturn = sString;
//		main:{
//				if (StringZZZ.isEmpty(sString)) break main;
//				
//				sReturn = "";
				if(iPos<0) break main;								
				if(iLength<0) break main;				
				if(iPos > sString.length())	break main;				
		
			int iIndexRight = iPos + iLength;
			
			//Ohne diese Sicherheitsabfrage wirft Java einen IndexOutOfBounds Fehler
			if(iIndexRight>sString.length()){
				iIndexRight = sString.length();
			}
			sReturn = sString.substring(iPos, iIndexRight);		
		}
		return sReturn;
	}
	
	
	public static String mid(String sString, int iPos, String sToFind) throws ExceptionZZZ {
		return StringZZZ.mid(sString, iPos, sToFind, true);
	}
	
	public static String mid(String sString, int iIndexStart, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
			
//		String sReturn = sString;
//		main:{
//			if (StringZZZ.isEmpty(sString)) break main;
//				
//			sReturn = "";
			if(iIndexStart<0) break main;								
			if(StringZZZ.isEmpty(sToFind)) break main;				
			if(iIndexStart > sString.length())	break main;				
			
		
			sReturn = sString.substring(iIndexStart, sString.length()-1);
			sReturn = StringZZZ.left(sReturn, sToFind, bExactMatch);			
		}//end main:
		return sReturn;
	}
	
	public static String mid(String sString, String sToFindLeft, String sToFindRight) throws ExceptionZZZ {
		return StringZZZ.mid_(sString, sToFindLeft, sToFindRight, true);
	}
	
	private static String mid_(String sString, String sToFindLeft, String sToFindRight,  boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sToFindLeft) && StringZZZ.isEmpty(sToFindRight))break main;
								
			if (StringZZZ.isEmpty(sToFindLeft)){
				sReturn = StringZZZ.leftback(sString, sToFindRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sToFindRight)){
				sReturn = StringZZZ.rightback(sString, sToFindLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			sReturn = StringZZZ.rightback(sString, sToFindLeft, bExactMatch);
			sReturn = StringZZZ.leftback(sReturn, sToFindRight, bExactMatch);
		}
		return sReturn;
	}
	
	
	//########################
	public static String midKeep(String sString, int iPos, String sToFind) throws ExceptionZZZ {
		return StringZZZ.midKeep(sString, iPos, sToFind, true);
	}
	
	public static String midKeep(String sString, int iIndexStart, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}

			if(iIndexStart<0) break main;								
			if(StringZZZ.isEmpty(sToFind)) break main;				
			if(iIndexStart > sString.length())	break main;				
		
			sReturn = sString.substring(iIndexStart, sString.length());
			sReturn = StringZZZ.leftKeep(sReturn, sToFind, bExactMatch);			
		}//end main:
		return sReturn;
	}
	
	
	public static String midKeep(String sString, String sToFindLeft, String sToFindRight) throws ExceptionZZZ {
		return StringZZZ.midKeep_(sString, sToFindLeft, sToFindRight, true);
	}
	
	private static String midKeep_(String sString, String sToFindLeft, String sToFindRight,  boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sToFindLeft) && StringZZZ.isEmpty(sToFindRight))break main;
								
			if (StringZZZ.isEmpty(sToFindLeft)){
				sReturn = StringZZZ.leftbackKeep(sString, sToFindRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sToFindRight)){
				sReturn = StringZZZ.rightbackKeep(sString, sToFindLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			sReturn = StringZZZ.rightbackKeep(sString, sToFindLeft, bExactMatch);
			sReturn = StringZZZ.leftbackKeep(sReturn, sToFindRight, bExactMatch);
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString
	* @param iLeftPosition
	* @param iRightPosition
	* @return
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, int iLeftPosition, int iRightPosition) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sString))break main;
			if (iLeftPosition <= -1) break main;
			if (iRightPosition >= sString.length()+1) break main;
						
			sReturn = StringZZZ.rightback(sString, iLeftPosition);
			if(StringZZZ.isEmpty(sReturn)) break main;
			if(iRightPosition-iLeftPosition >= sReturn.length()){
				sReturn = "";
				break main;
			}
			
			sReturn = StringZZZ.left(sReturn, (iRightPosition - iLeftPosition));
			if(StringZZZ.isEmpty(sReturn)) break main;			
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right, including the edge positions
	* @param sString
	* @param iLeftPosition
	* @param iRightPosition
	* @return
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightKeep(String sString, int iLeftPosition, int iRightPosition) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sString))break main;
			if ((iLeftPosition + iRightPosition) > sString.length()) break main;
						
			sReturn = StringZZZ.rightback(sString, iLeftPosition); //das beinhaltet das Zeichen
			if(StringZZZ.isEmpty(sReturn)) break main;
			if(iRightPosition-iLeftPosition >= sReturn.length()){
				sReturn = "";
				break main;
			}
			
			sReturn = StringZZZ.left(sReturn, (iRightPosition - iLeftPosition +1));
			if(StringZZZ.isEmpty(sReturn)) break main;			
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString
	* @param iLeftPosition
	* @param iRightPosition
	* @return
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midBounds(String sString, int iLeftPositionFromLeft, int iRightPositionFromRight) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			int iRightPosition = sString.length() - iRightPositionFromRight;
			if(iRightPosition <= iLeftPositionFromLeft) break main;
			
			sReturn = StringZZZ.midLeftRight(sString, iLeftPositionFromLeft, iRightPosition);
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightback(String sString, String sLeft, String sRight) throws ExceptionZZZ {
		return StringZZZ.midLeftRightback(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightback(String sString, String sLeft, String sRight, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
			
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.left(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				break main;
			}
			
			if(StringZZZ.equals(sLeft, sRight, bExactMatch)) {
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch); //dann also wie in midLeftRight(...)
				sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			}else {
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);
			}
		
		}
		return sReturn;
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: [[test]]
	* @param sLeft    e.g.: [
	* @param sRight  e.g.: ]
	* @return   ==>          test
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, String sLeft, String sRight) throws ExceptionZZZ {
		return StringZZZ.midLeftRight(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRight(String sString, String sLeft, String sRight, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
								
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
			sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			

		
		}
		return sReturn;
	}
	
	//#######################################################################
	/** returns the string cut on the left and on the right
	 *  INCLUDING the left and right Border
	* @param sString e.g.: [[test]]
	* @param sLeft    e.g.: [
	* @param sRight  e.g.: ]
	* @return   ==>          test
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightKeep(String sString, String sLeft, String sRight) throws ExceptionZZZ {
		return StringZZZ.midLeftRightKeep(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	 *  INCLUDING the left and right Border
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightKeep(String sString, String sLeft, String sRight, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
								
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			sReturn = StringZZZ.rightbackKeep(sString, sLeft, bExactMatch);
			sReturn = StringZZZ.leftKeep(sReturn, sRight, bExactMatch);
		}
		return sReturn;
	}
	
	//#######################################################################
	/** returns the string cut on the left and on the right
	* @param sString e.g.: [[test]]
	* @param sLeft    e.g.: [
	* @param sRight  e.g.: ]
	* @return   ==>          test
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightIntersect(String sString, String sLeft, String sRight) throws ExceptionZZZ {
		return StringZZZ.midLeftRightbackIntersect(sString, sLeft, sRight, true);
	}
	
	/** returns the string cut on the left and on the right
	* @param sString e.g.: abc~=~xyz
	* @param sLeft    e.g.: ~
	* @param sRight  e.g.: ~
	* @return   ==>           =
	* 
	* lindhauer; 19.08.2008 09:42:33
	 */
	public static String midLeftRightbackIntersect(String sString, String sLeft, String sRight, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
			
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			 
			//Normalerweise wird aber das midLeftRightback Ergebnis erwartet.
//			sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
//			sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			
			//Merke: Nur nach links und rechts abzuprüfen ist nun logisch richtig. 
			//       Um sie vom "erwarteten midLeftRighback Ergebnis abzugrenzen, bekommt diese Methode daher einen besonderen Namen "...Intersected(...)
			
			if(StringZZZ.equals(sLeft, sRight, bExactMatch)) {
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch); //dann also wie in midLeftRight(...)
				sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			}else {
				sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
				sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);
			}
		
		}
		return sReturn;
	}
	
	//#########################################
	
	/**Aus dem String
	 * https://github.com/firak01/Projekt_Kernel02_JAZDummy.git
	 * 
	 * soll firak01 geholt werden,
	 * wenn github.com/
	 * und  / übergeben wird
	 * 
	 * UND DAS AUCH wenn vielleicht ohne Projekt gearbeitet werden könnte und die URL für die Suche mit / erweitert wird.
	 * https://github.com/firak01/Projekt_Kernel02_JAZDummy.git/
	 * https://github.com/firak01/
	 * 
	 * @param sString
	 * @param sRight
	 * @param sLeft
	 * @param bExactMatch
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String midRightLeft(String sString, String sLeft, String sRight) throws ExceptionZZZ {
		return StringZZZ.midRightLeft(sString, sLeft, sRight, true);
	}
	
	public static String midRightLeft(String sString, String sLeft, String sRight, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString))break main;			
			if(StringZZZ.isEmpty(sLeft) && StringZZZ.isEmpty(sRight))break main;
								
			if (StringZZZ.isEmpty(sLeft)){
				sReturn = StringZZZ.leftback(sString, sRight, bExactMatch);
				break main;
			}
			
			if(StringZZZ.isEmpty(sRight)){
				sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
				break main;
			}
			
			//Merke: Nur nach links und rechts abzuprüfen wäre zwar logisch richtig. 
			//       erwartet wird aber eigentlich das midLeftRightback Ergebnis....
			//sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);

			//sReturn = StringZZZ.rightback(sString, sLeft, bExactMatch);
			//sReturn = StringZZZ.leftback(sReturn, sRight, bExactMatch);
			
			sReturn = StringZZZ.right(sString, sLeft, bExactMatch);
			sReturn = StringZZZ.left(sReturn, sRight, bExactMatch);
			

		
		}
		return sReturn;
	}
	
	//########################################
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * 
	 * Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von rechts nach dem Suchstring gesucht.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @return String
	 */
	public static String rightKeep(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.rightKeep(sString, sToFind, true, 0);
	}
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * 
	 * Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von rechts nach dem Suchstring gesucht.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @return String
	 */
	public static String rightKeep(String sString, String sToFind, int iIndexStartingFromRight) throws ExceptionZZZ {
		return StringZZZ.rightKeep(sString, sToFind, true, iIndexStartingFromRight);
	}
	
	
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return String
	 */
	public static String rightKeep(String sString, String sToFind, boolean bExactMatch, int iIndexStartingFromRight) throws ExceptionZZZ {
//		String sReturn=sString;
//		main:{
//			if(StringZZZ.isEmpty(sString)) break main;
//				
//			sReturn = "";
//			if(StringZZZ.isEmpty(sToFind)) break main;	
			
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
				
			int iIndexStarting = sString.length() - iIndexStartingFromRight - 1;
			if(iIndexStarting<0) {
				iIndexStarting=0;
			}
		
			int iIndex;
			if(bExactMatch){			
				iIndex = sString.lastIndexOf(sToFind, iIndexStarting);				
			}else {

				String sStringLCase = sString.toLowerCase();
				String sToFindLCase = sToFind.toLowerCase();
				
				iIndex = sStringLCase.lastIndexOf(sToFindLCase, iIndexStarting);
			}
			if(iIndex<= -1) break main;
			
			//die Länge des Strings NICHT(!) aufaddieren, wie es ohne KEEP waere
			//iIndex = iIndex + sToFind.length();
				
			sReturn = sString.substring(iIndex);						
		}//END main:
		return sReturn;
	}


	
	//############################################
	public static String letterAtPosition(String sString, int iIndex) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(iIndex<0) {
				sReturn = null;
				break main;
			}
			
			char[] ca = sString.toCharArray();
			char c = ca[iIndex];
			
			sReturn = Character.toString(c);
		}
		return sReturn;
	}
	
	public static String letterFirst(String sString) throws ExceptionZZZ {
		return StringZZZ.letterAtPosition(sString, 0);
	}
	
	public static String letterLast(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			sReturn = StringZZZ.letterAtPosition(sString, sString.length()-1);
		}
		return sReturn;
	}
	
	//#############################
	/** String, analog to LotusScript, returns the iLength number of strings from the right. Null if sString is null or empty.
	* Lindhauer; 15.05.2006 10:53:48
	 * @param iLength
	 * @return String
	 */
	public static String right(String sString, int iPos) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
						
//		String sReturn = null;
//		main:{			
//			if (StringZZZ.isEmpty(sString)) break main;
			if(iPos<=-1) break main;	
			
			sReturn = sString;
			int iLength = sString.length();
			int iBeginn = iLength-iPos;
			if(iBeginn<=0) break main;			
			if(iBeginn>=iLength+1) break main;
		
			sReturn = sString.substring(iBeginn);

		}//END main:
		return sReturn;
	}
	
	
	/** String,  analog to LotusScript, returns the substring "right from the last  occurance of sToFind". Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * 
	 * Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von rechts nach dem Suchstring gesucht.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @return String
	 */
	public static String right(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.right(sString, sToFind, true);
	}
	
	/** String,  analog to LotusScript, returns the substring right from the last  occurance of sToFind. Null if sString is null or empty or sToFind can not be found in the string.
	 * Returns the empty String if sToFind is empty
	 * bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	* Lindhauer; 16.05.2006 08:11:18
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return String
	 */
	public static String right(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
					
			int iIndex;
			if(bExactMatch){
				iIndex = sString.lastIndexOf(sToFind);				
			}else {

				String sStringLCase = sString.toLowerCase();
				String sToFindLCase = sToFind.toLowerCase();
				
				iIndex = sStringLCase.lastIndexOf(sToFindLCase);
			}
			if(iIndex<= -1) break main;
			
			//die Länge des Strings aufaddieren
			iIndex = iIndex + sToFind.length();
				
			sReturn = sString.substring(iIndex);						
		}//END main:
		return sReturn;
	}
	
	
	
	//#####################################
	
	/** Ergänzt die String.valueOf(obj) Methode um sicheres Typecasting 
	 *  und die Betrachtung von speziellen Klassen.
	 * @param obj
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.07.2025, 09:08:02
	 */
	public static String valueOf(Object obj) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(obj==null) break main;
			
			if(obj instanceof NullObjectZZZ) {
				sReturn = ((NullObjectZZZ) obj).valueOf();
			}else {
				try {
					sReturn = String.valueOf(obj);
				}catch (ClassCastException e) {
					//e.printStackTrace();
				}											
			}				
		}//end main
		return sReturn;		
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sLeftSep, String sRightSep) throws ExceptionZZZ {
		return StringZZZ.vecMid(sStringToParse, sLeftSep, sRightSep, true);
	}
	
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ {
		return StringZZZ.vecMid(sStringToParse, sLeftSep, sRightSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMid(String sStringToParse, String sSepLeft, String sSepRight, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ {
		return StringZZZ.vecMidCascaded(sStringToParse, sSepLeft, sSepRight, bReturnSeparators, bExactMatch);		
	}
	
	//####################################################################################
	
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidCascaded(String sStringToParse, String sLeftSep, String sRightSep) throws ExceptionZZZ {
		return StringZZZ.vecMidCascaded(sStringToParse, sLeftSep, sRightSep, true);
	}
	
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidCascaded(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ {
		return StringZZZ.vecMidCascaded(sStringToParse, sLeftSep, sRightSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidCascaded(String sStringToParse, String sSepLeft, String sSepRight, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.leftKeep(sStringToParse, sSepLeft,bExactMatch);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			String sRemainingTagged= StringZZZ.right(sStringToParse, sStringToParse.length()- MathZZZ.max(sLeft.length(),sSepLeft.length()));
			
			String sExpressionTagged = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch);
			if(StringZZZ.isEmpty(sExpressionTagged)){
				if(!StringZZZ.endsWith(sStringToParse, sSepRight)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
			
			String sMid = sExpressionTagged; 
			
			String sRight = StringZZZ.rightback(sRemainingTagged, sMid.length());
			
			if(!bReturnSeparators) {
				sLeft = StringZZZ.stripRight(sLeft,  sSepLeft);
				sRight = StringZZZ.stripLeft(sRight, sSepRight);							
			}
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sMid, sRight);			
		}//end main:
		return vecReturn;		
	}
	
	public static Vector3ZZZ<String>vecMidKeepSeparatorCentral(String sStringToParse, String sSepLeft, String sSepRight) throws ExceptionZZZ{
		return vecMidKeepSeparatorCentral(sStringToParse, sSepLeft, sSepRight, true);
	}
	
	public static Vector3ZZZ<String>vecMidKeepSeparatorCentral(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		return vecMidCascadedKeepSeparatorCentral(sStringToParse, sSepLeft, sSepRight, bExactMatch);
	}
	
	public static Vector3ZZZ<String>vecMidKeepSeparator(String sStringToParse, String sSepLeft, String sSepRight) throws ExceptionZZZ{		
		return vecMidCascadedKeep(sStringToParse, sSepLeft, sSepRight, true);
	}
	
	public static Vector3ZZZ<String>vecMidKeepSeparator(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		return vecMidCascadedKeep(sStringToParse, sSepLeft, sSepRight, bExactMatch);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidCascadedKeepSeparatorCentral(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String sLeft = StringZZZ.left(sStringToParse, sSepLeft,bExactMatch);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepLeft.length());
						
			String sExpressionTagged = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch);
			if(StringZZZ.isEmpty(sExpressionTagged)){
				if(!StringZZZ.endsWith(sStringToParse, sSepRight)) {
					vecReturn.replace(0,sStringToParse);//Wenn der Tag sebst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
			
			
			//nun gibt es einen Ausdruck			
			String sMid = sExpressionTagged;
			
			String sRight = new String("");
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sMid.length()-sSepRight.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				if(sRight==null) sRight = "";
			}
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replaceKeepSeparatorCentral(sLeft, sMid, sRight, sSepLeft, sSepRight);			
		}//end main:
		return vecReturn;		
	}

	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls der Trenner selbst zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Diese Methode dient z.B. dazu einen leeren Tag zu bearbeiten.
	 *        Ansonsten sind 2 Trenner notwendig.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String> vecMidFirst(String sStringToParse, String sMidSep, boolean bReturnSeparators) throws ExceptionZZZ{
		return StringZZZ.vecMidFirst(sStringToParse, sMidSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls der Trenner selbst zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Diese Methode dient z.B. dazu einen leeren Tag zu bearbeiten.
	 *        Ansonsten sind 2 Trenner notwendig.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirst(String sStringToParse, String sSepMid, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepMid)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(!StringZZZ.contains(sStringToParse, sSepMid,bReturnSeparators)) {
				vecReturn.replace(sStringToParse);
				break main;
			}
			
			
			String sLeft = StringZZZ.left(sStringToParse, sSepMid,bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepMid.length());
			String sRight = sRemainingTagged;
									
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sRight, bReturnSeparators, sSepMid);
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String> vecMidFirst(String sStringToParse, String sLeftSep, String sRightSep) throws ExceptionZZZ{
		return StringZZZ.vecMidFirst(sStringToParse, sLeftSep, sRightSep, true, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sLeftSep
	* @param sRightSep
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String> vecMidFirst(String sStringToParse, String sLeftSep, String sRightSep, boolean bReturnSeparators) throws ExceptionZZZ{
		return StringZZZ.vecMidFirst(sStringToParse, sLeftSep, sRightSep, bReturnSeparators, true);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirst(String sStringToParse, String sSepLeft, String sSepRight, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.leftKeep(sStringToParse, sSepLeft,bExactMatch);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length() - MathZZZ.max(sLeft.length(), sSepLeft.length()));									
			String sMid = StringZZZ.left(sRemainingTagged, sSepRight, bExactMatch);//!!!nur left, wg. "First", anders als sonst leftback!!!
			if(StringZZZ.isEmpty(sMid)){
				if(!StringZZZ.endsWith(sRemainingTagged, sSepRight)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
			
			sRemainingTagged = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sMid.length());
			
			String sRight = sRemainingTagged;
			
			if(!bReturnSeparators) {
				sLeft = StringZZZ.stripRight(sLeft,  sSepLeft, false);
				sRight = StringZZZ.stripLeft(sRight, sSepRight, false);							
			}
			
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sMid, sRight);
		}//end main:
		return vecReturn;
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirstKeepSeparatorCentral(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sSepLeft, bExactMatch);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepLeft.length());
			//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
			//String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length());
			
			String sExpressionTagged = StringZZZ.left(sRemainingTagged, sSepRight, bExactMatch); //nicht leftback wie bei einem cascaded-Tag benoetigt wuerde.
			if(StringZZZ.isEmpty(sExpressionTagged)){				
				vecReturn.replace(sStringToParse);
				break main;
			}
								
			String sMid = StringZZZ.left(sRemainingTagged, sSepRight, bExactMatch); //nicht leftback wie bei einem cascaded-Tag benoetigt wuerde.   //Das wirkt aber nicht bei verschachtelten XML Strings..., statt dessen wird tatsächlich der erste passende String geholt.
			if(StringZZZ.isEmpty(sMid)){
				if(!StringZZZ.endsWith(sRemainingTagged, sSepRight)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
			
			String sRight = new String("");
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sSepRight.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
				//sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				if(sRight==null) sRight = "";
			}
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replaceKeepSeparatorCentral(sLeft, sMid, sRight, sSepLeft, sSepRight);			
		}//end main:
		return vecReturn;		
	}
	
	
	//++++++++++++++++++++++++
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirstKeepSeparatorCentralTagged(String sStringToParse, String sStringToFind, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sStringToFind)){
				ExceptionZZZ ez = new ExceptionZZZ("The sStringToFind string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sSepLeft = XmlUtilZZZ.findFirstTagNamePreviousTo(sStringToParse, sStringToFind);
			
			
			String sSepRight = XmlUtilZZZ.findFirstTagNamePreviousTo(sStringToParse, sStringToFind);
			
			vecReturn = StringZZZ.vecMidFirstKeepSeparatorCentral(sStringToParse, sSepLeft, sSepRight, bExactMatch);
		}//end main:
		return vecReturn;		
	}
	
	
	//############################################################################
	
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirstKeep(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		return StringZZZ.vecMidFirstKeep(sStringToParse, sSepLeft, sSepRight, bExactMatch, 0);
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidFirstKeep(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch, int iIndexStartingFromLeftIn) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			int iIndexStartingFromLeft = iIndexStartingFromLeftIn - sSepLeft.length();
			if(iIndexStartingFromLeft<=-1) iIndexStartingFromLeft = 0; //Zur Sicherheit.
			String sLeft = StringZZZ.leftKeep(sStringToParse, sSepLeft, bExactMatch, iIndexStartingFromLeft);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			//String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepLeft.length());
			//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length());
			
			String sExpressionTagged = StringZZZ.left(sRemainingTagged, sSepRight, bExactMatch); //nicht leftback wie bei einem cascaded-Tag benoetigt wuerde.
			if(StringZZZ.isEmpty(sExpressionTagged)){
				vecReturn.replace(sStringToParse);
				break main;
			}
		
								
			String sMid = StringZZZ.left(sRemainingTagged, sSepRight, bExactMatch); //nicht leftback wie bei einem cascaded-Tag benoetigt wuerde.   //Das wirkt aber nicht bei verschachtelten XML Strings..., statt dessen wird tatsächlich der erste passende String geholt.
			String sRight = new String("");
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				//sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sSepRight.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				if(sRight==null) sRight = "";
			}
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sMid, sRight);			
		}//end main:
		return vecReturn;		
	}
	
	/** Gibt einen Vector mit 3 String-Bestandteilen zurück. Links, Mitte, Rechts. Falls die Trenner zurückgegeben werden sollen, die sonst im Mitte-String sind, muss bReturnSeparators auf true stehen.
	 * Merke: Die Mitte ist nur vorhanden, falls es sowohl den linken als auch den rechten SeparatorString gibt.
	* @param sStringToParse
	* @param sSepLeft
	* @param sSepRight
	* @param bReturnSeperators
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:56:33
	 */
	public static Vector3ZZZ<String>vecMidCascadedKeep(String sStringToParse, String sSepLeft, String sSepRight, boolean bExactMatch) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		main:{											
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSepLeft)){
				ExceptionZZZ ez = new ExceptionZZZ("Left separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sSepRight)){
				ExceptionZZZ ez = new ExceptionZZZ("Right separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.leftKeep(sStringToParse, sSepLeft, bExactMatch);
			if(StringZZZ.isEmpty(sLeft)){
				if(!StringZZZ.startsWith(sStringToParse, sSepLeft)) {
					vecReturn.replace(0, sStringToParse);//Wenn der Tag selbst nicht vorhanden ist, dann den ganzen String in 0 zurueckgeben.
					break main;
				}
			}
						
			//String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSepLeft.length());
			//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length());
			
			String sExpressionTagged = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch);
			if(StringZZZ.isEmpty(sExpressionTagged)){
				vecReturn.replace(sStringToParse);
				break main;
			}
		
			String sMid = sExpressionTagged; 
			
			String sRight = StringZZZ.rightback(sRemainingTagged, sMid.length());
			
			//String sMid = StringZZZ.leftback(sRemainingTagged, sSepRight, bExactMatch); 
			//String sRight = new String("");
			if(sMid==null) {
				sMid = "";
				sRight = sRemainingTagged;
			}else {
				//sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length()-sSepRight.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				//Gewuenscht ist, das der Seperator drin bleibt, also quasi ein StringZZZ.rightKeep(...), aber mit Indexwerten
				sRight = StringZZZ.right(sRemainingTagged, sRemainingTagged.length()-sExpressionTagged.length());//Das wuerde bei verschachtelten XML Strings funktionieren.
				if(sRight==null) sRight = "";
			}
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sMid, sRight);			
		}//end main:
		return vecReturn;		
	}
	//######################################################################
	
	/**Rückgabewert ist ein Vektor aus 3 Teilen: Anfang, Mitte, Ende
	 *                                           Ggfs. ist Mitte immer leer.
	 * @param sStringToParse
	 * @param sSeparator
	 * @param bReturnSeparators
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static Vector3ZZZ<String>vecSplitFirst(String sStringToParse, String sSeparator, boolean bReturnSeparators) throws ExceptionZZZ {		
		return StringZZZ.vecSplitFirst(sStringToParse, sSeparator, bReturnSeparators, true);
	}
	
	/**Rückgabewert ist ein Vektor aus 3 Teilen: Anfang, Mitte, Ende
	 *                                           Ggfs. ist Mitte immer leer.
	 * @param sStringToParse
	 * @param sSeparator
	 * @param bReturnSeparators
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static Vector3ZZZ<String>vecSplitFirst(String sStringToParse, String sSeparator, boolean bReturnSeparators, boolean bExactMatch) throws ExceptionZZZ {
		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{			
			if(StringZZZ.isEmpty(sStringToParse)) break main;
			if(StringZZZ.isEmpty(sSeparator)){				
				ExceptionZZZ ez = new ExceptionZZZ("Separator string", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sLeft = StringZZZ.left(sStringToParse, sSeparator,bExactMatch);
			if(sLeft==null) sLeft="";
						
			String sRemainingTagged = StringZZZ.right(sStringToParse, sStringToParse.length()-sLeft.length()-sSeparator.length());
			if(sRemainingTagged==null) sRemainingTagged="";
						
			//Nun die Werte in den ErgebnisVector zusammenfassen
			vecReturn.replace(sLeft, sRemainingTagged, bReturnSeparators, sSeparator);								
		}//end main:
		return vecReturn;
	}
	
	/** Fülle vor den String Leerzeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:53
	 */
	public static String padLeft(String sInput, int length) throws ExceptionZZZ {
		 return String.format("%" + length + "s", sInput);	
	}
	
	/** Fülle vor den String das übergebene Zeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:53
	 */
	public static String padLeft(String sInput, int length, char cFiller) throws ExceptionZZZ {
		return String.format("%1$" + length + "s", sInput).replace(' ', cFiller);
	}
	
	/** Fülle hinter den String Leerzeichen, bis zur angegebenen Anzahl Zeichen gesamt. 
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:48
	 */
	public static String padRight(String sInput, int length) throws ExceptionZZZ {
		 return String.format("%-" + length + "s", sInput);  
	}
	
	/** Fülle vor den String das übergebene Zeichen, bis zur angegebenen Anzahl Zeichen gesamt.
	 * s. https://www.baeldung.com/java-pad-string
	 *     https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java
	 * Merke: Ein anderer Ansatz als die Repeat-Methoden dieser Klasse
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:21:48
	 */
	public static String padRight(String sInput, int length, char cFiller) throws ExceptionZZZ {
		return String.format("%1$-" + length + "s", sInput).replace(' ', cFiller);
	}
	
	/** String, concatenate the string the number of times.
	 * e.g. .repeat("xyz", 2) will return "xyzxyz".
	* 0823; 28.05.2006 10:53:01
	 * @param sString
	 * @param iTimes
	 * @return String
	 */
	public static String repeat(String sString, int iTimes) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			check:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(iTimes <= 0) {
					sReturn = "";
					break main;
				}						
				if(iTimes==1) break main;				
			}//END check:
		
			for(int icount=2; icount <= iTimes; icount ++){
			 sReturn = sReturn + sString;	
			}
			
			/* Aus "Java f�r Domino.". Ist das besser ???? Nutzt StringBuffer !!!
  private static String times(String s, int n)
    {
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < n; i++) {
			b.append(s);
		}
		return b.toString();
	}
			 */
			
		}//END main:
		return sReturn;
	}
	
	/** Wiederhole die Zeichen x mal.
	 *  Merke: Die padLeft / padRight Methoden machen so etwas ähnliches mit Füllzeichen
	 *         aber auf eine andere Weise.
	 * @param c
	 * @param icount
	 * @return
	 * @author Fritz Lindhauer, 12.08.2022, 21:18:41
	 */
	public static final String repeatChar (char c, int icount) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			check:{
				if(icount <=0) break main;
			}
			StringBuffer sb = new StringBuffer();
			for (int k=0;k<icount;k++) {
				sb.append(c);
			}
			sReturn = sb.toString();
		}//END main:
		return sReturn;
	}
	
	public static String word(String sString, String sDelimiter, long lPosition) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			check:{
				if(StringZZZ.isEmpty(sString)) break main;
				if(StringZZZ.isEmpty(sDelimiter)) break main;
				if(lPosition <= 0) {
					sReturn = null;
					break main;								
				}
		}//END check
		
		long lcount=0;
		StringTokenizer objToken = new StringTokenizer(sString, sDelimiter);
		while( objToken.hasMoreTokens()){
			lcount++;
			sReturn = objToken.nextToken();
			if(lcount ==lPosition) break main;
		}
		
		//Falls man hierhin kommt, hat man den String an der Stelle nicht gefunden.
		sReturn = "";
		
		}//END main
		return sReturn;
	}
	
	public static boolean isNull(String sString) throws ExceptionZZZ {
		if(sString==null) return true;
		return false;
	}
	
	/**returns true if the string is empty or null.
	 * FGL: D.h. NULL oder Leerstring 
	 * 
	 * Uses Jakarta commons.lang.
	 * @param sString
	 * @return, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 08:52:50
	 */
	public static boolean isEmpty(String sString) throws ExceptionZZZ {
		return StringUtils.isEmpty(sString);
	}
	
	public static boolean isEmptyNull(String sString) throws ExceptionZZZ {		
		if(sString==null) return true;
		if(sString.equals("")) return true;
		return false;
	}
	
	public static boolean isEmptyTrimmed(String sString) throws ExceptionZZZ {		
		if(sString==null) return true;
		if(sString.trim().equals("")) return true;
		return false;
	}
	
	public static boolean isFloat(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			//check if float
		    try{
		        Float.parseFloat(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not float
		    }
		}//end main:
		return bReturn;		
	}
	
	/** Alles was Integer ist, ist dann nicht Float.
	 *   Auch wenn Float.parseFloat(sString) keine NumberFormatException werfen würde.
	 * @param sString
	 * @return
	 */
	public static boolean isFloatExplizit(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			//check if Integer
			if(StringZZZ.isInteger(sString)) break main;
			
			//check if float
		    try{
		        Float.parseFloat(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not float
		    }
		}//end main:
		return bReturn;		
	}
	
	public static boolean isInteger(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			 //check if int
		    try{
		        Integer.parseInt(sString);
		        bReturn = true;
		    }catch(NumberFormatException e){
		        //not int
		    }
		}//end main:
		return bReturn;		
	}
	

	
	/**returns true if the string is empty or null or containing blanks only.
	 * FGL: D.h. NULL oder Leerstring oder ein String mit nur Leerzeichen drin.
	 * 
	 * Uses Jakarta commons.lang.
	 * @param sString
	 * @return, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 08:52:50
	 */
	public static boolean isBlank(String sString) throws ExceptionZZZ {
		return StringUtils.isBlank(sString);
	}
	
	public static boolean isJson(String sString) throws ExceptionZZZ {
		return JsonUtilZZZ.isJsonValid(sString);
	}
	
	public static boolean isWhitespace(String sString) throws ExceptionZZZ {
		return StringUtils.isWhitespace(sString);
		/*
		String sLineProof = sLine.trim();
		if(StringZZZ.isEmpty(sLineProof)){
			bReturn = true;
			break main;
		}*/
	}
	
	
	/** true, wenn der erste Buchstabe ein Kleinbuchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:15:12
	 */
	public static boolean isLowerized(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toLowerCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}
	
	
	/** true, wenn der erste Buchstabe ein Gro�buchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:16:44
	 */
	public static boolean isCapitalized(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toUpperCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}
	
	/** true, wenn das uebergebene Zeichen doppelt im String vorhanden ist.
	 * @param sString
	 * @param sCharacter
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.12.2025, 08:32:02
	 */
	public static boolean isCharacterDoubled(String sString, char cCharacter) throws ExceptionZZZ{
		//Merke: Der Algorithums funktioniert nur mit einfachen Zeichen
		boolean bReturn = false;
		main:{
			
			String sDelim = CharZZZ.toString(cCharacter);
			Integer[] intaIndex = StringZZZ.indexOfAll(sString, sDelim);
		
			//Nun muss das Zeichen jeweils 1 Zeichen vor dem Indexwert geholt werden, 			
			if(intaIndex!=null){            	
            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
            		Integer inttemp = intaIndex[icount];
            		if(inttemp.intValue()>=0){
            			int itemp = inttemp.intValue()-1;  //Position des Zeichens vor dem ":" 
            			String stemp = sString.substring(itemp, itemp + 1);
            			
            			//Falls dies das Zeichen ist, gefunden
            			if (stemp.equals(sDelim)){
            				bReturn = true;
            				break main;
            			}            			
            		}
            	}
           }
		
		}//end main:
		return bReturn;
	}
	
	/** true, wenn hinter dem uebergebenem Zeichen eines der Zeichen aus dem Array im String vorhanden ist.
	 * @param sString
	 * @param sCharacter
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.12.2025, 08:32:02
	 */
	public static boolean isCharacterBehind(String sString, char cCharacter, char[]caCharacter) throws ExceptionZZZ{
		//Merke: Der Algorithums funktioniert nur mit einfachen Zeichen
		boolean bReturn = false;
		main:{
			
			Integer[] intaIndex1 = StringZZZ.indexOfAll(sString, cCharacter);
			if(!ArrayUtilZZZ.isNull(intaIndex1)) { //Wenn der ControlCharacter nicht vorkommt, darf es keinen Fehler geben
				for(Integer intIndex : intaIndex1) {
					int i = intIndex.intValue();
					int iNext = i+1;
					if(sString.length()> iNext) {
						char cNext = sString.charAt(iNext);
						bReturn = CharArrayZZZ.contains(caCharacter, cNext);
						if(bReturn) break main;
					}		
				}
			}
		
		}//end main:
		return bReturn;
	}

	
	

	/** Checks if the CharSequence contains only Unicode letters.

null will return false. An empty CharSequence (length()=0) will return false.

 StringUtils.isAlpha(null)   = false
 StringUtils.isAlpha("")     = false
 StringUtils.isAlpha("  ")   = false
 StringUtils.isAlpha("abc")  = true
 StringUtils.isAlpha("ab2c") = false
 StringUtils.isAlpha("ab-c") = false
 
	 * 
	 * 
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 14.01.2023, 08:45:10
	 */
	public static boolean isAlphabet(String sString) throws ExceptionZZZ {
		return StringUtils.isAlpha(sString);
	}
	
	/** Checks if the CharSequence contains only Unicode letters or digits.

null will return false. An empty CharSequence (length()=0) will return false.

 StringUtils.isAlphanumeric(null)   = false
 StringUtils.isAlphanumeric("")     = false
 StringUtils.isAlphanumeric("  ")   = false
 StringUtils.isAlphanumeric("abc")  = true
 StringUtils.isAlphanumeric("ab c") = false
 StringUtils.isAlphanumeric("ab2c") = true
 StringUtils.isAlphanumeric("ab-c") = false
 
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 14.01.2023, 08:47:53
	 */
	public static boolean isAlphanumeric(String sString) throws ExceptionZZZ {
		return StringUtils.isAlphanumeric(sString);
	}
	
	/**    Checks if the String contains only unicode digits.
	 * 	Uses Jakarta commons.lang.
	* @return boolean
	* @param sString
	* lindhaueradmin; 24.10.2006 09:16:35
	 */
	public static boolean isNumeric(String sString) throws ExceptionZZZ {
		return StringUtils.isNumeric(sString);
	}
	public static boolean isNumericWithPrefix(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=1) break main; //Für einen Wert mit Vorzeichen muss es zumindest 2 Zeichen geben....
			
			//Hole das 1. Zeichen.
			char c = sString.toCharArray()[0];
			boolean bHasNumericPrefix = CharZZZ.isNumericPrefix(c);
			if(!bHasNumericPrefix) break main;
			
			String sRest = StringZZZ.rightback(sString, 1);
			if(StringZZZ.isNumeric(sRest)) bReturn = true;
						
		}//end main:
		return bReturn;		
	}
	
	/** Beispiel für ein Palindrom: "ANNA".
	 *   Ein einzelner Buchstabe ist kein Palindrom.
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 30.05.2019, 12:45:46
	 */
	public static boolean isPalindrom(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=1) break main;
			
			String sStringReversed = StringZZZ.reverse(sString);
			if(sStringReversed.equals(sString)){
				bReturn = true;
			}			
		}
		return bReturn;
	}
	
	/** true, wenn der erste Buchstabe ein Kleinbuchstabe ist.
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:15:12
	 */
	public static boolean isUpperized(String sString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			String ersterBuchstabe = sString.substring(0, 1);
		    if (ersterBuchstabe.equals(ersterBuchstabe.toUpperCase())) {
		    	bReturn = true;
		    } else {
		       bReturn = false;
		    }
		}
		return bReturn;
	}
	
	/**https://stackoverflow.com/questions/46116882/how-to-check-for-consecutive-double-character-in-a-java-string
	 * @param str
	 * @return
	 * @author Fritz Lindhauer, 17.11.2020, 11:30:57
	 */
	public static boolean hasConsecutiveDuplicateCharacter(String str) throws ExceptionZZZ {
	    Pattern p = Pattern.compile("(.)\\1");
	    Matcher m = p.matcher(str);
	    return m.find();
	}
	
	public static boolean hasConsecutiveDuplicateCharacter(String str, char cToFind) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(str))break main;
			
			String sToFind = CharZZZ.toString(cToFind);
			if(StringZZZ.isEmpty(sToFind)) break main;
						
			Pattern p = Pattern.compile("("+sToFind+")\\1");
		    Matcher m = p.matcher(str);
		    bReturn = m.find();
		}//end main:
		return bReturn;						    
	}
	
	/**@param str
	 * @return
	 * @author Fritz Lindhauer, 17.11.2020, 11:30:57
	 */
	public static boolean endsWithConsecutiveDuplicateCharacter(String str) throws ExceptionZZZ {
	    Pattern p = Pattern.compile("(.)\\1$");
	    Matcher m = p.matcher(str);
	    return m.find();
	}
	
	/** Packt ein Stringarray und einen String zusammen.
	 *  Meke: In Java 8 gibt es diese Funktion schon. Hier für andere Java Versionen einsetzbar.
	 * @param saString
	 * @param sString
	 * @return
	 */
	public static String join(String[] saString, String sString, String sFlag) throws ExceptionZZZ {
		//FGL: Erst ab Java 8: return String.join(File.separator, pathElements);
    	String sReturn = null;
	    main:{    		
			if(StringZZZ.isEmpty(sFlag)){
				sFlag = "BEFORE";				
			}

	        String[] saTemp = StringArrayZZZ.plusString(saString, File.separator, sFlag);		
	       	sReturn = StringArrayZZZ.implode(saTemp);
	    }//End main
    	return sReturn;
	}
	
	 
	 /* Wird null nicht ignoriert, dann wird das "Replacement" verwendet.
	  * Wird null ignoriert, dann wird das "Replacement" verwendet, wenn alle Strings NULL sind.
	  */
	 public static String joinAll(boolean bSkipNull, String sReplacementOnNull, String... sStrings) throws ExceptionZZZ {
	        StringBuilder builder = new StringBuilder();        
	        if(bSkipNull) {
	        	boolean bAnyValue=false;
	        	for (String sString : sStrings) {
	        		if(sString!=null) { 
	        			bAnyValue=true;
	        			builder.append(sString);
	        		}
	        	}
	        	if(bAnyValue) {
	        		return builder.toString();
	        	}else {
	        		return sReplacementOnNull;
	        	}
	        }else {
	        	for (String sString : sStrings) {
	        		if(sString!=null) {
	        			builder.append(sString);
	        		}else {
	        			builder.append(sReplacementOnNull);
	        		}
	        	}
	        	return builder.toString();
	        }	        
	    }
	
	
	 
	 /*Java 1.7. Funktionalität
	 * Es können beliebig viele Strings übergeben werden (varargs Loesung).
	 */
	public static String joinAll(String... sStrings) throws ExceptionZZZ {
        return StringZZZ.joinAll(true, sStrings);
	}
		 
	 /*Java 1.7. Funktionalität
	 * Es können beliebig viele Strings übergeben werden (varargs Loesung).
	 * Aber es ist anders als sonst im ZKernel ueblich notwendig, das der Zusaetzliche Parameter vorne steht.
	 * 
	 * Merke: Wird NULL ignoriert und alle String sind null, dann kommt der Leerstring zurück.
	 */
	 public static String joinAll(boolean bSkipNull, String... sStrings)  throws ExceptionZZZ{
       return StringZZZ.joinAll(bSkipNull, "", sStrings);
    }
	 
	 /*Java 1.7. Funktionalität
	 * Es können beliebig viele Strings übergeben werden (varargs Loesung).
	 * Aber es ist anders als sonst im ZKernel ueblich notwendig, das der Zusaetzliche Parameter vorne steht.
	 * 
	 * Merke: Es NULL ignoriert. Sind alle String null, dann kommt der Defultwert zurück.
	 */
	 public static String joinAllDefaultValueIgnoreNull(String sDefault, String... sStrings) throws ExceptionZZZ {
		 return StringZZZ.joinAll(true, sDefault, sStrings);
	 }
	 
	 /*Java 1.7. Funktionalität
	 * Es können beliebig viele Strings übergeben werden (varargs Loesung).
	 * Aber es ist anders als sonst im ZKernel ueblich notwendig, das der Zusaetzliche Parameter vorne steht.
	 * 
	 * Merke: Es NULL mit dem uebergebenen Wert ersetzt.
	 */
	 public static String joinAllReplaceNullWith(String sReplacement, String... sStrings)  throws ExceptionZZZ{
	        return StringZZZ.joinAll(false, sReplacement, sStrings);
	 }

	
	
	/** The first letter of the string will become a capital letter. E.g. "abcd" => "Abcd"
	 * Uses Jakarta commons.lang.
	* @param sString
	* @return
	* 
	* lindhauer; 13.06.2007 06:58:41
	 */
	public static String capitalize(String sString) throws ExceptionZZZ {
		return StringUtils.capitalize(sString);
	}
	
	/** The first letter of the String will become lowercase. E.g. "CAT" => "cAT"
	* @param sString
	* @return
	* 
	* lindhauer; 20.09.2011 14:28:13
	 */
	public static String uncapitalize(String sString) throws ExceptionZZZ {
		return StringUtils.uncapitalize(sString);
	}
	
	
	/** THIS_IS_AN_EXAMPLE_STRING wird zu ThisIsAnExampleString    , wenn der Delimiter "_" ist.
	* @param s
	* @param sDelimiter
	* @return
	* 
	* lindhauer; 20.09.2011 14:46:19
	 */
	public static String toCamelCase(String sString, String sDelimiter) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sDelimiter)) break main;
			
			//+++++++++++
		   String[] parts = sString.split(sDelimiter);
		   
		   for (int icount=0; icount <= parts.length-1; icount++){
			   String part = parts[icount];
			   sReturn = sReturn + toProperCase(part);
		   }
		}//endmain:
	   return sReturn;
	}
	
	/** Dieser CamelCase arbeitet ohne Delimiter. 
	 *   Wenn der nachfolgende Buchstabe ein Gro�buchstabe ist, wird der aktuelle Buchstabe zum Kleinbuchstaben.
	* @param s
	* @return
	* 
	* lindhauer; 20.09.2011 14:54:55
	 */
	public static String toCamelCase(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
					
			//+++++++++++
		   sReturn = "";
		   for (int icount=0; icount <= sString.length()-2; icount++){
			   String cur = sString.substring(icount,icount+1);
			   String part = sString.substring(icount+1,icount+2); //der n�chste teilstring
			   if(StringZZZ.isCapitalized(part)){
				   //Wenn der Folgebuchstabe ein Großbuchstabe ist, dann den aktuellen Buchstaben zum Kleinbuchstaben umwandeln
				   sReturn = sReturn + cur.toLowerCase();
			   }else{
				   //ansonsten den Wert beibehalten
				   sReturn = sReturn + cur;
			   }
		   }
		   
		   //Den letzten Wert uebernehmen.
		   sReturn = sReturn + sString.substring(sString.length()-1, sString.length() );
		   
		}//endmain:
		return sReturn;
	}

		public static String toProperCase(String sString) throws ExceptionZZZ {
			String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
				sReturn = sString.substring(0, 1).toUpperCase() +  sString.substring(1).toLowerCase();
			}
		    return sReturn;
		}
		
		/** Das Problem ist, das es ggfs. eine TypeCastException gibt.
		 *  Z.B. bei (String)vector.get(0) wird wohl der Typ des Vektor - Element VOR dem greifen der object.toString Methode abgefragt.
		 *
		 *  Diese Methode soll das sicherere machen, indem ein instanceOf vorgeschaltet wird.
		 *  
		 *  
		 * @param obj
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 27.07.2025, 08:23:40
		 */
		public static String toString(Object obj) throws ExceptionZZZ {
			String sReturn = null;
			main:{
				if(obj==null) break main;
				
				if(obj instanceof NullObjectZZZ) {
					sReturn = obj.toString();
				}else {
					try {
						sReturn = obj.toString();
					}catch (ClassCastException e) {
						//e.printStackTrace();
					}											
				}				
			}//end main
			return sReturn;
		}
		
		public static String escapeFileSeparators(String sString) throws ExceptionZZZ {
			String sReturn = null;
			main:{
				if(StringZZZ.isEmptyNull(sString)) break main;
				
				sReturn = StringZZZ.replace(sString, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS, FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);		
			}//end main
			return sReturn;
		}
		
		
		public static String toShorten(String sString, int iShortenMethodType, int iOffset) throws ExceptionZZZ {
			String sReturn = sString;
			main:{
				if(StringZZZ.isEmpty(sString)) break main;
								
				//Ersetz werden nur Worte >= 3 Buchstaben mehr als das OFFSET
				if(sString.length() <= 3 + iOffset){
					sReturn = sString;
					break main;
				}
				
				String sOffset = sString.substring(0,iOffset);
				String sToParse = sString.substring(iOffset);
				
				org.apache.regexp.RE objReRemove = null;
				switch(iShortenMethodType){
				case StringZZZ.iSHORTEN_METHOD_NONE:
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL_LOWERCASE:					
					objReRemove = new org.apache.regexp.RE("[aeiou]\\B");				//als Beispiel für einen sinnvollen Parameter: Entferne alle Vokale...Wortende:		string.replaceAll("[aeiou]\\B", "")
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL_UPPERCASE:					
					objReRemove = new org.apache.regexp.RE("[AEIOU]\\B");				
					break;
				case StringZZZ.iSHORTEN_METHOD_VOWEL:					
					objReRemove = new org.apache.regexp.RE("[aAeEiIoOuU]\\B");		
					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Not configured ShortenMethodType.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
				}
								
			if(objReRemove!=null){
				sReturn = objReRemove.subst(sToParse,"");
			}else{
				sReturn = sToParse; //ausser Spesen nix gewesen....
			}
			sReturn = sOffset + sReturn;
				
			}
		    return sReturn;
		}
		
		
		/* http://www.kodejava.org/examples/266.html
		 * Von FGL angepasst
		 */
		public static InputStream toInputStream(String sString) throws ExceptionZZZ {
			InputStream objReturn=null;
			main:{
				if(sString==null) break main;			
				try {
					objReturn = new ByteArrayInputStream(sString.getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return objReturn;
		}
						
		public static JsonArrayZZZ toJsonArray(String sString) throws ExceptionZZZ {
			JsonArrayZZZ objReturn = null;
			main:{
				objReturn = new JsonArrayZZZ(sString);											
			}//end main
			return objReturn;
		}
		
	     /* 
	      * To convert the InputStream to String we use the
	      * Reader.read(char[] buffer) method. We iterate until the
	      * Reader return -1 which means there's no more data to
	      * read. We use the StringWriter class to produce the string.
	      * 
	      * http://www.kodejava.org/examples/266.html
	      * Von FGL angepasst
	      */
		 public String InputStream2String(InputStream is) throws ExceptionZZZ {
			 String sReturn = null;
			 try{
			 if (is != null) {				 
				 Writer writer = new StringWriter();
				 char[] buffer = new char[1024];
					 Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
					 int n;
					 while ((n = reader.read(buffer)) != -1) {
						 writer.write(buffer, 0, n);
					 }				
				  sReturn =  writer.toString();
			 	} else {        
			 	  sReturn = new String("");
			 	}
			 }catch(Exception e){
			 	e.printStackTrace();
			 } finally {
				 try{
					 is.close();
				 }catch(IOException e){
					 e.printStackTrace();
				 }
			 }
			 return sReturn;
		 }

	
	/** Counts the occurance of the second String in the first one.
	 *    Like countMatches()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:56:01
	 */
	public static int count(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.countMatches(sString, sToFind);
	}
	
	/** Counts the occurance of the second String in the first one.
	 *    Like countMatches()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:56:01
	 */
	public static int count(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		return StringZZZ.countMatches(sString, sToFind, bExactMatch);
	}
	
	/** Counts the occurance of the second String in the first one.
	 *    Like count()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:59:27
	 */
	public static int countMatches(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.countMatches(sString, sToFind, true);
	}
	
	/** Counts the occurance of the second String in the first one.
	 *    Like count()
	* @param sString
	* @param sToFind
	* @return
	* 
	* Fritz Lindhauer; 06.10.2015 07:59:27
	 */
	public static int countMatches(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		if(bExactMatch) {
			return StringUtils.countMatches(sString, sToFind);
		}else {
			if(sString!=null) {
				return StringUtils.countMatches(sString.toLowerCase(), sToFind.toLowerCase());
			}else {
				return 0;
			}
		}
	}
	
	public static int countSubstring(String sString, String sToFind) throws ExceptionZZZ {
		int iReturn=0;
		main:{
			if(StringZZZ.isEmpty(sString) || StringZZZ.isEmpty(sToFind)){
				break main;
			}
			
			//Also: Es wird geguckt, ob der String noch im restlichen Teistring vorhanden ist,
			//        Falls ja, setze die Indexposition so, dass der restliche Teilstring hinter dem zuletzt gefundenen String beginnt.
			//        Dann wieder gucken... usw.
			int idx = 0;
			while((idx=sString.indexOf(sToFind, idx)) != -1){
				iReturn++;
				idx+=sToFind.length();
			}
					
		}//end main:
		return iReturn;
	}
	
	public static int countChar(String sString, char cToFind) throws ExceptionZZZ {
		int iReturn=0;
		main:{
			//Merke: Als primitiver Datentyp kann char nicht null sein
			if(StringZZZ.isEmpty(sString)){
				break main;
			}
			
			for(int i=0; i<sString.length(); i++){
				if(sString.charAt(i)==cToFind){
					iReturn++;
				}
			}
			
		}//end main:
		return iReturn;
	}
	
	public static int countChar(String sString, Character objChar) throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sString) || objChar == null){
				break main;
			}
			char cToFind = objChar.charValue();
			iReturn = StringZZZ.countChar(sString, cToFind);
		}		
		return iReturn;
	}
	
	
	//#######################################################################
	/** Gezählt wird von Links
	 * 
	 * 
	 * Returns the right part, but iPosition is counted from the left.
	 * e.g. StringZZZ.rightback("abcde", 2) will return "cde".
	 * 
	 * This example returns "nard Wallace." 
@RightBack("Lennard Wallace";3) 
	 * 
	* @param sString
	* @param iPosition
	* @return
	* 
	* lindhauer; 15.06.2007 00:52:08
	 */
	public static String rightback(String sString, int iPosition) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(iPosition <= -1) break main;
			
			if(iPosition >= sString.length()) {
				sReturn = "";
				break main;
			}
		
			sReturn = sString.substring(iPosition, sString.length());
		}
		return sReturn;
	}
	
	/** Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 * @param sString
	 * @param sToFind
	 * @return
	 */
	public static String rightback(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.rightback(sString, sToFind, true);
	}
	
	/**Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 *  bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return
	 */
	public static String rightback(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sToFind)) {
				sReturn = "";
				break main;	
			}
			
			int iIndex;
			if(bExactMatch) {
				iIndex = sString.indexOf(sToFind);				
			}else {
			
				String sStringLCase = sString.toLowerCase();
				String sToFindLCase = sToFind.toLowerCase();
			
				iIndex = sStringLCase.indexOf(sToFindLCase);
			}
			if(iIndex<= -1) break main;
		
			//die Länge des Strings aufaddieren
			iIndex = iIndex + sToFind.length();
				
			sReturn = sString.substring(iIndex);
		}
		return sReturn;
	}
	
	
	//+++++++++++++++

	/**Gibt den String rechts von der Position zurück.
	 *  Gezählt wird von Links.

	 * This example returns "nard Wallace." 
	@RightBack("Lennard Wallace";3)
	
	 Nun bekommt rightBackKeep das Ergebnis "nnard Wallace"

	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return
	 */
	public static String rightbackKeep(String sString, int iPosition) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(iPosition <= -1) break main;
			
			if(iPosition >= sString.length()) {
				sReturn = "";
				break main;
			}
		
			iPosition = iPosition - 1; //-1 ist das Keep
			if(iPosition <= -1 ) iPosition = 0;
			sReturn = sString.substring(iPosition, sString.length());
		}
		return sReturn;
	}
	
	/**Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 * @param sString
	 * @param sToFind
	 * @return
	 */
	public static String rightbackKeep(String sString, String sToFind) throws ExceptionZZZ {
		return StringZZZ.rightbackKeep(sString, sToFind, true);
	}
	
	/**Gibt den String rechts von dem Suchstring zurück.
	 *  Dabei wird von LINKS nach dem Suchstring gesucht.
	 *  bExactMatch=false => es wird von beiden Strings lowercase gebildet.
	 * @param sString
	 * @param sToFind
	 * @param bExactMatch
	 * @return
	 */
	public static String rightbackKeep(String sString, String sToFind, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			String sRightBack=StringZZZ.rightback(sString, sToFind, bExactMatch);
			if(sRightBack==null)break main;
			
			sReturn = sToFind + sRightBack;
		}
		return sReturn;
	}
	
	
	
	/** Ersetze ae, ue, ss, oe in dem übergebenen String.
	 *    Dabei wird versucht besonderheiten zu Berücksichtigen:
	 *    - Ersetzt werden nur Worte >= 4 Buchstaben (so bleibt z.B. Suez bestehen).
	 *    
	 *    ae) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    
	 *    oe) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *          So bleibt Oelde bestehen.
	 *          
	 *    ss) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    	    So bleibt WaffenSS bestehen. (mir fiel kein anderes Wort ein, sorry).
	 *    
	 *    ue) Es wird das ganze Wort betrachtet.
	 *         
	 *          
	 *     Tip: Aufgrund der vielen Ausnahmen sollte man vorher prüfen, ob es nicht schon einen Umlaut gibt:
	 *     org.apache.regexp.RE objReUmlaut = new org.apache.regexp.RE("[ÖöÜüÄäß]");
			boolean bHasUmlaut = objReUmlaut.match(sAllRaw);
	 *          
	* @param sString2Parse
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.09.2008 09:19:44
	 */
	public static String replaceCharacterGerman(String sString2Parse) throws ExceptionZZZ {
		String sReturn = sString2Parse;
		main:{
			//Mache dies als mehrmaligen Aufruf von replaceOneCharacterGerman(sString2Parse, objReturnReference);
			//und wenn objReturnReference true enthaelt, halt noch einmal probieren. Solange bis nix ersetzt wurde.
			
			boolean bGoon = true;
			ReferenceZZZ<Boolean> objReturnReference= new ReferenceZZZ<Boolean>(false);
			while(bGoon) {				
				sReturn = StringZZZ.replaceOneCharacterGerman_(sReturn, objReturnReference);
				bGoon = objReturnReference.get();
			}
		}///end main
		return sReturn;
	}
	
	
	/** Ersetze ae, ue, ss, oe in dem übergebenen String.
	 *    Dabei wird versucht besonderheiten zu Berücksichtigen:
	 *    - Ersetzt werden nur Worte >= 4 Buchstaben (so bleibt z.B. Suez bestehen).
	 *    
	 *    ae) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    
	 *    oe) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *          So bleibt Oelde bestehen.
	 *          
	 *    ss) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    	    So bleibt WaffenSS bestehen. (mir fiel kein anderes Wort ein, sorry).
	 *    
	 *    ue) Es wird das ganze Wort betrachtet.
	 *         
	 *          
	 *     Tip: Aufgrund der vielen Ausnahmen sollte man vorher prüfen, ob es nicht schon einen Umlaut gibt:
	 *     org.apache.regexp.RE objReUmlaut = new org.apache.regexp.RE("[ÖöÜüÄäß]");
			boolean bHasUmlaut = objReUmlaut.match(sAllRaw);
	 *          
	* @param sString2Parse
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.09.2008 09:19:44
	 */
	public static String replaceOneCharacterGerman(String sString2Parse) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = StringZZZ.replaceOneCharacterGerman_(sString2Parse, null);
		}///end main
		return sReturn;
	}
	
	
	/** Ersetze ae, ue, ss, oe in dem übergebenen String.
	 *    Dabei wird versucht besonderheiten zu Berücksichtigen:
	 *    - Ersetzt werden nur Worte >= 4 Buchstaben (so bleibt z.B. Suez bestehen).
	 *    
	 *    ae) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    
	 *    oe) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *          So bleibt Oelde bestehen.
	 *          
	 *    ss) Wird nur innerhalb des Wortes ersetzt (vorne und hinten ein Buchstabe abgeschnitten.
	 *    	    So bleibt WaffenSS bestehen. (mir fiel kein anderes Wort ein, sorry).
	 *    
	 *    ue) Es wird das ganze Wort betrachtet.
	 *         
	 *          
	 *     Tip: Aufgrund der vielen Ausnahmen sollte man vorher prüfen, ob es nicht schon einen Umlaut gibt:
	 *     org.apache.regexp.RE objReUmlaut = new org.apache.regexp.RE("[ÖöÜüÄäß]");
			boolean bHasUmlaut = objReUmlaut.match(sAllRaw);
	 *          
	* @param sString2Parse
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 24.09.2008 09:19:44
	 */
	public static String replaceOneCharacterGerman(String sString2Parse, ReferenceZZZ<Boolean>objReturn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = StringZZZ.replaceOneCharacterGerman_(sString2Parse, objReturn);		
		}///end main
		return sReturn;
	}
	
	private static String replaceOneCharacterGerman_(String sString2Parse, ReferenceZZZ<Boolean>objReturnReferenceIn) throws ExceptionZZZ {
		//TODOGOON20250927 Parameter, ob dieser Umlautkontext für Eigennamen berücksichtigt werden soll
		String sReturn = sString2Parse;
		ReferenceZZZ<Boolean> objReturnReference = null;
		Boolean objReturn = null;
		main:{								
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<Boolean>(false); //nicht den Default-Konstruktor nehmen!!! Referencewert bleibt NULL.
			}else {
				objReturnReference = objReturnReferenceIn;
				objReturn = objReturnReference.get();
				if(objReturn==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Initial value misssing in objReturnReference",iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}
			
			//####################################################################################			
			//Ersetz werden nur Worte >= 4 Buchstaben, die Kleingeschreiben sind.
			//So bleiben Abkuerzungen bestehen. Z.B. EU
			if(sString2Parse.length() <= 3){
				sReturn = sString2Parse;
				break main;
			}
				
			
			//######################################
			//### RegEx Definitons-Block
			//######################################
			//leider ist es mir nicht gelungen das alles auf einen Streich zu definieren und zu ersetzen        org.apache.regexp.RE objRe = new org.apache.regexp.RE("[(oe)]|[(ae)]|[(ue)]|[(ss)]");
			//Daher werden mehrere Objekte definiert.
			//Am Schluss der Funktion erfolgt im Umsetzungsblock dann die Ersetzung der Buchstabenkombination in den dt. Umlaut fuer jedes der Objekte.

			//Merke: Wenn org.apache.regexp.RE verwendet wird fehlt Lookbehind
			//       Lösungsvorschlag: Ersetze dies durch "Capture Group Workaraound"
			//                         Z.B. Statt (?<!q)ue kannst du schreiben: ([^qQ])ue

			//Merke: Wenn org.apache.regexp.RE verwendet ist Lookahead erlaubt.
			//       Z.B. ue([^aeiouAEIOU])
			//       Darum funktioniert die unten beschriebene Loesung fuer den Umlautkontext.
			//       
		
		  //###### Merke: Heuristischer Ansatz fuer Eigennamen Goethe, Suez
		  //              Lösung Umlautkontext beruecksichtigen, durch Lookbehind ([^aeiouAEIOU])
	      //			  z.B. ae([^aeiouAEIOU])
		  //				   oe([^aeiouAEIOU])
				
		
		  //###### Merke: TODO eine Liste an Eigennamen pflegen, die nicht bearbeitet werden duerfen.
					
		
		
			//als Beispiel für einen sinnvollen Parameter			objRe1.setMatchFlags(org.apache.regexp.RE.MATCH_CASEINDEPENDENT); 			
			// stemp = objRe.subst(stemp, "[ö]|[ä]|[ü]|[ß]");//objRe.subst("[(oe)][(ae)][(ue)][(ss)]","[ö][ä][ü][ß]"); oder ähnlich.
			//als ein Beispiel für ne statische Methode: String stemp2 =org.apache.regexp.RE.simplePatternToFullRegularExpression("oe");
			//Merke: Als Beispiel für die Prüfung: 					//boolean bMatch = objRe.match(stemp);					   			   
			org.apache.regexp.RE objReOe = new org.apache.regexp.RE("oe([^aeiouAEIOU])?"); //(^t) dann klappt Moerser nicht. verhindert die Umsetzung bei Goethe.			
			org.apache.regexp.RE objReOe_kleinEigennamenKorrektur = new org.apache.regexp.RE("ö(th)"); //Soll z.B. Göthe wieder in Goethe korregieren.
			org.apache.regexp.RE objReAe = new org.apache.regexp.RE("ae([^aeiouAEIOU])");
			
			//einen RegEx-Ansatz für die Ersetzung von ue → ü, aber nur in Fällen, wo es sich tatsächlich um ein Umlaut-ue handelt – nicht, wenn es Teil einer echten Buchstabenfolge wie in Quelle ist.
	        //Das Problem: Im Deutschen ist ue nicht immer gleich ü. Klassisch wird ue nur als Ersatz für ü verwendet, wenn kein q davorsteht (weil nach q immer ein „qu-“ Laut kommt, nie ein Umlaut).
			//Also funktioniert die einfache Loesung nicht: org.apache.regexp.RE objReUe1 = new org.apache.regexp.RE("(ue)");
			// * Zudem noch Wortanfang und in der Mitte unterschiedlich behandeln			
			// * Kleinbuchstaben: ue → ü, aber nicht nach q
			// * Ergänzung der heuristischen Lösung um (^z). 
			//   Das ist ein Weg um "Suez" (also Eigenname des Suez Kanals) nicht in "Süz" umzuwandeln.
			
			
			org.apache.regexp.RE objReUe_klein = new org.apache.regexp.RE("([^qQ])ue([^aeiouAEIOU])?");//(^z)"); //mit dem "Eigennamencheck" matcht das sonst nicht beim Wort "muessen"
			org.apache.regexp.RE objReUe_kleinEigennamenKorretur = new org.apache.regexp.RE("ü(z)");
			
			// Großbuchstaben: Ue → Ü, aber nicht nach Q
			org.apache.regexp.RE objReUe_gross = new org.apache.regexp.RE("([^qQ])Ue([^aeiouAEIOU])");
			
						
			//Direkt am Anfang:
			org.apache.regexp.RE objReUe_amAnfangGross = new org.apache.regexp.RE("^Ue([^aeiouAEIOU])");
			org.apache.regexp.RE objReUe_amAnfangKlein = new org.apache.regexp.RE("^ue([^aeiouAEIOU])");
			
			//Grossschreibung
			org.apache.regexp.RE objReUe_Grossschreibung = new org.apache.regexp.RE("![Q]UE([^aeiouAEIOU])");
			
			
			//ss normal ss -> ß, aber nicht nach Z x Y oder einem anderen Umlaut
			//auch nicht wenn e folgt wie bei: Odyssee , müssen 
			org.apache.regexp.RE objReSs = new org.apache.regexp.RE("[^zZxXyYüUöÖäÄß]ss([^aeiouAEIOU])");
			
			//ss umwandeln von OdXssee -> OdXßee erlauben
			//b-d f-h j-n p-t v-x z → alle Konsonanten außer Vokale
			//B-D F-H J-N P-T V-X Z → Großbuchstaben-Konsonanten OHNE Y
			//0-9 → Ziffern
			org.apache.regexp.RE objReSs_ohneY = new org.apache.regexp.RE("([b-df-hj-np-tv-xzB-DF-HJ-NP-TV-XZ0-9])ss");  //nicht ß selber und kein y (wg. Odyssee)
													
					
			//es dürfen keine 3 Vokale aufeinander folgen z.B. aus "treuer" soll nicht "treür" werden.
			org.apache.regexp.RE objReVowel = new org.apache.regexp.RE("[aeou]{3,}"); 
			
			
			//##################################################################
			//### Ersetzungsblock
			//##################################################################
			String sReplace = StringZZZ.midBounds(sString2Parse, 1, 1);
			boolean bTest = objReVowel.match(sReplace);
			if(bTest==true){
				//Merke: Da hier der nur 1. Buchstabe ersetzt wird, anschliessend sofort: break main;
				sReturn = sString2Parse;
				break main;
			}
				
				
			if(!sReplace.equals("")){//Merke: Ist ein Wort z.B. nur ein Buchstabe, so würde er verdoppelt beim Zusammenbauen
				boolean bMatch; boolean bMatch1; boolean bMatch2; String sReturnBackup=null; 
				
				//sReplace = objReOe.subst(sReplace, "ö");
				sReturnBackup = sReturn;
				bMatch1 = objReOe.match(sReplace);
				if(bMatch1) {
					sReplace = StringZZZ.replaceFirst(sReplace, "oe", "ö");
					
					//2b) Wieder zusammenbauen
					sReturn = StringZZZ.left(sString2Parse, 1) + sReplace + StringZZZ.right(sString2Parse, 1);					
				}
				
				//aber: In einem 2. Schritt pruefen, ob nicht Goethe in Göthe umgewandelt wurde.
				//      Wenn das fälschlicherweise passiert ist, wieder korregieren.
				bMatch2 = objReOe_kleinEigennamenKorrektur.match(sReturn);
				if(bMatch2) {
					sReturn = sReturnBackup;
				}
				if(bMatch1 & !bMatch2) {
					objReturn = true;
					break main;
				}
				
				
				//sReplace = objReAe.subst(sReplace, "ä");
				bMatch = objReAe.match(sReplace);
				if(bMatch) {
					sReplace = StringZZZ.replaceFirst(sReplace, "ae", "ä");
					
					//2b) Wieder zusammenbauen
					sReturn = StringZZZ.left(sString2Parse, 1) + sReplace + StringZZZ.right(sString2Parse, 1);
					objReturn = true;
					break main;
				}
				
									
				bMatch = objReSs.match(sReplace);
				if(bMatch){
					sReplace = StringZZZ.replaceFirst(sReplace, "ss", "ß");
					
					//2b) Wieder zusammenbauen
					sReturn = StringZZZ.left(sString2Parse, 1) + sReplace + StringZZZ.right(sString2Parse, 1);
					objReturn = true;
					break main;
				}
				else {
					bMatch = objReSs_ohneY.match(sReplace);
					if(bMatch){
						sReplace = StringZZZ.replaceFirst(sReplace, "ss", "ß");
						
						//2b) Wieder zusammenbauen
						sReturn = StringZZZ.left(sString2Parse, 1) + sReplace + StringZZZ.right(sString2Parse, 1);
						objReturn = true;
						break main;
					}
				}
			}//end if sReplace!=""
			
			
			if(sReturn.length()>=4) {//20250927 jetzt habe ich auch eine Lösung für Ue am Anfang, darum nicht mehr: && !StringZZZ.isCapitalized(sReturn)){
				boolean bMatch; boolean bMatch1; boolean bMatch2; String sReturnBackup=null;
				
				
				//Zweistufige korrektur notwendig
				sReturnBackup = sReturn;
				bMatch1 = objReUe_klein.match(sReturn);				
				if(bMatch1){
					sReturn = StringZZZ.replaceFirst(sReturn, "ue", "ü");					
				}
				
				//aber: In einem 2. Schritt pruefen, ob nicht Suez in Süz umgewandelt wurde.
				//      Wenn das fälschlicherweise passiert ist, wieder korregieren.
				bMatch2 = objReUe_kleinEigennamenKorretur.match(sReturn);
				if(bMatch2) {
					sReturn = sReturnBackup;
				}
				if(bMatch1 & !bMatch2) {
					objReturn = true;
					break main;
				}
				
				bMatch = objReUe_gross.match(sReturn);
				if(bMatch){
					sReturn = StringZZZ.replaceFirst(sReturn, "Ue", "Ü");
					objReturn = true;
					break main;
				}
				
				bMatch = objReUe_Grossschreibung.match(sReturn);
				if(bMatch){
					sReturn = StringZZZ.replaceFirst(sReturn, "UE", "Ü");
					objReturn = true;
					break main;
				}
				
				bMatch = objReUe_amAnfangGross.match(sReturn);
				if(bMatch){
					sReturn = StringZZZ.replaceLeft(sReturn, "Ue", "Ü");
					objReturn = true;
					break main;
				}
				
				bMatch = objReUe_amAnfangKlein.match(sReturn);
				if(bMatch){
					sReturn = StringZZZ.replaceLeft(sReturn, "ue", "ü");
					objReturn = true;
					break main;
				}												
			}
			
			objReturn = false;				
		}///end main		
		objReturnReference.set(objReturn);
		return sReturn;
	}
	
	public static String replaceCharacterGermanFromSentence(String sString2ParseIn) throws ExceptionZZZ {
		String sReturn = sString2ParseIn;
		main:{
			if(StringZZZ.isEmptyNull(sString2ParseIn)) break main;
			
			//Ersetz werden nur Worte >= 4 Buchstaben, die keinen großen Anfangsbuchstaben haben. So bleibt z.B. Suez bestehen
			String sString2Parse = sString2ParseIn.trim();
			if(sString2Parse.length() <= 3){
				sReturn = sString2Parse;
				break main;
			}
			
			//++++ Satz in Einzelworte zerlegen...
			StringTokenizer tokenSentence = new StringTokenizer(sString2Parse, " ", false);
			while(tokenSentence.hasMoreTokens()){				
				String sReplaceOrig = (String) tokenSentence.nextToken();
				String stemp = StringZZZ.replaceCharacterGerman(sReplaceOrig);
				
				//++++ ... und wieder zusammenbauen
				if(sReturn.equals("")){
					sReturn = stemp;
				}else{
					sReturn = sReturn + " " + stemp;
				}
			}//End while						
		}//end main:
		return sReturn;
	}

	/* Merke, aus der Doku.
If str is less than maxWidth characters long, return it.
Else abbreviate it to (substring(str, 0, max-3) + "...").
If maxWidth is less than 4, throw an IllegalArgumentException.
In no case will it return a String of length greater than maxWidth.

StringUtils.abbreviate(null, *)      = null
StringUtils.abbreviate("", 4)        = ""
StringUtils.abbreviate("abcdefg", 6) = "abc..."
StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
StringUtils.abbreviate("abcdefg", 4) = "a..."
StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException

	 */
	public static String abbreviateDynamic(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ {
		return abbreviateDynamic_(sSource, iMaxCharactersAllowed, true);
	}
	
	public static String abbreviateDynamicLeft(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ {
		return abbreviateDynamic_(sSource, iMaxCharactersAllowed, false);
	}
	
	private static String abbreviateDynamic_(String sSource, int iMaxCharactersAllowed, boolean bRight) throws ExceptionZZZ {
		String sReturn = sSource;
		main:{
			if(StringZZZ.isEmptyNull(sSource)) break main;			
			if(iMaxCharactersAllowed >= sSource.length()){
				sReturn = sSource;
				break main;
			}
			if(iMaxCharactersAllowed<=1){
				ExceptionZZZ ez = new ExceptionZZZ("MaxCharactersAllowed to small. Must be >= 2.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			String myDynamicAbbreviator=null;
			if(sSource.length()- iMaxCharactersAllowed == 1){
				myDynamicAbbreviator = new String(".");				
			}
			if(sSource.length()- iMaxCharactersAllowed == 2){
				myDynamicAbbreviator = new String("..");				
			}
			
			if(bRight) {
				if(myDynamicAbbreviator!=null){
					int iLeft = iMaxCharactersAllowed - myDynamicAbbreviator.length();
					sReturn = StringZZZ.left(sSource, iLeft);						
					sReturn = sReturn + myDynamicAbbreviator;
					break main;
				}
								
				sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
				break main;
			}else {				
				if(myDynamicAbbreviator!=null){
					int iRight = iMaxCharactersAllowed - myDynamicAbbreviator.length();
					sReturn = StringZZZ.right(sSource, iRight);						
					sReturn = myDynamicAbbreviator + sReturn;
					break main;
				}
				
				sReturn = StringZZZ.right(sSource, iMaxCharactersAllowed-3);
				sReturn = "..." + sReturn;
			}		
	}//end main:
	return sReturn;
	}
	
	/* Merke, aus der Doku.
	If str is less than maxWidth characters long, return it.
	Else abbreviate it to (substring(str, 0, max-3) + "...").
	If maxWidth is less than 4, throw an IllegalArgumentException.
	In no case will it return a String of length greater than maxWidth.

	StringUtils.abbreviate(null, *)      = null
	StringUtils.abbreviate("", 4)        = ""
	StringUtils.abbreviate("abcdefg", 6) = "abc..."
	StringUtils.abbreviate("abcdefg", 7) = "abcdefg"
	StringUtils.abbreviate("abcdefg", 8) = "abcdefg"
	StringUtils.abbreviate("abcdefg", 4) = "a..."
	StringUtils.abbreviate("abcdefg", 3) = IllegalArgumentException

			 */
	public static String abbreviateStrict(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ {
		return abbreviateStrict_(sSource, iMaxCharactersAllowed,true);
	}
	
	public static String abbreviateStrictFromRight(String sSource, int iMaxCharactersAllowed) throws ExceptionZZZ {
		return abbreviateStrict_(sSource, iMaxCharactersAllowed,false);
	}
	
	private static String abbreviateStrict_(String sSource, int iMaxCharactersAllowed, boolean bFromLeft) throws ExceptionZZZ {
		String sReturn = sSource;
		main:{
			if(StringZZZ.isEmptyNull(sSource)) break main;			
			if(iMaxCharactersAllowed >= sSource.length()) break main;
			
			if(iMaxCharactersAllowed<=1){
				ExceptionZZZ ez = new ExceptionZZZ("MaxCharactersAllowed to small. Must be >= 2.", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String myDynamicAbbreviator=null;
			if(iMaxCharactersAllowed==2 && sSource.length()>=3){
				myDynamicAbbreviator = new String(".");				
			}
			if(iMaxCharactersAllowed==3 && sSource.length()>=3){
				myDynamicAbbreviator = new String("..");				
			}
			
			if(bFromLeft) {
				if(myDynamicAbbreviator!=null){
					sReturn = StringZZZ.left(sSource, 1);
					sReturn = sReturn + myDynamicAbbreviator;
					break main;
				}				
				sReturn = StringUtils.abbreviate(sSource, iMaxCharactersAllowed);
				break main;
			}else {
				if(myDynamicAbbreviator!=null){
					sReturn = StringZZZ.right(sSource, 1);
					sReturn = myDynamicAbbreviator + sReturn;
					break main;
				}
				sReturn = StringZZZ.right(sSource, iMaxCharactersAllowed-3);
				sReturn = "..." + sReturn;
			}
		
		
		
		
		
	}//end main:
	return sReturn;
	}

	//##########################################
	
	/** Haenge an den String den anderen String an, aber nur die Zeichen, die in dem vorherigen String noch fehlen!
	 * 
	 * @param sString
	 * @param sToAppend
	 * @return
	 * @author Fritz Lindhauer, 05.11.2022, 09:09:25
	 */
	public static String appendMissing(String sString, String sToAppend) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)&&StringZZZ.isEmpty(sToAppend)) break main;
			if(StringZZZ.isEmpty(sToAppend)) break main;						
			if(StringZZZ.isEmpty(sString)) {
				sReturn = sToAppend;
				break main;
			}
			
			//entferne aus dem anzuhängenden String die Zeichen, die in dem Ausgangsstring vorhanden sind
			char[] caString = sString.toCharArray();
			String sToAppendReal = StringZZZ.stripCharacters(sToAppend, caString);
			if(!StringZZZ.isEmpty(sToAppendReal)) {
				sReturn = sString + sToAppendReal;
			}else {
				sReturn = sString;
			}
		}//end main:
		return sReturn;
	}
	
	
	//#############################################################
	/** Gibt ein Array aller Index-Startpostionen zurück. Das Array ist NICHT sortiert !!!
	* @param sSource, der durchsuchte String.
	* @param saString2Find, die Strings, die gesucht werden. D.h. es können mehrere sein.
	* @return
	* 
	* lindhauer; 30.06.2007 11:43:38
	 */
	public static Integer[] indexOfAll(String sSource, char  cCharacter2Find) throws ExceptionZZZ {
		String[]saString2Find=new String[1];
		
		String sString2Find = CharZZZ.toString(cCharacter2Find);
		saString2Find[0]=sString2Find;
		return StringZZZ.indexOfAll(sSource, saString2Find, true);		
	}
	
	
	/** Gibt ein Array aller Index-Startpostionen zurück. Das Array ist NICHT sortiert !!!
	* @param sSource, der durchsuchte String.
	* @param saString2Find, die Strings, die gesucht werden. D.h. es können mehrere sein.
	* @return
	* 
	* lindhauer; 30.06.2007 11:43:38
	 */
	public static Integer[] indexOfAll(String sSource, String sString2Find) throws ExceptionZZZ {
		String[]saString2Find=new String[1];
		saString2Find[0]=sString2Find;
		return StringZZZ.indexOfAll(sSource, saString2Find, true);		
	}
	
	public static Integer[] indexOfAll(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		String[]saString2Find=new String[1];
		saString2Find[0]=sString2Find;
		return StringZZZ.indexOfAll(sSource, saString2Find, bCaseSensitive);		
	}
	
	public static Integer[] indexOfAll(String sSource, String[] saString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfAll(sSource, saString2Find, true);		
	}
	
	public static Integer[] indexOfAll(String sSource, String[] saString2Find, boolean bCaseSensitive ) throws ExceptionZZZ {
		Integer[] intaReturn=null;
		main:{
			if(isEmpty(sSource)) break main;
			if(ArrayUtilZZZ.isNull(saString2Find)) break main;
			
			ArrayList<Integer> listaReturn = new ArrayList<Integer>();
			for(int icount=0; icount < saString2Find.length; icount ++){
				String sCurrent = sSource;
				int iIndex=-1;
				int iIndexAdded = 0;
				int iLengthOld = 0;
				do{
					iIndex = StringZZZ.indexOfFirst(sCurrent, saString2Find[icount], bCaseSensitive);
					if(iIndex >= 0){
						iIndexAdded += iIndex+iLengthOld;
						Integer intIndexAdded = new Integer(iIndexAdded);
						listaReturn.add(intIndexAdded);
						
						//Nun den String vorne abschneiden !!!
						iLengthOld = saString2Find[icount].length();
						sCurrent = rightback(sCurrent, iIndex+ iLengthOld);//den Index um die L�nge des zu suchenden String serweitern !!!
					}
				}while(iIndex >= 0 & isEmpty(sCurrent)==false);
			}//END for
			
			if(listaReturn.size()>=1){
				intaReturn = new Integer[listaReturn.size()];
				intaReturn = listaReturn.toArray(intaReturn);
			}
		}//END main:
		
		return intaReturn;
	}

	/** wie indexOf, allerdings wird die Laenge des Strings daraufgerechnet.
	 *  Der index ist also der Anfang des dahinterliegenden Teils
	 *  
	 * @param sSource
	 * @param sString2Find
	 * @return
	 * @author Fritz Lindhauer, 05.05.2024, 08:53:59
	 */
	public static int indexOfFirstBefore(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBefore(sSource, sString2Find, true);
	}
	
	public static int indexOfFirstBefore(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.indexOf(sString2Find);			
			}else {
				itemp = sSource.toLowerCase().indexOf(sString2Find.toLowerCase());
			}
			if(itemp<=-1)break main;
			
			iReturn = itemp;
		}//end main:
		return iReturn;
	}
	
	
	public static int indexOfFirstBefore(String sSource, String sString2Find, int iIndexStarting) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBefore(sSource, sString2Find, iIndexStarting, true);
	}
	
	public static int indexOfFirstBefore(String sSource, String sString2Find, int iIndexStarting, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.indexOf(sString2Find, iIndexStarting);			
			}else {
				itemp = sSource.toLowerCase().indexOf(sString2Find.toLowerCase(), iIndexStarting);
			}
			if(itemp<=-1)break main;
			
			iReturn = itemp;
		}//end main:
		return iReturn;
	}
	
	public static int indexOfFirstBehind(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBehind(sSource, sString2Find, true);
	}
	
	public static int indexOfFirstBehind(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.indexOf(sString2Find);			
			}else {
				itemp = sSource.toLowerCase().indexOf(sString2Find.toLowerCase());
			}
			if(itemp<=-1)break main;
			
			iReturn = itemp + sString2Find.length();
		}//end main:
		return iReturn;
	}
	
	public static int indexOfFirstBehind(String sSource, String sString2Find, int iIndexStarting) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBehind(sSource, sString2Find, iIndexStarting, true);
	}
	
	public static int indexOfFirstBehind(String sSource, String sString2Find, int iIndexStarting, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.indexOf(sString2Find, iIndexStarting);			
			}else {
				itemp = sSource.toLowerCase().indexOf(sString2Find.toLowerCase(), iIndexStarting);
			}
			if(itemp<=-1)break main;
			
			iReturn = itemp + sString2Find.length();
		}//end main:
		return iReturn;
	}
	
	public static int indexOfFirst(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfFirst(sSource, sString2Find, true);
	}
	public static int indexOfFirst(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBefore(sSource, sString2Find, bCaseSensitive);
	}
	
	public static int indexOfFirst(String sSource, String sString2Find, int iIndexStarting) throws ExceptionZZZ {
		return StringZZZ.indexOfFirst(sSource, sString2Find, iIndexStarting, true);
	}
	public static int indexOfFirst(String sSource, String sString2Find, int iIndexStarting, boolean bCaseSensitive) throws ExceptionZZZ {
		return  StringZZZ.indexOfFirstBefore(sSource, sString2Find, iIndexStarting, bCaseSensitive);
	}
	

	/** int, Gibt den ersten index wert zurueck, der existiert. Falls keiner der zu suchenden Strings existiert, wird -1 zur�ckgegeben.
	* Lindhauer; 13.05.2006 09:22:14
	 * @param sSource, der String, der durchsucht wird.
	 * @param saString2Find, das Array der Strings, die gesucht werden. D.h. man kann also nach mehreren Strings suchen.
	 * @return
	 */
	public static int indexOfFirst(String sSource, String[] saString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfFirst(sSource, saString2Find, true);
	}
	public static int indexOfFirst(String sSource, String[] saString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn=-1; //Merke: -1 wird auch zurueckgegeben, falls keiner der Teilstring nicht im String enthalten ist.
		main:{
			if(isEmpty(sSource))break main;
			if(ArrayUtilZZZ.isNull(saString2Find)) break main;
			
			int iEndCur=sSource.length() + 1;			
			for(int icount=0; icount < saString2Find.length; icount ++){
				int iEnd = StringZZZ.indexOfFirst(sSource,saString2Find[icount], bCaseSensitive);
				if (iEnd >= 0){
					//Nur Strings, die auch vorkommen, "spielen mit"
					if(iEnd==0){
						//Falls man den ersten Index erreicht hat, dann ist schon vorzeitig schluss.
						iReturn = 0;
						break main;
					}else{
						if(iEnd<iEndCur)	iEndCur = iEnd;					
					}						
				}
			}//END for
						
			//Falls die Laenge von iEndCur unveraendert geblieben ist, dann wurde nix gefunden. Es wird -1 zurueckgegeben
			if(iEndCur==sSource.length()+1) break main;
			iReturn = iEndCur;
		
		}//END main:
		return iReturn;
	}
	
	


	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static int indexOfLast(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfLastBefore(sSource, sString2Find);
	}
	
	/** wie indexOf, allerdings wird die Laenge des Strings daraufgerechnet.
	 *  Der index ist also der Anfang des dahinterliegenden Teils
	 *  
	 * @param sSource
	 * @param sString2Find
	 * @return
	 * @author Fritz Lindhauer, 05.05.2024, 08:53:59
	 */
	public static int indexOfLastBefore(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfLastBefore(sSource, sString2Find, true);
	}
	
	public static int indexOfLastBefore(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.lastIndexOf(sString2Find);
			}else {
				itemp= sSource.toLowerCase().lastIndexOf(sString2Find.toLowerCase());
			}
			if(itemp==-1)break main;
						
			iReturn = itemp;
		}//end main:
		return iReturn;
	}
	
		
	public static int indexOfLastBefore(String sSource, String sString2Find, int iIndexStarting) throws ExceptionZZZ {
		return StringZZZ.indexOfLastBefore(sSource, sString2Find, iIndexStarting, true);
	}
	
	public static int indexOfLastBefore(String sSource, String sString2Find, int iIndexStarting, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.lastIndexOf(sString2Find, iIndexStarting);
			}else {
				itemp= sSource.toLowerCase().lastIndexOf(sString2Find.toLowerCase(), iIndexStarting);
			}
			if(itemp==-1)break main;
						
			iReturn = itemp;
		}//end main:
		return iReturn;
	}

	public static int indexOfLastBehind(String sSource, String sString2Find) throws ExceptionZZZ {
		return StringZZZ.indexOfLastBehind(sSource, sString2Find, true);
	}
	
	public static int indexOfLastBehind(String sSource, String sString2Find, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.lastIndexOf(sString2Find);
			}else {
				itemp = sSource.toLowerCase().lastIndexOf(sString2Find.toLowerCase());
			}
			if(itemp==-1)break main;
						
			iReturn = itemp + sString2Find.length();
		}//end main:
		return iReturn;
	}
	
		
	public static int indexOfLastBehind(String sSource, String sString2Find, int iIndexStarting) throws ExceptionZZZ {
		return StringZZZ.indexOfLastBehind(sSource, sString2Find, iIndexStarting, true);
	}

	public static int indexOfLastBehind(String sSource, String sString2Find, int iIndexStarting, boolean bCaseSensitive) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sSource)) break main;
			if(StringZZZ.isEmpty(sString2Find)) break main;
			
			int itemp;
			if(bCaseSensitive) {
				itemp = sSource.lastIndexOf(sString2Find, iIndexStarting);
			}else {
				itemp = sSource.toLowerCase().lastIndexOf(sString2Find.toLowerCase(), iIndexStarting);
			}
			if(itemp==-1)break main;
						
			iReturn = itemp + sString2Find.length();
		}//end main:
		return iReturn;
	}
	
	

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	/** Erzeugt eine ArrayList, welche die Strings, die in saPattern enthalten sind in der korrekten Reihenfolge und Anzahl wiedergibt.
	 * 
	 * sString = "eins  zwei  drei  eins  zwei  vier  acht sechzehn"
	 * saPattern = {"drei", "zwei"};
	 * 
	 * ===> {"zwei", "drei", "zwei"} als ArrayList
	 * 
	* @param sString
	* @param saPattern
	* @return
	* 
	* lindhaueradmin; 19.03.2007 12:57:39
	 */
	public static ArrayList<String> findSorted(String sString, String[] saPattern) throws ExceptionZZZ {
		ArrayList<String> listaString = new ArrayList<String>();
		main:{
			if(isEmpty(sString)) break main;
			if(ArrayUtilZZZ.isNull(saPattern)) break main;
			
			
			//Idee: In einer Schleife auf Vorhandenheit des Strings des Pattern-Arrays pr�fen  (von links nach rechts).
			//        Der niedrigere Indexwert gewinnt (am besten auch in ein Array packen).
			//        Aus diesem Indexwert und der L�nge des jeweiligen "Pattern" Strings bekommt man dann einen Reststring, der wiederum in der Schleife auf Vorhandenheit gepr�ft werden muss.
			int[] iaPattern = new int[saPattern.length];					
			String sRemaining = new String(sString);			
								
			do{
				int iLength = sRemaining.length();			
				for(int icount = 0; icount <= saPattern.length-1;icount++){
					String stemp = saPattern[icount];
					int itemp = sRemaining.indexOf(stemp);
					if(itemp >=0){
						iaPattern[icount] = itemp;
					}else{
						iaPattern[icount] = iLength + 1; //String wurde nicht gefunden, darum wird hier der h�chstm�gliche Wert plus 1 in das auszuwertende Array geschrieben.					
					}				
				}
				
				//Nun das int - Array auswerten. Der niedrigste Wert gewinnt.
				int itemp = MathZZZ.min(iaPattern);
							
				//Falls der niedrigeste Wert kleiner ist als die L�nge des Strings, muss die Index-Position des Werts ermittelt werden.
				int iIndex=-1;
				if(itemp <= iLength){
					for(int i=0; i<=iaPattern.length-1;i++){
						if(itemp==iaPattern[i]){						
							iIndex = i;
							break;
						}
					}
				}
				
				if(iIndex >= 0){
					String sValue=saPattern[iIndex];
					listaString.add(sValue);	
					sRemaining = right(sRemaining, sRemaining.length() - (iaPattern[iIndex]+sValue.length()));
				}else{
					//Es konnte keinerlei String mehr gefunden werden
					break main;
				}
			}while(sRemaining.length()>=1);
		}//end main
		return listaString;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es werden Grossbuchstaben und Zahlen im uebergebenenString verwendet.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toUpperCaseNumberAbbreviation(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			sReturn = "";
			
			boolean isNumeric=false;
			for(int iIndex=0; iIndex<=sString.length()-1; iIndex++){
				String s = sString.substring(iIndex, iIndex+1);
				try{
					@SuppressWarnings("unused")
					Integer intObj = Integer.parseInt(s);
				   isNumeric = true;
				}catch(Exception e){
					isNumeric = false;
				}
				
				if(s.toUpperCase().equals(s) || isNumeric){
					sReturn+=s;
				}
			}				
		}
		return sReturn;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es werden Grossbuchstaben und Zahlen im uebergebenenString verwendet.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toUpperCaseNumberAbbreviation(String sString, String[] saDelimiterIn) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			sReturn = "";
			
			boolean isNumeric=false;
			
			
			String[] saDelimiterDefault = {"-","_","."};
			String[] saDelimiter;
			if(ArrayUtilZZZ.isNullOrEmpty(saDelimiterIn)) {
				saDelimiter = saDelimiterDefault;
			}else {
				saDelimiter = saDelimiterIn;
			}
			
			String[] saSplitted = StringZZZ.explode(sString, saDelimiter);
			for(String sSplitted :saSplitted) {			
				sReturn = sReturn + toUpperCaseNumberAbbreviation(sSplitted);
			}//end for saSplitted
		}
		return sReturn;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es werden Grossbuchstaben und Zahlen im uebergebenenString verwendet.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toUpperCaseNumberAbbreviation(String sString, String[] saDelimiterIn, String sAppenderIn) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			sReturn = "";
			
			boolean isNumeric=false;
			
			
			String[] saDelimiterDefault = {"-","_","."};
			String[] saDelimiter;
			if(ArrayUtilZZZ.isNullOrEmpty(saDelimiterIn)) {
				saDelimiter = saDelimiterDefault;
			}else {
				saDelimiter = saDelimiterIn;
			}
			
			String sAppender;
			if(StringZZZ.isEmptyNull(sAppenderIn)) {
				sAppender = "";
			}else {
				sAppender = sAppenderIn;
			}
			
			String[] saSplitted = StringZZZ.explode(sString, saDelimiter);
			for(String sSplitted :saSplitted) {
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = toUpperCaseNumberAbbreviation(sSplitted);
				}else {
					sReturn = sReturn + sAppender + toUpperCaseNumberAbbreviation(sSplitted);
				}
			}//end for saSplitted
		}
		return sReturn;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es wird der String aufgeteilt. 
	 * Danach werden die Teile gekürzt und wieder zusammengesetzt.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toShorten(String sString, String[] saDelimiterIn, int iPartLengthIn, String sAppenderIn) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			sReturn = "";
			
			boolean isNumeric=false;
			
			
			String[] saDelimiterDefault = {"-","_","."};
			String[] saDelimiter;
			if(ArrayUtilZZZ.isNullOrEmpty(saDelimiterIn)) {
				saDelimiter = saDelimiterDefault;
			}else {
				saDelimiter = saDelimiterIn;
			}
			
			int iPartLength;
			if(iPartLengthIn<=0) {
				iPartLength = 1;
			}else {
				iPartLength = iPartLengthIn;
			}
			
			String sAppender;
			if(StringZZZ.isEmptyNull(sAppenderIn)) {
				sAppender = "";
			}else {
				sAppender = sAppenderIn;
			}
			
			String[] saSplitted = StringZZZ.explode(sString, saDelimiter);
			for(String sSplitted :saSplitted) {
				String sSplittedTemp = StringZZZ.left(sSplitted, iPartLengthIn);
				
				if(StringZZZ.isEmpty(sReturn)) {					
					sReturn = sSplittedTemp;
				}else {
					sReturn = sReturn + sAppender + sSplittedTemp;
				}
			}//end for saSplitted
		}
		return sReturn;
	}
	
	/** 'Heuristische' Loesung einen 'sprechenden Key' / eine Abkuerzung zu generieren.
	 * Es wird der String aufgeteilt. 
	 * Danach werden die Teile gekürzt und wieder zusammengesetzt.
	 * @author Fritz Lindhauer
	 *
	 * @param sString
	 * @return
	 */
	public static String toShorten(String sStringIn, String[] saDelimiterIn, int[] iaPartLengthIn, String sAppenderIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn))break main;
			sReturn = "";
			
			String sString = sStringIn.trim();
			
			boolean isNumeric=false;
			
			
			
			String[] saDelimiterDefault = {"-","_","."};
			String[] saDelimiter;
			if(ArrayUtilZZZ.isNullOrEmpty(saDelimiterIn)) {
				saDelimiter = saDelimiterDefault;
			}else {
				saDelimiter = saDelimiterIn;
			}
			
			int[] iaPartLengthDefault = { 4, 3};
			int[] iaPartLength;
			if(ArrayUtilZZZ.isNullOrEmpty(iaPartLengthIn)) {
				iaPartLength = iaPartLengthDefault;
			}else {
				iaPartLength = iaPartLengthIn;
			}
			
			String sAppender;
			if(StringZZZ.isEmptyNull(sAppenderIn)) {
				sAppender = "";
			}else {
				sAppender = sAppenderIn;
			}
			
			//Mache als heuristische Lösung die Summe der definierten PartLength für den Fall ohne "Appender"
			int iPartLengthTotal = MathZZZ.sum(iaPartLength);
			
			String[] saSplitted = StringZZZ.explode(sString, saDelimiter);
			int iIndex = -1;
			int iPartLengthUsed=iaPartLengthDefault[0];
			
			//Nur mit dem sAppender arbeiten, wenn der String aufgeteilt worden ist
			if(saSplitted.length>=2) {
				for(String sSplitted :saSplitted) {
					String sSplittedUsed = sSplitted.trim();
					if(!StringZZZ.isEmpty(sSplittedUsed)) {
						iIndex++;
						if(iIndex > iaPartLength.length) {
							//nix, verwende den alten Wert weiter
						}else {
							iPartLengthUsed = iaPartLength[iIndex];
						}
						String sSplittedUsedTemp = StringZZZ.left(sSplittedUsed, iPartLengthUsed);
						
						if(StringZZZ.isEmpty(sReturn)) {					
							sReturn = sSplittedUsedTemp;
						}else {
							sReturn = sReturn + sAppender + sSplittedUsedTemp;
						}
					}
				}//end for saSplitted
			}else {
				sReturn = saSplitted[0];	
				sReturn = sReturn.trim();
				sReturn = StringZZZ.left(sReturn, iPartLengthTotal);
			}//end if saSplitted.length
		}
		return sReturn;
	}
	
	
	/**
	 * @param sString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 15.11.2022, 08:50:02
	 * 
	 * siehe auch UnicodeZZZ.toByteArray(string) und .from(byte[])
	 */
	public static String toUtf8(String sString) throws ExceptionZZZ {		
		String sReturn=sString;
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			try {
				sReturn = new String(sString.getBytes(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				ExceptionZZZ ez = new ExceptionZZZ("UnsupportedEncodingException", iERROR_PARAMETER_VALUE, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main
		return sReturn;
	}
	
	/**Merke: So einfach kann man sonst nicht nach float parsen
	 * @param sString
	 * @return
	 */
	public static float toFloat(String sString) throws ExceptionZZZ {
		float fReturn=0.0f;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			
			if(StringZZZ.isFloat(sString)){
				Float fltValue = new Float(sString);
				fReturn = fltValue.floatValue();
			}else{
				int iValue = Integer.parseInt(sString);	
				fReturn = (float) iValue;
			}
		}//end main:
		return fReturn;
	}
	
	public static String asHtml(String sString) throws ExceptionZZZ {
		String sReturn = null;
		main:{		
			if(StringZZZ.isEmpty(sString)){
				sReturn = "<html><body></body></html>";
				break main;
			}
			
			sReturn = StringZZZ.toHtml(sString);
			

			if(!StringZZZ.startsWithIgnoreCase(sString, "<html>")){
				if(!StringZZZ.startsWithIgnoreCase(sString, "<body>")) {
					sReturn = "<body>" + sReturn;					
				}
				sReturn = "<html>" + sReturn;				
			}
			
			if(!StringZZZ.endsWithIgnoreCase(sString, "</html>")){
				if(!StringZZZ.endsWithIgnoreCase(sString, "</body>")) {
					sReturn = sReturn + "</body>";
				}
				sReturn = sReturn + "</html>";
			}			
		}//end main:
		return sReturn;
	}
	
	public static String toHtml(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			sReturn = StringZZZ.toHtmlEscaped(sString);
			sReturn = StringZZZ.toHtmlCrlf(sString);
			
			//Merke: Will man die HTML - Tags noch haben, dann StringZZZ.asHtml(...)
		}
		return sReturn;
	}
	public static String toHtmlSimple(String sString) throws ExceptionZZZ {
		/*
		 You should do some replacements on the text programmatically. Here are some clues:

    All Newlines should be converted to "<br>\n" (The \n for better readability of the output).
    All CRs should be dropped (who uses DOS encoding anyway).
    All pairs of spaces should be replaced with " &nbsp;"
    Replace "<" with "&lt;"
    Replace "&" with "&amp;"
    All other characters < 128 should be left as they are.
    All other characters >= 128 should be written as "&#"+((int)myChar)+";", to make them readable in every encoding.
    To autodetect your links, you could either use a regex like "http://[^ ]+", or "www.[^ ]" and convert them like JB Nizet said. to "<a href=\""+url+"\">"+url+"</a>", but only after having done all the other replacements.

		  String str = "(?i)\\b((?:https?://|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:\'\".,<>?«»“”‘’]))";
Pattern patt = Pattern.compile(str);
Matcher matcher = patt.matcher(plain);
plain = matcher.replaceAll("<a href=\"$1\">$1</a>");
		 */
		StringBuilder builder = new StringBuilder();
	    boolean previousWasASpace = false;
	    for( char c : sString.toCharArray() ) {
	        if( c == ' ' ) {
	            if( previousWasASpace ) {
	                builder.append("&nbsp;");
	                previousWasASpace = false;
	                continue;
	            }
	            previousWasASpace = true;
	        } else {
	            previousWasASpace = false;
	        }
	        switch(c) {
	            case '<': builder.append("&lt;"); break;
	            case '>': builder.append("&gt;"); break;
	            case '&': builder.append("&amp;"); break;
	            case '"': builder.append("&quot;"); break;
	            case '\n': builder.append("<br>"); break;
	            // We need Tab support here, because we print StackTraces as HTML
	            case '\t': builder.append("&nbsp; &nbsp; &nbsp;"); break;  
	            default:
	                if( c < 128 ) {
	                    builder.append(c);
	                } else {
	                    builder.append("&#").append((int)c).append(";");
	                }    
	        }
	    }
	    return builder.toString();
	}
	
	public static String toHtmlCrlf(String sString) throws ExceptionZZZ {
		String sReturn = StringZZZ.replace(sString, StringZZZ.crlf(), "<br>");
		return sReturn;
	}
	
	
	public static String toHtmlEscaped(String sString) throws ExceptionZZZ {
		String sReturn = StringEscapeUtils.escapeHtml(sString);
		return sReturn;
	}
	
	
	/** Merke: Integer.parseInt(...) wirft beispielsweise eine java.lang.NumberFormatException, wenn man einen float-String, (z.B. "2.0") übergibt.
	 *              Das wird hier vermieden.
	 * @param sValue
	 */
	public static int toInteger(String sValue) throws ExceptionZZZ {
		int iReturn = 0;
		main:{
			if(StringZZZ.isEmpty(sValue)) break main;
			
			if(StringZZZ.isFloat(sValue)){
				Float fltValue = new Float(sValue);
				iReturn = fltValue.intValue();
			}else{
				iReturn = Integer.parseInt(sValue);	
			}
		}
		return iReturn;
	}
	
	//Leerzeichen wird von links und rechts abgeschnitten
	public static String trim(String sString) throws ExceptionZZZ {
		return StringZZZ.trim(sString," ");
	}
	
	//Zeichen werden von links und von rechts abgeschnitten
	public static String trim(String sString, String sStringToBeTrimmed) throws ExceptionZZZ {
		String sReturn = StringZZZ.trimLeft(sString, sStringToBeTrimmed);
		sReturn = StringZZZ.trimRight(sString, sStringToBeTrimmed);
		return sReturn;
	}
	
	
	public static String trimLeft(String sStringIn, int iNumberOfCharacters2beRemoved) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(iNumberOfCharacters2beRemoved<=0) break main;
			
			String sString = sStringIn;
			sReturn = StringZZZ.trimLeft(sString, iNumberOfCharacters2beRemoved, 0);
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	public static String trimLeft(String sStringIn, int iNumberOfCharacters2beRemoved, int iStringLengthMinRemainingIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(iNumberOfCharacters2beRemoved<=0) break main;
			int iStringLengthMinRemaining;
			if(iStringLengthMinRemainingIn<0){
				iStringLengthMinRemaining = 0;
			}else{
				iStringLengthMinRemaining = iStringLengthMinRemainingIn;
			}
			
			String sString = sStringIn;
			sReturn = StringZZZ.rightback(sReturn, iNumberOfCharacters2beRemoved);
			if(StringZZZ.isEmpty(sReturn)) {
				sReturn = StringZZZ.right(sString, 1);
				break main;
			}
			
			if(sReturn.length()< iNumberOfCharacters2beRemoved) {
				sReturn = StringZZZ.right(sString, 1);
				break main;
			}
			
		}//end main:
		return sReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String trimLeft(String sStringIn, String[] saStringToBeTrimmed) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(StringArrayZZZ.isEmpty(saStringToBeTrimmed)) break main;
			
			String sString = sStringIn;
			for(String sStringToBeTrimmed : saStringToBeTrimmed) {
				sString = StringZZZ.trimLeft(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			}
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	public static String trimLeft(String sString, String sStringToBeTrimmed) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			
			sReturn = StringZZZ.trimLeft(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			
		}//end main:
		return sReturn;
	}
	
	public static String trimLeft(String sString, String sStringToBeTrimmed, int iStringLengthMinRemainingIn) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			int iStringLengthMinRemaining;
			if(iStringLengthMinRemainingIn<0){
				iStringLengthMinRemaining = 0;
			}else{
				iStringLengthMinRemaining = iStringLengthMinRemainingIn;
			}
			
			int iStringIndexMinRemaining = 1 + iStringLengthMinRemaining;			
			boolean bGoon = false;			
			while(!bGoon){
				if(sReturn.startsWith(sStringToBeTrimmed) && sReturn.length()>=iStringIndexMinRemaining){
					sReturn = StringZZZ.rightback(sReturn, sStringToBeTrimmed.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	
	public static String trimRight(String sStringIn, String[] saStringToBeTrimmed) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(StringArrayZZZ.isEmpty(saStringToBeTrimmed)) break main;
			
			String sString = sStringIn;
			for(String sStringToBeTrimmed : saStringToBeTrimmed) {
				sString = StringZZZ.trimRight(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			}
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	public static String trimRight(String sString, String sStringToBeTrimmed) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			
			sReturn = StringZZZ.trimRight(sString, sStringToBeTrimmed, 0); //Es soll kein Zeichen (0) übrigbleiben.
			
		}//end main:
		return sReturn;
	}
	
	public static String trimRight(String sString, String sStringToBeTrimmed, int iStringLengthMinRemainingIn) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeTrimmed)) break main;
			int iStringLengthMinRemaining;
			if(iStringLengthMinRemainingIn<0){
				iStringLengthMinRemaining = 0;
			}else{
				iStringLengthMinRemaining = iStringLengthMinRemainingIn;
			}
			
			int iStringIndexMinRemaining = 1 + iStringLengthMinRemaining;			
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.endsWith(sStringToBeTrimmed) && sReturn.length()>=iStringIndexMinRemaining){
					sReturn = StringZZZ.leftback(sReturn, sStringToBeTrimmed.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils das Markierungszeichen weg, trimme wieder, ...  schneide Markierungszeichen weg, usw. bis es kein passendes Paar Markierungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimMarked(String sStringIn, String[] saMark) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			
			String sString = sStringIn;
			for(String sMark : saMark) {
				sString = StringZZZ.trimMarked(sString, sMark);
			}
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils das Markierungszeichen weg, trimme wieder, ...  schneide Markierungszeichen weg, usw. bis es kein passendes Paar Markierungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimMarked(String sString, String sMark) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
		
			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.startsWith(sMark) && sReturn.endsWith(sMark)){
					sReturn = StringZZZ.midBounds(sReturn, sMark.length(), sMark.length()); //Schneide die Markierungszeichen links und rechts weg
					sReturn = sReturn.trim();
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches/doppeltes Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches/doppeltes Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimAnyQuoteMarked(String sString) throws ExceptionZZZ {
		String sReturn = sString;		
		main:{
			if(StringZZZ.isEmpty(sString))break main;
			
			boolean bGoon = false;
			while(!bGoon) {
				String sCompare = sReturn;
				sReturn = StringZZZ.trimSingleQuoteMarked(sReturn);
				sReturn = StringZZZ.trimDoubleQuoteMarked(sReturn);
				
				if(sCompare.equals(sReturn)) bGoon=true;
			}
		}//end main:
		return sReturn;
	}

	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimSingleQuoteMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.trimMarked(sString, "'");
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein doppeltes Anfuehrungszeichen weg, trimme wieder, ...  schneide doppeltes Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimDoubleQuoteMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.trimMarked(sString, "\"");
	}
	
	//Trimme FILE-SEPARATORS
	public static String trimFileSeparators(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			sString = StringZZZ.trimFileSeparatorsLeft(sString);
			sString = StringZZZ.trimFileSeparatorsRight(sString);
			if(sString.equals(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)) {
				sString = "";
			}
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	public static String trimFileSeparatorsLeft(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{           
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeTrimmed = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sString = StringZZZ.trimLeft(sString, saStringsToBeTrimmed);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	
	public static String trimFileSeparatorsRight(String sStringIn) throws ExceptionZZZ{
		String sReturn = sStringIn;
		main:{			         
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeTrimmed = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sString = StringZZZ.trimRight(sString, saStringsToBeTrimmed);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	
	//Trimme die Zahlen von links und von rechts weg
	public static String trimNumeric(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{			
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			sString = StringZZZ.trimNumericLeft(sString);
			sString = StringZZZ.trimNumericRight(sString);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	//Trimme die Zahlen von links weg
	public static String trimNumericLeft(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			String[] saToTrim = {"1","2","3","4","5","6","7","8","9","0"};
			sReturn = StringZZZ.trimLeft(sString, saToTrim);
		}//end main:
		return sReturn;
	}
	
	//Trimme die Zahlen von rechts weg
	public static String trimNumericRight(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			String[] saToTrim = {"1","2","3","4","5","6","7","8","9","0"};
			sReturn = StringZZZ.trimRight(sString, saToTrim);
		}//end main:
		return sReturn;
	}
	
	
	/* Trimme den String, schneide links und rechts jeweils ein Anfuehrungszeichen weg, trimme wieder, ...  schneide Anfuehrungszeichen weg, usw. bis es kein passendes Paar Anfuehrungszeichen links und rechts mehr gibt.	 
	 */
	public static String trimQuotationMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.trimDoubleQuoteMarked(sString);		
	}
	
	public static String stripCharacters(String sString, char[]caToStrip) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(CharArrayZZZ.isEmpty(caToStrip)) break main;

			char[]caString = sString.toCharArray();
			char[]caReturn = new char[(caString.length)];
			int iPositionLast=-1;
			for(char c : caString) {
					if(CharArrayZZZ.contains(caToStrip,c)) {
							//nix machen
					}else {
						iPositionLast++;
						caReturn[iPositionLast]=c;
					}
			}
			sReturn = CharArrayZZZ.toString(caReturn,iPositionLast);
		}//end main:
		return sReturn;
	}
	
	public static String stripCharacters(String sStringIn, String[]saToStrip) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(StringArrayZZZ.isEmpty(saToStrip)) break main;

			String sString = sStringIn;
			for(String sToStrip : saToStrip) {
				if(sToStrip!=null) {
					char[] caToStrip = sToStrip.toCharArray();
					sString = StringZZZ.stripCharacters(sString, caToStrip);
				}			
			}
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	/* Anders als beim trimMarked werden hier Leerzeichen nicht getrimmt. 
	 */
	public static String stripMarked(String sString, String sMark) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;

			boolean bGoon = false;
			while(!bGoon){
				if(sReturn.startsWith(sMark) && sReturn.endsWith(sMark)){
					sReturn = StringZZZ.midBounds(sReturn, sMark.length(), sMark.length()); //Schneide die Markierungszeichen links und rechts weg
					
					//Falls der String nur aus "Markierungen" besteht, wurde er ggfs. null.
					//Dies hier korregieren
					if(sReturn==null) {
						sReturn = "";
					}
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches Anf�hrungszeichen weg, usw. bis es kein passendes Paar Anf�hrungszeichen links und rechts mehr gibt.	 
	 */
	public static String stripSingleQuoteMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.stripMarked(sString, "'");
	}
	
	/* Trimme den String, schneide links und rechts jeweils ein einfaches Anfuehrungszeichen weg, trimme wieder, ...  schneide einfaches Anf�hrungszeichen weg, usw. bis es kein passendes Paar Anf�hrungszeichen links und rechts mehr gibt.	 
	 */
	public static String stripDoubleQuoteMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.stripMarked(sString, "\"");
	}
	
	/* Anders als beim trimQuotationMarked werden hier Leerzeichen nicht getrimmt. 
	 */
	public static String stripQuotationMarked(String sString) throws ExceptionZZZ {
		return StringZZZ.stripDoubleQuoteMarked(sString);		
	}
	
	/** Entferne den String von links kommend, entsprechend der Anzahl der Zeichen.
	 *  Lasse mindestens 1 Zeichen übrig.
	 *  Ohne ein Zeichen übrig zu lassen StringZZZ.trimLeft(s,i,0)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sString, int i) throws ExceptionZZZ {
		return StringZZZ.trimLeft(sString,1, 1);
	}
	
	/** Entferne den String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimLeft(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sString, String sStringToBeStripped) throws ExceptionZZZ {
		return StringZZZ.stripLeft(sString, sStringToBeStripped, true);
	}
	
	/** Entferne den String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimLeft(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sString, String sStringToBeStripped, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeStripped)) break main;
			
			boolean bGoon = false;			
			while(!bGoon){
				if(StringZZZ.startsWith(sReturn, sStringToBeStripped, bExactMatch) && sReturn.length()>=2){
					sReturn = StringZZZ.rightback(sReturn, sStringToBeStripped.length());
				}else{
					bGoon = true;
				}
			}
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripLeft(String sStringIn, String[] saStringsToBeStripped) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(saStringsToBeStripped==null) break main;
				
			String sString = sStringIn;
			String sStringWithRemaining=sStringIn;
			boolean bGoon = false;
			while(!bGoon){
				bGoon = true;
				for(String sStringToBeStripped:saStringsToBeStripped){
					if(StringZZZ.startsWithIgnoreCase(sString, sStringToBeStripped)){
						bGoon = false;
						sStringWithRemaining = sString;
						sString = StringZZZ.rightback(sString, sStringToBeStripped.length());
					}
				}				
			}
			sReturn = sStringWithRemaining;//Nimm den Wert, bei dem etwas uebriggeblieben ist.
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparators(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sString)) break main;
			
			sReturn = StringZZZ.stripFileSeparatorsLeft(sString);
			sReturn = StringZZZ.stripFileSeparatorsRight(sReturn);
			if(sReturn.equals(IFileEasyConstantsZZZ.sDIRECTORY_CURRENT)) {
				sReturn = "";
			}
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparatorsLeft(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeStripped = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sString = StringZZZ.stripLeft(sString, saStringsToBeStripped);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von rechts kommend, lasse mindestens 1 Zeichen übrig.
	 *  Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String sStringToBeStripped) throws ExceptionZZZ {
		return StringZZZ.stripRight(sString, sStringToBeStripped, -1);
	}
	
	/** Entferne den String von rechts kommend.
	 *  Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String sStringToBeStripped, boolean bExactMatch) throws ExceptionZZZ {
		return StringZZZ.stripRight(sString, sStringToBeStripped, -1, bExactMatch);
	}
	
	
	/** Entferne den String von rechts kommend, lasse mindestens iNumberOfStripsIn Zeichen übrig.
	 *  Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 *   
	 *  Beachte hier den Parameter, der es erlaubt festzulege wieviele "Strips" entfernt werden sollen.
	 *   
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String sStringToBeStripped, int iNumberOfStripsIn) throws ExceptionZZZ {
		return StringZZZ.stripRight(sString, sStringToBeStripped, iNumberOfStripsIn, true);
	}
	
	/** Entferne den String von rechts kommend, lasse mindestens iNumberOfStripsIn Zeichen übrig.
	 *  Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 *   
	 *  Beachte hier den Parameter, der es erlaubt festzulege wieviele "Strips" entfernt werden sollen.
	 *   
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sString, String sStringToBeStripped, int iNumberOfStripsIn, boolean bExactMatch) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(StringZZZ.isEmpty(sStringToBeStripped)) break main;
			if(iNumberOfStripsIn==0) break main;
			
			int iNumberOfStrips = iNumberOfStripsIn;
			if(iNumberOfStrips<=-1) {
				//nimm alle
				boolean bGoon = false;
				while(!bGoon){
					//if(sReturn.endsWith(sStringToBeStripped) && sReturn.length()>=2){
					if(StringZZZ.endsWith(sReturn, sStringToBeStripped, bExactMatch) && sReturn.length()>=2){
						sReturn = StringZZZ.leftback(sReturn, sStringToBeStripped.length());
					}else{
						bGoon = true;
					}
				}			
			}else {
				//nimm die Anzahl
				int iCount=0;
				boolean bGoon = false;
				while(!bGoon){
					iCount++;
					if(StringZZZ.endsWith(sReturn, sStringToBeStripped, bExactMatch) && sReturn.length()>=2){
						sReturn = StringZZZ.leftback(sReturn, sStringToBeStripped.length());
					}else{
						bGoon = true;
					}
					if(iCount>=iNumberOfStrips) bGoon = true;
				}				
			}
		}//end main:
		return sReturn;
	}
	
	public static String stripFileSeparatorsRight(String sString) throws ExceptionZZZ {
		String sReturn = sString;
		main:{
            //nur entfernen, wenn mehr als 1 Zeichen. Ziel ist es zu verhindern, das das Kennzeichen "." als lokales Kennzeichen weggetrimmt wird.
			if(StringZZZ.isEmpty(sString)) break main;
			if(sString.length()<=IFileEasyConstantsZZZ.sDIRECTORY_CURRENT.length())break main;
			String[] saStringsToBeStripped = {IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR, IFileEasyConstantsZZZ.sDIRECTORY_CURRENT,"/"};
			sReturn = StringZZZ.stripRight(sString, saStringsToBeStripped);
		}//end main:
		return sReturn;
	}
	
	/** Entferne den String von rechts kommend
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimRight(...)
	 * @param sString
	 * @param sStringToBeStripped
	 * @return
	 */
	public static String stripRight(String sStringIn, String[] saStringsToBeStripped) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			if(saStringsToBeStripped==null) break main;
			
			String sString = sStringIn;						
			String sStringRemaining = sStringIn;
			boolean bGoon = false;
			while(!bGoon){
				bGoon = true;
				for(String sStringToBeStripped:saStringsToBeStripped){
					if(StringZZZ.endsWithIgnoreCase(sString, sStringToBeStripped)){
						bGoon = false;
						sStringRemaining = sString;
						sString = StringZZZ.leftback(sString, sStringToBeStripped.length());
					}
				}				
			}
			sReturn = sStringRemaining;
		}//end main:
		return sReturn;
	}
	
	
	//##########################################################
	/** Schneide numerische Strings links und rechts ab und lasse jeweils 1 Zeichen uebrig.
	 *  Ohne ein Zeichen uebrig links oder rechts uebrig zu lassen trimNumeric(...)
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 02.11.2025, 08:49:46
	 */
	public static String stripNumeric(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			
			String sString = sStringIn;
			sString = StringZZZ.stripNumericLeft(sString);
			sString = StringZZZ.stripNumericRight(sString);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	/** Entferne den Numerische String von links kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimNumericLeft(...)
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 02.11.2025, 08:49:46
	 */
	public static String stripNumericLeft(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			String sString = sStringIn;
			
			String[] saToStrip = {"1","2","3","4","5","6","7","8","9","0"};
			sString = StringZZZ.stripLeft(sString, saToStrip);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	
	/** Entferne den String von rechts kommend, lasse mindestens 1 Zeichen übrig.
	 *   Ohne ein Zeichen übrig zu lassen StringZZZ.trimNumericRight(...)
	 * @param sString
	 * @return
	 * @author Fritz Lindhauer, 02.11.2025, 08:49:46
	 */
	public static String stripNumericRight(String sStringIn) throws ExceptionZZZ {
		String sReturn = sStringIn;
		main:{
			if(StringZZZ.isEmpty(sStringIn)) break main;
			String sString = sStringIn;
			
			String[] saToStrip = {"1","2","3","4","5","6","7","8","9","0"};
			sString = StringZZZ.stripRight(sString, saToStrip);
			
			sReturn = sString;
		}//end main:
		return sReturn;
	}
	
	
}// END class