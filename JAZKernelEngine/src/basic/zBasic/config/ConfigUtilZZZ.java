package basic.zBasic.config;

import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.IConstantZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zKernel.config.help.IKernelConfigHeaderLineZZZ;
import basic.zKernel.config.help.IKernelConfigHelpLineZZZ;

public class ConfigUtilZZZ  implements IConstantZZZ{
	private ConfigUtilZZZ() { 
		//Zum Verstecken des Konsruktors
	} //static methods only

	public static String createHelp(List<IKernelConfigHelpLineZZZ> listaHelpLineTotal)throws ExceptionZZZ{
		String sReturn="";
		main:{
			String sHeadLineOld = "";
			for(IKernelConfigHelpLineZZZ objHelpLineTotal : listaHelpLineTotal) {
				IKernelConfigHeaderLineZZZ objHeaderLine = objHelpLineTotal.getHeaderLine();
				String sHeadLine = "";
				if(objHeaderLine!=null) {
					sHeadLine = objHeaderLine.getHeaderLine();
				}				
				String sAbbr = "";
				if(!StringZZZ.isEmptyNull(objHelpLineTotal.getAbbreviation())){
					sAbbr = StringZZZ.left(objHelpLineTotal.getAbbreviation() + StringZZZ.repeat(" ", 20),20);
				}else {
					sAbbr = StringZZZ.repeat(" ", 20);
				}
				
				String sName = "";
				if(!StringZZZ.isEmptyNull(objHelpLineTotal.getName())){
					sName = StringZZZ.left(objHelpLineTotal.getName() + StringZZZ.repeat(" ", 30),30);
				}else {
					sName = StringZZZ.repeat(" ", 30);
				}
								
				String sDescr = "";
				if(!StringZZZ.isEmptyNull(objHelpLineTotal.getDescription())) {
					sDescr = objHelpLineTotal.getDescription();
				}
				
				
				if(!StringZZZ.isEmptyNull(sHeadLine) & !sHeadLine.equals(sHeadLineOld)) {
					sReturn = sReturn + StringZZZ.crlf() + StringZZZ.crlf() + sHeadLine ;//also Ende der vorherigen Zeile PLUS eine Zeile Abstand
					sHeadLineOld = sHeadLine;
				}
				
				String sContentLine = sAbbr + sName + sDescr;
				if(!StringZZZ.isEmptyTrimmed(sContentLine)){
					sReturn = sReturn + StringZZZ.crlf() + sContentLine;
				}
				
			}//end for
		}//end main:
		return sReturn;
	}
}
