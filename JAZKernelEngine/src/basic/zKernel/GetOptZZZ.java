package basic.zKernel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.start.GetOpt;

/**Ein Wrapper um GetOpt.  
   | bedeutet - Parameter ohne Wert (Wert=Parametername)
   : bedeutet - Parameter muss einen Wert haben
   . bedeutet - Paramater kann einen Wert haben. Leer Parameter mit Leerstring definiert ""
 * @author lindhauer
 *
 */
public class GetOptZZZ extends AbstractObjectWithFlagZZZ{
	protected volatile String sPattern; 
	protected HashMap hmOpt = new HashMap();//Die Hashmap der von aussen gesetzten Steuerungsoptionen. Merke: Dann gibt es noch die HashMap der FlagZ in ObjectZZZ
	protected boolean bFlagIsLoaded = false;
	
	private Iterator itOpt = null;
	
		
	public GetOptZZZ(){
	}
	
	/** Konstruktor, mit Pattern-String.	       
	 * @param saArg, Array der Argumente. Hier werden die Steuerzeichen mit Bindestrich eingeleitet. z.B. -k wert1 -s wert2. Es muessen nicht alle im Pattern definierten Steuerzeichen vorhanden sein.
	 * @throws ExceptionZZZ
	 */
	public GetOptZZZ(String sPattern) throws ExceptionZZZ{
		this.setPattern(sPattern);		
	}
	
	/** *  Merke: Konstruktor nur mit Argument-String ist nicht sinnvoll,
	 *         da ohne Pattern String diese nicht ausgewertet werden können.
	 */         
//	public GetOptZZZ(String[] saArg) throws ExceptionZZZ{		
//		this.loadOptionAll(saArg);
//	}
	
	/** Konstruktor, der sofort alles initiiert
	* lindhauer; 18.07.2007 06:47:43
	 * @param objKernel, kann ein mit new KernelZZZ() initiierter default - Kernel sein (vorausgesetzt die entsprechende Default ini-Datei existiert f�r den default kernel)
	 * @param sPattern, die Steuerzeichen. Ein Buchstabe, ein Doppelpunkt bedeutet, dass ein Argument folgt. z.B. "k:s:abc". Die Argumente tauchen dann im Argument Array auf
	 * @param saArg, Array der Argumente. Hier werden die Steuerzeichen mit Bindestrich eingeleitet. z.B. -k wert1 -s wert2. Es muessen nicht alle im Pattern definierten Steuerzeichen vorhanden sein.
	 * @throws ExceptionZZZ
	 */
	public GetOptZZZ(String sPattern, String[] saArg) throws ExceptionZZZ{
		this.setPattern(sPattern);
		this.loadOptionAll(saArg);
	}
	
