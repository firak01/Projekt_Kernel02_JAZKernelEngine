package basic.zKernel.component;

import java.util.ArrayList;
import java.util.Iterator;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IResettableValuesZZZ;
import basic.zBasic.IResettableZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.cache.IKernelCacheZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public abstract class AbstractKernelProgramZZZ  extends AbstractKernelUseObjectZZZ implements IResettableValuesZZZ, IKernelProgramZZZ, IKernelModuleUserZZZ {
	protected IKernelModuleZZZ objModule=null; //Das Modul, in der UI Variante z.B. die Dialogbox, in der das Program gestartet wird.
	protected String sModuleName=null;	
	protected String sProgramName=null;
	
	/**Z.B. Wg. Reflection immer den Standardkonstruktor zur Verfügung stellen.
	 * 
	 * 31.01.2021, 12:15:10, Fritz Lindhauer
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelProgramZZZ() throws ExceptionZZZ {
		super();
		AbstractKernelProgramNew_();
	}
	
	public AbstractKernelProgramZZZ(IKernelZZZ objKernel, String[]saFlag) throws ExceptionZZZ {
		super(objKernel,saFlag);
		AbstractKernelProgramNew_();
	}
	
	public AbstractKernelProgramZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(objKernel); 	
		AbstractKernelProgramNew_();
	}
	
	public void setProgramName(String sProgramName){
		this.sProgramName = sProgramName;		
	}
	
	private boolean AbstractKernelProgramNew_() throws ExceptionZZZ {
		
		//Da dies ein KernelProgram ist automatisch das FLAG IKERNELPROGRAM Setzen!!!
	    this.setFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name(), true);	
				
		return true;
	}
	
	//### Aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ {
		//super.reset();//gibt es nicht, da oberste Ebene
		this.objModule=null;
		this.sModuleName=null;
		this.sProgramName=null;
		this.resetValues();
		return true;
	}
	
	@Override
	public abstract boolean resetValues() throws ExceptionZZZ;
	
	
	@Override
	public abstract boolean resetValues(Object objDefault) throws ExceptionZZZ;

	
	//### Aus IKernelProgramZZZ
	@Override
	public String getProgramName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sProgramName)) {
			if(this.getFlag(IKernelProgramZZZ.FLAGZ.ISKERNELPROGRAM.name())) {
				this.sProgramName = this.getClass().getName();
			}
		}
		return this.sProgramName;
	}
		
		
	
	@Override
	public void resetProgramUsed() {
		this.sProgramName = null;
		//this.sProgramAlias = null;
	}
	
	
	@Override
	public String getProgramAlias() throws ExceptionZZZ {		
		return null;
	}
	
	@Override
	public boolean getFlag(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelProgramZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelProgramZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelProgramZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
	
	@Override
	public boolean proofFlagSetBefore(IKernelProgramZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}	
	
	

	
	
	public String readModuleName() throws ExceptionZZZ {
		String sReturn = null;
		main:{
			IKernelModuleZZZ objModule = this.getModule();
			if(objModule!=null) {
				sReturn = objModule.getModuleName();
				if(StringZZZ.isEmpty(sReturn)) {
					sReturn = this.getKernelObject().getSystemKey();
				}
			}else {
				sReturn = this.getKernelObject().getSystemKey();
			}
		}//end main:
		return sReturn;
	}
	
	public String getModuleName() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sModuleName)) {
			this.sModuleName = this.readModuleName();
		}
		return this.sModuleName;
	}
	public void setModuleName(String sModuleName){
		this.sModuleName=sModuleName;
	}
	
	public void resetModuleUsed() {
		this.objModule = null;
		this.sModuleName = null;
	}
	
	//### Aus IKernelModuleUserZZZ	
	@Override
	public IKernelModuleZZZ getModule() {
		return this.objModule;
	}
	
	@Override
	public void setModule(IKernelModuleZZZ objModule) {
		this.objModule = objModule;
	}
	
	@Override
	public boolean getFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelModuleUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelModuleUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelModuleUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelModuleUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
			
	//### Methoden
	
}
