package basic.zKernel.config;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionHandlingMainTypeZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceHashMapZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.file.ini.ExpressionIniUtilZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.KernelCallIniSolverZZZ;
import basic.zKernel.file.ini.KernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonIniSolverZZZ;
import basic.zKernel.file.ini.KernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniConverterZZZ;
import basic.zKernel.file.ini.KernelZFormulaIniSolverZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public class KernelConfigSectionEntryUtilZZZ implements IConstantZZZ{
	
	private KernelConfigSectionEntryUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	public static void adoptEntryValuesMissing(ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceTargetIn,  ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSourceIn) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntrySource = null;
		IKernelConfigSectionEntryZZZ objEntryTarget = null;
		
		main:{
			if(objReturnReferenceSourceIn==null) {				
			}else {
				objEntrySource = objReturnReferenceSourceIn.get();
			}			
			if(objEntrySource==null) break main; //ohne Souce hat das ja keinen Zweck.
							
			if(objReturnReferenceTargetIn==null) {				
			}else {
				objEntryTarget = objReturnReferenceTargetIn.get();
			}			
			if(objEntryTarget==null) {
				objEntryTarget = new KernelConfigSectionEntryZZZ();				
			}
			
			//######################################
			//Da die Werte immer nur von false nach true wechseln, also nur die übernehme, die im Target false sind.
			//Einfach die Methoden in IKernelConfigSectionEntryZZZ durchgehen.
			if(objEntrySource.isParseCalled()) {
				objEntryTarget.isParseCalled(objEntrySource.isParseCalled());
			}
		
			if(objEntrySource.isParsed()) {
				objEntryTarget.isParsed(objEntrySource.isParsed());
			}
			
			if(objEntrySource.isParsedChanged()) {
				objEntryTarget.isParsedChanged(objEntrySource.isParsedChanged());
			}
		
			
			if(objEntrySource.isSubstituteCalled()) {
				objEntryTarget.isSubstituteCalled(objEntrySource.isSubstituteCalled());
			}
			
			if(objEntrySource.isSubstitutedChanged()) {
				objEntryTarget.isSubstitutedChanged(objEntrySource.isSubstitutedChanged());
			}
			
			
			if(objEntrySource.isPathSubstituted()) {
				objEntryTarget.isPathSubstituted(objEntrySource.isPathSubstituted());
			}
			
			if(objEntrySource.isPathSubstitutedChanged()) {
				objEntryTarget.isPathSubstitutedChanged(objEntrySource.isPathSubstitutedChanged());
			}
			
			if(objEntrySource.isVariableSubstituted()) {
				objEntryTarget.isVariableSubstituted(objEntrySource.isVariableSubstituted());
			}
			
			if(objEntrySource.isVariableSubstitutedChanged()) {
				objEntryTarget.isVariableSubstituted(objEntrySource.isVariableSubstituted());
			}
			
			
						
			if(objEntrySource.isSolveCalled()) {
				objEntryTarget.isSolveCalled(objEntrySource.isSolveCalled());
			}
			
			if(objEntrySource.isSolved()) {
				objEntryTarget.isSolved(objEntrySource.isSolved());
			}
			
			if(objEntrySource.isSolvedChanged()) {
				objEntryTarget.isSolvedChanged(objEntrySource.isSolvedChanged());
			}
			
			if(objEntrySource.isConversion()) {
				objEntryTarget.isConversion(objEntrySource.isConversion());
			}
			
			if(objEntrySource.isConverted()) {
				objEntryTarget.isConverted(objEntrySource.isConverted());
			}
			
			if(!objEntrySource.getValueAsConversionVector().isEmpty()) {
				objEntryTarget.setValueAsConversionVector(objEntrySource.getValueAsConversionVector());
			}
		
			
			if(objEntrySource.isFormula()) {
				objEntryTarget.isFormula(objEntrySource.isFormula());
			}
			
			if(objEntrySource.isFormulaSolved()) {
				objEntryTarget.isFormulaSolved(objEntrySource.isFormulaSolved());
			}
			
			if(!objEntrySource.getValueFormulaSolvedAndConvertedVector().isEmpty()) {
				objEntryTarget.setValueFormulaSolvedAndConvertedVector(objEntrySource.getValueFormulaSolvedAndConvertedVector());
			}
			
			if(objEntrySource.isFormulaMathSolved()) {
				objEntryTarget.isFormulaMathSolved(objEntrySource.isFormulaMathSolved());
			}
						
			if(objEntrySource.isJson()) {
				objEntryTarget.isJson(objEntrySource.isJson());
			}
			
			if(objEntrySource.isJsonMap()) {
				objEntryTarget.isJsonMap(objEntrySource.isJsonMap());
			}
			
			if(objEntrySource.getValueHashMap()!=null){
				objEntryTarget.setValue(objEntrySource.getValueHashMap());
			}
			
			if(objEntryTarget.isJsonArray()) {
				objEntryTarget.isJsonArray(objEntrySource.isJsonArray());
			}
			
			if(objEntrySource.getValueArrayList()!=null){
				objEntryTarget.setValue(objEntrySource.getValueArrayList());
			}
			
			if(objEntrySource.isCrypt()) {
				objEntryTarget.isCrypt(objEntrySource.isCrypt());
			}
			
			if(objEntrySource.isDecrypted()) {
				objEntryTarget.isDecrypted(objEntrySource.isDecrypted());
			}
			
			if(!objEntrySource.getRawDecryptedVector().isEmpty()) {
				objEntryTarget.setRawDecryptedVector(objEntrySource.getRawDecryptedVector());
			}
			
			if(objEntrySource.isEncrypted()) {
				objEntryTarget.isEncrypted(objEntrySource.isEncrypted());
			}

			if(objEntrySource.isRawDecrypted()) {
				objEntryTarget.isRawDecrypted(objEntrySource.isRawDecrypted());
			}

			if(objEntrySource.isRawEncrypted()) {
				objEntryTarget.isRawEncrypted(objEntrySource.isRawEncrypted());
			}

			if(!objEntrySource.getRawEncryptedVector().isEmpty()) {
				objEntryTarget.setRawEncryptedVector(objEntrySource.getRawEncryptedVector());
			}

			if(!objEntrySource.getValueEncryptedVector().isEmpty()) {
				objEntryTarget.setValueEncryptedVector(objEntrySource.getValueEncryptedVector());
			}

			if(!objEntrySource.getValueEncryptedPartVector().isEmpty()) {
				objEntryTarget.setValueEncryptedPartVector(objEntrySource.getValueEncryptedPartVector());
			}

			if(!objEntrySource.getValueDecryptedVector().isEmpty()) {
				objEntryTarget.setValueDecryptedVector(objEntrySource.getValueDecryptedVector());
			}

			if(objEntrySource.isCall()) {
				objEntryTarget.isCall(objEntrySource.isCall());
			}

			if(objEntrySource.isCallParsed()) {
				objEntryTarget.isCallParsed(objEntrySource.isCallParsed());
			}
			
			if(objEntrySource.isCallParsedChanged()) {
				objEntryTarget.isCallParsedChanged(objEntrySource.isCallParsedChanged());
			}
			
			
			if(objEntrySource.isCallSolveCalled()) {				
				objEntryTarget.isCallSolveCalled(objEntrySource.isCallSolveCalled());
			}
			
			if(objEntrySource.isCallSolved()) {
				objEntryTarget.isCallSolved(objEntrySource.isCallSolved());
			}

			if(objEntrySource.isCallSolvedChanged()) {
				objEntryTarget.isCallSolvedChanged(objEntrySource.isCallSolvedChanged());
			}
			
			if(objEntrySource.isJavaCall()) {
				objEntryTarget.isJavaCall(objEntrySource.isJavaCall());
			}
			
			if(objEntrySource.isJavaCallSolveCalled()) {
				objEntryTarget.isJavaCallSolveCalled(objEntrySource.isJavaCallSolveCalled());
			}

			if(objEntrySource.isJavaCallSolved()) {
				objEntryTarget.isJavaCallSolved(objEntrySource.isJavaCallSolved());
			}
			
			if(objEntrySource.isJavaCallSolvedChanged()) {
				objEntryTarget.isJavaCallSolvedChanged(objEntrySource.isJavaCallSolvedChanged());
			}
			
			
			if(objEntrySource.getCallingClassname()!=null) {
				objEntryTarget.setCallingClassname(objEntrySource.getCallingClassname());
			}

			if(objEntrySource.getCallingMethodname()!=null) {
				objEntryTarget.setCallingMethodname(objEntrySource.getCallingMethodname());
			}

			if(objEntrySource.getValueCallSolved()!=null) {
				objEntryTarget.setValueCallSolved(objEntrySource.getValueCallSolved());
			}
	
			if(objEntrySource.isExploded()) {
				objEntryTarget.isExploded(objEntrySource.isExploded());
			}
			
			if(objEntrySource.getIndex()>=0) {
				objEntryTarget.setIndex(objEntrySource.getIndex());
			}

			if(objEntrySource.getKey()!=null) {
				objEntryTarget.setKey(objEntrySource.getKey());
			}

			if(objEntrySource.sectionExists()) {
				objEntryTarget.sectionExists(objEntrySource.sectionExists());
			}

			if(objEntrySource.propertyExists()) {
				objEntryTarget.propertyExists(objEntrySource.propertyExists());
			}		
		}//end main:
		
	}
	
	/** Die statischen Methoden dieser Klasse leisten mehr als nur die ...Solver... aufzurufen.
	 *  Durch den Rückgabewert können dann die Details im letztendlich benötigten IKernelConfigSectionEntryZZZ object gesetzt werden.
	 *  Also z.B. bJson, bJsonMap, etc...
	 *  
	 * Nur true / false zurückzugeben reicht daher nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  1 = Formel
	 *  2 = Expression
	 *  3 = Formel UND Expression
	 *  
	 *  5 = JsonMap
	 *  6 = JsonArray
	 *  usw. denkbar fortsetzbar
	 *  
	 *  
	 * @param objFileIni
	 * @param sRaw
	 * @param bUseFormula
	 * @param hmVariable
	 * @param saFlagZpassed
	 * @param objsReturnValueExpressionSolved
	 * @param objsReturnValueConverted
	 * @param objsReturnValue
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 19.12.2019, 11:18:39
	 */
	public static String getExpressionSolvedAndConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
		String sReturn = sRaw;
		main:{
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			IKernelConfigSectionEntryZZZ objEntry = null;
			if(objReturnReferenceIn==null) {				
			}else {
				objEntry = objReturnReferenceIn.get();
			}			
			if(objEntry==null) {
				objEntry = new KernelConfigSectionEntryZZZ();				
			}
			
			String sRawExpressionSolved = null;
			ReferenceZZZ<String> objsReturnValueExpressionSolved= new ReferenceZZZ<String>("");			
			boolean bExpressionSolved = KernelConfigSectionEntryUtilZZZ.getExpressionSolved(objFileIni, sRaw, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueExpressionSolved);							
			if(bExpressionSolved) {			
				if(bUseFormula) objEntry.isFormula(true);
				String sValueSolved = objsReturnValueExpressionSolved.get();					
				objEntry.setValueAsExpression(sValueSolved);				
				sRawExpressionSolved = sValueSolved;				
			}else {
				sRawExpressionSolved = sRaw;
			}
			sReturn = sRawExpressionSolved;

			String sRawConverted = null;
			ReferenceZZZ<String> objsReturnValueConverted= new ReferenceZZZ<String>("");
			boolean bConverted = KernelConfigSectionEntryUtilZZZ.getConverted(objFileIni, sRawExpressionSolved, bUseFormula, hmVariable, saFlagZpassed, objsReturnValueConverted);
			if(bConverted) {				
				objEntry.isConverted(true);
				
				
				String sValueAsConversion= objEntry.getValue();
				objEntry.setValueAsConversion(sValueAsConversion);
				
				sRawConverted = objsReturnValueConverted.get();								
			}else {
				sRawConverted = sRawExpressionSolved;
			}
			sReturn = sRawConverted;
			
			objEntry.setValue(sRawConverted);
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);					
		}//end main:
		return sReturn;
	}
	
	public static boolean getExpressionSolved(FileIniZZZ objFileIni, String sRawIn, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueExpressionSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseFormula) break main;
			
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sValueExpressionSolved=null;
			boolean bAnyFormula = false;
			String sRaw = sRawIn;
			String sRawOld = sRaw;
			while(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelZFormulaIniSolverZZZ.sTAG_NAME)){//Schrittweise die Formel auflösen.
				bAnyFormula = true;
									
				KernelZFormulaIniSolverZZZ ex = new KernelZFormulaIniSolverZZZ(objFileIni, hmVariable, saFlagZpassed);
				String stemp = ex.solve(sRaw);
				if(!StringZZZ.equals(stemp,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniSolver verändert von '" + sRaw + "' nach '" + stemp +"'");
				}else {
					break;
				}
				sRaw=stemp;//Sonst Endlosschleife.
				if(sRawOld.equals(sRaw)) break;
				sRawOld = sRaw;
			}
			sValueExpressionSolved = sRaw;
			if(bAnyFormula){
				objsReturnValueExpressionSolved.set(sValueExpressionSolved);
				bReturn = true;
			}															
		}//end main:
		return bReturn;
	}
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix

		
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	public static boolean getCallSolved(FileIniZZZ objFileIni, String sExpressionIn, boolean bUseCall, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ{
		boolean bReturn = false;
		 
		IKernelConfigSectionEntryZZZ objEntry = null;
		IKernelConfigSectionEntryZZZ objReturn = objEntry; //new KernelConfigSectionEntryZZZ<T>(this);
					
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			//static Methode, darum geht nicht  objEntry = new KernelConfigSectionEntryZZZ<T>(this); //this.getEntryNew(); es gingen alle Informationen verloren				
			                                                                                    //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ();
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReferenceIn.get();
		}
		
		if(objEntry==null) {
			//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
			//objEntry = this.getEntry();
			//static Methode, darum geht nicht  objEntry = new KernelConfigSectionEntryZZZ<T>(this); // =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ();
		}	
		 
		 main:{			 			 								
	 		if(!bUseCall)break main;
	 		
	 		if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

	 		
						
			parse:{
				if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
				String sExpression = sExpressionIn;
				
				objEntry.setRaw(sExpression);
				objReturnReference.set(objEntry);
											
				String sExpressionOld = "";
				KernelCallIniSolverZZZ objDummy = new KernelCallIniSolverZZZ();			
				while(objDummy.isSolve(sExpression) && !sExpressionOld.equals(sExpression)){//Schrittweise die Formel auflösen UND Verhindern von Endlosschleife.			
					bReturn = true;
										
					IKernelZZZ objKernel = null;
					if(objFileIni!=null) {
						objKernel = objFileIni.getKernelObject();
					}
					KernelCallIniSolverZZZ ex = new KernelCallIniSolverZZZ(objKernel, saFlagZpassed);
					ex.setEntry(objEntry);
					
					//TODOGOON20250409: Loesungsansatz... wenn man will, das in objEntry entsprechende Statuswerte gesetzt werden, dann muss man auch mit dem Objekt der Klasse selbst arbeiten 
					//                  und nicht mit dem static Aufruf einer Hilfsmethode. 
					//                  Kein Wunder das in der Analyse durch die JUnit-Tests bei CallIniSolver die Z-Tags fehlen.
					//Vector<String>vecReturn=XmlUtilZZZ.parseFirstVector(sExpression, ex.getTagPartOpening(), ex.getTagPartClosing(), !bForFurtherProcessing);
					Vector3ZZZ<String>vecReturn = ex.parseFirstVector(sExpression);
					if(vecReturn==null) break main;
					
					
					//Pruefe ob der Tag enthalten ist:
					//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
					if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
	
					String sValue = vecReturn.get(1).toString();
					if(!StringZZZ.equals(sValue,sExpression)){
						sExpression = ex.solveParsed(sValue, objReturnReferenceIn, !bForFurtherProcessing);					
						objEntry = objReturnReferenceIn.get();										
						//Merke: Hier nicht anschliessend solveParsedWrapup(sExpression) aufrufen. Das "Aufraeumen" der aufrufenden Methode ueberlassen. Sie will ja ggfs. noch andere Solver aufrufen.
						
						if(!StringZZZ.equals(sValue,sExpression)){
							System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch CallIniSolverZZZ verändert von '" + sExpression + "' nach '" + sValue +"'");
						}
					}
					sExpressionOld = sExpression;
					sExpression=sValue;//Sonst Endlosschleife.					
				}//end while
										
				if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
		 	}//end parse:
		 }//end main:
		 return bReturn;
	 }
	
	
	public static boolean getConverted(FileIniZZZ objFileIni, String sRaw, boolean bUseFormula, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueConverted) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseFormula) break main;
				
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//20190122: Ein Ansatz leere Werte zu visualisieren. Merke: <z:Empty/> wird dann als Wert erkannt und durch einen echten Leerstring erstetzt.
			//Merke: Der Expression-Wert kann sowohl direkt in der Zeile stehen, als auch erst durch einen Formel gesetzt worden sein.
			boolean bAnyExpression = false;				
			String sValueConverted = KernelZFormulaIniConverterZZZ.getAsStringStatic(sRaw);
			if(!StringZZZ.equals(sRaw,sValueConverted)){
				System.out.println(ReflectCodeZZZ.getPositionCurrent()+ ": Value durch ExpressionIniConverter verändert von '" + sRaw + "' nach '" + sValueConverted +"'");
				bAnyExpression = true;						
			}					
			if(bAnyExpression){
				objsReturnValueConverted.set(sValueConverted);					
				bReturn = true;
			}				
		}//end main:
		return bReturn;
	}
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix

		
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	 //public static boolean getValueEncryptionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseEncryption, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<String>objsReturnValueEncryptionSolved, ReferenceZZZ<ICryptZZZ>objobjReturn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
	public static boolean getEncryptionSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseEncryption, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ{
		 boolean bReturn = false;
		 main:{			 			 								
	 		if(!bUseEncryption)break main;
	 		
	 		if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		 	IKernelConfigSectionEntryZZZ objReturn=objReturnReference.get();
			if(objReturn==null) objReturn = new KernelConfigSectionEntryZZZ();//Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.

			boolean bAnyEncryption = false;
			
			KernelEncryptionIniSolverZZZ objDummy = new KernelEncryptionIniSolverZZZ();			
			while(objDummy.isExpression(sRaw)){//Schrittweise die Formel auflösen.
				objReturn.setRawEncrypted(sRaw);
				bAnyEncryption = true;
									
				IKernelZZZ objKernel = null;
				if(objFileIni!=null) {
					objKernel = objFileIni.getKernelObject();
				}
				KernelEncryptionIniSolverZZZ ex = new KernelEncryptionIniSolverZZZ(objKernel, saFlagZpassed);
				ex.setEntry(objReturn);
				String sValue = null; String sFall=null;
				if(!bForFurtherProcessing) {
					sFall = "computeAsEntry";
					objReturn = ex.solveAsEntryNew(sRaw);
					sValue = objReturn.getValue();
					
				}else {
					sFall = "computeAsExpression";
					objReturn = ex.solveAsEntryNew(sRaw);
					sValue = objReturn.getValue();
					sValue = ExpressionIniUtilZZZ.makeAsExpression(sValue);					

				}
				
				if(!StringZZZ.equals(sValue,sRaw)){
					System.out.println(ReflectCodeZZZ.getPositionCurrent()+ " - " + sFall + ": Value durch EncryptionIniSolverZZZ verändert von '" + sRaw + "' nach '" + sValue +"'");
					objReturn.setRawEncrypted(sRaw);
					objReturn.isDecrypted(true);
					objReturn.setRawDecrypted(sValue);
					objReturn.setValueDecrypted(sValue);					
				}					
				sRaw=sValue;//Sonst Endlosschleife.
				
				
				ICryptZZZ objCrypt = ex.getCryptAlgorithmType();//Zur weiteren Verwendung, z.B. zum erneuten Verschluesseln mit einem geaenderten Wert hier auch zurueckgeben.
				objReturn.setCryptAlgorithmType(objCrypt);				
			}

			if(bAnyEncryption){
				objReturn.isCrypt(true);
				bReturn = true;
			}				
					
			objReturnReference.set(objReturn);
		 }//end main:
		 return bReturn;
	 }
	
	
	/** Nur true / false zurückzugeben reicht nicht. Darum wird ein Integerwert zurückgegeben, der die Kombinationen verschlüsselt enthält:
	 *  0 = nix
	 *  5 = JsonArray
	 *  6 = JsonMap
	 *  usw. denkbar fortsetzbar
	 *  
	 * 
	 */
	 public static boolean getJsonSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, boolean bForFurtherProcessing, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, ReferenceArrayZZZ<String>objalsReturnValueJsonSolved,ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved) throws ExceptionZZZ{
		 boolean bReturn = false;
		 main:{			 			 								
			 		if(!bUseJson)break main;
			 		
			 		String sReturnLine = sRaw;
			 		
			 		if(objFileIni==null){
						String stemp = "'IniFile'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
			 		
			 		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
					IKernelConfigSectionEntryZZZ objEntry = null;
					if(objReturnReferenceIn==null) {
						objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					}else {
						objReturnReference = objReturnReferenceIn;
					}
					
					objEntry = objReturnReference.get();
					if(objEntry==null) {						
						objEntry = new KernelConfigSectionEntryZZZ(); 
						objReturnReference.set(objEntry);
					}else {						
					}
					
					String sTagStartZ = "<Z>";
					String sTagEndZ = "</Z>";
				
					boolean bUseJsonFlagged = objFileIni.getFlag(IKernelJsonIniSolverZZZ.FLAGZ.USEJSON);
					if(bUseJsonFlagged) {
						boolean bAnyJson = KernelConfigSectionEntryUtilZZZ.getJsonSolved(objFileIni, sRaw, bUseJson, saFlagZpassed, objReturnReferenceIn, objalsReturnValueJsonSolved);			
						if(bAnyJson) {				
//							objEntry.isJson(true);
//							objEntry.isJsonArray(true);
//							ArrayList<String> listas = objalsReturnValueJsonSolved.getArrayList();
//							objEntry.setValue(listas); //Dabei wird auch der String-Wert gesetzt
//						
							sReturnLine = objEntry.getValue();
//							break main;
						}						
					}else {
						
						//Einschraenkung auch auf das was im Ini-File moeglich ist.
				 		boolean bUseJsonArrayFlagged = objFileIni.getFlag(IKernelJsonArrayIniSolverZZZ.FLAGZ.USEJSON_ARRAY);
						if(bUseJsonArrayFlagged) {
												
							//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.						
							boolean bAnyJsonArray = KernelConfigSectionEntryUtilZZZ.getJsonArraySolved(objFileIni, sRaw, bUseJson, saFlagZpassed, objReturnReferenceIn, objalsReturnValueJsonSolved);			
							if(bAnyJsonArray) {				
//								objEntry.isJson(true);
//								objEntry.isJsonArray(true);
//								ArrayList<String> listas = objalsReturnValueJsonSolved.getArrayList();
//								objEntry.setValue(listas); //Dabei wird auch der String-Wert gesetzt
//							
								sReturnLine = objEntry.getValue();
//								break main;
							}					
						}
				 		
				 		boolean bUseJsonMapFlagged = objFileIni.getFlag(IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP);
						if(bUseJsonMapFlagged) {				
						
							//Merke: objReturnValue ist ein Hilfsobjekt, mit dem CallByReference hinsichtlich der Werte realisiert wird.						
							boolean bAnyJsonMap = KernelConfigSectionEntryUtilZZZ.getJsonMapSolved(objFileIni, sRaw, bUseJson, saFlagZpassed, objReturnReferenceIn, objhmReturnValueJsonSolved);			
							if(bAnyJsonMap) {						
//								objEntry.isJson(true);
//								objEntry.isJsonMap(true);
//								HashMap<String,String> hm = objhmReturnValueJsonSolved.get();
//								objEntry.setValue(hm); //Dabei wird auch der String-Wert gesetzt
//								
								sReturnLine = objEntry.getValue();
//								break main;
							}
						} 

					}
					
					
					
//								
					//+++++++++++++++++++++++++++++++++++++++++
					if(!bForFurtherProcessing) { //umgebendes Z-Tag entfernen					
						sReturnLine = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ);
					}
					
					objEntry.setValue(sReturnLine);
					bReturn = true;
		 }//end main:
		 return bReturn;
	 }
	
	 
	 /*
		 * @param objFileIni
		 * @param sRaw
		 * @param bUseJson	 
		 * @param saFlagZpassed
		 * @param objsReturnJson
		 * @return
		 * @throws ExceptionZZZ
		 * @author Fritz Lindhauer, 11.07.2021
		 */
		public static boolean getJsonSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, ReferenceArrayZZZ<String>objalsReturnValueJsonSolved) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				if(bUseJson){
					if(objFileIni==null){
						String stemp = "'IniFile'";
						System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
						ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
					IKernelConfigSectionEntryZZZ objEntry = null;
					if(objReturnReferenceIn==null) {
						objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
					}else {
						objReturnReference = objReturnReferenceIn;
					}
					
					objEntry = objReturnReference.get();
					if(objEntry==null) {					
						objEntry = new KernelConfigSectionEntryZZZ();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
						objReturnReference.set(objEntry);
					}else {					
					}
									
					
					boolean bAnyJson = false;									
					if(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelJsonIniSolverZZZ.sTAG_NAME)){
						
						//Ohne diese "minimal notwendigen "Flags macht diese utility - Methode keinen Sinn.
						//Dann noch die übergebenen Flags hinzunehmen.
						//String[]saFlagZ = {IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() , IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
						//saFlagZ =StringArrayZZZ.appendMissing(saFlagZ, saFlagZpassed, null);
							
						//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonArray passen.
						//      Darum neu definieren
						KernelJsonIniSolverZZZ exDummy03 = new KernelJsonIniSolverZZZ();
						String[] saFlag = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);
						//String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
					
						//Einschraenkung auch auf die uebergebenen Flags
						//String[] saFlag = StringArrayZZZ.intersect(saFlagZpassed03, saFlagZpassed);
											
						KernelJsonIniSolverZZZ ex = new KernelJsonIniSolverZZZ(objFileIni, saFlag);
						//20250412; das scheint zu klappen alstemp = ex.computeArrayList(sRaw);
						//aber eigentlich muss es sein
						ex.solve(sRaw,objReturnReference);
						bAnyJson = true;						
					}

					if(bAnyJson){					
						objEntry.isJson(true);						
						bReturn = true;
					}												
				}else{
					//Fall: Keine Formel soll interpretiert werden.
					//unverändert
				}		
			}//end main:
			return bReturn;
		}

	 
	 
	 /*
	 * @param objFileIni
	 * @param sRaw
	 * @param bUseJson	 
	 * @param saFlagZpassed
	 * @param objsReturnJson
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 11.07.2021
	 */
	public static boolean getJsonArraySolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, ReferenceArrayZZZ<String>objalsReturnValueJsonSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(bUseJson){
				if(objFileIni==null){
					String stemp = "'IniFile'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PROPERTY_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
				IKernelConfigSectionEntryZZZ objEntry = null;
				if(objReturnReferenceIn==null) {
					objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				}else {
					objReturnReference = objReturnReferenceIn;
				}
				
				objEntry = objReturnReference.get();
				if(objEntry==null) {					
					objEntry = new KernelConfigSectionEntryZZZ();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
					objReturnReference.set(objEntry);
				}else {					
				}
								
				ArrayList<String> alsRawJsonSolved=null;
				boolean bAnyJson = false;
				
				ArrayList<String> alstemp = null;
				if(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelJsonArrayIniSolverZZZ.sTAG_NAME)){
					
					//Ohne diese "minimal notwendigen "Flags macht diese utility - Methode keinen Sinn.
					//Dann noch die übergebenen Flags hinzunehmen.
					//String[]saFlagZ = {IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() , IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
					//saFlagZ =StringArrayZZZ.appendMissing(saFlagZ, saFlagZpassed, null);
						
					//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonArray passen.
					//      Darum neu definieren
					KernelJsonArrayIniSolverZZZ exDummy03 = new KernelJsonArrayIniSolverZZZ();
					String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
				
					//Einschraenkung auch auf die uebergebenen Flags
					String[] saFlag = StringArrayZZZ.intersect(saFlagZpassed03, saFlagZpassed);
										
					KernelJsonArrayIniSolverZZZ ex = new KernelJsonArrayIniSolverZZZ(objFileIni, saFlag);
					//20250412; das scheint zu klappen alstemp = ex.computeArrayList(sRaw);
					//aber eigentlich muss es sein
					ex.solve(sRaw,objReturnReference);
					alstemp = ex.getValueArrayList();					
					if(alstemp==null || alstemp.isEmpty()) {				
					}else{
						bAnyJson = true;
					}
				}

				if(bAnyJson){					
					objEntry.isJson(true);
					objEntry.isJsonArray(true);
					//ArrayList<String> listas = objalsReturnValueJsonSolved.getArrayList();
					//objEntry.setValue(listas); //Dabei wird auch der String-Wert gesetzt
				
					objalsReturnValueJsonSolved.set(alstemp);	
					objEntry.setValue(alstemp);
					bReturn = true;
				}												
			}else{
				//Fall: Keine Formel soll interpretiert werden.
				//unverändert
			}		
		}//end main:
		return bReturn;
	}
	
	
	public static boolean getJsonMapSolved(FileIniZZZ objFileIni, String sRaw, boolean bUseJson, String[] saFlagZpassed, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, ReferenceHashMapZZZ<String,String>objhmReturnValueJsonSolved) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(!bUseJson) break main;
			
			if(objFileIni==null){
				String stemp = "'IniFile'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KernelConfigSectionEntryUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
			IKernelConfigSectionEntryZZZ objEntry = null;
			if(objReturnReferenceIn==null) {
				objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			}else {
				objReturnReference = objReturnReferenceIn;
			}
			
			objEntry = objReturnReference.get();
			if(objEntry==null) {				
				objEntry = new KernelConfigSectionEntryZZZ();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
				objReturnReference.set(objEntry);
			}else {
			}
			
		
			boolean bAnyJson = false;
			
			HashMap<String,String> hmtemp = null;
			if(KernelConfigSectionEntryUtilZZZ.isExpression(sRaw,KernelJsonMapIniSolverZZZ.sTAG_NAME)){
				//Ohne diese "minimal notwendigen "Flags macht diese utility - Methode keinen Sinn.
				//Dann noch die übergebenen Flags hinzunehmen.
				//String[]saFlagZ = {IKernelJsonIniSolverZZZ.FLAGZ.USEJSON.name() , IKernelJsonMapIniSolverZZZ.FLAGZ.USEJSON_MAP.name()};
				//saFlagZ =StringArrayZZZ.appendMissing(saFlagZ, saFlagZpassed, null);
				
				//ABER: Nicht alle flags aus saFlagZpassed mussen für JsonArray passen.
				//      Darum neu definieren
				KernelJsonMapIniSolverZZZ exDummy03 = new KernelJsonMapIniSolverZZZ();
				String[] saFlagZpassed03 = FlagZFassadeZZZ.seekFlagZrelevantForObject(objFileIni, exDummy03, true); //this.getFlagZ_passable(true, exDummy);					
			
				//Einschraenkung auch auf die uebergebenen Flags
				String[] saFlag = StringArrayZZZ.intersectOrNotNull(saFlagZpassed03, saFlagZpassed);
								
				KernelJsonMapIniSolverZZZ ex = new KernelJsonMapIniSolverZZZ(objFileIni, saFlag);
				//DAS KLAPPT 20250412  hmtemp = ex.computeLinkedHashMap(sRaw); //TODO GOON 20241009; //Eigentlich muss noch eine Reference fuer objEntry uebergeben werden.
				//TODOGOON20250412; //das sollte docch eigentlich ...
				ex.solve(sRaw, objReturnReferenceIn); //...sein.Darin dann solveParsed und darin dann irgendwann computeLinkedHashMap();
				hmtemp = ex.getValueHashMap();
				if(hmtemp==null || hmtemp.isEmpty()) {				
				}else{
					bAnyJson = true;
				}
			}

			if(bAnyJson){			
				objEntry.isJson(true);
				objEntry.isJsonMap(true);
				//HashMap<String,String> hm = objhmReturnValueJsonSolved.get();
				//objEntry.setValue(hm); //Dabei wird auch der String-Wert gesetzt
								
				objhmReturnValueJsonSolved.set(hmtemp);
				objEntry.setValue(hmtemp);
				bReturn = true;
			}													
		}//end main:
		return bReturn;
	}
	
	public static boolean isExpression(String sLine, String sExpressionTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Merke: z:null und z:empty sind nicht Tagspezifische Ausdrücke, gelten als Conversion
			
			//Merke: Contains reicht nicht. Die Positionen sind auch wichtig.
			boolean bAsTagFound = XmlUtilZZZ.containsTagName(sLine, sExpressionTagName, false);
			if(!bAsTagFound) break main;

			bReturn = true;
		}//end main
		return bReturn;
	}
	
	public static boolean isExpressionTagNameValid(String sExpressionTagName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static boolean isConvertable(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			//Anders als Tags sind NULL und EMPTY Werte Conversions. 
			//Sie entstehen sogar, wenn nur der Leerstring konfiguriert wird
			boolean bIsEmptyTag = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagEmpty());
			if(bIsEmptyTag) {
				bReturn = true;
				break main;
			}
			boolean bIsNullTag = StringZZZ.contains(sLine, XmlUtilZZZ.computeTagNull());
			if(bIsNullTag) {
				bReturn = true;
				break main;
			}
			
		}//end main:
		return bReturn;
		
	}
	
	
	//###########################################
	//### TAGS ENTEFERNEN
	//###########################################
	
	//###########################################
	//### CONTAINED
	//###########################################
	public static String getExpressionTagContainedRemoved(String sExpression, String sTagName, String sTagNameContainer) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainedRemoved_(sExpression, sTagName, false, sTagNameContainer);	
	}
	
	public static String getExpressionTagpartsContainedRemoved(String sExpression, String sTagName, String sTagNameContainer) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainedRemoved_(sExpression, sTagName, true, sTagNameContainer);
	}
	
	private static String getExpressionTagpartsContainedRemoved_(String sExpression, String sTagName, boolean bLeaveTagAnyContent, String sTagNameContainer) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
			XmlUtilZZZ.ensureExpressionTagNameValid(sTagNameContainer);
			
			String sTagPartOpening; String sTagPartClosing;
			sTagPartOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
			sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
			
			String sTagPartContainerOpening; String sTagContainerClosing;
			sTagPartContainerOpening = XmlUtilZZZ.computeTagPartOpening(sTagNameContainer);
			sTagContainerClosing = XmlUtilZZZ.computeTagPartClosing(sTagNameContainer);
			
			boolean bLeaveTagContent = bLeaveTagAnyContent;
			boolean bLeaveTagInterContent = bLeaveTagAnyContent;
			sReturn = getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing, bLeaveTagContent, bLeaveTagInterContent, sTagPartContainerOpening, sTagContainerClosing);
		}//end main;
		return sReturn;
	}
	
	private static String getExpressionTagpartsContainedRemoved_(String sExpression, String sTagName, boolean bLeaveTagContent, boolean bLeaveTagInterContent, String sTagNameContainer) throws ExceptionZZZ {
		String sReturn = sExpression;		
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
			XmlUtilZZZ.ensureExpressionTagNameValid(sTagNameContainer);									
			
			String sTagPartOpening; String sTagPartClosing;
			sTagPartOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
			sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
			
			String sTagPartContainerOpening; String sTagPartContainerClosing;
			sTagPartContainerOpening = XmlUtilZZZ.computeTagPartOpening(sTagNameContainer);
			sTagPartContainerClosing = XmlUtilZZZ.computeTagPartClosing(sTagNameContainer);
			
			sReturn = getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing, bLeaveTagContent, bLeaveTagInterContent, sTagPartContainerOpening, sTagPartContainerClosing);
		}//end main;
		return sReturn;
	}
	
	
	

	public static String getExpressionTagContainedRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bLeaveTagAnyContent, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		boolean bLeaveTagContent = bLeaveTagAnyContent;
		boolean bLeaveTagInterContent = bLeaveTagAnyContent;
		return getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing, bLeaveTagContent, bLeaveTagInterContent, sTagPartContainerOpening, sTagPartContainerClosing);
	}
	
	public static String getExpressionTagContainedRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bLeaveTagContent, boolean bLeaveTagInterContent, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		return getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing, bLeaveTagContent, bLeaveTagInterContent, sTagPartContainerOpening, sTagPartContainerClosing);
	}
	
	public static String getExpressionTagContainedRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		return getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing, false, false, sTagPartContainerOpening, sTagPartContainerClosing);
	}
	
	public static String getExpressionTagpartsContainedRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {	
		return getExpressionTagContainedRemoved_(sExpression, sTagPartOpening, sTagPartClosing,true, true, sTagPartContainerOpening, sTagPartContainerClosing);
	}
	

	
	private static String getExpressionTagContainedRemoved_(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bLeaveTagContent,  boolean bLeaveTagInterContent, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));	
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing));
			
			//Den Container Tag parsen aus dem String
			Vector3ZZZ<String>vecExpression	= StringZZZ.vecMidCascaded(sExpression, sTagPartContainerOpening, sTagPartContainerClosing, true, false);//Also MIT CONTAINER Tags holen, ohne Exactmatch
			//Vector3ZZZ<String>vecExpression	= StringZZZ.vecMidFirstKeepSeparatorCentral(sValueExpression, sTagContainerStart, sTagContainerEnd, false); //Also MIT CONTAINER Tags holen -im mittleren Teil des Vectors -, ohne Exactmatch
			if(vecExpression==null) break main;

			//### Den zu entfernenden Tag ermitteln
			String sExpressionInner = (String) vecExpression.get(1);
			if(StringZZZ.isEmpty(sExpressionInner)) break main;
									
			Vector3ZZZ<String> vecExpressionInner = StringZZZ.vecMidFirst(sExpressionInner, sTagPartOpening, sTagPartClosing, true);//Also MIT Tags holen, Merke, das wird gemacht, um ggfs. "Zwischen den Tags stehenden Text" zu behalten.
			if(vecExpressionInner!=null) {
				if(!bLeaveTagInterContent) {
					vecExpressionInner.replace(0,""); //allen Content vor dem Tag entfernen
				}else {
					vecExpressionInner.replace(0,StringZZZ.stripRight((String) vecExpressionInner.get(0), sTagPartOpening)); //Den Container Tag entfernen
				}
				if(!bLeaveTagContent) {
					vecExpressionInner.replace(1,""); //allen Content innerhalb des Tags entfernen
				}
				if(!bLeaveTagInterContent) {
					vecExpressionInner.replace(2,""); //allen Content hinter dem Tag entfernen
				}else {
					vecExpressionInner.replace(2,StringZZZ.stripLeft((String) vecExpressionInner.get(2), sTagPartClosing)); //Den Container Tag entfernen
				}
			}
			
			//Idee: bRemoveTagInterContent... also auch den Content Zwischen den Tags (d.h. vor dem zu entfernenden Tag loeschen
			//                                Das müsste dann allerdings in einer großen Abfrage drumherum gemacht werden.
			
			//Hier der code oben, vor der Verschlankung
