package debug.zBasic.util.console.thread.multi.menuless02;

import java.util.Scanner;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadMenulessZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;


public class ExampleKeyPressThreadZZZ<T> extends AbstractKeyPressThreadMenulessZZZ<T> {
	private static final long serialVersionUID = -5471924402325569415L;
		
	private boolean bStop = false;

    //Method that gets called when the object is instantiated
    public ExampleKeyPressThreadZZZ() throws ExceptionZZZ {
    	super();
    }
    
    public ExampleKeyPressThreadZZZ(long lSleepTime) throws ExceptionZZZ {
    	super(lSleepTime);
    	setSleepTime(lSleepTime);
    }
   
    @Override
    public boolean start() throws ExceptionZZZ {
    	boolean bReturn = false;
    	main:{
    		//irgendwie auch ohne menuepoint die Variablen HashMap initialisieren
    		HashMapZZZ hmVariable = this.getVariableHashMap();
    		initit(hmVariable);
    		
	    	System.out.println("Eingaben: + - oder Q");
	        while(true){
		    	 try {
		         	System.out.println("Warte auf Eingabe...");                 	
					Thread.sleep(500);                 	
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: 1. Wait Error");
					e.printStackTrace();
				}
		
		    	 String input = inputReader.next();
		         //System.out.println(input);
		         if (input.equals("-")) { //also Minus mach den Thread langsamer... lSleeptime erhöhen
		         	//System.out.println("Pressed -");
		        	long lSleepTime = this.getSleepTime();
		        	lSleepTime+=100; 
		         	try {
						this.setSleepTime(lSleepTime);
					} catch (ExceptionZZZ e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}               
		         }
		         if (input.equals("+")) {  //also Plus mach den Thread schneller... lSleeptime verringern
		         	//System.out.println("Pressed +");
		        	long lSleepTime = this.getSleepTime();
		         	lSleepTime-=100; 
		         	this.setSleepTime(lSleepTime);    
		         }
		         if (input.equalsIgnoreCase("Q")) {
		            this.requestStop();
		         	break; // stop KeyPressThread
		         }
	
	            try {
	            	System.out.println("Nach der Eingabe.");
	            	Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("KeyPressThread: 2. Wait Error");
					e.printStackTrace();
				}
	        }
	        bReturn = true;
    	}//end main:
    	return bReturn;
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
	public boolean initit(HashMapZZZ<String,Object> hmVariableExternal) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Die Hier übergebene Methode wird in ... .startit() ausgelesen.
			//Plus alle anderen INPUT - Variablen.
			HashMapZZZ<String, Object> hmVariableInternal = this.getVariableHashMap();							   
			HashMapZZZ<String, Object> hmVariable = HashMapUtilZZZ.mergeMapsZZZ_LastKeyRemains(hmVariableInternal, hmVariableExternal);

			String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
			switch(sCallingMethod){
//				case "ascii":
//					bReturn = initAscii_(hmVariable);
//					break;
				case "process1":
					bReturn = initProcess1_(hmVariable);
					break;
//				case "countAlphanumeric":
//					bReturn = initCountAlphanumeric_(hmVariable);
//					break;
				default:
					ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
					throw ez;
			}
			//bReturn = true;
		}//end main:
		return bReturn;
	}

	//#########################################################################
	private boolean initProcess1_(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Hier noch zusätzliche Input Variablen übergebbar.
			//Beispiel:
			//if(hmVariable!=null) {
        	//	String sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation();
        	//	hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CIPHER, sCipher);
        	//}				
			bReturn = true;
		}//end main;	
		return bReturn;
	}
	
	
	/* (non-Javadoc)
	 * @see basic.zBasic.AbstractObjectWithStatusLocalZZZ#queryOfferStatusLocalCustom()
	 */
	@Override
	public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
		return true;
	}
	
	
}

