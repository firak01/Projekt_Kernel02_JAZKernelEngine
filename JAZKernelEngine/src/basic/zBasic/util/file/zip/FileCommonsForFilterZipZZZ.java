package basic.zBasic.util.file.zip;

import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.jar.JarEasyUtilZZZ;
import basic.zBasic.util.file.jar.JarEasyZZZ;

public class FileCommonsForFilterZipZZZ implements IConstantZZZ,IResourceHandlingObjectZZZ{

		public static String  computeDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ {
			String sReturn = "";
			main:{
				if(StringZZZ.isEmpty(sDirectoryPathIn)) break main;
		
				String sDirectoryPath = JarEasyUtilZZZ.toJarDirectoryPath(sDirectoryPathIn);				
				boolean bCheck = JarEasyUtilZZZ.isJarPathDirectoryValid(sDirectoryPath);
				if(!bCheck) {
					ExceptionZZZ ez = new ExceptionZZZ("Provided Path is not a valid JarFileDirectory: " + sDirectoryPathIn + "'", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
				
				sReturn = sDirectoryPath;
			}//end main:			
			return sReturn;		
		}
		
		
		
		
		//#################################################
		//Interface IResourceHandlingObjectZZZ
		@Override
		public boolean isInJar() throws ExceptionZZZ {
			boolean bReturn = false;
			main:{
				bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
			}
			return bReturn;
		}
		
}
