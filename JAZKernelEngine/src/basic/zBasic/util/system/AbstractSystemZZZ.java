package basic.zBasic.util.system;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import custom.zKernel.ConfigZZZ;
import custom.zKernel.ILogLevelUserZZZ;
import custom.zKernel.ILogLevelUserZZZ.LOGLEVEL;

public abstract class AbstractSystemZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements ISystemZZZ{
	private static final long serialVersionUID = -41535753990842671L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
	
	
	// --- Globale Objekte ---	
	//Die Liste der Spaltenseparatoren, das aktuelle Format kann sich ja ggfs. aendern (je nachdem welche Log-Funktion verwendet wird),
	//die Reihenfolge der Spalten (markiert durch die Separatoren) sollte nicht veraendert werden. Sonst bekommt man in den buendig gemachten Zeilen grosse Luecken.
	//Neue Separatoren werden in diese Liste reingemischt (ArrayListUtilZZZ.merge...)
	//Z.B: protected volatile ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator=null;
	
	protected volatile IKernelConfigZZZ objConfig = null;   //die Werte für den Applikationskey, Systemnummer, etc.
	protected volatile IPrintLevelUserZZZ.PRINTLEVEL enumPrintLevelOverall = null; //Wieviel ausgedruckt werden soll 3=Debug All, 0 = NONE;
	
	
	//als private deklariert, damit man es nicht so instanzieren kann, sondern die Methode .getInstance() verwenden muss		
	protected AbstractSystemZZZ() throws ExceptionZZZ{
		super();
	}
	
	
	//### aus ISystemZZZ
	//++++++++++++++ Ohne Zeilenumbruch
	@Override
	public void print(String s, boolean bPrintOutput) throws ExceptionZZZ{
		main:{
			if(!bPrintOutput) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.print(s);
		}//end main:
	}
	
	
	@Override
	public void print(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ{
		main:{
			if(enumLogLevel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("enumLogLevel", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			int iPrintLevelAllowed = this.getPrintLevelOverall(); //analog zu dem Code in log.writeLine
			int iLogLevelUsed = enumLogLevel.ordinal();
			if(iLogLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.print(s);
		}//end main:
	}
	
	@Override
	public void print(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ{
		main:{
			if(enumPrintLevel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("enumPrintLevel", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			int iPrintLevelAllowed = this.getPrintLevelOverall();
			int iPrintLevelUsed = enumPrintLevel.ordinal();
			if(iPrintLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.print(s);
		}//end main:
	}
	
	@Override
	public void print(String s, int iPrintLevelUsed) throws ExceptionZZZ{
		main:{
			int iPrintLevelAllowed = this.getPrintLevelOverall();
			if(iPrintLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.print(s);
		}//end main:
	}
	
	
	//++++++++++++++ Mit Zeilenumbruch
	@Override
	public void println(String s, boolean bPrintOutput) throws ExceptionZZZ{
		main:{
			if(!bPrintOutput) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.println(s);
		}//end main:
	}
	
	
	@Override
	public void println(String s, LOGLEVEL enumLogLevel) throws ExceptionZZZ{
		main:{
			if(enumLogLevel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("enumLogLevel", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			int iPrintLevelAllowed = this.getPrintLevelOverall(); //analog zu dem Code in log.writeLine
			int iLogLevelUsed = enumLogLevel.ordinal();
			if(iLogLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.println(s);
		}//end main:
	}
	
	@Override
	public void println(String s, PRINTLEVEL enumPrintLevel) throws ExceptionZZZ{
		main:{
			if(enumPrintLevel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("enumPrintLevel", iERROR_PARAMETER_MISSING, AbstractKernelLogZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		
			int iPrintLevelAllowed = this.getPrintLevelOverall();
			int iPrintLevelUsed = enumPrintLevel.ordinal();
			if(iPrintLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.println(s);
		}//end main:
	}
	
	@Override
	public void println(String s, int iPrintLevelUsed) throws ExceptionZZZ{
		main:{
			int iPrintLevelAllowed = this.getPrintLevelOverall();
			if(iPrintLevelUsed > iPrintLevelAllowed) break main;		
			if(StringZZZ.isEmptyTrimmed(s)) break main;
			
			System.out.println(s);
		}//end main:
	}
	
	
	//### aus IPrintLevelUserZZZ
	@Override
	public PRINTLEVEL getPrintLevelOverallEnumDefault() throws ExceptionZZZ{
		return PRINTLEVEL.INFO;
	}
	
	@Override
	public PRINTLEVEL getPrintLevelOverallEnum() throws ExceptionZZZ{
		PRINTLEVEL enumLogLevel = this.enumPrintLevelOverall;
		if(enumLogLevel==null) {
			IKernelConfigZZZ objConfig = this.getConfigObject();
			if(objConfig!=null) {
				PRINTLEVEL enumPrintLevelByConfig = objConfig.getPrintLevelOverallEnum();
				this.enumPrintLevelOverall = enumPrintLevelByConfig;
			}
			
			if(this.enumPrintLevelOverall==null) {
				PRINTLEVEL enumPrintLevelDefault = this.getPrintLevelOverallEnumDefault();
				this.enumPrintLevelOverall = enumPrintLevelDefault;
			}
		}
		return this.enumPrintLevelOverall;
	}
	
	@Override
	public void setPrintLevelOverall(PRINTLEVEL enumLogLevel) throws ExceptionZZZ{
		this.enumPrintLevelOverall = enumLogLevel;
	}
	
	@Override
	public int getPrintLevelOverall() throws ExceptionZZZ{
		int iReturn = -1;
		main:{
			PRINTLEVEL enumPrintLevel = this.getPrintLevelOverallEnum();
			if(enumPrintLevel == null) {
				PRINTLEVEL enumPrintLevelDefault = this.getPrintLevelOverallEnumDefault();
				this.enumPrintLevelOverall = enumPrintLevelDefault;
			}
			iReturn = this.enumPrintLevelOverall.ordinal();
		}//end main:
		return iReturn;
	}
	
	//### aus IKernelConfigUserZZZ
	@Override
	public IKernelConfigZZZ getConfigObject() throws ExceptionZZZ{
		if(this.objConfig==null){
			this.objConfig = new ConfigZZZ(null);			
		}
		return this.objConfig;
	}
	
	@Override
	public void setConfigObject(IKernelConfigZZZ objConfig){
		this.objConfig = objConfig;
	}
	
	//###########################################
	//### FLAG HANDLING
	//###########################################
	
	//### IListenerObjectFlagZsetZZZ
	//Der FormatManager soll hinsichtlich der Flags von z.B. LogZZZ gesteuert werden koennen. Also wenn registriert, dann dort gesetzte Flags uebernehmen.
	@Override
	public boolean getFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectFlagZsetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
		
	@Override
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventFlagZset==null) break main;
			
			//Wenn das Objekt ueber die Aenderung des Setzen des Flags informiert wird. 
			//Dieses Setzen des Flags ggfs. nachvollziehen.
			String sFlagText = eventFlagZset.getFlagText();
			boolean bFlagValue = eventFlagZset.getFlagValue();
			
			try {
				bReturn = this.setFlag(sFlagText, bFlagValue);
			} catch (ExceptionZZZ e) {
				//Falls es das Flag hier nicht gibt, wird die Exception hier nicht weitergeworfen.
				//Es kann aber auch ggfs. anders verfahren werden. 
			}
			
		}//end main:
		return bReturn;
	}
	
	
	//###################################################
	//### FLAG: ISystemEnabledZZZ
	//###################################################
	
	//#######################################
	//### FLAGZ
	@Override
	public boolean getFlag(ISystemEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(ISystemEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(ISystemEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ISystemEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(ISystemEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(ISystemEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
	
	//###################################
	//### FLAGLOCAL Handling

	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(ISystemEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(ISystemEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(ISystemEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ISystemEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(ISystemEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(ISystemEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalSetBefore(objEnumFlag.name());
	}

	//###################################
	//### FLAG CUSTOM Handling
		
	@Override
	public boolean getFlagCustom(ISystemEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());
	}

	@Override
	public boolean setFlagCustom(ISystemEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(ISystemEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ISystemEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(ISystemEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(ISystemEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}
}
