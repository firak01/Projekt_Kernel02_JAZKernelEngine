package basic.zBasic.util.file.jar.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.IJarEasyConstantsZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterNameZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathTotalZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileFilePartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFilenamePartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileFileFilterInJarZZZ extends AbstractObjectWithFlagZZZ implements IFileFilePartFilterZipUserZZZ, ZipEntryFilter,IFileExpansionEnabledZZZ{		
	protected FilenamePartFilterPathZipZZZ objFilterPath=null; //Der Dateipfad
	protected FilenamePartFilterNameZipZZZ objFilterName=null; //Der ganze Name
	protected FilenamePartFilterPathTotalZipZZZ objFilterPathTotal=null;//Der Dateipfad plus den ganzen Namen
	
	//Weitere Filter werden nicht über den Konstruktor gesetzt.
	protected FilenamePartFilterPrefixZipZZZ objFilterPrefix=null;;
	protected FilenamePartFilterMiddleZipZZZ objFilterMiddle=null;;
	protected FilenamePartFilterSuffixZipZZZ objFilterSuffix=null;;	
	protected FilenamePartFilterEndingZipZZZ objFilterEnding=null;;
		
	//wg. des Interfaces IFileExpansionUserZZZ
	protected IFileExpansionZZZ objExpansion = null;
	
	
	public AbstractFileFileFilterInJarZZZ() throws ExceptionZZZ {
		this("","");
	}		
	public AbstractFileFileFilterInJarZZZ(String sFileName) throws ExceptionZZZ {
		super();
		AbstractFileFilterInJarNew_(null, sFileName, null);
	}
	public AbstractFileFileFilterInJarZZZ(String sDirectoryName, String sFileName) throws ExceptionZZZ {
		super();
		AbstractFileFilterInJarNew_(sDirectoryName, sFileName, null);
	} 
	public AbstractFileFileFilterInJarZZZ(String sDirectoryName, String sFileName, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractFileFilterInJarNew_(sDirectoryName, sFileName, saFlagControl);
	}
	public AbstractFileFileFilterInJarZZZ(String sDirectoryName, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractFileFilterInJarNew_(sDirectoryName, sFileName, saFlagControlIn);
	} 
	private void AbstractFileFilterInJarNew_(String sDirectoryName, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ {
		String stemp; boolean btemp; String sLog;
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
			
			if(StringZZZ.isEmpty(sDirectoryName)) {	
				sLog = ReflectCodeZZZ.getPositionCurrent()+" Rechne das verwendete Verzeichnis ggfs. aus dem Dateinamen aus.";
				System.out.println(sLog);
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+" Variable: sFileName='"+sFileName+"'";
				System.out.println(sLog);
				
				String stempDirectory = JarEasyUtilZZZ.computeDirectoryFromJarPath(sFileName);
				sLog = ReflectCodeZZZ.getPositionCurrent()+" Variable: stempDirectory='"+stempDirectory+"'";
				System.out.println(sLog);
				this.setDirectoryPath(stempDirectory);
				
				String stempName = JarEasyUtilZZZ.computeFilenameFromJarPath(sFileName);
				sLog = ReflectCodeZZZ.getPositionCurrent()+" Variable: stempName='"+stempName+"'";
				System.out.println(sLog);
				this.setName(stempName);
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()+" Verzeichnis und Dateiname uebergeben.  Variable: sDirectoryName='"+sDirectoryName+"' | sFileName='"+ sFileName+ "'";
				System.out.println(sLog);

				this.setDirectoryPath(sDirectoryName);
				this.setName(sFileName);
			}		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			try {
			
				String sName = ze.getName();
				if(ze.isDirectory()) {
					System.out.println("STOP: REINES VERZEICHNIS "+ze.getName());
					
					if(StringZZZ.contains(sName,"template")) {
						System.out.println("STOP: BREAKPOINT01");
						System.out.println("------");
					}
					
					if(StringZZZ.contains(sName,"subDirectory01")) {
						System.out.println("STOP: BREAKPOINT02");
						System.out.println("------");
					}
					
					if(StringZZZ.contains(sName,"subDirectory03")) {
						System.out.println("STOP: BREAKPOINT03");
						System.out.println("------");
					}
					
					if(StringZZZ.contains(sName,"subDirectory04withoutFiles")) {
						System.out.println("STOP: BREAKPOINT04");
						System.out.println("------");
					}
					
				}
				
				//Falls das Verzeichnis nicht passt	
				if(!StringZZZ.isEmpty(this.getDirectoryPath())){
					this.getDirectoryPartFilter().setCriterion(this.getDirectoryPath());
					if(this.getDirectoryPartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}
				
				if(!StringZZZ.isEmpty(this.getName())){
					this.getNamePartFilter().setCriterion(this.getName());
					if(this.getNamePartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}
				
				//Falls der OvpnContext nicht passt
				if(!StringZZZ.isEmpty(this.getMiddle())) {
					this.getMiddlePartFilter().setCriterion(this.getMiddle());
					if(this.getMiddlePartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}
		
				//Template-Dateinamen fangen eben mit einem bestimmten String an.
				if(!StringZZZ.isEmpty(this.getPrefix())) {
					this.getPrefixPartFilter().setCriterion(this.getPrefix());
					if(this.getPrefixPartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}
									
				//Falls die Endung nicht passt
				if(!StringZZZ.isEmpty(this.getEnding())) {
					this.getEndingPartFilter().setCriterion(this.getEnding());
					if(this.getEndingPartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}
						
				//Falls das Suffix nicht passt
				if(!StringZZZ.isEmpty(this.getSuffix())) {
					this.getSuffixPartFilter().setCriterion(this.getSuffix());
					if(this.getSuffixPartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
				}				
			}catch(ExceptionZZZ ez) {
				String sLog = "AbstractFileFilterInJarZZZ -> ExceptionZZZ: '" + ez.getMessageLast() +"'.";
				System.out.println(sLog);
			}
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER
	public void setDirectoryPartFilter(FilenamePartFilterPathZipZZZ objDirectoryFilterZip) {
		this.objFilterPath = objDirectoryFilterZip;
	}
	public FilenamePartFilterPathZipZZZ getDirectoryPartFilter() {
		if(this.objFilterPath==null) {
			this.objFilterPath = new FilenamePartFilterPathZipZZZ(); //Das Verzeichnis
		}
		return this.objFilterPath;
	}
	
	public void setNamePartFilter(FilenamePartFilterNameZipZZZ objNameFilterZip) {
		this.objFilterName = objNameFilterZip;
	}
	public FilenamePartFilterNameZipZZZ getNamePartFilter() {
		if(this.objFilterName==null) {
			this.objFilterName = new FilenamePartFilterNameZipZZZ();
		}
		return this.objFilterName;
	}
	
	public void setPathTotalFilter(FilenamePartFilterPathTotalZipZZZ objFilterPathTotalZip) {
		this.objFilterPathTotal = objFilterPathTotalZip;
	}
	public FilenamePartFilterPathTotalZipZZZ getPathTotalFilter() {
		if(this.objFilterPathTotal==null) {
			this.objFilterPathTotal = new FilenamePartFilterPathTotalZipZZZ();
		}
		return this.objFilterPathTotal;
	}
	
	public void setPrefixPartFilter(FilenamePartFilterPrefixZipZZZ objPrefixFilterZip) {
		this.objFilterPrefix = objPrefixFilterZip;
	}
	public FilenamePartFilterPrefixZipZZZ getPrefixPartFilter() {
		if(this.objFilterPrefix==null) {
			this.objFilterPrefix = new FilenamePartFilterPrefixZipZZZ();
		}
		return this.objFilterPrefix;
	}
		
	public void setMiddlePartFilter(FilenamePartFilterMiddleZipZZZ objMiddleFilterZip) {
		this.objFilterMiddle = objMiddleFilterZip;
	}
	public FilenamePartFilterMiddleZipZZZ getMiddlePartFilter() {
		if(this.objFilterMiddle==null) {
			this.objFilterMiddle = new FilenamePartFilterMiddleZipZZZ();
		}
		return this.objFilterMiddle;
	}
	
	
	public void setSuffixPartFilter(FilenamePartFilterSuffixZipZZZ objSuffixFilterZip) {
		this.objFilterSuffix = objSuffixFilterZip;
	}
	public FilenamePartFilterSuffixZipZZZ getSuffixPartFilter() {
		if(this.objFilterSuffix==null) {
			this.objFilterSuffix = new FilenamePartFilterSuffixZipZZZ();
		}
		return this.objFilterSuffix;
	}
	
	public void setEndingPartFilter(FilenamePartFilterEndingZipZZZ objEndingFilterZip) {
		this.objFilterEnding = objEndingFilterZip;
	}
	public FilenamePartFilterEndingZipZZZ getEndingPartFilter() {
		if(this.objFilterEnding==null) {
			this.objFilterEnding = new FilenamePartFilterEndingZipZZZ();
		}
		return this.objFilterEnding;
	}
	
	
		protected void setDirectoryPath(String sDirectoryPath) throws ExceptionZZZ {
			this.getDirectoryPartFilter().setDirectoryPath(sDirectoryPath);	
			this.getPathTotalFilter().setDirectoryPath(sDirectoryPath);			
		}
		protected String getDirectoryPath() {
			return this.getDirectoryPartFilter().getDirectoryPath();
		}
		
		protected void setName(String sName) throws ExceptionZZZ {
			this.getNamePartFilter().setFileName(sName);
			this.getPathTotalFilter().setFileName(sName);
			
		}
		protected String getName() {
			return this.getNamePartFilter().getFileName();
		}
		
		protected void setPrefix(String sPrefix) throws ExceptionZZZ {
			this.getPrefixPartFilter().setPrefix(sPrefix);
		}
		protected String getPrefix() {
			return this.getPrefixPartFilter().getPrefix();
		}
		
		protected void setMiddle(String sMiddle) throws ExceptionZZZ {
			this.getMiddlePartFilter().setMiddle(sMiddle);
		}
		protected String getMiddle() {
			return this.getMiddlePartFilter().getMiddle();
		}

		
		protected void setSuffix(String sSuffix) throws ExceptionZZZ {
			this.getSuffixPartFilter().setSuffix(sSuffix);
		}
		protected String getSuffix() {
			return this.getSuffixPartFilter().getSuffix();
		}
		
		protected void setEnding(String sEnding) throws ExceptionZZZ{
			this.getEndingPartFilter().setEnding(sEnding);
		}
		protected String getEnding() {
			return this.getEndingPartFilter().getEnding();
		}				
		
		public IFileExpansionZZZ getFileExpansionObject() {
			return this.objExpansion;
		}
		public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) {
			this.objExpansion = objFileExpansion;
		}
		
		public IFilenamePartFilterZipZZZ computeFilePartFilterUsed() throws ExceptionZZZ{
			IFilenamePartFilterZipZZZ objReturn = null;		
			main:{
	
				IFilenamePartFilterZipZZZ objPartFilterName = this.getNamePartFilter();
				IFilenamePartFilterZipZZZ objPartFilterDirectory = this.getDirectoryPartFilter();
				 
				String sDirectoryPath = objPartFilterDirectory.getCriterion();
				String sName = objPartFilterName.getCriterion();
				
				//A) Kompletter Dateinamensfilter (ggfs. mit Verzeichnis)			 
				if((!StringZZZ.isEmpty(sName) && !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && StringZZZ.isEmpty(sDirectoryPath)){
					objReturn = objPartFilterName;
					break main;
				}
				
				//B) REINER VERZEICHNIS-FILTER
				if((StringZZZ.isEmpty(sName) ||  sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = objPartFilterDirectory;
					break main;
				}
				
				//C) Dateinamesfilter mit Verzeichnis rulez
				if((!StringZZZ.isEmpty(sName)&& !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = this.getPathTotalFilter();		
					break main;
				}
			}//end main:
			return objReturn;
		}
		
		public String computeCriterionInJarUsed() throws ExceptionZZZ {
			String objReturn = null;		
			main:{				
				IFilenamePartFilterZipZZZ objPartFilterName = this.getNamePartFilter();
				IFilenamePartFilterZipZZZ objPartFilterDirectory = this.getDirectoryPartFilter();
				 
				
				
				//A) Reiner Dateinamensfilter (ohne Verzeichnis)			 
				if(!StringZZZ.isEmpty(objPartFilterName.getCriterion()) && StringZZZ.isEmpty(objPartFilterDirectory.getCriterion())) {
					objReturn = objPartFilterName.getCriterion();
					break main;
				}
				
				//B) VERZEICHNIS-FILTER
				if(StringZZZ.isEmpty(objPartFilterName.getCriterion()) && !StringZZZ.isEmpty(objPartFilterDirectory.getCriterion())) {
					objReturn = objPartFilterDirectory.getCriterion();
					break main;
				}
				
				//C) Kompletter Dateinamensfilter (mit Verzeichnis)	rulez
				if(!StringZZZ.isEmpty(objPartFilterName.getCriterion()) && !StringZZZ.isEmpty(objPartFilterDirectory.getCriterion())) {
					FilenamePartFilterPathTotalZipZZZ objPartFilterPathTotal = this.getPathTotalFilter();
					objReturn = objPartFilterPathTotal.getCriterion();
					break main;
				}
			}//end main:
			return objReturn;
		}
		
		public String computeDirectoryPathInJarUsed() throws ExceptionZZZ {
			String objReturn = null;		
			main:{					
				IFilenamePartFilterZipZZZ objPartFilterName = this.getNamePartFilter();
				IFilenamePartFilterZipZZZ objPartFilterDirectory = this.getDirectoryPartFilter();
				 
				String sDirectoryPath = objPartFilterDirectory.getCriterion();
				String sName = objPartFilterName.getCriterion();
				
				//A) Reiner Dateinamensfilter (ohne Verzeichnis)			 
				if ((!StringZZZ.isEmpty(sName) && !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR))  && StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = sName;
					objReturn = JarEasyUtilZZZ.computeDirectoryFromJarPath(objReturn);
					break main;
				}
				
				//B) VERZEICHNIS-FILTER
				if((StringZZZ.isEmpty(sName) ||  sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = sDirectoryPath;
					objReturn = JarEasyUtilZZZ.computeDirectoryFromJarPath(objReturn);
					break main;
				}
				
				//C) Kompletter Dateinamensfilter (mit Verzeichnis)	rulez
				if((!StringZZZ.isEmpty(sName)&& !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					FilenamePartFilterPathTotalZipZZZ objPartFilterPathTotal = this.getPathTotalFilter();
					objReturn = objPartFilterPathTotal.getDirectoryPath();
					break main;
				}
			}//end main:
			return objReturn;
		}
		
		public String computeFileNameInJarUsed() throws ExceptionZZZ {
			String objReturn = null;		
			main:{					
				IFilenamePartFilterZipZZZ objPartFilterName = this.getNamePartFilter();
				IFilenamePartFilterZipZZZ objPartFilterDirectory = this.getDirectoryPartFilter();
				 
				String sDirectoryPath = objPartFilterDirectory.getCriterion();
				String sName = objPartFilterName.getCriterion();
				
				//A) Reiner Dateinamensfilter (ohne Verzeichnis)			 
				if ((!StringZZZ.isEmpty(sName) && !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR))  && StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = sName;
					objReturn = JarEasyUtilZZZ.computeFilenameFromJarPath(objReturn);
					break main;
				}
				
				//B) VERZEICHNIS-FILTER
				if((StringZZZ.isEmpty(sName) ||  sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					objReturn = sDirectoryPath;
					objReturn = JarEasyUtilZZZ.computeFilenameFromJarPath(objReturn);
					break main;
				}
				
				//C) Kompletter Dateinamensfilter (mit Verzeichnis)	rulez
				if((!StringZZZ.isEmpty(sName)&& !sName.equals(IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR)) && !StringZZZ.isEmpty(sDirectoryPath)) {
					FilenamePartFilterPathTotalZipZZZ objPartFilterPathTotal = this.getPathTotalFilter();
					objReturn = objPartFilterPathTotal.getFileName();
					break main;
				}
			}//end main:
			return objReturn;
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