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

/**Merke: Mit dieser abstrakten Klasse werden Redundante Methoden aller Solver vermieden, die Solver fuer ein Child-Tag sind.
 * @author fl86kyvo
 *
 * @param <T>
 */
public abstract class AbstractKernelIniSolver4ChildTagZZZ<T> extends AbstractKernelIniSolverZZZ<T>{
	private static final long serialVersionUID = -2863273133743963380L;

	public AbstractKernelIniSolver4ChildTagZZZ() throws ExceptionZZZ{
		super("init");
	}
		
	public AbstractKernelIniSolver4ChildTagZZZ(String sFlag) throws ExceptionZZZ{
		super(sFlag);
	}
			
	public AbstractKernelIniSolver4ChildTagZZZ(String[] saFlag) throws ExceptionZZZ{
		super(saFlag);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super(objKernel);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
	}
	
	//Merke: Konstruktoren ohne ini-File machen keinen Sinn und muessen ohne "init"-Flag zu Exception fuehren
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig) throws ExceptionZZZ{
		super(objFileIniKernelConfig); //Inifile als IKernelUserZZZ				
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, saFlag);//Inifile als IKernelUserZZZ		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objFileIniKernelConfig, hmVariable);//Inifile als IKernelUserZZZ		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(FileIniZZZ<T> objFileIniKernelConfig, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objFileIniKernelConfig, hmVariable, saFlag);//Inifile als IKernelUserZZZ				
	}
	
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni) throws ExceptionZZZ{
		super(objKernel, objFileIni);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		super(objKernel, objFileIni, hmVariable);		
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, saFlag);
	}
	
	public AbstractKernelIniSolver4ChildTagZZZ(IKernelZZZ objKernel, FileIniZZZ<T> objFileIni, HashMapCaseInsensitiveZZZ<String,String> hmVariable, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, objFileIni, hmVariable, saFlag);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++
	//+++ Folgende Methoden koennen ueberschrieben werden um fuer den konkreten Solver eine Loesung einzubauen.
	//### aus ISolveZZZ
		
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
	
	//#### aus ISolveZZZZ
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
				String sTagName = this.getName();				
				if(sTagName.equalsIgnoreCase("Z")) {
					//dann mache nix... der Tag wird anders behandelt...
				}else {
					String sTagStart = XmlUtilZZZ.computeTagPartOpening(sTagName);
					String sTagEnd = XmlUtilZZZ.computeTagPartClosing(sTagName);  					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
					
					sTagName = this.getParentName();
					sTagStart = XmlUtilZZZ.computeTagPartOpening(sTagName);
					sTagEnd = XmlUtilZZZ.computeTagPartClosing(sTagName);  					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
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
	
	
	//### ALS VECTOR 
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, null, bRemoveSurroundingSeparators);
	}
	
	@Override
	public Vector3ZZZ<String> solveParsedWrapup(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return solveParsedWrapup_(vecExpression, objReturnReferenceIn, bRemoveSurroundingSeparators);
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
				String sTagName = this.getName();				
				if(sTagName.equalsIgnoreCase("Z")) {
					//dann mache nix... der Tag wird anders behandelt...
				}else {
					String sTagStart = XmlUtilZZZ.computeTagPartOpening(sTagName);
					String sTagEnd = XmlUtilZZZ.computeTagPartClosing(sTagName);  					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
					
					sTagName = this.getParentName();
					sTagStart = XmlUtilZZZ.computeTagPartOpening(sTagName);
					sTagEnd = XmlUtilZZZ.computeTagPartClosing(sTagName);  					
					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecExpressionIn, sTagStart,sTagEnd, true, false); //also AN JDEDER POSITION (d.h. nicht nur am Anfang) von aussen nach innen!!!
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
	
}//End class