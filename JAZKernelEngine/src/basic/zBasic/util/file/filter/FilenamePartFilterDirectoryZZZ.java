package basic.zBasic.util.file.filter;

import java.io.File;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;

public class FilenamePartFilterDirectoryZZZ extends AbstractObjectWithExceptionZZZ implements IFilenamePartFilterZZZ  {
	private String sFileEnding;
	
	public FilenamePartFilterDirectoryZZZ() {
		super();
	}
	public FilenamePartFilterDirectoryZZZ(String sFileEnding){
		super();
		this.setEnding(sFileEnding);
	}
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)	 
	 */
	@Override
	public boolean accept(File objFile, String sName) {
		boolean bReturn=false;
		main:{
			//if(objFile==null) break main;
			if(sName==null) break main;		
			
			try {
				if(StringZZZ.isEmpty(this.getEnding())) {
					bReturn = true;
					break main;
				}
				
						
				//Dateiendung berechnen
				String sEndingCur = FileEasyZZZ.NameEndCompute(sName);
				if(sEndingCur.equals(this.sFileEnding)) bReturn = true;	
				
			} catch (ExceptionZZZ ez) {
				ez.printStackTrace();
				return false;
			}
		}//END main:
		return bReturn;
	}
	
	/**A file filter, but unlike the method, implemented be the interface,
	 * this method doesn't need a string as parameter.
	 * @return boolean
	 *
	 * javadoc created by: 0823, 14.07.2006 - 11:43:07
	 */
	public boolean accept(File objFile){
		boolean bReturn = false;
		main:{
			if(objFile==null) break main;				
		
			String sName = objFile.getPath();
			
			//Ende berechnen		
			String sEndingCur = FileEasyZZZ.NameEndCompute(sName);
			if(sEndingCur.equals(this.sFileEnding)) bReturn = true;			
		}//END main:
		return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getEnding(){
		return this.sFileEnding;
	}
	public void setEnding(String sEnding){
		this.sFileEnding = sEnding;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setEnding(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getEnding();
	}

}
