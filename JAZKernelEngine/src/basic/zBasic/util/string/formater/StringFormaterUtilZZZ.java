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
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_01_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_02_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_03_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_04_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZ;
import basic.zBasic.util.datatype.enums.EnumMappedLogStringFormatAvailableHelperZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ.LOGSTRINGFORMAT;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;


public class StringFormaterUtilZZZ implements IConstantZZZ{
	
		
	//#######################################################################################################################
	public static String justifyLine(IStringJustifierZZZ objStringJustifier, String sLog) throws ExceptionZZZ{		
		String sReturn = sLog;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
								
			//Teile die Zeile ggfs. am Zeilenumbruch auf
			String[] saLine = StringZZZ.explode(sLog, StringZZZ.crlf());
			
			sReturn = justifyLine_(objStringJustifier, saLine);
		}//end main:
		return sReturn;
	}
	
	public static String justifyLine(IStringJustifierZZZ objStringJustifier, String[] saLog) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(saLog==null) break main;						
			
			sReturn = justifyLine_(objStringJustifier, saLog);
		}//end main:
		return sReturn;		
	}
	
	private static String justifyLine_(IStringJustifierZZZ objStringJustifier, String[] saLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(saLine==null) break main;
			
			ArrayListZZZ<String> listasLineZ = ArrayListUtilZZZ.toArrayListString(saLine);
			ArrayListZZZ<String> listasReturn = justifyLineArrayList_(objStringJustifier, listasLineZ);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	public static ArrayListZZZ<String> justifyLineArrayList(IStringJustifierZZZ objStringJustifier, ArrayListZZZ<String>listasLine) throws ExceptionZZZ{
		return justifyLineArrayList_(objStringJustifier, listasLine);
	}

	private static ArrayListZZZ<String> justifyLineArrayList_(IStringJustifierZZZ objStringJustifier, ArrayListZZZ<String>listasLine) throws ExceptionZZZ{
		ArrayListZZZ<String>listasReturn=null;;
		main:{	
			if(listasLine==null) break main;
			
			listasReturn = new ArrayListZZZ<String>();
			if(objStringJustifier==null | listasLine.isEmpty()) break main;
			
			
			//In den übergebenen Zeilen koennten weitere Kommentartrenner stecken.
			//Daher jede Zeile splitten und danach das neue "Standardisierte Array" weiterverarbeiten
			ArrayListZZZ<String>listasLineNormed = normStringForJustifyArrayList_(objStringJustifier, listasLine);
			
			//Bei 0 wuerde nix buendig gemacht, darum die Grenze ggfs. explizit auf die Position des ersten SeparatorStrings setzen
			int iIndexMayIncrease = objStringJustifier.computeLineMarkerIndexLeftBehind2use(listasLineNormed.get(0)); 
			objStringJustifier.setLineMarkerIndexLeftBehindIncreased(iIndexMayIncrease);
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Nun das Standardisierte Array anpassen
			if(listasLineNormed.size()>=2) {
				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
				ArrayListZZZ<String> listasLineReversed1 =  ArrayListUtilZZZ.reverse(listasLineNormed);
				ArrayListZZZ<String> listasLineReversedJustified1 = objStringJustifier.justifyLine(listasLineReversed1);
				
				ArrayListZZZ<String>listasLineReversed2 =  ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
				ArrayListZZZ<String>listasLineReversedJustified2 = objStringJustifier.justifyLine(listasLineReversed2);
				
				listasReturn = listasLineReversedJustified2;
			}else {				
				String sLine = listasLineNormed.get(0);
				if(sLine!=null) {
					String sLineJustified = objStringJustifier.justifyLine(sLine);
					listasReturn.add(sLineJustified);
				}
			}		
		}//end main:
		return listasReturn;
	}
	
	//###############################################
	//Wenn nun schon eine StringPosition uebergeben wurde, dann wird die Kommentarmarkierung ggfs. unnoetigerweise weit nach rechts verschoben.
	//Also aufteilen, trimmen und neu bauen.
	public static String justifyInfoPartAdded(IStringJustifierZZZ objStringJustifier, String sLine, String sLog) throws ExceptionZZZ{
		String sReturn = sLog;
		main:{			
			boolean bLineEmpty = StringZZZ.isEmpty(sLine);
			boolean bLogEmpty  = StringZZZ.isEmpty(sLog);
			if( bLogEmpty & bLineEmpty ) break main;			
			if(bLogEmpty && !bLineEmpty) {
				return justifyLine(objStringJustifier, sLine);
			}else if (!bLogEmpty && bLineEmpty) {
				return justifyLine(objStringJustifier, sLog);
			}
			
			String[]saLogCommented = normStringForJustify_(objStringJustifier, sLine, sLog);					
			sReturn = justifyLine(objStringJustifier, saLogCommented);
		}//end  main:
		return sReturn;
	}
	
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier,String sLine, String sLog) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{			
			boolean bLineEmpty = StringZZZ.isEmpty(sLine);
			boolean bLogEmpty  = StringZZZ.isEmpty(sLog);
			if(bLineEmpty && bLogEmpty) break main;
			
			if(bLogEmpty) {
				saReturn = new String[1];
				saReturn[0] = sLine;
				break main;
			}
			
			if(bLineEmpty) {
				sLine = "";
			}
										
			//Trimme nun ggf. Leerzeichen weg
			String[]saLineTrimmed = normStringForJustify_(objStringJustifier, sLine);
			
			//Ergänze nun die getrimmten String mit einem neuen Kommentar-Marker voranstellen
			String sSeparatorMessageDefault = StringFormaterUtilZZZ.computeLinePartInLog_ControlCommentSeparator();
			
			int iIndex=-1;
			saReturn = new String[saLineTrimmed.length];
			for(String sLineTrimmed : saLineTrimmed ) {
				if(!StringZZZ.isEmpty(sLineTrimmed)){					
					iIndex++;
					if(iIndex==saLineTrimmed.length-1) { //Das Log an der letzten Zeile anhaengen
						//Beim Anhaengen nur den Kommentartrenner hinzufuegen, wenn er noch nicht im Log ist.
						//Merke: Der Kommentartrenner kann ja schon vorher durch andere Log Berechnungen reingekommen sein.						  
						if(sLog.contains(sSeparatorMessageDefault) || sLog.contains(IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)){
							if(sLineTrimmed.endsWith(sSeparatorMessageDefault)){
								sLineTrimmed = StringZZZ.stripRight(sLineTrimmed, sSeparatorMessageDefault);
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else if (sLineTrimmed.endsWith(IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
								sLineTrimmed = StringZZZ.stripRight(sLineTrimmed, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else {
								saReturn[iIndex] = sLineTrimmed + sLog;
							}
						}else {
							if(sLineTrimmed.endsWith(sSeparatorMessageDefault) || sLineTrimmed.endsWith(IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT)) {
								saReturn[iIndex] = sLineTrimmed + sLog;
							}else {
								saReturn[iIndex] = sLineTrimmed + sSeparatorMessageDefault + sLog;
							}
						}						
					}else {					
						saReturn[iIndex] = sLineTrimmed;					
					}		
				}
			}
		}//end main:
		return saReturn;
	}
	
	
	
	
	public static String[] normStringForJustify(IStringJustifierZZZ objStringJustifier,String[] saLogIn) throws ExceptionZZZ {	
		return normStringForJustify_(objStringJustifier, saLogIn);
	}
	
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier, String sLog) throws ExceptionZZZ {		
		String[]saLog = new String[1];
		saLog[0]=sLog;
		return normStringForJustify_(objStringJustifier, saLog);
	}
	
	private static String[] normStringForJustify_(IStringJustifierZZZ objStringJustifier, String... saLogIn) throws ExceptionZZZ {
		String[] saReturn=null;
		main:{			
			if(ArrayUtilZZZ.isEmpty(saLogIn)) break main;
			if(objStringJustifier==null) {
				saReturn = saLogIn;
				break main;
			}
			
			ArrayListZZZ<String> listasLogZ = ArrayListUtilZZZ.toArrayListString(saLogIn);
			ArrayListZZZ<String> listasReturn = normStringForJustifyArrayList_(objStringJustifier, listasLogZ);
								
			saReturn = listasReturn.toStringArray();
		}//end main:
		return saReturn;
	}
	
	private static ArrayListZZZ<String> normStringForJustifyArrayList_(IStringJustifierZZZ objStringJustifier, ArrayListZZZ<String>listasLog) throws ExceptionZZZ {
		ArrayListZZZ<String>listasReturn=null;;
		main:{	
			if(listasLog==null) break main;
			
			if(objStringJustifier==null | listasLog.isEmpty()) {
				listasReturn=listasLog;
				break main;
			}else {
				listasReturn = new ArrayListZZZ<String>();
			}
							
			String sSeparatorCommentUsed = objStringJustifier.getPositionSeparator();//ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT; //Also nicht ThreadIdSeparator, das bringt nur Probleme //;objStringJustifier.getPositionSeparator();
			
			//Die Einträge aufteilen und trimmen. Damit macht ein ein vorheriges "justifien" wieder rueckgaengig
			ArrayListZZZ<String>listasLogTrimmed = new ArrayListZZZ<String>();
			int iIndex=-1; int iIndexSub; String sReturnSub=null;
			for(String sLog : listasLog) {
				if(sLog!=null) {
					iIndex++;
					if(StringZZZ.contains(sLog, sSeparatorCommentUsed)) {
						String[] saLogSub = StringZZZ.explode(sLog, sSeparatorCommentUsed);
						iIndexSub=-1;
						sReturnSub="";
						for(String sLogSub : saLogSub) {	
							iIndexSub++;
							sLogSub = sLogSub.trim(); //trimmen
							if(!StringZZZ.isEmpty(sLogSub)) {							
								if(iIndexSub==0) {
									sReturnSub = sLogSub;
								}else{
									sReturnSub = sReturnSub + sSeparatorCommentUsed + sLogSub; // + wieder den hinteren Kommentarteil	
								}
								
							}//isEmpty(sLogSub)
						}//end for	
						listasLogTrimmed.add(sReturnSub);
					}else {
						sLog = sLog.trim();
						listasLogTrimmed.add(sLog);
					}//end if constains
				}//end if sLog!=null	
			}//end for
			
			//mache nix weiter
			listasReturn = listasLogTrimmed;

		}//end main:
		return listasReturn;
	}
	
	
	//###############################################################################################
	//### Berechne alle moeglichen Kommentar-, etc. Trenner. 
	//### - auch um auszuschliessen, das eine Zeile nur aus diesem beseht.
	public static String[] computeLinePartInLog_ControlSeparatorAny() throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			String stemp=null; 
			ArrayList<String>listasSeparator=new ArrayList<String>();
			
			//+++++++++++++++++++++
			stemp = computeLinePartInLog_ControlCommentSeparator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_ControlCommentSeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control01Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control01SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control02Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control02SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control03Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control03SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_Control04Separator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_Control04SeparatorXml();
			listasSeparator.add(stemp);
			
			stemp = computeLinePartInLog_ControlFilePositionSeparator();
			listasSeparator.add(stemp);
			stemp = computeLinePartInLog_ControlFilePositionSeparatorXml();
			listasSeparator.add(stemp);
			
			//+++++++++++++++++++++++++++++
			saReturn = ArrayListUtilZZZ.toStringArray(listasSeparator);
		}//end main:
		return saReturn;
	}
	
	//Hole den formatierten CommentSeparator, ausgehend von dem einfachen Separator-String
	public static String computeLinePartInLog_ControlCommentSeparator(String sSeparatorSearched) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			//Heuristische Loesung, auch um redundanten Code zu vermeiden
			String[] saControlCommentSeparator = computeLinePartInLog_ControlSeparatorAny();
			for(String sControlCommentSeparator : saControlCommentSeparator) {
				if(StringZZZ.contains(sControlCommentSeparator, sSeparatorSearched)) {
					sReturn = sControlCommentSeparator;
					break main;
				}
			}
		}//end main:
		return sReturn;
	}
	
	public static String computeLinePartInLog_ControlCommentSeparator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_ControlCommentSeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control01Separator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control01SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control02Separator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control02SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control03Separator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control03SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_Control04Separator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	public static String computeLinePartInLog_Control04SeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_ControlFilePositionSeparatorXml() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	public static String computeLinePartInLog_ControlFilePositionSeparator() throws ExceptionZZZ {
		IEnumSetMappedStringFormatZZZ ienumFormatLogString = StringFormatManagerUtilZZZ.getHashMapFormatPositionAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING));
		return StringFormaterUtilZZZ.computeLinePartInLog(ienumFormatLogString);
	}
	
	//###############################################################################################
	
	
	/** Nur das formatierte SEPARATOR-ZEICHEN
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String computeLinePartInLog(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		
		main:{
//			Class classObj = null;		
//			if(classObjIn==null) {
//				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;					
//			}else {
//				classObj = classObjIn;
//			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
//			String sLog= null;
//			if(sLogIn!=null) {
//				String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
//				if(!StringZZZ.isEmpty(sOuter)) {				
//					//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
//					//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
//					//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
//					//Darum entfernen wir dies ggfs.
//					sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
//					if(StringZZZ.isEmpty(sOuter)) break main;
//					sLog = StringZZZ.joinAll(sLog, sOuter);
//				}else {
//					//Also: sOuter ist Leerstring oder Null UND es nicht explizit ein XML, nur dann den String übernehme
//					boolean bContainsXml = XmlUtilZZZ.isXmlContained(sLogIn);	
//					if(bContainsXml) {
//						//mache nix
//					}else {
//						sLog = StringZZZ.joinAll(sLog, sOuter);
//					}
//				}				
//			}
//			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
//			if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			String sLog = "";
	        switch (ienumFormatLogString.getFactor()) {
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR04, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
		        	//Das ist die Spalte vor dem ersten Separator, also der nicht vorhandene erste Separator
		        	break;
	            case IStringFormatZZZ.iFACTOR_NULL_STRING:
					//NULL STRING WERT, moeglich aber hier nicht verwendet			
					break;
	            case IStringFormatZZZ.iFACTOR_LINENEXT_STRING:
					//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
					break;
	            default:
	                System.out.println("LogStringFormaterUtilZZZ.computeLinePartInLog(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }				          
		}//end main: 
		return sReturn;
	}
	
	/** Nur das unformatierte SEPARATOR-ZEICHEN
	 * @param ienumFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static String getLinePartInLog(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		
		main:{
//			Class classObj = null;		
//			if(classObjIn==null) {
//				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;					
//			}else {
//				classObj = classObjIn;
//			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();

			
			String sLog = "";
	        switch (ienumFormatLogString.getFactor()) {
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:	            	
            	  sReturn = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));
            	  break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	sMessageSeparator = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	                 
		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING: 
	            	sReturn = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING:
	            	//ByControl?
	                //  sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	 
	            	  sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	           
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML:
	            	//ByControl?
	               // sFormat = this.getHashMapFormatPositionString().get(
	                //        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));
	            	sFormat = StringFormatManagerUtilZZZ.getHashMapFormatPositionStringAll().get(new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sLog + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR04, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
		        	//Das ist die Spalte vor dem ersten Separator, also der nicht vorhandene erste Separator
		        	break;
	            case IStringFormatZZZ.iFACTOR_NULL_STRING:
					//NULL STRING WERT, moeglich aber hier nicht verwendet			
					break;
	            case IStringFormatZZZ.iFACTOR_LINENEXT_STRING:
					//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
					break;
	            default:
	                System.out.println("LogStringFormaterUtilZZZ.getLinePartInLog(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }				          
		}//end main: 
		return sReturn;
	}

	
}
