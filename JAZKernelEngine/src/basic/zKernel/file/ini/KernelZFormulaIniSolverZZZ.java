package basic.zKernel.file.ini;

import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelZFormulaIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = 7989209806367224848L;
	public static final String sTAG_NAME = "Z:formula";
	
	public KernelZFormulaIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelZFormulaIniSolverNew_(null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelZFormulaIniSolverNew_(null, null);
	}
		
	public KernelZFormulaIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelZFormulaIniSolverNew_(null, null);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelZFormulaIniSolverNew_(null, null);
	}

	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
		KernelZFormulaIniSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIni, hmVariable); //als IKernelUserZZZ - Object
	KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelZFormulaIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelZFormulaIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelZFormulaIniSolverNew_(objFileIni, hmVariable);
	}
	
	
	private boolean KernelZFormulaIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
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
						
			if(hmVariable!=null){				
				this.setVariable(hmVariable);			//soll zu den Variablen aus derm Ini-File hinzuaddieren, bzw. ersetzen		
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
	 }//end function KernelExpressionIniSolverNew_
	
	
	//### Andere Interfaces
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		return false;
	}

	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			bReturn = this.getFlag(IKernelZFormulaMathZZZ.FLAGZ.USEFORMULA_MATH);
			if(bReturn) break main;
			
			bReturn = true;
		}//end main;
		
		return bReturn;
	}

	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
		
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit MATH-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)		
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal Pfade oder Variablen ersetzt werden sollen.
	 * @param sExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;
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
	
		sReturnTag = this.getValue(); //??? sollte das nicht auch sReturnLine sein ???
		
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
					
			//Merke: Aufloesen von Pfaden und ini-Variablen passiert beim Parsen 

			//Aufloesen des Formula Tags (z.B. auch Math-Tags)
			sReturnLine = this.solveParsed_Formula_(sExpression, objReturnReference, bRemoveSurroundingSeparators);
			sReturnTag = this.getValue();
			objEntry = objReturnReference.get();															
		}//end main
	
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(bUseExpression && bUseSolver && bUseSolverThis) {
				objEntry.isSolved(true);				
			}				
			if(sExpressionIn!=null) {				
				if(!sExpressionIn.equals(sReturnLine)) {						
					objEntry.isSolvedChanged(true);
				}
			}
			
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;
	}


	//############################################
	private String solveParsed_Formula_(String sExpressionIn,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		//Merke: Vesuche das gleich zu halten mit AbstractKernelIniSolver.solve_()
		String sReturn = null; String sReturnLine = null; String sReturnTag = null; String sReturnTagParsed = null; String sReturnTagSolved = null;			
		String sExpression = null; String sTagParsed = "";
		//Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		
		boolean bUseExpression = false;	boolean bUseSolver = false; boolean bUseSolverThis = false;
		boolean bUseFormulaMath = false; //Abweichung zu AbstractKernelInisolver.solve_()
		
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
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);			
		objEntry.isSolveCalled(true);
		sReturnLine = sExpressionIn;
		sReturnTag = this.getValue();
		sReturnTagParsed = sReturnTag;
		sReturnTagSolved = sReturnTag;
		sReturn = sReturnLine;
		
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			sExpression = sExpressionIn;
		
			//!!! Pfade und Variablen ersetzen, wurde schon vorher gemacht !!!
									
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;	
			
			//sReturnTag = this.getValue();
			//sTagParsed = sExpression; //das soll ja der geparste Werte sein, s. Methodenname. 
			//sReturn = sExpression;
			
			//### Nun erst der MATH Teil, ggfs. mit ersetzten Variablen
			bUseSolver = this.isSolverEnabledThis();
			if(bUseSolver) {
				String sExpressionWithTags = sExpression;
			
						
				//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
				//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
				//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
				bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH); 
				if(bUseFormulaMath) {				
					//Analog zum vorgehen in parseFirstVector(...), nur hier wird vom JavaCallIniSolver.solveParsed() aufgerufen.
					sExpression = sExpressionWithTags;
					
					//Hier KernelZFormulaMathSolverZZZ verwenden
					//WICHTIG: DIE FLAGS VERERBEN !!!
					KernelZFormulaMathSolverZZZ<T> objMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objMathSolverDummy, true);
					
					//Variablen sind vorher schon aufgeloest, wir brauchen also keine HashMap mit den Variablen:	HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable();
					IKernelZZZ objKernel = this.getKernelObject();
					KernelZFormulaMathSolverZZZ<T> objMathSolver = new KernelZFormulaMathSolverZZZ<T>(objKernel, this.getFileConfigKernelIni(), saFlagZpassed);
																									
					//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
					String sExpressionWithTagsOld = sExpression;
					while(objMathSolver.isExpression(sExpressionWithTags)){
									
						//20250409 Statt objMathSolver.solve aufzurufen (in dem ja noch einmal ein parse gemacht wird solveParsed aufrufen und dahinter solveParsedWrapup
//						String sExpressionMathParsedAndSolved = objMathSolver.solve(sExpressionWithTags, bRemoveSurroundingSeparators);
//						if(sExpressionWithTags.equals(sExpressionMathParsedAndSolved)) break; //Sicherheitsmassnahme gegen Endlosschleife
//						sExpressionWithTags=sExpressionMathParsedAndSolved;	
//						sReturnTag = objMathSolver.getValue();			
												
						//Wahrscheinlich ist es guenstiger sofort mit dem bereits geparsten Wert weiterzuarbeiten. 
						String sExpressionMathParsedAndSolved = objMathSolver.solveParsed(sExpressionWithTags);						
						sExpressionMathParsedAndSolved = objMathSolver.solveParsedWrapup(sExpressionMathParsedAndSolved); //entspricht der solvePost()-Methode.
						
						if(sExpressionWithTags.equals(sExpressionMathParsedAndSolved)) break; //Sicherheitsmassnahme gegen Endlosschleife
						sExpressionWithTags=sExpressionMathParsedAndSolved;	
						sReturnTag = objMathSolver.getValue();	
						sReturnTagSolved = sReturnTag;
					}
					
										
