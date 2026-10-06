package basic.zBasic.util.crypt.code;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.crypt.code.CryptAlgorithmMappedValueZZZ.CipherTypeZZZ;
import basic.zBasic.util.datatype.character.AsciiZZZ;

/**
 * Returns a list with Strings which are rotated ROT-n. n = 26 - listIndex
 *
 * Source: http://www.rot-n.com/?page_id=4 
 *
 * @param input the string to mutate
 * @param numeric include numeric values
 * @return a list with mutated strings
 */
public class ROTnumericZZZ extends AbstractROTZZZ implements ICharacterPoolEnabledConstantZZZ{
	private static final long serialVersionUID = -7120488041281101978L;
	ROTnumericZZZ() throws ExceptionZZZ {//Konstruktor auf PAKETEBENE Sichtbar
		super();
		String[] saFlagControl = {"init"};
		RotNumericNew_(-1,saFlagControl);
	}
	ROTnumericZZZ(String[] saFlagControl) throws ExceptionZZZ{
		super();
		RotNumericNew_(-1,saFlagControl);
	}
	public ROTnumericZZZ(int iCryptKey) throws ExceptionZZZ {
		super();
		RotNumericNew_(iCryptKey,null);
	}
	public ROTnumericZZZ(int iCryptKey, String sFlagControl) throws ExceptionZZZ {
		super();
		String[]saFlagControl = new String[1];
		saFlagControl[0]=sFlagControl;
		RotNumericNew_(iCryptKey,saFlagControl);
	}
	
