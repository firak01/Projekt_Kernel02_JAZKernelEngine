package basic.zKernel;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;

/**This class is used for reading/writing from/to 1 one or more .property-file(s).
 * !!! This class is build as a Singleton., with a private constructor !!! Therefore use the static KernelPropertyZZZ.getInstance(...) to receive an object.
 * 
 * Usage in this framework:
 *  The .property-file(s) will contain parameters, which were used by a "Program" of a "Module".
 *  Remember: The KernelObject is used for acessing module configuration files, etc.
 *  
 * MERKE: 
 * Hier geht es darum mehrere Property-Dateien zu verwalten.
 * Eine Utility Klasse für die Arbeit mit einer Property-Datei ist PropertiesUtilZZZ
 * 
 * MERKE:
 * Java ignoriert Kommentarzeilen in .properties-Dateien automatisch beim Aufruf der load()-Methode. 
 * Sie müssen diese nicht manuell herausfiltern.
 * Kommentarzeichen in Properties:
 * # (Raute) am Zeilenanfang markiert einen Kommentar.
 * ! (Ausrufezeichen) am Zeilenanfang wird ebenfalls als Kommentar gewertet.
 * Leere Zeilen werden ebenfalls automatisch übersprungen.
 *  
 * @author 0823
 *
 */
public class KernelPropertyZZZ extends AbstractObjectWithFlagZZZ implements java.io.Serializable {
	private static final long serialVersionUID = 7649664671090993076L;

		// --- Singleton Instanz ---
		//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
		protected static KernelPropertyZZZ objPropertyINSTANCE;   
		
		//##########################################################
		//Trick, um Mehrfachinstanzen zu verhindern (optional)
		//Warum das funktioniert:
		//initialized ist static → nur einmal pro ClassLoader
		//Wird beim ersten Konstruktoraufruf gesetzt
		//Jeder weitere Versuch (Reflection!) schlägt fehl
	    private static boolean INITIALIZED = false;
	    
	    //Reflection-Schutz ist eine Hürde, kein Sicherheitsmechanismus.
	    //Denn:
	    //Field f = AbstractService.class.getDeclaredField("initialized");
	    //f.setAccessible(true);
	    //f.set(null, false);
	    //Danach kann man wieder instanziieren.
		//##########################################################
		
		
		//--- weiter Objekte ---
		private static HashMap<File,Properties> hmProperty = new HashMap<File,Properties>();
		
		/** This is a private constructor !!! 
		 * You should use the static .getInstance(...)-Method to receive an object.
		 * 
		* 0823; 01.06.2006 08:42:46
		 * @param sConfigFile
		 */
		private KernelPropertyZZZ(String sConfigFile) throws IOException{
			load(sConfigFile);
		}
		
		private KernelPropertyZZZ(File fileConfigFile) throws IOException{
			load(fileConfigFile);
		}
		
		
		public void finalize(){
			//Der JUnit Test hat herausgefunden, dass das Objekt so am besten zerst�rt wird.
			objException=null;
			hmProperty=null;
		}
		
		
	/** KernelPropertyZZZ, receive the singleton object. This method does only work, if .getInstance( sFilePath ) was used before.
	* 0823; 01.06.2006 09:59:53
	 * @return
	 * @throws ExceptionZZZ
	 */
	public static synchronized KernelPropertyZZZ getInstance() throws ExceptionZZZ{
		if(objPropertyINSTANCE == null){
			ExceptionZZZ ez = new ExceptionZZZ(sERROR_PROPERTY_MISSING, iERROR_PROPERTY_MISSING, null,ReflectCodeZZZ.getMethodCurrentName());
			throw ez;
		}
		return objPropertyINSTANCE;
	}
		
	/** KernelPropertyZZZ, receive the singleton object. If the file has been loaded before it will become reloaded, but no new property will be stored in the hashmap ( .getFileLoadedAll() ).
	* 0823; 01.06.2006 10:01:37
	 * @param sConfigFile
	 * @return
	 * @throws IOException
	 */
	public static KernelPropertyZZZ getInstance(String sConfigFile) throws ExceptionZZZ, IOException{
		KernelPropertyZZZ objReturn = null;
		main:{
			File fileConfigFile = new File(sConfigFile);
			objReturn = getInstance(fileConfigFile);
		}
		return objReturn;
	}
	
