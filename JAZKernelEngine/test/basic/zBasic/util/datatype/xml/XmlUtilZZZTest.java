package basic.zBasic.util.datatype.xml;
import java.util.ArrayList;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.NullObjectZZZ;
import basic.zBasic.reflection.position.TagTypeFilePositionZZZ;
import basic.zBasic.reflection.position.TagTypeMethodZZZ;
import basic.zBasic.reflection.position.TagTypePositionCurrentZZZ;
import basic.zBasic.util.abstractList.VectorUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.xml.tagtype.ITagByTypeZZZ;
import basic.zBasic.xml.tagtype.ITagTypeZZZ;
import junit.framework.TestCase;

public class XmlUtilZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
	}//END setup
	 
	
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ PREVIOUS
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNamePreviousTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//+++ fuer die getPostionCurrent - XMX
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFilePosition = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################

			sExpression="PRE" + objTagTypeFilePosition.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFilePosition.getTagPartClosing() + "POS";
			sExpressionSolved = objTagTypeFilePosition.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++++++
			//+++ Einfache Verschachtelung aus der Praxis, es gibt keinen Tagname vorher, also den einzigen holen.
			sExpression="Der dynamische Wert1 ist '{[Section A]Testentry1}'. FGL rulez.";
			sExpressionSolved = null;
			sStringToSearch= "{[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
						
			//mit Leerstring
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFilePosition.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFilePosition.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++ Negativtest ohne Suchwert im String und Leerstring als Suchtstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNamePreviousTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POS";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//mit Leerstring
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNamePreviousTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = null;//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
            ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			
			sExpression="PRE"+ objTagTypeMethod.getTagPartOpening() + "eine Methode" +objTagTypeMethod.getTagPartClosing() +  "a"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();			
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = null;
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
							
			//################################################
			//### Negativtest ohne einen CLOSING Tag vorher
			//################################################
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POS";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	
	 
	 
	 
	 
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ NEXT
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testFindFirstTagNameNextTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Class";
			sExpressionSolved = "Z:Class";
			
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Class";
			sExpressionSolved = "Z:Method";
			
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Class";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();		
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "positioncurrent";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//############################################
			//### Negativtest
			//############################################
			
			//+++++ ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 
	 
	 public void testFindFirstOpeningTagNameNextTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//+++++++++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "[";
			sExpressionSolved = "Z:Method";			
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "/Z:Java";
			sExpressionSolved = "Z:Method";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Method";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNamePreviousTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();				
			sExpression="PRE"+ objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "a" + objTagTypeMethod.getTagPartOpening() + "eine Methode" + objTagTypeMethod.getTagPartClosing() + "POST";								
			sExpressionSolved = objTagTypeMethod.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
						
			//++++ Negativtest, es gibt keinen folgenden, offenen TagPArt
			sExpression="PRE" + objTagTypeFile.getTagPartOpening() + "ein Test[abc]pfad" + objTagTypeFile.getTagPartClosing() + "POST";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			

			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();			
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "positioncurrent";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest ohne Suchwert im String
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstOpeningTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstClosingTagNameNextTo() {
		 try {
			String sExpression; String sExpressionSolved;
			String sValue; String sStringToSearch;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			//++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sStringToSearch= "Z:Java";
			sExpressionSolved = "Z:Class";//es gibt kein schliessendes Tag vorher			
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//+++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved = "Z:Class";
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			
			//#################################################################
			//### Einfache Verschachtelung
			//#################################################################
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression="PRE" + objTagType.getTagPartOpening() + "ein Test[abc]pfad" + objTagType.getTagPartClosing() + "POST";
			sExpressionSolved = objTagType.getTagName();
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++++++++++++++++++++++++++
			//Einfacher Test, wenn Leerstring angegeben ist, soll vom Anfang aus gesucht werden und das Erste Tag geholt werden.
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
            ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();						
			sStringToSearch = "";
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sExpressionSolved = "method";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			
			//#####################################
			//### Negativtest ohne Suchwert im String
			//#######################################
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//und das auch mit Leerstring als Suchstring
			sExpression = "Das ist ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
			//+++++++++++++++++++++++++
			//+++++ Negativtest mit Suchwert im String
			sExpression = "Das [ist] ein Wert";
			sExpressionSolved = null;
			sStringToSearch= "[";
			sValue = XmlUtilZZZ.findFirstClosingTagNameNextTo(sExpression, sStringToSearch);
			assertEquals(sExpressionSolved, sValue);
			
		}catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //+++ TAG-FIRST
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
		 
		
	 //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 public void testComputeTagNameFromTagPart() {
		 String sExpression; String sExpressionSolved; 
		 String sValue;
		 try {
			ITagTypeZZZ objTagType = new TagTypeFilePositionZZZ();
			
			sExpression=objTagType.getTagPartOpening();
			sExpressionSolved = objTagType.getTagName();
			sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression=objTagType.getTagPartClosing();
			sExpressionSolved = objTagType.getTagName();
			sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//#### Negativtest
			sExpression = "Blabla";
			sExpressionSolved = null;
			try {
				sValue = XmlUtilZZZ.computeTagNameFromTagPart(sExpression);							
				fail("Method should have thrown an exception.");
			}catch(ExceptionZZZ ez){				
			}
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testFindFirstChildTagNameByParentName() {
		 try {
			//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
			String sExpression; String sExpressionSolved;
			String sValue; String sTagName;
			
			//################################################################
			//### Cascaded Verschachtelung
			//################################################################
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sTagName= "Z:Call";
			sExpressionSolved= "Z:Java";
			sValue = XmlUtilZZZ.findFirstChildTagNameByParentName(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++++++++++
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sTagName= "Z:Java";
			sExpressionSolved= "Z:Class";
			sValue = XmlUtilZZZ.findFirstChildTagNameByParentName(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sTagName= "Z:Class";
			sExpressionSolved= null;
			sValue = XmlUtilZZZ.findFirstChildTagNameByParentName(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			//++++++++++++++++++++++++++++						
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();			
			sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
			sTagName = objTagTypePositionCurrent.getTagName();
			sExpressionSolved = objTagTypeMethod.getTagName();		
			sValue = XmlUtilZZZ.findFirstChildTagNameByParentName(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
							
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	  
	 public void testFindFirstOpeningTagName() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
			String sExpression; String sExpressionSolved;
			String sValue; String sTagName;
			
			try {
				//################################################################
				//### Cascaded Verschachtelung
				//################################################################
			
				//++++++++++++++++++++++++++++
				sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				sExpressionSolved= "Z:Call";
				sValue = XmlUtilZZZ.findFirstOpeningTagName(sExpression);
				assertEquals(sExpressionSolved, sValue);
				
				//++++++++++++++++++++++++++++							
				//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
				ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
				ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();			
				sExpression = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# ";
				sExpressionSolved = objTagTypePositionCurrent.getTagName();
				
				sValue = XmlUtilZZZ.findFirstOpeningTagName(sExpression);
				assertEquals(sExpressionSolved, sValue);
				
				
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}		 
	 }
	 
	 public void testFindFirstTag() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
			String sExpression; String sExpressionSolved;
			String sValue; String sTagName;
			
			try {
				//################################################################
				//### KEINE Cascaded Verschachtelung
				//################################################################
				
				//Fuer das Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
				ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
				ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
				ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();	
				
				
				//############ OHNE KONKRETEN NAMEN #################
				//++++++++++++++++++++++++++++
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sExpressionSolved= "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				sValue = XmlUtilZZZ.findFirstTag(sExpression);
				assertEquals(sExpressionSolved, sValue);
				
				//++++++++++++++++++++++++++++						
				//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.	
				sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
				sExpressionSolved = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing();		
				sValue = XmlUtilZZZ.findFirstTag(sExpression);
				assertEquals(sExpressionSolved, sValue);
				
				//############## MIT KONKRETEN NAMEN ##############
				//++++++++++++++++++++++++++++
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sTagName = "Z:Call";
				sExpressionSolved= "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sTagName = "Z:Java";
				sExpressionSolved= "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sTagName = "Z:Class";
				sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class>";
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sTagName = "Z:Method";
				sExpressionSolved= "<Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method>";
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				
				//++++++++++++++++++++++++++++						
				//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.		
				sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
				sTagName = objTagTypePositionCurrent.getTagName();
				sExpressionSolved = objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() +  objTagTypePositionCurrent.getTagPartClosing();		
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
				sTagName = objTagTypeMethod.getTagName();
				sExpressionSolved = objTagTypeMethod.getTagPartOpening() + "searchDirectory" + objTagTypeMethod.getTagPartClosing() ;		
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
				sTagName = objTagTypeFile.getTagName();
				sExpressionSolved = objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " +  objTagTypeFile.getTagPartClosing();		
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				
				//##############################
				//### Negativtest, Tag nicht im XML enthalten
				//##############################
				sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
				sTagName = "nichtda";
				sExpressionSolved= null;
				sValue = XmlUtilZZZ.findFirstTag(sExpression, sTagName);
				assertEquals(sExpressionSolved, sValue);
				
				
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}		 
	 }
	 
	 public void testFindFirstTagValue() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
		//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		String sExpression; String sExpressionSolved;
		String sValue; String sTagName;
		
		try {
			//################################################################
			//### KEINE Cascaded Verschachtelung
			//################################################################
			
			//Fuer das Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.
			ITagTypeZZZ objTagTypePositionCurrent = new TagTypePositionCurrentZZZ();
			ITagTypeZZZ objTagTypeFile = new TagTypeFilePositionZZZ();
			ITagTypeZZZ objTagTypeMethod = new TagTypeMethodZZZ();	
			
			
			//############ OHNE KONKRETEN NAMEN #################
			//++++++++++++++++++++++++++++
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sExpressionSolved= "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			//++++++++++++++++++++++++++++						
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.	
			sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
			sExpressionSolved = objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing();		
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			//############## MIT KONKRETEN NAMEN ##############
			//++++++++++++++++++++++++++++
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sTagName = "Z:Call";
			sExpressionSolved= "<Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java>";
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sTagName = "Z:Java";
			sExpressionSolved= "<Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method>";
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sTagName = "Z:Class";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaClass}";
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sTagName = "Z:Method";
			sExpressionSolved= "{[ArgumentSection for testCallComputed]JavaMethod}";
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			
			//++++++++++++++++++++++++++++						
			//Hier am Beispiel eines ReflectCodeZZZ.getPositionCurrent() Strings.		
			sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
			sTagName = objTagTypePositionCurrent.getTagName();
			sExpressionSolved = objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing();		
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
			sTagName = objTagTypeMethod.getTagName();
			sExpressionSolved = "searchDirectory";		
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "PRE" + objTagTypePositionCurrent.getTagPartOpening() + objTagTypeMethod.getTagPartOpening() +"searchDirectory" + objTagTypeMethod.getTagPartClosing() + objTagTypeFile.getTagPartOpening() + "< (FileEasyZZZ.java:625) " + objTagTypeFile.getTagPartClosing() + objTagTypePositionCurrent.getTagPartClosing() + "# POST";
			sTagName = objTagTypeFile.getTagName();
			sExpressionSolved = "< (FileEasyZZZ.java:625) ";		
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			
			//##############################
			//### Negativtest, Tag nicht im XML enthalten
			//##############################
			sExpression = "PRE<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>POST";
			sTagName = "nichtda";
			sExpressionSolved= null;
			sValue = XmlUtilZZZ.findFirstTagValue(sExpression, sTagName);
			assertEquals(sExpressionSolved, sValue);
			
			
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 //###########
	 public void testFindLastTag() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
		//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		String sExpression; String sExpressionSolved;
		String sValue; String sTagName;
		
		try {
			//################################################################
			//### KEINE Cascaded Verschachtelung
			//################################################################
			
			//############ OHNE KONKRETEN NAMEN #################
			//++++++++++++++++++++++++++++
			sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
			sExpressionSolved= "<zwei>secondvalue</zwei>";
			sValue = XmlUtilZZZ.findLastTag(sExpression);
			assertEquals(sExpressionSolved, sValue);
								
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		 
	 }
	 
	 //###########
	 public void testFindLastTagValue() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
		//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		String sExpression; String sExpressionSolved;
		String sValue; String sTagName;
		
		try {
			//################################################################
			//### KEINE Cascaded Verschachtelung
			//################################################################
			
			//############ OHNE KONKRETEN NAMEN #################
			//++++++++++++++++++++++++++++
			sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
			sExpressionSolved= "secondvalue";
			sValue = XmlUtilZZZ.findLastTagValue(sExpression);
			assertEquals(sExpressionSolved, sValue);
								
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		 
	 }
	 
	 
	 
	 public void testFindLastClosingTagPartIndexBehind() {
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
			String sExpression; String sExpressionSolved; String sValue; String sTagName;
			                       int iExpressionSolved; int iValue;
			
			try {
				//################################################################
				//### KEINE Cascaded Verschachtelung
				//################################################################
				
				//############ OHNE KONKRETEN NAMEN #################
				//++++++++++++++++++++++++++++
				sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
				iExpressionSolved= StringZZZ.indexOfLastBehind(sExpression, "</zwei>");
				iValue = XmlUtilZZZ.findLastClosingTagPartIndexBehind(sExpression);
				assertEquals(iExpressionSolved, iValue);
									
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}		 		 		 
	 }
	 
	 
	
	 
	 //###########
	 public void testFindSibblingTagNameNextByName() {
			fail("Test existiert noch nicht");
	 }
	 
	 public void testFindTagPreviousByName() {
			fail("Test existiert noch nicht");		 
	 }
	 
	 public void testFindTextOuterXmlBefore() {	
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
		//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
	 	String sExpression = null; String sExpressionSolved = null; String sValue = null;
	 	
		try {
			//Besonderer Test... ueberhaupt kein XML im String vorhanden
			sExpression = "Bin ein Text ohne XML";
			sExpressionSolved= "Bin ein Text ohne XML";
			sValue = XmlUtilZZZ.findTextOuterXmlBefore(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//Negativtest... nix vorher vorhanden				
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "";
			sValue = XmlUtilZZZ.findTextOuterXmlBefore(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "Bin ein Text vor dem XML";
			sValue = XmlUtilZZZ.findTextOuterXmlBefore(sExpression);
			assertEquals(sExpressionSolved, sValue);
															

			//++++++++++++++++++++++++++++
			//+++ Bei mehreren Tags auf gleicher Ebene
			//++++++++++++++++++++++++++++
			sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
			sExpressionSolved= "PRE";
			sValue = XmlUtilZZZ.findTextOuterXmlBefore(sExpression);
			assertEquals(sExpressionSolved, sValue);
								
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	 }
		
	 public void testFindTextOuterXmlBehind() {	
		//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
		//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
	 	String sExpression = null; String sExpressionSolved = null; String sValue = null;
	 	
		try {
			//Besonderer Test... ueberhaupt kein XML im String vorhanden
			sExpression = "Bin ein Text ohne XML";
			sExpressionSolved= "Bin ein Text ohne XML";
			sValue = XmlUtilZZZ.findTextOuterXmlBehind(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			
			//Negativtest... nix dahinter vorhanden				
			sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
			sExpressionSolved= "";
			sValue = XmlUtilZZZ.findTextOuterXmlBehind(sExpression);
			assertEquals(sExpressionSolved, sValue);
			
			sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>Bin ein Text hinter dem XML";
			sExpressionSolved= "Bin ein Text hinter dem XML";
			sValue = XmlUtilZZZ.findTextOuterXmlBehind(sExpression);
			assertEquals(sExpressionSolved, sValue);
															

			//++++++++++++++++++++++++++++
			//+++ Bei mehreren Tags auf gleicher Ebene
			//++++++++++++++++++++++++++++
			sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
			sExpressionSolved= "POST";
			sValue = XmlUtilZZZ.findTextOuterXmlBehind(sExpression);
			assertEquals(sExpressionSolved, sValue);
							
		}catch(ExceptionZZZ ez){
			ez.printStackTrace();
			fail("Method throws an exception." + ez.getMessageLast());
		}		
	 }
	 
	 //#######################################################################################
	 //### TESTS FÜR is....
	 public void testIsXmlContained() {
			//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		 	String sExpression = null; String sExpressionSolved = null; String sValue = null; 
		 	                           boolean bExpressionSolved = false; boolean bValue = false;
		 	
			try {
				//Besonderer Test... ueberhaupt kein XML im String vorhanden
				sExpression = "Bin ein Text ohne XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlContained(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				
				//Negativtest... nix dahinter vorhanden				
				sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlContained(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>Bin ein Text hinter dem XML";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlContained(sExpression);
				assertEquals(bExpressionSolved, bValue);
																

				//++++++++++++++++++++++++++++
				//+++ Bei mehreren Tags auf gleicher Ebene
				//++++++++++++++++++++++++++++
				sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlContained(sExpression);
				assertEquals(bExpressionSolved, bValue);
								
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	 
	 public void testIsXmlStarting() {
			//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		 	String sExpression = null; String sExpressionSolved = null; String sValue = null; 
		 	                           boolean bExpressionSolved = false; boolean bValue = false;
		 	
			try {
				//Besonderer Test... ueberhaupt kein XML im String vorhanden
				sExpression = "Bin ein Text ohne XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				
				//Negativtest... nix dahinter vorhanden				
				sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>Bin ein Text hinter dem XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
																

				//++++++++++++++++++++++++++++
				//+++ Bei mehreren Tags auf gleicher Ebene
				//++++++++++++++++++++++++++++
				sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
								
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	 
	 public void testIsXmlEnding() {
			//Hier ohne FactoryKlassen im Ergebnis arbeiten!!!
			//GRUND: Der gesuchte TagType muss sonst in der TagByTypeFactory vorhanden sein.
		 	String sExpression = null; String sExpressionSolved = null; String sValue = null; 
		 	                           boolean bExpressionSolved = false; boolean bValue = false;
		 	
			try {
				//Besonderer Test... ueberhaupt kein XML im String vorhanden
				sExpression = "Bin ein Text ohne XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
								
				//Negativtest...
				sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>Bin ein Text hinter dem XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				
				sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>Bin ein Text hinter dem XML";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
																
				//Positivtest...
				sExpression = "Bin ein Text vor dem XML<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlStarting(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				sExpression = "<Z:Call><Z:Java><Z:Class>{[ArgumentSection for testCallComputed]JavaClass}</Z:Class><Z:Method>{[ArgumentSection for testCallComputed]JavaMethod}</Z:Method></Z:Java></Z:Call>";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
			
				//++++++++++++++++++++++++++++
				//+++ Bei mehreren Tags auf gleicher Ebene
				//++++++++++++++++++++++++++++
				
				//Negativtest...
				sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				sExpression = "<eins>firstvalue</eins><zwei>secondvalue</zwei>POST";
				bExpressionSolved= false;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
					
				//Positivtest... 	
				sExpression = "PRE<eins>firstvalue</eins><zwei>secondvalue</zwei>";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				sExpression = "<eins>firstvalue</eins><zwei>secondvalue</zwei>";
				bExpressionSolved= true;
				bValue = XmlUtilZZZ.isXmlEnding(sExpression);
				assertEquals(bExpressionSolved, bValue);
				
				
			}catch(ExceptionZZZ ez){
				ez.printStackTrace();
				fail("Method throws an exception." + ez.getMessageLast());
			}
	 }
	 
}//End class
