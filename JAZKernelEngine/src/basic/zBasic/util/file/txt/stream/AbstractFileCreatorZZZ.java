package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

public abstract class AbstractFileCreatorZZZ extends AbstractKernelUseObjectZZZ{
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
			String sTargetPath = this.getTargetPath();
			if(StringZZZ.isEmpty(sTargetPath)) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING + " TargetFilePath ", iERROR_PROPERTY_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}			
			FileTextWriterZZZ objTargetWriter = new FileTextWriterZZZ(sTargetPath);
			
			File objFileTemplate = this.getTemplateFile();
			if(fileTemplate==null) {
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING + " TemplateFile ", iERROR_PROPERTY_MISSING,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			ArrayList<String> listaLineReadme = this.computeLines(objFileTemplate);
			for(String sLine : listaLineReadme){
				objTargetWriter.writeLine(sLine);
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
	
	
}
