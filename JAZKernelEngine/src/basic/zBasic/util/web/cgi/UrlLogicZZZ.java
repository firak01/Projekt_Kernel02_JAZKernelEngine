package basic.zBasic.util.web.cgi;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.util.StringTokenizer;





import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.UrlLogicBaseZZZ;
import  basic.zBasic.util.datatype.string.StringZZZ;

/** Im wesentlichen ein Wrapper um das J2SE ULR-Objekt.
 *   Was aber auch mit unvollst�ndigen URLs arbeiten soll.
 *   
 *   Ist ein äquivalent zur Javascript Klasse ZApi.Basic.Url.js .
 *   
 *  Dabei gibt es folgende Mögliche Elemente einer URL
 * 
 http://hans:geheim@www.example.org:80/demo/example.cgi?land=de&stadt=aa#abschnitt1
|     		 | 			|    		  |              				|  |  				              |         				       |
|    		 |    		|     		 Host            			|  Pfad         				  Query           				Anker
|    		 |    		Passwort                              Port
|     		 Benutzer
Protokoll
* 
* 
 * @author lindhaueradmin
 *
 */
public class UrlLogicZZZ  extends UrlLogicBaseZZZ{	
	public static final String sURL_SEPARATOR_PATH =  "/";
	public static final String sURL_SEPARATOR_PROTOCOL = ":" + sURL_SEPARATOR_PATH + sURL_SEPARATOR_PATH;
	public static final String sURL_SEPARATOR_QUERY = "?";
	public static final String sURL_SEPARATOR_PARAM = "&";
	public static final String sURL_SEPARATOR_VALUE = "=";
	public static final String sURL_SEPARATOR_ANKER = "#";
	public static final String sURL_SEPARATOR_PORT =  ":";
	
	public UrlLogicZZZ(){
		super(); 
	}
	
	public UrlLogicZZZ(String sUrl) throws ExceptionZZZ{
		super(sUrl);	
	}
	
