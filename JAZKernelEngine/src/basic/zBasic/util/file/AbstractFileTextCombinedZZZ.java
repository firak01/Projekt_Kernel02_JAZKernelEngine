package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public abstract class AbstractFileTextCombinedZZZ extends AbstractFileTextZZZ{
	private static final long serialVersionUID = -1464375530224033955L;
	
	public static final String sFILE_NAME_PRE_DEFAULT= "NewTextfilePre_default.txt";
	public static final String sFILE_NAME_POST_DEFAULT= "NewTextfilePost_default.txt";
		
	protected List<String> listaLineSplittedPre = null;
	protected List<String> listaLineSplittedPost = null;
	
	
	public AbstractFileTextCombinedZZZ() {	
		super();
	}
	public AbstractFileTextCombinedZZZ(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	
	public AbstractFileTextCombinedZZZ(File objFile) throws ExceptionZZZ{
		super(objFile);
	}
	
	public AbstractFileTextCombinedZZZ(List<String> listaLine) throws ExceptionZZZ{
		super(listaLine);
	}
	
	
	//##### Getter / Setter ###################

	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public String getFileNamePreDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ.sFILE_NAME_PRE_DEFAULT;
	}
	
	public String getFileNamePostDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ.sFILE_NAME_POST_DEFAULT;
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
