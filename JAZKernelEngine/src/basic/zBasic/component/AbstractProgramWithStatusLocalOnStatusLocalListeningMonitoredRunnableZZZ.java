package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IStatusLocalMapForMonitoringStatusLocalUserZZZ;
import custom.zKernel.Log;

public abstract class AbstractProgramWithStatusLocalOnStatusLocalListeningMonitoredRunnableZZZ extends AbstractProgramWithStatusLocalOnStatusLocalListeningRunnableZZZ implements IStatusLocalMapForMonitoringStatusLocalUserZZZ {
	private static final long serialVersionUID = -1445384815158662362L;
	protected volatile HashMap<IEnumSetMappedStatusLocalZZZ, IEnumSetMappedStatusLocalZZZ> hmEnumSet =null;
	
	
	public AbstractProgramWithStatusLocalOnStatusLocalListeningMonitoredRunnableZZZ() throws ExceptionZZZ {
		super();		
	}

	public AbstractProgramWithStatusLocalOnStatusLocalListeningMonitoredRunnableZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	
		AbstractProgramRunnableWithStatusListeningCascadedNew_();
	}
	
	private boolean AbstractProgramRunnableWithStatusListeningCascadedNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			
			if(this.getFlag("init")) break main;
			
			bReturn = true;						
		}//end main:
		return bReturn;
	}

	//#### GETTER / SETTER
	
	
	
	//#### METHODEN
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
	
	
}
