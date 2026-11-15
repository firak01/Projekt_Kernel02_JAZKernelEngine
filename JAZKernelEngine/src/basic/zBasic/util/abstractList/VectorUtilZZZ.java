package basic.zBasic.util.abstractList;

import java.util.Iterator;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.ObjectUtilZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class VectorUtilZZZ extends AbstractObjectWithExceptionZZZ {
	public static Vector unique(Vector vec) throws ExceptionZZZ{
		Vector objReturn = new Vector();
		main:{
			if(vec==null){
				ExceptionZZZ ez = new ExceptionZZZ("Vector is null", iERROR_PARAMETER_MISSING, "VectorZZZ", ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
			
			Iterator it = vec.iterator();
			while(it.hasNext()){				
				Object obj = it.next();
				if(!objReturn.contains(obj)){
					objReturn.add(obj);
				}				
			}
		}//END main
		return objReturn;
	}
	
	
	public static Vector append(Vector vecFirst, Vector vecBehind) throws ExceptionZZZ{
		Vector objReturn = null;
		main:{
			if(vecFirst==null && vecBehind == null) break main;
			if(vecFirst == null){
				objReturn = (Vector)vecBehind.clone();
				break main;
			}
			if(vecBehind==null){
				objReturn = (Vector) vecFirst.clone();
				break main;
			}
			
			//+++++++++++++++++++++++++++++++++++++++++
			objReturn = (Vector) vecFirst.clone();
			for(int icount = 0; icount <= vecBehind.size()-1; icount++){
				objReturn.addElement(vecBehind.elementAt(icount));
			}
			
		}//End main:
	return objReturn;
	}
	
	public static Vector reverse(Vector vecIn) throws ExceptionZZZ{
		Vector objReturn = null;
		main:{
			if(vecIn==null){
				break main;
			}
			
			objReturn = new Vector();
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				objReturn.addElement(vecIn.elementAt(icount));
			}
			
		}
		return objReturn;
	}
	
	public static boolean containsString(Vector vecIn, String sToFind) throws ExceptionZZZ{
		return 	VectorUtilZZZ.containsString(vecIn, sToFind, true);
	}
	
	public static boolean containsString(Vector vecIn, String sToFind, boolean bExactMatch) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			ExtendedVectorZZZ vecExtended = new ExtendedVectorZZZ(vecIn);
			bReturn = vecExtended.containsString(sToFind,!bExactMatch); //!!!Achtung VectorZZZ verwendet ignoreCase und nicht wie alle StringZZZ-Methoden bExcactMatch
		}//end main:
		return bReturn;		
	}
	
	/**Merke: Ensure Methoden werfen sofort die ExceptionZZZ
	 * @param vecIn
	 * @param iSize
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 25.09.2024, 09:01:00
	 */
	public static boolean ensureSize(Vector vecIn, int iSize) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(vecIn==null) break main;
			if(iSize<=0) break main;
			
			if(vecIn.size()!=iSize) {
				ExceptionZZZ ez = new ExceptionZZZ("Vector has invalid size: '" + vecIn.size() +"'", iERROR_PARAMETER_VALUE, VectorUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
				
			bReturn = true;
		}//end main:
		return bReturn;
	}
	
	public static Vector rightOfString(Vector vecIn, String sToFind, boolean bIgnoreCase) throws ExceptionZZZ{
		Vector vecReturn = new Vector();
		main:{
			if(vecIn==null){
				break main;
			}
			if(StringZZZ.isEmpty(sToFind)){
				break main;
			}
			
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				String sString =(String)vecIn.elementAt(icount);
				if(StringZZZ.isEmpty(sString)){
					vecReturn.add("");					
				}else{
					String sResult = StringZZZ.right(sString, sToFind, bIgnoreCase);
					vecReturn.addElement(sResult);
				}				
			}
			
		}//End main
		return vecReturn;
	}
	
	public static String implode(Vector vecIn) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			sReturn = VectorUtilZZZ.implode(vecIn, "");
		}//end main:
		return sReturn;
		
	}
	
	public static String implode(Vector vecIn, String sDelimiter) throws ExceptionZZZ {
		String sReturn = null;
		main:{
			if(vecIn==null) break main;
			if(vecIn.size()==0 || vecIn.isEmpty()){
				sReturn = new String("");
				break main;
			}
			
			if(vecIn.size()>=2){
				Iterator itValue = vecIn.iterator();
				while(itValue.hasNext()){
					Object obj = itValue.next();
					if(obj!=null){
						if(!StringZZZ.isEmpty(sReturn)){
							sReturn = sReturn + sDelimiter + obj.toString();
						}else{
							sReturn = obj.toString();
						}
					}
				}
			}else{
				Object obj = vecIn.firstElement();
				if(obj!=null){
					sReturn = obj.toString();
				}							
			}			
		}//end  main:
		return sReturn;			
	}
	
	public static <T> boolean isEmpty(Vector<T> vecIn) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			if(vecIn == null) break main;
			if(vecIn.isEmpty()){
				bReturn = true;
				break main;
			}
			
			boolean btemp;
			for(int icount = vecIn.size()-1; icount >= 0; icount--){
				Object obj =(Object)vecIn.elementAt(icount);
				
				//FGL 20241227 -Das muss noch getestet werden
				if(obj==null) {
					//mache nix...
				}else if(obj instanceof NullObjectZZZ) {
					//mache nix...
				}else if(obj instanceof String) {
				    btemp = StringZZZ.isEmpty(obj.toString());
				    if(!btemp) break main;
				}else if(obj.getClass().isArray()) {
					btemp = ArrayUtilZZZ.isNullOrEmpty((T[]) obj);
					if(!btemp) break main;
					
					btemp = ArrayUtilZZZ.isEmpty((T[]) obj);
					if(!btemp) break main;
					

					//FGL 20241227 -Das muss ggfs. fuer andere Typen mit ...else if ...erweitert werden
					if(obj==null || obj instanceof String) {
						btemp = StringZZZ.isEmpty(obj.toString());
					    if(!btemp) {
					    	bReturn = false;
					    	break main;	
					    }
					} else if(obj instanceof NullObjectZZZ) {
						//mache nix
					} else {
						ExceptionZZZ ez = new ExceptionZZZ("Object with this type not handled yet: '" + obj.getClass().getName() +"'", iERROR_PARAMETER_VALUE, VectorUtilZZZ.class, ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}					
				}
			}//end for
			
			bReturn = true;
		}//end main:
		return bReturn;		
	}
	
	
	/** Hintergrund ist die TypeCastException wenn man mit dem NullObjectZZZ arbeitet
	 *  Z.B.
	 *  
	 *  Vector<String> vecValue;
	 *  vecValue = objExpressionSolver.parseFirstVector(sExpression);
			assertFalse(StringZZZ.isEmpty((vecValue.get(0).toString()))); //in der 0ten Position ist der Tag vor dem gesuchten String ODER wenn nicht geparst wurde ODER wenn der Tag nicht enthalten ist.
			
			//Aber wenn im Index 1 ein NullObjectZZZ ist, funktioniert dessen .toString() - Methode erst "zu spät".
			try {
			String obj1 = vecValue.get(1);
			}catch (ClassCastException e) {
				e.printStackTrace();
			}
			
			//Das gibt eine TypeCastException, 
			String obj1 = vecValue.get(1);
			String s1 = obj1.toString();
			assertTrue(StringZZZ.isEmpty(s1)); //in der 1ten Position ist der Tag
			String s2 = vecValue.get(2).toString();
			assertTrue(StringZZZ.isEmpty(s2)); //in der 2ten Position ist der Tag nach dem gesuchten String		

	 * @param vecIn
	 * @param iIndex
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.07.2025, 07:48:55
	 */
	public static <T> String getElementAsString(Vector<T> vecIn,int iIndex) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(vecIn == null) break main;
			
			if(vecIn.size() >= iIndex + 1) {
				Object obj = vecIn.get(iIndex);
				sReturn = StringZZZ.toString(obj);	
			}
		}//end main:
		return sReturn;
	}
	
	
	/** Der Unterschied zu getElementToString() ist , das hier ggfs. valueOf() aufgerufen wird.
	 *  Und das ist ggfs. NULL		
	 * @param vecIn
	 * @param iIndex
	 * @return
	 * @throws ExceptionZZZ
	 * @author Fritz Lindhauer, 27.07.2025, 07:48:55
	 */
	public static <T> String getElementAsValueOf(Vector<T> vecIn,int iIndex) throws ExceptionZZZ{
		String sReturn = null;
		main:{
			if(vecIn == null) break main;
			
			if(vecIn.size() >= iIndex + 1) {
				Object obj = vecIn.get(iIndex);
				sReturn = StringZZZ.valueOf(obj);
			}
		}//end main:
		return sReturn;
	}
}//end class
