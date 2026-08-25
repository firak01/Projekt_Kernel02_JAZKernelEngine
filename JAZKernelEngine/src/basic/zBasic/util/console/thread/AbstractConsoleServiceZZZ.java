package basic.zBasic.util.console.thread;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import debug.zBasic.util.console.thread.multi.menu02.AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ;

public abstract class AbstractConsoleServiceZZZ<T> extends AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> implements IConsoleServiceZZZ {	
	private static final long serialVersionUID = 839513259027284036L;
	
	private IConsoleControllerZZZ objConsoleController=null;
	
	public AbstractConsoleServiceZZZ()  throws ExceptionZZZ {
		super();
	}
	public AbstractConsoleServiceZZZ(IConsoleControllerZZZ objConsole) throws ExceptionZZZ {
		super();
		AbstractConsoleUserStarterNew_(objConsole,null);
	}
	public AbstractConsoleServiceZZZ(IConsoleControllerZZZ objConsole,String sFlag) throws ExceptionZZZ {
		super();
		String[]saFlag=new String[1];
		saFlag[0]=sFlag;
		AbstractConsoleUserStarterNew_(objConsole,saFlag);
	}
	public AbstractConsoleServiceZZZ(IConsoleControllerZZZ objConsole,String[] saFlag) throws ExceptionZZZ {
		super();
		AbstractConsoleUserStarterNew_(objConsole,saFlag);
	}
	
	private boolean AbstractConsoleUserStarterNew_(IConsoleControllerZZZ objConsole, String[]saFlagControlIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(objConsole==null) {
				ExceptionZZZ ez = new ExceptionZZZ("No Console Object provided", iERROR_PARAMETER_MISSING, StringZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			this.setConsoleController(objConsole);
 		
 			//setzen der übergebenen Flags	
			if(saFlagControlIn != null){
				 String stemp; boolean btemp; String sLog;
				for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
					stemp = saFlagControlIn[iCount];
					btemp = setFlag(stemp, true);
					if(btemp==false){
						 String sKey = stemp;
						 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
						 this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
						//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
						// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
						// throw ez;		 
					}
				}
				if(this.getFlag("init")==true){
					bReturn = true;
					break main;
				}
			}
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
		 
	//### GETTER / SETTER
	@Override
	public synchronized IConsoleControllerZZZ getConsoleController() {
		return this.objConsoleController;
	}
	
	@Override
	public synchronized void setConsoleController(IConsoleControllerZZZ objConsoleController) {
		this.objConsoleController = objConsoleController;
	}
	
	//### Methoden
	@Override
	public boolean startit() throws ExceptionZZZ{
		//Für Klassen, die Methoden ohne Variablenübergabe machen wollen.
		boolean bReturn = false;
		main:{
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
