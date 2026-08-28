package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;


	 
	public class KeyPressThreadDefaultZZZ<T> extends AbstractKeyPressThreadWithMenueZZZ<T> {
		private static final long serialVersionUID = 1605060588538690793L;

		//Method that gets called when the object is instantiated
        public KeyPressThreadDefaultZZZ(IConsoleControllerZZZ objConsole, long lSleepTime) throws ExceptionZZZ {
        	super(objConsole, lSleepTime);
        }
       
		@Override
		public void makeMenuMain() throws ExceptionZZZ {
			System.out.println();//Leerzeile zum ggfs. vorherigen Consolentext
			System.out.println("#######################################################################################################");		
			System.out.println("# Eingaben: + - zur Console-Threadgeschwindigkeit | Q zum Abbruch | M zurueck zum Menue | A für die Ausgabe der ASCII-Tabelle");
			System.out.println("# Folgende zusätzliche Aktionen:");
			System.out.println("# 1: Erhöhe den Dummy Zähler");
			System.out.println("#####################################################################################################");			

			//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
			//       Darum muss man alle Eingaben in dem KeyPressThread erledigen
			try {
				Thread.sleep(this.getSleepTime());
			} catch (InterruptedException e) {	
				System.out.println("KeyPressThread: 1. Wait Error");
				e.printStackTrace();
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} 
			System.out.println("Warte auf Eingabe Default...");  
		}

		@Override
		public boolean processMenuPoint(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ {
			boolean bReturn = true;
			main:{
				//Merke: Man kann keine zweite Scanner Klasse auf den sys.in Stream ansetzen.
				//       Darum muss man alle Eingaben in dem KeyPressThread erledigen
				
				IKeyPressThreadMenuableZZZ objKeyPressThreadUsed = null; //Damit kann man auch andere Thread - Klassen nutzen.
				
				
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
					case "ascii":
						ascii_(hmVariable);
						break;
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

		//########################
		/* (non-Javadoc)
		 * @see basic.zBasic.AbstractObjectWithStatusLocalZZZ#queryOfferStatusLocalCustom()
		 */
		@Override
		public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}
    }

