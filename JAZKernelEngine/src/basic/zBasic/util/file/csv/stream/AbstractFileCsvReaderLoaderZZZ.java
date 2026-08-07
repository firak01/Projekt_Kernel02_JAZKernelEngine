package basic.zBasic.util.file.csv.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
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
import basic.zBasic.util.file.txt.stream.AbstractFileTextReaderLoaderZZZ;
import basic.zBasic.util.file.txt.stream.AbstractFileTextZZZ;
import basic.zBasic.util.file.txt.stream.FileTextReaderIteratorZZZ;
import basic.zBasic.util.file.txt.stream.FileTextReaderZZZ;
import basic.zBasic.util.file.txt.stream.IFileTextReaderIteratorUserZZZ;
import basic.zBasic.util.file.txt.stream.IFileTextReaderUserZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

/**
 *  Merke: Ausgangssversion verwendete nicht den FileTextReaderZZZ,
 *         Sondern basiert auf folgenden Klassen:
          
           	//create streams
			FileReader objFRead = new FileReader(this.objFile);
			CSVReader objCSV = new CSVReader(objFRead, ';');
			
 * @author Fritz Lindhauer
 *
 * @param <T>
 */
public abstract class AbstractFileCsvReaderLoaderZZZ<T>  extends AbstractFileCsvReaderZZZ<T> implements IFileTextReaderUserZZZ{
	private static final long serialVersionUID = 8453120372088993124L;
		
	protected FileTextReaderZZZ objFileTextReader = null;
	
	public AbstractFileCsvReaderLoaderZZZ() throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderLoaderZZZ(char cDelimiter) throws ExceptionZZZ {
		super(cDelimiter);
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderLoaderZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);				
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderLoaderZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super(sFilePathTotal,cDelimiter);				
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderLoaderZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn);		
		AbstractFileCsvReaderNew_();
	}
	
	public AbstractFileCsvReaderLoaderZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn,cDelimiter);		
		AbstractFileCsvReaderNew_();
	}
	
	//++++++++++++++++++
	public AbstractFileCsvReaderLoaderZZZ(File objFile, String[] saFlag) throws ExceptionZZZ {
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
	
	//### aus IFileTextReaderUserZZZ
	@Override
	public FileTextReaderZZZ getFileTextReaderObject() throws ExceptionZZZ {
		if(this.objFileTextReader==null) {
			this.objFileTextReader = new FileTextReaderZZZ(this.getFile());
		}
		return this.objFileTextReader;
	}

	@Override
	public void setFileTextReaderObject(FileTextReaderZZZ objFileTextReader) throws ExceptionZZZ {		
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
				do {
					iLineStart++;
					sLine = this.getFileTextReaderObject().getLines().get(iLineStart);
					bCsvLine = this.isCsvLine(sLine);					
				}while(!bCsvLine && sLine!=null);
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
	
	
	
	/** Verwende den FileTextReaderZZZ und lies alle Zeilen auf einmal ein. 
	 *  Diese Zeilen sind aber noch nicht "geparsed".
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean load() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
//			try{
				FileTextReaderZZZ objFileTextReader = this.getFileTextReaderObject();
				File objFile = objFileTextReader.getFileObject();
				if(objFile==null) {
					ExceptionZZZ ez = new ExceptionZZZ("Filepath or File-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				if(objFile.isDirectory()){
				   ExceptionZZZ ez = new ExceptionZZZ( "file is a directory '" + objFile.getPath() + "'", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				   throw ez;						
				}
				
				//###############################################
				//### 1. Lies alle Zeilen ein, als Liste
				List<String> listasLine = objFileTextReader.getLines();
				
				//Die erste Zeile ist der Header
				String sHeaderLine = listasLine.get(0);
				this.readHeader(sHeaderLine);

		}//end main:
		return bReturn;
	}
	
	
	/** Gibt es weiter CSV Zeilen
	 * @return
	 * @throws ExceptionZZZ
	 */
	@Override
	public boolean hasMoreLines() throws ExceptionZZZ {
		boolean bReturn=false;
		main:{		
			String sNextLine=null;
			int iNextLine = iCurrentLine;			
			boolean bCsv=false;
			do{
				iNextLine = iNextLine+1;
				
				FileTextReaderZZZ objFileTextReader = this.getFileTextReaderObject();
				sNextLine = objFileTextReader.readLine(iNextLine); //Weil der ganze Inhalt der Textdatei gelesen wird, kann das lange dauern.				
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
