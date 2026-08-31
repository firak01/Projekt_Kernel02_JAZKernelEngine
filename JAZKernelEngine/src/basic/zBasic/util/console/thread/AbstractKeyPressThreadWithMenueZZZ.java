package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;
import debug.zBasic.util.console.thread.multi.menu02.AbstractThreadWithStatusLocalZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;


	 
	/** Der KeypressThread bestimmt die Eingabemöglichkeiten
	 *  und was damit getan werden soll.
	 *  Darum gibt es zu Demonstrationszwecken den KeyPressThreadDefaultZZZ
	 *  
	 * 
	 * @author Fritz Lindhauer, 18.10.2022, 09:15:40
	 * 
	 */
	public abstract class AbstractKeyPressThreadWithMenueZZZ<T> extends AbstractThreadWithStatusLocalZZZ<T> implements IConsoleControllerUserZZZ, IKeyPressThreadUserZZZ, IKeyPressThreadMenuableZZZ {
		private static final long serialVersionUID = -4067907743385739750L;
		
		private static Scanner inputReader = new Scanner(System.in);
		protected volatile static IConsoleControllerZZZ objConsoleController = null; //Darüber (und desssen hmVariable) werden die globalen Variablen ausgetauscht
		protected volatile HashMapZZZ<String, Object> hmVariable = null; //Darüber werden die lokalen Variablen und die Eingabe verwaltet.
		protected IMenuPointZZZ objMenuPoint = null; //Der im Menü ausgewählte Punkt, mit all seinen Eigenschaften und Code, der auszuführen ist.
		
		
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
		public IMenuPointZZZ getMenuPoint() throws ExceptionZZZ {
			return this.objMenuPoint;
		}
		
		@Override
		public void setMenuPoint(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
			this.objMenuPoint = objMenuPoint;
		}
		
		@Override 
		public HashMapZZZ<String, Object> getVariableHashMap() throws ExceptionZZZ {
			if(this.hmVariable==null) {
				this.hmVariable = new HashMapZZZ<String, Object>();				
			}
			return this.hmVariable;
		}
		
		@Override
		public void setVariableHashMap(HashMapZZZ<String, Object> hmVariable ) throws ExceptionZZZ {
			this.hmVariable = hmVariable;
		}
		
		//######################################
		@Override 
		public String getMethodForConsoleService() throws ExceptionZZZ{
//			HashMap<String,String> hm = this.getConsoleController().getVariableHashMap();
//			return (String) hm.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			
			HashMap<String,String> hm = this.getVariableHashMap();
			return (String) hm.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
		}
		
		@Override 
		public void setMethodForConsoleService(String sMethod) throws ExceptionZZZ{
			HashMap<String,String> hm1 = this.getConsoleController().getVariableHashMap();
			hm1.put(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED, sMethod);
			
			HashMap<String,String> hm2 = this.getVariableHashMap();
			hm2.put(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED, sMethod);
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
	        public boolean isStopped() throws ExceptionZZZ {
	        	return this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED);	    		
	    	}
	        
	        @Override
	    	public void isStopped(boolean bStop) throws ExceptionZZZ {
	        	this.requestStop();
	    	}
	        
	        @Override
	    	public void requestStop() throws ExceptionZZZ {
	        	
	        	//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
	        	this.getConsoleController().setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);
	    		
	        	//20260825
	        	//Momentan ist der ConsoleController nicht registriert. Ihn also so ansteuern.
	        	//ER soll dann die an ihn registrierten anderen Threads stoppen.
	        	
	        	//Setze also den ConsoleController... Alternativ dazu müsste er ggfs. auch hieran registriert werden.
	        	//D.h. er müsste andere Interfaces noch implementieren.
	    		this.getConsoleController().isStopped(true);
	        	
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
					
					HashMapZZZ<String,Object> hmVariable = this.getConsoleController().getVariableHashMap();								
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
				        	    //Das wird nur im Menue wieder auf false gesetzt !!! this.isCurrentInputFinished(false);
					        	if(bSkipArguments) {
					        		System.out.println("KeyPressThread: bSkipArguments=true");
					        	}else {				        		
					        		do {					        			
							        	if(this.isCurrentMenue()) {				        			
								        	this.makeMenuMain();  									
							        	}
														
						                //das holt wohl wort fuer wort von der Konsole: String sInput = inputReader.next();
							        	Scanner inputReader = this.getInputReader();				      
							        	sInput = inputReader.nextLine();
						                System.out.println("Pressed Menueselection:" + sInput);
						                if(sInput==null) break main;
						                
						                boolean bGoon = this.processMenuPoint(sInput,hmVariable);
						                if(!bGoon) break main;//Quit
						                
					        		}while(!this.isCurrentInputValid());	                
					        	}//end if bSkipArguments	
					        					        	
			        			this.isInputAllFinished(false);
					        	this.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
					        	
					        	//######################################################################
			                	//### Frage nach Mehrfacheingabe
					        	 if(!(this.isCurrentInputFinished() && this.isInputAllFinished())) {
					        		Syso.printSeparator();
					        		
					        		//String[] saKeysOfMenue =
					        		//TODOGOON20260826;//Einmalig makeQuestionYesNoMenueQuit anzeigen. Bei N, danach nur noch processMenueMainArgumentInput auswerten.
					        		
					        		//TODOGOON20260826;//Hier muss makeQuestionForKeysPressable(this.getInputReader(), saKeysOfMenue, "Eingabemöglichkeiten, siehe Menü. Anzeige des Menüs mit 'm');
					        		//Anschliessend mit m das Menü anzeigen, und irgendwie noch einen Menübefehl startbar machen (dort ist dann auch q drin).
					        		//   processMenueMainArgumentInput(sInput, hmVariable);
					        		
					        		
			                		sInput = KeyPressUtilZZZ.makeQuestionYesNoMenueQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue oder mit den akuellen Menueangaben im gleichen Menüpunkt weiterarbeiten?");		                		                			                			    	                			                				               
			                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
			                			this.quit();
				                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMenue)) {			                				                				                    
				                    	this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten	
				                    	//Aber sofort und nicht erst noch eine Eingabe abwarten
				                    	
				                    	//Einen bestehenden Thread stoppen
				    	            	//Nein, damit beendet man sich selbst this.getKeyPressThread().requestStop();	            		            	
				                    	IMenuPointZZZ objMenuOld = this.getMenuPoint();
				    	            	if(objMenuOld!=null) {
				    	            		objMenuOld.onStopit();
				    	            	}			                    	
				                	} else {		               		                					                				                		
				                		boolean bYes = BooleanZZZ.stringToBoolean(sInput);
				                		boolean bDefault = sInput.length()==0; //Die Scanner Klasse liefert bei ENTER einen Leerstring
				                		boolean bMenue = bYes && !bDefault;
				                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü
				                			this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten
				                		}else {			                		
				                			this.validSkipMenue(hmVariable);			                			
				                		}				                		
				                			
