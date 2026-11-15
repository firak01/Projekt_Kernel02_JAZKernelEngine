package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Klasse, welche die "Ini-Solver" Klassen anwendet.
 *  Somit ist sie die "Ausgangsklasse" für die FileIniZZZ-Methoden
 *  einen Wert zu errechnen.
 * 
 * @author Fritz Lindhauer, 02.05.2023, 19:55:30
 * 
 */
public class KernelExpressionIniHandlerZZZ<T>  extends AbstractKernelIniSolverZZZ<T> implements IKernelExpressionIniHandlerZZZ {
	private static final long serialVersionUID = -6430027792689200422L;
	public static final String sTAG_NAME = "Z";
		
	public KernelExpressionIniHandlerZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniHandlerNew_(null, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, null);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	public KernelExpressionIniHandlerZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	public KernelExpressionIniHandlerZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniHandlerNew_(objFileIni, hmVariable);
	}
	
	
	private boolean KernelExpressionIniHandlerNew_(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
	 boolean bReturn = false;	
	 main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
		
			if(objFileIni==null ){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez; 
			}else{
				this.setFileConfigKernelIni(objFileIni);						
			}
			
			if(hmVariable!=null){				
					this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
			}else {
				if(hmVariable!=null){
					this.setHashMapVariable(hmVariable);
				}
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionIniSolverNew_
	
	//### STATIC METHODS
	//Fuer den statischen Zugriff, als Alternative zu der Variante bei einem initialisierten Object obj.makeAsExpression(String sValue)....
	public static String makeAsExpressionDefault(String sValue) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.makeAsExpression(sValue, KernelExpressionIniHandlerZZZ.sTAG_NAME);
	}
	
	
	//### GETTER / SETTER
	
	
	//######### Interfaces #############################################
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		//das wäre default, s. Solver:  return this.isSolverEnabledThis();
		return this.getFlag(IKernelExpressionIniParserZZZ.FLAGZ.USEEXPRESSION_PARSER); 
	}	
	
	//### Aus IParseUserZZZ
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
			
			super.updateValueParseCustom(objReturnReference, sExpression); //isExpression setzen			
			if(!this.isParserEnabledCustom()) break main;
			boolean bUseParser = true;
			
			
			
			//20250526: Der KernelExpressionIniHandler macht folgendes:
			//          Beim SOLVEN wird jeder einzelne Solver aufgerufen, und darin wird auch jeder sein "parse" aufrufen.
			//          Nun kann man sich überlegen, ob man beim PARSEN auch jeden einzelnen Solver aufruft und seine "parse" Methode nutzt.
			//Alternativ kann man ein Dummy - Objekt verwenden und von jeder Klasse die "updateValueParseCustom" Methode aufrufen. 
			//Alternativ ohne ein Dummy - Objekt direkt die Tags parsen.
			
			//##########################.
			if (!this.isSolverEnabledAnyRelevant()) break main;
			
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			boolean bUseJavaCall = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
			boolean bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);				
			if(!(bUseFormula | bUseCall | bUseJavaCall | bUseJson | bUseJsonArray | bUseJsonMap | bUseEncryption )) break main;
			
			
			//Merke: Auch wenn die Solver wg. des USE... Flags nicht laufen werden, so wird das Parsen immer gemacht
			//       !!! Aber nur bis zur naechsten "Parent-Tag-Grenze". D.h. isJson wird gesetzt werden auch ohne aktives Json. isJsonMap allerdings nicht!!!! 
			if(bUseParser | bUseFormula) {				
				KernelZFormulaIniSolverZZZ<T> formulaSolverDummy = new KernelZFormulaIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaSolverDummy, true);
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
					
				KernelZFormulaIniSolverZZZ<T> objFormulaSolver = new KernelZFormulaIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), hmVariable, saFlagZpassed);
				objFormulaSolver.updateValueParseCustom(objReturnReference, sExpression);
			}
				
									
			if(bUseParser | bUseCall | bUseJavaCall){
				
				if(bUseJavaCall) {  //nur einen von beiden ausfuehren
					KernelJavaCallIniSolverZZZ<T> callSolverDummy = new KernelJavaCallIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callSolverDummy, true);
					
					KernelJavaCallIniSolverZZZ objCallSolver = new KernelJavaCallIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
					objCallSolver.updateValueParseCustom(objReturnReference, sExpression);					
				}else { //Wenn JavaCall ausgefuehrt werden soll, dann wird isCall aus dem ElternTag auch gesetzt.
					KernelCallIniSolverZZZ<T> callSolverDummy = new KernelCallIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callSolverDummy, true);
					
					KernelCallIniSolverZZZ objCallSolver = new KernelCallIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
					objCallSolver.updateValueParseCustom(objReturnReference, sExpression);
				}
			}
			
		
			if(bUseParser | bUseJson | bUseJsonMap | bUseJsonArray) {
				
				if(bUseJsonMap | bUseJsonArray) { 
					if(bUseJsonMap) {
						KernelJsonMapIniSolverZZZ<T> jsonSolverDummy = new KernelJsonMapIniSolverZZZ<T>();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonSolverDummy, true); //this.getFlagZ_passable(true, exDummy);					
									
						KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
						objJsonMapSolver.updateValueParseCustom(objReturnReference, sExpression);		
					}
					
					if(bUseJsonArray) {
						KernelJsonArrayIniSolverZZZ<T> jsonSolverDummy = new KernelJsonArrayIniSolverZZZ<T>();
						String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonSolverDummy, true); //this.getFlagZ_passable(true, exDummy);					
									
						KernelJsonArrayIniSolverZZZ objJsonArraySolver = new KernelJsonArrayIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
						objJsonArraySolver.updateValueParseCustom(objReturnReference, sExpression);
					}
					
				}else {
					KernelJsonIniSolverZZZ<T> jsonSolverDummy = new KernelJsonIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonSolverDummy, true); //this.getFlagZ_passable(true, exDummy);					
				
					KernelJsonIniSolverZZZ objJsonSolver = new KernelJsonIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
					objJsonSolver.updateValueParseCustom(objReturnReference, sExpression);
				}
				
			}								
									
			if(bUseParser | bUseEncryption) {
				KernelEncryptionIniSolverZZZ<T> encryptionDummy = new KernelEncryptionIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, encryptionDummy, true);
				
				KernelEncryptionIniSolverZZZ objEncryptionSolver = new KernelEncryptionIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
				objEncryptionSolver.updateValueParseCustom(objReturnReference, sExpression);
			}			
			
			
			
			//################################################################################################
			//Alternativ kann man hier verkürzt alle möglichen Tags aufnehmen...
			
			//DARUM:
			//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe z.B. auch KernelCallIniSolverZZZ			
			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			
			//#####################################################################
