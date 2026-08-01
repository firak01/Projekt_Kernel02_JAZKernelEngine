package custom.zKernel;

import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_NAME_DEFAULT;
import static basic.zKernel.IKernelConfigConstantZZZ.sLOG_FILE_DIRECTORY_DEFAULT;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.FileTextWriterZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerUserZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zKernel.IKernelConfigUserZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import custom.zUtil.io.FileZZZ;

public interface ILogZZZ extends IKernelConfigUserZZZ, IStringFormatManagerUserZZZ, ILogStringComputerZZZ{
//	public enum FLAGZ{
//		USE_FILE_EXPANSION; //Merke: DEBUG und INIT aus ObjectZZZ sollen über IObjectZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von File geerbt.
//	}
	
	public String getFilename() throws ExceptionZZZ;
	public void setFilename(String sFilename) throws ExceptionZZZ;
	
	public String getFilenameExpanded() throws ExceptionZZZ;
	
	public String getDirectory() throws ExceptionZZZ;
	public void setDirectory(String sDirectorypath) throws ExceptionZZZ;
	
	public FileZZZ getFileObject() throws ExceptionZZZ;
	public void setFileObject(FileZZZ objFile) throws ExceptionZZZ;
	
	public FileTextWriterZZZ getFileTextWriterObject() throws ExceptionZZZ;
	
	public boolean write(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean writeLine(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean writeLineDate(String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean writeLineDate(String sLog1, String sLog2) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	public boolean writeLineDate(String... sLogs) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	
	public boolean writeLineDate(Object obj, String sLog) throws ExceptionZZZ;//wird dann als Synchronized implementiert.
	
	public boolean writeLineDateWithPosition(String sLog) throws ExceptionZZZ;
	
	public boolean writeLineDateWithPosition(Class classObj, String sLog) throws ExceptionZZZ;	
	public boolean writeLineDateWithPosition(Object obj, String sLog) throws ExceptionZZZ;
	public boolean writeLineDateWithPosition(Object obj, int iStackTraceOffset, String sLog) throws ExceptionZZZ;
	
	public boolean writeLineDateWithPositionXml(String sLog) throws ExceptionZZZ;
	
	public boolean writeLineDateWithPositionXml(Class classObj, String sLog) throws ExceptionZZZ;
	public boolean writeLineDateWithPositionXml(Object obj, String sLog) throws ExceptionZZZ;
	public boolean writeLineDateWithPositionXml(Object obj, int iStackTraceOffset, String sLog) throws ExceptionZZZ;
}
