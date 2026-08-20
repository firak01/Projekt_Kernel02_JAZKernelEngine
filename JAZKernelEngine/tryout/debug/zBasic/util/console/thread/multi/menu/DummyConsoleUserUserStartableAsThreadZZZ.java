package debug.zBasic.util.console.thread.multi.menu;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractConsoleUserStartableZZZ;
import basic.zBasic.util.console.thread.IConsoleUserZZZ;
import basic.zBasic.util.console.thread.IConsoleZZZ;
import basic.zBasic.util.crypt.thread.ConsoleUserEncryptZZZ;

	public class DummyConsoleUserUserStartableAsThreadZZZ extends AbstractConsoleUserStartableZZZ implements Runnable {
	//public class ConsoleUserThreadZZZ extends AbstractKeyPressThreadCommonZZZ{
		//TODOGOON20260814; Irgendetwas sinnvollens
        //Method that gets called when the object is instantiated
        public DummyConsoleUserUserStartableAsThreadZZZ(IConsoleZZZ objConsole)  throws ExceptionZZZ {
        	super(objConsole);        	
        }
     
		public void run() 
        {
        	try {        		
				this.start();
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
				this.requestStop();
			}
        }
        
//		@Override
//		public boolean isInputAllFinished() {
//			return this.getConsole().isInputAllFinished();
//		}
//
//		@Override
//		public void isInputAllFinished(boolean bInputFinished) {
//			this.getConsole().isInputAllFinished(bInputFinished);
//		}
//
//		@Override
//		public boolean isOutputAllFinished() {
//			return this.getConsole().isOutputAllFinished();
//		}
//
//		@Override
//		public void isOutputAllFinished(boolean bOutputFinished) {
//			this.getConsole().isOutputAllFinished(bOutputFinished);
//		}	
		
        public boolean isStopped() {
    		return this.getConsole().isStopped();
    	}
    	public void isStopped(boolean bStop) {
    		this.getConsole().isStopped(bStop);
    	}
    	public void requestStop() {
    		this.isStopped(true);
    	}
		

		public boolean start() throws ExceptionZZZ {
			boolean bReturn=false;
			main:{		
				try {    				
		           while(!this.isStopped()) {
		                long lSleepTime = this.getConsole().getSleepTime();
		                //System.out.println("ConsoleThread.sleep: " + lSleepTime);
		                Thread.sleep(lSleepTime);			                		               
		                if(this.getConsole().isInputAllFinished()){
			                IConsoleUserZZZ objUser = this.getConsole().getConsoleUserStartableObject();
			                if(objUser!=null) {
			                	boolean bStop = this.getConsole().isStopped(); 
				                if(bStop) {
				                	//this.getConsole().getConsoleUserObject().requestStop();
				                	//this.getConsole().getConsoleThread().requestStop();
				                	this.requestStop();
				                }else {				                			                		
			                		if(!this.getConsole().isConsoleUserThreadRunning()) { //den Thread nicht mehrmals starten
					                		//boolean bResult = this.getConsole().getConsoleUserObject().start();
			                				//boolean bResult = this.getConsole().getConsoleThread().start();
					                		this.getConsole().isInputAllFinished(false); //Bereit für neue Eingabe...					                				                			
					                		this.getConsole().isOutputAllFinished(false); //Bereit für neue Ausgabe...
			                		}//end if isConsoleUserThreadRunning()
				                }			                				               
			                }else {
			                	this.requestStop();
			                }
		                }
	                }//end while	               
		            bReturn = true;
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
			}//end main
			return bReturn;
		}

//		@Override
//		public IKeyPressThreadZZZ getKeyPressThreadUsed() throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public void setKeyPressThreadUsed(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public String getMethodForThreadUsed() throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public void setMethodForThreadUsed(String sMethodName) throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public long getSleepTime() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void setSleepTime(long lSleepTime) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public Scanner getInputReader() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public void setInputReader(Scanner objScanner) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean isKeyPressThreadFinished() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public void isKeyPressThreadFinished(boolean bFinished) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean isCurrentInputFinished() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public void isCurrentInputFinished(boolean bCurrentInput) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean isCurrentInputValid() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public void isCurrentInputValid(boolean bCurrentInput) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean isCurrentMenue() {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public void isCurrentMenue(boolean bMakeMenue) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void makeMenueMain() throws InterruptedException, ExceptionZZZ {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			return false;
//		}
//
//		@Override
//		public boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ {
//			// TODO Auto-generated method stub
//			return false;
//		}

		@Override
		public boolean startit(HashMapZZZ hmVariable) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}			
    }

