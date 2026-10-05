/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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


/**

@author 0823 ,date 07.10.2004
*/
public class KernelFileTextImportCrackZZZ extends AbstractKernelUseObjectZZZ{
		private Vector objVString;
		private File objFile;


	public KernelFileTextImportCrackZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, File objFile, String[] saFlagControl) throws ExceptionZZZ{
			KernelFileTextImportCrackNew_(objKernel, objLog, objFile, null, null, saFlagControl);
		}
		
		public boolean load() throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				try{

				check:{
					//proof file existance
					if(this.objFile.exists() == false){
					   ExceptionZZZ ez = new ExceptionZZZ("file does not exists '" + this.objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;							
					}						
					if(this.objFile.isDirectory()){
					   ExceptionZZZ ez = new ExceptionZZZ("file is a directory '" + this.objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;						
					}
				}//end check:
				
				//create streams
				FileReader objFRead = new FileReader(this.objFile);
				BufferedReader objReader = new BufferedReader(objFRead);
				
				//Create an vektor and add the Strings
				//TODO vektoren.add
				Vector objV = new Vector();
				String sLine = null;
				do{
					sLine = objReader.readLine();
					if(sLine!=null){
						objV.addElement(sLine);
					}
				}while(sLine!=null);
				this.objVString = objV;
												
				//close streams
				objFRead.close();
				objReader.close();
				
				//return value
				bReturn = true;
				
				}catch(FileNotFoundException e){
					System.out.println(e.getMessage());
				}catch(IOException e){
					System.out.println(e.getMessage());
				}
				
			}//end main:
			return bReturn;
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
		
		private boolean KernelFileTextImportCrackNew_(IKernelZZZ objKernelIn, KernelLogZZZ objLogIn,File objFileIn, String sDirectoryIn, String sFileIn, String[] saFlagControlIn ) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				String stemp; boolean btemp; 
				this.objKernel = objKernelIn;
				if(objLogIn==null){
					this.setLogObject(objKernelIn.getLogObject());
				}else{
					this.setLogObject(objLogIn);
				}
			
				if(saFlagControlIn != null){
						for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
							stemp = saFlagControlIn[iCount];
							btemp = setFlag(stemp, true);
							if(btemp==false){ 
								   ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.",IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
								   throw ez;		 
							}
						}
					}				
					if(getFlag("init")==true) {
						bReturn = true;
						break main;
					}
						 
	
					//Find the file, read the content to an array, crack it.
				if(objFileIn==null){
				
									if(StringZZZ.isEmpty(sFileIn)){
									   ExceptionZZZ ez = new ExceptionZZZ("missing parameter 'filename'", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
									   throw ez;		 
									}
					
									if(StringZZZ.isEmpty(sDirectoryIn)){
										this.objFile = new File(sFileIn);
									}else{
										this.objFile = new File(sDirectoryIn + File.separator + File.separator + sFileIn);
									}					
								}else{
									this.objFile = objFileIn;
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
		 @date: 08.10.2004
		 @return Vector
		 */
		public Vector getStringVector() {
			return objVString;
		}

		/**
		 @date: 08.10.2004
		 @param vector
		 */
		public void setStringVector(Vector vector) {
			objVString = vector;
		}

	/**
	 @date: 08.10.2004
	 @return FileObject
	 */
	public File getFile() {
		return objFile;
	}



}
