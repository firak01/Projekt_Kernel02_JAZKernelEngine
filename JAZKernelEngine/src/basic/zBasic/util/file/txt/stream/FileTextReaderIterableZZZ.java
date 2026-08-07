package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
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

public class FileTextReaderIterableZZZ<T> extends AbstractFileTextZZZ<T> implements Iterable<String>{
	private static final long serialVersionUID = -9054462955710855745L;
	
	private Iterator<String> itReader = null;
	
	public FileTextReaderIterableZZZ() throws ExceptionZZZ {	
		super();
	}
	public FileTextReaderIterableZZZ(String sFileName) throws ExceptionZZZ {
		super();
		this.setFilePath(sFileName);
	}
	public FileTextReaderIterableZZZ(File objFile) throws ExceptionZZZ {
		super();
		this.setFile(objFile);
	}
	
	//### aus Closeable
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
	
	//### aus Iterable
	@Override
	public Iterator<String> iterator() {
		if(this.itReader==null) {
			try {
				File objFile = this.getFile();
				this.itReader = new FileTextReaderIteratorZZZ<String>(objFile);
			} catch (ExceptionZZZ e) {
				e.printStackTrace();
			}
		}
		return this.itReader;
	}
	
	//##############################################################
}
