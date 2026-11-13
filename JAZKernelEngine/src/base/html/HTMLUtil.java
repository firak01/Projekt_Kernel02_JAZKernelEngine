package base.html;
//package de.his.core.base.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Element;

//FGL 20180216: Rausgenommen beim Reengineering
//import de.his.core.common.globalConfiguration.GlobalConfiguration;

/**
 * Stringverarbeitung f&uuml;r HTML.
 *
 * @author richter / brummermann
 */
// FIXME used by JRuby, the de.his.core hierarchie should be free from such references!!!!
public class HTMLUtil {
    public static final String HTML_EntityStart = "&#";

    private static Properties maskedValueTable;

    /** Special tag information. */
    public static final String[] specialTags = { "<br>", "<br/>", "<br />", "<hr>", "<hr/>", "<hr />" };

    /** Tag information for fo transformation. */
    public static final String[] formatTags = { "<b>", "<i>", "<br/>", "<u>", "<br />", "<br>", "<sub>", "<sup>", "<ol>", "<li>", "<ul>", "<em>", "<strong>", "<p>", "<strike>" };

    /** End tag information for fo transformation. */
    public static final String[] strFormatEndTag = { "</b>", "</i>", "<br/>", "</u>", "<br />", "<br/>", "</sub>", "</sup>", "</ol>", "</li>", "</ul>", "</em>", "</strong>",
                                                     "</p>", "</strike>" };

    /** Xml-tag transformation. */
    public static final String[] strFormatXMLTag = { "b", "i", "br", "u", "br", "br", "sub", "sup", "ol", "li", "ul", "em", "strong", "p", "strike" };

    public HTMLUtil() {
    }

    public static enum HtmlParserStatus {
        START, IN_TAG, IN_CLOSING_TAG, WAIT_FOR_ATTR_OR_CLOSING_OF_TAG, WAIT_FOR_ATTRIBUTEVALUE, WAIT_FOR_OPENQUOTE, WAIT_FOR_CLOSINGQUOTE, WAIT_FOR_CLOSING_OF_TAG;
    }

    /**
     *  ______________________________________________________________________
     * |                                                                      |
     * |  ACHTUNG: Es geht in dieser Klasse sowohl um die Verhinderung von    |
     * |           JavaScript-Injection als auch die Verhinderung von         |
     * |           Phishing-Attacken durch vorgetäuschte Systemmeldungen.     |
     * |______________________________________________________________________|

     * Nur diese HTML-Tags sind erlaubt
     */
    public static final List<String> WHITELIST_TAGS_PAIR = Arrays.asList("h1", "h2", "h3", "h4", "h5", "h6", "a", "b", "i", "u", "sub", "sup", "ul", "ol", "li", "dl", "dt", "dd",
                    "p", "span", "em", "s", "strike", "strong", "table", "tbody", "thead", "tfoot", "tr", "th", "td",
                    "caption", "div", "small", "pre", "code");

    /** Elemente ohne eigenständiges schließendes Tag (wie <br />). */
    public static final List<String> WHITELIST_TAGS_SINGLE = Arrays.asList("br", "hr", "img");

    /** Gültige Attribute für die Texte. */
    public static final List<String> ATTRIBUTES = Arrays.asList("title", "alt", "src", "target", "style", "class", "align", "valign", "bgcolor", "colspan", "rowspan", "height",
                    "nowrap", "width", "cellpadding", "cellspacing", "border", "id", "scope", "summary", "href", "lang");

    /** Gueltige style-Regeln. */
    private static final List<String> WHITELIST_CSS_PROPERTIES = Arrays.asList("width", "height",
                    // negative margins might be used to move text out: "margin", "margin-top", "margin-right", "margin-bottom", "margin-left",
                    // "padding", "padding-top", "padding-right", "padding-bottom", "padding-left",
                    "direction", "line-height", "vertical-align", "list-style-type", "list-style-position", "color",
                    "background-color", "background-repeat", "font", "font-family", "font-style", "font-variant",
                    "font-weight", "font-size", "text-indent", "text-align", "text-decoration", "letter-spacing",
                    "word-spacing", "text-transform", "white-space");

    /*    RegEx:    	
    Zwei kleine Tipps für deine Methode
    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
     */
    //alt bleibte erhalten:
    private static final Pattern PATTERN_STYLE_RGB = Pattern.compile("(?i)^rgb[(]([^,]*), *([^,]*), *([^,)]*).*$");
    //neu: 
    //private static final Pattern PATTERN_STYLE_RGB = Pattern.compile(Pattern.quote("(?i)^rgb[(]([^,]*), *([^,]*), *([^,)]*).*$"));

    
    

