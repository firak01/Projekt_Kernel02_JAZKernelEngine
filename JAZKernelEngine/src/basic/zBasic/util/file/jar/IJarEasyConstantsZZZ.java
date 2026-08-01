package basic.zBasic.util.file.jar;

public interface IJarEasyConstantsZZZ {
	public static final int iJAR_NULL = 0;
	
	public static final int iJAR_DUMMY = 10;
	public static final String sJAR_DIRECTORYPATH_DUMMY = "c:\\\\1fgl\\\\client\\\\JAZKernel";
	public static final String sJAR_FILENAME_DUMMY = "JAZDummy.jar";
	
	public static final int iJAR_TEST = 20;
	public static final String sJAR_DIRECTORYPATH_TEST = "c:\\\\1fgl\\\\client\\\\JAZKernel";
	public static final String sJAR_FILENAME_TEST = "JAZDummyJar.jar"; //Jar-File mit spezieller Struktur der Verzeichnisse und Dateien, für die Tests.
	
	public static final int iJAR_TESTZIP = 21;
	public static final String sJAR_DIRECTORYPATH_TESTZIP = "c:\\\\1fgl\\\\client\\\\JAZKernel";
	public static final String sJAR_FILENAME_TESTZIP = "JAZDummyJar.zip"; //In einem Jar File gibt es keine Verzeichnisse ohne Dateien, in einem Zip-File schon.
	
	
	public static final int iJAR_KERNEL = 30;
	public static final String sJAR_DIRECTORYPATH_KERNEL = "c:\\\\1fgl\\\\client\\\\JAZKernel";
	public static final String sJAR_FILENAME_KERNEL = "JAZKernel.jar";
	

	
	//########### Jar Dateien aus den Projekten
	public static final int iJAR_OVPN = 100;
	public static final String sJAR_DIRECTORYPATH_OVPN = "C:\\\\1fgl\\\\client\\\\OVPN";
	public static final String sJAR_FILENAME_OVPN = "OpenVPNZZZ.jar";

	String sDIRECTORY_SEPARATOR = "/";

	//Findet alle die nicht mit / beginnen und mit / enden .... 
	String sDIRECTORY_VALID_REGEX="^(?![/]{1,}).*([/]$)";

	//Findet alle die nicht mit / beginnen und nicht mit / enden ....
	//https://stackoverflow.com/questions/16398471/regex-for-string-not-ending-with-given-suffix
	String sFILE_VALID_REGEX="^(?![/]{1,}).*(?<![/])$";
}
