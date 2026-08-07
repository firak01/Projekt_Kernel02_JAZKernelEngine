package basic.zBasic.util.file.txt.stream;

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
import basic.zBasic.util.file.txt.FileTextUtilZZZ;
import basic.zBasic.util.stream.IStreamZZZ;
import basic.zBasic.util.stream.StreamZZZ;
import basic.zKernel.AbstractKernelLogZZZ;

public abstract class AbstractFileTextWriterLoaderZZZ extends AbstractFileTextZZZ{
	private static final long serialVersionUID = -8791560715152516646L;
	public static final String sFILE_NAME_DEFAULT= "NewTextfile_default.txt";
	
	protected IStreamZZZ objStream = null;	
	
	protected List<String> listaLine = null;
	
	
	public AbstractFileTextWriterLoaderZZZ() throws ExceptionZZZ {
		super();
	}
	
	public AbstractFileTextWriterLoaderZZZ(String sFilePath) throws ExceptionZZZ{
		super(sFilePath);
	}
	
	public AbstractFileTextWriterLoaderZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public AbstractFileTextWriterLoaderZZZ(List<String> listaLine) throws ExceptionZZZ {
		super();
		this.setLines(listaLine);
	}
	
	//### GETTER / SETTER 
	@Override
	public String getFileNameDefault() throws ExceptionZZZ {
		return AbstractFileTextWriterLoaderZZZ.sFILE_NAME_DEFAULT;
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public List<String> getLines() throws ExceptionZZZ{
		return this.listaLine;
	}
	public void setLines(List<String>listaLine) {
		this.listaLine = listaLine;
	}
	
	//### METHODEN
	private boolean createStreamInternal_(String sFileNameIn){
		boolean bReturn = false;
		try {
			String sFileName;
			if(StringZZZ.isEmpty(sFileNameIn)){
				sFileName = this.getFilePath();
			}else{
				sFileName = sFileNameIn;
			}
			this.objStream = new StreamZZZ(sFileName,1); //0 = Read, 1 = Write //ggfs. noch das Encoding übergeben in dieser ZZZ-Klasse
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
	public synchronized boolean writeLines() throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		List<String> listString = this.getLines();
		if(listString==null) break main;
		
			boolean bHasStream = true;
			if(this.objStream==null) bHasStream = createStreamInternal_("");
			if(bHasStream){
				for(String stemp : listString) {
					this.objStream.println(stemp);
				}
			}
		
			bReturn = bHasStream;
		}//end main;
		return bReturn;
	}
	
	public synchronized boolean writeLines(List<String> listString) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{		
			if(listString==null) break main;
			
			boolean bHasStream = true;
			if(this.objStream==null) bHasStream = createStreamInternal_("");
			if(bHasStream){
				for(String stemp : listString) {
					this.objStream.println(stemp);
				}
			}
		
			bReturn = bHasStream;
		}//end main;
		return bReturn;
	}
	
	
	public synchronized boolean writeLine(String stemp){
		boolean bHasStream = true;
		if(this.objStream==null) bHasStream = createStreamInternal_("");
		if(bHasStream){
			this.objStream.println(stemp);
		}
		return bHasStream;
	}


	public synchronized boolean write(String stemp){
		boolean bHasStream = true;
		if(this.objStream==null) bHasStream = createStreamInternal_("");
		if(bHasStream){
			this.objStream.print(stemp);
		}
		return bHasStream;
	}
	
	//### aus Closable, das soll besser sein als einen Destruktor zu verwenden.
	@Override
    public void close() throws IOException{
        if(objStream!=null){
            objStream.close();
        }
    }
}
