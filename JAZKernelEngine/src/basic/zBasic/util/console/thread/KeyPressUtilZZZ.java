package basic.zBasic.util.console.thread;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KeyPressUtilZZZ implements IKeyPressConstantZZZ, IConstantZZZ{
	
		
	public static String computeKeyTag(String sKeyDescription) {
		String sReturn = IKeyPressConstantZZZ.sKeyTagOpen + sKeyDescription + IKeyPressConstantZZZ.sKeyTagClose;
		return sReturn;
	}
	public static String computeKeyTag(char cKey) {
		String sReturn = IKeyPressConstantZZZ.sKeyTagOpen + cKey + IKeyPressConstantZZZ.sKeyTagClose;
		return sReturn;
	}
	public static String computeKeyTagAsDefault(char cKey) {
		String sReturn = IKeyPressConstantZZZ.sKeyTagOpen + cKey + IKeyPressConstantZZZ.sKeyTagClose + "*default";
		return sReturn;
	}
	
	public static String computeKeyTagStringInputAlphabetCancel() {
		String sReturn = KeyPressUtilZZZ.computeKeyTag("Buchstaben des Alphabets") + "/";
		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
		return sReturn;
	}
	
	public static String computeKeyTagStringInputNumericCancel() {
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
	
	public static String makeInputNumericCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
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
											
			KeyPressUtilZZZ.printlnInputNumericCancel(sQuestion);
			
			boolean bGoon=false; String sInput = null;
			do {
			   sInput = inputReader.nextLine();	                
               if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
            		//System.out.println("Abbruch eingegeben");
            		bGoon = true;
               }else if(StringZZZ.isEmpty(sInput)) {
            	   System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - isEmpty Zweig: sInput = '"+sInput+"'");
            	   System.out.println("ungueltige Eingabe");
            	   bGoon = false;
            	}else if(StringZZZ.isNumeric(sInput)){            		
            		bGoon = true;
            	}else {  
            		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
            		System.out.println("Hier ungueltige Eingabe. Vielleicht erst zum Menü mit 'm' zurückgehen?");			                		
                	bGoon=false;				                	
            	}				
			}while(!bGoon);
			sReturn = sInput;
		}//end main:
		return sReturn;
	}
	
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
		sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringInputNumericCancel()+ "?";
		System.out.println( sQuestion);				
	}
	
	//##########################################################################
	//### Kompaktere Lösung, mit LinkedHashMap. 
	//### Reduziert Code-Redundanz
	//##########################################################################
