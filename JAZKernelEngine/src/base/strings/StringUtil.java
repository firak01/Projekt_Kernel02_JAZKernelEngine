package base.strings;
//package de.his.core.base.strings;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.util.CollectionUtils;

import com.google.common.io.CharStreams;

//import de.his.core.base.collections.CollectionUtil;
import base.collections.CollectionUtil;

//import de.his.core.base.md5.MDFiveHash;
import base.md5.MDFiveHash;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

/**
 * Utility class for common string operations.

 * Company: HIS
 * @author McLaren
 * @version $Revision: 1.92.2.2 $
 */
final public class StringUtil {
    public static Logger logger = Logger.getLogger(StringUtil.class);


    /**
     * Suffix für change_prop-Attribute.
     */
    public static final String ATTR_SUFFIX = "_attr";

    /** Default charset used by HOSU and HISinOne */
    public static final String defaultCharset = "UTF8";

    /** Pseudo index of no matching item or substring was found */
    public static final int INDEX_NOT_FOUND = -1;

    /** Sucht nach Kommata, die nicht in einfache Anführungszeichen eingebettet sind. */
    private static final String quotedValueListPatternString = "\\s*('[^']*')\\s*|([^,]+)";

    /*    RegEx:    	
    Zwei kleine Tipps für deine Methode
    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
     */
    //alt bleibt erhalten:
    private static final Pattern quotedValueListPattern = Pattern.compile(quotedValueListPatternString);
    //neu: 
    //private static final Pattern quotedValueListPattern = Pattern.compile(Pattern.quote(quotedValueListPatternString));


    /**
     * Formatiert einen Text, indem dieser LINKSBÜNDIG in die Maske geschrieben wird.
     * Ist der Eingabetext länger als die Maske, wird er unverändert zurückgegeben.
     * Es gehen also keine Informationen verloren, aber dafür wird durch die Länge der
     * Maske nur die minimale Länge des Rückgabetextes definiert.<br><br>
     *
     * Beispiele:<br>formatLeft("abc", "123456") -> "abc456"<br>
     *               formatLeft("abcdef", "123") -> "abcdef"<br>
     *
     * @param text Der zu formatierende Eingabetext
     * @param mask Die Formatierungsmaske
     * @return Linksbündig formatierter Text.
     */

    public static String alignLeft(String text, String mask) {
        String ret = text != null ? text : "";
        if (mask != null) {
            final int textLen = ret.length();
            final int maskLen = mask.length();
            if (textLen < maskLen) {
                // Rest aus Maske auffüllen:
                ret += mask.substring(textLen, maskLen);
            }
        }
        return ret;
    }

    /**
     * Schneidet Text zuerst auf maximal Maskenlänge und formatiert den
     * Rest LINKSBÜNDIG in der Maske.
     *
     * @param text Zu behandelnder Text
     * @param mask Maske
     * @return Ergebnis des linksbuendigen Einfuegens vom Text in die Maske
     */
    public static String alignLeftAndTruncate(String text, String mask) {
        final String myText = text == null || mask == null ? "" : substring(text, mask.length());
        return alignLeft(myText, mask);
    }

    /**
     * Formatiert einen Text, indem dieser RECHTSBÜNDIG in die Maske geschrieben wird.
     * Ist der Eingabetext länger als die Maske, wird er unverändert zurückgegeben.
     * Es gehen also keine Informationen verloren, aber dafür wird durch die Länge der
     * Maske nur die minimale Länge des Rückgabetextes definiert.<br><br>
     *
     * Beispiele:<br>formatRight("abc", "123456") -> "123abc"<br>
     *               formatRight("abcdef", "123") -> "abcdef"<br>
     *
     * @param text Der zu formatierende Eingabetext
     * @param mask Die Formatierungsmaske
     * @return Rechtsbündig formatierter Text.
     */
    public static String alignRight(String text, String mask) {
        final int charsToFill = mask.length() - text.length();
        if (charsToFill <= 0) {
            return text;
        }
        final String clippedMask = mask.substring(0, charsToFill);
        return clippedMask + text;
    }

    @Deprecated
    public static String argsubst(String arg, Map<?, ?> prop, boolean keep) {
        return argsubst(arg, null, prop, keep);
    }

    @Deprecated
    public static String argsubst(String arg, String prefix, Map<?, ?> prop, boolean keep) {
        if (prop == null) {
            return arg;
        }
        if (arg == null) {
            return null;
        }
        return substituteVars(arg, prefix, prop, keep);
    }

    // TODO testcase
    public static String charsAfterOrNull(String string, char sep) {
        final int pos = string.indexOf(sep);
        if (pos >= 0) {
            return string.substring(pos + 1);
        }
        return null;
    }

    public static String charsBeforeLastOccurrence(String string, String cutHere) {
        int lastPos = -1;
        int position = -1;
        do {
            position = containsWord(string.toLowerCase(), cutHere, position + 1);
            if (position > -1) {
                lastPos = position;
            }
        } while (position > -1);

        if (lastPos >= 0) {
            return string.substring(0, lastPos);
        }
        return string;
    }

    // TODO testcase
    public static String charsBeforeOrAll(String string, char sep) {
        final int pos = string.indexOf(sep);
        if (pos >= 0) {
            return string.substring(0, pos);
        }
        return string;
    }

    public static String charsBeforeSpace(String name) {
        final String trimmedName = name.trim();
        final int ix = trimmedName.indexOf(" ");
        return ix <= 0 ? trimmedName : trimmedName.substring(0, ix);
    }

    /**
     * Liefert den String innerhalb der ersten runden Klammern, sonst null
     *
     * @param str String, evtl mit runden Klammern
     * @return Wert in runden Klammern (erstes Vorkommen).
     */
    public static String charsInfirstPairOfParens(String str) {
        final char myOpeningBracket = '(';
        final char closingBracket = ')';
        final int openPos = str.indexOf(myOpeningBracket);
        if (openPos < 0 || str.length() <= openPos + 1) {
            return null;
        }
        final String myStr = str.substring(openPos + 1);
        final List<String> bracketContents = splitRespectingQuotesAndParens(myStr, closingBracket + "", true);
        if (bracketContents.size() == 1) {
            return bracketContents.get(0);
        }
        final int closePos = myStr.indexOf(closingBracket);
        if (closePos < 0) {
            return null;
        }
        return myStr.substring(0, closePos);
    }



    /**
     * Returns true if this string contains any of the specified characters.
     * For example: stringContainsAny("http://www.google.com" , ":", ",", "/", "(", ")", "?") returns true.
     *
     * @param inputString The string to be examined
     * @param searchStrings The characters to search for
     * @return True if the string contains any characters
     */
    public static boolean containsAny(String inputString, String... searchStrings) {
        for (final String string : searchStrings) {
            if (inputString.contains(string)) {
                return true;
            }
        }
        return false;
    }



    public static boolean containsAsSubstring(Collection<String> haystacks, String needle) {
        if (StringUtils.isEmpty(needle) || CollectionUtils.isEmpty(haystacks)) {
            return false;
        }
        for (final String curString : haystacks) {
            if (StringUtils.contains(curString, needle)) {
                return true;
            }
        }
        return false;
    }


    public static boolean containsAt(String text, final String substring, int position) {
        return position + substring.length() <= text.length() && text.substring(position, position + substring.length()).equals(substring);
    }

    public static boolean containsIgnoreCase(String toFind, final String[] strings) {
        for (final String besch : strings) {
            if (besch.equalsIgnoreCase(toFind)) {
                return true;
            }
        }
        return false;
    }

    /**
     * &Uuml;berpr&uuml;ft, ob in dem in dem &uuml;bergebenen String das
     * &uuml;bergebene <code>word</code> als komplettes Wort enthalten ist.
     * Dabei werden Worte von Leerzeichen, dem Stringanfang, dem Stringende oder
     * Sonderzeichen getrennt.
     *
     * @param str String, in dem gesucht wird
     * @param word Wort, nach dem gesucht werden soll
     * @return position, Position des Wortes (-1, wenn das Wort nicht enthalten
     *         ist).
     */
    public static int containsWord(String str, String word) {
        return containsWord(str, word, 0);
    }

