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

public class FileTextReaderZZZ extends  AbstractFileTextReaderLoaderZZZ{
	private static final long serialVersionUID = -9054462955710855745L;
	
	public FileTextReaderZZZ() throws ExceptionZZZ {	
		super();
	}
	public FileTextReaderZZZ(String sFileName) throws ExceptionZZZ {
		super();
		this.setFilePath(sFileName);
	}
	public FileTextReaderZZZ(File objFile) throws ExceptionZZZ {
		super();
		this.setFileObject(objFile);
	}
	
	//##############################################################
}
