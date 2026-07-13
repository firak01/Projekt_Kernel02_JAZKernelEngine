package basic.zBasic.util.system;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface ISystemEnabledZZZ extends IFlagZEnabledZZZ{
		
	public enum FLAGZLOCAL {
		DUMMYFLAGZLOCAL(1 << 0),
		;
		
		private final int mask;
		
		private FLAGZLOCAL(int mask) {
			this.mask = mask;
		}
		
		public int getMask() {
			return mask;
		}
	}
	
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public abstract boolean getFlagLocal(FLAGZLOCAL objEnumFlag) throws ExceptionZZZ;
	public abstract boolean setFlagLocal(FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlagLocal(FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagLocalExists(FLAGZLOCAL objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagLocalSetBefore(FLAGZLOCAL objEnumFlag) throws ExceptionZZZ;
	
	//#############################################################
	//### FLAGZCustom
	//#############################################################
	public enum FLAGZCUSTOM{
		DUMMYFLAGZCUSTOM
	}
		
	public abstract boolean getFlagCustom(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
	public abstract boolean setFlagCustom(FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlagCustom(FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagCustomExists(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagCustomSetBefore(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
		
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	public abstract boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public abstract boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
