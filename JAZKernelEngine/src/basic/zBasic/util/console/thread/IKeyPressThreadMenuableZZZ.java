package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointUserZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;

public interface IKeyPressThreadMenuableZZZ extends IKeyPressThreadMenulessZZZ, IMenuPointUserZZZ {
    //wird immer gemacht bei IKeyPressThreadZZZ
	//darum hier nicht       public void makeMenuMain() throws InterruptedException,ExceptionZZZ;//zu überschreiben...Das Hauptmenue ausgeben
          
    public boolean processMenuPoint(String sInput, HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit
    public boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit, Also die Eingabe nach der Eingabe der Argumente
    

	public IMenuPointZZZ getMenuPoint() throws ExceptionZZZ;
	public void setMenuPoint(IMenuPointZZZ objMenuPoint) throws ExceptionZZZ;
	
    public boolean isCurrentMenue()throws ExceptionZZZ;
    public void isCurrentMenue(boolean bMakeMenue) throws ExceptionZZZ;
    
    public boolean isMenueWhenFinished() throws ExceptionZZZ;
    public void isMenueWhenFinished(boolean bMakeMenueWhenFinished) throws ExceptionZZZ;
    
    
    //######################
    public void cancelToMenue(HashMapZZZ hmVariable, String sConsoleText) throws IllegalArgumentException, ExceptionZZZ;
    public void cancelToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ;
	public void cancelToMenue() throws ExceptionZZZ;
	
	//Nach dem Beenden des Menüpunkts zurück zum Menü
	public void validToMenueLater(HashMapZZZ hmVariable, String sConsoleText) throws ExceptionZZZ; 
	public void validToMenueLater() throws ExceptionZZZ;
	
    public void validToMenue(HashMapZZZ hmVariable, String sConsoleText) throws IllegalArgumentException, ExceptionZZZ;
    public void validToMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ;
	public void validToMenue() throws ExceptionZZZ;
	
	public void validSkipMenue(HashMapZZZ hmVariable, String sConsoleText) throws IllegalArgumentException, ExceptionZZZ;
	public void validSkipMenue(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ;
	public void validSkipMenue() throws ExceptionZZZ;
	
	public void validMenue(boolean bCurrentMenue) throws ExceptionZZZ;
	
	
	//+++++++++++++++++++++++++
	
	public void validSkipRepeatQuestion(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ;
	public void validEnableRepeatQuestion(HashMapZZZ hmVariable) throws IllegalArgumentException, ExceptionZZZ;	
}
