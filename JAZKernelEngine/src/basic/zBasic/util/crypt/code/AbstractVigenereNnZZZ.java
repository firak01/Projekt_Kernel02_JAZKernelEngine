package basic.zBasic.util.crypt.code;

import java.util.ArrayList;

import base.files.DateiUtil;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.datatype.character.CharArrayZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.datatype.string.UnicodeZZZ;
import custom.zKernel.Log;

public abstract class AbstractVigenereNnZZZ extends AbstractVigenereZZZ implements IROTUserZZZ, ICharacterPoolEnabledZZZ{	
	private static final long serialVersionUID = 1L;
	public static int iOffsetForAsciiRange=0;//wird dann später aus der Laenge des CharacterPools errechnet.
	public static int iOffsetForUtf8Range=0; //im CharacterPool sind keine nicht druckbaren Zeichen.
	
	protected CharacterExtendedZZZ objCharacterMissingReplacement = null;
	ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = new ArrayListZZZ<CharacterExtendedZZZ>();

	protected String sCharacterPoolBase = null;
	protected String sCharacterPoolAdditional = null;
	
	protected int[] iaEncryptedPositionInPool = null;
	protected int[] iaDecryptedPositionInPool = null;
	
	public AbstractVigenereNnZZZ() {
		super();		
	}
	public AbstractVigenereNnZZZ(String[]saFlagControl) throws ExceptionZZZ {
		super(saFlagControl);		
		AbstractVigenereNnNew_("","",saFlagControl);
	}
	public AbstractVigenereNnZZZ(String sSchluesselWort) throws ExceptionZZZ {
		super((String[])null);
		AbstractVigenereNnNew_(sSchluesselWort,"",null);
	}
	public AbstractVigenereNnZZZ(String sSchluesselWort, String sFilePath) throws ExceptionZZZ {
		super((String[])null);
		AbstractVigenereNnNew_(sSchluesselWort,sFilePath,null);
	}
	
	private boolean AbstractVigenereNnNew_(String sSchluesselWort, String sFilePath, String[] saFlagControlIn) throws ExceptionZZZ {
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
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */	
	public static String encrypt(String sInput, String sCharacterPoolBaseIn, char cCharacterReplacement, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseBlank, boolean bUseAdditionalCharacter, String sCharacterPoolAdditionalIn, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolBase = null;
			if(StringZZZ.isEmpty(sCharacterPoolBaseIn)) {
				sCharacterPoolBase= AbstractVigenereNnZZZ.getCharacterPoolBaseDefault();
			}else {
				sCharacterPoolBase = sCharacterPoolBaseIn;
			}

			String sCharacterPoolAdditional = null;
			if(sCharacterPoolAdditionalIn==null) {
				sCharacterPoolAdditional = AbstractVigenereNnZZZ.getCharacterPoolAdditionalDefault();
			}else {
				sCharacterPoolAdditional = sCharacterPoolAdditionalIn;
			}
			
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseBlank, bUseAdditionalCharacter, sCharacterPoolAdditional);			
			ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyword, listasCharacterPool);
			
