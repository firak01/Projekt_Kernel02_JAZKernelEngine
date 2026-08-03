package basic.zBasic.util.datatype.string;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.crypt.code.Vigenere256ZZZ;
import basic.zBasic.util.crypt.code.Vigenere26ZZZ;
import basic.zBasic.util.crypt.code.Vigenere96ZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;

/**Internally in Java all strings are kept in Unicode. 
 * Since not all text received from users or the outside world is in unicode, 
 * your application may have to convert from non-unicode to unicode. 
 * Additionally, when the application outputs text it may have to convert the internal unicode format 
 * to whatever format the outside world needs.

    Java has a few different methods you can use to convert text to and from unicode. These methods are:

    The String class
    The Reader and Writer classes and subclasses


First of all I would like to clarify that Unicode consist of a set of "code points" 
which are basically a numerical value that corresponds to a given character. 
There are several ways to "encode" these code points (numerical values) into bytes. 
The two most common ones are UTF-8 and UTF-16. In this tutorial I will only show examples of converting 
to UTF-8 - since this seems to be the most commonly used Unicode encoding.

 * 
 * @author Fritz Lindhauer, 15.11.2022, 08:32:28
 * 
 */
public class UnicodeZZZ implements IConstantZZZ{
	
	private UnicodeZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only
	

	
	/** 
	 * You can also write unicode characters directly in strings in the code, by escaping the with "\\u". (only 1 backslash, the second backslash her is for escaping the unicode)
	 * Here is an example:
	 * The danish letters Æ Ø Å
	 * String myString = "\u00C6\u00D8\u00C5" ;
	 * 
	 * @param c
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:28:35
	 */
	public static String from(char c) {
		String sReturn = null;
		main:{			
			if(CharZZZ.isEmptyNull(c)) break main;			
			sReturn = "\\u" + Integer.toHexString('÷' | 0x10000).substring(1);
		}//end main:
		return sReturn;
	}
	
	public static String from(String s) {
		String sReturn = "";
		main:{
			if(s==null)break main;
			if(s=="") {
				sReturn = null;
				break main;
			}
			
			String stemp;
			char[]ca=s.toCharArray();
			for(char c : ca) {
				stemp = UnicodeZZZ.from(c);
				sReturn += stemp;
			}
		}//end main:
		return sReturn;
	}
	
	/** Converting to and from Unicode UTF-8 Using the String Class

		You can use the String class to convert a byte array to a String instance. 
		You do so using the constructor of the String class. 
				
		This example first creates a byte array. 
		The byte array does not actually contain any sensible data, but for the sake of the example, 
		that does not matter. 
		The example then creates a new String, passing the byte array and the character set of the characters in the byte array as parameters to the constructor. 
		The String constructor will then convert the bytes from the character set of the byte array to unicode.
		
		
		Siehe auch StringZZZ.toUtf8(string)
	 * @param ba
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:38:36
	 */
	public static String from(byte[] bytea) {
		String sReturn = "";
		main:{
			if(bytea==null) {
				sReturn=null;
				break main;
			}
			if(bytea.length==0) {
				break main;
			}
			
			sReturn = new String(bytea, Charset.forName("UTF-8"));
		}//end main:
		return sReturn;
	}
	
	/** You can convert the text of a String to another format using the getBytes() method. 
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:44:16
	 */
	public static byte[] toByteArray(String s) {
		byte[] bytea = s.getBytes(Charset.forName("UTF-8"));
		return bytea;
	}
	
	public static int[] toIntArray(String s) {				
		byte[]bytea = UnicodeZZZ.toByteArray(s);
		int[]iaReturn = UnicodeZZZ.fromByteToInt(bytea);
		return iaReturn;
	}
	
	/**Gib für jeden Buchstaben des Strings die Indexpositionen in der ArrayList zurück.
	 * 
	 * @param s
	 * @param listasCharacterPool
	 * @return
	 * @author Fritz Lindhauer, 23.12.2022, 10:00:47
	 * @throws ExceptionZZZ 
	 */
	public static int[] toIntArrayCharacterPoolPosition(String s, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		int iaReturn[]=null;
		main:{
			if(StringZZZ.isEmpty(s)) break main;
			if(listasCharacterPool==null)break main;
			
			iaReturn = new int[s.length()];
						
			for (int i = 0; i < s.length(); i++) {
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(s.charAt(i));
				int iFound = listasCharacterPool.getIndex(objChar);	
				
				if(iFound>=0) {
					iaReturn[i]=iFound;
				}else {
					ExceptionZZZ ez = new ExceptionZZZ("Character of String '" + objChar.toString() + "' not in CharacterPool: '" + listasCharacterPool.debugString("|") + "'", iERROR_PARAMETER_VALUE,   UnicodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());								  
					throw ez;
				}
		    }
			
			
		}//end main:
		return iaReturn;
	}
	
