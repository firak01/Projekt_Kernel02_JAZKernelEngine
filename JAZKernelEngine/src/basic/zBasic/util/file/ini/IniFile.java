//////////////////////////////////////////////////////////////////////////////
//                                                                          //
// TYPE			: Java class                                                //
//                                                                          //
// NAME			: IniFile.java                                              //                         //
//                                                                          //
// AUTOR		: Steve DeGroof, degroof@mindspring.com,                    //
//				:                http://www.mindspring.com/~degroof         //
//				                                                            //
//                Peter K�hn, CoCoNet GmbH                                  //
//                Stephan Mecking, CoCoNet GmbH                             //
//                                                                          //
// COMPONENT    : MULTIWEB client -> some different componets               //
//                                                                          //
// DESCRIPTION	: This object manages files in Windows ".ini" format.       //
//				: It can read/write sections (here named subjects),         //
//				: keys (here named variables) and values.                   //
//				  The original source code of this object was found some-   //
//                where in the Internet without any copyright notice.       //
//                                                                          //
// HISTORY		                                                            //
//                                                                          //
// Date       | Changes                                        | Who        //
// ??.??.???? | first version                                  | SD         //
// 04.08.1997 | All objects of type Vector were replaced by    | PK, SM     //
//            | extended Vector and all method calls of        |            //
//            | indexOf and contains were replaced by the new  |            //
//            | methods indexOfString and containsString.      |            //
//            | In method findAssignmentBeetween was a string  |            //
//            | converted to lower-case. That has all happened |            //
//            | to make IniFile work case-independent.         |            //
// 08.08.1997 | In addAssignement were all leading and ending  | SM         //
//            | blank chars deleted by using of trim() on      |            //
//            | variables and values. Hope, now IniFile        |            //
//            | behaves like Windows                           |            //
// 01.10.1997 | Now no comment is created by creating a new    | SM         //
//            | Ini-File with createFile().                    |            //
// 28.10.1997 | Some methods and the constructors now throw    | SM         //
//            | a IOException in case of error instead of      |            //
//            | only returning "false".                        |            //
// 16.11.1998 | Bug in subject handling fixed                  | SM         //
//                                                                          //
//////////////////////////////////////////////////////////////////////////////

package basic.zBasic.util.file.ini;

import java.io.*;
import java.util.*;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ExtendedVectorZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zBasic.util.file.UtfEasyZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;


/**
* A class for handling Windows-style INI files. The file format is as 
* follows:
*   [subject]       - anything beginning with [ and ending with ] is a subject . FGL: Nun kann man dahinter noch Semikolon setzen, um einen Kommentar zu setzen. Leerzeichen werden weggetrimmt
*   ;comment        - anything beginning with a ; is a comment 
*   variable=value  - anything of the format string=string is an assignment 
*  comment         - anything that doesn't match any of the above is a comment 
*  
*  
*  FGL 20200316: Erweitert um UTF-8 und BOM Behandlung.  
*/


public class IniFile extends Object implements IIniStructureConstantZZZ
{
	/*
	Java does not handle BOM properly. In fact Java handles a BOM like every other char.
	Found this:
	http://www.rgagnon.com/javadetails/java-handle-utf8-file-with-bom.html
	
	May be I would use apache IO instead:
	http://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/input/BOMInputStream.html
	*/
	public static final String UTF8_BOM = "\uFEFF";
	
   /**Actual text lines of the file stored in a vector.*/
   protected ExtendedVectorZZZ lines;       
   /**A vector of all subjects*/
   protected ExtendedVectorZZZ subjects;    
   /**A vector of variable name vectors grouped by subject*/
   protected ExtendedVectorZZZ variables;   
   /**A vector of variable value vectors grouped by subject*/
   protected ExtendedVectorZZZ values;      
   /**Name of the file*/
   protected String fileName;   
   /**If true, INI file will be saved every time a value is changed. Defaults to false*/
   protected boolean saveOnChange = false;
      
   /** FGL: 20060423 setting this to false, will enable the class to read properties, where the values are empty*/
   protected boolean bFlagValueEmptyScip = false;
   