//					if(!sExpressionWithTagsOld.equals(sExpressionWithTags)) {												
//						objEntry.setValueFormulaSolvedAndConverted(sReturnTag);	
//					}
					
					//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
					objEntry.isFormulaMathSolved(true);
					sReturnLine = sExpressionWithTags;
				}else {
					//Also: FORMULA-Tag soll aufgeloest werden, FORMULA-MATH aber nicht. 
					//Dann muss/darf nur der FORMULA-Tag entfernt werden. Eine weitere Aufloesung passiert ja nicht.					
					sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, KernelZFormulaIniSolverZZZ.sTAG_NAME);//also an jeder Position (d.h. nicht nur am Anfang), von innen nach aussen.
					sReturnTag = sReturnLine;
					sReturnTagSolved = sReturnTag;
				}//end if bUseFormulaMath					
			}//end if bUseFormula		
						
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen		
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
//			objEntry.setValueFormulaSolvedAndConverted(sReturnLine);
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
						
			if(bUseExpression && bUseSolver && bUseSolverThis) {
				if(objEntry.isEncrypted() && bUseSolverThis) objEntry.setValueDecrypted(sReturn);
				if(sReturnTagSolved!=null) {				
					if(!sReturnTagSolved.equals(sReturnTagParsed)) {				
						this.updateValueSolvedChanged();
						this.updateValueSolvedChanged(objReturnReference);
					}
				}				
			}			
				
			this.adoptEntryValuesMissing(objEntry);						
		}	
		return sReturn;	
	}
	
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //Somit ist das Parsen vom Solven entkoppelt. Letzteres wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
	}
	
	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();
			//boolean bEnabledJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			//boolean bEnabledJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
					
			bReturn = bEnabledThis;//| bEnabledJsonArray | bEnabledJsonMap;
		}
		return bReturn;
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
						
		if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
			objEntry.isFormula(true);
			this.getEntry().isFormula(true);
		}
		
		//20250526: Der KernelExpressionIniHandler macht folgendes:
		//          Beim SOLVEN wird jeder einzelne Solver aufgerufen, und darin wird auch jeder sein "parse" aufrufen.
		//          Nun kann man sich überlegen, ob man beim PARSEN auch jeden einzelnen Solver aufruft und seine "parse" Methode nutzt.
		//Alternativ kann man ein Dummy - Objekt verwenden und von jeder Klasse die "updateValueParseCustom" Methode aufrufen. 
		//Alternativ ohne ein Dummy - Objekt direkt die Tags parsen.
		
		//##########################.	
		boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);
