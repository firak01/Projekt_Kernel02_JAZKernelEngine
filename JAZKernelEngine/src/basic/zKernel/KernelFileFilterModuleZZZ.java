package basic.zKernel;

import java.io.File;
import java.io.FilenameFilter;

import basic.zBasic.util.file.filter.FilenamePartFilterEndingZZZ;
import basic.zBasic.util.file.filter.FilenamePartFilterSuffixZZZ;

public class KernelFileFilterModuleZZZ implements FilenameFilter {
	FilenamePartFilterEndingZZZ objFilterEnding;
	
	public KernelFileFilterModuleZZZ(){
		objFilterEnding = new FilenamePartFilterEndingZZZ("ini");
	}
	public boolean accept(File objFileDir, String sName) {
		boolean bReturn=false;
		main:{
			check:{
				if(sName==null) break main;				
			}
	 		
		//Falls die Endung nicht passt
		if(this.objFilterEnding.accept(objFileDir, sName)==false) break main;
		if(! sName.toLowerCase().startsWith("zkernelconfig")) break main;
		bReturn = true;
		}//END main:
		return bReturn;		
	}

}

