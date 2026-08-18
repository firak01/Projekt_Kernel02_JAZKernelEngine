package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadDecryptZZZ;
import basic.zBasic.util.system.Syso;

public class DebugConsoleAsThreadZZZ implements Runnable{
	protected IConsoleZZZ objConsole = null;
	
	public DebugConsoleAsThreadZZZ() throws ExceptionZZZ{		
	}
	
	public DebugConsoleAsThreadZZZ(String[] args) throws ExceptionZZZ{		
	}
	
	public IConsoleZZZ getConsole() throws ExceptionZZZ {
		return this.objConsole;
	}
	public void setConsole(IConsoleZZZ objConsole) throws ExceptionZZZ {
		this.objConsole = objConsole;
	}
				
	public void startit() throws ExceptionZZZ {								
		IConsoleZZZ objConsole = ConsoleThreadZZZ.getInstance();	
		this.setConsole(objConsole);
		
		IKeyPressThreadZZZ objKeyPressThread = new KeyPressThreadDefaultZZZ(objConsole, 100);			
		objConsole.setKeyPressThread(objKeyPressThread);
					
		DummyConsoleUserStartableZZZ objConsoleUserStartable = new DummyConsoleUserStartableZZZ(objConsole);			
		objConsole.setConsoleUserStartableObject(objConsoleUserStartable);
		objConsole.start();		
	}

	@Override
	public void run() {
		try {
			start();
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		try {
		main:{
			this.startit();
			
			this.getConsole().isConsoleUserThreadRunning(true);
			//Merke: Diesen Teil nicht als Schleife ausführen... viel zu kompliziert... es gibt schon genug andere Threads
			//while(!this.isStopped()) {
//			if(this.isStopped()) break main;
//			if(this.isOutputAllFinished()) break main; //wenn Z.B. schon ein Menuepunkt ausgefuehrt worden ist. Z.B. eine einfache ASCII-Tabelle ausgegeben wurde.
//			if(!this.isInputAllFinished()) break main; 
			String sInput = null;
			
			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alles in dem KeyPressThread erledigen
			//Warten auf die fertige Eingabe.			
			//if(!this.getConsole().isKeyPressThreadFinished()) break main;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: WARTE AUF FERTIGE KONSOLENEINGABE ######");				
			do {
				 try {				 
					 Thread.sleep(200);
					 //System.out.println("CryptThread wartet auf fertige Konsoleneingabe");
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: Wait Error");
					e.printStackTrace();
				}
			}while(!this.getConsole().isStopped());
			//}while(!this.getConsole().isInputAllFinished());
				
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: WARTE AUF FERTIGE KONSOLENEINGABE ######");
//			
//			
//			//this.isOutputAllFinished(false);			
//			this.iCounter++;
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Zähler crypt: " + iCounter);
//
//			HashMapZZZ<String,Object>hmVariable=this.getConsole().getVariableHashMap();			
//			this.startit(hmVariable);
//			
//			if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread START: DUMMYWARTEN ALS TEST ######");
//			 try {				 
//				 Thread.sleep(4500);
//			} catch (InterruptedException e) {
//				System.out.println("KeyPressThread: Wait Error");
//				e.printStackTrace();
//			}
//			 if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("####### CryptThread ENDE: DUMMYWARTEN ALS TEST ######");			 
//			 this.isOutputAllFinished(true);			
			//}//end while !isStopped
		}//end main:
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
		}
		this.getConsole().isConsoleUserThreadFinished(true);
		return bReturn;
	}
}
