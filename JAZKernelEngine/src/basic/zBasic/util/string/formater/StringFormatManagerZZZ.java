package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.MapUtilZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilTagByTypeZZZ;
import basic.zBasic.util.math.MathZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierManagerZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import basic.zBasic.xml.tagtype.TagByTypeFactoryZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;

public class StringFormatManagerZZZ extends AbstractStringFormatManagerZZZ implements IStringFormatManagerJustifiedZZZ{
	private static final long serialVersionUID = 5164996113432507434L;

	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static IStringFormatManagerZZZ objFormatManagerINSTANCE=null;

	//##########################################################
	//Trick, um Mehrfachinstanzen zu verhindern (optional)
	//Warum das funktioniert:
	//initialized ist static → nur einmal pro ClassLoader
	//Wird beim ersten Konstruktoraufruf gesetzt
	//Jeder weitere Versuch (Reflection!) schlägt fehl
    private static boolean INITIALIZED = false;
    
    //Reflection-Schutz ist eine Hürde, kein Sicherheitsmechanismus.
    //Denn:
    //Field f = AbstractService.class.getDeclaredField("initialized");
    //f.setAccessible(true);
    //f.set(null, false);
    //Danach kann man wieder instanziieren.
	//##########################################################
	    
	//als private deklariert, damit man es nicht so instanzieren kann, sondern die Methode .getInstance() verwenden muss
	private StringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	public static IStringFormatManagerJustifiedZZZ getInstance() throws ExceptionZZZ{
		//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
		//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
		synchronized(StringFormatManagerZZZ.class) {
			if(objFormatManagerINSTANCE == null) {
				if (INITIALIZED) {
		            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
		        }
				objFormatManagerINSTANCE = getNewInstance();
				INITIALIZED=true;
			}
		}
		return (IStringFormatManagerJustifiedZZZ) objFormatManagerINSTANCE;
	}
	
