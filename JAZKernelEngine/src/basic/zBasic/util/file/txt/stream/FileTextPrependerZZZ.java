package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ListUtilZZZ;

public class FileTextPrependerZZZ extends AbstractFileTextReaderSaverZZZ {
	private static final long serialVersionUID = 7754836813854270845L;

	public FileTextPrependerZZZ() throws ExceptionZZZ {
	}

	public FileTextPrependerZZZ(String sFileName) throws ExceptionZZZ {
		super(sFileName);
	}

	public FileTextPrependerZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}

	public FileTextPrependerZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}

	// ##############################################################

	/**
	 * Hängt eine einzelne Zeile an den Anfang der Textdatei an.
	 */
	public boolean prepend(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(sLine == null) break main;

			List<String> listaPrepend = ListUtilZZZ.toList(sLine);
			bReturn = this.prepend(listaPrepend);
		}
		return bReturn;
	}

	/**
	 * Hängt mehrere Zeilen an den Anfang der Textdatei an.
	 */
	public boolean prepend(List<String> listaPrepend) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaPrepend == null) break main;

			List<String> listaString = this.getLines();
			if(listaString == null) break main;

			List<String> listReturn = ListUtilZZZ.join(listaPrepend, listaString);
			this.setLines(listReturn);

			bReturn = true;
		}// end main:
		return bReturn;
	}
	
	public boolean prepend(String[] saLine) throws ExceptionZZZ {
	    return this.prepend(ArrayUtilZZZ.toList(saLine));
	}
	
	//##########################
	//### Komfortfunktion 2 in 1
	public boolean prependAndSave(String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.prepend(sLine);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
}