   /**
   * Creates an INI file object using the specified name
   * If the named file doesn't exist, create one
   * @param name the name of the file
 * @throws ExceptionZZZ 
   */
   public IniFile(String name) throws IOException, ExceptionZZZ
   {
      this(name,true);
   }

   /**
   * Creates an INI file object using the specified name
   * If the named file doesn't exist, create one
   * @param name the name of the file
   * @param saveOnSet save file whenever a value is set
 * @throws ExceptionZZZ 
   */
   public IniFile(String name, boolean save) throws IOException, ExceptionZZZ
   {
      saveOnChange = save;
      fileName = name;
      
     // if(this.saveOnChange == true){
      	//if false, the ini-file could resist only in memory
		if (!((new File(name)).exists()))
			{
			   createFile();
			}
			loadFile();
			parseLines();
    //  }
	
   }
   
  /**    CONSTRUCTOR: only for writing. the file will be kept in memory.
   @date: 15.11.2004
   @param save
   @throws IOException
   */ 
   public IniFile() throws IOException
   {
   		
   	//for initializing the variables
	lines = new ExtendedVectorZZZ();     
	subjects = new ExtendedVectorZZZ();  
	variables = new ExtendedVectorZZZ(); 
	values = new ExtendedVectorZZZ();    
   }


   /**
   * Loads and parses the INI file. Can be used to reload from file.
   */
   public void loadFile() throws IOException
   {
      //reset all vectors
      lines = new ExtendedVectorZZZ();     
      subjects = new ExtendedVectorZZZ();  
      variables = new ExtendedVectorZZZ(); 
      values = new ExtendedVectorZZZ();    
      //open the file
      try 
      {
    	  
    	  /* FGL 20060422 die Zeile ini.readLine gilt als "Depreciated", darum durch eine andere Programmierung ersetzt
         DataInputStream ini = new DataInputStream(
                                   new BufferedInputStream(
                                       new FileInputStream(fileName)));
         String line = "";
         //read all the lines in
         while (true)
         {
            line = ini.readLine();
            if (line == null)
               break;
            else
               lines.addElement(line.trim());
         }

		 ini.close();		 
		 */
    	  
    	  //Merke: Die ini Datei muss UTF-8 ohne BOM sein. Ansonsten sind in der ersten Zeile immer 2 zusätzliche Bytes, wenn man iniReader.readLine() ausführt.     	  
    	 FileInputStream inStream = new FileInputStream(fileName);    	      	 
    	 String line = "";
         //read all the lines in
   	  	 int itest = 0;   	  
         while (true)
         {
       	  itest++;
       	  if(inStream.available()<=0){
       		  break;
       	  }
            //Das liest garantiert nicht utf-8 ein line = dis.readLine();
       	  	line = IniFile.readLine(inStream); //Ziel ist es UTF-8 einzulesen
       	  	//System.out.println("sLine='" + line + "'");
            if (line == null)
               break;
            else
           	line = line.trim();           
           	String sLineUtf8 = new String(line.getBytes(),"UTF-8"); 
           	
           	//ABER: Falls es UTF-8 mit BOM ist, wird das allereste Zeichen mit BOM versehen.
           	if(itest==1) {//also nur für die 1. Zeile
           		sLineUtf8 = UtfEasyZZZ.removeUTF8BOM(sLineUtf8);
           	}
           	
            lines.addElement(sLineUtf8);
         }

         inStream.close();  
      }
      catch (IOException e)
      {
         System.out.println("IniFile load failed: " + e.getMessage());
         e.printStackTrace();
		 throw e;
      }
   }
   
   
   /** Reads UTF-8 character data; lines are terminated with '\n' */
   public static String readLine(InputStream in) throws IOException {
     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
     while (true) {
       int b = in.read();
       if (b < 0) {
         throw new IOException("Data truncated");
       }
       if (b == 0x0A) {
         break;
       }
       buffer.write(b);
     }
     return new String(buffer.toByteArray(), "UTF-8");
   }


