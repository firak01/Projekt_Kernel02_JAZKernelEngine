package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public abstract class AbstractProgramWithFlagOnStatusLocalListeningRunnableZZZ extends AbstractProgramWithFlagOnStatusLocalListeningZZZ implements IProgramRunnableZZZ{
	private static final long serialVersionUID = 202987237863158494L;
	
	public AbstractProgramWithFlagOnStatusLocalListeningRunnableZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractProgramWithFlagOnStatusLocalListeningRunnableZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	public AbstractProgramWithFlagOnStatusLocalListeningRunnableZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);		
	}
	
	
	//### aus IProgramRunnableZZZ
	@Override
	public void run() {		
		try {
			this.startCustom();
		} catch (ExceptionZZZ ez) {
			try {
				this.protocol(ez.getDetailAllLast());
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
	public boolean startAsThread() throws ExceptionZZZ{
		
		Thread objThread = new Thread(this);
		objThread.start();//Damit wird run() aufgerufen, was wiederum start_() als private Methode aufruft
		
		return true;
	}
	
	@Override
	public abstract boolean startCustom() throws ExceptionZZZ;
	
	@Override
	public boolean queryReactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			boolean bQueryReactOn = super.queryReactOnStatusLocalEvent(eventStatusLocal);
			if(!bQueryReactOn)break main;

			String sLog;
			
			//Falls das REQUEST_STOP Flag gesetzt ist, nicht weiter reagieren...
			if(this.getFlag(IProgramRunnableZZZ.FLAGZ.REQUEST_STOP)) {
				sLog = ReflectCodeZZZ.getPositionCurrent() + this.getClass().getName()+"=> Flag '" + IProgramRunnableZZZ.FLAGZ.REQUEST_STOP.name() + "' gesetzt. Keine weitere Verarbeitung von Events. Breche ab.";
				this.protocol(sLog);
				break main;
			}
						
			bReturn = true;
		}//emd main:
		return bReturn;
	}
	
	
	//##########################################
	//### FLAGZ IProgramRunnableZZZ
	//##########################################
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
}
