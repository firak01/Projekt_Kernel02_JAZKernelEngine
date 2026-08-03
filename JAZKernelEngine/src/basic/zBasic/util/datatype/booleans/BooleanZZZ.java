package basic.zBasic.util.datatype.booleans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.console.multithread.IKeyPressConstantZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Merke: Der Packagename endet mit einem "s", da "boolean" selbst als java-Schluesselwort nicht darin verwendet werden darf. 
 * @author fl86kyvo
 *
 */
public class BooleanZZZ implements IConstantZZZ, Serializable{
	private static final char cYes = IKeyPressConstantZZZ.cKeyYes;
	private static final char cNo = IKeyPressConstantZZZ.cKeyYes;
	
	public static final List<? extends Object> listasSHORTCUT_TRUE_DEFAULT = Arrays.asList("y","yes","j","ja","true",cYes);
	public static final List<? extends Object> listasSHORTCUT_FALSE_DEFAULT = Arrays.asList("n","no","nein","false",cNo);
	
	private static final Map<Boolean,List<? extends Object>>hmConstInternal = new HashMap<Boolean,List<? extends Object>>()
	{
		{
			put(true,listasSHORTCUT_TRUE_DEFAULT);
			put(false,listasSHORTCUT_FALSE_DEFAULT);
		};
	};
	
	//Alle statischen Konstanten dann in dieser HashMap zusammenführen.
	//siehe: public static final List<String> listas_SHORTCUT_TRUE_DEFAULT = Array.asList("y","yes","j","ja");
	//und für die Listen: public static final List<String> listas_SHORTCUT_TRUE_DEFAULT = Array.asList("y","yes","j","ja");
	public static final Map<Boolean,List<? extends Object>>hmSHORTCUT_BOOLEAN = Collections.unmodifiableMap(hmConstInternal);
	
	
	/** Es wird versucht einen String in einen boolean Wert zu ueberfuehren.
	 *  Dabei wird ein Leerzeichen zu false.
	 *  Ansonsten werden die erlaubten String in einer Map zusammengefasst, fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind werfen eine IllegalArgumentException
	 *  
	 *  Merke: Hier wird die definierte Kanstanten-Map verwendet BooleanZZZ.hmSHORTCUT_BOOLEAN
	 *  
	 *   
	 *  
	 * @param sString
	 * @return
	 * @throws ExceptionZZZ
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 01.11.2022, 13:13:51
	 */
	public static boolean charToBoolean(char cChar) throws ExceptionZZZ, IllegalArgumentException {
		Map<Boolean,List<? extends Object>> hmConstBoolean = BooleanZZZ.hmSHORTCUT_BOOLEAN;
		return BooleanZZZ.charToBoolean(cChar, hmConstBoolean, true);
	}
	
	/** Es wird versucht ein Zeichen in einen boolean Wert zu ueberfuehren.
	 *  Dabei wird ein Leerzeichen zu false.
	 *  Ansonsten werden die erlaubten String in einer Map zusammengefasst, fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind werfen eine IllegalArgumentException
	 * @param sString
	 * @param hmConstBoolean
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 01.11.2022, 13:07:43
	 */
	public static boolean charToBoolean(char cChar, Map<Boolean,List<? extends Object>> hmConstBoolean, boolean bIgnoreCase) throws ExceptionZZZ, IllegalArgumentException {
		boolean bReturn = false;
		main:{
			if(CharZZZ.isEmptyNull(cChar)) break main;
			if(hmConstBoolean==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+" HashMap of the Boolean-String Expressions", iERROR_PARAMETER_MISSING,BooleanZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bIgnoreCase) {				
				List<? extends Object>listasTrue = hmConstBoolean.get(true);
				List<? extends Object>listasFalse = hmConstBoolean.get(false);
				String sCharUpper = CharZZZ.toString(CharZZZ.toUppercase(cChar));
				if(listasTrue.contains(sCharUpper)) {
					bReturn = true;
					break main;
				}
				if(listasFalse.contains(sCharUpper)) {
					bReturn = false;
					break main;
				}
				
				String sCharLower = CharZZZ.toString(CharZZZ.toLowercase(cChar));				
				if(listasTrue.contains(sCharLower)) {
					bReturn = true;
					break main;
				}
				if(listasFalse.contains(sCharLower)) {
					bReturn = false;
					break main;
				}
				
				
			}else {
				List<? extends Object>listasTrue = hmConstBoolean.get(true);
				String sChar = CharZZZ.toString(cChar);
				if(listasTrue.contains(sChar)) {
					bReturn = true;
					break main;
				}
				
				List<? extends Object>listasFalse = hmConstBoolean.get(false);
				if(listasFalse.contains(sChar)) {
					bReturn = false;
					break main;
				}
			}
			
			
			
			
			throw new IllegalArgumentException("Expression '"+ cChar + "' not defined by a boolean string representative.");
			
		}//end main:
		return bReturn;
	}
	
