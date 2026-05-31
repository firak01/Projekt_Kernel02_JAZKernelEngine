package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;

public abstract class AbstractFileTextZZZ extends AbstractObjectWithExceptionZZZ{
	private static final long serialVersionUID = -1464375530224033955L;
	protected IStreamZZZ objStream = null;
	protected String sFileName = null;
	protected File objFile = null;
	
	protected List<String> listaLine = null;
	
	public static final String sFILE_NAME_DEFAULT= "NewTextfile_default.txt";
	
	
	public AbstractFileTextZZZ() {		
	}
	public AbstractFileTextZZZ(String sFileName) throws ExceptionZZZ{
		this.setFileName(sFileName);
	}
	
	public AbstractFileTextZZZ(File objFile) throws ExceptionZZZ{
		this.setFileObject(objFile);
	}
	
	public AbstractFileTextZZZ(List<String> listaLine) throws ExceptionZZZ{
		this.setLines(listaLine);
	}
	
	//##### Getter / Setter ###################
	public String getFileNameDefault() throws ExceptionZZZ {
		return AbstractFileTextZZZ.sFILE_NAME_DEFAULT;
	}
	
	
	public String getFileName() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sFileName)) {
			if(this.objFile!=null) {
				this.sFileName = objFile.getPath();
			}else {
				this.sFileName = this.getFileNameDefault();
			}
		}		
		return this.sFileName;
	}
	public void setFileName(String sFileName) {
		this.sFileName = sFileName;
	}
	
	public File getFileObject() throws ExceptionZZZ {
		if(this.objFile==null) {
			String sFileName = this.getFileName();
			if(StringZZZ.isEmpty(sFileName)) {
				ExceptionZZZ ez = new ExceptionZZZ("Filename or File-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}
			
			File objFile = new File(sFileName);
			if(!FileEasyZZZ.exists(objFile)) {
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFileName + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
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
				ExceptionZZZ ez = new ExceptionZZZ("File does not exist '" + sFileName + "'", iERROR_PROPERTY_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
				throw ez;
			}			
		}
		this.setFileName(null); //egal ob Datei oder NULL, wenn benötigt den Dateinamen also wieder neu aus der Datei holen ODER DEFAULT.
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
}
