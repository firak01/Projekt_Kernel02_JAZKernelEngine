package basic.zBasic.util.abstractList;

import java.util.ArrayList;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;

public class ListUtilZZZ implements IConstantZZZ{

	//Private Konstruktor, zum Verbergen, damit die Klasse nicht instanziiert werden kann.
	//Ist hier protected, damit ListUtilZZZ erben kann, wg. Fehlermeldung:
	//"Implicit super constructor ListUtilZZZ() is not visible for default constructor. Must define an explicit constructor"
	//Zudem: <K,V> nicht auf Klassenebene definieren, sondern damit das für static Methoden möglich ist, nur auf Methodenebene. 
	//       oder <?,?> verwenden.
	
	protected ListUtilZZZ() {}
	
	
	/**
	 * @param list
	 * @return
	 * 
	 * https://stackoverflow.com/questions/10766492/what-is-the-simplest-way-to-reverse-an-arraylist
	 */
//	public static <T> List<T> reverse(final List<T> list) {
//    final int size = list.size();
//    final int last = size - 1;
//
//    // create a new list, with exactly enough initial capacity to hold the (reversed) list
//    final List<T> result = new ArrayList<>(size);
//
//    // iterate through the list in reverse order and append to the result
//    for (int i = last; i >= 0; --i) {
//        final T element = list.get(i);
//        result.add(element);
//    }
//
//    // result now holds a reversed copy of the original list
//    return result;
//}
	public static <T> List<T> reverse(List<T> list) throws ExceptionZZZ {
	    List<T> listReturn = null; 
		main:{
			if(list==null) break main;
			if(list.size()==0) break main;
			
			
			
			// create a new list, with exactly enough initial capacity to hold the (reversed) list
			final int size = list.size();
			final int last = size - 1;
			
		    listReturn = new ArrayList<>(size);
		    
		    // iterate through the list in reverse order and append to the result
		    for (int i = last; i >= 0; --i) {
		        final T element = list.get(i);
		        listReturn.add(element);
		    }
		}//end main	
	    return listReturn;
	}
	
	//################################################################
	public static <T> List<T> join(List<T> lista1, List<T> lista2) throws ExceptionZZZ {
		return ListUtilZZZ.join(lista1, lista2, false);
	}
	
