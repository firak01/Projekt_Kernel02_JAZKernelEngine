/*
 * Created on 25.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.csv.CSVReader;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.KernelLogZZZ;

/**

@author 0823 ,date 25.10.2004
*/
public class KernelFileCsvZZZ  extends AbstractKernelUseObjectZZZ{
	private CSVReader objCSV;
	private File objFile;

	public KernelFileCsvZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, String sDirectory, String sFilename, String[] saFlagControl) throws ExceptionZZZ{
		KernelFileCsvNew_(objKernel, objLog, null, sDirectory, sFilename, saFlagControl);
	}

	/** 
	
	 @author 0823 , date: 25.10.2004
	 @param objKernel
	 @param objLog
	 @param object
	 @param sDirectory
	 @param sFilename
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileCsvNew_(IKernelZZZ objKernelIn, KernelLogZZZ objLogIn, File objFileIn, String sDirectoryIn, String sFileIn, String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		String stemp; boolean btemp; 
		main:{
			this.objKernel = objKernelIn;
			if(objLogIn == null){
				objLog = this.objKernel.getLogObject();
			}else{
				this.objLog = objLogIn;
			 }
					 
			   //setzen der �bergebenen Flags	
				   if(saFlagControlIn != null){
					   for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						   stemp = saFlagControlIn[iCount];
						   btemp = setFlag(stemp, true);
						   if(btemp==false){
							   ExceptionZZZ ez = new ExceptionZZZ( "the flag '" + stemp + "' is not available.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							   throw ez;		 
						   }
					   }
				   }
				   if(this.getFlag("init")==true){
				   	bReturn = true;
				   	break main;
				   }
	 			 				
			File objFile = null;	 	
			   if(objFileIn==null & (sDirectoryIn==null | StringZZZ.isEmpty(sFileIn))){
				   ExceptionZZZ ez = new ExceptionZZZ("Missing Parameter File-Object or File-Path information", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez; 
			   }
				
			   if(objFileIn !=null){
				   if(objFileIn.exists()==false){
					   ExceptionZZZ ez = new ExceptionZZZ("File not found '" + objFileIn.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 
				   }
				   if(objFileIn.isDirectory()){
					   ExceptionZZZ ez = new ExceptionZZZ("Parameter File is a directory: '" + objFileIn.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 
				   }
				   objFile = objFileIn;
					
			   }else if(sFileIn.equals("")){
					   ExceptionZZZ ez = new ExceptionZZZ("Parameter Filename is empty: '" + objFile.getPath() + "'",iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 
			   }else{
				   if(StringZZZ.isEmpty(sDirectoryIn)){
					   objFile = new File(sFileIn);
				   }else{
					   objFile = new File(sDirectoryIn + File.separator + File.separator + sFileIn);
					   }				
			   }
			
			   //create the csv-file-object from file-object
			  	this.objFile = objFile;
			  	this.load();

		   }//end main:
		   return bReturn;
	}
	
	public boolean load() throws ExceptionZZZ{
				boolean bReturn = false;
				main:{
					try{
			
			
					check:{
						//proof file existance
						if(this.objFile.exists() == false){
						   ExceptionZZZ ez = new ExceptionZZZ( "file does not exists '" + this.objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						   throw ez;							
						}						
						if(this.objFile.isDirectory()){
						   ExceptionZZZ ez = new ExceptionZZZ( "file is a directory '" + this.objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						   throw ez;						
						}
					}//end check:
				
					//create streams
					FileReader objFRead = new FileReader(this.objFile);
					CSVReader objCSV = new CSVReader(objFRead, ';');
					this.objCSV = objCSV;

					}catch(FileNotFoundException e){
						System.out.println(e.getMessage());
					}

				}//end main:
				return bReturn;
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
		this.objKernel= objKernel;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#getLogObject()
	 */
	public KernelLogZZZ getLogObject() {
		return this.objLog;
	}
	
	public File getFileObject(){
		return this.objFile;
	}
	
	public void setFileObject(File objFile) throws ExceptionZZZ{
		this.objFile = objFile;
		load();
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#setLogObject(zzzKernel.custom.LogZZZ)
	 */
	public void setLogObject(KernelLogZZZ objLog) {
		this.objLog = objLog;
	}
	

	/** 
	* @return CSVReader
	* 
	* lindhaueradmin; 19.10.2006 09:36:12
	 */
	public CSVReader getCSVObject() {
		return objCSV;
	}

	/**
	* @return void
	* @param reader 
	* 
	* lindhaueradmin; 19.10.2006 09:35:39
	 */
	public void setCSVObject(CSVReader reader) {
		objCSV = reader;
	}


	public Hashtable getNextLine(){
			return this.getCSVObject().getNextLine();
	}
}//end class KernelFileCsvZZZ