	public String getPattern(){
		return this.sPattern;
	}
	/**Setzt die Kommandozeile in dem Format, wie es auch von GetOpt erwartet wird.
	 *  z.B. -a: erster_wert -b -c: zweiter_wert
	            Dabei ist -b ein Parameter ohne Wert (er ist einfach nur da), -a und -c haben einen Wert zugewiesen bekommen.
	            Die Parametername duerfen jetzt (20260316) auch eine Laenge von >=2 haben.
	             
	* @param sPattern
	* 
	* lindhauer; 20.06.2007 06:19:59
	 * @throws ExceptionZZZ 
	 */
	public void setPattern(String sPattern) throws ExceptionZZZ{
		if(this.sPattern!=null){
			if(! this.sPattern.equals(sPattern)){ //NUr falls ein anderer neuer Wert
				if(this.isPatternStringValid(sPattern)){
					this.sPattern = sPattern;	
					this.clearOptions(); //Bisherige HAshmap loeschen und Iterator wieder zuruecksetzen
				}else{
					String sResult = this.proofPatternString(sPattern);
					ExceptionZZZ ez = new ExceptionZZZ("Not a valid pattern string." + sResult, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		
				}
			}else{
			  //Nix, das gleicher Wert
			}
		}else{
			//Neuen Wert setzen
			if(this.isPatternStringValid(sPattern)){
				this.sPattern = sPattern;								
			}else{
				ExceptionZZZ ez = new ExceptionZZZ("Not a valid pattern string: " + sPattern, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;		
			}
		}
	}

	/**Die Interne HashMap zuruecksetzen
	* lindhauer; 28.06.2007 06:59:29
	 */
	public void clearOptions(){		
		this.getOptionMap().clear(); //Die Hashmap zur�cksetzen
		this.setOptionIterator_(null);
		this.bFlagIsLoaded = false;	
	}	
	
	/** Hier sollen alle Steueranweisungen sein, die gemaess GetOpt.sCOMMAND_PREFX ein Kommando sind
	 * 
	 */
	public static ArrayList<String> getList4ControlAny(String[] saArgument) throws ExceptionZZZ{
		ArrayList<String> listasReturn = null;
		main:{
			if(ArrayUtilZZZ.isNull(saArgument))break main;
			listasReturn = new ArrayList<String>();
			
			//Gehe das Array durch. 
			//Jeder String der mit einem Kommando-Prefix versehen ist, wird in die Returnliste uebernommen.
			for(String sArgument : saArgument) {
				if(StringZZZ.startsWith(sArgument, GetOpt.sCOMMAND_PREFIX)) {
					listasReturn.add(StringZZZ.stripLeft(sArgument, 1));
				}
			}
			
		}//end main:
		return listasReturn;
	}
	
	
	/** Hier sollen alle Steueranweisungen sein, die im Pattern enthalten sind, also auch die ohne Argumente
	 *  Problem: Seit der Einfuehrung von Steueranweisungen mit mehr als 1 Zeichen (z.B. flagz ) 
	 *           funktioniert das so nicht mehr. 
	 *  Lösungsidee: Steueranweisungen ohne Argumente müssen am Schluss sein, d.h. ohne abschliessenden Doppelpunkt.
	 * 
	 * @param sPattern
	 * @return
	 * @author Fritz Lindhauer, 31.03.2021, 09:09:29
	 * @throws ExceptionZZZ 
	 */
	public static ArrayList<String> getPatternList4ControlAll(String sPattern) throws ExceptionZZZ{
		ArrayList<String> listaControlWithValue = getPatternList4ControlWithValue(sPattern);
		ArrayList<String> listaControlWithOptionalValue = getPatternList4ControlWithOptionalValue(sPattern);
		ArrayList<String> listaControlSimple = getPatternList4ControlSimple(sPattern);
		
		return (ArrayList<String>) ArrayListUtilZZZ.join(listaControlWithValue, listaControlWithOptionalValue, listaControlSimple);
	}
	
	public static ArrayList<String> getPatternList4ControlWithValue(String sPattern) throws ExceptionZZZ{
		return getPatternList4ControlWithValue_(sPattern);
	}
	
	
	private static ArrayList<String> getPatternList4ControlWithValue_(String sPattern) throws ExceptionZZZ{
		ArrayList<String> listaReturn = new ArrayList<String>();
		main:{
			if(StringZZZ.isEmpty(sPattern)) break main;
			
//			1b Pattern String auf Doppelpunkte untersuchen. 
			//Merke: Irgendwelche Indexbetrachtungen koennen nicht funktionieren. Die Reihenfolge der Argumente ist naemlich beliebig. 
			//Es muss vielmehr das Steuerungszeichen davor ermittelt werden. Damit kann dann das Argumentenarray untersucht werden: Folgt dem Steuerungszeichen immer ein anderer Wert
			String[] saDelim = {":"};
			Integer[] intaIndex = StringZZZ.indexOfAll(sPattern, saDelim);
			
						
			  if(intaIndex!=null){            	
	            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
	            		Integer inttemp = intaIndex[icount];
	            		if(inttemp.intValue()>=0){
	            			int itemp = inttemp.intValue();   
	            			
	            			//die Zeichen vor dem ":" ermitteln
	            			String stemp = null;
	            			int ilength = 0;
	            			do {	            				
	            				stemp = sPattern.substring(itemp-1, itemp); //Position des Zeichens vor dem ":", dann davor, etc.	            					            					            			
	            				if(!stemp.equals(":") && itemp>0) ilength++;
	            				itemp = itemp-1; //ein Zeichen weiterschieben nach links
	            			}while(stemp!=null && !stemp.equals(":") && itemp>0);
	            			
	            			if(ilength>=0) {
	            				itemp = inttemp.intValue();
	            				String sControl = sPattern.substring(itemp-ilength, itemp);
	            				//if(!StringZZZ.isEmpty(sControl)){
	            					
	            					//so, ggfs. haben sich Steuerungszeichen ohne Argument eingeschlichen.
	            					//diese entfernen aus dem String
	            					sControl = StringZZZ.right("|" + sControl, "|");
	            					if(!StringZZZ.isEmpty(sControl)){
	            						
	            						//so, ggfs. haben sich Steuerungszeichen mit optionalen Argument eingeschlichen.
		            					//diese entfernen aus dem String
		            					sControl = StringZZZ.right("." + sControl, ".");
		            					if(!StringZZZ.isEmpty(sControl)){
		            						listaReturn.add(sControl);
		            					}
	            						
	            						//listaReturn.add(sControl);
	            					}
	            					
	            					
	            				//}
	            			}
	            		}
	            	}
	           }
		}
		return listaReturn;
	}
	
	public static ArrayList<String> getPatternList4ControlWithOptionalValue(String sPattern) throws ExceptionZZZ{
		return getPatternList4ControlWithOptionalValue_(sPattern);
	}
	
	
	private static ArrayList<String> getPatternList4ControlWithOptionalValue_(String sPattern) throws ExceptionZZZ{
		ArrayList<String> listaReturn = new ArrayList<String>();
		main:{
			if(StringZZZ.isEmpty(sPattern)) break main;
			
//			1b Pattern String auf eínfachen Punkt untersuchen. 
			//Merke: Irgendwelche Indexbetrachtungen koennen nicht funktionieren. Die Reihenfolge der Argumente ist naemlich beliebig. 
			//Es muss vielmehr das Steuerungszeichen davor ermittelt werden. Damit kann dann das Argumentenarray untersucht werden: Folgt dem Steuerungszeichen immer ein anderer Wert
			String[] saDelim = {"."};
			Integer[] intaIndex = StringZZZ.indexOfAll(sPattern, saDelim);
			
						
			  if(intaIndex!=null){            	
	            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
	            		Integer inttemp = intaIndex[icount];
	            		if(inttemp.intValue()>=0){
	            			int itemp = inttemp.intValue();   
	            			
	            			//die Zeichen vor dem "." ermitteln
	            			String stemp = null;
	            			int ilength = 0;
	            			do {	            				
	            				stemp = sPattern.substring(itemp-1, itemp); //Position des Zeichens vor dem ":", dann davor, etc.	            					            					            			
	            				if(!stemp.equals(".") && itemp>0) ilength++;
	            				itemp = itemp-1; //ein Zeichen weiterschieben nach links
	            			}while(stemp!=null && !stemp.equals(".") && itemp>0);
	            			
	            			if(ilength>=0) {
	            				itemp = inttemp.intValue();
	            				String sControl = sPattern.substring(itemp-ilength, itemp);
	            				//if(!StringZZZ.isEmpty(sControl)){
	            					
	            					//so, ggfs. haben sich Steuerungszeichen ohne Argument eingeschlichen.
	            					//diese entfernen aus dem String
	            					sControl = StringZZZ.right("|" + sControl, "|");
	            					if(!StringZZZ.isEmpty(sControl)){
	            						
	            						//so, ggfs. haben sich Steuerungszeichen mit zwingendem Argument eingeschlichen.
		            					//diese entfernen aus dem String
		            					sControl = StringZZZ.right(":" + sControl, ":");
		            					if(!StringZZZ.isEmpty(sControl)){
		            						listaReturn.add(sControl);
		            					}
	            						
	            						//listaReturn.add(sControl);
	            					}
	            					
	            					
	            				//}
	            			}
	            		}
	            	}
	           }
		}
		return listaReturn;
	}
	
	
	
