package basic.zBasic.util.datatype.xml;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zBasic.util.xml.tagsimple.ITagSimpleZZZ;
import basic.zBasic.util.xml.tagsimple.TagSimpleZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;

/**Hintergrund dieser Klasse ist, das die statischen Methoden in der Klasse XmlUtilZZZ immer eine Exception werfen.
 * Ich möchte diese Methoden aber z.B. in der Erstellung von Werten eines Enums verwenden.
 * Daher duerfen (wie generell bei Feld - Initialisierungen) keine checked Exceptions geworfen werden.
 * 
 * Lösung: Rufe die Methoden der Klasse mit den Exceptions auf, fange diese aber einfach ab.
 *         Private Methoden braucht es hier nicht.
 * 
 * @author Fritz Lindhauer, 19.11.2025, 07:15:09
 * 
 */
public class XmlUtilUnensuredZZZ implements IConstantZZZ{
	private XmlUtilUnensuredZZZ(){
		//Zum Verstecken des Konstruktors, sind halt nur static Methoden
	}
	
	public static boolean containsRegEx(String sExpression, String sRegEx){
		try {
			return XmlUtilZZZ.containsRegEx(sExpression, sRegEx);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return false;
		}
	}
	
	public static boolean containsRegEx(String sExpression, String sRegEx, boolean bExactMatch){
		try {
			return XmlUtilZZZ.containsRegEx(sExpression, sRegEx, bExactMatch);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return false;
		}
	}
	
