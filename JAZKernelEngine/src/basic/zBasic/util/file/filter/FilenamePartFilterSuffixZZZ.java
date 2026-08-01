package basic.zBasic.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterSuffixZZZ extends AbstractObjectWithExceptionZZZ implements IFilenamePartFilterZZZ  {
	private String sFileSuffix;
	
	public FilenamePartFilterSuffixZZZ() {
		super();
	}
	public FilenamePartFilterSuffixZZZ(String sFileSuffix){
		super();
		this.setSuffix(sFileSuffix);
	}
	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)	 
	 */
	public boolean accept(File objFile, String sName) {
		boolean bReturn=false;
		main:{
			//if(objFile==null) break main;
			if(sName==null) break main;
			
			try {
				if(StringZZZ.isEmpty(this.getSuffix())) {
					bReturn = true;
					break main;
				}
					
				//Ende des Dateinamens berechnen			
				String sSuffixCur=null;		
				sSuffixCur = FileEasyZZZ.getNameOnly(sName);
				if(StringZZZ.endsWithIgnoreCase(sSuffixCur, this.getSuffix())) bReturn = true;
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
	public boolean accept(File objFile){
		boolean bReturn = false;
		main:{
			if(objFile==null) break main;				
		
			try {
				String sName = objFile.getPath();
				
				if(StringZZZ.isEmpty(this.getSuffix())) {
					bReturn = true;
					break main;
				}
			
				
				//Ende des Dateinamens berechnen			
				String sSuffixCur=null;							
				sSuffixCur = FileEasyZZZ.getNameOnly(sName);
				if(StringZZZ.endsWithIgnoreCase(sSuffixCur, this.getSuffix())) bReturn = true;
			} catch (ExceptionZZZ e) {			
				e.printStackTrace();
				return false;
			} 								
		}//END main:
		return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getSuffix(){
		return this.sFileSuffix;
	}
	public void setSuffix(String sSuffix){
		this.sFileSuffix = sSuffix;
	}
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setSuffix(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getSuffix();
	}
}
