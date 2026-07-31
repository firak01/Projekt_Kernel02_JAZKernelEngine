package basic.zBasic.util.file;

import java.io.File;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import custom.zUtil.io.FileZZZ;

/**
 * Basis fuer Textdateien, deren Inhalt in einen vorderen und einen hinteren
 * Teil aufgeteilt wurde. Beide Teile koennen getrennt oder gemeinsam
 * gespeichert werden.
 */
public abstract class AbstractFileTextCombinedSaverZZZ extends AbstractFileTextCombinedZZZ {
	private static final long serialVersionUID = 1878762695844895640L;

	public AbstractFileTextCombinedSaverZZZ() {
		super();
	}

	public AbstractFileTextCombinedSaverZZZ(String sFilePath) throws ExceptionZZZ {
		super(sFilePath);
	}

	public AbstractFileTextCombinedSaverZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}

	public AbstractFileTextCombinedSaverZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}

	/** Speichert beide Teile in ihren Standard- bzw. konfigurierten Pfaden. */
	public boolean save() throws ExceptionZZZ {
		return this.save(this.getFilePathPre(), this.getFilePathPost());
	}

	/** Speichert beide Teile in den angegebenen Pfaden. */
	public boolean save(String sFilePathPre, String sFilePathPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			bReturn = this.savePre(sFilePathPre);
			if (!bReturn) break main;

			bReturn = this.savePost(sFilePathPost);
		}
		return bReturn;
	}

	public boolean savePre() throws ExceptionZZZ {
		return this.savePre(this.getFilePathPre());
	}

	public boolean savePre(String sFilePath) throws ExceptionZZZ {
		return this.saveInternal(sFilePath, this.getLinesPre());
	}

	public boolean savePost() throws ExceptionZZZ {
		return this.savePost(this.getFilePathPost());
	}

	public boolean savePost(String sFilePath) throws ExceptionZZZ {
		return this.saveInternal(sFilePath, this.getLinesPost());
	}

	/** Speichert beide Teile mit jeweils naechster freier Dateinamenerweiterung. */
	public boolean saveAsExpanded() throws ExceptionZZZ {
		return this.saveAsExpanded(this.getFilePathPre(), this.getFilePathPost());
	}

	public boolean saveAsExpanded(String sFilePathPre, String sFilePathPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			bReturn = this.savePreAsExpanded(sFilePathPre);
			if (!bReturn) break main;

			bReturn = this.savePostAsExpanded(sFilePathPost);
		}
		return bReturn;
	}

	public boolean savePreAsExpanded() throws ExceptionZZZ {
		return this.savePreAsExpanded(this.getFilePathPre());
	}

	public boolean savePreAsExpanded(String sFilePath) throws ExceptionZZZ {
		return this.savePre(this.getExpandedFilePath(sFilePath));
	}

	public boolean savePostAsExpanded() throws ExceptionZZZ {
		return this.savePostAsExpanded(this.getFilePathPost());
	}

	public boolean savePostAsExpanded(String sFilePath) throws ExceptionZZZ {
		return this.savePost(this.getExpandedFilePath(sFilePath));
	}

	private String getExpandedFilePath(String sFilePath) throws ExceptionZZZ {
		if (StringZZZ.isEmpty(sFilePath)) {
			throw new ExceptionZZZ("FilePath zum Speichern.", iERROR_PROPERTY_MISSING, this,
					ReflectCodeZZZ.getMethodCurrentName());
		}

		FileZZZ objFile = new FileZZZ(sFilePath);
		return objFile.getNameExpandedNext();
	}

	/** Der gemeinsame, einzige Schreibzugriff fuer alle Save-Varianten. */
	private boolean saveInternal(String sFilePath, List<String> listaLine) throws ExceptionZZZ {
		if (StringZZZ.isEmpty(sFilePath)) {
			throw new ExceptionZZZ("FilePath zum Speichern.", iERROR_PROPERTY_MISSING, this,
					ReflectCodeZZZ.getMethodCurrentName());
		}

		FileTextWriterZZZ objWriter = new FileTextWriterZZZ(sFilePath);
		return objWriter.writeLines(listaLine);
	}
}