   /**
   * Create a new INI file.
   */
   protected void createFile() throws IOException
   {
	   //20190712: Ziel ist es nun UTF-8 Datei zu erstellen
        //DataOutputStream newFile = new DataOutputStream(new FileOutputStream(fileName));
        //newFile.close();
         
	   //20190712: Ziel ist es nun UTF-8 Datei zu erstellen
       OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");
       writer.close();    
   }


   /**
   * Reads lines, filling in subjects, variables and values.
   * 
   * FGL 2008-02-19: Nun werden auch leere Sections erfasst !!!
 * @throws ExceptionZZZ 
   */
   protected void parseLines() throws ExceptionZZZ
   {
      String currentLine = null;    //current line being parsed
      String currentSubject = null; //the last subject found
      for (int i=0;i<lines.size();i++) //parse all lines
      {
         currentLine = (String)lines.elementAt(i); 
         if (isaSubject(currentLine)) //if line is a subject, set currentSubject
         {
        	int iIndexOfEnd = currentLine.indexOf(IIniStructureConstantZZZ.sINI_SUBJECT_END);//FGL 2019-12-18: Damit kann man Kommentare (hinter einem Semikolon) hinzufügen und Leerzeichen sind auch egal
            currentSubject = currentLine.substring(1,iIndexOfEnd);
            
            //FGL 2008-02-19: !!! Ohne nachstehende Erweiterung werden leere Sections nicht als Section erfasst !!! Sections wurden sonst nur erstellt, wenn sie auch Inhalt hatten !!!
            //             Die so gefundene Section muss sofort hinzugefügt werden.
            this.addSection(currentSubject);
         }
         else if (isanAssignment(currentLine)) //if line is an assignment, add it
         {
            String assignment = currentLine;
            addAssignment(currentSubject,assignment);
         }
      }
   }
   
   /** Adds and assignment (i.e. "variable=value") to a subject.
    * FGL 20060423 Now adding subjects, when the value is empty is the default case.
    * This is controlled by a flag.
 * @throws ExceptionZZZ 
   */
   protected boolean addAssignment(String subject, String assignment) throws ExceptionZZZ
   {
      String value;
      String variable;
      int index = assignment.indexOf("=");
      // trim the following strings, because it doesn`t matter if there
	  // are blanks before or after variables/values
	  variable = (assignment.substring(0,index)).trim();
      value = (assignment.substring(index+1,assignment.length())).trim();
      if(bFlagValueEmptyScip==true){
    	  if ((value.length()==0) || (variable.length()==0)) return false;
    	  else return addValue(subject, variable, value, false);
      }else{
    	 return  addValue(subject, variable, value, false);
      }
   }


   /**
   * Sets a specific subject/variable combination the given value. If the subject
   * doesn't exist, create it. If the variable doesn't exist, create it. If 
   * saveOnChange is true, save the file;
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   * @param value the value of the variable (e.g. "green")
   * @return true if successful
   */
   public boolean setValue(String subject, String variable, String value) throws IOException,ExceptionZZZ
   {
      boolean result = addValue(subject, variable, value, true);
      if (saveOnChange) saveFile();
      return result;
   }

   
   /**
   * Sets a specific subject/variable combination the given value. If the subject
   * doesn't exist, create it. If the variable doesn't exist, create it.
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   * @param value the value of the variable (e.g. "green")
   * @param addToLines add the information to the lines vector
   * @return true if successful
 * @throws ExceptionZZZ 
   */
   protected boolean addValue(String subject, String variable, String value, boolean addToLines) throws ExceptionZZZ
   {

      //if no subject, quit
      if ((subject == null) || (subject.length()==0)) return false;

      //if no variable, quit
      if ((variable == null) || (variable.length()==0)) return false;

      //if the subject doesn't exist, add it to the end
      
	  // contains was replaced by containsString. See history for details.

      /* FGL 
	  if (!subjects.containsString(subject)) 
      {
		 subjects.addElement(subject);
         variables.addElement(new ExtendedVector());
         values.addElement(new ExtendedVector());
      }
      */
      this.addSection(subject); //FGL 2008-02-19: neue Methode, durch deren Einsatz man auch Sections ohne Wert erstellen kann !!!

      //set the value, if the variable doesn't exist, add it to the end of the subject
      int subjectIndex = subjects.indexOfString(subject,true);

      ExtendedVectorZZZ subjectVariables = (ExtendedVectorZZZ)(variables.elementAt(subjectIndex));
      ExtendedVectorZZZ subjectValues = (ExtendedVectorZZZ)(values.elementAt(subjectIndex));

	  // contains was replaced by containsString. See history for details.

      if (!subjectVariables.containsString(variable))
      {
         subjectVariables.addElement(variable);
         subjectValues.addElement(value);
      }
      int variableIndex = subjectVariables.indexOfString(variable,true);
      subjectValues.setElementAt(value,variableIndex);

      //add it to the lines vector?
      if (addToLines)
         setLine(subject,variable,value);

      return true;
   }

