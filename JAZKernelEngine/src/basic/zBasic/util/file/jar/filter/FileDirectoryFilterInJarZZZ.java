package basic.zBasic.util.file.jar.filter;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryWithContentPartFilterZipZZZ;

/** Filter - Oberklasse für den Unterfilter, der nur Verzeichnisse zurückgibt.
 * @author Fritz Lindhauer, 16.11.2020, 08:29:53
 * 
 */
public class FileDirectoryFilterInJarZZZ extends AbstractFileDirectoryFilterInJarZZZ{

	public FileDirectoryFilterInJarZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super(sDirectoryPath);
	}
}
