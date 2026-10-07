package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.LogZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.MapUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;
import custom.zKernel.Log;
import custom.zKernel.LogSingletonZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IConsoleControllerEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;


	 
/** Der KeypressThread bestimmt die Eingabemöglichkeiten
 *  und was damit getan werden soll.
 *  Darum gibt es zu Demonstrationszwecken den KeyPressThreadDefaultZZZ
 *  
 * 
 * @author Fritz Lindhauer, 18.10.2022, 09:15:40
 * 
 */
public abstract class AbstractKeyPressThreadWithMenueZZZ<T> extends AbstractKeyPressThreadZZZ<T> implements IVariableHashMapUserZZZ, IKeyPressThreadMenuableZZZ, IConsoleControlableZZZ {
	private static final long serialVersionUID = -4067907743385739750L;
	
	protected volatile HashMapZZZ<String, Object> hmVariable = null; //Darüber werden die lokalen Variablen und die Eingabe verwaltet.
	protected IMenuPointZZZ objMenuPoint = null; //Der im Menü ausgewählte Punkt, mit all seinen Eigenschaften und Code, der auszuführen ist.
	
	protected boolean bMakeMenue=true;//true, damit die erste Anzeige generiert wird
	protected boolean bMakeMenueWhenFinished=false;
	
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
    
    
    //### aus IKeyPressThreadMenueableZZZ
	@Override
	public IMenuPointZZZ getMenuPoint() throws ExceptionZZZ {
		return this.objMenuPoint;
	}
	
	@Override
	public void setMenuPoint(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
		this.objMenuPoint = objMenuPoint;
	}
	
	@Override
    public boolean isCurrentMenue() throws ExceptionZZZ {
    	return this.bMakeMenue;
    }
    @Override
    public void isCurrentMenue(boolean bMakeMenue) throws ExceptionZZZ {
    	this.bMakeMenue = bMakeMenue;
    }
    
    @Override
    public boolean isMenueWhenFinished() throws ExceptionZZZ{
    	return this.bMakeMenueWhenFinished;
    }
    
    @Override
    public void isMenueWhenFinished(boolean bMakeMenueWhenFinished) throws ExceptionZZZ{
    	this.bMakeMenueWhenFinished = bMakeMenueWhenFinished;
    }
    
    public void cancelToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
    	//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS01, false);//wieder so als würde das Menü nicht übersprungen.
		this.cancelToMenue();
	}
	public void cancelToMenue() throws ExceptionZZZ {			
		System.out.println("Abbruch. Zurueck zum Menue");
		//this.isCurrentInputValid(false);					
		this.isCurrentMenue(true); //wieder zurück zum Menue
		this.isCurrentInputFinished(true);
	}
	
	//Nach dem Beenden des Menüpunkts zurück zum Menü
	public void validToMenueLater(HashMapZZZ hmVariable) throws ExceptionZZZ {
		this.validSkipMenue(hmVariable);
		this.validToMenueLater();
	}
	public void validToMenueLater() throws ExceptionZZZ {			
		System.out.println("Spaeter zurueck zum Menue");			
		this.isMenueWhenFinished(true);		
	}
	
    public void validToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
    	//Merke: Das wit nur intern wichtig, darum hier keinen Status sondern nur die HashMap direkt setzen
		if(hmVariable!=null) {
			hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS01, false);//so, damit die Eingabe der Menue-Argumente NICHT MEHR übersprungen.
			this.validEnableRepeatQuestion(hmVariable);
		}
		this.validToMenue();
	}
	public void validToMenue() throws ExceptionZZZ {			
		System.out.println("Zurueck zum Menue");			
		this.validMenue(true);	
	}
	
	public void validSkipMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS01, true); //so, damit die Eingabe der Menue-Argumente uebersprungen wird 
		this.validSkipMenue();
	}
	public void validSkipMenue() throws ExceptionZZZ {			
		System.out.println("Menue ueberspringen");
		this.validMenue(false);	
	}
	public void validMenue(boolean bCurrentMenue) throws ExceptionZZZ {
		this.isCurrentInputValid(true);						                			
		this.isCurrentMenue(bCurrentMenue);		
	}
	
	
	//+++++++++++++++++++++++++
	
	public void validSkipRepeatQuestion(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS02, true); //so, damit die Eingabe der Mehrfacheingabe-Argumente uebersprungen wird 		
	}
	public void validEnableRepeatQuestion(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS02, false); //so, damit die Eingabe der Mehrfacheingabe-Argumente ermöglicht wird
	}
	
	//++++++++++++++
	
	@Override 
	public void setMethodForConsoleService(String sMethod) throws ExceptionZZZ{
		HashMapZZZ<String,Object> hm1 = this.getConsoleController().getVariableHashMap();
		hm1.put(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED, sMethod);
		
		HashMapZZZ<String,Object> hm2 = this.getVariableHashMap();
		hm2.put(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED, sMethod);
	}
	
	//### Methoden
	
	//### aus IKeyPressThreadZZZ


	
	
	
	
  
    //### aus IThreadEnabledZZZ
    @Override
    public boolean isStopped() throws ExceptionZZZ {
    	return this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED);	    		
	}
    
    @Override
	public void isStopped(boolean bStop) throws ExceptionZZZ {
    	this.requestStop(bStop);
	}
    	       
    @Override
	public boolean requestStop(boolean bStop) throws ExceptionZZZ {
    	System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": NEU STATT QUIT");
    	
    	//DAS IST FALSCH, STATT DESSEN MUSS DER CONTROLLER EINEN EVENT AN ALLE REGISTRIERTEN SCHICHEN
    	//DER KEYPRESSTHREAD SELBST WIRD NICHT GESTOPPT!!!
    	//this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);	        	

