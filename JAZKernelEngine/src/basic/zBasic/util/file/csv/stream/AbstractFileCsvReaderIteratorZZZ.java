package basic.zBasic.util.file.csv.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
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
import basic.zBasic.util.file.txt.stream.IFileTextReaderIteratorUserZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractFileCsvReaderIteratorZZZ<T>  extends AbstractFileCsvReaderZZZ<T> implements IFileCsvReaderEnabledZZZ, IFileTextReaderIteratorUserZZZ, Iterator<String>, Closeable{
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
	
	
	/** Verwende den FileTextReaderZZZ und lies alle Zeilen ein. 
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean load() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
//			try{
				FileTextReaderIteratorZZZ objFileTextReader = this.getFileTextReaderObject();
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
				
//				TODOGOON: Mache das fertig und realisiere danach die Idee...			
//				//Idee: Mache einen FileTextLineReaderZZZ
//				///     Iterator implementierend wie
//				//      als Beispiel TreeNodeIteratorZZZ
//		        //      Der soll ebenfalls auf protected IStreamZZZ objStream = null; basieren
//				//      Aber dann Neu: AbstractFileTextZZZ
//				//                     AbstractFileTextLineReaderZZZ
//				
//			//create streams
//			FileReader objFRead = new FileReader(this.objFile);
//			CSVReader objCSV = new CSVReader(objFRead, ';');
//			this.objCSV = objCSV;

//			}catch(FileNotFoundException e){
//				System.out.println(e.getMessage());
//			}

		}//end main:
		return bReturn;
	}
	
	
	//#####################################################
	
	

	public boolean hasMoreLines() {
		try {
			if (nextLine == null || nextLine.trim().equals(""))
				nextLine = reader.readLine();
		}
		catch (Exception ignore)
		{}

		if (nextLine == null || nextLine.trim().equals("")) {
			close();
			return false;
		}
		else
			return true;
	}

	public Hashtable getNextLine() {
		// Liest auf jeden Fall die neue Zeile, wenn es eine gibt.
		if (!hasMoreLines())
			return null;

		Hashtable hash = new Hashtable();

		// Aus der Zeile wird die Hashtable erzeugt.
		Vector dataFields = parseLine(nextLine.trim());
		for (int i=dataFields.size()-1; i>=0; i--)
			hash.put(header.elementAt(i), dataFields.elementAt(i));

		// L�scht die Zeile, damit hasMoreLines auf jeden Fall
		// eine neue Zeile einliest.
		nextLine = null;

		return hash;
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
	public String next() {
		try {
			return this.getFileTextReaderObject().next();
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remove() {
		try {
			this.getFileTextReaderObject().remove();
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
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
