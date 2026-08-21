package debug.zBasic.util.console.thread.multi.menuless;

import java.util.Scanner;


	 
	public class ExampleKeyPressThreadZZZ implements Runnable {
		private long lSleepTime;
		private boolean bStop = false;
		
		 public long getSleepTime() {
	     	return this.lSleepTime;
	     }
		 public void setSleepTime(long lSleepTime) {
			 this.lSleepTime = lSleepTime;
		 }
		
        Scanner inputReader = new Scanner(System.in);

        //Method that gets called when the object is instantiated
        public ExampleKeyPressThreadZZZ(long lSleepTime) {
        	setSleepTime(lSleepTime);
        }
       

        public void run() 
        {
        	System.out.println("Eingaben: [ ] oder Q");
            while(true){
        	 try {
             	System.out.println("Warte auf Eingabe...");                 	
				Thread.sleep(500);                 	
			} catch (InterruptedException e) {
				System.out.println("KeyPressThread: 1. Wait Error");
				e.printStackTrace();
			}

                String input = inputReader.next();
                System.out.println(input);
                if (input.equals("[")) {
                	lSleepTime+=100;
                    System.out.println("Pressed [");
                }
                if (input.equals("]")) {
                	lSleepTime-=100;
                   System.out.println("Pressed ]");
                }
                if (input.equalsIgnoreCase("Q")) {
                    this.requestStop();
                	break; // stop KeyPressThread
                }

                try {
                	System.out.println("Nach der Eingabe.");
                	Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: 2. Wait Error");
					e.printStackTrace();
				}
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

    }

