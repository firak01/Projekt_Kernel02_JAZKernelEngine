package basic.zKernel.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapMultiZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.file.ini.ZTagFormulaIni_VariableZZZ;

public class KernelCacheZZZ extends AbstractKernelCacheZZZ{
	private HashMapMultiZZZ<String, ICachableObjectZZZ>hmCache=null;
	
	public KernelCacheZZZ() throws ExceptionZZZ{
		super();
	}
	
	@Override
	public HashMapMultiZZZ<String, ICachableObjectZZZ> getHashMapCache() {
		if(this.hmCache==null){
			this.hmCache=new HashMapMultiZZZ<String,ICachableObjectZZZ>();
		}
		return this.hmCache;
	}

	@Override
	public void setHashMapCache(HashMapMultiZZZ<String, ICachableObjectZZZ> hmCache) {
		this.hmCache=hmCache;
	}

	@Override
	public ICachableObjectZZZ getCacheEntry(String sSection, String sProperty) throws ExceptionZZZ {
		ICachableObjectZZZ objReturn = null;
		main:{
			HashMapMultiZZZ<String, ICachableObjectZZZ> hmCache = this.getHashMapCache();
			objReturn = (ICachableObjectZZZ) hmCache.get(sSection, sProperty);
			if(objReturn!=null){
				if(objReturn.isCacheSkipped()){ //d.h. es wurde erzwungen, dass der Eintrag neu gelesen werden muss.
					objReturn=null;
				}
			}
		}//end main:
		return objReturn;
	}

	@Override
	public void setCacheEntry(String sSection, String sProperty, ICachableObjectZZZ objEntry) throws ExceptionZZZ {
		HashMapMultiZZZ<String, ICachableObjectZZZ> hmCache = this.getHashMapCache();
		hmCache.put(sSection, sProperty, objEntry);
	}

	@Override
	public int isCacheSkippedContainingVariable(boolean bValue, String sVariableName) throws ExceptionZZZ {
		int iReturn = 0; //Die Anzahl der gefundenen und veränderten Einträge
		main:{
			if(StringZZZ.isEmpty(sVariableName)) break main;
			
			/*
			 Java regex to match word with nonboundaries (!!!!! FGL !!!!!) – contain word example

Suppose, you want to match “java” such that it should be able to match words like “javap” or “myjava” or “myjavaprogram” i.e. java word can lie anywhere in the data string. It could be start of word with additional characters in end, or could be in end of word with additional characters in start as well as in between a long word.

"\B" matches at every position in the subject text where "\B" does not match. "\B" matches at every position that is not at the start or end of a word.

To match such words, use below regex :

    Solution Regex : \\Bword|word\\B

String data1 = "Searching in words : javap myjava myjavaprogram";
       
String regex = "\\Bjava|java\\B";
 
Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
Matcher matcher = pattern.matcher(data1);
while (matcher.find())
{
    System.out.print("Start index: " + matcher.start());
    System.out.print(" End index: " + matcher.end() + " ");
    System.out.println(matcher.group());
}
 
Output:
 
Start index: 21 End index: 25 java
Start index: 29 End index: 33 java
Start index: 36 End index: 40 java

Please note that it will not match “java” word in first example i.e. “Today, java is object oriented language” because “\\B” does not match start and end of a word.
			 */
			
			ZTagFormulaIni_VariableZZZ objVariable = new ZTagFormulaIni_VariableZZZ();
			String sRegEx=objVariable.getTagPartOpening() + sVariableName + objVariable.getTagPartClosing(); //Den Namen einer Variablen finden, über diesen RegEx-Ausdruck
			sRegEx = "\\B"+sRegEx+"|"+sRegEx+"\\B";
			ArrayList<ICachableObjectZZZ> listaCacheEntry = this.getCacheEntriesWithPropertiesByRegEx(sRegEx);
			for(ICachableObjectZZZ objCacheEntry: listaCacheEntry){				
				objCacheEntry.isCacheSkipped(bValue);
				iReturn++;
			}
		}//end main:
		return iReturn;
	}

