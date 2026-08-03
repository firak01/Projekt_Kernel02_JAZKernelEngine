package basic.zBasic.util.string.formater;

import java.util.LinkedHashMap;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierManagerZZZ;
import basic.zBasic.util.string.justifier.IStringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator01StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator02StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator03StringJustifierZZZ;
import basic.zBasic.util.string.justifier.Separator04StringJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorFilePositionJustifierZZZ;
import basic.zBasic.util.string.justifier.SeparatorMessageStringJustifierZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerUtilZZZ;
import basic.zBasic.util.string.justifier.StringJustifierManagerZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zKernel.flag.event.IEventObjectFlagZsetZZZ;
import basic.zKernel.flag.event.IListenerObjectFlagZsetZZZ;
import custom.zKernel.LogZZZ;

/** Abstrakte Klasse der FormatManager.
 *  
 *  Ein FormatManager ruft den Formater auf, der aus einer Formatierungsanweisung einen String baut.
 *  Dabei enthält die Formatierungsanweisung neben ggfs. konkreten Texten
 *  auch dynamische Bestandteil, wie z.B. das Datum 
 *  oder auch Bestandteile aus den Reflections, wie z.B. die aktuelle Positon im Java Code.
 *  
 *   Zudem werden Trenner definiert, die es dann ermöglichen diese Anageben im Ergebnis buendig zu sehen.
 *   
 *   
 *  Der FormatManager ruft nach dem erstellen des "unbuendigen / jagged" Textes 
 *  ggfs. einen JustifierManager auf, der eben dieses "Buendig Machen" durchfuehrt.
 *  
 *  Merke: Der FormatManager fuer XML strukturierten Text verzichtet auf das "Buendig Machen",
 *         da die Tag-Struktur doch sehr breite Spalten erzeugt.
 *  
 * @author Fritz Lindhauer, 08.02.2026, 09:03:41
 * 
 */
public abstract class AbstractStringFormatManagerZZZ extends AbstractObjectWithFlagZZZ implements IStringFormatManagerZZZ, IStringFormatManagerJaggedZZZ{
	private static final long serialVersionUID = 432992680546312138L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static ILogStringFormatManagerZZZ objLogStringManagerINSTANCE; //muss als Singleton static sein
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
	
	
	// --- Globale Objekte ---	
	//Die Liste der Spaltenseparatoren, das aktuelle Format kann sich ja ggfs. aendern (je nachdem welche Log-Funktion verwendet wird),
	//die Reihenfolge der Spalten (markiert durch die Separatoren) sollte nicht veraendert werden. Sonst bekommt man in den buendig gemachten Zeilen grosse Luecken.
	//Neue Separatoren werden in diese Liste reingemischt (ArrayListUtilZZZ.merge...)
	protected volatile ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator=null;
	
	//Das zuletzt verwendete Format, ggfs. durch weitere Anwendung mit anderen Formaten erweitert
	protected volatile IEnumSetMappedStringFormatZZZ[] ienumaFormatStringCurrent=null; 
	
	//Der LogString-Index - also von den reinen String ( nicht ggfs. hinzugefuegte XML Strings wie von ReflectCodeZZZ.getPositionCurrent() )
	//Hier als Array aller schon benutzter "einfacher" Strings. Das wird gemacht, damit die XML Strings auch weiterhin bei jeder Operation beruecksichtigt werden können.
	protected volatile ArrayListUniqueZZZ<Integer>listaintStringIndexRead=null;
	
	

	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringFormatManagerZZZ() throws ExceptionZZZ{
		super();
	}

	
	
	//##########################################################	
	//### GETTER / SETTER
	
