package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IKeyPressThreadZZZ extends IThreadEnabledZZZ{
	
	//### Damit wird der konkrete KeyPressThread von anderen KeyPressThreads aus nutzbar, ohne den Code zu verdoppeln
	public IKeyPressThreadZZZ getKeyPressThreadUsed() throws ExceptionZZZ;
	public void setKeyPressThreadUsed(IKeyPressThreadZZZ objKeyPressThread) throws ExceptionZZZ;
	
	public String getMethodForThreadUsed() throws ExceptionZZZ;
	public void setMethodForThreadUsed(String sMethodName) throws ExceptionZZZ;
	
	public boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die 
	
	//###########################################################
	public long getSleepTime();
	public void setSleepTime(long lSleepTime);

	public Scanner getInputReader();//Das Scanner Objekt
	public void setInputReader(Scanner objScanner);
	
	public boolean isKeyPressThreadFinished(); //Informiert die Konsole, das der Eingabethread fertig ist
	public void isKeyPressThreadFinished(boolean bFinished);
			
	public boolean isCurrentInputFinished(); //Flag, das darüber informiert, dass keine weiteren Eingaben gemacht werden sollen. 
    public void isCurrentInputFinished(boolean bCurrentInput);
    
    public boolean isCurrentInputValid();    //Gedacht für eine WHILE Schleife, z.B. im ersten Menue: Solange die Eingabe abfragen, bis was gueltiges ausgewaehlt wird.
    public void isCurrentInputValid(boolean bCurrentInput) ;
    
    public boolean isCurrentMenue();         //falls true, dann soll wieder das Menue mit den Eingabemoeglichkeiten angezeigt werden
    public void isCurrentMenue(boolean bMakeMenue);
    
    public boolean isInputAllFinished();
    public void isInputAllFinished(boolean bInputAllFinished) ;
    
    public boolean isOutputAllFinished();
    public void isOutputAllFinished(boolean bOutputAllFinished) ;
    
    public void makeMenueMain() throws InterruptedException,ExceptionZZZ;//zu überschreiben...Das Hauptmenue ausgeben
    public boolean processMenueMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit
    public boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit, Also die Eingabe nach der Eingabe der Argumente
    
}
