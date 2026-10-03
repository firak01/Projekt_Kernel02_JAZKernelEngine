package custom.zKernel;

import basic.zBasic.ExceptionZZZ;

public interface ILogLevelUserZZZ {
	public enum LOGLEVEL{
		NONE,
		WARNING,
		INFO,
		DEBUG
	}
	
	public LOGLEVEL getLogLevelEnum() throws ExceptionZZZ;
	public LOGLEVEL getLogLevelEnumDefault() throws ExceptionZZZ;
	public void setLogLevel(LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	public int getLogLevel() throws ExceptionZZZ;	
}
