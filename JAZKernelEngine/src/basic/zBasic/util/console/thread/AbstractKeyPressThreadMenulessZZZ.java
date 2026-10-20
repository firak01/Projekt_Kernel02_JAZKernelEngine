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
public abstract class AbstractKeyPressThreadMenulessZZZ<T> extends AbstractKeyPressThreadZZZ<T> implements IVariableHashMapUserZZZ, IKeyPressThreadMenulessZZZ, IConsoleControlableZZZ {
	private static final long serialVersionUID = -4067907743385739750L;
	
	protected volatile HashMapZZZ<String, Object> hmVariable = null; //Darüber werden die lokalen Variablen und die Eingabe verwaltet.
	
	//### Konstruktor
	 public AbstractKeyPressThreadMenulessZZZ() throws ExceptionZZZ {
    	super();
    }    
    public AbstractKeyPressThreadMenulessZZZ(long lSleepTime) throws ExceptionZZZ {
    	super(lSleepTime);    	
    }
	public AbstractKeyPressThreadMenulessZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
    	super();
    	AbstractKeyPressThreadWithMenueNew_(objConsole, -1);
    }
    public AbstractKeyPressThreadMenulessZZZ(IConsoleControllerZZZ objConsole, long lSleepTime) throws ExceptionZZZ {
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
    
    
    //### aus IKeyPressThreadMenulessZZZ
	
	//### Methoden
	
	//### aus IKeyPressThreadZZZ


	
	
	
	
  
    //### aus IThreadEnabledZZZ
//    @Override
//    public boolean isStopped() throws ExceptionZZZ {
//    	return this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED);	    		
//	}
//    
//    @Override
//	public void isStopped(boolean bStop) throws ExceptionZZZ {
//    	this.requestStop(bStop);
//	}
//    	       
//    @Override
//	public boolean requestStop(boolean bStop) throws ExceptionZZZ {
//    	String sLog = "NEU STATT QUIT";
//    	//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": NEU STATT QUIT");
//    	Log.writeDebug(this, sLog);
//    		
//    	//DAS IST FALSCH, STATT DESSEN MUSS DER CONTROLLER EINEN EVENT AN ALLE REGISTRIERTEN SCHICHEN
//    	//DER KEYPRESSTHREAD SELBST WIRD NICHT GESTOPPT!!!
//    	//this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);	        	
//
////      //Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
//    	//this.getConsoleController().setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, true);
//    	this.getConsoleController().setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISTHREAD_STOPPED, bStop);
//    	
//    	return true;
//	}
    

    /** Abstrakte Methode, die so angelegt ist, das sie von anderen Consolen genutzt werden kann.
     *  Bisherige Implementierungen:
     *  Z.B. mit Verschlüsselungsklassen
     */
	@Override
	public abstract boolean start() throws ExceptionZZZ; //Da hier keine Menü definiert wird (processMenuPoint(...), etc.) wird das im konkreten KeyPressThread ausprogrammiert.
//	{
//		boolean bReturn = true;
//    	main:{
//			String sLog=null;
//			int iDebugCounterServiceThread=0;
//			int iDebugCounterLoop=0;
//			
//			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
//			//       Darum muss man alle Eingaben in diesem KeyPressThread erledigen				
//			this.getConsoleController().isKeyPressThreadRunning(true);
//										
//			HashMapZZZ<String,Object> hmVariable = this.getVariableHashMap();
//            while(!this.getConsoleController().isStopped()) {	
//            	iDebugCounterLoop++;
////            	Log.printlnDateWithPosition(this, "TEST01");
////            	Log.protocolDateWithPosition(this, "TEST01");
//            	
//            	sLog = "Loop: " + iDebugCounterLoop;
//            	Log.writeDebug(this, sLog);//System.out.println("Loop: " + iDebugCounterLoop);
//            	long lSleepTime = this.getSleepTime();
//            	//synchronized(this) {
//            	input:{	            		
//            		String sInput = null; boolean bSkipArguments01=false; boolean bSkipArguments02=false;
//	            		            		            			            	
//	            	if(!this.isInputAllFinished()) {
//		        	    if(hmVariable!=null) {
//		        	    	Object obj = hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS01);
//		        	    	if(obj==null) {
//		        	    		bSkipArguments01 = false;
//		        	    	}else if (obj instanceof Boolean) {
//		        	    		bSkipArguments01=((Boolean) obj).booleanValue();
//		        	    	}else if(obj instanceof String) {
//		        	    		bSkipArguments01 = BooleanZZZ.stringToBoolean(obj.toString());				        	        
//		        	    	}
//		        	    	
//		        	    	
//		        	    	Object obj02 = hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_BOOLEAN_SKIP_ARGUMENTS02);
//		        	    	if(obj02==null) {
//		        	    		bSkipArguments02 = false;
//		        	    	}else if (obj instanceof Boolean) {
//		        	    		bSkipArguments02=((Boolean) obj).booleanValue();
//		        	    	}else if(obj instanceof String) {
//		        	    		bSkipArguments02 = BooleanZZZ.stringToBoolean(obj.toString());				        	        
//		        	    	}
//		        	    }
//			        	   
//		        	    //########################################################
//		        	    //#### Ohne Menü starten...
//		        	  //++ 
//		        		IConsoleControllerZZZ objConsoleController = this.getConsoleController();
//		        		IConsoleServiceZZZ objConsoleService = objConsoleController.getConsoleServiceObject();
//		        		objConsoleService.startit(hmVariable); //direkter, ohne Thread...	
////		        		//++
//		        	   
//
//	
//				        	//#########################################################################
//			                try {
//			                	//Aber hier keine Flags vorhanden if(this.getFlag(IFlagZEnabledZZZ.FLAGZ.DEBUG)) System.out.println("Warte auf neue Eingabe.");
//			                	//Syso.println("\nWarte auf neue Eingabe.");
//			                	Thread.sleep(lSleepTime);			                	
//							} catch (InterruptedException e) {
//								System.out.println("KeyPressThread: 2. Wait Error");
//								e.printStackTrace();																						
//								ExceptionZZZ ez = new ExceptionZZZ(e);
//								throw ez;
//							}
//			                
//			                
//			                //objKeyPressThreadUsed.isInputAllFinished(true);
//			                //this.getKeyPressThread().isInputAllFinished(true);
////			               	this.isInputAllFinished(false); //Auf zur nächsten Eingabe
//			               		                	
////			        	} //end if 	!(this.isCurrentInputFinished() && this.isInputAllFinished())			        					        					        	
//            		}//end if inputAllFinished
//            	}//end input:
//            	//}//End synchro		      		            	
//            }//end while(!this.getConsoleController().isStopped()) {	
//    	}//end main:
//		this.getConsoleController().isKeyPressThreadFinished(true);
//    	return bReturn;
//	}    
	 
	
	@Override
	public boolean stop() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
					
			System.out.println("THREAD beenden");	
			bReturn = super.stop();
			if(!bReturn) break main;
						
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
	public abstract boolean initit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;		
}

