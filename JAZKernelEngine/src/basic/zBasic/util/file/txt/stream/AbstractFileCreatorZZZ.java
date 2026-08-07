package basic.zBasic.util.file.txt.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.IKernelZZZ;

public abstract class AbstractFileCreatorZZZ extends AbstractKernelUseObjectZZZ implements Closeable {
	private static final long serialVersionUID = -2881829929933440233L;
	private File fileTemplate = null;
	private String sTargetPath = null;
	
	public AbstractFileCreatorZZZ(IKernelZZZ objKernel, File fileTemplate, String sTargetPath) throws ExceptionZZZ {
		super(objKernel);
		AbstractFileCreator_(fileTemplate, sTargetPath);
	}
	private boolean AbstractFileCreator_(File fileTemplate, String sTargetPath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(fileTemplate==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + " TemplateFile ", iERROR_PARAMETER_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			this.setTemplateFile(fileTemplate);
			
			if(StringZZZ.isEmpty(sTargetPath)) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + " TargetFilePath ", iERROR_PARAMETER_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			this.setTargetPath(sTargetPath);
			
		}
		return bReturn;
	}
	
	public File createFile() throws ExceptionZZZ {
		File objReturn = null;
		main:{			
			try {
				String sTargetPath = this.getTargetPath();
				if(StringZZZ.isEmpty(sTargetPath)) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING + " TargetFilePath ", iERROR_PROPERTY_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}			
				
				File objFileTemplate = this.getTemplateFile();
				if(fileTemplate==null) {
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING + " TemplateFile ", iERROR_PROPERTY_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				//++++++++++++++++++++
				FileTextWriterZZZ objTargetWriter = new FileTextWriterZZZ(sTargetPath);
				
				ArrayList<String> listaLineReadme = this.computeLines(objFileTemplate);
				for(String sLine : listaLineReadme){
					objTargetWriter.writeLine(sLine);
				}

				objTargetWriter.close();
			} catch (IOException ioe) {				
				ExceptionZZZ ez = new ExceptionZZZ(ioe);
				throw ez;
			}
		}// end main:
		return objReturn;
	}
	
	public ArrayList<String> computeLines() throws ExceptionZZZ {
		return this.computeLines(null);
	}
	public abstract ArrayList<String> computeLines(File objFileTemplate) throws ExceptionZZZ;
	
	
	
	
	//### Getter / Setter
	public File getTemplateFile() {
		return this.fileTemplate;
	}
	public void setTemplateFile(File fileTemplate) {
		this.fileTemplate = fileTemplate;
	}
	
	public String getTargetPath() {
		return this.sTargetPath;
	}
	public void setTargetPath(String sTargetPath) {
		this.sTargetPath = sTargetPath;
	}
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	@Override
    public void close() throws IOException{
		//Hier gibt es (noch) keinen Stream auf Property-Ebene.
		//Die Streams werden (noch) alle in den Methoden schon geschlossen.
//	        if(objStream!=null){
//	            objStream.close();
//	        }
    }
}
