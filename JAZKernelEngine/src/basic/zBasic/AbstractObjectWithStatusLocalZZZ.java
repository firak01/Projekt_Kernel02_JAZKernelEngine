package basic.zBasic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.xmlbeans.impl.store.Query;

import basic.zBasic.component.IProgramMonitorZZZ;
import basic.zBasic.component.IProgramRunnableZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractList.CircularBufferForStatusBooleanMessageZZZ;
import basic.zBasic.util.datatype.enums.EnumSetUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventBrokerStatusLocalUserZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalUserZZZ;
import basic.zKernel.status.ISenderObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusBooleanMessageZZZ;
import basic.zKernel.status.IStatusBooleanZZZ;
import basic.zKernel.status.IStatusLocalMessageUserZZZ;
import basic.zKernel.status.SenderObjectStatusLocalZZZ;
import basic.zKernel.status.StatusBooleanMessageZZZ;
import basic.zKernel.status.StatusLocalAvailableHelperZZZ;

/**Merke: Halte gleich zu AbstractProgramRunnableWithStatusZZZ
 *        wg. der Implementierung von IStatusLocalUserMessageZZZ
 * @param <T> 
 * 
 * @author Fritz Lindhauer, 20.01.2024, 17:04:43
 * 
 */
public abstract class AbstractObjectWithStatusLocalZZZ <T> extends AbstractObjectWithFlagZZZ<Object> implements IStatusLocalMessageUserZZZ, IEventBrokerStatusLocalUserZZZ{
	private static final long serialVersionUID = 1218960624186397845L;
	
	protected volatile HashMap<String, Boolean>hmStatusLocal = new HashMap<String, Boolean>(); //Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	protected volatile HashMap<String, String>hmStatusLocalMessage = new HashMap<String, String>();//20240310; Im Zuge der Arbeiten eingesehen, dass eine extra Message auch in eine extra HashMap gespeichert werden muss.
	//Dann faellt der extra CircularBufferSpeicher auch weg. Die "abweichenden" Messages werden selbst auch in IStatusBooleanMessage gespeichert.
	//protected volatile CircularBufferZZZ<String> cbStatusLocalMessage = new CircularBufferZZZ<String>(9);
	
	
	//Ziel: Das Frontend soll so Infos im laufende Prozess per Button-Click abrufen koennen.
	//Der StatusMessageString. Als Extra Speicher. Kann daher zum Ueberschreiben des Default StatusMessage Werts aus dem Enum genutzt werden.
	protected volatile CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> cbStatusLocal = new CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ>(9);
	
	//Ein einmaliger Vorgang. Der quasi letzte gemeldete Fehler.
	//Diese Meldung ist flexibel und nicht in irgendeinem EnumSet hinterlegt.
	protected volatile String sStatusLocalError=null;
	
