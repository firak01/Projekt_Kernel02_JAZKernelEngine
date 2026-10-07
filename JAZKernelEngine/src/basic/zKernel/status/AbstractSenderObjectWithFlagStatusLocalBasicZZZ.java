package basic.zKernel.status;


import java.io.Serializable;
import java.util.ArrayList;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import custom.zKernel.Log;

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
public abstract class AbstractSenderObjectWithFlagStatusLocalBasicZZZ extends AbstractObjectWithFlagZZZ implements  ISenderObjectStatusLocalZZZ, Serializable{
	private static final long serialVersionUID = 8999783685575147532L;
	
	//Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden
	//Spezieller unique Datentyp, damit ein Objekt nicht mehrfach registriert wird. (Z.B. Monitor-Objekte, die sich im Konstruktor am Broker selbst registrieren, ... und im Elternojekt, ... und im weiteren Elternobjekt, etc.		
	protected ArrayListUniqueZZZ<IListenerObjectStatusBasicZZZ> listaLISTENER_REGISTERED = new ArrayListUniqueZZZ<IListenerObjectStatusBasicZZZ>();  //Das ist die Arrayliste, in welche  die registrierten Komponenten eingetragen werden
	protected IEventObjectStatusBasicZZZ eventPrevious=null;

	public AbstractSenderObjectWithFlagStatusLocalBasicZZZ() throws ExceptionZZZ{
		super();
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
			
			String sLog;
			try {
				//Dafür sorgen, dass der Event nur 1x geworfen wird, wenn der vorherige Event der gleich war.
				IEventObjectFlagZsetZZZ eventPrevious = (IEventObjectFlagZsetZZZ) this.getEventPrevious();
				if(eventPrevious!=null) {
					if(eventPrevious.equals(event))break main;
				}
				this.setEventPrevious(event);
				
				
				for(int i = 0 ; i < this.getListenerRegisteredAll().size(); i++){
					//Mit instanceof den Typ abfragen und dahingehend die passende Unterabfrage des Events aufrufen.
					//Merke: Ohne das instanceof entstehen typcast-mapping-Fehler.
					IListenerObjectStatusBasicZZZ l = this.getListenerRegisteredAll().get(i);
					if(l instanceof IListenerObjectStatusLocalZZZ) {
						IEventObjectStatusLocalZZZ eventUsed = (IEventObjectStatusLocalZZZ) event;
						sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object '"+ this.getClass().getName() +"' called: listener.reactOnStatusLocalEvent(event) # " + i + ". IListenerObjectStatusLocalSetZZZ ("+l.getClass().getName()+")";
						Log.protocol(this, sLog);
						IListenerObjectStatusLocalZZZ lused = (IListenerObjectStatusLocalZZZ) l;
						lused.reactOnStatusLocalEvent(eventUsed);
					}else {					
						sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object '"+ this.getClass().getName() +"' instanceof type is not used yet # " + i + ". IListenerObjectStatusLocalSetZZZ ("+l.getClass().getName()+")";
						Log.protocol(this, sLog);
					}
				}
			} catch (ExceptionZZZ ez) {
				try {
					sLog = ReflectCodeZZZ.getPositionCurrent() + "Sender Broker Object '"+ this.getClass().getName() +"' # throws Exception " + ez.getDetailAllLast();
					Log.protocol(this, sLog);
				} catch (ExceptionZZZ ez2) {				
					ez2.printStackTrace();
				}
			}
			
		}//end main:
	}
		
	@Override
	public IEventObjectStatusBasicZZZ getEventPrevious() {
		return this.eventPrevious;
	}
	
	@Override
	public void setEventPrevious(IEventObjectStatusBasicZZZ event) {
		this.eventPrevious = event;
	}
	
	@Override	
	public void removeListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getListenerRegisteredAll().remove(objEventListener);
	}
	
	@Override	
	public void addListenerObject(IListenerObjectStatusBasicZZZ objEventListener) throws ExceptionZZZ {
		this.getListenerRegisteredAll().add(objEventListener);
	}
	
	@Override
	public ArrayListUniqueZZZ<IListenerObjectStatusBasicZZZ> getListenerRegisteredAll() throws ExceptionZZZ {
		return listaLISTENER_REGISTERED;
	}
}