	public static ArrayList<String> getPatternList4ControlSimple(String sPattern) throws ExceptionZZZ{
		return getPatternList4ControlSimple_(sPattern);
	}
	
	private static ArrayList<String> getPatternList4ControlSimple_(String sPattern) throws ExceptionZZZ{
		ArrayList<String> listaReturn = new ArrayList<String>();
		main:{
			if(StringZZZ.isEmpty(sPattern)) break main;
			
			//Merke: Ein einzelner Wert ist immer ein Argument "ohne Wert".
			//       Darum einfach ein PIPE "|" hintenanhaengen.
			sPattern = sPattern + "|";
			
//			1b Pattern String auf PIPE "|" untersuchen. 
			//Merke: Irgendwelche Indexbetrachtungen koennen nicht funktionieren. Die Reihenfolge der Argumente ist naemlich beliebig. 
			//Es muss vielmehr das Steuerungszeichen davor ermittelt werden. Damit kann dann das Argumentenarray untersucht werden: Folgt dem Steuerungszeichen immer ein anderer Wert
			String[] saDelim = {"|"};
			Integer[] intaIndex = StringZZZ.indexOfAll(sPattern, saDelim);
			
		
			//Nun muss das Zeichen jeweils 1 Zeichen vor dem Indexwert geholt werden, 
			//dann hat man die STEUERZEICHEN, die kein ARGUMENT ERWARTEN	
			  if(intaIndex!=null){            	
	            	for(int icount=0; icount <= intaIndex.length-1; icount++){            		
	            		Integer inttemp = intaIndex[icount];
	            		if(inttemp.intValue()>=0){
	            			int itemp = inttemp.intValue();   
	            			
	            			//die Zeichen vor dem "|" ermitteln
	            			String stemp = null;
	            			int ilength = 0;
	            			do {	            				
	            				stemp = sPattern.substring(itemp-1, itemp); //Position des Zeichens vor dem "|", dann davor, etc.	            					            					            			
	            				if(!stemp.equals("|") && itemp>0) ilength++;
	            				itemp = itemp-1; //ein Zeichen weiterschieben nach links
	            			}while(stemp!=null && !stemp.equals("|") && itemp>0);
	            			
	            			if(ilength>=0) {
	            				itemp = inttemp.intValue();
	            				String sControl = sPattern.substring(itemp-ilength, itemp);
	            				//if(!StringZZZ.isEmpty(sControl)){
	            					
	            					//so, ggfs. haben sich Steuerungszeichen "mit Argument" eingeschlichen.
	            					//diese entfernen aus dem String
	            					sControl = StringZZZ.right(":" + sControl, ":");
	            					if(!StringZZZ.isEmpty(sControl)){
	            						
		            					//so, ggfs. haben sich Steuerungszeichen mit optionalen Argument eingeschlichen.
		            					//diese entfernen aus dem String
		            					sControl = StringZZZ.right("." + sControl, ".");
		            					if(!StringZZZ.isEmpty(sControl)){
		            								            						
		            						//es duerfen aber nur Strings ohne Sonderzeichen sein
			            					boolean bAlphanumeric = StringZZZ.isAlphanumeric(sControl);
			            					if(bAlphanumeric){
			            						listaReturn.add(sControl);
			            					}
		            					}
		            			
	            					}
	            				//}
	            			}	
	            		}
	            	}
	           }
		}
		return listaReturn;
	}
	
