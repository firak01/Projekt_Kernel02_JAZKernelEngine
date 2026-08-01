package basic.zBasic.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileFilterZZZ extends AbstractObjectWithFlagZZZ implements IFilenameFilterExpansionUserZZZ{
	public enum FLAGZ{
		REGARD_FILE_EXPANSION_ALL, REGARD_FILE_EXPANSION_LAST;
	}
	protected FilenamePartFilterPrefixZZZ objFilterPrefix;
	protected FilenamePartFilterMiddleZZZ objFilterMiddle;
	protected FilenamePartFilterSuffixZZZ objFilterSuffix;	
	protected FilenamePartFilterEndingZZZ objFilterEnding;
	
	protected String sOvpnContext="";
	
	protected String sPrefix="";
	protected String sMiddle="";
	protected String sSuffix="";
	protected String sEnding="";
	
	//wg. des Interfaces IFileExpansionUserZZZ
	protected IFileExpansionZZZ objExpansion = null;
	
	
	public AbstractFileFilterZZZ() throws ExceptionZZZ {
		this("");
	}		
	public AbstractFileFilterZZZ(String sOvpnContextServerOrClient) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterNew_(sOvpnContextServerOrClient, null);
	} 
	public AbstractFileFilterZZZ(String sOvpnContextServerOrClient, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractOVPNFileFilterNew_(sOvpnContextServerOrClient, saFlagControl);
	}
	public AbstractFileFilterZZZ(String sOvpnContextServerOrClient, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractOVPNFileFilterNew_(sOvpnContextServerOrClient, saFlagControlIn);
	} 
	private void AbstractOVPNFileFilterNew_(String sOvpnContextServerOrClient, String[] saFlagControlIn) throws ExceptionZZZ {
		String stemp; boolean btemp;
		main:{
		//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					   //doesn�t work. Only works when > JDK 1.4
					   //Exception e = new Exception();
					   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					   throw ez;		 
				}
			}
			}

		//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session �ber das Factory-Objekt erzeugt werden. 
		// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
		// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
		btemp = this.getFlag("init");
		if(btemp==true) break main;
		
		
		this.setOvpnContext(sOvpnContextServerOrClient);
		
//Diese Angaben gelten eben nicht für alle FileFilter, darum nicht in dieser abstrakten Elternklasse verwenden.
//		this.setPrefix(ConfigFileTemplateOvpnOVPN.sFILE_TEMPLATE_PREFIX);
//		this.setMiddle(this.getOvpnContext());

