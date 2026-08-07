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
	
	
	
	
	//####################################################
	
	@Override
	public boolean hasMoreLines() throws ExceptionZZZ {
		boolean bReturn=false;
		main:{
			int iNextLine = iCurrentLine+1;
			
			TODOGOON20260809; Hier nur CSV Zeilen betrachten
						
			FileTextReaderIteratorZZZ objFileTextReader = this.getFileTextReaderObject();
			String sNextLine = objFileTextReader.next(); //Weil der ganze Inhalt der Textdatei gelesen wird, kann das lange dauern.				
			if (sNextLine == null) {
				bReturn = false;
			} else {
				this.sNextLine = sNextLine;
				iCurrentLine = iNextLine;
				bReturn = true;
			}		
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
		} catch (ExceptionZZZ ez) {			
			ez.printStackTrace();
		}
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
