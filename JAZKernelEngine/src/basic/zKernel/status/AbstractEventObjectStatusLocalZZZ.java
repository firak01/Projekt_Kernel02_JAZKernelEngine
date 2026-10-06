package basic.zKernel.status;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ILogProtocolPositionZZZ;
import basic.zBasic.ILogProtocolZZZ;
import basic.zBasic.ObjectZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractEnum.IEnumSetMappedStatusLocalZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormaterZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import custom.zKernel.Log;

/** 
 * Merke: Der gleiche "Design Pattern" wird auch im UI - Bereich fuer Komponenten verwendet ( package basic.zKernelUI.component.model; )  
 *        Dann erweitert die Event-Klasse aber EventObjekt.
 *  
 *  Merke2: Auch wenn hier nur normale Objekte verwendet weden, kann man in der FLAG-Verarbeitung bestimmt EventObject verwenden.
 *  
 * @author Fritz Lindhauer, 02.04.2023, 12:00:33  
 */
public abstract class AbstractEventObjectStatusLocalZZZ extends EventObject implements IEventObjectStatusLocalZZZ, Comparable<IEventObjectStatusLocalZZZ>{
	//Merke: Das Interface comparable kann nicht mehrmals eingebunden werden. Daher in der Ausgangsklasse comparable nutzen und dort die Methoden erstellen.
	protected IEnumSetMappedStatusLocalZZZ objStatusEnum=null;
	protected String sStatusMessage=null;
	protected boolean bStatusValue;
	
