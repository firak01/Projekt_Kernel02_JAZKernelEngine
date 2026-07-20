package basic.zKernel;

import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.config.IConfigProjectHelperZZZ;
import basic.zBasic.config.IConfigZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Interface, welches von KernelConfigZZZ etc. eingebunden wird, um die Default Werte festzulegen.
 * @author lindhauer
 *
 */
public interface IKernelConfigZZZ extends IKernelConfigConstantZZZ, IConfigZZZ, IConfigProjectHelperZZZ{
	
	/**Falls Kein entsprechender Parameter in der Kommandozeile übergeben worden ist, so wird der hier definierte Wert verwendet für den Initialisierung des Kernels
	* @return
	* 
	* lindhauer; 25.07.2007 07:20:25
	 * @throws ExceptionZZZ 
	 */
	public String readApplicationKey() throws ExceptionZZZ;
	public String getApplicationKeyDefault();
	public String readSystemNumber() throws ExceptionZZZ;
	public String getSystemNumberDefault();
	public String readConfigDirectoryName() throws ExceptionZZZ;
	public String getConfigDirectoryNameDefault();
	public String readConfigFileName() throws ExceptionZZZ;
	public String getConfigFileNameDefault();
	
	public String getLogFileName() throws ExceptionZZZ;
	public String getLogFileNameDefault();
	public String readLogFileName() throws ExceptionZZZ;
	public boolean isLogFileNameDefault(String sValue) throws ExceptionZZZ;
	
	public String getLogDirectoryName() throws ExceptionZZZ;
	public String getLogDirectoryNameDefault();
	public String readLogDirectoryName() throws ExceptionZZZ;
	public boolean isLogDirectoryNameDefault(String sValue) throws ExceptionZZZ;
	
	

	
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;
	public boolean isInIDE() throws ExceptionZZZ;
	public boolean isOptionObjectLoaded() throws ExceptionZZZ;
	
	public boolean isApplicationKeyDefault(String sApplicationKey) throws ExceptionZZZ;
	public boolean isSystemNumberDefault(String sSystemNumber) throws ExceptionZZZ;
	public boolean isPatternStringDefault(String sValue) throws ExceptionZZZ;
	public boolean isConfigFlagzJsonDefault(String sValue) throws ExceptionZZZ;
	public boolean isConfigFileNameDefault(String sValue) throws ExceptionZZZ;
	public boolean isConfigDirectoryNameDefault(String sValue) throws ExceptionZZZ;
	
}
