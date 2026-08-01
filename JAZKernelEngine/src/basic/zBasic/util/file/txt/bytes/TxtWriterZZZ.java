package basic.zBasic.util.file.txt.bytes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ.FLAGZ;

/**Klasse dient zum Schreiben in TextDateien. Dies ist ein Wrapper um "RandomAccessFile".
 * Bietet Komfortmethoden, wie z.B. das Einf�gen einer Zeile in eine sortierte Datei.
 * 
 * @author lindhaueradmin
 *
 */
public class TxtWriterZZZ  extends TxtCommonZZZ{
	private static final long serialVersionUID = 462093389087394951L;
	
	public enum FLAGZ{
		IsFileSorted, IgnoreCase, TopOfEmptyLine, TopOfCommentLine;
	}
	
	//Aus IFlagZZZ, �ber die abstrakte TxtCommonZZZ Klasse
	@Override
	public Class getClassZ() {
		return FLAGZ.class;
	}
	
	public TxtWriterZZZ() throws ExceptionZZZ{
		String[] saFlag = {"INIT"};
		TxtWriterNew_(null, null, saFlag);
	} 
	public TxtWriterZZZ(File file) throws ExceptionZZZ{
		TxtWriterNew_(null, file, null);
	}
	
	public TxtWriterZZZ(File file, String[] saFlagcontrol) throws ExceptionZZZ{
		TxtWriterNew_(null, file, saFlagcontrol);
	}
	public TxtWriterZZZ(TxtReaderZZZ objReader, String[] saFlagControl) throws ExceptionZZZ{
		TxtWriterNew_(objReader, null, saFlagControl);
	}
	



	private boolean TxtWriterNew_(TxtReaderZZZ objReader, File file, String[] saFlagControlIn ) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
		 //setzen der �bergebenen Flags	
		  if(saFlagControlIn != null){
			  for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				  String stemp = saFlagControlIn[iCount];
				  boolean btemp = setFlag(stemp, true);
				  if(btemp==false){						
					  ExceptionZZZ ez = new ExceptionZZZ("the flag '" + stemp + "' is not available.", iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
					  throw ez;		 
				  }
			  }
				if(this.getFlag("INIT")==true){
					bReturn = true;
					break main; 
				}		
		  }
		  
		  if(objReader==null){
			  if(file == null){
				  ExceptionZZZ ez = new ExceptionZZZ("File-Object", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }		  
			  this.setFile(file);
		  }else{
			  File fileFromReader = objReader.getFile();
			  if(fileFromReader == null){
				  ExceptionZZZ ez = new ExceptionZZZ("File-Object from reader-object.", iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				  throw ez;
			  }
			  this.setFile(fileFromReader);
		  }	  
		  bReturn = true;
		}//end main:
		return bReturn;
	}



