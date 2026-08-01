package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public class FileTextSplitterZZZ extends AbstractFileTextCombinedSaverZZZ {
	private static final long serialVersionUID = 489990254115931232L;

	public FileTextSplitterZZZ() {
	}

	public FileTextSplitterZZZ(String sFilePath) throws ExceptionZZZ {
		super(sFilePath);
	}

	public FileTextSplitterZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}

	public FileTextSplitterZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}

	public boolean split(int iLineNumber) throws ExceptionZZZ {
		return splitKeepBefore(iLineNumber);
	}

	public boolean splitRemove(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i < iLineNumber; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumber + 1; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean splitKeepBefore(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i <= iLineNumber; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumber + 1; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean splitKeepBehind(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i < iLineNumber; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumber; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean split(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		return splitKeepBefore(iLineNumberPre, iLineNumberPost);
	}

	public boolean splitRemove(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i < iLineNumberPre; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumberPost + 1; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean splitKeep(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i <= iLineNumberPre; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumberPost; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean splitKeepBefore(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i <= iLineNumberPre; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumberPost; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}

	public boolean splitKeepBehind(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main: {
			List<String> listaLine = this.getLines();
			if (listaLine == null) break main;

			List<String> listaLinePre = new ArrayList<String>();
			for (int i = 0; i < iLineNumberPre; i++) {
				listaLinePre.add(listaLine.get(i));
			}
			this.setLinesPre(listaLinePre);

			List<String> listaLinePost = new ArrayList<String>();
			for (int i = iLineNumberPost + 1; i < listaLine.size(); i++) {
				listaLinePost.add(listaLine.get(i));
			}
			this.setLinesPost(listaLinePost);
			bReturn = true;
		}
		return bReturn;
	}
}
