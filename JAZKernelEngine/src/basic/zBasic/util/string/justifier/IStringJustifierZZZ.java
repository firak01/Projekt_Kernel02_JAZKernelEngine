package basic.zBasic.util.string.justifier;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormaterZZZ;
import custom.zKernel.ILogZZZ;

public interface IStringJustifierZZZ extends IStringJustifierJustifyZZZ{
	public static String sSEPARATOR_MESSAGE_DEFAULT=IStringFormatZZZ.sSEPARATOR_MESSAGE_DEFAULT;//IReflectCodeZZZ.sPOSITION_MESSAGE_SEPARATOR;
	public static String sSEPARATOR_01_DEFAULT=IStringFormatZZZ.sSEPARATOR_01_DEFAULT;
	public static String sSEPARATOR_02_DEFAULT=IStringFormatZZZ.sSEPARATOR_02_DEFAULT;
	public static String sSEPARATOR_03_DEFAULT=IStringFormatZZZ.sSEPARATOR_03_DEFAULT;
	public static String sSEPARATOR_04_DEFAULT=IStringFormatZZZ.sSEPARATOR_04_DEFAULT;
	public static String sSEPARATOR_POSITION_DEFAULT=IStringFormatZZZ.sSEPARATOR_POSITION_DEFAULT;
	
	public boolean reset() throws ExceptionZZZ;
	
	//Methoden, mit denen versucht wird die Uebersichtlichkeit der Ausgaben noch weiter zu erhöhen.
	//Beispiel:
	//Nach jeder Logausgabe wird zwischen dem Positionsteil und dem Informationsteil unterscheiden.
	//Mit Leerzeichen wird dann gearbeitet um die Ausgaben des Informationsteils möglichst buendig untereinander zu bekommen.	
	public String getPositionSeparatorDefault() throws ExceptionZZZ;
	public String getPositionSeparator() throws ExceptionZZZ;
	public void setPositionSeparator(String sPositionSeparator) throws ExceptionZZZ;

	public String getPositionIdentifierDefault() throws ExceptionZZZ;
	public String getPositionIdentifier() throws ExceptionZZZ;
	public void setPositionIdentifier(String sPositionIdentifier) throws ExceptionZZZ;

	public int getLineMarkerIndexLeftBehindCurrent() throws ExceptionZZZ;
	public void setLineMarkerIndexLeftBehindCurrent(int iIndex) throws ExceptionZZZ;
	public void setLineMarkerIndexLeftBehindIncreased(int iIndexMayIncrease) throws ExceptionZZZ;
	public int computeLineMarkerIndexLeft(String sLog) throws ExceptionZZZ; //Rechne fuer den konkreten Log String die Postion aus.
	public int computeLineMarkerIndexLeftBehind(String sLog) throws ExceptionZZZ; //Rechne fuer den konkreten Log String die Postion aus.
	public int computeLineMarkerIndexLeftBehind2use(String sLog) throws ExceptionZZZ;     //Rechne aus, gib aber ggfs. den gespeicherten Wert zurueck, wenn der groesser ist.	
}