//		boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
		if(!bUseFormulaMath) break main; // | bUseJsonArray )) break main;
		
	
		if(bUseFormulaMath) {				
			KernelZFormulaMathSolverZZZ<T> formulaMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, formulaMathSolverDummy, true);
				
			KernelZFormulaMathSolverZZZ<T> objFormulaMathSolver = new KernelZFormulaMathSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
			objFormulaMathSolver.updateValueParseCustom(objReturnReference, sExpression);
		}
			
								
//		if(bUseJsonMap){
//			KernelJsonMapIniSolverZZZ<T> jsonMapSolverDummy = new KernelJsonMapIniSolverZZZ<T>();
//			String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonMapSolverDummy, true);
//			
//			KernelJsonMapIniSolverZZZ<T> objJsonMapSolver = new KernelJsonMapIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
//			objJsonMapSolver.updateValueParseCustom(objReturnReference, sExpression);
//		}
	
		
		//################################################################################################
		//Alternativ kann man hier verkürzt alle möglichen Tags aufnehmen...
		
		//DARUM:
		//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe z.B. auch KernelCallIniSolverZZZ			
		//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
		
		//#####################################################################
//		objEntry = objReturnReference.get();			
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isCall(true);
//			this.getEntry().isCall(true);
//		}
//		
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isJavaCall(true);
//			this.getEntry().isJavaCall(true);
//		}
		
		//#####################################################################
		
		
	
	}//end main
	}


	//### aus IConvertableZZZ
		@Override
		public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {		
			return false;
		}

	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//	 return KernelZFormulaIniSolverZZZ.sTAG_NAME;
//	}
//	
//	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelZFormulaIniSolverZZZ.sTAG_PARENT_NAME;
//	}
	
	//### aus IKernelEntryExpressionUserZZZ
	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, null, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, true);
