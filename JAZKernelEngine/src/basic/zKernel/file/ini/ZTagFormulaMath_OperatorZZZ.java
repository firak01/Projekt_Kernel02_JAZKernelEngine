package basic.zKernel.file.ini;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Das ist eine Vereinfachung.
 * Beim Parsen wird hier nicht nur der Operator Wert geholt, 
 * sondern auch das Ausrechnen gemacht.
 * 
 * @author fl86kyvo
 *
 * @param <T>
 */
public class ZTagFormulaMath_OperatorZZZ<T>  extends AbstractIniTagSimpleZZZ<T>{
	private static final long serialVersionUID = 3096748072633354679L;
	public static final String sTAG_NAME = "Z:op";
	private String sOperator = null;
	private String sOperand01 = null;
	private String sOperand02 = null;
		
	public ZTagFormulaMath_OperatorZZZ() throws ExceptionZZZ{
		super();
		ZTagFormulaMath_OperatorNew_();
	}
		
	private boolean ZTagFormulaMath_OperatorNew_() throws ExceptionZZZ {
//	 boolean bReturn = false;
//	 main:{
//			if(this.getFlag("init")==true){
//				bReturn = true;
//				break main;
//			}
//			
//	 	}//end main:
		return true;
	 }//end function ZTagFormulaMath_OperatorNew_
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();		
		return true;
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		this.sOperator=null;
		this.sOperand01=null;
		this.sOperand02=null;
		return true;
	}
	
	
	//###### Getter / Setter
	public String getOperator(){
		return this.sOperator;
	}
	public void setOperator(String sOperator){
		this.sOperator=sOperator;
	}
	
	public String getOperand01(){
		return this.sOperand01;
	}
	public void setOperand01(String sOperand){
		this.sOperand01=sOperand;
	}
	
	public String getOperand02(){
		return this.sOperand02;
	}
	public void setOperand02(String sOperand){
		this.sOperand02=sOperand;
	}
	
	
	//### Aus ITagBasicZZZ
