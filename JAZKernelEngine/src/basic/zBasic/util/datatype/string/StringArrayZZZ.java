package basic.zBasic.util.datatype.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.abstractArray.ArrayUniqueUtilZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListUtilZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.datatype.json.JsonUtilZZZ;
import basic.zBasic.util.math.MathZZZ;

/**
 * @author 0823
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
//Generisch ist nicht sinnvoll, bei den statischen Methoden  public class StringArrayZZZ<T> implements IConstantZZZ{
public class StringArrayZZZ extends AbstractObjectWithExceptionZZZ{
	private String[] saIntern;
	private boolean bIsString = false;

	//Konstruktoren
	public StringArrayZZZ(String args[]) throws ExceptionZZZ{
		if(args==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		this.saIntern = args;
		this.bIsString = true;
	}
	
	public StringArrayZZZ( Object args[]) throws ExceptionZZZ{
		if(args==null){
			ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, this, ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		ArrayList<String> listas = new ArrayList<String>();
		for(Object obj : args){
			String s = obj.toString();
			listas.add(s);
		}
		
		String[] saArgs = listas.toArray(new String[listas.size()]);
		this.saIntern = saArgs;
		this.bIsString = true;
	}
	
	//#########################################################
	//### apend
	///#####################################
	
	//Strings als Array hintereinander haengen
	public static String[] append(String sString1, String sString2) throws ExceptionZZZ{
		return StringArrayZZZ.append(sString1, sString2, (String)null);
	}
	
	public static String[] append(String sString1, String sString2, String sFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			String[]saFlag=null;
			if(sFlagIn!=null) {
				saFlag=new String[1];
				saFlag[0]=sFlagIn;
			}
			
			String[]saString1=new String[1];
			saString1[0]=sString1;
						
			String[]saString2=new String[1];
			saString2[0]=sString2;
						
			objReturn = StringArrayZZZ.append(saString1, saString2, saFlag);
		}//end main:
		return objReturn;
	}
	
	public static String[] append(String sString2, String[] saString1) throws ExceptionZZZ{
		return StringArrayZZZ.append(sString2, saString1, (String)null);		
	}
	
	public static String[] append(String sString2, String[] saString1, String sFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			String[]saFlag=null;
			if(sFlagIn!=null) {
				saFlag=new String[1];
				saFlag[0]=sFlagIn;
			}
			
			String[]saString2=new String[1];
			saString2[0]=sString2;
			objReturn = StringArrayZZZ.append(saString2, saString1, saFlag);
		}//end main:
		return objReturn;		
	}
	
	public static String[] append(String[] saString1, String sString2, String sFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			String[]saFlag=null;
			if(sFlagIn!=null) {
				saFlag=new String[1];
				saFlag[0]=sFlagIn;
			}
			
			String[]saString2=new String[1];
			saString2[0]=sString2;
			objReturn = StringArrayZZZ.append(saString1, saString2, saFlag);
		}//end main:
		return objReturn;
	}
	
	
	public static String[] append(String[] saString1, String[] saString2) throws ExceptionZZZ{
		return StringArrayZZZ.append(saString1, saString2, (String)null);
	}
	
	public static String[] append(String[] saString1, String[] saString2, String sFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			String[]saFlag=null;
			if(sFlagIn!=null) {
				saFlag=new String[1];
				saFlag[0]=sFlagIn;
			}
			objReturn = StringArrayZZZ.append(saString1, saString2, saFlag);
		}//end main:
		return objReturn;
	}
	
	public static String[] append(String[] saString1, String[] saString2, String[] saFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(saString1==null && saString2==null) break main;
		if(saString1==null){
			objReturn = new String[saString2.length];
			System.arraycopy(saString2,0,objReturn, 0, saString2.length);
			break main;
		}else if(saString2 == null){
			objReturn = new String[saString1.length];
			System.arraycopy(saString1,0,objReturn, 0, saString1.length);
			break main;			
		}
		
		boolean bBehind=false; boolean bSkipNull=false; boolean bAddMissing=false;
		
		if(saFlagIn==null){
			bBehind=true;
		}else{
			String[] saFlagAllowed = {"BEHIND","BEFORE","SKIPNULL","ADDMISSING"};
			if(!(StringArrayZZZ.containsOtherThan(saFlagIn, saFlagAllowed))){
				String sError = "Flags='"+StringArrayZZZ.implode(saFlagIn,", ")+"' - but expected: '" + StringArrayZZZ.implode(saFlagAllowed,", ")+"'";
				ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(StringArrayZZZ.contains(saFlagIn, "ADDMISSING")) {
				bAddMissing=true;
			}else {
				bAddMissing=false;
			}				
			if(bAddMissing) {
				String[] saFlagGoon = StringArrayZZZ.remove(saFlagIn,"ADDMISSING",true);
				objReturn = StringArrayZZZ.appendMissing(saString1,saString2,saFlagGoon);
				break main;
			}
			
			//... und weiter geht´s ohne ADDMISSING
			////Merke: Diese if-Abfragen sind aus Performance-Gründen ausserhalb der Schleife, bzw. appendMissing wird sogar in einer extra Methode erledigt.
			if(StringArrayZZZ.contains(saFlagIn, "BEHIND")) {
				bBehind=true;
			}else if(StringArrayZZZ.contains(saFlagIn, "BEFORE")) {
				bBehind=false;
			}		
			
			if(StringArrayZZZ.contains(saFlagIn, "SKIPNULL")) {
				bSkipNull=true;
			}else {
				bSkipNull=false;
			}										
		}
				
		if(bBehind){
			if(bSkipNull) {
				ArrayList<String>listas = new ArrayList<String>();
				int iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString2[icount]!=null) {												
						listas.add(saString2[icount]);
					}
				}
				
				iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString1[icount]!=null) {
						listas.add(saString1[icount]);
					}
				}
				
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());				
			}else {
				int iSize = saString1.length + saString2.length;
				
				objReturn = new String[iSize];
				System.arraycopy(saString1,0,objReturn, 0, saString1.length);
				System.arraycopy(saString2,0,objReturn, saString1.length, saString2.length);							
			}
			break main;			
		}else{
			if(bSkipNull) {
				ArrayList<String>listas = new ArrayList<String>();
				int iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString1[icount]!=null) {
						listas.add(saString1[icount]);
					}
				}
												
				iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString2[icount]!=null) {
						listas.add(saString2[icount]);
					}
				}
				
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());	
			}else {
				int iSize = saString1.length + saString2.length;
				
				objReturn = new String[iSize];
				System.arraycopy(saString1,0,objReturn, 0, saString1.length);
				System.arraycopy(saString2,0,objReturn, saString1.length, saString2.length);	
				}
				break main;
			}
		
		}//End main:
		return objReturn;
	}
	
	public static String[] appendMissing(String[] saString1, String[] saString2, String[] saFlagIn) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(saString1==null && saString2==null) break main;
		if(saString1==null){
			objReturn = new String[saString2.length];
			System.arraycopy(saString2,0,objReturn, 0, saString2.length);
			break main;
		}else if(saString2 == null){
			objReturn = new String[saString1.length];
			System.arraycopy(saString1,0,objReturn, 0, saString1.length);
			break main;			
		}
		
		boolean bBehind=false; boolean bSkipNull=false; boolean bAddMissing=false;
		
		String sFlag = null;
		if(saFlagIn==null){
			bBehind=true;
		}else{
			String[] saFlagAllowed = {"BEHIND","BEFORE","SKIPNULL"};
			if(!(StringArrayZZZ.containsOtherThan(saFlagIn, saFlagAllowed))){
				String sError = "Flags='"+StringArrayZZZ.implode(saFlagIn,", ")+"' - but expected: '" + StringArrayZZZ.implode(saFlagAllowed,", ")+"'";
				ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(StringArrayZZZ.contains(saFlagIn, "BEHIND")) {
				bBehind=true;
			}else if(StringArrayZZZ.contains(saFlagIn, "BEFORE")) {
				bBehind=false;
			}
			
			if(StringArrayZZZ.contains(saFlagIn, "SKIPNULL")) {
				bSkipNull=true;
			}else {
				bSkipNull=false;
			}											
		}
				
		if(bBehind){//Merke: Diese if-Abfragen sind aus Performance-Gründen ausserhalb der Schleife, bzw. appendMissing wird sogar in einer extra Methode erledigt.
			if(bSkipNull) {
				ArrayList<String>listas = new ArrayList<String>();
				int iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString2[icount]!=null) {												
						if(!listas.contains(saString2[icount])){
							listas.add(saString2[icount]);
						}
					}
				}
				
				iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString1[icount]!=null) {
						if(!listas.contains(saString1[icount])){
							listas.add(saString1[icount]);
						}
					}
				}
				
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());				
			}else {
				ArrayList<String>listas = new ArrayList<String>();
				int iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){																					
					if(!listas.contains(saString2[icount])){
						listas.add(saString2[icount]);						
					}
				}
				
				iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){										
					if(!listas.contains(saString1[icount])){
						listas.add(saString1[icount]);
					}					
				}
				
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());							
			}
			break main;			
		}else{
			if(bSkipNull) {
				ArrayList<String>listas = new ArrayList<String>();
				int iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString1[icount]!=null) {
						if(!listas.contains(saString1[icount])){
							listas.add(saString1[icount]);
						}
					}
				}
												
				iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){					
					if(saString2[icount]!=null) {
						if(!listas.contains(saString2[icount])){
							listas.add(saString2[icount]);
						}
					}
				}
				
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());	
			}else {
				ArrayList<String>listas = new ArrayList<String>();
				
				int iSize = saString1.length;
				for(int icount = 0; icount<=iSize-1;icount++){										
					if(!listas.contains(saString1[icount])){
						listas.add(saString1[icount]);
					}					
				}
				
				iSize = saString2.length;
				for(int icount = 0; icount<=iSize-1;icount++){																					
					if(!listas.contains(saString2[icount])){
						listas.add(saString2[icount]);						
					}
				}
												
				objReturn = new String[listas.size()];
				System.arraycopy(listas.toArray(objReturn),0,objReturn, 0, listas.size());
			}
		}
		}//End main:
		return objReturn;
	}
	
	
	//#####################################
	//### prepend als Komfortfunktion
	//#####################################
	public static String[] prepend(String sString1, String sString2) throws ExceptionZZZ{
		return StringArrayZZZ.append(sString1, sString2, "BEFORE");
	}
	
	public static String[] prepend(String sString1, String sString2, String sFlagControl) throws ExceptionZZZ{
		String[]saFlagControl=new String[2];
		saFlagControl[0]=sFlagControl;
		saFlagControl[1]="BEFORE";
		
		String[]saString1=new String[1];
		saString1[0]=sString1;
					
		String[]saString2=new String[1];
		saString2[0]=sString2;
		return StringArrayZZZ.append(saString1, saString2, saFlagControl);
	}
	
	public static String[] prepend(String sString1, String sString2, String[] saFlagControlIn) throws ExceptionZZZ{
		String[]saFlagControl=StringArrayZZZ.prepend(saFlagControlIn, "BEFORE");
		
		String[]saString1=new String[1];
		saString1[0]=sString1;
					
		String[]saString2=new String[1];
		saString2[0]=sString2;
		return StringArrayZZZ.append(saString1, saString2, saFlagControl);
	}
	
	
	public static String[] prepend(String sString2, String[] saString1) throws ExceptionZZZ{
		return StringArrayZZZ.append(sString2, saString1, "BEFORE");
	}
	
	public static String[] prepend(String[] saString1, String sString2) throws ExceptionZZZ{
		return StringArrayZZZ.append(saString1, sString2, "BEFORE");
	}
		
	public static String[] prepend(String[] saString1, String sString2, String sFlagControlIn) throws ExceptionZZZ{
		String[]saFlagControl=StringArrayZZZ.prepend(sFlagControlIn, "BEFORE");
		
		String[]saString2=new String[1];
		saString2[0]=sString2;
		return StringArrayZZZ.append(saString1, saString2, saFlagControl);
	}
	
	public static String[] prepend(String[] saString1, String sString2, String[] saFlagControlIn) throws ExceptionZZZ{		
		String[]saFlagControl=StringArrayZZZ.prepend(saFlagControlIn, "BEFORE");
		
		String[]saString2=new String[1];
		saString2[0]=sString2;
		return StringArrayZZZ.append(saString1, saString2, saFlagControl);
	}
	
	
	public static String[] prepend(String[] saString1, String[] saString2) throws ExceptionZZZ{
		return StringArrayZZZ.append(saString1, saString2, "BEFORE");
	}
	
	public static String[] prepend(String[] saString1, String[] saString2, String sFlagControlIn) throws ExceptionZZZ{
		String[]saFlagControl=StringArrayZZZ.prepend(sFlagControlIn, "BEFORE");		
		return StringArrayZZZ.append(saString1, saString2, saFlagControl);
	}
	
	
	//#####################################
	public static String asHtml(String[] saValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(StringArrayZZZ.isEmptyNull(saValue)) break main;
			
			sReturn = new String();

			int iIndex=-1;
			for(String sValue : saValue) {				
				if(sValue!=null) {
					iIndex=iIndex+1;
					if(iIndex==0) {
						if(!StringZZZ.startsWithIgnoreCase(sValue, "<html>")){
							if(!StringZZZ.startsWithIgnoreCase(sValue, "<body>")) {
								sReturn = "<body>" + sReturn;
							}
							sReturn = "<html>" + sReturn;
						}
					}
																		
					if(iIndex>=1) {
						sReturn=sReturn+"<br>";
					}
					sValue = StringZZZ.toHtml(sValue);
					sReturn = sReturn + sValue;
				}
			}
								
			if(!StringZZZ.endsWithIgnoreCase(sReturn, "</html>")){
				if(!StringZZZ.endsWithIgnoreCase(sReturn, "</body>")) {
					sReturn = sReturn + "</body>";
				}
				sReturn = sReturn + "</html>";
			}
			
		}//end main:
		return sReturn;	
	}
	
	public boolean contains(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		
		if(this.bIsString == true){
			bReturn = StringArrayZZZ.containsExact(this.getArray(), sToFind);
		} //end if
		
		return bReturn;
	}//end contains
	
	public boolean containsIgnoreCase(String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		
		if(this.bIsString == true){
			bReturn = StringArrayZZZ.containsIgnoreCase(this.getArray(), sToFind);
		} //end if
		
		return bReturn;
	}//end contains
	
	public boolean contains(String sToFind, boolean bExact) throws ExceptionZZZ{
		boolean bReturn = false;
		
		if(this.bIsString == true){
			bReturn = StringArrayZZZ.contains(this.getArray(), sToFind, bExact);
		} //end if
			
		return bReturn;
	}//end contains
	
	/** containsExact als Default verwenden
	 * @param saSource
	 * @param sToFind
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 04.04.2023, 09:11:26
	 */
	public static boolean contains(String[] saSource, String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			bReturn = StringArrayZZZ.containsExact(saSource, sToFind);
					
		}//end main;		
		return bReturn;
	}
	
	public static boolean contains(String[] saSource, String sToFind, boolean bExact) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			if(bExact) {
				bReturn = StringArrayZZZ.containsExact(saSource, sToFind);
			}else {
				bReturn = StringArrayZZZ.containsIgnoreCase(saSource, sToFind);
			}			
		}//end main;		
		return bReturn;
	}
	
	public static boolean containsIgnoreCase(String[] saSource, String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];			 	
			 	if (sTemp.equalsIgnoreCase(sToFind)){	
			 		bReturn = true;
			 		break main;
			 	}
			 } // end for	
		}//end main;		
		return bReturn;
	}
	
	public static boolean containsExact(String[] saSource, String sToFind) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			 for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];
			 	if (sTemp.equals(sToFind)){	
			 		bReturn = true;
			 		break main;
			 	}
			 } // end for	
		}//end main;		
		return bReturn;
	}
		
	public static boolean containsOtherThan(String[] saSource, String[] saToFind) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{			
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(saToFind==null) break main;
									
			 for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];
			 	if (!StringArrayZZZ.contains(saToFind, sTemp)){	
			 		bReturn = false;
			 		break main;
			 	}
			 } // end for	
		}//end main;		
		return bReturn;
	}
	
	public static boolean containsOtherThanExact(String[] saSource, String[] saToFind) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{			
			if(saSource==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			if(saToFind==null) break main;
									
			 for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];
			 	if (!StringArrayZZZ.containsExact(saToFind, sTemp)){	
			 		bReturn = false;
			 		break main;
			 	}
			 } // end for	
		}//end main;		
		return bReturn;
	}
	
	/** long, returns the index of the first sToFind in the string Array
	* Lindhauer; 16.05.2006 07:42:37
	 * @param sToFind
	 * @return long
	 */
	public long getIndexFirst(String sToFind) throws ExceptionZZZ {
		long lFunction = -1;
		
		if(this.bIsString == true){
			 for(int iCounter = 0; iCounter <= this.saIntern.length -1; iCounter++){
		 	String sTemp = saIntern[iCounter];
		 	// Erst ab Java 1.2   if (sTemp.compareToIgnoreCase(sToFind)== 0){
		 	if (sTemp.compareTo(sToFind)== 0){
		 		lFunction = (long)iCounter;
		 		break;
		 	}
		 } // end for				
		} //end if
output:
		return lFunction;
	}//end IndexFirstGet
	
	

	/** long, returns the index of the first sToFind in the string Array
	* Lindhauer; 16.05.2006 07:42:37
	 * @param sToFind
	 * @return long
	 */
	public static long getIndexFirst(String[] saSource, String sToFind) throws ExceptionZZZ {
		long lFunction = -1;
		main:{
			if(saSource==null) break main;
			
			for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++){
			 	String sTemp = saSource[iCounter];
			 	// Erst ab Java 1.2   if (sTemp.compareToIgnoreCase(sToFind)== 0){
			 	if (sTemp.compareTo(sToFind)== 0){
			 		lFunction = (long)iCounter;
			 		break;
			 	}
			 } // end for
		}//end main:
		return lFunction;
	}//end IndexFirstGet
	
	public static int[] getIndexContains(String[] saSource, String sToFind) throws ExceptionZZZ {
		int[] iaReturn=null;
		main:{
			if(saSource==null) break main;
			
			ArrayList<Integer> listaint = new ArrayList<Integer>();
			for(int iCounter = 0; iCounter <= saSource.length -1; iCounter++) {
				String sTemp = saSource[iCounter];
			 	// Erst ab Java 1.2   if (sTemp.compareToIgnoreCase(sToFind)== 0){
			 	if (sTemp.compareTo(sToFind)== 0){
			 		listaint.add(new Integer(iCounter));
			 	}				
			}
			iaReturn = ArrayListUtilZZZ.toIntArray(listaint);
			
		}//end main:				
		return iaReturn;
	}
	//##################################################################
	public static String[] get(String[] saSource, int[]iaIndex) throws ExceptionZZZ {
		String[]saReturn = null;
		main:{
			if(ArrayUtilZZZ.isEmpty(saSource)) break main;
			if(ArrayUtilZZZ.isEmpty(iaIndex)) break main;
			
			ArrayList<String>listas=new ArrayList<String>();
			for(int iCounter = 0; iCounter <= iaIndex.length -1; iCounter++) {
				int iIndex = iaIndex[iCounter];
				if(iIndex <= saSource.length-1 && iIndex >=0) {
					String sTemp = saSource[iIndex];
					listas.add(sTemp);
				}
			}//end for
			saReturn = ArrayListUtilZZZ.toStringArray(listas);						
		}//end main:
		return saReturn;
	}
	
	//##################################################################
	public static String getLast(Object[] saSource) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saSource==null) break main;
			long lIndex = saSource.length - 1;
			if(lIndex<0)break main;
			
			Long lngIndex = new Long(lIndex);
			int iIndex = lngIndex.intValue();
			sReturn = saSource[iIndex].toString();
		}// end main:
		return sReturn;
	}
	public static String getLast(String[] saSource) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saSource==null) break main;
			long lIndex = saSource.length - 1;
			if(lIndex<0)break main;
			
			Long lngIndex = new Long(lIndex);
			int iIndex = lngIndex.intValue();
			sReturn = saSource[iIndex];
		}// end main:
		return sReturn;
	}
	
	public static String getFirst(Object[] saSource) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saSource==null) break main;
			long lIndex = saSource.length - 1;
			if(lIndex<0)break main;

			sReturn = saSource[0].toString();
		}// end main:
		return sReturn;
	}
		
	public static String getFirst(String[] saSource) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saSource==null) break main;
			long lIndex = saSource.length - 1;
			if(lIndex<0)break main;

			sReturn = saSource[0];
		}// end main:
		return sReturn;
	}
	
	public static String implode(String[] saString) throws ExceptionZZZ {
		return StringUtils.join(saString);   //
	}
	
	public static String implode(String[] saString, String sDelimiter) throws ExceptionZZZ {
		return StringUtils.join(saString, sDelimiter);   //
	}	
	
	public void insertSorted(String sString, String sFlagin) throws ExceptionZZZ {
		String[] saNew = StringArrayZZZ.insertSorted(this.getArray(), sString, sFlagin);
		this.setArray(saNew);
	}
	
	public static String[] insertSorted(String[] saSorted, String sString, String sFlagIn) throws ExceptionZZZ {
		String[] objReturn = null;
		main:{
		if(saSorted==null && StringZZZ.isEmpty(sString)) break main;
		if(saSorted==null){
			objReturn = new String[0];
			objReturn[0] = sString;
			break main;
		}
		if(StringZZZ.isEmpty(sString)){
			objReturn = new String[saSorted.length];
			System.arraycopy(saSorted,0,objReturn, 0, saSorted.length);
			break main;
		}
		
		String sFlag = null;
		if(StringZZZ.isEmpty(sFlagIn)){
			sFlag = "";
		}else{
			sFlag = sFlagIn.toUpperCase();
			if(!(sFlag.equals("IGNORECASE"))){
				ExceptionZZZ ez = new ExceptionZZZ("Flag='"+sFlagIn+"', but expected '', 'IGNORECASE', ''", iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		
		boolean bInserted = false;
		int iPositionOriginal = 0;
		objReturn = new String[saSorted.length+1];
		int iSize = objReturn.length;
		
		
		if(sFlag.equals("IGNORECASE")){
//			bis zur vorletzen Position inder Schleife behandeln
			for(int icount = 0; icount<=iSize-2;icount++){	
				String stemp = saSorted[iPositionOriginal];
				if(stemp.compareToIgnoreCase(sString)<=0 && bInserted ==false){
					//Bis zum gefundenen Wert das R�ckgabeArray auff�llen					
					objReturn[icount] = saSorted[iPositionOriginal];
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}else if(stemp.compareToIgnoreCase(sString)>0 && bInserted ==false){
					
					//Neuen Wert einf�gen
					objReturn[icount] = sString;		
					bInserted = true;
					
				}else if(bInserted == true){
					
					// Bis zum Ende die Originalwerte in das R�ckgabeArray f�llen					
					objReturn[icount] = stemp;
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}
			}//end for			
		}else{
			//bis zur vorletzen Position inder Schleife behandeln
			for(int icount = 0; icount<=iSize-2;icount++){	
				String stemp = saSorted[iPositionOriginal];
				if(stemp.compareToIgnoreCase(sString)<=0 && bInserted ==false){
					//Bis zum gefundenen Wert das R�ckgabeArray auff�llen				
					objReturn[icount] = saSorted[iPositionOriginal];
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}else if(stemp.compareTo(sString)>0 && bInserted ==false){
				
					//Neuen Wert einf�gen
					objReturn[icount] = sString;	
					bInserted = true;
					
				}else if(bInserted == true){
					
					// Bis zum Ende die Originalwerte in das R�ckgabeArray f�llen				
					objReturn[icount] = stemp;
					if(iPositionOriginal < saSorted.length-1) iPositionOriginal++;
				}
			}//end for
		}
		
//		immer separat die letzen Position einfuegen
		if(bInserted == false){
			objReturn[iSize-1] = sString;
		}else{
			objReturn[iSize-1] = saSorted[iPositionOriginal];
		}			
		
		}//End main:
		return objReturn;
	}
	
	public static String[] intersect(String[] saString01, String[] saString02) throws ExceptionZZZ{
		String[]saReturn = null;
		main:{
			if(saString01==null)break main;
			if(saString02==null)break main;
			if(saString01.length==0) break main;
			if(saString02.length==0) break main;
						
			ArrayList<String>listasTemp = new ArrayList<String>();
			for(String s01 : saString01){
				if(StringArrayZZZ.contains(saString02, s01)){
					listasTemp.add(s01);
				}				
			}			
			saReturn = listasTemp.toArray(new String[listasTemp.size()]);
		}//End main:
		return saReturn;
	}	
	
	
	public static String[] intersectOrNotNull(String[] saString01, String[] saString02) throws ExceptionZZZ{
		String[]saReturn = null;
		main:{
			if(saString01==null && saString02==null)break main;
			if(saString01==null) {
				if(saString02.length==0) break main;
				
				saReturn = saString02;
				break main;
			}
			
			
			if(saString02==null) {
				if(saString01.length==0) break main;
				
				saReturn = saString01;
				break main;
			}
			
			
			saReturn = StringArrayZZZ.intersect(saString01, saString02);
		}//End main:
		return saReturn;
	}	
	
	/**returns true if the string is empty or null.
	 * FGL: D.h. NULL oder Leerstring 
	 * 
	 * Uses Jakarta commons.lang.
	 * @param sString
	 * @return, 
	 *
	 * @return boolean
	 *
	 * javadoc created by: 0823, 24.07.2006 - 08:52:50
	 */
	public static boolean isEmpty(String[] saString) throws ExceptionZZZ {
		if (StringArrayZZZ.isEmptyNull(saString)) return true;
		for(int iCounter = 0; iCounter <= saString.length-1; iCounter++) {
			String sString = saString[iCounter];
			if(!StringZZZ.isEmpty(sString)) return false;
		}
		return true;
	}
	
	public static boolean isEmptyNull(String[] saString) throws ExceptionZZZ {		
		if(saString==null) return true;
		if(saString.length==0) return true;
		return false;
	}
	
	public static boolean isEmptyTrimmed(String[] saString) throws ExceptionZZZ {		
		if (StringArrayZZZ.isEmptyNull(saString)) return true;
		for(int iCounter = 0; iCounter <= saString.length-1; iCounter++) {
			String sString = saString[iCounter];
			if(!StringZZZ.isEmpty(sString.trim())) return false;
		}
		return true;
	}

	
	/**Alle Elemente des String Arrays werden um einen weiteren String erweitert.	
	 * @param saString
	 * @param sString	 
	 * @return
	 * @throws ExceptionZZZ, 
	 *
	 * @return String[]
	 *
	 * javadoc created by: 0823, 08.12.2006 - 13:13:09
	 */
	public static String[] plusString(String[] saString, String sString) throws ExceptionZZZ{
		return plusStringBehind(saString, sString);
	}
	
	public static String[] plusString(String sString, String[] saString) throws ExceptionZZZ{
		return plusStringBefore(saString, sString);
	}
		
	public static String[] plusStringBefore(String[] saString, String sString) throws ExceptionZZZ{
		return plusString(saString, sString, "BEFORE");
	}
	
	public static String[] plusStringBehind(String[] saString, String sString) throws ExceptionZZZ{
		return plusString(saString, sString, "BEHIND");
	}
	
	/**Alle Elemente des String Arrays werden um einen weiteren String erweitert.
	 * Merke: Bei "BEFORE" gilt, dass ein String um die Elemente eines Arrays erweitert wird.
	 *        
	 * @param saString
	 * @param sString
	 * @param sFlagin, BEFORE oder BEHIND
	 * @return
	 * @throws ExceptionZZZ, 
	 *
	 * @return String[]
	 *
	 * javadoc created by: 0823, 08.12.2006 - 13:13:09
	 */
	public static String[] plusString(String[] saString, String sString, String sFlagin) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
		if(StringZZZ.isEmpty(sString)){
			objReturn = new String[saString.length];
			System.arraycopy(saString,0,objReturn, 0, saString.length);
			break main;
		}
		
		String sFlag = null;
		if(StringZZZ.isEmpty(sFlagin)){
			sFlag = "BEHIND";
		}else{
			sFlag = sFlagin.toUpperCase();
			if(!(sFlag.equals("BEHIND")|sFlag.equals("BEFORE"))){
				ExceptionZZZ ez = new ExceptionZZZ("Flag='"+sFlagin+"', but expected '', 'BEFORE', 'BEHIND'", iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
		
		int iSize = saString.length;
		objReturn = new String[iSize];
		
		if(sFlag.equals("BEHIND")){
			for(int icount = 0; icount<=iSize-1;icount++){
				objReturn[icount]=saString[icount] + sString;
			}
			break main;
		}else{
			for(int icount = 0; icount<=iSize-1;icount++){
				objReturn[icount]=sString + saString[icount];
			}
			break main;
		}
		
		}//End main:
		return objReturn;
	}
	
	public void plusString(String sString, String sFlagin) throws ExceptionZZZ{
		String[] saNew = StringArrayZZZ.plusString(this.getArray(), sString, sFlagin);
		this.setArray(saNew);
	}
	
	
	public static String[] plusStringArray(String[] saString1, String[] saString2, String sFlagin) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			if(sFlagin!=null) {
				String[] saFlag = new String[1];
				saFlag[0]=sFlagin;
				objReturn = StringArrayZZZ.plusStringArray(saString1, saString2, saFlag);
			}
		}//end main:
		return objReturn;
	}	
	public static String[] plusStringArray(String[] saString1, String[] saString2, String[] saFlagin) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			if(saString1==null && saString2==null) break main;
			
			if(saString1==null){
				objReturn = new String[saString2.length];
				System.arraycopy(saString2,0,objReturn, 0, saString2.length);
				break main;
			}else if(saString2 == null){
				objReturn = new String[saString1.length];
				System.arraycopy(saString1,0,objReturn, 0, saString1.length);
				break main;			
			}
			
			boolean bBehind=false;
			
			String sFlag = null;
			if(saFlagin==null){
				bBehind=true;
			}else{
				if(StringArrayZZZ.contains(saFlagin, "BEHIND")) {
					bBehind=true;
				}else if(StringArrayZZZ.contains(saFlagin, "BEFORE")) {
					bBehind=false;
				}
							
				String[] saFlagAllowed = {"BEHIND","BEFORE"};
				if(!(StringArrayZZZ.containsOtherThan(saFlagin, saFlagAllowed))){
					String sError = "Flags='"+StringArrayZZZ.implode(saFlagin,", ")+"' - but expected: '" + StringArrayZZZ.implode(saFlagAllowed,", ")+"'";
					ExceptionZZZ ez = new ExceptionZZZ(sError, iERROR_PARAMETER_VALUE, StringArrayZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			}
					
			int iSize = MathZZZ.max(saString1.length, saString2.length);
			objReturn = new String[iSize];
			
			if(bBehind){//Merke: Diese if-Abfragen sind aus performance-gründen ausserhalb der Schleife			
				for(int icount = 0; icount<=iSize-1;icount++){
					if(saString1.length-1>=icount && saString2.length-1>= icount){
						objReturn[icount]=saString1[icount] + saString2[icount];
					}else if(saString1.length-1<icount && saString2.length-1 >= icount){
						objReturn[icount] = saString2[icount];
					}else if(saString1.length-1 >= icount && saString2.length-1 < icount){
						objReturn[icount] = saString1[icount];
					}
				}
				break main;				
			}else{
				for(int icount = 0; icount<=iSize-1;icount++){
					if(saString1.length-1>=icount && saString2.length-1>= icount){							
						objReturn[icount]= saString2[icount] + saString1[icount] ;                    //Hier dann die Reihenfolge anders
					}else if(saString1.length-1<icount && saString2.length-1 >= icount){
						objReturn[icount] = saString2[icount];
					}else if(saString1.length-1 >= icount && saString2.length-1 < icount){
						objReturn[icount] = saString1[icount];
					}
				}			
				break main;	
			}		
		}//End main:
		return objReturn;
	}
	public void plusStringArray(String[] saString, String sFlagin) throws ExceptionZZZ{
		String[] saNew = StringArrayZZZ.plusStringArray(this.getArray(), saString, sFlagin);
		this.setArray(saNew);
	}
		
	public static String[] remove(String[] saString, String sStringToRemove, boolean bIgnoreCase) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			if(saString==null) break main;
			if(StringZZZ.isEmpty(sStringToRemove)) break main;
					
			if(bIgnoreCase){
				ArrayList<String>listas = StringArrayZZZ.toArrayList(saString);
				ArrayListUtilZZZ.remove(listas, sStringToRemove, true);
				objReturn = ArrayListUtilZZZ.toStringArray(listas);
			}else{
				objReturn = (String[]) ArrayUtils.removeElement(saString, sStringToRemove);
			}		
		}//End main:
		return objReturn;
	}
	
	public static String[] remove(String[] saString, String[] saStringToRemove, boolean bIgnoreCase) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
			if(saString==null) break main;
			objReturn = saString;			
			if(saStringToRemove==null) break main;
			
			for(String sStringToRemove : saStringToRemove) {
				objReturn = StringArrayZZZ.remove(objReturn, sStringToRemove, bIgnoreCase);
			}		
		}//End main:
		return objReturn;
	}
	
	public static long searchIndexFirst(String[] saSource, String sToFind) throws ExceptionZZZ {
		return StringArrayZZZ.getIndexFirst(saSource, sToFind);
	}
	
	public String[] sort() throws ExceptionZZZ{
		String[] sa = this.saIntern;
		Arrays.sort(sa);
		return sa;
	}
	public static String[] sort(Object[] saSource) throws ExceptionZZZ{
		StringArrayZZZ saObj = new StringArrayZZZ(saSource);
		return saObj.sort();					
	}
	
	public static Map<String, String[]> splitByKeysToMap(String[] values, String[] keys) throws ExceptionZZZ {

        Map<String, String[]> result = new LinkedHashMap<String, String[]>();
        Set<String> keySet = new HashSet<String>(Arrays.asList(keys));

        List<String> currentList = new ArrayList<String>();
        String currentKey = "pre";

        for (String value : values) {

            if (keySet.contains(value)) {
                // aktuelles Segment speichern
                result.put(currentKey,
                        currentList.toArray(new String[currentList.size()]));

                // neues Segment beginnen
                currentKey = value;
                currentList = new ArrayList<String>();
            } else {
                currentList.add(value);
            }
        }

        // letztes Segment speichern
        result.put(currentKey,
                currentList.toArray(new String[currentList.size()]));

        return result;
    }

	
	/**Trimme hier nicht leere Array-Elemente (einzeln wie mit String.trim() )
	 * @param saString
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.11.2025, 19:11:51
	 */
	public static String[] trim(String[] saString) throws ExceptionZZZ{
		String[] saReturn = null;
		main:{
			if(ArrayUtilZZZ.isEmpty(saString)) break main;
			
			saReturn = new String[saString.length];
			int iIndex = -1;
			for(String sString : saString) {
				iIndex++;
				if(sString!=null) {
					saReturn[iIndex] = sString.trim();
				}
			}
		}//end main:
		return saReturn;
	}
		
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++
	public Vector toVector() throws ExceptionZZZ {
		Vector objReturn = new Vector();
		main:{
			String[] saString = this.getArray();
			objReturn = StringArrayZZZ.toVector(saString);
		}
		return objReturn;
	}
	
	public static Vector toVector(String[] saString) throws ExceptionZZZ {
		Vector objReturn = new Vector();
		main:{
			if(saString==null) break main;
			if(saString.length==0) break main;
			
			for(int icount = 0; icount <= saString.length-1; icount++){
				objReturn.add(saString[icount]);
			}
			
		}
		return objReturn;
	}
	
	//++++++++++++++++++++++++++++++++++++++++
	public ArrayList<String> toArrayList() throws ExceptionZZZ {
		String[] saString = this.getArray();
		ArrayList<String> listaString = StringArrayZZZ.toArrayList(saString);
		return listaString;
	}
	

	public static ArrayList<String> toArrayList(String[] saString) throws ExceptionZZZ {
		ArrayList<String> listaString = new ArrayList<String>();
		main:{
			if(saString==null) break main;
			if(saString.length==0) break main;
			
			for(int icount=0; icount <= saString.length-1; icount++){
				String stemp = saString[icount];
				listaString.add(stemp);
			}
			
		}
		return listaString;
	}
	
	/** Merke: Integer.parseInt(...) wirft beispielsweise eine java.lang.NumberFormatException, wenn man einen float-String, (z.B. "2.0") übergibt.
	 *              Das wird hier vermieden.
	 * @param sValue
	 */
	public static int[] toInteger(String[][] samValue) throws ExceptionZZZ {
		int iaReturn[];
		main:{
			if(samValue==null) {
				iaReturn=null;
				break main;
			}
			iaReturn = new int[samValue.length];
			
			int iIndexMax = samValue.length-1;
			for(int iIndex = 0; iIndex <= iIndexMax; iIndex++) {
				String[] saValue = samValue[iIndex];
				int iValue = StringArrayZZZ.toInteger(saValue);
				iaReturn[iIndex]=iValue;
			}			
		}//end main:
		return iaReturn;
	}
	
	public static int toInteger(String[] saValue) throws ExceptionZZZ {
		int iReturn=0;
		main:{
			if(saValue==null) break main;
						
			int iIndexMax = saValue.length-1;
			for(int iIndex = 0; iIndex <= iIndexMax; iIndex++) {
				String sValue = saValue[iIndex];
				int iValue = StringZZZ.toInteger(sValue);
				iReturn=iReturn + iValue;
			}			
		}//end main:
		return iReturn;
	}
	
	
	/**Einfache Variante, als Alternative zu Bibliotheken wie Gson oder Jackson
	 * aus: https://stackoverflow.com/questions/35250698/how-do-i-convert-string-array-into-json-array/35250809
	 * 
	 * @return
	 * @author Fritz Lindhauer, 22.03.2021, 09:05:06
	 * @throws ExceptionZZZ 
	 */
	public static String toJsonByStringBuilder(String[] saValue) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(saValue==null){
				ExceptionZZZ ez = new ExceptionZZZ("No array available.", iERROR_PARAMETER_MISSING, StringArrayZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
		int val = saValue.length-1;
		
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0; i <= val; i++) {
			if(i==0) {
				sb.append("\"");
			}
			
			String a=saValue[i].replaceAll("[^A-Za-z0-9]"," ");
		    sb.append(a);
		    if(i < val) {
		        sb.append("\",\"");
		    }
		    
		    if(i==val) {
		    	sb.append("\"");
		    }
		}
		sb.append("]").toString();
				
		sReturn = sb.toString();		
		}//end main:
		return sReturn;
	}
	
	public static String toJson(String[] saValue) throws ExceptionZZZ{
		String sReturn=null;
		main:{							
		    sReturn = JsonUtilZZZ.toJson(saValue);
		}//end main:
		return sReturn;
	}
	
	public static String[] unique(String[] saString1) throws ExceptionZZZ{
		String[] objReturn = null;
		main:{
				if(saString1==null) break main;
			
				objReturn = ArrayUniqueUtilZZZ.toUniqueArrayString(saString1);
		}//end main:
		return objReturn;
	}	
	
	public String[] unique() throws ExceptionZZZ{
		String[] sa = this.saIntern;
		this.saIntern = StringArrayZZZ.unique(sa);
		return this.saIntern;
	}
	
	
	
	//#### GETTER / SETTER
	public String[] getArray() throws ExceptionZZZ {
		return this.saIntern;
	}
	public void setArray(String[] saString) throws ExceptionZZZ {
		this.saIntern = saString;
	}
}
