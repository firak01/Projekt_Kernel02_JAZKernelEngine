package basic.zBasic.util.file;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**Einfache Operationen, die sich auf ein Laufwerk beziehen
 * @author lindhaueradmin
 *
 */
public class DriveEasyZZZ extends AbstractObjectWithExceptionZZZ{
	private DriveEasyZZZ(){
//		Zum Verstecken des Konstruktors
	}
	
	public static String getTitle(File fileRoot) throws ExceptionZZZ{
		String sReturn = null;;
		main:{
			if(FileEasyZZZ.isRoot(fileRoot)==false) break main;
			
			//Die fileSystemView - Methode funktioniert nur, wenn der absolute Pfad des �bergebenen File-Objekts einen Backslash zum Abschluss hat.
			File file2use = null;
			if(!fileRoot.getAbsolutePath().endsWith(File.separator)){
				file2use = new File(fileRoot.getAbsolutePath() + File.separator);
			}else{
				file2use = fileRoot;
			}
			
			//JFileChooser fc = new JFileChooser();
			//FileSystemView fileSystemView = fc.getFileSystemView();
			FileSystemView fileSystemView = FileSystemView.getFileSystemView();
			String stemp = fileSystemView.getSystemDisplayName(file2use);		
			System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#fileSystemView.getSystemDisplayName() liefert f�r '" + fileRoot.getAbsolutePath() + "' den Wert '" + stemp + "'");
			
			//!!! Das enth�lt aber auch den Laufwerksbuxhstaben (in Klammern) diesen also rausrechnen
			int iLeft = stemp.lastIndexOf("(");
			if (iLeft == 0){
				sReturn = "";
				break main;
			}
			if(iLeft>=1){
				int iRight = stemp.lastIndexOf(")");
				if(iRight<=iLeft){
					//??? dann ist etwas nicht so wie ich es erwartet habe
					sReturn = stemp;
					break main;
				}else{
					//Hinter der rechten Klammer darf kein weiterer String stehen
					if(iRight!=stemp.length()-1){				
						sReturn = stemp; //Das hatte ich nicht erwartet.
						break main;
					}else{
						sReturn = stemp.substring(0,iLeft-1);
					}
				}
			}else{
				//Scheint doch kein geklammerter Laufwerksbuchstabe drin enthalten zu sein. Nur falls "betriebsystemabh�ngig"
				sReturn = stemp;
				break main;
			}
		}
		System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "#R�ckgabewert ist: '" + sReturn + "'");
		return sReturn;
	}
	
	/** Gibt den Namen der Festplatte, der CD etc. zur�ck und am Schluss den Laufwerksbuchstaben in runden Klammern.
	 *  z.B. "Hannibal (C:)"
	 *  
	* @param fileRoot
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 06.02.2007 13:52:00
	 */
	public String getTitleDetail(File fileRoot) throws ExceptionZZZ{
		String sReturn = null;;
		main:{
			if(FileEasyZZZ.isRoot(fileRoot)==false) break main;
			FileSystemView fileSystemView = FileSystemView.getFileSystemView();
			
			sReturn = fileSystemView.getSystemDisplayName(fileRoot);
		}
		return sReturn;
	}
	
	
	/** gibt als Beschreibungstext zur�ck, um was f�r einen Laufwerkstyp es sich handelt:
	 * z.B. CD-Laufwerk, lokaler Datentr�ger
	 * 
	 * ACHTUNG: Das ist nat�rlich voll vom Betriebsystem und der dort installierten Sprache abh�ngig.
	 * 
	* @param fileRoot
	* @return
	* @throws ExceptionZZZ
	* 
	* lindhaueradmin; 06.02.2007 14:10:45
	 */
	public static String getTypeDescriptonString(File fileRoot) throws ExceptionZZZ{
		String sReturn = null;;
		main:{
			if(FileEasyZZZ.isRoot(fileRoot)==false) break main;
			FileSystemView fileSystemView = FileSystemView.getFileSystemView();
			
			sReturn = fileSystemView.getSystemTypeDescription(fileRoot);
		}
		return sReturn;
	}	
}