//	public static String makeMenuInput(
//	        Scanner inputReader,
//	        String question,
//	        LinkedHashMap<String, String> menuItems) throws ExceptionZZZ {
//
//	    if (inputReader == null) {
//	        throw new ExceptionZZZ("'Scanner as InputReader'",
//	                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
//	                ReflectCodeZZZ.getMethodCurrentName());
//	    }
//	    if (menuItems == null || menuItems.isEmpty()) {
//	        throw new ExceptionZZZ("'Menu items'",
//	                iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,
//	                ReflectCodeZZZ.getMethodCurrentName());
//	    }
//
//	    printlnMenu(question, menuItems);
//
//	    while (true) {
//	        String input = inputReader.nextLine().trim();
//
//	        for (String key : menuItems.keySet()) {
//	            if (key.equalsIgnoreCase(input)) {
//	                return key; // kanonischen Schlüssel zurückgeben
//	            }
//	        }
//	        System.out.println("Ungültige Eingabe.");
//	    }
//	}
	
	//Verbessert, weil man den Key nun als Objekt übergibt.
	//Dann kann man auch "Defaultkeys" festlegen.
	public static String makeMenuInput(
	        Scanner inputReader,
	        String question,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {

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

	    printlnMenu(question, menuItems);

	    while (true) {
	        String input = inputReader.nextLine().trim();

	        //for (String key : menuItems.keySet()) {
	        for (IKeyPressCharZZZ key : menuItems) {
	        	String sKey = CharZZZ.toString(key.getKeyChar());
	            if (sKey.equalsIgnoreCase(input)) {
	                return sKey; // kanonischen Schlüssel zurückgeben
	            }
	        }
	        System.out.println("Ungültige Eingabe.");
	    }
	}
	

//	public static void printlnMenu(
//	        String question,
//	        LinkedHashMap<String, String> menuItems) {
//
//	    StringBuilder text = new StringBuilder(question);
//	    text.append(System.lineSeparator());
//
//	    for (Map.Entry<String, String> entry : menuItems.entrySet()) {
//	        text.append("  ")
//	            .append(computeKeyTag(entry.getKey()))
//	            .append(" ")
//	            .append(entry.getValue())
//	            .append(System.lineSeparator());
//	    }
//
//	    System.out.print(text);
//	}
	
	//Verbesserung, da man die ArrayListe der Keys verwendet und so auch default-Keys erzeugen kann
	public static void printlnMenu(
	        String question,
	        ArrayList<IKeyPressCharZZZ> menuItems) throws ExceptionZZZ {

	    StringBuilder text = new StringBuilder(question);
	    text.append(System.lineSeparator());

	    //for (Map.Entry<String, String> entry : menuItems.entrySet()) {
	    for(IKeyPressCharZZZ entry : menuItems) {
	        text.append("  ")
	            .append(computeKeyTag(entry.getKeyChar()))
	            .append(" ")
	            .append(entry.getKeyText())
	            .append(System.lineSeparator());
	    }

	    System.out.print(text);
	}
	
	
	
	//############################################################

	
	
	//###########################################
//	public static String computeKeyTagStringYesNoCancel() {
//		String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
//		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_noZZZ.getKey()) + "/";
//		sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
//		return sReturn;
//	}
	
//		public static String makeQuestionYesNoCancel(Scanner inputReader, String sQuestionIn) throws ExceptionZZZ{
//			String sReturn = null;
//			main:{
//				if(inputReader==null){
//					String stemp = "'Scanner as InputReader'";
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//				
//				String sQuestion = StringZZZ.trim(sQuestionIn);
//				if(StringZZZ.isEmpty(sQuestion)){
//					String stemp = "'Question String'";
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}
//												
//				KeyPressUtilZZZ.printlnQuestionYesNoCancel(sQuestion);
//				
//				boolean bGoon=false; String sInput = null;
//				do {
//					sInput = inputReader.nextLine();
//					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sInput = '"+sInput+"'");
//	                if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
//	                	bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
//                		//System.out.println("Abbruch eingegeben");
//                		bGoon = true;
//                	}else {
//                		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
//                		System.out.println("Hier ungueltige Eingabe.");			                		
//	                	bGoon=false;				                	
//                	}				
//				}while(!bGoon);
//				sReturn = sInput;
//			}//end main:
//			return sReturn;
//		}
	
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
			if(StringZZZ.isEmpty(sQuestion)){
				String stemp = "'Question String'";
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
					
			//Einsatz der neuen KI generierten Methode
//			LinkedHashMap<String,String>hmMenuItems=new LinkedHashMap<String,String>();
//			hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyNo), "Nein");
//			hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyYes), "Ja");
//			hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyCancel), "Abbruch");			
//			String sInput = makeMenuInput(inputReader, sQuestion, hmMenuItems);
			
			//20260918
			//Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
			ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
			listaKey.add(Key_noZZZ.getInstance());
			listaKey.add(Key_yesZZZ.getInstance());
			listaKey.add(Key_cancelZZZ.getInstance());			
			String sInput = makeMenuInput(inputReader, sQuestion, listaKey);
		
			sReturn = sInput;
		}//end main:
		return sReturn;
	}
	
	
		
//		public static void printlnQuestionYesNoCancel(String sQuestionIn) throws ExceptionZZZ{
//
//				String sQuestion = StringZZZ.trim(sQuestionIn);
//				if(StringZZZ.isEmpty(sQuestion)){
//					String stemp = "'Question String'";
//					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//					throw ez;
//				}	
//				
//				if(sQuestion.endsWith("?")) {
//					sQuestion = StringZZZ.stripRight(sQuestion, "?");
//				}
//				sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoCancel()+ "?";
//				System.out.println( sQuestion);				
//		}
		
		
		
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
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//Einsatz der neuen KI generierten Methode
//				LinkedHashMap<String,String>hmMenuItems=new LinkedHashMap<String,String>();
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyNo), "Nein");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyYes), "Ja");				
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyQuit), "Quit");									
//				String sInput = makeMenuInput(inputReader, sQuestion, hmMenuItems);
				
				//20260918
				//Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
				ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
				listaKey.add(Key_noZZZ.getInstance());
				listaKey.add(Key_yesZZZ.getInstance());
				listaKey.add(Key_quitZZZ.getInstance());
				String sInput = makeMenuInput(inputReader, sQuestion, listaKey);
				
				
				
												
