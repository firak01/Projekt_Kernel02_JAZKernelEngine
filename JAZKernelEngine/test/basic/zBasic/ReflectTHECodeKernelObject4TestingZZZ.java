package basic.zBasic;

import basic.zBasic.reflection.ReflectCodeKernelZZZ;
import basic.zBasic.util.counter.ICounterStrategyZZZ;

/** Klasse, wird in den Testklassen aufgerufen.
 *   Sie ist Ausgangsposition für den Aufruf weiterer Testklassen, in denen dann die zu testenden Reflect-Methoden ausgeführt werden.
 *  
 * @author Fritz Lindhauer, 09.08.2019, 08:43:27
 * 
 */
public class ReflectTHECodeKernelObject4TestingZZZ <T extends IObjectZZZ> extends AbstractObjectReflectableZZZ implements IObjectReflectableImplementerZZZ{
	String[] saCallingStack = null;
	T objInitializedInternal = null;
	
	public ReflectTHECodeKernelObject4TestingZZZ() throws ExceptionZZZ{
		super();
		this.makeReflectableInitialization();
	}
	
	public String[] getCallingStackComputed(){
		return this.saCallingStack;
	}

	private void setCallingStackComputed(String[] sa){
		this.saCallingStack = sa;
	}
		
	//############
	public boolean startAsMain() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSub();
		}//end main:
		return bReturn;
	}
	

	
	public boolean startAsSub() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSubA();
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubA() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			bReturn = this.startAsSubAA();
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubAA() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			String[] saReturn = ReflectCodeKernelZZZ.getCallingStack();
			this.setCallingStackComputed(saReturn);
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubInitialiseObject() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{					
			ReflectTHECodeKernelObject4TestingSubZZZ objTest = new ReflectTHECodeKernelObject4TestingSubZZZ();
			this.setObjectInitialisedInternal((T) objTest);
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubInitialiseObject4ExternalTest() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			ReflectTHECodeKernelObject4TestingExternalSubZZZ objTest = new ReflectTHECodeKernelObject4TestingExternalSubZZZ();
			this.setObjectInitialisedInternal((T) objTest);
		}//end main:
		return bReturn;
	}
	
	public boolean startAsSubInitialiseObject4StackExternalTest() throws ExceptionZZZ{
		//Dummy Funktion zum Aufruf von Tests
		boolean bReturn = false;
		main:{
			ReflectTHECodeKernelObject4TestingExternalSubZZZ objTest = new ReflectTHECodeKernelObject4TestingExternalSubZZZ();
			this.setObjectInitialisedInternal((T) objTest);
		}//end main:
		return bReturn;
	}
	
	public void setObjectInitialisedInternal(T obj){
		this.objInitializedInternal = obj;
	}
	public T getObjectInitialisedInternal(){
		return this.objInitializedInternal;
	}
	
	@Override
	public boolean makeReflectableInitialization() throws ExceptionZZZ {
		return this.initClassMethodCallingString();
	}
}
