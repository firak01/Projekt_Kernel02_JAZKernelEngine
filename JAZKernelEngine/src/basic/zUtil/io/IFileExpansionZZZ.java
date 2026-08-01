package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.FileZZZ;

public interface IFileExpansionZZZ extends IFileExpansionStateEnabledZZZ, IFileExpansionProxyZZZ {
	
	public FileZZZ getFileBase();
	public void setFileBase(FileZZZ objFile);
	
	public int getExpansionLength();
	public void setExpansionLength(int iExpansionLength);
	
	public String getExpansionFilling();
	public void setExpansionFilling(char cExpansionFilling);
	public void setExpansionFilling(String sExpansionFillingCharacter) throws ExceptionZZZ;
	
	public int getExpansionValueCurrent();
	public void setExpansionValueCurrent(int iExpansionValue);
	
	public String searchExpansionCurrent() throws ExceptionZZZ;
	public String searchExpansionCurrent(int iExpansionLength) throws ExceptionZZZ;
	
	public String searchExpansionUsedLowest() throws ExceptionZZZ;
	public String searchExpansionUsedLowest(int iExpansionLength) throws ExceptionZZZ;
	
	public String searchExpansionFreeNext() throws ExceptionZZZ;
	public String searchExpansionFreeNext(int iExpansionLength) throws ExceptionZZZ;
	
	public String computeExpansionValueCurrentString();
	public String computeExpansionValueCurrentString(int iExpansionLength);
	
	public String computeExpansion(int iExpansionValue);
	public String computeExpansion(String sFilling, int iExpansionValue);
	public String computeExpansion(String sFilling, int iExpansionValue, int iExpansionLength);
}
