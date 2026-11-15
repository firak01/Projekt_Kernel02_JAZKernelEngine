package basic.zKernel.file.ini;

import java.util.Vector;

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
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Merke: Der Path-Tag besteht aus [ oder ] ist damit kein normaler Tag 
 *         und kann also nicht aus AbstractIniTagSimple oder AbstractIniCascadedZZZ erben.
 *         
 *  Merke2: Stand jetzt 20250225 braucht man aber irgendeinen umgebenden Tag, der beim Parsen einen Wert bekommt. 
 *          Dieser Wert ist dann die Grundlage für das Auflösen des Pfads.
 *          
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 12.07.2024, 09:26:56 
 */
public class KernelZFormulaIni_PathZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelFileIniUserZZZ, IKernelExpressionIniSolverZZZ, IKernelZFormulaIni_PathTagZZZ{
	private static final long serialVersionUID = -6403139308573148654L;
	
	//Hier kein Tag-Name sondern eine RegEx. Der Tag ist definiert als [section]property
	//Merke: In RegEx Ausdruechen muessen eckige Klammern mit Backslash escaped werden. 
	//public static String sTAG_NAME = ".*<Z>.*[\\[]*[\\]].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text
	//public static String sTAG_NAME = ".*<Z>.*\\[[^\"].*[^\"]\\].*</Z>.*"; //finde einen Ausdruck in eckigen Klammern mit Z-Tags drumherum und ggfs. Text UND auf keine Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist); dito fuer die geschlossenen eckige Klammer
	//public static String sTAG_NAME = ".*<[^/].*>.*\\[[^\"].*[^\"]\\].*<[/].*>.*"; //finde einen Ausdruck in eckigen Klammern mit Tags (erstes Tag ohne Slash) drumherum und ggfs. Text UND auf keine Fall nach der offenen eckigen Klammer ein Hochkomma (was fuer Java escaped ist); dito fuer die geschlossenen eckige Klammer
	public static final String sTAG_NAME = ".*\\{\\[.*[^\"']\\].*\\}.*"; //finde einen Ausdruck in geschweiften Klammern drumherum PLUS finde einen Ausdruck in eckigen Klammern und ggfs. Text, aber OHNE Hochkommata (was hier im Ausdruck fuer Java escaped ist), einfaches Hochkommata etc.
	
