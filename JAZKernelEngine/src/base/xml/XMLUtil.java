package base.xml;
//package de.his.core.base.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.IllegalDataException;
import org.jdom.JDOMException;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;
import org.jdom.input.JDOMParseException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

//import de.his.core.base.files.FilenameUtil;
import base.files.FilenameUtil;

//FGL 20180216: Rausgenommen beim Reengineering
//import de.his.core.base.files.FileUtil;
//import de.his.core.base.files.TempFileUtil;


//import de.his.core.base.html.HTMLUtil;
import base.html.HTMLUtil;

//import de.his.core.base.numeric.NumberUtil;
import base.numeric.NumberUtil;

//import de.his.core.base.strings.StringUtil;
import base.strings.StringUtil;

/**
 * XMLUtil: Allgemeine Utilities für Document/Element Company: HIS
 *
 * @author Jauer, Bolte, Brummermann
 */
public class XMLUtil {
    private static Logger logger = Logger.getLogger(XMLUtil.class);

    private static String confroot = null;

    public static final String encoding = "UTF-8";

    public static final String HTML_ENTITY = "&([a-zA-Z]+);";
    
    /*    RegEx:    	
    Zwei kleine Tipps für deine Methode
    • Sonderzeichen absichern: Da sToFind als beliebiger Text von außen übergeben wird, könnte darin zufällig ein Regex-Steuerzeichen wie ein Punkt (.) oder ein Fragezeichen (?) stecken. Mit Pattern.compile(Pattern.quote(sToFind)) stellst du sicher, dass die Methode immer nach dem exakten Text sucht und nicht abstürzt.
    • Dollarzeichen beim Ersetzen: Falls im Ersatztext (sReplacement) später einmal ein $-Zeichen vorkommen sollte, wirft replaceAll eine Fehlermeldung, weil es darin eine Regex-Gruppe vermutet. Mit Matcher.quoteReplacement(sReplacement) umgehst du dieses Problem zuverlässig.
     */
    //alt: public static final Pattern htmlEntityPattern = Pattern.compile(XMLUtil.HTML_ENTITY);
    //neu: 
    public static final Pattern htmlEntityPattern = Pattern.compile(Pattern.quote(XMLUtil.HTML_ENTITY));
        

    /**
     * Fügt etwas als Kind einem Element zu. Im Gegensatz zu
     * element.addContent muss die Klasse nicht bekannt sein. Dies ist zum
     * Beispiel notwendigt, wenn die Objekte aus einer
     * element.getContent()-Liste stammen.
     *
     * @param e
     *            Element
     * @param o
     *            hinzuzuf&uuml;gendes Objekt
     * @throws ClassCastException
     *             bei einer unbekannten Klasse
     */
    public static void addContent(Element e, Object o) throws ClassCastException {
        if (o instanceof Element) {
            e.addContent((Element) ((Element) o).clone());
        } else if (o instanceof Comment) {
            e.addContent((Comment) ((Comment) o).clone());
        } else if (o instanceof CDATA) {
            e.addContent((CDATA) ((CDATA) o).clone());
        } else if (o instanceof EntityRef) {
            e.addContent((EntityRef) ((EntityRef) o).clone());
        } else if (o instanceof ProcessingInstruction) {
            e.addContent((ProcessingInstruction) ((ProcessingInstruction) o).clone());
        } else if (o instanceof Text) {
            e.addContent((Text) ((Text) o).clone());
        } else if (o instanceof String) {
            e.addContent((String) o);
        } else {
            throw new ClassCastException("Exspected jdom-content class but got: " + o.getClass().getName());
        }
    }

    public static boolean attributeContainsSomeWord(String[] words, final Element element, final String attribute) {
        final String modulesAttr = element.getAttributeValue(attribute);
        if (modulesAttr == null) {
            return false;
        }
        for (final String word : words) {
            if (StringUtil.containsWord(modulesAttr, word) > -1) {
                return false;
            }
        }
        return true;
    }

    public static boolean attributeValueContains(final Element element, final String key, final String substring) {
        return element.getAttribute(key) != null && element.getAttributeValue(key).contains(substring);
    }

    /**
     * Erstellt ein Element mit dem gleichen Namen und Attributen ohne Inhalt.
     *
     * @return neues Element
     */
    public static Element cloneTagWithAttibutes(Element element) {
        final Element res = new Element(element.getName(), element.getNamespace());
        final Iterator<?> itr = element.getAttributes().iterator();
        while (itr.hasNext()) {
            res.setAttribute((Attribute) ((Attribute) itr.next()).clone());
        }
        return res;
    }


    /**
     * Displays a Hierarchy of JDom Elements as a nested HTML List
     * Attribute values and Element texts are HTML escaped and displayed as strong
     *
     * Do not use any fancy HTML Tags that would be filtered by HTMLUtil.filterHTML!
     *
     * @param element
     * @param cropLargeTexts
     * @return HTML String for Element
     */
    @SuppressWarnings("unchecked")
    public static String dumpAsNestedHtmlList(Element element, boolean cropLargeTexts) {
        if (element == null) {
            return "";
        }

        final StringBuilder builder = new StringBuilder("<ul class=\"treelist\">");

        // Öffnendes Tag mit Attributen
        builder.append("&lt;").append(element.getName());
        for (final Attribute attr : (List<Attribute>) element.getAttributes()) {
            builder.append(" ").append(attr.getName()).append("=\"");
            builder.append("<strong>").append(HTMLUtil.escapeML(attr.getValue())).append("</strong>").append("\"");
        }
        builder.append("&gt;");

        // Text
        if (!StringUtils.isEmpty(element.getTextTrim())) {
            final int cropLimit = 250;
            String text = element.getTextTrim();
            if (cropLargeTexts && text.length() > cropLimit) {
                text = StringUtils.abbreviate(text, cropLimit);
            }

            builder.append("<strong>").append(HTMLUtil.escapeML(text)).append("</strong>");
        }

        // Kindelemente
        for (final Element child : (List<Element>) element.getChildren()) {
            builder.append(dumpAsNestedHtmlList(child, cropLargeTexts));
        }

        // Schließendes Tag
        builder.append("&lt;/").append(element.getName()).append("&gt;");

        builder.append("</ul>");
        return builder.toString();
    }

