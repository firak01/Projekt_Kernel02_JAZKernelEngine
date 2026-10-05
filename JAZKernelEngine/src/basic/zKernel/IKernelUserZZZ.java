package basic.zKernel;

import basic.zBasic.ExceptionZZZ;
import custom.zKernel.KernelLogZZZ;


/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface IKernelUserZZZ extends IKernelLogUserZZZ{
	public abstract IKernelZZZ getKernelObject() throws ExceptionZZZ;
	public abstract void setKernelObject(IKernelZZZ objKernel);			
}