//			if(bRemoveTagContent) {
//				//### Den Tag aus der Mitte entfernen und den Content
//				//vecExpressionInner = StringZZZ.vecMidFirst(sExpressionInner, sTagStart, sTagEnd, false);//Also ohne Tags holen
//				vecExpressionInner = StringZZZ.vecMidFirst(sExpressionInner, sTagStart, sTagEnd, true);//Also MIT Tags holen, Merke, das wird gemacht, um ggfs. "Zwischen den Tags stehenden Text" zu behalten.
//				if(vecExpressionInner!=null) {
//					vecExpressionInner.replace(0,StringZZZ.stripRight((String) vecExpressionInner.get(0), sTagStart)); //Den Container Tag entfernen
//					vecExpressionInner.replace(1,""); //allen Content innerhalb des Tags entfernen
//					vecExpressionInner.replace(2,StringZZZ.stripLeft((String) vecExpressionInner.get(2), sTagEnd)); //Den Container Tag entfernen
//				}
//			}else {
//				//### Den Tag aus der Mitte entfernen, aber den Content behalten!!!
//				vecExpressionInner = StringZZZ.vecMidFirst(sExpressionInner, sTagStart, sTagEnd, true);//Also MIT Tags holen, Merke, das wird gemacht, um ggfs. "Zwischen den Tags stehenden Text" zu behalten.
//				//vecExpressionInner = StringZZZ.vecMidFirstKeepSeparatorCentral(sExpressionInner, sTagStart, sTagEnd, false); //Also MIT CONTAINER Tags holen -im mittleren Teil des Vectors -, ohne Exactmatch
//				if(vecExpressionInner!=null) {
//					vecExpressionInner.replace(0,StringZZZ.stripRight((String) vecExpressionInner.get(0), sTagStart)); //Den Container Tag entfernen
//					//... der Inhalt bleibt aber erhalten. vecExpressionInner.replace(1,""); //allen Content innerhalb des Tags entfernen
//					vecExpressionInner.replace(2,StringZZZ.stripLeft((String) vecExpressionInner.get(2), sTagEnd)); //Den Container Tag entfernen
//				}				
//			}
						
			//### Die bereinigte Mitte wieder in das Gesamttag uebernehmen.
			String sReturnInner="";
			if(vecExpressionInner!=null) sReturnInner = VectorUtilZZZ.implode(vecExpressionInner);
			
			vecExpression.replace(1, sReturnInner);			
			sReturn = VectorUtilZZZ.implode(vecExpression);
			
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression veraendert nach = '"+sReturn+"'");			
		}//end main:
		return sReturn;
	}


	//###########################################
	public static Vector3ZZZ<String> getExpressionTagpartsContainedRemoved(Vector3ZZZ<String>vecExpression, String sTagStart, String sTagEnd, String sTagParentStart, String sTagParentEnd) throws ExceptionZZZ {		
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(vecExpression, sTagStart, sTagEnd, true,  sTagParentStart, sTagParentEnd, true);
	}
	
	public static  Vector3ZZZ<String> getExpressionTagContainedRemoved(Vector3ZZZ<String>vecExpression, String sTagStart, String sTagEnd, String sTagParentStart, String sTagParentEnd) throws ExceptionZZZ {		
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(vecExpression, sTagStart, sTagEnd, false,  sTagParentStart, sTagParentEnd, true);
	}	
				
	public static  Vector3ZZZ<String> getExpressionTagpartsContainedRemoved(Vector3ZZZ<String>vecExpression, String sTagStart, String sTagEnd, String sTagParentStart, String sTagParentEnd, boolean bOnAnyPosition) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(vecExpression, sTagStart, sTagEnd, false,  sTagParentStart, sTagParentEnd, bOnAnyPosition);
	}
	
	public static  Vector3ZZZ<String> getExpressionTagContainedRemoved(Vector3ZZZ<String>vecExpression, String sTagStart, String sTagEnd, String sTagParentStart, String sTagParentEnd, boolean bOnAnyPosition) throws ExceptionZZZ {
		return KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(vecExpression, sTagStart, sTagEnd, false,  sTagParentStart, sTagParentEnd, bOnAnyPosition);
	}
	
	public static  Vector3ZZZ<String> getExpressionTagContainedRemoved_(Vector3ZZZ<String>vecExpressionIn, String sTagStart, String sTagEnd, boolean bLeaveTagAnyContent,  String sTagParentStart, String sTagParentEnd, boolean bOnAnyPosition) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = null;
		main:{		
			if(vecExpressionIn==null)break main;
			vecReturn = new Vector3ZZZ<String>(vecExpressionIn);//Mache eine deep copy von Vector3ZZZ objekt.
			
			//+++ Den Parent Tag holen aus dem zusammengefassten Vector.
			String sExpression = VectorUtilZZZ.implode(vecExpressionIn);
			boolean bLeaveTagContent = bLeaveTagAnyContent;
			boolean bLeaveTagInterContent = bLeaveTagAnyContent;
			String sReturnRemoved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(sExpression, sTagStart, sTagEnd, bLeaveTagContent, bLeaveTagInterContent, sTagParentStart, sTagParentEnd);
			
			
			//+++ Den Ausgangsvector wieder herstellen.
			String  sPRE = (String) vecExpressionIn.get(0);						
			String sPOST = (String) vecExpressionIn.get(2);
			if(bOnAnyPosition) {								
				//Das funktioniert, wenn der zu entfernende Tag nicht im PRE / POST selbst ist.												
				if(!StringZZZ.contains(sPRE, sTagStart) && !StringZZZ.contains(sPOST, sTagEnd)) {
					vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPRE, sPOST, true);//Also mit Tags holen	
				}else {
					//Wenn der zu entfernende Tag im PRE / POST selbst ist, diesen daraus loeschen 
					//und dann den Vector neu aufbauen.
					String sPreCleaned = StringZZZ.replace(sPRE, sTagStart, "");
					String sPostCleaned = StringZZZ.replace(sPOST,  sTagEnd, "");
					vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPreCleaned, sPostCleaned, true);//Also mit Tags holen
				}								
				break main;				
			}else {
				//Nur die Mitte betrachten.
				vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPRE, sPOST, true);//Also mit Tags holen								
			}
									
