package basic.zKernel.config.help;

import basic.zBasic.ExceptionZZZ;

public class KernelConfigHeaderLineZZZ implements IKernelConfigHeaderLineZZZ {	
	private String sHeaderLine=null;
	
	

	//### Konstruktoren
	public KernelConfigHeaderLineZZZ() {		
	}
	
	public KernelConfigHeaderLineZZZ(String sHeaderLine) throws ExceptionZZZ {	
		KernelConfigHeaderLineNew_(sHeaderLine);
	}
	
	private boolean KernelConfigHeaderLineNew_(String sHeaderLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			this.setHeaderLine(sHeaderLine);
			
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

}
