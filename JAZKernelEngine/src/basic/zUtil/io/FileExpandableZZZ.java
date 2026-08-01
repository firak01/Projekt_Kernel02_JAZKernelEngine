package basic.zUtil.io;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/**
This class extends File and not ObjectZZZ !!!
==> it inherits the methods of file and can also be used as input for printwriter/reader - objects !!!
==> it implements AssetObjectZZZ for throwing ExcetptionZZZ
==> it doesn�t implement assetKernelZZZ, because this class is used to create the log-file of the kernel.
         I never implement any functionality in an object which is used for this functionality !!!

 * @author Lindhauer
 */
public class FileExpandableZZZ extends FileZZZ implements IFileExpansionUserZZZ, IFileExpansionEnabledZZZ, IFileExpansionProxyZZZ{
	private static final long serialVersionUID = 2355847392852232484L;
	
	private IFileExpansionZZZ objExpansion=null;
	
//	### Constructor ##########################
	public FileExpandableZZZ() throws ExceptionZZZ{
		this("","","init");
	}
	
	public FileExpandableZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);
		KernelFileWithExpansionNew_(null, (String[])null);
	}
	
	public FileExpandableZZZ(String sDirectoryPath, String sFileName) throws ExceptionZZZ{
		super(sDirectoryPath, sFileName);
		KernelFileWithExpansionNew_(null, (String[])null);
	}
	
	public FileExpandableZZZ(String sDirectoryPath, String sFileName, String[] saFlagControlIn) throws ExceptionZZZ{
		super(sDirectoryPath, sFileName);
		KernelFileWithExpansionNew_(null, saFlagControlIn);
	}
	
	
	public FileExpandableZZZ(String sDirectoryPath, String sFileName, String sFlagControl) throws ExceptionZZZ{
		super(sDirectoryPath, sFileName);
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControl;			
		KernelFileWithExpansionNew_(null, saFlagControl);		 
	}

	public FileExpandableZZZ(String sDirectoryPath, String sFileName, IFileExpansionZZZ objFileExpansion, String[] saFlagControl) throws ExceptionZZZ {		
		super(sDirectoryPath, sFileName);
		KernelFileWithExpansionNew_(objFileExpansion, saFlagControl);
	}
	
	private void KernelFileWithExpansionNew_(IFileExpansionZZZ objFileExpansionIn, String[] saFlagControl) throws ExceptionZZZ {
		main:{
			if(saFlagControl!=null){
				boolean btemp = false;
				for(int icount=0;icount <= saFlagControl.length-1;icount++){
					String stemp = saFlagControl[icount];
					btemp = this.setFlag(stemp, true);
					
					if(btemp==false){ 								   
						   ExceptionZZZ ez = new ExceptionZZZ( IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						   throw ez;		 
					}
				}
				if(this.getFlag("init")) break main;
			}
			
			IFileExpansionZZZ objFileExpansion = null;
			if(objFileExpansionIn!=null) {
				objFileExpansion = objFileExpansionIn;
				this.setFlag(IFileExpansionEnabledZZZ.FLAGZ.USE_FILE_EXPANSION,true);
			}else{
				if(this.getFlag(IFileExpansionEnabledZZZ.FLAGZ.USE_FILE_EXPANSION)) {
					objFileExpansion = new FileExpansionZZZ((FileZZZ) this);							
				}
			}
			this.setFileExpansionObject(objFileExpansion);
		}//End main:
		
	}

	
	//### Acessor - Methods ###################
	public String getNameEnd(){	
		return FileEasyZZZ.NameEndCompute(this.getName());
	}
	
	public String getNameWithChangedEnd(String sEnd) throws ExceptionZZZ{
		String sFileName = this.getName();
		return FileEasyZZZ.getNameWithChangedEnd(sFileName, sEnd);	
	}
	
	//++++++++++++++++++++++++++++++++++++++++++
	public String getNameOnly() throws ExceptionZZZ{		
		return FileEasyZZZ.NameOnlyCompute(this.getName());	
	}
	
	
	/** Berechnet den nächsten Dateinamen. Dabei wird ggf. eine Zählvariable an den Dateinamen angehängt
	 * 
	 * Merke: Der Rückgabewert enthält nur den Dateinamen nicht den Pfad
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhauer; 25.02.2008 11:58:46
	 */
	public String getNameExpandedNext() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedNextCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
	
	public String getNameExpandedFirst() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedFirstCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
	
	public String getNameExpandedCurrent() throws ExceptionZZZ{
		String sReturn = null;
		main:{
			String stemp = PathNameTotalExpandedCurrentCompute();
			File objFile = new File(stemp);
			sReturn = objFile.getName();  				
		}
		return sReturn;
	}
		
	//### Functions #########################
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		@Override
		public IFileExpansionZZZ getFileExpansionObject() throws ExceptionZZZ {
			if(this.objExpansion==null) {
				IFileExpansionZZZ objExpansion = new FileExpansionZZZ(this);
				this.objExpansion = objExpansion;
			}
			return this.objExpansion;
		}

		@Override
		public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) throws ExceptionZZZ {
			this.objExpansion = objFileExpansion;
		}

		@Override
		public String searchExpansionFreeNext() throws ExceptionZZZ {
			String sReturn = new String("");		
			main:{
				IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
				if(objFileExpansion==null)break main;
				
				sReturn = objFileExpansion.searchExpansionFreeNext();
			}
			return sReturn;
		}

		@Override
		public String searchExpansionCurrent() throws ExceptionZZZ {
			String sReturn = new String("");		
			main:{
				IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
				if(objFileExpansion==null)break main;
				
				sReturn = objFileExpansion.searchExpansionCurrent();
			}
			return sReturn;
		}

		@Override
		public String searchExpansionFreeLowest() throws ExceptionZZZ {
			String sReturn = new String("");		
			main:{
				IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
				if(objFileExpansion==null)break main;
				
				sReturn = objFileExpansion.searchExpansionFreeLowest();
			}
			return sReturn;
		}
	
	
	/**
	 @param sDirectoryName
	 @param sFilePath
	 @param iExpansionLength
	 @return String, Path with filename. The filename does have the next expansion. 
	 * @throws ExceptionZZZ 
	 */
	public String PathNameTotalExpandedNextCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{					
			String sDirectoryName;
			if(StringZZZ.isEmpty(sDirectoryNameIn)){
				sDirectoryName = this.getPathDirectory();
			}else{
				sDirectoryName = sDirectoryNameIn;
			}
			
			String sFileName;	
			if(sFileNameIn == null){
				sFileName = this.getName();
			}else{
				sFileName = sFileNameIn;
			}
			
			//Leere Dateinamen können nicht "expanidert" werden.
			int iFileLength = sFileName.length();
			if(iFileLength <= 0){
				break main;
			}											 
		
 	 	
 	 		sReturn = PathNameTotalExpandedNextCompute_(sDirectoryName, sFileName);
		
		}//end main
		return sReturn;
	}
	
	public String PathNameTotalExpandedFirstCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectoryName;
		if(StringZZZ.isEmpty(sDirectoryNameIn)){
			sDirectoryName = this.getPathDirectory();
		}else{
			sDirectoryName = sDirectoryNameIn;
		}
			
		String sFileName;	
		if(sFileNameIn == null){
			sFileName = this.getName();
		}else{
			sFileName = sFileNameIn;
		}
			
		//Leere Dateinamen können nicht "expanidert" werden.
		int iFileLength = sFileName.length();
		if(iFileLength <= 0){
			break main;
		}				

 	 	
		sReturn = PathNameTotalExpandedFirstCompute_(sDirectoryName, sFileName);
		
	}//end main
	return sReturn;
}


