package basic.zBasic.config;

import java.util.EnumSet;

import basic.zKernel.config.IEnumSetKernelConfigDefaultEntryZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

public interface IConfigConstantZZZ {
	//#################################################
	//Merke: Die Konstanten sind meist nicht final, damit sie von der konkreten Konfiguration
	//       ueberschrieben werden koennen.
	//       Final sind die fuer den Kernel selbst wichtige Konstanten

  	//####### Reflektion zum Gesamtprojekt
  	static String sPROJECT_DIRECTORY = "Project_Kernel02_JAZKernelEngine";
  	static String sPROJECT_NAME = "JAZKernelEngine";

  	//####### Wieviel per Default auf der Konsole ausgeben werden soll
  	static int iPRINT_LEVEL_DEFAULT = 3; //Als eine Art Debug Level
  	static int iPRINT_LEVEL_ALL=3;       //also Debug All
	
	//#####################################################################
	//####### Konfiguration der Argumgentuebergabe von aussen an das Program (s. GetOptZZZ).
	//Merke1: Ein Doppelpunkt bedeutet "es folgt ein Wert". 
	//        Moeglich ist auch ein Pipe "|" nachfolgend. D.h. es gibt dazu keinen Wert.
	//        Entsprechend wird ein Wert ohne "|" gesehen.
	//Merke2: Es ist auch moeglich Argumente mit mehr als 2 Zeichen zu definieren.
	
	//Merke3: Die Flags sind für alle Objekte, die Flags behandeln gedacht. Hier definiert zur Vereinheitlichung.
	final static String sPATTERN4FLAG_DEFAULT="z:zcustom:zlocal:"; 
										//z = Flags, die dann JSON aehnlich ueber, die dann JSON aehnlich uebergeben werden, berücksichtigen Vererbungshierarchie.
	                                    //zcustom == Anwendungsspezifische Flags
										//zlocal = Lokale Flags, die dann JSON aehnlich uebergeben werden, berücksichtigen KEINE Vererbungshierarchie
	
	//Mereke4: Die Angaben zum Kernel sind nur für Kernel Objekte gedacht. Das ist nicht in allen meinen Appliaktionen notwendig. 
	final static String sPATTERN4CONFIG_DEFAULT="help|h|printLevel:pd:p:";
	final static String sPATTERN_CONFIG_DEFAULT= sPATTERN4CONFIG_DEFAULT + sPATTERN4FLAG_DEFAULT;	
	final static String sFLAGZ_DEFAULT="{}"; //leerer JSON ähnlicher String für zu setztende Flags, z.B. gefüllt {"DEBUGUI_PANELLABEL_ON":true}
	
	
	//######################################################################
	//######## Konfigurationsdefinitionen in der INI-Datei #################
	
	
	//##################################################
	//######### PFADE ##################################
	public static String sDIRECTORY_ROOT="c:\\fglkernel";
}
