/*
 * Created on 28.09.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file;

import java.io.File;
import custom.zKernel.KernelLogZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.file.KernelFileTextJoinZZZ;

/**

@author 0823 ,date 01.10.2004
*/
public class FileTextJoinZZZ extends KernelFileTextJoinZZZ {
	


public FileTextJoinZZZ(IKernelZZZ objKernelIn,KernelLogZZZ objLogIn, String sBaseDirectoryIn, String sBaseFileIn, String[] saFlagControl ) throws ExceptionZZZ{
	super(objKernelIn, objLogIn,sBaseDirectoryIn, sBaseFileIn,saFlagControl);
}

public FileTextJoinZZZ(IKernelZZZ objKernelIn,KernelLogZZZ objLogIn, File objFileIn, String[] saFlagControl ) throws ExceptionZZZ{
	super(objKernelIn, objLogIn,objFileIn, saFlagControl);
}
}//end class
