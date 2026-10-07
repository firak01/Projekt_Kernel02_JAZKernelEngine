package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalReactZZZ;
import basic.zKernel.status.IMonitorObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;
import custom.zKernel.Log;

public abstract class AbstractObjectWithStatusLocalMonitoringZZZ <T> extends AbstractObjectWithStatusLocalOnStatusLocalListeningZZZ<Object> implements IListenerObjectStatusLocalReactZZZ, IMonitorObjectStatusLocalZZZ{
	private static final long serialVersionUID = 1L;
	protected HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> hmEnumSetForActionCascaded_EnumStatus = null; //Hier wird ggfs. der Eigene Status mit dem Status einer anderen Klasse (definiert durch das Interface) gemappt.

	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusLocalMonitoringZZZ() {	
		super();
	}
	public AbstractObjectWithStatusLocalMonitoringZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractObjectWithStatusMonitoringNew_();
	}
	public AbstractObjectWithStatusLocalMonitoringZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractObjectWithStatusMonitoringNew_();
	}
	public AbstractObjectWithStatusLocalMonitoringZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
		AbstractObjectWithStatusMonitoringNew_();
	}
	
	private boolean AbstractObjectWithStatusMonitoringNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			if(this.getFlag("init")) break main;
								
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	//### aus IStatusLocalMapForMonitoringStatusMessageUserZZZ	
	@Override
	public HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> getHashMapEnumSetForCascadingStatusLocal() {
		if(this.hmEnumSetForActionCascaded_EnumStatus==null) {
			this.hmEnumSetForActionCascaded_EnumStatus = this.createHashMapEnumSetForCascadingStatusLocalCustom();
		}
		return this.hmEnumSetForActionCascaded_EnumStatus;
	}
	
	@Override
	public void setHashMapEnumSetForCascadingStatusLocal(HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> hmEnumSet) {
		this.hmEnumSetForActionCascaded_EnumStatus = hmEnumSet;
	}
	
	//---------- der Monitor erweitert dies um reactOnStatusLocalEvent4Monitor ....
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{	
		boolean bReturn;
		
		//1. Monitor: D.h. einen Status weiterleiten. Das muss zuerst passieren.
		bReturn = this.reactOnStatusLocalEvent4Monitor(eventStatusLocal);
		
		//falls ein Status weitergeleitet wurde, kann der Event ja nicht von der Monitor-Klasse selbst kommen.
		//in dem Fall warten wir darauf, dass der Weitergeleitete Event (diesmal von der Monitor-Klasse selbst) hier eintrifft.
		//WICHTIG: TROTZDEM DIE REACTION AUSFÜHREN. Also nicht .... if(!bReturn) {
		//MERKE:   Da der Monitor sich immer an sich selbst registriert, kann man dann hier auch die neu geworfenen STATUS hinzufuegen.
		
		String sLog =  ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Ohne gemappten Status: Rufe CustomReaktionsmethode auf (reactOnStatusLocalEvent4Action)";
		Log.protocol(this, sLog);
			
		//	2. Eigene Action... das hat das Ziel, das dadurch ja ggfs. wieder neue Events geworfen werden können
		bReturn = this.reactOnStatusLocalEvent4Action(eventStatusLocal);
		
		return bReturn;
	}
	
	@Override
	public boolean reactOnStatusLocalEvent4Monitor(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {
				  ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject not provided", this.iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 						
				  throw ez;
			}
			
			//Merke: Das Nachsehen in der HashMap ist ja quasi eine Relevanzpruefung. Darum an dieser Stelle nicht noch extra pruefen.
			//Falls nicht zuständig, setze keinen Status
			//boolean bProofStatus = this.isEventRelevant4MonitorOnStatusLocal(eventStatusLocal);
			//if(!bProofStatus) break main;
			
			
			String sLog=null;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
			IEnumSetMappedStatusLocalZZZ enumStatusIn = eventStatusLocal.getStatusLocal();
			if(enumStatusIn==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Keinen Status aus dem Event-Objekt erhalten. Breche ab";				
				Log.protocol(this, sLog);
				break main;
			}
			
			HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ>hmStatus=this.getHashMapEnumSetForCascadingStatusLocal();
			IEnumSetMappedStatusLocalZZZ enumStatusOut = hmStatus.get(enumStatusIn); 
			if(enumStatusOut==null) {
				sLog =  ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> KEINEN Gemappten Status gefunden. Setze also keinen eigenen Status.";
				Log.protocol(this, sLog);
				break main; //Wenn der Status nicht gemappt ist, wird auch nichts gesetzt.
			}else {			
				sLog =  ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> Gemappten Status gefunden... Setze dazu den passenden eigenen Status.";
				Log.protocol(this, sLog);
			}
			
			boolean bStatusValue = eventStatusLocal.getStatusValue();
			String sStatusMessage = eventStatusLocal.getStatusMessage();
			bReturn = this.setStatusLocalEnum(enumStatusOut, bStatusValue, sStatusMessage);
		}//end main:
		return bReturn;
	}
	
	public boolean isEventRelevant4MonitorOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventStatusLocal==null) {
				  ExceptionZZZ ez = new ExceptionZZZ( "EventStatusObject not provided", this.iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 						
				  throw ez;
			}
			
			String sLog;
			
			//+++ Mappe nun die eingehenden Status-Enums auf die eigenen.
			IEnumSetMappedStatusLocalZZZ enumStatusIn = eventStatusLocal.getStatusLocal();
			if(enumStatusIn==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Keinen Status aus dem Event-Objekt erhalten. Breche ab";				
				Log.protocol(this, sLog);
				break main;
			}
			
			HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ>hmStatus=this.getHashMapEnumSetForCascadingStatusLocal();
			IEnumSetMappedStatusLocalZZZ enumStatusOut = hmStatus.get(enumStatusIn); 
			if(enumStatusOut==null) {
				sLog =  ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> KEINEN Gemappten Status gefunden. Also Event NICHT mit Monitor-Objekt weiter verarbeitbar.";
				Log.protocol(this, sLog);
				break main; //Wenn der Status nicht gemappt ist, wird auch nichts gesetzt.
			}else {			
				sLog =  ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> Gemappten Status gefunden. Also Event mit Monitor-Objekt weiter verarbeitbar.";
				Log.protocol(this, sLog);				
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ#createHashMapEnumSetForCascadingStatusLocalCustom()
	 */
	@Override
	public abstract HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> createHashMapEnumSetForCascadingStatusLocalCustom();
	
	
	
	//######################################################################
	//### FLAGZ: aus IStatusLocalMapForMonitoringStatusMessageUserZZZ   ####
	//######################################################################
	@Override
	public boolean getFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IStatusLocalMapForMonitoringStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	
	//######################################################################
	//### FLAGZ: aus IStatusLocalMapForMonitoringStatusMessageUserZZZ   ####
	//######################################################################
	@Override
	public boolean getFlag(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IMonitorObjectStatusLocalZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IMonitorObjectStatusLocalZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}		
}
