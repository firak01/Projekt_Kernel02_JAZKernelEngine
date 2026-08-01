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

public class FilenamePartFilterPathZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFilenamePartFilterZipZZZ  {
			
	public FilenamePartFilterPathZipZZZ() {
		super();
	}
	public FilenamePartFilterPathZipZZZ(String sDirectoryPath, String sFileName) throws ExceptionZZZ{
		super();
		this.setDirectoryPath(sDirectoryPath);		
	}
	
	@Override
	public boolean accept(ZipEntry ze) {		 
		    boolean bReturn=false;
			main:{
				if(ze==null) break main;
				
				try {
					if(ze.isDirectory()) break main;
					
					String sName = ze.getName();				
					String sCriterion = this.getCriterion();
					if(StringZZZ.isEmpty(this.getDirectoryPath())) {
						if(StringZZZ.startsWithIgnoreCase(sName, sCriterion))	{					
							bReturn = true;
							break main;
						}
					}else {							
							//Pfad des Dateinamens berechnen			
							String sDirectoryPath=null;			
							//String zurückgeben, einfach den Parent des Dateipfads. //Wichtig: Der abschliessende Slash dient dazu die Verzeichnispfade zu "normieren".
							sDirectoryPath = FileEasyZZZ.getParent(sName,"/")+"/";//Also: In der aktuell  betrachteten JAR - Datei sind die Pfade mit "SLASH" getrennt.
							if(StringZZZ.startsWithIgnoreCase(sDirectoryPath, sCriterion))	{					
								bReturn = true;
								break main;
							}
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
	public void setCriterion(String sCriterion) throws ExceptionZZZ {	
		this.setDirectoryPath(sCriterion);		
	}
	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
