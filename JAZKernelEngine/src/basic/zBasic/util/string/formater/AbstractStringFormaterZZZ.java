package basic.zBasic.util.string.formater;

import static basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ.sENUMNAME;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_LINENEXT_STRING;
import static basic.zBasic.util.string.formater.IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IReflectCodeZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.reflection.position.TagTypeFileNameZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeLineNumberZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZ;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZ;
import basic.zBasic.util.datatype.enums.EnumMappedLogStringFormatAvailableHelperZZZ;
import basic.zBasic.util.datatype.longs.LongZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringAnalyseUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.math.PrimeNumberZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zBasic.xml.tagtype.TagByTypeZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractStringFormaterZZZ extends AbstractObjectWithFlagZZZ implements IStringFormaterZZZ, IStringFormatZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Globale Objekte ---
	//MERKE: Alles volatile, damit es über mehrere Threads gleich bleibt.
	protected volatile HashMap<Integer,String>hmFormatPositionString=null;
	
	//Das Fomat
	protected volatile IEnumSetMappedStringFormatZZZ[]ienumaMappedFormat=null;
	
	//Der LogString-Index - also von den reinen String ( nicht ggfs. hinzugefuegte XML Strings wie von ReflectCodeZZZ.getPositionCurrent() )
	//Hier als Array aller schon benutzter "einfacher" Strings. Das wird gemacht, damit die XML Strings auch weiterhin bei jeder Operation beruecksichtigt werden können.
	protected volatile ArrayListUniqueZZZ<Integer>listaintStringIndexRead=null;
	
	//######################
	//### KONSTRUKTOR
	public AbstractStringFormaterZZZ() throws ExceptionZZZ{		
		super();
		AbstractStringFormaterNew_(null);
	}
	
	public AbstractStringFormaterZZZ(ArrayListUniqueZZZ<Integer>listaintStringIndexRead) throws ExceptionZZZ{		
		super();
		AbstractStringFormaterNew_(listaintStringIndexRead);
	}
	
		
	private boolean AbstractStringFormaterNew_(ArrayListUniqueZZZ<Integer>listaintStringIndexRead) throws ExceptionZZZ{
		this.listaintStringIndexRead = listaintStringIndexRead;
		return true;
	}
	
	//### GETTER / SETTER
//	@Override
//	public boolean hasStringJustifierPrivate() throws ExceptionZZZ{
//		if(this.objStringJustifier==null) {
//			return false;
//		}else {
//			return true;
//		}		
//	}
	
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			boolean btemp1 = this.resetStringIndexRead();

			//!!! nur resetten, wenn es ein eigener String Justifier ist
