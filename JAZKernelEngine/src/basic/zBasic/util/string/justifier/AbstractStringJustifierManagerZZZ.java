package basic.zBasic.util.string.justifier;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUniqueZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IEnumSetMappedStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class AbstractStringJustifierManagerZZZ extends AbstractObjectWithFlagZZZ implements IStringJustifierManagerZZZ{
	private static final long serialVersionUID = 7094594085787231868L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	private static final boolean INITIALIZED = true;// Trick, um Mehrfachinstanzen zu verhindern (optional)
	
	
	// --- Globale Objekte ---
	//Zum Buendig machen
	//Idee: Bei mehreren Instanzen per Default auf das Singleton zugreifen
	//      Aber ggfs. ueberschreibend auf einen hinterlegten zugreifen.
	//Merke: XML-Strings werden nicht buendig gemacht. 
	//       Darum wird der StringJustifier nicht mehr in dieser Elternklasse im Konstruktor an den "Formater" uebergeben.
	private volatile ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = null;
	
	//Die liste der verwendeten Justifier, nach der Filterung
	private volatile ArrayListUniqueZZZ<IStringJustifierZZZ> listaStringJustifierUsed = null;

	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringJustifierManagerZZZ() throws ExceptionZZZ{
		super();
	}
	
	//############################################################################
	//### GETTER / SETTER
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ>getStringJustifierListDefault() throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn = new ArrayListZZZ<IStringJustifierZZZ>();
		
		
		Separator01StringJustifierZZZ objJustifier01 = (Separator01StringJustifierZZZ) Separator01StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier01);

		Separator02StringJustifierZZZ objJustifier02 = (Separator02StringJustifierZZZ) Separator02StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier02);

		Separator03StringJustifierZZZ objJustifier03 = (Separator03StringJustifierZZZ) Separator03StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier03);
		
		Separator04StringJustifierZZZ objJustifier04 = (Separator04StringJustifierZZZ) Separator04StringJustifierZZZ.getInstance();
		listaReturn.add(objJustifier04);
		
		SeparatorFilePositionJustifierZZZ objJustifierFilePosition = (SeparatorFilePositionJustifierZZZ) SeparatorFilePositionJustifierZZZ.getInstance();
		listaReturn.add(objJustifierFilePosition);
				
		SeparatorMessageStringJustifierZZZ objJustifierMessage = (SeparatorMessageStringJustifierZZZ) SeparatorMessageStringJustifierZZZ.getInstance();
		listaReturn.add(objJustifierMessage);
		
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ>getStringJustifierList() throws ExceptionZZZ{
		if(this.listaStringJustifier==null) {
			this.listaStringJustifier = this.getStringJustifierListDefault();			
		}
		return this.listaStringJustifier;
	}
	
	@Override
	public void setStringJustifierList(ArrayListZZZ listaStringJustifier) throws ExceptionZZZ{
		this.listaStringJustifier = listaStringJustifier;
	}
	
	@Override
	public boolean resetStringJustifierList() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getStringJustifierList().size()>=1) {
				bReturn = true;
			}
			this.getStringJustifierList().clear();	
		}//end main:
		return bReturn;
	}
	
	//++++++++++++++++++++
	@Override
	public ArrayListUniqueZZZ<IStringJustifierZZZ>getStringJustifierListUsed() throws ExceptionZZZ{
		if(this.listaStringJustifierUsed==null) {
			this.listaStringJustifierUsed = new ArrayListUniqueZZZ<IStringJustifierZZZ>();
		}
		return this.listaStringJustifierUsed;
	}
	
	@Override
	public void setStringJustifierListUsed(ArrayListUniqueZZZ<IStringJustifierZZZ> listaStringJustifier) throws ExceptionZZZ{
		this.listaStringJustifierUsed = listaStringJustifier;
	}
	
	@Override
	public boolean resetStringJustifierListUsed() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(this.getStringJustifierListUsed().size()>=1) {
				bReturn = true;
			}
			this.getStringJustifierListUsed().clear();	
		}//end main:
		return bReturn;
	}
	
	//+++++++++++++++++++++++++
	//Die optimale Reihenfolge der Justifier orientiert sich an der Reihenfolge der Formatanweisungen oder der Reihenfolge der Zeichen im String
	//Bei einer nicht optimalen Reihenfolge sind die vorderen Spalten ggfs. zu breit.
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierListFiltered(listaStringJustifierDefault, ienumaFormatLogString);
		}//end main:
		return listaReturn;
	}
	
	@Override
	public ArrayListZZZ<IStringJustifierZZZ> getStringJustifierListFiltered(LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ {
		ArrayListZZZ<IStringJustifierZZZ> listaReturn=new ArrayListZZZ<IStringJustifierZZZ>();
		main:{
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifierDefault = this.getStringJustifierListDefault();			
			listaReturn = StringJustifierManagerUtilZZZ.getStringJustifierList(listaStringJustifierDefault, hm);
		}//end main:
		return listaReturn;
	}
	
	
	//#############################################
	@Override
	public IStringJustifierZZZ getStringJustifierDefault() throws ExceptionZZZ{
		//Verwende als default das Singleton vom MessageStringJustifier
		return SeparatorMessageStringJustifierZZZ.getInstance();
	}
	
	@Override
	public IStringJustifierZZZ getStringJustifier(int iIndex) throws ExceptionZZZ {
		if(!this.hasStringJustifier(iIndex)) {
			//Das Problem ist, so wird fuer jeden Indexwert immer etwas erstellt, das darf nicht sein... eigentlich eine Endlosschleife
			//Verwende als default das Singleton
			//IStringJustifierZZZ objJustifier = this.getStringJustifierDefault();
			//this.addStringJustifier(objJustifier);			
			return null;
		}else {
			//Verwende als "manual override" den einmal hinterlegten StringJustifier.
			return this.getStringJustifierList().get(iIndex);
		}
	}

	@Override
	public void addStringJustifier(IStringJustifierZZZ objStringJustifier) throws ExceptionZZZ {
		this.getStringJustifierList().add(objStringJustifier);
	}

	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean hasStringJustifier(int iIndex) throws ExceptionZZZ{
		if(this.listaStringJustifier==null) {
			return false;
		}else {
			if(this.listaStringJustifier.size()>=iIndex+1) {
				return true;
			}else {
				return false;
			}
		}		
	}
	
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;				
		main:{
			boolean bJustiferUsed = this.resetStringJustifierListUsed();
			boolean bJustifier    = this.resetStringJustifierList();
			
			bReturn = bJustiferUsed | bJustifier; 
		}//end main:
		return bReturn;
	}
	
	//#######################################
	//### aus IStringJustifierManagerComputerZZZ
	@Override 
	public String compute(String sJagged) throws ExceptionZZZ{
		String sReturn = sJagged;
		main:{
			if(StringZZZ.isEmpty(sJagged)) break main;
			
			ArrayListZZZ<String>listasJagged = new ArrayListZZZ<String>();
			listasJagged.add(sJagged);
			
			ArrayListZZZ<String>listasReturn = compute(listasJagged);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	@Override
	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged) throws ExceptionZZZ{
		ArrayListZZZ<String>listasReturn=null;
		main:{			
			if(listasJagged==null) break main;
			listasReturn=new ArrayListZZZ<String>();
			
			String stemp;
									
			//#######################
			//Hier werden keine Formatierungsanweisungen uebergeben, aus denen dann die benoetigten Zeilentrenner berechnet werden.
			//Daher die im Manager gespeicherten Justifier verwenden.			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierList();
				
				
			//### Versuch den Infoteil ueber alle Zeilen buendig zu halten
		    //WICHTIG1: DAS ERST NACHDEM ALLE STRING-TEILE, ALLER FORMATSTYPEN ABGEARBEITET WURDEN UND ZUSAMMENGESETZT WORDEN SIND.
			//WICHTIG2: DAHER AUCH NACH DEM ENTFERNEN DER XML-TAGS NEU AUSRECHNEN
			//WICHTIG3: Man es nicht dies buendig zu halten, wenn spätere Strings länger sind.
			//		    Lösungsansatz: Beim "Justifien" eine ArrayList übergeben. Darin wird einmal hin und wieder zurück bündig gemacht.
			
			//... ggfs. aus der FilePosition entstandene Tags löschen
			ArrayListZZZ<String>listasJaggedDetaged = new ArrayListZZZ<String>();
			for(String sLog : listasJagged) {
				stemp = ReflectCodeZZZ.removePositionCurrentTagPartsFrom(sLog);
				listasJaggedDetaged.add(stemp);
			}
						
			listasReturn = listasJaggedDetaged;							
			for(int icount=0; icount<=listaStringJustifier.size()-1;icount++) {
				IStringJustifierZZZ objJustifier = listaStringJustifier.get(icount);		
				this.getStringJustifierListUsed().add(objJustifier);
				
				listasReturn = StringFormaterUtilZZZ.justifyLineArrayList(objJustifier, listasReturn);//true=fasse erste und zweite Zeile zusammen, die entstehen beim "Normieren", wenn an den Kommentartrenner aufgeteilt wird.
			}
			
		
		//########
		}//end main:
		return listasReturn;
	}

	
	
	@Override
	public ArrayListZZZ<String> compute(ArrayListZZZ<String> listasJagged, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogStringIn) throws ExceptionZZZ{
		ArrayListZZZ<String>listasReturn=null;
		main:{			
			if(listasJagged==null) break main;
			listasReturn=new ArrayListZZZ<String>();
			
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
			
			//2. Mache jede Zeile bündig, per den notwendigen Justifiern
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier; 
			ArrayListZZZ<String> listasReturnTemp;
			for(IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString : listaEnumLine) {
			
				//... Liste der Justifier der Zeile
				listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
				this.setStringJustifierList(listaStringJustifier);
								
				listasReturnTemp = this.compute(listasJagged);
				listasReturn.addAll(listasReturnTemp);
			}//end for
			
		//########
		}//end main:
		return listasReturn;
	}
	
	//##############################
	//### Justifier aus HashMap
	@Override 
	public String compute(String sJagged, LinkedHashMap<IEnumSetMappedStringFormatZZZ, String> hm) throws ExceptionZZZ{
		String sReturn = sJagged;
		main:{
			if(StringZZZ.isEmpty(sJagged)) break main;
			
			ArrayListZZZ<String>listasJagged = new ArrayListZZZ<String>();
			listasJagged.add(sJagged);
			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(hm);
			this.setStringJustifierList(listaStringJustifier);
			
			ArrayListZZZ<String>listasReturn = compute(listasJagged);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	//##############################
	//### Justifier aus Array 
	@Override 
	public String compute(String sJagged, IEnumSetMappedStringFormatZZZ[] ienumaFormatLogString) throws ExceptionZZZ{
		String sReturn = sJagged;
		main:{
			if(StringZZZ.isEmpty(sJagged)) break main;
			
			ArrayListZZZ<String>listasJagged = new ArrayListZZZ<String>();
			listasJagged.add(sJagged);
			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumaFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			ArrayListZZZ<String>listasReturn = compute(listasJagged);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	
	//###############################
	//### Justifier aus Einzelwert
	@Override 
	public String compute(String sJagged, IEnumSetMappedStringFormatZZZ ienumFormatLogString) throws ExceptionZZZ{
		String sReturn = sJagged;
		main:{
			if(StringZZZ.isEmpty(sJagged)) break main;
			
			ArrayListZZZ<String>listasJagged = new ArrayListZZZ<String>();
			listasJagged.add(sJagged);
			
			ArrayListZZZ<IStringJustifierZZZ> listaStringJustifier = this.getStringJustifierListFiltered(ienumFormatLogString);
			this.setStringJustifierList(listaStringJustifier);
			
			ArrayListZZZ<String>listasReturn = compute(listasJagged);
			sReturn = ArrayListUtilZZZ.implode(listasReturn, StringZZZ.crlf());
		}//end main:
		return sReturn;
	}
	

	//###################################################
	//### FLAG: ILogStringFormatManagerZZZ
	//###################################################
	@Override
	public boolean getFlag(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IStringJustifierManagerZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IStringJustifierManagerZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IStringJustifierManagerZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IStringJustifierManagerZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}
}
