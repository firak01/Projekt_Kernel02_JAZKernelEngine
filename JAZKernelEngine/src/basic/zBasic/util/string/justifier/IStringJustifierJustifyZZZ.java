package basic.zBasic.util.string.justifier;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.reflection.IReflectCodeZZZ;
import basic.zBasic.util.abstractList.ArrayListZZZ;
import basic.zBasic.util.string.formater.IStringFormatZZZ;
import basic.zBasic.util.string.formater.IStringFormaterZZZ;
import custom.zKernel.ILogZZZ;

//Methode, die sowohl der einzelne Justifier als auch der JustifierManager haben
public interface IStringJustifierJustifyZZZ {
		public String justifyLine(String sLine) throws ExceptionZZZ; //versuche den Log-InfoPart ueber alle Zeilen buendig zu machen. Teile auf und verwende zusätzliche \t	
		public ArrayListZZZ<String> justifyLine(ArrayListZZZ<String> listasLine) throws ExceptionZZZ; //versuche den Log-InfoPart ueber alle Zeilen buendig zu machen. Teile auf und verwende zusätzliche \t
}
