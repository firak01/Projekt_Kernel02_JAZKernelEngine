package basic.zBasic.util.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zUtil.io.FileExpandableZZZ;
import basic.zUtil.io.FileExpansionZZZ;
import basic.zUtil.io.FileZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionUserZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileTextSaverZZZ extends AbstractFileTextReaderZZZ implements IFileExpansionUserZZZ {
	private static final long serialVersionUID = -6026050043450090577L;

	IFileExpansionZZZ objFileExpansion = null;
	
	protected String sFilePathSavedLast = null; 
	
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
	
	@Override
	public IFileExpansionZZZ getFileExpansionObject() throws ExceptionZZZ{
		if(this.objFileExpansion==null) {
			File objFile = this.getFileObject();
			this.objFileExpansion = new FileExpansionZZZ(objFile);
		}
		return this.objFileExpansion;
	}
	
	@Override
	public void setFileExpansionObject(IFileExpansionZZZ objFileExpansion) throws ExceptionZZZ {
		this.objFileExpansion = objFileExpansion;
	}
	
	public String getFilePathSavedLast() throws ExceptionZZZ{
		return this.sFilePathSavedLast;
	}
	private void setFilePathSavedLast(String sFilePath) throws ExceptionZZZ{
		this.sFilePathSavedLast = sFilePath;
	}
	
	
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
			try {
				//Speichere die - wie auch immer generierten - Zeilen der Textdatei ab
				FileTextWriterZZZ objWriter = new FileTextWriterZZZ(sFilePath); //!!! Damit sind die intern verwendeten Zeilen der Textdatei noch die alten!!!
				bReturn = objWriter.writeLines(this.getLines()); //... sag dem Writer also, er soll die neuen schreiben.
				
				if(bReturn) {
					this.setFilePathSavedLast(sFilePath); //Das ist besonders interessant, wenn es um Dateien mit EXAPNSION im Dateinamen geht.
				}
				
				objWriter.close();
			} catch (IOException ioe) {
				ExceptionZZZ ez = new ExceptionZZZ(ioe);
				throw ez;
			}
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
			FileExpandableZZZ objFile = new FileExpandableZZZ(sFilePath);
			objFile.setFlag(IFileExpansionEnabledZZZ.FLAGZ.USE_FILE_EXPANSION, true);
			IFileExpansionZZZ objFileExpansion = this.getFileExpansionObject();
			objFile.setFileExpansionObject(objFileExpansion);
			

			String sFilePathTotalWithExpansionNext = objFile.PathNameTotalExpandedNextCompute();
			
			//Speichere die - wie auch immer generierten - Zeilen der Textdatei ab			
			bReturn = this.save(sFilePathTotalWithExpansionNext);
			
			if(bReturn) {
				//Da wir in den ...Text...Behandler Objekten nur Listen speichern, wird ein neues Speichern die Verändete Liste in den neuen Dateinamen sichern.
				IFileExpansionZZZ objExpansion = objFile.getFileExpansionObject();
				this.setFileExpansionObject(objExpansion); 
			}
		}//end main:
		return bReturn;
	}
}
