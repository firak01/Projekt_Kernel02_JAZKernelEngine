import junit.framework.Test;
import junit.framework.TestSuite;
import basic.zBasic.ReflectTHECodeKernelZZZTest;
import basic.zBasic.ReflectTHECodeZZZTest;
import basic.zBasic.ReflectUtilZZZTest;
import basic.zBasic.ReflectClassZZZTest;
import basic.zBasic.ReflectEnvironmentZZZTest;
import basic.zBasic.util.abstractArray.ArrayUtilZZZTest;
import basic.zBasic.util.abstractEnum.EnumSetMappedZZZTest;
import basic.zBasic.util.abstractList.ArrayListZZZTest;
import basic.zBasic.util.abstractList.ArrayListUtilZZZTest;
import basic.zBasic.util.abstractList.HashMapZZZTest;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZTest;
import basic.zBasic.util.abstractList.HashMapIterableKeyZZZTest;
import basic.zBasic.util.abstractList.HashMapIndexedZZZTest;
import basic.zBasic.util.abstractList.HashMapMultiIndexedZZZTest;
import basic.zBasic.util.abstractList.HashMapMultiZZZTest;
import basic.zBasic.util.abstractList.HashMapUtilZZZTest;
import basic.zBasic.util.abstractList.HashtableSortedZZZTest;
import basic.zBasic.util.abstractList.MapUtilZZZTest;
import basic.zBasic.util.abstractList.Vector3ZZZTest;
import basic.zBasic.util.abstractList.VectorLimitedZZZTest;
import basic.zBasic.util.abstractList.VectorUtilZZZTest;
import basic.zBasic.util.data.DataStoreZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAsciiFactoryZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAsciiSingletonZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphabetZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericSignificantZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAscii_AlphanumericZZZTest;
import basic.zBasic.util.counter.CounterByCharacterAscii_NumericZZZTest;
import basic.zBasic.util.datatype.binary.BinaryTokenizerZZZTest;
import basic.zBasic.util.datatype.character.AsciiZZZTest;
import basic.zBasic.util.datatype.dateTime.DateTimeZZZTest;
import basic.zBasic.util.datatype.enums.EnumAvailableHelperZZZTest;
import basic.zBasic.util.datatype.enums.EnumMappedAvailableHelperZZZTest;
import basic.zBasic.util.datatype.enums.EnumMappedStatusLocalAvailableHelperZZZTest;
import basic.zBasic.util.datatype.enums.EnumSetMappedUtilZZZTest;
import basic.zBasic.util.datatype.enums.EnumUtilZZZTest;
import basic.zBasic.util.datatype.enums.EnumSetMappedStatusLocalUtilZZZTest;
import basic.zBasic.util.datatype.integer.IntegerArrayZZZTest;
import basic.zBasic.util.datatype.integer.IntegerZZZTest;
import basic.zBasic.util.datatype.json.JsonArrayZZZTest;
import basic.zBasic.util.datatype.json.JsonUtilZZZTest;
import basic.zBasic.util.datatype.string.StringArrayZZZTest;
import basic.zBasic.util.datatype.string.StringAnalyseUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZTest;
import basic.zBasic.util.datatype.xml.XmlTagByTypeUtilZZZTest;
import basic.zBasic.util.datatype.xml.XmlUtilZZZTest;
import basic.zBasic.util.file.FileEasyConstantConverterZZZTest;
import basic.zBasic.util.file.FileEasyCopyZZZTest;
import basic.zBasic.util.file.FileEasyZZZTest;
import basic.zBasic.util.file.FileTextAppenderZZZTest;
import basic.zBasic.util.file.FileTextParserZZZTest;
import basic.zBasic.util.file.FileTextSplitterZZZTest;
import basic.zBasic.util.file.JarEasyZZZTest;
import basic.zBasic.util.file.ini.IniFileTest;
import basic.zBasic.util.file.jar.JarInfoClassLoaderTest;
import basic.zBasic.util.file.jar.JarInfoFeaturesTest;
import basic.zBasic.util.file.jar.JarInfoTest;
import basic.zBasic.util.file.jar.JarResourcesReadingTest;
import basic.zBasic.util.file.txt.TxtReaderZZZTest;
import basic.zBasic.util.file.txt.TxtWriterZZZTest;
import basic.zBasic.util.stream.StreamZZZTest;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ00Test;
import basic.zBasic.util.xml.XmlParserZZZTest;
import basic.zBasic.util.xml.XmlTagMatcherZZZTest;
import basic.zKernel.GetOptZZZTest;
import basic.zKernel.KernelPropertyZZZTest;
import basic.zKernel.KernelUseObjectZZZTest;
import basic.zKernel.KernelZZZTest;
import basic.zKernel.LogZZZTest;
import basic.zKernel.config.EnumSetKernelConfigDefaultEntryUtilZZZTest;
import basic.zKernel.file.ini.ExpressionIniUtilZZZTest;
import basic.zKernel.file.ini.FileIniZZZTest;
import basic.zKernel.file.ini.KernelCallIniSolverZZZTest;
import basic.zKernel.file.ini.KernelConfigSectionEntryUtilZZZTest;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZTest;
import basic.zKernel.file.ini.KernelZFormulaIniConverterZZZTest;
import basic.zKernel.file.ini.KernelExpressionIniHandlerZZZTest;
import basic.zKernel.file.ini.KernelJavaCallIniSolverZZZTest;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZTest;
import basic.zKernel.file.ini.KernelJsonIniSolverZZZTest;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZTest;
import basic.zKernel.flag.FlagZHelperZZZTest;
import basic.zKernel.status.StatusLocalAvailableHelperZZZTest;
import basic.zKernel.file.ini.KernelJsonMapIniSolverZZZTest;
import basic.zUtil.io.FileExpansionZZZTest;
import basic.zUtil.io.FileZZZTest;
 
