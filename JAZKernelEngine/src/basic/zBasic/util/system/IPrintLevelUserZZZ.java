package basic.zBasic.util.system;

import basic.zBasic.ExceptionZZZ;

/** Analog zu ILogLevelUserZZZ
 * @author Fritz Lindhauer
 *
 */
public interface IPrintLevelUserZZZ {
	public enum PRINTLEVEL{
		NONE,
		WARNING,
		INFO,
		DEBUG
	}

	public PRINTLEVEL getPrintLevelOverallEnum() throws ExceptionZZZ;
	public PRINTLEVEL getPrintLevelOverallEnumDefault() throws ExceptionZZZ;
	public void setPrintLevelOverall(PRINTLEVEL enumLogLevel) throws ExceptionZZZ;
	
	//WICHTIG: Mit dem int - Wert hier, lassen sich die Level in ihrer Hierarchie vergleichen
	public int getPrintLevelOverall() throws ExceptionZZZ;	
}