	public static String sSECTION_OPENING = "[";
	public static String sSECTION_CLOSING = "]";
	public static String sTAGPART_OPENING = "{" + sSECTION_OPENING;	
	public static String sTAGPART_CLOSING = "}";//Merke: Hinter der Section kommt noch der Property-Text, der hat keine Trenner.
	
	
	public KernelZFormulaIni_PathZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniPathNew_(null);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	public KernelZFormulaIni_PathZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelExpressionIniPathNew_(objFileIni);
	}
	
	
	private boolean KernelExpressionIniPathNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false;
	 main:{			 															
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}										
					
			this.setFileConfigKernelIni(objFileIni);
			
			bReturn = true;
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	

	//### aus IExpressionUserZZZ
	@Override
	public boolean isExpression(String sLine) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			//Merke: Contains reicht nicht. Die Positionen sind auch wichtig.			
			//boolean btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagStarting(), false);
			//if(btemp==false) break main;
		
			//btemp = StringZZZ.contains(sLine, KernelZFormulaIni_PathZZZ.getExpressionTagClosing(), false);
			//if(btemp==false) break main;
			
			//!!! Wichtig: nur auf [ ] abzupruefen reicht nicht. Das koennte auch ein Array sein.
			//Also auf echtes TAG pruefen
			boolean bAsTagFound = StringZZZ.containsAsTag(sLine, this.getTagPartOpening(), this.getTagPartClosing(), false);
			if(!bAsTagFound) break main;
				
			bReturn = true;
		}//end main
		return bReturn;
	}
	
	
	//### Aus IParseEnabledZZZ		
	@Override
	public boolean isParseRelevant(String sExpressionToProof) throws ExceptionZZZ {
		//Ueberschreibt den Standard, da der Name nicht so ist wie ein normales Tag
		return ExpressionIniUtilZZZ.isExpressionRegEx(sExpressionToProof, this.getName(), false);
	}
		
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, null, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	//### aus IKernelEntryExpressionUserZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = null; String sReturnTag = null; String sReturnLine = null; String sReturnSubstituted = null; 
		boolean bExpressionFound = false; boolean bCascadedExpressionFound = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		//20240919: Dummy debug mit diesen statischen Werten
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>[ArgumentSection for testCallComputed]JavaMethod</Z></Z:Method></Z:Java></Z:Call>";			
		//sExpression = "<Z:Call><Z:Java><Z:Class><Z>irgendwas</Z></Z:Class><Z:Method><Z>nochnemethod</Z></Z:Method></Z:Java></Z:Call>";					
				
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
			objEntry = new KernelConfigSectionEntryZZZ<T>(this); //Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objReturnReference.set(objEntry);
		}
		this.updateValueParseCalled();
		this.updateValueParseCalled(objReturnReference);
		
		sReturnLine = sExpressionIn;
		this.setRaw(sReturnLine);
		objEntry.setRaw(sReturnLine);	
		sReturn = sReturnLine;
		
		sReturnTag = this.getValue();//???? sollte das nicht auch sReturnLine sein???
		
		vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;			
			String sExpression = sExpressionIn;
			
			boolean bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
			
			boolean bUseExpressionPath = this.isSubstitutePathEnabledThis();
			if(!bUseExpressionPath) break main;

			bExpressionFound = this.isExpression(sExpressionIn); 
			if(!bExpressionFound)break main;
			
			//++++++++++++++++++++++++++++++++++++++
			boolean bReturnSeparators = bKeepSurroundingSeparatorsOnParse;
			
			//Die PATH Anweisung soll zwischen jedem Tag liegen koennen oder auch einfach so darstehen
			String sTagXPathStarting = this.getTagPartOpening();
			int iTagXPathStartingIndex = sExpression.indexOf(sTagXPathStarting)-1;
			
			String sSepLeft = XmlUtilZZZ.findFirstTagPartPreviousTo(sExpression, sTagXPathStarting);
			String sSepRight = XmlUtilZZZ.findFirstTagPartNextTo(sExpression, sTagXPathStarting);
		
			String sTagValueTotal=null;
			if(StringZZZ.isEmpty(sSepLeft)){
				sTagValueTotal = VectorUtilZZZ.getElementAsValueOf(vecReturn, 0);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				bCascadedExpressionFound = false;
			}else { 
				/*DAS IST FALSCH, er holt sonst nach der ersten erfolgreichen Ersetzung wieder den gleichen, z.B.:  Z:VAL 
				   <Z:formula><z:Math><Z:VAL>4.0</Z:val><Z:oP>*</Z:op><Z:val>{[Section for testComputeMathArguments FLOAT]WertB_float}</Z:val></Z:math></Z:formula> */
				/*
				vecReturn = StringZZZ.vecMidFirstKeep(sExpression, sSepLeft, sSepRight, false);
				sTagValueTotal = vecReturn.get(1).toString();
				bCascadedExpressionFound = true;
				*/
				
				vecReturn = StringZZZ.vecMidFirstKeep(sExpression, sSepLeft, sSepRight, false, iTagXPathStartingIndex);
				sTagValueTotal = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				bCascadedExpressionFound = true;
			}
			
			//Loesungsansatz: Arbeite mit einer nicht weiter verwendeten Kopie des Ini-Objekts		
			FileIniZZZ<T> objFileIni = this.getFileConfigKernelIni();
			if(objFileIni==null){
				ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//+++ ACHTUNG: Mit dem einfache Clonen kopiert man die internen Variablen, ohne die Referenzen zu aendern.
			//             Mit clonez() werden eine echte Kopien der HashMaps erstellt.
			//             Das bewirkt, das sich Aenderungen an den Flags NICHT auch an das Ursprungsobjekt uebertragen!!!
			FileIniZZZ<T> objFileIniUsed = (FileIniZZZ<T>) objFileIni.clonez();
			
			//+++ Wichtig: Diese geclonte objFileIni nur fuer eine direkte Suche nutzen.
			//    Daher unbedingt darin die Flags so setzen, dass keine Aufloesung von Expressions passiert.			
			objFileIniUsed.setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE,false);
			objFileIniUsed.setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH,false);
			objFileIniUsed.setFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER,false);			
			
			//+++ Wichtig: Dieses geclonte objFileIni darf auch nicht Statuswerte frueherer Verarbeitungen im Entry-Objekt haben.
			objFileIniUsed.setEntry(null); //Durch das Null-Setzen wird ein neues Erstellen erzwungen.
			
			
			//20080109: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
			//20230316: Aber, jetzt ist es allgemeingültiger nicht eine konkrete SystemNumber vorzugeben. Darum null dafür.
			//          Dann werden alle Sections durchsucht
			
			//Merke: Es gibt: KernelZZZ.computeSectionFromSystemSection(sSystemSection)
			//                Damit wird eine Section aus abcsection!01 geholt. 
			Vector3ZZZ<String> vecTagValueTotal = StringZZZ.vecMidFirstKeepSeparatorCentral(sTagValueTotal, this.getTagPartOpening(), this.getTagPartClosing(), false);
			
			String sTagValue = VectorUtilZZZ.getElementAsValueOf(vecTagValueTotal, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.//(String) vecTagValueTotal.get(1);
			String sSection = StringZZZ.midLeftRightback(sTagValue, this.getTagPartOpening(), this.getSectionClosing());
			String sProperty = StringZZZ.midLeftRightback(sTagValue, this.getSectionClosing(), this.getTagPartClosing());
			
			//Wenn keine Section oder keine Property gefunden wurde, nicht danach suchen. Das wuerde berechtigterweise eine Exception werfen.
			if(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty)) {
				sReturnSubstituted = sTagValueTotal; //also unveraendert lassen.
				
			}else {			
				//Zu einfach, was tun wenn kein Wert gefunden wird... einfach so belassen			
				IKernelConfigSectionEntryZZZ objEntrySearch = objFileIniUsed.getPropertyValueSystemNrSearched(sSection, sProperty, null);
				if(objEntrySearch.hasAnyValue()) {
					sReturnSubstituted = objEntrySearch.getValue();
					vecTagValueTotal.replace(sReturnSubstituted);
				}else {
					sReturnSubstituted = sTagValueTotal; //also unveraendert lassen.
				}
			}
									
			sReturnTag = VectorUtilZZZ.implode(vecTagValueTotal);
			this.setValue(sReturnTag);	
			if(bCascadedExpressionFound) {
				vecReturn.replace(1,sReturnTag); //Uebernimm den ersetzten Wert mit ggfs. vorhandenen Zeichen drumherum in die Rückgabe
			}else {
				vecReturn.replace(0,"");
				vecReturn.replace(1,sReturnTag); //Wenn kein Tag drumherum, uebernimm den Wert an Stelle 1 UND setze drumherum alles leer
				vecReturn.replace(2,"");
			}
			//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
			//Als echten Ergebniswert aber die <Z>-Tags rausrechnen, falls gewuenscht. BESONDERHEIT: Hier nicht versuchen den einenen Path-Tag rauszurechnen [...]
			vecReturn = this.parseFirstVectorPost(vecReturn, bKeepSurroundingSeparatorsOnParse, false);
			sReturnTag = this.getValue();			
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
			
			this.updateValueParsed();
			this.updateValueParsed(objReturnReference);
		}//end main:		
		 
		//Aus AbstractKernelIniTagSimpleZZZ
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null)objReturnReference.set(objEntry);
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
		return vecReturn;
	}
	
	
	//###### Getter / Setter
	
	//### aus IKernelZFormulaIni_PathTagZZZ
	//Merke: Static Ausdruecke koennen erst ab Java 8 in ein Interface
	@Override
	public String getSectionOpening() {
		return "[";
	}
	
	@Override
	public String getSectionClosing() {
		return "]";
	}
	
	//#### aus IBasicTagSimpleZZZ
	//Merke: Static Ausdruecke koennen erst ab Java 8 in ein Interface		
	@Override
	public String getTagPartOpening() throws ExceptionZZZ{
		return KernelZFormulaIni_PathZZZ.sTAGPART_OPENING;
	}
	
	@Override
	public String getTagPartClosing() throws ExceptionZZZ{
		return KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING; 
	}
	
	@Override
	public String getTagEmpty()throws ExceptionZZZ{
		//Merke: Das ist nicht nur ein Zeichen, sondern gilt wirklich als ganzes Tag
		return KernelZFormulaIni_PathZZZ.sTAGPART_OPENING + KernelZFormulaIni_PathZZZ.sSECTION_CLOSING + "/" + KernelZFormulaIni_PathZZZ.sTAGPART_CLOSING;
	}
		

	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return sTAG_NAME;
//	}

	//### Aus IConvertable
	@Override
	public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {
		return false;
	}
	

	//### Aus IParseEnabledZZZ
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {		
		//Ziel ist, dass Solver, die Kinder/Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			//boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			//boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);
			boolean bEnabledThis = this.isParserEnabledThis();
					
			bReturn = bEnabledThis;// && bUseFormulaMath && bUseFormula;
		}
		return bReturn; 	
	}
	
	//### Aus IParseUserZZZ
	@Override
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, String sExpressionIn) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	
	//########################################################
	//### FLAG Handling
	
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
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objaEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objaEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	//### aus IKernelExpressionIniSolverZZZ
	@Override
	public boolean getFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelZFormulaIni_PathZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelExpressionIniSolverZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_PathZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_PathZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_PathZZZ.length];
				int iCounter=-1;
				for(IKernelExpressionIniSolverZZZ.FLAGZ objEnum_IKernelZFormulaIni_PathZZZ:objaEnum_IKernelZFormulaIni_PathZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_PathZZZ, bFlagValue);
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
}//End class