//				                		PROBLEM: 
//				                			WENN MAN EINMAL 2 ausgewählt hat, kommt man nach Änderung zu 1, keine Ausgabe mehr.
				         
				                		//PROBLEM BEIM ÄNERN VON 2 IN 1 und wieder in 2 wird dort mit 0 gezählt.
				                		//if(!this.isCurrentInputFinished()) {
								        	IMenuPointZZZ objMenuPoint = this.getMenuPoint();
								        	if(objMenuPoint!=null) {
								        		objMenuPoint.initit(hmVariable);
								        		 
								        		IConsoleControllerZZZ objConsoleController = this.getConsoleController();
								        		objConsoleController.addVariableHashMap(objMenuPoint.getVariableHashMap());
								        		IConsoleServiceZZZ_menuPointUsing objConsoleService = (IConsoleServiceZZZ_menuPointUsing) objConsoleController.getConsoleServiceObject();
								        		 
								        		objConsoleService.startit(objMenuPoint); //der Code liegt dann im objMenuPoint.onStartit();
								        		//this.isCurrentInputFinished(true);//Damit wird sichergestellt, den ConsoleService nur 1x auszuführen.
								        	 }else {
								        		 //
								        		//FALLS im Menü eine ANDERE THREAD KLASSE gewählt worden ist, oder this falls nicht...
										        IKeyPressThreadMenuableZZZ objKeyPressThreadUsed = (IKeyPressThreadMenuableZZZ) this.getKeyPressThread();
										        objKeyPressThreadUsed.isInputAllFinished(false);
										        objKeyPressThreadUsed.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
										    
										        //Jetzt erst noch eine Eingabe machen....					                		
								        		if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
										        	boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
										        	if(!bGoon) break main; //Quit
									        	}
								        		 
								        		IConsoleControllerZZZ objConsoleController = this.getConsoleController();
								        		IConsoleServiceZZZ objConsoleService = objConsoleController.getConsoleServiceObject();
								        		objConsoleService.startit(hmVariable); //direkter, ohne Thread...								        		 
								        	 } 								        	
					                	//}
							        	
							        	
							        	//TEST TESTS
							        	//boolean bTest = this.getConsoleController().getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTARTING);
							        	//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": STATUSLOCAL isStarting= " + bTest);
							        	
							        	//bTest = this.getConsoleController().getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTARTED);
							        	//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": STATUSLOCAL isStarted= " + bTest);
							        	
							        	 
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
						                
						                
						                //objKeyPressThreadUsed.isInputAllFinished(true);
						                this.getKeyPressThread().isInputAllFinished(true);
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
		
        
        public void cancelToMenue(HashMapZZZ<String,Object> hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//wieder so als würde das Menü nicht übersprungen.
			this.cancelToMenue();
		}
		public void cancelToMenue() {			
			System.out.println("Abbruch. Zurueck zum Menue");
			//this.isCurrentInputValid(false);					
    		this.isCurrentMenue(true); //wieder zurück zum Menue
    		this.isCurrentInputFinished(true);
		}
		
        public void validToMenue(HashMapZZZ<String,Object> hmVariable) throws IllegalArgumentException, ExceptionZZZ {
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
		
		public void validSkipMenue(HashMapZZZ<String,Object> hmVariable) throws IllegalArgumentException, ExceptionZZZ {
			//System.out.println("Menueeingabe ueberspringen");
			if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyYes)); //so, damit die Eingabe der Menue-Argumente uebersprungen wird 
			this.validSkipMenue();
		}
		public void validSkipMenue() {			
			//System.out.println("Menueaufbau ueberspringen");
			this.isCurrentInputValid(true);						                		
			this.isCurrentMenue(false);	
		}
		
		
		public void quit() throws ExceptionZZZ {
			System.out.println("Beenden");		                					                    
            this.isCurrentInputValid(false);
            this.isCurrentInputFinished(true);
            this.isKeyPressThreadFinished(true);
            this.requestStop(); //stop KeyPressThread über die gesetzte STOP Variable
		}
       
        @Override
		public synchronized IConsoleControllerZZZ getConsoleController() {
			return this.objConsoleController;
		}
		@Override
		public synchronized void setConsoleController(IConsoleControllerZZZ objConsoleController) {
			this.objConsoleController = objConsoleController;
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
		public abstract void makeMenuMain() throws ExceptionZZZ;
    	
    	@Override
    	public abstract boolean initit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
    	
    	@Override
		public abstract boolean processMenuPoint(String sInput, HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
    	
    	@Override
    	public abstract boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
    }

