package basic.zBasic.util.stream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.EnumSet;

import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/** Erweiterung der Stream-Klasse um das zu verwendende CharacterSet / Encoding
 *  Klasse: Charset ALS ZWINGEND NOTWENDIG.
 * 
 *  Hintergrund:
 *  Beim Arbeiten mit DateiUtil.java (entstanden aus Datei.java vom Buch "Kryptografie mit Java"
 *  ist mir aufgefallen, dass heutzutage es problematisch ist OHNE Encoding - Angabe einen Stream zu öffnen.
 *  
 *  siehe:
 *  https://stackoverflow.com/questions/696626/java-filereader-encoding-issue
Yes, you need to specify the encoding of the file you want to read.
Yes, this means that you have to know the encoding of the file you want to read.
No, there is no general way to guess the encoding of any given "plain text" file.
The one-arguments constructors of FileReader always use the platform default encoding which is generally a bad idea.

Since Java 11 FileReader has also gained constructors that accept an encoding: new FileReader(file, charset) and new FileReader(fileName, charset).
In earlier versions of java, you need to use new InputStreamReader(new FileInputStream(pathToFile), <encoding>).
       
 * Die definierten Standard charsets stehen hier:
 * https://docs.oracle.com/javase/7/docs/api/java/nio/charset/Charset.html
 * Merke: "ANSI" ist also kein passender Typ.
 
 * Beispiele für den Charsetname:
 * "ISO-8859-1"
 * 
 * @author Fritz Lindhauer, 09.10.2022, 09:05:49
 * 
 */
public class StreamZZZ extends Stream implements IStreamZZZ, Serializable{
	private static final long serialVersionUID = -3561557197050804231L;
	protected ExceptionZZZ objException = null;
	
	public enum CharsetUsedZZZ implements IEnumSetMappedZZZ, IEnumSetZZZ{ //Folgendes geht nicht, da alle Enums schon von einer Java BasisKlasse erben... extends EnumSetMappedBaseZZZ{
		ANSI("ISO-8859-1","used for ANSI files"),
		UTF8("UTF-8","uses for UTF files");
				
		private String descr, abbr;

		//#############################################
		//#### Konstruktoren
		//Merke: Enums haben keinen public Konstruktor, können also nicht intiantiiert werden, z.B. durch Java-Reflektion.
		//In der Util-Klasse habe ich aber einen Workaround gefunden.
		CharsetUsedZZZ(String abbr, String descr) {
		    this.abbr = abbr;
		    this.descr = descr;
		}


		//the identifierMethod ---> Going in DB
		@Override
		public String getAbbreviation() {
		 return this.abbr;
		}
		
		@Override
		public EnumSet<?>getEnumSetUsed(){
			return CharsetUsedZZZ.getEnumSet();
		}

		/* Die in dieser Methode verwendete Klasse für den ...TypeZZZ muss immer angepasst werden. */
		@SuppressWarnings("rawtypes")
		public static <E> EnumSet getEnumSet() {
			
		 //Merke: Das wird anders behandelt als FLAGZ Enumeration.
			//String sFilterName = "FLAGZ"; /
			//...
			//ArrayList<Class<?>> listEmbedded = ReflectClassZZZ.getEmbeddedClasses(this.getClass(), sFilterName);
			
			//Erstelle nun ein EnumSet, speziell für diese Klasse, basierend auf  allen Enumrations  dieser Klasse.
			Class<CharsetUsedZZZ> enumClass = CharsetUsedZZZ.class;
			EnumSet<CharsetUsedZZZ> set = EnumSet.noneOf(enumClass);//Erstelle ein leeres EnumSet
			
			 Enum[]objaEnum = (Enum[]) enumClass.getEnumConstants();
			 for(Object obj : objaEnum){
				//System.out.println(obj + "; "+obj.getClass().getName());
				set.add((CharsetUsedZZZ) obj);
			}
			return set;
			
		}

