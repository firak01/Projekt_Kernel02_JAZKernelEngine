package basic.zBasic;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.StringTokenizer;

import basic.zBasic.reflection.ReflectCodeZZZ;

/** This class handles every exception in the Z-Kernel-Framework.
 * TODO Mapping-Methode, in der aus einem ErrorCode der zugeh�rige Fehelrtext geholt wird. Dieser Fehlertext wird dann vor der �bergebenen Meldung gesetzt.
 * @author 0823
 */
public class ExceptionZZZ extends Exception implements  IConstantZZZ, Serializable {
	private static final long serialVersionUID = -6539082726335203435L;
	//#################################################################
	 //private Variablen
	 String[] saMessage;
	 String[] saFunction;
	 int[] iaCode;
	
	 //MERKE: Jeden Konstruktor gibt es auch mit der Exception e - Variante am Ende.
	 
 /*TODO: Ein Konstruktor, dem ein Array �bergeben werden kann.
  * Dann kann zus�tzlich zu dem Z-Kernel Error - Hinweis noch der exception.getMessage() Hinweis eines anderen Objekts hinzugef�gt werden
  * 
  * TODO: Ein Konstruktor, dem ein ExceptionZZZ-Objekt mitgegeben werden kann. Und ein Array/bzw. ein String.
  * Dann kann eine Aufeinanderfolge von Exception-Strings generiert werden.
  * 
  *TODO: Eine Methode, mit der die Exception-Strings in das XML-Format gebracht werden k�nnen (s. LotusScript-Z-Kernel)
  *
  */ 
  