//			objEntry = objReturnReference.get();			
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelCallIniSolverZZZ.sTAG_NAME, false)) {
//				objEntry.isCall(true);
//				this.getEntry().isCall(true);
//			}
//			
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//				objEntry.isJavaCall(true);
//				this.getEntry().isJavaCall(true);
//			}
			
			//#####################################################################
			
			
		
		}//end main:
	}
	
	//### aus ISolveZZZ
	//+++++++++++++++++++++++++++++++++++++++++++++
		//+++ Folgende Methoden koennen ueberschrieben werden um fuer den konkreten Solver eine Loesung einzubauen.
		//### aus ISolveZZZ
		@Override
		public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
			return this.solvePostCustom_(vecExpression, null, true);
		}

		@Override
		public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
			return this.solvePostCustom_(vecExpression, null, bRemoveSurroundingSeparators);
		}
		
		@Override
		public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
			return this.solvePostCustom_(vecExpression, objReturnReference, true);
		}
		
		@Override
		public Vector3ZZZ<String> solvePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
			return this.solvePostCustom_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
		}
		
		//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
		//!!! hier wuerde dann etwas konkretes stehen.
		private Vector3ZZZ<String> solvePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
			Vector3ZZZ<String> vecReturn = vecExpressionIn;		
			String sReturn = null;
			String sReturnTag = null; String sReturnLine = null;
			String sExpressionIn=null;
			boolean bUseExpression = false; boolean bUseSolverThis = false;
				
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
				
		
			main:{	
				if(vecExpressionIn==null) break main;
					
				//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
				//!!! hier wuerde dann etwas konkretes stehen.
				
//				sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);

//				sReturnTag = vecReturn.get(1).toString();
//				sReturnLine = VectorUtilZZZ.implode(vecReturn);
							
				//BESONDERHEIT				
//				if(!sExpressionIn.equals(sReturnLine)) {
//					objEntry.setValueFormulaSolvedAndConverted(sReturnTag);					
//				}
				
				//Besonderheit:
				//Wurde in der Gesamtexpression-Behandlung ein Formula-Handler aufgerufen, 
				//dann muss die Gesamtzeile "LineFormulaSolvedAndConverted" aktualisiert werden.				
				if(objEntry.isFormulaSolved()) {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Uebernim den Gesamtstring in den Vector zur formulaSolvedAndConverted.");
					sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
					String sLineFormulaSolvedAndConverted = sExpressionIn;
					objEntry.setLineFormulaSolvedAndConverted(sLineFormulaSolvedAndConverted);
				}
				
				
				
			}//end main:
			
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
	
	//### aus  ISolveEnabledZZZ
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			
			//Frage hier die Flags ab, s. solveParsed_ 
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			if(bUseFormula) break main;
			
			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) break main;
			
			
			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			if(bUseJson) break main;
			
			
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption ) break main;
					
			bReturn = false;
		}//end main:		
		return bReturn;
	}

	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			
			//Frage hier die Flags ab, s. solveParsed_ 
			boolean bUseJavaCall = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bUseJavaCall) break main;
			
			boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
			if(bUseJsonMap) break main;
					
			boolean bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			if(bUseJsonArray) break main;
					
			bReturn = false;
		}//end main:		
		return bReturn;
	}
		
	/** Berechne die INI-Werte. 
	 *  Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * 	 
	 *  STRATEGIE:
	 *  1. Ini-Variablen und Pfade aufloesen (wird im solve(...) gemacht.
	
   *  Hier also:	
	 *  2. JavaCalls aufloesen
	 *  3. JSON Werte
	 *  4. Entschluesseln 			 
	 *  
	 *  IDEE: 
	 *  Die Entschluesselung zuletzt. Damit kann z.B. theoretisch in den JSON-Werten verschluesselter Inhalt enthalten sein.
	 *  
	 * @param sExpression
	 * @param objReturnReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 06.05.2023, 07:41:02
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
		String sReturn = null; String sReturnLine = null; String sReturnTag = null; String sReturnTagParsed = null; String sReturnTagSolved = null;
		boolean bUseExpression = false; boolean bUseSolver = false; boolean bUseSolverThis = false;
		
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
		sReturn = sReturnLine;				
		sReturnTag = sReturnLine; //nein, schliesslich heisst diese Methode solve ! parsed ! //this.getValue();
		
		sReturnTagParsed = sReturnTag;
		sReturnTagSolved = sReturnTag;
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;			
			
			bUseExpression = this.isExpressionEnabledGeneral();			
			if(!bUseExpression) break main;
			
			String sExpression = sExpressionIn;
			
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			//Löse den eigenen und anderen Solver auf.
			bUseSolverThis = this.isSolverEnabledAnyRelevant(); 		
			if(!bUseSolverThis) break main;			
			
			
			//##################################
			//### Besonderheiten dieses Solvers
			//###################################
			
			//Merke: Die statischen Methoden leisten mehr als nur die ...Solver....
			//       Durch den boolschen Rückgabwert geben sie an, ob in "DIESEM LAUF" etwas ersetzt wurde.
			//       Das ist unabhaengig vom objEntry.
			//       IDEE: Wenn etwas z.B. Verschluesselt war, dann kann in einem 2. Lauf darin ggfs. JSON als entschlüsselter Wert gefunden werden.
			//             Nun wuerde im 2. Lauf JSON verarbeitet, etc. 
			
			String sExpressionUsed = sExpression;
			boolean bUseEncryption = this.getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ.USEENCRYPTION);
			if(bUseEncryption) {
				KernelEncryptionIniSolverZZZ<T> encryptionDummy = new KernelEncryptionIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, encryptionDummy, true);
								
				boolean bForFurtherProcessing = false;//Auf ExpressionHandler-Ebene entferne die Z-Tags der einzelnen Solver
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceEncryption = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceEncryption.set(objEntry);
				boolean bAnyEncryption = KernelConfigSectionEntryUtilZZZ.getEncryptionSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseEncryption, bForFurtherProcessing, saFlagZpassed, objReturnReferenceEncryption);
				objEntry = objReturnReferenceEncryption.get();
				
				//TODOGOON20250809;//Die entry-Werte sollen durch den Solver in der statischen Methode gesetzt werden!!!!
				if(bAnyEncryption) {
					
					objEntry.isRawEncrypted(true);
					String sLineDecrypted = objEntry.getRawDecrypted();//Wert zur weiteren Verarbeitung weitergeben						
					if(!sExpressionUsed.equals(sLineDecrypted)) {													
						objEntry.isDecrypted(true);
						objEntry.setRawDecrypted(sLineDecrypted);
						objEntry.setValue(sLineDecrypted);       //quasi erst mal den Zwischenstand festhalten.							
					}//Merke: Keinen Else-Zweig zum false setzen. Vielleicht war in einem vorherigen Schritt ja durchaus Verschluesselung enthalten
					
					sExpressionUsed = sLineDecrypted; //Zur Verarbeitung weitergeben			
				}//Merke: Keinen Else-Zweig. Vielleicht war in einem vorherigen Schritt ja durchaus Encryption enthalten
			}//end if buseencryption	
			
			
			
			boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			if(bUseFormula) {				
				KernelZFormulaIniSolverZZZ<T> formulaSolverDummy = new KernelZFormulaIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaSolverDummy, true);
				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
					
				KernelZFormulaIniSolverZZZ<T> objFormulaSolver = new KernelZFormulaIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), hmVariable, saFlagZpassed);
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceFormula = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceFormula.set(objEntry);
				
				//TODOGOON20250809;//Rueckgabewert muss boolean sein!!!!
				sExpressionUsed = objFormulaSolver.solve(sExpressionUsed, objReturnReferenceFormula, true); //Auf ExpressionHandler-Ebene entferne die Z-Tags der einzelnen Solver
				objEntry = objReturnReferenceFormula.get();				
			}//end bUseFormula
				
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);				
			if(bUseCall){
				KernelCallIniSolverZZZ<T> callDummy = new KernelCallIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, callDummy, true);
								
				boolean bForFurtherProcessing = false; //Auf ExpressionHandler-Ebene entferne die Z-Tags der einzelnen Solver
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolverCall.set(objEntry);
				
				boolean bAnyCall = KernelConfigSectionEntryUtilZZZ.getCallSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseCall, bForFurtherProcessing, saFlagZpassed, objReturnReferenceSolverCall);
				objEntry = objReturnReferenceSolverCall.get();	
 
				String sLineDecalled = objEntry.getValueCallSolved();//Wert zur weiteren Verarbeitung weitergeben						
				sExpressionUsed = sLineDecalled; 		
			}//end if busecall
			
			
			
			
			//20250814: Problem - Hier wird nur auf das Flag abgeprüft.
			//          im KernelJsonIniSolverZZZ ist unter dem Test aber USE_JSONMAP aktiviert. 
			//          D.h. der KernelJsonIniSolver würde die Auflösung machen (weil er zwar nicht aber das Kind aktivert ist)            
			boolean bUseJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			if(bUseJson) {
				KernelJsonIniSolverZZZ<T> exDummy03 = new KernelJsonIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolverJson = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolverJson.set(objEntry);
								
				ReferenceArrayZZZ<String>objalsReturnValueJsonSolved=new ReferenceArrayZZZ<String>();
				ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved=new ReferenceHashMapZZZ<String,String>();
								
				boolean bForFurtherProcessing = false; //Auf ExpressionHandler-Ebene entferne die Z-Tags der einzelnen Solver
				boolean bAnyJson = KernelConfigSectionEntryUtilZZZ.getJsonSolved(this.getFileConfigKernelIni(), sExpressionUsed, bUseJson, bForFurtherProcessing, saFlagZpassed, objReturnReferenceSolverJson, objalsReturnValueJsonSolved,objhmReturnValueJsonSolved);					
				objEntry = objReturnReferenceSolverJson.get();
				sExpressionUsed = objEntry.getValue(); //Zur Verarbeitung weitergeben
			}//end if busejson									
								
			sReturnLine = sExpressionUsed;	
			sReturnTagSolved = sReturnLine;
			
			//TODOGOON20250809;//Idee: wenn eine der statischen Methoden - die den Solver aufruft - als boolschen Wert true zurueckliefert
			                   //      Dann müssen alle anderen Solver erneut aufgerufen werden, da ggfs. deren Werte sich nun aendern.
			                   //      z.B. vorher war etwas verschluesselt.
			                   //      Also die Schleife nur verlassen, wenn alle als Bool-Wert false zurückliefern.
							   //
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
						
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		sReturn = sReturnLine;
		this.setValue(sReturn);	//Der Handler bekommt die ganze Zeile als Wert	
		if(objEntry!=null) {
			objEntry.setValue(sReturn);
			objEntry.setValueFromTag(sReturn);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			if(sReturnTagSolved!=null) {				
				if(!sReturnTagSolved.equals(sReturnTagParsed)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objReturnReference);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);
		}
		return sReturn;				
	}

	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() {
//		return KernelExpressionIniHandlerZZZ.sTAG_NAME;
//	}
//	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return null;
//	}
	
	//### Interface aus IKernelExpressionIniSolver
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ {
		if(this.objEntryInner==null) {
			this.objEntryInner = new KernelConfigSectionEntryZZZ<T>(this);			
		}
		return this.objEntryInner;
	}
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		this.objEntryInner = objEntry;
	}
	
	//##############################################
	//### FLAG HANDLING
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	

	@Override
	public boolean proofFlagExists(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIniZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}


	//### Aus Interface IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelEncryptionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelEncryptionIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelEncryptionIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
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

	//### Aus IKernelJsonMapIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IKernelJsonMapIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelJsonMapIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonMapIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	//### aus IKernelJsonArrayIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
	
	@Override
	public boolean proofFlagExists(IKernelJsonArrayIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonArrayIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### aus IKernelJsonIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelJsonIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
	
	@Override
	public boolean proofFlagExists(IKernelJsonIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelJsonIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	//### aus IKernelCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
		public boolean[] setFlag(IKernelCallIniSolverZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}
	
	@Override
	public boolean proofFlagExists(IKernelCallIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//### Aus Interface IKernelJavaCallIniSolverZZZ
	@Override
	public boolean getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelJavaCallIniSolverZZZ.FLAGZ[] objaEnum_IKernelJavaCallIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelJavaCallIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelJavaCallIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ:objaEnum_IKernelJavaCallIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelJavaCallIniSolverZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelJavaCallIniSolverZZZ.FLAGZ objEnum_IKernelJavaCallIniSolverZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelJavaCallIniSolverZZZ.name());
	}

	@Override
	public boolean proofFlagSetBefore(IKernelJavaCallIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
