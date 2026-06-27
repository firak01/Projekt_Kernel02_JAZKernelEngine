package basic.zKernel.config.help;

import basic.zBasic.ExceptionZZZ;

public class KernelConfigHeaderLineZZZ implements IKernelConfigHeaderLineZZZ {	
	private int iHeaderSpacingAbove=0;
	private String sHeaderLine=null;	
	private int iHeaderSpacingBelow=0;
	
	

	//### Konstruktoren
	public KernelConfigHeaderLineZZZ() {		
	}
	
	public KernelConfigHeaderLineZZZ(String sHeaderLine) throws ExceptionZZZ {	
		KernelConfigHeaderLineNew_(0, sHeaderLine, 0);
	}
		
	public KernelConfigHeaderLineZZZ(String sHeaderLine, int iHeaderSpacingBelow) throws ExceptionZZZ {	
		KernelConfigHeaderLineNew_(0, sHeaderLine, iHeaderSpacingBelow);
	}
	
	
	public KernelConfigHeaderLineZZZ(int iHeaderSpacingAbove, String sHeaderLine) throws ExceptionZZZ {	
		KernelConfigHeaderLineNew_(iHeaderSpacingAbove, sHeaderLine, 0);
	}
	
	public KernelConfigHeaderLineZZZ(int iHeaderSpacingAbove, String sHeaderLine, int iHeaderSpacingBelow) throws ExceptionZZZ {	
		KernelConfigHeaderLineNew_(iHeaderSpacingAbove, sHeaderLine, iHeaderSpacingBelow);
	}
	
	
	
	private boolean KernelConfigHeaderLineNew_(int iHeaderSpacingAbove, String sHeaderLine, int iHeaderSpacingBelow) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			this.setHeaderSpacingAbove(iHeaderSpacingAbove);
			this.setHeaderLine(sHeaderLine);
			this.setHeaderSpacingBelow(iHeaderSpacingBelow);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//### GETTER / SETTER
	@Override
	public void setHeaderLine(String sHeaderLine) throws ExceptionZZZ{
		this.sHeaderLine = sHeaderLine;
	}
	
	@Override
	public String getHeaderLine() throws ExceptionZZZ{
		return this.sHeaderLine;
	}
	
	
	
	@Override
	public void setHeaderSpacingBelow(int iHeaderSpacingBelow) throws ExceptionZZZ{
		this.iHeaderSpacingBelow = iHeaderSpacingBelow;
	}
	
	@Override
	public int getHeaderSpacingBelow() throws ExceptionZZZ{
		return this.iHeaderSpacingBelow;
	}

	
	@Override
	public void setHeaderSpacingAbove(int iHeaderSpacingAbove) throws ExceptionZZZ{
		this.iHeaderSpacingAbove = iHeaderSpacingAbove;
	}
	
	@Override
	public int getHeaderSpacingAbove() throws ExceptionZZZ{
		return this.iHeaderSpacingAbove;
	}

}
