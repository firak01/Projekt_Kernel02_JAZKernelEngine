package basic.zBasic.util.console.multithread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;
import basic.zKernel.flag.IFlagZEnabledZZZ;


	 
	/** Der KeypressThread bestimmt die Eingabemöglichkeiten
	 *  und was damit getan werden soll.
	 *  Darum gibt es zu Demonstrationszwecken den KeyPressThreadDefaultZZZ
	 *  
	 * 
	 * @author Fritz Lindhauer, 18.10.2022, 09:15:40
	 * 
	 */
	public abstract class AbstractKeyPressThreadZZZ implements Runnable,IConstantZZZ, IConsoleUserZZZ, IKeyPressThreadZZZ {
		private static Scanner inputReader = new Scanner(System.in);
		protected volatile static IConsoleZZZ objConsole = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
		
		private long lSleepTime=1000;//default
		
		protected boolean bCurrentInputValid=false;
		protected boolean bCurrentInputFinished=false;
		//protected boolean bCurrentOutputFinished=false;
		protected boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
		
		protected IKeyPressThreadZZZ objKeyPressThreadUsed = null; //Damit kann man auch andere Thread-Klassen nutzen
		
		//### GETTER / SETTER
		@Override
		public IKeyPressThreadZZZ getKeyPressThreadUsed() throws ExceptionZZZ {
			if(this.objKeyPressThreadUsed==null) {
				return this;
			}else {
				return this.objKeyPressThreadUsed;
			}				
		}

		@Override
		public void setKeyPressThreadUsed(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
			this.objKeyPressThreadUsed = objKeyPressThread;					
		}
		
		@Override 
		public String getMethodForThreadUsed() throws ExceptionZZZ{
			HashMapZZZ hm = this.getConsole().getVariableHashMap();
			return (String) hm.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
		}
		
		@Override 
		public void setMethodForThreadUsed(String sMethod) throws ExceptionZZZ{
			HashMapZZZ hm = this.getConsole().getVariableHashMap();
			hm.put(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED, sMethod);
		}
		
		//### Methoden
		@Override
		public boolean isCurrentInputFinished() {
        	return this.bCurrentInputFinished;
        }
		@Override
        public void isCurrentInputFinished(boolean bCurrentInput) {
        	this.bCurrentInputFinished = bCurrentInput;
        }    
        @Override
        public boolean isCurrentInputValid() {
        	return this.bCurrentInputValid;
        }
        @Override
        public void isCurrentInputValid(boolean bCurrentInput) {
        	this.bCurrentInputValid = bCurrentInput;
        }
        @Override
        public boolean isCurrentMenue() {
        	return this.bMakeMenue;
        }
        @Override
        public void isCurrentMenue(boolean bMakeMenue) {
        	this.bMakeMenue = bMakeMenue;
        }
        @Override
        public synchronized boolean isInputAllFinished() {
        	return this.getConsole().isInputAllFinished();
        }        
        @Override
        public synchronized void isInputAllFinished(boolean bInputAllFinished) {
        	this.getConsole().isInputAllFinished(bInputAllFinished);
        }
        
        @Override
        public synchronized boolean isOutputAllFinished() {
        	return this.getConsole().isOutputAllFinished();
        } 
        
        @Override
        public synchronized void isOutputAllFinished(boolean bOutputAllFinished) {
        	this.getConsole().isOutputAllFinished(bOutputAllFinished);
        }
		
		@Override
		 public long getSleepTime() {
	     	return this.lSleepTime;
	     }
		
		 @Override
		 public void setSleepTime(long lSleepTime) {
			 this.lSleepTime = lSleepTime;
		 }
		 
		 @Override
			public boolean isKeyPressThreadFinished() {
				return this.getConsole().isKeyPressThreadFinished();
			}
			@Override
			public void isKeyPressThreadFinished(boolean bFinished) {
				this.getConsole().isKeyPressThreadFinished(bFinished);
			}
		
        //Method that gets called when the object is instantiated
        public AbstractKeyPressThreadZZZ(IConsoleZZZ objConsole) {
        	this.setConsole(objConsole);
        }
        public AbstractKeyPressThreadZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	this.setConsole(objConsole);
        	this.setSleepTime(lSleepTime);
        }
        
        public void cancelToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//wieder so als würde das Menü nicht übersprungen.
			this.cancelToMenue();
		}
		public void cancelToMenue() {			
			System.out.println("Abbruch. Zurueck zum Menue");
			//this.isCurrentInputValid(false);					
    		this.isCurrentMenue(true); //wieder zurück zum Menue
    		this.isCurrentInputFinished(true);
		}
		
        public void validToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//so, damit die Eingabe der Menue-Argumente übersprungen.
			this.validToMenue();
		}
		public void validToMenue() {			
			System.out.println("Zurueck zum Menue");
			this.isCurrentInputValid(true);					
    		this.isCurrentMenue(true);
    		this.isCurrentInputFinished(true);
		}
		
		public void validSkipMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyYes)); //so, damit die Eingabe der Menue-Argumente uebersprungen wird 
			this.validSkipMenue();
		}
		public void validSkipMenue() {			
			System.out.println("Menue ueberspringen");
			this.isCurrentInputValid(true);						                			
	    	this.isCurrentInputFinished(true);
			this.isCurrentMenue(false);	
		}
		
		
		public void quit() {
			System.out.println("Beenden");		                					                    
            this.isCurrentInputValid(false);
            this.isCurrentInputFinished(true);
            this.isKeyPressThreadFinished(true);
            this.requestStop(); //stop KeyPressThread über die gesetzte STOP Variable
		}
       
        @Override
		public synchronized IConsoleZZZ getConsole() {
			return this.objConsole;
		}
		@Override
		public synchronized void setConsole(IConsoleZZZ objConsole) {
			this.objConsole = objConsole;
		}
		
		@Override
		public Scanner getInputReader() {
			return this.inputReader;
		}
		
		@Override
		public void setInputReader(Scanner inputReader) {
			this.inputReader = inputReader;
		}
		
		
		@Override
        public void run() 
        {
        	try {        		
				this.start();
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
        }
        
        @Override
        public boolean isStopped() {
    		return this.getConsole().isStopped();
    	}
        
        @Override
    	public void isStopped(boolean bStop) {
    		this.getConsole().isStopped(bStop);
    	}
        
        @Override
    	public void requestStop() {
    		this.isStopped(true);
    	}

        /** Abstrakte Methode, die so angelegt ist, das sie von anderen Consolen genutzt werden kann.
         *  Bisherige Implementierungen:
         *  Z.B. mit Verschlüsselungsklassen
         */
    	@Override
		public boolean start() throws ExceptionZZZ {
			boolean bReturn = true;
        	main:{
    			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
    			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen				
				this.getConsole().isKeyPressThreadRunning(true);
				
				HashMapZZZ hmVariable = this.getConsole().getVariableHashMap();								
	            while(!this.isStopped()){
	            	
	            	long lSleepTime = this.getSleepTime();
	            	//synchronized(this) {
	            	input:{	            		
	            		String sInput = null; boolean bSkipArguments=false;
		            		            		            			            	
		            	//while(!this.getConsole().isKeyPressThreadFinished()) {
		            	if(!this.isInputAllFinished()) {
			        	    if(hmVariable!=null) {
			        	    	Object obj = hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS);
			        	    	if(obj==null) {
			        	    		bSkipArguments = false;
			        	    	}else if (obj instanceof Boolean) {
			        	    		bSkipArguments=((Boolean) obj).booleanValue();
			        	    	}else if(obj instanceof String) {
			        	    		bSkipArguments = BooleanZZZ.stringToBoolean(obj.toString());				        	        
			        	    	}
			        	    }
				        	   
			        	    //########################################################
			        	    //#### Eingabe der Argumente
			        	    this.isCurrentInputFinished(false);
				        	if(bSkipArguments) {
				        		System.out.println("KeyPressThread: bSkipArguments=true");
				        	}else {				        		
				        		do {
				        			this.isCurrentInputFinished(false);
				        			this.isCurrentInputValid(false);				        							        		
						        	 try {
						        		if(this.isCurrentMenue()) {				        			
							        		this.makeMenueMain();  									
						        		}
									} catch (InterruptedException e) {
										System.out.println("KeyPressThread: 1. Wait Error");
										e.printStackTrace();
									}
			
					                //das holt wohl wort fuer wort von der Konsole: String sInput = inputReader.next();
						        	Scanner inputReader = this.getInputReader();				      
						        	sInput = inputReader.nextLine();
					                System.out.println("Pressed Menueselection:" + sInput);
					                if(sInput==null) break main;
					                
					                boolean bGoon = this.processMenueMainArgumentInput(sInput,hmVariable);
					                if(!bGoon) break main;//Quit
					                
				        		}while(!this.isCurrentInputValid());	                
				        	}//end if bSkipArguments	
				        					        	
		        			this.isInputAllFinished(false);
				        	this.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
				        	
				        	//######################################################################
		                	//### Frage nach Mehrfacheingabe
				        	 if(!(this.isCurrentInputFinished() && this.isInputAllFinished())) {
		                		sInput = KeyPressUtilZZZ.makeQuestionYesNoQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue oder mit den akuellen Menueangaben im gleichen Menüpunkt weiterarbeiten?");		                		                			                			    	                			                				               
		                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
		                			this.quit();
			                	}else {		               		                		
			                		boolean bMenue = BooleanZZZ.stringToBoolean(sInput);
			                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü
			                			this.validToMenue();
			                		}else {
			                			this.validSkipMenue();			                			
			                		}		                		
			                	}
				        	}
				        	
				        	
				        	//FALLS im Menü eine ANDERE THREAD KLASSE gewählt worden ist, oder this falls nicht...
				        	IKeyPressThreadZZZ objKeyPressThreadUsed = this.getKeyPressThreadUsed();
				        	objKeyPressThreadUsed.isInputAllFinished(false);
				        	objKeyPressThreadUsed.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
				        					        	//				        	
				        	 if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
					        		boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
					        		if(!bGoon) break main; //Quit
				        	 }
				        	 
				        	 
				        	IConsoleUserStartableZZZ objConsoleUserStarter = this.getConsole().getConsoleUserStartableObject();
				        	objConsoleUserStarter.startit(hmVariable); //direkter, ohne Thread...
				        	 
				        	 
				        	//#########################################################################
			                try {
			                	//Aber hier keine Flags vorhanden if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Warte auf neue Eingabe.");
			                	Syso.println("\nWarte auf neue Eingabe.");
			                	Thread.sleep(lSleepTime);			                	
							} catch (InterruptedException e) {
								System.out.println("KeyPressThread: 2. Wait Error");
								e.printStackTrace();
							}
			                //
			                objKeyPressThreadUsed.isInputAllFinished(true);
			               	this.isInputAllFinished(false); //Auf zur nächsten Eingabe
			               
	            		}//end if inputAllFinished
	            	}//end input:
	            	//}//End synchro
	      
	            	//DA DER CONSOLEUSER JETZT KEIN THREAD IST, HIER NICHT MEHR DARAUF WARTEN
	            	/*
	            	while(!this.getConsole().isConsoleUserThreadFinished() && !this.getConsole().isStopped()) {
			        	 try {
			             	//System.out.println("Warte auf Ergebnis des Cryptlaufs...");  			        		
							Thread.sleep(lSleepTime);  		
							//this.isInputAllFinished(false);//Bereit für neue Eingaben, hier und nicht nach der Schleife!!!
						} catch (InterruptedException e) {
							System.out.println("KeyPressThread: 2. Wait Error");
							e.printStackTrace();
						}
	            	}//end while		            	
	            	*/	
	            	
	            }//end while isStopped
	    	}//end main:
			this.getConsole().isKeyPressThreadFinished(true);
	    	return bReturn;
		}    
    	    	
    	@Override
		public abstract void makeMenueMain() throws InterruptedException, ExceptionZZZ;
    	
    	@Override
    	public abstract boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ;
    	
    	@Override
		public abstract boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ;
    	
    	@Override
    	public abstract boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ;
    }

