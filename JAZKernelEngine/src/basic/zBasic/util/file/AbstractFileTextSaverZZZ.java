package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import custom.zUtil.io.FileZZZ;

public abstract class AbstractFileTextSaverZZZ extends AbstractFileTextReaderZZZ{
			
	public AbstractFileTextSaverZZZ() {		
	}
	public AbstractFileTextSaverZZZ(String sFilePath) throws ExceptionZZZ{
		super(sFilePath);
	}
	
	public AbstractFileTextSaverZZZ(File objFile) throws ExceptionZZZ{
		super(objFile);
	}
	
	public AbstractFileTextSaverZZZ(List<String> listaLine) throws ExceptionZZZ{
		super(listaLine);
	}
	
	
	//##### Getter / Setter ###################
	
	
	//##########################################
	public boolean save() throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			
			String sFilePath = this.getFilePath();
			if(StringZZZ.isEmpty(sFilePath)) {
				ExceptionZZZ ez = new ExceptionZZZ("FilePath zum Speichern.", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			bReturn = this.save(sFilePath);
		}//end main:
		return bReturn;
	}
	
	public boolean save(String sFilePath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Speichere die - wie auch immer generierten - Zeilen der Textdatei ab
			FileTextWriterZZZ objWriter = new FileTextWriterZZZ(sFilePath); //!!! Damit sind die intern verwendeten Zeilen der Textdatei noch die alten!!!
			bReturn = objWriter.writeLines(this.getLines()); //... sag dem Writer also, er soll die neuen schreiben.
		}//end main:
		return bReturn;
	}
	
	public boolean saveAsExpanded() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			
			String sFilePath = this.getFilePath();
			if(StringZZZ.isEmpty(sFilePath)) {
				ExceptionZZZ ez = new ExceptionZZZ("FilePath zum Speichern.", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
						
			bReturn = this.saveAsExpanded(sFilePath);
		}//end main:
		return bReturn;
	}
	
	public boolean saveAsExpanded(String sFilePath) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			//Hole den Dateinamen mit "Expansion"
			FileZZZ objFile = new FileZZZ(sFilePath);
			String sFilePathWithExpansion = objFile.getNameExpandedNext();
			
			//Speichere die - wie auch immer generierten - Zeilen der Textdatei ab			
			bReturn = this.save(sFilePathWithExpansion);
		}//end main:
		return bReturn;
	}
}
