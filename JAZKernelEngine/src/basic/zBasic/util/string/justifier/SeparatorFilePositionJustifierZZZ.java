package basic.zBasic.util.string.justifier;

import basic.zBasic.AbstractObjectWithExceptionZZZ;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.string.formater.IStringFormatManagerZZZ;
import basic.zBasic.util.string.formater.StringFormatManagerXmlZZZ;

/** Mache einen String buendig zu den anderen "Vorausgegangenen" String.
 *  
 *  Merke: Damit das auch sichtbar wird, ist eine Monospace Schriftart notwendig.
 *         Sowohl auf der Java-Konsole (z.B. in Eclipse)
 *         als auch in einer Textdatei für das Protokol.
 *         
 *         Verwende dazu die Schriftart:
 *         a) Unter Windows XP "Lucida Console" (meiner Meinung nach am besten lesbar)
 *                             , "Courier New", "Terminal"
 *                             , als ClearType "Consolas" 
 *         
 * 
 * @author Fritz Lindhauer, 22.12.2025, 09:32:31
 * 
 */
public class SeparatorFilePositionJustifierZZZ extends AbstractStringJustifierZZZ {
	private static final long serialVersionUID = 1931006668388552859L;
	
	// --- Singleton Instanz ---
	//muss als Singleton static sein. //Muss in der Konkreten Manager Klasse definiert sein, da ja unterschiedlich
	protected static IStringJustifierZZZ objStringJustifierINSTANCE;
	
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
    
	//als private deklariert, damit man es nicht so instanzieren kann, sonder die Methode .getInstance() verwenden muss
	protected SeparatorFilePositionJustifierZZZ() throws ExceptionZZZ{
		super();
	}

	public static IStringJustifierZZZ getInstance() throws ExceptionZZZ{
		if(objStringJustifierINSTANCE==null){
			
			//siehe: https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
			//Threadsafe sicherstellen, dass nur 1 Instanz geholt wird. Hier doppelter Check mit synchronized, was performanter sein soll als die ganze Methode synchronized zu machen.
			synchronized(SeparatorFilePositionJustifierZZZ.class) {
				if(objStringJustifierINSTANCE == null) {
					 // optional: Schutz vor Reflection
			        if (INITIALIZED) {
			            throw new ExceptionZZZ(new IllegalStateException("Singleton already initialized"));
			        }
					objStringJustifierINSTANCE = getNewInstance();
					INITIALIZED=true;
				}
			}
			
		}
		return (IStringJustifierZZZ) objStringJustifierINSTANCE;
	}
	
	public static IStringJustifierZZZ getNewInstance() throws ExceptionZZZ{
		//Damit wird garantiert einen neue, frische Instanz geholt.
		//Z.B. bei JUnit Tests ist das notwendig, denn in Folgetests wird mit .getInstance() doch tatsächlich mit dem Objekt des vorherigen Tests gearbeitet.
		objStringJustifierINSTANCE = new SeparatorFilePositionJustifierZZZ();
		return (IStringJustifierZZZ)objStringJustifierINSTANCE;
	}
	
	public static synchronized void destroyInstance() throws ExceptionZZZ{
		objStringJustifierINSTANCE = null;
	}
	
	//##########################################################
	//### METHODEN ##########
	
	
	//### Hilfsmethoden zum Buendig machen des Informationsteils im Log ueber meherer Zeilen ########################
	@Override
	public String getPositionSeparatorDefault() throws ExceptionZZZ {
		return IStringJustifierZZZ.sSEPARATOR_POSITION_DEFAULT;
	}
	
	@Override
	public String getPositionIdentifierDefault() throws ExceptionZZZ {
		return ReflectCodeZZZ.sPOSITION_IN_FILE_IDENTIFIER;
	}
	
	
	//### STATIC METHODEN
	
	
	
}
