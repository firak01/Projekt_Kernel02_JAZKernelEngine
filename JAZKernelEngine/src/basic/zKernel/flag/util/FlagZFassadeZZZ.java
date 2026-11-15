package basic.zKernel.flag.util;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class FlagZFassadeZZZ implements IConstantZZZ{

	public static String[] seekFlagZrelevantForObject(IFlagZEnabledZZZ objSource, IFlagZEnabledZZZ objTarget) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(objSource==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'objSource'",iERROR_PARAMETER_MISSING, FlagZFassadeZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objTarget==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'objTarget'",iERROR_PARAMETER_MISSING, FlagZFassadeZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String[] saFlagSource = objSource.getFlagZ_passable(true, objTarget);//Aber darin sind ja auch DEBUG, INIT, etc.
			String[] saFlagIrrelevant = {"DUMMY","DEBUG","INIT"};
			
			saReturn = StringArrayZZZ.remove(saFlagSource, saFlagIrrelevant, true);
			
		}//end main:
		return saReturn;
		
		//##################################################################
		//### Initialisiere das FileIniZZZ-Objekt
		//### - Uebernahme der relevanten Flags
		//### - Registrierung am KernelObjekt für Flag Aenderungsevent 
		//TODOGOON20230404: DEN CODE IN EINE FlagUtilZZZ-Klasse packen (und damit "retten").
		//TODOGOON20230404: DEN CODE HIER LOESCHEN UND STATT DESSEN komplett auf Flag - Event setzen...
		
		//read the ini-content: 
		//Merke: Falls die Datei nicht existiert, wird ein Fehler geworfen								
//		HashMap<String, Boolean> hmFlag = new HashMap<String, Boolean>();					
//		FileIniZZZ exDummy = new FileIniZZZ();
//		//String[] saFlagRelevantFileIniZZZ = exDummy.getFlagZ();//Aber darin sind ja auch DEBUG, INIT, etc.
//		String[] saFlagRelevantFileIniZZZ = {"USEEXPRESSION", "USEFORMULA", "USEFORMULA_MATH", "USEJSON", "USEJSON_ARRAY", "USEJSON_MAP", "USEENCRYPTION"};
//		String[] saFlagZpassedFalse = this.getFlagZ_passable(false, true, exDummy);	
//		
//		String[] saFlagFileIniZZZ = StringArrayZZZ.remove(saFlagRelevantFileIniZZZ, saFlagZpassedFalse, true);				
//		FileIniZZZ objFileIniZZZ = new FileIniZZZ(this, this.getFileConfigKernelDirectory(), this.getFileConfigKernelName(), saFlagFileIniZZZ);
//		this.setFileConfigKernelIni(objFileIniZZZ);
//		
//		//Registriere das FileIniZZZ - Objekt fuer Aenderungen an den Kernel Flags. Z.B. wenn mal die <Z>-Formeln ausgewertet werden sollen, mal nicht.
//		this.registerForFlagEvent(objFileIniZZZ);
		//####################################################################
		
		
	}
	
	public static String[] seekFlagZrelevantForObject(IFlagZEnabledZZZ objSource, IFlagZEnabledZZZ objTarget, boolean bValueToSearchFor) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(objSource==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'objSource'",iERROR_PARAMETER_MISSING, FlagZFassadeZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(objTarget==null) {
				ExceptionZZZ ez = new ExceptionZZZ("Missing parameter: 'objTarget'",iERROR_PARAMETER_MISSING, FlagZFassadeZZZ.class,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			String[] saFlagSource = objSource.getFlagZ_passable(bValueToSearchFor, true, objTarget);//Aber darin sind ja auch DEBUG, INIT, etc.
			String[] saFlagIrrelevant = {"DUMMY","DEBUG","INIT"};
			
			saReturn = StringArrayZZZ.remove(saFlagSource, saFlagIrrelevant, true);
			
		}//end main:
		return saReturn;
	}
	
}