	//##########################################
	
	
	/** Es wird versucht einen String in einen boolean Wert zu ueberfuehren.
	 *  Dabei wird ein Leerzeichen zu false.
	 *  Ansonsten werden die erlaubten String in einer Map zusammengefasst, fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind werfen eine IllegalArgumentException
	 *  
	 *  Merke: Hier wird die definierte Kanstanten-Map verwendet BooleanZZZ.hmSHORTCUT_BOOLEAN
	 *  
	 *   
	 *  
	 * @param sString
	 * @return
	 * @throws ExceptionZZZ
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 01.11.2022, 13:13:51
	 */
	public static boolean stringToBoolean(String sString) throws ExceptionZZZ, IllegalArgumentException {
		Map<Boolean,List<? extends Object>> hmConstBoolean = BooleanZZZ.hmSHORTCUT_BOOLEAN;
		return BooleanZZZ.stringToBoolean(sString, hmConstBoolean, true);
	}
	
	/** Es wird versucht einen String in einen boolean Wert zu ueberfuehren.
	 *  Dabei wird ein Leerzeichen zu false.
	 *  Ansonsten werden die erlaubten String in einer Map zusammengefasst, fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind werfen eine IllegalArgumentException
	 * @param sString
	 * @param hmConstBoolean
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 01.11.2022, 13:07:43
	 */
	public static boolean stringToBoolean(String sString, Map<Boolean,List<? extends Object>> hmConstBoolean, boolean bIgnoreCase) throws ExceptionZZZ, IllegalArgumentException {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sString)) break main;
			if(hmConstBoolean==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+" HashMap of the Boolean-String Expressions", iERROR_PARAMETER_MISSING,BooleanZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			if(bIgnoreCase) {
				List<? extends Object>listasTrue = hmConstBoolean.get(true);
				List<? extends Object>listasFalse = hmConstBoolean.get(false);
				
				String sUpper = sString.toUpperCase();
				if(listasTrue.contains(sUpper)) {
					bReturn = true;
					break main;
				}
				if(listasFalse.contains(sUpper)) {
					bReturn = false;
					break main;
				}			
				
				String sLower = sString.toLowerCase();
				if(listasTrue.contains(sLower)) {
					bReturn = true;
					break main;
				}
				if(listasFalse.contains(sLower)) {
					bReturn = false;
					break main;
				}	
			}else {
				List<? extends Object>listasTrue = hmConstBoolean.get(true);
				if(listasTrue.contains(sString)) {
					bReturn = true;
					break main;
				}
				
				List<? extends Object>listasFalse = hmConstBoolean.get(false);
				if(listasFalse.contains(sString)) {
					bReturn = false;
					break main;
				}			
			}
			
			
			
			throw new IllegalArgumentException("Expression '"+ sString + "' not defined by a boolean string representative.");
			
		}//end main:
		return bReturn;
	}
	
	//##########################################
	
	/** Heuristische Lösung:
	 * Es wird versucht einen boolschen Wert in einen String zu ueberfuehren.
     *
	 *  Verwendet wird eine HashMap fuer fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind 
	 * @param sString
	 * @param hmConstBoolean
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 01.11.2022, 13:07:43
	 */
	public static String booleanToString(boolean bValue) throws ExceptionZZZ, IllegalArgumentException {
		Map<Boolean,List<? extends Object>> hmConstBoolean = BooleanZZZ.hmSHORTCUT_BOOLEAN;
		return BooleanZZZ.booleanToString(bValue, hmConstBoolean);
	}
	
	/** Heuristische Lösung:
	 * Es wird versucht einen boolschen Wert in einen String zu ueberfuehren.
     *
	 *  Verwendet wird eine HashMap fuer fuer TRUE-Werte und fuer FALSE-Werte.
	 *  Strings, die nicht in einer Liste vorhanden sind 
	 * @param sString
	 * @param hmConstBoolean
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 01.11.2022, 13:07:43
	 */
	public static String booleanToString(boolean bValue, Map<Boolean,List<? extends Object>> hmConstBoolean) throws ExceptionZZZ, IllegalArgumentException {
		String sReturn = null;
		main:{
			if(hmConstBoolean==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING+" HashMap of the Boolean-String Expressions", iERROR_PARAMETER_MISSING,BooleanZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bValue) {
				List<? extends Object>listasTrue = hmConstBoolean.get(true);
				sReturn = (String) listasTrue.get(0);				
			}else {
				List<? extends Object>listasFalse = hmConstBoolean.get(false);
				sReturn = (String) listasFalse.get(0);				
			}
			
		}//end main:
		return sReturn;
	}
	
}
