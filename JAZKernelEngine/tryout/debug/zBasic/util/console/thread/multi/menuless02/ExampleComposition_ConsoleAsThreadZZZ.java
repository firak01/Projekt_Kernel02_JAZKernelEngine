package debug.zBasic.util.console.thread.multi.menuless02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithStatusEnabledZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenuableZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenulessZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;


/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 *  Weiterentwicklung von debug.zBasic.util.console.thread.multi.menuless02...
 *  
 *  Als Weiterentwickung
 *  -- ist diese Klasse selbst "runnable", d.h. wird als Thread ausgeführt
 *     Also ExampleComposition_ConsoleZZZ wird zu ExampleComposition_ConsoleAsThreadZZZ
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
//Baue das so auf wie:
//package debug.zBasic.util.console.thread.multi.menu03;
//                   ExampleComposition_ConsoleAsThreadZZZ
/**
 * @author Fritz Lindhauer
 *
 * @param <T>
 */
//public class ExampleComposition_ConsoleZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IExampleConsoleZZZ {
public class ExampleComposition_ConsoleAsThreadZZZ<T> implements Runnable, IConsoleControllerUserZZZ { //extends AbstractObjectWithFlagZZZ<T> implements IExampleConsoleZZZ {
	private static final long serialVersionUID = 2937832009910133403L;

//	//SINGLETON
//	private static ExampleComposition_ConsoleZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
//	
//	private IKeyPressThreadZZZ objThreadKeyPress=null;
//	private IConsoleServiceZZZ objConsoleService = null;
//	private ExampleConsoleThreadZZZ objThreadConsole = null;
//	
//	//Variablen zur Steuerung des internen Threads
//	private long lSleepTime=1000;
//	private boolean bStop = false;
//	
//	/**Konstruktor ist private, wg. Singleton
//	 */
//	private ExampleComposition_ConsoleZZZ() {		
//		super();
//		ConsoleMain_();
//	}
//	
//	public static ExampleComposition_ConsoleZZZ getInstance(){
//		if(objConsole==null){
//			objConsole = new ExampleComposition_ConsoleZZZ();
//		}
//		return objConsole;		
//	}
//	
//	private boolean ConsoleMain_() {
//		boolean bReturn = false;
//		main:{
//			
//			bReturn = true;
//		}//end main:
//		return bReturn;
//	}
	
	protected IConsoleControllerZZZ objConsoleController = null;
	
	public ExampleComposition_ConsoleAsThreadZZZ() throws ExceptionZZZ{		
	}
	
	public ExampleComposition_ConsoleAsThreadZZZ(String[] args) throws ExceptionZZZ{		
	}
	
	@Override
	public IConsoleControllerZZZ getConsoleController() throws ExceptionZZZ {
		return this.objConsoleController;
	}
	
	@Override
	public void setConsoleController(IConsoleControllerZZZ objConsoleController) throws ExceptionZZZ {
		this.objConsoleController = objConsoleController;
	}
				
	@Override
	public void run() {
		try {
			start();
		} catch (ExceptionZZZ ez) {
			System.out.println(ez.getMessageLast());
			ez.printStackTrace();
		}
	}
	
	
	
