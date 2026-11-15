package basic.zBasic.util.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.regex.Matcher;

import org.apache.commons.io.FileUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zKernel.AbstractKernelObjectZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;

/**Einfache Dateioperationen
 * @author lindhaueradmin
 *
 */
public class FileArrayEasyZZZ extends AbstractObjectWithExceptionZZZ{
	
private FileArrayEasyZZZ(){
	//Zum Verstecken des Konstruktors
}

public static File[] add(File[] objaFile, File objFile) throws ExceptionZZZ {
	File[] objaReturn = null;
	main:{
		ArrayList<File> listaFile = new ArrayListZZZ<File>(objaFile);		
		listaFile.add(objFile);
		objaReturn = ArrayListUtilZZZ.toFileArray(listaFile);
	}//end main:
	return objaReturn;
}

public static boolean contains(File[] objaFile, String sFilePath) {
	boolean bReturn = false;
	main:{
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) break main;
	
	for(File objFileTemp:objaFile) {
		if(objFileTemp!=null) {
			String sPath = objFileTemp.getAbsolutePath();
			if(sPath.endsWith(sFilePath)){
				bReturn = true;
				break;
			}
		}
	}
	}//end main
	return bReturn;	 
}

public static boolean containsAbsolute(File[] objaFile, String sFilePath) {
	boolean bReturn = false;
	main:{
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) break main;
	
	for(File objFileTemp:objaFile) {
		if(objFileTemp!=null) {
			String sPath = objFileTemp.getAbsolutePath();
			if(sPath.equals(sFilePath)){
				bReturn = true;
				break;
			}
		}
	}
	
	}//end main
	return bReturn;	 
}

public static boolean containsName(File[] objaFile, String sFileName) {
	boolean bReturn = false;
	main:{
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) break main;
	
	for(File objFileTemp:objaFile) {
		if(objFileTemp!=null) {
			String sName = objFileTemp.getName();
			if(sName.equals(sFileName)){
				bReturn = true;
				break;
			}
		}
	}
	
}//end main
return bReturn;	 	 
}

/**returns true if the string is empty or null.
 * FGL: D.h. NULL oder Leerstring 
 * 
 * @param sString
 * @return, 
 *
 * @return boolean
 *
 * javadoc created by: 0823, 24.07.2006 - 08:52:50
 */
public static boolean isEmpty(File[] objaFile){
	if (FileArrayEasyZZZ.isEmptyNull(objaFile)) return true;
	for(int iCounter = 0; iCounter <= objaFile.length-1; iCounter++) {
		File objFile = objaFile[iCounter];
		if(objFile!=null) return false;//Falls nur ein Objekt vorhanden ist, ist das Array nicht leer
	}
	return true;
}

public static boolean isEmptyNull(File[] objaFile){		
	if(objaFile==null) return true;
	if(objaFile.length==0) return true;
	return false;
}



}//END class
