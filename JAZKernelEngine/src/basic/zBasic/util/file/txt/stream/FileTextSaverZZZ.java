package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public class FileTextSaverZZZ extends AbstractFileTextReaderSaverZZZ{ 
	private static final long serialVersionUID = 1940510972927370691L;

	public FileTextSaverZZZ() throws ExceptionZZZ {
		super();
	}
	
	public FileTextSaverZZZ(String sFilePath) throws ExceptionZZZ{
		super(sFilePath);
	}
	
	public FileTextSaverZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public FileTextSaverZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);		
	}

}
