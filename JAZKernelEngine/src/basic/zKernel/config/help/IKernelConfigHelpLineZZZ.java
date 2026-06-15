package basic.zKernel.config.help;

import basic.zBasic.ExceptionZZZ;

public interface IKernelConfigHelpLineZZZ {
	
	public String getsAbbreviation()  throws ExceptionZZZ;
	public void setAbbreviation(String sAbbreviation)  throws ExceptionZZZ;
	
	public String getName() throws ExceptionZZZ;
	public void setName(String sName) throws ExceptionZZZ;
		
	public String getDescription()  throws ExceptionZZZ;
	public void setDescription(String sDescription) throws ExceptionZZZ;
}
