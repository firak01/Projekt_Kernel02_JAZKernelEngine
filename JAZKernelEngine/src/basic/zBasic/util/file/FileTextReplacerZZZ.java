package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.ListUtilZZZ;

public class FileTextReplacerZZZ  extends  AbstractFileTextSaverZZZ{
	private static final long serialVersionUID = -3451810324201026617L;

	public FileTextReplacerZZZ() {		
	}
	public FileTextReplacerZZZ(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	public FileTextReplacerZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public FileTextReplacerZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}
	
	//###############################	
	public boolean replace(int iLineNumber, String sLine) throws ExceptionZZZ {		
		List<String> listaStringReplacement = ListUtilZZZ.toList(sLine);
		return replace(iLineNumber, listaStringReplacement);
	}
			
	public boolean replace(int iLineNumber, List<String>listaStringReplacement) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaStringReplacement==null) break main;
			
			List<String> listaString = this.getLines();
			if(listaString==null) break main;
			
			
			FileTextSplitterZZZ objFileTextSplitter = new FileTextSplitterZZZ(listaString);
			objFileTextSplitter.splitRemove(iLineNumber);
			
			List<String> listaStringPre = objFileTextSplitter.getLinesPre();
			List<String> listaStringPost = objFileTextSplitter.getLinesPost();
			
			List<String> listReturn = ListUtilZZZ.join(listaStringPre, listaStringReplacement, listaStringPost);
			this.setLines(listReturn);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public boolean replace(int iLineNumberPre, int iLineNumberPost, String sLine) throws ExceptionZZZ {
		List<String> listaStringReplacement = ListUtilZZZ.toList(sLine);
		return replace(iLineNumberPre, iLineNumberPost, listaStringReplacement);
	}
	
	public boolean replace(int iLineNumberPre, int iLineNumberPost, List<String>listaStringReplacement) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			if(listaStringReplacement==null) break main;
			
			List<String> listaString = this.getLines();
			if(listaString==null) break main;
			
			FileTextSplitterZZZ objFileTextSplitter = new FileTextSplitterZZZ(listaString);
			objFileTextSplitter.splitRemove(iLineNumberPre, iLineNumberPost);
			
			List<String> listaStringPre = objFileTextSplitter.getLinesPre();
			List<String> listaStringPost = objFileTextSplitter.getLinesPost();
			
			List<String> listReturn = ListUtilZZZ.join(listaStringPre, listaStringReplacement, listaStringPost);
			this.setLines(listReturn);
			
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	//##########################
	//### Komfortfunktion 2 in 1
	public boolean replaceAndSave(int iLineNumber, String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.replace(iLineNumber, sLine);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
	
	public boolean replaceAndSave(int iLineNumber, List<String>listaStringReplacement) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.replace(iLineNumber, listaStringReplacement);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
	
	public boolean replaceAndSave(int iLineNumberPre, int iLineNumberPost, String sLine) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.replace(iLineNumberPre, iLineNumberPost, sLine);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;
	}
	
	public boolean replaceAndSave(int iLineNumberPre, int iLineNumberPost, List<String>listaStringReplacement) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			bReturn = this.replace(iLineNumberPre, iLineNumberPost, listaStringReplacement);
			if(!bReturn) break main;
			
			bReturn = this.save();			
		}
		return bReturn;		
	}
}
