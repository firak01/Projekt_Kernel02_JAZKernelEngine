package debug.zBasic.util.console.thread.multi.menuless;

import java.util.Scanner;


	 
	public class ConsoleThreadZZZ implements Runnable,IConsoleZZZ {
		private IConsoleEnabledZZZ objConsoleUser = null;
		private KeyPressThreadZZZ objKeyPressThread = null;
		private long lSleepTime = 1000;
		private boolean bStop = false;
		
		 public long getSleepTime() {		
	     	return this.lSleepTime;
	     }
		 public void setSleepTime(long lSleepTime) {
			 if(lSleepTime<0){
				 lSleepTime=0;
			 }
			 this.lSleepTime = lSleepTime;
		 }
		
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public ConsoleThreadZZZ(long lSleepTime, KeyPressThreadZZZ objKeyPressThread) {
        	this.setSleepTime(lSleepTime);
        	this.setKeyPressThread(objKeyPressThread);
        }
     
		public void run() 
        {
        	try {    
	           while(!this.isStopped()) {
	                long lSleepTime = this.getKeyPressThread().getSleepTime();
	                this.setSleepTime(lSleepTime);
	                lSleepTime = this.getSleepTime();
	                System.out.println("ConsoleThread.sleep: " + lSleepTime);
	                Thread.sleep(lSleepTime);
	                
	                IConsoleEnabledZZZ objUser = this.getConsoleUserObject();
	                if(objUser!=null) {
	                	boolean bStop = this.getKeyPressThread().isStopped(); 
		                if(bStop) {
		                	objUser.requestStop();
		                	this.requestStop();
		                }else {
		                	 objUser.executeOnConsole();                     
		                }
	                }else {
	                	this.requestStop();
	                }
                }
               
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public boolean isStopped() {
    		return this.bStop;
    	}
    	public void isStopped(boolean bStop) {
    		this.bStop = bStop;
    	}
    	public void requestStop() {
    		this.isStopped(true);
    	}
		@Override
		public IConsoleEnabledZZZ getConsoleUserObject() {
			return this.objConsoleUser;
		}
		@Override
		public void setConsoleUserObject(IConsoleEnabledZZZ objConsoleUser) {
			this.objConsoleUser = objConsoleUser;
		}
		@Override
		public KeyPressThreadZZZ getKeyPressThread() {
			return this.objKeyPressThread;
		}		
		 private void setKeyPressThread(KeyPressThreadZZZ objKeyPressThread) {
				this.objKeyPressThread = objKeyPressThread;
			}
		@Override
		public ConsoleThreadZZZ getConsoleThread() {
			return this;
		}				
    }

