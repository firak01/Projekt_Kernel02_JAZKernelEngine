package debug.zBasic.util.console.thread.multi.menu02;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerUserZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceUserZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenueableZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;
import basic.zBasic.util.console.thread.KeyPressUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.system.Syso;

public class AbstractConsoleServiceThreadZZZ<T> extends AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> implements IConsoleControllerUserZZZ, IConsoleServiceUserZZZ {
	protected volatile IConsoleControllerZZZ objConsoleController = null; //Darüber werden die Variablen und auch die Eingaben ausgetauscht
	protected volatile IConsoleServiceZZZ objConsoleService = null;

	protected boolean bStop = false;
	
	public static long lSLEEPTIME_DEFAULT = 1000;	
	protected long lSleepTime = -1;
	
	//### Konstruktor
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
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
	
	
	//### METHODEN
	@Override
	public void run() {
		try {        		
			this.start();
		} catch (ExceptionZZZ e) {				
			e.printStackTrace();
		}
	}
	
	//### aus IThreadEnabledZZZ
	@Override
	public boolean isStopped() {
		return this.bStop;
	}
	
	@Override
	public void isStopped(boolean bStop) {
		this.bStop = bStop;
	}
	
	@Override
	public void requestStop() {
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
			
			HashMapZZZ hmVariable = this.getConsoleController().getVariableHashMap();								
	        while(!this.isStopped()){
	        	
	        	long lSleepTime = this.getSleepTime();
	        	
	        	//Nein, das startet doppelt den consoleController.ConsoleService-Thread
	        	//IConsoleServiceZZZ objConsoleService = this.getConsoleController().getConsoleServiceObject();
				//objConsoleService.startit(hmVariable); //direkter, ohne Thread...
					        	 
	        	IConsoleServiceZZZ objConsoleService = this.getConsoleServiceObject();
	        	objConsoleService.startit(hmVariable); //direkter, ohne weiteren Thread...
					   
	        	
	        	
	        	//#########################################################################
                try {
                	Thread.sleep(lSleepTime);			                	
				} catch (InterruptedException e) {
					System.out.println("ConsoleServiceThread Wait Error");
					e.printStackTrace();
					
					ExceptionZZZ ez = new ExceptionZZZ(e);
					throw ez;
				}
				               
	        }//end while isStopped
		}//end main:
		this.getConsoleController().isKeyPressThreadFinished(true);
		return bReturn;
		
	}

	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	
	
}
