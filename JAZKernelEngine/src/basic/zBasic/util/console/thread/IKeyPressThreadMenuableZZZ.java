package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;

public interface IKeyPressThreadMenuableZZZ extends IKeyPressThreadZZZ{
    public void makeMenuMain() throws InterruptedException,ExceptionZZZ;//zu überschreiben...Das Hauptmenue ausgeben
    
    public boolean initit(HashMapZZZ hmVariable) throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die 
	
    
    public boolean processMenuMainArgumentInput(String sInput, HashMapZZZ hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit
    public boolean processMenuePostArgumentInput(HashMapZZZ hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit, Also die Eingabe nach der Eingabe der Argumente
    
}
