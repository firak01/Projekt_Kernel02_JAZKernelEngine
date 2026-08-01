package basic.zBasic.util.file;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;

public abstract class AbstractFileTextReaderZZZ extends AbstractObjectWithExceptionZZZ implements Closeable{
	private static final long serialVersionUID = -1464375530224033955L;
	protected IStreamZZZ objStream = null;
	protected String sFilePath = null;
	protected File objFile = null;
	
	protected List<String> listaLine = null;
	
	public static final String sFILE_NAME_DEFAULT= "NewTextfile_default.txt";
	
	
	public AbstractFileTextReaderZZZ() {		
	}
	public AbstractFileTextReaderZZZ(String sFilePath) throws ExceptionZZZ{
		this.setFilePath(sFilePath);
	}
	
	public AbstractFileTextReaderZZZ(File objFile) throws ExceptionZZZ{
		this.setFileObject(objFile);
	}
	
	public AbstractFileTextReaderZZZ(List<String> listaLine) throws ExceptionZZZ{
		this.setLines(listaLine);
	}
	
	//##### Getter / Setter ###################
	public String getFileNameDefault() throws ExceptionZZZ {
		return AbstractFileTextReaderZZZ.sFILE_NAME_DEFAULT;
	}
	
	
	public String getFilePath() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sFilePath)) {
			if(this.objFile!=null) {
				this.sFilePath = objFile.getPath();
			}else {
				this.sFilePath = this.getFileNameDefault();
			}
		}		
		return this.sFilePath;
	}
	public void setFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	
	public File getFileObject() throws ExceptionZZZ {
		if(this.objFile==null) {
			String sFilePath = this.getFilePath();
			if(StringZZZ.isEmpty(sFilePath)) {
				ExceptionZZZ ez = new ExceptionZZZ("Filepath or File-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			File objFile = new File(sFilePath);
			if(!FileEasyZZZ.exists(objFile)) {
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFilePath + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			this.objFile = objFile;
		}
		return this.objFile;
	}
	
	/** Null ist erlaubt.
	 *  Aber wenn eine Datei übergeben wird, dann sollte die auch vorhanden sein.
	 * @param objFile
	 * @throws ExceptionZZZ
	 */
	public void setFileObject(File objFile) throws ExceptionZZZ{
		if(objFile!=null) {
			if(!FileEasyZZZ.exists(objFile)) {
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFilePath + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}			
		}
		this.setFilePath(null); //egal ob Datei oder NULL, wenn benötigt den Dateinamen also wieder neu aus der Datei holen ODER DEFAULT.
		this.objFile = objFile;
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
	
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	@Override
    public void close() throws IOException{
        if(objStream!=null){
            objStream.close();
        }
    }
}
