package basic.zKernel;

import java.util.EnumSet;

import basic.zBasic.config.IConfigConstantZZZ;
import basic.zKernel.config.IEnumSetKernelConfigDefaultEntryZZZ;
import basic.zKernel.config.KernelConfigDefaultEntryZZZ;

public interface IKernelConfigConstantZZZ {
	//#################################################
	//Merke: Die Konstanten sind meist nicht final, damit sie von der konkreten Konfiguration
	//       ueberschrieben werden koennen.
	//       Final sind die fuer den Kernel selbst wichtige Konstanten
	
	
	//#####################################################################
	//####### Reflektion zum Gesamtprojekt
	static String sPROJECT_DIRECTORY = "Project_Kernel02_JAZKernelEngine";
	static String sPROJECT_NAME = "JAZKernelEngine";
	
	
	//#####################################################################
	//####### Konfiguration der Argumgentuebergabe von aussen an das Program (s. GetOptZZZ).
	//Merke1: Ein Doppelpunkt bedeutet "es folgt ein Wert". 
	//        Moeglich ist auch ein Pipe "|" nachfolgend. D.h. es gibt dazu keinen Wert.
	//        Entsprechend wird ein Wert ohne "|" gesehen.
	//Merke2: Es ist auch moeglich Argumente mit mehr als 2 Zeichen zu definieren.
	
	//Merke3: Die Flags sind für alle Objekte, die Flags behandeln gedacht. Hier definiert zur Vereinheitlichung.
	//final static String sPATTERN4FLAG_DEFAULT="z:zcustom:zlocal:"; 
										//z = Flags, die dann JSON aehnlich ueber, die dann JSON aehnlich uebergeben werden, berücksichtigen Vererbungshierarchie.
	                                    //zcustom == Anwendungsspezifische Flags
										//zlocal = Lokale Flags, die dann JSON aehnlich uebergeben werden, berücksichtigen KEINE Vererbungshierarchie
	
	//Mereke4: Die Angaben zum Kernel sind nur für Kernel Objekte gedacht. Das ist nicht in allen meinen Appliaktionen notwendig. 
	final static String sPATTERN4KERNEL_DEFAULT = IConfigConstantZZZ.sPATTERN4CONFIG_DEFAULT + "k:s:f:d:lf:ld:";
	final static String sPATTERN_KERNEL_DEFAULT = sPATTERN4KERNEL_DEFAULT + IConfigConstantZZZ.sPATTERN4FLAG_DEFAULT;	
	//final static String sFLAGZ_DEFAULT="{}"; //leerer JSON ähnlicher String für zu setztende Flags, z.B. gefüllt {"DEBUGUI_PANELLABEL_ON":true}
	
	
	//######################################################################
	//######## Konfigurationsdefinitionen in der INI-Datei #################
	public enum MODULEPROPERTY{
		Path, File ; //20220814: EIGENSCHAFTEN, DIE ZUR Definition der ini-Datei Einträge fuer Module genutzt werden können
	}
	
	public static String sKEY_APPLICATION_DEFAULT = "ZZZ";
	public static String sNUMBER_SYSTEM_DEFAULT= "01";
	
	
	public final static String sMODULE_PREFIX="KernelConfig";
	
	//Scheinbar manchmal Probleme diese Konstanten "Auszurechnen". Verwende dann die Methode KernelKernelZZZ.getModuleDirectoryPrefix()
	public final static String sMODULE_DIRECTORY_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.Path.name();
	//Scheinbar manchmal Probleme diese Konstanten "Auszurechnen". Verwende dann die Methode KernelKernelZZZ.getModuleFilenamePrefix()
	public final static String sMODULE_FILENAME_PREFIX=sMODULE_PREFIX+MODULEPROPERTY.File.name();
	
	
	//##################################################
	//######### PFADE ##################################
	public static String sDIRECTORY_ROOT="c:\\fglkernel";
	
	//Zum Auslesen der Konfigurationseinträge in der INI-Datei
	//Merke: Dies wird in KernelConfigDefaultEntryZZZ, in der Enumeration ggfs. verwendet.
	public static String sDIRECTORY_CONFIG_DEFAULT=sDIRECTORY_ROOT + "\\kernelconfig";
	//public final static String sDIRECTORY_CONFIG_DEFAULT = "<z:Null/>";//Merke: Ein Leerstring ist der Root vom Classpath, z.B. in Eclipse der src-Ordner. Ein "." oder ein NULL-Wert ist der Projektordner in Eclipse
	public static String sFILENAME_CONFIG_DEFAULT="ZKernelConfigKernel.ini";
	//public final static String sFILE_CONFIG_DEFAULT = "ZKernelConfigKernel_default.ini";
	
	static String sLOG_FILE_NAME_DEFAULT= "ZKernelLog_default.txt";
	static String sLOG_FILE_DIRECTORY_DEFAULT= sDIRECTORY_ROOT + "\\kernellog";//Merke: "." waere der Projektordner als Verzeichnis
	
