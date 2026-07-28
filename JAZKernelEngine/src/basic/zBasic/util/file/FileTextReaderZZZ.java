package basic.zBasic.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import basic.javagently.Stream;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.AbstractKernelLogZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class FileTextReaderZZZ extends  AbstractFileTextReaderZZZ{
	private static final long serialVersionUID = -9054462955710855745L;
	
	public FileTextReaderZZZ() {		
	}
	public FileTextReaderZZZ(String sFileName) throws ExceptionZZZ{
		this.setFilePath(sFileName);
	}
	public FileTextReaderZZZ(File objFile) throws ExceptionZZZ {
		this.setFileObject(objFile);
	}
	
	private boolean createStreamInternal_(String sFileNameIn){
		boolean bReturn = false;
		try {
			String sFileName;
			if(StringZZZ.isEmpty(sFileNameIn)){
				sFileName = this.getFilePath();
			}else{
				sFileName = sFileNameIn;
			}
			this.objStream = new StreamZZZ(sFileName,0); //0 = Read, 1 = Write //ggfs. noch das Encoding übergeben in dieser ZZZ-Klasse
			bReturn = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return bReturn;
	}
	
	//##############################################################
	

//  TODO: Auch per Stream lesen...
//	public synchronized boolean readToStringByStream(){
//		boolean bHasStream = true;
//		if(this.objStream==null) bHasStream = createStreamInternal("");
//		if(bHasStream){
//			this.objStream.readString();
//		}
//		return bHasStream;
//	}
	
	
	public synchronized String read() throws ExceptionZZZ{			
		return readAsString();
	}
	
	public synchronized String readAsString() throws ExceptionZZZ{			
		File objFile = this.getFileObject();		
		return FileTextUtilZZZ.readFileToString(objFile);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public synchronized List<String> readAsList() throws ExceptionZZZ{
		File objFile = this.getFileObject();
		return FileTextUtilZZZ.readFileToList(objFile);
	}
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	public String getFileNameDefault() throws ExceptionZZZ {
		return FileTextReaderZZZ.sFILE_NAME_DEFAULT;
	}
}