//Auch die konkreten Ausprägungen können erst in der konkreten Kindklasse gefüllt werden.		
		objFilterPrefix = new FilenamePartFilterPrefixZZZ();
		objFilterMiddle = new FilenamePartFilterMiddleZZZ();
		objFilterSuffix = new FilenamePartFilterSuffixZZZ();
		objFilterEnding = new FilenamePartFilterEndingZZZ();
		
		}//end main:		
	}
	
	@Override
	public boolean accept(File objFileDir, String sName){
		boolean bReturn=false;
		main:{
			if(sName==null) break main;				
			
			try {
				//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
				
				//Falls der OvpnContext nicht passt
				this.objFilterMiddle.setCriterion(this.getMiddle());
				if(this.objFilterMiddle.accept(objFileDir, sName)==false) break main;
		
				//Template-Dateinamen fangen eben mit einem bestimmten String an.
				this.objFilterPrefix.setCriterion(this.getPrefix());
				if(this.objFilterPrefix.accept(objFileDir, sName)==false) break main;
									
				//Falls die Endung nicht passt
				this.objFilterEnding.setCriterion(this.getEnding());
				if(this.objFilterEnding.accept(objFileDir, sName)==false) break main;
						
				//Falls das Suffix nicht passt
				if(this.getFlag(FLAGZ.REGARD_FILE_EXPANSION_ALL.name()) || (this.getFlag(FLAGZ.REGARD_FILE_EXPANSION_LAST.name()))) {
					IFileExpansionZZZ objExpansion = this.getFileExpansionObject();
					
					//TODO GOON 20200324: Berücksichtigung der "FileExpansion" 
					if(this.getFlag(FLAGZ.REGARD_FILE_EXPANSION_ALL.name())){
						//Falls das Flag Regard_FILE_EXPANSION_ALL gesetzt ist:
						//... Nur prüfen, ob hinter dem Suffix ein "Zahlenwert steht".
						
					}else if(this.getFlag(FLAGZ.REGARD_FILE_EXPANSION_LAST.name())) {
						//Falls das Flag Regard_FILE_EXPANSION_LAST gesetzt ist:
						//... Rückwärts vom maximalen Wert zu 1 gehen und den ersten gefundenen Wert zurückgeben.
						//
					}												
				}else {
					this.objFilterSuffix.setCriterion(this.getSuffix());
					if(this.objFilterSuffix.accept(objFileDir, sName)==false) break main;
				}												
				bReturn = true;
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER	
		public void setOvpnContext(String sContext) throws ExceptionZZZ {
			this.sOvpnContext=sContext;
		}
		public String getOvpnContext()throws ExceptionZZZ  {
			return this.sOvpnContext;
		}
	
		protected void setPrefix(String sPrefix)throws ExceptionZZZ {
			this.sPrefix = sPrefix;
		}
		protected String getPrefix() throws ExceptionZZZ {
			if(StringZZZ.isEmpty(this.sPrefix)) {
				this.setPrefix("");
			}
			return this.sPrefix;
		}
		
		protected void setMiddle(String sMiddle) throws ExceptionZZZ {
			this.sMiddle = sMiddle;
		}
		protected String getMiddle() throws ExceptionZZZ {
			if(StringZZZ.isEmpty(this.sMiddle)) {
				this.setMiddle("");
			}
			return this.sMiddle;
		}

		
		protected void setSuffix(String sSuffix) throws ExceptionZZZ {
			this.sSuffix = sSuffix;
		}
		protected String getSuffix() throws ExceptionZZZ {
			if(StringZZZ.isEmpty(this.sSuffix)) {
				this.setSuffix("");
			}
			return this.sSuffix;
		}
		
		protected void setEnding(String sEnding)throws ExceptionZZZ {
			this.sEnding = sEnding;
		}
		protected String getEnding() throws ExceptionZZZ {
			if(StringZZZ.isEmpty(this.sEnding)) {
				this.setEnding("");
			}
			return this.sEnding;
		}
		
		public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion)throws ExceptionZZZ  {
			this.objExpansion = objFileExpansion;
		}
		public IFileExpansionZZZ getFileExpansionObject()throws ExceptionZZZ  {
			return this.objExpansion;
		}
		
		
		//###################################################
		//### FLAG HANDLING #################################
		//###################################################
		
		//### aus IFileExpansionEnabledZZZ
		@Override
		public boolean getFlag(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.getFlag(objEnumFlag.name());
		}

		@Override
		public boolean setFlag(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}

		@Override
		public boolean[] setFlag(IFileExpansionEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}

		@Override
		public boolean proofFlagExists(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}

		@Override
		public boolean proofFlagSetBefore(IFileExpansionEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}

		//###################################
		//### FLAG CUSTOM Handling
			
		@Override
		public boolean getFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
			return this.getFlagCustom(objEnumFlag.name());
		}

		@Override
		public boolean setFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
		}

		@Override
		public boolean[] setFlagCustom(IFileExpansionEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}

		@Override
		public boolean proofFlagCustomExists(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagCustomExists(objEnumFlag.name());
		}

		@Override
		public boolean proofFlagCustomSetBefore(IFileExpansionEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagCustomSetBefore(objEnumFlag.name());
		}


		//###################################
		//### FLAGLOCAL Handling

		/* ES GIBT HIER KEIN FLAGLOCAL
		//### aus JgitEnabledZZZ	
		@Override
		public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
			return this.getFlagLocal(objEnumFlag.name());
		}

		@Override
		public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
		}

		@Override
		public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
						iCounter++;
						boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
						baReturn[iCounter]=bReturn;
					}
				}
			}//end main:
			return baReturn;
		}

		@Override
		public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagLocalExists(objEnumFlag.name());
		}

		@Override
		public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}

		*/
		
		
}//END class