	//#######################################################
	//### Eingebettete Enum-Klasse mit den Defaultwerten, diese Werte werden auch per Konstruktor übergeben.
	//### int Key, String shorttext, String longtext, String description
	//#######################################################
	public enum EnumConfigDefaultEntryZZZ implements IEnumSetKernelConfigDefaultEntryZZZ{//Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		
		//Merke1: Die Defaultwerte in der Kernel-Konfiguration richten sich nach den in der INI-Konfiguration verwedendeten Werten.
		//        z.B. KernelExpressionIni_NullZZZ liefert <z:Null> als Tag.
		//Merke2: In den Testklassen wird aber als Pfad verwendet: test
	    //@IFieldDescription(description = "DTXT01 TEXTVALUES")
		//T01(1,KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX ,KernelConfigDefaultEntryZZZ.sDIRECTORY_CONFIG_DEFAULT,"The default path of the configuration"),
		T01(1,KernelConfigDefaultEntryZZZ.sMODULE_DIRECTORY_PREFIX ,"<z:Null/>","The default path of the configuration"),

		//@IFieldDescription(description = "DTXT02 TEXTVALUES") 
		//T02(2,"KernelConfigFile",KernelConfigDefaultEntryZZZ.sFILENAME_CONFIG_DEFAULT, "The default filename of the configuration");
		T02(2,KernelConfigDefaultEntryZZZ.sMODULE_FILENAME_PREFIX,KernelConfigDefaultEntryZZZ.sFILENAME_CONFIG_DEFAULT, "The default filename of the configuration");
   	   	
		private int iId;
		private String sProperty;
		private String sValueDefault;
		private String sDescription;

		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht instantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.  ( basic/zBasic/util/abstractEnum/EnumSetMappedUtilZZZ.java ).
		EnumConfigDefaultEntryZZZ(){	
		}
		   
		EnumConfigDefaultEntryZZZ(int iId, String sProperty, String sValueDefault, String sDescription){
	       this.iId = iId;
	       this.sProperty = sProperty;
	       this.sValueDefault = sValueDefault;
	       this.sDescription = sDescription;
		}
	   	   

	   //##################################################
	   //#### Folgende Methoden holen die definierten Werte.
	   @Override
	   public int getType() {   	
	   	return this.iId;
	   }
	
	   @Override
	   public String getConfigProperty() {   	
	   	return this.sProperty;
	   }
	
	   @Override
	   public String getValueDefault() {
	   	return this.sValueDefault;
	   }
	   
	
		@Override
		public String getDescription() {
		 return this.sDescription;
		}
	   
		//#### Methode aus IKeyProviderZZZ
		//public Long getThiskey() {
		//	return this.lKey;
		//}
		
	   @Override
	   public EnumSet<?>getEnumSetUsed(){
	   	return EnumConfigDefaultEntryZZZ.getEnumSet();
	   }
	      
	   public static EnumSet<?> getEnumSet() {
		   //Merke: Das wird anders behandelt als FLAGZ Enumeration.
		   	//String sFilterName = "FLAGZ"; /
		   	//...
		   	//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
		   	
		   	//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
		   	Class<EnumConfigDefaultEntryZZZ> enumClass = EnumConfigDefaultEntryZZZ.class;
		   	EnumSet<EnumConfigDefaultEntryZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
		   	
		   	for(Object obj : EnumConfigDefaultEntryZZZ.class.getEnumConstants()){
		   		//System.out.println(obj + "; "+obj.getClass().getName());
		   		set.add((EnumConfigDefaultEntryZZZ) obj);
		   	}
		   	return set;
		}
	 
	
	   //TODO: Mal ausprobieren was das bringt
	   //Convert Enumeration to a Set/List
	   private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
	   	  EnumSet<E> set=EnumSet.noneOf(enumClass);
	   	  long mask=1;
	   	  for (  E e : enumClass.getEnumConstants()) {
	   	    if ((mask & vector) == mask) {
	   	      set.add(e);
	   	    }
	   	    mask<<=1;
	   	  }
	   	  return set;
	   	}
	   
	 //+++ Das könnte auch in einer Utility-Klasse sein.
	   // the valueOfMethod <--- Translating from DB
	   public static EnumConfigDefaultEntryZZZ fromShorttext(String s) {
	       for (EnumConfigDefaultEntryZZZ state : values()) {
	           if (s.equals(state.getConfigProperty()))
	               return state;
	       }
	       throw new IllegalArgumentException("Not a correct shorttext: " + s);
	   }

   	  //##################################################
	  //#### Folgende Methoden bring Enumeration von Hause aus mit. 
   			//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
	   public String getName(){
		   return super.name();
	   }
	   
	   @Override
	   public String toString() {
	       return this.sProperty+"="+this.sValueDefault+"#"+this.iId + " "+ this.sDescription;
	   }

	   @Override
	   public int getIndex() {
	   	return ordinal();
	   }
	   
	   //### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
	   @Override
	   public int getPosition() {
	   		return getIndex() + 1;
	   }
   }//End inner classs			
}
