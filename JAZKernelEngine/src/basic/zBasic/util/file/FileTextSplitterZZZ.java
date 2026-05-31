package basic.zBasic.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;

public class FileTextSplitterZZZ extends  AbstractFileTextCombinedZZZ{
	private static final long serialVersionUID = 489990254115931232L;

	public FileTextSplitterZZZ() {		
	}
	public FileTextSplitterZZZ(String sFileName) throws ExceptionZZZ{
		super(sFileName);
	}
	public FileTextSplitterZZZ(File objFile) throws ExceptionZZZ {
		super(objFile);
	}
	
	public FileTextSplitterZZZ(List<String> listaLine) throws ExceptionZZZ {
		super(listaLine);
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean split(int iLineNumber) throws ExceptionZZZ {
		return splitKeepBefore(iLineNumber);
	}
	
	/** Die Angegebene Zeile wird im Ergebnis nicht vorhanden sein.
	 *  Ideal, wenn sie durch einen Block ersetzen will.
	 * @param iLineNumber
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean splitRemove(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vor der Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i < iLineNumber; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Alles nach der Trennzeile
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumber + 1; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
		    
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean splitKeepBefore(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vorher und die Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i <= iLineNumber; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Alles nach der Trennzeile
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumber + 1; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
		    
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean splitKeepBehind(int iLineNumber) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vor der Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i < iLineNumber; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Die Trennzeile und alles danach
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumber; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
			
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	
	
	//################################################################
	//### Splitte an 2 Stellen
	//################################################################
	
	public boolean split(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		return splitKeepBefore(iLineNumberPre, iLineNumberPost);
	}
	
	/** Die Angegebene Zeilen werden im Ergebnis nicht vorhanden sein.
	 *  Ideal, wenn man sie durch einen Block ersetzen will.
	 * @param iLineNumber
	 * @return
	 * @throws ExceptionZZZ
	 */
	public boolean splitRemove(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vor der Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i < iLineNumberPre; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Alles nach der Trennzeile
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumberPost + 1; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
		    
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	public boolean splitKeep(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Trennzeile und Alles vorher
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i <= iLineNumberPre; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Trennzeile und Alles nachher.
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumberPost; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
		    
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean splitKeepBefore(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vorher und die Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i <= iLineNumberPre; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Alles nach der Trennzeile
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumberPost; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
		    
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public boolean splitKeepBehind(int iLineNumberPre, int iLineNumberPost) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
			List<String>listaLine = this.getLines();
			if(listaLine==null)break main;
			
			//Alles vor der Trennzeile
			List<String>listaLinePre = new ArrayList<String>();			
		    for (int i = 0; i < iLineNumberPre; i++) {
		    	listaLinePre.add(listaLine.get(i));
		    }
		    this.setLinesPre(listaLinePre);
		    
		    //Die Trennzeile und alles danach
		    List<String>listaLinePost = new ArrayList<String>();
		    for (int i = iLineNumberPost+1; i < listaLine.size(); i++) {
		    	listaLinePost.add(listaLine.get(i));
		    }
		    this.setLinesPost(listaLinePost);
			
		    bReturn = true;
		}//end main:
		return bReturn;		
	}
}
