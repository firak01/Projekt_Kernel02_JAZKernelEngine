package basic.zKernel.file.ini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IObjectWithExpressionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.HashMapCaseInsensitiveZZZ;
import basic.zBasic.util.abstractList.Vector3ZZZ;
import basic.zBasic.util.abstractList.VectorDifferenceZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.xml.XmlUtilZZZ;
import basic.zKernel.IKernelConfigSectionEntryZZZ;
import basic.zKernel.IKernelConfigZZZ;
import basic.zKernel.IKernelEntryReferenceExpressionUserZZZ;
import basic.zKernel.IKernelEntryReferenceSubstituteUserZZZ;
import basic.zKernel.IKernelFileIniUserZZZ;
import basic.zKernel.IKernelUserZZZ;
import basic.zKernel.IKernelZZZ;
import basic.zKernel.KernelConfigSectionEntryZZZ;
import basic.zKernel.config.KernelConfigSectionEntryUtilZZZ;
import basic.zKernel.flag.util.FlagZFassadeZZZ;
import custom.zKernel.KernelLogZZZ;
import custom.zKernel.Log;
import custom.zKernel.file.ini.FileIniZZZ;

//DIES IST DER FLAG - WEG: AbstractObjectWithFlagZZZ -> AbstractObjectWithExpression -> AbstractTagWithExpressionBasic
//ALSO:      In AbstractInitTagWitchExpressionBasicZZZ steht schon ALLES WAS IN AbstractIniTagBasicZZZ und dessen Elternklasse implementiert ist
//NUN NOCH:  Alles WAS in AbstractIniTagSimpleZZZ steht hier auch noch hinein.

//ABER MIT DEM internen Entry-Objek aus IKernelConfigSectionEntryUserZZZ

//ZUDEM IST DAS DIE STELLE, AN DER KERNEL INI-PFADE/-VARIABLEN "substitiuert" werden beim Parsen.
//      Dadurch ist es notwendig eine Variablen HashMap zu verwalten und Flags für PATH und VARIABLE auch aufzunehmen
public abstract class AbstractKernelIniTagSimpleZZZ<T> extends AbstractIniTagWithExpressionBasicZZZ<T> implements IKernelUserZZZ, IKernelFileIniUserZZZ, ISubstituteEnabledZZZ, IParseUserZZZ, IKernelEntryReferenceExpressionUserZZZ, IKernelEntryReferenceSubstituteUserZZZ{
	private static final long serialVersionUID = -3319737210584524888L;
	protected volatile IKernelZZZ objKernel=null;
	protected volatile KernelLogZZZ objLog = null; //Kann anders als beim Kernel selbst sein.
	protected volatile FileIniZZZ<T> objFileIni = null; //Kann anders als beim Kernel selbst sein.
	
	//Für Interface IValueVariableUserZZZ
	protected HashMapCaseInsensitiveZZZ<String,String> hmVariable =null;
	
	//Fuer Interface IKernelConfigSectionEntryUserZZZ
	protected volatile IKernelConfigSectionEntryZZZ objEntryInner = null; //Vereinfachung, ich speichere alles hier ab, hier werden auch die Statusergebnisse der Formelaufloesungsschritte verwaltet.	
			
	public AbstractKernelIniTagSimpleZZZ() throws ExceptionZZZ {
		super(); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}

	public AbstractKernelIniTagSimpleZZZ(String sFlag) throws ExceptionZZZ {
		super(sFlag); //Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(String[] saFlag) throws ExceptionZZZ {
		super(saFlag);	//Elternklasse kennt keinen Kernel
		AbstractKernelIniTagSimpleNew_(null, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String sFlag) throws ExceptionZZZ {
		super(sFlag);
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelZZZ objKernel, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagSimpleNew_(objKernel, null);
	}

	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		super(""); //Elternklasse kennt keinen Kernel, aber mit super() wuerde "init"-Flag gesetzt. Also Leerzeichen setzen.
		AbstractKernelIniTagSimpleNew_(null, objKernelUsing);
	}
	
