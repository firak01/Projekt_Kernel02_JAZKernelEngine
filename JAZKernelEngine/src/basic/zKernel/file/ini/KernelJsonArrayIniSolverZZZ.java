package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.code.CryptEnumSetFactoryZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.crypt.code.KernelCryptAlgorithmFactoryZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.json.JsonUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 * @param <T>
 *
 */
public class KernelJsonArrayIniSolverZZZ<T> extends AbstractKernelIniSolver4ChildTagZZZ<T> implements IKernelJsonArrayIniSolverZZZ{
	private static final long serialVersionUID = 8458180798440285715L;
	public static final String sTAG_NAME = "JSON:ARRAY";
	public static final String sTAG_PARENT_NAME = KernelJsonIniSolverZZZ.sTAG_NAME;
			
	public KernelJsonArrayIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonArrayIniSolverNew_(null, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelJsonArrayIniSolverNew_(null, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJsonArrayIniSolverNew_(null, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonArrayIniSolverNew_(null, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelJsonArrayIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelJsonArrayIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(null, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonArrayIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelJsonArrayIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelJsonArrayIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonArrayIniSolverNew_(objFileIni, hmVariable);
	}
		
	private boolean KernelJsonArrayIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
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
	 }//end function KernelJsonMapIniSolverNew_
		
	//###### Getter / Setter
		
	public ArrayList<String> computeArrayList(String sExpressionIn) throws ExceptionZZZ{
		ArrayList<String> alsReturn = new ArrayList<String>();
		String sReturnTag = null; String sReturnLine = sExpressionIn; String sReturn = sReturnLine;
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseParser = this.isSolverEnabledGeneral();
			if(!bUseParser) break main;
			
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben			
			bUseParserThis = this.isSolverEnabledThis();
		    if(!bUseParserThis) break main;
			
		    String sExpression = sExpressionIn;
			
			Vector3ZZZ<String> vecReturn = this.parseFirstVector(sExpression);//Hole hier erst einmal die Variablen-Anweisung und danach die IniPath-Anweisungen und ersetze sie durch Werte.
			if(vecReturn==null) break main;
			
			//Sehr flexibel gehalten... Wenn auf 0: Dann waren die Tags nicht (mehr) drumherum
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			if(StringZZZ.isEmpty(sReturnTag)){
				sReturnTag = (String)vecReturn.get(0);								
			}
			if(StringZZZ.isEmpty(sReturnTag)) break main; //Dann ist der Tag-Wert nicht enthalten und es darf(!) nicht weitergearbeitet werden.
			this.setValue(sReturnTag);
			
			//20180714 Hole Ausdrücke mit <z:math>...</z:math>, wenn das entsprechende Flag gesetzt ist.
			//Beispiel dafür: TileHexMap-Projekt: GuiLabelFontSize_Float
			//GuiLabelFontSize_float=<Z><Z:math><Z:val>[THM]GuiLabelFontSizeBase_float</Z:val><Z:op>*</Z:op><Z:val><z:var>GuiZoomFactorUsed</z:var></Z:val></Z:math></Z>
			
			//Beschränke das ausrechnen auf den JSON-Array Teil  sReturn = VectorZZZ.implode(vecAll);//Erst den Vector der "übersetzten" Werte zusammensetzen
		
			if(this.getFlag("useFormula_math")==true){				
				//Dann erzeuge neues KernelExpressionMathSolverZZZ - Objekt.
				KernelZFormulaMathSolverZZZ objMathSolver = new KernelZFormulaMathSolverZZZ(); 
													
				//2. Ist in dem String math?	Danach den Math-Teil herausholen und in einen neuen vec packen.
				//Sollte das nicht für mehrerer Werte in einen Vector gepackt werden und dann immer weiter mit vec.get(1) ausgerechnet werden?
				while(objMathSolver.isExpression(sReturn)){
					String sValueMath = objMathSolver.parse(sReturn);
					sReturn=sValueMath;				
				}	
				//sReturn = VectorZZZ.implode(vecAll);
			}
			
			//ANSCHLIESSEND die ArrayList erstellen
			//if(!JsonUtilZZZ.isJsonValid(sReturn)) break main;
			
			
			//JsonArray ja = JsonEasyZZZ.toJsonArray(sReturn);
			alsReturn = JsonUtilZZZ.toArrayListString(sReturnTag);
			
			//Verwendete Lösung für die HashMap
//			TypeToken<HashMap<String, String>> typeToken = new TypeToken<HashMap<String, String>>(){};
//			hmReturn = JsonEasyZZZ.toHashMap(typeToken, sReturn);
			
			//Alternativer, generische Lösung
//			TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>(){};
//			alsReturn = JsonEasyZZZ.toArrayList(typeToken, sReturn);
			
			//Merke: Die Positionen vec(0) und vec(2) werden also dann entfallen.
		}//end main:
		return alsReturn;
	}
	
	//######### Interfaces #######################################################
	
//	//### aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return KernelJsonArrayIniSolverZZZ.sTAG_NAME;
//	}
//	
//	//### aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelJsonArrayIniSolverZZZ.sTAG_PARENT_NAME		;
//	}
	
	//+++++++++++++++++++++++++++++++++++++++++	
	//### aus IParseEnabled				
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
	 //Ziel ist es das Parsen vom Solven zu entkoppelt.
	 //Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();	 
	  boolean bReturn = false;
		main:{		  
		  bReturn = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
		  if(!bReturn) break main;		  
		}//end main:
		return bReturn;
	}
		
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {		
		//Ziel ist, dass Solver, die Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();
			boolean bEnabledJson = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
				
			bReturn = bEnabledJson || bEnabledThis; 
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
			
			
			//Nun, ggfs. wird .solve() nicht aufgerufen, in dem alle Tags richtig geparsed werden
			//weil sie ihrerseits mit .solve() ausgeführt werden.
			
			//DARUM:
			//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe auch KernelExpressionIniHandlerZZZ
			
			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
			
			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJsonIniSolverZZZ.sTAG_NAME, false)) {
				objEntry.isJson(true);
				this.getEntry().isJson(true);
			}
										
