package basic.zBasic;

import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;

public class DummyTestObjectWithFlagOnFlagListeningZZZ extends AbstractObjectWithFlagOnFlagListeningZZZ<Object> implements IDummyTestObjectWithFlagOnFlagListeningZZZ{
	private static final long serialVersionUID = 2089503406657203691L;

	private String sValueDummy=null;//Merke: Wir beim Test in einem Event gesetzt.
	
	public DummyTestObjectWithFlagOnFlagListeningZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestObjectWithFlagOnFlagListeningZZZ() {
		super();
	}
	
	//########################
	//### GETTER / SETTER
	//########################
	@Override
	public String getValueDummyByFlagEvent() {
		return this.sValueDummy;
	}

	@Override
	public void setValueDummyByFlagEvent(String sValue) {			
		if(this.getValueDummyByFlagEvent()==null) {
			this.sValueDummy = sValue;
		}else {
			if(sValue==null) {
				//Dann resetten
				this.sValueDummy = sValue;
			}else {
				//Damit kann man beim Test das wiederholte setzen feststellen
				this.sValueDummy = this.getValueDummyByFlagEvent() + "|" + sValue;
			}
		}
	}




	//#########################
	//### IListenerObjectFlagZsetZZZ
	@Override
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ {
		if(eventFlagZset==null) {
			ExceptionZZZ ez = new ExceptionZZZ( "EventObjectFlagZset", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			throw ez;	
		}
		
		String sFlagText = eventFlagZset.getFlagText();
		this.setValueDummyByFlagEvent(sFlagText);
	
		return true;
		
	}
	
	//###################################################
	//### FLAGS: IDummytestObjectWithFlag
	//###################################################
		
	@Override
	public boolean getFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IDummyTestObjectWithFlagZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IDummyTestObjectWithFlagZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	//###################################################
	//### FLAGS: IDummyTestObjectWithFlagOnFlagListeningZZZ
	//###################################################
		
	@Override
	public boolean getFlag(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IDummyTestObjectWithFlagOnFlagListeningZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
