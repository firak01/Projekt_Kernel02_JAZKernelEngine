package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelJsonIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelJsonIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelJsonMapIniSolveUserZZZ,  IKernelJsonArrayIniSolverZZZ, IKernelJsonArrayIniSolveUserZZZ{
	private static final long serialVersionUID = 6588176234400554782L;
	public static final String sTAG_NAME = "JSON";
	
	public KernelJsonIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(null, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag);//als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, null);
	}
		
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(objFileIni, null);
	}
	
	public KernelJsonIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelJsonIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelJsonIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelJsonIniSolverNew_(objFileIni, hmVariable);
	}
			
	private boolean KernelJsonIniSolverNew_(FileIniZZZ objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
	 boolean bReturn = false;
	 String stemp; boolean btemp; 
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
	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	public HashMap<String,String> computeHashMap(String sExpression) throws ExceptionZZZ{
		HashMap<String,String>hmReturn=new HashMap<String,String>();				
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main; 			
			if(!this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP)) break main;				
		
			//Dann erzeuge neues KernelJsonMapSolverZZZ - Objekt.			
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
				
			KernelJsonMapIniSolverZZZ objJsonMapSolver = new KernelJsonMapIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonMapSolver, true);
			objJsonMapSolver.setFlag(saFlag,true);	
			String sLineSolved = objJsonMapSolver.solve(sExpression); //ABER: Das ist ein String ohne die wichtigen { }. Ohne geschweifte Klammern keine JSON-Map!!! 
			String sLineWithExpression = objJsonMapSolver.getValue();
			hmReturn=objJsonMapSolver.getValueHashMap();
			//hmReturn=objJsonMapSolver.computeHashMapFromJson(sLineWithExpression);							
		}//end main:
		this.setValue(hmReturn);
		return hmReturn;
	}
	
	public ArrayList<String> computeArrayList(String sExpression) throws ExceptionZZZ{
		ArrayList<String>alsReturn=new ArrayList<String>();				
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(!this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON)) break main; 			
			if(!this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY)) break main;				
		
			//Dann erzeuge neues KernelJsonArraySolverZZZ - Objekt.		
			FileIniZZZ objFileIni = this.getFileConfigKernelIni();
			
			KernelJsonArrayIniSolverZZZ objJsonArraySolver = new KernelJsonArrayIniSolverZZZ(objFileIni);
			String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, objJsonArraySolver, true);
			objJsonArraySolver.setFlag(saFlag,true);
			String sLineWithExpression = objJsonArraySolver.solve(sExpression);
			alsReturn=objJsonArraySolver.computeArrayList(sLineWithExpression);																					
		}//end main:
		this.setValue(alsReturn);		
		return alsReturn;
	}
	

	//######### Interfaces #######################################################
	
	//### aus ITagBasicZZZ
//	@Override
//	public String getNameDefault(){
//		return KernelJsonIniSolverZZZ.sTAG_NAME;
//	}
//	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelJsonIniSolverZZZ.sTAG_PARENT_NAME;
//	}
	
	//### aus IParseEnabled		
	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledThis = this.isParserEnabledThis();					
			bReturn = bEnabledThis;
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
			objEntry.isJson(true);
			this.getEntry().isJson(true);
		}
				
		//########################
		//Nun, ggfs. wird .solve() nicht aufgerufen, in dem alle Tags richtig geparsed werden
		//weil sie ihrerseits mit .solve() ausgeführt werden.
			
		//DARUM:
		//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe auch KernelExpressionIniHandlerZZZ
		//Das kann man auch 
		//a) durch die Flags steuern
		//b) durch direkten Aufruf der "Untersolver".custom-Methode
		//c) durch Analyse der Tags
	
//		//a)
//		boolean bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
//		boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
//		if(!(bUseJsonMap | bUseJsonArray )) break main;
//							
		
		String[] saFlagZpassed=null;
//		if(bUseJsonArray) {				
		KernelJsonArrayIniSolverZZZ<T> jsonArraySolverDummy = new KernelJsonArrayIniSolverZZZ<T>();
		saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonArraySolverDummy, true);
			
		KernelJsonArrayIniSolverZZZ<T> objJsonArraySolver = new KernelJsonArrayIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
		objJsonArraySolver.updateValueParseCustom(objReturnReference, sExpression);
