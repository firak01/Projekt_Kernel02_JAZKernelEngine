package basic.zBasic.config;

import basic.zBasic.ExceptionZZZ;

/** Das Problem ist, dass Java nichts ueber die Projektstruktur innerhalb einer IDE (Z.B. Eclipse) weiss
 *  Darauf zuzugreifen benötigt das Einbinden von Plugins (z.B. unter Eclipse)
 *  Wenn aber ein Modul eine Datei in einem anderen Modul benötigt und diese Module in verschiedenen Projekten liegen,
 *  dann muss man irgendwie den Projektnamen übergeben.
 *  Daraus kann dann der Pfadname errechnet werden. 
 *  
 * 
 * @author Fritz Lindhauer, 22.06.2023, 19:58:31
 * 
 */
public interface IConfigProjectHelperZZZ extends IConfigProjectZZZ{
	//... dito
	public String getProjectPath() throws ExceptionZZZ;
	public String computeProjectPath() throws ExceptionZZZ;
	
	public String getProjectPathTotal() throws ExceptionZZZ;
	public String computeProjectPathTotal() throws ExceptionZZZ;
	
	public String getCallingProjectPath(); //um den Projektnamen auszurechen, braucht man den Pfad des aufrufenden Projekts. Dann kann man den reinen WorkspacePfad ausrechnen.
	public void setCallingProjectPath(String sCallingProjectPath);

}