	/** Gibt die BytePosition, in die eingef�gt wurde zur�ck.
	* @param sToBeInserted
	* @return
	* 
	* lindhaueradmin; 10.02.2007 14:34:44
	 * @throws ExceptionZZZ 
	 */
	public long insertLine(String sToBeInserted) throws ExceptionZZZ{
		long lReturn=-1;  //Merke: Selbst wenn es noch keinen Inhalt gibt, dann sollte dieser Wert auf 0 ge�ndert werden.
		main:{
			try{
			if(StringZZZ.isEmpty(sToBeInserted)==true) break main;
			
			RandomAccessFile rwFile = this.getRandomAccessFileObject();
			
			
			boolean bFlagSorted = this.getFlag("IsFileSorted");
			//Falls man davon ausgehen kann, dass die Datei sortiert ist ...
			if(bFlagSorted==true){
				boolean bInserted = false;
				boolean bFlagIgnoreCommentLine = this.getFlag("TopOfCommentLine");
				boolean bFlagIgnoreCase = this.getFlag("IgnoreCase");
				boolean bFlagTopOfEmptyLine = this.getFlag("TopOfEmptyLine");
				
				rwFile.seek(0);
				
				String sLineForCompare;
				String sLine = rwFile.readLine();			
				while(sLine!=null){
					boolean bTopOfLine = false;
					
					currentLine:{
						//ggf. pr�fen, ob eine Komentarzeile
						if(bFlagIgnoreCommentLine==true){
							if(this.isCommentLine(sLine)){
								bTopOfLine = true;
								break currentLine;
							}
						}	
						if(bFlagTopOfEmptyLine==true){
							if(this.isEmptyLine(sLine)){
								bTopOfLine = true;
								break currentLine;
							}
						}
						
						sLineForCompare = sLine; //nachdem einige Zeilen ignoriert wurden, kann nun die "Vergleichszeile" gesetzt werden.
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLineForCompare.compareTo(sToBeInserted);
						}else{
							iProof = sLineForCompare.compareToIgnoreCase(sToBeInserted);
						}
						
						
						if(iProof >= 0){
							//der neue String ist gr�sser oder gleich
							
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen fuellen
							//          Ggf. mal in eine temporaere Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);
																				
							Vector vec = objReader.readVectorStringByByte(lReturn);
							
							//Nun an der alten Stelle einfuegen
							if(lReturn == rwFile.length()){
								//a) Am Ende der Datei
								if(lReturn<=-1) lReturn = 0;
								rwFile.seek(lReturn);
								sToBeInserted = "\r\n" + sToBeInserted;
								rwFile.writeBytes(sToBeInserted);												
								break main;
								
							}else{
								//b) Mitten in der Datei
								if(lReturn<=-1) lReturn = 0;
								rwFile.seek(lReturn);
								sToBeInserted = sToBeInserted + "\r\n";
								rwFile.writeBytes(sToBeInserted);
								
								//Den Speicher wieder auslesen und alle fehlenden Datens�tze schreiben. 
								this.appendVectorString(rwFile.getFilePointer(), vec);								
								break main;
								
							}												
						}
						//lReturn = rwFile.getFilePointer();  //die alte File Position festhalten
					}//end currentLine:
					if(bTopOfLine==false){
						lReturn = rwFile.getFilePointer();  //die alte File Position festhalten
					}
					sLine = rwFile.readLine();		
				}//end while
				if(bInserted==false){
					lReturn = rwFile.length();
					sToBeInserted = sToBeInserted + "\r\n";
					rwFile.writeBytes(sToBeInserted);
				}
				
			}else{		
				//... falls es eine unsortierte Datei ist. Einfach anh�ngen.
				lReturn = rwFile.length();
				if(lReturn<=-1) lReturn = 0;
				rwFile.seek(lReturn);
				sToBeInserted = sToBeInserted + "\r\n";
				rwFile.writeBytes(sToBeInserted);	
			}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return lReturn;
	}
	
	public long removeLineFirst(String sToBeRemoved, long lPositionIn) throws ExceptionZZZ{
		long lReturn = -1;
		main:{
		try{
			if(StringZZZ.isEmpty(sToBeRemoved)==true) break main;
			long lPosition;
			if(lPositionIn <= -1){
				lPosition = 0;
			}else{
				lPosition = lPositionIn;
			}
			
			RandomAccessFile rwFile = this.getRandomAccessFileObject();
			rwFile.seek(lPosition);
			
			boolean bFlagSorted = this.getFlag("IsFileSorted");
			boolean bFlagIgnoreCase = this.getFlag("IgnoreCase");
			
			String sLine = rwFile.readLine();	
			
			//Falls man davon ausgehen kann, dass die Datei sortiert ist ...
			if(bFlagSorted==true){												
				while(sLine!=null){				
					currentLine:{								
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLine.compareTo(sToBeRemoved);
						}else{
							iProof = sLine.compareToIgnoreCase(sToBeRemoved);
						}
						
						
						if(iProof == 0){
							//Die alte Stelle merken
							lReturn = lPosition;
						
							
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen f�llen
							//          Ggf. mal in eine tempor�re Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);		
							lPosition = rwFile.getFilePointer();
												
							//Nun an der alten Stelle einf�gen
							if(lPosition == rwFile.length()){
								//a) am Ende der Date nix machen, ausser die Dateil�nge zu verk�rzen
								rwFile.setLength(lReturn);
								
							}else{
								//b) Mitten in der Datei
							
								//Speicher mit den restlichen Zeilen f�llen
								Vector vec = objReader.readVectorStringByByte(lPosition);
															
								//Den Speicher wieder auslesen und alle fehlenden Datens�tze schreiben. 
								this.appendVectorString(lReturn, vec);								
								
	//							Die Dateil�nge entsprechend verl�rzen
								lPosition = rwFile.getFilePointer();
								rwFile.setLength(lPosition);
								
								break main;							
							}												
						}else if(iProof >= 1){
	//						Bei einer sortierten Datei kann es nun ein Abbruchkriterium geben.
							//System.out.println(ReflectionZZZ.getMethodCurrentName() + "# Abbruch der Suche, neue Zeile kann in sortierter Datei nicht mehr der gesuchten Zeile entsprechen");
							break main;
						}
					}//end currentLine:
					lPosition = rwFile.getFilePointer();
					sLine = rwFile.readLine();	
				}//end while
				
			}else{		
				while(sLine!=null){				
					currentLineUnsorted:{								
						int iProof;
						if(bFlagIgnoreCase==false){
							iProof = sLine.compareTo(sToBeRemoved);
						}else{
							iProof = sLine.compareToIgnoreCase(sToBeRemoved);
						}
	
						if(iProof == 0){
							//Die alte Stelle merken
							lReturn = lPosition;
					
													
							//TODO den Rest der Datei irgendwo zwischenspeichern
							//          Erste Alternative: ArrayList mit den restlichen Zeilen f�llen
							//          Ggf. mal in eine tempor�re Datei speichern. Das ist aber nur bei SEHR grossen Dateine sinnvoll.
							TxtReaderZZZ objReader = new TxtReaderZZZ(this, null);		
							lPosition = rwFile.getFilePointer();
													
							//Nun an der alten Stelle einf�gen
							if(lPosition == rwFile.length()){
								//a) am Ende der Date nix machen, auser die Datei zu verk�rzen
								rwFile.setLength(lReturn);
							}else{
								//b) Mitten in der Datei
								
								//Speicher mit den restlichen Zeilen f�llen
								Vector vec = objReader.readVectorStringByByte(lPosition);
								
								//Den Speicher wieder auslesen und alle fehlenden Datens�tze schreiben. 
								this.appendVectorString(lReturn, vec);		
								
								//Die Dateil�nge entsprechend verl�rzen
								lPosition = rwFile.getFilePointer();
								rwFile.setLength(lPosition);
								
								break main;							
							}												
						}else{
							lPosition = rwFile.getFilePointer();
						}
					}//end currentLineUnsorted:
					sLine = rwFile.readLine();	
				}//end while
			}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
		return lReturn;
	}
	
	public void appendVectorString(Vector vec) throws ExceptionZZZ{
		try {
			RandomAccessFile raFile = this.getRandomAccessFileObject();
			long lPosition = raFile.length();
			this.appendVectorString(lPosition, vec);
		}catch(IOException ie){
			ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
	}
	
	/** Anh�ngen von Datens�tzen ab der bestimtten Position 
	 *  !!! Dabei werden aber ggf. folgende Bytes �berschrieben !!!
	 *  
	 *  !!! Auch bei sortierten Dateien wird (noch nicht ?) neu sortiert.
	* @param lPositionin
	* @param vec
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 11.02.2007 15:16:41
	 */
	public void appendVectorString(long lPositionin, Vector vec) throws ExceptionZZZ{
		main:{
			try{
				if(vec == null) break main;
				if(vec.size()==0) break main;
				long lPosition;
				if(lPositionin <= -1){
					lPosition = 0;
				}else{
					lPosition = lPositionin;
				}
				
				RandomAccessFile raFile = this.getRandomAccessFileObject();
				raFile.seek(lPosition);
				
				Iterator it = vec.iterator();
				while(it.hasNext()){
					String sLine = (String) it.next();
					//Problem, wie fragt man die vorherige Zeile ab.   if(lPosition>=1), darum wird \r\n angeh�ngt und nicht vorangestellt
					sLine = sLine + "\r\n";
					raFile.writeBytes(sLine);
					lPosition = raFile.getFilePointer();
				}
			}catch(IOException ie){
				ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}//end main:
	}
	
	
	
	/** Gibt den Mode fuer das RandomAccessFile zurueck.
	 *   Es muss sowohl lesend als auch schreibend auf die Datei zugegriffen werden.
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
		return "rw";
	}
	
}//end class
