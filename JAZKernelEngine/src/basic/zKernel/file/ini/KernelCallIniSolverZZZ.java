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
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Diese Klasse verarbeitet ggf. Ausdruecke/Formeln in Ini-Dateien.
 *  Es kann dann in einem dieser Formeln z.B. auf den Property-Wert einer anderen Sektion zugegriffen werden. So entstehen 'dynamische' ini-Dateien.
 * @author lindhaueradmin
 *
 */
public class KernelCallIniSolverZZZ<T> extends AbstractKernelIniSolverZZZ<T> implements IKernelCallIniSolverZZZ, IKernelCallIniParseUserZZZ, IKernelCallIniSolveUserZZZ, IKernelJavaCallIniSolverZZZ{
	private static final long serialVersionUID = -8017698515311079738L;
	public static final String sTAG_NAME = "Z:Call";
	
	public KernelCallIniSolverZZZ() throws ExceptionZZZ{
		super("init");
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ{		
		super(objKernel,saFlag);
		KernelCallIniSolverNew_(null, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objFileIni);//als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelCallIniSolverNew_(objFileIni, null);
	}
	
	public KernelCallIniSolverZZZ(FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni, saFlag); //als IKernelUserZZZ - Object
		KernelCallIniSolverNew_(objFileIni, hmVariable);
	}
	
	public KernelCallIniSolverZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelCallIniSolverNew_(objFileIni, hmVariable);
	}
			
	private boolean KernelCallIniSolverNew_(FileIniZZZ<T> objFileIn, HashMapCaseInsensitiveZZZ<String,String> hmVariableIn) throws ExceptionZZZ {
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

	 }//end function KernelJsonIniSolverNew_
					
	//###### Getter / Setter
	
	//### aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return KernelCallIniSolverZZZ.sTAG_NAME;
//	}
//	
//	//### Aus ITagBasicChildZZZ
//	@Override
//	public String getParentNameDefault() throws ExceptionZZZ {
//		return KernelCallIniSolverZZZ.sTAG_PARENT_NAME;
//	}
	
		
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
			
			super.updateValueParseCustom(objReturnReference, sExpression); //isExpression setzen			
			if(!this.isParserEnabledCustom()) break main;
			
						
			if(XmlUtilZZZ.containsTagName(sExpressionIn, this.getName(), false)){
				objEntry.isCall(true);
				this.getEntry().isCall(true);
			}
			
			//########################
			//Es gilt: 
			//Wird der "Untersolver aufgerufen, dann wird auch der "Parent-Tag-Solver" hinsichtlich des Tags geprüft, 
			//auch wenn das USE-Flag deaktiviert ist
			//Wird der "Parent-Solver aufgerufen, dann wird das Tag eine deaktivierten "Untersolvers" nicht berücksichtigt.
				
			//DARUM:
			//Hier die moeglichen enthaltenden Tags alle Pruefen..., siehe auch KernelExpressionIniHandlerZZZ
			//Das kann man durch eine Kombination von
			//a) durch die Flags steuern
			//b) durch direkten Aufruf der "Untersolver".custom-Methode
			//c) durch direkte Analyse der Tags
		
			//a)
			boolean bUseJavaCall = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
//			//boolean bUseJsonMap = this.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
//			if(!bUseJavaCall) break main; // | bUseJsonArray )) break main;
//								
			if(bUseJavaCall) {
				//b)
				KernelJavaCallIniSolverZZZ<T> javaCallSolverDummy = new KernelJavaCallIniSolverZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, javaCallSolverDummy, true);
					
				KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
				objJavaCallSolver.updateValueParseCustom(objReturnReference, sExpression);
			}
		
			