			int[] iaText = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);
						
			int[]iaPositionInPool = AbstractVigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);
			
			CharacterExtendedZZZ objCharMissingReplacement = new CharacterExtendedZZZ(cCharacterReplacement);
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPositionInPool, listasCharacterPool,objCharMissingReplacement);		    			
		}//end main;
		return sReturn;
    }
	
	/** Der reine Entschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 */
	public int[]encrypt(int[]iaText) throws ExceptionZZZ{		
		int[]iaReturn=null;
		main:{
			if(ArrayUtilZZZ.isNull(iaText))break main;
			
			String sKeyWord = this.getCryptKey();
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=iaText;
				break main;
			}
			          
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			ArrayListZZZ<CharacterExtendedZZZ> listAsCharacterPool = this.getCharacterPoolList();
						
            iaReturn = AbstractVigenereNnZZZ.encryptAsPositionInPool(iaText, listAsCharacterPool, iaSchluesselwort);
            this.setEncryptedCharacterPoolPosition(iaReturn);
            
            CharacterExtendedZZZ objCharMissingReplacement = this.getCharacterMissingReplacment();
            String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaReturn, listasCharacterPool,objCharMissingReplacement);
            iaReturn = UnicodeZZZ.toIntArray(sReturn);
			this.setEncryptedValues(iaReturn);		
		}//end main:
		return iaReturn;
	}
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringZZZ.isEmpty(sInput))break main;
			
			String sKeyword = this.getCryptKey();

			ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			int[] iaPosition = VigenereNnZZZ.encryptAsPositionInPool(sInput,listasCharacterPool,sKeyword);
			this.setEncryptedCharacterPoolPosition(iaPosition);
			
			CharacterExtendedZZZ objCharMissingReplacement = this.getCharacterMissingReplacment();
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPosition, listasCharacterPool,objCharMissingReplacement);
			int[] iaEncrypted = UnicodeZZZ.toIntArray(sReturn);			
			this.setEncryptedValues(iaEncrypted);
		}//end main:
		return sReturn;
	}
	
		
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * -1 wird als Position zurueckgegeben, wenn das Zeichen nicht im Pool ist.
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] encryptAsPositionInPool(String sInput, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			
			int[] iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyword);
			
			int[] iaText = UnicodeZZZ.toIntArray(sInput);
			
			int[] iaPositionInPool = VigenereNnZZZ.encryptAsPositionInPool(iaText, listasCharacterPool, iaSchluesselwort);
			iaReturn = iaPositionInPool;
		}//end main;
		return iaReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] encryptAsPositionInPool(int[] iaText, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(ArrayUtilZZZ.isNull(iaText)) break main;
			if(ArrayUtilZZZ.isNull(iaSchluesselwort)) {
				iaReturn = iaText;
				break main;
			}
			
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}						
			
			int[]iaTextAsPositionInCharacterPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaText, listasCharacterPool);
			int[]iaSchluesselwortAsPositionInCharacterPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);
			
			int laengeSW = iaSchluesselwortAsPositionInCharacterPool.length;			
			
			int[]ppure = new int[iaTextAsPositionInCharacterPool.length];
		    for (int i = 0; i < iaTextAsPositionInCharacterPool.length; i++) {
		    	if(iaTextAsPositionInCharacterPool[i]==-1) {
		    		ppure[i] = -1;//mache also nichts, sondern ueberspringe das nicht vorhandene Zeichen einfach.
		    	}else {
			        //Das steht in der Codedatei
			    	//Merke: c = Chiffrebuchstabe
			    	int iIndexS = i%laengeSW;
			    	int iSum = iaTextAsPositionInCharacterPool[i]+iaSchluesselwortAsPositionInCharacterPool[iIndexS];
			    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128    	
			    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
			      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
			                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.            
			    	ppure[i] = c;				// nur wegen abspeichern  
			      
			    	//if(i>=1) System.out.print("|");
			    	//System.out.print("i="+i+", c='"+c+"'");
		    	}		    	
		    }
		    //System.out.print("\n");
			
		    iaReturn = ppure;		   
		}//end main;
		return iaReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static String decrypt(String sInput, String sCharacterPoolBaseIn, boolean bUseUppercasePool, boolean bUseLowercasePool, boolean bUseNumericPool, boolean bUseBlank, boolean bUseAdditionalCharacter, String sCharacterPoolAdditionalIn, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		//Merke: Bei Vigenere reicht das einfache Umdrehen des Codes wie bei Caesar oder anderen Rotierenden Verschluesselungen nicht mehr aus.
		String sReturn = sInput;
		main:{
			if(StringZZZ.isEmpty(sInput)) break main;
			
			String sCharacterPoolBase = null;
			if(StringZZZ.isEmpty(sCharacterPoolBaseIn)) {
				sCharacterPoolBase= AbstractVigenereNnZZZ.getCharacterPoolBaseDefault();
			}else {
				sCharacterPoolBase = sCharacterPoolBaseIn;
			}

			String sCharacterPoolAdditional = null;
			if(sCharacterPoolAdditionalIn==null) {
				sCharacterPoolAdditional= AbstractVigenereNnZZZ.getCharacterPoolAdditionalDefault();
			}else {
				sCharacterPoolAdditional = sCharacterPoolAdditionalIn;
			}
						
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseBlank, bUseAdditionalCharacter,sCharacterPoolAdditional);	
			ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);					
			
			int[]ppure = AbstractVigenereNnZZZ.decrypt(sInput,listasCharacterPool,sKeyword);
			
			sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(ppure, listasCharacterPool);		    			
		}//end main;
		return sReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decrypt(String sInput, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, String sKeyword) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(StringZZZ.isEmpty(sInput)) break main;
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			int[]iaEncryptedTextAsPositionInPool = UnicodeZZZ.toIntArrayCharacterPoolPosition(sInput, listasCharacterPool);
			if(StringZZZ.isEmpty(sKeyword)) {
				iaReturn = UnicodeZZZ.toIntArray(sInput);
				break main;
			}			
			int[] iaSchluesselwortAsPositionInPool = UnicodeZZZ.toIntArrayCharacterPoolPosition(sKeyword, listasCharacterPool);
			
			int[] iaPositionInPool = AbstractVigenereNnZZZ.decryptAsPositionInPool(iaEncryptedTextAsPositionInPool, listasCharacterPool, iaSchluesselwortAsPositionInPool);
		    
		    String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPositionInPool, listasCharacterPool);
		    iaReturn = UnicodeZZZ.toIntArray(sReturn);
		}//end main;
		return iaReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decryptAsPositionInPool(int[] iaEncryptedText, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(ArrayUtilZZZ.isNull(iaEncryptedText)) break main;
			if(ArrayUtilZZZ.isNull(iaSchluesselwort)) {
				iaReturn = iaEncryptedText;
				break main;
			}
			
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//!Nun den iaEncryptedText in ein Array der Postionen umwandeln			
			int[] iaTextPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaEncryptedText, listasCharacterPool);
			int[] iaSchluesselwortPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);

			int laengeSW = iaSchluesselwortPositionInPool.length;			
			
			int[]ppure = new int[iaTextPositionInPool.length];												
		    for (int i = 0; i < iaTextPositionInPool.length; i++) {		    	
		        //Das steht in der Codedatei
		    	//Merke: c = Chiffrebuchstabe
		    	int iIndexS = i%laengeSW;
		    	//int iSum = iaTextPositionInPool[iIndexS]-iaSchluesselwortPositionInPool[iIndexS];
		    	int iSum = iaTextPositionInPool[i]-iaSchluesselwortPositionInPool[iIndexS];
		    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128
		    	if(iFormula<0) {
		    		iFormula += listasCharacterPool.size();
		    	}
		    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
		      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
		                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
		    	
		      ppure[i] = c;				// nur wegen abspeichern  
		      //if(i>=1) System.out.print("|");
		      //System.out.print("i="+i+", c='"+c+"'"); 
		    }
		    //System.out.print("\n");
			
		    iaReturn = ppure;		    
		}//end main;
		return iaReturn;
    }
	
	/** Wie AbstractVigenereZZZ, aber auf den CharacterPool bezogen
	 * @param sInput
	 * @param sCharacterPoolIn
	 * @param n
	 * @return
	 * @throws IllegalArgumentException
	 * @author Fritz Lindhauer, 18.12.2022, 08:58:20
	 * @throws ExceptionZZZ 
	 */
	public static int[] decryptAsPositionInPool(int[] iaEncryptedText, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, int[] iaSchluesselwort, CharacterExtendedZZZ objCharMissingReplacement) throws IllegalArgumentException, ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			if(ArrayUtilZZZ.isNull(iaEncryptedText)) break main;
			if(ArrayUtilZZZ.isNull(iaSchluesselwort)) {
				iaReturn = iaEncryptedText;
				break main;
			}
			
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}

			//!Nun den iaEncryptedText in ein Array der Postionen umwandeln		
			//TODOGOON; //20230121: "Lisas Hund # ist toll" ..... soll verarbeitet werden können OHNE Sonderzeichen reinzunehmen und # dann erstetz werden!!!
			int[] iaTextPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaEncryptedText, listasCharacterPool,objCharMissingReplacement);
			int[] iaSchluesselwortPositionInPool = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaSchluesselwort, listasCharacterPool);

			int laengeSW = iaSchluesselwortPositionInPool.length;			
			
			
			int[]ppure = new int[iaTextPositionInPool.length];												
		    for (int i = 0; i < iaTextPositionInPool.length; i++) {	
		    	//Die auszulassenden Zeichen nicht decodieren
		    	if(iaTextPositionInPool[i]==-1) {
		    		ppure[i] = -1; //mache also keine Entschluesselung an der Stelle.
		    	}else {
		    		 //Das steht in der Codedatei
			    	//Merke: c = Chiffrebuchstabe
			    	int iIndexS = i%laengeSW;
			    	//int iSum = iaTextPositionInPool[iIndexS]-iaSchluesselwortPositionInPool[iIndexS];
			    	int iSum = iaTextPositionInPool[i]-iaSchluesselwortPositionInPool[iIndexS];
			    	int iFormula = (iSum)% listasCharacterPool.size();  //auf Seite 35 wird der Modulus 96 verwendet. Merke 32+96=128
			    	if(iFormula<0) {
			    		iFormula += listasCharacterPool.size();
			    	}
			    	int c = iFormula; //FGL: 	Das ist der Mathematische Ansatz: 
			      								//		Die Buchstaben wurden durch natuerliche Zahlen ersetzt.
			                                    //		Dann fiel eine Gesetzmaessigkeit auf (s. Seite 32 im Buch), die so ausgenutzt wurde.
			    	
			      ppure[i] = c;				// nur wegen abspeichern  
			      //if(i>=1) System.out.print("|");
			      //System.out.print("i="+i+", c='"+c+"'"); 
		    	}
		    	
		       
		    }
		    //System.out.print("\n");
			
		    iaReturn = ppure;		    
		}//end main;
		return iaReturn;
    }
	
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		String sReturn = null;
		main:{	
			if(StringZZZ.isEmpty(sInput))break main;
			
			char[] caEntryptedText = sInput.toCharArray();
			int[] iaEncryptedText = CharArrayZZZ.toIntArray(caEntryptedText);
			
			//sollte uebereinstimmen !!! int[] iaEncryptedTextSavedTest = this.getEncryptedValuesAsInt();
			this.setEncryptedValues(iaEncryptedText);
						
			int[]iaDecryptedText = this.decrypt(iaEncryptedText);					
			sReturn = CharArrayZZZ.toString(iaDecryptedText);
		}//end main:
		return sReturn;
	}
	
	/** Der reine Verschluesselungsalgorithmus, ohne Eingaben per Console, etc.
	 * @param p
	 * @return
	 * @author Fritz Lindhauer, 02.12.2022, 08:31:12
	 * @throws ExceptionZZZ 
	 */
	public int[] decrypt(int[]iaEncryptedText) throws ExceptionZZZ {
		int[]iaReturn=null;
		main:{
			if(ArrayUtilZZZ.isNull(iaEncryptedText)) break main;
			
			String sKeyWord = this.getCryptKey();
			int[] iaSchluesselwort=null;
			if(StringZZZ.isEmpty(sKeyWord)) {
				iaReturn=iaEncryptedText;
				break main;
			}else {
				iaSchluesselwort = UnicodeZZZ.toIntArray(sKeyWord);
			}
								
			ArrayListZZZ<CharacterExtendedZZZ> listCharacterPool = this.getCharacterPoolList();	
			CharacterExtendedZZZ objCharMissingReplacement = this.getCharacterMissingReplacment();
			int[]iaPosition = VigenereNnZZZ.decryptAsPositionInPool(iaEncryptedText, listCharacterPool, iaSchluesselwort,objCharMissingReplacement);
			this.setDecryptedCharacterPoolPosition(iaPosition);
						
			String sReturn = CharacterExtendedZZZ.computeStringFromCharacterPoolPosition(iaPosition, listasCharacterPool,objCharMissingReplacement);
			//iaReturn = UnicodeZZZ.toIntArray(sReturn);
			char[]caReturn = sReturn.toCharArray();
			iaReturn = CharArrayZZZ.toIntArray(caReturn);
			this.setDecryptedValues(iaReturn);
		}//end main:
		return iaReturn;   
	}
	
	@Override
	public int getOffsetForUtf8Range() {
		return AbstractVigenereNnZZZ.iOffsetForUtf8Range;
	}

	@Override
	public int getOffsetForAsciiRange() {
		try {
			return this.getCharacterPoolList().size();//Vigenere256ZZZ.iOffsetForAsciiRange;
		} catch (ExceptionZZZ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} 
	}

	@Override
	public int[] fromUtf8ToAsciiForOffset(int[] p) {
		 //Analog zu Vigenere96.java, nur mit 32
		 //ImBuch, auf Seite 34 steht... "wird auf space (Nr. 32) bezogen, 
		 //    for(int i=0; i < p.length; i++) {
		 //    	p[i]=p[i]-65;
		 //    }
		 return UnicodeZZZ.fromUtf8ToAsciiForNn(p);
	}

	@Override
	public int[] fromAsciiToUtf8ForOffset(int[] p) {
		//Analog zum buch, nur mit +32
	    //Im Buch auf Seite 36 wird dann 32 draufgerechnet ("blank")
	    return UnicodeZZZ.fromAsciiToUtf8ForNn(p);//funktioniert bei Viginere26 Verschluesselung, es wird 65 draufgerechnet
	}
	
	@Override
	public CharacterExtendedZZZ getCharacterMissingReplacment() throws ExceptionZZZ {
		if(this.objCharacterMissingReplacement==null) {
			this.objCharacterMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolEnabledZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
		}
		return this.objCharacterMissingReplacement;		
	}
	public void setCharacterMissingReplacement(CharacterExtendedZZZ objCharacterMissingReplacement) throws ExceptionZZZ {
		this.objCharacterMissingReplacement = objCharacterMissingReplacement;
	}

	@Override
	public ArrayListZZZ<CharacterExtendedZZZ> getCharacterPoolList() throws ExceptionZZZ {
		if(ArrayListUtilZZZ.isEmpty(this.listasCharacterPool)) {
			String sCharacterPoolBase = this.getCharacterPoolBase();
			String sCharacterPoolAdditional = this.getCharacterPoolAdditional();
			
			boolean bUseUppercasePool = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEUPPERCASE);
			boolean bUseLowercasePool = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USELOWERCASE);
			boolean bUseNumericPool = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USENUMERIC);
			boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
			boolean bUseAdditionalCharacter = this.getFlag(ICharacterPoolEnabledZZZ.FLAGZ.USEADDITIONALCHARACTER);
			String abcABC = CharacterExtendedZZZ.computeCharacterPoolExtended(sCharacterPoolBase, bUseUppercasePool, bUseLowercasePool, bUseNumericPool, bUseBlank, bUseAdditionalCharacter, sCharacterPoolAdditional);
					
			ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = CharacterExtendedZZZ.computeListFromCharacterPoolString(abcABC);
			this.listasCharacterPool = listasCharacterPool;
		}
		return this.listasCharacterPool;
	}

	@Override
	public String getCharacterPool() throws ExceptionZZZ {
		
		ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();		
		String sCharacterPool =listasCharacterPool.debugString("");
		return sCharacterPool;		
	}
	
	public static String getCharacterPoolBaseDefault() {
		return CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
	}

	public static String getCharacterPoolAdditionalDefault() {
		return CharacterExtendedZZZ.sCHARACTER_ADDITIONAL;
	}
	
	@Override
	public String getCharacterPoolBase() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sCharacterPoolBase)) {
			this.sCharacterPoolBase = AbstractVigenereNnZZZ.getCharacterPoolBaseDefault();
		}
		return this.sCharacterPoolBase;
	}
	@Override
	public void setCharacterPoolBase(String sCharacterPoolBase) throws ExceptionZZZ {
		this.sCharacterPoolBase = sCharacterPoolBase;
		
		//!!! nach dem Ändern der Base die Liste leeren, so dass diese neu aufgebaut würde.
		this.listasCharacterPool.clear();
	}
	@Override
	public String getCharacterPoolAdditional() throws ExceptionZZZ {
		if(StringZZZ.isEmpty(this.sCharacterPoolAdditional)) {
			this.sCharacterPoolAdditional = AbstractVigenereNnZZZ.getCharacterPoolAdditionalDefault();
		}
		return this.sCharacterPoolAdditional;
	}
	@Override
	public void setCharacterPoolAdditional(String sCharacterPoolAdditional) throws ExceptionZZZ {
		this.sCharacterPoolAdditional = sCharacterPoolAdditional;
		
		//!!! nach dem Ändern der Zusatzzeichen die Liste leeren, so dass diese neu aufgebaut würde.
		this.listasCharacterPool.clear();
	}
	
	@Override
	public int[] getEncryptedCharacterPoolPosition() {
		return this.iaEncryptedPositionInPool;
	}
	@Override	
	public void setEncryptedCharacterPoolPosition(int[]iaPosition) {
		this.iaEncryptedPositionInPool = iaPosition;
	}
	@Override
	public int[] getDecryptedCharacterPoolPosition() {
		return this.iaDecryptedPositionInPool;
	}
	@Override
	public void setDecryptedCharacterPoolPosition(int[]iaPosition) {
		this.iaDecryptedPositionInPool = iaPosition;
	}
	
	public int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{
			
			ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool = this.getCharacterPoolList();
			iaReturn = AbstractVigenereNnZZZ.makeOriginalValuesAsCharacterPoolPosition(iaPure, listasCharacterPool);
		}//end main:
		return iaReturn;
	}
	
	/** String-Zeichen werden im Character Pool gesucht und es kommt dort der Indexwert zurück.
	 * Dies hier ist die Methode fuer die Decodierung.
	 * 
	 *  -1 wird zurückgegeben, wenn das String-Zeichen nicht im CharacterPool enthalten ist.
	 *  insbesondere dann wenn es das "missing Zeichen ist"
	 *  
	 *  Das Problem ist, das ggfs. durch die Unicode-Betrachtung mit Unicode.toIntArray(MissingCharacter)
	 *  das Array größer geworden ist. Das muss man wieder um jeweils 1 reduzieren.
	 * @param iaPure
	 * @param listasCharacterPool
	 * @param objCharMissingReplacementIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.01.2023, 13:45:09
	 */
	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool, CharacterExtendedZZZ objCharMissingReplacementIn) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			ArrayList<Integer>listaInt=new ArrayList<Integer>();//Temporäre Liste, damit ggf. besondere Zeichen angemarkert werden können.
						
			CharacterExtendedZZZ objCharMissingReplacement = null;
			if(objCharMissingReplacementIn==null) {
				objCharMissingReplacement=new CharacterExtendedZZZ(ICharacterPoolEnabledZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
			}else {
				objCharMissingReplacement=objCharMissingReplacementIn;
			}
			int[]iaUnicodeCharMissingReplacement = UnicodeZZZ.toIntArray(objCharMissingReplacement.toString());
	
		
			boolean bCharReplacementFirstIntegerFound=false;
			for(int i=0; i<iaPure.length;i++){
				int iPure = iaPure[i];
				
				if(iPure==iaUnicodeCharMissingReplacement[0]) { //objCharMissingReplacement.getChar()) {
					listaInt.add(new Integer(-1));
					bCharReplacementFirstIntegerFound=true;
				}else if(iPure==iaUnicodeCharMissingReplacement[1]&& bCharReplacementFirstIntegerFound) {
					listaInt.add(new Integer(-1000));													
					bCharReplacementFirstIntegerFound=false;
				}else {
					char c = (char) iPure;
					CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(c);				
					int iReturn = listasCharacterPool.getIndex(objChar);
					listaInt.add(new Integer(iReturn));
					
					bCharReplacementFirstIntegerFound=false;
				}								
			}
			
			//Nun Array ggfs. wieder "zusammenfassen"
			ArrayListUtilZZZ.remove(listaInt, new Integer(-1000));
			iaReturn = ArrayListUtilZZZ.toIntArray(listaInt);
			
		}//end main:
		return iaReturn;				
	}
	
	/** String-Zeichen werden im Character Pool gesucht und es kommt dort der Indexwert zurück.
	 *  -1 wird zurückgegeben, wenn das String-Zeichen nicht im CharacterPool enthalten ist.
	 * @param iaPure
	 * @param listasCharacterPool
	 * @param objCharMissingReplacementIn
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.01.2023, 13:45:09
	 */
	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListZZZ<CharacterExtendedZZZ> listasCharacterPool) throws ExceptionZZZ {
		int[] iaReturn = null;
		main:{			
			iaReturn = new int[iaPure.length];
						
			for(int i=0; i<iaPure.length;i++){
				int iPure = iaPure[i];
				
				char c = (char) iPure;
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(c);				
				int iReturn = listasCharacterPool.getIndex(objChar);
				iaReturn[i] = iReturn;

			}
		}//end main:
		return iaReturn;
	}
	
	