    /**
     * &Uuml;berpr&uuml;ft, ob in dem in dem &uuml;bergebenen String das
     * &uuml;bergebene <code>word</code> als komplettes Wort enthalten ist.
     * Dabei werden Worte von Leerzeichen, dem Stringanfang, dem Stringende oder
     * Sonderzeichen getrennt.
     *
     * @param theString String, in dem gesucht wird
     * @param word Wort, nach dem gesucht werden soll
     * @param start Startposition der Suche
     * @return position, Position des Wortes (-1, wenn das Wort nicht enthalten
     *         ist).
     */
    public static int containsWord(String theString, String word, int start) {
        if (theString == null || word == null) {
            return -1;
        }
        final String str = theString.substring(start);
        int i = str.indexOf(word);
        int j = 0;
        while (i > -1) {
            // String gefunden. Muss überprüfen, ob dieser ein eigenständiges
            // Wort ist
            // oder nur Teil eines längeren Wortes...
            // 1. Prüfe Zeichen vor dem gefundenen String:
            if (i <= 0 || !isWordChar(str.charAt(i - 1))) {
                // Zeichen vor dem gefundenen String war ein Separator...
                // 2. Jetzt checke erstes Zeichen hinter dem String:
                j = i + word.length();
                if (j >= str.length() || !isWordChar(str.charAt(j))) {
                    // Zeichen hinter dem gefundenen String war ebenfalls ein
                    // Separator...
                    // Gefundener String ist ein eigenständiges Wort, nicht
                    // bloss Teil
                    // eines längeren Wortes. Gib Startposition zurück:
                    return i + start;
                }
            }

            // Gefundener String war bloss Teil eines längeren Wortes. Suche
            // weiter:
            j = i + 1;
            if (j < str.length() - 1) {
                i = str.indexOf(word, j);
            } else {
                i = -1;
            }
        }

        return -1;
    }

    /**
     * Decode a String base 64.
     *
     * @param str
     * @return String
     */
    public static String decodeBase64(final String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        return org.apache.commons.codec.binary.StringUtils.newStringUtf8(Base64.decodeBase64(str));
    }

    public static String formatMoneyToDatabase(String input) {
        final StringBuilder value = new StringBuilder(input.length());
        boolean kommaGefunden = false;

        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (c == '.') {
                // ok
            } else if (c == ',') {
                if (!kommaGefunden) {
                    kommaGefunden = true;
                    value.append('.');
                }
            } else {
                value.append(c);
            }
        }

