/*
 * Created on 28.09.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.IKernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.FileTextImportCrackZZZ;

/**
Class, which joins more files to one single file.
+ changing the structure of the sets (e.g. make the set with the 'key'-information the first set, called 'header-set')
+ appending the current filename in the header-set
+ copying all files and the new created "join-file" to the target directory

The goal is have a valid structured file for the Z-Kernel import routine. (a lotusscript agent)

@author 0823 ,date 01.10.2004
*/
public class KernelFileTextJoinZZZ extends AbstractKernelUseObjectZZZ{
	private File objFileBase;
	private BufferedWriter objWriterBase;


	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() {
		return objKernel;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#setKernelObject(zzzKernel.custom.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
	 	this.objKernel = objKernel;
	}
	
	



	/**
	 * DESTRUCTOR
	 * 
	 * flushes + closes the Stream, used for writing !!!
	 */
	public void finalize(){
		//flush + close the stream, just for secure reasons !!!
		if(this.objWriterBase!=null){
			try {
				this.objWriterBase.flush();
				this.objWriterBase.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private boolean KernelFileTextJoinNew_(IKernelZZZ objKernelIn, KernelLogZZZ objLogIn, File objFileBaseIn, String sDirectoryIn, String sFileIn, String[] saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;
			
		String stemp;  boolean btemp;
		
		main:{
			try{
			
			this.objKernel = objKernelIn;
			this.objLog = objLogIn;
			
			if(saFlagControlIn != null){
					for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
						stemp = saFlagControlIn[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 
							   ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.", IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							   throw ez;		 
						}
					}
				}
			
				if(getFlag("init")==true) break main;
				
				if(objFileBaseIn==null){
				
					if(StringZZZ.isEmpty(sFileIn)){
					   ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'filename'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
					}
					
					if(StringZZZ.isEmpty(sDirectoryIn)){
						this.objFileBase = new File(sFileIn);					
					}else{
						this.objFileBase = new File(sDirectoryIn + File.separator + File.separator + sFileIn);
					}					
				}else{
					this.objFileBase = objFileBaseIn;
				}
				
				//proof file existance
				if(this.objFileBase.exists() == false){
					FileOutputStream objFOut = new FileOutputStream(objFileBase);
					objFOut.flush();
					objFOut.close();
					//das gibt es erst ab JDK 1.2 this.objFileBase.createNewFile();
					if(this.objFileBase.exists()==false){
						 ExceptionZZZ ez = new ExceptionZZZ( "file does not exists '" + this.objFileBase.getPath() + "'",iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
						 throw ez;							
					}						
				}
				if(this.objFileBase.isDirectory()){
				   ExceptionZZZ ez = new ExceptionZZZ( "file is a directory '" + this.objFileBase.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				   throw ez;						
				}
			
			
			//Stream for writing !!!
			FileWriter objfw = new FileWriter(this.objFileBase); //erst ab 1.4 new FileWriter(this.objFileBase, true);  //true = for append;);
			this.objWriterBase = new BufferedWriter(objfw);
			
			
			
			}catch(IOException e){
				System.out.println(e.getMessage());
				break main;
			}
					
		}//end main
		return bReturn;
	}
	
	/** CONSTRUCTOR
	 @date: 01.10.2004
	 @param objKernelIn
	 @param objLogIn
	 @param sSourceDirectoryIn
	 @param sTargetDirectoryIn
	 @param saFileSourceIn
	 @param sFileTargetIn
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public KernelFileTextJoinZZZ(
	IKernelZZZ objKernelIn,
	KernelLogZZZ objLogIn,
	String sBaseDirectoryIn, 
	String sBaseFileIn, 
	String[] saFlagControl ) throws ExceptionZZZ{
	KernelFileTextJoinNew_(objKernelIn, objLogIn, null, sBaseDirectoryIn, sBaseFileIn, saFlagControl);
	} //end constructor
	
	public KernelFileTextJoinZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, File objFile, String[] saFlagControl) throws ExceptionZZZ{
		KernelFileTextJoinNew_(objKernel, objLog, objFile, null, null, saFlagControl);
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#getLogObject()
	 */
	public KernelLogZZZ getLogObject() {
		return objLog;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#setLogObject(zzzKernel.custom.LogZZZ)
	 */
	public void setLogObject(KernelLogZZZ objLog) {
		this.objLog = objLog;		
	}
	
	public boolean appendLine(String sLineToAppend){
		boolean bReturn = false;
		main:{
			try{
		
					if(sLineToAppend!=null){
						this.objWriterBase.write(sLineToAppend);
						this.objWriterBase.newLine();
						this.objWriterBase.flush();
						
						
						bReturn = true;
					}
					
			}catch(IOException e){
				System.out.println(e.getMessage());
				break main;
			}
		}//end main:
		return bReturn;
	}
	
	public boolean append(FileTextImportCrackZZZ objCrack) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			check:{
				if(objCrack == null){ 
					   ExceptionZZZ ez = new ExceptionZZZ( "missing parameter 'FileTextCrackZZZ'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}
			}//end check
			Vector objVString = objCrack.getStringVector();
			if(objVString == null){ 
				   ExceptionZZZ ez = new ExceptionZZZ("'FileTextCrackZZZ'-Object has not loaded a file", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				   throw ez;		 
			}
				
			//append the content of the crack-object				
			String sLine = null;
			for(int iCount = 0; iCount <= objVString.size()-1; iCount++ ){
				sLine=(String)objVString.elementAt(iCount);
				 this.appendLine(sLine);
			}
				
		}//end main
		return bReturn;
	}
	
	/**
	 * 
	 * @author 0823
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	}//end class KernelFileTextJoinZZZ