	@Override
	public ArrayList<ICachableObjectZZZ> getCacheEntriesWithPropertiesByRegEx(String sRegEx) throws ExceptionZZZ {
		ArrayList<ICachableObjectZZZ>listaObjectReturn = new ArrayList<ICachableObjectZZZ>();
		main:{
			HashMapMultiZZZ<String, ICachableObjectZZZ> hmCache = this.getHashMapCache();
			ICacheFilterZZZ objFilter = this.getFilterObject(sRegEx); //Das ist performanter, da das Pattern Objekt schon kompiliert ist und so besser mehrfach aufgerufen werden kann.
			
			//20190818 : Diese MultiHashmap durchgehen und die einzelenen Elemente filtern. 
			Set<String> setOuter = (Set<String>) hmCache.getOuterKeySet();
			for(String sKeyOuter : setOuter){
				HashMap<String,ICachableObjectZZZ>hmInner=hmCache.getInnerHashMap(sKeyOuter);
				Set<String> setInner = (Set<String>) hmCache.getInnerKeySet(sKeyOuter);
				for(String sKeyInner : setInner){
					ICachableObjectZZZ objCachable = hmInner.get(sKeyInner);
										
					boolean btemp = objFilter.accept(objCachable);//Wende nun den Filter auf das gecachte Object an.
					if(btemp)	listaObjectReturn.add(objCachable);   //Falls der Filter zutrifft, packe das Objekt in das Ergebnis. Damit kann dann, z.B. für alle Formeln mit einer bestimmten Variable, der Cache geskipped werden. 
				}
			}
		}//end main:
		return listaObjectReturn;
	}
	
	@Override
	//20190818: Gelöst als innere anonyme Klasse
	public ICacheFilterZZZ getFilterObject(final String sRegEx){
		//Erstelle innere anonyme Klasse
		return new ICacheFilterZZZ(){
			
			 /*    RegEx:    	
		    Zwei kleine Tipps für deine Methode
		    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
		    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
		     */
			//alt: private Pattern pattern = Pattern.compile(sRegEx, Pattern.CASE_INSENSITIVE); //es muss der Ausdruck z.B. z:Var nicht casesensitive in der ini stehen!
			//neu:
			private Pattern pattern = Pattern.compile(Pattern.quote(sRegEx), Pattern.CASE_INSENSITIVE); //es muss der Ausdruck z.B. z:Var nicht casesensitive in der ini stehen!
			
			@Override
			public boolean accept(ICachableObjectZZZ objCachable) throws ExceptionZZZ {
				boolean bReturn = false;
				main:{
					if(objCachable==null) break main;
					if(!objCachable.wasValueComputed())break main; //soll etwas mehr Performance liefern, da der Aufwand ein Pattern  zu finden entfällt.
					
					String sStringToMatch = objCachable.getValueForFilter();
					if(StringZZZ.isEmpty(sStringToMatch)) break main;
										
					Matcher matcher = pattern.matcher(sStringToMatch);
					bReturn = matcher.find();//Merke: .matches() funktioniert nicht!!! weil: Attempts to match the entire region against the pattern.
					                                                     //.find() Attempts to find the next subsequence of the input sequence that matches the pattern.
				}//end main:
				return bReturn; //Hier regeex pattern und objEntryConfiguration.getValueForFilter() matchen		
			}//Ende der inneren Methode "accept"
		}; //Ende der anonymen inneren Klasse	
	}//end method: filter(...)
	
	/** Löscht die intern verwendete HashMap.
	 *  Gibt als int - Wert die Anzahl der Einträge, die vorher darin waren zurück.
	 * @return
	 * @author Fritz Lindhauer, 28.08.2022, 14:52:50
	 */
	public int clear() {
		int iReturn = 0;
		main:{
			HashMapMultiZZZ<String, ICachableObjectZZZ> hmCache = this.getHashMapCache();
			iReturn = hmCache.size();
			hmCache.clear();			
		}//end main:
		return iReturn;
	}
}
