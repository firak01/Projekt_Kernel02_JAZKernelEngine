package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import basic.zBasic.util.xml.tagsimple.IParseEnabledZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

/**Grundliegende Idee dahinter stammt aus den Converter-Klassen in JSF. 
 * 
 * 20190123
 * @author Fritz Lindhauer
 * @param <T>
 *
 */
public class KernelZFormulaIniConverterZZZ<T> extends AbstractKernelUseObjectZZZ<T> implements IIniTagWithConversionZZZ{
	private static final long serialVersionUID = -8982048164029456581L;
	private FileIniZZZ objFileIni=null;
	
	//###########################################
	public KernelZFormulaIniConverterZZZ() throws ExceptionZZZ{
		super("init");
		KernelExpressionIniConverterNew_(null);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni) throws ExceptionZZZ{
		super(objFileIni.getKernelObject());
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	public KernelZFormulaIniConverterZZZ(FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objFileIni.getKernelObject(),saFlag);
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	public KernelZFormulaIniConverterZZZ(IKernelZZZ objKernel, FileIniZZZ objFileIni, String[] saFlag) throws ExceptionZZZ{
		super(objKernel,saFlag);
		KernelExpressionIniConverterNew_(objFileIni);
	}
	
	private boolean KernelExpressionIniConverterNew_(FileIniZZZ objFileIn) throws ExceptionZZZ {
		 boolean bReturn = false;
		 main:{
			 
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
				
				if(objFileIn==null ){
					ExceptionZZZ ez = new ExceptionZZZ("FileIni-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez; 
				}else{
					this.setFileIni(objFileIn);							
				}										
		 	}//end main:
			return bReturn;
		 }//end function KernelExpressionIniConverterNew_
		
	//### GETTER / SETTER ########################
	public void setFileIni(FileIniZZZ objFileIni){
		this.objFileIni = objFileIni;
	}
	public FileIniZZZ getFileIni(){
		return this.objFileIni;
	}
	
	//############################################
	public static IConvertEnabledZZZ getAsObject(String sToConvert)throws ExceptionZZZ{
		IConvertEnabledZZZ objReturn = null;
	main:{
			
		//Erstelle nun alle möglichen Klassen und prüfe, ob sie mit dem String etwas anfangen können.
		IConvertEnabledZZZ objTemp = new KernelZFormulaIni_EmptyZZZ(); //Klasse ohne Kernel oder Expression Flags
		if(objTemp.isConvertRelevant(sToConvert)){
			objReturn = objTemp;
			break main;
		}
		
		objTemp = new ZTagFormulaIni_NullZZZ();
		if(objTemp.isConvertRelevant(sToConvert)){
			objReturn = objTemp;
			break main;
		}
		
	}
		return objReturn;
	}
	
	public static String getAsString(IConvertEnabledZZZ objExpression, String sExpression) throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			if(objExpression==null) break main;
			
			sReturn = objExpression.convert(sExpression);
		}
		return sReturn;
	}
	
	public String getAsString(String sExpression)throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			if(!this.getFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION))break main;
			
			sReturn = KernelZFormulaIniConverterZZZ.getAsStringStatic(sExpression);
		}//end main:
		return sReturn;		
	}
	
	public static String getAsStringStatic(String sExpression)throws ExceptionZZZ{
		String sReturn = sExpression;
		main:{
			
			IConvertEnabledZZZ objExpression = new KernelZFormulaIni_EmptyZZZ();//Klasse ohne Kernel oder Expression Flags
			if((objExpression).isConvertRelevant(sExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsString(objExpression, sExpression);
				break main;
			}
			
			//Wie soll das gehen... nach der Umstrukturieren hat die Klasse nun doch KErnel, FileIni und Expression Flags
			objExpression = new ZTagFormulaIni_NullZZZ(); //Klasse ohne Kernel oder Expression Flags
			if(objExpression.isConvertRelevant(sExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsString(objExpression, sExpression);
				break main;
			}						
		}
		return sReturn;
	}
	
	
	public String getAsExpression(String sLineWithExpression)throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(!this.getFlag(IIniTagWithConversionZZZ.FLAGZ.USECONVERSION))break main;
			
			sReturn = KernelZFormulaIniConverterZZZ.getAsExpressionStatic(sLineWithExpression);
		}//end main:
		return sReturn;		
	}
	
	public static String getAsExpressionStatic(String sLineWithExpression)throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			//Erstelle nun alle möglichen KernelExpressionIni-Klassen und prüfe, ob sie mit dem Ausdruck etwas anfangen können.			
			IConvertEnabledZZZ objExpressionEmpty = new KernelZFormulaIni_EmptyZZZ();
			if(objExpressionEmpty.isConvertRelevant(sLineWithExpression)){	
				
				sReturn = KernelZFormulaIniConverterZZZ.getAsExpression(objExpressionEmpty, sLineWithExpression);
				break main;
			}
			
			IConvertEnabledZZZ objExpressionNull = new ZTagFormulaIni_NullZZZ();
			if(objExpressionNull.isConvertRelevant(sLineWithExpression)){				
				sReturn = KernelZFormulaIniConverterZZZ.getAsExpression(objExpressionNull, sLineWithExpression);
				break main;
			}
			
			
		}
		return sReturn;
	}

	public static String getAsExpression(IConvertEnabledZZZ objExpression, String sLineWithExpression) throws ExceptionZZZ{
		String sReturn = sLineWithExpression;
		main:{
			if(objExpression==null) break main;
			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
			
			sReturn = objExpression.convert(sLineWithExpression);
			if(!sLineWithExpression.equals(sReturn)){
				if(sReturn==null) {
					sReturn="<Z/>";
				}else if(sReturn.equals("")) {
					sReturn="<Z/>";
				}else {
					sReturn = "<Z>" + sReturn + "</Z>";
				}
			}
		}
		return sReturn;
	}

	//###################################
	//### FLAG Handling
	
	//### aus IIniTagWithConversionZZZ	
	@Override
	public boolean getFlag(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IIniTagWithConversionZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IIniTagWithConversionZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IIniTagWithConversionZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IIniTagWithConversionZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
