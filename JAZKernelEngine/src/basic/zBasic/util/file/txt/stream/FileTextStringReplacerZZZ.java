package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ListUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class FileTextStringReplacerZZZ  extends  AbstractFileTextReaderSaverZZZ{
	private static final long serialVersionUID = -3451810324201026617L;

	public FileTextStringReplacerZZZ() throws ExceptionZZZ {		
	}
	public FileTextStringReplacerZZZ(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	public FileTextStringReplacerZZZ(Path objPath) throws ExceptionZZZ {
		super(objPath);
	}
	public FileTextStringReplacerZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}	
	public FileTextStringReplacerZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}
	
	//###############################	
	public boolean replace(String sStringToFind, String sStringToReplace) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sStringToFind)) break main;
			//Leerzeichen sind erlaubte Ersetzung if(StringZZZ.isEmpty(sStringToReplace)) break main;
			
			//Hole alle Zeilen und mache daraus eine String
			List<String> listaLine = this.getLines();
			if(listaLine.isEmpty()) break main;
			
			String sContentAll = ListUtilZZZ.implode(listaLine, StringZZZ.crlf());
			if(StringZZZ.isEmpty(sContentAll)) break main;
						
			String sContentProcessed = processString_(sContentAll, sStringToFind, sStringToReplace);
			
			List<String> listaProcessed = ListUtilZZZ.explode(sContentProcessed, StringZZZ.crlf());
			this.setLines(listaProcessed);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//##########################
	//### Komfortfunktion 2 in 1
	public boolean replaceAndSave(String sStringToFind, String sStringToReplace) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.replace(sStringToFind, sStringToReplace);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
	
	//#################################################
	private static String processString_(String sTotal, String sToFind, String sReplacement) {
		String sReturn = null;
        main:{
        	/*        	
Zwei kleine Tipps für deine Methode

• Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
• Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
        	 */
			
			
			// Regex: 
			//Pattern P = Pattern.compile(sToFind);
			Pattern P = Pattern.compile(Pattern.quote(sToFind));

//        	System.out.println("###########");
//        	System.out.println("## CONTENT ");
//        	System.out.println("###########");
//        	System.out.println(content);
        	
            Matcher m = P.matcher(sTotal);            
            String newContent = m.replaceAll(sReplacement);
            
//            System.out.println("###########");
//            System.out.println("## NEW CONTENT");
//            System.out.println("###########");
//            System.out.println(newContent);
            
            	sReturn = newContent;
            } //end main;
            return sReturn;
	}
}
