package basic.zBasic.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.LogZZZ;
import basic.zBasic.ReflectLaunchArgumentZZZ;
import basic.zBasic.util.datatype.enums.EnumUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.IPrintLevelUserZZZ;
import basic.zBasic.util.system.SystemSingletonZZZ;
import basic.zKernel.AbstractKernelConfigZZZ;
import basic.zKernel.GetOptZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.config.help.IKernelConfigHeaderLineZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;
import basic.zKernel.config.help.KernelConfigHeaderLineZZZ;
import basic.zKernel.config.help.KernelConfigHelpLineZZZ;
import custom.zKernel.ILogLevelUserZZZ;
import custom.zKernel.Log;
import custom.zKernel.LogSingletonZZZ;

public abstract class AbstractConfigZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IConfigZZZ, IConfigConstantZZZ{
	private static final long serialVersionUID = 3005226115171469499L;
		
	protected GetOptZZZ objOpt = null;
	protected volatile ILogLevelUserZZZ.LOGLEVEL enumLogLevelOverall = null;
	protected volatile IPrintLevelUserZZZ.PRINTLEVEL enumPrintLevelOverall = null;
	
	
	public AbstractConfigZZZ() throws ExceptionZZZ{
		super();//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		AbstractConfigNew_(null);
	}
	public AbstractConfigZZZ(String[] saArg) throws ExceptionZZZ{
		super();//!!! Hier wäre die Elternklasse, diejenige, die Flags setzt //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		AbstractConfigNew_(saArg);
	}	
	public AbstractConfigZZZ(String[] saArg, String[]saFlagControl) throws ExceptionZZZ{
		super(saFlagControl); //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt 	
		AbstractConfigNew_(saArg);
	}
	
