package basic.zBasic.util.file.csv.stream;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.stream.AbstractFileTextZZZ;

/** Vorbild ist CSVReader.java, was aber ohne Wiederverwendbare Kernel-Komponenten gebaut ist.
 *  FileCsvReaderZZZ setzt auf Kernel-Komponenten auf, die dadurch wiederverwendet werden und "immer robuster" werden.
 *  
 *  Es gibt schon KernelFileCsvZZZ, darin werden sogar KernelZZZ und LogZZZ und der eingangs genannte CSVReader genutzt,
 *  aber das ist eigentlich für nicht Kernel-Konfiguration nutzende Tools und kleine Applikationen zu viel Overhead.
 * @author Fritz Lindhauer
 * @param <T>
 *
 */
public class FileCsvReaderIterableZZZ<T> extends AbstractFileCsvReaderZZZ<T> implements Iterable<LinkedHashMap<String,String>>{
	private static final long serialVersionUID = -7107764826935730960L;

	private Iterator<LinkedHashMap<String, String>> itCsvReader = null;

	public FileCsvReaderIterableZZZ() throws ExceptionZZZ {
		super();
		FileCsvReaderNew_();
	}
	

	public FileCsvReaderIterableZZZ(char cDelimiter) throws ExceptionZZZ {
		super(cDelimiter);
		FileCsvReaderNew_();
	}
	

	public FileCsvReaderIterableZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);		
		FileCsvReaderNew_();
	}
		
	public FileCsvReaderIterableZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super(sFilePathTotal, cDelimiter);		
		FileCsvReaderNew_();
	}
	
	public FileCsvReaderIterableZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn);		
		FileCsvReaderNew_();
	}
	
	public FileCsvReaderIterableZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn, cDelimiter);
		FileCsvReaderNew_();
	}
	
	//++++++
	public FileCsvReaderIterableZZZ(File objFile, String[] saFlag) throws ExceptionZZZ {
		super(objFile, saFlag);
		FileCsvReaderNew_();
	}
	
	
	private boolean FileCsvReaderNew_() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			bReturn = true;
		}//end main:
		return bReturn;
	}


	@Override
	public Vector<String> readHeader() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasMoreLines() throws ExceptionZZZ {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Iterator<LinkedHashMap<String, String>> iterator() {
		if(this.itCsvReader==null) {
			try {
				File objFile = this.getFile();
				this.itCsvReader = new FileCsvReaderIteratorZZZ<LinkedHashMap<String, String>>(objFile);
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return this.itCsvReader;
	}
}