	public static <T> List<T> join(List<T> lista1, List<T> lista2, boolean bFlagUnique) throws ExceptionZZZ{
		List<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			
			if(bFlagUnique==false){
				//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
				listaReturn = new ArrayList<T>();
				if(lista1!=null){
					for(int icount=0; icount < lista1.size(); icount++){
						listaReturn.add(lista1.get(icount));
					}
				}//lista1!=null
				if(lista2!=null){
					for(int icount=0; icount < lista2.size(); icount ++){
						listaReturn.add(lista2.get(icount));
					}
				}		
				break main;
				
			}else{
				//Wenn 'uniqued' werden soll, dann ist "keepFirst" die default-Strategie
				listaReturn = ListUtilZZZ.joinKeepFirst(lista1,lista2);		
				break main;
			}//End if (bFlagUnique ....
			
		}//END main:
		return listaReturn;
	}
	
	
	public static <T> List<T> join(List<T> lista1, List<T> lista2, List<T> lista3) throws ExceptionZZZ {
		return ListUtilZZZ.join(lista1, lista2, lista3, false);
	}
	
	public static <T> List<T> join(List<T> lista1, List<T> lista2, List<T> lista3, boolean bFlagUnique) throws ExceptionZZZ{
		List<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null  && lista3 ==null) break main;
			
			
			if(bFlagUnique==false){
				//Wenn nicht ''uniqued' werden soll, dann kann man sofort in der Return-liste joinen
				listaReturn = new ArrayList<T>();
				if(lista1!=null){
					for(int icount=0; icount < lista1.size(); icount++){
						listaReturn.add(lista1.get(icount));
					}
				}//lista1!=null
				if(lista2!=null){
					for(int icount=0; icount < lista2.size(); icount ++){
						listaReturn.add(lista2.get(icount));
					}
				}//lista2!=null
				if(lista3!=null){
					for(int icount=0; icount < lista3.size(); icount ++){
						listaReturn.add(lista3.get(icount));
					}
				}			
				break main;
				
			}else{
				//Wenn 'uniqued' werden soll, dann ist "keepFirst" die default-Strategie
				listaReturn = ListUtilZZZ.joinKeepFirst(lista1,lista2,lista3);		
				break main;
			}//End if (bFlagUnique ....
			
		}//END main:
		return listaReturn;
	}
	
	//##############################
	public static <T> List<T> joinKeepFirst(List<T> lista1, List<T> lista2) throws ExceptionZZZ {
		List<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null) break main;
			
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			ArrayList<T> listaTemp = new ArrayList<T>();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}		
			
			listaReturn = ArrayListUtilZZZ.uniqueKeepFirst(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	public static <T> List<T> joinKeepFirst(List<T> lista1, List<T> lista2, List<T> lista3) throws ExceptionZZZ {
		List<T> listaReturn = null;
		main:{
			if(lista1==null && lista2 ==null && lista3 ==null) break main;
			
			//Wenn 'uniqued' werden soll, dann erst in eine temporaere Liste joinen
			List<T> listaTemp = new ArrayList<T>();
			if(lista1!=null){
				for(int icount=0; icount < lista1.size(); icount++){
					listaTemp.add(lista1.get(icount));
				}
			}//lista1!=null
			if(lista2!=null){
				for(int icount=0; icount < lista2.size(); icount ++){
					listaTemp.add(lista2.get(icount));
				}
			}//lista2!=null
			if(lista3!=null){
				for(int icount=0; icount < lista3.size(); icount ++){
					listaTemp.add(lista3.get(icount));
				}
			}			
			
			listaReturn = ListUtilZZZ.uniqueKeepFirst(listaTemp);			
		}//END main:
		return listaReturn;
	}
	
	//#############################################
	public static <T> List<T> uniqueKeepFirst(List<T> lista) throws ExceptionZZZ {
		List<T> listReturn = null;
		main:{
			if(lista==null)break main;					
		
			listReturn=new ArrayListZZZ<T>();
			for(int icount=0; icount < lista.size(); icount++ ){
				if(! listReturn.contains(lista.get(icount))) listReturn.add(lista.get(icount));
			}	
		}//End main:
		return listReturn;
	}
	
	//##############################################
	public static <T> List<T> replace(List<T> listOriginal, int iStart, int iEnd, List<T> listReplacement) throws ExceptionZZZ{
		List<T> listasReturn = new ArrayList<T>();
		main:{
			try {
				if (listOriginal == null) {
				throw new IllegalArgumentException("listOriginal");
				}
				
				if (listReplacement == null) {
				throw new IllegalArgumentException("listReplacement");
				}
				
				if (iStart < 0 || iEnd < iStart || iEnd >= listOriginal.size()) {
				throw new IndexOutOfBoundsException(
				"Invalid range: " + iStart + " - " + iEnd);
				}
				
				// vor dem zu ersetzenden Bereich
				listasReturn.addAll(listOriginal.subList(0, iStart));
				
				// Ersatzinhalt
				listasReturn.addAll(listReplacement);
				
				// nach dem zu ersetzenden Bereich
				listasReturn.addAll(listOriginal.subList(iEnd + 1, listOriginal.size()));
			}catch(IllegalArgumentException iae) {
				ExceptionZZZ ez = new ExceptionZZZ(iae);
				throw ez;
			}catch(IndexOutOfBoundsException ioob) {
				ExceptionZZZ ez = new ExceptionZZZ(ioob);
				throw ez;
			}
		}//end main:
		return listasReturn;
	}
}
