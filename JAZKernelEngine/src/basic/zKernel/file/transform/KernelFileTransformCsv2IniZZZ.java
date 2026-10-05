/*
 * Created on 25.10.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package basic.zKernel.file.transform;

import java.io.File;
import java.util.Hashtable;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.KernelZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.file.csv.FileCsvZZZ;
import custom.zKernel.file.ini.FileIniZZZ;
import custom.zUtil.io.FileZZZ;

/**

@author 0823 ,date 25.10.2004
*/
public class KernelFileTransformCsv2IniZZZ  extends AbstractKernelUseObjectZZZ{
	private FileIniZZZ objIniConfigAction;
	private FileCsvZZZ objFileCsv;	
	private Hashtable objintHashCC;
	
	public Hashtable getSectionCountTableCurrent(){
		return this.objintHashCC;	
	}
	
	public void setSectionCountTableCurrent(Hashtable objintHashCC){
			this.objintHashCC = objintHashCC;
	}
	
	public int getSectionCounterCurrent(String sCC){
		int iReturn = -1;
		main:{
			check:{
				if(this.objintHashCC==null) break main;
				if(sCC==null) break main;
				if(sCC.equals("")==true) break main;
				if(this.objintHashCC.containsKey(sCC)==false)break main;				
			}//end check:
			
				Integer intTemp = (Integer)objintHashCC.get(sCC);				
				iReturn = intTemp.intValue();
				
		}//end main:
		return iReturn;
	}//end function getSectionCounterCurrent
	
	
	public boolean setSectionCounterCurrent(String sCC, int iValue){
			boolean bReturn = false;
			main:{
				check:{
					if(sCC==null) break main;
					if(sCC.equals("")==true) break main;
					if(this.objintHashCC==null){
						this.objintHashCC=new Hashtable();						
					}
				}//end check
				
				Integer intTemp = new Integer(iValue);
				this.objintHashCC.put(sCC,intTemp);
				bReturn = true;
				
			}//end main:
			return bReturn;
	}
	
	private String sDirTarget;
	
	public String getDirectoryTarget(){
		return this.sDirTarget;	
	}
	public void setDirectoryTarget(String sDir){
		this.sDirTarget = sDir;
	}
	

	
	
	
	public KernelFileTransformCsv2IniZZZ(IKernelZZZ objKernel, KernelLogZZZ objLog, FileCsvZZZ objCSV, FileIniZZZ objActionConfigurationIni, String sDirTargetIn, String[] saFlagControl) throws ExceptionZZZ{
		KernelFileTransformCsv2IniNew_(objKernel, objLog, objCSV, objActionConfigurationIni, sDirTargetIn, saFlagControl);
	}
	
	public FileIniZZZ getActionConfiguration(){
		return objIniConfigAction;
	}
	
	public void setActionConfiguration(FileIniZZZ objIni){
		this.objIniConfigAction = objIni;
	}
	