//		}
		
							
//		if(bUseJsonMap){
		KernelJsonMapIniSolverZZZ<T> jsonMapSolverDummy = new KernelJsonMapIniSolverZZZ<T>();
		saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, jsonMapSolverDummy, true);
		
		KernelJsonMapIniSolverZZZ<T> objJsonMapSolver = new KernelJsonMapIniSolverZZZ(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
		objJsonMapSolver.updateValueParseCustom(objReturnReference, sExpression);
//		}
	
		
//		//c)
//		//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
//		if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//			objEntry.isJavaCall(true);
//			this.getEntry().isJavaCall(true);
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


	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		  bReturn = this.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
		  if(!bReturn) break main;
	  
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		return false;
	}
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		  bReturn = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
		  if(bReturn) break main;
	  
		  bReturn = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
		  if(bReturn) break main;
	  		 
		}//end main:
		return bReturn;
	}

	
	
	/**Methode ueberschreibt die Aufloesung von Pfaden und Ini-Variablen.
	 * @param sLine
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
		
		boolean bUseJsonArray = false; boolean bUseJsonMap = false;							
		ArrayList<String> alsReturn = null;
		HashMap<String,String> hmReturn = null;
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
								
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
						
			bUseSolverThis = this.isSolverEnabledEveryRelevant();
			if(!bUseSolverThis) break main;

			//##################################
			//### START: Besonderheiten dieses Solvers
			//###################################		
			
			//nach dem ...post noch den Wert fuer spaeter sichern
			//sReturnLineSolved2compareWithParsed = VectorUtilZZZ.implode(vecReturn); //WEIL Intern mit einem String gearbeitet wird soll an dieser Stelle in vecReturn(1) eh der ganze String stehen!!!
			//sReturnLineSolved2compareWithParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineSolved2compareWithParsed, sTagStartZ, sTagEndZ);
			
			//Aufloesen des JSON-ARRAY-Tags
			bUseJsonArray = this.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
			if(bUseJsonArray) {
			
				alsReturn = this.computeArrayList(sExpression);
				if(alsReturn!=null) {					
					sReturnTag = alsReturn.toString(); //ArrayListExtendedZZZ.computeDebugString(alsReturn);
					this.setValue(sReturnTag);
					sReturnLine = sReturnTag;
					sReturn = sReturnTag;
					
					//nach dem ...post noch den Wert fuer spaeter sichern
					sReturnLineParsed2compareWithSolved = sExpressionIn;
					sReturnLineParsed2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed2compareWithSolved, sTagStartZ, sTagEndZ);					
					sReturnLineParsed2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed2compareWithSolved, KernelJsonArrayIniSolverZZZ.sTAG_NAME);
					
					sReturnLineSolved2compareWithParsed = sReturnLine;
					sReturnLineSolved2compareWithParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineSolved2compareWithParsed, sTagStartZ, sTagEndZ);
					if(sReturnLineSolved2compareWithParsed!=null) { //if(sReturnTagSolved!=null) {
						//Ziel ist es zu ermitteln, ob durch das Solven selbst ein Aenderung passierte.
						//Daher absichtlich nicht sExpressionIn und sReturn verwenden. Darin sind ggfs. Aenderungen durch das Parsen enthalten. 
						//aber nur den geparsten Tag zu verwenden ist zuwenig... z.B. wenn ein ganzer Tag wg. der Flags nicht gesolved wird (z.B. Fall: Call ja, Java nein): if(!sReturnTagSolved.equals(sReturnTagParsed)) {
						System.out.println("sReturnLineParsed2compareWithSolved='"+sReturnLineParsed2compareWithSolved+"'");
						System.out.println("sReturnLineSolved2compareWithParsed='"+sReturnLineSolved2compareWithParsed+"'");
						if(!sReturnLineSolved2compareWithParsed.equals(sReturnLineParsed2compareWithSolved)) {
							this.updateValueJsonArraySolvedChanged(); //zur Not nur, weil die Z-Tags entfernt wurden.
							this.updateValueJsonArraySolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.	
						}
					}
				}	
			}
			
			//Aufloesen des JSON-MAP-Tags
			bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
			if(bUseJsonMap) {
			
				hmReturn = this.computeHashMap(sExpression);
				if(!hmReturn.isEmpty()) {
					//Das baut wieder Klammern drum... sReturnTag = hmReturn.toString();
					sReturnTag = HashMapUtilZZZ.computeImplodeString(hmReturn);
					this.setValue(sReturnTag);
					sReturnLine = sReturnTag;
					sReturn = sReturnTag;
					
					//nach dem ...post noch den Wert fuer spaeter sichern
					sReturnLineParsed2compareWithSolved = sExpressionIn;
					sReturnLineParsed2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed2compareWithSolved, sTagStartZ, sTagEndZ);					
					sReturnLineParsed2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed2compareWithSolved, KernelJsonMapIniSolverZZZ.sTAG_NAME);
					
					sReturnLineSolved2compareWithParsed = sReturnLine;
					sReturnLineSolved2compareWithParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineSolved2compareWithParsed, sTagStartZ, sTagEndZ);																							
					if(sReturnLineSolved2compareWithParsed!=null) { //if(sReturnTagSolved!=null) {
						//Ziel ist es zu ermitteln, ob durch das Solven selbst ein Aenderung passierte.
						//Daher absichtlich nicht sExpressionIn und sReturn verwenden. Darin sind ggfs. Aenderungen durch das Parsen enthalten. 
						//aber nur den geparsten Tag zu verwenden ist zuwenig... z.B. wenn ein ganzer Tag wg. der Flags nicht gesolved wird (z.B. Fall: Call ja, Java nein): if(!sReturnTagSolved.equals(sReturnTagParsed)) {
						System.out.println("sReturnLineParsed2compareWithSolved='"+sReturnLineParsed2compareWithSolved+"'");
						System.out.println("sReturnLineSolved2compareWithParsed='"+sReturnLineSolved2compareWithParsed+"'");
						if(!sReturnLineSolved2compareWithParsed.equals(sReturnLineParsed2compareWithSolved)) {
							this.updateValueJsonMapSolvedChanged(); //zur Not nur, weil die Z-Tags entfernt wurden.
							this.updateValueJsonMapSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.	
						}
					}																	
				}										
			}	
			
			//nach dem ...post noch den Wert fuer spaeter sichern
			//sReturnLineSolved2compareWithParsed = sReturnLine;
			//sReturnLineSolved2compareWithParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineSolved2compareWithParsed, sTagStartZ, sTagEndZ);
						
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
			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
						
			if(bUseExpression && bUseSolverThis) {
				if(objEntry.isEncrypted() && bUseSolverThis) objEntry.setValueDecrypted(sReturnLine);
				if(sReturnTagSolved!=null) {				
					if(!sReturnTagSolved.equals(sReturnTagParsed)) {				
						this.updateValueSolvedChanged();
						this.updateValueSolvedChanged(objReturnReference);
					}
				}
				
				
//				if(sReturnLineSolved2compareWithParsed!=null) { //if(sReturnTagSolved!=null) {
//					//Ziel ist es zu ermitteln, ob durch das Solven selbst ein Aenderung passierte.
//					//Daher absichtlich nicht sExpressionIn und sReturn verwenden. Darin sind ggfs. Aenderungen durch das Parsen enthalten. 
//					//aber nur den geparsten Tag zu verwenden ist zuwenig... z.B. wenn ein ganzer Tag wg. der Flags nicht gesolved wird (z.B. Fall: Call ja, Java nein): if(!sReturnTagSolved.equals(sReturnTagParsed)) {
//					System.out.println("sReturnLineParsed2compareWithSolved='"+sReturnLineParsed2compareWithSolved+"'");
//					System.out.println("sReturnLineSolved2compareWithParsed='"+sReturnLineSolved2compareWithParsed+"'");
//					if(!sReturnLineSolved2compareWithParsed.equals(sReturnLineParsed2compareWithSolved)) {
//						this.updateValueSolvedChanged(); //zur Not nur, weil die Z-Tags entfernt wurden.
//						this.updateValueSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.	
//					}
//				}
				
				if(alsReturn!=null) {
					objEntry.setValue(alsReturn);
					objEntry.isJsonArray(true);
				}
				
				if(hmReturn!=null) {
					objEntry.setValue(hmReturn);
					objEntry.isJsonMap(true);
				}										
			}																		
			this.adoptEntryValuesMissing(objEntry);
		}	
		return sReturn;				
	}

	
	//### aus IKernelJsonArrayIniSolveUserZZZ
	@Override
	public void updateValueJsonArraySolvedChanged() throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueJsonArraySolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueJsonArraySolvedChanged(boolean bIsJsonArraySolvedChanged) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueJsonArraySolvedChanged(objReturnReference, bIsJsonArraySolvedChanged);
	}
		
	@Override
	public void updateValueJsonArraySolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueJsonArraySolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueJsonArraySolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsJsonArraySolvedChanged) throws ExceptionZZZ{		
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;

			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isJsonArraySolvedChanged(bIsJsonArraySolvedChanged);
		}//end main:
	}

	
	
	//### aus IKernelJsonMapIniSolverUserZZZ
	@Override
	public void updateValueJsonMapSolvedChanged() throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueJsonMapSolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueJsonMapSolvedChanged(boolean bIsJsonMapSolvedChanged) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueJsonMapSolvedChanged(objReturnReference, bIsJsonMapSolvedChanged);
	}
		
	@Override
	public void updateValueJsonMapSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueJsonMapSolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueJsonMapSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsJsonMapSolvedChanged) throws ExceptionZZZ{		
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;

			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isJsonMapSolvedChanged(bIsJsonMapSolvedChanged);
		}//end main:
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	//####################################
	//### FLAG Handling
	
	
	//### aus IKernelExpressionIniSolverZZZ
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
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
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
	
	
	//Aus IKernelJsonArrayIniSolverZZZ
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


	//Aus IKernelJsonMapIniSolverZZZ
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

	
	
}//End class
