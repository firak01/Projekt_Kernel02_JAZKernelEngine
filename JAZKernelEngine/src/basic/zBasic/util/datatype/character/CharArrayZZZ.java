package basic.zBasic.util.datatype.character;

import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.calling.ReferenceArrayZZZ;
import basic.zBasic.util.datatype.calling.ReferenceZZZ;

public class CharArrayZZZ {
	public static boolean contains(char[]ca, char cToFind) {
		boolean bReturn = false;
		main:{
			if(ca==null)break main;
			if(CharZZZ.isEmptyNull(cToFind)) {
				for(char c : ca) {
					if(CharZZZ.isEmptyNull(c)) {
						bReturn = true;
						break main;
					}
				}
			}else {				
				for(char c : ca) {
					if(c == cToFind) {
						bReturn = true;
						break main;
					}
				}
			}								
		}//end main:
		return bReturn;
	}
	
	public static void convert(int[] array, char[] array1) {
		main:{
			if(ArrayUtilZZZ.isNull(array))break main;
			//Merke: "call by reference" Problem: Wenn array1 neu dimensioniert wird, geht die Referenz verloren. D.h. in der aufrufenden Methode ist array1 leer.
			//       Lösungsansatz ware eine neue Methode mit Übergabeparameter: ReferenceArrayZZZ<Integer>objReturn=new ReferenceArrayZZZ<Integer>(0);
			
			int length = array.length;					
		    for (int i = 0; i < length; i++) {
		        // this converts a integer into a character
		        array1[i] = (char) array[i];
		    }
		}//end main:
	}
	
	public static void convert(char[] array, int[] array1 ) {
		main:{
			if(ArrayUtilZZZ.isNull(array))break main;
			
			//Merke: "call by reference" Problem: Wenn array1 neu dimensioniert wird, geht die Referenz verloren. D.h. in der aufrufenden Methode ist array1 leer.
			//       Lösungsansatz ware eine neue Methode mit Übergabeparameter: ReferenceArrayZZZ<Integer>objReturn=new ReferenceArrayZZZ<Integer>(0);
			int length = array.length;												
		    for (int a = 0; a < length; a++) {
		        array1[a] = array[a];
		    }
		}//end main
	}
	
	public static char[] from(int[]ia) {
		char[]caReturn=null;
		main:{
			if(ia==null)break main;
			caReturn = new char[ia.length];
			for(int i=0;i<ia.length;i++) {
				byte b = (byte) ia[i];
				//char c = (char) ia[i];
				//char ctest = (char)b;
				
				//https://stackoverflow.com/questions/7401550/how-to-convert-int-to-unsigned-byte-and-back
				//A byte is always signed in Java. You may get its unsigned value by binary-anding it with 0xFF, though:
				int itest = b & 0xFF;  
				char ctest2 = (char) itest;
				caReturn[i] = ctest2;
			}
		}
		return caReturn;
	}
	
	public static boolean isEmpty(char[]ca) {
		boolean bReturn = true;
		main:{
			if(ca==null)break main;
			for(char c : ca) {
				if(!CharZZZ.isEmptyNull(c)) {
					bReturn = false;
					break main;
				}
			}
		}//end main:
		return bReturn;		
	}
	
	public static int[] toIntArray(char[] ca) {
		int[]iaReturn = null;
		main:{
			iaReturn = new int[ca.length];
			CharArrayZZZ.convert(ca,iaReturn);			
		}//end main:
		return iaReturn;
	}
	
	
	public static String toString(int[]ia) {
		String sReturn = null;
		main:{
			if(ia==null) break main;
			
			char[]ca = CharArrayZZZ.from(ia);
			sReturn = CharArrayZZZ.toString(ca);
		}
		return sReturn;
	}
	
	public static String toString(char[]ca) {
		String sReturn = null;
		main:{
			if(CharArrayZZZ.isEmpty(ca)) break main;
			
			sReturn = CharArrayZZZ.toString(ca, ca.length-1);//die letzte Indexposition ist -1 von der Länge des Strings
		}
		return sReturn;		
	}
	
	
	/**
	 * @param ca
	 * @param iPositionLastIn wenn > Anzahl Zeichen, < Anzahl Zeichen, Abbruch.
	 * @return
	 * @author Fritz Lindhauer, 05.11.2022, 11:01:48
	 */
	public static String toString(char[]ca, int iPositionLastIn) {
		String sReturn = null;
		main:{
			if(CharArrayZZZ.isEmpty(ca))break main;
			
			int iPositionLast;
			if(iPositionLastIn>ca.length-1) {
				break main;
			}else if(iPositionLastIn<0){
				break main;
			}else {
				iPositionLast=iPositionLastIn;
			}
			
			sReturn = "";
			for(int iIndex=0;iIndex <= iPositionLast; iIndex++) {
				char c = ca[iIndex];
				sReturn = sReturn + c; 
			}			
		}//end main:
		return sReturn;
	}
}
