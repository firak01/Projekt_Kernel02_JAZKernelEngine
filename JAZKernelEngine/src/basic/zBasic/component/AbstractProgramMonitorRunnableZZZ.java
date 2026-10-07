package basic.zBasic.component;

import java.util.ArrayList;
import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusBasicZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import custom.zKernel.Log;

public abstract class AbstractProgramMonitorRunnableZZZ extends AbstractProgramMonitorZZZ implements IProgramMonitorRunnableZZZ{
	private static final long serialVersionUID = 6586079955658760005L;
		
	protected volatile ArrayList<IProgramZZZ> listaProgram = null;//Die Liste der Runnable-Threadfaehigen Objekte, mit Status....
			
	public AbstractProgramMonitorRunnableZZZ() throws ExceptionZZZ {
		super();		
		AbstractProgramRunnableMonitorNew_();
	}

	public AbstractProgramMonitorRunnableZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	
		AbstractProgramRunnableMonitorNew_();
	}
	
	private boolean AbstractProgramRunnableMonitorNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			if(this.getFlag("init")) break main;
								
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//#### GETTER / SETTER
	
	//#### METHODEN
	//### aus IProgramRunnableZZZ
	@Override
	public void run() {		
		try {
			this.startCustom();
		} catch (ExceptionZZZ ez) {
			try {
				Log.protocol(this, ez.getDetailAllLast());
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
		}
	}//END run
	
	@Override 
	public boolean start() throws ExceptionZZZ {
		return this.startAsThread(); //Merke: Anders als ein einfaches Program wird ein runnable Program in seinem eigenen Thread gestarted.
	}
	
	@Override
	public boolean startAsThread() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
				Thread objThreadMonitor = new Thread(this);
				objThreadMonitor.start();//Damit wird run() aufgerufen, was wiederum start_() als private Methode aufruft
				
				bReturn = true;								
		}//end main:
		return bReturn;
	}
	
	@Override
	abstract public boolean startCustom() throws ExceptionZZZ;
	
	public boolean queryReactOnStatusLocal(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn=false;		
		main:{
			boolean bQueryReactOn = super.queryReactOnStatusLocalEvent(eventStatusLocal);
			if(!bQueryReactOn) break main;
			
			String sLog;
			
			//Falls das REQUEST_STOP Flag gesetzt ist, nicht weiter reagieren...
			if(this.getFlag(IProgramRunnableZZZ.FLAGZ.REQUEST_STOP)) {
				sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getName()+"=> Flag '" + IProgramRunnableZZZ.FLAGZ.REQUEST_STOP.name() + "' gesetzt. Keine weitere Verarbeitung von Events. Breche ab.";
				Log.protocol(this, sLog);
				break main;
			}
			
			bReturn = true;
		}//end main
		return bReturn;
	}
		
	//###############################################
	//### FLAGZ: IProgramMonitorRunnableZZZ
	//###############################################
	
	@Override
	public boolean getFlag(IProgramMonitorRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IProgramMonitorRunnableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IProgramMonitorRunnableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IProgramMonitorRunnableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IProgramMonitorRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
	
	@Override
	public boolean proofFlagSetBefore(IProgramMonitorRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}		
	
	//######################################################################
		//### FLAGZ: aus IProgramRunnableZZZ                 ##########################
		//######################################################################
		@Override
		public boolean getFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.getFlag(objEnumFlag.name());
		}
		@Override
		public boolean setFlag(IProgramRunnableZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			return this.setFlag(objEnumFlag.name(), bFlagValue);
		}
		
		@Override
		public boolean[] setFlag(IProgramRunnableZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
			boolean[] baReturn=null;
			main:{
				if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
					baReturn = new boolean[objaEnumFlag.length];
					int iCounter=-1;
					for(IProgramRunnableZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
		public boolean proofFlagExists(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}	
		
		@Override
		public boolean proofFlagSetBefore(IProgramRunnableZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
		}	

	
	//##############################################################################
	//############################################################
	//### STATUS
	//############################################################
	
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectWithStatusZZZ#offerStatusLocal(int, java.lang.Enum, java.lang.String, boolean)
	 */
	@Override
	public boolean offerStatusLocal(Enum enumStatusIn, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) {
				break main;
			}
			
			IProgramMonitorZZZ.STATUSLOCAL enumStatus = (IProgramMonitorZZZ.STATUSLOCAL) enumStatusIn;
			
			bFunction = this.offerStatusLocal_(enumStatus, sStatusMessage, bStatusValue);				
		}//end main;
		return bFunction;
	}
	
	private boolean offerStatusLocal_(Enum enumStatusIn, String sStatusMessage, boolean bStatusValue) throws ExceptionZZZ {
		boolean bFunction = false;
		main:{
			if(enumStatusIn==null) break main;
			
		
	    //Merke: In anderen Klassen, die dieses Design-Pattern anwenden ist das eine andere Klasse fuer das Enum
		IProgramMonitorZZZ.STATUSLOCAL enumStatus = (IProgramMonitorZZZ.STATUSLOCAL) enumStatusIn;
		String sStatusName = enumStatus.name();
		bFunction = this.proofStatusLocalExists(sStatusName);															
		if(!bFunction) {
			String sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getName()+"=> ServerThreadProcessWatchMonitor for Process would like to fire event, but this status is not available: '" + sStatusName + "'";
			Log.protocol(this, sLog);			
			break main;
		}
			
		bFunction = this.proofStatusLocalValueChanged(sStatusName, bStatusValue);
		if(!bFunction) {
			String sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getName()+"=> ServerThreadProcessWatchMonitor would like to fire event, but this status has not changed: '" + sStatusName + "'";
			Log.protocol(this, sLog);
			break main;
		}	
		
		//++++++++++++++++++++	
		//Setze den Status nun in die HashMap
		HashMap<String, Boolean> hmStatus = this.getHashMapStatusLocal();
		hmStatus.put(sStatusName.toUpperCase(), bStatusValue);
		
		//Den enumStatus als currentStatus im Objekt speichern...
		//                   dito mit dem "vorherigen Status"
		//Setze nun das Enum, und damit auch die Default-StatusMessage
		String sStatusMessageToSet = null;
		if(StringZZZ.isEmpty(sStatusMessage)){
			if(bStatusValue) {
				sStatusMessageToSet = enumStatus.getStatusMessage();
			}else {
				sStatusMessageToSet = "NICHT " + enumStatus.getStatusMessage();
			}			
		}else {
			sStatusMessageToSet = sStatusMessage;
		}
		
		String sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getName()+"=> ServerMain verarbeite sStatusMessageToSet='" + sStatusMessageToSet + "'";
		Log.protocol(this, sLog);

		//Falls eine Message extra uebergeben worden ist, ueberschreibe...
		if(sStatusMessageToSet!=null) {
			sLog = ReflectCodeZZZ.getPositionCurrent()  + this.getClass().getName()+"=> ServerMain setze sStatusMessageToSet='" + sStatusMessageToSet + "'";
			Log.protocol(this, sLog);
		}
		//Merke: Dabei wird die uebergebene Message in den speziellen "Ringspeicher" geschrieben, auch NULL Werte...
		this.offerStatusLocalEnum(enumStatus, bStatusValue, sStatusMessageToSet);
		
		
		
		//Falls irgendwann ein Objekt sich fuer die Eventbenachrichtigung registriert hat, gibt es den EventBroker.
		//Dann erzeuge den Event und feuer ihn ab.	
		if(this.getSenderStatusLocalUsed()==null) {
			sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> ServerThreadProcessWatchMonitor for Process would like to fire event '" + enumStatus.getAbbreviation() + "', but no objEventStatusLocalBroker available, any registered?";
			Log.protocol(this, sLog);		
			break main;
		}
		
		//Erzeuge fuer das Enum einen eigenen Event. Die daran registrierten Klassen koennen in einer HashMap definieren, ob der Event fuer sie interessant ist.		
		sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> Erzeuge Event fuer '" + sStatusName + "', bValue='"+ bStatusValue + "', sMessage='"+sStatusMessage+"'";
		Log.protocol(this, sLog);
		IEventObjectStatusBasicZZZ event = new EventObjectStatusLocalZZZ(this, enumStatus, bStatusValue);			
//		event.setApplicationObjectUsed(this.getMainObject().getApplicationObject());
					
		//das ClientStarterObjekt nun auch noch dem Event hinzufuegen
//		sLog = ReflectCodeZZZ.getPositionCurrent() + " ServerThreadProcessWatchMonitor for Process iIndex= '" + iIndexOfProcess + "'";
//		System.out.println(sLog);
//		this.logProtocolString(sLog);
//		if(iIndexOfProcess>=0) {
//			event.setServerConfigStarterObjectUsed(this.getMainObject().getServerConfigStarterList().get(iIndexOfProcess));
//		}		
		
		sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> ServerThreadProcessWatchMonitor for Process fires event '" + enumStatus.getAbbreviation() + "'";
		Log.protocol(this, sLog);
		this.getSenderStatusLocalUsed().fireEvent(event);
				
		bFunction = true;				
	}	// end main:
	return bFunction;
	}
	
	
	//#########################################################
	
	

}
