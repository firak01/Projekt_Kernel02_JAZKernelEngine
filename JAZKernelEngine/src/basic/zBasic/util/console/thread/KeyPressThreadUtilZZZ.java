package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public class KeyPressThreadUtilZZZ {
	
		
		public static void printTableAscii() throws ExceptionZZZ {
			int iLine=1;
			for(int i = 0; i<=255; i++) {					
				char c = (char)i;
				System.out.print(StringZZZ.padLeft(Integer.toString(i),3));
				
				if(i==9) {
				//if(CharZZZ.toString(c)=="\\t") {
					System.out.print(": T | ");
				//}else if(CharZZZ.toString(c)=="\\n") {
				}else if(i==10) {
					System.out.print(": L | ");
				//}else if(CharZZZ.toString(c)=="\\r") {
				}else if(i==13) {
					System.out.print(": R | ");
				}else {
					System.out.print(": " + c + " | ");
				}
				//if (((i+1)%80)==0) System.out.println();	 // neue Zeile
				//System.out.print("X"+((((9*i)+9)%20)==0));
				//Wichtige Erkenntnis... modulo wirkt hier auf die Anzahl der Zeichen.
				//                       Das ist egal wie lang so etwas ist, eine Tatsache, die in der Kryptografie ( und meinen Crypt Klassen) ausgenutzt wird!!!
				//if ((((9*i)+9)%20)==0) System.out.println(); // neue Zeile (normalerweise 80 Zeichen), Pro Zeichendarstellung werden 9 Zeichen verbraucht
				
				//Also: Althergebracht...
				if (((9*iLine)+9)>80) {
					System.out.println(); // neue Zeile (normalerweise 80 Zeichen), Pro Zeichendarstellung werden 9 Zeichen verbraucht
					iLine=0;
				}else {
					iLine++;
				}

			}			
			System.out.println(StringZZZ.crlf());
							
			
		}
}
