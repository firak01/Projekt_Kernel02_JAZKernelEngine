package basic.zKernel;

import java.io.File;
import java.util.ArrayList;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.KernelZZZ;
import custom.zKernel.ConfigFGL;
import custom.zKernel.KernelLogZZZ;
import custom.zUtil.io.FileZZZ;
import junit.framework.TestCase;

public class KernelZZZIsolatedTest extends TestCase {
	/** wird in der TestSuite AllTest zusammengefasst.
	 * Darum gibt es hier keine Main - Methode.
	 * Die Main Methode von AllTest kann ausgef�hrt werden.
	 */
	
	private IKernelZZZ objKernelFGL;
	private IKernelZZZ objKernelTest;
	
	
	protected void setUp(){
		try {			
			//TODO: Dies ini-Datei zuvor per Programm erstellen
			objKernelFGL = new KernelZZZ("FGL", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
			
			//TODO: Die ini-Datei zuvor per Programm erstellen
			objKernelTest = new KernelZZZ("TEST", "01", "test", "ZKernelConfigKernel_test.ini",(String[]) null);
	
		} catch (ExceptionZZZ e) {
			fail("Method throws an exception." + e.getMessageLast());
		}		
	}
	
	private void removeLogFile(IKernelZZZ objKerneltemp) throws ExceptionZZZ{
		//		Log-File entfernen
		if(objKerneltemp != null){
			KernelLogZZZ objLog = objKerneltemp.getLogObject();
			if(objLog != null){
				FileZZZ objFile = objLog.getFileObject();
				if(objFile!=null){
					objFile.deleteOnExit();
				}
			}
		}
	}
	


/** Kopiere hier einzelne Testausdrücke rein, um nicht alle Tests debuggen zu müssen
 * 
 * @author Fritz Lindhauer, 13.07.2019, 08:38:03
 */
public void testParameterByProgramAlias(){
	try {
//Merke: Mache folgendes, damit kein Unterschied vom Isolated zum normalen Test besteht.
String sClassname = this.getClass().getName(); 
sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
			
}


public void testGetModuleAliasAll(){
	try {
	//Merke: Mache folgendes, damit kein Unterschied vom Isolated zum normalen Test besteht.
	String sClassname = this.getClass().getName(); 
	sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}

public void testConstructorConfigObject(){
	try {
	//Merke: Mache folgendes, damit kein Unterschied vom Isolated zum normalen Test besteht.
	String sClassname = this.getClass().getName(); 
	sClassname = StringZZZ.replace(sClassname, "Isolated", "");
	}catch(ExceptionZZZ ez){
		ez.printStackTrace();
		fail("Method throws an exception." + ez.getMessageLast());
	}
	
}


}//END Class