package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;

/**
 *  Default Configuration of this project
 * 
 * @author lindhaueradmin, 20.10.2018, 06:32:14
 * 
 */
@SuppressWarnings("unchecked")
public class KernelConfigZZZ extends AbstractKernelConfigZZZ{	
	private static final long serialVersionUID = 1L;
	private static String sDIRECTORY_CONFIG_DEFAULT = "";
	private static String sFILE_CONFIG_DEFAULT = "ZKernelConfigKernel_default.ini";
	private static String sKEY_APPLICATION_DEFAULT = "ZZZ";
	private static String sNUMBER_SYSTEM_DEFAULT= "01";
	
	public KernelConfigZZZ() throws ExceptionZZZ{
		super();
	}
	public KernelConfigZZZ(String[] saArg) throws ExceptionZZZ{
		super(saArg);
	}
	
	@Override
	public String getApplicationKeyDefault() {
		return KernelConfigZZZ.sKEY_APPLICATION_DEFAULT;
	}
	
	@Override
	public String getConfigDirectoryNameDefault() {
		return KernelConfigZZZ.sDIRECTORY_CONFIG_DEFAULT;	
	}
	@Override
	public String getConfigFileNameDefault() {
		return KernelConfigZZZ.sFILE_CONFIG_DEFAULT;		
	}	

	@Override
	public String getSystemNumberDefault() {		
		return KernelConfigZZZ.sNUMBER_SYSTEM_DEFAULT;
	}
	
	@Override
	public String getPatternStringDefault() {
		return "k:s:f:d:lf:ld:";
	}
	@Override
	public String getProjectNameDefault() {
		return IKernelConfigConstantZZZ.sPROJECT_NAME;
	}
	
	@Override
	public String getProjectDirectoryDefault() {
		return IKernelConfigConstantZZZ.sPROJECT_DIRECTORY;
	}
}
