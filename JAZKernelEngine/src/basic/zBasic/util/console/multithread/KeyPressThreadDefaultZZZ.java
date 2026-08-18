package basic.zBasic.util.console.multithread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadDecryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


	 
	public class KeyPressThreadDefaultZZZ extends AbstractKeyPressThreadCommonZZZ {


        //Method that gets called when the object is instantiated
        public KeyPressThreadDefaultZZZ(IConsoleZZZ objConsole, long lSleepTime) {
        	super(objConsole, lSleepTime);
        }
       
// 		@Override
//		public boolean start() throws ExceptionZZZ {
//			boolean bReturn = false;
//        	main:{
//    			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
//    			//       Darum muss man alle Eingaben in dem KeyPressThread erledigen
//	        	System.out.println("Eingaben: [ ] oder Q");
//	            while(!this.isStopped()){
//	            	long lSleepTime = this.getSleepTime();
//	            	long lSleepTimeConsole = this.getConsole().getSleepTime();
//		        	 try {
//		             	System.out.println("Kein warten auf Eingabe. Die ist während des laufenden Threads möglich ...");                 	
//						Thread.sleep(lSleepTime);                 	
//					} catch (InterruptedException e) {
//						System.out.println("KeyPressThread: 1. Wait Error");
//						e.printStackTrace();
//					}
//	
//		        	Scanner inputReader = this.getInputReader();
//	                String input = inputReader.next();
//	                System.out.println("Pressed " + input);
//	                if (input.equals("[")) {
//	                	lSleepTimeConsole+=100;
//	                	this.getConsole().setSleepTime(lSleepTimeConsole);
//	                }
//	                if (input.equals("]")) {
//	                	lSleepTimeConsole-=100;
//	                	this.getConsole().setSleepTime(lSleepTimeConsole);
//	                }
//	                if (input.equalsIgnoreCase("Q")) {
//	                    this.requestStop();
//	                	break; // stop KeyPressThread durch Setzen einer internen Variablen
//	                }
//	                
//	                System.out.println("Nach der Eingabe.");	               					
//	            }//end while
//	            bReturn = true;
//	    	}//end main:
//	    	return bReturn;
//		}

		@Override
		public void makeMenueMain() throws InterruptedException, ExceptionZZZ {
			System.out.println();//Leerzeile zum ggfs. vorherigen Consolentext
			System.out.println("#######################################################################################################");		
			System.out.println("# Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch | A für die Ausgabe der ASCII-Tabelle");
			System.out.println("# Folgende zusätzliche Aktionen:");
			System.out.println("# 1: Erhöhe den Dummy Zähler");
			System.out.println("#####################################################################################################");
			
			Thread.sleep(this.getSleepTime()); 
			System.out.println("Warte auf Eingabe Decrypt...");  
		
			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alle Eingaben in dem KeyPressThread erledigen
//        	System.out.println("Eingaben: [ ] oder Q");
//            while(!this.isStopped()){
//            	long lSleepTime = this.getSleepTime();
//            	long lSleepTimeConsole = this.getConsole().getSleepTime();
//	        	 try {
//	             	System.out.println("Kein warten auf Eingabe. Die ist während des laufenden Threads möglich ...");                 	
//					Thread.sleep(lSleepTime);                 	
//				} catch (InterruptedException e) {
//					System.out.println("KeyPressThread: 1. Wait Error");
//					e.printStackTrace();
//				}
//
//	        	Scanner inputReader = this.getInputReader();
//                String input = inputReader.next();
//                System.out.println("Pressed " + input);
//                if (input.equals("[")) {
//                	lSleepTimeConsole+=100;
//                	this.getConsole().setSleepTime(lSleepTimeConsole);
//                }
//                if (input.equals("]")) {
//                	lSleepTimeConsole-=100;
//                	this.getConsole().setSleepTime(lSleepTimeConsole);
//                }
//                if (input.equalsIgnoreCase("Q")) {
//                    this.requestStop();
//                	break; // stop KeyPressThread durch Setzen einer internen Variablen
//                }
//                
//                System.out.println("Nach der Eingabe.");
		}

		@Override
		public boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
				IKeyPressThreadZZZ objKeyPressThreadUsed = null; //Damit kann man auch andere Thread - Klassen nutzen.
				
				
			//In the JDK 7 release, you can use a String object in the expression of a switch statement:
            //Das keine lowercase Methode oder eine Fallunterscheidung in den CASE eingebaut werden kann, 
            //vorher lowercase
            this.isCurrentMenue(true);
            String input = sInput.toLowerCase();			                
            switch(input) {
            case "+":
            	this.isCurrentInputValid(true);					                	
            	this.setSleepTime(this.getSleepTime()+100);
            	this.getConsole().setSleepTime(this.getSleepTime());			                	
            	break;
            case "-":
            	this.isCurrentInputValid(true);
            	this.setSleepTime(this.getSleepTime()-100);
            	this.getConsole().setSleepTime(this.getSleepTime());			                	
            	break;
            case "q":
            	this.quit();
            	bReturn=false;
            	break main; 
            case "a":
            	this.isCurrentInputValid(true);            	            	
            	this.printTableASCII(hmVariable);//Mache eine einfache Print-Ausgabe der ASCII Tabelle
            	break;
            case "1":
            	this.isCurrentInputValid(true);
            	//this.processROT13_(hmVariable);              	
            	objKeyPressThreadUsed = this;
            	this.setKeyPressThreadUsed(objKeyPressThreadUsed);
            	this.setMethodForThreadUsed("process1");           
            	objKeyPressThreadUsed.initit(hmVariable);             	
            	break;
            default:
            	System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - default Zweig: sInput = '"+sInput+"'");
            	System.out.println("ungueltige Eingabe");
            	this.isCurrentMenue(false);//Neue Eingabe OHNE erneut das Menue aufzubauen.
            	this.isCurrentInputValid(false);					                	
            	break;
            }		 		
		}//end main:
		return bReturn;
		}

		@Override
		public boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn =false ;
			main:{
//Hier ist nichst zusätzliches zu übergeben.
				
//        		//######################################################################
//	        	//### Eingabe des zu verarbeitenden/hier: entschluesslenden Textes
//	        	//Merke: Verschluesselte Beispiele kann man sich mit EncryptConsoleMainZZZ erstellen.
//				
//        		//Merke Fehler abfangen, wie z.B.: Exception in thread "Thread-1" java.lang.IllegalArgumentException: Illegal character 'ß'
//				//Das passiert beim Aufruf der Verschlüsselung selbst.
//	        	System.out.println("Geben Sie den zu entschluesselnden Text als String ein");
//            	String sInput = this.getInputReader().nextLine();
//            	if(hmVariable!=null) hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_TEXT_ENCRYPTED, sInput);
//            	if(StringZZZ.isEmpty(sInput)) {
//            		this.cancelToMenue(hmVariable);
//            	}
				
				bReturn = true;
			}//end main:
			return bReturn;
		}

		@Override
		public boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
				//Die Hier übergebene Methode wird in ... .startit() ausgelesen.
				//Plus alle anderen INPUT - Variablen.
				
				
				String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
				switch(sCallingMethod){
					case "process1":
						process1_(hmVariable);
						break;
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
						throw ez;
				}
				
			}//end main:
			return bReturn;
		}

		//#########################################################################
		private void process1_(HashMapZZZ hm) throws ExceptionZZZ {
			//Hier noch zusätzliche Input Variablen übergebbar.
		}
		
    }

