package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;

public class FileDirectoryWithContentPartFilterZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFileDirectoryWithContentPartFilterZipZZZ{
	
	public FileDirectoryWithContentPartFilterZipZZZ() {
		super();
	}
	public FileDirectoryWithContentPartFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();		
		this.setDirectoryPath(sDirectoryPath);
	}
	
	public void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ {
		String sDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPathIn);
		super.setDirectoryPath(sDirectoryPath);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {
		 boolean bReturn=false;
			main:{
			 try {
				if(ze==null) break main;
				
				//!!! Hier werden Verzeichnisse ausgeblendet, nur Dateien geholt.
				if(ze.isDirectory()) break main;
				
				if(StringZZZ.isEmpty(this.getCriterion())) {
					bReturn = true;
					break main;
				}				
				String sName = ze.getName();
								
				//Verzeichnisname vergleichen, hinsichtlich Namensanfang. Also nur die Dateien in exakt diesem Verzeichnis, bzw. in Unterverzeichnissen davon. 
								
				//Pfad des Dateinamens berechnen
				//Merke: Die "/" dienen jetzt dazu den Verzeichnisnamen "zu normieren". So dass in "tester/" nicht "test/" gefunden wird.
				sName = JarEasyUtilZZZ.toJarDirectoryPath(sName);
				String sNameSearchedFor = JarEasyUtilZZZ.toJarDirectoryPath(this.getCriterion());			
				if(StringZZZ.startsWithIgnoreCase(sName, sNameSearchedFor)){					
					bReturn = true;
				}	
			} catch (ExceptionZZZ e) {			
				e.printStackTrace();
			} 	
		}//END main:
		return bReturn;
	}

	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ {
		this.setDirectoryPath(sCriterion);
	}

	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
