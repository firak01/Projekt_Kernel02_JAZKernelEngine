package basic.zBasic.component;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import custom.zKernel.Log;

public abstract class AbstractProgramWithFlagRunnableZZZ extends AbstractProgramWithFlagZZZ implements IProgramRunnableZZZ {	
	private static final long serialVersionUID = 1185996372719861922L;

	public AbstractProgramWithFlagRunnableZZZ() throws ExceptionZZZ {
		super();		
	}
	
	public AbstractProgramWithFlagRunnableZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);		
	}
	
	public AbstractProgramWithFlagRunnableZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);		
	}
	
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
		
	//##########################################
	//### FLAG HANDLING
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
	
	
	
	//##########################
	
	
	
}
