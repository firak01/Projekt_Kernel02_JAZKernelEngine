package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;

public class ExampleMenuPoint_MinusZZZ extends AbstractMenuPointZZZ{
	public ExampleMenuPoint_MinusZZZ() throws ExceptionZZZ {
		super();
	}

	public ExampleMenuPoint_MinusZZZ(HashMap<String,String> hmVariableInit) throws ExceptionZZZ {
		super(hmVariableInit);
	}

	@Override
	public boolean initit() throws ExceptionZZZ {
		return false;
	}

	@Override
	public boolean onStartit() throws ExceptionZZZ {
    	
    	//this.setSleepTime(this.getSleepTime()-100);
    	//this.getConsoleController().setSleepTime(this.getSleepTime());
		
		return false;
	}
}
