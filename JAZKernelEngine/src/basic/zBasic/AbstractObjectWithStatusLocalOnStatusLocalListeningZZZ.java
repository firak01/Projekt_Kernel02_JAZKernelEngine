package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusBasicZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.StatusLocalEventHelperZZZ;

public abstract class AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ <T> extends AbstractObjectWithStatusLocalZZZ<Object> implements IListenerObjectStatusLocalZZZ {
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusLocalZZZ,String> hmEnumSetForAction_String = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.		
	protected HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedZZZ> hmEnumSetForAction_Enum = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.	
	protected HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> hmEnumSetForAction_EnumStatus = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.

	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ() {	
		super();
	}
	public AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractObjectWithStatusOnStatusListeningNew_();
	}
	public AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractObjectWithStatusOnStatusListeningNew_();
	}
	public AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
		AbstractObjectWithStatusOnStatusListeningNew_();
	}
		
	private boolean AbstractObjectWithStatusOnStatusListeningNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getFlag("init")) break main;
			
			//Das Programm sollte sich ggfs. am eigenen ObjectBroker registrieren.
			//Ansonsten bleibt nur die reaction4Action-Methode.
			if(this.getFlag(IListenerObjectStatusLocalZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT)) {
				this.getSenderStatusLocalUsed().addListenerObject(this);
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public void registerForStatusLocalEvent(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		
		//Das Programm sollte sich ggfs. am eigenen ObjectBroker registrieren.
		//Ansonsten bleibt nur die reaction4Action-Methode.
		if(this.getFlag(IListenerObjectStatusLocalZZZ.FLAGZ.REGISTER_SELF_FOR_EVENT)) {
			this.getSenderStatusLocalUsed().addListenerObject(this);
		}
		
		//this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
		super.registerForStatusLocalEvent(objEventListener);
	}
	
	//####################
		//### STATUS
		//####################
		@Override
		public HashMap<IEnumSetMappedStatusLocalZZZ, String> getHashMapStatusLocal4Reaction_String() {
			if(this.hmEnumSetForAction_String==null) {
				HashMap<IEnumSetMappedStatusLocalZZZ, String> hmEnumSetForAction = this.createHashMapStatusLocal4ReactionCustom_String();
				this.hmEnumSetForAction_String = hmEnumSetForAction;
			}
			return this.hmEnumSetForAction_String;
		}

		@Override
		public void setHashMapStatusLocal4Reaction_String(HashMap<IEnumSetMappedStatusLocalZZZ, String> hmEnumSetForAction) {
			this.hmEnumSetForAction_String = hmEnumSetForAction;
		}
		
		@Override
		public boolean isEventRelevantAny(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				
				bReturn = this.isEventRelevant2ChangeStatusLocal(eventStatusLocal);
				if(bReturn) break main;
				
				bReturn = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
				if(bReturn) break main;
										
			}//end main:
			return bReturn;
		}
		

		@Override
		public boolean isEventRelevant2ChangeStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(!this.isEventRelevant2ChangeStatusLocalByClass(eventStatusLocal)) break main;
				if(!this.isEventRelevant2ChangeStatusLocalByStatusLocalValue(eventStatusLocal)) break main;
				
				bReturn = true;
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean isEventRelevant4ReactionOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocalReact) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
				String sLog;
				
				//1. Hole die HashMap für die Aktionen pro reinkommenden Status.
				//   Gibt es sie nicht oder sie ist leer, wird jeder Event - unabhaengig von dem Status - weiter verfolgt.
				HashMap<IEnumSetMappedStatusLocalZZZ,String>hmStatusLocal4Reaction= this.getHashMapStatusLocal4Reaction_String();
				
				//2. Static - Methode, die ueber alle Klassen gleich ist anwenden.
				//   Log-String als CallByValue Ersatzloesung mit einem Referenz-Objekt
				ReferenceArrayZZZ<String>objReferenceLog = new ReferenceArrayZZZ<String>();
				bReturn = StatusLocalEventHelperZZZ.isEventRelevant4ReactionOnStatusLocal(eventStatusLocalReact, hmStatusLocal4Reaction,objReferenceLog);
				
				String[] saLog = objReferenceLog.get();
				if(!ArrayUtilZZZ.isNull(saLog)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> From referenced Log";
					this.logProtocol(sLog);
					this.logProtocol(saLog);
				}
				
			}//end main:			
			return bReturn;
		}
		
		@Override
		public String getActionAliasString(IEnumSetMappedStatusLocalZZZ enumStatus) {
			String sReturn = null;
			main:{
				if(enumStatus==null)break main;
							
				HashMap<IEnumSetMappedStatusLocalZZZ,String>hm= this.getHashMapStatusLocal4Reaction_String();
				if(hm==null) break main;			
				if(hm.isEmpty()) break main;
				
				sReturn = hm.get(enumStatus);
			}//end main:
			return sReturn;
		}
		
		
		//++++++++++++++++++++++++++++++++++++++++++
		//+++ Ggfs. fuer andere Status - Zwecke (Tray, Monitor, ...)
		@Override
		public abstract HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> createHashMapStatusLocal4ReactionCustom_Enum();
			
		@Override
		public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> getHashMapStatusLocal4Reaction_Enum() {
			if(this.hmEnumSetForAction_Enum==null) {
				this.hmEnumSetForAction_Enum = this.createHashMapStatusLocal4ReactionCustom_Enum();
			}
			return this.hmEnumSetForAction_Enum;
		}

		@Override
		public void setHashMapStatusLocal4Reaction_Enum(HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedZZZ> hmEnumSetForReaction) {
			this.hmEnumSetForAction_Enum = hmEnumSetForReaction;
		}

		//+++
		@Override
		public abstract HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> createHashMapStatusLocal4ReactionCustom_EnumStatus();

		@Override
		public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> getHashMapStatusLocal4Reaction_EnumStatus() {
			if(this.hmEnumSetForAction_EnumStatus==null) {
				this.hmEnumSetForAction_EnumStatus = this.createHashMapStatusLocal4ReactionCustom_EnumStatus();
			}
			return this.hmEnumSetForAction_EnumStatus;
		}

		@Override
		public void setHashMapStatusLocal4Reaction_EnumStatus(HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> hmEnumSetForReaction) {
			this.hmEnumSetForAction_EnumStatus = hmEnumSetForReaction;
		}
		
		
		//++++++++++++++++++++++++++++++++++++++++++
		//++++++++++++++++++++++++++++++++++++++++++
		@Override
		public boolean offerStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusLocalIn) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				bReturn = this.offerStatusLocalEnum(enumStatusLocalIn, true);			
			}//end main:
			return bReturn;
		}
		
		
		@Override
		public boolean offerStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusLocalIn, boolean bStatusValue) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{			
				bReturn = this.offerStatusLocalEnum(enumStatusLocalIn, bStatusValue, null);
			}//end main:
			return bReturn;
		}
		

		@Override
		public boolean offerStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusLocal, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(enumStatusLocal == null) {
					ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedStatusZZZ Objekct", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sStatusName = enumStatusLocal.getName();
				bReturn = this.offerStatusLocal(sStatusName, bStatusValue, sStatusMessage);			
			}//end main:
			return bReturn;
		}

		@Override
		public boolean offerStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = this.offerStatusLocal(sStatusName, bStatusValue,null);
			}//end main:
			return bReturn;
		}
		
		@Override
		public boolean offerStatusLocal(String sStatusName, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				String sLog;
				
				if(sStatusMessage==null) {
					bReturn = super.offerStatusLocal(sStatusName, bStatusValue);
				}else {
					bReturn = super.offerStatusLocal(sStatusName, bStatusValue, sStatusMessage);
				}
				if(bReturn) {
					sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Status '" + sStatusName + "', Wert '" + bStatusValue + "'... offerStatusLocal  der Superclass gibt '" + bReturn + "' zurueck.";
					this.logProtocol(sLog);
				}

				//############################################################################
				//### 
				//### Reagiere nun ggfs. mit einer eigenen Reaktion - wichtig!!! unabhängig vom Rueckgabewert des offers an ggfs. an diesm Objekt registerte Listener
				//###
				//############################################################################
				
				
				//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				//+++ Mappe nun die eingehenden Status-Enums auf die eigene Reaction.
				HashMap<IEnumSetMappedStatusLocalZZZ,String>hmEnum = this.getHashMapStatusLocal4Reaction_String();				
				if(hmEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Es ist KEINE Mapping Hashmap StatusLocal4Reaction vorhanden. Breche ab";
					this.logProtocol(sLog);
					break main;
				}
				
				//Hole den ActionAlias
				ReferenceArrayZZZ<String>objReturnReferenceLog = new ReferenceArrayZZZ<String>();
				
				//TODOGOON20240518 hier ist was im Argen. ProcessWatchRunner bekommt nie den Event vom Monitor, der den passenden actionAlias triggert.
				String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(sStatusName, hmEnum, objReturnReferenceLog);
				String[] saLog = objReturnReferenceLog.get();
				if(!ArrayUtilZZZ.isNull(saLog)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' dazu ActionAlias geholt sActionAlias='" + sActionAlias + "'";				
					this.logProtocol(sLog);
					this.logProtocol(saLog);
				}
				
				if(StringZZZ.isEmpty(sActionAlias)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' ist NICHT relevant zum Reagieren mit einer eigenen Aktion.";
					this.logProtocol(sLog);
					break main;
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' ist relevant zum Reagieren mit einer eigenen Aktion (ActionAlias = " + sActionAlias + ").";
					this.logProtocol(sLog);
				}
				
				//Suche zuerst wieder das passende IEnumSetMappedStatusZZZ-Objekt fuer die Reaction
				ReferenceArrayZZZ<String>objReferenceLog02 = new ReferenceArrayZZZ<String>();			
				IEnumSetMappedStatusLocalZZZ enumStatus = StatusLocalEventHelperZZZ.getEnumStatusMapped(sStatusName, hmEnum, objReferenceLog02);
				String[] saLog02 = objReferenceLog02.get();
				if(!ArrayUtilZZZ.isNull(saLog02)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' EnumStatusMapped geholt.";				
					this.logProtocol(sLog);
					this.logProtocol(saLog);								
				}
				
				if(enumStatus!=null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' EnumStatusMapped gefunden. '" + enumStatus.getName() + "' passt. (bStatusValue="+bStatusValue + " sStatusMessage='" + sStatusMessage + "'";
					this.logProtocol(sLog);
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName() + "=> Status '" + sStatusName + "' KEINEN gemappten Status gefunden. (bStatusValue="+bStatusValue + " sStatusMessage='" + sStatusMessage + "'";
					this.logProtocol(sLog);
				}
				
				//Mache die Rection
				bReturn = this.reactOnStatusLocal4Action(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
				
			}//end main:
			return bReturn;
		}
		
		//++++++++++++++++++++++++++++++++++++++++
		//-----------------------------------------
		@Override
		public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
			//siehe AbstractObjectWithFlagOnStatusListening
			boolean bReturn = false;		
			main:{
				String sLog;
				
				//+++ Pruefe, ob der ActionAlias (anhand von IEnumSetMappedStatusZZZ) in der reactionHashmapCustom vorhanden ist.
				boolean bProof = this.queryReactOnStatusLocalEventCustom(eventStatusLocal);
				if(!bProof) {
					sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Zum Reagieren: QueryReactCustom ergibt false ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
					this.logProtocol(sLog);
					break main;
				}
			
				bReturn = true;
			}//end main:
			return bReturn;
		}

			
		@Override
		public boolean queryReactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
			//siehe AbstractObjectWithFlagOnStatusListening
			boolean bReturn = false;		
			main:{
				String sLog;
				
				//+++ Pruefe, ob der ActionAlias (anhand von IEnumSetMappedStatusZZZ) in der reactionHashmapCustom vorhanden ist.
				boolean bProof = this.isEventRelevant4ReactionOnStatusLocal(eventStatusLocal);
				if(!bProof) {
					sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Zum Reagieren: KEINE gemappte Reaktion für den Status aus dem Event-Objekt ("+ eventStatusLocal.getStatusEnum().name() + ") . Breche ab";				
					this.logProtocol(sLog);
					break main;
				}

				bReturn = true;
			}//end main:
			return bReturn;
		}
			
		@Override
		public boolean queryReactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
			//siehe AbstractObjectWithFlagOnStatusListening
			boolean bReturn = false;		
			main:{
				String sLog;
				
				//+++ Pruefe, ob der ActionAlias (anhand von IEnumSetMappedStatusZZZ) in der reactionHashmapCustom vorhanden ist.
				boolean bProof = this.queryReactOnStatusLocal4ActionCustom(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
				if(!bProof) {
					sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> Zum Reagieren: QueryReact4ActionCustom ergibt false ("+ enumStatus.getName() + ") . Breche ab";				
					this.logProtocol(sLog);
					break main;
				}
			
				bReturn = true;
			}//end main:
			return bReturn;
		}

		
		@Override
		public boolean reactOnStatusLocal4Action(String sActionAlias, IEnumSetMappedStatusLocalZZZ enumStatus, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				bReturn = this.reactOnStatusLocal4ActionCustom(sActionAlias, enumStatus, bStatusValue, sStatusMessage);
			}//end main:
			return bReturn;
		}

		@Override
		public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				boolean bQueryOnReact = this.queryReactOnStatusLocalEvent(eventStatusLocal);
				if(!bQueryOnReact) break main;
				
				bReturn = this.reactOnStatusLocalEvent4Action(eventStatusLocal);
			}
			return bReturn;
		}
		
		

		
		@Override
		public boolean reactOnStatusLocalEvent4Action(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
			boolean bReturn = false;
			main:{
				String sLog;

				if(eventStatusLocal==null) {				 
					 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					 throw ez;
				}
				
				IEnumSetMappedStatusLocalZZZ enumStatus = (IEnumSetMappedStatusLocalZZZ) eventStatusLocal.getStatusEnum();
				if(enumStatus==null) {
					 ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject has no EnumStatus", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					 throw ez;
				}
				
				boolean bQueryOnStatusLocal4Action = this.queryReactOnStatusLocalEvent4Action(eventStatusLocal);
				if(!bQueryOnStatusLocal4Action)break main;
				
				
				//+++ Mappe nun die eingehenden Status-Enums auf die eigene Reaction.
				HashMap<IEnumSetMappedStatusLocalZZZ,String>hmEnum = this.getHashMapStatusLocal4Reaction_String();				
				if(hmEnum==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+ "=> KEINE Hashmap StatusLocal4Reaction vorhanden. Breche ab";
					this.logProtocol(sLog);
					break main;
				}
				
				//Hole den ActionAlias
				ReferenceArrayZZZ<String>objReturnReferenceLog = new ReferenceArrayZZZ<String>();
				String sActionAlias = StatusLocalEventHelperZZZ.getActionAliasString4Reaction(enumStatus, hmEnum, objReturnReferenceLog);
				String[] saLog = objReturnReferenceLog.get();
				if(!ArrayUtilZZZ.isNull(saLog)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+ "=> Event ("+ eventStatusLocal.getStatusEnum().name() + ") Referenziertes Log von der Suche nach dem ActionAlias. (ActionAlias = '" + sActionAlias + "') Breche ab";				
					this.logProtocol(sLog);
					this.logProtocol(saLog);
				}
				
				if(StringZZZ.isEmpty(sActionAlias)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+"=> Event  ("+ eventStatusLocal.getStatusEnum().name() + ") ist NICHT relevant. (ActionAlias = '" + sActionAlias + "'";
					this.logProtocol(sLog);
					break main;
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+this.getClass().getSimpleName()+"=> Event ("+ eventStatusLocal.getStatusEnum().name() + ") ist relevant. (ActionAlias = " + sActionAlias + ").";
					this.logProtocol(sLog);
				}
				
				//Mache die Reaktion
				boolean bStatusValue = eventStatusLocal.getStatusValue();
				String sStatusMessage = eventStatusLocal.getStatusMessage();
				bReturn = this.reactOnStatusLocal4Action(sActionAlias, enumStatus, bStatusValue, sStatusMessage);			
			}//end main:
			return bReturn;
		}
		
		@Override
		abstract public HashMap<IEnumSetMappedStatusLocalZZZ, String> createHashMapStatusLocal4ReactionCustom_String();

		
		@Override
		abstract public boolean reactOnStatusLocal4ActionCustom(String sAction, IEnumSetMappedStatusLocalZZZ enumStatus,boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ;
		
	//######################################################################
	//### FLAG-HANDLING                                            #########
	//######################################################################
		
	//### aus IListenerObjectStatusLocalZZZ
	@Override
	public boolean getFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	
	//### aus IListenerObjectStatusBasicZZZ
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


