package basic.zBasic.util.file.csv.stream;

import java.io.File;
import java.io.IOException;

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
public class FileCsvReaderZZZ<T> extends AbstractFileCsvReaderLoaderZZZ<T> {
	private static final long serialVersionUID = -7107764826935730960L;


	public FileCsvReaderZZZ() throws ExceptionZZZ {
		super();
		FileCsvReaderNew_();
	}
	

	public FileCsvReaderZZZ(char cDelimiter) throws ExceptionZZZ {
		super(cDelimiter);
		FileCsvReaderNew_();
	}
	

	public FileCsvReaderZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super(sFilePathTotal);		
		FileCsvReaderNew_();
	}
		
	public FileCsvReaderZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super(sFilePathTotal, cDelimiter);		
		FileCsvReaderNew_();
	}
	
	public FileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn);		
		FileCsvReaderNew_();
	}
	
	public FileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super(sDirectoryIn, sFileNameIn, cDelimiter);
		FileCsvReaderNew_();
	}
	
	//++++++
	public FileCsvReaderZZZ(File objFile) throws ExceptionZZZ {
		super(objFile, (String[])null);
		FileCsvReaderNew_();
	}
	
	public FileCsvReaderZZZ(File objFile, char cDelimiter) throws ExceptionZZZ {
		super(objFile, cDelimiter, (String[])null);
		FileCsvReaderNew_();
	}
	
	
	public FileCsvReaderZZZ(File objFile, String[] saFlag) throws ExceptionZZZ {
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
}
