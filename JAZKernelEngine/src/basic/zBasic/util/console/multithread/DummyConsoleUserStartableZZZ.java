package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public class DummyConsoleUserStartableZZZ extends AbstractConsoleUserStartableZZZ {
	public DummyConsoleUserStartableZZZ() throws ExceptionZZZ {
		super();
	}
	public DummyConsoleUserStartableZZZ(IConsoleZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	protected volatile int iCounter = 0;

	
	
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
			//Jetzt können Varablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			switch(sCallingMethod){
				case "process1":
					process1_(hmVariable);
					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
					throw ez;
			}
			
			
			
			bReturn = true;
		}//end main:
		return bReturn;
	}	
	
	
	private void process1_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		int iCounter = this.getCounter();
		iCounter++;
		System.out.println("Zähler: " + iCounter);
		this.setCounter(iCounter);
		
	}
}