    /**
     * Displays a Hierarchy for a List of JDom Elements as a nested HTML List
     * @param elements
     * @param cropLargeTexts
     * @return HTML String for Element
     */
    public static String dumpAsNestedHtmlList(List<Element> elements, boolean cropLargeTexts) {
        final StringBuilder builder = new StringBuilder("");
        dumpAsNestedHtmlList(builder, elements, cropLargeTexts);
        return builder.toString();
    }

    public static void dumpAsNestedHtmlList(StringBuilder builder, List<Element> children, boolean cropLargeTexts) {
        if (children == null) {
            return;
        }
        for (final Element child : children) {
            final String dumpAsNestedHtmlList = dumpAsNestedHtmlList(child, cropLargeTexts);
            builder.append(dumpAsNestedHtmlList);
        }
    }

//    /**FGL: Herausgenommen wg. Java 1.7
//     * Erzeugt ein String-Objekt aus einem XML-Baum ohne einleitendes XML-Tag
//     *
//     * @param element
//     *            element
//     * @return String
//     */
//    public static String dumpElement(Element element) {
//        return dumpElement(element, false);
//    }

//    /**FGL: Herausgenommen wg. Java 1.7
//     * Erzeugt ein String-Objekt aus einem XML-Baum ohne einleitendes XML-Tag
//     *
//     * @param raw (true for OO-Reports)
//     * @return String
//     */
//    public static String dumpElement(Element elementRaw, boolean raw) {
//        if (elementRaw == null) {
//            return "";
//        }
//        final Element element = (Element) elementRaw.clone();
//        try {
//            Format format = Format.getPrettyFormat();
//            if (raw) {
//                format = Format.getRawFormat();
//            }
//            format.setEncoding(XMLUtil.encoding);
//            final XMLOutputter out = new XMLOutputter(format);
//            final StringWriter sw = new StringWriter();
//            try (final BufferedWriter bw = new BufferedWriter(sw);) {
//                out.output(element, bw);
//            }
//            return sw.toString();
//        } catch (final Exception e) {
//            logger.error(e, e);
//            return "";
//        }
//    }

//    /**FGL: Herausgenommen wg. Java 1.7
//     * Erzeugt ein String-Objekt aus einem XML-Element
//     *
//     */
//    public static String dumpXML(Element element) {
//        return dumpXML(element, "    ", encoding);
//    }

//    /**FGL: Herausgenommen wg. Java 1.7
//     * Erzeugt ein String-Objekt aus einem XML-Element
//     *
//     */
//    public static String dumpXML(Element element0, String indent, String charset) {
//        if (element0 == null) {
//            return "";
//        }
//        final Element element = (Element) element0.clone();
//        final Document doc = new Document(element);
//        try {
//            final Format format = Format.getPrettyFormat();
//            format.setEncoding(charset);
//            format.setIndent(indent);
//            final XMLOutputter out = new XMLOutputter(format);
//            final StringWriter sw = new StringWriter();
//            try (final BufferedWriter bw = new BufferedWriter(sw);) {
//                out.output(doc, bw);
//            }
//            return sw.toString();
//        } catch (final Exception e) {
//            logger.error(e, e);
//            return "";
//        }
//    }

    /**
     * Ermittelt rekursiv alle Elemente, welche einen bestimmten Namen und ein Kind mit einem bestimmten Namen enthält.
     * Alle gefundenen Elemente werden in die Liste aufgenommen, ...
     *
     * ...bis auf die Child-Elemente von Elementen des bestimmten Namens
     * XXX ist diese Semantik 'by accident'?
     *
     * @param element Element, in welchem gesucht werden soll
     * @param elementName
     * @param childName
     * @param collect Ergebnisliste
     */
    @SuppressWarnings("unchecked")
    public static void getAllElementsByChild(Element element, String elementName, String childName, List<Element> collect) {
        if (element == null) {
            return;
        }
        if (element.getName().equals(elementName)) {
            if (element.getChild(childName) != null) {
                collect.add(element);
            }
            // keine Child-Elemente von Ergebnissen
            return;
        }
        // recurse into child tags
        for (final Element child : (List<Element>) element.getChildren()) {
            getAllElementsByChild(child, elementName, childName, collect);
        }
    }

    /**
     * Diese rekursive Methode ermittelt die Attributwerte, die in den Elementen
     * mit dem Tag "[root]" ein Attribut mit dem Namen "[attname]" haben.
     *
     * @param element
     *            Element, in dem gesucht wird
     * @param tagName
     *            Wort, Root-Tag
     * @param attributeName
     *            Wort, Name des Attributs nach dem gesucht werden soll
     * @param collect wird mit Ergebnissen gefüllt
     */
    @SuppressWarnings("unchecked")
    public static void getAttributeValueListByAttributeKey(Element element, String tagName, String attributeName, List<String> collect) {
        if (element == null) {
            return;
        }
        // try to fetch attribute from current tag
        if (element.getName().equals(tagName)) {
            final String attval = element.getAttributeValue(attributeName);
            if (attval != null) {
                collect.add(attval);
            }
        }
        // recurse into child tags
        for (final Element child : (List<Element>) element.getChildren()) {
            getAttributeValueListByAttributeKey(child, tagName, attributeName, collect);
        }
    }

