package basic.zKernel.status;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelContextUserZZZ;
import basic.zKernel.IKernelContextZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.KernelLogZZZ;

/** Diese Klasse implementiert alles, was benoetigt wird, damit die eigenen Events "Flag hat sich geaendert" abgefeuert werden kann
 *  und auch von den Objekten, die hier registriert sind empfangen wird. Damit fungieren Objekte dieser Klasse als "EventBroker".
 *   
 *   Wichtig: Diese Klasse darf nicht final sein, damit sie von anderen Klassen geerbt werden kann.
 *               Die Methoden dieser Klasse sind allerdings final.
 *               
 *   Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )            
 * @author lindhaueradmin
 *
 */
public abstract class AbstracKernelSenderObjectWithFlagStatusLocalBasicZZZ extends AbstractSenderObjectWithFlagStatusLocalBasicZZZ implements IKernelUserZZZ, IKernelContextUserZZZ {
	private static final long serialVersionUID = 8999783685575147532L;
	protected volatile IKernelZZZ objKernel=null;
	protected volatile KernelLogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile IKernelContextZZZ objContext = null; //die Werte des aufrufenden Programms (bzw. sein Klassenname, etc.), Kann anders als beim Kernel selbst sein.
	
	public AbstracKernelSenderObjectWithFlagStatusLocalBasicZZZ() throws ExceptionZZZ{
		super();
		KernelUseObjectNew_(null,null,null);
	}
																							  //wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht m�glich ist 
	@Override                                                                                     //             mehrere Events, an verschiedenen Komponenten, unabhaengig voneinander zu verwalten.	
	public void fireEvent(IEventObjectStatusBasicZZZ event){
		/* Die Abfrage nach getSource() funktioniert so mit dem Interface noch nicht....
		 * Auszug aus: KernelSenderComponentSelectionResetZZZ.fireEvent(....)
		if(event.getSource() instanceof ISenderSelectionResetZZZ){
			ISenderSelectionResetZZZ sender = (ISenderSelectionResetZZZ) event.getSource();
			for(int i = 0 ; i < sender.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) sender.getListenerRegisteredAll().get(i);
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + event.getSource().getClass().getName() + " fired: " + i);
				l.doReset(event);
			}
		}else{
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerSelectionResetZZZ l = (IListenerSelectionResetZZZ) this.getListenerRegisteredAll().get(i);				
				System.out.println(ReflectCodeZZZ.getMethodCurrentName() + "# EventComponentSelectionResetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
				l.doReset(event);
			}
		}
		*/
		
		main:{
			if(event==null)break main;
			
			try {
				for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
					//Mit instanceof den Typ abfragen und dahingehend die passende Unterabfrage des Events aufrufen.
					//Merke: Ohne das instanceof entstehen typcast-mapping-Fehler.
					IListenerObjectStatusBasicZZZ l = this.getListenerRegisteredAll().get(i);
					if(l instanceof IListenerObjectStatusLocalZZZ) {
						IEventObjectStatusLocalZZZ eventUsed = (IEventObjectStatusLocalZZZ) event;
						System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# IListenerObjectStatusLocalSetZZZ by " + this.getClass().getName() + " - object (d.h. this - object) fired: " + i);
						IListenerObjectStatusLocalZZZ lused = (IListenerObjectStatusLocalZZZ) l;
						lused.reactOnStatusLocalEvent(eventUsed);
					}else {					
						String sLog = ReflectCodeZZZ.getPositionCurrent() + "# type is not used yet: '" + l.getClass().getName() + "'";
						this.protocol(sLog);
					}
				}
			} catch (ExceptionZZZ ez) {
				try {
					System.out.println(ReflectCodeZZZ.getPositionCurrent() + "# throws Exception " + ez.getDetailAllLast() );
				} catch (ExceptionZZZ ez2) {				
					ez2.printStackTrace();
				}
			}
			
		}//end main:
	}	
	
	private boolean KernelUseObjectNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing, IKernelContextZZZ objKernelContext) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{						
			boolean btemp; String sLog;	
								
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
						while(itFlag.hasNext()) {
							String sKey = itFlag.next();
							 if(!StringZZZ.isEmpty(sKey)){
								 Boolean booValue = hmFlagZpassed.get(sKey);
								 btemp = setFlag(sKey, booValue.booleanValue());//setzen der "auf Verdacht" indirekt übergebenen Flags
								 if(btemp==false){						 
									 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
									 this.printlnDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
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
			String sTemp = AbstractKernelLogZZZ.computeLineDate(sLog);
			System.out.println(sTemp);
		}else {
			objLog.writeLineDate(sLog);
		}		
	}
}