	//@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		try {
		main:{
			this.startit();
			
			this.getConsoleController().isConsoleUserThreadRunning(true);							
			do {
				 try {				 
					 Thread.sleep(200);
					 //System.out.println("ExampleComposition_ConsoleAsThreadZZZ wartet auf fertige Konsoleneingabe");
				} catch (InterruptedException e) {
					System.out.println("02 Menuless ExampleComposition_ConsoleAsThreadZZZ: Wait Error");
					e.printStackTrace();
					ExceptionZZZ ez = new ExceptionZZZ(e);
					throw ez;
				}
			}while(!this.getConsoleController().isStopped());
		}//end main:
		}catch(ExceptionZZZ ez) {
			ez.printStackTrace();
		}
		this.getConsoleController().isConsoleUserThreadFinished(true);
		return bReturn;
	}
	
	public void startit() throws ExceptionZZZ {								
		IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();	
		objConsoleController.setFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED, true);
		this.setConsoleController(objConsoleController);
		
		//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
		//       Darum muss man alles in dem KeyPressThread erledigen
		//IKeyPressThreadMenuableZZZ objKeyPressThread = new ExampleKeyPressThreadZZZ(objConsoleController, 100);
		
		//Hier haben wir aber Menuless, darum Weiternutzen analog zu ...Menuless01...
		IKeyPressThreadMenulessZZZ objKeyPressThread = new ExampleKeyPressThreadZZZ();
		//Der Key-Thread hat keinen Status, also nicht: objKeyPressThread.setFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED, true);
		objConsoleController.setKeyPressThread(objKeyPressThread);
		//Nein, keypressthread ist selber kein Listener: objConsoleController.registerForStatusLocalEvent(objKeyPressThread);
		//Der KeyPressThread muss aber den ConsoleController kennen und der ConsoleController muss darauf hören
		objKeyPressThread.setConsoleController(objConsoleController);
		
		
		IConsoleServiceZZZ objConsoleService = new ExampleConsoleServiceZZZ(objConsoleController);	
		//objConsoleService.setFlag(IObjectWithStatusEnabledZZZ.FLAGZ.STATUSLOCAL_PROOF_VALUECHANGED, true);
		objConsoleController.setConsoleServiceObject(objConsoleService);
		objConsoleController.registerForStatusLocalEvent(objConsoleService);
		
		
		//Merke: Beim Ausführen von Aktionen 	erstellt der ExampleConsoleService ggfs. neue Threads, die dann am ConsoleController registriert werden.
		//       Dann soll beim Beenden des ConsoleControllers ein entsprechendes Ereignis an alle registrierten Threads geworfen werden.
		objConsoleController.start(); 
		
	}
	
//	public boolean start() throws ExceptionZZZ{
//		boolean bReturn = false;
//		main:{			
//	        try {
//	        	IConsoleServiceZZZ objConsoleService = new ExampleConsoleServiceZZZ();
//	        	this.setConsoleServiceObject(objConsoleService);
//	        	
//	        	final ExampleKeyPressThreadZZZ objThreadKeyPress = (ExampleKeyPressThreadZZZ) this.getKeyPressThread();
//	            Thread t1 = new Thread(objThreadKeyPress);
//	            t1.start();
//
//	            final ExampleConsoleThreadZZZ objThreadConsole = this.getConsoleThread();	          
//		        Thread t2 = new Thread(objThreadConsole);
//		        t2.start();
//	         
//	        } catch (Exception e)        {
//	            ExceptionZZZ ez = new ExceptionZZZ(e);
//	            throw ez;
//	        }
//			
//		}//end main:
//		return bReturn;		
//	}
//	
//	public boolean isStopped() {
//		return this.bStop;
//	}
//	public void isStopped(boolean bStop) {
//		this.bStop = bStop;
//	}
//	public void requestStop() {
//		this.isStopped(true);
//	}
//	
//	public long getConsoleSleepTime() {
//		return this.lSleepTime;
//	}
//	public void setConsoleSleepTime(long lSleepTime) {
//		this.lSleepTime = lSleepTime;
//	}
//
//	@Override
//	public IConsoleServiceZZZ getConsoleServiceObject() {
//		return this.objConsoleService;
//	}
//
//	@Override
//	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleService) {
//		this.objConsoleService = objConsoleService;
//	}
//
//	@Override
//	public IKeyPressThreadZZZ getKeyPressThread() throws ExceptionZZZ {
//		if(this.objThreadKeyPress==null) {
//			long lSleepTime = this.getConsoleSleepTime();
//			this.objThreadKeyPress = new ExampleKeyPressThreadZZZ(lSleepTime);		
//		}
//		return this.objThreadKeyPress;
//	}
//
//	@Override
//	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
//		this.objThreadKeyPress = objKeyPressThread;
//	}
//	
//	
//	
//	public ExampleConsoleThreadZZZ getConsoleThread() throws ExceptionZZZ {
//		if(this.objThreadConsole==null) {
//			ExampleKeyPressThreadZZZ objKeyPressThread = (ExampleKeyPressThreadZZZ) this.getKeyPressThread();
//			if(objKeyPressThread!=null) {
//			
//				IConsoleServiceZZZ objConsoleUser = this.getConsoleServiceObject();
//				if(objConsoleUser!=null) {
//					long lSleepTime = this.getConsoleSleepTime();
//					this.objThreadConsole = new ExampleConsoleThreadZZZ(lSleepTime, objKeyPressThread);
//			        this.objThreadConsole.setConsoleServiceObject(this.getConsoleServiceObject());
//				}
//			}
//		}
//		return this.objThreadConsole;    
//	}
//	
//	public void setConsoleThread(ExampleConsoleThreadZZZ objThreadConsole) {
//		this.objThreadConsole = objThreadConsole;
//	}
}
