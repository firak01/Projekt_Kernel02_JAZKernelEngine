/*
 * Created on 24.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package custom.zKernel.file;

import java.io.File;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.file.KernelFileTextExportCrackZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.csv.FileCsvZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zKernel.file.transform.FileTransformCsv2IniZZZ;
import custom.zUtil.io.FileZZZ;

/**

@author 0823 ,date 24.10.2004
*/
public class FileTextExportCrackZZZ extends KernelFileTextExportCrackZZZ {

	/**CONSTRUCTOR
	 @date: 24.10.2004
	 @param objKernel
	 @param objLog
	 @param objFile
	 @param saFlagControl
	 @throws ExceptionZZZ
	 */
	public FileTextExportCrackZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, File objFile,String sProgramAlias, String[] saFlagControl) throws ExceptionZZZ {
		super(objKernel, objLog, objFile, sProgramAlias, saFlagControl);
	}
	
	public boolean crackCustom() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			String stemp; 
			String sDirTarget;
			//TODO Hier den Transformator csv-->ini starten
			KernelLogZZZ objLog=null;
			FileIniZZZ objFileConfigIniExport;
						try {	
							IKernelZZZ objKernel = this.getKernelObject();
							objLog = objKernel.getLogObject();						
							check:{
								//Konfigurationsdatei holen
								objFileConfigIniExport = objKernel.getFileConfigModuleIni("Export");
								//Darin wird die Existenz, etc. schon gepr�ft
								//Dadurch dass sofort das IniZZZ-Object verwendet wird, braucht man es nur einmal einzulesen
								
								//Auslesen des Zielverzeichnisses 																				
								sDirTarget= objKernel.getParameterByProgramAlias(objFileConfigIniExport,this.getProgramAliasCurrent(),"TargetPath").getValue();
								if(sDirTarget==null){
									stemp = "'Target Directory'";
								   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
								   throw ez;		 															
								}
							}//end check:
						
						
						//Auslesen der Quelldatei
						File objFileTemp = this.getFileObject();
						String sBaseDirectory = objFileTemp.getParent();
						String sBaseFile = objFileTemp.getName();
						
						//!!! n�chste Datei ermitteln
						FileZZZ objFileToCrack = new FileZZZ(sBaseDirectory, sBaseFile, "");
						 String sFileExpanded = objFileToCrack.getNameExpandedCurrent();
			 
						 //als CSV einlesen			 			 
						 FileCsvZZZ objCsv= new FileCsvZZZ(objKernel, objLog, sBaseDirectory, sFileExpanded,null);

						//!!! die configuration (quasi das mapping von csv-->ini)
						String sDirConfigAction = objKernel.getParameterByProgramAlias(objFileConfigIniExport, this.getProgramAliasCurrent(), "ActionPath").getValue();
						String sFileConfigAction = objKernel.getParameterByProgramAlias(objFileConfigIniExport, this.getProgramAliasCurrent(), "ActionFilenameBasis").getValue();
						FileIniZZZ objFileConfigIniAction = new FileIniZZZ(objKernel, sDirConfigAction,sFileConfigAction,(String[])null);
						
						//!!! die Transformation starten
						FileTransformCsv2IniZZZ objCrack = new FileTransformCsv2IniZZZ(objKernel, objLog, objCsv,objFileConfigIniAction, sDirTarget, null);
						bReturn = objCrack.startIt();
			
						} catch (ExceptionZZZ ez) {
							if(objLog!=null){
								objLog.writeLineDate(ez.getDetailAllLast());	
								bReturn = false;
							}else{
								System.out.println(ez.getDetailAllLast());
								bReturn = false;
							}							
						}
		
		
					
			}//end main:
						
		return bReturn;
	}//end function crackCustom()
}//end class