//	public static int[] makeOriginalValuesAsCharacterPoolPosition(int[] iaPure, ArrayListExtendedZZZ<CharacterExtendedZZZ> listasCharacterPool, int iNotInPoolCharacter) throws ExceptionZZZ {
//		int[] iaReturn = null;
//		main:{			
//			iaReturn = new int[iaPure.length];			
//			for(int i=0; i<iaPure.length;i++){
//				int iPure = iaPure[i];
//				char c = (char) iPure;
//				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(c);				
//				int iReturn = listasCharacterPool.getIndex(objChar);
//				if(iReturn==-1) {
//					//Fuer Zeichen, die nicht im CharacterPool sind, wurde -1 zurueckgegeben.
//					//Ohne besondere Betrachtung wuerde dann das letzte Zeichen des Pools genommen. Also z.B. 9 wenn "numeric" dazugenommen wurde.
//					iReturn = iNotInPoolCharacter;					
//				}
//				iaReturn[i] = iReturn;				
//			}
//		}//end main:
//		return iaReturn;
//	}
	
	@Override
	public boolean getFlag(IROTUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(IROTUserZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(IROTUserZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(IROTUserZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(IROTUserZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.proofFlagExists(objEnumFlag.name());
	}
	
	
	@Override
	public boolean getFlag(ICharacterPoolEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
		return this.getFlag(objEnumFlag.name());
	}
	
	@Override
	public boolean setFlag(ICharacterPoolEnabledZZZ.FLAGZ objEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		return this.setFlag(objEnumFlag.name(), bFlagValue);
	}
	
	@Override
	public boolean[] setFlag(ICharacterPoolEnabledZZZ.FLAGZ[] objaEnumFlag, boolean bFlagValue) throws ExceptionZZZ {
		boolean[] baReturn=null;
		main:{
			if(!ArrayUtilZZZ.isNull(objaEnumFlag)) {
				baReturn = new boolean[objaEnumFlag.length];
				int iCounter=-1;
				for(ICharacterPoolEnabledZZZ.FLAGZ objEnumFlag:objaEnumFlag) {
					iCounter++;
					boolean bReturn = this.setFlag(objEnumFlag, bFlagValue);
					baReturn[iCounter]=bReturn;
				}
			}
		}//end main:
		return baReturn;
	}

	@Override
	public boolean proofFlagExists(ICharacterPoolEnabledZZZ.FLAGZ objEnumFlag) throws ExceptionZZZ {
			return this.proofFlagExists(objEnumFlag.name());
		}
}
