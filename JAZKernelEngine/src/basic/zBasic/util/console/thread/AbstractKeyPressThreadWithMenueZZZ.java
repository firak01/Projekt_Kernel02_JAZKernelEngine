package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.MapUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;
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
    
    public void cancelToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
    	//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//wieder so als würde das Menü nicht übersprungen.
		this.cancelToMenue();
	}
	public void cancelToMenue() throws ExceptionZZZ {			
		System.out.println("Abbruch. Zurueck zum Menue");
		//this.isCurrentInputValid(false);					
		this.isCurrentMenue(true); //wieder zurück zum Menue
		this.isCurrentInputFinished(true);
	}
	
    public void validToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
    	//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyNo));//so, damit die Eingabe der Menue-Argumente übersprungen.
		this.validToMenue();
	}
	public void validToMenue() throws ExceptionZZZ {			
		System.out.println("Zurueck zum Menue");			
		this.isCurrentMenue(true);
		this.stop();
	}
	
	public void validSkipMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Das wit nur intern wichtig, darum hier keinen Status setzen
		if(hmVariable!=null) hmVariable.put(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS, BooleanZZZ.charToBoolean(IKeyPressConstantZZZ.cKeyYes)); //so, damit die Eingabe der Menue-Argumente uebersprungen wird 
		this.validSkipMenue();
	}
	public void validSkipMenue() throws ExceptionZZZ {			
		System.out.println("Menue ueberspringen");
		this.isCurrentInputValid(true);						                			
		this.isCurrentMenue(false);	
	}
	
	
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
    	this.requestStop();
	}
    	       
    @Override
	public void requestStop() throws ExceptionZZZ {
    	//TODOGOON20260831;//stopt den thread aber leider nicht...
    	System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": NEU STATT QUIT");
    	
    	//DAS IST FALSCH, STATT DESSEN MUSS DER CONTROLLER EINEN EVENT AN ALLE REGISTRIERTEN SCHICHEN
    	//DER KEYPRESSTHREAD SELBST WIRD NICHT GESTOPPT!!!
    	//this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);	        	