	/**Gib für jeden Buchstaben des Strings die Indexpositionen in der ArrayList zurück.
	 * 
	 * @param s
	 * @param listasCharacterPool
	 * @return
	 * @author Fritz Lindhauer, 23.12.2022, 10:00:47
	 * @throws ExceptionZZZ 
	 */
	public static int[] toIntArrayCharacterPoolPosition(String s, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, CharacterExtendedZZZ objCharMissingReplacement) throws ExceptionZZZ {
		int iaReturn[]=null;
		main:{
			if(StringZZZ.isEmpty(s)) break main;
			if(listasCharacterPool==null)break main;
			
			iaReturn = new int[s.length()];
						
			for (int i = 0; i < s.length(); i++) {
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(s.charAt(i));
				int iFound = listasCharacterPool.getIndex(objChar);	
				
				if(iFound>=0) {
					iaReturn[i]=iFound;
				}else {
					if(objCharMissingReplacement!=null) {
						char c = objCharMissingReplacement.getChar();
						iaReturn[i]=c;
					}else {
						ExceptionZZZ ez = new ExceptionZZZ("Character of String '" + objChar.toString() + "' not in CharacterPool: '" + listasCharacterPool.debugString("|") + "'", iERROR_PARAMETER_VALUE,   UnicodeZZZ.class, ReflectCodeZZZ.getMethodCurrentName());								  
						throw ez;
					}
				}
		    }
			
			
		}//end main:
		return iaReturn;
	}
	
