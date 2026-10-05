/*
 * Created on 07.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.FileTextImportCrackZZZ;
import custom.zKernel.file.FileTextJoinZZZ;

/**

@author 0823 ,date 07.10.2004
*/
public class DebugFileTextJoinZZZ {

	public static void main(String[] args) {
		System.out.println("Starte Debugging von 'FileTextJoinZZZ'");
			main:
			{	
				KernelLogZZZ objLog=null;
			
				try {
				KernelZZZ objKernel = new KernelZZZ();
				objLog = objKernel.getLogObject();
		
				//die Verzeichnisse
				String sBaseDirectory = "c:\\tempfgl\\SI\\EDM_Interface\\file00ZZZ";
				String sBaseFile = "ln_testimport.txt";
			
				String sFileToCrack = "04000464.ka";
				File objFileToCrack = new File(sBaseDirectory + File.separator + sFileToCrack);
			
				FileTextJoinZZZ objJoin = new FileTextJoinZZZ(objKernel, objLog, sBaseDirectory, sBaseFile, null);
				FileTextImportCrackZZZ objCrack = new FileTextImportCrackZZZ(objKernel, objLog, objFileToCrack, null);
				objCrack.load();
				objCrack.crackCustom();
				
				
				objJoin.append(objCrack);
				
				
				//!!! n�chste Datei
				 sFileToCrack = "04000465.ka";
				 objFileToCrack = new File(sBaseDirectory + File.separator + sFileToCrack);

				objCrack = new FileTextImportCrackZZZ(objKernel, objLog, objFileToCrack, null);
				objCrack.load();
				objCrack.crackCustom();


				objJoin.append(objCrack);

				
				} catch (ExceptionZZZ e) {
					try {
						objLog.writeLineDate(e.getDetailAllLast());
					} catch (ExceptionZZZ e1) {				
						e1.printStackTrace();
						System.out.println(e1.getDetailAllLast());
					}
				}
		
			}//end main
			end:
			{
				System.out.println("Ende Debugging von 'FileTextJoinZZZ'");
				System.exit(0);
			}
	}
}
