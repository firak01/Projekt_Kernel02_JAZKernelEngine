package basic.zBasic.util.start;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.character.CharZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/** A class to implement UNIX-style (single-character) command arguments
 * @author Ian F. Darwin, ian@darwinsys.com
 * based on the standard UNIX getopt(3) program.
 * @version $Id$
 * 
 * 
 * FGL:
 * - Optionsparameter mit mehr als 1 Zeichen sind möglich
 * - Konstante definiert für "-". Damit kann ich nach COMMANDS suchen, die nicht im Pattern sind.
 *   Wird dann in GetOptZZZ benoetigt.
 *   
 * - Erweitert um Parametertypen
   | bedeutet - Parameter ohne Wert (Wert=Parametername)
   : bedeutet - Parameter muss einen Wert haben
   . bedeutet - Paramater kann einen Wert haben. Leer Parameter mit Leerstring definiert ""
 * 
 */
public class GetOpt {
	public final static String sCOMMAND_PREFIX="-"; //FGL 20260407 Damit werden Kommandos definiert. Muss public sein, damit GetOptZZZ dies lesen kann.
	
	/** The set of characters to look for */
	protected String pattern;
	/** Where we are in the options */
	protected int optind = 0;
	/** Public constant for "no more options"
	 * XXX should switch to hasNext()/next() pattern.
	 */
	public static final int DONE = 0;
	/** Internal flag - whether we are done all the options */
	protected boolean done = false;

	/** Retrieve the option index */
	public int getOptInd() {
		return optind;
	}

	/** The option argument, if there is one. */
	protected String optarg;

	/** Retrieve the current option argument */
	public String optarg() {
		return optarg;
	}

	/* Construct a GetOpt object, storing the set of option characters. */
	public GetOpt(String patt) {
		pattern = patt;
		rewind();
	}

	public void rewind() {
		done = false;
		optind = 0;
	}

	/** Return one argument.
	 */
	public char getopt(String argv[]) {
		if (optind == (argv.length)) {
			done = true;
		}

		// Do not combine with previous if statement.
		if (done) {
			return DONE;
		}

		// Pick off the next command line argument, check if it starts sCOMMAND_PREFIX.
		// If so look it up in the list.
		String thisArg = argv[optind++];
		if (thisArg.startsWith(sCOMMAND_PREFIX)) {
			optarg = null;
			for (int i=0; i<pattern.length(); i++) {
				char c = pattern.charAt(i);
				if (thisArg.equals(sCOMMAND_PREFIX+c)) {	// we found it
					// If it needs an option argument, get it.
					if (i+1 < pattern.length() && 
						pattern.charAt(i+1)==':' &&
						optind < argv.length)
						optarg = argv[optind++]; 
					return c;
				}
			}
			// Still no match, and not used all args, so must be error.
			return '?';
		} else {
			// Found non-argument non-option word in argv: end of options.
			optind--;
			done = true;
			return DONE;
		}
	}
	
	public String getoptString(String argv[]) throws ExceptionZZZ {
		if (optind == (argv.length)) {
			done = true;
		}

		// Do not combine with previous if statement.
		if (done) {
			return CharZZZ.toString(DONE);
		}

		// Pick off the next command line argument, check if it starts sCOMMAND_PREFIX.
		// If so look it up in the list.
		String thisArg = argv[optind++];
		if (thisArg.startsWith(sCOMMAND_PREFIX)) {
			optarg = null;
			
			if(thisArg.length()>=2 && thisArg.length() < 3) {
				for (int i=0; i<pattern.length(); i++) {
					char c = pattern.charAt(i);
					if (thisArg.equals(sCOMMAND_PREFIX+c)) {	// we found it
						// If it needs an option argument, get it.
						if (i+1 < pattern.length() && 
							pattern.charAt(i+1)==':' &&
							optind < argv.length)
							optarg = argv[optind++]; 
						return CharZZZ.toString(c);
					}
				}
				// Still no match, and not used all args, so must be error.
				return CharZZZ.toString('?');
			}else if(thisArg.length()>=3) {
				String sThisArgWithoutMinus = thisArg.substring(1,thisArg.length());
				int iPositionBehind = StringZZZ.indexOfFirstBehind(pattern, sThisArgWithoutMinus);
				char cBehind = pattern.charAt(iPositionBehind); 
				boolean bIsColon = cBehind==':'; boolean bIsPoint = cBehind=='.';
				//if (iPositionBehind+1 < pattern.length() && bIsColon && optind < argv.length) {
				//also die Abfrage der Pattern Länge ist Blödsinn
				if (bIsColon && optind < argv.length) {
					optarg = argv[optind++]; //das ist also der errechnete Wert für eine -Kommandoanweisung 
				}
				if (bIsPoint && optind < argv.length) {
					optarg = argv[optind++]; //das ist also der errechnete Wert für eine -Kommandoanweisung (leerer Parameter erlaubt) "" bei Übergabe.
				}
				//return new String(thisArg);
				return thisArg.substring(1,thisArg.length()); //Rueckgabewerte wie bei dem 1 Zeichen Argument ohne sCOMMAND_PREFIX
			} else {
				// Found non-argument non-option word in argv: end of options.
				optind--;
				done = true;
				return CharZZZ.toString(DONE);
			}
		} else {
			// Found non-argument non-option word in argv: end of options.
			optind--;
			done = true;
			return CharZZZ.toString(DONE);
		}
	}
}