		//TODO: Mal ausprobieren was das bringt
		//Convert Enumeration to a Set/List
		private static <E extends Enum<E>>EnumSet<E> toEnumSet(Class<E> enumClass,long vector){
			  EnumSet<E> set=EnumSet.noneOf(enumClass);
			  long mask=1;
			  for (  E e : enumClass.getEnumConstants()) {
			    if ((mask & vector) == mask) {
			      set.add(e);
			    }
			    mask<<=1;
			  }
			  return set;
			}

		//+++ Das könnte auch in einer Utility-Klasse sein.
		//the valueOfMethod <--- Translating from DB
		public static CharsetUsedZZZ fromAbbreviation(String s) {
		for (CharsetUsedZZZ state : values()) {
		   if (s.equals(state.getAbbreviation()))
		       return state;
		}
		throw new IllegalArgumentException("Not a correct abbreviation: " + s);
		}

		//##################################################
		//#### Folgende Methoden bring Enumeration von Hause aus mit. 
				//Merke: Diese Methoden können aber nicht in eine abstrakte Klasse verschoben werden, zum daraus Erben. Grund: Enum erweitert schon eine Klasse.
		@Override
		public String getName() {	
			return super.name();
		}

		@Override
		public String toString() {
		    //return this.fullName+"="+this.abbr+"#";
			return this.abbr+"#";
		}

		@Override
		public int getIndex() {
			return ordinal();
		}

		//### Folgende Methoden sind zum komfortablen Arbeiten gedacht.
		@Override
		public int getPosition() {
			return getIndex()+1; 
		}

		@Override
		public String getDescription() {
			return this.descr;
		}
		//+++++++++++++++++++++++++
	}//End internal Class
	
	//In dieser erbenden Klasse das Encoding für das Einlesen/Schreiben beachten...
	protected String sCharsetName=null;
	
	public StreamZZZ() {
		super();
	}
	public StreamZZZ(String filename, int how) throws FileNotFoundException, IOException, Exception {
		super(filename, how);
	}
	public StreamZZZ(InputStream i) throws Exception {
		super(i);
	}
	public StreamZZZ(String filename, int how, String sCharsetName) throws Exception {
		super();
		StreamNew_(null, filename, how, sCharsetName);
	  }
	public StreamZZZ(InputStream i,String sCharsetName) throws Exception {
		super();
		StreamNew_(i, null, 0, sCharsetName);
	}
	
	public String getCharsetName() {
		return this.sCharsetName;
	}
	public void setCharsetName(String sCharsetName) {
		this.sCharsetName = sCharsetName;
	}
	
