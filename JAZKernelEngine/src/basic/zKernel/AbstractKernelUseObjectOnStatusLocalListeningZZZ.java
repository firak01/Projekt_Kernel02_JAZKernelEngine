package basic.zKernel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ILogProtocolZZZ;
import basic.zBasic.IObjectWithStatusEnabledZZZ;
import basic.zBasic.AbstractObjectWithFlagOnStatusLocalListeningZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.component.AbstractProgramWithFlagOnStatusLocalListeningZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zKernel.status.IStatusLocalMapForStatusLocalUserZZZ;
import basic.zKernel.status.StatusLocalAvailableHelperZZZ;
import custom.zKernel.KernelLogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractKernelUseObjectOnStatusLocalListeningZZZ extends AbstractObjectWithFlagOnStatusLocalListeningZZZ implements IKernelUserZZZ, IKernelContextUserZZZ {
	private static final long serialVersionUID = 6048997985909418786L;
	
	//Merke: Da es keine Mehrfachvererbung gibt, müssen die Objekte und Methoden aus AbstractKernelUseObjectZZZ hier auch vorkommen...
	protected volatile IKernelZZZ objKernel=null;
	protected volatile KernelLogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile IKernelContextZZZ objContext = null; //die Werte des aufrufenden Programms (bzw. sein Klassenname, etc.), Kann anders als beim Kernel selbst sein.
		
	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ() throws ExceptionZZZ{		
		super();
	}
	
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	
	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
		KernelUseObjectListeningNew_(objKernel, null, null);		
	}
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ{
		super(sFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_(objKernel, null, null);
	}
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(saFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt		
		KernelUseObjectListeningNew_(objKernel, null, null);
	}
	
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelZZZ objKernel, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_(objKernel, null, null);				
	}
	
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext) throws ExceptionZZZ{
		super();//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectListeningNew_(objKernel, null, objKernelContext);						
	}
	
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super();
		KernelUseObjectListeningNew_(null, objKernelUsing, null);
	}
	
	public AbstractKernelUseObjectOnStatusLocalListeningZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		KernelUseObjectListeningNew_(null, objKernelUsing, null);
	}
	
	private boolean KernelUseObjectListeningNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing, IKernelContextZZZ objKernelContext) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			boolean btemp;	
								
			//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			IKernelZZZ objKernelUsed = null;
			if(objKernel==null) {
				if(objKernelUsing==null) {
					ExceptionZZZ ez = new ExceptionZZZ("KernelObject and KernelUsingObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;					
				}
				
				objKernelUsed = objKernelUsing.getKernelObject();
				if(objKernelUsed==null) {
					ExceptionZZZ ez = new ExceptionZZZ("KernelObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}else {
				objKernelUsed = objKernel;
			}
			
			this.objKernel = objKernelUsed;
			this.objLog = objKernelUsed.getLogObject();
			
			this.setContextUsed(objKernelContext);
						
			//++++++++++++++++++++++++++++++
			//HIER geht es darum ggfs. die Flags zu übernehmen, die irgendwo gesetzt werden sollen und aus dem Kommandozeilenargument -z stammen.
			//D.h. ggfs. stehen sie in dieser Klasse garnicht zur Verfügung
			//Kommandozeilen-Argument soll alles übersteuern. Darum auch FALSE setzbar. Darum auch nach den "normalen" Flags verarbeiten.
			if(this.getKernelObject()!=null) {
				IKernelConfigZZZ objConfig = this.getKernelObject().getConfigObject();
				if(objConfig!=null) {
					//Übernimm die als Kommandozeilenargument gesetzten FlagZ... die können auch "false" sein.
					Map<String,Boolean>hmFlagZpassed = objConfig.getHashMapFlagPassed();
					if(hmFlagZpassed!=null) {
						Set<String> setFlag = hmFlagZpassed.keySet();
						Iterator<String> itFlag = setFlag.iterator();
						String sLog;
						while(itFlag.hasNext()) {
							String sKey = itFlag.next();
							 if(!StringZZZ.isEmpty(sKey)){
								 Boolean booValue = hmFlagZpassed.get(sKey);
								 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
								 if(btemp==false){						 
									 sLog = ReflectCodeZZZ.getPositionCurrent() + "The passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
									 this.printlnDate(sLog);
		//							  Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!
		//							  ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_VALUE, this,  ReflectCodeZZZ.getMethodCurrentName()); 
		//							  throw ez;		 
								  }
							 }
						}
					}
				}	
			}			
			//+++++++++++++++++++++++++++++++						
			bReturn = true;
		}//end main;
		return bReturn;
	}
}//end class

