package debug.zBasic.util.console.thread.multi.menu;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadWithMenueZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadConstantZZZ;
import basic.zBasic.util.console.thread.IKeyPressThreadMenueableZZZ;
import basic.zBasic.util.counter.ICounterByCharacterAsciiFactoryZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ;
import basic.zBasic.util.crypt.thread.KeyPressThreadDecryptZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;


	 
	public class ExampleKeyPressThreadZZZ extends AbstractKeyPressThreadWithMenueZZZ {


        //Method that gets called when the object is instantiated
        public ExampleKeyPressThreadZZZ(IConsoleControllerZZZ objConsole, long lSleepTime) throws ExceptionZZZ {
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
			System.out.println("# Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch | M zurueck zum Menue | A für die Ausgabe der ASCII-Tabelle");
			System.out.println("# Folgende zusätzliche Aktionen:");
			System.out.println("# 1: Erhöhe den Dummy Zähler");
			System.out.println("# 2: Erhöhe eine Alphanumeric Zähler");
			System.out.println("#####################################################################################################");
			
			Thread.sleep(this.getSleepTime()); 
			System.out.println("Warte auf Eingabe Default...");  
		
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
				IKeyPressThreadMenueableZZZ objKeyPressThreadUsed = null; //Damit kann man auch andere Thread - Klassen nutzen.
				
				
			//In the JDK 7 release, you can use a String object in the expression of a switch statement:
            //Das keine lowercase Methode oder eine Fallunterscheidung in den CASE eingebaut werden kann, 
            //vorher lowercase
            this.isCurrentMenue(true);
            String input = sInput.toLowerCase();			                
            switch(input) {
            case "+":
            	this.isCurrentInputValid(true);					                	
            	this.setSleepTime(this.getSleepTime()+100);
            	this.getConsoleController().setSleepTime(this.getSleepTime());			                	
            	break;
            case "-":
            	this.isCurrentInputValid(true);
            	this.setSleepTime(this.getSleepTime()-100);
            	this.getConsoleController().setSleepTime(this.getSleepTime());			                	
            	break;
            case "q":
            	this.quit();
            	bReturn=false;
            	break main; 
            case "m":
            	bReturn = true;
            	break main; //Das Menü ist ja schon da...
            case "a":
            	this.isCurrentInputValid(true);            	            	
            	//this.printTableASCII(hmVariable);//Mache eine einfache Print-Ausgabe der ASCII Tabelle
            	objKeyPressThreadUsed = this;
            	this.setKeyPressThread(objKeyPressThreadUsed);
            	this.setMethodForConsoleService("ascii");           
            	objKeyPressThreadUsed.initit(hmVariable);             	
            	break;
            case "1":
            	this.isCurrentInputValid(true);
            	//this.processROT13_(hmVariable);              	
            	objKeyPressThreadUsed = this;
            	this.setKeyPressThread(objKeyPressThreadUsed);
            	this.setMethodForConsoleService("process1");           
            	objKeyPressThreadUsed.initit(hmVariable);             	
            	break;
            case "2":
            	this.isCurrentInputValid(true);            	          
            	objKeyPressThreadUsed = this;
            	this.setKeyPressThread(objKeyPressThreadUsed);
            	this.setMethodForConsoleService("processCountAlphanumeric");           
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
			boolean bReturn = false;
			main:{
				//Die Hier übergebene Methode wird in ... .startit() ausgelesen.
				//Plus alle anderen INPUT - Variablen.
				
				
				String sCallingMethod= (String) hmVariable.get(IKeyPressThreadConstantZZZ.sINPUT_STRING_METHOD_USED);
				switch(sCallingMethod){
					case "ascii":
						bReturn = ascii_(hmVariable);
						break;
					case "process1":
						bReturn = process1_(hmVariable);
						break;
					case "processCountAlphanumeric":
						bReturn = processCountAlphanumeric_(hmVariable);
						break;
					default:
						ExceptionZZZ ez = new ExceptionZZZ("Nicht behandelte Methode: '" + sCallingMethod + "'", iERROR_PROPERTY_VALUE, this.getClass(), ReflectCodeZZZ.getPositionCurrent());
						throw ez;
				}
				//bReturn = true;
			}//end main:
			return bReturn;
		}

		//#########################################################################
		private boolean process1_(HashMapZZZ hmVariable) throws ExceptionZZZ {
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
		
		private boolean ascii_(HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				//Hier noch zusätzliche Input Variablen übergebbar.
				bReturn = true;
			}//end main;	
			return bReturn;
		}
		
		private boolean processCountAlphanumeric_(HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				if(hmVariable!=null) {				
					//Beispiel mit Verschlüsselung: 
					//Hier werden die Keys für die Variable als Konstante möglich, da sie ihren eigenen KeyPressThread haben
	        		//String sCipher = CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROT13.getAbbreviation();
	        		//hmVariable.put(KeyPressThreadDecryptZZZ.sINPUT_CIPHER, sCipher);
					
					//Die Verschiedenen alphanumerischen Zähler haben neben ihrem Namen auch eine "Typenzahl"					
					int iAlphanumericType = ICounterByCharacterAsciiFactoryZZZ.iCounter_TYPE_ALPHANUMERIC_SIGNIFICANT;
	        		String sAlphanumericType = Integer.toString(iAlphanumericType);
	        		hmVariable.put("INPUT_COUNTER_TYPE", sAlphanumericType);
	        	}
				bReturn = true;
			}//end main;	
			return bReturn;
		}
		
    }