	public AbstractKernelIniTagSimpleZZZ(IKernelUserZZZ objKernelUsing, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);
		AbstractKernelIniTagSimpleNew_(null, objKernelUsing);
	}
	
	
	/** Wg. Interface IKernelUserZZZ notwendige Redundanz zu 
	 *  AbstractKernelUseObjectZZZ
	 *  und dem dortigen Konstruktor
	 *  
	 * @param objKernel
	 * @param objKernelUsing
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 05.07.2024, 07:10:15
	 */
	private boolean AbstractKernelIniTagSimpleNew_(IKernelZZZ objKernel, IKernelUserZZZ objKernelUsing) throws ExceptionZZZ {
		boolean bReturn = false;		
		main: {			
			if(this.getFlag("INIT")==true){
				bReturn = true;
				break main; 
			}	
			
			boolean btemp;
			String sLog = null;
			if(objKernel==null & objKernelUsing==null) {				
				ExceptionZZZ ez = new ExceptionZZZ("KernelObject and KernelUsingObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;					
			}
				
			IKernelZZZ objKernelUsed=null;
			if(objKernelUsing!=null) {	
				objKernelUsed = objKernelUsing.getKernelObject();
				if(objKernelUsed==null && objKernel==null) {
					ExceptionZZZ ez = new ExceptionZZZ("KernelObject missing",iERROR_PARAMETER_MISSING, this,  ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}else if (objKernelUsed==null){
					objKernelUsed = objKernel;
				}
			}else {
				objKernelUsed = objKernel;
			}
			
			this.objKernel = objKernelUsed;
			this.objLog = objKernelUsed.getLogObject();
			
			//Damit erst wird sichergestellt, dass die Flag-Aenderungen auf das Ini-File-Objekt uebertragen werden.
			this.registerForFlagEventAdopted(objKernelUsed.getFileConfigKernelIni());
									
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
									 Log.printlnDate(this, sLog);
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

	//### aus IResettableValuesZZZ
	@Override
	public boolean reset() throws ExceptionZZZ{
		return super.reset();
	}
	
	@Override
	public boolean resetValues() throws ExceptionZZZ{
		super.resetValues();
		this.resetEntry();
		return true;
	}
	
	@Override
	public boolean resetValues(Object objDefault) throws ExceptionZZZ {
		super.resetValues(objDefault);
		this.resetEntry();
		return true;
	}
	
	
	//### aus IKernelUserZZZ
	@Override
	public IKernelZZZ getKernelObject() throws ExceptionZZZ {
		return this.objKernel;
	}

	@Override
	public void setKernelObject(IKernelZZZ objKernel) {
		this.objKernel=objKernel;
	}
	
	//### aus IKernelLogUserZZZ
	@Override
	public KernelLogZZZ getLogObject() throws ExceptionZZZ {
		return this.objLog;
	}

	@Override
	public void setLogObject(KernelLogZZZ objLog) throws ExceptionZZZ {
		this.objLog = objLog;
	}	
	
	//### aus IKernelFileIniuserZZZ
	@Override
	public void setFileConfigKernelIni(FileIniZZZ objFileIni) throws ExceptionZZZ{
		this.objFileIni = objFileIni;
		this.registerForFlagEvent(objFileIni);
	}
	@Override
	public FileIniZZZ<T> getFileConfigKernelIni()throws ExceptionZZZ{
		if(this.objFileIni==null) {
			IKernelZZZ objKernel = this.getKernelObject();
			if(objKernel==null) {
				ExceptionZZZ ez = new ExceptionZZZ("FileIni and KernelObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			return objKernel.getFileConfigKernelIni();						
		}else {
			return this.objFileIni;
		}
	}
		
	//### aus IKernelConfigSectionEntryUserZZZ 	
	@Override
	public IKernelConfigSectionEntryZZZ getEntryNew() throws ExceptionZZZ {
		this.objEntryInner = null; //Also explizit leer setzen, um etwas neues zu holen. Ist wichtig für Objekte, die eine 2te Verarbeitung starten. z.B. KernelFileIni.
		return this.getEntry();
	}
				
	@Override
	public IKernelConfigSectionEntryZZZ getEntry() throws ExceptionZZZ{
		if(this.objEntryInner==null) {
			//!!! Endlosschleifengefahr.
			//Damit der Tag selbst uebegeben werden kann muss beim 
			//holen der Werte mit getValue() unbedigt auf this.objEntry==null geprueft werden.
			this.objEntryInner = new KernelConfigSectionEntryZZZ<T>(this);		
		}
		return this.objEntryInner;
	}
	
	@Override
	public void setEntry(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		this.objEntryInner = objEntry;
	}
	
	@Override 
	public boolean adoptEntryValuesMissing(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ {
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceTarget = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceTarget.set(this.getEntry());
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSource = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReferenceSource.set(objEntry);
		KernelConfigSectionEntryUtilZZZ.adoptEntryValuesMissing(objReturnReferenceTarget, objReturnReferenceSource);
		
		IKernelConfigSectionEntryZZZ objEntryTarget = objReturnReferenceTarget.get();
		this.setEntry(objEntryTarget);
		
		return true; //nur weil ja was gemacht wurde.
	}
	

	@Override
	public boolean resetEntry() throws ExceptionZZZ{
		return this.getEntry().reset();
	}
		
	// Die Methoden aus AbstractObjectWithExpressionZZZ muessen nur auf den Entry umgebogen werden.
	// Aber Achtung: Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
	//               Darum beim holen der Werte mit .get...() immer auf this.objEntry==null prüfen.

	//### Aus IValueBufferedUserZZZ
	@Override
	public VectorDifferenceZZZ<String> getValueVector() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr.
		return this.getEntry().getValueVector();
	}
	
	@Override
	public String getValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValue();
	}

	@Override
	public void setValue(String sValue) throws ExceptionZZZ {
		this.getEntry().setValue(sValue);
	}
	
	
	
	@Override
	public boolean hasAnyValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().hasAnyValue();
	}	
	
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasAnyValue(boolean bAnyValue) throws ExceptionZZZ {		
		this.getEntry().hasAnyValue(bAnyValue);
	}
	
	@Override
	public boolean hasNullValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().hasNullValue();
	}
	//Wird beim Setzen des Werts automatisch mit gesetzt. Also nicht "von aussen" setzbar, 
	//daher protected. Was nicht im Intface definierbar ist.
	@Override
	public void hasNullValue(boolean bNullValue) {		
		this.bNullValueInObjectWithExpression=bNullValue;
	}
	
	//####################################################
    //### aus IValueComputedBufferedUserZZZ
	@Override
	public VectorDifferenceZZZ<String> getRawVector() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRawVector();
	}

	@Override
	public String getRaw() throws ExceptionZZZ {
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getRaw();
	}

	@Override
	public void setRaw(String sRaw) throws ExceptionZZZ {
		this.getEntry().setRaw(sRaw);
	}
	
	//##############################
	//### aus IValueArrayUserZZZ
	//Merke: Arrays erst in ini-Tag behandeln, da es dafuer Separatorn in der Zeile geben muss
	@Override	
	public ArrayList<String> getValueArrayList() throws ExceptionZZZ{
		if(this.objEntryInner==null)return null; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().getValueArrayList();
	}

	@Override
	public void setValue(ArrayList<String> listasValue) throws ExceptionZZZ{
		this.getEntry().setValue(listasValue);		
	}
	
	@Override
	public boolean isArrayValue() throws ExceptionZZZ {
		if(this.objEntryInner==null)return false; //!!!Sonst Endlosschleifengefahr. Wg. Konstruktor mit diesem Objekt selbst als Uebergabe (this)
		return this.getEntry().isArrayValue();
	}

	@Override
	public void isArrayValue(boolean bIsArrayValue) throws ExceptionZZZ {
		this.getEntry().isArrayValue(bIsArrayValue);
	}
			
	//###############################################
	//### aus IValueMapUserZZZ
	@Override 
	public VectorDifferenceZZZ<HashMap<String,String>> getValueHashMapVector() throws ExceptionZZZ{
		return this.getEntry().getValueHashMapVector();
	}
		
	@Override
	public HashMap<String,String> getValueHashMap() throws ExceptionZZZ {		
		if(this.hasNullValue()){
			return null;		
		}else if (!this.hasAnyValue()){
			return new HashMap<String,String>(); //also anders als beim definierten </NULL> -Objekt hier einen Leerstring zurückgeben. Ein Leerstring kann nämlich auch gewuenscht sein!				
		}else {
			return this.getValueHashMapVector().getEntryHigh();
		}
	}

	@Override
	public void setValue(HashMap<String,String> hmValue) throws ExceptionZZZ {
		this.getValueHashMapVector().add(hmValue);
		this.isMapValue(true);
	}
	
	@Override
	public boolean isMapValue() throws ExceptionZZZ {
		return this.getEntry().isMapValue();
	}
	
	@Override 
	public void isMapValue(boolean bIsMapValue) throws ExceptionZZZ {
		this.getEntry().isMapValue(bIsMapValue);
	}

	
	//### aus IParseEnabledZZZ
	@Override
	//Im ersten Wurf abstract lassen. 
	//public abstract boolean isParserEnabledThis() throws ExceptionZZZ;

	//Aber eigentlich immer true. Falls ggfs. notwendig ein Interface fuer Parser machen, also wie IKernelCallIniSolverZZZ => IKernelCallIniParserZZZ. 
	//und da hinein ein enum FLAGZ usexxx definieren. Darum ggfs. in der erbenden Klasse ueberschreiben.
	public boolean isParserEnabledThis() throws ExceptionZZZ {
		//z.B. Moeglichekeit fuer Solver: return this.isSolverEnabledThis(); //(halt gleich wie solver), es gibt auch (noch) kein eigenes Flag dafuer. 
		return true; //Default, weil: Tags ohne Flag-Steuerung werden immer geparsed.
	}
	
	@Override
	public String parse(String sExpression) throws ExceptionZZZ{
		return this.parse_(sExpression, null, true);
	}	
	
	@Override
	public String parse(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parse_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	//Die Idee ist, das die konkreten Klassen den ersten Vector parsen
	//also ggfs. überschreiben: public Vector<String>parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{	
	//+++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		//Muss ueberschrieben werden, damit die "einfache Tag" Methode nicht greift und wir mit der parse - Methode dieser konkreten Klasse arbeiten.
		return this.parseFirstVector_(sExpression, null, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.parseFirstVectorPost(vecExpression, null);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVectorPost(vecExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		return this.parseFirstVectorPost(vecExpression, null, bKeepSurroundingSeparatorsOnParse, bRemoveOwnTagParts);
	}

	
	//++++++++++++++++++++++++++++++++++++++++++
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntryNew(String sExpression) throws ExceptionZZZ{
		//Nein, das setzt das Entry-Objekt des Solvers zurueck IKernelConfigSectionEntryZZZ objReturn = this.getEntryNew();
		//und damit sind bestehende Eintragswerte ggfs. uebernommen IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>(this);
		IKernelConfigSectionEntryZZZ objReturn = new KernelConfigSectionEntryZZZ<T>();						
		main:{			
			if(StringZZZ.isEmpty(sExpression)) break main;
			objReturn.setRaw(sExpression);
					
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceSolve = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceSolve.set(objReturn);
			objReturn = this.parseAsEntry_(sExpression, objReturnReferenceSolve, true);			
		}//end main:
		return objReturn;
	}	
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, objReturnReferenceIn, true);
	}
	
	@Override
	public IKernelConfigSectionEntryZZZ parseAsEntry(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		return this.parseAsEntry_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
	}
	
	private IKernelConfigSectionEntryZZZ parseAsEntry_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objReturn = null;		
		String sReturn = sExpressionIn;
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);

		this.updateValueParseCalled();
		this.updateValueParseCalled(objReturnReference);
		objReturn = objEntry;
	
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			//Es soll immer ein Entry Objekt zurückkommen, darum hier erst auf das Expression-Flag abpruefen.
			bUseExpression = this.isExpressionEnabledGeneral();
			if(!bUseExpression) break main;
			
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;

			//Direkte nachdem feststeht, dass Expression und Parser behandelt werden die Tags analysieren!!!
			this.updateValueParseCustom(objReturnReference, sExpression);

			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben
			bUseParserThis = this.isParserEnabledCustom(); //this.isParserEnabledThis();
			if(!bUseParserThis) break main;
			
			
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			//Merke: in Elternklassen gibt es diese Methode nur ohne Reference, da ohne KernelEbene das Objekt nicht vorhanden ist.
			//       Darum wird die Methode auch hier ueberschrieben.
			//Hier Methode nur ohne Reference... String sReturn = this.parse(sExpression, objReturnReferenceParse, bRemoveSurroundingSeparators);
			//Mit Reference geht ab: AbstractKernelIniTagSimpleZZZ
			ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>(); 			
			objReturnReferenceParse.set(objEntry);
			sReturn = this.parse(sExpression, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
			objEntry = objReturnReferenceParse.get();
			objReturn = objEntry;
		}//end main:
		
		if(objEntry!=null) {	
			objEntry.setValue(sReturn); 														
			if(objReturnReferenceIn!=null) objReturnReferenceIn.set(objEntry);
			this.adoptEntryValuesMissing(objEntry);
		}	
		return objReturn;
	}
	
	//++++++++++++++++++++++++++++++++	
	//### Aus IKernelEntryReferenceExpressionUserZZZ
	@Override
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String parse(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parse_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
	}
	
	private String parse_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();		
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		//############
		//Wichtig: Bei jedem neuen Parsen (bzw. vor dem Solven(), nicht parse/solveFirstVector!) die internen Werte zuruecksetzen, sonst wird alles verfaelscht.
		//         Ziel ist, das nach einem erfolgreichen Parsen/Solven das Flag deaktiviert werden kann UND danach bei einem neuen Parsen/Solven die Werte null sind.
		this.resetValues();			
		//#######
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {	
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);							
		}else{
			//Achtung: Das objReturn Objekt NICHT generell versuchen mit .getEnry() und ggfs. dann darin .getEntryNew() uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
						
		}

		main:{
			//das ist zwar dann auch in .parseFirstVector() aber die Genauigkeit fordert es an dieser Stelle.
			this.updateValueParseCalled();
			this.updateValueParseCalled(objReturnReference);
			
			
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
			
			this.setRaw(sExpressionIn);		
			objEntry.setRaw(sExpressionIn);

			sReturnTag = this.getValue();
			sReturnLine = sExpressionIn;
			sReturn = sReturnLine;
			vecReturn.set(0, sExpressionIn);//nur bei in dieser Methode neu erstellten Vector.
			
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;		
					
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			//Direkte nachdem feststeht, dass Expression und Paser behandelt werden die Tags analysieren!!!
			this.updateValueParseCustom(objReturnReference, sExpression);
			
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben			
			bUseParserThis = this.isParserEnabledCustom(); //this.isParserEnabledThis();
			if(!bUseParserThis) break main;
			
			parse:{
				//Den ersten Vektor bearbeiten. Darin wird auch die Kernel Ini-Pfad/-Variablenersetzung gemacht
				//sowie die ganzen Flags hinsichtlich "is...Enabled" ausgewertet.
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceParse.set(objEntry);										
				vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
				objEntry = objReturnReferenceParse.get();
				if(vecReturn==null) break parse;
				
				//Pruefe ob der Tag enthalten ist:
				//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
				if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
				
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				this.setValue(sReturnTag);
				
				//Tags entfernen und eigenen Wert setzen
				vecReturn = this.parsePost(vecReturn, objReturnReference, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt. //sReturnTag = this.getValue();//Der eigene Wert, ohne drumherum
				
				//Der Vector ist schon so aufbereiten, dass hier nur noch "zusammenaddiert" werden muss
				sReturnLine = VectorUtilZZZ.implode(vecReturn);		
			}//end parse:
			this.updateValueParsed();
			this.updateValueParsed(objReturnReference);			
		}//end main:
	
		//Auf PARSE-Ebene... Als echten Ergebniswert aber die <Z>-Tags ggfs. rausrechnen, falls gewuenscht
		//if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag); //BEIM PARSEN DEN TAG-WERT NICHT IN VEC(1) UEBERNEHMEN
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
				
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null) objReturnReference.set(objEntry);
			if(bUseExpression  || bUseParser) {								
				if(sExpressionIn!=null) {
					String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					if(!sExpression2Compare.equals(sReturnLine2Compare)) {
						this.updateValueParsedChanged();
						this.updateValueParsedChanged(objReturnReference);
					}
				}						
				this.adoptEntryValuesMissing(objEntry);
			}			
		}		
		return sReturn;
	}
	
		
	//### Aus IKernelEntryReferenceExpressionUserZZZ	
	@Override
	public Vector3ZZZ<String>parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference) throws ExceptionZZZ{
		return this.parseFirstVector_(sExpression, objReturnReference, true, true);
	}

	@Override
	public Vector3ZZZ<String> parseFirstVector(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.parseFirstVector_(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	private Vector3ZZZ<String> parseFirstVector_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bIgnoreCase) throws ExceptionZZZ {
		String sReturn = null; String sReturnSubstituted = null; String sReturnTag = null; String sReturnLine = null; String sReturnLineParsed = null;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;			
		if(objReturnReferenceIn==null) {
			objReturnReference =  new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();												
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		
		if(objEntry==null) {
			//Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
		this.updateValueParseCalled();
		this.updateValueParseCalled(objReturnReference);
		
		main:{
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			String sExpression = sExpressionIn;
				
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);	
			
			sReturnTag = this.getValue();
			sReturnLine=sExpressionIn;
			vecReturn.set(0, sReturnLine);//nur bei in dieser Methode neu erstellten Vector.
			sReturn = sReturnLine;
						
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;		
					
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			//Direkte nachdem feststeht, dass Expression und Paser behandelt werden die Tags analysieren!!!
			this.updateValueParseCustom(objReturnReference, sExpression);
			
			//Falls man diesen Tag aus dem Parsen (des Gesamtstrings) rausnimmt, muessen die umgebenden Tags drin bleiben			
			bUseParserThis = this.isParserEnabledCustom(); //this.isParserEnabledThis();
			if(!bUseParserThis) break main;
						
			
														
			//###########################################
			//### 
			//###########################################
			
			parse:{
				//Zerlegen des Z-Tags, d.h. Teil vorher, Teil dahinter.
			    //Wichtig hier die Z-Tags in der MITTE des Vector3 drin lassen, nur dann funktioniert die RegEx-Expression für Pfadangabe.
				//!!! Unterschied zum AbstractKernelIniTagCascadedZZZ
				vecReturn = super.parseFirstVector(sExpression, bKeepSurroundingSeparatorsOnParse); //Merke: Auf der hoeheren Hierarchieben gibt es kein objEntry....
				if(vecReturn==null) break main;	
							
				//Pruefe ob der Tag enthalten ist:
				//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
				if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
			
				//+++++++++++++++++++++++++
				//20241023 Erweiterungsarbeiten, Ini-Pfade und Variablen "substituieren"
				String sValueToSubstitute = vecReturn.get(1).toString();  //Merke: Das ist dann der Wert es Tags, wenn der Parser nicht aktiviert ist. Wenn der Tag nicht im String ist, ist das korrekterweise auch ein Leerstring.
				this.setValue(sValueToSubstitute);
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSubstitute= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSubstitute.set(objEntry);
				sReturnSubstituted = this.substituteParsed(sValueToSubstitute, objReturnReferenceSubstitute, bKeepSurroundingSeparatorsOnParse);				
				objEntry = objReturnReferenceSubstitute.get();						
				vecReturn.replace(1,sReturnSubstituted);
				
				//Falls Substitution durchgeführt wurde noch einmal den String durchsuchen, nach Tags.
				//und ggfs. wieder (neu gefundene) Value-Eintraege setzen
				sReturnLine  = VectorUtilZZZ.implode(vecReturn);
				if(objEntry.isSubstitutedChanged()) {				
					this.updateValueParseCustom(objReturnReference, sReturnLine);			
				}			
				//+++++++++++++++++++++++++
				
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
				this.setValue(sReturnTag);			
				vecReturn.replace(sReturnTag); //da noch weiter verarbeitet werden muss.
				sReturnLineParsed = VectorUtilZZZ.implode(vecReturn);
				sReturnLine = sReturnLineParsed;			
				sReturn = sReturnLine;
				
				//+++ Der endgueltige Wert der Zeile und eigenen Wert setzen 
				//Als echten Ergebniswert aber die <Z>-Tags und den eigenen Tag rausrechnen, falls gewuenscht
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParserSuper= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceParserSuper.set(objEntry);
				vecReturn = this.parseFirstVectorPost(vecReturn, objReturnReferenceParserSuper, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = this.getValue();
				sReturnLine = VectorUtilZZZ.implode(vecReturn);
				
				//###############
				//### Andere Solver parsen noch unterelemente. Im Abstract Solver reicht es nun.
				//###############
			}//end parse:			
			this.updateValueParsed();
			this.updateValueParsed(objReturnReference);
		}//end main:		
		
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null)objReturnReference.set(objEntry);
			if(sExpressionIn!=null) {								 						
				String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				if(!sExpression2Compare.equals(sReturnLine2Compare)) {
					this.updateValueParsedChanged();
					this.updateValueParsedChanged(objReturnReference);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);
		}		
		return vecReturn;
	}
	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parseFirstVectorPost_(vecExpression, objReturnReference, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parseFirstVectorPost_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {		
		return this.parseFirstVectorPost_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse, bRemoveOwnTagParts);
	}
	
	private Vector3ZZZ<String> parseFirstVectorPost_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sExpressionIn = null; 
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;				
		boolean bUseExpression = false; boolean bUseParser = false; boolean bUseParserThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}							
		
		main:{			
			if(vecExpressionIn==null) break main;
		
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;						
					
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);	
			objEntry.setValueAsExpression(sExpressionIn, false);
			
			sReturnLine = sExpressionIn;
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecExpressionIn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.//hier nicht: this.getValue(); weil der ja erst ggfs. hier geholt wird.
			sReturn = sReturnLine;
			
			bUseParser = this.isParserEnabledGeneral();
			if(!bUseParser) break main;
			
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
			bUseParserThis = this.isParserEnabledCustom(); //this.isParserEnabledThis();
			if(!bUseParserThis) break main;
						
			if(!bKeepSurroundingSeparatorsOnParse) {
				//Wirf die Surrounding Z-Tags raus (falls vorhanden).
				//Aber behalte die Z-Tags innerhalb des Container Tags (z.B. hier z:Formula muss weiterhin Z-Tags enthalten)
					
				String sTagContainerStart = this.getTagPartOpening();
				String sTagContainerEnd = this.getTagPartClosing();					
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ, false, false, sTagContainerStart, sTagContainerEnd); //also von aussen nach innen!!!
				sReturnLine = VectorUtilZZZ.implode(vecReturn);	
										
//				//PROBLEM BEI {[...]...} also den PATH - Tags, da sie unterschiedlich im Aufbau sind.
//				if(!(sTagStartZ.equals(sTagContainerStart) & sTagEndZ.equals(sTagContainerEnd))) {
//					KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(vecReturn, sTagContainerStart, sTagContainerEnd);  //also von innen nach aussen
//				}
			}
								
			//+++ Der Wert des Tags selbst (nicht der Zeile(!)) ++++++++++++++++++++++++++++++++++++
			//Merke: Bei einem INI-Path wird der so komplett aufgeloest, das er eh nicht mehr drin ist.
			//       Dann wuerde ein Aufloesen sogar einen Fehler bedeuten.
			if(bRemoveOwnTagParts) {
				//Bei anderen Tags gilt:
				//Diesen Wert nicht in den Vektor zurueckscheiben. Das wuerde die Zeile verfaelschen.			
				//Also: Den Wert des Tags setzen. Das ist der Wert aus vec(1), und dann den Tag-Namen darum entfernt.
	
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();
					
//				//Entferne auch nur die eigenen Tags, wenn das nicht schon durch bRemoveSurroundigSeperators erlegigt wurde
//				//bzw. bei Gleichnamigkeit ueberhaupt entfernt werden darf.
//				String sTagStartZ = "<Z>";
//				String sTagEndZ = "</Z>";					
//				if(!(sTagStartZ.equals(sTagStart) & sTagEndZ.equals(sTagEnd))) {
				sReturnTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnTag, sTagStart, sTagEnd, true, false); //also an jeder Postition (d.h. nicht nur am Anfang), also von aussen nach innen!!!
