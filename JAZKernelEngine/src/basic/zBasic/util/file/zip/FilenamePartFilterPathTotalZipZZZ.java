package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.IJarEasyConstantsZZZ;
import basic.zBasic.util.file.jar.JarEasyHelperZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterPathTotalZipZZZ extends AbstractFilenamePartFilterZipZZZ {
	private String sCriterionPath=null;
	
	public FilenamePartFilterPathTotalZipZZZ() {
		super();
	}
	public FilenamePartFilterPathTotalZipZZZ(String sDirectoryPath) throws ExceptionZZZ{
		super();
		this.setDirectoryPath(sDirectoryPath);
		this.setFileName(null);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
		    	try {
					if(ze==null) break main;
					String sDirectoryPath = this.getDirectoryPath();
					if(StringZZZ.isEmpty(sDirectoryPath)) {
						bReturn = true;
						break main;
					}	
					String sName = ze.getName();	
					String sCriterion = this.getCriterion();
					if(sName.equals(sCriterion)) {//Genaue übereinstimmung zwischen Pfad und Dateiname. Also EINZIGARTIG!
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

	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ{				
		super.setCriterion(sCriterion);		
		
		String sCriterionPath = this.computeCriterionPath(sCriterion);
		this.setCriterionPath(sCriterionPath);
	}
	
	@Override
	public String computeCriterion() throws ExceptionZZZ {
		//Merke: Einfaches zusammenfuegen erzeugt ggfs. doppelte / . Die sind im Jar nicht erlaubt und daher würde kein gültiger Dateiname errechnet.
		//return this.computeCriterionPath(this.getDirectoryPath()+IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR+this.getFileName());
		return this.computeCriterionPath(JarEasyUtilZZZ.joinJarFilePathName(this.getDirectoryPath(), this.getFileName()));
	}
	
	public String computeCriterionPath(String sCriterion) throws ExceptionZZZ{
		String sReturn = sCriterion;
		main:{
		String sFileName = JarEasyUtilZZZ.computeFilenameFromJarPath(sCriterion);
		String sDirectory = JarEasyUtilZZZ.computeDirectoryFromJarPath(sCriterion);
		
		this.setDirectoryPath(sDirectory);
		this.setFileName(sFileName);
		sReturn = JarEasyUtilZZZ.joinJarFilePathName(sDirectory, sFileName);
		}//end main;
		return sReturn;
		
	}
	
	public String getCriterionPath()throws ExceptionZZZ{
		if(this.sCriterionPath==null) {
			String sCriterion = this.getCriterion();
			String stemp = this.computeCriterionPath(sCriterion);
			this.sCriterionPath = stemp;
		}
		return this.sCriterionPath;
	}
	
	private void setCriterionPath(String sCriterionPath) {
		this.sCriterionPath = sCriterionPath;
	}
	
	
	}
