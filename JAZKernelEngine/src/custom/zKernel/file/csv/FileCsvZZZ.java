/*
 * Created on 25.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file.csv;

import custom.zKernel.KernelLogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.csv.KernelFileCsvZZZ;

/**

@author 0823 ,date 25.10.2004
*/
public class FileCsvZZZ extends KernelFileCsvZZZ {

	/**
	CONSTRUCTOR
	
	
	 @author 0823 , date: 25.10.2004
	 @param objKernel
	 @param objLog
	 @param sDirectory
	 @param sFilename
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public FileCsvZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, String sDirectory, String sFilename, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, objLog, sDirectory, sFilename, saFlagControl);
	}

}
