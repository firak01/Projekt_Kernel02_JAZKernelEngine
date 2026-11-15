package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptUserZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.IKernelConfigSectionEntryUserZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelEntryReferenceSolveUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Merke: Es gibt auch solver, die unabhaengig von einem ini-File funktionieren sollen.
 *        Darum 2 Kategorien bei den Konstruktoren
 * @author fl86kyvo
 *
 * @param <T>
 */
public abstract class AbstractKernelIniSolverZZZ<T>  extends AbstractKernelIniTagCascadedZZZ<T> implements IKernelFileIniUserZZZ, IKernelEntryReferenceSolveUserZZZ, IKernelExpressionIniSolverZZZ,  IKernelZFormulaIni_PathZZZ, IKernelZFormulaIni_VariableZZZ, IKernelConfigSectionEntryUserZZZ, ICryptUserZZZ, IValueVariableUserZZZ, ISolveUserZZZ, IConvertEnabledZZZ{
	private static final long serialVersionUID = -4816468638381054061L;	
	protected ICryptZZZ objCrypt=null; //Das Verschlüsselungs-Algorithmus-Objekt, falls der Wert verschlüsselt ist.
			
	
	public AbstractKernelIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		AbstractKernelIniSolverNew1_(null);
	}
		
	public AbstractKernelIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		AbstractKernelIniSolverNew1_(null);
	}
			
	public AbstractKernelIniSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		AbstractKernelIniSolverNew1_(null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		AbstractKernelIniSolverNew1_(null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(null, null);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig) throws ExceptionZZZ{
		super(objFileIniKernelConfig); //Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, null);
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIniKernelConfig, null);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	public AbstractKernelIniSolverZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ		
		AbstractKernelIniSolverNew_(objFileIniKernelConfig, hmVariable);		
	}
	
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel, null);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, null);
	}
	
	public AbstractKernelIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		AbstractKernelIniSolverNew_(objFileIni, hmVariable);
	}
		
	private boolean AbstractKernelIniSolverNew_(FileIniZZZ<T> objFileIniIn, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {
		
	boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
			//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
			FileIniZZZ<T> objFileIni = null;
			if(objFileIniIn==null ){
				IKernelZZZ objKernel = this.getKernelObject();
				if(objKernel==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Kernel-Object or FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
				
				objFileIni = objKernel.getFileConfigKernelIni();				
				if(objFileIni == null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}
			}else{
				objFileIni = objFileIniIn;
			}
			
			this.setFileConfigKernelIni(objFileIni);	
				
			if(hmVariable!=null){				
				this.setVariable(hmVariable);					
			}else if(hmVariable == null && objFileIni.getHashMapVariable() !=null){
				this.setHashMapVariable(objFileIni.getHashMapVariable());
			}else {
				//soll zu den Variablen aus dem Ini-File hinzuaddieren, bzw. ersetzen
				//TODOGOON20240806; //HashMapCaseInsensitive PLus HashMapCaseInsensitive..
			}
			
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew_
	
	
	private boolean AbstractKernelIniSolverNew1_(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ {		
		boolean bReturn = false;		 
		main:{
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}
			
	
			if(hmVariable!=null){				
				this.setVariable(hmVariable);									
			}
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	}//AbstractKernelIniSolverNew2_
	
	
	//### Aus Interface IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();
		this.objCrypt=null;
		return true;
	}
	
	@Override 
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();		
		this.resetEntry();
		return true;
	}
	
	

	@Override
	public boolean resetValues(Object objDefault) throws ExceptionZZZ {
		super.resetValues(objDefault);
		this.resetEntry();
		return true;
	}
	
	//### Aus Interface ICryptUserZZZ
	@Override
	public ICryptZZZ getCryptAlgorithmType() throws ExceptionZZZ {
		return this.objCrypt;
	}

	@Override
	public void setCryptAlgorithmType(ICryptZZZ objCrypt) {
		this.objCrypt = objCrypt;
	}
		
	//### Aus IObject with Expression
	
	//### aus IParseEnabled		
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		//das ist default, es gibt kein eigenes Flag für den Parser speziell
		//Merke: Auch innerhalb der .parseFirstVector(), etc Methoden wird immer auf das gesetzte Flag geprueft.
		//       Damit wirkt sich ein Ueberschreiben dieser Methode (z.B. mit return true) auch nur begrenzt aus.
		return this.isSolverEnabledThis();  
	}
	
	
	@Override
	public boolean isParserEnabledCustom() throws ExceptionZZZ{
		//Ziel ist, dass Solver, die Kinder/Eltern-Tags haben auch deren Flags abrufen koennen.
		
		//Durch Ueberschreiben dieser Methode koennen Solver, die von einem anderen Solver abhaengen 
		//sich auch deaktiveren wenn der andere Solver deaktiviert ist. Z.B. JSON_ARRAY Flag haengt von JSON ab.
		return this.isParserEnabledThis();
	}
	
	
	//### Aus ISolveUserZZZ
	@Override
	public boolean isSolve(String sExpression) throws ExceptionZZZ {
		return this.isExpression(sExpression);
	}
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueSolveCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, String sExpressionIn) throws ExceptionZZZ{
		//VORLAGE ALS TEMPLATE WIE ETWA EINE UEBERSCHEIBENDE METHODE AUSSEHEN KOENNTE
		//oberste Ebene super.updateValueSolveCustom(objReturnReference, sExpressionIn);
		
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
	
		//#######################################################
		
		//Flags entscheiden ob es weitergeht.
//		if(this.isSolverEnabledGeneral()) {
//
//			//Zentrale Stelle, um den String/Entry als Expression zu kennzeichnen.
//			//Hier redundant zu parse(), weil z.B. in solve() nur parseFirstVector() aufgerufen wird.
//			if(XmlUtilZZZ.isExpression(sExpressionIn)){
//				objEntry.isExpression(true);
//				this.getEntry().isExpression(true);
//			}
//		}
	}

	
	//+++++++++++++++
	@Override 
	public void addHistorySolveCalled() throws ExceptionZZZ{
		String sTagName = this.getName();
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistorySolveCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySolveCalled(String sTagName) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistorySolveCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySolveCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		String sTagName = this.getName();
		this.addHistorySolveCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySolveCalled(IKernelConfigSectionEntryZZZ objEntry, String sTagName) throws ExceptionZZZ{
		objEntry.setHistorySolveCalled(sTagName);
	}
	
	@Override
	public void updateValueSolveCalled() throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSolveCalled(objReturnReference, true);
	}
	
	@Override
	public void updateValueSolveCalled(boolean bIsSolveCalled) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSolveCalled(objReturnReference, bIsSolveCalled);
	}
		
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueSolveCalled(objReturnReference, true);
	}
	
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference,  boolean bIsSolveCalled) throws ExceptionZZZ{
		main:{
			//solve wird aufgerufen... Egal ob Exprssion aktiviert ist oder nicht, also nicht beruecksichtigen:
			//if(!this.isExpressionEnabledGeneral()) break main;

			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isSolveCalled(bIsSolveCalled);
				
			//Die "echte" Feststellung welcher Tag aufgerufen wird. Ggfs. kann man daraus die Reihenfolge ablesen.
			this.addHistorySolveCalled(objEntry);
		}//end main:
	}
	
	//+++++++++++++++++++++++++++++++++
	