			if(!this.isParserEnabledThis()) break main;			
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
				objEntry.isJsonArray(true);
				this.getEntry().isJsonArray(true);
			}
		}//end main:		
	}

	//### aus ISolveUserZZZ
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueSolved(objReturnReference, true);		
	}
	
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolved(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolved(bIsSolveCalled);
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			objEntry.isJsonArraySolved(bIsSolveCalled);
		}
	}
	
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueSolveCalled(objReturnReference,true);
	}
	
	
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolveCalled(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolveCalled(bIsSolveCalled);
		
		//Den eigenen Solver
		objEntry.isJsonArraySolveCalled(bIsSolveCalled);
	}
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueSolvedChanged(objReturnReference, true);
	}
	
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolvedChanged(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isJsonSolvedChanged(bIsSolveCalled);
		
		//Den eigenen Solver
		objEntry.isJsonArraySolvedChanged(bIsSolveCalled);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{		  
		  bReturn = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
		  
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
			if(bReturn) break main;
		
		}//end main:
		return bReturn;
	}
	
	
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		return false;
	}
	
	
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sLineWithExpression
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
		
		ArrayList<String> alsReturn = null;
		
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
			
			//Berechnen der ArrayList aus einem vermeintlichen JSON-Ausdruck
			String sExpressionUsed = sExpression;
			alsReturn = this.computeArrayList(sExpressionUsed);
			if(alsReturn!=null) {
				sExpressionUsed = alsReturn.toString(); //ArrayListExtendedZZZ.computeDebugString(alsReturn);				
			}	
			sReturnTag = sExpressionUsed;
			sReturnTagSolved = sReturnTag;
			sReturnLine = sReturnTag;
			sReturn = sReturnLine;
				
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
			objEntry.setValue(sReturnLine);			
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			
			if(bUseExpression && bUseSolverThis) {
				if(objEntry.isEncrypted() && bUseSolverThis) objEntry.setValueDecrypted(sReturn);
				if(sReturnTagSolved!=null) {				
					if(!sReturnTagSolved.equals(sReturnTagParsed)) {				
						this.updateValueSolvedChanged();
						this.updateValueSolvedChanged(objReturnReference);
					}
				}
				
				if(alsReturn!=null) {					
					objEntry.isJsonArray(true);//Sollte das nicht im PARSE gesetzt werden?
					objEntry.setValue(alsReturn);//da das eine Wertzuweisung ist, die auf dem SOLVEN baisert, wird dasnicht beim PARSEN erledigt
				}else {
					objEntry.isJsonArray(false);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);										
		}
		return sReturn;				
	}

	

	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	
	//########################################################################
	//### FLAG - Handling
	
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

	
}//End class