	public String getHost() throws ExceptionZZZ{
		String sReturn = "";
		main:{
			String sUrl = this.getUrl();
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			
			sReturn = UrlLogicZZZ.getHost(sUrl);
			
		}//End main
		return sReturn;
	}
	public static String getHost(String sUrl) throws ExceptionZZZ{
		String sReturn = "";
		main:{
			try{
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
			
				//Falls in dem String ein Protokoll vorhanden ist, geht das �ber die Standardklasse.
				//ansonsten muss etwas mehr Intelligenz reingesteckt werden.
				if(UrlLogicBaseZZZ.hasProtocol(sUrl)){
					//... Falls das Protokoll unbekannt ist ... Fehler werfen.
					URL objUrl = new URL(sUrl);
					sReturn = objUrl.getHost();
				}else{
					//Dann ist der Host der String rechts vom "/" !!!
					sReturn = StringZZZ.left(sUrl, UrlLogicZZZ.sURL_SEPARATOR_PATH);
				}
			
			}catch(MalformedURLException urle){
				ExceptionZZZ ez = new ExceptionZZZ("Malformed Url Exception: " + urle.getMessage(), iERROR_RUNTIME, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			
		}//End main		
		return sReturn;
	}
	
	/** Merke: 
				Normalerweise bedeutet ein Doppelpunkt in der URL einen Port. Aber es gibt Probleme bei spezifischen URLs
				Z.B. Git-SSH-Shortcut (scp-artige Syntax)
				
				Git akzeptiert neben echten URLs:
				git@github.com:firak01/meinrepo.git
				Hier bedeutet der Doppelpunkt tatsächlich nicht Port, sondern trennt Host und Pfad.
				Diese Schreibweise stammt historisch von scp bzw. rsync und ist keine RFC-konforme URL.

				Git erkennt intern:
				git@github.com:firak01/meinrepo.git
				als ungefähr:
				'ssh://git@github.com/firak01/meinrepo.git'
				
				Falls GitUrls verarbeitet werden sollen
				https://github.com/firak01/repo.git        o.k.
				ssh://git@github.com/firak01/repo.git	   o.k.
				git@github.com:firak01/repo.git            hier nicht o.k., Verwende statt dessen etwas, das arbeitet mit (s. JGit): URIish uri = new URIish("git@github.com:firak01/repo.git");
				
	 * @param sUrl
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String getProtocol(String sUrl) throws ExceptionZZZ{
		String sReturn = "";
		main:{
			try {
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
				
				if(! UrlLogicBaseZZZ.hasProtocol(sUrl)) break main;
				
				
				//+++++++++++				
				//... Falls es ein Protokoll gibt, dann geht das mit der normalen Stadardklasse.
				//... Voraussetzung: Die URL muss encoded sein.			
				//String sUrlNormed = UrlLogicZZZ.getUrlWithoutParameter(sUrl);
				String sUrlEncoded = UrlLogicZZZ.getUrlEncoded(sUrl);
								
				URL objUrl = new URL(sUrlEncoded);				
				sReturn = objUrl.getProtocol();
					
			} catch (MalformedURLException urle) {
				ExceptionZZZ ez = new ExceptionZZZ("MalformedUrlException: " + urle.getMessage(), iERROR_RUNTIME, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return sReturn;
	}
	
	/** Pfad ohne den Query Teil (hinter dem ? ). Also nur Protokoll, Server, etc
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* Fritz Lindhauer; 14.06.2014 12:44:38
	 */
	public  static String getUrlWithoutParameter(String sUrl) throws ExceptionZZZ {
		String sReturn = "";
		main:{				
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,   UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
				
				sReturn = StringZZZ.left(sUrl+ UrlLogicZZZ.sURL_SEPARATOR_QUERY, UrlLogicZZZ.sURL_SEPARATOR_QUERY);
				
		}//end main:
		return sReturn;
	}
	
	public static String getUrlEncoded(String sUrl) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
			try {
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,   UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
				
				String sHost = UrlLogicZZZ.getUrlWithoutParameter(sUrl);
				String sParams = StringZZZ.right(sUrl, sHost + UrlLogicZZZ.sURL_SEPARATOR_QUERY);
				if(sParams==null){
					sReturn = sHost;
				}else{
					sReturn = sHost + UrlLogicZZZ.sURL_SEPARATOR_QUERY + URLEncoder.encode(sParams, "UTF-8");
				}
					
			} catch (UnsupportedEncodingException e) {
				ExceptionZZZ ez = new ExceptionZZZ("UnsupportedEncoding Exception: " + e.getMessage(), iERROR_RUNTIME, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
		}//end main:
		return sReturn;
	}
	
	
	/** Pfad in der URL (ohne Parameter ?..... ) aber mit f�hrenden Slasch /
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 04.04.2009 13:03:05
	 */
	public String getPath() throws ExceptionZZZ{
		String sReturn = "";
		main:{
				String sUrl = this.getUrl();
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
			
				sReturn = UrlLogicZZZ.getPath(sUrl);
		
		}//End main
		return sReturn;
	}
	
	
	/** Gibt den Pfad der URL zur�ck.
	 * D.h. zumindst der Dom�nen / Hostname wird als Bestandteil erwartet, der herauszutrennen ist.
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 05.04.2009 09:27:03
	 */
	public static String  getPath(String sUrl) throws ExceptionZZZ{
		String sReturn = "";
		main:{
			try{
				if(StringZZZ.isEmpty(sUrl)){
					ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;
				}
				
				if(UrlLogicZZZ.hasProtocolValid(sUrl)){
					URL objUrl = new URL(sUrl);
					sReturn = objUrl.getPath();
				}else{
					sReturn = StringZZZ.left(sUrl+"?", "?");
					
					//1. Ggfs. vorhandene "Protokoll-Trenner" und ung�ltige Protokolle entfernen
					String sReturnTemp = StringZZZ.rightback(sReturn, UrlLogicZZZ.sURL_SEPARATOR_PROTOCOL);
					if(! StringZZZ.isEmpty(sReturnTemp)){
						sReturn = sReturnTemp;
					}
					
					//2. Ggfs. die Domäne bzw. Servername  entfernen
					sReturn = StringZZZ.rightback(sReturn, "/");
					
					//Falls der Slash aus irgendwelchen Gr�nden abhanden gekommen sein sollte
					if(StringZZZ.left(sReturn, 1)!=UrlLogicZZZ.sURL_SEPARATOR_PATH) sReturn = UrlLogicZZZ.sURL_SEPARATOR_PATH  + sReturn;
				}
				
			}catch(MalformedURLException urle){
				ExceptionZZZ ez = new ExceptionZZZ("Malformed Url Exception: " + urle.getMessage(), iERROR_RUNTIME, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
		}//END Main
		return sReturn;
	}
	
	/** Prüft, ob rechts neben einem :// String noch ein Wert steht. UND ob der String von der URL - Klasse akzeptiert wird.
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 05.04.2009 09:38:48
	 */
	public static boolean hasProtocolValid(String sUrl) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			bReturn = UrlLogicBaseZZZ.hasProtocol(sUrl);
			if(bReturn){
				//Nun testen, ob es gültig ist
				try{
					URL objUrl = new URL(sUrl);
				}catch(MalformedURLException murl){
					bReturn = false;
				}
			}
			
		}
		return bReturn;
	}
	
	
	/** Verwendet das URL-Objekt um den Query String zu holen
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 16.05.2012 15:18:26
	 */
	public static String getQuery(String sUrl) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			try {
				URL objUrl = new URL(sUrl);			
				sReturn = objUrl.getQuery();				
			}catch(MalformedURLException urle){
				ExceptionZZZ ez = new ExceptionZZZ("Malformed Url Exception: " + urle.getMessage(), iERROR_RUNTIME, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}	
			
		}//end main
		return sReturn;
	}
	
	/** Verwendet intern nicht das URL-Objekt um den Query String zu holen. Dadurch k�nnen auch PSEUDO URLs, die lediglich eine �hnliche Syntax haben GEPARSED werden.
	* @param sUrl
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 16.05.2012 15:18:26
	 */
	public static String getQueryString(String sUrl) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{		
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
				}
			
				sReturn = StringZZZ.rightback(sUrl, UrlLogicZZZ.sURL_SEPARATOR_QUERY );						
		}//end main
		return sReturn;
	}
	
	public static String getQueryValue(String sUrl, String sAlias) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{		
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			if(StringZZZ.isEmpty(sAlias)) break main;
			
			String sQuery = UrlLogicZZZ.getQueryString(sUrl);
			if(StringZZZ.isEmpty(sQuery)) break main;
				
				StringTokenizer objTokenizer = new StringTokenizer(sQuery, UrlLogicZZZ.sURL_SEPARATOR_PARAM);				
				while(objTokenizer.hasMoreTokens()){
					String sToken = objTokenizer.nextToken();
					String sLeft = StringZZZ.left(sToken, UrlLogicZZZ.sURL_SEPARATOR_VALUE);					
					if(sAlias.equalsIgnoreCase(sLeft)){
						//Gefunden...
						sReturn = StringZZZ.right(sToken, UrlLogicZZZ.sURL_SEPARATOR_VALUE);
						break;										
					}else{							
					}
				}	
				
		}//end main
		return sReturn;
	}
	
	public static boolean hasQueryValue(String sUrl, String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			if(StringZZZ.isEmpty(sAlias)) break main;
			
			String sQuery = UrlLogicZZZ.getQueryString(sUrl);
			if(StringZZZ.isEmpty(sQuery)) break main;
				
			StringTokenizer objTokenizer = new StringTokenizer(sQuery, UrlLogicZZZ.sURL_SEPARATOR_PARAM);				
			while(objTokenizer.hasMoreTokens()){
				String sToken = objTokenizer.nextToken();
				String sLeft = StringZZZ.left(sToken, UrlLogicZZZ.sURL_SEPARATOR_VALUE);					
				if(sAlias.equalsIgnoreCase(sLeft)){
						//Gefunden...
						bReturn = true;
						break main;										
					}else{							
					}
				}	
				
		}//end main
		return bReturn;
	}
	
	public static boolean hasQuery(String sUrl) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{		
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			bReturn = StringZZZ.contains(sUrl, UrlLogicZZZ.sURL_SEPARATOR_QUERY);
			
		}//end main
		return bReturn;
	}
	
	
	
	
	/** H�ngt einen Parameter an den Query String an, aber nur wenn er noch nicht als Parameter vorhanden ist.
	 *   Ist der Parameter vorhanden, wird er ersetzt.
	* @param sUrl
	* @param sAlias
	* @param sValue
	* @return
	* 
	* lindhauer; 16.05.2012 15:42:29
	 */
	public static String appendQueryValue(String sUrl, String sAlias, String sValue) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{			
			if(StringZZZ.isEmpty(sUrl)){
				ExceptionZZZ ez = new ExceptionZZZ("No Url provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("No Alias provided", iERROR_PROPERTY_MISSING,  UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}	
			
//			++++++
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Alias String", iERROR_PARAMETER_MISSING, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sAlias, "?")){
				ExceptionZZZ ez = new ExceptionZZZ("Alias String must not contain '?'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sAlias, "=")){
				ExceptionZZZ ez = new ExceptionZZZ("Alias String must not contain '='", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sAlias, "&")){
				ExceptionZZZ ez = new ExceptionZZZ("Alias String must not contain '&'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sAlias, "#")){
				ExceptionZZZ ez = new ExceptionZZZ("Alias String must not contain '#'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//+++++
			if(StringZZZ.contains(sValue, "?")){
				ExceptionZZZ ez = new ExceptionZZZ("Value String must not contain '?'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sValue, "=")){
				ExceptionZZZ ez = new ExceptionZZZ("Value String must not contain '='", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sValue, "&")){
				ExceptionZZZ ez = new ExceptionZZZ("Value String must not contain '&'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.contains(sValue, "#")){
				ExceptionZZZ ez = new ExceptionZZZ("Value String must not contain '#'", iERROR_PARAMETER_VALUE, UrlLogicZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		
			//+++++++++++++++++++++++++++++++++++
			String sQuery = UrlLogicZZZ.getQueryString(sUrl);
						
			//+++++++++++++++++++++++++++++++++++
			String sUpdate = new String("");
			boolean bHasAlias = true;
			StringTokenizer objTokenizer = new StringTokenizer(sQuery, UrlLogicZZZ.sURL_SEPARATOR_PARAM);				
			while(objTokenizer.hasMoreTokens()){
				String sToken = objTokenizer.nextToken();
				String sLeft = StringZZZ.left(sToken,UrlLogicZZZ.sURL_SEPARATOR_VALUE);				
				if(sAlias.equalsIgnoreCase(sLeft)){
					//Gefunden...
					bHasAlias = true;
					break;										
				}else{
					String sRight = StringZZZ.right(sToken, UrlLogicZZZ.sURL_SEPARATOR_VALUE);
					sUpdate = sUpdate + UrlLogicZZZ.sURL_SEPARATOR_PARAM + sLeft + UrlLogicZZZ.sURL_SEPARATOR_VALUE + sRight;
				}
			}	
						
			if(bHasAlias){
				//Ersetze
				sUpdate = sUpdate + UrlLogicZZZ.sURL_SEPARATOR_PARAM + sAlias + UrlLogicZZZ.sURL_SEPARATOR_VALUE + sValue;
				
				//Fortfahren...
				while(objTokenizer.hasMoreTokens()){
					String sToken = objTokenizer.nextToken();
					sUpdate = sUpdate + UrlLogicZZZ.sURL_SEPARATOR_PARAM + sToken;
				}
				sReturn = sUpdate;
			}else{
				//H�nge an
				if(StringZZZ.isEmpty(sQuery)){
					sReturn = sUrl + UrlLogicZZZ.sURL_SEPARATOR_QUERY + sAlias + UrlLogicZZZ.sURL_SEPARATOR_VALUE + sValue;
				}else{
					sReturn = sUrl + UrlLogicZZZ.sURL_SEPARATOR_PARAM + sAlias + UrlLogicZZZ.sURL_SEPARATOR_VALUE + sValue;
				}
			}
			
			
		}//end main
		return sReturn;		
	}
	
	/* Es hängt davon ab, ob es einen StreamHandler gibt, wenn Java ein bestimmtes Protocol nicht kennt.
	 * Mit dieser Method kann man per Reflection den passenden Stream Handler f�r ein Protokoll bekommen */
	public static URLStreamHandler getURLStreamHandler(String protocol) {
	    try {
	        Method method = URL.class.getDeclaredMethod("getURLStreamHandler", String.class);
	        method.setAccessible(true);
	        return (URLStreamHandler) method.invoke(null, protocol);        
	    } catch (Exception e) {
	        return null;
	    }
	}
	

	//### GETTER / SETTER ###############
	
}
