package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

/** Klasse zur Eingabe von Befehlen an der Konsole.
 *  Es wird dann in einer Schleife eine andere Klasse ausgeführt.
 *  
 *  Ausgelegt als Singleton.
 *  
 * 
 * @author Fritz Lindhauer, 16.10.2022, 08:01:04
 * 
 */
public class ConsoleControllerZZZ<T> extends AbstractConsoleControllerZZZ<T> {
	private static final long serialVersionUID = 1222382952813216242L;

	/**Konstruktor ist private, wg. Singleton
	 */
	private ConsoleControllerZZZ() throws ExceptionZZZ {		
		super();		
	}

	@SuppressWarnings("rawtypes")
	public static IConsoleControllerZZZ getInstance() throws ExceptionZZZ {
		{
			if(objConsole==null){
				objConsole = new ConsoleControllerZZZ();
			}
			return objConsole;		
		}
	}

	

	//########################
		/* (non-Javadoc)
		 * @see basic.zBasic.AbstractObjectWithStatusLocalZZZ#queryOfferStatusLocalCustom()
		 */
		@Override
		public boolean queryOfferStatusLocalCustom() throws ExceptionZZZ {
			// TODO Auto-generated method stub
			return false;
		}	
}