	/** 
	
	 @author 0823 , date: 25.10.2004
	 @param objKernel
	 @param objLog
	 @param objCSV
	 @param objIni
	 @param saFlagControl
	 @return
	 */
	private boolean KernelFileTransformCsv2IniNew_(IKernelZZZ objKernelIn, KernelLogZZZ objLogIn, FileCsvZZZ objCsvIn, FileIniZZZ objActionConfigurationIniIn, String sDirTargetIn, String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
			String stemp; boolean btemp;
			main:{
			  		   //setzen der �bergebenen Flags	
					   if(saFlagControlIn != null){
							for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
								stemp = saFlagControlIn[iCount];
								btemp = setFlag(stemp, true);
								if(btemp==false){ 								   
									   ExceptionZZZ ez = new ExceptionZZZ( stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
									   throw ez;		 
								}
							}
					   }
	 		
	 		
				   this.objKernel = objKernelIn;
				   if(objLogIn == null){
					   objLog = this.objKernel.getLogObject();
				   }else{
					   this.objLog = objLogIn;
					}

				   if(objCsvIn==null){
					   stemp = "CSV-File-Object or CSV-File-Path information";
					   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 
				   }
				  this.objFileCsv = objCsvIn;
				 
				  File objFileTemp = this.objFileCsv.getFileObject();
				   if(objFileTemp !=null){
					   if(objFileTemp.exists()==false){
						   stemp = "CSV-File not found '" + objFileTemp.getPath() + "'";
						   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						   throw ez; 
					   }
					   if(objFileTemp.isDirectory()){
						   stemp = "CSV-File is a directory: '" + objFileTemp.getPath() + "'";
						   ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName());
						   throw ez; 
					   }
				   }else{
					   stemp = "CSV-File in CSV-Object";
					   ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 					
				   }
			
				   //create the ini-file-object from file-object
				   if(objActionConfigurationIniIn == null){
					  stemp = "Action-Configuration-Ini-File";
					  ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
					   throw ez; 		
				   }
				   this.objIniConfigAction = objActionConfigurationIniIn;

					if(StringZZZ.isEmpty(sDirTargetIn)){
						stemp = "target-directory";
						ExceptionZZZ ez = new ExceptionZZZ(stemp, iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
						throw ez; 	
					}
					this.sDirTarget = sDirTargetIn;

			   }//end main:
			   return bReturn;
	}//end function

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() {
	return this.objKernel;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#setKernelObject(zzzKernel.custom.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
	this.objKernel = objKernel;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#getLogObject()
	 */
	public KernelLogZZZ getLogObject() {
		return this.objLog;
	}
	
	
	public FileCsvZZZ getCsvObject(){
		return this.objFileCsv;	
	}
	

	
	
	public void setCsvObject(FileCsvZZZ objFileCsv){
		this.objFileCsv = objFileCsv;
	}

	/* (non-Javadoc)
	@see zzzKernel.basic.KernelAssetKernelZZZ#setLogObject(zzzKernel.custom.LogZZZ)
	 */
	public void setLogObject(KernelLogZZZ objLog) {
	this.objLog = objLog;
	}
	
	public boolean startIt() throws ExceptionZZZ{
		boolean bReturn = false;
		
		String stemp;int itemp=0;boolean btemp; 
		String sCCLast=new String("");
		String sColumntemp;
		String sProperty = new String("");
		String sSection=new String("");
		String sSectionBase=new String("");
		String sFileExpanded = new String("");
		String sFileEnd = new String("");
		FileZZZ objFileExpanded=null;		
		FileIniZZZ objIni=null;							
		Hashtable objsHashLine=null;
				
		main:{
		IKernelZZZ objKernel = this.getKernelObject();
		KernelLogZZZ objLog = objKernel.getLogObject();
		
		FileCsvZZZ objCSV = this.getCsvObject();			
	
		check:{
			if(objCSV == null){
				stemp = "'CSV Object, loaded from file'";
				 ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;							
			}		
			if(this.getActionConfiguration()==null){
				stemp = "missing property 'Action Configuration File'";
				 ExceptionZZZ ez = new ExceptionZZZ( stemp, iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());				  
				   throw ez;			
			}
		}//end check:

		//the default Headerline
		//TODO Default headerline - CC konfigurierbar machen, der f�r den "Wechsel zur n�chsten Datei verantwortlich ist.".Ohne diesen korrekten HeaderlineCC gibt es FEHLER		
		String sCCHeader = new String("Z00");
	
	  // The Filename
	  String sFileCSV = objCSV.getFileObject().getName();
	  //sFile = "processing_" + sFileCSV;

							
	
		//get the first line in the CSV-File
		objsHashLine = objCSV.getNextLine(); 
				
								
				
								while(objsHashLine!=null){
			
								//get the ControlCharacter element
								if (objsHashLine.containsKey("CC")){
									sColumntemp = (String)objsHashLine.get("CC");								
									//System.out.println(sColumntemp);
									objLog.writeLine(sColumntemp);								
													
									if (sColumntemp.equals(sCCHeader)==true){																														
										//saving the last ini-file																			
										if(objIni != null){
											//get a possible new filename from the ini-file
												  sFileEnd = objIni.getPropertyValue("ZSystem","Filename").getValue();
												  if(sFileEnd==null){
													sFileEnd = FileEasyZZZ.NameEndChange(sFileCSV, "ini"); 
												  }else if(sFileEnd.equals("")){
													sFileEnd = FileEasyZZZ.NameEndChange(sFileCSV, "ini"); 
												  }			
												  	
										    //remove the ZSystem-Section
										    btemp = objIni.deleteSection("ZSystem");
										    							
											//compute a possible expansion for the filename
											objFileExpanded = new FileZZZ(this.getDirectoryTarget(), sFileEnd, "");
											sFileExpanded = objFileExpanded.getNameExpandedNext();
											sFileEnd = this.getDirectoryTarget() + File.separator + sFileExpanded;
											objLog.writeLineDate("Saving data in file: '" + sFileEnd + "'");
											objIni.save(sFileEnd);
										}
									
										//the new ini-Object, with the default - processing - filename							
										//objIni = new FileIniZZZ(objKernel, objLog, this.getDirectoryTarget(), sFile, null);
										String[] satemp ={"filenew"};
										objIni = new FileIniZZZ(objKernel, "", "", satemp);	
																				
										//reset the section counter-table
										if(this.objintHashCC==null){
											this.objintHashCC = new Hashtable();	
										}else{
											this.objintHashCC.clear();
										}
									}
									sCCLast = sColumntemp;
								    
								     
									//get the section
									sSectionBase = this.getActionConfiguration().getPropertyValue("CC",sColumntemp).getValue();
									if(sSectionBase !=null){
										if(sSectionBase.equals("")==false){																					
												
												//get a section counter (if the same section has been used before in this data-set
												btemp = this.SectionCounterIncrease(sCCLast);			   
												if(btemp==true){
								    				itemp = this.getSectionCounterCurrent(sCCLast);
								    				}
								 				
								 				//only append a section counter only when there has been a section before
								 				if(itemp>= 1){
								 					sSection = sSectionBase + itemp; 						
								 				}else{
								 					sSection=sSectionBase;						
								 				}
												//System.out.println("Section: [" + sSection + "]");
												objLog.writeLine("Section: [" + sSection + "]");
											
											
												//get the other elements by column, column 1 is reserved for an update sign e.g. N__ or U__, etc.
												int iColumn = 2;  
												Integer intColumn;
												do{									
																						
													intColumn = new Integer(iColumn);
													stemp= intColumn.toString();
														
													//get the property (use the section base, no section counter appended)
													sProperty =  this.getActionConfiguration().getPropertyValue(sSectionBase, stemp).getValue();
	
													if(sProperty!=null){
														if(sProperty.equals("")){ break;} //Schleife verlassen
														if(sProperty.equals("-")==false){	  															  

															  //get the Value											
															  sColumntemp = (String)objsHashLine.get(stemp);													
															  //System.out.println(sProperty + ": " + sColumntemp);
															  objLog.writeLine(sProperty + ": " + sColumntemp);
	
															  //write the property to the current section
															  //if no value was found, sColumntemp is null. But null is not allowed for that paramenter.
															  //TODO Mach es konfigurierbar, ob Leerwerte gesetzt werden sollen. Normalerweise ja, sonst kann man ja keine Property in der ini-Datei leersetzen.
															  //TODO Entferne ggf. beim Leersetzen die Property aus der ini-Datei
															  if(sColumntemp==null){
															  	sColumntemp = new String("");
															  }
															  objIni.setPropertyValue(sSection, sProperty, sColumntemp, false);
														}//sproperty!="-"																	  										
													}//sproperty!=null
													iColumn++;	
												}while(sProperty.equals("")==false);
											
									}//end if sSection.equals("") == false
									}//end if sSection!=null			
										
								}//end if objHashLine.containsKey(""CC")
							
								//get the next line in the CSV-File
								objsHashLine = objCSV.getNextLine();
						
								}//end while(objHashLine!=null);
														
						
								//to save the last data-sets to the last ini-File
								  sFileEnd = objIni.getPropertyValue("ZSystem","Filename").getValue();
								  if(sFileEnd==null){
									sFileEnd = FileEasyZZZ.NameEndChange(sFileCSV, "ini"); 
								  }else if(sFileEnd.equals("")){
									sFileEnd = FileEasyZZZ.NameEndChange(sFileCSV, "ini"); 
								  }			
								
								//now remove the ZSystem-Section
								btemp = objIni.deleteSection("ZSystem");
								 														
								//compute a possibel expansion for the filename
								objFileExpanded = new FileZZZ(this.getDirectoryTarget(), sFileEnd, "");
								sFileExpanded = objFileExpanded.getNameExpandedNext();
								sFileEnd = this.getDirectoryTarget() + File.separator + sFileExpanded;
								objLog.writeLineDate("Saving data in file: '" + sFileEnd + "'");
								if(objIni != null){
									objLog.writeLineDate("Saving data in file: '" + this.getDirectoryTarget() + File.separator + sFileExpanded + "'");
									objIni.save(sFileEnd);
									bReturn = true;
								}
							
					
							}//end main:
		
		return bReturn;
	}//end function startIt()
	
	public boolean SectionCounterIncrease(String sCC){
		boolean bReturn = false;
		main:{
			Integer intTemp=null;
			int itemp;
			
			check:{
				if(sCC==null) break main;
				if(sCC.equals("")) break main;
				if(this.objintHashCC==null){
					 objintHashCC=new Hashtable();
				}
			}//end check
			
			//get the section-number-current
			 if(objintHashCC.containsKey(sCC)){
				 //case: increase the current entry
				 intTemp = (Integer)objintHashCC.get(sCC);
				 itemp = intTemp.intValue();
				 itemp++;
				 intTemp = new Integer(itemp);
			 }else{
				 //case: create a new entry
				 intTemp = new Integer(0);
			 }
			
			this.objintHashCC.put(sCC,intTemp);
			bReturn = true;
											 
		}//end main:
		return bReturn;	
	}//end function SectionCounterIncrease
}
