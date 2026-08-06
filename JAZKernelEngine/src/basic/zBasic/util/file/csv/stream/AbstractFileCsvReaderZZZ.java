package basic.zBasic.util.file.csv.stream;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.stream.AbstractFileTextReaderZZZ;
import basic.zBasic.util.file.txt.stream.FileTextReaderIteratorZZZ;
import basic.zBasic.util.file.txt.stream.IFileTextReaderIteratorUserZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public abstract class AbstractFileCsvReaderZZZ<T>  extends AbstractObjectWithFlagZZZ<T> implements IFileCsvReaderEnabledZZZ, Closeable{
	private static final long serialVersionUID = 8453120372088993124L;
		
	private AbstractFileTextReaderZZZ objFileTextReader = null;
	protected File objFile = null;
	
	private Vector<String> header = null;
	
	protected char cDELIMITER_DEFAULT = ';';
	protected char cDelimiter = CharZZZ.getNull();
	
	public AbstractFileCsvReaderZZZ() throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_(null, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(char cDelimiter) throws ExceptionZZZ {
		super();
		AbstractFileCsvReaderNew_(null, cDelimiter);
	}
	
	public AbstractFileCsvReaderZZZ(String sFilePathTotal) throws ExceptionZZZ {
		super();		
		File objFile = new File(sFilePathTotal);
		AbstractFileCsvReaderNew_(objFile, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(String sFilePathTotal, char cDelimiter) throws ExceptionZZZ {
		super();		
		File objFile = new File(sFilePathTotal);
		AbstractFileCsvReaderNew_(objFile, cDelimiter);
	}
	
	public AbstractFileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn) throws ExceptionZZZ {
		super();
		String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileNameIn);
		File objFile = new File(sFilePathTotal);
		AbstractFileCsvReaderNew_(objFile, CharZZZ.getNull());
	}
	
	public AbstractFileCsvReaderZZZ(String sDirectoryIn, String sFileNameIn, char cDelimiter) throws ExceptionZZZ {
		super();
		String sFilePathTotal = FileEasyZZZ.joinFilePathName(sDirectoryIn, sFileNameIn);
		File objFile = new File(sFilePathTotal);
		AbstractFileCsvReaderNew_(objFile, cDelimiter);
	}
	
	//++++++++++++++++++
	public AbstractFileCsvReaderZZZ(File objFile, String[] saFlag) throws ExceptionZZZ {
		super(saFlag);		
		AbstractFileCsvReaderNew_(objFile, cDelimiter);
	}
	
	
	private boolean AbstractFileCsvReaderNew_(File objFile, char cDelimiter) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			this.objFile = objFile;
			this.cDelimiter = cDelimiter;
							
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	
	
	
	//#########################################
	public Vector getHeader() {
		return header;
	}
	
	private Vector readHeader() {
		Vector header = null;
		try {
			
			//Die Kopfzeile ist die erte Zeile
			String sLine = this.getFileTextReaderObject().getLines().get(0);
			header = parseLine(sLine);
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return header;
	}
	
	public Vector<String> readHeader(String sHeaderLine) {
		Vector<String> header = null;
		try {			
			header = parseLine(sHeaderLine);
		}
		catch(Exception e) {
			e.printStackTrace(System.out);
		}
		return header;
	}
	
	
	
	//#####################################################
	
	
	
	
	//#####################################################
	
	public Vector<String> parseLine(String sLine) throws ExceptionZZZ {
		char cDelimiter = this.getDelimiter();
		return AbstractFileCsvReaderZZZ.parseLine(sLine, cDelimiter);
	}
	
	public static Vector<String> parseLine(String line, char cDelimiter) throws ExceptionZZZ {
		Vector<String> fields = new Vector<String>();
		boolean quote = false;
		int start = 0, end = 0, index = 0, max = line.length()-1;
		try {
			// Alle Spalten durchlaufen
			while (index <= max) {
				start = index;
				quote = false;

				//Inhalt einer Spalte extrahieren
				while (index <= max) {
					char check = line.charAt(index);
					//Nun wird der naechste Delimiter gesucht, der NICHT
					//innerhalb von Anfuehrungszeichen (") steht. Wenn ein
					//Anfuehrungszeichen gefunden wurde, dann muss der Merker
					//getoggled werden.
					if (check == '"')
						quote = !quote;
					//Es befindet sich auf jeden Fall eine gerade Anzahl von
					//"-Zeichen zwischen den Quotes, so dass nur die am Anfang
					//und Ende ber�cksichtigt werden.
					else if (check == cDelimiter && quote == false)
						break;
					index++;
				}
				end = index;

				// Anfuehrungszeichen am Anfang und am Ende gehoeren nicht zum
				// String und werden deshalb auch nicht beachtet.
				if (line.charAt(start) == '"' && line.charAt(end-1) == '"') {
					start++;
					end--;
				}

				// Der gefundene Text wird in dem Vector gespeichert, ohne jedoch zu
				// vergessen, dass zwei auf einander folgende Anfuehrungszeichen ("")
				// durch ein Einzelnes (") zu ersetzen sind.
				fields.addElement(StringZZZ.replace(line.substring(start, end), "\"\"", "\""));
				index++;
			}
		}
		catch(Exception e) {
			ExceptionZZZ ez = new ExceptionZZZ(e);
			throw ez;
		}
		return fields;
	}
	
    public static List<String> parseCsvLineAsList(String line) {
        List<String> result = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // Doppelte Hochkommas im String ("") → ein Hochkomma
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++; // nächste Anführungszeichen überspringen
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0); // zurücksetzen
            } else {
                current.append(c);
            }
        }
        result.add(current.toString()); // letztes Element hinzufügen

        return result;
    }
    
    public static String[] parseCsvLine(String line) {
        List<String> result = parseCsvLineAsList(line);

        // Rückgabe als Array
        return result.toArray(new String[result.size()]);
    } 
	
	
	
	//############################################
	//### GETTER / SETTER
	public File getFile() throws ExceptionZZZ {
		return this.objFile;
	}
	
	public void setFile(File objFile) throws ExceptionZZZ {		
		this.reset();
		this.objFile = objFile;		
	}
	
	public char getDelimiter() throws ExceptionZZZ {
		if(CharZZZ.isEmptyNull(this.cDelimiter)){
			return this.cDELIMITER_DEFAULT;
		}else {
			return this.cDelimiter;
		}
	}
	
	public void setDelimiter(char cDelimiter) throws ExceptionZZZ {
		this.cDelimiter = cDelimiter;
	}
	
	
	
	//###############################################
	//### Methoden
	public void setFileTextReaderObject(AbstractFileTextReaderZZZ obj) throws ExceptionZZZ {
		this.objFileTextReader = obj;
	}
	
	public AbstractFileTextReaderZZZ getFileTextReaderObject() throws ExceptionZZZ {
		return this.objFileTextReader;
	}

	
	public boolean reset() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			this.setFileTextReaderObject(null);
			
			
			bReturn = true;			
		}//end main:
		return bReturn;
	}
	
	
	//### aus Closeable
	@Override
	public void close() throws IOException {		
		if(this.objFileTextReader!=null) {
			this.objFileTextReader.close();
		}
		
	}
	
	//###################################################
	//### FLAG HANDLING
	//###################################################
	
	//### aus IFileCsvReaderEnabledZZZ
	@Override
	public boolean getFlag(IFileCsvReaderEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}	
	
	@Override
	public boolean setFlag(IFileCsvReaderEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlag(IFileCsvReaderEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileCsvReaderEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
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
	public boolean proofFlagExists(IFileCsvReaderEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagSetBefore(IFileCsvReaderEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	
	
	//###################################
	//### FLAG CUSTOM Handling
		
	@Override
	public boolean getFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.getFlagCustom(objEnumFlag.name());
	}

	@Override
	public boolean setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagCustom(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagCustom(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagCustom(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagCustomExists(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagCustomSetBefore(IFileCsvReaderEnabledZZZ.FLAGZCUSTOM objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagCustomSetBefore(objEnumFlag.name());
	}

	

	//###################################
	//### FLAGLOCAL Handling

	/* ES GIBT HIER KEIN FLAGLOCAL
	//### aus JgitEnabledZZZ	
	@Override
	public boolean getFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.getFlagLocal(objEnumFlag.name());
	}

	@Override
	public boolean setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlagLocal(objEnumFlag.name(), bFlagValue);
	}

	@Override
	public boolean[] setFlagLocal(IFileExpansionEnabledZZZ.FLAGZLOCAL[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlagLocal(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagLocalExists(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagLocalExists(objEnumFlag.name());
	}

	@Override
	public boolean proofFlagLocalSetBefore(IFileExpansionEnabledZZZ.FLAGZLOCAL objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagSetBefore(objEnumFlag.name());
	}

	*/
}