	public AbstractConfigZZZ(String[] saArg, String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl); //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		AbstractConfigNew_(saArg);
	}
	
	public AbstractConfigZZZ(String sFlagControl) throws ExceptionZZZ{
		super(sFlagControl); //20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		AbstractConfigNew_(null);
	}
	
	private boolean AbstractConfigNew_(String[] saArgIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{				
			String sLog = "Initializing ConfigObject";
			LogZZZ.printlnDateWithPosition(this, sLog); //Hier nicht das Singleton Objekt nehmen, da es ggfs. noch nicht erstellt ist. Endlosschleife
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
						
			String[] saArg = null;
			if(saArgIn==null || StringArrayZZZ.isEmpty(saArgIn)){
				//Das uebergebene Argument-Array darf auch leer sein.
//				ExceptionZZZ ez = new ExceptionZZZ("Argument - Array", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
				
				saArg = this.getArgumentArrayDefault();				
			}else {
				saArg = saArgIn;
			}
			
			
			//Wenn in dem Übergabestring Platzhalter für Umgebungsvariablen sind, z.B. $.{sPATZZZ}, diese hiermit in den tatsächlichen Wert umwandeln
			saArg = ReflectLaunchArgumentZZZ.replaceArgumentsWithEnvironmentValue(saArg);
			
			
			//Nun den konfigurierten String holen
			String sPattern = this.getPatternStringDefault();
			
			//Das Objekt, das für die Interpretation der Argumente sorgt.Falls Argument werte vorhanden sind "Werden sie automatisch sofort geladen".
			this.objOpt = new GetOptZZZ(sPattern, saArg);
			
			//20210331: Nun die HashMap für die weiterzureichenden FlagZ Werte füllen
			String sJson = this.objOpt.readValue("z");
			HashMap<String, Boolean> hmFlagZpassed = AbstractKernelConfigZZZ.computeHashMapFlagFromJSON(sJson);
			this.setHashMapFlagPassed(hmFlagZpassed);
		
			//20260419: Nun als ergänzende HashMap die custom FlagZ Werte füllen
			String sJsonCustom = this.objOpt.readValue("zcustom");
			HashMap<String, Boolean> hmFlagZCustomPassed = AbstractKernelConfigZZZ.computeHashMapFlagFromJSON(sJsonCustom);
			this.setHashMapFlagCustom(hmFlagZCustomPassed);
			
			//20260419: Nun als ergänzende HashMap die lokalen FlagZ Werte füllen
			String sJsonLocal = this.objOpt.readValue("zlocal");
			HashMap<String, Boolean> hmFlagZlocalPassed = AbstractKernelConfigZZZ.computeHashMapFlagFromJSON(sJsonLocal);
			this.setHashMapFlagLocal(hmFlagZlocalPassed);
			
			
			bReturn = true;
		}
		return bReturn;
	}
	
	
	
	//##########
	// Getter / Setter
	//##########
	
	
	//### aus IConfigZZZ
	@Override
	public String readProjectName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("p");
			if(sReturn==null){
				sReturn = this.getProjectNameDefault();
			}
		}//end main:		
		return sReturn;
	}	
	
	@Override
	public abstract String getProjectNameDefault() throws ExceptionZZZ;

	@Override
	public String getProjectName() throws ExceptionZZZ{
		return this.getProjectNameDefault();
	}
	
	
	//++++++++++++++++++++++++++++++
	@Override
	public String readProjectDirectory() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("pd");
			if(sReturn==null){
				sReturn = this.getProjectDirectoryDefault();
			}
		}//end main:		
		return sReturn;
	}	
	
	@Override
	public abstract String getProjectDirectoryDefault() throws ExceptionZZZ;
	 
	@Override
	public String getProjectDirectory() throws ExceptionZZZ{
		return this.getProjectDirectoryDefault();
	}
	
	@Override
	public GetOptZZZ getOptObject(){
		return this.objOpt;
	}
	
	//### aus IPrintLevelUserZZZ
	
	@Override
	public String getConfigFlagzJsonDefault() {
		return IKernelConfigZZZ.sFLAGZ_DEFAULT;
	}
		
	

	@Override
	public String readPatternString() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.getPattern();
			if(sReturn==null){
				sReturn = this.getPatternStringDefault();
			}
		}		
		return sReturn;
	}

	@Override
	public String getPatternStringDefault() throws ExceptionZZZ {
		return IConfigZZZ.sPATTERN_CONFIG_DEFAULT;
	}

	@Override
	public String[] getArgumentArrayDefault() throws ExceptionZZZ {
		String[] saArg = new String[14];
		saArg[0] = "-h";							
		saArg[1] = "-z";
		saArg[2] = this.getConfigFlagzJsonDefault();
		
		return saArg;
	}
	

	@Override
	public String createHelp() throws ExceptionZZZ{
		String sReturn = "";
		main:{
			//Hier gibt es keine Elternklasse mit solch einer Methode... In hiervon erbenden Klassen die super-Methode aufrufen. 
			//List<IKernelConfigHelpLineZZZ> listaHelpLineSuper = super.getHelpList();											
			List<IKernelConfigHelpLineZZZ> listaHelpLineTotal = this.getHelpList();			
		    //listaHelpLineTotal = ListUtilZZZ.join(listaHelpLineSuper, listaHelpLineTotal);
			
			sReturn = ConfigUtilZZZ.createHelp(listaHelpLineTotal);
		}//end main
		return sReturn;
	}
	
	//Merke 20260615: Besser eine Liste von Hilf-Objekt-Zeilen. Auch kein Enum. 
	//                Der Ansatz mit der einfachen Liste der Objekte läßt sich einfacher 
	//                über mehrere Projekte und Vererbungstrukturen umsetzen
	//Also nicht so etwas nutzen wie:
	//public enum LOGSTRINGFORMAT implements IEnumSetMappedStringFormatZZZ{		
	//            und darin:    STRINGTYPE01_STRING_BY_STRING("stringtype01",IStringFormatZZZ.iFACTOR_STRINGTYPE01_STRING_BY_STRING, IStringFormatZZZ.sSEPARATOR_PREFIX_DEFAULT + "[A01]", "%s",IStringFormatZZZ.iARG_STRING,  "[/A01]" + IStringFormatZZZ.sSEPARATOR_POSTFIX_DEFAULT, "Gib den naechsten Log String - sofern vorhanden - in diesem Format aus."),			
	@Override
	public List<IKernelConfigHelpLineZZZ>getHelpList() throws ExceptionZZZ{
		ArrayList<IKernelConfigHelpLineZZZ>listaReturn=new ArrayList<IKernelConfigHelpLineZZZ>();
		main:{
		//Berücksichtige dabei die Paramter aus den "Pattern" Strings
		//final static String sPATTERN4FLAG_DEFAULT="z:zcustom:zlocal:";
		//final static String sPATTERN4CONFIG_DEFAULT="help|h|";
												//z = Flags, die dann JSON aehnlich ueber, die dann JSON aehnlich uebergeben werden, berücksichtigen Vererbungshierarchie.
			                                    //zcustom == Anwendungsspezifische Flags
												//zlocal = Lokale Flags, die dann JSON aehnlich uebergeben werden, berücksichtigen KEINE Vererbungshierarchie
		IKernelConfigHeaderLineZZZ objHeaderLine=null;
		objHeaderLine= new KernelConfigHeaderLineZZZ("Argumente für: " + this.getProjectName());
		
		IKernelConfigHelpLineZZZ objHelp=null;
		objHelp = new KernelConfigHelpLineZZZ();
		objHelp.setHeaderLine(objHeaderLine);
		listaReturn.add(objHelp);
		
		objHeaderLine= new KernelConfigHeaderLineZZZ("Argumente aus: " + IConfigConstantZZZ.sPROJECT_NAME);				
		objHelp = new KernelConfigHelpLineZZZ("h","Hilfe","Zeige diese Hilfe der Argumente.");
		objHelp.setHeaderLine(objHeaderLine);
		listaReturn.add(objHelp);
		objHelp = new KernelConfigHelpLineZZZ("help","Hilfe","Zeige diese Hilfe der Argumente.");
		listaReturn.add(objHelp);
		objHelp = new KernelConfigHelpLineZZZ("p:","Projekname","Projektname, wie unter Eclipse vergeben. Ggfs. als Verzeichnis hinter dem Repository-Namen.");
		objHelp.setHeaderLine(objHeaderLine);
		listaReturn.add(objHelp);
		objHelp = new KernelConfigHelpLineZZZ("pd:","Projektverzeichnis","Projektname, wie unter Eclipse vergeben. Ggfs. als Verzeichnis hinter dem Repository-Namen.");
		listaReturn.add(objHelp);
		objHelp = new KernelConfigHelpLineZZZ("z:","Z-Kernel-flag","Flagdefinition, berücksichtigen Vererbungshierarchie.: Es muss ein JSON String folgen, z.B. -z {\"DEBUG\":false,\"INIT\":true}");
		listaReturn.add(objHelp);	
		objHelp = new KernelConfigHelpLineZZZ("zcustom:","Custom Flag","Flagdefinition, berücksichtigen nur direkte Vererbungshierarchie. Es muss ein JSON String folgen, z.B. -zcustom {\"xyz\":false,\"abc\":true}");
		listaReturn.add(objHelp);	
		objHelp = new KernelConfigHelpLineZZZ("zlocal:","Lokaler Flag","Flagdefinition, berücksichtigen KEINE Vererbungshierarchie. Es muss ein JSON String folgen, z.B. -zlocal {\"MERGE_IGNORE_CHECKOUT_CONFLICTS\":false,\"USE_STRATEGY_MERGE_CONFLICT_THEIRS\":false,\"USE_PULL_DIRECT\":true}");
		listaReturn.add(objHelp);	
		
		}//end main:
		return listaReturn;
	}

	
	//###############################################################
	//### "ist Option" angegeben
	@Override 
	public boolean hasOption(String sOption) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			HashMap<String,String>hm = objOpt.getOptionMap();
			bReturn = hm.containsKey(sOption);
		}//end main:
		return bReturn;
	}
	
	@Override
	public String readOptionValue(String sOption) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			if(hasOption(sOption)) {
				sReturn = objOpt.readValue(sOption);
			}
		}//end main:
		return sReturn;
	}
	
	
	//### "fachliche" actions
	@Override
	public String readActionHelp() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("help");
		}//end main:		
		return sReturn;
	}
	
	@Override
	public String readActionH() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("h");
		}//end main:		
		return sReturn;
	}	
	
	@Override
	public String readPrintLevel() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("printLevel");			
		}//end main:		
		return sReturn;
	}
	
	@Override
	public String readLogLevel() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			GetOptZZZ objOpt = this.getOptObject();
			if(objOpt==null) break main;
			if(objOpt.getFlag("isLoaded")==false) break main;
			
			sReturn = objOpt.readValue("ll");			
		}//end main:		
		return sReturn;
	}
	
	//### aus ILogLevelUserZZZ
	public LOGLEVEL getLogLevelOverallEnumDefault() throws ExceptionZZZ{
		return LogSingletonZZZ.getInstance().getLogLevelOverallEnumDefault();
	}
	
	public LOGLEVEL getLogLevelOverallEnum() throws ExceptionZZZ{		
		LOGLEVEL enumLogLevelReturn = null;
		main:{
			enumLogLevelReturn = this.enumLogLevelOverall;  //Endlosschleifengefahr, darum kein getter Verwenden
		
			if(enumLogLevelReturn == null) {
				enumLogLevelReturn = this.getLogLevelOverallEnumDefault();
				
				String sReturn = this.readLogLevel();
				if(StringZZZ.isEmpty(sReturn)) {
					break main;
				}else {					
					//NUN AUS DEM WERT DAS ENUM HOLEN
					enumLogLevelReturn = EnumUtilZZZ.findEnumIgnoreCase(LOGLEVEL.class, sReturn);					
				}
			}		
		}//end main:
		return enumLogLevelReturn;
	}
	
	public void setLogLevelOverall(LOGLEVEL enumLogLevel) throws ExceptionZZZ{
		this.enumLogLevelOverall = enumLogLevel;
	}
	
	public int getLogLevelOverall() throws ExceptionZZZ{
		int iReturn = -1;
		main:{
			LOGLEVEL enumLogLevel = this.enumLogLevelOverall; //Endlosschleifengefahr, darum kein getter Verwenden
			if(enumLogLevel == null) {
				enumLogLevel = this.getLogLevelOverallEnumDefault();
				iReturn = enumLogLevel.ordinal();
				
				String sReturn = this.readLogLevel();
				if(StringZZZ.isEmpty(sReturn)) {
					break main;
				}else {
					//NUN AUS DEM WERT DAS ENUM HOLEN
					LOGLEVEL enumLogLevelByConfig = EnumUtilZZZ.findEnumIgnoreCase(LOGLEVEL.class, sReturn);
					if(enumLogLevelByConfig==null) break main;					
					iReturn = enumLogLevelByConfig.ordinal();
				}
			}else {
				iReturn = enumLogLevel.ordinal();
			}
		}//end main
		return iReturn;
	}
	
	//### aus IPrintLevelUserZZZ
	@Override
	public PRINTLEVEL getPrintLevelOverallEnumDefault() throws ExceptionZZZ{
		return SystemSingletonZZZ.getInstance().getPrintLevelOverallEnumDefault();
	}
	
	@Override
	public PRINTLEVEL getPrintLevelOverallEnum() throws ExceptionZZZ{
		PRINTLEVEL enumPrintLevelReturn = null;
		main:{
			enumPrintLevelReturn = this.enumPrintLevelOverall;  //Endlosschleifengefahr, darum kein getter Verwenden
		
			if(enumPrintLevelReturn == null) {
				enumPrintLevelReturn = this.getPrintLevelOverallEnumDefault();
				
				String sReturn = this.readPrintLevel();
				if(StringZZZ.isEmpty(sReturn)) {
					break main;
				}else {					
					//NUN AUS DEM WERT DAS ENUM HOLEN
					enumPrintLevelReturn = EnumUtilZZZ.findEnumIgnoreCase(PRINTLEVEL.class, sReturn);					
				}
			}
		
		}//end main:
		return enumPrintLevelReturn;
	}
	
	@Override
	public void setPrintLevelOverall(PRINTLEVEL enumLogLevel) throws ExceptionZZZ{
		this.enumPrintLevelOverall = enumLogLevel;
	}
	
	@Override
	public int getPrintLevelOverall() throws ExceptionZZZ{		
		int iReturn = -1;
		main:{
			PRINTLEVEL enumPrintLevel = this.enumPrintLevelOverall;  //Endlosschleifengefahr, darum kein getter Verwenden
			if(enumPrintLevel == null) {
				enumPrintLevel = this.getPrintLevelOverallEnumDefault();
				iReturn = enumPrintLevel.ordinal();
								
				String sReturn = this.readPrintLevel();
				if(StringZZZ.isEmpty(sReturn)) {
					break main;
				}else {
					//NUN AUS DEM WERT DAS ENUM HOLEN
					PRINTLEVEL enumPrintLevelByConfig = EnumUtilZZZ.findEnumIgnoreCase(PRINTLEVEL.class, sReturn);
					if(enumPrintLevelByConfig==null) break main;					
					iReturn = enumPrintLevelByConfig.ordinal();
				}
			}else {
				iReturn = enumPrintLevel.ordinal();
			}
		}//end main
		return iReturn;
	}
}
