package basic.zBasic.util.console.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.abstractList.MapUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import debug.zBasic.util.console.thread.multi.menu02.AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ;
import debug.zBasic.util.console.thread.multi.menu02.AbstractThreadWithStatusLocalZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IConsoleControllerEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
//public abstract class AbstractConsoleControllerZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IConsoleControllerZZZ {
public abstract class AbstractConsoleControllerZZZ<T> extends AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> implements IConsoleControllerZZZ, IConsoleControlableZZZ, IConsoleControllerEnabledZZZ {
	private static final long serialVersionUID = 303154337707751073L;

	protected volatile static IConsoleControllerZZZ objConsole = null;  //muss static sein, wg. getInstance()!!!
	
	private IKeyPressThreadZZZ objThreadKeyPress=null;
	private IConsoleServiceZZZ objConsoleUserStarter = null;
	private IMenuPointZZZ      objMenuPoint = null;
	
	//Variablen zur Steuerung des internen Threads
	private volatile static boolean bInputFinished=false;
	private volatile static boolean bOutputFinished=false;
	private volatile static boolean bInputThreadFinished = false;
	private volatile static boolean bInputThreadRunning = false;	
	private volatile static boolean bConsoleUserThreadFinished = false;
	private volatile static boolean bConsoleUserThreadRunning = false;
	
	//Zur dynmischen Verwaltung von globalen Variablen, die in einem Thread für den anderen Thread gedacht sind
	//To ensure that updates to variables propagate predictably to other threads, we should apply the volatile modifier to those variables:
	private volatile HashMapZZZ<String,Object> hmVariable = null;

	
	
	/**Konstruktor ist private, wg. Singleton
	 */
	protected AbstractConsoleControllerZZZ() throws ExceptionZZZ {		
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
	
	//### GETTER / SETTER #######

	@Override
	public IConsoleServiceZZZ getConsoleServiceObject() {
		return this.objConsoleUserStarter;
	}

	@Override
	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleUser) {
		this.objConsoleUserStarter = objConsoleUser;
	}

	@Override
	public IKeyPressThreadZZZ getKeyPressThread() throws ExceptionZZZ {
		if(this.objThreadKeyPress==null) {
			long lSleepTime = this.getSleepTime();
			this.objThreadKeyPress = new KeyPressThreadDefaultZZZ(this, lSleepTime);		
		}
		return this.objThreadKeyPress;
	}

	@Override
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread)  throws ExceptionZZZ {
		this.objThreadKeyPress = objKeyPressThread;
	}
	
	@Override
	public IMenuPointZZZ getMenuPoint() throws ExceptionZZZ {
		return this.objMenuPoint;
	}

	@Override
	public void setMenuPoint(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
		this.objMenuPoint = objMenuPoint;
	}	
	
	
	//### aus IThreadEnabledZZZ
	/* (non-Javadoc)
	 * @see basic.zBasic.util.console.thread.IThreadEnabledZZZ#start()
	 */
	@Override
	public boolean start() {
		boolean bReturn = false;
		main:{			
	        try {
	        	this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTARTING, true);

	        	
	        	final IKeyPressThreadZZZ objThreadKeyPress = this.getKeyPressThread();
	        	if(objThreadKeyPress!=null) {
	        		Thread t1 = new Thread((Runnable) objThreadKeyPress);
	        		t1.start();
	        	}else {
	        		ExceptionZZZ ez = new ExceptionZZZ("No KeyPressThread provided", iERROR_PROPERTY_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
	        	}
	        	this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTARTED, true);
	        	this.setStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTARTING, false);

	        	
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
	public HashMapZZZ<String,Object> getVariableHashMap() throws ExceptionZZZ {
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapZZZ<String,Object>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ{
		this.hmVariable = hmVariable;
	}
	
	
	@Override
	public void addVariableHashMap(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		HashMapZZZ<String,Object> hmOld = this.hmVariable;
		HashMap<String, Object> hmTemp = HashMapUtilZZZ.mergeMaps_LastKeyRemains(hmOld, hmVariable);
		this.hmVariable = MapUtilZZZ.toHashMapZZZ(hmTemp);
	}
	
	
	//### aus IConsoleControlableZZZ
	
	@Override
	public boolean isQuitted() throws ExceptionZZZ {		
		return this.getStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED);
	}
	
	@Override
	public void isQuitted(boolean bStop) throws ExceptionZZZ {		
		this.requestQuit();
	}
	
	@Override
	public void requestQuit() throws ExceptionZZZ {		
		//Das wirft an registrierte Objekte einen Event: .offerStatusLocal(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED,true);
		this.setStatusLocal(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED, true);
	}
	
	//##### Auf Events hören, ist jetzt um QUIT ergänzt
	@Override
	public boolean reactOnStatusLocalEvent(IEventObjectStatusLocalZZZ eventStatusLocal) throws ExceptionZZZ {		
		boolean bReturn = false;
		main:{
			//TODOGOON ; FALLUNTERSCHEIDUNG.
			if(eventStatusLocal==null)break main;
			
			IEnumSetMappedStatusLocalZZZ objStatus = eventStatusLocal.getStatusLocal();
			if(objStatus.equals(IThreadWithStatusLocalEnabledZZZ.STATUSLOCAL.ISSTOPPED)) {
				
				this.requestStop();
				
			}else if(objStatus.equals(IConsoleControllerEnabledZZZ.STATUSLOCAL.ISQUITTED)) {
				
				this.requestQuit();
			}
			
			bReturn = true;
		}//end main:
		return bReturn;

	}
	
	//###################################################
	//### FLAG HANDLING
	//###################################################
	
	//### aus IConsoleControllerEnabledZZZ
	@Override
	public boolean getFlag(IConsoleControllerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IConsoleControllerEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IConsoleControllerEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IConsoleControllerEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IConsoleControllerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IConsoleControllerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	
	//###################################
	//### FLAG CUSTOM Handling
	/* ES GIBT HIER KEIN FLAGCUSTOM
	@Override
	public boolean getFlagCustom(IAbstractThreadWithStatusLocalEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());
	}

	@Override
	public boolean setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}
	*/

	

	//###################################
	//### FLAGLOCAL Handling

	/* ES GIBT HIER KEIN FLAGLOCAL
	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	*/
}
