package basic.zBasic.util.console.thread;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
public class ConsoleThreadZZZ<T> extends AbstractConsoleThreadZZZ<T> {
	private static final long serialVersionUID = 1222382952813216242L;

	/**Konstruktor ist private, wg. Singleton
	 */
	private ConsoleThreadZZZ() {		
		super();		
	}

	@SuppressWarnings("rawtypes")
	public static IConsoleZZZ getInstance() {
		{
			if(objConsole==null){
				objConsole = new ConsoleThreadZZZ();
			}
			return objConsole;		
		}
	}
}
