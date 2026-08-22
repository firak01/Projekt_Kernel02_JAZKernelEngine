package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;

public class ExampleConsoleServiceZZZ extends AbstractConsoleServiceZZZ implements IConsoleServiceZZZ {
	private static final long serialVersionUID = -8542443837227617372L;

	public ExampleConsoleServiceZZZ() throws ExceptionZZZ {
		super();		
	}

	private int iCounter = 0;
	
	@Override
	public boolean startit() throws ExceptionZZZ{		
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
	
	public int getcounter() {
		return this.iCounter;
	}


	@Override
	public IConsoleControllerZZZ getConsoleController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConsoleController(IConsoleControllerZZZ objConsole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

		

}
