package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControlableZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerUserZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceUserZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ_menuPointUsing;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;

/**Der ConsoleServiceThread wird dann gestartet,
 * wenn ein einfacher Aufruf des ConsoleService nicht reicht.
 * Der ConsoleServiceThread ruft dann den ConsoleService in einer Schleife immer wieder auf.
 * @author Fritz Lindhauer
 *
 * @param <T>
 */
public class AbstractConsoleServiceThreadZZZ<T> extends AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> implements IConsoleControllerUserZZZ, IConsoleServiceUserZZZ, IConsoleControlableZZZ {
	private static final long serialVersionUID = -1207680138665628581L;
	
	protected volatile IConsoleControllerZZZ objConsoleController = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
	protected volatile IConsoleServiceZZZ objConsoleService = null;
	
	public static long lSLEEPTIME_DEFAULT = 1000;	
	protected long lSleepTime = -1;
	
	//### Konstruktor
	public AbstractConsoleServiceThreadZZZ() throws ExceptionZZZ {
		super();
		AbstractThreadNew_();
	}
	
	public AbstractConsoleServiceThreadZZZ(String[]saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractThreadNew_();
	}
	
	public AbstractConsoleServiceThreadZZZ(HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);
		AbstractThreadNew_();
	}
	
		
	private boolean AbstractThreadNew_() throws ExceptionZZZ {
			
		return true;
	}
	
	
	//### GETTER / SETTER
	@Override
	public IConsoleControllerZZZ getConsoleController() throws ExceptionZZZ {
		return this.objConsoleController;
	}

	@Override
	public void setConsoleController(IConsoleControllerZZZ objConsoleController) throws ExceptionZZZ {
		this.objConsoleController = objConsoleController;
	}
	
	@Override
	public IConsoleServiceZZZ getConsoleServiceObject() {
		return this.objConsoleService;
	}

	@Override
	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleService) {
		this.objConsoleService = objConsoleService;
	}
	
	//###################################################
	//### METHODEN
		
	//### aus IThreadEnabledZZZ
	@Override
	public boolean isStopped() throws ExceptionZZZ {
		return this.getStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED);
	}
	
	@Override
	public void isStopped(boolean bStop) throws ExceptionZZZ {
		//this.bStop = bStop;
		this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, bStop);
	}
	
	@Override
	public void requestStop() throws ExceptionZZZ {
		this.isStopped(true);
	}
	
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
		 if(lSleepTime<0){
			 lSleepTime=0;
		 }
		 this.lSleepTime = lSleepTime;
	 }
	
	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.isStopped()) break main;
		
			this.getConsoleController().isKeyPressThreadRunning(true);
        	long lSleepTime = this.getSleepTime();

        	//20260828: Analog zur HashMap-Lösung auch den MenuPoint zur Verfügung stellen.
			IMenuPointZZZ objMenuPoint = this.getConsoleController().getMenuPoint();
			if(objMenuPoint==null) {				
				HashMapZZZ<String, Object> hmVariable = this.getConsoleController().getVariableHashMap();								
		        while(!this.isStopped()){
		        	
		        	
		        	
		        	/* Z.B. in ExamplanConsoleService wird, wenn entsprechender Menüpunkt ausgewählt wurde
		        	 *      folgendes aufgerufen,
		        	 *      sprich: Das steckt hinter: objConsoleService.startit(hmVariable);
		        	 *      
		        	   private boolean startCountAlphanumeric_(HashMapZZZ hmVariable) throws ExceptionZZZ {
							boolean bReturn = false;
							main:{
								ConsoleServiceMyAlphabetCounterZZZ objCounterService = new ConsoleServiceMyAlphabetCounterZZZ();
								
								IConsoleControllerZZZ objConsoleController = this.getConsoleController();
								
								//Einen neuen Thread erstellen
								final ConsoleServiceThreadZZZ objCounterServiceThread = new ConsoleServiceThreadZZZ();
								objCounterServiceThread.setConsoleController(objConsoleController);
								objCounterServiceThread.setConsoleServiceObject(objCounterService);
								
								//Den objCounterServiceThread am ConsoleController registrieren.
								//Dann kann er z.B. auf die "quit" Anweisung reagieren.
							    objConsoleController.registerForStatusLocalEvent(objCounterServiceThread);
													
							    Thread t2 = new Thread(objCounterServiceThread);
							    t2.start();					        	
		        	*/
		        	
		        	//#########################################################################
		        	IConsoleServiceZZZ objConsoleService = this.getConsoleServiceObject();
		        	objConsoleService.startit(hmVariable); //direkter Aufruf der Service-Methode, ohne weiteren Thread...
		        	
		        	try {
	                	Thread.sleep(lSleepTime);			                	
					} catch (InterruptedException e) {
						System.out.println("ConsoleServiceThread Wait Error");
						e.printStackTrace();
						
						ExceptionZZZ ez = new ExceptionZZZ(e);
						throw ez;
					}
		        }//end while isStopped
			}else {
				while(!this.isStopped()){
		        	
		        	//#########################################################################			        				        	
		        	IConsoleServiceZZZ_menuPointUsing objConsoleService = (IConsoleServiceZZZ_menuPointUsing) this.getConsoleServiceObject();
		        	objConsoleService.startit(objMenuPoint); //direkter Aufruf der Service-Methode, ohne weiteren Thread...

	                try {
	                	Thread.sleep(lSleepTime);			                	
					} catch (InterruptedException e) {
						System.out.println("ConsoleServiceThread Wait Error");
						e.printStackTrace();
						
						ExceptionZZZ ez = new ExceptionZZZ(e);
						throw ez;
					}						              
		        }//end while isStopped	        						               		       
			}
		}//end main:
		this.getConsoleController().isKeyPressThreadFinished(true);
		return bReturn;
		
	}

	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		return true; //... hier gibt es keine Einschränkung den Status nicht zu feuern.
	}
	
	
	//#### 
		/* (non-Javadoc)
		 * @see debug.zBasic.util.console.thread.multi.menu03.AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ#reactOnStatusLocalEvent(basic.zKernel.status.IEventObjectStatusLocalZZZ)
		 */
		@Override
		public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {		
			boolean bReturn = false;
			main:{			
				if(eventStatusLocal==null)break main;
				
				//Merke: Der hier empfangene Event wird folgendermassen erzeugt, s.: 
				//class AbstractObjectWithStatusLocalZZZ 
				//private boolean offerStatusLocal_(String sStatusName, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ{
				//
				//             Darin wird der Status gesucht über:
				//             ...             
				//             Ermittle das Enum aus dem Namen
				//             IEnumSetMappedStatusLocalZZZ objEnumMapped = StatusLocalAvailableHelperZZZ.searchEnumMappedByName(source, sEnumName, true);
				//			
				//Daher unbedingt nach dem Namen prüfen und nicht direkt die enums vergleichen
				//if(objStatus.equals(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED)) {
				
				//TODOGOON ; FALLUNTERSCHEIDUNG.
				IEnumSetMappedStatusLocalZZZ objStatus = eventStatusLocal.getStatusLocal();			
				if(objStatus.getName().equals(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED.name())) {
					
					this.requestStop();
					
				}
				
				if(objStatus.getName().equals(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED.name())) {
					
					this.requestQuit();
					
				}
				bReturn = true;
			}//end main:
			return bReturn;

		}

		//### aus IConsoleControlableZZZ
		@Override
		public boolean isQuitted() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void isQuitted(boolean bStop) throws ExceptionZZZ {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void requestQuit() throws ExceptionZZZ {
			this.requestStop();
		}
	
	
	
}
