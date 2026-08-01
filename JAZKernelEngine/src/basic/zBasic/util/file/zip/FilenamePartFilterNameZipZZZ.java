package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.IJarEasyConstantsZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterNameZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFilenamePartFilterZipZZZ  {	
	public FilenamePartFilterNameZipZZZ() {
		super();
	}
	public FilenamePartFilterNameZipZZZ(String sFileName){
		super();
		this.setFileName(sFileName);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;	
				try {
					if(StringZZZ.isEmpty(this.getFileName())) {
						bReturn = true;
						break main;
					}				
					String sName = ze.getName();
					
					//Ende des JarPfads als Dateinamens berechnen			
					String sNameCur=null;					
					String sCriterion = this.getCriterion();
					if(StringZZZ.endsWithIgnoreCase(sName, sCriterion)) {
						bReturn = true;
						break main;
					}
			    } catch (ExceptionZZZ e) {				
					e.printStackTrace();
					return false;
				}						
			}//END main:
			return bReturn;
	}
	
	//########################
	//### Getter / Setter

	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setFileName(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return IJarEasyConstantsZZZ.sDIRECTORY_SEPARATOR + this.getFileName();
	}
	

}
