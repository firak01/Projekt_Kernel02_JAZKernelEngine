package basic.zBasic.util.file.txt.bytes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
//import basic.zBasic.ObjectZZZ.FLAGZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** Verwendet intern "RandomAccessFile", das ist der Unterschied zu FileTextParserZZZ
 * @author lindhaueradmin
 *
 */
public class TxtReaderZZZ extends TxtCommonZZZ{
	public enum FLAGZ{
		IsFileSorted, IgnoreCase, IgnoreEmptyLine, IgnoreCommentLine;
	}
	
	//Aus IFlagZZZ, über die abstrakte TxtCommonZZZ Klasse
	@Override
	public Class getClassZ() {
		return FLAGZ.class;
	}
	
public TxtReaderZZZ() throws ExceptionZZZ{
	String[] saFlag = {"INIT"};
	TxtReaderNew_(null, null, saFlag);
} 
public TxtReaderZZZ(File file) throws ExceptionZZZ{
	TxtReaderNew_(null, file, null);
}
public TxtReaderZZZ(File file, String sFlagControl) throws ExceptionZZZ{
	String[] saFlag = new String[1];
	saFlag[0] = sFlagControl;
	TxtReaderNew_(null, file, saFlag);
}
public TxtReaderZZZ(File file, String[] saFlagcontrol) throws ExceptionZZZ{
	TxtReaderNew_(null, file, saFlagcontrol);
}
public TxtReaderZZZ(TxtWriterZZZ objWriter, String[] saFlagControl) throws ExceptionZZZ{
	TxtReaderNew_(objWriter, null, saFlagControl);
}



private boolean TxtReaderNew_(TxtWriterZZZ objWriter, File file, String[] saFlagControlIn ) throws ExceptionZZZ{
boolean bReturn = false;
main:{
 //setzen der übergebenen Flags	
  if(saFlagControlIn != null){
	  for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
		  String stemp = saFlagControlIn[iCount];
		  if(!StringZZZ.isEmpty(stemp)){
			  boolean btemp = setFlag(stemp, true);
			  if(btemp==false){						
				  ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
				  throw ez;		 
			  }
		  }
	  }
		if(this.getFlag("INIT")==true){
			bReturn = true;
			break main; 
		}		
  }

  
  if(objWriter==null){  
	  if(file == null){
		  ExceptionZZZ ez = new ExceptionZZZ("File-Object", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
		  throw ez;
	  }
	  this.setFile(file);
  }else{
	  File fileFromWriter = objWriter.getFile();
	  if(fileFromWriter == null){
		  ExceptionZZZ ez = new ExceptionZZZ("File-Object from Writer-Object", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
		  throw ez;
	  }
	  this.setFile(fileFromWriter);
	  
	  RandomAccessFile raFileFromWriter = objWriter.getRandomAccessFileObject();
	  this.setRandomAccessFileObject(raFileFromWriter);
  }
  
  bReturn = true;
}//end main:
return bReturn;
}




/** Gibt den Mode für das RandomAccessFile zurück.
*   Es muss hier nur lesend auf die Datei zugegriffen werden.
* @return
* 
* 
* Value

Meaning
"r" 	Open for reading only. Invoking any of the write methods of the resulting object will cause an IOException to be thrown.
"rw" 	Open for reading and writing. If the file does not already exist then an attempt will be made to create it.
"rws" 	Open for reading and writing, as with "rw", and also require that every update to the file's content or metadata be written synchronously to the underlying storage device.
"rwd"   	Open for reading and writing, as with "rw", and also require that every update to the file's content be written synchronously to the underlying storage device.

* lindhaueradmin; 10.02.2007 15:09:59
*/
public String getMode(){
return "r";
}

/** Ermittelt, die BytePostion, an der diese Zeile existiert.
 *  -1 wird zurückgegeben, wenn sie nicht existiert
* @param sLine2proof
* @param lStartByteIn
* @return
* @throws ExceptionZZZ
* 
* lindhaueradmin; 12.02.2007 07:50:23
 */
public long readPositionLineFirst(String sLine2proof, long lStartByteIn) throws ExceptionZZZ{
	long lReturn = -1;
	main:{
		try{
			if(StringZZZ.isEmpty(sLine2proof)) break main;
			long lStartByte;
			if(lStartByteIn <= -1){
				lStartByte = 0;
			}else{
				lStartByte = lStartByteIn;
			}
			
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			boolean bFlagIgnoreCase = this.getFlag("IgnoreCase");
			
			long lPosition = lStartByte;
			raFile.seek(lPosition);
			
			String sLine = raFile.readLine();//ABER: DAS LIEST KEINE UTF8-DATEIEN 
			if(!StringZZZ.isEmptyNull(sLine)){
				//Darum ggfs. umkodieren
				String UTF8 = new String(sLine.getBytes("ISO-8859-1"), "UTF-8");
				sLine = UTF8;
			}
			
			while(sLine!=null){
				if(bFlagIgnoreCase==true){
					if(sLine.equalsIgnoreCase(sLine2proof)){
						lReturn = lPosition;
						break main;
					}
				}else{
					if(sLine.equals(sLine2proof)){
						lReturn = lPosition;
						break main;
					}
				}
				lPosition = raFile.getFilePointer(); //Die vorherige Position speichern
				sLine = raFile.readLine();////ABER: DAS LIEST KEINE UTF8-DATEIEN 
				if(!StringZZZ.isEmptyNull(sLine)){
					//Darum ggfs. umkodieren
					String UTF8 = new String(sLine.getBytes("ISO-8859-1"), "UTF-8");
					sLine = UTF8;
				}
				
			}
			
			//raFile.close();
		}catch(IOException ie){
			ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}//end main:
	return lReturn;
}

/** Liste die Zeile ab der gegebenen Byte Position aus. Das kann der Leerstring sein, wenn das Ende z.B. der zuvorigen Zeile als BytePosition angegeben worden ist (wg CR + LF).
 *   Das ist NICHT eine Zeilennummer.
* @param lStartByteIn
* @return
* @throws ExceptionZZZ
* 
* lindhaueradmin; 21.03.2007 17:56:41
 */
public String readLineByByte(long lStartByteIn) throws ExceptionZZZ{
	String sReturn=null;
	main:{
		try{
			long lStartByte;
			if(lStartByteIn <= -1){
				lStartByte = 0;
			}else{
				lStartByte = lStartByteIn;
			}
			
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			raFile.seek(lStartByte);
		    
			String sLine = raFile.readLine();//ABER: Das liest kein UTF8
			String UTF8 = new String(sLine.getBytes("ISO-8859-1"), "UTF-8");
			sReturn = UTF8;
			
			//raFile.close();
	}catch(IOException ie){
		ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	}//end main:
	return sReturn;
}

public String readLineNextByByte(long lStartByteIn) throws ExceptionZZZ{
	String sReturn=null;
	main:{
		try{
			long lStartByte;
			if(lStartByteIn <= -1){
				lStartByte = 0;
			}else{
				lStartByte = lStartByteIn;
			}
			
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			raFile.seek(lStartByte);

			String sTemp = raFile.readLine();
			
			//Das ist aber noch nicht die nächste Zeile
			sReturn = raFile.readLine();
			
			//raFile.close();
	}catch(IOException ie){
		ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
		throw ez;
	}
	}//end main:
	return sReturn;
}


/** List die Datei ab der gegebenen Byte-Position aus und schreibt Zeile f�r Zeile in einen Vector.
 *   Falls das Flag "IgnoreCommentLine" gesetzt ist, 
 *  
* @param lStartByteIn
* @return
* @throws ExceptionZZZ
* 
* lindhaueradmin; 11.02.2007 10:13:17
 */
public Vector readVectorStringByByte(long lStartByteIn) throws ExceptionZZZ{
	Vector vecReturn = new Vector();
	main:{
		try {
			long lStartByte;
			if(lStartByteIn <= -1){
				lStartByte = 0;
			}else{
				lStartByte = lStartByteIn;
			}
			
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			raFile.seek(lStartByte);
			
			boolean bIgnoreCommentLine = this.getFlag("IgnoreCommentLine");
			boolean bIgnoreEmptyLine = this.getFlag("IgnoreEmptyLine");
			
			//Nun die Zeilen auslesen und in einen Vector packen
			String sLine = raFile.readLine();
			while (sLine != null){
				currentLine:{
					if(bIgnoreEmptyLine==true){
						if(this.isEmptyLine(sLine)) break currentLine;
					}
					if(bIgnoreCommentLine==true){
						if(this.isCommentLine(sLine)) break currentLine;
					}
					
					//TODO: Das IsFileSorted - Flag irgendwie ber�cksichtigen. Z.B. in einer speziellen .VectorSortedFromFile(...) Methode, in der die Werte dann sortiert 
					//in den Vector gegeben werden. Falls die Datei als sortiert markiert ist, dann braucht eben nicht extra sortiert werden. 
					vecReturn.add(sLine);
					
				}//end currentLine:
				sLine = raFile.readLine();
			}
			
			//raFile.close();
		}catch(IOException ie){
			ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}//end main:
	return vecReturn;
	}
}//end class