//			boolean btemp2 = false;
//			if(this.hasStringJustifierPrivate()) {
//				btemp2 = this.getStringJustifier().reset();
//			}
//			
//			bReturn = btemp1 | btemp2;
			
			bReturn = btemp1;
		}//end main:
		return bReturn;
	}
	
	//##########################################################################
	//### aus IStringFormatComputerZZZ
	@Override 
	public boolean resetStringIndexRead() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.getStringIndexReadList().size()>=1) {
				bReturn = true;
			}
			this.getStringIndexReadList().clear();	
		}//end main:
		return bReturn;
	}
	
	@Override 
	public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ{
		if(this.listaintStringIndexRead==null) {
			this.listaintStringIndexRead = new ArrayListUniqueZZZ<Integer>();
		}
		return this.listaintStringIndexRead;
	}
	
	@Override
	public void setStringIndexRead(ArrayListUniqueZZZ<Integer> listaintStringIndexRead) throws ExceptionZZZ{
		this.listaintStringIndexRead = listaintStringIndexRead;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(ienumFormatLogString);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(obj, ienumFormatLogString);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(classObj, ienumFormatLogString);
	}
	
	
	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(sLogs);
	}
	
	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(obj, ienumaFormatLogString, sLogs);		
	}
	
	
	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(classObj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(ienumaFormatLogString, sLogs);
	}

	
	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(obj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(classObj, ienumaFormatLogString, sLogs);
	}


	//################################################
	
	private ArrayListZZZ<String> computeUsingFormat_ArrayList__(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();
		main:{
			String sReturn = null; String sValue = null;
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString ) {
				sValue = this.computeUsingFormat__(classObj, null, ienumFormatLogString, sLogs);
				if(sValue!=null) {
					if(sReturn!=null) {
						sReturn = sReturn + sValue;
					}else {
						sReturn = sValue;
					}
				}
			}
			if(sReturn!=null) {
				listasReturn.add(sReturn);
			}
		}//end main:
		return listasReturn;
	}
	
	
	private String computeUsingFormat__(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return this.computeUsingFormat__(classObj, null, ienumFormatLogString, sLogs);	
	}
	
	private String computeUsingFormat__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLogString,  IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
			
			boolean bFormatUsingControl = StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl & bFormatUsingString) {
				//Hier werden Strings mit dem Steuerungszeichen mitverarbeitet
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByControl__(classObj, ienumFormatLogString, sLogs[iStringIndexToRead]);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}else {
					sReturn = this.computeByControl__(classObj, ienumFormatLogString, sLogs);
				}	
				
			}else if(bFormatUsingControl & !bFormatUsingString) {
				//Hier wird nur das Steuerungszeichen ohne String verarbeitet
				sReturn = this.computeByControl__(classObj, ienumFormatLogString, sLogs);					
			}else if(bFormatUsingObject) {
				sReturn = this.computeUsingFormatByObject__(classObj, ienumFormatLogString);				
			}else if(bFormatUsingString & !bFormatUsingControl) {				
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead); //Durchlaufe die Log-Einträge auf der Suche nach dem ersten Eintrag, der noch nich geschrieben wurde.
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByString__(classObj, sLogs[iStringIndexToRead], ienumFormatLogString);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}							
										
			}else if(bFormatUsingStringXml) {			
				sReturn = this.computeByStringXml__(classObj, ienumFormatLogString, sLogs);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_Jagged__(classObj, hmLogString, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}
	
	private String computeUsingFormat__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog,  IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj=null;
			if(classObjIn == null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
									
			boolean bFormatUsingControl = StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl) {		
				String sLog = hmLog.get(ienumFormatLogString);
				sReturn = this.computeByControl__(classObj, ienumFormatLogString, sLog);
			}else if(bFormatUsingObject) {
				sReturn = this.computeUsingFormatByObject__(classObj, ienumFormatLogString);			
			}else if(bFormatUsingString) {	
				String sLog = hmLog.get(ienumFormatLogString);
				sReturn = this.computeByString__(classObj, sLog, ienumFormatLogString);						
			}else if(bFormatUsingStringXml) {
				String sLog = hmLog.get(ienumFormatLogString); //Könnte ja auch ein etwas umfangreicherer Tag sein und man fischt daraus den passenden raus.
				sReturn = this.computeByStringXml__(classObj, sLog, ienumFormatLogString);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_Jagged__(classObj, hmLog, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}
	
//	private String computeByControl_(Class classObjIn, IEnumSetMappedLogStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{
//			Class classObj = null;		
//			if(classObjIn==null) {
//				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;					
//			}else {
//				classObj = classObjIn;
//			}
//			
//			if(ienumFormatLogString == null) {
//				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;				
//			}
//			if (!LogStringFormaterUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
//		    if (LogStringFormaterUtilZZZ.isFormatUsingString(ienumFormatLogString)) break main;
//		    					   
//			String sFormat=null; 
//			String sMessageSeparator=null;
//			
//			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
//			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
//		
//	        switch (ienumFormatLogString.getFactor()) {
//	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
//	            	//ByControl?
//	                  sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
//	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//	                  
//	                  sReturn = sMessageSeparator;
//	                break;
//	                
//	            case ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
//	            	//ByControl?
//	                sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
//	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
//	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//	                  
//
//		        	ITagByTypeZZZ objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
//		        	String sMessageSeparatorTag = objTagMessageSeparator.getElementString();
//		            
//	                sReturn = sMessageSeparatorTag;
//	                break;
//	            
//	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
//	            	//ByControl?
//	                  sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                    
//	                  sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
//	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//	                  
//	                  sReturn = sMessageSeparator;
//	                break;
//	            case ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
//	            	//ByControl?
//	                sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));	                    
//	                sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_01_DEFAULT);
//	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//	                  
//
//		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
//		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
//		            
//	                sReturn = sMessageSeparatorTag;
//	                break;
//	            
//		        case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
//		        	//ByControl?
//		            sFormat = this.getHashMapFormatPositionString().get(
//		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));	                    
//		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
//		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//		              
//		            sReturn = sMessageSeparator;
//		            break;
//		        case ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
//		        	//ByControl?
//		            sFormat = this.getHashMapFormatPositionString().get(
//		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));	                    
//		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_02_DEFAULT);
//		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//		              
//		
//		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
//		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
//		            
//		            sReturn = sMessageSeparatorTag;
//		            break;
//		        case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
//		        	//ByControl?
//		              sFormat = this.getHashMapFormatPositionString().get(
//		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));	                    
//		              sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
//		              sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//		              
//		            sReturn = sMessageSeparator;
//		            break;
//		        case ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
//		        	//ByControl?
//		            sFormat = this.getHashMapFormatPositionString().get(
//		                    new Integer(ILogStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));	                    
//		            sMessageSeparator = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_03_DEFAULT);
//		            sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
//		              
//		
//		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
//		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
//		            
//		            sReturn = sMessageSeparatorTag;
//		            break;
//		        default:
//		            System.out.println("AbstractLogStringFormaterZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
//		                    + ienumFormatLogString.getFactor());
//		            break;
//		    }	
//		}//end main:
//		return sReturn;
//	}

	
	//Merke: Das String-Array wird nur gebraucht um zu prüfen, ob es überhaupt einen Kommentar gibt.
	//       Falls es keinen Kommentar gibt, dann wird auch kein Kommentarseparator geschrieben.   
	private String computeByControl__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String sLogIn) throws ExceptionZZZ {
		String[]saLog = new String[1];
		saLog[0]=sLogIn;
		return computeByControl__(classObjIn,ienumFormatLogString,saLog );
	}
	
	private String computeByControl__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String[] saLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
            
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = classObjIn;
			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			String sLogTotal= null;
			
			//Rechne aus, ob es überhaupt einen gueltigen Kommentar gibt, der keine XML-Angabe ist.
			if(saLogIn!=null) {
				for(String sLog : saLogIn) {
					String sOuter = XmlUtilZZZ.findTextOuterXml(sLog);
					if(!StringZZZ.isEmpty(sOuter)) {				
						//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
						//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
						//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
						//Darum entfernen wir dies ggfs.															
						sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
						if(StringZZZ.isEmpty(sOuter)) break main;
						sLogTotal = StringZZZ.joinAll(sLogTotal, sOuter);
					}else {
						//Also: sOuter ist Leerstring oder Null UND es nicht explizit ein XML, nur dann den String übernehme
						boolean bContainsXml = XmlUtilZZZ.isXmlContained(sLog);	
						if(bContainsXml) {
							//mache nix
						}else {
							sLogTotal = StringZZZ.joinAll(sLogTotal, sOuter);
						}
					}	
				}//end for
			}else {
				sLogTotal="";
			}
			
			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
			//if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			
	        switch (ienumFormatLogString.getFactor()) {
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	            	//Aber nur, wenn es ueberhaupt einen Kommentar gibt (und das kein XML Kommentar ist, wie im der Positionsangabe)
	            	if(!StringZZZ.isEmpty(sLogTotal)) {
		            
		                sFormat = this.getHashMapFormatPositionString().get(
		                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
		                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
		                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		                  
		                sReturn = sMessageSeparator;
	            	}
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	            	//Aber nur, wenn es ueberhaupt einen Kommentar gibt (und das kein XML Kommentar ist, wie im der Positionsangabe)
	            	if(!StringZZZ.isEmpty(sLogTotal)) {
		                sFormat = this.getHashMapFormatPositionString().get(
		                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
		                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
		                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		                  
	
			        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
			        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
			            
		                sReturn = sMessageSeparatorTag;
	            	}
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR04, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

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
	                System.out.println("AbstractStringFormaterZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	
	private String computeByControl__(IEnumSetMappedStringFormatZZZ ienumFormatLogString, String sLogIn) throws ExceptionZZZ {
		String[]saLog = new String[1];
		saLog[0]=sLogIn;
		return computeByControl__(ienumFormatLogString,saLog );
	}
	
	private String computeByControl__(IEnumSetMappedStringFormatZZZ ienumFormatLogString, String[] saLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			ITagByTypeZZZ objTagMessageSeparator = null; String sMessageSeparatorTag = null;
            			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
		    					   
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sMessageSeparator=null;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			String sLogTotal= null;
			
			//Rechne aus, ob es überhaupt einen gueltigen Kommentar gibt, der keine XML-Angabe ist.
			if(saLogIn!=null) {
				for(String sLog : saLogIn) {
					String sOuter = XmlUtilZZZ.findTextOuterXml(sLog);
					if(!StringZZZ.isEmpty(sOuter)) {				
						//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
						//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
						//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
						//Darum entfernen wir dies ggfs.															
						sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
						if(StringZZZ.isEmpty(sOuter)) break main;
						sLogTotal = StringZZZ.joinAll(sLogTotal, sOuter);
					}else {
						//Also: sOuter ist Leerstring oder Null UND es nicht explizit ein XML, nur dann den String übernehme
						boolean bContainsXml = XmlUtilZZZ.isXmlContained(sLog);	
						if(bContainsXml) {
							//mache nix
						}else {
							sLogTotal = StringZZZ.joinAll(sLogTotal, sOuter);
						}
					}	
				}//end for
			}else {
				sLogTotal="";
			}
			
			//Ziel ist es eine unnoetigerweise erzeugte Leerzeile mit KommentarSeparator zu verhindern.
			//if(sLog==null)break main; //Ein explizit uebergebener Leerstring gilt aber.
			
			
	        switch (ienumFormatLogString.getFactor()) {
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
	            	//ByControl?
	            	//Aber nur, wenn es ueberhaupt einen Kommentar gibt (und das kein XML Kommentar ist, wie im der Positionsangabe)
	            	if(!StringZZZ.isEmpty(sLogTotal)) {
		            
		                sFormat = this.getHashMapFormatPositionString().get(
		                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
		                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
		                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		                  
		                sReturn = sMessageSeparator;
	            	}
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
	            	//ByControl?
	            	//Aber nur, wenn es ueberhaupt einen Kommentar gibt (und das kein XML Kommentar ist, wie im der Positionsangabe)
	            	if(!StringZZZ.isEmpty(sLogTotal)) {
		                sFormat = this.getHashMapFormatPositionString().get(
		                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML));	                    
		                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);
		                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
		                  
	
			        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATORMESSAGE, sMessageSeparator);
			        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
			            
		                sReturn = sMessageSeparatorTag;
	            	}
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL01SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_01_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR01, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL02SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_02_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR02, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL03SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_03_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR03, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROL04SEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_04_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.SEPARATOR04, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING:
	            	//ByControl?
	                  sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_STRING));	                    
	                  sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                  sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  
	                  sReturn = sMessageSeparator;
	                break;
	            case IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML:
	            	//ByControl?
	                sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_CONTROLPOSITIONSEPARATOR_XML));	                    
	                sMessageSeparator = String.format(sFormat, IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT);
	                sMessageSeparator = sPrefixSeparator + sMessageSeparator + sPostfixSeparator;
	                  

		        	objTagMessageSeparator = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITION_IN_FILE, sMessageSeparator);
		        	sMessageSeparatorTag = objTagMessageSeparator.getElementString();
		            
	                sReturn = sMessageSeparatorTag;
	                break;	
	            case  IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
		        	//Das ist die Spalte vor dem ersten Separator, also der nicht vorhandene erste Separator
		        	break;
	            case IStringFormatZZZ.iFACTOR_NULL_STRING:
					//NULL STRING WERT, moeglich aber hier nicht verwendet			
					break;
	            case IStringFormatZZZ.iFACTOR_LINENEXT_STRING:
					//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
					break;   
	            default:
	                System.out.println("AbstractStringFormaterZZZ.computeByControl_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor=" + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	private ArrayListZZZ<String> computeLinePartByObject_ArrayList__(Class classObjIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<String>listasReturn = new ArrayListZZZ<String>();
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;						
			}else {
				classObj = classObjIn;
			}
			
			
			if(ienumaFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			
			String sReturn = null; String sValue = null;
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
				sValue = computeUsingFormatByObject__(classObj, ienumFormatLogString);
				if(sValue!=null) {
					if(sReturn!=null) {
						sReturn = sReturn + sValue;
					}else {
						sReturn = sValue;
					}
				}
			}
			if(sReturn!=null) {
				listasReturn.add(sReturn);
			}
		}//end main:
		return listasReturn;
	}
	
	private String computeUsingFormat__(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLogString,  IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
						
			boolean bFormatUsingControl = StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl & bFormatUsingString) {
				//Hier werden Strings mit dem Steuerungszeichen mitverarbeitet
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByString__(ienumFormatLogString, sLogs[iStringIndexToRead]);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}else {
					sReturn = this.computeUsingFormat__(ienumFormatLogString, sLogs);
				}	
				
			}else if(bFormatUsingControl & !bFormatUsingString) {
				//Hier wird nur das Steuerungszeichen ohne String verarbeitet
				sReturn = this.computeUsingFormat__(ienumFormatLogString, sLogs);					
			}else if(bFormatUsingObject) {
				sReturn = this.computeUsingFormat__(ienumFormatLogString);				
			}else if(bFormatUsingString & !bFormatUsingControl) {				
				sReturn = this.computeUsingFormat__(ienumFormatLogString, sLogs);				
			}else if(bFormatUsingStringXml) {			
				sReturn = this.computeByStringXml__(ienumFormatLogString, sLogs);
			}else if(bFormatUsingStringHashMap) {
				sReturn = this.computeByStringHashMap_Jagged__(hmLogString, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}
	
	private String computeUsingFormat__(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//!!! Verwende hier nur einfache Methoden und keine Methoden, die wiederum Logging verwenden, sonst Endlosschleifengefahr !!!
		
		String sReturn = null;
		main:{		 
				
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
	    
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sDate = null;
			GregorianCalendar objCalendar=null; Integer intDateYear = null; Integer intDateMonth = null; Integer intDateDay = null; Integer intTimeHour = null; Integer intTimeMinute = null;

			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			
	        switch (ienumFormatLogString.getFactor()) {
	            //case IStringFormatZZZ.iFACTOR_CLASSNAME_STRING:
	            //case IStringFormatZZZ.iFACTOR_CLASSNAME_XML:	               
	            //case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING:	               
	            //case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML:	               
	            //case IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING:	              
	            //case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML:
	              
	            case IStringFormatZZZ.iFACTOR_DATE_STRING:
	                //objCalendar = new GregorianCalendar();
	                sDate = DateTimeZZZ.computeTimestampStringFormatedDefault();
	            	sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_DATE_STRING));
	                sReturn = String.format(sFormat, sDate);
                    sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                break;

	            case IStringFormatZZZ.iFACTOR_DATE_XML:
	                //objCalendar = new GregorianCalendar();
	                sDate = DateTimeZZZ.computeTimestampStringFormatedDefault();
	                sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_DATE_STRING));
	                sDate = String.format(sFormat, sDate);
                    sDate = sPrefixSeparator + sDate + sPostfixSeparator;
                    
                    ITagByTypeZZZ objTagDate = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.DATE, sDate);
        	 		String sDateTag = objTagDate.getElementString();
                    sReturn = sDateTag;
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_THREADID_STRING:
	                if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_THREADID_STRING));
	                    long lngThreadID = Thread.currentThread().getId();
	                    sReturn = String.format(sFormat, lngThreadID);
                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_THREADID_XML:
	                if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_THREADID_XML));
	                    long lngThreadId = Thread.currentThread().getId();     
	        			String sThreadId = LongZZZ.longToString(lngThreadId);
	        			sThreadId = String.format(sFormat, sThreadId);
	        			sThreadId = sPrefixSeparator + sThreadId + sPostfixSeparator;
	        			ITagByTypeZZZ objTagThreadId = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID, sThreadId);
	        			String sThreadIdTag = objTagThreadId.getElementString();
	                    
	                    sReturn = sThreadIdTag;                        
	                }
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
	                System.out.println("AbstractStringFormaterZZZ.computeByObject_(...): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	private String computeUsingFormat__(IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
						
			boolean bFormatUsingControl = StringFormatManagerUtilZZZ.isFormatUsingControl(ienumFormatLogString);
			boolean bFormatUsingObject = StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString);
			boolean bFormatUsingString = StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString);
			boolean bFormatUsingStringXml = StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumFormatLogString);
			boolean bFormatUsingStringHashMap = StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumFormatLogString);
									
			//Merke: Das Log-String-Array kann nur hier verarbeitet werden.
			//       Es in einer aufrufenden Methode zu verarbeitet, wuerde ggfs. mehrmals .computeByObject_ ausfuehren, was falsch ist.
			if(bFormatUsingControl & bFormatUsingString) {
				//Hier werden Strings mit dem Steuerungszeichen mitverarbeitet
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead);
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByControl__(ienumFormatLogString, sLogs[iStringIndexToRead]);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}else {
					sReturn = this.computeByControl__(ienumFormatLogString, sLogs);
				}	
				
			}else if(bFormatUsingControl & !bFormatUsingString) {
				//Hier wird nur das Steuerungszeichen ohne String verarbeitet
				sReturn = this.computeByControl__(ienumFormatLogString, sLogs);					
			}else if(bFormatUsingObject) {
				sReturn = this.computeUsingFormat__(ienumFormatLogString);				
			}else if(bFormatUsingString & !bFormatUsingControl) {				
				if(!StringArrayZZZ.isEmpty(sLogs)) {
					ArrayListUniqueZZZ<Integer>listaIndexRead=this.getStringIndexReadList();					
					for(int iStringIndexToRead=0; iStringIndexToRead <= sLogs.length-1; iStringIndexToRead++) {					
						
						Integer intIndex = new Integer(iStringIndexToRead); //Durchlaufe die Log-Einträge auf der Suche nach dem ersten Eintrag, der noch nich geschrieben wurde.
						if(!listaIndexRead.contains(intIndex)){
							String sValue = this.computeByString__(ienumFormatLogString, sLogs[iStringIndexToRead]);
							if(sValue!=null) {								
								if(sReturn!=null) {
									sReturn = sReturn + sValue;
								}else {
									sReturn = sValue;
								}
								this.getStringIndexReadList().add(intIndex);
								break; //nach der ersten Verarbeitung aus der Schleife raus!!!
							}														
						}
					}
				}							
										
			}else if(bFormatUsingStringXml) {			
				sReturn = this.computeByStringXml__(ienumFormatLogString, sLogs);
			}else if(bFormatUsingStringHashMap) {
				//mache nix     sReturn = this.computeByStringHashMap_Jagged__(hmLogString, ienumFormatLogString);
			}else {
				//mache nix				
			}									
		}//end main:
		return sReturn;
	}

	
	private String computeUsingFormatByObject__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//!!! Verwende hier nur einfache Methoden und keine Methoden, die wiederum Logging verwenden, sonst Endlosschleifengefahr !!!
		
		String sReturn = null;
		main:{
		 	Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;						
			}else {
				classObj = classObjIn;
			}
				
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingObject(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
	    
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sDate = null;
			//GregorianCalendar d=null; Integer iDateYear = null; Integer iDateMonth = null; Integer iDateDay = null; Integer iTimeHour = null; Integer iTimeMinute = null;

			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
			
	        switch (ienumFormatLogString.getFactor()) {
	            case IStringFormatZZZ.iFACTOR_CLASSNAME_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSNAME_STRING));
	                        sReturn = String.format(sFormat, classObj.getName());
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_CLASSNAME_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSNAME_XML));
	                        String sClassname = String.format(sFormat, classObj.getName());
	                        sClassname = sPrefixSeparator + sClassname + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME, sClassname);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	    	                break;
	                    }
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING));
	                        sReturn = String.format(sFormat, classObj.getSimpleName());
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() + 
	                            "In diesem Format ist die Ausgabe des Klassennamens per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_STRING));
	                        String sClassnameSimple = String.format(sFormat, classObj.getSimpleName());
	                        sClassnameSimple = sPrefixSeparator + sClassnameSimple + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.CLASSNAME, sClassnameSimple);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	                    }
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
//	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
//	                        String sFileName = classObj.getSimpleName() + ".java";
//	                        //NEIN: ENDLOSSCHLEIFE weil darin ebenfalls geloggt wird.
//	                        //String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
//	                        //ALSO: Einfacher halten.
//	                        String sFilePathTotal = sDirectory + StringZZZ.char2String(IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR) + sFileName;
	                        
	                        String sFilePathTotal = ReflectCodeZZZ.getClassFilePath(classObj);
	                        sReturn = String.format(sFormat, sFilePathTotal);
	                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                    }
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML:
	                if (classObj == null) {
	                    // Nichts tun
	                } else {
	                    if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_CLASSNAME)) {
	                        System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                            "In diesem Format ist die Ausgabe des Klassennamens (also auch des Dateinamens) per gesetztem Flag unterbunden.");
	                    } else {
	                        sFormat = this.getHashMapFormatPositionString().get(
	                            new Integer(IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING));
	                        //sReturn = String.format(sFormat, StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS) + FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS + classObj.getSimpleName() + ".java");
	                        String sDirectory = StringZZZ.replace(classObj.getPackage().getName(),".",FileEasyZZZ.sDIRECTORY_SEPARATOR_WINDOWS);
	                        String sFileName = classObj.getSimpleName() + ".java";
	                        //NEIN: ENDLOSSCHLEIFE weil darin ebenfalls geloggt wird.
	                        //String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectory, sFileName);
	                        //ALSO: Einfacher halten.
	                        String sFilePathTotal = sDirectory + StringZZZ.char2String(IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR) + sFileName;
	                        String sClassFileName = String.format(sFormat, sFilePathTotal);
	                        sClassFileName = sPrefixSeparator + sClassFileName + sPostfixSeparator;
	                        
	                        ITagByTypeZZZ objTagClassname = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.FILENAME, sClassFileName);
	            	 		String sClassnameTag = objTagClassname.getElementString();
	                        sReturn = sClassnameTag;
	                    }
	                }
	                break;

	            case IStringFormatZZZ.iFACTOR_DATE_STRING:
	                //d = new GregorianCalendar();
	                sDate = DateTimeZZZ.computeTimestampStringFormatedDefault();
	                sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_DATE_STRING));
	                sReturn = String.format(sFormat, sDate);
                    sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                break;

	            case IStringFormatZZZ.iFACTOR_DATE_XML:
	                //d = new GregorianCalendar();
	                sDate = DateTimeZZZ.computeTimestampStringFormatedDefault();
	                sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_DATE_STRING));
	                sDate = String.format(sFormat, sDate);
                    sDate = sPrefixSeparator + sDate + sPostfixSeparator;
                    
                    ITagByTypeZZZ objTagDate = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.DATE, sDate);
        	 		String sDateTag = objTagDate.getElementString();
                    sReturn = sDateTag;
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_THREADID_STRING:
	                if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_THREADID_STRING));
	                    long lngThreadID = Thread.currentThread().getId();
	                    sReturn = String.format(sFormat, lngThreadID);
                        sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
	                }
	                break;
	            case IStringFormatZZZ.iFACTOR_THREADID_XML:
	                if (this.getFlag(IStringFormaterZZZ.FLAGZ.EXCLUDE_THREAD)) {
	                    System.out.println(ReflectCodeZZZ.getPositionCurrent() +
	                        "In diesem Format ist die Ausgabe der ThreadId per gesetztem Flag unterbunden.");
	                } else {
	                    sFormat = this.getHashMapFormatPositionString().get(
	                        new Integer(IStringFormatZZZ.iFACTOR_THREADID_XML));
	                    long lngThreadId = Thread.currentThread().getId();     
	        			String sThreadId = LongZZZ.longToString(lngThreadId);
	        			sThreadId = String.format(sFormat, sThreadId);
	        			sThreadId = sPrefixSeparator + sThreadId + sPostfixSeparator;
	        			ITagByTypeZZZ objTagThreadId = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.THREADID, sThreadId);
	        			String sThreadIdTag = objTagThreadId.getElementString();
	                    
	                    sReturn = sThreadIdTag;                        
	                }
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
	                System.out.println("AbstractStringFormaterZZZ.computeByObject_(Class ...,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumFormatLogString.getFactor());
	                break;
	        }			    
		}//end main:
		return sReturn;
	}
	
	private String computeByObject_Justified__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}else {
				classObj = classObjIn;
			}
			
			sReturn = this.computeUsingFormatByObject__(classObj, ienumFormatLogString);

			//20251128: Der MessageSeparator ist nun eine eigene Formatanweisung
			//Damit hiervon ggfs. folgende Kommentare abgegrenzt werden koennen
			//sReturn = sReturn  + ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;
			
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //ABER: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			sReturn = this.getStringJustifier().justifyInfoPart(sReturn);
		}//end main:
		return sReturn;
	}
	
	
	private String computeByString__(Class classObjIn, String sLogIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}else {
				classObj = classObjIn;
			}
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			
			
			//+++ Pruefe darauf, ob es ein XML-String ist. Wenn ja... Abbruch. Ansonsten wird ggfs. <filepositioncurrent> als normaler Logeintrag behandelt.
			//    Dieser String wird naemlich über das Array saLog gerettet und uebergeben ( aus der entsprechenden ermittelnden ReflectionZZZ Methode ).
			
			//Nein, dadurch wird ggfs. Text vor oder nach dem XML unterschlagen.
			//boolean bXml = XmlUtilZZZ.isXmlContained(sLogIn);
			//if(bXml) break main; //hier werden nur einfach Strings verarbeitet und keine XML Strings...
			
			//Statt dessen
			//+++ Pruefe darauf, ob Text vor oder hinter XML steht (oder alles, wenn kein XML).
			String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
			//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
			//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
			//Darum entfernen wir dies ggfs.
			sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			
			
			//+++++++++++++++++++++++++++					
			String sLog = sOuter;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
						
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sLogTag=null;
			
			switch(ienumFormatLogString.getFactor()) {		
			case IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING));
								
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				
//				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
			
			case IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING:												
			 	sFormat = this.getHashMapFormatPositionString().get(
                     new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING));
			 	
			 	sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType01 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE01, sLog);
     			sLogTag = objTagStringType01.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			case IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);				
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType02 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE02, sLog);
     			sLogTag = objTagStringType02.getElementString();
                 
                sReturn = sLogTag; 				
				break;
				
			case IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
			
			case IStringFormatZZZ.iFACTOR_STRINGTYPE03_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType03 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE03, sLog);
     			sLogTag = objTagStringType03.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			
			case iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_IN_FILE_IDENTIFIER, ReflectCodeZZZ.sPOSITION_IN_FILE_IDENTIFIER);				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				
				
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(stemp + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT + stemp, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				stemp = StringZZZ.left(stemp + ReflectCodeZZZ.sPOSITION_LINENR_IDENTIFIER, ReflectCodeZZZ.sPOSITION_LINENR_IDENTIFIER);
				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				//Neu 20260204 nun wird ja jeder Separator per Konfiguration reingerechnet.
				//             Also reduziert sich das auf die Klammern um (FileEasyZZZ.java:1911), was ein Ausdruck ist, damit die Position in Eclipse Clickbar ist.
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);	
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT + stemp, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				
				sLog = stemp;
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;
				break;
			case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
				//IST GGF. DER LINKE RAND, AN STELLE EINES SEPARATORS
				break;
			case IStringFormatZZZ.iFACTOR_NULL_STRING:
				//NULL STRING WERT, moeglich aber hier nicht verwendet			
				break;
			case IStringFormatZZZ.iFACTOR_LINENEXT_STRING:
				//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
				break;
			default:
				System.out.println("AbstractStringFormaterZZZ.computeByString_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
				break;					
			}				
						
		}//end main:
		return sReturn;		
	}
	
	private String computeByStringXml__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumMappedFormat, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				if(sReturn==null) {
					sReturn = this.compute(classObj, ienumMappedFormat);
				}else {
					sReturn = sReturn + this.compute(classObj, ienumMappedFormat);
				}
				break main;
			}
			
			//###### Mit Strings, alle durchsuchen.			
			for(String sLog:sLogs) {
				if(sReturn==null) {
					sReturn = this.computeByStringXml__(classObj, sLog, ienumMappedFormat);
				}else {
					sReturn = sReturn + this.computeByStringXml__(classObj, sLog, ienumMappedFormat);
				}
			}
		}//end main:
		return sReturn;
	}
	
	private String computeByStringXml__(Class classObjIn, String sLogIn, IEnumSetMappedStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();					    

			String sLog=sLogIn;
			ITagTypeZZZ objTagTypePositionCurrent = null; ITagTypeZZZ objTagTypeLineNummer = null; ITagTypeZZZ objTagTypeFileName = null; ITagTypeZZZ objTagTypeFilePosition = null; ITagTypeZZZ objTagTypeMethod = null;
						
			String sTagTemp=null;
			switch(ienumMappedFormat.getFactor()) {
			//#######################################################################
			//### XML AUS DEM XML-STRING WERT ZURUECKGEBEN
			//#######################################################################
			case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFilePosition = new TagByTypeZZZ(objTagTypeFilePosition);
					objTagFilePosition.setValue(sTagTemp);
					sReturn = objTagFilePosition.getElementString();	
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagMethod = new TagByTypeZZZ(objTagTypeMethod);
					objTagMethod.setValue(sTagTemp);
					sReturn = objTagMethod.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagLineNumber = new TagByTypeZZZ(objTagTypeLineNummer);
					objTagLineNumber.setValue(sTagTemp);
					sReturn = objTagLineNumber.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFileName = new TagByTypeZZZ(objTagTypeFileName);
					objTagFileName.setValue(sTagTemp);
					sReturn = objTagFileName.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagPositionCurrent = new TagByTypeZZZ(objTagTypePositionCurrent);
					objTagPositionCurrent.setValue(sTagTemp);
					sReturn = objTagPositionCurrent.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
				
			//#######################################################################
			//### EINFACHEN STRING AUS DEM XML-STRINGWERT ZURUECKGEBEN
			//#######################################################################
			case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_POSITIONCURRENT_STRING_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			default:
				System.out.println("AbstractStringFormaterZZZ.computeByStringXml_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumMappedFormat.getFactor());
				break;					
			}								
		}//end main:
		return sReturn;		
	}
	
	private String computeByStringXml__(IEnumSetMappedStringFormatZZZ ienumMappedFormat, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				if(sReturn==null) {
					sReturn = this.compute(ienumMappedFormat);
				}else {
					sReturn = sReturn + this.compute(ienumMappedFormat);
				}
				break main;
			}
			
			//###### Mit Strings, alle durchsuchen.			
			for(String sLog:sLogs) {
				if(sReturn==null) {
					sReturn = this.computeByStringXml__(ienumMappedFormat, sLog);
				}else {
					sReturn = sReturn + this.computeByStringXml__(ienumMappedFormat, sLog);
				}
			}
		}//end main:
		return sReturn;
	}
	
	private String computeByStringXml__(IEnumSetMappedStringFormatZZZ ienumMappedFormat, String sLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
						
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingStringXml(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();					    

			String sLog=sLogIn;
			ITagTypeZZZ objTagTypePositionCurrent = null; ITagTypeZZZ objTagTypeLineNummer = null; ITagTypeZZZ objTagTypeFileName = null; ITagTypeZZZ objTagTypeFilePosition = null; ITagTypeZZZ objTagTypeMethod = null;
						
			String sTagTemp=null;
			switch(ienumMappedFormat.getFactor()) {
			//#######################################################################
			//### XML AUS DEM XML-STRING WERT ZURUECKGEBEN
			//#######################################################################
			case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagPositionCurrent = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFilePosition = new TagByTypeZZZ(objTagTypeFilePosition);
					objTagFilePosition.setValue(sTagTemp);
					sReturn = objTagFilePosition.getElementString();	
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagMethod = new TagByTypeZZZ(objTagTypeMethod);
					objTagMethod.setValue(sTagTemp);
					sReturn = objTagMethod.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagLineNumber = new TagByTypeZZZ(objTagTypeLineNummer);
					objTagLineNumber.setValue(sTagTemp);
					sReturn = objTagLineNumber.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagFileName = new TagByTypeZZZ(objTagTypeFileName);
					objTagFileName.setValue(sTagTemp);
					sReturn = objTagFileName.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_POSITIONCURRENT_XML_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {
					
					//umgib die Werte noch mit einem Tag...
		            //ITagByTypeZZZ objTagTypeMethod = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.POSITIONCURRENT, sReturn);
					ITagByTypeZZZ objTagPositionCurrent = new TagByTypeZZZ(objTagTypePositionCurrent);
					objTagPositionCurrent.setValue(sTagTemp);
					sReturn = objTagPositionCurrent.getElementString();
					sReturn = sPrefixSeparator + sReturn + sPostfixSeparator;
					
				}			
				break;
				
			//#######################################################################
			//### EINFACHEN STRING AUS DEM XML-STRINGWERT ZURUECKGEBEN
			//#######################################################################
			case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_XML:			
				objTagTypeFilePosition = new TagTypeFilePositionZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFilePosition.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_XML:
				objTagTypeMethod = new TagTypeMethodZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeMethod.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_XML:
				objTagTypeLineNummer = new TagTypeLineNumberZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeLineNummer.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}		
				break;
			case IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_XML:
				objTagTypeFileName = new TagTypeFileNameZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypeFileName.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			case IStringFormatZZZ.iFACTOR_POSITIONCURRENT_STRING_BY_XML:
				objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				sTagTemp = XmlUtilZZZ.findFirstTagValue(sLog, objTagTypePositionCurrent.getTagName());
				if(sTagTemp!=null) {					
					sReturn = sPrefixSeparator + sTagTemp + sPostfixSeparator;					
				}			
				break;
			default:
				System.out.println("AbstractStringFormaterZZZ.computeByStringXml_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumMappedFormat.getFactor());
				break;					
			}								
		}//end main:
		return sReturn;		
	}
	
	
	
	//++++++++++++++++++++++++++++
	private String computeByString__(IEnumSetMappedStringFormatZZZ ienumFormatLogString, String sLogIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			
			if(ienumFormatLogString == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			if (!StringFormatManagerUtilZZZ.isFormatUsingString(ienumFormatLogString)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			
			
			//+++ Pruefe darauf, ob es ein XML-String ist. Wenn ja... Abbruch. Ansonsten wird ggfs. <filepositioncurrent> als normaler Logeintrag behandelt.
			//    Dieser String wird naemlich über das Array saLog gerettet und uebergeben ( aus der entsprechenden ermittelnden ReflectionZZZ Methode ).
			
			//Nein, dadurch wird ggfs. Text vor oder nach dem XML unterschlagen.
			//boolean bXml = XmlUtilZZZ.isXmlContained(sLogIn);
			//if(bXml) break main; //hier werden nur einfach Strings verarbeitet und keine XML Strings...
			
			//Statt dessen
			//+++ Pruefe darauf, ob Text vor oder hinter XML steht (oder alles, wenn kein XML).
			String sOuter = XmlUtilZZZ.findTextOuterXml(sLogIn);
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			//+++ Problem: Wenn '# ' um den XML String stehen, dann wird das fuer eine neue Zeile verwendet
			//    Das wird erzeugt durch ReflectCodeZZZ.getPositionCurrent()
			//    sPOSITION_MESSAGE_SEPARATOR wird explizit dahinter gesetzt.
			//Darum entfernen wir dies ggfs.
			sOuter = StringZZZ.trimRight(sOuter, IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR );
			if(StringZZZ.isEmpty(sOuter)) break main;
			
			
			
			//+++++++++++++++++++++++++++					
			String sLog = sOuter;
			
			String sPrefixSeparator = ienumFormatLogString.getPrefixSeparator();
			String sPostfixSeparator = ienumFormatLogString.getPostfixSeparator();
						
			String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;
			String sLogTag=null;
			
			switch(ienumFormatLogString.getFactor()) {		
			case IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING));
								
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				
//				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
			
			case IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING:												
			 	sFormat = this.getHashMapFormatPositionString().get(
                     new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING));
			 	
			 	sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType01 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE01, sLog);
     			sLogTag = objTagStringType01.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			case IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING:	
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);				
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
				
			case IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType02 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE02, sLog);
     			sLogTag = objTagStringType02.getElementString();
                 
                sReturn = sLogTag; 				
				break;
				
			case IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING));
				
