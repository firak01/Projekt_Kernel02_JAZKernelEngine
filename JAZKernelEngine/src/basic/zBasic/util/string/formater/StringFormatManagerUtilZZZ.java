package basic.zBasic.util.string.formater;

import static basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ.sENUMNAME;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_LINENEXT_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_01_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_02_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_03_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_04_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.MapUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumMappedLogStringFormatAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;

/**Von ChatGPT Redundanz verringert am 20260221;
 * @author Fritz Lindhauer, 21.02.2026, 08:49:16
 * 
 */
public class StringFormatManagerUtilZZZ implements IConstantZZZ {

	
	 /* Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
		 * und dem String "sSeparator" als Wert.
		 * 
		 */
		public static HashMap<Integer, String> getHashMapLogStringSeparatorAll() throws ExceptionZZZ {
			return StringFormatManagerUtilZZZ.getHashMapLogStringSeparatorAll_(false);
		}
		
		public static HashMap<Integer, String> getHashMapLogStringSeparatorAll(boolean bLineFilter) throws ExceptionZZZ {
			return StringFormatManagerUtilZZZ.getHashMapLogStringSeparatorAll_(bLineFilter);
		}
		
		public static HashMap<Integer, String> getHashMapLogStringSeparatorAllForLine() throws ExceptionZZZ {
			return StringFormatManagerUtilZZZ.getHashMapLogStringSeparatorAll_(true);
		}
		
		
		private static HashMap<Integer, String> getHashMapLogStringSeparatorAll_(boolean bLineFilter) throws ExceptionZZZ {
			HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
			main:{
				//HashMap automatisch aus dem Enum errechnen.
				
				//Problem: ClassCastException.
				//Merksatz (wichtig!) - Erstellt von ChatGPT
				//Ein Enum-Array kann niemals direkt zu einem Interface-Array gecastet werden,
				//auch wenn das Enum dieses Interface implementiert
				
				//Aber das geht nicht, s. ChatGPT vom 20260110
				//IEnumSetMappedLogStringFormatZZZ[] ienuma = EnumAvailableHelperZZZ.searchEnumMapped(LogStringFormaterZZZ.class, ILogStringFormatZZZ.sENUMNAME);
				IEnumSetMappedStringFormatZZZ[] ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedArray(StringFormaterZZZ.class, sENUMNAME);
				for(IEnumSetMappedStringFormatZZZ ienum : ienuma) {

					int iArgumentType =  ienum.getArgumentType();
					if(iArgumentType==IStringFormatZZZ.iARG_CONTROL) {
						int iFaktor = ienum.getFactor();
						 switch(iFaktor){
					        case iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_MESSAGE_DEFAULT);
					            break;
					        case iFACTOR_CONTROLMESSAGESEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_MESSAGE_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROL01SEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_01_DEFAULT);
					            break;
					        case iFACTOR_CONTROL01SEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_01_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROL02SEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_02_DEFAULT);
					            break;
					        case iFACTOR_CONTROL02SEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_02_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROL03SEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_03_DEFAULT);
					            break;
					        case iFACTOR_CONTROL03SEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_03_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROL04SEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_04_DEFAULT);
					            break;
					        case iFACTOR_CONTROL04SEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_04_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROLPOSITIONSEPARATOR_STRING:
					        	hmReturn.put(iFaktor, sSEPARATOR_POSITION_DEFAULT);
					            break;
					        case iFACTOR_CONTROLPOSITIONSEPARATOR_XML:
					        	hmReturn.put(iFaktor, sSEPARATOR_POSITION_DEFAULT); //das eigentlich noch in XML umwandeln
					            break;
					        case iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
					        	//Das ist die Spalte vor dem ersten Separator, also der nicht vorhandene erste Separator
					        	break;
					        case IStringFormatZZZ.iFACTOR_NULL_STRING:
								//NULL STRING WERT, moeglich aber hier nicht verwendet			
								break;
					        case iFACTOR_LINENEXT_STRING:
					        	if(!bLineFilter) {
					        		//Der Format Manager teilt anhand dieses Kennzeichens die Formatanweisungen auf die verschiedenenen Zeile auf
					        		hmReturn.put(iFaktor,  StringZZZ.crlf());				        		
					        	}
					        	break;
					        default:
					            System.out.println("StringFormaterUtilZZZ.getHashMapLogStringSeparatorAll_(): Faktor iFaktor="+iFaktor + " wird nicht behandelt");
					            break;
					        }					
					}				
				}				
			}//end main:
			return hmReturn;
		}
		
		
		 /* Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
		 * und dem IEnumSetMappedZZZ "Abarbeitungstypen" als Wert.
		 */
		public static HashMap<Integer, IEnumSetMappedStringFormatZZZ> getHashMapFormatPositionAll() throws ExceptionZZZ {
			HashMap<Integer, IEnumSetMappedStringFormatZZZ> hmReturn = new HashMap<Integer,IEnumSetMappedStringFormatZZZ>();
			main:{
				//HashMap automatisch aus dem Enum errechnen.
				ArrayList<IEnumSetMappedStringFormatZZZ> ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(StringFormaterZZZ.class, sENUMNAME);
				for(IEnumSetMappedZZZ ienum : ienuma) {
					IEnumSetMappedStringFormatZZZ ienumLogString = (IEnumSetMappedStringFormatZZZ) ienum;
					hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString);
				}				
			}//end main:
			return hmReturn;
		}
		
		/* Analog zu
		 * @see basic.zBasic.util.log.ILogStringFormaterZZZ#getHashMapFormatPositionStringDefault()
		 * 
		 * Macht eine HashMap mit dem ienumLogString.getFactor() als Key 
		 * und der Formatanweisung des IEnumSetMappedZZZ "Abarbeitungstypen" als Wert.
		 */
		public static HashMap<Integer, String> getHashMapFormatPositionStringAll() throws ExceptionZZZ {
			HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
			main:{
				//HashMap automatisch aus dem Enum errechnen.
				ArrayList<IEnumSetMappedStringFormatZZZ> ienuma = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(StringFormaterZZZ.class, sENUMNAME);
				for(IEnumSetMappedZZZ ienum : ienuma) {
					IEnumSetMappedStringFormatZZZ ienumLogString = (IEnumSetMappedStringFormatZZZ) ienum;
					hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString.getFormat());
				}				
			}//end main:
			return hmReturn;
		}
		
		public static boolean isFormatUsingControl(IEnumSetMappedStringFormatZZZ ienumFormatLogString) {
			boolean bReturn = false;
			main:{
				if(ienumFormatLogString==null) break main;
				
				int iArgumentType = ienumFormatLogString.getArgumentType();
				
				switch(iArgumentType) {
				case IStringFormatZZZ.iARG_CONTROL:
					bReturn = true;
					break;
				case IStringFormatZZZ.iARG_CONTROLSTRING:
					bReturn = true;
					break;
				default:
					bReturn = false;
				};
			}//end main:
			return bReturn;
			
		}

		public static boolean isFormatUsingHashMap(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(ienumFormatLogString==null) break main;
				
				int iArgumentType = ienumFormatLogString.getArgumentType();
				
				switch(iArgumentType) {
				case IStringFormatZZZ.iARG_STRINGHASHMAP:
					bReturn = true;
					break;
				default:
					bReturn = false;
				};
			}//end main:
			return bReturn;
			
		}

		public static boolean isFormatUsingObject(IEnumSetMappedStringFormatZZZ ienumFormatLogString) {
			boolean bReturn = false;
			main:{
				if(ienumFormatLogString==null) break main;
				
				int iArgumentType = ienumFormatLogString.getArgumentType();
				
				switch(iArgumentType) {
				case IStringFormatZZZ.iARG_OBJECT:
					bReturn = true;
					break;
				case IStringFormatZZZ.iARG_SYSTEM:
					bReturn = true;
					break;
				default:
					bReturn = false;
				};
			}//end main:
			return bReturn;
			
		}

		//############################################################
		public static boolean isFormatUsingString(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(ienumFormatLogString==null) break main;
				
				int iArgumentType = ienumFormatLogString.getArgumentType();
				
				switch(iArgumentType) {
				case IStringFormatZZZ.iARG_STRING:
					bReturn = true;
					break;
				case IStringFormatZZZ.iARG_CONTROLSTRING:
					bReturn = true;
					break;
				default:
					bReturn = false;
				};
			}//end main:
			return bReturn;
			
		}

		public static boolean isFormatUsingStringXml(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(ienumFormatLogString==null) break main;
				
				int iArgumentType = ienumFormatLogString.getArgumentType();
				
				switch(iArgumentType) {
				case IStringFormatZZZ.iARG_STRINGXML:
					bReturn = true;
					break;
				default:
					bReturn = false;
				};
			}//end main:
			return bReturn;
			
		}

	
    // #############################################################
    // Öffentliche Hauptmethoden (Hier u.a. Weiterleiten auf besondere Util-Klassen, die gekapselt sind
    // #############################################################

	public static IEnumSetMappedStringFormatZZZ[] adaptFormatArray(IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringVorhanden) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]objaReturn=null;
		main:{
			if(ienumaFormatStringNeu==null) {
				objaReturn = ienumaFormatLogStringVorhanden;
				break main;
			}
			
			if(ienumaFormatLogStringVorhanden==null) {
				objaReturn = ienumaFormatStringNeu;
				break main;
			}
			
			//Hier werden aus dem neuen Array alle Elemente entfernt, die es schon im bestehenden Array gibt.
			//Passiert ueber Differenzmengenbildung
			//aber kein echtes Array, sondern ein enumSet... objaReturn = ArrayUtilZZZ.differenceSet(ienumaFormatStringVorhanden, ienumaFormatLogStringNeu, IEnumSetMappedStringFormatZZZ.class);
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.differenceMappedArray_rightSet(ienumaFormatStringNeu, ienumaFormatLogStringVorhanden);
			
			//Wenn es keine Differenzmenge gibt, dann ist die Anpassung = AusgangsFormatArray
			if(objaReturn==null || ArrayUtilZZZ.isEmpty(objaReturn)) {
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Keine neuen Felder in der individuellen Formatvorlage. Verwende die Feldreihenfolge wie in zuvor verwendeter (" + ienumaFormatStringVorhanden.length + " Felder) Formatvorlage .");
				objaReturn = ienumaFormatStringNeu;
				break main;
			}
			
			//Wenn es eine Differenzmenge gibt, dann wird diese an das Ausgangsformat angehaengt.
			//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Mische "+objaReturn.length+ " neue Felder der individuelle Formatvorlage in zuvor verwendeter (" + ienumaFormatStringVorhanden.length + " Felder) Formatvorlage ein. ");
			objaReturn = ArrayUtilZZZ.join(ienumaFormatStringNeu, objaReturn);
			
		}//end main:
		return objaReturn;
	}

	//##############################################################
	public static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyAcrosswise(IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		return StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyAcrosswise_(null, ienumaFormatStringVorhanden, ienumaFormatStringNeu);	
	}
	
	
	public static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyAcrosswise(ArrayListZZZ listaSeparator, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		return StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyAcrosswise_(listaSeparator, ienumaFormatStringVorhanden, ienumaFormatStringNeu);	
	}
	
	private static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyAcrosswise_(ArrayListZZZ listaSeparatorIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]objaReturn=null;
		main:{						
			if(listaSeparatorIn==null || listaSeparatorIn.size()==0) {
				if(ienumaFormatStringNeu==null) {
					objaReturn = ienumaFormatStringVorhanden;
					break main;
				}
				
				if(ienumaFormatStringVorhanden==null) {
					objaReturn = ienumaFormatStringNeu;
					break main;
				}
			}
			
			//1. Aufteilen auf eine HashMap: current und neues Formatierungsarray
			Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> mFormatCurrent = StringFormatManagerUtilZZZ.splitBySeparatorToStringHashMap(ienumaFormatStringVorhanden);			

			Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> mFormatNew = StringFormatManagerUtilZZZ.splitBySeparatorToStringHashMap(ienumaFormatStringNeu);
			
			
			//2. Hole eine Liste der verwendeten Separatoren
			//Merke: Das Zusammenfassend der Separatoren ist nicht nur einfaches Anhaengen der neuen Werte,
			//       sondern auch das Reinmischen neuer Werte an die passende Stelle. 
			ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator = MapUtilZZZ.mergeKeys(mFormatCurrent, mFormatNew);						
			if(listaSeparatorIn!=null && listaSeparatorIn.size()>=1) {														
				ArrayList<IEnumSetMappedStringFormatZZZ>listaSeparatorNew = ArrayListUtilZZZ.mergeKeepFirst(listaSeparatorIn, listaSeparator);
				listaSeparator = ArrayListUtilZZZ.toArrayList(listaSeparatorNew);
			}
				
			//3. Mischen der beiden HashMaps, so dass pro Separator ein Array ohne Redundante Einträge übrigbleibt 
			//   und neue Separatoren mit ihren Arrays nach hinten wandern. (also LinkedHashMap)... mergeSorted()...			
			LinkedHashMap<IEnumSetMappedStringFormatZZZ,IEnumSetMappedStringFormatZZZ[]> mFormatReturn = (LinkedHashMap<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]>) HashMapUtilZZZ.mergeMapsAndJoinArrayValuesUniqueKeyAcrosswise(listaSeparator, mFormatCurrent, mFormatNew);
			//Falls man die HashMap als Debug-String augeben moechte
			//String sDebug = HashMapUtilZZZ.computeDebugString4Arrays(mFormatReturn);
			//System.out.println(sDebug);
			//System.out.println("#########");
			
			
			//4. Nun die HashMap durchiterieren und das Rückgabearray zusammensetzen. 
			//   Von jedem Element den Key hinzufügen, danach die Value-Arrays joinen.
			objaReturn = StringFormatManagerUtilZZZ.mergeHashMapFormatPositions(mFormatReturn);
		}//end main:
		return objaReturn;
	}
	
	
	//##############################################################
	
	
	public static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyEachwise(IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		return StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyEachwise_(null, ienumaFormatStringVorhanden, ienumaFormatStringNeu);	
	}
	
	
	public static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyEachwise(ArrayListZZZ listaSeparator, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		return StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyEachwise_(listaSeparator, ienumaFormatStringVorhanden, ienumaFormatStringNeu);	
	}
	
	private static IEnumSetMappedStringFormatZZZ[] mergeFormatArraysUniqueKeyEachwise_(ArrayListZZZ listaSeparatorIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringVorhanden, IEnumSetMappedStringFormatZZZ[] ienumaFormatStringNeu) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]objaReturn=null;
		main:{						
			if(listaSeparatorIn==null || listaSeparatorIn.size()==0) {
				if(ienumaFormatStringNeu==null) {
					objaReturn = ienumaFormatStringVorhanden;
					break main;
				}
				
				if(ienumaFormatStringVorhanden==null) {
					objaReturn = ienumaFormatStringNeu;
					break main;
				}
			}
			
			//1. Aufteilen auf eine HashMap: current und neues Formatierungsarray
			Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> mFormatCurrent = StringFormatManagerUtilZZZ.splitBySeparatorToStringHashMap(ienumaFormatStringVorhanden);			

			Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> mFormatNew = StringFormatManagerUtilZZZ.splitBySeparatorToStringHashMap(ienumaFormatStringNeu);
			
			
			//2. Hole eine Liste der verwendeten Separatoren
			//Merke: Das Zusammenfassend der Separatoren ist nicht nur einfaches Anhaengen der neuen Werte,
			//       sondern auch das Reinmischen neuer Werte an die passende Stelle. 
			ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator = MapUtilZZZ.mergeKeys(mFormatCurrent, mFormatNew);						
			if(listaSeparatorIn!=null && listaSeparatorIn.size()>=1) {														
				ArrayList<IEnumSetMappedStringFormatZZZ>listaSeparatorNew = ArrayListUtilZZZ.mergeKeepFirst(listaSeparatorIn, listaSeparator);
				listaSeparator = ArrayListUtilZZZ.toArrayList(listaSeparatorNew);
			}
				
			//3. Mischen der beiden HashMaps, so dass pro Separator ein Array ohne Redundante Einträge übrigbleibt 
			//   und neue Separatoren mit ihren Arrays nach hinten wandern. (also LinkedHashMap)... mergeSorted()...			
			LinkedHashMap<IEnumSetMappedStringFormatZZZ,IEnumSetMappedStringFormatZZZ[]> mFormatReturn = (LinkedHashMap<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]>) HashMapUtilZZZ.mergeMapsAndJoinArrayValuesUniqueKeyEachwise(listaSeparator, mFormatCurrent, mFormatNew);
			//Falls man die HashMap als Debug-String augeben moechte
			//String sDebug = HashMapUtilZZZ.computeDebugString4Arrays(mFormatReturn);
			//System.out.println(sDebug);
			//System.out.println("#########");
			
			
			//4. Nun die HashMap durchiterieren und das Rückgabearray zusammensetzen. 
			//   Von jedem Element den Key hinzufügen, danach die Value-Arrays joinen.
			objaReturn = StringFormatManagerUtilZZZ.mergeHashMapFormatPositions(mFormatReturn);
		}//end main:
		return objaReturn;
	}
	
	
	//###########################################################
	public static IEnumSetMappedStringFormatZZZ[] mergeHashMapFormatPositions(LinkedHashMap<IEnumSetMappedStringFormatZZZ,IEnumSetMappedStringFormatZZZ[]> mFormatIn) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[]objaReturn=null;
		main:{
			if(mFormatIn==null) break main;
			
			ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaReturn = new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
			
			Set<Entry<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]>> setEntry = mFormatIn.entrySet();   // raw usage beibehalten wg. Signaturen
	        Iterator<Entry<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]>> it = setEntry.iterator();

	        while (it.hasNext()) {
	        	
	        	//================= KEY Handling ===========================
	        	 
	        	//1. Versuch mit raw cast
	        	//mit dem raw cast bekommt man die ArrayList<IEnumSetMappedStatusLocalZZZ> aber nicht gefüllt
	            //Map.Entry entry = (Map.Entry) it.next();   // raw cast
	        	
	        	//2. Versuch mit richtigem Typ
	        	Entry<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> entry = it.next();   // raw cast
	        	IEnumSetMappedStringFormatZZZ objKey = entry.getKey();
	        	IEnumSetMappedStringFormatZZZ[] objValue = entry.getValue();
	            listaReturn.add(objKey);
	            
	            // ================= VALUE -ARRAY HANDLING =================
	            if (objValue != null && objValue.getClass().isArray()) {

	                int length = java.lang.reflect.Array.getLength(objValue);

	                for (int i = 0; i < length; i++) {
	                    Object element = java.lang.reflect.Array.get(objValue, i);
	                    listaReturn.add((IEnumSetMappedStringFormatZZZ)element);
	                }

	            }
	        }

	        objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaReturn);
		}//end main:
		return objaReturn;		
	}
	
	
	//###############################################################
    public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType(
            IEnumSetMappedStringFormatZZZ[] formats,
            String... logs) throws ExceptionZZZ {
    	
    	return StringFormatManagerUtil_StringTypeLineAppenderZZZ.appendLines_StringType(formats, logs);
    	
    }

	public static ArrayListZZZ<IEnumSetMappedStringFormatZZZ> filterSeparatorsAsArrayList(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaReturn = null;
		main:{
			if(hm==null)break main;
			listaReturn = new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
			
			Set<IEnumSetMappedStringFormatZZZ> set = hm.keySet();
			Iterator<IEnumSetMappedStringFormatZZZ> it = set.iterator();
			while(it.hasNext()) {
				IEnumSetMappedStringFormatZZZ objFormatSet = it.next();
				if(objFormatSet.getArgumentType()==IStringFormatZZZ.iARG_CONTROL) {
					listaReturn.add(objFormatSet);
				}
			}
			
		}//end main:
		return listaReturn;
	}
	
	

	public static ArrayListZZZ<IEnumSetMappedStringFormatZZZ> filterSeparatorsAsArrayList(ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaFormat) throws ExceptionZZZ {
		ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaReturn = null;
		main:{
			if(listaFormat==null)break main;
			listaReturn = new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
		
			for(IEnumSetMappedStringFormatZZZ objFormat : listaFormat) {				
				if(objFormat.getArgumentType()==IStringFormatZZZ.iARG_CONTROL) {
					listaReturn.add(objFormat);
				}
			}
			
		}//end main:
		return listaReturn;
	}
	
	public static ArrayListZZZ<IEnumSetMappedStringFormatZZZ> filterSeparatorsAsArrayList(IEnumSetMappedStringFormatZZZ[] objaFormat) throws ExceptionZZZ {
		ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaReturn = null;
		main:{
			if(objaFormat==null)break main;
			listaReturn = new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();
		
			for(IEnumSetMappedStringFormatZZZ objFormat : objaFormat) {				
				if(objFormat.getArgumentType()==IStringFormatZZZ.iARG_CONTROL) {
					if(!listaReturn.contains(objFormat)) {
						listaReturn.add(objFormat);
					}
				}
			}
			
		}//end main:
		return listaReturn;
	}
    

	// #############################################################
		
	public static Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> splitBySeparatorToStringHashMap(IEnumSetMappedStringFormatZZZ[] objaFormatValue) throws ExceptionZZZ {
		Map<IEnumSetMappedStringFormatZZZ, IEnumSetMappedStringFormatZZZ[]> hmReturn = null;
		main:{
			if(objaFormatValue==null) break main;
			
			ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaFormatKey = StringFormatManagerUtilZZZ.filterSeparatorsAsArrayList(objaFormatValue);
			IEnumSetMappedStringFormatZZZ[] objaFormatKey = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaFormatKey);
			
			IEnumSetMappedStringFormatZZZ objFormatKeyFirst = IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_NULL_STRING_;
			hmReturn = ArrayUtilZZZ.splitByKeysToMap(objaFormatValue, objaFormatKey, objFormatKeyFirst, IEnumSetMappedStringFormatZZZ.class);
		}//end main:
		return hmReturn;
	}
}