//				KeyPressUtilZZZ.printlnQuestionYesNoQuit(sQuestion);
//				
//				boolean bGoon=false; String sInput = null;
//				do {
//					sInput = inputReader.nextLine();
//					if(sInput.length()==0) { //Merke: Die Scanner Klasse liefert bei ENTER einfach eine Leerzeile
//						bGoon = true;
//					}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
//	                	bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
//                		//System.out.println("Abbruch eingegeben");
//                		bGoon = true;
//                	}else {
//                		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
//                		System.out.println("Hier ungueltige Eingabe.");			                		
//	                	bGoon=false;			                	
//                	}				
//				}while(!bGoon);
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
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//Einsatz der neuen KI generierten Methode
//				LinkedHashMap<String,String>hmMenuItems=new LinkedHashMap<String,String>();
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyNo), "Nein");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyYes), "Ja");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyMenue), "Menü");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyQuit), "Quit");			
//				String sInput = makeMenuInput(inputReader, sQuestion, hmMenuItems);
				
				//20260918
				//Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
				ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
				listaKey.add(Key_noZZZ.getInstance());
				listaKey.add(Key_yesZZZ.getInstance());
				listaKey.add(Key_menueZZZ.getInstance());
				listaKey.add(Key_quitZZZ.getInstance());
				String sInput = makeMenuInput(inputReader, sQuestion, listaKey);
			
				
				
//				KeyPressUtilZZZ.printlnQuestionYesNoMenueQuit(sQuestion);
//				
//				boolean bGoon=false; String sInput = null;
//				do {
//					sInput = inputReader.nextLine();
//					if(sInput.length()==0) { //Merke der Scanner liefert kein '\n' sondern nur eine Leerzeile
//						bGoon = true;
//					}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
//	                	bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyMenue)) {
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
//                		//System.out.println("Quit eingegeben");
//                		bGoon = true;                	
//                	}else {
//                		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
//                		System.out.println("Hier ungueltige Eingabe. Vielleicht erst zum Menü mit 'm' zurückgehen?");			                		
//	                	bGoon=false;				                	
//                	}				
//				}while(!bGoon);
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
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				//Einsatz der neuen KI generierten Methode
//				LinkedHashMap<String,String>hmMenuItems=new LinkedHashMap<String,String>();
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyNo), "Nein");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyYes), "Ja");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyMenue), "Menü");
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyQuit), "Quit");			
//				hmMenuItems.put(CharZZZ.toString(IKeyPressConstantZZZ.cKeyStop), "Stop");
//				String sInput = makeMenuInput(inputReader, sQuestion, hmMenuItems);
				
				//20260918
				//Verbessert mit ArrayList und dem KeyObjekt... dann kann man auch Default-Keys übergeben.
				ArrayList<IKeyPressCharZZZ>listaKey = new ArrayList<IKeyPressCharZZZ>();
				listaKey.add(Key_noZZZ.getInstance());
				listaKey.add(Key_yesZZZ.getInstance());
				listaKey.add(Key_menueZZZ.getInstance());
				listaKey.add(Key_quitZZZ.getInstance());
				listaKey.add(Key_stopZZZ.getInstance());
				String sInput = makeMenuInput(inputReader, sQuestion, listaKey);
			
				
				
//				KeyPressUtilZZZ.printlnQuestionYesNoMenueStopQuit(sQuestion);
//				
//				//Merke: Das Warten auf die inputReader.nextLine() Eingabe verhindert in der aufrufenden Methode, das z.B. ein Thread x-fach gestartet wird.
//				boolean bGoon=false; String sInput = null;
//				do {
//					sInput = inputReader.nextLine();
//					if(sInput.length()==0) { //Merke der Scanner liefert kein '\n' sondern nur eine Leerzeile
//						bGoon = true; //Merke: Das ist das Problem, das ein einfaches "ENTER" während der Verarbeitung einen zweiten Thread starten würde.
//						              //       Darum ist das bei der "ersten Eingabe" erlaubt.
//						              //       Beim "Warten auf eine Menüeingabe" im folgenden aber nicht mehr. .waitForInputYesNoMenueStopQuit(...);
//					}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
//	                	bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyMenue)) {
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
//                		//System.out.println("Quit eingegeben");
//                		bGoon = true;
//                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyStop)) {
//                		//System.out.println("Stop eingegeben");
//                		bGoon = true;
//                	}else {
//                		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
//                		System.out.println("Hier ungueltige Eingabe. Vielleicht erst zum Menü mit 'm' zurückgehen?");			                		
//	                	bGoon=false;				                	
//                	}				
//				}while(!bGoon);
				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
		//##########################
