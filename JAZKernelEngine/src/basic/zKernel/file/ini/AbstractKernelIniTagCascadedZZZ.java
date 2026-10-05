package basic.zKernel.file.ini;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.abstractList.VectorZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/*
 * In dieser Klasse wird das Ende Tag "von hinten" gesucht.
 * D.h. auch verschachtelte Tags funktionieren <Z> ... ... <Z> .. </Z> ... ... </Z>
 */
public abstract class AbstractKernelIniTagCascadedZZZ<T> extends AbstractKernelIniTagSimpleZZZ<T> implements IIniTagCascadedZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	public static String sTAG_PARENT_NAME = null; //Merke: Statische Variablen werden durch Vererbung aber nicht ueberschrieben.
	
	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagParentName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
			
	public AbstractKernelIniTagCascadedZZZ() throws ExceptionZZZ {
		super("init");
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);		
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(objKernel, saFlag);
		AbstractKernelIniTagCascadedNew_();
	}

	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(objKernelUsing);
		AbstractKernelIniTagCascadedNew_();
	}
	
	public AbstractKernelIniTagCascadedZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(objKernelUsing,saFlag);
		AbstractKernelIniTagCascadedNew_();
	}
	
	
	/** Wg. Interface IKernelUserZZZ notwendige Redundanz zu 
	 *  AbstractKernelUseObjectZZZ
	 *  und dem dortigen Konstruktor
	 *  
	 * @param objKernel
	 * @param objKernelUsing
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:10:15
	 */
	private boolean AbstractKernelIniTagCascadedNew_() throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {
			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}
	
	//#####################################################
	
	//### Analog zu AbstractTagBasicZZZ
	//### aus ITagBasicChildZZZ
	//Merke: Der Name wird auf unterschiedliche Arten geholt. Z.B. aus dem TagTypeZZZ, darum diese Methode dann ueberschreiben.
	@Override
	public String getParentName() throws ExceptionZZZ{
		if(this.sTagParentName==null) {
			return this.getParentNameDefault();
		}else {
			return this.sTagParentName;
		}
	}	
	
	//### Aus ITagBasicChildZZZ
	@Override
	public String getParentNameDefault() throws ExceptionZZZ{
        	return ExpressionIniUtilZZZ.getTagParentNameDefault(this);           
    }	
	
	
	@Override
	public void setParentName(String sTagParentName) throws ExceptionZZZ{
		if(StringZZZ.isEmptyTrimmed(sTagParentName)) {
			ExceptionZZZ ez = new ExceptionZZZ( "Missing TagParentName.", iERROR_PARAMETER_MISSING, AbstractIniTagSimpleZZZ.class, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;
		}
		this.sTagParentName = sTagParentName;		
	}
	
	
	//### aus IParseEnabledZZZ
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	//### Aus IKernelEntryExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	/**Methode wird z.B. vom AbstractKernelIniSolver ueberschrieben **/
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ {
		Vector3ZZZ<String>vecReturn = new Vector3ZZZ<String>();
		String sReturn = null; String sReturnSubstituted = null; String sReturnTag = null; String sReturnLine = null;
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		
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
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}		
		
		this.updateValueParseCalled();
		this.updateValueParseCalled(objReturnReference);		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			this.setRaw(sExpressionIn);		
			objEntry.setRaw(sExpressionIn);
		
			sReturnTag = this.getValue();
			sReturnLine = sExpressionIn;
			vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
			sReturn = sReturnLine;

			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			//Direkte nachdem feststeht, dass Expression und Parser generell behandelt werden die Tags analysieren!!!
			this.updateValueParseCustom(objReturnReference, sExpression); 
						
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben			
			bUseParserThis = this.isParserEnabledCustom();//.isParserEnabledThis();
		    if(!bUseParserThis) break main;
			
		    
		    //###########################################
			//### 
			//###########################################
		    parse:{
			    //Zerlegen des Z-Tags, d.h. Teil vorher, Teil dahinter.
			    //Wichtig hier die Z-Tags in der MITTE des Vector3 drin lassen, nur dann funktioniert die RegEx-Expression für Pfadangabe.
				//!!! Unterschied zum AbstractKernelIniTagSimpleZZ	    
				String sTagOpening = this.getTagPartOpening();
				String sTagClosing = this.getTagPartClosing();
				vecReturn = XmlUtilZZZ.parseFirstVectorCascadedTag(sExpression, sTagOpening, sTagClosing, bKeepSurroundingSeparatorsOnParse, bIgnoreCase);//StringZZZ.vecMidKeepSeparator(sExpression, sTagOpening, sTagClosing, !bIgnoreCase);
				if (vecReturn==null)break parse;		
				
				//Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
				//Also werden nur Werte in den jeweiligen Tags substituiert durch ihren konkreten Solver!!!
				if(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsValueOf(vecReturn, 1))) break parse; 
				
				//+++++++++++++++++++++++++
				//20241023 Erweiterungsarbeiten, Ini-Pfade und Variablen "substituieren"
				//String sValueToSubstitute = vecReturn.get(1).toString();  //Merke: Das ist dann der Wert es Tags, wenn der Parser nicht aktiviert ist. Wenn der Tag nicht im String ist, ist das korrekterweise auch ein Leerstring.
				String sValueToSubstitute = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1); //Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.	
				this.setValue(sValueToSubstitute);
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSubstitute= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSubstitute.set(objEntry);
				sReturnSubstituted = this.substituteParsed(sValueToSubstitute, objReturnReferenceSubstitute, bKeepSurroundingSeparatorsOnParse);				
				objEntry = objReturnReferenceSubstitute.get();						
				vecReturn.replace(1,sReturnSubstituted);
				
				//Falls Substitution durchgeführt wurde noch einmal den String durchsuchen, nach Tags.
				//und ggfs. wieder (neu gefundene) Value-Eintraege setzen
				sReturnLine  = VectorUtilZZZ.implode(vecReturn);
				if(objEntry.isSubstitutedChanged()) {				
					this.updateValueParseCustom(objReturnReference, sReturnLine);			
				}			
				//+++++++++++++++++++++++++
				
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//vecReturn.get(1).toString();
				this.setValue(sReturnTag);
				
				sReturnLine = VectorUtilZZZ.implode(vecReturn);
				
				//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
				//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParserSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceParserSuper.set(objEntry);
				vecReturn = this.parseFirstVectorPost(vecReturn, objReturnReference, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = this.getValue();
				sReturnLine  = VectorUtilZZZ.implode(vecReturn);
		    }//end parse:
			this.updateValueParsed();
			this.updateValueParsed(objReturnReference);			
		}//end main:			
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen		//
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null)objReturnReference.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
			if(bUseExpression & bUseParser & bUseParserThis) {
				if(sExpressionIn!=null) {							
					String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					if(!sExpression2Compare.equals(sReturnLine2Compare)) {
						this.updateValueParsedChanged();
						this.updateValueParsedChanged(objReturnReference);
					}
				}		
			}
			this.adoptEntryValuesMissing(objEntry);						
		}
		return vecReturn;
	}
	
	//### aus IParseUserZZZ

	
	//### aus IExpressionUserZZZ	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	@Override
//	public Vector3ZZZ<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String> vecReturn = null;
//
//		main:{
//			if(sExpression==null) break main;
//			
//			vecReturn = new Vector3ZZZ<String>();
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//								
//			//Bei dem cascaded Tag wird das schliessende Tag vom Ende gesucht...
//			vecReturn = StringZZZ.vecMid(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
//				
//		}//end main:			
//		return vecReturn;
//	}
}// End class