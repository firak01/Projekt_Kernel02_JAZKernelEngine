package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ListUtilZZZ;

public class FileTextInserterZZZ  extends  AbstractFileTextReaderZZZ{
	private static final long serialVersionUID = -3451810324201026617L;

	public FileTextInserterZZZ() {		
	}
	public FileTextInserterZZZ(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	public FileTextInserterZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public FileTextInserterZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}
	
	//###############################	
	public boolean insert(int iLineNumber, List<String>listaStringInsertment) throws ExceptionZZZ {
		return insertBehind(iLineNumber, listaStringInsertment);
	}
	
	public boolean insertBehind(int iLineNumber, List<String>listaStringInsertment) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaStringInsertment==null) break main;
			
			List<String> listaString = this.getLines();
			if(listaString==null) break main;
			
			
			FileTextSplitterZZZ objFileTextSplitter = new FileTextSplitterZZZ(listaString);
			objFileTextSplitter.splitKeepBehind(iLineNumber);
			
			List<String> listaStringPre = objFileTextSplitter.getLinesPre();
			List<String> listaStringPost = objFileTextSplitter.getLinesPost();
			
			List<String> listReturn = ListUtilZZZ.join(listaStringPre, listaStringInsertment, listaStringPost);
			this.setLines(listReturn);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean insertBefore(int iLineNumber, List<String>listaStringInsertment) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaStringInsertment==null) break main;
			
			List<String> listaString = this.getLines();
			if(listaString==null) break main;
			
			
			FileTextSplitterZZZ objFileTextSplitter = new FileTextSplitterZZZ(listaString);
			objFileTextSplitter.splitKeepBefore(iLineNumber);
			
			List<String> listaStringPre = objFileTextSplitter.getLinesPre();
			List<String> listaStringPost = objFileTextSplitter.getLinesPost();
			
			List<String> listReturn = ListUtilZZZ.join(listaStringPre, listaStringInsertment, listaStringPost);
			this.setLines(listReturn);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
}