    /**
     * Liefert alle Kinder des gegebenen Elements. Kapselt dabei die Warning,
     * dass der Typ-Check nicht ok ist.
     *
     * @param elem Element
     * @return Liste der Kinder-Elemente
     * @throws NullPointerException wenn das Argument nicht definiert ist.
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getChildren(Element elem) {
        return elem.getChildren();
    }

    /**
     * Liefert die Kinder des Elements mit dem gewuenschten Namen. Kapselt dabei
     * die Warning, dass der Typ-Check nicht ok ist.
     *
     * @param element Element
     * @param childrenName Name der gewuenschten Kinder
     * @return Liste der Kinder-Elemente mit dem gegebenen Namen
     * @throws NullPointerException
     *             wenn ein Argument nicht definiert ist.
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getChildren(Element element, String childrenName) throws NullPointerException {
        return element == null ? null : element.getChildren(childrenName);
    }

    /**
     * Diese Methode erzeugt ein Document-Object aus einem File-Object
     *
     * Wenn das übergebene Argument kein gültiger FileName ist, wird null
     * zurückgegeben ohne Fehlermeldung.
     * <p>
     * Wenn das übergebene Argument ein gültiger FileName ist, aber kein
     * gültiger XML-File, wird null zurückgegeben mit Fehlermeldung.
     *
     * @param fileName
     *            Name eines Files
     * @return aus dem File erzeugtes Document
     */
    public static Document getDocumentFromFile(String fileName) {
        return getDocumentFromFile(fileName, true);
    }

    /**
     * Diese Methode erzeugt ein Document-Object aus einem File-Object
     *
     * Wenn das übergebene Argument kein gültiger FileName ist, wird null
     * zurückgegeben ohne Fehlermeldung.
     * <p>
     * Wenn das übergebene Argument ein gültiger FileName ist, aber kein
     * gültiger XML-File, wird null zurückgegeben mit Fehlermeldung.
     *
     * @param fileName Name eines Files
     * @param useChroot true, wenn nur Dateien aus dem conf-Verzeichnis gelesen werden duerfen.
     * @return aus dem File erzeugtes Document
     */
    public static Document getDocumentFromFile(String fileName, boolean useChroot) {
        final File file = new File(fileName);
       final boolean res;       
        if (confroot == null) {
            res = true;
        } else {
        	//FGL 20180216: Rausgenommen beim Reengineering
            //res = !useChroot || FileUtil.checkChroot(confroot, fileName);
        	res = false;
        }
        if (res && file.isFile()) {
            final SAXBuilder builder = new SAXBuilder();
            try {
                return builder.build(file);
            } catch (final JDOMException e) {
                logger.error("Fehler bei " + fileName + ": ", e);
            } catch (final IllegalDataException e) {
                logger.error("Fehler bei " + fileName + ": ", e);
            } catch (final IOException e) {
                logger.error("Fehler bei " + fileName + ": ", e);

            }
        }
        return null;
    }

    /**
     * gibt das letzte Element einer Kette von Elementen zurück
     *
     * @param element
     *            Element
     * @param children
     *            Aufzählung der Kinder
     * @return Element letztes gefundenes Elements bzw. null, wenn nicht
     *         gefunden.
     */
    public static Element getElement(Element element, String children) {
        final String[] childArray = StringUtil.splitToArray(children);
        Element e = element;
        for (final String element2 : childArray) {
            if (e == null) {
                break;
            }
            e = e.getChild(element2);
        }
        return e;
    }

