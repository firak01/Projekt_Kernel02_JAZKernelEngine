package basic.zBasic.util.file.jar.filter;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.filter.AbstractFileFilterZZZ;
import basic.zBasic.util.file.filter.FilenamePartFilterEndingZZZ;
import basic.zBasic.util.file.filter.FilenamePartFilterMiddleZZZ;
import basic.zBasic.util.file.filter.FilenamePartFilterPrefixZZZ;
import basic.zBasic.util.file.filter.FilenamePartFilterSuffixZZZ;

public class FileFileFilterInJarZZZ extends AbstractFileFileFilterInJarZZZ{	
	public static String sDIRECTORY_PATH_DEFAULT="";
	public static String sNAME_DEFAULT=""; //Der ganze Dateiname
	public static String sPREFIX_DEFAULT="";
	public static String sMIDDLE_DEFAULT="";
	public static String sSUFFIX_DEFAULT="";
	public static String sENDING_DEFAULT="";
	
	public FileFileFilterInJarZZZ(String sName) throws ExceptionZZZ{
		super(sName);
		FileFilterInJarNew_();
	}
	public FileFileFilterInJarZZZ(String sDirectory, String sName, String[] saFlagControl) throws ExceptionZZZ {
		super(sDirectory, sName, saFlagControl);	
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ(String sDirectory, String sName, String sFlagControl) throws ExceptionZZZ {
		super(sDirectory, sName, sFlagControl);
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ(String sDirectory, String sName) throws ExceptionZZZ {
		super(sDirectory, sName);		
		FileFilterInJarNew_();
	} 
	public FileFileFilterInJarZZZ() throws ExceptionZZZ {
		super();		
		FileFilterInJarNew_();
	}
	private void FileFilterInJarNew_() {		
		//Merke: Hier könnenten dann in einer speziellen Klasse Statische Konstantenwerte gesetzt werden, z.B.:
		//this.setPrefix(ConfigFileTemplateOvpnOVPN.sFILE_TEMPLATE_PREFIX);
		//this.setMiddle(this.getOvpnContext());
	}
	
	//##### GETTER / SETTER	
	public void setDirectoryPath(String sDirectoyPath) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sDirectoyPath)) {
			super.setDirectoryPath(FileFileFilterInJarZZZ.sDIRECTORY_PATH_DEFAULT);
		}else {
			super.setDirectoryPath(sDirectoyPath);
		}
	}
	
	public void setName(String sName) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sName)) {
			super.setName(FileFileFilterInJarZZZ.sNAME_DEFAULT);
		}else {
			super.setName(sName);
		}
	}
	
	public void setPrefix(String sPrefix) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sPrefix)) {
			super.setPrefix(FileFileFilterInJarZZZ.sPREFIX_DEFAULT);
		}else {
			super.setPrefix(sPrefix);
		}
	}
	
	public void setMiddle(String sMiddle) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sMiddle)) {
			super.setMiddle(FileFileFilterInJarZZZ.sMIDDLE_DEFAULT);
		}else {
			super.setMiddle(sMiddle);
		}
	}
	
	public void setSuffix(String sSuffix) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sSuffix)) {
			super.setSuffix(FileFileFilterInJarZZZ.sSUFFIX_DEFAULT);
		}else {
			super.setSuffix(sSuffix);
		}
	}

				
	public void setEnding(String sEnding) throws ExceptionZZZ {
		if(StringZZZ.isEmpty(sEnding)) {
			super.setEnding(FileFileFilterInJarZZZ.sENDING_DEFAULT);
		}else {
			super.setEnding(sEnding);
		}
	}

}//END class