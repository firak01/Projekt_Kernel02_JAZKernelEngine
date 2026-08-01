package basic.zBasic.util.file.txt.bytes;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringArrayZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class TxtCommonZZZ extends AbstractObjectWithFlagZZZ{
	private File file=null;
	private RandomAccessFile rwFile = null;
	
	private StringArrayZZZ zsaCommentLineCharakter = null;
	
	//Default Konstruktor, wichtig um die Klasse per Reflection mit .newInstance() erzeugen zu k�nnen.
	public TxtCommonZZZ(){
		super();
	}
	
	public File getFile(){
		return this.file;
	}
	public void setFile(File file){
		this.file = file;
		this.setRandomAccessFileObject(null); //Beim Zugriff auf das RandomAccessFile mit getRandomAccessObject() wird es dann erstellt.
	}
	
	public abstract String getMode();
	public abstract Class getClassZ();
	
//	//Nur durchreichen, wg. der ENUM-Funktionalit�t der Flags und der Tatsache, das diese Klasse abstact ist, werden hier keine Flags gehalten. 
//	public boolean getFlag(String sFlag){
//		return super.getFlag(sFlag);
//	}
//	public boolean setFlag(String sFlag, boolean bValue){
//		return super.setFlag(sFlag, bValue);
//	}
	
	
	public RandomAccessFile getRandomAccessFileObject() throws ExceptionZZZ{
		try{
			if(this.rwFile==null){		
				this.rwFile = new RandomAccessFile(this.getFile(), this.getMode());
			}else if(this.rwFile.getChannel().isOpen() == false){
				this.rwFile.getChannel().force(true);
			}
		}catch(IOException ie){
			ExceptionZZZ ez = new ExceptionZZZ("IOException happend: " + ie.getMessage(), iERROR_RUNTIME, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return this.rwFile;
	}
	protected void setRandomAccessFileObject(RandomAccessFile raFile){
		this.rwFile = raFile;
		//!!! es gibt keine Methode von RandomAccessFile, um auf deren File - Objekt zuzugreifen.
		//     Darum ist diese Methode private !!! Es m�sste n�mlich auf jeden Fall das dazugeh�rende File-Objekt auch gesetz werden.
		//     In meiner Klasse reicht es daher ein File-Objekt zu setzen und das neue RandomAccessFile-Objekt wird erstellt.
	}
	
	
	
	
	public StringArrayZZZ getCommentCharacterAll() throws ExceptionZZZ{
		if(this.zsaCommentLineCharakter == null){
			String[] saTemp = {"#",";"};
			this.zsaCommentLineCharakter = new StringArrayZZZ(saTemp);
		}
		return this.zsaCommentLineCharakter;
	}
	
	public boolean isCommentLine(String sLine) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			StringArrayZZZ zsaCommentCharacter = this.getCommentCharacterAll();
			String sProof = sLine.substring(0,1);
			if(zsaCommentCharacter.contains(sProof)) bReturn = true;
		}
		return bReturn;
	}
	
	public boolean isEmptyLine(String sLine) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(StringZZZ.isBlank(sLine)){
				bReturn = true;
				break main;
			}					
		}
		return bReturn;
	}
}
