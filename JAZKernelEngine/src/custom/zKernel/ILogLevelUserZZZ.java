package custom.zKernel;

import basic.zBasic.ExceptionZZZ;

/** Analog zu IPrintLevelUserZZZ
 * @author Fritz Lindhauer
 *
 */
public interface ILogLevelUserZZZ {
	public enum LOGLEVEL{
		NONE,
		WARNING,
		INFO,
		DEBUG
	}
	
	public LOGLEVEL getLogLevelOverallEnum() throws ExceptionZZZ;
	public LOGLEVEL getLogLevelOverallEnumDefault() throws ExceptionZZZ;
	public void setLogLevelOverall(LOGLEVEL enumLogLevel) throws ExceptionZZZ;
	
	//WICHTIG: Mit dem int - Wert hier, lassen sich die Level in ihrer Hierarchie vergleichen
	public int getLogLevelOverall() throws ExceptionZZZ;	
}
