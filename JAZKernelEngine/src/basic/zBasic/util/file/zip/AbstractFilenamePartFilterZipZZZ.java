package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;
import custom.zUtil.io.FileZZZ;

public abstract class AbstractFilenamePartFilterZipZZZ extends AbstractObjectWithFlagZZZ implements IFilenamePartFilterZipZZZ  {
	private String sDirectoryPath=null;
	private String sFileName=null;
	private String sCriterion=null;
	private String sCriterionPath=null;
	
	public AbstractFilenamePartFilterZipZZZ() {
		super();
	}
	public AbstractFilenamePartFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ{
		super();
		this.setDirectoryPath(sDirectoryPath);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
		    	try {
					if(ze==null) break main;								
					if(StringZZZ.isEmpty(this.getDirectoryPath())) {
						bReturn = true;
						break main;
					}	
					String sName = ze.getName();					
					if(sName.equals(this.getCriterion())) {//Genaue übereinstimmung zwischen Pfad und Dateiname. Also EINZIGARTIG!
						bReturn = true;
						break main;
					}
				
				} catch (ExceptionZZZ e) {			
					e.printStackTrace();
				} 					
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getDirectoryPath(){
		return this.sDirectoryPath;
	}
	public void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ{
		this.sDirectoryPath = FileCommonsForFilterZipZZZ.computeDirectoryPath(sDirectoryPathIn);
	}
	
	public String getFileName(){
		return this.sFileName;
	}
	public void setFileName(String sName){
		this.sFileName = sName;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ{				
		this.sCriterion=sCriterion;		
	}
	
	@Override
	public String getCriterion() throws ExceptionZZZ {
		if(this.sCriterion==null) {
			String stemp = this.computeCriterion();
			this.sCriterion = stemp;
		}
		return this.sCriterion;
	}
	
	@Override
	public String computeCriterion() throws ExceptionZZZ{
		return null;
	}
}//end class