//			//Wenn der zu entfernende Tag im PRE / POST selbst ist, diesen daraus loeschen 
//			//und dann den Vector neu aufbauen.
//			if(StringZZZ.contains(sPRE, sTagStart) && StringZZZ.contains(sPOST, sTagEnd)) {
//				String sPreCleaned = StringZZZ.replace(sPRE, sTagStart, "");
//				String sPostCleaned = StringZZZ.replace(sPOST,  sTagEnd, "");				
//				vecReturn = StringZZZ.vecMidFirst(sReturnRemoved, sPreCleaned, sPostCleaned);
//			}else {
//				vecReturn = StringZZZ.vecMidFirst(sReturnRemoved, sPRE, sPOST);
//			}
			
		}//end main:
		return vecReturn;
	}	
	
	public static  Vector3ZZZ<String> getExpressionTagContainedRemoved_(Vector3ZZZ<String>vecExpressionIn, String sTagStart, String sTagEnd, boolean bRemoveTagContent, boolean bRemoveTagInterContent, String sTagParentStart, String sTagParentEnd, boolean bOnAnyPosition) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = null;
		main:{		
			if(vecExpressionIn==null)break main;
			vecReturn = new Vector3ZZZ<String>(vecExpressionIn);//Mache eine deep copy von Vector3ZZZ objekt.
			
			//+++ Den Parent Tag holen aus dem zusammengefassten Vector.
			String sExpression = VectorUtilZZZ.implode(vecExpressionIn);
			String sReturnRemoved = KernelConfigSectionEntryUtilZZZ.getExpressionTagContainedRemoved_(sExpression, sTagStart, sTagEnd, bRemoveTagContent, bRemoveTagInterContent, sTagParentStart, sTagParentEnd);
			
			
			//+++ Den Ausgangsvector wieder herstellen.
			String  sPRE = (String) vecExpressionIn.get(0);						
			String sPOST = (String) vecExpressionIn.get(2);
			if(bOnAnyPosition) {								
				//Das funktioniert, wenn der zu entfernende Tag nicht im PRE / POST selbst ist.												
				if(!StringZZZ.contains(sPRE, sTagStart) && !StringZZZ.contains(sPOST, sTagEnd)) {
					vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPRE, sPOST, true);//Also mit Tags holen	
				}else {
					//Wenn der zu entfernende Tag im PRE / POST selbst ist, diesen daraus loeschen 
					//und dann den Vector neu aufbauen.
					String sPreCleaned = StringZZZ.replace(sPRE, sTagStart, "");
					String sPostCleaned = StringZZZ.replace(sPOST,  sTagEnd, "");
					vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPreCleaned, sPostCleaned, true);//Also mit Tags holen
				}								
				break main;				
			}else {
				//Nur die Mitte betrachten.
				vecReturn = StringZZZ.vecMidCascaded(sReturnRemoved, sPRE, sPOST, true);//Also mit Tags holen								
			}
									