    /**
     * Decodiert HTML. Nur einsetzen, wenn sichergestellt ist, dass das HTML
     * später, bspw. durch JSF, wieder maskiert wird.
     * Sichere Alternative: removeHTML(String html)
     *
     * @param html
     * @param replacement
     * @return String without HTML-Tags
     */
    public static String removeHTMLUnsafe(String html, String replacement) {
        return replaceHTMLTags(html, replacement, false);
    }

    /**
     * Remove all HTML-Tags from String.
     *
     * @param html
     * @return String without HTML-Tags
     */
    // XXX REFACTOR if not used by JRuby, name it removeHTMLTags
    public static String removeHTMLTags(String html) {
        return replaceHTMLTags(html, "", true);
    }


    /**
     * Remove all HTML-Tags from String.
     *
     * @param html
     * @param replacement
     * @return String without HTML-Tags
     */
    private static String replaceHTMLTags(String html, String replacement, boolean safeRemove) {
        if (html == null) {
            return null;
        }
        String escaped = html;
        if (escaped.contains("<")) {
            escaped = escaped.replaceAll("<.*?>", replacement);
        }
        //kodierte Zeichen dekodieren
        escaped = HTMLUtil.decodeString(escaped);
        // Wenn jetzt immer noch < oder > enthalten sind, dann war die HTML-Schachtelung nicht korrekt.
        // In diesem Fall alle Zeichen maskieren!
        if (safeRemove && (escaped.contains("<") || escaped.contains(">"))) {
            escaped = HTMLUtil.escapeML(escaped, false);
        }
        return escaped;
    }

