package debug.zBasic.util.console.thread.multi.menuless;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleUserStartableZZZ;
import basic.zBasic.util.console.thread.IConsoleUserStartableZZZ;
import basic.zBasic.util.console.thread.IConsoleZZZ;

public class ExampleConsoleUserStartableZZZ extends AbstractConsoleUserStartableZZZ implements IConsoleUserStartableZZZ {
	
	public ExampleConsoleUserStartableZZZ() throws ExceptionZZZ {
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
	public IConsoleZZZ getConsole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConsole(IConsoleZZZ objConsole) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}

		

}
