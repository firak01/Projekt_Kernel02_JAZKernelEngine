/*
 * Created on 25.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file.transform;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.csv.FileCsvZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zKernel.file.transform.FileTransformCsv2IniZZZ;
import custom.zUtil.io.FileZZZ;

/**

@author 0823 ,date 25.10.2004
*/
public class DebugFileTransformCsv2IniZZZ {

	public static void main(String[] args) {
		
		KernelLogZZZ objLog=null;
			
			try {
			KernelZZZ objKernel = new KernelZZZ("FGL","03","C:\\tempfgl\\KernelConfig","ZKernelConfigKernel_default.ini",(String)null);
			objLog = objKernel.getLogObject();
	
			//die Verzeichnisse
			String sBaseDirectory = "c:\\lotus\\notes\\data\\fileZZZ";
			String sBaseFile = "adp_reisekostenexport.csv";
			FileZZZ objFileToCrack = new FileZZZ(sBaseDirectory, sBaseFile, "");
			
			
			//!!! n�chste Datei
			 String sFileExpanded = objFileToCrack.getNameExpandedCurrent();
			 
			 //als CSV			 			 
			 FileCsvZZZ objCsv= new FileCsvZZZ(objKernel, objLog, sBaseDirectory, sFileExpanded,null);

			//das Zielverzeichnis
			String sTargetDirectory = "c:\\tempfgl\\SI\\EDM_Interface\\file01ZZZ";

			//!!! die configuration (quasi das mapping von csv-->ini)
			FileIniZZZ objIniConfig = new FileIniZZZ(objKernel, "C:\\tempfgl\\KernelConfig","ZKernelConfigAction_transformCSV2Ini00.ini",(String[]) null);
			FileTransformCsv2IniZZZ objCrack = new FileTransformCsv2IniZZZ(objKernel, objLog, objCsv,objIniConfig, sTargetDirectory, (String[]) null);
			objCrack.startIt();
			
			} catch (ExceptionZZZ e) {
				try {
					objLog.writeLineDate(e.getDetailAllLast());
				} catch (ExceptionZZZ e1) {				
					e1.printStackTrace();
					System.out.println(e1.getDetailAllLast());
				}
			}
		
		
		System.out.println("Ende Debugging von 'FileTransformCsvZZZ'");
					System.exit(0);	
		}//end main
}//end debug-class
