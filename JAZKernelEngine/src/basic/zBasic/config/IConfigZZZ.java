package basic.zBasic.config;

import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.GetOptZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IConfigZZZ extends IConfigConstantZZZ, IFlagZEnabledZZZ{

	//Das jeweils eigene Projekt. Merke: normalerweise private Konstante .sPROJECT_NAME
	public String getProjectName() throws ExceptionZZZ;
	
	//Der Pfad zum eigenen Projekt. Merke: normalerweise private Konstante .sPROJECT_DIRECTORY
	public String getProjectDirectory() throws ExceptionZZZ;
	
	//Das Objekt für die Übergabeparameter, per Batch
	public GetOptZZZ getOptObject();
	
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
}
