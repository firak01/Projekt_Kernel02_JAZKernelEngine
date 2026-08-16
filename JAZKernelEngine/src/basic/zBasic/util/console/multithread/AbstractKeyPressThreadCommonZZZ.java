package basic.zBasic.util.console.multithread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import basic.zBasic.util.console.multithread.AbstractKeyPressThreadZZZ;
import basic.zBasic.util.console.multithread.IConsoleZZZ;
import basic.zBasic.util.console.multithread.IKeyPressConstantZZZ;
import basic.zBasic.util.console.multithread.KeyPressUtilZZZ;
import basic.zBasic.util.datatype.booleans.BooleanZZZ;
import basic.zBasic.util.datatype.character.CharacterExtendedZZZ;
import basic.zBasic.util.datatype.character.ICharacterExtendedZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;

public abstract class AbstractKeyPressThreadCommonZZZ extends AbstractKeyPressThreadZZZ {
	public AbstractKeyPressThreadCommonZZZ(IConsoleZZZ objConsole) {
    	super(objConsole);
    }
    public AbstractKeyPressThreadCommonZZZ(IConsoleZZZ objConsole, long lSleepTime) {
    	super(objConsole, lSleepTime);
    }
    
  //###############################################
	protected boolean printTableASCII(HashMapZZZ hmVariable) throws ExceptionZZZ {
		//Ausgabe der ASCII-Zeichen auf dem aktuellen System
		boolean bReturn = true;
		main:{
			KeyPressThreadUtilZZZ.printTableAscii();
			this.isCurrentMenue(true);//das Menue erneut aufbauen
    		this.isCurrentInputFinished(true);
    		this.isInputAllFinished(true);//das beendet diesen Menuelauf
    		this.isOutputAllFinished(true);//das bewirkt, das kein anderer Thread eine Ausgabe macht.
    		
			System.out.println("Weiter mit der Menueeingabe....");
		}//end main:
		return bReturn;						
	}    		
}