	/** Füllt die übergebenen Argumente in eine HashMap.
	 *   Dazu wird erst ein GetOpt-Object verwendet und dann fuer Optionen des "Pattern" abgefragt.
	 *   
	* @param saArg
	* 
	* lindhauer; 28.06.2007 07:03:54
	 * @throws ExceptionZZZ 
	 */
	public boolean loadOptionAll(String[] saArg) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saArg==null) break main;
			if(saArg.length==0) break main;
			
			//Prüfen, ob einer der Argumentstrings gefüllt ist.
			boolean bfound = false;
			for(int icount = 0; icount <= saArg.length - 1; icount++){
				if(! StringZZZ.isEmpty(saArg[icount])){
					bfound = true;
					break;
				}
			}
			if(bfound==false) break main; //ohne ein Argument lohnt sich das alles nicht
			
			
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Pattern missing allthough Argument-Array not empty.", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			boolean btemp = this.isArgumentValid(saArg);
			if (btemp == false){
				String sError = this.proofArgument(saArg);
				ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			 
			//20210331: Jetzt sind aber Optionsparameter mit mehr als 1 Zeichen gewuenscht.
			//          Das ist gescheitert, da zuviel in der GetOpt.getopt(saArg) Methode zu aendern ist.	
			//20260316: Jetzt sind Optionsparameter mit mehr als 2 Zeichen moeglich.
			GetOpt objOption = new GetOpt(sPattern);
			String a = objOption.getoptString(saArg);		
			String sOption = a.trim();
			sOption = StringZZZ.leftRespectingQuotes(sOption + "|", "|");//ohne ein mögliches PIPE
			
			//Fuer die Analyse, ob die Steuerungszeichen Argumente haben oder nicht			
			ArrayList<String> listaControlSimple = GetOptZZZ.getPatternList4ControlSimple(sPattern);
			ArrayList<String> listaControlOptionalValue = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			boolean bGoon = !StringZZZ.isEmpty(sOption);
			while(bGoon){
				boolean bIsControlSimple = listaControlSimple.contains(sOption);
				boolean bIsControlOptional = listaControlOptionalValue.contains(sOption);
				String sParam = objOption.optarg();
				
				//20260317: Für Argumente ohne Optionsparameter gilt: Sie sind gleich dem Argumentwert
				if(sParam==null & bIsControlSimple) {
					sParam = sOption;
				}
				this.getOptionMap().put(sOption, sParam);
				
				//Zum naechsten Argument
				a = objOption.getoptString(saArg);
				sOption = a.trim();
				if(bIsControlOptional) {
					bGoon = true; //Dann darf die Option auch null sein.
				}else {
					if(StringZZZ.isEmpty(sOption)) bGoon = false;
				}
			}
		    bReturn = true;
		}//end main
		 this.setFlag("isloaded",bReturn);
		return bReturn;
	}
	public String readOptionFirst(){
		String sReturn = null;
		main:{
			HashMap hm = this.getOptionMap();
			Iterator it = hm.keySet().iterator();
			this.setOptionIterator_(it);
			if(it.hasNext()){
				sReturn =(String) it.next();
			}
		}//ENd main
		return sReturn;
	}
	
	public String readOptionNext(){
		String sReturn = null;
		main:{
			Iterator it = null;
			if(this.getOptionIterator_()==null){
				HashMap hm = this.getOptionMap();
				it = hm.keySet().iterator();
				this.setOptionIterator_(it);
			}else{
				it = this.getOptionIterator_();
			}
			
			if(it != null){
				if(it.hasNext()){
					sReturn =(String) it.next();
				}
			}
			
		}//ENd main
		return sReturn;
	}
	public String readValue(String sOption){
		String sReturn = null;
		main:{
			HashMap hm = this.getOptionMap();
			if(hm==null) break main;
			if(hm.isEmpty()) break main;
			if(!hm.containsKey(sOption)) break main;
			
			sReturn = (String) hm.get(sOption);
		}
		return sReturn;
	}
	public String proofArgument(String sArgumentIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			//#### Parameter fuer die Funktion pruefen
			String sArgument = StringZZZ.trim(sArgumentIn);//wg. des explode nach " " unbedingt Leerzeichen wegtrimmen.
			if(StringZZZ.isEmpty(sArgument)) break main;//Null oder Leerstring sind valid
				
			//Falls das Pattern leer ist, sArgument ist aber gefuellt, dann FEHLER:
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			//#### Die Pruefung des Argument strings
//			1a Alle nach dem Leerzeichen zerlegen. Parameter pruefen.
//			String[] saParamAll = StringZZZ.explode(sArgument, " ");

			//20260323: Nun soll man aber mit einfachem Hochkomma das Leerzeichen ignorieren können			
			String[] saParamAll = StringZZZ.explodeRespectingQuotes(sArgument, " ");
			
			if(saParamAll==null|saParamAll.length==0) break main;
			
			sReturn = this.proofArgument_(sPattern, saParamAll);
			
		}//end main
		return sReturn;
	}
	
	public String proofArgument(String[] saParamAll) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(saParamAll==null|saParamAll.length==0) break main;
			
