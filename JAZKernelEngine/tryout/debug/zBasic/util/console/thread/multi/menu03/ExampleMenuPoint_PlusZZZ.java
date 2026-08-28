package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public class ExampleMenuPoint_PlusZZZ extends AbstractMenuPointZZZ {
	public ExampleMenuPoint_PlusZZZ() throws ExceptionZZZ {
		super();
	}

	public ExampleMenuPoint_PlusZZZ(HashMap<String,String> hmVariableInit) throws ExceptionZZZ {
		super(hmVariableInit);
	}

	@Override
	public boolean initit() throws ExceptionZZZ {		
		return false;
	}
	
	@Override
	public boolean onStartit() throws ExceptionZZZ {
		
		//this.setSleepTime(this.getSleepTime()+100);
    	//this.getConsoleController().setSleepTime(this.getSleepTime());
		
		return false;
	}

	
}
