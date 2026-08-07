package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractFileTextCombinedZZZ extends AbstractFileTextReaderLoaderZZZ {
	private static final long serialVersionUID = -1464375530224033955L;

	public static final String sFILE_NAME_PRE_DEFAULT = "NewTextfileSplittedPre_default.txt";
	public static final String sFILE_NAME_POST_DEFAULT = "NewTextfileSplittedPost_default.txt";

	protected String sFileNamePre = null;
	protected String sFileNamePost = null;
	protected String sFilePathPre = null;
	protected String sFilePathPost = null;

	protected List<String> listaLineSplittedPre = null;
	protected List<String> listaLineSplittedPost = null;

	public AbstractFileTextCombinedZZZ() throws ExceptionZZZ {
		super();
	}

	public AbstractFileTextCombinedZZZ(String sFileName) throws ExceptionZZZ {
		super(sFileName);
	}

	public AbstractFileTextCombinedZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}

	public AbstractFileTextCombinedZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}

	public String getFileNamePreDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ.sFILE_NAME_PRE_DEFAULT;
	}

	public String getFileNamePostDefault() throws ExceptionZZZ {
		return AbstractFileTextCombinedZZZ.sFILE_NAME_POST_DEFAULT;
	}

	public String getFileNamePre() throws ExceptionZZZ {
		if (StringZZZ.isEmpty(this.sFileNamePre)) {
			return this.getFileNamePreDefault();
		}
		return this.sFileNamePre;
	}

	public String getFileNamePost() throws ExceptionZZZ {
		if (StringZZZ.isEmpty(this.sFileNamePost)) {
			return this.getFileNamePostDefault();
		}
		return this.sFileNamePost;
	}

	/**
	 * Liefert den explizit gesetzten Pre-Pfad oder leitet ihn aus dem
	 * Ausgangspfad ab, z. B. Original.txt zu Original_pre.txt.
	 */
	public String getFilePathPre() throws ExceptionZZZ {
		if (StringZZZ.isEmpty(this.sFilePathPre)) {
			this.sFilePathPre = this.createDerivedFilePath(this.getFilePath(), "_pre");
		}
		return this.sFilePathPre;
	}

	public void setFilePathPre(String sFilePathPre) {
		this.sFilePathPre = sFilePathPre;
	}

	/**
	 * Liefert den explizit gesetzten Post-Pfad oder leitet ihn aus dem
	 * Ausgangspfad ab, z. B. Original.txt zu Original_post.txt.
	 */
	public String getFilePathPost() throws ExceptionZZZ {
		if (StringZZZ.isEmpty(this.sFilePathPost)) {
			this.sFilePathPost = this.createDerivedFilePath(this.getFilePath(), "_post");
		}
		return this.sFilePathPost;
	}

	public void setFilePathPost(String sFilePathPost) {
		this.sFilePathPost = sFilePathPost;
	}

	private String createDerivedFilePath(String sSourceFilePath, String sSuffix) {
		File objSourceFile = new File(sSourceFilePath);
		String sFileName = objSourceFile.getName();
		int iExtensionPosition = sFileName.lastIndexOf('.');

		String sDerivedFileName;
		if (iExtensionPosition > 0) {
			sDerivedFileName = sFileName.substring(0, iExtensionPosition) + sSuffix
					+ sFileName.substring(iExtensionPosition);
		} else {
			sDerivedFileName = sFileName + sSuffix;
		}

		String sParent = objSourceFile.getParent();
		if (sParent == null) {
			return sDerivedFileName;
		}
		return new File(sParent, sDerivedFileName).getPath();
	}

	public List<String> getLinesPre() {
		return this.listaLineSplittedPre;
	}

	public void setLinesPre(List<String> listaLinePre) {
		this.listaLineSplittedPre = listaLinePre;
	}

	public List<String> getLinesPost() {
		return this.listaLineSplittedPost;
	}

	public void setLinesPost(List<String> listaLinePost) {
		this.listaLineSplittedPost = listaLinePost;
	}
}
