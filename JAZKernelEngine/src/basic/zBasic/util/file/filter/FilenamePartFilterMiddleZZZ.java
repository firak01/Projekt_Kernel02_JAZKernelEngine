package basic.zBasic.util.file.filter;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import custom.zUtil.io.FileZZZ;

public class FilenamePartFilterMiddleZZZ extends AbstractObjectWithExceptionZZZ implements IFilenamePartFilterZZZ  {
	private String sFileMiddle;
	
	public FilenamePartFilterMiddleZZZ() {
		super();
	}
	public FilenamePartFilterMiddleZZZ(String sFileMiddle){
		super();
		this.setMiddle(sFileMiddle);
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
				if(StringZZZ.isEmpty(this.getMiddle())) {
					bReturn = true;
					break main;
				}
							
				//"Mitte" des Dateinamens berechnen			
				String sMiddleCur = null;
				sMiddleCur = FileEasyZZZ.getNameOnly(sName);				
				sMiddleCur = StringZZZ.midBounds(sMiddleCur, 1, 1);
				if(StringZZZ.contains(sMiddleCur, this.getMiddle())) bReturn = true;
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
			String sMiddleCur;
			try {
				sMiddleCur = FileEasyZZZ.getNameOnly(sName);
				sMiddleCur = StringZZZ.midLeftRight(sMiddleCur, 1, 1);
				if(StringZZZ.contains(sMiddleCur, this.getMiddle())) bReturn = true;
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
				return false;
			}
					
		}//END main:
		return bReturn;
	}
	
	//########################
	//### Getter / Setter
	public String getMiddle(){
		return this.sFileMiddle;
	}
	public void setMiddle(String sMiddle){
		this.sFileMiddle = sMiddle;
	}
	
	//### Aus Interface
	@Override
	public void setCriterion(String sCriterion) {
		this.setMiddle(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getMiddle();
	}

}
