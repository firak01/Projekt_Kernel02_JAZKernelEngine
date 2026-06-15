package basic.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sPATTERN_DEFAULT;

import java.util.List;

import static basic.zKernel.IKernelConfigConstantZZZ.sFLAGZ_DEFAULT;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Interface, welches von KernelConfigZZZ etc. eingebunden wird, um die Default Werte festzulegen.
 * @author lindhauer
 *
 */
public interface IKernelConfigZZZ extends IFlagZEnabledZZZ,IKernelConfigProjectHelperZZZ, IKernelConfigConstantZZZ{
	
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
	
	
	//20210331: Flagz, default String, als leerer JSON-Wert
	public String getConfigFlagzJsonDefault();
		
	/** Die Argumente, die für diese Konfiguration erlaubt sind. Siehe dazu GetOptZZZ()
	* @return (z.B. "a:b:cde:", mit dem Doppelpunkt als Anzeichen dafür, das ein Parameter diesem Steuerungsargument folgt.)
	* 
	* lindhauer; 31.07.2007 06:24:53
	 * @throws ExceptionZZZ 
	 */
	public String readPatternString() throws ExceptionZZZ;
	public String getPatternStringDefault() throws ExceptionZZZ;
	public String[] getArgumentArrayDefault() throws ExceptionZZZ;
	
	
	//20260615 Die Dokumentation anzeigen lassen über -? oder -help
	public String readActionHelp() throws ExceptionZZZ;
	public String readActionH() throws ExceptionZZZ;
	public String getHelp() throws ExceptionZZZ;
	public List<IKernelConfigHelpLineZZZ>getHelpList() throws ExceptionZZZ;
	
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
