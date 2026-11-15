package basic.zBasic.util.file.zip;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import basic.zBasic.util.machine.EnvironmentZZZ;

public class ZipEasyZZZ implements IConstantZZZ {	
	/** Merke: Vewendet man lediglich das ArrayList.contains(ZipEntry....) dann ist man nicht erfolgreich,
	 *         wenn es darum geht lediglich nach dem Namen zu prüfen.
	 *         Vermutlich wird die Gleichheit der ZipEntry - Objekte auch noch hinsichtlich Größe, etc. durchgeführt.
	 *         
	 * @param listaZipEntry
	 * @param ze
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 17.12.2020, 14:26:43
	 */
	public static boolean containsByName(ArrayList<ZipEntry> listaZipEntry, ZipEntry ze) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			String sLog;
			if(listaZipEntry==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) ArrayList-Object is null.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + ": " + sLog, iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			if(ze==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) ZipEntry-Object is null.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + ": " + sLog, iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			
			String sName = ze.getName();
			for(ZipEntry zeTemp : listaZipEntry) {
				if(zeTemp.getName().equals(sName)) {
					bReturn = true;
					break main;
				}
			}								
		}//end main:
		return bReturn; 
	}
	public static File extractZipEntryTo(ZipFile zf, ZipEntry ze, File fileTargetIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog;
			if(fileTargetIn==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "File Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sFilePathTotal = fileTargetIn.getAbsolutePath();
			objReturn = ZipEasyZZZ.extractZipEntryTo_(zf, ze, sFilePathTotal);
						
		}//end main:
		return objReturn;   	
	}
	
	/** Erstellt aus einem Zip Entry eine tatsächliche Datei/Verzeichnis in dem TEMP-Verzeichnis auf der Maschine.
	 *  Der Dateiname entspricht dem ZipEntry-Namen.
	 * @param zf
	 * @param ze
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 12.11.2020, 08:21:58
	 */
	public static File extractZipEntryToTemp(ZipFile zf, ZipEntry ze) throws ExceptionZZZ {
		return ZipEasyZZZ.extractZipEntryToDirectoryRoot(zf, ze, null);		
	}
	
	/** Erstellt aus einem Zip Entry eine tatsächliche Datei in dem angegebenen Verzeichnis.
	 *  Wird kein Verzeichnis übergeben, ist das immer das TEMP-Verzeichnis auf der Maschine.
	 *  Der Dateiname entspricht dem ZipEntry-Namen.
	 * @param zf
	 * @param ze
	 * @param sDirPathRootIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 11.11.2020, 09:04:11
	 */
	public static File extractZipEntryToDirectoryRoot(ZipFile zf, ZipEntry ze, String sDirPathRootIn) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog = ReflectCodeZZZ.getPositionCurrent()+": START XXXXXXXXXXXXXX.";
		   	System.out.println(sLog);
		   	
			if(ze==null) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;
			}
			
			String sDirPathRoot;
			if(StringZZZ.isEmpty(sDirPathRootIn)) {
				String sDirPathTemp = EnvironmentZZZ.getHostDirectoryTemp();
				sDirPathRoot = FileEasyZZZ.joinFilePathName(sDirPathTemp, "ZZZ");
			}else {
				if(FileEasyZZZ.isPathRelative(sDirPathRootIn)) {
					String sDirPathTemp = EnvironmentZZZ.getHostDirectoryTemp();
					sDirPathRoot = FileEasyZZZ.joinFilePathName(sDirPathTemp, sDirPathRootIn);					
				}else {
					sDirPathRoot = sDirPathRootIn;
				}
			}
			
			String sKey = ze.getName();
			String sKeyNormed = StringZZZ.replace(sKey, "/", IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
			String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirPathRoot, sKeyNormed);
			sLog = ReflectCodeZZZ.getPositionCurrent()+": sFilePathTotal='" + sFilePathTotal + "'";
		   	System.out.println(sLog);
				
			objReturn = ZipEasyZZZ.extractZipEntryTo_(zf, ze, sFilePathTotal);	
									
		}//end main:
		return objReturn;
	}
	

	
	/**Diese Methodensignatur gibt es nicht: Files.copy(is,  os);
	 * Daher ist der Gesamtstring des Pfads der Übergabeparameter in dieser private Methode.
	 * 
	 * Ggfs. vorhandene Dateien werden gelöscht, um sie anschliessend neu zu erstellen.
	 * 
	 * @param zf
	 * @param ze
	 * @param sFilePathTotal
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 12.11.2020, 08:33:16
	 */
	private static File extractZipEntryTo_(ZipFile zf, ZipEntry ze, String sFilePathTotal) throws ExceptionZZZ {
		File objReturn = null;
		main:{
			String sLog;
			try {
				if(zf==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 01) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipFile Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				if(ze==null) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 02) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "ZipEntry Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				if(StringZZZ.isEmpty(sFilePathTotal)) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error 03) XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
				   	
					ExceptionZZZ ez = new ExceptionZZZ(sERROR_PARAMETER_MISSING + "FilePathTotal Object", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), "");
					throw ez;
				}
				
				if(ze.isDirectory()) {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": IS DIRECTORY.";
				   	System.out.println(sLog);
					
					FileEasyZZZ.createDirectory(sFilePathTotal);
					
					sLog = ReflectCodeZZZ.getPositionCurrent()+": IS DIRECTORY XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);
					
				}else {
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (JBBC) IS FILE.";
				   	System.out.println(sLog);
				   	
					//Nun aus dem ZipEntry ein File Objekt machen (geht nur in einem anderen Verzeichnis, als Kopie)																
					InputStream is = zf.getInputStream(ze);
					
					//!!! Bereits existierende Datei ggfs. löschen, Merke: Das ist aber immer noch nicht das Verzeichnis und die Datei, mit der in der Applikation gearbeitet wird.
					FileEasyZZZ.removeFile(sFilePathTotal);
					
					//!!! Notwendige Unterverzeichnisse wieder erstellen
					FileEasyZZZ.createDirectoryForFile(sFilePathTotal);
					sLog = ReflectCodeZZZ.getPositionCurrent()+": (IS FILE XXXXXXXXXXXXXX.";
				   	System.out.println(sLog);		
										
					//Diese Methodensignatur gibt es nicht: Files.copy(is,  os);
					//Daher ist der Gesamtstring des Pfads der Übergabeparameter in dieser private Methode.
					Path target = Paths.get(sFilePathTotal);
					Files.copy(is,target);
				}
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": CREATE RETURN FILE .";
			   	System.out.println(sLog);
			   	
				objReturn = new File(sFilePathTotal);
				
				sLog = ReflectCodeZZZ.getPositionCurrent()+": CREATE RETURN FILE XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
				
			} catch (IOException e) {
				sLog = ReflectCodeZZZ.getPositionCurrent()+": (Error) IOException: '" + e.getMessage() + "' XXXXXXXXXXXXXX.";
			   	System.out.println(sLog);
			   	
				ExceptionZZZ ez = new ExceptionZZZ("IOException: '" + e.getMessage() + "'", iERROR_RUNTIME,  ReflectCodeZZZ.getMethodCurrentName(), "");
				throw ez;	
			}
			
		}//end main:
		return objReturn;
	}
}
