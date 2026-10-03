package debug.zBasic.util.console.thread.multi.menuless02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 *  Als Weiterentwicklung nutzt sie hier 
 *  -- Einen ConsoleController
 *  -- AbstractKeyPressThreadZZZ, d.h. die run() Methode wird nicht vom Key-Press Thread komplett überschrieben.
 *     Ausserdem erbt AbstractKeyPressThreadZZZ von AbstractThreadWithStatusLocalZZZ
 *     D.h. wir arbeiten hier mit STATUS, statt einfache boolean Variablen zu setzen.
 *          Also nicht mehr
 *          	bStop=true.
 *          Sondern
 *          	this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED, bStop);
 *              D.h. es wird eine HashMap mit den Statuswerten gefüllt. 
 * 
 * @author Fritz Lindhauer, 03.10.2026, 08:01:04
 * 
 */
TODOGOON20261003;//Baue das so auf wie:
//package debug.zBasic.util.console.thread.multi.menu03;
//                   ExampleComposition_ConsoleAsThreadZZZ
public class ExampleComposition_ConsoleZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IExampleConsoleZZZ {
	private static final long serialVersionUID = 2937832009910133403L;

	//SINGLETON
	private static ExampleComposition_ConsoleZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
	
	private IKeyPressThreadZZZ objThreadKeyPress=null;
	private IConsoleServiceZZZ objConsoleService = null;
	private ExampleConsoleThreadZZZ objThreadConsole = null;
	
	//Variablen zur Steuerung des internen Threads
	private long lSleepTime=1000;
	private boolean bStop = false;
	
	/**Konstruktor ist private, wg. Singleton
	 */
	private ExampleComposition_ConsoleZZZ() {		
		super();
		ConsoleMain_();
	}
	
	public static ExampleComposition_ConsoleZZZ getInstance(){
		if(objConsole==null){
			objConsole = new ExampleComposition_ConsoleZZZ();
		}
		return objConsole;		
	}
	
	private boolean ConsoleMain_() {
		boolean bReturn = false;
		main:{
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean start() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{			
	        try {
	        	IConsoleServiceZZZ objConsoleService = new ExampleConsoleServiceZZZ();
	        	this.setConsoleServiceObject(objConsoleService);
	        	
	        	final ExampleKeyPressThreadZZZ objThreadKeyPress = (ExampleKeyPressThreadZZZ) this.getKeyPressThread();
	            Thread t1 = new Thread(objThreadKeyPress);
	            t1.start();

	            final ExampleConsoleThreadZZZ objThreadConsole = this.getConsoleThread();	          
		        Thread t2 = new Thread(objThreadConsole);
		        t2.start();
	         
	        } catch (Exception e)        {
	            ExceptionZZZ ez = new ExceptionZZZ(e);
	            throw ez;
	        }
			
		}//end main:
		return bReturn;		
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
	
	public long getConsoleSleepTime() {
		return this.lSleepTime;
	}
	public void setConsoleSleepTime(long lSleepTime) {
		this.lSleepTime = lSleepTime;
	}

	@Override
	public IConsoleServiceZZZ getConsoleServiceObject() {
		return this.objConsoleService;
	}

	@Override
	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleService) {
		this.objConsoleService = objConsoleService;
	}

	@Override
	public IKeyPressThreadZZZ getKeyPressThread() throws ExceptionZZZ {
		if(this.objThreadKeyPress==null) {
			long lSleepTime = this.getConsoleSleepTime();
			this.objThreadKeyPress = new ExampleKeyPressThreadZZZ(lSleepTime);		
		}
		return this.objThreadKeyPress;
	}

	@Override
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
		this.objThreadKeyPress = objKeyPressThread;
	}
	
	
	
	public ExampleConsoleThreadZZZ getConsoleThread() throws ExceptionZZZ {
		if(this.objThreadConsole==null) {
			ExampleKeyPressThreadZZZ objKeyPressThread = (ExampleKeyPressThreadZZZ) this.getKeyPressThread();
			if(objKeyPressThread!=null) {
			
				IConsoleServiceZZZ objConsoleUser = this.getConsoleServiceObject();
				if(objConsoleUser!=null) {
					long lSleepTime = this.getConsoleSleepTime();
					this.objThreadConsole = new ExampleConsoleThreadZZZ(lSleepTime, objKeyPressThread);
			        this.objThreadConsole.setConsoleServiceObject(this.getConsoleServiceObject());
				}
			}
		}
		return this.objThreadConsole;    
	}
	
	public void setConsoleThread(ExampleConsoleThreadZZZ objThreadConsole) {
		this.objThreadConsole = objThreadConsole;
	}
}
