package basic.zBasic.util.file;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.file.ini.KernelZFormulaIni_EmptyZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_NullZZZ;

public class FileEasyConstantConverterZZZ implements IFileEasyConstantsZZZ {
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn) throws ExceptionZZZ {
		if(FileEasyZZZ.isPathRelative(sFilePathIn)) {
			return FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
		}else {
			return FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
		}
	}
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn, char cDirectorySeparator) throws ExceptionZZZ {
		if(FileEasyZZZ.isPathRelative(sFilePathIn)) {
			return FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathIn,cDirectorySeparator);
		}else {
			return FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
		}
	}	
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn,boolean bReturnAsRelativePath) throws ExceptionZZZ {
		if(bReturnAsRelativePath) {
			return FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
		}else {
			return FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathIn,IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
		}
	}
	
	public static IFileEasyPathObjectZZZ convertFilePath(String sFilePathIn, char cDirectorySeparator, boolean bReturnAsRelativePath) throws ExceptionZZZ {
		if(bReturnAsRelativePath) {
			return FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathIn, cDirectorySeparator);
		}else {
			return FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathIn, cDirectorySeparator);
		}
	}
	
	
	public static IFileEasyPathObjectZZZ convertFilePathToAbsolute(String sFilePathIn) throws ExceptionZZZ {
		return FileEasyConstantConverterZZZ.convertFilePathToAbsolute(sFilePathIn, IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);		
	}
	
	public static IFileEasyPathObjectZZZ convertFilePathToAbsolute(String sFilePathIn, char cDirectorySeparator) throws ExceptionZZZ{
		IFileEasyPathObjectZZZ objReturn = new FileEasyPathObjectZZZ(sFilePathIn);
		String sLog=null;
		
		//ggfs. ueberfluessige Slashes, etc. entfernen
		String sFilePath = StringZZZ.trimFileSeparators(sFilePathIn);		
		main:{			
			String sDirectorySeparator = StringZZZ.char2String(cDirectorySeparator);
			
			String sReturnFilePath="";
			String sReturnRoot="";
			String sReturnFilePathTotal="";
			
			//An empty string is allowed as ROOT-Directory. A null String is the Project/Execution Directory		
			if(sFilePath==null || XmlUtilZZZ.computeTagNull().equals(sFilePath)){
				String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
				sReturnRoot = "";
				sReturnFilePath = sWorkspace;				
				sReturnFilePathTotal = sWorkspace;
			}else if(sFilePath.equals("") || XmlUtilZZZ.computeTagEmpty().equals(sFilePath)){	
				String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
				sReturnRoot = FileEasyZZZ.getFileRootPath();
				sReturnFilePath= sWorkspace;
				sReturnFilePathTotal= FileEasyZZZ.joinFilePathName(sWorkspace,sReturnRoot);	
			}else if(sFilePath.equals(FileEasyConstantConverterZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)){
				String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
				sReturnRoot = sFilePath;
				sReturnFilePath= sWorkspace;
				sReturnFilePathTotal= FileEasyZZZ.joinFilePathName(sWorkspace,sReturnRoot);	
			}else {				
				if(FileEasyZZZ.isPathRelative(sFilePath) & !StringZZZ.isEmpty(sFilePath)) {			
					boolean btempCaseNULL = StringZZZ.startsWithIgnoreCase((sFilePath + sDirectorySeparator),XmlUtilZZZ.computeTagNull() + sDirectorySeparator);
					boolean btempCaseEMPTY = StringZZZ.startsWithIgnoreCase((sFilePath + sDirectorySeparator),XmlUtilZZZ.computeTagEmpty() + sDirectorySeparator);
					if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btempCaseNULL ) {
						sReturnRoot = FileEasyZZZ.getDirectoryOfExecutionAsString();
						sReturnFilePath = StringZZZ.rightback(sFilePath, XmlUtilZZZ.computeTagNull()+sDirectorySeparator,false);
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnFilePath;
						}
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btempCaseEMPTY ) {
						if(FileEasyZZZ.isOnServer()) {
							sReturnRoot=FileEasyZZZ.getDirectoryOfExecutionAsString();																																
						}else {	
							sReturnRoot=FileEasyZZZ.getDirectoryOfExecutionAsString();
							sReturnRoot=sReturnRoot + sDirectorySeparator + IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
						}
						
						sReturnFilePath = StringZZZ.rightback(sFilePath, XmlUtilZZZ.computeTagEmpty()+sDirectorySeparator,false);
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnFilePath;
						}
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);																	
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);						
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER + sDirectorySeparator)) {
						if(FileEasyZZZ.isOnServer()) {
							sReturnRoot="";
							sReturnFilePath = sFilePath;							
							sReturnFilePathTotal = sFilePath;
						}else if(FileEasyZZZ.isInJarStatic()) {
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;							
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
						}else {
							//Also wie in FileEasyZZZ.isInIDE();
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							
							if(StringZZZ.isEmpty(sReturnRoot)) {
								sReturnFilePathTotal = sReturnFilePath;
							}else {
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;																						
							}	
						}	
					}else {
						sReturnRoot = FileEasyZZZ.getFileRootPath();
						sLog="sReturnRoot='"+sReturnRoot+"'";
						ObjectZZZ.printlnDateWithPosition(FileEasyConstantConverterZZZ.class, sLog);
						
						sLog="sFilePath='"+sFilePath+"'";
						ObjectZZZ.printlnDateWithPosition(FileEasyConstantConverterZZZ.class, sLog);
						
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePath=StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
						}else {
							sReturnFilePath=sFilePath;
						}
						
						if(StringZZZ.isEmpty(sReturnFilePath)) {
							sReturnFilePath=sFilePath;
						}
						if(StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;							
						}										
					}
				}else {
					//absoluter Pfad, irgendwo auf der Festplatte
					//auch in der IDE
					sReturnRoot = "";
					sReturnFilePath=sFilePath;
					sReturnFilePathTotal=sReturnRoot + sFilePath;
				}
			}
			objReturn.setFilePathTotal(sReturnFilePathTotal);
			objReturn.setFilePath(sReturnFilePath);
			objReturn.setRootAdditionalComputed(sReturnRoot);
		}
		return objReturn;
	}
	
	public static IFileEasyPathObjectZZZ convertFilePathToRelative(String sFilePathIn) throws ExceptionZZZ{
		return FileEasyConstantConverterZZZ.convertFilePathToRelative(sFilePathIn, IFileEasyConstantsZZZ.cDIRECTORY_SEPARATOR);
	
	}
	
	public static IFileEasyPathObjectZZZ convertFilePathToRelative(String sFilePathIn, char cDirectorySeparator) throws ExceptionZZZ{
		IFileEasyPathObjectZZZ objReturn = new FileEasyPathObjectZZZ(sFilePathIn);
		
		//ggfs. ueberfluessige Slashes, etc. entfernen
		String sFilePath = StringZZZ.stripFileSeparators(sFilePathIn);		
		main:{			
			String sDirectorySeparator = StringZZZ.char2String(cDirectorySeparator);
			
			String sReturnFilePath="";
			String sReturnRoot="";
			String sReturnFilePathTotal="";
			
			//An empty string is allowed as ROOT-Directory. A null String is the Project/Execution Directory		
			if(sFilePath==null || XmlUtilZZZ.computeTagNull().equals(sFilePath)){
				String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
				sReturnRoot = "";
				sReturnFilePath = sWorkspace;				
				sReturnFilePathTotal = sWorkspace;
			}else if(sFilePath.equals("") || XmlUtilZZZ.computeTagEmpty().equals(sFilePath)){					
				sReturnRoot = FileEasyZZZ.getFileRootPath();
				sReturnFilePath="";
				sReturnFilePathTotal=sReturnRoot;	
			}else if(sFilePath.equals(FileEasyConstantConverterZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER) || sFilePath.equals(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)){
				sReturnRoot = sFilePath;
				sReturnFilePath = "";
				sReturnFilePathTotal=sReturnRoot;
			}else {				
				if(FileEasyZZZ.isPathRelative(sFilePath) & !StringZZZ.isEmpty(sFilePath)) {
					boolean btempCaseNULL = StringZZZ.startsWithIgnoreCase((sFilePath + sDirectorySeparator),XmlUtilZZZ.computeTagNull() + sDirectorySeparator);
					boolean btempCaseEMPTY = StringZZZ.startsWithIgnoreCase((sFilePath + sDirectorySeparator),XmlUtilZZZ.computeTagEmpty() + sDirectorySeparator);
					if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btempCaseNULL ) {
						sReturnRoot = FileEasyZZZ.getDirectoryOfExecutionAsString();
						sReturnFilePath = StringZZZ.rightback(sFilePath, XmlUtilZZZ.computeTagNull()+sDirectorySeparator,false);
						
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnRoot + sDirectorySeparator + sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnFilePath;
						}						
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btempCaseEMPTY ) {
						if(FileEasyZZZ.isOnServer()) {
							sReturnRoot="";																										
						}else {							
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
						}						
						sReturnFilePath = StringZZZ.rightback(sFilePath, XmlUtilZZZ.computeTagEmpty()+sDirectorySeparator,false);

						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnFilePath;
						}						
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);																	
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER + sDirectorySeparator)) {
						sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
						sReturnFilePathTotal = sFilePath;
						sReturnFilePath = StringZZZ.rightback(sReturnFilePathTotal, sReturnRoot+sDirectorySeparator);						
					}else if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && (sFilePath + sDirectorySeparator).startsWith(IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER + sDirectorySeparator)) {
						if(FileEasyZZZ.isOnServer()) {
							sReturnRoot="";
							sReturnFilePath = sFilePath;							
							sReturnFilePathTotal = sFilePath;
						}else if(FileEasyZZZ.isInJarStatic()) {
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;							
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
						}else {
							//Also wie in FileEasyZZZ.isInIDE();
							sReturnRoot=IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
							sReturnFilePath = StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
							if(StringZZZ.isEmpty(sReturnFilePath)) {
								sReturnFilePath=sFilePath;
							}
							
							if(StringZZZ.isEmpty(sReturnRoot)) {
								sReturnFilePathTotal = sReturnFilePath;
							}else {
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;														
								if(FileEasyZZZ.isPathAbsolute(sReturnFilePathTotal)) {
									sReturnFilePathTotal = sReturnFilePath;
								}								
							}	
						}//end if FileEasyZZZ.isOnServer()
					}else {
						sReturnRoot = FileEasyZZZ.getFileRootPath();
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"sReturnRoot='"+sReturnRoot+"'");
						System.out.println(ReflectCodeZZZ.getPositionCurrent()+"sFilePath='"+sFilePath+"'");
						if(!StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePath=StringZZZ.rightback(sFilePath, sReturnRoot+sDirectorySeparator);
						}else {
							sReturnFilePath=sFilePath;
						}
						
						if(StringZZZ.isEmpty(sReturnFilePath)) {
							sReturnFilePath=sFilePath;
						}
						if(StringZZZ.isEmpty(sReturnRoot)) {
							sReturnFilePathTotal=sReturnFilePath;
						}else {
							sReturnFilePathTotal=sReturnRoot+sDirectorySeparator+sReturnFilePath;							
							if(FileEasyZZZ.isPathAbsolute(sReturnFilePathTotal)) {
								sReturnFilePathTotal = sReturnFilePath;
							}	
						}										
					}//end if(!(sFilePath + sDirectorySeparator).startsWith(sDirectorySeparator) && btempCaseNULL ) {
				}else {
					//absolute Pfade
					if(FileEasyZZZ.isInIDE()) {
						//den Pfad zum Workspace suchen
						String sWorkspace = FileEasyZZZ.getDirectoryOfExecutionAsString();
						if(StringZZZ.startsWith(sFilePathIn, sWorkspace)) {
							//absoluter Pfad in der IDE, diesen zerlegen
							String sReturnFilePathTemp = StringZZZ.rightback(sFilePathIn, sWorkspace + sDirectorySeparator);
							if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER)) {
								sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_SOURCEFOLDER;
								sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;								
								if(!StringZZZ.isEmpty(sWorkspace)) {
									sReturnFilePathTotal=sWorkspace+sDirectorySeparator+sReturnFilePathTotal;
								}
							}else if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER)) {
								sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TESTFOLDER;
								sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;
								if(!StringZZZ.isEmpty(sWorkspace)) {
									sReturnFilePathTotal=sWorkspace+sDirectorySeparator+sReturnFilePathTotal;
								}
							}else if(StringZZZ.startsWith(sReturnFilePathTemp, IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER)) {
								sReturnRoot = IFileEasyConstantsZZZ.sDIRECTORY_CONFIG_TRYOUTFOLDER;
								sReturnFilePath = StringZZZ.rightback(sReturnFilePathTemp, sReturnRoot + sDirectorySeparator);
								sReturnFilePathTotal = sReturnRoot + sDirectorySeparator + sReturnFilePath;	
							}else {
								sReturnRoot = "";
								sReturnFilePath = sFilePathIn;
								sReturnFilePathTotal=sFilePathIn;
							}							
												
						}else {
							//absoluter Pfad, irgendwo auf der Festplatte
							sReturnRoot = "";
							sReturnFilePath=sFilePath;
							sReturnFilePathTotal=sReturnRoot + sFilePath;
						}
					}else {
						//absoluter Pfad, irgendwo auf der Festplatte
						sReturnRoot = "";
						sReturnFilePath=sFilePath;
						sReturnFilePathTotal=sReturnRoot + sFilePath;
					}//end if(FileEasyZZZ.isInIDE()) {
				}//end if(FileEasyZZZ.isPathRelative(sFilePath) & !StringZZZ.isEmpty(sFilePath)) {
			}
			objReturn.setRootAdditionalComputed(sReturnRoot);
			objReturn.setFilePath(sReturnFilePath);			
			objReturn.setFilePathTotal(sReturnFilePathTotal);
		}
		return objReturn;
	}
}