   /**FGL 2008-02-19 F�gt eine Section hinzu, aber nur, wenn sie noch nicht existiert !!!
    * Dadurch wird es auch möglich Sections hinzuzufügen, die keine Werte beinhalten. Diese Sections wurden sonst ignoriert.
* @param sSection
* @return
* 
* lindhauer; 19.02.2008 09:18:15
 */
protected boolean addSection(String sSection){
	   boolean bReturn;
	   if (!subjects.containsString(sSection)) {
			 subjects.addElement(sSection);
	         variables.addElement(new ExtendedVectorZZZ());
	         values.addElement(new ExtendedVectorZZZ());
	         
	         bReturn = true;
	   }else{
		   	bReturn = false;
	   }
	   return bReturn;
}
   
   /**
   * does the line represent a subject?
   * @param line a string representing a line from an INI file
   * @return true if line is a subject
 * @throws ExceptionZZZ 
   */
   protected boolean isaSubject(String line) throws ExceptionZZZ
   {
	   if(line.startsWith(IIniStructureConstantZZZ.sINI_SUBJECT_START)) {
		   //FGL: 20191218: Versehentlich war ein Semikolon hinter dem Subject gelandet. Es wurde daraufhin nicht erkannt.
		   //               Idee: Kommentare und Leerzeichen hinter dem Subject erlauben.
		   String sLineNormed = StringZZZ.left(line+IIniStructureConstantZZZ.sINI_COMMENT, IIniStructureConstantZZZ.sINI_COMMENT);
		   sLineNormed = sLineNormed.trim();
		   return (sLineNormed.startsWith(IIniStructureConstantZZZ.sINI_SUBJECT_START) && sLineNormed.endsWith(IIniStructureConstantZZZ.sINI_SUBJECT_END));
	   }else {
		   return false;
	   }
	   
   }

   /**
   * set a line in the lines vector 
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   * @param value the value of the variable (e.g. "green")
 * @throws ExceptionZZZ 
   */
   protected void setLine(String subject, String variable, String value) throws ExceptionZZZ
   {

      //find the line containing the subject
      int subjectLine = findSubjectLine(subject);
      if (subjectLine == -1)
      {
         addSubjectLine(subject);
         subjectLine = lines.size()-1;
      }

	
      //find the last line of the subject
      int endOfSubject = endOfSubject(subjectLine);
  
	  //find the assignment within the subject
      int lineNumber =  findAssignmentBetween(variable,subjectLine,endOfSubject);

      //if an assignment line doesn't exist, insert one, else change the existing one
      if (lineNumber == -1)
         lines.insertElementAt(variable+"="+value,endOfSubject);
      else
         lines.setElementAt(variable+"="+value,lineNumber);
   }

   /**
   * find the line containing a variable within a subject
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   * @return the line number of the assignment, -1 if not found
 * @throws ExceptionZZZ 
   */
   protected int findAssignmentLine(String subject, String variable) throws ExceptionZZZ
   {
      int start = findSubjectLine(subject);
      int end = endOfSubject(start);
      return findAssignmentBetween(variable,start,end);
   }

