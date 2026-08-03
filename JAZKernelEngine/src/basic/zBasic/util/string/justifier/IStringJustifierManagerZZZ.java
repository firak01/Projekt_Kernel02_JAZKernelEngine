package basic.zBasic.util.string.justifier;

import java.util.LinkedHashMap;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;

public interface IStringJustifierManagerZZZ extends IStringJustifierManagerComputerZZZ{
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListDefault() throws ExceptionZZZ;
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierList() throws ExceptionZZZ;
	public void setStringJustifierList(ArrayListZZZ<IStringJustifierZZZ> listaJustifier) throws ExceptionZZZ;
	public boolean resetStringJustifierList() throws ExceptionZZZ;

	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ;
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ;
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ;
	
	public ArrayListUniqueZZZ<IStringJustifierZZZ> getStringJustifierListUsed() throws ExceptionZZZ;
	public void setStringJustifierListUsed(ArrayListUniqueZZZ<IStringJustifierZZZ> listaJustifier) throws ExceptionZZZ;	
	public boolean resetStringJustifierListUsed() throws ExceptionZZZ;
	
	public boolean hasStringJustifier(int iIndex) throws ExceptionZZZ;
	public IStringJustifierZZZ getStringJustifierDefault() throws ExceptionZZZ;
	
	public IStringJustifierZZZ getStringJustifier(int iIndex) throws ExceptionZZZ;
	public void addStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ;
	
	public boolean reset() throws ExceptionZZZ;

	
	
	//#############################################################
	//### FLAGZ
	//#############################################################
	public enum FLAGZ{
		DUMMY
	}
		
	boolean getFlag(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean setFlag(FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean[] setFlag(FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	boolean proofFlagExists(FLAGZ objEnumFlag) throws ExceptionZZZ;
	boolean proofFlagSetBefore(FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	
	
	//#######################################################################################
	// STATUS	
    //............ hier erst einmal nicht .....................
}
