package debug.zBasic.util.console.thread.multi.menuless01;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;

public class ExampleConsoleServiceZZZ extends AbstractConsoleServiceZZZ implements IConsoleServiceZZZ {
	private static final long serialVersionUID = -8542443837227617372L;

	public ExampleConsoleServiceZZZ() throws ExceptionZZZ {
		super();		
	}

	private int iCounter = 0;
	
	
	
	public int getcounter() {
		return this.iCounter;
	}


	@Override
	public IConsoleControllerZZZ getConsoleController() {
		//Das wird erst in der Erweiterung menuless02 gebraucht
		return null;
	}

	@Override
	public void setConsoleController(IConsoleControllerZZZ objConsole) {
		//Das wird erst in der Erweiterung menuless02 gebraucht
	}

	@Override
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			try {					
				this.iCounter++;
				System.out.println("Zähler: " + iCounter);			 
				Thread.sleep(900);
			} catch (InterruptedException ie) {
				System.out.println("KeyPressThread: Wait Error");
				ie.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ(ie);
				throw ez;
			}	
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