//	@Override
//	public void updateValueSolved() throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
//		this.updateValueSolved(objEntry, true);
//	}
//	
//	@Override
//	public void updateValueSolved(boolean bIsSolved) throws ExceptionZZZ{
//		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
//		this.updateValueSolved(objEntry, bIsSolved);
//	}
		
//	@Override
//	public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
//		this.updateValueSolved(objEntry, true);
//	}
//	
//	@Override
//	public void updateValueSolved(IKernelConfigSectionEntryZZZ objEntry, boolean bIsSolved) throws ExceptionZZZ{
//		objEntry.isSolved(bIsSolved);
//	}
	
	
	@Override
	public void updateValueSolved() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSolved(objReturnReference, true);
	}
	
	
	@Override
	public void updateValueSolved(boolean bIsSolved) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);		
		this.updateValueSolved(objReturnReference, bIsSolved);
	}
	
	
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueSolved(objReturnReference, true);		
	}

		
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolved) throws ExceptionZZZ {
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;
			
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();							
			if(this.isSolverEnabledGeneral()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
				objEntry.isSolved(bIsSolved);
			}
		}//end main:
	}

	
	//+++++++++++++++++++++++++++++++++
	
	@Override
	public void updateValueSolvedChanged() throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueSolvedChanged(boolean bIsSolvedChanged) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSolvedChanged(objReturnReference, bIsSolvedChanged);
	}
		
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		this.updateValueSolvedChanged(objReturnReference, true);
	}
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolvedChanged) throws ExceptionZZZ{		
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;

			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isSolvedChanged(bIsSolvedChanged);
		}//end main:
	}
	
	
	//### Aus ISolveEnabled
	@Override
	public boolean isSolverEnabledGeneral() throws ExceptionZZZ{
		return this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
	}
	
	//In folgender konkreten Implementierung kann ueber das konkrete Flag des konkreten Solvers, dieser ein-/ausgeschaltet werden.
	@Override
	public abstract boolean isSolverEnabledThis() throws ExceptionZZZ;
	
	//Merke: Wenn ein "Elternsolver" auch relevant sein soll, dann kann in einer ueberschriebenen Version dieser hier aufgenommen werden.
	@Override
	public abstract boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ;
		
	@Override
	public abstract boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ;
	
	
	@Override 
	public boolean isSolverEnabledEveryRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			
			bReturn = this.isSolverEnabledGeneral();
			if(!bReturn) break main;	
			
			//"Elterntags" werden anders behandelt als "Kindtags"
			if(StringZZZ.isEmpty(this.getParentName())){
				bReturn = this.isSolverEnabledThis();
				if(!bReturn) break main;
			}else {
				bReturn = this.isSolverEnabledThis() && (this.isSolverEnabledAnyParentCustom() || this.isSolverEnabledAnyChildCustom()) ;
				if(!bReturn) break main;
			}
						
		}//end main:
		return bReturn;
	}
	
	@Override 
	public boolean isSolverEnabledAnyRelevant() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Merke: Die Abfrage auf isExpressionEnabledGeneral() ... nicht hierein, damit wird ggf. noch eine Feinsteuerung auf Entfernen des reinen Z-Tags gesteuert.
			//       Muss also immer eine extra Abfrage bleiben.
			boolean bReturn1 = this.isSolverEnabledThis();						
			boolean bReturn2 = this.isSolverEnabledGeneral();
			boolean bReturn3 = this.isSolverEnabledAnyChildCustom();
			
			bReturn = bReturn1 | bReturn2 | bReturn3;
		}//end main:
		return bReturn;
	}
	
	@Override 
	public boolean isSolverEnabledByFlagZ(Enum objEnum) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objEnum==null) break main;
			
			bReturn = this.getFlag(objEnum.name());
		}//end main:
		return bReturn;
	}
	
	
	//#### aus ISolveZZZZ
	@Override
	public boolean isSolveRelevant(String sExpression) throws ExceptionZZZ {
		return this.isSolve(sExpression);
	}

	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, null, true);
	}

	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, null, bRemoveSurroundingSeparatorsOnSolve);
	}
	
	@Override
	public String solveParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ {
		return this.solveParsed_(vecExpression, objReturnReference, bRemoveSurroundingSeparatorsOnSolve);
	}

	private String solveParsed_(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparatorsOnSolve) throws ExceptionZZZ {
		String sReturn = "";
		String sReturnTag = null;
		main:{
			if(vecExpression==null) break main;
			
			String sExpression = (String) vecExpression.get(1);
			sReturnTag = this.solveParsed(sExpression, objReturnReference, bRemoveSurroundingSeparatorsOnSolve);
			sReturn = sReturnTag;
			
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
			this.setValue(sReturnTag);
			vecExpression.replace(sReturnTag);
			sReturn = VectorUtilZZZ.implode(vecExpression);			
			if(objEntryInner!=null) objEntryInner.setValue(sReturn);				
		}//end main:
		return sReturn;
	}
	
	@Override
	public String solve(String sExpression) throws ExceptionZZZ{
		return this.solve_(sExpression, true);
	}
	
	@Override
	public String solve(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpression, bRemoveSurroundingSeparators);
	}
	
	/** Methoder ruft erst parse() auf, dann solvedParsed()**/
	private String solve_(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = sExpression;
		
		//Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
		//Wichtig: Das InnerEntry-Objekt vom externen Entry-Objekt TRENNEN, also nicht: IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();
		objReturn.setRaw(sExpression);

		main:{			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference=new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReference.set(objReturn);
			
			sReturn = this.solve(sExpression, objReturnReference, bRemoveSurroundingSeparators);
		}//end main:						
		return sReturn;

	}
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.solvePost_(vecExpression, null, true);
	}

	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solvePost_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String solveParsed(String sExpression) throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, true);
	}

	@Override
	public String solveParsed(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
		return this.solveParsed_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	/** Methode, die Nacharbeiten und customSolve-Methode aufruft.
	 *  Sie kann nach der der solveParsed() Methode eingesetzt werden, um ggfs. Tags zu eintfernen, etc.
	 *  
	 *  Hintergrund:
	 *  Statt dem vollständigen solve() aufzurufen ist es ggfs. günstiger schon auf das Ergebnis des Parsens zuzugreifen.
	 *  was mit solveParsed() passiert.
	 *  nun müssen noch "Nacharbeiten" gemacht werden, wie sie auch in solvePost() gemacht würden.
	 *  
	 *   Im Gegensatz zu einer "Post" Methode, die "intern" am Ende einer Methode (hier solve() ) aufgerufen wird,
	 *   gehe ich bei einer "Wrapup" Methdoe davon aus, das sie "extern" nach einer anderen Methode aufgerufen wird. D.h. nicht innerhalb der Methode, am Ende.
	 *  
	 * @param sExpression
	 * Merke: Methode nur mit String als Eingabeparameter und nicht Vektor, das solveParsed nur String zurueckliefert und hier mit dem Wert weitergearbeitet wird.
	 * 
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public String solveParsedWrapup(String sExpression) throws ExceptionZZZ {
		return solveParsedWrapup_(sExpression, null, true);
	}
	
	@Override
	public String solveParsedWrapup(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(sExpression, null, bRemoveSurroundingSeparators);
	}
	
	private String solveParsedWrapup_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {						
		String sReturn = null;
		String sReturnTag = null; String sReturnLine = null;
		String sExpression=null;
		boolean bUseExpression = false;
		boolean bUseSolver = false;
		boolean bUseSolverThis = false;		
			
			
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
			
		sReturnTag = this.getValue();
		sReturnLine = sExpressionIn;	
		main:{				
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			sExpression = sExpressionIn;
				
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
							
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
								
			this.setRaw(sExpression);
			objEntry.setRaw(sExpression);
			objEntry.setValueAsExpression(sExpressionIn, false);
								
			//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
			if(bRemoveSurroundingSeparators & bUseExpression) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!				
			}
			
			
			bUseSolverThis = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
				
				
			//Als echten Ergebniswert aber die <Z: ... konkreten Solver Tags rausrechnen (!!! unabhaengig von bRemoveSurroundingSeperators)
			if(bUseExpression & bUseSolver & bUseSolverThis){
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				if(sTagStart.equalsIgnoreCase("<Z>")) {
					//dann mache nix... der Tag wird spaeter behandelt...
				}else {
					sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStart, sTagEnd);//also AN JDEDER POSITION (d.h. nicht nur am Anfang) von innnen nach aussen!!!
				}
			}	
		}//end main:
		
				
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.						
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
			
		if(objEntry!=null) {				
			objEntry.setValue(sReturnLine);	
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return sReturn;
	}
	
	
	
	/** Methode, die Nacharbeiten und customSolve-Methode aufruft.
	 *  Sie kann nach der der solveParsed() Methode eingesetzt werden, um ggfs. Tags zu eintfernen, etc.
	 *  
	 *  Hintergrund:
	 *  Statt dem vollständigen solve() aufzurufen ist es ggfs. günstiger schon auf das Ergebnis des Parsens zuzugreifen.
	 *  was mit solveParsed() passiert.
	 *  nun müssen noch "Nacharbeiten" gemacht werden, wie sie auch in solvePost() gemacht würden.
	 *  
	 *   Im Gegensatz zu einer "Post" Methode, die "intern" am Ende einer Methode (hier solve() ) aufgerufen wird,
	 *   gehe ich bei einer "Wrapup" Methodo davon aus, das sie "extern" nach einer anderen Methode aufgerufen wird. D.h. nicht innerhalb der Methode, am Ende.
	 *  
	 *   BESONDERHEIT 20250409:
	 *   DAS PROBLEM ist nun, das sReturnLine alle richtig hat. ABER in vecReturn(0) bzw. (1) gibt es noch die Tags
	 *   WAS wenn wir alles in vec(1) schreiben und 0 bzw. 2 dann leersetzen!!!
	 *	 Da das Veraendern von den Indices 0 bzw. 2 nicht nachvollziehbar waere dies nicht machen. 
	 *   STATT DESSEN GIBT ES DIESE METHODE MIT VECTOR ALS EINGABEPARAMETER (trotz der erzeugten Coderedundanz)	
	 *    
	 * @param sExpression
	 * Merke: Methode nur mit String als Eingabeparameter und nicht Vektor, das solveParsed nur String zurueckliefert und hier mit dem Wert weitergearbeitet wird.
	 * 
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> solveParsedWrapup_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {						
		Vector3ZZZ<String> vecReturn = null; //String sReturn = null; 
		String sReturnTag = null; String sReturnLine = null;
		String sExpression=null;
		boolean bUseExpression = false;
		boolean bUseSolver = false;
		boolean bUseSolverThis = false;		
			
			
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
			
		sReturnTag = this.getValue();		
		vecReturn = vecExpressionIn;
		sReturnLine = "";
		main:{		
			if(VectorUtilZZZ.isEmpty(vecExpressionIn)) break main;
			sReturnLine = VectorUtilZZZ.implode(vecExpressionIn);			
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
							
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
								
			this.setRaw(sReturnLine);
			objEntry.setRaw(sReturnLine);
			objEntry.setValueAsExpression(sReturnLine, false);
			
			//Besonderheit: Vor dem rausrechnen der Z-Tags den Wert noch einmal quasi speichern
			objEntry.setValueAsExpression(sReturnLine, false);
					
			//Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
			if(bRemoveSurroundingSeparators & bUseExpression) {
				String sTagStartZ = "<Z>";
				String sTagEndZ = "</Z>";
				
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
				sReturnLine = VectorUtilZZZ.implode(vecExpressionIn);
			}
			
			bUseSolverThis = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
							
			//Als echten Ergebniswert aber die <Z: ... konkreten Solver Tags rausrechnen (!!! unabhaengig von bRemoveSurroundingSeperators)
			if(bUseExpression & bUseSolver & bUseSolverThis){
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
				if(sTagStart.equalsIgnoreCase("<Z>")) {
					//dann mache nix... der Tag wird spaeter behandelt... (bzw. in AbstractKernelIniSolverZZZ)
				}else {
					//sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStart, sTagEnd);//also AN JDEDER POSITION (d.h. nicht nur am Anfang) von innnen nach aussen!!!
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart, sTagEnd);//also AN JDEDER POSITION (d.h. nicht nur am Anfang) von innnen nach aussen!!!
					sReturnLine = VectorUtilZZZ.implode(vecExpressionIn);
				}								
			}
		}//end main:
		
				
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.						
		this.setValue(sReturnTag);		
		if(objEntry!=null) {				
			objEntry.setValue(sReturnLine);	
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}
		return vecReturn;
	}
	//++++++++++++++++++++++

	//### IKernelEntryReferenceSolveUserZZZ	
	@Override
	public String solve(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		return this.solve_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solve(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solve_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private String solve_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;	
		String sReturnTag2Solve = null; String sReturnTagParsed = null; String sReturnTagSolved = null;	
		String sReturnLineParsed = null; String sReturnLineSolved = null; String sReturnLineSolved2compareWithParsed = null; String sReturnLineParsed2compareWithSolved = null;	
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		
		boolean bUseExpression = false;	boolean bUseSolver = false; boolean bUseSolverThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";	
		
		//############
		//Wichtig: Bei jedem neuen Parsen (bzw. vor dem Solven(), nicht parse/solveFirstVector!) die internen Werte zuruecksetzen, sonst wird alles verfaelscht.
		//         Ziel ist, das nach einem erfolgreichen Parsen/Solven das Flag deaktiviert werden kann UND danach bei einem neuen Parsen/Solven die Werte null sind.
		this.resetValues();			
		//#######
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			//Achtung: Das objReturn Objekt NICHT generell versuchen ueber .getEntry() und dann ggfs. .getEntryNew() zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//objEntry = this.getEntry();
			
			//nein, dann gehen alle Informationen verloren
			//objReturn = this.parseAsEntryNew(sExpression);
			
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}else {
			
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);		
		objEntry.setRaw(sExpressionIn);
		this.updateValueSolveCalled(); 			//20250311: verwendet eine ueberschreibbare Methode der Solver, dito soll fuer Parser gelten
		this.updateValueSolveCalled(objReturnReference);	//20250311: verwendet eine ueberschreibbare Methode der Solver, dito soll fuer Parser gelten
		
		sReturnLine = sExpressionIn;		
		sReturnTag = this.getValue();
		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
		sReturn = sReturnLine;
		
		main:{
		solverThis:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;	
			
			//Rufe nun parseFirstVector() auf... (und nicht das gesamte Parse!!!
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry); 
			vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse);
			objEntry = objReturnReferenceParse.get();
			if(vecReturn==null)break main;
			
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			if(StringZZZ.isEmpty(sReturnTag)) break solverThis; //Dann ist der Tag nicht enthalten oder "Solver fuer den Tag" deaktiviert
						
			sReturnTagParsed = sReturnTag;
			sReturnTagSolved = sReturnTag;
			this.setValue(sReturnTag);
			
//			//Da wir hier verkuerzt parseFirstVector aufrufen... Explizit parsePost() ausfuehren.
			vecReturn = this.parsePost(vecReturn, objReturnReferenceParse);
			sReturnTag = this.getValue();
			sReturnTagParsed = sReturnTag;
			sReturnTagSolved = sReturnTag;
			if(vecReturn==null) break main;
			
			//Zwischenstand, falls nach dem Parse beendet ist, also ein Solver nicht weiter ausgeführt wird
			sReturnLine = VectorUtilZZZ.implode(vecReturn); 
			sReturnLineParsed = sReturnLine;
			sReturnLineParsed2compareWithSolved = sReturnLineParsed;
			
			
			//Rufe nun solveParsed() auf...
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			//Zwischenstand, falls nach dem Generellen Solver beendet ist, Spezieller Solver also nicht weiter ausgeführt wird
			sReturnLineParsed2compareWithSolved = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed, sTagStartZ, sTagEndZ);			
			if(bRemoveSurroundingSeparators) {
				sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineParsed, sTagStartZ, sTagEndZ);
			}	
			
			//TODOGOON20250308; //TICKETGOON20250308;;//Generische Problematik, wenn man hier nur auf den eigenen Solver abbprüft, kann man nicht das "Elternflag setzen"
			this.updateValueSolveCustom(objReturnReference, sReturnTagParsed);
			
			//TODOGOON20250328;//Der Gesamtsolver wird aber ausgeführt. D.h. dafuer muss das solvePOST auch ausgefuehrt werden!!!  Sprich Z-Tag raus
			bUseSolverThis = this.isSolverEnabledEveryRelevant();
			if(!bUseSolverThis) break solverThis;
			
			
			//###########################
			sReturnTag2Solve = sReturnTag; 
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolve= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objEntry);
			sReturnTagSolved = this.solveParsed(sReturnTag2Solve, objReturnReferenceSolve, bRemoveSurroundingSeparators);			
			objEntry = objReturnReferenceSolve.get();								
			//##########################
			
			sReturnTag = sReturnTagSolved;
		}//end solveThis:	
		
			//... das post vorbereiten
		    //Merke: Das wird wg. des aktivieretn generellen Solvers immer ausgeführt. Intern wird dann auf den deaktivierten aktuellen Solver geprueft.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferencePost= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferencePost.set(objEntry);
		
			//Nun als Tag Value die Solve Loesung einsetzen vor dem ... post. Merke: Das Post entfernt dann umliegende Tags.			
			vecReturn.replace(1,sReturnTag);
			this.setValue(sReturnTag);

			
			//Nun als Zeilenwert das ...post durchfuehren
			vecReturn = this.solvePost(vecReturn, objReturnReferencePost, bRemoveSurroundingSeparators);
			sReturnLine = VectorUtilZZZ.implode(vecReturn); //WEIL Intern mit einem String gearbeitet wird soll an dieser Stelle in vecReturn(1) eh der ganze String stehen!!!
			sReturnLineSolved = sReturnLine;
		
			//nach dem ...post noch den Wert fuer spaeter sichern
			sReturnLineSolved2compareWithParsed = VectorUtilZZZ.implode(vecReturn); //WEIL Intern mit einem String gearbeitet wird soll an dieser Stelle in vecReturn(1) eh der ganze String stehen!!!
			sReturnLineSolved2compareWithParsed = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLineSolved2compareWithParsed, sTagStartZ, sTagEndZ);
		
			
			//Nun als Tag Value die Solve Loesung einsetzen nach dem ... post
			sReturnTag = this.getValue();	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!	
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			//objEntry.setValueFromTag(sReturnTag);			
			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
						
			if(bUseExpression && bUseSolverThis) {
				if(objEntry.isEncrypted() && bUseSolverThis) objEntry.setValueDecrypted(sReturnLine);
				if(sReturnLineSolved2compareWithParsed!=null) { //if(sReturnTagSolved!=null) {
					//Ziel ist es zu ermitteln, ob durch das Solven selbst ein Aenderung passierte.
					//Daher absichtlich nicht sExpressionIn und sReturn verwenden. Darin sind ggfs. Aenderungen durch das Parsen enthalten. 
					//aber nur den geparsten Tag zu verwenden ist zuwenig... z.B. wenn ein ganzer Tag wg. der Flags nicht gesolved wird (z.B. Fall: Call ja, Java nein): if(!sReturnTagSolved.equals(sReturnTagParsed)) {
					System.out.println("sReturnLineParsed2compareWithSolved='"+sReturnLineParsed2compareWithSolved+"'");
					System.out.println("sReturnLineSolved2compareWithParsed='"+sReturnLineSolved2compareWithParsed+"'");
					if(!sReturnLineSolved2compareWithParsed.equals(sReturnLineParsed2compareWithSolved)) {
						this.updateValueSolvedChanged(); //zur Not nur, weil die Z-Tags entfernt wurden.
						this.updateValueSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.	
					}
				}
			}																		
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;	
	}
	
	
	/**Methode muss vom konkreten "solver" ueberschrieben werden (!!!!) , wenn darin keine Pfade oder Variablen ersetzt werden sollen.
	 * Nachfolgender Code ist also eine Art Blaupause.
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

	//!!! METHODE IST AN DIESER STELLE EINE BLAUPAUSE, die vom konkreten Solver mit der konkreten "Aufloesung" ueberschrieben werden muss. 
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;
		
		boolean bUseExpression = false; 
		boolean bUseSolver = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
//			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
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
		sReturnLine = sExpressionIn;
		sReturnTag = this.getValue();
		sReturn = sReturnLine;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledEveryRelevant(); //this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
			if(!bUseSolver) break main;
			
			String sExpression = sExpressionIn;
				
			//!!! METHODE IST EINE BLAUPAUSE, die vom konkreten Solver ueberschrieben werden muss.
			//!!! HIer steht dann die konkrete Aufloesung. 
			
			
			//+++ Aufruf einer Methode, die vom konkreten Solver ueberschrieben werden kann.
//			sReturn = this.solvePostCustom(sReturn, objReturnReference, bRemoveSurroundingSeparators);
//			objEntry = objReturnReference.get();
			
		}//end main:	
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	//Der Handler bekommt die ganze Zeile als Wert
		sReturn = sReturnLine;
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);			
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;				
	}

	
	@Override
	public String solveParsedWrapup(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return solveParsedWrapup_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public String solveParsedWrapup(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//+++++++++++++++++++++
	//Merke: Folgende Methoden koennen nur im konkreten Solver implementiert werden.
	//       Z.B. kennt nur der konkrete Solver das Flag, das ihn deaktiviert.
	//            Ist der Solver deaktiviert, findet dann auch das Entfernen umgebender Tags nicht statt.
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ;
	//public abstract Vector3ZZZ<String> parseFirstVectorSolverCustomPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ;
	
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.solvePost_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> solvePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		return this.solvePost_(vecExpression, objReturnReference, bRemoveSurroundingSeparators);
	}
	
	private Vector3ZZZ<String> solvePost_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;		
		//String sReturn = null;
		String sReturnTag = null; String sReturnLine = null;
		String sExpressionIn=null;
		boolean bUseExpression = false;
		boolean bUseSolver = false;
		boolean bUseSolverThis = false;		
		
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell mit .getEntry() und darin ggfs. .getEntryNew() versuchen zu uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
				
		main:{	
			if(vecExpressionIn==null) break main;
			vecReturn=vecExpressionIn;			
						
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
						
			bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
										
			//Wenn der generelle Solver ausgeführt wird
			vecReturn = this.solveParsedWrapup(vecReturn, objReturnReferenceIn, bRemoveSurroundingSeparators);
			
			//+++ Aufruf einer Methode, die vom konkreten Solver ueberschrieben werden kann.
			bUseSolverThis = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);		
			if(!bUseSolverThis) break main;
						
			vecReturn = this.solvePostCustom(vecReturn, objReturnReference, bRemoveSurroundingSeparators);			
			objEntry = objReturnReference.get();	
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
				
		//#################################		
		sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1); //damit bekommt man vom NullObjectZZZ nicht nur den Leerstring, sonder auch NULL zurück.
		sReturnLine = VectorUtilZZZ.implode(vecReturn);
						
		if(objEntry!=null) {
			//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {												
				if(!sExpressionIn.equals(sReturnLine)) {
					this.updateValueSolvedChanged();
					this.updateValueSolvedChanged(objReturnReference); //zur Not nur, weil die Z-Tags entfernt wurden.									
				}
			}			
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		}		
		return vecReturn;
	}
	
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
			
//			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);

//			sReturnTag = vecReturn.get(1).toString();
//			sReturnLine = VectorUtilZZZ.implode(vecReturn);
										
//			if(!sExpressionIn.equals(sReturnLine)) {
//				objEntry.setValueFormulaSolvedAndConverted(sReturnTag);									
//			}
			
		}//end main:
		
		if(objEntry!=null) {				
			objEntry.setValue(sReturn);
			objEntry.setValueFromTag(sReturnTag);
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
	
	
	//++++++++++++++
	
//	//@Override
//	public String solvePostCustom(String sExpression) throws ExceptionZZZ {
//		return this.solvePostCustom_(sExpression, null, true);
//	}
//
//	//@Override
//	public String solvePostCustom(String sExpression, boolean bRemoveSurroundingSeparators)throws ExceptionZZZ {
//		return this.solvePostCustom_(sExpression, null, bRemoveSurroundingSeparators);
//	}
//	
//	//@Override
//	public String solvePostCustom(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
//		return this.solvePostCustom_(sExpression, objReturnReference, true);
//	}
//	
//	//@Override
//	public String solvePostCustom(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {		
//		return this.solvePostCustom_(sExpression, objReturnReference, bRemoveSurroundingSeparators);
//	}
//	
//	//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
//	//!!! hier wuerde dann etwas konkretes stehen.
//	private String solvePostCustom_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {					
//		String sReturn = null;
//		String sReturnTag = null; String sReturnLine = null;
//		String sExpression=null;
//		boolean bUseExpression = false;
//			
//		IKernelConfigSectionEntryZZZ objEntry = null;
//		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
//		if(objReturnReferenceIn==null) {				
//			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
//		}else {
//			objReturnReference = objReturnReferenceIn;
//			objEntry = objReturnReference.get();
//		}
//		if(objEntry==null) {
//			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
//			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
//			objReturnReference.set(objEntry);
//		}			
//		
//		sReturnTag = this.getValue();
//		sReturnLine = sExpressionIn;
//		
//		main:{	
//			if(StringZZZ.isEmpty(sExpressionIn)) break main;
//			sExpression = sExpressionIn;	
//			
//			bUseExpression = this.isExpressionEnabledGeneral(); 
//			if(!bUseExpression) break main;
//			
//			this.setRaw(sExpression);
//			objEntry.setRaw(sExpression);					
//			
//			//!!! nur eine Blaupause, die vom konkreten Solver ueberschrieben werden kann.
//			//!!! hier wuerde dann etwas konkretes stehen.
//						
//			sReturnTag = sExpression;
//			sReturnLine = sExpression;
//		}//end main:
//	
//		//#################################
//		//Den Wert ersetzen, wenn es was zu ersetzen gibt.		
//		this.setValue(sReturnTag);
//		sReturn = sReturnLine;
//						
//		if(objEntry!=null) {
//			//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
//			objEntry.setValue(sReturnLine);
//			objEntry.setValueFromTag(sReturnTag);
//			this.updateValueSolved(objEntry);
//			if(sExpressionIn!=null) {												
//				if(!sExpressionIn.equals(sReturn)) {
//					this.updateValueSolvedChanged();
//					this.updateValueSolvedChanged(objEntry); //zur Not nur, weil die Z-Tags entfernt wurden.									
//				}
//			}			
//			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
//		}
//		return sReturn;
//	}		
	
	//+++++++++++++++++++++++++++++++++ siehe analog zu AbstractIniTagWithExpressionBasicZZZ
	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntryNew(String sExpression) throws ExceptionZZZ {
		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
			objReturn.setRaw(sExpression);
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objReturn);
			objReturn = this.solveAsEntry_(sExpression, objReturnReferenceSolve, true);
			
		}//end main:
		return objReturn;
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, null, true);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, null, bRemoveSurroundingSeparators);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, objReturnReferenceIn, true);
	}

	@Override
	public IKernelConfigSectionEntryZZZ solveAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveAsEntry_(sExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	private IKernelConfigSectionEntryZZZ solveAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null; //new KernelConfigSectionEntryZZZ<T>(this);
		String sReturn = sExpressionIn;
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
						
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
				
						
			//Merke: Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			String sExpression = sExpressionIn;
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			objReturnReferenceSolve.set(objEntry);
			sReturn = this.solve(sExpression, objReturnReferenceSolve, bRemoveSurroundingSeparators);					
			//Nein, das ist nicht der Wert des Tags... sondern der Gesamtenzeile   this.setValue(sReturn);
			objEntry = objReturnReferenceSolve.get();
			objReturn = objEntry;
		}//end main:
		

		if(objReturn!=null) {
			objReturn.setValue(sReturn);	
			if(sExpressionIn!=null) {
				if(!sExpressionIn.equals(sReturn)) objReturn.isParseCalled(true);
			}				
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objReturn);
		}					
		return objReturn;
	}


	
	//###### Getter / Setter
	
	//### aus IKernelZFormulaIniZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.IKernelZFormulaIniZZZ#isStringForConvertRelevant(java.lang.String)
	 */
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.isConvertable(sStringToProof);
	}
	
	//##################################
	//### Flag handling	
	//Merke: Nicht jeder Solver implementiert alle Interfaces, noch nicht einmal IKernelExpressionIniSolverZZZ (also mit <Z>-Tags arbeiten) notwendigerweise 
	//darum sind hier nur:
	//-- IKernelExpressionIniSolverZZZ	
	
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
	public boolean proofFlagExists(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelExpressionIniSolverZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
}//End class