//				}				
			}
			vecReturn.replace(sReturnTag);
			this.setValue(sReturnTag);	
			
			//+++ ggfs. weitere Sachen aus der Zeile (!) rausrechnen, falls gewuenscht
			vecReturn = this.parseFirstVectorPostCustom(vecReturn, bKeepSurroundingSeparatorsOnParse);				
			sReturnTag = this.getValue();			
			sReturnLine = VectorUtilZZZ.implode(vecReturn);	
			
//			this.updateValueParsed();
//			this.updateValueParsed(objReturnReference);
		}//end main:
				
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.		
		vecReturn.replace(sReturnTag);						
		this.setValue(sReturnTag);
//		sReturn = sReturnLine;
//				
//		if(objEntry!=null) {			
//			objEntry.setValue(sReturnLine);	
//			objEntry.setValueFromTag(sReturnTag);
//			if(objReturnReference!=null) objReturnReference.set(objEntry);
//			if(bUseExpression | bUseParse) {
//				if(sExpressionIn!=null) {									
//					String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//					String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//					if(!sExpression2Compare.equals(sReturnLine2Compare)) {
//						this.updateValueParsedChanged();
//						this.updateValueParsedChanged(objReturnReference);
//					}
//				}			
//			}
//			this.adoptEntryValuesMissing(objEntry);
//		}
		return vecReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parseFirstVectorPostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> parseFirstVectorPostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parseFirstVectorPostCustom_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
	}
	
	//Methode mit Reference Objekt
	private Vector3ZZZ<String> parseFirstVectorPostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;
		String sExpressionIn = null;		
		boolean bUseExpression = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}							
		
		main:{	
			if(vecExpressionIn==null) break main;
						
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
							
			sReturnLine=sExpressionIn;
			this.setRaw(sExpressionIn);		
			objEntry.setRaw(sExpressionIn);	
			objEntry.setValueAsExpression(sReturnLine, false);
												
			bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
				
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
			sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
			sReturnLine = VectorUtilZZZ.implode(vecReturn);			
		}//end main:
				
		//#################################
		//Den Wert ersetzen, wenn es was zu ersetzen gibt.		
		if(vecReturn!=null && sReturnTag!=null) vecReturn.replace(sReturnTag);						
		if(sReturnTag!=null) this.setValue(sReturnTag);
		sReturn = sReturnLine;
				
		if(objEntry!=null) {			
			objEntry.setValue(sReturn);	
			if(sExpressionIn!=null) {			 								
				String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
				if(!sExpression2Compare.equals(sReturnLine2Compare)) {
					this.updateValueParsedChanged();
					this.updateValueParsedChanged(objReturnReference);
				}
			}			
			if(objReturnReference!=null) objReturnReference.set(objEntry);
		}
		return vecReturn;
	}

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, objReturnReference, true, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parsePost_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePost(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		return this.parsePost_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse, bRemoveOwnTagParts);
	}
	
	private Vector3ZZZ<String> parsePost_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse, boolean bRemoveOwnTagParts) throws ExceptionZZZ {
		Vector3ZZZ<String> vecReturn = vecExpressionIn; //Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;		
		boolean bUseExpression = false; boolean bUseParse = false; boolean bUseParserThis = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
						
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}							
		

		
		
		String sExpressionIn = null;
		main:{
			if(vecExpressionIn==null) break main;			
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			sReturnLine = sExpressionIn;
			this.setRaw(sReturnLine);
			objEntry.setRaw(sReturnLine);
			objEntry.setValueAsExpression(sReturnLine, false);
				
			sReturnTag = this.getValue();
							
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;						
				
						
			//Als echten Ergebniswert aber die konkreten <Z>-Tags (z.B. eines Solves) ggfs. rausrechnen, falls gewuenscht
			//Z...-Tags "aus der Mitte entfernen"... Wichtig für das Ergebnis eines Parsens
			bUseParserThis = this.isParserEnabledCustom();
			if(!bUseParserThis) break main;
			
			if(!bKeepSurroundingSeparatorsOnParse) {
				//Wirf die Surrounding Z-Tags raus (falls vorhanden).
				//Aber behalte die Z-Tags innerhalb des Container Tags (z.B. hier z:Formula muss weiterhin Z-Tags enthalten)					
				String sTagContainerStart = this.getTagPartOpening();
				String sTagContainerEnd = this.getTagPartClosing();					
				KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsContainerSurroundingRemoved(vecReturn, sTagStartZ, sTagEndZ, false, false, sTagContainerStart, sTagContainerEnd); //also von aussen nach innen!!!				
			}
								
			//+++ Der Wert des Tags			
			//Merke: Diesen Wert nicht in den Vektor zurueckscheiben. Das wuerde die Zeile verfaelschen.			
			//    Also: Den Wert des Tags setzen. Das ist der Wert aus vec(1), und dann den Tag-Namen darum entfernt.				
			if(bRemoveOwnTagParts) {										
				String sTagStart = this.getTagPartOpening();
				String sTagEnd = this.getTagPartClosing();								
//					
//				//Entferne auch nur die eigenen Tags, wenn das nicht schon durch bRemoveSurroundigSeperators erledigt wurde
//				//bzw. bei Gleichnamigkeit ueberhaupt entfernt werden darf.			
//				if(!(sTagStartZ.equals(sTagStart) & sTagEndZ.equals(sTagEnd))) {
					sReturnTag = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnTag, sTagStart, sTagEnd, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
					this.setValue(sReturnTag);		
//				}					
			}
			
			//+++ ggfs. weitere Sachen aus der Zeile (!) rausrechnen, falls gewuenscht
			vecReturn = this.parsePostCustom(vecReturn, bKeepSurroundingSeparatorsOnParse);
			sReturnTag = this.getValue();
			sReturnLine = VectorUtilZZZ.implode(vecReturn);
		
//			this.updateValueParsed();
//			this.updateValueParsed(objReturnReference);
		}//end main:
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
//		sReturn = sReturnLine;
//		
//		if(objEntry!=null) {			
//			objEntry.setValue(sReturnLine);	
//			objEntry.setValueFromTag(sReturnTag);
//			if(objReturnReference!=null) objReturnReference.set(objEntry);
//			if(sExpressionIn!=null) {
//				String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//				String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//				if(!sExpression2Compare.equals(sReturnLine2Compare)) {
//					this.updateValueParsedChanged();
//					this.updateValueParsedChanged(objReturnReference);
//				}
//			}			
//			this.adoptEntryValuesMissing(objEntry);
//		}
		return vecReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.parsePostCustom_(vecExpression, objReturnReference, true);
	}
	
	@Override
	public Vector3ZZZ<String> parsePostCustom(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.parsePostCustom_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
	}
	
	//Methode mit Reference Objekt
	private Vector3ZZZ<String> parsePostCustom_(Vector3ZZZ<String> vecExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		Vector3ZZZ<String> vecReturn = vecExpressionIn;
		String sReturn = null; String sReturnLine = null;
		String sExpressionIn = null;		
		boolean bUseExpression = false;
		
		String sTagStartZ = "<Z>";
		String sTagEndZ = "</Z>";
				
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}	
		
		//20250312 objEntry.isParseCalled(true);
		//this.updateValueParseCalled();
		//this.updateValueParseCalled(objReturnReference);
		
		main:{			
			if(vecExpressionIn==null) break main;
			
			sExpressionIn = VectorUtilZZZ.implode(vecExpressionIn);
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			this.setRaw(sExpressionIn);
			objEntry.setRaw(sExpressionIn);
			
			//sReturn = (String) vecExpressionIn.get(1);
			//sReturnLine = VectorUtilZZZ.implode(vecExpressionIn);		
			bUseExpression = this.getFlag(IObjectWithExpressionZZZ.FLAGZ.USEEXPRESSION); 
			if(!bUseExpression) break main;
				
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
								
		}//end main:
				
		//#################################
		
