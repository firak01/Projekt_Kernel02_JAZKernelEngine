package basic.zBasic.util.console.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
public abstract class AbstractConsoleControllerZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IConsoleControllerZZZ {
	private static final long serialVersionUID = 303154337707751073L;

	protected volatile static IConsoleControllerZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
	
	private IKeyPressThreadMenulessZZZ objThreadKeyPress=null;
	private IConsoleServiceZZZ objConsoleUserStarter = null;
	
	//Variablen zur Steuerung des internen Threads
	private long lSleepTime=1000;
	private volatile static boolean bStop = false;
	private volatile static boolean bInputFinished=false;
	private volatile static boolean bOutputFinished=false;
	private volatile static boolean bInputThreadFinished = false;
	private volatile static boolean bInputThreadRunning = false;	
	private volatile static boolean bConsoleUserThreadFinished = false;
	private volatile static boolean bConsoleUserThreadRunning = false;
	
	//Zur dynmischen Verwaltung von Variablen, die in einem Thread für den anderen Thread gedacht sind
	private volatile static HashMapZZZ<String,Object> hmVariable = null;
	//To ensure that updates to variables propagate predictably to other threads, we should apply the volatile modifier to those variables:
	
	
	/**Konstruktor ist private, wg. Singleton
	 */
	protected AbstractConsoleControllerZZZ() {		
		super();
		ConsoleMain_();
	}
	//Merke: das geht static nicht abstract public abstract IConsoleZZZ getInstance();
	
	
	
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
	        	final IKeyPressThreadMenulessZZZ objThreadKeyPress = this.getKeyPressThread();
	        	if(objThreadKeyPress!=null) {
	        		Thread t1 = new Thread((Runnable) objThreadKeyPress);
	        		t1.start();
	        	}else {
	        		ExceptionZZZ ez = new ExceptionZZZ("No KeyPressThread provided", iERROR_PROPERTY_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
	        	}

//	            final IConsoleZZZ objThreadConsole = this.getConsoleThread();	  
//	            if(objThreadConsole!=null) {
//			        Thread t2 = new Thread((Runnable) objThreadConsole);
//			        t2.start();
//	            }else {
//	        		ExceptionZZZ ez = new ExceptionZZZ("No ConsoleThread provided", iERROR_PROPERTY_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//	        	}
	         
	        } catch (Exception e)        {
	            e.printStackTrace();
	        }
			
		}//end main:
		return bReturn;		
	}
	
	@Override
	public synchronized boolean isInputAllFinished() {
		return this.bInputFinished;
	}
	
	@Override
	public synchronized void isInputAllFinished(boolean bInputFinished) {
		this.bInputFinished = bInputFinished;
	}
	
	@Override
	public synchronized boolean isOutputAllFinished() {
		return this.bOutputFinished;
	}
	
	@Override
	public synchronized void isOutputAllFinished(boolean bOutputFinished) {
		this.bOutputFinished = bOutputFinished;
	}
	
	
	@Override
	public boolean isKeyPressThreadFinished() {
		return this.bInputThreadFinished;
	}
	
	@Override
	public void isKeyPressThreadFinished(boolean bInputFinished) {
		this.bInputThreadFinished = bInputFinished;
		if(this.bInputThreadFinished) {
			this.bInputThreadRunning=false;
		}else {
			this.bInputThreadRunning=true;
		}
	}
	
	
	@Override
	public boolean isKeyPressThreadRunning() {		
		return this.bInputThreadRunning;
	}
	
	@Override
	public void isKeyPressThreadRunning(boolean bInputRunning) {
		this.bInputThreadRunning = bInputRunning;
		if(this.bInputThreadRunning) {
			this.bInputThreadFinished=false;
		}else {
			this.bInputThreadFinished=true;
		}
	}
	
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
	public long getSleepTime() {
		return this.lSleepTime;
	}
	
	@Override
	 public void setSleepTime(long lSleepTime) {
		 if(lSleepTime<0){
			 lSleepTime=0;
		 }
		 this.lSleepTime = lSleepTime;
	 }

	@Override
	public IConsoleServiceZZZ getConsoleServiceObject() {
		return this.objConsoleUserStarter;
	}

	@Override
	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleUser) {
		this.objConsoleUserStarter = objConsoleUser;
	}

	@Override
	public IKeyPressThreadMenulessZZZ getKeyPressThread() throws ExceptionZZZ {
		if(this.objThreadKeyPress==null) {
			long lSleepTime = this.getSleepTime();
			this.objThreadKeyPress = new KeyPressThreadDefaultZZZ(this, lSleepTime);		
		}
		return this.objThreadKeyPress;
	}

	@Override
	public void setKeyPressThread(IKeyPressThreadMenulessZZZ objKeyPressThread)  throws ExceptionZZZ {
		this.objThreadKeyPress = objKeyPressThread;
	}
	
	
	
	@Override
	public boolean isConsoleUserThreadRunning() {
		return this.bConsoleUserThreadRunning;
	}

	@Override
	public void isConsoleUserThreadRunning(boolean bConsoleUserThreadRunning) {
		this.bConsoleUserThreadRunning = bConsoleUserThreadRunning;
		if(this.bConsoleUserThreadRunning) {
			this.bConsoleUserThreadFinished=false;
		}else {
			this.bConsoleUserThreadFinished=true;
		}
	}
	
	@Override
	public boolean isConsoleUserThreadFinished() {
		return this.bConsoleUserThreadFinished;
	}

	@Override
	public void isConsoleUserThreadFinished(boolean bConsoleUserThreadFinished) {
		this.bConsoleUserThreadFinished = bConsoleUserThreadFinished;
		if(this.bConsoleUserThreadFinished) {
			this.bConsoleUserThreadRunning=false;
		}else {
			this.bConsoleUserThreadRunning=true;
		}
	}
	
	@Override
	public HashMapZZZ<String,Object> getVariableHashMap(){
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapZZZ<String,Object>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariableHashMap(HashMapZZZ<String,Object> hmVariable) {
		this.hmVariable = hmVariable;
	}
}
