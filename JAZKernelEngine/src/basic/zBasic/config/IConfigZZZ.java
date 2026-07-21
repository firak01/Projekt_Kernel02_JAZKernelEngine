package basic.zBasic.config;

import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.GetOptZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IConfigZZZ extends IConfigProjectZZZ, IConfigConstantZZZ, IFlagZEnabledZZZ{

	
	//Das Objekt für die Übergabeparameter, per Batch
	public GetOptZZZ getOptObject();
	
	//20260704 Nur die Werte auszulesen reicht nicht. Vorher die Option ermitteln. Merke: Die Option ist der Key in  der HashMap
	public boolean hasOption(String sOption) throws ExceptionZZZ;
	public String readOptionValue(String sOption) throws ExceptionZZZ;
		
	
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
	
	public String createHelp() throws ExceptionZZZ;
	public List<IKernelConfigHelpLineZZZ>getHelpList() throws ExceptionZZZ;
	

	public String readPrintLevel() throws ExceptionZZZ;
	public String getPrintLevelDefault() throws ExceptionZZZ;
	
	//Ergänzend zu IConfigProjectZZZ
	public String readProjectName() throws ExceptionZZZ;
	public String readProjectDirectory() throws ExceptionZZZ;
}
