package basic.zBasic.util.datatype.character;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.crypt.code.ICharacterPoolEnabledZZZ;
import basic.zBasic.util.crypt.code.VigenereNnZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**
 * Merke: Character ist final, kann also nicht selbst "extended" werden 
 * 
 * @author Fritz Lindhauer, 23.12.2022, 10:17:15
 * 
 */
public class CharacterExtendedZZZ extends AbstractObjectWithExceptionZZZ implements ICharacterExtendedZZZ, Comparable<ICharacterExtendedZZZ>{
	private static final long serialVersionUID = 1L;
	private Character objChar=null; 
	
	//Kontruktor verstecken
	private CharacterExtendedZZZ() {
		super();
	}
	public CharacterExtendedZZZ(char c) throws ExceptionZZZ {
		Character objChar = new Character(c);
		CharacterExtendedNew_(objChar);
	}
	public CharacterExtendedZZZ(Character objChar) throws ExceptionZZZ {
		super();
		CharacterExtendedNew_(objChar);
	}
	public CharacterExtendedZZZ(int i) throws ExceptionZZZ {
		Character objChar = new Character((char) i);
		CharacterExtendedNew_(objChar);
	}
	
	private void CharacterExtendedNew_(Character objChar) throws ExceptionZZZ {
		if(objChar== null){
			ExceptionZZZ ez = new ExceptionZZZ("Character Object missing", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
		
		this.setChar(objChar);
	}
	
	@Override
	public void setChar(Character objChar) {
		this.objChar=objChar;
	}
	
	@Override
	public char getChar() {
		return this.objChar.charValue();
	}
		
	public String toString() {
		Character objChar = this.getChar();
		return objChar.toString();
	}
	
	//##############################
	public static String computeCharacterPoolExtended(String sCharacterPoolBaseIn, boolean bUseUppercase, boolean bUseLowercase, boolean bUseNumeric, boolean bUseBlank, boolean bUseAdditionalCharacter, String sCharacterPoolAdditionalIn) throws ExceptionZZZ {
		String sReturn="";
		main:{
			String sCharacterPoolBase;
			if(StringZZZ.isEmpty(sCharacterPoolBaseIn)) {
				sCharacterPoolBase=CharacterExtendedZZZ.sCHARACTER_POOL_DEFAULT;
			}else {
				sCharacterPoolBase = sCharacterPoolBaseIn;
			}
			sReturn = sCharacterPoolBase;
			
			
			String sCharacterPoolAdditional="";
			if(sCharacterPoolAdditionalIn==null) {
				sCharacterPoolAdditional = CharacterExtendedZZZ.sCHARACTER_ADDITIONAL;				
			}else {
				sCharacterPoolAdditional = sCharacterPoolAdditionalIn;
			}
			
			
			//Wichtig: Das Leerzeichen bei der Erweiterung der Liste raus. Spätestens beim Dekodieren faellt einem die andere Position (beim Umdrehen des Strings) auf die Fuesse.
			if(bUseUppercase) {
				String stemp = sCharacterPoolBase.trim().toUpperCase();
				sReturn = StringZZZ.appendMissing(sReturn, stemp);
			}
			
			if(bUseLowercase) {
				String stemp = sCharacterPoolBase.trim().toLowerCase();
				sReturn = StringZZZ.appendMissing(sReturn, stemp);
			}
			
			if(bUseNumeric) {
				sReturn = StringZZZ.appendMissing(sReturn, CharacterExtendedZZZ.sCHARACTER_NUMERIC);
			}
			
			if(bUseBlank) {
				sReturn = StringZZZ.appendMissing(sReturn, " ");
			}
			
			if(bUseAdditionalCharacter) {
				sReturn = StringZZZ.appendMissing(sReturn, sCharacterPoolAdditional);
			}
			
		}//end main:
		return sReturn;
	}
	
	public static ArrayListZZZ<CharacterExtendedZZZ> computeListFromCharacterPoolString(String sCharacterPool) throws ExceptionZZZ {
		ArrayListZZZ<CharacterExtendedZZZ> listReturn = null;
		main:{
					
			//IDEE DER NN-Behandlung:
			//Jeden Buchstaben in einen Integer-Wert ueberfuehren, der seine Position in dem Character-Pool hat.			
			//MERKE: Wg. der Zuordnung zu einer Map muss sichergestellt sein, dass kein Zeichen im CharacterPool doppelt vorkommt.
			//+++++++++++ CharacterPool normieren
			int len = sCharacterPool.length();			
			listReturn = new ArrayListZZZ<CharacterExtendedZZZ>();
			for (int i = 0; i < len; i++) {		
				CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(sCharacterPool.charAt(i));
				listReturn.addUnique(objChar);				
		    }			
			//+++++++++++
			
		} //End main:
		return listReturn;		
	}
	
	public static String computeStringFromCharacterPoolPosition(int[] iaPosition, ArrayListZZZ<CharacterExtendedZZZ>listasCharacterPool) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ArrayUtilZZZ.isNull(iaPosition))break main;
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			//die Arraylist nutzen zum bestimmen der Zeichen
		    StringBuilder sb = new StringBuilder();
		    for(int i = 0; i<iaPosition.length;i++) {
		    	int iCharPos = iaPosition[i];		    	
		    	CharacterExtendedZZZ objChar = listasCharacterPool.get(iCharPos);
		    	sb.append(objChar);
		    }
		    sReturn = sb.toString();
		}//end main:
		return sReturn;		
	}
	
	public static String computeStringFromCharacterPoolPosition(int[] iaPosition, ArrayListZZZ<CharacterExtendedZZZ>listasCharacterPool, CharacterExtendedZZZ objCharMissingReplacementIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(ArrayUtilZZZ.isNull(iaPosition))break main;
			if(ArrayListUtilZZZ.isEmpty(listasCharacterPool)) {
				String sLog = "Character pool not provided.";
				 //this.logLineDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);								 
				ExceptionZZZ ez = new ExceptionZZZ(sLog, iERROR_PARAMETER_MISSING, VigenereNnZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;				
			}
			
			CharacterExtendedZZZ objCharMissingReplacement=null;
			if(objCharMissingReplacementIn==null) {
				objCharMissingReplacement = new CharacterExtendedZZZ(ICharacterPoolEnabledZZZ.cCHARACTER_MISSING_REPLACEMENT_DEFAULT);
			}else {
				objCharMissingReplacement = objCharMissingReplacementIn;
			}
			
			//Vorab pruefen, ob alle Wert nicht die Groesse des CharacterPools uebersteigen.
			//Das ist ggfs. der Fall, wenn kein Schluesselwort bei Vigenere angegeben worden ist.
			boolean bUseCharacterPool = true;
			for(int i= 0; i<iaPosition.length;i++) {
				int iCharPos = iaPosition[i];
				if(iCharPos>listasCharacterPool.size()) {
					bUseCharacterPool = false;
					break;
				}
			}
			
			
		    StringBuilder sb = new StringBuilder();
		    
		    //die Arraylist nutzen zum bestimmen der Zeichen, entweder mit CharacterPool oder so..
		    if(bUseCharacterPool) { 
			    for(int i = 0; i<iaPosition.length;i++) {
			    	int iCharPos = iaPosition[i];
			    	if(iCharPos==-1 && objCharMissingReplacement!=null) {
			    		sb.append(objCharMissingReplacement.getChar());
			    	}else {		
			    		CharacterExtendedZZZ objChar = listasCharacterPool.get(iCharPos);			    		
				    	sb.append(objChar);
			    	}
			    }
		    }else {
		    	for(int i = 0; i<iaPosition.length;i++) {
			    	int iCharPos = iaPosition[i];
			    	if(iCharPos==-1 && objCharMissingReplacement!=null) {
			    		sb.append(objCharMissingReplacement.getChar());
			    	}else {		
			    		CharacterExtendedZZZ objChar = new CharacterExtendedZZZ(iCharPos);			    		
				    	sb.append(objChar);
			    	}
			    }
		    }
		    sReturn = sb.toString();
		}//end main:
		return sReturn;		
	}
	
	@Override
	public int compareTo(ICharacterExtendedZZZ o) {
		//Das macht lediglich .sort funktionsfähig und wird nicht bei .equals(...) verwendet.
		int iReturn = 0;
		main:{
			if(o==null)break main;
			
			int itoCompare = o.getChar();
			int i = this.getChar();
			
			if(i==itoCompare) iReturn = 1;
		}
		return iReturn;
	}
	
	 /**
	   * Define equality of state.
	   */
	   @Override 
	   public boolean equals(Object aThat) {
	     if (this == aThat) return true;
	     if (!(aThat instanceof ICharacterExtendedZZZ)) return false;
	     ICharacterExtendedZZZ that = (ICharacterExtendedZZZ)aThat;
	    	     
	     if(that.getChar() == this.getChar()) {
	    	 return true;
	     }else {
	    	 return false;
	     }
	       
	   }

	   /**
	   * A class that overrides equals must also override hashCode.
	   */
	   @Override 
	   public int hashCode() {
		   char c = this.getChar();
		   Character objCharacter = new Character(c);
		   return objCharacter.hashCode();     
	   }
}
