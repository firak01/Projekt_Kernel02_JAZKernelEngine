package basic.zBasic.util.string.formater.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ.LOGSTRINGFORMAT;
import junit.framework.TestCase;

public abstract class AbstractStringFormatZZZTest extends TestCase {

    // -------------------------------------------------------------------------
    // ABSTRACT: von Kindklassen zu implementieren
    // -------------------------------------------------------------------------
    
    /** Reines Formatieren ohne Position/Format-Enum */
    protected abstract String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ;

    /** Formatieren mit Format-Enum */
    protected abstract String computeWithFormat(
            DummyTestObjectZZZ dummy,
            IEnumSetMappedStringFormatZZZ[] format,
            String... logs
    ) throws ExceptionZZZ;

    // -------------------------------------------------------------------------
    // Hilfsmethoden, die in beiden Klassen identisch waren:
    // -------------------------------------------------------------------------

    protected void assertContainsOnce(String text, String part) {
    	try {
        assertTrue(StringZZZ.contains(text, part));
        assertEquals(1, StringZZZ.count(text, part));
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    protected void assertThreadAndClassPresent(String logValue, String threadMarker, String classMarker) {
    	try {
        assertEquals(1, StringZZZ.count(logValue, threadMarker));
        assertEquals(1, StringZZZ.count(logValue, classMarker));
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    protected void assertOrder(String logValue, String className, String threadMarker, String logEntry) {
    	try {
        int idxLog   = StringZZZ.indexOfFirst(logValue, logEntry);
        int idxThread= StringZZZ.indexOfFirst(logValue, threadMarker);
        int idxCls   = StringZZZ.indexOfFirst(logValue, className);

        assertTrue(idxLog > idxCls + className.length());
        assertTrue(idxThread + threadMarker.length() < idxCls);
    	}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
    }

    // -------------------------------------------------------------------------
    // Gemeinsame Tests:
    // -------------------------------------------------------------------------

    public void testSingleLogEntry_DefaultFormat() {
        try {
            DummyTestObjectZZZ dummy = new DummyTestObjectZZZ();
            String sLog = "der erste Logeintrag";
            String thread = "[Thread:";
            String cls = dummy.getClass().getSimpleName() + IReflectCodeZZZ.sPOSITION_METHOD_SEPARATOR;

            String result = compute(dummy, sLog);
            System.out.println(result);
            
            assertNotNull(result);
            assertContainsOnce(result, sLog);
            assertThreadAndClassPresent(result, thread, cls);
            assertOrder(result, cls, thread, sLog);

        } catch(ExceptionZZZ e) {
            fail("Exception: " + e.getMessageLast());
        }
    }

    public void testMultipleLogs_WithArray_UsingFormat() {
        try {
            DummyTestObjectZZZ dummy = new DummyTestObjectZZZ();

            String s1="erster"; 
            String s2="zweiter";
            String s3="dritter";

            String thread = "[Thread:";
            String cls = dummy.getClass().getSimpleName();  

            IEnumSetMappedStringFormatZZZ[] format = new IEnumSetMappedStringFormatZZZ[]{
                IStringFormatZZZ.LOGSTRINGFORMAT.CLASSNAME_STRING,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE01_STRING_BY_STRING,
                IStringFormatZZZ.LOGSTRINGFORMAT.THREADID_STRING, 
                IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE02_STRING_BY_STRING,
                IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_,
                IStringFormatZZZ.LOGSTRINGFORMAT.STRINGTYPE03_STRING_BY_STRING
            };

            String result = computeWithFormat(dummy, format, s1, s2, s3);

            assertContainsOnce(result, s1);
            assertContainsOnce(result, s2);
            assertContainsOnce(result, s3);

            assertThreadAndClassPresent(result, thread, cls);

        } catch(ExceptionZZZ e) {
            fail("Exception: " + e.getMessageLast());
        }
    }
}
