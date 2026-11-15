package basic.zBasic.formula;

import org.jdom.Document;
import org.jdom.Element;

import base.xml.XMLUtil;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmUtilZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledConstantZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.IROTEnabledConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlDocumentUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryption_CharacterPoolAdditionalZZZ;
import basic.zKernel.file.ini.KernelEncryption_CharacterPoolZZZ;
import basic.zKernel.file.ini.KernelEncryption_CipherZZZ;
import basic.zKernel.file.ini.KernelEncryption_CodeZZZ;
import basic.zKernel.file.ini.KernelEncryption_KeyNumberZZZ;
import basic.zKernel.file.ini.Kernel_FlagControlZZZ;
import basic.zKernel.file.ini.ZTagEncryption_KeyStringZZZ;

/**Klasse, in der die Zeile mit einer Z-Formula behandelt wird.
 * Z.B.: Erstelle eine Zeile aus einem übergebenen Objekt heraus (IKernelConfigSectionEntryZZZ)
 *   * 
 * @author Fritz Lindhauer, 20.03.2023, 12:21:15
 * @param <T>
 * 
 */
public class ZFormulaIniLineZZZ<T>  extends AbstractObjectWithFlagZZZ<T>{
	private static final long serialVersionUID = 890064280935919576L;
	
