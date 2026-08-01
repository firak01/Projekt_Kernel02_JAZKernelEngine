package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;

public class FileDirectoryEmptyPartFilterZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFileDirectoryEmptyPartFilterZipZZZ{
	
	public FileDirectoryEmptyPartFilterZipZZZ() {
		super();
	}
	public FileDirectoryEmptyPartFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();		
		this.setDirectoryPath(sDirectoryPath);
	}
	
	@Override
	public boolean accept(ZipEntry ze) {
		 boolean bReturn=false;
		main:{			
			try {
				if(ze==null) break main;
				
				//Wichtig: Filtert dadurch nur Verzeichnisse!!!
				//ABER: In einer JAR DATEI GIBT ES DIESEN FALL NUR FÜR LEERE VERZEICHNISS ODER WENN DIE JAR DATEI EXPLIZIT MIT VERZEICHNISSEN ERSTELLT WURDE.
				if(!ze.isDirectory()) break main;

					if(StringZZZ.isEmpty(this.getCriterion())) {
						bReturn = true;
						break main;
					}						
				String sName = ze.getName();
								
				//Verzeichnisname mit dem Pfad vergleichen, d.h. damit bekommt man auch Unterverzeichnisse!!!
				if(StringZZZ.contains(sName, this.getCriterion(), true)){ 
					bReturn = true;	
				}
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
				return false;
			}	
		}//END main:
		return bReturn;
	}

	@Override
	public void setCriterion(String sCriterion) throws ExceptionZZZ {
		super.setCriterion(sCriterion);
		this.setDirectoryPath(sCriterion);
	}
}
