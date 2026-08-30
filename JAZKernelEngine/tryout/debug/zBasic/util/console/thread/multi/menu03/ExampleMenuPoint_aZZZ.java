package debug.zBasic.util.console.thread.multi.menu03;

import java.util.HashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.KeyPressThreadUtilZZZ;

public class ExampleMenuPoint_aZZZ extends AbstractMenuPointZZZ {
	public ExampleMenuPoint_aZZZ() throws ExceptionZZZ {
		super();
	}

	public ExampleMenuPoint_aZZZ(HashMapZZZ<String,Object> hmVariableInit) throws ExceptionZZZ {
		super(hmVariableInit);
	}

	@Override
	public boolean onStartit() throws ExceptionZZZ {
		//Aus ehemals ExampleKeyPressThread
		//this.printTableASCII(hmVariable);//Mache eine einfache Print-Ausgabe der ASCII Tabelle
    	//this.setMethodForConsoleService("ascii");           
    	//objKeyPressThreadUsed.initit(hmVariable);
    	
    	//Die Lösung, dass der Menüpunkt selbst seinen auszuführenden Code hat ist besser.
    	boolean bReturn = false;
		main:{
			KeyPressThreadUtilZZZ.printTableAscii();
			bReturn = true;
		}//end main:
		return bReturn;
    	
	}

	@Override
	public boolean initit(HashMapZZZ<String, Object> hmVariableExternal) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}
}
