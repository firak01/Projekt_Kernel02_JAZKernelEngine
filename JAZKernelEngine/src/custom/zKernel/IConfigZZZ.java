package custom.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sPROJECT_NAME;
import static basic.zKernel.IKernelConfigConstantZZZ.sPROJECT_DIRECTORY;

public interface IConfigZZZ {
	//#################################################
	//Merke: Die Konstanten sind meist nicht final, damit sie von der konkreten Konfiguration
	//       ueberschrieben werden koennen.
	//       Final sind die fuer den Kernel selbst wichtige Konstanten
	
	
	//#####################################################################
	//####### Reflektion zum Gesamtprojekt
	static String sPROJECT_DIRECTORY = "Project_Kernel02_JAZKernelEngine";
	static String sPROJECT_NAME = "JAZKernelEngine";
	
	//Konstanten, die IKernelConfigConstantZZZ ueberschreiben sollen.
	public static final String sKEY_APPLICATION_DEFAULT = "FGL";
	public static final String sNUMBER_SYSTEM_DEFAULT= "01";
	
}
