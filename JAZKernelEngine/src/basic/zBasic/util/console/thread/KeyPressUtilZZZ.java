package basic.zBasic.util.console.thread;

import java.util.ArrayList;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KeyPressUtilZZZ implements IKeyPressConstantZZZ, IConstantZZZ{
	
	public static String computeKeyTag(char cKey) {
		String sReturn = null;
		main:{
			if(CharZZZ.isEmpty(cKey)) {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + IKeyPressConstantZZZ.sKeyTagClose;
			}else {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + cKey + IKeyPressConstantZZZ.sKeyTagClose;
			}
		}//end main:
		return sReturn;
	}
	public static String computeKeyTag(String sKeyText) throws ExceptionZZZ {
		return computeKeyTag(sKeyText, true);
	}
	
	public static String computeKeyTag(String sKeyText, boolean bForLine) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(bForLine) {
				sReturn = computeKeyTag_forLine_(sKeyText);
			}else {
				sReturn = computeKeyTag_forBlock_(sKeyText);
			}
		}//end main:
		return sReturn;
	}
	private static String computeKeyTag_forLine_(String sKeyText) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKeyText)) {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + IKeyPressConstantZZZ.sKeyTagClose;
			}else {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + sKeyText + IKeyPressConstantZZZ.sKeyTagClose;
			}
		}//end main:
		return sReturn;
	}
	private static String computeKeyTag_forBlock_(String sKeyText) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = computeKeyTag_forLine_(sKeyText); 
			sReturn = sReturn + StringZZZ.repeat(" ", 20);
			sReturn = StringZZZ.left(sReturn,20);
		}//end main:
		return sReturn;
	}
	
	
	//+++++++++++++++++++++++++++++++++
	public static String computeKeyTagAsDefault(char cKey) {
		String sReturn = null;
		main:{
			if(CharZZZ.isEmpty(cKey)) {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + IKeyPressConstantZZZ.sKeyTagClose + "*default";
			}else {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + cKey + IKeyPressConstantZZZ.sKeyTagClose + "*default";
			}
		}//end main:
		return sReturn;
	}
	
	public static String computeKeyTagAsDefault(String sKeyText) throws ExceptionZZZ {
		return computeKeyTagAsDefault(sKeyText, true);
	}
	
	public static String computeKeyTagAsDefault(String sKeyText, boolean bForLine) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(bForLine) {
				sReturn = computeKeyTagAsDefault_forLine_(sKeyText);
			}else {
				sReturn = computeKeyTagAsDefault_forBlock_(sKeyText);
			}
		}//end main:
		return sReturn;
	}
	
	private static String computeKeyTagAsDefault_forLine_(String sKeyText) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sKeyText)) {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + IKeyPressConstantZZZ.sKeyTagClose + "*default";
			}else {
				sReturn = IKeyPressConstantZZZ.sKeyTagOpen + sKeyText + IKeyPressConstantZZZ.sKeyTagClose + "*default";
			}
		}//end main:
		return sReturn;
	}
	private static String computeKeyTagAsDefault_forBlock_(String sKeyText) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = computeKeyTagAsDefault_forLine_(sKeyText); 
			sReturn = sReturn + StringZZZ.repeat(" ", 20);
			sReturn = StringZZZ.left(sReturn,20);
		}//end main:
		return sReturn;
	}
	//++++++++++++++++++++++++++++++++++++
	
	public static String computeKeyTagStringInputAlphabetCancel() throws ExceptionZZZ {
		String sReturn = KeyPressUtilZZZ.computeKeyTag("Buchstaben des Alphabets") + "/";
		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
		return sReturn;
	}
	
	public static String computeKeyTagStringInputNumericCancel() throws ExceptionZZZ {
		String sReturn = KeyPressUtilZZZ.computeKeyTag("Ganzzahlen") + "/";
		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
		return sReturn;
	}
	
	public static String makeInputAlphabetCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(inputReader==null){
				String stemp = "'Scanner as InputReader'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sQuestion = StringZZZ.trim(sQuestionIn);
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
											
			KeyPressUtilZZZ.printlnInputAlphabetCancel(sQuestion);
			
			boolean bGoon=false; String sInput = null;
			do {
			   sInput = inputReader.nextLine();	                
               if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
            		//System.out.println("Abbruch eingegeben");
            		bGoon = true;
               }else if(StringZZZ.isEmpty(sInput)) {
            	   System.out.println("ungueltige Eingabe");
            	   bGoon = false;
            	}else if(StringZZZ.isAlphabet(sInput)){            		
            		bGoon = true;
            	}else {  
            		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
            		System.out.println("Hier ungueltige Eingabe.");			                		
                	bGoon=false;				                	
            	}				
			}while(!bGoon);
			sReturn = sInput;
		}//end main:
		return sReturn;
	}
	
	public static String makeMenuInputNumeric(Scanner inputReader, String sQuestionIn, ArrayList<IKeyPressCharZZZ> menuItems, boolean bAsLine) throws ExceptionZZZ{		
		main:{
			if(inputReader==null){
				String stemp = "'Scanner as InputReader'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sQuestion = StringZZZ.trim(sQuestionIn);
			if(sQuestion.endsWith("?")) {
				sQuestion = StringZZZ.stripRight(sQuestion, "?");
			}
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 if(bAsLine) {
			    	printlnMenuNumeric(sQuestion, menuItems);
			    }else {
			    	printBlockMenuNumeric(sQuestion, menuItems);
			    }
			    
			    while (true) {
			        String sInput = inputReader.nextLine().trim();	  
			        
			        //Defaultkey (also ENTER) berücksichtigen
		            if(sInput.length()==0) { //Merke: Die Scanner Klasse liefert bei ENTER einfach eine Leerzeile
		            	for (IKeyPressCharZZZ key : menuItems) {
				        	if(key.isKeyDefault()) {
				        		String sKey = CharZZZ.toString(key.getKeyChar());
				                return sKey; // kanonischen Schlüssel zurückgeben
				            }	            	           	           
				        }	    	
		            }else if(StringZZZ.isNumeric(sInput)){
		            	return sInput;		            	
		            }else {
				        for (IKeyPressCharZZZ key : menuItems) {
				        	String sKey = CharZZZ.toString(key.getKeyChar());
				            if (sKey.equalsIgnoreCase(sInput)) {
				                return sKey; // kanonischen Schlüssel zurückgeben
				            }	            	           	           
				        }
		            }           
		            System.out.println("Ungültige Eingabe.");
			    }//end while(true)	 
		}//end main:		
	}
	
	//Verbesserung, da man die ArrayListe der Keys verwendet und so auch default-Keys erzeugen kann
		public static void printlnMenuNumeric(
		        String question,
		        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
			String sLine = makeMenuLineNumeric(question, menuItems);
			System.out.println(sLine);
		}
	
	public static void printBlockMenuNumeric(
	        String question,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
		String sLine = makeMenuBlockNumeric(question, menuItems);
		System.out.println(sLine);
	}
	
	
	public static String makeMenuLineNumeric(String sQuestion, ArrayList<IKeyPressCharZZZ> listaMenuItem) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			if(listaMenuItem==null) {
				throw new ExceptionZZZ("'Menu items'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());				
			}						
			if(listaMenuItem.isEmpty()) break main;
			
			if(sQuestion.endsWith("?")) {
				sQuestion = StringZZZ.stripRight(sQuestion, "?");
			}
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			sReturn = sQuestion;
								
			boolean bKeyDefaultAssigned= false; boolean bKeyTagCreated = false;
			for(IKeyPressCharZZZ entry : listaMenuItem) {				
				if(!bKeyTagCreated) {
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						bKeyDefaultAssigned = true;
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText());
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText());
					}
				}else {					
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						sReturn = sReturn + "/" + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText());
					}else {
						sReturn = sReturn + "/" + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText());
					}
				}												
				bKeyTagCreated=true;
			}//end for			
		}//end main:		
		return sReturn;		
	}					
	
	public static String makeMenuBlockNumeric(String sQuestion, ArrayList<IKeyPressCharZZZ> listaMenuItem) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(listaMenuItem==null) {
				throw new ExceptionZZZ("'Menu items'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());				
			}						
			if(listaMenuItem.isEmpty()) break main;
			
			sReturn = sQuestion;
			
			boolean bKeyDefaultAssigned= false; boolean bKeyTagCreated = false;
			for(IKeyPressCharZZZ entry : listaMenuItem) {	
				sReturn = sReturn + StringZZZ.crlf();
				if(!bKeyTagCreated) {
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						bKeyDefaultAssigned = true;
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText(), false);						
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText(), false);
					}
				}else {										
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText(), false);
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText(), false);
					}
				}
				bKeyTagCreated=true;
				sReturn = sReturn + " " + entry.getKeyDescription();				
			}//end for		
		}
		return sReturn;		
	}
	

	public static String makeInputNumericCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
		return makeInputNumericCancel(inputReader, sQuestionIn,true);
	}
	
	public static String makeInputNumericCancel(Scanner inputReader, String sQuestionIn, boolean bAsLine) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(inputReader==null){
				String stemp = "'Scanner as InputReader'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sQuestion = StringZZZ.trim(sQuestionIn);			
			if(sQuestion.endsWith("?")) {
				sQuestion = StringZZZ.stripRight(sQuestion, "?");
			}
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
	    	//20260918 Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
			ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
			IKeyPressCharZZZ keyEnter = Key_enterZZZ.getInstance();	
			keyEnter.isKeyDefault(true);
			listaKey.add(keyEnter);
			IKeyPressCharZZZ keyCancel = Key_cancelZZZ.getInstance();			
			listaKey.add(keyCancel);
			sReturn = makeMenuInputNumeric(inputReader, sQuestion, listaKey, bAsLine);
			
		}//end main:
		return sReturn;
	}
	//##############################
	
	public static void printlnInputAlphabetCancel(String sQuestionIn) throws ExceptionZZZ{

		String sQuestion = StringZZZ.trim(sQuestionIn);
		if(StringZZZ.isEmpty(sQuestion)){
			String stemp = "'Question String'";
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
			ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}	
		
		if(sQuestion.endsWith("?")) {
			sQuestion = StringZZZ.stripRight(sQuestion, "?");
		}
		sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringInputAlphabetCancel()+ "?";
		System.out.println( sQuestion);				
	}
	
	public static void printlnInputNumericCancel(String sQuestionIn) throws ExceptionZZZ{

		String sQuestion = StringZZZ.trim(sQuestionIn);
		if(StringZZZ.isEmpty(sQuestion)){
			String stemp = "'Question String'";
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
			ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}	
		
		if(sQuestion.endsWith("?")) {
			sQuestion = StringZZZ.stripRight(sQuestion, "?");
		}
		sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringInputNumericCancel();
		System.out.println( sQuestion);				
	}
	
	//##########################################################################
	//### Kompaktere Lösung, mit ArrayList. 
	//### Reduziert Code-Redundanz
	//### Mit dem KeyPress-Objekt kann man auch "Default" Key festlegen
	//##########################################################################
	public static String makeMenuInputLine(Scanner inputReader,
	        String sQuestion,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
		return makeMenuInput(inputReader, sQuestion, menuItems, true);
	}
	public static String makeMenuInputBlock(Scanner inputReader,
	        String sQuestion,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
		return makeMenuInput(inputReader, sQuestion, menuItems, false);
	}
	public static String makeMenuInput(
	        Scanner inputReader,
	        String sQuestion,
	        ArrayList<IKeyPressCharZZZ> menuItems, boolean bAsLine) throws ExceptionZZZ {

	    if (inputReader == null) {
	        throw new ExceptionZZZ("'Scanner as InputReader'",
	                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
	                ReflectCodeZZZ.getMethodCurrentName());
	    }
	    if (menuItems == null || menuItems.isEmpty()) {
	        throw new ExceptionZZZ("'Menu items'",
	                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
	                ReflectCodeZZZ.getMethodCurrentName());
	    }

	    if(bAsLine) {
	    	printlnMenu(sQuestion, menuItems);
	    }else {
	    	printBlockMenu(sQuestion, menuItems);
	    }
	    
	    while (true) {
	        String sInput = inputReader.nextLine().trim();	  
	        
	        //Defaultkey (also ENTER) berücksichtigen
            if(sInput.length()==0) { //Merke: Die Scanner Klasse liefert bei ENTER einfach eine Leerzeile
            	for (IKeyPressCharZZZ key : menuItems) {
		        	if(key.isKeyDefault()) {
		        		String sKey = CharZZZ.toString(key.getKeyChar());
		                return sKey; // kanonischen Schlüssel zurückgeben
		            }	            	           	           
		        }	    	
            }else {                        
		        for (IKeyPressCharZZZ key : menuItems) {
		        	String sKey = CharZZZ.toString(key.getKeyChar());
		            if (sKey.equalsIgnoreCase(sInput)) {
		                return sKey; // kanonischen Schlüssel zurückgeben
		            }	            	           	           
		        }
            }           
            System.out.println("Ungültige Eingabe.");
	    }//end while(true)	 
	}
	
	
	//Verbesserung, da man die ArrayListe der Keys verwendet und so auch default-Keys erzeugen kann
	public static void printlnMenu(
	        String question,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
		String sLine = makeMenuLine(question, menuItems);
		System.out.println(sLine);
	}
	
	public static void printBlockMenu(
	        String question,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {
		String sLine = makeMenuBlock(question, menuItems);
		System.out.println(sLine);
	}
	
	public static String makeMenuBlock(String sQuestion, ArrayList<IKeyPressCharZZZ> listaMenuItem) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(listaMenuItem==null) {
				throw new ExceptionZZZ("'Menu items'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());				
			}						
			if(listaMenuItem.isEmpty()) break main;
			
			sReturn = sQuestion + "? ";
			
			boolean bKeyDefaultAssigned= false; boolean bKeyTagCreated = false;
			for(IKeyPressCharZZZ entry : listaMenuItem) {	
				sReturn = sReturn + StringZZZ.crlf();
				if(!bKeyTagCreated) {
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						bKeyDefaultAssigned = true;
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText(), false);						
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText(), false);
					}
				}else {										
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText(), false);
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText(), false);
					}
				}
				bKeyTagCreated=true;
				sReturn = sReturn + " " + entry.getKeyText();				
			}//end for		
		}
		return sReturn;		
	}
	
	public static String makeMenuLine(String sQuestion, ArrayList<IKeyPressCharZZZ> listaMenuItem) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			if(listaMenuItem==null) {
				throw new ExceptionZZZ("'Menu items'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());				
			}						
			if(listaMenuItem.isEmpty()) break main;
			
			sReturn = sQuestion + "? ";
								
			boolean bKeyDefaultAssigned= false; boolean bKeyTagCreated = false;
			for(IKeyPressCharZZZ entry : listaMenuItem) {				
				if(!bKeyTagCreated) {
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						bKeyDefaultAssigned = true;
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText());
					}else {
						sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText());
					}
				}else {					
					if(entry.isKeyDefault() && !bKeyDefaultAssigned) {
						sReturn = sReturn + "/" + KeyPressUtilZZZ.computeKeyTagAsDefault(entry.getKeyText());
					}else {
						sReturn = sReturn + "/" + KeyPressUtilZZZ.computeKeyTag(entry.getKeyText());
					}
				}												
				bKeyTagCreated=true;
			}//end for			
		}//end main:		
		return sReturn;		
	}					
	//############################################################

	
	public static String makeQuestionYesNoCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(inputReader==null){
				String stemp = "'Scanner as InputReader'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sQuestion = StringZZZ.trim(sQuestionIn);
			if(sQuestion.endsWith("?")) {
				sQuestion = StringZZZ.stripRight(sQuestion, "?");
			}
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			//20260918 Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
			ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
			IKeyPressCharZZZ keyNo = Key_noZZZ.getNewInstance();
			keyNo.isKeyDefault(true);
			listaKey.add(keyNo);
			listaKey.add(Key_yesZZZ.getInstance());
			listaKey.add(Key_cancelZZZ.getInstance());			
			String sInput = makeMenuInputLine(inputReader, sQuestion, listaKey);
		
			sReturn = sInput;
		}//end main:
		return sReturn;
	}
	
	
	//#############################
	public static String makeQuestionYesNoQuit(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(inputReader==null){
				String stemp = "'Scanner as InputReader'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String sQuestion = StringZZZ.trim(sQuestionIn);
			if(sQuestion.endsWith("?")) {
				sQuestion = StringZZZ.stripRight(sQuestion, "?");
			}
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			//20260918 Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
			ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
			IKeyPressCharZZZ keyNo = Key_noZZZ.getNewInstance();
			keyNo.isKeyDefault(true);
			listaKey.add(keyNo);
			listaKey.add(Key_yesZZZ.getInstance());
			listaKey.add(Key_quitZZZ.getInstance());
			String sInput = makeMenuInputLine(inputReader, sQuestion, listaKey);
			
			sReturn = sInput;
		}//end main:
		return sReturn;
	}
		
		public static String makeQuestionYesNoMenueQuit(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sQuestion = StringZZZ.trim(sQuestionIn);
				if(sQuestion.endsWith("?")) {
					sQuestion = StringZZZ.stripRight(sQuestion, "?");
				}
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
								
				//20260918 Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
				ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
				IKeyPressCharZZZ keyNo = Key_noZZZ.getNewInstance();
				keyNo.isKeyDefault(true);
				listaKey.add(keyNo);
				listaKey.add(Key_yesZZZ.getInstance());
				listaKey.add(Key_menueZZZ.getInstance());
				listaKey.add(Key_quitZZZ.getInstance());
				String sInput = makeMenuInputLine(inputReader, sQuestion, listaKey);

				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
		public static String makeQuestionYesNoMenueStopQuit(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				String sQuestion = StringZZZ.trim(sQuestionIn);
				if(sQuestion.endsWith("?")) {
					sQuestion = StringZZZ.stripRight(sQuestion, "?");
				}
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}				
				
							
				//20260918 Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
				ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
				IKeyPressCharZZZ keyNo = Key_noZZZ.getNewInstance();
				keyNo.isKeyDefault(true);
				listaKey.add(keyNo);
				listaKey.add(Key_yesZZZ.getInstance());
				listaKey.add(Key_menueZZZ.getInstance());
				listaKey.add(Key_quitZZZ.getInstance());
				listaKey.add(Key_stopZZZ.getInstance());
				String sInput = makeMenuInputLine(inputReader, sQuestion, listaKey);

				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		

				
		//###################################################################
		//### Ausgabe ohne ein Menü oder Frage, hier wird einfach auf eine Eingabe gewartet.
		//### Für das Warten sorgt die Scanner - Klasse
		//###################################################################
		//##########################################################################
		//### Kompaktere Lösung, mit ArrayList. 
		//### Reduziert Code-Redundanz
		//### Mit dem KeyPress-Objekt kann man auch "Default" Key festlegen
		//### ENTER als "Default" ist auch möglich.
		//##########################################################################
		public static String makeWaitInput(
		        Scanner inputReader,
		        ArrayList<IKeyPressCharZZZ> listaKey) throws ExceptionZZZ {

		    if (inputReader == null) {
		        throw new ExceptionZZZ("'Scanner as InputReader'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());
		    }
		    if (listaKey == null || listaKey.isEmpty()) {
		        throw new ExceptionZZZ("'Menu items'",
		                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
		                ReflectCodeZZZ.getMethodCurrentName());
		    }

		    while (true) {
		    	//Merke: Das Warten auf die inputReader.nextLine() Eingabe verhindert in der aufrufenden Methode, das z.B. ein Thread x-fach gestartet wird.
		        String sInput = inputReader.nextLine().trim();	  
		        
		        //ENTER als "DEFAUTKEY" ist theoretisch auch möglich 
	            if(sInput.length()==0) { //Merke: Die Scanner Klasse liefert bei ENTER einfach eine Leerzeile
	            	for (IKeyPressCharZZZ key : listaKey) {
			        	if(key.isKeyDefault()) {
			        		if(CharZZZ.isNull(key.getKeyChar())) {//AnyKey
			        			return sInput;
			        		}else {
			        			String sKey = CharZZZ.toString(key.getKeyChar());
			        			return sKey; // kanonischen Schlüssel zurückgeben
			        		}
			            }	            	           	           
			        }	    	
	            }else {                        
			        for (IKeyPressCharZZZ key : listaKey) {			        	
			        	if(CharZZZ.isNull(key.getKeyChar())) {//AnyKey
		        			return sInput;
		        		}else {
				        	String sKey = CharZZZ.toString(key.getKeyChar());
				            if (sKey.equalsIgnoreCase(sInput)) {
				                return sKey; // kanonischen Schlüssel zurückgeben
				            }	    
		        		}
			        }
	            }           
	            System.out.println("Ungültige Eingabe.");
		    }//end while(true)	 
		}
	
		public static String makeWaitForInputYesNoMenueStopQuit(Scanner inputReader) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
																			
				//OHNE FRAGE ... KeyPressUtilZZZ.printlnQuestionYesNoMenueStopQuit(sQuestion);
				
				//ArrayList aufbauen und ohne Frage übergeben... analog zu den Fragemethoden mit Menü
				ArrayList<IKeyPressCharZZZ> listaKey = new ArrayList<IKeyPressCharZZZ>();
				IKeyPressCharZZZ keyNo = Key_noZZZ.getNewInstance();
				//keyNo.isKeyDefault(true); //ENTER als Defaultkey wird nicht berücksichtigt
				listaKey.add(keyNo);
				listaKey.add(Key_yesZZZ.getInstance());
				
				IKeyPressCharZZZ keyMenue = Key_menueZZZ.getNewInstance();
				keyMenue.isKeyDefault(true);//ENTER als Defaultkey wird berücksichtigt
				listaKey.add(keyMenue);
				
				listaKey.add(Key_quitZZZ.getInstance());
				listaKey.add(Key_stopZZZ.getInstance());
				String sInput = makeWaitInput(inputReader, listaKey);
					
				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
		public static String makeWaitForInputAny(Scanner inputReader) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
																			
				//OHNE FRAGE ... KeyPressUtilZZZ.printlnQuestionYesNoMenueStopQuit(sQuestion);
				
				//ArrayList aufbauen und ohne Frage übergeben... analog zu den Fragemethoden mit Menü
				ArrayList<IKeyPressCharZZZ> listaKey = new ArrayList<IKeyPressCharZZZ>();
				IKeyPressCharZZZ keyAny = Key_anyZZZ.getNewInstance();
				keyAny.isKeyDefault(true); //ANY Key wird berücksichtigt
				listaKey.add(keyAny);				
				String sInput = makeWaitInput(inputReader, listaKey);
					
				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
}
