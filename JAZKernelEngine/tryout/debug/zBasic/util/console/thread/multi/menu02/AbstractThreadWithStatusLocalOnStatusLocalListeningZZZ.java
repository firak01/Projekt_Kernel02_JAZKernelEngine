package debug.zBasic.util.console.thread.multi.menu02;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

//Merke: IListenerObjectStatusLocalZZZ nicht implementieren.
//       Dann muss man viele Methoden einfach übernehmen, die es hier in einer "einfachen Variante" nicht benötigt. 
public abstract class AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> extends AbstractThreadWithStatusLocalZZZ<T>  implements IListenerObjectStatusBasicZZZ{
	private static final long serialVersionUID = 202987237863158494L;
	
	public AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	public AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);		
	}
	
	//### aus IListenerObjectStatusBasicZZZ
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{		
			if(eventStatusLocal==null)break main;
			
			IEnumSetMappedStatusLocalZZZ objStatus = eventStatusLocal.getStatusLocal();
			if(objStatus.equals(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED)) {
				
				this.requestStop();
				
			}
			bReturn = true;
		}//end main:
		return bReturn;

	}

	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectWithStatusLocalZZZ#queryOfferStatusLocalCustom()
	 */
	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		return true; //... hier gibt es keine Einschränkung den Status nicht zu feuern.
	}
	
	//########################################
	//### FLAG HANDLING
	//########################################
	
	@Override
	public boolean getFlag(basic.zKernel.status.IListenerObjectStatusBasicZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setFlag(basic.zKernel.status.IListenerObjectStatusBasicZZZ.FLAGZ objEnumFlag, boolean bFlagValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] setFlag(basic.zKernel.status.IListenerObjectStatusBasicZZZ.FLAGZ[] objaEnumFlag,
			boolean bFlagValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean proofFlagExists(basic.zKernel.status.IListenerObjectStatusBasicZZZ.FLAGZ objEnumFlag)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean proofFlagSetBefore(basic.zKernel.status.IListenerObjectStatusBasicZZZ.FLAGZ objEnumFlag)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
