package debug.zBasic.util.console.thread.nomenu;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.console.multithread.extended.IConsoleEnabledZZZ;

public class DummyConsoleUserZZZ extends AbstractObjectWithFlagZZZ implements IConsoleEnabledZZZ {
	private int iCounter = 0;
	private boolean bStop = false;
	
	@Override
	public boolean executeOnConsole() throws InterruptedException {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
			
			this.iCounter++;
			System.out.println("Zähler: " + iCounter);
			 try {				 
				 Thread.sleep(900);
			} catch (InterruptedException e) {
				System.out.println("KeyPressThread: Wait Error");
				e.printStackTrace();
			}
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public int getcounter() {
		return this.iCounter;
	}

	@Override
	public void requestStop() {
		this.isStopped(true);
	}
	
	public boolean isStopped() {
		return this.bStop;
	}
	public void isStopped(boolean bStop) {
		this.bStop = bStop;
	}

		

}