//				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
//				//Merke: Der Position steht im Logstring immer am Anfang
//				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
//				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
//				
//				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;					
				break;
			
			case IStringFormatZZZ.iFACTOR_STRINGTYPE03_XML_BY_STRING:
				sLog = String.format(sFormat, sLog);
				sLog = sPrefixSeparator + sLog + sPostfixSeparator;
				
     			ITagByTypeZZZ objTagStringType03 = TagByTypeFactoryZZZ.createTagByName(TagByTypeFactoryZZZ.TAGTYPE.STRINGTYPE03, sLog);
     			sLogTag = objTagStringType03.getElementString();
                 
                sReturn = sLogTag; 				
				break;
			
			case iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSMETHOD_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_IN_FILE_IDENTIFIER, ReflectCodeZZZ.sPOSITION_IN_FILE_IDENTIFIER);				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				
				
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILELINE_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(stemp + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILENAME_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT + stemp, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				stemp = StringZZZ.left(stemp + ReflectCodeZZZ.sPOSITION_LINENR_IDENTIFIER, ReflectCodeZZZ.sPOSITION_LINENR_IDENTIFIER);
				
				sLog = stemp;
				
				
				//Die Postionsangabe weglassen
				
				//sLogUsed = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
				//sLog = StringZZZ.joinAll(sLeft, sMid, sRight);		
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;				
				break;
				
			case iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP:
				sFormat = this.getHashMapFormatPositionString().get(new Integer(IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_STRING_BY_HASHMAP));
				
				//!!!Aus dem Logstring (der ja immer mit Position uebergeben werden muss) die Position herausrechenen
				//Merke: Der Position steht im Logstring immer am Anfang
				//Merke: So sieht der rohe ReflectCodeZZZ.getPositionCurrent() String aus:
				//Z.B.:  joinFilePathName_ ~ (FileEasyZZZ.java:1911) # wird.........
				//Neu 20260204 nun wird ja jeder Separator per Konfiguration reingerechnet.
				//             Also reduziert sich das auf die Klammern um (FileEasyZZZ.java:1911), was ein Ausdruck ist, damit die Position in Eclipse Clickbar ist.
				
				//Auseinanderbauen
//				sLeft = StringZZZ.left(sLogIn, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER);
//				sMid = StringZZZ.midLeftRightback(sLog, sLeft, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER);
//				sRight = StringZZZ.right(ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER + sLog, ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER); //ReflectCodeZZZ.sPOSITION_MESSAGE_IDENTIFIER davor, falls nur ein String uebergeben wurde, wird trotzdem etwas gefunden
//				
//				//Die Postionsangabe weglassen
//				sRight = StringZZZ.stripLeft(sRight, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
//				sLog = StringZZZ.joinAll(sLeft, sMid, sRight);	
				
				stemp = StringZZZ.left(sLogIn + ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_RIGHT);
				stemp = StringZZZ.right(ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT + stemp, ReflectCodeZZZ.sPOSITION_FILE_IDENTIFIER_LEFT);				
				
				sLog = stemp;
				sLog = String.format(sFormat, sLog);
				sReturn = sPrefixSeparator + sLog + sPostfixSeparator;
				break;
			case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case IStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_XML:
				//SOLLTE HIER NICHT ERSCHEINEN SONDERN EINE EIGENE FORMATKLASSE SEIN computeByControl_(...)
//				//By HashMap?
//				 sFormat = this.getHashMapFormatPositionString().get(
//	                        new Integer(ILogStringFormatZZZ.iFACTOR_CONTROLMESSAGESEPARATOR_STRING));	                    
//	             sReturn = String.format(sFormat, ILogStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT);	               
	              break;
			case IStringFormatZZZ.iFACTOR_CONTROLBORDERLEFT_SEPARATOR:
				//IST GGF. DER LINKE RAND, AN STELLE EINES SEPARATORS
				break;
			case IStringFormatZZZ.iFACTOR_NULL_STRING:
				//NULL STRING WERT, moeglich aber hier nicht verwendet			
				break;
			case IStringFormatZZZ.iFACTOR_LINENEXT_STRING:
				//SOLLTE ZUVOR ALS TRENNER FUER DAS FORMAT-ARRAY VERWENDET WORDEN SEIN UND HIER GARNICHT MEHR AUFTRETEN			
				break;
			default:
				System.out.println("AbstractStringFormaterZZZ.computeByString_(obj, String, IEnumSetMapped): Dieses Format ist nicht in den gueltigen Formaten für einen LogString vorhanden iFaktor="+ienumFormatLogString.getFactor());
				break;					
			}				
						
		}//end main:
		return sReturn;		
	}
	
	
	
	
	
	//######################################