//	@Override
//	public String getNameDefault() throws ExceptionZZZ {
//		return ZTagFormulaMath_OperatorZZZ.sTAG_NAME;
//	}
	
	public String computeParsed() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String sOperator = this.getOperator();
			String sOperand01=this.getOperand01();
			String sOperand02=this.getOperand02();
			
			sReturn = this.compute(sOperand01, sOperator, sOperand02);
		}//end main:
		return sReturn;
	}
	
	public String computeParsed(String sValue01, String sValue02) throws ExceptionZZZ{
		String sReturn = null;
		main:{			
			String sOperator = this.getOperator();			
			sReturn = this.compute(sValue01, sOperator ,sValue02);
		}//end main:
		return sReturn;
	}
	
	public String compute(String sValue01, String sOperator, String sValue02) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(StringZZZ.isEmptyTrimmed(sValue01) && StringZZZ.isEmptyTrimmed(sValue02)) break main;
			
			if(StringZZZ.isEmptyTrimmed(sOperator)) {
				//Dann einfach hintereinandersetzen
				sReturn = sValue01 + sValue02;
				break main;
			}
			
			if(StringZZZ.isEmptyTrimmed(sValue01)) break main;
			if(StringZZZ.isEmptyTrimmed(sValue02)) break main;								
									
			try{
				String sOp = sOperator;
				
			//erst ab Java 1.7 kann auf Strings in einenr Switch - Anweisung gepürft werden.
//			switch(sOperator){
//			case "*":
//				break;
//			case "+":
//				
//			case "-":
//			case "/":
//			case else:
//				//+ als Default
//				
//			}
			
//			String sOp = "+"; //+ als Default
//			if(!StringZZZ.isEmpty(sOperator)){
//				sOp = sOperator.trim();				
//			}
			
			if(StringZZZ.isFloatExplizit(sValue01) || StringZZZ.isFloatExplizit(sValue02)){ //Explizit: Damit nicht Integer Werte (die auch gültig als Float umgerechnet werden könnten, hier nicht umgeändert werden)
				Float fltA = new Float(sValue01);
				Float fltB = new Float(sValue02);
				
				float fReturn = 0.0f;
				if(sOp.equals("+")){
					fReturn = fltA.floatValue() + fltB.floatValue();				
				}else if(sOp.equals("*")){
					fReturn = fltA.floatValue() * fltB.floatValue();
				}else{
					ExceptionZZZ ez = new ExceptionZZZ( "the operator '" + sOperator + "' is not handled yet.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
				Float fltReturn = new Float(fReturn);
				sReturn = fltReturn.toString();

			}else{
				Integer intA = new Integer(sValue01);
				Integer intB = new Integer(sValue02);
				int iReturn = 0;
				if(sOp.equals("+")){
					iReturn = intA.intValue() + intB.intValue();				
				}else if(sOp.equals("*")){
					iReturn = intA.intValue() * intB.intValue();
				}else{
					ExceptionZZZ ez = new ExceptionZZZ( "the operator '" + sOperator + "' is not handled yet.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					throw ez;		 
				}
				Integer intReturn = new Integer(iReturn);
				sReturn = intReturn.toString();
				}
			}catch(NumberFormatException e){
				String stemp = "Fehler bei Berechung: '" + sValue01 + "'_'" + sOperator + "'_'" + sValue02 + "'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": " + stemp);
				ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
									
		}//end main:
		return sReturn;
	}
	
//	public Vector3ZZZ<String> computeFirstVector(String sLineWithExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
//		String sReturn = sLineWithExpression;
//		String sReturnTag = null;
//		boolean bIsError = false;
//		main:{
//			if(StringZZZ.isEmpty(sLineWithExpression)) break main;
//			
//			vecReturn = this.parseFirstVector(sLineWithExpression);			
//			sReturnTag = vecReturn.get(1).toString();		
//									
//			if(!StringZZZ.isEmpty(sReturnTag)){															
//				this.setOperator(sReturnTag);
//				//Nein, das ist nicht korrekt  String sValue01 = (String)vecReturn.get(0);
//				//Nein, das ist nicht korrekt  String sValue02 = vecReturn.get(2).toString();
//				
//				//links vom Operator
//				ZTagFormulaMath_ValueZZZ objValue01 = new ZTagFormulaMath_ValueZZZ();
//				if(objValue01.isExpression(sLineWithExpression)){
//					sLineWithExpression = objValue01.parse(sLineWithExpression);						
//				}
//				//String sValue01 = objValue01.getValue();
//				
//				//rechts vom Operator
//				ZTagFormulaMath_ValueZZZ objValue02 = new ZTagFormulaMath_ValueZZZ();
//				if(objValue02.isExpression(sLineWithExpression)){
//					sLineWithExpression = objValue02.parse(sLineWithExpression);						
//				}	
//				//String sValue02 = objValue02.getValue();
//				
//				//scheint irgendwie besser zu sein im Fehlerfall die ganze Zeile zurückzugeben.
//				try{
//					sReturn = this.computeParsed();
//				}catch(ExceptionZZZ ez){
//					bIsError=true;
//				}					
//			} //end if sExpression = ""
//				
//			if(bIsError) sReturn = sLineWithExpression;					
//		}//end main:
//		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT in den Return-Vector übernehmen
//		//Wichtig, die Werte drum herum leer setzen
//		vecReturn.replace("",sReturn,"");				
//		return vecReturn;
//	}
	
	/* Merke: Das solve() kommt nicht aus einem Interface, heisst nur so */
	//Merke: Hier macht das bRemoveSurroundingSeparator keinen Sinn.
//	public String solve(String sExpressionIn) throws ExceptionZZZ{
//		return this.solve(sExpressionIn, true);
//	}
	
	/* Merke: Das solve() kommt nicht aus einem Interface, heisst nur so */
	public String solve(String sExpressionIn) throws ExceptionZZZ{
		String sReturn = sExpressionIn;
		String sReturnTag = null;
		
		//gibt es z.B. in dieser Klasse nicht, da nur SimpleTag ... this.setRaw(sExpressionIn);
		//dito kein Entry-Objekt...                                 objEntry.isSolveCalled(true);
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;						
			
			String sExpression = sExpressionIn;
			
			sReturn = this.parse(sExpression); //Merke: Das fuellt auch die Operanden und den Operator.
			
			sReturn = this.computeParsed();    //Merke: Das nutzt dann den Operator und die Operanden.
		}//end main:
		return sReturn;
	}
	

	//### Aus Interface IKernelExpressionIniZZZ	
		@Override
		public String parse(String sExpressionIn) throws ExceptionZZZ{
			String sReturn = sExpressionIn;
			String sReturnTag = null;			
			main:{
				if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
				
				String sExpression = sExpressionIn;
				Vector3ZZZ<String> vecExpression=null;				
				
				//links vom Operator	
				ZTagFormulaMath_ValueZZZ objValue01 = new ZTagFormulaMath_ValueZZZ();
				if(objValue01.isExpression(sExpression)){
					//false bedeutet, dass der umgebende Tag entfernt werden soll. Das ist wichtig, um den nächsten Wert zu finden.
					vecExpression = objValue01.parseFirstVector(sExpression, false);						
				}
				this.setOperand01(objValue01.getValue());
				sExpression = VectorUtilZZZ.implode(vecExpression);
				
				
				//rechts vom Operator
				ZTagFormulaMath_ValueZZZ objValue02 = new ZTagFormulaMath_ValueZZZ();
				if(objValue02.isExpression(sExpression)){
					//false bedeutet, dass der umgebende Tag entfernt werden soll. Das ist wichtig, um den nächsten Wert zu finden.
					vecExpression = objValue02.parseFirstVector(sExpression, false);						
				}	
				this.setOperand02(objValue02.getValue());
				sExpression = VectorUtilZZZ.implode(vecExpression);
				
				//Der Operator selbst.
				Vector3ZZZ<String> vecReturn = this.parseFirstVector(sExpression);			
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.	
				this.setValue(sReturnTag);
				this.setOperator(sReturnTag);				
				
				//TODOGOON20240114;//Das Ausrechnen in eine solve...() Methode packen, die aber wohl nicht aus einem Interface kommt
				//Vector3ZZZ<String> vecAll = this.computeFirstVector(sExpression);
				//Nein, der Operator selbst ist ja ein z.B. "+",    sReturnTag = (String) vecAll.get(1);
				//this.setValue(sReturnTag);
				
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturnTag = this.getOperand01() + this.getOperator() + this.getOperand02();
				vecReturn.replace(0, "");
				vecReturn.replace(1, sReturnTag);
				vecReturn.replace(2, "");
				sReturn = VectorUtilZZZ.implode(vecReturn);
			}//end main:
			return sReturn;
		}

		

		//### aus IConvertableZZZ
		@Override
		public boolean isConvertRelevant(String sStringToProof) throws ExceptionZZZ {	
			return false;
		}
}//End class