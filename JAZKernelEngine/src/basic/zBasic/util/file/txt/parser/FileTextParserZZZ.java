package basic.zBasic.util.file.txt.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import custom.zUtil.io.FileZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;


/**This class handles sequentiel files.
 * Working with "Random Access" files is more comfortable. See "TxtReaderZZZ"
 * @author 0823
 *
 */
public class FileTextParserZZZ extends AbstractObjectWithFlagZZZ implements Closeable {
	private File objFileSourceOriginal = null;
	private File objFileSource=null;   //nach jedem Schreiben wird hier das zuvorige Target-File reingesetzt, so dass hier ein erneutes Schreiben von dem zuvor erfolgtem Schreiben ausgehen kann.
	
	public FileTextParserZZZ(File objFile, String[] saFlagControl) throws ExceptionZZZ{		
		KernelFileTextParserNew_(objFile, saFlagControl);
	}
	public FileTextParserZZZ(File objFile) throws ExceptionZZZ{
		KernelFileTextParserNew_(objFile, null);
	}
	 
	private void KernelFileTextParserNew_(File objFile, String[] saFlagControl) throws ExceptionZZZ{		
		main:{
			check:{
				String stemp; boolean btemp;
				if(saFlagControl != null){
					for(int iCount = 0;iCount<=saFlagControl.length-1;iCount++){
						stemp = saFlagControl[iCount];
						btemp = setFlag(stemp, true);
						if(btemp==false){ 								   
							   ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 
							   throw ez;		 
						}
					}
					if(this.getFlag("init")) break main;
				}
				
				
				if(objFile==null){
					   ExceptionZZZ ez = new ExceptionZZZ("Source-File", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}
				this.setFileSource(objFile);
				this.setFileSourceOriginal(objFile);				
		}//END check:		
	}//END main:
}
	
	/**replaces the line, which matches the regular expression, with the passed string.
	 *  This is no "in place" replacement, so a new file is created with all lined (and the replaced ones).
	 *  
	 *  TODO: Die Funktionalit�ten von "KernelFileTextJoin" und "KernelFileTextCopy" in diese Klasse �bernehmen.
	 *  TODO: InPlace - replacement, d.h. die gleiche Datei behandeln und darin ersetzen.
	 *  TODO: replaceNthLine(...) Nur der xte-Zeileneintrag soll ge�ndert werden. Normalerwiese der 1.
	 *  TODO: readLineAt(int iLineNr) Gibt lediglich den String in der iLineNr zur�ck.
	 *  TODO: Es erlauben, nicht sofort zu speichern. Z.B: die Zielen erst in einen Vektor einlasen, diesen dann parsen und den Wert �ndern.
	 *             Dann muss es aber auch eine Methode .save(...) geben.
	 *  
	 *  
	 * @param objFileTarget
	 * @param objRe
	 * @param sStringNew, if null, the line will be removed.
	 *
	 * @return int, number of lines replaced
	 *
	 * javadoc created by: 0823, 03.07.2006 - 13:59:13
	 * @throws ExceptionZZZ 
	 */
	public int replaceLine(File objFileTarget, org.apache.regexp.RE objRe, String sStringNew) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
			try{
			File objFileSource=null;
			check:{
				if(objFileTarget==null){
					 ExceptionZZZ ez = new ExceptionZZZ("TargetFile-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					 throw ez;		 					   
				}else if(objFileTarget.isDirectory()==true){
					 ExceptionZZZ ez = new ExceptionZZZ("TargetFile-Object is a directory, a file was expected.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 	
				}

				if(objRe==null){
					 ExceptionZZZ ez = new ExceptionZZZ(  "RegularExpression-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					  throw ez;		 
				}
				
				if(this.objFileSource==null){
					 ExceptionZZZ ez = new ExceptionZZZ( "SourceFile-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}else{
					objFileSource = this.objFileSource;
				}
			}//END check:
			
//			Von dem SourceFile nun alles Zeilen durchgehen und mit dem Re-Ausdruck vergleichen
			   FileReader fin = new FileReader(objFileSource);
			   BufferedReader bfin = new BufferedReader(fin);
			   String sLine = bfin.readLine();
		
			   //TODO dies per getFlag(..) des KernelObjekts steuern
			   objRe.setMatchFlags(org.apache.regexp.RE.MATCH_CASEINDEPENDENT); 
			   
			   FileWriter fout = new FileWriter(objFileTarget);
			   BufferedWriter bfout = new BufferedWriter(fout);
			   while(sLine != null){
				 
				   boolean bMatch = objRe.match(sLine);
				   if(bMatch == true){
					  if(sStringNew!=null){
						  //Fall: Anderen String setzen
						  bfout.write(sStringNew);
						  bfout.newLine();
						  iReturn++;
					  }
				   }else{
					   //Fall: Zeile unverändert übernehmen
					   bfout.write(sLine);
					   bfout.newLine();
				   }				   
				   sLine = bfin.readLine();
			   }//END while
					   
			   //Schliessen der Dateistroeme
				bfin.close();
				bfout.close();
				fin.close();
				fout.close();
				
				// NUN DAS BISHERIGE TARGET-FILE ZUM SOURCE-FILE MACHEN, DAMIT EIN ERNEUTES SCHREIBEN VON DEM BISHER GESCHREIBENEN AUSGEHEN KANN
				this.setFileSource(objFileTarget);
			
		   }catch(FileNotFoundException e){
			   //Dateibehandlung - Exceptions 	
			   ExceptionZZZ ez = new ExceptionZZZ("File not found exception: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }catch(IOException e){
			   ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }	
		
			
			
		}//END main:
		return iReturn;
	}
	
	
	/**TODO Describe what the method does
	 * @param objFileTarget
	 * @param objRe
	 * @throws ExceptionZZZ, 
	 *
	 * @return int, Number of lines which were removed
	 *
	 * javadoc created by: 0823, 05.07.2006 - 07:36:00
	 */
	public int removeLine(File objFileTarget, org.apache.regexp.RE objRe) throws ExceptionZZZ{
		int iReturn = 0;
		iReturn = this.replaceLine(objFileTarget, objRe, null);
		return iReturn;
	}
	
	public Integer[] readLineNumberAll(org.apache.regexp.RE objRe) throws ExceptionZZZ{
		Integer[] intaReturn=null;
		 ArrayList listaInteger = new ArrayList();
		   
		main:{
			File objFileSource = null;
			  try{
			check:{
				if(objRe==null){
					 ExceptionZZZ ez = new ExceptionZZZ("RegularExpression-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}
				if(this.objFileSource==null){
					 ExceptionZZZ ez = new ExceptionZZZ("SourceFile-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 
				}else{
					objFileSource = this.objFileSource;
				}
				
			}//END check
		
		//Von dem SourceFile nun alles Zeilen durchgehen und mit dem Re-Ausdruck vergleichen
			   FileReader fin = new FileReader(objFileSource);
			   BufferedReader bfin = new BufferedReader(fin);
			   String sLine = bfin.readLine();
			   int iCount = 0;
			  
			   //TODO dies per getFlag(..) des KernelObjekts steuern
			   objRe.setMatchFlags(org.apache.regexp.RE.MATCH_CASEINDEPENDENT); 
			   
			   while(sLine != null){
				   iCount++;
				   
				   boolean bMatch = objRe.match(sLine);
				   if(bMatch == true){
					   Integer intLine = new Integer(iCount);
					   listaInteger.add(intLine);
				   }
				   sLine = bfin.readLine();
			   }
				
				//   File objFileTargetExpanded = new File(sFileTargetExpanded);
			   //FileWriter fout = new FileWriter(objFileTargetExpanded);
			   //BufferedWriter bfout = new BufferedWriter(fout);

			  // int c;
			   //ToDo: Verlauf der Kopieraktion protokollieren.
			   //while ((c = bfin.read()) != -1) bfout.write(c);
			   //Schliessen der Dateistr�me
				bfin.close();
				//bfout.close();
				fin.close();
				//fout.close();
			
		   }
		   //Dateibehandlung - Exceptions 				 
		   catch(FileNotFoundException e){
			   ExceptionZZZ ez = new ExceptionZZZ("File not found exception: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }

		   //Schreib-/LeseExceptions
		   catch(IOException e){
			   ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }	
		
		
		
		}//End main
		 
		   if(listaInteger.isEmpty()==false){
			   intaReturn = new Integer[listaInteger.size()];
			   for(int icount=0;icount < listaInteger.size(); icount ++){
				   intaReturn[icount] = (Integer)listaInteger.get(icount);
			   }
		   }
			   return intaReturn;
		
	}
	
	/**Pr�ft auf Vorhandensein dieser Zeile in der Datei. Ggf. kann eine casesensitive �bereinstimmung gefordert werden.
	* @param sLineToFind
	* @param bCaseSenitiveMatch
	* @return
	* 
	* lindhaueradmin; 21.03.2007 16:29:11
	 */
	public boolean hasLine(String sLineToFind, boolean bCaseSensitiveMatch) throws ExceptionZZZ{ 
		boolean bReturn = false;
		main:{
			File objFileSource=null;
			try{
				check:{
					if(StringZZZ.isEmpty(sLineToFind)) break main;					
					if(this.objFileSource==null){
						 ExceptionZZZ ez = new ExceptionZZZ( "SourceFile-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						 throw ez;		 
					}else{
						objFileSource = this.objFileSource;
					}
				}//END check
				
	
				FileReader fread = new FileReader(objFileSource);
				BufferedReader bfread = new BufferedReader(fread);
				String sLine = bfread.readLine();
				if(bCaseSensitiveMatch==false){		//Diese if-Abfrage aus Performancegr�nden ausserhalb der Schleife	
					while(StringZZZ.isEmpty(sLine)==false){				
						sLine = bfread.readLine();
						if(sLineToFind.equals(sLine)){
							bReturn = true;
							break main;
						}						
					}
				}else{
					while(StringZZZ.isEmpty(sLine)==false){				
						sLine = bfread.readLine();
						if(sLineToFind.equalsIgnoreCase(sLine)){
							bReturn = true;
							break main;
						}						
					}				
				}
				bfread.close();
				fread.close();					
			} catch(FileNotFoundException e){
//				Dateibehandlung - Exceptions 			
			   		ExceptionZZZ ez = new ExceptionZZZ("File not found exception: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
			   		throw ez;
			   }  
			   catch(IOException e){
				   //Schreib-/LeseExceptions
				   ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
			   		throw ez;
			   }	
		}//END main
		return bReturn;
	}
	
	/**Returns true if the .getFileSource() - file has a line which machtes the RE
	 * @param objRe
	 * @return
	 * @return boolean
	 *
	 * javadoc created by: 0823, 04.07.2006 - 11:58:33
	 * @throws Exception 
	 */
	public boolean hasLine(org.apache.regexp.RE objRe) throws ExceptionZZZ{
		boolean bReturn = false;
			main:{
				File objFileSource=null;
				try{
				check:{
					if(objRe==null){
						 ExceptionZZZ ez = new ExceptionZZZ("RegularExpression-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						   throw ez;		 
					}
					if(this.objFileSource==null){
						 ExceptionZZZ ez = new ExceptionZZZ( "SourceFile-Object", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
						   throw ez;		 
					}else{
						objFileSource = this.objFileSource;
					}
				}//END check:
		
		
//		Von dem SourceFile nun alles Zeilen durchgehen und mit dem Re-Ausdruck vergleichen
		   FileReader fin = new FileReader(objFileSource);
		   BufferedReader bfin = new BufferedReader(fin);
		   String sLine = bfin.readLine();
		   
		   //TODO dies per getFlag(..) des KernelObjekts steuern
		   objRe.setMatchFlags(org.apache.regexp.RE.MATCH_CASEINDEPENDENT); 
		   
		   while(sLine != null){			  
			   boolean bMatch = objRe.match(sLine);
			   if(bMatch == true){
				 bReturn = true;
				 break main;
			   }
			   sLine = bfin.readLine();
		   }
			
			//   File objFileTargetExpanded = new File(sFileTargetExpanded);
		   //FileWriter fout = new FileWriter(objFileTargetExpanded);
		   //BufferedWriter bfout = new BufferedWriter(fout);

		  // int c;
		   //ToDo: Verlauf der Kopieraktion protokollieren.
		   //while ((c = bfin.read()) != -1) bfout.write(c);
		   //Schliessen der Dateistr�me
			bfin.close();
			//bfout.close();
			fin.close();
			//fout.close();
			   	 
			} catch(FileNotFoundException e){
//				Dateibehandlung - Exceptions 			
			   		ExceptionZZZ ez = new ExceptionZZZ("File not found exception: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
			   		throw ez;
			   }  
			   catch(IOException e){
				   //Schreib-/LeseExceptions
				   ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
			   		throw ez;
			   }	
			}//END main:
		return bReturn;
	}
	
	public long appendLine(File objFileTargetIn, String sStringNew) throws ExceptionZZZ{
		long lReturn = 0;
		main:{
			try{
			File objFileTarget=null;
			check:{
				if(objFileTargetIn==null){
					objFileTarget = this.getFileSource();
					if( objFileTarget == null){
					 ExceptionZZZ ez = new ExceptionZZZ("TargetFile-Object", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;
					}
				}else if(objFileTargetIn.isDirectory()==true){
					 ExceptionZZZ ez = new ExceptionZZZ( "TargetFile-Object is a directory, a file was expected.", iERROR_PARAMETER_VALUE, this, ReflectCodeZZZ.getMethodCurrentName()); 
					   throw ez;		 	
				}else{
					objFileTarget = objFileTargetIn;
				}
			
		if(sStringNew==null){
			 ExceptionZZZ ez = new ExceptionZZZ( "Line to be appended", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName()); 
			   throw ez;	
		}
			}//END check:
			
			//LAUT DOKU, KANN MAN DEN WAHLFREIEN ZUGRIFF NUR BEI DATENS�TZEN MIT GLEICHER L�NGE DURCHF�HREN

			  
			  /* DAS ERSETZT ABER DIE BESTEHENDEN EINTR�GE !!! WAS NICHT GEW�NSCHT IST !!! 
			   FileWriter fout = new FileWriter(objFileTarget);			   
			   BufferedWriter bfout = new BufferedWriter(fout);
			   PrintWriter pw = new PrintWriter(bfout, true); //Damit man eine Zeile mit println(...) anh�ngen kann
			   pw.write(sStringNew);
			   //pw.println(sStringNew);
			   
			   //Schliessen der Dateistr�me
				//bfin.close();
				bfout.close();
				//fin.close();
				fout.close();
				pw.close();
				
				// NUN DAS BISHERIGE TARGET-FILE ZUM SOURCE-FILE MACHEN, DAMIT EIN ERNEUTES SCHREIBEN VON DEM BISHER GESCHREIBENEN AUSGEHEN KANN
				this.setFileSource(objFileTarget);
				bReturn = true;
				*/
			
			//DARUM ERST BIS ZUM LETZTEN DATENSATZ GEHEN
//			Von dem SourceFile nun alles Zeilen durchgehen und mit dem Re-Ausdruck vergleichen
			   FileReader fin = new FileReader(objFileSource);
			   BufferedReader bfin = new BufferedReader(fin);
			   String sLine = bfin.readLine();
			  			  
			   FileWriter fout = new FileWriter(objFileTarget);
			   BufferedWriter bfout = new BufferedWriter(fout);
			   while(sLine != null){
				   lReturn++;
				   
					   //Fall: Zeile unver�ndert �bernehmen
					   bfout.write(sLine);
					   bfout.newLine();		   
				   sLine = bfin.readLine();
			   }//END while
			
				//an der letzten Zeile etwas anh�ngen
			   lReturn++;
				bfout.write(sStringNew);
				bfout.newLine();		
			
			   //Schliessen der Dateistr�me
				bfin.close();
				bfout.close();
				fin.close();
				fout.close();
				
				// NUN DAS BISHERIGE TARGET-FILE ZUM SOURCE-FILE MACHEN, DAMIT EIN ERNEUTES SCHREIBEN VON DEM BISHER GESCHREIBENEN AUSGEHEN KANN
				this.setFileSource(objFileTarget);
			
		   }catch(FileNotFoundException e){
			   //Dateibehandlung - Exceptions 	
			   ExceptionZZZ ez = new ExceptionZZZ("File not found exception: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }catch(IOException e){
			   ExceptionZZZ ez = new ExceptionZZZ("IOException: " + e.getMessage(), iERROR_PARAMETER_VALUE, this,ReflectCodeZZZ.getMethodCurrentName());
		   		throw ez;
		   }	
		
			
			
		}//END main:
		return lReturn;
	}
	
	//######## Getter // Setter
	public void setFileSource(File objFile){
		this.objFileSource = objFile;
	}
	public File getFileSource(){
		return this.objFileSource;
	}
	
	
	public void setFileSourceOriginal(File objFile){
		this.objFileSourceOriginal = objFile;
	}
	public File getFileSourceOriginal(){
		return this.objFileSourceOriginal;
	}
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	@Override
    public void close() throws IOException{
		//Hier gibt es (noch) keinen Stream auf Property-Ebene.
		//Die Streams werden (noch) alle in den Methoden schon geschlossen.
//        if(objStream!=null){
//            objStream.close();
//        }
    }
}//END class
