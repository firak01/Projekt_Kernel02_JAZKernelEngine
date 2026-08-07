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
public abstract class AbstractFileTextZZZ extends AbstractObjectWithExceptionZZZ implements Closeable{
	private static final long serialVersionUID = -1464375530224033955L;
	public static final String sFILE_NAME_DEFAULT= "Textfile_default.txt";
	
	protected File objFile = null;
	protected String sFilePath=null;
		
	public AbstractFileTextZZZ() throws ExceptionZZZ {	
		super();
	}
	
	public AbstractFileTextZZZ(String sFilePath) throws ExceptionZZZ {
		super();		
		this.setFilePath(sFilePath);
	}
	
	public AbstractFileTextZZZ(File objFile) throws ExceptionZZZ {
		super();
		this.setFileObject(objFile);
	}
	
	//##### Getter / Setter ###################
	public String getFileNameDefault() throws ExceptionZZZ {
		return AbstractFileTextZZZ.sFILE_NAME_DEFAULT;
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
	
	
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	//Aber da hier die genaue Methode/das Konkret verwendete Objekt noch nicht bekannt ist, nur abstract
	@Override
    public abstract void close() throws IOException;
        
}
