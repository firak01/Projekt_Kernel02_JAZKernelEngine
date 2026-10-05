/*
 * Created on 25.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file.transform;

import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.csv.FileCsvZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.file.transform.KernelFileTransformCsv2IniZZZ;

/**

@author 0823 ,date 25.10.2004
*/
public class FileTransformCsv2IniZZZ extends KernelFileTransformCsv2IniZZZ {

	/** CONSTRUCTOR
	 @date: 25.10.2004
	 @param objKernel
	 @param objLog
	 @param objCSV
	 @param objActionConfigurationIni
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public FileTransformCsv2IniZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, FileCsvZZZ objCSV, FileIniZZZ objActionConfigurationIni,String sDirTarget, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, objLog, objCSV, objActionConfigurationIni, sDirTarget, saFlagControl);
	}
}
