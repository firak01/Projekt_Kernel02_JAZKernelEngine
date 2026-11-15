package basic.zBasic.util.string.formater;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.math.MathZZZ;

public class StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ  implements IConstantZZZ{
	
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = null;
		main:{
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString = ienumaFormatLogStringIn;
			
			//0. Vergleiche die Formatanweisung, hinsichtlicher Stringausgaben mit der Anzahl der sLogs.
			//   Wenn zuwenig Stringausgaben sind, ergänze diese in jeweils einer neuen Zeile, mit vorangestelltem Kommentarseparator.
			int iNumberOfFormats_StringType01 = StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.evaluateNumberOf_StringType01(ienumaFormatLogStringIn);
			int iNumberOfFormats_StringType02 = 0;
			int iNumberOfFormats_StringType03 = 0;
			
			int iNumberOfStringFormats = MathZZZ.sum(iNumberOfFormats_StringType01, iNumberOfFormats_StringType02, iNumberOfFormats_StringType03);
			int iLinesDifference = sLogs.length - iNumberOfStringFormats;					
			if(iLinesDifference>=1) {			
				int iMaxType = MathZZZ.max(iNumberOfFormats_StringType01, iNumberOfFormats_StringType02, iNumberOfFormats_StringType03);
				
				//Strategie: Mache als Format der neuen Zeile das meist verwendete
				if(iMaxType == iNumberOfFormats_StringType01) {	
					System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Ergänze um neue Kommentarzeilen vom StringType01.");
					ienumaFormatLogString = StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.appendLines_StringType01(ienumaFormatLogStringIn, iLinesDifference);					
				} else if(iMaxType == iNumberOfFormats_StringType02){
					System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Ergänze um neue Kommentarzeilen vom StringType02.");
					ienumaFormatLogString = StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.appendLines_StringType02(ienumaFormatLogStringIn, iLinesDifference);	
				} else if(iMaxType == iNumberOfFormats_StringType03) {					
					System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Ergänze um neue Kommentarzeilen vom StringType03.");
					ienumaFormatLogString = StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.appendLines_StringType03(ienumaFormatLogStringIn, iLinesDifference);	
				} else {
					ReflectCodeZZZ.printStackTrace("unexpected StringType counted");					
				}							
			}
			
			objaReturn = ienumaFormatLogString;
		}//end main:
		return objaReturn;
	}
	
	
	//###############################################
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, int iNumberOfLines) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{			
			for(int i=1;i<=iNumberOfLines;i++) {
				objaReturn = appendLines_StringType01(objaReturn);
			}			
		}//end main:
		return objaReturn;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaFormat = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArrayList(ienumaFormatLogString);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING);
			
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaFormat);
		}//end main:
		return objaReturn;
	}
	
	public static boolean isStringType01(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iFaktor = ienumFormatLogString.getFactor();
			int[] iaFaktorStringType01 = new int[]{
					IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING,
					IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING
			};
			
			if(IntegerArrayZZZ.contains(iaFaktorStringType01, iFaktor)) bReturn=true;
		}//end main:
		return bReturn;
	}
	
	public static int evaluateNumberOf_StringType01(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{				
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) break main;
			
			
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogStringIn) {
				if(isStringType01(ienumFormatLogString)){
					iReturn++;
				}
				
				
			}								
		}//end main:
		return iReturn;
	}
	
	
	//#####################################################
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType02(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, int iNumberOfLines) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{			
			for(int i=1;i<=iNumberOfLines;i++) {
				objaReturn = appendLines_StringType02(objaReturn);
			}			
		}//end main:
		return objaReturn;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType02(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaFormat = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArrayList(ienumaFormatLogString);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING);
			
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaFormat);
		}//end main:
		return objaReturn;
	}
	
	public static boolean isStringType02(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iFaktor = ienumFormatLogString.getFactor();
			int[] iaFaktorStringType02 = new int[]{
					IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING,
					IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING
			};
			
			if(IntegerArrayZZZ.contains(iaFaktorStringType02, iFaktor)) bReturn=true;
		}//end main:
		return bReturn;
	}
	
	public static int evaluateNumberOf_StringType02(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{				
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) break main;
			
			
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogStringIn) {
				if(isStringType02(ienumFormatLogString)){
					iReturn++;
				}
				
				
			}								
		}//end main:
		return iReturn;
	}
	
	//###############################################################
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType03(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, int iNumberOfLines) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{			
			for(int i=1;i<=iNumberOfLines;i++) {
				objaReturn = appendLines_StringType03(objaReturn);
			}			
		}//end main:
		return objaReturn;
	}
	
	public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType03(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn = ienumaFormatLogString;
		main:{
			ArrayList<IEnumSetMappedStringFormatZZZ> listaFormat = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArrayList(ienumaFormatLogString);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
			listaFormat.add(IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING);
			
			objaReturn = EnumSetMappedLogStringFormatUtilZZZ.toEnumMappedArray(listaFormat);
		}//end main:
		return objaReturn;
	}
	
	public static boolean isStringType03(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iFaktor = ienumFormatLogString.getFactor();
			int[] iaFaktorStringType02 = new int[]{
					IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING,
					IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING
			};
			
			if(IntegerArrayZZZ.contains(iaFaktorStringType02, iFaktor)) bReturn=true;
		}//end main:
		return bReturn;
	}
	
	public static int evaluateNumberOf_StringType03(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{				
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, StringFormatManagerUtilZZZ_BACKUP_VERSION_MIT_REDUNDANZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) break main;
						
			for(IEnumSetMappedStringFormatZZZ ienumFormatLogString : ienumaFormatLogStringIn) {
				if(isStringType03(ienumFormatLogString)){
					iReturn++;
				}
			}								
		}//end main:
		return iReturn;
	}
	
	//######################################################################
}