	/**
	 * Converting to and from Unicode UTF-8 Using the Reader and Writer Classes
	 * The Reader and Writer classes are stream oriented classes 
	 * that enable a Java application to read and write streams of characters. 
	 
	 * Here is an example that uses an InputStreamReader to convert from a certain character set (UTF-8) to unicode.
	 * This example creates a FileInputStream and wraps it in a InputStreamReader. 
	 * The InputStreamReader is told to interprete the characters in the file as UTF-8 characters. 
	 * This is done using the second constructor paramter in the InputStreamReader class.
	 * 
	 * @param sFilepath, e.g.: "c:\\data\\utf-8-text.txt"
	 * @return
	 * @author Fritz Lindhauer, 15.11.2022, 08:56:56
	 */
	public static String fileToUtf8 (String sFilepath) throws ExceptionZZZ{
		String sReturn="";
		main:{
			if(StringZZZ.isEmpty(sFilepath)) {
				sReturn = null;
				break main;
			}
			
			try {
			
				InputStream inputStream = new FileInputStream(sFilepath);
				Reader      reader      = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

				int data = reader.read();
				while(data != -1){
					char theChar = (char) data;
					sReturn += theChar;
					
					data = reader.read();				
				}
				reader.close();
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
			
		}//end main:
		return sReturn;
	}
	
	/**
	 * Here is an example writing a stream of characters back out to UTF-8.
	 * This example creates an OutputStreamWriter which converts the string written through it to the UTF-8 character set.
	 * @author Fritz Lindhauer, 15.11.2022, 09:09:16
	 */
	public static boolean writeUtf8ToFile(String s, String sFilepath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(StringZZZ.isEmpty(sFilepath)) break main;
			
			try {
				OutputStream outputStream = new FileOutputStream(sFilepath);			
				Writer       writer       = new OutputStreamWriter(outputStream, Charset.forName("UTF-8"));
				if(s!=null) writer.write(s);			
				writer.close();
				
				bReturn = true;
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return bReturn;
	}
	
	public static boolean writeUtf8ToFile(byte[]ba, String sFilepath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(ba==null) break main;
			if(ba.length==0)break main;
			
			try {
				 //Besser das Encoding mitgeben
			      int[] ia = new int[ba.length];
			      for (int i=0; i<ba.length; i++) ia[i] = (int)ba[i];
			      
			      			      
			      File objFile = new File(sFilepath);
			      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("ISO-8859-1"));//, EnumSet.of(CREATE_NEW));
			      BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("UTF-8"));//, EnumSet.of(CREATE_NEW));
			      for (int i=0;i<ba.length;i++) {
			    	  char c = (char)ia[i];
			          objWriter.write(c);
			      }			
			      objWriter.close();
				
				bReturn = true;
			} catch (FileNotFoundException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return bReturn;
	}
	
	public static File writeAnsiToFile(int[]ia, String sFilepath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(ia==null) break main;
			if(ia.length==0)break main;
			
			int iPosition=-1;
			try {	      
		      objReturn = new File(sFilepath);
		      //gibt "unsupported Mapping" ggfs. als Fehler, wenn in dem String etwas anderes als ANSI steht		      
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("cp1252"), StandardOpenOption.CREATE);
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("ISO-8859-1"));//, EnumSet.of(CREATE));		     
		      BufferedWriter objWriter = Files.newBufferedWriter(objReturn.toPath(), Charset.forName("windows-1252"), StandardOpenOption.CREATE);		     
		      for (int i=0;i<ia.length;i++) {
		    	  iPosition=i;
		    	  //if (i==9)break; //FGL: UnmappableCharacterException vermeiden beim Debug, austesten?
		    	  if(ia[i]>127 || ia[i]<0) {
		    		  
		    	  }else {
		    		char c = (char) ia[i];
		         	objWriter.write(c);
		    	  }
		          
		      }			
		      objWriter.close();
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				System.out.println("IOException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	public static File writeAnsiToFileUsingPoolPosition(int[]ia, String sFilepath, ArrayListZZZ<CharacterExtendedZZZ>listasCharacterPool) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(ia==null) break main;
			if(ia.length==0)break main;
			
			int iPosition=-1;
			try {	      
		      objReturn = new File(sFilepath);
		      //gibt "unsupported Mapping" ggfs. als Fehler, wenn in dem String etwas anderes als ANSI steht		      
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("cp1252"), StandardOpenOption.CREATE);
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("ISO-8859-1"));//, EnumSet.of(CREATE));		     
		      BufferedWriter objWriter = Files.newBufferedWriter(objReturn.toPath(), Charset.forName("windows-1252"), StandardOpenOption.CREATE);		     
		      for (int i=0;i<ia.length;i++) {
		    	  iPosition=i;
		    	  CharacterExtendedZZZ objChar = listasCharacterPool.get(ia[i]);
		          objWriter.write(objChar.getChar());		    	  
		      }			
		      objWriter.close();
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				System.out.println("IOException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	
	public static File writeUtf8ToFile(int[]ia, String sFilepath) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(ia==null) break main;
			if(ia.length==0)break main;
			
			int iPosition=-1;
			try {	      
		      objReturn = new File(sFilepath);
		      //gibt "unsupported Mapping" ggfs. als Fehler.
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("cp1252"), StandardOpenOption.CREATE);
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("windows-1252"), StandardOpenOption.CREATE);		     
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("ISO-8859-1"));//, EnumSet.of(CREATE));

		      BufferedWriter objWriter = Files.newBufferedWriter(objReturn.toPath(), Charset.forName("UTF-8"));//, EnumSet.of(CREATE));
		      for (int i=0;i<ia.length;i++) {
		    	  iPosition=i;
		          objWriter.write(ia[i]);		        
		      }			
		      objWriter.close();
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				System.out.println("IOException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	public static File writeUtf8ToFileUsingPoolPosition(int[]ia, String sFilepath, ArrayListZZZ<CharacterExtendedZZZ>listasCharacterPool) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			if(ia==null) break main;
			if(ia.length==0)break main;
			
			int iPosition=-1;
			try {	      
		      objReturn = new File(sFilepath);
		      //gibt "unsupported Mapping" ggfs. als Fehler.
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("cp1252"), StandardOpenOption.CREATE);
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("windows-1252"), StandardOpenOption.CREATE);		     
		      //BufferedWriter objWriter = Files.newBufferedWriter(objFile.toPath(), Charset.forName("ISO-8859-1"));//, EnumSet.of(CREATE));

