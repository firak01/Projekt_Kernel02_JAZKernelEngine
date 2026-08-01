package basic.zBasic.util.file.jar;

import java.io.File;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IResourceHandlingObjectZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.JarKernelZZZ;

/** Methoden für die Arbeit mit der gleichen Jar Datei, wenn die Methoden aus der Jar-Datei aufgerufen werden.
    @author Fritz Lindhauer, 05.08.2020, 14:05:25

Aus Doku gründen stehen lassen: Alle Einträge eines Jar-Files durchgehen:				    
    final Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
    while(entries.hasMoreElements()) {
        final String name = entries.nextElement().getName();
        if (name.startsWith(sFile)) { //filter according to the path
            System.out.println("BINGO");
            System.out.println(name);
            break main;
        } else {
            //System.out.println(name);
        }
    } 
 */
public class JarEasyInCurrentJarZZZ implements IConstantZZZ, IResourceHandlingObjectZZZ {

    /** Prüft, ob der angegebene Pfad in der JAR ein Verzeichnis ist.
     * @param jar
     * @param sPath
     * @return boolean
     * @throws ExceptionZZZ
     * @author Fritz Lindhauer
     */
    public static boolean isDirectory(JarFile jar, String sPath) throws ExceptionZZZ {
        boolean bReturn = false;
        main: {
            if (StringZZZ.isEmpty(sPath)) {
                ExceptionZZZ ez = new ExceptionZZZ("No filepath provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
                throw ez;
            }
            if (jar == null) {
                ExceptionZZZ ez = new ExceptionZZZ("No JarFile-Object provided.", iERROR_PARAMETER_MISSING, JarEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
                throw ez;
            }

            JarEntry entry = JarEasyUtilZZZ.getEntryForDirectory(jar, sPath);
            if (entry == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) ENTRY IN JAR FILE NOT FOUND: '" + sPath + "'");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) ENTRY IN JAR FILE FOUND: '" + sPath + "'");

                // Merke: Der Zugriff auf Verzeichnis oder Datei muss anders erfolgen.
                if (entry.isDirectory()) { // Dateien nicht extrahieren!!!
                    bReturn = true;
                } else {
                    bReturn = false;
                }
            }
        } // end main:
        return bReturn;
    }

    /** Verbindet Dateipfad und Dateiname zu einem Pfad für URLs.
     * @param sFilePathIn
     * @param sFileNameIn
     * @return String
     * @throws ExceptionZZZ
     */
    public static String joinFilePathName(String sFilePathIn, String sFileNameIn) throws ExceptionZZZ {
        return FileEasyZZZ.joinFilePathNameForUrl(sFilePathIn, sFileNameIn);
    }

    /** Extrahiert die Datei als echte TEMP Datei. Verzeichnisse werden nicht angelegt.
     * @param sPath
     * @return File
     * @throws ExceptionZZZ
     * @author Fritz Lindhauer
     */
    public static File extractFileAsTemp(String sPath) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class, "(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.extractFileAsTemp(jar, sPath);
            }
        } // end main:
        return objReturn;
    }

    /** Prüft Verzeichnisse in der JAR und gibt ein Array von Dateien zurück.
     * @param sSourceDirectoryPath
     * @param sTargetDirectoryPathIn
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn);
            }
        } // end main:
        return objaReturn;
    }

    /** Prüft Verzeichnisse in der JAR inkl. Dateien.
     * @param sSourceDirectoryPath
     * @param sTargetDirectoryPathIn
     * @param bWithFiles
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] peekDirectories(String sSourceDirectoryPath, String sTargetDirectoryPathIn, boolean bWithFiles) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                objaReturn = JarEasyZZZ.peekDirectories(jar, sSourceDirectoryPath, sTargetDirectoryPathIn, bWithFiles);
            }
        } // end main:
        return objaReturn;
    }

    /** Gibt die erste Datei im Pfad zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File
     * @throws ExceptionZZZ
     */
    public static File peekFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.peekFileFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objReturn;
    }

    /** Gibt alle Dateien im Pfad zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] peekFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objaReturn = JarEasyZZZ.peekFiles(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objaReturn;
    }

    /** Gibt das erste Verzeichnis im Pfad zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File
     * @throws ExceptionZZZ
     */
    public static File peekDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.peekDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objReturn;
    }

    /** Sucht ein Verzeichnis in der JAR und gibt es zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File
     * @throws ExceptionZZZ
     */
    public static File searchResourceDirectoryFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objReturn;
    }

    /** Sucht eine Datei in der JAR und gibt sie zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File
     * @throws ExceptionZZZ
     */
    public static File searchResourceFileFirst(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.searchResourceFileFirst(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objReturn;
    }

    /** Sucht mehrere Dateien in der JAR und gibt sie zurück.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] searchResourceFiles(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objaReturn = JarEasyZZZ.searchResourceFiles(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objaReturn;
    }

    /** Sucht ein Verzeichnis in der JAR inkl. Dateien.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @param bWithFiles
     * @return File
     * @throws ExceptionZZZ
     */
    public static File searchResourceDirectory(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
        File objReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objReturn = JarEasyZZZ.searchResourceDirectoryFirst(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
            }
        } // end main:
        return objReturn;
    }

    /** Sucht mehrere Ressourcen in der JAR.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class,"(D) JAR FILE FOUND.");
                objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn);
            }
        } // end main:
        return objaReturn;
    }

    /** Sucht mehrere Ressourcen in der JAR inkl. Dateien.
     * @param sPath
     * @param sTargetDirectoryPathRootIn
     * @param bWithFiles
     * @return File[]
     * @throws ExceptionZZZ
     */
    public static File[] searchResources(String sPath, String sTargetDirectoryPathRootIn, boolean bWithFiles) throws ExceptionZZZ {
        File[] objaReturn = null;
        main: {
            JarFile jar = JarKernelZZZ.getJarFileCurrent();
            if (jar == null) {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class, "(D) JAR FILE NOT FOUND.");
            } else {
                ObjectZZZ.logLineDate(JarEasyInCurrentJarZZZ.class, "(D) JAR FILE FOUND.");
                objaReturn = JarEasyZZZ.searchResources(jar, sPath, sTargetDirectoryPathRootIn, bWithFiles);
            }
        } // end main:
        return objaReturn;
    }

    /** Prüft, ob die aktuelle Klasse in einer JAR-Datei läuft (statisch).
     * @return boolean
     * @throws ExceptionZZZ
     */
    public static boolean isInJarStatic() throws ExceptionZZZ {
        boolean bReturn = false;
        main: {
            bReturn = JarEasyUtilZZZ.isInJar(JarEasyInCurrentJarZZZ.class);
        }
        return bReturn;
    }

    /** Prüft, ob diese Instanz in einer JAR-Datei läuft.
     * @return boolean
     * @throws ExceptionZZZ
     */
    public boolean isInJar() throws ExceptionZZZ {
        boolean bReturn = false;
        main: {
            bReturn = JarEasyUtilZZZ.isInJar(this.getClass());
        }
        return bReturn;
    }
}
