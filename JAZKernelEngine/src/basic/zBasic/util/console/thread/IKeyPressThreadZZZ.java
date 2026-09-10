package basic.zBasic.util.console.thread;

import java.util.Scanner;

import basic.zBasic.ExceptionZZZ;

public interface IKeyPressThreadZZZ extends IThreadableZZZ, IKeyPressThreadUserZZZ{
	
	//### Für den ConsoleService
	public String getMethodForConsoleService() throws ExceptionZZZ;
	public void setMethodForConsoleService(String sMethodName) throws ExceptionZZZ;
	   
	
	//###########################################################

	public Scanner getInputReader()throws ExceptionZZZ;//Das Scanner Objekt
	public void setInputReader(Scanner objScanner)throws ExceptionZZZ;
	
	public boolean isKeyPressThreadFinished()throws ExceptionZZZ; //Informiert die Konsole, das der Eingabethread fertig ist
	public void isKeyPressThreadFinished(boolean bFinished)throws ExceptionZZZ;
			
	public boolean isCurrentInputFinished()throws ExceptionZZZ; //Flag, das darüber informiert, dass keine weiteren Eingaben gemacht werden sollen. 
	public void isCurrentInputFinished(boolean bCurrentInput)throws ExceptionZZZ;
    
    public boolean isCurrentInputValid()throws ExceptionZZZ;    //Gedacht für eine WHILE Schleife, z.B. im ersten Menue: Solange die Eingabe abfragen, bis was gueltiges ausgewaehlt wird.
    public void isCurrentInputValid(boolean bCurrentInput)throws ExceptionZZZ;
    
    public boolean isInputAllFinished() throws ExceptionZZZ;
    public void isInputAllFinished(boolean bInputAllFinished)throws ExceptionZZZ; ;
}
