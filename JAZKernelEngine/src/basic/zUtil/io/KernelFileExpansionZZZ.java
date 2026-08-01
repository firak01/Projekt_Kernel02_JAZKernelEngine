package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import custom.zUtil.io.FileZZZ;

public class KernelFileExpansionZZZ extends FileExpansionZZZ{
	public KernelFileExpansionZZZ() throws ExceptionZZZ {
		super();
	}
	public KernelFileExpansionZZZ(char cFilling, int iExpansionLength) throws ExceptionZZZ {
		super(cFilling, iExpansionLength);
	}
	public KernelFileExpansionZZZ(FileZZZ objFileBase)  throws ExceptionZZZ{
		super(objFileBase);
	}
	public KernelFileExpansionZZZ(FileZZZ objFileBase, int iExpansionLength)  throws ExceptionZZZ{
		super(objFileBase, iExpansionLength);
	}
	
}