	//fuer ISenderObjectStatusLocalUserZZZ
	protected volatile ISenderObjectStatusLocalZZZ objEventStatusLocalBroker=null;//Das Broker Objekt, an dem sich AUCH ANDERE Objekte registrieren können, um ueber Aenderung eines StatusLocal per Event informiert zu werden.
	
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu können.
	//Merke: Jede Unterklasse muss ihren eigenen Default Konstruktor haben.	
	public AbstractObjectWithStatusLocalZZZ() {	
		super();
	}
	public AbstractObjectWithStatusLocalZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractObjectWithStatusNew_();
	}
	public AbstractObjectWithStatusLocalZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractObjectWithStatusNew_();
	}
	public AbstractObjectWithStatusLocalZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ{
		super(hmFlag);
		AbstractObjectWithStatusNew_();
	}
	
	private boolean AbstractObjectWithStatusNew_() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.getFlag("init"))break main;
			
			bReturn =true;
		}//end main:
		return bReturn;
	}
	
	@Override
	public String getStatusLocalError() {
		return this.sStatusLocalError;
	}
	
	@Override 
	public void setStatusLocalError(String sError) {
		this.sStatusLocalError = sError;
	}
	
	//#########################################################
	//### aus ISenderObjectStatusLocalUserZZZ
	@Override
	public ISenderObjectStatusLocalZZZ getSenderStatusLocalUsed() throws ExceptionZZZ {
		if(this.objEventStatusLocalBroker==null) {
			//++++++++++++++++++++++++++++++
			//Nun geht es darum den Sender/Broker fuer Aenderungen am Status zu erstellen, der dann registrierte Objekte ueber Aenderung des Status zu informiert
			ISenderObjectStatusLocalZZZ objSenderStatusLocal = new SenderObjectStatusLocalZZZ();			
			this.objEventStatusLocalBroker = objSenderStatusLocal;
		}		
		return this.objEventStatusLocalBroker;
	}

	@Override
	public void setSenderStatusLocalUsed(ISenderObjectStatusLocalZZZ objEventSender) {
		this.objEventStatusLocalBroker = objEventSender;
	}

	
	@Override
	public void registerForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().addListenerObject(objEventListener);
	}

	@Override	
	public void unregisterForStatusLocalEvent(IListenerObjectStatusLocalZZZ objEventListener) throws ExceptionZZZ {
		this.getSenderStatusLocalUsed().removeListenerObject(objEventListener);
	}	
		
	
	
	
	//###############################
	//### Flags IObjectWithStatusZZZ
	//###############################
	
	//### Aus IObjectWithStatusZZZ
	@Override
	public boolean getFlag(IObjectWithStatusEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IObjectWithStatusEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IObjectWithStatusEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IObjectWithStatusEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IObjectWithStatusEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IObjectWithStatusEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	
	//###############################
	//### Flags IStatusLocalMessageUserZZZ
	//###############################
	
	@Override
	public boolean getFlag(IStatusLocalMessageUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IStatusLocalMessageUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IStatusLocalMessageUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStatusLocalMessageUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStatusLocalMessageUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IStatusLocalMessageUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	
	//###############################
	//### Flags ISenderObjectStatusLocalUserZZZ
	//###############################
	@Override
	public boolean getFlag(ISenderObjectStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(ISenderObjectStatusLocalUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(ISenderObjectStatusLocalUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ISenderObjectStatusLocalUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(ISenderObjectStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(ISenderObjectStatusLocalUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}			
		
	
	
	//#########################
	//### STATUS MAPPING aus 
	//#########################
	

	//##########################
	//### STATUS HANDLING 
	//##########################
	//Analog zu AbstractProgramRunnableWithStatusZZZ
	//aus IStatusLocalUserMessageZZZ
		
	/**
	 * @param sStatusString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 23.10.2023, 11:48:47
	 */
	@Override
	public boolean isStatusSetBefore(String sStatusString) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(sStatusString == null) {
				bReturn = this.getStatusLocalAbbreviation()==null;
				break main;
			}
			
			if(!sStatusString.equals(this.getStatusLocalAbbreviation())) {
				bReturn = true;
			}
		}//end main:
		if(bReturn) {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+ "ObjectWithStatus ("+this.getClass().getSimpleName()+ ") - Status changed to '"+sStatusString+"'";
		    this.logProtocol(sLog);			
		}
		return bReturn;
	}
	
	
	@Override
	public boolean isStatusLocalDifferent(String sStatusString, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{	
			bReturn = this.proofStatusLocalValueChanged(sStatusString, bStatusValue);	
		}//end main:
		if(bReturn) {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+ "ObjectWithStatus ("+this.getClass().getSimpleName()+ ") - Status changed to '"+sStatusString+"', Value="+bStatusValue;
			this.logProtocol(sLog);			
		    this.logProtocol(sLog);			
		}else {
			String sLog = ReflectCodeZZZ.getPositionCurrent()+ "ObjectWithStatus ("+this.getClass().getSimpleName()+ ") - Status remains '"+sStatusString+"', Value="+bStatusValue;			
		    this.logProtocol(sLog);
		}
		return bReturn;
	}

	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalUserBasicZZZ#resetStatusLocalError()
	 */
	@Override
	public void resetStatusLocalError() {
		this.sStatusLocalError = null;
	}
	
	
	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObject() {
		return (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getLast();
	}
	
	@Override
	public boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum) {
		boolean bReturn = false;
		main:{
			//Hier nicht den Status setzen, sondern nur das letze Objekt im CircularBuffer ersetzen
			bReturn = this.getCircularBufferStatusLocal().replaceLastWith(objEnum);
			if(!bReturn) break main;
				
			String sMessage = objEnum.getMessage();
			bReturn = this.getCircularBufferStatusLocal().replaceLastWith(sMessage);
			if(!bReturn) break main;
			
		}//end main
		return bReturn;
	}
	
	@Override
	public boolean setStatusLocalObject(IStatusBooleanMessageZZZ objEnum, String sStatusMessage) {
		boolean bReturn = false;
		main:{
			//Hier nicht den Status setzen, sondern nur das letze Objekt im CircularBuffer ersetzen
			bReturn = this.getCircularBufferStatusLocal().replaceLastWith(objEnum);
			if(!bReturn) break main;
				
			bReturn = this.getCircularBufferStatusLocal().replaceLastWith(sStatusMessage);
			if(!bReturn) break main;
			
		}//end main
		return bReturn;
	}
	

	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObjectPrevious() {
		return (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious();
	}
	
	@Override
	public IStatusBooleanMessageZZZ getStatusLocalObjectPrevious(int iIndexStepsBack) {
		return (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
	}
	
	@Override
	public IEnumSetMappedStatusLocalZZZ getStatusLocalEnumPrevious() {
		IEnumSetMappedStatusLocalZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious();
			if(objStatus==null) break main;
				
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
	
	@Override
	public IEnumSetMappedStatusLocalZZZ getStatusLocalEnumPrevious(int iIndexStepsBack) {
		IEnumSetMappedStatusLocalZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
			if(objStatus==null) break main;
			
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}

	@Override
	public IEnumSetMappedStatusLocalZZZ getStatusLocalEnumCurrent() {
		IEnumSetMappedStatusLocalZZZ objReturn = null;
		main:{
			IStatusBooleanMessageZZZ objStatus = this.getStatusLocalObject();
			if(objStatus==null) break main;
			
			objReturn = objStatus.getEnumObject();
		}//end main:
		return objReturn;
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++
	@Override
	public boolean offerStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusLocalIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.offerStatusLocalEnum(enumStatusLocalIn, true, null);
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
			String sLog; 
			
			if(enumStatusLocal == null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedStatusZZZ Objekct", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sStatusName = enumStatusLocal.getName();
			
			String sStatusMessageToSet = null;
			if(sStatusMessage==null){//also wenn absichtlich ein Leerstring übergeben wird, dann soll das so sein.
				if(bStatusValue) {
					sStatusMessageToSet = enumStatusLocal.getStatusMessage();
				}else {
					sStatusMessageToSet = "NICHT " + enumStatusLocal.getStatusMessage();
				}	
				
				if(sStatusMessageToSet!=null) {
					sLog = ReflectCodeZZZ.getPositionCurrent() + "Setzt sStatusMessageToSet='" + sStatusMessageToSet + "'";
					this.logProtocol(sLog);
				}
			}else {
				sStatusMessageToSet = sStatusMessage;
			}
			
			
			bReturn = this.offerStatusLocal(sStatusName, bStatusValue, sStatusMessageToSet);			
		}//end main:
		return bReturn;
	}
	

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String getStatusLocalAbbreviation(){
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumCurrent();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}
			
		}//end main:
		return sReturn;
	}
		
	/**
	 * @return
	 * @author Fritz Lindhauer, 11.10.2023, 08:25:54
	 */
	@Override
	public String getStatusLocalAbbreviationPrevious() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
			if(objEnum!=null) {
				sReturn = objEnum.getAbbreviation();
			}			
		}//end main:
		return sReturn;
	}
		
	@Override
	public String getStatusLocalDescription() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumCurrent();
			if(objEnum!=null) {
				sReturn = objEnum.getDescription();
			}
			
		}//end main:
		return sReturn;
	}
	
	@Override
	public String getStatusLocalDescriptionPrevious() {
		String sReturn = null;
		main:{			
			IEnumSetMappedZZZ objEnum = this.getStatusLocalEnumPrevious();
			if(objEnum!=null) {
				sReturn = objEnum.getDescription();
			}
			
		}//end main:
		return sReturn;
	}
	
	//### aus IStatusLocalUserMessageZZZ
	
	//Merke: Ohne Angabe eines Status, bleibt einem nur uebrig den letzten Wert aus dem CicularBuffer zu verwenden.
	//       mit angabe eines Status holt man sich den Wert aus der HashMap.
	@Override 
	public String getStatusLocalMessage(String sStatusName) throws ExceptionZZZ	{
		String sReturn = null;
		main:{
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof) break main;
			
			HashMap<String,String>hmMessage = this.getHashMapStatusLocalMessage();
			sReturn = hmMessage.get(sStatusName);
		}
		return sReturn;
	}
	
	@Override
	public String getStatusLocalMessage(Enum enumStatusIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			boolean bProof = this.proofStatusLocalExists(enumStatusIn);
			if(!bProof) break main;
			
			String sStatusName = enumStatusIn.name();
			HashMap<String,String>hmMessage = this.getHashMapStatusLocalMessage();
			sReturn = hmMessage.get(sStatusName);
		}
		return sReturn;
	}
	
	
	@Override 
	public String getStatusLocalMessage()	{
		IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getLast();
		return objStatus.getMessage();
	}
	
	@Override
	public boolean replaceStatusLocalMessage(String sStatusMessage) {
		IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getLast();
		objStatus.setMessage(sStatusMessage);
		return true;
	}
	
	@Override
	public String getStatusLocalMessagePrevious(){
		IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious();
		return objStatus.getMessage();
	}
	
	@Override
	public String getStatusLocalMessagePrevious(int iIndexStepsBack) {
		IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) this.getCircularBufferStatusLocal().getPrevious(iIndexStepsBack);
		return objStatus.getMessage();
	}
	
	//### aus IStatusLocalUserZZZ
	@Override
	public boolean getStatusLocal(Enum objEnumStatusIn) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(objEnumStatusIn==null) {
				break main;
			}
						
			String sStatusName = objEnumStatusIn.name();
			if(StringZZZ.isEmpty(sStatusName)) break main;
										
			HashMap<String, Boolean> hmFlag = this.getHashMapStatusLocal();
			Boolean objBoolean = hmFlag.get(sStatusName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}

	
	
	@Override
	public boolean getStatusLocal(String sStatusName) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName)) break main;
										
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			Boolean objBoolean = hmStatus.get(sStatusName.toUpperCase());
			if(objBoolean==null){
				bFunction = false;
			}else{
				bFunction = objBoolean.booleanValue();
			}
							
		}	// end main:
		
		return bFunction;	
	}
	
	
	//###### aus IObjectWithStatusZZZ
	@Override
	public HashMap<String, Boolean> getHashMapStatusLocal() {
		return this.hmStatusLocal;
	}

	@Override
	public void setHashMapStatusLocal(HashMap<String, Boolean> hmStatusLocal) {
		this.hmStatusLocal = hmStatusLocal;
	}
	
	@Override 
	public HashMap<String, String> getHashMapStatusLocalMessage(){
		return this.hmStatusLocalMessage;
	}
	
	@Override
	public void setHashMapStatusLocalMessage(HashMap<String, String> hmStatusLocalMessage) {
		this.hmStatusLocalMessage = hmStatusLocalMessage;
	}
	
	//### aus ICircularBufferStatusBooleanMessageUserZZZ
	@Override
	public CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> getCircularBufferStatusLocal(){		
		return this.cbStatusLocal;
	}
	
	@Override
	public void setCircularBufferStatusLocal(CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> cb){
		this.cbStatusLocal = cb;
	}
	
	
	//++++++++++++++++++++++++++++++++
	//### aus ICircularBufferStatusBooleanUserBasicZZZ 
	@Override
	public void debugCircularBufferStatusLocal() throws ExceptionZZZ {
		int iStepsMax = this.getCircularBufferStatusLocal().getCapacity();
		this.debugCircularBufferStatusLocal(iStepsMax);
	}
	
	//### aus ICircularBufferStatusBooleanMessageUserZZZ
	@Override
	public void debugCircularBufferStatusLocal(int iStepsMax) throws ExceptionZZZ {		
		String sLog="";
		int iStepsToSearchBackwardsTEST=-1;
		boolean bGoonTEST = false;
		IEnumSetMappedStatusLocalZZZ objStatusLocalPreviousTEST = null;
		do {	
			iStepsToSearchBackwardsTEST = iStepsToSearchBackwardsTEST + 1; 
			int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsToSearchBackwardsTEST);
			sLog = ReflectCodeZZZ.getPositionCurrent()+"TEST: Vorheriger Status= " + iStepsToSearchBackwardsTEST + " | Verwendeter Index= " + iIndex;							
			this.logProtocol(sLog);
			
			objStatusLocalPreviousTEST = (IEnumSetMappedStatusLocalZZZ) this.getStatusLocalEnumPrevious(iStepsToSearchBackwardsTEST);
			if(objStatusLocalPreviousTEST==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+"TEST: Kein weiterer entsprechend weit entfernter vorheriger Status vorhanden";									
				this.logProtocol(sLog);
				bGoonTEST=true;
			}else {				
				sLog = ReflectCodeZZZ.getPositionCurrent()+"TEST : Der " + iStepsToSearchBackwardsTEST + " Schritte vorherige Status im Main ist. GroupId/Abbreviation: " + objStatusLocalPreviousTEST.getStatusGroupId() + "/'" + objStatusLocalPreviousTEST.getAbbreviation()+"'.";									
				this.logProtocol(sLog);		
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+"TEST : Message='" + this.getStatusLocalMessagePrevious(iStepsToSearchBackwardsTEST) + "'";									
				this.logProtocol(sLog);	
			}
			if(iStepsToSearchBackwardsTEST>=iStepsMax) bGoonTEST=true;											
		}while(!bGoonTEST);						
	}

	@Override
	public int getStatusLocalGroupIdFromCurrent() {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObject();
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusLocalZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;
	}
	
	@Override
	public int getStatusLocalGroupIdFromPrevious() {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObjectPrevious();
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusLocalZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;
	}
	
	@Override
	public int getStatusLocalGroupIdFromPrevious(int iIndexStepsBack) {
		int iReturn = -1;
		main:{
			IStatusBooleanMessageZZZ objMessage = this.getStatusLocalObjectPrevious(iIndexStepsBack);
			if(objMessage==null) break main;
			
			IEnumSetMappedStatusLocalZZZ objEnum = objMessage.getEnumObject();
			if(objEnum==null) break main;
			
			iReturn = objEnum.getStatusGroupId();
		}//end main
		return iReturn;

	}
	
	@Override
	public int searchStatusLocalGroupIdPreviousDifferentFromCurrent() throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			int iReturnTemp=iReturn;
			String sLog;
			
			//+++ Hole die aktuelle GroupId
			int iGroupIdCurrent = this.getStatusLocalGroupIdFromCurrent();
			if(iGroupIdCurrent==-1) break main;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			//boolean bGoon = false;
			IEnumSetMappedStatusLocalZZZ objStatusLocalPrevious = null;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();
			//do {					
				for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
					int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
					sLog = ReflectCodeZZZ.getPositionCurrent()+"Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;										
					this.logProtocol(sLog);
					
					objStatusLocalPrevious = (IEnumSetMappedStatusLocalZZZ) this.getStatusLocalEnumPrevious(iStepsPrevious);
					if(objStatusLocalPrevious==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+"Kein entsprechend weit entfernter vorheriger Status vorhanden";											
						this.logProtocol(sLog);
						break main;
					}else {				
						//Frage nach dem Status im Backend nach...
						iReturnTemp = objStatusLocalPrevious.getStatusGroupId();
						
						sLog = ReflectCodeZZZ.getPositionCurrent()+"Der " + iStepsPrevious + " Schritte vorherige Status im Main ist. GroupId/Abbreviation: " + objStatusLocalPrevious.getStatusGroupId() + "/'" + objStatusLocalPrevious.getAbbreviation()+"'.";											
						this.logProtocol(sLog);	
						
						if(iReturnTemp!=iGroupIdCurrent) {
							iReturn = iReturnTemp;
							break main;
						}
					}
				}//end for															
			//}while(!bGoon);
			
		}//end main:
		return iReturn;
	}

	//+++
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent() throws ExceptionZZZ {
		return this.searchStatusLocalGroupCurrent(false);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupCurrent(boolean bWithInterruption) throws ExceptionZZZ {
		int iStatusLocalGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, bWithInterruption);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious() throws ExceptionZZZ {		
		return this.searchStatusLocalGroupPrevious(false);
	}
	
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupPrevious(boolean bWithInterruption) throws ExceptionZZZ {
		int iStatusLocalGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, bWithInterruption);
	}

	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupById(iStatusLocalGroupId, false);
	}
	@Override
	public ArrayList<IStatusBooleanZZZ> searchStatusLocalGroupById(int iStatusLocalGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		ArrayList<IStatusBooleanZZZ>listaReturn=null;
		main:{						
			//+++ Hole die aktuelle GroupId			
			if(iStatusLocalGroupId<=-1) break main;
			listaReturn = new ArrayList<IStatusBooleanZZZ>();
			
			
			String sLog;
			int iGroupIdTemp=-1;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			//boolean bGoon = false;
			//IEnumSetMappedStatusZZZ objStatusLocalPrevious = null;
			IStatusBooleanZZZ objStatusLocalPrevious = null;
			boolean bGroupPreviousFound=false;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();
			//do {					
				for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
					int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
					sLog = ReflectCodeZZZ.getPositionCurrent()+"Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;										
					this.logProtocol(sLog);
					
					objStatusLocalPrevious = (IStatusBooleanZZZ) this.getStatusLocalObjectPrevious(iStepsPrevious);					
					if(objStatusLocalPrevious==null) {
						sLog = ReflectCodeZZZ.getPositionCurrent()+"Kein entsprechend weit entfernter vorheriger Status vorhanden";											
						this.logProtocol(sLog);
						break main;
					}else {				
						//Frage nach dem Status im Backend nach...
						iGroupIdTemp = objStatusLocalPrevious.getEnumObject().getStatusGroupId();
						
						sLog = ReflectCodeZZZ.getPositionCurrent()+"Der " + iStepsPrevious + " Schritt(e) vorherige Status im Main ist. GroupId/Abbreviation: " + iGroupIdTemp + "/'" + objStatusLocalPrevious.getEnumObject().getAbbreviation()+"'.";											
						this.logProtocol(sLog);	
						
						if(iGroupIdTemp==iStatusLocalGroupId) {
							sLog = ReflectCodeZZZ.getPositionCurrent()+"Event mit gemappten Status gefunden: " + objStatusLocalPrevious.getEnumObject().getAbbreviation();							
							this.logProtocol(sLog);
							
							
//							//ggfs. nicht aufnehmen, also quasi weiter schrittweise zurück, wenn der Status ein Steuerevent ist...,d.h. ohne Icon
//							if(objStatusLocalPrevious!=null) {
//								objEnum = (ClientTrayStatusTypeZZZ) hmEnum.get(objStatusLocalPrevious);			
//								if(objEnum!=null) {					
//									if(StringZZZ.isEmpty(objEnum.getIconFileName())){
//										iStepsToSearchBackwards=1;
//										
//										sLog = ReflectCodeZZZ.getPositionCurrent()+": Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";
//										System.out.println(sLog);
//										this.getMainObject().logProtocolString(sLog);							
//									}else {
//										bGoon=true;
//									}
//								}
//							}
							
							//ggfs. doch nicht aufnehmen, wenn es sich um ein steuer-Eintrag handelt.
							if(!objStatusLocalPrevious.getEnumObject().getAbbreviation().equalsIgnoreCase("PREVIOUSEVENTRTYPE")) {														
								listaReturn.add((IStatusBooleanZZZ) objStatusLocalPrevious);
								if(!bWithInterruption) {
									bGroupPreviousFound=true;
								}
							}else {
								sLog = ReflectCodeZZZ.getPositionCurrent()+"Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";								
								this.logProtocol(sLog);	
							}
						}else {
							//Wurde im Fall: "Ohne Unterbrechung" dann wieder eine andere GroupId gefunden, ist ende
							if(bGroupPreviousFound)break main;
						}
					}
				}//end for															
			//}while(!bGoon);
			
		}//end main:
		return listaReturn;
	}
	
	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer() throws ExceptionZZZ {
		int iGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupIndexLowerInBuffer(iGroupId);
	}
	
	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexLowerInBuffer(iGroupId, false);
	}

	@Override
	public int searchStatusLocalGroupIndexLowerInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexInBuffer_(iGroupId,bWithInterruption,"LOWER");
	}
	
	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer() throws ExceptionZZZ {
		int iGroupId = this.getStatusLocalGroupIdFromCurrent();
		return this.searchStatusLocalGroupIndexUpperInBuffer(iGroupId);
	}
	
	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexUpperInBuffer(iGroupId, false);
	}

	@Override
	public int searchStatusLocalGroupIndexUpperInBuffer(int iGroupId, boolean bWithInterruption) throws ExceptionZZZ {
		return this.searchStatusLocalGroupIndexInBuffer_(iGroupId,bWithInterruption,"UPPER");
	}
		
	public int searchStatusLocalGroupIndexInBuffer_(int iGroupId, boolean bWithInterruption, String sFlagControl) throws ExceptionZZZ {
		int iReturn = -1;
		main:{			
			if(iGroupId<=-1) break main;
			
			boolean bLower=false;
			if(!(StringZZZ.equals(sFlagControl, "") || StringZZZ.equalsIgnoreCase(sFlagControl, "LOWER") || StringZZZ.equalsIgnoreCase(sFlagControl, "UPPER"))){
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_VALUE + "Flag '" + sFlagControl +"', does not exist. Expected 'UPPER','LOWER',''.", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}else if(StringZZZ.equals(sFlagControl,"") || StringZZZ.equalsIgnoreCase(sFlagControl, "LOWER")) {
				bLower=true;
			}
			
			
//			String sLog;
			int iGroupIdTemp=-1;
			
			//+++ Durchsuche die vorherigen Schritte nach deren GroupId
			IStatusBooleanZZZ objStatusLocalPrevious = null;
			boolean bGroupPreviousFound=false;
			int iStepsToSearchBackwards = this.getCircularBufferStatusLocal().getCapacity();	
			for(int iStepsPrevious = 0;iStepsPrevious<=iStepsToSearchBackwards;iStepsPrevious++) {
				int iIndex = this.getCircularBufferStatusLocal().computeIndexForStepPrevious(iStepsPrevious);
//				sLog = ReflectCodeZZZ.getPositionCurrent()+"Vorheriger Status= " + iStepsPrevious + " | Verwendeter Index= " + iIndex;					
//				this.logProtocolString(sLog);
				
				objStatusLocalPrevious = (IStatusBooleanZZZ) this.getStatusLocalObjectPrevious(iStepsPrevious);					
				if(objStatusLocalPrevious==null) {
//					sLog = ReflectCodeZZZ.getPositionCurrent()+"Kein entsprechend weit entfernter vorheriger Status vorhanden";					
//					this.logProtocolString(sLog);
					break main;
				}else {				
					//Frage nach dem Status im Backend nach...
					iGroupIdTemp = objStatusLocalPrevious.getEnumObject().getStatusGroupId();
					
//					sLog = ReflectCodeZZZ.getPositionCurrent()+"Der " + iStepsPrevious + " Schritt(e) vorherige Status im Main ist. GroupId/Abbreviation: " + iGroupIdTemp + "/'" + objStatusLocalPrevious.getEnumObject().getAbbreviation()+"'.";					
//					this.logProtocolString(sLog);	
					
					if(iGroupIdTemp==iGroupId) {
//						sLog = ReflectCodeZZZ.getPositionCurrent()+"Event mit gemappten Status gefunden: " + objStatusLocalPrevious.getEnumObject().getAbbreviation();
//						this.logProtocolString(sLog);
						
						//ggfs. doch nicht aufnehmen, wenn es sich um ein steuer-Eintrag handelt.
						if(!objStatusLocalPrevious.getEnumObject().getAbbreviation().equalsIgnoreCase("PREVIOUSEVENTRTYPE")) {														
							iReturn = iIndex;																		
							if(!bLower) {
								break main;								
							}else {
								bGroupPreviousFound = true;	//Das ist fuer den UPPER Eintrag nicht wichtig. Aber fuer den UPPER Eintrag mit INTERRUPTION
							}							
						}else {
//							sLog = ReflectCodeZZZ.getPositionCurrent()+"Steuerevent als gemappten Status aus dem Event-Objekt erhalten. Gehe noch einen weitere " + iStepsToSearchBackwards + " Schritt(e) zurueck.";
//							this.logProtocolString(sLog);	
						}
					}else {
						if(bLower) {							
							//das ist fuer den UPPER Eintrag nicht wichtig
							if(bWithInterruption) {
								if(bGroupPreviousFound) {
//									sLog = ReflectCodeZZZ.getPositionCurrent()+"Weitere Gruppe gefunden. Unterbrechung erlaubt. Weiter mit diesem vorlaeufigen Index bis zum Ende: " + iIndex;
//									this.logProtocolString(sLog);
								}
							}else {	
								if(bGroupPreviousFound) {
//									sLog = ReflectCodeZZZ.getPositionCurrent()+"Weitere Gruppe gefunden. Keine Unterbrechung suchen. Beende mit diesem Index: " + iIndex;
//									this.logProtocolString(sLog);								
									break main;
								}
							}
						}
					}
				}
			}					
		}//end main:
		return iReturn;
		
		
		
		
	}	
	
	
	//+++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++
	@Override
	public boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(enumStatusIn==null) break main;
			bReturn = this.offerStatusLocal_(enumStatusIn, bStatusValue, sStatusMessage);				
		}//end main;
		return bReturn;
	}
	
	@Override
	public boolean offerStatusLocal( Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(enumStatusIn==null) break main;
			bReturn = this.offerStatusLocal_(enumStatusIn, bStatusValue, null);				
		}//end main;
		return bReturn;
	}
		
	private boolean offerStatusLocal_(Enum enumStatusIn, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(enumStatusIn==null) break main;

			String sStatusName = enumStatusIn.name();
			if(sStatusMessage==null) {
				bReturn  = this.offerStatusLocal(sStatusName, bStatusValue);
			}else {
				bReturn  = this.offerStatusLocal(sStatusName, bStatusValue, sStatusMessage);
			}
		}	// end main:
		return bReturn;
	}

	
	@Override
	public boolean offerStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		return this.offerStatusLocal_(sStatusName, bStatusValue, null);
	}
	
	@Override
	public boolean offerStatusLocal(String sStatusName, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
		return this.offerStatusLocal_(sStatusName, bStatusValue, sStatusMessage);	
	}

	private boolean offerStatusLocal_(String sStatusName, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String sLog;
						
			//20240416;//Alles auf diese String-Methode umstellen. Also darf die String Methode dies selbst nicht mehr aufrufen.
			/*Dann in der String Methode:
				1. den enumStatusLocalIn holen, um die StatusBoolean Message zu erzeugen
				2. am Anfang proofStatusLocalQueryOffer aufrufen.
				                   Darin wird die Existenz des Status geprüft.				                   
				                   Dann wird die ...proof..OfferCustom aufgerufen.
				3. in Abstracten Runner Methoden wird super.proof aufgerufen.
				                   dann noch auf das Flag "Nicht mehr offer wenn REQUEST_STOP" geprüft.								
				                   Dann wird auf den Status REQUEST_STOP geprüft und ggfs. ein False 
			
            Genauso muss das beim proof..QuryReact passieren.
            
            Dann muss alles umbenannt werden, so dass query voransteht im Methodennamen.*/
			
			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ("StatusName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			boolean bQuery = this.queryOfferStatusLocal(sStatusName, bStatusValue);			
			sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> queryOffer returns '" + bQuery + "' for '" + sStatusName + "' and StatusValue '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
			if(!bQuery) {
//				this.logProtocolString(sLog);
				break main;
			}else {				
				this.logProtocol(sLog);
			}
			
			//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
			//Dann erzeuge den Event und feuer ihn ab.	
			if(this.getSenderStatusLocalUsed()==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent() +  this.getClass().getSimpleName()+"=> Would like to fire event but no objEventStatusLocalBroker available, any registered? For '" + sStatusName + "' and StatusValue '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);		
				break main;
			}
			
			//Erzeuge fuer das Enum einen eigenen Event. Die daran registrierten Klassen koennen in einer HashMap definieren, ob der Event fuer sie interessant ist.		
			if(bStatusValue) { //!!! nur im TRUE Fall wird eine Logausgabe erzeugt... sonst wird das Log zu voll.			
				sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Creates event for '" + sStatusName + "' and StatusValue '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);
			}
			IEventObjectStatusBasicZZZ event;
			if(sStatusMessage==null) {
				event = new EventObjectStatusLocalZZZ(this, sStatusName, bStatusValue);
			}else{
				event = new EventObjectStatusLocalZZZ(this, sStatusName, bStatusValue, sStatusMessage);			
			}
					
			sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getSimpleName()+"=> Fires event for '" + sStatusName + "' and value '" + bStatusValue + "'";
			this.logProtocol(sLog);
			this.getSenderStatusLocalUsed().fireEvent(event);
						
			//########################################################################################
			//Wenn ein Status verarbeitet wird, dann wird er auch "historisch" gespeichert.
			
			//20240310: IEnumsetMappedStatusZZZ aus dem String-Namen ermitteln	
			//IStatusBooleanMessageZZZ objStatus = new StatusBooleanMessageZZZ(enumStatusLocalIn, bValue, sMessage);
			HashMap<String,IStatusBooleanMessageZZZ> hmStatus = StatusLocalAvailableHelperZZZ.searchHashMapBooleanMessage(this, true);
			if(hmStatus==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> Es war keine HashMap mit Statusname erstellbar, fuer: '" + sStatusName + "' and value '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);
				break main;
			}

			if(hmStatus.isEmpty()) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> HashMap mit Statusnamen ist leer, fuer: '" + sStatusName + "' and value '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);
				break main;
			}else {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> HashMap mit Statusnamen erstellt, fuer: '" + sStatusName + "' and value '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);				
			}
			
			IStatusBooleanMessageZZZ objStatus = hmStatus.get(sStatusName);
			if(objStatus==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> Der Status wurde nicht in der HashMap der Statusname gefunden, fuer: '" + sStatusName + "' and value '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);
				break main;
			}
			
			//Übernimm dieses Statusobjekct in den Ringspeicher						
			bReturn = this.getCircularBufferStatusLocal().offer(objStatus);
			if(!bReturn) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> Der Status wurde nicht erfolgreich im CircularBuffer abgelegt, fuer: '" + sStatusName + "' and value '" + bStatusValue + "', StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);				
				break main;
			}
		}//end main:
		return bReturn;		
	}

	
	
	//+++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++
		
	
	
	@Override
	public boolean setStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			if(this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED)) {
				bProof = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
				if(!bProof) break main;
			}
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
									
			bReturn=this.offerStatusLocal(sStatusName, bStatusValue, null);
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean setStatusLocal(String sStatusName, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			boolean bProofValue = true;
			if(this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED)) {
				bProofValue = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
			}
			
			boolean bProofMessage = true;
			if(this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_MESSAGECHANGED)) {
				bProofMessage = this.proofStatusLocalMessageChanged(sStatusMessage);				
			}
			if(! (bProofValue | bProofMessage) ) break main;
			
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
									
			bReturn=this.offerStatusLocal(sStatusName, bStatusValue, sStatusMessage);
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;			
			boolean bProof = this.proofStatusLocalExists(sStatusName);
			if(!bProof)break main;					
			
			//Setze das Flag nun in die HashMap
			HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
			Set<String> setKey = hmStatus.keySet();
			for(String sKey : setKey) {
				hmStatus.put(sKey, !bStatusValue);				
			}
			hmStatus.put(sStatusName.toUpperCase(), bStatusValue);			
			bReturn=true;
		}//end main:
		return bReturn;		
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(Enum enumStatus) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(Enum enumStatus, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(enumStatus, null, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalAsGroupTo(Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalAsGroupTo_(enumStatus, sStatusMessage, bStatusValue);	
	}
	
	@Override
	public boolean switchStatusLocalAllGroupTo(Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		return this.switchStatusLocalAsGroupTo_(enumStatus, sStatusMessage, bStatusValue);
	}
	
	private boolean switchStatusLocalAsGroupTo_(Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(-1, enumStatus, sStatusMessage, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusLocalZZZ enumStatusMapped) throws ExceptionZZZ{
		int iGroupId = enumStatusMapped.getStatusGroupId();
		Enum enumStatus = (Enum)enumStatusMapped;
		return this.switchStatusLocalForGroupTo_(iGroupId, enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusLocalZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ{
		int iGroupId = enumStatusMapped.getStatusGroupId();
		Enum enumStatus = (Enum)enumStatusMapped;
		return this.switchStatusLocalForGroupTo_( iGroupId, enumStatus, null, bStatusValue);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(iGroupId, enumStatus, null, true);
	}
	
	@Override
	public boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus, boolean bStatusValue) throws ExceptionZZZ{
		return this.switchStatusLocalForGroupTo_(iGroupId, enumStatus, null, bStatusValue);
	}
			
	@Override
	public boolean switchStatusLocalForGroupTo(IEnumSetMappedStatusLocalZZZ enumStatusMapped, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			int iGroupId = enumStatusMapped.getStatusGroupId();
			Enum enumStatus = (Enum) enumStatusMapped;
			bReturn = this.switchStatusLocalForGroupTo(iGroupId, enumStatus, sStatusMessage, bStatusValue);
		}//end main:
		return bReturn;
	}
	
	/* (non-Javadoc)
	 * @see basic.zKernel.status.IStatusLocalBasicUserZZZ#switchStatusLocalForGroupTo(int, int, java.lang.Enum, java.lang.String, boolean)
	 */
	@Override
	public boolean switchStatusLocalForGroupTo(int iGroupId, Enum enumStatus, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		return this.switchStatusLocalForGroupTo_(iGroupId, enumStatus, sStatusMessage, bStatusValue);
	}
	
	private boolean switchStatusLocalForGroupTo_(int iGroupId, Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
			if(enumStatusIn==null) break main;
			String sStatusName = enumStatusIn.name().toUpperCase();
			
			bReturn = this.offerStatusLocal(enumStatusIn, bStatusValue, sStatusMessage);
			if(!bReturn)break main;
			
			//Hole die StatusNamen der angegebenen Gruppe
			if(iGroupId==-1) {
				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey) {				
					if(!sKey.equals(sStatusName)) hmStatus.put(sKey, !bStatusValue); //Setze die anderen Werte, als der uebergebene Status
				}			
			}else {
				String[] saStatusForGroup = StatusLocalAvailableHelperZZZ.searchForGroup(this.getClass(), iGroupId);
				if(saStatusForGroup==null) break main;
				
				//Setze den Status nun in die HashMap DIESER GUPPE
				HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey) {				
					if(!sKey.equals(sStatusName)) {//Setze die anderen Werte, als der uebergebene Status
						if(StringArrayZZZ.contains(saStatusForGroup, sKey)) { //...aber nur in die Gruppe fuer die GroupId
							hmStatus.put(sKey, !bStatusValue);
						}
					}
				}					
			}															
		}//end main:
		return bReturn;		
	}

	@Override
	public boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		return this.setStatusLocal(enumStatusIn.name(), bStatusValue, sStatusMessage);
	}

	@Override
	public boolean setStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusMapped, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		return this.setStatusLocal(enumStatusMapped.getName(), bStatusValue, sStatusMessage);
	}
	
	@Override
	public boolean setStatusLocal(Enum enumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		return this.setStatusLocal(enumStatusIn.name(),bStatusValue);
	}

	@Override
	public boolean setStatusLocalEnum(IEnumSetMappedStatusLocalZZZ enumStatusMapped, boolean bStatusValue) throws ExceptionZZZ {
		return this.setStatusLocal(enumStatusMapped.getName(), bStatusValue);
	}
		
	@Override
	public boolean[] setStatusLocal(Enum[] objaEnumStatusIn, boolean bStatusValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumStatusIn)) {
				baReturn = new boolean[objaEnumStatusIn.length];
				int iCounter=-1;
				for(Enum objEnumStatus:objaEnumStatusIn) {
					iCounter++;
					boolean bReturn = this.setStatusLocal(objEnumStatus, bStatusValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean[] setStatusLocal(String[] saStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!StringArrayZZZ.isEmptyTrimmed(saStatusName)) {
				baReturn = new boolean[saStatusName.length];
				int iCounter=-1;
				for(String sStatusName:saStatusName) {
					iCounter++;
					boolean bReturn = this.setStatusLocal(sStatusName, bStatusValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public abstract boolean queryOfferStatusLocalCustom() throws ExceptionZZZ;
	
	
	@Override
	public boolean proofStatusLocalExists(Enum objEnumStatus) throws ExceptionZZZ {
		return this.proofStatusLocalExists(objEnumStatus.name());
	}

	@Override
	public boolean proofStatusLocalExists(String sStatusName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			bReturn = StatusLocalAvailableHelperZZZ.proofExists(this.getClass(), sStatusName);				
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalDirectExists(Enum objEnumStatus) throws ExceptionZZZ {
		return this.proofStatusLocalDirectExists(objEnumStatus.name());
	}
	
	@Override
	public boolean proofStatusLocalDirectExists(String sStatusName) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			bReturn = StatusLocalAvailableHelperZZZ.proofDirectExists(this.getClass(), sStatusName);				
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalValue(Enum objEnumStatus, boolean bValue) throws ExceptionZZZ {
		return this.proofStatusLocalValue(objEnumStatus.name(), bValue);
	}
	
	@Override
	public boolean proofStatusLocalValue(String sStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			boolean bFlag = this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUE);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
			
			if(!bStatusValue) { //Ggfs False Werte nicht weiter verarbeiten, also NUR True Werte
				boolean bFlagTrue = this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUETRUE);
				if(bFlagTrue) break main;			
			}
			
			bReturn = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
			if(!bReturn) {
				String sLog = ReflectCodeZZZ.getPositionCurrent() + "This status has not changed, for: '" + sStatusName + "' and value '" + bStatusValue + "'"; //, StatusMessage='\\\"+sStatusMessage+\\\"'\\\";);";					
				this.logProtocol(sLog);
				break main;
			}
			
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalValueChanged(Enum objEnumStatus, boolean bStatusValue) throws ExceptionZZZ {
		return this.proofStatusLocalValueChanged(objEnumStatus.name(), bStatusValue);
	}

	
	
	@Override
	public boolean proofStatusLocalValueChanged(String sStatusName, boolean bStatusValue) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusName))break main;
			boolean bFlag = this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
						
			HashMap<String,Boolean>hmStatusLocal = this.getHashMapStatusLocal();
			bReturn = StatusLocalAvailableHelperZZZ.proofOnChange(hmStatusLocal, sStatusName, bStatusValue);
			if(!bReturn) {
				//Hier auch keinen Log Eintrag erzeugen, die blaehen das Log auf.
				//String sLog = ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatus ("+this.getClass().getSimpleName()+") - This status has a value to be ignored: '" + sStatusName + "'";				
				//this.logProtocolString(sLog);
				break main;
			}
			
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalMessageChanged(String sStatusMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStatusMessage))break main;
			boolean bFlag = this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_MESSAGECHANGED);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
						
			CircularBufferForStatusBooleanMessageZZZ<IStatusBooleanMessageZZZ> cbStatusLocalMessage = this.getCircularBufferStatusLocal();
			IStatusBooleanMessageZZZ objStatus = (IStatusBooleanMessageZZZ) cbStatusLocalMessage.getLast(); 
			String sStatusMessageLast = objStatus.getMessage();
						
			bReturn = StringZZZ.equals(sStatusMessage, sStatusMessageLast); 
			if(!bReturn) {
				//Hier auch keinen Log Eintrag erzeugen, die blaehen das Log auf.
				//String sLog = ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatus ("+this.getClass().getSimpleName()+") - This Message has not changed: '" + sMessage + "'";				
				//this.logProtocolString(sLog);
				break main;
			}
			
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean proofStatusLocalMessageChanged(IStatusBooleanMessageZZZ objStatusWithMessage) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(objStatusWithMessage==null)break main;
			boolean bFlag = this.getFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_MESSAGECHANGED);
			if(!bFlag) {
				bReturn = true;
				break main;
			}
						
			String sStatusMessage = objStatusWithMessage.getMessage();
			bReturn = this.proofStatusLocalMessageChanged(sStatusMessage);
		}//end main:
		return bReturn;
	}
	
	@Override
	public boolean queryOfferStatusLocal(String sStatusName, boolean bStatusValue) throws ExceptionZZZ{
		boolean bReturn = false;		
		main:{
			String sLog;			
			if(StringZZZ.isEmpty(sStatusName)) {
				ExceptionZZZ ez = new ExceptionZZZ("StatusName", iERROR_PARAMETER_MISSING,   this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			boolean bExists = this.proofStatusLocalExists(sStatusName);															
			if(!bExists) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> queryOfferStatusLocal would like to fire event, but this status is not available. For: '" + sStatusName + "' and value '" + bStatusValue + "'"; //, StatusMessage='"+sStatusMessage+"'";
				this.logProtocol(sLog);			
				break main;
			}
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
			//Aus iSenderObjectStatusLocalUserZZZ
			//Es ist nur die Frage, ob Status - Werte mit false versendet werden sollen
			
			sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> queryOfferStatusLocal for: '" + sStatusName + "' and value '" + bStatusValue + "'"; //, StatusMessage='\"+sStatusMessage+\"'\";);
			if(!bStatusValue) {
				if(!this.getFlag(ISenderObjectStatusLocalUserZZZ.FLAGZ.STATUSLOCAL_SEND_VALUEFALSE)) {
					//Diese Ausgabe blaeht das Log unnoetig auf.
					//sLog = ReflectCodeZZZ.getPositionCurrent() + "ObjectWithStatus ("+this.getClass().getSimpleName()+") would like to fire event for status '" + sStatusName + "', but 'false-values' should not be send (Flag: "+ ISenderObjectStatusLocalUserZZZ.FLAGZ.STATUSLOCAL_SEND_VALUEFALSE.name() +")";
					//this.logProtocolString(sLog);
					break main; //Also im Normalfall nur Events mit TRUE Wert behandeln
				}
			}else {
				//true fall. Jetzt kann man einen Breakpoint setzen
				this.logProtocol(sLog);
			}
			
			

			//++++++++++++++++++++++++++++++++++++++++++++++
			boolean bQuery = this.queryOfferStatusLocalCustom();
			if(!bQuery) { 			
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getSimpleName()+"=> queryOfferStatusLocal would like to fire event but custom query returned '" + bQuery + "', for: '" + sStatusName + "' and value '" + bStatusValue + "'"; //, StatusMessage='\\\"+sStatusMessage+\\\"'\\\";);
				this.logProtocol(sLog);			
				break main;
			}else {
				
			}
			
			bReturn = true;
		}
		return bReturn;
	}

	@Override
	public String[] getStatusLocal(boolean bStatusValueToSearchFor) throws ExceptionZZZ {
		return this.getStatusLocal_(bStatusValueToSearchFor, false);
	}

	
	
	
	@Override
	public String[] getStatusLocal(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ {
		return this.getStatusLocal_(bStatusValueToSearchFor, bLookupExplizitInHashMap);
	}

	@Override
	public String[] getStatusLocalAll() throws ExceptionZZZ {
		String[] saReturn = null;
		main:{	
			saReturn = StatusLocalAvailableHelperZZZ.searchDirect(this.getClass());				
		}//end main:
		return saReturn;
	}
	
	private String[]getStatusLocal_(boolean bStatusValueToSearchFor, boolean bLookupExplizitInHashMap) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			ArrayList<String>listasTemp=new ArrayList<String>();
			
			//FALLUNTERSCHEIDUNG: Alle gesetzten Status werden in der HashMap gespeichert. Aber die noch nicht gesetzten FlagZ stehen dort nicht drin.
			//                                  Diese kann man nur durch Einzelprüfung ermitteln.
			if(bLookupExplizitInHashMap) {
				HashMap<String,Boolean>hmStatus=this.getHashMapStatusLocal();
				if(hmStatus==null) break main;
				
				Set<String> setKey = hmStatus.keySet();
				for(String sKey : setKey){
					boolean btemp = hmStatus.get(sKey);
					if(btemp==bStatusValueToSearchFor){
						listasTemp.add(sKey);
					}
				}
			}else {
				//So bekommt man alle Flags zurück, also auch die, die nicht explizit true oder false gesetzt wurden.						
				String[]saStatus = this.getStatusLocalAll();
				
				//20211201:
				//Problem: Bei der Suche nach true ist das egal... aber bei der Suche nach false bekommt man jedes der Flags zurück,
				//         auch wenn sie garnicht gesetzt wurden.
				//Lösung:  Statt dessen explitzit über die HashMap der gesetzten Werte gehen....						
				for(String sStatus : saStatus){
					boolean btemp = this.getStatusLocal(sStatus);
					if(btemp==bStatusValueToSearchFor ){ //also 'true'
						listasTemp.add(sStatus);
					}
				}
			}
			saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//end main:
		return saReturn;
	}
}
