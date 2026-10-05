package basic.zKernel.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.*;


/**
 * @author Lindhauer
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DebugFileTextCopyZZZ {
	

	public static void main(String[] args) {
		System.out.println("Starte Debugging von 'FileTextCopyZZZ'");
	main:
	{	
		KernelLogZZZ objLog=null;			
		try {
		KernelZZZ objKernel = new KernelZZZ();
		objLog = objKernel.getLogObject();
		
			//die Verzeichnisse
			String sDirectorySource = "c:\\tempfgl\\SI\\EDM_Interface\\temp";
			String sDirectoryTarget = "c:\\tempfgl\\SI\\EDM_Interface\\file00ZZZ";
			
			//Die Dateinamen
			String[] saFileSource = 
			{
			"04000464.ka",
			"04000465.ka",
			"04000466.ka",
			"04000544.fe"
			};
			
			//F�r den Fall, dass der ganze Dateipfad �bergeben werden soll
			String[] saFileSourceTotal = new String[saFileSource.length];
			for(int iCount = 0; iCount < saFileSourceTotal.length; iCount++){
				saFileSourceTotal[iCount]=sDirectorySource + File.separator + saFileSource[iCount];
			}

			String[] saFileTarget = 
			{
			"",
			"",
			"",
			""
			};
			
			//Initialisieren der Objekte
			String[] saFlagControl = {
				"source_remove"
			};

			//Das ist die eine M�glichkeit
			FileTextCopyZZZ objCopy = new FileTextCopyZZZ(objKernel, objLog, sDirectorySource, sDirectoryTarget, saFileSource, saFileTarget,saFlagControl);
		   
		   //�bergabe der Dateien mit einen kompletten Pfad geht dann so:
		   //FileTextCopyZZZ objCopy = new FileTextCopyZZZ(objKernel, objLog, null, sDirectoryTarget, saFileSourceTotal, null, saFlagControl);
		    
		    //Die Dateien nun kopieren
		    objCopy.startit((String[])null);
		
		} catch (ExceptionZZZ e) {
			if(objLog==null){
				objLog = new KernelLogZZZ();
			}
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
		System.out.println("Ende Debugging von 'FileTextCopyZZZ'");
		System.exit(0);
	}
	}
}
