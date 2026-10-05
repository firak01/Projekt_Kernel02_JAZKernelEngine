package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.util.abstractList.HashMapIndexedObjectZZZ;
import basic.zBasic.util.crypt.code.ICryptZZZ;
import basic.zBasic.util.file.ini.IniFile;
import basic.zKernel.cache.IKernelCacheUserZZZ;
import basic.zKernel.file.ini.IIniTagWithExpressionZZZ;
import basic.zKernel.file.ini.IKernelEncryptionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniParserZZZ;
import basic.zKernel.file.ini.IKernelExpressionIniSolverZZZ;
import basic.zKernel.file.ini.IKernelFileIniZZZ;
import basic.zKernel.file.ini.IKernelJavaCallIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonArrayIniSolverZZZ;
import basic.zKernel.file.ini.IKernelJsonMapIniSolverZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIniZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_PathZZZ;
import basic.zKernel.file.ini.IKernelZFormulaIni_VariableZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IEventBrokerFlagZsetUserZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.ini.FileIniZZZ;

public interface IKernelZZZ extends IObjectWithExpressionZZZ, IKernelFileIniUserZZZ, IKernelConfigUserZZZ, IKernelConfigParameterHandlerZZZ,IKernelCacheUserZZZ,IFlagZEnabledZZZ, IKernelExpressionIniParserZZZ,IKernelExpressionIniSolverZZZ, IKernelZFormulaIniZZZ, IKernelZFormulaIni_VariableZZZ, IKernelZFormulaIni_PathZZZ, IKernelJsonArrayIniSolverZZZ, IKernelJsonMapIniSolverZZZ, IKernelJavaCallIniSolverZZZ, IKernelEncryptionIniSolverZZZ{
	//FLAGZ, die dann zum "Rechnen in der Konfigurations Ini Datei" gesetzt sein müssen.
	public enum FLAGZ{
		DUMMY; //die sollte in IFormula.... stehen USEFORMULA, USEFORMULA_MATH;
	}
	//damit muss man nicht mehr tippen hinter dem enum .name()
	public boolean getFlag(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public boolean setFlag(IKernelZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;
	public boolean[] setFlag(IKernelZZZ.FLAGZ[] objEnumFlag, boolean bFlagValue) throws ExceptionZZZ;	
	public abstract boolean proofFlagExists(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	public abstract boolean proofFlagSetBefore(IKernelZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ;
	
	public KernelLogZZZ getLogObject();
	public void setLogObject(KernelLogZZZ objLog);
	
	public String getApplicationKey() throws ExceptionZZZ;
	public String getSystemKey() throws ExceptionZZZ;	
	public String getSystemNumber() throws ExceptionZZZ;
	public String getFileConfigKernelDirectory()throws ExceptionZZZ;
	public String getFileConfigKernelName()throws ExceptionZZZ;
	
	public boolean isOnServer() throws ExceptionZZZ;
	public boolean isInJar() throws ExceptionZZZ;	
	
	//### häufig verwendete Methoden, z.B. auch in den JUnit Tests:

	public ArrayList<String> computeSystemSectionNamesForProgram(String sProgramName) throws ExceptionZZZ;
	
	public IniFile getFileConfigKernelAsIni() throws ExceptionZZZ;
	public File getFileConfigModule(String sModuleAlias) throws ExceptionZZZ;
	public File[]getFileConfigModuleAllByDir(String sDir) throws ExceptionZZZ;
	
	
	public <T> FileIniZZZ<T> searchModuleFileWithProgramAlias(String sModule, String sProgramOrSection) throws ExceptionZZZ;
	public <T> FileIniZZZ<T> searchModuleFileByModule(String sModule, boolean bExistingOnly) throws ExceptionZZZ;
	public <T> FileIniZZZ<T> searchModuleFileByModuleInWorkspace(String sModule, boolean bExistingOnly) throws ExceptionZZZ;
	public String searchFileConfigIniPathByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ;
	
	//HashMapIndexedZZZ<Integer,IKernelConfigSectionEntryZZZ>getParameterHashMapWithEntryByProgramAlias(String sModule, String sKeyInHashMapForProgramOrSection, String sValueInHashMapForsProperty) throws ExceptionZZZ;
	
	public IKernelConfigSectionEntryZZZ getProgramAliasFor(String sModuleOrProgram) throws ExceptionZZZ;
	public IKernelConfigSectionEntryZZZ getSectionAliasFor(String sModuleOrProgramAsSection) throws ExceptionZZZ;
	
	public String searchAliasForModule(String sModule) throws ExceptionZZZ;
	public String searchAliasForProgram(String sProgramName) throws ExceptionZZZ;
	public String searchAliasForProgram(String sModule, String sProgramName) throws ExceptionZZZ;
	
	public String searchFileConfigIniNameByAlias(IniFile objIni, String sAlias) throws ExceptionZZZ;
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der uebergebene Wert wird in der Methode erst noch verschluesselt. 
	public void setParameterByProgramAlias(String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der uebergebene Wert wird in der Methode erst noch verschluesselt. 
	public void setParameterByProgramAlias(String sModule, String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	//Schreibe einen Wert mit weiteren Argumenten als <Z> - Expression. Der übergebene Wert ist schon verschluesselt.
	public void setParameterByProgramAliasEncrypted(String sModule, String sSectionOrProgram, String sProperty,
			String sEncrypted, ICryptZZZ objCrypt) throws ExceptionZZZ;
	
	
}
