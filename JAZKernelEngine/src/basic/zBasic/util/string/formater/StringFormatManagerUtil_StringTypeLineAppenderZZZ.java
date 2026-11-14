package basic.zBasic.util.string.formater;

import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.enums.EnumSetMappedLogStringFormatUtilZZZ;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.math.MathZZZ;

/**Von ChatGPT Redundanz verringert am 20260221;
 * @author Fritz Lindhauer, 21.02.2026, 08:49:16
 * 
 */
public class StringFormatManagerUtil_StringTypeLineAppenderZZZ implements IConstantZZZ {

    // #############################################################
    // Konfiguration der StringTypes
    // #############################################################

    private static final int[] FACTORS_TYPE01 = new int[]{
            IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING,
            IStringFormatZZZ.iFACTOR_STRINGTYPE01_XML_BY_STRING
    };

    private static final int[] FACTORS_TYPE02 = new int[]{
            IStringFormatZZZ.iFACTOR_STRINGTYPE02_STRING_BY_STRING,
            IStringFormatZZZ.iFACTOR_STRINGTYPE02_XML_BY_STRING
    };

    private static final int[] FACTORS_TYPE03 = new int[]{
            IStringFormatZZZ.iFACTOR_STRINGTYPE03_STRING_BY_STRING,
            IStringFormatZZZ.iFACTOR_STRINGTYPE03_XML_BY_STRING
    };

    private enum StringTypeConfig {
        TYPE01(FACTORS_TYPE01,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING),

        TYPE02(FACTORS_TYPE02,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING),

        TYPE03(FACTORS_TYPE03,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING);

        final int[] factors;
        final IEnumSetMappedStringFormatZZZ enumValue;

        StringTypeConfig(int[] factors,
                         IEnumSetMappedStringFormatZZZ enumValue) {
            this.factors = factors;
            this.enumValue = enumValue;
        }
    }

    // #############################################################
    // Öffentliche Hauptmethode
    // #############################################################

    public static IEnumSetMappedStringFormatZZZ[] appendLines_StringType(
            IEnumSetMappedStringFormatZZZ[] formats,
            String... logs) throws ExceptionZZZ {

        if (formats == null) {
            throw new ExceptionZZZ(
                    "IEnumSetMappedLogStringFormatZZZ[]",
                    iERROR_PARAMETER_MISSING,
                    StringFormatManagerUtil_StringTypeLineAppenderZZZ.class,
                    ReflectCodeZZZ.getMethodCurrentName());
        }
        
        if(logs==null) return formats;
        if (ArrayUtilZZZ.isEmpty(formats)) return formats;

        //+++++++++++++++++++++++++++++++++++++++++++++++++++
        //Wichtig: Falls in logs Steueranweisungen/Informationen, wie die Codeposition enthalten sind, 
        //darf diese Zeile nicht herangezogen werden um die Differenz der benötigten StringType Einträge zu berechnen.
        //Solch eine Zeile wird ja selbst nicht ausgegeben.
        int iLogsLength = logs.length;
        int iControlsLength = 0;
        
        for(String sLog : logs) {
        	if(XmlUtilZZZ.isXmlStarting(sLog)) {
        		iControlsLength++;
        	}
        }
        
        iLogsLength = iLogsLength - iControlsLength;
        //++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        
        int maxCount = 0;
        StringTypeConfig maxType = null;
        int totalFormats = 0;

        for (StringTypeConfig type : StringTypeConfig.values()) {
            int count = evaluateNumberOf_StringType(formats, type.factors);
            totalFormats += count;

            if (count > maxCount) {
                maxCount = count;
                maxType = type;
            }
        }

        int difference = iLogsLength - totalFormats;
        if (difference <= 0) return formats;

        if (maxType == null) {
            ReflectCodeZZZ.printStackTrace("unexpected StringType counted");
            return formats;
        }

        //System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Ergänze um neue Kommentarzeilen vom Typ " + maxType.name());
        return appendLines_StringType(formats, difference, maxType.enumValue);
    }

    // #############################################################
    // Generische Kernlogik
    // #############################################################

    private static int evaluateNumberOf_StringType(
            IEnumSetMappedStringFormatZZZ[] formats,
            int[] validFactors) throws ExceptionZZZ {

        if (formats == null) {
            throw new ExceptionZZZ(
                    "IEnumSetMappedLogStringFormatZZZ[]",
                    iERROR_PARAMETER_MISSING,
                    StringFormatManagerUtil_StringTypeLineAppenderZZZ.class,
                    ReflectCodeZZZ.getMethodCurrentName());
        }

        if (ArrayUtilZZZ.isEmpty(formats)) return 0;

        int count = 0;

        for (IEnumSetMappedStringFormatZZZ format : formats) {
            if (isStringType(format, validFactors)) {
                count++;
            }
        }

        return count;
    }

    private static boolean isStringType(
            IEnumSetMappedStringFormatZZZ format,
            int[] validFactors) throws ExceptionZZZ {

        if (format == null) return false;

        int factor = format.getFactor();
        return IntegerArrayZZZ.contains(validFactors, factor);
    }

    private static IEnumSetMappedStringFormatZZZ[] appendLines_StringType(
            IEnumSetMappedStringFormatZZZ[] formats,
            int numberOfLines,
            IEnumSetMappedStringFormatZZZ stringTypeEnum)
            throws ExceptionZZZ {

        IEnumSetMappedStringFormatZZZ[] result = formats;

        for (int i = 0; i < numberOfLines; i++) {
            result = appendOneLine(result, stringTypeEnum);
        }

        return result;
    }

    private static IEnumSetMappedStringFormatZZZ[] appendOneLine(
            IEnumSetMappedStringFormatZZZ[] formats,
            IEnumSetMappedStringFormatZZZ stringTypeEnum)
            throws ExceptionZZZ {

        ArrayList<IEnumSetMappedStringFormatZZZ> list =
                EnumSetMappedLogStringFormatUtilZZZ
                        .toEnumMappedArrayList(formats);

        list.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_);
        list.add(IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_SEPARATORMESSAGE_STRING);
        list.add(stringTypeEnum);

        return EnumSetMappedLogStringFormatUtilZZZ
                .toEnumMappedArray(list);
    }

    // #############################################################
}