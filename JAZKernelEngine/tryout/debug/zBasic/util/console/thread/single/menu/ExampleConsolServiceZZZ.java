package debug.zBasic.util.console.thread.single.menu;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class ExampleConsolServiceZZZ extends AbstractConsoleServiceZZZ {
	public ExampleConsolServiceZZZ() throws ExceptionZZZ {
		super();
	}
	public ExampleConsolServiceZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super(objConsole);
	}

	protected volatile int iCounter = 0;

	
	
	public int getCounter() {
		return this.iCounter;
	}
	
	public void setCounter(int iCounter) {
		this.iCounter = iCounter;
	}
	
	//Das ist kein eigener Thread mehr, dafür gibt es nun den ConsoleServiceThreadZZZ
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
	
	
	//Startit wird dann von einem anderen Thread aus aufgerufen.
	@Override
	public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Jetzt können Varablen aus dem KeyPressThread entgegengenommen werden.
			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			if(StringZZZ.isEmptyNull(sCallingMethod)) break main;
			
			switch(sCallingMethod){
				case "ascii":
					ascii_(hmVariable);
					break;
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
	
	private void ascii_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		KeyPressThreadUtilZZZ.printTableAscii();		
	}
	
	private void process1_(HashMapZZZ hmVariable) throws ExceptionZZZ {
		int iCounter = this.getCounter();
		iCounter++;
		System.out.println("Zähler: " + iCounter);
		this.setCounter(iCounter);
		
	}
}
