package basic.zBasic.util.file.csv.stream;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.csv.CSVReader;
import basic.zBasic.util.file.txt.stream.FileTextReaderZZZ;
import basic.zBasic.util.file.txt.stream.IFileTextReaderUserZZZ;
import basic.zBasic.util.stream.IStreamZZZ;

public class AbstractFileCsvReaderZZZ  extends AbstractObjectWithExceptionZZZ implements IFileTextReaderUserZZZ, Closeable{
	protected FileTextReaderZZZ objFileTextReader = null;
	protected String sFilePathTotal = null;
	
	protected char cDELIMITER_DEFAULT = ';';
	protected char cDelimiter = CharZZZ.getNull();
	
	public AbstractFileCsvReaderZZZ() throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_(null, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(char cDelimiter) throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_(null, cDelimiter);
	}
	
	public AbstractFileCsvReaderZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super();		
		AbstractFileCsvReaderNew_(sFilePathTotal, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super();		
		AbstractFileCsvReaderNew_(sFilePathTotal, cDelimiter);
	}
	
	public AbstractFileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super();
		String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileNameIn);
		AbstractFileCsvReaderNew_(sFilePathTotal, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super();
		String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileNameIn);
		AbstractFileCsvReaderNew_(sFilePathTotal, cDelimiter);
	}
	
	private boolean AbstractFileCsvReaderNew_(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			this.sFilePathTotal = sFilePathTotal;
			this.cDelimiter = cDelimiter;
							
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	/** Verwende den FileTextReaderZZZ und lies alle Zeilen ein. 
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean load() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			try{
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
				
				TODOGOON: Mache das fertig und realisiere danach die Idee...			
				//Idee: Mache einen FileTextLineReaderZZZ
				///     Iterator implementierend wie
				//      als Beispiel TreeNodeIteratorZZZ
		        //      Der soll ebenfalls auf protected IStreamZZZ objStream = null; basieren
				//      Aber dann Neu: AbstractFileTextZZZ
				//                     AbstractFileTextLineReaderZZZ
				
			//create streams
			FileReader objFRead = new FileReader(this.objFile);
			CSVReader objCSV = new CSVReader(objFRead, ';');
			this.objCSV = objCSV;

			}catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}

		}//end main:
		return bReturn;
	}
	
	public void givenLargeFile_whenUsingFilesAPI_thenExtractedLineIsCorrect() {
	    try (Stream lines = Files.lines(Paths.get(""))) {
	        String extractedLine = lines.skip(4).findFirst().get();

	        assertEquals("Line 5", extractedLine);
	    }
	}
	
	
	
	//############################################
	//### GETTER / SETTER
	public String getFilePathTotal() throws ExceptionZZZ {
		return this.sFilePathTotal;
	}
	
	public void setFilePathTotal(String sFilePathTotal) throws ExceptionZZZ {		
		this.reset();
		this.sFilePathTotal = sFilePathTotal;		
	}
	
	public char getDelimiter() throws ExceptionZZZ {
		if(CharZZZ.isEmptyNull(this.cDelimiter)){
			return this.cDELIMITER_DEFAULT;
		}else {
			return this.cDelimiter;
		}
	}
	
	public void setDelimiter(char cDelimiter) throws ExceptionZZZ {
		this.cDelimiter = cDelimiter;
	}
	
	//### aus IFileTextReaderUserZZZ
	@Override
	public FileTextReaderZZZ getFileTextReaderObject() throws ExceptionZZZ {
		if(this.objFileTextReader==null) {
			this.objFileTextReader = new FileTextReaderZZZ(this.getFilePathTotal());
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
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.setFileTextReaderObject(null);
			
			
			bReturn = true;			
		}//end main:
		return bReturn;
	}
	
	//### aus Closeable
	@Override
	public void close() throws IOException {
		
		if(this.objFileTextReader!=null) {
			this.objFileTextReader.close();
		}
		
	}

	

}