//	}
//	
//	@Override
//	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
//		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
//	}
//	
//	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		
//		String sReturn = sExpressionIn;
//		String sExpressionUsed = sExpressionIn;
//
//		main:{		
//			if(StringZZZ.isEmpty(sExpressionIn)) break main;
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//			if(!this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA)) break main;			
//					
//			
//			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
//			if(objReturnReferenceIn==null) {	
//				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//			}else {
//				objReturnReference = objReturnReferenceIn;
//				objEntry = objReturnReference.get();
//			}
//			if(objEntry==null) {
//				objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
//											 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
//				objReturnReference.set(objEntry);								
//			}//Achtung: Das objReturn Objekt NICHT generell versuchen mit .getEnry() und ggfs. dann darin .getEntryNew89 uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
//			objEntry.setRaw(sExpressionIn);
//			
//			vecReturn = StringZZZ.vecMidFirst(sExpressionUsed, this.getTagStarting(), this.getTagClosing(), true, false);			
//			sExpressionUsed = vecReturn.get(1).toString();
//			sReturn = sExpressionUsed;
//			objEntry.setValueAsExpression(sReturn); //nicht noch andere Z-Tags rumsetzen
//			
//			
//			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
//			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
//			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
//			if(this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH)) {			
//																						
//				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
//				KernelZFormulaMathSolverZZZ<T> objMathSolverDummy = new KernelZFormulaMathSolverZZZ<T>();
//				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objMathSolverDummy, true);
//				HashMapCaseInsensitiveZZZ<String,String>hmVariable = this.getHashMapVariable(); //wird wohl nicht benutzt, noch nicht...
//				
//				KernelZFormulaMathSolverZZZ<T> objMathSolver = new KernelZFormulaMathSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
//				 														
//				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.								
//				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceMathSolver = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
//				objReturnReferenceMathSolver.set(objEntry);
//
//				String sExpressionTemp;				
//				while(objMathSolver.isExpression(sExpressionUsed)){
//					sExpressionTemp = objMathSolver.solve(sExpressionUsed, objReturnReferenceMathSolver, true); //die Z-Math Tags rausnehmen
//					
//					if(StringZZZ.isEmpty(sExpressionTemp)){
//						break;
//					}else if(sExpressionUsed.equals(sExpressionTemp)) {
//						break;
//					}else {
//						sExpressionUsed = sExpressionTemp;
//					}											
//				}//end while
//				sReturn = sExpressionUsed;
//				
//				this.setValue(sReturn);
//				vecReturn.replace(sReturn);
//				objEntry.setValue(sReturn);
//				if(sReturn!=sExpressionIn) {
//					objEntry.isParsed(true);
//					objEntry.isFormulaMathSolved(true);
//				}				
//			}//end if flag useFormulaMath
//						
//			//Als echten Ergebniswert aber die Z-Tags rausrechnen
//			//An dieser Stelle die Tags vom akuellen "Solver" Rausnehmen
//			//AUSSER: Die <Z>-Tags sind am Anfang/Ende UND(!) es sind noch andere Formel Z-Tags "<Z:... im String vorhanden
//			if(bRemoveSurroundingSeparators) {
//				String sTagStart = this.getTagStarting();
//				String sTagEnd = this.getTagClosing();
//				String sValue = KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(sReturn, sTagStart, sTagEnd);												
//				sReturn = sValue;
//			}			
//		}//end main:
//		
//		vecReturn.replace(sReturn);
//		
//		//Z-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
//		//...aber nur, wenn ein Pfad gefunden wurde.
//		if(bRemoveSurroundingSeparators) {
//			String sTagStart="<Z>";
//			String sTagEnd="</Z>";
//			KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemoved(vecReturn, sTagStart, sTagEnd);
//		}
//
//		this.setValue(vecReturn.get(1).toString());
//		if(objEntry!=null) {
//			objEntry.setValue(sReturn);	
//			if(sExpressionIn!=null) {
//				if(!sExpressionIn.equals(sReturn)) objEntry.isParsed(true);
//			}				
//			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
//		}			
//		return vecReturn;
//	}
	
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
		boolean bUseExpression = false;  boolean bUseSolverThis = false;
		
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
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
						
			//BESONDERHEIT, die nur zieht, wenn ein Formel-Tag vorhanden war.	
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			sReturnLine = sExpressionIn;
								
			objEntry.setValueFormulaSolvedAndConverted(sReturnTag);					
			
			//DER GESAMTAUSDRUCK MUSS ABER REIN
			objEntry.setLineFormulaSolvedAndConverted(sReturnLine);			
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
	
	
	

	//########################################################
	//### FLAG Handling
	
	//### aus IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelZFormulaIniZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIniZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelZFormulaIniZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIniZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIniZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelZFormulaIniZZZ:objaEnum_IKernelZFormulaIniZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIniZZZ, bFlagValue);
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
	
	//### aus IKernelZFormulaIni_PathZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelZFormulaIni_PathZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_PathZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_PathZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ:objaEnum_IKernelZFormulaIni_PathZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	

	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	
	
	//### aus ISolveUserZZZ
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsFormulaSolveCalled) throws ExceptionZZZ{
		super.updateValueSolveCalled(objReturnReference, bIsFormulaSolveCalled);
		
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308		
		//objEntry.isFormulaSolveCalled(bIsFormulaSolveCalled);//der Aufruf findet immer statt, auch ohne gesetztes Flag
		
		//Den eigenen Solver
		objEntry.isFormulaSolveCalled(bIsFormulaSolveCalled);
	}
	
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsFormulaSolved) throws ExceptionZZZ{
		super.updateValueSolved(objReturnReference, bIsFormulaSolved);
		
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308		
//		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
//			objEntry.isCallSolved(bIsSolveCalled);
//		}
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isFormulaSolved(bIsFormulaSolved);
		}
	}
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsFormulaSolvedChanged) throws ExceptionZZZ{
		super.updateValueSolvedChanged(objReturnReference, bIsFormulaSolvedChanged);
				
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308		
//		if(this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL)){
//			objEntry.isCallSolvedChanged(bIsSolveChanged);
//		}
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isFormulaSolvedChanged(bIsFormulaSolvedChanged);
		}
	}

}//End class
