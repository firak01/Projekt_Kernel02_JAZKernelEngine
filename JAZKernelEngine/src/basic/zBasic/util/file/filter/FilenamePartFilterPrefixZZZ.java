package basic.zBasic.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterPrefixZZZ extends AbstractObjectWithExceptionZZZ implements IFilenamePartFilterZZZ  {
	private String sFilePrefix;
	
	public FilenamePartFilterPrefixZZZ() {
		super();
	}
	public FilenamePartFilterPrefixZZZ(String sFilePrefix){
		super();
		this.setPrefix(sFilePrefix);
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
				if(StringZZZ.isEmpty(this.getPrefix())) {
					bReturn = true;
					break main;
				}
							
				//Anfang des Dateinamens berechnen			
				String sPrefixCur = null;					
				sPrefixCur = FileEasyZZZ.getNameOnly(sName);
				if(StringZZZ.startsWithIgnoreCase(sPrefixCur, this.getPrefix())) bReturn = true;
			} catch (ExceptionZZZ e) {			
				e.printStackTrace();
				return false;
			} 					
		}//END main:
		return bReturn;
	}
	
	/**A file filter, but unlike the method, implemented be the interface,
	 * this method doesn�t need a string as parameter.
	 * @return boolean
	 *
	 * javadoc created by: 0823, 14.07.2006 - 11:43:07
	 */
	//@Override
	public boolean accept(File objFile){
		boolean bReturn = false;
		main:{
			if(objFile==null) break main;				
					
			String sName = objFile.getPath();
			
			//Ende berechnen		
			String sPrefixCur;
			try {
				sPrefixCur = FileEasyZZZ.getNameOnly(sName);
				if(StringZZZ.startsWithIgnoreCase(sPrefixCur, this.getPrefix())) bReturn = true;
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		}//END main:
		return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getPrefix(){
		return this.sFilePrefix;
	}
	public void setPrefix(String sPrefix){
		this.sFilePrefix = sPrefix;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setPrefix(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getPrefix();
	}

}