	/** In dem Konstruktor wird neben der ID dieses Events auch der identifizierende Name der neu gewaehlten Komponente �bergeben.
	 * @param source
	 * @param iID
	 * @param sComponentItemText, z.B. fuer einen DirectoryJTree ist es der Pfad, fuer eine JCombobox der Name des ausgew�hlten Items 
	 */
	public AbstractEventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue)  throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, bStatusValue, null);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, String sEnumName, boolean bStatusValue, String sStatusMessage)  throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, sEnumName, null, bStatusValue, sStatusMessage);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, bStatusValue, sStatusMessage);
	}
	
	public AbstractEventObjectStatusLocalZZZ(Object source, Enum objStatusEnum,  boolean bStatusValue) throws ExceptionZZZ {
		super(source);		
		AbstractEventObjectStatusLocalNew_(source, null, objStatusEnum, bStatusValue, null);
	}
	
	private boolean AbstractEventObjectStatusLocalNew_(Object source, String sEnumName, Enum objStatusEnum, boolean bStatusValue, String sStatusMessage) throws ExceptionZZZ {
		if(objStatusEnum==null) {
			if(StringZZZ.isEmpty(sEnumName)) {
				ExceptionZZZ ez = new ExceptionZZZ( "StatusString", iERROR_PARAMETER_MISSING, ReflectCodeZZZ.getMethodCurrentName(), ""); 
				throw ez;
			}else {
				String sLog;
				
				//Ermittle das Enum aus dem Namen
				IEnumSetMappedStatusLocalZZZ objEnumMapped = StatusLocalAvailableHelperZZZ.searchEnumMappedByName(source, sEnumName, true);
				if(objEnumMapped==null) {
					
					//20240319: Wenn es ein Monitor-Objekt ist, dann kann der Status auch aus der cascadedHashMap stammen.
					if(source instanceof IStatusLocalMapForMonitoringStatusLocalUserZZZ) {
						sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum not found in normal Status: '" + sEnumName + "', this is a monitor Objekt therefore a search in the cascading Hashmap may will succeed. ";
						Log.protocol(this, sLog);
						
						IStatusLocalMapForMonitoringStatusLocalUserZZZ sourceAsMonitor = (IStatusLocalMapForMonitoringStatusLocalUserZZZ) source;
						HashMap<IEnumSetMappedStatusLocalZZZ,IEnumSetMappedStatusLocalZZZ> hmFromMonitor = sourceAsMonitor.getHashMapEnumSetForCascadingStatusLocal();
						
						Set<IEnumSetMappedStatusLocalZZZ> setKeyFromMonitor = hmFromMonitor.keySet();
						Iterator<IEnumSetMappedStatusLocalZZZ> itKeyFromMonitor = setKeyFromMonitor.iterator();																
						while(itKeyFromMonitor.hasNext()) {
							IEnumSetMappedStatusLocalZZZ objKey = (IEnumSetMappedStatusLocalZZZ) itKeyFromMonitor.next();
							if(objKey.getName().equalsIgnoreCase(sEnumName)) {
								objEnumMapped = objKey;
								sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum '" + sEnumName + "' found in the cascading Hashmap as Key. ";
								Log.protocol(this, sLog);
								break;
							}
							
							IEnumSetMappedStatusLocalZZZ objValue = (IEnumSetMappedStatusLocalZZZ) hmFromMonitor.get(objKey);
							if(objValue.getName().equalsIgnoreCase(sEnumName)) {
								objEnumMapped = objValue;
								sLog = ReflectCodeZZZ.getPositionCurrent() + "EventObject ("+this.getClass().getName()+") for SourceClass ("+source.getClass().getName() +"). Enum '" + sEnumName + "'  found in the cascading Hashmap as Value. ";
								Log.protocol(this, sLog);
								break;
							}
						}
					}
										
					if(objEnumMapped==null) {
						ExceptionZZZ ez = new ExceptionZZZ( "Status not available for Source-Object ("+source.getClass().getName()+ ") - StatusString '"+ sEnumName + "' (Object-Class: '" + this.getClass() +"')", iERROR_PARAMETER_VALUE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
						throw ez;
					}
				}
				this.setStatusLocal(objEnumMapped);
			}
		}else {
			this.setStatusLocal(objStatusEnum);
		}
		
		this.setStatusMessage(sStatusMessage);		
		this.setStatusValue(bStatusValue);
		return true;
	}
	
	
	@Override
	public String getStatusText(){
		if(this.getStatusLocal()==null) {
			return this.getStatusMessage();
		}else {
			return this.getStatusLocal().getName();
		}
	}

	@Override
	public String getStatusAbbreviation(){
		if(this.getStatusLocal()==null) {
			return this.getStatusText();
		}else {
			return this.getStatusLocal().getAbbreviation();
		}
	}
	
	@Override
	public String getStatusMessage(){
		if(this.sStatusMessage==null) { //Wenn NULL als Meldung eingeht, dann gib den Standardwert zurueck.
			if(this.getStatusLocal()==null) {
				return null;
			}else {
				return this.getStatusLocal().getStatusMessage();
			}
		}else {
			return this.sStatusMessage; //Merke: Ein Leerstring waere damit ein erlaubter Meldungstext
		}		
	}
	
	@Override 
	public void setStatusMessage(String sStatusMessage) {
		this.sStatusMessage = sStatusMessage;
	}
	
	@Override
	public boolean getStatusValue() {
		return this.bStatusValue;
	}
	
	@Override
	public void setStatusValue(boolean bValue) {
		this.bStatusValue = bValue;
	}

	@Override
	public Enum getStatusEnum() {
		return (Enum) this.objStatusEnum;
	}
	
	@Override
	public IEnumSetMappedStatusLocalZZZ getStatusLocal() {
		return this.objStatusEnum;
	}
	
	@Override
	public void setStatusLocal(Enum objEnum) {
		this.objStatusEnum = (IEnumSetMappedStatusLocalZZZ) objEnum;
	}
	
	@Override
	public void setStatusLocal(IEnumSetMappedStatusLocalZZZ objEnumSet) {
		this.objStatusEnum = objEnumSet;
	}
	
	
	//### Aus dem Interface Comparable	
	@Override
	public int compareTo(IEventObjectStatusLocalZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			String sTextToCompare = o.getStatusText();
			boolean bValueToCompare = o.getStatusValue();
			
			String sText = this.getStatusText();
			boolean bValue = this.getStatusValue();
			
			if(sTextToCompare.equals(sText) && bValueToCompare==bValue) iReturn = 1;
			
			
		}
		return iReturn;
	}

	/**
   * Define equality of state.
   */
   @Override 
   public boolean equals(Object aThat) {
     if (this == aThat) return true;
     if (!(aThat instanceof EventObjectStatusLocalZZZ)) return false;
     EventObjectStatusLocalZZZ that = (EventObjectStatusLocalZZZ)aThat;
     
     String sTextToCompare = that.getStatusText();
	 boolean bValueToCompare = that.getStatusValue();
		
		String sText = this.getStatusText();
		boolean bValue = this.getStatusValue();
     
		if(sTextToCompare.equals(sText) && bValueToCompare==bValue) return true;
		
     return false;     
   }

   /**
   * A class that overrides equals must also override hashCode.
   */
   @Override 
   public int hashCode() {
	   return this.getStatusText().hashCode();
   }
   
   
   //#################################################################
   //### aus IObjectZZZ
   //Meine Variante Objekte zu clonen
	@Override
	public Object clonez() throws ExceptionZZZ {
		try {
			return this.clone();
		}catch(CloneNotSupportedException e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
				
		}
	}
	
	//#################################################################
	//### aus ILogZZZ
