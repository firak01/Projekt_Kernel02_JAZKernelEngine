package basic.zKernel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zKernel.KernelLogZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 * @param <T>
 */
public abstract class AbstractKernelUseObjectZZZ<T> extends AbstractObjectWithFlagZZZ<T> implements IKernelUserZZZ, IKernelContextUserZZZ {
	private static final long serialVersionUID = 8229692064424314912L;
	protected volatile IKernelZZZ objKernel=null;
	protected volatile KernelLogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile IKernelContextZZZ objContext = null; //die Werte des aufrufenden Programms (bzw. sein Klassenname, etc.), Kann anders als beim Kernel selbst sein.
		
	/** This Constructor is used as 'implicit super constructor' 
	* Lindhauer; 10.05.2006 06:05:14
	 */
	public AbstractKernelUseObjectZZZ(){		
		//20080422 wenn objekte diese klasse erweitern scheint dies immer ausgeführt zu werden. Darum hier nicht setzen !!! this.setFlag("init", true);
	}
	
	public AbstractKernelUseObjectZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag);
	}
	
	public AbstractKernelUseObjectZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
	}

	/** This constructor declares the used Log-Object as the Kernel-LogObject.
	* Lindhauer; 10.05.2006 06:06:00
	 * @param objKernel
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectZZZ(IKernelZZZ objKernel) throws ExceptionZZZ{
		super();
		KernelUseObjectNew_(objKernel, null, null);		
	}
	public AbstractKernelUseObjectZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ{
		super(sFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectNew_(objKernel, null, null);
	}
	public AbstractKernelUseObjectZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ{
		super(saFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt		
		KernelUseObjectNew_(objKernel, null, null);
	}
	
	public AbstractKernelUseObjectZZZ(IKernelZZZ objKernel, HashMap<String,Boolean> hmFlag) throws ExceptionZZZ {
		super(hmFlag);//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectNew_(objKernel, null, null);				
	}
	
	
	/** Dieser Konstruktor kann fuer Objkete verwendet werden, die auf bestimmte Bereiche der Modulkonfiguration zurueckgreifen m�ssen UND bei denen diese Bereiche nicht dem eigenen Klassennamen entsprechen.
	* lindhaueradmin; 12.04.2007 15:46:51
	 * @param objKernel
	 * @param objKernelSection
	 * @throws ExceptionZZZ 
	 */
	public AbstractKernelUseObjectZZZ(IKernelZZZ objKernel, IKernelContextZZZ objKernelContext) throws ExceptionZZZ{
		super();//20210403: Das direkte Setzen der Flags wird nun in ObjectZZZ komplett erledigt
		KernelUseObjectNew_(objKernel, null, objKernelContext);						
	}
	
	public AbstractKernelUseObjectZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super();
		KernelUseObjectNew_(null, objKernelUsing, null);
	}
	
	public AbstractKernelUseObjectZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		KernelUseObjectNew_(null, objKernelUsing, null);
	}
	
	/**Wg. Interface IKernelUserZZZ 
	 * muessen entsprechende Klassen diesen Code redundant uebernehmen
	 * 
	 * 
	 * @param objKernel
	 * @param objKernelUsing
	 * @param objKernelContext
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:12:37
	 */
	private boolean KernelUseObjectNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing, IKernelContextZZZ objKernelContext) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			boolean btemp; String sLog;	
			
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
						while(itFlag.hasNext()) {
							String sKey = itFlag.next();
							 if(!StringZZZ.isEmpty(sKey)){
								 Boolean booValue = hmFlagZpassed.get(sKey);
								 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
								 if(btemp==false){						 
									 sLog = ReflectCodeZZZ.getPositionCurrent() +"The passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
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
	
	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#getKernelObject()
	 */
	public IKernelZZZ getKernelObject() throws ExceptionZZZ {
		return this.objKernel;
	}

	/* (non-Javadoc)
	 * @see zzzKernel.basic.KernelAssetKernelZZZ#setKernelObject(zzzKernel.custom.KernelZZZ)
	 */
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel=objKernel;
	}
	
	public String getModuleUsed(){
		if(this.getContextUsed() == null){
			return this.getClass().getName();
		}else{
			return this.getContextUsed().getModuleName();
		}
	}
	
	public String getProgramUsed(){
		if(this.getContextUsed() == null){
			return this.getClass().getName();
		}else{
			return this.getContextUsed().getProgramName();
		}
	}

	public void setContextUsed(IKernelContextZZZ objContext) {
		this.objContext = objContext;
	}

	public IKernelContextZZZ getContextUsed() {
		return this.objContext;
	}

	//aus IKernelLogObjectUserZZZ, analog zu KernelKernelZZZ
	@Override
	public KernelLogZZZ getLogObject() throws ExceptionZZZ {
		return this.objLog;
	}

	@Override
	public void setLogObject(KernelLogZZZ objLog) throws ExceptionZZZ {
		this.objLog = objLog;
	}	
	
	@Override
	public void printlnDate(String sLog) throws ExceptionZZZ {
		KernelLogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDate(this, sLog);
			System.out.println(sTemp);
		}else {
			objLog.writeLineDate(sLog);
		}		
	}
	
	
	@Override
	public void printlnDateWithPosition(String sLog) throws ExceptionZZZ {
		KernelLogZZZ objLog = this.getLogObject();
		if(objLog==null) {
			String sTemp = KernelLogZZZ.computeLineDateWithPosition(this, sLog);
			System.out.println(sTemp);
		}else {
			objLog.writeLineDate(sLog);
		}	
	}
}//end class