  //#################################################################
  //Constructor
 public ExceptionZZZ(){
	super();
	
 	String sFunctionCurrent=null;
	try {
		sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	
	String sClassName = null;
	try {
		sClassName = ReflectCodeZZZ.getClassCallingName();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
 	ExceptionNew_(sERROR_RUNTIME, iERROR_RUNTIME, null, sClassName, sFunctionCurrent, null);
 }
 
 public ExceptionZZZ(Exception e){
		super(e);
		
	 	String sFunctionCurrent=null;
		try {
			sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
		
		String sClassName = null;
		try {
			sClassName = ReflectCodeZZZ.getClassCallingName();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
	 	ExceptionNew_(sERROR_RUNTIME, iERROR_RUNTIME, null, sClassName, sFunctionCurrent, null);
	 }
 //##################################################################################################
 
 
 /**
  * @param Errormessage
  * @param FlagControl
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage){
	super(sMessage); 	
 	String sFunctionCurrent=null;
	try {
		sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	
	String sClassName = null;
	try {
		sClassName = ReflectCodeZZZ.getClassCallingName();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
 	ExceptionNew_(sMessage, iERROR_RUNTIME, null, sClassName, sFunctionCurrent, null);
 }
 
 

 
 /**
  * @param Errormessage
  * @param FlagControl
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage, Exception e){
	super(sMessage, e); 	
 	String sFunctionCurrent=null;
	try {
		sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	
	String sClassName = null;
	try {
		sClassName = ReflectCodeZZZ.getClassCallingName();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
 	ExceptionNew_(sMessage, iERROR_RUNTIME, null, sClassName, sFunctionCurrent, e);
 }
 
 //#####################################################################################################
 /**
  * @param Errormessage
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage, int iErrorCode){
	 	super(sMessage);
	 	String sFunctionCurrent=null;
		try {
			sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
		
		String sClassName = null;
		try {
			sClassName = ReflectCodeZZZ.getClassCallingName();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
	 	ExceptionNew_(sMessage, iErrorCode, null, sClassName, sFunctionCurrent, null);
 }
 
 public ExceptionZZZ(String sMessage, int iErrorCode, Object objCurrent){
	 	super(sMessage);
	 	String sFunctionCurrent=null;
		try {
			sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}

		String sClassName = null;
		if(objCurrent==null) {
			try {
				sClassName = ReflectCodeZZZ.getClassCallingName();
			} catch (ExceptionZZZ e1) {		
				e1.printStackTrace();
			}
		}else {
			sClassName = objCurrent.getClass().getName();
		}
	 	ExceptionNew_(sMessage, iErrorCode, null, sClassName, sFunctionCurrent, null);
}
 
 /**
  * @param Errormessage
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage, int iErrorCode, Exception e){
	 	super(sMessage, e);
	 	String sFunctionCurrent=null;
		try {
			sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
		
		String sClassName = null;
		try {
			sClassName = ReflectCodeZZZ.getClassCallingName();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
	 	ExceptionNew_(sMessage, iErrorCode, null, sClassName, sFunctionCurrent, e);
 }

 
 
 
 //##########################################################################################
  /**
  * @param Errormessage
  * @param Array of control flags
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage, int iErrorCode, String sFunction){
	 super(sMessage);
	 String sClassName = null;
		try {
			sClassName = ReflectCodeZZZ.getClassCallingName();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
	 ExceptionNew_(sMessage, iErrorCode, null, sClassName, sFunction, null);
 }
 
 /**
  * @param Errormessage
  * @param Array of control flags
  * 
  * internally the errorcode 101 will be set.
  */
 public ExceptionZZZ(String sMessage, int iErrorCode, String sFunction, Exception e){
	 super(sMessage, e);
	 String sClassName = null;
		try {
			sClassName = ReflectCodeZZZ.getClassCallingName();
		} catch (ExceptionZZZ e1) {		
			e1.printStackTrace();
		}
	 ExceptionNew_(sMessage, iErrorCode, null, sClassName, sFunction, e);
 }
 
 //###########################################################################################
 
 
public ExceptionZZZ(String sMessage, int iCode, Object objCurrent,String sFunctionCurrent){
	super(sMessage);
	String sClassName = null;
	try {
		sClassName = ReflectCodeZZZ.getClassCallingName();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	ExceptionNew_(sMessage, iCode, objCurrent, sClassName, sFunctionCurrent, null);
}

public ExceptionZZZ(String sMessage, int iCode, Object objCurrent,String sFunctionCurrent,Exception e){
	super(sMessage,e);
	String sClassName = null;
	try {
		sClassName = ReflectCodeZZZ.getClassCallingName();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	ExceptionNew_(sMessage, iCode, objCurrent, sClassName, sFunctionCurrent,e);
}
//#################################################################################################

public ExceptionZZZ(String sMessage, int iCode, String sClassName,String sFunctionCurrent){
	super(sMessage);
	ExceptionNew_(sMessage, iCode, null, sClassName, sFunctionCurrent, null);
}

public ExceptionZZZ(String sMessage, int iCode, String sClassName,String sFunctionCurrent, Exception e){
	super(sMessage, e);
	ExceptionNew_(sMessage, iCode, null, sClassName, sFunctionCurrent, e);
}

//#################################################################################################


/**
 * Only use this in a JDK 1.4 or above environment !!!
 * @param Errormessage
 * @param Errorcode
 * @param the current object (this)
 * @param Exception
 * @param FlagControl
 * 
 * from the current object the classname will be extracted and from the exception the last executed methodname will be used.
 * 
 * 		   //doesn�t work. Only works when > JDK 1.4
			   //Exception e = new Exception();
			   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
 */
public ExceptionZZZ(String sMessage, int iCode, Object objCurrent, Exception e){
	super(sMessage, e);
	String sFunctionCurrent=null;
	try {
		sFunctionCurrent = ReflectCodeZZZ.getClassMethodCallingString();
	} catch (ExceptionZZZ e1) {		
		e1.printStackTrace();
	}
	ExceptionNew_(sMessage, iCode, objCurrent, null, sFunctionCurrent, e);			
}
//#################################################################################################


private void ExceptionNew_(String sInfoin, int iCode, Object object, String sClassname, String sFunctionCurrentIn, Exception e){
	
	String sInfo = null;
	if(sInfoin==null){
		sInfo = "No ZKernel Message, but... ";
	}else{
		//TODO: In Abh�ngigkeit von dem standardisierten Code, soll der Message ein standardiesierter Text vorangestellt werden.
		sInfo = sInfoin;
	}
	
	if(e!=null) {
		sInfo = sInfo + ": " + e.getMessage();		
	}else {
		//TODO: Falls ein Objekt die Methode setExceptionObject() hat, dann soll die Exception sich selbst dem Object hinzuf�gen.		
	}
	
	String sFunctionCalling=null;
	if(sFunctionCurrentIn==null & e == null){
		sFunctionCalling = "No function provided";
	}else if (sFunctionCurrentIn != null & e == null){
		sFunctionCalling = sFunctionCurrentIn;
	}else if (sFunctionCurrentIn != null & e != null) {
		sFunctionCalling = e.getStackTrace()[0].getMethodName() + "(" + sFunctionCurrentIn + ")";
	} else if(sFunctionCurrentIn == null & e != null){
		sFunctionCalling = e.getStackTrace()[0].getMethodName();
	}
	
	if(object!=null){
		sFunctionCalling = object.getClass() + "/" + sFunctionCalling;
	}else if(sClassname!=null) {
		sFunctionCalling = sClassname + "/" + sFunctionCalling;
	}
	
	
	
	if (this.saMessage == null){
	   this.saMessage = new String[1];
	   this.iaCode = new int[1];
	   this.saFunction = new String[1];
	}
 	
 	//todo: Das Array erweitern !!!!
 	//Das geht nicht so einfach, vermutlich ein neues Array erzeugen + 1 Eintrag und anschliessend Werte reinkopieren
 	
 	this.iaCode[this.iaCode.length - 1] = iCode;
 	this.saMessage[this.saMessage.length - 1] = sInfo;
 	this.saFunction[this.saFunction.length - 1] = sFunctionCalling;
}
  
public static String computeStringFromStackTrace(Throwable t){
	String sReturn = null;
	main:{
		if(t == null) break main;
		
		
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintWriter pw = new PrintWriter(baos);
		  t.printStackTrace(pw);
		  pw.flush();
		  sReturn = baos.toString();
		  pw.close();
	}
	return sReturn;
}

public static String computeHtmlFromStackTrace(Throwable t){
	String sReturn = "";
	main:{
		if(t == null) break main;
		
	  String sTrace = ExceptionZZZ.computeStringFromStackTrace(t);
	  StringTokenizer tok = new StringTokenizer(sTrace, "\n");
	  while(tok.hasMoreTokens()){
		  sReturn += (String) tok.nextToken() + "<br>";
	  }
	  
	}//end main:
	return sReturn;
}

//#################  GETTER / SETTER ###############################################
/**
	 * @param the index position iIndex
	 * 
	 * @return the message with index position iIndex
	 */
 public String getMessage(int iIndex){
 	return this.saMessage[iIndex]; 
	}
			
	/**
	 * @return all messages
	 */
	public String[] getMessageAll(){
		return this.saMessage;
	}
	
/**
 * @return last reported errormessage
 */
	public String getMessageLast(){
		String sFunction = this.saMessage[this.saMessage.length - 1];
		return sFunction;		
	}
	
	/** 
	 * @return last reporting function plus last reported errormessage
	 */
	public String getDetailAllLast(){
		String sFunction =  this.saMessage[this.saMessage.length - 1] + " # " + this.saFunction[this.saFunction.length - 1];
		return sFunction;
	}
	
	/**
 * @return last reported function
 */
	public String getFunctionLast(){
		String sFunction = null;
		sFunction = this.saFunction[this.saFunction.length - 1];
		return sFunction;
	}
	
	
}  // end class KernelExceptionZZZ