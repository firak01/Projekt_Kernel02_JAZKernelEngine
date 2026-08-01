package basic.zUtil.io;

import basic.zBasic.ExceptionZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public interface IFileExpansionEnabledZZZ extends IFlagZEnabledZZZ{
	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMYFLAGZ, USE_FILE_EXPANSION; //Merke: DEBUG und INIT über IFlagZZZ eingebunden werden, weil von ObjectkZZZ kann man ja nicht erben. Es wird schon von anderer Objektklasse geerbt.
	}
	
	boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	

	//#############################################################
	//### FLAGZCUSTOM
	//#############################################################
	public enum FLAGZCUSTOM{
		DUMMYFLAGZCUSTOM
	}
		
	boolean getFlagCustom(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
	boolean setFlagCustom(FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlagCustom(FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagCustomExists(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagCustomSetBefore(FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ;
		
	
	//#############################################################
	//### FLAGZLOCAL
	//#############################################################
	/* ES GIBT HIER NOCH KEIN FLAGLOCAL
	public enum FLAGZLOCAL {
		DUMMYFLAGZLOCAL (1 << 0), 
		//MERGE_IGNORE_CHECKOUT_CONFLICTS     (1 << 1), // beim PULL / MERGE werden Konflikte unterdrückt. Es wird gemäß der Strategie ausgewählt was gewinnt
		//USE_STRATEGY_MERGE_CONFLICT_OURS    (1 << 2),
		//USE_STRATEGY_MERGE_CONFLICT_THEIRS (1 << 3), 
		//USE_STRATEGY_MERGE_CONFLICT_NEWEST  (1 << 4), //NOCH NICHT REALISIERT
		//MERGE_AUTOSOLVE_CHECKOUT_CONFLICTS  (1 << 5), //beim PULL / MERGE wird erst versucht zu Mischen. Konflikte werden danach gemäß Strategie aufgelöst was gewinnt.
		//STAGING_IGNORE_DELETES  (1 << 6)             //beim Commit wird nicht der eigens entwickelte GitAutoStageService verwendet. Sondern nur auf untracked und trackedChanged geachtet.
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
	*/
	
	
	//#######################################################################################
	// STATUS	
	//............ hier erst einmal nicht .....................
}