		      BufferedWriter objWriter = Files.newBufferedWriter(objReturn.toPath(), Charset.forName("UTF-8"));//, EnumSet.of(CREATE));
		      for (int i=0;i<ia.length;i++) {
		    	  iPosition=i;
		    	  CharacterExtendedZZZ objChar = listasCharacterPool.get(ia[i]);
		          objWriter.write(objChar.getChar());		        
		      }			
		      objWriter.close();
			} catch (FileNotFoundException e) {
				System.out.println("FileNotFoundException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			} catch (IOException e) {
				System.out.println("IOException: Character at position: '" + iPosition + "' - " + e.toString());
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return objReturn;
	}
	
	
	
	
	//###########################################################################################	
	public static int[] fromAsciiToUtf8For26(int[] ia) {
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");	    	
	    	iaPure[i] = UnicodeZZZ.fromAsciiToUtf8(ia[i],Vigenere26ZZZ.iOffsetForUtf8Range);	    	
	    }	
	    System.out.print("\n");
	    return iaPure;   		
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++
	public static int[] fromUtf8ToAsciiFor26(int[] ia) {
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");
	    	iaPure[i] = UnicodeZZZ.fromUtf8ToAscii(ia[i],Vigenere26ZZZ.iOffsetForUtf8Range);	    	
	    }	    
	    System.out.println("\n");
	    return iaPure;   		
	}
	
	
	//##########################################################
	public static int[] fromAsciiToUtf8For96(int[] ia) {	    
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");
	    	iaPure[i] = UnicodeZZZ.fromAsciiToUtf8(ia[i],Vigenere96ZZZ.iOffsetForUtf8Range);	    	
	    }	    
	    System.out.println("\n");
		return iaPure;   		
	}
		
	//++++++++++++++++++++++++++++++++++++++
	public static int[] fromUtf8ToAsciiFor96(int[] ia) {	    
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");
	    	iaPure[i] = UnicodeZZZ.fromUtf8ToAscii(ia[i],Vigenere96ZZZ.iOffsetForUtf8Range);	    	
	    }	    
	    System.out.println("\n");
	    return iaPure;   		
	}
	
	//##########################################################
	public static int[] fromAsciiToUtf8For256(int[] ia) {	    
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");
	    	iaPure[i] = UnicodeZZZ.fromAsciiToUtf8(ia[i],Vigenere256ZZZ.iOffsetForUtf8Range);	    	
	    }	    
	    System.out.println("\n");
		return iaPure;   		
	}

	
	//++++++++++++++++++++++++++++++++++++++
	public static int[] fromUtf8ToAsciiFor256(int[] ia) {	    
	    //FGL: Nun die Zeichen vom ASCII Wert an UTF-8 Wert anpassen
	    int[]iaPure = new int[ia.length];

	    for(int i=0;i<ia.length;i++) {
	    	System.out.print("#"+i+". Stelle ");
	    	iaPure[i] = UnicodeZZZ.fromUtf8ToAscii(ia[i],Vigenere256ZZZ.iOffsetForUtf8Range);	    	
	    }	    
	    System.out.println("\n");
		return iaPure;   		
	}
	
	//###########################################################################
	public static int[] fromAsciiToUtf8ForNn(int[] ia) {	    
		//FGL: Keine Anpassung notwendig, da ein Characterpool verwendet wird
		return ia;   		
	}
	
	//++++++++++++++++++++++++++++++++++++++
	public static int[] fromUtf8ToAsciiForNn(int[] ia) {	    
		//FGL: Keine Anpassung notwendig, da ein Characterpool verwendet wird	   
		return ia;   		
	}
			
	//###########################################################################################
	/** Damit werden nichtdruckbare Zeichen ausgespart.
	 *  Z.B. angewendet in meiner Überarbeitung des Codes zum Buch "Kryptografie mit Java", Vigenere Verschluesselung, S. 33ff
	 *  
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 17.11.2022, 08:45:23
	 */
	public static int[] fromAsciiToUtf8(String s, int iOffset) {
		int[] ia = UnicodeZZZ.fromByteToInt(s.getBytes()); 
		return UnicodeZZZ.fromAsciiToUtf8(ia, iOffset);  	
	}
	public static int[] fromAsciiToUtf8(int[] ia, int iOffset) {
		    
		    //FGL: Nun die Zeichen vom UTF-8 Wert an ASCII anpassen
		    int[]iaPure = new int[ia.length];

		    for(int i=0;i<ia.length;i++) {
		    	iaPure[i] = UnicodeZZZ.fromAsciiToUtf8(ia[i], iOffset);
		    }
		    
		 return iaPure;   		
	}
	public static int fromAsciiToUtf8(int iz, int iOffset) {
		int iReturn=-1;
		main:{
			if (iz<0) iz+=256;		
		    if (iz==10) {
		    	//System.out.println();
		    	iReturn = iz - iOffset;
		    }else {
		       //Nichtdruckbare Zeichen und besondere rechnerspezifische Zeichen ausschliessen.	    	
			   if (((iz>31)&&(iz<127)) || ((iz>160)&&(iz<256) )) {       
		         //System.out.print((char)(iz) + ":"+iz+"|");
				 iReturn = iz + iOffset;
		       }else {
		         //System.out.print("." + ":"+iz+"|");
		         iReturn = iz + iOffset;
		       }
		    }
		}//end main:
	    return iReturn;		
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/** Damit werden nichtdruckbare Zeichen ausgespart.
	 *  Z.B. angewendet in meiner Überarbeitung des Codes zum Buch "Kryptografie mit Java", Vigenere Verschluesselung, S. 33ff
	 *  
	 * @param s
	 * @return
	 * @author Fritz Lindhauer, 17.11.2022, 08:45:23
	 */
	public static int[] fromUtf8ToAscii(String s, int iOffset) {
		int[] ia = UnicodeZZZ.fromByteToInt(s.getBytes()); 
		return UnicodeZZZ.fromUtf8ToAscii(ia, iOffset);  	
	}
	public static int[] fromUtf8ToAscii(int[] ia, int iOffset) {
		    
		    //FGL: Nun die Zeichen vom UTF-8 Wert an ASCII anpassen
		    int[]iaPure = new int[ia.length];

		    for(int i=0;i<ia.length;i++) {
		    	iaPure[i] = UnicodeZZZ.fromUtf8ToAscii(ia[i], iOffset);
		    }
		    
		 return iaPure;   		
	}
	public static int fromUtf8ToAscii(int iz, int iOffset) {
		int iReturn=-1;
		main:{
			if (iz<0) iz+=256;		
		    if (iz==10) {
		    	//System.out.println();
		    	iReturn = iz - iOffset;
		    }else {
		       //Nichtdruckbare Zeichen und besondere rechnerspezifische Zeichen ausschliessen.	    	
			   if (((iz>31)&&(iz<127)) || ((iz>160)&&(iz<256) )) {       
		         //System.out.print((char)(iz) + ":"+iz+"|");
				 iReturn = iz - iOffset;
		       }else {
		         //System.out.print("." + ":"+iz+"|");
		         iReturn = iz - iOffset;
		       }
		    }
		}//end main:
	    return iReturn;		
	}

	
	
	
	 /** Angelehnt an "Kryptografie mit Java", IoUtil.Unicode(s.getBytes());
	  * 
	 * @param ByteFeld
	 * @return
	 * @author Fritz Lindhauer, 17.11.2022, 08:53:24
	 */
	public static int[] fromByteToInt(byte[] ByteFeld) {
	    int[] dummy=new int[ByteFeld.length];
	    for (int i=0; i<dummy.length; i++) 
	      if (ByteFeld[i]>=0)  dummy[i]=ByteFeld[i];
	      else                 dummy[i]=256+ByteFeld[i];
	    return dummy;
	  }
	
	//-------------------------------------------
//	byte[] toByteArray(int value) {
//	     return  ByteBuffer.allocate(4).putInt(value).array();
//	}
//
//	byte[] toByteArray(int value) {
//	    return new byte[] { 
//	        (byte)(value >> 24),
//	        (byte)(value >> 16),
//	        (byte)(value >> 8),
//	        (byte)value };
//	}
//
//	public static int fromByteArrayToInt(byte[] bytes) {
//	     return ByteBuffer.wrap(bytes).getInt();
//	}
	
//	// packing an array of 4 bytes to an int, big endian, minimal parentheses
//	// operator precedence: <<, &, | 
//	// when operators of equal precedence (here bitwise OR) appear in the same expression, they are evaluated from left to right
//	int fromByteArray(byte[] bytes) {
//	     return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
//	}
//
//	// packing an array of 4 bytes to an int, big endian, clean code
//	int fromByteArray(byte[] bytes) {
//	     return ((bytes[0] & 0xFF) << 24) | 
//	            ((bytes[1] & 0xFF) << 16) | 
//	            ((bytes[2] & 0xFF) << 8 ) | 
//	            ((bytes[3] & 0xFF) << 0 );
//	}
}