    /**
     * Ersetzt ein Token durch ein anderes, wobei der Inhalt von Tags ignoriert wird.
     * Es wird nur der "sichtbare" HTML-Text berücksichtigt.
     * Fehler in der Syntax werden ignoriert.
     *
     * @param html
     * @param searchRegex
     * @param replacement
     * @return html with replaced tokens
     */
    public static String replaceToken(String html, String searchRegex, String replacement) {
        final int STATUS_START = 0;
        final int STATUS_IN_TAG = 1;
        int status = STATUS_START;
        final StringBuffer buf = new StringBuffer();
        final StringTokenizer st = new StringTokenizer(html, "<>", true);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            switch (status) {
            case STATUS_START: { // Ausserhalb von Tags
                if ("<".equals(token)) {
                    status = STATUS_IN_TAG;
                } else if (">".equals(token)) {
                    //                    ok = false;
                } else {
                    token = token.replaceAll(searchRegex, replacement);
                }
                buf.append(token);
                break;
            }
            case STATUS_IN_TAG: { // Innerhalb eines Tags
                if (">".equals(token)) {
                    status = STATUS_START;
                }
                if ("<".equals(token)) {
                    //                    ok = false;
                }
                buf.append(token);
                break;
            }
            }
        }
        return buf.toString();
    }

    /**
     * Rewrites all rgb()-definitions to the color values.
     *
     * @param aStyle a style definition which may contain zero, one or more rgb()-definitions
     * @return a cleaned up style definition without rgb-function calls
     */
    public static String rewriteStyleRGBDefinitions(String aStyle) {
        String style = aStyle;
        int pos = style.indexOf("rgb(");
        while (pos > -1) {
            //"rgb(255, 0, 0)" +
            final int posEnd = style.indexOf(")", pos);
            if (posEnd < 0) {
                break;
            }
            style = style.substring(0, pos) + rewriteStyleRGBDefinition(style.substring(pos, posEnd)) + style.substring(posEnd + 1);
            pos = style.indexOf("rgb(");
        }
        return style;
    }

    /**
     * Rewrites a rgb()-definition to a color value
     *
     * @param style rgb(..., ..., ...) definition
     * @return #rrggbb color value
     */
    public static String rewriteStyleRGBDefinition(String style) {
        final Matcher matcher = PATTERN_STYLE_RGB.matcher(style);
        if (matcher.find()) {
            final String r = matcher.group(1);
            final String g = matcher.group(2);
            final String b = matcher.group(3);
            return "#" + intTo2DigitHex(r) + intTo2DigitHex(g) + intTo2DigitHex(b);
        }
        return style;
    }

    /**
     * Converts an integer to a 2-digit hexadecimal value.
     *
     * @param number number to convert
     * @return 2-digit hexadecimal value
     */
    private static String intTo2DigitHex(String number) {
        final String temp = Integer.toHexString(Integer.parseInt(number));
        if (temp.length() < 2) {
            return "0" + temp;
        }
        return temp;
    }

    /**
     * Validates a single style-declaration, used internally by validateStyleAttribute which
     * checks a number of asumptions made by this methods.
     *
     * @param declaration css style declaration
     * @return true if it is secure, false otherwise
     */
    public static boolean validateStyleDeclaration(String declaration) {
        if (declaration.isEmpty()) {
            return true;
        }
        final int pos = declaration.indexOf(":");
        if (pos < 0) {
            return false;
        }
        final String property = declaration.substring(0, pos).trim();
        /*String value = declaration.substring(pos + 1).trim();
        logger.debug("property: !" + property + "!");
        logger.debug("value: !" + value + "!");*/
        return WHITELIST_CSS_PROPERTIES.contains(property.toLowerCase(Locale.ENGLISH));
    }

    /**
     * @return all Tags within whitelist
     */
    // XXX used by JRuby
    public static List<String> getWhitelistTags() {
        final List<String> toReturn = new ArrayList<String>(WHITELIST_TAGS_PAIR);
        toReturn.addAll(WHITELIST_TAGS_SINGLE);
        return toReturn;
    }

    /**
     * @return all attributes within whitelist
     */
    // XXX used by JRuby
    public static List<String> getWhitelistAttributes() {
        return ATTRIBUTES;
    }

    /**
     * Wandelt in einer Zeichenfolge HTMLEntities, die NichtLatin1Characters repräsentieren, zurück.
     *
     * @param source
     *            Zeichenfolge
     * @return eventuell umgewandelte source
     */
    public static String recodeNotLatin1CharsAsHTMLEntities(String source) {
        final StringBuilder res = new StringBuilder();
        int start = 0;
        int ix = source.indexOf(HTMLUtil.HTML_EntityStart);
        while (ix > start - 1) {
            res.append(source, start, ix);
            start = ix + HTMLUtil.HTML_EntityStart.length();
            ix = start + source.substring(start).indexOf(";");
            if (ix > start - 1) {
                final String val = source.substring(start, ix);
                try {
                    final int numVal = Integer.parseInt(val);
                    res.append((char) numVal);
                    start = ix + 1;
                } catch (final NumberFormatException e) {
                    // Do nothing
                }
                ix = start + source.substring(start).indexOf(HTMLUtil.HTML_EntityStart);
            } else {
                // This is maybe irregular
                res.append(HTMLUtil.HTML_EntityStart);
            }
        }
        res.append(source.substring(start));
        return res.toString();
    }

    /**
     * Ersetzt alle Zeilenumbrüche (egal welches Format) durch eine Zeichenfolge.
     *
     * @param string      Quelle
     * @param replacement Ersätzung
     * @return ersätzter String
     */
    public static String replaceLineBreaks(String string, String replacement) {
        if (string != null) {
            return string.replaceAll("\r\n", replacement).replaceAll("\n", replacement).replaceAll("\r", replacement);
        }
        return null;
    }

    /**
     * Method to demask quoted characters.
     *
     * @param strInput
     * @return decodierter String
     */
    public static String decodeString(String strInput) {
        if (strInput == null) {
            return strInput;
        }
        final String source = strInput;
        String strResult = StringUtils.replace(source, "&quot;", "\"");
        final String source1 = strResult;
        strResult = StringUtils.replace(source1, "&amp;quot;", "\"");
        if (HTMLUtil.maskedValueTable != null) {
            for (Entry<Object, Object> entry : HTMLUtil.maskedValueTable.entrySet()) {
                final String key = (String) entry.getKey();
                final String value = (String) entry.getValue();
                if (value != null) {
                    final String source2 = strResult;
                    strResult = StringUtils.replace(source2, key, value);
                }
            }
        }
        return HTMLUtil.decodeNumericHTMLEntities(strResult);
    }

    public static String decodeString2(String string) {
    	////FGL 20180216: Rausgenommen beim Reengineering
//        if (maskedValueTable == null) {
//            Properties rPropDispatch = GlobalConfiguration.getRPropDispatch();
//            setMaskedValueTable(GlobalConfiguration.getConfProperties(rPropDispatch.getProperty("CONFROOT")
//                                                                      + rPropDispatch.getProperty("MaskedValueTable", "MaskedValueTable.txt"),
//                            rPropDispatch));
//        }
        return decodeString(string);
    }

    /**
     * Diese Funktion kodiert in HTML/XML nicht erlaubte bzw.
     * problematische Zeichen wie <, >, " und & in &lt; &gt; &quot; &amp;
     *
     * @param st Eingabestring
     * @param escapeAmp true, wenn &amp; maskiert werden sollen
     * @param c wenn vorhanden, nur die vorhandenen berücksichtigen
     * @return String kodierte Ausgabe
     */
    public static String escapeML(String st, boolean escapeAmp, char... c) {
        if (st == null) {
            return "";
        }
        final int estimatedCapacity = (int) Math.round(st.length() * 1.1 + 100); // + 100 ist für kleine Strings
        final StringBuilder buff = new StringBuilder(estimatedCapacity);
        final char[] block = st.toCharArray();
        String stEntity = null;
        int i, last;
        for (i = 0, last = 0; i < block.length; i++) {
            final boolean replace = c.length <= 0 || ArrayUtils.contains(c, block[i]);
            if (!replace) {
                continue;
            }
            // Ampersand nur ersetzen, wenn angefordert, vorhanden und nicht bereits ersetzt
            final boolean doEscapeAmp = escapeAmp && block[i] == '&' && (st.length() < i + 5 || !"&amp;".equals(st.substring(i, i + 5)));
            stEntity = HTMLUtil.escapeChar(block[i], doEscapeAmp);
            // Ersetzung nur durchführen, wenn ersetzte(s) Zeichen unterschiedlich zum urspr. Zeichen
            if (stEntity != null && (stEntity.length() != 1 || stEntity.charAt(0) != block[i])) {
                buff.append(block, last, i - last);
                buff.append(stEntity);
                stEntity = null;
                last = i + 1;
            }
        }
        if (last < block.length) {
            buff.append(block, last, i - last);
        }
        return buff.toString();
    }

    /**
     * Method to demask quoted characters of the type &#nnn[n];.
     *
     * @param strInput
     * @return decodierter String
     */
    public static String decodeNumericHTMLEntities(String strInput) {
        final StringBuilder strBuilder = new StringBuilder();
        int start = 0;
        int ix = strInput.indexOf(HTML_EntityStart);
        while (ix > -1 && ix < strInput.length() - 3) {
            int i = ix + 2;
            char chr = strInput.charAt(i);
            if (Character.isDigit(chr) && i < strInput.length() - 1) {
                final StringBuilder value = new StringBuilder();
                chr = strInput.charAt(i++);
                while (Character.isDigit(chr) && i < strInput.length()) {
                    value.append(chr);
                    chr = strInput.charAt(i++);
                }
                if (chr == ';') {
                    strBuilder.append(strInput, start, ix);
                    strBuilder.append((char) Integer.parseInt(value.toString()));
                } else {
                    strBuilder.append(strInput, start, i);
                }
            }
            start = i;
            ix = strInput.indexOf(HTML_EntityStart, start);
        }
        strBuilder.append(strInput.substring(start));
        return strBuilder.toString();
    }

    /**
     * @param c
     * @param escapeAmp
     * @return escaped String
     */
    public static String escapeChar(char c, boolean escapeAmp) {
        final char[] charArr = { c };
        String stEntity = new String(charArr);
        switch (c) {
        case '<':
            stEntity = "&lt;";
            break;
        case '>':
            stEntity = "&gt;";
            break;
        case '\'':
            stEntity = "&apos;";
            break;
        case '\"':
            stEntity = "&quot;";
            break;
        case '&':
            if (escapeAmp) {
                stEntity = "&amp;";
            }
            break;
        default:
            /* no-op */
        }
        return stEntity;
    }

    /**
     * Method to mask UTF-8 characters to quoted characters.
     *
     * @param strInput
     * @return decodierter String
     */
    public static String ecodeUTF8ToHTML(String strInput) {
        if (strInput == null || maskedValueTable == null) {
            return strInput;
        }
        String strResult = strInput;
        final Iterator<?> itr = maskedValueTable.keySet().iterator();
        while (itr.hasNext()) {
            final String key = (String) itr.next();
            final String value = maskedValueTable.getProperty(key);
            if (value != null) {
                final String source = strResult;
                strResult = StringUtils.replace(source, value, key);
            }
        }
        return strResult;
    }

    /**
     * @return maskedValueTable HTML-Entities
     */
    public static Properties getMaskedValueTable() {
        return maskedValueTable;
    }

    /**
     * Setzt maskedValueTable.
     *
     * @param maskedValueTable HTML-Entities
     */
    public static void setMaskedValueTable(Properties maskedValueTable) {
        HTMLUtil.maskedValueTable = maskedValueTable;
    }

    /**
     * Diese Funktion kodiert in HTML/XML nicht erlaubte bzw.
     * problematische Zeichen wie &lt; &gt; &quot; &amp;
     *
     * @param st Eingabestring
     * @return String kodierte Ausgabe
     */
    public static String escapeML(String st) {
        return escapeML(st, true);
    }

    /**
     * Diese Funktion kodiert in HTML/XML nicht erlaubte bzw.
     * problematische Zeichen wie &lt; &gt; &quot; &amp;
     *
     * @param st Eingabestring
     * @param escapeAmp
     * @return String kodierte Ausgabe
     */
    public static String escapeXML(String st, boolean escapeAmp) {
        return escapeML(st, escapeAmp, '<', '&');
    }

    public static String clearWrongHTMLSyntaxAndPreserveWhitespace(String htmlText) {
        return clearWrongHTMLSyntax(htmlText, true);
    }

    public static String clearWrongHTMLSyntax(String htmlText) {
        return clearWrongHTMLSyntax(htmlText, false);
    }

    public static String clearWrongHTMLSyntax(String htmlText, boolean preserveWhiteSpaces) {
        String strCorrectText = htmlText;
        String strNewTag = null;
        if (preserveWhiteSpaces) {
            final String source = strCorrectText;
            strCorrectText = StringUtils.replace(source, "\r\n", "<br/>");
        }
        // Begin-Tags bearbeiten und umformatieren, wenn notwendig
        for (final String element : HTMLUtil.strFormatXMLTag) {
            strNewTag = "<" + element + ">";
            final String source = strCorrectText;
            final String after = strNewTag;
            strCorrectText = StringUtils.replace(source, "< " + element + ">", after);
            final String source1 = strCorrectText;
            final String after1 = strNewTag;
            strCorrectText = StringUtils.replace(source1, "<" + element + " >", after1);
            final String source2 = strCorrectText;
            final String after2 = strNewTag;
            strCorrectText = StringUtils.replace(source2, "< " + element + " >", after2);
        }
        // End-Tags bearbeiten und umformatieren, wenn notwendig
        for (final String element : HTMLUtil.strFormatXMLTag) {
            strNewTag = "</" + element + ">";
            final String source = strCorrectText;
            final String after = strNewTag;
            strCorrectText = StringUtils.replace(source, "< /" + element + ">", after);
            final String source1 = strCorrectText;
            final String after1 = strNewTag;
            strCorrectText = StringUtils.replace(source1, "</" + element + " >", after1);
            final String source2 = strCorrectText;
            final String after2 = strNewTag;
            strCorrectText = StringUtils.replace(source2, "</ " + element + ">", after2);
            final String source3 = strCorrectText;
            final String after3 = strNewTag;
            strCorrectText = StringUtils.replace(source3, "</ " + element + " >", after3);
            final String source4 = strCorrectText;
            final String after4 = strNewTag;
            strCorrectText = StringUtils.replace(source4, "< /" + element + " >", after4);
            final String source5 = strCorrectText;
            final String after5 = strNewTag;
            strCorrectText = StringUtils.replace(source5, "< / " + element + " >", after5);
        }
        return strCorrectText;
    }

    public static String getAlphabeticLinks(Element e, String tableAlias) {
        StringBuffer sb = new StringBuffer();
        @SuppressWarnings("unchecked")
        List<Element> l = e.getChildren();
        char c1 = 'z';
        char c2 = 'y';
        for (Element kind : l) {
            if (kind.getChild(tableAlias) != null) {
                c2 = kind.getChildText(tableAlias).toUpperCase().charAt(0);
                if (c2 == 'Ä') {
                    c2 = 'A';
                } else if (c2 == 'Ö') {
                    c2 = 'O';
                } else if (c2 == 'Ü') {
                    c2 = 'U';
                }
                if (c2 != c1) {
                    c1 = c2;
                    sb.append("&nbsp;<a href='#");
                    sb.append(c1);
                    sb.append("'>");
                    sb.append(c2);
                    sb.append("</a>&nbsp;");
                    kind.setAttribute("LinkTarget", Character.toString(c2));
                }
            }
        }
        return sb.toString();
    }

    /**
     * @param optionList  Einträge der Drop Down Liste
     * @param label  Beschriftung der Drop Down Liste
     * @param name  Name der Variablen in der Drop Down Liste
     * @param style  Style der Drop Down Liste
     * @return  HTML Drop Down Liste
     */
    public static String createDropDownList(String optionList, String label, String name, String style) {
        StringBuilder sb = new StringBuilder("<label");
        if (style != null && !style.trim().equals("")) {
            sb.append(" class=");
            sb.append(style);
        }
        sb.append(" for=\"");
        double idr = Math.random();
        String id = "n" + Double.toString(idr).substring(2);
        sb.append(id);
        sb.append("\">");
        sb.append(label);
        sb.append("</label>:&nbsp;&nbsp;<select id=\"");
        sb.append(id);
        sb.append("\" name=\"");
        sb.append(name);
        sb.append("\">");
        sb.append(optionList);
        sb.append("</select>");
        return sb.toString();
    }

    public static String getOptionList(String dropDownString, String sep, String selected) {
        String res = null;
        StringTokenizer st = new StringTokenizer(dropDownString, sep);
        if (st.countTokens() > 1) {
            StringBuilder sb = new StringBuilder();
            while (st.hasMoreTokens()) {
                String value = st.nextToken().trim();
                if (!value.equals("")) {
                    sb.append("<option value=\"");
                    sb.append(value);
                    sb.append('"');
                    if (value.equals(selected)) {
                        sb.append(" selected ");
                    }
                    sb.append('>');
                    sb.append(value);
                    sb.append("</option>");
                }
            }
            res = sb.toString();
        }
        return res;
    }

    public static String createHiddenAndLabel(String dropDownString, String label, String name, String style) {
        StringBuilder sb = new StringBuilder("<span");
        if (style != null && !style.trim().equals("")) {
            sb.append(" class=\"");
            sb.append(style);
        }
        sb.append("\">");
        sb.append(label);
        sb.append(":&nbsp;&nbsp;");
        sb.append(dropDownString);
        sb.append("</span><input type=\"hidden\" name=\"");
        sb.append(name);
        sb.append("\" value=\"");
        sb.append(dropDownString.trim());
        sb.append("\">");
        return sb.toString();
    }

    /**
     * Kodiert & in &amp;.
     *
     * @param st Eingabestring
     * @return String kodierte Ausgabe
     */
    public static String encodeAmpersand(String st) {
        if (st == null) {
            return "";
        }
        final StringBuilder buff = new StringBuilder();
        for (int i = 0; i < st.length(); i++) {
            if ("&".equals(st.substring(i, i + 1)) && !st.substring(i).startsWith("&amp;")) {
                buff.append("&amp;");
            } else {
                buff.append(st, i, i + 1);
            }
        }
        return buff.toString();
    }

    /**
    * Diese Funktion kodiert in HTML/XML
    * problematische Zeichen <, >.
    *
    * @param st Eingabestring
    * @return String kodierte Ausgabe
    */
    public static String encodeTagsAsText(String st) {
        if (st == null) {
            return "";
        }
        final StringBuilder buff = new StringBuilder();
        final char[] block = st.toCharArray();
        int i, last;
    
        for (i = 0, last = 0; i < block.length; i++) {
            String stEntity = null;
            switch (block[i]) {
            case '<':
                stEntity = "&lt;";
                break;
            case '>':
                stEntity = "&gt;";
                break;
            default:
                /* no-op */
            }
            if (stEntity != null) {
                buff.append(block, last, i - last);
                buff.append(stEntity);
                last = i + 1;
            }
        }
        if (last < block.length) {
            buff.append(block, last, i - last);
        }
        return buff.toString();
    }

    /**
     * Kodiert &amp; in &.
     *
     * @param st Eingabestring
     * @return String kodierte Ausgabe
     */
    public static String revertEncodeAmpersand(String st) {
        if (st != null) {
            final String source = st;
            return StringUtils.replace(source, "&amp;", "&");
        }
        return "";
    }

    /**
     * @param str with escaped HTML entities
     * @return escaped String
     */
    public static String decodeHTMLText(String str) {
        final String[] entities = { "&lt;", "&gt;", "&apos;", "&quot;", "&amp;" };
        final String[] substitutes = { "<", ">", "'", "\"", "&" };
        String strResult = str;
        for (int i = 0; i < entities.length; i++) {
            final String source = strResult;
            strResult = StringUtils.replace(source, entities[i], substitutes[i]);
        }
        return strResult;
    }
}