//		if(objEntry!=null) {
//			if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
//			objEntry.setValue(sReturn);	
//			if(sExpressionIn!=null) {							 								
//				String sExpression2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sExpressionIn, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//				String sReturnLine2Compare = KernelConfigSectionEntryUtilZZZ.getExpressionTagpartsSurroundingRemoved(sReturnLine, sTagStartZ, sTagEndZ, true, false); //also an jeder Position (d.h. nicht nur am Anfang) ,also von aussen nach innen!!!
//				if(!sExpression2Compare.equals(sReturnLine2Compare)) {
//					this.updateValueParsedChanged();
//					this.updateValueParsedChanged(objReturnReference);
//				}
//			}			
//			if(objReturnReference!=null) objReturnReference.set(objEntry);
//		}
		return vecReturn;
	}


	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	//### Aus IParseUserZZZ
	@Override
	public boolean isParse(String sExpression) throws ExceptionZZZ {
		return this.isExpression(sExpression);
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override 
	public void addHistoryParseCalled() throws ExceptionZZZ{
		String sTagName = this.getName();
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistoryParseCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistoryParseCalled(String sTagName) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistoryParseCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistoryParseCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		String sTagName = this.getName();
		this.addHistoryParseCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistoryParseCalled(IKernelConfigSectionEntryZZZ objEntry, String sTagName) throws ExceptionZZZ{
		objEntry.setHistoryParseCalled(sTagName);
	}
	
	
	
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueParseCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, String sExpressionIn) throws ExceptionZZZ{
		//oberste Klasse erreicht   super.updateValueParseCustom(objReturnReference, sExpressionIn);
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
	
	
		//#######################################################
		//Flags entscheiden ob es weitergeht.
		if(this.isExpressionEnabledGeneral()) {

			//Zentrale Stelle, um den String/Entry als Expression zu kennzeichnen.
			//Hier redundant zu parse(), weil z.B. in solve() nur parseFirstVector() aufgerufen wird.
			if(XmlUtilZZZ.isExpression(sExpressionIn)){
				objEntry.isExpression(true);
				this.getEntry().isExpression(true);
			}
		}
	}
		
	//##############################################################
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueParseCalled() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParseCalled(objReturnReference, true);
	}


	@Override
	public void updateValueParseCalled(boolean bIsParseCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParseCalled(objReturnReference, bIsParseCalled);
	}


	@Override
	public void updateValueParseCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueParseCalled(objReturnReference, true);
	}


	@Override
	public void updateValueParseCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParseCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isParseCalled(bIsParseCalled);
			
		//Die "echte" Feststellung welcher Tag aufgerufen wird. Ggfs. kann man daraus die Reihenfolge ablesen.
		this.addHistoryParseCalled(objEntry);
	}

	//+++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueParsed() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParsed(objReturnReference, true);
	}


	@Override
	public void updateValueParsed(boolean bIsParsed) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParsed(objReturnReference, true);
	}


	@Override
	public void updateValueParsed(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueParsed(objReturnReference, true);		
	}


	@Override
	public void updateValueParsed(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsed) throws ExceptionZZZ {
		main:{
			if(!this.isExpressionEnabledGeneral()) break main;

			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();			
			if(this.isParserEnabledGeneral()) {
				objEntry.isParsed(bIsParsed);
			}
		}//end main:
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueParsedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParsedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueParsedChanged(boolean bIsParsedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueParsedChanged(objReturnReference, bIsParsedChanged);
	}


	@Override
	public void updateValueParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueParsedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueParsedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsedChanged)	throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isParsedChanged(bIsParsedChanged);
	}
	
	//+++++++++++++++++++++++++++++++++++++
	//#####################################
	
	
	
	//### aus IExpressionUserZZZ	
	/** Gibt einen Vector zurück, in dem das erste Element der Ausdruck VOR der ersten 'Expression' ist. Das 2. Element ist die Expression. Das 3. Element ist der Ausdruck NACH der ersten Expression.
	* @param sExpression
	* @return
	* 
	* lindhaueradmin; 06.03.2007 11:20:34
	 * @throws ExceptionZZZ 
	 */