//	@Override
//	public synchronized void printlnDate(String sLog) throws ExceptionZZZ {
//		ObjectZZZ.printlnDate(this, sLog);
//	}
//	
//	@Override
//	public void printlnDate(String... sLogs) throws ExceptionZZZ {
//		ObjectZZZ.printlnDate(this, sLogs);
//	}
//	
//	@Override
//	public synchronized void printlnDateWithPosition(String sLog) throws ExceptionZZZ {
//		ObjectZZZ.printlnDateWithPosition(this, sLog);
//	}
//	
//	@Override
//	public synchronized void printlnDateWithPosition(String... sLogs) throws ExceptionZZZ {
//		ObjectZZZ.printlnDateWithPosition(this, sLogs);
//	}
//	
//	
//			
//	//++++++++++++++++++++++++++++++++++++++++++++++++
//	
//	@Override
//	public synchronized void protocol(String... sLogs) throws ExceptionZZZ{
//		this.protocol(this, sLogs); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
//	}
//	
//	@Override
//	public synchronized void protocol(String sLog) throws ExceptionZZZ{
//		this.protocol(this, sLog); //Merke: In der aehnlichen Methode von KernelLogZZZ (also static) "null" statt this
//	}
//	
//	@Override
//	public synchronized void protocol(Object obj, String sLog) throws ExceptionZZZ{
//		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLog);						
//		System.out.println(sLogUsed);
//	}
//	
//	@Override
//	public synchronized void protocol(Object obj, String... sLogs) throws ExceptionZZZ{
//		String sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, sLogs);						
//		System.out.println(sLogUsed);
//	}
//
//	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	
//	@Override
//	public synchronized void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		this.protocol(this, ienumMappedLogString, sLog); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
//	}
//	
//	@Override
//	public void protocol(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		this.protocol(this, ienumaMappedLogString, sLogs);
//	}
//	
//	@Override
//	public synchronized void protocol(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
//		this.protocol(this, ienumaMappedLogString, sLogs); //Merke: In der aehnlichen Methode von KerneleLosgZZZ (also static) "null" statt this
//	}
//		
//	@Override
//	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		this.protocol(ienumaMappedLogString, sLogs);
//	}
//	
//	@Override
//	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
//		main:{
//			if(ArrayUtilZZZ.isNull(sLogs)) break main;
//			if(ArrayUtilZZZ.isNull(ienumaMappedLogString)){
//				this.protocol(sLogs);
//				break main;
//			}
//			
//			int iIndex=0;
//			if(obj==null) {			
//				for(String sLog : sLogs) {
//					if(ienumaMappedLogString.length>iIndex) {
//						this.protocol(ienumaMappedLogString[iIndex],sLog);
//						iIndex++;
//					}else {
//						this.protocol(sLog);
//					}
//				}
//			}else {
//				for(String sLog : sLogs) {
//					if(ienumaMappedLogString.length>iIndex) {
//						this.protocol(obj, ienumaMappedLogString[iIndex],sLog);
//						iIndex++;
//					}else {
//						this.protocol(sLog);
//					}
//				}			
//			}
//		}//end main:
//	}
//	
//	@Override
//	public synchronized void protocol(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		String sLogUsed;
//		if(obj==null) {
//			sLogUsed = StringFormatManagerZZZ.getInstance().compute(sLog, ienumMappedLogString);
//		}else {
//			sLogUsed = StringFormatManagerZZZ.getInstance().compute(obj, ienumMappedLogString, sLog);
//		}
//		System.out.println(sLogUsed);
//	}
//	
//	//############ ALLE METHODEN NUN AUCH NOCH MIT POSITIONSANGABE
//	
//	@Override
//	public synchronized void protocolWithPosition(String... sLogs) throws ExceptionZZZ{
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		this.protocol(this, saLog);
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(String sLog) throws ExceptionZZZ{
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
//		this.protocol(this, saLog);
//	}
//		
//	@Override
//	public synchronized void protocolWithPosition(Object obj, String... sLogs) throws ExceptionZZZ{
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		this.protocol(obj, saLog); 
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(Object obj, String sLog) throws ExceptionZZZ{
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
//		this.protocol(obj, saLog); 
//	}
//	
//	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//		
//	@Override
//	public void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		this.protocol(this, ienumaMappedLogString, saLog);
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		this.protocol(this, ienumaMappedLogString, saLog); 
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
//		
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		this.protocol(this, ienumaMappedLogString, saLog); 
//	}
//	
//	@Override
//	public void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String... sLogs) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		
//		IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString = new IEnumSetMappedStringFormatZZZ[1];
//		ienumaMappedLogString[0] = ienumMappedLogString;
//		
//		this.protocol(this, ienumMappedLogString, saLog);
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaMappedLogString, String... sLogs) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLogs, sPositionCalling);
//		this.protocol(this, ienumaMappedLogString, saLog); 
//	}
//	
//	@Override
//	public synchronized void protocolWithPosition(Object obj, IEnumSetMappedStringFormatZZZ ienumMappedLogString, String sLog) throws ExceptionZZZ {
//		String sPositionCalling = ReflectCodeZZZ.getPositionCalling();
//		String[] saLog = StringArrayZZZ.prepend(sLog, sPositionCalling);
//		this.protocol(this, ienumMappedLogString, saLog); 
//	}
}

