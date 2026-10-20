package debug.zBasic.util.console.thread.multi.menuless02;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;

public class ExampleConsoleServiceZZZ extends AbstractConsoleServiceZZZ implements IExampleConsoleServiceZZZ {
	private static final long serialVersionUID = -8542443837227617372L;

	protected volatile int iCounter = 0;
	
	public ExampleConsoleServiceZZZ() throws ExceptionZZZ {
		super();		
	}
	public ExampleConsoleServiceZZZ(IConsoleControllerZZZ objConsoleController) throws ExceptionZZZ {
		super(objConsoleController);
	}

	//### METHODEN
	@Override
	public int getCounter() throws ExceptionZZZ{
		return this.iCounter;
	}
	
	@Override
	public void setCounter(int iCounter) throws ExceptionZZZ {
		this.iCounter = iCounter;
	}
	
	
	//Das ist kein eigener Thread mehr
	//Startit wird dann von einem anderen Thread aus aufgerufen.		
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
	public boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ{
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