   /**
   * find the line containing a variable within a range of lines
   * @param variable the variable name (e.g. "Color")
   * @param start the start of the range (inclusive)
   * @param end the end of the range (exclusive)
   * @return the line number of the assignment, -1 if not found
   */
   protected int findAssignmentBetween(String variable, int start, int end)
   {
      for (int i=start;i<end;i++)
      {
		  // toLowerCase() was inserted because of treading variables case-independent. See history for details.
		  if (((String)lines.elementAt(i)).toLowerCase().startsWith(variable.toLowerCase()+IIniStructureConstantZZZ.sINI_PROPERTY_SEPERATOR))
            return i;
      }
      return -1;
   }

   /**
   * add a subject line to the end of the lines vector
   * @param subject the subject heading (e.g. "Widget Settings")
   */
   protected void addSubjectLine(String subject)
   {
      lines.addElement("["+subject+"]");
   }

   /**
   * find a subject line within the lines vector
   * @param subject the subject heading (e.g. "Widget Settings")
   * @return the line number of the subject, -1 if not found
 * @throws ExceptionZZZ 
   */
   protected int findSubjectLine(String subject) throws ExceptionZZZ
   {
      String line;
      String formattedSubject = IIniStructureConstantZZZ.sINI_SUBJECT_START+subject+IIniStructureConstantZZZ.sINI_SUBJECT_END;
      for (int i=0;i<lines.size();i++)
      {
         line = (String)lines.elementAt(i);
         //FGL: 20211128: Seitdem es moeglich ist Kommentare hinter das Subject zu schreiben ist das ueberholt if (formattedSubject.equalsIgnoreCase(line))  return i;         
  	   if(line.startsWith(IIniStructureConstantZZZ.sINI_SUBJECT_START)) {
		   //FGL: 20191218: Versehentlich war ein Semikolon hinter dem Subject gelandet. Es wurde daraufhin nicht erkannt.
		   //               Idee: Kommentare und Leerzeichen hinter dem Subject erlauben.
		   String sLineNormed = StringZZZ.left(line+IIniStructureConstantZZZ.sINI_COMMENT, IIniStructureConstantZZZ.sINI_COMMENT);
		   sLineNormed = sLineNormed.trim();
		   
		   if (formattedSubject.equals(sLineNormed)) return i;	        
  	   }
         
      }
      return -1;
   }


   /**
   * find the line number which is 1 past the last assignment in a subject
   * starting at a given line
   * @param start the line number at which to start looking
   * @return the line number of the last assignment + 1
 * @throws ExceptionZZZ 
   */
   protected int endOfSubject(int start) throws ExceptionZZZ
   {
      int endIndex = start+1;
      if (start>=lines.size()) return lines.size();
      for (int i=start+1;i<lines.size();i++)
      {
         if (isanAssignment((String)lines.elementAt(i)))
            endIndex = i+1;
         if (isaSubject((String)lines.elementAt(i)))
            return endIndex;
      }
      return endIndex;
   }

   /**
   * does the line represent an assignment?
   * @param line a string representing a line from an INI file
   * @return true if line is an assignment
   */
   protected boolean isanAssignment(String line)
   {
      if ((line.indexOf("=")!=-1) && (!line.startsWith(IIniStructureConstantZZZ.sINI_COMMENT) && ((!line.startsWith(IIniStructureConstantZZZ.sINI_SUBJECT_START)))))
         return true;
      else
         return false;
   }

   /**
   * get a copy of the lines vector
   */
   public ExtendedVectorZZZ getLines()
   {
      return (ExtendedVectorZZZ)lines.clone();
   }

   /**
   * get a vector containing all variables in a subject
   * @param subject the subject heading (e.g. "Widget Settings")
   * @return a list of variables, empty vector if subject not found
   */
   public String[] getVariables(String subject)
   {
      String[] v;
      int index = subjects.indexOfString(subject);
      if (index != -1)
      {
         ExtendedVectorZZZ vars = (ExtendedVectorZZZ)(variables.elementAt(index));
         v = new String[vars.size()];
         vars.copyInto(v);
         return v;
      }
      else
      {
         v = new String[0]; 
         return v;
      }
   }
   
