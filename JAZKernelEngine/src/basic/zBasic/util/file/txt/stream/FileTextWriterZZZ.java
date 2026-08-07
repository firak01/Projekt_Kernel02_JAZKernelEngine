package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import basic.javagently.Stream;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.AbstractKernelLogZZZ;

public class FileTextWriterZZZ extends AbstractFileTextWriterLoaderZZZ{
	private static final long serialVersionUID = -8791560715152516646L;
	
	
	public FileTextWriterZZZ() throws ExceptionZZZ {
		super();
	}
	
	public FileTextWriterZZZ(String sFilePath) throws ExceptionZZZ{
		super(sFilePath);
	}
	
	public FileTextWriterZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public FileTextWriterZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);		
	}

}
