package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

/** Merke: Hier wird Zeilenweise geschrieben.
 *         Für das komplette schreiben aller Zeile: FileTextSaverZZZ.save(...)
 *                                 (dort wird dann intern der FileTextWriter verwendet)
 * @author Fritz Lindhauer
 *
 */
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
