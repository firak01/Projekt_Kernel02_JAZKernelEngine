package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ListUtilZZZ;

public class FileTextAppenderZZZ extends AbstractFileTextSaverZZZ {
	private static final long serialVersionUID = 7754836813854270845L;

	public FileTextAppenderZZZ() throws ExceptionZZZ {
	}

	public FileTextAppenderZZZ(String sFileName) throws ExceptionZZZ {
		super(sFileName);
	}

	public FileTextAppenderZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}

	public FileTextAppenderZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}

	// ##############################################################

	/**
	 * Hängt eine einzelne Zeile an das Ende der Textdatei an.
	 */
	public boolean append(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sLine == null) break main;

			List<String> listaAppend = ListUtilZZZ.toList(sLine);
			bReturn = this.append(listaAppend);
		}
		return bReturn;
	}

	/**
	 * Hängt mehrere Zeilen an das Ende der Textdatei an.
	 */
	public boolean append(List<String> listaAppend) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaAppend == null) break main;

			List<String> listaString = this.getLines();
			if(listaString == null) break main;

			List<String> listReturn = ListUtilZZZ.join(listaString, listaAppend);
			this.setLines(listReturn);

			bReturn = true;
		}// end main:
		return bReturn;
	}
	
	public boolean append(String[] saLine) throws ExceptionZZZ {
	    return this.append(ArrayUtilZZZ.toList(saLine));
	}
	
	//##########################
	//### Komfortfunktion 2 in 1
	public boolean appendAndSave(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.append(sLine);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
	
	public boolean appendAndSaveAsExpanded(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.append(sLine);
			if(!bReturn) break main;
			
			bReturn = this.saveAsExpanded();			
		}
		return bReturn;
	}
}
