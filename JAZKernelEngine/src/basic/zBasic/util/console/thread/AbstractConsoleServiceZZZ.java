package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import debug.zBasic.util.console.thread.multi.menu02.AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;

public abstract class AbstractConsoleServiceZZZ<T> extends AbstractThreadWithStatusLocalOnStatusLocalListeningZZZ<T> implements IConsoleServiceZZZ, IConsoleServiceZZZ_menuPointUsing {	
	private static final long serialVersionUID = 839513259027284036L;
	
	private IConsoleControllerZZZ objConsoleController=null;
	private HashMapZZZ<String, Object> hmVariable = null;
	private IMenuPointZZZ         objMenuPoint = null; //Der ausgewählte Menüpunkt. Das Objekt hat Methoden die ausgeührt werden können, HashMap von Variablen, etc. 
	
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
	
	@Override
	public synchronized HashMapZZZ<String, Object> getVariableHashMap() throws ExceptionZZZ {
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapZZZ<String,Object>();
		}
		return this.hmVariable;
	}
	
	@Override 
	public synchronized void setVariableHashMap(HashMapZZZ<String, Object> hmVariable) throws ExceptionZZZ {
		this.hmVariable = hmVariable;
	}
	
	@Override 
	public synchronized IMenuPointZZZ getMenuPoint() throws ExceptionZZZ {
		return this.objMenuPoint;
	}
	
	@Override
	public void setMenuPoint(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
		this.objMenuPoint = objMenuPoint;
	}
	
	//### Methoden
	@Override
	public boolean start() throws ExceptionZZZ {
		return this.startit();
	}
	
	@Override
	public boolean startit() throws ExceptionZZZ{
		//Für Klassen, die Methoden ohne Variablenübergabe machen wollen.
		boolean bReturn = false;
		main:{
			IMenuPointZZZ objMenuPoint = this.getMenuPoint();		
			if(objMenuPoint==null) {
				HashMapZZZ<String, Object> hmVariable = this.getVariableHashMap();
				bReturn = this.startit(hmVariable);
			}else {
				bReturn = this.startit(objMenuPoint);
			}
		}//end main:
		return bReturn;
	}
	
	@Override
	public abstract boolean startit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ;
	
	//Startit wird dann von einem anderen Thread aus aufgerufen.
	//Das MenuPoint-Objekt hat seinen eigenen Code, bzw. weiss welches ServiceObject er nutzt.
	//Der MenuPoint weiss auch, ob er das ServiceObjekt wiederholt in einem ServiceThreadObject nutzt.
	@Override
	public boolean startit(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{			
			bReturn = objMenuPoint.onStartit();
		}//end main:
		return bReturn;
	}	
}