//      //Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
    	//this.getConsoleController().setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);
    	this.getConsoleController().setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISTHREAD_STOPPED, bStop);
    	
    	return true;
	}
    

    /** Abstrakte Methode, die so angelegt ist, das sie von anderen Consolen genutzt werden kann.
     *  Bisherige Implementierungen:
     *  Z.B. mit Verschlüsselungsklassen
     */
	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = true;
    	main:{
			int iDebugCounterServiceThread=0;
			int iDebugCounterLoop=0;
			
			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen				
			this.getConsoleController().isKeyPressThreadRunning(true);
										
			HashMapZZZ<String,Object> hmVariable = this.getVariableHashMap();
            while(!this.getConsoleController().isStopped()) {	
            	iDebugCounterLoop++;
            	System.out.println("Loop: " + iDebugCounterLoop);
            	long lSleepTime = this.getSleepTime();
            	//synchronized(this) {
            	input:{	            		
            		String sInput = null; boolean bSkipArguments01=false; boolean bSkipArguments02=false;
	            		            		            			            	
	            	if(!this.isInputAllFinished()) {
		        	    if(hmVariable!=null) {
		        	    	Object obj = hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS01);
		        	    	if(obj==null) {
		        	    		bSkipArguments01 = false;
		        	    	}else if (obj instanceof Boolean) {
		        	    		bSkipArguments01=((Boolean) obj).booleanValue();
		        	    	}else if(obj instanceof String) {
		        	    		bSkipArguments01 = BooleanZZZ.stringToBoolean(obj.toString());				        	        
		        	    	}
		        	    	
		        	    	
		        	    	Object obj02 = hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS02);
		        	    	if(obj02==null) {
		        	    		bSkipArguments02 = false;
		        	    	}else if (obj instanceof Boolean) {
		        	    		bSkipArguments02=((Boolean) obj).booleanValue();
		        	    	}else if(obj instanceof String) {
		        	    		bSkipArguments02 = BooleanZZZ.stringToBoolean(obj.toString());				        	        
		        	    	}
		        	    }
			        	   
		        	    //########################################################
		        	    //#### Eingabe der Argumente
		        	    //Das wird nur im Menue wieder auf false gesetzt !!! this.isCurrentInputFinished(false);
			        	if(bSkipArguments01) {
			        		System.out.println("KeyPressThread: bSkipArguments01=true");
			        	}else {				        		
			        		do {					        			
					        	if(this.isCurrentMenue()) {				        			
						        	this.makeMenuMain();  									
					        	}
												
				                //das holt wohl wort fuer wort von der Konsole: String sInput = inputReader.next();
					        	Scanner inputReader = this.getInputReader();				      
					        	sInput = inputReader.nextLine();				                
				                Log.writeInfo("Pressed Menueselection: " + sInput);
				                
				                //FGL20261006:
				                //this.logProtocol soll weg, wenn das nicht mehr in AbstractObjekt eine Methode ist.
				                //this.protocol("Pressed Menueselection: " + sInput);				                
				                //statt dessen:
				                Log.protocol(this, "Pressed Menueselection: " + sInput);// für die formatierte Ausgabe.
				                
				                Log.printLine(this, "Pressed Menueselection: " + sInput);// für die Ausgabe mit Syso.println(...).
				                if(sInput==null) break main;
				                
				                boolean bGoon = this.processMenuPoint(sInput,hmVariable); //bereite alles vor, gemäß dem ausgewählten Menüpunkt.
				                if(!bGoon) {
				                	Log.writeDebug(this, "Break after Menueselection: "  + sInput);
				                	break main;//Quit
				                }
				                
			        		}while(!this.isCurrentInputValid());	                
			        	}//end if bSkipArguments	
			        					        	
	        			this.isInputAllFinished(false);
			  			        	
			        	//######################################################################
	                	//### Starte service
			        	 if(!(this.isCurrentInputFinished() && this.isInputAllFinished())) {
			        		 
			        		//FALLS im Menü eine ANDERE THREAD KLASSE gewählt worden ist, oder this falls nicht...
						    IKeyPressThreadMenuableZZZ objKeyPressThreadUsed = (IKeyPressThreadMenuableZZZ) this.getKeyPressThread();
						        			        		 
	                		if(this.getConsoleController().getStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISTHREAD_STOPPED)) {
	                			this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten
	                		}else {
					        	IMenuPointZZZ objMenuPoint = this.getMenuPoint();
					        	if(objMenuPoint==null) {
					        		
					        		//#######################################
					        		 //### Ohne MenuPoint-Objekt
						        		
					        		//#############################################################
					        		//Noch weitere Angaben holen... aus dem KeyPressThread						        							                		
					        		if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
							        	boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
							        	if(!bGoon) break main; //Quit
						        	}
						        		
					        		
					        		//++ 
					        		IConsoleControllerZZZ objConsoleController = this.getConsoleController();
					        		IConsoleServiceZZZ objConsoleService = objConsoleController.getConsoleServiceObject();
					        		objConsoleService.startit(hmVariable); //direkter, ohne Thread...	
					        		//++
					        		
					        		//### Frage nach Mehrfacheingabe (NACH service start, nur sinnvoll ohne Thread)			        			                						        		 
					        		Syso.printSeparator();			        		
					        		sInput = KeyPressUtilZZZ.makeQuestionYesNoMenuStopQuit(this.getInputReader(), "Wollen Sie jetzt zurueck zum Menue?");			        					        					        			                		                			                			    	                			                				              
			                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
			                			this.quit();
			                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyStop)) {
			                			this.stop();
				                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMenue)) {			                				                				                    
				                    	this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten	
				                    	//Aber sofort und nicht erst noch eine Eingabe abwarten
				                    					                    	
				    	            	//Nein, damit beendet man sich selbst this.getKeyPressThread().requestStop();
				                    	
				                    	//Einen bestehenden Thread stoppen, aber will man das wirklich, nur wenn das menü angezeigt werden soll?
//								        IMenuPointZZZ objMenuOld = this.getMenuPoint();
//								    	if(objMenuOld!=null) {
//								    		objMenuOld.onStopit();
//								    	}			                    	
				                	} else {		               		                					                				                		
				                		
							        	
				                		boolean bYes = BooleanZZZ.stringToBoolean(sInput);
				                		boolean bDefault = sInput.length()==0; //Die Scanner Klasse liefert bei ENTER einen Leerstring
				                		boolean bMenue = bYes && !bDefault;
				                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü
				                			this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten
				                		}else {			                		
				                			this.validSkipMenue(hmVariable);			                			
				                		}	
				                		
				                		//Nach dem ersten Schritt schon wieder stoppen
				                		if(this.isCurrentMenue()) {
				                			this.stop();
				                		}
				                									        					                					                		
				                	}//end if cKey
					        	 }else {		

						        		//#######################################
						        		//### MIT MenuPoint-Objekt
						        							        							        							        	
						        		//### Frage nach Mehrfacheingabe (VOR dem servicestart, sinnvoll bei Thread)					     
						        		//Merke: Die Scanner - Eingabe verhindert, dass belibeig viele THREADS gestartet werden. Darum nur die Eingabe "verbergen"					        		
						        		Syso.printSeparator();		
						        		if(bSkipArguments02) {
						        			//Damit wartet man auf bestimmte Punkte... sInput = KeyPressUtilZZZ.makeWaitForInputYesNoMenueStopQuit(this.getInputReader());
						        			//Warten auf irgendeine Eingabe, z.B. während das Laufs des ConsoleServiceThreads oder nach dem Ende des ConsoleServiceThreads
						        			if(objMenuPoint.getServiceThread()==null) {
						        			
						        			}else {
						        				//sInput = KeyPressUtilZZZ.makeWaitForInputAny(this.getInputReader(), "Nach dem Ende irgendeine Eingabe machen.");						        				
						        				sInput = KeyPressUtilZZZ.makeWaitForInputMinusPlusMenuStopQuit(this.getInputReader(), "Nach dem Ende irgendeine Eingabe machen, bzw. während des Laufs einen Menüpunkt eingeben");
						        			}
						        		}else {					        			
						        			
						        			 //Jetzt erst noch ggfs. eine Eingabe machen....	
									        //Merke: Der Code aus dem KeyPressThread soll in den Menüpunkt verlagert sein.
//							        		if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
//									        	boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
//									        	if(!bGoon) break main; //Quit
//								        	}
							        		
							        		//#####################################################################
						        			//Noch weitere Angaben holen... aus dem Menüpunkt						                    	
							        		if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
									        	boolean bGoon = objMenuPoint.processMenuePostArgumentInput(hmVariable);
									        	
									        	//Das Problem ist, dass man hier nur true/false auswerten kann.
									        	//TODOGOON: IRGENDEINEN RETURNCODE......
									        	//ALSO MOMENTAN: NUR ZURÜCK ZUM MENÜ SINNVOLL....
									        	if(!bGoon) {
									        		sInput =  CharZZZ.toString(IKeyPressConstantZZZ.cKeyMenue); //damit wird der 'M' Tastenkey simuliert.								       
									        	}else {
								        			sInput = KeyPressUtilZZZ.makeQuestionYesNoMenuStopQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue?");
								        			this.validSkipRepeatQuestion(hmVariable);
								        			
								        			
									        	}			
							        		}
						        		}//end if bSkipArguments02
				                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
				                			this.quit();
				                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyStop)) {
				                			this.stop();
					                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMenue)) {			                				                				                    
					                    	this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten	
					                    	//Aber sofort und nicht erst noch eine Eingabe abwarten
					                    					                    	
					    	            	//Nein, damit beendet man sich selbst this.getKeyPressThread().requestStop();
					                    	
					                    	//Einen bestehenden Thread stoppen, 
					                    	//aber will man das wirklich, nur wenn das menü angezeigt werden soll?
//								            IMenuPointZZZ objMenuOld = this.getMenuPoint();
//								    	    if(objMenuOld!=null) {
//								    	    	objMenuOld.onStopit();
					                    		this.stop();
//								    	     }	
					                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyYes)) {	
					                		boolean bYes = BooleanZZZ.stringToBoolean(sInput);
					                		boolean bDefault = sInput.length()==0; //Die Scanner Klasse liefert bei ENTER einen Leerstring
					                		boolean bMenue = bYes && !bDefault;
					                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü					                		
					                			this.validToMenueLater(hmVariable);//Zurueck zum Menü nach dem Ende vorbereiten
					                		}else {			                		
					                			this.validSkipMenue(hmVariable);			                			
					                		}
					                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMinus)) {
					                		this.setSleepTime(this.getSleepTime() - 1000);
					                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyPlus)) {
					                		this.setSleepTime(this.getSleepTime() + 1000);
					                	} else {				                		
					                		//this.validSkipMenue(hmVariable);				                			
					                	}//end if cKey	
					                		
				                		//Wenn der ConsoleService fertig ist zum Menü
				                		//if(objConsoleService.getMenuPoint().getServiceThread().isStopped()) {
				                		if(objMenuPoint==null) {
				                			
				                		}else {
							        		if(objMenuPoint.getServiceThread()==null) {
							        			
							        			iDebugCounterServiceThread++;
									        	System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": START DES SERVICE NR " + iDebugCounterServiceThread + " !!!!!!!!!!!!!!!!!!");
									        	//objMenuPoint.initit(hmVariable);
									        		 
									        	IConsoleControllerZZZ objConsoleController = this.getConsoleController();
									        	objConsoleController.addVariableHashMap(objMenuPoint.getVariableHashMap());
									        	IConsoleServiceZZZ_menuPointUsing objConsoleService = (IConsoleServiceZZZ_menuPointUsing) objConsoleController.getConsoleServiceObject();
									        		 
									        	objConsoleService.startit(objMenuPoint); //der Code liegt dann im objMenuPoint.onStartit();
									        	
									        	
							        		}else {
					                			
					                			//++ Falls gestoppt wurde, nicht doch noch neu starten, das wird durch die obige "wait" Methode sichergestellt. Zum Überprüfen die Anzahl der Threads ausgeben.
						                		//if(!this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED)){
							        			if(objMenuPoint.getServiceThread().isStopped()) {
							        				if(this.isMenueWhenFinished()){
						                				this.validToMenue(hmVariable);	//zurück zum Menü vorbereiten				                			
							                			//this.stop();
							                		}
						                		}else {
						                			
						                		}									        
					                		}		                			
				                		}
				                					                					                						                					     
					        		//+++++++++++++++++++++++++ 					        		
					        	 } //end if objMenue==null								        	
		                	} //end if consoleController.isThreadsStopped
	
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
			                //this.getKeyPressThread().isInputAllFinished(true);
			               	this.isInputAllFinished(false); //Auf zur nächsten Eingabe
			               		                	
			        	} //end if 	!(this.isCurrentInputFinished() && this.isInputAllFinished())			        					        					        	
            		}//end if inputAllFinished
            	}//end input:
            	//}//End synchro		      		            	
            }//end while(!this.getConsoleController().isStopped()) {	
    	}//end main:
		this.getConsoleController().isKeyPressThreadFinished(true);
    	return bReturn;
	}    
	 
	
	@Override
	public boolean stop() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
		
		System.out.println("THREAD beenden");	
		bReturn = super.stop();
		if(!bReturn) break main;
		
		IMenuPointZZZ objMenuOld = this.getMenuPoint();
 	    if(objMenuOld!=null) {
 	    	bReturn = objMenuOld.onStopit();
 	     }	
		
		}//end main:
		return bReturn;
	}
	

	//### aus IConsoleControlable
    @Override
    public boolean isQuitted() throws ExceptionZZZ {
    	return this.getConsoleController().getStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED);
    	
	}
    
    @Override
	public void isQuitted(boolean bQuit) throws ExceptionZZZ {
    	this.requestQuit(bQuit);
	}
    	       
    
	@Override 
	public boolean quit() throws ExceptionZZZ {
		System.out.println("Konsole Beenden");		                					                    
        this.isCurrentInputValid(true);
        this.isCurrentInputFinished(true);
        this.isKeyPressThreadFinished(true);
        return this.requestQuit(true); //stop KeyPressThread über die gesetzte STOP Variable        
	}

	@Override
	public boolean requestQuit(boolean bQuit) throws ExceptionZZZ {
    	//Folgendes beendet im Grunde die ganze Konsole "q"="quit"
    	    	
    	//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
    	this.getConsoleController().setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED, true);
    	
    	//Setze also den ConsoleController... Alternativ dazu müsste er ggfs. auch hieran registriert werden.
    	//D.h. er müsste andere Interfaces noch implementieren.
		this.getConsoleController().isStopped(bQuit);
		return true;
	}

	
	//+++++++++++++++++++++++++++
	@Override
	public synchronized HashMapZZZ<String, Object> getVariableHashMap() throws ExceptionZZZ {
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapZZZ<String,Object>();
		}
		return this.hmVariable;
	}
	
	@Override 
	public synchronized void setVariableHashMap(HashMapZZZ<String, Object> hmVariable) throws ExceptionZZZ {
		this.hmVariable = hmVariable;
	}
	
	@Override
	public void addVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		HashMapZZZ<String,Object> hmOld = this.hmVariable;
		HashMap<String, Object> hmTemp = HashMapUtilZZZ.mergeMaps_LastKeyRemains(hmOld, hmVariable);
		this.hmVariable = MapUtilZZZ.toHashMapZZZ(hmTemp);
	}
	
	//+++++++++++++++++++++++++++++++++    	
	@Override
	public abstract void makeMenuMain() throws ExceptionZZZ;
	
	@Override
	public abstract boolean initit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
	
	@Override
	public abstract boolean processMenuPoint(String sInput, HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
	
	@Override
	public abstract boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
}

