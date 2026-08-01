package basic.zBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import junit.framework.TestCase;
import basic.javagently.Stream;
import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.ReflectEnvironmentZZZ;
import basic.zBasic.util.file.FileEasyZZZ;
import basic.zBasic.util.file.txt.parser.FileTextParserZZZ;

public class ReflectEnvironmentZZZTest extends TestCase{
	
	private DummyTestObjectWithFlagZZZ objObjectTest = null;

	protected void setUp(){
		//try {			
			
			//The main object used for testing
			objObjectTest = new DummyTestObjectWithFlagZZZ();
			
		
//		} catch (ExceptionZZZ e) {
//			fail("Method throws an exception." + e.getMessageLast());
//		} 
	}//END setup
	 
	
	public void testEnvironment(){
		//try{
			boolean btemp;
		
			//Init - Object
			String stemp = ReflectEnvironmentZZZ.getJavaVersionCurrent();
			assertNotNull("Kann nicht auf aktuelle Java-Version zugreifen (NULL)",stemp);
			
			stemp = stemp.trim();
			assertFalse("Kann nicht auf aktuelle Java-Version zugreifen (Leerstring))", stemp.equals(""));					
			System.out.println("Aktuelle Java-Version=" + stemp);
						
			String sVersionString = ReflectEnvironmentZZZ.getJavaVersionMain(stemp);
			switch (sVersionString){//äh, switch mit String ist erst ab Java ... verfügbar
			case ReflectEnvironmentZZZ.sJAVA4:
				btemp = ReflectEnvironmentZZZ.isJava4();
				assertTrue("Version ist nicht wie erwartet Java 4", btemp);
				break;
			case ReflectEnvironmentZZZ.sJAVA5:
				btemp = ReflectEnvironmentZZZ.isJava5();
				assertTrue("Version ist nicht wie erwartet Java 5", btemp);
				break;
			case ReflectEnvironmentZZZ.sJAVA6:
				btemp = ReflectEnvironmentZZZ.isJava6();
				assertTrue("Version ist nicht wie erwartet Java 6", btemp);
				break;
			case ReflectEnvironmentZZZ.sJAVA7:
				btemp = ReflectEnvironmentZZZ.isJava7();
				assertTrue("Version ist nicht wie erwartet Java 7", btemp);
				break;
			case ReflectEnvironmentZZZ.sJAVA8:
				btemp = ReflectEnvironmentZZZ.isJava8();
				assertTrue("Version ist nicht wie erwartet Java 8", btemp);
				break;
			default:
				fail("Java Version " + stemp + " wird noch nicht behandelt.");			
			}
			
//		}catch(ExceptionZZZ ez){
//			fail("An exception happend testing: " + ez.getDetailAllLast());
//		}
	}
	
	public void testIsJavaVersionMainCurrentEqualOrNewerThan(){
//		try{
		String stemp = ReflectEnvironmentZZZ.getJavaVersionCurrent();
		assertNotNull("Kann nicht auf aktuelle Java-Version zugreifen (NULL)",stemp);
		
		stemp = stemp.trim();
		assertFalse("Kann nicht auf aktuelle Java-Version zugreifen (Leerstring))", stemp.equals(""));
		
		boolean btemp = ReflectEnvironmentZZZ.isJavaVersionMainCurrentEqualOrNewerThan(stemp);
		assertTrue("Fehler beim Java-Versionsvergleich",btemp);//Da man die aktuelle Version mit der aktuellen Version selbst vergleicht, muss einfach true herauskommen
//		} catch (ExceptionZZZ ez) {
//			fail("Method throws an exception." + ez.getMessageLast());
//		} 		
	}	
}//END Class
