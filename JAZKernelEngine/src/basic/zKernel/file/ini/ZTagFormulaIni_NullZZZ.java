package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/** Warum diese Klasse existiert:
 *  Beispielsweise NULL-Wert in der Ini-Datei-Konfiguarition bedeutet "Eclipse Projekteordner".
 *  Man könnte den Eintrag auch weglassen, aber dann verliert man:
 *  a) Die Möglichkeit, dass ein "Unter"-System den Pfadeintrag der Hauptkonfigurationsdatei überschreiben kann.
 *  b) Den Performancegewinn, der dadurch erzielt wird, dass bei der Suche nach dem konfigurierten Parameter 
 *     auch ein NULL - Wert gefunden wird (halt der <z:Null/> Tag). Wenn etwas gefunden wird, dann wird auch diese Parametersuche beendet.   
 * @param <T>
 */
public class ZTagFormulaIni_NullZZZ<T>  extends AbstractKernelIniTagSimpleZZZ<T> implements IKernelZFormulaIniZZZ{
	private static final long serialVersionUID = -3773890882498236252L;
	public static final String sTAG_NAME = "z:Null"; 
		
	public ZTagFormulaIni_NullZZZ() throws ExceptionZZZ{	
		super("init");
		KernelExpressionIniNullNew_(null);
	}
	
	public ZTagFormulaIni_NullZZZ(String sFlagControl) throws ExceptionZZZ{	
		super(sFlagControl);
		KernelExpressionIniNullNew_(null);
	}
	
	public ZTagFormulaIni_NullZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject());
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(FileIniZZZ objFileIni,String[] saFlag) throws ExceptionZZZ{		
		super(objFileIni.getKernelObject(), saFlag);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objKernel);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	public ZTagFormulaIni_NullZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel, saFlag);
		KernelExpressionIniNullNew_(objFileIni);
	}
	
	
	private boolean KernelExpressionIniNullNew_(FileIniZZZ objFileIni) throws ExceptionZZZ {
	 boolean bReturn = false; 
	 main:{			 																
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}										
				
			this.setFileConfigKernelIni(objFileIni);
	 	}//end main:
		return bReturn;
	 }//end function KernelExpressionMathSolverNew_
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpression) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{
			if(StringZZZ.isEmpty(sExpression)) break main;
			
			//Nun die Section suchen
			Vector3ZZZ<String> vecSection = super.parseFirstVector(sExpression);	
								
			String sSection = (String) vecSection.get(1);
			String sProperty = (String) vecSection.get(2);
			String sBefore = "";
			String sRest = "";
						
			if(!(StringZZZ.isEmpty(sSection) || StringZZZ.isEmpty(sProperty))){

					//Falls noch ein Value-Tag im Rest ist, diesen daraus rechnen!!!
					String sMathValueTag = XmlUtilZZZ.computeTagPartClosing(ZTagFormulaMath_ValueZZZ.sTAG_NAME);
					if(StringZZZ.contains(sProperty, sMathValueTag)){
						sBefore = (String) vecSection.get(0);
						sRest = sMathValueTag + StringZZZ.rightback(sProperty, sMathValueTag);
						sProperty = StringZZZ.left(sProperty, sMathValueTag);												
					}
										
					FileIniZZZ objFileIni = this.getFileConfigKernelIni();
					if(objFileIni==null){
						ExceptionZZZ ez = new ExceptionZZZ("FileIni", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
					
					//20080109 FGL: Falls es eine Section gibt, so muss die Auflösung der Section über eine Suche über die Systemnummer erfolgen
					String sSystemNr = this.getKernelObject().getSystemNumber();
					String sValue =  objFileIni.getPropertyValueSystemNrSearched(sSection, sProperty, sSystemNr).getValue();
					
					//Den Wert ersetzen, aber nur, wenn es auch etwas zu ersetzen gibt.
					if(sValue!=null){						
						vecReturn.replace(sBefore, sValue, sRest);
					}//end if sValue!=null
									
			}//end if isempty(sSection)
			
		}//end main:
		return vecReturn;
	}
		
	//### Aus Interface IConvertable
	@Override
	public String convert(String sLineWithoutExpression) throws ExceptionZZZ{
		String sReturn = sLineWithoutExpression;
		main:{
			if(!this.isConvertRelevant(sLineWithoutExpression)) break main;
			
			//Hier einfach den Leeren-Tag zurückgeben			
			sReturn = null; //this.getTagEmpty();
						
		}//end main
		return sReturn;
	}
	


	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return ZTagFormulaIni_NullZZZ.sTAG_NAME;
//	}

	//### Aus IParseEnabledZZZ	
	@Override 
	public boolean isParserEnabledCustom() throws ExceptionZZZ {		
		//Ziel ist, dass Solver, die Kinder/Eltern-Tags haben auch deren Flags abrufen koennen.
		boolean bReturn = false;
		main:{
			//boolean bUseFormula = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA);
			//boolean bUseFormulaMath = this.getFlag(IKernelZFormulaIniZZZ.FLAGZ.USEFORMULA_MATH);
			boolean bEnabledThis = this.isParserEnabledThis();
					
			bReturn = bEnabledThis; // && bUseFormulaMath && bUseFormula;
		}
		return bReturn; 	
	}
	
	
	//### Aus IParseUserZZZ
	@Override
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, String sExpressionIn) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}

	//### Aus IKernelEntryExpressionUserZZZ
			
	//##############################################################
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
}//End class