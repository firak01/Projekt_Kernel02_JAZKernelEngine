package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import basic.javagently.Stream;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.txt.FileTextUtilZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class FileTextReaderIteratorZZZ extends  AbstractFileTextReaderIteratorZZZ{
	private static final long serialVersionUID = -9054462955710855745L;
	
	public FileTextReaderIteratorZZZ() throws ExceptionZZZ {	
		super();
	}
	public FileTextReaderIteratorZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super();
		this.setFilePath(sFilePathTotal);
	}
	public FileTextReaderIteratorZZZ(File objFile) throws ExceptionZZZ {
		super();
		this.setFileObject(objFile);
	}
	
	//##############################################################

	public synchronized String read() throws ExceptionZZZ{			
		return readAsString();
	}
	
	public synchronized String readAsString() throws ExceptionZZZ{			
		File objFile = this.getFileObject();		
		return FileTextUtilZZZ.readFileToString(objFile);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public synchronized List<String> readAsList() throws ExceptionZZZ{
		File objFile = this.getFileObject();
		return FileTextUtilZZZ.readFileToList(objFile);
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String getFileNameDefault() throws ExceptionZZZ {
		return FileTextReaderIteratorZZZ.sFILE_NAME_DEFAULT;
	}
}
