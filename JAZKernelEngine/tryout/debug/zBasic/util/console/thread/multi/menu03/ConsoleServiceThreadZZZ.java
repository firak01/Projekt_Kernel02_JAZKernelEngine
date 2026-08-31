package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

public class ConsoleServiceThreadZZZ<T> extends AbstractConsoleServiceThreadZZZ<T> {
	private static final long serialVersionUID = 3708101486270524692L;


	//### Konstruktor
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public ConsoleServiceThreadZZZ() throws ExceptionZZZ {
		super();
		AbstractThreadNew_();
	}
	
	public ConsoleServiceThreadZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractThreadNew_();
	}
	
	public ConsoleServiceThreadZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);
		AbstractThreadNew_();
	}
	
		
	private boolean AbstractThreadNew_() throws ExceptionZZZ {
			
		return true;
	}
	
	
	
}
