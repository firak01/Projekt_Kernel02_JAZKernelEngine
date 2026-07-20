package basic.zBasic.config;

import basic.zBasic.ExceptionZZZ;

public interface IConfigProjectZZZ {
	//Das jeweils eigene Projekt. Merke: normalerweise private Konstante .sPROJECT_NAME
	public String getProjectNameDefault() throws ExceptionZZZ;			
	public String getProjectName() throws ExceptionZZZ; //Merke: Da man den Projektnamen nicht programmatisch ermitteln kann, wird hier nur ein statischer Wert zurueckgeliefert. ... 	
	//... Daher ist eine entsprechende Setter-Funktion nicht notwendig.	
	
	//Der Pfad zum eigenen Projekt. Merke: normalerweise private Konstante .sPROJECT_DIRECTORY
	public String getProjectDirectoryDefault() throws ExceptionZZZ;
	public String getProjectDirectory() throws ExceptionZZZ; //der Projektname reicht ggfs. nicht
		
}