//	@Override
//	public Vector3ZZZ<String>parseFirstVectorAsExpression(String sExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String> vecReturn = null;
//
//		main:{
//			if(sExpression==null) break main;
//			
//			vecReturn = new Vector3ZZZ<String>();
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//								
//			//Bei dem einfachen Tag wird die naechste Tag genommen und dann auch das naechste schliessende Tag...
//			//vecReturn = StringZZZ.vecMidFirst(sExpression, this.getTagStarting(), this.getTagClosing(), true, false);
//			vecReturn = this.parseFirstVector(sExpression, false);
//				
//		}//end main:			
//		return vecReturn;
//	}
//	
//	@Override
//	public Vector3ZZZ<String>parseAllVectorAsExpression(String sExpression) throws ExceptionZZZ{
//		Vector3ZZZ<String> vecReturn = null;		
//		main:{
//			if(sExpression==null) break main;
//			
//			vecReturn = new Vector3ZZZ<String>();
//			if(StringZZZ.isEmpty(sExpression)) break main;
//			
//			if(! this.getFlag(IIniTagWithExpressionZZZ.FLAGZ.USEEXPRESSION)) break main;
//								
//			//Merke: Das ist der Fall, das ein Ausdruck NICHT verschachtelt ist
//			//       Für verschachtelte Tags muss hier extra was programmiert und diese Methode ueberschrieben werden.
//			vecReturn = this.parseFirstVectorAsExpression(sExpression); 			
//			
//		}//end main:
//		
//		return vecReturn;
//	}
	
	//### aus IConvertableZZZ
		
	/* IDEE: convertable != parasable.
    convertable bedeutet DER GANZE STRING Wird ersetzt, also nur wenn nix davor oder dahniter steht.
    parsable würde dann lediglich den Wert aus der Mitte (s. Vector.getIndex(1) ) durch ein Leerzeichen ersetzen
	
	* 
	* (non-Javadoc)
	* @see basic.zKernel.file.ini.AbstractIniTagSimpleZZZ#isConvertRelevant(java.lang.String)
	*/
	@Override
	public boolean isConvertRelevant(String sExpression) throws ExceptionZZZ {
	boolean bReturn = false;
	main:{
		if(StringZZZ.isEmptyTrimmed(sExpression)) break main;
		
		bReturn = this.getEmpty().equalsIgnoreCase(sExpression);
		if(bReturn) break main;
	
		bReturn = XmlUtilZZZ.isSurroundedByTag(sExpression, this.getTagPartOpening(), this.getTagPartClosing());		
	}//end main
	return bReturn;
	}
	
	//### aus ISubstituteEnabledZZZ
	@Override
	public boolean isSubstituteEnabledThis() throws ExceptionZZZ {
		return this.isSubstitutePathEnabledThis() | this.isSubstituteVariableEnabledThis();
	}
	
	@Override 
	public boolean isSubstitutePathEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ.USEEXPRESSION_PATH);
	}
	
	@Override 
	public boolean isSubstituteVariableEnabledThis() throws ExceptionZZZ {
		return this.getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ.USEEXPRESSION_VARIABLE);
	}
		
	@Override
	public boolean isSubstituteRelevant() throws ExceptionZZZ {
		return true;
	}
				
	@Override
	public boolean isSubstitute(String sExpression) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.isSubstitutePath(sExpression);
			if(bReturn) break main;
			
			bReturn = this.isSubstituteVariable(sExpression);
			if(bReturn) break main;
						
		}//end main;
		return bReturn;
	}
		
	@Override
	public boolean isSubstitutePath(String sExpression) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isExpression(sExpression, KernelZFormulaIni_PathZZZ.sTAG_NAME, false);
	}
	
	@Override
	public boolean isSubstituteVariable(String sExpression) throws ExceptionZZZ {
		return ExpressionIniUtilZZZ.isExpression(sExpression, ZTagFormulaIni_VariableZZZ.sTAG_NAME, false);
	}
	@Override
	public String substitute(String sExpression) throws ExceptionZZZ {
		return this.substitute_(sExpression, null, true);
	}

	@Override
	public String substitute(String sExpression, boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.substitute_(sExpression, null, bRemoveSurroundingSeparators);
	}
		
	//### aus IKernelEntryReferenceSubstituteUserZZZ
	@Override
	public String substitute(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn,	boolean bRemoveSurroundingSeparators) throws ExceptionZZZ {
		return this.substitute_(sExpressionIn, objReturnReferenceIn, bRemoveSurroundingSeparators);
	}
	
	@Override
	public String substitute(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn) throws ExceptionZZZ {
		return this.substitute_(sExpressionIn, objReturnReferenceIn, true);
	}
	
	private String substitute_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		String sReturn=sExpressionIn;
		String sReturnLine = null; String sReturnTag = null;
		Vector3ZZZ<String> vecReturn = new Vector3ZZZ<String>();
		boolean bUseExpression = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = null;
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
										   //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			//unten objEntry immer an das ReferenceOjekt zurueckgeben
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		this.setRaw(sExpressionIn);
		objEntry.setRaw(sExpressionIn);		
		objEntry.isSubstituteCalled(true);
		
		main:{
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			String sExpression = sExpressionIn;
			
			//Rufe nun parse() auf...				
			ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceParse= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
			objReturnReferenceParse.set(objEntry); 
			vecReturn = this.parseFirstVector(sExpression, objReturnReferenceParse, bKeepSurroundingSeparatorsOnParse);
			objEntry = objReturnReferenceParse.get();
			if(vecReturn==null) break main;
			
			parse:{
				//Pruefe ob der Tag enthalten ist:
				//Wenn der Tag nicht enthalten ist darf(!) nicht weitergearbeitet werden. Trotzdem sicherstellen, das isParsed()=true wird.
				if(StringZZZ.isEmpty(vecReturn.get(1).toString())) break parse;
								
				sReturnTag = VectorUtilZZZ.getElementAsValueOf(vecReturn, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.							
				sReturnLine = VectorUtilZZZ.implode(vecReturn);
						
				//Rufe nun substituteParsed() auf...	
				if(!this.isSubstituteEnabledThis()) break main;
				
				ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceSolve= new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
				objReturnReferenceSolve.set(objEntry);
				
				sReturnLine = this.substituteParsed(sReturnLine, objReturnReferenceSolve, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = this.getValue();
				objEntry = objReturnReferenceSolve.get();	
			}//end parse:
		}//end main:
				
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		this.setValue(sReturnTag);
		sReturn = sReturnLine;
		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);
			if(sExpressionIn!=null) {								 						
				if(!sExpressionIn.equals(sReturnLine)) {
					objEntry.isPathSubstitutedChanged(true);
				}
			}			
			this.adoptEntryValuesMissing(objEntry);
		}		
		return sReturn;
		
