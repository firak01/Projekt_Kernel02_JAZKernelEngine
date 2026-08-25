package debug.zBasic.util.console.thread.multi.menu02;

import java.util.HashMap;

import basic.zBasic.AbstractObjectWithStatusLocalZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalUserZZZ;

public abstract class AbstractThreadWithStatusLocalZZZ<T> extends AbstractObjectWithStatusLocalZZZ<T> implements Runnable, IThreadableZZZ, IEventBrokerStatusLocalUserZZZ, IThreadWithStatusLocalEnabledZZZ {
	private static final long serialVersionUID = -5409829624205292974L;
		
	//Variablen zur Steuerung des internen Threads
	public static long lSLEEPTIME_DEFAULT = 1000;
	private long lSleepTime=-1;	
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractThreadWithStatusLocalZZZ() throws ExceptionZZZ {
		super();
		AbstractThreadNew_();
	}
	
	public AbstractThreadWithStatusLocalZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractThreadNew_();
	}
	
	public AbstractThreadWithStatusLocalZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);
		AbstractThreadNew_();
	}
	
		
	private boolean AbstractThreadNew_() throws ExceptionZZZ {
			
		return true;
	}
	
	//### aus Runnable
	@Override
	public void run() {
		try {
			this.start();
		} catch (ExceptionZZZ ez) {
			System.out.println(ez.getMessageLast());
			ez.printStackTrace();
		}
	}
	
	//### aus IThreadEnabledZZZ
	@Override
	public abstract boolean start() throws ExceptionZZZ;
	
	@Override
	public boolean isStopped() throws ExceptionZZZ {		
		return this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED);
	}
	
	@Override
	public void isStopped(boolean bStop) throws ExceptionZZZ {		
		this.requestStop();
	}
	
	@Override
	public void requestStop() throws ExceptionZZZ {		
		//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
		this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);
	}

	@Override
	 public long getSleepTime() throws ExceptionZZZ {
		if(lSleepTime< 0) {
			return this.lSLEEPTIME_DEFAULT;
		}else {
			return this.lSleepTime;
		}
    }
	
	@Override
	 public void setSleepTime(long lSleepTime) throws ExceptionZZZ {
		 if(lSleepTime<0){
			 lSleepTime=0;
		 }
		 this.lSleepTime = lSleepTime;
	 }
	
	
	//###################################################
	//### FLAG HANDLING
	//###################################################
	
	//### aus IAbstractThreadWithStatusLocalEnabledZZZ
	@Override
	public boolean getFlag(IThreadWithStatusLocalEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IThreadWithStatusLocalEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IThreadWithStatusLocalEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IThreadWithStatusLocalEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IThreadWithStatusLocalEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IThreadWithStatusLocalEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	
	//###################################
	//### FLAG CUSTOM Handling
	/* ES GIBT HIER KEIN FLAGCUSTOM
	@Override
	public boolean getFlagCustom(IAbstractThreadWithStatusLocalEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());
	}

	@Override
	public boolean setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}
	*/

	

	//###################################
	//### FLAGLOCAL Handling

	/* ES GIBT HIER KEIN FLAGLOCAL
	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	*/
}
