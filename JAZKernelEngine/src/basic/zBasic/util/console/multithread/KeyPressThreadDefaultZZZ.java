package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;


	 
	public class KeyPressThreadDefaultZZZ extends AbstractKeyPressThreadCommonZZZ {


        //Method that gets called when the object is instantiated
        public KeyPressThreadDefaultZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
       
 		@Override
		public boolean start() throws ExceptionZZZ {
			boolean bReturn = false;
        	main:{
    			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
    			//       Darum muss man alle Eingaben in dem KeyPressThread erledigen
	        	System.out.println("Eingaben: [ ] oder Q");
	            while(!this.isStopped()){
	            	long lSleepTime = this.getSleepTime();
	            	long lSleepTimeConsole = this.getConsole().getSleepTime();
		        	 try {
		             	System.out.println("Kein warten auf Eingabe. Die ist während des laufenden Threads möglich ...");                 	
						Thread.sleep(lSleepTime);                 	
					} catch (InterruptedException e) {
						System.out.println("KeyPressThread: 1. Wait Error");
						e.printStackTrace();
					}
	
		        	Scanner inputReader = this.getInputReader();
	                String input = inputReader.next();
	                System.out.println("Pressed " + input);
	                if (input.equals("[")) {
	                	lSleepTimeConsole+=100;
	                	this.getConsole().setSleepTime(lSleepTimeConsole);
	                }
	                if (input.equals("]")) {
	                	lSleepTimeConsole-=100;
	                	this.getConsole().setSleepTime(lSleepTimeConsole);
	                }
	                if (input.equalsIgnoreCase("Q")) {
	                    this.requestStop();
	                	break; // stop KeyPressThread durch Setzen einer internen Variablen
	                }
	                
	                System.out.println("Nach der Eingabe.");	               					
	            }//end while
	            bReturn = true;
	    	}//end main:
	    	return bReturn;
		}

		@Override
		public void makeMenueMain() throws InterruptedException, ExceptionZZZ {
			//TODOGOON20260817://Erzeuge irgendwelche Dummy Einträge 
			System.out.println("MAKE MENUE MAIN");
		}

		@Override
		public boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		
    }

