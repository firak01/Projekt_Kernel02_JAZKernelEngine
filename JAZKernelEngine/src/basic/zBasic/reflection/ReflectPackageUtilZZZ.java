package basic.zBasic.reflection;

/** aus http://www.java2s.com/example/java-utility-method/package-convert-to/packagetosrcfilepath-string-packagename-a898b.html
 * @author Fritz Lindhauer, 22.06.2023, 19:32:24
 * 
 */
public class ReflectPackageUtilZZZ {
	
	private ReflectPackageUtilZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	 public static String packageToPath(String basePackage) {
	        return basePackage.replaceAll("\\.", "/") + "/";
	 }
	 
//	 public static String packageToPath(String pkg) {
//	        return "src/main/java/" + pkg.replace(".", "/");
//	    }
	 
	 public static String packageToSrcFilePath(String packageName) {
	        return packageName.replace(".", "/");
	    }
	 
	 public static String packageToResourcePath(String thePackage) {
	        return thePackage.replaceAll("\\.", "/");
	  }
	 
	  /** get an Char into int depending on the package it represent
      *
      * @param pack   the package
      * @return 0 for "A",1 for "B" ....
      *
    */
    public static int PackageToInt(char pack) { // package to int A = 0  Z = 25 
        int iPack;
        iPack = (int) pack - 65;

        if (iPack >= 0 && iPack <= 25)
            return iPack;
        else
            return 42;
    }
	  
    
    public static String packageToDir(String packages) {
        String dir = packages;
        if (packages != null && packages.length() > 1) {
            dir = packages.replaceAll("[.]", "/");
        }
        return dir;
    }
    
    /**
     * Return a directory path with the given directory + the given 
     * @param sDestDir file system directory ( ie "/tmp/src" )
     * @param sPackage package name ( ie "org.demo.vo.bean" )
     * @return full path of the class file ( ie "/tmp/src/org/demo/vo/bean" )
     */
    public static String packageToDirectory(String sDestDir, String sPackage) {
        // --- Replace . by / in the path
        String sPackageDir = sPackage.replace('.', '/');
        if (sDestDir.endsWith("/") || sDestDir.endsWith("\\")) {
            return sDestDir + sPackageDir;
        } else {
            return sDestDir + "/" + sPackageDir;
        }
    }
	  
    public static String packageToPath(Object obj) {
        return packageToPath(obj.getClass());
    }

    public static String packageToPath(Class objClass) {
        String className = objClass.getName();
        int lastDotIndex = className.lastIndexOf(".");
        return (lastDotIndex < 0) ? "" : className.substring(0, lastDotIndex).replace('.', '/');
    }
}
