package basic.zBasic.util.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.map.MultiValueMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.reflection.ReflectClassZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Speichert Daten und die Metadaten zu den Daten.
 *  Aufgrunde des Wissens um den Datentyp des Objects kann der ggf. notwendige Typecast gemacht werden, wenn man die Daten wieder ausliest.
 *  
 * a) Hashmap in Hashmap, zum Speichern der Metadaten (Dies wird durch - HashMapMultiZZZ - realisiert)
 * 
 *  	liste("alias1")=hashmap1("Fieldname") = "Titel"
 *                                     1("Datatype")  = "String"
 *                                     1("Formula")    = "@Trim(#V#)
 *                                     
 *      liste("alias2")=hashmap2("Fieldname") = "Autor"
 *                         ........................
 *                         
 *                         
 *  b) Benutzt eine MultiValueMap (aus jakarta commons collections 3.2) zum speichern von Mehrfachwerten                       
 *      (dies wird durch MultiHashMap
 *                
 *      liste("alias1") = "mein erstes Buch"
 *      liste("alias1") = "das zweite Buch"
 *      
 *      liste("alias2") = "Anton A. Autor"
 *      
 *############################################################################################     
 *Anwendungsfall:      
* Aufgabe dieser Klasse ist der Datenaustausch zwischen dem eingentlichen Servlet und der DocumentCreatorZZZ Klasse.
 * Die Inputwerte werden aus dem HttpRequest-Objekt geholt und an diese Objekt (versehen mit einem Alias) �bergeben.
 * Dann kann das DocumentCreator-Objekt per Alias auf diese Werte zugreifen.
 * 
 * Die HashMaps, die diese Werte enthalten werden dann z.B. der DocumentCreatorZZZ-Klasse �bergeben und beim createDocument(...) ausgelesen.
 * Der Alias ist dabei der Feldname. Die verschiedenen Hashmaps beinhalten dabei verschiedene Datentypen.
 * 
 * Hintergrund:
 * Dadurch wird das DocumentCreator-Objekt testbar, auch wenn wir kein Http-Request-Objekt im Test haben.
 * 
 *                         
 * @author lindhaueradmin
 *
 */
public class DataStoreZZZ implements IConstantZZZ{
	private HashMapMultiZZZ objHmMeta = new HashMapMultiZZZ();
	private MultiValueMap objHmData = new MultiValueMap();
	private String sStoreAlias = null;
	
	public DataStoreZZZ(String sStoreAlias) throws ExceptionZZZ{
		if(StringZZZ.isEmpty(sStoreAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("StoreAlias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.sStoreAlias = sStoreAlias;
	}
	
	public void setField(DataFieldZZZ objFieldZ) throws ExceptionZZZ{
		try{
			if(objFieldZ==null){
				ExceptionZZZ ez = new ExceptionZZZ("Field-Objekt", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Class objClassCurrent = objFieldZ.getClass();
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Untersuche per ReflectionApi die Klasse '" + objClassCurrent.getName() +"'" );
			Field[] fieldaz = null;
			if(System.getSecurityManager()!= null){
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#SecurityManager vorhanden... beachte Privilegien..." ); //Seit Java 1.6 auf Servern notwendig
				ReflectClassZZZ rf = new ReflectClassZZZ();
				fieldaz = rf.selectFieldsPrivileged(objClassCurrent, 0, Modifier.FINAL);
			}else{
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#Kein SecurityManager vorhanden... arbeite ohne spezielle Privilegien..." ); //Seit Java 1.6 auf Servern notwendig
				fieldaz = ReflectClassZZZ.selectFields(objClassCurrent, 0, Modifier.FINAL);  //Es sollen alle Felder betrachtet werden (�ber alle Vererbungen hinweg), mit ausnahme der Finalen Felder.
			}
			
			if(fieldaz.length==0){
				ExceptionZZZ ez = new ExceptionZZZ("Field-Objekt has no declared Fields", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//### Felder durchsehen		
			//1. Den Alias auslesen
			String sAlias=null;
			for(int icount=0; icount <= fieldaz.length-1; icount++){
				Field objField = fieldaz[icount];
				if(objField.getName().equals("Alias")){
						sAlias = objFieldZ.Alias;          //(String)objField.get(objField.Alias);	
						break;
				}				
				//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#" + objField.getName());
			}
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Field-Objekt has no declared Field named 'Alias'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//2. Nun alle weiteren Felder bzgl. des Namens und des Wertes auslesen. Dieses der MetadataHashMap und der WerteHashMap zuweisen
			String sMetadata = null; String sName = null;
			for(int icount = 0; icount <= fieldaz.length-1; icount++){
				Field objField = fieldaz[icount];
				sName = objField.getName();
				if(!sName.equals("Alias") && !sName.endsWith("$0")){  //Merke: $0 gibt Probleme. Muss was mit Reflection und PRoxies zu tun haben, dass dies �berhaupt entsteht.
					//System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#" + icount + "|'" + sAlias + "'----'" + sName);
					sMetadata = (String)objField.get(objFieldZ);
					//System.out.print( "'----'" + sMetadata + "'");
					if(sMetadata!=null){
						//3. in der Metadata-hashmap diesen Wert setzen
						this.setMetadata(sAlias, sName, sMetadata);
					}							
				}
			}//end for
			
		} catch (IllegalArgumentException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		} catch (IllegalAccessException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}
	
	
	/** Meke: Metadata ist das, was in DataFieldZZZ als Property festgelegt worden ist.
	 *             Diese Funktion wird als von .setField(DataFieldZZZ) aufgerufen, f�r jede Property.
	 *                
	* @param sAlias
	* @param sName
	* @param sValue
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 27.11.2006 08:55:05
	 */
	public void setMetadata(String sAlias, String sName, String sValue) throws ExceptionZZZ{
		if(StringZZZ.isEmpty(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Alias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(StringZZZ.isEmpty(sName)){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		if(sValue==null){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Value", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//1. Unter dem Alias eine Hashmap suchen oder anlegen
		HashMap hm;
		if(this.objHmMeta.containsKey(sAlias)){
			hm = (HashMap) this.objHmMeta.get(sAlias);
			hm.put(sName, sValue);
		}else{
			hm = new HashMap();
			hm.put(sName, sValue);
			this.objHmMeta.put(sAlias, hm);
		}
	}
	
	
	public String getMetadata(String sAlias, String sName) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Alias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(StringZZZ.isEmpty(sName)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
//			1. Unter dem Alias eine Hashmap suchen
			if(!this.objHmMeta.containsKey(sAlias)) break main;
			
			//2. in der inneren Hashmap den Namen des Metadata-Parameters suchen
			HashMap hm = (HashMap) this.objHmMeta.get(sAlias);
			if(!hm.containsKey(sName)) break main;
			
			//3. Wert zur�ckgeben
			sReturn = (String) hm.get(sName);
			
		}//END main
		return sReturn;
	}
	
	/** Merke: Beim Datentyp Date muss das Objekt als Date() vorliegen.
	 *  
	* @param sAlias
	* @param objValue, merke: bei Null passiert nix
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 28.11.2006 09:06:05
	 */
	public void appendValue(String sAlias, Object objValue) throws ExceptionZZZ{
		main:{					
		if(StringZZZ.isEmpty(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Alias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//1. Ermitteln, ob der Alias als Metadata definiert ist
		if(!this.objHmMeta.containsKey(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(objValue==null)	break main;
					
//		Metadata analysieren
		String sDatatype = this.getMetadata(sAlias, "Datatype");
		
		//2. Das passende Objekt wegspeichern
		if(sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT) | sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
			//Der Datentyp entspricht derm angegebenen
			String objString = (String)objValue;
			this.objHmData.put(sAlias, objString);
		}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){			
				Integer objInteger = (Integer) objValue;
				this.objHmData.put(sAlias, objInteger);
		}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
				Long objLong = (Long) objValue;
				this.objHmData.put(sAlias, objLong);		
		}else if(sDatatype.equals(DataFieldZZZ.sDOUBLE)){			
				Double objDouble = (Double) objValue;
				this.objHmData.put(sAlias, objDouble);					
		}else if(sDatatype.equals(DataFieldZZZ.sDATE)){
				Date objDate = (Date) objValue;
				this.objHmData.put(sAlias, objDate);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "' not yet developped.", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		}//END main:
	}
	
	
	
	
	public void appendValue(String sAlias, String sValue) throws ExceptionZZZ{
		main:{					
		if(StringZZZ.isEmpty(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Alias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		//1. Ermitteln, ob der Alias als Metadata definiert ist
		if(!this.objHmMeta.containsKey(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(sValue==null){
			this.objHmData.remove(sAlias);
			break main;
		}
			
		
//		Metadata analysieren
		String sDatatype = this.getMetadata(sAlias, "Datatype");
		
		//2. Das passende Objekt wegspeichern
		if(sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT) | sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
			//Der Datentyp entspricht derm angegebenen
			this.objHmData.put(sAlias, sValue);
		}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){
			//!!! Den Leerstring abfangen. Er gilt als 0.
			if(sValue.equals("")){
				Integer objInteger = new Integer(0);
				this.objHmData.put(sAlias, objInteger);
			}else{			
				Integer objInteger = new Integer(sValue);
				this.objHmData.put(sAlias, objInteger);
			}
		}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
			if(sValue.equals("")){
				Long objLong = new Long(0);
				this.objHmData.put(sAlias, objLong);
			}else{
				Long objLong = new Long(sValue);
				this.objHmData.put(sAlias, objLong);
			}			
		}else if(sDatatype.equals(DataFieldZZZ.sDOUBLE)){
			if(sValue.equals("")){				
				Double objDouble = new Double(0);
				this.objHmData.put(sAlias, objDouble);
			}else{				
				Double objDouble = new Double(sValue);
				this.objHmData.put(sAlias, objDouble);
			}						
		}else if(sDatatype.equals(DataFieldZZZ.sDATE)){
				//Unter der Annahme, es sei ein String, wird der String nun nach einem vorzugebenden Datumsformat geparsed.
				if(sValue.equals("")){
					//Leerstring abfangen. 1.1.1900 dort reinsetzen !!!
					String sFormat = "dd.MM.yyyy";
					SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
					try{
						Date objDate = formatter.parse("01.01.1900");
						this.objHmData.put(sAlias, objDate);
					}catch(ParseException pe){
						ExceptionZZZ ez = new ExceptionZZZ("Error processing datatype '" + sDatatype + "'. Unable to create date objct from string '"+ sValue+"', by using format:'" + sFormat + "'" , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					//Vermeintlichen Datumsstring parsen. Das Format kann konfiguriert werden.
					String sFormat = this.getMetadata(sAlias, DataFieldZZZ.FORMAT);
					if(StringZZZ.isEmpty(sFormat)){
						ExceptionZZZ ez = new ExceptionZZZ("Error processing date. No date-object provided and unable to retrieve a format string for the field (alias: '" + sAlias + "').", iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
					try{
						Date objDate = formatter.parse(sValue);
						this.objHmData.put(sAlias, objDate);
					}catch(ParseException pe){
						ExceptionZZZ ez = new ExceptionZZZ("Error processing datatype '" + sDatatype + "'. Unable to create date object from string '"+ sValue+"', by using format:'" + sFormat + "'" , iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}				
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "' not yet developped.", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		}//END main:
	}
	
	public void appendValue(String sAlias, int iValue) throws ExceptionZZZ{
		//1. Ermitteln, ob der Alias als Metadata definiert ist
		if(!this.objHmMeta.containsKey(sAlias)){
			ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		

//		Metadata analysieren
		String sDatatype = this.getMetadata(sAlias, DataFieldZZZ.DATATYPE);
		
		//2. Das passende Objekt wegspeichern
		if(sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT)){
			//Der Datentyp entspricht derm angegebenen
			Integer intTemp = new Integer(iValue);			
			String objString = intTemp.toString();
			this.objHmData.put(sAlias, objString);
		}else if(sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
			ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "'. This datatype should not be converted to an integer value.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){
			Integer objInteger = new Integer(iValue);
			this.objHmData.put(sAlias, objInteger);
		}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
			Long objLong = new Long(iValue);
			this.objHmData.put(sAlias, objLong);
		}else if (sDatatype.equals(DataFieldZZZ.sDOUBLE)){
			Double objDouble = new Double(iValue);
			this.objHmData.put(sAlias, objDouble);
		}else if(sDatatype.equals((DataFieldZZZ.sDATE))){
			Long objLong = new Long(iValue);
			Date objDate = new Date(objLong.longValue());
			this.objHmData.put(sAlias, objDate);
		}else{
			ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "' not yet developped.", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}		
	}
		
		/** Merke: Long Werte vom Typ Date werden hier in das Date(long in millisekunden) - Objekt umgewandelt 
		* @param sAlias
		* @param lValue
		* @throws ExceptionZZZ
		* 
		* lindhaueradmin; 28.11.2006 09:08:33
		 */
		public void appendValue(String sAlias, long lValue) throws ExceptionZZZ{
			//1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			

//			Metadata analysieren
			String sDatatype = this.getMetadata(sAlias, DataFieldZZZ.DATATYPE);
			
			//2. Das passende Objekt wegspeichern
			if(sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT)){
				//Der Datentyp entspricht derm angegebenen
				Long lngTemp = new Long(lValue);			
				String objString = lngTemp.toString();
				this.objHmData.put(sAlias, objString);
			}else if(sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
				ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "'. This datatype should not be converted to a long value.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){
				Long objLong = new Long(lValue);
				int itemp = objLong.intValue();
				Integer objInteger = new Integer(itemp);                      //!!!!!!!!!!!                      
				this.objHmData.put(sAlias, objInteger);
			}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
				Long objLong = new Long(lValue);
				this.objHmData.put(sAlias, objLong);
			}else if (sDatatype.equals(DataFieldZZZ.sDOUBLE)){
				Double objDouble = new Double(lValue);
				this.objHmData.put(sAlias, objDouble);
			}else if(sDatatype.equals(DataFieldZZZ.sDATE)){
				Date objDate = new Date(lValue);
				this.objHmData.put(sAlias, objDate);
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "' not yet developped.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}		
		}
		
		
		/** Merke: Double Werte vom Typ Date werden hier in das Date(long in millisekunden) - Objekt umgewandelt 
		* @param sAlias
		* @param lValue
		* @throws ExceptionZZZ
		* 
		* lindhaueradmin; 28.11.2006 09:08:33
		 */
		public void appendValue(String sAlias, double dValue) throws ExceptionZZZ{
			//1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			

//			Metadata analysieren
			String sDatatype = this.getMetadata(sAlias, DataFieldZZZ.DATATYPE);
			
			//2. Das passende Objekt wegspeichern
			if(sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT)){
				//Der Datentyp entspricht derm angegebenen
				Double objDouble = new Double(dValue);		
				String objString = objDouble.toString();
				this.objHmData.put(sAlias, objString);
			}else if(sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
				ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "'. This datatype should not be converted to a double value.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){
				Double objDouble = new Double(dValue);		
				int itemp = objDouble.intValue();
				Integer objInteger = new Integer(itemp);                      //!!!!!!!!!!!                      
				this.objHmData.put(sAlias, objInteger);
			}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
				Double objDouble = new Double(dValue);
				long ltemp = objDouble.longValue();
				Long objLong = new Long(ltemp);
				this.objHmData.put(sAlias, objLong);
			}else if (sDatatype.equals(DataFieldZZZ.sDOUBLE)){
				Double objDouble = new Double(dValue);
				this.objHmData.put(sAlias, objDouble);
			}else if(sDatatype.equals(DataFieldZZZ.sDATE)){
				Double objDouble = new Double(dValue);
				long ltemp = objDouble.longValue();
				Date objDate = new Date(ltemp);
				this.objHmData.put(sAlias, objDate);
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("Processing datatype '" + sDatatype + "' not yet developped.", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}		
		}
		
		public void replaceValue(String sAlias, double dValue) throws ExceptionZZZ{
			//1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.objHmData.remove(sAlias);
			this.appendValue(sAlias, dValue);
		}
		
		public void replaceValue(String sAlias, int iValue) throws ExceptionZZZ{
//			1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.objHmData.remove(sAlias);
			this.appendValue(sAlias, iValue);
		}
		
		public void replaceValue(String sAlias, Object objValue) throws ExceptionZZZ{
//			1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.objHmData.remove(sAlias);
			this.appendValue(sAlias, objValue);
		}
		
		public void replaceValue(String sAlias, long lValue) throws ExceptionZZZ{
//			1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.objHmData.remove(sAlias);
			this.appendValue(sAlias, lValue);
		}
		
		public void replaceValue(String sAlias, String sValue) throws ExceptionZZZ{
//			1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.objHmData.remove(sAlias);
			this.appendValue(sAlias, sValue);
		}
		
		
	
	/** Gibt den Wert des Objekts als String zur�ck.
	 * 
	 * Merke: Beim Datumswert kann es sein, dass dieser entsprechend des SimpleDateFormats ver�ndert wird (ggf. mit 0 aufgef�llt).
	 *            z.B. 6.12.2006 wird dann zu 06.12.2006 beim Format dd.MM.yyyy
	 *            
	* @param sAlias
	* @param iIndex
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 06.12.2006 10:15:28
	 */
	public String getValueString(String sAlias, int iIndex) throws ExceptionZZZ{
		String objReturn=null;
		main:{
			if(!this.objHmData.containsKey(sAlias)) break main;
			
			//Metadata analysieren
			String sDatatype = this.getMetadata(sAlias, DataFieldZZZ.DATATYPE);
			
			// Collection aus dem MultiValue-Map holen
			Collection colVal = (Collection) this.objHmData.get(sAlias);
			Iterator colIt = colVal.iterator();
			
			int iPos = -1;
			while(colIt.hasNext()){
				iPos++;
				if(iPos==iIndex){
					Object obj = colIt.next();
					objReturn = this.getValueString(sAlias, sDatatype, obj);
					break main;
				}else{
					//Iterator der Collection nat�rlich um 1 weiter verschieben
					colIt.next();
					
				}//End if iPos = iIndex
			}//END while
		}//END main:
		return objReturn;
	}
	
	
	/** Falls der Datatype definiert ist, wird das Objekt entsprechend der Metadaten analysiert und in den String umgewandelt.
	 *   Diese Methode wird aufgerufen von getValueString / getValueVectorString
	* @param sDatatype
	* @param obj
	* @return String
	* 
	* lindhaueradmin; 06.12.2006 09:56:05
	 * @throws ExceptionZZZ 
	 */
	private String getValueString(String sAlias, String sDatatype, Object obj) throws ExceptionZZZ{
		String objReturn = null;
		main:{
			if(StringZZZ.isEmpty(sDatatype)){
				ExceptionZZZ ez = new ExceptionZZZ("Datatype-String", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(obj==null) break main;
			
			//+++++++++++++++++++++++++++++
			if(sDatatype.equals(DataFieldZZZ.sNOTESRICHTEXT) | sDatatype.equals(DataFieldZZZ.sSTRING)|sDatatype.equals(DataFieldZZZ.sNOTESAUTHOR) | sDatatype.equals(DataFieldZZZ.sNOTESNAME) | sDatatype.equals(DataFieldZZZ.sNOTESREADER)){
				//Der Datentyp entspricht derm angegebenen
				objReturn = (String)obj;
			}else if(sDatatype.equals(DataFieldZZZ.sINTEGER)){
				//Der Datentyp muss dem gew�nschten angepasst werden.
				Integer objInt = (Integer)obj;
				objReturn = objInt.toString();
			}else if(sDatatype.equals(DataFieldZZZ.sDOUBLE)){
//				Der Datentyp muss dem gew�nschten angepasst werden.
				Double objDouble = (Double)obj;
				objReturn = objDouble.toString();
			}else if(sDatatype.equals(DataFieldZZZ.sLONG)){
//				Der Datentyp muss dem gew�nschten angepasst werden.
				Long objLong = (Long) obj;
				objReturn = objLong.toString();
			}else if(sDatatype.equals(DataFieldZZZ.sDATE)){
//				Der Datentyp muss dem gewuenschten angepasst werden.
				Date objDate = (Date) obj;		
				
//				!!! aus irgendeinem Grund muss wohl ein Leerwert als 1.1.1900 zur�ckgegeben werden. Darauf pr�fen
				Calendar cal = Calendar.getInstance();
				cal.setTime(objDate);
				int iYear = cal.get(Calendar.YEAR);
				if(iYear == 1900){ 
					objReturn = "";
				}else{
					String sFormat = this.getMetadata(sAlias, "Format");
					if(StringZZZ.isEmpty(sFormat)){
						objReturn=objDate.toString();
					}else{
						SimpleDateFormat objFormat = new SimpleDateFormat(sFormat);
						objReturn = objFormat.format(objDate);
					}
				}				
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("This datatype is not developed yet: '"+sDatatype+"'", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}					
		}//END main
		return objReturn;
	}
	
	/**R�ckgabe aller Werte f�r den Alias, als Vektor. Siehe auch .getValueString(...)
	 * 
	 * Hintergrund:
	 * Will man mit der NotesItem-Klasse arbeiten und Mehrfachwerte hinzuf�gen, so braucht man einen Vector.
	 * 
	* @param sAlias
	* @return vector, mit String Elementen
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 22.11.2006 10:18:29
	 */
	public Vector getValueVectorString(String sAlias) throws ExceptionZZZ{
		Vector objReturn = new Vector();
		main:{
			if(!this.objHmData.containsKey(sAlias)) break main;
			
			//Metadata analysieren
			String sDatatype = this.getMetadata(sAlias, "Datatype");
			
			// Collection aus dem MultiValue-Map holen
			Collection colVal = (Collection) this.objHmData.get(sAlias);
			//objReturn.addAll(colVal) Das geht nicht, da ggf. erst eine Datentypumwandlung vorgenommen werden muss
			
			Iterator colIt = colVal.iterator();
			while(colIt.hasNext()){
				Object obj = colIt.next();
				String objString = this.getValueString(sAlias, sDatatype, obj);
				objReturn.add(objString);
			}//END while
		}//END main:
		return objReturn;
	}
	
	/** R�ckgabe aller Werte f�r den Alias als Vektor. ABER: Der Datentyp ist noch nicht festgelegt.
	 *  Es wird der aufrufenden Funktion �berlassen den Datentyp aus den Metadaten zu ermitteln. 
	 *  z.B. durch 
	 *                   		
			//Metadata analysieren
			String sDatatype = objDataStore.getMetadata(sAlias, "Datatype");
	
	 * 
	* @param sAlias
	* @return Vector
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 23.11.2006 09:21:41
	 */
	public Vector getValueVector(String sAlias) throws ExceptionZZZ{
		Vector objReturn = new Vector();
		main:{
	if(!this.objHmData.containsKey(sAlias)) break main;
			
			// Collection aus dem MultiValue-Map holen
			Collection colVal = (Collection) this.objHmData.get(sAlias);
			//objReturn.addAll(colVal) Das geht nicht, da ggf. erst eine Datentypumwandlung vorgenommen werden muss
			
			Iterator colIt = colVal.iterator();
			while(colIt.hasNext()){				
				objReturn.add(colIt.next());					
			}//END while
		}//END main:
		return objReturn;
	}
	
	
	/**Setze die Werte eines Vektors in die MultiHashMap mit dem Alias.
	 * 
	 * Merke: Es wird davon ausgegangen, dass alle Werte den gleichen Datentyp haben
	 * 
	 * @param sAlias
	 * @param objVector
	 * @throws ExceptionZZZ, 
	 *
	 * @return void
	 *
	 * javadoc created by: 0823, 22.11.2006 - 11:31:33
	 */
	public void appendValueVector(String sAlias, Vector objVector) throws ExceptionZZZ{
		main:{
			if(StringZZZ.isEmpty(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Alias", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objVector==null) break main;
			
			//1. Ermitteln, ob der Alias als Metadata definiert ist
			if(!this.objHmMeta.containsKey(sAlias)){
				ExceptionZZZ ez = new ExceptionZZZ("Metadata-Name does not exist: '" + sAlias + "'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Nun allgemeines Objekt aus dem Vektor holen
			for(int icount=0; icount <= objVector.size()-1; icount++){
				Object objValue = objVector.get(icount);			
				this.appendValue(sAlias, objValue);			
			}//END for
		}//END main
	}
	
	/**List alle Aliasse aus, f�r die in der Werte-HashMap ein Wert gespeichert ist.
	 * s. getAliasStringAll()
	* @return ArrayList
	* 
	* lindhaueradmin; 23.11.2006 09:00:05
	 */
	public ArrayList getValueKeyStringAlll(){
		ArrayList alsReturn = new ArrayList();
		main:{
			Set objSet = this.objHmData.keySet();
			if (objSet.isEmpty()) break main;
			
			Iterator itSet = objSet.iterator();
			while(itSet.hasNext()){
				String stemp = (String) itSet.next();
				alsReturn.add(stemp);
			}
		
		}//END main:
		return alsReturn;
	}
	
	/**List alle Aliasse aus, f�r die in der Werte-HashMap ein Wert gespeichert ist.
	 * s. getValueKeyStringAll
	* @return ArrayList
	* 
	* lindhaueradmin; 23.11.2006 09:00:05
	 */
	public ArrayList getAliasStringWithValue(){
		return this.getValueKeyStringAlll();
	}
	
	
	/**List alle Aliasse aus, f�r die in der Metadata-HashMap ein Wert gespeichert ist.
	 * s. getValueKeyStringAll
	* @return ArrayList
	* 
	* lindhaueradmin; 23.11.2006 09:00:05
	 */
	public ArrayList getFieldNameMappedAll(){
		ArrayList alsReturn = new ArrayList();
		main:{
			Set objSet = this.objHmMeta.keySet();
			if (objSet.isEmpty()) break main;
			
			Iterator itSet = objSet.iterator();
			while(itSet.hasNext()){
				String stemp = (String) itSet.next();
				alsReturn.add(stemp);
			}
		
		}//END main:
		return alsReturn;
	}
	
	public boolean isFieldAliasMapped(String sAlias) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sAlias)) break main;
			
			Set objSet = this.objHmMeta.keySet();
			if (objSet.isEmpty()) break main;
			
			Iterator itSet = objSet.iterator();
			while(itSet.hasNext()){
				String stemp = (String) itSet.next();
				if(stemp.equals(sAlias)) {
					bReturn = true;
					break main;
				}
			}						
		}
		return bReturn;
	}
	
	
	/**List alle Aliasse aus, f�r die f�r die es ein Mapping der HTTP-Parameter gibt.
	 * s. getValueKeyStringAll
	* @return ArrayList
	* 
	* lindhaueradmin; 23.11.2006 09:00:05
	 */
	public ArrayList getAliasStringMappedAll(){
		ArrayList alsReturn = new ArrayList();
		main:{
			Set objSet = this.objHmData.entrySet();
			if (objSet.isEmpty()) break main;
			
			Iterator itSet = objSet.iterator();
			while(itSet.hasNext()){
				String stemp = (String) itSet.next();
				alsReturn.add(stemp);
			}
		
		}//END main:
		return alsReturn;
	}
	
	
	
	public int sizeMetadata(){
		return this.objHmMeta.size();
	}
	
	public int sizeValuedata(){
	 return this.objHmData.size();
	}
	
	
	public void clear(){
		this.objHmData.clear();
		this.objHmMeta.clear();
	}
	
	
	public void invokePostStoreRead(String sAliasin) throws ExceptionZZZ{
		String sAlias = null;
		if(StringZZZ.isEmpty(sAliasin)){
			sAlias = "$ALL";
		}else{
			sAlias = sAliasin;
		}
		
		//+++ Nun basierend auf einer Konfigurierten Klasse / Methode den gerade eingef�gten Wert (das Item) noch nachbearbeiten
		String sMethodname = this.getMetadata(sAlias, "CustomMethodPostStoreRead");
		if(! StringZZZ.isEmpty(sMethodname)){
			String sClassname = this.getMetadata(sAlias, "CustomClassPostStoreRead");
			if(StringZZZ.isEmpty(sClassname)){
				ExceptionZZZ ez = new ExceptionZZZ("Classname was not provided, which contains the method '" + sMethodname + "', for the field with the alias: '" + sAlias + "'", iERROR_CONFIGURATION_MISSING, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else{
				Class clX = null;
				try{
					clX = Class.forName(sClassname);
				}catch(ClassNotFoundException ce){
					ExceptionZZZ ez = new ExceptionZZZ("Class was not found '" + sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				Method meX = null;
				try{
					Class[] argTypes={};
					meX = clX.getMethod(sMethodname, argTypes);
				}catch(NoSuchMethodException me){
					ExceptionZZZ ez = new ExceptionZZZ("Method was not found '" + sMethodname + "' which should be found in the class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				Object objX;
				try {
					objX = clX.newInstance();
				} catch (InstantiationException e) {
					ExceptionZZZ ez = new ExceptionZZZ("InstanciationException on class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (IllegalAccessException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException on class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				try {
					meX.invoke(objX, null);
				} catch (IllegalArgumentException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException on invoking method: '" + sMethodname + "' of class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (IllegalAccessException e) {
					ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException on invoking method: '" + sMethodname + "' of class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				} catch (InvocationTargetException e) {
					ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException on invoking method: '" + sMethodname + "' of class: '" +  sClassname + "', for the field with the alias: '" + sAlias + "'", iERROR_RUNTIME, "DocumentCreatorZZZ", ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
			}
		}
	}
	
	public String getStoreAlias(){
		return this.sStoreAlias;
	}
	
}//END class