//		public static String computeKeyTagStringYesNoQuit() {
//			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(Key_noZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_quitZZZ.getKey());	
//			return sReturn;
//		}
		
//		public static void printlnQuestionYesNoQuit(String sQuestionIn) throws ExceptionZZZ{
//
//			String sQuestion = StringZZZ.trim(sQuestionIn);
//			if(StringZZZ.isEmpty(sQuestion)){
//				String stemp = "'Question String'";
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}	
//			
//			if(sQuestion.endsWith("?")) {
//				sQuestion = StringZZZ.stripRight(sQuestion, "?");
//			}
//			sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoQuit()+ "?";
//			System.out.println( sQuestion);				
//	}
		
		
		//###################################################################
		
//		public static String computeKeyTagStringYesNoMenueQuit() {
//			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(Key_noZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_menueZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_quitZZZ.getKey());	
//			return sReturn;
//		}
		
//		public static void printlnQuestionYesNoMenueQuit(String sQuestionIn) throws ExceptionZZZ{
//
//			String sQuestion = StringZZZ.trim(sQuestionIn);
//			if(StringZZZ.isEmpty(sQuestion)){
//				String stemp = "'Question String'";
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}	
//			
//			if(sQuestion.endsWith("?")) {
//				sQuestion = StringZZZ.stripRight(sQuestion, "?");
//			}
//			sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoMenueQuit()+ "?";
//			System.out.println( sQuestion);				
//	}
		
		
		
		//##########################################################
//		public static String computeKeyTagStringYesNoMenueStopQuit() {
//			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(Key_noZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_menueZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_stopZZZ.getKey()) + "/";
//			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_quitZZZ.getKey());	
//			return sReturn;
//		}
		
//		public static void printlnQuestionYesNoMenueStopQuit(String sQuestionIn) throws ExceptionZZZ{
//
//			String sQuestion = StringZZZ.trim(sQuestionIn);
//			if(StringZZZ.isEmpty(sQuestion)){
//				String stemp = "'Question String'";
//				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
//				ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
//				throw ez;
//			}	
//			
//			if(sQuestion.endsWith("?")) {
//				sQuestion = StringZZZ.stripRight(sQuestion, "?");
//			}
//			sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoMenueStopQuit()+ "?";
//			System.out.println( sQuestion);				
//	}
				
		//###################################################################
		
		public static String waitForInputYesNoMenueStopQuit(Scanner inputReader) throws ExceptionZZZ{
			String sReturn = null;
			main:{
				if(inputReader==null){
					String stemp = "'Scanner as InputReader'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
																			
				//OHNE FRAGE ... KeyPressUtilZZZ.printlnQuestionYesNoMenueStopQuit(sQuestion);
				
				//Merke: Das Warten auf die inputReader.nextLine() Eingabe verhindert in der aufrufenden Methode, das z.B. ein Thread x-fach gestartet wird.
				boolean bGoon=false; String sInput = null;
				do {
					sInput = inputReader.nextLine();
					
					//ENTER ist kein gültiger Befehl 
//					if(sInput.length()==0) { //Merke der Scanner liefert kein '\n' sondern nur eine Leerzeile
//						bGoon = true; //Merke: Das ist das Problem, das ein einfaches "ENTER" während der Verarbeitung einen zweiten Thread starten würde.
//						              //       Darum ist das bei der "ersten Eingabe" erlaubt.
//						              //       Beim "Warten auf eine Menüeingabe" im folgenden aber nicht mehr. .waitForInputYesNoMenueStopQuit(...);
//					}else 
						
					if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
	                	bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyMenue)) {
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
                		//System.out.println("Quit eingegeben");
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyStop)) {
                		//System.out.println("Stop eingegeben");
                		bGoon = true;
                	}else {
                		System.out.println(ReflectCodeZZZ.getPositionCurrent() + " - else Zweig: sInput = '"+sInput+"'");
                		System.out.println("Hier ungueltige Eingabe. Vielleicht erst zum Menü mit 'm' zurückgehen?");			                		
	                	bGoon=false;				                	
                	}				
				}while(!bGoon);
				sReturn = sInput;
			}//end main:
			return sReturn;
		}
		
}