	//##########################################################################
	//### aus IStringFormatUserZZZ
	@Override 
	public boolean resetSeparatorArrayList() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getSeparatorArrayList().size()>=1) {
				bReturn = true;
			}
			this.getSeparatorArrayList().clear();	
		}//end main:
		return bReturn;		
	}
	
	@Override
	public ArrayListZZZ<IEnumSetMappedStringFormatZZZ>getSeparatorArrayList() throws ExceptionZZZ{
		if(this.listaSeparator==null) {
			this.listaSeparator = new ArrayListZZZ<IEnumSetMappedStringFormatZZZ>();			
		}
		return this.listaSeparator;
	}
	
	@Override
	public void setSeparatorArrayList(ArrayListZZZ<IEnumSetMappedStringFormatZZZ> listaSeparator) throws ExceptionZZZ{
		this.listaSeparator = listaSeparator;
	}
	
	//++++++++++++++++++++++
	@Override
	public IEnumSetMappedStringFormatZZZ[] getStringFormatArrayDefault() throws ExceptionZZZ{
		return LogZZZ.getFormatForComputeLineDefault();			
	}
	
	@Override
	public IEnumSetMappedStringFormatZZZ[] getStringFormatArrayCurrent() throws ExceptionZZZ{
		IEnumSetMappedStringFormatZZZ[] objaReturn=null;
		if(this.ienumaFormatStringCurrent==null) {
			objaReturn = LogZZZ.getFormatForComputeLineDefault();
			this.ienumaFormatStringCurrent = objaReturn;
		}else {
			objaReturn = this.ienumaFormatStringCurrent;
		}
		return objaReturn;
	}
	
	@Override
	public void setStringFormatArrayCurrent(IEnumSetMappedStringFormatZZZ[] ienumaFormatString) throws ExceptionZZZ{
		this.ienumaFormatStringCurrent = ienumaFormatString;
	}

	
	
	//##########################################################################
	//### aus IStringFormatComputerZZZ
	@Override 
	public boolean resetStringIndexRead() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.getStringIndexReadList().size()>=1) {
				bReturn = true;
			}
			this.getStringIndexReadList().clear();	
		}//end main:
		return bReturn;
	}
	
	@Override 
	public ArrayListUniqueZZZ<Integer> getStringIndexReadList() throws ExceptionZZZ{
		if(this.listaintStringIndexRead==null) {
			this.listaintStringIndexRead = new ArrayListUniqueZZZ<Integer>();
		}
		return this.listaintStringIndexRead;
	}
	
	@Override
	public void setStringIndexRead(ArrayListUniqueZZZ<Integer> listaintStringIndexRead) throws ExceptionZZZ{
		this.listaintStringIndexRead = listaintStringIndexRead;
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			boolean bReturnIndexRead = this.resetStringIndexRead();
			
			boolean bReturnSepartorArray = this.resetSeparatorArrayList();
			
			bReturn = bReturnIndexRead | bReturnSepartorArray;			
		}//end main:		
		return bReturn;
	}
	
	
	//#################################################################
	//### COMPUTE
	//#################################################################
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(ienumFormatLogString);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumFormatLogString);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public String compute(String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.compute(null, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}
	
	@Override
	public String compute(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs)	throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String compute(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, ienumFormatLogString);
	}

	@Override
	public String compute(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(hm);
	}

	@Override
	public String compute(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, hm);
	}

	@Override
	public String compute(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, hm);
	}

	@Override
	public String compute(Object obj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(obj, sLogs);
	}

	@Override
	public String compute(Class classObj, String... sLogs) throws ExceptionZZZ {
		this.resetStringIndexRead();
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.compute(classObj, sLogs);
	}
	
	//######################
	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(ienumFormatLogString);

			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(obj, ienumFormatLogString);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{			
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(ienumaFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;	
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(obj, ienumFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(obj, ienumaFormatLogString, sLogs);
						
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			sReturn = objFormater.computeJagged_(classObj, ienumFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			//TODOGOON20260210;//zunächst einmal muss der objFormater die Formatanweisungen "normieren".
            //d.h. seine bisherigen Formatanweisungen mit den neuen Formatanweisungen vergleichen
			sReturn = objFormater.computeJagged_(classObj, ienumaFormatLogString, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumaFormatLogString);			
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			//... die konkrete Formatanweisung wurde schon aus einem Array der "normierten" Formatanweisungen geholt.
			sReturn = objFormater.computeJagged_(classObj, ienumFormatLogString);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, ienumFormatLogString);			
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged_(hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);			
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged_(obj, hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);			
		}//end main:
		return sReturn;			
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged_(classObj, hm);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn, hm);		
		}//end main:
		return sReturn;		
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged_(obj, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn);
		}//end main:
		return sReturn;				
	}

	@Override
	public String compute(IStringFormaterZZZ objFormater, Class classObj, String... sLogs) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			this.resetStringIndexRead();
			
			sReturn = objFormater.computeJagged_(classObj, sLogs);
			
			IStringJustifierManagerZZZ objJustifierManager = StringJustifierManagerZZZ.getInstance();
			sReturn = objJustifierManager.compute(sReturn);			
		}//end main:
		return sReturn;		
	}
		
	//#####################################################
	//### Aus ILogStringFormat Manager JAGGED
	//#####################################################
	@Override
	public String computeJagged_(String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ();
		return objFormater.computeJagged_(sLogs);
	}

	
	@Override
	public String computeJagged_(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(obj, sLogs);
	}

	@Override
	public String computeJagged_(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public String computeJagged_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(hm);
	}

	@Override
	public String computeJagged_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(obj, hm);
	}

	@Override
	public String computeJagged_(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(classObj, hmLog);
	}

	@Override
	public String computeJagged_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(ienumFormatLogString);
	}
	
	@Override
	public String computeJagged_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(obj, ienumFormatLogString);
	}
	
	@Override
	public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJagged_(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged_(obj, ienumFormatLogString);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged_(classObj, ienumFormatLogString);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged_(hm);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged_(obj, hm);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJagged_(classObj, hm);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJagged_(obj, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJagged_(classObj, sLogs);
	}

	@Override
	public String computeJagged_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJagged_(ienumFormatLogString);
	}
	
	
	//###########################################################
	//### Jede Methode auch als ....ArrayList
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());
		return objFormater.computeJaggedArrayList_(sLogs);
	}

	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(obj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(classObj, (IEnumSetMappedStringFormatZZZ[]) null, sLogs);
	}

	
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm)	throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(obj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hmLog) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(classObj, hmLog);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(ienumaFormatLogString, sLogs);
	}


	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(obj, ienumFormatLogString);
	}
	
	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(classObj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {	
		IStringFormaterZZZ objFormater = new StringFormaterZZZ(this.getStringIndexReadList());		
		return objFormater.computeJaggedArrayList_(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(obj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(obj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Object obj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(obj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(classObj, ienumFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(classObj, ienumaFormatLogString, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Class classObj, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(classObj, ienumFormatLogString);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Object obj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(obj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Class classObj, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(classObj, hm);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Object obj, String... sLogs) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(obj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, Class classObj, String... sLogs)	throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(classObj, sLogs);
	}

	@Override
	public ArrayListZZZ<String> computeJaggedArrayList_(IStringFormaterZZZ objFormater, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ {
		return objFormater.computeJaggedArrayList_(ienumFormatLogString);
	}
	
	//###################################################
	//### Merke: COMPUTE JUSTIFIED gibt es nicht bei XML-Format Manager, darum die entsprechenden Methoden direkt in FormatManager-Klasse
	//###################################################

	//###########################################
	//### FLAG HANDLING
	//###########################################
	
	//### IListenerObjectFlagZsetZZZ
	//Der FormatManager soll hinsichtlich der Flags von z.B. LogZZZ gesteuert werden koennen. Also wenn registriert, dann dort gesetzte Flags uebernehmen.
	@Override
	public boolean getFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IListenerObjectFlagZsetZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}	
	
	@Override
	public boolean proofFlagSetBefore(IListenerObjectFlagZsetZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
		
	@Override
	public boolean flagChanged(IEventObjectFlagZsetZZZ eventFlagZset) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(eventFlagZset==null) break main;
			
			//Wenn das Objekt ueber die Aenderung des Setzen des Flags informiert wird. 
			//Dieses Setzen des Flags ggfs. nachvollziehen.
			String sFlagText = eventFlagZset.getFlagText();
			boolean bFlagValue = eventFlagZset.getFlagValue();
			
			try {
				bReturn = this.setFlag(sFlagText, bFlagValue);
			} catch (ExceptionZZZ e) {
				//Falls es das Flag hier nicht gibt, wird die Exception hier nicht weitergeworfen.
				//Es kann aber auch ggfs. anders verfahren werden. 
			}
			
		}//end main:
		return bReturn;
	}
	
	
	//###################################################
	//### FLAG: ILogStringFormatManagerEnabledZZZ
	//###################################################
	@Override
	public boolean getFlag(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IStringFormatManagerEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
				
				//!!! Ein mögliches init-Flag ist beim direkten setzen der Flags unlogisch.
				//    Es wird entfernt.
				this.setFlag(IFlagZEnabledZZZ.FLAGZ.INIT, false);
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStringFormatManagerEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
