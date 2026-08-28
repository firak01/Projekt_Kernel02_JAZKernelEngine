package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.console.thread.ConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadZZZ;

public class ExampleMenuPoint_minusZZZ extends AbstractMenuPointZZZ{
	public ExampleMenuPoint_minusZZZ() throws ExceptionZZZ {
		super();
	}

	public ExampleMenuPoint_minusZZZ(HashMap<String,String> hmVariableInit) throws ExceptionZZZ {
		super(hmVariableInit);
	}

	@Override
	public boolean initit() throws ExceptionZZZ {
		return false;
	}

	@Override
	public boolean onStartit() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
	    	//Der Menüpunkt braucht Zugriff auf die übergeordnete Konsole.
			//Gut das die per Singleton erreichbar ist.
	    	IConsoleControllerZZZ objConsoleController = ConsoleControllerZZZ.getInstance();
	    	IKeyPressThreadZZZ objKeyPressThread = objConsoleController.getKeyPressThread();
	    	long lSleepTime = objKeyPressThread.getSleepTime();
	    	
	    	lSleepTime = lSleepTime - 100;
	    	objKeyPressThread.setSleepTime(lSleepTime);
	    	objConsoleController.setSleepTime(lSleepTime);
	    	bReturn = true;
		}//end main
		return bReturn;
	}
}
