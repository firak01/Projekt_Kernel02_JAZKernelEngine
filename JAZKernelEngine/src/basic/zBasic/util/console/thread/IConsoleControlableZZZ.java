package basic.zBasic.util.console.thread;

import basic.zBasic.ExceptionZZZ;

public interface IConsoleControlableZZZ {
    //analog zu IThreadableZZZ
    //Zum Beenden der Gesamtkonsole. Ergänzt das einfache stop eines Threads
    public boolean isQuitted() throws ExceptionZZZ ;
	public void isQuitted(boolean bStop) throws ExceptionZZZ;
	public void requestQuit() throws ExceptionZZZ;	
}
