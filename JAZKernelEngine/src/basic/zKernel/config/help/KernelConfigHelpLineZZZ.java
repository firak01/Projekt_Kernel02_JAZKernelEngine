package basic.zKernel.config.help;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KernelConfigHelpLineZZZ implements IKernelConfigHelpLineZZZ {
	private IKernelConfigHeaderLineZZZ objHeadLine=null;
	private String sAbbreviation=null;
	private String sName=null;
	private String sDescription=null;
	
	

	//### Konstruktoren
	public KernelConfigHelpLineZZZ() {		
	}
	
	public KernelConfigHelpLineZZZ(String sAbbreviation, String sName, String sDescription) throws ExceptionZZZ {	
		KernelConfigHelpLineZZZnew_(sAbbreviation, sName, sDescription);
	}
	
	private boolean KernelConfigHelpLineZZZnew_(String sAbbreviation, String sName, String sDescription) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			this.setAbbreviation(sAbbreviation);
			this.setDescription(sDescription);
			this.setName(sName);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//### GETTER / SETTER
	@Override
	public IKernelConfigHeaderLineZZZ getHeaderLine() throws ExceptionZZZ{
		return this.objHeadLine;
	}

	@Override
	public void setHeaderLine(IKernelConfigHeaderLineZZZ objHeaderLine) throws ExceptionZZZ{
		this.objHeadLine = objHeaderLine;
	}
	
	@Override
	public String getAbbreviation() throws ExceptionZZZ{
		return sAbbreviation;
	}

	@Override
	public void setAbbreviation(String sAbbreviation) throws ExceptionZZZ{
		this.sAbbreviation = sAbbreviation;
	}

	@Override
	public String getName() throws ExceptionZZZ{
		return sName;
	}

	@Override
	public void setName(String sName) throws ExceptionZZZ{
		this.sName = sName;
	}

	@Override
	public String getDescription() throws ExceptionZZZ{
		return sDescription;
	}

	@Override
	public void setDescription(String sDescription) throws ExceptionZZZ{
		this.sDescription = sDescription;
	}
		
}
