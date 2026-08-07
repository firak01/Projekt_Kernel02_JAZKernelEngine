package basic.zBasic.util.file.txt.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.FileTextUtilZZZ;
import basic.zBasic.util.stream.IStreamZZZ;

/** Merke: Es gibt auch den TxtReaderZZZ, für RandomAccess - Zugriff
 * @author Fritz Lindhauer
 *
 */
public abstract class AbstractFileTextReaderLoaderZZZ extends AbstractFileTextZZZ implements Closeable{
	private static final long serialVersionUID = -1464375530224033955L;
	
	//In FileTextUtilZZZ.readFileToList(this.getFileObject()); wird ein Buffered Reader Objekt verwendet, um alles auf einen Schlag einzulesen.
		
	protected List<String> listaLine = null;
		
	public AbstractFileTextReaderLoaderZZZ() throws ExceptionZZZ {	
		super();
	}
	
	public AbstractFileTextReaderLoaderZZZ(String sFilePath) throws ExceptionZZZ {
		super(sFilePath);		
	}
	
	public AbstractFileTextReaderLoaderZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);		
	}
	
	public AbstractFileTextReaderLoaderZZZ(List<String> listaLine) throws ExceptionZZZ{
		super();
		this.setLines(listaLine);
	}
	
	//##### Getter / Setter ###################
	
	//##### METHODEN
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
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public List<String> getLines() throws ExceptionZZZ{
		if(this.listaLine==null) {
			if(this.getFileObject()==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Filename or File-Object AND List of Lines", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;				
			}else {				
				List<String> listaLine = FileTextUtilZZZ.readFileToList(this.getFileObject());
				this.setLines(listaLine);
			}
		}
		return this.listaLine;
	}
	public void setLines(List<String>listaLine) {
		this.listaLine = listaLine;
	}
	
	public String readLine(int iLineNumber) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			ArrayList<String>listasLine = (ArrayList<String>) this.getLines();
			if(listasLine.size()>iLineNumber) {
				sReturn = listasLine.get(iLineNumber);
			}			
		}//end main:
		return sReturn;
	}
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	@Override
    public void close() throws IOException{
		//in FileTextUtilZZZ.readFileToList(this.getFileObject()); wird der verwendete BufferedReader sofort geschlossen.
    }
}
