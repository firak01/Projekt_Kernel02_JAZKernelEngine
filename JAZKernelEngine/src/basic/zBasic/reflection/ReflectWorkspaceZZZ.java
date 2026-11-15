package basic.zBasic.reflection;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zKernel.IKernelConfigZZZ;

public class ReflectWorkspaceZZZ implements IConstantZZZ {
	
	private ReflectWorkspaceZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	
	public static String computeWorkspaceRunningProjectPath() throws ExceptionZZZ {
		String sReturn = null;
//		try {
			//Das verwendet Slashe ... ist fuer den WorkspacePath ggfs. nicht gut.
//			URL workspaceURL = new File(FileEasyZZZ.sDIRECTORY_CURRENT).toURI().toURL();
//			sReturn = workspaceURL.getPath();
			
			//siehe auch
			sReturn = FileEasyZZZ.getDirectoryOfExecutionAsString();
			//String sTest2 = FileEasyZZZ.getDirectoryOfSystemClassloaderAsString();
			
//			String userDir = System.getProperty("user.dir");
//			Path path = Paths.get(userDir);
//			String project = path.toString();
			
			sReturn = StringZZZ.stripFileSeparators(sReturn);
//		} catch (MalformedURLException e) {
//			ExceptionZZZ ez  = new ExceptionZZZ("MalformedURLException: " + e.getMessage(), iERROR_PARAMETER_VALUE, FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
//			throw ez;
//		}
		return sReturn;
	}
	
	/** Der Pfad eines ZKernel-Projekts im Eclipse-Workspace.
	 *  Das wird aus einer KernelKonfiguration geholt.
	 *  
	 *  Merke: Java selbst hat keine Idee von einem Workspace;
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.06.2023, 21:02:40
	 */
	public static String computeWorkspacePath(IKernelConfigZZZ objConfig) throws ExceptionZZZ{
		String sReturn = null;
		main:{
		 if(objConfig==null) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }
			//Das ist ein Z-Kernel Konzept und funktioniert nur innerhalb der Entwicklungsumgebung
		    if(objConfig.isInJar() || objConfig.isOnServer()) break main;
				 
			sReturn = objConfig.getProjectPathTotal();
		}//end main:
		return sReturn;
	}
	
	/** Der Pfad eines ZKernel-Projekts im Eclipse-Workspace.
	 *  Das wird aus einer KernelKonfiguration geholt.
	 *  
	 *  Merke: Java selbst hat keine Idee von einem Workspace;
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.06.2023, 21:02:40
	 */
	public static String computeWorkspacePath(IKernelConfigZZZ objConfig, String sDirectory) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			if(objConfig==null) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }
			//Das ist ein Z-Kernel Konzept und funktioniert nur innerhalb der Entwicklungsumgebung
		    if(objConfig.isInJar() || objConfig.isOnServer()) break main;
		    	 
		    String sWorkspaceProjekt = ReflectWorkspaceZZZ.computeWorkspacePath(objConfig);
		    if(StringZZZ.isEmpty(sWorkspaceProjekt)){							
			  	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object has no project path.", iERROR_PROPERTY_MISSING,   FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;								
			}
		    
		    String sDirectoryTotal = FileEasyZZZ.joinFilePathNameForWorkspace(sWorkspaceProjekt, sDirectory);
		    sReturn = sDirectoryTotal;
		}//end main:
		return sReturn;
	}
	
	/** Der Pfad eines ZKernel-Projekts im Eclipse-Workspace.
	 *  Das wird aus einer KernelKonfiguration geholt.
	 *  
	 *  Merke: Java selbst hat keine Idee von einem Workspace;
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 22.06.2023, 21:02:40
	 */
	public static String computeWorkspacePath(IKernelConfigZZZ objConfig, String sDirectory, String sFilename) throws ExceptionZZZ{
		String sReturn = null;	
		main:{
			if(objConfig==null) {
		    	ExceptionZZZ ez = new ExceptionZZZ("IKernelConfig - Object", iERROR_PARAMETER_MISSING,   FileEasyZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
		    }
			//Das ist ein Z-Kernel Konzept und funktioniert nur innerhalb der Entwicklungsumgebung
		    if(objConfig.isInJar() || objConfig.isOnServer()) break main;
				 
		    String sWorkspaceProjekt = ReflectWorkspaceZZZ.computeWorkspacePath(objConfig);
		    String sDirectoryTotal = FileEasyZZZ.joinFilePathNameForWorkspace(sWorkspaceProjekt, sDirectory);
		    String sFilepathTotal = FileEasyZZZ.joinFilePathName(sDirectoryTotal, sFilename);
		    sReturn = sFilepathTotal;
		}//end main:
		return sReturn;
	}
	
	
	
	/**Merke: Das benötigt Zugriff auf Plugins, also unbedingt in ein eigenständiges Projekt auslagern,
	 *        in das man dann diese Plugins einbinden kann.
	 * 
	 *  if no editor is open
	 * @return
	 * @author Fritz Lindhauer, 22.06.2023, 19:45:08
	 */
//	public static String getCurrentProjectName() {
//		IViewPart [] parts =
//			      MyPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().getViews();
//			    IProject activeProject = null;
//
//			    for(int i=0;i<parts.length;i++)
//			    {
//			        if(parts[i] instanceof ResourceNavigator)
//			        {
//			            ResourceNavigator navigator = (ResourceNavigator)parts[i];
//			            StructuredSelection sel   =
//			              (StructuredSelection)navigator.getTreeViewer().getSelection();
//			            IResource resource = (IResource)sel.getFirstElement();
//			            activeProject = resource.getProject();
//			            break;
//			        }
//			    }
//			    String activeProjectName = activeProject .getName();
//	}
}
