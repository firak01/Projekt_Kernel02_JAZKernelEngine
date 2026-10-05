/*
 * Created on 24.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.KernelLogZZZ;

/**

@author 0823 ,date 24.10.2004
*/
public class KernelFileTextExportCrackZZZ extends AbstractKernelUseObjectZZZ{
	private String sProgramAlias;
    private File objFile;

	
	public KernelFileTextExportCrackZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, File objFileToCrack, String sProgramAlias, String[] saFlagControl) throws ExceptionZZZ{
		KernelFileTextExportCrackNew_(objKernel, objLog, objFileToCrack, null, null, sProgramAlias, saFlagControl);		
	}
	
	
	public boolean crackCustom() throws ExceptionZZZ{
				//String sFunction = new String("");
		main:{
			ExceptionZZZ ez = new ExceptionZZZ("this function has to be overwritten by a function of the include package", iERROR_ZFRAME_METHOD, this, ReflectCodeZZZ.getMethodCurrentName()); 
			//doesn�t work. Only works when > JDK 1.4
			//Exception e = new Exception();
			//ExceptionZZZ ez = new ExceptionZZZ("Parameter missing: KernelObject", 101,this, e, "");
			throw ez;
		}
		//return sFunction;
			}
			
	
	
	
	
	/** 
	
	 @author 0823 , date: 24.10.2004
	 @param objKernel
	 @param objLog
	 @param objFile
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileTextExportCrackNew_(IKernelZZZ objKernelIn, KernelLogZZZ objLogIn, File objFileIn, String sDirIn, String sFileIn, String sProgramAliasIn, String[] saFlagControlIn) throws ExceptionZZZ{
		boolean bFunction = false;
		main:{
						String stemp; boolean btemp;
						this.objKernel = objKernelIn;
						this.objLog = objLogIn;

						if(saFlagControlIn != null){
								for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
									stemp = saFlagControlIn[iCount];
									btemp = setFlag(stemp, true);
									if(btemp==false){ 
											stemp = "the flag '" + stemp + "' is not available.";
										   ExceptionZZZ ez = new ExceptionZZZ(stemp,IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
										   //doesn't work. Only works when > JDK 1.4
										   //Exception e = new Exception();
										   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
										   throw ez;		 
									}
								}
							}
				
							if(getFlag("init")==true) break main;
													
							
							//Find the file, read the content to an array, crack it.
							if(objFileIn==null){				
											if(StringZZZ.isEmpty(sFileIn)){										
											   ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'filename'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
											   //doesn't work. Only works when > JDK 1.4
											   //Exception e = new Exception();
											   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");										
											   throw ez;		 
											}
					
											if(StringZZZ.isEmpty(sDirIn)){
												this.objFile= new File(sFileIn);									
											}else{
												this.objFile = new File(sDirIn + File.separator + sFileIn);
											}					
										}else{
											this.objFile = objFileIn;
										}
										
										if (StringZZZ.isEmpty(sProgramAliasIn)){
										   ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'program-alias'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
										   throw ez;		 											
										}else{
											this.sProgramAlias = sProgramAliasIn;
										}
										
					}//end main:
		return bFunction;
	}



	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() {
		return this.objKernel;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#setKernelObject(zzzKernel.custom.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel = objKernel;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#getLogObject()
	 */
	public KernelLogZZZ getLogObject() {
		return this.objLog;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#setLogObject(zzzKernel.custom.LogZZZ)
	 */
	public void setLogObject(KernelLogZZZ objLog) {
		this.objLog = objLog;		
	}


	/** 
	
	 @author 0823 , date: 24.10.2004
	 @return
	 */
	public File getFileObject() {
		return objFile;
	}
	

	/** 
	
	 @author 0823 , date: 24.10.2004
	 @param file
	 */
	public void setFileObject(File file) {
		objFile = file;
	}


	/** 
	
	 @author 0823 , date: 26.10.2004
	 @return
	 */
	public String getProgramAliasCurrent() {
		return this.sProgramAlias;
	}



}