//			Falls das Pattern leer ist, sArgument ist aber gefuellt, dann FEHLER:
			String sPattern = this.getPattern();
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			//##### Die Pruefung
			sReturn = this.proofArgument_(sPattern, saParamAll);
			
		}//End main
		return sReturn;
	}
	
	private String proofArgument_(String sPattern, String[] saParamAll) throws ExceptionZZZ{
		String sReturn = "";
		main:{
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//#### Die Pruefung des Argumentarrays
			if(saParamAll==null|saParamAll.length==0) break main;

            //Die Liste aller moeglichen Steuerzeichen, gemaess fuehrendem Bindestrich GetOpt.sCOMMAND_PREFIX
			ArrayList<String> listaControlAny = GetOptZZZ.getList4ControlAny(saParamAll);
			
			//Die Liste der Steuerzeichen, gemaess Pattern
			ArrayList<String> listaControlWithValue = GetOptZZZ.getPatternList4ControlWithValue(sPattern);
			ArrayList<String> listaControlWithOptionalValue = GetOptZZZ.getPatternList4ControlWithOptionalValue(sPattern);
			ArrayList<String> listaControlWithoutValue = GetOptZZZ.getPatternList4ControlSimple(sPattern);
            ArrayList<String> listaControlAll = GetOptZZZ.getPatternList4ControlAll(sPattern);     
            
            //... Fehler, wenn Steuerzeichen ohne Pattern in der Liste sind
            ArrayList<String> listaControlWithoutPattern = ArrayListUtilZZZ.difference(listaControlAny, listaControlAll);
            if(listaControlWithoutPattern.size()>=1) {
            	String sControlNotInPattern="";
            	for(String sControl : listaControlWithoutPattern) {
            		if(!sControlNotInPattern.equals("")) {
            			sControlNotInPattern = sControlNotInPattern + ", ";
            		}else {
            			sControlNotInPattern = sControlNotInPattern + sControl;	
            		}            		
            	}
            	sReturn = "Error 09: Control characters found ('" + sControlNotInPattern + "'), which are not in Pattern ('" + sPattern + "')";
            	break main;
            } 
			
			//Nun die Argumentliste pruefen:
            //Es muss immer ein Steuerzeichen sein,
            //nur wenn es ein Steuerzeichen mit Parameter ist, dann darf der folgende Wert beliebig sein
            ArrayList<String> listaControlFound = new ArrayList<String>();
            String sControlPrevious = new String(""); String sControlCurrent = new String("");
            boolean bArgumentNeeded = false; boolean bArgumentAllowed = false;
            boolean bControlNeeded = true;//es braucht zu beginn immer ein Steuerzeichen
			for(int icount=0; icount <= saParamAll.length-1;icount++){
				String sParamCurrent = saParamAll[icount]; 				
				
				if(StringZZZ.isNull(sParamCurrent)){
					//20260403: null sollte wg. Ersetzung durch Umgebungsvariablen auch erlaubt sein (s. EnvironmentPlaceholderZZZ) if(!StringZZZ.isEmpty(sParamCurrent)){
					sControlPrevious="";
				}else if(StringZZZ.isBlank(sParamCurrent)) {
					//20260506: Ein Leerstring als Kommentar ist erlaubt.
					sControlPrevious="";
				}else if(!StringZZZ.isNull(sParamCurrent) && !StringZZZ.isBlank(sParamCurrent)) {
					String stemp = sParamCurrent.substring(0, 1);
					boolean bIsControl = false;
					if(stemp.equals(GetOpt.sCOMMAND_PREFIX)){
						bIsControl = true;
						sControlCurrent = StringZZZ.rightback(sParamCurrent, 1); //Wert ohne den Bindestrich
						sControlCurrent = StringZZZ.leftRespectingQuotes(sControlCurrent + "|", "|");                 //Wert ohne einen moeglichen PIPE.
						listaControlFound.add(sControlCurrent);  //Ohne den Bindestrich !!!
					}
								
					if(bIsControl) {
						//Steuerzeichen prüfen
						if(!listaControlAll.contains(sControlCurrent)){						
							//Fehler ein Steuerelement, dass nicht im PatternString definiert ist
							sReturn = "Error 25: Control character not defined in pattern: '" + sControlCurrent + "'";
							break main;
						}
						
						//Vorher wurde ein Steuerzeichen gefunden, das ein Argument braucht
						if(bArgumentNeeded) {
							//Der vorherige Eintrag des Pattern endete mit einem Doppelpunkt, aber jetzt kommt ein Steuerzeichen. 
							//D.h. es fehlt etwas
							sReturn = "Error 29: Previous control character needs an argument, but this is: '" + sParamCurrent + "'. Ergo: Missing argument for controlcharacter '"+sControlPrevious+"'." ;
							break main;
						}
						
						//Man weiss ja nicht, ob 2 Steuerzeichen hintereinander kommen.
						//Argumente prüfen (Einleiten, basierend auf dem aktuellen Steuerzeichen)
						if(listaControlWithoutValue.contains(sControlCurrent)) {
							bArgumentAllowed = false;
							bArgumentNeeded = false; //Das ist ein Steuerzeichen mit PIPE => KEIN Argument erlaubt
						}else if(listaControlWithValue.contains(sControlCurrent)){
							bArgumentAllowed = true;
							bArgumentNeeded = true; //Das ist ein Steuerzeichen mit Doppelpunkt => Argument wird benoetigt							
						}else if(listaControlWithOptionalValue.contains(sControlCurrent)){
							bArgumentAllowed = true;
							bArgumentNeeded = false; //Das ist ein Steuerzeichen mit Punkt => Argument ist optional							
						}
												
						//Nachdem die Pruefung der Steuerzeichen abgeschlossen ist
						sControlPrevious = sControlCurrent;	
						bControlNeeded = false;
					}else {
						if(bControlNeeded) {																				
							sReturn = "Error 10: Argument string ('"+ sParamCurrent+"') has no control parameter.";
							break main;						
						}
						
						//Mann weiss ja nicht, ob 2 Argumente hintereinander kommen.
						//Argumente prüfen (
						if(listaControlWithoutValue.contains(sControlPrevious)) {
							bArgumentAllowed = false;
							bArgumentNeeded = false; //Das ist ein Steuerzeichen mit PIPE => KEIN Argument erlaubt
						}else if(listaControlWithValue.contains(sControlPrevious)){
							bArgumentAllowed = true;
							bArgumentNeeded = true; //Das ist ein Steuerzeichen mit Doppelpunkt => Argument wird benoetigt							
						}else if(listaControlWithOptionalValue.contains(sControlPrevious)){
							bArgumentAllowed = true;
							bArgumentNeeded = false; //Das ist ein Steuerzeichen mit Punkt => Argument ist optional							
						}
						
						if(!bArgumentAllowed) {
							//sParam ist ja nicht leer, obwohl er es sein sollte.
							sReturn = "Error 12: Argument string exists ('"+sParamCurrent+"', but control character ('" + sControlPrevious + "' doesn´t allow any";
							break main;
						}
					
						//Nachdem die Pruefung der Argumente abgeschlossen ist
						bArgumentNeeded=false;
						bControlNeeded=true;
					}
					
					
//					if( !bIsControl & sControlPrevious.equals("")){						 
//						//Falls dieses gefundene Argument kein Steuerzeichen hat, FEHLER
//						sReturn = "Error 10: Argument string ('"+ sParamCurrent+"') has no control parameter.";
//						break main;
//					}
					
//					}else if(bIsControl & ! listaControlWithValue.contains(sControlPrevious)){
//						//Der vorherige Eintrag des Pattern endete nicht mit einem Doppelpunkt, dann ist das jetzt ein Steuerzeichen
//						sControlPrevious = StringZZZ.rightback(sParamCurrent, 1);	 //saParamAll[icount].substring(1);
//						stemp = StringZZZ.rightback(sParamCurrent, 1);
//						if(listaControlWithValue.contains(stemp)){
//							bArgumentNeeded = true; //Das ist ein Steuerzeichen mit Doppelpunkt => Argument wird ben�tigt
//							//Hier braucht man ggf. das vorherige Steuerzeiche noch
//						}else{
//							bArgumentNeeded = false; //Das ist Kein Steuerzeichen mit Punkt
//							if(!listaControlWithOptionalValue.contains(sControlPrevious)) { //Das ist kein Steuerzeichen mit Punkt
//								sControlPrevious = "";
//							}
//						}
//					}else if(bIsControl & bArgumentNeeded){
//					
//					}else{						
//						if(icount==0){
//							//kein vorheriger Eintrag, Fehler !!!
//							sReturn = "Error 30: No previous control character for the string: '" + saParamAll[icount] + "'";
//							break main;
//						}else if(sControlPrevious.equals("")){
//							//Der vorherige Eintrag ist kein Steuerzeichen, Fehler !!!
//							sReturn = "Error 40: Previous entry is no control character expecting an argument. Argument string: '" + saParamAll[icount] +"'";
//							break main;																			
//						}else if(! listaControlWithValue.contains(sControlPrevious)){
//							if(! listaControlWithOptionalValue.contains(sControlPrevious)) {
//								//							der vorherige Wert muss in der Liste der Steuerzeichen mit Argument sein
//								sReturn = "Error 50: Previous entry is not a valid control character. current string: '" + saParamAll[icount] + "'";
//								break main;
//							}
//						}
//						bArgumentNeeded = false;						
					}
//				}else{
//					//Fall sParamCurrent ist leer
//					if(bArgumentNeeded==true){
//						sReturn = "Error 59: Last control character '" + sControlPrevious + "' has no argument.";
//						break main;						
//					}else{
//						//Leeres Steuerzeichen
//						bArgumentNeeded = false;
//						sControlPrevious = "";
//					}
//				}
				
	
			}//END FOR
			
			//Fehler - LETZTES STEUERZEICHEN SOLLTE EINEN WERT HABEN, HAT ES ABER NCIHT
			if(bArgumentNeeded == true){
				sReturn = "Error 60: Last control character has no argument: '" + saParamAll[saParamAll.length-1] + "'";
				break main;
			}
		}//end main
		return sReturn;
	}
	
	public boolean isArgumentValid(String sArgumentIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Null oder Leerstring sind valid
			String sArgument = StringZZZ.trim(sArgumentIn);
			if(StringZZZ.isEmpty(sArgument)){
				bReturn = true;
				break main;
			}
			
			//Argument-Pattern
			String sPattern = this.getPattern();
			
			//Falls das Pattern leer ist, sArgument ist aber gef�llt, dann FEHLER:
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sReturnError = this.proofArgument(sArgument);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
	}
	
	public boolean isArgumentValid(String[] saArg) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Null oder Leerstring sind valid
			if(saArg==null){
				bReturn = true;
				break main;
			}
			
			if(saArg.length==0){
				bReturn = false;
				break main;
			}
			
			
			//Argument-Pattern
			String sPattern = this.getPattern();
			
			//Falls das Pattern leer ist, sArgument ist aber gefuellt, dann FEHLER:
			if(StringZZZ.isEmpty(sPattern)){
				ExceptionZZZ ez = new ExceptionZZZ("Unexpected arguments. No Argument-Pattern available", iERROR_PROPERTY_EMPTY, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sReturnError = this.proofArgument(saArg);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
		
	}
	
	
	/** Prueft. ob der Pattern String valide ist.
	 * Z.B: zwei Doppelpunkte hintereinander sind nicht valide
	 * - ueberhaupt sind doppelte zeichen im pattern string nicht valide
	 * - Bindestriche sind im pattern string ebenfalls nicht erlaubt (sonst muesste im Argument string ja "-- hallo" stehen duerfen.
	 * 
	* @param sPattern
	* @return
	* 
	* lindhauer; 07.07.2007 12:04:03
	 * @throws ExceptionZZZ 
	 */
	public boolean isPatternStringValid(String sPattern) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sReturnError = this.proofPatternString(sPattern);
			int iReturnError = GetOptZZZ.computeCodeFromProofResult(sReturnError);
			if (iReturnError <= -1){
				bReturn = true;
			}else{
				bReturn = false;
			}
		}
		return bReturn;
	}
	
	/** Prueft den Pattern String auf folgendes:
	 *1. Kein wert darf doppelt vorkommen, mit Ausnahme des Doppelpunkts
 	* 2. Nach einem Doppelpunkt darf kein zweiter Doppelpunkt sofort folgen
	* 3. Der Pattern String darf nicht mit einem Doppelpunkt beginnen.	
	* 
	* 20210331: Beispiel für einen gültigen gesamt String: 
	*           -s 02 -d <z:Null/> -z {"DEBUGUI_PANELLABEL_ON":true}
	* 
	* @param sPattern
	* @return  Ein String, der eine Fehlermeldung enthaelt. 
	* 
	* lindhauer; 11.07.2007 06:55:02
	 */
	public String proofPatternString(String sPattern){
		String sReturn = new String("");
		main:{			
			try {
				String sCharacter = null;
				String[] saPattern = null; String[] saPatternUnique = null; 
				int iLength = -1; int iLengthUnique = -1;
				char cDelim = 0; 
				boolean bCharacterDoubled = false;
				
				
				//#### Parameter fuer die Funktion pruefen
				//Null oder Leerstring sind valid
				if(StringZZZ.isEmpty(sPattern))	break main;
			
			
				//+++ 1a. Der Pattern String darf nicht mit einem Doppelpunkt beginnen.
				sCharacter = sPattern.substring(0,1);
				if(sCharacter.equals(":")){
					sReturn = "Error 3: The character ':' is an argument placeholder. It is not allowed at the beginning. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
					break main;
				}
			
				//+++ 1a. Der Pattern String darf nicht mit einem Pipe beginnen.
				sCharacter = sPattern.substring(0,1);
				if(sCharacter.equals("|")){
					sReturn = "Error 3: The character '|' is an argument separator. It is not allowed at the beginning. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
					break main;
				}
				
				//+++ 1a. Der Pattern String darf nicht mit einem Punkt beginnen.
				sCharacter = sPattern.substring(0,1);
				if(sCharacter.equals(".")){
					sReturn = "Error 3: The character '.' is an argument placeholder. It is not allowed at the beginning. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
					break main;
				}
			
				//+++ 2a. In dem Pattern String darf kein Wert doppelt vorkommen, mit Ausnahme des Doppelpunkts, Punkts oder Pipes
				String[]saArgumentPlaceholder= {":","|","."};
				saPattern = StringZZZ.explode(sPattern, saArgumentPlaceholder);
				iLength = saPattern.length;

				saPatternUnique = StringArrayZZZ.unique(saPattern);
				iLengthUnique = saPatternUnique.length;
			
				if(iLength!=iLengthUnique) {
					sReturn = "Error 1: There are duplicate Entries (seperated by : or | or .) in the pattern string . Pattern '" + sPattern +"'";
					break main;
				}
			
			//+++ 3a. Nach einem Doppelpunkt darf kein zweiter Doppelpunkt sofort folgen, bzw. Punkt, bzw. Pipe
			cDelim=':';
			bCharacterDoubled = StringZZZ.isCharacterDoubled(sPattern, cDelim);
			if(bCharacterDoubled) {
				sReturn = "Error 2: The character " + cDelim + " is an argument placeholder or separator. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
				break main;
			}
			
			cDelim='|';
			bCharacterDoubled = StringZZZ.isCharacterDoubled(sPattern, cDelim);
			if(bCharacterDoubled) {
				sReturn = "Error 2: The character " + cDelim + " is an argument placeholder or separator. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
				break main;
			}
		
			cDelim='.';
			bCharacterDoubled = StringZZZ.isCharacterDoubled(sPattern, cDelim);
			if(bCharacterDoubled) {
				sReturn = "Error 2: The character " + cDelim + " is an argument placeholder or separator. It is allowed only one time after a control character. Pattern '" + sPattern +"'";
				break main;
			}
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//+++ 4a. Nach einem Argumentplaceholder darf kein weiterer Argumentplaceholder folgen
			boolean bPlaceholderBehind=false;
			char[]caDelim= {':','.','|'};
			
			cDelim=':';			
			bPlaceholderBehind = StringZZZ.isCharacterBehind(sPattern,cDelim,caDelim);
			if(bPlaceholderBehind) {
				sReturn = "Error 4: The character " + cDelim + " is an argument placeholder or separator. No other placeholder is allowed directly behind. Pattern '" + sPattern +"'";
				break main;
			}
						
			//++++++++++++++++++++++
			cDelim='.';			
			bPlaceholderBehind = StringZZZ.isCharacterBehind(sPattern,cDelim,caDelim);
			if(bPlaceholderBehind) {
				sReturn = "Error 4: The character " + cDelim + " is an argument placeholder or separator. No other placeholder is allowed directly behind. Pattern '" + sPattern +"'";
				break main;
			}
			
			//++++++++++++++++++++++
			cDelim='|';			
			bPlaceholderBehind = StringZZZ.isCharacterBehind(sPattern,cDelim,caDelim);
			if(bPlaceholderBehind) {
				sReturn = "Error 4: The character " + cDelim + " is an argument placeholder or separator. No other placeholder is allowed directly behind. Pattern '" + sPattern +"'";
				break main;
			}
					
			}catch(ExceptionZZZ ez) {
				sReturn = "Error ExceptionZZZ: '" + ez.getMessageLast() + "'";
				break main;
			}
		}//end main
		return sReturn;
	}
	
	
	/** Liest beispielsweise aus dem String "Error 123: der Fehler ist folgender" den Wert 123 aus 
	* @param sResult
	* @return -1 bei keinem gefundenen Fehler
	* 
	* lindhauer; 07.07.2007 09:57:08
	 * @throws ExceptionZZZ 
	 */
	public static int computeCodeFromProofResult(String sResult) throws ExceptionZZZ{
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sResult)) break main;
			
			String sReturn = StringZZZ.left(sResult, 5, ":");  //5 ist die Zeichenlaenge von "Error"
			if(StringZZZ.isEmpty(sReturn)){
				ExceptionZZZ ez = new ExceptionZZZ("No error code found in the string:'"+ sResult+"'", iERROR_RUNTIME, GetOptZZZ.class.getName());
				throw ez;
			}
			sReturn = sReturn.trim();
			
			if(!StringZZZ.isNumeric(sReturn)){
				ExceptionZZZ ez = new ExceptionZZZ("None numeric error code found in the string: '" + sResult + "'", iERROR_RUNTIME, GetOptZZZ.class.getName());
				throw ez;
			}
			
			Integer intReturn = new Integer(sReturn);
			iReturn = intReturn.intValue();
		}
		return iReturn;
	}
						
	//############
	// Getter / Setter
	//############
	public HashMap getOptionMap(){
		return this.hmOpt;
	}
		
	private void setOptionIterator_(Iterator it){
		this.itOpt = it;
	}
	private Iterator getOptionIterator_(){
		return this.itOpt;
	}
	
	@Override
	public ExceptionZZZ getExceptionObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		// TODO Auto-generated method stub
		
	}

	//###########
	// FLAGS für diese Klassen
	//###########
	/**
	 * @see AbstractKernelUseObjectZZZ.basic.KernelUseObjectZZZ#setFlag(java.lang.String, boolean)
	 * @param sFlagName
	 * 	"isLoaded": Wird gesetzt, wenn die interne HashMap erfolgreich gef�llt worden ist, z.B. in .loadOptionAll(..);
	 * @throws ExceptionZZZ 
	 */
	public boolean setFlag(String sFlagName, boolean bFlagValue) throws ExceptionZZZ{
		boolean bFunction = false;
		main:{
		if(StringZZZ.isEmpty(sFlagName)) break main;
		bFunction = super.setFlag(sFlagName, bFlagValue);
		if(bFunction==true) break main;
	
		//setting the flags of this object
		String stemp = sFlagName.toLowerCase();
		if(stemp.equals("isloaded")){
			bFlagIsLoaded = bFlagValue;
			bFunction = true;
			break main;
		}
		}//end main:
		return bFunction;
	}
	
	public boolean getFlag(String sFlagName) throws ExceptionZZZ{
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sFlagName)) break main;
			bFunction = super.getFlag(sFlagName);
			if(bFunction==true) break main;
			
			//getting the flags of this object
			String stemp = sFlagName.toLowerCase();
			if(stemp.equals("isloaded")){
				bFunction = bFlagIsLoaded;
				break main;
			}
		}//end main:
		return bFunction;
	}
}//END CLASS