//			//Wenn der zu entfernende Tag im PRE / POST selbst ist, diesen daraus loeschen 
//			//und dann den Vector neu aufbauen.
//			if(StringZZZ.contains(sPRE, sTagStart) && StringZZZ.contains(sPOST, sTagEnd)) {
//				String sPreCleaned = StringZZZ.replace(sPRE, sTagStart, "");
//				String sPostCleaned = StringZZZ.replace(sPOST,  sTagEnd, "");				
//				vecReturn = StringZZZ.vecMidFirst(sReturnRemoved, sPreCleaned, sPostCleaned);
//			}else {
//				vecReturn = StringZZZ.vecMidFirst(sReturnRemoved, sPRE, sPOST);
//			}
			
		}//end main:
		return vecReturn;
	}	

	
	//######################################################
	//### SURROUNDING A CONTAINER
	//######################################################
	
	
	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sValueExpression, String sTagName, String sTagContainerName) throws ExceptionZZZ {
		return getExpressionTagpartsContainerSurroundingRemoved(sValueExpression, sTagName, true, true, sTagContainerName);
	}
	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sValueExpression, String sTagName, boolean bDirectionFromInToOut, String sTagContainerName) throws ExceptionZZZ {
		return getExpressionTagpartsContainerSurroundingRemoved(sValueExpression, sTagName, bDirectionFromInToOut, true, sTagContainerName);
	}
	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sExpression, String sTagName, boolean bDirectionFromInToOut, boolean bAnyPosition, String sTagContainerName) throws ExceptionZZZ {
		String sReturn = sExpression;		
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(XmlUtilZZZ.ensureExpressionTagNameValid(sTagName));
			if(XmlUtilZZZ.ensureExpressionTagNameValid(sTagContainerName));
								
			String sTagStart; String sTagEnd;
			sTagStart = XmlUtilZZZ.computeTagPartOpening(sTagName);
			sTagEnd = XmlUtilZZZ.computeTagPartClosing(sTagName);
			
			String sTagContainerStart = XmlUtilZZZ.computeTagPartOpening(sTagContainerName);
			String sTagContainerEnd = XmlUtilZZZ.computeTagPartClosing(sTagContainerName);
					
			sReturn = getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagStart, sTagEnd, bDirectionFromInToOut, bAnyPosition, sTagContainerStart, sTagContainerEnd);
		}//end main;
		return sReturn;
	}
	
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing ) throws ExceptionZZZ {
		return getExpressionTagpartsContainerSurroundingRemoved(sExpression, sTagPartOpening, sTagPartClosing, true, true, sTagPartContainerOpening, sTagPartContainerClosing);
	}
	
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bDirectionFromInToOut, boolean bAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		if(bDirectionFromInToOut) {
			return getExpressionTagpartsContainerSurroundingRemovedFromInToOut(sExpression, sTagPartOpening, sTagPartClosing, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);
		}else {
			return getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(sExpression, sTagPartOpening, sTagPartClosing, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);
		}
		
	}
	
	public static String getExpressionTagpartsContainerSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bDirectionFromInToOut, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		if(bDirectionFromInToOut) {
			return getExpressionTagpartsContainerSurroundingRemovedFromInToOut(sExpression, sTagPartOpening, sTagPartClosing, true, sTagPartContainerOpening, sTagPartContainerClosing);
		}else {
			return getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(sExpression, sTagPartOpening, sTagPartClosing, true, sTagPartContainerOpening, sTagPartContainerClosing);
		}
		
	}
	
	
	public static String getExpressionTagpartsContainerSurroundingRemovedFromInToOut(String sExpression, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));				
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing));
		
			//Vector<String>vecReturn = StringZZZ.vecMidFirst(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
			Vector3ZZZ<String>vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(vecReturn, sTagPartOpening, sTagPartClosing, true, true, sTagPartContainerOpening, sTagPartContainerClosing);

			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
	
	public static String getExpressionTagpartsContainerSurroundingRemovedFromInToOut(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
							
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));				
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing));
			
			//Container herausloesen, diese Tags sollen im Mittelteil des 3er Vectors sein.
			Vector3ZZZ<String>vecReturn =StringZZZ.vecMidKeepSeparatorCentral(sExpression, sTagPartContainerOpening, sTagPartContainerClosing, false);
			
			//Vector<String>vecReturn = StringZZZ.vecMidFirst(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
			//Vector3ZZZ<String>vec = StringZZZ.vecMid(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(vecReturn, sTagPartOpening, sTagPartClosing, true, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);

			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
		
	//####################################
	public static String getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(String sExpression, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(StringZZZ.isEmpty(sTagPartOpening)) break main;
			if(StringZZZ.isEmpty(sTagPartClosing)) break main;
			if(StringZZZ.isEmpty(sTagPartContainerOpening)) break main;
			if(StringZZZ.isEmpty(sTagPartContainerClosing)) break main;
																		
			Vector3ZZZ<String>vecReturn = StringZZZ.vecMidKeepSeparatorCentral(sExpression, sTagPartContainerOpening, sTagPartContainerClosing, false); //Also mit Tags holen, die dann in der Mitte erhalten bleiben
			if(vecReturn==null) break main;
			
			//Dogwatch-Klauseln
		    //A) Der Container Tag muss in der Mitte stehen
			String sCONTAINER = vecReturn.get(1).toString();
			if(!StringZZZ.startsWith(sCONTAINER, sTagPartContainerOpening)){
				ExceptionZZZ ez = new ExceptionZZZ("Expected a starting container tag at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(!StringZZZ.endsWith(sCONTAINER, sTagPartContainerClosing)){
				ExceptionZZZ ez = new ExceptionZZZ("Expected a closing container tag at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//Nun müssen aus dem PRE bzw. POST Teil die umgebendenden TagParts rausgenommen werden.
			String sPRE = (String) vecReturn.get(0);				
			String sPOST = vecReturn.get(2).toString();
			
			//B) Watchdog clausel fuer die gleiche GESAMT Anzahl von Tags in sPRE, sPOST!!!
			//   PROBLEMATISCH FUER INNEN NACH AUSSEN
			int iCountOPENING = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);
			iCountOPENING = iCountOPENING + XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);
			
			int iCountCLOSING = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);
			iCountCLOSING = iCountCLOSING + XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);
			
			if(iCountOPENING != iCountCLOSING){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			//C) Watchdog clausel fuer die SYMMETRISCHE gleiche Anzahl von Tags pro sPRE, bzw. sPOST
			//   PROBLEMATISCH FUER DAS ENTFERNEN VON TAGS VON AUSSEN nach INNEN
			int iCountOPENING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);
			int iCountCLOSING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartClosing, false);
			
			int iCountOPENING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);
			int iCountCLOSING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);
			
			if(iCountOPENING_PRE-iCountCLOSING_PRE != iCountCLOSING_POST-iCountOPENING_POST){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			
			int iPreStart = StringZZZ.count(sPRE, sTagPartOpening);
			int iPreEnd = StringZZZ.count(sPRE, sTagPartClosing);
			
			int iPostStart = StringZZZ.count(sPOST, sTagPartOpening);
			int iPostEnd = StringZZZ.count(sPOST, sTagPartClosing);
			
			int iPreRemovable = iPreStart - iPreEnd;
			if(iPreRemovable<=0) break main; //dann ist nix zu entfernen
			
			int iPostRemovable = iPostEnd - iPostStart;
			if(iPostRemovable<=0) break main; //dann ist nix zu entfernen
			
			if(iPostRemovable==0 || iPreRemovable==0) break main; //wenn es kein passenden zu ersetztenden String gibt, abbruch.
			sPRE = StringZZZ.replaceFromLeft1(sPRE, sTagPartOpening, "");
			sPOST = StringZZZ.replaceFromRight1(sPOST, sTagPartClosing, "");
			
			
			vecReturn.replace(0, sPRE);
			vecReturn.replace(2, sPOST);
			
			sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
	
	public static String getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		Vector3ZZZ<String>vecReturn=null;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));				
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing));
						

			//TODOGOON: Iregendwie fehlt hier noch die Schärfe, wo der Unterschied ist!!!!
			if(!bAnyPosition) {
				//vecReturn = StringZZZ.vecMidFirst(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
				vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
				
				//nur vecMid reicht schon. Das würde dann noch weiteres entfernen... 			
				//KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemovedFromOutToIn(vecReturn, sTagStart, sTagEnd, bAnyPosition);

			}else {
				
				//vecReturn = StringZZZ.vecMidFirst(sValueExpression, sTagStart, sTagEnd, false);//Also ohne Tags holen
				vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
				
				//nur vecMid reicht schon. Das würde dann noch weiteres entfernen... 			
				//KernelConfigSectionEntryUtilZZZ.getValueExpressionTagSurroundingRemovedFromOutToIn(vecReturn, sTagStart, sTagEnd, bAnyPosition);

				
				
			}
			
			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
		
			
		
		
	public static void getExpressionTagpartsContainerSurroundingRemovedFromInToOut(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bRemoveAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
			if(vecReturn==null)break main;				
							
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);
			XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing);
			
			//Dogwatch-Klauseln
		    //A) Der Container Tag muss in der Mitte stehen
