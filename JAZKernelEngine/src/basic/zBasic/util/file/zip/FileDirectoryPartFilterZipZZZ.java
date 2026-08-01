package basic.zBasic.util.file.zip;

import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;

public class FileDirectoryPartFilterZipZZZ extends AbstractFilenamePartFilterZipZZZ implements IFileDirectoryPartFilterZipZZZ{	
	public FileDirectoryPartFilterZipZZZ() {
		super();
	}
	public FileDirectoryPartFilterZipZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();		
		this.setDirectoryPath(sDirectoryPath);
	}
	
	
	@Override
	public boolean accept(ZipEntry ze) {
		 boolean bReturn=false;
			main:{
				if(ze==null) break main;
				
				try {
					//!!! Hier werden NUR Dateien abgeholt!!!
					if(ze.isDirectory()) break main;
					if(StringZZZ.isEmpty(this.getCriterion())) {
						bReturn = true;
						break main;
					}			
													
					String sName = ze.getName();
					//#############################################
	//					if(ze.isDirectory()) {
	//						System.out.println("STOP: REINES VERZEICHNIS "+ze.getName());
	//						
	//						if(StringZZZ.contains(sName,"template")) {
	//							System.out.println("STOP: BREAKPOINT01");
	//							System.out.println("------");
	//						}
	//						
	//						if(StringZZZ.contains(sName,"subDirectory01")) {
	//							System.out.println("STOP: BREAKPOINT02");
	//							System.out.println("------");
	//						}
	//					}					
					//#############################################
					//Dateipfad mit dem Pfad vergleichen!!!	
					String sCriterion = this.getCriterion();
					if(StringZZZ.contains(sName, sCriterion, true)){ 
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
		this.setDirectoryPath(sCriterion);
	}

	@Override
	public String getCriterion() {
		return this.getDirectoryPath();
	}
	

}