   public String[] getVariables() {
	   String[] saReturn=null;

	   ArrayList<String>listasReturn = new ArrayList<String>();
	   for(int index=0;subjects.size()<=index;index++) {
		   ExtendedVectorZZZ vars = (ExtendedVectorZZZ)(variables.elementAt(index));		   
		   String[] saTemp = (String[]) vars.toArray();
		   
		   for(String stemp : saTemp) {
			   if(!listasReturn.contains(stemp)) {
				   listasReturn.add(stemp);
			   }
		   }		   		   
	   }
	   
	   saReturn = listasReturn.toArray(saReturn);
	   return saReturn;
   }

   /**
   * get an array containing all subjects
   * @return a list of subjects
   */
   public String[] getSubjects()
   {
      String[] s = new String[subjects.size()];
      subjects.copyInto(s);
      return s;
   }

   /**
   * get an array containing all subjects which contains a specified string
   * @return a list of subjects
   */
   public String[] getSubjectsContainsString(String searchPattern)
   {
	  Vector temp = new Vector();
	  String allSubjects[] = getSubjects();
	  for (int i=0; i<allSubjects.length; i++)
		  if (allSubjects[i].indexOf(searchPattern)!=-1)
			temp.addElement(allSubjects[i]);
	  String result[] = new String[temp.size()];
	  temp.copyInto(result);
      return result;
   }

   /**
   * get the value of a variable within a subject
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   * @return the value of the variable (e.g. "green"), empty string if not found
 * @throws ExceptionZZZ 
   */
   public String getValue(String subject, String variable) throws ExceptionZZZ
   {
	   String sReturn = null;       //FGL 20061025 Falls der Wert nicht konfiguriert ist, null zurückgeben
	   main:{
	      int subjectIndex = subjects.indexOfString(subject,true);
	      if (subjectIndex == -1) break main;	       //20190720: null zurückgeben, wenn die Section nicht definiert ist return "";
	      
	      ExtendedVectorZZZ valVector = (ExtendedVectorZZZ)(values.elementAt(subjectIndex));
	      ExtendedVectorZZZ varVector = (ExtendedVectorZZZ)(variables.elementAt(subjectIndex));
	      int valueIndex = varVector.indexOfString(variable,true) ;
	      if (valueIndex != -1)
	      {
	         sReturn = (String)(valVector.elementAt(valueIndex));
	         if(StringZZZ.isEmpty(sReturn)) {
	        	//FGL 20191218: Wenn der Wert Konfiguriert wurde, aber ein Leerstring enthalten soll, dann kann man ihn nur mit diesem "Formelausdruck" erkennen.
		    	sReturn = XmlUtilZZZ.computeTagEmpty();
		    }	    	 
	      }
	   }//end main:
      return sReturn;
   }
   
   /** Lies eine Zeile aus und gib sie als Array zurück. Dabei wird ein Separator String angegeben oder ein Defaultwert genommen.
 * @param subject
 * @param variable
 * @return
 * @author Fritz Lindhauer, 08.04.2020, 08:28:33
 * @throws ExceptionZZZ 
 */
public String[] getValueAsArray(String subject, String variable) throws ExceptionZZZ {
	 String[] saReturn = null;
	   main:{
		   saReturn = this.getValueAsArray(subject, variable, null);
	   }
	   return saReturn;
 }

public String[] getValueAsArray(String subject, String variable, String sSeparatorIn) throws ExceptionZZZ {
	   String[] saReturn = null;
	   main:{
		   String sSeparator;
		   if(StringZZZ.isEmpty(sSeparatorIn)) {
			   sSeparator = IIniStructureConstantZZZ.sINI_MULTIVALUE_SEPARATOR; 
		   }else {
			   sSeparator = sSeparatorIn;
		   }
		   
		   String sValue = this.getValue(subject, variable);
		   saReturn = StringZZZ.explode(sValue, sSeparator);
	   }
	   return saReturn;
}


