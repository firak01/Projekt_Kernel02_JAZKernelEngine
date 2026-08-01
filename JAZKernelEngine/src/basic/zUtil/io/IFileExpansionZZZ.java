package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.FileZZZ;

public interface IFileExpansionZZZ extends IFileExpansionStateEnabledZZZ, IFileExpansionProxyZZZ {
	
	public FileZZZ getFileBase() throws ExceptionZZZ;
	public void setFileBase(FileZZZ objFile) throws ExceptionZZZ;
	
	public int getExpansionLength() throws ExceptionZZZ;
	public void setExpansionLength(int iExpansionLength) throws ExceptionZZZ;
	
	public String getExpansionFilling() throws ExceptionZZZ;
	public void setExpansionFilling(char cExpansionFilling) throws ExceptionZZZ;
	public void setExpansionFilling(String sExpansionFillingCharacter) throws ExceptionZZZ;
	
	public int getExpansionValueCurrent() throws ExceptionZZZ;
	public void setExpansionValueCurrent(int iExpansionValue) throws ExceptionZZZ;
	
	public String searchExpansionCurrent() throws ExceptionZZZ;
	public String searchExpansionCurrent(int iExpansionLength) throws ExceptionZZZ;
	
	public String searchExpansionUsedLowest() throws ExceptionZZZ;
	public String searchExpansionUsedLowest(int iExpansionLength) throws ExceptionZZZ;
	
	public String searchExpansionFreeNext() throws ExceptionZZZ;
	public String searchExpansionFreeNext(int iExpansionLength) throws ExceptionZZZ;
	
	public String searchExpansionFreeLowest(int iExpansionLength) throws ExceptionZZZ;
	
	public String computeExpansionValueCurrentString() throws ExceptionZZZ;
	public String computeExpansionValueCurrentString(int iExpansionLength) throws ExceptionZZZ;
	
	public String computeExpansion(int iExpansionValue) throws ExceptionZZZ;
	public String computeExpansion(String sFilling, int iExpansionValue) throws ExceptionZZZ;
	public String computeExpansion(String sFilling, int iExpansionValue, int iExpansionLength) throws ExceptionZZZ;
}
