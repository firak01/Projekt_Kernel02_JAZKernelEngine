package basic.zBasic.util.datatype.dateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import junit.framework.TestCase;

public class DateTimeZZZTest extends TestCase{
	
	
	 protected void setUp(){
		    			
		}//END setup
	 
	 public void testComputeTimestampString(){
		 try{
			 Date objDate = new Date(); //Hole die aktuelle Zeit
			 String sTimestamp01 = DateTimeZZZ.computeTimestampString();
			 assertNotNull("TimestampString01 is never NULL", sTimestamp01);
			 System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ermittelter Timestamp01 " + sTimestamp01);
			 
			 String sTimestamp02 = DateTimeZZZ.computeTimestampStringByDate(objDate);
			 assertNotNull("TimestampString02 is never NULL", sTimestamp02);
			 System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ermittelter Timestamp02 " + sTimestamp02);
			 
			 //Merke: Dieser Timestamp ist nicht zwingend unique. Dafür gibt es anderer Methoden
			 //assertFalse("Timestamps sollten verschieden sein, auch wenn nur wenige Milisekunden zwischen ihrer Erzeugung liegen.", sTimestamp01.equals(sTimestamp02) );
			 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
	 public void testComputeTimestampUniqueString(){
		 try{
			 ///Date objDate = new Date(); //Hole die aktuelle Zeit  //Merke: Es ist nicht möglich für einen bestimmten String einen Unique-Wert zu holen. Dann sollte man eher auf reine Zufallszahlen ausweichen.
			 String sTimestamp01 = DateTimeZZZ.computeTimestampUniqueString();
			 assertNotNull("TimestampString01 is never NULL", sTimestamp01);
			 System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ermittelter Timestamp01 " + sTimestamp01);
			 			
			 String sTimestamp02 = DateTimeZZZ.computeTimestampUniqueString();
			 assertNotNull("TimestampString02 is never NULL", sTimestamp02);
			 System.out.println(ReflectCodeZZZ.getPositionCurrent() + ": Ermittelter Timestamp02 " + sTimestamp02);
			 
			 //Merke: Dieser Timestamp ist in diesem Fall zwingend unique.
			 assertFalse("Timestamps sollten verschieden sein, auch wenn nur wenige Milisekunden zwischen ihrer Erzeugung liegen.", sTimestamp01.equals(sTimestamp02) );
			 
		 }catch(ExceptionZZZ ez){
				fail("Method throws an exception." + ez.getMessageLast());
		}
	 }
	 
}//End class
