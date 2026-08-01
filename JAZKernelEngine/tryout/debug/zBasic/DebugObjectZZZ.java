package debug.zBasic;

import static java.lang.System.out;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basic.zBasic.ExceptionZZZ;
import basic.zBasic.ReflectCodeZZZ;
import basic.zBasic.util.file.txt.bytes.TxtReaderZZZ;
import basic.zKernel.flag.IFlagZEnabledZZZ;

public class DebugObjectZZZ {

	/** TODO What the method does.
	 * @param args
	 * 
	 * Fritz Lindhauer; 12.06.2014 13:29:49
	 */
	public static void main(String[] args) {
		
		DebugObjectZZZ objDebug = new DebugObjectZZZ();
		
		//Debugge die getFlag / setFlag / proofFlagExists Funktionalit�t
		System.out.println("### DEBUG: SubClasses - Scan Rekursiv ####");
		objDebug.getSubClasses();
		
		System.out.println("### DEBUG: EmbededClasses - Aus Classes[] Array ####");
		objDebug.getEmbeddedClasses();
		
		
	}
	
	public void getSubClasses(){
		
		

		ArrayList<Class<?>> subTypes = new ArrayList<Class<?>>();
	
		TxtReaderZZZ objReader;
		try {
			objReader = new TxtReaderZZZ();

			System.out.println("+++ Start scanning ++++");
		this.scanSuperClasses(objReader.getClass(), subTypes);		
		
		for (Class subClass : subTypes) {
			System.out.println("Classname stored: " + subClass.getName());
		}
		
		} catch (ExceptionZZZ e) {
			e.printStackTrace();		
		}		
		
	}
	
	public void scanSuperClasses(Class objSubClass, ArrayList<Class<?>> subTypes){
		
		Class objSuperClass = objSubClass.getSuperclass();
		if(objSuperClass!=null){
		System.out.println("SuperClassname: " + objSuperClass.getName());
		subTypes.add(objSuperClass);
		
		this.scanSuperClasses(objSuperClass, subTypes);
		}
	}
	
	
public void getEmbeddedClasses(){
				
		ArrayList<Class<?>> subTypes = new ArrayList<Class<?>>();
	
		TxtReaderZZZ objReader;
		try {
			objReader = new TxtReaderZZZ();
		
			System.out.println("+++ Start scanning ++++");
			this.scanEmbeddedClasses(objReader, subTypes);	
		
		for (Class subClass : subTypes) {
			System.out.println("Classname stored: " + subClass.getName());		
		}
		
		for (Class subClass : subTypes) {
			Object[] objaEnumConstant = subClass.getEnumConstants();
						
			if(objaEnumConstant!=null){
				
				List<Object> list = Arrays.asList(objaEnumConstant);
				Object obj = list.get(0);
				out.format("%s# Enum aus Klasse %s%n", ReflectCodeZZZ.getMethodCurrentName(), obj.getClass().getName());
				Field[] fields = obj.getClass().getDeclaredFields();
				for(Field field : fields){
					if(!field.isSynthetic()){ //Sonst wird ENUM$VALUES auch zur�ckgegeben.
						out.format("%s# Field...%s%n", ReflectCodeZZZ.getMethodCurrentName(), field.getName());							
					}
				}//end for				
			}//end if objaEnumConstant!=null		
		}
		
		
		
		} catch (ExceptionZZZ e) {
			e.printStackTrace();		
		}		
		
	}
	

	
	
	public void scanEmbeddedClasses(IFlagZEnabledZZZ objSubClass, ArrayList<Class<?>> subTypes){
		Class[] objaClass = objSubClass.getClass().getClasses();
		for(Class objClass : objaClass){
		
			System.out.println("Classname in array: " + objClass.getName());
			subTypes.add(objClass);
		}	
	}

}//end class
