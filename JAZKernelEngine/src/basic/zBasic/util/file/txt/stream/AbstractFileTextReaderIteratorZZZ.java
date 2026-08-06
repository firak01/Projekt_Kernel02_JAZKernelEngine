package basic.zBasic.util.file.txt.stream;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

/** Merke: Es gibt auch den TxtReaderZZZ, für RandomAccess - Zugriff
 * @author Fritz Lindhauer
 *
 */
public abstract class AbstractFileTextReaderIteratorZZZ extends AbstractFileTextReaderZZZ implements Iterator<String> {
	private static final long serialVersionUID = -7772083118156845601L;
	
	//+++ für Iterator
	protected volatile int iCurrent = -1; //Index der aktuellen Zeile

	public AbstractFileTextReaderIteratorZZZ() throws ExceptionZZZ {	
		super();
	}
	
	public AbstractFileTextReaderIteratorZZZ(String sFilePath) throws ExceptionZZZ {
		super();
		this.setFilePath(sFilePath);
	}
	
	public AbstractFileTextReaderIteratorZZZ(File objFile) throws ExceptionZZZ {
		super();
		this.setFileObject(objFile);
	}
	
	public AbstractFileTextReaderIteratorZZZ(List<String> listaLine) throws ExceptionZZZ{
		super();
		this.setLines(listaLine);
	}
	
	//##### Getter / Setter ###################

	
	//### aus Iterator
	@Override
	public boolean hasNext() {	
		int iCurrentNext = this.iCurrent++;
		try {
			if(iCurrentNext < this.getLines().size()) {
				return true;
			}
		} catch (ExceptionZZZ e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String next() {
		String sReturn = null;
		main:{			
			try {
				if(!this.hasNext()) break main;
			
				this.iCurrent++;				
				sReturn = this.getLines().get(this.iCurrent);
			} catch (ExceptionZZZ e) {			
				e.printStackTrace();
			}
		}//end main:
		return sReturn;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
