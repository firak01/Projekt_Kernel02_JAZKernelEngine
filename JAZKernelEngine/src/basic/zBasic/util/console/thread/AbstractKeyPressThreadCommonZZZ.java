package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.thread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.thread.IConsoleControllerZZZ;
import basic.zBasic.util.console.thread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.thread.KeyPressUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.character.ICharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractKeyPressThreadCommonZZZ extends AbstractKeyPressThreadZZZ {
	public AbstractKeyPressThreadCommonZZZ(IConsoleControllerZZZ objConsole) {
    	super(objConsole);
    }
    public AbstractKeyPressThreadCommonZZZ(IConsoleControllerZZZ objConsole, long lSleepTime) {
    	super(objConsole, lSleepTime);
    }
    
//  //###############################################
//	protected boolean printTableASCII(HashMapZZZ hmVariable) throws ExceptionZZZ {
//		//Ausgabe der ASCII-Zeichen auf dem aktuellen System
//		boolean bReturn = true;
//		main:{
//			KeyPressThreadUtilZZZ.printTableAscii();
//			this.isCurrentMenue(true);//das Menue erneut aufbauen
//    		this.isCurrentInputFinished(true);
//    		this.isInputAllFinished(true);//das beendet diesen Menuelauf
//    		this.isOutputAllFinished(true);//das bewirkt, das kein anderer Thread eine Ausgabe macht.
//    		
//			System.out.println("Weiter mit der Menueeingabe....");
//		}//end main:
//		return bReturn;						
//	}    		
}
