/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file;

import java.io.File;
import java.util.Vector;

import custom.zKernel.KernelLogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.file.KernelFileTextImportCrackZZZ;

/**

@author 0823 ,date 07.10.2004
*/
public class FileTextImportCrackZZZ extends KernelFileTextImportCrackZZZ {

	public FileTextImportCrackZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, File objFile, String[] saFlagControl) throws ExceptionZZZ{
		super(objKernel, objLog, objFile, saFlagControl);
	}
	
	public boolean crackCustom() throws ExceptionZZZ{
				boolean bReturn = false;
				main:{
					String stemp;
		
					Vector objVString = this.getStringVector();					
					check:{
						if(objVString == null){
						stemp = "'Vector of strings, loaded from file'";
						ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez;							
					}		
					if(objVString.size()==0){
						stemp = "''Vector of strings, loaded from file'";
						ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PROPERTY_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;			
					}
					}//end check:
					
					
				
					//now do the cracking
					Vector objVStringNew = new Vector(objVString.size());
					
				    //1. expand the Control character by "_"
				    String sLine = null;
				    for(int iCount = 0; iCount <= objVString.size()-1; iCount++ ){
				    	sLine=(String)objVString.elementAt(iCount);
				    	if(sLine!=null){
				    		if(sLine.equals("")==false){
				    	
				    		String sLineLeft = sLine.substring(0,2);
				    		String sLineRight = sLine.substring(2);
				    		sLine = sLineLeft + "_" + sLineRight;
				    		
							objVStringNew.addElement(sLine);	
				    		}
				    	}
				    	
				    }
					
					//2. reoder the header line to index position 0
					for(int iCount = 0; iCount <= objVStringNew.size()-1; iCount++){
						sLine = (String) objVStringNew.elementAt(iCount);
						if(sLine!=null){
							String sLineLeft = sLine.substring(0,3);
							if(sLineLeft.equals("06_")){
								//gefunden Headerposition an die erste stelle verschieben
								stemp = (String)objVStringNew.elementAt(iCount);
								objVStringNew.removeElementAt(iCount);
								objVStringNew.insertElementAt(stemp,0);
								break; //nun die for - Schleife verlassen
							}
						}
					}
					
					
					//3. append the filename					
					String sFileToCrack = this.getFile().getPath();	
					stemp = (String)objVStringNew.elementAt(0)+sFileToCrack;
					objVStringNew.removeElementAt(0);
					objVStringNew.insertElementAt(stemp, 0);
					
					
					//
					this.setStringVector(objVStringNew);
				}//end main:
				return bReturn;
			}

}//end class

