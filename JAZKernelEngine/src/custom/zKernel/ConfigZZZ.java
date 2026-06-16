package custom.zKernel;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;


/**Klasse enthaelt die Werte, die im Kernel als default angesehen werden.
	 *- ApplicationKey: FGL
	 * - SystemNumber: 01
	 * - Verzeichnis: c:\\fglKernel\\KernelConfig
	 * - Datei:		ZKernelConfigKernel_default.ini
	
	Verwende eine eigene Klasse, die KernelConfigZZZ erweitert, um für eine Spezielles Projekt andere Werte zu verwenden.
 * @author lindhauer
 *
 */
public class ConfigZZZ extends AbstractKernelConfigZZZ implements IConfigZZZ{
	
	public ConfigZZZ() throws ExceptionZZZ{
		super();
	}
	public ConfigZZZ(String[] saArg) throws ExceptionZZZ {
		super(saArg); 
	} 
			
	@Override
	public String getApplicationKeyDefault() {
		return IConfigZZZ.sKEY_APPLICATION_DEFAULT;
	}
	@Override
	public String getConfigDirectoryNameDefault() {
		return ConfigZZZ.sDIRECTORY_CONFIG_DEFAULT;
	}
	@Override
	public String getConfigFileNameDefault() {		
		return ConfigZZZ.sFILENAME_CONFIG_DEFAULT;
	}
	@Override
	public String getPatternStringDefault() {
		return ConfigZZZ.sPATTERN_KERNEL_DEFAULT;
	}
	@Override
	public String getSystemNumberDefault() {
		return IConfigZZZ.sNUMBER_SYSTEM_DEFAULT;
}
	@Override
	public String getProjectName() {
		return ConfigZZZ.sPROJECT_NAME;
	}
	@Override
	public String getProjectDirectory() {
		return ConfigZZZ.sPROJECT_PATH;
	}


	

	

	

}