	public static StringFormatManagerZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objFormatManagerINSTANCE = new StringFormatManagerZZZ();
		return (StringFormatManagerZZZ)objFormatManagerINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objFormatManagerINSTANCE = null;
	}
	
	 // Trick, um Mehrfachinstanzen zu verhindern (optional)
    private static class HolderAlreadyInitializedHolder {
        private static final boolean INITIALIZED = true;
    }
    
	//################################################
	//### Methoden
   
    @Override
	public synchronized String compute(String... sLogs) throws ExceptionZZZ {
    	this.resetStringIndexRead();
		return this.computeJustified(sLogs);		
	}
    
    @Override
	public synchronized String compute(Object obj, String... sLogs) throws ExceptionZZZ {
    	this.resetStringIndexRead();
		return this.computeJustified(obj, sLogs);		
	}
    
    @Override
	public synchronized String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
    	this.resetStringIndexRead();
		return this.computeJustified(classObj, sLogs);		
	}
	
    
    //######################
    @Override
   	public synchronized String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
    	this.resetStringIndexRead();
   		return this.computeJustified(ienumFormatLogString);	
   	}
       		       
    @Override
	public synchronized String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
    	this.resetStringIndexRead();
		return this.computeJustified(ienumaFormatLogString, sLogs);		
	}

	@Override
	public synchronized String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified(obj, ienumaFormatLogString, sLogs);			
	}
	
	
	@Override
	public synchronized String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified(classObj, ienumaFormatLogString, sLogs);	
	}
	

	//######################
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, obj, sLogs);		
	}
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, ienumFormatLogString);
	}
	
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, ienumaFormatLogString, sLogs);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, obj, ienumFormatLogString);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, obj, ienumFormatLogString, sLogs);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, obj, ienumaFormatLogString, sLogs);
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, classObj, ienumFormatLogString, sLogs);				
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, classObj, ienumaFormatLogString, sLogs);		
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, classObj, ienumFormatLogString);	
	}
		
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		return this.computeJustified_(objFormater, classObj, sLogs);	
	}

	
	
	//#########################################################################################
	///##################################################################
  
	@Override
	public synchronized String computeJustified(String... sLogs) throws ExceptionZZZ {
		return computeJustified__(this.getClass(), sLogs);	
	}
	
	
	
	@Override
	public synchronized String computeJustified(Object objIn, String... sLogs) throws ExceptionZZZ {
		
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		return computeJustified__(objClass, sLogs);
	}
	

	@Override
	public synchronized String computeJustified(Class classObj, String... sLogs) throws ExceptionZZZ {				
		return computeJustified__(classObj, sLogs);
	}
	
	private String computeJustified__(Class classObj, String... sLogs) throws ExceptionZZZ {				
		String sReturn = null;
		main:{
			String stemp=null;
		
			//#######################
			//Hole das Format, aus einem Default.
			//Will man keinen Default-Format-Style haben, dann soll man halt die Methode mit Formatanweisung nutzen
			IStringFormaterZZZ objFormater = new StringFormaterZZZ();
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn = objFormater.getFormatPositionsMapped();
						 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
						
			sReturn = computeJustified__(classObj, ienumaFormatLogStringIn, sLogs);
		}//end main:
		return sReturn;	
	}
	
	
	
	//############################################################
	@Override
	public synchronized String computeJustified(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		return this.computeJustified__(ienumaFormatLogStringIn, sLogs);
	}	
	
	
	@Override
	public synchronized String computeJustified(Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		Object obj = null;
		if(objIn==null) {
			obj = this;
		}else {
			obj = objIn;
		}
		Class objClass = obj.getClass();
		
		return this.computeJustified__(objClass, ienumaFormatLogStringIn, sLogs);
	}
	
	@Override
	public synchronized String computeJustified(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		return computeJustified__(classObj, ienumaFormatLogStringIn, sLogs);
	}
	
	private String computeJustified__(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}

			//TODOGOON20260210: 0. Formatanweisung (auch ueber mehrere Spalten) normieren.
			//d.h.0.1. aufteilen auf ggfs. mehrere Zeilen (bisherige version, neue Version)
			//    0.2. fehlende Spalten etc. ergänzen, Reihenfolge angleichen
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString;
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringCurrent;
			
			boolean bUseIndividualFormat = this.getFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_INDIVIDUAL_FORMAT);	
			boolean bUseColumnRedundantFormat = this.getFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_REDUNDANT_FORMAT);
			boolean bUseColumnUnmergedFormat = this.getFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_UNMERGED_FORMAT);
			boolean bUseColumnMergedDouble = this.getFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_COLUMN_MERGED_DOUBLE);
			
			if(!bUseIndividualFormat) {
				ienumaFormatLogStringCurrent = this.getStringFormatArrayCurrent();
				if(!bUseColumnRedundantFormat && !bUseColumnUnmergedFormat) {
					//Der Normalfall, bUseColumnUnmergedMergedFormat
					//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Verwende bisherige Formatvorlage, ggfs. als Zusammenfassung der Spalten mit zuvor verwendeter.");

					//Hole die bisherige ArrayList der Separatoren, zwischend denen die Formataufweisungen aufgeteilt werden.
					ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator = this.getSeparatorArrayList();					
					
					if(!bUseColumnMergedDouble) {
					
						//Merge die Liste der Formatanweisungen und auch die Separatoren, Unique ueber alle Separatoren
						ienumaFormatLogString = StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyAcrosswise(listaSeparator, ienumaFormatLogStringCurrent, ienumaFormatLogStringIn);

					}else {
					
						//Merge die Liste der Formatanweisungen und auch die Separatoren, aber nur Unique pro Separator
						ienumaFormatLogString = StringFormatManagerUtilZZZ.mergeFormatArraysUniqueKeyEachwise(listaSeparator, ienumaFormatLogStringCurrent, ienumaFormatLogStringIn);
					
					}
					
					//Hole die neue Liste der Separatoren, inkl. neu hinzugekommenden, reingemergten Separatoren (sprich Spalten)
					ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparatorNew = StringFormatManagerUtilZZZ.filterSeparatorsAsArrayList(ienumaFormatLogStringIn);
					this.setSeparatorArrayList(listaSeparatorNew);
										
				}else {
					if(bUseColumnRedundantFormat) {
						//also nichts mergen und keine Redundanz entfernen, dann nur einfach joinen???????						
						ienumaFormatLogString = ArrayUtilZZZ.join(ienumaFormatLogStringCurrent, ienumaFormatLogStringIn);
					}else {
						//Zumindest doppelte Spalten entfernen
						//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Verwende biherige Formatvorlage, ggfs. als Zusammenfassung der Spalten mit zuvor verwendeter.");
						ienumaFormatLogString = StringFormatManagerUtilZZZ.adaptFormatArray(ienumaFormatLogStringCurrent, ienumaFormatLogStringIn);						
					}
				}
			}else {
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Verwende die individuelle Formatvorlage und keine Vermischung mit zuvor verwendeter.");
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}//end if getFlag			
			this.setStringFormatArrayCurrent(ienumaFormatLogString);
			//#######################
				
			
			
			//#######################
			//1. Ergänze ggfs. die Zeilen um weitere Zeilen fuer LogEintraege 			
			if(!this.getFlag(IStringFormatManagerEnabledZZZ.FLAGZ.USE_STATIC_FORMAT)) {
				ienumaFormatLogString = StringFormatManagerUtilZZZ.appendLines_StringType(ienumaFormatLogString, sLogs);
			}else {
				//System.out.println(ReflectCodeZZZ.getPositionCurrent() +": Verwende die Formatvorlage statisch und ergänze keine zusätzliche Kommentarzeilen.");
				ienumaFormatLogString = ienumaFormatLogStringIn;
			}//end if getFlag			
			//#######################
						
			//2. Teile Formatierung an den Zeilentrennern auf.
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitByValue(ienumaFormatLogString, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
						
			//3. Alle Zeilen unbündig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringTemp : listaEnumLine) { 
				listasJaggedTemp = super.computeJaggedArrayList_(classObj, ienumaFormatLogStringTemp, sLogs);
				listasJaggedReturn.addAll(listasJaggedTemp);
			}
			
			//4. Zeilen bündig machen			
			ArrayListZZZ<String> listasJustifiedReturn=listasJaggedReturn;
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringTemp : listaEnumLine) {
				listasJustifiedReturn = objJustifierManager.compute(listasJustifiedReturn, ienumaFormatLogStringTemp);
			}
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}

	
	
	//###################################################
	private String computeJustified__(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			String stemp;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			
			
						
			//#######################
						
			//1. Teile Formatierung an den Zeilentrennern auf.
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
						
			//3. Alle Zeilen unbündig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) { 
				listasJaggedTemp = super.computeJaggedArrayList_(ienumaFormatLogString, sLogs);
				listasJaggedReturn.addAll(listasJaggedTemp);
			}
			
			//4. Zeilen bündig machen			
			ArrayListZZZ<String> listasJustifiedReturn=listasJaggedReturn;
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJustifiedReturn = objJustifierManager.compute(listasJustifiedReturn, ienumaFormatLogString);
			}
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());			
		}//end main:
		return sReturn;
	}


	
	

	
	
	
	//####################################################
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {		
		return this.computeJustified__(objFormater, this.getClass(), ienumFormatLogStringIn);					
	}

	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn=null;
		main:{							
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = this.computeJustified__(objFormater, objClass, ienumFormatLogString);
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {
		return computeJustified__(objFormater, classObj, ienumFormatLogStringIn);
	}
	
	private String computeJustified__(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			String stemp;
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			
			//Nein hier wurde explizit nur 1 Formatanweisung übergeben. Dies dann zum Ausrechnen des Zeileninhalts nutzen
//			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
//				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
//				listasJaggedReturn.addAll(listasJaggedTemp);
//			}
						
			//Das ist hier nur 1 Zeile, darum keine Schleife
			listasJaggedTemp = super.computeJaggedArrayList_(objFormater, classObj, ienumFormatLogStringIn);
			listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());						
		}//end main:
		return sReturn;
	}


	//########################################################
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {
		String sReturn=null;
		main:{							
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = this.computeJustified__(objFormater, objClass, ienumFormatLogStringIn, sLogs);
		}//end main:
		return sReturn;		
	}

	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified__(objFormater, classObj, ienumFormatLogStringIn, sLogs);
	}
	
	private String computeJustified__(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn = objFormater.getFormatPositionsMapped();
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			
			//Nein hier wurde explizit nur 1 Formatanweisung übergeben. Dies dann zum Ausrechnen des Zeileninhalts nutzen
//			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
//				listasJaggedTemp = super.computeJaggedArrayList(classObj, ienumaFormatLogString, sLogs);
//				listasJaggedReturn.addAll(listasJaggedTemp);
//			}
						
			//Das ist hier nur 1 Formatanweisung, darum keine Schleife
			listasJaggedTemp = super.computeJaggedArrayList_(objFormater, classObj, ienumFormatLogStringIn, sLogs);
			listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());									
		}//end main:
		return sReturn;
	}
	
	//####################################################################
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified__(objFormater, this.getClass(), ienumaFormatLogStringIn, sLogs);
	}
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Object objIn, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = computeJustified__(objFormater, objClass, ienumaFormatLogStringIn, sLogs);	
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		return computeJustified__(objFormater, classObj, ienumaFormatLogStringIn, sLogs);
	}
	
	private String computeJustified__(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn, String... sLogs) throws ExceptionZZZ {		
		String sReturn = null;
		main:{
			String stemp = null;
			
			//Will man einen Default-Format-Style haben, dann soll man halt die Methode ohne diese Formatanweisung nutzen 
			if(ienumaFormatLogStringIn==null) {
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(ArrayUtilZZZ.isEmpty(ienumaFormatLogStringIn)) {				
				ExceptionZZZ ez = new ExceptionZZZ("IEnumSetMappedLogStringFormatZZZ[]", iERROR_PARAMETER_EMPTY, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;	
			}
			
			//#######################						
			//1. Teile Formatierung an den Zeilentrennern auf.
			List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
			
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
						
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJaggedTemp = super.computeJaggedArrayList_(classObj, ienumaFormatLogString, sLogs);
				listasJaggedReturn.addAll(listasJaggedTemp);
			}
			
			//4. Zeilen buendig machen
			ArrayListZZZ<String> listasJustifiedReturn=listasJaggedReturn;
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
				listasJustifiedReturn = objJustifierManager.compute(listasJustifiedReturn, ienumaFormatLogString);				
			}
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());						
		}//end main:
		return sReturn;
	}
	
	//##################################################################
	//### Hole ohne Angabe der Formatanweisungen als Array, diese aus dem objFormater
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Object objIn, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			Object obj = null;
			if(objIn==null) {
				obj = this;
			}else {
				obj = objIn;
			}
			Class objClass = obj.getClass();
			
			sReturn = computeJustified__(objFormater, objClass, sLogs);	
		}//end main:
		return sReturn;
	}
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		return computeJustified__(objFormater, classObj, sLogs);
	}
	
	private String computeJustified__(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			//1. Teile Formatierung an den Zeilentrennern auf.
			//Nein, entfaellt, da nur 1 Formatanweisung
			//List<IEnumSetMappedStringFormatZZZ[]> listaEnumLine = ArrayUtilZZZ.splitArrayByValue(ienumaFormatLogStringIn, (IEnumSetMappedStringFormatZZZ)IStringFormatZZZ.LOGSTRINGFORMAT.CONTROL_LINENEXT_, IEnumSetMappedStringFormatZZZ.class);
	
			//TODOGOON20260210: 2. Formatanweisung jeder Zeile normieren. (d.h. fehlende Spalten etc. ergänzen, Reihenfolge angleichen)
			//Ohne Formatanweisungen muss das wohl in der im folgenden aufgerufenen super - Methode passieren
			
			//3. Alle Zeilen unbuendig holen
			ArrayListZZZ<String>listasJaggedTemp;
			ArrayListZZZ<String>listasJaggedReturn=new ArrayListZZZ<String>();

			
			listasJaggedTemp = super.computeJaggedArrayList_(objFormater, classObj, sLogs);
			if(listasJaggedTemp!=null) listasJaggedReturn.addAll(listasJaggedTemp);
			
			//4. Zeilen buendig machen
			//Das ist hier zwar nur 1 Zeile, wir muessen aber alle Justifier ansetzen, um an die passende Stelle zu ruecken
			ArrayListZZZ<String>listasJustifiedTemp;
			ArrayListZZZ<String> listasJustifiedReturn=new ArrayListZZZ<String>();
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
					
			//Wir brauchen blos keine Formatanweisungen für die Separatoren zu uebergeben.
			listasJustifiedTemp = objJustifierManager.compute(listasJaggedReturn);
			listasJustifiedReturn.addAll(listasJustifiedTemp);
			
			
			ArrayListZZZ<String> listasReturn = listasJustifiedReturn; 
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}

	//#######################################################################################################
	//##################################################################################
	//### HashMap mit den "Daten"
	//TODOGOON20260127: HashMap übergeben, aber: Das müsste dann auch irgendwie umstrukturiert werden hin zur ArrayList-Methodik
	
	//###################################################################
	@Override
	public synchronized String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged_(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}

	@Override
	public synchronized String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJustified(obj, hm);
	}

	@Override
	public synchronized String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged_(classObj, hm);	
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return this.computeJustified_(objFormater, hm);	
	}
	
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified_(objFormater, obj, hm);
		return sReturn;
	}
	
	@Override
	public synchronized String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = this.computeJustified_(objFormater, classObj, hm);
		return sReturn;
	}

	
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged_(objFormater, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
				
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}

	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged_(objFormater, obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	
	@Override
	public synchronized String computeJustified_(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {		
		String sReturn = super.computeJagged_(objFormater, classObj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}


	@Override
	public synchronized String computeJustified(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged_(hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
	
	@Override
	public synchronized String computeJustified(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = super.computeJagged_(obj, hm);
		sReturn = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sReturn);	
		
		IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();		
		return objJustifierManager.compute(sReturn, hm);
	}
}