        return value.toString();
    }

    public static String formatMoneyToDisplay(double input) {
        return String.format(Locale.GERMANY, "%,.2f", Double.valueOf(input));
    }

    /**
     * @param input
     * @return money
     */
    public static String formatMoneyToDisplay(String input) {
        String retval = input;

        try {
            final double v = Double.parseDouble(input);
            retval = formatMoneyToDisplay(v);
        } catch (final Exception e) {
            logger.error(e);
        }

        return retval;
    }

    /**
     * Erzeugt einen zuf&auml;lligen String.
     *
     * @return String
     */
    public static String generateRandomString() {
        final int laenge = 20;
        final String chars = "0123456789$.ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return generateRandomString(laenge, chars);
    }

    /**
     * Erzeugt einen zufälligen String von angegebener Länge,.
     * @param laenge
     * @return String
     */
    public static String generateRandomStringLaenge(int laenge) {
        final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        return generateRandomString(laenge, chars);
    }

    /**
     * Erzeugen eines HashSet aus einem sep-separierten Strings.
     *
     * @param s   String mit oder ohne Separatoren
     * @param sep Separator
     * @return    HashSet aller Substrings
     */
    public static HashSet<String> getHashSetFromString(String s, String sep) {
        if (s == null) {
            return null;
        }
      //FGL: Erst ab Java 1.7 final HashSet<String> res = new HashSet<>();
        final HashSet<String> res = new HashSet<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        final StringTokenizer st = new StringTokenizer(s, sep);
        while (st.hasMoreTokens()) {
            res.add(st.nextToken());
        }
        return res;
    }

    /**
     * Sucht das letzte Zeichen vor (!) Position after, das kein Whitespace ist.
     *
     * @param str Der zu betrachtende String
     * @param before Position, ab der rueckwaerts gesucht werden soll
     * @return Position des gefundenen Zeichens, oder -1 wenn keins gefunden wurde.
     */
    public static int getLastNonWhitespaceCharPos(CharSequence str, int before) {
        int i = before - 1;
        while (i >= 0 && Character.isWhitespace(str.charAt(i))) {
            i--;
        }
        return i;
    }

    /**
     * Sucht das nächste Zeichen nach (!) Position before, das kein Whitespace ist.
     *
     * @param str Der zu betrachtende String
     * @param after Position, nach der gesucht werden soll
     * @return Position des gefundenen Zeichens, oder -1 wenn keins gefunden wurde.
     */
    public static int getNextNonWhitespaceCharPos(CharSequence str, int after) {
        final int strlen = str.length();
        for (int i = after + 1; i < strlen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return i;
            }
        }
        // Kein Zeichen gefunden
        return -1;
    }

    /**
     * Sucht Werte in [...] in einem String
     *
     * @param arg Der zu behandelnde String.
     * @return StringArray mit Werten in [...]
     */
    public static String[] getParsFromString(String arg) {
    	//FGL: Erst ab Java 1.7  final Vector<String> v = new Vector<>();
        final Vector<String> v = new Vector<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        if (arg == null) {
            return null;
        }
        String work = arg;
        while (work.length() > 0) {
            int i = work.indexOf("[");

            if (i > -1 && i < work.length()) {
                i++;
                work = work.substring(i);
                int j = work.indexOf("]");

                if (j < 0) {
                    logger.warn("argsubst: ]" + " fehlt in " + arg);
                    break;
                }

                final String par = work.substring(0, j);
                if (par != null && par.length() > 0) {
                    final int ix = par.indexOf(",");
                    if (ix < 0) {
                        v.addElement(par);
                    }
                }
                j++;
                work = work.substring(j);
            } else {
                work = work.substring(work.length());
            }
        }
        final String[] pars = new String[v.size()];
        for (int i = 0; i < v.size(); i++) {
            pars[i] = v.elementAt(i);
        }
        return pars;
    }

    /**
     * Methode zur Lieferung des internen Array-Wertes mit einem Vergleichsstring.
     * @param arrList Arrayliste
     * @param strCheckValue Vergleichswert
     * @return Index im Array
     */
    public static int getPositionOfTrimmedString(String[] arrList, String strCheckValue) {
        if (strCheckValue == null) {
            return -1;
        }
        for (int i = 0; i < arrList.length; i++) {
            if (arrList[i] != null && arrList[i].trim().equals(strCheckValue.trim())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Liefert den MD5-Hash eines Strings zurück.
     *
     * @param str the str
     * @return String
     */
    public static String hashOrNull(String str) {
        return str == null ? null : MDFiveHash.hash(str);
    }

    /** Sieht nach, ob etwas in einem zweidimensionalen Array enthalten ist, in der Regel von DB-Anfrage.
     * @param dbAntwort Array, in dem gesucht wird
     * @return Array hat mindestens ein Wert (der auch noch null sein kann).
     */
    public static boolean hasRows(String[][] dbAntwort) {
        return ArrayUtils.isNotEmpty(dbAntwort) && ArrayUtils.isNotEmpty(dbAntwort[0]);
    }


    /**
     * Sucht das erste Auftreten eines Zeichens aus einer Zeichenmenge in einem String,
     * beginnend beim Stringanfang.
     *
     * @param str Der zu durchsuchende String.
     * @param charset Die Menge der zu suchenden Zeichen.
     * @return Der Index des ersten Auftretens eines der Zeichen aus der Zeichenmenge
     * im String, oder -1, wenn kein solches Zeichen gefunden wurde.
     */
    public static int indexOfCharSetElem(CharSequence str, String charset) {
        if (str != null) {
            for (int pos = 0; pos < str.length(); pos++) {
                if (charset.indexOf(str.charAt(pos)) >= 0) {
                    return pos;
                }
            }
        }
        return -1;

    }

    public static int indexOfTrimmableChar(CharSequence str) {
        if (str != null) {
            int pos = 0;
            while (pos < str.length()) {
                if (str.charAt(pos) <= ' ') {
                    return pos;
                }
                pos++;
            }
        }
        return -1;
    }

    /**
     * Liefert erstes Vorkommen eines Whitespace-Zeichens in gegebenem String,
     * oder -1, wenn dieser kein solches Zeichen enthält.
     *
     * @param str Zu betrachtender String
     * @return Position des ersten Whitespace-Zeichens, oder -1 wenn keins enthalten
     */
    public static int indexOfWhitespace(CharSequence str) {
        return indexOfWhitespace(str, 0);
    }



    /**
     * Liefert erstes Vorkommen eines Whitespace-Zeichens ab der Start-Position im gegebenen String,
     * oder -1, wenn dieser ab der Start-Position kein solches Zeichen mehr enthält.
     *
     * @param str Zu betrachtender String
     * @param startPos Position ab der zu suchen ist
     * @return Position des ersten Whitespace-Zeichens nach der <code>startPos</code>, oder -1 wenn keins mehr enthalten
     */
    public static int indexOfWhitespace(CharSequence str, int startPos) {
        if (str != null) {
            int pos = startPos;
            while (pos < str.length()) {
                if (Character.isWhitespace(str.charAt(pos))) {
                    return pos;
                }
                pos++;
            }
        }
        return -1;
    }

    /**
     * Prüft, ob vor und nach dem durch Position und Länge angegebenen Substring
     * etwas anderes als ein "word char" ist
     *.
     */
    public static boolean isSurroundedByNonWordcharacters(String aString, int wordPosition, int wordLength) {
        if (aString == null || wordPosition < 0 || wordPosition >= aString.length()) {
            return false;
        }
        if (wordPosition > 0 && isWordChar(aString.charAt(wordPosition - 1))) {
            // not preceded by non-word char
            return false;
        }
        final int positionAfterWord = wordPosition + wordLength;
        // followed by non-word char?
        return positionAfterWord >= aString.length() || !isWordChar(aString.charAt(positionAfterWord));
    }

    /** @return true für alle Zeichen, die innerhalb eines Worts auftreten duerfen.
     * Alle anderen sind Word-Separatoren!. */
    public static boolean isWordChar(char charValue) {
        final String WORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvwxyz" + "ÄÖÜäöüß" + "_0123456789";
        return WORD_CHARS.indexOf(charValue) >= 0;
    }


    /**
     * Wandelt Array in String um und maskiert dabei das Trennzeichen innerhalb
     * des Texts
     *
     * @param array Der umzuwandelnde Array.
     * @param sep Separator.
     * @return sep-separierte String-Liste
     */
    public static String joinAndEscapeSeparators(String[] array, String sep) {
        if (array == null) {
            return "";
        }
        final String trimmedSep = sep.trim();
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (final String element : array) {
            if (first) {
                first = false;
            } else {
                sb.append(sep);
            }
            final String item = StringUtils.replace(element, trimmedSep, "\\" + trimmedSep);
            sb.append(item);
        }
        return sb.toString();
    }

    /**
     * Konvertiert eine Liste in einen String von durch Komma getrennten Elementen.
     * @param list Liste als List-Objekt
     * @return String
     */
    public static String joinWithComma(List<?> list) {
        return StringUtils.join(list, ", ");
    }

    public static String joinWithComma(String[] strings) {
    	//FGL: Erst ab Java 8:   return String.join(", ", strings);      
    	//FGL Ersetzt durch einen ZZZ Klasse
    	String sReturn = new String("");
	    main:{
	    	try {	       	
	       	sReturn = StringZZZ.join(strings, ", ", "BEFORE");
			} catch (ExceptionZZZ e) {				
				e.printStackTrace();
			}
	    }//End main
    	return sReturn;
    }

    public static String joinWithQuotationMarks(Collection<String> strings, String quotationMark, String separator) {
        return StringUtils.join(surroundAll(strings, quotationMark), separator);
    }

    /**
     * Erzeugen eines Komma-separierten Strings aus einem Set zwecks Verwendung in SQL.
     * Aufruf in HModul (WWrite).<br/>
     * Achtung diese Funktion macht mehr als:
     * <pre>
     *    String setString = lastSet.toString();
     *    String res = setString.substring(1, setString.length() - 1));
     * </pre> <br/>
     *
     * @param hs  Gewünschte Länge des Strings
     * @param quotationMark Vor und nach jedem Element einfügen. z. B. ein Hochkomma '
     * @return    Komma-separierter String aus Elementen des HashSet
     */
    public static String joinWithQuotationMarks(Set<?> hs, char quotationMark) {
        if (hs == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final Iterator<?> it = hs.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(quotationMark);
            sb.append(it.next());
            sb.append(quotationMark);
            i++;
        }
        return sb.toString();
    }

    /**
     * Vergleicht zwei kommaseparierte Listen daraufhin,
     * ob sie gemeinsame Elemente haben.
     * Die gemeinsamen Elemente werden als kommaseparierte Liste
     * zurückgegeben, ansonsten null.
     *
     * @param list1 Liste 1
     * @param list2 Liste 2
     * @return Liste der gemeinsamen Elemente.
     */
    public static boolean matchLists(String list1, String list2) {
        final String[] list1Arr = splitToArrayRespectingQuotesAndParens(list1);
        final String[] list2Arr = splitToArrayRespectingQuotesAndParens(list2);
        for (final String element : list1Arr) {
            for (final String element2 : list2Arr) {
                if (element.equals(element2)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns a given string if it is not empty otherwise returning a default value.
     * @param value         the value; can be {@code null}
     * @param defaultValue  the default value to be used if value is empty; can be {@code null}
     * @return  {@code value} if non empty; otherwise {@code defaultValue}
     */
    public static String nonemptyOrDefault(String value, String defaultValue) {
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }


    public static String removePrefixIfpresent(final String prefix, String aString) {
        return aString.startsWith(prefix) ? aString.substring(prefix.length()) : aString;
    }

    public static String removeSuffixIfpresent(String aString, final String suffix) {
        return aString.endsWith(suffix) ? aString.substring(0, aString.length() - suffix.length()) : aString;
    }

    public static String replace(String text, String token, String newval) {
        StringBuffer buf = new StringBuffer(text);
        StringBufferUtil.replace(buf, token, newval);
        return buf.toString();
    }

    /**
     * Replaces for the given string all line separators with the unix line separator.
     *
     * <pre>
     * replaceOSDependentLineSeparatorWithUnixLineSeparator("foo"\r\n"bar") = "foo\nbar"
     * replaceOSDependentLineSeparatorWithUnixLineSeparator("foo\rbar")     = "foo\nbar"
     * replaceOSDependentLineSeparatorWithUnixLineSeparator("foo\nbar")     = "foo\nbar"
     * replaceOSDependentLineSeparatorWithUnixLineSeparator(null)           = null
     * replaceOSDependentLineSeparatorWithUnixLineSeparator("")             = ""
     * replaceOSDependentLineSeparatorWithUnixLineSeparator(" ")            = " "
     * </pre>
     *
     * @param value the string which contains the line separators to replace.
     *
     * @return the given string with unix line separators.
     */
    public static String replaceOSDependentLineSeparatorsWithUnixLineSeparator(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        final List<String> lines = splitLinesOSDependently(value);
        return StringUtils.join(lines, "\n");
    }

    /**
     * Ersetzt oder entfernt im uebergebenen String alle Sonderzeichen
     *
     * @param aString          Der zu bereinigende String
     * @param allowed      Weitere Zeichen (außer Buchstaben und Zahlen) die erlaubt sind
     * @param forbidden   Zeichen die zusaetzlich herausgefiltert werden sollen
     * @param replacement        Zeichenkette, die anstelle des Sonderzeichens eingefuegt wird
     * @return bereinigter String
     */

    public static String replaceSpecialChars(String aString, Collection<Character> allowed, Collection<Character> forbidden, String replacement) {
        final StringBuilder sb = new StringBuilder();
        for (final char c : aString.toCharArray()) {
            final boolean isSpecialChar = forbidden.contains(Character.valueOf(c)) || !allowed.contains(Character.valueOf(c)) && !Character.isLetterOrDigit(c);
            if (isSpecialChar) {
                sb.append(replacement);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Ersetzt alle Whitespaces im gegebenen String durch das gegebene Zeichen.
     *
     * @param str Der zu bearbeitende String.
     * @param repChar Das Zeichen, durch das whitspaces ersetzt werden sollen.
     * @return String, in dem whitspaces ersetzt wurden.
     */
    public static String replaceWhitespaces(String str, char repChar) {
        if (str == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            final char c = str.charAt(i);
            sb.append(Character.isWhitespace(c) ? repChar : c);
        }
        return sb.toString();
    }

    /**
     * Reduziert eine Zeichenkette mit Whitespaces auf das Minimum.
     * Jedes Whitespace wird durch ein Leerzeichen (" ") ersetzt. Mehrere
     * hintereinander auftretende Whitespaces werden durch ein Leerzeichen
     * ersetzt. Auch führende bzw. endende Whitespaces werden durch ein
     * Leerzeichen ersetzt.
     * Bem.: Es werden auch Teilstrings übergeben, deshalb dürfen führende
     * und endende Leerzeichen nicht entfernt werden.
     *
     * @param str der zu bearbeitende String
     * @return String, in dem Whitespaces ersetzt wurden
     */
    public static String replaceWhiteSpacesWithSingleSpace(String str) {
        if (str == null) {
            return null;
        }
        boolean previousIsWhite = false;
        final StringBuilder copy = new StringBuilder();
        final int len = str.length();
        for (int i = 0; i < len; i++) {
            final char c = str.charAt(i);
            final boolean currentIsWhite = Character.isWhitespace(c);
            if (!currentIsWhite) {
                copy.append(c);
            } else if (!previousIsWhite) {
                copy.append(' ');
            }
            previousIsWhite = currentIsWhite;
        }
        return copy.toString();
    }

    /**
     * Replaces a full word "needle" in a string "haystack" with the replacement "replacement".
     *
     * A word matches if "needle" is separated in the string "haystack" with a whitespace character (one of [ \t\n\x0B\f\r]),
     * with string boundaries (start or end of "haystack") or any punctation (one of !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~)
     * Anyways, needle can contain any of these characters.
     *
     * <pre>
     * replaceWord("today is a good day","day","night") = "today is a good night"
     * replaceWord("today is a good day","a","not a") = "today is not a good day"
     * replaceWord("today is a good day","today is","yesterday was") = "yesterday was a good day"
     * replaceWord("FROM Person p WHERE p.surname LIKE :param1", ":param1", "'A%'") = "FROM Person p WHERE p.surname LIKE 'A%'"
     * </pre>
     *
     * See class StringUtilTest for more working examples.
     *
     * @param haystack
     * @param needle
     * @param replacement
     * @return String
     *
     */
    public static String replaceWord(final String haystack, final String needle, String replacement) {
        if (haystack == null) {
            return null;
        }
        if (StringUtils.isEmpty(needle)) {
            return haystack;
        }
        // am Anfang, in der Mitte oder am Ende getrennt durch whitespace-chars oder Satz-Zeichen oder Symbole vom restlichen Text
        return haystack.replaceAll("(^|\\s|\\p{Punct})(" + Pattern.quote(needle) + ")(\\s|\\p{Punct}|$)",
                        "$1" + Matcher.quoteReplacement(StringUtils.defaultString(replacement, "")) + "$3");
    }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     * @param s1
     * @param s2
     * @return similarity
     */
    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2;
            shorter = s1;
        }
        final int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0; /* both strings are zero length */
        }
        /* // If you have StringUtils, you can use it to calculate the edit distance:
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) /
                                   (double) longerLength; */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    /**
     * Zerlegt einen String in die durch Whitespace-Zeichen getrennten Wörter.
     *
     * @param str Der zu zerlegende String.
     * @return Wörter ohne Whitespaces oder null, wenn der Input-String null war.
     */
    public static List<String> splitByWhitespaces(String str) {
        String myStr = str;
        if (myStr == null) {
            return null;
        }
      //FGL: Erst ab Java 1.7  final List<String> tokens = new ArrayList<>();
        final List<String> tokens = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        myStr = myStr.trim();
        while (myStr.length() > 0) {
            // Muss noch nicht-Whitespace-Zeichen geben...
            final int whitespaceIdx = indexOfTrimmableChar(myStr);
            if (whitespaceIdx >= 0) {
                // Nehme Wort bis zum gefundenen Leerzeichen
                final String token = myStr.substring(0, whitespaceIdx);
                tokens.add(token);
                myStr = myStr.substring(whitespaceIdx).trim();
            } else {
                // Rest ist das letzte Wort
                tokens.add(myStr);
                myStr = "";
            }
        }
        return tokens;
    }


    /**
     * Wandelt einen String in eine ArrayList um. Notwendig ist ein Separator.
     * Leere (null) Strings werden nicht mit in die Liste aufgenommen.
     *
     * @param str the str
     * @param sep the sep
     * @return List<String>
     */
    public static List<String> splitIgnoringEmpties(String str, String sep) {
        final String[] x = splitToArray(str, sep);
        return nonemptiesToArrayList(x);
    }

    /**
     * Wandelt String in Array um
     *
     * @param str Der umzuwandelnde String.
     * @return Array of String
     */
    public static List<String> splitRespectingQuotesAndParens(String str) {
        return splitRespectingQuotesAndParens(str, null, false);
    }

    /**
     * Parses a string and returns a list of items, each of which is a part of the original string until you find the next ','.
     * A ',' in round brackets or in quotes with '\'' is ignored
     *
     * @param str
     * @param limitingStr stop parsing when you find this expression in str (may be null)
     * @param oneItem if true return one long item instead of several items which are separated by ','
     * @return selectItems
     */
    public static List<String> splitRespectingQuotesAndParens(String str, String limitingStr, boolean oneItem) {
    	//FGL: Erst ab Java 1.7 final List<String> items = new ArrayList<>();
        final List<String> items = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        StringBuffer item = new StringBuffer();
        if (str != null) {
            if (str.trim().isEmpty()) {
                items.add(str);
            } else {
                int i = 0;
                while (i < str.length()) {
                    if (i < str.length() && limitingStr != null && str.substring(i).toLowerCase().startsWith(limitingStr)) {
                        if (!oneItem) {
                            addTrimmedAndPrinted(items, item);
                            item = new StringBuffer();
                        }
                        break;
                    }
                    if (str.charAt(i) == '\'') {
                        // Store constants regardless of content
                        item.append(str.charAt(i++));
                        while (i < str.length() && str.charAt(i) != '\'') {
                            item.append(str.charAt(i++));
                        }
                        if (i < str.length() && str.charAt(i) == '\'') {
                            item.append(str.charAt(i++));
                        }
                    } else if (str.charAt(i) == '(') {
                        i = storeBracketContent(str, i, item);
                        if (!oneItem && i >= str.length()) {
                            addTrimmedAndPrinted(items, item);
                            item = new StringBuffer();
                        }
                    } else {
                        while (i < str.length() && str.charAt(i) != ',' && str.charAt(i) != '(' && str.charAt(i) != '\'') {
                            if (i < str.length() && limitingStr != null && str.substring(i).toLowerCase().startsWith(limitingStr)) {
                                break;
                            }
                            item.append(str.charAt(i++));
                        }
                        if (i < str.length() && str.charAt(i) == '(') {
                            continue;
                        }
                        if (i < str.length() && str.charAt(i) == ',') {
                            if (!oneItem) {
                                addTrimmedAndPrinted(items, item);
                                item = new StringBuffer();
                            } else {
                                item.append(str.charAt(i));
                            }
                            i++;
                        } else if (!oneItem && i >= str.length()) {
                            addTrimmedAndPrinted(items, item);
                            item = new StringBuffer();
                        }
                    }
                }
                if (item.toString().trim().length() > 0 && !oneItem) {
                    addTrimmedAndPrinted(items, item);
                }
            }
        }
        if (oneItem) {
            addTrimmedAndPrinted(items, item);
        }
        return items;
    }

    /**
     * Wandelt String in Array um
     *
     * @param str Der umzuwandelnde String.
     * @return Array of String
     */
    public static String[] splitToArray(String str) {
        return str == null ? new String[0] : str.split("\\s*[,;\003]\\s*");
    }

    /**
     * Splittet einen String anhand eines Separator-Zeichens.
     *
     * Wenn null als Eingabestring übergeben wird, dann wird ein leeres
     * Array der länge 0 geliefert.
     *
     * @param str Der umzuwandelnde String
     * @param separators the set of separator chars
     * @return array of strings, never null
     */
    public static String[] splitToArray(String str, String separators) {
        final List<String> v = splitToList(str, separators);
        return toArray(v);
    }

    /**
     * Splittet einen String anhand eines Separator-Zeichens.
     *
     * @param str Der umzuwandelnde String.
     * @param separators the set of separator chars
     * @param removeBackslashes
     * @return array of strings, never null
     */
    public static String[] splitToArray(String str, String separators, boolean removeBackslashes) {
        final List<String> v = splitToVector(str, separators, removeBackslashes);
        return toArray(v);
    }

    /**
     * Wandelt String in Array um
     *
     * @param str Der umzuwandelnde String.
     * @return Array of String
     */
    public static String[] splitToArrayRespectingQuotesAndParens(String str) {
        final List<String> items = splitRespectingQuotesAndParens(str);
        return toArray(items);
    }


    /**
     * @param listString
     * @return Array of StringList-Elements separated by comma.
     *  commas included in single qotes are ignored
     */
    public static String[] splitToArrayWithQuotableCommas(String listString) {
    	//FGL: Erst ab Java 1.7 final List<String> results = new LinkedList<>();
        final List<String> results = new LinkedList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type

        final Matcher m = quotedValueListPattern.matcher(listString);
        while (m.find()) {
            final String group1 = m.group(1);
            final String h = group1 != null ? group1 : m.group(2);
            results.add(h.trim());
        }

        return results.toArray(new String[0]);
    }

    /**
     * Wandelt String in Vector um
     *
     * @param str Der umzuwandelnde String.
     * @return Vector
     */

    public static List<String> splitToList(String str) {
        return splitToList(str, null);
    }

    public static List<String> splitToList(String str, String sep) {
        return splitToVector(str, sep, false);
    }

    /**
     * Wandelt eine als String gegebene Liste von Werten in einen HashSet um.
     * @param str Der String mit der Liste von Werten
     * @param separatorChars Separator
     * @return HashSet mit den Werten aus dem String
     */
    public static Set<String> splitToSet(String str, String separatorChars) {
        String[] splitted = StringUtils.split(str, separatorChars);
        return CollectionUtil.toSet(splitted);
    }

    /**
     * Wandelt String in Vector um.
     *
     * @param str Der umzuwandelnde String.
     * @param separator String mit Separator-Zeichen.
     * @param removeBackslashes Sollen Backslashes entfernt werden?
     * @return Vector
     */
    public static Vector<String> splitToVector(String str, String separator, boolean removeBackslashes) {
        final String _sep = "\003";
      //FGL: Erst ab Java 1.7 final Vector<String> v = new Vector<>();
        final Vector<String> v = new Vector<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        if (str == null) {
            return v;
        }
        if (str.trim().length() == 0) {
            v.addElement("");
            return v;
        }
        String delimiters = ",;" + _sep;
        if (separator != null) {
            delimiters = separator;
        }
        int i = 0;
        StringBuilder strbuf = new StringBuilder();
        String current = str.substring(i, i + 1);
        while (i < str.length() - 1) {
            if (current.equals("\\")) {
                if (!removeBackslashes) {
                    appendWithoutLeadingEmpties(strbuf, current);
                }
                i++;
                if (i < str.length() - 1) {
                    current = str.substring(i, i + 1);
                    appendWithoutLeadingEmpties(strbuf, current);
                    i++;
                }
            } else {
                if (delimiters.indexOf(current) < 0) {
                    appendWithoutLeadingEmpties(strbuf, current);
                    i++;
                } else {
                    // delimiter found ..
                    v.add(strbuf.toString());
                    strbuf = new StringBuilder();
                    i++;
                    current = str.substring(i, i + 1);
                }
            }
            current = str.substring(i, i + 1);
        }
        if (delimiters.indexOf(current) < 0) {
            v.add(strbuf.toString() + current);
        } else {
            v.add(strbuf.toString());
            v.add("");
        }
        return v;
    }




    /**
     * Konvertiert einen String von durch <code>sep</code> getrennten
     * un durch trim() vereinheitlcichten Elementen
     * in eine Liste der Elemente. Im Gegensatz zur Verwendung von StringTokenizern
     * werden auch leere Elemente geliefert.
     * @param str Der in eine Liste zu konvertierende String
     * @param sep Separator fuer Listen-Elemente
     * @return Liste als List-Objekt
     */
    public static List<String> splitWithEmptyEntries(String str, char sep) {
    	//FGL: Erst ab Java 1.7 final List<String> list = new ArrayList<>();
        final List<String> list = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        String rest = str;
        int kommaIdx = str.indexOf(sep);
        while (kommaIdx >= 0) {
            final String value = rest.substring(0, kommaIdx).trim();
            list.add(value);
            rest = rest.substring(kommaIdx + 1);
            kommaIdx = rest.indexOf(sep);
        }
        list.add(rest.trim());

        return list;
    }

    /**
     * Wandelt String in Vector um.
     *
     * @param str Der umzuwandelnde String.
     * @param sep String mit Separator-Zeichenkette länger als 1.
     * @return Vector
     */
    public static List<String> splitWithLongDelimiter(String str, String sep) {
    	//FGL: Erst ab Java 1.7 final List<String> v = new Vector<>();
        final List<String> v = new Vector<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        if (str == null) {
            return v;
        }
        if (str.trim().length() == 0 && sep != null && !str.contains(sep)) {
            v.add("");
            return v;
        }
        final String delimiters = sep == null ? "," : sep;
        final int len = delimiters.length();

        // Von vorn beginnen ...
        int start = 0;
        int hit = str.indexOf(delimiters);
        int i = 0;
        while (hit > -1 && i < 5) {
            final String par = str.substring(start, start + hit);
            v.add(par);
            start = start + hit + len;
            hit = str.substring(start).indexOf(delimiters);
            i++;
        }
        if (start < str.length()) {
            v.add(str.substring(start));
        }
        return v;
    }

    /**
     * Returns true if the string starts with specified sequence.
     * For example: stringStartsWithAny("http://www.google.com", "http", "www", "ftp"); returns true.
     *
     * @param inputString The string to be examined
     * @param searchStrings The sequence to search for
     * @return True if the string starts with sequence
     */
    public static boolean startsWithAny(String inputString, String... searchStrings) {
        for (final String string : searchStrings) {
            if (StringUtils.startsWith(inputString, string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Wandelt String in 2D-Array um
     *
     * Eingabe-Beispiel: {abc}, {defg, hij}, {klmn, opqr}
     *
     * "Innere" geschweifte Klammern werden in beliebiger Schachtelungstiefe als "normaler" Text behandelt.
     * Bei fehlenden schließenden Klammern wird ein Array der Länge 0 zurückgegeben und ein ERROR geloggt
     * Überflüssige schließende Klammern werden als "normales" Zeichen betrachtet
     *
     * @param str Der umzuwandelnde String.
     * @return Array of array of strings, never null
     */
    public static String[][] stringTo2DArray(final String str) {
    	//FGL: Erst ab Java 1.7 final List<List<String>> vList = new ArrayList<>();
        final List<List<String>> vList = new ArrayList<List<String>>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        final String inBuf = str;
        StringBuilder parBuf = new StringBuilder();
        boolean ok = true;
        int ix = 0;
        while (ok && ix > -1 && ix < inBuf.length()) {
            while (ix < inBuf.length()) {
                if (inBuf.charAt(ix) == '{') {
                    break;
                }
                if (inBuf.charAt(ix) != ' ') {
                    parBuf.append(inBuf.charAt(ix));
                }
                ix++;
            }
            addVectorToList(vList, parBuf);
            parBuf = new StringBuilder();
            if (ix < inBuf.length() && inBuf.charAt(ix) == '{') {
                ix++;
                while (ok && ix < inBuf.length()) {
                    if (inBuf.charAt(ix) == '{') {
                        // This is an "inner" bracket, look for the corresponding closing bracket
                        ix = skipContentOfInnerBrackets(inBuf, parBuf, ix);
                    }
                    if (ix < inBuf.length()) {
                        if (inBuf.charAt(ix) == '}') {
                            break;
                        }
                        parBuf.append(inBuf.charAt(ix));
                        ix++;
                    }
                }
                if (ix < inBuf.length() && inBuf.charAt(ix) == '}') {
                    ix++;
                } else {
                    logger.error("Missing closing Bracket at position " + (ix + 1) + " in " + inBuf);
                    ok = false;
                }
            }
            addVectorToList(vList, parBuf);
            parBuf = new StringBuilder();
            while (ix < inBuf.length() && (inBuf.charAt(ix) == ',' || inBuf.charAt(ix) == ' ')) {
                ix++;
            }
        }
        int size = 0;
        if (ok) {
            size = vList.size();
        }
        final String[][] twoDArray = new String[size][];
        if (ok) {
            for (int i = 0; i < vList.size(); i++) {
                twoDArray[i] = new String[vList.get(i).size()];
                for (int j = 0; j < vList.get(i).size(); j++) {
                    twoDArray[i][j] = vList.get(i).get(j);
                }
            }
        }
        return twoDArray;
    }

    /**
     * Löscht Leerzeichen aus einem String.
     *
     * @param s String
     * @return String ohne Leerzeichen
     * @deprecated use StringUtils.isBlank() or StringUtils.trim() or  or StringUtils.trimToEmpty() where appropriate
     */
    @Deprecated
    public static String stripAllSpaces(String s) {
        return s.replaceAll(" ", "");
    }

    public static String stripLeadingAndTrailingQuotes(String strArrData) {
        final int beginIndex = strArrData.startsWith("'") ? 1 : 0;
        final int endIndex = strArrData.endsWith("'") ? 1 : 0;
        return strArrData.substring(beginIndex, strArrData.length() - endIndex);
    }

    /**
     * Entfernt führende Leerzeichen aus einem String.
     * @param text = übergebener String aus dem die führenden Leerzeichen gelöscht werden sollen
     * @return text ohne führende Leerzeichen
     */
    public static String stripLeadingWhitespace(String text) {
        int start = 0;
        while (start < text.length() && Character.isWhitespace(text.charAt(start))) {
            start++;
        }
        return text.substring(start);
    }

    /**
     * Entfernt Tabellenangaben aus einem String, der eine Auflistung von Spalten enthält
     *
     * @param str Der zu untersuchende String, der aus mehreren
     * Spalten, die durch " ,;" getrennt sein können, bestehen kann.
     * @return String ohne Tabellen, oder null, wenn das Argument null war.
     */
    public static String stripTables(String str) {
        if (str == null) {
            return null;
        }
        final StringBuilder strbuf = new StringBuilder();
        final StringTokenizer st = new StringTokenizer(str, " ,;", true);
        String token, key, value;
        int i;
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            i = token.indexOf("=");
            key = token;
            value = "";
            if (i > -1) {
                key = token.substring(0, i);
                value = token.substring(i);
            }
            i = key.indexOf(".");
            if (i > -1) {
                strbuf.append(key.substring(i + 1)).append(value);
            } else {
                strbuf.append(token);
            }
        }
        return strbuf.toString();
    }

    /**
     * Ersetzt Werte in [...], hierbei kann der Platzhalter ein Token #
     * enthalten, welches es ermöglicht, Werte aus einem Properties-Objekt zu
     * lesen, welches Teil der Map ist.
     *
     * @param arg Der zu behandelnde String.
     * @param prefix Eventuelles Präfix in den Properties.
     * @param prop Properties mit zu ersetzenden Werten.
     * @param keep true: nicht gefundene Parameter bleiben erhalten false: nicht
     *        gefundene Parameter werden durch den Leerstring ersetzt
     * @return String mit ersetzten Werten
     */
    public static String substituteVars(String arg, String prefix, Map<?, ?> prop, boolean keep) {
        final StringBuilder parlist = new StringBuilder();
        String work = arg;
        while (work.length() > 0) {
            // Naechste Variablen finden
            final int backslashPos = work.indexOf('\\');
            int i = work.indexOf('[');
            boolean replace;
            // Wenn ein \ direkt vor [...] existiert dann soll dies nicht als
            // Variable angesehen werden und nicht ersetzt werden
            // kann bei Matches in SELECTS benutzt werden
            replace = backslashPos <= -1 || backslashPos + 1 != i;
            if (i > -1 && replace) {
                if (i > 0) {
                    parlist.append(work.substring(0, i));
                }
                i++;
                work = work.substring(i);

                // Ende der Variable finden
                int j = work.indexOf(']');
                if (j < 0) {
                    logger.warn("argsubst: ]" + " fehlt in " + arg);
                    parlist.append(work);
                    break;
                }
                final int k = work.indexOf('[');
                if (k > -1 && k < j) {
                    // Auessere [ einer verschachtelten Variable:
                    parlist.append("[");
                    continue;
                }

                final String par = work.substring(0, j);
                if (par != null && par.length() > 0) {
                    // Bestimmte Zeichen in vermeintlichen Variablennamen deuten
                    // an,
                    // dass das gar keine Variable ist:
                    // [2,3] Informix-Syntax fuer einen Teilstring
                    // = ( @ deuten auf einen xpath-Ausdruck hin
                    // "(..." muss ersetzt werden!
                    if (par.contains(",") || par.contains("=") || par.contains("@")) {
                        parlist.append("[" + par + "]");
                    } else {
                        String key = par;
                        if (prefix != null) {
                            key = prefix + "." + par;
                        }
                        String parval = null;
                        Object parvalObj = null;

                        if (key.contains("#")) {
                            final String[] keyNewArr = key.split("#");
                            final Map<?, ?> tmpProp = (Map<?, ?>) prop.get(keyNewArr[0]);
                            if (tmpProp != null && keyNewArr.length > 1) {
                                parvalObj = tmpProp.get(keyNewArr[1]);
                            } else {
                                logger.warn("tmpProp is " + tmpProp + "  for " + key);
                            }
                        } else {
                            parvalObj = prop.get(key);
                        }

                        if (parvalObj != null) {
                            parval = parvalObj.toString();
                        }

                        if (StringUtils.isBlank(parval)) {
                            if (prefix != null) {
                                key = par;
                                final Object temp = prop.get(key);
                                if (temp != null) {
                                    parval = temp.toString();
                                }
                                if (!StringUtils.isBlank(parval)) {
                                    parlist.append(parval);
                                }
                            }
                        } else {
                            parlist.append(parval);
                        }
                        // evtl. defaults probieren ...
                        if ((parval == null || parval.length() < 1) && keep) {
                            parlist.append("[" + par + "]");
                        }
                    }
                }
                j++;
                work = work.substring(j);
            } else {
                if (backslashPos > -1 && backslashPos + 1 == i) {
                    // der \ wird aus dem String entfernt, damit er als gültiger
                    // Select an die DB übergeben werden kann
                    work = work.replace("\\", "");
                    final int weiter = work.indexOf(']') + 1;
                    // Übergabe des Substring bis an das Ende der eckigen
                    // Klammer an die bestehende Liste
                    parlist.append(work, 0, weiter);
                    work = work.substring(weiter);
                } else {
                    parlist.append(work);
                    work = work.substring(work.length());
                }
            }
        }
        return parlist.toString();
    }

    /**
     * Ersetzt Platzhalter, die durch eckige Klammern begrenzt werden. Sie haben
     * die Form [name], wobei für 'name' der Bezeichner des Platzhalters
     * eingesetzt werden muss. Der Platzhalter wird durch den Wert in der
     * ebenfalls spezifizierten Map ersetzt. Dort wird der Wert des Platzhalters
     * abgelegt, wobei der Bezeichner als Key verwendet wird. Sowohl der String,
     * in dem substituiert werden soll, als auch die Map, welche die Werte
     * enthält, dürfen null sein. Falls der String null ist, wird null
     * geliefert. Nicht vorhandene Werte werden durch den Leerstring ersetzt.
     *
     * @param arg
     *            Der zu behandelnde String. Darf null sein.
     * @param prop
     *            Properties mit zu ersetzenden Werten. Darf null sein.
     * @return String mit ersetzten Werten oder null, falls der Parameter arg
     *         null ist.
     */
    public static String substituteVarsDoNotKeep(String arg, Map<?, ?> prop) {
        return argsubst(arg, prop, false);
    }

    /**
     * Ersetzt Werte in [...], wenn vorhanden;
     * Nicht vorhandene Werte bleieben erhalten
     *
     * @param arg Der zu behandelnde String.
     * @param prop Properties mit zu ersetzenden Werten.
     * @return String mit ersetzten Werten
     */
    public static String substituteVarsKeep(String arg, Map<?, ?> prop) {
        return argsubst(arg, prop, true);
    }

    public static String substring(String str, int l) {
        return str.length() <= l ? str : str.substring(0, l);
    }

    /**
     * Substring, adjusts begin and end so that a
     * java.lang.StringIndexOutOfBoundsException is avoided
     *
     * @param str the str
     * @param beginIndex the begin index
     * @param endIndex the end index
     * @return Substring
     */
    public static String substring(String str, int beginIndex, int endIndex) {
        final int begin = Math.max(0, beginIndex);
        final int end = Math.min(str.length(), endIndex);
        if (end <= begin) {
            return "";
        }
        return str.substring(begin, end);
    }

    public static String substringDelimitedBy(String before, String after, String toSearch) {
        int startPos = toSearch.indexOf(before);
        if (startPos < 0) {
            return null;
        }
        startPos += before.length();
        final int endPos = toSearch.indexOf(after, startPos);
        if (endPos < 0) {
            return null;
        }
        return toSearch.substring(startPos, endPos);
    }

    public static boolean substringIsSurroundedByNonWordCharacters(final String wholeString, int startOfWord, int wordLength) {
        if (startOfWord < 0 || startOfWord >= wholeString.length()) {
            return false;
        }
        if (startOfWord > 0 && isWordChar(wholeString.charAt(startOfWord - 1))) {
            return false;
        }
        final int nextpos = startOfWord + wordLength;
        return nextpos >= wholeString.length() || !isWordChar(wholeString.charAt(nextpos));
    }

    /**
     * Loescht alle Zeichen, die nicht im 7bit-ASCII vorkommen
     * oder Steuerzeichen sind. Zeilenumbrueche und Tabs sind erlaubt.
     *
     * @param st zu konvertierender String
     * @return konvertierter String
     */
    public static String to7ASCII(String st) {
        final StringBuilder sb = new StringBuilder();
        final char[] block = st.toCharArray();
        for (final char element : block) {
            final byte byteValue = (byte) element;
            if (Character.isISOControl(element) && !isTabOrCRLF(byteValue) || byteValue == 0 || byteValue == 1) {
                continue;
            }
            if (element < 128) {
                sb.append(element);
            }
        }
        return sb.toString();
    }

    /**
     * Wandelt Collection in String[]
     *
     * @param strings das umzuwandelnde ArraylList.
     * @return String[] array
     */
    public static String[] toArray(Collection<String> strings) {
        if (strings == null) {
            return new String[0];
        }
        return strings.toArray(new String[strings.size()]);
    }

    public static String toCommaList(Collection<?> c) {
        final StringBuffer msg = new StringBuffer();
        for (final Object o : c) {
            msg.append(o).append(",");
        }
        if (msg.length() > 1) {
            StringBufferUtil.removeSuffix(msg, ",");
        }
        return msg.toString();
    }

    /**
     * Ersetzt alle nichtdruckbaren Sonderzeichen (ausser Tab, CR und LF) durch "?"
     *
     * @param st Der zu bereinigende String
     * @return String.
     */

    public static String toEASCII(String st) {
        final StringBuilder buff = new StringBuilder();
        final char[] block = st.toCharArray();
        for (final char element : block) {
            final byte byteValue0 = (byte) element;
            if (byteValue0 == 0 || byteValue0 == 1) {
                // null-Zeichen und EMPTY einfach ignorieren
                continue;
            }
            final char toPrint = isPrintable(element) ? element : '?';
            buff.append(toPrint);
        }
        return buff.toString();
    }

    /**
     * Null-safe wrapper for toString()
     *
     * @param object the o
     * @return object.toString() or null
     */
    public static String toStringOrNull(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }

    /**
     * Upper case String: Muss alle Zeichen toUpper konvertieren bis auf 'ß' !!
     * Daraus würden sonst zwei große S gemacht werden, die Positionsberechnungen zerschiessen...
     * @param str
     * @return Upper case String
     */
    // XXX missing testcase
    public static String toUpperCaseExceptß(String str) {
        final StringBuffer uBuffer = new StringBuffer();
        int lPos = 0;
        int sPos = str.indexOf('ß');
        while (sPos > -1) {
            final String subStr = str.substring(lPos, sPos);
            uBuffer.append(subStr.toUpperCase());
            uBuffer.append('ß');
            // Nächstes 'ß' suchen...
            lPos = sPos + 1;
            sPos = str.indexOf('ß', lPos);
        }
        // Rest anhängen
        final String subStr = str.substring(lPos);
        uBuffer.append(subStr.toUpperCase());
        return uBuffer.toString();
    }

  //FGL:
  //Cannot refer to the non-final local variable prop defined in an enclosing scope
   // public static <T> String translateAndJoin(Collection<T> list, Properties prop, String separator) {
    public static <T> String translateAndJoin(Collection<T> list, final Properties prop, String separator) {
        if (list == null) {
            return "";
        }
        if (prop == null) {
            return StringUtils.join(list, separator);
        }
        final Iterator<T> it = list.iterator();
        final Iterator<String> iterator = new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public String next() {
                String token = it.next().toString();
                if (prop.containsKey(token)) {
                    token = prop.getProperty(token);
                }
                return token.trim();
            }

            @Override
            public void remove() {
                it.remove();
            }
        };

        return StringUtils.join(iterator, separator);
    }

    /**
     * @param aString Zu bearbeitender String
     * @param toRemove Zeichen, das abgeschnitten werden soll
     * @return evtl. gekürzter String
     */
    public static String trimCharacter(String aString, char toRemove) {
        if (aString == null) {
            return null;
        }
        int startPos = 0;
        int endPos = aString.length();
        while (startPos < endPos && aString.charAt(startPos) == toRemove) {
            startPos++;
        }
        while (startPos < endPos && aString.charAt(endPos - 1) == toRemove) {
            endPos--;
        }
        return aString.substring(startPos, endPos);
    }


    /**
     * Entfernt leere Zeilen aus String
     *
     * Mehrere aufeinanderfolgende Leerzeilen werden zu einer zusammengefasst.
     *
     * @param str Zu bearbeitender String
     * @return evtl. um leere Zeilen erleichterter String
     */
    public static String trimEmptyLines(String str) {
        if (str == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        int startIx = 0;
        int ix = str.indexOf("\n");
        final String crlf = System.lineSeparator();
        while (ix > startIx) {
            sb.append(str, startIx, ix);
            int lineCount = 0;
            while (Character.isWhitespace(str.charAt(ix))) {
                if (str.charAt(ix) == '\n') {
                    lineCount++;
                }
                ix++;
            }
            sb.append(crlf);
            if (lineCount > 1) {
                sb.append(crlf);
            }
            startIx = ix;
            ix = str.substring(startIx).indexOf("\n") + startIx;
        }
        sb.append(str.substring(startIx));
        return sb.toString();
    }

    /**
     * Löscht Whitespaces an Anfang und Ende sowie vor und nach einem Minus
     * und ersetzt darüber hinaus  alle Folgen von Whitespaces durch einzelne Spaces.
     *
     * @param string
     * @return der bereinigte String
     */
    public static String trimWhitespaceAroundDashes(final String string) {
        if (string == null || string.length() < 2 || !string.contains("-")) {
            return string;
        }
        boolean lastIsWhite = false;
        boolean afterDash = true;
        final StringBuilder sb = new StringBuilder();
        for (final char current : string.toCharArray()) {
            if (Character.isWhitespace(current)) {
                lastIsWhite = true;
                continue;
            }
            final boolean currentIsWhite = current == '-';
            if (!currentIsWhite && !afterDash && lastIsWhite) {
                sb.append(' ');
            }
            afterDash = currentIsWhite;
            lastIsWhite = false;
            sb.append(current);
        }
        return sb.toString();
    }


    /**
     * Entfernt Paare äusserer Single Quotes sofern vorhanden
     *
     * @param quotedString Der zu behandelnde String.
     * @return String ohne Single Quotes
     */
    public static String unquote(String quotedString) {
        if (quotedString == null) {
            return quotedString;
        }
        if (!isDelimitedBySingleQuotes(quotedString)) {
            return quotedString;
        }
        return quotedString.substring(1, quotedString.length() - 1);
    }

    /**
     * Entfernt Paare äusserer Double Quotes um einen String.
     *
     * @param str Der zu behandelnde String.
     * @return String ohne Double Quotes
     */
    public static String unquoteDoubles(String str) {
        if (str == null) {
            return null;
        }
        final boolean isInDoubleQuotes = isInDoubleQuotes(str);
        if (!isInDoubleQuotes) {
            return str;
        }
        return str.substring(1, str.length() - 1);
    }

    static boolean isInDoubleQuotes(String str) {
        final int len = str.length();
        return len >= 2 && str.charAt(0) == '"' && str.charAt(len - 1) == '"';
    }

    static String removeBackslashes(String str) {
        boolean justSwallowedABackslash = false;
        final StringBuilder sb = new StringBuilder();
        for (final char c : str.toCharArray()) {
            if (justSwallowedABackslash) {
                sb.append(c);
                justSwallowedABackslash = false;
            } else if (c == '\\') {
                justSwallowedABackslash = true;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static void addTrimmedAndPrinted(Collection<String> items, StringBuffer item) {
        items.add(item.toString().trim());
    }

    private static void addVectorToList(List<List<String>> vList, StringBuilder parBuf) {
        if (parBuf.length() > 0) {
            vList.add(stringToVectorRegardingQuotes(parBuf.toString().trim()));
        }
    }

    /**
     * @param strbuf
     * @param current
     * @return
     */
    private static StringBuilder appendWithoutLeadingEmpties(StringBuilder strbuf, String current) {
        if (current.trim().isEmpty() && strbuf.length() == 0) {
            return strbuf;
        }
        return strbuf.append(current);
    }

    /**
     * @param str1
     * @param str2
     * @return editDistance
     */
    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    private static int editDistance(String str1, String str2) {
        // XXX use StringUtils
        final String s1 = str1.toLowerCase();
        final String s2 = str2.toLowerCase();

        final int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue),
                                            costs[j])
                                       + 1;
                        }
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0) {
                costs[s2.length()] = lastValue;
            }
        }
        return costs[s2.length()];
    }

    private static String generateRandomString(int length, final String chars) {
        final StringBuilder res = new StringBuilder(length);
        final Random rnd = new SecureRandom();
        for (int i = 0; i < length; i++) {
            final int pos = rnd.nextInt(chars.length());
            res.append(chars.charAt(pos));
        }
        return res.toString();
    }

    private static boolean isDelimitedBySingleQuotes(String str) {
        final int len = str.length();
        return len >= 2 && str.charAt(0) == '\'' && str.charAt(len - 1) == '\'';
    }



    private static boolean isPrintable(final char element) {
        final byte asciiValue = (byte) element;
        return !Character.isISOControl(element) || isTabOrCRLF(asciiValue);
    }

    private static boolean isTabOrCRLF(final byte asciiValue) {
        return asciiValue == 9 || asciiValue == 10 || asciiValue == 13;
    }

    private static List<String> nonemptiesToArrayList(String[] x) {
    	//FGL: Erst ab Java 1.7 final List<String> list = new ArrayList<>();
        final List<String> list = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        for (final String string : x) {
            if (StringUtils.isNotEmpty(string)) {
                list.add(string);
            }
        }
        return list;
    }

    private static int skipContentOfInnerBrackets(String inBuf, StringBuilder parBuf, int currentIx) {
        int ix = currentIx;
        while (ix < inBuf.length() && inBuf.charAt(ix) != '}') {
            parBuf.append(inBuf.charAt(ix++));
            if (inBuf.charAt(ix) == '{') {
                // This is an "inner" bracket, look for the corresponding closing bracket
                ix = skipContentOfInnerBrackets(inBuf, parBuf, ix);
            }
        }
        if (ix < inBuf.length() && inBuf.charAt(ix) == '}') {
            parBuf.append(inBuf.charAt(ix++));
        }
        return ix;
    }

    private static List<String> splitLinesOSDependently(String value) {
        List<String> lines = null;
        try {
            lines = CharStreams.readLines(new StringReader(value));
        } catch (final IOException e) {
            logger.warn("Can not split lines OS dependently: " + value, e);
        }
        return lines;
    }

    private static int storeBracketContent(String fieldlist, int current, StringBuffer item) {
        int i = current;
        item.append(fieldlist.charAt(i++));
        while (i < fieldlist.length() && fieldlist.charAt(i) != ')') {
            if (i < fieldlist.length() && fieldlist.charAt(i) == '(') {
                i = storeBracketContent(fieldlist, i, item);
            }
            if (i < fieldlist.length()) {
                if (fieldlist.charAt(i) != ')') {
                    item.append(fieldlist.charAt(i++));
                }
            }
        }
        if (i < fieldlist.length() && fieldlist.charAt(i) == ')') {
            item.append(fieldlist.charAt(i++));
        }
        return i;
    }

    private static Vector<String> stringToVectorRegardingQuotes(String str) {
    	//FGL: Erst ab Java 1.7 final Vector<String> v = new Vector<>();
        final Vector<String> v = new Vector<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        if (str == null || str.trim().length() == 0) {
            return v;
        }
        int ix = 0;
        while (ix < str.length()) {
            StringBuffer strBuf = new StringBuffer();
            while (ix < str.length() && str.charAt(ix) != '\"' && str.charAt(ix) != ',') {
                strBuf.append(str.charAt(ix));
                ix++;
            }
            if (strBuf.length() > 0) {
                v.add(strBuf.toString());
                strBuf = new StringBuffer();
            }
            if (ix < str.length() && str.charAt(ix) == '\"') {
                ix++;
                while (ix < str.length() && str.charAt(ix) != '\"') {
                    strBuf.append(str.charAt(ix));
                    ix++;
                }
                if (strBuf.length() > 0) {
                    v.add(strBuf.toString());
                    strBuf = new StringBuffer();
                }
                ix++;
            }
            if (ix < str.length() && str.charAt(ix) == ',') {
                ix++;
                if (strBuf.length() > 0) {
                    v.add(strBuf.toString());
                    strBuf = new StringBuffer();
                }
                while (ix < str.length() && str.charAt(ix) == ' ') {
                    ix++;
                }
            }
        }
        return v;
    }

    private static List<String> surroundAll(Collection<String> list, String sr) {

        if (StringUtils.isEmpty(sr)) {
            return null;
        }
      //FGL: Erst ab Java 1.7 final List<String> result = new ArrayList<>();
        final List<String> result = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        for (final String item : list) {
            result.add(surroundWith(item, sr));
        }
        return result;
    }

    private static String surroundWith(String item, String sr) {
        return sr + item + sr;
    }

    private StringUtil() {
        // only static utility functions
    }

    /**
     * Wandelt Array in String um
     *
     * @param array Der umzuwandelnde Array.
     * @return comma-separierte String-Liste
     */
    public static String arrayToString(Object[] array) {
        if (array == null) {
            return "";
        }
        final StringBuilder strbuf = new StringBuilder();
        final char sep = ',';
        boolean first = true;
        for (Object item : array) {
            if (!first) {
                strbuf.append(sep);
            } else {
                first = false;
            }
            if (item instanceof Object[]) {
                item = "[" + arrayToString((Object[]) item) + "]";
            }
            strbuf.append(item);
        }
        return strbuf.toString();
    }

    public static String joinWithComma2(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String id : strings) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            sb.append(id);
        }
        return sb.toString();
    }

    public static int countOccurrences(String aString, char charToBeCounted) {
        int z = 0, anz = 0;
        char[] ca = aString.toCharArray();
        anz = ca.length;
        for (int i = 0; i < anz; i++) {
            if (ca[i] == charToBeCounted) {
                z++;
            }
        }
        return z;
    }

    public static List<String> stringToListWithEmptyEntries(String str, String sep) {
    	//FGL: Erst ab Java 1.7 List<String> list = new ArrayList<>();
        List<String> list = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        String rest = str;
        int kommaIdx = str.indexOf(sep);
        while (kommaIdx >= 0) {
            String value = rest.substring(0, kommaIdx).trim();
            list.add(value);
            rest = rest.substring(kommaIdx + sep.length());
            kommaIdx = rest.indexOf(sep);
        }
        list.add(rest.trim());

        return list;
    }

    public static List<String> stringToListWithEmptyEntries2(String str, String sep) {
    	//FGL: Erst ab Java 1.7List<String> list = new ArrayList<>();
        List<String> list = new ArrayList<String>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        String[] result = str.split(sep);
        for (String string : result) {
            if (string != null && string.length() > 0) {
                list.add(string);
            }
        }
        return list;
    }

    public static String stripEOLComments(String cmd, String eolCommentTag) {
        String rest = cmd;
        final StringBuffer cmdbuf = new StringBuffer();
        int ix = rest.indexOf(eolCommentTag);
        while (ix > -1) {
            cmdbuf.append(rest.substring(0, ix));
            ix = rest.indexOf("\n", ix);
            if (ix > -1) {
                rest = rest.substring(ix + 1);
                ix = rest.indexOf(eolCommentTag);
            } else {
                rest = "";
            }

        }
        cmdbuf.append(rest);
        return cmdbuf.toString();
    }

    public static String concat(Object... os) {
        StringBuilder sb = new StringBuilder();
        for (Object o : os) {
            sb.append(o);
        }
        return sb.toString();
    }

    public static String suffixOrall(String aString, char separatorChar) {
        final int lastPointIdx = aString.lastIndexOf(separatorChar);
        if (lastPointIdx < 0) {
            return aString;
        }
        return aString.substring(lastPointIdx + 1);
    }

    /**
     * @see StringUtils#substringBefore(String, String)
     * @param str
     * @param separators
     * @return the substring before the first occurrence of one of the given separators, null if null String input
     */
    public static String substringBeforeAny(String str, String... separators) {
        if (StringUtils.isEmpty(str) || separators == null || separators.length == 0) {
            return str;
        }
        int pos = str.length();
        for (String curSeparator : separators) {
            if (curSeparator.length() == 0) {
                continue;
            }
            int curPos = str.indexOf(curSeparator);
            if (curPos > INDEX_NOT_FOUND && curPos < pos) {
                pos = curPos;
            }
        }
        if (pos == INDEX_NOT_FOUND) {
            return str;
        } else {
            return str.substring(0, pos);
        }
    }

    /**
     * @see StringUtils#substringAfter(String, String)
     * @param str
     * @param separators
     * @return the substring after the first occurrence of one of the given separators, null if null String input
     */
    public static String substringAfterAny(String str, String... separators) {
        if (StringUtils.isEmpty(str) || separators == null || separators.length == 0) {
            return str;
        }
        int pos = str.length();
        for (String curSeparator : separators) {
            if (curSeparator.length() == 0) {
                continue;
            }
            int curPos = str.indexOf(curSeparator);
            if (curPos > INDEX_NOT_FOUND && curPos < pos) {
                pos = curPos + curSeparator.length();
            }
        }
        if (pos == INDEX_NOT_FOUND) {
            return StringUtils.EMPTY;
        } else {
            return str.substring(pos);
        }
    }
}

