package basic.zBasic.util.file.csv.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.stream.FileTextReaderIteratorZZZ;
import basic.zBasic.util.file.txt.stream.FileTextReaderZZZ;
import basic.zBasic.util.file.txt.stream.IFileTextReaderIteratorUserZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

//public abstract class AbstractFileCsvReaderIteratorZZZ<T>  extends AbstractFileCsvReaderZZZ<T> implements IFileCsvReaderEnabledZZZ, IFileTextReaderIteratorUserZZZ, Iterator<String>, Closeable{
public abstract class AbstractFileCsvReaderIteratorZZZ<T>  extends AbstractFileCsvReaderZZZ<T> implements IFileCsvReaderEnabledZZZ, IFileTextReaderIteratorUserZZZ, Iterator<LinkedHashMap<String,String>>, Iterable<LinkedHashMap<String,String>>, Closeable{
	private static final long serialVersionUID = 8453120372088993124L;
	
	protected FileTextReaderIteratorZZZ objFileTextReader = null;

	public AbstractFileCsvReaderIteratorZZZ() throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderIteratorZZZ(char cDelimiter) throws ExceptionZZZ {
		super(cDelimiter);
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderIteratorZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);		
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderIteratorZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super(sFilePathTotal, cDelimiter);				
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderIteratorZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn);		
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderIteratorZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn,cDelimiter);		
		AbstractFileCsvReaderNew_();
	}
	
	//++++++++++++++++++
	public AbstractFileCsvReaderIteratorZZZ(File objFile, String[] saFlag) throws ExceptionZZZ {
		super(objFile, saFlag);		
		AbstractFileCsvReaderNew_();
	}
	
	
	private boolean AbstractFileCsvReaderNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//############################################
	//### GETTER / SETTER
	
	//### aus IFileTextReaderIteratorUserZZZ
	@Override
	public FileTextReaderIteratorZZZ getFileTextReaderObject() throws ExceptionZZZ {
		if(this.objFileTextReader==null) {
			this.objFileTextReader = new FileTextReaderIteratorZZZ(this.getFile());
		}
		return this.objFileTextReader;
	}

	@Override
	public void setFileTextReaderObject(FileTextReaderIteratorZZZ objFileTextReader) throws ExceptionZZZ {		
		try {
			//Erst einmal das alte schliessen.
			if(this.objFileTextReader!=null) {				
				this.objFileTextReader.close();				
			}
			this.objFileTextReader = objFileTextReader;
		}catch(IOException ioe) {
			ExceptionZZZ ez = new ExceptionZZZ(ioe);
			throw ez;
		}
	}
		
		
	//###############################################
	//### Methoden

	@Override
	public Vector<String> readHeader() throws ExceptionZZZ {
		Vector<String> header = null;
		main:{
			try {				
				//Die Kopfzeile ist die erste Zeile, die kein Kommentar ist
				int iLineStart=-1;
				boolean bCsvLine=false;
				String sLine=null;
				
				
				FileTextReaderIteratorZZZ objReader = this.getFileTextReaderObject();
				if(objReader==null) {
					ExceptionZZZ ez = new ExceptionZZZ("FileTextReaderIteratorZZZ", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getPositionCurrent());
					throw ez;
				}
				
				//A) In den schon eingelesenen Zeilen suchen
				ArrayList<String> listasLine = objReader.getLines();
				do {
					iLineStart++;
					if(listasLine.size()-1>=iLineStart) {
						sLine = listasLine.get(iLineStart);
						bCsvLine = this.isCsvLine(sLine);
					}else {
						iLineStart--; //nix gefunden, also wieder zurück.
						bCsvLine = false;
						sLine=null;
					}
					//sLine = this.getFileTextReaderObject().getLines().get(iLineStart);
					//sLine = this.getFileTextReaderObject().next();					
					//bCsvLine = this.isCsvLine(sLine);					
				}while(!bCsvLine && sLine!=null);
				
				//B) In neuen Zeilen suchen
				if(!bCsvLine) {
					do {
						iLineStart++;
						sLine = objReader.next();
						bCsvLine = this.isCsvLine(sLine);
					}while(!bCsvLine && sLine!=null);
				}
				
				if(!bCsvLine) break main;//dann gibt es überhaupt keine CSV-Zeile und damit auch keinen Header.
				
				this.iLineStartCsv=iLineStart;
				this.iCurrentLine=iLineStart;
				header = parseLine(sLine);			
			}catch(Exception e) {
				ExceptionZZZ ez = new ExceptionZZZ(e);
				throw ez;
			}
		}//end main:
		return header;
	}
	
	@Override
	public boolean hasMoreLines() throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			int iNextLine = iCurrentLine;
			boolean bCsv=false;
			
			FileTextReaderIteratorZZZ objFileTextReader = this.getFileTextReaderObject();
			do{
				iNextLine = iNextLine+1;
			
				String sNextLine = objFileTextReader.next(); //Weil der Reader nicht sofort den ganzen Inhalt der Textdatei gelesen hat, was dann nicht so lange dauern soll.				
				if (sNextLine == null) {
					bReturn = false;
				} else {
					bCsv = this.isCsvLine(sNextLine);
					if(bCsv) {
						this.sNextLine = sNextLine;
						iCurrentLine = iNextLine;
						bReturn = true;
					}else {
						bReturn = false;
					}
				}		
			}while(!bCsv && sNextLine!=null);
		}//end main:		
		return bReturn;
	}
	
	
	
	//### aus Iterator	
	@Override
	public boolean hasNext() {
		try {
			return this.getFileTextReaderObject().hasNext();
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
			return false;
		}
	}

	@Override
	//public String next() {
	public LinkedHashMap<String,String> next() {
		LinkedHashMap<String,String> hmReturn = null;
		main:{
			try {
				//String sReturn = this.getFileTextReaderObject().next();
				hmReturn = this.parseNextLineAsMap();
			} catch (ExceptionZZZ e) {			
				e.printStackTrace();
				return null;
			}
		}//end main:
		return hmReturn;		
	}

//	@Override
//	IST DOCH EIGENTLICH NICHT IMPLEMENTIERT
	public void remove() {
//		try {
//			//this.getFileTextReaderObject().remove();
//		} catch (ExceptionZZZ ez) {			
//			ez.printStackTrace();
//		}
	}
	
	
	
	//### aus Closeable
	@Override
	public void close() throws IOException {		
		if(this.objFileTextReader!=null) {
			this.objFileTextReader.close();
		}
	}

	
	//###################################################
	//### FLAG HANDLING
	//###################################################
	
	
	//###################################
	//### FLAGLOCAL Handling

	/* ES GIBT HIER KEIN FLAGLOCAL
	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	*/
}
