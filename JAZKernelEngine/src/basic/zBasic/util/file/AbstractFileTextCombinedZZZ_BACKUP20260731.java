package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractFileTextCombinedZZZ_BACKUP20260731 extends AbstractFileTextReaderZZZ{
	private static final long serialVersionUID = -1464375530224033955L;
	
	public static final String sFILE_NAME_PRE_DEFAULT= "NewTextfilePre_default.txt";
	public static final String sFILE_NAME_POST_DEFAULT= "NewTextfilePost_default.txt";
		
	protected String sFileNamePre = null;
	protected String sFileNamePost = null;
	
	protected List<String> listaLineSplittedPre = null;
	protected List<String> listaLineSplittedPost = null;
	
	
	public AbstractFileTextCombinedZZZ_BACKUP20260731() {	
		super();
	}
	public AbstractFileTextCombinedZZZ_BACKUP20260731(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	
	public AbstractFileTextCombinedZZZ_BACKUP20260731(File objFile) throws ExceptionZZZ{
		super(objFile);
	}
	
	public AbstractFileTextCombinedZZZ_BACKUP20260731(List<String> listaLine) throws ExceptionZZZ{
		super(listaLine);
	}
	
	
	//##### Getter / Setter ###################

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public String getFileNamePreDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ_BACKUP20260731.sFILE_NAME_PRE_DEFAULT;
	}
	
	public String getFileNamePostDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ_BACKUP20260731.sFILE_NAME_POST_DEFAULT;
	}
	
	public String getFileNamePre() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sFileNamePre)) {
			return this.getFileNamePreDefault();
		}else {
			return this.sFileNamePre;
		}
	}
	
	public String getFileNamePost() throws ExceptionZZZ{
		if(StringZZZ.isEmpty(this.sFileNamePost)) {
			return this.getFileNamePostDefault();
		}else {
			return this.sFileNamePre;
		}
	}
	
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public List<String> getLinesPre(){
		return this.listaLineSplittedPre;		
	}
	public void setLinesPre(List<String>listaLinePre) {
		this.listaLineSplittedPre = listaLinePre;
	}
	
	public List<String> getLinesPost(){
		return this.listaLineSplittedPost;		
	}
	public void setLinesPost(List<String>listaLinePost) {
		this.listaLineSplittedPost = listaLinePost;
	}
}
