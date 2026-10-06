package basic.zKernel.flag.event;


import java.io.Serializable;
import java.util.ArrayList;

import basic.zKernel.IKernelZZZ;
import basic.zKernel.status.IEventObjectStatusLocalZZZ;
import basic.zKernel.status.IListenerObjectStatusLocalZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zKernel.AbstractKernelUseObjectZZZ;

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
public class KernelSenderObjectFlagZsetZZZ extends AbstractObjectWithExceptionZZZ implements ISenderObjectFlagZsetZZZ, Serializable{
	private static final long serialVersionUID = 8999783685575147532L;
	private IEventObjectFlagZsetZZZ eventPrevious=null;
	
	public KernelSenderObjectFlagZsetZZZ() throws ExceptionZZZ{		
	}
	
	//Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden
	//Spezieller unique Datentyp, damit ein Objekt nicht mehrfach registriert wird. (Z.B. Monitor-Objekte, die sich im Konstruktor am Broker selbst registrieren, ... und im Elternojekt, ... und im weiteren Elternobjekt, etc.
	//wichtig: Sie muss private sein und kann nicht im Interace global definiert werden, weil es sonst nicht moeglich ist
	 //             mehrere Events, an verschiedenen Komponenten, unabhaengig voneinander zu verwalten.
	private ArrayListUniqueZZZ<IListenerObjectFlagZsetZZZ> listaLISTENER_REGISTERED = new ArrayListUniqueZZZ<IListenerObjectFlagZsetZZZ>();  
 
	@Override                                                                                         
	public final void fireEvent(IEventObjectFlagZsetZZZ event){	
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
			
			String sLog;
			try {
				
			
			//Dafür sorgen, dass der Event nur 1x geworfen wird, wenn der vorherige Event der gleich war.
			IEventObjectFlagZsetZZZ eventPrevious = this.getEventPrevious();
			if(eventPrevious!=null) {
				if(eventPrevious.equals(event))break main;
			}
			this.setEventPrevious(event);
			
			for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
				IListenerObjectFlagZsetZZZ l = (IListenerObjectFlagZsetZZZ) this.getListenerRegisteredAll().get(i);
				if(l instanceof IListenerObjectFlagZsetZZZ) {
					IEventObjectFlagZsetZZZ eventUsed = (IEventObjectFlagZsetZZZ) event;
					//sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object ("+ this.getClass().getName() +") called: listener.flagChanged(event) # " + i + ". IListenerObjectFlagZsetZZZ ("+l.getClass().getName()+")";
					//this.logProtocolString(sLog);
					IListenerObjectFlagZsetZZZ lused = (IListenerObjectFlagZsetZZZ) l;
					
					try {
						boolean bFlagChanged = lused.flagChanged(eventUsed);
						if(bFlagChanged) {
							//sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object ("+ this.getClass().getName() + ") # " + i + ". IListenerObjectFlagZsetZZZ " + this.getClass().getName() + " hat Flag '" + event.getFlagText() + "' gesetzt.";
							//this.logProtocolString(sLog);
						}
					} catch (ExceptionZZZ ez) {
						//Z.B. falls es das Flag hier nicht gibt, wird die ggfs. die Exception weitergeworfen.
						sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object ("+ this.getClass().getName() + ")# IListenerObjectFlagZsetZZZ by " + this.getClass().getName() + " throws Exception " + ez.getDetailAllLast(); 
						this.protocol(sLog);
					}
				}else {					
					sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object ("+ this.getClass().getName() +") # " + i + ". IListenerObjectStatusLocalSetZZZ ("+l.getClass().getName()+") - instanceof type is not used yet";
					this.protocol(sLog);
				}
			}
			
			} catch (ExceptionZZZ e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}//end main:
	}
	
	@Override
	public IEventObjectFlagZsetZZZ getEventPrevious() {
		return this.eventPrevious;
	}

	@Override
	public void setEventPrevious(IEventObjectFlagZsetZZZ event) {
		this.eventPrevious = event;
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#removeSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	@Override
	public final void removeListenerObjectFlagZset(IListenerObjectFlagZsetZZZ l){
		this.getListenerRegisteredAll().remove(l);
	}
	
	/* (non-Javadoc)
	 * @see use.via.client.module.export.ISenderEventComponentReset#addSelectionResetListener(basic.zKernelUI.component.model.ISelectionResetListener)
	 */
	@Override
	public final void addListenerObjectFlagZset(IListenerObjectFlagZsetZZZ l){
		this.getListenerRegisteredAll().add(l);
	}
	
	@Override
	public final ArrayListUniqueZZZ<IListenerObjectFlagZsetZZZ> getListenerRegisteredAll(){
		return listaLISTENER_REGISTERED;
	}
}

