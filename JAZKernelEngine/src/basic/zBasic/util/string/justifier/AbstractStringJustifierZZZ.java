package basic.zBasic.util.string.justifier;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;
import basic.zBasic.util.string.formater.StringFormaterUtilZZZ;

public abstract class AbstractStringJustifierZZZ extends AbstractObjectWithExceptionZZZ implements IStringJustifierZZZ {
	private static final long serialVersionUID = 1931006668388552859L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	//protected static IStringJustifierZZZ objStringJustifierINSTANCE;

	
	//Zum Buendig machen
	protected volatile String sPositionSeparator = null;	  //Ggfs. individuelle Positions-Trenner Zeichen.
	protected volatile String sPositionIdentifier = null;     //Merke: Identifier und Separator sind voneinander abhaengig.
	protected volatile int iLineMarkerIndexLeftBehind=-1;             //Die aktuelle linke Grenze, an der der InfoPart beginnt.
	
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected AbstractStringJustifierZZZ() throws ExceptionZZZ{
		super();
	}
	
	//##########################################################
	//### METHODEN ##########
	@Override
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(this.iLineMarkerIndexLeftBehind==-1) break main;
			this.iLineMarkerIndexLeftBehind=-1;
					
			bReturn = true;
		}//end main:
		return bReturn;
		
	}
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	@Override
	public abstract String getPositionSeparatorDefault() throws ExceptionZZZ;
	
	@Override
	public String getPositionSeparator() throws ExceptionZZZ {
		if(this.sPositionSeparator==null) {
			this.sPositionSeparator = this.getPositionSeparatorDefault();
		}
		return this.sPositionSeparator;
	}
	
	@Override
	public void setPositionSeparator(String sPositionSeparator) throws ExceptionZZZ {
		this.sPositionSeparator = sPositionSeparator;
	}
	
	//+++++++++++++++++++++++++++++++++++++++
	@Override
	public abstract String getPositionIdentifierDefault() throws ExceptionZZZ; 
	
	@Override
	public String getPositionIdentifier() throws ExceptionZZZ {
		if(this.sPositionIdentifier==null) {
			this.sPositionIdentifier = this.getPositionIdentifierDefault();
		}
		return this.sPositionIdentifier;
	}
	
	@Override
	public void setPositionIdentifier(String sPositionIdentifier) throws ExceptionZZZ {
		this.sPositionIdentifier = sPositionIdentifier;
	}
    //+++++++++++++++++++++++++++++++++
	
	@Override
	public synchronized int getLineMarkerIndexLeftBehindCurrent() throws ExceptionZZZ {
		return this.iLineMarkerIndexLeftBehind;
	}
	
	@Override
	public synchronized void setLineMarkerIndexLeftBehindCurrent(int iIndex) throws ExceptionZZZ {
		this.iLineMarkerIndexLeftBehind = iIndex;
	}
	
	@Override
	public void setLineMarkerIndexLeftBehindIncreased(int iIndexMayIncrease) throws ExceptionZZZ {
		main:{
			if(iIndexMayIncrease<=0) break main;
			
			//hole die bisherige, aktuelle Grenze
			int iIndexCurrent = this.getLineMarkerIndexLeftBehindCurrent();
			
			//Keine Differenz vorhanden			
			if(iIndexMayIncrease == iIndexCurrent) break main;				
			
			//Differenz vorhanden und groesser.
			//Merke: Nun zum Schluss, da hier iBoundLeftBehind neu gesetzt wird			
			if(iIndexMayIncrease > iIndexCurrent) { 
				this.setLineMarkerIndexLeftBehindCurrent(iIndexMayIncrease);
				break main;
			}
		}//end main:
	}
	
				
	
		
	
	@Override 
	public int computeLineMarkerIndexLeftBehind(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = AbstractStringJustifierZZZ.computeLineMarkerIndexLeftBehindStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override 
	public int computeLineMarkerIndexLeft(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			String sPositionSeparator = this.getPositionIdentifier();
			iReturn = AbstractStringJustifierZZZ.computeLineMarkerIndexLeftStatic(sLog, sPositionSeparator);			
		}//end main:
		return iReturn;
	}
	
	@Override
	public int computeLineMarkerIndexLeftBehind2use(String sLog) throws ExceptionZZZ {
		int iReturn = -1;
		main:{
			if(StringZZZ.isEmpty(sLog)) break main;
			
			int itemp = this.computeLineMarkerIndexLeftBehind(sLog);
			iReturn = this.getLineMarkerIndexLeftBehindCurrent(); 
			if(itemp>iReturn) {
				//nein, nicht setzen. Das justify überlassen this.setInfoPartBoundLeftCurrent(itemp);
				iReturn = itemp;
			}			
		}//end main:
		return iReturn;
	}
	
	@Override
	public synchronized String justifyLine(String sLine) throws ExceptionZZZ {
		String sReturn = sLine;
		main:{
			if(StringZZZ.isEmpty(sLine)) break main;

			//ermittle die Grenze aus dem Logstring
			int iLineMarkerIndexLeftBehind = this.computeLineMarkerIndexLeftBehind(sLine);
			if(iLineMarkerIndexLeftBehind<=-1) break main; //Dann gibt es diese Grenzmarkierung noch nicht, z.B. beim anfänglichen Datumsstring.

			//erhoehte ggfs. die Grenze
			this.setLineMarkerIndexLeftBehindIncreased(iLineMarkerIndexLeftBehind);
			
			//hole die nun aktuelle Grenze, die es zu verwenden gilt
			int iLineMarkerIndexLeftBehindToUse = this.getLineMarkerIndexLeftBehindCurrent();
						
			//Differenz vorhanden und kleiner
			//Merke: Nun aufsplitten und auffuellen
			if(iLineMarkerIndexLeftBehind < iLineMarkerIndexLeftBehindToUse) {

				int iIndexLeft = this.computeLineMarkerIndexLeft(sLine);
				int iDifference = iLineMarkerIndexLeftBehindToUse - iLineMarkerIndexLeftBehind;
				
				if(iDifference>=1) {
					String sLeft = StringZZZ.left(sLine, iIndexLeft);
					String sRight = StringZZZ.rightback(sLine, iIndexLeft);
					String sMid = StringZZZ.repeat(" ", iDifference);
								
					//wieder zusamensetzen
					sReturn = sLeft + sMid + sRight;// + "-DEBUG02a: [" + ReflectCodeZZZ.getPositionCurrent() + "] getInfoPartBoundLeftBehindCurrent="+ iBoundLeftBehindCurrent ;
				}else {
					break main;
				}				
			}//end if(iBoundLeftBehind < iBoundLeftBehindCurrent) {	
		}//end main:
		return sReturn;
	}
		
	@Override
	public synchronized ArrayListZZZ<String> justifyLine(ArrayListZZZ<String> listasLine) throws ExceptionZZZ {
		ArrayListZZZ<String> listasReturn = null;
		main:{
			if(listasLine==null) break main;
			
			listasReturn=new ArrayListZZZ<String>();			
			if(ArrayListUtilZZZ.isEmpty(listasLine)) break main;
			
			for(String sLine : listasLine) {
				String sLineJustified = this.justifyLine(sLine);
				listasReturn.add(sLineJustified);
			}
				
		}//end main:
		return listasReturn;
	}

	
	//### STATIC METHODEN
	public static int computeLineMarkerIndexLeftStatic(String sLog, String sPositionSeparator) throws ExceptionZZZ {
		return StringZZZ.indexOfFirst(sLog, sPositionSeparator);				
	}
	
	public static int computeLineMarkerIndexLeftBehindStatic(String sLog, String sPositionSeparator) throws ExceptionZZZ {
		return StringZZZ.indexOfFirstBefore(sLog, sPositionSeparator);				
	}

	
		
}
