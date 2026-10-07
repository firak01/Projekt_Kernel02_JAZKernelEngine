package basic.zBasic.util.crypt.code;

import base.files.DateiUtil;
import base.io.IoUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import basic.zBasic.util.file.IFileEasyConstantsZZZ;
import custom.zKernel.Log;

public abstract class AbstractVigenereZZZ extends AbstractObjectWithFlagZZZ implements IVigenereZZZ{

	private static final long serialVersionUID = 1L;
	private String sFilePath=null;
	private String sKeyWord=null;

	private int[] iaOriginal=null;
	private int[] iaEncrypted=null;
	private int[] iaDecrypted=null;
	
	public AbstractVigenereZZZ() {
		super();
	}
	public AbstractVigenereZZZ(String[]saFlagControl) throws ExceptionZZZ {
		super(saFlagControl);
		AbstractVigenereNew_("","", saFlagControl);
	}	
	public AbstractVigenereZZZ(String sSchluesselWort) throws ExceptionZZZ {
		super((String[])null);
		AbstractVigenereNew_(sSchluesselWort,"", null);
	}
	public AbstractVigenereZZZ(String sSchluesselWort, String sFilePath) throws ExceptionZZZ {	
		super((String[])null);
		AbstractVigenereNew_(sSchluesselWort,sFilePath, null);		
	}
	
	private boolean AbstractVigenereNew_(String sSchluesselWort, String sFilePath, String[] saFlagControlIn) throws ExceptionZZZ {
		boolean bReturn = false;
		main:{
		//try{	 		
			//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			 String stemp; boolean btemp; String sLog;
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){
					 String sKey = stemp;
					 sLog = "the passed flag '" + sKey + "' is not available for class '" + this.getClass() + "'.";
					 Log.printlnDate(this, ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					 
					// Bei der "Übergabe auf Verdacht" keinen Fehler werfen!!!							
					// ExceptionZZZ ez = new ExceptionZZZ(stemp, IFlagUserZZZ.iERROR_FLAG_UNAVAILABLE, this, ReflectCodeZZZ.getMethodCurrentName()); 							
					// throw ez;		 
				}
			}
			if(this.getFlag("init")==true){
				bReturn = true;
				break main;
			}						
		}
		