	public static boolean containsRegEx_(String sExpression, String sRegEx, boolean bExactMatch){
		try {
			return StringZZZ.matchesPattern(sExpression, sRegEx, bExactMatch);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return false;
		}
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String computeTag(String sTagName, String sTagValue) {
		String sReturn = null;
		main:{			
			try {
				if(sTagValue==null) {
					//Das enthaelt nicht den TagNamen  sReturn = XmlUtilZZZ.computeTagNull();
					//Die aufrufende Methode kann ggfs. diesen Z:Null - Tag verwenden, wenn null zurueckkommt.
					break main;
				}else if(sTagValue.equals("")) {
					sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
					break main;
				}
				
				String sTagOpening = XmlUtilZZZ.computeTagPartOpening(sTagName); 
				String sTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				sReturn = sTagOpening + sTagValue + sTagClosing;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}
		return sReturn;
	}
	
	public static String computeTagAsHashMapEntry(String sTagName, String sTagValue){
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sTagName)) break main;
				
				if(sTagValue==null) {
					//Das enthaelt nicht den TagNamen  sReturn = XmlUtilZZZ.computeTagNull();
					//Die aufrufende Methode kann ggfs. diesen Z:Null - Tag verwenden, wenn null zurueckkommt.
					break main;
				}else if(sTagValue.equals("")) {
					sReturn = XmlUtilZZZ.computeTagEmpty(sTagName);
					break main;
				}
	
				sReturn = HashMapUtilZZZ.computeAsHashMapEntry(sTagName, sTagValue);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}
		return sReturn;
	}
	
	public static ITagSimpleZZZ computeAsTagSimple(String sTagName, String sTagValue) {
		try {
			ITagSimpleZZZ objReturn = new TagSimpleZZZ(sTagName, sTagValue);
			return objReturn;
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	
	//++++++++++++++++++++++
	public static String computeTagNull() {
		try {
			return XmlUtilZZZ.computeTagEmpty(ZTagFormulaIni_NullZZZ.sTAG_NAME);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
		
	}	
	
	public static String computeTagEmpty() {
		try {
			return XmlUtilZZZ.computeTagEmpty(KernelZFormulaIni_EmptyZZZ.sTAG_NAME);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
		
	//+++++++++++++++++++++
	public static String computeTagNameFromTagPart(String sTagPart){
		String sReturn = null;
		main:{
			try {
				ensureTagPart(sTagPart);
				
				if(XmlUtilZZZ.isTagPartOpening(sTagPart)) {
					sReturn = StringZZZ.midLeftRight(sTagPart, "<" ,">");
				}else if(XmlUtilZZZ.isTagPartClosing(sTagPart)) {
					sReturn = StringZZZ.midLeftRight(sTagPart, "</" ,">");
				}else {
					ExceptionZZZ ez = new ExceptionZZZ("Unexpected parameter sTagPart='" + sTagPart +"'", iERROR_PARAMETER_VALUE, XmlUtilZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	//++++++++++++++++++++++
	public static String computeTagPartOpening(String sTagName) {
		try {
			return XmlUtilZZZ.computeTagPartOpening(sTagName);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	}
	
	public static String computeTagPartClosing(String sTagName){	
		try {
			return XmlUtilZZZ.computeTagPartClosing(sTagName);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}			
	}
	
	public static String computeTagEmpty(String sTagName) {
		try {
			XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);				
			return "<" + sTagName + "/>";
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return null;
		}
	}
	
	//################################
	public static boolean containsTagName(String sExpression, String sTagName) {
		try {
			return XmlUtilZZZ.containsTagName(sExpression, sTagName, true);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return false;
		}
	}

		
	public static boolean containsTagName(String sExpression, String sTagName, boolean bExactMatch) {
		boolean bReturn = false;
		main:{
			try{
				String sMatchTagStarting = XmlUtilZZZ.computeTagPartOpening(sTagName);
				String sMatchTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName); 
				bReturn=StringZZZ.containsAsTag(sExpression, sMatchTagStarting, sMatchTagClosing, bExactMatch);
				if(bReturn) break main;
				
				String sMatchTagEmpty = XmlUtilZZZ.computeTagEmpty(sTagName);
				bReturn=StringZZZ.containsAsTag(sExpression, sMatchTagEmpty, "", bExactMatch);
				if(bReturn) break main;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}					
		}//end main
		return bReturn;
	}
	
	//################################
	public static int countTagName(String sExpression, String sTagName, boolean bExactMatch) {
		int iReturn = -1;
		main:{
			try {
				String sMatchTagStarting = XmlUtilZZZ.computeTagPartOpening(sTagName);
				String sMatchTagClosing = XmlUtilZZZ.computeTagPartClosing(sTagName); 
				int iStarting = StringZZZ.count(sExpression, sMatchTagStarting, bExactMatch);
				int iClosing = StringZZZ.count(sExpression, sMatchTagClosing, bExactMatch);
				
				int iValued = MathZZZ.min(iStarting, iClosing);
				
				String sMatchTagEmpty = XmlUtilZZZ.computeTagEmpty(sTagName);
				int iEmpty=StringZZZ.count(sExpression, sMatchTagEmpty, bExactMatch);
				
				iReturn = iValued + iEmpty;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return -1;
			}				
		}//end main
		return iReturn;
	}
	
	public static int countTagPart(String sExpression, String sTagPart, boolean bExactMatch) {
		int iReturn = -1;		
		main:{
			try {
				ensureTagPart(sTagPart);			
				iReturn = StringZZZ.count(sExpression, sTagPart, bExactMatch);	
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return -1;
			}
		}//end main
		return iReturn;
	}
	
	
	//################################
	public static boolean isTagEmpty(String sTag) {
		boolean bReturn = false;
		main:{		
			try {
				if(!StringZZZ.startsWith(sTag, "<")) break main;
				if(!StringZZZ.endsWith(sTag, "/>")) break main;
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean isTagPartOpening(String sTagPartOpening) {
		boolean bReturn = false;
		main:{	
			try {
				if(!StringZZZ.startsWith(sTagPartOpening, "<")) break main;
				if(!StringZZZ.endsWith(sTagPartOpening, ">")) break main;
				
				if(XmlUtilZZZ.isTagPartClosing(sTagPartOpening)) break main;
				if(XmlUtilZZZ.isTagEmpty(sTagPartOpening)) break main;
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
		
	public static boolean isTagPartClosing(String sTagPart) {
		boolean bReturn = false;
		main:{			
			try {
				if(!StringZZZ.startsWith(sTagPart, "</")) break main;
				if(!StringZZZ.endsWith(sTagPart, ">")) break main;
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean isTag(String sTagName) {
		boolean bReturn = false;
		main:{		
			try {
				if(!StringZZZ.startsWith(sTagName, "<")) break main;
				if(!StringZZZ.endsWith(sTagName, ">")) break main;
				
				//Aber das reicht noch nicht. Ein Tag hat einen Opening-Part und einen Closing-Part
				//oder ist ein Leertag
				boolean bTagEmpty = XmlUtilZZZ.isTagEmpty(sTagName);
				if(bTagEmpty) break main;
				
				//reine Closing oder Opening Tagparts ausschliessen
				boolean bTagOpening = XmlUtilZZZ.isTagPartOpening(sTagName);
				if(bTagOpening) break main;
				
				boolean bTagClosing = XmlUtilZZZ.isTagPartClosing(sTagName);
				if(bTagClosing) break main;
				
				//Ansonsten wird es schwierig, wir müssen den Tagnamen holen
				//Danach kann man pruefen, ob wirklich ein opening Tag am Anfang steht und ein closing Tag am Ende
				//TODOGOON20250209;
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean isTagPart(String sTagPart) {
		boolean bReturn = false;
		main:{	
			try {
				boolean bTagPartStarting = XmlUtilZZZ.isTagPartOpening(sTagPart);
				boolean bTagPartClosing = XmlUtilZZZ.isTagPartClosing(sTagPart);
				if(!bTagPartStarting & !bTagPartClosing) break main;
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	//+++++
	/*
	 * https://stackoverflow.com/questions/5396164/java-how-to-check-if-string-is-a-valid-xml-element-name
	 */
	public static boolean isTagNameValid(String sTagName) {
		try {
			return XmlUtilZZZ.isTagNameValid(sTagName);
			//return org.apache.xerces.util.XMLChar.isValidName(sTagName);
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			return false;
		}
	}
	
	//######################################
	//### Ensure wirft immer eine Exception, wenn was nicht klappt
	//######################################
	public static boolean ensureExpressionTagNameValid(String sTagName) {
		boolean bReturn = false;
		main:{
			try {
				if(StringZZZ.isEmpty(sTagName)) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected a filled String as TagName '" + sTagName +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;				
				}
				
				if(isTag(sTagName)) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not the tag itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(isTagPart(sTagName)) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagname as parameter not a tagpart itself '" + sTagName +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
							
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureNotTagPart(String sString) {
		boolean bReturn = false;
		main:{			
			try {
				boolean bTagPart = XmlUtilZZZ.isTagPart(sString);
				if(!bTagPart) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected not a tagpart as parameter '" + sString +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPart(String sTagPart){
		boolean bReturn = false;
		main:{
			try {
				boolean bTag = XmlUtilZZZ.isTag(sTagPart);
				if(bTag) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPart +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				boolean bTagPart = XmlUtilZZZ.isTagPart(sTagPart);
				if(!bTagPart) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname as parameter '" + sTagPart +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPartOpening(String sTagPartOpening){
		boolean bReturn = false;
		main:{
			try {
				if(StringZZZ.isEmpty(sTagPartOpening)) {
					ExceptionZZZ ez = new ExceptionZZZ("A tagpartname as parameter expected. Is '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				
				boolean bTag = XmlUtilZZZ.isTag(sTagPartOpening);
				if(bTag) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				boolean bTagPart = XmlUtilZZZ.isTagPartOpening(sTagPartOpening);
				if(!bTagPart) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname type opening as parameter '" + sTagPartOpening +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	/*
	 Ensure wirft immer eine Exception, wenn was nicht klappt
	 */
	public static boolean ensureTagPartClosing(String sTagPartClosing) {
		boolean bReturn = false;
		main:{			
			try {
				if(StringZZZ.isEmpty(sTagPartClosing)) {
					ExceptionZZZ ez = new ExceptionZZZ("A tagpartname as parameter expected. Is '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				boolean bTag = XmlUtilZZZ.isTag(sTagPartClosing);
				if(bTag) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected only the tagpartname as parameter not tag itself '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				boolean bTagPart = XmlUtilZZZ.isTagPartClosing(sTagPartClosing);
				if(!bTagPart) {
					ExceptionZZZ ez = new ExceptionZZZ("Expected a tagpartname type closing as parameter '" + sTagPartClosing +"'", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getClassCallingName(), ReflectCodeZZZ.getMethodCallingName());
					throw ez;
				}
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	
	//########################################
	//### PREVIOUS - Richtung
	//########################################
				
	public static String findFirstTagNamePreviousTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstTagPartPreviousTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstOpeningTagNamePreviousTo(String sXml, String sSep){
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartPreviousTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstClosingTagNamePreviousTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstClosingTagPartPreviousTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;		
	}


	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstTagPartPreviousTo(String sXml, String sSep) {	
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep)) break main;
				
				String sLEFT = StringZZZ.left(sXml, sSep);
				if(StringZZZ.isEmpty(sLEFT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; String sTagPartNameProof = null;
				do {
	 				
					iIndexCLOSING = StringZZZ.indexOfLastBefore(sLEFT, ">");
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
					iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "<", iIndexCLOSING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexOPENING <= -1) break main;
					
					
					sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Ein gueltiges Tag gefunden? //Leere Tags werden nicht beruecksichtigt	
					sTagPartNameProof = StringZZZ.stripLeft(sTagPartName, "/"); //Egal ob Oeffnendes oder Schliessendes Tag					
					bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartNameProof);
					if(bValidTagName) {
						bGoon=true;
						break;
					}
					
					sLEFT = StringZZZ.left(sLEFT, iIndexCLOSING-1);//Verbereiten fuer Weitersuchen //falls ein > zwischen opening und closing lag, also nun closing verringern
				} while(bGoon==false);
				
				sReturn = "<" + sTagPartName + ">"; // bei einem abschliesenden Tag ist der SLASH voran im Namen mit dabei.
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public static String findFirstOpeningTagPartPreviousTo(String sXml, String sSep) {	
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep)) break main;
				
				String sLEFT = StringZZZ.left(sXml, sSep);
				if(StringZZZ.isEmpty(sLEFT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; 
				do {
	 				
					iIndexCLOSING = StringZZZ.indexOfLastBefore(sLEFT,  ">");
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
			
					iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "<", iIndexCLOSING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexOPENING <= -1) break main;
	
					
					
					sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Ein gueltiges OEFFNENDES Tag gefunden? 
					if(StringZZZ.startsWith(sTagPartName, "/")) {
						//Kein oeffnendes Tag gefunden
					}else {
						if(StringZZZ.endsWith(sTagPartName, "/")) {
							//Leere Tags werden nicht beruecksichtigt
						}else {						
							bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
							if(bValidTagName) {
								bGoon=true;
								break;
							}
						}
					}
									
					sLEFT = StringZZZ.left(sLEFT, iIndexCLOSING-1); //Vorbereitung zum Weitersuchen. //falls ein > zwischen opening und closing lag, also nun closing verringern
					
				} while(bGoon==false);
				
				sReturn = "<" + sTagPartName + ">";
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}

	public static String findFirstClosingTagPartPreviousTo(String sXml, String sSep) {	
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep)) break main;
				
				String sLEFT = StringZZZ.left(sXml, sSep);
				if(StringZZZ.isEmpty(sLEFT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; 
				do {
	 				
					iIndexOPENING = StringZZZ.indexOfLastBehind(sLEFT, "</"); //Dann gibt es kein (weiteres) oeffendes Tagzeichen, des Schliessenden TagParts
					if(iIndexOPENING <= -1) break main;		
					
					iIndexCLOSING = StringZZZ.indexOfFirstBefore(sLEFT, ">", iIndexOPENING);
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
															
					sTagPartName = StringZZZ.midLeftRight(sLEFT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Kein schliesendes Tag gefunden
					if(StringZZZ.endsWith(sTagPartName, "/")) {
						//Leere Tags werden nicht beruecksichtigt
					}else {						
						bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
						if(bValidTagName) {
							bGoon=true;
							break;
						}
					}
					
					sLEFT = StringZZZ.left(sLEFT, (iIndexCLOSING)); //Vorbereitung zum Weitersuchen.
				} while(bGoon==false);
				
				sReturn = "</" + sTagPartName + ">"; 
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	//########################################
	//### NEXT - Richtung
	//########################################
	public static String findFirstOpeningTagPart(String sXml) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartNextTo(sXml, "");
				if(sTagPart==null) break main;
				
				sReturn = sTagPart;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	public static String findFirstOpeningTagName(String sXml) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartNextTo(sXml, "");
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	public static String findFirstClosingTagPart(String sXml) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstClosingTagPartNextTo(sXml, "");
				if(sTagPart==null) break main;
				
				sReturn = sTagPart;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	/**Da lediglich die Rueckgabe des Tags keinen Mehrwert besitzt, gib wenigstens die Startposition als Index zurück.
	 * @param sXmlIn
	 * @param sTagName
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 06.11.2025, 09:42:55
	 */
	public static int findFirstClosingTagPartIndex(String sXmlIn, String sTagName) {
		int iReturn = -1;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				String sXmlPart = XmlUtilZZZ.computeTagPartClosing(sTagName);
				int iXmlPartLength = sXmlPart.length();
				//Nun in einer Schleife diesen TagPart suchen
				String sXmlRemaining = sXmlIn;
				String sTagPartFound=null;
				int iTagPartIndex;	int iTagPartIndexTotal=0;		
				while(true) {
					iTagPartIndex = XmlUtilZZZ.findFirstClosingTagPartIndexNextTo(sXmlRemaining, "");
					if(iTagPartIndex==-1) break main;
				
					iTagPartIndexTotal = iTagPartIndexTotal+iTagPartIndex; 				
					sXmlRemaining = StringZZZ.rightback(sXmlIn, iTagPartIndexTotal);
					
					sTagPartFound = StringZZZ.midKeep(sXmlIn, iTagPartIndexTotal, ">");
					if(sTagPartFound==null) break main; //Dann gibt es den Tag nicht
					if(sXmlPart.equalsIgnoreCase(sTagPartFound)) break;
					
					
					iTagPartIndexTotal = iTagPartIndexTotal+sTagPartFound.length();
					sXmlRemaining = StringZZZ.right(sXmlRemaining, sTagPartFound); 
					if(sXmlRemaining.length() < iXmlPartLength) break main; //Dann kann der Tag nicht mehr im String vorhanden sein.
				}
				iReturn = iTagPartIndexTotal;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return -1;
			}
		}//end main:
		return iReturn;
	}
	
	public static String findFirstClosingTagName(String sXml){
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstClosingTagPartNextTo(sXml, "");
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	//++++++++++++++++++++++++
	public static String findFirstTag(String sXmlIn){
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				String sXml=sXmlIn;
				
				String sTagPartOpening = XmlUtilZZZ.findFirstOpeningTagPartNextTo(sXml, "");
				if(sTagPartOpening==null) break main;									
				
				String sTagName = XmlUtilZZZ.computeTagNameFromTagPart(sTagPartOpening);
				String sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				
				sXml = sXmlIn;
				sReturn = StringZZZ.midLeftRightKeep(sXml, sTagPartOpening, sTagPartClosing);	
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	public static String findFirstTag(String sXmlIn, String sTagName) {
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				String sXml=sXmlIn;
				
				String sTagPartOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
				String sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				
				sReturn = StringZZZ.midLeftRightKeep(sXml, sTagPartOpening, sTagPartClosing);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	

	//+++++++++++++++++++++++++
	public static String findFirstTagValue(String sXmlIn) {
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				String sXml=sXmlIn;
				
				String sTagPartOpening = XmlUtilZZZ.findFirstOpeningTagPartNextTo(sXml, "");
				if(sTagPartOpening==null) break main;									
				
				String sTagName = XmlUtilZZZ.computeTagNameFromTagPart(sTagPartOpening);
				String sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				
				sXml = sXmlIn;
				sReturn = StringZZZ.midLeftRight(sXml, sTagPartOpening, sTagPartClosing);	
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	public static String findFirstTagValue(String sXmlIn, String sTagName){
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				String sXml=sXmlIn;
				
				String sTagPartOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
				String sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
				
				sReturn = StringZZZ.midLeftRight(sXml, sTagPartOpening, sTagPartClosing);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	//++++++++++++++++++++++++++++++++
	public static String findFirstTagNameNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstTagPartNextTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}

	//++++++++++++++++++++++
	public static String findFirstOpeningTagNameNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstOpeningTagPartNextTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	//+++++++++++++++++++++++
	public static String findFirstClosingTagNameNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				String sTagPart = XmlUtilZZZ.findFirstClosingTagPartNextTo(sXml, sSep);
				if(sTagPart==null) break main;
				
				sReturn = XmlUtilZZZ.computeTagNameFromTagPart(sTagPart);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstTagPartNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep)) break main;
				
				String sRIGHT = StringZZZ.rightback(sXml, sSep);
				if(StringZZZ.isEmpty(sRIGHT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; String sTagPartNameProof = null;
				do {
	 				
					iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT , "<");
					if(iIndexOPENING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
					
					iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT , ">", iIndexOPENING); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexCLOSING <= -1) break main;
				
					
					sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Ein gueltiges Tag gefunden? //Leere Tags werden nicht beruecksichtigt				
					sTagPartNameProof = StringZZZ.stripLeft(sTagPartName, "/"); //Egal ob Oeffnendes oder Schliessendes Tag
					bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartNameProof);
					if(bValidTagName) {					
						bGoon=true;
						break;
					}
					
					sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1);//falls ein < zwischen opening und closing lag, also nur opening erhöhen und nicht von closing ausgehen.
				} while(bGoon==false);
				
				sReturn = "<" + sTagPartName + ">"; // bei einem abschliesenden Tag ist der SLASH voran im Namen mit dabei.
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;			
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String findFirstOpeningTagPartNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep) & !StringZZZ.isEmpty(sSep)) break main; //Leerstring ist fuer sSep erlaubt. Dann soll vom Anfang her gesucht werden.
				
				String sRIGHT = StringZZZ.rightback(sXml, sSep);
				if(StringZZZ.isEmpty(sRIGHT)) {
					sRIGHT = sXml;
					//break main;
				}
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; 
				do {
	 				
					iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT, "<"); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexOPENING <= -1) break main;
	
					iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT,  ">", iIndexOPENING);
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
		
					sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Ein gueltiges OEFFNENDES Tag gefunden? 
					if(StringZZZ.startsWith(sTagPartName, "/")) {
						//Kein oeffnendes Tag gefunden
					}else {
						if(StringZZZ.endsWith(sTagPartName, "/")) {
							//Leere Tags werden nicht beruecksichtigt
						}else {						
							bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
							if(bValidTagName) {
								bGoon=true;
								break;
							}
						}
					}
					
					sTagPartName=null;
					sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1); //Vorbereitung zum Weitersuchen. //falls ein < zwischen opening und closing lag, also nur opening erhöhen
				} while(bGoon==false);
				
				sReturn = "<" + sTagPartName + ">";
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;			
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++
	public static int findFirstClosingTagPartIndexNextTo(String sXml, String sSep) {
		int iReturn = -1;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep) & !StringZZZ.isEmpty(sSep)) break main; //Leerstring ist fuer sSep erlaubt. Dann soll vom Anfang her gesucht werden.
				
				String sRIGHT = StringZZZ.rightback(sXml, sSep);
				if(StringZZZ.isEmpty(sRIGHT)) {
					sRIGHT = sXml;
					//break main;
				}
				//String sRIGHT = StringZZZ.rightback(sXml, sSep);
				//if(StringZZZ.isEmpty(sRIGHT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; 
				do {
	 				
					iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT, "</"); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexOPENING <= -1) break main;
	
					iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT,  ">", iIndexOPENING);
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
		
					sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Kein schliesendes Tag gefunden
					if(StringZZZ.endsWith(sTagPartName, "/")) {
						//Leere Tags werden nicht beruecksichtigt
					}else {						
						bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
						if(bValidTagName) {
							bGoon=true;
							break;
						}
					}
					
					
					sTagPartName=null;
					sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1); //Vorbereitung zum Weitersuchen.
					
					
				
				} while(bGoon==false);
				
				iReturn = iIndexOPENING-2; //-2 wg Laenge von "<" und "Behind".
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return -1;
			}
		}//end main:
		return iReturn;						
	}
	
	public static String findFirstClosingTagPartNextTo(String sXml, String sSep) {
		String sReturn = null;
		main:{
			try {
				if(!StringZZZ.contains(sXml, sSep) & !StringZZZ.isEmpty(sSep)) break main; //Leerstring ist fuer sSep erlaubt. Dann soll vom Anfang her gesucht werden.
				
				String sRIGHT = StringZZZ.rightback(sXml, sSep);
				if(StringZZZ.isEmpty(sRIGHT)) {
					sRIGHT = sXml;
					//break main;
				}
				//String sRIGHT = StringZZZ.rightback(sXml, sSep);
				//if(StringZZZ.isEmpty(sRIGHT)) break main;
				
				int iIndexCLOSING=-1; int iIndexOPENING = -1;
				
				//Suche nach dem TAG, bzw. dem naechsten gueltigen TAG.
				boolean bGoon = false; boolean bValidTagName = false;
				String sTagPartName = null; 
				do {
	 				
					iIndexOPENING = StringZZZ.indexOfFirstBehind(sRIGHT, "</"); //Dann gibt es kein (weiteres) oeffnendes Tagzeichen
					if(iIndexOPENING <= -1) break main;
	
					iIndexCLOSING = StringZZZ.indexOfFirstBefore(sRIGHT,  ">", iIndexOPENING);
					if(iIndexCLOSING <= -1) break main; //Dann gibt es kein (weiteres) abschliessendes Tagzeichen
									
		
					sTagPartName = StringZZZ.midLeftRight(sRIGHT, iIndexOPENING, iIndexCLOSING);
					if(sTagPartName==null)break main;
					
					//Kein schliesendes Tag gefunden
					if(StringZZZ.endsWith(sTagPartName, "/")) {
						//Leere Tags werden nicht beruecksichtigt
					}else {						
						bValidTagName = XmlUtilZZZ.isTagNameValid(sTagPartName);
						if(bValidTagName) {
							bGoon=true;
							break;
						}
					}
					
					
					sTagPartName=null;
					sRIGHT = StringZZZ.rightback(sRIGHT, iIndexOPENING+1); //Vorbereitung zum Weitersuchen.
					
					
				
				} while(bGoon==false);
				
				sReturn = "</" + sTagPartName + ">";
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;						
	}
	
	
	public static String findFirstChildTagNameByParentName(String sXmlIn, String sTagName) {
		String sReturn = null;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn))break main;
				if(StringZZZ.isEmpty(sTagName))break main;
				
				String sXml = sXmlIn;
				sXml = XmlUtilZZZ.findFirstTagValue(sXml, sTagName);
				if(sXml==null) break main;
				
				sXml = XmlUtilZZZ.findFirstOpeningTagName(sXml);
				
				sReturn = sXml;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;	
	}
	
	//############################
	//############################
	//++++++++++++++++++++++++
		public static String findLastTag(String sXmlIn) {
			String sReturn = null;
			main:{
				try {
					if(StringZZZ.isEmpty(sXmlIn)) break main;
					
					String sXml=sXmlIn;				
					boolean bGoon=true;
					do {
						String sTagTemp = XmlUtilZZZ.findFirstTag(sXml);
						if(!StringZZZ.isEmpty(sTagTemp)) {
							sXml = StringZZZ.rightback(sXml, sTagTemp);
							sReturn = sTagTemp;						
						}else {
							bGoon=false;
						}					
					}while(bGoon);	
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					return null;
				}
			}//end main:
			return sReturn;
		}
		
		public static String findLastTag(String sXmlIn, String sTagName) {
			String sReturn = null;
			main:{
				try {
					if(StringZZZ.isEmpty(sXmlIn)) break main;
					
					String sXml=sXmlIn;				
					boolean bGoon=true;
					do {
						String sTagTemp = XmlUtilZZZ.findFirstTag(sXml, sTagName);
						if(!StringZZZ.isEmpty(sTagTemp)) {
							sXml = StringZZZ.rightback(sXml, sTagTemp);
							sReturn = sTagTemp;						
						}else {
							bGoon=false;
						}					
					}while(bGoon);
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					return null;
				}
			}//end main:
			return sReturn;
		}
		

		//+++++++++++++++++++++++++
		public static String findLastTagValue(String sXmlIn) {
			String sReturn = null;
			main:{
				try {
					if(StringZZZ.isEmpty(sXmlIn)) break main;
					
					String sXml=sXmlIn;				
					boolean bGoon=true;
					do {
						String sTagTemp = XmlUtilZZZ.findFirstTagValue(sXml);
						if(!StringZZZ.isEmpty(sTagTemp)) {
							sXml = StringZZZ.rightback(sXml, sTagTemp);
							sReturn = sTagTemp;						
						}else {
							bGoon=false;
						}					
					}while(bGoon);	
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					return null;
				}
			}//end main:
			return sReturn;
		}
		
		public static String findLastTagValue(String sXmlIn, String sTagName) {
			String sReturn = null;
			main:{
				try {
					if(StringZZZ.isEmpty(sXmlIn)) break main;
										
					String sXml=sXmlIn;				
					boolean bGoon=true;
					do {
						String sTagTemp = XmlUtilZZZ.findFirstTagValue(sXml, sTagName);
						if(!StringZZZ.isEmpty(sTagTemp)) {
							sXml = StringZZZ.rightback(sXml, sTagTemp);
							sReturn = sTagTemp;						
						}else {
							bGoon=false;
						}					
					}while(bGoon);	
				} catch (ExceptionZZZ ez) {
					ez.printStackTrace();
					return null;
				}
			}//end main:
			return sReturn;
		}
	
	
	//#############################
	//#############################
	public static String findTextOuterXml(String sXml) {
		String sReturn = sXml;
		main:{
			try {
				if(StringZZZ.isEmpty(sXml)) break main;
				
				
				boolean bContainsXml = XmlUtilZZZ.isXmlContained(sXml);			
				if(!bContainsXml)break main; //Wenn kein xml vorhanden ist, dann nimm nur 1 Wert, bzw. gib den Wert zurueck
				
				//Wenn xml vorhanden ist, dann verbinde beide Werte
				String sBefore = null;
				String sBeforeIn = XmlUtilZZZ.findTextOuterXmlBefore(sXml);
				if(sBeforeIn!=null) {
					sBefore = sBeforeIn;
				}else {
					sBefore = "";
				}
				
				String sBehindIn = XmlUtilZZZ.findTextOuterXmlBehind(sXml);								
				String sBehind = null;
				if(sBehindIn!=null) {
					sBehind = sBehindIn;
				}else {
					sBehind = "";
				}
				
				sReturn = sBefore + sBehind;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	public static String findTextOuterXmlBefore(String sXmlIn) {
		String sReturn = sXmlIn;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				
	
				String sTag = XmlUtilZZZ.findFirstTag(sXmlIn);
				if(StringZZZ.isEmpty(sTag)) break main; //dannn gibt es keinen Tag
				
				sReturn = StringZZZ.left(sXmlIn, sTag);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	
	public static String findTextOuterXmlBehind(String sXmlIn) {
		String sReturn = sXmlIn;
		main:{
			try {
				if(StringZZZ.isEmpty(sXmlIn)) break main;
				
	
				String sTag = XmlUtilZZZ.findLastTag(sXmlIn);
				if(StringZZZ.isEmpty(sTag)) break main; //dannn gibt es keinen Tag
				
				sReturn = StringZZZ.rightback(sXmlIn, sTag);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}//end main:
		return sReturn;
	}
	
	//#############################
	//#############################
	
	
	/** Heuristischer Ansatz. Ermittle einen Tagname.
	 *  Dann ermittle, ob es zu dem Tagnamen einen schliessenden Tag gibt.
	 *  Dann sollte es irgendwie etwas mit XML sein...
	 *   
	 * @param sXml
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 06.11.2025, 08:42:35
	 */
	public static boolean isXmlContained(String sXml) {
		boolean bReturn = false;
		main:{
			try {
				//ermittle einen unbestimmten Tag
				String sTagOpening = XmlUtilZZZ.findFirstOpeningTagPart(sXml);
				if(StringZZZ.isEmpty(sTagOpening)) break main;
			
				//ermittle nun dahinter den abschliessenden Tag
				String sXmlRemaining = StringZZZ.right(sXml, sTagOpening);
				String sTagName = XmlUtilZZZ.computeTagNameFromTagPart(sTagOpening);
				int iIndex = XmlUtilZZZ.findFirstClosingTagPartIndex(sXmlRemaining, sTagName);
				if(iIndex==-1) break main;
			
				//TEST: Hier muss nun der String mit dem abschliessenden Tag anfangen....
				//String sText = sXmlRemaining.substring(iIndex);
				
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean isExpression(String sExpression) {
		boolean bReturn = true;
		main:{
			try {
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			boolean bCheck = false;
			bCheck = XmlUtilZZZ.containsTagName(sExpression, "Z", false);
			if(bCheck) break main;
					
			//Ein Z-Tag ist nicht zwingend notwendig
			//Weil nur der Start-Tag nicht reicht, auch den Ende Tag mitnehmen.
			int iIndex = StringZZZ.indexOfFirst(sExpression, "<Z:", false);
			if (iIndex>=0) {
			
				//Das Ende Tag sollte auch hinter dem Starttag liegen
				iIndex = StringZZZ.indexOfFirst(sExpression, "</Z:", iIndex, false);
				if (iIndex>=0)break main;
			}
			
			//Path Anweisungen koennen auch ohne Z-Tags hier stehen
			iIndex = StringZZZ.indexOfFirst(sExpression, KernelZFormulaIni_PathZZZ.sTAGPART_OPENING, false);
			if (iIndex>=0) {
				
				//Das Ende Tag sollte auch hinter dem Starttag liegen
				iIndex = StringZZZ.indexOfFirst(sExpression, KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING, iIndex, false);
				if (iIndex>=0)break main;				
			}
			
			//TODOGOON20250222: Evt. Noch Empty-Tag und Null-Tag hier hierein
			
				bReturn = false;
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean isExpression4TagXml(String sExpression, String sTagName) {
		boolean bReturn = false;
		main:{			
			try {
				bReturn = XmlUtilZZZ.containsTagName(sExpression, sTagName, false);
				if(bReturn) break main;			
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
			
		}//end main
		return bReturn;
	}
	
	/** Merke: Path-Anweisungen in einer ini-Datei haben eine andere Struktur als XML-Tags.
	 * @param sLineWitchExpression
	 * @param sTagName
	 * @return
	 * @author Fritz Lindhauer, 20.07.2024, 07:48:25
	 * @throws ExceptionZZZ 
	 */
	public static boolean isExpression4Tag(String sExpression, String sTagOpening, String sTagClosing) {
		boolean bReturn = false;
		main:{			
			try {
				bReturn=StringZZZ.containsAsTag(sExpression, sTagOpening, sTagClosing, false);
				if(bReturn) break main;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}			
		}//end main
		return bReturn;
	}
	
	
	/** Also der String beginnt mit dem Starting Tag und endet mit dem Closing Tag
	 * @param sExpression
	 * @param sTagOpening
	 * @param sTagClosing
	 * @return
	 * @author Fritz Lindhauer, 05.09.2024, 18:30:57
	 */
	public static boolean isSurroundedByTag(String sExpression, String sTagOpening, String sTagClosing){
		boolean bReturn = false;
		main:{
			try {
				if(StringZZZ.isEmpty(sExpression)) break main;
				
			
				boolean bSurroundedLeft = StringZZZ.startsWithIgnoreCase(sExpression, sTagOpening);
				boolean bSurroundedRight = StringZZZ.endsWithIgnoreCase(sExpression, sTagClosing);
				
				if(bSurroundedLeft && bSurroundedRight) bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//end main:
		return bReturn;
	}
	
	
	public static Vector3ZZZ<String>parseFirstVector(String sExpression, String sTagOpening, String sTagClosing, boolean bKeepSurroundingSeparatorsOnParse){
		Vector3ZZZ<String>vecReturn = null;		
		main:{
			try {
				//Bei dem einfachen Tag wird das naechste oeffnende Tag genommen und dann auch das naechste schliessende Tag...
				vecReturn = StringZZZ.vecMidFirst(sExpression, sTagOpening, sTagClosing, bKeepSurroundingSeparatorsOnParse, false);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}
		return vecReturn;
	}
	
	
	public static Vector3ZZZ<String>parseFirstVectorCascadedTag(String sExpression, String sTagOpening, String sTagClosing, boolean bKeepSurroundingSeparatorsOnParse) {
		Vector3ZZZ<String>vecReturn = null;		
		main:{
			try {
				boolean bIgnoreCase = true;
				vecReturn = parseFirstVectorCascadedTag(sExpression, sTagOpening, sTagClosing, bKeepSurroundingSeparatorsOnParse, bIgnoreCase);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}
		return vecReturn;
	}
	
	public static Vector3ZZZ<String>parseFirstVectorCascadedTag(String sExpression, String sTagOpening, String sTagClosing, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ{
		Vector3ZZZ<String>vecReturn = null;		
		main:{
			try {
				//20250322; //ich will aber die umgebenden Separatoren erst einmal erhalten. 
				//            Wenn man sie central haelt, dann gehen sie automatisch beim solven, substitute, etc verloeren
				//            also nicht: vecReturn = StringZZZ.vecMidKeepSeparatorCentral(sExpression, sTagOpening, sTagClosing, !bIgnoreCase);					
				vecReturn = StringZZZ.vecMidKeepSeparator(sExpression, sTagOpening, sTagClosing, !bIgnoreCase);
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return null;
			}
		}
		return vecReturn;
	}
}
