package basic.zBasic.util.console.thread;

import java.util.HashMap;
import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.util.abstractList.HashMapZZZ;
import debug.zBasic.util.console.thread.multi.menu02.IThreadWithStatusLocalEnabledZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointUserZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IMenuPointZZZ;
import debug.zBasic.util.console.thread.multi.menu03.IVariableHashMapUserZZZ;

public interface IKeyPressThreadMenuableZZZ extends IKeyPressThreadZZZ, IMenuPointUserZZZ, IVariableHashMapUserZZZ {
    public void makeMenuMain() throws InterruptedException,ExceptionZZZ;//zu überschreiben...Das Hauptmenue ausgeben
    
    public boolean initit(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //Die Methode des Threads aufrufen. Hier wird die 
	    
    public boolean processMenuPoint(String sInput, HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit
    public boolean processMenuePostArgumentInput(HashMapZZZ<String,Object> hmVariable) throws ExceptionZZZ; //zu überschreiben, false=quit, Also die Eingabe nach der Eingabe der Argumente
}