public String PathNameTotalExpandedCurrentCompute(String sDirectoryNameIn, String sFileNameIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{		
		String sDirectoryName;
		if(StringZZZ.isEmpty(sDirectoryNameIn)) {
			sDirectoryName = this.getPathDirectory();
		}else {
			sDirectoryName = sDirectoryNameIn;
		}
				
		String sFileName;
		if(StringZZZ.isEmpty(sFileNameIn)){
			sFileName = this.getName();
		}else {
			sFileName = sFileNameIn;
		}
				
		//Leere Dateinamen können nicht "expanidert" werden.
		int iFileLength = sFileName.length();
		if(iFileLength <= 0){
			break main;
		}				

		sReturn = PathNameTotalExpandedCurrentCompute_(sDirectoryName, sFileName);
		
	}//end main
	return sReturn;
}

public String PathNameTotalExpandedFirstCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedFirstCompute_(this.getPathDirectory(), this.getName());				
} //end main
return sReturn;
}

public String PathNameTotalExpandedCurrentCompute() throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		sReturn = PathNameTotalExpandedCurrentCompute_(this.getPathDirectory(), this.getName());				
} //end main
return sReturn;
}


	
	public String PathNameTotalExpandedNextCompute() throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			sReturn = PathNameTotalExpandedNextCompute_(this.getPathDirectory(), this.getName());				
	} //end main
	return sReturn;
	}
	
	private String PathNameTotalExpandedNextCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
		String sReturn = new String("");
		main:{								
			String sDirectory;
			if(StringZZZ.isEmpty(sDirectoryIn)) {
				sDirectory = this.getPathDirectory();
			}else {
				sDirectory=sDirectoryIn;
			}
			
			String sFile; String sFileOnly;String sEnding;
			if(StringZZZ.isEmpty(sFileIn)) {
				sFile = this.getName();
				sFileOnly = this.getNameOnly();	
				sEnding = this.getNameEnd();
			}else {
				sFile = sFileIn;
				sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
				sEnding = FileEasyZZZ.NameEndCompute(this.getName());
			}			 
			if(sEnding.length() > 0){
				sEnding = "." + sEnding;
			}
			
			//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
			if(this.getFileExpansionObject()==null) {
				sReturn =  sDirectory + File.separator + sFileOnly + sEnding;							
				break main;
			}else {
				String sExpandValue = this.searchExpansionFreeNext();
				sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;						
				break main;			
			}				
		}//end main
		return sReturn;
	}
	
	private String PathNameTotalExpandedFirstCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectory;
		if(StringZZZ.isEmpty(sDirectoryIn)) {
			sDirectory = this.getPathDirectory();
		}else {
			sDirectory=sDirectoryIn;
		}
		
		String sFile; String sFileOnly;String sEnding;
		if(StringZZZ.isEmpty(sFileIn)) {
			sFile = this.getName();
			sFileOnly = this.getNameOnly();	
			sEnding = this.getNameEnd();
		}else {
			sFile = sFileIn;
			sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
			sEnding = FileEasyZZZ.NameEndCompute(this.getName());
		}	
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}

		//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
		if(this.getFileExpansionObject()==null) {
			sReturn =  sDirectory +File.separator + sFileOnly + sEnding;							
			break main;
		}else{
			String sExpandValue = this.searchExpansionFreeLowest();						
			sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;					
			break main;			
		}			 
	}//end main
	return sReturn;
}

