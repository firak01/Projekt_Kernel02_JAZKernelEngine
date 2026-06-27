package basic.zKernel.config.help;

import basic.zBasic.ExceptionZZZ;

public interface IKernelConfigHeaderLineZZZ {
	public String getHeaderLine() throws ExceptionZZZ;
	public void setHeaderLine(String sHeaderLine) throws ExceptionZZZ;	
	
	public int getHeaderSpacingBelow() throws ExceptionZZZ;
	public void setHeaderSpacingBelow(int iHeaderSpacingBelow) throws ExceptionZZZ;
	
	public int getHeaderSpacingAbove() throws ExceptionZZZ;
	public void setHeaderSpacingAbove(int iHeaderSpacingAbove) throws ExceptionZZZ;
}
