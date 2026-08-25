package basic.zBasic;

import java.util.HashMap;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.EventObjectStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ extends AbstractDummyTestProgramWithStatusLocalByInterfaceExtendedZZZ{
	private static final long serialVersionUID = -4468952293339389490L;

	public DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	public DummyTestProgramWithStatusLocalByInterfaceExtendedZZZ() throws ExceptionZZZ {
		super();
	}
	
	//+++++++++++++++++++++++++++
	//+++ CUSTOM STATUS LISTENING
	//+++++++++++++++++++++++++++
	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		return true; //Es gibt hier keinen Grund das Werfen des Status einzuschränken.
	}

	@Override
	public boolean startCustom() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

		
}