//	private String computeLinesInLog_Justified_(Class classObjIn, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
//		String sReturn = "";
//		main:{	
//		  Class classObj = null;		
//			if(classObjIn==null) {
//				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractLogStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;			
//			}else {
//				classObj = classObjIn;
//			}
//			
//			ArrayListZZZ<String> listasLine = this.computeLinesInLogArrayList_Jagged_(classObj, hmLog);
//			
//			if(listasLine.size()>=2) {
//				//Nun über mehrere Zeilen das machen!!! Einmal hin und wieder zurueck
//				ArrayListZZZ<String>listasLineReversed1 = (ArrayListZZZ<String>) ArrayListUtilZZZ.reverse(listasLine);
//				ArrayListZZZ<String>listasLineReversedJustified1 = new ArrayListZZZ<String>();
//				for(String sLine : listasLineReversed1) {
//					String sLineJustified = this.getStringJustifier().justifyInfoPart(sLine);
//					listasLineReversedJustified1.add(sLineJustified);
//				}
//				
//				ArrayListZZZ<String>listasLineReversed2 = (ArrayListZZZ<String>) ArrayListUtilZZZ.reverse(listasLineReversedJustified1);
//				ArrayListUniqueZZZ<String>listasLineReversedJustified2 = new ArrayListUniqueZZZ<String>();
//				for(String sLine : listasLineReversed2) {
//					String sLineJustified = this.getStringJustifier().justifyInfoPart(sLine);
//					listasLineReversedJustified2.add(sLineJustified);
//				}
//				
//				//Die Zeilen so verbinden, das sie mit einem "System.println" ausgegeben werden können.
//				for(String sLineJustified : listasLineReversedJustified2) {
//					if(sReturn.equals("")){
//						sReturn = sLineJustified;
//					}else {					
//						sReturn = sReturn + StringZZZ.crlf() + sLineJustified;
//					}
//				}							
//			}else {
//				String sLine = listasLine.get(0);
//				if(sLine!=null) {
//					sReturn = this.getStringJustifier().justifyInfoPart(sLine);					
//				}
//			}		
//		}//end main:
//		return sReturn;
//
//	}
	
	private String computeLineInLog__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
				
			//Iteration über die Einträge, die ja in einer Zeile sein sollen, findet darin statt	                  	                  
            sReturn = this.computeByStringHashMap_Jagged__(classObj, hmLog);	                              		
		}//end main:
		return sReturn;

	}
	
	
	
	private ArrayListZZZ<String> computeLinesInLogArrayList_Jagged__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		ArrayListZZZ<String>  listasReturn = new ArrayListZZZ<String>();
		main:{	
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			String sReturn = null; String sValue = null;
			
			//TODOGOON20251117;//Eigentlich müssten hier mit dem .LINENEXT Argument versehen die HashMap gesplittet werden.
			//
			
			//Ermittle in einer Schleife jede Zeile
			//Iteration über die mit LINENEXT gesteuerten Einträge
	        //for (Entry<IEnumSetMappedLogStringFormatZZZ, String> entry : hmLog.entrySet()) {
	        //    IEnumSetMappedLogStringFormatZZZ enumAsKey = entry.getKey();	                   
	            sValue = this.computeLineInLog__(classObj, hmLog);	           	          	           
	            if(sValue!=null) {
					if(sReturn!=null) {
						sReturn = sReturn + sValue;
					}else {
						sReturn = sValue;
					}
				}
			//}
			if(sReturn!=null) {
				listasReturn.add(sReturn);
			}
	        //}
		}//end main:
		return listasReturn;

	}
	
	private String computeByStringHashMap_Jagged__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = "";
		main:{		
		  Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(hm == null) 	break main;
						
			//Der zu verwendende Logteil
			String sLogUsed=null;
						
			//Ermittle in einer Schleife den auszugebenden Teil
			// Iteration über die Einträge
	        for (Entry<IEnumSetMappedStringFormatZZZ, String> entry : hm.entrySet()) {
	            IEnumSetMappedStringFormatZZZ enumAsKey = entry.getKey();	                   
	            sLogUsed = this.computeUsingFormat__(classObj, hm, enumAsKey);	           
	            if(sLogUsed!=null) {
	            	if(StringZZZ.isEmpty(sReturn)) {
	            		sReturn = sLogUsed;
	            	}else {	            		
	            		sReturn = sReturn + sLogUsed;	
	            	}
				}
	        }
	        
	        
		}//end main:
		return sReturn;

	}
	
	private String computeByStringHashMap_Jagged__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLogString, IEnumSetMappedStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			if(hmLogString == null) break main;
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if (!StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();
			
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;	
			
			//TODO: Weil es immer das gleiche ist, scheint es die SWITCH Anweisung eigentlich nicht zu benoetigen.
	        switch (ienumMappedFormat.getFactor()) {
	        	case IStringFormatZZZ.iFACTOR_DATE_XML_BY_HASHMAP:
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	        	case IStringFormatZZZ.iFACTOR_THREADID_XML_BY_HASHMAP:	        		
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	            case IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;					
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP:	            	
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}	            	
	            	break;
	            	
	            case IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_HASHMAP:	            	               
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
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
	                System.out.println("AbstractStringStringZZZ.computeByStringHashMap_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumMappedFormat.getFactor());
	                break;
	        }		
		}//end main:
		return sReturn;
	}
	
	
	private String computeByStringHashMap_Jagged__(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLogString, IEnumSetMappedStringFormatZZZ ienumMappedFormat) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			if(hmLogString == null) break main;
			
			if(ienumMappedFormat == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			if (!StringFormatManagerUtilZZZ.isFormatUsingHashMap(ienumMappedFormat)) break main; // Hier werden also nur Werte errechnet aufgrund des Objekts selbst
		
			String sPrefixSeparator = ienumMappedFormat.getPrefixSeparator();
			String sPostfixSeparator = ienumMappedFormat.getPostfixSeparator();
			
			String sLog=null; String sFormat=null; String sLeft=null; String sMid = null; String sRight=null;	
			
			//TODO: Weil es immer das gleiche ist, scheint es die SWITCH Anweisung eigentlich nicht zu benoetigen.
	        switch (ienumMappedFormat.getFactor()) {
	        	case IStringFormatZZZ.iFACTOR_DATE_XML_BY_HASHMAP:
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	        	case IStringFormatZZZ.iFACTOR_THREADID_XML_BY_HASHMAP:	        		
	        		sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	        		break;
	            case IStringFormatZZZ.iFACTOR_CLASSFILELINE_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case IStringFormatZZZ.iFACTOR_CLASSFILENAME_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;					
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;

	            case IStringFormatZZZ.iFACTOR_CLASSFILEPOSITION_XML_BY_HASHMAP:
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
	                break;
	                
	            case IStringFormatZZZ.iFACTOR_CLASSNAMESIMPLE_XML_BY_HASHMAP:	            	
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}	            	
	            	break;
	            	
	            case IStringFormatZZZ.iFACTOR_CLASSMETHOD_XML_BY_HASHMAP:	            	               
	            	sLog = hmLogString.get(ienumMappedFormat);
	            	if(sLog!=null) {
	            		sLog = sPrefixSeparator + sLog + sPostfixSeparator;
		            	if(sReturn==null) {
		            		sReturn = sLog;
		            	}else {
		            		sReturn = sReturn + sLog;
		            	}
	            	}
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
	                System.out.println("AbstractStringStringZZZ.computeByStringHashMap_(..,..): Dieses Format ist nicht in den gültigen Formaten für einen objektbasierten LogString vorhanden. iFaktor="
	                        + ienumMappedFormat.getFactor());
	                break;
	        }		
		}//end main:
		return sReturn;
	}
	
	
	
	/** Beruecksichtigt .LINENEXT als Steuerkennzeichen und teilt das Array entsprechend auf.
	 * @param classObjIn
	 * @param saLog
	 * @param iStringIndexToReadFromStart
	 * @param ienumaFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
	 */
	private ArrayListZZZ<String> computeLinesInLog_Jagged_ArrayList__(Class<?> classObjIn, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();		
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
						
			IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			
				if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
								
			//###### Splitte das Array der Formatanweisungen auf an der "LINENEXT" STEUERANWEISUNG
			String sReturn = null; String sValue = null;
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitByValue(ienumaFormatLogString, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);			
			for(IEnumSetMappedStringFormatZZZ[] ienumaLine: listaEnumLine){
				sValue = computeLineInLog_Jagged__(classObj, ienumaLine, sLogs);
				if(sValue!=null) { //z.B. werden Zeilen, nur mit Separatoren fuer die Justifier ausgeschlossen.
					if(sReturn!=null) {
						sReturn = sReturn + sValue;
					}else {
						sReturn = sValue;
					}
				}				
			}	
			if(sReturn!=null) {
				listasReturn.add(sReturn);
			}
		}//end main:
		return listasReturn;
	}
	
	private ArrayListZZZ<String> computeLinesInLog_Jagged_ArrayList__(IEnumSetMappedStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();		
		main:{
								
			IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}	
			
				if(ArrayUtilZZZ.isEmpty(ienumaFormatLogString)) {				
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;	
				}
				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
								
			//###### Splitte das Array der Formatanweisungen auf an der "LINENEXT" STEUERANWEISUNG
			String sReturn = null; String sValue = null;
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitByValue(ienumaFormatLogString, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);			
			for(IEnumSetMappedStringFormatZZZ[] ienumaLine: listaEnumLine){
				sValue = computeLineInLog_Jagged__(ienumaLine, sLogs);
				if(sValue!=null) { //z.B. werden Zeilen, nur mit Separatoren fuer die Justifier ausgeschlossen.
					if(sReturn!=null) {
						sReturn = sReturn + sValue;
					}else {
						sReturn = sValue;
					}
				}				
			}	
			if(sReturn!=null) {
				listasReturn.add(sReturn);
			}
		}//end main:
		return listasReturn;
	}
	
	
	private String computeLinesInLog_Jagged__(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			
			sReturn = computeLinesInLog_Jagged__(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	private ArrayList<String> computeLinesInLog_Jagged_ArrayList__(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		ArrayList<String> listasReturn = null;
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			
			listasReturn = computeLinesInLog_Jagged_ArrayList__(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return listasReturn;

	}
	
	
	private String computeLinesInLog_Jagged__(Object obj, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = computeLinesInLog_Jagged__(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Jagged__(Class<?> classObjIn, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListZZZ<String> listasLine = this.computeLinesInLog_Jagged_ArrayList__(classObj, ienumaFormatLogString, sLogs);
			sReturn = ArrayListUtilZZZ.implode(listasLine, StringZZZ.crlf());				
		}//end main:
		return sReturn;
	}
	
	
	private String computeLinesInLog_Jagged__(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		String sReturn = "";
		main:{	
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			ArrayListZZZ<String> listasLine = this.computeLinesInLogArrayList_Jagged__(classObj, hmLog);
			sReturn = ArrayListUtilZZZ.implode(listasLine, StringZZZ.crlf()); 			
		}//end main:
		return sReturn;
	}
	
	
	private String computeLinesInLog_Jagged__(IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{			
			ArrayListZZZ<String> listasLine = this.computeLinesInLog_Jagged_ArrayList__(ienumaFormatLogString, sLogs);
			sReturn = ArrayListUtilZZZ.implode(listasLine, StringZZZ.crlf());				
		}//end main:
		return sReturn;
	}
	
	//++++++++++++++++++++++	
	private String computeLinesInLog_Justified__(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
			ienumaFormatLogString[0] = ienumFormatLogString;
			
			sReturn = computeLinesInLog_Justified__(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	
	private String computeLinesInLog_Justified__(Object obj, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = "";
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = computeLinesInLog_Justified__(classObj, ienumaFormatLogString, sLogs);		
		}//end main:
		return sReturn;

	}
	
	/** .LINENEXT als Steuerkennzeichen wird hier nicht mehr beruecksichtig
	 * @param classObjIn
	 * @param saLog
	 * @param iStringIndexToRead
	 * @param ienumaFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
	 */
	private String computeLineInLog_Jagged__(Class classObjIn, IEnumSetMappedStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
				
			IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
		
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
					String sValue = this.computeLinePartInLog__(classObj, ienumFormatLogString, sLogs);//this.computeByObject_(classObj, ienumFormatLogString);
					if(sValue!=null) {
						if(sReturn==null) {
							sReturn = sValue;
						}else {
							sReturn = sReturn + sValue; 
						}
					}
				}
				break main;
			}
			
						
			//####### Mit Strings
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
				String sValue = this.computeLinePartInLog__(classObj, ienumFormatLogString, sLogs);
				if(sValue!=null) {
					if(sReturn==null) {
						sReturn = sValue;
					}else {
						sReturn = sReturn + sValue; 
					}
				}
			}
			
			//Also eine Zeile, die nur den Kommentartrenner enthaelt ist keine Zeile.			
			//20260124: Vermeide eine Zeile, die nur einen Kommentartrenner enthält
			String[] saCommentSeparatorFormated = StringFormaterUtilZZZ.computeLinePartInLog_ControlSeparatorAny();
			
			//Da Kombinationen moeglich sind und auch die Reihenfolge wichtig ist:
			//Verwende daher einen Ansatz der Stringreduktion (s. ChatGPT 2026-01-24)
			if(sReturn!=null && StringAnalyseUtilZZZ.consistsOnlyOf(sReturn, saCommentSeparatorFormated)){
				sReturn = null;
			}
		}//end main:
		return sReturn;
	}
	
	private String computeLineInLog_Jagged__(IEnumSetMappedStringFormatZZZ[]ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
						
			IEnumSetMappedStringFormatZZZ[]ienumaFormatLogString=null;
			if(ArrayUtilZZZ.isNullOrEmpty(ienumaFormatLogStringIn)) {
				ienumaFormatLogString = this.getFormatPositionsMapped();
				
				if(ArrayUtilZZZ.isNull(ienumaFormatLogString)) {										
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ Array", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
			}else {
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}
		
		
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.
				for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
					String sValue = this.computeLinePartInLog__(ienumFormatLogString, sLogs);//this.computeByObject_(classObj, ienumFormatLogString);
					if(sValue!=null) {
						if(sReturn==null) {
							sReturn = sValue;
						}else {
							sReturn = sReturn + sValue; 
						}
					}
				}
				break main;
			}
			
						
			//####### Mit Strings
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogString) {
				String sValue = this.computeLinePartInLog__(ienumFormatLogString, sLogs);
				if(sValue!=null) {
					if(sReturn==null) {
						sReturn = sValue;
					}else {
						sReturn = sReturn + sValue; 
					}
				}
			}
			
			//Also eine Zeile, die nur den Kommentartrenner enthaelt ist keine Zeile.
			//20260124: Vermeide eine Zeile, die nur einen Kommentartrenner enthält
			String[] saCommentSeparatorFormated = StringFormaterUtilZZZ.computeLinePartInLog_ControlSeparatorAny();
			
			//Da Kombinationen moeglich sind und auch die Reihenfolge wichtig ist:
			//Verwende daher einen Ansatz der Stringreduktion (s. ChatGPT 2026-01-24)
			if(sReturn!=null && StringAnalyseUtilZZZ.consistsOnlyOf(sReturn, saCommentSeparatorFormated)){
				sReturn = null;
			}
		}//end main:
		return sReturn;
	}
		
	
	
	
	//+++++++++++++++++++++++++
	private ArrayListZZZ<String> computeLinePartInLog_ArrayList__(Class classObjIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = new ArrayListZZZ<String>();
		main:{								
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.						
				listasReturn = this.computeLinePartByObject_ArrayList__(classObj, ienumaFormatLogString); 					
				break main;
			}
	
		
			//##### Mit zu verarbeitenden Strings			
			listasReturn = this.computeUsingFormat_ArrayList__(classObj, ienumaFormatLogString, sLogs);				
	}//end main:
	return listasReturn;
}
	
	
	/** .LINENEXT als Steuerkennzeichen wird hier nicht mehr beruecksichtig
	 * @param classObjIn
	 * @param saLog
	 * @param iStringIndexToRead
	 * @param ienumaFormatLogString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 09.11.2025, 08:08:19
	 */
	private String computeLinePartInLog__(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{								
			Class classObj = null;		
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;			
			}else {
				classObj = classObjIn;
			}
			
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.						
				sReturn = this.computeUsingFormatByObject__(classObj, ienumFormatLogString); 					
				break main;
			}
	
		
			//##### Mit zu verarbeitenden Strings			
			sReturn = this.computeUsingFormat__(classObj, ienumFormatLogString, sLogs);				
		}//end main:
		return sReturn;
	}
	
	private String computeLinePartInLog__(IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{								
			
			//###### Ohne irgendeinen String
			if(ArrayUtilZZZ.isNull(sLogs)) {
				//Dann können es immer noch Formatanweisungen vom Typ ILogStringZZZ.iARG_OBJECT darin sein.						
				sReturn = this.computeUsingFormat__(ienumFormatLogString); 					
				break main;
			}
	
		
			//##### Mit zu verarbeitenden Strings			
			sReturn = this.computeUsingFormat__(ienumFormatLogString, sLogs);				
		}//end main:
		return sReturn;
	}


	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//ohne explizite Formatangabe 
	
	//OHNE KLASSEN ODER OBJEKTANGABE MACHT DAS KEINEN SINN		
	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(obj, sLogs);
	}
		
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe
	@Override
	public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(hm);
	}
	
	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(obj, hm);
	}
	
	//##########################################################
	
	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(classObj, sLogs);
	}
	
	//+++ Mit expliziter Angabe zu ILogStringZZZ.iFACTOR_CLASSMETHOD und darin ggfs. der komplette String, aber ohne konkrete Formatsangabe	
	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJagged_(classObj, hmLog);
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setFormatPositionsMapped(IEnumSetMappedStringFormatZZZ[] ienumaMappedFormat) {
		this.ienumaMappedFormat=ienumaMappedFormat;
	}

	@Override
	public IEnumSetMappedStringFormatZZZ[] getFormatPositionsMapped() throws ExceptionZZZ {
		if(ArrayUtilZZZ.isNull(this.ienumaMappedFormat)) {
			this.ienumaMappedFormat = this.getFormatPositionsMappedCustom();
			
			//Wenn im custom nix drin ist, default nehmen
			if(ArrayUtilZZZ.isNull(this.ienumaMappedFormat)) {
				this.ienumaMappedFormat = this.getFormatPositionsMappedDefault();
			}
		}
		return this.ienumaMappedFormat;
	}
	
	
	@Override
	public IEnumSetMappedStringFormatZZZ[] getFormatPositionsMappedDefault() throws ExceptionZZZ {
		
		//Merke: Verwendet wird z.B. ein LogString in dieser Form, den es abzubilden gilt:
		//       In getPositionCurrent() wird schon die ThreadID zum ersten Mal gesetzt. Damit das Log lesbarer wird soll vor dem Status noch der Thread gesetzt werden.
		//       String sLog = ReflectCodeZZZ.getPositionCurrent() + "[Thread: "+lngThreadID + "] Status='"+enumStatus.getName() +"', StatusValue="+bStatusValue+", StatusMessage='" + sStatusMessage +"'";
		
		
		
		//Also Classname und Thread z.B. raus. Das 1. iARGNext ist für getPositionCurrent(), das 2. ARGNext für den Text ab "Status...", das 3. ARGNext als Reserve.
		
		//Kein Array, s. ChatGPT 20260110
		List<?> listaReturn = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(this.getClass(), sENUMNAME);
		
		//Array automatisch aus dem Enum errechnen.
		IEnumSetMappedStringFormatZZZ[] ienumaReturn = (IEnumSetMappedStringFormatZZZ[]) ArrayListUtilZZZ.toArray((ArrayList<?>) listaReturn);
		return ienumaReturn;
	}
	
	@Override
	public abstract IEnumSetMappedStringFormatZZZ[] getFormatPositionsMappedCustom();
	
	//+++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void setHashMapFormatPositionString(HashMap<Integer, String> hmFormatPostionString) {
		this.hmFormatPositionString = hmFormatPostionString;
	}
	
	@Override
	public HashMap<Integer,String>getHashMapFormatPositionString() throws ExceptionZZZ{
		if(this.hmFormatPositionString==null) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringCustom();
		}else if(this.hmFormatPositionString.isEmpty()) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringCustom();			
		}
		
		//Wenn im custom nix drin ist, default nehmen
		if(this.hmFormatPositionString==null) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringDefault();
		}else if(this.hmFormatPositionString.isEmpty()) {
			this.hmFormatPositionString = this.getHashMapFormatPositionStringDefault();
		}
		return this.hmFormatPositionString;
	}
	
	/* (non-Javadoc)
	 * @see basic.zBasic.util.log.ILogStringFormaterZZZ#getHashMapFormatPositionStringDefault()
	 * 
	 * Macht eine HashMap mit dem Enum des Formats als Key 
	 * und dem "Abarbeitungstypen" als Wert
	 */
	@Override
	public HashMap<Integer, String> getHashMapFormatPositionStringDefault() throws ExceptionZZZ {
		HashMap<Integer, String> hmReturn = new HashMap<Integer,String>();
		main:{
			//HashMap automatisch aus dem Enum errechnen.			
			ArrayList<IEnumSetMappedStringFormatZZZ>  listaEnumMappedLogStringFormat = EnumMappedLogStringFormatAvailableHelperZZZ.searchEnumMappedList(this, sENUMNAME);
			for(IEnumSetMappedStringFormatZZZ ienum : listaEnumMappedLogStringFormat) {
				IEnumSetMappedStringFormatZZZ ienumLogString = (IEnumSetMappedStringFormatZZZ) ienum;
				hmReturn.put(new Integer(ienumLogString.getFactor()), ienumLogString.getFormat());
			}				
		}//end main:
		return hmReturn;
	}
	
	@Override 
	public abstract HashMap<Integer,String>getHashMapFormatPositionStringCustom() throws ExceptionZZZ;

	//++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	
	@Override
	public int computeFormatPositionsNumber() throws ExceptionZZZ {
		//FGL 20240421 - experimentell und nicht notwendig, solange ich den Weg der Rueckumwandlung noch nicht kann.
		int iReturn = 0;
		main:{
			IEnumSetMappedStringFormatZZZ[]ienumaMappedFormat = this.getFormatPositionsMapped();
			if(ArrayUtilZZZ.isNull(ienumaMappedFormat))break main;
			
			int iPosition=0;
			for(IEnumSetMappedStringFormatZZZ ienumMappedFormat : ienumaMappedFormat) {
				iPosition++;
				iReturn = iReturn + PrimeNumberZZZ.primePosition(ienumMappedFormat.getFactor(), iPosition);
			}
		}//end main:
		return iReturn;
	}
	
	//###################################################
	//### aus ILogStringFormatComputerJaggedZZZ
	//###################################################
	

	@Override
	public String computeJagged_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode

		return computeUsingFormat__(ienumFormatLogString);

	}
	
	
	@Override
	public String computeJagged_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode.
				
				    	
		return this.computeLinesInLog_Jagged__(ienumaFormatLogString, sLogs);
	}
	

	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
				
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
		
		Class classObj = null;
	    if (obj == null) {
	    	classObj = this.getClass();	
	    }else {
	    	classObj = obj.getClass();
	    }
	    	   
	    return computeUsingFormatByObject__(classObj, ienumFormatLogString);
	}
		
	@Override
	public String computeJagged_(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
				
		Class classObj = null;
	    if (classObj == null) {
	    	//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	
	    }else {
	    	classObj = classObjIn;
	    }
	    
	    return computeUsingFormatByObject__(classObj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged_(Class classObjIn, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
				
		Class classObj = null;
	    if (classObjIn == null) {
	    	//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
	    }else {
	    	classObj = classObjIn;
	    }		
	    ArrayList<String> listas = this.computeLinesInLogArrayList_Jagged__(classObj, hmLog);
	    return ArrayListUtilZZZ.implode(listas, StringZZZ.crlf());
	}
	
	@Override
	public String computeJagged_(String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listas =  this.computeJaggedArrayList_(sLogs);		
		String sReturn = ArrayListUtilZZZ.implode(listas, StringZZZ.crlf());
		return sReturn;	
	}

	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		ArrayListZZZ<String> listas = this.computeJaggedArrayList_(ienumaFormatLogString, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(listas, StringZZZ.crlf());
		return sReturn;
	}
	
	@Override
	public String computeJagged_(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs)	throws ExceptionZZZ {
		String sReturn = "";
		main:{								
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			//20260212 raus
			//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			Class classObj = null;
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				classObj = classObjIn;
			}
			
			if(StringArrayZZZ.isEmpty(sLogs)) {							
				sReturn = this.computeUsingFormatByObject__(classObj, ienumFormatLogString);										
			}else {
				sReturn = this.computeLinePartInLog__(classObj, ienumFormatLogString, sLogs);
			}
			
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public String computeJagged_(Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			//20260212 raus
			//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			Class classObj = null;
			if(obj==null) {
				classObj = this.getClass();
			}else {
				classObj = obj.getClass();
			}
			ArrayListZZZ<String> lista =  this.computeLinesInLog_Jagged_ArrayList__(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
			sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public String computeJagged_(Class classObjIn, String... sLogs) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
	
		Class classObj = null;
		if(classObjIn==null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			classObj = classObjIn;
		}
		
		//Hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//von der Methode der erbenden Klasse. Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.		
		ArrayListZZZ<String> lista =  this.computeLinesInLog_Jagged_ArrayList__(classObj, (IEnumSetMappedStringFormatZZZ[])null, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}
	
	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			Class classObj = null;
			if(obj==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				classObj = obj.getClass();
			}
			
			sReturn = this.computeJagged_(classObj, ienumFormatLogString, sLogs);				
		}//end main:
		return sReturn;
	}

	
	@Override
	public String computeJagged_(Class classObjIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		Class classObj = null;
		if(classObjIn==null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			classObj = classObjIn;
		}
		
		//Hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		ArrayListZZZ<String> lista =  this.computeLinesInLog_Jagged_ArrayList__(classObj, ienumaFormatLogString, sLogs);
		String sReturn = ArrayListUtilZZZ.implode(lista, StringZZZ.crlf());
		return sReturn;
	}
	
	

	@Override
	public String computeJagged_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		Class classObj = null;
		if(obj==null) {
			classObj = this.getClass();
		}else {
			classObj = obj.getClass();
		}
		
		return this.computeLinesInLog_Jagged__(classObj, hm);
	}
	
	@Override
	public String computeJagged_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {

		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
		
		return this.computeLinesInLog_Jagged__(this.getClass(), hm);
	}
	
	//#################################################################
	//### Jede Methode auch für das Ergebnis ArrayList<String>
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 

		//Hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		Class classObj = this.getClass();
		ArrayListZZZ<String> listas =  this.computeLinesInLog_Jagged_ArrayList__(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);		
		return listas;	
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, String... sLogs) throws ExceptionZZZ {
		//Hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.
		Class classObj = obj.getClass();
		ArrayListZZZ<String> listas =  this.computeLinesInLog_Jagged_ArrayList__(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);		
		return listas;	
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, String... sLogs) throws ExceptionZZZ {		
		//Hier nicht die Zeilen buendig machen, es koennten XML-Tags angefordert sein
		//Die Aufrufende Methode soll sich dann um das Buendig-Machen kuemmern.		
		ArrayListZZZ<String> listas =  this.computeLinesInLog_Jagged_ArrayList__(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);		
		return listas;	
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 
		
		return this.computeLinesInLog_Jagged_ArrayList__(ienumaFormatLogString, sLogs);
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //hier 1x  der aufrufenden Methode und nicht in der x-mal aufgerufenen private Methode. 
				
		Object obj;
		if(objIn==null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			obj = objIn;
		}
		
		IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaFormatLogString[0] = ienumFormatLogString;
		
		return this.computeLinesInLog_Jagged_ArrayList__(obj.getClass(), ienumaFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
				
		Class classObj = null;
		if (classObjIn == null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;	
	    }else {
	    	classObj = classObjIn;
	    }
		
		IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaFormatLogString[0] = ienumFormatLogString;
		
		return computeLinePartByObject_ArrayList__(classObj, ienumaFormatLogString);
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
						
		Object obj = null;
		if (objIn == null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
		   	obj = objIn;
		}
				
		IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaFormatLogString[0] = ienumFormatLogString;
				
		return computeLinesInLog_Jagged_ArrayList__(obj.getClass(), ienumaFormatLogString, sLogs);
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObjIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
								
		Class classObj  = null;
		if (classObjIn == null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
			classObj = classObjIn;
		}
						
		IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = new IEnumSetMappedStringFormatZZZ[1];
		ienumaFormatLogString[0] = ienumFormatLogString;
						
		return computeLinesInLog_Jagged_ArrayList__(classObj, ienumaFormatLogString, sLogs);
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
		//20260212 raus
		//this.resetStringIndexRead(); //Hier in der aufrufenden Methode, nicht in der von x-Stellen aufgerufenen private Methode
						
		Object obj = null;
		if (objIn == null) {
			//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
			ExceptionZZZ ez = new ExceptionZZZ("Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}else {
		   	obj = objIn;
		}
								
		return computeLinesInLog_Jagged_ArrayList__(obj.getClass(), ienumaFormatLogString, sLogs);
	}



	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObjIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = null;				
		main:{								
			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
			//20260212 raus
			//this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
			
			Class classObj = null;
			if(classObjIn==null) {
				//In den aufrufenden Methoden dieser private Methode sollte das schon geklaert sein.
				ExceptionZZZ ez = new ExceptionZZZ("Class-Object", iERROR_PARAMETER_MISSING, AbstractStringFormaterZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}else {
				classObj = classObjIn;
			}
			
			listasReturn = computeLinesInLog_Jagged_ArrayList__(classObj, ienumaFormatLogString, sLogs);			
		}//end main:
		return listasReturn;	
	}
	
	
	
//	//###################################################
//	//### aus ILogStringFormatComputerJustifiedZZZ
//	//### Normalerweise sorgt der FormatManager für das Justified, 
//	//### aber ggfs. soll der Formater selbst auch buendige Zeilen produzieren
//	//###################################################
//	
//	@Override
//	public String computeJustified(String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{
//		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//		return sReturn;
//	}
//	
//	@Override
//	public String computeJustified(LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
//
//		//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//		this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//		
//		return this.computeLinesInLog_Justified_(this.getClass(), hm);
//	}
//	
//	
//	@Override
//	public String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedLogStringFormatZZZ, String> hm) throws ExceptionZZZ {
//		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			return this.computeLinesInLog_Justified_(obj.getClass(), hm);
//	}
//
//	
//	@Override
//	public String computeJustified(Object obj, String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(obj, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}
//
//	@Override
//	public String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(classObj, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}
//
//
//	@Override
//	public String computeJustified(IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs)
//			throws ExceptionZZZ {
//		//TODOGOON: Soll der Formater überhaupt .computeJustified können?
//		return null;
//	}
//	
//	@Override
//	public String computeJustified(Object obj, IEnumSetMappedLogStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(obj, ienumaFormatLogString, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}
//
//	@Override
//	public String computeJustified(Class classObj, IEnumSetMappedLogStringFormatZZZ[] ienumFormatLogString,	String... sLogs) throws ExceptionZZZ {
//		String sReturn = null;
//		main:{		
//			//###### Mache das Array der verarbeiteten "normalen" Text-Log-Zeilen leer
//			this.resetStringIndexRead(); //Hier in der aufrufenden Methode und nicht in der von x-Stellen aufgerufene private Methode
//			
//			sReturn = this.computeJagged(classObj, ienumFormatLogString, sLogs);
//			if(StringZZZ.isEmpty(sReturn)) break main;
//		
//		
//			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
//			//WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
//			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
//		
//			IStringJustifierZZZ objStringJustifier = this.getStringJustifier();
//			sReturn = LogStringFormaterUtilZZZ.justifyInfoPart(objStringJustifier, sReturn);							
//		}//end main:
//	return sReturn;
//	}

	//###################################################
	//### FLAG: ILogStringFormaterZZZ
	//###################################################
	@Override
	public boolean getFlag(IStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IStringFormaterZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IStringFormaterZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStringFormaterZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStringFormaterZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
