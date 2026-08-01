package basic.zBasic.util.file.jar.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.zip.ZipEntry;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.AbstractObjectWithFlagZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.datatype.string.StringZZZ;
import basic.zBasic.util.file.zip.FileDirectoryEmptyPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FileDirectoryWithContentPartFilterZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterEndingZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterMiddleZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterNameZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPathZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterPrefixZipZZZ;
import basic.zBasic.util.file.zip.FilenamePartFilterSuffixZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryEmptyPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipUserZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryWithContentPartFilterZipZZZ;
import basic.zBasic.util.file.zip.IFileDirectoryPartFilterZipZZZ;
import basic.zBasic.util.file.zip.ZipEntryFilter;
import basic.zKernel.flag.IFlagZEnabledZZZ;
import basic.zUtil.io.IFileExpansionEnabledZZZ;
import basic.zUtil.io.IFileExpansionZZZ;

public abstract class AbstractFileDirectoryFilterInJarZZZ extends AbstractObjectWithFlagZZZ implements IFileDirectoryPartFilterZipUserZZZ{
	protected IFileDirectoryEmptyPartFilterZipZZZ objPartFilterDirectoryEmpty; //Nur Verzeichnisse
	protected IFileDirectoryPartFilterZipZZZ objPartFilterDirectory;	//Nur Dateien, ohne konkreten Verzeichnispfad, also nur nach Dateiname/relativen Pfad.
	protected IFileDirectoryWithContentPartFilterZipZZZ objPartFilterDirectoryWithContent; //Nur Dateien, Verzeichnispfad am Anfang
		
