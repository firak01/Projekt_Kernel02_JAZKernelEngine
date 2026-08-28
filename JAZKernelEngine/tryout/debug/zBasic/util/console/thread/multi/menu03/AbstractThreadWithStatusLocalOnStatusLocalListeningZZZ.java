package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;

public abstract class AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> extends AbstractThreadWithStatusLocalZZZ<T>  implements IListenerObjectStatusLocalZZZ{
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
	
	
	//### aus IListenerObjectStatusLocalZZZ
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IListenerObjectStatusLocalZZZ#queryReactOnStatusLocalEvent(basic.zKernel.status.IEventObjectStatusLocalZZZ)
	 */
	@Override
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//boolean bQueryReactOn = super.queryReactOnStatusLocalEvent(eventStatusLocal);
			//if(!bQueryReactOn)break main;

			String sLog;
			
			//Falls das REQUEST_STOP Flag gesetzt ist, nicht weiter reagieren...
			//if(this.getFlag(IProgramRunnableZZZ.FLAGZ.REQUEST_STOP)) {
			//	sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> Flag '" + IProgramRunnableZZZ.FLAGZ.REQUEST_STOP.name() + "' gesetzt. Keine weitere Verarbeitung von Events. Breche ab.";
			//	this.logProtocol(sLog);
			//	break main;
			//}
						
			bReturn = true;
		}//emd main:
		return bReturn;
	}
	
	//##################
	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, String> createHashMapStatusLocal4ReactionCustom_String() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, String> getHashMapStatusLocal4Reaction_String() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHashMapStatusLocal4Reaction_String(
			HashMap<IEnumSetMappedStatusLocalZZZ, String> hmEnumSetForReaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> createHashMapStatusLocal4ReactionCustom_Enum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> getHashMapStatusLocal4Reaction_Enum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHashMapStatusLocal4Reaction_Enum(
			HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> hmEnumSetForReaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> createHashMapStatusLocal4ReactionCustom_EnumStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> getHashMapStatusLocal4Reaction_EnumStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHashMapStatusLocal4Reaction_EnumStatus(
			HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> hmEnumSetForReaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getActionAliasString(IEnumSetMappedStatusLocalZZZ enumStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean queryReactOnStatusLocalEventCustom(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
			//TODOGOON ; FALLUNTERSCHEIDUNG.
			if(eventStatusLocal==null)break main;
			
			IEnumSetMappedStatusLocalZZZ objStatus = eventStatusLocal.getStatusLocal();
			if(objStatus.equals(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED)) {
				
				this.requestStop();
				
			}
			bReturn = true;
		}//end main:
		return bReturn;

	}

	@Override
	public boolean queryReactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryReactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus,
			boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean queryReactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus,
			boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus,
			boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reactOnStatusLocal4ActionCustom(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus,
			boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEventRelevant2ChangeStatusLocalByClass(IEventObjectStatusLocalZZZ eventStatusLocal)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEventRelevant2ChangeStatusLocalByStatusLocalValue(IEventObjectStatusLocalZZZ eventStatusLocal)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
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
	public boolean getFlag(basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setFlag(basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] setFlag(basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag,
			boolean bFlagValue) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean proofFlagExists(basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean proofFlagSetBefore(basic.zKernel.status.IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag)
			throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

	
}
