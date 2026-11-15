package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelZFormulaMathSolverZZZ<T>  extends AbstractKernelIniSolverZZZ<T>  implements IKernelZFormulaMathZZZ{
	private static final long serialVersionUID = -6400035649490240580L;
	public static final String sTAG_NAME = "Z:math";
	public static final String sTAG_NAME_PARENT = KernelZFormulaIniSolverZZZ.sTAG_NAME;
	
	public KernelZFormulaMathSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionMathSolverNew_(null, null);
	}
	
	public KernelZFormulaMathSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelExpressionMathSolverNew_(null, null);
	}

	public KernelZFormulaMathSolverZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
		KernelExpressionMathSolverNew_(null, null);
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionMathSolverNew_(null, null);
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionMathSolverNew_(null, null);
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag)  throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionMathSolverNew_(objFileIni, null);
	}
	
	public KernelZFormulaMathSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelExpressionMathSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelZFormulaMathSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionMathSolverNew_(objFileIni, hmVariable);
	}
	
	
	private boolean KernelExpressionMathSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
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
	 }//end function KernelExpressionMathSolverNew_
	
	
	//###### Getter / Setter
	
	
	//####################### Andere Interfaces
	
	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return KernelZFormulaMathSolverZZZ.sTAG_NAME;
//	}
	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelZFormulaIniSolverZZZ.sTAG_NAME;
//	}	
	
	//### aus IParseEnabled				
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //Somit ist das Parsen vom Solven entkoppelt. Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
		//this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);
	}
		
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {
		//Ziel ist, dass Solver, die Kinder/Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			boolean bEnabledFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			boolean bEnabledThis = this.isParserEnabledThis();
					
			bReturn = bEnabledThis && bEnabledFormula ;
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
			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelZFormulaIniSolverZZZ.sTAG_NAME, false)) {
				objEntry.isFormula(true);
				this.getEntry().isFormula(true);
			}
			
			if(!this.isParserEnabledThis()) break main;					
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
				objEntry.isFormulaMath(true);
				this.getEntry().isFormulaMath(true);
			}
		}//end main:		
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//### aus ISolveEnabledZZZ
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Hier der "Elternsolver"
			bReturn = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			if(!bReturn) break main;
		}
	
		return bReturn;
	}
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		return false;
	}

	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {
		return false;
	}
	
	

	//### Aus ISolveEnabled		
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
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit MATH-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;
		
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
		
		this.updateValueSolveCalled();//Stichwort TODOGOON20250308 , auch die Entry-Werte der Parents muessen gesetzt werden
		this.updateValueSolveCalled(objReturnReference);
		
		sReturnLine = sExpressionIn;		
		this.setRaw(sReturnLine);
		objEntry.setRaw(sReturnLine);			
		sReturn = sReturnLine;
		
		sReturnTag = this.getValue(); //??? sollte das nicht auch sReturnLine sein ???
				
		main:{			
			//Aufloesen von Pfaden und ini-Variablen passierte schon beim Parsen.
			//Aufloesen des Math-Tags
			sReturnLine = this.solveParsed_Math_(sExpressionIn, objReturnReference, bRemoveSurroundingSeparators);
			sReturnTag = this.getValue();
			objEntry = objReturnReference.get();	
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);	
		}//end main
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {
				objEntry.isSolved(true);
				if(!sExpressionIn.equals(sReturnLine)) {
					objEntry.isSolvedChanged(true);
				}
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	
	private String solveParsed_Math_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{				
		String sReturn = null;	String sReturnTag = null; String sReturnLine = null;
		boolean bUseExpression=false;
		
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
					
			boolean bUseSolver = this.isSolverEnabledGeneral();
			if(!bUseSolver) break main;
					
			boolean bUseFormulaMath = this.isSolverEnabledEveryRelevant();		
			if(!bUseFormulaMath) break main;
						
			//++++++++++++++++++++++++++++++
		    //Das Ziel ist, das der Operator nur mit der reinen Formel arbeitet. Da er umgebenden Text nicht herausfiltern kann.
			//Nun den z:operator Tag suchen
						
			String sFormulaExpression = sExpression;
			ZTagFormulaMath_OperatorZZZ<T> objOperator = new ZTagFormulaMath_OperatorZZZ<T>();
			if(objOperator.isExpression(sFormulaExpression)){
				 sExpression = objOperator.solve(sFormulaExpression); //Merke: Das ist ein dummy - Solve, das nicht aus einem Interface stammt.				
				 sReturnTag = sExpression;			 
			}else{
				//Da gibt es wohl nix weiter auszurechen...	also die Werte als String nebeneinander setzen....
				//Nun die z:value-of Einträge suchen, Diese werden jeweils zu eine String aufaddiert.
				//TODOGOON20250406; //Die Expression muss sich dabei immer weiter reduzieren... Also Tags suchen...
				
				String sRemaining = sExpression;
				String sRemainingOld = sRemaining;
				while(XmlUtilZZZ.isExpression4TagXml(sRemaining, ZTagFormulaMath_ValueZZZ.sTAG_NAME)){
					ZTagFormulaMath_ValueZZZ<T> objValue = new ZTagFormulaMath_ValueZZZ<T>();
					Vector3ZZZ<String> vecRemaining = objValue.parseFirstVector(sRemaining, false); 					
					sRemaining = VectorUtilZZZ.implode(vecRemaining);
					
					if(sRemainingOld.equals(sRemaining)) break; //Sicherheitsmassnahme gegen Endlosschleife
					sRemainingOld = sRemaining;
				}
				sReturnTag = sRemainingOld;							
			}//end if operator.isExpression(...)
			
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);	
		}//end main:

					
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);	
		sReturnLine = sReturnTag;
		sReturn = sReturnLine;
		if(objEntry!=null) {		
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(sExpressionIn!=null) {
				objEntry.isSolved(true);
				if(!sExpressionIn.equals(sReturnLine)) {
					objEntry.isSolvedChanged(true);
				}
			}
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
		}
		return sReturn;	
	}
	
	//##########################
	//### FLAG Handling
	
	//### Aus Interface IKernelZFormulaIniZZZ
	@Override
	public boolean getFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelEncryptionIniSolverZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ.name(), bFlagValue);
	}
	
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIniZZZ.FLAGZ[] objaEnum_IKernelEncryptionIniSolverZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelEncryptionIniSolverZZZ)) {
				baReturn = new boolean[objaEnum_IKernelEncryptionIniSolverZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIniZZZ.FLAGZ objEnum_IKernelEncryptionIniSolverZZZ:objaEnum_IKernelEncryptionIniSolverZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelEncryptionIniSolverZZZ, bFlagValue);
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

}//End class