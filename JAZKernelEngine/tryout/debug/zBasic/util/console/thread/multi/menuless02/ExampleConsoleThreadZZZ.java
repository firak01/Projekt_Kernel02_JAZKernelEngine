package debug.zBasic.util.console.thread.multi.menuless02;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.IThreadableZZZ;

	 
public class ExampleConsoleThreadZZZ implements Runnable,IThreadableZZZ, IExampleConsoleZZZ {
	private IConsoleServiceZZZ objConsoleService = null;
	private ExampleKeyPressThreadZZZ objKeyPressThread = null;
	
	
	public static long lSLEEPTIME_DEFAULT = 1000;	
	protected long lSleepTime = -1;
	
	private boolean bStop = false;
	
	Scanner inputReader = new Scanner(System.in);
	
	//### GETTER / SETTER
	@Override
	public IConsoleServiceZZZ getConsoleServiceObject() {
		return this.objConsoleService;
	}
	@Override
	public void setConsoleServiceObject(IConsoleServiceZZZ objConsoleService) {
		this.objConsoleService = objConsoleService;
	}
	
	
	@Override
	public ExampleKeyPressThreadZZZ getKeyPressThread() throws ExceptionZZZ{
		return this.objKeyPressThread;
	}		

	@Override
	public void setKeyPressThread(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
		this.objKeyPressThread = (ExampleKeyPressThreadZZZ) objKeyPressThread;
	}
	
	//### aus Runnable
	public void run() 
    {
    	try {
			this.start();
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
		}
    }
	
	//### aus IThreadEnabledZZZ
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
	
    
    //Method that gets called when the object is instantiated
    public ExampleConsoleThreadZZZ(long lSleepTime, ExampleKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ {
    	this.setSleepTime(lSleepTime);
    	this.setKeyPressThread(objKeyPressThread);
    }
 
   
	
	@Override
	public boolean start() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			try {    
	           while(!this.isStopped()) {
	                long lSleepTime = this.getKeyPressThread().getSleepTime();
	                this.setSleepTime(lSleepTime);
	                lSleepTime = this.getSleepTime();
	                System.out.println("ConsoleThread.sleep: " + lSleepTime);
	                Thread.sleep(lSleepTime);
	                
	                IConsoleServiceZZZ objUser = this.getConsoleServiceObject();
	                if(objUser!=null) {
	                	boolean bStop = this.getKeyPressThread().isStopped(); 
		                if(bStop) {
		                	//Merke: ConsoleService muss kein Thread sein... dann kann man ihn auch nicht stoppen 
		                	//objUser.requestStop();
		                	
		                	//Diese Klasse selbst ist aber ein Thread, den kann man versuchen zu stoppen.
		                	this.requestStop(true);
		                }else {
		                	//Methode ist gleich benannt, auch wenn ConsoleService kein Thread ist funktioniert es so.
		                	objUser.startit();                     
		                }
	                }else {
	                	this.requestStop(true);
	                }
	            }
	           
	            bReturn = true;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        } catch (ExceptionZZZ ez) {
	        	ez.printStackTrace();
	        }
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++
	 @Override
	public boolean stop() throws ExceptionZZZ {
		return this.requestStop(true);
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
	public boolean requestStop(boolean bStop) {
		this.isStopped(bStop);
		return true;
	}
	
	//++++++++++++++++++++++++++++++++++++
	@Override
	public boolean pause() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean requestPause(boolean bPause) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPaused() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void isPaused(boolean bPause) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	
	//++++++++++++++++++++++++++++++++++++++++
	@Override
	public boolean finish() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean requestFinish(boolean bFinish) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isFinished() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void isFinished(boolean bFinish) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
}

