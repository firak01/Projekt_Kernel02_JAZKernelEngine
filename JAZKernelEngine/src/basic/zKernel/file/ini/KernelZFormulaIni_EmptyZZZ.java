package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConvertEnabledZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.ini.IIniStructureConstantZZZ;
import basic.zBasic.util.xml.tagsimple.AbstractTagParseEnabledZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

//Klasse verwendet keinen Kernel, keine Expression Flags also nur ein einfacher Tag 
//Sie wird einfach nur vom KernelZFormulaIniConvertZZZ aus aufgerufen, wenn dieser die passenden Flags hat.
public class KernelZFormulaIni_EmptyZZZ<T> extends AbstractIniTagSimpleZZZ<T> implements IConvertEnabledZZZ{	
	private static final long serialVersionUID = 7203160369729097L;
	public static final String sTAG_NAME = "z:Empty";
	
	//Hier die Variante ohne ini-File
	public KernelZFormulaIni_EmptyZZZ() throws ExceptionZZZ{		
		super();		
		KernelExpressionIniEmptyNew_();
	}
			
	private boolean KernelExpressionIniEmptyNew_() throws ExceptionZZZ {
//	 boolean bReturn = false; 
//	 main:{			 														
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}				
//			
//				
//	 	}//end main:
		return true;
	 }//end function KernelExpressionMathSolverNew_
			
	//### Aus IParseEnabledZZZ	
		
	//### Aus IIniTagBasicZZZ
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ{
		return this.parseFirstVector(sExpression, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpressionIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		main:{
			//Nein, der Konverter nutzt das Flag auch nicht if(!this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
			vecReturn = StringZZZ.vecMidFirst(sExpressionIn, this.getTagPartOpening(), this.getTagPartClosing(), false, false);
			if(vecReturn==null) break main;			
			
			parse:{
				//Hier explizit leer setzen
				
				//nun den mittleren Teil weiter verarbeiten, sprich leersetzen
				vecReturn.replace("");
				
				this.setValue(vecReturn.get(1).toString());
			}//end parse:
		}//end main:
		return vecReturn;
	}
	
	//### aus IConvertableZZZ
	@Override
	public String convert(String sLineWithoutExpression) throws ExceptionZZZ{
		String sReturn = sLineWithoutExpression;
		main:{
			if(!this.isConvertRelevant(sLineWithoutExpression)) break main;
			
			//Hier einfach den Leeren-Tag zurückgeben. Merke: Convert != Parse, convert bezieht sich auf den ganzen String. 
			sReturn = "";
						
		}//end main
		return sReturn;
	}

//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return KernelZFormulaIni_EmptyZZZ.sTAG_NAME;
//	}
}//End class