	public static String createLineFrom(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objEntry==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(!objEntry.isExpression()) {
				sReturn = objEntry.getProperty() + "=" + objEntry.getValue();
			}else {
				ICryptZZZ objCrypt = objEntry.getCryptAlgorithmType();
				if(objCrypt!=null) {
					String sValue = objEntry.getValue();
					if(objEntry.isEncrypted()) {
						sReturn = ZFormulaIniLineZZZ.createLineFromEncrypted(sValue,objCrypt);
					}else {
						sReturn = ZFormulaIniLineZZZ.createLineFrom(sValue,objCrypt);
					}
				}							
			}
		}
		return sReturn;
	}
	
	public static String createLineFrom(String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'KernelConfigSectionEntry Object'",iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//1. Verschluesseln
			String sValueEncrypted = objCrypt.encrypt(sValue);
			
			//2. Zeile erzeugen
			sReturn = ZFormulaIniLineZZZ.createLineFromEncrypted(sValueEncrypted, objCrypt);
			
		}//end main
		return sReturn;
	}
	
	public static String createLineFromEncrypted(String sValue, ICryptZZZ objCrypt) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(objCrypt==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'CryptAlgorithm Object'",iERROR_PARAMETER_MISSING, ZFormulaIniLineZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			              			
			//Merke: Aus KernelZFormulaIniSolverTest. Dort wird im setup die Datei aufgebaut und eine Zeile hat z.B. folgende Struktur:
			//objStreamFile.println("WertCencrypted=<Z><Z:Encrypted><Z:Cipher>ROTnn</Z:Cipher><z:KeyNumber>"+iKeyNumber+"</z:KeyNumber><z:CharacterPool>"+sCharacterPool+"</z:CharacterPool><z:FlagControl>"+sFlagUppercase+"</Z:FlagControl><Z:Code>"+sEncrypted+"</Z:Code></Z:Encrypted></Z>");
			//bzw:     //TestGetParameter_Encrypted=<Z><Z:Encrypted><Z:Cipher>VigenereNn</Z:Cipher><z:KeyString>Hundi</z:KeyString><z:CharacterPool> abcdefghijklmnopqrstuvwxyz</z:CharacterPool><z:CharacterPoolAdditional>!</z:CharacterPoolAdditional><z:FlagControl>USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER</Z:FlagControl><Z:Code>pzGxiMMtsuOMsmlPt</Z:Code></Z:Encrypted></Z>
			Document objDocument = XmlDocumentUtilZZZ.createDocumentWithRootTag("Z");
			org.jdom.Element objRoot = objDocument.getRootElement();
									
			String stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryptionIniSolverZZZ.sTAG_NAME);
			Element objElementZEncryption = new Element(stemp);
			
			stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryption_CipherZZZ.sTAG_NAME);
			Element objElementZCipher = new Element(stemp);
			
			//z:Cipher
			//Alternative Ansätze, aber jetzt hat ICryptZZZ die Methode getCipherType(), da braucht man die Workarounds nicht
			//sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnn.getAbbreviation();
			//Class objClass = CryptAlgorithmMappedValueZZZ.getEnumClassStatic();			
			String sCipher = objCrypt.getCipherType().getAbbreviation();
			objElementZCipher.addContent(sCipher);						
			objElementZEncryption.addContent(objElementZCipher);
			
			//z:code
			stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryption_CodeZZZ.sTAG_NAME);
			Element objElementZCode = new Element(stemp);	
			String sCode = sValue;
			objElementZCode.addContent(sCode);
			objElementZEncryption.addContent(objElementZCode);
		
			
			//z:KeyNumber
			int iKeyNumber = objCrypt.getCryptNumber();
			if(iKeyNumber>-1) {				
				stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryption_KeyNumberZZZ.sTAG_NAME);
				Element objElementZKeyNumber = new Element(stemp);
				String sKeyNumber = Integer.toString(iKeyNumber);
				objElementZKeyNumber.addContent(sKeyNumber);
				objElementZEncryption.addContent(objElementZKeyNumber);
			}
						
			//z:KeyString
			String sKeyString = objCrypt.getCryptKey();
			if(!StringZZZ.isEmpty(sKeyString)) {
				stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(ZTagEncryption_KeyStringZZZ.sTAG_NAME);
				Element objElementZKeyString = new Element(stemp);				
				objElementZKeyString.addContent(sKeyString);
				objElementZEncryption.addContent(objElementZKeyString);
			}
			
			//z:CharacterPool
			if(CryptAlgorithmUtilZZZ.isUsingCharacterPoolBase(objCrypt)){
				String sCharacterPool = objCrypt.getCharacterPoolBase();
				if(!StringZZZ.isEmpty(sCharacterPool)) {
					stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryption_CharacterPoolZZZ.sTAG_NAME);
					Element objElementZCharacterPoolString = new Element(stemp);				
					objElementZCharacterPoolString.addContent(sCharacterPool);
					objElementZEncryption.addContent(objElementZCharacterPoolString);
				}
				
				//z:CharacterPoolAdditional
				String sCharacterPoolAdditional = objCrypt.getCharacterPoolAdditional();
				if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {
					stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(KernelEncryption_CharacterPoolAdditionalZZZ.sTAG_NAME);
					Element objElementZCharacterPoolString = new Element(stemp);				
					objElementZCharacterPoolString.addContent(sCharacterPoolAdditional);
					objElementZEncryption.addContent(objElementZCharacterPoolString);
				}
				
				//z:FlagControl
				StringBuilder objBuilderFlag = new StringBuilder();
				
				//USEUPPERCASE,USENUMERIC,USELOWERCASE,USEADDITIONALCHARACTER				
				if(objCrypt.getFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEADDITIONALCHARACTER.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEADDITIONALCHARACTER.name());
				}
				if(objCrypt.getFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USELOWERCASE.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(ICharacterPoolEnabledConstantZZZ.FLAGZ.USELOWERCASE.name());
				}
				if(objCrypt.getFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USENUMERIC.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(ICharacterPoolEnabledConstantZZZ.FLAGZ.USENUMERIC.name());
				}
				if(objCrypt.getFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USESTRATEGY_CHARACTERPOOL.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(ICharacterPoolEnabledConstantZZZ.FLAGZ.USESTRATEGY_CHARACTERPOOL.name());
				}
				if(objCrypt.getFlag(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEUPPERCASE.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(ICharacterPoolEnabledConstantZZZ.FLAGZ.USEUPPERCASE.name());
				}
				
				//USESTRATEGY_CASECHANGE,USEBLANK
				if(objCrypt.getFlag(IROTEnabledConstantZZZ.FLAGZ.USEBLANK.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(IROTEnabledConstantZZZ.FLAGZ.USEBLANK.name());
				}
				if(objCrypt.getFlag(IROTEnabledConstantZZZ.FLAGZ.USESTRATEGY_CASECHANGE.name())) {
					if(objBuilderFlag.length()>0)objBuilderFlag.append(",");
					objBuilderFlag.append(IROTEnabledConstantZZZ.FLAGZ.USESTRATEGY_CASECHANGE.name());
				}
								
				//XML-Element setzen
				String sFlag = objBuilderFlag.toString();												
				if(!StringZZZ.isEmpty(sFlag)) {
					stemp = XmlDocumentUtilZZZ.replaceColonInXmlTag(Kernel_FlagControlZZZ.sTAG_NAME);
					Element objElementZFlagControl = new Element(stemp);				
					objElementZFlagControl.addContent(sFlag);
					objElementZEncryption.addContent(objElementZFlagControl);
				}
			}
					
			XMLUtil.addContent(objRoot, objElementZEncryption);			
			objDocument.setRootElement(objRoot);
			String sXmlReplaced = XmlDocumentUtilZZZ.documentToString(objDocument);

			//Aus diesem String die "validen" Tags wieder durch Tags mit : ersetzten, was dann der ZExpression entspricht
			sReturn = XmlDocumentUtilZZZ.placeColonInZExpression(sXmlReplaced);
		}//end main
		return sReturn;
	}
	
	
}
