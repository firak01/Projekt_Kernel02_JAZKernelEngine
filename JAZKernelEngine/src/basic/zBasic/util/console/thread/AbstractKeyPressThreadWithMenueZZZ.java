package basic.zBasic.util.console.thread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;


	 
	/** Der KeypressThread bestimmt die Eingabemöglichkeiten
	 *  und was damit getan werden soll.
	 *  Darum gibt es zu Demonstrationszwecken den KeyPressThreadDefaultZZZ
	 *  
	 * 
	 * @author Fritz Lindhauer, 18.10.2022, 09:15:40
	 * 
	 */
	public abstract class AbstractKeyPressThreadWithMenueZZZ implements Runnable,IConstantZZZ, IConsoleControllerUserZZZ, IKeyPressThreadUserZZZ, IKeyPressThreadMenueableZZZ {
		
		private static Scanner inputReader = new Scanner(System.in);
		protected volatile static IConsoleControllerZZZ objConsole = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
		
		public static long lSLEEPTIME_DEFAULT = 1000;		
		private long lSleepTime=-1;
		
		protected boolean bCurrentInputValid=false;
		protected boolean bCurrentInputFinished=false;
		//protected boolean bCurrentOutputFinished=false;
		protected boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
		
		protected IKeyPressThreadZZZ objKeyPressThreadUsed = null; //Damit kann man auch andere Thread-Klassen nutzen
		
		//### Konstruktor
		public AbstractKeyPressThreadWithMenueZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
	    	super();
	    	AbstractKeyPressThreadWithMenueNew_(objConsole, -1);
	    }
	    public AbstractKeyPressThreadWithMenueZZZ(IConsoleControllerZZZ objConsole, long lSleepTime) throws ExceptionZZZ {
	    	super();
	    	AbstractKeyPressThreadWithMenueNew_(objConsole, -1);
	    }
	    
	    private boolean AbstractKeyPressThreadWithMenueNew_(IConsoleControllerZZZ objConsole, long lSleepTime) throws ExceptionZZZ {
	    	boolean bReturn = false;
	    	main:{
	    		this.setConsoleController(objConsole);
	    		this.setSleepTime(lSleepTime);
	    	}//end main:
	    	return bReturn;
	    }
		
		
		//### GETTER / SETTER		
		@Override
		public IKeyPressThreadZZZ getKeyPressThread() {
			if(this.objKeyPressThreadUsed==null) {
				return this;
			}else {
				return this.objKeyPressThreadUsed;
			}	
		}

		@Override
		public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) {
			this.objKeyPressThreadUsed = objKeyPressThread;	
		}
		
		@Override 
		public String getMethodForConsoleService() throws ExceptionZZZ{
			HashMapZZZ hm = this.getConsoleController().getVariableHashMap();
			return (String) hm.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
		}
		
		@Override 
		public void setMethodForConsoleService(String sMethod) throws ExceptionZZZ{
			HashMapZZZ hm = this.getConsoleController().getVariableHashMap();
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
        	return this.getConsoleController().isInputAllFinished();
        }        
        @Override
        public synchronized void isInputAllFinished(boolean bInputAllFinished) {
        	this.getConsoleController().isInputAllFinished(bInputAllFinished);
        }
        
        @Override
        public synchronized boolean isOutputAllFinished() {
        	return this.getConsoleController().isOutputAllFinished();
        } 
        
        @Override
        public synchronized void isOutputAllFinished(boolean bOutputAllFinished) {
        	this.getConsoleController().isOutputAllFinished(bOutputAllFinished);
        }
		
        
        //### aus IThreadEnabledZZZ
		@Override
		 public long getSleepTime() throws ExceptionZZZ {
			if(lSleepTime< 0) {
				return this.lSLEEPTIME_DEFAULT;
			}else {
				return this.lSleepTime;
			}
	     }
		
		 @Override
		 public void setSleepTime(long lSleepTime) throws ExceptionZZZ {
			 this.lSleepTime = lSleepTime;
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
	    		return this.getConsoleController().isStopped();
	    	}
	        
	        @Override
	    	public void isStopped(boolean bStop) {
	    		this.getConsoleController().isStopped(bStop);
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
					this.getConsoleController().isKeyPressThreadRunning(true);
					
					HashMapZZZ hmVariable = this.getConsoleController().getVariableHashMap();								
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
							        	
							        	if(this.isCurrentMenue()) {				        			
								        	this.makeMenueMain();  									
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
					        		Syso.printSeparator();
			                		sInput = KeyPressUtilZZZ.makeQuestionYesNoMenueQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue oder mit den akuellen Menueangaben im gleichen Menüpunkt weiterarbeiten?");		                		                			                			    	                			                				               
			                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
			                			this.quit();
				                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMenue)) {			                				                				                    
				                    	this.validToMenue(hmVariable);//Zurueck zum Menü	
				                    	//Aber sofort und nicht erst noch eine Eingabe abwarten			                    	
				                	} else {		               		                					                				                		
				                		boolean bYes = BooleanZZZ.stringToBoolean(sInput);
				                		boolean bDefault = sInput.length()==0; //Die Scanner Klasse liefert bei ENTER einen Leerstring
				                		boolean bMenue = bYes && !bDefault;
				                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü
				                			this.validToMenue(hmVariable);//Zurueck zum Menü	
				                		}else {			                		
				                			this.validSkipMenue(hmVariable);			                			
				                		}				                		
				                		//Jetzt erst noch eine Eingabe machen....
				                		
				                		//FALLS im Menü eine ANDERE THREAD KLASSE gewählt worden ist, oder this falls nicht...
							        	IKeyPressThreadMenueableZZZ objKeyPressThreadUsed = (IKeyPressThreadMenueableZZZ) this.getKeyPressThread();
							        	objKeyPressThreadUsed.isInputAllFinished(false);
							        	objKeyPressThreadUsed.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
							        					        	//				        	
							        	 if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
								        		boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
								        		if(!bGoon) break main; //Quit
							        	 }
							        	 
							        	 
							        	IConsoleServiceZZZ objConsoleService = this.getConsoleController().getConsoleServiceObject();
							        	objConsoleService.startit(hmVariable); //direkter, ohne Thread...
							        	 
							        	 
							        	//#########################################################################
						                try {
						                	//Aber hier keine Flags vorhanden if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Warte auf neue Eingabe.");
						                	//Syso.println("\nWarte auf neue Eingabe.");
						                	Thread.sleep(lSleepTime);			                	
										} catch (InterruptedException e) {
											System.out.println("KeyPressThread: 2. Wait Error");
											e.printStackTrace();																						
											ExceptionZZZ ez = new ExceptionZZZ(e);
											throw ez;
										}
						                
						                
						                objKeyPressThreadUsed.isInputAllFinished(true);
						               	this.isInputAllFinished(false); //Auf zur nächsten Eingabe
						               
				                	}//end if cKey
					        	} //end if 	!(this.isCurrentInputFinished() && this.isInputAllFinished())			        					        					        	
		            		}//end if inputAllFinished
		            	}//end input:
		            	//}//End synchro		      		            	
		            }//end while isStopped
		    	}//end main:
				this.getConsoleController().isKeyPressThreadFinished(true);
		    	return bReturn;
			}    
		 
		 
		 
		 //############################
		 @Override
			public boolean isKeyPressThreadFinished() {
				return this.getConsoleController().isKeyPressThreadFinished();
			}
			@Override
			public void isKeyPressThreadFinished(boolean bFinished) {
				this.getConsoleController().isKeyPressThreadFinished(bFinished);
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
        	//System.out.println("Menueeingabe machen");
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
			//System.out.println("Menueeingabe ueberspringen");
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyYes)); //so, damit die Eingabe der Menue-Argumente uebersprungen wird 
			this.validSkipMenue();
		}
		public void validSkipMenue() {			
			//System.out.println("Menueaufbau ueberspringen");
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
		public synchronized IConsoleControllerZZZ getConsoleController() {
			return this.objConsole;
		}
		@Override
		public synchronized void setConsoleController(IConsoleControllerZZZ objConsole) {
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
		public abstract void makeMenueMain() throws ExceptionZZZ;
    	
    	@Override
    	public abstract boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ;
    	
    	@Override
		public abstract boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ;
    	
    	@Override
    	public abstract boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ;
    }