		this.setCryptKey(sSchluesselWort);
		this.setFilePath(sFilePath);
		bReturn = true;
	}//end main:
		return bReturn;
	}
		
	//+++++++ Getter / Setter
	public void setFilePath(String sFilePath) {
		this.sFilePath = sFilePath;
	}
	public String getFilePath() {
		return this.sFilePath;
	}
		
	//######################
	public abstract int getOffsetForUtf8Range();
	public abstract int getOffsetForAsciiRange();
	public abstract int[]fromUtf8ToAsciiForOffset(int[] p);
	public abstract int[]fromAsciiToUtf8ForOffset(int[] p);

	//###########################
	//### Verschluesselungsmethoden
	//###########################		
	/** Der reine Entschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 */
	public int[] encrypt(int[]p) throws ExceptionZZZ{
		int[]iaReturn=null;
		main:{
			if(p==null)break main;
			iaReturn=new int[p.length];
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=p;
				break main;
			}
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			
			//Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
			//    for(int i=0; i < iaSchluesselwort.length; i++) {
			//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
			//    }
			iaSchluesselwort = this.fromUtf8ToAsciiForOffset(iaSchluesselwort);
				
			int laengeSW = sKeyWord.length();
			
			//Nun gemaess Buch auf Seite 35, d.h. "blank/Leerzeichen" beziehen.
			//Merke: "B" ist im Buchbeispiel der erste Buchstabe mit einem ASCII Code von 66
	        //       Also kommt für den ersten Buchstaben 66-32=34 heraus.
			p = this.fromUtf8ToAsciiForOffset(p);
			
		    int[]ppure = new int[p.length];
		    for (int i = 0; i < p.length; i++) {
		    	if(i>=1) System.out.print("|");
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	int iSum = iaSchluesselwort[iIndexS]+p[i];
		    	int iFormula = (iSum)% this.getOffsetForAsciiRange();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
		      ppure[i] = c;				// nur wegen abspeichern  
		      System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    System.out.print("\n");
		    //Gemaess Seite 35 noch 32 wieder draufaddieren, das ist das Leerzeichen "blank".
		    //c = c +32;
		    iaReturn = this.fromAsciiToUtf8ForOffset(ppure);
		}//end main:
		return iaReturn;
	}
	
	
	
	@Override
	public int[] getEncryptedValuesAsInt() throws ExceptionZZZ {
		return this.iaEncrypted;
	}
	public void setEncryptedValues(int[] iaCrypted) {
		this.iaEncrypted = iaCrypted;
	}
	
	//##########################
	//### Entschluesselungsmethoden
	//##########################	    
	/** Der reine Verschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 * @throws ExceptionZZZ 
	 */
	public int[] decrypt(int[]c) throws ExceptionZZZ {
		int[]iaReturn=null;
		main:{
			if(c==null)break main;
			iaReturn=new int[c.length];
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=c;
				break main;
			}
				
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
				
			//Auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
			//    for(int i=0; i < iaSchluesselwort.length; i++) {
			//    	iaSchluesselwort[i]=iaSchluesselwort[i]-32;
			//    }
			iaSchluesselwort = this.fromUtf8ToAsciiForOffset(iaSchluesselwort);					
			int laengeSW = sKeyWord.length();
				
		    int[]iaPure = new int[c.length];
		    for (int i=0; i<c.length; i++) {
		      int iModLaengeSW = i%laengeSW;
		      int iBezug = iaSchluesselwort[iModLaengeSW];
		      int p = c[i]-iBezug;			// c-s
		      if (p < this.getOffsetForUtf8Range()) {    	  
		    	  p+=this.getOffsetForAsciiRange();//26 Fuer Viginere26 Verschluesselung
		      }   
		      iaPure[i]=p;
		    }			  
		    iaReturn = iaPure;
		}//end main:
		return iaReturn;   
	}
	
	public static String computeKeyWordFromEncryptedFile(String sFilePath) throws ExceptionZZZ {
		String sReturn=null;
		main:{
			if(StringZZZ.isEmpty(sFilePath)) break main;
			
			DateiUtil objFile = new DateiUtil(sFilePath);
			sReturn = computeKeyWordFromEncryptedFile(objFile);
		}//end main:
		return sReturn;
	}
	public static String computeKeyWordFromEncryptedFile(DateiUtil fileUtil) throws ExceptionZZZ {
		  String sReturn=null;
		  main:{
			  if(fileUtil==null)break main;
			  String sFilepath = fileUtil.computeFilePath();
			  if(StringZZZ.isEmpty(sFilepath)) break main;
			  
			  sReturn = StringZZZ.leftback(sFilepath, IFileEasyConstantsZZZ.sFILE_ENDING_SEPARATOR);
			  sReturn = StringZZZ.right(sReturn, IFileEasyConstantsZZZ.sDIRECTORY_SEPARATOR);
			  sReturn = StringZZZ.right(sReturn, "_");
		  }//end main:
		  return sReturn;
	  }
	
	//####################################################
		
	//####################################################	
	//### Methoden aus ICryptZZZ
	//####################################################
	@Override
	public int getSubtype() {
		return CryptAlgorithmMaintypeZZZ.TypeZZZ.VIGENERE.ordinal();
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			int[] iaInput = UnicodeZZZ.toIntArray(sInput);
			int[] iaEncrypted = this.encrypt(iaInput);
			this.setEncryptedValues(iaEncrypted);
			
		    sReturn = CharArrayZZZ.toString(iaEncrypted);
		}//end main:
		return sReturn;
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			int[] iaInput = UnicodeZZZ.toIntArray(sInput);
			int[] iaDecrypted = this.decrypt(iaInput);
			this.setDecryptedValues(iaDecrypted);
		    sReturn = CharArrayZZZ.toString(iaDecrypted);
		}//end main:
		return sReturn;
	}
	
	
	@Override
	public void setCryptKey(String sKeyWord) throws ExceptionZZZ {
		this.sKeyWord= sKeyWord;
	}
	
	@Override
	public String getCryptKey() throws ExceptionZZZ {
		return this.sKeyWord;
	}
		
	@Override
	public int[] getOriginalValuesAsInt(Object newParam) throws ExceptionZZZ {
		return this.iaOriginal;
	}
	
	@Override
	public void setOriginalValues(int[] iaOriginal) throws ExceptionZZZ {
		this.iaOriginal = iaOriginal;
	}
	
	@Override
	public int[] getDecryptedValuesAsInt() throws ExceptionZZZ {
		return this.iaDecrypted;
	}
	
	@Override
	public void setDecryptedValues(int[] iaDecrypted) throws ExceptionZZZ {
		this.iaDecrypted = iaDecrypted;
	}
	
	//#################################################
	//AUS dem Interface ICrypt	
	//+++ nur wichtig für ROT-Verfahren
	@Override
	public void setCryptNumber(int iCryptKey) throws ExceptionZZZ {
		//wg. ICryptZZZ
	}
	
	@Override
	public int getCryptNumber() throws ExceptionZZZ {
		return -1;
		//wg. ICryptZZZ
	}
	
	//### Methoden werden zwar z.B. nur fuer Vigenere Verschluesselung, bzw. fuer Verschluesselung mit CharacterPool gebraucht.
	//    Um den Einsatz von ICrypt - Objekten zu standardiesieren, hier notwendig.
	@Override
	public String getCharacterPool() throws ExceptionZZZ {
		return null;		//wg. ICryptZZZ
	}

	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ {
		return null;		//wg. ICryptZZZ
	}

	@Override
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) throws ExceptionZZZ {
		//wg. ICryptZZZ	
	}
	
	@Override
	public void setCharacterPoolBase(String sCharacterPool) throws ExceptionZZZ {
		//wg. ICryptZZZ		
	}
	
	@Override
	public String getCharacterPoolBase() throws ExceptionZZZ {
		return null;		//wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) throws ExceptionZZZ {
		//Nur wg. ICryptZZZ
	}
	
	@Override
	public String getCharacterPoolAdditional() throws ExceptionZZZ {
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public ArrayListZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		return null; //Nur wg. ICryptZZZ
	}
	
	@Override
	public void setCharacterPoolList(ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		//Nur wg. ICryptZZZ
	}
	
	
	
}