//		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
//		if(vecReturn!=null && sReturnLine!=null) vecReturn.replace(sReturnLine);
//			
//		if(objEntry!=null) {	
//			if(!bUseExpression) {
//				objEntry.setValue(sReturn);
//			}else {
//				if(vecReturn!=null) sReturn = VectorUtilZZZ.implode(vecReturn);
//				objEntry.isSubstituted(true);
//				objEntry.setValue(sReturn);
//				if(sExpressionIn!=null) {
//					if(!sExpressionIn.equals(sReturn)) {					
//						objEntry.isPathSubstitutedChanged(true);
//					}
//					
//				}			
//				if(objReturnReferenceIn!=null)objReturnReferenceIn.set(objEntry);//Wichtig: Reference nach aussen zurueckgeben.
//				this.adoptEntryValuesMissing(objEntry);
//			}
//		}
//		return sReturn;	
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++		
	@Override
	public String substituteParsedPost(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.substituteParsedPost_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String substituteParsedPost(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.substituteParsedPost_(sExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
	}
	
	private String substituteParsedPost_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;		
		boolean bUseExpression = false; boolean bUseParse = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}							
		
		main:{			
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			sReturnLine = sExpressionIn;
			this.setRaw(sReturnLine);
			objEntry.setRaw(sReturnLine);
			objEntry.setValueAsExpression(sReturnLine, false);
			sReturn = sReturnLine;
			
			sReturnTag = this.getValue();
									
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
										
			//... hier koennte noch etwas passieren
							
			//ggfs. weitere Sachen aus dem Tag oder dem Return rausrechnen, falls gewuenscht				
			sReturn = this.substituteParsedPostCustom(sReturn, objReturnReference, bKeepSurroundingSeparatorsOnParse);				
							
		}//end main:
							
		//#################################							
		return sReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public String substituteParsedPostCustom(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		return this.substituteParsedPostCustom_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String substituteParsedPostCustom(String sExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		return this.substituteParsedPostCustom_(sExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
	}
	
	//Methode mit Reference Objekt
	private String substituteParsedPostCustom_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {		
		String sReturn = null; String sReturnLine = null; String sReturnTag = null;		
		boolean bUseExpression = false; boolean bUseParse = false;
		
		IKernelConfigSectionEntryZZZ objEntry = null;
		ReferenceZZZ<IKernelConfigSectionEntryZZZ>objReturnReference = null;
		if(objReturnReferenceIn==null) {				
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();								
		}else {
			objReturnReference = objReturnReferenceIn;
			objEntry = objReturnReference.get();
		}
		if(objEntry==null) {
			//ZWAR: Das Ziel ist es moeglichst viel Informationen aus dem entry "zu retten"      =  this.parseAsEntryNew(sExpression);  //nein, dann gehen alle Informationen verloren   objReturn = this.parseAsEntryNew(sExpression);
			objEntry = new KernelConfigSectionEntryZZZ<T>();   //nicht den eigenen Tag uebergeben, das ist der Entry der ganzen Zeile!
			objReturnReference.set(objEntry);
		}							
		
		main:{		
			if(StringZZZ.isEmpty(sExpressionIn)) break main;
			
			sReturnLine = sExpressionIn;
			this.setRaw(sReturnLine);
			objEntry.setRaw(sReturnLine);
			sReturn = sReturnLine;
			
			sReturnTag = this.getValue();
										
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;
			
			bUseParse = this.isParserEnabledThis();
			if(!bUseParse) break main;
		
		
			//.... hier könnte dann ein echter custom Code in einer Klasse stehen.
			
		}//end main:
				
		//#################################
		
		if(objEntry!=null) {				
			objEntry.setValue(sReturn);	
		}
		return sReturn;
	}

	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String substituteParsed(String sExpression) throws ExceptionZZZ {
		return this.substituteParsed_(sExpression, null, true);
	}
	
	@Override
	public String substituteParsed(String sExpression,ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {		
		return this.substituteParsed_(sExpression, objReturnReference, true);
	}
	
	@Override
	public String substituteParsed(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse)	throws ExceptionZZZ {
		return this.substituteParsed_(sExpressionIn, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
	}

	@Override
	public String substituteParsed(Vector3ZZZ<String> vecExpression) throws ExceptionZZZ {
		return this.substituteParsed_(vecExpression, null, true);
	}

	@Override
	public String substituteParsed(Vector3ZZZ<String> vecExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.substituteParsed_(vecExpression, null, bKeepSurroundingSeparatorsOnParse);
	}
	
	@Override
	public String substituteParsed(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.substituteParsed_(vecExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
	}
	
	@Override
	public String substituteParsed(String sExpression, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		return this.substituteParsed_(sExpression, null, bKeepSurroundingSeparatorsOnParse);
	}

	/* Aufloesen der INI-Pfade und Variablen. */
	private String substituteParsed_(String sExpressionIn, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse)	throws ExceptionZZZ {
		String sReturn = null; String sReturnTag = null; String sReturnLine = null;
		boolean bUseExpression=false; boolean bUseParse=false; boolean bUseSubstitute = false;
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference= null;		
		IKernelConfigSectionEntryZZZ objEntry = null;
		if(objReturnReferenceIn==null) {
			objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		}else {
			objReturnReference = objReturnReferenceIn;
		}
		objEntry = objReturnReference.get();
		if(objEntry==null) {
			//Nein, das holt auch ein neues inneres Objekt und die teilen sich dann die Referenz... objEntry = this.getEntryNew(); //Hier schon die Rückgabe vorbereiten, falls eine weitere Verarbeitung nicht konfiguriert ist.
			 //Wichtig: Als oberste Methode immer ein neues Entry-Objekt holen. Dann stellt man sicher, das nicht mit Werten der vorherigen Suche gearbeitet wird.
			objEntry = new KernelConfigSectionEntryZZZ<T>();
			objReturnReference.set(objEntry);
		}//Achtung: Das objReturn Objekt NICHT generell uebernehmen. Es verfaelscht bei einem 2. Suchaufruf das Ergebnis.
		
		this.updateValueSubstituteCalled();
		this.updateValueSubstituteCalled(objReturnReference);	
		
		sReturnLine = sExpressionIn;
		this.setRaw(sReturnLine);
		objEntry.setRaw(sReturnLine);	
		sReturn = sReturnLine;	
						
		sReturnTag = this.getValue();
		
		main:{						
			if(StringZZZ.isEmptyTrimmed(sExpressionIn)) break main;			
			String sExpression = sExpressionIn;
			
			bUseExpression = this.isExpressionEnabledGeneral(); 
			if(!bUseExpression) break main;					
						
			bUseParse = this.isParserEnabledCustom(); //.isParserEnabledThis();
			if(!bUseParse) break main;
			
			bUseSubstitute = this.isSubstituteEnabledThis();
			if(!bUseSubstitute) break main;

			//objEntry.isVariableSubstituteCalled(true);	
			this.updateValueVariableSubstituteCalled();
			this.updateValueVariableSubstituteCalled(objReturnReference);
			if(this.isSubstituteVariableEnabledThis()) {
									
			//Pruefe vorher ob ueberhaupt eine Variable in der Expression definiert ist
			if(ExpressionIniUtilZZZ.isExpression(sExpression, ZTagFormulaIni_VariableZZZ.sTAG_NAME, false)) {
									
				//ZUERST: Löse ggfs. übergebene Variablen auf.
				//!!! WICHTIG: BEI DIESEN AUFLOESUNGEN NICHT DAS UEBERGEORNETE OBJENTRY VERWENDEN, SONDERN INTERN EIN EIGENES!!! 
										
				//Merke: Fuer einfache Tag gibt es keine zu verarbeitenden Flags, also muss man auch keine suchen und uebergeben.
				//       Hier aber ein 
				String sExpressionOld = sExpression;
				String sExpressionTemp;					
					
				ZTagFormulaIni_VariableZZZ<T> exDummy = new ZTagFormulaIni_VariableZZZ<T>();
				String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
						
				ZTagFormulaIni_VariableZZZ<T> objVariable = new ZTagFormulaIni_VariableZZZ<T>(this.getHashMapVariable(), saFlagZpassed); 
				while(objVariable.isExpression(sExpression)){
											
					//Auf gar keinen Fall den Z-Tag entfernen, sonst können nachfolgende PATH - Anweisungen nicht mehr gefunden werden.
					//Merke: Weil es ein SimpleTag ist, kann man keine objReference uebergeben. 
					Vector3ZZZ<String> vecExpressionTemp =  objVariable.parseFirstVector(sExpression, false);
					if(vecExpressionTemp==null) break;
					if(StringZZZ.isEmpty((String)vecExpressionTemp.get(1))) break; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.	
					
					//sExpressionTemp = (String) vecExpressionTemp.get(1);
					sExpressionTemp = VectorUtilZZZ.getElementAsValueOf(vecExpressionTemp, 1);//Damit wird aus dem NullObjectZZZ ggfs. NULL als Wert geholt.
					if(sExpression.equals(sExpressionTemp)) {
						break;
					}else{
						sExpression = VectorUtilZZZ.implode(vecExpressionTemp);					
					}
				} //end while
				sReturnTag = sExpression; //!!! 20250205: Das hat aber noch die TagName-Werte drin.. Er wird dann in parseFirstVectorPost rausgerechnet
				objVariable.setValue(sReturnTag);//Das braucht noch nicht der endgueltige TAG-Wert sein,da ggfs. noch der TAG-Selbst drum ist.
					
				sReturnLine = sExpression;
				objEntry.setValue(sReturnLine);
				objEntry.setValueFromTag(sReturnTag);
																					
				if(!sExpressionOld.equals(sReturnLine)) {							
					this.updateValueVariableSubstitutedChanged();
					this.updateValueVariableSubstitutedChanged(objReturnReference);	
				}							
			}//end if .isParse(..)
			//objEntry.isVariableSubstituted(true);
			this.updateValueVariableSubstituted();
			this.updateValueVariableSubstituted(objReturnReference);
		}	
						
		//objEntry.isPathSubstituteCalled(true);
		this.updateValuePathSubstituteCalled();
		this.updateValuePathSubstituteCalled(objReturnReference);
		if(this.isSubstitutePathEnabledThis()){											
				//Pruefe vorher ob ueberhaupt eine Variable in der Expression definiert ist
				if(ExpressionIniUtilZZZ.isExpressionRegEx(sExpression, KernelZFormulaIni_PathZZZ.sTAG_NAME, false)) {
										
					//DANACH: ALLE PATH-Ausdrücke, also [xxx]yyy ersetzen
					//Problem hier: [ ] ist auch der JSON Array-Ausdruck
					String sExpressionOld = sExpression;
					String sExpressionTemp;
					
					KernelZFormulaIni_PathZZZ<T> exDummy = new KernelZFormulaIni_PathZZZ<T>();
					String[] saFlagZpassed = FlagZFassadeZZZ.seekFlagZrelevantForObject(this, exDummy, true); //this.getFlagZ_passable(true, exDummy);
									
					KernelZFormulaIni_PathZZZ<T> objFormulaIniPath = new KernelZFormulaIni_PathZZZ<T>(this.getKernelObject(), this.getFileConfigKernelIni(), saFlagZpassed);
					while(objFormulaIniPath.isExpression(sExpression)){
						//Problem [ ] aus dem JSON:ARRAY (Z.B.: <Z><JSON><JSON:ARRAY>["TESTWERT2DO2JSON01","TESTWERT2DO2JSON02"]</JSON:ARRAY></JSON></Z> )
				        //sind auch Bestandteile des INI-Pfads (z.B. [SECTION]Property )
						//Der INI-Pfad wird per RegEx-Ausdruck definiert.
						//IDEE: Der RegEx-Ausdruck muss Hochkommata zwischen den [ ] ausschliessen!
				   														
						//Merke: Es ist nicht nur ein simpleTag, dann kann man auch eine objReturnReference uebergeben.
						Vector3ZZZ<String> vecExpressionTemp = objFormulaIniPath.parseFirstVector(sExpression, objReturnReference, false); //20250205: Die Aufloesung der PATH-Anweisung darf jetzt den Z-Tag nicht entfernen. Das macht dann später der SOLVER.
						if(vecExpressionTemp==null) break;
						if(StringZZZ.isEmpty(VectorUtilZZZ.getElementAsValueOf(vecExpressionTemp, 1))) break; //Dann ist der Tag nicht enthalten und es darf(!) nicht weitergearbeitet werden.
						
						sExpressionTemp = VectorUtilZZZ.implode(vecExpressionTemp);	
						if(sExpression.equals(sExpressionTemp)) {
							break;
						}else{
							sExpression = sExpressionTemp;						
						}											
					} //end while						
					//TODOGOON: Der ganze String ist eigentlich nicht der PATH-Tag, ergo sExpressionTagTemp = vecExpressionTemp.get(1) oben verwenden.
					sReturnTag = sExpression; //!!! 20250205: Das hat aber noch die TagName-Werte drin.. Er wird dann in parseFirstVectorPost rausgerechnet
					objFormulaIniPath.setValue(sReturnTag);//Das braucht noch nicht der endgueltige TAG-Wert sein,da ggfs. noch der TAG-Selbst drum ist.
						
					sReturnLine = sExpression;
					objEntry.setValue(sReturnLine);
					objEntry.setValueFromTag(sReturnTag);
					this.setValue(sReturnTag);																	
					if(!sExpressionOld.equals(sReturnLine)) {							
						//objEntry.isPathSubstitutedChanged(true);
						this.updateValuePathSubstitutedChanged();
						this.updateValuePathSubstitutedChanged(objReturnReference);
					}										
				}//end if .isParseRegEx();
				//objEntry.isPathSubstituted(true);
				this.updateValuePathSubstituted();
				this.updateValuePathSubstituted(objReturnReference);
			}//end if .getFlag(..USE_...Path...)
					
			if(objEntry.isPathSubstituted() | objEntry.isVariableSubstituted()) {
				sExpression = sReturnLine;
				sReturnLine = this.substituteParsedPost(sExpression, objReturnReference, bKeepSurroundingSeparatorsOnParse);
				sReturnTag = this.getValue();
				objEntry = objReturnReference.get();			
			}									
			//Merke: Weitere Aufloesung bedarf das explizite solver-Flag
			//if(!this.getFlag(IKernelExpressionIniSolverZZZ.FLAGZ.USEEXPRESSION_SOLVER)) break main;
						
			this.updateValueSubstituted();
			this.updateValueSubstituted(objReturnReference);
		}//end main:
		this.setValue(sReturnTag);			
		sReturn = sReturnLine;
					
		//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen
		//Der Wert ist nur der TagInhalt this.setValue(sReturn);		
		if(objEntry!=null) {
			objEntry.setValue(sReturnLine);
			objEntry.setValueFromTag(sReturnTag);
			if(objReturnReference!=null)objReturnReferenceIn.set(objEntry);
			if(objEntry.isPathSubstituted() | objEntry.isVariableSubstituted()) {
				this.updateValueSubstituted(objReturnReference);
			}
			if(objEntry.isPathSubstitutedChanged() | objEntry.isVariableSubstitutedChanged()) {
				this.updateValueSubstitutedChanged(objReturnReference);				
			}				
			this.adoptEntryValuesMissing(objEntry);			
		}
		return sReturn;	
	}
	
	private String substituteParsed_(Vector3ZZZ<String> vecExpression, ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReferenceIn, boolean bKeepSurroundingSeparatorsOnParse) throws ExceptionZZZ {
		String sReturn = "";
		String sReturnSubstituted = null;
		main:{
			if(vecExpression==null) break main;
			
			String sExpression = (String) vecExpression.get(1);
			sReturnSubstituted = this.substituteParsed(sExpression, objReturnReferenceIn, bKeepSurroundingSeparatorsOnParse);
							
			//NUN DEN INNERHALB DER EXPRESSION BERECHUNG ERSTELLTEN WERT uebernehmen		
			vecExpression.replace(sReturnSubstituted);			
			sReturn = VectorUtilZZZ.implode(vecExpression);							
		}//end main:
		return sReturn;
	}
	
	
	//### Aus ISubstituteUserZZZ
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override 
	public void addHistorySubstituteCalled() throws ExceptionZZZ{
		String sTagName = this.getName();
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistorySubstituteCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySubstituteCalled(String sTagName) throws ExceptionZZZ{
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		this.addHistorySubstituteCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySubstituteCalled(IKernelConfigSectionEntryZZZ objEntry) throws ExceptionZZZ{
		String sTagName = this.getName();
		this.addHistorySubstituteCalled(objEntry, sTagName);
	}
	
	@Override
	public void addHistorySubstituteCalled(IKernelConfigSectionEntryZZZ objEntry, String sTagName) throws ExceptionZZZ{
		objEntry.setHistorySubstituteCalled(sTagName);
	}
		
	//#######################################
	@Override
	public void updateValueSubstituteCustom(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference,String sExpression) throws ExceptionZZZ {
		// TODO Auto-generated method stub
		
	}
	//++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueSubstituteCalled() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValueSubstituteCalled(boolean bIsSubstituteCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSubstituteCalled(objReturnReference, bIsSubstituteCalled);
	}


	@Override
	public void updateValueSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValueSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSubstituteCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isSubstituteCalled(bIsSubstituteCalled);
			
		//Die "echte" Feststellung welcher Tag aufgerufen wird. Ggfs. kann man daraus die Reihenfolge ablesen.
		this.addHistorySubstituteCalled(objEntry);
	}
	
	//+++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueSubstituted() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
		if(this.isParserEnabledGeneral()) {
			this.updateValueSubstituted(objReturnReference, true);
		}
	}


	@Override
	public void updateValueSubstituted(boolean bIsParsed) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSubstituted(objReturnReference, true);
	}


	@Override
	public void updateValueSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueSubstituted(objReturnReference, true);		
	}


	@Override
	public void updateValueSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsed) throws ExceptionZZZ {
		main:{
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			if(!this.isExpressionEnabledGeneral()) break main;
				
			//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
			if(this.isParserEnabledGeneral()) {
				objEntry.isSubstituted(bIsParsed);
			}
		}//end main:
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueSubstitutedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueSubstitutedChanged(boolean bIsParsedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueSubstitutedChanged(objReturnReference, bIsParsedChanged);
	}


	@Override
	public void updateValueSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsedChanged)	throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isSubstitutedChanged(bIsParsedChanged);
	}
		

	//+++++++++++++++++++++++++++++++++++
	//###################################
		
	//##############################################################
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueVariableSubstituteCalled() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueVariableSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValueVariableSubstituteCalled(boolean bIsSubstituteCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueVariableSubstituteCalled(objReturnReference, bIsSubstituteCalled);
	}


	@Override
	public void updateValueVariableSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueVariableSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValueVariableSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParseCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isVariableSubstituteCalled(bIsParseCalled);
			
		//Die "echte" Feststellung welcher Tag aufgerufen wird. Ggfs. kann man daraus die Reihenfolge ablesen.
		this.addHistorySubstituteCalled(objEntry);
	}

	//+++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueVariableSubstituted() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
		if(this.isParserEnabledGeneral()) {
			this.updateValueVariableSubstituted(objReturnReference, true);
		}
	}


	@Override
	public void updateValueVariableSubstituted(boolean bIsParsed) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueVariableSubstituted(objReturnReference, true);
	}


	@Override
	public void updateValueVariableSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueVariableSubstituted(objReturnReference, true);		
	}


	@Override
	public void updateValueVariableSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsSubstituted) throws ExceptionZZZ {
		main:{
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			if(!this.isExpressionEnabledGeneral()) break main;
				
			//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
			if(this.isParserEnabledGeneral()) {
				objEntry.isVariableSubstituted(bIsSubstituted);
			}
		}//end main:
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValueVariableSubstitutedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueVariableSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueVariableSubstitutedChanged(boolean bIsParsedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValueVariableSubstitutedChanged(objReturnReference, bIsParsedChanged);
	}


	@Override
	public void updateValueVariableSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValueVariableSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValueVariableSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsedChanged)	throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isVariableSubstitutedChanged(bIsParsedChanged);
	}
	
	//+++++++++++++++++++++++++++++++++++++
	//#####################################
	
	//##############################################################
    //++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValuePathSubstituteCalled() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValuePathSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValuePathSubstituteCalled(boolean bIsPathSubstituteCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValuePathSubstituteCalled(objReturnReference, bIsPathSubstituteCalled);
	}


	@Override
	public void updateValuePathSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValuePathSubstituteCalled(objReturnReference, true);
	}


	@Override
	public void updateValuePathSubstituteCalled(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParseCalled) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isPathSubstituteCalled(bIsParseCalled);
			
		//Die "echte" Feststellung welcher Tag aufgerufen wird. Ggfs. kann man daraus die Reihenfolge ablesen.
		this.addHistorySubstituteCalled(objEntry);
	}

	//+++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValuePathSubstituted() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
		if(this.isParserEnabledGeneral()) {
			this.updateValuePathSubstituted(objReturnReference, true);
		}
	}


	@Override
	public void updateValuePathSubstituted(boolean bIsPathSubstituted) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValuePathSubstituted(objReturnReference, true);
	}


	@Override
	public void updateValuePathSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValuePathSubstituted(objReturnReference, true);		
	}


	@Override
	public void updateValuePathSubstituted(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsed) throws ExceptionZZZ {
		main:{
			IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
			if(!this.isExpressionEnabledGeneral()) break main;
				
			//if(this.isParserEnabledThis()) { //Beim Solven gilt auch, das nur das allgemeine Abstellen des Solves den Wert nicht setzt.
			if(this.isParserEnabledGeneral()) {
				objEntry.isPathSubstituted(bIsParsed);
			}
		}//end main:
	}

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public void updateValuePathSubstitutedChanged() throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValuePathSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValuePathSubstitutedChanged(boolean bIsPathSubstitutedChanged) throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = this.getEntry();
		
		ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference = new ReferenceZZZ<IKernelConfigSectionEntryZZZ>();
		objReturnReference.set(objEntry);
		this.updateValuePathSubstitutedChanged(objReturnReference, bIsPathSubstitutedChanged);
	}


	@Override
	public void updateValuePathSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference) throws ExceptionZZZ {
		this.updateValuePathSubstitutedChanged(objReturnReference, true);
	}


	@Override
	public void updateValuePathSubstitutedChanged(ReferenceZZZ<IKernelConfigSectionEntryZZZ> objReturnReference, boolean bIsParsedChanged)	throws ExceptionZZZ {
		IKernelConfigSectionEntryZZZ objEntry = objReturnReference.get();
		objEntry.isPathSubstitutedChanged(bIsParsedChanged);
	}
	
	//+++++++++++++++++++++++++++++++++++++
	//#####################################
	
	
	//### Aus IValueVariableUserZZZ
	@Override
	public void setHashMapVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{				
		this.hmVariable = hmVariable;
	}
	
	@Override
	public HashMapCaseInsensitiveZZZ<String,String> getHashMapVariable() throws ExceptionZZZ{
		if(this.hmVariable==null) {
			this.hmVariable = new HashMapCaseInsensitiveZZZ<String, String>();
		}
		return this.hmVariable;
	}
	
	@Override
	public void setVariable(HashMapCaseInsensitiveZZZ<String,String> hmVariable) throws ExceptionZZZ{
		if(this.hmVariable==null){
			this.hmVariable = hmVariable;
		}else{
			if(hmVariable==null){
				//nix....
			}else{
				//füge Werte hinzu.
				Set<String> sSet =  hmVariable.keySet();
				for(String sKey : sSet){
					this.hmVariable.put(sKey, (String)hmVariable.get(sKey));
				}
			}
		}	
	}
	
	@Override
	public void setVariable(String sVariable, String sValue) throws ExceptionZZZ{
		this.getHashMapVariable().put(sVariable, sValue);
	}
	
	@Override
	public String getVariable(String sKey) throws ExceptionZZZ{
		return (String) this.getHashMapVariable().get(sKey);
	}
	
	//###################################
	//### FLAG Handling
	@Override
	public boolean getFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
		return this.getFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	@Override
	public boolean setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ.name(), bFlagValue);
	}
			
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_VariableZZZ.FLAGZ[] objaEnum_IKernelZFormulaIni_VariableZZZ, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnum_IKernelZFormulaIni_VariableZZZ)) {
				baReturn = new boolean[objaEnum_IKernelZFormulaIni_VariableZZZ.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ:objaEnum_IKernelZFormulaIni_VariableZZZ) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnum_IKernelZFormulaIni_VariableZZZ, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
		return this.proofFlagExists(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_VariableZZZ.FLAGZ objEnum_IKernelZFormulaIni_VariableZZZ) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnum_IKernelZFormulaIni_VariableZZZ.name());
	}
	
	//### aus IKernelZFormulaIni_PathZZZ	
	@Override
	public boolean getFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	@Override
	public boolean setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IKernelZFormulaIni_PathZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}
	
	@Override
	public boolean proofFlagExists(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
	}
	
	@Override
	public boolean proofFlagSetBefore(IKernelZFormulaIni_PathZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagSetBefore(objEnumFlag.name());
	}
}// End class