	public AbstractFileDirectoryFilterInJarZZZ() throws ExceptionZZZ {
		this("","init");
	}		
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath) throws ExceptionZZZ {
		super();
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, null);
	}
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath, String sFlagControlIn) throws ExceptionZZZ {
		super();
		String[] saFlagControl = new String[1];
		saFlagControl[0] = sFlagControlIn;
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControl);
	}
	public AbstractFileDirectoryFilterInJarZZZ(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		super();
		AbstractDirectoryFilterInJarNew_(sDirectoryPath, saFlagControlIn);
	} 	
	private void AbstractDirectoryFilterInJarNew_(String sDirectoryPath, String[] saFlagControlIn) throws ExceptionZZZ {
		String stemp; boolean btemp;
		main:{
		//setzen der übergebenen Flags	
		if(saFlagControlIn != null){
			for(int iCount = 0;iCount<=saFlagControlIn.length-1;iCount++){
				stemp = saFlagControlIn[iCount];
				btemp = setFlag(stemp, true);
				if(btemp==false){ 								   
					   ExceptionZZZ ez = new ExceptionZZZ( IFlagZEnabledZZZ.sERROR_FLAG_UNAVAILABLE + stemp, IFlagZEnabledZZZ.iERROR_FLAG_UNAVAILABLE, ReflectCodeZZZ.getMethodCurrentName(), ""); 
					   //doesn�t work. Only works when > JDK 1.4
					   //Exception e = new Exception();
					   //ExceptionZZZ ez = new ExceptionZZZ(stemp,iCode,this, e, "");
					   throw ez;		 
				}
			}
			}

		//+++ Falls das Debug-Flag gesetzt ist, muss nun eine Session �ber das Factory-Objekt erzeugt werden. 
		// Damit kann auf andere Datenbanken zugegriffen werden (z.B. im Eclipse Debugger)
		// Besser jedoch ist es beim Debuggen mit einem anderen Tool eine Notes-ID zu verwenden, die ein leeres Passwort hat.
		btemp = this.getFlag("init");
		if(btemp==true) break main;

		this.setDirectoryPath(sDirectoryPath);
		
		}//end main:		
	}
	
	public boolean accept(ZipEntry ze) {
		boolean bReturn=false;
		main:{
			if(ze==null) break main;				
			
			//Merke: Die Reihenfolge ist so gewählt, dass im Template Verzeichnis frühestmöglich ein "break main" erreicht wird.
			
			//Falls das Verzeichnis nicht passt	
			try {
				if(!StringZZZ.isEmpty(this.getDirectoryPath())){
					String sName = ze.getName();
					if(ze.isDirectory()) {
						System.out.println("STOP: REINES VERZEICHNIS "+ze.getName());
						
						if(StringZZZ.contains(sName,"template")) {
							System.out.println("STOP: BREAKPOINT01");
							System.out.println("------");
						}
						
						if(StringZZZ.contains(sName,"subDirectory01")) {
							System.out.println("STOP: BREAKPOINT02");
							System.out.println("------");
						}
						
						if(StringZZZ.contains(sName,"subDirectory04withoutFiles")) {
							System.out.println("STOP: BREAKPOINT03");
							System.out.println("------");
						}
					}
					
					
					
					this.getDirectoryPartFilterEmpty().setCriterion(this.getDirectoryPath());					
					if(this.getDirectoryPartFilterEmpty().accept(ze)==true) {
						bReturn = true;
						break main;
					}
					
					this.getDirectoryPartFilterWithConent().setCriterion(this.getDirectoryPath());
					if(this.getDirectoryPartFilterWithConent().accept(ze)==true) {
						bReturn = true;
						break main;
					}
																
					this.getDirectoryPartFilter().setCriterion(this.getDirectoryPath());				
					if(this.getDirectoryPartFilter().accept(ze)==true) {
						bReturn = true;
						break main;
					}
																			
				}//End if 	!StringZZZ.isEmpty(this.getDirectoryPath())						
			} catch (ExceptionZZZ ez) {
				String sLog = "AbstractFileDirectoryFilterInJarZZZ -> ExceptionZZZ: '" + ez.getMessageLast() +"'.";
				System.out.println(sLog);
			}
		}//END main:
		return bReturn;		
	}
	
	//##### GETTER / SETTER			
		protected void setDirectoryPath(String sDirectoryPathIn) throws ExceptionZZZ {			
			this.getDirectoryPartFilter().setCriterion(sDirectoryPathIn);
		}
		protected String getDirectoryPath() throws ExceptionZZZ {			
			return this.getDirectoryPartFilter().getCriterion();
		}
		
		@Override
		public void setDirectoryPartFilter(IFileDirectoryPartFilterZipZZZ objDirectoryFilterZip) {
			this.objPartFilterDirectory = objDirectoryFilterZip;
		}
		@Override
		public IFileDirectoryPartFilterZipZZZ getDirectoryPartFilter() throws ExceptionZZZ {
			if(this.objPartFilterDirectory==null) {
				this.objPartFilterDirectory = new FileDirectoryPartFilterZipZZZ();
			}
			return this.objPartFilterDirectory;
		}
		
		
		@Override
		public void setDirectoryPartFilterWithContent(IFileDirectoryWithContentPartFilterZipZZZ objDirectoryFilterZip) {
			this.objPartFilterDirectoryWithContent = objDirectoryFilterZip;
		}

		@Override
		public IFileDirectoryWithContentPartFilterZipZZZ getDirectoryPartFilterWithConent() throws ExceptionZZZ {
			if(this.objPartFilterDirectoryWithContent==null) {
				this.objPartFilterDirectoryWithContent = new FileDirectoryWithContentPartFilterZipZZZ(this.getDirectoryPath());
			}
			return this.objPartFilterDirectoryWithContent;
		}
		
		@Override
		public void setDirectoryPartFilterEmpty(IFileDirectoryEmptyPartFilterZipZZZ objDirectoryFilterZip) {
			this.objPartFilterDirectoryEmpty = objDirectoryFilterZip;
		}

		@Override
		public IFileDirectoryEmptyPartFilterZipZZZ getDirectoryPartFilterEmpty() throws ExceptionZZZ {
			if(this.objPartFilterDirectoryEmpty==null) {
				this.objPartFilterDirectoryEmpty = new FileDirectoryEmptyPartFilterZipZZZ(this.getDirectoryPath());
			}
			return this.objPartFilterDirectoryEmpty;
		}
		
}//END class