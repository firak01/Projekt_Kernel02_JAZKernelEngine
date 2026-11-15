package basic.zKernel.file.ini;

import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.CryptEnumSetFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.KernelCryptAlgorithmFactoryZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelEncryptionIniSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelEncryptionIniSolverZZZ{
	private static final long serialVersionUID = 5426925764480431586L;
	public static final String sTAG_NAME = "Z:Encrypted";	
	
	public KernelEncryptionIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelEncryptionIniSolverNew_(null, null);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelEncryptionIniSolverNew_(null, null);
	}
		
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
	
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
		
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelEncryptionIniSolverNew_(objFileIni, null);
	}
	
	public KernelEncryptionIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelEncryptionIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelEncryptionIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelEncryptionIniSolverNew_(objFileIni, hmVariable);
	}
	
	private boolean KernelEncryptionIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
		 boolean bReturn = false;	
		 main:{		
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
		
			FileIniZZZ<T> objFile=null;
			if(objFileIn==null ){
				objFile = this.getKernelObject().getFileConfigKernelIni();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else {
				objFile = objFileIn;
			}

			//Ubernimm ggfs. das Kernel-Objekt aus dem FileIni-Objekt
			if(this.getKernelObject()==null) this.setKernelObject(objFileIni.getKernelObject());
						
			if(hmVariableIn!=null){				
				this.setVariable(hmVariableIn);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
			}else {
				
				//Uebernimm ggfs. die Variablen aus dem FileIni-Objekt
				this.setFileConfigKernelIni(objFile);	
				if(objFile.getHashMapVariable()!=null){
					this.setHashMapVariable(objFile.getHashMapVariable());			
				}
			}
						
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelEncryptionIniSolverNew_
	
	//### aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ{
//		return KernelEncryptionIniSolverZZZ.sTAG_NAME;
//	}
//	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelEncryptionIniSolverZZZ.sTAG_PARENT_NAME;
//	}
	
	//+++++++++++++++++++++++++++++++++++++++++	
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //Somit ist das Parsen vom Solven entkoppelt. Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
	}
	
	
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder/Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{		
			boolean bEnabledThis = this.isParserEnabledThis();					
			bReturn = bEnabledThis;
		}
		return bReturn; 	
	}
	
	//### aus IParseUserZZZ
	@Override
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, String sExpressionIn) throws ExceptionZZZ {
		main:{
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
			IKernelConfigSectionEntryZZZ objEntry = null;
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			}else {
				objReturnReference = objReturnReferenceIn;
			}
			objEntry = objReturnReference.get();
			if(objEntry==null) {
				//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
				 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
				objEntry = new KernelConfigSectionEntryZZZ<T>();
				objReturnReference.set(objEntry);
			}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
		
			//#####################################################################
			//Flags entscheiden, ob es weiter geht			
			if(!this.isExpressionEnabledGeneral()) break main;
			if(!this.isParserEnabledGeneral()) break main;
			
			super.updateValueParseCustom(objReturnReference, sExpressionIn);
			if(!this.isParserEnabledCustom()) break main;
											
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
				objEntry.isEncrypted(true);
				this.getEntry().isEncrypted(true);
			}
		}//end main:
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabled
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		return false;
	}
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		return false;
	}
	
	
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}
	
	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;	
		String sReturnTag2Solve = null; String sReturnTagParsed = null; String sReturnTagSolved = null;	
		String sReturnLineParsed = null; String sReturnLineSolved = null; String sReturnLineSolved2compareWithParsed = null; String sReturnLineParsed2compareWithSolved = null;	
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();		
		boolean bUseExpression = false;	boolean bUseSolver = false; boolean bUseSolverThis = false;
				
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		
		this.updateValueSolveCalled();
		this.updateValueSolveCalled(objReturnReference);
		
		sReturnLine = sExpressionIn;
		this.setRaw(sReturnLine);
		objEntry.setRaw(sReturnLine);				
		sReturnLineParsed2compareWithSolved = sReturnLine;						
		sReturn = sReturnLine;
		sReturnTag = sReturnLine; //schlieslich ist das eine .solve ! PARSED ! Methode, also nicht   this.getValue();
		
		sReturnTagParsed = sReturnTag;
		sReturnTagSolved = sReturnTag;
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			String sExpression = sExpressionIn;
			
			bUseSolverThis = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolverThis) break main;
			
			//##################################
			//### START: Besonderheiten dieses Solvers
			//###################################		
			
			String sExpressionUsed = sExpression;
			
			String sCode=null;
			
			//Nun den z:cipher Tag suchen				
			KernelEncryption_CipherZZZ<T> objCipher = new KernelEncryption_CipherZZZ<T>();
			if(objCipher.isExpression(sExpression)){						
				String sRemaining = objCipher.parse(sExpression); //wichtig: Erst parsen, bevor man den Wert holen kann.
				String sCipher = objCipher.getValue();
				 
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//START: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++++++++
				EnumSet<?> objEnumSet = CryptEnumSetFactoryZZZ.getInstance().getEnumSet(sCipher);
				//wie verwenden???? EnumSetUtilZZZ.readEnumConstant_IndexValue(, "ROT13");
				 
				EnumSet<?> objEnumSet2 =CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.getEnumSet();					 
				CryptAlgorithmMappedValueZZZ objEnums = new CryptAlgorithmMappedValueZZZ();
				Class enumClass = objEnums.getEnumClassStatic();					
				int itest = EnumSetUtilZZZ.readEnumConstant_IndexValue(enumClass, "ROT13");	
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ itest="+itest);
			 
				String stest = EnumSetUtilZZZ.readEnumConstant_StringValue(enumClass, "ROT13");
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
				stest = EnumSetUtilZZZ.readEnumConstant_NameValue(enumClass, "ROT13");
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " Wert aus Enum-Klasse ueber EnumSetUtilZZZ stest="+stest);
				//ENDE: TODOGOON: WAS BRINGT NUN DIE ENUMERATION? +++++++++++++
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				//Nun mit diesem Schlüssel über eine Factory den SchlüsselAlgorithmus holen
				KernelCryptAlgorithmFactoryZZZ objFactory = KernelCryptAlgorithmFactoryZZZ.getInstance(objKernel);					 
				ICryptZZZ objAlgorithm = objFactory.createAlgorithmType(sCipher);
				this.setCryptAlgorithmType(objAlgorithm); //Damit kann der gefundene Wert durch einen anderen Wert ersetzt werden ohne die CryptAlgorithmFactoryZZZ neu zu bemuehen.
			 
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++++ Weitere Parameter suchen und ggfs. dem Algorithmusobjekt hinzufügen.
				//das ist einfacher als den konstruktor direkt aufzurufen
				//ICryptZZZ objAlgorithm = objFactory.createAlgorithmTypeByCipher(objKernel, sCipher, iKeyNumber, sCharacterPool);
										
				KernelEncryption_KeyNumberZZZ<T> objKeyNumber = new KernelEncryption_KeyNumberZZZ<T>();
				if(objKeyNumber.isExpression(sExpression)){					
					String sRemaining2 = objKeyNumber.parse(sExpression); //wichtig: Erst parsen, bevor man den Wert holen kann.
					String sKeyNumber = objKeyNumber.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyNumber)) {
						if(StringZZZ.isNumeric(sKeyNumber)) {
							int iKeyNumber = StringZZZ.toInteger(sKeyNumber);
							objAlgorithm.setCryptNumber(iKeyNumber);
						}								
					}													
			 	}
			 
			 	ZTagEncryption_KeyStringZZZ<T> objKeyString = new ZTagEncryption_KeyStringZZZ<T>();
				if(objKeyString.isExpression(sExpression)){					
					String sRemaining2 = objKeyString.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
					String sKeyString = objKeyString.getValue();
					if(!StringZZZ.isEmptyTrimmed(sKeyString)) {
							objAlgorithm.setCryptKey(sKeyString);											
					}													
			 	}
			 
			 					
				 KernelEncryption_CharacterPoolZZZ<T> objCharacterPool = new KernelEncryption_CharacterPoolZZZ<T>();
				 if(objCharacterPool.isExpression(sExpression)){					
				 	String sRemaining2 = objCharacterPool.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
				 	String sCharacterPool = objCharacterPool.getValue();
					if(!StringZZZ.isEmpty(sCharacterPool)) {//Merke: Leerzeichen wäre erlaubt								
						objAlgorithm.setCharacterPoolBase(sCharacterPool);
					}																											
			 	}
			 
				 KernelEncryption_CharacterPoolAdditionalZZZ<T> objCharacterPoolAdditional = new KernelEncryption_CharacterPoolAdditionalZZZ<T>();
				 if(objCharacterPoolAdditional.isExpression(sExpression)){					
				 	String sRemaining2 = objCharacterPoolAdditional.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
				 	String sCharacterPoolAdditional = objCharacterPoolAdditional.getValue();
					if(!StringZZZ.isEmpty(sCharacterPoolAdditional)) {							
						objAlgorithm.setCharacterPoolAdditional(sCharacterPoolAdditional);
					}																											
			 	}
			 
				//saFlagcontrol verarbeiten, Also der String ist mit "," getrennt. 
				Kernel_FlagControlZZZ<T> objFlagControl = new Kernel_FlagControlZZZ<T>();
				if(objFlagControl.isExpression(sExpression)){					
					String[] saControl = objFlagControl.parseAsArray(sExpression,",");						
					boolean[] baFound = objAlgorithm.setFlag(saControl, true);
					if(!ArrayUtilZZZ.isNull(baFound)) {
						int iCounter = -1;
						for(boolean bFound:baFound) {
							iCounter++;
							if(!bFound) {
								this.getLogObject().writeLineDate("Flag not available: '"+saControl[iCounter]+"'");
							}
						}
					}													
				 }
								 
			 	KernelEncryption_CodeZZZ<T> objValue = new KernelEncryption_CodeZZZ<T>();
				if(objValue.isExpression(sExpression)){
					this.getEntry().setValueEncrypted(sExpression);	//Zwischenstand festhalten
					String sRemaining2 = objValue.parse(sExpression);//wichtig: Erst parsen, bevor man den Wert holen kann.
					sCode = objValue.getValue();
//							String sDebug = vecValue.get(1).toString();;
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Value01=" + sDebug);
//							System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": Gesamt-Reststring soweit=" + sExpression);
				}
									                 
				 if(!StringZZZ.isEmpty(sCode)) {
					 //Das ist der reine kodierte Wert. Er gehört in objEntry.getValueEncrypted().
					 this.getEntry().isEncrypted(true);
					 objEntry.isEncrypted(true);
					 this.getEntry().setValueEncryptedPart(sCode);	//Zwischenstand festhalten
					 objEntry.setValueEncryptedPart(sCode);
					 sExpressionUsed = objAlgorithm.decrypt(sCode);
					 if(sExpressionUsed!=null) {
						if(!sExpressionUsed.equals(sCode)) {
							this.getEntry().isDecrypted(true);
							objEntry.isDecrypted(true);
							this.getEntry().setValueDecryptedPart(sExpressionUsed);
							objEntry.setValueDecryptedPart(sExpressionUsed);
						}							
					}					
				 }											
			}
			
			sReturnTag = sExpressionUsed;
			sReturnLine = sReturnTag;
			sReturn = sReturnTag;
			
			//##################################
			//### ENDE: Besonderheiten dieses Solvers
			//###################################
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);	
		}//end main:	
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	//Der Handler bekommt die ganze Zeile als Wert
		sReturn = sReturnLine;		
		
		if(objEntry!=null) {		
		objEntry.setValue(sReturn); 
		objEntry.setValueFromTag(sReturnTag);
		if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
		
		if(bUseExpression && bUseSolverThis) {
			if(sReturnTagSolved!=null) {				
				if(!sReturnTagSolved.equals(sReturnTagParsed)) {				
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objReturnReference);
				}
			}
			
			if(objEntry.isEncrypted()) {
				this.getEntry().setValueDecrypted(sReturn);
				objEntry.setValueDecrypted(sReturn);
			}
							
			
						
			}
			this.adoptEntryValuesMissing(objEntry);
		}
		return sReturn;				
	}
	
	//#########
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	//### aus IKernelEntryReferenceSolveUserZZZ
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> solvePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;		
		String sReturn = null;
		String sReturnTag = null; String sReturnLine;
		String sExpressionIn=null; String sExpression;	
		boolean bUseExpression  = false; boolean bUseSolverThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
		
		this.updateValueParseCalled();
		this.updateValueParseCalled(objReturnReference);
		
		sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
		sReturnLine = sExpressionIn;
		
		this.setRaw(sReturnLine);
		objEntry.setRaw(sReturnLine);	
		
		sReturn = sReturnLine;
		//sReturnTag = sReturnLine; //schlieslich ist das eine .solve ! PARSED ! Methode, also nicht   this.getValue();
		
		main:{	
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
			
			bUseSolverThis = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolverThis) break main;
					
			if(vecExpressionIn==null) break main;
			vecReturn=vecExpressionIn;	
							
			sReturnTag = (String) vecExpressionIn.get(1);//VectorUtilZZZ.implode(vecExpressionIn);
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			
			//Folgender speziellen code soll auch ohne vorherige Aufloesung etwas setzen, naemlich den Code selbst.
			if(StringZZZ.isEmpty(sReturnTag)){
				
				//Da gibt es wohl nix weiter auszurechen....	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String.
				KernelEncryption_CodeZZZ<T> objValue = new KernelEncryption_CodeZZZ<T>();
				if(objValue.isExpression(sReturnTag)){	
					objEntry.isEncrypted(true);
					objEntry.setValueEncrypted(sReturnLine);//Zwischenstand festhalten
					sReturnTag = objValue.parse(sReturnTag);															
				}																							
			}					
		}//end main:
	
			
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.
		if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag);
		if(sReturnTag!=null) this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturn);
			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			if(bUseExpression && bUseSolverThis) {
				if(objEntry.isEncrypted()) objEntry.setValueDecrypted(sReturn);//Zwischenstand festhalten
				if(sExpressionIn!=null) {							 							
					String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					if(!sExpression2Compare.equals(sReturnLine2Compare)) {
						this.updateValueParsedChanged();
						this.updateValueParsedChanged(objReturnReference);
					}
				}			
				
				this.adoptEntryValuesMissing(objEntry);
			}
		}
		return vecReturn;
	}
	
	
	//### Aus ISolveEnabled		
	
	
	

	//### Aus Interface IParseEnabledZZZ		
	@Override
	public String[] parseAsArray(String sExpression, String sDelimiter) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			
			//Ergaenzen der Elternmethode um das nachsehen in einem Flag
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				saReturn = super.parseAsArray(sExpression, sDelimiter);
			}else {
				saReturn = StringZZZ.explode(sExpression, sDelimiter);
			}		
		}//end main
		return saReturn;
	}
	
	
	
	//### Aus IKernelIniSolver
	/* (non-Javadoc)
	 * @see basic.zKernel.file.ini.AbstractKernelIniSolverZZZ#computeAsEntry(java.lang.String)
	 */
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{		
			
			//Ergaenzen der Elternmethode um das Nachsehen in einem Flag
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				objReturn = super.parseAsEntryNew(sExpression);
			}else {
				objReturn.setValue(sExpression);
			}									
		}//end main:
		return objReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {		
		return false;
	}
		
	//##########################
	//### FLAG Handling
	
	//### Aus Interface IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}//End class