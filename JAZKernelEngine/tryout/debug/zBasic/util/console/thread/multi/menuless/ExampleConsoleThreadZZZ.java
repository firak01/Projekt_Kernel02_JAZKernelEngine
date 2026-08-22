package debug.zBasic.util.console.thread.multi.menuless;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.IConsoleServiceZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;

	 
public class ExampleConsoleThreadZZZ implements Runnable,IExampleConsoleZZZ {
	private IConsoleServiceZZZ objConsoleService = null;
	private ExampleKeyPressThreadZZZ objKeyPressThread = null;
	private long lSleepTime = 1000;
	private boolean bStop = false;
	
	Scanner inputReader = new Scanner(System.in);
	
	 public long getSleepTime() {		
     	return this.lSleepTime;
     }
	 public void setSleepTime(long lSleepTime) {
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
 
	public void run() 
    {
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
	                	//objUser.requestStop();
	                	this.requestStop();
	                }else {
	                	 objUser.startit();                     
	                }
                }else {
                	this.requestStop();
                }
            }
           
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExceptionZZZ ez) {
        	ez.printStackTrace();
        }
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
}

