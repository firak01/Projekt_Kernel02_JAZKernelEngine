package basic.zKernel.file.ini;

import java.util.Vector;

import basic.zBasic.AbstractObjectWithExpressionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;

public abstract class AbstractIniTagCascadedZZZ<T> extends AbstractIniTagSimpleZZZ<T> implements IIniTagCascadedZZZ{
	private static final long serialVersionUID = 3041123191138631240L;

	//Merke: Der Name der Tags wird auf unterschiedliche Weise geholt.
	protected String sTagParentName = null; //String fuer den Fall, das ein Tag OHNE TagType erstellt wird.	
		
	public AbstractIniTagCascadedZZZ() throws ExceptionZZZ {
		super();
		AbstractKernelIniTagNew_();
	}

	private boolean AbstractKernelIniTagNew_() throws ExceptionZZZ {
		return true;
	}// end function AbstractKernelIniTagNew_

	
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
	
	@Override
	public String getParentNameDefault() throws ExceptionZZZ {
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
	
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		super.reset();	
		return true;
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		return true;
	}
		
	//### Aus Interface IKernelExpressionIniZZZ		
	@Override
	public String parse(String sExpressionIn) throws ExceptionZZZ{
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		
		//############
		//Wichtig: Bei jedem neuen Parsen (bzw. vor dem Solven(), nicht parse/solveFirstVector!) die internen Werte zuruecksetzen, sonst wird alles verfaelscht.
		//         Ziel ist, das nach einem erfolgreichen Parsen/Solven das Flag deaktiviert werden kann UND danach bei einem neuen Parsen/Solven die Werte null sind.
		this.resetValues();			
		//#######
		
		//Kein Kernel => kein objEntry Handling
		
		main:{							
			parse:{
				if(StringZZZ.isEmpty(sExpressionIn)) break main;
				String sExpression = sExpressionIn;
				
				sReturnTag = this.getValue();
				sReturnLine = sExpressionIn;
				sReturn = sReturnLine;
				vecReturn.set(0, sExpressionIn);//nur bei in dieser Methode neu erstellten Vector.
				
				//Bei CASCADED Tags alle Untertags holen.
				//TODOGOON: Das muesste bei cascaded aber eigentlich AllVector sein.
				//Vector<String>vecAll = this.parseAllVector(sExpression);
				vecReturn = this.parseFirstVector(sExpressionIn);
				if(vecReturn==null) break main;
				
				//Pruefe ob der Tag enthalten ist:
				//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
				if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
								
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				this.setValue(sReturnTag);
				
				//Tags entfernen und eigenen Wert setzen
				vecReturn = this.parseFirstVectorPost(vecReturn);
				if(vecReturn==null) break main;
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturnLine = VectorUtilZZZ.implode(vecReturn);		
			} //end parse:
		}//end main:
		
		//Auf PARSE-Ebene... Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
		//if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag); //BEIM PARSEN DEN TAG-WERT NICHT IN VEC(1) UEBERNEHMEN
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
				
		
		
		return sReturn;
	}
}// End class