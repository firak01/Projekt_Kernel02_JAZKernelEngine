package basic.zBasic.util.abstractList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import basic.javareflection.mopex.Mopex;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**  Diese HashMap verwendet als Keys Werte von Integer. 
 *    Als Objekte werden Werte vom Typ ArrayList verwendet, die Ihrerseits nur Integer-Elemente besitzen.
 *    
 *    Ziel: Ist z.B. die Aussage "An der Stelle 2 gibt es Elemente, die Ebenfalls an den Stellen 2, 12, 14, 20 auftreten."
 *            Entferne nun alle diese Doppelten und lasse den letzten Wert bestehen.
 *            
 *     Merke: Es soll keinen Konstruktor mit new(HashMap) geben und auch keine importIt(HashMap).
 *                Zuordnung nur über TypeCast    HashMapIndexedZZZ hmIndexed = (HashMapIndexedZZZ) hmWhichShouldBeIndexed.
 * @author lindhaueradmin
 *
 */
public class HashMapIndexedZZZ<T,X> extends HashMap implements  IConstantZZZ{

	/** Prüfe, ob die übergebene HashMap die vorausgesetzte Struktur besitzt.
	 *   a) Alle Keys sind Integer Werte
	 *   b) Alle Elemente sind Arraylisten, die ebenfalls nur Integer Werte enthalten.
	 *   
	 * @param hmIndexed
	 * @param bQuiet              Wenn true, dann wird bei ung�ltiger Struktur keine Exception geworfen.
	 * @return
	 * 
	 * lindhaueradmin; 17.05.2011 06:57:05
	 * @throws ExceptionZZZ 
	 */
	public static boolean checkStructure(HashMap hmIndexed, boolean bQuiet) throws ExceptionZZZ{
		boolean bReturn = true;
		main:{
			if(hmIndexed==null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap", iERROR_PARAMETER_MISSING, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}			 
			if(hmIndexed.size()==0) break main;

			//#####################################

			//1. Pruefe auf Integer Objekt des Keys
			Set keySet = hmIndexed.keySet();
			Iterator itKey = keySet.iterator();
			while(itKey.hasNext()){
				Object obj = itKey.next();
				if(obj.getClass()!= Integer.class){
					if(bQuiet){
						bReturn = false;
						break main;
					}else{
						ExceptionZZZ ez = new ExceptionZZZ("HashMap contains key which is no Integer-Object. (" + obj.getClass().getName() + ")", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}
			}//end while


			//2. Pruefe auf ArrayList des Entries
			Set entrySet = hmIndexed.entrySet();
			Iterator itSet = entrySet.iterator();
			while(itSet.hasNext()){
				Object obj2 = itSet.next();
				if(obj2==null){
					if(bQuiet){
						bReturn = false;
						break main;
					}else{
						ExceptionZZZ ez = new ExceptionZZZ("HashMap contains entry which is null.", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				}else{
					//if(obj2.getClass()!=ArrayList.class){  //NEIN, das ist immer vom Typ java.util.Map.Entry
					Map.Entry entry = (Map.Entry) obj2;
					Object obj2b = entry.getValue();
					if(obj2b.getClass()!= ArrayList.class){
						if(bQuiet){
							bReturn = false;
							break main;
						}else{
							ExceptionZZZ ez = new ExceptionZZZ("HashMap contains entry which is no ArrayList-Object. (" + obj2b.getClass().getName() + ")", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
							throw ez;
						}
					}//end if

					//3. Pruefe auf Integer Objekt der Eintraege der ArrayList
					ArrayList alSet = (ArrayList) obj2b;
					for(int icount=0; icount< alSet.size(); icount++){
						Object obj3 = alSet.get(icount);
						if(obj3==null){
							if(bQuiet){
								bReturn = false;
								break main;
							}else{
								ExceptionZZZ ez = new ExceptionZZZ("HashMap contains ArrayListEntry which contains a null element.", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
								throw ez;
							}
						}else{
							if(obj3.getClass()!= Integer.class){
								if(bQuiet){
									bReturn = false;
									break main;
								}else{
									ExceptionZZZ ez = new ExceptionZZZ("HashMap contains ArrayListEntry which contains an element which is no Integer-Object. (" + obj3.getClass().getName() + ")", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
									throw ez;
								}
							}
						}//end for icount

					}//obj2 ==null
				}   //end if obj2.getClass()!=ArrayList.class){
			}//end while
		}//end main
		return bReturn;
	}
	
	public boolean checkStructure(boolean bQuiet) throws ExceptionZZZ{
		boolean bReturn = false;
		main:{
			bReturn = HashMapIndexedZZZ.checkStructure(this, bQuiet);
		}//end main
		return bReturn;
	}
	
	/** Vergleiche die Werte der in der HashMap gespeicherten Objekte. 
	 *   Aber: Gib eine Methode an, �ber die die Werte der Objekte erst einmal ermittelt werden sollen.
	 *   Die daraus reslutierenden Werte werden mit .equals() miteinander verglichen.
	* @param hm
	* @param sMethod
	* @return
	* 
	* lindhaueradmin; 18.05.2011 06:02:54
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIndexedZZZ getValueDupsIndexedByMethod(ArrayList objAl, String sMethodName) throws ExceptionZZZ{
		HashMapIndexedZZZ hmIndexed = new HashMapIndexedZZZ();
		main:{
			if(objAl== null){
				ExceptionZZZ ez = new ExceptionZZZ("ArrayList to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			//###############################
			String sMethod = null; //Fuer das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;     //Fuer das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			try {
					
			//2. Vergleich der gleichen ArrayList, elemtweise.
			ArrayList objAlExt = objAl;
			ArrayList objAlInt = objAl;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			
			//#################################
			//Ersten Wert holen: Aeussere Schleife
			//Merke: Anders als bei der HashMap-Index-Ermittlung, hier mit For-Schleifen arbeiten.
			int iposMaxExt = objAlExt.size();
			for(int icountExt=0; icountExt < iposMaxExt; icountExt++){
				System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
				Object objExt = objAlExt.get(icountExt); //Wer sagt denn, das ein Key immer String sein muss.....
				
				//+++ Suche nun nach der Methode im Objekt.
				//Merke: In der Reflection API Paramter f�r die Methode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objExt; //F�r das Exception Handling
				Method mExt = Mopex.getSupportedMethod(objExt.getClass(), sMethodName, null);
				
				if(mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in the value of the key object: " + objExt.toString() , iERROR_RUNTIME, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			

				//+++ Rufe die Methode auf dem Objekt auf. Das Ergebnis ist wiederrum ein Objekt.
				Object objErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objExt; //Fuer das Exception Handling
				objErgExt = mExt.invoke(objExt, null);
				if(objErgExt!=null){
					System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objErgExt.toString());
				}else{
					System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					
					//TODO: Hier muss eigentlich zum n�chsten Element der �u�eren Schleife gegangen werden.
					break main;
				}
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objErgExt.getClass(), "equals", classaParam);  //Merke: Der Vergleich wird dann in der inneren Schleife durchgef�hrt.
				
//				#############################################
				//### Zweiten Wert holen: Innere Schleife	
				ArrayList alInt = new ArrayList(); //zum Speichern der in der inneren Schleife gefundenen Indexwerte
				int iposMaxInt = objAlInt.size();
				for(int icountInt=0; icountInt < iposMaxInt; icountInt++){
					System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
					Object objInt = objAlInt.get(icountInt); //Wer sagt denn, das ein Key immer String sein muss.....
					
//					+++ Suche nun nach der Methode im Objekt.
					//Merke: In der Reflection API Paramter f�r die Methode anzugeben geht so:
					//Class[] classaParam = new Class[1];
					//classaParam[0] = objKeyValueToStore.getClass();
					//
					//aber hier ist classaParm=null;  
					sMethod = sMethodName; //F�r das Exception Handling
					obj = objInt; //F�r das Exception Handling
					Method mInt = Mopex.getSupportedMethod(objInt.getClass(), sMethodName, null);
					
					if(mInt==null){
						ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethod + " in the value object: " + obj.toString() , iERROR_RUNTIME, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				

					//+++ Rufe die Methode auf dem Objekt auf. Das Ergebnis ist wiederrum ein Objekt.
					Object objErgInt = null;
					
					//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
					//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
					
					//Object objReturnType = m.invoke(objWithKey, null);
					//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
					//wir habe aber keinen Parameter, also NULL
					obj = objInt; //F�r das Exception Handling
					objErgInt = mInt.invoke(objInt, null);
					if(objErgInt!=null){
						System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objErgInt.toString());
					}else{
						System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH zum n�chsten Wert");
						
						//TODO: Hier muss eigentlich zum n�chsten Element der inneren Schleife gegangen werden.
						break main;
					}
					
			        //##########################################################	
					//+++ F�re die "equals" Methode aus.
					Object objErgEquals = mEqualsExt.invoke(objErgExt, new Object[] {objErgInt});
					if(objErgEquals!=null){
						System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
						if(objErgEquals.toString().equals("false")){
							//Mache nix;
						}else{
							//Erweitere die Index-Arraylist um die aktuelle Indexposition
							alInt.add(new Integer(icountInt));
						}
					}else{
						//System.out.println("Methode " + sMethod + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
						 //Mache nix;
					}
					
				} //End while "innere Schleife"
				
				//############################################################ 
				//+++ Pr�fe die Anzahl der Index-Arraylist-Elemente und f�ge bei mehr als 2 Elementen den Wert hinzu. Merke: 1 Element sollte immer darin sein, wenn der gleich wert auf sich selbst gepr�ft wurde.
				if(alInt.size()>=2){
					hmIndexed.put(new Integer(icountExt), alInt);
				}
	
			}//end for
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException " + sMethod + " in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} 
		}//end main
		return hmIndexed;
	}
	
	/** Vergleiche die Werte der in der HashMap gespeicherten Objekte. 
	 *   Aber: Gib eine Methode an, �ber die die Werte der Objekte erst einmal ermittelt werden sollen.
	 *   Die daraus reslutierenden Werte werden mit .equals() miteinander verglichen.
	* @param hm
	* @param sMethod
	* @return
	* 
	* lindhaueradmin; 18.05.2011 06:02:54
	 * @throws ExceptionZZZ 
	 */
	public static HashMapIndexedZZZ getValueDupsIndexedByMethod(HashMap objHashMap, String sMethodName) throws ExceptionZZZ{
		HashMapIndexedZZZ hmIndexed = new HashMapIndexedZZZ();
		main:{
			if(objHashMap== null){
				ExceptionZZZ ez = new ExceptionZZZ("HashMap to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			  }
			if(StringZZZ.isEmpty(sMethodName)){
				ExceptionZZZ ez = new ExceptionZZZ("String MethodName", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
				throw ez;	
			}
			//###############################
			String sMethod = null; //F�r das Exception Handling: Name der Methode, die gerade gesucht, invoked, etc. wird
			Object obj = null;        //F�r das Exception Handling: Objekt, das gerade in Arbeit ist, bzgl. der Methode
			try {
					
			//2. Vergleiche der gleichen Hashmap, elemtweise.
			HashMap objHashMapExt = objHashMap;
			HashMap objHashMapInt = objHashMap;
						
			/*
			for (objHashMapExt.Entry e : objHashMapExt.entrySet()){
				System.out.println(e.getKey() + " = " + e.getValue());
			}*/
			
			
			//#################################
			//Ersten Wert holen: �ussere Schleife
			Iterator itExt = objHashMapExt.keySet().iterator();
			int iposExt = -1;
			while(itExt.hasNext()){
				//System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
				Object objKeyExt = itExt.next(); //Wer sagt denn, das ein Key immer String sein muss.....
				iposExt++;
				//System.out.println("+++ Neuer Keywert: " + objKeyExt.toString());
				
				Object objValueExt = objHashMapExt.get(objKeyExt);
				//System.out.println("+++ Neues Element (�u�ere Schleife): " + objValueExt.toString());
				
				//+++ Suche nun nach der Methode im Objekt.
				//Merke: In der Reflection API Paramter f�r die Methode anzugeben geht so:
				//Class[] classaParam = new Class[1];
				//classaParam[0] = objKeyValueToStore.getClass();
				//
				//aber hier ist classaParm=null;  
				sMethod = sMethodName; //F�r das Exception Handling
				obj = objValueExt; //F�r das Exception Handling
				Method mExt = Mopex.getSupportedMethod(objValueExt.getClass(), sMethodName, null);
				
				if(mExt==null){
					ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethodName + " in the value of the key object: " + objKeyExt.toString() , iERROR_RUNTIME, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
					throw ez;
				}
			

				//+++ Rufe die Methode auf dem Objekt auf. Das Ergebnis ist wiederrum ein Objekt.
				Object objValueErgExt = null;
				
				//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
				//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
				
				//Object objReturnType = m.invoke(objWithKey, null);
				//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
				//wir habe aber keinen Parameter, also NULL
				obj = objValueExt; //F�r das Exception Handling
				objValueErgExt = mExt.invoke(objValueExt, null);
				if(objValueErgExt!=null){
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Ergebnisobjekt: " + objValueErgExt.toString());
				}else{
					//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH");
					
					//TODO: Hier muss eigentlich zum n�chsten Element der �u�eren Schleife gegangen werden.
					break main;
				}
				
				//+++ Suche die "equals" Methode in dem Ergebnisobjekt und f�hre sie aus.
				//!!! Parameter ist das andere Ergebnisobjekt
				Class[] classaParam = new Class[1];
				classaParam[0] = Object.class; //die MEthode "equals" bekommt als Parameter ein Classe Objekt, nicht genauer wie z.B. String. Dazu wird keine Methode gefunden. Also nicht: objTestErgInt.getClass();
				
				sMethod = "equals(OBJECT)"; //NEIN, das wird nicht gefunden.... " + objTestErgInt.getClass().getName() + ")"; //F�r das Exception Handling
				obj = objValueErgExt; //F�r das Exception Handling
				Method mEqualsExt = Mopex.getSupportedMethod(objValueErgExt.getClass(), "equals", classaParam);  //Merke: Der Vergleich wird dann in der inneren Schleife durchgef�hrt.
				
				
				
				//#############################################
				//### Zweiten Wert holen: Innere Schleife	
				Iterator itInt = objHashMapInt.entrySet().iterator();
				int iposInt = -1;
				ArrayList alInt = new ArrayList(); //zum Speichern der in der inneren Schleife gefundenen Indexwerte
				while(itInt.hasNext()){
					Map.Entry objEntry = (Map.Entry)  itInt.next(); 
					Object objValueInt = objEntry.getValue();
					iposInt++;
					
//					+++ Suche nun nach der Methode im Objekt.
					//Merke: In der Reflection API Paramter f�r die Methode anzugeben geht so:
					//Class[] classaParam = new Class[1];
					//classaParam[0] = objKeyValueToStore.getClass();
					//
					//aber hier ist classaParm=null;  
					sMethod = sMethodName; //F�r das Exception Handling
					obj = objValueInt; //F�r das Exception Handling
					Method mInt = Mopex.getSupportedMethod(objValueExt.getClass(), sMethodName, null);
					
					if(mInt==null){
						ExceptionZZZ ez = new ExceptionZZZ("NoMethodFound " + sMethod + " in the value object: " + obj.toString() , iERROR_RUNTIME, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
						throw ez;
					}
				

					//+++ Rufe die Methode auf dem Objekt auf. Das Ergebnis ist wiederrum ein Objekt.
					Object objValueErgInt = null;
					
					//kein Return Objekt integer: int iCode = ((Integer) m.invoke(objWithKeyValue, null)).intValue();
					//System.out.println("Ergebnis der Method invocation addNewKeyPrimary=" + iCode);
					
					//Object objReturnType = m.invoke(objWithKey, null);
					//so w�rde der Parameterwert �bergeben an eine MEthode, die einen Parameter erwartet objTestErgInt = mInt.invoke(objKeyStore, new Object[] {objKeyValueToStore});
					//wir habe aber keinen Parameter, also NULL
					obj = objValueInt; //F�r das Exception Handling
					objValueErgInt = mInt.invoke(objValueInt, null);
					if(objValueErgInt!=null){
						//System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objValueErgInt.toString());
					}else{
						//System.out.println("Methode " + sMethodName + " erfolgreich invoked. Kein Ergebnisobjekt. ABBRUCH zum n�chsten Wert");
						
						//TODO: Hier muss eigentlich zum n�chsten Element der inneren Schleife gegangen werden.
						break main;
					}
					
			        //##########################################################	
					//+++ F�re die "equals" Methode aus.
					Object objErgEquals = mEqualsExt.invoke(objValueErgExt, new Object[] {objValueErgInt});
					if(objErgEquals!=null){
						//System.out.println("Methode " + sMethod + " erfolgreich invoked. Ergebnisobjekt: " + objErgEquals.toString());
						if(objErgEquals.toString().equals("false")){
							//Mache nix;
						}else{
							//Erweitere die Index-Arraylist um die aktuelle Indexposition
							alInt.add(new Integer(iposInt));
						}
					}else{
						//System.out.println("Methode " + sMethod + " erfolgreich invoked. Kein Ergebnisobjekt. R�CKGABEWERT: false");
						 //Mache nix;
					}
					
				} //End while "innere Schleife"
				
				//############################################################ 
				//+++ Pr�fe die Anzahl der Index-Arraylist-Elemente und f�ge bei mehr als 2 Elementen den Wert hinzu. Merke: 1 Element sollte immer darin sein, wenn der gleich wert auf sich selbst gepr�ft wurde.
				if(alInt.size()>=2){
					hmIndexed.put(new Integer(iposExt), alInt);
				}
				
			} //end while "�u�ere Schleife"
			
			} catch (IllegalArgumentException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalArgumentException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (IllegalAccessException e) {
				ExceptionZZZ ez = new ExceptionZZZ("IllegalAccessException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (InvocationTargetException e) {
				ExceptionZZZ ez = new ExceptionZZZ("InvocationTargetException bei " + sMethod + " in Object " + obj.getClass().getName() + ": "  + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} catch (NoSuchMethodException e) {
				ExceptionZZZ ez = new ExceptionZZZ("NoSuchMethodException " + sMethod + " in Object " + obj.getClass().getName() + ": " + e.getMessage(), iERROR_PARAMETER_VALUE, HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			} 
		}//end main
		return hmIndexed;
	}
	
	/** Entferne Dubletten. Dabei wird die IndexHashMap zum Kennzeichnen der Dubletten verwendet.
	 *    Intern werden die Dubletten in einer einzigen ArrayList zusammengefasst und diese dann gel�scht.
	* @param hm
	* @param hmIndexed
	* @param sFlagRemain "KEEPFIRST" / "KEEPLAST", default ist "KEEPLAST"
	* @throws ExceptionZZZ
	* @return Anzahl der entferneten werte
	* 
	* lindhauer; 17.05.2011 11:37:46
	 */
	public static int removeDupsFromByIndex( ArrayList objAl, HashMapIndexedZZZ hmIndexed, String sFlagRemainIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
		if(objAl==null){
			ExceptionZZZ ez = new ExceptionZZZ("ArrayList to compare", iERROR_PARAMETER_MISSING,   HashMapZZZ.class.getName(), ReflectCodeZZZ.getMethodCurrentName());								  
			throw ez;	
		  }
		
		if(hmIndexed==null){
			ExceptionZZZ ez = new ExceptionZZZ("HashMapIndexed", iERROR_PARAMETER_MISSING, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}			 
		if(hmIndexed.size()==0) break main;
		
		//Pr�fe die Struktur der Index-Hash-Map
		boolean btemp = hmIndexed.checkStructure(true);  //!!!Hier keine Fehler auswerfen.
		if(btemp==false){
		   String sMessage = null;	
			try{
				btemp = hmIndexed.checkStructure(false);  //!!!Hier Fehler auswerfen.
			}catch(ExceptionZZZ e){
				sMessage = e.getDetailAllLast();
			}
			ExceptionZZZ ez = new ExceptionZZZ("HashMapIndexed has no valid structure. " + sMessage, iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		String sFlagRemain = null;
		if(StringZZZ.isEmpty(sFlagRemainIn)){
			sFlagRemain = "KEEPLAST";
		}else{
			sFlagRemain = sFlagRemainIn.toUpperCase();
			if(sFlagRemain!="KEEPLAST" && sFlagRemain != "KEEPFIRST"){
				ExceptionZZZ ez = new ExceptionZZZ("Wrong reamining Flag (=" + sFlagRemain + "). Expected 'KEEPFIRST' / 'KEEPLAST'.", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
			
		//##################################################
		/* Algorithmus:
		 * Fasse alle ArrayListen der HashMapIndexed zu einer einzigen zusammen. 
		 * Ignoriere dabei jeweils den ersten / bzw. letzten Eintrag.
		 * Sorge auch daf�r, dass die Eintr�ge in der neuen ArrayList unique sind.
		 */
		
		//1. Zusammenfassen
		//Die Indizes sortieren!!!
		//TEST
		Iterator ittest = hmIndexed.keySet().iterator();
		/*
		while(ittest.hasNext()){
			Integer objtest = (Integer)ittest.next();
			System.out.println(objtest.intValue());
		}*/
		HashMapUtilZZZ.sortByKeyInteger_usingInnerComparator(hmIndexed);  //Fest steht, das die Schl�ssel Integer Werte sind.
		
		/*
		List list = new LinkedList( hmIndexed.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2){
				return( (Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue() );  //	http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
			}
		});
		*/
		
		
		Iterator it = hmIndexed.keySet().iterator(); //jetzt sortiert                                               //das ist sonst nicht sortiert!!! hmIndexed.entrySet().iterator();
		ArrayListZZZ alUnique = new ArrayListZZZ();
		while(it.hasNext()){
			Object obj = it.next();
			ArrayList alIndexed = (ArrayList) hmIndexed.get(obj);
			Collections.sort(alIndexed); //normale sortierung sicherstellen
			int iposStart = 0;
			int iposEnd = alIndexed.size()-1;
			if(sFlagRemain.equals("KEEPFIRST")) iposStart = iposStart + 1; //halt den ersten oder den letzten weggelassen, d.h. er soll "erhalten bleiben".
			if(sFlagRemain.equals("KEEPLAST")) iposEnd = iposEnd-1;
				
				for(int icount=iposStart; icount <= iposEnd; icount++){
					alUnique.addUnique(alIndexed.get(icount)); 
				}
		}
		
		//2. Sortieren
		//Vector objV = new Vector(objHt.keySet());
		//if(this.sSortDirectionAlias.equals("-")){
			//Collections.sort(objV, Collections.reverseOrder());				
		//}else{
			//Collections.sort(objV);			
		//}

		//anders als bei der HashMap-Entfernung von hinten nach vorne durchgehen Collections.sort(alUnique);
		Collections.sort(alUnique, Collections.reverseOrder());
		
		//3. In der hashMap an den entsprechend zu entfernenden Keys finden.
		//???? ist das sicher ???? Wenn die IndexHashMap auf der �bergebenen HashMap basiert, wahrscheinlich schon.
		//Object[] obja = new Object[hm.size()];
		//obja = hm.keySet().toArray(obja);  //Zum Anschauen beim Debuggen

		//+++++
		//ArrayList alKeyRemove = new ArrayList(); //Die Liste der zu entfenenden Schl�ssel-Element-Paare
		int iposMax = objAl.size();
		
		//Anders als bei der HashMap-Entfernung jetzt eine For - Schleife und von hinten nach vorne durchgehen.
		for(int icount = iposMax; icount >= 0; icount --){
			Integer objInt = (Integer) alUnique.get(iReturn);  //Hole als Vergleichswert die sortierten Indizes
			if(objInt.intValue()==icount){
				objAl.remove(icount);
				iReturn++; //zum n�chsten Vergleichswert
				if(iReturn >= alUnique.size()) {
					break; //for Schleife verlassen wenn es keine weiteren Vergleichswerte gibt.
				}
			}
		}//end for	
		}//end main
		return iReturn;
	}
	
	/** Entferne Dubletten. Dabei wird die IndexHashMap zum Kennzeichnen der Dubletten verwendet.
	 * 
	* @param hm
	* @param hmIndexed
	* @param sFlagRemain "KEEPFIRST" / "KEEPLAST", default ist "KEEPLAST"
	* @throws ExceptionZZZ
	* @return Anzahl der entferneten werte
	* 
	* lindhauer; 17.05.2011 11:37:46
	 */
	public static int removeDupsFromByIndex(HashMap hm, HashMapIndexedZZZ hmIndexed, String sFlagRemainIn) throws ExceptionZZZ{
		int iReturn = 0;
		main:{
		if(hm == null){
			ExceptionZZZ ez = new ExceptionZZZ("HashMap", iERROR_PARAMETER_MISSING, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		
		if(hmIndexed==null){
			ExceptionZZZ ez = new ExceptionZZZ("HashMapIndexed", iERROR_PARAMETER_MISSING, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}			 
		if(hmIndexed.size()==0) break main;
		
		//Prüfe die Struktur der Index-Hash-Map
		boolean btemp = hmIndexed.checkStructure(true);  //!!!Hier keine Fehler auswerfen.
		if(btemp==false){
		   String sMessage = null;	
			try{
				btemp = hmIndexed.checkStructure(false);  //!!!Hier Fehler auswerfen.
			}catch(ExceptionZZZ e){
				sMessage = e.getDetailAllLast();
			}
			ExceptionZZZ ez = new ExceptionZZZ("HashMapIndexed has no valid structure. " + sMessage, iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
				
		String sFlagRemain = null;
		if(StringZZZ.isEmpty(sFlagRemainIn)){
			sFlagRemain = "KEEPLAST";
		}else{
			sFlagRemain = sFlagRemainIn.toUpperCase();
			if(sFlagRemain!="KEEPLAST" && sFlagRemain != "KEEPFIRST"){
				ExceptionZZZ ez = new ExceptionZZZ("Wrong reamining Flag (=" + sFlagRemain + "). Expected 'KEEPFIRST' / 'KEEPLAST'.", iERROR_PARAMETER_VALUE, HashMapIndexedZZZ.class.getName(),  ReflectCodeZZZ.getMethodCurrentName());
				throw ez;
			}
		}
			
		//##################################################
		/* Algorithmus:
		 * Fasse alle ArrayListen der HashMapIndexed zu einer einzigen zusammen. 
		 * Ignoriere dabei jeweils den ersten / bzw. letzten Eintrag.
		 * Sorge auch dafür, dass die Einträge in der neuen ArrayList unique sind.
		 */
		
		//1. Zusammenfassen
		//Die Indizes sortieren!!!
		//TEST
		Iterator ittest = hmIndexed.keySet().iterator();
		/*
		while(ittest.hasNext()){
			Integer objtest = (Integer)ittest.next();
			System.out.println(objtest.intValue());
		}*/
		HashMapUtilZZZ.sortByKeyInteger_usingInnerComparator(hmIndexed);  //Fest steht, das die Schl�ssel Integer Werte sind.
		
		/*
		List list = new LinkedList( hmIndexed.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2){
				return( (Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue() );  //	http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
			}
		});
		*/
		
		
		Iterator it = hmIndexed.keySet().iterator(); //jetzt sortiert                                               //das ist sonst nicht sortiert!!! hmIndexed.entrySet().iterator();
		ArrayListZZZ alUnique = new ArrayListZZZ();
		while(it.hasNext()){
			Object obj = it.next();
			ArrayList alIndexed = (ArrayList) hmIndexed.get(obj);
			Collections.sort(alIndexed); //normale sortierung sicherstellen
			int iposStart = 0;
			int iposEnd = alIndexed.size()-1;
			if(sFlagRemain.equals("KEEPFIRST")) iposStart = iposStart + 1; //halt den ersten oder den letzten weggelassen, d.h. er soll "erhalten bleiben".
			if(sFlagRemain.equals("KEEPLAST")) iposEnd = iposEnd-1;
				
				for(int icount=iposStart; icount <= iposEnd; icount++){
					alUnique.addUnique(alIndexed.get(icount)); 
				}
		}
		
		//2. Sortieren
		//Vector objV = new Vector(objHt.keySet());
		//if(this.sSortDirectionAlias.equals("-")){
			//Collections.sort(objV, Collections.reverseOrder());				
		//}else{
			//Collections.sort(objV);			
		//}

		Collections.sort(alUnique); 
		
		//3. In der hashMap an den entsprechend zu entfernenden Keys finden.
		//???? ist das sicher ???? Wenn die IndexHashMap auf der �bergebenen HashMap basiert, wahrscheinlich schon.
		Object[] obja = new Object[hm.size()];
		obja = hm.keySet().toArray(obja);  //Zum Anschauen beim Debuggen

		//+++++
		ArrayList alKeyRemove = new ArrayList(); //Die Liste der zu entfenenden Schl�ssel-Element-Paare
		int ipos = 0;
		Iterator itKey = hm.keySet().iterator();
		while(itKey.hasNext()){
			Integer objInt = (Integer) alUnique.get(iReturn);  //Hole als Vergleichswert die sortierten Indizes
		    			
		    Object objKey = (Object)itKey.next();
			if(objInt.intValue()==ipos){
				alKeyRemove.add(objKey);
				iReturn++;  //zum n�chsten Vergleichswert
				if(iReturn >= alUnique.size()) {
					break; //while Schleife verlassen wenn es keine weiteren Vergleichswerte gibt.
				}
			}
			
			
			ipos++; //zur n�chsten Position in der Hashmap
		}

		
		//Das eigentliche Entfernen aus der Hashmap über die Keys
		Iterator itRemove = alKeyRemove.iterator();
		while(itRemove.hasNext()){
			Object objKey = itRemove.next();
			hm.remove(objKey);	
		}
		
		
		
		}//end main
		return iReturn;
	}


public int removeDupsFrom(HashMap hm, String sFlagRemain) throws ExceptionZZZ{
	int iReturn = 0;
	main:{
		iReturn = HashMapIndexedZZZ.removeDupsFromByIndex(hm, this, sFlagRemain);
	}
	return iReturn;
}
}//end class