    /**
     * Diese rekursive Methode &uuml;berpr&uuml;ft, ob in dem &uuml;bergebenen
     * Element ein Element mit dem Tag <root> existiert und dieses Element ein
     * Kindelement mit dem Tag <child> hat, welches den Wert <childvalue> hat.
     *
     * @param current
     *            Element, in dem gesucht wird
     * @param root
     *            Wort, Root-Tag
     * @param child
     *            Wort, Child-Tag
     * @param childvalue
     *            Wort, ChildValue
     * @return <code>Element</code>, gefundenes Root-Element bzw. null, wenn
     *         nicht gefunden.
     */
    public static Element getElement(Element current, String root, String child, String childvalue) {
        final List<?> children = current.getChildren();
        if (children == null) {
            return null;
        }
        Element result = null;
        final Iterator<?> iterator = children.iterator();
        if (current.getName().equals(root)) {
            while (iterator.hasNext()) {
                final Element element = (Element) iterator.next();
                if (element.getName().equals(child) && element.getText().trim().equals(childvalue)) {
                    result = current;
                    break;
                }
            }
        } else {
            while (iterator.hasNext()) {
                final Element element = (Element) iterator.next();
                result = getElement(element, root, child, childvalue);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Diese Methode sucht ein direktes Kindelement mit dem angegebenen
     * Attribut.
     *
     * @param element
     *            Element, in dem gesucht wird
     * @param attname
     *            Wort, ChildValue
     * @param attvalue
     *            Wort, ChildValue
     * @return Element, gefundenes Element bzw. null, wenn nicht gefunden.
     */
    public static Element getElementByAttribute(Element element, String attname, String attvalue) {
        return getElementByAttribute(element, null, attname, attvalue);
    }

    /**
     * Sucht ein direktes Kindelement mit dem angegebenen Attribut.
     *
     * @param element
     *            Element, in dem gesucht wird
     * @param elementname
     *            Name der zu betrachtenden Kind-Elemente oder <code>null</code>.
     * @param attname
     *            Attribut-Name
     * @param attvalue
     *            Attribut-Wert oder <code>null</code> wenn Attribut-Wert egal ist
     * @return Element, gefundenes Element bzw. null, wenn nicht gefunden.
     */
    public static Element getElementByAttribute(Element element, String elementname, String attname, String attvalue) {
        if (element != null) {
            final Iterator<?> iterator = element.getChildren().iterator();
            while (iterator.hasNext()) {
                final Element child = (Element) iterator.next();
                if (child.getName().equals(elementname) || elementname == null) {
                    final String attval = child.getAttributeValue(attname);
                    if (attval != null && (attvalue == null || attval.equals(attvalue))) {
                        return child;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Sucht das erste Element mit dem angegebenen Namen.<!--sic--> Dabei wird,
     * sofern vorhanden, das name-Attribut ber&uuml;cksichtigt.
     *
     * @param mergeAttributeName
     *            Name des Such-Attributs
     * @param parent
     *            Elternelement, dessen Kinder durchsucht werden sollen
     * @param elementName
     *            Name des Elements
     * @param nameAttribute
     *            Wert des name-Attributs
     * @return gefundenen Element, oder <code>null</code> wenn nichts
     *         gefundenwurde.
     */
    public static Element getElementByNameAndNameAttribute(String mergeAttributeName, Element parent, String elementName, String nameAttribute) {
        if (!StringUtils.isBlank(nameAttribute)) {
            return XMLUtil.getElementByAttribute(parent, elementName, mergeAttributeName, nameAttribute);
        }
        final Element element = parent.getChild(elementName);
        if (element != null && "".equals(element.getAttributeValue(mergeAttributeName, ""))) {
            return element;
        }
        return null;
    }

    /**
     * Diese rekursive Methode &uuml;berpr&uuml;ft, ob in dem &uuml;bergebenen
     * Element ein Element mit dem Tag "[root]" existiert welches ein Attribut
     * mit dem Namen "[attname]" und dem Wert "[attvalue]" hat.
     *
     * @param e
     *            Element, in dem gesucht wird
     * @param root
     *            Wort, Root-Tag
     * @param attname
     *            Wort, RootValue
     * @param attvalue
     *            Wort, RootValue
     * @return <code>Element</code>, gefundenes Root-Element bzw. null, wenn
     *         nicht gefunden.
     */
    @SuppressWarnings("unchecked")
    public static Element getElementFromAttribute(Element e, String root, String attname, String attvalue) {
        if (e == null) {
            return null;
        }
        if (e.getName().equals(root)) {
            final String attval = e.getAttributeValue(attname);
            if (attval == null || !attval.equals(attvalue)) {
                return null;
            }
            return e;
        }
        final List<Element> children = e.getChildren();
        if (children == null) {
            return null;
        }
        for (final Element current : children) {
            final Element treffer = getElementFromAttribute(current, root, attname, attvalue);
            if (treffer != null) {
                return treffer;
            }
        }
        return null;
    }

    /**
     * Diese rekursive Methode &uuml;berpr&uuml;ft, ob in dem &uuml;bergebenen
     * Element ein Element mit dem Tag <root> existiert und dieses Element ein
     * Kindelement mit dem Tag <child> hat, welches den Wert <childvalue> hat.
     *
     * @param e
     *            Element, in dem gesucht wird
     * @param root
     *            Root-Tag
     * @param child
     *            Child-Tag
     * @param attname
     *            ChildValue
     * @param attvalue
     *            ChildValue
     * @return <code>Element</code>, gefundenes Root-Element bzw. null, wenn
     *         nicht gefunden.
     */
    public static Element getElementFromAttributeWithChild(Element e, String root, String child, String attname, String attvalue) {
        final List<?> children = e.getChildren();
        if (children == null) {
            return null;
        }
        final Iterator<?> iterator = children.iterator();
        if (e.getName().equals(root)) {
            while (iterator.hasNext()) {
                final Element element = (Element) iterator.next();
                if (element.getName().equals(child)) {
                    final String attval = element.getAttributeValue(attname);
                    if (attval != null && attval.equals(attvalue)) {
                        return e;
                    }
                }
            }
        } else {
            while (iterator.hasNext()) {
                final Element treffer = getElementFromAttributeWithChild((Element) iterator.next(), root, child, attname, attvalue);
                if (treffer != null) {
                    return treffer;
                }
            }
        }
        return null;

    }

    /**
     * Diese Methode erzeugt ein Element-Object aus einem File-Object (Root)
     * Wenn das übergebene Argument kein gültiger FileName ist, wird null
     * zurückgegeben ohne Fehlermeldung.
     * <p>
     * Wenn das übergebene Argument ein gültiger FileName ist, aber kein
     * gültiger XML-File, wird null zurückgegeben mit Fehlermeldung.
     *
     * @param fileName Name eines Files
     * @return aus dem File erzeugtes Element
     *
     */
    public static Element getElementFromFile(String fileName) {
        Element element = null;
        final Document document = XMLUtil.getDocumentFromFile(fileName, true);
        if (document != null) {
            element = document.getRootElement();
        }
        return element;
    }

    /**
     * Diese Methode erzeugt ein Element-Object aus einem File-Object (Root)
     * Wenn das übergebene Argument kein gültiger FileName ist, wird null
     * zurückgegeben ohne Fehlermeldung.
     * <p>
     * Wenn das übergebene Argument ein gültiger FileName ist, aber kein
     * gültiger XML-File, wird null zurückgegeben mit Fehlermeldung.
     *
     * @param fileName
     *            Name eines Files
     * @param useChroot
     *            true, wenn nur Dateien aus dem conf-Verzeichnis gelesen werden
     *            duerfen.
     * @return aus dem File erzeugtes Element
     *
     */
    public static Element getElementFromFile(String fileName, boolean useChroot) {
        Element element = null;
        final Document document = getDocumentFromFile(fileName, useChroot);
        if (document != null) {
            element = document.getRootElement();
        }
        return element;
    }

    /**
     * Diese Methode erzeugt ein Element-Object aus einem String
     *
     * Wenn der übergebene String kein gültiger Element-String ist, wird null
     * zurückgegeben ohne Fehlermeldung.
     *
     * @param elementString
     *            Element-String
     * @return aus dem String erzeugtes Element
     */
    public static Element getElementFromString(String elementString) {
        Element element = null;
        if (elementString != null) {
                      
        	//FGL: Etwa erst ab einer höheren Java Version?
            //final SAXBuilder saxbuilder = SAXBuilderFactory.getDefaultSAXBuilder();
        	final SAXBuilder saxbuilder = new SAXBuilder();
        	
            try {
                final Document document = saxbuilder.build(new StringReader(elementString));
                if (document != null) {
                    element = document.getRootElement();
                }
              //FGL: Erst ab Java 1.7
              //Multi-catch parameters are not allowed for source level below 1.7
//            } catch (IOException | JDOMException e) {
//                logger.debug(e + "\n" + elementString);
//                if (e instanceof JDOMParseException) {
//                    final Properties props = HTMLUtil.getMaskedValueTable();
//                    if (props != null) {
//                        final Matcher match = XMLUtil.htmlEntityPattern.matcher(elementString);
//                        final StringBuffer sb = new StringBuffer();
//                        while (match.find()) {
//                            String replacement = props.getProperty(match.group());
//                            if (replacement == null) {
//                                replacement = "?" + match.group(1) + "?";
//                            }
//                            match.appendReplacement(sb, replacement);
//                        }
//                        match.appendTail(sb);
//                        logger.debug("-----REPLACED: " + sb);
//                        Document document;
//                        try {
//                            document = saxbuilder.build(new StringReader(sb.toString()));
//                            if (document != null) {
//                                element = document.getRootElement();
//                            }
//                          //FGL: Erst ab Java 1.7
//                          //Multi-catch parameters are not allowed for source level below 1.7
////                        } catch (JDOMException | IOException e1) {
////                            logger.warn(e1 + "\n" + sb);
////                        }
//                        } catch (JDOMException e1) {
//                            logger.warn(e1 + "\n" + sb);
//                        } catch (IOException e1) {
//                            logger.warn(e1 + "\n" + sb);
//                        }
//                    }
//                } else {
//                    logger.warn(e + "\n" + elementString);
//                }
                
            } catch (IOException e) {
                logger.debug(e + "\n" + elementString);                                
            } catch (JDOMException e) {
                logger.debug(e + "\n" + elementString);
                if (e instanceof JDOMParseException) {
                    final Properties props = HTMLUtil.getMaskedValueTable();
                    if (props != null) {
                        final Matcher match = XMLUtil.htmlEntityPattern.matcher(elementString);
                        final StringBuffer sb = new StringBuffer();
                        while (match.find()) {
                            String replacement = props.getProperty(match.group());
                            if (replacement == null) {
                                replacement = "?" + match.group(1) + "?";
                            }
                            match.appendReplacement(sb, replacement);
                        }
                        match.appendTail(sb);
                        logger.debug("-----REPLACED: " + sb);
                        Document document;
                        try {
                            document = saxbuilder.build(new StringReader(sb.toString()));
                            if (document != null) {
                                element = document.getRootElement();
                            }
                          //FGL: Erst ab Java 1.7
                          //Multi-catch parameters are not allowed for source level below 1.7
//                        } catch (JDOMException | IOException e1) {
//                            logger.warn(e1 + "\n" + sb);
//                        }
                        } catch (JDOMException e1) {
                            logger.warn(e1 + "\n" + sb);
                        } catch (IOException e1) {
                            logger.warn(e1 + "\n" + sb);
                        }
                    }
                } else {
                    logger.warn(e + "\n" + elementString);
                }
            }
        }
        return element;
    }

    public static void getElementList(List<Element> collect, Element element, String nameOfListingElements) {
        if (element == null) {
            return;
        }
        if (element.getName().equals(nameOfListingElements)) {
            collect.add(element);
        } else {
            @SuppressWarnings("unchecked")
            final List<Element> children = element.getChildren();
            if (children != null) {
                final Iterator<Element> iterator = children.iterator();
                while (iterator.hasNext()) {
                    getElementList(collect, iterator.next(), nameOfListingElements);
                }
            }
        }
    }

    /**
     * Diese rekursive Methode ermittelt die Elemente die im Element mit dem Tag
     * "[root]" existieren ein Attribut mit dem Namen "[attname]" haben das den
     * Wert "[attvalue]" hat.
     *
     * @param e
     *            Element, in dem gesucht wird
     * @param root
     *            Wort, Root-Tag
     * @param attname
     *            Wort, RootValue
     * @param attvalue
     *            Wort, RootValue
     * @param list
     *            ElementList
     * @return <code>ElementList</code>, Liste gefundener Elemente
     */
    public static List<Element> getElementListFromAttribute(Element e, String root, String attname, String attvalue, List<Element> list) {
        if (e == null) {
            return null;
        }
        if (e.getName().equals(root)) {
            final String attval = e.getAttributeValue(attname);
            if (attval != null && attval.equals(attvalue)) {
                list.add(e);
            }
        } else {
            final List<?> children = e.getChildren();
            if (children != null) {
                final Iterator<?> iterator = children.iterator();
                while (iterator.hasNext()) {
                    list = getElementListFromAttribute((Element) iterator.next(), root, attname, attvalue, list);
                }
            }
        }
        return list;
    }

    /**
     * Diese Methode liefert false, wenn das Element ein Attribute "active" mit
     * dem Wert "n" oder "N" hat, ansonsten true.
     *
     * @param e Element
     * @return <code>active</code> true/false
     */
    public static boolean isActive(Element e) {
        boolean active = true;
        if (e != null) {
            final String activeStr = e.getAttributeValue("active");
            if (activeStr != null && activeStr.toLowerCase().equals("n")) {
                active = false;
            }
        }
        return active;
    }

    /**
     * Wandelt eine Map (Properties-Objekt) in eine XML-Struktur um. ( auch komplexe Maps möglich)
     *
     * @param parent
     *            Eltern-Element
     * @param theMap
     *            Map (z. B. Properties)
     * @return Element
     */
    public static Element mapToXML(Element parent, Map<?, ?> theMap) {
        return mapToXML(parent, theMap, null);
    }

    /**
     * Wandelt eine Map (Properties-Objekt) in eine XML-Struktur um. ( auch komplexe Maps möglich)
     *
     * @param parent  Eltern-Element
     * @param aMap  Map (z. B. Properties)
     * @param keyMask Maskierung für Elementnamen, welche numerisch sind
     * @return Element
     */
    public static Element mapToXML(Element parent, Map<?, ?> aMap, String keyMask) {
        if (aMap == null) {
            return parent;
        }

      //FGL: Erst ab Java 1.7 final Set<Object> sortedSet = new TreeSet<>();
        final Set<Object> sortedSet = new TreeSet<Object>();//FGL: Eclipse Vorschlag: Insert Inferred Type
        sortedSet.addAll(aMap.keySet());

        for (final Object current : sortedSet) {
            String key;
            try {
                key = "_" + Integer.valueOf(current.toString());
            } catch (final Exception e) {
                key = current.toString();
            }
            Object test = null;
            if (key.startsWith("_")) {
                final String realKey = key.substring(1);
                if (NumberUtil.representsInt(realKey)) {
                    final int index = Integer.parseInt(realKey);
                    test = aMap.get(Integer.valueOf(index));
                    if (keyMask != null) {
                        key = keyMask;
                    }
                }
            } else {
                test = aMap.get(key);
            }
            Element element = new Element(key);
            if (test != null && "java.util.LinkedHashMap".equals(test.getClass().getName())) {
                element = mapToXML(element, (Map<?, ?>) test, keyMask);
            } else {
                element.setText(aMap.get(key).toString());
            }
            parent.addContent(element);
        }
        return parent;
    }

    /**
     * Vergleicht die Struktur zweier Elemente anhand der Elementnamen und:
     *
     * ergänzt das Element a um die Unterelemente aus b
     * ersetzt Elemente in a mit Elementen aus b, sofern das Attribut "ueberschreiben='y'" im Element in b gesetzt
     * loesche Element a, wenn Element b das Attribut loeschen="y" enthaelt
     *
     * @param basiselement
     *            Basiselement
     * @param zusatzelement
     *            Element mit Erg&auml;nzungen
     * @return vermischtes Element
     */
    public static Element mergeElements(Element basiselement, Element zusatzelement) {
        if (zusatzelement == null) {
            final Element result = XMLUtil.cloneTagWithAttibutes(basiselement);
            XMLUtil.mergeElements(basiselement, zusatzelement, result);
            return result;
        }
        if ("y".equalsIgnoreCase(zusatzelement.getAttributeValue("ueberschreiben", "").trim())) {
            return zusatzelement;
        }
        final Element result = XMLUtil.cloneTagWithAttibutes(basiselement);
        for (final Object attribute : zusatzelement.getAttributes()) {
            result.setAttribute((Attribute) ((Attribute) attribute).clone());
        }
        XMLUtil.mergeElements(basiselement, zusatzelement, result);
        return result;
    }

    /**
     * Vergleicht die Struktur zweier Elemente anhand der Elementnamen und:
     * <ol>
     * <li>erg&auml;nzt das Element <b>a</b> um die Unterelemente aus <b>b</b></li>
     * <li>ersetzt Elemente in <b>a</b> mit Elementen aus <b>b</b>, sofern das Attribut
     * "ueberschreiben='y'" im Element in <b>b</b> gesetzt ist</li>
     * </ol>
     *
     * @param basiselement
     *            Basiselement
     * @param zusatzelement
     *            Element mit Erg&auml;nzungen
     * @param destination
     *            Ziel des Verschmelzens
     */
    public static void mergeElements(Element basiselement, Element zusatzelement, Element destination) {
        Iterator<?> itr = basiselement.getContent().iterator();
        if (zusatzelement == null) {

            // Wenn es kein zusatzelement gibt, uebernehmen wir einfach alles
            // vom basiselement
            while (itr.hasNext()) {
                XMLUtil.addContent(destination, itr.next());
            }

        } else {
            final String mergeAttributeName = zusatzelement.getAttributeValue("mischattribut", "name");

            // Alle Kinder der ersten Datei durchgehen und entsprechende Kinder
            // von zusatzelement auswerten
            while (itr.hasNext()) {
                final Object o = itr.next();
                if (!(o instanceof Element)) {
                    XMLUtil.addContent(destination, o);
                } else {
                    final Element element = (Element) o;
                    Element newElement = null;

                    // Gibt es ein korrespodierendes Element in der zweiten
                    // Dateien?
                    final Element repElement = XMLUtil.getElementByNameAndNameAttribute(mergeAttributeName, zusatzelement, element.getName(),
                                    element.getAttributeValue(mergeAttributeName));

                    // Element nicht uebernehmen, wenn es als "zu loeschen"
                    // markiert ist.
                    if (repElement != null && "y".equals(repElement.getAttributeValue("loeschen", "").trim().toLowerCase())) {
                        zusatzelement.removeContent(repElement);
                        continue;
                    }

                    // Wenn "ueberschreiben" definiert ist, neues Element
                    // verwenden, sonst das alte
                    if (repElement != null && "y".equalsIgnoreCase(repElement.getAttributeValue("ueberschreiben", "").trim())) {
                        newElement = (Element) repElement.clone();
                        destination.addContent(newElement);
                    } else {
                        // gerade bestimmtes Element uebernehmen
                        newElement = cloneTagWithAttibutes(element);
                        destination.addContent(newElement);
                        if (repElement != null) {
                            final Iterator<?> itr2 = repElement.getAttributes().iterator();
                            while (itr2.hasNext()) {
                                newElement.setAttribute((Attribute) ((Attribute) itr2.next()).clone());
                            }
                        }

                        // Weiter auf der naechsten Ebene
                        mergeElements(element, repElement, newElement);
                    }

                    // Wir haben das Zusatzelement "verbraucht".
                    if (repElement != null) {
                        zusatzelement.removeContent(repElement);
                    }
                }
            }

            // Restliche Objekte der zweiten Datei anfuegen
            itr = zusatzelement.getChildren().iterator();
            while (itr.hasNext()) {
                final Element element = (Element) itr.next();
                if (!"y".equals(element.getAttributeValue("loeschen", "n"))) {
                    XMLUtil.addContent(destination, element);
                }
            }
        }
    }

    /**
     * "Normalisiert" einen String, so dass er als XLM-Identifier geeignet ist.
     *
     * @param nameRaw
     *            vermeintlicher XML-Identifier
     * @return XML-Identifier
     */
    public static String normalizeXMLIdentifier(String nameRaw) {
        if (nameRaw == null || nameRaw.trim().isEmpty()) {
            return "unknown";
        }
        final String name = StringUtil.charsBeforeSpace(nameRaw);
        final char[] nameArray = name.toCharArray();
        for (int i = 0; i < nameArray.length; i++) {
            // Punkte sind auch erlaubt ...
            if (!Character.isJavaIdentifierPart(nameArray[i]) && nameArray[i] != '.') {
                nameArray[i] = '_';
            }
        }
        if (!Character.isJavaIdentifierStart(nameArray[0])) {
            nameArray[0] = '_';
        }
        return new String(nameArray);
    }

    /**
     * Parst eine XML-Datei und führt dabei eine Validierung durch. Fehler werden nicht abgefangen und müssen daher von aufrufenden Methoden
     * behandelt werden. Spezialmodule werden nicht unterstützt.
     *
     * @param xmlFileName Dateiname der XML-Datei mit vollständigem Pfad
     * @return Das Root-Element der Konfigurationsdatei
     * @throws JDOMException wird vom Parser geworfen
     * @throws IOException wird vom Parser geworfen
     */
    public static Document parseAndValidateXMLFile(final String xmlFileName) throws JDOMException, IOException {
        return parseAndValidateXMLFile(xmlFileName, null);
    }

    /**
     * Parst eine XML-Datei und führt dabei eine Validierung durch. Optional kann eine XML-Schema-Datei angegeben werden,
     * die zur Validierung verwendet wird. Fehler werden nicht abgefangen und müssen daher von aufrufenden Methoden
     * behandelt werden. Spezialmodule werden nicht unterstützt.
     *
     * @param xmlFileName Dateiname der XML-Datei mit vollständigem Pfad
     * @param schemaFileName Optionaler Dateiname einer XML-Schema-Datei mit vollständigem Pfad. Kann null sein.
     * @return Das Root-Element der Konfigurationsdatei
     * @throws JDOMException wird vom Parser geworfen
     * @throws IOException wird vom Parser geworfen
     */
    public static Document parseAndValidateXMLFile(final String xmlFileName, final String schemaFileName) throws JDOMException, IOException {
        if (xmlFileName == null) {
            throw new NullPointerException("xmlFileName");
        }

        final File xmlFile = new File(xmlFileName);

        if (!xmlFile.isFile() || !xmlFile.canRead()) {
            throw new IllegalStateException("Kann Konfigurationsdatei '" + xmlFileName + "' nicht laden");
        }

        final SAXBuilder builder = new SAXBuilder("org.apache.xerces.parsers.SAXParser", true);
        builder.setFeature("http://xml.org/sax/features/validation", true);
        // Validierung nur, wenn Schema angegeben ist
        builder.setFeature("http://apache.org/xml/features/validation/dynamic", true);
        builder.setFeature("http://apache.org/xml/features/validation/schema", true);
        builder.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);

        if (schemaFileName != null) {
            builder.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", schemaFileName);
        }

        return builder.build(xmlFile);
    }

    /**
     * Parst die Daten eines Readers in einen JDOM-Objekt-Baum
     *
     * @param xml XML-String
     * @return Element
     */
    public static Element parseXML(Reader xml) {
        if (xml == null) {
            return null;
        }
        Element res = null;
        final SAXBuilder builder = new SAXBuilder(false);
        try {
            res = builder.build(xml).getRootElement();
        } catch (final JDOMException e) {
            logger.error(e, e);
        } catch (final IOException e) {
            logger.error(e, e);
        }
        return res;
    }

    /**
     * Parst einen XML-String in einen JDOM-Objekt-Baum
     *
     * @param xml XML-String
     * @return Element
     */
    public static Element parseXML(String xml) {
        if (xml == null) {
            return null;
        }
        Element res = null;
        final SAXBuilder builder = new SAXBuilder(false);
        try {
            res = builder.build(new StringReader(xml)).getRootElement();
        } catch (final JDOMException e) {
            logger.error(e, e);
        } catch (final IOException e) {
            logger.error(e, e);
        }
        return res;
    }

    public static Element readXMLFile(final File file) {
        final SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try {
            document = builder.build(file);
        } catch (final org.jdom.JDOMException j) {
            logger.error("XML-Datei " + file.getPath() + " fehlerhaft: ", j);
            return null;
        } catch (final IOException e) {
            logger.error("XML-Datei " + file.getPath() + " konnte nicht gelesen werden: ", e);
            return null;
        }
        return document.getRootElement();
    }

    /**
     * Liefert eine XML-Datei als Element. In der Regel sollte getElementFromFile oder getConfElement verwendet werden.
     *
     * @param filename Dateiname mit Pfad
     * @return Root-Element
     */
    public static Element readXMLFile(String filename) {
        final File file = new File(filename);
        return readXMLFile(file);
    }

    /**
     * Setzt das chroot-Verzeichnis
     *
     * @param confroot Verzeichnis
     */
    public static void setConfroot(String confroot) {
        XMLUtil.confroot = confroot;
    }

    /**
     * Dies Funktion überprüft den übergebenen String auf nicht valide XML-Zeichen
     * Falls ein solches Zeichen gefunden wird, wird es entfernt
     * @param st Eingabestring
     * @return String ohne nicht valide XML-Zeichen
     */
    public static String stripNonValidXMLCharacters(String st) {
        if (st == null) {
            return "";
        }
        final StringBuilder buff = new StringBuilder();
        final char[] block = st.toCharArray();
        int i, last;

        for (i = 0, last = 0; i < block.length; i++) {
            if (block[i] == 0x1a) {
                buff.append(block, last, i - last);
                last = i + 1;
            }
        }
        if (last < block.length) {
            buff.append(block, last, i - last);
        }
        return buff.toString();
    }

    public static void writeDocument(final Writer writer, Document document) throws IOException {
        final Format format = Format.getPrettyFormat();
        format.setIndent("   ");
        format.setEncoding(encoding);
        format.setLineSeparator(System.lineSeparator());
        final XMLOutputter out = new XMLOutputter(format);
        out.output(document, writer);
    }

    //FGL Erst ab Java 1.7
    //Resource specification not allowed here for source level below 1.7	
//    /**
//     * Ausgabe eines JDOM-Documents als Datei.
//     *
//     * @return {@code true} wenn erfolgreich; {@code false} andernfalls
//     */
//    public static boolean writeDocumentToFile(Document document, File file) {
//        try (FileOutputStream out2 = new FileOutputStream(file);
//             OutputStreamWriter out = new OutputStreamWriter(out2, "UTF-8");
//             final Writer writer = new BufferedWriter(out);) {
//            writeDocument(writer, document);
//            return true;
//        } catch (final IOException e) {
//            logger.error("Datei konnte nicht geschrieben werden, weil: ", e);
//            return false;
//        }
//    }

    //FGL: Erst ab Java 1.7
    //Resource specification not allowed here for source level below 1.7	
//    /**
//     * Ausgabe eines JDOM-Documents als Datei.
//     *
//     * @return {@code true} wenn erfolgreich; {@code false} andernfalls
//     */
//    public static boolean writeDocumentToFile(Document document, String filenameRaw) {
//        // Schreiben ins Tomcat-Temp-Verzeichnis, in der Regel x:\tomcat\temp,
//        // wenn nichts anderes angegeben.
//        // (abhängig von Tomcat-Installation und Betriebssystems)
//        final String file = TempFileUtil.inTempdirIfRelative(filenameRaw);
//
//        try (FileOutputStream out2 = new FileOutputStream(file);
//             OutputStreamWriter out = new OutputStreamWriter(out2, "UTF-8");
//             final Writer writer = new BufferedWriter(out);) {
//            writeDocument(writer, document);
//            return true;
//        } catch (final IOException e) {
//            logger.error("Datei konnte nicht geschrieben werden, weil: ", e);
//            return false;
//        }
//    }

  //FGL: Erst ab Java 1.7		
  //Resource specification not allowed here for source level below 1.7	
//    public static boolean writeElementToFile(Element element, String file0) {
//        if (element == null) {
//            logger.error("Element ist null, File:" + file0);
//            return false;
//        }
//        if (file0 == null) {
//            logger.error("Element oder File ist null, Element:" + element.getName());
//            return false;
//        }
//        final Element e = (Element) element.clone();
//        final Document doc = new Document((Element) e.detach());
//        final String file = FilenameUtil.appendFileExtensionIfNotPresent(file0, ".xml");
//        return writeDocumentToFile(doc, file);
//    }


    /**
     * Diese rekursive Methode &uuml;berpr&uuml;ft, ob in dem &uuml;bergebenen
     * Element ein Element mit dem Tag <root> existiert welches den Wert
     * <rootvalue> hat.
     *
     * @param e
     *            Element, in dem gesucht wird
     * @param root
     *            Wort, Root-Tag
     * @param rootvalue
     *            Wort, RootValue
     * @param indent
     *            zum Einr&uuml;cken, damit die Schachtelungstiefe erkennbar
     *            wird (nur zum Testen wichtig)
     * @return <code>Element</code>, gefundenes Root-Element bzw. null, wenn
     *         nicht gefunden.
     */
    public static Element getElementQIS(Element e, String root, String rootvalue, String indent) {
        indent = indent + "---";
        Element treffer = null;
        if (e != null) {
            // System.err.println(indent + e.getName() + "=" +
            // e.getText().trim());
            if (e.getName().equals(root)) {
                if (e.getText().trim().equals(rootvalue)) {
                    treffer = e;
                }
            } else {
                final List<?> children = e.getChildren();
                if (children != null) {
                    final Iterator<?> iterator = children.iterator();
                    while (iterator.hasNext()) {
                        treffer = getElementQIS((Element) iterator.next(), root, rootvalue, indent);
                        if (treffer != null) {
                            break;
                        }
                    }
                }
            }
        }
        return treffer;
    }

}

