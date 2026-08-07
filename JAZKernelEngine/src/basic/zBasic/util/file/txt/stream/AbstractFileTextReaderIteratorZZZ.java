package basic.zBasic.util.file.txt.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/** Merke: Es gibt auch den TxtReaderZZZ, für RandomAccess - Zugriff
 * @author Fritz Lindhauer
 *
 */
public abstract class AbstractFileTextReaderIteratorZZZ<T> extends AbstractFileTextZZZ<T> implements Iterator<String> {
	private static final long serialVersionUID = -7772083118156845601L;
	
	protected BufferedReader reader=null;

	/** Bereits gelesene Zeilen */
	protected ArrayList<String> listaLine = null;
	protected volatile int iCurrent = -1; //Index der aktuellen Zeile
	
	//+++ für Iterator	
	private String nextLine=null;
	private boolean hasLookedAhead = false;
	private boolean finished = false;
	
	
	public AbstractFileTextReaderIteratorZZZ() throws ExceptionZZZ {	
		super();
		AbstractFileTextReaderIteratorNew_();
	}
	
	public AbstractFileTextReaderIteratorZZZ(String sFilePath) throws ExceptionZZZ {
		super(sFilePath);
		AbstractFileTextReaderIteratorNew_();
	}
	
	public AbstractFileTextReaderIteratorZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);	
		AbstractFileTextReaderIteratorNew_();
	}
	
	private boolean AbstractFileTextReaderIteratorNew_() throws ExceptionZZZ{
//		File objFile = this.getFile();
//		 try {
//			 if(objFile==null) {
//				 ExceptionZZZ ez = new ExceptionZZZ("FileObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getPositionCurrent());
//				 throw ez;
//			 }
//			 
//			this.reader = new BufferedReader(new FileReader(objFile));
//			
//		} catch (FileNotFoundException fnfe) {
//			ExceptionZZZ ez = new ExceptionZZZ(fnfe);
//			throw ez;
//		}
//		
		return true;
	}
	
		
	//##### Getter / Setter ###################
	public BufferedReader getReader() throws ExceptionZZZ{
		try {
			if(this.reader==null) {
				File objFile = this.getFile();
				if(objFile==null) {
					 ExceptionZZZ ez = new ExceptionZZZ("FileObject", iERROR_PROPERTY_MISSING, this, ReflectCodeZZZ.getPositionCurrent());
					 throw ez;
				 }
				
				this.reader = new BufferedReader(new FileReader(objFile));				
			}
		} catch (FileNotFoundException fnfe) {
			ExceptionZZZ ez = new ExceptionZZZ(fnfe);
			throw ez;
		}
		return this.reader;
	}
	
	public ArrayList<String> getLines() throws ExceptionZZZ{
		if(this.listaLine==null) {
			this.listaLine=new ArrayList<String>();
		}
		return this.listaLine;
	}
	public void setLines(List<String>listaLine) {
		this.listaLine = (ArrayList<String>) listaLine;
	}

	//### aus Iterator
	@Override
    public boolean hasNext() {
		try {
	        if (finished) {
	            return false;
	        }
	
	        if (hasLookedAhead) {
	            return nextLine != null;
	        }
	
	        try {
	            nextLine = this.getReader().readLine();
	            hasLookedAhead = true;
	
	            if (nextLine == null) {
	                finished = true;
	                close();
	            }
	
	        } catch (IOException e) {
	            finished = true;
	            try {
	                close();
	            } catch (IOException ignored) {
	            }
	            throw new RuntimeException(e);
	        }
		} catch(ExceptionZZZ ez) {
			ez.printStackTrace();			
		}

        return nextLine != null;
    }

    @Override
    public String next() {
    	 try {
	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }

	        hasLookedAhead = false;

	        // Hier wird die interne Liste erweitert
			this.getLines().add(nextLine);
			this.iCurrent++;
		} catch (ExceptionZZZ e) {			
			e.printStackTrace();
		}
        return nextLine;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    //### aus Closeable
	@Override
	public void close() throws IOException {
		if(reader!=null) reader.close();
	}

}
