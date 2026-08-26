package basic.zBasic.util.console.thread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
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
	
	
	
	//###########################################
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
												
				KeyPressUtilZZZ.printlnQuestionYesNoCancel(sQuestion);
				
				boolean bGoon=false; String sInput = null;
				do {
					sInput = inputReader.nextLine();
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": sInput = '"+sInput+"'");
	                if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
	                	bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyCancel)) {
                		//System.out.println("Abbruch eingegeben");
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
		
		public static void printlnQuestionYesNoCancel(String sQuestionIn) throws ExceptionZZZ{

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
				sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoCancel()+ "?";
				System.out.println( sQuestion);				
		}
		
		public static String computeKeyTagStringYesNoCancel() {
			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_noZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_cancelZZZ.getKey());	
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
				if(StringZZZ.isEmpty(sQuestion)){
					String stemp = "'Question String'";
					System.out.println(ReflectCodeZZZ.getMethodCurrentName() + ": "+ stemp);
					ExceptionZZZ ez = new ExceptionZZZ(stemp,iERROR_PARAMETER_MISSING, KeyPressUtilZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
												
				KeyPressUtilZZZ.printlnQuestionYesNoQuit(sQuestion);
				
				boolean bGoon=false; String sInput = null;
				do {
					sInput = inputReader.nextLine();
					if(sInput.length()==0) { //Merke: Die Scanner Klasse liefert bei ENTER einfach eine Leerzeile
						bGoon = true;
					}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
	                	bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
                		//System.out.println("Abbruch eingegeben");
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
												
				KeyPressUtilZZZ.printlnQuestionYesNoMenueQuit(sQuestion);
				
				boolean bGoon=false; String sInput = null;
				do {
					sInput = inputReader.nextLine();
					if(sInput.length()==0) { //Merke der Scanner liefert kein '\n' sondern nur eine Leerzeile
						bGoon = true;
					}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyNo)) {				                		
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyYes)) {				                		
	                	bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyMenue)) {
                		bGoon = true;
                	}else if(StringZZZ.equalsIgnoreCase(sInput, IKeyPressConstantZZZ.cKeyQuit)) {
                		//System.out.println("Abbruch eingegeben");
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
		
		//##########################
		public static void printlnQuestionYesNoQuit(String sQuestionIn) throws ExceptionZZZ{

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
			sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoQuit()+ "?";
			System.out.println( sQuestion);				
	}
		
		public static String computeKeyTagStringYesNoQuit() {
			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(Key_noZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_quitZZZ.getKey());	
			return sReturn;
		}
		//###################################################################
		
		public static void printlnQuestionYesNoMenueQuit(String sQuestionIn) throws ExceptionZZZ{

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
			sQuestion = sQuestion + " " + KeyPressUtilZZZ.computeKeyTagStringYesNoMenueQuit()+ "?";
			System.out.println( sQuestion);				
	}
		
		public static String computeKeyTagStringYesNoMenueQuit() {
			String sReturn = KeyPressUtilZZZ.computeKeyTag(Key_yesZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTagAsDefault(Key_noZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_menueZZZ.getKey()) + "/";
			sReturn = sReturn + KeyPressUtilZZZ.computeKeyTag(Key_quitZZZ.getKey());	
			return sReturn;
		}
		//###################################################################
}
