package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public class DummyConsoleUserStartableZZZ extends AbstractConsoleUserStartableZZZ {
	public DummyConsoleUserStartableZZZ() throws ExceptionZZZ {
		super();
	}
	public DummyConsoleUserStartableZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	private int iCounter = 0;

	
	
	public int getCounter() {
		return this.iCounter;
	}
	
	public void setCounter(int iCounter) {
		this.iCounter = iCounter;
	}
	
	//Das ist kein eigener Thread mehr
//	@Override
//	public boolean start() throws ExceptionZZZ {
//		boolean bReturn = false;
//		main:{
//			if(this.isStopped()) break main;
//			
//			this.iCounter++;
//			System.out.println("Zähler: " + iCounter);
//			 try {				 
//				Thread.sleep(100);
//				bReturn = true;
//			} catch (InterruptedException e) {
//				System.out.println("KeyPressThread: Wait Error");
//				e.printStackTrace();
//			}
//		}//end main:
//		return bReturn;	
//	}
	
	
	//Start it wird dann von einem anderen Thread aus aufgerufen.
	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			int iCounter = this.getcounter();
			iCounter++;
			System.out.println("Zähler: " + iCounter);
			this.setCounter(iCounter);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}	
}