//	        	//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
    	//this.getConsoleController().setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);
    	this.getConsoleController().setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISTHREADS_STOPPED, true);
	
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
			
			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen				
			this.getConsoleController().isKeyPressThreadRunning(true);
										
			HashMapZZZ<String,Object> hmVariable = this.getVariableHashMap();
            while(!this.getConsoleController().isStopped()) {	
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
//					        	this.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
			        	
			        	
			        	//######################################################################
	                	//### Frage nach Mehrfacheingabe
			        	 if(!(this.isCurrentInputFinished() && this.isInputAllFinished())) {
			        		Syso.printSeparator();
			        		
			        		//String[] saKeysOfMenue =
			        		//TODOGOON20260826;//Einmalig makeQuestionYesNoMenueQuit anzeigen. Bei N, danach nur noch processMenueMainArgumentInput auswerten.
			        		
			        		//TODOGOON20260826;//Hier muss makeQuestionForKeysPressable(this.getInputReader(), saKeysOfMenue, "Eingabemöglichkeiten, siehe Menü. Anzeige des Menüs mit 'm');
			        		//Anschliessend mit m das Menü anzeigen, und irgendwie noch einen Menübefehl startbar machen (dort ist dann auch q drin).
			        		//   processMenueMainArgumentInput(sInput, hmVariable);
			        		
			        		//TODOGOON20260831;//Diese Question und die Antworten dynamisch mit einer Liste von Buchstaben/Zeichen definieren.
			        		
	                		sInput = KeyPressUtilZZZ.makeQuestionYesNoMenueStopQuit(this.getInputReader(), "Wollen Sie danach zurueck zum Menue oder mit den akuellen Menueangaben im gleichen Menüpunkt weiterarbeiten?");		                		                			                			    	                			                				               
	                		if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)){
	                			this.quit();
	                		}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyStop)) {
	                			this.stop();
		                	}else if(StringZZZ.equalsIgnoreCase(sInput,  IKeyPressConstantZZZ.cKeyMenue)) {			                				                				                    
		                    	this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten	
		                    	//Aber sofort und nicht erst noch eine Eingabe abwarten
		                    					                    	
		    	            	//Nein, damit beendet man sich selbst this.getKeyPressThread().requestStop();
		                    	
		                    	//Einen bestehenden Thread stoppen, aber will man das wirklich, nur wenn das menü angezeigt werden soll?
//				                    	IMenuPointZZZ objMenuOld = this.getMenuPoint();
//				    	            	if(objMenuOld!=null) {
//				    	            		objMenuOld.onStopit();
//				    	            	}			                    	
		                	} else {		               		                					                				                		
		                		boolean bYes = BooleanZZZ.stringToBoolean(sInput);
		                		boolean bDefault = sInput.length()==0; //Die Scanner Klasse liefert bei ENTER einen Leerstring
		                		boolean bMenue = bYes && !bDefault;
		                		if(bMenue) { //Merke: Hier wird die Logik nun vertauscht Y=nicht skippen, da zurück zum Menü
		                			this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten
		                		}else {			                		
		                			this.validSkipMenue(hmVariable);			                			
		                		}				                		

		                		
		                		if(this.getConsoleController().getStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISTHREADS_STOPPED)) {
		                		//if(this.getConsoleController().isConsoleServiceThreadStopped()) {
		                			this.validToMenue(hmVariable);//Zurueck zum Menü vorbereiten
		                		}else {
						        	IMenuPointZZZ objMenuPoint = this.getMenuPoint();
						        	if(objMenuPoint!=null) {
						        		iDebugCounterServiceThread++;
						        		System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": START DES SERVICE THREADS NR " + iDebugCounterServiceThread + " !!!!!!!!!!!!!!!!!!");
						        		//objMenuPoint.initit(hmVariable);
						        		 
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
//										        objKeyPressThreadUsed.isOutputAllFinished(false);//erst nach der Eingabe einen ggfs. vorher
								    
								        //Jetzt erst noch eine Eingabe machen....					                		
						        		if(!(objKeyPressThreadUsed.isCurrentInputFinished() && objKeyPressThreadUsed.isInputAllFinished())) {
								        	boolean bGoon = objKeyPressThreadUsed.processMenuePostArgumentInput(hmVariable);
								        	if(!bGoon) break main; //Quit
							        	}
						        		 
						        		IConsoleControllerZZZ objConsoleController = this.getConsoleController();
						        		IConsoleServiceZZZ objConsoleService = objConsoleController.getConsoleServiceObject();
						        		objConsoleService.startit(hmVariable); //direkter, ohne Thread...								        		 
						        	 } 								        	
			                	}
					        	
					        	
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
	 
	
	@Override
	public boolean stop() throws ExceptionZZZ {
		System.out.println("THREAD beenden");	
		return super.stop();
	}
	

	//### aus IConsoleControlable
    @Override
    public boolean isQuitted() throws ExceptionZZZ {
    	return this.getConsoleController().getStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED);
    	
	}
    
    @Override
	public void isQuitted(boolean bStop) throws ExceptionZZZ {
    	this.requestQuit();
	}
    	       
    
	@Override 
	public boolean quit() throws ExceptionZZZ {
		System.out.println("Konsole Beenden");		                					                    
        this.isCurrentInputValid(true);
        this.isCurrentInputFinished(true);
        this.isKeyPressThreadFinished(true);
        this.requestQuit(); //stop KeyPressThread über die gesetzte STOP Variable
        return true;
	}

	@Override
	public void requestQuit() throws ExceptionZZZ {
    	//Folgendes beendet im Grunde die ganze Konsole "q"="quit"
    	    	
    	//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
    	this.getConsoleController().setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED, true);
    	
    	//Setze also den ConsoleController... Alternativ dazu müsste er ggfs. auch hieran registriert werden.
    	//D.h. er müsste andere Interfaces noch implementieren.
		this.getConsoleController().isStopped(true);	        	
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

