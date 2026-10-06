package basic.zBasic.component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractModuleZZZ  extends AbstractObjectWithFlagZZZ implements IModuleZZZ {	
	protected String sModuleName=null;	
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractModuleZZZ() throws ExceptionZZZ {
		super();
		ModuleNew_("", null);
	}
	
	
	public AbstractModuleZZZ(String[] saFlagUsed) throws ExceptionZZZ {
		super(); 		
		ModuleNew_("", saFlagUsed);
	}
	
	public AbstractModuleZZZ(String sModule) throws ExceptionZZZ {
		super();
		ModuleNew_(sModule, null);
	}
	
	private boolean ModuleNew_(String sModule, String[] saFlagUsed) throws ExceptionZZZ {
		boolean bReturn = false;		
		main:{			
			String stemp=null; boolean btemp=false; String sLog = null;
			this.printlnDate(ReflectCodeZZZ.getMethodCurrentName() + ": Initializing ModuleObject");
						
			//Weitere Flags setzen
			if(saFlagUsed!=null) {
				//setzen der übergebenen Flags
				for(int iCount = 0;iCount<=saFlagUsed.length-1;iCount++){
				  stemp = saFlagUsed[iCount];
				  if(!StringZZZ.isEmpty(stemp)){
					  btemp = setFlag(stemp, true);
					  if(btemp==false){
						  sLog = "the flag '" + stemp + "' is not available.";
						  this.printlnDate(ReflectCodeZZZ.getMethodCurrentName() + ": " + sLog);
						  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
						  throw ez;		 
					  }
				  }
				}
				if(this.getFlag("INIT")==true){
					bReturn = true;
					break main; 
				}	
			}//end if saFlagUsed!=null
		
			//Da dies ein KernelProgram ist automatisch das FLAG IKERNELMODULE Setzen!!!
			this.setFlag(IModuleZZZ.FLAGZ.ISMODULE.name(), true);
			this.setModuleName(sModule);
						
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	//### Aus IKernelModuleZZZ
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = this.getClass().getName();
		}//end main:
		return sReturn;
	}
	
	@Override
	public String getModuleName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sModuleName)) {
			this.sModuleName = this.readModuleName();
		}
		return this.sModuleName;
	}
	
	private void setModuleName(String sModuleName){
		this.sModuleName=sModuleName;
	}
	
	@Override
	public void resetModuleUsed() {
		this.setModuleName(null);
	}
	
	//### Methoden
	@Override
	public abstract void reset();
	
	
	//##################################
	//### Flag handling
	
	@Override
	public boolean getFlag(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IModuleZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IModuleZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IModuleZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IModuleZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
