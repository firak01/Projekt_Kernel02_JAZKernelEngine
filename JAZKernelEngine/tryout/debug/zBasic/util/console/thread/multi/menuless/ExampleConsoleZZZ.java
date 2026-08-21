package debug.zBasic.util.console.thread.multi.menuless;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.console.thread.IConsoleUserStartableZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
public class ExampleConsoleZZZ extends AbstractObjectWithFlagZZZ implements IExampleConsoleZZZ {
	private static ExampleConsoleZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
	
	private ExampleKeyPressThreadZZZ objThreadKeyPress=null;
	private IConsoleUserStartableZZZ objConsoleUser = null;
	private ExampleConsoleThreadZZZ objThreadConsole = null;
	
	//Variablen zur Steuerung des internen Threads
	private long lSleepTime=1000;
	private boolean bStop = false;
	
	/**Konstruktor ist private, wg. Singleton
	 */
	private ExampleConsoleZZZ() {		
		super();
		ConsoleMain_();
	}
	
	public static ExampleConsoleZZZ getInstance(){
		if(objConsole==null){
			objConsole = new ExampleConsoleZZZ();
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
	
	public boolean start() {
		boolean bReturn = false;
		main:{			
	        try {	        	
	        	final ExampleKeyPressThreadZZZ objThreadKeyPress = this.getKeyPressThread();
	            Thread t1 = new Thread(objThreadKeyPress);
	            t1.start();

	            final ExampleConsoleThreadZZZ objThreadConsole = this.getConsoleThread();	          
		        Thread t2 = new Thread(objThreadConsole);
		        t2.start();
	         
	        } catch (Exception e)        {
	            e.printStackTrace();
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
	public IConsoleUserStartableZZZ getConsoleUserObject() {
		return this.objConsoleUser;
	}

	@Override
	public void setConsoleUserObject(IConsoleUserStartableZZZ objConsoleUser) {
		this.objConsoleUser = objConsoleUser;
	}

	@Override
	public ExampleKeyPressThreadZZZ getKeyPressThread() {
		if(this.objThreadKeyPress==null) {
			long lSleepTime = this.getConsoleSleepTime();
			this.objThreadKeyPress = new ExampleKeyPressThreadZZZ(lSleepTime);		
		}
		return this.objThreadKeyPress;
	}

	private void setKeyPressThread(ExampleKeyPressThreadZZZ objKeyPressThread) {
		this.objThreadKeyPress = objKeyPressThread;
	}
	
	@Override
	public ExampleConsoleThreadZZZ getConsoleThread() {
		if(this.objThreadConsole==null) {
			ExampleKeyPressThreadZZZ objKeyPressThread = this.getKeyPressThread();
			if(objKeyPressThread!=null) {
			
				IConsoleUserStartableZZZ objConsoleUser = this.getConsoleUserObject();
				if(objConsoleUser!=null) {
					long lSleepTime = this.getConsoleSleepTime();
					this.objThreadConsole = new ExampleConsoleThreadZZZ(lSleepTime, objKeyPressThread);
			        this.objThreadConsole.setConsoleUserObject(this.getConsoleUserObject());
				}
			}
		}
		return this.objThreadConsole;    
	}
	
	private void setConsoleThread(ExampleConsoleThreadZZZ objThreadConsole) {
		this.objThreadConsole = objThreadConsole;
	}
	
	
	
	

	
	
	
}
