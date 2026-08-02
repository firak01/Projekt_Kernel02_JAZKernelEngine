package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.file.txt.stream.FileTextSplitterZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zBasic.util.system.Syso;
import junit.framework.TestCase;

public class FileTextSplitterZZZTest extends TestCase{
	private File objFileSource=null;
	private File objFileTargetPre=null;
	private File objFileTargetPost=null;
	private final static String strFILE_DIRECTORY_DEFAULT = new String("c:\\fglKernel\\KernelTest");
	private final static String strFILE_NAME_DEFAULT = new String("JUnitKernelFileSplittTest.txt");
	private final static String strFILE_NAME_DEFAULT_RESULT_A = new String("JUnitKernelFileSplitResultA.txt");
	private final static String strFILE_NAME_DEFAULT_RESULT_B = new String("JUnitKernelFileSplitResultB.txt");

	
	private FileTextSplitterZZZ objSplitterTest = null;
	
	//+++ Test setup
	private static boolean doCleanup = true;		//default = true      false -> kein Aufraeumen im tearDown().
	List<String>listFilePathUsed= null; // Wichtig für das Aufräumen

	protected void setUp(){
		try {			
			
			//Eine Beispieldatei. Durch deren Existenz kann man den Namen einer Folgedatei bestimmen, welche eine 'Expansion' erhält.
			//Merke: Dadurch, dass man die Datei per stream - aufbaut, werden keine Einträge an eine vorherige Datei angehängt, sondern die Datei wird für jeden Test neu geschrieben.			
			String sFilePathTotal = null;            String sFilePathTotalA = null; 			String sFilePathTotalB = null;
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotal = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT );
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotal = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT );			   
			}
			IStreamZZZ objStreamFile = new StreamZZZ(sFilePathTotal, 1);  //This is not enough, to create the file
			objStreamFile.println("This is a temporarily test file.");      //Now the File is created
			objStreamFile.println("erste zeile");      //Now the File is created
			objStreamFile.println("zweite zeile");   
			objStreamFile.println("dritte zeile");    
			objStreamFile.println("vierte zeile");   
			objStreamFile.println("fünfte zeile");     
			objStreamFile.println("sechste zeile");      
			objStreamFile.println("sechste zeile ist doppelt");     
			objStreamFile.println("doppelte sechste zeile");     
			objStreamFile.println("erste zeile ist doppelt");  
			objStreamFile.println("doppelte erste zeile");    
			objStreamFile.println("noch eine doppelte erste zeile");   
			objStreamFile.close();
			
			this.objFileSource = new File(sFilePathTotal);
			
			//+++++++++++++++++++++++++++++++++++
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotalA = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT_RESULT_A );
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotalA = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT_RESULT_A );			   
			}
						
			this.objFileTargetPre = new File(sFilePathTotalA);			
			//++++++++++++++++++++++++++
			//+++++++++++++++++++++++++++++++++++
			if(FileEasyZZZ.exists(strFILE_DIRECTORY_DEFAULT)){
				sFilePathTotalB = FileEasyZZZ.joinFilePathName(strFILE_DIRECTORY_DEFAULT, strFILE_NAME_DEFAULT_RESULT_B );
			}else{
				//Eclipse Workspace
				File f = new File("");
			    String sPathEclipse = f.getAbsolutePath();
			    System.out.println("Path for Kernel Directory Default does not exist. Using workspace absolut path: " + sPathEclipse);
			    sFilePathTotalB = FileEasyZZZ.joinFilePathName(sPathEclipse + File.separator + "test", strFILE_NAME_DEFAULT_RESULT_B );			   
			}						
			this.objFileTargetPost = new File(sFilePathTotalB);	
			
			
			//The main object used for testing
			objSplitterTest = new FileTextSplitterZZZ(objFileSource);
			
			//für´s Aufräumen
			listFilePathUsed = new ArrayList<String>();
			listFilePathUsed.add(objFileSource.getAbsolutePath());
			listFilePathUsed.add(objFileTargetPre.getAbsolutePath());
			listFilePathUsed.add(objFileTargetPost.getAbsolutePath());
		
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}//END setup
	 
	@Override
	protected void tearDown() {
		try {
			if(doCleanup) {
				if(listFilePathUsed!=null) {
					for(String sFilePath : listFilePathUsed) {
						Syso.println("Lösche Datei: '" + sFilePath + "'");
						boolean btemp = FileEasyZZZ.removeFile(sFilePath);
						if(!btemp) {
							Syso.println("Konnte Datei: '" + sFilePath + "' nicht erfolgreich löschen.");
						}
					}
				}
			}
		} catch (ExceptionZZZ ez) {
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	}
	
	public void testConstructor(){
		try{
			//Init - Object
			FileTextSplitterZZZ objSplitterInit = new FileTextSplitterZZZ(objFileSource);
						
			//TestKonfiguration pr�fen
			assertNotNull(objSplitterInit);
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
	}
	
	public void testSplitByLinenumber(){		
		try {
			FileTextSplitterZZZ objSplitterInit = new FileTextSplitterZZZ(objFileSource);					
			objSplitterInit.split(3);
			objSplitterInit.setFilePathPre(objFileTargetPre.getAbsolutePath());
			objSplitterInit.setFilePathPost(objFileTargetPost.getAbsolutePath());
			objSplitterInit.save();
									
			boolean bExistsPre = FileEasyZZZ.exists(objFileTargetPre);
			assertTrue(bExistsPre);
			listFilePathUsed.add(objFileTargetPre.getAbsolutePath());
			
			boolean bExistsPost = FileEasyZZZ.exists(objFileTargetPost);			
			assertTrue(bExistsPost);
			listFilePathUsed.add(objFileTargetPost.getAbsolutePath());
			
			
		}catch(ExceptionZZZ ez){
			fail("An exception happend testing: " + ez.getDetailAllLast());
		}
		
	}
	
	

}//END Class
