package basic.zBasic.reflection;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.IReflectLaunchArgumentZZZ;
import basic.zBasic.util.abstractArray.ArrayUtilZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.machine.EnvironmentPlaceholderZZZ;

/** Mit AbstractKernelConfigZZZ - Klassen koennen ja Argumente entgegen genommen werden.
 *  Diese Klasse ist dem noch vorgelagert und wird von AbstractKernelConfigZZZ verwendet.
 *  
 *  Beispielsweise ist eine Konvention, dass es in der Kommandozeile Platzhalter für Umgebungsvariablen geben kann,
 *  die dann vor dem Bauen des Konfigurationsobjekts gegen ihren Wert ausgetauscht werden.
 *  
 *  Hintergrund:
 *  Damit soll es dann möglich sein
 *  a) Launch-Konfigurationen über mehrer Eclipse Installationen gleich zu halten
 *  b) Geheime Daten, die nicht in der Launch-Konfiguration selbst stehen dürfen (z.B. weil die auch in ein Git-Repository kommen)
 *     von aussen an die Applikation zu uebergeben.
 *  
 * @author Fritz Lindhauer, 02.04.2026, 14:29:33
 * 
 */
public class ReflectLaunchArgumentZZZ implements IReflectLaunchArgumentZZZ, IConstantZZZ{
	
	private ReflectLaunchArgumentZZZ(){
		//zum 'Verstecken" des Konstruktors
	}//only static Methods
	
	//hier ggfs. die entsprechenden Umgebungsvariablen holen mit system.getEnv(...)
	public static String[] replaceArgumentsWithEnvironmentValue(String[] saArg) throws ExceptionZZZ{
		String[] saReturn=saArg;
		main:{
			if(ArrayUtilZZZ.isEmpty(saArg)) break main;
						
			
			//Idee: Versuche Placeholder-Objekte zu erstellen, wenn diese einen Content haben, 
			//      deren Namen holen und sie dann den Wert holen lassen.
			int iIndex=-1;
			for(String sArg : saArg) {
				iIndex++;
				
				String sContent = EnvironmentPlaceholderZZZ.readContent(sArg); //das spart das Erstellen des Objekts
				if(!StringZZZ.isEmpty(sContent)) {
					EnvironmentPlaceholderZZZ objPlaceholderTemp = new EnvironmentPlaceholderZZZ(sArg);
					objPlaceholderTemp.setContent(sContent); //damit nutzen wir die Tatsache, das wir den Content schon errechnet haben.
					
					String sValue = objPlaceholderTemp.getValue();
					saReturn[iIndex]=sValue;
				}
			}
		
		}//end main:
		return saReturn;
	}
}