	/** KernelPropertyZZZ, receive the singleton object. If the file has been loaded before it will become reloaded, but no new property will be stored in the hashmap ( .getFileLoadedAll() ).
	* 0823; 01.06.2006 10:01:37
	 * @param sConfigFile
	 * @return
	 * @throws IOException
	 */
	public static KernelPropertyZZZ getInstance(File fileConfigFile) throws ExceptionZZZ, IOException{
		KernelPropertyZZZ objReturn = null;
		main:{
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(KernelPropertyZZZ.class) {
				if(objPropertyINSTANCE == null) {
					if (INITIALIZED) {
			            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
			        }
					objPropertyINSTANCE = getNewInstance(fileConfigFile);
					INITIALIZED=true;
				}else {
					//ggfs. die neue Datei hinzufügen
					//also, das was schon im Konstruktor passiert wiederholen.				
					if (hmProperty==null) hmProperty = new HashMap();
					Properties p = load(fileConfigFile);		
				}
				objReturn = objPropertyINSTANCE;
				
													
			}					
		}
		return objReturn;
	}
	
	public static KernelPropertyZZZ getNewInstance(String sConfigFile) throws IOException{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new KernelPropertyZZZ(sConfigFile);
	}
	
	public static KernelPropertyZZZ getNewInstance(File fileConfigFile) throws IOException{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		return new KernelPropertyZZZ(fileConfigFile);
	}
	
	
	public static Properties load(String sConfigFile) throws IOException{
		Properties objReturn=null;
		main:{
			File fileConfigFile = new File(sConfigFile);
			objReturn = load(fileConfigFile);			
		}//END main:
		return objReturn;
	}
	
	public static Properties load(File fileConfigFile) throws IOException{
		Properties objReturn=null;
		main:{
			objReturn = new Properties();			
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fileConfigFile)));			
			objReturn.load(in);		
			
			//Falls das "neue" File nicht vorhanden ist, dann wird eine Exception ausgeloest. Darum kann man nun schreiben... 
			hmProperty.put(fileConfigFile, objReturn);  //.... ohne das p == null ist.
			
		}//END main:
		return objReturn;
	}
	
	/** int, the number of elements stored in the hashmap, which stores all filepath and the corresponding java.util.properties - objects
	* 0823; 01.06.2006 10:13:52
	 * @return int
	 */
	public int getFileLoadedCounter(){
		int iReturn=0;
		main:{
			if(hmProperty==null) break main;
			iReturn = hmProperty.size();
		}
		return iReturn;
	}
	
	/** HashMap, the key is a filepath. The stored value is a java.util.properties-object.
	 * 
	* 0823; 01.06.2006 10:12:42
	 * @return HashMap
	 */
	public HashMap<File,Properties> getFileLoadedAll(){
		return hmProperty;
	}
	
	/** String, read from the propery file. If the file has not been loaded before, it will be loaded now.
	 * 
	* 0823; 01.06.2006 10:25:53
	 * @param sFilepath
	 * @param sKey
	 * @return
	 * @throws IOException
	 */
	public String getProperty(String sFilePath, String sKey) throws IOException{
		String sReturn = null;
		main:{
			File fileFilePath = new File(sFilePath);
			sReturn = this.getProperty(fileFilePath, sKey);
		}//END main:
		return sReturn;
	}
	
	/** String, read from the propery file. If the file has not been loaded before, it will be loaded now.
	 * 
	* 0823; 01.06.2006 10:25:53
	 * @param sFilepath
	 * @param sKey
	 * @return
	 * @throws IOException
	 */
	public String getProperty(File fileFilePath, String sKey) throws IOException{
		String sReturn = null;
		main:{
			check:{
				if(hmProperty==null) break main;				
			}//END Check
		
			//Get a property from hashmap (file read before) or load the file new into the hashmap and read the property. 
			Properties p = null;
			if(! hmProperty.containsKey(fileFilePath)){
				p = load(fileFilePath);					
			}else{
				p = (Properties) hmProperty.get(fileFilePath);			
			}
			if (p==null) break main;  //Sicherheitshalber
			sReturn = p.getProperty(sKey);
		}//END main:
		return sReturn;
	}
}