   /**
   * delete variable within a subject
   * @param subject the subject heading (e.g. "Widget Settings")
   * @param variable the variable name (e.g. "Color")
   */
   public void deleteValue(String subject, String variable) throws IOException,ExceptionZZZ
   {
      int subjectIndex = subjects.indexOfString(subject,true);
      if (subjectIndex == -1)
         return;

      ExtendedVectorZZZ valVector = (ExtendedVectorZZZ)(values.elementAt(subjectIndex));
      ExtendedVectorZZZ varVector = (ExtendedVectorZZZ)(variables.elementAt(subjectIndex));

      int valueIndex = varVector.indexOfString(variable,true) ;
      if (valueIndex != -1)
      {
         //delete from variables and values vectors
         valVector.removeElementAt(valueIndex);
         varVector.removeElementAt(valueIndex);
         //delete from lines vector
         int assignmentLine = findAssignmentLine(subject, variable);
         if (assignmentLine != -1)
         {
            lines.removeElementAt(assignmentLine);
         }
         //if the subject is empty, delete it
         if (varVector.size()==0)
         {
            deleteSubject(subject);
         }
         if (saveOnChange) saveFile();
      }
   }

	   /**
	 * @param subject
	 * @throws IOException
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 10.04.2023, 07:50:27
	 */
	public void createSubject(String subject) throws IOException,ExceptionZZZ
	   {
		//NEIN, Keine Section Klammern hier hierumsetzen, wird später gemacht...
		//String sSubjectNew = IniFile.sINI_SUBJECT_START + subject + IniFile.sINI_SUBJECT_END; 
	
	    this.addSection(subject);
	      
	    if (saveOnChange) saveFile();
	}
   
   
   /**
   * delete a subject and all its variables
   * @param subject the subject heading (e.g. "Widget Settings")
   */
   public void deleteSubject(String subject) throws IOException,ExceptionZZZ
   {
      int subjectIndex = subjects.indexOfString(subject,true);
      if (subjectIndex == -1)
         return;
      //delete from subjects, variables and values vectors
      values.removeElementAt(subjectIndex);
      variables.removeElementAt(subjectIndex);
      subjects.removeElementAt(subjectIndex);
      
      //delete from lines vector
      int start = findSubjectLine(subject);
      int end = endOfSubject(start);
      for (int i=start;i<end;i++)
      {
   		  lines.removeElementAt(i);
      }
      if (saveOnChange) saveFile();
   }


   /**
   * Gets the filename of the current file in use.
   * @return fileName the filename
   */
   public String getFileName() {
	   return fileName;
   }
   
   /**
    * Sets the filename of the current file, used by loadFile() or saveFile() 
   
    @author 0823 , date: 15.11.2004
    @throws IOException
    */
   public void setFileName(String sFileName){
   		this.fileName = sFileName;
   }
   
 

   /**
   * save the lines vector back to the INI file
   */
   public void saveFile() throws IOException,ExceptionZZZ
   {
      try
      {         
         //20190712: Ziel ist es nun UTF-8 Datei zu erstellen
         OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8");          
         for (int i=0;i<lines.size();i++)
         {

			if (i>0) {
				if (((String) lines.elementAt(i)).startsWith("[") && !((String) lines.elementAt(i-1)).equals("")){
					String sLineUTF8 = StringZZZ.toUtf8("\r\n");					
					writer.write(sLineUTF8);			
				}
			}
			String sLineRaw = (String)(lines.elementAt(i))+"\r\n";
			String sLineUTF8 = StringZZZ.toUtf8(sLineRaw);			
			writer.write(sLineUTF8);
         }
         writer.close();
      }
      catch (IOException e)
      {
         System.out.println("IniFile save failed: " + e.getMessage());
         e.printStackTrace();
		 throw e;
	  }
   }
   
   /** 
    * saves the line vector to a file, with the current name
    * FGL 2004-11-15, uses the new setFileName(..) - method and then calls the saveFile() method
    * 
    */
   public void saveFile(String sFileName) throws IOException,ExceptionZZZ
   {
	  this.setFileName(sFileName);	  
	  this.saveFile();
   }

   /**
   * clean up
   */
   protected void finalize() throws IOException
   {
      //saveFile();
   }

public boolean hasSection(String sSection) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		StringArrayZZZ saZZZ = new StringArrayZZZ(this.getSubjects());
		bReturn = saZZZ.contains(sSection);
	}
	return bReturn;
}   
}