	@Override
	public Enum<?> getCipherTypeAsEnum() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnumeric;
	}
	
	@Override
	public CipherTypeZZZ getCipherType() throws ExceptionZZZ {
		return CryptAlgorithmMappedValueZZZ.CipherTypeZZZ.ROTnumeric;
	}
	
	private boolean RotNumericNew_(int iCryptKey, String[] saFlagControlIn) throws ExceptionZZZ {
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
					 this.printlnDate(ReflectCodeZZZ.getPositionCurrent() + ": " + sLog);
					 
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
		
		this.setCryptNumber(iCryptKey);
		
	}//end main:
		return bReturn;
	}
	
	
	
	@Override
	public String encrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		boolean bCaseChange = this.getFlag(IROTUserZZZ.FLAGZ.USESTRATEGY_CASECHANGE);
		boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
		return ROTnumericZZZ.encrypt(sInput, iCryptKey, bCaseChange,bUseBlank);
	}
	@Override
	public String decrypt(String sInput) throws ExceptionZZZ {
		int iCryptKey = this.getCryptNumber();
		boolean bCaseChange = this.getFlag(IROTUserZZZ.FLAGZ.USESTRATEGY_CASECHANGE);
		boolean bUseBlank = this.getFlag(IROTUserZZZ.FLAGZ.USEBLANK);
		return ROTnumericZZZ.decrypt(sInput, iCryptKey, bCaseChange,bUseBlank);
	}
	
	
	//###########################################
	public static String encrypt(String input, int iCryptKey, boolean bCaseChange, boolean bBlank) throws ExceptionZZZ{
		return ROTnumericZZZ.crypt_(input, iCryptKey, bCaseChange,bBlank, false);
	}
	public static String decrypt(String input, int iCryptKey, boolean bCaseChange, boolean bBlank) throws ExceptionZZZ{
		return ROTnumericZZZ.crypt_(input, iCryptKey, bCaseChange,bBlank, true);
	}
	private static String crypt_(String input, int iCryptKey, boolean bCaseChange, boolean bBlank, boolean bReverse) throws ExceptionZZZ {
	   String sReturn = new String();
	   main:{		  
		   if(!bCaseChange) {
			   if(bReverse) {
				   sReturn = ROTnumericZZZ.decryptNumericSimple_(input, iCryptKey);
			   }else {
				   sReturn = ROTnumericZZZ.encryptNumericSimple_(input, iCryptKey);
			   }
		   }else {
			   if(!bBlank) {
				   if(bReverse) {
					   sReturn = ROTnumericZZZ.decryptNumericAdvanced_(input, iCryptKey);
				   }else {
					   sReturn = ROTnumericZZZ.encryptNumericAdvanced_(input, iCryptKey);
				   }
			   }else{
				   if(bReverse) {
					   sReturn = ROTnumericZZZ.decryptNumericAdvancedBlank_(input, iCryptKey);
				   }else {
					   sReturn = ROTnumericZZZ.encryptNumericAdvancedBlank_(input, iCryptKey);
				   }
				   
			   }
		   }
	   }//end main:
	   return sReturn;
	}
	
	/** Leerzeichen wird zu Zahlen.
	 *  Zahlen werden ggfs. in den Grossbuchstabenbereich verschoben.
	 *  Grossbuchstaben werden ggfs. in den Kleinbuchstabenbereich verschoben.
	 *  Kleinbuchstaben werden ggfs. in den Zahlenbereich verschoben.
	 *  
	 *  Merke: Bei 63 ist man einmal durch und der verschluesslete Werte entspricht dem entschlusselten wert
     *   	   Anzahl Leerzeichen + Anzahl Ziffern + Anzahl Kleinbuchstaben + Anzahl Grossbuchstaben 
	 *  
	 * @param input
	 * @param iCryptKey ( 0 bis 63)
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String encryptNumericAdvancedBlank_(String input, int iCryptKey) throws ExceptionZZZ {
		   String sReturn = new String();
		   main:{
			   if(iCryptKey<0 || iCryptKey >=65) {
				   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range only from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
			   }
			   
			   String result = "";	   
			   int length = input.length();
			   int iStart = 0;
			    
		        for (int i = iStart; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;
		            			           
		            if(AsciiZZZ.isBlank(ascii)) {		            	
		            	 rotated=(char)AsciiZZZ.fromBlankAdvancedBlank(rotated, iCryptKey);			            				          
		            } else if(AsciiZZZ.isLetterUppercase(ascii)) {			            	
		                rotated=(char)AsciiZZZ.fromLetterUppercaseAdvancedBlank(rotated,iCryptKey);			                			          			                
		            }else if(AsciiZZZ.isLetterLowercase(ascii)) {
		            	rotated = (char) AsciiZZZ.fromLetterLowercaseAdvancedBlank(rotated,iCryptKey);			              
		            }else if(AsciiZZZ.isNumber(ascii)) {
		            	rotated = (char) AsciiZZZ.fromNumberAdvancedBlank(rotated,iCryptKey);			             
		            }
		            result += (char) rotated;
		        }				   				   
		        sReturn = result;			   
		   }//end main:
		   return sReturn;
	}
	
	/** Leerzeichen wird zu Zahlen.
	 *  Zahlen werden ggfs. in den Grossbuchstabenbereich verschoben.
	 *  Grossbuchstaben werden ggfs. in den Kleinbuchstabenbereich verschoben.
	 *  Kleinbuchstaben werden ggfs. in den Zahlenbereich verschoben.
	 *  
	 *  Merke: Das Entschluesseln ist quasi ein "Rueckwaerts - Rechnen".
	 *  
	 *  Merke: Bei 63 ist man einmal durch und der verschluesslete Werte entspricht dem entschlusselten wert
     *   	   Anzahl Leerzeichen + Anzahl Ziffern + Anzahl Kleinbuchstaben + Anzahl Grossbuchstaben 
	 *  
	 * @param input
	 * @param iCryptKey ( 0 bis 63)
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String decryptNumericAdvancedBlank_(String input, int iCryptKey) throws ExceptionZZZ {
		   String sReturn = new String();
		   main:{
			   if(iCryptKey<0 || iCryptKey >=65) {
				   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range only from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
			   }
			   
			   String result = "";			   
			   int length = input.length();
			   int iStart = 0;

				//REVERSE, für das decrypten
		        for (int i = iStart; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;         		            
		            if(AsciiZZZ.isLetterUppercase(ascii)) {
		            	rotated = (char)AsciiZZZ.fromLetterUppercaseAdvancedBlankReverse(rotated,iCryptKey);				          
		            }else if(AsciiZZZ.isLetterLowercase(ascii)) {
		            	rotated = (char)AsciiZZZ.fromLetterLowercaseAdvancedBlankReverse(rotated, iCryptKey);
		            }else if(AsciiZZZ.isBlank(ascii)) {				            	
		            	rotated = (char)AsciiZZZ.fromBlankAdvancedBlankReverse(rotated, iCryptKey);				            				            
		            }else if (AsciiZZZ.isNumber(ascii)) {				            	
		            	rotated = (char)AsciiZZZ.fromNumberAdvancedBlankReverse(rotated,iCryptKey);
		            }
		            result += (char) rotated;
		        }				 
		        sReturn = result;			   
		   }//end main:
		   return sReturn;
	}
	
	/** Leerzeichen bleiben erhalten.
	 *  Zahlen werden ggfs. in den Grossbuchstabenbereich verschoben.
	 *  Grossbuchstaben werden ggfs. in den Kleinbuchstabenbereich verschoben.
	 *  Kleinbuchstaben werden ggfs. in den Zahlenbereich verschoben.
	 *  
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String decryptNumericAdvanced_(String input, int iCryptKey) throws ExceptionZZZ {
		   String sReturn = new String();
		   main:{
			   if(iCryptKey<0 || iCryptKey >=65) {
				   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
			   }
			   
			   //REVERSE, für das decrypten
			   String result = "";			   				  
			   int length = input.length();
			   int iStart = 0;
			    
		        for (int i = iStart; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;

		            if(AsciiZZZ.isLetterUppercase(ascii)) {
		            	rotated = (char) AsciiZZZ.fromLetterUppercaseReverse(ascii,iCryptKey);			            			          
		            }else if(AsciiZZZ.isLetterLowercase(ascii)) {			            	
		            	rotated = (char) AsciiZZZ.fromLetterLowercaseReverse(rotated,iCryptKey);			            	
		            }else if(AsciiZZZ.isNumber(ascii)) {
		            	rotated = (char) AsciiZZZ.fromNumberReverse(rotated,iCryptKey);			            	         
		            }
		            result += (char) rotated;
		        }//end for			   
		        sReturn = result;
		   }//end main:
		   return sReturn;
		}
	
	/** Leerzeichen bleiben erhalten.
	 *  Zahlen werden ggfs. in den Grossbuchstabenbereich verschoben.
	 *  Grossbuchstaben werden ggfs. in den Kleinbuchstabenbereich verschoben.
	 *  Kleinbuchstaben werden ggfs. in den Zahlenbereich verschoben.
	 *  
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String encryptNumericAdvanced_(String input, int iCryptKey) throws ExceptionZZZ {
		   String sReturn = new String();
		   main:{			   
			   if(iCryptKey<0 || iCryptKey >=65) {
				   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				   throw ez;
			   }
			   
			   String result = ""; 
			   int length = input.length();
			   int iStart = 0; 
		        for (int i = iStart; i < length; i++) {
		            char ascii = input.charAt(i);
		            char rotated = ascii;
		            if(AsciiZZZ.isLetterUppercase(ascii)) {		            			            	
		                rotated = (char) AsciiZZZ.fromLetterUppercase(rotated, iCryptKey);		               		               
		            }else if (AsciiZZZ.isLetterLowercase(ascii)) {
		            	rotated = (char) AsciiZZZ.fromLetterLowercase(rotated, iCryptKey);
		            }else if(AsciiZZZ.isNumber(ascii)) {
		            	rotated = (char) AsciiZZZ.fromNumber(rotated, iCryptKey);		            			               		               
		            }
		            result += (char) rotated;
		        }			  		      
		        sReturn = result;
		   }//end main:
		   return sReturn;
		}
	
	/** Leerzeichen bleibt Leerzeichen.
	 *  Zahlen werden mit anderen Zahlen vertauscht, d.h. sie bleiben Zahlen.
	 *  Kleinbuchstaben bleiben Kleinbuchstaben, 
	 *  Grossbuchstaben bleiben Grossbuchstaben.
	 * 
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String decryptNumericSimple_(String input, int iCryptKey) throws ExceptionZZZ {
	   String sReturn = new String();
	   main:{
		   if(iCryptKey<0 || iCryptKey >635) {
			   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;
		   }
		   
		   //REVERSE, für das decrypten
		   String result = "";			  
		   int length = input.length();
		   int iStart = 0;
		   
		   for (int i = iStart; i < length; i++) {
			   char ascii = input.charAt(i);
			   char rotated = ascii;
	           if(AsciiZZZ.isBlank(ascii)) {
	        	   //Merke: Leerzeichen bleibt unveraendert			            			          
		        }else if(AsciiZZZ.isLetterUppercase(ascii)) {
		        	rotated = (char) AsciiZZZ.fromLetterUppercaseReverseTYPECONSTANT(ascii,iCryptKey);
		            }else if(AsciiZZZ.isLetterLowercase(ascii)) {
		            	rotated = (char) AsciiZZZ.fromLetterLowercaseReverseTYPECONSTANT(ascii, iCryptKey);
		            }else if(AsciiZZZ.isNumber(ascii)) {
		            	rotated = (char) AsciiZZZ.fromNumberReverseTYPECONSTANT(ascii,iCryptKey);
		            }			           
		            result += (char) rotated;
		    }			   
	        sReturn = result;			   
	   }//end main:
	   return sReturn;
	}
	
	/** Leerzeichen bleibt Leerzeichen.
	 *  Zahlen werden mit anderen Zahlen vertauscht, d.h. sie bleiben Zahlen.
	 *  Kleinbuchstaben bleiben Kleinbuchstaben, 
	 *  Grossbuchstaben bleiben Grossbuchstaben.
	 * 
	 * @param input
	 * @param iCryptKey
	 * @param bReverse
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 08.02.2023, 10:39:45
	 */
	private static String encryptNumericSimple_(String input, int iCryptKey) throws ExceptionZZZ {
	   String sReturn = new String();
	   main:{
		   if(iCryptKey<0 || iCryptKey >65) {
			   ExceptionZZZ ez = new ExceptionZZZ("iCryptKey must range from 0 to 64", iERROR_PARAMETER_VALUE, ROTnumericZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
			   throw ez;
		   }
		   
		   String result = "";		 
		   int length = input.length();
		   int iStart = 0;
		    
	        for (int i = iStart; i < length; i++) {
	            char ascii = input.charAt(i);
	            char rotated = ascii;
	            if(AsciiZZZ.isBlank(ascii)) {
	            	//Merke: Leerzeichen bleibt unveraendert
	            }else if(AsciiZZZ.isLetterUppercase(ascii)) {
	                rotated = (char) (rotated + iCryptKey);
	                while(!AsciiZZZ.isLetterUppercase(rotated)) {
		                if(AsciiZZZ.isHigherLetterUppercase(rotated)) {
		                    rotated += -90 + 64;			                	
		                }else if(AsciiZZZ.isLowerLetterUppercase(rotated)) {
		                	rotated += -64 + 90;
		                }		                		                
	                }		                		           
	            }else if (AsciiZZZ.isLetterLowercase(ascii)) {
	                rotated = (char) (rotated + iCryptKey);
	                while(!AsciiZZZ.isLetterLowercase(rotated)) {
		                if(AsciiZZZ.isHigherLetterLowercase(rotated)) {
		                    rotated += -122 + 96;			                	
		                }else if (AsciiZZZ.isLowerLetterLowercase(rotated)) {
		                    rotated += -96 + 122;
		                }
	                }
	            }else if (AsciiZZZ.isNumber(ascii)) {
	                rotated = (char) (rotated + iCryptKey);
	            	while(!AsciiZZZ.isNumber(rotated)) {
	            		if(AsciiZZZ.isHigherNumber(rotated)) {
	            			rotated += -57 + 47; 
	            		}else if(AsciiZZZ.isLowerNumber(rotated)) {
	            			rotated += -47 + 57;
	            		}		           
	                }		            	
	            }		            
	            result += (char) rotated;
	        }			   			  
	        sReturn = result;			   
	   }//end main:
	   return sReturn;
	}
}