	private boolean StreamNew_(InputStream i, String filename, int ihow, String sCharsetName) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sCharsetName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "CharsetName is not available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				this.setExceptionObject(ez);
				throw ez;
			}
			this.setCharsetName(sCharsetName);	
			
			if(i!=null) {
				in = this.open(i);//in ist protected in der Elternklasse
			
			}else {
				if(StringZZZ.isEmpty(filename)) {
					ExceptionZZZ ez = new ExceptionZZZ( "filename is not available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					this.setExceptionObject(ez);
					throw ez;
				}
				
				switch(ihow) {
				  case READ: 
						in = this.open(filename);
						break;
				  case WRITE: 
					  out = this.create(filename); 
					  break;
				  default:
					  ExceptionZZZ ez = new ExceptionZZZ( "ihow = "+ihow+" is not handled.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
					  this.setExceptionObject(ez);
				      throw ez; 						  
				} 
			}	
			bReturn = true;
		}
		return bReturn;
	}
	
	

    public BufferedReader open(InputStream in) throws ExceptionZZZ {
    	//return new BufferedReader(new InputStreamReader(in));
    	
    	BufferedReader br = null;    	
    	main:{
	    	String sCharsetName = this.getCharsetName();
	    	if(StringZZZ.isEmpty(sCharsetName)) {
			try {
				InputStreamReader fr = new InputStreamReader(in,sCharsetName);
		    	br = new BufferedReader(fr);
			} catch (IOException e) {
				e.printStackTrace();
				
				ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
	  			this.setExceptionObject(ez);
	  			throw ez; 
			} 
		  }else { 
			  br = new BufferedReader(new InputStreamReader(in));
		  }
	    	
	    }//end main:
        return br;
      }

      public BufferedReader open(String filename) throws ExceptionZZZ {
    	//return new BufferedReader(new FileReader(filename));
    	  
    	BufferedReader br = null;
      
  		try {      	
  	    	//Also nicht einfach so: FileReader fr = new FileReader(sPath);
  	       
  	    	String sCharsetName = this.getCharsetName();
  	    	if(StringZZZ.isEmpty(sCharsetName)) {
	    		try {
					br = super.open(filename);
				} catch (IOException e) {
	    			e.printStackTrace();
	    			
	    			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		  			this.setExceptionObject(ez);
		  			throw ez; 
				} catch (Exception e) {
					e.printStackTrace();
	    			
	    			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		  			this.setExceptionObject(ez);
		  			throw ez; 
				}
	    	  }else {
	  	    	//Geht erst ab Java 11: FileReader fr = new FileReader(filename, sCharsetName);  	    	
	  	    	InputStreamReader fr = new InputStreamReader(new FileInputStream(filename), sCharsetName);
	  	    	br = new BufferedReader(fr);
	    	  }
  		} catch (UnsupportedEncodingException e) {
  			e.printStackTrace();
  			
  			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			this.setExceptionObject(ez);
			throw ez; 
  		} catch (FileNotFoundException e) {
  			e.printStackTrace();
  			
  			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			this.setExceptionObject(ez);
			throw ez; 
  		}
        return br;
      }

      public PrintWriter create(String filename) throws ExceptionZZZ {
    	//return new PrintWriter(new FileWriter(filename));
    	  PrintWriter pw = null;
    	  try {
	    	  String sCharsetName = this.getCharsetName();
	    	  if(StringZZZ.isEmpty(sCharsetName)) {
	    		try {
					pw = super.create(filename);
				} catch (IOException e) {
	    			e.printStackTrace();
	    			
	    			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		  			this.setExceptionObject(ez);
		  			throw ez; 
				} catch (Exception e) {
					e.printStackTrace();
	    			
	    			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		  			this.setExceptionObject(ez);
		  			throw ez; 
				}
	    	  }else {
	    	  
		    	  //Z.B.: BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))
		    	  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, true), sCharsetName));
		    	  pw = new PrintWriter(bw);
	    	  }
    	  } catch (IOException e) {
    			e.printStackTrace();
    			
    			ExceptionZZZ ez = new ExceptionZZZ(e.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
	  			this.setExceptionObject(ez);
	  			throw ez; 
    	  }
    	  return pw;
      }
      
  	//### aus IObjectZZZ
	//Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
	
	//### aus ObjectWithExcetionZZZ
	@Override
	public ExceptionZZZ getExceptionObject() {
	  return this.objException;
	}
	@Override
	public void setExceptionObject(ExceptionZZZ objException) {
		this.objException = objException;
	}
	
	
	//#############
	//Neue Komfort-Methoden, die es in der Elternklasse nicht gibt, bzw. Exceptions abfaengt.
	@Override
	public String readLineNext() throws ExceptionZZZ{
		String sReturn=null;
		try {
			sReturn = super.readLine();
		}catch(EOFException e) {	
			sReturn = null;
		}catch(IOException ioe) {
			ioe.printStackTrace();
			
			ExceptionZZZ ez = new ExceptionZZZ(ioe.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
  			this.setExceptionObject(ez);
  			throw ez; 
		}
		return sReturn;
	}
	
	@Override
	public String readLineLast() throws ExceptionZZZ {
		String sReturn=null; String stemp = null;
		do {
			sReturn = stemp;//rette die vorherige Zeile
			stemp = this.readLineNext();							
		}while(stemp!=null);		
		return sReturn;
	}
	
	
	
}