//			//c)
//			//TODOGOON20250308; //TICKETGOON20250308;; //Analog zu dem PARENT - Tagnamen muesste es auch eine Loesung für die CHILD - Tagnamen geben
//			if(XmlUtilZZZ.containsTagName(sExpressionIn, KernelJavaCallIniSolverZZZ.sTAG_NAME, false)) {
//				objEntry.isJavaCall(true);
//				this.getEntry().isJavaCall(true);
//			}
			
			
		}//end main:
	}
	
	//### aus IKernelCallIniParseUserZZZ
	@Override
	public void updateValueCallParsedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueCallParsedChanged(objReturnReference,true);
	}

	@Override
	public void updateValueCallParsedChanged(boolean bIsCallParsedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueCallParsedChanged(objReturnReference,bIsCallParsedChanged);
	}

	@Override
	public void updateValueCallParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueCallParsedChanged(objReturnReference, true);
	}

	@Override
	public void updateValueCallParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference,boolean bIsCallParsedChanged) throws ExceptionZZZ {
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;
	
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isCallParsedChanged(bIsCallParsedChanged);
		}//end main:
	}

	//###  aus ISolveUserZZZ
	@Override
	public void updateValueSolved(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolved(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		//... den es hier nicht gibt. Anders als beim JAVACALL-SOLVER
		
		//Den eigenen Solver
		if(this.isSolverEnabledThis()) {
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isCallSolved(bIsSolveCalled);
		}
	}
		
	@Override
	public void updateValueSolveCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolveCalled(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		//... gibt es hier nicht
		
		//Den eigenen Solver
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isCallSolveCalled(bIsSolveCalled);
	}
	
	@Override
	public void updateValueSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSolveCalled) throws ExceptionZZZ{
		super.updateValueSolvedChanged(objReturnReference, bIsSolveCalled);
				
		//Den "Elternsolver", siehe dazu auch TicketGOON20250308
		//... gibt es hier nicht
		
		//Den eigenen Solver
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isCallSolvedChanged(bIsSolveCalled);		
	}
	
	//+++++++++++++++++++
	//### aus IKernelCallIniSolveUserZZZ
	@Override
	public void updateValueCallSolvedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueCallSolvedChanged(objReturnReference,true);
	}

	@Override
	public void updateValueCallSolvedChanged(boolean bIsCallSolvedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueCallSolvedChanged(objReturnReference,bIsCallSolvedChanged);
	}

	@Override
	public void updateValueCallSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueCallSolvedChanged(objReturnReference, true);
	}

	@Override
	public void updateValueCallSolvedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference,boolean bIsCallSolvedChanged) throws ExceptionZZZ {
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;
	
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			objEntry.isCallSolvedChanged(bIsCallSolvedChanged);
		}//end main:
	}
	
	//+++++++++++++++++++++++++++++++++++++++++
	//### aus IParseEnabled			
	@Override 
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		return true; //Somit ist das Parsen vom Solven entkoppelt. Das wäre default in der abstracten Elternklasse, s. Solver:  return this.isSolverEnabledThis();
	}
		
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
		
	//Analog zu KernelJsonMapIniSolverZZZ, KernelZFormulaMathSolver, KernelEncrytptionIniSolver aufbauen...	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {		
		return this.parseFirstVector_(sExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
	}
		
	private Vector3ZZZ<String>parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{		
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = sExpressionIn; 
		String sReturnTag = null; String sReturnLine = null;
		String sReturnLineParsed = null; String sReturnLineParsedCallInner = null;
		boolean bUseExpression=false; boolean bUseParser = false; boolean bUseParserThis=false; boolean bUseSolver=false; boolean bUseCall=false; boolean bUseSolverThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();														
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

		sReturnLine = sExpressionIn;
		objEntry.setRaw(sReturnLine);		
		this.setRaw(sReturnLine);
		sReturn = sReturnLine;
		
		sReturnTag = this.getValue();
						
		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
		
		main:{
			String sExpression = sExpressionIn;
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
				
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			//Direkte nachdem feststeht, dass Expression und Paser behandelt werden die Tags analysieren!!!
			this.updateValueParseCustom(objReturnReference, sExpression);
			
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			bUseParserThis = this.isParserEnabledCustom(); //this.isParserEnabledThis();
			if(!bUseParserThis) break main;
			
			
			//###########################################
			//### 
			//###########################################
			
			//Mehrere Ausdruecke. Dann muss der jeweilige "Rest-Bestandteil" des ExpressionFirst-Vectors weiter zerlegt werden.
			//Im Aufruf der Eltern-Methode findet ggfs. auch eine Aufloesung von Pfaden und eine Ersetzung von Variablen statt.
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry);			
			vecReturn = super.parseFirstVector(sExpression, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
			if(vecReturn==null) break main;
			
			parse:{
				//Pruefe ob der Tag enthalten ist:
				//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
				if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
			
				//++++++++++++++++++++++
				//+++ reicht wenn substitute in der Super-Klasse gemacht wurde.
				//++++++++++++++++++++++
			
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				this.setValue(sReturnTag);
				vecReturn.replace(sReturnTag);
				sReturnLineParsed = VectorUtilZZZ.implode(vecReturn);
				sReturnLine = sReturnLineParsed;
				sReturn = sReturnLine;
				
				//################
				//Abweichung vom AbstractSolver. Hier werden die einzelnen "Call-Elemente" auch schon geparsed
				//################
				//Das weitere Verarbeiten haengt aber davon ab, ob der Solver angestellt ist.
				bUseSolver = this.isSolverEnabledGeneral();//getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER);
				if(!bUseSolver) break main;
							
				bUseSolverThis = this.isSolverEnabledThis();		
				if(!bUseSolverThis) break main;
				
				//1.+ 2. Kein Versuch als HashMap oder ArrayList
				
				//3. Versuch als Einzelwert
				if(this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA)){
					if(vecReturn!=null) sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
					sExpression = sReturnTag;
					if(StringZZZ.isEmpty(sExpression)) break main;
					
					//WICHTIG: DIE FLAGS VERERBEN !!!
					KernelJavaCallIniSolverZZZ<T> init4FlagLookup = new KernelJavaCallIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
					
					//FileIniZZZ objFileIni = this.getFileIni();
					IKernelZZZ objKernel = this.getKernelObject();
					
					//Dann erzeuge neues KernelJavaCallSolverZZZ - Objekt.				
					KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(objKernel, saFlagZpassed);
					
					//Merke: sReturn hat dann wg. parse noch Werte drum herum. Darum den Wert es Tags holen.
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceJavaCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReferenceJavaCall.set(objEntry);
					sReturnLineParsedCallInner=objJavaCallSolver.parse(sExpression, objReturnReferenceJavaCall, bKeepSurroundingSeparatorsOnParse);
					objEntry = objReturnReferenceJavaCall.get();
					
					//20250304: Beim Parsen ist dient das Parsen des inneren Tag dazu hier ggf. noch Entry-Werte z.B. isPath() oder so, zu setzten.
					//          Falsch ist es aber den Wert des inneren Tags als eigenen Tag-Wert zu uebernehmen.
					//sReturnTag = objJavaCallSolver.getValue();
					//this.setValue(sReturnTag);
																				
					//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen				
					sReturnTag = sReturnLineParsedCallInner;
									
					//20250304: Den Returnwert des geparsten inneren Strings übernehmen. Der könnte geaendert worden sein.
					vecReturn.replace(sReturnLineParsedCallInner);//NUR BEIM SOLVEN DEN WERT IN VEC(1) UEBERNEHMEN				
					sReturnLine = VectorUtilZZZ.implode(vecReturn);
				}else {
					//Der JAVA-CALL Tag wird nicht verwendet. Aber der CALL-TAG schon.
					//Aber die umgebenden Tags sollen drinbleiben, darum hier nicht nacharbeiten.
					
					//Der Reine CALL-Tag Wert
					sReturnTag = this.getValue();
				}
			}//end parse:
			this.updateValueParsed();
			this.updateValueParsed(objReturnReference);
		}//end main:
		
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			if(bUseExpression) {		
				if(bUseSolver && bUseSolverThis) {
					//++++ Die Besonderheit ist hier: CALL und JAVA_CALL werden in einer Klasse erledigt....
					//     nur .isJavaCall() wird vorher gesetzt.
					if(sReturnLineParsedCallInner!=null) {
						if(sReturnLineParsed.equals(sReturnLineParsedCallInner)) {
							this.updateValueCallParsedChanged();
							this.updateValueCallParsedChanged(objReturnReference);
						}
					}																								
				}
				
				String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				if(!sExpression2Compare.equals(sReturnLine2Compare)) {
					this.updateValueParsedChanged();
					this.updateValueParsedChanged(objReturnReference);
				}		
				this.adoptEntryValuesMissing(objEntry);
			}
		}					
		return vecReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++			
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
		main:{			
			boolean bUseCall = this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(bUseCall) {
				objReturn = super.parseAsEntryNew(sExpression);	
				
				//Speichere nun das USECALL - Ergebnis auch ab.
				String sReturn = objReturn.getValue();
				objReturn.setValue(sReturn);
				objReturn.setValueCallSolvedAsExpression(sReturn);				
			}else {
				objReturn.setValue(sExpression);
				objReturn.setValueCallSolvedAsExpression(null);
			}									
		}//end main:
		return objReturn;
	}
		
	//### Aus ISolveEnabled	
	//Analog zu KernelJavaCallIniSolverZZZ, KernelJavaCallIniSolverZZZ, KernelJsonMapInisolver, KernelZFormulaMathSolver aufbauen... Der code ist im Parser
	//Merke: Mit folgender Methode wird über das konkrete Flag dieser Solver ein-/ausgeschaltet. 
		
	//       Z.B. wichtig für das Entfernen der geparsten Tags, oder halt dieses Entfernen weglassen.
	//Merke: Folgende Methoden muessen im konkreten Solver implementiert werden, da der abstrakte Solver die konkreten Flags zur deaktivierung diese konkreten Solvers nicht kennt.
	@Override
	public boolean isSolverEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
	}
	
	@Override
	public boolean isSolverEnabledAnyParentCustom() throws ExceptionZZZ {
		return false;
	}
	
	@Override
	public boolean isSolverEnabledAnyChildCustom() throws ExceptionZZZ {
		boolean bReturn = true;
		main:{
			bReturn = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
			if(bReturn) break main;
			
			bReturn = false;
		}//end main:
		return bReturn;
	}
	
	@Override
	public String solveParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.solveParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String solveParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
		return this.solveParsed_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	//Analog zu AbstractkernelIniSolverZZZ, nur jetzt mit Call-Tag (vorher aber noch Pfade und ini-Variablen aufloesen)
	/**Methode ersetzt in der Zeile alle CALL Werte.
	 * Methode überschreibt den abstrakten "solver", weil erst einmal Pfade oder Variablen ersetzt werden sollen.
	 * @param sExpression
	 * @param objEntryReference
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.04.2023, 15:28:40
	 */
	private String solveParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators)	throws ExceptionZZZ {
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
			
			//Aufloesen des CALL-Tags
			sReturnLine = this.solveParsed_JavaCall_(sExpression, objReturnReference, bRemoveSurroundingSeparators);
			sReturnTag = this.getValue();
			sReturnTagSolved = sReturnTag;
			objEntry = objReturnReference.get();	
					
			//##################################
			//### ENDE: Besonderheiten dieses Solvers
			//###################################	
						
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
				
		if(objEntry!=null) {
//			if(bUseSolver & bUseSolverThis) objEntry.setValueCallSolved(sReturnLine);
			if(bUseSolver) objEntry.setValueCallSolved(sReturnLine);
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
			}
			
			this.adoptEntryValuesMissing(objEntry);	
		}
		return sReturn;	
	}
	
	private String solveParsed_JavaCall_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ{		
		String sReturn = null; String sReturnLine = null; String sReturnTag = null; String sReturnTagParsed = null; String sReturnTagSolved = null;
		boolean bUseExpression = false; boolean bUseCall = false; boolean bUseCallJava = false;
				
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
		sReturnTag = sReturnLine; //schliesslich ist das eine solve ! PARSED ! Methode, also nicht this.getValue();
		
		sReturnTagParsed = sReturnTag;
		sReturnTagSolved = sReturnTag;
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
						
			bUseCall = this.isSolverEnabledThis(); //this.getFlag(IKernelCallIniSolverZZZ.FLAGZ.USECALL);
			if(!bUseCall) break main;
			
				bUseCallJava = this.getFlag(IKernelJavaCallIniSolverZZZ.FLAGZ.USECALL_JAVA);
				if(bUseCallJava) {									
					//Analog zum vorgehen in parseFirstVector(...), nur hier wird vom JavaCallIniSolver.solveParsed() aufgerufen.
					sExpression = sReturnTag;
					
					//Hier KernelJavaCallSolverZZZ verwenden
					//WICHTIG: DIE FLAGS VERERBEN !!!
					KernelJavaCallIniSolverZZZ<T> init4FlagLookup = new KernelJavaCallIniSolverZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, init4FlagLookup, true);
					
					IKernelZZZ objKernel = this.getKernelObject();		
					KernelJavaCallIniSolverZZZ<T> objJavaCallSolver = new KernelJavaCallIniSolverZZZ<T>(objKernel, saFlagZpassed);
					
					
				
					//Merke: Beim Parsen (parseFirstVector) werden interen Variabelen im objEntry gesetzt.
					//       Darum darf man dies nicht unterschlagen, indem man z.B. direkt solveParsed(sExpression) aufruft.
					//Aber: 20250409 Das sollte nun an anderer Stelle schon erledigt werden.
					
					ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceJavaCall = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					objReturnReferenceJavaCall.set(objEntry);
					
					//sReturnLine = objJavaCallSolver.solve(sExpression, objReturnReferenceJavaCall, bRemoveSurroundingSeparators);
					sReturnLine = objJavaCallSolver.solveParsed(sExpression, objReturnReferenceJavaCall, bRemoveSurroundingSeparators);
					sReturnLine = objJavaCallSolver.solveParsedWrapup(sReturnLine, objReturnReference, bRemoveSurroundingSeparators);
					objEntry = objReturnReferenceJavaCall.get();
					
					//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
					//Merke: sReturn hat dann wg. parse noch Werte drum herum. Darum den Wert es Tags holen.
					sReturnTag = objJavaCallSolver.getValue();
					sReturnTagSolved = sReturnTag;
					
					//FESTHALTEN, DASS DER JAVACALL-SOLVER GEWIRKT HAT
					if(objEntry!=null) {		
						//TODOGOON20250813 
						//objEntry.setLineJavaCallSolved(sReturnLineSolved);
						//objEntry.setValueJavaCallSolved(sReturnTagSolved);						
						if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
						if(sReturnTagSolved!=null) {
							if(!sReturnTagSolved.equals(sReturnTagParsed)) {	
								objJavaCallSolver.updateValueSolvedChanged();
								objJavaCallSolver.updateValueSolvedChanged(objReturnReference);
							}
						}
					}
					
					
//				}else {
//					//Also: CALL-Tag soll aufgeloest werden, JAVA-CALL aber nicht. 
//					//Dann muss/darf nur der CALL-Tag entfernt werden. Eine weitere Aufloesung passiert ja nicht.
//					sExpression = sReturn;				
//					sReturn = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpression, KernelCallIniSolverZZZ.sTAG_NAME);							
//				
				}//end if bUseJavaCall		
					
			this.updateValueSolved();
			this.updateValueSolved(objReturnReference);
		}//end main:
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHNUNG ERSTELLTEN WERT uebernehmen
		sReturn = sReturnLine;
		this.setValue(sReturnTag);
		if(objEntry!=null) {		
			if(bUseCall){
				//TODOGOON20250813
				//objEntry.setLineCallSolved(sReturnLineSolved);
				objEntry.setValueCallSolved(sReturnTagSolved);
			}
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
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
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### aus IConvertableZZZ
	@Override
	public boolean isConvertRelevant(String sToProof) throws ExceptionZZZ {			
		return false;
	}
	
	//###############################
	//### FLAG Handling
	
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