//				String sCONTAINER = vecReturn.get(1).toString();
//				if(!StringZZZ.startsWith(sCONTAINER, sTagPartContainerOpening)){
//					ExceptionZZZ ez = new ExceptionZZZ("Expected a starting container tag at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
//				if(!StringZZZ.endsWith(sCONTAINER, sTagPartContainerClosing)){
//					ExceptionZZZ ez = new ExceptionZZZ("Expected a closing container tag at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
			
			//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
			String sPRE = (String) vecReturn.get(0);				
			String sPOST = vecReturn.get(2).toString();
							
			//B) Watchdog clausel fuer die gleiche Gesamtanzahl von Tags in sPRE plus sPOST!!!
			int iCountOPENING_TOTAL = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);
			iCountOPENING_TOTAL = iCountOPENING_TOTAL + XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);
			
			int iCountCLOSING_TOTAL = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);
			iCountCLOSING_TOTAL = iCountCLOSING_TOTAL + XmlUtilZZZ.countTagPart(sPRE, sTagPartClosing, false);
			
			if(iCountOPENING_TOTAL != iCountCLOSING_TOTAL){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//Zaehle die Tags fuer weitere Watchdog clauseln
			int iCountOPENING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);								
			int iCountCLOSING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);				
			int iCountCLOSING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartClosing, false);				
			int iCountOPENING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);

			
			
			//C) Watchdog clausel fuer Beschraenkung der Anzahl auf maximal 1 umgebungstag.
			//   !!! Dies ist nur problematisch fuer das Entfernen der Tags von innen nach aussen.
			//   TODOGOON: Damit das funktioniert muessten beim Entfernen 2 Bedingungen abgeprueft werden.
			//             1) Hinter dem oeffnenden Tag (,welches einem zu loeschenden Tagnamen entspricht)
			//				  gibt es ein passendes schliessendes Tag.
			//                Dann ignoriere den TagPart fuer die Loeschung.
			//             2) Es gibt keine weiteren Tags hinter dem offnenden Tag (siehe 1) und dem passenden schliessenden Tag. 

			
			if(iCountOPENING_PRE >= 2) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_TOOMANY + ": '" + sTagPartOpening + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}						
			if(iCountCLOSING_POST >= 2) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_TOOMANY + ": '" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
			

			//D) Watchdoc clausel fuer Beschraenkung der Anzahl auf min 1 Umgebungstag				
			//Keine Exception werfen. Wenn gar kein Umgebungstag da ist, einfach vorzeitig beenden.
			if(iCountOPENING_PRE==0 && iCountCLOSING_PRE==0 && iCountOPENING_POST==0 && iCountCLOSING_POST==0 ) break main;

											
			//Ea) Watchdog clausel dafuer, dass fuer jedes in POST geschlossenem Tag ein geoeffnetes Tag in PRE gibt.
			if(iCountOPENING_PRE > iCountCLOSING_POST) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING + ": '" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			
			//Eb) Watchdog clausel dafuer, dass fuer jedes in PRE geschlossenem Tag ein geoeffnetes Tag in POST gibt.												
			if(iCountOPENING_PRE <= 0) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING + ": '" + sTagPartOpening + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
			
			
			//Ec) Watchdog clausel dafuer, das es fuer jedes in PRE nicht geschlossene Tag ein schliessendes Tag in sPOST gibt. 
			//   Etwas genauer als das einfache zaehlen der Gesamttags.			
			if(iCountOPENING_PRE-iCountCLOSING_PRE != iCountCLOSING_POST-iCountOPENING_POST){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			
			//F) Watchdog clausel dafuer, das es mindestens 1 ueberzaehliges Tag gibt vor und nach dem Container, der entfernt werden darf.
			if(iCountOPENING_PRE<(iCountCLOSING_PRE+1)) {					
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(iCountCLOSING_POST<(iCountOPENING_POST+1)) {					
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		
							
			String sPREold = sPRE;				
			String sPOSTold = sPOST;
			
			if(bRemoveAnyPosition) {
				while(StringZZZ.contains(sPRE, sTagPartOpening, false) & StringZZZ.contains(sPOST, sTagPartClosing, false)) {						
					sPRE = StringZZZ.replaceFromRight1(sPRE,sTagPartOpening, "");						
					sPOST = StringZZZ.replaceFromLeft1(sPOST, sTagPartClosing, "");
											
					if(sPREold.equals(sPRE) | sPOSTold.equals(sPOST)) break; //sonst ggfs. Endlosschleifengefahr.
					sPREold=sPRE;
					sPOSTold=sPOST;
				}//end while
			
				//Merke: Wg. Container, wird nix aus dem mittleren Teil entfernt.

			}else {
				//Nur 1x entfernen
				if(StringZZZ.contains(sPRE, sTagPartOpening, false) & StringZZZ.contains(sPOST, sTagPartClosing, false)) {
					sPRE = StringZZZ.replaceFromRight1(sPRE,sTagPartOpening, "");
					sPOST = StringZZZ.replaceFromLeft1(sPOST, sTagPartClosing, "");
				}	
				
				//Merke: Wg. Container, wird nix aus dem mittleren Teil entfernt.
				
			}//end if bRemoveAnyPosition	
	
			vecReturn.replace(0,sPRE);
			vecReturn.replace(2,sPOST);
		}//end main
	}	
	
	
		
	public static void getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bRemoveAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
		    //Verglichen mit ...FromIntoOut gilt also:
			//Alle endsWith werden zu startWith und alle startWith werden zu endsWith.
		    //und leftbacks werden zu right und alle rightbacks zu left.
		
			if(vecReturn==null)break main;
			
//			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
//			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));				
//			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartContainerOpening));
//			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartContainerClosing));
			
			//Dogwatch-Klauseln
		    //A) Der Container Tag muss in der Mitte stehen
//				String sCONTAINER = vecReturn.get(1).toString();
			
//				//Hier keinen Fehler werfen, da ggfs. auch Strings ohne Tag drum verarbeitet werden.
//				if(!StringZZZ.startsWith(sCONTAINER, sTagPartContainerOpening)){
////					ExceptionZZZ ez = new ExceptionZZZ("Expected an opening container tagpart at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
////					throw ez;
//					break main;
//				}
//				
//				//Wenn ein oeffnendes Tag da war aber dann doch den Fehler werfen wenn der schliessende Tag nicht da ist.
//				if(!StringZZZ.endsWith(sCONTAINER, sTagPartContainerClosing)){
//					ExceptionZZZ ez = new ExceptionZZZ("Expected a closing container tagpart at mid-position of the vector3 '" + sTagPartContainerOpening +"'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
							
			//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
			String sPRE = (String) vecReturn.get(0);				
			String sPOST = vecReturn.get(2).toString();
			
			//B) Watchdog clausel fuer die gleiche Gesamtanzahl von Tags in sPRE plus sPOST!!!
			int iCountOPENING_TOTAL = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);
			iCountOPENING_TOTAL = iCountOPENING_TOTAL + XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);
			
			int iCountCLOSING_TOTAL = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);
			iCountCLOSING_TOTAL = iCountCLOSING_TOTAL + XmlUtilZZZ.countTagPart(sPRE, sTagPartClosing, false);
			
			if(iCountOPENING_TOTAL != iCountCLOSING_TOTAL){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_UNEQUAL_TOTAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Zähle die Tags fuer die Watchdog clauseln
			int iCountOPENING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartOpening, false);								
			int iCountCLOSING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartClosing, false);				
			int iCountCLOSING_PRE = XmlUtilZZZ.countTagPart(sPRE, sTagPartClosing, false);				
			int iCountOPENING_POST = XmlUtilZZZ.countTagPart(sPOST, sTagPartOpening, false);

			
			//C) Watchdoc clausel fuer Beschraenkung der Anzahl auf max 1 Umgebungstag
			//   Diese ist fuer AUSSEN nach INNEN nicht so problematisch																
			//........
			
			
			//D) Watchdoc clausel fuer Beschraenkung der Anzahl auf min 1 Umgebungstag				
			//Keine Exception werfen. Wenn gar kein Umgebungstag da ist, einfach vorzeitig beenden.
			if(iCountOPENING_PRE==0 && iCountCLOSING_PRE==0 && iCountOPENING_POST==0 && iCountCLOSING_POST==0 ) break main;

											
			//Ea) Watchdog clausel dafuer, dass fuer jedes in POST geschlossenem Tag ein geoeffnetes Tag in PRE gibt.
			if(iCountOPENING_PRE > iCountCLOSING_POST) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_MISSING + ": '" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			
			//Eb) Watchdog clausel dafuer, dass fuer jedes in PRE geschlossenem Tag ein geoeffnetes Tag in POST gibt.												
			if(iCountOPENING_PRE <= 0) {
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_MISSING + ": '" + sTagPartOpening + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
			
			
			//Ec) Watchdog clausel dafuer, das es fuer jedes in PRE nicht geschlossene Tag ein schliessendes Tag in sPOST gibt. 
			//   Etwas genauer als das einfache zaehlen der Gesamttags.			
			if(iCountOPENING_PRE-iCountCLOSING_PRE != iCountCLOSING_POST-iCountOPENING_POST){
				ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_UNEQUAL + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//Warum kam diese Clausel eigentlich rein??? Vielleicht Entfernen von Innen nach Aussen?
			//F) Watchdog clausel dafuer, das es mindestens 1 ueberzaehliges Tag gibt vor und nach dem Container, der entfernt werden darf.
//				if(iCountOPENING_PRE<=iCountCLOSING_PRE+1) {					
//					ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_OPENING_SURPLUS_MISSING + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
//				if(iCountCLOSING_POST<=iCountOPENING_POST+1) {					
//					ExceptionZZZ ez = new ExceptionZZZ(XmlUtilZZZ.sERROR_TAGPARTS_SURROUNDING_CONTAINER_CLOSING_SURPLUS_MISSING + ": '" + sTagPartOpening +"'/'" + sTagPartClosing + "'", iERROR_PARAMETER_VALUE, KernelConfigSectionEntryUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
		
			
			
			
			String sPREold = sPRE;				
			String sPOSTold = sPOST;
			
			if(bRemoveAnyPosition) {
				while(StringZZZ.contains(sPRE, sTagPartOpening, false) & StringZZZ.contains(sPOST, sTagPartClosing, false)) {						
					sPRE = StringZZZ.replaceFromLeft1(sPRE,sTagPartOpening, "");						
					sPOST = StringZZZ.replaceFromRight1(sPOST, sTagPartClosing, "");
											
					if(sPREold.equals(sPRE) | sPOSTold.equals(sPOST)) break; //sonst ggfs. Endlosschleifengefahr.
					sPREold=sPRE;
					sPOSTold=sPOST;
				}//end while
			
				//Merke: Wg. Container, wird nix aus dem mittleren Teil entfernt.

			}else {
				//Nur 1x entfernen
				if(StringZZZ.contains(sPRE, sTagPartOpening, false) & StringZZZ.contains(sPOST, sTagPartClosing, false)) {
					sPRE = StringZZZ.replaceFromLeft1(sPRE,sTagPartOpening, "");
					sPOST = StringZZZ.replaceFromRight1(sPOST, sTagPartClosing, "");
				}	
				
				//Merke: Wg. Container, wird nix aus dem mittleren Teil entfernt.
				
			}//end if bRemoveAnyPosition	
	
			vecReturn.replace(0,sPRE);
			vecReturn.replace(2,sPOST);
		}//end main
	}	
	
	
	//################
	//Merke: Default ist ...FromInToOut... , ... bAnyPosition ...
	public static void getExpressionTagpartsContainerSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, true, sTagPartContainerOpening, sTagPartContainerClosing);
		}//end main
	}
	
	public static void getExpressionTagpartsContainerSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);
		}//end main
	}
	
	public static void getExpressionTagpartsContainerSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bDirectionFromInToOut, boolean bAnyPosition, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
			if(bDirectionFromInToOut) {
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);
			}else {
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemovedFromOutToIn(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition, sTagPartContainerOpening, sTagPartContainerClosing);
			}
		}//end main
	}	
	
	
	public static void getExpressionTagpartsContainerSurroundingRemovedFromIntoOut(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, String sTagPartContainerOpening, String sTagPartContainerClosing) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, true, sTagPartContainerOpening, sTagPartContainerClosing);
		}//end main
	}
		
	
	//######################################################
	//### SURROUNDING
	//######################################################
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf. 
	//Merke: Die Exception zu werfen ist default
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagName, true, true, ExceptionHandlingMainTypeZZZ.TypeZZZ.THROW);
	}
	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	//Merke: Die Exception zu werfen ist default
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName, boolean bDirectionFromInToOut) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagName, true, bDirectionFromInToOut, ExceptionHandlingMainTypeZZZ.TypeZZZ.THROW);
	}

    //++++++++++	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	//Merke: Steuere das Verhalten eine Exception zu verarbeiten
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName, IEnumSetMappedZZZ objEnumExceptionHandlingType) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagName, true, true, objEnumExceptionHandlingType);
	}
	
	//Merke: Von Innen nach aussen zu entfernen ist Default. Bei verschachtelten Tags loest man auch von innen nach aussen auf.
	//Merke: Steuere das Verhalten eine Exception zu verarbeiten
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName, boolean bDirectionFromInToOut, IEnumSetMappedZZZ objEnumExceptionHandlingType) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagName, true, bDirectionFromInToOut, objEnumExceptionHandlingType);
	}
	
	//++++++++++++
	//Merke: Steuere Richtung (Innen nach aussen) und Position im String.
	//Merke: Die Exception zu werfen ist default
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName, boolean bAnyPosition, boolean bDirectionFromInToOut) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagName, bAnyPosition, bDirectionFromInToOut, ExceptionHandlingMainTypeZZZ.TypeZZZ.THROW);
	}
		
	//++++++++++++
	//Merke: Steuere Richtung (Innen nach aussen) und Position im String.
	//Merke: Steuere das Verhalten eine Exception zu verarbeiten
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagName, boolean bAnyPosition, boolean bDirectionFromInToOut, IEnumSetMappedZZZ objEnumExceptionHandlingType) throws ExceptionZZZ {
		String sReturn = sExpression;		
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
						
			if(objEnumExceptionHandlingType == ExceptionHandlingMainTypeZZZ.TypeZZZ.CATCH_AWAY) {
				try {
					XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
				}catch(Exception e) {
					break main;
				}
			}else {
				XmlUtilZZZ.ensureExpressionTagNameValid(sTagName);
			}
			
			String sTagPartOpening; String sTagPartClosing;
			sTagPartOpening = XmlUtilZZZ.computeTagPartOpening(sTagName);
			sTagPartClosing = XmlUtilZZZ.computeTagPartClosing(sTagName);
					
			sReturn = getExpressionTagpartsSurroundingRemoved(sExpression, sTagPartOpening, sTagPartClosing, bAnyPosition, bDirectionFromInToOut);
		}//end main;
		return sReturn;
	}
	
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagPartOpening, sTagPartClosing, true, true);
	}
	
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bDirectionFromInToOut) throws ExceptionZZZ {
		return getExpressionTagpartsSurroundingRemoved(sExpression, sTagPartOpening, sTagPartClosing, true, bDirectionFromInToOut);
	}
	
	public static String getExpressionTagpartsSurroundingRemoved(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition, boolean bDirectionFromInToOut) throws ExceptionZZZ {
		if(bDirectionFromInToOut) {
			return getExpressionTagpartsSurroundingRemovedFromInToOut(sExpression, sTagPartOpening, sTagPartClosing, bAnyPosition);
		}else {
			return getExpressionTagpartsSurroundingRemovedFromOutToIn(sExpression, sTagPartOpening, sTagPartClosing, bAnyPosition);
		}
		
	}
		
	public static String getExpressionTagpartsSurroundingRemovedFromInToOut(String sExpression, String sTagPartOpening, String sTagPartClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			if(XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening));
			if(XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing));	
					
			Vector3ZZZ<String>vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagPartOpening, sTagPartClosing, true, true);  //also an jeder Stelle (d.h. nicht mehr am Anfang), also von innen nach aussen!!!

			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
	
	public static String getExpressionTagpartsSurroundingRemovedFromInToOut(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);
						
			Vector3ZZZ<String>vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition, true);  //also ggfs. an jeder Stelle (d.h. nicht mehr am Anfang), also von innen nach aussen!!!

			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
			System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Expression per Schleife veraendert nach = '"+sReturn+"'");
			
		}//end main:
		return sReturn;
	}
	
	//####################################
	public static String getExpressionTagpartsSurroundingRemovedFromOutToIn(String sExpression, String sTagPartOpening, String sTagPartClosing) throws ExceptionZZZ {
		String sReturn = sExpression;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;			
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);
											
			Vector3ZZZ<String>vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
		
			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);	
		}//end main:
		return sReturn;
	}
	
	public static String getExpressionTagpartsSurroundingRemovedFromOutToIn(String sExpression, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition) throws ExceptionZZZ {
		String sReturn = sExpression;
		Vector3ZZZ<String>vecReturn=null;
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);
		
			if(bAnyPosition) {
				//nur vecMid reicht schon. Das würde dann noch weiteres entfernen... 			
				vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen
								
			}else {
				if(!StringZZZ.startsWithIgnoreCase(sExpression, sTagPartOpening)) break main;				
				vecReturn = StringZZZ.vecMid(sExpression, sTagPartOpening, sTagPartClosing, false);//Also ohne Tags holen												
			}
			
			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
		}//end main:
		return sReturn;
	}
	
		
	
	
	public static void getExpressionTagpartsSurroundingRemovedFromInToOut(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bRemoveAnyPosition) throws ExceptionZZZ {		
		main:{
			if(vecReturn==null)break main;			
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);

			
			//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
			String sBefore = VectorUtilZZZ.getElementAsValueOf(vecReturn, 0); //(String) vecReturn.get(0);
			String sValue = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);  //vecReturn.get(1).toString();
			String sRest = VectorUtilZZZ.getElementAsValueOf(vecReturn, 2);   //vecReturn.get(2).toString();
			
			//Merke 20241010: Stand jetzt ist es so, dass flogendes nur beim Loeschen der Tags von innen nach aussen notwendig ist
			//nicht aber beim Loeschen der Tags von aussen nach innen:			
			
			//WICHTIG: Die <Z>-Tags sind am Anfang/Ende UND es sind noch andere Formel Z-Tags "<Z:... im String vorhanden.
			//         Dann loesche sie nicht raus, auch nicht im Ergebnisstring.
			//         Will man sie loswerden, dann muessen halt die inneren Z: - Tags aufgeloest werden.
			//MERKE:   Dazu muessen ja dann auch die passenden Flags gesetzt sein. Das Setzen eines Flags ist ja vielleicht in dem Einzelfall nicht gewuenscht.
			
			boolean bSkip = false;
			if(StringZZZ.contains(sValue, "<Z:")) {
			if(StringZZZ.endsWithIgnoreCase(sBefore, "<Z>")) {
					if(!bSkip) bSkip = true;
				}
				if(StringZZZ.startsWithIgnoreCase(sRest, "</Z>")) {
					if(!bSkip) bSkip = true;
				}
			}
			if(bSkip) {
				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Weitere Formeln im String vermutet. Entferne aeusser Z-Tags nicht.");
				break main;
			}
			
			
			String sBeforeOld = sBefore;
			String sValueOld = sValue;
			String sRestOld = sRest;
			
			if(bRemoveAnyPosition) {
		while(StringZZZ.contains(sBefore, sTagPartOpening, false) & StringZZZ.contains(sRest, sTagPartClosing, false)) {
					
					if(!StringZZZ.isEmpty(sBefore)){							
						if(StringZZZ.contains(sBefore, sTagPartOpening, false)) {
							Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sBefore, sTagPartOpening, true);
							vecTemp.replace(1,"");
							sBefore = VectorUtilZZZ.implode(vecTemp);
						}
						vecReturn.replace(0, sBefore);
					}else{
						vecReturn.replace(0,"");
					}
					
					if(!StringZZZ.isEmpty(sRest)){	
					
						if(StringZZZ.contains(sRest, sTagPartClosing, false)) {
							Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sRest, sTagPartClosing, true);
							vecTemp.replace(1,"");
							sRest = VectorUtilZZZ.implode(vecTemp);
						}
						vecReturn.replace(2, sRest); //Falls vorhanden einen Restwert eintragen.
					}else{
						vecReturn.replace(2,"");
					}
					
					if(sBeforeOld.equals(sBefore) | sRestOld.equals(sRest)) break; //sonst ggfs. Endlosschleifengefahr.
					sBeforeOld=sBefore;
					sRestOld=sRest;
				}//end while
			
				//ggfs. aus dem Mittleren Teil auch entfernen
				while(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening) & StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {
		
						if(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening)) {
							sBefore = StringZZZ.right(sValue, sTagPartOpening);
							sValue = sBefore;
						}
						
						if(StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {
							sRest = StringZZZ.left(sValue, sTagPartClosing);
							sValue = sRest;
						}	
						
						if(sValueOld.equals(sValue))  break; //sonst ggfs. Endlosschleifengefahr.
						sValueOld = sValue;
				}//end while

			}else {
				while(StringZZZ.endsWithIgnoreCase(sBefore, sTagPartOpening) & StringZZZ.startsWithIgnoreCase(sRest, sTagPartClosing)) {
					
					if(!StringZZZ.isEmpty(sBefore)){									
						if(StringZZZ.endsWithIgnoreCase(sBefore, sTagPartOpening)) {
							sBefore = StringZZZ.leftback(sBefore, sTagPartOpening);
						}
						vecReturn.replace(0, sBefore);
					}else{
						vecReturn.replace(0,"");
					}
					
					if(!StringZZZ.isEmpty(sRest)){						
						if(StringZZZ.startsWithIgnoreCase(sRest, sTagPartClosing)) {
							sRest = StringZZZ.rightback(sRest, sTagPartClosing);
						}
						vecReturn.replace(2, sRest); //Falls vorhanden einen Restwert eintragen.
					}else{
						vecReturn.replace(2,"");
					}
					
					if(sBeforeOld.equals(sBefore) | sRestOld.equals(sRest)) break; //sonst ggfs. Endlosschleifengefahr.
					sBeforeOld=sBefore;
					sRestOld=sRest;
				}//end while
				
				//ggfs. aus dem Mittleren Teil auch entfernen
				while(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening) & StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {
		
						if(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening)) {
							sBefore = StringZZZ.right(sValue, sTagPartOpening);
							sValue = sBefore;
						}
						
						if(StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {
							sRest = StringZZZ.left(sValue, sTagPartClosing);
							sValue = sRest;
						}	
						
						if(sValueOld.equals(sValue))  break; //sonst ggfs. Endlosschleifengefahr.
						sValueOld = sValue;
				}//end while
		
					}//end if bRemoveAnyPosition	
			
			//ggfs. aus dem Mittleren Teil auch entfernen
			//Merke: Der Tag faengt quasi mitten im String an, darum nicht mit startWith.. endsWith..
			//Merke: Nun wird mit NullObjectZZZ gearbeitet. Aber ein toString() ignoriert null und liefert "" zurueck.
			Vector3ZZZ<String> vecMid = StringZZZ.vecMid(sValue, sTagPartOpening, sTagPartClosing, false);
			sBefore= VectorUtilZZZ.getElementAsValueOf(vecMid, 0); //vecMid.get(0).toString();
			sValue = VectorUtilZZZ.getElementAsValueOf(vecMid, 1); //vecMid.get(1).toString();
			sRest =  VectorUtilZZZ.getElementAsValueOf(vecMid, 2); //vecMid.get(2).toString();
			//sValue = sBefore + sValue + sRest;
			//Im Gesamtstring NULL rausfiltern
			sValue = StringZZZ.joinAllDefaultValueIgnoreNull(null, sBefore, sValue, sRest);
			vecReturn.replace(sValue);
			
		}//end main
	}	
	
	
		
	public static void getExpressionTagpartsSurroundingRemovedFromOutToIn(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bRemoveAnyPosition) throws ExceptionZZZ {		
		main:{
		    //Verglichen mit ...FromIntoOut gilt also:
			//Alle endsWith werden zu startWith und alle startWith werden zu endsWith.
		    //und leftbacks werden zu right und alle rightbacks zu left.
		
			if(vecReturn==null)break main;
			XmlUtilZZZ.ensureTagPartOpening(sTagPartOpening);
			XmlUtilZZZ.ensureTagPartClosing(sTagPartClosing);
			
				
			//Dann hat man auch den Fall, dass dies Bestandteil einer Formel ist. Also den Wert vorher und den Rest in den Vektor packen
			//Das Problem ist, das ein NullObjectZZZ bei toString() den Leerstring zurueckgibt. In diesem Fall soll aber null nicht durch einen Leerstring ersetzt werden. 
			String sBefore = vecReturn.getValueOrString(0);   
			String sValue = vecReturn.getValueOrString(1);  
			String sRest = vecReturn.getValueOrString(2);
			
			String sBeforeOld = sBefore;
			String sValueOld = sValue;
			String sRestOld = sRest;

			
			//Merke 20241010: Stand jetzt ist es so, dass dies nur beim Loeschen der Tags von innen nach aussen notwendig ist
			//			      nicht aber beim Loeschen der Tags von aussen nach innen.			
			//			//WICHTIG: Die <Z>-Tags sind am Anfang/Ende UND es sind noch andere Formel Z-Tags "<Z:... im String vorhanden.
			//			//         Dann loesche sie nicht raus, auch nicht im Ergebnisstring.
			//			//         Will man sie loswerden, dann muessen halt die inneren Z: - Tags aufgeloest werden.
			//			//MERKE:   Dazu muessen ja dann auch die passenden Flags gesetzt sein. Das Setzen eines Flags ist ja vielleicht in dem Einzelfall nicht gewuenscht.
			//			boolean bSkip = false;
			//			if(StringZZZ.contains(sValue, "<Z:")) {
			//				if(StringZZZ.startsWithIgnoreCase(sBefore, "<Z>")) {
			//					if(!bSkip) bSkip = true;
			//				}
			//				if(StringZZZ.endsWithIgnoreCase(sRest, "</Z>")) {
			//					if(!bSkip) bSkip = true;
			//				}
			//			}
			//			if(bSkip) {
			//				System.out.println(ReflectCodeZZZ.getMethodCurrentName()+": Weitere Formeln im String vermutet. Entferne aeusser Z-Tags nicht.");
			//				break main;
			//			}
			
			if(bRemoveAnyPosition) {
				
				while(StringZZZ.contains(sBefore, sTagPartOpening, false) & StringZZZ.contains(sRest, sTagPartClosing, false)) {
					
					if(!StringZZZ.isEmpty(sBefore)){										
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sBefore, sTagPartOpening, true);
						vecTemp.replace(1,"");
						sBefore = VectorUtilZZZ.implode(vecTemp);
						
						vecReturn.replace(0, sBefore);
					}else{
						vecReturn.replace(0,"");
					}
						
					if(!StringZZZ.isEmpty(sRest)){																		
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sRest, sTagPartClosing, true);
						vecTemp.replace(1,"");
						sRest = VectorUtilZZZ.implode(vecTemp);
						
						vecReturn.replace(2, sRest); //Falls vorhanden einen Restwert eintragen.
					}else{
						vecReturn.replace(2,"");
					}
					
					if(sBeforeOld.equals(sBefore) | sRestOld.equals(sRest)) break; //sonst ggfs. Endlosschleifengefahr.
					sBeforeOld=sBefore;
					sRestOld=sRest;
				}//end while
					
				//ggfs. aus dem Mittleren Teil auch entfernen
				while(StringZZZ.contains(sValue, sTagPartOpening, false) & StringZZZ.contains(sValue, sTagPartClosing, false)) {

					if(StringZZZ.contains(sValue, sTagPartOpening, false)) {
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sValue, sTagPartOpening, true);
						vecTemp.replace(1,"");
						sBefore = VectorUtilZZZ.implode(vecTemp);
						sValue = sBefore;
					}
						
					if(StringZZZ.contains(sValue, sTagPartClosing, false)) {						
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sValue, sTagPartClosing, true);
						vecTemp.replace(1,"");
						sRest = VectorUtilZZZ.implode(vecTemp);
						sValue = sRest;
					}		
					
					if(sValueOld.equals(sValue))  break; //sonst ggfs. Endlosschleifengefahr.
					sValueOld = sValue;
				}//end while									
				
				//##################### bAnyPosition = true 
				//### FALLS DER GANZE STRING AUF POSITION 0 STEHT ihn auch dort ersetzten				
				while(StringZZZ.contains(sBefore, sTagPartOpening, false) & StringZZZ.contains(sBefore, sTagPartClosing, false)) {
					
					if(!StringZZZ.isEmpty(sBefore)){										
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sBefore, sTagPartOpening, true);
						vecTemp.replace(1,"");
						sBefore = VectorUtilZZZ.implode(vecTemp);						
					}
						
					if(!StringZZZ.isEmpty(sBefore)){																		
						Vector3ZZZ<String> vecTemp = StringZZZ.vecMidFirst(sBefore, sTagPartClosing, true);
						vecTemp.replace(1,"");
						sBefore = VectorUtilZZZ.implode(vecTemp);
					}
					
					if(sBeforeOld.equals(sBefore)) break; //sonst ggfs. Endlosschleifengefahr.
					sBeforeOld=sBefore;		
					
					if(!StringZZZ.isEmpty(sBefore)){																						
						vecReturn.replace(0, sBefore);
					}else{
						vecReturn.replace(0,"");
					}
				}//end while
				//#####################
		}else{

			while(StringZZZ.startsWithIgnoreCase(sBefore, sTagPartOpening) & StringZZZ.endsWithIgnoreCase(sRest, sTagPartClosing)) {
			
			if(!StringZZZ.isEmpty(sBefore)){
												
				if(StringZZZ.startsWithIgnoreCase(sBefore, sTagPartOpening)) {
					sBefore = StringZZZ.right(sBefore, sTagPartOpening);
				}
				vecReturn.replace(0, sBefore);
			}else{
				vecReturn.replace(0,"");
			}
				 
			if(!StringZZZ.isEmpty(sRest)){	
				
				if(StringZZZ.endsWithIgnoreCase(sRest, sTagPartClosing)) {
					sRest = StringZZZ.left(sRest, sTagPartClosing);
				}
				vecReturn.replace(2, sRest); //Falls vorhanden einen Restwert eintragen.
			}else{
				vecReturn.add(2,"");
			}
			
			if(sBeforeOld.equals(sBefore) | sRestOld.equals(sRest)) break; //sonst ggfs. Endlosschleifengefahr.
			sBeforeOld=sBefore;
			sRestOld=sRest;
		}//end while
		
		//ggfs. aus dem Mittleren Teil auch entfernen
		while(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening) & StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {

				if(StringZZZ.startsWithIgnoreCase(sValue, sTagPartOpening)) {
					sBefore = StringZZZ.right(sValue, sTagPartOpening);
					sValue = sBefore;
				}
				
				if(StringZZZ.endsWithIgnoreCase(sValue, sTagPartClosing)) {
					sRest = StringZZZ.left(sValue, sTagPartClosing);
					sValue = sRest;
				}	
				
				if(sValueOld.equals(sValue))  break; //sonst ggfs. Endlosschleifengefahr.
				sValueOld = sValue;
		}//end while

		}//end if bRemoveAnyPosition
			
		vecReturn.replace(sValue);
		
		}//end main
	}	
	
	
	//################
	//Merke: Default ist ...FromInToOut... , ... bAnyPosition ...
	public static void getExpressionTagpartsSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, true);
		}//end main
	}
	
	public static void getExpressionTagpartsSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromIntoOut(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition);
		}//end main
	}
	
	public static void getExpressionTagpartsSurroundingRemoved(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition, boolean bDirectionFromInToOut) throws ExceptionZZZ {		
		main:{
			if(bDirectionFromInToOut) {
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition);
			}else {
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromOutToIn(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition);
			}
		}//end main
	}	
	
	
	public static void getExpressionTagpartsSurroundingRemovedFromIntoOut(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, true);
		}//end main
	}
	
	public static void getExpressionTagpartsSurroundingRemovedFromIntoOut(Vector3ZZZ<String>vecReturn, String sTagPartOpening, String sTagPartClosing, boolean bAnyPosition) throws ExceptionZZZ {		
		main:{
			KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemovedFromInToOut(vecReturn, sTagPartOpening, sTagPartClosing, bAnyPosition);
		}//end main
	}
	
	
	//##################################################################################
	//##################################################################################
	
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String computeAsExpressionReflected(String sValue) throws ExceptionZZZ {
		String sReturn = sValue;
		main:{
			if(sValue!=null) {			
				if(sValue.startsWith("<Z>") && sValue.endsWith("</Z>")) {
					//dann ist hier schon eine Expression drin
					sReturn = sValue;
					break main;
				}else {
					//Merke: Das Problem ist: Wenn im String selbst vorher schon ein Z-Tag war und nicht nur am Anfang, dann wird hier trotzdem noch etwas drumgesetzt.
					
					//Suche, ob ein Z-Tag im String enthalten ist. 
					boolean bZExistsStart = StringZZZ.contains(sValue, "<Z>");
					boolean bZExistsEnd = StringZZZ.contains(sValue, "</Z>");
					if(bZExistsStart && bZExistsEnd){
						sReturn = sValue;
						break main;
					}
				}	
			}	
			sReturn = KernelConfigSectionEntryUtilZZZ.computeAsExpression(sValue);
		}//end main:
		return sReturn;
	}
	
	public static String computeAsExpression(String sValue) throws ExceptionZZZ {
		String sReturn = sValue;
		main:{
			if(sValue==null) {
				sReturn = XmlUtilZZZ.computeTagNull();
			}else if(sValue.equals("")) {
				sReturn = XmlUtilZZZ.computeTagEmpty();
			}else {
				sReturn = XmlUtilZZZ.computeTag("Z", sValue);
			}			
		}//end main:
		return sReturn;
	}
			
}