public class KernelAllTestZZZ {

	public static Test suite(){
		TestSuite objReturn = new TestSuite();
		//Merke: Die Tests bilden in ihrer Reihenfolge in etwa die Hierarchie im Framework ab. 
		//            Dies beim Einfügen weiterer Tests bitte beachten.         
		
		//Tests fuer Reflection
		objReturn.addTestSuite(ReflectEnvironmentZZZTest.class);
		objReturn.addTestSuite(ReflectTHECodeZZZTest.class);
		objReturn.addTestSuite(ReflectTHECodeKernelZZZTest.class);
		objReturn.addTestSuite(ReflectUtilZZZTest.class);
		objReturn.addTestSuite(ReflectClassZZZTest.class);
		
		//Tests fuer das bauen eines FORMATIERTEN (ggfs. buendigen) Strings
		objReturn.addTestSuite(basic.zBasic.util.string.formater.StringFormatManagerUtilZZZTest.class);
		objReturn.addTestSuite(basic.zBasic.util.string.formater.StringFormaterUtilZZZTest.class);
		
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV00.StringFormaterZZZ00Test.class);
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV00.StringFormatManagerZZZ00Test.class);
		
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV01.StringFormaterZZZ01Test.class);
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV01.StringFormatManagerZZZ01Test.class);
		
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV02.StringFormaterZZZ02Test.class);
		objReturn.addTestSuite(basic.zBasic.util.string.formater.testV02.StringFormatManagerZZZ02Test.class);
		
		//Tests fuer das bauen eines BUENDIGEN (ggfs. formatierten) Strings
		objReturn.addTestSuite(StringJustifierManagerZZZ00Test.class);
		
		
		
		//Tests fuer Enum
		objReturn.addTestSuite(EnumSetMappedStatusLocalUtilZZZTest.class);
		objReturn.addTestSuite(EnumSetMappedZZZTest.class);
		
		//Tests fuer Container ARRAY
		objReturn.addTestSuite(ArrayUtilZZZTest.class);
		objReturn.addTestSuite(IntegerArrayZZZTest.class);
		objReturn.addTestSuite(StringArrayZZZTest.class);
		
		//Tests fuer Container LISTE / MAP
		objReturn.addTestSuite(ArrayListZZZTest.class);
		objReturn.addTestSuite(ArrayListUtilZZZTest.class);	
		
		objReturn.addTestSuite(VectorUtilZZZTest.class);
		objReturn.addTestSuite(VectorLimitedZZZTest.class);
		objReturn.addTestSuite(Vector3ZZZTest.class);
		
		objReturn.addTestSuite(MapUtilZZZTest.class);
		objReturn.addTestSuite(HashMapUtilZZZTest.class);
		objReturn.addTestSuite(HashMapIterableKeyZZZTest.class);
		objReturn.addTestSuite(HashMapIndexedObjectZZZTest.class);
		
		objReturn.addTestSuite(HashMapZZZTest.class);
		objReturn.addTestSuite(HashMapIndexedZZZTest.class);
		objReturn.addTestSuite(HashMapMultiZZZTest.class);
		objReturn.addTestSuite(HashMapMultiIndexedZZZTest.class);
		objReturn.addTestSuite(HashtableSortedZZZTest.class);

		//TODO Noch nicht fertig objReturn.addTestSuite(HashtableWithDupsTestZZZ.class));
		objReturn.addTestSuite(CounterByCharacterAscii_AlphabetZZZTest.class);
		objReturn.addTestSuite(CounterByCharacterAscii_NumericZZZTest.class);
		objReturn.addTestSuite(CounterByCharacterAscii_AlphanumericZZZTest.class);
		objReturn.addTestSuite(CounterByCharacterAscii_AlphanumericSignificantZZZTest.class);
		objReturn.addTestSuite(CounterByCharacterAsciiFactoryZZZTest.class);
		objReturn.addTestSuite(CounterByCharacterAsciiSingletonZZZTest.class);
		
		//Tests fuer Datentypen etc.
		objReturn.addTestSuite(AsciiZZZTest.class);				
		objReturn.addTestSuite(DataStoreZZZTest.class);
		objReturn.addTestSuite(DateTimeZZZTest.class);
		objReturn.addTestSuite(BinaryTokenizerZZZTest.class);
		objReturn.addTestSuite(IntegerZZZTest.class);		
		objReturn.addTestSuite(StringZZZTest.class);
		objReturn.addTestSuite(StringAnalyseUtilZZZ.class);
		objReturn.addTestSuite(XmlTagMatcherZZZTest.class);
		objReturn.addTestSuite(XmlParserZZZTest.class);		
		objReturn.addTestSuite(XmlUtilZZZTest.class);
		objReturn.addTestSuite(XmlTagByTypeUtilZZZTest.class);
		
		//Stream - Tests
		objReturn.addTestSuite(StreamZZZTest.class);
		
		//Dateiverarbeitung - Tests		
		objReturn.addTestSuite(FileEasyConstantConverterZZZTest.class);
		objReturn.addTestSuite(FileEasyZZZTest.class);
		objReturn.addTestSuite(FileEasyCopyZZZTest.class);
		objReturn.addTestSuite(JarEasyZZZTest.class);
		
		//Bytebasiert, RandomAccess
		objReturn.addTestSuite(TxtReaderZZZTest.class);
		objReturn.addTestSuite(TxtWriterZZZTest.class);
		
		//Streambasiert, Erbend von AbstractFileTextReaderZZZ
		objReturn.addTestSuite(FileTextAppenderZZZTest.class);
		objReturn.addTestSuite(FileTextSplitterZZZTest.class);
		
		//FileIni - Tests
		objReturn.addTestSuite(ExpressionIniUtilZZZTest.class);
		objReturn.addTestSuite(KernelConfigSectionEntryUtilZZZTest.class);
		objReturn.addTestSuite(KernelZFormulaIniSolverZZZTest.class);
		
		objReturn.addTestSuite(KernelEncryptionIniSolverZZZTest.class);
		objReturn.addTestSuite(KernelZFormulaIniConverterZZZTest.class);
				
		objReturn.addTestSuite(KernelJavaCallIniSolverZZZTest.class);
		objReturn.addTestSuite(KernelCallIniSolverZZZTest.class);
		
		objReturn.addTestSuite(JsonUtilZZZTest.class);
		objReturn.addTestSuite(JsonArrayZZZTest.class);
		objReturn.addTestSuite(KernelJsonMapIniSolverZZZTest.class);
		objReturn.addTestSuite(KernelJsonArrayIniSolverZZZTest.class);
		objReturn.addTestSuite(KernelJsonIniSolverZZZTest.class);	
				
		objReturn.addTestSuite(KernelExpressionIniHandlerZZZTest.class);
		
		objReturn.addTestSuite(IniFileTest.class); //Merke: Diese Klasse ist nicht origin�r ZZZ, darum der Test auch nicht		
		objReturn.addTestSuite(FileIniZZZTest.class);
		
		objReturn.addTestSuite(EnumUtilZZZTest.class);
		objReturn.addTestSuite(EnumSetMappedUtilZZZTest.class);
		objReturn.addTestSuite(EnumSetMappedStatusLocalUtilZZZTest.class);
		
		objReturn.addTestSuite(EnumAvailableHelperZZZTest.class);//Teste die Behandlung der Enumeration
		objReturn.addTestSuite(EnumMappedAvailableHelperZZZTest.class);//Teste die Behandlung der Enumeration
		objReturn.addTestSuite(EnumMappedStatusLocalAvailableHelperZZZTest.class);//Teste die Behandlung der Enumeration
		
		objReturn.addTestSuite(FlagZHelperZZZTest.class);//Teste die Behandlung der Enumeration
		objReturn.addTestSuite(StatusLocalAvailableHelperZZZTest.class);//Teste die Behandlung der Enumeration
		
		
		
		
		//Test fuer Filter auf jar/zip Dateien
		objReturn.addTestSuite(JarInfoClassLoaderTest.class);
		objReturn.addTestSuite(JarInfoFeaturesTest.class);
		objReturn.addTestSuite(JarInfoTest.class);
		objReturn.addTestSuite(JarResourcesReadingTest.class);
		
		//Echte KernelObjekte testen
		objReturn.addTestSuite(KernelPropertyZZZTest.class);
		objReturn.addTestSuite(KernelZZZTest.class);
		objReturn.addTestSuite(KernelUseObjectZZZTest.class);
		objReturn.addTestSuite(LogZZZTest.class);
		objReturn.addTestSuite(EnumSetKernelConfigDefaultEntryUtilZZZTest.class);		
		objReturn.addTestSuite(FileTextParserZZZTest.class);		
		objReturn.addTestSuite(FileExpansionZZZTest.class);
		objReturn.addTestSuite(FileZZZTest.class);
		objReturn.addTestSuite(GetOptZZZTest.class);
		
		
		
		
		return objReturn;
	}
	/**
	 * Hiermit eine Swing-Gui starten.
	 * Das ist bei eclipse aber nicht notwendig, au�er man will alle hier eingebundenen Tests durchf�hren.
	 * @param args
	 */
	public static void main(String[] args) {
		//Ab Eclipse 4.4 ist junit.swingui sogar nicht mehr Bestandteil des Bundles
		//also auch nicht mehr unter der Eclipse Variablen JUNIT_HOME/junit.jar zu finden. 
	    //junit.swingui.TestRunner.run(KernelAllTestZZZ.class);
	}

}
