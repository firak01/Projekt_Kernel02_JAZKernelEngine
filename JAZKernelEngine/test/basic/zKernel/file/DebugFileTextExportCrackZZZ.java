/*
 * Created on 08.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.FileTextExportCrackZZZ;

/**

@author 0823 ,date 08.10.2004
*/
public class DebugFileTextExportCrackZZZ {

	public static void main(String[] args) {
				KernelLogZZZ objLog=null;
			main:{
					try {
					//Erst einmal das Kernel-Objekt richtig initialisieren
					KernelZZZ objKernel = new KernelZZZ("FGL","03","C:\\tempfgl\\KernelConfig","ZKernelConfigKernel_default.ini",(String) null);
					objLog = objKernel.getLogObject();
		
					//die Verzeichnisse
					String sBaseDirectory = "c:\\lotus\\notes\\data\\filezzz";
					String sBaseFile = "adp_reisekostenexport.csv";
					File objFile = new File(sBaseDirectory + File.separator + File.separator + sBaseFile);
			
					String sProgramAlias = "FGL#03;split1";
					FileTextExportCrackZZZ objCrack = new FileTextExportCrackZZZ(objKernel, objLog, objFile, sProgramAlias, null);
					objCrack.crackCustom();
				
					} catch (ExceptionZZZ e) {
						try {
							objLog.writeLineDate(e.getDetailAllLast());
						} catch (ExceptionZZZ e1) {				
							e1.printStackTrace();
							System.out.println(e1.getDetailAllLast());
						}
					}
	}//end main
				System.out.println("Ende Debugging von 'FileTextCrackZZZ'");
				System.exit(0);
	}
}