private String PathNameTotalExpandedCurrentCompute_(String sDirectoryIn, String sFileIn) throws ExceptionZZZ{
	String sReturn = new String("");
	main:{
		String sDirectory;
		if(StringZZZ.isEmpty(sDirectoryIn)) {
			sDirectory = this.getPathDirectory();
		}else {
			sDirectory=sDirectoryIn;
		}
		
		String sFile; String sFileOnly;String sEnding;
		if(StringZZZ.isEmpty(sFileIn)) {
			sFile = this.getName();
			sFileOnly = this.getNameOnly();	
			sEnding = this.getNameEnd();
		}else {
			sFile = sFileIn;
			sFileOnly = FileEasyZZZ.NameOnlyCompute(sFile);
			sEnding = FileEasyZZZ.NameEndCompute(this.getName());
		}	
		if(sEnding.length() > 0){
			sEnding = "." + sEnding;
		}

		//Merke: Nur mit dem Flag "use_file_expansion" ist das Expansion Objekt normalerweise gefüllt.			
		if(this.getFileExpansionObject()==null) {
			sReturn =  sDirectory + File.separator + sFileOnly + sEnding;						
			break main;
		}else{
			String sExpandValue = this.searchExpansionCurrent();
			sReturn = sDirectory + File.separator + sFileOnly + sExpandValue + sEnding;				
			break main;			
		}			 
	}//end main
	return sReturn;
}	
	
	/** void, An expansion has a fixed length. This character is used to fill the missing charakters of a given expansion-number.
	 * Default is '0' ---> e.g. "0001"
	 * but it is possible to change this to e.g.  '-'  --->  "---1"
	 * 
	* Lindhauer; 22.04.2006 07:20:14
	 * @param cExpansionFilling
	 */
	public void setExpansionFilling(char cExpansionFilling) throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			this.getFileExpansionObject().setExpansionFilling(cExpansionFilling);
		}
	}
	
	public void setExpansionFilling(String sExpansionFilling) throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			 this.getFileExpansionObject().setExpansionFilling(sExpansionFilling);
		}
	}
	
	public String getExpansionFilling()  throws ExceptionZZZ{
		String sReturn = new String("");
		main:{
			if(this.getFileExpansionObject()!=null) {
				sReturn = this.getFileExpansionObject().getExpansionFilling();
			}
		}//end main:
		return sReturn;
	}
	

	


	
	public int getExpansionLength() throws ExceptionZZZ{	
		if(this.getFileExpansionObject()!=null) {
			return this.getFileExpansionObject().getExpansionLength();
		}else {
			return 0;
		}
	}

	public void setExpansionLength(int iExpansionLength)throws ExceptionZZZ{
		if(this.getFileExpansionObject()!=null) {
			this.getFileExpansionObject().setExpansionLength(iExpansionLength);
		}else {
			  ExceptionZZZ ez = new ExceptionZZZ( iERROR_PROPERTY_MISSING + " kein ExpansionObjekt vorhanden ", iERROR_PROPERTY_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
			   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
			   throw ez;		
		}
	}

	/** 
	 @param sFilling
	 @param iEndingValue
	 @param iEndingLength
	 @return String Expansion, e.g. '001'
	 * @throws ExceptionZZZ 
	 */
	public String ExpansionCompute(String sFilling, int iExpansionValue) throws ExceptionZZZ {
		String sReturn = new String("");		
		main:{
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			if(objFileExpansion==null)break main;
				
			sReturn = objFileExpansion.computeExpansion(sFilling, iExpansionValue);			
		}//end main
		return sReturn;
	} //end function
	
	
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
	
}//end class
