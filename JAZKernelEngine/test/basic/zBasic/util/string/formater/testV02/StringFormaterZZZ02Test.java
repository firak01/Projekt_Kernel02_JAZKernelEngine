package basic.zBasic.util.string.formater.testV02;

import basic.zBasic.DummyTestObjectZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;

/**Per ChatGPT von Redundanz befreit durch Verwendung einer Abstrakten Test-Klasse
 * Diese wurde von ChatGPT basierend auf den .testV00 Klassen erstellt  
 * @author Fritz Lindhauer, 15.11.2025, 16:22:53
 * 
 */
public class StringFormaterZZZ02Test extends AbstractStringFormatZZZTest {

	private StringFormaterZZZ formater = null;

	protected void setUp(){
		try {			
			formater = new StringFormaterZZZ();
			
			//################################################
			//Singleton zurücksetzen, damit der Test auch mit mehreren JUnit Tests funktioniert.
			StringFormatManagerZZZ.getInstance().reset();			
			StringJustifierManagerZZZ.getInstance().reset(); //sonst werden die Zeilen unübersichtlich lang mit leerzeichen aufgefüllt
								
		} catch (ExceptionZZZ ez) {
			fail("Method throws an exception." + ez.getMessageLast());
		} 	
	}//END setup

    @Override
    protected String compute(DummyTestObjectZZZ dummy, String... logs) throws ExceptionZZZ {
        return formater.compute(dummy, logs);
    }

    @Override
    protected String computeWithFormat(DummyTestObjectZZZ dummy,
            IEnumSetMappedStringFormatZZZ[] format,
            String... logs) throws ExceptionZZZ {

        formater.setFormatPositionsMapped(format);
        return formater.compute(dummy, logs);
    }
}
